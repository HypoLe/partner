<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>









	

	<table id="sheet" class="formTable">
		<tr>
			<td class="label">
				考核指标名
			</td>
			<td>
				${quota.idctNm }
			</td>

			<td class="label">
				权重
			</td>
			<td>
				${quota.proportion}
			</td>
		</tr>
		<tr>
			<td class="label">
				分数
			</td>
			<td>
				${quota.fraction}
			</td>
			<td class="label">
				评分说明
			</td>
			<td class="content">
			  <pre>${quota.scoreExpl}</pre>
			</td>
			
		</tr>
		<tr>
			<td class="label">
				指标实际定义
			</td>
			<td class="content">
				<pre>${quota.idCtdFnt}</pre>
			</td>
		
			<td class="label">
				评分类型
			</td>
			<td class="content">
				 <c:if test="${quota.scoreTyp=='0'}"> 加分</c:if>
				 <c:if test="${quota.scoreTyp=='1'}"> 减分</c:if>
				 <c:if test="${quota.scoreTyp=='2'}"> 指标计算</c:if>
			
			</td>

		</tr>
		<tr>
		<td class="label">
				备注
			</td>

			<td class="content">
				<pre>${quota.note}</pre>
			</td>
		</tr>
<c:if test="${not empty entity.id }">	
<c:if test="${entity.id != '0'}">

					
<tr>
				<td class="label">
				关联实现类：
			</td>
					<td class="content">

					<c:forEach items='${quotaToImplList}' var="entity">
					 <c:if test="${entity.id eq quota.quotatoimplid}">
						${entity.name}
					</c:if>
				</c:forEach>
			</td>
		</tr>
</c:if>
</c:if>






	</table>

</form>

<%@ include file="/common/footer_eoms.jsp"%>