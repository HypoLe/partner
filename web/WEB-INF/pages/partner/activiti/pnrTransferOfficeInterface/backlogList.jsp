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
	//重置
	function newReset(){
		document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
		document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
		document.getElementById("wsNum").value="";		//工单号
		document.getElementById("wsTitle").value="";	        // 工单主题
		document.getElementById("status").value="";	        // 当前状态
	}
</script>

	<div id="sheetform">
			<html:form action="/pnrTransferInterface.do?method=listBacklog"
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
								<option value="newTask" ${wsStatus == "newTask" ? 'selected="selected"':'' }>
									传输分局
								</option>
								<option value="transferTask" ${wsStatus == "transferTask" ? 'selected="selected"':'' }>
									外包公司
								</option>
								<option value="transferHandle" ${wsStatus == "transferHandle" ? 'selected="selected"':'' }>
									施工队
								</option>
								<option value="transferAudit" ${wsStatus == "transferAudit" ? 'selected="selected"':'' }>
									外包质检
								</option>
								<option value="audit" ${wsStatus == "audit" ? 'selected="selected"':'' }>
									传输分局审核
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



<bean:define id="url" value="pnrTransferInterface.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="false"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferInterface.do"
		sort="list" size="total" partialList="true">
   		
   		  <display:column  sortable="true" headerClass="sortable" title="工单号">			
				 <a href="${app}/activiti/pnrTransferInterface/pnrTransferInterface.do?method=todo&taskId=${taskList.taskId}" target="_blank" title="回复">
				${taskList.processInstanceId}
			     </a>					     
		</display:column>	
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="主题" maxLength="15"/>

        <display:column  sortable="true"
            headerClass="sortable" title="工单生成人">
            <eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/> 
        </display:column>
        <display:column  sortable="true"
            headerClass="sortable" title="所属部门">
            <eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/> 
        </display:column>
          <display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
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
					<a href="${app}/activiti/pnrTransferInterface/pnrTransferInterface.do?method=todo&taskId=${taskList.taskId}" title="回复">
					 处理
					</a>
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