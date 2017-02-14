<jsp:directive.page import="com.boco.partner2.workReport.util.InfopubConstants" />
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<html:form action="/thread.do?method=search2" method="post" styleId="workReportForm">

<logic:notEmpty name="workReportForm" property="forumsId">
<eoms:id2nameDB id="${workReportForm.forumsId }" beanId="forumsDao" />
</logic:notEmpty>
	<html:hidden property="forumsId" />
	<display:table name="threadList" cellspacing="0" cellpadding="0" id="threadList" pagesize="${pageSize }" offset="${offset }" class="table threadList" export="false" requestURI="${app}/partner2/workReport/thread.do?method=list&forumsId=${forumsId }"
	 sort="external" defaultsort="1"
		partialList="true" size="resultSize" decorator="com.boco.partner2.workReport.displaytag.support.ThreadListDisplaytagDecorator">

		<logic:notEmpty name="threadMgrPriv">
			<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" />
			<display:setProperty name="css.table" value="0,"/>
		</logic:notEmpty>
		<display:column property="title" sortable="true" sortName="title" headerClass="sortable" title="标题" paramId="id" paramProperty="id" href="${app}/partner2/workReport/thread.do?method=detail&forumsId=${param.forumsId }" />
		
		<display:column property="threadCount" sortable="true" sortName="threadCount" headerClass="sortable" title="阅读次数" />
		<display:column property="createrName" sortable="true" sortName="createrName" headerClass="sortable" title="上报人" />

		<display:column property="createTime" sortable="true" sortName="createTime" headerClass="sortable" title="上报时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="editTime" sortable="true" sortName="editTime" headerClass="sortable" title="最后修改时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		
		<display:column sortable="true" sortName="forumsId" headerClass="sortable" title="专题">
			<eoms:id2nameDB id="${threadList.forumsId}" beanId="forumsDao2" />
		</display:column>
	</display:table>

	<logic:notEmpty name="allowAdd">
		<input type="button" class="button" onclick="location.href='<html:rewrite page='/thread.do?method=edit&forumsId=${forumsId}'/>'" value="<fmt:message key="button.add"/>" />
	</logic:notEmpty>
	
		
		<input type="button" class="button" value="发布报告" onclick="location.href='<html:rewrite page='/thread.do?method=edit&forumsId=${param.forumsId }'/>'" />
	

	
	<priv:region url="<%=InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR%>">
		<html:submit styleClass="button" property="method.delChoose" onclick="return ConfirmDel()">
			<fmt:message key="button.delete" />
		</html:submit>
		<html:hidden property="toForumsId" styleId="toForumsId" />
	</priv:region>
	

</html:form>

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

	function ConfirmDel(){
			var flag=false;
                 var frm=document.forms[0];
           if(confirm("确定删除吗?")){
           	 var objs = document.getElementsByName("ids");
           	 for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			if(objs[i].checked){
      				flag=true;
                     
      			}
			 }
			 if(flag){
			 	frm.action="${app}/partner2/workReport/thread.do?method=delChoose";
             	return true;
             }else {
	            alert("未选择");
             	return false;
             }
             
           }else{
             return false;
           }
    }
    
function openQuery(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开快速查询";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭快速查询";
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>