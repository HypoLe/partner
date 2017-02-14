<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<table id="sheet" class="formTable">
<c:if test="${not empty evaluationEntityList}">
<td colspan="4">
				<div class="ui-widget-header">
					等待确认列表
				</div>
			</td></c:if>
<display:table name="evaluationEntityList" cellspacing="0" cellpadding="0"
		id="evaluationEntityList" pagesize="${pagesize}"
		requestURI=""
		class="table appraisalList" export="false"
		size="${size}">
		<display:column property="year"   
			headerClass="sortable" title="考核年份" />
		<display:column property="month"   
			headerClass="sortable" title="考核月份" />
		<display:column  
			headerClass="sortable" title="考核模板">
			<a href="${app}/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId=${evaluationEntityList.usedTemplateId}" target="_blank">
				${evaluationEntityList.usedTemplateName}
			</a>
		</display:column>
				
		<display:column   headerClass="sortable" title="被考核单位">
			<eoms:id2nameDB id="${evaluationEntityList.evaluationTarget}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column   headerClass="sortable" title="考核发起人">
		<eoms:id2nameDB id="${evaluationEntityList.initate}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column  headerClass="sortable" title="考核执行人">
		<eoms:id2nameDB id="${evaluationEntityList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column property="score"  headerClass="sortable" title="得分" />
		<display:column   headerClass="sortable" title="意见" >
			<pre>${evaluationEntityList.opinion}</pre>
		</display:column>
		<display:column headerClass="sortable" title="考核确认">
			<a href="${app}/partner/evaluation/evaluationEntity.do?method=evaluationConfirmForm&id=${evaluationEntityList.id}&confirmPersonnel=${confirmPersonnel}&urlType=0">代维确认</a>
		</display:column>
</display:table>
</table>

<%@ include file="/common/footer_eoms.jsp"%>