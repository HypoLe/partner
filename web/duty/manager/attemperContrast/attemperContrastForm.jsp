<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperContrastForm'});
	init();
});

function validateForm() {
	var frm = document.forms["attemperContrastForm"];
	var date1 = frm.endTime.value;
	var date2 = frm.beginTime.value;
	
	if (document.getElementById("beginTime").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.beginTime" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("beginTime").focus();
 		return false;
 	}
 	
 	if (document.getElementById("endTime").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.endTime" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("endTime").focus();
 		return false;
 	}
 	
 	if (document.getElementById("persistTimes").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.persistTimes" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("persistTimes").focus();
 		return false;
 	}
 	
 	if (document.getElementById("subRing").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.subRing" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("subRing").focus();
 		return false;
 	}
 	
 	if (document.getElementById("mainCable").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.mainCable" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("mainCable").focus();
 		return false;
 	}
 	
 	if (document.getElementById("protectCable").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.protectCable" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("protectCable").focus();
 		return false;
 	}
 	
 	if (document.getElementById("netNameA").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.netNameA" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("netNameA").focus();
 		return false;
 	}
 	
 	if (document.getElementById("netNameB").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.netNameB" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("netNameB").focus();
 		return false;
 	}
 	
 	if (document.getElementById("finishResult").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.finishResult" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("finishResult").focus();
 		return false;
 	}
 	
 	if (document.getElementById("beforeIntoA").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.beforeIntoA" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("beforeIntoA").focus();
 		return false;
 	}
 	
 	if (document.getElementById("beforeOuterA").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.beforeOuterA" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("beforeOuterA").focus();
 		return false;
 	}
 	
 	if (document.getElementById("beforeIntoB").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.beforeIntoB" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("beforeIntoB").focus();
 		return false;
 	}
 	
 	if (document.getElementById("afterIntoA").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.afterIntoA" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("afterIntoA").focus();
 		return false;
 	}
 	
 	if (document.getElementById("afterOuterA").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.afterOuterA" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("afterOuterA").focus();
 		return false;
 	}
 	
 	if (document.getElementById("afterIntoB").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.afterIntoB" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("afterIntoB").focus();
 		return false;
 	}
 	
 	if (document.getElementById("afterOuterB").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.afterOuterB" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("afterOuterB").focus();
 		return false;
 	}
 	
 	if (document.getElementById("adjustReason").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.adjustReason" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("adjustReason").focus();
 		return false;
 	}
 	
 	if (document.getElementById("cableClass").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperContrast.detail.title" />'
		,'<fmt:message key="attemperContrast.cableClass" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("cableClass").focus();
 		return false;
 	}
	
 	frm.submit();
 }

function evaluate(){
    var chk =document.all.chk;
    var cableClass='';
    for(var k=0;k<chk.length;k++){
    	if(chk[k].checked) {
    		cableClass += '#' +chk[k].value+'#,';
    	}
    }
    if(cableClass.length!=0){
    	cableClass=cableClass.substring(0,cableClass.length-1);
    	document.getElementById("cableClass").value=cableClass;
    }
}

