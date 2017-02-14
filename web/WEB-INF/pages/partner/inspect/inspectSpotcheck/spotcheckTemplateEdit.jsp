<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%response.setHeader("cache-control","public"); %>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
var score = 0;
var allnoselect = 'true';
Ext.onReady(function(){
	jq(".hideTr").hide();
	jq("#thisclick").hide();
    v = new eoms.form.Validation({form:'taskOrderForm'});
    v.custom = function(){
    	/*if(validateScore()){
    		return;
    	}*/
    	if(score!=100 && allnoselect=='false'){
    		alert('模板分数应该为100');
    		return;
    	}
    	
    	getcheckBox();
    	return true;
    };
    
    function validateScore(){
    	var score = document.getElementsByName("bigItemScore");
    	var sum =0;
    	for(var i=0;i<score.length;i++){
    		sum = sum+ parseFloat(score[i].value)-0;
    	}
    	
    	if(sum>100){
    		alert("填写大项分数总和超过了100");
    		return true;
    	}
    	/*
    	var usableScore = '${usableScore}';
    	var score = jq('#score').val();
    	if(parseFloat(score) > usableScore){
    		alert('填写分数超过了可用分数('+usableScore+"分)");
    		return true;
    	}*/
    }
    
    function getcheckBox(){
    	var bigId = document.getElementsByName("bigId");
    	for(var i=0;i<bigId.length;i++){
			var checkBo = document.getElementsByName("check"+bigId[i].value)
			var checkValue="";
			for(var j=0;j<checkBo.length;j++){
				if (checkBo[j].checked==true && checkBo[j].value !="on"){ 
                checkValue+=checkBo[j].value;   
		        checkValue+="|";
                }
			}  
			 document.getElementsByName("checkBox")[i].value=checkValue;	
    	}
    }
});

var checkflag = "false";
function chooseAll(obj,obj2){
	if(obj2.checked){  //表示当前没有多选没有被选择
		checkflag = "false";
	}else{
		checkflag = "checked";
	}
    var objs = document.getElementsByName(obj);    
    if(checkflag=="false"){
        for(var i=0; i<objs.length; i++){
           objs[i].checked="checked";
        } 
        checkflag="checked";
    }
    else if(checkflag=="checked"){ 	    	    
	    for(var i=1; i<objs.length; i++){
	           objs[i].checked=false;
	    } 
	    checkflag="false";
    }
}
function deleteSpotcheck(){
	if (confirm("是否确认删除?")==true){
		var form = document.getElementById('taskOrderForm');
		form.action = '${app}/partner/inspect/inspectSpotcheckAction.do?method=deleteSpotcheckTemplate';
		form.submit();
	}
}

function openImport(handler,obj,img){
		var id="listQueryObject"+handler;   //div的id
		var tabid = "tab"+handler;
		var hidid = "hid"+handler;
		jq(document.getElementsByName("isdelete")[(obj.parentNode.parentNode.rowIndex-1)/2]).val("yes");
		var el = Ext.get(id);
		var checkname="check"+handler;
		var hidval = document.getElementById(hidid).value;
		if(img=='add'){
			jq(obj.parentNode.parentNode).next().show();
			document.getElementById("dele"+handler).style.display='block';
			document.getElementById("add"+handler).style.display='none';
		}else{
			document.getElementById("add"+handler).style.display='block';
			document.getElementById("dele"+handler).style.display='none';
		}
		
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			jq(obj.parentNode.parentNode).next().hide();
		}
		else{
			el.slideIn();
			jq(obj.parentNode.parentNode).next().show();
			if(hidval=="no"){
			return;
			}
			Ext.Ajax.request({
				url:'${app}/partner/inspect/inspectSpotcheckAction.do?method=getAllInspectTemplateItem',
				params:{inspectTemplateId:'${inspectTemplateId}',bigItemId:handler,month:'${month}',year:'${year}'},
				success: function(response){
					var jsonResult = Ext.util.JSON.decode(response.responseText); 
					for(var i=0;i<jsonResult.length;i++){
						if(jsonResult[i].ID==null || jsonResult[i].ID==''){
							jq(document.getElementById(tabid)).append("<tr><td><input type='checkbox' name='"+checkname+"' value='"+jsonResult[i].TEMPLATEID+"' /><input type='hidden' value='"+jsonResult[i].ID+"' /></td><td>"+jsonResult[i].INSPECT_ITEM+"</td>"+
							"<td>"+jsonResult[i].INSPECT_CONTENT+"</td></tr>")
						}else{
							jq(document.getElementById(tabid)).append("<tr><td><input type='checkbox' checked='checked' name='"+checkname+"' value='"+jsonResult[i].TEMPLATEID+"' /><input type='hidden' value='"+jsonResult[i].ID+"' /></td><td>"+jsonResult[i].INSPECT_ITEM+"</td>"+
							"<td>"+jsonResult[i].INSPECT_CONTENT+"</td></tr>")
						}
					}
					jq(document.getElementById(hidid)).val("no");
				},
				failure: function(response) {
					
				}
			})
		}
	}

