<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
  	var checkflag = "false";
  	
	function choose() {
		var objs = document.getElementsByName("ids");
		if(checkflag=="false"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = true;
      			checkflag="true";
			}
		}else if(checkflag=="true"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = false;
      			checkflag="false"
			}
		}
	}
	  	
	function openwin(id) {
		var url = "${app}/partner/baseinfo/aptitudes.do?method=edit&proId=${proId}&id="+id;
		window.open(url,"eduWin","height=530,width=400,top=100,left=200,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");		
	}
	
	function del(){
		return checkSelect();
	}
	
	function checkSelect(){
		var objs = document.getElementsByName("ids");
		var checkflag = false;
		for(var i=0; i<objs.length; i++) {
		    if(objs[i].type.toLowerCase() == "checkbox" && objs[i].checked == true)      			
      		    checkflag = true;
		}
		if(!checkflag)
		    alert("请选择服务资质");
		return checkflag;
	}	

</script>
<!--  
<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/aptitudes.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>
-->
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
    <html:form action="/aptitudes.do?proId=${proId}" method="post" styleId="aptitudeForm">	
	<display:table name="aptitudeList" cellspacing="0" cellpadding="0"
		id="aptitudeList" pagesize="${pageSize}" class="table aptitudeList"
		export="false"
		requestURI="${app}/partner/baseinfo/aptitudes.do?method=searchOne"
		sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.baseinfo.displaytag.support.AptitudeDisplaytagDecorator">
	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" style="width:3%" />
	<display:setProperty name="css.table" value="0," />
	<display:column property="providerName" sortable="true"
			headerClass="sortable" titleKey="aptitude.providerName"  paramId="id" paramProperty="id"/>

	<display:column property="aptitudeName" sortable="true"
			headerClass="sortable" titleKey="aptitude.aptitudeName"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="aptitude.aptitudeLevle" >
			
			<eoms:id2nameDB id="${aptitudeList.aptitudeLevle}"  beanId="ItawSystemDictTypeDao" />
	</display:column> 
	
	<display:column property="aptitudeStartTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="aptitude.aptitudeStartTime"  paramId="id" paramProperty="id"/>

	<display:column property="aptitudeEndTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="aptitude.aptitudeEndTime"  paramId="id" paramProperty="id"/>

	<display:column property="aptitudeAccessory" sortable="true"
			headerClass="sortable" titleKey="aptitude.aptitudeAccessory"  paramId="id" paramProperty="id"/>
			
			<display:column titleKey="aptitude.updated">
				<c:if test="${aptitudeList != null}">
					<html:link href="javascript:openwin('${aptitudeList.id}')">
					    <img src="${app }/images/icons/edit.gif">
					</html:link>
				</c:if>
			</display:column>

		<display:setProperty name="paging.banner.item_name" value="aptitude" />
		<display:setProperty name="paging.banner.items_name" value="aptitudes" />
	</display:table>
	
	<table>
	    <tr>
	        <td>
	            <html:button styleClass="btn" property="method.added" onclick="openwin('')">
	                <fmt:message key="button.add" />
	            </html:button>		            
	            <html:submit styleClass="btn" property="method.remove" onclick="return del()">
	                <fmt:message key="button.delete" />
	            </html:submit>
	        </td>
	    </tr>
	</table>
<!--  
	<c:out value="${buttons}" escapeXml="false" />
-->
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>