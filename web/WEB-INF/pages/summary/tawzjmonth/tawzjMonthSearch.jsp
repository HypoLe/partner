<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/tawzjMonths.do?method=search" styleId="tawzjMonthForm" method="post"> 

<fmt:bundle basename="config/ApplicationResources-summary">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawzjMonth.heading.lisst" /></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.team" />
		</td>
		<td class="content">
			<html:text property="team" styleId="team"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawzjMonth.team}" />
		</td>
		<td class="label">
			<fmt:message key="tawzjMonth.status" />
		</td>
		<td class="content">
		<html:select property="status" style="width:100px">
					<html:option value="" >请选择</html:option>
        			<html:option value="0" >待提交</html:option>
        			<html:option value="1" >待审核</html:option>
        			<html:option value="2" >审核通过</html:option>
        			<html:option value="3" >驳回</html:option>
        		</html:select>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="tawzjMonth.year" />
		</td>
		<td class="content">
			<html:text property="year" styleId="year"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawzjMonth.year}" />
		</td>
		<td class="label">
			<fmt:message key="tawzjMonth.month" />
		</td>
		<td class="content">
			<html:select property="month" style="width:100px">
					<html:option value="" >请选择</html:option>
        			<html:option value="01" >01</html:option>
        			<html:option value="02" >02</html:option>
        			<html:option value="03" >03</html:option>
        			<html:option value="04" >04</html:option>
        			<html:option value="05" >05</html:option>
        			<html:option value="06" >06</html:option>
        			<html:option value="07" >07</html:option>
        			<html:option value="08" >08</html:option>
        			<html:option value="09" >09</html:option>
        			<html:option value="10" >10</html:option>
        			<html:option value="11" >11</html:option>
        			<html:option value="12" >12</html:option>
        		</html:select>
		</td>
	</tr>
	<tr>
		
	</tr>


	
</table>
</fmt:bundle>
<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
		</td>		
	</tr>
</table>

<html:hidden property="id" value="${tawzjMonthForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>