<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/repairinfos.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="repairinfo.list.heading" />
</content>

	<display:table name="repairinfoList" cellspacing="0" cellpadding="0"
		id="repairinfoList" pagesize="${pageSize}" class="table repairinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/repairinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">
    <%-- 
	<display:column property="sheetId" sortable="true"
			headerClass="sortable" titleKey="repairinfo.sheetId" href="${app}/repairinfo/repairinfos.do?method=edit" paramId="id" paramProperty="id"/>
	--%>
	<display:column property="sheetNum" sortable="true"
			headerClass="sortable" titleKey="repairinfo.sheetNum" />

	<display:column property="createTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="repairinfo.createTime" />

	<display:column property="pigeonholeTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="repairinfo.pigeonholeTime" />

	<display:column property="affairName" sortable="true"
			headerClass="sortable" titleKey="repairinfo.affairName" />

	<display:column sortable="true"
			headerClass="sortable" titleKey="repairinfo.affairLevel" >
			<eoms:id2nameDB id="${repairinfoList.affairLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="province" sortable="true"
			headerClass="sortable" titleKey="repairinfo.province" />

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="repairinfo.city" />

	<display:column property="factory" sortable="true"
			headerClass="sortable" titleKey="repairinfo.factory" >
			<eoms:id2nameDB id="${repairinfoList.factory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="repairinfo.speciality" >
			<eoms:id2nameDB id="${repairinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="repairinfo.equipmentType" >
			<eoms:id2nameDB id="${repairinfoList.equipmentType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true" property="equipmentName"
			headerClass="sortable" titleKey="repairinfo.equipmentName" />

	<display:column property="equipmentVersion" sortable="true"
			headerClass="sortable" titleKey="repairinfo.equipmentVersion" />

	<display:column property="blockNum" sortable="true"
			headerClass="sortable" titleKey="repairinfo.blockNum" />

	<display:column property="repairTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="repairinfo.repairTime" />

	<display:column property="returnTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="repairinfo.returnTime" />

	<display:column property="repairLong" sortable="true"
			headerClass="sortable" titleKey="repairinfo.repairLong" />

	<display:column property="takeCountOf" sortable="true"
			headerClass="sortable" titleKey="repairinfo.takeCountOf" />
			
	<display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${repairinfoList.deviceAssessApprove.approveUser}"/>		
		</display:column>	
			
		<display:column sortable="false" media="html" title="状态">
		<c:if test="${repairinfoList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
		<c:if test="${repairinfoList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${repairinfoList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${repairinfoList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/repairinfos.do?method=edit&id=${repairinfoList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${repairinfoList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/repairinfos.do?method=goToDetail&id=${repairinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${repairinfoList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/repairinfos.do?method=goToDetail&id=${repairinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>	

		<display:setProperty name="paging.banner.item_name" value="repairinfo" />
		<display:setProperty name="paging.banner.items_name" value="repairinfos" />
	</display:table>
	
	

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>