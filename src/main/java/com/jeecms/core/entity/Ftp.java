package com.jeecms.core.entity;

import com.jeecms.common.upload.UploadUtils;
import com.jeecms.common.util.MyFileUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.*;
import java.net.SocketException;
import java.util.List;

/**
 * FTP配置类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 22:59
 */
@Entity
@Table(name = "jo_ftp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "beanCache")
public class Ftp implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String defaultEncoding = "UTF-8";
    private static final Logger log = LoggerFactory.getLogger(Ftp.class);

    @Id
    @Column(name = "ftp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "ftp_name")
    private String name;

    /**
     * IP
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 端口
     */
    @Column(name = "port")
    private Integer port;

    /**
     * 登录名
     */
    @Column(name = "username")
    private String username;

    /**
     * 登陆密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 编码
     */
    @Column(name = "encoding")
    private String encoding;

    /**
     * 超时时间
     */
    @Column(name = "timeout")
    private Integer timeout;

    /**
     * 路径
     */
    @Column(name = "ftp_path")
    private String path;

    /**
     * 访问URL
     */
    @Column(name = "url")
    private String url;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getIp() {
        return ip;
    }


    public void setIp(String ip) {
        this.ip = ip;
    }


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEncoding() {
        return encoding;
    }


    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


    public Integer getTimeout() {
        return timeout;
    }


    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject convertToJson() {
        JSONObject json = new JSONObject();
        if (getId() != null) {
            json.put("id", getId());
        } else {
            json.put("id", "");
        }
        if (StringUtils.isNotBlank(getName())) {
            json.put("name", getName());
        } else {
            json.put("name", "");
        }
        if (StringUtils.isNotBlank(getIp())) {
            json.put("ip", getIp());
        } else {
            json.put("ip", "");
        }
        if (getPort() != null) {
            json.put("port", getPort());
        } else {
            json.put("port", "");
        }
        if (getTimeout() != null) {
            json.put("timeout", getTimeout());
        } else {
            json.put("timeout", "");
        }
        if (StringUtils.isNotBlank(getUsername())) {
            json.put("username", getUsername());
        } else {
            json.put("username", "");
        }
        if (StringUtils.isNotBlank(getPassword())) {
            json.put("password", getPassword());
        } else {
            json.put("password", "");
        }
        if (StringUtils.isNotBlank(getPath())) {
            json.put("path", getPath());
        } else {
            json.put("path", "");
        }
        if (StringUtils.isNotBlank(getEncoding())) {
            json.put("encoding", getEncoding());
        } else {
            json.put("encoding", "");
        }
        if (StringUtils.isNotBlank(getUrl())) {
            json.put("url", getUrl());
        } else {
            json.put("url", "");
        }
        return json;
    }

    public String storeByExt(String path, String ext, InputStream in)
            throws IOException {
        String filename = UploadUtils.generateFilename(path, ext);
        store(filename, in);
        return filename;
    }

    public void storeByExt(String path, InputStream in) throws IOException {
        store(path, in);
    }

    public String storeByFilename(String filename, InputStream in)
            throws IOException {
        store(filename, in);
        return filename;
    }

    public File retrieve(String name, String fileName) throws IOException {
        String path = System.getProperty("java.io.tmpdir");
        File file = new File(path, fileName);
        file = UploadUtils.getUniqueFile(file);
        FTPClient ftp = getClient();
        OutputStream output = new FileOutputStream(file);
        ftp.retrieveFile(getPath() + name, output);
        output.close();
        ftp.logout();
        ftp.disconnect();
        return file;
    }

    public boolean deleteFile(String fileName) {
        boolean flag;
        try {
            FTPClient ftp = getClient();
            flag = ftp.deleteFile(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            flag = false;
            log.error("ftp delete error", e);
        }
        return flag;
    }

    public boolean restore(String name, File file) throws IOException {
        store(name, FileUtils.openInputStream(file));
        file.deleteOnExit();
        return true;
    }

    public int storeByFloder(String folder, String rootPath) {
        String fileAbsolutePath;
        String fileRelativePath;
        try {
            FTPClient ftp = getClient();
            if (ftp != null) {
                List<File> files = MyFileUtils.getFiles(new File(folder));
                for (File file : files) {
                    String filename = getPath() + file.getName();
                    String name = FilenameUtils.getName(filename);
                    String path = FilenameUtils.getFullPath(filename);
                    fileAbsolutePath = file.getAbsolutePath();
                    fileRelativePath = fileAbsolutePath.substring(rootPath.length() + 1, fileAbsolutePath.indexOf(name));
                    path += fileRelativePath.replace("\\", "/");
                    if (!ftp.changeWorkingDirectory(path)) {
                        String[] ps = StringUtils.split(path, '/');
                        String p = "/";
                        ftp.changeWorkingDirectory(p);
                        for (String s : ps) {
                            p += s + "/";
                            if (!ftp.changeWorkingDirectory(p)) {
                                ftp.makeDirectory(s);
                                ftp.changeWorkingDirectory(p);
                            }
                        }
                    }
                    //解决中文乱码问题
                    name = new String(name.getBytes(getEncoding()), "iso-8859-1");
                    if (!file.isFile()) {
                        ftp.makeDirectory(name);
                    } else {
                        InputStream in = new FileInputStream(file.getAbsolutePath());
                        ftp.storeFile(name, in);
                        in.close();
                    }
                }
                ftp.logout();
                ftp.disconnect();
            }
            return 0;
        } catch (SocketException e) {
            log.error("ftp store error", e);
            return 3;
        } catch (IOException e) {
            log.error("ftp store error", e);
            return 4;
        }
    }

    private int store(String remote, InputStream in) {
        try {
            FTPClient ftp = getClient();
            if (ftp != null) {
                String filename = getPath() + remote;
                String name = FilenameUtils.getName(filename);
                String path = FilenameUtils.getFullPath(filename);
                if (!ftp.changeWorkingDirectory(path)) {
                    String[] ps = StringUtils.split(path, '/');
                    String p = "/";
                    ftp.changeWorkingDirectory(p);
                    for (String s : ps) {
                        p += s + "/";
                        if (!ftp.changeWorkingDirectory(p)) {
                            ftp.makeDirectory(s);
                            ftp.changeWorkingDirectory(p);
                        }
                    }
                }
                ftp.storeFile(name, in);
                ftp.logout();
                ftp.disconnect();
            }
            in.close();
            return 0;
        } catch (SocketException e) {
            log.error("ftp store error", e);
            return 3;
        } catch (IOException e) {
            log.error("ftp store error", e);
            return 4;
        }
    }

    private FTPClient getClient() throws SocketException, IOException {
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(
                new PrintWriter(System.out)));
        ftp.setDefaultPort(getPort());
        ftp.connect(getIp());
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            log.warn("FTP server refused connection: {}", getIp());
            ftp.disconnect();
            return null;
        }
        if (!ftp.login(getUsername(), getPassword())) {
            log.warn("FTP server refused login: {}, user: {}", getIp(),
                    getUsername());
            ftp.logout();
            ftp.disconnect();
            return null;
        }
        ftp.setControlEncoding(getEncoding());
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }

    public void init() {
        if (getPort() == null) {
            setPort(21);
        }
        if (getTimeout() == null) {
            setTimeout(0);
        }
        if (StringUtils.isBlank(getEncoding())) {
            setEncoding(defaultEncoding);
        }
    }
}