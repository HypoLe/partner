<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@page import="com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="java.util.List"%>
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>
<body>
<script type="text/javascript">
eoms.TabPanel = function(el,config){
	this.el = Ext.get(el);
	this.tabPanel = new Ext.TabPanel(this.el);
	if(config.items){
		Ext.each(config.items,this.addTab,this);
	}
	this.tabPanel.activate(0);
}

eoms.TabPanel.prototype.addTab = function(item){
	var id = item.id || Ext.id();
	var tabItemEl = Ext.get(id);
	if(item.isIframe){
		if(tabItemEl){
			tabItemEl.set({
				width:'100%',
				height:'100%',
				frameborder:0
			},false);		
		}
		else{
			this.el.createChild({
				tag:"iframe",
				id:id,
				name:id,
				width:"100%",
				height:"100%",
				frameborder:0,
				src:"about:blank"
			});
		}
	}
	var tab = this.tabPanel.addTab(id,item.text);
	if(item.isIframe){
		tab.on("activate",function(){
			if(Ext.getDom(id).src=="about:blank"){
				Ext.getDom(id).src = item.url;
			}    		
      	});
	}
	else if(item.url){
		tab.setUrl(item.url, item.params, item.loadOnce || true).loadScripts = true;
	}
	tab.setHidden(item.hidden || false);
}
</script>
<%
	List pnrFeeInfoPlanList = (List)request.getAttribute("pnrFeeInfoPlans");
	PnrFeeInfoPlan pnrFeeInfoPlan = null;
%>
<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
				<%
				for(int i=0;i<pnrFeeInfoPlanList.size();i++){
					pnrFeeInfoPlan = (PnrFeeInfoPlan)pnrFeeInfoPlanList.get(i);
				if(i!=0){
				%>
				,
				<%
				}
				%>
					{id:'step-<%=i %>',text:'第<%=i+1 %>次付款', url:'${app}/partner/baseinfo/pnrFlow.do?method=showFlow&id=<%=pnrFeeInfoPlan.getId()%>&flowType=feeinfo', isIframe : true}
				<%
        		}
				%>
            ]
        };
        _tabs = new eoms.TabPanel('flow-page', tabConfig);
    });
</script>

<div id="flow-page"></div>
</body>
</html>