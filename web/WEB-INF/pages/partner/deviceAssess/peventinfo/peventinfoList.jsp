<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/peventinfos.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="peventinfo.list.heading" />
</content>

	<display:table name="peventinfoList" cellspacing="0" cellpadding="0"
		id="peventinfoList" pagesize="${pageSize}" class="table peventinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/peventinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column property="area" sortable="true"
			headerClass="sortable" title="省份" />
	<display:column property="proNum" sortable="true"
			headerClass="sortable" titleKey="peventinfo.proNum" href="${app}/partner/deviceAssess/peventinfos.do?method=goToDetail" paramId="id" paramProperty="id"/>

	<display:column property="proName" sortable="true"
			headerClass="sortable" titleKey="peventinfo.proName" />

	<display:column sortable="true"
			headerClass="sortable" titleKey="peventinfo.proLevel" >
		<eoms:id2nameDB id="${peventinfoList.proLevel}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="peventinfo.factory" >
			<eoms:id2nameDB id="${peventinfoList.factory}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="peventinfo.speciality" >
			<eoms:id2nameDB id="${peventinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column property="beginTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="peventinfo.beginTime" />

	<display:column property="endTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="peventinfo.endTime" />

	<display:column property="satisfaction" sortable="true"
			headerClass="sortable" titleKey="peventinfo.satisfaction" />
	<display:column property="satisfactionCase" sortable="true"
			headerClass="sortable" title="满意度打分原因" />
	<display:column property="takeOf" sortable="true"
			headerClass="sortable" titleKey="peventinfo.takeOf" />

	<%--
	<display:column property="proId" sortable="true"
			headerClass="sortable" titleKey="peventinfo.proId" />
   --%>
   <display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${peventinfoList.approveUser}"/>
    </display:column>
			
	<display:column sortable="false" media="html" title="状态">
		<c:if test="${peventinfoList.deviceAssessApprove.state=='0'}">
			驳回
		</c:if>
		<c:if test="${peventinfoList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${peventinfoList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${peventinfoList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/peventinfos.do?method=edit&id=${peventinfoList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${peventinfoList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/peventinfos.do?method=goToDetail&id=${peventinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${peventinfoList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/peventinfos.do?method=goToDetail&id=${peventinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>
		<display:setProperty name="paging.banner.item_name" value="peventinfo" />
		<display:setProperty name="paging.banner.items_name" value="peventinfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>