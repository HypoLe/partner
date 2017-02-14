<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%
request.setAttribute("province", "10");
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
		showChkFldId:'publicizeArticleArea',saveChkFldId:'publicizeArticlePlace',returnJSON:false
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
				<td class="label">审核人：</td>
			<td>
					<input type="text" name="approvalUserStringLike" value=""/>
			
			</td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="提交查询结果"></html:submit>
			
	</html:form>
	
	</div>

</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>




 <!-- Information hints area end-->


 <logic:present
	name="maintainApprovalList" scope="request">
	<display:table name="maintainApprovalList" cellspacing="0" cellpadding="0"
		id="maintainApprovalList" pagesize="${pagesize}"
		class="table advertisementApprovalList" export="true"
		requestURI="maintainApproval.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="割接名称">
			${maintainApprovalList.maintainName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="光缆线段">
		${maintainApprovalList.fiberSection}
		<!-- 
			<eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticleApprovalList.publicizeArticlePlace}"/>
		 --></display:column>
		<display:column sortable="true" headerClass="sortable"
			title="影响系统">
			${maintainApprovalList.influenceSystem}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="预计用时">
			${maintainApprovalList.expectationTimeConsuming}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="割接原因">
			${maintainApprovalList.maintainCause}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
			
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${maintainApprovalList.creatUser}"/>
		
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="审批"
			paramProperty="id" url="/deviceManagement/line/maintain.do?method=goToApproval"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${maintainApprovalList.id }"
				href="${app }/deviceManagement/line/maintain.do?method=goToDetail&id=${maintainApprovalList.id}"
				target="blank" shape="rect"> <img
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
