<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${app}/baidu/jquery-easyui-1.2.6/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="${app}/baidu/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${app}/baidu/js/baiduGisHelp.js"></script>
<script type="text/javascript" src="${app}/baidu/js/setBaidu.js"></script>
<script type="text/javascript" src="${app}/baidu/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
<%--<script type="text/javascript" src="${app}/baidu/js/baiduApiOverwrite.js"></script>--%>
<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.3&services=true"></script>



<style type="text/css">
	.tabTitle {
		text-decoration: none;
		
	}
</style>


<body class="easyui-layout" onload="loadMap()">
	<div id="east" region="east" split="true" title="信息栏" style="width:500px;padding:5px;">
		<div class="easyui-tabs" fit="true" border="false"   style="height:100%;weight:100%">
			<div id="gis" title="<a id='resListclick' class='tabTitle' href='${app}/partner/gis/baiduGis.do?method=gisInformation' target='res-List' onClick='addblock();' >gis信息</a>"  style="weight:100%;overflow:hidden;"> 
				<iframe id="res-List" name="res-List" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="jz" title="<a id='siteList' class='tabTitle' href='${app}/partner/gis/baiduGis.do?method=search&type=site' target='site-page'  onclick='addblock();'>资源信息</a>"  style="weight:100%;overflow:hidden;"> 
				<iframe id="site-page" name="site-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<%--<div id="jk" title="<a id='customList' class='tabTitle' href='${app}/partner/gis/baiduGis.do?method=search&type=custom' target='custom-page' onclick='addblock();'  >集客列表</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="custom-page" name="custom-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			--%><div id="tj" title="<a id='inspectList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindList' target='inspect-page' onclick='addblock();' >任务完成情况</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="inspect-page" name="inspect-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="rw" title="<a id='inspectTaskList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskFindList' target='inspectTask-page' onclick='addblock();'>任务查询</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="inspectTask-page" name="inspectTask-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<%--<div id="xj" title="<a id='locusList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisLocusFindList' target='locusList-page' onclick='addblock();' >历史巡检轨迹</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="locusList-page" name="locusList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>--%>
			<div id="xj" title="<a id='locusList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=getAllInspectMainPlan' target='locusList-page' onclick='addblock();' >历史巡检轨迹</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="locusList-page" name="locusList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			</div>
	</div>
	<div id="center" region="center" title="地图">
		    <div id="container" style="width:100%;height:100%;" ></div>
	</div>
	


<script  type="text/javascript">
var jq=jQuery.noConflict();
	//getbandwidth();
	//handleBtnClick();
	setTimeout(function(){
		document.getElementById("resListclick").click();
		},500);

</script>	
</body>