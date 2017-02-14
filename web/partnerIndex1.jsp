<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <title>主页面首页</title>
	    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	    
	    <script type="text/javascript" charset="utf-8" src="${app}/scripts/base/eoms.js"></script>
  		<script type="text/javascript">eoms.appPath = "${app}";</script>
	    <%@ include file="/common/extlibs.jsp"%>
		<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
		<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
		<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
	    <link type="text/css" rel="Stylesheet" href="css/partnerIndex/main1.css" />
		<script type="text/javascript">
		var jq=jQuery.noConflict();
		</script>
		
	    <style type="text/css">
			
	    </style>
	</head>
	<body scroll="no">
		<div id="container">
			<div id="main-content">
				<div id="sidebar-left">
					<div class="sidebar-left-1">
					  <h2>为您服务</h2>
					  <div class="service_main">
					    <ul>
					      <li><span><img src="images/partnerIndex/images/main_icon_1.png"  align="absmiddle" /></span><a href="#">新建业务联系函</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_2.png"  align="absmiddle" /></span><a href="#">新建巡检任务</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_3.png"  align="absmiddle" /></span><a href="#">新建通用任务工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_4.png"  align="absmiddle" /></span><a href="#">新建故障工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_5.png"  align="absmiddle" /></span><a href="#">新建资源核查工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_6.png"  align="absmiddle" /></span><a href="#">新建隐患上报工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_7.png"  align="absmiddle" /></span><a href="#">新建考核</a></li>
					    </ul>
					  </div>
					</div>
				</div>
				
				<div id="sidebar-right">
					<div class="sidebar-right-list">
			            <div class="sbr-box">
			            	<div class="sbr-box-con">
				              <h2>
				              		<span style="float: right;padding-right: 1em;display: block;">
				              			<a target="_blank" href="home/publish.do?method=getList&listType=2">更多</a>
				              		</span>
				              		公告
				              </h2>
				              <div class="sbr-box-list" id="gongGaoDIV" style="height:150px;">
				              </div>
				             </div>
			            </div>
			            
			            <div class="sbr-box">
			            	<div class="sbr-box-con" style="background: url(images/partnerIndex/images/title_bg_2.png) repeat-x;">
				              <h2 style="background: url(images/partnerIndex/images/icon_2.png) no-repeat;">附助功能</h2>
				              <div class="sbr-box-list" style="height:150px;">
				                <ul class="sbr-box-ul">
				                  <li><a target="_blank" href="${app}/partner/arcGis/arcGis.do?method=goToArcGis">电子地图 </a></li>
				                </ul>
				              </div>
				             </div>
			            </div>
            
          			</div>
				</div>
				
				<div id="content">
					<div class="undojob-tabs">
						<ul class="ui-tabs-nav box-top">
							<li class="btl">
									<span class="ui-tabs-li bt-text ui-tabs-selected">我的巡检任务</span>
							</li>							
							<li class="btr">							
							</li>
							<li class="btc">
								<span class="ui-tabs-li btl bt-text">我的待办工单</span>
								<span class="ui-tabs-li btl bt-text">业务联第函</span>
								<span class="btc-more">
									<span class="bt-text" style="float:right;"><a href="partner/inspect/inspectPlanExecute.do?method=findInspectPlanMainByCheckUserList" target="_blank">更多</a></span>
								</span>
							</li>
						</ul>
						<!-- 巡检任务 begin -->
						<div id="xjrwDiv" class="box-content ui-tabs-panel" style="height:220px;">
							<table width="98%" height="92%" cellpadding="0" cellspacing="0">
								<thead>
								    <tr>
										<th class="title">巡检资源</td>
										<th class="title">代维小组</td>
										<th class="title">巡检开始时间</td>
										<th class="title">巡检结束时间</td>
										<th class="title">查看</td>
								    </tr>
							 	</thead>
							 	<tbody>
							 	</tbody>
							</table>
						</div>
						<!-- 巡检任务 end -->
						<!-- 待办工单 begin -->
						<div id="gongdanDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:220px;">
							<iframe id="gongdanFrame" name="gongdanFrame" frameborder="0" width="100%" height="220px" 
										src="${app}/partner/mainPage/pnrHomePageAction.do?method=getEomsUndoAllSheetList" scrolling="yes"></iframe>
						</div>
						<!-- 待办工单 end -->
						<!-- 业务联系函 begin -->
						<div id="ywlxhDiv" class="box-content ui-tabs-panel ui-tabs-hide" style="height:220px;">
							<table width="98%" height="92%" cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<th class="title">文号</td>
										<th class="title">发布人</td>
										<th class="title">主题</td>
										<th class="title">处理时限</td>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
						<!-- 业务联系函 begin -->
					</div>
					
					
					
					<!-- 计次汇总 begin -->
					<div class="box">
						<div class="box-top">
							<div class="btl">
								<div class="btl-bg">
									<span class="bt-text">计次汇总</span>
								</div>
							</div>							
							<div class="btr">
								
							</div>
							<div class="btc">
								
							</div>
						</div>
						<div id="jchzDIV" class="box-content">
							<iframe id="jchzFrame" name="jchzFrame" frameborder="0" width="100%" height="220px" 
										src="${app}/partner/mainPage/pnrHomePageAction.do?method=pnrResMeteringSearch" scrolling="yes"></iframe>
						</div>
					</div>
					<!-- 计次汇总 end -->
				</div>
				
			</div>
			
		</div>
		
		
		<div id="footer" style="position:fixed; bottom:0;right:0;width:100%">
					<span class="text" style="float:left;margin-left:80px;">
						代维服务热线:18954519455  E-mail:lizhong@boco.com.cn    
					</span>
					<span class="text" style="margin-top:1px;background:url('images/partnerIndex/cut-line.png') no-repeat;padding-left:20px;float:right">
						版权所有  亿阳信通
					</span>
			</div>
	</body>

