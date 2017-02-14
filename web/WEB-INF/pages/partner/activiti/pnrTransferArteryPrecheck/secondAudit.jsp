<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
var roleTree;
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
	   v.custom = function(){
	   			//获取审核状态的值
	   			var handleState = jq('input[name="initiatorHandleState"]:checked').val(); 
	   			if(handleState == 'handle'){
	   				var flag = jq("#daiweiAuditSceneHandleFlag").val();
	   				if(flag != '1'){
		   				if(confirm('工单尚未抽检是否确认提交?')){
				      	 	return true;
			      	  	}else{
			      	  		return false;
			      	  	}
	   				}else{
	   					return true;
	   				}
	   			}else{
	   				return true;
	   			}
		   }
   });
   
   
  function changeType1()
  {
		//$('phaseId').value = "DealTask";
   		//$('operateType').value = "0";
   }
   
   
</script>

<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/transfer_basis_noAttachment.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferArteryPrecheck/transfer_do_msg.jsp"%>

<div style="TEXT-ALIGN: center; height:20"></div>
 <!-- 工单基本信息end --> 

	<html:form action="/pnrTransferArteryPrecheck.do?method=secondAudit" styleId="theform" >	
			    <input type="hidden" name="testAudit" value="${testAudit}"/>
		        <input type="hidden" name="taskId" value="${taskId}">
		        
		        <input type="hidden" name="theme" value="${pnrTransferOffice.theme}">
		        <input type="hidden" name="themeId" value="${pnrTransferOffice.id}">
		        
		         <input type="hidden" name="linkName" value="daiweiAudit">
		         <input type="hidden" name="step" value="14">
		         
		         <input type="hidden" id="condition" name="condition" value="${condition}" />
		         <input type="hidden" id="daiweiAuditSceneHandleFlag" name="daiweiAuditSceneHandleFlag" value="${pnrTransferOffice.daiweiAuditSceneHandleFlag}" />
		         
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
		  <td class="label">审核状态*</td>
		  <td class="content">
		    <input type="radio" name="initiatorHandleState" value="handle"  checked=”checked”/>通过
		    <input type="radio" name="initiatorHandleState" value="rollback"/>不通过
		  </td>	
		  <td class="label">距离</td>
		  <td class="content">
			${distanceShow }
		 </td>			
		</tr>
		
		<tr>
		  <td class="label">决算金额-工费*</td>
		  <td class="content">
		    <input type="text" class="text" id="balanceOperatingCosts"  name="balanceOperatingCosts" value="${balanceOperatingCosts}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:元)
		  </td>
		  <td class="label">决算金额-材料费*</td>
		  <td class="content">
			<input type="text" class="text" id="balanceMaterialsCosts"  name="balanceMaterialsCosts" value="${balanceMaterialsCosts}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:元)
		 </td>
		</tr>
		
		 <tr>
		    
		    <td class="label">
		    	附件
			</td>	
			<td colspan="3">
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="daiweiAudit" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>	
			
		<tr>
 			<td class="label">
				审核意见*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入审批意见，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
</table>

	    <p/>	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				提交
			</html:submit>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 transferSitcher = new detailSwitcher();
  transferSitcher.init({
	container:'transferHistory',
  	handleEl:'div.history-item-title'
  });
</script> 