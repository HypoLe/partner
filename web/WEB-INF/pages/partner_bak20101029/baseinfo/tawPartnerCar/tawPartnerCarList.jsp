<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
	
<script type="text/javascript">
function setvalue(ll){
   return document.getElementById(ll).value;
}
</script>

<c:if test="${in!=null}">
<%@ include file="/WEB-INF/pages/partner/baseinfo/hrefSearch.jsp"%>
</c:if>
<c:choose>
	<c:when test="${isCity=='yes'}">
<c:set var="buttons">
		  <br/>
	<a href='tawPartnerCars.do?method=add&nodeId=${nodeId}'><fmt:message key="button.add"/></a>
</c:set>
</c:when>
</c:choose>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="tawPartnerCars.do?method=xquery" method="post"
		styleId="tawPartnerCarForm">
		<input type="hidden" name="">
	<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="tawPartnerCar.list.heading" /></div>
	</caption>
			<tr>
				<td class="label">
			<fmt:message key="tawPartnerCar.car_number" />
		</td>
		<td class="content">
			<html:text property="car_number" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.car_number}" />
		</td>
		<td class="label">
			<fmt:message key="tawPartnerCar.piece" />
		</td>
		<td class="content">
		<eoms:comboBox name="piece" id="piece" initDicId="11210"
						defaultValue='${tawPartnerCarForm.piece}' />
				</tr>
				<tr>
				<td class="label">
			<fmt:message key="tawPartnerCar.area_id" />
		</td>
		<td class="content">
		<%
							List listId = (List) request.getAttribute("listId");
							List listName = (List) request.getAttribute("listName");
							TawPartnerCarForm fm = (TawPartnerCarForm) request
									.getAttribute("tawPartnerCarForm");
							String nodeId = fm.getArea_id();
		 %>
  <c:choose>
	<c:when test="${in=='province'}">
		<html:select property="area_id" styleId="area_id" size="1" onchange="changeDep();" >
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
			<html:select property="area_id" styleId="area_id" size="1" disabled="true" onchange="changeDep();">
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
			<%--<html:text property="area_id" styleId="area_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.area_id}" />
		</td>
				--%><td class="label">
			<fmt:message key="tawPartnerCar.dept_id" />
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
		
			<%--<html:text property="dept_id" styleId="dept_id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${tawPartnerCarForm.dept_id}" />
		--%></td>
		
		</tr>
		<tr>
		<input type="hidden" name="nodeId" value="<%=request.getAttribute("nodeId") %>">
				 <td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
				</td>
				</tr>
		</table>
	</html:form>

<html:form action="tawPartnerCars" method="post" styleId="tawPartnerCarForm">
	<display:table name="tawPartnerCarList" cellspacing="0" cellpadding="0"
		id="tawPartnerCarList" pagesize="${pageSize}" class="table tawPartnerCarList"
		export="false" decorator="com.boco.eoms.partner.baseinfo.util.PartnerCarDecorator"
		requestURI="${app}/partner/baseinfo/tawPartnerCars.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="car_number" sortable="true"
			headerClass="sortable" titleKey="tawPartnerCar.car_number" href="${app}/partner/baseinfo/tawPartnerCars.do?method=detail" paramId="id" paramProperty="id"/>

	<display:column property="dept_id" sortable="true"
			headerClass="sortable" titleKey="tawPartnerCar.dept_id" />

	<display:column property="area_id" sortable="true"
			headerClass="sortable" titleKey="tawPartnerCar.area_id" />

	<display:column property="start_time" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="tawPartnerCar.start_time" />



	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="tawPartnerCar.remark" />

		<display:setProperty name="paging.banner.item_name" value="tawPartnerCar" />
		<display:setProperty name="paging.banner.items_name" value="tawPartnerCars" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
</html:form>
</fmt:bundle>
<script type="text/javascript">

var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 var deptId ="<%=((TawPartnerCarForm) request
							.getAttribute("tawPartnerCarForm")).getDept_id()%>";
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawPartnerCars.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 changeList(url,fieldName,"");
		}

  </script>
<%@ include file="/common/footer_eoms.jsp"%>