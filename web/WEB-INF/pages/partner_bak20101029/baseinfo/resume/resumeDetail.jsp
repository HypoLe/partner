<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'resumeForm'});
});

var tr = true;
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
	var url = "../baseinfo/resumes.do?method=validationResume&reId="+document.getElementById("id").value+"&idCardNo="+idCardNo;
	
	
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
	val();
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

	<tr>
		<td class="label">
			<fmt:message key="resume.personnelName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${resumeForm.personnelName}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.idCardNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${resumeForm.idCardNo}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<eoms:id2nameDB id="${resumeForm.provider}" beanId="partnerDeptDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.state" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-baseinfo" dictId="state" itemId="${resumeForm.state}" beanId="id2nameXML" />
	
				
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.commencementDate" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${resumeForm.commencementDate}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.dimissionDate" />
		</td>
		<td class="content">
			${resumeForm.dimissionDate}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.post" />
		</td>
		<td class="content">
			${resumeForm.post}
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="resume.remark" />
		</td>
		<td class="content">
			${resumeForm.remark}
		</td>
	</tr>


</table>

</fmt:bundle>


</html:form>

<%@ include file="/common/footer_eoms.jsp"%>