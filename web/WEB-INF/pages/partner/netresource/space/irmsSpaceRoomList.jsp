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
		params :{resourceName:"IrmsSpaceRoom"},
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
		params :{resourceName:"IrmsSpaceRoom"},
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
<div align="center"><b>机房-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.space.IrmsSpaceRoom" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	机房名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="roomNameStringLike" value="${roomNameStringLike}" />
						</td>
						
						
						<td class="label">
						 	机房类型
						</td>
						<td class="content" >
								<eoms:comboBox id="roomType" name="roomTypeStringEqual" initDicId="11228010201" defaultValue="${roomTypeStringEqual}" styleClass="input select" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	所属站点
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedSiteNameStringLike" value="${relatedSiteNameStringLike}" />
						</td>
						
						
						<td class="label">
						 	是否共建
						</td>
						<td class="content" >
								<eoms:comboBox id="isConstructShare" name="isConstructShareStringEqual" initDicId="10301" defaultValue="${isConstructShareStringEqual}" styleClass="input select" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	是否共享
						</td>
						<td class="content" colspan="3">
								<eoms:comboBox id="isShared" name="isSharedStringEqual" initDicId="10301" defaultValue="${isSharedStringEqual}" styleClass="input select" />
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.space.IrmsSpaceRoom'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.space.IrmsSpaceRoom" 
							 serviceBeanId="eomsService"   title="网络资源--空间资源--机房" >
		<eoms:row name="机房名称" value="roomName"/>
		<eoms:row name="机房缩写" value="abbreviation"/>
		<eoms:row name="机房别名" value="roomAlias"/>
		<eoms:row name="机房类型" value="roomType"/>
		<eoms:row name="传输业务级别" value="serviceLevel"/>
		<eoms:row name="所属站点" value="relatedSiteName"/>
		<eoms:row name="所在楼层" value="floorNum"/>
		<eoms:row name="长" value="length"/>
		<eoms:row name="宽" value="width"/>
		<eoms:row name="高" value="height"/>
		<eoms:row name="机架起始行号" value="rackStartRowNum"/>
		<eoms:row name="机架终止行号" value="rackEndRowNum"/>
		<eoms:row name="机架起始列号" value="rackStartColNum"/>
		<eoms:row name="机架终止列号" value="rackEndColNum"/>
		<eoms:row name="机架行方向" value="rackRowDirect"/>
		<eoms:row name="机架列方向" value="rackColumnDirect"/>
		<eoms:row name="是否共建" value="isConstructShare"/>
		<eoms:row name="共建单位" value="buildTogtherUnit"/>
		<eoms:row name="是否共享" value="isShared"/>
		<eoms:row name="共享单位" value="sharedUnit"/>
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

<logic:present name="irmsSpaceRoomList" scope="request">
	<display:table id="irmsSpaceRoomList"
					name="irmsSpaceRoomList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsSpaceRoomList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="机房名称">
						${irmsSpaceRoomList.roomName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机房缩写">
						${irmsSpaceRoomList.abbreviation}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机房别名">
						${irmsSpaceRoomList.roomAlias}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机房类型">
						<eoms:id2nameDB id="${irmsSpaceRoomList.roomType}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输业务级别">
						<eoms:id2nameDB id="${irmsSpaceRoomList.serviceLevel}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属站点">
						${irmsSpaceRoomList.relatedSiteName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所在楼层">
						${irmsSpaceRoomList.floorNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="长">
						${irmsSpaceRoomList.length}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="宽">
						${irmsSpaceRoomList.width}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="高">
						${irmsSpaceRoomList.height}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机架起始行号">
						${irmsSpaceRoomList.rackStartRowNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机架终止行号">
						${irmsSpaceRoomList.rackEndRowNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机架起始列号">
						${irmsSpaceRoomList.rackStartColNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机架终止列号">
						${irmsSpaceRoomList.rackEndColNum}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机架行方向">
						<eoms:id2nameDB id="${irmsSpaceRoomList.rackRowDirect}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机架列方向">
						<eoms:id2nameDB id="${irmsSpaceRoomList.rackColumnDirect}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共建">
						<eoms:id2nameDB id="${irmsSpaceRoomList.isConstructShare}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共建单位">
						${irmsSpaceRoomList.buildTogtherUnit}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共享">
						<eoms:id2nameDB id="${irmsSpaceRoomList.isShared}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共享单位">
						${irmsSpaceRoomList.sharedUnit}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsSpaceRoomList.remark}
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
