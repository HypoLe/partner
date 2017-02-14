<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>

<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.FileUpload"%>
<%@page import="org.apache.commons.fileupload.FileUploadException"%>
<%@page import="org.apache.commons.fileupload.RequestContext"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletRequestContext"%>
<%@page import="com.boco.eoms.common.util.StaticMethod"%>
<%
	try {
		String handle = StaticMethod.null2String(request
				.getParameter("handle"));
		if ("".equals(handle)) {
			handle = StaticMethod.nullObject2String(request
					.getAttribute("handle"));
		}
		request.setAttribute("handle", handle);

		if ("".equals(handle)) {
			//out.println("<br/><font color='red' size='20'>未知操作，需要[handle]等参数！</font><br/>");
		}

		
			upload(request,response);
		
	} catch (Exception e) {
		out.println("<br/><font color='red' size='20'>出错！</font><br/>");
		e.printStackTrace(response.getWriter());
		out.println(e);
	}
%>


<%!

public void upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String replacePath = "";
	
		//设置request编码，主要是为了处理普通输入框中的中文问题
		request.setCharacterEncoding("gbk");
		//这里对request进行封装，RequestContext提供了对request多个访问方法
		RequestContext requestContext = new ServletRequestContext(request);
		//判断表单是否是Multipart类型的。这里可以直接对request进行判断，不过已经以前的用法了
		if (FileUpload.isMultipartContent(requestContext)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置文件的缓存路径
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			response.getWriter().println("<br/>"+System.getProperty("java.io.tmpdir"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置上传文件大小的上限，-1表示无上限 
			upload.setSizeMax(100 * 1024 * 1024);
			List items = new ArrayList();
			try {
				//上传文件，并解析出所有的表单字段，包括普通字段和文件字段
				items = upload.parseRequest(request);
			} catch (FileUploadException e1) {
				response.getWriter().println("<br/>文件上传发生错误" + e1.getMessage());
			}
			//下面对每个字段进行处理，分普通字段和文件字段
			Iterator it = items.iterator();
			while (it.hasNext()) {
				FileItem fileItem = (FileItem) it.next();
				//如果是普通字段
				if (fileItem.isFormField()) {
					System.out.println("<br/>"+fileItem.getFieldName()
							+ "   "
							+ fileItem.getName()
							+ "   "
							+ new String(fileItem.getString().getBytes(
									"iso8859-1"), "gbk"));
					response.getWriter().println("<br/>"+fileItem.getFieldName()
							+ "   "
							+ fileItem.getName()
							+ "   "
							+ new String(fileItem.getString().getBytes(
									"iso8859-1"), "gbk"));
					if("replacePath".equals(fileItem.getFieldName())) {
						replacePath = fileItem.getString();
						
					}
				} else {
					response.getWriter().println("<br/>"+fileItem.getFieldName() + "   "
							+ fileItem.getName() + "   "
							+ fileItem.isInMemory() + "    "
							+ fileItem.getContentType() + "   "
							+ fileItem.getSize());
					//保存文件，其实就是把缓存里的数据写到目标路径下
					if (fileItem.getName() != null && fileItem.getSize() != 0) {
						File newFile = new File(replacePath,fileItem.getName());
						if(newFile.exists()) {
							File newFileBak = new File(replacePath,fileItem.getName()+"_pnrbak");
							copyFile(newFile,newFileBak);
						}
						try {
							fileItem.write(newFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						response.getWriter().println("<br/>文件没有选择 或 文件内容为空");
					}
				}
			}
		}
	}

// 复制文件
public void copyFile(File sourceFile, File targetFile) throws IOException {
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try {
        // 新建文件输入流并对它进行缓冲
        inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

        // 新建文件输出流并对它进行缓冲
        outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

        // 缓冲数组
        byte[] b = new byte[1024 * 5];
        int len;
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }
        // 刷新此缓冲的输出流
        outBuff.flush();
    } finally {
        // 关闭流
        if (inBuff != null)
            inBuff.close();
        if (outBuff != null)
            outBuff.close();
    }
}


%>

<form action="patchFile.jsp" enctype="multipart/form-data" method="post">
	路径：<input type="text" name="replacePath" /><br />
	文件：<input type="file" name="uploadFile" /><br/>
	<input type="submit" value="提交" />
</form>