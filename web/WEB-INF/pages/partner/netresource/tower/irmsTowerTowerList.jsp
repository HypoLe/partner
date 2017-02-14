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
		params :{resourceName:"IrmsTowerTower"},
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
		params :{resourceName:"IrmsTowerTower"},
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
<div align="center"><b>铁塔-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.tower.IrmsTowerTower" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	铁塔名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="labelCnStringLike" value="${labelCnStringLike}" />
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
						 	铁塔类型
						</td>
						<td class="content" >
								<eoms:comboBox id="towerType" name="towerTypeStringEqual" initDicId="11228040101" defaultValue="${towerTypeStringEqual}" styleClass="input select" />
						</td>
						
						
						<td class="label">
						 	铁塔厂家
						</td>
						<td class="content" >
								<input class="text" type="text" name="vendorStringLike" value="${vendorStringLike}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	建塔日期
						</td>
						<td class="content" >
								<input type="text" name="buildtimeStringEqual" styleClass="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,true,-1);"  readonly="true" alt="allowBlank:true,vtext:'请选择建塔日期！'" value="${buildtimeStringEqual}"/>
						</td>
						
						
						<td class="label">
						 	铁塔高度
						</td>
						<td class="content" >
								<input class="text" type="text" name="towerHeightStringLike" value="${towerHeightStringLike}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	铁塔代维公司
						</td>
						<td class="content" colspan="3">
								<input class="text" type="text" name="manageCompanyStringEqual" value="${manageCompanyStringEqual}" />
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.tower.IrmsTowerTower'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.tower.IrmsTowerTower" 
							 serviceBeanId="eomsService"   title="网络资源--铁塔及天馈--铁塔" >
		<eoms:row name="铁塔名称" value="labelCn"/>
		<eoms:row name="铁塔平台数量" value="towerPlatnum"/>
		<eoms:row name="已使用平台数量" value="usePlatnum"/>
		<eoms:row name="所属机房" value="relatedRoom"/>
		<eoms:row name="铁塔塔身高度" value="towerStature"/>
		<eoms:row name="铁塔类型" value="towerType"/>
		<eoms:row name="铁塔厂家" value="vendor"/>
		<eoms:row name="铁塔产权" value="towerPropertyAtt"/>
		<eoms:row name="建塔日期" value="buildtime"/>
		<eoms:row name="铁塔高度" value="towerHeight"/>
		<eoms:row name="铁塔设计承重" value="designBareWeight"/>
		<eoms:row name="铁塔实际承重" value="realBareWeight"/>
		<eoms:row name="铁塔代维公司" value="manageCompany"/>
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

<logic:present name="irmsTowerTowerList" scope="request">
	<display:table id="irmsTowerTowerList"
					name="irmsTowerTowerList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsTowerTowerList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="铁塔名称">
						${irmsTowerTowerList.labelCn}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔平台数量">
						${irmsTowerTowerList.towerPlatnum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="已使用平台数量">
						${irmsTowerTowerList.usePlatnum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属机房">
						${irmsTowerTowerList.relatedRoom}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔塔身高度">
						${irmsTowerTowerList.towerStature}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔类型">
						<eoms:id2nameDB id="${irmsTowerTowerList.towerType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔厂家">
						${irmsTowerTowerList.vendor}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔产权">
						<eoms:id2nameDB id="${irmsTowerTowerList.towerPropertyAtt}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="建塔日期">
						${irmsTowerTowerList.buildtime}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔高度">
						${irmsTowerTowerList.towerHeight}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔设计承重">
						${irmsTowerTowerList.designBareWeight}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔实际承重">
						${irmsTowerTowerList.realBareWeight}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="铁塔代维公司">
						${irmsTowerTowerList.manageCompany}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共建">
						<eoms:id2nameDB id="${irmsTowerTowerList.isConstructShare}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共建单位">
						${irmsTowerTowerList.constructCompany}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共享">
						<eoms:id2nameDB id="${irmsTowerTowerList.isShared}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共享单位">
						${irmsTowerTowerList.sharedCompany}
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
