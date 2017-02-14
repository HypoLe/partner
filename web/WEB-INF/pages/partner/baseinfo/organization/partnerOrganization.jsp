	<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ include file="/common/header.jsp"%>
	<%@ include file="/common/extlibs.jsp"%>
	<%@ include file="/common/body.jsp"%>
	<link rel="stylesheet" href="${app}/jit/Spacetree.css" type="text/css"></link>
	<link rel="stylesheet" href="${app}/jit/base.css" type="text/css"></link>
	 <script type="text/javascript" src="${app}/jit/excanvas.js"></script>
	 <script type="text/javascript" src="${app}/jit/jit.js"></script>
	 <script type="text/javascript" src="${app}/jit/example1.js"></script>
	 <div id="infovis"></div>
	<div id="log"></div>
	<script type="text/javascript">
		//var jq=jQuery.noConflict();
		if(-[1,]){ 
					var parentDivHeight=document.getElementById("main").style.height;
					var parentDivWidth=document.getElementById("main").style.width;
					//document.getElementById("map").style.height=parentDivHeight;
					//document.getElementById("map").style.width=parentDivWidth;
					document.getElementById("infovis").style.width=1000+"px";
					document.getElementById("infovis").style.height=800+"px";
		}else{
			    	var parentDivHeightIe=document.getElementById("main").offsetHeight;
			    	var parentDivWidthIe=document.getElementById("main").offsetWidth;
			    	//var parentScrollWidthIe=parent.document.getElementById("center").scrollWidth;
			    	//var parentEastWidthIe=parent.document.getElementById("east").offsetWidth;
					document.getElementById("infovis").style.width=1300+"px";
					document.getElementById("infovis").style.height=800+"px";
		}
		Ext.onReady(function(){
			var json=eval(${json});
			init(json);
		});
	</script>
	<%@ include file="/common/footer_eoms.jsp"%>
