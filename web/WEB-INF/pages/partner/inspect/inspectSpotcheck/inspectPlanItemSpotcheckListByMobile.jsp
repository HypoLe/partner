<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<style>
.coolscrollbar{scrollbar-arrow-color:yellow;scrollbar-base-color:lightsalmon;max-width: 330px;}
.input{
	color: black;
	font-size: 17px;
	background: white;
	border-radius: 15px
}
.select{
	color: white;
	font-size: 16;
	background: black;
}
.submit{
	color: black;
	font-size: 30;
	background: #D4FE83;
	width: 50%;
	border-radius: 15px;
}
.button{
	color: black;
	font-size: 25px;
	background: green;
}
.body{
	color: black;
	background: white;
}
.div{
	color: black;
	background: white;
	font-size: 25px;
}
.span{
	color: black;
	background: white;
	font-size: 25px;
}
.spanEven{
	color: black;
	background: #F7F7F7;
	font-size: 25px;
}
.table{
	color: black;
	background: white;
	font-size: 25px;
	width: 380px
}
</style>
<script language="javascript">
var id="";
$(function(){
	//alert(0);
});
function showDiv(id){
       var el = $('#'+id);
		if (el.is(":hidden")) {
				el.show();
		} else {
				el.hide();
		}
}

var allSubId="";
function addId(domId){
	allSubId = allSubId+domId+"|";
}
function isNumber(oNum){
	  if(!oNum) return false;
	  var strP=/^\d+(\.\d+)?$/;
	  if(!strP.test(oNum)) return false;
	  try{
	  	if(parseFloat(oNum)!=oNum) return false;
	  }
	  catch(ex)
	  {
	   	return false;
	  }
	  return true;
}
function submit(){
	var idArray = allSubId.split('|');
	var returnjson = "";
	if(idArray.length !=0){
		for(var i = 0;i<idArray.length-1;i++){
			//id=idArray[i]
			var val = $("#"+idArray[i]).val();
			var len = val.length;  
			if(!isNumber(val)) {
            	partner.showMsg("填写内容必须为数字");
                return;  
	        }
			returnjson+="{\"id\":\""+idArray[i]+"\",\"value\":\""+val+"\"}";
			if(i != idArray.length-2){
				returnjson+=",";
			}else{
				returnjson+="";
			}
		}
		//alert(returnjson);
		partner.saveAllResult(returnjson);
	}else{
		partner.showMsg("无数据");
		return;
	}
}
function pageMethod(type,msg){
	if("1" == type+""){
		submit();
	}
	if("2" == type+""){
		if("480" == msg){
			$("table").css("width",300);
		}else{
			$("table").css("width",330);
		}
	}
}
</script>
<div align="center">
<table class="table" cellpadding="0"  cellspacing="0" id="table">
	<thead>
		<tr>
			<th id="th" style="color: black;font: oblique;font-size: 43px;background: white;">抽&nbsp;&nbsp;检<br><hr></th>
		</tr>
	</thead>
	<c:forEach items="${list}" var="map" varStatus="idxStatus">
		<c:if test="${empty map.itemList }" var="result">
      		<tr>
				<td>
					${map.itemgroup}
				</td>
				<td>${map.markrule}</td>
				<td>${map.score}</td>
				<td></td>
			</tr>
      	</c:if>
      	<c:if test="${!result}">
      		<c:forEach items="${map.itemList}" var="obj" varStatus="status"> 
				<tr>
					<td>
					<c:if test="${(itemIndex.index)%2 != 0}">
					<div class="spanEven" onclick="showDiv('div_${obj.spotTmpId}_${obj.planItemId}')">第${idxStatus.index+1 }组&nbsp;巡检项:&nbsp;${obj.item}&nbsp;&nbsp;;&nbsp;是否异常:&nbsp;<c:if test="${obj.expFlag == 1}" var="result">是</c:if><c:if test="${!result}">否</c:if>;&nbsp;&nbsp;<br>总分:${map.score}<br>
					${obj.content}</div>
					</c:if>
					<c:if test="${(itemIndex.index)%2 == 0}">
					<div class="spanEven" onclick="showDiv('div_${obj.spotTmpId}_${obj.planItemId}')">第${idxStatus.index+1 }组&nbsp;巡检项:&nbsp;${obj.item}&nbsp;&nbsp;;&nbsp;是否异常:&nbsp;<c:if test="${obj.expFlag == 1}" var="result">是</c:if><c:if test="${!result}">否</c:if>;&nbsp;&nbsp;<br>总分:${map.score}<br>
					${obj.content}</div>
					</c:if>
					<div id="div_${obj.spotTmpId}_${obj.planItemId}" style="display: none" class="div">
						巡检项组:${map.itemgroup}<br>
						扣分规则:${map.markrule}
					<br>异常内容:${obj.expContent}
					<br>扣分:&nbsp;<input class="input" type="number" id="score_${obj.spotTmpId}_${obj.planItemId}" name="score_${obj.spotTmpId}_${obj.planItemId}"
					id="score"  value="0"/>
					<script>
					//添加ID;
						addId('score_${obj.spotTmpId}_${obj.planItemId}');
					</script>
					<br>
					</div><hr></td>
				</tr>
	  		</c:forEach>
      	</c:if>
    </c:forEach>
</table>
</div>
<div align="center"><input class="submit" align="middle" type="button" value="提交" onclick="submit()"/></div>

<script language="javascript">
partner.modifyPageWidth();
</script>