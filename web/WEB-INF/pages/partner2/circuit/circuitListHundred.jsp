<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.google.common.base.Objects"%>
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
function checkValue(evt){
	document.forms[0].submit();
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
<html:form action="circuit.do?method=searchHundred" method="post">
	<fieldset><br />
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">报表类型</td>
			<td class="content"><select size='1' name='styleId' id='styleId'
				class='select'>
				<option value="selectoption" selected="selected">请选择</option>
				<option value="compareCity" >市县级单位数据对比</option>
				<option value="compareMonitor">代维公司数据对比</option>
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
	<input type="button" class="submit" value="提交查询结果"
		onclick="checkValue(this)" />
	</fieldset>
</html:form></div>
<div>
</div>
<%
String page1 = null;
String page2 = null ;
page1 = String.valueOf(request.getAttribute("page1"));
page2 = String.valueOf(request.getAttribute("page2"));
if(page1==null||page1.equals("null")){
	page1 = "block";
	page2 = "none" ;
}

	String type= Objects.firstNonNull(request.getAttribute("category"),"").toString();
	System.out.print("Todoo"+type);
	if(type.equals("type1")){
		request.setAttribute("cityText","市级单位");
	}else if(type.equals("type2")){
		request.setAttribute("cityText","代维公司");
	}else if(type.equals("type3")){
		request.setAttribute("cityText","县级单位");
	}else if(type.equals("type4")){
		request.setAttribute("cityText","代维公司");
	}else{
		//Do nothing.
	}
	
%>


<logic:present name="circuitList" scope="request">
	<div id = 'page1' style="display:<%=page1 %>" >
	<display:table name="circuitList" cellspacing="0" cellpadding="0" id="circuitList" pagesize="15"
		class="table circuitList" export="true" requestURI="/duty/circuit.do?method=listHundred" sort="list"
		partialList="true" size="resultSize"  > 
		
		<display:column sortable="true" headerClass="sortable" title="分公司" >
			<eoms:id2nameDB id="${circuitList.city}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归属县公司" >
			<eoms:id2nameDB id="${circuitList.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${circuitList.monitorCompany}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column property="cableLength" sortable="true" headerClass="sortable" title="光缆长度(皮长公里)" />
		<display:column property="faultDuring" sortable="true" headerClass="sortable" title="阻断历时(分钟)" />
		<display:column property="yearFlag" sortable="true" headerClass="sortable" title="年度" />
		<display:column property="monthFlag" sortable="true" headerClass="sortable" title="月度" />
		<display:column property="hundredFault" sortable="true" headerClass="sortable" title="百公里阻断历时(小时)" />
		 <!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false"/>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
		
	</display:table>
	</div>
	<div id='page2' style="display:<%=page2 %>" >
	<display:table name="circuitList" cellspacing="0" cellpadding="0"
		id="circuitList" class=" table circuitList"  export="true"
		requestURI="/duty/circuit.do?method=listHundred" >
			<display:column sortable="true" headerClass="sortable" title="${cityText }">
				<eoms:id2nameDB id="${circuitList.city}"
					beanId="IItawSystemDictTypeDao" />
			</display:column>

		<display:column property="cableLength" sortable="true" headerClass="sortable" title="光缆长度（皮长公里)" />
		<display:column property="faultDuring" sortable="true" headerClass="sortable" title="阻断历时(分钟)" />
		<display:column property="yearFlag" sortable="true" headerClass="sortable" title="年度" />
		<display:column property="monthFlag" sortable="true" headerClass="sortable" title="月度" />
		
			<display:column property="country" sortable="true" headerClass="sortable" title="百公里阻断历时(小时)" />
			<display:setProperty name="export.rtf" value="false"/>
			<display:setProperty name="export.pdf" value="false"/>
			<display:setProperty name="export.xml" value="false"/>
			<display:setProperty name="export.csv" value="false"/>
	</display:table>
	</div>
</logic:present>


<%@ include file="/common/footer_eoms.jsp"%>