package com.boco.eoms.partner.dataSynch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


/** 
 * Description: Zip文件解压工工具类
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   Feb 23, 2012 11:32:27 AM
 */
public class ZipFileUnpackUtils {

	public static void main(String args[]) throws IOException {
		zipFileUnpack("E:\\workplace_fjsdh\\SDHXDCZ20120411152457437022.zip",false);
		
		InputStream[] isArray = getInputStreamsInZipFile("E:\\workplace_fjsdh\\SDHXDCZ20120411152457437022.zip");
		InputStream xmlIS = isArray[0];
		InputStream chkIS = isArray[1];
		
		FileOutputStream fos = new FileOutputStream("E:\\SDHXDCZ20120411152457437022.xml");
		
		int len = 0;
		while((len=xmlIS.read()) != -1) {
			fos.write(len);
		}
		
		fos.flush();
		fos.close();
		xmlIS.close();
		
	}
	
	/**
	 * 解压压缩文件到和当前文件名相同的文件夹里（只支持ZIP压缩文件）
	 * 该方法只支持压缩文件里是文件的压缩文件，不支持含有目录的压缩文件解压
	 * @param zipFilePath   压缩文件的全路径，包含文件名
	 * @param newDir		是否创建以当前压缩文件相同名的目录
	 * @throws IOException  
	 */
	public static void zipFileUnpack(String zipFilePath,boolean newDir) throws IOException {
		File file = new File(zipFilePath);//压缩文件
		
		//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		ZipFile zipFile = new ZipFile(file);
		
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			
			File temp = null;
			if(newDir) {
				String str = zipFilePath.substring(0, zipFilePath.indexOf("."));
				temp = new File(str + File.separator + fileName);
			} else {
				temp = new File(file.getParent()+File.separator + fileName);
			}
			
			if (! temp.getParentFile().exists())
				temp.getParentFile().mkdirs();
			OutputStream os = new FileOutputStream(temp);
			//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			InputStream is = zipFile.getInputStream(zipEntry);
			int len = 0;
			while ((len = is.read()) != -1)
				os.write(len);
			os.close();
			is.close();
		}
		zipInputStream.close();
	}
	
	/**
	 * 返加指定zip文件中对就的xml和chk文件的文件流
	 * 约定zip文件中只能有一个xml文件和一个chk文件，否则得到的不是预想要的
	 * @param zipFilePath
	 * @return InputStream[0] zip文件中对应xml文件的InputStream,
	 * 		   InputStream[1] zip文件中对应CHK文件的InputStream
	 * @throws IOException
	 */
	public static InputStream[] getInputStreamsInZipFile(String zipFilePath) throws IOException {
		InputStream[] isArray = new InputStream[]{null,null};
		
		File file = new File(zipFilePath);//压缩文件
		
		//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		ZipFile zipFile = new ZipFile(file);
		
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			
			if(fileName.toLowerCase().contains("xml")) {
				//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
				InputStream is = zipFile.getInputStream(zipEntry);
				isArray[0] = is;
			} else {
				//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
				InputStream is = zipFile.getInputStream(zipEntry);
				isArray[1] = is;
			}
		}
		zipInputStream.close();
		
		return isArray;
	}
	/**
	 * 返加指定zip文件中对就的xml文件的文件流
	 * 约定zip文件中只能有一个xml文件和一个chk文件，否则得到的不是预想要的
	 * @param zipFilePath
	 * @return zip文件中对应xml文件的InputStream,
	 * @throws IOException
	 */
	public static InputStream getXmlFileInputStreamsInZipFile(String zipFilePath) throws IOException {
		InputStream is = null;
		
		File file = new File(zipFilePath);//压缩文件
		
		//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		ZipFile zipFile = new ZipFile(file);
		
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			
			if(fileName.toLowerCase().contains("xml")) {
				//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
				is = zipFile.getInputStream(zipEntry);
			}
		}
		zipInputStream.close();
		
		return is;
	}
	/**
	 * 返加指定zip文件中对就的chk文件的文件流
	 * 约定zip文件中只能有一个xml文件和一个chk文件，否则得到的不是预想要的
	 * @param zipFilePath
	 * @return zip文件中对应CHK文件的InputStream
	 * @throws IOException
	 */
	public static InputStream getChkFileInputStreamsInZipFile(String zipFilePath) throws IOException {
		InputStream is = null;
		
		File file = new File(zipFilePath);//压缩文件
		
		//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile
		ZipFile zipFile = new ZipFile(file);
		
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
		ZipEntry zipEntry = null;
		
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {
			String fileName = zipEntry.getName();
			
			if(fileName.toLowerCase().contains("chk")) {
				//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
			    is = zipFile.getInputStream(zipEntry);
			} 
		}
		zipInputStream.close();
		
		return is;
	}
	
}
