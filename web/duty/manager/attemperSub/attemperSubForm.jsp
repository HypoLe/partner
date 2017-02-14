<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperSubForm'});
});

function ifEffects(ifEffectValue) {
	 var frm = document.forms["attemperSubForm"];
	 var obj = document.getElementById("ifEffectDiv");

	 if(ifEffectValue == 1) { 
		obj.style.display = "block";
	 } else {
		obj.style.display = "none";
	}
}

function validateForm() {
	var frm = document.forms["attemperSubForm"];
	var date1 = frm.intendEndTime.value;
	var date2 = frm.intendBeginTime.value;
	
	if(document.getElementById("isAppEffect").checked&&document.getElementById("effectOperation").value=="") { 
 		Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
		,'<fmt:message key="attemper.validate.date07" />');
		document.getElementById("effectOperation").focus();
 		return false;
 	}
 	
 	if (document.getElementById("content").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
		,'<fmt:message key="attemper.validate.date08" />');
		document.getElementById("content").focus();
 		return false;
 	}
 	
 	if (document.getElementById("intendBeginTime").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
		,'<fmt:message key="attemper.validate.date09" />');
		document.getElementById("intendBeginTime").focus();
 		return false;
 	}
 	
 	if (document.getElementById("intendEndTime").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
		,'<fmt:message key="attemper.validate.date10" />');
		document.getElementById("intendEndTime").focus();
 		return false;
 	}
	
	if(date1!="" && dateCompare(date2,date1)) { 
		Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
		,'<fmt:message key="attemper.validate.date11" />');
		document.getElementById("intendBeginTime").focus();
		return false;
	} 
	
 	frm.submit();
 }
</script>

<html:form action="/attemperSubs.do?method=save" styleId="attemperSubForm" method="post"> 
<html:hidden name="attemperSubForm" property="cruser"/>
<html:hidden name="attemperSubForm" property="deptId"/>
<html:hidden name="attemperSubForm" property="status"/>
<html:hidden name="attemperSubForm" property="serial"/>
<html:hidden name="attemperSubForm" property="crtime"/>
<html:hidden name="attemperSubForm" property="attemperId"/>
<html:hidden name="attemperSubForm" property="persistTimes"/>
<html:hidden name="attemperSubForm" property="ifContrastReport"/>

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemperSub.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.intendBeginTime" />
		</td>
		<td class="content">
			<input type="text" name="intendBeginTime" size="20" value="${attemperSubForm.intendBeginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td class="label">
			<fmt:message key="attemperSub.intendEndTime" />
		</td>
		<td class="content">
			<input type="text" name="intendEndTime" size="20" value="${attemperSubForm.intendEndTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.isAppEffect" />
		</td>
		<td class="content" colspan="3">
			<c:choose>
			<c:when	test="${attemperSubForm.isAppEffect=='1'}">
				<input type="radio" id="isAppEffectId1" name="isAppEffect" value="1" checked="checked" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.yes" />
				<input type="radio" id="isAppEffectId2" name="isAppEffect" value="0" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.no" />
			</c:when>
			<c:otherwise>
				<input type="radio" id="isAppEffectId1" name="isAppEffect" value="1" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.yes" />
				<input type="radio" id="isAppEffectId2" name="isAppEffect" value="0" checked="checked" onClick="ifEffects(this.value)">
				<fmt:message key="faultCommont.no" />
			</c:otherwise>
		</c:choose>
		</td>
	</tr>

	<c:choose>
		<c:when	test="${attemperSubForm.isAppEffect=='1'}">
		<tr id="ifEffectDiv" style="display:block">
		</c:when>
		<c:otherwise>
			<tr id="ifEffectDiv" style="display:none">
		</c:otherwise>
	</c:choose>
			<td class="label">
			<fmt:message key="attemperSub.effectOperation" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="effectOperation"  rows="4" style="width:9.5cm;" styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperSubForm.effectOperation}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="attemperSub.content" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="content"  rows="6" style="width:9.5cm;" styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${attemperSubForm.content}"/>
		</td>
	</tr>
</table>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateForm();" />
		</td>
	</tr>
</table>
<html:hidden property="id" value="${attemperSubForm.id}" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>