<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>


<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferArteryPrecheck/transfer_basis.jsp"%>
 
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferArteryPrecheck/transfer_do_msg.jsp"%>

<c:if test="${samplingFlag eq '1'}">
<div style="height:20"></div>
	<html:form action="/pnrTransferArteryPrecheck.do?method=saveSamplingJudgments" styleId="theform" >
	<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}" />
	<input type="hidden" id="condition" name="condition" value="${condition}" />
		<table id="sheet" class="formTable" style="width:100%">
			<tr>
				   <td class="label" style="width:10%">
						抽检结果<font color="red">*</font>
					</td>
					<td class="content" style="width:90%">
						<textarea class="textarea max" name="samplingJudgments" id="samplingJudgments" alt="allowBlank:false,maxLength:250,vtext:'请填写抽检结果'" ${pnrTransferOffice.samplingState ne '2'? '':'disabled="disabled"'}>${pnrTransferOffice.samplingJudgments}</textarea>
			     </td>
			</tr>
		</table>
	
		<div class="form-btns" id="btns" style="display:block">
			<c:if test="${pnrTransferOffice.samplingState ne '2'}">
				<html:submit styleClass="btn" property="method.save" styleId="method.save">
					提交
				</html:submit>&nbsp;&nbsp;
			</c:if>
		</div>
	</html:form>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 switcher = new detailSwitcher();
  switcher.init({
	container:'history',
  	handleEl:'div.history-item-title'
  });
</script>  