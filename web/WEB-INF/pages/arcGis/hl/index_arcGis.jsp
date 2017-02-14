<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${app}/arcGis/jquery-easyui-1.2.6/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="${app}/arcGis/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${app}/arcGis/js/arcGisHelp.js"></script>
<script type="text/javascript" src="${app}/arcGis/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>

<body class="easyui-layout" onload="fff();">
	<%--<div id="south" region="south"  style="height:200px;padding:3px;" >
	</div>--%>
	<div id="east" region="east" split="true" title="信息栏" style="width:500px;height:100%;padding:3px;">
		<div class="easyui-tabs" fit="true" border="false"   style="height:100%;weight:100%">
			<div id="gis" title="<a id='resListclick' class='tabTitle' href='${app}/partner/arcGis/arcGis.do?method=gisInformation' target='res-List' onClick='addblock();' >GIS信息</a>"  style="weight:100%;overflow:hidden;"> 
				<iframe id="res-List" name="res-List" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="jz" title="<a id='siteList' class='tabTitle' href='${app}/partner/arcGis/arcGis.do?method=search&type=site' target='site-page'  onclick='addblock();'>资源信息</a>"  style="weight:100%;overflow:hidden;"> 
				<iframe id="site-page" name="site-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="xl" title="<a id='transLineList' class='tabTitle' href='${app}/partner/arcGis/arcGis.do?method=gotoTransLineList' target='transLineList-page'  onclick='addblock();' >传输线路信息</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="transLineList-page" name="transLineList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="tj" title="<a id='inspectList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindListForArcGis' target='inspect-page' onclick='addblock();' >巡检任务完成情况</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="inspect-page" name="inspect-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="rw" title="<a id='inspectTaskList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskFindListForArcGis' target='inspectTask-page' onclick='addblock();'>巡检任务查询</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="inspectTask-page" name="inspectTask-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="xj" title="<a id='locusList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=getAllInspectMainPlanForArcGis' target='locusList-page' onclick='addblock();' >历史巡检轨迹</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="locusList-page" name="locusList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="cl" title="<a id='carList' class='tabTitle' href='${app}/partner/arcGis/arcGis.do?method=carList' target='carList-page' onclick='addblock();' >车辆列表</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="carList-page" name="carList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<div id="ry" title="<a id='userList' class='tabTitle' href='${app}/partner/arcGis/arcGis.do?method=userList' target='userList-page' onclick='addblock();' >人员列表</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="userList-page" name="userList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			<%--<div id="wx" title="<a id='errorList' class='tabTitle' href='${app}/partner/inspect/inspectPlanGisAction.do?method=findInspectPlanErrorRes' target='errorList-page' onclick='addblock();' >无效定位巡检任务</a>"  style="weight:100%;overflow:hidden;"> 
			<iframe id="errorList-page" name="errorList-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			--%>
			
			
			
			
			</div>
	</div>
	<div id="center" region="center" title="地图">
		    <iframe id="arcGis-page" name="arcGis-page" frameborder="no" style="width:100%;height:100%" src="${app}/partner/arcGis/arcGis.do?method=goToArcGisMap"></iframe>
	</div>
<%--	 <div id="w" class="easyui-window" title="Basic Window" data-options="iconCls:'icon-save'" style="width:500px;height:200px;padding:10px;">  
        The window content.  
    </div>  
<div style="margin:10px 0;">  
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('open')">Open</a>  
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="$('#w').window('close')">Close</a>  
    </div>--%>

<script  type="text/javascript">
var jq=jQuery.noConflict();
var southHeight;
var eastHeight;	
function fff(){
/**
 *huhao
 * set the map div's height
 **/
	//var divHeight=document.getElementById("center").style.height;
	//var divWidth=document.getElementById("center").style.width;
	//document.getElementById("arcGis-page").style.height=divHeight;
	//document.getElementById("arcGis-page").style.width=divWidth;
	//southHeight=document.getElementById("south").style.height;
	eastHeight=document.getElementById("east").style.height;
	setTimeout(function(){
	     document.getElementById("resListclick").click();
		},500);
		
		//setTimeout(function(){
	   // addDiv();
		//},5000);
}
	/********
	**huhao
	**block the window 
	*********/
	function addblock(){
			jq.blockUI({
				css: { 
		        border: 'none', 
		        padding: '15px', 
		        backgroundColor: '#000', 
		        '-webkit-border-radius': '10px', 
		        '-moz-border-radius': '10px', 
		        opacity: .5, 
		        color: '#fff',
		        display:'absolute'
		    } }); 
	}


	/********
	**huhao
	**remove the block 
	*********/
	function removeblock(){
		jq.unblockUI(500);
		jq.growlUI('','加载成功！',500);
	}
	

	function addDiv(){

	     document.getElementById("south").style.height="0px";
	    var parentHeight= parent.document.getElementById("ext-gen22").style.height;
	    document.getElementById("east").style.height=parentHeight;
	     document.getElementById("center").style.height=parentHeight;
	    // jq(".panel")[0].innerHTML=""
	    jq(".panel")[0].style.top=parentHeight;
	     jq(".easyui-tabs")[0].style.height=parentHeight;
	     jq(".tabs-panels")[0].style.height=parentHeight;
	     jq("#gis")[0].style.height=parentHeight;
	     
	     // jq(".panel")[0].innerHTML="<div class='panel-body panel-body-noheader layout-body' title='' id='south' region='south' style='padding: 1px; width: 1250px;'></div>";
	     // jq("#south")[0].style.height=southHeight;
	    // jq(".easyui-tabs")[0].style.height=eastHeight;
	      //jq(".tabs-panels")[0].style.height=eastHeight;
	     // jq("#gis")[0].style.height=eastHeight;
	      
	    //  easyui-tabs tabs-container
	       var aaaa="";
	}
</script>	
</body>