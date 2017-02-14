<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.forceCheck.webapp.form.*;"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	var temp=cardNumberList[0].checked;
	if(temp==null){
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked='checked';
		}
	}else{
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked=!cardNumberList[i].checked;
		}
	}
}
   	function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].defaultValue;
			idResult+=myTempStr.toString()+";"
		}
	}
	if (idResult == "") {
		alert("请选择需要删除的记录");
		return false;
	} else {
		if(confirm("确定要删除所选记录吗？")){
			$("deleteIds").value=idResult.toString();
			//var myParam=idResult.toString();
			var formObj = document.getElementById("deleteAllForm");
 			formObj.submit();
		}
		else{
			return false;
		}
	}
}
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
 	function goadd(){
		window.open("${app}/forceCheck/forceCheckTask.do?method=add");
		}  
	function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}			     
</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<fmt:bundle basename="com/boco/eoms/forceCheck/config/applicationResources-forceCheck">
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content>
<html:form action="/forceCheckTask.do?method=xquery" styleId="pnrForceCheckTaskForm" method="post">
		<fieldset>
			<table id="sheet" class="formTable">
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.forcecheckRoute" />&nbsp;:
		</td>
		<td class="content">
		<html:text property="forcecheckRoute" styleId="forcecheckRoute"
						styleClass="text medium" />
		</td>
        <td class="label">
			<fmt:message key="forceCheckTask.forcecheckRouteStage" />&nbsp;:
		</td>
		<td class="content">
		<html:text property="forcecheckRouteStage" styleId="forcecheckRouteStage"
						styleClass="text medium" />
		</td>
	  </tr>
	  <tr>
        <td class="label">
			<fmt:message key="forceCheckTask.forcecheckPlace" />&nbsp;:
		</td>
		<td class="content">
		<html:text property="forcecheckPlace" styleId="forcecheckPlace"
						styleClass="text medium" />
		</td>        
		<td class="label">
			<fmt:message key="forceCheckTask.siteName" />&nbsp;:
		</td>
		<td class="content">
		<html:text property="siteName" styleId="siteName"
						styleClass="text medium" />
		</td>
	  </tr>	
				<tr>
					<td class="label">
					   <fmt:message key="forceCheckTask.taskName" />&nbsp;:
					</td>
					<td>
		               <html:text property="taskName" styleId="taskName"
						styleClass="text medium" />
					</td>
					
					<td class="label">
						外力盯防任务状态:
					</td>
					<td>
						<select name="status" >
						<option value="">请选择</option> 
						<option value="0">任务新增</option> 
						<option value="1">任务执行中</option>
						<option value="2">任务归档</option>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form></div> 

<display:table  name="pnrForceCheckTaskList" cellspacing="0" cellpadding="0"
      id="pnrForceCheckTaskList"  pagesize="${pageSize}" class="table pnrForceCheckTaskList" 
      export="false" 
      requestURI="${app}/forceCheck/forceCheckTask.do?method=search"
      sort="list" partialList="true" size="resultSize">
      <display:column property="taskName" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.taskName" />
	  <display:column property="forcecheckRoute" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.forcecheckRoute" />
	  <display:column property="forcecheckRouteStage" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.forcecheckRouteStage" />
	  <display:column property="forcecheckPlace" sortable="true"
			headerClass="sortable" titleKey="forceCheckTask.forcecheckPlace" />
      <display:column sortable="true" headerClass="sortable" title="任务状态">
			<c:if test="${pnrForceCheckTaskList.status eq '0'}">
				新建状态
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '1'}">
				任务执行中
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '2'}">
				任务归档
			</c:if>
			<c:if test="${pnrForceCheckTaskList.status eq '-1'}">
				计划取消或终止
			</c:if>
		</display:column>
	   <display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			 <c:if test="${pnrForceCheckTaskList.status=='0'}">
			<a id="${pnrForceCheckTaskList.id }"
				href="${app }/forceCheck/forceCheckTask.do?method=edit&id=${pnrForceCheckTaskList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
				</c:if>
				 <c:if test="${pnrForceCheckTaskList.status!='0'}">
				无
				 </c:if>
		</display:column>		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${pnrForceCheckTaskList.id }"
				href="${app }/forceCheck/forceCheckTask.do?method=detail&id=${pnrForceCheckTaskList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>						
 
  </display:table>
<!--		<input type="button" name="add" id="add" value="新增" onclick="goadd();"/> 
		<input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/>  -->
</fmt:bundle>
<form id="deleteAllForm" action="forceCheckTask.do?method=removeall" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>    
<%@ include file="/common/footer_eoms.jsp"%>