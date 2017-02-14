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
		params :{resourceName:"IrmsBsBtssite"},
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
		params :{resourceName:"IrmsBsBtssite"},
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
<div align="center"><b>BTSSITE-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.IrmsBsBtssite" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	OMC中网元名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="userlabelCmStringLike" value="${userlabelCmStringLike}" />
						</td>
						
						
						<td class="label">
						 	中文名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="labelCnStringLike" value="${labelCnStringLike}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	VIP基站
						</td>
						<td class="content" >
								<eoms:comboBox id="vipType" name="vipTypeStringEqual" initDicId="11228022601" defaultValue="${vipTypeStringEqual}" styleClass="input select" />
						</td>
						
						
						<td class="label">
						 	所属机房
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedRoomStringEqual" value="${relatedRoomStringEqual}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	基站编号
						</td>
						<td class="content" >
								<input class="text" type="text" name="btssiteNoStringLike" value="${btssiteNoStringLike}" />
						</td>
						
						
						<td class="label">
						 	代维公司（统一考虑）
						</td>
						<td class="content" >
								<eoms:comboBox id="manageCompany" name="manageCompanyStringEqual" initDicId="112280704" defaultValue="${manageCompanyStringEqual}" styleClass="input select" />
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.IrmsBsBtssite'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.bs.IrmsBsBtssite" 
							 serviceBeanId="eomsService"   title="网络资源--基站设备及配套--BTSSITE" >
		<eoms:row name="OMC中网元名称" value="userlabelCm"/>
		<eoms:row name="中文名称" value="labelCn"/>
		<eoms:row name="所属BSC" value="relatedBsc"/>
		<eoms:row name="移动通信系统标识" value="freBand"/>
		<eoms:row name="传输类型" value="transmode"/>
		<eoms:row name="基站应急类型" value="btssiteCrisisType"/>
		<eoms:row name="VIP基站" value="vipType"/>
		<eoms:row name="所属机房" value="relatedRoom"/>
		<eoms:row name="蜂窝类型" value="beehiveType"/>
		<eoms:row name="基站编号" value="btssiteNo"/>
		<eoms:row name="设备型号" value="model"/>
		<eoms:row name="故障受理单位(统一考虑)" value="failAccUnit"/>
		<eoms:row name="代维公司（统一考虑）" value="manageCompany"/>
		<eoms:row name="网元状态" value="status"/>
		<eoms:row name="设备供应商" value="relatedVendor"/>
		<eoms:row name="软件版本信息" value="softVersion"/>
		<eoms:row name="开通时间" value="openTime"/>
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

<logic:present name="irmsBsBtssiteList" scope="request">
	<display:table id="irmsBsBtssiteList"
					name="irmsBsBtssiteList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsBtssiteList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="OMC中网元名称">
						${irmsBsBtssiteList.userlabelCm}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="中文名称">
						${irmsBsBtssiteList.labelCn}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属BSC">
						${irmsBsBtssiteList.relatedBsc}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动通信系统标识">
						<eoms:id2nameDB id="${irmsBsBtssiteList.freBand}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输类型">
						<eoms:id2nameDB id="${irmsBsBtssiteList.transmode}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="基站应急类型">
						<eoms:id2nameDB id="${irmsBsBtssiteList.btssiteCrisisType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="VIP基站">
						<eoms:id2nameDB id="${irmsBsBtssiteList.vipType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属机房">
						${irmsBsBtssiteList.relatedRoom}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="蜂窝类型">
						<eoms:id2nameDB id="${irmsBsBtssiteList.beehiveType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="基站编号">
						${irmsBsBtssiteList.btssiteNo}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备型号">
						${irmsBsBtssiteList.model}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="故障受理单位(统一考虑)">
						${irmsBsBtssiteList.failAccUnit}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="代维公司（统一考虑）">
						<eoms:id2nameDB id="${irmsBsBtssiteList.manageCompany}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="网元状态">
						<eoms:id2nameDB id="${irmsBsBtssiteList.status}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="设备供应商">
						<eoms:id2nameDB id="${irmsBsBtssiteList.relatedVendor}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="软件版本信息">
						${irmsBsBtssiteList.softVersion}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="开通时间">
						${irmsBsBtssiteList.openTime}
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
