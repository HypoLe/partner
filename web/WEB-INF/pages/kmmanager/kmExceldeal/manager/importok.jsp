<%@ page language="java" pageEncoding="UTF-8" %>
<%if(request.getAttribute("err")==null||request.getAttribute("err").equals("")){
	out.println("OK!");
}else {  
	out.print(request.getAttribute("err"));
} 
%>