package com.bfly.common.microsoft;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.hssf.converter.AbstractExcelConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Excel2007Process extends AbstractExcelConverter {

    private static final Logger logger = LoggerFactory.getLogger(ExcelToHtmlConverter.class);

    private String cssClassContainerCell = null;

    private String cssClassContainerDiv = null;

    private String cssClassPrefixCell = "c";

    private String cssClassPrefixDiv = "d";

    private String cssClassPrefixRow = "r";

    private String cssClassPrefixTable = "t";

    private Map<Short, String> excelStyleToClass = new LinkedHashMap<Short, String>();

    private final HtmlDocumentFacade htmlDocumentFacade;

    private boolean useDivsToSpan = false;

    public Excel2007Process(Document doc) {
        htmlDocumentFacade = new HtmlDocumentFacade(doc);
    }

    public Excel2007Process(HtmlDocumentFacade htmlDocumentFacade) {
        this.htmlDocumentFacade = htmlDocumentFacade;
    }

    public static void excelToHtml(String source, String targetDir) throws Exception {
        String dirName = FilenameUtils.getBaseName(source).concat("_").concat(FilenameUtils.getExtension(source));
        targetDir=targetDir.concat(File.separator).concat(dirName);
        FileUtils.forceMkdir(new File(targetDir));
        String htmlPath = targetDir.concat(File.separator).concat("//index.html");

        Document doc = process(new File(source), targetDir);
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

    private static Document process(File xlsFile, String dirPath) throws Exception {
        final XSSFWorkbook workbook = new XSSFWorkbook(xlsFile);
        Excel2007Process convert = new Excel2007Process(
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
     * @param dirPath  图片文件的路径
     * @param htmlPath 生成的html路径
     * @return 关于图片的html
     * @throws IOException
     */
    private static void processImg(String source, String dirPath, String htmlPath) throws Exception {
        // 获取Excel所有的图片
        final XSSFWorkbook workbook = new XSSFWorkbook(new File(source));
        Map<String, XSSFPictureData> pics = getPictrues(workbook);
        if (pics == null || pics.size() == 0) {
            return;
        }
        File imgFolder = new File(dirPath);
        StringBuffer imgHtml = new StringBuffer();
        for (Map.Entry<String, XSSFPictureData> entry : pics.entrySet()) {
            XSSFPictureData pic = entry.getValue();
            // 保存图片, 图片路径 = HTML路径/HTML名称/sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid.后缀
            String ext = pic.suggestFileExtension();
            String imgName = entry.getKey() + "." + ext;
            byte[] data = pic.getData();
            // 创建图片
            try (FileOutputStream out = new FileOutputStream(imgFolder + "/" + imgName)) {
                out.write(data);
            } catch (Exception e) {
                logger.error("导出Excle2007图片出错", e);
            }
            // 图片在Excel中的坐标，sheet索引_行号_列号_单元格内的上边距_单元格内的左边距_uuid
            String[] arr = StringUtils.split(entry.getKey(), "_");
            // 图片的上边距和左边距
            float top = getTop(workbook, Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[3]));
            float left = getLeft(workbook.getSheetAt(Integer.parseInt(arr[0])), Integer.parseInt(arr[2]),
                    Integer.parseInt(arr[4]));
            // margin设置为8，以保持和body的内边距一致

            imgHtml.append("<div style='position: absolute; margin:8; left:" + left + "px; top:" + top + "px;'>");
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
    private static Map<String, XSSFPictureData> getPictrues(XSSFWorkbook wb) {
        Map<String, XSSFPictureData> map = new HashMap<String, XSSFPictureData>();
        // getAllPictures方法只能获取不同的图片，如果Excel中存在相同的图片，只能得到一张图片
        List<XSSFPictureData> pics = wb.getAllPictures();
        if (pics.size() == 0) {
            return map;
        }
        int picIndex = 0;
        for (Integer sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            XSSFSheet sheet = wb.getSheetAt(sheetIndex);
            XSSFDrawing patriarch = sheet.getDrawingPatriarch();
            if (patriarch == null) {
                continue;
            }
            for (XSSFShape shape : patriarch.getShapes()) {
                if (shape instanceof XSSFPicture) {
                    XSSFPicture pic = (XSSFPicture) shape;
                    XSSFPictureData picData = pic.getPictureData();
                    XSSFClientAnchor anchor = pic.getPreferredSize();
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
    private static float getTop(XSSFWorkbook wb, Integer sheetIndex, Integer rowIndex, int dy) {
        float top = 0;
        XSSFSheet sheet = null;
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
    private static float getTop(XSSFSheet sheet, Integer rowIndex, int dy) {
        float top = 0;
        // SUM(上方每个单元格的高度)
        for (int i = 0; i < rowIndex; i++) {
            XSSFRow row = sheet.getRow(i);
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
    private static float getLeft(XSSFSheet sheet, Integer colIndex, int dx) {
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

    private static String getColor(XSSFColor color) {
        StringBuilder stringBuilder = new StringBuilder(7);
        stringBuilder.append('#');
        for (short s : color.getARGB()) {
            if (s < 10) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(s));
        }
        String result = stringBuilder.toString();

        if (result.equals("#ffffff")) {
            return "white";
        }
        if (result.equals("#c0c0c0")) {
            return "silver";
        }
        if (result.equals("#808080")) {
            return "gray";
        }
        if (result.equals("#000000")) {
            return "black";
        }
        return result;
    }

    private String buildStyle(XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        StringBuilder style = new StringBuilder();

        style.append("white-space:pre-wrap;");
        ExcelToHtmlUtils.appendAlign(style, cellStyle.getAlignment());

        switch (cellStyle.getFillPattern()) {
            // no fill
            case 0:
                break;
            case 1:
                final XSSFColor foregroundColor = cellStyle.getFillForegroundColorColor();
                if (foregroundColor == null) {
                    break;
                }
                String fgCol = getColor(foregroundColor);
                style.append("background-color:" + fgCol + ";");
                break;
            default:
                final XSSFColor backgroundColor = cellStyle.getFillBackgroundColorColor();
                if (backgroundColor == null) {
                    break;
                }
                String bgCol = getColor(backgroundColor);
                style.append("background-color:" + bgCol + ";");
                break;
        }

        buildStyle_border(workbook, style, "top", cellStyle.getBorderTop(), cellStyle.getTopBorderXSSFColor());
        buildStyle_border(workbook, style, "right", cellStyle.getBorderRight(), cellStyle.getRightBorderXSSFColor());
        buildStyle_border(workbook, style, "bottom", cellStyle.getBorderBottom(), cellStyle.getBottomBorderXSSFColor());
        buildStyle_border(workbook, style, "left", cellStyle.getBorderLeft(), cellStyle.getLeftBorderXSSFColor());

        XSSFFont font = cellStyle.getFont();
        buildStyle_font(workbook, style, font);

        return style.toString();
    }

    private void buildStyle_border(XSSFWorkbook workbook, StringBuilder style, String type, short xlsBorder,
                                   XSSFColor borderColor) {
        if (xlsBorder == HSSFCellStyle.BORDER_NONE) {
            return;
        }

        StringBuilder borderStyle = new StringBuilder();
        borderStyle.append(ExcelToHtmlUtils.getBorderWidth(xlsBorder));
        borderStyle.append(' ');
        borderStyle.append(ExcelToHtmlUtils.getBorderStyle(xlsBorder));

        if (borderColor != null && borderColor.getARGBHex()!=null) {
            borderStyle.append('#');
            borderStyle.append(borderColor.getARGBHex().substring(2));
        }

        style.append("border-" + type + ":" + borderStyle + ";");
    }

    void buildStyle_font(XSSFWorkbook workbook, StringBuilder style, XSSFFont font) {
        switch (font.getBoldweight()) {
            case HSSFFont.BOLDWEIGHT_BOLD:
                style.append("font-weight:bold;");
                break;
            case HSSFFont.BOLDWEIGHT_NORMAL:
                // by default, not not increase HTML size
                // style.append( "font-weight: normal; " );
                break;
        }

        final XSSFColor fontColor = font.getXSSFColor();
        if (fontColor != null) {
            style.append("color: " + fontColor.getARGBHex().substring(2) + "; ");
        }
        if (font.getFontHeightInPoints() != 0) {
            style.append("font-size:" + font.getFontHeightInPoints() + "pt;");
        }
        if (font.getItalic()) {
            style.append("font-style:italic;");
        }
    }

    @Override
    public Document getDocument() {
        return htmlDocumentFacade.getDocument();
    }

    protected String getStyleClassName(XSSFWorkbook workbook, XSSFCellStyle cellStyle) {
        final Short cellStyleKey = Short.valueOf(cellStyle.getIndex());

        String knownClass = excelStyleToClass.get(cellStyleKey);
        if (knownClass != null) {
            return knownClass;
        }
        String cssStyle = buildStyle(workbook, cellStyle);
        String cssClass = htmlDocumentFacade.getOrCreateCssClass(cssClassPrefixCell, cssStyle);
        excelStyleToClass.put(cellStyleKey, cssClass);
        return cssClass;
    }

    public boolean isUseDivsToSpan() {
        return useDivsToSpan;
    }

    protected boolean processCell(XSSFCell cell, Element tableCellElement, int normalWidthPx, int maxSpannedWidthPx,
                                  float normalHeightPt) {
        final XSSFCellStyle cellStyle = cell.getCellStyle();

        String value;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                // XXX: enrich
                value = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case HSSFCell.CELL_TYPE_STRING:
                        XSSFRichTextString str = cell.getRichStringCellValue();
                        if (str != null && str.length() > 0) {
                            value = (str.toString());
                        } else {
                            value = "";
                        }
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        double nValue = cell.getNumericCellValue();
                        short df = cellStyle.getDataFormat();
                        String dfs = cellStyle.getDataFormatString();
                        value = _formatter.formatRawCellContents(nValue, df, dfs);
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        value = ErrorEval.getText(cell.getErrorCellValue());
                        break;
                    default:
                        logger.warn("Unexpected cell cachedFormulaResultType (" + cell.getCachedFormulaResultType() + ")");
                        value = "";
                        break;
                }
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                value = _formatter.formatCellValue(cell);
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = ErrorEval.getText(cell.getErrorCellValue());
                break;
            default:
                logger.warn("Unexpected cell type (" + cell.getCellType() + ")");
                return true;
        }

        final boolean noText = StringUtils.isEmpty(value);
        final boolean wrapInDivs = !noText && isUseDivsToSpan() && !cellStyle.getWrapText();

        if (cellStyle.getIndex() != 0) {
            XSSFWorkbook workbook = cell.getRow().getSheet().getWorkbook();
            String mainCssClass = getStyleClassName(workbook, cellStyle);

            if (wrapInDivs) {
                tableCellElement.setAttribute("class", mainCssClass + " " + cssClassContainerCell);
            } else {
                tableCellElement.setAttribute("class", mainCssClass);
            }

            if (noText) {
                /*
                 * if cell style is defined (like borders, etc.) but cell text is empty, add
                 * "&nbsp;" to output, so browser won't collapse and ignore cell
                 */
                value = "\u00A0";
            }
        }

        if (isOutputLeadingSpacesAsNonBreaking() && value.startsWith(" ")) {
            StringBuilder builder = new StringBuilder();
            for (int c = 0; c < value.length(); c++) {
                if (value.charAt(c) != ' ') {
                    break;
                }
                builder.append('\u00a0');
            }

            if (value.length() != builder.length()) {
                builder.append(value.substring(builder.length()));
            }
            value = builder.toString();
        }

        Text text = htmlDocumentFacade.createText(value);

        if (wrapInDivs) {
            Element outerDiv = htmlDocumentFacade.createBlock();
            outerDiv.setAttribute("class", this.cssClassContainerDiv);

            Element innerDiv = htmlDocumentFacade.createBlock();
            StringBuilder innerDivStyle = new StringBuilder();
            innerDivStyle.append("position:absolute;min-width:");
            innerDivStyle.append(normalWidthPx);
            innerDivStyle.append("px;");
            if (maxSpannedWidthPx != Integer.MAX_VALUE) {
                innerDivStyle.append("max-width:");
                innerDivStyle.append(maxSpannedWidthPx);
                innerDivStyle.append("px;");
            }
            innerDivStyle.append("overflow:hidden;max-height:");
            innerDivStyle.append(normalHeightPt);
            innerDivStyle.append("px;white-space:nowrap;");
            ExcelToHtmlUtils.appendAlign(innerDivStyle, cellStyle.getAlignment());
            htmlDocumentFacade.addStyleClass(outerDiv, cssClassPrefixDiv, innerDivStyle.toString());

            innerDiv.appendChild(text);
            outerDiv.appendChild(innerDiv);
            tableCellElement.appendChild(outerDiv);
        } else {
            tableCellElement.appendChild(text);
        }

        return StringUtils.isEmpty(value) && (cellStyle.getIndex() == 0);
    }

    protected void processColumnHeaders(XSSFSheet sheet, int maxSheetColumns, Element table) {
        Element tableHeader = htmlDocumentFacade.createTableHeader();
        table.appendChild(tableHeader);

        Element tr = htmlDocumentFacade.createTableRow();

        if (isOutputRowNumbers()) {
            // empty row at left-top corner
            tr.appendChild(htmlDocumentFacade.createTableHeaderCell());
        }

        for (int c = 0; c < maxSheetColumns; c++) {
            if (!isOutputHiddenColumns() && sheet.isColumnHidden(c)) {
                continue;
            }
            Element th = htmlDocumentFacade.createTableHeaderCell();
            String text = getColumnName(c);
            th.appendChild(htmlDocumentFacade.createText(text));
            tr.appendChild(th);
        }
        tableHeader.appendChild(tr);
    }

    /**
     * Creates COLGROUP element with width specified for all columns. (Except first
     * if <tt>{@link #isOutputRowNumbers()}==true</tt>)
     */
    protected void processColumnWidths(XSSFSheet sheet, int maxSheetColumns, Element table) {
        // draw COLS after we know max column number
        Element columnGroup = htmlDocumentFacade.createTableColumnGroup();
        if (isOutputRowNumbers()) {
            columnGroup.appendChild(htmlDocumentFacade.createTableColumn());
        }
        for (int c = 0; c < maxSheetColumns; c++) {
            if (!isOutputHiddenColumns() && sheet.isColumnHidden(c)) {
                continue;
            }
            Element col = htmlDocumentFacade.createTableColumn();
            col.setAttribute("width", String.valueOf(getColumnWidth(sheet, c)));
            columnGroup.appendChild(col);
        }
        table.appendChild(columnGroup);
    }

    protected void processDocumentInformation(CoreProperties summaryInformation) {
        if (StringUtils.isNoneBlank(summaryInformation.getTitle())) {
            htmlDocumentFacade.setTitle(summaryInformation.getTitle());
        }
        if (StringUtils.isNoneBlank(summaryInformation.getCreator())) {
            htmlDocumentFacade.addAuthor(summaryInformation.getCreator());
        }
        if (StringUtils.isNoneBlank(summaryInformation.getKeywords())) {
            htmlDocumentFacade.addKeywords(summaryInformation.getKeywords());
        }
        if (StringUtils.isNoneBlank(summaryInformation.getDescription())) {
            htmlDocumentFacade.addDescription(summaryInformation.getDescription());
        }
    }

    private static int getColumnWidth(XSSFSheet sheet, int columnIndex) {
        return ExcelToHtmlUtils.getColumnWidthInPx(sheet.getColumnWidth(columnIndex));
    }

    protected boolean isTextEmpty(XSSFCell cell) {
        final String value;
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                // XXX: enrich
                value = cell.getRichStringCellValue().getString();
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case HSSFCell.CELL_TYPE_STRING:
                        XSSFRichTextString str = cell.getRichStringCellValue();
                        if (str == null || str.length() <= 0) {
                            return false;
                        }
                        value = str.toString();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        XSSFCellStyle style = cell.getCellStyle();
                        double nval = cell.getNumericCellValue();
                        short df = style.getDataFormat();
                        String dfs = style.getDataFormatString();
                        value = _formatter.formatRawCellContents(nval, df, dfs);
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        value = ErrorEval.getText(cell.getErrorCellValue());
                        break;
                    default:
                        value = "";
                        break;
                }
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                value = _formatter.formatCellValue(cell);
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = ErrorEval.getText(cell.getErrorCellValue());
                break;
            default:
                return true;
        }

        return StringUtils.isBlank(value);
    }

    /**
     * @return maximum 1-base index of column that were rendered, zero if none
     */
    protected int processRow(CellRangeAddress[][] mergedRanges, XSSFRow row, Element tableRowElement) {
        final XSSFSheet sheet = row.getSheet();
        final short maxColIx = row.getLastCellNum();
        if (maxColIx <= 0) {
            return 0;
        }
        final List<Element> emptyCells = new ArrayList<Element>(maxColIx);

        if (isOutputRowNumbers()) {
            Element tableRowNumberCellElement = htmlDocumentFacade.createTableHeaderCell();
            processRowNumber(row, tableRowNumberCellElement);
            emptyCells.add(tableRowNumberCellElement);
        }

        int maxRenderedColumn = 0;
        for (int colIx = 0; colIx < maxColIx; colIx++) {
            if (!isOutputHiddenColumns() && sheet.isColumnHidden(colIx)) {
                continue;
            }
            CellRangeAddress range = ExcelToHtmlUtils.getMergedRange(mergedRanges, row.getRowNum(), colIx);

            if (range != null && (range.getFirstColumn() != colIx || range.getFirstRow() != row.getRowNum())) {
                continue;
            }
            XSSFCell cell = row.getCell(colIx);

            int divWidthPx = 0;
            if (isUseDivsToSpan()) {
                divWidthPx = getColumnWidth(sheet, colIx);

                boolean hasBreaks = false;
                for (int nextColumnIndex = colIx + 1; nextColumnIndex < maxColIx; nextColumnIndex++) {
                    if (!isOutputHiddenColumns() && sheet.isColumnHidden(nextColumnIndex)) {
                        continue;
                    }
                    if (row.getCell(nextColumnIndex) != null && !isTextEmpty(row.getCell(nextColumnIndex))) {
                        hasBreaks = true;
                        break;
                    }

                    divWidthPx += getColumnWidth(sheet, nextColumnIndex);
                }

                if (!hasBreaks) {
                    divWidthPx = Integer.MAX_VALUE;
                }
            }

            Element tableCellElement = htmlDocumentFacade.createTableCell();

            if (range != null) {
                if (range.getFirstColumn() != range.getLastColumn()) {
                    tableCellElement.setAttribute("colspan", String.valueOf(range.getLastColumn() - range.getFirstColumn() + 1));
                }
                if (range.getFirstRow() != range.getLastRow()) {
                    tableCellElement.setAttribute("rowspan", String.valueOf(range.getLastRow() - range.getFirstRow() + 1));
                }
            }

            boolean emptyCell;
            if (cell != null) {
                emptyCell = processCell(cell, tableCellElement, getColumnWidth(sheet, colIx), divWidthPx,
                        row.getHeight() / 20f);
            } else {
                emptyCell = true;
            }

            if (emptyCell) {
                emptyCells.add(tableCellElement);
            } else {
                for (Element emptyCellElement : emptyCells) {
                    tableRowElement.appendChild(emptyCellElement);
                }
                emptyCells.clear();

                tableRowElement.appendChild(tableCellElement);
                maxRenderedColumn = colIx;
            }
        }

        return maxRenderedColumn + 1;
    }

    protected String getRowName(XSSFRow row) {
        return String.valueOf(row.getRowNum() + 1);
    }

    protected void processRowNumber(XSSFRow row, Element tableRowNumberCellElement) {
        tableRowNumberCellElement.setAttribute("class", "rownumber");
        Text text = htmlDocumentFacade.createText(getRowName(row));
        tableRowNumberCellElement.appendChild(text);
    }

    private static CellRangeAddress[][] buildMergedRangesMap(XSSFSheet sheet) {
        CellRangeAddress[][] mergedRanges = new CellRangeAddress[1][];
        for (final CellRangeAddress cellRangeAddress : sheet.getMergedRegions()) {
            final int requiredHeight = cellRangeAddress.getLastRow() + 1;
            if (mergedRanges.length < requiredHeight) {
                CellRangeAddress[][] newArray = new CellRangeAddress[requiredHeight][];
                System.arraycopy(mergedRanges, 0, newArray, 0, mergedRanges.length);
                mergedRanges = newArray;
            }

            for (int r = cellRangeAddress.getFirstRow(); r <= cellRangeAddress.getLastRow(); r++) {
                final int requiredWidth = cellRangeAddress.getLastColumn() + 1;

                CellRangeAddress[] rowMerged = mergedRanges[r];
                if (rowMerged == null) {
                    rowMerged = new CellRangeAddress[requiredWidth];
                    mergedRanges[r] = rowMerged;
                } else {
                    final int rowMergedLength = rowMerged.length;
                    if (rowMergedLength < requiredWidth) {
                        final CellRangeAddress[] newRow = new CellRangeAddress[requiredWidth];
                        System.arraycopy(rowMerged, 0, newRow, 0, rowMergedLength);

                        mergedRanges[r] = newRow;
                        rowMerged = newRow;
                    }
                }

                Arrays.fill(rowMerged, cellRangeAddress.getFirstColumn(), cellRangeAddress.getLastColumn() + 1,
                        cellRangeAddress);
            }
        }
        return mergedRanges;
    }

    protected void processSheet(XSSFSheet sheet) {
        processSheetHeader(htmlDocumentFacade.getBody(), sheet);

        final int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
        if (physicalNumberOfRows <= 0) {
            return;
        }
        Element table = htmlDocumentFacade.createTable();
        htmlDocumentFacade.addStyleClass(table, cssClassPrefixTable, "border-collapse:collapse;border-spacing:0;");

        Element tableBody = htmlDocumentFacade.createTableBody();

        final CellRangeAddress[][] mergedRanges = buildMergedRangesMap(sheet);

        final List<Element> emptyRowElements = new ArrayList<Element>(physicalNumberOfRows);
        int maxSheetColumns = 1;
        for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
            XSSFRow row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            if (!isOutputHiddenRows() && row.getZeroHeight()) {
                continue;
            }
            Element tableRowElement = htmlDocumentFacade.createTableRow();
            htmlDocumentFacade.addStyleClass(tableRowElement, cssClassPrefixRow,
                    "height:" + (row.getHeight() / 20f) + "pt;");

            int maxRowColumnNumber = processRow(mergedRanges, row, tableRowElement);

            if (maxRowColumnNumber == 0) {
                emptyRowElements.add(tableRowElement);
            } else {
                if (!emptyRowElements.isEmpty()) {
                    for (Element emptyRowElement : emptyRowElements) {
                        tableBody.appendChild(emptyRowElement);
                    }
                    emptyRowElements.clear();
                }

                tableBody.appendChild(tableRowElement);
            }
            maxSheetColumns = Math.max(maxSheetColumns, maxRowColumnNumber);
        }

        processColumnWidths(sheet, maxSheetColumns, table);

        if (isOutputColumnHeaders()) {
            processColumnHeaders(sheet, maxSheetColumns, table);
        }

        table.appendChild(tableBody);

        htmlDocumentFacade.getBody().appendChild(table);
    }

    protected void processSheetHeader(Element htmlBody, XSSFSheet sheet) {
        Element h2 = htmlDocumentFacade.createHeader2();
        h2.appendChild(htmlDocumentFacade.createText(sheet.getSheetName()));
        htmlBody.appendChild(h2);
    }

    public void processWorkbook(XSSFWorkbook workbook) {
        final CoreProperties summaryInformation = workbook.getProperties().getCoreProperties();
        if (summaryInformation != null) {
            processDocumentInformation(summaryInformation);
        }

        if (isUseDivsToSpan()) {
            // prepare CSS classes for later usage
            this.cssClassContainerCell = htmlDocumentFacade.getOrCreateCssClass(cssClassPrefixCell,
                    "padding:0;margin:0;align:left;vertical-align:top;");
            this.cssClassContainerDiv = htmlDocumentFacade.getOrCreateCssClass(cssClassPrefixDiv, "position:relative;");
        }

        for (int s = 0; s < workbook.getNumberOfSheets(); s++) {
            XSSFSheet sheet = workbook.getSheetAt(s);
            processSheet(sheet);
        }

        htmlDocumentFacade.addMeta("viewport","width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0");
        htmlDocumentFacade.updateStylesheet();
    }

    public void setCssClassPrefixCell(String cssClassPrefixCell) {
        this.cssClassPrefixCell = cssClassPrefixCell;
    }

    public void setCssClassPrefixDiv(String cssClassPrefixDiv) {
        this.cssClassPrefixDiv = cssClassPrefixDiv;
    }

    public void setCssClassPrefixRow(String cssClassPrefixRow) {
        this.cssClassPrefixRow = cssClassPrefixRow;
    }

    public void setCssClassPrefixTable(String cssClassPrefixTable) {
        this.cssClassPrefixTable = cssClassPrefixTable;
    }

    /**
     * Allows converter to wrap content into two additional DIVs with tricky styles,
     * so it will wrap across empty cells (like in Excel).
     * <p>
     * <b>Warning:</b> after enabling this mode do not serialize result HTML with
     * INDENT=YES option, because line breaks will make additional (unwanted)
     * changes
     */
    public void setUseDivsToSpan(boolean useDivsToSpan) {
        this.useDivsToSpan = useDivsToSpan;
    }

}
