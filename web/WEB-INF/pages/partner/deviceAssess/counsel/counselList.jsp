<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/counsels.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="counsel.list.heading" />
</content>

	<display:table name="counselList" cellspacing="0" cellpadding="0"
		id="counselList" pagesize="${pageSize}" class="table counselList"
		export="false"
		requestURI="${app}/partner/deviceAssess/counsels.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="sheetNum" sortable="true"
			headerClass="sortable" titleKey="counsel.sheetNum" href="${app}/partner/deviceAssess/counsels.do?method=goToDetail" paramId="id" paramProperty="id"/>

	<display:column property="createTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="counsel.createTime" />

	<display:column property="changeTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="counsel.changeTime" />

	<display:column property="pigeonholeTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="counsel.pigeonholeTime" />

	<display:column property="affairName" sortable="true"
			headerClass="sortable" titleKey="counsel.affairName" />

	<display:column sortable="true"
			headerClass="sortable" titleKey="counsel.affairLevel" >
			<eoms:id2nameDB id="${counselList.affairLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="province" sortable="true"
			headerClass="sortable" titleKey="counsel.province" />

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="counsel.city" />

	<display:column sortable="true"
			headerClass="sortable" titleKey="counsel.factory" >
			<eoms:id2nameDB id="${counselList.factory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="counsel.speciality" >
			<eoms:id2nameDB id="${counselList.speciality}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="counsel.equipmentType" >
			<eoms:id2nameDB id="${counselList.equipmentType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="equipmentName" sortable="true"
			headerClass="sortable" titleKey="counsel.equipmentName" />

	<display:column property="equipmentVersion" sortable="true"
			headerClass="sortable" titleKey="counsel.equipmentVersion" />

	<display:column property="finallyTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="counsel.finallyTime" />
	
	<display:column property="referTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="咨询提出时间" />

	<display:column property="finallyLong" sortable="true"
			headerClass="sortable" titleKey="counsel.finallyLong" />

	<display:column property="satisfaction" sortable="true"
			headerClass="sortable" titleKey="counsel.satisfaction" />
	
	<display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${counselList.approveUser}"/>
    </display:column>
	<display:column property="amount" sortable="true"
			headerClass="sortable" titleKey="counsel.amount" />
			
	<display:column sortable="false" media="html" title="状态">
		<c:if test="${counselList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
		<c:if test="${counselList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${counselList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${counselList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/counsels.do?method=edit&id=${counselList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${counselList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/counsels.do?method=goToDetail&id=${counselList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${counselList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/counsels.do?method=goToDetail&id=${counselList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>

		<display:setProperty name="paging.banner.item_name" value="counsel" />
		<display:setProperty name="paging.banner.items_name" value="counsels" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>