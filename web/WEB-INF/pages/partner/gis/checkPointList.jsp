<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	Ext.onReady(function(){
		parent.Ext.get("left-panel-body").unmask();
		
		var cpJSONObj = Ext.util.JSON.decode('${cpListJSON}');
		jq("#btnShowAllCheckPoint").bind("click",showAllCheckPoint);
		jq("#btnCleanAllCheckPoint").bind("click",cleanAllCheckPoint);
		
		function showAllCheckPoint(e) {
			parent.frames["gis-frame"].paramConfig.cpJSONObj = cpJSONObj;
			parent.frames["gis-frame"].Ext.get("map").mask("正在加载网格，请稍等......");
			parent.frames["gis-frame"].setAllGridGraphics(cpJSONObj);
			parent.frames["gis-frame"].addAllGridGraphics();
			parent.frames["gis-frame"].Ext.get("map").unmask();
		}
		
		function cleanAllCheckPoint(e) {
			parent.frames["gis-frame"].cleanAllGridGraphics();
		}
		pageAdjust();
		//cleanAllCheckPoint(null);
});

//界面修饰
function pageAdjust() {
		var page = jq(".pagelinks");
		var table = jq("#checkPointList");
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
						<input type="button" value="显示所有巡检点" id="btnShowAllCheckPoint" class="btn"/> 
					</td>
					<td class="label">
						<input type="button" value="清除所有巡检点" id="btnCleanAllCheckPoint" class="btn"/> 
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<%--
	private String id;
	private String resourceCode;//资源点编码
	private String resourceName;//资源点名称
	private String address;//地址
	private String type;//类型
	private String longitude;//经度
	private String latitude;//纬度
	private String cableSegment;//所属光缆段
	private String cableSystem;//所属光缆系统
	private String checkPointSegmentId;//所属巡检段
	private String importantFocus;//重要关注点
	private String isCheckPoint;//是否为检查点    
--%>
<!-- Information hints area end-->
<logic:present name="checkPointList" scope="request">
	<display:table name="checkPointList" cellspacing="0" cellpadding="0" 
		id="checkPointList" pagesize="${pagesize}"
		class="table checkPointList" export="false"
		requestURI="gisReq.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="资源点名称">
			${checkPointList.resourceName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="类型">
			<eoms:id2nameDB id="${checkPointList.type}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="所属巡检段">
			<eoms:id2nameDB id="${checkPointList.checkPointSegmentId}" beanId="checkSegmentDao" />
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			<a href="${app }/checkpoint/checkpoint.do?method=goToEdit&id=${checkPointList.id }" target="_blank">
				<img src="${app }/images/icons/edit.gif">
			</a>
		</display:column>
	</display:table>
</logic:present>
	
	</div>
</div>


<%@ include file="/common/footer_eoms.jsp"%>
