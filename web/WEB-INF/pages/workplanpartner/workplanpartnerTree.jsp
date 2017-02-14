
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
 
<script type="text/javascript" src="${app}/scripts/layout/AppFrameTree.js"></script>
<script type="text/javascript">
  var config = {
	/**************
	 * Tree Configs
	 **************/
	treeGetNodeUrl:"${app}/workplanpartner/workplanpartners.do?method=getNodes",
	treeRootId:'104',
	treeRootText:'代维作业统计',
	pageFrameId:'formPanel-page',
	onClick:{url:"${app}/workplanpartner/workplanpartners.do?method=deptStat",text:""},
	ctxMenu:[
		],//end of ctxMenu 
	/************************
 	* Custom onLoad Functions
 	*************************/
	onLoadFunctions:function(){
	}
  }; // end of config
  Ext.onReady(AppFrameTree.init, AppFrameTree);
</script>
<div id="headerPanel" class="x-layout-inactive-content">
	<h1>代维作业计划</h1>
</div>
<div id="helpPanel" style="padding:10px;" class="x-layout-inactive-content">
	<dl>
		<B>代维作业统计</B><br/><br/>
			&nbsp&nbsp 代维作业统计提供对月度作业计划部门完成率、部门下各执行人完成率、及执行人完成率详细作业计划信息的查看功能。
	</dl>
</div>
<div id="treePanel" class="x-layout-inactive-content">
	<div id="treePanel-tb" class="tb"></div>
	<div id="treePanel-body"></div>
</div>
<div id="formPanel" class="x-layout-inactive-content">
	<iframe id="formPanel-page" name="formPanel-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
</div>


<%@ include file="/common/footer_eoms.jsp"%>