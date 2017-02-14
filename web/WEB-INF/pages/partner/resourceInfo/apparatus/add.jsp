<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
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
	if(!v2.check()) {
		return;
	}
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
		url : "${app}/partner/resourceInfo/apparatus.do?method=importData",
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


Ext.onReady(function(){
	v1 = new eoms.form.Validation({form:'apparatusForm'});
	v2 = new eoms.form.Validation({form:'importForm'});
	v2.custom = function() {
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
function reward(){
	window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/apparatus.do?method=search";
}
</script>
<div class="header center"><b>增加仪器仪表</div><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>
<div id="listQueryObject"  	style="display: none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="apparatus.do?method=importData" method="post" 
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
								<input id="importFile" type="file" name="importFile"	class="file" alt="allowBlank:false"/>
							</td>
						</tr>
						<tr>
							<td class="label">
								模板下载
							</td>
							<td>
								<input type="button" class="btn" value="下载导入文件模板" 
										onclick="javascript:location.href='${app}/partner/resourceInfo/apparatus.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="${app}/partner/resourceInfo/apparatus.do?method=save"   id="apparatusForm" method="post"  >
	<table class="formTable">
		
		<tr>
				<td class="label">
					区域&nbsp;*
				<br></td>
				<td class="content">
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"	checktype="" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="maintainCompany_name" class="text max"  readonly="readonly"    id="maintainCompany_name" alt="allowBlank:false"/>
					<input type="hidden" name="maintenanceCompany" id="maintenanceCompany"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintenanceCompany" handler="maintainCompany_name" textField="maintainCompany_name"
					checktype="dept" single="true">
					</eoms:xbox>
				</td>
		</tr>
		
		<tr>
				<td class="label">
					所属专业&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox  name="maintenanceMajor" id="maintenanceMajor" 	initDicId="1230101" 	 
					styleClass="input select" sub="apparatusName" alt="allowBlank:false,vtext:'请选择(单选字典)...'" 	/>
				</td>
				<td class="label">
					仪器名称&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox  name="apparatusName" id="apparatusName" alt="allowBlank:false" styleClass="input select" />
				</td>
				
		</tr>
		<tr>
				<td class="label">
					出厂日期&nbsp;*
				</td>
				<td class="content">
					<input class="text"  name="apparatusDateOfProduction"  id="apparatusDateOfProduction" 	onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)"
					readonly="readonly" 	alt="allowBlank:false,vtype:'lessThen',link:'purchasingTime',vtext:'出厂日期必须小于采购日期' "/>
				</td>
				<td class="label">
					采购日期&nbsp;*
				</td>
				<td class="content">
					<input class="text" name="purchasingTime" id="purchasingTime" 	onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)"
					readonly="readonly" alt="allowBlank:false,vtype:'moreThen',link:'apparatusDateOfProduction',vtext:'采购日期必须大于出厂日期' "/>
				</td>
		</tr>
		<tr>
				<td class="label">
					型号&nbsp;*
				</td>
				<td class="content">
					<input  name="apparatusType" id="apparatusType" class="text " alt="allowBlank:false"/>
				</td>
				<td class="label">
					序列号&nbsp;
				</td>
				<td class="content">
					<input  name="apparatusSerialNumber" id="apparatusSerialNumber" 	class="text medium"  alt="allowBlank:true"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					产权属性&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="apparatusProperty" id="apparatusProperty"  initDicId="1230103" alt="allowBlank:false"  styleClass="input select">
					</eoms:comboBox> 
				</td>
				<td class="label">
					资源所属&nbsp;*
				</td>
				<td class="content">
					<input  name="apparatusBelongs" id="apparatusBelongs" class="text medium" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					在用状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="apparatusStatus" id="apparatusStatus" 	initDicId="1230102" styleClass="input select" alt="allowBlank:false" 	/>
				</td>
				<td class="label">
					使用年限(年)&nbsp;*
				</td>
				<td class="content">
					<input  name="apparatusServiceLife" id="apparatusServiceLife" 	class="text medium" 
					alt="allowBlank:false,vtype:'number', vtext:'输入必须为数值'" 	/>            <!--必须为数值  -->
				</td>
		</tr>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
						<textarea class="textarea max"  name="notes" id="notes" ></textarea>
				</td>
		</tr>
	</table>
	<table>
	<tr>
		<td>
		<input type="submit" value="提交"> 		
		<input type="reset" value="重置"> 		
		<%--<input type="button" value="返回" onclick="reward()">
		--%></td>
	</tr>
	</table>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
