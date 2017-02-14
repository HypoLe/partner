<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

<script type="text/javascript">
function openImport(){
	var handler = document.getElementById("openQuery");
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

<div align="center">
	直放站-数据同步-
	<font color="red">
		<c:if test="${synchType eq 'add'}">新增</c:if>
		<c:if test="${synchType eq 'delete'}">删除</c:if>
	</font>
	-列表页面
</div><br><br/>

<!-- 同步结果开始 -->
 <div style="margin-top:20px;border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;">
 	最近一次同步时间：<font color="red"><font color="red">${dataSynchResult['datasynchTime']}</font>，本次同步共
		<c:if test="${synchType eq 'add'}">新增<font color="red">${dataSynchResult['dataSynchAddResultCount']}</font></c:if>
		<c:if test="${synchType eq 'delete'}">删除<font color="red">${dataSynchResult['dataSynchDeleteResultCount']}</font></c:if>
 	个站点。
 </div>
<!-- 同步结果结束 -->

<logic:present name="irmsTowerTowerDSRList" scope="request">
	<display:table id="irmsTowerTowerDSRList"
					name="irmsTowerTowerDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsTowerTowerDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="铁塔名称">
						${irmsWlanRepeaterDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔平台数量">
						${irmsWlanRepeaterDSRList['tower_platnum']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="已使用平台数量">
						${irmsWlanRepeaterDSRList['use_platnum']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属机房">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_room']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔塔身高度">
						${irmsWlanRepeaterDSRList['tower_stature']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['tower_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔厂家">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['vendor']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔产权">
						${irmsWlanRepeaterDSRList['tower_property_att']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="建塔日期">
						${irmsWlanRepeaterDSRList['buildtime']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔高度">
						${irmsWlanRepeaterDSRList['tower_height']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔设计承重">
						${irmsWlanRepeaterDSRList['design_bare_weight']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔实际承重">
						${irmsWlanRepeaterDSRList['real_bare_weight']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔代维公司">
						${irmsWlanRepeaterDSRList['manage_company']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共建">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['is_construct_share']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共建单位">
						${irmsWlanRepeaterDSRList['construct_company']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共享">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['is_shared']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共享单位">
						${irmsWlanRepeaterDSRList['shared_company']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
