<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<eoms:xbox id="toOrgUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText='被考核人' 
	  	valueField="toOrgUser" handler="toOrgUserName"
		textField="toOrgUserName"
		checktype="user" single="false"		
		data= "${orgData}"
	  ></eoms:xbox>
	  
<html:form action="/evaTemplates.do?method=save" styleId="evaTemplateTempForm" method="post"> 
		<table class="formTable">
		<caption>
			<div class="header center">其他考核内容</div>
		</caption>	
			<tr>
				<td class="label" style="vertical-align:middle">
					考核指标名称
				</td>
				<td class="content">
					<html:text property="kpiName" styleClass="text medium" alt="allowBlank:false,vtype:'',maxLength:50"  style="width:98%;" value="" />
				</td>
				<td class="label" style="vertical-align:middle">
					被考核人
				</td>
				<td class="content">
				<c:if test="${tableId!=''}">
				<input type="text" name="toOrgUserName" id="toOrgUserName"  style="width:96%;"  value="" maxLength="100" class="text medium" disabled="disabled"/>
				</c:if>
				<c:if test="${tableId==''}">
				<input type="text" name="toOrgUserName" id="toOrgUserName"  alt="allowBlank:false,vtype:''"  style="width:96%;"  value="" maxLength="100" class="text medium"  />
				</c:if>		
		    	<input type="hidden" name="toOrgUser" id="toOrgUser" value="" maxLength="32" class="text medium" />		
				</td>					
			</tr>	
			<tr>
				<td class="label" style="vertical-align:middle">
					考核开始时间
				</td>
				<td class="content">
					<html:text property="evaStartTime" styleId="evaStartTime"
								styleClass="text medium" style="width:95%;" 
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtext:''" readonly="true"  value="" />
				</td>
				<td class="label" style="vertical-align:middle">
					考核结束时间
				</td>
				<td class="content">
					<html:text property="evaEndTime" styleId="evaEndTime"
								styleClass="text medium" style="width:97%;" 
								 onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtext:''" readonly="true"  value="" />
				</td>			
			</tr>		
			<tr>
				<td class="label" style="vertical-align:middle">
					考核来源
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
							 selectId="algorithmType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择算法分类'" onchange="changeAlgorithmType();"/>	
				</td>								
			</tr>			
			<tr>
				<td class="label" style="vertical-align:middle">
					指标详细算法
				</td>
				<td id='algorithm0' class="content" colspan="3">
					<html:textarea property="algorithm"   rows="3"  style="width:98%;"  alt="maxLength:1000" value=""  title="指标详细算法" ></html:textarea>
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
					<html:text property="weight"  styleId="weight" alt="re:/^-?[0-9]+\.{0,1}[0-9]{0,2}$/,re_vt:'请输入分数',allowBlank:false,vtext:'请选择所占分数'"  styleClass="text medium" onblur="confirmWeight();" style="width:18%;" value="" />&nbsp;&nbsp;&nbsp;&nbsp;<font color='blue'><span>填写范围0~${allWeight}分</span></font>
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" defaultId="" 
							 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择考核周期'" />			
				</td>						
			</tr>	
			<html:hidden property="evaId" value="" />	
			<tr>
				<td class="label" style="vertical-align:middle">
					备注
				</td>
				<td colspan="3" class="content">
					<input type="textarea" id="kpiRemark" name="kpiRemark" class="textarea"
						style="width:95%;"  value=""   alt="maxLength:255" />
				</td>
			</tr>				
			<html:hidden property="agreementWorkId" value="" />
	</table>
	</html:form>
	<div id="evaTemplate" >
	<div id="divIdName">
		<table class="formTable">
		<tr><td> </td></tr>
			<tr>
				<td class="label" style="vertical-align:middle">
					考核指标开始时间
				</td>
				<td class="content">
					evaStartTimeValue
					<html:hidden property="evaStartTime" value="evaStartTimeValue" />
				</td>
				<td class="label" style="vertical-align:middle">
					考核指标结束时间
				</td>
				<td class="content">
					evaEndTimeValue
					<html:hidden property="evaEndTime" value="evaEndTimeValue" />			
				</td>			
			</tr>		
			<tr>	
				<td class="label" style="vertical-align:middle">
						指标名称
					</td>
					<td class="content">
						kpiNameValue
					<html:hidden property="kpiName" value="kpiNameValue" />
				</td>			
				<td class="label" style="vertical-align:middle" >
					被考核人
				</td>
				<td class="content">
					toOrgUserName
					<html:hidden property="toOrgUserName" value="toOrgUserNameValue" />
					<html:hidden property="toOrgUser" value="toOrgUserValue" />
				</td>
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					考核来源
				</td>
				<td class="content" >
						evaSourceName
					<html:hidden property="evaSource" value="evaSourceValue" />				
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
					algorithmName
				<html:hidden property="algorithm" value="algorithmValue" />
				</td>						
			</tr>
			<tr>		
				<td class="label" style="vertical-align:middle">
					所占分数
				</td>
				<td class="content">
					weightValue
					<html:hidden property="weight" value="weightValue" />
				</td>	
				<td class="label" style="vertical-align:middle">
					考核周期
				</td>
				<td class="content">
					cycleName
					<html:hidden property="cycle" value="cycleValue" />
				</td>	
			</tr>
			<input type='hidden'  name='evaId' value='evaIdValue'>
			<tr>
				<td class="label" style="vertical-align:middle">
					备注
				</td>
				<td class="content" colspan="3">
					kpiRemarkValue
				<html:hidden property="kpiRemark" value="kpiRemarkValue" />
					<img align=right src="${app}/images/icons/list-delete.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
					<img align=right src="${app}/images/icons/edit.gif" alt="编辑工作任务" onclick="javascript:show_windowsEva('${app}/eva/evaTemplateTemps.do?method=createEva','','divIdName');" />
				</td>						
			</tr>		
			<input  type='hidden'  name='agreementWorkId' value='agreementWorkIdValue'>	
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
	v = new eoms.form.Validation({form:'evaTemplateTempForm'});
});
	var evaStr = document.getElementById("divIdName").innerHTML;
	document.getElementById("evaTemplate").removeNode(true);
	var now= new Date();
	var minute=now.getMinutes();   
    var second=now.getSeconds();
    var tableMax = minute+second;
	eoms.$("algorithm1").hide();
	eoms.$("algorithm2").hide();
	
	var tableId = '${tableId}';
	if(tableId!=''){
		putDefaultValue(tableId);
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
 	evaStr = evaStr.replace('toOrgUserName',document.getElementById('toOrgUserName').value);
 	evaStr = evaStr.replace('toOrgUserNameValue',document.getElementById('toOrgUserName').value);
 	evaStr = evaStr.replace('toOrgUserValue',document.getElementById('toOrgUser').value);
 	evaStr = evaStr.replace('evaSourceValue',document.getElementById('evaSource').value);
 	evaStr = evaStr.replace('evaSourceName',document.getElementById("evaSource").options[document.getElementById("evaSource").selectedIndex].text);
 	evaStr = evaStr.replace('kpiNameValue',document.getElementById('kpiName').value);
 	evaStr = evaStr.replace('kpiNameValue',document.getElementById('kpiName').value);
 	evaStr = evaStr.replace('evaStartTimeValue',document.getElementById('evaStartTime').value);
 	evaStr = evaStr.replace('evaStartTimeValue',document.getElementById('evaStartTime').value);
 	evaStr = evaStr.replace('evaEndTimeValue',document.getElementById('evaEndTime').value);
 	evaStr = evaStr.replace('evaEndTimeValue',document.getElementById('evaEndTime').value);
 	evaStr = evaStr.replace('weightValue',document.getElementById('weight').value);
 	evaStr = evaStr.replace('weightValue',document.getElementById('weight').value);
 	evaStr = evaStr.replace('cycleValue',document.getElementById('cycle').value);
 	evaStr = evaStr.replace('cycleName',document.getElementById("cycle").options[document.getElementById("cycle").selectedIndex].text);
 	
  	if(document.getElementById('kpiRemark').value==''){
  		evaStr = evaStr.replace('kpiRemarkValue',"");
 		evaStr = evaStr.replace('kpiRemarkValue',"''");
 	}else {
 		evaStr = evaStr.replace('kpiRemarkValue',document.getElementById('kpiRemark').value);
 		evaStr = evaStr.replace('kpiRemarkValue',document.getElementById('kpiRemark').value);
 	}	
 	if(document.getElementById('evaId').value==''){
 		evaStr = evaStr.replace('evaIdValue',"''");
 	}else {
 		evaStr = evaStr.replace('evaIdValue',document.getElementById('evaId').value);
 	}
 	if(document.getElementById('agreementWorkId').value==''){
 		evaStr = evaStr.replace('agreementWorkIdValue',"''");
 	}else {
 		evaStr = evaStr.replace('agreementWorkIdValue',document.getElementById('agreementWorkId').value);
 	}
 	evaStr = evaStr.replace('algorithmTypeValue',document.getElementById('algorithmType').value);
 	evaStr = evaStr.replace('algorithmTypeName',document.getElementById("algorithmType").options[document.getElementById("algorithmType").selectedIndex].text);
 	var algorithmName ='';
 	var algorithmvalue ='';
 	//根据不同的算法得到，算法描述和算法存储格式
 	if(document.getElementById('algorithmType').value==0){
 		if(document.getElementById('algorithm').value==''){
 			alert("请填写考核指标算法");
 			return false;
 		}  	
 		evaStr = evaStr.replace('algorithmName',document.getElementById('algorithm').value);
 		evaStr = evaStr.replace('algorithmValue',document.getElementById('algorithm').value);
 	}else if(document.getElementById('algorithmType').value==1){
 		if(document.getElementById('rateMiddleSpan').value==''){
 			alert("请填写考核指标算法");
 			return false;
 		}  	
 		algorithmName = document.getElementById('rateFullSpan').value+'%~';
 		algorithmName = algorithmName+document.getElementById('rateMiddleSpan').value+'% 得满分，';
 		algorithmName = algorithmName+document.getElementById('rateMiddleSpan').value+'%~';
 		algorithmName = algorithmName+document.getElementById('rateZeroSpan').value+'%不得分';
 		algorithmvalue = algorithmvalue+document.getElementById('rateFullSpan').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('rateMiddleSpan').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('rateZeroSpan').value+'-100,0';
 	}else{
 		if(document.getElementById('rateTwoSpan').value==''||document.getElementById('rateThreeSpan').value==''||document.getElementById('workScoreOne')==''||document.getElementById('workScoreTwo')==''||document.getElementById('workScoreThree')==''){
 			alert("请填写考核指标算法");
 			return false;
 		} 	
 	 	algorithmName = document.getElementById('rateOneSpan').value+'%~'+document.getElementById('rateTwoSpan').value+'%';
 		algorithmName = algorithmName+' 得总分的'+document.getElementById('workScoreOne').value+'%,';
 		algorithmName = algorithmName+document.getElementById('rateTwoSpan').value+'%~'+document.getElementById('rateThreeSpan').value+'%';
 		algorithmName = algorithmName+' 得总分的'+document.getElementById('workScoreTwo').value+'%,';
 		algorithmName = algorithmName+document.getElementById('rateThreeSpan').value+'%~'+document.getElementById('rateFourSpan').value+'%';
 		algorithmName = algorithmName+' 得总分的'+document.getElementById('workScoreThree').value+'%,';
 		algorithmvalue = algorithmvalue+document.getElementById('rateOneSpan').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('rateTwoSpan').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('rateThreeSpan').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('rateFourSpan').value+'-';
 		algorithmvalue = algorithmvalue+document.getElementById('workScoreOne').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('workScoreTwo').value+',';
 		algorithmvalue = algorithmvalue+document.getElementById('workScoreThree').value;
 	}
 		evaStr = evaStr.replace('algorithmName',algorithmName);
 		evaStr = evaStr.replace('algorithmValue',algorithmvalue);
 	if(tableId==''){
 		evaStr = '<div id="evaTable-'+tableMax+'">'+evaStr+'</div>';
 		Ext.DomHelper.append(parent.opener.document.getElementById('kpiDiv'),evaStr,true);
 	}else{
 		Ext.DomHelper.overwrite(parent.opener.document.getElementById(tableId),evaStr,true);  
 	}
 	window.close();
        }
