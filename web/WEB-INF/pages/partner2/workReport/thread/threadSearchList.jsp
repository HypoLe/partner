
<jsp:directive.page import="com.boco.eoms.workbench.infopub.util.InfopubConstants"/>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<priv:region url="<%=InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR %>" >
	<script type="text/javascript">
Ext.onReady(function(){
var treeAction='${app}/partner2/workReport/forums.do?method=getNodes4query';
	  var config4 = {
		btnId:'forumsName',
		dlgId:'dlg4',
		treeDataUrl:treeAction,
		treeRootId:'-1',
		treeRootText:'请选择',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'forumsName',
		saveChkFldId:'toForumsId'
	}
	var t4 = new xbox(config4);
});
</script>
	</priv:region>
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
		var flag=false;
		var frm=document.forms[0];
		var toForumsId=document.getElementById("toForumsId");
		var objs = document.getElementsByName("ids");
           	 for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			if(objs[i].checked){
      				flag=true;
      			}
			 }
			 if(flag){
				frm.action="${app}/partner2/workReport/thread.do?method=sort";
             	frm.submit();
             }else {
	            alert("未选择");
             	return false;
            }
	}
	
	function ConfirmDel(){
			var flag=false;
           if(confirm("确认删除？")){
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
	            alert("请选择需要删除的记录！");
             	return false;
             }
             
           }else{
             return false;
           }
    }

</script>


<% 
	List myTmpList = (List)request.getAttribute("threadList");
	if(myTmpList!=null&&myTmpList.size()>0){
%>	

<html:form action="/thread.do" method="post" styleId="workReportForm" >

<html:hidden property="forumsId"/>

<display:table name="threadList" cellspacing="0" cellpadding="0"
	sort="external" defaultsort="1"
    id="threadList" pagesize="${pageSize }" class="table threadList"
    export="false" requestURI="${app}/partner2/workReport/thread.do?method=search" partialList="true" size="resultSize"
    decorator="com.boco.partner2.workReport.displaytag.support.ThreadListDisplaytagDecorator" >
	
	<logic:notEmpty name="threadMgrPriv">
	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'/>" />
	</logic:notEmpty>
	<display:column property="title" sortable="true" sortName="title" headerClass="sortable"
         titleKey="infopub.threadForm.title" paramId="id" paramProperty="id" href="${app}/partner2/workReport/thread.do?method=detail&forumsId=${forumsId }"/>
         
    <display:column property="threadCount" sortable="true" sortName="threadCount" headerClass="sortable"
         titleKey="infopub.threadForm.num" href="${app}/partner2/workReport/threadHistory.do?method=search" paramId="threadId" paramProperty="id"/>

    <display:column property="createrName" sortable="true" sortName="createrName" headerClass="sortable"
         titleKey="infopub.threadForm.createrId" />

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
         <eoms:id2nameDB id="${threadList.forumsId}" beanId="forumsDao2" />
    </display:column>


    <display:setProperty name="paging.banner.item_name" value="thread"/>
    <display:setProperty name="paging.banner.items_name" value="threads"/>
    <display:setProperty name="export.rtf" value="false"/>
<display:setProperty name="export.pdf" value="false"/>
<display:setProperty name="export.xml" value="false"/>
<display:setProperty name="export.csv" value="false"/>
    
</display:table>

	<input type="button" class="button" onclick="location.href='<html:rewrite page='/thread.do?method=initSearch'/>'" value="查询" />
	<priv:region url="<%=InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR%>">
		<html:submit styleClass="button" property="method.delChoose" onclick="return ConfirmDel()">
			<fmt:message key="button.delete" />
		</html:submit>
		<input type="button" class="button" value="归类" onclick="sort(); " />
		
		<input type="text" id="forumsName" name="forumsName" class="text" readonly="readonly" value="请选择归类" />
		<html:hidden property="toForumsId" styleId="toForumsId" />
	</priv:region>

</html:form>
<%}else{%>
	无符合查询结果的信息
<%} %>
