<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'sitePapersForm'});
});
    
    window.onload = function(){
        var idSite = document.getElementById('idSite').value;
		var url = eoms.appPath+"/partner/serviceArea/sitePaperss.do?method=search&idSite="+idSite;
		//window.opener.location=url;
		var operType = '${operType}';
		if(operType == 'save'){
			window.opener.location=url;//刷新父窗口
			window.close(); //关闭自己			
		}
	}
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
function valgrid()
{
	var obj = document.getElementById("papersNo");
	var url = eoms.appPath+"/partner/serviceArea/sitePaperss.do?method=validationPapersNo&sitePapersId="+document.getElementById("id").value;
	var papersNo=document.getElementById("papersNo").value;
			Ext.Ajax.request({
									url : url ,
									method: 'POST',
									params:{papersNo:papersNo},
									success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
										var json = eval(res);
						     			if(json[0].message == true){
						     				if(obj.value!=""){
						     					document.getElementById("message").innerHTML = "<font color='green'>此基站证件号可以使用</font>";
						     					tr = true;
						     				}else{
						     					document.getElementById("message").innerHTML = "";
						     					obj.focus();
						     				}
						     			}else{
						     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此基站证件号已存在</font>";
						     				tr = false;
						     				obj.focus();
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
		alert("存在的基站证件号");
		return false;
	}
	if(v.check()){
       $("sitePapersForm").submit();
	}	
}
</script>

<html:form action="/sitePaperss.do?method=save" styleId="sitePapersForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="sitePapers.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="sitePapers.siteName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				${siteName}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="sitePapers.papersNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="papersNo" styleId="papersNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${sitePapersForm.papersNo}" onblur="valgrid()"/>
						<span id="message"></span>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="sitePapers.startTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="startTime" id="startTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt="allowBlank:false,vtext:''" value="${sitePapersForm.startTime}" />		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="sitePapers.endTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="endTime" id="endTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt="allowBlank:false,vtext:''" value="${sitePapersForm.endTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="sitePapers.remark" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:textarea property="remark" styleId="remark" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:255" value="${sitePapersForm.remark}" />
		</td>
	</tr>

</table>


</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="sub();"/>
			<input type="button" class="btn" value="关闭" onclick="javascript:window.close();" />
		</td>
	</tr>
</table>
<html:hidden property="isDel" value="${sitePapersForm.isDel}" />
<html:hidden property="addUser" value="${sitePapersForm.addUser}" />
<html:hidden property="addTime" value="${sitePapersForm.addTime}" />
<html:hidden property="updateUser" value="${sitePapersForm.updateUser}" />
<html:hidden property="delTime" value="${sitePapersForm.delTime}" />
<html:hidden property="delUser" value="${sitePapersForm.delUser}" />
<html:hidden property="updateTime" value="${sitePapersForm.updateTime}" />
<html:hidden property="id"  styleId="id"  value="${sitePapersForm.id}" />
<html:hidden property="idSite" value="${sitePapersForm.idSite}"/>
<html:hidden property="siteName" value="${siteName}"/>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>