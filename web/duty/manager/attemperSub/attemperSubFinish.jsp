<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperSubForm'});
});

function validateForm() {
	var frm = document.forms["attemperSubForm"];
	var date1 = document.getElementById("holdTime").value;
	var date2 = frm.intendBeginTime.value;
	var ifSubFinish= frm.ifSubFinish.value;
	
	if (document.getElementById("resultType").value == "") {
 		Ext.MessageBox.alert('<fmt:message key="attemper.detail.title" />'
		,'<fmt:message key="attemperSub.resultType" />,<fmt:message key="attemperContrast.must.write" />');
		document.getElementById("resultType").focus();
 		return false;
 	}
	
	if(date1!="" && dateCompare(date2,date1)) { 
		Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
		,'<fmt:message key="attemper.validate.date15" />');
		document.getElementById("endTime").focus();
		return false;
	} 
	
	// 对于传输专业,除本地网、时钟、时间同步网网调结果为：核实未完成(未影响业务),核实未完成(影响业务)外都要求生成对比表,请先生成对比表!
	var speciality=frm.speciality.value;
	var subSpeciality=frm.subSpeciality.value;
	var ifContrastReport=frm.ifContrastReport.value;
	var resultType = frm.resultType.value;
	if(speciality==9&&ifContrastReport<1){
		if(subSpeciality!=4&&subSpeciality!=5) {
			Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />','<fmt:message key="attemper.validate.date18" />');
			return false;
		} else {
			if(resultType!= "3" && resultType!= "4"){
				Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />','<fmt:message key="attemper.validate.date18" />');
				return false;
			}
		}
	}

	if(frm.resultType.value=="3" || frm.resultType.value=="4") {
		if(frm.remark.value=="") {
			Ext.MessageBox.alert('<fmt:message key="attemperSub.detail.title" />'
			,'<fmt:message key="attemper.validate.date16" />');
			document.getElementById("remark").focus();
			return false;
		}
	}

	if(ifSubFinish == 1) {
		if(confirm('<fmt:message key="attemper.validate.date17" />')) {
			frm.ifFinishAttemper.value = 1 ;
		} else {
			frm.ifFinishAttemper.value = -1 ;
		}
	}
	
 	frm.submit();
 }
</script>

<html:form action="/attemperSubs.do?method=save" styleId="attemperSubForm" method="post"> 
<html:hidden name="attemperSubForm" property="cruser"/>
<html:hidden name="attemperSubForm" property="deptId"/>
<html:hidden name="attemperSubForm" property="serial"/>
<html:hidden name="attemperSubForm" property="crtime"/>
<html:hidden name="attemperSubForm" property="attemperId"/>
<html:hidden name="attemperSubForm" property="persistTimes"/>
<html:hidden name="attemperSubForm" property="deleted"/>
<html:hidden name="attemperSubForm" property="ifContrastReport"/>
<html:hidden name="attemperSubForm" property="ifFinishAttemper"/>
<html:hidden name="attemperSubForm" property="finishUser"/>
<html:hidden name="attemperSubForm" property="intendBeginTime"/>
<html:hidden name="attemperSubForm" property="intendEndTime"/>
<html:hidden name="attemperSubForm" property="isAppEffect"/>
<html:hidden name="attemperSubForm" property="effectOperation"/>
<html:hidden name="attemperSubForm" property="content"/>
<html:hidden name="attemperSubForm" property="status" value="2"/>
<html:hidden name="ATTEMPER" property="ifSubFinish"/>
<html:hidden name="ATTEMPER" property="speciality"/>
<html:hidden name="ATTEMPER" property="subSpeciality"/>

<table class="formTable">
	<caption>
		<div class="header center"><bean:write name="ATTEMPER" property="title" scope="request"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.deptId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperSubForm.deptId}" beanId="tawSystemDeptDao" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.cruser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperSubForm.cruser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.intendBeginTime" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="intendBeginTime" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.intendEndTime" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="intendEndTime" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.isAppEffect" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${attemperSubForm.isAppEffect}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemperSub.persistTimes" />
		</td>
		<td class="content">
			<bean:write name="attemperSubForm" property="persistTimes" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${attemperSubForm.isAppEffect=='1'}">
		<tr>
			<td class="label" height="78"><fmt:message key="attemperSub.effectOperation" /></td>
			<td class="content" colspan="3">
				<c:choose><c:when test="${attemperSubForm.effectOperation !=null}">	
					<bean:define id="effectOperation" name="attemperSubForm" property="effectOperation" />
						<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)effectOperation) %>
				</c:when></c:choose>&nbsp;
			</td>
		</tr>
	</c:when></c:choose>
	<tr>
		<td class="label" height="78"><fmt:message key="attemperSub.content" /></td>
		<td class="content" colspan="3">
			<c:choose><c:when test="${attemperSubForm.content !=null}">	
				<bean:define id="content" name="attemperSubForm" property="content" />
				<%= com.boco.eoms.base.util.StaticMethod.toHtml((String)content) %>
			</c:when></c:choose>&nbsp;
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.resultType" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperResult" defaultId="${attemperSubForm.resultType}" beanId="selectXML" selectId="resultType"/>
		</td>
		<td class="label">
			<fmt:message key="attemperSub.holdTime" />
		</td>
		<td class="content">
			<input type="text" name="holdTime" size="20" value="${attemperSubForm.holdTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemperSub.remark" />
		</td>
		<td class="content" colspan="3">
			<html:textarea property="remark"  rows="6" style="width:9.5cm;" styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${attemperSubForm.remark}"/>
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