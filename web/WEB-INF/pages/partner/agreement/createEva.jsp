<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<eoms:xbox id="toEvaUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="2" 
	  	rootText='被考核人' 
	  	valueField="toEvaUser" handler="toEvaUserName"
		textField="toEvaUserName"
		checktype="user" single="false"		
		data= "${orgData}"
	  ></eoms:xbox>

<html:form action="/pnrAgreementWorks.do?method=save" styleId="pnrAgreementWorkForm" method="post"> 
		<table class="formTable">
		<caption>
			<div class="header center">其他考核内容</div>
		</caption>	
			<tr>
				<td class="label" style="vertical-align:middle">
					本项考核开始时间
				</td>
				<td class="content">
					<html:text property="evaStartTime" styleId="evaStartTime"
								styleClass="text medium" style="width:95%;" 
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);"  alt="allowBlank:false,vtext:'请填写考核开始时间'"  readonly="true"  value="" />
				</td>
				<td class="label" style="vertical-align:middle">
					本项考核结束时间
				</td>
				<td class="content">
					<html:text property="evaEndTime" styleId="evaEndTime"
								styleClass="text medium" style="width:97%;" 
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);"  alt="allowBlank:false,vtext:'请填写考核结束时间'"  readonly="true"  value="" />
				</td>			
			</tr>
			<tr>
				<c:if test="${serviceType!='Purchase'}">
					<td class="label" style="vertical-align:middle">
						考核指标名称
					</td>
					<td class="content">
						<html:text property="evaName" styleClass="text medium"  alt="allowBlank:false,vtext:'请填写考核指标名称',maxLength:50"  style="width:98%;" value="" />
					</td>
				</c:if>
				<c:if test="${serviceType=='Purchase'}">
					<td class="label" style="vertical-align:middle">
						考核指标名称
					</td>
					<td class="content" colspan="3">
						<html:text property="evaName"  styleClass="text medium"  alt="allowBlank:false,vtext:'请填写考核指标名称',maxLength:50"  style="width:98%;" value="" />
					</td>
				</c:if>
				<c:if test="${serviceType!='Purchase'}">
					<td class="label" style="vertical-align:middle">
						被考核人
					</td>
					<td class="content">
					<input type="text" name="toEvaUserName" id="toEvaUserName"  style="width:96%;"  value="" maxLength="100" class="text medium" alt="allowBlank:false,vtext:'请选择被考核人...'" />		
			    	<input type="hidden" name="toEvaUser" id="toEvaUser" value="" maxLength="32" class="text medium" />		
					</td>			
				</c:if>
				<c:if test="${serviceType=='Purchase'}">
					<input type="hidden" name="toEvaUserName" id="toEvaUserName"  style="width:96%;"  value="" maxLength="100" class="text medium" />		
			    	<input type="hidden" name="toEvaUser" id="toEvaUser" value="" maxLength="32" class="text medium" />		
				</c:if>
			</tr>			
			<tr>
				<td class="label" style="vertical-align:middle">
					得分来源
				</td>
				<td class="content" >
						<eoms:dict key="dict-partner-agreement" dictId="workType" defaultId=""
							 selectId="evaSource" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核来源'" onchange="changeWorType(this.value);"/>			
				</td>	
				<td class="label" style="vertical-align:middle">
					算法分类
				</td>
				<td class="content">
					<eoms:dict key="dict-eva" dictId="algorithmType" defaultId=""
							 selectId="evaAlgorithmType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择算法类型'" onchange="changeAlgorithmType();"/>	
				</td>								
			</tr>			
			<tr>
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td id='algorithm0' class="content" colspan="3">
					<html:textarea property="evaContent"  rows="3"  style="width:98%;"  alt="maxLength:1000"  value="" title="指标详细算法" ></html:textarea>
					<br>
					<font color='red'><span>注：如果该指标为自动采集得分则该指标的采集值乘以指标所设分值，如果该指标为非自动采集则手工填写得分</span></font>
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
					<html:text property="evaWeight"  styleId="evaWeight"  alt="re:/^-?[0-9]+\.{0,1}[0-9]{0,2}$/,re_vt:'请输入分数',allowBlank:false,vtext:'请选择所占分数'"  styleClass="text medium" onblur="confirmWeight();" style="width:18%;" value="" />&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span>填写范围0~${allWeight}分</span></font>
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" defaultId=""
							 selectId="evaCycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />			
				</td>						
			</tr>		
			<html:hidden property="evaId" value="" />	
	</table>
	</html:form>
	<div id="evaTemplate" >
	<div id="divIdName">
		<table class="formTable">
		<tr><td> </td></tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					本项考核开始时间
				</td>
				<td class="content">
					evaStartTimeValue
					<html:hidden property="evaStartTime" value="evaStartTimeValue" />
				</td>
				<td class="label" style="vertical-align:middle">
					本项考核结束时间
				</td>
				<td class="content">
					evaEndTimeValue
					<html:hidden property="evaEndTime" value="evaEndTimeValue" />			
				</td>			
			</tr>		
			<tr>	
				<td class="label" style="vertical-align:middle">
						考核指标名称
					</td>
					<td class="content" contentStyle>
						evaNameValue
					<html:hidden property="evaName" value="evaNameValue" />
				</td>			
				<td class="label" toEvaUserStyle >
					被考核人
				</td>
				<td class="content" toEvaUserContentStyle>
					toEvaUserName
					<html:hidden property="toEvaUserName" value="toEvaUserNameValue" />
					<html:hidden property="toEvaUser" value="toEvaUserValue" />
				</td>
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					得分来源
				</td>
				<td class="content" >
						evaSourceName
					<html:hidden property="evaSource" value="evaSourceValue" />				
				</td>
				<td class="label" style="vertical-align:middle">
					算法分类
				</td>
				<td class="content">
					evaAlgorithmTypeName
				<html:hidden property="evaAlgorithmType" value="evaAlgorithmTypeValue" />
				</td>					
			</tr>	
			<tr>
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td class="content" colspan="3">
					evaContentName
				<html:hidden property="evaContent" value="evaContentValue" />
				</td>						
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					evaWeightValue
					<html:hidden property="evaWeight" value="evaWeightValue" />
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
					evaCycleName
					<html:hidden property="evaCycle" value="evaCycleValue" />
					<img align=right src="${app}/images/icons/deleva.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
					<img align=right src="${app}/images/icons/editeva.gif" alt="编辑工作任务" onclick="javascript:show_windowsEva('${app}/partner/agreement/pnrAgreementMains.do?method=createEva','','divIdName');" />
				</td>	
			</tr>
			<input type='hidden'  name='evaId' value='evaIdValue'>
			</table>
			<hr />
			</div>
		</div>
