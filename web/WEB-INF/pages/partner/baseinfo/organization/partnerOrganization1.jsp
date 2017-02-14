<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ include file="/common/body.jsp"%>
	<table class="formTable" style="width:100%" align="center" id="all">
					<tr  id="trTop">
						<td class="label" style="width:320px;" colspan="4" align="center">
							<span style=""><b>陕西省代维公司组织一览表</b></span>
						</td>
					</tr>
					<tr>
						<td class="label"><b>省代维公司</b></td>
						<td class="label"><b>地市代维公司</b></td>
						<td class="label"><b>区县代维公司</b></td>
						<td class="label"><b>代维小组</b></td>
					</tr>
						<c:forEach items="${tableList}" var="tdList">
							<tr>
								<c:forEach items="${tdList}" var="td" varStatus="i">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
											    	${td.name}
												</td>
											</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
	</table>
<%@ include file="/common/footer_eoms.jsp"%>