function init(){
    var chk =document.all.chk;
    var cableChk =document.getElementById("cableClass").value;
    var cableClass = cableChk.replace(/#/g,"");
    
    if(cableClass.length ==1){
    	for(var k=0;k<chk.length;k++){
    		if(chk[k].value == cableClass)
    		chk[k].checked =true;
    	}
    }else{
    	var  ids =new Array()
    	cableClass.replace("#","");
    	ids=cableClass.split(",");
    	for(var k=0;k<chk.length;k++){
    		for(var n=0;n<ids.length;n++){
    			if(chk[k].value == ids[n])
    			chk[k].checked =true;
    		}
    	}
    }
   }  

function GoBack(){
   window.history.back();
}

</script>

<html:form action="/attemperContrasts.do?method=save" styleId="attemperContrastForm" method="post"> 
<html:hidden name="attemperContrastForm" property="attemperId"/>
<html:hidden name="attemperContrastForm" property="subAttemperId"/>
<html:hidden name="attemperContrastForm" property="deleted"/>
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemperContrast.form.heading"/></div>
	</caption>
	
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.friendCompany" />
		</td>
		<td class="content">
			<eoms:comboBox name="friendCompany" id="friendCompany"
						defaultValue="${attemperContrastForm.friendCompany}"
						initDicId="1040404" alt="allowBlank:true,vtext:''" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.beginTime" />
		</td>
		<td class="content">
			<input type="text" name="beginTime" size="20"
						value="${attemperContrastForm.beginTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.subCompany" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultfilialeId"
						defaultId="${attemperContrastForm.subCompany}" beanId="selectXML"
						selectId="subCompany" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.endTime" />
		</td>
		<td class="content">
			<input type="text" name="endTime" size="20"
						value="${attemperContrastForm.endTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						readonly="true" class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.cableClass" />
		</td>
		<td class="content">
			<logic:iterate id="dictItemXML" indexId="index" name="CABLECLASSLIST" type="com.boco.eoms.commons.system.dict.model.DictItemXML">
				<input type='checkbox' name='chk' value='<bean:write name="dictItemXML" property="id" scope="page"/>' onclick='evaluate();'></input>
				<bean:write name="dictItemXML" property="name" scope="page"/>
			</logic:iterate>
			<html:hidden property="cableClass" value="${attemperContrastForm.cableClass}" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.persistTimes" />
		</td>
		<td class="content">
			<html:text property="persistTimes" styleId="persistTimes"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.persistTimes}" />
			(<fmt:message key="attemperContrast.hour.name"/>)
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.ifNormalSwitch" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="contrastIfSwitch"
						defaultId="${attemperContrastForm.ifNormalSwitch}" beanId="selectXML"
						selectId="ifNormalSwitch" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.subRing" />
		</td>
		<td class="content">
			<html:text property="subRing" styleId="subRing"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.subRing}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.mainCable" />
		</td>
		<td class="content">
			<html:text property="mainCable" styleId="mainCable"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.mainCable}" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.protectCable" />
		</td>
		<td class="content">
			<html:text property="protectCable" styleId="protectCable"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.protectCable}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.netNameA" />
		</td>
		<td class="content">
			<html:text property="netNameA" styleId="netNameA"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.netNameA}" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.netNameB" />
		</td>
		<td class="content">
			<html:text property="netNameB" styleId="netNameB"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.netNameB}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.isAppEffect" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultIsAppEffectId"
						defaultId="${attemperContrastForm.isAppEffect}" beanId="selectXML"
						selectId="isAppEffect" />
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.finishResult" />
		</td>
		<td class="content">
			<html:text property="finishResult" styleId="finishResult"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.finishResult}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.beforeIntoA" />
		</td>
		<td class="content">
			<html:text property="beforeIntoA" styleId="beforeIntoA"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.beforeIntoA}" />(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.beforeOuterA" />
		</td>
		<td class="content">
			<html:text property="beforeOuterA" styleId="beforeOuterA"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.beforeOuterA}" />(db)
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.beforeIntoB" />
		</td>
		<td class="content">
			<html:text property="beforeIntoB" styleId="beforeIntoB"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.beforeIntoB}" />(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.beforeOuterB" />
		</td>
		<td class="content">
			<html:text property="beforeOuterB" styleId="beforeOuterB"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.beforeOuterB}" />(db)
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.afterIntoA" />
		</td>
		<td class="content">
			<html:text property="afterIntoA" styleId="afterIntoA"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.afterIntoA}" />(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.afterOuterA" />
		</td>
		<td class="content">
			<html:text property="afterOuterA" styleId="afterOuterA"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.afterOuterA}" />(db)
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.afterIntoB" />
		</td>
		<td class="content">
			<html:text property="afterIntoB" styleId="afterIntoB"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.afterIntoB}" />(db)
		</td>
		<td class="label">
			<fmt:message key="attemperContrast.afterOuterB" />
		</td>
		<td class="content">
			<html:text property="afterOuterB" styleId="afterOuterB"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.afterOuterB}" />(db)
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.adjustReason" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="adjustReason" styleId="adjustReason" style="width:13.5cm;"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.adjustReason}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperContrast.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark" styleId="remark" style="width:13.5cm;"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperContrastForm.remark}" />
		</td>
	</tr>
</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateForm();" />
			<input type="button" class="btn" value="<fmt:message key="button.goback"/>" onclick="GoBack();" />
		</td>
	</tr>
</table>
<html:hidden property="id" value="${attemperContrastForm.id}" />
<html:hidden property="deptId" value="${attemperContrastForm.deptId}" />
<html:hidden property="cruser" value="${attemperContrastForm.cruser}" />
<html:hidden property="crtime" value="${attemperContrastForm.crtime}" />
<html:hidden property="attemperId" value="${attemperContrastForm.attemperId}" />
<html:hidden property="subAttemperId" value="${attemperContrastForm.subAttemperId}" />
<html:hidden property="deleted" value="${attemperContrastForm.deleted}" />
<html:hidden property="id" value="${attemperContrastForm.id}" />

</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>