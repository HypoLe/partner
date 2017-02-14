<%@ page contentType="text/html; charset=utf-8" %>
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
	<head>
		<title>地图</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<style>
				v\: * {
					behavior: url(#default#VML);
				}
				
				o\: * {
					behavior: url(#default#VML);
				}
				
				body,td,div,table {
					font-size: 12px;
					padding: 2px;
				}
				
				.STYLE2 {
					color: #F9F5F2
				}
				
				.tit {
					font-weight: bold;
					color: blue;
				}
				
				.title {
					cursor: default;
					color: #333333;
					text-align: right;
					padding-right: 3px;
				}
				
				div {
					padding: 0px;
				}
				
				.mypoint5 {
					position: absolute;
					width: 2px;
					height: 2px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint6 {
					position: absolute;
					width: 3px;
					height: 3px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint7 {
					position: absolute;
					width: 3px;
					height: 3px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint8 {
					position: absolute;
					width: 4px;
					height: 4px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint9 {
					position: absolute;
					width: 4px;
					height: 4px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint10 {
					position: absolute;
					width: 5px;
					height: 5px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint11 {
					position: absolute;
					width: 5px;
					height: 5px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint12 {
					position: absolute;
					width: 6px;
					height: 6px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint13 {
					position: absolute;
					width: 7px;
					height: 7px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.mypoint14 {
					position: absolute;
					width: 8px;
					height: 8px;
					background-color: #FF0000;
					overflow: hidden;
					line-height: 0px;
					cursor: default;
				}
				
				.stationText {
					position: absolute;
					color: #003399;
					padding-top: 2px;
				}
				
				.infodiv {
					position: absolute;
					left: 0px;
					top: 0px;
					padding: 3px;
					border: solid 1px #cccccc;
					background-color: #ffffff;
				}
				
				.countinfo {
					position: absolute;
					left: 600px;
					top: 30px;
					padding: 3px;
					border: solid 2px #990000;
					width: 170px;
					height: 400px;
					color: green;
					background-color: #eeeeee;
				}
		</style>
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="js/ext/resources/css/ext-ux-wiz.css" />
		<link rel="stylesheet" type="text/css" href="css/column-tree.css" />

		<script language="JavaScript" src="./js/map_05.js" type="text/javascript"></script>
		<script type="text/javascript" src="./js/ext/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="./js/ext/ext-all.js"></script>
		<script language="JavaScript" src="./js/prototype.js" type="text/javascript"></script>
		<script language="javascript" src="./js/My97DatePicker/WdatePicker.js" refer='true'></script>
		<script language="javascript" src="./js/ColumnNodeUI.js"></script>
		<script language="javascript" src="./js/column-tree.js"></script>
		<script language="javascript" src="./js/ajform.js"></script>
		<script language="javascript" src="./js/line.js"></script>

	</head>
	<body onload='loadMap("FJFZ");' topmargin="0" rightmargin="0"
		leftmargin="0" bottommargin="0">
		<div style="width:40%; height: 100%;display:inline; float: left" id="wingMap">

		</div>
		<div id="resourceDiv"
			style="width: 20%; height: 100%; display:inline; float: right;">
			
		</div>

	</body>
</html>