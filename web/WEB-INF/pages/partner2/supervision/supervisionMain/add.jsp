<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<form action="${app }/partner2/supervisionMain.do?method=add&listType=list" id="myForm" method="post">

<input type="hidden" name="taskOwner" value=""/>
<input type="hidden" name="taskOwnerType" value="user"/>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
	<tr>
			<td class="label">手工督办项名称</td>
			<td ><input type="text" name="myTitle" class="text" alt="allowBlank:false" /></td>
	</tr>
	<tr>
			<td class="label">督办项业务类型</td>
			<td ><select  name='myType' id='myType' class='select'>
				<option value='sheet'>故障工单</option>
				<option value='work' >作业计划</option>
				<option value='danger'>隐患</option>
				<option value='monitor'>盯防</option>
			</select></td>
	</tr>
</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办时间项配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">督办即将超时时间</td>
			<td >
				  <input type="text" class="text" name="onDateTime" readonly="readonly" id="onDateTime" 
					onclick="popUpCalendar(this, this)" alt="vtype:'lessThen',link:'overDateTime',vtext:'督办即将超时时间需小于超时时间',allowBlank:false"/> 
			</td>
			<td class="label">督办超时时间</td>
			<td >
				  <input type="text" class="text" name="overDateTime" readonly="readonly" id="overDateTime" 
					onclick="popUpCalendar(this, this)" alt="vtype:'moreThen',link:'onDateTime',vtext:'督办超时时间需大于即将超时时间',allowBlank:false"/> 
			</td>
		</tr>
		<tr>
			<td class="label">
				督办项执行人
			</td>
			<td>
				<input type="text" id="taskOwnerName" class="text" alt="allowBlank:false" readonly="true" value="" />
				<input type="hidden" id="taskOwner" name="taskOwner" />
				<input type="hidden" name="taskOwnerType" value="user" />
				<eoms:xbox id="payMoneyUser" dataUrl="${app}/xtree.do?method=userFromDept" rootId="-1" rootText='督办项执行人'
						valueField="taskOwner" handler="taskOwnerName" textField="taskOwnerName" checktype="user" single="true">
				</eoms:xbox>
			</td>
		</tr>
		<tr>
			<td class="label">
				短信提醒次数
			</td>
			<td>
				<input type="text" class="text" style="width: 40" alt="allowBlank:false" value="1" readonly="readonly" />次
			</td>
			<td class="label">
				督办项即将超时周期内生成次数
			</td>
			<td>
				<input type="text" class="text" style="width: 40" alt="allowBlank:false" value="1" readonly="readonly" />次
			</td>
		</tr>
	</table>
</div>

<div id="sheetDiv" class="supervisionClass" style="display: none">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >故障工单督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				故障工单号
			</td>
			<td>
				<input type="text" name="myText1" class="text" alt="allowBlank:false" />
			</td>
			<td class="label">故障处理响应级别</td>
			<td>
				<eoms:comboBox name="myText2" id="myText2" initDicId="1010304" defaultValue="101030401" alt="allowBlank:false"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型(网络分类一级)
			</td>
			<td>
				<eoms:comboBox name="myText3" id="myText3" sub="myText4" initDicId="1010104" alt="allowBlank:false"/>
			</td>
			<td class="label">
				故障类型(网络分类二级)
			</td>
			<td>
				  <eoms:comboBox name="myText4" id="myText4" sub="myText5" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型(网络分类三级)
			</td>
			<td>
				<eoms:comboBox name="myText5" id="myText5" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				故障工单处理时限
			</td>
			<td>
				  <input type="text" class="text" name="myText6" id="myText6" readonly="readonly"   
					onclick="popUpCalendar(this, this)" alt="allowBlank:false" /> 
			</td>
			<td class="label">督办短信提醒人</td>
			<td >故障工单处理者</td>
		</tr>
	</table>
</div>
<!-- End sheetDetailDiv div -->
</div>

