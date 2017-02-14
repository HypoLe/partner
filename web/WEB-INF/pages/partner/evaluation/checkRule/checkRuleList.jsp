<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>







	<display:table name="checkRuleList" cellspacing="0" cellpadding="0"
		id="checkRuleList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		requestURI="checkRule.do?method=list" sort="list" partialList="true"
		>
		<display:column property="ruleNm"  title="规则名称" />
		<display:column property="btomRate"  title="指标下限" />
		<display:column property="topRate"  title="指标上限" />
		<display:column property="btomScore"  title="基础分值" />
		<display:column property="topScore"  title="挑战分值" />
		<display:column  title="备注">
		   <pre>${checkRuleList.note}</pre>
		</display:column>

		<display:column property="creator" title="考核指标创建人" >
			
			</display:column>
		<display:column property="crdtTm"  title="考核指标创建时间" />
			
		<display:column  title="编辑公式"
			paramProperty="id" url="/partner/evaluation/formula.do?method=edit"
			paramId="ownRuleId" media="html">
			<img src="${app}/nop3/images/edit.gif">
		</display:column>
		<display:column  title="查看规则"
			paramProperty="id" url="/partner/evaluation/checkRule.do?method=showDetail"
			paramId="id" media="html">
			<img src="${app}/nop3/images/edit.gif">
		</display:column>
		<display:column  title="编辑规则"
			paramProperty="id" url="/partner/evaluation/checkRule.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/nop3/images/edit.gif">
		</display:column>
		<display:column  title="删除规则"
				url="/partner/evaluation/checkRule.do?method=delete" paramProperty="id"
				paramId="id" media="html">
				<img src="${app }/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>


<br />






<%@ include file="/common/footer_eoms.jsp"%>