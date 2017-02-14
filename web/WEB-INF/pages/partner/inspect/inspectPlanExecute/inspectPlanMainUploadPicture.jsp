
<%@page import="java.util.Date"%><%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.boco.eoms.base.util.StaticMethod"%>
<%
 request.setAttribute("currentDate",StaticMethod.getCurrentDateTime());
%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
function upload() {
	
	var importFile = jq("#importFile").val();
	
	if(importFile == '' || importFile == null){
		alert("请选择图片");
		return;
	}
	
	new Ext.form.BasicForm('uploadForm').submit({
	method : 'post',
		url : "${app }/partner/inspect/inspectPlanExecute.do?method=inspectPlanMainUploadPicture",
	  	waitTitle : '请稍后...',
		waitMsg : '正在上传...',
	    success : function(form, action) {
			window.dialogArguments.updatePictureNum('${id}',action.result.pictureNum);
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
			
		}
    });
 }
 
 function windowClose(){
 	 window.close();
 }
</script>

	 <html:form action="inspectPlanExecute.do?method=inspectPlanMainUploadPicture"
		method="post" styleId="uploadForm"
		enctype="multipart/form-data" onsubmit="return checkForm()">
		<table border=0 cellspacing="1" cellpadding="1" class="listTable">
			 
			<tr>
			<td class="label">
				选择图片
			</td>
			<td class="content">
				<!--  <eoms:attachment  property="picture_id" scope="request" idField="picture_id" appCode="workplan"  viewFlag="N"/> -->
				<input id="importFile" type="file" name="importFile" class="file" alt="allowBlank:false"  />
			</td>
		</tr>
		<tr>
			<td  colspan="2" align="center">
				<input type="button" value="上传" onclick="upload()" class="btn">
				<input type="hidden" value="${id}" name="picture_id">
				<input type="hidden" value="${typeId}" name="idType">
			</td>
		</tr>
		</table>
		<input type="button" value="关闭" onclick="windowClose();" class="btn" />
	</html:form>
