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
		params :{resourceName:"IrmsGroupSound"},
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
		params :{resourceName:"IrmsGroupSound"},
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
<div align="center"><b>语音专线信息表-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGroupSound" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGroupSound'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.group.IrmsGroupSound" 
							 serviceBeanId="eomsService"   title="网络资源--集客家客--语音专线信息表" >
		<eoms:row name="客户名称" value="customerName"/>
		<eoms:row name="产品实例标识" value="relatedInstance"/>
		<eoms:row name="码号段表" value="codeSection"/>
		<eoms:row name="来话权限" value="incomingCallPermissions"/>
		<eoms:row name="信令方式" value="signalingPattern"/>
		<eoms:row name="业务带宽(速率)" value="businessBw"/>
		<eoms:row name="中继数" value="relayNum"/>
		<eoms:row name="传输接入方式" value="tranAccessWay"/>
		<eoms:row name="客户业务接入地址" value="customerBusAddr"/>
		<eoms:row name="客户端客户设备名称" value="relatedSdName"/>
		<eoms:row name="客户端客户设备端口编号" value="relatedSdPort"/>
		<eoms:row name="移动接入设备名称" value="relatedAdName"/>
		<eoms:row name="移动接入设备端口编号" value="relatedAdPort"/>
		<eoms:row name="城域网设备名称" value="relatedLanName"/>
		<eoms:row name="城域网设备端口编号" value="relatedLanPort"/>
		<eoms:row name="汇聚语音交换机设备名称" value="convergedName"/>
		<eoms:row name="汇聚语音交换机端口编号" value="convergedPort"/>
		<eoms:row name="局端机房名称" value="relatedOrName"/>
		<eoms:row name="局端设备名称" value="relatedOrDevice"/>
		<eoms:row name="局端设备端口" value="relatedOrDevicePort"/>
		<eoms:row name="电路名称" value="relatedCircuitName"/>
		<eoms:row name="光路名称" value="relatedOcName"/>
		<eoms:row name="客户业务应用IP地址（公网/私网）" value="clientBusAppIp"/>
		<eoms:row name="接入客户日期" value="customerAcsDate"/>
		<eoms:row name="备注" value="remark"/>
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

<logic:present name="irmsGroupSoundList" scope="request">
	<display:table id="irmsGroupSoundList"
					name="irmsGroupSoundList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupSoundList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGroupSoundList.customerName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupSoundList.relatedInstance}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="码号段表">
						${irmsGroupSoundList.codeSection}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="来话权限">
						<eoms:id2nameDB id="${irmsGroupSoundList.incomingCallPermissions}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="信令方式">
						<eoms:id2nameDB id="${irmsGroupSoundList.signalingPattern}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsGroupSoundList.businessBw}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="中继数">
						${irmsGroupSoundList.relayNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						<eoms:id2nameDB id="${irmsGroupSoundList.tranAccessWay}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入地址">
						${irmsGroupSoundList.customerBusAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备名称">
						${irmsGroupSoundList.relatedSdName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户端客户设备端口编号">
						${irmsGroupSoundList.relatedSdPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动接入设备名称">
						${irmsGroupSoundList.relatedAdName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动接入设备端口编号">
						${irmsGroupSoundList.relatedAdPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="城域网设备名称">
						${irmsGroupSoundList.relatedLanName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="城域网设备端口编号">
						${irmsGroupSoundList.relatedLanPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="汇聚语音交换机设备名称">
						${irmsGroupSoundList.convergedName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="汇聚语音交换机端口编号">
						${irmsGroupSoundList.convergedPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端机房名称">
						${irmsGroupSoundList.relatedOrName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端设备名称">
						${irmsGroupSoundList.relatedOrDevice}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局端设备端口">
						${irmsGroupSoundList.relatedOrDevicePort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsGroupSoundList.relatedCircuitName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsGroupSoundList.relatedOcName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务应用IP地址（公网/私网）">
						${irmsGroupSoundList.clientBusAppIp}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入客户日期">
						${irmsGroupSoundList.customerAcsDate}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsGroupSoundList.remark}
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
