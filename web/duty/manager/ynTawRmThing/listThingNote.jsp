<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
	<script type="text/javascript">
	Ext.onReady(function() {
		window.parent.document.getElementById("subListViewThing<%=request.getParameter("thingId")%>").innerHTML = document.body.innerHTML;
	});
	</script>
<display:table name="ThingNoteList" cellspacing="0" cellpadding="0"
		id="ThingNoteList"  class="table">
 	<display:column titleKey="tawRmThingNote.no">
		<%=pageContext.getAttribute("ThingNoteList_rowNum")%>
	</display:column>
	<display:column  property="beginTime" sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.beginTime"/>
	<display:column  property="toUser" sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.toUser"/>
	<display:column  property="endTime"  sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.endTime"/>
	<display:column property="noteComment" sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.noteComment"/>
	<display:column  property="inputUserName" sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.inputUserName"/>
	</display:table>
</fmt:bundle>