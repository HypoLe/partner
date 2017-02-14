<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;
	
	"%>
	
	
<c:if test="${in!=null}">
</c:if>
<c:choose>
	<c:when test="${isCity=='yes'}">
		<c:set var="buttons">
				  <br/>
				<a href='tawApparatuss.do?method=add&nodeId=${nodeId}'><fmt:message key="button.add"/></a>	
		</c:set>
	</c:when>
</c:choose>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
	<html:form action="tawApparatuss.do?method=xquery&in=${in}&nodeId=${nodeId}" method="post"
		styleId="tawApparatusForm">
		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="tawApparatus.list.heading" />
				</div>
			</caption>
			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.type" />
				</td>
				<td class="content">
					<eoms:comboBox name="type" id="type" initDicId="11204" />
				</td>
				<td class="label">
					<fmt:message key="tawApparatus.state" />
				</td>
				<td class="content">
					<eoms:comboBox name="state" id="state" initDicId="11205" />
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="tawApparatus.dept_id" />
				</td>
				<td class="content">
				<eoms:pnrComp name="bigpartnerid" id="dept_id" onchange="getselcompany()"/>
				</td>

			</tr>
			<tr>
<input type="hidden" name="nodeId" value="<%=request.getAttribute("nodeId") %>">
				<td colspan="4" align="center">
					<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
			</tr>
		</table>  
	</html:form>
	<display:table name="tawApparatusList" cellspacing="0" cellpadding="0"
		id="tawApparatusList" pagesize="${pageSize}" decorator="com.boco.eoms.partner.baseinfo.util.PartnerApparatusDecorator"
		class="table tawApparatusList" export="false"
		requestURI="${app}/partner/baseinfo/tawApparatuss.do?method=search"
		sort="list" partialList="true" size="resultSize">
		
		<%--<display:column property="apparatusName" sortable="true"
					href="${app}/partner/baseinfo/tawApparatuss.do?method=detail&&id=${tawApparatusList.id }"
			headerClass="sortable" titleKey="tawApparatus.apparatusName"/>
		--%><display:column  sortable="true"
			headerClass="sortable" titleKey="tawApparatus.apparatusName">
			<a href="${app}/partner/baseinfo/tawApparatuss.do?method=detail&&id=${tawApparatusList.id }">${tawApparatusList.apparatusName }</a>
			</display:column>
		<display:column property="apparatusId" sortable="true"
			headerClass="sortable" titleKey="tawApparatus.apparatusId"
			paramId="id" paramProperty="id" />
			<%--href="${app}/partner/baseinfo/tawApparatuss.do?method=detail"--%>

		<display:column sortable="true" headerClass="sortable"
			titleKey="tawApparatus.factory">
				<eoms:id2nameDB id="${tawApparatusList.factory}"
					beanId="ItawSystemDictTypeDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable"
			titleKey="tawApparatus.type">
				<eoms:id2nameDB id="${tawApparatusList.type}"
					beanId="ItawSystemDictTypeDao" />
		</display:column>

		<display:column property="model" sortable="true"
			headerClass="sortable" titleKey="tawApparatus.model"
			paramId="id" paramProperty="id" />

		<display:column sortable="true" headerClass="sortable"
			titleKey="tawApparatus.state">
				<eoms:id2nameDB id="${tawApparatusList.state}"
					beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="所属公司"  >
			
			<eoms:id2nameDB beanId="partnerDeptDao"  id="${tawApparatusList.partnerid }"/>
			</display:column>
			<display:column sortable="true"
			headerClass="sortable" title="操作"  >
			<a href="javascript:remove('${tawApparatusList.id}');">删   除</a>
			</display:column>
		<display:setProperty name="paging.banner.item_name"
			value="tawApparatus" />
		<display:setProperty name="paging.banner.items_name"
			value="tawApparatuss" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>
<input type="button" value="添加" onclick="add()"/>
<script type="text/javascript">
function remove(id){
window.location.href="<%=request.getContextPath()%>/partner/baseinfo/tawApparatuss.do?method=remove&id="+id;
}
function add(){
window.location.href="<%=request.getContextPath()%>/partner/baseinfo/tawApparatuss.do?method=edit";

}
var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawApparatuss.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 var deptId ="<%=((TawApparatusForm) request.getAttribute("tawApparatusForm")).getDept_id()%>";
			 changeList(url,fieldName,deptId);
function changeDep()
		{    
			 var id = document.getElementById("area_id").value;
			 var url="<%=request.getContextPath()%>/partner/baseinfo/tawApparatuss.do?method=changeDep2&id="+id;
			 var fieldName = "dept_id";
			 changeList(url,fieldName,"");
		}

  </script>
<%@ include file="/common/footer_eoms.jsp"%>
