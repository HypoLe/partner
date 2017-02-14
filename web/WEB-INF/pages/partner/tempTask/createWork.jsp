<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<eoms:xbox id="toOrgUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="2" 
	  	rootText='执行者' 
	  	valueField="toOrgUser" handler="toOrgUserName"
		textField="toOrgUserName"
		checktype="user" single="false"		
		data= "${orgData}"
	  ></eoms:xbox>
	  
<html:form action="/pnrTempTaskWorks.do?method=save" styleId="pnrTempTaskWorkForm" method="post"> 
		<table class="formTable">
		<caption>
			<div class="header center">服务工作任务及考核内容</div>
		</caption>		
			<tr>
				<td class="label" style="vertical-align:middle">
					工作内容
				</td>
				<td class="content">
					<textarea name="workContent" id="workContent" maxLength="1000" rows="1" style="width:95%;" alt="allowBlank:false,vtext:'请填写工作内容',maxLength:1000" ></textarea>			
					<html:hidden property="workId" value="" />
				</td>
				<td class="label" style="vertical-align:middle">
					执行人
				</td>
				<td class="content">
				<input type="text" name="toOrgUserName" id="toOrgUserName"  style="width:95%;"  value="" maxLength="100" class="text medium" alt="allowBlank:false,vtext:'请填写执行人...'"  readonly="readonly" />		
		    	<input type="hidden" name="toOrgUser" id="toOrgUser" value="" maxLength="32" class="text medium" />		
				</td>				
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					工作完成标准
				</td>
				<td class="content" colspan="3">
					<textarea name="workStandard" id="workStandard" maxLength="1000" rows="1" style="width:97%;"  alt="allowBlank:false,vtext:'请填写工作完成标准',maxLength:255"  ></textarea>			
				</td>				
			</tr>			
			<tr>
				<td class="label" style="vertical-align:middle">
					工作任务类型
				</td>
				<td class="content">
						<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId=""
							 selectId="workType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务类型'" onchange="changeWorType(this.value);"/>			
				</td>	
				<td class="label" style="vertical-align:middle">
					工作任务执行周期
				</td>
				<td class="content">
						<eoms:dict key="dict-partner-agreement" dictId="workCycle" defaultId=""
							 selectId="workCycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择工作任务执行周期'" />			
				</td>									
			</tr>				
			<tr>
				<td class="label" style="vertical-align:middle">
					考核开始时间
				</td>
				<td class="content">
					<html:text property="workEvaStartTime" styleId="workEvaStartTime"
								styleClass="text medium" style="width:95%;" 
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:''" readonly="true"  value="" />
				</td>
				<td class="label" style="vertical-align:middle">
					考核结束时间
				</td>
				<td class="content">
					<html:text property="workEvaEndTime" styleId="workEvaEndTime"
								styleClass="text medium" style="width:97%;" 
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:'moreThen',link:'workStartTime',vtext:'结束时间应晚于开始时间'" readonly="true"  value="" />
				</td>			
			</tr>	
			<tr>
				<td class="label" style="vertical-align:middle">
					考核指标名称
				</td>
				<td class="content">
					<html:text property="workEvaName" styleClass="text medium"  style="width:95%;" value=""  alt="allowBlank:false,vtext:'请填写考核指标名称',maxLength:50" />
				</td>	
				<td class="label" style="vertical-align:middle">
					算法分类
				</td>
				<td class="content">
					<eoms:dict key="dict-eva" dictId="algorithmType" defaultId=""
							 selectId="algorithmType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择算法类型'" onchange="changeAlgorithmType();"/>	
				</td>
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td id='algorithm0' class="content" colspan="3">
					<html:textarea property="workEvaContent" styleClass="textarea max"  alt="maxLength:1000" value="" title="指标详细算法" ></html:textarea>
				</td>
				<td id='algorithm1' class="content" colspan="3">
					<html:text property="rateFullSpan" styleClass="text medium" style="width:50px;" value="100" readonly="true"  />%~<html:text property="rateMiddle" styleClass="text medium" style="width:50px;" value=""  onblur="changeRate('rateFullSpan','rateMiddleSpan','rateZeroSpan',this);"/>%得满分
					<br>
					<html:text property="rateMiddleSpan" styleClass="text medium" style="width:50px;" value=""  onblur="changeRate('rateFullSpan','rateMiddle','rateZeroSpan',this);"/>%~<html:text property="rateZeroSpan" styleClass="text medium" style="width:50px;" value="0"  />%不得分
					<br>
					<font color='red'><span>注意：该范围向下包容，例如80%~60%的意思是：80%>当前值=>60%</span></font>
				</td>
				<td id='algorithm2' class="content" colspan="3">
					<html:text property="rateOneSpan" styleClass="text medium" style="width:50px;" value="100"  readonly="true" />%~<html:text property="rateTwo" styleClass="text medium" style="width:50px;" value=""  onblur="changeRate('rateOneSpan','rateTwoSpan','rateThreeSpan',this);"/>%得<html:text property="workScoreOne" styleClass="text medium"  style="width:50px;" value="" />分（百分制）
					<br>
					<html:text property="rateTwoSpan" styleClass="text medium" style="width:50px;" value=""  onblur="changeRate('rateOneSpan','rateTwo','rateThreeSpan',this);"/>%~<html:text property="rateThree" styleClass="text medium"  style="width:50px;" value=""  onblur="changeRate('rateTwoSpan','rateThreeSpan','rateFourSpan',this);"/>%得<html:text property="workScoreTwo" styleClass="text medium"  style="width:50px;" value="" />分（百分制）
					<br>
					<html:text property="rateThreeSpan" styleClass="text medium" style="width:50px;" value=""  onblur="changeRate('rateTwoSpan','rateThree','rateFourSpan',this);"/>%~<html:text property="rateFourSpan" styleClass="text medium" style="width:50px;" value="0"  readonly="true" />%得<html:text property="workScoreThree" styleClass="text medium"  style="width:50px;" value="" />分（百分制）
					<br>
				<font color='red'><span>注意：该范围向下包容，例如80%~60%的意思是：80%>当前值=>60%</span></font>
				
				</td>					
			</tr>
			<tr>	
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					<html:text property="workEvaWeight" alt="re:/^-?[0-9]+\.{0,1}[0-9]{0,2}$/,re_vt:'请输入分数',allowBlank:false,vtext:'请填写所占分数'"  styleId="workEvaWeight"  styleClass="text medium" onblur="confirmWeight();"  style="width:18%;" value="" />&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span>填写范围0~${allWeight}分</span></font>
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" defaultId=""
							 selectId="workEvaCycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />			
				</td>						
			</tr>	
	</table>
	</html:form>
	<div id="workTemplate" >
	<div id="divIdName">
		<table class="formTable">
		<tr><td> </td></tr>
			<tr>		
				<td class="label" style="vertical-align:middle" >
					工作内容
				</td>
				<td class="content">
					workContentValue
					<html:hidden property="workContent" value="workContentValue" />
				</td>
				<td class="label" style="vertical-align:middle" >
					执行人
				</td>
				<td class="content">
					toOrgUserName
					<html:hidden property="toOrgUserName" value="toOrgUserNameValue" />
					<html:hidden property="toOrgUser" value="toOrgUserValue" />
				</td>
				</tr>
				<tr>
				<td class="label" style="vertical-align:middle" >
					工作完成标准
				</td>
				<td class="content" colspan="3">
					workStandardValue
					<html:hidden property="workStandard" value="workStandardValue" />
				</td>				
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					工作任务类型
				</td>
				<td class="content" >
						workTypeName
					<html:hidden property="workType" value="workTypeValue" />				
				</td>
				<td class="label" style="vertical-align:middle">
					工作任务执行周期
				</td>
				<td class="content">
					workCycleName
					<html:hidden property="workCycle" value="workCycleValue" />	
				</td>	
			</tr>	
			<tr>		
					<td class="label" style="vertical-align:middle">
						考核指标名称
					</td>
					<td class="content">
						workEvaNameValue
					<html:hidden property="workEvaName" value="workEvaNameValue" />
					</td>		
					<td class="label" style="vertical-align:middle">
						算法分类
					</td>
					<td class="content">
						algorithmTypeName
					<html:hidden property="algorithmType" value="algorithmTypeValue" />
					</td>	
			</tr>
			<tr>
					<td class="label" style="vertical-align:middle">
						指标详细算法
					</td>
					<td class="content" colspan="3">
						workEvaContentName
					<html:hidden property="workEvaContent" value="workEvaContentValue" />
					</td>						
			</tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					考核开始时间
				</td>
				<td class="content">
					workEvaStartTimeValue
					<html:hidden property="workEvaStartTime" value="workEvaStartTimeValue" />
				</td>
				<td class="label" style="vertical-align:middle">
					考核结束时间
				</td>
				<td class="content">
					workEvaEndTimeValue
					<html:hidden property="workEvaEndTime" value="workEvaEndTimeValue" />			
				</td>			
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					workEvaWeightValue
					<html:hidden property="workEvaWeight" value="workEvaWeightValue" />
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
					workEvaCycleName
					<html:hidden property="workEvaCycle" value="workEvaCycleValue" />
					<img align=right src="${app}/images/icons/delwork.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
					<img align=right src="${app}/images/icons/editwork.gif" alt="编辑工作任务" onclick="javascript:show_windowsWork('${app}/partner/tempTask/pnrTempTaskMains.do?method=createWork','','divIdName');" />
				</td>	
			</tr>
			<html:hidden property="workId" value="workIdValue" />
			</table>
			<hr />
			</div>
		</div>
