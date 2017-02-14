<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
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
	rootId="1" rootText='甲方负责人'
	valueField="payUnitUser" handler="payUnitUserName"
	textField="payUnitUserName" checktype="user" single="true"></eoms:xbox>
<eoms:xbox id="collectUnitUser"
	dataUrl="${app}/xtree.do?method=userFromDept"
	rootId="2" rootText='乙方负责人'
	valueField="collectUnitUser" handler="collectUnitUserName"
	textField="collectUnitUserName" checktype="user" single="true"></eoms:xbox>

<eoms:xbox id="payMoneyUser"
	dataUrl="${app}/xtree.do?method=userFromDept" rootId="1" rootText='付款负责人'
	valueField="payMoneyUser" handler="payMoneyUserName"
	textField="payMoneyUserName" checktype="user" single="true"></eoms:xbox>

<html:form action="/pnrFeeInfoMains.do?method=saveProxySer"
	styleId="pnrFeeInfoMainForm" method="post">
	<div class="ui-widget-header ui-corner-top ui-state-default" style="cursor:pointer;" 
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
	<div class="ui-widget" style="margin-top: 30px">
		<input type="button" value="关联服务协议" class="button" id="showProtocols"/>
		<div class="ui-widget-header ui-corner-top ui-state-default"
			id="relateProtocol">
			合同关联协议信息
		</div>
	</div>
	<table class="formTable" id="myProtocolTable">
		<tr>
			<td class="label">
				协议名称
			</td>
			<td class="label">
				协议所占比重
			</td>
			<td class="label">
				查看
			</td>
			<td class="label">
				删除
			</td>
		</tr>
	</table>
	<br/>
	<br/>
	<div class="ui-widget-header ui-corner-top ui-state-default" style="cursor:pointer;"
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
					<select id='city' name="city">
					<input type="hidden" name="city" id="city" />
				</td>
				<td class="label">
					归属县公司
				</td>
				<td>
					<select id='country' name="country">
					<input type="hidden" name="country" id="country" />
				</td>
			</tr>
			<tr>
				<td class="label">
					代维公司
					<font color='red'>*</font>
				</td>
				<td>
					<select id='monitorCompany' name="monitorCompany">
					<input type="hidden" name="monitorCompany" id="monitorCompany" />
				</td>
			</tr>
		</table>
	</div>
	<br/>
	<br/>
	<font color="red" style="font-size: 14px; margin-top: 20px">第1月：</font>
	<div id="0div" class="realDiv ui-widget-header ui-corner-top ui-state-default" style="cursor:pointer;">付费阶段</div>
	<div id="div0" class="ui-widget-header ui-corner-top ui-state-default">
		<table class="formTable idChangeable" id="table1">
			<tr>
				<td class="label">
					阶段付款名称：
				</td>
				<td class="content" colspan="3">
					<html:text property="planPayName" styleId="planPayName"
						style="width:98%;maxLength:64" />
					<input type="hidden" name="planId" value="" />
				</td>

			</tr>
			<tr>
				<td class="label">
					计划付费时间：
				</td>
				<td class="content">
					<%--<html:text property="planPayTime" styleId="planPayTime"
						styleClass="text medium"
						onclick="popUpCalendar(this, this,null,null,null,false,-1);"
						alt="allowBlank:false,vtype:''" readonly="true" value="" />--%>
					<input type="text" value="<%=(String) request.getAttribute("1")%>"
						name="planPayTime" class="text" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td class="label">
					阶段付款描述：
				</td>
				<td class="content" colspan="3">
					<html:textarea property="planRemark" styleId="planRemark"
						style="width:99%;maxLength:1000" rows="3" />
				</td>
			</tr>
			
			<tr>
				<td class="label">
					考核级次公式：
				</td>
				<td class="content" colspan="3">
					<font color="red">公式中可输入两个变量，a代表当月考核最终得分，b代表当月线路代维或者基站代维应付款,例如：(100-a)/100*b</font>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					是否按照此月计划默认填写后期的付费计划：
				</td>
				<td class="content" colspan="3">
					是：<input type="checkbox" id="checkbox0" value="checkbox0"/>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					关联考核
				</td>
				<td class="content" colspan="3">
					<input type="button" id="myExamine0" class="btn"
						onclick="addExamineLevel(this.id)" value="添加考核级次关系" />
				</td>
			</tr>
		</table>
	</div>
	<br />
	<br />
	<%
		for (int i = 1; i < 12; i++) {
	%>
	<br />
	<br />
	<font color="red" style="font-size: 14px;">第<%=i + 1%>月：</font>
	<div id="<%=i%>div" class="realDiv ui-widget-header ui-corner-top ui-state-default" style="cursor:pointer;">付费阶段</div>
	<div class="realDiv ui-widget-header ui-corner-top ui-state-default" style="display:none;"
		id="div<%=i%>">
		
		<table class="formTable">
			<tr>
				<td class="label">
					阶段付款名称：
				</td>
				<td class="content" colspan="3">
					<html:text property="planPayName" styleId="planPayName"
						style="width:98%;maxLength:64" />
					<input type="hidden" name="planId" value="" />
				</td>
			</tr>
			<tr>
				<td class="label">
					计划付费时间：
				</td>
				<td class="content">
					<%--<input name="planPayTime" class="text" onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true"></input>--%>
					<input type="text" value="<%=request.getAttribute(i + 1 + "")%>"
						name="planPayTime" class="text" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td class="label">
					阶段付款描述：
				</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="planRemark"></textarea>
				</td>
			</tr>
			<tr>
				<td class="label">
					考核级次公式：
				</td>
				<td class="content" colspan="3">
					<font color="red">公式中可输入两个变量，a代表当月考核最终得分，b代表当月线路代维或者基站代维应付款，例如：(100-a)/100*b</font>
				</td>
			</tr>
			<tr>
				<td class="label">
					关联考核
				</td>
				<td class="content" colspan="3">
					<input type="button" class="realExamine btn" id="myExamine<%=i%>"
						onclick="addExamineLevel(this.id)" value="添加考核级次关系" />
				</td>
			</tr>
		</table>
	</div>
	<%
		}
	%>
	<!-- 付费阶段div计数器 -->
	<br />
	<br />
	<font color="red" style="font-size: 14px; margin-top: 20px">最终考核：</font>
	<div id="12div" class="realDiv ui-widget-header ui-corner-top ui-state-default" style="cursor:pointer;">付费阶段</div>
	<div id="div12" class="ui-widget-header ui-corner-top ui-state-default">
		<table class="formTable idChangeable" id="table1">
			<tr>
				<td class="label">
					阶段付款名称：
				</td>
				<td class="content" colspan="3">
					<html:text property="planPayName" styleId="planPayName"
						style="width:98%;maxLength:64" />
					<input type="hidden" name="planId" value="" />
				</td>

			</tr>
			<tr>
				<td class="label">
					计划付费时间：
				</td>
				<td class="content">
					<%--<html:text property="planPayTime" styleId="planPayTime"
						styleClass="text medium"
						onclick="popUpCalendar(this, this,null,null,null,false,-1);"
						alt="allowBlank:false,vtype:''" readonly="true" value="" />--%>
					<input type="text" value="<%=(String) request.getAttribute("12")%>"
						name="planPayTime" class="text" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td class="label">
					阶段付款描述：
				</td>
				<td class="content" colspan="3">
					<html:textarea property="planRemark" styleId="planRemark"
						style="width:99%;maxLength:1000" rows="3" />
				</td>
			</tr>
			<tr>
				<td class="label">
					考核级次公式：
				</td>
				<td class="content" colspan="3">
					<font color="red">公式中可输入两个变量，a代表当月考核最终得分，b代表当月线路代维或者基站代维应付款,例如：(100-a)/100*b</font>
				</td>
			</tr>
			<tr>
				<td class="label">
					关联考核
				</td>
				<td class="content" colspan="3">
					<input type="button" id="myExamine12" class="btn"
						onclick="addExamineLevel(this.id)" value="添加考核级次关系" />
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
	</div>
	
	<div id="auditDiv">
	<fieldset>
			<legend> 派发给:合同审核人 </legend> 
			<eoms:chooser id="test" type="user" config="{returnJSON:true,showLeader:true}"
			category="[{id:'taskOwner',text:'派发',childType:'user',allowBlank:true,vtext:'请选择派发对象'}]" />
		</fieldset>
