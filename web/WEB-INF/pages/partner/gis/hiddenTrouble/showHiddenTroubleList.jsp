<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#B5BCC7;
	}
</style>
<script type="text/javascript">

var jq=jQuery.noConflict();
var htJSONObj=[];
Ext.onReady(function(){
		parent.Ext.get("left-panel-body").unmask();
		
		htJSONObj = eval('('+'${htListJSON}'+')');
		jq("#btnShowAllHiddenTrouble").bind("click",showAllHiddenTrouble);
		jq("#btnCleanAllHiddenTrouble").bind("click",cleanAllHiddenTrouble);
		
		if(parent.frames["gis-frame"] != undefined && parent.frames["gis-frame"].document.readyState=="complete") {
			parent.frames["gis-frame"].setAllHiddenTroubleGraphics(htJSONObj);
		}
		
		function showAllHiddenTrouble(e) {
			parent.frames["gis-frame"].Ext.get("map").mask("正在加载隐患点，请稍等......");
			parent.frames["gis-frame"].setAllHiddenTroubleGraphics(htJSONObj);
			parent.frames["gis-frame"].addAllHiddenTroubleGraphics();
			parent.frames["gis-frame"].Ext.get("map").unmask();
		}
		
		function cleanAllHiddenTrouble(e) {
			parent.frames["gis-frame"].cleanAllHiddenTroubleGraphics();
		}
		
		pageAdjust();
		//cleanAllHiddenTrouble(null);
		
		
		jq("#hiddenTroubleList tbody tr").bind("mouseover",function(e) {
			jq(this).addClass("trMouseOver");
		});
		
		jq("#hiddenTroubleList tbody tr").bind("mouseout",function(e) {
			jq(this).removeClass("trMouseOver");
		});
		
		jq("#hiddenTroubleList tbody tr").bind("click",function(e) {
			var siteId = jq(this).find("input:hidden").val();
			parent.frames["gis-frame"].locationHiddenTrouble(siteId);
		});
});

//界面修饰
function pageAdjust() {
		var page = jq(".pagelinks");
		var table = jq("#hiddenTroubleList");
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
	<form action="gisHiddenTroubleReq.do?method=showHiddenTroubleList" method="post" id="searchForm" name="searchForm">
			<fieldset>
			<legend>操作选项</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td colspan="2"><div class="ui-widget-header" >地图操作</div></td>
				</tr>
				
				<tr>
					<td class="label">
						<input type="button" value="显示所有隐患点" id="btnShowAllHiddenTrouble" class="btn"/> 
					</td>
					<td class="label">
						<input type="button" value="清除所有隐患点" id="btnCleanAllHiddenTrouble" class="btn"/> 
					</td>
				</tr>
				
				<tr>
					<td colspan="2"><div class="ui-widget-header" >隐患查询</div></td>
				</tr>
				
				<tr>
					<td class="label">隐患类型:</td>
					<td class="label">
						<eoms:comboBox name="hiddenTroubleTypeStringEqual" defaultValue="${hiddenTrouble.hiddenTroubleType }"
							id="hiddenTroubleType" initDicId="1200201" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
				
				<tr>
					<td class="label">
						地区:
					</td>
					<td >
						<input class="text" name="textArea" id="textArea" type="text"  readonly="true"  alt="allowBlank:false" /> 
						<input type="hidden" name="areaIdStringEqual" id="areaIdStringEqual" />
						<eoms:xbox   id="tree1" dataUrl="${app}/partner/baseinfo/xtree.do?method=area" 
			 				 rootId="24" 
			  				 rootText='四川省' 
			  				 valueField="areaIdStringEqual"
			  				 handler="textArea"
			  				 textField="textArea"
			 				 checktype="area" 
			 				 single="true"></eoms:xbox>
					</td>
				</tr>
				
				<tr>
					<td class="label">
						状态:
					</td>
					<td >
						<select name="processStatusStringEqual" id="processStatusStringEqual" alt="allowBlank:true" class="select">
							<option value="">请选择</option>
							<option value="0">未处理</option>
							<option value="1">已处理</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/partner/gis/gisHiddenTroubleReq.do?method=showHiddenTroubleList'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!-- Information hints area end-->
<logic:present name="hiddenTroubleList" scope="request">
	<display:table   name="hiddenTroubleList" cellspacing="0" cellpadding="0"
		id="hiddenTroubleList" pagesize="${pagesize}" 
		class="table hiddenTroubleList" export="false" requestURI="gisHiddenTroubleReq.do"
		sort="list" partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="上报时间">
			${hiddenTroubleList.reportTime}<input type="hidden" id="dataId_${hiddenTroubleList_rowNum }" value="${hiddenTroubleList.id}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="上报巡检员">
			<%--<eoms:id2nameDB id="${hiddenTroubleList.checkUser}"
				beanId="partnerUserDao" />
			--%>
			<eoms:id2nameDB id="${hiddenTroubleList.checkUser}" beanId="tawSystemUserDao" /> 
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="处理状态">
			<c:if test="${hiddenTroubleList.processStatus == '0'}">
				未处理
			</c:if>
			<c:if test="${hiddenTroubleList.processStatus == '1'}">
				已处理
			</c:if>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="编辑" media="html">
				<a href="${app }/hiddenTrouble/hiddenTrouble.do?method=goToEdit&id=${hiddenTroubleList.id}" target="_blank"
					><img src="${app }/images/icons/edit.gif"></a>
		</display:column>

		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${hiddenTroubleList.id }"
				href="${app }/hiddenTrouble/hiddenTrouble.do?method=goToDetail&id=${hiddenTroubleList.id}"
				target="blank" shape="rect"> <img
					src="${app }/images/icons/table.gif"> </a>
		</display:column>
	</display:table>
</logic:present>
<%@ include file="/common/footer_eoms.jsp"%>
