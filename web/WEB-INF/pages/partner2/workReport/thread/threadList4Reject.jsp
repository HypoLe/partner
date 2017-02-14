
<jsp:directive.page import="com.boco.partner2.workReport.util.InfopubConstants"/><%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<content tag="heading">标题</content>
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
	
	function sort(){
		var toForumsId=document.getElementById("toForumsId");
		if(""==toForumsId.value){
			alert('请选择');
			return false;
		}
		var flag=false;
		var objs = document.getElementsByName("ids");
           	 for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			if(objs[i].checked){
      				flag=true;
      			}
			 }
			 if(flag){
             	return true;
             }else {
	            alert("未选择");
             	return false;
            }
	}
	
	function ConfirmDel(){
			var flag=false;
           if(confirm("确定删除吗?")){
           	 var objs = document.getElementsByName("ids");
           	 for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			if(objs[i].checked){
      				flag=true;
      			}
			 }
			 if(flag){
             	return true;
             }else {
	            alert("未选择");
             	return false;
             }
             
           }else{
             return false;
           }
    }
</script>


<html:form action="/thread.do" method="post" styleId="workReportForm" >

<html:hidden property="forumsId"/>

<!-- <c:out value="${buttons}" escapeXml="false"/> -->
<display:table name="threadList" cellspacing="0" cellpadding="0"
    id="threadList" pagesize="${pageSize }" class="table threadList"
    sort="external" defaultsort="1"
    export="true" requestURI="${app}/partner2/workReport/thread.do?method=list4forumId&forumsId=${forumsId }" partialList="true" size="resultSize"
    decorator="com.boco.partner2.workReport.displaytag.support.ThreadListDisplaytagDecorator" >
    
    <display:setProperty name="export.rtf" value="false"></display:setProperty>
    
	
	<display:column property="title" sortable="true" sortName="title" headerClass="sortable"
         titleKey="infopub.threadForm.title" paramId="id" paramProperty="id" href="${app}/partner2/workReport/thread.do?method=detail"/>
         
 	<display:column property="status" sortable="true" sortName="status" headerClass="sortable"
         titleKey="infopub.threadForm.status" href="${app}/partner2/workReport/threadAuditHistory.do?method=edit" paramId="threadId" paramProperty="id"/>
         
    <display:column property="threadCount" sortable="true" sortName="threadCount" headerClass="sortable"
         titleKey="infopub.threadForm.num" href="${app}/partner2/workReport/threadHistory.do?method=search" paramId="threadId" paramProperty="id"/>

    <display:column property="createrName" sortable="true" sortName="createrName" headerClass="sortable"
         titleKey="infopub.threadForm.createrId"/>

    <display:column property="createTime" sortable="true" sortName="createTime" headerClass="sortable"
         titleKey="infopub.threadForm.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>

    <display:column property="editTime" sortable="true" sortName="editTime" headerClass="sortable"
         titleKey="infopub.threadForm.editTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>

    <display:column  sortable="true" sortName="threadTypeId" headerClass="sortable"
         titleKey="infopub.threadForm.threadTypeId">
         <eoms:dict key="dict-workbench-infopub" dictId="threadType" itemId="${threadList.threadTypeId}" beanId="id2nameXML" />
    </display:column>

    <display:column sortable="true" sortName="forumsId" headerClass="sortable"
         titleKey="infopub.threadForm.forumsId">
         <eoms:id2nameDB id="${threadList.forumsId}" beanId="forumsDao" />
    </display:column>


   

    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
</display:table>

<html:submit styleClass="button" property="method.delChoose" onclick="return ConfirmDel()">
	<fmt:message key="button.delete" />
</html:submit>
 

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>