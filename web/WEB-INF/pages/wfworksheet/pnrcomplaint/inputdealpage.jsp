<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintMain" %>
<%
 String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 System.out.println("=====taskName======"+taskName);
 String operateType = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getParameter("operateType"));
 System.out.println("=====operateType======"+operateType);
 request.setAttribute("operateType",operateType);
 PnrComplaintMain sheetMain = (PnrComplaintMain)request.getAttribute("sheetMain");
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

<%@ include file="/WEB-INF/pages/wfworksheet/pnrcomplaint/baseinputlinkhtmlnew.jsp"%>
	<br/>
	<table class="formTable">
		 <input type="hidden" id="tmpCompleteLimit" value="" alt="vtype:'moreThen',link:'${sheetPageName}nodeCompleteLimit',vtext:'处理时限不能晚于工单完成时限'"/>
         <input type="hidden" name="linkBeanId" value="iPnrComplaintLinkManager"/> 
         <input type="hidden" name="beanId" value="iPnrComplaintMainManager"/> 
         <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintMain"/>	
         <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintLink"/>
         <input type="hidden" name="toDeptId" value="${sheetMain.toDeptId}"/>
         <input type="hidden" name="gatherPhaseId" value="HoldTask" />
         <input type="hidden" name="operateType" id="operateType" value="<%=operateType %>" />
         
		<c:choose>
		<c:when test="${task.subTaskFlag == 'true'}">
			<input type="hidden" name="hasNextTaskFlag" id="hasNextTaskFlag" value="true" />
		</c:when>
		</c:choose>      
		<%if(operateType.equals("4")){ %>
			<input type="hidden" name="operateType" id="operateType" value="4" />
		  	<input type="hidden" name="phaseId" id="phaseId" value="${fPreTaskName}" />		
    	<tr>
	       <td clss="lbel">备注说明*</td>
			<td colspn="3">			
		  		<textarea name="remark" class="textarea max" id="remark" 
		        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'">${sheetLink.remark}</textarea>
		  </td>
		</tr>  		
		<%} %>    
		<!-- 受理工单 -->
		<%if(taskName.equals("AcceptTask")) {%>
			<%if(operateType.equals("99")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="FaultDealTask" />
				<tr>
					<td class="label">填写意见*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'">${sheetLink.remark}</textarea>
				  	</td>
			  	</tr>
			  	<td colspan="4">
				 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
				   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'1'}]" 
				   panels="[{text:'巡检部门和人员',dataUrl:'/xtree.do?method=userFromDeptPartner&noself=true'}]"
				   data="" />
				</td>
			<%}else if(operateType.equals("94")){ //驳回到派发人%>
				<input type="hidden" name="phaseId" id="phaseId" value="RejectTask" />
 				<tr>
				  	<td class="label">驳回原因*</td>
				    <td colspan="3">
				      <textarea name="remark" id="remark" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
				    </td>
			 	</tr>
			<%}
		} %>   
		
		<!-- 工单完成确认 -->
		<%if(taskName.equals("ConfirmTask")) {%>
			<%if(operateType.equals("96")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="HoldTask" />
				<tr>
					<td class="label">确认内容*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'">${sheetLink.remark}</textarea>
				  	</td>
			  	</tr>
			<%}else if(operateType.equals("93")){//驳回到处理人%>
				<input type="hidden" name="phaseId" id="phaseId" value="FaultDealTask" />
 				<tr>
				  	<td class="label">驳回原因*</td>
				    <td colspan="3">
				      <textarea name="remark" id="remark" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
				    </td>
			 	</tr>
			<%}
		} %> 
		<!-- 处理故障 -->
		<%if(taskName.equals("FaultDealTask")) {%>
 			   <%if(operateType.equals("95")){ %>
 				 	<input type="hidden" name="phaseId" id="phaseId" value="ConfirmTask" />
 					<input type="hidden" name="mainBackTime" id="mainBackTime" value="${sheetMain.mainBackTime}" />
	 				
	 				<tr>
	 					<td class="label">是否已答复客户*</td>
						<td class="content">
							<eoms:comboBox name="linkIsReplied" id="linkIsReplied" defaultValue="${sheetLink.linkIsReplied}"
								initDicId="10301" alt="allowBlank:false" styleClass="select"/>
						</td>
			 			<td class="label">问题消除时间*</td>
						<td class="content">
							<input type="text" class="text" name="linkIssueEliminatTime" readonly="readonly" 
								id="linkIssueEliminatTime" value="${sheetLink.linkIssueEliminatTime }"
								onclick="popUpCalendar(this, this,null,null,null,true,-1,0);" alt="allowBlank:false"/>   
						</td>
					</tr>
					<tr>
	 					<td class="label">处理结果*</td>
						<td class="content">
							<eoms:comboBox name="linkDealResult" id="linkDealResult" defaultValue="${sheetLink.linkDealResult}"
								initDicId="1010607" alt="allowBlank:false" styleClass="select"/>
						</td>
			 			<td class="label">问题原因*</td>
						<td class="content">
							<eoms:comboBox name="linkIssueReasonDict" id="linkIssueReasonDict" defaultValue="${sheetLink.linkIssueReasonDict}"
								initDicId="1010608" alt="allowBlank:false" styleClass="select"/>
						</td>
					</tr>
					<tr>
						<td class="label">故障原因*</td>
						<td class="content">
							<eoms:comboBox name="linkFaultReason" id="linkFaultReason" defaultValue="${sheetLink.linkFaultReason}"
								initDicId="1010609" alt="allowBlank:false" styleClass="select"/>
						</td>
					</tr>
					<c:if test="${sheetMain.mainSpecialty == '1122505'}">
						<tr>
							<td class="label">客户名称*</td>
							<td class="content">
								<input class="text" type="text" name="linkCustomerName"
									id="linkCustomerName" alt="allowBlank:false" value="${sheetLink.linkCustomerName }"/>
							</td>
				 			<td class="label">客户电话*</td>
							<td class="content">
								<input class="text" type="text" name="linkCustomerPhone"
									id="linkCustomerPhone" alt="allowBlank:false" value="${sheetLink.linkCustomerPhone }"/>
							</td>
						</tr>
						<tr>
							<td class="label">设备端口*</td>
							<td class="content">
								<input class="text" type="text" name="linkDevicePort"
									id="linkDevicePort" alt="allowBlank:false" value="${sheetLink.linkDevicePort }"/>
							</td>
						</tr>
					</c:if>
					
	 				<tr>
					  	<td class="label">问题原因</td>
					    <td colspan="3">
					      <textarea name="linkIssueEliminatReason" id="linkIssueEliminatReason" class="textarea max"
					      alt="allowBlank:true,maxLength:2000,vtext:'请输入内容，最多输入1000汉字'">${sheetLink.linkIssueEliminatReason}</textarea>
					    </td>
				 	</tr>
	 				<tr>
					  	<td class="label">解决措施*</td>
					    <td colspan="3">
					      <textarea name="linkDealDesc" id="linkDealDesc" class="textarea max"
					      alt="allowBlank:false,maxLength:2000,vtext:'请输入内容，最多输入1000汉字'">${sheetLink.linkDealDesc}</textarea>
					    </td>
				 	</tr>
				 	
					<tr>
					    <td class="label">
					    	<bean:message bundle="sheet" key="mainForm.accessories"/>
						</td>	
						<td colspan="3">
							<%if( fileNeed == true){%>	
							<eoms:attachment name="sheetLink" property="nodeAccessories" 
						            scope="request" idField="nodeAccessories" appCode="pnrcomplaint" alt="allowBlank:false"/> 				
							<%}else{%>	
							<eoms:attachment name="sheetLink" property="nodeAccessories" 
						            scope="request" idField="nodeAccessories" appCode="pnrcomplaint" alt="allowBlank:true"/> 				
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
					            scope="request" idField="nodeAccessories" appCode="pnrcomplaint" alt="allowBlank:true"/> 				
					    </td>
					 </tr>		
					<tr>
						<td colspan="4">
							 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
							   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'none'}]" 
							  data="" />
					  	</td>
					</tr>
 		  		<%}else if(operateType.equals("97")){ //驳回到受理人%>
 		  			<input type="hidden" name="phaseId" id="phaseId" value="AcceptTask" />
	 				<tr>
					  	<td class="label">驳回原因*</td>
					    <td colspan="3">
					      <textarea name="remark" id="remark" class="textarea max"
					      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
					    </td>
				 	</tr>
 		  		<%}%>
 		  <%}%>
 		  
 		  
 		  <%if( taskName.equals("HoldTask")){
 				if(operateType.equals("18")){ %>
         	 	<input type="hidden" name="hasNextTaskFlag" id="hasNextTaskFlag" value="true" />
      			<input type="hidden" name="operateType" id="operateType" value="18" />
      			<input type="hidden" name="phaseId" id="phaseId" value="Over" />
      			<input type="hidden" name="status" id="status" value="1"/>	
         			 
	 		 <tr>
			  	<td class="label"><bean:message bundle="sheet" key="mainForm.holdStatisfied"/>*</td>
			    <td>
			      <eoms:comboBox name="holdStatisfied" 
			        id="holdStatisfied" defaultValue="${sheetMain.holdStatisfied != 0 ? sheetMain.holdStatisfied : 1030301}" initDicId="10303" styleClass="select" alt="allowBlank:false"/>
			    </td>
			    <td class="label">客户满意度*</td>
			    <td>
			      <eoms:comboBox name="mainCustomerStatisfied" id="mainCustomerStatisfied" 
			      	defaultValue="${sheetMain.mainCustomerStatisfied}" initDicId="10303" styleClass="select" alt="allowBlank:false"/>
			    </td>
		     </tr>
		     
		      <tr>
			  	<td class="label">故障原因*</td>
			    <td>
			      <eoms:comboBox name="mainFaultReason" id="mainFaultReason" defaultValue="${sheetMain.mainFaultReason}" 
			      	initDicId="1010609" styleClass="select" alt="allowBlank:false"/>
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
              	<input type="hidden" name="phaseId" id="phaseId" value="ConfirmTask" />
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
	
<% if( taskName.equals("HoldTask") && operateType.equals("54")){%>	
	function selfx(){
var put=document.getElementsByName("deal");
 for(i=0;i<put.length;i++){
   put[i].checked=(put[i].checked)?false:true;
 }
}
function check(){
	var put1=document.getElementsByName("checkall");
	var put2=document.getElementsByName("checkbackall");
	if (put1[0].checked == true) {
		put1[0].checked = false;
	}
	if (put2[0].checked == true) {
		put2[0].checked = false;
	}
	
}

function selfcheckall(){
	var put1=document.getElementsByName("checkall");
	var put2=document.getElementsByName("checkbackall");
	 if(put1[0].checked==true)
	 {
	 put2[0].checked=false;
	 }
	 else {
	 put2[0].checked=true;
	 }
	 }
	 function selfcheckbackall(){
	var put1=document.getElementsByName("checkall");
	var put2=document.getElementsByName("checkbackall");
	 if(put2[0].checked==true)
	 {
	 put1[0].checked=false;
	 }
	 else {
	 put1[0].checked=true;
	 }
}
<%}%>	
 </script>