<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

	<table id="sheet" class="formTable">
		
			<td colspan="4">
				<div class="ui-widget-header">
					计次费用模板
				</div>
			</td>
			</table>
<display:table name="feeCountPrcTmplList" cellspacing="0" cellpadding="0"
		id="feeCountPrcTmplList" pagesize="${pagesize}"
		class="table appraisalList" export="false"
	   requestURI=""
		size="${size}">
		<display:column property="prcTmplNm"   
			headerClass="sortable" title="模板名称" />
		<display:column property="majorName"   
			headerClass="sortable" title="专业名称" />
		<display:column   headerClass="sortable" title="创建人">
		<eoms:id2nameDB id="${feeCountPrcTmplList.creatorId}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column property="createdtTm"   
			headerClass="sortable" title="创建时间" format="{0,date,yyyy-MM-dd}" />

      <display:column  headerClass="sortable" title="编辑">
			<a href="${app}/partner/feeManage/feeCountManage.do?method=goToEditeFeeCountDetailTmpl&id=${feeCountPrcTmplList.id}">编辑</a>
		</display:column>
</display:table>


<%@ include file="/common/footer_eoms.jsp"%>