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

<logic:present name="irmsBsUpsDSRList" scope="request">
	<display:table id="irmsBsUpsDSRList"
					name="irmsBsUpsDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsUpsDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="机房（基站）名称">
						${irmsBsUpsDSRList['related_room']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备类型">
						<eoms:id2nameDB id="${irmsBsUpsDSRList['device_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备子类">
						<eoms:id2nameDB id="${irmsBsUpsDSRList['device_subclass']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备名称">
						${irmsBsUpsDSRList['label_dev']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备编码">
						${irmsBsUpsDSRList['device_sequence']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="资产编号">
						${irmsBsUpsDSRList['equipmentcode']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="系统编号">
						${irmsBsUpsDSRList['systemcode']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="型号">
						${irmsBsUpsDSRList['model']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="生产厂商">
						${irmsBsUpsDSRList['related_vendor']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="品牌">
						${irmsBsUpsDSRList['trademark']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供应商">
						${irmsBsUpsDSRList['supplier']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="代维公司">
						${irmsBsUpsDSRList['manage_company']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开始使用时间">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="预计报废时间">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="使用状态">
						<eoms:id2nameDB id="${irmsBsUpsDSRList['status']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="额定功率（KVA）">
						${irmsBsUpsDSRList['rating_power']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="额定工作电压（V）">
						<eoms:id2nameDB id="${irmsBsUpsDSRList['rating_voltage']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="工作方式">
						<eoms:id2nameDB id="${irmsBsUpsDSRList['work_mode']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护负责人">
						${irmsBsUpsDSRList['preserver']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
