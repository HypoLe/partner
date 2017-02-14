<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript"><!--
var showType='${showType}';



Ext.onReady(function(){
	var selIndex = 0;
	if(showType+""=="1122501"){
		selIndex = 0;
	}
	if(showType+""=="1122502"){
		selIndex = 1;
	}
	if(showType+""=="1122503"){
		selIndex = 2;
	}
	if(showType+""=="1122504"){
		selIndex = 3;
	}
	if(showType+""=="1122505"){
		selIndex = 4;
	}
	if(showType+""=="1122506"){
		selIndex = 5;
	}
/*	if(showType+""=="1122507"){
		selIndex = 6;
	}
	if(showType+""=="1122508"){
		selIndex = 7;
	} */
	if(showType+""=="1122509"){
		selIndex = 6;
	}
	var tabs = new Ext.TabPanel('info-page');
   	tabs.addTab('li1', '基站');
   	tabs.addTab('li2', '室外箱体');
   	tabs.addTab('li3', '室分');
   	tabs.addTab('li4', '铁塔及天馈');
   	tabs.addTab('li5', '接入网');
   	tabs.addTab('li6', 'WLAN');
// 	tabs.addTab('li7', '巡检抽查');
// 	tabs.addTab('li8', '电费管理');
   	tabs.addTab('li7', '重点客户机房');   	   	
	tabs.activate(selIndex);
   	tabs.on('tabchange',function(_panel,_tab){
   		window.location='${app}/partner/inspect/errorDistanceAction.do?method=goToList&showType='+Ext.get(_tab.id).dom.value;
   	},this);
   		
});

function resetValue(){
	document.getElementById("ruleId").value="";
	document.getElementById("intervalTimeId").value="";
}


function addErrorDistanceRule(){
	document.getElementById("hidenForm").style.display="";
	document.getElementById("hiddenRuleId").value="";
	document.getElementById("resource").value="";
	document.getElementById("hiddenResource").value="";
	document.getElementById("resource").disabled=false;
	document.getElementById("ruleId").value="";
	document.getElementById("intervalTimeId").value="";
	
	document.getElementById("resName").innerHTML = '';
	document.getElementById("resource").style.display = "";
}
var resourceIdArray = '';
function addresourceId(resourceId){
	if('' == resourceIdArray){
		resourceIdArray = resourceId;
	}else{
		resourceIdArray = resourceIdArray+"|"+resourceId;
	}
}

function submitValue(){
	if(validate()){
		var resId = document.getElementById("resource").value;
		var resourceIdArrayTemp = (resourceIdArray+"").split("|");
		var exist = false;
		if(resourceIdArrayTemp.length>0){
			for(var i = 0;i<resourceIdArrayTemp.length;i++){
				if(resourceIdArrayTemp[i] == resId){
					exist = true;
				}
			}
		}
		if(exist){
			if(confirm("该资源已添加,点击确定要进行修改吗?")){
				document.getElementById("errorDistanceForm").submit();
			}else{
			
			}
		}else{
			document.getElementById("errorDistanceForm").submit();
		}
	}
}

function validate(){
     if(document.getElementById("resource").value == ''){
     	alert("请选择资源");
     	return false;
     }
     if(document.getElementById("ruleId").value == ''){
     	alert("请填写规则");
     	return false;
     }
     if(document.getElementById("intervalTimeId").value == ''){
     	alert("请填写时间间隔");
     	return false;
     }
     var reg = new RegExp("^[0-9]*$");
     var rule = document.getElementById("ruleId");
     var intervalTimeIdTemp = document.getElementById("intervalTimeId");
     if(rule.value.length>6){
     	alert("误差规则不能超过6位");
        return false;
     }
     if(intervalTimeIdTemp.value.length>6){
     	alert("误差距离不能超过6位");
        return false;
     }
     
    if(!reg.test(rule.value)){
        alert("误差距离必须数字!");
        return false;
    }
    if(!/^[0-9]*$/.test(rule.value)){
        alert("误差距离必须数字!");
        return false;
    }
    if(!reg.test(intervalTimeIdTemp.value)){
        alert("时间间隔必须为数字!");
        return false;
    }
    if(!/^[0-9]*$/.test(intervalTimeIdTemp.value)){
        alert("时间间隔必须数字!");
         return false;
    }
    return true;
  }
