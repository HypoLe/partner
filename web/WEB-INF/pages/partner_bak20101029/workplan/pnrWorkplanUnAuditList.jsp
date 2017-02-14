<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<c:if test="${resultSize==0}">
<table class="formTable">
	<caption>
		<div class="header center">待审核列表</div>
	</caption>
		<tr>
			<td  style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
		</tr>
</table>		
</c:if>
<c:if test="${resultSize!=0}">
	<display:table name="unAuditList" cellspacing="0" cellpadding="0"
		id="unAuditList" pagesize="${pageSize}" class="table unAuditList"
		export="false"
		requestURI="${app}/partner/workplan/pnrWorkplanMains.do?method=getUnAuditList"
		sort="list" partialList="true" size="resultSize">
		
	
	<display:column sortable="true"	headerClass="sortable" title="工作计划名称" >
			<eoms:id2nameDB id="${unAuditList.workplanId}" beanId="pnrWorkplanMainDao" />
	</display:column>
	<display:column property="createTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="提交时间" />

	<display:column sortable="true"	headerClass="sortable" title="提交人" >
			<eoms:id2nameDB id="${unAuditList.operateId}" beanId="tawSystemUserDao" />
	</display:column>

		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var workplanId = '${unAuditList.workplanId }';
		                        var url=eoms.appPath+'/partner/workplan/pnrWorkplanMains.do?method=detail&id='+workplanId;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:column title="审核" headerClass="imageColumn">
			<a href="javascript:var workplanId = '${unAuditList.workplanId }';
								var id = '${unAuditList.id }';
		                        var url=eoms.appPath+'/partner/workplan/pnrWorkplanMains.do?method=audit&workplanId='+workplanId+'&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/edit.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>