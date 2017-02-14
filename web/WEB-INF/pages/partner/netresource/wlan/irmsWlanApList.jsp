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
		params :{resourceName:"IrmsWlanAp"},
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
		params :{resourceName:"IrmsWlanAp"},
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
<div align="center"><b>AP-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.wlan.IrmsWlanAp" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	AP名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="apNameStringLike" value="${apNameStringLike}" />
						</td>
						
						
						<td class="label">
						 	地市
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedDistrictStringEqual" value="${relatedDistrictStringEqual}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	所属热点名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedHotpointStringEqual" value="${relatedHotpointStringEqual}" />
						</td>
						
						
						<td class="label">
						 	AP厂家
						</td>
						<td class="content" >
								<eoms:comboBox id="apVendor" name="apVendorStringEqual" initDicId="112280702" defaultValue="${apVendorStringEqual}" styleClass="input select" />
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.wlan.IrmsWlanAp'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.wlan.IrmsWlanAp" 
							 serviceBeanId="eomsService"   title="网络资源--直放站室分及WLAN--AP" >
		<eoms:row name="AP名称" value="apName"/>
		<eoms:row name="地市" value="relatedDistrict"/>
		<eoms:row name="所属热点名称" value="relatedHotpoint"/>
		<eoms:row name="AP厂家" value="apVendor"/>
		<eoms:row name="AP类型" value="apType"/>
		<eoms:row name="AP MAC地址" value="apMacAddr"/>
		<eoms:row name="AP管理地址" value="manageApAddr"/>
		<eoms:row name="空口制式" value="stressPattern"/>
		<eoms:row name="AP安装位置" value="apLocation"/>
		<eoms:row name="AP覆盖区域" value="apCoverageArea"/>
		<eoms:row name="是否2G/3G室分合路" value="is2g3gDistributo"/>
		<eoms:row name="所属热点交换机名称" value="relatedHotpingSw"/>
		<eoms:row name="所属热点交换机端口" value="hotpotSwPort"/>
		<eoms:row name="软件版本" value="softVersion"/>
		<eoms:row name="所属AC名称" value="relatedBackAcCu"/>
		<eoms:row name="工程状态" value="neWorkingState"/>
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

<logic:present name="irmsWlanApList" scope="request">
	<display:table id="irmsWlanApList"
					name="irmsWlanApList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsWlanApList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="AP名称">
						${irmsWlanApList.apName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="地市">
						${irmsWlanApList.relatedDistrict}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属热点名称">
						${irmsWlanApList.relatedHotpoint}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP厂家">
						<eoms:id2nameDB id="${irmsWlanApList.apVendor}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP类型">
						<eoms:id2nameDB id="${irmsWlanApList.apType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP MAC地址">
						${irmsWlanApList.apMacAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP管理地址">
						${irmsWlanApList.manageApAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="空口制式">
						<eoms:id2nameDB id="${irmsWlanApList.stressPattern}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP安装位置">
						${irmsWlanApList.apLocation}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="AP覆盖区域">
						${irmsWlanApList.apCoverageArea}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否2G/3G室分合路">
						${irmsWlanApList.is2g3gDistributo}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属热点交换机名称">
						${irmsWlanApList.relatedHotpingSw}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属热点交换机端口">
						${irmsWlanApList.hotpotSwPort}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="软件版本">
						${irmsWlanApList.softVersion}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属AC名称">
						${irmsWlanApList.relatedBackAcCu}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="工程状态">
						<eoms:id2nameDB id="${irmsWlanApList.neWorkingState}" beanId="ItawSystemDictTypeDao"/>
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
