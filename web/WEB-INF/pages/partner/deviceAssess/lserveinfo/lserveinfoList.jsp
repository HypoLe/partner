<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/lserveinfos.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="lserveinfo.list.heading" />
</content>

	<display:table name="lserveinfoList" cellspacing="0" cellpadding="0"
		id="lserveinfoList" pagesize="${pageSize}" class="table lserveinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/lserveinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">
<%--
	<display:column property="sheetId" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.sheetId" href="${app}/partner/deviceAssess/lserveinfos.do?method=edit" paramId="id" paramProperty="id"/>
--%>
	<display:column property="sheetNum" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.sheetNum" href="${app}/partner/deviceAssess/lserveinfos.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="lserveinfo.createTime" />

	<display:column property="turnFactoryTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="lserveinfo.turnFactoryTime" />

	<display:column property="pigeonholeTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="lserveinfo.pigeonholeTime" />

	<display:column property="affairName" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.affairName" />

	<display:column sortable="true"
			headerClass="sortable" titleKey="lserveinfo.affairLevel" >
			<eoms:id2nameDB id="${lserveinfoList.affairLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="province" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.province" />

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.city" />

	<display:column sortable="true"
			headerClass="sortable" titleKey="lserveinfo.factory" >
			<eoms:id2nameDB id="${lserveinfoList.factory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" titleKey="lserveinfo.speciality" >
			<eoms:id2nameDB id="${lserveinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="设备类型" >
			<eoms:id2nameDB id="${lserveinfoList.deviceType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="servePopu" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.servePopu" />

	<display:column property="satisfaction" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.satisfaction" />

	<display:column property="takeCountOf" sortable="true"
			headerClass="sortable" titleKey="lserveinfo.takeCountOf" />
				
			<display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${lserveinfoList.deviceAssessApprove.approveUser}"/>		
		</display:column>	
			
		<display:column sortable="false" media="html" title="状态">
		<c:if test="${lserveinfoList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
		<c:if test="${lserveinfoList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${lserveinfoList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${lserveinfoList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/lserveinfos.do?method=edit&id=${lserveinfoList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${lserveinfoList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/lserveinfos.do?method=goToDetail&id=${lserveinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${lserveinfoList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/lserveinfos.do?method=goToDetail&id=${lserveinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>	

		<display:setProperty name="paging.banner.item_name" value="lserveinfo" />
		<display:setProperty name="paging.banner.items_name" value="lserveinfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>