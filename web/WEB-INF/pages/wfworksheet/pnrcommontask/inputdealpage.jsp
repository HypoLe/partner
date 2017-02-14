<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.boco.eoms.partner.sheet.commontask.model.PnrCommonTaskMain" %>
<%
 String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 System.out.println("=====taskName======"+taskName);
 String operateType = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getParameter("operateType"));
 System.out.println("=====operateType======"+operateType);
 request.setAttribute("operateType",operateType);
 PnrCommonTaskMain sheetMain = (PnrCommonTaskMain)request.getAttribute("sheetMain");
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

<%@ include file="/WEB-INF/pages/wfworksheet/pnrcommontask/baseinputlinkhtmlnew.jsp"%>
	<br/>
	<table class="formTable">
		 <input type="hidden" id="tmpCompleteLimit" value="" alt="vtype:'moreThen',link:'${sheetPageName}nodeCompleteLimit',vtext:'处理时限不能晚于工单完成时限'"/>
         <input type="hidden" name="linkBeanId" value="iPnrCommonTaskLinkManager"/> 
         <input type="hidden" name="beanId" value="iPnrCommonTaskMainManager"/> 
         <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.commontask.model.PnrCommonTaskMain"/>	
         <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.commontask.model.PnrCommonTaskLink"/>
         <input type="hidden" name="toDeptId" value="${sheetMain.toDeptId}"/>
         <input type="hidden" name="gatherPhaseId" value="HoldTask" />
         
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
		<!-- 审批 -->
		<%if(taskName.equals("AuditTask")) {%>
			<%if(operateType.equals("99")){ %>
				<input type="hidden" name="phaseId" id="phaseId" value="DealTask" />
				<input type="hidden" name="dealPerformer" id="dealPerformer" value="${dealPerformer}"/>
	         	<input type="hidden" name="dealPerformerLeader" id="dealPerformerLeader" value="${dealPerformerLeader}"/>
	         	<input type="hidden" name="dealPerformerType" id="dealPerformerType" value="${dealPerformerType}"/>
				<input type="hidden" name="operateType" id="operateType" value="93"/><!-- 默认通过 -->
				<tr>
					<td class="label">填写意见*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'"></textarea>
				  	</td>
			  	</tr>
			  	<tr>
				  	<td class="label">操作</td>
					<td colspan="3">	
						<select id="auditOperate" onchange="changeAuditOperate()">
							<option value="1">通过</option>
							<option value="0">驳回</option>
						</select>		
				  	</td>
			  	<tr>
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
						            scope="request" idField="nodeAccessories" appCode="pnrcommontask" alt="allowBlank:false"/> 				
							<%}else{%>	
							<eoms:attachment name="sheetLink" property="nodeAccessories" 
						            scope="request" idField="nodeAccessories" appCode="pnrcommontask" alt="allowBlank:true"/> 				
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
				            scope="request" idField="nodeAccessories" appCode="pnrcommontask" alt="allowBlank:true"/> 				
				    </td>
				</tr>		
			<tr>
			<tr>
			<td colspan="4">
			 <eoms:chooser id="sendObject"  type="dept,user" flowId="1103" config="{returnJSON:true,showLeader:true}"
			   category="[{id:'dealPerformer',text:'派发',allowBlank:false,vtext:'请选择派发对象',limit:'none'}]" 
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
              	<input type="hidden" name="phaseId" id="phaseId" value="DealTask" />
              	<input type="hidden" name="mainBackTime" id="mainBackTime" value="${mainBackTime}" />
          	 	<input type="hidden" name="operateType" id="operateType" value="54" />
          	 	<input type="hidden" name="dealPerformer" id="dealPerformer" value="" />
          	 	<input type="hidden" name="dealPerformerType" id="dealPerformerType" value="" />
          	 	<input type="hidden" name="dealPerformerLeader" id=dealPerformerLeader value="" />
          	 	
          	 	 <tr>
			        <td class="label"><bean:message bundle="sheet" key="phase.pleaseSelect" /></br>
				        <input type="checkbox" name="checkall" id="checkall" value="" onclick="selfcheckall();eoms.util.checkAll(this, 'deal')" >
				        全选</br>
				        <input type="checkbox" name="checkbackall" id="checkbackall" value="" onclick="selfcheckbackall();selfx()" >
				         反选</td>
			        <td colspan="3">
			       <table class="formTable">
			         <logic:present name="linkList" scope="request"> 
			          <tr>
			           <td class="label">
			            	目的任务对象
			           </td>
			           <td class="label">
			            	目的任务所有者
			            </td>
			           <td class="label">
			            	回复内容
			           </td>
       		 	</tr>
		      <logic:iterate id="link" name="linkList" type="com.boco.eoms.partner.sheet.commontask.model.PnrCommonTaskLink">  
		        <tr>
		          <td>
		              <input type="checkbox" name="deal" id="deal" value="${link.operateRoleId}" onclick="check()">
		              <eoms:id2nameDB id="${link.operateRoleId}" beanId="tawSystemSubRoleDao" /><eoms:id2nameDB id="${link.operateRoleId}" beanId="tawSystemUserDao" />&nbsp;
		              <input type="hidden" name="toid" id="toid" value="${link.id}" />
		              <input type="hidden" name="performer" id="performer" value="${link.operateRoleId}" />
		              <input type="hidden" name="performerType" id="performerType" value="user" />
		              <input type="hidden" name="performerLeader" id="performerLeader" value="${link.operateUserId}" />
		          </td>
		           <td>
		              <eoms:id2nameDB id="${link.operateUserId}" beanId="tawSystemSubRoleDao" /><eoms:id2nameDB id="${link.operateUserId}" beanId="tawSystemUserDao" />&nbsp;
		          </td>
		          <td>
		              <bean:write name="link" property="remark" scope="page"/>
		          </td>
		        </tr>
		      </logic:iterate> 
		     </logic:present>
		     </td>
		     </tr>
              	<tr>
					<td class="label">退回原因*</td>
					<td colspan="3">
				        <textarea name="remark" class="textarea max" id="remark" 
				        alt="allowBlank:false,width:500,vtext:'请最多输入1000字'" alt="width:'500px'"></textarea>
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
	function changeAuditOperate(){
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