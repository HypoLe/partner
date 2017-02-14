<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form  action="/grids.do?method=save" styleId="gridForm" method="post" > 

<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="grid.form.heading"/></div>
	</caption>
	<tr>
		<td class="label">
			网格名称
		</td>
		<td class="content">
			${gridForm.gridName}
		</td>	
		<td class="label">
			网格编号
		</td>
		<td class="content" >
			${gridForm.gridNumber}			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.region" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.region}" beanId="tawSystemAreaDao" />
		</td>
		<td class="label">
			<fmt:message key="grid.city" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.city}" beanId="tawSystemAreaDao" />
		</td>
	</tr>
	


	<tr>
		<td class="label">
			<fmt:message key="grid.provider" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.partnerid}" beanId="partnerDeptDao" />
		</td>
		<td class="label">
			自维/代维
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" itemId="${gridForm.maintainType}" beanId="id2nameXML" />
		</td>		
	</tr>
	<tr>
		<td class="label">
			网格运维监督员
		</td>
		<td class="content" >
			<eoms:id2nameDB id="${gridForm.gridMonitor}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">
			网格运维调度员
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.gridYardman}" beanId="tawSystemUserDao" />
		</td>		
	</tr>
	<tr>
		<td class="label">
			经度
		</td>
		<td class="content">
			${gridForm.longitude}
		</td>
		<td class="label">
			纬度
		</td>
		<td class="content">
			${gridForm.latitude}
		</td>
	</tr>
	<tr>
		<td class="label">
			网格说明
		</td>
		<td class="content" colspan="3">
			${gridForm.regionExplain}
		</td>	
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="grid.addUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.addUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">
			<fmt:message key="grid.addTime" />
		</td>
		<td class="content">
			${gridForm.addTime}
		</td>
	</tr>
<c:if test="${gridForm.updateUser != null}">
	<tr>
		<td class="label">
			<fmt:message key="grid.updateUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${gridForm.updateUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">
			<fmt:message key="grid.updateTime" />
		</td>
		<td class="content">
			${gridForm.updateTime}
		</td>
	</tr>
</c:if>
</table>
</fmt:bundle>
	<table>
		<tr>
			<td>
				<c:if test="${param.flag!='search'}">
					<input type="button" class="btn"
						value="<fmt:message key="button.edit"/>"
						onclick="javascript:if(confirm('确定修改吗?')){
						var url=eoms.appPath+'/partner/net/grids.do?method=edit&id=${gridForm.id}';
						location.href=url}" />
					<input type="button" class="btn"
						value="<fmt:message key="button.delete"/>"
						onclick="javascript:if(confirm('确定删除吗?')){
						var url=eoms.appPath+'/partner/net/grids.do?method=remove&id=${gridForm.id}';
						location.href=url}" />
				
				</c:if>
			</td>
		</tr>
	</table>
</html:form>
	<div class="header center"><b>网格下基站列表</b></div>
	<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">
		<display:table name="siteList" cellspacing="0" cellpadding="0"
			id="siteList" pagesize="${pageSize}" class="table siteList"
			export="false" 
			requestURI="${app}/partner/net/grids.do?method=detailWithSite&gridId=${gridForm.id}"
			sort="list" partialList="true" size="resultSize"  
			decorator="com.boco.eoms.partner.net.displaytag.support.SiteDisplaytagDecorator">
			<display:setProperty name="css.table" value="0," />	
			
			<display:column property="siteName" sortable="true"
					headerClass="sortable" titleKey="site.siteName" paramId="siteNo" paramProperty="siteNo"/>
		
			<display:column property="siteNo" sortable="true"
					headerClass="sortable" titleKey="site.siteNo"  paramId="id" paramProperty="id"/>
			<!-- 所在地市 -->
			<display:column  sortable="true" headerClass="sortable" titleKey="line.region">
				<eoms:id2nameDB id="${siteList.region}" beanId="tawSystemAreaDao" />
			</display:column>			
		
			<!-- 所在县区 -->
			<display:column  sortable="true" headerClass="sortable" titleKey="line.city">
				<eoms:id2nameDB id="${siteList.city}" beanId="tawSystemAreaDao" />
			</display:column>			
			
		
			<display:column property="grid" sortable="true"
					headerClass="sortable" titleKey="site.grid"  paramId="id" paramProperty="id"/>
			<display:column sortable="true" headerClass="sortable" titleKey="site.siteType">
				<c:if test="${siteList.siteType!=null}">
								<eoms:id2nameDB id="${siteList.siteType}"  beanId="IItawSystemDictTypeDao" />
				</c:if>
				<c:if test="${siteList.siteType==null}">
					暂无
				</c:if>
			</display:column>
		
			<display:column property="partnername" sortable="true"
					headerClass="sortable" titleKey="site.provider"  paramId="id" paramProperty="id"/>
			<display:column property="longitude" sortable="true"
					headerClass="sortable" titleKey="site.longitude"  paramId="id" paramProperty="id"/>
		
			<display:column property="latitude" sortable="true"
					headerClass="sortable" titleKey="site.latitude"  paramId="id" paramProperty="id"/>
					
			<display:column sortable="true" headerClass="sortable" titleKey="site.siteLevle"  paramId="id" paramProperty="id">
				${siteList.siteLevle}
			</display:column>
			<c:if test="${param.flag!='search'}">
				<display:column title="操作" headerClass="imageColumn">
					
					    <a href='${app}/partner/net/sites.do?method=detailWithInspect&siteId=${siteList.id}' target='_blank'>
					         查看</a>
				</display:column> 
			</c:if>	
		
			<display:setProperty name="paging.banner.item_name" value="site" />
			<display:setProperty name="paging.banner.items_name" value="sites" />
		</display:table>
</fmt:bundle>
<input type="hidden" id="gridId" name="gridId" value="${gridForm.id}"/>
<input type="hidden" id="region" name="region" value="${ gridForm.region}"/>
<input type="hidden" id="city" name="city" value="${gridForm.city}"/>
<input type="hidden" id="gridNumber" name="gridNumber" value="${gridForm.gridNumber }"/>
<input type="hidden" id="partnerid" name="partnerid" value="${ gridForm.partnerid}"/>
<input type="button" class="btn"
						value="新增网格下基站"
						onclick="javascript:if(true){
						var url=eoms.appPath+'/partner/net/sites.do?method=edit&gridId=${gridForm.id}';
						window.open(url,'_blank');}" />
<%@ include file="/common/footer_eoms.jsp"%>