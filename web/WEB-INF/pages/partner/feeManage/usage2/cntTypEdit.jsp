<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>


<form action="${app}/partner/feeManageUsage2/usage2.do?method=saveCntTyp"
	method="post" id="PriceMajorForm" name="PriceMajorForm"/>
	<div>
		<font color="red">${errormsg}</font>
	</div>
	<input type="hidden" id="parentId" name="parentId"  value="${parentId}"/>
	<input type="hidden" id="id" name="id"  value="${cntTyp.id}"/>
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					编辑专业
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>计次类型名：
			</td>
			<td>
				<input type="text" class="text" name="name" id="name" value="${cntTyp.name}"
					alt="allowBlank:false,vtext:'考核指标名不能为空 不能超出1000个汉字！',maxLength:2000"/>
			</td>

			<td class="label">
				<font color='red'> * </font>计次类型字典号：
			</td>
			<td>


				<input class="text" type="text" name="cntTypId"id="cntTypId"  value="${cntTyp.cntTypId}"alt="allowBlank:false"/>

			</td>
		</tr>
	
	</table>
	<tr>
		<td class="label">
		</td>
		<td>
			<input type="submit" value="提交"  />
		</td>
	</tr>

</form>
<%@ include file="/common/footer_eoms.jsp"%>