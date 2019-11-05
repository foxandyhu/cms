package com.bfly.common.microsoft;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.html.dom.HTMLDocumentImpl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.converter.HtmlDocumentFacade;
import org.fit.pdfdom.PDFDomTree;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * PDF转工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/11/4 22:09
 */
public class TxtToHtmlUtil {

    public static void txtToHtml(String source, String targetDir) throws Exception {
        String dirName = FilenameUtils.getBaseName(source).concat("_").concat(FilenameUtils.getExtension(source));
        targetDir = targetDir.concat(File.separator).concat(dirName);
        FileUtils.forceMkdir(new File(targetDir));
        String htmlPath = targetDir.concat("//index.html");

        HtmlDocumentFacade facade=new HtmlDocumentFacade(new HTMLDocumentImpl());
        String text = FileUtils.readFileToString(new File(source), Constants.ENCODEING_UTF8);
        Element txt = facade.createBlock();
        txt.setTextContent(text);
        facade.getBody().appendChild(txt);
        facade.addMeta("viewport","width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0");

        DOMSource domSource = new DOMSource(facade.getDocument());
        StreamResult streamResult = new StreamResult(new File(htmlPath));

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, Constants.ENCODEING_UTF8);
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
    }
}
