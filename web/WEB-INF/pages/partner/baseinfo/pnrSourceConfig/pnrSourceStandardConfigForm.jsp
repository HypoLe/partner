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
 
<div align="center"><b>代维资源标准配置-增加页面</b></div><br><br/>
<form action="${app}/partner/baseinfo/pnrSourceConfig.do?method=savePnrSourceStandardConfig" method="post" id="pnrSourceStandardConfigForm" name="pnrSourceStandardConfigForm" >
	<!-- 隐藏域 begin -->
	<input type="hidden" id="id" name="id" value="${pnrSourceStandardConfig.id}" />
	<input type="hidden" id="provinceId" name="provinceId" value="${pnrSourceStandardConfig.provinceId}" />
	<input type="hidden" id="cityId" name="cityId" value="${pnrSourceStandardConfig.cityId}" />
	<input type="hidden" id="countryId" name="countryId" value="${pnrSourceStandardConfig.countryId}" />
	<input type="hidden" id="companyId" name="companyId" value="${pnrSourceStandardConfig.companyId}" />
	<input type="hidden" id="companyMagId" name="companyMagId" value="${pnrSourceStandardConfig.companyMagId}" />
	<input type="hidden" id="addTimeY" name="addTimeY" value="${pnrSourceStandardConfig.addTimeY}" />
	<input type="hidden" id="addTimeM" name="addTimeM" value="${pnrSourceStandardConfig.addTimeM}" />
	<input type="hidden" id="addTimeD" name="addTimeD" value="${pnrSourceStandardConfig.addTimeD}" />
	<input type="hidden" id="addUser" name="addUser" value="${pnrSourceStandardConfig.addUser}" />
	<input type="hidden" id="isdeleted" name="isdeleted" value="${pnrSourceStandardConfig.isdeleted}" />
	<input type="hidden" id="remark" name="remark" value="${pnrSourceStandardConfig.remark}" />
	<input type="hidden" id="saveTime" name="saveTime" value="${pnrSourceStandardConfig.saveTime}" />
	<!-- 隐藏域 end -->
	<table id="sheet" class="formTable">
					<tr>
						<td class="label">
						 	区域<span class="redStar">*</span>
						</td>
						<td class="content">
							<input type="text" name="areaName" id="areaName" value="${pnrSourceStandardConfig.areaName}"   class="text medium" alt="allowBlank:false" readonly="readonly"/>
							<input type="hidden" name="areaId" id="areaId" value="${pnrSourceStandardConfig.areaId}"  class="text medium"/>
							<input type="button"   name="treeBtn_10" id="treeBtn_10" value="请选择区域" class="btn" />
							<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
							rootText='所属区域' valueField="areaId" handler="treeBtn_10" textField="areaName"	checktype="" single="true">
							</eoms:xbox>
						</td>
						<td class="label">
						 	代维公司<span class="redStar">*</span>
						</td>
						<td class="content">
							<input type="text" name="companyName" class="text medium"  readonly="readonly" value="${pnrSourceStandardConfig.companyName}"   id="companyName" alt="allowBlank:false"/>
							<input type="hidden" name="companyId" id="companyId"  maxLength="32" value="${pnrSourceStandardConfig.companyId}"	class="text medium" />
							<input type="button"  name="treeBtn_9" id="treeBtn_9" value="请选择代维公司" class="btn" />
							<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf" rootId=""
							rootText='代维公司组织' valueField="companyId" handler="treeBtn_9" textField="companyName" checktype="dept" single="true">
							</eoms:xbox>
						</td>
					</tr>
					<tr>
						<td class="label">
						 	巡检专业<span class="redStar">*</span>
						</td>
						<td class="content">
								<eoms:comboBox id="professional" name="professional" initDicId="11225" defaultValue="${pnrSourceStandardConfig.professional}" styleClass="input select"  alt="allowBlank:false" />
						</td>
						<td class="label">
						 	资源类型<span class="redStar">*</span>
						</td>
						<td class="content">
								<eoms:comboBox id="sourceType" name="sourceType" initDicId="12412" defaultValue="${pnrSourceStandardConfig.sourceType}" styleClass="input select"  alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	维护资源数量<span class="redStar">*</span>
						</td>
						<td class="content">
								<input class="text" type="text" id="sourceAmount" name="sourceAmount" value="${pnrSourceStandardConfig.sourceAmount}" alt="allowBlank:false" />
								<!-- <input class="btn" type="button" id="btn" value="获取维护数量" onclick="getSourceAmount();"/>
								 -->
						</td>
						<td class="label">
						 	资源计量单位<span class="redStar">*</span>
						</td>
						<td class="content">
								<eoms:comboBox id="sourceDw" name="sourceDw" initDicId="12411" defaultValue="${pnrSourceStandardConfig.sourceDw}" styleClass="input select"  alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	配置类型<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="configType" name="configType" initDicId="12402" defaultValue="${pnrSourceStandardConfig.configType}" styleClass="input select"  alt="allowBlank:false" />
						</td>
						<td class="label">
						 	配置标准<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="configDw" name="configDw" value="${pnrSourceStandardConfig.configDw}" alt="allowBlank:false" />
						</td>
					</tr>
					<tr>	
						<td class="label">
						 	标准配置数量<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="standardConfig" name="standardConfig" value="${pnrSourceStandardConfig.standardConfig}" 
								alt="allowBlank:false,vtype:'number',vtext:'请输入整数'" />
						</td>
						<td class="label">
						 	实际配置数量<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" type="text" id="actualConfig" name="actualConfig" value="${pnrSourceStandardConfig.actualConfig}" 
								alt="allowBlank:false,vtype:'number',vtext:'请输入整数'" />
						</td>
					</tr>
					<tr>
						<td class="label">
						 	到位时间<span class="redStar">*</span>
						</td>
						<td class="content" >
								<input class="text" name="configTime" id="configTime" value="${pnrSourceStandardConfig.configTime}"	onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)"
									readonly="readonly" alt="allowBlank:false"/>
						</td>
						<td class="label">
						 	是否审核<span class="redStar">*</span>
						</td>
						<td class="content" >
								<eoms:comboBox id="ischecked" name="ischecked" initDicId="12504" defaultValue="${pnrSourceStandardConfig.ischecked}" styleClass="input select"  alt="allowBlank:false" />
						</td>
					</tr>
					<tr>
						<td class="label"> 
							备注 
						</td>
						<td class="content" colspan="7">
							<textarea name="remark"  class="textarea max" id="remark"> ${pnrSourceStandardConfig.remark}
							</textarea>
						</td>
					</tr>
	</table>
		<input  type="submit" class="btn"  value="保存" />
		<input  type="button" class="btn"  value="重置" onclick="res();"/>
</form>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	Ext.onReady(function(){
	        v1 = new eoms.form.Validation({form:'pnrSourceStandardConfigForm'});
	        v1.custom = function() {
	        		return true;
	        }
	});
	function getSourceAmount(){
	   	 
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>