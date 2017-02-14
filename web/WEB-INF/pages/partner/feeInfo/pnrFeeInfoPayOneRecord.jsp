<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<div class="ui-widget-content ui-corner-top " style="margin-top: 15">
<table class="formTable">
	<caption>
		<div class="header center">合同费用信息</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle" >
			合同编号：
		</td>
		<td class="content" >
					${pnrFeeInfoMainForm.contractNO}	
		</td>
		<td class="label" style="vertical-align:middle">
			合同名称:
		</td>
		<td class="content" colspan="3">
			${pnrFeeInfoMainForm.contractName}
		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnit}" beanId="tawSystemDeptDao" />		
		</td>
<td class="label" style="vertical-align:middle">
			乙方:
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnit}" beanId="tawSystemDeptDao" />	
		</td>
		
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			甲方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payUnitUser}" beanId="tawSystemUserDao" />
		</td>

		<td class="label" style="vertical-align:middle">
			乙方负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.collectUnitUser}" beanId="tawSystemUserDao" />
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			付款负责人：
		</td>
		<td class="content">
			<eoms:id2nameDB id="${pnrFeeInfoMainForm.payMoneyUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label" style="vertical-align:middle">
			付款方式:
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.payWay}
		</td>
	</tr>
	<tr>
		
		<td class="label" style="vertical-align:middle">
			合同金额(万元):
		</td>
		<td class="content"  >
			${pnrFeeInfoMainForm.contractAmount}
		</td>
		
	</tr>
	
	<tr>
		<td class="label" style="vertical-align: middle">
			合同开始时间：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.startTime}
		</td>
		<td class="label" style="vertical-align: middle">
			合同结束时间：
		</td>
		<td class="content">
			${pnrFeeInfoMainForm.endTime}
		</td>
	</tr>
	</table>
<form action="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=deal" method="post" id="myForm" >
	<logic:notEmpty name="pnrFeeInfoMainList" scope="request">
		<table id="sheet" class="formTable">
			<input type="hidden" name="dealIds" id="dealIds" />
			<input type="hidden" name="listType" value="${listType}" />
			<input type="hidden" name="myTextType" value="plainText" />
			<tr>
				<td class="label">操作类型*</td>
				<td class="content">
				<select id="myOption" name="operateType" >
					<option selected="selected" value="3">
						确认通过
					</option>
					<option value="2">
						驳回
					</option>
				</select>
			</td>
			</tr>
			<tr>
				<td class="label">派发对象</td>
				<td colspan="3">
				<span id="mySpan" >合同付款负责人</span>
				</td>
			</tr>
			<tr >
				<td class="label">备注*</td>
				<td colspan="3"><textarea id="myText"
					class="textarea max" name="myText" 
					alt="width:500,allowBlank:false"></textarea></td>
			</tr>
		</table>
		<fieldset>
			<legend> 操作 </legend>
			<input type="submit" class="btn" value="确定" /> 
			<input type="reset" class="btn" value="重置" /> 
		</fieldset>
	</logic:notEmpty>
</form>
</div>
<%@ include file="/common/footer_eoms.jsp"%>