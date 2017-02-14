<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>

<script type="text/javascript">
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'TawRmGuestform'});//validation form
	});
</script>

<html:form action="/TawRmGuestform.do?method=save" method="post" styleId="TawRmGuestform">
<table class="formTable" align="center">
	<tr>
		<td align="center" colspan="4">
			<caption><div class="header center"><bean:message key="label.view"/>
				<bean:message key="TawRmGuestform.tablename"/>
				</div>
			</caption>
		</td>
	</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.guestname" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="guestname"/>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.inputdate" /></td>
			<td class="content" align="left"><bean:write name="tawRmGuestForm" property="inputdate"/></td>
			<html:hidden property="inputdate" name="tawRmGuestForm"/>
		</tr>
		 <tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.company" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="company"/>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.sender" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="sender"/>
			</td>
		</tr>
		
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.department" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="department"/>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.dutyman" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="dutyman"/>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.starttime" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="starttime"/>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.endtime" /></td>
			<td class="content">
				<bean:write name="tawRmGuestForm" property="endtime"/>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.purpose" /></td>
			<td class="content" colspan="4" height="38">
				<bean:write name="tawRmGuestForm" property="purpose"/>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.concerned" /></td>
			<td class="content" align="left" colspan="4" height="38">
				<bean:write name="tawRmGuestForm" property="concerned"/>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.affection" /></td>
			<td class="content" align="left" colspan="4" height="38">
				<bean:write name="tawRmGuestForm" property="affection"/>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.notes" /></td>
			<td class="content" align="left" colspan="4" height="38">
				<bean:write name="tawRmGuestForm" property="notes"/>
			</td>
		</tr>
		<tr>
			<td  align="left" colspan="4">
				<c:choose>
					<c:when	test="${tawRmGuestForm.roomId==sessionform.roomId}">	
					<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('<fmt:message key="faultCommont.is.delete"/>')){
						var url='${app}/duty/TawRmGuestform.do?method=doDelete&id=${tawRmGuestForm.id}';
						location.href=url}"	/>&nbsp;&nbsp;
				</c:when>
				</c:choose>
				<input type="button" class="btn" value="<fmt:message key="button.goback"/>" onclick="GoBack();" />
			</td>
		</tr>
	</table>
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
