<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<eoms:xbox id="payUnit" dataUrl="${app}/xtree.do?method=dept&perspective=partyA" rootId="1"
	rootText='甲方' valueField="payUnit" handler="payUnitName"
	textField="payUnitName" checktype="dept" single="true"></eoms:xbox>
<eoms:xbox id="collectUnit" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="" rootText='乙方' valueField="collectUnit"
	handler="collectUnitName" textField="collectUnitName" checktype="dept"
	single="true"></eoms:xbox>
<eoms:xbox id="payMoneyUser"
	dataUrl="${app}/xtree.do?method=userFromDept" rootId="1" rootText='付款负责人'
	valueField="payMoneyUser" handler="payMoneyUserName"
	textField="payMoneyUserName" checktype="user" single="true"></eoms:xbox>

<html:form action="/pnrFeeInfoMains.do?method=saveProxyContract"
	styleId="pnrFeeInfoMainForm" method="post">
	<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" id="opBasicInfo">合同基本信息</div>
	<div id="basicInfo" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
		<table class="formTable">
			<tr>
				<td class="label">
					合同名称&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content" colspan="3">
					<input type="text" name="contractName" class="text"
						value="${pnrFeeInfoMainForm.payUnit}" style="width: 98%"
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
						readonly="true" >
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
						readonly="true" />
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
					<input type="text" readonly="readonly" name="startTime" id="startTime" value="${thisYear}-01-01"
					alt="allowBlank:false,vtext:'',maxLength:50" style="width:95%;" class="text"/>
				</td>
				<td class="label" style="vertical-align: middle">
					合同结束时间：&nbsp;
					<font color='red'>*</font>
				</td>
				<td class="content">
					<input type="text" readonly="readonly" name="endTime" id="endTime" value="${thisYear}-12-31"
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
	<!--  
	<div id="periodExamineControlDiv" class="ui-widget">
	<div style="margin-top: 20px; padding: 0 .7em;"
		class="ui-state-highlight ui-corner-all"><span
		style="float: left; margin-right: .3em;"
		class="ui-icon ui-icon-carat-1-e"></span> 关联的服务协议类型可以有多种类型，但相同类型的服务协议所对应的代维公司必须是唯一的。</div>
	</div>
	-->
	
	<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >合同关联代维规模信息</div>
	<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
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
	</table>
	<input type="button" value="关联基站代维规模数据" class="button" id="showBaseStationScales" />&nbsp;&nbsp;
		<input type="button" value="关联线路代维规模数据" class="button" id="showCircuitScales" />
	</div>
	<!-- 
	<input type="text" id="serverTypeArray"/>
	<input type="text" id="monitorCompanyArray"/>
	<input type="text" name="abilityType" id="abilityType"/>
	 -->
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
	<input type="hidden" name="baseStationIds" id="baseStationIds"/>
	<input type="hidden" name="circuitIds" id="circuitIds"/>

	<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >合同相关操作</div>
	<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
		<table class="formTable">
			<tr>
				<td class="label">
					操作类型
				</td>
				<td class="content">
					<select id="operateType" name="operateType">

						<option id="submit20" value="draft" >
							保存草稿
						</option>
						<option value="submit" selected="selected">
							提交审核
						</option>
					</select>
				</td>
			</tr>
		</table>
	
	<div id="auditDiv">
	<fieldset>
			<legend> 派发给:合同审核人 </legend> 
			<eoms:chooser id="test" type="user" config="{returnJSON:true,showLeader:true}"
			panels="[{text : '联通公司部门与人员树',dataUrl : '/xtree.do?method=userFromDept&perspective=partyA' }]"
			category="[{id:'taskOwner',text:'派发',childType:'user',allowBlank:true,vtext:'请选择派发对象'}]" />
		</fieldset>
	</div>

	<input type="submit" id="operationSubmitButton" class="btn" value="确定" />
</html:form>

<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>


