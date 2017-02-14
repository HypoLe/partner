<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
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
	<form action="armypolicecivilian.do?method=approveList" method="post" name="armyPoliceCivilian">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					项目名称:
				</td>
				<td>
					<input type="text" class="text" name="projectName" id="projectName1"/>
				</td>

				<td class="label">
					军警民共建单位名称:
				</td>
				<td>
					<input type="text" class="text" id="nameOfOrganization1" name="nameOfOrganization" />
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="left">
						<input type="submit" class="btn" value="确认查询" />
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<!-- Information hints area end-->
<logic:notEmpty name="armyPoliceCivilian" scope="request">
	<display:table name="armyPoliceCivilian" cellspacing="0" cellpadding="0"
		class="table armyPoliceCivilian" id="armyPoliceCivilian"
		pagesize="${pagesize}" export="true"
		requestURI="armypolicecivilian.do" sort="list" partialList="true"
		size="${size}">
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${armyPoliceCivilian.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
			<eoms:id2nameDB id="${armyPoliceCivilian.approvingPerson}"
				beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="填报时间">
			${armyPoliceCivilian.writeTime}
		</display:column>
	<%-- 	<display:column sortable="true" headerClass="sortable" title="所属地市">
			<eoms:id2nameDB id="${armyPoliceCivilian.belongTheLocal}"
				beanId="tawSystemAreaDao" />
		</display:column> --%>
		
		<display:column sortable="true" headerClass="sortable"
			title="军警民共建单位名称">
			${armyPoliceCivilian.nameOfOrganization}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="合作内容">
		     ${armyPoliceCivilian.cooperativeContent}
		</display:column>
		<display:column sortable="true" headerClass="sortable"
			title="负责光缆线路里程（km）">
			${armyPoliceCivilian.lengthOfOpticalCableRoutes}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="实际效果">
			${armyPoliceCivilian.realEffect}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			${armyPoliceCivilian.state}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="操作">
			<a id="${armyPoliceCivilian.id }"
				href="${app }/armypolicecivilian/armypolicecivilian.do?method=gotoApprovedDetail&id=${armyPoliceCivilian.id}&type=edit">
				<img src="${app }/images/icons/edit.gif"> </a>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${armyPoliceCivilian.id }"
				href="${app }/armypolicecivilian/armypolicecivilian.do?method=gotoApprovedDetail&id=${armyPoliceCivilian.id}&type=detail">
				<img src="${app }/images/icons/table.gif"> </a>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	
</logic:notEmpty>
<logic:empty name="armyPoliceCivilian" scope="request">
没有记录
</logic:empty>
<%@ include file="/common/footer_eoms.jsp"%>
