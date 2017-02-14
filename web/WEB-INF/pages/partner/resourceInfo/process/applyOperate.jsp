<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<div class="header center">
	<b>变更申请处理
</div>
<br>
<div >
	<logic:notEmpty name="resultList" scope="request">
	<display:table name="resultList" cellspacing="0" cellpadding="0" 		id="resultList" pagesize="${pageSize}"
			class="table resultList" export="false" 
			requestURI="${app}/partner/process/process.do?method=goToApplyOperatePage" sort="list"
			partialList="true" size="${resultSize}">
		<%--<display:column  sortable="true" headerClass="sortable" title="序号">${resultList.id2}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="附件名称">${resultList[0].changeAttachment}
		</display:column>
		--%><display:column sortable="true" headerClass="sortable" title="申请时间">${resultList.startTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请人员">${resultList.createUser}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请变更类型">
			<eoms:id2nameDB id="${resultList.changeType}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请模块">
			<eoms:id2nameDB id="${resultList.referenceModel}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="查看申请附件">
			<a  href="${app}/partner/process/process.do?method=applyFileDownload&idMain=${resultList.id}">下载</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请处理">
			<a href="${app}/partner/process/process.do?method=applyDetail&idMain=${resultList.id}">申请处理</a>
		</display:column>
	</display:table>
	</logic:notEmpty> 
	<logic:empty name="resultList" scope="request">
	当前无变更申请！
	</logic:empty> 
</div>
<%@ include file="/common/footer_eoms.jsp"%>