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

var subLastBeginTime; // 最后一个子过程的开始时间
var subEventNum =0; // 子过程数量
var faultCommontCount = ${tawRmDutyEventForm.faultCommontCount};
var faultEquipmentCount = ${tawRmDutyEventForm.faultEquipmentCount};
var faultCircuitCount = ${tawRmDutyEventForm.faultCircuitCount};
var faultReportSheet = ${tawRmDutyEventForm.faultReportSheet};

function validateEventForm(){
   	var frm = document.forms["tawRmDutyEventForm"];
   	var complateFlag = frm.complateFlag.value;
   	var date1 = frm.beginTime.value;
   	var date2 = frm.endtime.value;

   	if(complateFlag=="") {
   		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}",
   		"${eoms:a2u('完成状态不能为空，请选择！')}");
   		frm.complateFlag.focus();
 		return false;
 	}

   	// 事件记录选择已完成,子过程至少有2条
   	if(complateFlag==1&&subEventNum<2) {
   		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}",
   		"${eoms:a2u('事件记录为完成时,子过程不能少于2条,请增加子过程！')}");
 		return false;
 	}

 	if(complateFlag==1&&date2=="") {
 		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}",
   		"${eoms:a2u('事件记录为完成，完成时间不能为空，请填写！')}");
   		frm.endtime.focus();
 		return false;
 	}

	if(date2!="" && dateCompare(date1,date2)) { 
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('结束时间要大于开始时间！')}");
		document.getElementById("endtime").focus();
		return false;
	} 

	if(dateCompare(date1,getDateStr(0))) {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('发生时间不能是将来时！')}");
		document.getElementById("subHappentime").focus();
		return false;
	}

   	if (frm.eventtitle.value == "") {
 		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请填写事件主题！')}");
		frm.eventtitle.focus();
 		return false;
 	}

 	if (frm.faultType.value == "") {
 		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}","${eoms:a2u('请选择事件类别！')}");
		frm.faultType.focus();
 		return false;
 	}
 	
 	if (frm.flag.value == "") {
 		alert("${eoms:a2u('请重要等级！')}");
		frm.flag.focus();
 		return false;
 	}
 	
 	// 是否生成故障记录
 	var typeName = frm.faultType.options[frm.faultType.selectedIndex].innerText;
 	
 	if (complateFlag==1&&frm.flag.value == 3&&(typeName.search("${eoms:a2u('故障')}")> -1)) {
		if (faultCommontCount == 0) {
			alert(${eoms:a2u('请生成通用故障记录后该事件才可以结束！！')});
			return false;										
		} else if(faultReportSheet=="") { // 判断重大故障上报工单号是否为空
			alert(${eoms:a2u('请派发紧急故障工单后(紧急故障工单编号不能为空),该事件才可以结束！！')});
			return false;
		}				
		if (faultEquipmentCount == 0 && faultCircuitCount == 0 && typeName.search("${eoms:a2u('传输')}") > -1) {
			alert(${eoms:a2u('请生成传输故障设备/线路故障记录后该事件才可以结束！！')});
			return false;	 
		}
				
	}
 	
   	frm.submit();
 }	 
 
 function validateSubForm() {
	var form = document.getElementById("tawRmDutyEventSubForm");

	var date2 = form.happentime.value // 开始时间
	if(dateCompare(subLastBeginTime,date2)) {
		Ext.MessageBox.alert("${eoms:a2u('监控事件子过程')}"	,
		"${eoms:a2u('新增子过程时间要大于最后一个子过程时间！')}");
		form["happentime"].focus();
		return false;
	} 

	var date3 = document.getElementsByName("endtime")[0].value; // 结束时间
	if(date3!=""&&dateCompare(date2,date3)) {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('开始时间要小于结束时间！')}");
		document.getElementById("endtime").focus();
		return false;
	} 

	if(dateCompare(date2,getDateStr(0))) {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('子过程发生时间不能是将来时！')}");
		form["happentime"].focus();
		return false;
	} 
			
	if(form["happentime"].value == "") {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('请输入开始时间！')}");
		form["happentime"].focus();
		return false;
	}
			
	if(form["content"].value == "") {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('请输入子过程内容！')}");
		form["content"].focus();
		return false;
	}
			
	form.submit();
}

