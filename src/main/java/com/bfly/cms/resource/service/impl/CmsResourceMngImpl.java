package com.bfly.cms.resource.service.impl;

import com.bfly.cms.resource.entity.CmsFile;
import com.bfly.cms.resource.service.CmsFileMng;
import com.bfly.cms.resource.entity.FileWrap;
import com.bfly.cms.resource.entity.FileWrap.FileComparator;
import com.bfly.cms.resource.service.CmsResourceMng;
import com.bfly.cms.siteconfig.entity.CmsSite;
import com.bfly.cms.siteconfig.entity.Ftp;
import com.bfly.cms.siteconfig.service.FtpMng;
import com.bfly.cms.staticpage.DistributionThread;
import com.bfly.common.util.Zipper.FileEntry;
import com.bfly.common.web.springmvc.RealPathResolver;
import com.bfly.core.web.util.CmsUtils;
import com.bfly.core.web.util.FrontUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static com.bfly.common.web.Constants.SPT;
import static com.bfly.common.web.Constants.UTF8;
import static com.bfly.core.web.util.FrontUtils.RES_EXP;

/**
 * @author andy_hulibo@163.com
 * @date 2018/11/26 12:48
 */
@Service
public class CmsResourceMngImpl implements CmsResourceMng {

    private static final Logger log = LoggerFactory.getLogger(CmsResourceMngImpl.class);

    @Override
    public List<FileWrap> listFile(String path, boolean dirAndEditable) {
        File parent = new File(realPathResolver.get(path));
        if (parent.exists()) {
            File[] files;
            if (dirAndEditable) {
                files = parent.listFiles(filter);
            } else {
                files = parent.listFiles();
            }
            Arrays.sort(files, new FileComparator());
            List<FileWrap> list = new ArrayList<>(files.length);
            for (File f : files) {
                list.add(new FileWrap(f, realPathResolver.get("")));
            }
            return list;
        } else {
            return new ArrayList<>(0);
        }
    }

    @Override
    public List<FileWrap> queryFiles(String path, Boolean valid) {
        File parent = new File(realPathResolver.get(path));
        if (parent.exists()) {
            File[] files = parent.listFiles();
            Arrays.sort(files, new FileComparator());
            List<FileWrap> list = new ArrayList<>(files.length);
            CmsFile file;
            for (File f : files) {
                file = fileMng.findByPath(f.getName());
                if (valid != null) {
                    if (file != null) {
                        if (valid.equals(file.isFileIsvalid())) {
                            list.add(new FileWrap(f, realPathResolver.get(""), null, valid));
                        }
                    } else {
                        if (valid.equals(false)) {
                            list.add(new FileWrap(f, realPathResolver.get(""), null, false));
                        }
                    }
                } else {
                    if (file != null) {
                        list.add(new FileWrap(f, realPathResolver.get(""), null, file.isFileIsvalid()));
                    } else {
                        list.add(new FileWrap(f, realPathResolver.get(""), null, false));
                    }
                }
            }
            return list;
        } else {
            return new ArrayList<>(0);
        }
    }

    @Override
    public boolean createDir(String path, String dirName) {
        File parent = new File(realPathResolver.get(path));
        parent.mkdirs();
        File dir = new File(parent, dirName);
        return dir.mkdir();
    }

    @Override
    public void saveFile(HttpServletRequest request, String path, MultipartFile file)
            throws IllegalStateException, IOException {
        File dest = new File(realPathResolver.get(path), file
                .getOriginalFilename());
        file.transferTo(dest);
        CmsSite site = CmsUtils.getSite(request);
        if (site.getResouceSync()) {
            distributeFile(site, dest, path + "/" + dest.getName());
        }
    }

    @Override
    public void createFile(HttpServletRequest request, String path, String filename, String data)
            throws IOException {
        File parent = new File(realPathResolver.get(path));
        parent.mkdirs();
        File file = new File(parent, filename);
        FileUtils.writeStringToFile(file, data, UTF8);
        CmsSite site = CmsUtils.getSite(request);
        if (site.getResouceSync()) {
            distributeFile(site, file, path + "/" + file.getName());
        }
    }

    @Override
    public String readFile(String name) throws IOException {
        File file = new File(realPathResolver.get(name));
        return FileUtils.readFileToString(file, UTF8);
    }

    @Override
    public void updateFile(String name, String data) throws IOException {
        File file = new File(realPathResolver.get(name));
        FileUtils.writeStringToFile(file, data, UTF8);
    }