<div id="workDiv" class="supervisionClass" style="display: none">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >作业计划督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
	<c:if test="${addType =='work' }">
		<tr>
			<td class="label">巡检项名称</td>
			<td class="content">${myText1}</td>
			<td class="label">专业</td>
			<td class="content">${myText2}</td>
		</tr>
		<tr>
			<td class="label">网格名称</td>
			<td class="content"><eoms:id2nameDB id="${myText3}" beanId="gridDao" /></td>
			<td class="label">站点名称</td>
			<td class="content"><eoms:id2nameDB id="${myText4}" beanId="siteDao" /></td>
		</tr>
		<tr>
			<td class="label">正常值</td>
			<td class="content" colspan="3">${myText5}</td>
		</tr>
		<tr>
			<td class="label">巡检操作细则</td>
			<td class="content" colspan="3">${myText6}</td>
		</tr>
	</c:if>
	<c:if test="${addType !='work' }">
			<tr>
			<td colspan="4">
				<a href="${app }/partner/inspect/inspectTasks.do?method=showDeviceTask&privilege=all" target="_blank"  >开始新建我的作业计划督办项</a>
			</td>
		</tr>	
	</c:if>
	</table>
</div>
<!-- End workDiv div -->
</div>

<div id="dangerDiv" class="supervisionClass" style="display: none">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >隐患督办项督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td colspan="4">
				<a href="${app }/partner/inspect/inspectTasks.do?method=listUnDoForInspectUser&privilege=all" target="_blank"  >开始新建我的隐患督办项</a>
			</td>
		</tr>	
	</table>
</div>
<!-- End dangerDiv div -->
</div>


<div id="monitorDiv" class="supervisionClass" style="display: none">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >盯防督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td colspan="4">
				<a href="${app }/partner/inspect/inspectTasks.do?method=listUnDoForInspectUser&privilege=all" target="_blank"  >开始新建我的盯防督办项</a>
			</td>
		</tr>	
	</table>
</div>
<!-- End monitorDiv div -->
</div>

<div style="margin-top: 30px"> 
<input type="submit" id="operationSubmitButton"  class="btn" value="新增记录" />
<!-- 
	<input type="button" id="goToImportExcel"  class="btn" value="导入记录" />
 -->
</div>

</form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
	var myEomsForm = eoms.form;
	
	//初始化业务类型，因为有很多页面链接该页面。默认情况为工单督办的新建页面
	var myInitType = '${addType}';
	if(!myInitType || myInitType == ""){
		myJ('select#myType').val('sheet');
	}else{
		myJ('select#myType').val(myInitType);
	}

	//初始化表单，禁用不需要的验证。
	var tmpType = myJ('select#myType').val()+'Div';
	myJ('div.supervisionClass').each(
			function(){
				var tmpId = myJ(this).attr('id');
				if(tmpId==tmpType){
					myEomsForm.enableArea(tmpId);
					myJ('div#'+tmpId+'.supervisionClass').show();
				}else{
					myEomsForm.disableArea(tmpId);
					myJ('div#'+tmpId+'.supervisionClass').hide();
				}
			}
	);
	
	myJ('select#myType').bind('change',function(event){
	
		//目前只支持工单在本页面的新建，其它业务需要在弹出页中进行
		if(myJ(this).val()=='sheet'){
			myJ('input#operationSubmitButton').show();
		}else{
			myJ('input#operationSubmitButton').hide();
		}
	
		var theChoice = myJ(this).val()+'Div';
		var privateId = "";
		myJ('div.supervisionClass').each(
			function(){
				privateId = myJ(this).attr('id');
				if(privateId==theChoice){
					myJ('div#'+privateId+'.supervisionClass').show();
					myEomsForm.enableArea(privateId);
				}else{
					myJ('div#'+privateId+'.supervisionClass').hide();
					myEomsForm.disableArea(privateId);
				}
			}
		);
	});
	
	//As this function's special, so read the comments please. By Steve. 2011-10-09
	myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'addNewRecord'){
		
		}else if(buttonId == 'goToImportExcel'){
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToImportExcel&listType=goToImportExcel';
			//禁用该按钮防止多次提交
		}else{
			//Do nothing.
		}
	});
	
	// Add validation function supplied by Ext.
	v = new eoms.form.Validation({form:'myForm'});
	// Write your validation here.
v.custom = function(){
	var myType = myJ('select#myType').val();
	if(myType=='sheet'){
		var myTime =  myJ('input#myText6').val();
		var onDateTime = myJ('input#onDateTime').val();
		var overDateTime = myJ('input#overDateTime').val();
		
		if(myTime>onDateTime&&myTime<overDateTime){
			return true;
		}else{
			alert('故障完成时间需要在即将超时时间和超时时间之内！');
			return false;
		}
		return true;
	}
	return true;
};
});

</script>
<%@ include file="/common/footer_eoms.jsp"%>