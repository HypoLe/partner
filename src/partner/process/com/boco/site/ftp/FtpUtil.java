package com.boco.site.ftp;

import java.io.*;
import java.util.StringTokenizer;

import com.boco.site.ftp.model.FtpConfModel;
import org.apache.log4j.Logger;

import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

/**
 * 用于ftp下载文件的公用类，功能为按照给定参数链接服务器，并下载文件。
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author menglg
 * @version 1.0
 */
public class FtpUtil {
	private static Logger logger = Logger.getLogger(FtpUtil.class);
	private FtpConfModel ftpConfModel;
    private FtpClient ftpClient;
    private String delimiter = System.getProperty("file.separator");

    public void setFtpConfModel(FtpConfModel ftpConfModel) {
        this.ftpConfModel = ftpConfModel;
    }

    /**
	 * 根据给定条件，获得ftp实例
	 * 
	 * @return FtpClient
	 */
	private FtpClient getFtp() {
		System.out.println("链接FTP");
		logger.info("链接FTP");
		FtpClient fc = new FtpClient();
		System.out.println(this.ftpConfModel.getIp());
		logger.info(this.ftpConfModel.getIp());
		if (!this.ftpConfModel.getIp().equals("")) {
			try {
				fc.openServer(this.ftpConfModel.getIp(),
						this.ftpConfModel.getPort());
				fc.login(this.ftpConfModel.getUserName(),
						this.ftpConfModel.getPassword());
			} catch (IOException e) {
				logger.error("连接ftp服务器失败", e);
				System.out.println("连接ftp服务器失败"+ e.getMessage());
			}
			System.out.println("连接ftp服务器成功。");
			logger.info("连接ftp服务器成功。");
		} else {
			System.out.println("对不起，无法链接ftp服务器,请咨询管理员。");
			logger.error("对不起，无法链接ftp服务器,请咨询管理员。");
		}
		return fc;
	}

	/**
	 * 调用的主要方法，通过这个方法从ftp服务器下载文件
	 */
	public boolean getFile(String localpath, String fileName, String path) {
		if (localpath.equals("")) {
			localpath = System.getProperty("user.dir");
		}
		System.out.println("调试信息");
		boolean back = false;
		FtpClient fc = new FtpClient();
		fc = this.getFtp();
		if (fc != null) {
			if (!fileName.equals("")) {
				try {
					if (path == null || path.equals("")) {
						logger.info(this.ftpConfModel.getUrl());
						fc.cd(this.ftpConfModel.getUrl());
					} else {
						logger.info(this.ftpConfModel.getUrl() +delimiter + path);
						fc.cd(this.ftpConfModel.getUrl() + delimiter + path);
					}
					fc.binary();
					FilterInputStream fis = fc.get(fileName); // 从FTP服务器获取文件的输入流
					File file_dir = new File(localpath);
					if (!file_dir.exists()) {
						file_dir.mkdirs();
					}
					File file_out = new File(localpath + delimiter + fileName);
					logger.info(file_out.getPath());
					FileOutputStream os = new FileOutputStream(file_out);
					byte[] bytes = new byte[1024];
					int c;
					while ((c = fis.read(bytes)) != -1) { // 将FTP服务器的文件写到本地
						os.write(bytes, 0, c);
					}
					fis.close();
					os.close();
					back = true;
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("获取文件失败，请检查相关参数是否正确。");
				}
			} else {
				System.out.println("无文件名称。");
			}
		} else {
			System.out.println("ftp服务器连接错误，请确认连接成功后下载文件。");
		}
		return back;
	}

