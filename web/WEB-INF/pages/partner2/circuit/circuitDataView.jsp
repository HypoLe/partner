<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.google.common.base.Objects"%>
<script type="text/javascript">
Ext.onReady(function(){
});

function checkValue(evt){
		document.forms[0].submit();
}
</script>

<html:form action="circuit.do?method=goToDataView" method="post"
	styleId="theform">
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">报表类型</td>
			<td class="content"><select size='1' name='styleId' id='styleId'
				class='select'>
				<option value="compareCity" selected="selected">市县级单位数据对比</option>
				<option value="compareMonitor">代维公司数据对比</option>
			</select></td>
			<td class="label">分公司</td>
			<td class="content"><eoms:comboBox name="city" id="city"
				sub="country" initDicId="1012503" alt="allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">年份</td>
			<td class="content"><select size='1' name='yearFlag'
				id='yearFlag' class='select'>
				<option value='2009'>2009</option>
				<option value='2010'>2010</option>
				<option value='2011' selected="selected">2011</option></td>
			<td class="label">月份</td>
			<td class="content"><select size='1' name='monthFlag'
				id='monthFlag' class='select'>
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
	<input type="button" class="submit" value="生成数据报表"
		onclick="checkValue(this)" />
</html:form>

<%
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
	<display:table name="circuitList" cellspacing="0" cellpadding="0"
		id="circuitList" class=" table circuitList"  export="true"
		requestURI="/duty/circuit.do?method=goToDataView">
			<display:column sortable="true" headerClass="sortable" title="${cityText }">
				<eoms:id2nameDB id="${circuitList.city}"
					beanId="tawSystemAreaDao" />
			</display:column>
			<display:column property="country" sortable="true" headerClass="sortable" title="数据" />
			<display:setProperty name="export.rtf" value="false"/>
			<display:setProperty name="export.pdf" value="false"/>
			<display:setProperty name="export.xml" value="false"/>
			<display:setProperty name="export.csv" value="false"/>
	</display:table>
</logic:present>
<%@ include file="/common/footer_eoms.jsp"%>
