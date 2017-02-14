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
	<display:table name="unConfirmList" cellspacing="0" cellpadding="0"
		id="unConfirmList" pagesize="${pageSize}" class="table unConfirmList"
		export="false"
		requestURI="${app}/evaTemplateTemps.do?method=unConfirmList"
		sort="list" partialList="true" size="resultSize">
		
	<display:column sortable="true"	headerClass="sortable" title="模板名称" >
			<eoms:id2nameDB id="${unConfirmList.evaTemplateId}" beanId="evaTemplateTempDao" />
	</display:column>
	<display:column property="createTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="提交时间" />

	<display:column sortable="true"	headerClass="sortable" title="提交人" >
			<eoms:id2nameDB id="${unConfirmList.operateId}" beanId="tawSystemUserDao" />
	</display:column>
	
		<display:column title="确认" headerClass="imageColumn">
			<a href="javascript:var evaTemplateId = '${unConfirmList.evaTemplateId }';
								var id = '${unConfirmList.id }';
		                        var url=eoms.appPath+'/eva/evaTemplateTemps.do?method=confirm&evaTemplateId='+evaTemplateId+'&confirmId='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/edit.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>