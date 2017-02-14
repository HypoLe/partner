<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#B5BCC7;
	}
</style>

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

var jq=jQuery.noConflict();
var historyListJSON=[];
Ext.onReady(function(){
		var v = new eoms.form.Validation({form:'searchForm'});
		setSearchConditions();
		parent.Ext.get("left-panel-body").unmask();
		
		historyListJSON = eval('('+'${historyListJSON}'+')');
		jq("#btnShowAllHistory").bind("click",showAllHistory);
		jq("#btnCleanAllHistory").bind("click",cleanAllHistory);
		
		if(parent.frames["gis-frame"] != undefined) {
			parent.frames["gis-frame"].setAllHistoryGraphics(historyListJSON);
		}
		
		function showAllHistory(e) {
			//parent.frames["gis-frame"].paramConfig.historyListJSON = historyListJSON;
			parent.frames["gis-frame"].Ext.get("map").mask("正在加载巡检历史数据，请稍等......");
			parent.frames["gis-frame"].setAllHistoryGraphics(historyListJSON);
			parent.frames["gis-frame"].addAllHistoryGraphics();
			parent.frames["gis-frame"].Ext.get("map").unmask();
		}
		
		function cleanAllHistory(e) {
			parent.frames["gis-frame"].cleanAllHistoryGraphics();
		}
		
		pageAdjust();
		//cleanAllHistory(null);
		
		
		jq("#historyList tbody tr").bind("mouseover",function(e) {
			jq(this).addClass("trMouseOver");
		});
		
		jq("#historyList tbody tr").bind("mouseout",function(e) {
			jq(this).removeClass("trMouseOver");
		});
		
		jq("#historyList tbody tr").bind("click",function(e) {
			var siteId = jq(this).find("input:hidden").val();
			parent.frames["gis-frame"].locationHistory(siteId);
		});
});
//设置查询条件
function setSearchConditions() {
	var paramRegion = "${paramRegion}";
	var year = "${year}";
	var month = "${month}";
	if(paramRegion != "") {
		setSelectedOptionDefaultValue("region",paramRegion);
		requestCountryOnCityChange();
	}
	if(year != "") {
		setSelectedOptionDefaultValue("year",year);
	}
	if(month != "") {
		setSelectedOptionDefaultValue("month",month);
	}
}
//设置下拉框默认值
function setSelectedOptionDefaultValue(id,value) {
	document.getElementById(id).value = value;
}
//界面修饰
function pageAdjust() {
		var page = jq(".pagelinks");
		var table = jq("#historyList");
		page.remove();
		table.after(page);
		table.after("<br/><br/>");
} 

function openSearch(handler){
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开操作界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭操作界面";
	}
}

function delAllOption(elementid){
    var ddlObj = document.getElementById(elementid);//获取对象
     for(var i=ddlObj.length-1;i>=0;i--){
         ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
    }
}

