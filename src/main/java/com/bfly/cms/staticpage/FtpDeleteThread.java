package com.bfly.cms.staticpage;

import com.bfly.cms.siteconfig.entity.Ftp;

public class FtpDeleteThread implements Runnable {
	private String fileName;
	private Ftp ftp;
	public FtpDeleteThread(Ftp ftp,String fileName) {
		this.ftp = ftp;
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		if(ftp!=null){
			boolean flag;
			try {
				flag=ftp.deleteFile(fileName);
				System.out.println("flag - >"+flag);
			} catch (Exception e) {
			}
		}
	}

}
