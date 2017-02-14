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

function openExcelImport(){
	var handler = document.getElementById("openExcelHandle");
	var el = Ext.get('listExcelPanel');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开手动同步界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭手动同步界面";
	}
}

function excelFileUpload() {
	var file1 = document.getElementById("excelFile").value;
	if(file1.match("(.xls)$") == null) {
		alert("请选择excel文件上传！");
		return;
	} else {
		 jq("#excelUploadBtn").attr("disabled","disabled");
	}

	Ext.Ajax.timeout=30000;
	//Ext.get("listExcelPanel").mask("正在上传...");
	Ext.Ajax.request({
		url : '${app}/netresource/synchExcel2XmlAction.do?method=uploadExcelFile',
		params :{resourceName:"IrmsBsGrnerat2"},
		method : 'post',
		form:'excel2xmlForm',
		isUpload:true,
		success : function(response, options) {
				var result = response.responseText || '{}';
				var json = eval("("+result+")");
				var info = json.info;
				var msg = json.message;
				if(info == "true") {
					Ext.MessageBox.alert('提示：', msg,function(){document.getElementById("excelFile").value=""});
				} else {
				    Ext.MessageBox.alert('提示：', msg,function(){document.getElementById("excelFile").value=""});
				}
			    //Ext.get("listExcelPanel").unmask();
		},
		failure : function() {
			    //Ext.get("listExcelPanel").unmask();
		}
	});
}
function synchDataFileUpload() {
	var file1 = document.getElementById("synchDataFile").value;
	if(file1.match("(.zip)$") == null) {
		alert("请选择zip数据文件上传！");
		return;
	}

	Ext.Ajax.timeout=30000;
	Ext.get("listExcelPanel").mask("正在上传...");
	Ext.Ajax.request({
		url : '${app}/netresource/synchExcel2XmlAction.do?method=uploadSynchDataFile',
		params :{resourceName:"IrmsBsGrnerat2"},
		method : 'post',
		form:'synchDataForm',
		isUpload:true,
		success : function(response, options) {
				var result = response.responseText || '{}';
				var json = eval("("+result+")");
				var info = json.info;
				var msg = json.message;
				if(info == "true") {
					Ext.MessageBox.alert('提示：', msg,function(){document.getElementById("synchDataFile").value=""});
				} else {
				    Ext.MessageBox.alert('提示：', msg,function(){document.getElementById("synchDataFile").value=""});
				}
			    Ext.get("listExcelPanel").unmask();
		},
		failure : function() {
			    Ext.get("listExcelPanel").unmask();
		}
	});
}
</script>
<div align="center"><b>开关电源-列表页面</b></div><br><br/>

<c:if test="${openHandleSynch eq 'true'}">
<div>
	<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openExcelImport();">
		<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
		<span id="openExcelHandle">Excel转xml导入</span>
	</div>
	
	<div id="listExcelPanel" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;display:none">
		<form id="excel2xmlForm" name="excel2xmlForm" action="${app}/netresource/synchExcel2XmlAction.do?method=uploadExcelFile" method="post" enctype="multipart/form-data">
			<fieldset>
				<legend>Excel转xml导入</legend>
				<table class="formTable">
						<tr>
							<td class="label">
							 	上传Excel生成xml文件
							</td>
							<td class="content" colspan="3">
									<input type="file" name="excelFile" id="excelFile" />
							</td>
						</tr>
					<tr>
						<td colspan="4">
							<div align="left">
								<input id="excelUploadBtn" type="button" class="btn" value="确认上传" onclick="excelFileUpload();" />
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
		<form id="synchDataForm" name="synchDataForm" action="${app}/netresource/synchExcel2XmlAction.do?method=uploadExcelFile" method="post" enctype="multipart/form-data">
			<input type="hidden" name="aaa" value="bbb" />
			<fieldset>
				<legend>同步数据文件导入</legend>
				<table class="formTable">
						<tr>
							<td class="label">
							 	上传同步数据文件
							</td>
							<td class="content" colspan="3">
									<input type="file" width="300px" name="synchDataFile" id="synchDataFile" />
							</td>
						</tr>
					<tr>
						<td colspan="4">
							<div align="left">
								<input type="button" class="btn" value="确认上传" onclick="synchDataFileUpload();" />
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div><br><br/>
</c:if>

