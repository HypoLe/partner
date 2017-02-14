<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
function selectSheet(){
		var objName= document.getElementsByName("radio1");
		var string = '';
		 for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string = objName[i].value.trim();
                break;
                }
        } 
		window.returnValue=string;
		window.close();
	}
	function deleteData(id){
	if(window.confirm("确定删除?")){
		window.location="pnrTransferOffice.do?method=removeProcessInstance&processInstanceId="+id;
	}
	}
</script>
<script type="text/javascript">
	//重置
	function newReset(){
		document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
		document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
		document.getElementById("wsNum").value="";		// 工单号
		document.getElementById("wsTitle").value="";	        // 工单主题
		document.getElementById("status").value="";	        // 当前状态
	}
</script>
	<div id="sheetform">
			<html:form action="/pnrTransferOffice.do?method=listRunningProcessInstances"
				styleId="theform">
				<table class="formTable" style="width:100%">
				  <tr>
				    <td class="label" style="width:10%">开始时间</td>
				    <td class="content" style="width:20%">
					<input type="text" class="text" name="sheetAcceptLimit"
												readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
												onclick="popUpCalendar(this, this,null,null,null,null,-1)"
												alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />
					</td>
				    <td class="label" style="width:10%">结束时间</td>
				    <td class="content" style="width:20%">
					<input type="text" class="text" name="sheetCompleteLimit"
												readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
												onclick="popUpCalendar(this, this,null,null,null,null,-1)"
												alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
					</td>
				    <td class="label" style="width:10%">工单号</td>
				    <td class="content" style="width:20%">
					<input type="text" name="wsNum" class="text" id="wsNum"
												value="${wsNum}" />
					</td>
				  </tr>
				  <tr>
				    <td class="label" style="width:10%">工单主题</td>
				    <td style="width:20%">
					<input type="text" name="wsTitle" class="text" id="wsTitle"
												value="${wsTitle}" />
					</td>
				    <td class="label" style="width:10%">工单状态</td>
				    <td style="width:20%" colspan="3">
					<select id="status" name="status" class="text" 
												style="width: 150px;">
												<option value="">
													所有
												</option>
												<option value="newTask" ${wsStatus == "newTask" ? 'selected="selected"':'' }>
													派发工单
												</option>
												<option value="transferTask" ${wsStatus == "transferTask" ? 'selected="selected"':'' }>
													传输局
												</option>
												<option value="epibolyTask" ${wsStatus == "epibolyTask" ? 'selected="selected"':'' }>
													外包公司
												</option>
												<option value="transferHandle" ${wsStatus == "transferHandle" ? 'selected="selected"':'' }>
													施工队
												</option>
												<option value="acheck" ${wsStatus == "acheck" ? 'selected="selected"':'' }>
													一次核验
												</option>
												<option value="twoSpotChecks" ${wsStatus == "twoSpotChecks" ? 'selected="selected"':'' }>
													二次抽查
												</option>
												<option value="auditReport" ${wsStatus == "auditReport" ? 'selected="selected"':'' }>
													周期报表
												</option>
												<option value="archive" ${wsStatus == "archive" ? 'selected="selected"':'' }>
													已归档
												</option>
												<option value="delete" ${wsStatus == "delete" ? 'selected="selected"':'' }>
													已删除
												</option>
											</select>
					</td>
				  </tr>
				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
				</div>
			</html:form>
		</div>       

<bean:define id="url" value="pnrTransferOffice.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="false"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferOffice.do"
		sort="list" size="total" partialList="true" >

   		<display:column sortable="true"
			headerClass="sortable" title="工单号">
			<c:choose>
				<c:when test="${taskList.statusName != '已删除'}">
				<a href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
					${taskList.processInstanceId}
				</a>
				</c:when>
			<c:otherwise>${taskList.processInstanceId}</c:otherwise>
				
			</c:choose>
		
			</display:column>
			
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" maxLength="15"/>

		<display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column property="statusName" sortable="true"
            headerClass="sortable" title="当前状态"/>
		<display:column sortable="true"
			headerClass="sortable" title="处理">
			<c:choose>
				<c:when test="${taskList.taskDefKey eq 'transferTask' || taskList.taskDefKey eq 'transferHandle'||taskList.taskDefKey eq 'epibolyTask'||taskList.taskDefKey eq 'newTask'}">
				<a href="javascript:deleteData(${taskList.processInstanceId});">删除</a>
				</c:when>
			<c:otherwise>--</c:otherwise>
				
			</c:choose>
		
		</display:column>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
	</br>
	<c:if test="${carApprove eq 'yes'}">
		<input type="button" class="btn" value="确定" onclick="selectSheet();"/>
	</c:if>
<%@ include file="/common/footer_eoms.jsp"%>