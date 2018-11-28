package com.bfly.cms.resource.entity;

import com.bfly.common.image.ImageUtils;
import com.bfly.common.util.DateUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileFilter;
import java.sql.Timestamp;
import java.util.*;

import static com.bfly.common.web.Constants.SPT;

/**
 * 文件包装类
 * <p>
 * 用于前台显示文件信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 12:55
 */
public class FileWrap {

    /**
     * 可编辑的后缀名
     */
    public static final String[] EDITABLE_EXT = new String[]{"html", "htm", "css", "js", "txt"};
    private File file;
    private String rootPath;
    private FileFilter filter;
    private List<FileWrap> child;
    private String filename;

    private Boolean valid;

    public JSONObject convertToJson()
            throws JSONException {
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getPath())) {
            json.put("path", getPath());
        } else {
            json.put("path", "");
        }
        if (StringUtils.isNotBlank(getFilename())) {
            json.put("filename", getFilename());
        } else {
            json.put("filename", "");
        }
        json.put("edit", isEditable());
        if (StringUtils.isNotBlank(getExtension())) {
            json.put("ext", getExtension());
        } else {
            json.put("ext", "");
        }
        if (getLastModifiedDate() != null) {
            json.put("lastModifiedDate", DateUtils.parseDateToTimeStr(getLastModifiedDate()));
        } else {
            json.put("lastModifiedDate", "");
        }
        json.put("size", getSize());
        json.put("isDirectory", isDirectory());
        json.put("isImage", isImage());
        json.put("isFile", isFile());
        if (getValid() != null) {
            json.put("valid", getValid());
        } else {
            json.put("valid", "");
        }
        return json;
    }

    public JSONObject convertToTreeJson(FileWrap ob)
            throws JSONException {
        JSONObject json = new JSONObject();
        json.put("name", ob.getFilename());
        json.put("path", ob.getName());
        json.put("isEditable", ob.isEditable());
        List<FileWrap> childs = ob.getChild();
        if (childs.size() > 0) {
            JSONArray childJson = new JSONArray();
            int j = 0;
            for (FileWrap wrap:childs) {
                if (wrap.isDirectory() || wrap.isEditable()) {
                    JSONObject obj = convertToTreeJson(wrap);
                    childJson.put(j, obj);
                } else {
                    j--;
                }
                j++;
            }
            json.put("child", childJson);
        } else {
            json.put("child", "");
        }
        return json;
    }


    /**
     * 构造器
     *
     * @param file     待包装的文件
     * @param rootPath 根路径，用于计算相对路径
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public FileWrap(File file, String rootPath) {
        this(file, rootPath, null);
    }

    /**
     * 构造器
     *
     * @param file     待包装的文件
     * @param rootPath 根路径，用于计算相对路径
     * @param filter   文件过滤器，应用于所有子文件
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public FileWrap(File file, String rootPath, FileFilter filter) {
        this.file = file;
        this.rootPath = rootPath;
        this.filter = filter;
    }

    /**
     * 构造器
     *
     * @param file     待包装的文件
     * @param rootPath 根路径，用于计算相对路径
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public FileWrap(File file, String rootPath, FileFilter filter, Boolean valid) {
        this.file = file;
        this.rootPath = rootPath;
        this.filter = filter;
        this.valid = valid;
    }

    /**
     * 是否允许编辑
     *
     * @param ext 文件扩展名
     * @return 是否允许编辑
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public static boolean editableExt(String ext) {
        ext = ext.toLowerCase(Locale.ENGLISH);
        for (String s : EDITABLE_EXT) {
            if (s.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得完整文件名，根据根路径(rootPath)。
     *
     * @return 文件名
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public String getName() {
        String path = file.getAbsolutePath();
        String relPath = path.substring(rootPath.length());
        //tomcat8 rootPath会加上/结尾
        if (!relPath.startsWith(File.separator)) {
            relPath = File.separator + relPath;
        }
        return relPath.replace(File.separator, SPT);
    }

    /**
     * 获得文件路径，不包含文件名的路径。
     *
     * @return 路径
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public String getPath() {
        String name = getName();
        return name.substring(0, name.lastIndexOf('/'));
    }

    /**
     * 获得文件名，不包含路径的文件名。
     * <p>
     * 如没有指定名称，则返回文件自身的名称。
     *
     * @return 文件名
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:56
     */
    public String getFilename() {
        return filename != null ? filename : file.getName();
    }

    /**
     * @return 扩展名
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 扩展名
     */
    public String getExtension() {
        return FilenameUtils.getExtension(file.getName());
    }

    /**
     * @return 时间戳
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 最后修改时间。长整型(long)。
     */
    public long getLastModified() {
        return file.lastModified();
    }

    /**
     * @return 日期
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 最后修改时间。日期型(Timestamp)。
     */
    public Date getLastModifiedDate() {
        return new Timestamp(file.lastModified());
    }

    /**
     * @return 大小
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 文件大小，单位KB。
     */
    public long getSize() {
        return file.length() / 1024 + 1;
    }


    /**
     * @return 文件集合
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 获得下级目录
     * <p>
     * 如果没有指定下级目录，则返回文件夹自身的下级文件，并运用FileFilter。
     */
    public List<FileWrap> getChild() {
        if (this.child != null) {
            return this.child;
        }
        File[] files;
        if (filter == null) {
            files = getFile().listFiles();
        } else {
            files = getFile().listFiles(filter);
        }
        List<FileWrap> list = new ArrayList<>();
        if (files != null) {
            Arrays.sort(files, new FileComparator());
            for (File f : files) {
                FileWrap fw = new FileWrap(f, rootPath, filter);
                list.add(fw);
            }
        }
        return list;
    }

    /**
     * @return 文件
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 获得被包装的文件
     */
    public File getFile() {
        return this.file;
    }

    /**
     * @return 是否是图片
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 是否图片
     */
    public boolean isImage() {
        if (isDirectory()) {
            return false;
        }
        String ext = getExtension();
        return ImageUtils.isValidImageExt(ext);
    }

    /**
     * @return 是否可编辑
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 是否可编辑
     */
    public boolean isEditable() {
        if (isDirectory()) {
            return false;
        }
        String ext = getExtension().toLowerCase();
        for (String s : EDITABLE_EXT) {
            if (s.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return true目录反之
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:57
     * 是否目录
     */
    public boolean isDirectory() {
        return file.isDirectory();
    }

    /**
     * @return true文件 反之
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:58
     * 是否文件
     */
    public boolean isFile() {
        return file.isFile();
    }

    /**
     * @param file 文件
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:58
     * 设置待包装的文件
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @param filename 文件名
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:58
     * 指定名称
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @param child 子项
     * @author andy_hulibo@163.com
     * @date 2018/11/26 12:58
     * 指定下级目录
     */
    public void setChild(List<FileWrap> child) {
        this.child = child;
    }


    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }


    /**
     * 文件比较器，文件夹靠前排。
     */
    public static class FileComparator implements Comparator<File> {
        @Override
        public int compare(File o1, File o2) {
            if (o1.isDirectory() && !o2.isDirectory()) {
                return -1;
            } else if (!o1.isDirectory() && o2.isDirectory()) {
                return 1;
            } else {
                return o1.compareTo(o2);
            }
        }
    }
}
