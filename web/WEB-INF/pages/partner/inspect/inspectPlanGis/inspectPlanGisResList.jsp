<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<%
String jsonList=request.getAttribute("jsonString").toString();
%>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
		var jsonList='<%=jsonList%>';
		jsonList = eval( '(' + jsonList + ')' );
		var pathUrl="${app}";
		var type="siteList";
		var pInterval = setInterval(function(){
				if(parent.map != null) {
					parent.addblock();
					parent.ifOnload(jsonList,pathUrl,type);
					window.clearInterval(pInterval);
					parent.removeblock();
				}
			},1000);
});
function goToStartCheck(id,exe){
window.location="${app}/partner/inspect/inspectPlanExecute.do?method=goToInspectPlanMainItemList&planResId="+id+"&detail="+exe;
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

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>
<%--
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
--%>
<%---
	
<table id="stylesheet" class="formTable">

	<content tag="heading">
	</content>
		<tr>
			<td class="label">巡检计划名称</td>
			<td class="content">
			${planMain.planName}
			</td>
			<td class="label">巡检专业</td>
			<td><eoms:id2nameDB id="${planMain.specialty}" beanId="ItawSystemDictTypeDao" /></td>
		</tr>

		<tr>
			<td class="label">代维公司</td>
			<td>
			<eoms:id2nameDB id="${planMain.partnerDeptId}" beanId="partnerDeptDao"/>
			
			</td>
			<td class="label">关联资源数</td>
			<td class="content">${planMain.resNum }</td>

		</tr>
		<tr>
			<td class="label">巡检日期</td>
			<td class="content">
			<c:if test="${!empty planMain.year}" var="result">
				${planMain.year}年${planMain.month}月
			</c:if>
			</td>
			<td class="label">制定人</td>
			<td class="content"><eoms:id2nameDB id="${planMain.creator }" beanId="tawSystemUserDao" /></td>
		</tr>
		<tr>
			<td class="label">制定日期</td>
			<td class="content">
				<fmt:formatDate value="${planMain.createTime }" type="date"/>
			</td>
			<td class=""></td>
			<td class="content"></td>
		</tr>
		<tr>
			<td class="label">描述</td>
			<td class="content" colspan="3">${planMain.content}</td>
		</tr>
	</table>

<div id="history-info" class="tabContent">
 --%>	
<br/>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:online;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	<!-- 
	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content>
	 -->
	 
	<form action="${app }/partner/inspect/inspectPlanGisAction.do?method=getInspectPlanMainDetail" method="post">
	   <input type="hidden" value="${planMain.id}" name="id">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">资源名称</td>
				<td><input class="text" type="text" name="resName"/></td>
				<td class="label">巡检小组</td>
				<td class="content">
					<input type="text"  class="text"  name="executeObject" id="executeObject"  
					value="" 
					alt="allowBlank:true" readonly="readonly"/>
				 <input name="chgExecuteObject" id="partnerDeptId"  value="" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf&showPartnerLevelType=4"
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="executeObject" 
						textField="executeObject" checktype="dept" single="true" />
				</td>
			</tr>
			<tr>	
				<td class="label">周期</td>	
				<td>
				<select name="inspectCycle" class="select">
					<option value="">请选择</option>
					<c:forEach items="${cycleMap}" var="cycleMap" > 
						<option value="${cycleMap.key}">${cycleMap.value}</option>
					</c:forEach> 
				</select>
				</td>
				<td class="label">完成情况</td>
				<td>
					<select name="inspectState" class="select">
						<option value="">全部</option>
						<option value="1">已完成</option>
						<option value="0">未完成</option>
					</select>
				</td>
			</tr>
	</table>
		<input type="submit" class="btn" value="查询"/>
	</form>
	</div>
 
<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
	<center>
		<display:column title="资源名称" >
			${list.resName}
		</display:column>
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		
		<display:column  title="代维小组" >
		<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		
		<display:column  title="巡检开始时间" >
		<fmt:formatDate value="${list.planStartTime}" type="date" dateStyle="medium"/>
		</display:column>
		
		<display:column title="巡检结束时间" >
		<fmt:formatDate value="${list.planEndTime}" type="date"/>
		
		</display:column>
		
			
		<display:column title="完成情况">
			<c:choose>
				<c:when test="${list.inspectState eq 1}">
					已完成
				</c:when>
				<c:otherwise>
					未完成
				</c:otherwise>
			</c:choose>
		</display:column>	
			
		
		
	</display:table>
	<%--
  </div>
--%>	
  
  <div id="comments-info" class="tabContent">	 
  </div>

</form>
<input type="button" class="btn" value="返回" 
						                    onclick="javascript:history.back();" /> 
<%@ include file="/common/footer_eoms.jsp"%>