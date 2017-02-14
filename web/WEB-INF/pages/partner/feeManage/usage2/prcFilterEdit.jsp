<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>


<form action="${app}/partner/feeManageUsage2/usage2.do?method=savePrcFilter"
	method="post" id="PriceMajorForm" name="PriceMajorForm"/>
	<div>
		<font color="red">${errormsg}</font>
	</div>
	<input type="hidden" id="parentId" name="parentId"  value="${parentId}"/>
	<input type="hidden" id="id" name="id"  value="${prcFilter.id}"/>
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					编辑过滤条件
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>dictYN
			</td>
			<td>
			<select id="dictYN">
			<option value="true">是</option>
			<option value="false">否</option>
			
			</select>
			</td>

			<td class="label">
				<font color='red'> * </font>prtDictId：
			</td>
			<td>


				<input class="text" type="text" name="prtDictId" id="prtDictId"  value="${prcFilter.prtDictId}"alt="allowBlank:false"/>

			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>name
			</td>
			<td>
			<input class="text" type="text" name="name" id="name"  value="${prcFilter.name}"alt="allowBlank:false"/>
			
			</td>

			<td class="label">
				<font color='red'> * </font>businessName：
			</td>
			<td>


				<input class="text" type="text" name="businessName" id="businessName"  value="${prcFilter.businessName}"alt="allowBlank:false"/>

			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>dictTyp
			</td>
			<td>
			<input class="text" type="text" name="dictTyp" id="dictTyp"  value="${prcFilter.dictTyp}"alt="allowBlank:false"/>
			
			</td>

			<td class="label">
				<font color='red'> * </font>sub：
			</td>
			<td>


				<input class="text" type="text" name="sub" id="sub"  value="${prcFilter.sub}"alt="allowBlank:false"/>

			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>htmlType
			</td>
			<td>
			<input class="text" type="text" name="htmlType" id="htmlType"  value="${prcFilter.htmlType}"alt="allowBlank:false"/>
			
			</td>

			<td class="label">
				<font color='red'> * </font>text：
			</td>
			<td>


				<input class="text" type="text" name="text" id="text"  value="${prcFilter.text}"alt="allowBlank:false"/>

			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>sourceColName
			</td>
			<td>
			<input class="text" type="text" name="sourceColName" id="sourceColName"  value="${prcFilter.sourceColName}"alt="allowBlank:false"/>
			
			</td>

			<td class="label">
				<font color='red'> * </font>sourceTableName：
			</td>
			<td>


				<input class="text" type="text" name="sourceTableName" id="sourceTableName"  value="${prcFilter.sourceTableName}"alt="allowBlank:false"/>

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