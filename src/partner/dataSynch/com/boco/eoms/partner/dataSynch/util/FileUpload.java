package com.boco.eoms.partner.dataSynch.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload {
	/**
	 * 上传文件 
	 * @param request
	 * @return true 上传成功 false上传失败
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> upload(HttpServletRequest request,File uploadDir){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", "true");
		boolean flag = true;
		//检查输入请求是否为multipart表单数据。
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		//若果是的话
		if(isMultipart){
			/**为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。**/
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
				factory.setRepository(new File(System.getProperty("java.io.tmpdir")));// 设置缓冲区目录
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");//解决文件乱码问题
				upload.setSizeMax(1024*1024*100);// 设置最大文件尺寸
				List<FileItem> items = upload.parseRequest(request);
//				// 检查是否符合上传的类型
//				if(!checkFileType(items)) return false;
				Iterator<FileItem> itr = items.iterator();//所有的表单项
				//保存文件
				Map<String,String> requestParams = new HashMap<String,String>();
				while (itr.hasNext()){
					 FileItem item = (FileItem) itr.next();//循环获得每个表单项
					 if (!item.isFormField()){//如果是文件类型
						 String name = item.getName();//获得文件名 包括路径啊
						 if(name!=null){
							 File fullFile=new File(item.getName());
							 File savedFile=new File(uploadDir,fullFile.getName());
							 map.put("savePath", savedFile.getAbsolutePath());
							 item.write(savedFile);
						 }
					 } else {
						 requestParams.put(item.getFieldName(), item.getString());
					 }
				}
				map.put("params", requestParams);
			} catch (FileUploadException e) {
				map.put("success", "false");
				flag = false;
				e.printStackTrace();
			}catch (Exception e) {
				map.put("success", "false");
				flag = false;
				e.printStackTrace();
			}
		}else{
			map.put("success", "false");
			flag = false;
			System.out.println("the enctype must be multipart/form-data");
		}
		return map;
	}
	
	/**
	 * 删除一组文件
	 * @param filePath 文件路径数组
	 */
	public static void deleteFile(String [] filePath){
		String uploadPath = "";
		if(filePath!=null && filePath.length>0){
			for(String path:filePath){
				String realPath = uploadPath+path;
				File delfile = new File(realPath);
				if(delfile.exists()){
					delfile.delete();
				}
			}
			
		}
	}
	
	/**
	 * 删除单个文件
	 * @param filePath 单个文件路径
	 */
	public static void deleteFile(String filePath){
		if(filePath!=null && !"".equals(filePath)){
			String [] path = new String []{filePath};
			deleteFile(path);
		}
	}
	
//	/**
//	 * 判断上传文件类型
//	 * @param items
//	 * @return
//	 */
//	private static Boolean checkFileType(List<FileItem> items){
//		Iterator<FileItem> itr = items.iterator();//所有的表单项
//		while (itr.hasNext()){
//			 FileItem item = (FileItem) itr.next();//循环获得每个表单项
//			 if (!item.isFormField()){//如果是文件类型
//					 String name = item.getName();//获得文件名 包括路径啊
//					 if(name!=null){
//						 File fullFile=new File(item.getName());
//						 boolean isType = ReadUploadFileType.readUploadFileType(fullFile);
//						 if(!isType) return false;
//						 break;
//					 }
//			 }
//		}
//		
//		return true;
//	}
	
	public static void main(String[]sdaf) {
		File file_out = new File("E:\\aaa\\bbb\\c.txt");
		try {
			if(!file_out.getParentFile().exists()) {
				file_out.getParentFile().mkdirs();
			}
			FileOutputStream os = new FileOutputStream(file_out);
			os.write(new String("中国").getBytes());
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
	}
}