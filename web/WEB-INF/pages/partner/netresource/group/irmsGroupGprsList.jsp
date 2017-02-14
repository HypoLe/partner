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
		params :{resourceName:"IrmsGroupGprs"},
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
		params :{resourceName:"IrmsGroupGprs"},
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
<div align="center"><b>GPRS信息表-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGroupGprs" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGroupGprs'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.group.IrmsGroupGprs" 
							 serviceBeanId="eomsService"   title="网络资源--集客家客--GPRS信息表" >
		<eoms:row name="客户名称" value="customerName"/>
		<eoms:row name="产品实例标识" value="relatedProInstanceId"/>
		<eoms:row name="APN名称" value="apnName"/>
		<eoms:row name="APN号码" value="relatedApnId"/>
		<eoms:row name="业务带宽(速率)" value="businessBw"/>
		<eoms:row name="业务开放范围" value="businessOpenLimi"/>
		<eoms:row name="业务承载方式" value="businessHostingWay"/>
		<eoms:row name="关联产品实例标识" value="relativeProInstanceId"/>
		<eoms:row name="IP地址分配方式" value="ipAllocationMode"/>
		<eoms:row name="地址数量" value="useIpNum"/>
		<eoms:row name="是否双GGSN" value="isBothGgsn"/>
		<eoms:row name="传输接入方式" value="tranAccessWay"/>
		<eoms:row name="企业服务器IP地址" value="enterpriseService"/>
		<eoms:row name="第一个GGSN的名称" value="labelCn1"/>
		<eoms:row name="第一个GGSN的GRE本端地址" value="greAddressName1"/>
		<eoms:row name="第一个GGSN的GRE企业端地址" value="greEnterpriseAdd1"/>
		<eoms:row name="第一个GGSN的GRE名称" value="greName1"/>
		<eoms:row name="第一个GGSN的GRE隧道key" value="greTunnrlKey1"/>
		<eoms:row name="第二个GGSN的名称" value="labelCn2"/>
		<eoms:row name="第二个GGSN的GRE本端地址" value="greAddressName2"/>
		<eoms:row name="第二个GGSN的GRE企业端地址" value="greEnterpriseAdd2"/>
		<eoms:row name="第二个GGSN的GRE名称" value="greName2"/>
		<eoms:row name="第二个GGSN的GRE隧道key" value="greTunnrlKey2"/>
		<eoms:row name="终端IP地址池" value="apnAddressPool"/>
		<eoms:row name="是否使用客户侧radius" value="isUseRadius"/>
		<eoms:row name="RADIUS服务器地址" value="radiusIpAdd"/>
		<eoms:row name="是否允许终端间互访" value="isTerminalVisits"/>
		<eoms:row name="电路名称" value="circuitName"/>
		<eoms:row name="光路名称" value="opticalCircuitName"/>
		<eoms:row name="接入客户日期" value="customerAccessDate"/>
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

<logic:present name="irmsGroupGprsList" scope="request">
	<display:table id="irmsGroupGprsList"
					name="irmsGroupGprsList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupGprsList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGroupGprsList.customerName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupGprsList.relatedProInstanceId}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="APN名称">
						${irmsGroupGprsList.apnName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="APN号码">
						${irmsGroupGprsList.relatedApnId}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsGroupGprsList.businessBw}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务开放范围">
						${irmsGroupGprsList.businessOpenLimi}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务承载方式">
						<eoms:id2nameDB id="${irmsGroupGprsList.businessHostingWay}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="关联产品实例标识">
						${irmsGroupGprsList.relativeProInstanceId}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="IP地址分配方式">
						${irmsGroupGprsList.ipAllocationMode}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地址数量">
						${irmsGroupGprsList.useIpNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否双GGSN">
						${irmsGroupGprsList.isBothGgsn}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						${irmsGroupGprsList.tranAccessWay}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="企业服务器IP地址">
						${irmsGroupGprsList.enterpriseService}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的名称">
						${irmsGroupGprsList.labelCn1}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE本端地址">
						${irmsGroupGprsList.greAddressName1}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE企业端地址">
						${irmsGroupGprsList.greEnterpriseAdd1}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE名称">
						${irmsGroupGprsList.greName1}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第一个GGSN的GRE隧道key">
						${irmsGroupGprsList.greTunnrlKey1}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的名称">
						${irmsGroupGprsList.labelCn2}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE本端地址">
						${irmsGroupGprsList.greAddressName2}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE企业端地址">
						${irmsGroupGprsList.greEnterpriseAdd2}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE名称">
						${irmsGroupGprsList.greName2}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="第二个GGSN的GRE隧道key">
						${irmsGroupGprsList.greTunnrlKey2}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="终端IP地址池">
						${irmsGroupGprsList.apnAddressPool}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否使用客户侧radius">
						<eoms:id2nameDB id="${irmsGroupGprsList.isUseRadius}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="RADIUS服务器地址">
						${irmsGroupGprsList.radiusIpAdd}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否允许终端间互访">
						<eoms:id2nameDB id="${irmsGroupGprsList.isTerminalVisits}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsGroupGprsList.circuitName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsGroupGprsList.opticalCircuitName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="接入客户日期">
						${irmsGroupGprsList.customerAccessDate}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsGroupGprsList.remark}
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
