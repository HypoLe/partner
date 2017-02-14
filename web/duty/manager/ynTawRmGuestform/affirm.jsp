<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<script type="text/javascript">
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'TawRmGuestform'});//validation form
	});
	
	function alert_success(){
		if(confirm("${eoms:a2u('是否确认提交该数据信息，注:提交之后不能修改该信息！')}")){
			return true;
		}
		return false;
	}
</script>

<html:form action="/TawRmGuestform.do?method=save" method="post" styleId="TawRmGuestform">
	<html:hidden property="id" name="tawRmGuestForm"/>
	<html:hidden property="cruser" name="tawRmGuestForm"/>
	<html:hidden property="deptId" name="tawRmGuestForm"/>
	<html:hidden property="deptName" name="tawRmGuestForm"/>
	<input type="hidden" name="action" value="UPDATE"/>

	<table class="formTable" align="center">
	<tr>
	<td align="center" colspan="4">
		<caption>
		<div class="header center">
			<c:choose>
      <c:when test="${requestScope['tawRmGuestformForm'].strutsAction == 1}">
        <bean:message key="label.add"/>
      </c:when>
      <c:otherwise>
        <bean:message key="label.edit"/>
      </c:otherwise>
    </c:choose>
   <bean:message key="TawRmGuestform.tablename"/>
		</div>
	</caption>
	</td>
	</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.guestname" /></td>
			<td class="label">
				<html:text property="guestname" name="tawRmGuestForm" styleId="guestname" styleClass="text medium" alt="allowBlank:false"></html:text>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.inputdate" /></td>
			<td class="label" align="left"><bean:write name="tawRmGuestForm" property="inputdate"/></td>
			<html:hidden property="inputdate" name="tawRmGuestForm"/>
		</tr>
		 <tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.company" /></td>
			<td class="label">
				<html:errors property="company" />
				<html:text property="company" name="tawRmGuestForm" styleId="company" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.sender" /></td>
			<td class="label">
				<html:errors property="sender" />
				<html:text property="sender" name="tawRmGuestForm" styleId="sender" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
		</tr>
		
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.department" /></td>
			<td class="label">
				<html:errors property="department" />
				<html:text property="department" name="tawRmGuestForm" styleId="department" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.dutyman" /></td>
			<td class="label">
				<html:errors property="dutyman" />
				<html:text property="dutyman" name="tawRmGuestForm" styleId="dutyman" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.starttime" /></td>
			<td class="label">
				<html:errors property="starttime" />
				<input type="text" name="starttime" size="20" value='<bean:write name="tawRmGuestForm" property="starttime"/>'
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.endtime" /></td>
			<td class="label">
				<html:errors property="endtime" />
				<input type="text" name="endtime" size="20" value='<bean:write name="tawRmGuestForm" property="endtime"/>'
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.purpose" /></td>
			<td class="label" colspan="4">
				<html:errors property="purpose" />
				<html:textarea property="purpose" name="tawRmGuestForm"  styleId="purpose" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.concerned" /></td>
			<td class="label" align="left" colspan="4">
				<html:errors property="concerned" />
				<html:textarea property="concerned" name="tawRmGuestForm" styleId="concerned" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.affection" /></td>
			<td class="label" align="left" colspan="4">
				<html:errors property="affection" />
				<html:textarea property="affection" name="tawRmGuestForm" styleId="affection" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.notes" /></td>
			<td class="label" align="left" colspan="4">
				<html:errors property="notes" />
				<html:textarea property="notes" name="tawRmGuestForm" styleId="notes" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td  align="left" colspan="4">
				 <input type="submit" class="button" name="save" value='<bean:message key="label.save"/>' onclick="return alert_success();">
			</td>
		</tr>
	</table>
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
