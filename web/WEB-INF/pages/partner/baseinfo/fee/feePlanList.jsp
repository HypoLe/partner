<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle	basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">

	<display:table name="partnerFeePlanList" cellspacing="0" cellpadding="0"
		id="partnerFeePlanList" pagesize="${pageSize}" class="table partnerFeePlanList"
		export="false"
		requestURI="${app}/fee/partnerFeePlans.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column title="选择 " href="" paramId="id" paramProperty="id"> 
			<input type="radio" name="id" id="id" value="${partnerFeePlanList.id}" onclick="getFeePlan(this.value);" /> 
			<input type="hidden" name="${partnerFeePlanList.id}_name" id="${partnerFeePlanList.id}_name" value="${partnerFeePlanList.name}"/> 
			<input type="hidden" name="${partnerFeePlanList.id}_payUnit" id="${partnerFeePlanList.id}_payUnit" value="${partnerFeePlanList.payUnit}"/> 
			<input type="hidden" name="${partnerFeePlanList.id}_payUnitName" id="${partnerFeePlanList.id}_payUnitName" value="${partnerFeePlanList.payUnitName}"/>
			<input type="hidden" name="${partnerFeePlanList.id}_collectUnit" id="${partnerFeePlanList.id}_collectUnit" value="${partnerFeePlanList.collectUnit}"/> 
			<input type="hidden" name="${partnerFeePlanList.id}_collectUnitName" id="${partnerFeePlanList.id}_collectUnitName" value="${partnerFeePlanList.collectUnitName}"/>
		
		</display:column> 
		
	<display:column property="name" sortable="true" 
			headerClass="sortable" titleKey="partnerFeePlan.name"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="partnerFeePlan.payUnit" >
		${partnerFeePlanList.payUnitName}
	</display:column>

	<display:column property="planPayDate" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="partnerFeePlan.planPayDate"  paramId="id" paramProperty="id"/>

	<display:column property="planPayFee" sortable="true"
			headerClass="sortable" title="计划付款金额(元)"/>

	<display:column sortable="true"	headerClass="sortable" title="是否已付款" >
			<eoms:dict key="dict-kmmanager" dictId="isOrNot"
				itemId="${partnerFeePlanList.payStatus}" beanId="id2nameXML" />
	</display:column>

	<display:column property="payStage" sortable="true"
			headerClass="sortable" titleKey="partnerFeePlan.payStage"  paramId="id" paramProperty="id"/>
	
		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${partnerFeePlanList.id }';
		                        var url=eoms.appPath+'/fee/partnerFeePlans.do?method=searchDetail&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
	
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>
</fmt:bundle>
<script>
    function getFeePlan(id){
    	 var name = document.getElementById(id+"_name").value;
    	 var payUnit = document.getElementById(id+"_payUnit").value;
    	 var payUnitName = document.getElementById(id+"_payUnitName").value;
    	 var collectUnit = document.getElementById(id+"_collectUnit").value;
    	 var collectUnitName = document.getElementById(id+"_collectUnitName").value;
         window.opener.document.all.planId.value=id;
         window.opener.document.all.name.value=name;
         window.opener.document.all.payUnit.value=payUnit;
         window.opener.document.all.payUnitName.value=payUnitName;
         window.opener.document.all.collectUnit.value=collectUnit;
         window.opener.document.all.collectUnitName.value=collectUnitName;
         self.close(); 
		
    }
</script>

<%@ include file="/common/footer_eoms.jsp"%>