<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String[] oilNum = (String[])request.getAttribute("oilNum");
String[] kind = (String[])request.getAttribute("kind");

%>
<center> 
<div style="width:85%">
<table class="formTable">

	<caption>
		<div class="header center"></div>
	</caption>
			<tr>
				<td class="label">油机性质</td>
				<td class="label">数量</td>
			</tr>
		
			<%
			for(int i=0;i<kind.length;i++){
			%>
			<tr>

			<td >
			
			<eoms:id2nameDB id="<%=kind[i] %>" beanId="IItawSystemDictTypeDao" />
				
			</td>
			
			<td >
				   <%=oilNum[i] %>
			</td>
			
			</tr>
			<%
			}
			%>
</table>
</div>
</center>

<%@ include file="/common/footer_eoms.jsp"%>
