package com.bfly.common.microsoft;

import org.apache.commons.io.FilenameUtils;

/**
 * 微软文档转换器
 *
 * @author andy_hulibo@163.com
 * @date 2019/11/4 22:19
 */
public class MicroSoftConvert {

    private final static String PPT = ".ppt";
    private final static String PPTX = ".pptx";
    private final static String PDF = ".pdf";
    private final static String DOC = ".doc";
    private final static String DOCX = ".docx";
    private final static String XLS = ".xls";
    private final static String XLSX = ".xlsx";
    private final static String TXT = ".txt";

    /**
     * 转换
     *
     * @param source    源文件
     * @param targetDir 目标文件夹
     * @author andy_hulibo@163.com
     * @date 2019/11/4 22:19
     */
    public static void convert(String source, String targetDir) throws Exception {
        String suffix = "." + FilenameUtils.getExtension(source).toLowerCase();
        switch (suffix) {
            case TXT:
                TxtToHtmlUtil.txtToHtml(source, targetDir);
                break;
            case PPTX:
            case PPT:
                PPTToHtmlUtil.pptToHtml(source, targetDir);
                break;
            case PDF:
                PdfToHtmlUtil.pdfToHtml(source, targetDir);
                break;
            case DOC:
            case DOCX:
                WordToHtmlUtil.wordToHtml(source, targetDir);
                break;
            case XLS:
                Excel2003Process.excelToHtml(source, targetDir);
                break;
            case XLSX:
                Excel2007Process.excelToHtml(source, targetDir);
                break;
            default:
                new RuntimeException("该类型文档不能转换为HTML");
        }
    }
}
