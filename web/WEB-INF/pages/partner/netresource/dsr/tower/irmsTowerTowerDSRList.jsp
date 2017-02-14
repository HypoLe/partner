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
	<b>直放站-数据同步-
	<font color="red">
		<c:if test="${synchType eq 'add'}">新增</c:if>
		<c:if test="${synchType eq 'delete'}">删除</c:if>
	</font>
	-列表页面</b>
</div><br><br/>

<!-- 同步结果开始 -->
 <div style="margin-top:20px;border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;">
 	最近一次同步时间：<font color="red">${dataSynchResult['datasynchTime']}</font>，本次同步共
		<c:if test="${synchType eq 'add'}">新增<font color="red">${dataSynchResult['dataSynchAddResultCount']}</font></c:if>
		<c:if test="${synchType eq 'delete'}">删除<font color="red">${dataSynchResult['dataSynchDeleteResultCount']}</font></c:if>
 	条数据。
 </div>
<!-- 同步结果结束 -->

<logic:present name="irmsTowerTowerDSRList" scope="request">
	<display:table id="irmsTowerTowerDSRList"
					name="irmsTowerTowerDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsTowerTowerDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="铁塔名称">
						${irmsTowerTowerDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔平台数量">
						${irmsTowerTowerDSRList['tower_platnum']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="已使用平台数量">
						${irmsTowerTowerDSRList['use_platnum']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属机房">
						${irmsTowerTowerDSRList['related_room']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔塔身高度">
						${irmsTowerTowerDSRList['tower_stature']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔类型">
						<eoms:id2nameDB id="${irmsTowerTowerDSRList['tower_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔厂家">
						${irmsTowerTowerDSRList['vendor']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔产权">
						<eoms:id2nameDB id="${irmsTowerTowerDSRList['tower_property_att']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="建塔日期">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔高度">
						${irmsTowerTowerDSRList['tower_height']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔设计承重">
						${irmsTowerTowerDSRList['design_bare_weight']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔实际承重">
						${irmsTowerTowerDSRList['real_bare_weight']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔代维公司">
						${irmsTowerTowerDSRList['manage_company']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共建">
						<eoms:id2nameDB id="${irmsTowerTowerDSRList['is_construct_share']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共建单位">
						${irmsTowerTowerDSRList['construct_company']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共享">
						<eoms:id2nameDB id="${irmsTowerTowerDSRList['is_shared']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共享单位">
						${irmsTowerTowerDSRList['shared_company']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