//地市变化 联动县区
function requestCountryOnCityChange() {  
	delAllOption("city");
	var region = document.getElementById("region").value;
	var url="${app}/partner/inspect/ajaxRequest.do?method=requestCountryOnCityChange&region="+region;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
			res = result.responseText;
			var json = eval(res);
			var obj=$("city");
			for(i=0;i<json.length;i++){
				var opt=new Option(json[i].areaName,json[i].areaId);
				obj.options[i]=opt;
			}
			var paramCity = "${paramCity}";
			if(paramCity != "") {
				setSelectedOptionDefaultValue("city",paramCity);
			}
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/layout_add.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">操作面板</span>
</div>

<div id="listQueryObject" 
	 style="display:block;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="gisHistoryReq.do?method=showHistoryList" method="post" id="searchForm" name="searchForm">
			<fieldset>
			<legend>操作选项</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td colspan="2"><div class="ui-widget-header" >地图操作</div></td>
				</tr>
				
				<tr>
					<td class="label">
						<input type="button" value="本页历史数据" id="btnShowAllHistory" class="btn"/> 
					</td>
					<td class="label">
						<input type="button" value="清除本页数据" id="btnCleanAllHistory" class="btn"/> 
					</td>
				</tr>
				
				<tr>
					<td colspan="2"><div class="ui-widget-header" >查询条件</div></td>
				</tr>   
				
				<tr>
					<td class="label">
						查询日期
					</td>
					<td class="content">
						<select id="year" name="year" alt="allowBlank:false,vtext:'选择年'">
							<option value="">选择年</option>
							<option value="2000">2000</option>
							<option value="2001">2001</option>
							<option value="2002">2002</option>
							<option value="2003">2003</option>
							<option value="2004">2004</option>
							<option value="2005">2005</option>
							<option value="2006">2006</option>
							<option value="2007">2007</option>
							<option value="2008">2008</option>
							<option value="2009">2009</option>
							<option value="2010">2010</option>
							<option value="2011">2011</option>
							<option value="2012">2012</option>
							<option value="2013">2013</option>
							<option value="2014">2014</option>
							<option value="2015">2015</option>
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							<option value="2019">2019</option>
							<option value="2020">2020</option>
						</select>年
						<select id="month" name="month" alt="allowBlank:false,vtext:'选择月'">
							<option value="">选择月</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</select>月
					</td>
					
				<tr>
					<td class="label">
						维护人员
					</td>
					<td >
						<eoms:xbox id="inspectUserTree" dataUrl="${app}/xtree.do?method=userFromDept" 
								  	rootId="-1" 
								  	rootText="人员选择" 
								  	valueField="inspectUser" handler="inspectUserName"
									textField="inspectUserName"
									checktype="user" single="true"	
									data=''></eoms:xbox>
						<input type="hidden" id="inspectUser" name="inspectUser" value="${inspectUser}"  />	
						<input type="text" name="inspectUserName" id="inspectUserName" alt="allowBlank:true,vtext:'选择维护人员'" 
						 	   value="<eoms:id2nameDB id="${inspectUser}" beanId="tawSystemUserDao" />" 
						       class="text maxsize" readonly="true" />
						</select>
					</td>
				</tr>
		
				<tr>
					<!-- 所属地市 -->
					<td class="label">
						所属地市:
					</td>
					<td >
						<!-- 地市 -->			
						<select name="region" id="region"
							alt="allowBlank:true,vtext:'请选择所在地市'"
							onchange="requestCountryOnCityChange();">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:present name="region">
							<logic:iterate id="region" name="region">
								<option value="${region.areaid}">
									${region.areaname}
								</option>
							</logic:iterate>
							</logic:present>
						</select>
					</td>
				</tr>
		
				<tr>
					<!-- 所属县区 -->
					<td class="label">
						所属县区:
					</td>
					<td>
						<!-- 县区 -->			
						<select name="city" id="city" 
							alt="allowBlank:true,vtext:'请选择所在县区'">
							<option value="">
								--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<br/>
<display:table name="historyList" cellspacing="0" cellpadding="0"
	id="historyList" pagesize="${pageSize}" class="table historyList"
	export="false" 
	requestURI="${app}/partner/gis/gisHistoryReq.do?method=showHistoryList"
	sort="list" partialList="true" size="resultSize" >
	<display:column  headerClass="sortable" title="站点名称">
			${historyList.siteName }<input type="hidden" id="dataId_${historyList_rowNum }" value="${historyList.id}"/>
	</display:column>
	<display:column property="deviceNum" sortable="true"
			headerClass="sortable" title="设备数"  />
	<display:column property="itemNum" sortable="true"
			headerClass="sortable" title="巡检项数" />
	<display:column  property="taskStateName" sortable="true" 
			headerClass="sortable" title="巡检状态" />	
	<display:column  sortable="true" headerClass="sortable" title="维护人员">
		<eoms:id2nameDB id="${historyList.inspectUser}" beanId="tawSystemUserDao" />
	</display:column>			
	<display:column sortable="true" headerClass="sortable" title="查看">
		 <a href="../inspect/inspectTasks.do?method=showDeviceTaskDo&id=${showItemTaskList.id}" target="_blank">查看</a>
	</display:column>
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>
