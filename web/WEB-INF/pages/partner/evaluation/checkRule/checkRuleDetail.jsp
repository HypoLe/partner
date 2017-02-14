<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<div>
	<table id="sheet" class="formTable">
	    <caption><center>考核规则</center></caption> 
		<tr>
			<td class="label">
				规则名称
			</td>
			<td>
				${checkRule.ruleNm }
			</td>
			<c:if test="${not empty formulaList}"> 
				<td class="label">
				指标下限(B)
			</td>
			<td>
				${checkRule.btomRate}
			</td>
			
		</tr>
		<tr>

		
			<td class="label">
				指标上限(C)
			</td>
			<td>
				${checkRule.topRate}
			</td>
				<td class="label">
				基础分值(D)
			</td>
			<td>
				${checkRule.btomScore}
			</td>
		</tr>
		<tr>

		
			<td class="label">
				挑战分值(E)
			</td>
			<td>
				${checkRule.topScore}
			</td>
</c:if>
			<td class="label">
				备注
			</td>

			<td class="content">
			 <pre>${checkRule.note}</pre>
			</td>

		</tr>

</table>
</div>
<br/>

<c:if test="${not empty formulaList}"> 

<fieldSet title="计算公式">
<legend>计算公式</legend>
<div>
	<br/>
	<font color='red'>计算公式打分用A表示;指标下限用B表示;指标上限用C标示；基础分值用D标示；挑战分值用E标示</font>
	<br/>
	&nbsp;&nbsp; 
</div>
<display:table name="formulaList" cellspacing="0" cellpadding="0"
		id="formulaList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		requestURI="formula.do?method=list" sort="list" partialList="true"
		>
		<display:column property="minScore"  title="左边限" />
		<display:column property="maxScore"  title="右边限" />
		<display:column property="formulaExpression"  title="适用公式" />

</display:table>
</fieldSet>
</c:if>


<%@ include file="/common/footer_eoms.jsp"%>