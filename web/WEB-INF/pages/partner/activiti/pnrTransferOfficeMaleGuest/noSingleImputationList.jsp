<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

	<script type="text/javascript">
		//重置
		function newReset(){
			document.getElementById("sheetAcceptLimit").value="";            // 派单开始时间
			document.getElementById("sheetCompleteLimit").value="";		// 派单结束时间
			document.getElementById("maleGuestNumber").value="";		// 工单号
			document.getElementById("wsTitle").value="";	        // 工单主题
			document.getElementById("wsNum").value="";	        // 业务号码
			document.getElementById("status").value="";	        // 当前状态
			document.getElementById("installAddress").value="";	        // 地址
			document.getElementById("dept").value="";	        // 所属区域
			document.getElementById("person").value="";	        // 当前处理人
			document.getElementById("stationName").value="";	        // 局站名称
		}
		
		  //此刷新函数被弹出的子模态窗口调用
   function refresh(){
   		//var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=queryNoSingleImputationList";
	} 
		
		
		
		</script>
		
		<!-- 批量回复按钮点击事件  -->	
		<script type="text/javascript">
		function batchReply(){
			var total = 0;
			var max =document.testform.Fruit.length;
	        for (var idx = 0; idx < max; idx++) {
	        if (eval("document.testform.Fruit[" + idx + "].checked") == true) {
	          total += 1;
	        }
	       }
	       if(total==0){
	       	//alert("您选择了 " + total + " 个选项！");
	       	alert("请选择要处理的工单！");
	       }else if(total>50){
			alert("批量回复最多回复50条,当前已勾选"+total+"条!");
		   }else{
	      // 	alert("提交");
	       		document.getElementById("testform").submit();
	       }
	 
		}
			
		</script>
		
		<script type="text/javascript">
function getAll(){ 
var tit = document.getElementById("operAll");
var inputs = document.testform.Fruit;
for(var i = 0; i < inputs.length; i++) {

if(tit.checked == true)   { 
inputs[i].checked = true;  
 }else{
 inputs[i].checked = false;  
 } 
 }
 }
 
 </script>
 
 	<script type="text/javascript">
		
		function batchApprove(){
			if(window.confirm('确定要把选中的工单进行批量处理操作麽？')){
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
				       }else if(total>50){
							alert("批量处理最多处理50条,当前已勾选"+total+"条!");
					   }else{
				       		    $.ajax({
				       		    	type:'POST',      
								    url:'${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=batchApprove',      
								    type:'post',      
								    data:'taskids='+objs,      
								    dataType:'text',
								    async : true, //默认为true 异步          
								    success:function(data, textStatus){
									    if(data.indexOf("true")>-1){ //判断返回的字符串中是否包含true
					                     	 alert("批量处理成功!");      
									      	 location.reload(true);   
				                        }else{
				                         	 alert("批量处理出错,请联系管理员!");
				                        }        			
								       
								    }   
								}); 
				       }
                 return true;
              }else{
                 //alert("取消");
                 return false;
             }

		
			
	 
		}
 	</script>

		<div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=queryNoSingleImputationList"
				styleId="theform">
				<table class="formTable">
					<!--时间 -->
					<tr>
						<td class="label">
							开始时间 未归集
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
						<td class="label">
							业务号码
						</td>
						<td>
							<input type="text" name="wsNum" class="text" id="wsNum"
								value="${selectAttribute.wsNum}" />
						</td>
						<td class="label">
							当前状态
						</td>
						<td >
							<select id="status" name="status" class="text" 
								style="width: 150px;">
								<option value="auditor">
									故障处理
								</option>
							</select>
						</td>
					</tr>

					<tr>
						<!-- 当前状态 -->
						<td class="label">
							地址
						</td>
						<td>
							<input type="text" name="installAddress" class="text" id="installAddress"
								value="${selectAttribute.installAddress}" />
						</td>
						<td class="label">
							所属区域
						</td>
						<td>
							<input type="text" name="dept" class="text" id="dept"
								value="${selectAttribute.dept}" />
						</td>
						<td class="label">
							当前处理人
						</td>
						<td>
							<input type="text" name="person" class="text" id="person"
								value="${selectAttribute.person}" />
						</td>
					</tr>
					
					<tr>
						<!-- 当前状态 -->
						<td class="label">
							局站名称
						</td>
						<td colspan="5">
							<input type="text" name="stationName" class="text" id="stationName" value="" />
						</td>
					</tr>
					
					<tr>
						<td colspan="6" class="content">
							<div style="text-align:left;">
								<h6>
									<font color="red">*注意：</font>工单号变红为驳回工单，如：<font color="red">123456</font>
								</h6>
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

<bean:define id="url" value="pnrTransferOfficeMaleGuest.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="false"></c:set>
	</c:if>
	<form id="testform" name="testform" action="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=showBatchReplyView" method="post">	
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
		sort="list" size="total" partialList="true">
   		 <display:column  sortable="true" headerClass="sortable" title="工单号">
   		 	<c:if test="${taskList.rollbackFlag eq '1'}">
   		 		 <a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=todo&taskId=${taskList.taskId}&queryFlag=${queryFlag}"  title="回复">
					<font color='red'>${taskList.processInstanceId}</font>
				 </a>	
   		 	</c:if>
   		 	<c:if test="${taskList.rollbackFlag == null ||taskList.rollbackFlag eq '' || taskList.rollbackFlag eq '0'}">
   		 		<a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=todo&taskId=${taskList.taskId}&queryFlag=${queryFlag}"  title="回复">
					${taskList.processInstanceId}
				</a>
   		 	</c:if>
		</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
			${taskList.theme}
		</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
			${taskList.barrierNumber}
		</display:column>
	
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
				${taskList.workTask}
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
				${taskList.processLimit}
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			${taskList.statusName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
			${taskList.connectPerson}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			${taskList.installAddress}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>
		</display:column>
   		
		<display:column sortable="true" headerClass="sortable" title="处理">
					<a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=todo&taskId=${taskList.taskId}&queryFlag=${queryFlag}" title="回复">
					 处理
					</a>
    	</display:column>
    	<display:column sortable="false" headerClass="sortable" title="添加归集" media="html">
				<a href="javascript:void(0);" onclick="addNoSingleImputationDetail(&quot;${taskList.processInstanceId}&quot;,&quot;${taskList.siteCd}&quot;)">添加</a>
		</display:column>	
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
</form>
	</br>
	<c:if test="${carApprove eq 'yes'}">
		<input type="button" class="btn" value="确定" onclick="selectSheet();"/>
	</c:if>

<script type="text/javascript">
		function addNoSingleImputationDetail(processInstanceId,siteCd){
			var url = '${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=addNoSingleImputationDetail&processInstanceId='+processInstanceId+'&siteCd='+siteCd;
			var _sHeight = 603;
		    var _sWidth = 1158;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures);
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>