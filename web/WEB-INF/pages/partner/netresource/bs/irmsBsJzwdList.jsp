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
		params :{resourceName:"IrmsBsJzwd"},
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
		params :{resourceName:"IrmsBsJzwd"},
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
<div align="center"><b>机楼基站外电-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.IrmsBsJzwd" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.IrmsBsJzwd'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.bs.IrmsBsJzwd" 
							 serviceBeanId="eomsService"   title="网络资源--基站设备及配套--机楼基站外电" >
		<eoms:row name="所属省份" value="relatedProvince"/>
		<eoms:row name="所属地市" value="relatedCity"/>
		<eoms:row name="所属区县" value="relatedCounty"/>
		<eoms:row name="所属站点" value="relatedSite"/>
		<eoms:row name="机楼（基站）名称" value="relatedSiteName"/>
		<eoms:row name="机楼（基站）编号" value="relatedSiteNo"/>
		<eoms:row name="产权属性" value="property"/>
		<eoms:row name="重要级别" value="importantLevel"/>
		<eoms:row name="机楼(基站)地址" value="roomAddr"/>
		<eoms:row name="变电站一" value="oneSubstation"/>
		<eoms:row name="变电站二" value="twoSubstation"/>
		<eoms:row name="维护负责人" value="preserver"/>
		<eoms:row name="市电容量(KVA)" value="utilityCpacity"/>
		<eoms:row name="市电引入方式" value="utilityMode"/>
		<eoms:row name="线径(m)" value="line"/>
		<eoms:row name="线缆长度(m)" value="cablesLength"/>
		<eoms:row name="供电性质" value="electKind"/>
		<eoms:row name="转供信息" value="turnInfo"/>
		<eoms:row name="海拔高度" value="height"/>
		<eoms:row name="经度" value="longitude"/>
		<eoms:row name="纬度" value="latitude"/>
		<eoms:row name="供电合同号" value="electContract"/>
		<eoms:row name="变电站电话" value="substationTel"/>
		<eoms:row name="业主联系人" value="ownerLinkman"/>
		<eoms:row name="查询联系人" value="queryLinkman"/>
		<eoms:row name="业主电话" value="ownerLinktel"/>
		<eoms:row name="查询电话" value="queryLinktel"/>
		<eoms:row name="动力代维" value="powerMaint"/>
		<eoms:row name="传输代维" value="transMaint"/>
		<eoms:row name="环境代维" value="environmentMaint"/>
		<eoms:row name="消防代维" value="fireMaint"/>
		<eoms:row name="空调代维" value="airConditionMain"/>
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

<logic:present name="irmsBsJzwdList" scope="request">
	<display:table id="irmsBsJzwdList"
					name="irmsBsJzwdList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsJzwdList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="所属省份">
						${irmsBsJzwdList.relatedProvince}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属地市">
						${irmsBsJzwdList.relatedCity}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属区县">
						${irmsBsJzwdList.relatedCounty}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属站点">
						${irmsBsJzwdList.relatedSite}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机楼（基站）名称">
						${irmsBsJzwdList.relatedSiteName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机楼（基站）编号">
						${irmsBsJzwdList.relatedSiteNo}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产权属性">
						<eoms:id2nameDB id="${irmsBsJzwdList.property}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="重要级别">
						<eoms:id2nameDB id="${irmsBsJzwdList.importantLevel}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机楼(基站)地址">
						${irmsBsJzwdList.roomAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="变电站一">
						${irmsBsJzwdList.oneSubstation}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="变电站二">
						${irmsBsJzwdList.twoSubstation}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护负责人">
						${irmsBsJzwdList.preserver}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="市电容量(KVA)">
						${irmsBsJzwdList.utilityCpacity}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="市电引入方式">
						<eoms:id2nameDB id="${irmsBsJzwdList.utilityMode}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="线径(m)">
						${irmsBsJzwdList.line}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="线缆长度(m)">
						${irmsBsJzwdList.cablesLength}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供电性质">
						<eoms:id2nameDB id="${irmsBsJzwdList.electKind}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="转供信息">
						${irmsBsJzwdList.turnInfo}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="海拔高度">
						${irmsBsJzwdList.height}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="经度">
						${irmsBsJzwdList.longitude}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="纬度">
						${irmsBsJzwdList.latitude}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供电合同号">
						${irmsBsJzwdList.electContract}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="变电站电话">
						${irmsBsJzwdList.substationTel}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业主联系人">
						${irmsBsJzwdList.ownerLinkman}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="查询联系人">
						${irmsBsJzwdList.queryLinkman}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业主电话">
						${irmsBsJzwdList.ownerLinktel}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="查询电话">
						${irmsBsJzwdList.queryLinktel}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="动力代维">
						${irmsBsJzwdList.powerMaint}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输代维">
						${irmsBsJzwdList.transMaint}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="环境代维">
						${irmsBsJzwdList.environmentMaint}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="消防代维">
						${irmsBsJzwdList.fireMaint}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="空调代维">
						${irmsBsJzwdList.airConditionMain}
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
