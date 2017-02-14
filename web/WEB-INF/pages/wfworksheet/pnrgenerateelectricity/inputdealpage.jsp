<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityMain" %>
<%
 String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 System.out.println("=====taskName======"+taskName);
 String operateType = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getParameter("operateType"));
 System.out.println("=====operateType======"+operateType);
 request.setAttribute("operateType",operateType);
 PnrGenerateElectricityMain sheetMain = (PnrGenerateElectricityMain)request.getAttribute("sheetMain");
 boolean fileNeed = false;
 if("1030101".equals(sheetMain.getMainFileNeeded())){
	 fileNeed = true;
 }
 %>

<script type="text/javascript">
	//处理时限不能超过工单完成时限
	var dtnow = new Date();
	var str = '${sheetMain.sheetCompleteLimit}';
	str = str.replace(/-/g,"/");
	str = str.substring(0,str.length-2);
	var dt2 = new Date(str);
	if(dt2>dtnow){
		document.getElementById("tmpCompleteLimit").value='${sheetMain.sheetCompleteLimit}';
	}else{
		document.getElementById("tmpCompleteLimit").value='';
	}
 </script>

<%@ include file="/WEB-INF/pages/wfworksheet/pnrgenerateelectricity/baseinputlinkhtmlnew.jsp"%>
	<br/>
	<table class="formTable">
		 <input type="hidden" id="tmpCompleteLimit" value="" alt="vtype:'moreThen',link:'${sheetPageName}nodeCompleteLimit',vtext:'处理时限不能晚于工单完成时限'"/>
         <input type="hidden" name="linkBeanId" value="iPnrGenerateElectricityLinkManager"/> 
         <input type="hidden" name="beanId" value="iPnrGenerateElectricityMainManager"/> 
         <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityMain"/>	
         <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.generateelectricity.model.PnrGenerateElectricityLink"/>
         <input type="hidden" name="toDeptId" value="${sheetMain.toDeptId}"/>
         <input type="hidden" name="gatherPhaseId" value="HoldTask" />
         
		<c:choose>
		<c:when test="${task.subTaskFlag == 'true'}">
			<input type="hidden" name="hasNextTaskFlag" id="hasNextTaskFlag" value="true" />
		</c:when>
		</c:choose>      
		 
		<!-- 受理发电 -->
		<%if(taskName.equals("AcceptTask")) {%>
			<%if(operateType.equals("2")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="ExecuteTask" />
				<input type="hidden" name="operateType" id="operateType" value="${operateType }"/><!-- 受理发电 -->
				<tr>
					<td class="label">填写意见*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'"></textarea>
				  	</td>
			  	</tr>
			  	<tr>
				  	<td class="label">选择处理人或处理部门</td>
					<td colspan="3">
						<eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
		   category="[{id:'dealPerformer',text:'派发',allowBlank:false,limit:'none',vtext:'请选择派发对象'}]" 
		  data="" />
					
						 <!-- 
						<select id="auditOperate" onchange="acceptOperate">
							<option value="1">受理</option>
							<option value="0">驳回</option>
						</select>		
						 -->
				  	</td>
			  	<tr>
			<%}%>
			<%if(operateType.equals("4")){ %>
			<input type="hidden" name="operateType" id="operateType" value="4" />
		  	<input type="hidden" name="phaseId" id="phaseId" value="RejectTask" />		
    	<tr>
	       <td clss="lbel">备注说明*</td>
			<td colspn="3">			
		  		<textarea name="remark" class="textarea max" id="remark" 
		        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'">${sheetLink.remark}</textarea>
		  </td>
		</tr>  		
		<%} %>   
		<% } %>
		<!-- 执行发电 -->
		<%if(taskName.equals("ExecuteTask")) {%>
			<%if(operateType.equals("14")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="HoldTask" />
				<input type="hidden" name="operateType" id="operateType" value="14"/><!-- 执行发电 -->
			  	<tr>
				  	<td class="label">发电开始时间*</td>
					<td colspan="1">
					<input class="text" type="text" name="linkStartGenerateTime" readonly="readonly" 
					id="linkStartGenerateTime" alt="allowBlank:false" value="" onclick="popUpCalendar(this, this,null,null,null,null,-1,null);"
					/>
				  	</td>
				  	<td class="label">发电结束时间*</td>
					<td colspan="1">
					<input class="text" type="text" name="linkEndGenerateTime" readonly="readonly" 
					id="linkEndGenerateTime" alt="allowBlank:false,vtype:'moreThen',link:'linkStartGenerateTime',vtext:'发电结束时间不能早于发电开始时间'" value="" onclick="popUpCalendar(this, this,null,null,null,null,-1,null);"
					/>
				  	</td>
			  	</tr>
			  	<tr>
				  	<td class="label">市电恢复时间*</td>
					<td colspan="1">
					<input class="text" type="text" name="linkResumeTime" readonly="readonly" 
					id="linkResumeTime" alt="allowBlank:false,vtype:'moreThen',link:'linkEndGenerateTime',vtext:'市电恢复时间不能早于发电结束时间'" value="" onclick="popUpCalendar(this, this,null,null,null,null,-1,null);"
					/>
				  	</td>
				  	<td class="label">工作电流(A)*</td>
					<td colspan="1">
					<input class="text" type="number" name="linkWorkingCurrent"
					id="linkWorkingCurrent" alt="allowBlank:false,vtype:'float'" value=""
					/>
				  	</td>
			  	</tr>
			  	<tr>
				  	
				  	<td class="label">油机编号/型号*</td>
					<td colspan="1">
					<input class="text" type="text" name="linkOilEngineSerialNumber"
					id="linkOilEngineSerialNumber" alt="allowBlank:false" value="" 
					/>
				  	<td class="label">耗油(升)*</td>
					<td colspan="1">
					<input class="text" type="text" name="linkFuelConsumption" 
					id="linkFuelConsumption" alt="allowBlank:false,vtype:'float'" value=""
					/>
			  	</tr>
			  	<tr>
			  	</td>
				  	<td class="label">零星耗材记录</td>
					<td colspan="3">
					<textarea name="linkConsumptiveMaterial" class="textarea max" id="linkConsumptiveMaterial" 
				        alt="allowBlank:true,width:500,vtext:'请最多输入1000字'" alt="width:'500px'"></textarea>
					</td><tr>
					<td class="label">处理情况说明*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'"></textarea>
				  	</td>
			  	</tr>
			  	
			  	
			  	<!-- 附件 -->
			  	<table id="sheet" class="formTable">
	  <tr>
		    <td class="label">
		    	<bean:message bundle="sheet" key="mainForm.accessories"/>
			</td>	
			<td colspan="3">					
    				<%if( fileNeed == true){%>	
					<eoms:attachment name="sheetLink" property="nodeAccessories" 
				            scope="request" idField="nodeAccessories" appCode="pnrgenerateelectricity" alt="allowBlank:false"/> 				
					<%}else{%>	
					<eoms:attachment name="sheetLink" property="nodeAccessories" 
				            scope="request" idField="nodeAccessories" appCode="pnrgenerateelectricity" alt="allowBlank:true"/> 				
					<%} %>				
		    </td>
	   </tr>			  
</table>
			<%}
		} %>   
 		<%if(taskName.equals("DealTask")) {%>
 				<input type="hidden" name="${sheetPageName}operateType" id="${sheetPageName}operateType" value="<%=operateType %>" /> 
 				 <%if(operateType.equals("95")||operateType.equals("11")){ %>
 				 	<input type="hidden" name="phaseId" id="phaseId" value="HoldTask" />
 					<input type="hidden" name="mainBackTime" id="mainBackTime" value="${sheetMain.mainBackTime}" />
	 				<tr>
					  	<td class="label">完成情况*</td>
					    <td colspan="3">
					      <textarea name="remark" id="remark" class="textarea max"
					      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
					    </td>
				 	</tr>
					<tr>
					    <td class="label">
					    	<bean:message bundle="sheet" key="mainForm.accessories"/>
						</td>	
						<td colspan="3">
							<%if( fileNeed == true){%>	
							<eoms:attachment name="sheetLink" property="nodeAccessories" 
						            scope="request" idField="nodeAccessories" appCode="pnrgenerateelectricity" alt="allowBlank:false"/> 				
							<%}else{%>	
							<eoms:attachment name="sheetLink" property="nodeAccessories" 
						            scope="request" idField="nodeAccessories" appCode="pnrgenerateelectricity" alt="allowBlank:true"/> 				
							<%} %>
						    
					    </td>
					</tr>
 
 		  		<%}else if(operateType.equals("10")){ %>
 				 <input type="hidden" name="phaseId" id="phaseId" value="DealTask" />
 				 <input type="hidden" name="mainBackTime" id="mainBackTime" value="${sheetMain.mainBackTime}" />
 				 <tr>
				  	<td class="label">备注*</td>
				    <td colspan="3">
				      <textarea name="remark" id="remark" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入备注，最多输入1000汉字'">${sheetLink.remark}</textarea>
				    </td>
			 	 </tr>
				<tr>
				    <td class="label">
				    	<bean:message bundle="sheet" key="mainForm.accessories"/>
					</td>	
					<td colspan="3">
				    <eoms:attachment name="sheetLink" property="nodeAccessories" 
				            scope="request" idField="nodeAccessories" appCode="pnrgenerateelectricity" alt="allowBlank:true"/> 				
				    </td>
				</tr>		
			<tr>
			<tr>
			<td colspan="4">
			 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
			   category="[{id:'dealPerformer',text:'派发',vtext:'请选择派发对象',limit:'none'}]" 
			  data="" />
			  </td>
		</tr>
 
 		  <%}}%>
 			<%if( taskName.equals("HoldTask")){
 				if(operateType.equals("18")){ %>
         	 	<input type="hidden" name="hasNextTaskFlag" id="hasNextTaskFlag" value="true" />
      			<input type="hidden" name="operateType" id="operateType" value="18" />
      			<input type="hidden" name="phaseId" id="phaseId" value="Over" />
      			<input type="hidden" name="status" id="status" value="1"/>	
         			 
	 		 <tr>
			  	<td class="label"><bean:message bundle="sheet" key="mainForm.holdStatisfied"/>*</td>
			    <td colspan='3'>
			      <eoms:comboBox name="holdStatisfied" 
			        id="holdStatisfied" defaultValue="${sheetMain.holdStatisfied != 0 ? sheetMain.holdStatisfied : 1030301}" initDicId="10303" styleClass="select" alt="allowBlank:false"/>
			    </td>
		     </tr>
			  
			  <tr>
			  	<td class="label"><bean:message bundle="sheet" key="mainForm.endResult"/>*</td>
			    <td colspan="3">
			      <textarea name="endResult" id="endResult" class="textarea max"
			      alt="allowBlank:false,maxLength:2000,vtext:'请输入归档内容，最多输入1000汉字'">${sheetMain.endResult}</textarea>
			    </td>
			  </tr>	        			    			 
              <%}else if(operateType.equals("54")){%>
              	<input type="hidden" name="phaseId" id="phaseId" value="ExecuteTask" />
              	<input type="hidden" name="operateType" id="operateType" value="54" />
              	<tr>
				  	<td class="label">退回原因*</td>
				    <td colspan="3">
				      <textarea name="remark" id="remark" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
				    </td>
			 	</tr>
              <%  
              }%> 
                    
          <%}%>
		<% if(taskName.equals("cc")){%>
	    	<tr>
		       <td class="label">
		        <bean:message bundle="sheet" key="linkForm.remark" />*
			    </td>
				<td colspan="3">			
				 <input type="hidden" name="operateType" id="operateType" value="-15" />
			           <textarea name="remark" class="textarea max" id="remark" 
			        alt="allowBlank:false,width:500,maxLength:2000,vtext:'请最多输入1000汉字'" alt="width:'500px'">${sheetLink.remark}</textarea>
			  </td>
			</tr>  
     <%} %> 			     
  </table>

 <script type="text/javascript">

	/**
	*  改变审批操作触发
	*/
	function acceptOperate(){
		var auditOperate = document.getElementById('auditOperate');
		var phaseId = document.getElementById('phaseId');
		var dealPerformer = document.getElementById('dealPerformer');
		var dealPerformerLeader = document.getElementById('dealPerformerLeader');
		var dealPerformerType = document.getElementById('dealPerformerType');
		var operateType = document.getElementById('operateType');
		if('0' == auditOperate.value){ //驳回
         	phaseId.value = 'RejectTask';
         	dealPerformer.disabled = true; 
         	dealPerformerLeader.disabled = true; 
         	dealPerformerType.disabled = true; 
         	operateType.value = '94';
		}else if('1' == auditOperate.value){ //通过
			phaseId.value = 'DealTask';
         	dealPerformer.disabled = false; 
         	dealPerformerLeader.disabled = false; 
         	dealPerformerType.disabled = false;
         	operateType.value = '93';
		}
	}
	
	
<% if( taskName.equals("HoldTask") && operateType.equals("54")){%>	

<%}%>	
 </script>