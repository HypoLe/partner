<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<%request.setAttribute("idSite", request.getParameter("idSite")); %>


<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
                {id:'sheet-site',  text:'站点信息',    url:eoms.appPath+'/partner/net/sites.do?method=edit&idSite=${idSite}', isIframe : true},
                {id:'sheet-papers',text:'基站证件信息', url:eoms.appPath+'/partner/net/sitePaperss.do?method=search&idSite=${idSite}', isIframe : true}
            ]
        };

        _tabs = new eoms.TabPanel('sheet-detail-page', tabConfig);
        init();
    });
    
    function doCheck(id,bCheck){
        var item = _tabs.getActiveTab();
        var selfItem = _tabs.getTab(id);
        if(item==selfItem){
            _tabs.activate("sheet-site");
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
        _tabs.tabPanel.disableTab("sheet-papers");
    }
		
    function enableAllTab(){
        _tabs.tabPanel.enableTab("sheet-papers");		
    }

    function setId(idSite){
    window.location.href=eoms.appPath+"/partner/net/sites.do?method=newExpert&idSite="+idSite;
    
      //  window.navigate("${app}/partner/net/sites.do?method=newExpert&siteNo="+siteNo);
    }
		
    function init(){
        var idSite = '${idSite}';
        if(idSite == ''){
            disableAllTab();
        }else{
            enableAllTab();
        }
    }
</script>

<div id="sheet-detail-page">
</div>

<br/>
<%@ include file="/common/footer_eoms.jsp"%>
