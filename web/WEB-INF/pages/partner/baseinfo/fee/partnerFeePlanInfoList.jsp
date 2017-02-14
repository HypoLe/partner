<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
					<!-- 有付款计划的付费信息 -->
<c:set var="buttons">
   	  <br/>
	 <a href='partnerFeeInfos.do?method=addPlanFee'><fmt:message key="button.add"/></a>	
</c:set>

<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">

<content tag="heading">
	<b>有付款计划的付费信息</b>
</content>

	<display:table name="partnerFeeInfoList" cellspacing="0" cellpadding="0"
		id="partnerFeeInfoList" pagesize="${pageSize}" class="table partnerFeeInfoList"
		export="false"
		requestURI="${app}/fee/partnerFeeInfos.do?method=searchPlanFee"
		sort="list" partialList="true" size="resultSize">
	
	<display:column property="name" sortable="true"
			headerClass="sortable" titleKey="partnerFeeInfo.name"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="partnerFeeInfo.payUnit"  >
		<eoms:id2nameDB id="${partnerFeeInfoList.payUnit}"  beanId="partnerDeptDao" />
	</display:column>

	<display:column property="feeType" sortable="true"
			headerClass="sortable" titleKey="partnerFeeInfo.feeType"  paramId="id" paramProperty="id"/>

	<display:column property="payDate" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="partnerFeeInfo.payDate"  paramId="id" paramProperty="id"/>

	<display:column property="payWay" sortable="true"
			headerClass="sortable" titleKey="partnerFeeInfo.payWay"  paramId="id" paramProperty="id"/>

	<display:column property="payFee" sortable="true"
			headerClass="sortable" title="费用金额(元)"/>

	<display:column property="payNo" sortable="true"
			headerClass="sortable" titleKey="partnerFeeInfo.payNo"  paramId="id" paramProperty="id"/>

	<display:column property="payUser" sortable="true"
			headerClass="sortable" titleKey="partnerFeeInfo.payUser"  paramId="id" paramProperty="id"/>

	<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${partnerFeeInfoList.id }';
		                        var url=eoms.appPath+'/fee/partnerFeeInfos.do?method=editPlanFee&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>

		<display:column title="付款计划" headerClass="imageColumn">
			<a href="javascript:var planId = '${partnerFeeInfoList.planId}';
		                        var url=eoms.appPath+'/fee/partnerFeePlans.do?method=searchDetail&id='+planId;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>


	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
