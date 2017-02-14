<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>

	<table id="sheet" class="formTable">
			<c:if test="${not empty evaluationEntityList}">
			<td colspan="4">
				<div class="ui-widget-header">
					计次费用列表
				</div>
			</td></c:if>
<display:table name="feeCountEntityList" cellspacing="0" cellpadding="0"
		id="feeCountEntityList" pagesize="${pagesize}"
		class="table " export="false"
	   requestURI=""
		size="${size}">
		<display:column property="feeCountEntityNm"   
			headerClass="sortable" title="名称" />
		<display:column property="year"   
			headerClass="sortable" title="年份" />
		<display:column property="month"   
			headerClass="sortable" title="月份" />
		<display:column  
			headerClass="sortable" title="使用的模板">
			<a href="${app}/partner/feeManage/feeCountManage.do?method=templateOnlyView&id=${feeCountEntityList.ownFeePrcTmplId}" target="_blank">
				${feeCountEntityList.ownFeePrcTmplNm}
			</a>
		</display:column>
	 <display:column   headerClass="sortable" title="被考核单位">
			<eoms:id2nameDB id="${feeCountEntityList.compId}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column   headerClass="sortable" title="考核发起人">
		<eoms:id2nameDB id="${feeCountEntityList.creatorId}" beanId="tawSystemUserDao"/>
		</display:column>

		<display:column  
			headerClass="sortable" title="编辑">
			<a href="${app}/partner/feeManage/feeCountManage.do?method=goToEditFeeCountEntity&id=${feeCountEntityList.id}"><img src="${app}/nop3/images/edit.gif"></a>
		</display:column>
</display:table>

</table>





	








<%@ include file="/common/footer_eoms.jsp"%>