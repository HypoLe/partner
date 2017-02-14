<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">
  function deleteConfirm()
	{
	   if(confirm('确定要删除吗？'))
	{
		return true;
		}else
		return false;
		}
	function clearConfirm()
	{
	  if(confirm('确定清空回收站吗？'))
	  {
	  	return true;
	  }else
	  	return false;
	}
	function changeAction()
	{	
		var revivifyButton=document.getElementById("recyclefrm");
		revivifyButton.action="ctContentss.do?method=revivify";
		revivifyButton.submit();
	}
</script>
<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<table class="formTable">
	<caption>
			<div class="header center"><fmt:message key="ctContents.recycle" />
			<img src="${app}/images/icons/icon.gif"/>
			</div>
	</caption>
</table>	
<table width="20%" cellspacing="0" cellpadding="0">
	
	<tr>
	<td>
		<html:form action="/ctContentss.do?method=recycleDelAll" method="post" styleId="ctContentsForm">
			   <input type="submit" ${disbutton} value="<fmt:message key="ctContents.clearup"/>"  class="btn" id="clearbtn" onclick="return clearConfirm()"/>
		</html:form>
		</td>
		<td>
		<html:form action="/ctContentss.do?method=revivifyAll" method="post" styleId="ctContentsForm">
				<input type="submit" ${disbutton} value="<fmt:message key="ctContents.revivifyAll"/>" class="btn"/>
		</html:form>
		</td>
	</tr>
</table></br>
<form id="recyclefrm" action="/ctContentss.do?method=recycleDel" method="post" styleId="ctContentsForm">
    <display:table name="ctContentsRecycleList" cellspacing="0" cellpadding="0"
		id="ctContentsRecycleList" pagesize="${pageSize}" class="table ctContentsRecycleList"
		export="false"
		requestURI="${app}/partner/contract/ctContentss.do?method=reycle"
		sort="list" partialList="true" size="resultSize">

	
	<display:column 
			headerClass="sortable" titleKey="ctContents.contentTitle" paramId="id" paramProperty="id">
		${ctContentsRecycleList.contentTitle}
		
	</display:column>
	<display:column 
			headerClass="sortable" titleKey="ctContents.createUser" paramId="id" paramProperty="id">
		${ctContentsRecycleList.createUser}
		
	</display:column>
	<display:column  headerClass="sortable" titleKey="ctContents.choose" paramId="id" paramProperty="id">
		<input type="submit" value="<fmt:message key="ctContents.deleted"/>" class="btn" onclick="return deleteConfirm()"/>
		<img src="${app}/images/icons/list-delete.gif"/> &nbsp;&nbsp;&nbsp;&nbsp;  
		<input type="button" id="revivify" value="<fmt:message key="ctContents.revivify"/>" class="btn" onclick="return changeAction()"/>
		<img src="${app}/images/icons/refresh.gif"/>
		<input type="hidden" value="${ctContentsRecycleList.themeId}" name="themeId" id="themeId"/>
	</display:column>
	<display:setProperty name="export.Excel" value="true"/>
	</display:table>
</form>

</fmt:bundle>
	
	
<%@ include file="/common/footer_eoms.jsp"%>