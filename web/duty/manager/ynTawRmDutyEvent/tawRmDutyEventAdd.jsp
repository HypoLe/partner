<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
 
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<script type="text/javascript" src="<%=request.getContextPath()%>/duty/js/faultCommontJs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">

<script>
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tawRmDutyEventForm'});
	fun_initMyBox();
});

function validateForm(){
   	var frm = document.forms["tawRmDutyEventForm"];
   	
   	if (document.getElementById("subHappentime").value == "") {
 		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请填写子过程发生时间！')}");
 		alert("${eoms:a2u('请填写子过程发生时间！')}");
		document.getElementById("subHappentime").focus();
 		return false;
 	}

   	// 初始化隐藏值
	frm.beginTime.value = document.getElementById("subHappentime").value;

   	if (document.getElementById("eventtitle").value == "") {
 		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请填写事件主题！')}");
 		alert("${eoms:a2u('请填写事件主题！')}");
		document.getElementById("eventtitle").focus();
 		return false;
 	}

 	if (document.getElementById("eventtitle").value == "") {
 		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请填写事件主题！')}");
 		alert("${eoms:a2u('请填写事件主题！')}");
		document.getElementById("eventtitle").focus();
 		return false;
 	}
 	
 	if (document.getElementById("faultType").value == "") {
 		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请选择事件类别！')}");
 		alert("${eoms:a2u('请选择事件类别！')}");
		document.getElementById("faultType").focus();
 		return false;
 	}
 	
 	if (document.getElementById("flag").value == "") {
 		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请重要等级！')}");
 		alert("${eoms:a2u('请重要等级！')}");
		document.getElementById("flag").focus();
 		return false;
 	}

 	if (document.getElementById("subContent").value == "") {
 		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请填写子过程内容！')}");
 		alert("${eoms:a2u('请填写子过程内容！')}");
		document.getElementById("subContent").focus();
 		return false;
 	}
 	
 	var date1 = frm.beginTime.value;
	var date2 = frm.endtime.value;	
	if(date2!="" && dateCompare(date1,date2)) { 
		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,"${eoms:a2u('结束时间要大于开始时间！')}");
		alert("${eoms:a2u('结束时间要大于开始时间！')}");
		document.getElementById("endtime").focus();
		return false;
	} 
	
	if(dateCompare(date1,getDateStr(0))) {
		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,"${eoms:a2u('子过程发生时间不能是将来时！')}");
		alert("${eoms:a2u('子过程发生时间不能是将来时！')}");
		document.getElementById("subHappentime").focus();
		return false;
	}
	
	if(dateCompare(getDateStr(-0.5),date1)) {
		// Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,"${eoms:a2u('子过程发生时间不能早于当前时间12小时！')}");
		alert("${eoms:a2u('子过程发生时间不能早于当前时间12小时！')}");
		document.getElementById("subHappentime").focus();
		return false;
	}
 	
   	frm.submit();
 }	 
</script>

