<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   
   });
   
   
  function changeType1()
  {
		//$('phaseId').value = "DealTask";
   		//$('operateType').value = "0";
   }
   
</script>

<%@ include file="/WEB-INF/pages/partner/activiti/common/task_basis.jsp"%>

<div style="TEXT-ALIGN: center; height:20"></div>
 <!-- 工单基本信息end --> 
	<html:form action="/pnrTaskTicket.do?method=goTodo" styleId="theform" >
        <input type="hidden" name="taskId" value="${taskId}">
        <input type="hidden" name="processInstanceId" value="${processInstanceId}">
       
        <input type="hidden" name="titleId" value="${pnrTaskTicket.id}">
        <input type="hidden" name="title" value="${pnrTaskTicket.theme}">
        <input type="hidden" name="userId" value="${userId}">
        <input type="hidden" name="handleState" value="handle">
        <input type="hidden" name="initiator" value="${initiator}">
		
<table id="sheet" class="formTable" >
		<tr>
			<td class="label">交通方式*</td>
			<td class="content">
				<eoms:comboBox name="transport" id="transport" defaultValue=""
					initDicId="1010109" alt="allowBlank:false" styleClass="select"/>
			</td>
		  
		  <td class="label">里程*</td>
		  <td class="content">
		    <input type="text" class="text"  name="mileage" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:公里)
		  </td>	
 			
		</tr>
		<tr>
		    <td class="label">
		    	附件
			</td>	
			<td colspan="3">
		    <eoms:attachment name="sheetReply" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTaskTicket" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>	
		<tr>
 			<td class="label">
				完成情况*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入完成情况，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
		<tr>
 			<td class="label">
				任务处理人*
			</td>
			<td class="content" colspan="3">
				<!--任务处理人-->
		<fieldset>
 			<legend>
		 	<span id="roleName1">
		 	 任务处理人
			 </span>
  			</legend>
		    <div class="x-form-item" >
				<eoms:chooser id="sendObject1" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDept',rootId:'${country}'} ]" type="user" flowId="1103" config="{returnJSON:true,showLeader:true}"
				   category="[{id:'dealPerformer2',text:'处理人',allowBlank:false,childType:'user',limit:20,vtext:'请选择处理对象'}]" 
				  data="" />
			</div>	
		</fieldset>
			</td>
		</tr>
		 <tr>
		   <td class="label">处理开始时间</td>
		   <td class="content">
		    <input type="text" class="text" name="sheetAcceptLimit" readonly="readonly" 
				id="sheetAcceptLimit" value="" 
				onclick="popUpCalendar(this, this,null,null,null,true,-1);" alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'处理开始时间不能晚于处理结束时间',allowBlank:true"/>  
		  	</td>
		 	 <td class="label">处理结束时间</td>
		  	<td class="content">
		    <input type="text" class="text" name="sheetCompleteLimit" readonly="readonly" 
				id="sheetCompleteLimit" value="" 
				onclick="popUpCalendar(this, this,null,null,null,true,-1)" alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'处理结束时间不能早于处理开始时间',allowBlank:true"/>   
		 	 </td>		
		</tr>
		
</table>

	    <p/>	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				处理
			</html:submit>&nbsp;&nbsp;
			<input type="button" onclick="javascript:history.go(-1);"value="返回" />
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>