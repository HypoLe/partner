<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<script language="JavaScript" src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<html:form action="appraisal.do?method=statisticsFor" method="post">
<div id="tableContainer" style="border:1px solid #98c0f4;padding:5px;width:98%;">
	<table id="sheet" class="formTable">
	<tr>
		<td class="label">统计年份</td>
			<td class="content" colspan=4>
			<select name="year">
				<option value="2011">2011</option>
				<option value="2012" selected="selected">2012</option>
				<option value="2013">2013</option>
			</select>

			</td>

		</tr>
		
		<tr>
			<td class="label">开始月份</td>
			<td class="content">
				<select name="month">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
				<option value="10">10</option>
				<option value="11">11</option>
				<option value="12">12</option>
				</select>
			</td>
		</tr>
		<tr>
		<td class=""><input type="submit" value="统计"/></td>
		</tr>
	</table><input type="hidden" value="sss"/>
</div>
<div>


<c:if test=""></c:if>
			<table cellpadding="0" class="table" cellspacing="0">
				<thead>
					<tr>
						<logic-el:present name="headList">
							<c:forEach items="${headList}" var="headlist">
								<th>
									${headlist}
								</th>

							</c:forEach>

						</logic-el:present>
					</tr>
				</thead>
				<logic-el:present name="tableList">
					<tbody>
						<c:forEach items="${tableList}" var="mode">
							<tr>
							<td><eoms:id2nameDB id="${mode.city }" beanId="tawSystemAreaDao"/> </td>
							<td><eoms:id2nameDB id="${mode.monitorCompany}" beanId="partnerDeptDao"/> </td>
							<td>${mode.appraisalrealscore } </td>
							<c:if test="${!empty mode.flag}">
							<td rowspan="${mode.flag}">
											${mode.averageScore}
							</td>
							</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</logic-el:present>
			</table>
		</div>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>