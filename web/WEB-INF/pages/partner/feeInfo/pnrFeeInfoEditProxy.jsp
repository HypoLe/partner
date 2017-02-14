<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<eoms:xbox id="payUnit" dataUrl="${app}/xtree.do?method=dept" rootId="1"
	rootText='单位' valueField="payUnit" handler="payUnitName"
	textField="payUnitName" checktype="dept" single="true"></eoms:xbox>
<eoms:xbox id="collectUnit" dataUrl="${app}/xtree.do?method=dept"
	rootId="2" rootText='单位' valueField="collectUnit"
	handler="collectUnitName" textField="collectUnitName" checktype="dept"
	single="true"></eoms:xbox>
<eoms:xbox id="payUnitUser"
	dataUrl="${app}/xtree.do?method=userFromDept"
	rootId="${pnrFeeInfoMainForm.payUnit}" rootText='负责人'
	valueField="payUnitUser" handler="payUnitUserName"
	textField="payUnitUserName" checktype="user" single="true"></eoms:xbox>
<eoms:xbox id="collectUnitUser"
	dataUrl="${app}/xtree.do?method=userFromDept"
	rootId="${pnrFeeInfoMainForm.collectUnit}" rootText='负责人'
	valueField="collectUnitUser" handler="collectUnitUserName"
	textField="collectUnitUserName" checktype="user" single="true"></eoms:xbox>

<eoms:xbox id="payMoneyUser"
	dataUrl="${app}/xtree.do?method=userFromDept" rootId="1" rootText='负责人'
	valueField="payMoneyUser" handler="payMoneyUserName"
	textField="payMoneyUserName" checktype="user" single="true"></eoms:xbox>

