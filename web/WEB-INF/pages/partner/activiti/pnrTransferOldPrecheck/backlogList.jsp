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
			<html:form action="/pnrTransferOldPrecheck.do?method=listBacklog"
				styleId="theform">
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
							工单主题
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
					</tr>

					<tr>
						<!-- 当前状态 -->
						<td class="label">
							当前状态
						</td>
						<td colspan="3">
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="need" ${wsStatus == "need" ? 'selected="selected"':'' }>
									需求发起
								</option>
								<option value="program" ${wsStatus == "program" ? 'selected="selected"':'' }>
									方案制定
								</option>
								<option value="csjCheck" ${wsStatus == "csjCheck" ? 'selected="selected"':'' }>
									传输局审批
								</option>
								<option value="sgsCheck" ${wsStatus == "sgsCheck" ? 'selected="selected"':'' }>
									市公司审批
								</option>
								<option value="automation" ${wsStatus == "automation" ? 'selected="selected"':'' }>
									自动转派
								</option>
								<option value="sendOrder" ${wsStatus == "sendOrder" ? 'selected="selected"':'' }>
									工单转派
								</option>
								<option value="worker" ${wsStatus == "worker" ? 'selected="selected"':'' }>
									工单执行
								</option>
								<option value="daiweiAudit" ${wsStatus == "daiweiAudit" ? 'selected="selected"':'' }>
									代维审核
								</option>
								<option value="orderAudit" ${wsStatus == "orderAudit" ? 'selected="selected"':'' }>
									工单审核
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



		<bean:define id="url" value="pnrTransferOldPrecheck.do" />
		<c:if test="${carApprove ne 'yes'}">
			<c:set var="export" value="false"></c:set>
		</c:if>
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="${export}" requestURI="pnrTransferOldPrecheck.do" sort="list"
			size="total" partialList="true">

			<display:column sortable="true" headerClass="sortable" title="工单号">
				<a
					href="${app}/activiti/pnrTransferOldPrecheck/pnrTransferOldPrecheck.do?method=todo&taskId=${taskList.taskId}"
					target="_blank" title="回复"> ${taskList.processInstanceId} </a>
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

			<display:column property="applicationTime" sortable="true"
				headerClass="sortable" title="申请提交时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="true" headerClass="sortable" title="当前状态">
				<c:choose>
					<c:when test="${taskList.statusName eq '需求发起'}">
			待处理
			</c:when>
					<c:otherwise>
			${taskList.statusName}
			</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="处理">
				<a
					href="${app}/activiti/pnrTransferOldPrecheck/pnrTransferOldPrecheck.do?method=todo&taskId=${taskList.taskId}"
					title="回复"> 处理 </a>
				<c:if test="${taskList.taskDefKey eq 'worker'}">
					&nbsp;&nbsp;
	           		 <a
						href="${app}/activiti/pnrTransferOldPrecheck/pnrTransferOldPrecheck.do?method=workerRollback&handle=transferHandle&taskId=${taskList.taskId}&initiator=${taskList.initiator}&processInstanceId=${taskList.processInstanceId}&theme=${taskList.theme}"
						title="回退"> 回退 </a>
				</c:if>
			</display:column>

			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		<%@ include file="/common/footer_eoms.jsp"%>