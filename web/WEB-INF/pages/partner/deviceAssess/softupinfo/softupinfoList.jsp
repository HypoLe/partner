<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/softupinfos.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="softupinfo.list.heading" />
</content>

	<display:table name="softupinfoList" cellspacing="0" cellpadding="0"
		id="softupinfoList" pagesize="${pageSize}" class="table softupinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/softupinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">
<%-- 
	<display:column property="sheetId" sortable="true"
			headerClass="sortable" titleKey="softupinfo.sheetId" href="${app}/partner/deviceAssess/softupinfos.do?method=edit" paramId="id" paramProperty="id"/>
--%>
	<display:column property="sheetNum" sortable="true"
			headerClass="sortable" titleKey="softupinfo.sheetNum" />

	<display:column property="createTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="softupinfo.createTime" />

	<display:column property="pigeonholeTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="softupinfo.pigeonholeTime" />

	<display:column property="affairName" sortable="true"
			headerClass="sortable" titleKey="softupinfo.affairName" />

	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.affairLevel" >
			<eoms:id2nameDB id="${softupinfoList.affairLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="province" sortable="true"
			headerClass="sortable" titleKey="softupinfo.province" />

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="softupinfo.city" />

	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.factory" >
			<eoms:id2nameDB id="${softupinfoList.factory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.speciality" >
			<eoms:id2nameDB id="${softupinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.equipmentType" >
			<eoms:id2nameDB id="${softupinfoList.equipmentType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="equipmentName" sortable="true"
			headerClass="sortable" titleKey="softupinfo.equipmentName" />

	<display:column property="nonceEdition" sortable="true"
			headerClass="sortable" titleKey="softupinfo.nonceEdition" />

	<display:column property="updateEdition" sortable="true"
			headerClass="sortable" titleKey="softupinfo.updateEdition" />

	<display:column property="firstUnsucceeTime" sortable="true"  title="第一次未成功的升级时间"
			headerClass="sortable"  />

	<display:column property="finalUpTime" sortable="true"
			headerClass="sortable" title="最终升级时间" />

	<display:column property="finalResult" sortable="true"
			headerClass="sortable" title="最终结果" />

	<display:column property="takeCountOf" sortable="true"
			headerClass="sortable" titleKey="softupinfo.takeCountOf" />
			
	<display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${softupinfoList.deviceAssessApprove.approveUser}"/>		
		</display:column>	
			
		<display:column sortable="false" media="html" title="状态">
		<c:if test="${softupinfoList.deviceAssessApprove.state=='0'}">
			 驳回
		</c:if>
		<c:if test="${softupinfoList.deviceAssessApprove.state=='1'}">
			通过
		</c:if>
		<c:if test="${softupinfoList.deviceAssessApprove.state=='2'}">
			待审批
		</c:if>
	</display:column>
	<display:column sortable="false" media="html" title="操作">
		<c:if test="${softupinfoList.deviceAssessApprove.state=='0'}">
			<a  href="${app}/partner/deviceAssess/softupinfos.do?method=edit&id=${softupinfoList.id }"
				 shape="rect">
				编辑并提交
			</a> 
		</c:if>
		<c:if test="${softupinfoList.deviceAssessApprove.state=='1'}">
			<a  href="${app}/partner/deviceAssess/softupinfos.do?method=goToDetail&id=${softupinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
		<c:if test="${softupinfoList.deviceAssessApprove.state=='2'}">
			<a  href="${app}/partner/deviceAssess/softupinfos.do?method=goToDetail&id=${softupinfoList.id }"
				 shape="rect">
				详情查看
			</a> 
		</c:if>
	</display:column>			

		<display:setProperty name="paging.banner.item_name" value="softupinfo" />
		<display:setProperty name="paging.banner.items_name" value="softupinfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>