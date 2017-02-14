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

<logic:present name="irmsGroupDeviceDSRList" scope="request">
	<display:table id="irmsGroupDeviceDSRList"
					name="irmsGroupDeviceDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupDeviceDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="设备名称">
						${irmsGroupDeviceDSRList['device_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备序列号">
						${irmsGroupDeviceDSRList['device_serial_num']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属地市">
						${irmsGroupDeviceDSRList['device_city']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属区县">
						${irmsGroupDeviceDSRList['device_county']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属乡镇街道">
						${irmsGroupDeviceDSRList['device_town']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属机房">
						${irmsGroupDeviceDSRList['device_room']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="厂家名称">
						${irmsGroupDeviceDSRList['device_vendor']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="规则型号">
						${irmsGroupDeviceDSRList['device_model']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="状态">
						${irmsGroupDeviceDSRList['device_status']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产权单位">
						${irmsGroupDeviceDSRList['device_property_unit']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="使用单位">
						${irmsGroupDeviceDSRList['device_use_unit']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护单位">
						${irmsGroupDeviceDSRList['device_manage_unit']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="IP地址">
						${irmsGroupDeviceDSRList['device_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="版本信息">
						${irmsGroupDeviceDSRList['device_version']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="入网时间">
						${irmsGroupDeviceDSRList['device_access_net_date']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="固定资产编号">
						${irmsGroupDeviceDSRList['device_fixed_asset_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupDeviceDSRList['related_iinstance']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备类型">
						${irmsGroupDeviceDSRList['client_device_type']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备端口类型">
						${irmsGroupDeviceDSRList['client_device_port_type']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备端口编号">
						${irmsGroupDeviceDSRList['client_device_port_id']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="端口状态">
						${irmsGroupDeviceDSRList['device_port_status']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="互联IP地址">
						${irmsGroupDeviceDSRList['inerconnect_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端设备VLAN">
						${irmsGroupDeviceDSRList['client_device_vlan']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端设备MAC地址">
						${irmsGroupDeviceDSRList['client_device_mac']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务接入点A经度">
						${irmsGroupDeviceDSRList['pointa_longitude']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务接入点A纬度">
						${irmsGroupDeviceDSRList['pointa_latitude']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>