<table>
	<tr>
		<td>
				<input type="button" class="btn" id="subButton" value="保存" onclick="createSubEva();"/>
		</td>
	</tr>
</table>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrAgreementWorkForm'});
});
	var evaStr = document.getElementById("divIdName").innerHTML;
	document.getElementById("evaTemplate").removeNode(true);
	var now= new Date();
	var minute=now.getMinutes();   
    var second=now.getSeconds();
    var tableMax = minute+second;
	eoms.$("algorithm1").hide();
	eoms.$("algorithm2").hide();
	
	var serverType = '${serviceType}';
	if(serverType == 'Purchase'){
		setFormPurchase();
	}else{
		setFormDelOptions();
	}
		
	var tableId = '${tableId}';
	if(tableId!=''){
		putDefaultValue(tableId);
	}else {
		document.getElementById('evaStartTime').value = parent.opener.document.getElementById("startTime").value;
		document.getElementById('evaEndTime').value = parent.opener.document.getElementById("endTime").value;
	}
function createSubEva() {
	if(!v.check()){
		return false;
	}
	if(tableId!=''){
		evaStr = evaStr.replace('divIdName',tableId);
		evaStr = evaStr.replace('divIdName',tableId);
	}else{
		evaStr = evaStr.replace('divIdName','evaTable-'+tableMax);
		evaStr = evaStr.replace('divIdName','evaTable-'+tableMax);
	}
	 	
	//如果是采购类
	if(serverType == 'Purchase'){
	 	evaStr = evaStr.replace('toEvaUserName',"''");
	 	evaStr = evaStr.replace('toEvaUserNameValue',"''");
 		evaStr = evaStr.replace('toEvaUserValue',"''");	 	
		evaStr = evaStr.replace('contentStyle',' colspan="3"');
		evaStr = evaStr.replace('toEvaUserStyle',' style="display:none;"');
		evaStr = evaStr.replace('toEvaUserContentStyle',' style="display:none;"');
	}else{
	 	evaStr = evaStr.replace('toEvaUserName',document.getElementById('toEvaUserName').value);
	 	evaStr = evaStr.replace('toEvaUserNameValue',document.getElementById('toEvaUserName').value);
	 	evaStr = evaStr.replace('toEvaUserValue',document.getElementById('toEvaUser').value);
 	  	evaStr = evaStr.replace('contentStyle','');
	  	evaStr = evaStr.replace('toEvaUserStyle',' style="vertical-align:middle"');
	  	evaStr = evaStr.replace('toEvaUserContentStyle','');
 	}
 		

 	evaStr = evaStr.replace('toEvaUserValue',document.getElementById('toEvaUser').value);
 	evaStr = evaStr.replace('evaSourceValue',document.getElementById('evaSource').value);
 	evaStr = evaStr.replace('evaSourceName',document.getElementById("evaSource").options[document.getElementById("evaSource").selectedIndex].text);
 	evaStr = evaStr.replace('evaNameValue',document.getElementById('evaName').value.replace('<', '&lt;').replace('>', '&gt;'));
 	evaStr = evaStr.replace('evaNameValue',document.getElementById('evaName').value.replace('<', '&lt;').replace('>', '&gt;'));
 	evaStr = evaStr.replace('evaStartTimeValue',document.getElementById('evaStartTime').value);
 	evaStr = evaStr.replace('evaStartTimeValue',document.getElementById('evaStartTime').value);
 	evaStr = evaStr.replace('evaEndTimeValue',document.getElementById('evaEndTime').value);
 	evaStr = evaStr.replace('evaEndTimeValue',document.getElementById('evaEndTime').value);
 	evaStr = evaStr.replace('evaWeightValue',document.getElementById('evaWeight').value);
 	evaStr = evaStr.replace('evaWeightValue',document.getElementById('evaWeight').value);
 	evaStr = evaStr.replace('evaCycleValue',document.getElementById('evaCycle').value);
 	evaStr = evaStr.replace('evaCycleName',document.getElementById("evaCycle").options[document.getElementById("evaCycle").selectedIndex].text);
 	if(document.getElementById('evaId').value==''){
 		evaStr = evaStr.replace('evaIdValue',"''");
 	}else {
 		evaStr = evaStr.replace('evaIdValue',document.getElementById('evaId').value);
 	}
 	evaStr = evaStr.replace('evaAlgorithmTypeValue',document.getElementById('evaAlgorithmType').value);
 	evaStr = evaStr.replace('evaAlgorithmTypeName',document.getElementById("evaAlgorithmType").options[document.getElementById("evaAlgorithmType").selectedIndex].text);
 	var evaContentName ='';
 	var evaContentvalue ='';
 	//根据不同的算法得到，算法描述和算法存储格式
 	if(document.getElementById('evaAlgorithmType').value==0){
 		if(document.getElementById('evaContent').value==''){
 			alert("请填写考核指标算法");
 			return false;
 		} 	
 		evaStr = evaStr.replace('evaContentName',document.getElementById('evaContent').value.replace('<', '&lt;').replace('>', '&gt;'));
 		evaStr = evaStr.replace('evaContentValue',document.getElementById('evaContent').value.replace('<', '&lt;').replace('>', '&gt;'));
 	}else if(document.getElementById('evaAlgorithmType').value==1){
 		if(document.getElementById('rateMiddleSpan').value==''){
 			alert("请填写考核指标算法");
 			return false;
 		}  	
 		evaContentName = document.getElementById('rateFullSpan').value+'%~';
 		evaContentName = evaContentName+document.getElementById('rateMiddleSpan').value+'% 得满分，';
 		evaContentName = evaContentName+document.getElementById('rateMiddleSpan').value+'%~';
 		evaContentName = evaContentName+document.getElementById('rateZeroSpan').value+'%不得分';
 		evaContentvalue = evaContentvalue+document.getElementById('rateFullSpan').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('rateMiddleSpan').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('rateZeroSpan').value+'-100,0';
 	}else{
 		if(document.getElementById('rateTwoSpan').value==''||document.getElementById('rateThreeSpan').value==''||document.getElementById('workScoreOne')==''||document.getElementById('workScoreTwo')==''||document.getElementById('workScoreThree')==''){
 			alert("请填写考核指标算法");
 			return false;
 		} 	
 	 	evaContentName = document.getElementById('rateOneSpan').value+'%~'+document.getElementById('rateTwoSpan').value+'%';
 		evaContentName = evaContentName+' 得总分的'+document.getElementById('workScoreOne').value+'%,';
 		evaContentName = evaContentName+document.getElementById('rateTwoSpan').value+'%~'+document.getElementById('rateThreeSpan').value+'%';
 		evaContentName = evaContentName+' 得总分的'+document.getElementById('workScoreTwo').value+'%,';
 		evaContentName = evaContentName+document.getElementById('rateThreeSpan').value+'%~'+document.getElementById('rateFourSpan').value+'%';
 		evaContentName = evaContentName+' 得总分的'+document.getElementById('workScoreThree').value+'%,';
 		evaContentvalue = evaContentvalue+document.getElementById('rateOneSpan').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('rateTwoSpan').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('rateThreeSpan').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('rateFourSpan').value+'-';
 		evaContentvalue = evaContentvalue+document.getElementById('workScoreOne').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('workScoreTwo').value+',';
 		evaContentvalue = evaContentvalue+document.getElementById('workScoreThree').value;
 	}
 		evaStr = evaStr.replace('evaContentName',evaContentName);
 		evaStr = evaStr.replace('evaContentValue',evaContentvalue);
 	if(tableId==''){
 		evaStr = '<div id="evaTable-'+tableMax+'">'+evaStr+'</div>';
 		Ext.DomHelper.append(parent.opener.document.getElementById('evaDiv'),evaStr,true);
 	}else{
 		Ext.DomHelper.overwrite(parent.opener.document.getElementById(tableId),evaStr,true);  
 	}
 	window.close();
        }
