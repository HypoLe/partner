<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<style type="text/css">
.small {
	width: 10px;
}

</style>

<script type="text/javascript">
var myjs=jQuery.noConflict();
Ext.onReady(function(){
  Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
		
})
</script>


</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>




<!-- Information hints area end-->


<logic:present name="advertisementApprovalList" scope="request">
	<display:table name="advertisementApprovalList" cellspacing="0"
		cellpadding="0" id="advertisementApprovalList" pagesize="${pagesize}"
		class="table advertisementApprovalList" export="true"
		requestURI="advertisement.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${advertisementApprovalList.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="墙体广告地点">

			<eoms:id2nameDB beanId="tawSystemAreaDao"
				id="${advertisementApprovalList.advertisementArea}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="墙体广告内容">
			${advertisementApprovalList.advertisementContent}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="墙体广告数量（份）">
			${advertisementApprovalList.advertisementAmount}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="完成时间">
			${advertisementApprovalList.finishTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="未完成原因">
			${advertisementApprovalList.incompleteCause}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">

			<eoms:id2nameDB beanId="tawSystemUserDao"
				id="${advertisementApprovalList.approvalUser}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="备注">
			${advertisementApprovalList.remark}
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="状态">
			${advertisementType[advertisementApprovalList.approvalType]}
		</display:column>



		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${advertisementApprovalList.id }"
				href="${app }/deviceManagement/advertisement/advertisement.do?method=goToDetail&id=${advertisementApprovalList.id}"
				target="blank" shape="rect"> <img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>


		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
