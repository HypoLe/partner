<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
 Ext.onReady(function(){
  		// 初始的时候给区县默认值
		delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
							res = "[{"+result.responseText;
				}
				if(res.indexOf("<\/SCRIPT>")>0){
			  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
				}
				
				var json = eval(res);
				
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${country}";
					if(arrOptions[i]==country){
					obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
		
  });
		//重置
		function newReset(){
			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
			document.getElementById("region").value="";		// 地市
			document.getElementById("wsNum").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("country").value="";	        // 区县
			document.getElementById("resourceType").value="";	//资源类型       
			document.getElementById("questionType").value="";	    //问题类型    
			document.getElementById("specialty").value="";	 //专业      
		}
		// 地区、区县连动
function changeCity(con)
		{    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("[{")!=0){
								res = "[{"+result.responseText;
					}
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					
					var json = eval(res);
					
					var countyName = "country";
					var arrOptions = json[0].cb.split(",");
					var obj=$(countyName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						obj.options[j]=opt;
						j++;
						i++;
					}
										
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}	
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }		
     
</script>
	<div id="sheetform">
			<html:form action="/netResInspect.do?method=listRunningProcessInstances"
				styleId="theform">
				<input type="hidden" id="batchApprovalFlag" name="batchApprovalFlag" value="${batchApprovalFlag}" /><!-- 是否能进行批量处理标识 -->
				<input type="hidden" id="wsStatusFlag" name="wsStatusFlag" value="${wsStatus}" /><!-- 工单状态标识 -->
				<!-- <input type="hidden" name="pagesize" value="${pageSize}">  -->
				<input type="hidden" id="condition" name="condition" value="${condition}" />
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						<td class="label" style="width:10%">
							派单开始时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${startTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label" style="width:10%">
							派单结束时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
						
						<td class="label" style="width:10%">
							地市
						</td>
						 <td class="content" style="width:20%">
							<select name="region"  id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${city.areaid ==region}">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</logic:iterate>
						</select>
						 </td>
					</tr>

					<tr>
						<!-- 工单号  -->
						<td class="label" style="width:10%">
							工单号
						</td>
						<td style="width:20%">
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${wsNum}" />
						</td>
						<!-- 工单主题 -->
						<td class="label" style="width:10%">
							工单名称
						</td>
						<td style="width:20%">
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${wsTitle}" />
						</td>
						<!-- 当前状态 -->
						<td class="label" style="width:10%">
							区县
						</td>
						<td style="width:20%">
							<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
						</td>
					</tr>

                  
                  <tr>
					<td class="label" style="width:10%">资源类型</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="resourceType" id="resourceType" defaultValue="${resourceType}" initDicId="12811" alt="allowBlank:false" styleClass="select" />
					</td>
					<td class="label" style="width:10%">问题类型</td>
					<td class="content" style="width:20%">
						<eoms:comboBox name="questionType" id="questionType" defaultValue="${questionType}" initDicId="12803" alt="allowBlank:false" styleClass="select" />
					</td>
					 <td class="label" style="width:10%">专业</td>
					  <td class="content">
						  <eoms:comboBox name="specialty" id="specialty" defaultValue="${specialty}" initDicId="12801" alt="allowBlank:false" styleClass="select" />
					 </td>
				</tr>
				<tr>
					<td class="label" style="width:10%">环节</td>
					<td class="content"  style="width:20%">
						<select id='taskDefKey' name='taskDefKey'>
							<option value='0'>所有</option>
							<option value='siteCheck'>现场核实</option>
							<option value='lineProcess'>线路抢修流程/预检预修流程</option>
							<option value='defectTreatment'>工单处理</option>
							<option value='archive'>已归档</option>
						</select>
					</td>
				</tr>
				
					
				
			<tr>
				
				<td colspan="6" class="content">				
					<div  style="text-align:right;">
						设置每页显示条数
						<select id="pagesize" name="pagesize" style="width: 58px">
										<option value="20" ${pageSize== "20" ? 'selected="selected"':'' } >
											20
										</option>
										<option value="50" ${pageSize== "50" ? 'selected="selected"':'' } >
											50
										</option>
										<option value="100" ${pageSize== "100" ? 'selected="selected"':'' } >
											100
										</option><!--
										<option value="500" ${pageSize== "500" ? 'selected="selected"':'' } >
											500
										</option>
										<option value="1000" ${pageSize== "1000" ? 'selected="selected"':'' } >
											1000
										</option>
										<option value="5000" ${pageSize== "5000" ? 'selected="selected"':'' } >
											5000
										</option>
									--></select>
					</div>
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
<form id="testform" name="testform" action="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listRunningProcessInstances" method="post">	
		<display:table name="taskList" cellspacing="0" cellpadding="0"
			id="taskList" pagesize="${pageSize}" class="listTable taskList"
			export="false" requestURI="netResInspect.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="true" headerClass="sortable" title="工单号">
				<a href="${app}/activiti/netResInspect/netResInspect.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}">${taskList.processInstanceId} </a>
			</display:column>
			<display:column property="theme" sortable="false" headerClass="sortable" title="&nbsp;&nbsp;&nbsp;工单名称&nbsp;&nbsp;&nbsp;" maxLength="10" />
			<display:column property="reportedDate" sortable="true"	headerClass="sortable" title="派发时间"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column sortable="false" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${taskList.county}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="专业">
				<eoms:id2nameDB id="${taskList.specialty}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="资源类型">
				<eoms:id2nameDB id="${taskList.resourceType}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="问题类型">
				<eoms:id2nameDB id="${taskList.questionType}" beanId="ItawSystemDictTypeDao" />					
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="环节" property="taskDefKeyName" />
			<display:column sortable="false" headerClass="sortable" title="子流程" property="subProcessName" />
			<display:column sortable="false" headerClass="sortable" title="子流程号" property="subProcessInstanceId" />
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
<c:if test="${!empty taskList}">
	<eoms:excelExportToProcess modelName="com.boco.activiti.partner.process.po.NetResInspectModel" serviceBeanId="netResInspectService" title="待回复工单" queryFlag="listBacklog" processKey="netResInspect" flag="backlog">		
			<eoms:rowToProcess name="工单号" value="processInstanceId"/>
			<eoms:rowToProcess name="工单名称" value="theme"/>
			<eoms:rowToProcess name="派发时间" value="reportedDate"/>
			<eoms:rowToProcess name="地市" value="city" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="区县" value="county" dictDaoName="tawSystemAreaDao"/>
			<eoms:rowToProcess name="专业" value="specialty" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="资源类型" value="resourceType" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="问题类型" value="questionType" dictDaoName="ItawSystemDictTypeDao"/>
			<eoms:rowToProcess name="环节" value="taskDefKeyName" />
			<eoms:rowToProcess name="子流程" value="subProcessName" />
			<eoms:rowToProcess name="子流程号" value="subProcessInstanceId" />
	</eoms:excelExportToProcess>
</c:if>
<script type="text/javascript">
	function defaultOptions(){
		var taskDefKey='${taskDefKey}'
		if(taskDefKey!=''){
			document.getElementById("taskDefKey").value = '${taskDefKey}';
		}
	}
	defaultOptions();
</script>
<%@ include file="/common/footer_eoms.jsp"%>