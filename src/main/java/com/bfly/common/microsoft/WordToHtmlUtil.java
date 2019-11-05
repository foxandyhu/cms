package com.bfly.common.microsoft;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Document;

/**
 * Word文档转HTMl工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/11/4 22:08
 */
public class WordToHtmlUtil {
    private static final String SUFFIX = ".doc";

    /**
     * Word转HTML 支持.doc .docx
     */
    public static void wordToHtml(String source, String targetDir) throws Exception {
        String dirName = FilenameUtils.getBaseName(source).concat("_").concat(FilenameUtils.getExtension(source));
        targetDir = targetDir.concat(File.separator).concat(dirName);
        FileUtils.forceMkdir(new File(targetDir));
        String htmlPath = targetDir.concat(File.separator).concat("//index.html");
        if (source.toLowerCase().endsWith(SUFFIX)) {
            word2003ToHtml(source, htmlPath, targetDir);
        } else {
            word2007ToHtml(source, htmlPath, targetDir);
        }
    }

    /**
     * Word2003转HTMl
     *
     * @param source
     * @throws Exception
     */
    private static void word2003ToHtml(String source, String htmlPath, String dirPath) throws Exception {
        HWPFDocument wordDocument;
        try (InputStream in = new FileInputStream(source)) {
            wordDocument = new HWPFDocument(in);
        }
        Document doc = DocumentBuilderFactoryImpl.newInstance().newDocumentBuilder().newDocument();
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(doc);
        wordToHtmlConverter.setPicturesManager((content, pictureType, suggestedName, widthInches, heightInches) -> {
            File file = new File(dirPath + File.separator + suggestedName);
            try {
                FileUtils.writeByteArrayToFile(file, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return suggestedName;
        });
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        try (OutputStream outStream = new FileOutputStream(new File(htmlPath))) {
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, Constants.ENCODEING_UTF8);
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
        }
    }

    /**
     * Word2007转HTMl
     *
     * @param source
     * @throws Exception
     */
    private static void word2007ToHtml(String source, String htmlPath, String dirPath) throws Exception {
        File wordFile = new File(source);
        XWPFDocument document;
        try(InputStream in = new FileInputStream(wordFile)) {
            document = new XWPFDocument(in);
        }
        File imgFolder = new File(dirPath);
        XHTMLOptions options = XHTMLOptions.create();
        options.setExtractor(new FileImageExtractor(imgFolder));
        options.URIResolver(new FileURIResolver(imgFolder));
        options.setIgnoreStylesIfUnused(true);
        options.setFragment(true);

        try (OutputStream out = new FileOutputStream(new File(htmlPath))) {
            XHTMLConverter.getInstance().convert(document, out, options);
        }
    }
}
