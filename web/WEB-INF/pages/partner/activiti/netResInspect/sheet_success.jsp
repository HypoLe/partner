<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	//var success = "${success}";
	function intoOwner(){
	 var undo=document.location.href;
	 undo = undo.substring(0,undo.indexOf("?")+1)+"method=listBacklog";
	 location.href = undo;
	}
	
	window.setTimeout(intoOwner, 3000);
	
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
<div class="successPage">
 	  <h2 class="header">操作成功!</h2>
</div>
