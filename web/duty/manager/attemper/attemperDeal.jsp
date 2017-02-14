<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'attemperForm'});
});

function dealSub(id){ // 处理子过程
alert("------------------------");
alert('${app}/duty/attemperSubs.do?method=deal&id=' + id);
	//showNewHtmlDlg('${app}/duty/attemperSubs.do?method=view&strutsAction=4&id=' + id,'','子过程',480,560,true,'网调子过程处理');
	return true;
}

</script>

<html:form action="/attempers.do?method=save" styleId="attemperForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemper.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="attemper.deptId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperForm.deptId}" beanId="tawSystemDeptDao" />
		</td>
		<td class="label">
			<fmt:message key="attemper.cruser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperForm.cruser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.sheetId" />
		</td>
		<td class="content">
			<bean:write name="attemperForm" property="sheetId" />
		</td>
		<td class="label">
			<fmt:message key="attemper.planSheetId" />
		</td>
		<td class="content">
			<bean:write name="attemperForm" property="planSheetId" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.crtime" />
		</td>
		<td class="content">
			<bean:write name="attemperForm" property="crtime" />
		</td>
		<td class="label">
			<fmt:message key="attemper.recordDept" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${attemperForm.recordDept}" beanId="tawSystemDictTypeDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.recordUser" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="attemperForm" property="recordUser" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="attemper.speciality" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperForm.speciality}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemper.netNames" />
		</td>
		<td class="content">
			<bean:write name="attemperForm" property="netNames" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${attemperForm.speciality=='9'}">
		<tr id="ifSpecialityDiv">
			<td class="label">
			<fmt:message key="attemper.subSpeciality" />
			</td>
			<td class="content" colspan="3">
			<eoms:dict key="dict-duty" dictId="attemperSpecialitySub" itemId="${attemperForm.subSpeciality}" beanId="id2nameXML" />
			</td>
		</tr>
		</c:when>
	</c:choose>
	<tr>
		<td class="label">
			<fmt:message key="attemper.netDeptList" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="attemperForm" property="netDeptName" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.status" />
		</td>
		<td class="content">
			<eoms:dict key="dict-duty" dictId="attemperStatus" itemId="${attemperForm.status}" beanId="id2nameXML" />
		</td>
		<td class="label">
			<fmt:message key="attemper.ifSubFinish" />
		</td>
		<td class="content">
			<bean:write name="attemperForm" property="ifSubFinish" />
		</td>		
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.title" />
		</td>
		<td class="content" colspan="3">
			<bean:write name="attemperForm" property="title" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.content" />
		</td>
		<td class="content" colspan="3" height="58">
			<bean:write name="attemperForm" property="content" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="attemper.remark" />
		</td>
		<td class="content" colspan="3" height="38">
			<bean:write name="attemperForm" property="remark" />
		</td>
	</tr>
	<c:choose>
		<c:when	test="${attemperForm.status=='3'}">
		<tr>
		<td class="label">
			<fmt:message key="attemper.delReason" />
		</td>
		<td class="content" colspan="3" height="38">
			<bean:write name="attemperForm" property="delReason" />
		</td>
	</tr>
		</c:when>
	</c:choose>
	<tr>
		<td class="label" nowrap="nowrap" align="right">
			<fmt:message key="attemper.accessories" /><html:errors property="accessory" /></td>
		<td colspan="5">
		<eoms:attachment name="attemperForm" property="accessory" 
           scope="request" idField="accessory" appCode="attemper" 
           viewFlag="Y"/> 
		</td>
	</tr>
</table>

<c:choose>
	<c:when	test="${attemperForm.deptId==sessionform.deptid&&attemperForm.status==1}">
		<table>
			<tr><td>&nbsp;</td></tr>
			<tr><td>
				<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
						onclick="javascript:{
							var url='${app}/duty/attempers.do?method=edit&id=${attemperForm.id}';
							location.href=url};"	/>
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
						onclick='javascript:{
							document.getElementById("delProcessIframe").style.display="inline";
							document.getElementById("delProcessIframe").style.height="200";}'	/>
				<input type="button" class="btn" value="<fmt:message key="attemper.button.addsub"/>" 
						onclick='javascript:{
							document.getElementById("subProcessIframe").style.display="inline";
							document.getElementById("subProcessIframe").style.height="350";}'	/>
				</td>
			</tr>
		</table>
	</c:when>
</c:choose>
<IFRAME style="display:none" ID="subProcessIframe" FRAMEBORDER=0 width="100%" height="2" SCROLLING=NO 
SRC='${app}/duty/attemperSubs.do?method=add&attemperId=${attemperForm.id}'>
</IFRAME>
<IFRAME style="display:none" ID="delProcessIframe" FRAMEBORDER=0 width="100%" height="2" SCROLLING=NO 
SRC='${app}/duty/attempers.do?method=removeinit&id=${attemperForm.id}'>
</IFRAME>

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="attemperSub.list.heading" /></div>
	</caption>
	<tr>
		<td width="18%">
			<fmt:message key="attemperSub.intendBeginTime" />
		</td>
		<td width="10%">
			<fmt:message key="attemperSub.isAppEffect" />
		</td>
		<td width="10%">
			<fmt:message key="attemperSub.persistTimes" />
		</td>
		<td width="8%">
			<fmt:message key="attemperSub.status" />
		</td>
		<td width="10%">
			<fmt:message key="attemperSub.cruser" />
		</td>
		<td width="18%">
			<fmt:message key="attemperSub.crtime" />
		</td>
		<td width="6%">
			<fmt:message key="button.deal" />
		</td>
	</tr>
	<logic:iterate id="ATTEMPERSUBLIST" name="ATTEMPERSUBLIST" type="com.boco.eoms.duty.model.AttemperSub">
	<tr>
		<td width="18%">
			<bean:write name="ATTEMPERSUBLIST" property="intendBeginTime" scope="page" />
		</td>
		<td width="10%">
			<eoms:dict key="dict-duty" dictId="faultIsAppEffectId" itemId="${ATTEMPERSUBLIST.isAppEffect}" beanId="id2nameXML" />
		</td>
		<td width="10%">
			<bean:write name="ATTEMPERSUBLIST" property="persistTimes" scope="page" />
		</td>
		<td width="8%">
			<eoms:dict key="dict-duty" dictId="attemperStatus" itemId="${ATTEMPERSUBLIST.status}" beanId="id2nameXML" />
		</td>
		<td width="10%">
			<eoms:id2nameDB id="${ATTEMPERSUBLIST.cruser}" beanId="tawSystemUserDao" />
		</td>
		<td width="18%">
			<bean:write name="ATTEMPERSUBLIST" property="crtime" scope="page" />
		</td>
		<td width="6%">
			<c:choose><c:when test="${attemperForm.deptId==sessionform.deptid&&attemperForm.status==1}">
				<html:link title="${eoms:a2u('处理网调子过程')}" href="${app}/duty/attemperSubs.do?method=view&strutsAction=4" paramId="id" paramProperty="id" paramName="ATTEMPERSUBLIST">
     				<img src="${app}/duty/images/an_bj.gif" border="0" >
       			</html:link>					
			</c:when></c:choose>
		</td>
	</tr>
	</logic:iterate>
</table>
<html:hidden property="id" value="${attemperForm.id}" />
<input type="hidden" name="BackUrl" id="BackUrl" 
value='${app}/duty/attempers.do?method=deal&id=${attemperForm.id}'>
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>