    @Override
    public int delete(String[] names) {
        int count = 0;
        File f;
        for (String name : names) {
            f = new File(realPathResolver.get(name));
            if (FileUtils.deleteQuietly(f)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void rename(String origName, String destName) {
        File orig = new File(realPathResolver.get(origName));
        File dest = new File(realPathResolver.get(destName));
        orig.renameTo(dest);
    }

    @Override
    public void copyTplAndRes(CmsSite from, CmsSite to) throws IOException {
        String fromSol = from.getTplSolution();
        String toSol = to.getTplSolution();
        File tplFrom = new File(realPathResolver.get(from.getTplPath()),
                fromSol);
        File tplTo = new File(realPathResolver.get(to.getTplPath()), toSol);
        if (tplFrom != null && tplTo != null && tplFrom.exists() && !tplFrom.getPath().equals(tplTo.getPath())) {
            FileUtils.copyDirectory(tplFrom, tplTo);
            File resFrom = new File(realPathResolver.get(from.getResPath()),
                    fromSol);
            if (resFrom.exists()) {
                File resTo = new File(realPathResolver.get(to.getResPath()), toSol);
                FileUtils.copyDirectory(resFrom, resTo);
            }
        }
    }

    @Override
    public String[] getSolutions(String path) {
        File tpl = new File(realPathResolver.get(path));
        return tpl.list((dir, name) -> dir.isDirectory());
    }

    @Override
    public List<FileEntry> export(CmsSite site, String solution) {
        List<FileEntry> fileEntrys = new ArrayList<>();
        File tpl = new File(realPathResolver.get(site.getTplPath()), solution);
        fileEntrys.add(new FileEntry("", "", tpl));
        File res = new File(realPathResolver.get(site.getResPath()), solution);
        if (res.exists()) {
            for (File r : res.listFiles()) {
                fileEntrys.add(new FileEntry(FrontUtils.RES_EXP, r));
            }
        }
        return fileEntrys;
    }

    @Override
    public void imoport(File file, CmsSite site) throws IOException {
        String resRoot = site.getResPath();
        String tplRoot = site.getTplPath();
        // 用默认编码或UTF-8编码解压会乱码！windows7的原因吗？
        ZipFile zip = new ZipFile(file, "GBK");
        ZipEntry entry;
        String name;
        String filename;
        File outFile;
        File pfile;
        byte[] buf = new byte[1024];
        int len;
        InputStream is = null;
        OutputStream os = null;
        String solution = null;

        Enumeration<ZipEntry> en = zip.getEntries();
        while (en.hasMoreElements()) {
            name = en.nextElement().getName();
            if (!name.startsWith(RES_EXP)) {
                solution = name.substring(0, name.indexOf("/"));
                break;
            }
        }
        if (solution == null) {
            return;
        }
        en = zip.getEntries();
        while (en.hasMoreElements()) {
            entry = en.nextElement();
            if (!entry.isDirectory()) {
                name = entry.getName();
                log.debug("unzip file：{}", name);
                // 模板还是资源
                if (name.startsWith(RES_EXP)) {
                    filename = resRoot + "/" + solution
                            + name.substring(RES_EXP.length());
                } else {
                    filename = tplRoot + SPT + name;
                }
                log.debug("解压地址：{}", filename);
                outFile = new File(realPathResolver.get(filename));
                pfile = outFile.getParentFile();
                if (!pfile.exists()) {
                    pfile.mkdirs();
                }
                try {
                    is = zip.getInputStream(entry);
                    os = new FileOutputStream(outFile);
                    while ((len = is.read(buf)) != -1) {
                        os.write(buf, 0, len);
                    }
                } finally {
                    if (is != null) {
                        is.close();
                        is = null;
                    }
                    if (os != null) {
                        os.close();
                        os = null;
                    }
                }
            }
        }
    }

    private void distributeFile(CmsSite site, File f, String filename) throws FileNotFoundException {
        if (site.getSyncPageFtp() != null) {
            Ftp syncPageFtp = ftpMng.findById(site.getSyncPageFtp().getId());
            Thread thread = new Thread(new DistributionThread(syncPageFtp, filename, new FileInputStream(f)));
            thread.start();
        }
    }

    /**
     * 文件夹和可编辑文件则显示
     */
    private FileFilter filter = (file) -> file.isDirectory() || FileWrap.editableExt(FilenameUtils.getExtension(file.getName()));

    @Autowired
    private RealPathResolver realPathResolver;
    @Autowired
    private CmsFileMng fileMng;
    @Autowired
    private FtpMng ftpMng;
}
