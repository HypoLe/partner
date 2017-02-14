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
       <div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=listInvolvedUnfinishedProcessInstances"
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
								<option value="">
									所有
								</option>
								<option value="newMaleGuest" ${selectAttribute.status == "newMaleGuest" ? 'selected="selected"':'' }>
									派发公客工单
								</option>
								<option value="auditor" ${selectAttribute.status == "auditor" ? 'selected="selected"':'' }>
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

				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
					
					<c:if test="${total!=0}">
						<input type="button" class="btn" value="批量回复" onclick="batchReply()" />
					</c:if>
					
				</div>
			</html:form>
		</div> 

<bean:define id="url" value="pnrTransferOfficeMaleGuest.do"/>
	<c:if test="${carApprove ne 'yes'}">
		<c:set var="export" value="true"></c:set>
	</c:if>
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
		sort="list" size="total" partialList="true" >
		<display:column  sortable="true" headerClass="sortable" title="工单号">		
   	   	 	<c:if test="${taskList.changeColor eq 'true'}">
		   		<a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
					${taskList.processInstanceId}
			    </a>	
   		 	</c:if>
   		  	<c:if test="${taskList.changeColor eq 'false'}">
   		  		<a href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
				 	<font color="red">${taskList.processInstanceId}</font>
			    </a>
   		 	</c:if>	
		</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="主题"  >
				<c:if test="${taskList.changeColor eq 'true'}">
					${taskList.theme}
   		 		</c:if>
   		  		<c:if test="${taskList.changeColor eq 'false'}">
   		  			<font color="red">${taskList.theme}</font>
   		 		</c:if>
		</display:column>
        <display:column  sortable="true"
			headerClass="sortable" title="业务号码">
				<c:if test="${taskList.changeColor eq 'true'}">
					${taskList.barrierNumber}
   		 		</c:if>
   		  		<c:if test="${taskList.changeColor eq 'false'}">
   		   			<font color="red">${taskList.barrierNumber}</font>
   		 		</c:if>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
			 <c:if test="${taskList.changeColor eq 'true'}">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
	   		 </c:if>
			 <c:if test="${taskList.changeColor eq 'false'}">
   		   		<font color="red"><fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
   			 </c:if>		
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
			<c:if test="${taskList.changeColor eq 'true'}">
				<fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
   			</c:if>
   		    <c:if test="${taskList.changeColor eq 'false'}">
   			   <font color="red"><fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></font>
   		   </c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
			<c:if test="${taskList.changeColor eq 'true'}">
				${taskList.workTask}
   		    </c:if>
   		    <c:if test="${taskList.changeColor eq 'false'}">
   			  <font color="red">${taskList.workTask}</font>
   		    </c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
			<c:if test="${taskList.changeColor eq 'true'}">
				${taskList.processLimit}
   		    </c:if>
   		    <c:if test="${taskList.changeColor eq 'false'}">
   			  <font color="red">${taskList.processLimit}</font>
   		    </c:if>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			<c:if test="${taskList.changeColor eq 'true'}">
				${taskList.statusName}
   		 	</c:if>
   		 	 <c:if test="${taskList.changeColor eq 'false'}">
   		  		<font color="red">
					${taskList.statusName}
				</font>
   		 	</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="联系人" >
			<c:if test="${taskList.changeColor eq 'true'}">
				${taskList.connectPerson}
   		    </c:if>
   		    <c:if test="${taskList.changeColor eq 'false'}">
   		        <font color="red">${taskList.connectPerson}</font>
   		    </c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			<c:if test="${taskList.changeColor eq 'true'}">
				${taskList.installAddress}
   		    </c:if>
   		    <c:if test="${taskList.changeColor eq 'false'}">
   		  		<font color="red">${taskList.installAddress}</font>
   		    </c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<c:if test="${taskList.changeColor eq 'true'}">
				<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>
   		 	</c:if>
   		  	<c:if test="${taskList.changeColor eq 'false'}">
   		  		<span style="color:red">
   		  			<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>
   		  		</span>
   		 	</c:if>
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="所属区域">
   		 	<c:if test="${taskList.changeColor eq 'true'}">
				<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>
   		 	</c:if>
   		    <c:if test="${taskList.changeColor eq 'false'}">
   		 		<span style="color:red">
   		  			<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>
   		  		</span>
   		 	</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<c:if test="${taskList.changeColor eq 'true'}">
				<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>
   		 	</c:if>
   		  	<c:if test="${taskList.changeColor eq 'false'}">
   		 		<span style="color:red"> <eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/></span>
   		 	</c:if>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="关联工单" style="text-align:center;" media="html">
   			<c:if test="${taskList.maleGuestState eq '1'}">
   		 		<a href="javascript:void(0);" onclick="viewRelatedWorkOrder(&quot;${taskList.processInstanceId}&quot;,&quot;${taskList.siteCd}&quot;)">查看</a>
   		 	</c:if>
   		 	<c:if test="${taskList.maleGuestState ne '1'}">
   		 		-
   		 	</c:if>
		</display:column>
		
		

		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>
	</br>
	<c:if test="${carApprove eq 'yes'}">
		<input type="button" class="btn" value="确定" onclick="selectSheet();"/>
	</c:if>
	
<script type="text/javascript">
//查看关联工单
	function viewRelatedWorkOrder(processInstanceId,siteCd){
			var url = '${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=viewRelatedWorkOrder&processInstanceId='+processInstanceId+'&siteCd='+siteCd;
			var _sHeight = 400;
		    var _sWidth = 1158;
		    var sTop=(window.screen.availHeight-_sHeight)/2;
		    var sLeft=(window.screen.availWidth-_sWidth)/2;
			var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(url,window,sFeatures);
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>