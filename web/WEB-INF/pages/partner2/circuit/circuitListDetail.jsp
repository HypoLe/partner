<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<script type="text/javascript">
function openQuery(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
Ext.onReady(function(){
});
</script>
<div style="border:1px solid #98c0f4;padding:5px;width:98%;"
	class="x-layout-panel-hd"><img
	src="${app}/images/icons/search.gif" align="absmiddle"
	style="cursor:pointer" /> <span id="openQuery" style="cursor:pointer"
	onclick="openQuery(this);">查询界面</span></div>

<div id="listQueryObject"
	style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<html:form action="circuit.do?method=search" method="post">
	<fieldset><br />
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">分公司</td>
			<td class="content"><eoms:comboBox name="cityStringExpression"
				id="cityStringExpression" sub="countryStringExpression"
				initDicId="1012503" /></td>
			<td class="label">归属县公司</td>
			<td><eoms:comboBox name="countryStringExpression"
				id="countryStringExpression" /></td>
		</tr>
		<tr>
			<td class="label">代维公司</td>
			<td class="content"><eoms:comboBox name="monitorCompanyStringExpression"
				id="monitorCompanyStringExpression"
				initDicId="1012504" /></td>
			<td class="label">数据状态</td>
			<td class="content"><select size='1'
				name='statusStringExpression' id='statusStringExpression'
				class='select'>
				<option value=''>请选择</option>
				<option value='GO_TO_MONITOR'>分公司提交阶段</option>
				<option value='WAIT_FOR_AUDIT'>代维公司核减阶段</option>
				<option value='WAIT_FOR_ARGREE'>分公司确认阶段</option>
				<option value='WAIT_FOR_SUBMIT'>省公司提交阶段</option>
				<option value='WAIT_FOR_GENERATE'>数据汇总阶段</option>
				<option value='FINISH_GENERATE'>统计完成阶段</option>
				<option value='ERROR_DATA'>无效数据</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1'
				name='yearFlagStringExpression' id='yearFlagStringExpression'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011'>2011年</option>
				<option value='2012'>2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1'
				name='monthFlagStringExpression' id='monthFlagStringExpression'
				class='select'>
				<option value=''>请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit></fieldset>
</html:form></div>

	<logic:present name="circuitList" scope="request">
		<display:table name="circuitList" cellspacing="0" cellpadding="0"
			id="circuitList" pagesize="15" class="table circuitList"
			export="true" requestURI="/duty/circuit.do?method=list" sort="list"
			partialList="true" size="resultSize">

			<display:column sortable="true" headerClass="sortable" title="分公司">
				<eoms:id2nameDB id="${circuitList.city}"
					beanId="tawSystemAreaDao" />
			</display:column>
			<display:column property="faultPart" sortable="true"
				headerClass="sortable" title="故障段落" />
			<display:column property="faultBeginTime" sortable="true"
				headerClass="sortable" title="故障开始时间" />
			<display:column property="faultEndTime" sortable="true"
				headerClass="sortable" title="故障结束时间" />
			<display:column property="faultDuring" sortable="true"
				headerClass="sortable" title="故障历时" />
			<display:column sortable="true" headerClass="sortable" title="光缆类别" property="cableSort" />
			<display:column sortable="true" headerClass="sortable" title="归属县公司">
				<eoms:id2nameDB id="${circuitList.country}"
					beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="代维公司">
				<eoms:id2nameDB id="${circuitList.monitorCompany}"
					beanId="tawSystemAreaDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="光缆产权" property="cableOwner" />
			<display:column property="ifEffect" sortable="true"
				headerClass="sortable" title="是否影响业务" />
			<display:column property="faultReason" sortable="false"
				headerClass="sortable" title="故障原因" />
			<display:column property="faultResult" sortable="false"
				headerClass="sortable" title="处理结果" />
			<display:column property="statusCnName" sortable="true"
				headerClass="sortable" title="记录状态" />
			<display:column property="yearFlag" sortable="true"
				headerClass="sortable" title="年" />
			<display:column property="monthFlag" sortable="true"
				headerClass="sortable" title="月" />
			<display:column sortable="false" headerClass="sortable"
				title="记录详情与历史" media="html">
				<a id="${circuitList.id }"
					href="circuit.do?method=goToSingleDetail&id=${circuitList.id}"
					target="blank" shape="rect">详情</a>
				<img src="${app}/images/icons/panel-new.gif">
				<a id="${circuitList.id }"
					href="circuit.do?method=goToHistory&id=${circuitList.id}"
					target="blank" shape="rect">处理过程</a>
			</display:column>
			<!-- Exclude the formats i do not need. -->
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
	</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>
