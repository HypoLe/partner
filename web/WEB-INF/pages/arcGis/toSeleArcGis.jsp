<%@ page language="java" pageEncoding="UTF-8" %>
<%
String city = (String)request.getAttribute("city");
response.sendRedirect("http://localhost:8081/partnerGis/partnerGis-debug/index.html?area="+city);
%>