<table>
	<tr>
		<td>
			<input type="button" class="btn" id="subButton" value="保存" onclick="createSubWork();"/>
		</td>
	</tr>
</table>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrTempTaskWorkForm'});
});
	var workStr = document.getElementById("divIdName").innerHTML;
	document.getElementById("workTemplate").removeNode(true);
	eoms.$("algorithm1").hide();
	eoms.$("algorithm2").hide();
	var now= new Date();
	var minute=now.getMinutes();   
    var second=now.getSeconds();
    var tableMax = minute+second;
	var tableId = '${tableId}';
	
	if(tableId!=''){
		putDefaultValue(tableId);
	} else {
		document.getElementById('workEvaStartTime').value = parent.opener.document.getElementById("startTime").value;
		document.getElementById('workEvaEndTime').value = parent.opener.document.getElementById("endTime").value;
	}	
function createSubWork() {
	if(!v.check()){
		return false;
	}
	if(tableId!=''){
		workStr = workStr.replace('divIdName',tableId);
		workStr = workStr.replace('divIdName',tableId);
	}else{
		workStr = workStr.replace('divIdName','workTable-'+tableMax);
		workStr = workStr.replace('divIdName','workTable-'+tableMax);
	}
 	workStr = workStr.replace('workContentValue',document.getElementById('workContent').value.replace('<', '&lt;').replace('>', '&gt;'));
 	workStr = workStr.replace('workContentValue',document.getElementById('workContent').value.replace('<', '&lt;').replace('>', '&gt;'));
 	workStr = workStr.replace('toOrgUserName',document.getElementById('toOrgUserName').value);
 	workStr = workStr.replace('toOrgUserNameValue',document.getElementById('toOrgUserName').value);
 	workStr = workStr.replace('toOrgUserValue',document.getElementById('toOrgUser').value);
 	workStr = workStr.replace('workStandardValue',document.getElementById('workStandard').value.replace('<', '&lt;').replace('>', '&gt;'));
 	workStr = workStr.replace('workStandardValue',document.getElementById('workStandard').value.replace('<', '&lt;').replace('>', '&gt;'));
 	workStr = workStr.replace('workTypeValue',document.getElementById('workType').value);
 	workStr = workStr.replace('workTypeName',document.getElementById("workType").options[document.getElementById("workType").selectedIndex].text);
 	workStr = workStr.replace('workCycleValue',document.getElementById('workCycle').value);
 	workStr = workStr.replace('workCycleName',document.getElementById("workCycle").options[document.getElementById("workCycle").selectedIndex].text);
 	workStr = workStr.replace('workEvaNameValue',document.getElementById('workEvaName').value.replace('<', '&lt;').replace('>', '&gt;'));
 	workStr = workStr.replace('workEvaNameValue',document.getElementById('workEvaName').value.replace('<', '&lt;').replace('>', '&gt;'));
 	workStr = workStr.replace('workEvaStartTimeValue',document.getElementById('workEvaStartTime').value);
 	workStr = workStr.replace('workEvaStartTimeValue',document.getElementById('workEvaStartTime').value);
 	workStr = workStr.replace('workEvaEndTimeValue',document.getElementById('workEvaEndTime').value);
 	workStr = workStr.replace('workEvaEndTimeValue',document.getElementById('workEvaEndTime').value);
 	workStr = workStr.replace('workEvaWeightValue',document.getElementById('workEvaWeight').value);
 	workStr = workStr.replace('workEvaWeightValue',document.getElementById('workEvaWeight').value);
 	workStr = workStr.replace('workEvaCycleValue',document.getElementById('workEvaCycle').value);
 	workStr = workStr.replace('workEvaCycleName',document.getElementById("workEvaCycle").options[document.getElementById("workEvaCycle").selectedIndex].text);
 	if(document.getElementById('workId').value==''){
 	 	workStr = workStr.replace('workIdValue',"''");
 	} else {
 		workStr = workStr.replace('workIdValue',document.getElementById('workId').value);
 	}
 	
 	workStr = workStr.replace('algorithmTypeValue',document.getElementById('algorithmType').value);
 	workStr = workStr.replace('algorithmTypeName',document.getElementById("algorithmType").options[document.getElementById("algorithmType").selectedIndex].text);
 	var workEvaContentName ='';
 	var workEvaContentvalue ='';
 	//根据不同的算法得到，算法描述和算法存储格式
 	if(document.getElementById('algorithmType').value==0){
 		if(document.getElementById('workEvaContent').value==''){
 			alert("请填写考核指标算法");
 			return false;
 		} 	
 		workStr = workStr.replace('workEvaContentName',document.getElementById('workEvaContent').value.replace('<', '&lt;').replace('>', '&gt;'));
 		workStr = workStr.replace('workEvaContentValue',document.getElementById('workEvaContent').value.replace('<', '&lt;').replace('>', '&gt;'));
 	}else if(document.getElementById('algorithmType').value==1){
 		if(document.getElementById('rateMiddleSpan').value==''){
 			alert("请填写考核指标算法");
 			return false;
 		}  	
 		workEvaContentName = document.getElementById('rateFullSpan').value+'%~';
 		workEvaContentName = workEvaContentName+document.getElementById('rateMiddleSpan').value+'% 得满分，';
 		workEvaContentName = workEvaContentName+document.getElementById('rateMiddleSpan').value+'%~';
 		workEvaContentName = workEvaContentName+document.getElementById('rateZeroSpan').value+'%不得分';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateFullSpan').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateMiddleSpan').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateZeroSpan').value+'-100,0';
 	}else{
 		if(document.getElementById('rateTwoSpan').value==''||document.getElementById('rateThreeSpan').value==''||document.getElementById('workScoreOne')==''||document.getElementById('workScoreTwo')==''||document.getElementById('workScoreThree')==''){
 			alert("请填写考核指标算法");
 			return false;
 		}  	
 	 	workEvaContentName = document.getElementById('rateOneSpan').value+'%~'+document.getElementById('rateTwoSpan').value+'%';
 		workEvaContentName = workEvaContentName+' 得总分的'+document.getElementById('workScoreOne').value+'%,';
 		workEvaContentName = workEvaContentName+document.getElementById('rateTwoSpan').value+'%~'+document.getElementById('rateThreeSpan').value+'%';
 		workEvaContentName = workEvaContentName+' 得总分的'+document.getElementById('workScoreTwo').value+'%,';
 		workEvaContentName = workEvaContentName+document.getElementById('rateThreeSpan').value+'%~'+document.getElementById('rateFourSpan').value+'%';
 		workEvaContentName = workEvaContentName+' 得总分的'+document.getElementById('workScoreThree').value+'%';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateOneSpan').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateTwoSpan').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateThreeSpan').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('rateFourSpan').value+'-';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('workScoreOne').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('workScoreTwo').value+',';
 		workEvaContentvalue = workEvaContentvalue+document.getElementById('workScoreThree').value;
 	}
 		workStr = workStr.replace('workEvaContentName',workEvaContentName);
 		workStr = workStr.replace('workEvaContentValue',workEvaContentvalue);
 	if(tableId==''){
 		workStr = '<div id="workTable-'+tableMax+'">'+workStr+'</div>';
 		Ext.DomHelper.append(parent.opener.document.getElementById('workDiv'),workStr,true);
 	}else{
 		Ext.DomHelper.overwrite(parent.opener.document.getElementById(tableId),workStr,true);  
 	}
 	window.close();
        }
