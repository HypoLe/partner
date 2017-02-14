<%@ page language="java" pageEncoding="utf-8"%>
<script type="text/javascript">
//查看修改场景模板
//参数说明：obj	当前对象;
//processType 流程类型（公客or抢修）;
//processInstanceId 流程号;
//linkType 所在环节（指在那个报表操作的）;
//operateFlag 当前登录人是否有修改场景模板的权限
//handleFlag 单条工单是否已经审批完成 Y：完成；N：未审批
 function viewUpdateSceneTemplate(obj,processType,processInstanceId,linkType,operateFlag,handleFlag){ 
	//alert(processInstanceId);
	var apprText = jq(obj).parents("tr").find("td:eq(0)").text();
	if(handleFlag == 'N'){
		if(apprText == '通过' || apprText == '驳回'){	
			handleFlag = 'Y';
		}
	}
	var url = '${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=viewUpdateSceneTemplate&processType='+processType+'&processInstanceId='+processInstanceId+'&linkType='+linkType+'&operateFlag='+operateFlag+'&handleFlag='+handleFlag;
	var _sHeight = 400;
    var _sWidth = 1200;
    var sTop=(window.screen.availHeight-_sHeight)/2;
    var sLeft=(window.screen.availWidth-_sWidth)/2;
	var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	window.showModalDialog(url,window,sFeatures);
	
} 

//归集工单查询
function viewSingleCollection(processInstanceId,siteCd){
	var url = '${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=viewSingleCollection&processInstanceId='+processInstanceId+'&siteCd='+siteCd;
	var _sHeight = 400;
    var _sWidth = 1158;
    var sTop=(window.screen.availHeight-_sHeight)/2;
    var sLeft=(window.screen.availWidth-_sWidth)/2;
	var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	window.showModalDialog(url,window,sFeatures);
}

//查看现场拍照
function viewLiveCamera(processInstanceId){
	var url = '${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=viewLiveCamera&processInstanceId='+processInstanceId;
	var _sHeight = 600;
    var _sWidth = 1158;
    var sTop=(window.screen.availHeight-_sHeight)/2;
    var sLeft=(window.screen.availWidth-_sWidth)/2;
	var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	window.showModalDialog(url,window,sFeatures);
}

</script>
