<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<html:form action="/TawRmGuestform.do?method=save" method="post" styleId="TawRmGuestform">
	<input type="hidden" name="action" value="ADD"/>
	<html:hidden property="cruser" name="TawRmGuestform"/>
	<html:hidden property="deptId" name="TawRmGuestform"/>
	<html:hidden property="deptName" name="TawRmGuestform"/>
	
	<script type="text/javascript">
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'TawRmGuestform'});//validation form
		clear();
	});
	function clear(){
		document.all.guestname.value='';
		document.all.company.value='';
		document.all.sender.value='';
		document.all.department.value='';
		document.all.dutyman.value='';
		document.all.endtime.value='';
		document.all.purpose.value='';
		document.all.notes.value='';
		document.all.concerned.value='';
		document.all.affection.value='';
	}
</script>
	<table class="formTable" align="center" style="width:700">
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
				<html:errors property="guestname" />
				<html:text property="guestname" styleId="guestname" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.inputdate" /></td>
			<td class="label" align="left">
				<html:errors property="inputdate" />
					<input type="text" name="inputdate" size="20" value="${TawRmGuestform.inputdate}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
		</tr>
		 <tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.company" /></td>
			<td class="label">
				<html:errors property="company" />
				<html:text property="company" styleId="company" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.dutyman" /></td>
			<td class="label">
				<html:errors property="dutyman" />
				<html:text property="dutyman" styleId="dutyman" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
		</tr>
		
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.department" /></td>
			<td class="label">
				<html:errors property="department" />
				<html:text property="department" styleId="department" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.sender" /></td>
			<td class="label">
				<html:errors property="sender" />
				<html:text property="sender" styleId="sender" styleClass="text medium" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:text>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.starttime" /></td>
			<td class="label">
				<html:errors property="starttime" />
				<input type="text" name="starttime" size="20" value="${TawRmGuestform.inputdate}"
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.endtime" /></td>
			<td class="label">
				<html:errors property="endtime" />
				<input type="text" name="endtime" size="20" value=""
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.purpose" /></td>
			<td class="label" colspan="4">
				<html:errors property="purpose" />
				<html:textarea property="purpose"  styleId="purpose" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:false,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.concerned" /></td>
			<td class="label" align="left" colspan="4">
				<html:errors property="concerned" />
				<html:textarea property="concerned" styleId="concerned" styleClass="textarea medium"  style="width:500;height:1.0cm" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.affection" /></td>
			<td class="label" align="left" colspan="4">
				<html:errors property="affection" />
				<html:textarea property="affection"  styleId="affection" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label" align="left"><eoms:label styleClass="desc" key="TawRmGuestform.notes" /></td>
			<td class="label" align="left" colspan="4">
				<html:errors property="notes" />
				<html:textarea property="notes"  styleId="notes" styleClass="textarea medium" style="width:500;height:1.0cm" alt="allowBlank:true,vtext:'${eoms:a2u('')}'"></html:textarea>
			</td>
		</tr>
		<tr>
			<td  align="left" colspan="4">
				 <input type="submit" class="button" name="save" value='<bean:message key="label.save"/>'>
			</td>
		</tr>
	</table>
</html:form>
<display:table name="UNCHECKGUESTFORMLIST" cellspacing="0" cellpadding="0"
		id="UNCHECKGUESTFORMLIST"  class="table">
 	<display:column titleKey="TawRmGuestform.id">
	<%=pageContext.getAttribute("UNCHECKGUESTFORMLIST_rowNum")%>
	</display:column>
	<display:column property="inputdate" sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.inputdate" />
	
	<display:column property="guestname" sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.guestname"/>

	<display:column property="company" sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.company"/>

	<display:column property="dutyman" sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.dutyman"/>
	
		<display:column property="starttime" sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.starttime"/>
	
		<display:column property="endtime" sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.endtime"/>
	
		<display:column  sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.flag">
			<c:choose>
				<c:when test="${requestScope['tawRmGuestformForm'].flag == 0}">
					<bean:message key="TawRmGuestform.nolimit"/>
				</c:when>
				<c:otherwise>
					<bean:message key="TawRmGuestform.nosubmit"/>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:column titleKey="TawRmGuestform.manage">
			<html:link title="" href="${app}/duty/TawRmGuestform.do?method=confirm" paramId="id" paramProperty="id" paramName="UNCHECKGUESTFORMLIST">
				<html:img src="${app}/duty/images/new.gif"/>
			</html:link>
		</display:column>
	</display:table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>