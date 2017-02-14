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

<logic:present name="irmsWlanAcDSRList" scope="request">
	<display:table id="irmsWlanAcDSRList"
					name="irmsWlanAcDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsWlanAcDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="地市">
						${irmsWlanAcDSRList['related_district']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AC名称">
						${irmsWlanAcDSRList['ac_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备厂家">
						<eoms:id2nameDB id="${irmsWlanAcDSRList['related_vendor']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="硬件型号">
						${irmsWlanAcDSRList['hard_mode']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AC软件版本">
						${irmsWlanAcDSRList['soft_version']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="管理IP地址">
						${irmsWlanAcDSRList['manage_ip_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="上行互联设备端口">
						${irmsWlanAcDSRList['up_orig_dev_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="上行数据接口线速转发速率总和">
						${irmsWlanAcDSRList['up_dateinterface_s']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机柜编号">
						${irmsWlanAcDSRList['label_no']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入电源柜位置">
						${irmsWlanAcDSRList['insert_power_locat']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="工程状态">
						<eoms:id2nameDB id="${irmsWlanAcDSRList['ne_working_state']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备最大管理AP数量">
						${irmsWlanAcDSRList['max_manage_ap_coun']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AC_NAME">
						${irmsWlanAcDSRList['ac_name2']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AC NAS_IP">
						${irmsWlanAcDSRList['nas_ip']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="入网时间">
						${irmsWlanAcDSRList['setup_time']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="实际管理AP数量">
						${irmsWlanAcDSRList['ap_count']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsWlanAcDSRList['remark']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
