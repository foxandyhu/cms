package com.bfly.common.microsoft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.SlideShowFactory;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.w3c.dom.Element;

/**
 * PPT转html工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/11/4 22:08
 */
public class PPTToHtmlUtil {

    private static final String SUFFIX = ".ppt";
    private static final String IMG = "img";

    /**
     * PPT转HTMl 支持.ppt 和.pptx
     *
     * @param source
     * @throws Exception
     */
    public static void pptToHtml(String source, String targetDir) throws Exception {
        String dirName = FilenameUtils.getBaseName(source).concat("_").concat(FilenameUtils.getExtension(source));
        targetDir = targetDir.concat(File.separator).concat(dirName);
        FileUtils.forceMkdir(new File(targetDir));
        String htmlPath = targetDir.concat(File.separator).concat("//index.html");

        HtmlDocumentFacade facade = new HtmlDocumentFacade(new HTMLDocumentImpl());
        try (InputStream in = new FileInputStream(source); SlideShow<?, ?> ppt = SlideShowFactory.create(in)) {
            Dimension pgSize = ppt.getPageSize();
            List<? extends Slide<?, ?>> slides = ppt.getSlides();
            for (int i = 0; i < slides.size(); i++) {
                Slide<?, ?> slide = slides.get(i);
                if (source.toLowerCase().endsWith(SUFFIX)) {
                    List<List<HSLFTextParagraph>> graphs = ((HSLFSlide) slide).getTextParagraphs();
                    graphs.forEach(list -> {
                        list.forEach(textRuns -> {
                            textRuns.forEach(textRun -> {
                                textRun.setFontIndex(1);
                                textRun.setFontFamily("宋体");
                            });
                        });
                    });
                } else {
                    List<XSLFShape> shapes = ((XSLFSlide) slide).getShapes();
                    for (XSLFShape shape : shapes) {
                        if (shape instanceof XSLFTextShape) {
                            XSLFTextShape sh = (XSLFTextShape) shape;
                            List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                            for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                                List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                                for (XSLFTextRun xslfTextRun : textRuns) {
                                    xslfTextRun.setFontFamily("宋体");
                                }
                            }
                        }
                    }
                }
                BufferedImage image = new BufferedImage(pgSize.width, pgSize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = image.createGraphics();
                graphics.setPaint(Color.BLUE);
                graphics.fill(new Rectangle2D.Float(0, 0, pgSize.width, pgSize.height));
                slide.draw(graphics);
                String imgName = ((i + 1) + ".png");
                try (FileOutputStream out = new FileOutputStream(targetDir + File.separator + imgName)) {
                    ImageIOUtil.writeImage(image, "png", out);
                }

                Element img = facade.createImage(imgName);
                facade.addStyleClass(img, IMG, "width:100%;height:100%;vertical-align:text-bottom;");
                facade.getBody().appendChild(img);

                facade.getBody().appendChild(facade.createLineBreak());
                facade.getBody().appendChild(facade.createLineBreak());
            }
        }

        facade.addMeta("viewport", "width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0");
        facade.updateStylesheet();
        DOMSource domSource = new DOMSource(facade.getDocument());
        StreamResult streamResult = new StreamResult(new File(htmlPath));
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, Constants.ENCODEING_UTF8);
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
    }
}
