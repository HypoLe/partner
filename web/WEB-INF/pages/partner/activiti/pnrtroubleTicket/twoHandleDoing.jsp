<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
  }
   );


  function changeType1()
  {
		
  }
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/common/trouble_basis.jsp"%>


<div style="height:20"></div>

<html:form action="/pnrTroubleTicket.do?method=twoHandleDoing" styleId="theform" >
	
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="title" value="${pnrTroubleTicket.theme}">
            <input type="hidden" name="titleId" value="${pnrTroubleTicket.id}">
            <input type="hidden" name="towHandleState" value="handle">
            <input type="hidden" name="taskAssignee" value="${auditor}">

<table id="sheet" class="formTable" >
		<tr>
			<td class="label">交通方式*</td>
			<td class="content">
				<eoms:comboBox name="transport" id="transport" defaultValue=""
					initDicId="1010109" alt="allowBlank:false" styleClass="select"/>
			</td>
		  
		  <td class="label">里程*</td>
		  <td class="content">
		    <input type="text" class="text" name="mileage" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:公里)
		  </td>	
 			
		</tr>
		
 		<tr>
			<td class="label">故障是否恢复*</td>
			<td class="content" colspan="3">
				<eoms:comboBox name="isRecover" id="isRecover" defaultValue=""
						initDicId="10301" alt="allowBlank:false" styleClass="select"/>
			</td>
 		
			
		</tr>		
		<tr>
 			<td class="label">
				处理描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入处理描述，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
		<tr>
 			<td class="label">
				故障处理人*
			</td>
			<td class="content" colspan="3">
			<!--故障处理人-->
			<fieldset>
 				<legend>
				 <span id="roleName1">
			 		 故障处理人
			 	 </span>
  				</legend>
			    <div class="x-form-item" >
					<eoms:chooser id="sendObject1"  panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDept',rootId:'${country}'}]" config="{returnJSON:true,showLeader:true}"
					   category="[{id:'dealPerformer2',text:'处理',allowBlank:false,childType:'user',limit:20,vtext:'请选择处理对象'}]" 
					  data="" />
				</div>	
			</fieldset>	
			</td>
		</tr>
</table>

	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				回复
			</html:submit>&nbsp;&nbsp;
			<input type="button" onclick="javascript:history.go(-1);" value="返回"/>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>