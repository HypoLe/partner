<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<c:if test="${proId1!=''}">
<%request.setAttribute("proId", request.getAttribute("proId1")); %>
</c:if>
<c:if test="${proId!=''}">
<%request.setAttribute("proId", request.getParameter("proId")); %>
</c:if>
<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
                {id:'sheet-provider',  text:'合作伙伴', url:eoms.appPath+'/partner/baseinfo/partnerDepts.do?method=${methodName}&nodeId=${nodeId}&proId=${proId}&proId1=${proId1}',collapsible: true, isIframe : true},
                {id:'sheet-aptitude',   text:'合作伙伴资质', url:eoms.appPath+'/partner/baseinfo/aptitudes.do?method=searchOne&proId=${proId}&proId1=${proId1}',collapsible: true, isIframe : true}
            ]
        };

        _tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
        init();
    });
    
    function doCheck(id,bCheck){
        var item = _tabs.getActiveTab();
        var selfItem = _tabs.getTab(id);
        if(item==selfItem){
            _tabs.activate("sheet-provider");
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
        _tabs.tabPanel.disableTab("sheet-aptitude");
    }
		
    function enableAllTab(){
        _tabs.tabPanel.enableTab("sheet-aptitude");		
    }

    function setproId(proId){
        window.navigate("${app}/partner/baseinfo/aptitudes.do?method=newExpert&proId="+proId);
    }
		
    function init(){
        var proId = '${proId}';
        var proId1 = '${proId1}';
        if(proId == ''){
	        if(proId1==''){
	        	disableAllTab();
	        }else{
	        	enableAllTab();
	        }
        }else{
            enableAllTab();
        }
    }
</script>

<div id="sheet-detail-page" >

</div>

<br/>
<%@ include file="/common/footer_eoms.jsp"%>
