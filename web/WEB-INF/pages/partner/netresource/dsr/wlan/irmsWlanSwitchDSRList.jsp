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

<logic:present name="irmsWlanSwitchDSRList" scope="request">
	<display:table id="irmsWlanSwitchDSRList"
					name="irmsWlanSwitchDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsWlanSwitchDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="地市">
						${irmsWlanSwitchDSRList['related_district']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="交换机名称">
						${irmsWlanSwitchDSRList['label_cn']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备厂家">
						<eoms:id2nameDB id="${irmsWlanSwitchDSRList['related_vendor_cui']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="硬件型号">
						${irmsWlanSwitchDSRList['model']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="交换机软件版本">
						${irmsWlanSwitchDSRList['soft_version']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="管理IP地址">
						${irmsWlanSwitchDSRList['manage_ip_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="上行互联设备端口">
						${irmsWlanSwitchDSRList['related_up_orig_port_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="上行端口类型">
						<eoms:id2nameDB id="${irmsWlanSwitchDSRList['up_port_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="交换机端口数量">
						${irmsWlanSwitchDSRList['sw_port_count']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="交换机所属热点">
						${irmsWlanSwitchDSRList['related_hotpot_cui']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所在机房">
						${irmsWlanSwitchDSRList['related_room_cuid']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="工程状态">
						${irmsWlanSwitchDSRList['status']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="交换机所属网络层次">
						<eoms:id2nameDB id="${irmsWlanSwitchDSRList['net_level']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="归属汇聚层交换机">
						${irmsWlanSwitchDSRList['related_tsw_dev_cu']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="归属汇聚层交换机端口">
						${irmsWlanSwitchDSRList['related_tsw_dev_po']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备最大接入AP数量">
						${irmsWlanSwitchDSRList['max_access_ap_coun']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="最大可接入的接入层交换机数量">
						${irmsWlanSwitchDSRList['max_access_sw_coun']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="入网时间">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="实际接入的交换机或AP数量">
						${irmsWlanSwitchDSRList['real_access_ap_cou']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="实际接入的接入层交换机数量">
						${irmsWlanSwitchDSRList['real_aceess_sw_cou']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsWlanSwitchDSRList['remark']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