function confirmWeight(){
	var weight = parseFloat(document.getElementById('workEvaWeight').value);
	if(weight>${allWeight}){
		alert('超出分数范围！');
		document.getElementById('workEvaWeight').value = '0';
		}
	}
function changeAlgorithmType(){
	var algorithmType = document.getElementById('algorithmType').value;
	if(algorithmType==0){
		eoms.$("algorithm1").hide();
		eoms.$("algorithm2").hide();
		eoms.$("algorithm0").show();
	}
	if(algorithmType==1){
		eoms.$("algorithm0").hide();
		eoms.$("algorithm2").hide();
		eoms.$("algorithm1").show();
	}
	if(algorithmType==2){
		eoms.$("algorithm0").hide();
		eoms.$("algorithm1").hide();
		eoms.$("algorithm2").show();
	}
	}
function changeRate(lastName,textName,nextName,thisInput){
	if(lastName!=''&&parseFloat(document.getElementById(lastName).value)<= parseFloat(thisInput.value)){
		alert('您填写的范围值超出上线！');
		thisInput.value = '';
	}else if(nextName!=''&&parseFloat(document.getElementById(nextName).value)>= parseFloat(thisInput.value)){
		alert('您填写的范围值超出上线！');
		thisInput.value = '';
	}
	document.getElementById(textName).value = thisInput.value;
}

