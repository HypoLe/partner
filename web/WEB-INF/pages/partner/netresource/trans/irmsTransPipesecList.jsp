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
		params :{resourceName:"IrmsTransPipesec"},
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
		params :{resourceName:"IrmsTransPipesec"},
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
<div align="center"><b>管道段-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.trans.IrmsTransPipesec" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	管道段名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="pipeSectionNameStringLike" value="${pipeSectionNameStringLike}" />
						</td>
						
						
						<td class="label">
						 	起始点人手井名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="startWellNameStringEqual" value="${startWellNameStringEqual}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	末端点人手井名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="endWellNameStringEqual" value="${endWellNameStringEqual}" />
						</td>
						
						
						<td class="label">
						 	所属管道
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedPipeStringEqual" value="${relatedPipeStringEqual}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	承载光缆段
						</td>
						<td class="content" >
								<input class="text" type="text" name="relatedSectionStringEqual" value="${relatedSectionStringEqual}" />
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
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.trans.IrmsTransPipesec'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.trans.IrmsTransPipesec" 
							 serviceBeanId="eomsService"   title="网络资源--传输线路--管道段" >
		<eoms:row name="管道段名称" value="pipeSectionName"/>
		<eoms:row name="起始点人手井名称" value="startWellName"/>
		<eoms:row name="末端点人手井名称" value="endWellName"/>
		<eoms:row name="材料" value="stuff"/>
		<eoms:row name="移动管孔数目" value="poreCount"/>
		<eoms:row name="移动子孔数目" value="subPoreCount"/>
		<eoms:row name="所属管道" value="relatedPipe"/>
		<eoms:row name="承载光缆段" value="relatedSection"/>
		<eoms:row name="产权" value="property"/>
		<eoms:row name="使用单位" value="useUnit"/>
		<eoms:row name="所有权人" value="ownerUnit"/>
		<eoms:row name="管道段长度" value="sectionLength"/>
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

<logic:present name="irmsTransPipesecList" scope="request">
	<display:table id="irmsTransPipesecList"
					name="irmsTransPipesecList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsTransPipesecList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="管道段名称">
						${irmsTransPipesecList.pipeSectionName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="起始点人手井名称">
						${irmsTransPipesecList.startWellName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="末端点人手井名称">
						${irmsTransPipesecList.endWellName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="材料">
						<eoms:id2nameDB id="${irmsTransPipesecList.stuff}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动管孔数目">
						${irmsTransPipesecList.poreCount}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="移动子孔数目">
						${irmsTransPipesecList.subPoreCount}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属管道">
						${irmsTransPipesecList.relatedPipe}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="承载光缆段">
						${irmsTransPipesecList.relatedSection}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产权">
						<eoms:id2nameDB id="${irmsTransPipesecList.property}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="使用单位">
						${irmsTransPipesecList.useUnit}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所有权人">
						${irmsTransPipesecList.ownerUnit}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="管道段长度">
						${irmsTransPipesecList.sectionLength}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共建">
						<eoms:id2nameDB id="${irmsTransPipesecList.isConstructShare}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共建单位">
						${irmsTransPipesecList.constructCompany}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="是否共享">
						<eoms:id2nameDB id="${irmsTransPipesecList.isShared}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="共享单位">
						${irmsTransPipesecList.sharedCompany}
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
