<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<table class="formTable">
  	<caption><div class="header center">人员流失率统计详细列表</div></caption>
</table>
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table" requestURI="${app}/personnel/statistics.do?method=partnerUserLostRateStatisticsSearchInto"
		export="false" sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${list['area_id']}"  beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		${list['dept_name']}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="维护专业">
		<eoms:id2nameDB id="${list['professional']}" beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="人员姓名">
		<%--<a   href="${app}/partner/baseinfo/resumes.do?method=newExpert&nodeId=${list['useruuid']}">
			${list['name']}
		</a>
	--%>${list['name']}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="ID">${list['user_id']}
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="在职状态">
			<eoms:id2nameDB id="${list['post_state']}" beanId="ItawSystemDictTypeDao" />
	</display:column>
	<c:choose>
		<c:when test="${postState!='1240903'}">
			<display:column  sortable="true" headerClass="sortable" title="入职时间">${list['savetime']}
			</display:column>
		</c:when>
		<c:otherwise>
			<display:column  sortable="true" headerClass="sortable" title="离职时间">${list['leavatime']}
			</display:column>
			<display:column  sortable="true" headerClass="sortable" title="离职原因" maxLength="30">${list['leavareason']}
			</display:column>	
			<display:column  sortable="true" headerClass="sortable" title="是否黑名单">
				<c:choose>
					<c:when test="${list['blacklist']==0}">
						否
					</c:when>
					<c:otherwise>
						是
					</c:otherwise>
				</c:choose>
			</display:column>			
		</c:otherwise>
	</c:choose>
	
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>