<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<!-- fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty"-->
<fmt:bundle basename="config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperForm'});
	fun_initMyBox();
	init();
});

function init(){
    var specialityValue =document.getElementById("speciality").value;
    var frm = document.forms["attemperForm"];
	var obj = document.getElementById("ifSpecialityDiv");

	if(specialityValue == 9) { 
		obj.style.display = "block";
	} else {
		obj.style.display = "none";
	}
}  

function ifSubSpeciality(specialityValue) {
	  var frm = document.forms["attemperForm"];
	  var obj = document.getElementById("ifSpecialityDiv");

	  if(specialityValue == 9) { 
		obj.style.display = "block";
	  } else {
		obj.style.display = "none";
	  }
}

function validateFaultForm() {
	var frm = document.forms["attemperForm"];
	var date1 = frm.endTime.value;
	var date2 = frm.beginTime.value;
	
	if (document.getElementById("title").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date06" />');
		document.getElementById("title").focus();
 		return false;
 	}
 	
 	if (document.getElementById("recordUser").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date01" />');
		document.getElementById("recordUser").focus();
 		return false;
 	}
 	
 	if (document.getElementById("netNames").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date02" />');
		document.getElementById("netNames").focus();
 		return false;
 	}

 	if (frm.content.value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date03" />');
		frm.content.focus();
 		return false;
 	}
 	
 	if (document.getElementById("netDeptName").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date04" />');
		document.getElementById("netDeptName").focus();
 		return false;
 	}
 	
 	if (document.getElementById("speciality").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date13" />');
		document.getElementById("speciality").focus();
 		return false;
 	}
 	
 	if (document.getElementById("recordDept").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemper.validate.date14" />');
		document.getElementById("recordDept").focus();
 		return false;
 	}
 	
 	frm.submit();
 }
 
</script>

<html:form action="/attempers.do?method=save" styleId="attemperForm" method="post"> 
<html:hidden name="attemperForm" property="cruser" />
<html:hidden name="attemperForm" property="deptId" />
<html:hidden name="attemperForm" property="contact" />
<html:hidden name="attemperForm" property="crtime" />
<html:hidden name="attemperForm" property="roomId" />
<html:hidden name="attemperForm" property="status" />
<html:hidden name="attemperForm" property="serial" />
<html:hidden name="attemperForm" property="beginTime" />
<html:hidden name="attemperForm" property="endTime" />
<html:hidden name="attemperForm" property="status" />
<html:hidden name="attemperForm" property="sheetId" />
<html:hidden name="attemperForm" property="id" />

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemper.form.heading"/></div>
	</caption>
	
	<tr>
		<td class="label">
			<fmt:message key="attemper.recordDept" />
		</td>
		<td class="content">
			<eoms:comboBox name="recordDept" id="recordDept" defaultValue="${attemperForm.recordDept}" initDicId="1040201" alt="allowBlank:false,vtext:''"/> 
		</td>
		<td class="label">
			<fmt:message key="attemper.planSheetId" />
		</td>
		<td class="content">
			<html:text property="planSheetId" styleId="planSheetId"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.planSheetId}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.recordUser" />
		</td>
		<td class="content">
			<html:text property="recordUser" styleId="recordUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperForm.recordUser}" />
		</td>
		<td class="label">
			<fmt:message key="attemper.netNames" />
		</td>
		<td class="content">
			<html:text property="netNames" styleId="netNames"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperForm.netNames}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.speciality" />
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" defaultId="${attemperForm.speciality}" beanId="selectXML" selectId="speciality" onchange="ifSubSpeciality(this.value)"/>
		</td>
	</tr>
	<tr id="ifSpecialityDiv" style="display:none">
		<td class="label">
			<fmt:message key="attemper.subSpeciality" />
		</td>
		<td class="content" colspan="3">
			<eoms:dict key="dict-duty" dictId="attemperSpecialitySub" defaultId="${attemperForm.subSpeciality}" beanId="selectXML" selectId="subSpeciality"/>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.netDeptList" />
		</td>
		<td class="content" colspan="3">
			<input type="button" name="query" value=<fmt:message key="faultCircuit.selected" /> onclick="fun_showMyBox(this);" class="clsbtn2">
			<input type="text" name="netDeptName" value="${attemperForm.netDeptName}" size="70" disabled="true" Class="clstext"/>
			<input type="hidden" name="netDeptList" value="${attemperForm.netDeptList}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.title" />
		</td>
		<td class="content" colspan="3">
			<html:text property="title" styleId="title" style="width:13.5cm;"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperForm.title}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.content" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="content" styleId="content" style="width:13.5cm;"
						styleClass="text medium" rows="10"
						alt="allowBlank:false,vtext:''" value="${attemperForm.content}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemper.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:13.5cm;"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperForm.remark}" />
		</td>
	</tr>
	<tr>
		<td class="label" nowrap="nowrap">
			<fmt:message key="attemper.accessories" /><html:errors property="accessory" /></td>
		<td colspan="5">
			<eoms:attachment name="attemperForm" property="accessory" 
           scope="request" idField="accessory" appCode="attemper" viewFlag="N"/>
			<!--eoms:attachment idList="" idField="accessory" appCode="attemper"/-->
		</td>
	</tr>
</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.release"/>" onclick="validateFaultForm();" />
		</td>
	</tr>
</table>
<p id="innerHtml" style="display:none">
	<table border="0" width="98%" cellspacing="1" class="table_show" cellPadding="0">
		<tr>
			<td class="content">
				<logic:iterate id="tawSystemDictType" indexId="index" name="NETDEPTLIST" type="com.boco.eoms.commons.system.dict.model.TawSystemDictType">
					<input type='checkbox' name='my_check' value='<bean:write name="tawSystemDictType" property="dictId" scope="page"/>,<bean:write name="tawSystemDictType" property="dictName" scope="page"/>'>
					<bean:write name="tawSystemDictType" property="dictName" scope="page"/></input>
					<c:choose><c:when test="${(index+1)%6==0}"></p></c:when></c:choose>
				</logic:iterate>
			</td>
		</tr>
		<tr >
			<td  align="center" colspan="8">
				<input type="button" property="ok" class="btn" value=<fmt:message key="button.confim"/> onclick="fun_setValue('netDeptName', 'netDeptList');" >
			</td>
		</tr>
	</table>
</p>
<html:hidden property="id" value="${attemperForm.id}" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>