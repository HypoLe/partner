<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" href="${app}/arcGis/jquery-easyui-1.2.6/themes/default/easyui.css" type="text/css"></link>
<script type="text/javascript" src="${app}/arcGis/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${app}/arcGis/js/arcGisHelp.js"></script>
<script type="text/javascript" src="${app}/arcGis/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
 <script type="text/javascript">
 		var app='${app}';
		var list='${list}';
		var processInstanceId='${processInstanceId}';
		var returnJson='${returnJson}';
 </script>

<body class="easyui-layout">
<div id="center" region="center" title="地图">
	<!-- 第一个为arcgis js开发 第二个为百度地图API js开发  add by chenruoke -->
		    <!-- <iframe id="arcGis-page" name="arcGis-page" frameborder="no" style="width:100%;height:100%" src="${app}/partner/arcGis/arcGis.do?method=goToArcGisMap"></iframe>  -->
			<iframe id="arcGis-page" name="arcGis-page" frameborder="no" style="width:100%;height:100%" src="${app}/arcGis/flowmap.jsp"></iframe>
	</div>
</body>

<%@ include file="/common/footer_eoms.jsp"%>