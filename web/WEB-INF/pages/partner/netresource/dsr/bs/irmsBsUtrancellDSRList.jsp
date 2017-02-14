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

<logic:present name="irmsBsUtrancellDSRList" scope="request">
	<display:table id="irmsBsUtrancellDSRList"
					name="irmsBsUtrancellDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsUtrancellDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="OMC中网元名称">
						${irmsBsUtrancellDSRList['userlabel_cm']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="中文名称">
						${irmsBsUtrancellDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="小区码CI">
						${irmsBsUtrancellDSRList['ci']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="位置区码LAC">
						${irmsBsUtrancellDSRList['lac']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属NODEB">
						${irmsBsUtrancellDSRList['related_nodeb']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="服务器编码（SAC）">
						${irmsBsUtrancellDSRList['sac']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="TRX总数-Carrier总数">
						${irmsBsUtrancellDSRList['trx_nbr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="蜂窝类型">
						<eoms:id2nameDB id="${irmsBsUtrancellDSRList['cell_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="网元状态">
						<eoms:id2nameDB id="${irmsBsUtrancellDSRList['status']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="边界小区类型">
						<eoms:id2nameDB id="${irmsBsUtrancellDSRList['boundcell_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="覆盖场景">
						<eoms:id2nameDB id="${irmsBsUtrancellDSRList['cover_filed']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开通时间">
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
