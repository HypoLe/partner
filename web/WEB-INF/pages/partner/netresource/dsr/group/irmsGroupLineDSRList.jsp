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

<logic:present name="irmsGroupLineDSRList" scope="request">
	<display:table id="irmsGroupLineDSRList"
					name="irmsGroupLineDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupLineDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGroupLineDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupLineDSRList['related_instance']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入地址">
						${irmsGroupLineDSRList['customer_busi_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsGroupLineDSRList['business_bw']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						<eoms:id2nameDB id="${irmsGroupLineDSRList['tran_access_way']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备名称">
						${irmsGroupLineDSRList['related_cd_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备端口编号">
						${irmsGroupLineDSRList['related_cd_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动接入设备名称">
						${irmsGroupLineDSRList['related_ad_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动接入设备端口编号">
						${irmsGroupLineDSRList['related_ad_port_no']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入点设备VLAN编号">
						${irmsGroupLineDSRList['related_ad_vlan']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入点设备SLAN编号">
						${irmsGroupLineDSRList['related_ad_slan']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入点设备CLAN编号">
						${irmsGroupLineDSRList['related_ad_clan']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端机房名称">
						${irmsGroupLineDSRList['related_osr_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端设备名称">
						${irmsGroupLineDSRList['related_osd_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端设备端口">
						${irmsGroupLineDSRList['related_osd_port']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户终端IP地址（公网/私网）">
						${irmsGroupLineDSRList['related_client_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备互联IP地址">
						${irmsGroupLineDSRList['related_connect_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsGroupLineDSRList['related_circuit_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsGroupLineDSRList['related_oc_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入客户日期">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsGroupLineDSRList['remark']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