function tagClick(){
	score = 0;
	var openarray = document.getElementsByName('isdelete');
	var selectarray = document.getElementsByName('selectnum');
	var scorearray = document.getElementsByName('bigItemScore');
	var rulerray = document.getElementsByName('markRule');
	for(var i=0;i<openarray.length;i++){
		var isopen = openarray[i].value;
		if(isopen == 'no') {  //当前没有打开
			if(selectarray[i].value>0){//没展开的下面有选择的项
				score = parseFloat(scorearray[i].value)-0+score;
				allnoselect = 'false';
			}else{
				jq(scorearray[i]).removeAttr('alt');
				jq(rulerray[i]).removeAttr('alt');
				jq(scorearray[i]).val('0');
			}
		}else{ //当前展开了巡检项
			
			var checkbo = jq(openarray[i]).parent().parent().parent().find('input[type=checkbox]');
			var ischeck = 'false';
			for(var j=1;j<checkbo.length;j++){
				if(checkbo[j].checked){
					ischeck = 'true';
				}				
			}
			if(ischeck == 'false'){
				jq(scorearray[i]).removeAttr('alt');
				jq(rulerray[i]).removeAttr('alt');
				jq(scorearray[i]).val('0');
			}else{
				score = parseFloat(scorearray[i].value)-0+score;
				allnoselect = 'false';
			}
			
		}
	}
	//jq(scorearray).removeAttr('alt');
	
	jq("#thisclick").click();
	
	jq(scorearray).attr('alt',"re:/^(\d+)(\.\d+)?$/,re_vt:'请输入正整数或小数',maxLength:7")
	
	jq(scorearray).attr('alt','allowBlank:false');
	
}

</script>

<form action="${app }/partner/inspect/inspectSpotcheckAction.do?method=saveSpotTemplate" method="post" id="taskOrderForm">
	<input type="hidden" name="year" value="${year }" />
	<input type="hidden" name="month" value="${month }" />
	<table  id="tab" class="table list" cellspacing="0" cellpadding="0">
	  <tr>
	  	<td><input type="hidden" name="inspectTemplateId" value="${inspectTemplateId}"></td>
	    <th width="10%">大项名称</th>
	    <th width="15%">大项分数</th>
	    <th>扣分规则</th>
	  </tr>
	  <c:forEach var="bigItem" items="${bitItemList}" varStatus="statue">
	  <tr>
	  	<td><img
			 src="${app}/images/icons/add.gif"
			 align="absmiddle"
			 style="cursor:pointer;display: block;" onclick="openImport('${bigItem.id }',this,'add')" id="add${bigItem.id }" />
			 <img
			 src="${app}/images/icons/delete.gif"
			 align="absmiddle"
			 style="cursor:pointer;display: none;" id="dele${bigItem.id }" onclick="openImport('${bigItem.id }',this,'dele')" />
			 <input type="hidden" name="bigId" value="${bigItem.id }"/>
			 <input type="hidden" name="selectnum" value="${bigItem.selectitemnum }"/>
		  </td>
	    <td>${bigItem.name }<input type="hidden" name="itemNum" value="${bigItem.itemNum}"><input type="hidden" name="bigItemname" value="${bigItem.name }"/>
	    	<input type="hidden" name="spotTemplateId" value="${bigItem.spotTemplateId }" /><input type="hidden" name="checkBox"/>
	    </td>
	    <td>
	    	<input alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入正整数或小数',maxLength:7"  type="text" name="bigItemScore" class="text medium" value="${bigItem.score}"/>
	    </td>
	    <td><textarea  name="markRule" alt="allowBlank:false" style="width: 92%;"
							id="content">${bigItem.mark_rule}</textarea>
		</td>
	  </tr>
	  <tr class="hideTr">
	  	<td colspan="4">
	  		<div id="listQueryObject${bigItem.id }" 
				 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
				<table id="tab${bigItem.id }" cellpadding="0" class="table list" cellspacing="0">
					<tr><td><input type="hidden" name="isdelete" value="no" />
						<input type="checkbox" name="check${bigItem.id}" onclick='javascript:chooseAll("check${bigItem.id}",this);'/>
						</td><td>巡检项目</td><td>巡检内容</td></tr>
				</table>
				<input type="hidden" value="yes" id="hid${bigItem.id }">
			</div>
	  	</td>
	  </tr>
	  </c:forEach>
	</table>
	<c:if test="${bitItemListSize ne '0'}" var="sult">
		<input type="submit" value="保存" class="btn" id="thisclick">
		<input type="button" value="保存" class="btn" onclick="tagClick()">
	</c:if>
	<input type="button" onclick="window.history.back();" value="返回" class="btn"/>
	<c:if test="${!sult}">
		<font color="red">该模板下没有模板项！</font>
	</c:if>
</form>
<%@ include file="/common/footer_eoms.jsp"%>