<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style>
	.redStar {
		color:red;
	}
</style>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
 
<div align="center"><b>新增资源标准</b></div><br><br/>
<span style="color:red">配置说明：相同"配置类型"下的某个专业只能配置一个标准
</span><br><br><br>
<form action="${app}/partner/baseinfo/pnrStandardConfig.do?method=savePnrStandardConfig" method="post" id="pnrStandardConfigForm" name="pnrStandardConfigForm" >
	<!-- 隐藏域 begin -->
	<input type="hidden" id="id" name="id" value="${pnrStandardConfig.id}" />
	<input type="hidden" id="provinceId" name="provinceId" value="${pnrStandardConfig.provinceId}" />
	<input type="hidden" id="cityId" name="cityId" value="${pnrStandardConfig.cityId}" />
	<input type="hidden" id="countryId" name="countryId" value="${pnrStandardConfig.countryId}" />
	<input type="hidden" id="addUser" name="addUser" value="${pnrStandardConfig.addUser}" />
	<input type="hidden" id="isdeleted" name="isdeleted" value="${pnrStandardConfig.isdeleted}" />
	<!-- 隐藏域 end -->
	<table id="sheet" class="formTable">
					<tr><%--
						<td class="label">
						 	区域<span class="redStar">*</span>
						</td>
						<td class="content">
							<input type="text" name="areaName" id="areaName" value="${pnrStandardConfig.areaName}"   class="text medium" alt="allowBlank:false" readonly="readonly"/>
							<input type="hidden" name="areaId" id="areaId" value="${pnrStandardConfig.areaId}"  class="text medium"/>
							<input type="button"   name="treeBtn_10" id="treeBtn_10" value="请选择区域" class="btn" />
							<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
							rootText='所属区域' valueField="areaId" handler="treeBtn_10" textField="areaName"	checktype="" single="true">
							</eoms:xbox>
						</td>
						--%>
						<td class="label">
						 	巡检专业<span class="redStar">*</span>
						</td>
						<td class="content">
								<eoms:comboBox id="professional" name="professional" initDicId="11225" defaultValue="${pnrStandardConfig.professional}"
								 styleClass="input select"  alt="allowBlank:false" onchange="setConfigDw();"/>
						</td>
						<td class="label">
						 	配置类型<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="configType" name="configType" initDicId="12402" onchange="setConfigDw();"
								defaultValue="${pnrStandardConfig.configType}" styleClass="input select"  alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	配置标准<span class="redStar">*</span>
						</td>
						<td class="content" colspan="3">
								<input  type="hidden" id="configDw" name="configDw" value="${pnrStandardConfig.configDw}"  />
								<input class="text" type="text" id="standardConfig" name="standardConfig" value="${pnrStandardConfig.standardConfig}" alt="allowBlank:false,vtype:'number',vtext:'请输入整数'" />
								<span id="info" >${pnrStandardConfig.configDw}</span>
						</td>
					</tr>
					<tr>
						<td class="label"> 
							备注 
						</td>
						<td class="content" colspan="7">
							<textarea name="remark"  class="textarea max" id="remark" >${pnrStandardConfig.remark}
							</textarea>
						</td>
					</tr>
	</table>
		<br>
		<input  type="submit" class="btn"  value="保存" />
		<input  type="reset" class="btn"  value="重置" />
		<input  type="button" class="btn"  value="返回" onclick="goBack();" />
		<%--<input  type="button" class="btn"  value="重置" onclick="res();"/>
--%>
</form>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	Ext.onReady(function(){
	        v1 = new eoms.form.Validation({form:'pnrStandardConfigForm'});
	        v1.custom = function() {
	        		var n=document.getElementById("standardConfig").value;
	        		if(n<=0){
	        			alert("配置标准必须为大于0的整数");
	        			return false;
	        		}
	        		return true;
	        }
	});
	function setConfigDw(){
	   	 var configType=document.getElementById("configType").value;
	   	 var professional=document.getElementById("professional").value;
	   	 var dw=document.getElementById("configDw");
	   	 var info=document.getElementById("info");
	   	 var d1="资源点/人";
	   	 var d2="资源点/辆"
	   	 if(configType=='1240201'){ //如果是"人员"
	   	 	if(professional!=""){
	   	 		info.innerHTML=d1;
	   	 	}else{
	   	 		info.innerHTML="";
	   	 	}
	   	 }else if(configType=='1240202'){ //如果是"车辆"
		   	 	if(professional!=""){
		   	 		info.innerHTML=d2;
		   	 	}else{
	   	 			info.innerHTML="";
	   	 		}
	   	 }else{
	   	 	info.innerHTML="";
	   	 }
	   	 dw.value=info.innerHTML;
	}
	function res(){
		var inputs = document.getElementsByTagName('input');
	   	 var selects =document.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
	function goBack(){
		window.location.href="${app}/partner/baseinfo/pnrStandardConfig.do?method=gotoPnrStandardConfigListPage";
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>