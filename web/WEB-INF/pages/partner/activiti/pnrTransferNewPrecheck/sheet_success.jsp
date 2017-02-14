<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var success = "${success}";
	function intoOwner(){
	 var undo=document.location.href;
	 undo = undo.substring(0,undo.indexOf("?")+1)+"method=listBacklog${condition}";
	 location.href = undo;
	}
	function intoDuelog(value,b){
	 var undo=document.location.href;
	 undo = undo.substring(0,undo.indexOf("?")+1)+"method="+value+"${condition}";
	 location.href = undo;
	}
	
	function changePage(){
	 	//var undo=document.location.href;
		//location.href = undo+'/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=querySamplingList${condition}';
		
	     var undo=document.location.href;
		 undo = undo.substring(0,undo.indexOf("?")+1)+"method=querySamplingList&noIndex=1${condition}";
		 location.href = undo;
	}
		
	if(success=="check"){
		window.setTimeout(intoOwner, 3000);
	}else if(success=="duelog"){
		window.setTimeout(intoDuelog("listDueBacklog&definitionKey=troubleShooting","mark"), 3000);
	}else if(success=="waitWorkOrderList"){
		window.setTimeout(intoDuelog("waitWorkOrderList","mark"), 3000);
	}else if(success=="sampling"){
		window.setTimeout(changePage(), 3000);
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
<div class="successPage">
 	  <h2 class="header">操作成功!</h2>
</div>
