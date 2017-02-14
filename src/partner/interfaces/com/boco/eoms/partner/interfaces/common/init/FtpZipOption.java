package com.boco.eoms.partner.interfaces.common.init;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;


/**
 * 
 * @author huangxionghui
 *
 */
public class FtpZipOption {
	private static Log log = LogFactory.getLog(FtpZipOption.class);
	/**
	  * kyx 增加
    *  下载FTP服务器上指定目录下的指定文件
    * @param filePath：本地存储目录（包含绝对路径，比如：G://FileStorage/）
    * @param ftphost:FTP服务地址（URL），比如：10.46.1.236
    * @param ftpport：FTP服务器的端口号，默认值为21
    * @param ftpusername：登陆FTP服务器的用户名
    * @param ftppassword：登陆FTP服务器的密码
    * @param ftpdir：FTP服务器的文件存放路径。比如：TNMS/flow/
    * @param filename:下载指定的文件名
    * @return
    */
	public int downloadFtpFile(String filepath,String filename) {
		// 返回值，下载文件的长度
		int fileSize = 0;
		FTPClient ftpclient = new FTPClient();
		try {
			// 尝试连接FTP服务器，判断FTP服务器有无响应
			int reply;
			ftpclient.connect(RequestConfig.getftphost(), Integer.parseInt(RequestConfig.getftpport()));
			ftpclient.login(RequestConfig.getftpusername(), RequestConfig.getftppassword());
			reply = ftpclient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpclient.disconnect();
				log.info("FTP服务器无响应");
			}

			// 跳转到FTP指定的目录
			boolean state = ftpclient.changeWorkingDirectory(RequestConfig.getftpdir());
			if (!state) {
				log.info("changeWorkingDirectory false, ftpdir = " + RequestConfig.getftpdir());
				return 0;
			}
			ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpclient.setControlEncoding("GBK");
			log.info("***********************登陆FTP成功***********************");

			log.info("ftp dir = " + RequestConfig.getftpdir());
			log.info(ftpclient.printWorkingDirectory());
			FTPFile[] fs = null;
			try {
				fs = ftpclient.listFiles();
				boolean fileexit = false;
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].getName().equals(filename)) {
						log.info("FTP服务器指定目录下存在接口参数指定的文件");
						fileexit = true;
					}
				}
				if (!fileexit)
					return 0;
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			if (fs.length == 0) {
				log.info("服务器上没有文件");
			} else {
				// 下载成功标志位
				boolean flag = false;

				File dir = new File(filepath);
				log.info(dir.getAbsolutePath());
				if (!dir.exists()) {
					dir.mkdir();
				}
				File localFile = new File(filepath + File.separatorChar + filename);
				OutputStream is = null;
				try {
					is = new FileOutputStream(localFile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				try {
					flag = ftpclient.retrieveFile(filename, is);
					log.info("文件名：" + filename);
				} catch (IOException e) {
					e.printStackTrace();
				}
				is.close();

				if (flag) {
					log.info("下载文件" + filename + "成功");
				} else {
					log.info("下载失败");
				}
				fileSize = fs.length;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		log.info("***********************************");
		log.info("本次下载文件任务结束！");
		return fileSize;
	}
	
	/**
	 * 从ftp服务器下载zip文件
	 * 
	 * @param
	 * @throws Exception
	 */
	public static void downLoadZipFile(String fileName, String destFile)
			throws Exception {

		String str; // 输出信息字符串
		/**
		 * 和服务器建立连接
		 */
		FtpClient ftp = new FtpClient(RequestConfig.getftphost()); // 根据服务器ip建立连接
		str = ftp.getResponseString(); // 获得响应信息
		System.out.println("连接服务器:" + str);
		/**
		 * 登陆到Ftp服务器
		 */
		ftp.login(RequestConfig.getftpusername(), RequestConfig.getftppassword()); // 根据用户名和密码登录服务器
		str = ftp.getResponseString();
		System.out.println("登录:" + str);
		/**
		 * 打开并定位到服务器目录
		 */
		// ftp.cd("c\\test"); //打开服务器上的文件目录
		str = ftp.getResponseString();
		System.out.println("打开服务器目录:" + str);
		ftp.binary();// 转化为二进制的文件
		TelnetInputStream ftpIn = ftp.get(fileName);// 找到要读取的文件
		byte[] buf = new byte[204800];
		int bufsize = 0;
		String toLocalPath = destFile +"\\"+ fileName;
		FileOutputStream ftpOut = new FileOutputStream(toLocalPath);
		while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
			ftpOut.write(buf, 0, bufsize);
		}
		ftpOut.close();
		ftpIn.close();
		System.out.println("下载" + fileName + "完成!");

	}

	// 上传文件;并返回上传文件的信息
	private static String upLoadZipToServer(String filename) throws Exception {
		String str; // 输出信息字符串
		String timeStr = getNowTime();// 获得当前时间
		String recordStr = "上传时间:" + timeStr + "\r\n";// 信息记录字符串
		/**
		 * 和服务器建立连接
		 */
		FtpClient ftp = new FtpClient("192.168.39.189"); // 根据服务器ip建立连接
		str = ftp.getResponseString(); // 获得响应信息
		System.out.println(str);
		recordStr += "连接服务器:" + str + "\r\n";
		/**
		 * 登陆到Ftp服务器
		 */
		ftp.login("test", "test"); // 根据用户名和密码登录服务器
		str = ftp.getResponseString();
		System.out.println(str);
		recordStr += "登录:" + str + "\r\n";
		/**
		 * 打开并定位到test目录
		 */
		ftp.cd("uptest"); // 打开服务器上的test文件夹
		ftp.binary();// 转化为二进制的文件
		str = ftp.getResponseString();
		System.out.println(str);

		FileInputStream is = null;
		TelnetOutputStream os = null;
		try {
			// "upftpfile"用ftp上传后的新文件名
			os = ftp.put("uptest.zip");
			File file_in = new java.io.File(filename);
			if (file_in.length() == 0) {
				return "上传文件为空!";
			}
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return "上传文件成功!";
	}

	/**
	 * zip压缩功能,压缩sourceFile(文件夹目录)下所有文件，包括子目录
	 * 
	 * @param sourceFile,待压缩目录;
	 *            toFolerName,压缩完毕生成的目录
	 * @throws Exception
	 */
	public static void fileToZip(String sourceFile, String toFolerName)
			throws Exception {

		List fileList = getSubFiles(new File(sourceFile)); // 得到待压缩的文件夹的所有内容
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(
				toFolerName));

		ZipEntry ze = null;
		byte[] buf = new byte[1024];
		int readLen = 0;
		for (int i = 0; i < fileList.size(); i++) { // 遍历要压缩的所有子文件
			File file = (File) fileList.get(i);
			System.out.println("压缩到的文件名:" + file.getName());
			ze = new ZipEntry(getAbsFileName(sourceFile, file));
			ze.setSize(file.length());
			ze.setTime(file.lastModified());
			zos.putNextEntry(ze);
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				zos.write(buf, 0, readLen);
			}
			is.close();
		}
		zos.close();
		System.out.println("压缩完成!");
	}

	/**
	 * 解压zip文件
	 * 
	 * @param sourceFile,待解压的zip文件;
	 *            toFolder,解压后的存放路径
	 * @throws Exception
	 */
	public static void zipToFile(String sourceFile, String toFolder)
			throws Exception {

		String toDisk = toFolder;// 接收解压后的存放路径
		ZipFile zfile = new ZipFile(sourceFile);// 连接待解压文件
		System.out.println("要解压的文件是:" + zfile.getName());

		Enumeration zList = zfile.entries();// 得到zip包里的所有元素
		ZipEntry ze = null;
		byte[] buf = new byte[1024];

		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				System.out.println("打开zip文件里的文件夹:" + ze.getName()
						+ " skipped...");
				continue;
			}
			System.out.println("zip包里的文件: " + ze.getName() + "\t" + "大小为:"
					+ ze.getSize() + "KB");

			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(getRealFileName(toDisk, ze.getName())));
			InputStream inputStream = new BufferedInputStream(zfile
					.getInputStream(ze));
			int readLen = 0;
			while ((readLen = inputStream.read(buf, 0, 1024)) != -1) {
				outputStream.write(buf, 0, readLen);
			}
			inputStream.close();
			outputStream.close();
			System.out.println("已经解压出:" + ze.getName());
		}
		zfile.close();
	}

	/**
	 * 给定根目录，返回另一个文件名的相对路径，用于zip文件中的路径.
	 * 
	 * @param baseDir
	 *            java.lang.String 根目录
	 * @param realFileName
	 *            java.io.File 实际的文件名
	 * @return 相对文件名
	 */
	private static String getAbsFileName(String baseDir, File realFileName) {
		File real = realFileName;
		File base = new File(baseDir);
		String ret = real.getName();
		while (true) {
			real = real.getParentFile();
			if (real == null)
				break;
			if (real.equals(base))
				break;
			else
				ret = real.getName() + "/" + ret;
		}
		return ret;
	}

	/**
	 * 取得指定目录下的所有文件列表，包括子目录.
	 * 
	 * @param baseDir
	 *            File 指定的目录
	 * @return 包含java.io.File的List
	 */
	private static List<File> getSubFiles(File baseDir) {
		List<File> ret = new ArrayList<File>();
		File[] tmp = baseDir.listFiles();
		for (int i = 0; i < tmp.length; i++) {
			if (tmp[i].isFile())
				ret.add(tmp[i]);
			if (tmp[i].isDirectory())
				ret.addAll(getSubFiles(tmp[i]));
		}
		return ret;
	}

	/**
	 * 给定根目录，返回一个相对路径所对应的实际文件名.
	 * 
	 * @param zippath
	 *            指定根目录
	 * @param absFileName
	 *            相对路径名，来自于ZipEntry中的name
	 * @return java.io.File 实际的文件
	 */
	private static File getRealFileName(String zippath, String absFileName) {

		String[] dirs = absFileName.split("/", absFileName.length());
		File ret = new File(zippath);// 创建文件对象
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {// 检测文件是否存在
			ret.mkdirs();// 创建此抽象路径名指定的目录
		}
		ret = new File(ret, dirs[dirs.length - 1]);// 根据 ret 抽象路径名和 child
													// 路径名字符串创建一个新 File 实例
		return ret;
	}

	/**
	 * 取得ftp服务器上某个目录下的所有文件名
	 * 
	 * @param ftp,
	 *            FtpClient类实例; folderName,服务器的文件夹名
	 * @throws Exception
	 * @return list 某目录下文件名列表
	 */
	private static List getServerFileNameList(FtpClient ftp, String folderName)
			throws Exception {

		BufferedReader dr = new BufferedReader(new InputStreamReader(ftp
				.nameList(folderName)));
		List<String> list = new ArrayList<String>();
		String s;
		while ((s = dr.readLine()) != null) {
			list.add(s);
		}
		return list;
	}

	/***************************************************************************
	 * 得到已经下载的目录下的所有文件名的数组
	 * 
	 * @param localPath
	 *            本地的下载文件保存路径
	 * @return 该路径下所有文件名
	 **************************************************************************/
	private static String[] getLocalFileNameArray(String localPath) {
		File diskFile = new File(localPath);
		if (diskFile != null) {
			String[] fileNameList = diskFile.list();
			return fileNameList;
		} else {
			return null;
		}
	}

	/**
	 * 获得当前系统时间
	 */
	public static String getNowTime() {
		String timeStr;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date currentTime = new Date(System.currentTimeMillis());
		timeStr = format.format(currentTime);
		return timeStr;
	}

	public static String getWantFileName() throws Exception {
		/** 得到当前的系统精确时间* */
		Date currentTime = new Date(System.currentTimeMillis());
		/** 接下来得到系统当前的年月日* */
		DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		Date todayDate = new Date(System.currentTimeMillis());
		String todayStr = df1.format(todayDate);// 得到当前的年月日
		/** 接下来得到四个比较时间的String类型；分别在00点，06点，12点和18点* */
		String compareTimeStr1 = todayStr + "00";
		String compareTimeStr2 = todayStr + "06";
		String compareTimeStr3 = todayStr + "12";
		String compareTimeStr4 = todayStr + "18";
		/** 接下来得到四个比较时间的date类型* */
		DateFormat df2 = new SimpleDateFormat("yyyyMMddHH");
		Date compareTime1 = df2.parse(compareTimeStr1);
		Date compareTime2 = df2.parse(compareTimeStr2);
		Date compareTime3 = df2.parse(compareTimeStr3);
		Date compareTime4 = df2.parse(compareTimeStr4);
		/** 接下来由当前系统时间和四个参照时间进行比较,找出该下载的文件名* */
		if (currentTime.after(compareTime1) && currentTime.before(compareTime2)) {
			// 此时应该下载00点的文件,文件名为:compareTimeStr1
			System.out.println("此时要下载的文件名为:" + compareTimeStr1 + ".zip");
			return compareTimeStr1;
		} else if (currentTime.after(compareTime2)
				&& currentTime.before(compareTime3)) {
			// 此时应该下载06点的文件,文件名为:compareTimeStr2
			System.out.println("此时要下载的文件名为:" + compareTimeStr2 + ".zip");
			return compareTimeStr2;
		} else if (currentTime.after(compareTime3)
				&& currentTime.before(compareTime4)) {
			// 此时应该下载12点的文件,文件名为:compareTimeStr3
			System.out.println("此时要下载的文件名为:" + compareTimeStr3 + ".zip");
			return compareTimeStr3;
		} else if (currentTime.after(compareTime4)) {
			// 此时应该下载18点的文件,文件名为:compareTimeStr4
			System.out.println("此时要下载的文件名为:" + compareTimeStr4 + ".zip");
			return compareTimeStr4;
		} else {
			// nothing to do
			return null;
		}
	}

	/***************************************************************************
	 * 判断此时是否需要下载文件
	 * 
	 * @param wantFileName,此时该下载的文件名;
	 *            localFileNameArray ,本地已经有的文件名
	 * @return ture--需要下载; false--本地已经有了,不需要下载
	 **************************************************************************/
	public static boolean ifToDownLoadFile(String wantFileName,
			String[] localFileNameArray) {

		if (wantFileName == null && localFileNameArray == null) {// 当想要下载的文件名获得失败
			return false;
		} else if (wantFileName == null && localFileNameArray != null) {// 当想要下载的文件名获得失败
			return false;
		} else if (wantFileName != null && localFileNameArray == null) {// 当本地没有已下载的文件
			return true;
		} else if (wantFileName != null && localFileNameArray != null) {// 当要下载的文件在本地还没有
			if (localFileNameArray.length > 0) {
				for (int i = 0; i < localFileNameArray.length; i++) {
					if (localFileNameArray[i].equals(wantFileName + ".zip")) {
						return false;
					}
				}
				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
//	将文件上传到NMS系统的FTP服务器上
   public static String upload2Ftp(String localfile, String ftphost, String ftpport, String ftpusername, String ftppassword, String ftpdir) {
       log.info("ftphost:" + ftphost);
       log.info("ftpport:" + ftpport);
       log.info("ftpusername:" + ftpusername);
       log.info("ftppassword:" + ftppassword);
       log.info("ftpdir:" + ftpdir);
       String filename = "";
       try {
           FtpClient ftpClient = new FtpClient();
           //话务网FTP服务器地址及端口
           if (ftpport != null) {
               ftpClient.openServer(ftphost, Integer.valueOf(ftpport));
           } else {
               ftpClient.openServer(ftphost);
           }

           //话务网FTP服务器用户名及密码
           ftpClient.login(ftpusername, ftppassword);
           //采用二进制方式上传
           ftpClient.binary();
           //文件上传到此目录下
           if (ftpdir != null) {
               ftpClient.cd(ftpdir);
           }
           File file_in = new File(localfile);
           filename = file_in.getName();
           FileInputStream is = new FileInputStream(file_in);
           TelnetOutputStream os = ftpClient.put(filename);
           byte[] bytes = new byte[1024];
           int readLength;
           while ((readLength = is.read(bytes)) != -1) {
               os.write(bytes, 0, readLength);
           }
           is.close();
           os.close();
           log.info("文件成功上传到FTP服务器");
       } catch (IOException ex) {
           ex.printStackTrace();
       }
       return filename;
   }

	public static void main(String args[]) {
		try {
			FtpZipOption.downLoadZipFile("NMS20090521202405125.CHK",
					"C:\\FJ_EXCEL\\");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