--></script>
<div>

	<ul id="info-page">
		<li id='li1' value="1122501"></li>
		<li id='li2' value="1122502"></li>
		<li id='li3' value="1122503"></li>
		<li id='li4' value="1122504"></li>
		<li id='li5' value="1122505"></li>
		<li id='li6' value="1122506"></li>
		<!--<li id='li7' value="1122507" ></li>
    <li id='li8' value="1122508" ></li> -->
		<li id='li7' value="1122509"></li>
	</ul>
	<logic:present name="list" scope="request">
		<display:table name="list" cellspacing="0" cellpadding="0" id="list"
			class="table list" export="false" sort="list" pagesize="${pagesize}"
			requestURI="errorDistanceAction.do?method=goToList"
			partialList="true" size="${size}">
			<display:column title="资源点">
				<script type="text/javascript">
			addresourceId(${list.resource});
		</script>
				<eoms:id2nameDB id="${list.resource}" beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column title="规则">
		${fn:split(list.rule,".")[0]} (米)
		</display:column>
			<display:column title="时间间隔">
		${list.intervalTime} (分钟)
		</display:column>
			<display:column title="添加时间">
		${list.addTime}
		</display:column>
			<display:column title="添加人员">
				<eoms:id2nameDB id="${list.addUser}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column title="操作">
				<input type="button" value="删除" class="btn"
					onclick="deleteRule('${list.id}')" />
				<input type="button" value="修改" class="btn"
					onclick="modify('${list.id}','${list.resource}','${list.rule}','${list.intervalTime}','${list.addTime}')" />
			</display:column>
		</display:table>
	</logic:present>
	<div>
		<input type="button" class="btn" value="给该专业添加规则"
			onclick="addErrorDistanceRule()" />
	</div>
</div>
<br>
<div id="hidenForm" style="display: none;">
	<form
		action="${app}/partner/inspect/errorDistanceAction.do?method=edit"
		method="post" id="errorDistanceForm" name="errorDistanceForm">
		<table id="taskOrderTable" class="formTable">
			<input id="hiddenRuleId" name="hiddenRuleId" type="hidden" />
			<input id="hiddenResource" name="hiddenResource" type="hidden" />
			<tr>
				<td class="label">
					巡检专业*
				</td>
				<td class="content">
					<eoms:id2nameDB id="${showType}" beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">
					资源点*
				</td>
				<td class="content">
					<eoms:comboBox name="resource" id="resource" styleClass="border"
						initDicId="${showType}" />
					<span id="resName"></span>
				</td>
			</tr>
			<tr>
				<td class="label">
					规则*
				</td>
				<td class="content">
					<input type="text" name="rule" id="ruleId"
						value="${errorDistanceForm.rule}" />
					米
				</td>
				<td class="label">
					时间间隔*
				</td>
				<td class="content">
					<input type="text" name="intervalTime" id="intervalTimeId"
						value="${errorDistanceForm.rule}" />
					分钟
				</td>
			</tr>
		</table>
		<input type="hidden" id="hidenSpeciality" value="${showType}"
			name="speciality" />
		<input type="button" class="btn" value="保存" onclick="submitValue()" />
		<input type="button" class="btn" value="重置" onclick="resetValue()" />
		<input type="button" class="btn" value="关闭" onclick="closeForm()" />
	</form>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function closeForm(){
	document.getElementById("hidenForm").style.display='none';
}

function modify(id,resource,rule,intervalTime,addTime){
	addErrorDistanceRule();
	document.getElementById("hiddenRuleId").value=id;
	var re = document.getElementById("resource");
	re.value=resource;
	
	var txt = re.options[re.selectedIndex].text; 
	
	
	document.getElementById("hiddenResource").value=resource;
	re.disabled=true;
	re.style.display = "none";
	document.getElementById("resName").innerHTML = txt;
	
	document.getElementById("ruleId").value=rule;
	document.getElementById("intervalTimeId").value=intervalTime;
}
 function deleteRule(id){
 	if(confirm("确定要删除吗?")){
 			window.location.href="${app}/partner/inspect/errorDistanceAction.do?method=edit&type=delete&id="+id;
	}else{
	}
 }
</script>