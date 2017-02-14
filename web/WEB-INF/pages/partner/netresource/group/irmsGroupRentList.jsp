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
		params :{resourceName:"IrmsGroupRent"},
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
		params :{resourceName:"IrmsGroupRent"},
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
<div align="center"><b>出租专线信息表-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGroupRent" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGroupRent'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.group.IrmsGroupRent" 
							 serviceBeanId="eomsService"   title="网络资源--集客家客--出租专线信息表" >
		<eoms:row name="客户名称" value="customerName"/>
		<eoms:row name="A端客户名称" value="pointaCustomerName"/>
		<eoms:row name="产品实例标识" value="relatedInstance"/>
		<eoms:row name="传输接入方式" value="tranAccessWay"/>
		<eoms:row name="业务带宽(速率)" value="businessBw"/>
		<eoms:row name="A端客户地址" value="pointaCustomerAddr"/>
		<eoms:row name="A 端业务设备名称" value="relatedPointa"/>
		<eoms:row name="A 端业务设备端口" value="relatedPointaPort"/>
		<eoms:row name="A 端业务设备DDF/ODF端口" value="pointaDdfodfPort"/>
		<eoms:row name="A端接入机房设备名称" value="relatedArDevice"/>
		<eoms:row name="A端接入机房设备端口" value="relatedArDevicePort"/>
		<eoms:row name="A端接入机房设备ODF端口" value="relatedArdOdfPort"/>
		<eoms:row name="电路名称" value="relatedCircuitName"/>
		<eoms:row name="电路接口类型" value="relatedCircuitType"/>
		<eoms:row name="电路级别" value="relatedCircuitLevel"/>
		<eoms:row name="电路要求" value="circuitRequirement"/>
		<eoms:row name="电路保护方式" value="circuitProtectWay"/>
		<eoms:row name="局向" value="officeRedirection"/>
		<eoms:row name="Z端客户名称" value="pointzCustomerName"/>
		<eoms:row name="Z端客户地址" value="pointzCustomerAddr"/>
		<eoms:row name="Z端业务设备名称" value="relatedPointzName"/>
		<eoms:row name="Z端业务设备端口" value="relatedPointzPort"/>
		<eoms:row name="Z端业务设备DDF/ODF端口" value="pointzDdfodfPort"/>
		<eoms:row name="Z端接入机房设备名称" value="relatedPointzArName"/>
		<eoms:row name="Z端接入机房设备端口" value="relatedPointzArPort"/>
		<eoms:row name="Z端接入机房设备ODF端口" value="relatedPointzArpOdf"/>
		<eoms:row name="光路名称" value="relatedOcName"/>
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

<logic:present name="irmsGroupRentList" scope="request">
	<display:table id="irmsGroupRentList"
					name="irmsGroupRentList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupRentList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGroupRentList.customerName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端客户名称">
						${irmsGroupRentList.pointaCustomerName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupRentList.relatedInstance}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输接入方式">
						${irmsGroupRentList.tranAccessWay}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务带宽(速率)">
						${irmsGroupRentList.businessBw}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端客户地址">
						${irmsGroupRentList.pointaCustomerAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A 端业务设备名称">
						${irmsGroupRentList.relatedPointa}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A 端业务设备端口">
						${irmsGroupRentList.relatedPointaPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A 端业务设备DDF/ODF端口">
						${irmsGroupRentList.pointaDdfodfPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端接入机房设备名称">
						${irmsGroupRentList.relatedArDevice}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端接入机房设备端口">
						${irmsGroupRentList.relatedArDevicePort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="A端接入机房设备ODF端口">
						${irmsGroupRentList.relatedArdOdfPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路名称">
						${irmsGroupRentList.relatedCircuitName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路接口类型">
						${irmsGroupRentList.relatedCircuitType}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路级别">
						${irmsGroupRentList.relatedCircuitLevel}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路要求">
						${irmsGroupRentList.circuitRequirement}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="电路保护方式">
						${irmsGroupRentList.circuitProtectWay}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="局向">
						${irmsGroupRentList.officeRedirection}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端客户名称">
						${irmsGroupRentList.pointzCustomerName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端客户地址">
						${irmsGroupRentList.pointzCustomerAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端业务设备名称">
						${irmsGroupRentList.relatedPointzName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端业务设备端口">
						${irmsGroupRentList.relatedPointzPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端业务设备DDF/ODF端口">
						${irmsGroupRentList.pointzDdfodfPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端接入机房设备名称">
						${irmsGroupRentList.relatedPointzArName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端接入机房设备端口">
						${irmsGroupRentList.relatedPointzArPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="Z端接入机房设备ODF端口">
						${irmsGroupRentList.relatedPointzArpOdf}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="光路名称">
						${irmsGroupRentList.relatedOcName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsGroupRentList.remark}
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
