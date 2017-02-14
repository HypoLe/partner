<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<%if(request.getAttribute("err")==null||request.getAttribute("err").equals("")){
	out.println("恭喜你,数据已成功导入!");
}else {
	List list = (List)request.getAttribute("err");
	for(int i = 0; i<list.size();i++){
		out.println(list.get(i));
		out.println("<br>");
	}
} 
%>