<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}"
		class="table ctContentsList" export="false"
		requestURI="${app}/fee/partnerFeePlans.do?method=queryContractDo" sort="list"
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">


		<display:column title="选择 " href="" paramId="id" paramProperty="id"> 
			<input type="radio" name="contentId" id="contentId" value="${ctContentsList.contentId}" onclick="getContract(this.value);" /> 
			<input type="hidden" name="${ctContentsList.contentId}_no" id="${ctContentsList.contentId}_no" value="${ctContentsList.contractNO}"/> 
			<input type="hidden" name="${ctContentsList.contentId}_amount" id="${ctContentsList.contentId}_amount" value="${ctContentsList.amount}"/> 
		
		</display:column> 
	
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>

		<display:column sortable="true" property="contractTitle" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />
			
		<display:column sortable="true" headerClass="sortable" title="合同总额(元)">
			${ctContentsList.amount}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.contentStatus">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${ctContentsList.contractStatus}" beanId="id2nameXML" />
		</display:column>
		<display:column title="查看" headerClass="imageColumn">
			<a href="${app}/partner/contract/ctContentss.do?method=detail&ID=${ctContentsList.contentId }&TABLE_ID=${ctContentsList.tableId}&THEME_ID=${ctContentsList.themeId}" target="_blank">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
</fmt:bundle>
<script>
    function getContract(contentId){
    	 var amount = document.getElementById(contentId+"_amount").value;
    	 var contractNO = document.getElementById(contentId+"_no").value;
         window.opener.document.all.planPayFee.value=amount;
         window.opener.document.all.compactNo.value=contractNO;
         window.opener.document.all.contentId.value=contentId;
         self.close(); 
		
    }
</script>

<%@ include file="/common/footer_eoms.jsp"%>