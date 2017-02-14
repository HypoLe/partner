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
  function selectRes(){
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=openRejectView';
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferArteryPrecheck.do?method=listBacklog"+condition;
  }
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/transfer_basis_noAttachment.jsp"%>


<div style="height:20"></div>

<html:form action="/pnrTransferArteryPrecheck.do?method=doOneWorkOrderArchiving" styleId="theform" >
		    <input type="hidden" id="marker" name="marker" value="orderAudit" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.createWork}"><!-- 驳回人 -->
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->

			<input type="hidden" id="isSpotCheck" name="isSpotCheck" value="${pnrTransferOffice.state}" /><!-- 是否抽查标识 -->
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="step" name="step" value="15"><!-- 附件用 -->
            <input type="hidden" id="userId" name="userId" value="${userId}">
            <input type="hidden" id="linkName" name="linkName" value="orderAudit">
            <input type="hidden" id="orderAuditHandleState" name="orderAuditHandleState" value="handle" /><!-- 归档标识 -->
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />
            
<c:if test="${pnrTransferOffice.state eq '10'}">
	<table id="sheet" class="formTable" >				
	 		<tr>
			  <td class="label">审减金额-工费*</td>
			  <td class="content">
			    <input type="text" class="text" id="shenJianOperatingCosts"  name="shenJianOperatingCosts" value="${shenJianOperatingCosts}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:元)
			  </td>
			  <td class="label">审减金额-材料费*</td>
			  <td class="content">
				<input type="text" class="text" id="shenJianMaterialsCosts"  name="shenJianMaterialsCosts" value="${shenJianMaterialsCosts}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数'"/>(单位:元)
			 </td>
			</tr>
			
			 <tr>
			    <td class="label">
			    	附件
				</td>	
				<td colspan="3">
			    <eoms:attachment name="sheetMain" property="sheetAccessories" 
			            scope="request" idField="orderAudit" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 		          				
			    </td>
		   </tr>
	</table>
</c:if>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
			<c:if test="${pnrTransferOffice.state eq '10'}">
				<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
					归档
				</html:submit>&nbsp;&nbsp;
			</c:if>
			
			<c:if test="${pnrTransferOffice.state ne '10'}">
				<html:submit styleClass="btn" property="method.save" onclick="javascript:return confirm('工单尚未抽检是否确认提交?');" styleId="method.save">
					归档
				</html:submit>&nbsp;&nbsp;
				<!--bohui 删除16-02-29 说有bug待查-->
	    	</c:if>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
