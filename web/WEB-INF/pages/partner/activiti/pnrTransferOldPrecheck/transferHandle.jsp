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
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOldPrecheck/transfer_basis.jsp"%>


<div style="height:20"></div>

<html:form action="/pnrTransferOldPrecheck.do?method=transferHandle" styleId="theform" >
	
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="title" value="${pnrTransferOffice.theme}">
            <input type="hidden" name="titleId" value="${pnrTransferOffice.id}">
            <input type="hidden" name="handleState" value="handle">
            <input type="hidden" name="taskAssignee" value="${auditor}">
            <!--公客接口时使用-->
            <input type="hidden" name="auditor" value="${auditor}">

<table id="sheet" class="formTable" >
				
 		<tr>
			<td class="label">预检预修是否完成*</td>
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
				预检预修处理人+手机号*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填入处理人'"></textarea>
	     </td>
		</tr>
</table>

	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				回复
			</html:submit>&nbsp;&nbsp;
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>