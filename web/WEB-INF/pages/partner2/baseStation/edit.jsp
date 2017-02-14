<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>


<form action="${app }/partner2/baseStation/baseStationMain.do?method=edit&listType=list" id="baseStationMainForm" method="post">

<!-- 弹出树图隐藏域 开始 -->
<div id="deptview" class="hide"></div>
<input type="hidden" id="treeBtnId" name="treeBtnId" value=""/>
<!-- 弹出树图隐藏域 结束 -->
<input type="hidden" name="taskOwner" value="${baseStationMain.taskOwner}"/>
<input type="hidden" name="taskOwnerType" value="${baseStationMain.taskOwnerType }"/>
<input type="hidden" name="id" value="${baseStationMain.id }"/>

<div class="ui-widget-header ui-corner-top ui-state-default " id="opBasicInfo">分公司和归属县公司</div>
<div id="basicInfo">
<table class="formTable" >
	<tr>
			<td class="label">分公司*</td>
			<td>
				<input class="text popupDeptTree" type="text" readonly="readonly" id="showCity" 
				value="<eoms:id2nameDB id="${baseStationMain.city}" beanId="tawSystemAreaDao" />"
				 alt="allowBlank:false,vtext:'请选择分公司'"  />
				<input type="hidden" name="city" id="city" value="${baseStationMain.city }" /> 
			</td>
			<td class="label">归属县公司*</td>
			<td>
				<input class="text popupDeptTree" type="text" readonly="readonly" id="showCountry" 
				value="<eoms:id2nameDB id="${baseStationMain.country}" beanId="tawSystemAreaDao" />"
				alt="allowBlank:false,vtext:'请选择归属县公司'" />
				<input type="hidden" name="country" id="country" value="${baseStationMain.country }" /> 				
			</td>
		</tr>
	<tr>
		<td class="label">代维公司*</td>
			<td>
			<input class="text popupMonitorCompanyTree" style="width:300" type="text" readonly="readonly" id="showMonitorCompany"
			value="<eoms:id2nameDB id="${baseStationMain.monitorCompany}" beanId="tawSystemAreaDao" />"
				alt="allowBlank:false,vtext:'请选择代维公司'" />
				<input type="hidden" name="monitorCompany" id="monitorCompany"  value="${baseStationMain.monitorCompany }"/> 
			</td>
	</tr>
		</table>
</div>


<div class="ui-widget-header ui-corner-top ui-state-default" style="margin-top: 15px">年度和月度信息</div>
<div>
<table class="formTable" >
	<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1'
				name='yearFlag' id='yearFlag'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011' selected="selected">2011年</option>
				<option value='2012'>2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1' name="monthFlag"
				id="monthFlag" class='select'>
				<option value="default">请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>
		</table>
</div>

<c:if test="${templateId!=null}">
<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">关联考核模板信息
<table class="formTable" >
			<tr>
				<td class="label">
					关联考核模板
				</td>
				<td id="templateLink">
					<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${templateId}" target="_blank">
						${templateName}
					</a>
				</td>
				<input type="button" id="${baseStationMain.id}" name="showAppraisals" class="btn" value="更改考核模板"/>
			</tr>
		</table>
</div>
</c:if>

<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">代维基站维护费用
<table class="formTable" >
			<tr>
				<td class="label">
					维护费用（元/个）*
				</td>
				<td>
					<input type="text" name="stationMaintenancePrice" class="text"
						value="${baseStationMain.stationMaintenancePrice }"
						alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
			</tr>
		</table>
</div>

