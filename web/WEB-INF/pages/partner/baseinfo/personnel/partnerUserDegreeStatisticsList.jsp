<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<table class="formTable">
  	<caption><div class="header center">人员学历情况统计详细列表</div></caption>
</table>
	<br>	<div>操作提示：学历可查看详细信息</div><br>
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table" requestURI="${app}/personnel/statistics.do?method=partnerUserDegreeStatisticsSearchInto"
		export="false" sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${list['area_id']}"  beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${list['dept_id']}"  beanId="tawSystemDeptDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="姓名">
		${list['name']}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="ID">${list['user_id']}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="巡检专业">
		<eoms:id2nameDB id="${list['professional']}" beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="学历">
		<a   href="${app}/personnel/studyExperience.do?method=getDetail&id=${list['cuuid']}&personCardNo=${list['person_card_no']}&hasRightGoBack=Y">
			<eoms:id2nameDB id="${list['degree']}" beanId="ItawSystemDictTypeDao" />
		</a>	
	</display:column>			
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>