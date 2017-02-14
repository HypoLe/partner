<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
 <script type="text/javascript" src="${app }/nop3/scripts/ext/ext1.1.1/AppFrameTree.js"></script>
  <script type="text/javascript">
  var config = {
    /**************
 	* Tree Configs
 	**************/
	treeGetNodeUrl:"<html:rewrite page='/forums.do?method=getNodes4Thread'/>",
    treeRootId:'-1',
	treeRootText:"所有报告专题",
	pageFrameId:'formPanel-page',
	onClick:{url:"<html:rewrite page='/thread.do?method=list&forumsId='/>",text:"信息列表"},
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTree.init, AppFrameTree);
  

  </script>
  <style type="text/css">
  	body{background-image:none;}
  </style>
<div id="headerPanel" class="x-layout-inactive-content">
	<!-- <img src="${app}/styles/default/images/header-user.gif">  -->
	头条
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<display:table name="workReportTemplates" cellspacing="0" cellpadding="0"
		id="workReportTemplates" pagesize="${pageSize }"
		class="table workReportTemplates" export="false"
		requestURI="thread.do?method=threadTree" sort="list"
		partialList="true" size="${resultSize}">

		<display:column sortable="true" headerClass="sortable" title="模板名称">
		<a href="${app}/partner2/workReport/template.do?method=showTemplateDeail&id=${workReportTemplates.id}">
			${workReportTemplates.title}
		</a>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="所属板块">
			${workReportTemplates.forumsId}
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="创建人">
			${workReportTemplates.createrName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="项目经理">
			${workReportTemplates.workReportTemplateProManager}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="创建时间">
			${workReportTemplates.createTime}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="有效期">
			${workReportTemplates.validityDate}
		</display:column>
		
	</display:table>
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