<div class="ui-widget-header ui-corner-top ui-state-default " style="margin-top: 15px">代维基站信息
<table class="formTable" >
			<tr>
				<td class="label">
					2G宏基站总数(个)*
				</td>
				<td>
					<input type="text" name="station1Sum" class="text" 
					value="${baseStationMain.station1Sum }"
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					2G宏基站代维数*
				</td>
				<td>
					<input type="text" name="station1Monitor" class="text" 
					value="${baseStationMain.station1Monitor }"
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					2G宏基站增减数*
				</td>
				<td>
					<input type="text" name="station1Minus" class="text"  
					value="${baseStationMain.station1Minus }"
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					2G宏基站设备差异系数*
				</td>
				<td>
					<input type="text" name="station1Differ" class="text"  
					value="${baseStationMain.station1Differ }"
					alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
			</tr>
			<tr>
				<td class="label">
					边际网基站总数(个)*
				</td>
				<td>
					<input type="text" name="station2Sum" class="text"
					value="${baseStationMain.station2Sum }"  
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					边际网基站代维数*
				</td>
				<td>
					<input type="text" name="station2Monitor" class="text"
					value="${baseStationMain.station2Monitor }"   
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					边际网基站增减数*
				</td>
				<td>
					<input type="text" name="station2Minus" class="text"  
					value="${baseStationMain.station2Minus }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					边际网基站差异系数*
				</td>
				<td>
					<input type="text" name="station2Differ" class="text"  
					value="${baseStationMain.station2Differ }"
					alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
			</tr>
			<tr>
				<td class="label">
					独立TD宏基站总数(个)*
				</td>
				<td>
					<input type="text" name="station3Sum" class="text" 
					value="${baseStationMain.station3Sum }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					独立TD宏基站代维数*
				</td>
				<td>
					<input type="text" name="station3Monitor"  class="text" 
					value="${baseStationMain.station3Monitor }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					独立TD宏基站增减数*
				</td>
				<td>
					<input type="text" name="station3Minus" class="text" 
					value="${baseStationMain.station3Minus }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					独立TD宏基站差异系数*
				</td>
				<td>
					<input type="text" name="station3Differ" class="text"  
					value="${baseStationMain.station3Differ }"
					alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
			</tr>
			<tr>
				<td class="label">
					共址TD宏基站总数(个)*
				</td>
				<td>
					<input type="text" name="station4Sum" class="text" 
					value="${baseStationMain.station4Sum }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					共址TD宏基站代维数*
				</td>
				<td>
					<input type="text" name="station4Monitor" class="text" 
					value="${baseStationMain.station4Monitor }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					共址TD宏基站增减数*
				</td>
				<td>
					<input type="text" name="station4Minus" class="text" 
					value="${baseStationMain.station4Minus }" 
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					共址TD宏基站差异系数*
				</td>
				<td>
					<input type="text" name="station4Differ" class="text"  
					value="${baseStationMain.station4Differ }"
					alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
			</tr>
			<tr>
				<td class="label">
					直放站总数(个)*
				</td>
				<td>
					<input type="text" name="station5Sum" class="text" 
					value="${baseStationMain.station5Sum }"
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					直放站代维数*
				</td>
				<td>
					<input type="text" name="station5Monitor" class="text" 
					value="${baseStationMain.station5Monitor }"
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
				<td class="label">
					直放站增减数*
				</td>
				<td>
					<input type="text" name="station5Minus" class="text" 
					value="${baseStationMain.station5Minus }"
					alt="re:/^[0-9]+$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
					<td class="label">
					直放站差异系数*
				</td>
				<td>
					<input type="text" name="station5Differ" class="text"  
					value="${baseStationMain.station5Differ }"
					alt="re:/^[0-9]+(.[0-9]+)?$/,re_vt:'请输入正整数或小数',allowBlank:false,vtext:'限整数'" />
				</td>
			</tr>
		</table>
</div>


<div style="margin-top: 30px"> 
<input type="hidden" id="templateId" value="" />
<input type="submit" id="operationSubmitButton"  class="btn" value="修改记录" />
<input type="button" id="goToImportExcel"  class="btn" value="导入记录" />
</div>

</form>

<script type="text/javascript">
var myJ = $.noConflict();
myJ(function() {

	myJ("input[name='showAppraisals']").click(function(event){
		//点击后防止多次提交
		myJ(this).attr('disabled','disabled');
		var _AppraisalsWindow = window.open("${app}/partner2/appraisal.do?method=list&operateType=edit",null,"left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes");
		//窗口打开后恢复按钮
		myJ(this).removeAttr('disabled');
	});
	
	//初始化年、月的值
	myJ('select#yearFlag').val('${baseStationMain.yearFlag }');
	myJ('select#monthFlag').val('${baseStationMain.monthFlag }');
	

	myJ('div#opBasicInfo').bind('click',function(event){
		myJ('div#basicInfo').fadeToggle("fast", "linear");
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
	v = new eoms.form.Validation({form:'baseStationMainForm'});
	// Write your validation here.
	v.custom = function(){
		return true;
	};
});


</script>
<%@ include file="/common/footer_eoms.jsp"%>