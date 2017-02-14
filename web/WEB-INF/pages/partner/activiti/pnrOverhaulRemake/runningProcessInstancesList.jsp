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
	
	function deleteData(id){
		if(window.confirm("确定删除?")){
			var condition=document.getElementById("condition").value;
			window.location="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=removeProcessInstance&processInstanceId="+id+condition;
		}
	}
</script>



<div id="sheetform">
			<html:form action="/pnrOverhaulRemake.do?method=listRunningProcessInstances"
				styleId="theform">
				<input type="hidden" id="condition" name="condition" value="${condition}" />
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
						<td class="text">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
							<!-- 工单号  -->
						<td class="label">
							工单号
						</td>
						<td>
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
					</tr>

					<tr>
					
						<!-- 工单主题 -->
						<td class="label">
							工单名称
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
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
								<option value="cityLineExamine" ${wsStatus == "cityLineExamine" ? 'selected="selected"':'' }>
									市线路发起
								</option>
								<option value="cityLineDirectorAudit" ${wsStatus == "cityLineDirectorAudit" ? 'selected="selected"':'' }>
									市线路主任审核
								</option>
								<option value="cityManageExamine" ${wsStatus == "cityManageExamine" ? 'selected="selected"':'' }>
									市运维主管审核
								</option>
								<option value="cityManageDirectorAudit" ${wsStatus == "cityManageDirectorAudit" ? 'selected="selected"':'' }>
									市运维主任审核
								</option>								
								<option value="cityViceAudit" ${wsStatus == "cityViceAudit" ? 'selected="selected"':'' }>
									市副总审核
								</option>
								<option value="provinceLineExamine" ${wsStatus == "provinceLineExamine" ? 'selected="selected"':'' }>
									省线路主管审核
								</option>
								<option value="provinceLineViceAudit" ${wsStatus == "provinceLineViceAudit" ? 'selected="selected"':'' }>
									省线路分管经理审核
								</option>
								<option value="provinceManageExamine" ${wsStatus == "provinceManageExamine" ? 'selected="selected"':'' }>
									省运维主管审核
								</option>
								<option value="provinceManageViceAudit" ${wsStatus == "provinceManageViceAudit" ? 'selected="selected"':'' }>
									省运维分管经理审批
								</option>
								<option value="waitingCallInterface" ${wsStatus == "waitingCallInterface" ? 'selected="selected"':'' }>
									省公司审批通过-等待商城
								</option>
								<option value="sendOrder" ${wsStatus == "sendOrder" ? 'selected="selected"':'' }>
									代维公司转派
								</option>
								<option value="worker" ${wsStatus == "worker" ? 'selected="selected"':'' }>
									施工队现场处理
								</option>
								<option value="daiweiAudit" ${wsStatus == "daiweiAudit" ? 'selected="selected"':'' }>
									工单发起人审核
								</option>
								<option value="orderAudit" ${wsStatus == "orderAudit" ? 'selected="selected"':'' }>
									市运维主管抽查
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
      
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrOverhaulRemake.do"
		sort="list" size="total" partialList="true" >
   			<display:column sortable="true"
			headerClass="sortable" title="工单号">
			<c:choose>
				<c:when test="${taskList.statusName != '已删除'}">
				<a href="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
					${taskList.processInstanceId}
				</a>
				</c:when>
			<c:otherwise>${taskList.processInstanceId}</c:otherwise>
				
			</c:choose>
		
			</display:column>
		<display:column property="sheetId" sortable="true" headerClass="sortable" title="工单编码" />	
		<display:column property="theme" sortable="true"
			headerClass="sortable" title="工单名称" maxLength="15"/>

		<display:column property="sendTime" sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column property="distributedInterfaceTime" sortable="true"	headerClass="sortable" title="省公司批复时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" />
        <display:column property="statusName" sortable="true"
            headerClass="sortable" title="当前状态"/>
            
            
		<display:column sortable="true"
			headerClass="sortable" title="处理">
			<c:choose>
				<c:when test="${taskList.taskDefKey eq 'cityLineExamine' && taskList.statusName ne '已删除' }">
					<a href="javascript:deleteData(${taskList.processInstanceId});">删除</a>
				</c:when>
			<c:otherwise>--</c:otherwise>
				
			</c:choose>
		
		</display:column>

		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
	
	
<%@ include file="/common/footer_eoms.jsp"%>