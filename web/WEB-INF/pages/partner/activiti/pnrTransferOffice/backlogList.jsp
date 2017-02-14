<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<base target="_self" />
		<%@ include file="/common/header_eoms_form.jsp"%>
		<script language="javaScript" type="text/javascript"
			src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		
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
			<html:form action="/pnrTransferOffice.do?method=listBacklog"
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
												<!-- <option value="epibolyAudit" ${wsStatus == "epibolyAudit" ? 'selected="selected"':'' }>
													外包质检
												</option>
												<option value="transferAudit" ${wsStatus == "transferAudit" ? 'selected="selected"':'' }>
													传输局质检
												</option>
												<option value="audit" ${wsStatus == "audit" ? 'selected="selected"':'' }>
													审核
												</option> -->
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



		<bean:define id="url" value="pnrTransferOffice.do" />
		<c:if test="${carApprove ne 'yes'}">
			<c:set var="export" value="false"></c:set>
		</c:if>
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="${export}" requestURI="pnrTransferOffice.do" sort="list"
			size="total" partialList="true">

			<display:column sortable="true" headerClass="sortable" title="工单号">
				<a
					href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=todo&taskId=${taskList.taskId}"
					 title="回复"> ${taskList.processInstanceId} </a>
			</display:column>
			<display:column property="theme" sortable="true"
				headerClass="sortable" title="主题" maxLength="15" />

			<display:column sortable="true" headerClass="sortable" title="工单生成人">
				<eoms:id2nameDB id="${taskList.oneInitiator}"
					beanId="tawSystemUserDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="所属部门">
				<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao" />
			</display:column>

			<display:column property="sendTime" sortable="true"
				headerClass="sortable" title="派单时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="true" headerClass="sortable" title="当前状态">
				<c:choose>
					<c:when test="${taskList.statusName eq '新建派发'}">
			待处理
			</c:when>
					<c:otherwise>
			${taskList.statusName}
			</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="处理">
				<a
					href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=todo&taskId=${taskList.taskId}"
					title="回复"> 处理 </a>
				<c:if test="${taskList.taskDefKey eq 'transferTask'}">
					&nbsp;&nbsp;
	           		 <a
						href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=rollback&handle=transferTask&taskId=${taskList.taskId}&initiator=${taskList.oneInitiator}&processInstanceId=${taskList.processInstanceId}"
						title="回退"> 回退 </a>
				</c:if>
				<c:if test="${taskList.taskDefKey eq 'epibolyTask'}">
					&nbsp;&nbsp;
	           		 <a
						href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=rollback&handle=epibolyTask&taskId=${taskList.taskId}&initiator=${taskList.secondInitiator}&processInstanceId=${taskList.processInstanceId}"
						title="回退"> 回退 </a>
				</c:if>
				<c:if test="${taskList.taskDefKey eq 'transferHandle'}">
					&nbsp;&nbsp;
	           		 <a
						href="${app}/activiti/pnrTransferOffice/pnrTransferOffice.do?method=rollback&handle=transferHandle&taskId=${taskList.taskId}&initiator=${taskList.initiator}&processInstanceId=${taskList.processInstanceId}"
						title="回退"> 回退 </a>
				</c:if>
			</display:column>

			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		<%@ include file="/common/footer_eoms.jsp"%>