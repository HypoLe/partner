<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="net.fckeditor.FCKeditor"%>
<%
	FCKeditor fckEditor = new FCKeditor(request, "content");
	fckEditor.setBasePath(request.getContextPath() + "/fckeditor");
%>

<html:form action="/template.do?method=save" method="post" 
	styleId="workReportTemplateForm">

	<ul>

		<html:hidden property="id" />
		<html:hidden property="forumsId" />
		<html:hidden property="createrId" />
		<html:hidden property="createrName" />
		<html:hidden property="createTime" />
		<html:hidden property="threadCount" />
		<html:hidden property="isDel" />
		<html:hidden property="status" />
		<input type="hidden" name="forumsId" value="${forumsId}"/>
		
		<table class="formTable" width="75%">
			<tr>
				<td class="label">
					标题：
					<html:errors property="title" />
				</td>
				<td colspan="3" class="content">
					<html:text property="title" styleId="title"
						styleClass="text medium" alt="allowBlank:false" style="width:98%" />
				</td>
			</tr>
			<tr>
				<td class="label">
					内容：
					<html:errors property="content" />
				</td>
				<td colspan="3" class="content">
					<textarea class="textarea max" alt="allowBlank:false" name="content"></textarea>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					有效期：
					<html:errors property="threadTypeId" />
				</td>
				<td class="content">
					<input type="text" id="validityDate" name="validityDate"
						readonly="readonly" alt="allowBlank:false"
						onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" />
					<INPUT type="checkbox" id="noValidity" name="noValidity"
						onclick="javascript:xvalidity(this)" value="1" />
					是否永久不过期
				</td>

				<td class="label">
					发布范围：
					<html:errors property="threadTypeId" />
				</td>
				<td class="content">
					<eoms:chooser id="reportRange" type="user,dept" config="{returnJSON:true,showLeader:true}"
				category="[{id:'reportRangeOwner',text:'派发',childType:'user,dept',limit:'none',allowBlank:true,vtext:'请选择发布范围'}]" />
					<input type="checkbox" name="isSendtoIncludeSubDept"
						id="isSendtoIncludeSubDept" value="1" />包括子部门
<!-- 					<html:checkbox property="isSend" styleId="isSend" value="1">发送短信到所选范围</html:checkbox> -->
				</td>
			</tr>
			
			<tr>
				<td class="label">
					模板使用人：
				</td>
				<td class="content"> 
					<eoms:chooser id="templateUser" type="user,dept" config="{returnJSON:true,showLeader:true}"
					category="[{id:'templateOwner',text:'派发',childType:'user,dept',limit:'none',allowBlank:false,vtext:'请选择项目经理'}]" />
					<input type="checkbox" name="isPrivIncludeSubDept"
						id="isPrivIncludeSubDept" value="1" />包括子部门
				</td>
				<td class="label">
					项目经理：
				</td>
				<td class="content">
					<input type="text" value="${projectManager}" readonly="readonly" name="projectManager"/>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					附件管理
				</td>
				<td colspan="3" class="content">
					<eoms:attachment name="currentWorkReport" property="accessories" 
           			scope="request" idField="accessories" appCode="9" viewFlag="Y"/> 
						<eoms:attachment idField="accessories" appCode="9" scope="request" name="workReportTemplateForm"  />
				</td>
			</tr>
			<tr>
				<td class="buttonBar bottom" colspan="3">
					<input type="submit" value="保存模板" />
				</td>
			</tr>
		</table>
	</ul>

</html:form>

<script type="text/javascript">

	function FCKeditor_OnComplete(editorInstance) {
		window.status = editorInstance.Description;
	}
	
	Ext.onReady(function(){
		v = new eoms.form.Validation({form:'workReportTemplateForm'});
	});

function IsInteger(string ,sign) {  
    var integer; 
    if ((sign!=null) && (sign!='-') && (sign!='+')) { 
        alert('IsInter(string,sign)的参数出错： sign为null或"-"或"+"'); 
        return false; 
    } 
    integer = parseInt(string); 
    if (isNaN(integer)) { 
        return false; 
    } else if (integer.toString().length==string.length) {  
        if ((sign==null) || (sign=='-' && integer<0) || (sign=='+' && integer>0)) { 
            return true; 
        } else 
            return false;  
    } else 
        return false; 
} 

function xvalidity(checkbox){
	document.getElementById("validityDate").disabled=checkbox.checked;
}
	
</script>

<%@ include file="/common/footer_eoms.jsp"%>
