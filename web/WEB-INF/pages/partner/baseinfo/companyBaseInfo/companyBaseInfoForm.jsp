<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	var checkDeptNameFlag=false;
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'partnerDeptForm'});
		v.custom=function(){
			if(checkDeptNameFlag==false){
				Ext.Msg.alert("提示信息","该组织名称已经使用,请选用其它名称!")
				return false;
			}else{
				return true;
			}
		}
});


function getareaId(){
	var areaId=document.getElementById("areaId").value;
	alert("areaId   "+ areaId);
}

function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}

function checkCapital(){ 
	var theInput=document.getElementById("registerCapital").value;
    if (theInput.search(/^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/)!= -1) 
   		{ 
 			 return true;
  			} 
  	else{
     	 alert("请输入正确的金额"); 
      	return false;
		}
  }
function isEmail(strEmail) {
	if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
	return true;
	else{
		document.forms[0].email.value = '';
		alert("请检查邮箱格式！");
	}
}
window.onload = function(){
    var proId = '${partnerDeptForm.id}';
	var operType = '${operType}';
	if(proId != '' && operType == 'save'){
	    parent.setproId(proId);
    }
}
	window.frameElement.height = 1000;
	var isEdit = '${isEdit}';
	if(isEdit!='add'){
		document.body.style.overflow = "hidden";
	}     
    var subRetutn = false;
function sub() {    
	var bigTypes = document.getElementsByName("bigType");
	for(var i = 0 ;bigTypes.length>i ; i++){
		var bigTypeId = bigTypes[i].checked;
		if(bigTypes[i].checked){
			subRetutn = true;
		}
	}
	var id = '${partnerDeptForm.id}';
	var treeNodeId = '${partnerDeptForm.treeNodeId}';
	treeNodeId = treeNodeId.substring(0,5);
	if(subRetutn){
		if(v.check()){
	       $("partnerDeptForm").submit();
		} 
	}else {
		alert("巡检专业为必选项");
		return false;	
	}
}

function addRow(obj) {
		var vNum=jq("#qualifyTable tbody tr").size()+1;
		var tr = jq(obj).parents("tr");//找到所在的tr
		var modTr=jq("#xxx table tbody").html();
		var trModel=jq("#s0");
		var trModelClone=trModel.clone();
		trModelClone[0].id="s"+vNum;
		tr.after(trModelClone);
	}
	function addRow1(obj) {
		var vNum=jq("#qualifyTable tbody tr").size()+1;
		var tr = jq(obj).parents("tr");//找到所在的tr
		var trModel=jq("#s0");
		var trModelClone=trModel.clone();
		trModelClone[0].id="s"+vNum;
		tr.after(trModelClone);
		tr.remove();
	}
	function delRow(obj) {
		var vNum=jq("#qualifyTable tbody tr").size();
		var tr = jq(obj).parents("tr");
		tr.remove();
		if(vNum==1){
			var tbody=jq("#qualifyTable tbody");
			var tr2="<tr id=\"ss0\" ><td colspan=\"5\" align=\"right\"><input onclick=\"addRow1(this)\" type=\"button\"";
			tr2+="style=\"width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image:";
			tr2+="url('${app }/images/icons/add.png');background-repeat: no-repeat;\"/></td></tr>";
			tbody.html(tr2);
		}
	}
	/*校验组织名称是否重复*/
	function checkDeptName(){
		var deptName=document.getElementById("name").value;
		Ext.Ajax.request({
		 		url:"${app}/partner/baseinfo/partnerDepts.do",
		 		params:{method:"checkDeptName",deptName:deptName},
		 		success:function(res,opt){
		 			var flag=res.responseText;
		 			if(flag=="true"){
		 				document.getElementById("errorMsg").innerHTML="";
		 				checkDeptNameFlag=true;
		 			}else{
		 				document.getElementById("errorMsg").innerHTML="组织名称已经被占用,请选用其它名称!";
		 				checkDeptNameFlag=false;
		 			}
		 		},
		 		failure:function(){
		 			checkDeptNameFlag=false;
		 			Ext.Msg.alert("提示信息","名称校验失败请重试!")
		 		}
		 	});
	}
</script>

<div id="xxx" style="display:none">
<table>
<tbody>
<tr id="s0">
<td align="center">
	<input type="text" class="text max" name="qualifyNames" id="qualifNames" alt="allowBlank:false,vtext:''"> 
</td>
<td  align="center">
	<input type="text" class="text max" name="qualifyLevels" id="qualifyLevels" alt="allowBlank:false,vtext:''"> 
</td>
<td  align="center">
	<input type="text" class="text max" name="issueAuthors" id="issueAuthors" alt="allowBlank:false,vtext:''"> 
</td>
<td  align="center">
	<input type="text" id="outOfDates" name="outOfDates"	class="text max"  readonly="true"
	onclick="popUpCalendar(this, this,null,null,null,false,-1);"	alt="allowBlank:false"/>