<html:form action="/pnrFeeInfoMains.do?method=saveProxySer&contractId=${pnrFeeInfoMain.id}"
	styleId="pnrFeeInfoMainForm" method="post">
	<div class="ui-widget-header ui-corner-top ui-state-default " style="cursor:pointer;"
		id="opBasicInfo">
		合同基本信息
	</div>
	<div id="basicInfo">
		<table class="formTable">
			<tr>
				<td class="label">
					合同名称&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content" colspan="3">
					<input type="text" name="contractName" class="text"
						value="${pnrFeeInfoMainForm.contractName}" style="width: 98%"
						alt="allowBlank:false,vtext:'',maxLength:100" />
				</td>
				<html:hidden property="feeName" value="" />
				<html:hidden property="contractNO" value="" />
				<html:hidden property="contractId"
					value="${pnrFeeInfoMainForm.contractId}" />
			</tr>
			<tr>
				<td class="label">
					甲方&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payUnitName" styleId="payUnitName"
						styleClass="text" style="width:95%;"
						alt="allowBlank:false,vtext:''" value="${partyADeptName}"
						readonly="true"
						onblur="changeRootId(xbox_payUnitUser,document.getElementById('payUnit').value);">
					</html:text>
					<input type="hidden" name="payUnit"
						value="${pnrFeeInfoMainForm.payUnit}" id="payUnit" />
				</td>

				<td class="label">
					乙方&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="collectUnitName" styleId="collectUnitName"
						styleClass="text" style="width:95%;"
						alt="allowBlank:false,vtext:''" value="${partyBDeptName}"
						readonly="true"
						onblur="changeRootId(xbox_collectUnitUser,document.getElementById('collectUnit').value);" />
					<input type="hidden" name="collectUnit"
						value="${pnrFeeInfoMainForm.collectUnit}" id="collectUnit" />
				</td>
			</tr>

			<tr>
				<td class="label">
					甲方负责人&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payUnitUserName" styleId="payUnitUserName"
						styleClass="text" style="width:95%;"
						alt="allowBlank:false,vtext:''" value="${payUnitUserName}"
						readonly="true" />
					<input type="hidden" name="payUnitUser"
						value="${pnrFeeInfoMainForm.payUnitUser}" id="payUnitUser" />
				</td>
				<td class="label">
					乙方负责人&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="collectUnitUserName"
						styleId="collectUnitUserName" styleClass="text" style="width:95%;"
						alt="allowBlank:false,vtext:''" value="${collectUnitUserName}"
						readonly="true" />
					<input type="hidden" name="collectUnitUser"
						value="${pnrFeeInfoMainForm.collectUnitUser}" id="collectUnitUser" />
				</td>
			</tr>

			<tr>
				<td class="label">
					付款负责人&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payMoneyUserName" styleId="payMoneyUserName"
						styleClass="text" style="width:95%;"
						alt="allowBlank:false,vtext:''" value="${payMoneyUserName}"
						readonly="true" />
					<input type="hidden" name="payMoneyUser"
						value="${pnrFeeInfoMainForm.payMoneyUser}" id="payMoneyUser" />
				</td>
				<td class="label">
					付款方式&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="payWay" styleId="payWay" styleClass="text"
						style="width:95%;" alt="allowBlank:false,vtext:'',maxLength:50"
						value="${pnrFeeInfoMainForm.payWay}" />
				</td>
			</tr>
			<tr>
				<td class="label" style="vertical-align: middle">
					合同编号&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<html:text property="feeNO" styleId="feeNO" styleClass="text"
						style="width:95%;" alt="allowBlank:false,vtext:'',maxLength:50"
						value="${pnrFeeInfoMainForm.feeNO}" /><div id="showDivFeeNO"></div>
					<input type="hidden" id="showInputFeeNO"/>
				</td>
				<td class="label" style="vertical-align: middle">
					选择年份：&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content" colspan="3">
					<select id="fillYears" name="fillYears">
						<option value="${last2Year}">${last2Year}年</option>
						<option value="${lastYear}">${lastYear}年</option>
						<option value="${thisYear}" selected="selected">${thisYear}年</option>
						<option value="${nextYear}">${nextYear}年</option>
						<option value="${next2Year}">${next2Year}年</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="label" style="vertical-align: middle">
					合同开始时间：&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" readonly="readonly" name="startTime" id="startTime" value="${pnrFeeInfoMainForm.startTime}"
					alt="allowBlank:false,vtext:'',maxLength:50" style="width:95%;" class="text"/>
				</td>
				<td class="label" style="vertical-align: middle">
					合同结束时间：&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" readonly="readonly" name="endTime" id="endTime" value="${pnrFeeInfoMainForm.endTime}"
					alt="allowBlank:false,vtext:'',maxLength:50" style="width:95%;" class="text"/>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					相关附件：
				</td>
				<td class="content" colspan="3">
					<c:choose>
						<c:when test="${ not empty pnrFeeInfoMainForm.id }">
							<eoms:attachment name="pnrFeeInfoMainForm"
								property="accessoriesId" scope="request" idField="accessoriesId"
								appCode="feeInfo" />
						</c:when>
						<c:otherwise>
							<eoms:attachment idList="" idField="accessoriesId"
								appCode="feeInfo" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
		<!-- 结束合同基本信息div -->
	</div>
	
	<div class="ui-widget" style="margin-top: 30px">
		<input type="button" value="添加服务协议" class="button" id="showProtocols"/>
		<div class="ui-widget-header ui-corner-top ui-state-default"
			id="relateProtocol">
			合同关联代维规模信息
		</div>
	</div>
	<table class="formTable" id="myProxyModelsTable">
		<tr>
			<td class="label">
				分公司
			</td>
			<td class="label">
				归属县公司
			</td>
			<td class="label">
				代维公司
			</td>
			<td class="label">
				类型
			</td>
			<td class="label">
				详情
			</td>
			<td class="label">
				删除
			</td>
		</tr>
	<c:if test="${baseStationMains!=null}">
		<c:forEach var="baseStation" items="${baseStationMains}">
			<tr>
				<td>
					${baseStation.city}
				</td>
				<td>
					${baseStation.country}
				</td>
				<td>
					${baseStation.monitorCompany}
				</td>
				<td>
					线路类
				</td>
				<td>
					<a href="${app}/partner2/baseStation/baseStationMain.do?method=detail&id=${baseStation.id}" target="_blank" ><img src="${app}/images/icons/search.gif"/></a>
				</td>
				<td>
					<img src="${app}/nop3/images/icon.gif" onmousedown="deleteBaseStation(this);" style="cursor:pointer;"/>
				</td>
				<input type="hidden" value="${baseStation.id}" name="baseStationId" />
			</tr>
		</c:forEach>
	</c:if>
	<c:if test="${circuitLengths!=null}">
		<c:forEach var="circuitLength" items="${circuitLengths}">
			<tr>
				<td>
					${circuitLength.city}
				</td>
				<td>
					${circuitLength.country}
				</td>
				<td>
					${circuitLength.monitorCompany}
				</td>
				<td>
					线路类
				</td>
				<td>
					<a href="${app}/partner2/circuit/circuitLength.do?method=goToSingleDetail&id=${circuitLength.id}" target="_blank" ><img src="${app}/images/icons/search.gif"/></a>
				</td>
				<td>
					<img src="${app}/nop3/images/icon.gif" onmousedown="deleteCircuit(this);" style="cursor:pointer;"/>
				</td>
				<input type="hidden" value="${circuitLength.id}" name="circuitId" />
			</tr>
		</c:forEach>
	</c:if>
	</table>
	<br/>
	<br/>
	<div class="ui-widget-header ui-corner-top ui-state-default " style="cursor:pointer;"
		id="opBasicInfo2" style="margin-top: 15">
		合同乙方基本信息
	</div>
	<!-- 弹出树图隐藏域 开始 -->
	<div id="deptview" class="hide"></div>
	<input type="hidden" id="treeBtnId" name="treeBtnId" value="" />
	<!-- 弹出树图隐藏域 结束 -->
	<div id="basicInfo2">
		<table class="formTable">
			<tr>
				<td class="label">
					分公司
					<font color='red'>*</font>
				</td>
				<td class="content">
					<input class="text popupDeptTree" type="text" readonly="readonly"
						id="showCity" alt="allowBlank:false,vtext:'请选择分公司'" value="${city}"/>
					<input type="hidden" name="city" id="city" value="${pnrFeeInfoMainForm.city}"/>
				</td>
				<td class="label">
					归属县公司
				</td>
				<td>
					<input class="text popupDeptTree" type="text" readonly="readonly"
						id="showCountry" alt="allowBlank:true,vtext:'请选择归属县公司'" value="${country}"/>
					<input type="hidden" name="country" id="country" value="${pnrFeeInfoMainForm.country}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					代维公司
					<font color='red'>*</font>
				</td>
				<td>
					<input class="text popupMonitorCompanyTree" style="width: 95%"
						type="text" readonly="readonly" id="showMonitorCompany"
						alt="allowBlank:false,vtext:'请选择代维公司'" value="${monitorCompany}" />
					<input type="hidden" name="monitorCompany" id="monitorCompany" value="${pnrFeeInfoMainForm.monitorCompany}"/>
				</td>
			</tr>
		</table>
	</div>
	
	<input type="hidden" name="abilityType" id="abilityType"/>
	<html:hidden property="agreementId" value="${agreementId}" />
	<html:hidden property="contractNew" value="${contractNew}" />
	<html:hidden property="id" value="${pnrFeeInfoMainForm.id}" />
	<html:hidden property="createTime"
		value="${pnrFeeInfoMainForm.createTime}" />
	<html:hidden property="createUser"
		value="${pnrFeeInfoMainForm.createUser}" />
	<html:hidden property="createDept"
		value="${pnrFeeInfoMainForm.createDept}" />
	<input type="hidden" value="2" name="state" id="state" />
	<html:hidden property="tableId" value="${tableId}" />
	<html:hidden property="themeId" value="${themeId}" />
	<input type="hidden" name="protocolIds" id="protocolIds" />
	<input type="hidden" name="baseStationIds" id="baseStationIds"/>
	<input type="hidden" name="circuitIds" id="circuitIds"/>

	<div class="ui-widget-header ui-corner-top ui-state-default"
		style="margin-top: 30px">
		合同相关操作
		<table class="formTable">
			<tr>
				<td class="label">
					操作类型
				</td>
				<td class="content">
					<select id="operateType" name="operateType">
						<option id="submit20" value="draft" selected="selected">
							保存草稿
						</option>
					</select>
				</td>
			</tr>
		</table>
	</div>

	<fieldset>
		<legend>
			操作
		</legend>
		<input type="submit" id="operationSubmitButton" class="btn" value="确定" />
	</fieldset>

