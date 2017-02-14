<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<div id="qucikNavRef" style="margin-bottom: 15px">
	<a class="linkDraft" href="${URI }?method=list">
		返回"督办项配置"列表
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"督办项配置详情页面"</span>	
</div>

<form action="${app }/partner2/supervisionConfig.do?method=edit" id="myForm" method="post">

<!-- Public Token Start -->
<input type="hidden" name="id" value="${supervisionConfig.id }"/>
<input type="hidden" name="status" value="${supervisionConfig.status }"/>
<input type="hidden" name="verticalStatus" value="${supervisionConfig.verticalStatus }"/>
<input type="hidden" name="taskOwner" value="${supervisionConfig.taskOwner }"/>
<input type="hidden" name="taskOwnerType" value="${supervisionConfig.taskOwnerType }"/>
<input type="hidden" name="createUserId" value="${supervisionConfig.createUserId }"/>
<input type="hidden" name="createDate" value="${supervisionConfig.createDate }"/>
<input type="hidden" name="yearFlag" value="${supervisionConfig.yearFlag }"/>
<input type="hidden" name="monthFlag" value="${supervisionConfig.monthFlag }"/>
<input type="hidden" name="dayFlag" value="${supervisionConfig.dayFlag }"/>
<!-- Public Token End -->


<input type="hidden" name="mySource" value="${supervisionConfig.mySource }"/>
<input type="hidden" name="myType" value="${supervisionConfig.myType }"/>
<input type="hidden" name="isDeleted" value="${supervisionConfig.isDeleted }"/>


<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" >
	<tr>
			<td class="label">督办设置名称</td>
			<td ><input type="text" name="configName" class="text" alt="allowBlank:false" value="${supervisionConfig.configName }" /></td>
	</tr>
	<tr>
			<td class="label">督办项业务类型</td>
			<td >${supervisionConfig.myType }</td>
	</tr>
</table>
</div>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办时间项配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">督办即将超时时长(最小计算单位：分钟)</td>
			<td >
				 <input type="text" class="text" name="onTimeLimitDay" value="${onTimeLimitDay }" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>天   
				 <input type="text" class="text" name="onTimeLimitHour" value="${onTimeLimitHour }" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>小时   
				 <input type="text" class="text" name="onTimeLimitMinute" value="${onTimeLimitMinute }" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>分钟  
			</td>
			<td class="label">督办超时时长(最小计算单位：分钟)</td>
			<td >
				 <input type="text" class="text" name="overTimeLimitDay" value="${overTimeLimitDay }" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>天   
				 <input type="text" class="text" name="overTimeLimitHour" value="${overTimeLimitHour }" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入正整数',allowBlank:false,vtext:'限整数'"/>小时   
				 <input type="text" class="text" name="overTimeLimitMinute" value="${overTimeLimitMinute }" style="width: 40" alt="re:/^[0-9]+$/,re_vt:'请输入整数',allowBlank:false,vtext:'限整数'"/>分钟  
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

<c:if test="${supervisionConfig.myType == 'sheet' }">

<div id="sheetDiv" class="supervisionClass">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >故障工单督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">
				故障工单号(模糊匹配)
			</td>
			<td>
				<input type="text" name="myText1" class="text" alt="allowBlank:false" value="${supervisionConfig.myText1 }" />
			</td>
			<td class="label">故障处理响应级别</td>
			<td>
				<eoms:comboBox name="myText2" id="myText2" initDicId="1010304" defaultValue="${supervisionConfig.myText2 }" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型(网络分类一级)
			</td>
			<td>
				<eoms:comboBox name="myText3" id="myText3" sub="myText4" initDicId="1010104" defaultValue="${supervisionConfig.myText3 }" alt="allowBlank:false"/>
			</td>
			<td class="label">
				故障类型(网络分类二级)
			</td>
			<td>
				  <eoms:comboBox name="myText4" id="myText4" sub="myText5" initDicId="${supervisionConfig.myText3 }" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型(网络分类三级)
			</td>
			<td>
				<eoms:comboBox name="myText5" id="myText5" initDicId="${supervisionConfig.myText4 }" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">督办短信提醒人</td>
			<td >故障工单处理者</td>
		</tr>
	</table>
</div>
<!-- End sheetDetailDiv div -->
</div>
</c:if>


<c:if test="${supervisionConfig.myType == 'work' }">
	<div id="workDiv" class="supervisionClass" style="display: none">
<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >作业计划督办详细配置</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label">作业计划Test</td>
			<td ><input type="text" name="myText1" class="text" alt="allowBlank:false" /></td>
		</tr>	
		<tr>
			<td class="label" colspan="2">working...</td>
		</tr>
	</table>
</div>
<!-- End workDiv div -->
</div>
</c:if>



<div style="margin-top: 30px"> 
<input type="submit" id="operationSubmitButton"  class="btn" value="修改记录" />
<!-- 
	<input type="button" id="goToImportExcel"  class="btn" value="导入记录" />
 -->
</div>

</form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {

	//初始化下拉菜单的默认值
	var myText4 = ${supervisionConfig.myText4};
	var myText5 = ${supervisionConfig.myText5};
	myJ('select#myText4').removeAttr('disabled').val(myText4);
	myJ('select#myText5').removeAttr('disabled').val(myText5);
	
	var myEomsForm = eoms.form;

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
		return true;
	};
});
</script>
<%@ include file="/common/footer_eoms.jsp"%>