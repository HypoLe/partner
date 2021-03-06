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
		var proId = '${partnerDeptForm.id}';
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
		var bigTypes = document.getElementsByName("bigType");
		for ( var i = 0; bigTypes.length > i; i++) {
			var bigTypeId = bigTypes[i].checked;
			if (bigTypes[i].checked) {
				subRetutn = true;
			}
		}
		var id = '${partnerDeptForm.id}';
		var treeNodeId = '${partnerDeptForm.treeNodeId}';
		treeNodeId = treeNodeId.substring(0, 5);
		if (subRetutn) {
			if (v.check()) {
				$("partnerDeptForm").submit();
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
<html:form action="/partnerDepts.do?method=saveCompanyBaseInfoGrandsonGroup&isPartner=${isPartner}"
	styleId="partnerDeptForm" method="post">

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
					value="${partnerDeptForm.name}" /><span id="errorMsg" style="color: red"></span>
			</td>
			</tr>
			
			<c:if test="${!empty partnerDeptForm.organizationNo}">
				<td class="label" cospan="3">
					组织编码*
				</td>
				<td class="content">
					${partnerDeptForm.organizationNo}
				</td>
			
			<input name="organizationNo" id="organizationNo" type="hidden" value="${partnerDeptForm.organizationNo}">
			
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
						value="${partnerDeptForm.contactor}" />
				</td>

				<td class="label">
					<fmt:message key="partnerDept.phone" />
					&nbsp;*
				</td>
				<td class="content">
					<html:text property="phone" styleId="phone"
						styleClass="text medium" onblur="testCharSize(this,255);"
						alt="allowBlank:false,vtext:''" value="${partnerDeptForm.phone}" />
				</td>
			</tr>



			<tr>
				<td class="label">
					邮箱&nbsp;*
				</td>
				<td class="content">
					<html:text property="email" styleId="email"
						styleClass="text medium"
						onblur="isEmail(this.value);testCharSize(this,255);"
						alt="allowBlank:false,vtext:'',maxLength:64"
						value="${partnerDeptForm.email}" />
				</td>

				<td class="label">
					<fmt:message key="partnerDept.fax" />
				</td>
				<td class="content">
					<html:text property="fax" styleId="fax" styleClass="text medium"
						onblur="testCharSize(this,255);" alt="allowBlank:true,vtext:''"
						value="${parentPnrDept.fax}" />
				</td>
			</tr>

			<tr>

				<td class="label">
					<fmt:message key="partnerDept.zip" />
					&nbsp;*
				</td>
				<td class="content" colspan="3">
					<html:text property="zip" styleId="zip" styleClass="text medium"
						onblur="testCharSize(this,255);"
						alt="allowBlank:true,vtext:'',maxLength:32"
						value="${partnerDeptForm.zip}" />
				</td>


			</tr>

			<tr>
				<td class="label">
					专业 &nbsp;*
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
				<c:if test="${partnerList!=null}">
					<tr>
						<td class="label">
							所属公司&nbsp;*
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
				<html:hidden property="interfaceHeadId"
					value="${partnerDeptForm.interfaceHeadId}" />
				<html:hidden property="deptMagId"
					value="${partnerDeptForm.deptMagId}" />
			<tr>
				<td class="label">
					公司地址*
				</td>
				<td class="content" colspan="3">
					<html:text property="address" styleId="address"
						styleClass="text max" onblur="testCharSize(this,255);"
						alt="allowBlank:false" value="${partnerDeptForm.address}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					简介
				</td>
				<td class="content" colspan="3">
					<textarea class="text max" type="text" name="companySynopses" id="companySynopses" />${partnerDeptForm.companySynopses}</textarea></td>
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
					<fmt:message key="partnerDept.remark" />
				</td>
				<td class="content" colspan="7">
					<textarea name="remark" id="remark" class="textarea max">${partnerDeptForm.remark}</textarea>
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
	<html:hidden property="id" value="${partnerDeptForm.id}" />
	<html:hidden property="areaId" value="${partnerDeptForm.areaId}" />
</html:form>
<script type="text/javascript">
	var interfaceHeadIdseled = '${partnerDeptForm.interfaceHeadId}';
	var id = '${partnerDeptForm.id}';
	if (interfaceHeadIdseled != '' && interfaceHeadIdseled != id) {
		document.getElementById("interfaceHeadId").value = interfaceHeadIdseled;
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>