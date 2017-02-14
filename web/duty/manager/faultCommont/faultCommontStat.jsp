<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<html:form action="/faultCommonts.do?method=stat" styleId="faultCommontForm" method="post"> 
	<table class="formTable">
		<caption>
			<div class="header center"><fmt:message key="faultCommont.stat.heading" /></div>
		</caption>
		<tr>
			<td class="label">
				<fmt:message key="faultCommont.beginTime" />
			</td>
			<td class="content" colspan="3">
				<input type="text" name="fromBeginTime" size="20" value="${faultCommontForm.fromBeginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		&nbsp;&nbsp;--<fmt:message key="faultCommont.to" />--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20" value="${faultCommontForm.toBeginTime}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
		</tr>
	</table>
	
	<table>
		<tr><td><input type="submit" class="btn" value="<fmt:message key="button.stat"/>" /></td></tr>
	</table>
	
	<table cellpadding="0" class="table faultCommontList" cellspacing="0" id="faultCommontList">
	<thead><tr>
		<th class="sortable"><fmt:message key="faultCommont.typeId" /></th>
		<th class="sortable"><fmt:message key="faultcommont.sum" /></th>
	</tr></thead>
	<logic:iterate id="faultCommontList" name="StatList" type="com.boco.eoms.duty.model.FaultCommont">
	<tr class="tr_show">
		<td><eoms:id2nameDB id="${faultCommontList.typeId}" beanId="tawSystemDictTypeDao" /></td>
		<td><a href="${app}/duty/faultCommonts.do?method=list&typeId=${faultCommontList.typeId}&fromBeginTime=${faultCommontForm.fromBeginTime}&toBeginTime=${faultCommontForm.toBeginTime}">
    		<bean:write name="faultCommontList" property="num"/>
       	</a>
		</td>
	</tr>
	</logic:iterate>
	</table>
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>