<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery">查询</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;display:none">
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.IrmsBsGrnerat2" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	机房名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedRoomStringEqual" value="${relatedRoomStringEqual}" />
						</td>
						
						
						<td class="label">
						 	设备类型
						</td>
						<td class="content" >
								<eoms:comboBox id="deviceType" name="deviceTypeStringEqual" initDicId="11228022611" defaultValue="${deviceTypeStringEqual}" styleClass="input select" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	品牌
						</td>
						<td class="content" colspan="3">
								<input class="text" type="text" name="trademarkStringLike" value="${trademarkStringLike}" />
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.IrmsBsGrnerat2'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.bs.IrmsBsGrnerat2" 
							 serviceBeanId="eomsService"   title="网络资源--基站设备及配套--开关电源" >
		<eoms:row name="机房名称" value="relatedRoom"/>
		<eoms:row name="设备类型" value="deviceType"/>
		<eoms:row name="设备子类" value="deviceSubclass"/>
		<eoms:row name="设备名称" value="labelDev"/>
		<eoms:row name="设备编码" value="deviceSequence"/>
		<eoms:row name="系统编号" value="sysNo"/>
		<eoms:row name="资产编号" value="equipmentcode"/>
		<eoms:row name="型号" value="model"/>
		<eoms:row name="生产厂商" value="relatedVendor"/>
		<eoms:row name="品牌" value="trademark"/>
		<eoms:row name="供应商" value="supplier"/>
		<eoms:row name="代维公司" value="manageCompany"/>
		<eoms:row name="开始使用时间" value="startTime"/>
		<eoms:row name="预计报废时间" value="endTime"/>
		<eoms:row name="使用状态" value="status"/>
		<eoms:row name="额定输出电压功率（A）" value="expRatingVoltage"/>
		<eoms:row name="开关电源类型" value="smpsType"/>
		<eoms:row name="监控模块型号" value="monitorModel"/>
		<eoms:row name="现有整流模块总数" value="commModelNum"/>
		<eoms:row name="整流模块型号" value="commModel"/>
		<eoms:row name="整流模块额定工作电压(V)" value="commModelVoltage"/>
		<eoms:row name="单个整流模块额定输出容量（A）" value="commModelExpCap"/>
		<eoms:row name="单组电池组熔丝容量" value="batteryCapability"/>
		<eoms:row name="电池组熔丝组数" value="batteryEleccapGr"/>
		<eoms:row name="能否二次下电" value="isUnderSecond"/>
		<eoms:row name="维护负责人" value="preserver"/>
		<eoms:row name="是否共建" value="isConstructShare"/>
		<eoms:row name="共建单位" value="constructCompany"/>
		<eoms:row name="是否共享" value="isShared"/>
		<eoms:row name="共享单位" value="sharedCompany"/>
</eoms:excelExport>
--%>
<!--导出功能结束-->

<!-- 同步结果开始 -->
 <div style="margin-top:20px;border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;">
 	最近一次同步时间：<font color="red">${dataSynchResult['datasynchTime']}</font>，
 	本次同步共
 	删除
 		<a target="_blank" href="${app}/netresource/pnrNetResourceAction.do?method=gotoDataSynchResultPage&datasynch_result_id=${dataSynchResult['dataSynchAddResultId']}&model=${model}&synchType=delete"><font color="red">${dataSynchResult['dataSynchDeleteResultCount']}</font></a>
 	条数据，
 	新增
 		<a target="_blank" href="${app}/netresource/pnrNetResourceAction.do?method=gotoDataSynchResultPage&datasynch_result_id=${dataSynchResult['dataSynchDeleteResultId']}&model=${model}&synchType=add"><font color="red">${dataSynchResult['dataSynchAddResultCount']}</font></a>
 	条数据。
 </div>
<!-- 同步结果结束 -->

<logic:present name="irmsBsGrnerat2List" scope="request">
	<display:table id="irmsBsGrnerat2List"
					name="irmsBsGrnerat2List" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsGrnerat2List" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="机房名称">
						${irmsBsGrnerat2List.relatedRoom}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备类型">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.deviceType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备子类">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.deviceSubclass}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备名称">
						${irmsBsGrnerat2List.labelDev}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备编码">
						${irmsBsGrnerat2List.deviceSequence}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="系统编号">
						${irmsBsGrnerat2List.sysNo}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="资产编号">
						${irmsBsGrnerat2List.equipmentcode}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="型号">
						${irmsBsGrnerat2List.model}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="生产厂商">
						${irmsBsGrnerat2List.relatedVendor}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="品牌">
						${irmsBsGrnerat2List.trademark}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供应商">
						${irmsBsGrnerat2List.supplier}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="代维公司">
						${irmsBsGrnerat2List.manageCompany}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开始使用时间">
						${irmsBsGrnerat2List.startTime}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="预计报废时间">
						${irmsBsGrnerat2List.endTime}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="使用状态">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.status}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="额定输出电压功率（A）">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.expRatingVoltage}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开关电源类型">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.smpsType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="监控模块型号">
						${irmsBsGrnerat2List.monitorModel}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="现有整流模块总数">
						${irmsBsGrnerat2List.commModelNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="整流模块型号">
						${irmsBsGrnerat2List.commModel}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="整流模块额定工作电压(V)">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.commModelVoltage}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="单个整流模块额定输出容量（A）">
						${irmsBsGrnerat2List.commModelExpCap}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="单组电池组熔丝容量">
						${irmsBsGrnerat2List.batteryCapability}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电池组熔丝组数">
						${irmsBsGrnerat2List.batteryEleccapGr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="能否二次下电">
						${irmsBsGrnerat2List.isUnderSecond}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护负责人">
						${irmsBsGrnerat2List.preserver}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共建">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.isConstructShare}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共建单位">
						${irmsBsGrnerat2List.constructCompany}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共享">
						<eoms:id2nameDB id="${irmsBsGrnerat2List.isShared}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共享单位">
						${irmsBsGrnerat2List.sharedCompany}
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