function deleteEvent(id) {	
	if (confirm("${eoms:a2u('您确认要删除故障事件吗？')}")) {
 		var url='${app}/duty/dutyevent.do?method=deleted&id=' + id;
		window.location.href=url;
 	}
 			
 	return false;
}

function updateSub(id) {

	var form = document.getElementById("updateSubForm");
	var date1 = document.getElementById("happentime" + id).value; // 开始时间
	// -----------------开始时间不能是将来时------------BEGIN
	if(dateCompare(date1,getDateStr(0))) {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('子过程发生时间不能是将来时！')}");
		document.getElementById("happentime" + id).focus();
		return false;
	} 

	// -----------------开始时间和结束时间比较------------BEGIN
	var date2 = document.getElementsByName("endtime")[0].value; // 结束时间
	if(date2!=""&&dateCompare(date1,date2)) {
		Ext.MessageBox.alert("${eoms:a2u('监控事件记录')}"	,
		"${eoms:a2u('结束时间要大于开始时间！')}");
		document.getElementById("happentime" + id).focus();
		return false;
	} 
	form["id"].value = id;
 	form["happentime"].value = document.getElementById("happentime" + id).value;
 	form["content"].value  = document.getElementById("content" + id).value;
 	form["worksheetid"].value  = document.getElementById("worksheetid" + id).value;
 			
 	form.submit();
}

function deleteSub(id) {
	if("${sessionform.isDutyMaster}") {
		Ext.MessageBox.alert("${eoms:a2u('监控事件子过程')}"	,
		"${eoms:a2u('你没有删除权限，只有值班长有删除权限！')}");
		return false;
	}

	if (confirm("${eoms:a2u('您确认要删除子过程吗？')}")) {
 		var url='${app}/duty/dutyeventsub.do?method=deletedSub&id=' + id;
		window.location.href=url;
 	}
 			
 	return false;
}	

/**
 * 生成故障记录表
 */
function openFault(flag,type) {
	if(flag==1) {
		if(type=="faultCircuit"&&faultEquipmentCount>0) { // 已经生成过设备故障
			Ext.MessageBox.alert("${eoms:a2u('生成故障记录')}"	,
			"${eoms:a2u('已经生成过设备故障，不能再新增！')}");
			return false;
		}
		if(type=="faultEquipment"&&faultCircuitCount>0) { // 已经生成过线路故障
			Ext.MessageBox.alert("${eoms:a2u('生成故障记录')}"	,
			"${eoms:a2u('已经生成过线路故障，不能再新增！')}");
			return false;
		}
		var url= '${app}/duty/' + type + 's.do?method=add&recordPerId='+'${tawRmDutyEventForm.id}';
		window.navigate(url);
	} else if(flag==2) {
		var id;
		if(type=="faultCommont") {
			id = "${tawRmDutyEventForm.faultCommontId}";
		}
		if(type=="faultEquipment") {
			id = "${tawRmDutyEventForm.faultEquipmentId}";
		}
		if(type=="faultCircuit") {
			id = "${tawRmDutyEventForm.faultCircuitId}";
		}
		var url= '${app}/duty/' + type + 's.do?method=edit&id=' + id;
		window.navigate(url);
	}
}
</script>
<table>
	<caption>${eoms:a2u('管理事件记录')}</caption>
</table>
 
