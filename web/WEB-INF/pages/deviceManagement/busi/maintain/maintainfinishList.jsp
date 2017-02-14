<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
//request.setAttribute("province", "10");
%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"	
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<style type="text/css">
.small {
	width: 10px;
}

</style>
<script type="text/javascript">
var myjs=jQuery.noConflict();
Ext.onReady(function(){
  Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'advertisementCity',saveChkFldId:'advertisementArea',returnJSON:false
	});
})
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
<html:form action="maintain.do?method=list" method="post">
		<table id="sheet" class="formTable">
			<tr>		
				<td class="label">
					割接名称:
				</td>
				<td>
					<input type="text" name="maintainNameStringLike" value=""/>
				</td>
				<td class="label">影响系统：</td>
			<td>	<input type="text" name="influenceSystemStringLike" value=""/></td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.list"
			styleId="method.iist" value="提交查询结果"></html:submit>
			
	</html:form>
	
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>
 <!-- Information hints area end-->
 <logic:present
	name="maintainList" scope="request">
	<display:table name="maintainList" cellspacing="0" cellpadding="0"
		id="maintainList" pagesize="${pagesize}"
		class="table advertisementList" export="true"
		requestURI="maintainfinish.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="割接名称">
			${maintainList.maintainName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="光缆线段">
		${maintainList.fiberSection}
		<!--<eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticleList.publicizeArticlePlace}"/>--->
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="影响系统">
			${maintainList.influenceSystem}
		</display:column>
		<%--<display:column sortable="true" headerClass="sortable"
			title="预计用时">
			${maintainList.expectationTimeConsuming}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="割接原因">
			${maintainList.maintainCause}
		</display:column>--%>
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${maintainList.approvalUser}"/>
		
		</display:column>
		<%--<display:column sortable="true" headerClass="sortable" title="备注">
			${maintainList.remark}
		</display:column>--%>
		<display:column sortable="true" headerClass="sortable" title="状态">
			<c:if test="${maintainList.isReport==1}">
				已经完成
			</c:if>
			<c:if test="${maintainList.isReport!=1}">
				未完成
			</c:if>
		</display:column>
		<c:if test="publicizeArticleList.approvalType"></c:if>
		<display:column sortable="false" headerClass="sortable" title="验收"
			 media="html">
			 <c:set value="${maintainList.isCheck}" var="check"/>
			 <c:choose>
			 <c:when test="${check == 1}">
			 已经验收
			 </c:when>
			 <c:when test="${check != 1}">
			 <a href="${app }/deviceManagement/line/maintainfinish.do?method=goToAddMaintainfinish&id=${maintainList.id }">验收<img src="${app }/images/icons/edit.gif"><a>
			 </c:when>
			 </c:choose>
			<%-- ${maintainList.maintainFinish}--
			<c:if test="${maintainList.approvalType==3}">
			<a href="${app }/deviceManagement/line/maintain.do?method=goToEdit&id=${maintainList.id }">修改<img src="${app }/images/icons/edit.gif"><a>
			</c:if>
			<c:if test="${maintainList.approvalType==2}">
			<a href="${app }/deviceManagement/line/maintain.do?method=goToAddMaintainReport&id=${maintainList.id }">汇报<img src="${app }/images/icons/edit.gif"><a>
			</c:if>
		--%></display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${maintainList.id }"
				href="${app }/deviceManagement/line/maintainfinish.do?method=goToDetailMaintainfinish&id=${maintainList.id}"
				target="blank" shape="rect"><img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>

		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present></div>
</div>


<%@ include file="/common/footer_eoms.jsp"%>