    /**
     * 检查文件夹在当前目录下是否存在
     * @param dir 要判断的目录
     * @return  如果有返回true,否则返回false
     */
    private boolean isDirExist(String dir){
        try {
            ftpClient.cd(dir);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    /**
     * 在当前目录下创建文件夹
     * @param dir 要创建的目录
     * @return  如果成功返回true,否则返回false
     */
    private boolean createDir(String dir){
        try{
            ftpClient.ascii();
            StringTokenizer s = new StringTokenizer(dir, delimiter);
            s.countTokens();
            String pathName = ftpClient.pwd();
            while(s.hasMoreElements()){
                pathName = pathName + delimiter + s.nextElement();
                try {
                    ftpClient.sendServer("MKD " + pathName + "\r\n");
                } catch (Exception e) {
                    return false;
                }
                ftpClient.readServerResponse();
            }
            ftpClient.binary();
            return true;
        }catch (IOException e1){
            e1.printStackTrace();
            return false;
        }
    }
    /**
     * 真正用于上传的方法
     * @param fileName
     * @param newName
     * @param path
     * @throws Exception
     */
    private void upload(String fileName, String newName,String path) throws Exception{
        String savefilename = new String(fileName.getBytes("ISO-8859-1"), "GBK");
        File file_in = new File(savefilename);//打开本地待长传的文件
        if(!file_in.exists()){

            System.out.println("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
            throw new Exception("此文件或文件夹[" + file_in.getName() + "]有误或不存在!");
        }
        if(file_in.isDirectory()){
            if(!isDirExist(newName)){
                createDir(newName);
            }
            ftpClient.cd(newName);
            File sourceFile[] = file_in.listFiles();
            for(int i = 0; i < sourceFile.length; i++){
                if(!sourceFile[i].exists()){
                    continue;
                }
                if(sourceFile[i].isDirectory()){
                    this.upload(sourceFile[i].getPath(),sourceFile[i].getName(),path+"/"+newName);
                }else{
                    this.uploadFile(sourceFile[i].getPath(),sourceFile[i].getName());
                }
            }
        }else{
            uploadFile(file_in.getPath(),newName);
        }
        ftpClient.cd(path);
    }

    /**
     *  upload 上传文件
     *
     * @param filename 要上传的文件名
     * @param newname 上传后的新文件名
     * @return -1 文件不存在 >=0 成功上传，返回文件的大小
     * @throws Exception
     */
    public long uploadFile(String filename, String newname) throws Exception{
        long result = 0;
        TelnetOutputStream os = null;
        FileInputStream is = null;
        try {
            java.io.File file_in = new java.io.File(filename);
            if(!file_in.exists())
                return -1;
            os = ftpClient.put(newname);
            result = file_in.length();
            is = new FileInputStream(file_in);
            byte[] bytes = new byte[1024];
            int c;
            while((c = is.read(bytes)) != -1){
                os.write(bytes, 0, c);
            }
        }finally{
            if(is != null){
                is.close();
            }
            if(os != null){
                os.close();
            }
        }
        return result;
    }
    /**
     * 调用的主要方法，通过这个方法从ftp服务器下载文件
     * @param file 要上传的文件     * @param dir 上传的目录
     * @return 上传成功返回true，否则返回false
     */
    public boolean upFile(File file, String dir,String newName) throws Exception {
        ftpClient = this.getFtp();
        if(file==null){
            return false;
        }
        if(dir!=null){
            dir= this.ftpConfModel.getUrl()+ delimiter+dir;
        }else{
            dir= this.ftpConfModel.getUrl();
        }
            if(!this.isDirExist(dir)){
               if( !this.createDir(dir)){
                   return false;
               }
                this.isDirExist(dir);
            }
            if(!file.exists()){

                System.out.println("此文件或文件夹[" + file.getName() + "]有误或不存在!");
                throw new Exception("此文件或文件夹[" + file.getName() + "]有误或不存在!");
            }
            if(file.isDirectory()){
                upload(file.getPath(),newName,ftpClient.pwd());
            }else{
                uploadFile(file.getPath(),file.getName());
            }
            ftpClient.closeServer();
            return true;
    }
	/**
	 * 调用的主要方法，把FTP目录内的文件生成目录下载下来
	 */
	public boolean getFileDirectory(String localpath, String path) {
		if (localpath.equals("")) {
			localpath = System.getProperty("user.dir");
		}
		boolean back = false;
		logger.error(1);
		FtpClient fc = new FtpClient();
		logger.error(2);
		fc = this.getFtp();
		logger.error(3);
		if (fc != null) {
			try {
				if (path == null || path.equals("")) {
					 System.out.println(this.ftpConfModel.getUrl());
					logger.info(this.ftpConfModel.getUrl());
					fc.cd(this.ftpConfModel.getUrl());
				} else {
					 System.out.println(this.ftpConfModel.getUrl() + "/" + path);
					logger.info(this.ftpConfModel.getUrl() + "/" + path);
					fc.cd(this.ftpConfModel.getUrl() + delimiter + path);
				}
				fc.binary();
				FilterInputStream fis = fc.list();
				File file_dir = new File(localpath);
				if (!file_dir.exists()) {
					if (file_dir.mkdir()) {
						 System.out.println("成功创建目录:" + file_dir.getPath());
						logger.info("成功创建目录:" + file_dir.getPath());
					} else {
						System.out.println("创建目录" + file_dir.getPath() + "失败！");
						logger.error("创建目录" + file_dir.getPath() + "失败！");
						return false;
					}
				}
				File file_out = new File(localpath + delimiter
						+ "fileDirectory.log");
				logger.info(file_out.getPath());
				FileOutputStream os = new FileOutputStream(file_out);
				byte[] bytes = new byte[1024];
				int c;
				while ((c = fis.read(bytes)) != -1) {
					os.write(bytes, 0, c);
				}
				fis.close();
				os.close();
				back = true;
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("获取文件失败，请检查相关参数是否正确。");
				logger.error("获取文件失败，请检查相关参数是否正确。");
			}
		} else {
			System.out.println("ftp服务器连接错误，请确认连接成功后下载文件。");
			logger.error("ftp服务器连接错误，请确认连接成功后下载文件。");
		}
		return back;
	}
}
