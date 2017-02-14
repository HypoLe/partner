<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/checkselect.js"></script>
	<script type="text/javascript">
	//Ext.onReady(function() {
		//v = new eoms.form.Validation({form:'TawRmGuestform'});//validation form
	//});
	function do_delete(id){
		if(confirm('<bean:message key="tawRmThingForm.message"/>')){
			window.navigate('${app}/duty/TawRmGuestform.do?method=doDelete&id='+id);
			return true;
		}
		return false;
	}
</script>
<% System.out.println("-------1-------------");%>
<table class="formTable" align="center">
	<tr>
		<td>	<caption>
		<div><bean:message key="TawRmGuestform.tablename"/></div>
	</caption></td>
	</tr>
</table>
<% System.out.println("-------2-------------");%>
<display:table name="UNCheckList" cellspacing="0" cellpadding="0"
		id="UNCheckList" pagesize="30" class="table">
 	<display:column titleKey="TawRmGuestform.id" sortable="false">
	<%=pageContext.getAttribute("UNCheckList_rowNum")%>
	</display:column>
	<% System.out.println("------3--------------");%>
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
	<% System.out.println("-------------4-------");%>
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
		<% System.out.println("-------5-------------");%>
		<display:column titleKey="TawRmGuestform.selectAll"  sortable="false">
	 		
		</display:column>
		<% System.out.println("---------6-----------");%>
	</display:table>
	<br>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>