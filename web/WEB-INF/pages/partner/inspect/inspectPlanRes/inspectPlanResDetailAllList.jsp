<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript">
	
	var items=[];
	if(${pnrInspect2SwitchConfig.sheetInspectSwitch}==true){
		items.push({
           text : "临时任务细化",
		   url : "${app }/partner/inspect/inspectPlan.do?method=findInspectPlanResDetailBurstList&id=${id }&sheet=${sheet}" ,
		   isIframe : true 
        });	
	}
	items.push({
        text : "周期任务细化",
   		url : "${app }/partner/inspect/inspectPlan.do?method=findInspectPlanResDetailList&id=${id }&sheet=${sheet}" ,
   		isIframe : true 
     });	

	var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:items
        };
        _tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
    });
    
    function backList(){
		 window.location.href= '${app }/partner/inspect/inspectPlan.do?method=findInspectPlanMainList';
	}
</script>

<div id="sheet-detail-page">
</div>
<input type="button" value="返回" onclick="backList();" class="btn"/>
<%@ include file="/common/footer_eoms.jsp"%>