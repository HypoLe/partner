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
		var url = '${app}/activiti/pnrTransferOldPrecheck/pnrTransferOldPrecheck.do?method=openRejectView';
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	    window.showModalDialog(url,window,sFeatures);
	}
 
   function refresh(){//此刷新函数被弹出的子模态窗口调用。
	    window.location.href = "${app}/activiti/pnrTransferOldPrecheck/pnrTransferOldPrecheck.do?method=listBacklog";
	}
 
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOldPrecheck/transfer_basis.jsp"%>


<div style="height:20"></div>

<html:form action="/pnrTransferOldPrecheck.do?method=transferProgram" styleId="theform" >
			<!-- 驳回需要的参数 -->
			<input type="hidden" id="marker" name="marker" value="program" /><!-- 流程标识 -->	
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.createWork}">
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- ID -->
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            
            <input type="hidden" name="userId" value="${userId}">           
            <input type="hidden" name="handleState" value="handle">
            <input type="hidden" name="approve" value="${pnrTransferOffice.cityCSJ}">
            <input type="hidden" name="step" value="2">
            <input type="hidden" name="hjflag" value="方案制定">

<table id="sheet" class="formTable" >
				
 		<tr>
			<td class="label">附件</td>
			<td class="content" >
				<eoms:attachment name="sheetMain" property="" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
			</td>
 		<td class="label" rowspan="4" align="center">简要描述</td>
			<td class="content" rowspan="4">
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填入处理人'"></textarea>
			</td>
			
		</tr>		
		<tr>
 			<td class="label">
				制定人
			</td>
			<td class="content" >
			<input class="text" type="text" name="teskAssignee"/>
			</td>
		</tr>
		<tr>
 			<td class="label">
				单位
			</td>
			<td class="content" >
			<input class="text" type="text" name="companyName"/>
			</td>
		</tr>
		<tr>
 			<td class="label">
				电话
			</td>
			<td class="content" >
			<input class="text" type="text" name="telephone"/>
			</td>
		</tr>
</table>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				提交
			</html:submit>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>