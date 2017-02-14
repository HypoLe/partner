<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<bean:define id="nodeId"    name="CtTableTheme" property="nodeId" />
<bean:define id="themeName" name="CtTableTheme" property="themeName" />

<script type="text/javascript">
var tree;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctContentsForm'});
	var config = {
		btnId:'themeName',
		treeDataUrl:'${app}/partner/contract/ctTableThemes.do?method=getNodesRadioTree',
		treeRootId:'<%=nodeId%>',
		treeRootText:'<%=themeName%>',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'themeName',
		saveChkFldId:'THEME_ID'
	}
	tree = new xbox(config);
});

function onTableChg(table){
    var selValue = table.options[table.options.selectedIndex].value;
    var applyId = document.getElementById("applyId").value;
	var url = '${app}/partner/contract/ctContentss.do?method=add&TABLE_ID='+ selValue+'&applyId='+applyId;
	location.href = url;
}

function addHiddenValue(){
	var draftButton=document.getElementById("draft");
	draftButton.value="yes";
	return true;
}

</script>

<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="PARTY_A_ID" handler="PARTY_A"
		textField="PARTY_A"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="tree" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='单位' 
	  	valueField="PARTY_B_ID" handler="PARTY_B"
		textField="PARTY_B"
		checktype="dept" single="true"		
	  ></eoms:xbox>



<html:form action="/ctContentss.do?method=save" styleId="ctContentsForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<!-- 合同状态：1-草稿，2-有效，3-失效，4-删除 -->
<input type="hidden" name="TableInfo/CONTRACT_STATUS" value="2" />	

<!-- 合同申请相关 -->
<input type="hidden" id="applyId" name="applyId" value="${applyId}"/>

*号为必填内容

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContents.form.heading"/>&nbsp;新增</div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="ctContents.tableId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:select property="TableInfo/TABLE_ID" styleId="TABLE_ID"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请选择合同库...'" value="${TABLE_ID}" onchange="onTableChg(this)">
			    <html:optionsCollection name="CtTableGeneralList" value="id" label="tableChname"></html:optionsCollection>
			</html:select>
		</td>

		<td class="label">
			<fmt:message key="ctContents.themeId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">								
			<input type="text"   id="themeName" name="themeName" class="text" readonly="readonly" value="" alt="allowBlank:false,vtext:'请选择合同分类(字典)...'"/>
			<input type="hidden" id="THEME_ID"  name="TableInfo/THEME_ID" value="" />		
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="ctContents.createUser" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <bean:write name="SessionUserName"/><!-- 创建人 -->
		    <input type="hidden" name="TableInfo/CREATE_USER" value="<bean:write name="SessionUserId"/>" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.createDept" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <bean:write name="SessionDeptName"/><!-- 创建人所在部门 -->
		    <input type="hidden" name="TableInfo/CREATE_DEPT" value="<bean:write name="SessionDeptId"/>" />	
		</td>
	</tr>

	<tr>
		<td class="label">
			合同标题&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTRACT_TITLE" id="CONTRACT_TITLE" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
	</tr>
	<!-- 
	<tr>
		<td class="label">
			合同编号&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTRACT_NO" id="CONTRACT_NO" value="" maxLength="32" class="text max" alt="allowBlank:false,vtext:'请输入合同编号...'" />		 
		</td>
	</tr>
	 -->
	<tr>	
		<td class="label">
			服务期限&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/SERVICR_LIMIT" id="SERVICR_LIMIT" value="" maxLength="32" class="text max" alt="allowBlank:false,vtext:'请输入服务期限...'" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" />		 
		</td>
		<td class="label">
			合同总额(万元)&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/AMOUNT" id="AMOUNT" value="" class="text max" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数',maxLength:10" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_A" id="PARTY_A" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入甲方...'" />		
		    <input type="hidden" name="TableInfo/PARTY_A_ID" id="PARTY_A_ID" value="" maxLength="32" class="text max" /> 
		</td>
		<td class="label">
			乙方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_B" id="PARTY_B" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入乙方...'" />		 
			<input type="hidden" name="TableInfo/PARTY_B_ID" id="PARTY_B_ID" value="" maxLength="32" class="text max"  />	
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_A_CONTACT" id="PARTY_A_CONTACT" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入甲方联系方式...'" />		 
		</td>
		<td class="label">
			乙方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_B_CONTACT" id="PARTY_B_CONTACT" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入乙方联系方式...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/PARTY_A_INTERFACE" id="PARTY_A_INTERFACE" value="" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入甲方接口规范...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			乙方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/PARTY_B_INTERFACE" id="PARTY_B_INTERFACE" value="" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入乙方接口规范...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			责权描述&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/DUTY_DESCRIBE" id="DUTY_DESCRIBE" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入责权描述...'" ></textarea>		 
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核内容&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/QUALITY_CHECK" id="QUALITY_CHECK" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入质量考核内容...'" ></textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/QUALITY_CHECK_WAY" id="QUALITY_CHECK_WAY" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入质量考核方式...'" ></textarea>			 
		</td>
	</tr>	
	<tr>
		<td class="label">
			争议解决方案&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/DERAIGN_WAY" id="DERAIGN_WAY" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入争议解决方案...'" ></textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label">
			业务变更管理&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/CHANGING_MANAGE" id="CHANGING_MANAGE" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入业务变更管理...'" ></textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label">
			附则&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/OTHER_RULE" id="OTHER_RULE" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入附则...'" ></textarea>			 
		</td>
	</tr>	
	<tr>
		<td class="label">
			附件&nbsp;<font color='red'></font>
		</td>
		<td class="content" colspan="3">
		<eoms:attachment name="TableInfo/ACCESSORIES" property="" scope="request" idField="TableInfo/ACCESSORIES" appCode="contract" startsWith="0" />
		</td>
	</tr>	
	<tr>
		<td class="label">
			备注&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/REMARK" id="REMARK" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入备注...'" ></textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label" colspan="4">
			附加信息
		</td>
	</tr>
	<input type="hidden" name="TableInfo/FEE_INFO_ID" id="FEE_INFO_ID" value="" maxLength="32" />
<%@ include file="ctContentColAdd.jsp"%>
</table>

<br>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="提交到合同库" />&nbsp;&nbsp;
			<input type="submit" class="btn" value="存到草稿箱" onclick="return addHiddenValue()"/>
			<input type="hidden" id="draft" name="draft" value=""/>
		</td>
	</tr>
	
</table>

</fmt:bundle>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>