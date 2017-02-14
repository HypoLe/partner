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

<logic:present name="irmsTransOpcablesecDSRList" scope="request">
	<display:table id="irmsTransOpcablesecDSRList"
					name="irmsTransOpcablesecDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsTransOpcablesecDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="光缆段名称">
						${irmsWlanRepeaterDSRList['opcablesection_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属光缆">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_opcable']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="起始点名称">
						${irmsWlanRepeaterDSRList['start_point_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="末端点名称">
						${irmsWlanRepeaterDSRList['end_point_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光缆串通设施">
						${irmsWlanRepeaterDSRList['opcable_connect_facilities']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="纤芯数">
						${irmsWlanRepeaterDSRList['opcable_core_num']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光缆类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['opcable_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="生产厂家">
						${irmsWlanRepeaterDSRList['vendor']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产权">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['property']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="使用单位">
						${irmsWlanRepeaterDSRList['use_unit']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所有权人">
						${irmsWlanRepeaterDSRList['owner']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="用途">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['use']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="长度">
						${irmsWlanRepeaterDSRList['length']}
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
