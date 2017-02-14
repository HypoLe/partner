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
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';

   
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'advertisementCity',saveChkFldId:'advertisementArea',returnJSON:false
	});
			
});
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
	<html:form action="advertisement.do?method=ApprovalList" method="post">
		<table id="sheet" class="formTable">
			<tr>		
				<td class="label">
					项目名称:
				</td>
				<td>
					<html:text property="projectName" styleClass="text"/>
					<input type="hidden" name="projectNameStringLike" value=""/>
				</td>
				<td class="label">墙体广告地点：</td>
			<%-- <input class="text" type="text" name="advertisementCityStringEqual"
				id="advertisementCity" readonly="true" value=""
				alt="allowBlank:false" /> 
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="advertisementArea" id="advertisementArea" />--%>
								<td>
					<html:text property="advertisementArea" styleClass="text"/>
					<input type="hidden" name="advertisementAreaStringLike" value=""/>
				</td>
			</tr>
			
				<tr >
				<td class="label" colspan="4">广告完成开始时间：</td>
				</tr>
				<tr >
				<td class="label">从</td><td >
					<input type="text" class="text" name="finishTimeA"
						onclick="popUpCalendar(this, this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'finishTimeEnd',vtext:'开始时间不能晚于结束时间'"
						id="finishTimeStart" value="${finishTimeBegin}"/>
						<input type="hidden" name="finishTimeA" value="">
				</td>
				<td class="label">到</td>
				<td >
					<input type="text" class="text" name="finishTimeB"
						onclick= "popUpCalendar(this, this,null,null,null,true,-1);"
                        alt="vtype:'moreThen',link:'finishTimeStart',vtext:'结束时间不能早于开始时间'"
						id="finishTimeEnd" value="${finishTimeEnd}"/>
					<input type="hidden" name="finishTimeB" value=""/>
				</td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="提交查询结果"></html:submit>
			
	</html:form>
	
	</div>

</hr>
 <!-- Information hints area end-->


 <logic:present
	name="advertisementApprovalList" scope="request">
	<display:table name="advertisementApprovalList" cellspacing="0" cellpadding="0"
		id="advertisementApprovalList" pagesize="${pagesize}"
		class="table advertisementApprovalList" export="true"
		requestURI="advertisement.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${advertisementApprovalList.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="墙体广告地点">
		${advertisementApprovalList.advertisementArea}
			<%-- <eoms:id2nameDB beanId="tawSystemAreaDao" id="${advertisementApprovalList.advertisementArea}"/>--%>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="墙体广告内容">
			${advertisementApprovalList.advertisementContent}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="墙体广告数量（份）">
			${advertisementApprovalList.advertisementAmount}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="完成时间">
			${advertisementApprovalList.finishTime}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="审核人">
			 <eoms:id2nameDB beanId="tawSystemUserDao" id="${advertisementApprovalList.approvalUser}"/>
		</display:column>
		

		<display:column sortable="false" headerClass="sortable" title="操作"
			paramProperty="id" url="/deviceManagement/advertisement/advertisement.do?method=goToApproval"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>

		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${advertisementApprovalList.id }"
				href="${app }/deviceManagement/advertisement/advertisement.do?method=goToDetail&id=${advertisementApprovalList.id}"
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