</td>
<td  align="center" >
	<input onclick="addRow(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;"  value="" />
	<input onclick="delRow(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/list-delete.gif');background-repeat: no-repeat;"  value="" />
</td>
<input type="hidden" name="qualifyId" />
</tr>
</tbody></table>
</div>
<form action="partnerDepts.do?method=saveCompanyBaseInfo&isPartner=${isPartner}"
	id="partnerDeptForm" method="post">

	<input type="hidden" name="parentNodeId" value="${parentNodeId }">
	<input type="hidden" name="deptLevel" value="1">
	<input type="hidden" name="treeNodeId"
		value="${partnerDeptForm.treeNodeId }">
	<input type="hidden" name="treeId" value="${partnerDeptForm.treeId }">


	<table class="formTable">
		<caption>
			<div class="header center">
				巡检组织基本信息
			</div>
		</caption>

		<tr>
			<td class="label">
				组织名称*
			</td>
			<td class="content" colspan="3">
				<input name="name" id="name" class="max text" style="width:70%"
					onblur="checkDeptName()" alt="allowBlank:false,vtext:''"
					value="${partnerDeptForm.name}" /><span id="errorMsg" style="color: red"></span>
			</td>
		<tr>
			<c:if test="${!empty partnerDeptForm.organizationNo}">
				<td class="label">
					组织编码*
				</td>
				<td class="content">
					${partnerDeptForm.organizationNo}
				</td>
			</c:if>
			<td class="label">
				是否为公司*
			</td>
			<td class="content" colspan="3">
				<c:if test="${empty partnerDeptForm.ifCompany}">
					<%--<select name="ifCompany" id="ifCompany"  onchange="companyChange();">
				<option value="yesorno" selected="selected">请选择 </option>
				<option value="yes">是 </option>
				<option value="no">否 </option>
				--%>
				是
				<input type="hidden" name="ifCompany" id="ifCompany" value="yes" />
				</c:if>
				<c:if test="${!empty partnerDeptForm.ifCompany}">
					<input type="hidden" name="ifCompany" id="ifCompany"
						value="${partnerDeptForm.ifCompany}" />
					<c:if test="${partnerDeptForm.ifCompany=='yes'}">是</c:if>
					<c:if test="${partnerDeptForm.ifCompany=='no'}">否</c:if>
				</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td class="label">
				企业性质*
			</td>
			<td class="content">
				<eoms:comboBox name="type" id="type" initDicId="1120101" defaultValue='${partnerDeptForm.type}' styleClass="input select"
					alt="allowBlank:false,vtext:''" />
			</td>
			<td class="label">
				法人代表*
			</td>
			<td class="content">
				<html:text property="manager" styleId="manager"
					styleClass="text medium" onblur="testCharSize(this,255);"
					alt="allowBlank:false,vtext:''" value="${partnerDeptForm.manager}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				入围级别*
			</td>
			<td class="content">
				<eoms:comboBox name="selectedLevel" id="selectedLevel" styleClass="input select"
					initDicId="12403" defaultValue='${partnerDeptForm.selectedLevel}'
					alt="allowBlank:false,vtext:''" />
			</td>
			<td class="label">
				注册日期*
			</td>
			<td class="content">
				<input type="text" id="registerTime" name="registerTime"
					class="text medium"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					readonly="true"
					alt="vtype:'lessThen',link:'planEndTime',allowBlank:false"
					value='${partnerDeptForm.registerTime}' />
			</td>
		</tr>
		<tr>
			<td class="label">
				注册资本(万)*
			</td>
			<td class="content">
				<input name="fund" id="fund" class="text" value="${partnerDeptForm.fund}" alt="allowBlank:false,vtype:'float'"/>
			</td>
			<td class="label">
				货币种类*
			</td>
			<td>
				<eoms:comboBox name="kindCurrencies" id="kindCurrencies" styleClass="input select"
					initDicId="12404" defaultValue='${partnerDeptForm.kindCurrencies}'
					alt="allowBlank:false,vtext:''" />
			</td>
		</tr>
		<tr>
			<td class="label">
				联系人*
			</td>
			<td class="content">
				<input name="contactor" id="contactor" class="text medium"
					alt="allowBlank:false,vtext:'',maxLength:32"
					value="${partnerDeptForm.contactor}" />
			</td>
			<td class="label">
				联系电话*
			</td>
			<td class="content">
				<input name="phone" id="phone" class="text medium"	onblur="testCharSize(this,255);" 
				alt="allowBlank:false,vtext:''"	value="${partnerDeptForm.phone}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				传真
			</td>
			<td class="content">
				<input name="fax" id="fax" class="text medium"		onblur="testCharSize(this,255);" alt="allowBlank:true,vtext:''"		value="${partnerDeptForm.fax}" />
			</td>
			<td class="label">
				邮箱*
			</td>
			<td class="content">
				<html:text property="email" styleId="email" styleClass="text medium"
					onblur="isEmail(this.value);testCharSize(this,255);"
					alt="allowBlank:false,vtext:'',maxLength:64"
					value="${partnerDeptForm.email}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				邮编
			</td>
			<td class="content" colspan="3">
				<input name="zip" id="zip" class="text"
					onblur="testCharSize(this,255);" value="${partnerDeptForm.zip}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				巡检专业&nbsp;*
			</td>
			<td class="content" colspan="3">
				<c:forEach items="${specialtyList}" var="dictBigType">
					<c:if test="${dictBigType.dictRemark=='isTrue'}">
						<input type="checkbox" name="bigType"
							value="${dictBigType.dictId}" checked='true' />${dictBigType.dictName}
			</c:if>
					<c:if test="${dictBigType.dictRemark!='isTrue'}">
						<input type="checkbox" name="bigType"
							value="${dictBigType.dictId}" />${dictBigType.dictName}
			</c:if>
				</c:forEach>
			</td>
		</tr>
		<c:if test="${partnerList!=null}">
			<tr>
				<td class="label">
					所属上层组织&nbsp;*
				</td>
				<td class="content" colspan="3">
					<select name="interfaceHeadId" id="interfaceHeadId"
						alt="allowBlank:false,vtext:'请选择所属公司'">
						<option value="">
							--请选择所属公司--
						</option>
						<logic:iterate id="partnerList" name="partnerList">
							<option value="${partnerList.interfaceHeadId}">
								${partnerList.nodeName}
							</option>
						</logic:iterate>
					</select>
				</td>
			</tr>
		</c:if>
		<input name="interfaceHeadId" id="interfaceHeadId" type="hidden"		value="${partnerDeptForm.interfaceHeadId}" />
		<input name="deptMagId" id="deptMagId" type="hidden"		value="${partnerDeptForm.deptMagId}" />
		<tr>
			<td class="label">
				公司地址*
			</td>
			<td class="content" colspan="3">
				<input name="address" id="address" type="text" class="text max"
					onblur="testCharSize(this,255);" alt="allowBlank:false,vtext:''"
					value="${partnerDeptForm.address}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				公司简介
			</td>
			<td class="content" colspan="3">
				<textarea class="text max" type="text" name="companySynopses" id="companySynopses" alt="allowBlank:true" />${partnerDeptForm.companySynopses}</textarea>
			</td>
		</tr>
		<c:if test="${qualifyConfig=='1'}">
		</table>
			<fieldset>
			<legend >
				<b>组织资质信息</b>
			</legend>
			<table class="formTable" id="qualifyTable">
					<thead>
					<tr id="th1">
						<td align="center" class="label">
							<b>公司资质名称&nbsp;*</b>
						</td>
						<td align="center" class="label">
							<b>资质级别&nbsp;*</b>
						</td>
						<td align="center" class="label">
							<b>发证机关&nbsp;*</b>
						</td>
						<td align="center" class="label">
							<b>资质截止日期&nbsp;*</b>
						</td>
						<td align="center"  class="label">
							<b>操作</b>
						</td>
					</tr>
					</thead>
					<tbody>
					<tr id="s1">
						<td  align="right" colspan="5" >
							<input onclick="addRow1(this)" type="button" style="width:23px;height:22px;cursor: pointer;background-position: 0 50%;border: medium none;background-image: url('${app }/images/icons/add.png');background-repeat: no-repeat;"  value="" />
					</tr>
					</tbody>
			</table>
			</fieldset>
		<table class="formTable">
		</c:if>
		<tr>
			<td class="label">
				附件
			</td>
			<td class="content" colspan="3">
				<!-- <html:text property="accessory" styleId="accessory"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${partnerDeptForm.accessory}" />  -->

				<eoms:attachment idList="" idField="accessory"
					appCode="baseinfo" scope="request" name="partnerDeptForm"
					property="accessory" />
			</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="7">
				<textarea name="remark" id="remark" class="textarea max">${partnerDeptForm.remark}</textarea>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td>
				<input type="button" class="btn" value="保存" onclick="sub();" />
			</td>
		</tr>
	</table>
	<input name="id" id="id" type="hidden" value="${partnerDeptForm.id}" />
	<input name="areaId" type="hidden" value="${partnerDeptForm.areaId}" />
	<form>
		<script type="text/javascript">
	var interfaceHeadIdseled = '${partnerDeptForm.interfaceHeadId}';
	var id = '${partnerDeptForm.id}';
	if(interfaceHeadIdseled!=''&&interfaceHeadIdseled!=id){
		document.getElementById("interfaceHeadId").value = interfaceHeadIdseled;
	}
</script>
		<%@ include file="/common/footer_eoms.jsp"%>