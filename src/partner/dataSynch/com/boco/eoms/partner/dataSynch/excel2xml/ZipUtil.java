package com.boco.eoms.partner.dataSynch.excel2xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	/**
	 * 压缩文件
	 * 
	 * @param srcfile
	 *            File[] 需要压缩的文件列表
	 * @param zipfile
	 *            File 压缩后的文件
	 */
	public static void zipFiles(File[] srcfile, File zipfile) {
		byte[] buf = new byte[1024];
		try {
			// Create the ZIP file
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipfile));
			// Compress the files
			for (int i = 0; i < srcfile.length; i++) {
				if(srcfile[i].isFile()) {
					FileInputStream in = new FileInputStream(srcfile[i]);
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(srcfile[i].getName()));
					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					// Complete the entry
					out.closeEntry();
					in.close();
				}
			}
			// Complete the ZIP file
			out.close();
//			System.out.println("压缩完成.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解压缩
	 * 
	 * @param zipfile
	 *            File 需要解压缩的文件
	 * @param descDir
	 *            String 解压后的目标目录
	 */
	public static void unZipFiles(File zipfile, File descDir) {
		try {
			// Open the ZIP file
			ZipFile zf = new ZipFile(zipfile);
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				// Get the entry name
				ZipEntry entry = ((ZipEntry) entries.nextElement());
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				// System.out.println(zipEntryName);
				OutputStream out = new FileOutputStream(new File(descDir,zipEntryName));
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				// Close the file and stream
				in.close();
				out.close();
//				System.out.println("解压缩完成.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[]sdf) {
//		File[] srcFile = new File("E:\\workspace_xa\\resource\\dataSynch").listFiles();
//		File zipFile = new File("E:\\workspace_xa\\resource\\dataSynch\\zipfile.zip");
//		zipFiles(srcFile,zipFile);
		
		
		File desDir = new File("E:\\workspace_xa\\resource\\dataSynch");
		File zipFile = new File("E:\\workspace_xa\\resource\\dataSynch\\zipfile.zip");
		unZipFiles(zipFile,desDir);
		
		
	}
}