</html:form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {

	myJ('div#opBasicInfo').bind('click',function(event){
		myJ('div#basicInfo').fadeToggle("fast", "linear");
	});
	
	myJ('#baseStationIds').val('${baseStationIds}');
	myJ('#circuitIds').val('${circuitIds}');
	
	var contarctStartTime = "${pnrFeeInfoMainForm.startTime}";
	var contarctYear = contarctStartTime.substring(0,4);
	myJ('#fillYears').find('option').each(function(){
		if(myJ(this).val()==contarctYear){
			myJ(this).attr('selected','selected');
			return false;
		}
	});
	
	myJ('#fillYears').bind('change',function(){
		var year = myJ(this).val();
		var startTime = myJ(this).val()+'-01-01';
		var endTime = myJ(this).val()+'-12-31';
		myJ('input#startTime').val(startTime);
		myJ('input#endTime').val(endTime);
		myJ("div[id^='div']").each(function(){
			var yearMonthDay = myJ(this).find("input[type='text']").eq(1).val();
			var monthDay = yearMonthDay.substring(4,yearMonthDay.length);
			myJ(this).find("input[type='text']").eq(1).val(year+monthDay);
		});
	});
	
	myJ('input#showProtocols').click(function(){
		var contractStartTime = myJ('input#startTime').val();
		var contractEndTime = myJ('input#endTime').val();
		var showProtocol = window.open("pnrFeeInfoMains.do?method=contractRelateProtocol&contractStartTime="+contractStartTime+"&contractEndTime="+contractEndTime,null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
	});
	
	myJ("div[id$='div']").bind('click',function(event){
		var divId = myJ(this).attr('id');
		var divNumber = divId.substring(0,divId.length-3);
		myJ("div#div"+divNumber).fadeToggle("fast", "linear");
	});
	

	myJ('div#opBasicInfo2').bind('click',function(event){
		myJ('div#basicInfo2').fadeToggle("fast", "linear");
	});
	
	myJ("input.popupMonitorCompanyTree").bind("click",function(event) {
			var showChkFldId = "showMonitorCompany";
			var saveChkFldId = "monitorCompany";
			showPartnerTree(showChkFldId,saveChkFldId);
	});
	
	myJ("input.popupDeptTree").bind("click",function(event) {
			var showChkFldId = "";
			var saveChkFldId = "";
			if(this.id == 'showCity'){
				showChkFldId="showCity";
				saveChkFldId="city";
				showCityTree(showChkFldId,saveChkFldId);
			}else if(this.id == 'showCountry'){
				showChkFldId="showCountry";
				saveChkFldId="country";
				showCountryTree(showChkFldId,saveChkFldId);
			}else{
				//Do nothing.
			}
	});
	
	myJ('input#feeNO').blur(function(){
		var feeNO = myJ('input#feeNO').val();
		myJ.get("${app}/partner/feeInfo/pnrFeeInfoMains.do?method=checkContractFeeNO&methodType=edit&previousFeeNO=${pnrFeeInfoMainForm.feeNO}&feeNO="+feeNO,function(data){
			if(data=="right"){
				myJ('div#showDivFeeNO').html("<font color=green>合同编号不存在，可输入！</font>");
				myJ('input#showInputFeeNO').val('right');
			}else if(data=="error"){
				myJ('div#showDivFeeNO').html("<font color=red>合同编号已存在，请重新输入！</font>");
				myJ('input#showInputFeeNO').val('error');
			}else if(data=="blank"){
				myJ('div#showDivFeeNO').html();
			}
		});
	});
	
	v = new eoms.form.Validation({form:'pnrFeeInfoMainForm'});
	v.custom = function(){
		myJ('input#state').val('3');
		var canSubmit = '0';
		if(myJ('input#showInputFeeNO').val()=='error'){
			myJ('input#feeNO').focus();
			return false;
		}
		if(canSubmit&&canSubmit=='1'){
			return false;
		}
		return true;
	}
	
});

