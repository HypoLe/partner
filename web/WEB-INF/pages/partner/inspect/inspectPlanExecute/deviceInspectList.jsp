<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}

</script>
<c:if test="${transLine eq 'yes'}" var="result_1"></c:if>
<c:if test="${result_1}">
<div align="center">敷设点列表</div><br><br/>
<!-- Information hints area end-->
<logic:present name="deviceInspectList" scope="request">
	<display:table name="deviceInspectList" cellspacing="0" cellpadding="0"
		id="deviceInspectList" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectPlanExecute.do?method=goToDeviceInspectList" 
		partialList="true" size="${size}">
	
		<display:column  sortable="true"  title="区域">
			<c:if test="${empty deviceInspectList.tlpDis}">-</c:if>
			${deviceInspectList.tlpDis }
		</display:column>
		<display:column  sortable="true"  title="光缆系统"
					headerClass="sortable" >
					${deviceInspectList.tlpWire }
		</display:column>
		<display:column  sortable="true"  title="光缆段"
				headerClass="sortable" >
				${deviceInspectList.tlpSeg }
		</display:column>
		<display:column  sortable="true" property="tlpPAName" title="起点名称" />
		<display:column  sortable="true" property="tlpPALo" title="起点经度" />
		<display:column  sortable="true" property="tlpPALa" title="起点纬度" />
		<display:column  sortable="true" property="tlpPZName" title="终点名称" />
		<display:column  sortable="true" property="tlpPZLo" title="终点经度" />
		<display:column  sortable="true" property="tlpPZLa" title="终点纬度" />
		<display:column  sortable="true" title="是否必到点">
			<c:if test="${deviceInspectList.isMustArrive eq '1'}">
				<font color="red">是</font>
			</c:if>
			<c:if test="${deviceInspectList.isMustArrive ne '1'}">
				否
			</c:if>
		</display:column>
		<display:column  sortable="true" property="arrivedTime" title="到达时间" />
		<display:column  sortable="true" property="arrivedLo" title="到达经度" />
		<display:column  sortable="true" property="arrivedLa" title="到达纬度" />
		
		<display:column  sortable="true" title="敷设点来源">
			<c:if test="${deviceInspectList.tlpSource eq '0'}">
				段间点
			</c:if>
			<c:if test="${deviceInspectList.tlpSource eq '1'}">
				<font color="red">光缆段起点新增</font>
			</c:if>
			<c:if test="${deviceInspectList.tlpSource eq '2'}">
				<font color="red">光缆段终点新增</font>
			</c:if>
		</display:column>
		
		<display:column title="完成情况">
			<c:choose>
				<c:when test="${empty deviceInspectList.signStatus}">
					未完成
				</c:when>
				<c:when test="${ deviceInspectList.signStatus eq -1}">
					PC上填写
				</c:when>
				<c:when test="${ deviceInspectList.signStatus eq 0}">
					手机填写
				</c:when>
				<c:when test="${ deviceInspectList.signStatus eq 1}">
					不在范围之内
				</c:when>
				<c:when test="${ deviceInspectList.signStatus eq 2}">
					无坐标有照片
				</c:when>
				<c:when test="${ deviceInspectList.signStatus eq 3}">
					不在范围之内,但有图片
				</c:when>
			</c:choose>
		</display:column>
		<display:column title="设备巡检操作">
				<a href="${app}/partner/inspect/inspectPlanExecute.do?method=goToDeviceInspectPlanMainItemList&detail=${detail }&planResId=${deviceInspectList.planResId }&inspectTaskLinkId=${deviceInspectList.id }"><img src="${app }/images/icons/search.gif"></a>
		</display:column>
		
	</display:table>
</logic:present>
</c:if>
<c:if test="${!result_1}">
<div align="center">巡检设备（网络资源）列表</div><br><br/>
<!-- Information hints area end-->
<logic:present name="deviceInspectList" scope="request">
	<display:table name="deviceInspectList" cellspacing="0" cellpadding="0"
		id="deviceInspectList" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectPlanExecute.do?method=goToDeviceInspectList" 
		partialList="true" size="${size}">
	
		<display:column title="网络资源专业类型">
			${deviceInspectList.deviceSpecialtyName }
		</display:column>
		<display:column title="网络资源设备类型">
			${deviceInspectList.deviceTypeName }
		</display:column>
		<display:column title="网络资源名">
			${deviceInspectList.netresName }
		</display:column>
		<display:column title="设备巡检操作">
				<a href="${app}/partner/inspect/inspectPlanExecute.do?method=goToDeviceInspectPlanMainItemList&detail=${detail }&planResId=${deviceInspectList.planResId }&inspectTaskLinkId=${deviceInspectList.id }"><img src="${app }/images/icons/search.gif"></a>
		</display:column>
		
	</display:table>
</logic:present>
</c:if>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