function confirmWeight(){
	var weight = parseFloat(document.getElementById('weight').value);
	if(weight>${allWeight}){
		alert('超出分数范围！');
		document.getElementById('weight').value = '0';
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
function putDefaultValue(evaDiv){
	var evaStartTime = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[0].value;
	var evaEndTime = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[1].value;
	var kpiName = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[2].value;
	var toOrgUserName =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[3].value;
	var toOrgUser =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[4].value;
	var evaSource =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[5].value;
	var algorithmType =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[6].value;
	var algorithm =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[7].value;
	var weight =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[8].value;
	var cycle =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[9].value;
	var evaId =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[10].value;
	var kpiRemark =  parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[11].value;
	var agreementWorkId = parent.opener.document.getElementById(evaDiv).getElementsByTagName("input")[12].value;
	document.getElementById('evaStartTime').value = evaStartTime;
	document.getElementById('evaEndTime').value = evaEndTime;
	document.getElementById('kpiName').value = kpiName;
	document.getElementById('evaSource').value = evaSource;
	document.getElementById('toOrgUserName').value = toOrgUserName;
	document.getElementById('toOrgUser').value = toOrgUser;
	document.getElementById('algorithm').value = algorithm;
	document.getElementById('weight').value = weight;
	document.getElementById('cycle').value = cycle;
	document.getElementById('algorithmType').value = algorithmType;
	document.getElementById('evaId').value = evaId;
	document.getElementById('kpiRemark').value = kpiRemark;
	document.getElementById('agreementWorkId').value = agreementWorkId;
	changeAlgorithmType();
	
	if(algorithmType==0){
	
	}else if(algorithmType==1){
		var rate = algorithm.split('-')[0].split(',');
		var Score = algorithm.split('-')[1].split(',');
		document.getElementById('rateMiddle').value = rate[1];
		document.getElementById('rateMiddleSpan').value = rate[1];
	}else{
		var rate = algorithm.split('-')[0].split(',');
		var Score = algorithm.split('-')[1].split(',');
		document.getElementById('rateTwo').value = rate[1];
		document.getElementById('rateTwoSpan').value = rate[1];
		document.getElementById('rateThree').value = rate[2];
		document.getElementById('rateThreeSpan').value = rate[2];
		document.getElementById('workScoreOne').value = rate[0];
		document.getElementById('workScoreTwo').value = rate[1];
		document.getElementById('workScoreThree').value = rate[2];
		
	}
	document.getElementById('algorithm').value = algorithm;

}
</script>
	<%@ include file="/common/footer_eoms.jsp"%>