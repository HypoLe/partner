<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	function readMsg(processInstanceId) {
		if(confirm("批阅后该条不再提醒，确定要批阅吗?")){
			Ext.Ajax.request({
				url:"${app}/partner/property/remind.do",
				params:{method:"readWorkOrderRemind",processInstanceId:processInstanceId},
				success:function(res,opt) {
					Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
				},
				failure:function(res,opt) {
					Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
				}
			});
		}
	}
	function selectAll(){
		var msgids = document.getElementsByName("msgids");
		var temp=msgids[0].checked;
		if(temp==null){
			for (i = 0 ; i < msgids.length; i ++) {
			cardNumberList[i].checked='checked';
			}
		}else{
			for (i = 0 ; i < msgids.length; i ++) {
			msgids[i].checked=!msgids[i].checked;
			}
		}
	}
	function readAllWorkOrderRemind(){
		var ids=document.getElementsByName("msgids");
		var idResult="";
		for(i=0;i<ids.length;i++){
			if(ids[i].checked){
				var idsStr=ids[i].value;
				idResult+=idsStr.toString()+";";
			}
		}
		if(idResult==""){
			alert("请选择要批阅的记录");
			return false;
		}else{
			if(confirm("确定要批阅吗?")){
				Ext.Ajax.request({
					url:"${app}/partner/property/remind.do",
					params:{method:"readAllWorkOrderRemind",id:idResult},
					success:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					},
					failure:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			}
		}
	}
	function res(){
		var formElement=document.getElementById("unreadForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
	}
</script>
<c:if test="${processDefinitionKey=='transferArteryPrecheckProcess'}">
	<div align="center"><b>提醒信息-干线预检预修待接口调用工单列表页面</div><br><br/>
</c:if>

<c:if test="${processDefinitionKey=='overHaulNewProcess'}">
	<div align="center"><b>提醒信息-大修改造待接口调用工单列表页面</div><br><br/>
</c:if>
<c:if test="${processDefinitionKey=='transferNewPrechechProcess'}">
	<div align="center"><b>提醒信息-本地网预检预修待接口调用工单列表页面</div><br><br/>
</c:if>

<div id="listQueryObject" style="border:1px solid #98c0f4;padding:5px;" >
	<form action="${app}/partner/property/remind.do?method=workOrderRemind" method="post" id="unreadForm">
		<input type="hidden" id="processDefinitionKey" name="processDefinitionKey" value="${processDefinitionKey}">
		<fieldset>
			<legend>快速查询</legend>
			<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							派单开始时间
						</td>
						<td class="content">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							派单结束时间
						</td>
						<td class="content">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
					</tr>

					<tr>
						<!-- 工单号  -->
						<td class="label">
							工单号
						</td>
						<td>
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
						<!-- 工单主题 -->
						<td class="label">
							工单名称
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
					</tr>

					
					
			

				</table>
			<table>
				<tr>
					<td>
					<input type="submit" class="btn" value="查询" />
					<input type="button"  class="btn"  id="reset" value="重置" onclick="res();">
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="remind.do"
		sort="list" size="total" partialList="true" >
   		  
   		<display:column  title="<input type='checkbox' onclick='javascript:selectAll();'>" >
         	<input type="checkbox" name="msgids" value="${taskList.processInstanceId}" >
    	</display:column>
   		  <display:column sortable="true"
			headerClass="sortable" title="工单号">
			
				<c:choose>
			<c:when test="${taskList.statusName != '已删除'}">
			<a href="${app}/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
			${taskList.processInstanceId}
			</a>
			</c:when>
			<c:otherwise>
				${taskList.processInstanceId}
			</c:otherwise>
			</c:choose>		
			</display:column>
		<display:column property="sheetId" sortable="true" headerClass="sortable" title="工单编码" />
		<display:column property="theme" sortable="false"
				headerClass="sortable" title="工单名称" maxLength="15" />				
		<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="工单状态">
				${taskList.statusName}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="线路类型">
				<eoms:id2nameDB id="${taskList.workType}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" property="workorderTypeName" headerClass="sortable" title="工单类型"/>
		
			<display:column sortable="false" property="subTypeName" headerClass="sortable" title="工单子类型"/>
			
			<display:column sortable="false" headerClass="sortable" title="关键字">
				<eoms:id2nameDB id="${taskList.keyWord}" beanId="ItawSystemDictTypeDao" />
			</display:column>
				<display:column property="applicationTime" sortable="true"	headerClass="sortable" title="申请提交时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="紧急程度">
				<eoms:id2nameDB id="${taskList.workOrderDegree}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="true" property="projectAmount" headerClass="sortable" title="项目金额"/>
			<display:column sortable="true" property="compensate" headerClass="sortable" title="赔补金额"/>
			<display:column sortable="true" property="calculateIncomeRatio" headerClass="sortable" title="收支比" />
			<display:column sortable="true"	headerClass="sortable" title="批阅" >
			 <a href="javascript:void(0)" onclick="readMsg('${taskList.processInstanceId}')">
				<img src="${app}/images/icons/edit.gif">
			</a>
		</display:column>
		
	</display:table>
	<c:if test="${!empty taskList }">
		<input type="button" value="批量批阅" onclick="readAllWorkOrderRemind()">
	</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
