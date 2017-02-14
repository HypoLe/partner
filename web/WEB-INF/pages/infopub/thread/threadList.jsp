<jsp:directive.page import="com.boco.eoms.workbench.infopub.util.InfopubConstants" />
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
 <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
<priv:region url="<%=InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR%>">
	<script type="text/javascript">
Ext.onReady(function(){
var treeAction='${app}/workbench/infopub/forums.do?method=getNodes4query';
	  var config4 = {
		btnId:'forumsName',
		dlgId:'dlg4',
		treeDataUrl:treeAction,
		treeRootId:'-1',
		treeRootText:'<bean:message key='forumsTree.title.choose'/>',
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
		var toForumsId=document.getElementById("toForumsId");
		if(""==toForumsId.value){
			alert('<bean:message key='thread.tip.sort'/>');
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
	            alert("<bean:message key='threadForm.tips.nochoose'/>");
             	return false;
            }
	}
	
	function ConfirmDel(){
			var flag=false;
           if(confirm("<bean:message key='common.tips.delete'/>")){
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
	            alert("<bean:message key='threadForm.tips.nochoose'/>");
             	return false;
             }
             
           }else{
             return false;
           }
    }

</script>
<script type="text/javascript">
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
<html:form action="/thread.do?" method="post" styleId="threadForm">
<div style="border:1px solid #98c0f4;padding:5px;width:98%;" class="x-layout-panel-hd">
工具栏： 
  <img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer"/>
  <span id="openQuery"  style="cursor:pointer" onclick="openQuery(this);">打开快速查询</span>
</div>

<div id="listQueryObject" style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" width="75%">
	
	<tr>	
		<td class="label" align="right">
			开始时间：
			<html:errors property="startDate" />
		</td>
		<td>
		<html:hidden property="forumsId" styleId="forumsId" styleClass="text medium" />
			<input id="startDate" name="startDate" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'lessThen',link:'endDate',vtext:'开始时间不能晚于结束时间'"/>
		</td>
		<td class="label" align="right">
			结束时间：
			<html:errors property="endDate" />
		</td>
		<td>
			<input id="endDate" name="endDate" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" alt="vtype:'moreThen',link:'startDate',vtext:'结束时间不能早于开始时间'"/>
		</td>
	</tr>
	<tr>	
	
		<td class="label" align="right">
		内容查询：
		</td>
		<td>
		<html:text property="content" styleId="content" styleClass="text medium" />
		
		</td>
<td class="label" align="right">
	紧急程度：
		</td>
		<td>
		
			<eoms:dict key="dict-workbench-infopub" dictId="threadType"
				selectId="threadTypeId" beanId="selectXML" 
				defaultId="${threadForm.threadTypeId }" />
		</td>
	</tr>

	<tr>
		<td colspan="4" align="center">
			<html:submit styleClass="button" property="method.search2"
				onclick="bCancel=false">
				<fmt:message key="button.query" />
			</html:submit>
		</td>
	</tr>
	</table>
</div>

<logic:notEmpty name="threadForm" property="forumsId">
	<bean:message key="threadForm.forumsId" /> 
<eoms:id2nameDB id="${threadForm.forumsId }" beanId="forumsDao" />
</logic:notEmpty>




	<html:hidden property="forumsId" />

	<display:table name="threadList" cellspacing="0" cellpadding="0" id="threadList" pagesize="${pageSize }" offset="${offset }" class="table threadList" requestURI="${app}/workbench/infopub/thread.do?method=list&forumsId=${forumsId }"
	 sort="external" defaultsort="1"
		partialList="true" size="resultSize" decorator="com.boco.eoms.workbench.infopub.displaytag.support.ThreadListDisplaytagDecorator">

		<display:setProperty name="export.rtf" value="false"></display:setProperty>

		<logic:notEmpty name="threadMgrPriv">
			<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" />
			<display:setProperty name="css.table" value="0,"/>
		</logic:notEmpty>
		
		<display:column property="title" sortable="true" sortName="title" headerClass="sortable" titleKey="infopub.threadForm.title" paramId="id" paramProperty="id" href="${app}/workbench/infopub/thread.do?method=detail" />
		
		<display:column sortable="true" sortName="status" headerClass="sortable" titleKey="infopub.threadForm.status" href="${app}/workbench/infopub/threadAuditHistory.do?method=device" paramId="threadId" paramProperty="id" >
			<eoms:dict key="dict-workbench-infopub" dictId="auditStatus" itemId="${threadList.status}" beanId="id2nameXML" />
		</display:column>
		<display:column property="threadCount" sortable="true" sortName="threadCount" headerClass="sortable" titleKey="infopub.threadForm.num" />
		<display:column property="createrName" sortable="true" sortName="createrName" headerClass="sortable" titleKey="infopub.threadForm.createrId" />

		<display:column property="createTime" sortable="true" sortName="createTime" headerClass="sortable" titleKey="infopub.threadForm.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column property="editTime" sortable="true" sortName="editTime" headerClass="sortable" titleKey="infopub.threadForm.editTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />

		<display:column sortable="true" sortName="threadTypeId" headerClass="sortable" titleKey="infopub.threadForm.threadTypeId">
			<eoms:dict key="dict-workbench-infopub" dictId="threadType" itemId="${threadList.threadTypeId}" beanId="id2nameXML" />
		</display:column>

		<display:column sortable="true" sortName="forumsId" headerClass="sortable" titleKey="infopub.threadForm.forumsId">
			<eoms:id2nameDB id="${threadList.forumsId}" beanId="forumsDao" />
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="thread" />
		<display:setProperty name="paging.banner.items_name" value="threads" />
	</display:table>

	<logic:notEmpty name="allowAdd">
		<input type="button" class="button" onclick="location.href='<html:rewrite page='/thread.do?method=edit&forumsId=${forumsId}'/>'" value="<fmt:message key="button.add"/>" />
	</logic:notEmpty>
	<input type="button" class="button" onclick="location.href='<html:rewrite page='/thread.do?method=edit'/>'" value="新增" />
	<input type="button" class="button" onclick="location.href='<html:rewrite page='/thread.do?method=initSearch'/>'" value="查询" />
	<input type="button" class="button" onclick="location.href='<html:rewrite page='/threadAuditHistory.do?method=waitList'/>'" value="审核" />
	
	<priv:region url="<%=InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR%>">
		<html:submit styleClass="button" property="method.delChoose" onclick="return ConfirmDel()">
			<fmt:message key="button.delete" />
		</html:submit>

		<html:submit styleClass="button" property="method.sort" onclick="return sort()">
			<bean:message key="threadList.sort" />
		</html:submit>

		<input type="text" id="forumsName" name="forumsName" class="text" readonly="readonly" value="<bean:message key='forumsTree.title.choose'/>" />
		<html:hidden property="toForumsId" styleId="toForumsId" />
	</priv:region>

</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
