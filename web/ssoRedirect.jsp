<%@ page language="java" pageEncoding="UTF-8"%>
<%

	String sesId = session.getId();
	String sAppId = request.getParameter("appid");
	String sTarget = request.getParameter("target");
	String sGoto = request.getParameter("goto");
	String sEnc=java.net.URLEncoder.encode(sTarget, "UTF-8");
	String sUrl = sGoto + "?sessionid=" + sesId + "&appid=" + sAppId + "&goto=" + sEnc;
	
	response.setHeader("P3P", "CP=\" OTI DSP COR IVA OUR IND COM \"");
	response.sendRedirect(sUrl);
%>