<html:form action="/yndutyevent.do?method=save" method="post" styleId="tawRmDutyEventForm">
	<html:hidden name="tawRmDutyEventForm" property="complateFlag" />
	<html:hidden name="tawRmDutyEventForm" property="inputdate" />
	<html:hidden name="tawRmDutyEventForm" property="inputuser" />
	<html:hidden name="tawRmDutyEventForm" property="roomid" />
	<html:hidden name="tawRmDutyEventForm" property="workserial" />		
	<html:hidden name="tawRmDutyEventForm" property="isSubmit" />		
		
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<!-- caption><bean:message key="tawRmDutyEvent.addEvent" /></caption-->
		<tr>
			<td class="label">
				 <bean:message key="tawRmDutyEvent.faultType" />
			</td>
			<td >
				<eoms:dict key="dict-duty" dictId="faultType" beanId="selectXML" defaultId="${faultType}" isQuery="false"  selectId="faultType"/>
			</td>
			<td class="label">
				 <bean:message key="tawRmDutyEvent.flag" />
			</td>
			<td >
			<eoms:dict key="dict-duty" dictId="faultflag" beanId="selectXML"  defaultId="${flag}" isQuery="false"  selectId="flag"/>
		 	</td>
		</tr> 
		<tr>
			<td class="label">
					 <bean:message key="tawRmDutyEvent.beginTime" />
			</td>
			<td >
				<input type="text" name="beginTime" size="20" onfocus="alert("${eoms:a2u('该时间参考第一个字过程开始时间!')}");" readonly="readonly">
			</td>
			<td class="label">
					<bean:message key="tawRmDutyEvent.endtime" />
			</td>
			<td >
					<input type="text" name="endtime" value='${endtime}' size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
		</tr>

		<tr class="tr_show">
			<td class="label">
				 <bean:message key="tawRmDutyEvent.eventtitle" />
			</td>
			<td COLSPAN="3">
					<input name="eventtitle" value='${tawRmDutyEventForm.eventtitle}'  type="text"  class="clstext"  size="81" value="" />
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="tawRmDutyEvent.regionlist" />
		</td>
		<td colspan="3">
			<input type="button" name="query" value=<fmt:message key="faultCircuit.selected" /> onclick="fun_showMyBox(this);" class="clsbtn2">
			<input type="text" name="regionname" value="${regionname}" size="70" disabled="true" Class="clstext"/>
			<input type="hidden" name="regionlist" value="${regionlist}" />
		</td>
		</tr>
	</table>
	<p id="innerHtml" style="display:none">
	<table border="0" width="98%" cellspacing="1" class="table_show" cellPadding="0">
		<tr>
			<td class="content">
				<logic:iterate id="dictItemXML" indexId="index" name="REGIONLIST" type="com.boco.eoms.commons.system.dict.model.DictItemXML">
					<input type='checkbox' name='my_check' value='<bean:write name="dictItemXML" property="id" scope="page"/>,<bean:write name="dictItemXML" property="name" scope="page"/>'>
					<bean:write name="dictItemXML" property="name" scope="page"/></input>
					<c:choose><c:when test="${(index+1)%7==0}"></p></c:when></c:choose>
				</logic:iterate>
			</td>
		</tr>
		<tr >
			<td  align="center" colspan="8">
				<input type="button" property="ok" class="btn" value=<fmt:message key="button.confim"/> onclick="fun_setValue('regionname', 'regionlist');" >
			</td>
		</tr>
	</table>
	</p>
	
	<table><caption><bean:message key="tawRmDutyEvent.addEventSub" /></caption></table>
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<tr>
			<td class="label">
				 <bean:message key="tawRmDutyEventSub.happentime" />
			</td>
			<td >
				<input type="text" name="subHappentime" size="20" VALUE='${tawRmDutyEventForm.subHappentime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td class="label">
				 <bean:message key="tawRmDutyEventSub.worksheetid" />
			</td>
			<td >
			<html:text property="subWorksheetid" styleId="subWorksheetid" value='${tawRmDutyEventForm.subWorksheetid}' styleClass="text medium" /> 
			</td>
		</tr> 
		<tr class="tr_show">
			<td class="label">
				 <bean:message key="tawRmDutyEventSub.content" />
			</td>
			<td COLSPAN="3">
				<textarea rows="4" style="width: 100%" name="subContent" value='${tawRmDutyEventForm.subContent}'>${tawRmDutyEventForm.subContent}</textarea>
			</td>
		</tr>
		<tr>
			<td COLSPAN="28" > 
				<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateForm();" />
			</td>
		</tr>
	</table>
</html:form>

<display:table name="TawRmDutyEventList" cellspacing="0" cellpadding="0"
    id="TawRmDutyEventList" class="table TawRmDutyEventList"
    sort="list" partialList="true" size="resultSize"
    requestURI="${app}/duty/yndutyevent.do?method=add"  >

    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.faultType" >
    	<span onclick="showSubList('<bean:write name="TawRmDutyEventList" property="id"/>', this, 'TawRmDutyEventList');">
			<img src="${app}/duty/images/plus.gif">
		</span>
    	<eoms:dict key="dict-duty" dictId="faultType" itemId="${TawRmDutyEventList.faultType}" beanId="id2nameXML" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.flag" >
    	<eoms:dict key="dict-duty" dictId="faultflag" itemId="${TawRmDutyEventList.flag}" beanId="id2nameXML" />
    </display:column>
  
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.deptid" >
  		<eoms:id2nameDB id="${TawRmDutyEventList.deptid}" beanId="tawSystemDeptDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputuser" >
    	<eoms:id2nameDB id="${TawRmDutyEventList.inputuser}" beanId="tawSystemUserDao" />
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.eventtitle" >
    	<html:link title="${eoms:a2u('打开管理界面!')}" href="${app}/duty/yndutyevent.do?method=edit" paramId="id" paramProperty="id" paramName="TawRmDutyEventList">
     		<bean:write name="TawRmDutyEventList" property="eventtitle"/>
       	</html:link>
    </display:column>  
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.sheetid" >
    	<a href="#" onclick='return faultsheet("<bean:write name="TawRmDutyEventList" property="sheetid"/>")'>
    		<bean:write name="TawRmDutyEventList" property="sheetid"/>
       	</a>
    </display:column>        
						
    <display:column property="beginTime" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.beginTime"/>
    
    <display:column sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.complateFlag" >
  		<eoms:dict key="dict-duty" dictId="complateFlag" itemId="${TawRmDutyEventList.complateFlag}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="inputdate" sortable="true" headerClass="sortable" titleKey="tawRmDutyEvent.inputdate"/>
    
    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
</display:table>
 <div style="display: none">
 	<iframe name="subListIframe" id="subListIframe" src="" width="400" height="300"></iframe>
</div>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
