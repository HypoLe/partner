<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'aptitudeForm'});
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
	var obj = document.getElementById("aptitudeName");
	var aptitudeName = encodeURIComponent(document.getElementById("aptitudeName").value);
	var providerName = encodeURIComponent(document.getElementById("providerName").value);
	var url = "../baseinfo/aptitudes.do?method=validationAptitudeName&aptId="+document.getElementById("id").value+"&aptitudeName="+aptitudeName+"&providerName="+providerName;
			
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
	     					document.getElementById("message").innerHTML = "<font color='green'>此名称可以使用</font>";
	     					tr = true;
	     				}else{
	     					obj.focus();
	     				}
	     			}else{
	     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此名称已存在</font>";
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
		alert("服务资质已存在");
		return false;
	}
	if(v.check()){
       $("aptitudeForm").submit();
	}	
}
   window.onload = function(){
		var url = "${app}/partner/baseinfo/aptitudes.do?method=searchOne&proId=${proId}";
		//window.opener.location=url;
		var operType = '${operType}';
		if(operType == 'save'){
			window.opener.location=url;//刷新父窗口
			window.close(); //关闭自己			
		}
	}
</script>

<html:form action="/aptitudes.do?method=save" styleId="aptitudeForm" method="post"> 
<font color='red'>*</font>号为必填内容
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="aptitude.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.providerName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${aptitudeForm.providerName}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${aptitudeForm.aptitudeName}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeLevle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				
			 <eoms:id2nameDB id="${aptitudeForm.aptitudeLevle}"  beanId="ItawSystemDictTypeDao" />
				
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeStartTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${aptitudeForm.aptitudeStartTime}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeEndTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			${aptitudeForm.aptitudeEndTime}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeDesc" />
		</td>
		<td class="content">
			${stationForm.remark}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="aptitude.aptitudeAccessory" />
		</td>
		<td class="content">
			
			${stationForm.aptitudeAccessory}
			
		<!-- 	<eoms:attachment name="aptitudeForm" property="aptitudeAccessory" scope="request" idField="aptitudeAccessory" appCode="partnerBaseinfo" />
		 -->
		</td>
	</tr>

</table>

</fmt:bundle>


<html:hidden property="proId" value="${proId}" />
<html:hidden property="addUser" value="${aptitudeForm.addUser}" />
<html:hidden property="addTime" value="${aptitudeForm.addTime}" />
<html:hidden property="id" value="${aptitudeForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>