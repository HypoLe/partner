<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<html:form action="/feeTotals.do?method=search" styleId="feeTotalForm" method="post" >
<center style="width:400">
	<table class="formTable">
		<caption>
			<div class="header center">查询光缆线路代理维护服务量及维护费用报价计算表</div>
		</caption>	
		<tr>
			<td class="label">
				年 
			</td>
			<td class="content" colspan="2">
				<select id="year" name="year" alt="allowBlank:false" >
						<option id="0" value="">请选择</option>
					<c:forEach begin="2009" end="2025" var="num">
						<c:if test="${feeTotalForm.year ==num}">
							<option id="${num}" value="${num}" selected="true">${num}年</option>
						</c:if>
						<c:if test="${feeTotalForm.year !=num}">
							<option id="${num}" value="${num}">${num}年</option>
						</c:if>					
					</c:forEach>
				</select>		
			</td>		
		</tr>
	</table>
	<input type="submit" class="btn" value="查询" />
</center>
</html:form>

<c:out value="${buttons}" escapeXml="false" />
<%@ include file="/common/footer_eoms.jsp"%>