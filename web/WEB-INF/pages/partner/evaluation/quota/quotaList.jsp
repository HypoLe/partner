<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>







	<display:table name="quotaList" cellspacing="0" cellpadding="0"
		id="quotaList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		requestURI="quota.do?method=list" sort="list" partialList="true"
		>
		<display:column property="idctNm"  title="考核指标名" />
		<display:column property="proportion"  title="指标权重" />
		<display:column property="scoreTyp" title="评分类型" />
		<display:column title="评分说明">
		  <pre>${quotaList.scoreExpl}</pre>
		</display:column>
		<display:column  title="指标实际定义" >
		  <pre>${quotaList.idCtdFnt}</pre>
		</display:column>
		<display:column title="备注">
		   <pre>${quotaList.note}</pre>
		</display:column>
		<display:column property="creator" title="考核指标创建人" >
			
			</display:column>
		<display:column property="crtdtTm"  title="考核指标创建时间" />
			
		<display:column title="查看规则" 
		  url="/partner/evaluation/checkRule.do?method=list"  paramProperty="id"
				paramId="ownIndcId" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column title="编辑规则" 
		  url="/partner/evaluation/checkRule.do?method=edit"  paramProperty="id"
				paramId="ownIndcId" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column  title="编辑指标"
			paramProperty="id" url="/partner/evaluation/quota.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/nop3/images/edit.gif">
		</display:column>
		<display:column  title="删除指标"
				url="/partner/evaluation/quota.do?method=delete" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>


<br />






<%@ include file="/common/footer_eoms.jsp"%>