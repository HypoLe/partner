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
		//document.getElementById("status").value="";	        // 工单状态
	}
</script>

<div id="sheetform">
			<html:form action="/pnrTransferNewPrecheck.do?method=listInvolvedFinishedProcessInstances"
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
							工单名称
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
					</tr>
			<!--  	<tr>
						<td class="label">
							工单状态
						</td>
						<td colspan="3">
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="archived" ${wsStatus == "archived" ? 'selected="selected"':'' }>
									已归档
								</option>
								<option value="delete" ${wsStatus == "delete" ? 'selected="selected"':'' }>
									已删除
								</option>
							</select>
						</td>
					</tr> -->	
			
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
      

<bean:define id="url" value="pnrTransferNewPrecheck.do"/>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="false" requestURI="pnrTransferNewPrecheck.do"
		sort="list" size="total" partialList="true" >
   		
   		  <display:column sortable="true"
			headerClass="sortable" title="工单号">
			
				<c:choose>
			<c:when test="${taskList.statusName != '已删除'}">
			<a href="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
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
			<display:column sortable="false" headerClass="sortable" title="线路级别">
				<eoms:id2nameDB id="${taskList.workType}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="false" property="workorderTypeName" headerClass="sortable" title="场景"/>
		
			<display:column sortable="false" property="subTypeName" headerClass="sortable" title="子场景"/>
			
			<display:column sortable="false" headerClass="sortable" title="关键字">
				<eoms:id2nameDB id="${taskList.keyWord}" beanId="ItawSystemDictTypeDao" />
			</display:column>
				<display:column property="applicationTime" sortable="true"	headerClass="sortable" title="申请提交时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="紧急程度">
				<eoms:id2nameDB id="${taskList.workOrderDegree}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="true" property="projectAmount" headerClass="sortable" title="项目金额"/>
			<display:column sortable="false" headerClass="sortable" title="工单状态">
				${taskList.statusName}
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