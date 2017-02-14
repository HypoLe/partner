<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
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
	/*校验组织名称是否重复*/
	function checkDeptName(){
		var deptName=document.getElementById("name").value;
		Ext.Ajax.request({
		 		url:"${app}/partner/appops/partnerAppOpsDept.do",
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
	Ext.onReady(function() {
		var userTreeAction = eoms.appPath + '/xtree.do?method=userFromDept';
		new xbox({
			btnId : 'areatree',
			treeDataUrl : '${app}/partner/baseinfo/xtree.do?method=area',
			treeRootId : '${province}',
			treeRootText : '四川',
			treeChkMode : '',
			treeChkType : 'area',
			single : false,
			showChkFldId : 'areaNames',
			saveChkFldId : 'areaId',
			returnJSON : false
		});

	})

	function getareaId() {
		var areaId = document.getElementById("areaId").value;
		var areaname = document.getElementById("areaNames").value;

		alert("areaId   " + areaId + "     areaname" + areaname);
	}

	function openSelectAreas() {
		window
				.open(
						eoms.appPath
								+ '/partner/baseinfo/areaDeptTrees.do?method=selectAreas',
						'newwindow',
						'height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
	}

	function checkNum(theInput, str) {
		a = parseInt(theInput);
		if (isNaN(a)) {
			alert(str + "请输入数字");
			return false;
		} else
			return true;
	}
	function isEmail(strEmail) {
		if (strEmail
				.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
			return true;
		else {
			document.forms[0].email.value = '';
			alert("请检查邮箱格式！");
		}
	}
	window.onload = function() {
		var proId = '${iPnrPartnerAppOpsDeptForm.id}';
		var operType = '${operType}';
		if (proId != '' && operType == 'save') {
			parent.setproId(proId);
		}
	}
	window.frameElement.height = 1000;
	var isEdit = '${isEdit}';
	if (isEdit != 'add') {
		document.body.style.overflow = "hidden";
	}
	var subRetutn = false;
	function sub() {
		var str = "";
		var selcity = document.getElementsByName("selcity");
		for ( var i = 0; i < selcity.length; i++) {
			if (selcity[i].checked == true) {
				str += selcity[i].value;
				str += "|";
			}
		}
		document.getElementById("sel_city").value = str;
		subRetutn = true;
		var id = '${iPnrPartnerAppOpsDeptForm.id}';
		var treeNodeId = '${iPnrPartnerAppOpsDeptForm.treeNodeId}';
		treeNodeId = treeNodeId.substring(0, 5);
		if (subRetutn) {
			if (v.check()) {
				$("iPnrPartnerAppOpsDeptForm").submit();
			}
		} else {
			alert("单位类型为必选项");
			return false;
		}
	}
	function companyChange() {
		var ifCompany = document.getElementById("ifCompany").value;
		if (ifCompany == 'yes') {
			eoms.form.enableArea('see1');
			eoms.form.enableArea('see2');
			eoms.form.enableArea('see3');
		} else if (ifCompany == 'no') {
			eoms.form.disableArea('see1', true);
			eoms.form.disableArea('see2', true);
			eoms.form.disableArea('see3', true);
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
</script>

<html:form action="/partnerAppOpsDept.do?method=saveCompanyBaseInfoGrandsonGroup&isPartner=${isPartner}"
	styleId="iPnrPartnerAppOpsDeptForm" method="post">

	<input type="hidden" name="parentNodeId" value="${parentNodeId }">
	<input type="hidden" name="parentid" id="parentid" value="${id }" />
	<input type="hidden" name="parentDeptname" id="parentDeptname"
		value="${parentDeptname}" />
	<input type="hidden" name="parentAreaId" id="parentAreaId"
		value="${parentAreaId}" />
		<input type="hidden" name="parentAreaName" id="parentAreaName"
		value="${parentAreaName}" />
		
	<input type="hidden" name="sel_city" id="sel_city" value="">
	<input type="hidden" name="deptLevel" value="4">
	<html:hidden property="treeNodeId" />
	<html:hidden property="treeId" />

	<fmt:bundle
		basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

		<table class="formTable">
			<caption>
				<div class="header center">
					添加子组织
				</div>
			</caption>
		</table>
		<table class="formTable">
			<tr>
				<td colspan="4" class="label">
					所属上层组织:&emsp;${parentDeptname}
				</td>
			</tr>
			<tr>
				<td class="label">
					组织名称*
				</td>
				<td class="content" colspan="3">
					<input name="name" id="name" class="max text" style="width:70%"
					onblur="checkDeptName()" alt="allowBlank:false,vtext:''"
					value="${iPnrPartnerAppOpsDeptForm.name}" /><span id="errorMsg" style="color: red"></span>
			</td>
			</tr>
			
			<c:if test="${!empty iPnrPartnerAppOpsDeptForm.organizationNo}">
				<td class="label" cospan="3">
					组织编码*
				</td>
				<td class="content">
					${iPnrPartnerAppOpsDeptForm.organizationNo}
				</td>
			
			<input name="organizationNo" id="organizationNo" type="hidden" value="${iPnrPartnerAppOpsDeptForm.organizationNo}">
			
			</c:if>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="partnerDept.contactor" />
					&nbsp;*
				</td>
				<td class="content">
					<html:text property="contactor" styleId="contactor"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32"
						value="${iPnrPartnerAppOpsDeptForm.contactor}" />
				</td>

				<td class="label">
					<fmt:message key="partnerDept.phone" />
					&nbsp;*
				</td>
				<td class="content">
					<html:text property="phone" styleId="phone"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${iPnrPartnerAppOpsDeptForm.phone}" />
				</td>
			</tr>			
				<html:hidden property="interfaceHeadId"
					value="${iPnrPartnerAppOpsDeptForm.interfaceHeadId}" />
				<html:hidden property="deptMagId"
					value="${iPnrPartnerAppOpsDeptForm.deptMagId}" />
			<tr>
				<td class="label">
					组织地址*
				</td>
				<td class="content" colspan="3">
					<html:text property="address" styleId="address"
						styleClass="text max" onblur="testCharSize(this,255);"
						alt="allowBlank:false" value="${iPnrPartnerAppOpsDeptForm.address}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerDept.remark" />
				</td>
				<td class="content" colspan="7">
					<textarea name="remark" id="remark" class="textarea max">${iPnrPartnerAppOpsDeptForm.remark}</textarea>
				</td>
			</tr>
		</table>

	</fmt:bundle>
	<table>
		<tr>
			<td>
				<input type="button" class="btn"	value="<fmt:message key="button.save"/>" onclick="sub();" />&nbsp;&nbsp;
				<input type="button" class="btn"	value="返回" onclick="javascript:self.history.back();" />
					
			</td>
		</tr>
	</table>
	<html:hidden property="id" value="${iPnrPartnerAppOpsDeptForm.id}" />
	<html:hidden property="areaId" value="${iPnrPartnerAppOpsDeptForm.areaId}" />
</html:form>
<script type="text/javascript">
	var interfaceHeadIdseled = '${iPnrPartnerAppOpsDeptForm.interfaceHeadId}';
	var id = '${iPnrPartnerAppOpsDeptForm.id}';
	if (interfaceHeadIdseled != '' && interfaceHeadIdseled != id) {
		document.getElementById("interfaceHeadId").value = interfaceHeadIdseled;
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>