<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript">
    var checkflag = "false";
	function chooseAll(){	
	    var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        }
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    }
		    checkflag="false";
	    }
	}
	
	function isChecked(flag){
		var notice = '';
		var pass = '';
		if(flag == 'yes'){
			notice='确认审批通过(审批通过人员将具备考试资格)？';
			pass = 'true';
		}else{
			notice='确认审批不通过(审批通过人员将不具备考试资格)？';
			pass = 'false';
		}
		if(confirm(notice)){
			var objs = document.getElementsByName("checkbox11");
		    document.forms[1].action = "${app}/partner/baseinfo/partnerUsers.do?method=approveAll&pass="+pass+"";
		    var flag = false;
		    for(var i=0; i<objs.length; i++){
		       if(objs[i].checked==true){
		           flag=true;
		           break;
		       }
		    }
		    if(flag==true){
		       document.forms[1].submit();
		       return;
		    }
		    else if(flag==false){
		        alert("请选择审批项！");
		        return;
		    }
		}
	}
</script>

<c:if test="${in==null}">
<c:set var="buttons">

</br>
	<a href='javascript:isChecked("yes")'>审批通过</a>	
    <a href='javascript:isChecked("no")'>审批不通过</a>	
</c:set>
</c:if>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<html:form action="partnerUsers.do?method=approvedList" method="post"  styleId="partnerUserForm">
<c:if test="${in!=null}">
<%@ include file="/WEB-INF/pages/partner/baseinfo/hrefSearch.jsp"%>
</c:if>
<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="partnerUser.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			人员姓名
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="name"
						class="text medium"
						alt="allowBlank:false,vtext:''"  />
		</td>
		<td class="label">
			<fmt:message key="partnerUser.deptName" />
		</td>
		<td class="content">	
		<eoms:pnrComp name="prov" id="prov" />
		</td>
		
		
	</tr>
	<tr>	
		<td class="label">
			<fmt:message key="partnerUser.email" />
		</td>
		<td class="content">
			<input type="text" name="emailSearch" id="email"
						class="text medium"
						alt="allowBlank:true,vtext:''"  />
		</td>
		
		<td class="label">
			<fmt:message key="partnerUser.phone" />
		</td>
		<td class="content">
			<input type="text" name="phoneSearch" id="phone"
						class="text medium"
						alt="allowBlank:true,vtext:''"  />
		</td>
	</tr>
</table>

<table align="center">
	<tr>
		<td>
			<input type="submit" class="btn" value="查询" />
			
			<input type="reset" class="btn" value="重置" >
		</td>
	</tr>
</table>
</html:form>	


<html:form action="partnerUsers" method="post"  styleId="partnerUserForm">
	<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<content tag="heading">
	<fmt:message key="partnerUser.list.heading" />
</content>

	<display:table name="partnerUserList" cellspacing="0" cellpadding="0"
		id="partnerUserList" pagesize="${pageSize}" class="table partnerUserList"
		export="false"
		requestURI="${app}/partner/baseinfo/partnerUsers.do?method=approvedList"
		sort="list" partialList="true" size="resultSize">

    <display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
         <input type="checkbox" name="checkbox11" value="<c:out value='${partnerUserList.id}'/>" >
    </display:column>

	<display:column property="name" sortable="true"      
			headerClass="sortable" titleKey="partnerUser.name" />
	
	<display:column  sortable="true"
			headerClass="sortable" titleKey="partnerUser.sex"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${partnerUserList.sex}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>		

	<display:column property="areaName" sortable="true"
			headerClass="sortable" titleKey="partnerUser.areaName"  paramId="id" paramProperty="id"/>

	<display:column property="personCardNo" sortable="true"
			headerClass="sortable" titleKey="partnerUser.personCardNo"  paramId="id" paramProperty="id"/>
			
	<display:column property="deptName" sortable="true"
			headerClass="sortable" titleKey="partnerUser.deptName"  paramId="id" paramProperty="id"/>

	<display:column property="phone" sortable="true"
			headerClass="sortable" titleKey="partnerUser.phone"  paramId="id" paramProperty="id"/>

	<display:column property="email" sortable="true"
			headerClass="sortable" titleKey="partnerUser.email"  paramId="id" paramProperty="id"/>
	
	<display:column  sortable="true"
			headerClass="sortable" title="在岗状态"  >
				未上岗 
	</display:column>
	
	<display:column  sortable="true"
			headerClass="sortable" title="审批状态"  >
				待审批
	</display:column>
				
		<display:setProperty name="paging.banner.item_name" value="partnerUser" />
		<display:setProperty name="paging.banner.items_name" value="partnerUsers" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
</html:form>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
