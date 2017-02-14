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

<logic:present name="irmsBsCellDSRList" scope="request">
	<display:table id="irmsBsCellDSRList"
					name="irmsBsCellDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsCellDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="OMC中网元名称">
						${irmsWlanRepeaterDSRList['userlabel_cm']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="中文名称">
						${irmsWlanRepeaterDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="小区码CI">
						${irmsWlanRepeaterDSRList['ci']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="位置区码LAC">
						${irmsWlanRepeaterDSRList['lac']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动通信系统标识">
						${irmsWlanRepeaterDSRList['gsmdcsindicator']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属BTS">
						${irmsWlanRepeaterDSRList['related_btssite_cu']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="蜂窝类型">
						${irmsWlanRepeaterDSRList['cell_type']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="网元状态">
						${irmsWlanRepeaterDSRList['status']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="边界小区类型">
						${irmsWlanRepeaterDSRList['boundcell_type']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否带直放站">
						${irmsWlanRepeaterDSRList['if_repeator']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="支持EGPRS">
						${irmsWlanRepeaterDSRList['is_enegprs']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="覆盖场景">
						${irmsWlanRepeaterDSRList['cover_filed']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="TRX数量">
						${irmsWlanRepeaterDSRList['trx_count']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开通时间">
						${irmsWlanRepeaterDSRList['open_time']}
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
