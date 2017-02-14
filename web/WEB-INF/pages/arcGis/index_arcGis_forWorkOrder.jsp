<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${app}/arcGis/jquery-easyui-1.2.6/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="${app}/arcGis/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${app}/arcGis/js/arcGisHelp.js"></script>
<script type="text/javascript" src="${app}/arcGis/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>

<body class="easyui-layout" onload="fff();">
	<div id="east" region="east" split="true" title="信息栏" style="width:500px;height:100%;padding:3px;">
		<div class="easyui-tabs" fit="true" border="false"   style="height:100%;weight:100%">
		
			<div id="order" title="<a id='orderList' class='tabTitle' href='${app}/partner/arcGis/arcGis.do?method=gisOrder&orderId=${orderId}' target='site-page'  onclick='addblock();'>GIS工单</a>"  style="weight:100%;overflow:hidden;"> 
				<iframe id="site-page" name="site-page" frameborder="no" style="width:100%;height:100%" src=""></iframe>
			</div>
			
		</div>
	</div>
	<div id="center" region="center" title="地图">
		    <iframe id="arcGis-page" name="arcGis-page" frameborder="no" style="width:100%;height:100%" src="${app}/partner/arcGis/arcGis.do?method=goToArcGisMap&type=1"></iframe>
	</div>
	


<script  type="text/javascript">
var jq=jQuery.noConflict();
function fff(){
/**
 *huhao
 * set the map div's height
 **/
	//var divHeight=document.getElementById("center").style.height;
	//var divWidth=document.getElementById("center").style.width;
	//document.getElementById("arcGis-page").style.height=divHeight;
	//document.getElementById("arcGis-page").style.width=divWidth;
	
	
	setTimeout(function(){
	     document.getElementById("orderList").click();
		},500);
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

</script>	
</body>