<html:form action="/dutyevent.do?method=save" method="post" styleId="tawRmDutyEventForm">
	<html:hidden name="tawRmDutyEventForm" property="id" />
		
	<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		<tr>
			<td class="label">
				 <bean:message key="tawRmDutyEvent.faultType" />
			</td>
			<td >
				<eoms:dict key="dict-duty" dictId="faultType" beanId="selectXML" defaultId="${tawRmDutyEventForm.faultType}" selectId="faultType"/>
			</td>
			<td class="label">
				 <bean:message key="tawRmDutyEvent.flag" />
			</td>
			<td >
			<eoms:dict key="dict-duty" dictId="faultflag" beanId="selectXML"  defaultId="${tawRmDutyEventForm.flag}" isQuery="false"  selectId="flag"/>
		 	</td>
		</tr> 
		<tr>
			<td class="label">
					 <bean:message key="tawRmDutyEvent.beginTime" />
			</td>
			<td >
				<input type="text" name="beginTime" size="20" value='${tawRmDutyEventForm.beginTime}' onfocus='alert("${eoms:a2u('该时间参考第一个字过程开始时间!')}");' readonly="readonly">
			</td>
			<td class="label">
					<bean:message key="tawRmDutyEvent.endtime" />
			</td>
			<td >
					<input type="text" name="endtime" value='${tawRmDutyEventForm.endtime}' size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
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
			<input type="text" name="regionname" value="${tawRmDutyEventForm.regionname}" size="70" disabled="true" Class="clstext"/>
			<input type="hidden" name="regionlist" value="${tawRmDutyEventForm.regionlist}" />
		</td>
	</tr>
	<tr height="25">
		<td align="center" class="clsfth" width="15%">
			<fmt:message key="tawRmDutyEvent.complateFlag" />
		</td>
		<td width="35%" >
			<eoms:dict key="dict-duty" dictId="complateFlag"
						defaultId="${tawRmDutyEventForm.complateFlag}" beanId="selectXML"
						selectId="complateFlag" />
		</td>
		<td colspan="2">
			<c:choose><c:when test="${tawRmDutyEventForm.deptid==sessionform.deptid&&tawRmDutyEventForm.complateFlag==2}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateEventForm();" />
				&nbsp;&nbsp;
				<input type="button"  class="btn" value="<fmt:message key="button.delete"/>" onclick='return deleteEvent("${tawRmDutyEventForm.id}");'/>
			</c:when></c:choose>
		</td>
	</tr>
	<!-- 167为云南 省公司监控室的机房ID号，各省根据自己情况定义或者取消条件限制 -->
	<c:choose><c:when test="${tawRmDutyEventForm.deptid==167}">
		<tr class="tr_show" height="25">
			<td align="center" class="clsfth">${eoms:a2u('操作')}</td>
			<td colspan="3">&nbsp;
				<c:choose><c:when test="${tawRmDutyEventForm.faultCommontCount==0}">
					<input type=button id="createFaultButton" class="btn" value="${eoms:a2u('生成通用故障记录')}" onclick="openFault('1','faultCommont');"><br>&nbsp;
				</c:when><c:otherwise>
					<input type=button id="createFaultButton" class="btn" value="${eoms:a2u('编辑通用故障记录')}" onclick="openFault('2','faultCommont');"><br>&nbsp;
				</c:otherwise></c:choose>
				<c:choose><c:when test="${tawRmDutyEventForm.faultCircuitCount==0}">
					<input type=button id="createFaultCircuitButton" class="btn" value="${eoms:a2u('生成传输线路故障记录')}" onclick="openFault('1','faultCircuit');"><br>&nbsp;
				</c:when><c:otherwise>
					<input type=button id="createFaultCircuitButton" class="btn" value="${eoms:a2u('编辑传输线路故障记录')}" onclick="openFault('1','faultCircuit');"><br>&nbsp;
				</c:otherwise></c:choose>
				<c:choose><c:when test="${tawRmDutyEventForm.faultEquipmentCount==0}">
					<input type=button id="createFaultEquipmentButton" class="btn" value="${eoms:a2u('生成传输设备故障记录')}" onclick="openFault('1','faultEquipment');"><br>&nbsp;
				</c:when><c:otherwise>
					<input type=button id="createFaultEquipmentButton" class="btn" value="${eoms:a2u('编辑传输设备故障记录')}" onclick="openFault('1','faultEquipment');"><br>&nbsp;
				</c:otherwise></c:choose>
			</td>
		</tr>
	</c:when></c:choose>		
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
				<input type="button" property="ok" value=<fmt:message key="button.confim"/> onclick="fun_setValue('regionname', 'regionlist');" >
			</td>
		</tr>
	</table>
	</p>
</html:form>

