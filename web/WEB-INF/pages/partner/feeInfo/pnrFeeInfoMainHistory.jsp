<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<% 
String listName = request.getAttribute("listName").toString();
List iList = (List)request.getAttribute(listName);
if(iList!=null&&iList.size()>0){%>
	<logic:iterate id="iObject" name="${listName}" type="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoLink">
	<div class="ui-widget-content ui-corner-top " style="margin-top: 15">
		<table class="history-item-table" width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td class="label">操作时间</td>
				<td class="content"><bean:write name="iObject"
					property="operateTime" scope="page" /></td>
				<td class="label">操作名称</td>
				<td class="content"><bean:write name="iObject"
					property="operateType" scope="page" /></td>
			</tr>
			<tr>
				<td class="label">操作人</td>
				<td class="content"><bean:write name="iObject"
					property="taskHandler" scope="page" /></td>
				<td class="label">意见</td>
				<td class="content"><bean:write name="iObject"
					property="myText" scope="page" /></td>
			</tr>
		</table>
	</div>
</logic:iterate>

<% }else{%>无历史信息<%} %>