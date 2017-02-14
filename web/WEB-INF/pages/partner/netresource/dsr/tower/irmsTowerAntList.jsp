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

<logic:present name="irmsTowerAntDSRList" scope="request">
	<display:table id="irmsTowerAntDSRList"
					name="irmsTowerAntDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsTowerAntDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="天线名称">
						${irmsWlanRepeaterDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="总俯仰角">
						${irmsWlanRepeaterDSRList['sum_angle']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属小区">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['related_cellid']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="天线厂家">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['vendor']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="挂高">
						${irmsWlanRepeaterDSRList['high_cube']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="天线类型">
						<eoms:id2nameDB id="${irmsWlanRepeaterDSRList['ant_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="方位角">
						${irmsWlanRepeaterDSRList['maxazimuth_value']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电调下倾角">
						${irmsWlanRepeaterDSRList['adjust_angle_ele']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机械下倾角">
						${irmsWlanRepeaterDSRList['adjust_angle_mac']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="安装时间">
						${irmsWlanRepeaterDSRList['install_time']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="天面">
						${irmsWlanRepeaterDSRList['tian_mian']}
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
