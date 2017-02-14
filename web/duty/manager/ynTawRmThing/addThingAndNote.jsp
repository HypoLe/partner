<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<html:form action="/TawRmThing.do?method=save" method="post" styleId="tawRmThingForm">
	<script type="text/javascript">
		Ext.onReady(function() {
			//v = new eoms.form.Validation({form:'tawRmThingForm'});//validation form
			init();
		});
		var thingId='<bean:write name="TawRmThing" property="id"/>';
		function init() { 		
 			if ("1" =='<bean:write name="TawRmThing" property="isForUse" />') {
 				document.getElementsByName("isForUse")[0].checked = true;
 			} else {
 				document.getElementsByName("isForUse")[1].checked = true;
 			}
 		 	if ("1" == '<bean:write name="TawRmThing" property="estate" />') {
 				document.getElementsByName("estate")[0].checked = true;
 			} else {
 				document.getElementsByName("estate")[1].checked = true;
 			}
 		}
 		
 		function deleteThing(id){
 			window.navigate('${app}/duty/TawRmThing.do?method=deleteThing&thingId='+id);
 		}
 		
 		
 		function updateThingNote(id) {
 			var uri='${app}/duty/TawRmThing.do?method=addThingNote&thingNoteId='+id+"&thingId="+thingId;
 	 		var beginTime =document.getElementById("beginTime" + id).value;
 	 		var toUser=document.getElementById("toUser" + id).value;
 	 		var endTime =document.getElementById("endTime" + id).value;
 	 		var noteComment=document.getElementById("noteComment" + id).value;
 	 		var estate= document.getElementById("estate" + id).value;
 	 		uri +="&beginTime="+beginTime+"&toUser="+toUser+"&endTime="+endTime+"&noteComment="+noteComment+"&estate="+estate;
 	 		window.navigate(uri);
 		}
 		
 		function deleteThingNote(id){
 			window.navigate('${app}/duty/TawRmThing.do?method=deleteThingNote&thingNoteId='+id+"&thinId="+thingId);
 		}
 		
 		function validateForm() {
			var frm = document.forms["updateThingNoteForm"];
			
			if (document.getElementById("beginTime").value == "") {
		 		alert("${eoms:a2u('外借时间不能为空，请填写！')}");
   				frm.beginTime.focus();
		 		return false;
		 	}
			
			if (document.getElementById("toUser").value == "") {
		 		alert("${eoms:a2u('外借人不能为空，请填写！')}");
   				frm.toUser.focus();
		 		return false;
		 	}
		 	
		 	frm.submit();
	   }
	</script>
	<table class="formTable">
		<caption>
			<div class="header center"><fmt:message key="tawRmThingForm.form.head"/></div>
		</caption>
		<tr class="label">
			<input type="hidden" name="action" value="update"/>
			<input type="hidden" name="id" value='<bean:write name="TawRmThing" property="id"/>'>
			<td class="label" align="left" style="width:10%">
				<fmt:message key="tawRmThingForm.thingName"/>
			</td>
			<td class="label" align="left" style="width:20%">
				<input type="text" name="thingName" value='<bean:write name="TawRmThing" property="thingName"/>' class="text">
			</td>
			<td class="label" align="left" style="width:10%">
				<fmt:message key="tawRmThingForm.isForUse"/>
			</td>
			<td class="label" align="left" style="width:20%">
				<input type="radio" name="isForUse" value="1"><fmt:message key="tawRmThingForm.isForUse_yes" />
				<input type="radio" name="isForUse" value="0"><fmt:message key="tawRmThingForm.isForUse_no" />
			</td>
			<td class="label" align="left" style="width:10%">
				<fmt:message key="tawRmThingForm.estate"/>
			</td>
			<td class="label" align="left" style="width:20%">
				<input type="radio" name="estate" value="1"><fmt:message key="tawRmThingForm.isEstate" />
				<input type="radio" name="estate" value="0"><fmt:message key="tawRmThingForm.noEstate" />
			</td>
		</tr>
		<tr class="label" align="left">
			<td class="label" align="left">
				<fmt:message key="tawRmThingForm.thingComment"/>
			</td>
			<td colspan="5">
				<input type="textarea" name="thingComment" style="width:85%; height:1.0cm" value='<bean:write name="TawRmThing" property="thingComment"/>' style="width:23.0cm; height:1.0cm" class="text">
			</td>
		</tr>
	</table>
	<table align="right">
		<tr align="right">
			<td align="right">
				<input type="submit" name="save" value="<bean:message key="label.save"/>" class="button">
				<input type="button" name="save" value="<bean:message key="label.delete"/>" onclick="deleteThing('<bean:write name="TawRmThing" property="id"/>')" class="button">
			</td>
		</tr>
	</table>
