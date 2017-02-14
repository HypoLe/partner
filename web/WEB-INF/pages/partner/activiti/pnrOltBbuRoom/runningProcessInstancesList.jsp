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
			window.location="${app}/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=removeProcessInstance&processInstanceId="+id+condition;
		}
	}
</script>

<script type="text/javascript">
	function checkChildSceneForDetails(processInstanceId){
		//alert(processInstanceId);
		
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkChildSceneForDetails&processInstanceId='+processInstanceId;
		var _sHeight = 300;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
		
	}
	 /**
	*  打开图片选择页面
	*/
	function selectPhoto(processInstanceId){	
		var url = '${app}/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=showCreateWorkPhoto&pid='+processInstanceId;
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

<div id="sheetform">
			<html:form action="/pnrOltBbuRoom.do?method=listRunningProcessInstances"
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

					<tr>
						<!-- 当前状态 -->
						<td class="label" style="width:10%">
							工单状态
						</td>
						<td style="width:20%" colspan="3">
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="">
									所有
								</option>
								<option value="need" ${wsStatus == "need" ? 'selected="selected"':'' }>
									区县公司发起
								</option>
								<option value="cityLineExamine" ${wsStatus == "cityLineExamine" ? 'selected="selected"':'' }>
									市线路主管审核
								</option>
								<option value="cityManageExamine" ${wsStatus == "cityManageExamine" ? 'selected="selected"':'' }>
									市运维主管审核
								</option>
								<option value="provinceLineExamine" ${wsStatus == "provinceLineExamine" ? 'selected="selected"':'' }>
									省线路主管审核
								</option>
								<option value="provinceManageExamine" ${wsStatus == "provinceManageExamine" ? 'selected="selected"':'' }>
									省运维主管审核
								</option>
								<option value="waitingCallInterface" ${wsStatus == "waitingCallInterface" ? 'selected="selected"':'' }>
									省公司审批通过-等待商城
								</option>
								<option value="sendOrder" ${wsStatus == "sendOrder" ? 'selected="selected"':'' }>
									派发代维
								</option>
								<option value="worker" ${wsStatus == "worker" ? 'selected="selected"':'' }>
									施工队施工回单
								</option>
								<option value="orderAudit" ${wsStatus == "orderAudit" ? 'selected="selected"':'' }>
									区县公司验收
								</option>
								<option value="daiweiAudit" ${wsStatus == "daiweiAudit" ? 'selected="selected"':'' }>
									市线路主管审批
								</option>
								<option value="cityManagefile" ${wsStatus == "cityManagefile" ? 'selected="selected"':'' }>
									市运维主管审核归档
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
		export="${export}" requestURI="pnrOltBbuRoom.do"
		sort="list" size="total" partialList="true" >
   			<display:column sortable="true"
			headerClass="sortable" title="工单号">
			<c:choose>
				<c:when test="${taskList.state != '1'}">
					<a href="${app}/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
					${taskList.processInstanceId}
					</a>
				</c:when>
			<c:otherwise>${taskList.processInstanceId}</c:otherwise>
				
			</c:choose>
		
			</display:column>
			
			<display:column property="sheetId" sortable="true" headerClass="sortable" title="工单编码" />
			<display:column property="theme" sortable="false" headerClass="sortable" title="工单名称" maxLength="8" />
				
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column property="jobOrderType" sortable="false" headerClass="sortable"  title="工单类型">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" title="事前照片">
				<a href="javascript:void(0);" onclick="selectPhoto(&quot;${taskList.processInstanceId}&quot;)">查看</a>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="主场景" maxLength="5" property="workOrderTypeName" />
			<display:column sortable="false" headerClass="sortable" title="子场景" maxLength="5" property="subTypeName" />
			<display:column sortable="false" headerClass="sortable" title="场景模板">
				<a href="javascript:void(0);" onclick="checkChildSceneForDetails(&quot;${taskList.processInstanceId}&quot;)">查看</a>
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="batch" title="批次">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="oltNumber" title="机房内OLT数量">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="bbuNumber" title="机房内BBU数量">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="totalUserNumber" title="总用户数">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="voiceUser" title="语音用户数">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="broadbandUser" title="宽带用户数">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="iptvUser" title="IPTV用户数">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="isNeedRodInvestment" title="是否需要杆路投资">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="isNeedCableInvestment" title="是否需要光缆投资">
			</display:column>
			
			
			<display:column sortable="false" headerClass="sortable" property="newBuiltRodLength" title="新建杆路长度（1000M以内）">
			</display:column>
			
			
			<display:column sortable="false" headerClass="sortable" property="newBuiltRodInvestment" title="新建杆路投资">
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="originalCableRoute" title="原光缆路由简述">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="newCableRoute" title="新光缆路由简述">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="newParagraph"   title="新建段落">	
			</display:column>
			
			<display:column sortable="false" headerClass="sortable" property="cableClothCoreNumber" title="光缆布放芯数">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="cableClothLength" title="光缆布放长度（5KM以内）">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="cableInvestment" title="光缆投资估算">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="lineTotalInvestment" title="线路总投资估算">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="boardDemand" title="设备板卡需求">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="lightModuleDemand" title="设备光模块需求">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="equipmentInvestment" title="设备类投资估算">
			</display:column>
			<display:column sortable="false" headerClass="sortable" property="statusName" title="工单状态">
			</display:column>
            
            
		<display:column sortable="true"
			headerClass="sortable" title="处理">
			<c:choose>
				<c:when test="${taskList.taskDefKey eq 'need' && taskList.state ne '1' }">
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