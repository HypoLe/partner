<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

	<display:table name="unAuditList" cellspacing="0" cellpadding="0"
		id="unAuditList" pagesize="${pageSize}" class="table unAuditList"
		export="false"
		requestURI="${app}/fee/partnerFeePlans.do?method=getUnAuditPlanList"
		sort="list" partialList="true" size="resultSize">
		
	
	<display:column sortable="true"	headerClass="sortable" title="计划名称" >
			<eoms:id2nameDB id="${unAuditList.feeId}" beanId="partnerFeePlanDao" />
	</display:column>
	<display:column property="createTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="提交时间" />

	<display:column sortable="true"	headerClass="sortable" title="提交人" >
			<eoms:id2nameDB id="${unAuditList.operateId}" beanId="tawSystemUserDao" />
	</display:column>

		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${unAuditList.feeId }';
		                        var url=eoms.appPath+'/fee/partnerFeePlans.do?method=searchDetail&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:column title="审核" headerClass="imageColumn">
			<a href="javascript:var feeId = '${unAuditList.feeId }';
								var id = '${unAuditList.id }';
		                        var url=eoms.appPath+'/fee/partnerFeePlans.do?method=planAudit&feeId='+feeId+'&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/edit.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>

<%@ include file="/common/footer_eoms.jsp"%>