</div>

	<fieldset>
		<legend>
			操作
		</legend>
		<input type="submit" id="operationSubmitButton" class="btn" value="确定" />
	</fieldset>

</html:form>

<div class="templateTr" style="display: none;">
	<table class="templateTr">
		<tr>
			<td class="label">
				考核级次
			</td>
			<td class="content" colspan="3">
				得分
				<input type="text" class="firstInput" />
				至
				<input type="text" class="secondInput"
					onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
					/>
				付款计算公式：
				<input type="text" class="thirdInput" style="margin-right:0px;"/><div style="float:right;"></div>
				<img src="${app}/nop3/images/icon.gif" onmousedown="deleteLevel(this);" style="cursor:pointer;"/>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	myJ('select#country').html('<option>请选择市</option>').attr('disabled',true);
	myJ('select#monitorCompany').html('<option>请选择市</option>').attr('disabled',true);
	myJ.ajax({
		  type:"POST",
		  url: "${app}/partner/baseinfo/partnerDepts.do?method=retriveCCC",
		  data: "flag=city&parentAreaId=10",
		  success: function(jsonMsg){
        	  var cityHtml =myJ.parseJSON(jsonMsg).city;
			  myJ('select#city').html(cityHtml);
      	  }
	});
	
	myJ('select#city').bind('change',function(event){
		var cityValue = myJ(this).val();
		if(cityValue==""){
			 myJ('select#country').html('<option value="">请选择市</option>').attr('disabled',true);
			 myJ('select#monitorCompany').html('<option value="">请选择市</option>').attr('disabled',true);	
		}else{
			myJ('select#country').html('<option value="">载入中...</option>').attr('disabled',false);
			myJ('select#monitorCompany').html('<option value="">载入中...</option>').attr('disabled',false);
			myJ.ajax({
				  type:"POST",
		  		  url: "${app}/partner/baseinfo/partnerDepts.do?method=retriveCCC",
				  data: "flag=country&parentAreaId="+cityValue,
		  		  success: function(jsonMsg){
        		  var countryHtml =myJ.parseJSON(jsonMsg).country;
        		  var monitorCompanyHtml =myJ.parseJSON(jsonMsg).monitorCompany;
			 	  myJ('select#country').html(countryHtml);
			 	  myJ('select#monitorCompany').html(monitorCompanyHtml);
      	  		}
			});
		}
	});
	//Bind jquery function. todoo
		myJ('#operateType').bind('change',function(event){
		if(this.value == 'draft'){
			myJ('#auditDiv').hide();
		}else{
			myJ('#auditDiv').show();
		}
	});
	
	myJ('input#showProtocols').click(function(){
		var contractStartTime = myJ('input#startTime').val();
		var contractEndTime = myJ('input#endTime').val();
		var showProtocol = window.open("pnrFeeInfoMains.do?method=contractRelateProtocol&contractStartTime="+contractStartTime+"&contractEndTime="+contractEndTime,null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
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
	
	myJ("div[id$='div']").bind('click',function(event){
		var divId = myJ(this).attr('id');
		var divNumber = divId.substring(0,divId.length-3);
		myJ("div#div"+divNumber).fadeToggle("fast", "linear");
	});
	myJ("input[name='planPayName']").each(function(){
		var planPayNameValue = myJ(this).val();
		myJ(this).blur(function(){
			if(planPayNameValue.trim()==null){
				myJ(this).parents("div[id^='div']").find('table.templateTr').remove();
				myJ(this).parents("div[id^='div']").find('textarea').val('');
			}
		});
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
	
	myJ('#checkbox0').change(function(){
		myJ(this).attr('disabled',true);
		if(myJ(this).attr('checked')==true){
			var parentDiv = myJ('div#div0');
			var planRemark = parentDiv.find('#planRemark').val();
			var i=0;
			parentDiv.find('input.firstInput').each(function(){
				i++;
			});
			var myArray=new Array();
			for(var j=0;j<i*3;j++){
				myArray[j]=parentDiv.find('table.templateTr').find("input[type='text']").eq(j).val();
			}
			myJ("div[id^='div'][id!='div0']").each(function(){
				for(var k=0;k<i;k++){
					var identifier = myJ(this).find("input[type='button']").attr('id');
					addExamineLevel(identifier);
				}
				myJ(this).find('textarea').val(planRemark);
				var month=parseInt(myJ(this).attr('id').substring(3))+1;
				if(month==13){
					myJ(this).find("input[type='text']").eq(0).val('最终考核付款计划');
				}else{
					myJ(this).find("input[type='text']").eq(0).val('第'+month+'月付款计划');
				}
				for(var z=0;z<myArray.length;z++){
					myJ(this).find('table.templateTr').find("input[type='text']").eq(z).val(myArray[z]);
				}
			});
			myJ(this).attr('disabled',false);
		}else{
			myJ("div[id^='div'][id!='div0']").each(function(){
				var timeInput = myJ(this).find("input[type='text']").eq(1).val();
				myJ(this).find("input[type='text']").val('');
				myJ(this).find("input[type='text']").eq(1).val(timeInput);
				myJ(this).find('textarea').val('');
				myJ(this).find('table.templateTr').remove();
			});
			myJ(this).attr('disabled',false);
		}
	});
	

	v = new eoms.form.Validation({form:'pnrFeeInfoMainForm'});
	v.custom = function(){
		myJ('input#state').val('3');
		var canSubmit = '0';
		myJ("input[class='secondInput'][name^='div']").each(function(){
			var score = myJ(this).val();
			var lastScore = myJ(this).parents("div[id^='div']").find('table.templateTr').last().find('input.secondInput');
			var prevInputValue = myJ(this).parents('table.templateTr').find('input.firstInput').val();
			if(parseInt(score) > 100){
				myJ(this).focus();
				alert("得分不能超过100，请重新填写");
				canSubmit='1';
				return false;
			}else if(parseInt(score)<= parseInt(prevInputValue)){
				myJ(this).focus();
				alert("得分区间不连续，请重新填写");
				canSubmit='1';
				return false;
			}else if(parseInt(lastScore.val()) != 100){
				lastScore.focus();
				alert('最后一项考核得分需为100,已自动设置为100');
				lastScore.val('100');
				canSubmit='1';
				return false;
			}else if(score==''){
				myJ(this).focus();
				alert('分数不能为空，请填写');
				canSubmit='1';
				return false;
			}
		});
		myJ("input[class='thirdInput'][name^='div']").each(function(){
			var s = myJ(this).val();
			var patt1=new RegExp("a","g");
			var patt2=new RegExp("b","g");
			s=s.replace(patt1,"(22.36247003+1)");
			s=s.replace(patt2,"(52.15+0.0000001)");
			try 
			{ 	
				if(!isNaN(eval(s))&&eval(s)!='Infinity')
				{ 	
					myJ(this).focus();
					alert('输入公式不合法，请检查！');
					canSubmit='1';
					return false;
				}
			} 
			catch (ex) 
			{ 
				myJ(this).focus();
				alert('输入公式不合法，请检查！');
				canSubmit='1';
				return false;
			} 
		});
		myJ("input[name='protocolWeight']").each(function(){
			if(myJ(this).val()==''){
				myJ(this).focus();
				alert('请填写协议所占比重');
				canSubmit='1';
			}
		});
		var agreementWeight = 0;
		myJ('table#myProtocolTable').find("input[type='text']").each(function(){
			agreementWeight += parseFloat(myJ(this).val());
		});
		if(parseFloat(agreementWeight)>100){
			alert('协议所占比重和需为100，现在是'+agreementWeight+'，请检查！');
			myJ('table#myProtocolTable').find("input[type='text']").eq(0).focus();
			return false;
		}
		if(myJ("input[name='taskOwner']").val()==''&&myJ('#operateType').val()!='draft'){
			alert('请选择合同审核人');
			return false;
		}
		if(myJ('input#showInputFeeNO').val()=='error'){
			myJ('input#feeNO').focus();
			return false;
		}
		if(canSubmit=='1'){
			return false;
		}
		return true;
	}
});

function addExamineLevel(identifier){
	var myIdentifier = identifier.substring(9,identifier.length);
	var myTr = myJ('div.templateTr').html();
	myJ('#div'+ myIdentifier).append(myTr);
	
	myJ('#div'+ myIdentifier).find('table.templateTr').last().find("input[type='text']").attr('name','div'+myIdentifier);
	
	var myDiv = myJ('input#'+identifier).parents("div[id^='div']");
	var firstInput = myDiv.find('table.templateTr').eq(0).find("input[type='text']").eq(0);
	firstInput.val('0');
	firstInput.attr('readonly','readonly');
	
	var nextFirstInput = myDiv.find('table.templateTr').last().find('input.firstInput');
	var prevSecondInput = nextFirstInput.parents('table.templateTr').prev().find('input.secondInput');
	nextFirstInput.attr('readonly','readonly');
	nextFirstInput.attr('value',prevSecondInput.val());
	
	myDiv.find("input[class='secondInput']").keyup(function(){
		myJ(this).parents('table.templateTr').next().find("input[class='firstInput']").val(myJ(this).val());
	});
	myDiv.find("input[type='text']").last().keyup(function(){
		var s = myJ(this).val();
		var patt1=new RegExp("a","g");
		var patt2=new RegExp("b","g");
		s=s.replace(patt1,"(22.362473+1)");
		s=s.replace(patt2,"(52.15+0.0000001)");
		try 
		{ 
			if(!isNaN(eval(s))&&eval(s)!='Infinity') 
			{   
				myJ(this).parents('table.templateTr').find('div').html("<font color='green'>输入公式合法，可进行操作！</font>");
			}else{
				myJ(this).parents('table.templateTr').find('div').html("<font color='red'>输入公式不合法，请检查！</font>");
			}
		} 
		catch (ex) 
		{ 
			myJ(this).parents('table.templateTr').find('div').html("<font color='red'>输入公式不合法，请检查！</font>");
		} 
	});
}
function deleteLevel(childInput){
	var deleteDiv = myJ(childInput).parents("div[id^='div']");
	myJ(childInput).parents('table.templateTr').remove();
	var holdFirstInput = deleteDiv.find('table.templateTr').eq(0).find('input.firstInput');
	holdFirstInput.val('0');
	holdFirstInput.attr('readonly','readonly');
}

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
</script>
<%@ include file="/common/footer_eoms.jsp"%>