<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
	myJ('#operateType').bind('change',function(event){
		if(this.value == 'draft'){
			myJ('#auditDiv').hide();
		}else{
			myJ('#auditDiv').show();
		}
	});
	
	myJ('input#showBaseStationScales').click(function(){
		var contractStartTime = myJ('input#startTime').val();
		var contractEndTime = myJ('input#endTime').val();
		myJ(this).attr("disabled","disabled");
		var showBaseStation = window.open("${app}/partner2/baseStation/baseStationMain.do?method=list&isRelated=true&contractStartTime="+contractStartTime+"&contractEndTime="+contractEndTime,null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		myJ(this).removeAttr("disabled");
	});
	
	myJ('input#showCircuitScales').click(function(){
		var contractStartTime = myJ('input#startTime').val();
		var contractEndTime = myJ('input#endTime').val();
		myJ(this).attr("disabled","disabled");
		var showCircuit = window.open("${app}/partner2/circuit/circuitLength.do?method=listLegacy&isRelated=true&contractStartTime="+contractStartTime+"&contractEndTime="+contractEndTime,null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		myJ(this).removeAttr("disabled");
	});
	
	myJ('#fillYears').bind('change',function(){
		var startTime = myJ(this).val()+'-01-01';
		var endTime = myJ(this).val()+'-12-31';
		myJ('input#startTime').val(startTime);
		myJ('input#endTime').val(endTime);
	});
	myJ('input#feeNO').blur(function(){
		var feeNO = myJ('input#feeNO').val();
		myJ.get("${app}/partner/feeInfo/pnrFeeInfoMains.do?method=checkContractFeeNO&feeNO="+feeNO,function(data){
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

	myJ('div#opBasicInfo').bind('click',function(event){
		myJ('div#basicInfo').fadeToggle("fast", "linear");
	});
	
	myJ('div#opBasicInfo2').bind('click',function(event){
		myJ('div#basicInfo2').fadeToggle("fast", "linear");
	});
	
	
	v = new eoms.form.Validation({form:'pnrFeeInfoMainForm'});
	v.custom = function(){
		myJ('input#state').val('3');
		if(myJ("input[name='taskOwner']").val()==''&&myJ('#operateType').val()!='draft'){
			alert('请选择合同审核人');
			return false;
		}
		if(myJ('input#showInputFeeNO').val()=='error'){
			myJ('input#feeNO').focus();
			return false;
		}
		if(canSubmit&&canSubmit=='1'){
			return false;
		}
		return true;
	}
	
	
	myJ('input#payUnitUserName').bind('click',function(event){
		if(myJ('input#payUnit').val()==''){
			alert('请先选择甲方部门');
			return;
		}
		showPartyATree('payUnitUserName','payUnitUser');
	});
	myJ('input#collectUnitUserName').bind('click',function(event){
		if(myJ('input#collectUnit').val()==''){
			alert('请先选择乙方部门');
			return;
		}
		showPartnerTree('collectUnitUserName','collectUnitUser');
	});
	
});

function changeRootId(xbox_obj,rootId) {   
	xbox_obj.defaultTree.root.id = rootId; 
	xbox_obj.reset();
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

function showPartyATree(showChkFldId,saveChkFldId) {
			var deptViewer = new Ext.JsonView("deptview",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>${eoms:a2u('没有选择项目')}</div>'
					}
					);
			var data = "[{id:'',name:'<eoms:id2nameDB id="" beanId="tawSystemUserDao"/>',nodeType:'user'}]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			var node = document.getElementById('payUnit').value;
			 var deptTreeAction='${app}/xtree.do?method=userFromDept&node='+node;
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'甲方负责人',
			      treeChkMode:'single',
			      treeChkType:'user',
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
			var data = "[{id:'',name:'<eoms:id2nameDB id="" beanId="tawSystemUserDao"/>',nodeType:'user'}]";
			deptViewer.jsonData = eoms.JSONDecode(data);
			deptViewer.refresh();
			
			 var node = document.getElementById('collectUnit').value;
			 var deptTreeAction='${app}/xtree.do?method=userFromDept&node='+'2';
		   	 deptetree = new xbox({
		      	  btnId:"treeBtnId",
			      dlgId:'dlg3',
			      treeDataUrl:deptTreeAction,
			      treeRootId:'-1',
			      treeRootText:'乙方负责人',
			      treeChkMode:'single',
			      treeChkType:'user',
			      showChkFldId:showChkFldId,
			      saveChkFldId:saveChkFldId,
			      viewer:deptViewer
		    });
		    deptetree.onBtnClick();
		    deptetree=null;
}

</script>
<%@ include file="/common/footer_eoms.jsp"%>