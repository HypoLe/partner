<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<%  //显示
	out.clear();
	out = pageContext.pushBody();
	java.io.DataOutput output = new java.io.DataOutputStream(response.getOutputStream());
	response.setContentType("application/pdf");
	byte[] bytes = (byte[])request.getAttribute("byteData");
	response.setContentLength(bytes.length);
	for( int i = 0;i < bytes.length;i++ )
	{
		output.writeByte( bytes[i] );
	}
%>
<%@ include file="/common/footer_eoms.jsp"%>