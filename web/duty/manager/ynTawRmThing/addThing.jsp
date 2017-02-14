<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<html:form action="/TawRmThing.do?method=save" method="post" styleId="tawRmThingForm">
	<script type="text/javascript">
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'tawRmThingForm'});//validation form
	});
	</script>
	<table class="formTable">
		<caption>
			<div class="header center"><fmt:message key="tawRmThingForm.form.head"/></div>
		</caption>
		<tr >
			<td class="label" align="left" style="width:10%">
				<eoms:label styleClass="desc" key="tawRmThingForm.thingName" />
			</td>
			<td class="content" align="left" style="width:20%">
				<html:text property="thingName" styleId="thingName" styleClass="text medium" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
			<td class="label" align="left" style="width:10%">
				<eoms:label styleClass="desc" key="tawRmThingForm.isForUse" />
			</td>
			<td class="content" align="left" style="width:20%">
				<input type="radio" name="isForUse" value="1" checked="checked"><fmt:message key="tawRmThingForm.isForUse_yes" />
				<input type="radio" name="isForUse" value="0"><fmt:message key="tawRmThingForm.isForUse_no" />
			</td>
			<td class="label" align="left" style="width:10%">
				<eoms:label styleClass="desc" key="tawRmThingForm.estate"/>
			</td>
			<td class="content" align="left" style="width:20%">
				<input type="radio" name="estate" value="1" checked="checked"><fmt:message key="tawRmThingForm.isEstate" />
				<input type="radio" name="estate" value="0"><fmt:message key="tawRmThingForm.noEstate" />
			</td>
		</tr>
		<tr align="left">
			<td class="label" align="left"><eoms:label styleClass="desc" key="tawRmThingForm.thingComment"/></td>
			<td colspan="5">
				<html:textarea property="thingComment" style="width:85%; height:1.0cm" styleId="thingComment" styleClass="textarea medium" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td>
				<input type="submit" name="save" value="<bean:message key="label.save"/>" class="button">
			</td>
		</tr>
	</table>
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