function changeRootId(xbox_obj,rootId) {   
	xbox_obj.defaultTree.root.id = rootId; 
	xbox_obj.reset();
}

function deleteWeight(protocolWeight){
	var weight = myJ(protocolWeight);
	weight.parents('tr').remove();
	var idResult = "";
	var agreementIds = document.getElementsByName("agreementId");
		for (i = 0 ; i < agreementIds.length-1; i++) {
			if(agreementIds[i] != ""){
				var myTempStr=agreementIds[i].value;
				idResult+=myTempStr.toString()+";"
			}
		}
	document.getElementById("protocolIds").value = idResult.toString();
}

function deleteBaseStation(baseStation){
	var baseStation = myJ(baseStation);
	baseStation.parents('tr').remove();
	var idResult = "";
	var baseStationIds = document.getElementsByName("baseStationId");
		for (i = 0 ; i < baseStationIds.length; i++) {
			if(baseStationIds[i] != ""){
				var myTempStr=baseStationIds[i].value;
				idResult+=myTempStr.toString()+";"
			}
		}
	document.getElementById("baseStationIds").value = idResult.toString();
}
function deleteCircuit(circuit){
	var circuit = myJ(circuit);
	circuit.parents('tr').remove();
	var idResult = "";
	var circuitIds = document.getElementsByName("circuitId");
		for (i = 0 ; i < circuitIds.length; i++) {
			if(circuitIds[i] != ""){
				var myTempStr=circuitIds[i].value;
				idResult+=myTempStr.toString()+";"
			}
		}
	document.getElementById("circuitIds").value = idResult.toString();
}

function showCityTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/partner/baseinfo/areaDeptTrees.do?method=getAreaTree';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'分公司',
			      treeChkMode:'single',
			      treeChkType:'city',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}

function showCountryTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/partner/baseinfo/areaDeptTrees.do?method=getAreaTree';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'分公司',
			      treeChkMode:'single',
			      treeChkType:'country',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}

function showPartnerTree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var deptTreeAction='${app}/partner/baseinfo/areaDeptTrees.do?method=selectPartner';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'1',
			      treeRootText:'代维公司',
			      treeChkMode:'single',
			      treeChkType:'factory',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>