<br>
<table cellpadding="0" class="table TawRmDutyEventSubList" cellspacing="0" id="TawRmDutyEventSubList">
	<thead><tr>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.recordman" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.happentime" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.content" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.worksheetid" /></th>
		<th class="sortable"><fmt:message key="tawRmDutyEventSub.recordtime" /></th>
		<th class="sortable">${eoms:a2u('操作')}</th>
	</tr></thead>
<logic:iterate id="TawRmDutyEventSubList" name="TawRmDutyEventSubList" type="com.boco.eoms.duty.model.TawRmDutyEventSub">
	<tr class="tr_show">
		<td><eoms:id2nameDB id="${TawRmDutyEventSubList.recordman}" beanId="tawSystemUserDao" /></td>
		<td>
			<input type="text" name="happentime" id="happentime${TawRmDutyEventSubList.id}" size="20" VALUE='${TawRmDutyEventSubList.happentime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
		</td>
		<td>
			<textarea rows="4" id="content${TawRmDutyEventSubList.id}" style="width: 100%" align="left" title='<fmt:message key="tawRmDutyEventSub.content" />'><bean:write name="TawRmDutyEventSubList" property="content" /></textarea>
		</td>
		<td>
			<input type="text" value="${TawRmDutyEventSubList.worksheetid}" id="worksheetid${TawRmDutyEventSubList.id}">
			<a href="#" onclick='return faultsheet("<bean:write name="TawRmDutyEventSubList" property="worksheetid" />")'><br>
				${eoms:a2u('查看工单信息')}
			</a> 
       	</a>
		</td>
		<td><bean:write name="TawRmDutyEventSubList" property="recordtime" /></td>
		<td>
			<c:choose><c:when test="${TawRmDutyEventSubList.recordman==sessionform.userid&&tawRmDutyEventForm.complateFlag==2}">
			<input type="button"  class="btn" value="<fmt:message key="button.save"/>" onclick="updateSub('${TawRmDutyEventSubList.id}');"/><br>
			<input type="button"  class="btn" value="<fmt:message key="button.delete"/>" onclick="deleteSub('${TawRmDutyEventSubList.id}');"/> 			
			</c:when></c:choose>
		</td>
	</tr>
	<script language="javascript">subLastBeginTime='${TawRmDutyEventSubList.happentime}'</script>
	<script language="javascript">subEventNum= subEventNum+1;</script>
</logic:iterate>
</table>
<br>
<html:form action="/dutyeventsub.do?method=saveSub" method="post" styleId="tawRmDutyEventSubForm">
	<html:hidden property="eventid" value="${tawRmDutyEventForm.id}" />
	<table><caption><bean:message key="tawRmDutyEvent.addEventSub" /></caption></table>
	<table border="0" cellspacing="1" cellpadding="1" class="listTable">
		<tr>
			<td class="label">
				 <bean:message key="tawRmDutyEventSub.happentime" />
			</td>
			<td >
				<input type="text" name="happentime" size="20" VALUE='${tawRmDutyEventSub.happentime}' onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true"  class="text">
			</td>
			<td class="label">
				 <bean:message key="tawRmDutyEventSub.worksheetid" />
			</td>
			<td >
			<html:text property="worksheetid" styleId="subWorksheetid" value='${tawRmDutyEventSub.worksheetid}' styleClass="text medium" /> 
			</td>
		</tr> 
		<tr class="tr_show">
			<td class="label">
				 <bean:message key="tawRmDutyEventSub.content" />
			</td>
			<td COLSPAN="3">
				<textarea rows="4" style="width: 100%" name="content" value='${tawRmDutyEventSub.content}'></textarea>
			</td>
		</tr>
		<tr>
			<td COLSPAN="28" > 
				<c:choose><c:when test="${tawRmDutyEventForm.complateFlag==2}">
				<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick="validateSubForm();" />
				</c:when></c:choose>
			</td>
		</tr>
	</table>
</html:form>
<div style="display: none">
	<html:form action="/dutyeventsub.do?method=saveSub" styleId="updateSubForm" method="post" >
		<input type="hidden" name="id" value="">
		<input type="hidden" name="eventid" value="">
		<input type="hidden" name="happentime" value="">
		<input type="hidden" name="content" value="">
		<textarea rows="" cols="" name="worksheetid"></textarea>
	</html:form>
</div>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
