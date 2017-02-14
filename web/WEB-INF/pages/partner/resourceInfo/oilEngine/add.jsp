<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/partner/resourceInfo/oilEngine.do?method=importData",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
 }
function openImport(){
	var handler=document.getElementById("openQuery");
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}
Ext.onReady(function() {
	v0 = new eoms.form.Validation({form:'oilEngineForm'});
	v1 = new eoms.form.Validation({form:'importForm'});
	v1.custom = function() {
		var reg = "(.xls)$";
		var file = jq("#importFile").val();
		var right = file.match(reg);
		if(right == null) {
			alert("请选择Excel文件!");
			return false;
		} else {
			return true;
		}
	}
});	

</script>
<div class="header center"><b>增加油机信息</div><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>
<div id="listQueryObject"
			style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="oilEngine.do?method=importData" method="post" 
				enctype="multipart/form-data" id="importForm" name="importForm">
				<fieldset>
					<legend>
						从Excel导入
					</legend>
					<table class="formTable">
						<tr>
							<td class="label">
								选择Excel文件
							</td>
							<td width="35%">
								<input id="importFile" type="file" name="importFile"	class="file" alt="allowBlank:false" />
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/partner/resourceInfo/oilEngine.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<br><form action="${app}/partner/resourceInfo/oilEngine.do?method=save"    id="oilEngineForm" method="post"  >
	<table class="formTable">
		<tr>
				<td class="label">
					区域&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="area_name" id="area_name"   readonly="readonly" class="text medium" alt="allowBlank:false"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"
					checktype="" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="maintainCompany_name" readonly="readonly" class="text max" id="maintainCompany_name" alt="allowBlank:false"/>
					<input type="hidden" name="maintainCompany" id="maintainCompany"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintainCompany" handler="maintainCompany_name" textField="maintainCompany_name"
					checktype="dept" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					油机编号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="oilEngineNumber" class="text medium" id="oilEngineNumber" alt="allowBlank:false"/>
				</td>
				<td class="label">
					规格型号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="oilEngineModel" id="oilEngineModel" class="text medium" 	alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					生产厂家&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="oilEngineFactory" id="oilEngineFactory" 	class="text medium" 	alt="allowBlank:false" />
				</td>
				<td class="label">
					油机类型&nbsp;*
				</td>
				<td class="content">
						<eoms:comboBox name="oilEngineType" id="oilEngineType" 	initDicId="1230302" 
						defaultValue="123030202"  styleClass="input select" alt="allowBlank:false" 	/>
				</td>
		</tr>
		<tr>
				<td class="label">
					标准油耗(L/小时)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="standardFuelConsumption" id="standardFuelConsumption"  class="text medium" 	
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
				</td>
				<td class="label">
					额定功率(KW)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="powerRating" id="powerRating"  class="text medium" 	
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
					<%--<eoms:comboBox name="powerRating" id="powerRating" 	initDicId="1230301"  styleClass="input select" alt="allowBlank:false" 	/>
				--%>
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;*
				</td>
				<td class="content">
						<eoms:comboBox name="fuleType" id="fuleType" 	initDicId="1230305"  styleClass="input select" defaultValue="123030501" alt="allowBlank:false" 	/>
				</td>
				<td class="label">
					存放地点&nbsp;
				</td>
				<td class="content">
					<input type="text" name="place_name" id="place_name"   readonly="readonly" class="text medium" alt="allowBlank:true"/>
					<input type="hidden" name="place" id="place"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="place" handler="place_name" textField="place_name"
					checktype="" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					油机状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="oilEngineStatus" id="oilEngineStatus" 	initDicId="1230303" styleClass="input select" 
					defaultValue="123030301" alt="allowBlank:false" 	/>
				</td>
				<td class="label">
					产权归属&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="oilEngineProperty" id="oilEngineProperty" 	initDicId="1230304"  
					defaultValue="123030401" styleClass="input select" alt="allowBlank:false" 	/>
				</td>
		</tr>
		<%--<tr>
				<td class="label">
					经度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="longitude"   id="longitude"   class="text" 
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'">
				</td>
				<td class="label">
					纬度&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="latitude"    id="latitude"    class="text"
					 alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'">
				</td>
		</tr>
		--%>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max"  name="notes" id="notes" ></textarea>
			</td>
		</tr>
	</table>
	<table>
	<tr>
		<td>
		<input type="submit" value="提交"> 		
		<input type="reset" value="重置"><%-- 		
		<input type="button" value="返回" onclick="goBack()">
		--%>
		</td>
	</tr>
	</table>
</form>
<script type="text/javascript">
function goBack(){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/oilEngine.do?method=search";
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
