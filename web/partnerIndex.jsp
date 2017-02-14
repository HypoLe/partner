<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        <link type="text/css" rel="Stylesheet" href="styles/${theme}/typo.css" />
		<script type="text/javascript">
		var jq=jQuery.noConflict();
		</script>
		<script type="text/javascript">
			//函数说明：合并指定表格（表格id为_w_table_id）指定列（列数为_w_table_colnum）的相同文本的相邻单元格
			//参数说明：_w_table_id 为需要进行合并单元格的表格的id。如在HTMl中指定表格 id="data" ，此参数应为 #data 
			//参数说明：_w_table_colnum 为需要合并单元格的所在列。为数字，从最左边第一列为1开始算起。
			function _w_table_rowspan(_w_table_id,_w_table_colnum){
			    _w_table_firsttd = "";
			    _w_table_currenttd = "";
			    _w_table_SpanNum = 0;
			    _w_table_Obj = jq(_w_table_id + " tr td:nth-child(" + _w_table_colnum + ")");
			    _w_table_Obj.each(function(i){
			        if(i==0){
			            _w_table_firsttd = jq(this);
			            _w_table_SpanNum = 1;
			        }else{
			            _w_table_currenttd = jq(this);
			            if(_w_table_firsttd.text()==_w_table_currenttd.text()){
			                _w_table_SpanNum++;
			                _w_table_currenttd.hide(); //remove();
			                _w_table_firsttd.attr("rowSpan",_w_table_SpanNum);
			            }else{
			                _w_table_firsttd = jq(this);
			                _w_table_SpanNum = 1;
			            }
			        }
			    });
			}
			//函数说明：合并指定表格（表格id为_w_table_id）指定行（行数为_w_table_rownum）的相同文本的相邻单元格
			//参数说明：_w_table_id 为需要进行合并单元格的表格id。如在HTMl中指定表格 id="data" ，此参数应为 #data 
			//参数说明：_w_table_rownum 为需要合并单元格的所在行。其参数形式请参考jQuery中nth-child的参数。
			//          如果为数字，则从最左边第一行为1开始算起。
			//          "even" 表示偶数行
			//          "odd" 表示奇数行
			//          "3n+1" 表示的行数为1、4、7、10.......
			//参数说明：_w_table_maxcolnum 为指定行中单元格对应的最大列数，列数大于这个数值的单元格将不进行比较合并。
			//          此参数可以为空，为空则指定行的所有单元格要进行比较合并。
			function _w_table_colspan(_w_table_id,_w_table_rownum,_w_table_maxcolnum){
			    if(_w_table_maxcolnum == void 0){_w_table_maxcolnum=0;}
			    _w_table_firsttd = "";
			    _w_table_currenttd = "";
			    _w_table_SpanNum = 0;
			    jq(_w_table_id + " tr:nth-child(" + _w_table_rownum + ")").each(function(i){
			        _w_table_Obj = jq(this).children();
			        _w_table_Obj.each(function(i){
			            if(i==0){
			                _w_table_firsttd = jq(this);
			                _w_table_SpanNum = 1;
			            }else if((_w_table_maxcolnum>0)&&(i>_w_table_maxcolnum)){
			                return "";
			            }else{
			                _w_table_currenttd = jq(this);
			                if(_w_table_firsttd.text()==_w_table_currenttd.text()){
			                    _w_table_SpanNum++;
			                    _w_table_currenttd.hide(); //remove();
			                    _w_table_firsttd.attr("colSpan",_w_table_SpanNum);
			                }else{
			                    _w_table_firsttd = jq(this);
			                    _w_table_SpanNum = 1;
			                }
			            }
			        });
			    });
			}
			//函数说明：合并指定表格（表格id为_w_table_id）指定列（行数大于_w_table_mincolnum 小于_w_table_maxcolnum）相同列中的相同文本的相邻单元格
			//          多于一列时，后一列的单元格合并范围不能超过前一列的合并范围。避免出现交错。
			//参数说明：_w_table_id 为需要进行合并单元格的表格id。如在HTMl中指定表格 id="data" ，此参数应为 #data 
			//参数说明：_w_table_mincolnum 为需要进行比较合并的起始列数。为数字，则从最左边第一行为1开始算起。
			//          此参数可以为空，为空则第一列为起始列。
			//          需要注意，如果第一列为序号列，则需要排除此列。
			//参数说明：_w_table_maxcolnum 为需要进行比较合并的最大列数，列数大于这个数值的单元格将不进行比较合并。
			//          为数字，从最左边第一列为1开始算起。
			//          此参数可以为空，为空则同_w_table_mincolnum。
			function _w_table_lefttitle_rowspan(_w_table_id,_w_table_mincolnum,_w_table_maxcolnum){   
			 if(_w_table_mincolnum == void 0){_w_table_mincolnum=1;}
			 if(_w_table_maxcolnum == void 0){_w_table_maxcolnum=_w_table_mincolnum;}
			 if(_w_table_mincolnum>_w_table_maxcolnum){
			  return "";
			 }else{
			  var _w_table_splitrow=new Array();
			  for(iLoop=_w_table_mincolnum;iLoop<=_w_table_maxcolnum;iLoop++){
			   _w_table_onerowspan(iLoop);
			  }
			 }
			 
			 function _w_table_onerowspan(_w_table_colnum){
			  _w_table_firstrow = 0;//前一列合并区块第一行
			  _w_table_SpanNum = 0;//合并单元格时的，单元格Span个数
			  _w_table_splitNum = 0;//数组的_w_table_splitrow的当前元素下标
			  _w_table_Obj = jq(_w_table_id + " tr td:nth-child(" + _w_table_colnum + ")");
			  _w_table_curcol_rownum = _w_table_Obj.length-1;//此列最后一行行数
			  if(_w_table_splitrow.length==0){_w_table_splitrow[0] = _w_table_curcol_rownum;}
			  _w_table_lastrow = _w_table_splitrow[0];//前一列合并区块最后一行
			  var _w_table_firsttd;
			  var _w_table_currenttd;
			  var _w_table_curcol_splitrow=new Array();
			  _w_table_Obj.each(function(i){
			   if(i==_w_table_firstrow){
			    _w_table_firsttd = jq(this);
			    _w_table_SpanNum = 1;
			   }else{
			    _w_table_currenttd = jq(this);
			    if(_w_table_firsttd.text()==_w_table_currenttd.text()){
			     _w_table_SpanNum++;
			     _w_table_currenttd.hide(); //remove();
			     _w_table_firsttd.attr("rowSpan",_w_table_SpanNum); 
			    }else{
			     _w_table_firsttd = jq(this);
			     _w_table_SpanNum = 1;
			     setTableRow(i-1);
			    }
			    if(i==_w_table_lastrow){setTableRow(i);}
			   }
			   function setTableRow(_splitrownum){
			    if(_w_table_lastrow<=_splitrownum&&_w_table_splitNum++<_w_table_splitrow.length){
			     //_w_table_firstrow=_w_table_lastrow+1;
			     _w_table_lastrow=_w_table_splitrow[_w_table_splitNum];
			    }
			    _w_table_curcol_splitrow[_w_table_curcol_splitrow.length]=_splitrownum;
			    if(i<_w_table_curcol_rownum){_w_table_firstrow=_splitrownum+1;}
			   }
			  });
			  _w_table_splitrow=_w_table_curcol_splitrow;
			 }
			}
		</script>
		
	    <style type="text/css">
			
	    </style>
	</head>
	<body>
		<div id="container">
			<div id="main-content">
				<div id="sidebar-left">
					<div class="sidebar-left-1">
					  <h2>常用功能</h2>
					  <div class="service_main">
					    <ul>
					   <!--  <li><span><img src="images/partnerIndex/images/main_icon_1.png"  align="absmiddle" /></span><a target="_blank" href="${app }/contact/contact.do?method=getJsp&pageName=draftsPage">新建业务联系函</a></li> -->  
					      <li><span><img src="images/partnerIndex/images/main_icon_2.png"  align="absmiddle" /></span><a target="_blank" href="${app }/partner/inspect/inspectPlan.do?method=toSaveInspectPlanMain">新建巡检任务</a></li>
                            <li><span><img src="images/partnerIndex/images/main_icon_9.png"  align="absmiddle" /></span><a target="_blank" href="${app }/partner/inspect/inspectPlanExecute.do?method=findInspectPlanMainByCheckUserList">巡检执行进度</a></li>
                            <li><span><img src="images/partnerIndex/images/main_icon_4.png"  align="absmiddle" /></span><a target="_blank" href="${app }/activiti/pnrTaskTicket/pnrTaskTicket.do?method=showNewSheetPage">新建任务工单</a></li>
                            <li><span><img src="images/partnerIndex/images/main_icon_5.png"  align="absmiddle" /></span><a target="_blank" href="${app }/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=showNewSheetPage">新建故障工单</a></li>
                            <li><span><img src="images/partnerIndex/images/main_icon_6.png"  align="absmiddle" /></span><a target="_blank" href="${app }/activiti/pnrTaskTicket/pnrTaskTicket.do?method=listBacklog">任务待办工单</a></li>
                            <li><span><img src="images/partnerIndex/images/main_icon_7.png"  align="absmiddle" /></span><a target="_blank" href="${app }/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=listBacklog&definitionKey=troubleShooting">故障待办工单</a></li>
					     <!--  <li><span><img src="images/partnerIndex/images/main_icon_3.png"  align="absmiddle" /></span><a target="_blank" href="${app }/sheet/pnrcommontask/pnrcommontask.do?method=showNewSheetPage">新建通用任务工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_4.png"  align="absmiddle" /></span><a target="_blank" href="${app }/sheet/pnrfaultdeal/pnrfaultdeal.do?method=showNewSheetPage">新建故障工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_5.png"  align="absmiddle" /></span><a target="_blank" href="${app }/sheet/pnrrescheck/pnrrescheck.do?method=showNewSheetPage">新建资源核查工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_6.png"  align="absmiddle" /></span><a target="_blank" href="${app }/sheet/pnrhiddentrouble/pnrhiddentrouble.do?method=showNewSheetPage">新建隐患上报工单</a></li>
					      <li><span><img src="images/partnerIndex/images/main_icon_7.png"  align="absmiddle" /></span><a target="_blank" href="${app }/partner/evaluation/evaluationEntity.do?method=starMonthEntity">新建考核</a></li> -->  
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
				                  <!-- <li><a target="" href="${app}/partner/arcGis/arcGis.do?method=goToArcGis">电子地图 </a></li> -->
				                <li><a target="_blank" href="sdcode.html">手机大厅二维码</a></li>
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
								<span class="ui-tabs-li btl bt-text">巡检完成情况</span>
                                <span class="ui-tabs-li btl bt-text">归档工单统计</span>
                                <span class="ui-tabs-li btl bt-text">在途工单统计</span>
                               <!--  <span class="ui-tabs-li btl bt-text">巡检异常情况</span>
                                <span class="ui-tabs-li btl bt-text">任务工单</span>
                                <span class="ui-tabs-li btl bt-text">故障工单</span> -->

								<!-- 
								<span class="ui-tabs-li btl bt-text">我的待办工单</span>
								<span class="ui-tabs-li btl bt-text">待办业务联系函</span>-->
								<span class="btc-more">
									<span class="bt-text" style="float:right;"><a href="partner/inspect/inspectPlanExecute.do?method=findInspectPlanMainByCheckUserList" target="_blank">更多</a></span>
								</span>
							</li>
						</ul>
						<!-- 巡检任务 begin -->
						<div id="xjrwDiv" class="box-content ui-tabs-panel" style="height:220px;">
							<table class="table list" width="98%" height="92%" cellpadding="0" cellspacing="0">
								<thead>
								    <tr>
										<th >巡检计划名称</td>
										<th >巡检专业</td>
										<th >巡检单位</td>
										<th >巡检日期</td>
										<th >查看</td>
								    </tr>
							 	</thead>
							 	<tbody>
							 	</tbody>
							</table>
						</div>
						<!-- 巡检任务 end -->
						
						<!-- 巡检情况 begin  -->
						<div id="xunJianQingKuangDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:430px;">
							<iframe id="xunJianQingKuangList" name="tjxunJianQingKuangList" frameborder="0" style="width:100%;height:100%" src="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCount"></iframe>
						</div>
						<!-- 巡检情况 end -->
						
						<!-- 巡检异常情况 begin -->
					<!--<div id="xunJianYiChangQingKuangDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:430px;">
							<iframe id="xunJianYiChangQingKuangList" name="xunJianYiChangQingKuangList" frameborder="0" style="width:100%;height:100%" src="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCount"></iframe>
						</div> -->
                        <!-- 巡检异常情况 end -->
                        <!-- 工单统计 begin -->
                        <div id="taskTicketStatisticsDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:430px;">
                            <iframe id="taskTicketStatisticsList" name="taskTicketList" frameborder="0" style="width:100%;height:100%" src="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatistics2"></iframe>
                        </div>
                        <!-- 工单统计 end -->
                        <!-- 在途工单统计 begin -->
                        <div id="taskTicketStatisticsDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:430px;">
                            <iframe id="taskTicketStatisticsList" name="taskTicketList" frameborder="0" style="width:100%;height:100%" src="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatistics3"></iframe>
                        </div>
                        <!-- 在途工单统计 end -->
                        <!-- 任务工单 begin -->
                      <!--<div id="taskTicketDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:430px;">
                            <iframe id="taskTicketList" name="taskTicketList" frameborder="0" style="width:100%;height:100%" src="${app}/activiti/pnrTaskTicket/pnrTaskTicket.do?method=listDeptProcessInstances"></iframe>
                        </div>-->
                        <!-- 任务工单 end -->
                        <!-- 故障工单 begin -->
                      <!--<div id="troubleTicketDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:430px;">
                            <iframe id="troubleTicketList" name="troubleTicketList" frameborder="0" style="width:100%;height:100%" src="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=listDeptProcessInstances"></iframe>
                        </div> -->
                        <!-- 故障工单 end -->
						<!-- 待办工单 begin
						<div id="gongdanDIV" class="box-content ui-tabs-panel ui-tabs-hide" style="height:220px;">
							<iframe id="gongdanFrame" name="gongdanFrame" frameborder="0" width="100%" height="220px" 
										src="${app}/sheet/alllist/eomsallsheetlist.do?method=getUndoAllLists" scrolling="yes"></iframe>
						</div>  -->
						<!-- 待办工单 end -->
						<!-- 业务联系函 begin-->
						<div id="ywlxhDiv" class="box-content ui-tabs-panel ui-tabs-hide" style="height:220px;">
							<table width="98%" height="92%" cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<th width="25%" class="title">文号</td>
										<th width="15%" class="title">发布人</td>
										<th width="40%" class="title">主题</td>
										<th width="20%" class="title">处理时限</td>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div> 
						<!-- 业务联系函 begin -->
					</div>
					<c:if test="${fn:startsWith(sessionScope.sessionform.deptid, sessionScope.sessionform.rootPnrDeptId)}" var="result">
						<!-- 计次汇总 begin -->
						<div class="box"style="height:430px;">
						<!-- 
							<div class="box-top">
							
								<div class="btl">
									<div class="btl-bg">
										<span class="bt-text"></span>
									</div>
								</div>							
								<div class="btr">
									
								</div>
								<div class="btc">
									<span class=""></span>
								</div>
							</div>
							<div id="tj" class="box-content" style="height:400px;">
							<iframe id="tjList" name="tjList" frameborder="no" style="width:100%;height:100%" src="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCount"></iframe>
							
							</div>
							<div id="xjycqk" class="box-content" style="height:400px;">
							<iframe id="tjList" name="tjList" frameborder="no" style="width:100%;height:100%" src="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCount"></iframe>
							
							</div>
							 -->
						</div>
						<!-- 计次汇总 end -->
					</c:if>
				
					<c:if test="${!result}">
						<!-- 计次汇总 begin -->
						
						<div class="box"style="height:430px;">
						<!-- 
							<div class="box-top">
								
								<div class="btl">
									<div class="btl-bg">
										<span class="bt-text">巡检情况</span>
									</div>
								</div>							
								<div class="btr">
									
								</div>
								<div class="btc">
									<span class="">
									</span>
								</div>
								
							</div>
							<div id="tj" class="box-content" style="height:400px;">
							<iframe id="tjList" name="tjList" frameborder="no" style="width:100%;height:100%" src="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResCount"></iframe>
							
							</div>
							 -->
						</div>
					<!-- 计次汇总 end -->
					</c:if>
					
					
					
				</div>
				
			</div>
		</div>
		<div id="footer" style="width:100%;padding: 10px 0px 0 0px;">
				<span class="text" style="float:left;margin-left:80px;">
					服务热线:13370591006  E-mail:lizhong@boco.com.cn
				</span>
				<span class="text" style="margin-top:1px;background:url('images/partnerIndex/cut-line.png') no-repeat;padding-left: 20px;float: right;margin-right: 100px;">
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
//              if (index == 2 && panels.eq(index)[0]) {
//                  jq(".btc-more").removeClass("ui-hide");
//                  jq(".btc-more span a")[0].href="contact/contact.do?method=search&type=todo";
//                  jq(".btc-more span a")[0].target="_blank";
//              }
          }
      });
  });
</script>

<script type="text/javascript">
loadYWLXHData();
loadXJRWData();
loadGongGaoData();
//loadSheetStatistics();

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


/**工单统计*/
function loadSheetStatistics() {
	Ext.Ajax.timeout=30000;
	Ext.get("jchzDIV").mask("正在加载工单统计数据...");
	Ext.Ajax.request({
		url : '${app}/partner/mainPage/pnrHomePageAction.do?method=getModuleData&type=sheet_statistics',
		/*params :{resourceName:""},*/
		method : 'post',
		isUpload:true,
		success : function(response, options) {
				var result = response.responseText || '';
				jq("#jchzDIV").html(result);
			    Ext.get("jchzDIV").unmask();
			    addStyle("jchzDIV");
			    _w_table_lefttitle_rowspan("#jchzDIV",1,11);  
			    for(var i=11;i>=1;i--) {
				    _w_table_rowspan("#jchzDIV",i);
			    }
			    
		},
		failure : function() {
			    Ext.get("jchzDIV").unmask();
		}
	});
}
 </script>
 

</html>