function changeWorType(value){
	if(value=='subjective'){
		eoms.form.Options.del("algorithmType","1");
		eoms.form.Options.del("algorithmType","2");
	}else{
		eoms.form.Options.del("algorithmType","1");
		eoms.form.Options.del("algorithmType","2");
		eoms.form.Options.add("algorithmType","界线得分","1");
		eoms.form.Options.add("algorithmType","范围得分","2");
	}
}
function putDefaultValue(workDiv){
	var workContent = parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[0].value;
	var toOrgUserName = parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[1].value;
	var toOrgUser = parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[2].value;
	var workStandard =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[3].value;
	var workType =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[4].value;
	var workCycle =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[5].value;
	var workEvaName =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[6].value;
	var algorithmType =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[7].value;
	var workEvaContent =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[8].value;
	var workEvaStartTime =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[9].value;
	var workEvaEndTime =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[10].value;
	var workEvaWeight =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[11].value;
	var workEvaCycle =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[12].value;
	var workId =  parent.opener.document.getElementById(workDiv).getElementsByTagName("input")[13].value;

	document.getElementById('workContent').value = workContent;
	document.getElementById('workStandard').value = workStandard;
	document.getElementById('workType').value = workType;
	document.getElementById('workCycle').value = workCycle;
	document.getElementById('workEvaName').value = workEvaName;
	document.getElementById('algorithmType').value = algorithmType;
	document.getElementById('workEvaStartTime').value = workEvaStartTime;
	document.getElementById('workEvaEndTime').value = workEvaEndTime;
	document.getElementById('workEvaWeight').value = workEvaWeight;
	document.getElementById('workEvaCycle').value = workEvaCycle;
	document.getElementById('workId').value = workId;
	
	changeAlgorithmType();
	
	if(algorithmType==0){
	
	}else if(algorithmType==1){
		var rate = workEvaContent.split('-')[0].split(',');
		var Score = workEvaContent.split('-')[1].split(',');
		document.getElementById('rateMiddle').value = rate[1];
		document.getElementById('rateMiddleSpan').value = rate[1];
	}else{
		var rate = workEvaContent.split('-')[0].split(',');
		var Score = workEvaContent.split('-')[1].split(',');
		document.getElementById('rateTwo').value = rate[1];
		document.getElementById('rateTwoSpan').value = rate[1];
		document.getElementById('rateThree').value = rate[2];
		document.getElementById('rateThreeSpan').value = rate[2];
		document.getElementById('workScoreOne').value = rate[0];
		document.getElementById('workScoreTwo').value = rate[1];
		document.getElementById('workScoreThree').value = rate[2];
		
	}
	document.getElementById('workEvaContent').value = workEvaContent;

}
</script>
	<%@ include file="/common/footer_eoms.jsp"%>