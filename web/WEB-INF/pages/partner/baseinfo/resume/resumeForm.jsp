<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'resumeForm'});
});

var tr = false;
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}
function val()
{
	var obj = document.getElementById("idCardNo");
	var idCardNo = encodeURIComponent(document.getElementById("idCardNo").value);
	var url = "../baseinfo/resumes.do?method=validationResume&reId="+document.getElementById("reId").value+"&idCardNo="+idCardNo;
	
						Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									 if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									if(json[0].message == true){
					     				if(obj.value!=""){
					     					document.getElementById("message").innerHTML = "<font color='green'>身份证号可以使用</font>";
					     					tr = true;
					     				}else{
					     					obj.focus();
					     				}
					     			}else{
					     				document.getElementById("message").innerHTML = "<font color='red'>对不起,身份证号已存在</font>";
					     				tr = false;
					     			}	
											},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
}

function sub(){
	if(tr){
	}else{
		alert("同一身份证只能对应一个工作简历");
		return false;
	}
	if(v.check()){
       $("resumeForm").submit();
	}	
}
   window.onload = function(){
   val();
		var url = "${app}/partner/baseinfo/resumes.do?method=searchOne&personCardNo=${personCardNo}";
		//window.opener.location=url;
		var operType = '${operType}';
		if(operType == 'save'){
			window.opener.location=url;//刷新父窗口
			window.close(); //关闭自己			
		}
	}
</script>

<html:form action="/resumes.do?method=save" styleId="resumeForm" method="post"> 
<font color='red'>*</font>号为必填内容
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="resume.form.heading"/></div>
	</caption>
             <input type="hidden" name="id" id="id"  value="${resumeForm.id}" />
	<tr>
		<td class="label">
			<fmt:message key="resume.personnelName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<c:if test="${personnelName==null}">
			<html:text property="personnelName" styleId="personnelName"
						styleClass="text medium" readonly="true"
						alt="allowBlank:false,vtext:''" value="${resumeForm.personnelName}" />
		</c:if>
		<c:if test="${personnelName!=null}">
			<html:text property="personnelName" styleId="personnelName"
						styleClass="text medium" readonly="true"
						alt="allowBlank:false,vtext:''" value="${personnelName}" />
		</c:if>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="resume.idCardNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<c:if test="${idCardNo==null}">
			<c:if test="${resumeForm.id==null}">
			<html:text property="idCardNo" styleId="idCardNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${resumeForm.idCardNo}"/>
			</c:if>
			<c:if test="${resumeForm.id!=null}">
			<html:text property="idCardNo" styleId="idCardNo"
						styleClass="text medium" readonly="true"
						alt="allowBlank:false,vtext:''" value="${resumeForm.idCardNo}"  onblur="val();"/>
			</c:if>
		</c:if>
		<c:if test="${idCardNo!=null}">
			<html:text property="idCardNo" styleId="idCardNo"
						styleClass="text medium" readonly="true"
						alt="allowBlank:false,vtext:''" value="${idCardNo}"  onblur="val();"/>
		</c:if>
		<span id="message"></span>
		</td>
	</tr>

	<tr>
		<td class="label">
			所在公司&nbsp;<font color='red'>*</font>
		</td>
		<td class="content"><%--
			<input id="provider" name="provider" alt="allowBlank:false" type="text" class="text"
			value="${resumeForm.provider}"/>
		--%>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.state" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				<eoms:comboBox name="postState" id="postState" defaultValue="${postState}" initDicId="124009" styleClass="input select">
				</eoms:comboBox>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.commencementDate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="text" readonly="true" class="text" 
                name="commencementDate" id="commencementDate"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt=" vtype:'lessThen',link:'dimissionDate',allowBlank:false,vtext:'入职时间时间要大于离职时间'"
                value="${resumeForm.commencementDate}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.dimissionDate" />
		</td>
		<td class="content">
			<input type="text" readonly="true" class="text" 
              name="dimissionDate" id="dimissionDate"
              onclick="popUpCalendar(this,this,null,null,null,true,-1);"
              alt=" vtype:'moreThen',link:'commencementDate'vtext:'入职时间时间要大于离职时间'"
              value="${resumeForm.dimissionDate}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.post" />
		</td>
		<td class="content">
			<html:text property="post" styleId="post"
						styleClass="text medium"
						alt="vtext:''" value="${resumeForm.post}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="resume.remark" />
		</td>
		<td class="content">
			<html:textarea property="remark" styleId="remark"
				styleClass="textarea max" 
				alt="maxLength:500" value="${resumeForm.remark}" rows="3"/>
		</td>
	</tr>
<c:if test="${resumeForm.id!=null}">
<input type="hidden" name="reId" id="reId" value="${resumeForm.id}" />
</c:if>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick=" sub();"/>
			<input type="button" class="btn" value="关闭" onclick="javascript:window.close();" />
		</td>
	</tr>
</table>
<html:hidden property="proId" value="${proId}" />
<html:hidden property="addUser" value="${resumeForm.addUser}" />
<html:hidden property="addTime" value="${resumeForm.addTime}" />
<html:hidden property="reId" value="${resumeForm.id}" />
</html:form>
<script type="text/javascript">
document.getElementById("provider").value = '${resumeForm.provider}';
</script>
<%@ include file="/common/footer_eoms.jsp"%>