<%@ page language="java" pageEncoding="UTF-8"%>

<script type="text/javascript">
	function go(){
		window.setTimeout(function(){
		window.location = "pnrTransferArteryPrecheck.do?method=getBack${condition}";
		},1000);
	}
</script>
<style>
<!--
.successPage h2{
	height:32px;
	padding:10px 0 0 45px;
	margin:20px;
	vertical-align:middle;
	background-repeat:no-repeat;
	background-position:3px bottom;
 }
.successPage .header{
	color:#3786d6;
	background-image:url(../../images/icons/success.gif);
}
-->
</style>
<body onload="go()">
<div class="successPage">
 	  <h2 class="header">抓回成功!</h2>
</div>
</body>

