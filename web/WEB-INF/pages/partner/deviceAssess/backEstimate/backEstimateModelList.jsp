<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
    var _tabs;
    Ext.onReady(function(){
        var tabConfig = {
            items:[
                {id:'sheet-facilityNum',  text:'设备量信息', url:eoms.appPath+'/partner/deviceAssess/facilityNums.do?method=search', isIframe : true},
                {id:'sheet-factoryDispose',  text:'厂家处理设备故障', url:eoms.appPath+'/partner/deviceAssess/factoryDisposes.do', isIframe : true},
                {id:'sheet-bigFault',  text:'设备重大故障', url:eoms.appPath+'/partner/deviceAssess/bigFaults.do?method=search', isIframe : true},
                {id:'sheet-facilityinfo',  text:'厂家设备问题', url:eoms.appPath+'/partner/deviceAssess/facilityinfos.do', isIframe : true},
                {id:'sheet-counsel',  text:'咨询服务', url:eoms.appPath+'/partner/deviceAssess/counsels.do?method=search', isIframe : true},
                {id:'sheet-repairinfo',  text:'板件返修', url:eoms.appPath+'/partner/deviceAssess/repairinfos.do', isIframe : true},
                {id:'sheet-softupinfo',  text:'软件升级', url:eoms.appPath+'/partner/deviceAssess/softupinfos.do?method=search', isIframe : true},
                {id:'sheet-lserveinfo',  text:'现场服务', url:eoms.appPath+'/partner/deviceAssess/lserveinfos.do?method=search', isIframe : true},
                {id:'sheet-ftraininfo',  text:'厂家培训', url:eoms.appPath+'/partner/deviceAssess/ftraininfos.do', isIframe : true},
                {id:'sheet-peventinfo',  text:'工程服务', url:eoms.appPath+'/partner/deviceAssess/peventinfos.do', isIframe : true}
            ]
        };
       	_tabs = new eoms.TabPanel("sheet-detail-page",tabConfig);
    });
/**
 * TabPanel控件
 *@version 1.0
 */

/**
 * @param el tabPanel容器ID
 * @param config 设置
 * @cfg items.id 元素ID
 * @cfg items.text tab文本
 * @cfg items.url tab载入的页面URL
 * @cfg items.params tab载入的页面URL的参数
 * @cfg items.loadOnce 是否载入一次
 * @cfg items.isIframe 是否是iframe
 * @cfg items.hidden 是否隐藏
 */
eoms.TabPanel = function(el,config){
	this.el = Ext.get(el);
	
	this.tabPanel = new Ext.TabPanel(this.el,{resizeTabs:true,autoTabs:true});

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
				height:500,
				frameborder:0
			},false);		
		}
		else{
			this.el.createChild({
				tag:"iframe",
				id:id,
				name:id,
				width:"100%",
				height:500,
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
<div id="sheet-detail-page">
</div>

<%@ include file="/common/footer_eoms.jsp"%>