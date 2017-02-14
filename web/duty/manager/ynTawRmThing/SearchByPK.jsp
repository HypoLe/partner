<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<form action="${app}/duty/TawRmThing.do?method=doQuery" method="post" styleId="tawRmThingForm"  name="form1">
	
	<table align="center">
		<tr>
			<td>
		<option>
			<div class="header center"><fmt:message key="tawRmThingForm.query"/></div>
		</option>
		</td></tr>
	</table>
	<table class="formTable">
		<tr class="label" align="left">
			<td class="label" align="left">
				<fmt:message key="tawRmThingForm.thingName"/>
				<select name="id" style="width: 6.0cm;">
					<logic:iterate id="TawRmThingList" name="TawRmThingList">
						<option value='<bean:write name="TawRmThingList" property="id"/>'><bean:write name="TawRmThingList" property="thingName"/></option>
					</logic:iterate>
				 </select>
				<fmt:message key="tawRmThingNote.beginTime"/>
				<input type="text" name="beginTime" size="20" value=""
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			<fmt:message key="tawRmThingNote.endTime"/>
							<input type="text" name="endTime" size="20" value=""
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			<input type="submit" name="save" value="<bean:message key="button.query"/>" class="button">
			</td>
		</tr>
		<tr>
			<td id="query_result"></td>
		</tr>
	</table>
</form>

</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