function confirmWeight(){
	var weight = parseFloat(document.getElementById('evaWeight').value);
	if(weight>${allWeight}){
		alert('超出分数范围！');
		document.getElementById('evaWeight').value = '0';
		}
	}
function changeAlgorithmType(){
	var evaAlgorithmType = document.getElementById('evaAlgorithmType').value;
	if(evaAlgorithmType==0){
		eoms.$("algorithm1").hide();
		eoms.$("algorithm2").hide();
		eoms.$("algorithm0").show();
	}
	if(evaAlgorithmType==1){
		eoms.$("algorithm0").hide();
		eoms.$("algorithm2").hide();
		eoms.$("algorithm1").show();
	}
	if(evaAlgorithmType==2){
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
		eoms.form.Options.del("evaAlgorithmType","1");
		eoms.form.Options.del("evaAlgorithmType","2");
	}else{
		eoms.form.Options.del("evaAlgorithmType","1");
		eoms.form.Options.del("evaAlgorithmType","2");
		eoms.form.Options.add("evaAlgorithmType","界线得分","1");
		eoms.form.Options.add("evaAlgorithmType","范围得分","2");
	}
}
function putDefaultValue(evaDiv){
	var evaStartTime = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[0].value;
	var evaEndTime = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[1].value;
	var evaName = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[2].value;
	var toEvaUserName =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[3].value;
	var toEvaUser =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[4].value;
	var evaSource =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[5].value;
	var evaAlgorithmType =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[6].value;
	var evaContent =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[7].value;
	var evaWeight =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[8].value;
	var evaCycle =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[9].value;
	var evaId =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[10].value;
	document.getElementById('evaStartTime').value = evaStartTime;
	document.getElementById('evaEndTime').value = evaEndTime;
	document.getElementById('evaName').value = evaName;
	document.getElementById('evaSource').value = evaSource;
	document.getElementById('toEvaUserName').value = toEvaUserName;
	document.getElementById('toEvaUser').value = toEvaUser;
	document.getElementById('evaContent').value = evaContent;
	document.getElementById('evaWeight').value = evaWeight;
	document.getElementById('evaCycle').value = evaCycle;
	document.getElementById('evaAlgorithmType').value = evaAlgorithmType;
	document.getElementById('evaId').value = evaId;
	changeAlgorithmType();
	
	if(evaAlgorithmType==0){
	
	}else if(evaAlgorithmType==1){
		var rate = evaContent.split('-')[0].split(',');
		var Score = evaContent.split('-')[1].split(',');
		document.getElementById('rateMiddle').value = rate[1];
		document.getElementById('rateMiddleSpan').value = rate[1];
	}else{
		var rate = evaContent.split('-')[0].split(',');
		var Score = evaContent.split('-')[1].split(',');
		document.getElementById('rateTwo').value = rate[1];
		document.getElementById('rateTwoSpan').value = rate[1];
		document.getElementById('rateThree').value = rate[2];
		document.getElementById('rateThreeSpan').value = rate[2];
		document.getElementById('workScoreOne').value = rate[0];
		document.getElementById('workScoreTwo').value = rate[1];
		document.getElementById('workScoreThree').value = rate[2];
		
	}
	document.getElementById('evaContent').value = evaContent;
}

function setFormDelOptions(){
	eoms.form.Options.del("evaCycle","times");
}

function setFormPurchase(){
	//设置表单默认值
	document.getElementById('evaCycle').value = 'times';
	document.getElementById('evaAlgorithmType').value = '0';
	document.getElementById('evaSource').value = 'subjective';
	document.getElementById("evaCycle").setAttribute("disabled", true); 
	document.getElementById("evaAlgorithmType").setAttribute("disabled", true); 
	document.getElementById("evaSource").setAttribute("disabled", true); 
}
</script>
	<%@ include file="/common/footer_eoms.jsp"%>