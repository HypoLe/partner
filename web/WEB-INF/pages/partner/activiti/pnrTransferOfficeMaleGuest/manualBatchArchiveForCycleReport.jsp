<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<script type="text/javascript">
	var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突	
		//重置
		function newReset(){
			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
			document.getElementById("maleGuestNumber").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
		}
		</script>
		
		<!-- 批量回复按钮点击事件  -->	
		<script type="text/javascript">
		function batchReply(){
			var total = 0;
			var objs ="";
			var max =document.testform.Fruit.length;
			if(max == null || max == ''){
				if(eval("document.testform.Fruit.checked") == true){
					total += 1;
	          		objs+=document.testform.Fruit.value+";";
				}
			}else {
				  for (var idx = 0; idx < max; idx++) {
			        if (eval("document.testform.Fruit[" + idx + "].checked") == true) {
			          total += 1;
			          objs+=document.testform.Fruit[idx].value+";";
			        }
			      }
			}
	       if(total==0){
	       		//alert("您选择了 " + total + " 个选项！");
	       		alert("请选择要处理的工单！");
	       }else{
	       		//window.location.href ="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=doOneWorkOrderArchiving&ids="+objs;
	       		//document.getElementById("testform").submit();
	       		    jq.ajax({
	       		    	type:'POST',      
					    url:'${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=doManualBatchArchiveForCycleReport',      
					    type:'post',      
					    data:'ids='+objs,      
					    dataType:'text',
					    async : true, //默认为true 异步          
					    success:function(data, textStatus){
						    if(data.indexOf("true")>-1){ //判断返回的字符串中是否包含true
		                     	 alert("批量归档成功!");      
						      	 location.reload(true);   
	                        }else{
	                         	alert("error");
	                        }        			
					       
					    }   
					}); 
	       }
	 
		}
			
		</script>
		
<script type="text/javascript">
	function getAll(){ 
		var tit = document.getElementById("operAll");
		var inputs = document.testform.Fruit;
			if(tit.checked==true){
				inputs.checked =true;
			}else{
				inputs.checked = false;  
			}
			for(var i = 0; i < inputs.length; i++) {
			
				if(tit.checked == true)   { 
					inputs[i].checked = true;  
				}else{
					inputs[i].checked = false;  
				} 
			}
	}
 
 </script>
		
		

		<div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=manualBatchArchiveForCycleReport"
				styleId="theform">
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							开始时间
						</td>
						<td>
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${selectAttribute.beginTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label">
							结束时间
						</td>
						<td>
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${selectAttribute.endTime}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />
						</td>
						<td class="label">
							工单号
						</td>
						<td>
							<input type="text" name="maleGuestNumber" class="text" id="maleGuestNumber"
								value="${selectAttribute.maleGuestNumber}" />
						</td>
					</tr>

					<tr>
						<!-- 工单号  -->
						
						<!-- 工单主题 -->
						<td class="label">
							工单主题
						</td>
						<td>
							<input type="text" name="wsTitle" class="text" id="wsTitle"
								value="${selectAttribute.theme}" />
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
					
					<c:if test="${total!=0}">
						<input type="button" class="btn" value="批量归档" onclick="batchReply()" />
					</c:if>
					
				</div>
			</html:form>
		</div>

<bean:define id="url" value="pnrTransferOfficeMaleGuest.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="false"></c:set>
	</c:if>
	<form id="testform" name="testform" action="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=doManualBatchArchiveForCycleReport" method="post">	
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
		sort="list" size="total" partialList="true">
		
		<display:column 
			headerClass="sortable" title="<input type='checkbox' id='operAll' onclick='getAll()'  />">
				<input type="checkbox" name="Fruit"  value="${taskList.taskId},${taskList.processInstanceId},${taskList.twoInitiator},${taskList.applicationTime}" />
		</display:column>
   		
   		<display:column sortable="true" headerClass="sortable" title="工单号">		
			<a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
						${taskList.processInstanceId}
		    </a>
		</display:column>	
		<display:column sortable="true" headerClass="sortable" title="工单主题">
			${taskList.theme}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="任务号">
			${taskList.taskId}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所在环节">
			${taskList.statusName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="当前环节人">
			${taskList.oneInitiator}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="当前环节人">
			<eoms:id2nameDB id="${taskList.oneInitiator}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="派单时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" >
			<fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="报表编号">
			${taskList.barrierNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="报表提交人">
			<eoms:id2nameDB id="${taskList.twoInitiator}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="报表提交时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" >
			<fmt:formatDate value="${taskList.applicationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>

		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
</form>
	
<%@ include file="/common/footer_eoms.jsp"%>