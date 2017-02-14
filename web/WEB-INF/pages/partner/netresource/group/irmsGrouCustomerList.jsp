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
		params :{resourceName:"IrmsGrouCustomer"},
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
		params :{resourceName:"IrmsGrouCustomer"},
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
<div align="center"><b>客户信息表-列表页面</b></div><br><br/>

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
	<form action="${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGrouCustomer" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	客户编号
						</td>
						<td class="content" >
								<input class="text" type="text" name="customerNoStringLike" value="${customerNoStringLike}" />
						</td>
						
						
						<td class="label">
						 	客户名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="customerNameStringLike" value="${customerNameStringLike}" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	客户地址
						</td>
						<td class="content" >
								<input class="text" type="text" name="customerAddrStringLike" value="${customerAddrStringLike}" />
						</td>
						
						
						<td class="label">
						 	客户级别
						</td>
						<td class="content" >
								<eoms:comboBox id="customerLevel" name="customerLevelStringEqual" initDicId="11228060101" defaultValue="${customerLevelStringEqual}" styleClass="input select" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	客户服务等级
						</td>
						<td class="content" >
								<eoms:comboBox id="serviceLevel" name="serviceLevelStringEqual" initDicId="11228060102" defaultValue="${serviceLevelStringEqual}" styleClass="input select" />
						</td>
						
						
						<td class="label">
						 	客户行业
						</td>
						<td class="content" >
								<eoms:comboBox id="industry" name="industryStringEqual" initDicId="11228060103" defaultValue="${industryStringEqual}" styleClass="input select" />
						</td>
						</tr>
						<tr>
						<td class="label">
						 	客户区域所在城市
						</td>
						<td class="content" colspan="3">
								<input class="text" type="text" name="cityStringEqual" value="${cityStringEqual}" />
						</td>
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/pnrNetResourceAction.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.group.IrmsGrouCustomer'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.group.IrmsGrouCustomer" 
							 serviceBeanId="eomsService"   title="网络资源--集客家客--客户信息表" >
		<eoms:row name="客户编号" value="customerNo"/>
		<eoms:row name="客户名称" value="customerName"/>
		<eoms:row name="客户地址" value="customerAddr"/>
		<eoms:row name="客户级别" value="customerLevel"/>
		<eoms:row name="客户服务等级" value="serviceLevel"/>
		<eoms:row name="客户行业" value="industry"/>
		<eoms:row name="客户区域所在城市" value="city"/>
		<eoms:row name="客户区域所在区县" value="region"/>
		<eoms:row name="客户业务联系人" value="linkman"/>
		<eoms:row name="客户业务联系人电话" value="linkPhone"/>
		<eoms:row name="客户经理" value="relatedManagerCu"/>
		<eoms:row name="客户经理联系电话" value="relatedManagerPh"/>
		<eoms:row name="集客部联系人" value="groupContact"/>
		<eoms:row name="集客部联系电话" value="groupPhone"/>
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

<logic:present name="irmsGrouCustomerList" scope="request">
	<display:table id="irmsGrouCustomerList"
					name="irmsGrouCustomerList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGrouCustomerList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户编号">
						${irmsGrouCustomerList.customerNo}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGrouCustomerList.customerName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户地址">
						${irmsGrouCustomerList.customerAddr}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户级别">
						<eoms:id2nameDB id="${irmsGrouCustomerList.customerLevel}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户服务等级">
						<eoms:id2nameDB id="${irmsGrouCustomerList.serviceLevel}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户行业">
						<eoms:id2nameDB id="${irmsGrouCustomerList.industry}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户区域所在城市">
						${irmsGrouCustomerList.city}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户区域所在区县">
						${irmsGrouCustomerList.region}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务联系人">
						${irmsGrouCustomerList.linkman}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务联系人电话">
						${irmsGrouCustomerList.linkPhone}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户经理">
						${irmsGrouCustomerList.relatedManagerCu}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户经理联系电话">
						${irmsGrouCustomerList.relatedManagerPh}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="集客部联系人">
						${irmsGrouCustomerList.groupContact}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="集客部联系电话">
						${irmsGrouCustomerList.groupPhone}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="备注">
						${irmsGrouCustomerList.remark}
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
