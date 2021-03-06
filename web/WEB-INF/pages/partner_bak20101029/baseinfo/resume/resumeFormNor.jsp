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
	
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,false);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		var json = eval(httpRequest.responseText);
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
				}	
	     }
	     httpRequest.send(null);
	}
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

<html:form action="/resumes.do?method=saveNor" styleId="resumeForm" method="post"> 
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
		<c:if test="${resumeForm.personnelName==null}">
			<html:text property="personnelName" styleId="personnelName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${resumeForm.personnelName}" />
		</c:if>
		<c:if test="${resumeForm.personnelName!=null}">
			<html:text property="personnelName" styleId="personnelName"
						styleClass="text medium" readonly="true"
						alt="allowBlank:false,vtext:''" value="${resumeForm.personnelName}" />
		</c:if>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.idCardNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		<c:if test="${resumeForm.idCardNo==null}">
			<html:text property="idCardNo" styleId="idCardNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${resumeForm.idCardNo}"  onblur="val();"/>
		</c:if>
		<c:if test="${resumeForm.idCardNo!=null}">
			<html:text property="idCardNo" styleId="idCardNo"
						styleClass="text medium" readonly="true"
						alt="allowBlank:false,vtext:''" value="${resumeForm.idCardNo}"  onblur="val();"/>
		</c:if>
		<span id="message"></span>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="provider" styleId="provider"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${resumeForm.provider}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="resume.state" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				<eoms:dict key="dict-partner-baseinfo" dictId="state" isQuery="false" alt="allowBlank:false"
				defaultId="${resumeForm.state}" selectId="state" beanId="selectXML" />
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


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick=" sub();"/>
		</td>
	</tr>
</table>
<html:hidden property="proId" value="${proId}" />
<html:hidden property="addUser" value="${resumeForm.addUser}" />
<html:hidden property="addTime" value="${resumeForm.addTime}" />
<html:hidden property="id" value="${resumeForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>