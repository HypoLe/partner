<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<html:form action="/yndutyevent.do?method=queryDone" method="post" styleId="tawRmDutyEventForm">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawRmDutyEvent.queryEvent"/></div>
	</caption>
	<tr class="tr_show">
		<td class="label" style="width:10%">${eoms:a2u('选择机房')}</td>
		<td class="content">
			<html:select property="roomid" style="width: 4.0cm;">
				<html:option value='-1'>${eoms:a2u('全部')}</html:option>
				<html:options collection="CPTROOMLIST" property="value" labelProperty="label" />
			</html:select>
		</td>
	</tr>
	<tr class="tr_show">
		<td class="label" style="width:8%"><bean:message key="tawRmDutyEvent.faultType" /></td>
		<td class="content">
			<input type="radio" name="faultType" value="-1" checked="checked">${eoms:a2u('全部')}
			<logic:iterate id="dictItemXML" name="FAULTTYPELIST" type="com.boco.eoms.commons.system.dict.model.DictItemXML">
				<input type="radio" name='faultType' value='<bean:write name="dictItemXML" property="id" scope="page"/>'>
					<bean:write name="dictItemXML" property="name" scope="page"/>
				</input>
			</logic:iterate>
		</td>		
	</tr>
	<tr class="tr_show">
		<td class="label" style="width:8%"><bean:message key="tawRmDutyEvent.flag" /></td>
		<td class="content">
			<input type="radio" name="flag" value="-1" checked="checked">${eoms:a2u('全部')}
			<logic:iterate id="dictItemXML" name="FLAGLIST" type="com.boco.eoms.commons.system.dict.model.DictItemXML">
				<input type="radio" name='flag' value='<bean:write name="dictItemXML" property="id" scope="page"/>'>
					<bean:write name="dictItemXML" property="name" scope="page"/>
				</input>
			</logic:iterate>
		</td>		
	</tr>
	<tr>
		<td class="label" style="width:8%">
			<fmt:message key="tawRmDutyEvent.beginTime" />
		</td>
		<td class="content">
			<input type="text" name="fromBeginTime" size="20" 
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		&nbsp;&nbsp;--<fmt:message key="faultCommont.to" />--&nbsp;&nbsp;
			<input type="text" name="toBeginTime" size="20"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
	</tr>
	<tr class="tr_show">
		<td class="label" style="width:8%"><bean:message key="tawRmDutyEvent.complateFlag" /></td>
		<td class="content">
			<input type="radio" name="complateFlag" value="-1" checked="checked">${eoms:a2u('全部')}
			<logic:iterate id="dictItemXML" name="COMPLATELIST" type="com.boco.eoms.commons.system.dict.model.DictItemXML">
				<input type="radio" name='complateFlag' value='<bean:write name="dictItemXML" property="id" scope="page"/>'>
					<bean:write name="dictItemXML" property="name" scope="page"/>
				</input>
			</logic:iterate>
		</td>		
	</tr>
	<tr class="tr_show">
		<td class="label" style="width:8%"><bean:message key="tawRmDutyEvent.eventtitle" /></td>
		<td class="content">
			<input name="eventtitle" type="text"  class="clstext"  size="60" value="" />
	</tr>
	<tr class="tr_show">
		<td class="label" style="width:8%">${eoms:a2u('子过程内容')}</td>
		<td class="content">
			<input name="subContent" type="text"  class="clstext"  size="60" value="" />
		</td>
	</tr>
	<tr class="tr_show">
		<td class="label" style="width:8%">${eoms:a2u('子过程工单号')}</td>
		<td class="content">
			<input name="subWorksheetid" type="text"  class="clstext"  size="30" value="" />
		</td>
	</tr>
</table><br>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />
		</td>
	</tr>
</table>
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
