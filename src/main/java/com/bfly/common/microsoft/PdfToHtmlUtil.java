package com.bfly.common.microsoft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;

import com.bfly.core.Constants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;

/**
 * PDF转工具类
 *
 * @author andy_hulibo@163.com
 * @date 2019/11/4 22:09
 */
public class PdfToHtmlUtil {

    public static void pdfToHtml(String source, String targetDir) throws Exception {
        String dirName = FilenameUtils.getBaseName(source).concat("_").concat(FilenameUtils.getExtension(source));
        targetDir = targetDir.concat(File.separator).concat(dirName);
        FileUtils.forceMkdir(new File(targetDir));
        String htmlPath = targetDir.concat("//index.html");

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlPath)), Constants.ENCODEING_UTF8))) {
            // 加载PDF文档
            try (PDDocument document = PDDocument.load(new File(source))) {
                PDFDomTree pdfDomTree = new PDFDomTree();
                pdfDomTree.writeText(document, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
