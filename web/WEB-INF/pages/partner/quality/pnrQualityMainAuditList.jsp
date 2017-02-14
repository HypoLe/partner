<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">



</script>


<table class="formTable">
	<caption>
		<div class="header center">待审批质量报告列表</div>
	</caption>
</table>	
	<display:table name="pnrQualityMainList" cellspacing="0" cellpadding="0"
		id="pnrQualityMainList" pagesize="${pageSize}" class="table pnrQualityMainList"
		export="false"
		requestURI="../quality/qualityAction.do?method=search"
		sort="list" partialList="true" size="resultSize">
		
	<display:column property="title" sortable="true"
			headerClass="sortable" title="标题"  paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" title="发布人" >
		<eoms:id2nameDB id="${pnrQualityMainList.publishUser}" beanId="tawSystemUserDao" />
	</display:column>	

	<display:column sortable="true" headerClass="sortable" title="发布部门" >
		<eoms:id2nameDB id="${pnrQualityMainList.publishDept}" beanId="tawSystemDeptDao" />
	</display:column>	
		
    <display:column title="审批" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='../quality/qualityAction.do?method=detail&id=${pnrQualityMainList.id}&isAudit=1' target='_blank'>
					<img src="${app}/images/icons/edit.gif" /> </a>
			</display:column>
	</display:table>	
	
<%@ include file="/common/footer_eoms.jsp"%>