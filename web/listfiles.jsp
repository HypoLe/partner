<%@ page language="java" pageEncoding="UTF-8" %>
<% 
try {
	String dirpath = request.getParameter("dirpath");
	String path = request.getParameter("path");
	String webpath = request.getParameter("webpath");
	String handle = request.getParameter("handle");
	String filepath = request.getParameter("filepath");
	if(handle!=null && "listfiles".equals(handle)) {
		listFiles(request,response,dirpath,path,webpath);
	} else if(handle!=null && "download".equals(handle)) {
		download(response,filepath);
	} else {
		out.println("<br/><font color='red' size='20'>未知操作，需要[path,webpath,handle,filepath]等参数！</font><br/>");
	}
} catch(Exception e) {
	out.println("<br/><font color='red' size='20'>出错！</font><br/>");
	e.printStackTrace(response.getWriter());
	out.println(e);
}
%>


<%!
public void listFiles(HttpServletRequest request, HttpServletResponse response,String dirpath,String path,String webpath)
		throws Exception {
	String webrootPath = request.getRealPath("/");
	String tomcatHome = System.getenv("CATALINA_HOME");
	System.out.println(System.getenv());
	java.io.File dir = null;
	String basePath = "";
	if(dirpath!=null && !"".equals(dirpath)) {
		dir = new java.io.File(dirpath);
	}
	if(path!=null && !"".equals(path)) {
		dir = new java.io.File(tomcatHome,path);
	}
	if(webpath!=null && !"".equals(webpath)) {
		dir = new java.io.File(webrootPath).getParentFile();
		dir = new java.io.File(dir,webpath);
	}
	
	
	java.io.File[] files = dir.listFiles();
	
	response.getWriter().println("<a href='listfiles.jsp?handle=listfiles&dirpath="+dir.getAbsolutePath()+"'><font color='red'>.</font></a><br/>");
	if(null != dir.getParentFile()) {
		response.getWriter().println("<a href='listfiles.jsp?handle=listfiles&dirpath="+dir.getParentFile().getAbsolutePath()+"'><font color='red'>..</font></a><br/>");
	}
	for(java.io.File f : files) {
		if(f.isDirectory()) {
			response.getWriter().println("<a href='listfiles.jsp?handle=listfiles&dirpath="+f.getAbsolutePath()+"'><font color='red'>"+f.getName()+"</font></a><br/>");
		} else {
			response.getWriter().println("<a href='listfiles.jsp?handle=download&filepath="+f.getAbsolutePath()+"'>"+f.getName()+"</a><br/>");
		}
	}
}

public void download( HttpServletResponse response,String path) throws Exception{
	java.io.File file = new java.io.File(path);
	String fileName = file.getName();
	// 读到流中
	java.io.InputStream inStream = new java.io.FileInputStream(file);// 文件的存放路径
	// 设置输出的格式
	response.reset();
	response.setContentType("application/x-msdownload;charset=GBK");
	response.setCharacterEncoding("GB2312");
	response.setHeader("Content-Disposition", "attachment; filename="
			+ new String(fileName.getBytes("gbk"), "iso8859-1"));
	// 循环取出流中的数据
	byte[] b = new byte[1024];
	int len = 0;
	while ((len = inStream.read(b)) > 0)
		response.getOutputStream().write(b, 0, len);
	inStream.close();	
	response.getOutputStream().flush();
	response.getOutputStream().close();
}

%>