</html:form>
<!-- ------------------------------------------------------------------------- -->
<form action="${app}/duty/TawRmThing.do?method=addThingNote" method="post" name="updateThingNoteForm">
	<br>
	<table class="formTable">
		<caption>
			<div class="header center"><fmt:message key="tawRmThingNote.add.head"/></div>
		</caption>
		<tr><input type="hidden" name="thingId" value='<bean:write name="TawRmThing" property="id"/>'>
			<td class="label" align="left"><fmt:message key="tawRmThingNote.beginTime"/></td>
			<td class="label" align="left"><fmt:message key="tawRmThingNote.toUser"/></td>
			<td class="label" align="left"><fmt:message key="tawRmThingNote.endTime"/></td>
			<td class="label" align="left"><fmt:message key="tawRmThingNote.estate"/></td>
			<td class="label" align="left"><fmt:message key="tawRmThingNote.noteComment"/></td>
		</tr>
			<tr>
			<td class="label" align="left">
				<input type="text" name="beginTime" size="20" value=""
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td class="label" align="left">
				<input type="text" id="toUser" name="toUser"  class="text"/>
			</td>
			<td class="label" align="left">
				<input type="text" id="endTime" name="endTime" size="20" value=""
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td class="label" align="left">
				<select name="thingNote.estate" class="text">
					<option value="0"><fmt:message key="tawRmThingNote.estate.loan"/></option>
				</select>
			</td>
			<td class="label" align="left">
				<input type="text" name="noteComment"  class="text">
			</td>
		</tr>
	</table>
	<table align="right">
		<tr  align="right">
			<td  align="right">
				<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateForm();" />
			</td>
		</tr>
	</table>	
</form>	
<!-- ------------------------------------------------------------------------- -->
<br>
<table align="center">
	<caption>
		<div class="header center"><fmt:message key="tawRmThingNote.list.head"/></div>
	</caption>
</table>
	<display:table name="TawRmThingNoteList" cellspacing="0" cellpadding="0" id="TawRmThingNoteList"  class="table">
 	<display:column titleKey="tawRmThingNote.no">
	<%=pageContext.getAttribute("TawRmThingNoteList_rowNum")%>
	</display:column>
	<display:column  sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.beginTime">
			<input type="text" name="beginTime${TawRmThingNoteList.id}" size="20" value='${TawRmThingNoteList.beginTime}' 
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
	</display:column>
	<display:column  sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.toUser">
		<input type="text" name="toUser${TawRmThingNoteList.id}" value='${TawRmThingNoteList.toUser}' class="text">
	</display:column>
	<display:column  sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.endTime">
			<input type="text" name="endTime${TawRmThingNoteList.id}" size="20" value='${TawRmThingNoteList.endTime}' 
			onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
	</display:column>
	<display:column  sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.estate">
			<c:choose>
				<c:when test="${TawRmThingNoteList.estate==1}">
				<select name="estate${TawRmThingNoteList.id}" styleId="estate" styleClass="select medium">
					<option value="1" selected="true"><fmt:message key="tawRmThingNote.estate.loan"/></option>
					<option value="0"><fmt:message key="tawRmThingNote.estate.return"/></option></select>
				</c:when>
				<c:otherwise>
					<select name="estate${TawRmThingNoteList.id}" styleId="estate" styleClass="select medium">
						<option value="1" class="text" ><fmt:message key="tawRmThingNote.estate.loan"/></option>
						<option value="0" selected="true" class="text"><fmt:message key="tawRmThingNote.estate.return"/></option>
					</select>
				</c:otherwise>
			</c:choose>
	</display:column>
	<display:column sortable="false"
			headerClass="sortable" titleKey="tawRmThingNote.noteComment">
		<input type="text" name="noteComment${TawRmThingNoteList.id}" class="text" value='${TawRmThingNoteList.noteComment}'>
	</display:column>
	<display:column  sortable="false"
			headerClass="sortable" titleKey="tawRmThingForm.operate" >
			<input type="button" name="save" value="<bean:message key="label.save"/>" class="button" onclick="updateThingNote('${TawRmThingNoteList.id}')">
			<input type="button" name="delete" value="<bean:message key="label.delete"/>" class="button" onclick="deleteThingNote('${TawRmThingNoteList.id}')">
	</display:column>
	</display:table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>