<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>


<table class="formTable">
	<caption>
		<div class="header center">待确认列表</div>
	</caption>
	<c:if test="${resultSize==0}">
		<tr>
			<td  style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
		</tr>
	</c:if>
</table>		

<c:if test="${resultSize!=0}">
	<display:table name="unAuditList" cellspacing="0" cellpadding="0"
		id="unAuditList" pagesize="${pageSize}" class="table unAuditList"
		export="false"
		requestURI="${app}/partner/agreement/pnrAgreementMains.do?method=getUnAuditList"
		sort="list" partialList="true" size="resultSize">
		
	
	<display:column sortable="true"	headerClass="sortable" title="协议名称" >
			<eoms:id2nameDB id="${unAuditList.agreementId}" beanId="pnrAgreementMainDao" />
	</display:column>
	<display:column property="createTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="提交时间" />

	<display:column sortable="true"	headerClass="sortable" title="提交人" >
			<eoms:id2nameDB id="${unAuditList.operateId}" beanId="tawSystemUserDao" />
	</display:column>
	
	<display:column sortable="true"	headerClass="sortable" title="类型" >
		<eoms:dict key="dict-partner-agreement" dictId="auditType" itemId="${unAuditList.auditType}" beanId="id2nameXML" />
	</display:column>
	
		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var agreementId = '${unAuditList.agreementId }';
		                        var url=eoms.appPath+'/partner/agreement/pnrAgreementMains.do?method=detail&id='+agreementId;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:column title="确认" headerClass="imageColumn">
			<a href="javascript:var agreementId = '${unAuditList.agreementId }';
								var auditId = '${unAuditList.id }';
								var type = '${unAuditList.auditType }';
		                        var url=eoms.appPath+'/partner/agreement/pnrAgreementAudits.do?method=audit&agreementId='+agreementId+'&auditId='+auditId+'&type='+type;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/edit.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>