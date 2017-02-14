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
		var url = "${app}/partner/baseinfo/resumes.do?method=edit&personCardNo=${personCardNo}&id="+id;
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
		    alert("请选择人员简历");
		return checkflag;
	}	

</script>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
    <html:form action="/resumes.do?personCardNo=${personCardNo}" method="post" styleId="resumeForm">
	<display:table name="resumeList" cellspacing="0" cellpadding="0"
		id="resumeList" pagesize="${pageSize}" class="table resumeList"
		export="false"
		requestURI="${app}/partner/baseinfo/resumes.do?method=searchOne"
		sort="list" partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.baseinfo.displaytag.support.ResumeDisplaytagDecorator">
	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" style="width:3%" />
	<display:setProperty name="css.table" value="0," />

	<display:column property="personnelName" sortable="true"
			headerClass="sortable" titleKey="resume.personnelName"  paramId="id" paramProperty="id"/>

	<display:column property="idCardNo" sortable="true"
			headerClass="sortable" titleKey="resume.idCardNo"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="provider" titleKey="resume.provider" >
		<eoms:id2nameDB id="${resumeList.provider}" beanId="partnerDeptDao" />
	</display:column>
	
	<display:column property="post" sortable="true"
			headerClass="sortable" titleKey="resume.post"  paramId="id" paramProperty="id"/>

	<display:column property="commencementDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="resume.commencementDate"  paramId="id" paramProperty="id"/>

	<display:column property="dimissionDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="resume.dimissionDate"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="resume.state" >
			<eoms:dict key="dict-partner-baseinfo" dictId="state" itemId="${resumeList.state}" beanId="id2nameXML" />
	</display:column>
	
	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="resume.remark"  paramId="id" paramProperty="id"/>
			<display:column titleKey="aptitude.updated">
				<c:if test="${resumeList != null}">
					<html:link href="javascript:openwin('${resumeList.id}')">
					    <img src="${app }/images/icons/edit.gif">
					</html:link>
				</c:if>
			</display:column>

		<display:setProperty name="paging.banner.item_name" value="resume" />
		<display:setProperty name="paging.banner.items_name" value="resumes" />
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
	            <html:button styleClass="btn" property="flush" onclick="location.reload() ">
	                刷新
	            </html:button>
	        </td>
	    </tr>
	</table>
<!--	<c:out value="${buttons}" escapeXml="false" />
 -->
 </html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>