<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
						<!-- 费用信息主页面 -->

<%request.setAttribute("userId", request.getParameter("userId")); %>

<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
                {id:'sheet-info',  text:'无付款计划付费信息', url:eoms.appPath+'/fee/partnerFeeInfos.do?method=search', isIframe : true},
                {id:'sheet-edu',   text:'有付款计划付费信息', url:eoms.appPath+'/fee/partnerFeeInfos.do?method=searchPlanFee', isIframe : true}             
            ]
        };

        _tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);

       // init();
    });
    
    function doCheck(id,bCheck){
        var item = _tabs.getActiveTab();
        var selfItem = _tabs.getTab(id);
        if(item==selfItem){
            _tabs.activate("sheet-info");
        }
        	
        if(bCheck){
            _tabs.tabPanel.hideTab(id);
        }
        else{
            _tabs.unhideTab(id);
        }
    }

    function disableTab(id){
        _tabs.tabPanel.disableTab(id);
    }
		
    function enableTab(id){
        _tabs.tabPanel.enableTab(id);
    }
		
    function disableAllTab(){
        _tabs.tabPanel.disableTab("sheet-edu");
        _tabs.tabPanel.disableTab("sheet-exp");
    }
		
    function enableAllTab(){
        _tabs.tabPanel.enableTab("sheet-edu");		
		_tabs.tabPanel.enableTab("sheet-exp");
    }

    function setUserid(userId){
        window.navigate("${app}/kmmanager/kmExpertBasics.do?method=newExpert&userId="+userId);
    }
		
    function init(){
        var userId = '${userId}';
        if(userId == ''){
            disableAllTab();
        }else{
            enableAllTab();
        }
    }
</script>

<div id="sheet-detail-page"></div>

<br/>

<%@ include file="/common/footer_eoms.jsp"%>
