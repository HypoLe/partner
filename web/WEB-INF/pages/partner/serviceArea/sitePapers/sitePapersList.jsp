<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
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
		var url = eoms.appPath+"/partner/serviceArea/sitePaperss.do?idSite=${idSite}&siteName=${siteName}&method=edit&id="+id;
		window.open(url,"eduWin","height=500,width=400,top=100,left=200,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");		
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
		    alert("请选择基站证件");
		return checkflag;
	}	
</script>
<c:set var="buttons">
		  <br/>
		<a href='sitePaperss.do?method=add'><fmt:message key="button.add"/></a>
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<content tag="heading">
	<fmt:message key="sitePapers.list.heading" />
</content>
    <html:form action="/sitePaperss.do" method="post" styleId="sitePapersForm">	
	<display:table name="sitePapersList" cellspacing="0" cellpadding="0"
		id="sitePapersList" pagesize="${pageSize}" class="table sitePapersList"
		export="false"
		requestURI="${app}/partner/serviceArea/sitePaperss.do?method=search"
		sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.serviceArea.displaytag.support.SitePapersDisplaytagDecorator">

	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" style="width:3%" />
	<display:setProperty name="css.table" value="0," />
	<display:column property="siteName" sortable="true"
			headerClass="sortable" titleKey="sitePapers.siteName" paramId="id" paramProperty="id"/>

	<display:column property="papersNo" sortable="true"
			headerClass="sortable" titleKey="sitePapers.papersNo"  paramId="id" paramProperty="id"/>

	<display:column property="startTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="sitePapers.startTime"  paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="sitePapers.endTime"  paramId="id" paramProperty="id"/>

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="sitePapers.remark"  paramId="id" paramProperty="id"/>

			<display:column titleKey="button.updated">
				<c:if test="${sitePapersList != null}">
					<html:link href="javascript:openwin('${sitePapersList.id}')">
					    <img src="${app }/images/icons/edit.gif">
					</html:link>
				</c:if>
			</display:column>
		<display:setProperty name="paging.banner.item_name" value="sitePapers" />
		<display:setProperty name="paging.banner.items_name" value="sitePaperss" />
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
<html:hidden property="idSite" value="${idSite}"/>
	</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>