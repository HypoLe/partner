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
	Ext.onReady(function(){
		parent.Ext.get("left-panel-body").unmask();
		
		var csJSONObj = Ext.util.JSON.decode('${csListJSON}');
		jq("#btnShowAllCheckSegment").bind("click",showAllCheckSegment);
		jq("#btnCleanAllCheckSegment").bind("click",cleanAllCheckSegment);
		jq("#checkSegmentList tbody tr").bind("mouseover",function(e) {
			var button = jq(this).find("input:button");
			var btnId = button.attr("id");
			
			var index = btnId.substring("btn_".length,btnId.length);
			var dataId = jq("#dataId_"+index).val();
			parent.frames["gis-frame"].addCheckSegment(dataId);
			
			if(button.val() == "显示") {
				jq(this).addClass("trMouseOver");
			} else {
			}
		});
		
		jq("#checkSegmentList tbody tr").bind("mouseout",function(e) {
			var button = jq(this).find("input:button");
			var btnId = button.attr("id");
			
			var index = btnId.substring("btn_".length,btnId.length);
			var dataId = jq("#dataId_"+index).val();
			
			if(button.val() == "显示") {
				parent.frames["gis-frame"].removeCheckSegment(dataId);
				jq(this).removeClass("trMouseOver");
			} else {
			}
		});
		
		//设置巡检段图层
		parent.frames["gis-frame"].setSegmentGraphics(csJSONObj);
		
		function showAllCheckSegment(e) {
			parent.frames["gis-frame"].paramConfig.csJSONObj = csJSONObj;
			parent.frames["gis-frame"].Ext.get("map").mask("正在加载巡检段，请稍等......");
			parent.frames["gis-frame"].addAllCheckSegmentGraphics();
			parent.frames["gis-frame"].Ext.get("map").unmask();
		}
		
		function cleanAllCheckSegment(e) {
			parent.frames["gis-frame"].cleanAllCheckSegmentGraphics();
		}
		
		window.addCheckSegment = function(btnId,id) {
			var value = jq("#"+btnId).val();
			if("显示" == value) {
				jq("#"+btnId).parents("tr").addClass("trMouseOver");
				parent.frames["gis-frame"].addCheckSegment(id);
				jq("#"+btnId).val("清除");
			} else {
				jq("#"+btnId).parents("tr").removeClass("trMouseOver");
				parent.frames["gis-frame"].removeCheckSegment(id);
				jq("#"+btnId).val("显示");
			}
		}
		
		cleanAllCheckSegment();
		pageAdjust();
});

//界面修饰
function pageAdjust() {
		var page = jq(".pagelinks");
		var table = jq("#checkSegmentList");
		page.remove();
		table.after(page);
		table.after("<br/><br/>");
} 


</script>
<script type="text/javascript">
	function openImport(handler){
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
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">操作</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="gisReq.do?method=searchCheckPoint" method="post">
		<fieldset>
			<legend>地图操作</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">
						<input type="button" value="显示所有巡检段" id="btnShowAllCheckSegment" class="btn"/> 
					</td>
					<td class="label">
						<input type="button" value="清除所有巡检点段" id="btnCleanAllCheckSegment" class="btn"/> 
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<div id="testDiv"></div>
<!-- Information hints area end-->
<logic:present name="checkSegmentList" scope="request">
	<display:table name="checkSegmentList" cellspacing="0" cellpadding="0"
		id="checkSegmentList" pagesize="${pagesize}"
		class="table checkSegmentList" export="false"
		requestURI="gisReq.do" sort="list"
		partialList="true" size="${size}">
		<display:column media="html" title="操作">
				 <input type="hidden" id="dataId_${checkSegmentList_rowNum }" value="${checkSegmentList.id}"/>
				 <input id="btn_${checkSegmentList_rowNum }" type="button" class="btn" name="btnAddCheckSegment" value="显示"  onclick="addCheckSegment('btn_${checkSegmentList_rowNum }','${checkSegmentList.id}')"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="巡检段名称">
			${checkSegmentList.segmentName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="开始标示">
			${checkSegmentList.startFlag}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="结束标示">
			${checkSegmentList.endFlag}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="巡检段类型">
			
			<eoms:id2nameDB id="${checkSegmentList.segmentType}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="编辑" media="html">
			<a href="${app }/checkSegment/checkSegmentAction.do?method=goToEdit&id=${checkSegmentList.id }" target="_blank">
				<img src="${app }/images/icons/edit.gif">
			</a>
		</display:column>
	</display:table>
</logic:present>

<%@ include file="/common/footer_eoms.jsp"%>
