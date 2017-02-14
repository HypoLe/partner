<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<display:table name="appraisalCountList" cellspacing="0" cellpadding="0"
		id="appraisalCountList" pagesize="${pagesize}"
		class="table appraisalList" export="false"
		requestURI="index.do?method=evaUndoList" sort="list" partialList="true"
		size="${size}">
		<display:column property="yearFlag"  sortable="true"
			headerClass="sortable" title="年" />
		<display:column property="monthFlag"  sortable="true"
			headerClass="sortable" title="月" />
		<display:column sortable="true"
			headerClass="sortable" title="考核模板">
			<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${appraisalCountList.apprailTemplateId }" target="_blank">
				${appraisalCountList.appraisalTemplateName}
			</a>
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="代维规模类型">
			<c:if test="${appraisalCountList.proxyScaleType eq 'baseStation'}">
				基站类
			</c:if>
			<c:if test="${appraisalCountList.proxyScaleType eq 'circuit'}">
				线路类
			</c:if>
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="代维规模数据详情">
			<c:if test="${appraisalCountList.proxyScaleType=='baseStation'}">
				<a href="${app}/partner2/baseStation/baseStationMain.do?method=showDetail&id=${appraisalCountList.proxyScaleId}"
				target="_blank">详情</a>
			</c:if>
			<c:if test="${appraisalCountList.proxyScaleType=='circuit'}">
				<a href="${app}/partner2/circuit/circuitLength.do?method=goToSingleDetail&id=${appraisalCountList.proxyScaleId}"
				target="blank">详情</a>
			</c:if>
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="操作">
			<a href="${app}/partner2/appraisal.do?method=showAppraisalDetail&proxyScaleId=${appraisalCountList.proxyScaleId}">审核</a>
		</display:column>
</display:table>


<%@ include file="/common/footer_eoms.jsp"%>