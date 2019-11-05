package com.bfly.common.microsoft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class Excel2003Process extends ExcelToHtmlConverter {

	public Excel2003Process(Document doc) {
		super(doc);
	}

	public Excel2003Process(HtmlDocumentFacade htmlDocumentFacade) {
		super(htmlDocumentFacade);
	}

	private static final Logger logger = LoggerFactory.getLogger(ExcelToHtmlConverter.class);

	public static void excelToHtml(String source,String targetDir) throws Exception {
		String dirName = FilenameUtils.getBaseName(source).concat("_").concat(FilenameUtils.getExtension(source));
		targetDir=targetDir.concat(File.separator).concat(dirName);
		FileUtils.forceMkdir(new File(targetDir));
		String htmlPath = targetDir.concat(File.separator).concat("//index.html");

		Document doc = process2003(new File(source), targetDir);
		DOMSource domSource = new DOMSource(doc);
		StreamResult streamResult = new StreamResult(new File(htmlPath));

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, Constants.ENCODEING_UTF8);
		serializer.setOutputProperty(OutputKeys.INDENT, "no");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);

		processImg(source, targetDir, htmlPath);
	}
	
	private static Document process2003(File xlsFile, String dirPath) throws IOException, ParserConfigurationException {
		final HSSFWorkbook workbook = ExcelToHtmlUtils.loadXls(xlsFile);
		Excel2003Process convert = new Excel2003Process(
				XMLHelper.getDocumentBuilderFactory().newDocumentBuilder().newDocument());
		convert.setOutputColumnHeaders(false);
		convert.setOutputRowNumbers(false);
		convert.processWorkbook(workbook);
		Document doc = convert.getDocument();
		workbook.close();
		return doc;
	}

	/**
	 * Excel中图片转化为HTML,图片以文件方式存储
	 * 
	 * @param source   Excel的源路径
	 * @param dirPath 图片文件的路径
	 * @param htmlPath 生成的HTML文件路径
	 * @return 关于图片的html
	 * @throws IOException
	 */
	private static void processImg(String source, String dirPath, String htmlPath) throws Exception {
		// 获取Excel所有的图片
		final HSSFWorkbook workbook = ExcelToHtmlUtils.loadXls(new File(source));
		Map<String, HSSFPictureData> pics = getPictrues(workbook);
		if (pics == null || pics.size() == 0) {
			return;
		}
		File imgFolder = new File(dirPath);
		StringBuffer imgHtml = new StringBuffer();
		for (Map.Entry<String, HSSFPictureData> entry : pics.entrySet()) {
			HSSFPictureData pic = entry.getValue();
			// 保存图片, 图片路径 = HTML路径/HTML名称/sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid.后缀
			String ext = pic.suggestFileExtension();
			String imgName = entry.getKey() + "." + ext;
			byte[] data = pic.getData();
			// 创建图片
			try (FileOutputStream out = new FileOutputStream(imgFolder + "/" + imgName)) {
				out.write(data);
			} catch (Exception e) {
				logger.error("导出Excle2003图片出错", e);
			}
			// 图片在Excel中的坐标，sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
			String[] arr = StringUtils.split(entry.getKey(), "_");
			// 图片的上边距和左边距
			float top = getTop(workbook, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
			float left = getLeft(workbook.getSheetAt(Integer.parseInt(arr[0])), Integer.parseInt(arr[2]),
					Integer.parseInt(arr[4]));
			// margin设置为8，以保持和body的内边距一致

			imgHtml.append("<div style='position: absolute; margin:8; left:" + left + "; top:" + top + "pt;'>");
			imgHtml.append("<img src='" + imgName + "'/></div>");
		}
		File htmlFile = new File(htmlPath);
		String html = FileUtils.readFileToString(htmlFile, Constants.ENCODEING_UTF8);
		int end = html.lastIndexOf("</body>");
		String text = StringUtils.substring(html, 0, end);
		text = text.concat(imgHtml.toString()).concat(StringUtils.substring(html, end));
		FileUtils.write(new File(htmlPath), text, Constants.ENCODEING_UTF8);
	}

	/**
	 * Excel的图片获取
	 * 
	 * @param wb Excel的工作簿
	 * @return Excel的图片，键格式：sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
	 */
	private static Map<String, HSSFPictureData> getPictrues(HSSFWorkbook wb) {
		Map<String, HSSFPictureData> map = new HashMap<String, HSSFPictureData>();
		// getAllPictures方法只能获取不同的图片，如果Excel中存在相同的图片，只能得到一张图片
		List<HSSFPictureData> pics = wb.getAllPictures();
		if (pics.size() == 0) {
			return map;
		}
		for (Integer sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet sheet = wb.getSheetAt(sheetIndex);
			HSSFPatriarch patriarch = sheet.getDrawingPatriarch();
			if (patriarch == null) {
				continue;
			}
			for (HSSFShape shape : patriarch.getChildren()) {
				HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
				if (shape instanceof HSSFPicture) {
					HSSFPicture pic = (HSSFPicture) shape;
					int picIndex = pic.getPictureIndex() - 1;
					HSSFPictureData picData = pics.get(picIndex);
					// 键格式：sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
					String key = sheetIndex + "_" + anchor.getRow1() + "_" + anchor.getCol1() + "_" + anchor.getDy1()
							+ "_" + anchor.getDx1() + "_" + (++picIndex);
					map.put(key, picData);
				}
			}
		}
		return map;
	}

	/**
	 * Excel中单元格的上边距（HTML的上边距），支持多Sheet，递加，单位pt
	 * 
	 * @param wb         Excel的工作簿
	 * @param sheetIndex Sheet的索引
	 * @param rowIndex   单元格所在行号
	 * @param dy         单元格内的上边距
	 * @return 上边距
	 */
	private static float getTop(HSSFWorkbook wb, Integer sheetIndex, Integer rowIndex, int dy) {
		float top = 0;
		HSSFSheet sheet = null;
		// 左侧Sheet的上边距
		for (Integer i = 0; i < sheetIndex; i++) {
			sheet = wb.getSheetAt(i);
			// 获得总行数
			Integer rowNum = sheet.getLastRowNum() + 1;
			// 空sheet的总行高是0，空行的行高是0
			top += getTop(sheet, rowNum, 0);
		}
		// 当前sheet的上边距
		sheet = wb.getSheetAt(sheetIndex);
		top += getTop(sheet, rowIndex, dy);
		return top;
	}

	/**
	 * Sheet中单元格的上边距，单位pt，上边距 = SUM(上方每个单元格的高度) + 单元格内的上边距
	 * 
	 * @param sheet    Excel的Sheet
	 * @param rowIndex 单元格所在行号
	 * @param dy       单元格内的上边距
	 * @return 上边距
	 */
	private static float getTop(HSSFSheet sheet, Integer rowIndex, int dy) {
		float top = 0;
		// SUM(上方每个单元格的高度)
		for (int i = 0; i < rowIndex; i++) {
			HSSFRow row = sheet.getRow(i);
			// 排除空行(HTML转化时也被排除了)
			if (row == null) {
				continue;
			}
			top += row.getHeightInPoints();
		}
		// 单元格内的上边距单位转化为pt
		if (dy != 0) {
			top += dy / 20.00f;
		}
		return top;
	}

	/**
	 * Sheet中单元格的左边距，单位px，左边距 = SUM(左侧每个单元格的宽度) + 单元格内的左边距
	 * 
	 * @param sheet    Excel的Sheet
	 * @param colIndex 单元格所在列号，
	 * @param dx       单元格内的左边距
	 * @return 左边距
	 */
	private static float getLeft(HSSFSheet sheet, Integer colIndex, int dx) {
		float left = 0;
		// SUM(左侧每个单元格的宽度)
		for (int i = 0; i < colIndex; i++) {
			float width = sheet.getColumnWidthInPixels(i);
			left += width;
		}
		// 单元格内的左边距单位转化为px
		if (dx != 0) {
			int cw = sheet.getColumnWidth(colIndex);
			int def = sheet.getDefaultColumnWidth() * 256;
			float px = (cw == def ? 32.00f : 36.56f);
			left += dx / px;
		}
		return left;
	}
}
