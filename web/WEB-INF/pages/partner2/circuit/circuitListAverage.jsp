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
<html:form action="circuit.do?method=searchAverage" method="post">
	<fieldset><br />
	<table id="sheet" class="formTable">
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
		<tr>
			<td class="label">分公司</td>
			<td class="content"><eoms:comboBox name="cityStringExpression"
				id="cityStringExpression" sub="countryStringExpression"
				initDicId="1012503" /></td>
			<td class="label">归属县公司</td>
			<td><eoms:comboBox name="countryStringExpression"
				id="countryStringExpression" /></td>
		</tr>
	</table>
	<br />
	<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit></fieldset>
</html:form></div>

<logic:present name="circuitList" scope="request">
	<display:table name="circuitList" cellspacing="0" cellpadding="0"
		id="circuitList" pagesize="15" class="table circuitList" export="true"
		requestURI="/duty/circuit.do?method=listAverage" sort="list"
		partialList="true" size="resultSize">

		<display:column sortable="true" headerClass="sortable" title="分公司">
			<eoms:id2nameDB id="${circuitList.city}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归属县公司">
			<eoms:id2nameDB id="${circuitList.country}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column property="duringLevel1" sortable="true"
			headerClass="sortable" title="一干(分钟)" />
		<display:column sortable="true" headerClass="sortable" title="一干(小时)">
			<c:if test="${circuitList.duringLevel1 == 0 }">00:00</c:if>
			<c:if test="${circuitList.duringLevel1 != 0 }">${circuitList.duringLevel1Hour}</c:if>
		</display:column>
		<display:column property="duringLevel2" sortable="true"
			headerClass="sortable" title="二干(分钟)" />
		<display:column sortable="true" headerClass="sortable" title="二干(小时)">
			<c:if test="${circuitList.duringLevel2 == 0 }">00:00</c:if>
			<c:if test="${circuitList.duringLevel2 != 0 }">${circuitList.duringLevel2Hour}</c:if>
		</display:column>
		<display:column property="duringConnect" sortable="true"
			headerClass="sortable" title="本地汇聚(分钟)" />
		<display:column sortable="true" headerClass="sortable" title="本地汇聚(小时)">
			<c:if test="${circuitList.duringConnect == 0 }">00:00</c:if>
			<c:if test="${circuitList.duringConnect != 0 }">${circuitList.duringConnectHour}</c:if>
		</display:column>
		<display:column property="duringCore" sortable="true"
			headerClass="sortable" title="本地核心(分钟)" />
		<display:column sortable="true" headerClass="sortable" title="本地核心(小时)">
			<c:if test="${circuitList.duringCore == 0 }">00:00</c:if>
			<c:if test="${circuitList.duringCore != 0 }">${circuitList.duringCoreHour}</c:if>
		</display:column>
		<display:column property="duringJoin" sortable="true"
			headerClass="sortable" title="接入(分钟)" />
		<display:column sortable="true" headerClass="sortable" title="接入(小时)">
			<c:if test="${circuitList.duringJoin == 0 }">00:00</c:if>
			<c:if test="${circuitList.duringJoin != 0 }">${circuitList.duringJoinHour}</c:if>
		</display:column>
		<display:column property="yearFlag" sortable="true" headerClass="sortable" title="年度" />
		<display:column property="monthFlag" sortable="true" headerClass="sortable" title="月度" />
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />

	</display:table>
</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>
