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
		v = new eoms.form.Validation({form:'iPnrPartnerAppOpsDeptForm'});
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
    window.open (eoms.appPath+'/partner/appops/partnerAppOpsDept.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
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
    var proId = '${iPnrPartnerAppOpsDeptForm.id}';
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

	subRetutn = true;
	var id = '${iPnrPartnerAppOpsDeptForm.id}';
	var treeNodeId = '${iPnrPartnerAppOpsDeptForm.treeNodeId}';
	treeNodeId = treeNodeId.substring(0,5);
	if(subRetutn){
		if(v.check()){
	       $("iPnrPartnerAppOpsDeptForm").submit();
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

<form action="partnerAppOpsDept.do?method=saveCompanyBaseInfo&isPartner=${isPartner}"
	id="iPnrPartnerAppOpsDeptForm" method="post">

	<input type="hidden" name="parentNodeId" value="${parentNodeId }">
	<input type="hidden" name="deptLevel" value="1">
	<input type="hidden" name="treeNodeId"
		value="${iPnrPartnerAppOpsDeptForm.treeNodeId }">
	<input type="hidden" name="treeId" value="${iPnrPartnerAppOpsDeptForm.treeId }">


	<table class="formTable">
		<caption>
			<div class="header center">
				组织基本信息
			</div>
		</caption>

		<tr>
			<td class="label">
				组织名称*
			</td>
			<td class="content" colspan="3">
				<input name="name" id="name" class="max text" style="width:70%"
					onblur="checkDeptName()" alt="allowBlank:false,vtext:''"
					value="${iPnrPartnerAppOpsDeptForm.name}" /><span id="errorMsg" style="color: red"></span>
			</td>
		<tr>
			<c:if test="${!empty iPnrPartnerAppOpsDeptForm.organizationNo}">
				<td class="label">
					组织编码*
				</td>
				<td class="content" colspan="3">
					${iPnrPartnerAppOpsDeptForm.organizationNo}
				</td>
			</c:if>
			
		</tr>
	
		<tr>
			<td class="label">
				联系人*
			</td>
			<td class="content">
				<input name="contactor" id="contactor" class="text medium"
					alt="allowBlank:false,vtext:'',maxLength:32"
					value="${iPnrPartnerAppOpsDeptForm.contactor}" />
			</td>
			<td class="label">
				联系电话*
			</td>
			<td class="content">
				<input name="phone" id="phone" class="text medium"	onblur="testCharSize(this,255);" 
				alt="allowBlank:false,vtext:''"	value="${iPnrPartnerAppOpsDeptForm.phone}" />
			</td>
		</tr>
		
			
		<input name="interfaceHeadId" id="interfaceHeadId" type="hidden"		value="${iPnrPartnerAppOpsDeptForm.interfaceHeadId}" />
		<input name="deptMagId" id="deptMagId" type="hidden"		value="${iPnrPartnerAppOpsDeptForm.deptMagId}" />
		<tr>
			<td class="label">
				组织地址*
			</td>
			<td class="content" colspan="3">
				<input name="address" id="address" type="text" class="text max"
					onblur="testCharSize(this,255);" alt="allowBlank:false,vtext:''"
					value="${iPnrPartnerAppOpsDeptForm.address}" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="7">
				<textarea name="remark" id="remark" class="textarea max">${iPnrPartnerAppOpsDeptForm.remark}</textarea>
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
	<input name="id" id="id" type="hidden" value="${iPnrPartnerAppOpsDeptForm.id}" />
	<input name="areaId" type="hidden" value="${iPnrPartnerAppOpsDeptForm.areaId}" />
	<form>
		<script type="text/javascript">
	var interfaceHeadIdseled = '${iPnrPartnerAppOpsDeptForm.interfaceHeadId}';
	var id = '${iPnrPartnerAppOpsDeptForm.id}';
	if(interfaceHeadIdseled!=''&&interfaceHeadIdseled!=id){
		document.getElementById("interfaceHeadId").value = interfaceHeadIdseled;
	}
</script>
		<%@ include file="/common/footer_eoms.jsp"%>