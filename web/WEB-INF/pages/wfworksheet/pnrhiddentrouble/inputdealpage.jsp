<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%
 String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 System.out.println("=====taskName======"+taskName);
 String operateType = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getParameter("operateType"));
 System.out.println("=====operateType======"+operateType);
 request.setAttribute("operateType",operateType);
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
	
	
	function costFlag(){
		var cost = document.getElementById("linkextraCostFlag").value;
		if(cost == '1030101'){  //需要要额外费用
			document.getElementById("linkbudgetDetial").disabled=false;
			document.getElementById("linkbudgetDetial").style.display ="block";
			document.getElementById("divnodeAccessories").disabled=false;
			
			document.getElementById("divnodeAccessories").style.display ="block";
			var html= '<eoms:attachment name="sheetLink" property="nodeAccessories" scope="request" idField="nodeAccessories" appCode="pnrhiddentrouble" />	';	
			var acc = document.getElementById("divnodeAccessories");
			acc.innerHTML = html;
			
		}else{//不需要额外费用
			document.getElementById("linkbudgetDetial").disabled=true;
			document.getElementById("linkbudgetDetial").style.display ="none";
			document.getElementById("divnodeAccessories").disabled=true;
			document.getElementById("divnodeAccessories").style.display ="none";
		}
	}
	

 </script>

