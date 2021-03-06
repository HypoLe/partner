<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
	
<c:if test="${in!=null}">
<%@ include file="/WEB-INF/pages/partner/baseinfo/hrefSearch.jsp"%>
</c:if>
<c:choose>
	<c:when test="${isCity=='yes'}">
<c:set var="buttons">
		  <br/>
	<a href='tawPartnerOils.do?method=add&nodeId=${nodeId}'><fmt:message key="button.add"/></a>
</c:set>
</c:when>
</c:choose>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
	<html:form action="tawPartnerOils.do?method=xquery" method="post"
		styleId="tawPartnerOilForm">
		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="tawPartnerOil.list.heading" />
				</div>
			</caption>
			<tr>
				<td class="label">
					<fmt:message key="tawPartnerOil.oil_number" />
				</td>
				<td class="content">
					<html:text property="oil_number" styleId="oil_number"
						styleClass="text medium "/>
		        </td>
		<td class="label">
			<fmt:message key="tawPartnerOil.factory" />
		</td>
		<td class="content">
<!-- 	<eoms:comboBox name="factory" id="factory" initDicId="11208"
					defaultValue='${tawPartnerOilForm.factory}' alt="allowBlank:false,vtext:''" />
 -->	
 
 			<html:text property="factory" styleId="factory"
						styleClass="text medium"/>
		
		
		</td>
				</tr>
				<tr>
				<td class="label">
					<fmt:message key="tawApparatus.area_id" />
				</td>
				<td class="content">
				<%
							List listId = (List) request.getAttribute("listId");
							List listName = (List) request.getAttribute("listName");
							TawPartnerOilForm fm = (TawPartnerOilForm) request
									.getAttribute("tawPartnerOilForm");
							String nodeId = fm.getArea_id();
				 %>
	<c:choose>
	<c:when test="${in=='province'}">
					<html:select property="area_id" styleId="area_id" size="1" onchange="changeDep();">
						<%
							if(nodeId==null){
							nodeId=" ";
							}
							for (int i = 0; i < listId.size(); i++) {
								if (nodeId.equals(listId.get(i))) {
						%>
						<option value="<%=listId.get(i)%>" selected>
							<%=listName.get(i)%>
						</option>
						<%
						} else {
						%>
						<option value="<%=listId.get(i)%>">
							<%=listName.get(i)%>
						</option>
						<%
							}
							}
						%>
					</html:select>
	</c:when>
	<c:otherwise>
						<html:select property="area_id" styleId="area_id" disabled="true" size="1" onchange="changeDep();">
						<%
							if(nodeId==null){
							nodeId=" ";
							}
							for (int i = 0; i < listId.size(); i++) {
								if (nodeId.equals(listId.get(i))) {
						%>
						<option value="<%=listId.get(i)%>" selected>
							<%=listName.get(i)%>
						</option>
						<%
						} else {
						%>
						<option value="<%=listId.get(i)%>">
							<%=listName.get(i)%>
						</option>
						<%
							}
							}
						%>
					</html:select>
					<html:hidden property="area_id"/>
	</c:otherwise>
	</c:choose>
					</td>
		<td class="label">
			<fmt:message key="tawPartnerOil.dept_id" />
		</td>
		<td class="content">
		<c:choose>
		<c:when test="${in=='province'||in=='area'}">	
		<html:select property="dept_id" styleId="dept_id" size="1">
					</html:select>
		</c:when>
		<c:when test="${in=='factory'||in==null}"> 
				<html:select property="dept_id" disabled="true" styleId="dept_id" size="1">
					</html:select>
					<html:hidden property="dept_id"/>
		</c:when>
		</c:choose>
		<%--
			<html:text property="dept_id" styleId="dept_id"
						styleClass="text medium" />
		--%></td>
		</tr>
		<tr>
		<td class="label">
			<fmt:message key="tawPartnerOil.type" />
		</td>
		<td class="content">
<!-- 	<eoms:comboBox name="type" id="type" initDicId="11209"
					defaultValue='${tawPartnerOilForm.type}' alt="allowBlank:false,vtext:''" />
 -->	
 		<html:text property="type" styleId="type"
						styleClass="text medium"/>
		</td>
		
		<td class="label">
			<fmt:message key="tawPartnerOil.state" />
		</td>
		<td class="content">
		<eoms:comboBox name="state" id="state" initDicId="11205"/>
		</td>
		</tr>
		<input type="hidden" name="nodeId" value="<%=request.getAttribute("nodeId") %>">
		<tr>
		
		<td class="label">
			<fmt:message key="tawPartnerOil.storage" />
		</td>
		<td class="content" colspan="3">
			<html:text property="storage" styleId="storage"
						styleClass="text medium"/>
		</td>
		</tr>
		<tr>
		
				<td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
				</tr>
		</table>
	</html:form>
	
	<display:table name="tawPartnerOilList" cellspacing="0" cellpadding="0"
		id="tawPartnerOilList" pagesize="${pageSize}" class="table tawPartnerOilList"
		export="false" decorator="com.boco.eoms.partner.baseinfo.util.PartnerOilDecorator"
		requestURI="${app}/partner/baseinfo/tawPartnerOils.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="oil_number" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.oil_number" href="${app}/partner/baseinfo/tawPartnerOils.do?method=detail" paramId="id" paramProperty="id"/>

			
	<display:column property="factory" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.factory" />
			

	<display:column property="dept_id" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.dept_id" />





	<display:column property="type" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.type" />



	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.power" >
					<eoms:id2nameDB id="${tawPartnerOilList.power}" beanId="ItawSystemDictTypeDao" />
			</display:column>

	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.kind" >
					<eoms:id2nameDB id="${tawPartnerOilList.kind}" beanId="ItawSystemDictTypeDao" />
			</display:column>

	<display:column  sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.state" >
					<eoms:id2nameDB id="${tawPartnerOilList.state}" beanId="ItawSystemDictTypeDao" />
			</display:column>

	<display:column property="storage" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.storage" />

		<display:setProperty name="paging.banner.item_name" value="tawPartnerOil" />
		<display:setProperty name="paging.banner.items_name" value="tawPartnerOils" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<script type="text/javascript">

var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 var deptId ="<%=((TawPartnerOilForm) request
							.getAttribute("tawPartnerOilForm")).getDept_id()%>";
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerOils.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 changeList(url,fieldName,"");
		}

  </script>
<%@ include file="/common/footer_eoms.jsp"%>