<script type="text/javascript">
  jq(function() {
      jq(".ui-tabs-nav .ui-tabs-li").bind("click",function(e) {
          if (e.target == this) {
              var tabs = jq(".ui-tabs-nav .ui-tabs-li");
              var panels = jq(".ui-tabs-panel");
              var moreTexts = jq(".btc-more");
              var index = jq.inArray(jq(this)[0], tabs);
              if (panels.eq(index)[0]) {
                  tabs.removeClass("ui-tabs-selected")
                      .eq(index).addClass("ui-tabs-selected");
                  panels.addClass("ui-tabs-hide")
                      .eq(index).removeClass("ui-tabs-hide");
                  moreTexts.addClass("ui-hide")
                      .eq(index).removeClass("ui-hide");
              }
          }
      });
  });
</script>

<script type="text/javascript">
loadYWLXHData();
loadXJRWData();
loadGongGaoData();

function addStyle(id) {
   jq('#'+id+' table tbody tr:odd').addClass('odd');
   jq('#'+id+' table tbody tr:even').addClass('even'); 
   jq('table td:contains("Because of you")').addClass('highlight');
}

/*获取公告数据*/
function loadGongGaoData() {
	Ext.Ajax.timeout=30000;
	Ext.get("gongGaoDIV").mask("正在加载公告...");
	Ext.Ajax.request({
		url : '${app}/partner/mainPage/pnrHomePageAction.do?method=getModuleData&type=publish',
		/*params :{resourceName:""},*/
		method : 'post',
		isUpload:true,
		success : function(response, options) {
				var gongGao = response.responseText || '';
				jq("#gongGaoDIV").html(gongGao);
			    Ext.get("gongGaoDIV").unmask();
		},
		failure : function() {
			    Ext.get("gongGaoDIV").unmask();
		}
	});
}
function loadYWLXHData() {
	Ext.Ajax.timeout=30000;
	Ext.get("ywlxhDiv").mask("正在加载业务联系函数据...");
	Ext.Ajax.request({
		url : '${app}/partner/mainPage/mainPageAction.do?method=loadYWLXHData',
		/*params :{resourceName:""},*/
		method : 'post',
		isUpload:true,
		success : function(response, options) {
				var result = response.responseText || '{}';
				var json = eval("("+result+")");
				
				if(json.success == "true") {
					jq("#ywlxhDiv table tbody").html(json.data);
				} 
			    Ext.get("ywlxhDiv").unmask();
			    addStyle("ywlxhDiv");
		},
		failure : function() {
			    Ext.get("ywlxhDiv").unmask();
		}
	});
}
/**工单*/
function loadGongDanData() {
	Ext.Ajax.timeout=30000;
	Ext.get("gongdanDIV").mask("正在加工单数据...");
	window.frames['gongdanFrame'].location.reload();
}
/**巡检任务*/
function loadXJRWData() {
	Ext.Ajax.timeout=30000;
	Ext.get("xjrwDiv").mask("正在加巡检任务数据...");
	Ext.Ajax.request({
		url : '${app}/partner/mainPage/mainPageAction.do?method=loadXJRWData',
		/*params :{resourceName:""},*/
		method : 'post',
		isUpload:true,
		success : function(response, options) {
				var result = response.responseText || '{}';
				var json = eval("("+result+")");
				
				if(json.success == "true") {
					jq("#xjrwDiv table tbody").html(json.data);
				} 
			    Ext.get("xjrwDiv").unmask();
			    addStyle("xjrwDiv");
		},
		failure : function() {
			    Ext.get("xjrwDiv").unmask();
		}
	});
}
/**计次汇总*/
function loadJCHZ() {
	Ext.Ajax.timeout=30000;
	Ext.get("jchzDIV").mask("正在加载计次数据...");
	window.frames['jchzFrame'].location.reload();
}
 </script>
</html>