<%@ include file="/WEB-INF/pages/wfworksheet/pnrhiddentrouble/baseinputlinkhtmlnew.jsp"%>
	<br/>
	<table class="formTable">
		 <input type="hidden" id="tmpCompleteLimit" value="" alt="vtype:'moreThen',link:'${sheetPageName}nodeCompleteLimit',vtext:'处理时限不能晚于工单完成时限'"/>
         <input type="hidden" name="linkBeanId" value="iPnrHiddenTroubleLinkManager"/> 
         <input type="hidden" name="beanId" value="iPnrHiddenTroubleMainManager"/>
         <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.pnrhiddentrouble.model.PnrHiddenTroubleMain"/>	
         <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.pnrhiddentrouble.model.PnrHiddenTroubleLink"/>
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
			<%if(operateType.equals("92")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="DealTask" />
				
				<tr>
					<td class="label">处理时限*</td>
					<td>
				        <input type="text" class="text" name="nodeCompleteLimit" readonly="readonly" 
					id="nodeCompleteLimit" value="${eoms:date2String(sheetLink.nodeCompleteLimit)}"
					onclick="popUpCalendar(this, this,null,null,null,true,1);" alt="allowBlank:false"/>
				  	</td>
					<td class="label">回复需附件*</td>
					<td>
				        <eoms:comboBox name="linkAccessoriesFlag" 
			        id="linkAccessoriesFlag" defaultValue="${sheetLink.linkAccessoriesFlag != null ? sheetLink.linkAccessoriesFlag : 1030102}" initDicId="10301" styleClass="select" />
				  	</td>
			  	</tr>
				<tr>
					<td class="label">紧急程度*</td>
					<td colspan="3">
				        <eoms:comboBox name="linkurgentLevel" id="linkurgentLevel" defaultValue="${sheetLink.linkurgentLevel}"
						initDicId="1010102"  styleClass="select" alt="allowBlank:false"/>
				  	</td>
			  	</tr>
				<tr>
					<td class="label">处理要求*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'">${sheetLink.remark}</textarea>
				  	</td>
			  	</tr>
			  	<td colspan="4">
				 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
				   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'1'}]" 
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
			<%}else if(operateType.equals("51")){ //派发到联通%>
				<input type="hidden" name="phaseId" id="phaseId" value="ChinaMobileAcceptTask" />
				<tr>
				  	<td class="label">是否需要额外费用</td>
				    <td >
				       <eoms:comboBox name="linkextraCostFlag" onchange="costFlag()"
			        id="linkextraCostFlag" defaultValue="${sheetLink.linkextraCostFlag != null ? sheetLink.linkextraCostFlag : 1030102}" initDicId="10301" styleClass="select" />
				    </td>
				    <td class="label">费用预算</td>
				    <td class="content">
				    	<input type="text" id="linkbudgetDetial" value="${sheetLink.linkbudgetDetial }" class="text medium" name="linkbudgetDetial" alt="re:/^\d+(\.\d+)?$/,re_vt:'请输入浮点数'" /> 
				    </td>
			 	</tr>
			 	<tr>
					<td class="label">紧急程度*</td>
					<td colspan="3">
				        <eoms:comboBox name="linkurgentLevel" id="linkurgentLevel" defaultValue="${sheetLink.linkurgentLevel}"
						initDicId="1010102"  styleClass="select" alt="allowBlank:false"/>
				  	</td>
			  	</tr>
 				<tr>
				  	<td class="label">处理建议*</td>
				    <td colspan="3">
				      <textarea name="remark" id="remark" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
				    </td>
			 	</tr>
 				<tr>
				  	<td class="label">处理方案详细</td>
				    <td colspan="3">
				      <textarea name="operateReason" id="operateReason" class="textarea max"
				      alt="maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.operateReason}</textarea>
				    </td>
			 	</tr>
 				<tr>
				  	<td class="label">预算表明细</td>
				      <td colspan="3">	
				        <div id="divnodeAccessories" style="display: block;">
					  
						</div>	
					 </td>
			 	</tr>
			 	<tr>
			 	<td colspan="4">
				 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
				   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'1'}]" 
				  data="" />
				</td>
			 	</tr>
			 	
			<%}else if(operateType.equals("99")){ //联通直接归档%>
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
			 	
			<%}
		} %>   
		
		<!-- 联通派发 -->
		<%if(taskName.equals("ChinaMobileAcceptTask")) {%>
			<%if(operateType.equals("52")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="DealTask" />
				
				<tr>
					<td class="label">处理时限*</td>
					<td>
				        <input type="text" class="text" name="nodeCompleteLimit" readonly="readonly" 
					id="nodeCompleteLimit" value="${eoms:date2String(sheetLink.nodeCompleteLimit)}"
					onclick="popUpCalendar(this, this,null,null,null,true,1);" alt="allowBlank:false"/>
				  	</td>
					<td class="label">回复需附件*</td>
					<td>
				        <eoms:comboBox name="linkAccessoriesFlag" 
			        id="linkAccessoriesFlag" defaultValue="${sheetLink.linkAccessoriesFlag != null ? sheetLink.linkAccessoriesFlag : 1030102}" initDicId="10301" styleClass="select" />
				  	</td>
			  	</tr>
				<tr>
					<td class="label">紧急程度*</td>
					<td colspan="3">
				        <eoms:comboBox name="linkurgentLevel" id="linkurgentLevel" defaultValue="${sheetLink.linkurgentLevel}"
						initDicId="1010102"  styleClass="select" alt="allowBlank:false"/>
				  	</td>
			  	</tr>
				<tr>
					<td class="label">处理要求*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'">${sheetLink.remark}</textarea>
				  	</td>
			  	</tr>
			  	<td colspan="4">
				 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
				   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'1'}]" 
				  data="" />
				</td>
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
			  	<tr>
			 		<td colspan="4">
				 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
				   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'1'}]" 
				  data="" />
				</td>
			 	</tr>
			<%}else if(operateType.equals("93")){//驳回到处理人%>
				<input type="hidden" name="phaseId" id="phaseId" value="DealTask" />
 				<tr>
				  	<td class="label">驳回原因*</td>
				    <td colspan="3">
				      <textarea name="remark" id="remark" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.remark}</textarea>
				    </td>
			 	</tr>
			<%}
		} %> 
		<!-- 隐患故障 -->
		<%if(taskName.equals("DealTask")) {%>
 			   <%if(operateType.equals("95")){ %>
 				 	<input type="hidden" name="phaseId" id="phaseId" value="ConfirmTask" />
 					<input type="hidden" name="mainBackTime" id="mainBackTime" value="${sheetMain.mainBackTime}" />
	 				
					<tr>
	 					<td class="label">处理描述*</td>
						<td colspan="3">
							<textarea name="operateReason" id="operateReason" class="textarea max"
				      alt="allowBlank:false,maxLength:2000,vtext:'请输入完成情况，最多输入1000汉字'">${sheetLink.operateReason}</textarea>
						</td>
					</tr>
					
					<tr>
						    <td class="label">
						    	<bean:message bundle="sheet" key="mainForm.accessories"/>
							</td>	
							<td colspan="3">
							
							<c:choose>
								<c:when test="${sheetMain.linkAccessoriesFlag eq '1030101'}">
									<eoms:attachment name="sheetLink" property="sheetAccessories" 
						            scope="request" idField="sheetAccessories" appCode="pnrhiddentrouble" alt="allowBlank:false"/> 	
								</c:when>
								<c:otherwise>
									<eoms:attachment name="sheetLink" property="sheetAccessories" 
						            scope="request" idField="sheetAccessories" appCode="pnrhiddentrouble" alt="allowBlank:true"/> 
								</c:otherwise>
							</c:choose>
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
					            scope="request" idField="nodeAccessories" appCode="pnrhiddentrouble" alt="allowBlank:true"/> 				
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

	Ext.onReady(function(){
		try{
			costFlag();
		}catch(e){
		}
	});
 </script>