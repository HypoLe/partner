<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ftraininfos.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="ftraininfo.list.heading" />
</content>

	<display:table name="ftraininfoList" cellspacing="0" cellpadding="0"
		id="ftraininfoList" pagesize="${pageSize}" class="table ftraininfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/ftraininfos.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<%--
	<display:column property="trainId" sortable="true"    
			headerClass="sortable" titleKey="ftraininfo.trainId" href="${app}/partner/deviceAssess/ftraininfos.do?method=edit" paramId="id" paramProperty="id"/>
    --%>
	<display:column property="eventName" sortable="true"
			headerClass="sortable" titleKey="ftraininfo.eventName" />

    <display:column property="province" sortable="true"
			headerClass="sortable" title="省份" />

	<display:column  sortable="true"
			headerClass="sortable" titleKey="ftraininfo.trainLevel" >
			<eoms:id2nameDB id="${ftraininfoList.trainLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="ftraininfo.factory" >
			<eoms:id2nameDB id="${ftraininfoList.factory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="ftraininfo.speciality" >
			<eoms:id2nameDB id="${ftraininfoList.speciality}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="beginTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="ftraininfo.beginTime" />

	<display:column property="endTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="ftraininfo.endTime" />

	<display:column property="trainPopulace" sortable="true"
			headerClass="sortable" titleKey="ftraininfo.trainPopulace" />

	<display:column property="eligibPopulace" sortable="true"
			headerClass="sortable" titleKey="ftraininfo.eligibPopulace" />

	<display:column property="trainEligibRate" sortable="true"
			headerClass="sortable" titleKey="ftraininfo.trainEligibRate" />

	<display:column property="satisfaction" sortable="true"
			headerClass="sortable" titleKey="ftraininfo.satisfaction" />

	<display:column property="takeCountOf" sortable="true"
			headerClass="sortable" titleKey="ftraininfo.takeCountOf" />
			
			<display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${ftraininfoList.deviceAssessApprove.approveUser}"/>		
		</display:column>	
			
		<display:column sortable="false" media="html" title="状态">
		<c:if test="${ftraininfoList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
		<c:if test="${ftraininfoList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${ftraininfoList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${ftraininfoList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/ftraininfos.do?method=edit&id=${ftraininfoList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${ftraininfoList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/ftraininfos.do?method=goToDetail&id=${ftraininfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${ftraininfoList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/ftraininfos.do?method=goToDetail&id=${ftraininfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>			

		<display:setProperty name="paging.banner.item_name" value="ftraininfo" />
		<display:setProperty name="paging.banner.items_name" value="ftraininfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>