<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	var checkDeptNameFlag=false;
	Ext.onReady(function() {
		
		checkDeptNameFlag=true;//加载的时候置为true避免未触发onblur事件;
		v = new eoms.form.Validation({form : 'iPnrPartnerAppOpsDeptForm'});
		v.custom=function(){
			if(checkDeptNameFlag==false){
				Ext.Msg.alert("提示信息","该组织名称已经使用,请选用其它名称!")
				return false;
			}else{
				return true;
			}
		}
	})
	/*校验组织名称是否重复*/
	function checkDeptName(){
		var oldDeptName="${iPnrPartnerAppOpsDeptForm.name}";//先获取加载的值;
		var deptName=document.getElementById("name").value.trim();
		if(oldDeptName==deptName){
			document.getElementById("errorMsg").innerHTML="";
			checkDeptNameFlag=true;
			return;
		}
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
	function checkNum(theInput, str) {
		a = parseInt(theInput);
		if (isNaN(a)) {
			alert(str + "请输入数字");
			return false;
		} else
			return true;
	}
	window.onload = function() {
		var proId = '${iPnrPartnerAppOpsDeptForm.id}';
		var operType = '${operType}';
		if (proId != '' && operType == 'save') {
			parent.setproId(proId);
		}
	}
	var subRetutn = false;
	function sub() {
		
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
	
/*增加一行*/
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
	/*删除行*/
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
	/*删除行*/
	function delRowAndObject(obj,id) {
		if(id==""){//如果为空表示删除新增的空白行或者还未保存的数据
			delRow(obj);
		}else{
			if(confirm("是否需要删除?") == true){
			/*ajax删除model*/
			Ext.Ajax.request({
			 		url:"${app}/partner/appops/partnerAppOpsDept.do",
			 		params:{method:"removePnrQualification",id:id},
			 		success:function(res,opt){
			 			Ext.Msg.alert("提示信息",Ext.util.JSON.decode(res.responseText).info,
			 			function(){
			 				window.location.reload();//重新加载页面
			 			})
			 		},
			 		failure:function(){
			 			Ext.Msg.alert("提示信息","请求失败",function(){window.location.reload();})
			 		}
			 	});
			 }
		}
	}
</script>

<html:form action="/partnerAppOpsDept.do?method=editCompanyBaseInfoGrandson&isPartner=${isPartner}"
	styleId="iPnrPartnerAppOpsDeptForm" method="post">

	<input type="hidden" name="parentNodeId" value="${parentNodeId }">


	<html:hidden property="dept_name" value="${iPnrPartnerAppOpsDeptForm.name}" />
	<input type="hidden" name="deptLevel" value="3">
	<html:hidden property="treeNodeId" />
	<html:hidden property="treeId" />

	<fmt:bundle
		basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

		<table class="formTable">
			<caption>
				<div class="header center">
					组织信息
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
			</tr>
			<tr>
				<c:if test="${!empty iPnrPartnerAppOpsDeptForm.organizationNo}">
					<td class="label">
					组织编码*
				</td>
				<td class="content">
					${iPnrPartnerAppOpsDeptForm.organizationNo}
				</td>
			<input name="organizationNo" id="organizationNo" type="hidden" value="${iPnrPartnerAppOpsDeptForm.organizationNo}">
			
				</c:if>
				<td class="label">
					所在地域&nbsp;*
				</td>
				
				<td class="content">
					<input type="text" class="text medium" name="areaName" id="areaName"
						value="${iPnrPartnerAppOpsDeptForm.areaName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="areaId" id="areaId" value="${iPnrPartnerAppOpsDeptForm.areaId}" type="hidden" />
					<eoms:xbox id="deptTree"
						dataUrl="${app}/xtree.do?method=areaTree"
						rootId="" rootText="区域" valueField="areaId" handler="areaName"
						textField="areaName" checktype="area" single="true" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerDept.contactor" />
					&nbsp;*
				</td>
				<td class="content">
					<html:text property="contactor" styleId="contactor" styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${iPnrPartnerAppOpsDeptForm.contactor}" />
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
					<html:text property="address" styleId="address" styleClass="text max"  alt="allowBlank:false" value="${iPnrPartnerAppOpsDeptForm.address}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="partnerDept.remark" />
				</td>
				<td class="content" colspan="3">
					<textarea property="remark" styleId="remark" class="textarea max">
						${iPnrPartnerAppOpsDeptForm.remark}
					</textarea>
				</td>
			</tr>
		</table>
	</fmt:bundle>
	<table>
		<tr>
			<td>
				<input type="button" class="btn"
					value="<fmt:message key="button.save"/>" onclick="sub();" />
			</td>
		</tr>
	</table>
	<html:hidden property="id" value="${iPnrPartnerAppOpsDeptForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>