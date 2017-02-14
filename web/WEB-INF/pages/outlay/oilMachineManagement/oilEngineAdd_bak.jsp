<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="org.apache.struts.taglib.html.Constants" %>
<%@ page import="org.apache.struts.Globals" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<form id="mainForm" name="mainForm" action="OilEngineManagement.do?method=save" method="post"> 
<input type="hidden" name="updateId" id="updateId" value="${oilEngine.id}"/> 
<input type="hidden" name="createUserId" id="createUserId" value="${createUserId}"/> 
<input type="hidden" name="nextAction" id="nextAction" value="${nextAction}"/> 
<table class="formTable">
	<tr>
		<td class="label">代维公司</td>
		<td class="content" colspan=1>
		<input type="text" id="partnerName_CN" name="partnerName_CN" value="${oilEngine.company}"  alt="allowBlank:false,vtext:'代维公司不能为空'"/>
		<input type="hidden" id="partnerName" name ="partnerName" value="${oilEngine.company}"/></td>
		 <eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
				rootId="" rootText="代维公司树"  valueField="partnerName" handler="partnerName_CN" 
				textField="partnerName_CN" checktype="dept" single="true" />
			<td class="label">市区</td>
		<td class="content">
		<input id="oilEngine" name ="oilEngine" type="text" readonly="readonly" value="${oilEngine.company}" />
		</td>			
	</tr>
	<tr>
	<td class="label">区县</td>
		<td class="content"><input id="oilEngine" name ="oilEngine" type="text" value="${oilEngine.company}" />
		</td>	
		<td class="label">油机费用填写时间</td>
		<td class="content">
		<input class="text" type="text" name="operateTime" value="${oilEngine.operateTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					 id="operateTime" readonly="readonly"/>		
		</td>	
		
	</tr>
	<tr>
		<td class="label">油机开始使用时间</td>
		<td class="content">
		<input class="text" type="text" name="startTime" value="${oilEngine.startTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间！',allowBlank:false"
					 id="startTime" readonly="readonly"/>
		</td>	
		<td class="label">油机使用结束时间</td>
		<td class="content">
		<input class="text" type="text" name="endTime" value="${oilEngine.endTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间！',allowBlank:false"
					 id="endTime" readonly="readonly"/>
		</td>
	</tr>	
	
	
	<tr>
	   <td class="label">驻点名称</td>
		<td class="content"><input id="stationName" name ="stationName" type="text" value="${oilEngine.stationName}"/>
		</td>
		<td class="label">发电基站</td>
		<td class="content">
		<input id="powerStations" name ="powerStations" type="text" value="${oilEngine.powerStations}"/>
		</td>
		
	</tr>	

</table>
			<input type="submit" name="submit" id="submit" value="保存" onclick="return saveAsDraft();"/>
			<input type="hidden" name="<%=Constants.TOKEN_KEY%>" value="<%=session.getAttribute(Globals.TRANSACTION_TOKEN_KEY)%>"/> 
</form>
<script>
var myJ = $.noConflict();

Ext.onReady(function(){
	setOldSelectionSelected("approvedView");
	if(document.all["approvedView"].value == '${APPROVED_YES}'){
		document.getElementById("auditUserTree").style.display = "";
	}
})
var vlt = new eoms.form.Validation({form:'mainForm'});

function saveConfirm(){
	if(document.all["status"].value == '${STATUS_REJECT}'){
		document.all["status"].value = '${STATUS_AUDIT}';
		return true;
	}

	if(document.all["approvedView"].selectedIndex == 0){
		alert("请选择是否审核！");
		return false;
	}
	if(document.all["approvedView"].selectedIndex == 1){
		if(confirm("确认提交审核？")){
			document.all["status"].value = '${STATUS_AUDIT}';
			return true;
		}else{
			return false;		
		}
	}else if(document.all["approvedView"].selectedIndex == 2){
		document.all["status"].value = '${STATUS_NO_NEED_AUDIT}';
		return true;
	}else{
		document.all["status"].value = '${STATUS_AUDIT}';
		return true;
	}


}


function setOldSelectionSelected(selectObjName){
	var selectObj = document.getElementsByName(selectObjName)[0];

		for(var i=0;i<selectObj.options.length;i++){
			if(selectObj.options[i].value=="${oilConsuming.approved}"){
				selectObj.options[i].selected = true;
				break;
			}
		}
	}


function setHiddenStatusValue(slt){
	document.getElementById("auditUserTree").style.display = "none";
	if(slt.selectedIndex == 0){
		document.all["status"].value = '${STATUS_DRAFT}';
		document.all["taskOwner"].value = "";
		document.all["taskOwner_CN"].value = "";
	}
	if(slt.selectedIndex == 1){
		document.all["status"].value = '${STATUS_AUDIT}';
		document.getElementById("auditUserTree").style.display = "";
	}
	if(slt.selectedIndex == 2){
		document.all["status"].value = '${STATUS_NO_NEED_AUDIT}';
		document.all["taskOwner"].value = "";
		document.all["taskOwner_CN"].value = "";
	}
	
	document.all["approved"].value = document.all["approvedView"].options[document.all["approvedView"].selectedIndex].value;
}

function saveAsDraft(){
//	document.all["status"].value = '${STATUS_DRAFT}';
	return true;
}

function autoCountFee(){
	document.all["totalFee"].value =  document.all["oilConsumingCount"].value * document.all["perPrice"].value;
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>