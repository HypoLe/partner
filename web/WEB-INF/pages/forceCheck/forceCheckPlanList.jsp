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
			var status = myTempStr.toString().substring(32,33);
			if (status != '0'){
			alert("已提交计划不允许删除,请重新选择");
			return false;
			}
			idResult+=myTempStr.toString().substring(0,32)+";"
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
		window.open("${app}/forceCheck/forceCheckPlan.do?method=add");
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
<html:form action="/forceCheckPlan.do?method=xquery" styleId="pnrForceCheckPlanForm" method="post">
		<fieldset>
			<table id="sheet" class="formTable">
	    <tr>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckRoute" />&nbsp;:
		</td>
		<td class="content">
		<html:text property="forcecheckRoute" styleId="forcecheckRoute"
						styleClass="text medium" />
		</td>
        <td class="label">
			<fmt:message key="forceCheckPlan.forcecheckRouteStage" />&nbsp;:
		</td>
		<td class="content">
		<html:text property="forcecheckRouteStage" styleId="forcecheckRouteStage"
						styleClass="text medium" />
		</td>
	  </tr>		
				<tr>
					<td class="label">
					   <fmt:message key="forceCheckPlan.forcecheckPlace" />&nbsp;:
					</td>
					<td>
						<input type="text" class="text" name="placeStringLike"/>
					</td>
					
					<td class="label">
						外力盯防计划状态:
					</td>
					<td>
						<select name="status" >
						<option value="">请选择</option>
						<option value="0">计划待提交</option>
						<option value="1">计划执行中</option>
						<option value="2">计划归档</option>
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


  <c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
  </c:set>
<display:table  name="pnrForceCheckPlanList" cellspacing="0" cellpadding="0"
      id="pnrForceCheckPlanList"  pagesize="${pageSize}" class="table pnrForceCheckPlanList" 
      export="false" 
      requestURI="${app}/forceCheck/forceCheckPlan.do?method=search"
      sort="list" partialList="true" size="resultSize">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${pnrForceCheckPlanList.id}${pnrForceCheckPlanList.planStauts}" />
		</display:column>
      <display:column property="planName" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.planName" />
	  <display:column property="forcecheckRoute" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.forcecheckRoute" />
	  <display:column property="forcecheckRouteStage" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.forcecheckRouteStage" />
	  <display:column property="forcecheckPlace" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.forcecheckPlace" />
	  <display:column property="forcecheckGpsFacility" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.forcecheckGpsFacility" />
	  <display:column property="startDate" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.startDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
	  <display:column property="endDate" sortable="true"
			headerClass="sortable" titleKey="forceCheckPlan.endDate" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
      <display:column sortable="true" headerClass="sortable" title="计划状态">
			<c:if test="${pnrForceCheckPlanList.planStauts eq '0'}">
				新建状态
			</c:if>
			<c:if test="${pnrForceCheckPlanList.planStauts eq '1'}">
				计划执行中
			</c:if>
			<c:if test="${pnrForceCheckPlanList.planStauts eq '2'}">
				计划归档
			</c:if>
			<c:if test="${pnrForceCheckPlanList.planStauts eq '-1'}">
				计划取消或终止
			</c:if>
		</display:column>
	   <display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			 <c:if test="${pnrForceCheckPlanList.planStauts=='0'}">
			<a id="${pnrForceCheckPlanList.id }"
				href="${app }/forceCheck/forceCheckPlan.do?method=edit&id=${pnrForceCheckPlanList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
				</c:if>
				 <c:if test="${pnrForceCheckPlanList.planStauts!='0'}">
				无
				 </c:if>
		</display:column>		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultSheetList.id }"
				href="${app }/forceCheck/forceCheckPlan.do?method=detail&id=${pnrForceCheckPlanList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>			
		 
</display:table>


</fmt:bundle>
<form id="deleteAllForm" action="forceCheckPlan.do?method=removeall" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form> 

		<input type="button" name="add" id="add" value="新增" onclick="goadd();"/> 
	    <input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/> 
<%@ include file="/common/footer_eoms.jsp"%>