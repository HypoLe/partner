<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*,com.boco.eoms.base.util.StaticMethod;"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style>
	.error{
		color:red;
	}
</style>
<div align="center"><b>增加车辆信息</div><br><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"	class="x-layout-panel-hd" onclick="openImport();">
			<img src="${app}/images/icons/layout_add.png" align="absmiddle"	style="cursor: pointer" />
			<span id="openQuery" style="cursor: pointer"	>从Excel导入</span>
</div>
<div id="listQueryObject"	style="display:none; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
			<form action="${app}/partner/resourceInfo/car.do?method=importData" method="post" 	enctype="multipart/form-data" id="importForm" name="importForm">
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
										onclick="javascript:location.href='${app}/partner/resourceInfo/car.do?method=download'"/>
							</td>
						</tr>
					</table>
					<input type="button" onclick="saveImport()" value="确定" />
				</fieldset>
			</form>
</div><br/>
<form action="${app}/partner/resourceInfo/car.do?method=save"    id="carForm" method="post"  >
	<table class="formTable">
	<tr>
				<td class="label">
					区域&nbsp;*
				<br></td>
				<td class="content">
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
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
					<input type="text" name="maintainCompany_name" class="text max" id="maintainCompany_name" 
					onblur="checkUser();" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="maintainCompany" id="maintainCompany"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintainCompany" handler="maintainCompany_name" textField="maintainCompany_name"
					checktype="dept" single="true">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					车牌号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carNumber" class="text medium" id="carNumber" onblur="checkCarNumber();" />
					<span class="error" id="error1"></span>
				</td>
				<td class="label">
					车辆类型&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carType"class="text medium" id="carType" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					车辆品牌&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carBrand" class="text medium" id="carBrand" alt="allowBlank:false"/>
				</td>
				<td class="label">
					车辆型号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carModel"class="text medium" id="carModel" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="fuleType" id="fuleType"  initDicId="1230203" alt="allowBlank:false" 
					 defaultValue="123020301" styleClass="input select">
					</eoms:comboBox> 
				</td>
				<td class="label">
					标准油耗(L/百公里)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="fuleConStandard" class="text medium" id="fuleConStandard" 
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					负责人（驾驶员）&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="driver_txt" class="text medium" id="driver_txt" alt="allowBlank:false" 
					readonly="readonly" onblur="checkUser();"><span class="error" id="error3"></span>
					<input type="hidden" name="driver" id="driver"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user" rootId="${sessionform.rootPnrDeptId}"
					rootText='代维公司人员树' valueField="driver,driverContact" handler="driver_txt" textField="driver_txt"
					checktype="user" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					联系方式（驾驶员手机）&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="driverContact" id="driverContact" 	class="text medium" 	
					alt="allowBlank:false,re:/^1[3|4|5|8][0-9]\d{4,8}$/,re_vt:'不是完整的11位手机号或者正确的手机号前七位'" />
				</td>
		</tr>
		<tr>
				<td class="label">
					产权属性&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="carProperty" id="carProperty"  initDicId="1230201" alt="allowBlank:false" 
					 defaultValue="123020102" styleClass="input select">
					</eoms:comboBox> 
				</td>
				<td class="label">
					车辆状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="carStatus" id="carStatus" 	initDicId="1230202"    defaultValue="123020201"
					 styleClass="input select" alt="allowBlank:false" 	/>
				</td>
		</tr>
		<tr>
				<td class="label">
					经度&nbsp;
				</td>
				<td class="content">
					<input type="text" name="longitude"   id="longitude"   class="text" >
				</td>
				<td class="label">
					纬度&nbsp;
				</td>
				<td class="content">
					<input type="text" name="latitude"    id="latitude"    class="text">
				</td>
		</tr>
		<tr>
				<td class="label">
					车载GPS设备编号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carGPSNumber" id="carGPSNumber" class="text medium" onblur="checkCarGPSNumber();"/>
					<span class="error" id="error2"></span>
				</td>
				<td class="label">
					车载GPS设备厂家&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carGPSFactory" id="carGPSFactory" 	class="text medium" 	alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
			<td class="label">
					车载GPS移动号码&nbsp;*
			</td>
			<td class="content" colspan="3">
				<input type="text" name="carGPSSimCardNumber" id="carGPSSimCardNumber" class="text medium" 	alt="allowBlank:false" />
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
		</td>
	</tr>
	</table>
</form>
<script type="text/javascript">
var jq=jQuery.noConflict();
function saveImport() {
	//表单验证
	if(!v2.check()) {
		return;
	}
	new Ext.form.BasicForm('importForm').submit({
		method : 'post',
				url : "${app}/partner/resourceInfo/car.do?method=importData",
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
var c1 = false;
var c2=false;
var c3=false;//检验人员是否符合代维公司的标志
function checkCarNumber(){
		c1 = true;
		var reg=/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
		var carNumber=jq("#carNumber").val();
		var isCarNumber=carNumber.match(reg);
		if(isCarNumber==null){
			jq("#error1").html("");//先清空错误信息
			jq("#error1").html("填写的车牌号不合法");
			c1 = false;
			return false;
		}else{
			Ext.Ajax.request({
		 		url:"${app}/partner/resourceInfo/car.do",
		 		params:{method:"onlyValidate",carNumber:carNumber,id:""},
		 		success:function(res,opt){
		 		 	var re=Ext.util.JSON.decode(res.responseText).infor;
		 			if(re=="only"){
		 				c1 = true;
		 				jq("#error1").html("");//清空错误信息
		 				return true;
		 			}else{
		 				jq("#error1").html("");//先清空错误信息
		 				jq("#error1").html("你输入的车牌号已经存在!");
		 				c1 = false;
						return false;
					}
				},
				failure:function(){
					alert("请求失败！");
		 			c1 = false;
					return false;
	 			}
			});
		}
		return c1;
}

function checkCarGPSNumber(){
		c2 = true;
		var carGPSNumber=jq("#carGPSNumber").val();
		if(carGPSNumber==""){
			jq("#error2").html("");//先清空错误信息
			jq("#error2").html("GPS编号不能为空!");
			c2 = false;
			return false;
		}else{
			Ext.Ajax.request({
		 		url:"${app}/partner/resourceInfo/car.do",
		 		params:{method:"onlyGPSNumberValidate",carGPSNumber:carGPSNumber,id:""},
		 		success:function(res,opt){
		 		 	var re=Ext.util.JSON.decode(res.responseText).infor;
		 			if(re=="only"){
		 				jq("#error2").html("");//清空错误信息
		 				c2 = true;
		 				return true;
		 			}else{
		 				jq("#error2").html("");//先清空错误信息
						jq("#error2").html("你输入的GPS编号已经存在!");
		 				c2 = false;
						return false;
					}
				},
				failure:function(){
					alert("请求失败！");
		 			c2 = false;
					return false;
	 			}
			});
		}
		return c2;
}
	/*校验人员是否来自所选公司*/
function checkUser(){
	var companyName=document.getElementById("maintainCompany_name").value;
	var driverName=document.getElementById("driver_txt").value;
	if(driverName!=""&&companyName!=""){
		Ext.Ajax.request({
		 		url:"${app}/partner/resourceInfo/car.do",
		 		params:{method:"dirverValidate",companyName:companyName,driverName:driverName},
		 		success:function(res,opt){
		 		 	var re=res.responseText;
		 		 	var json = eval( '(' + re + ')' ); 
		 		 	if(json.infor=="same"){
			 				jq("#error3").html("");//清空错误信息
			 				c3 = true;
			 				return true;
			 			}else{
			 				jq("#error3").html("");//先清空错误信息
							jq("#error3").html(" 负责人不在所选代维公司请核对!");
			 				c3= false;
							return false;
						}
				},
				failure:function(){
					c3=false;
					alert("请求失败!");
					return false;
				}
		});
	}
	return c3;
}
Ext.onReady(function() {
	v1 = new eoms.form.Validation({form:'carForm'});
	v1.custom=function(){
		var reg=/^(\d+)(\.\d{1,15})?$/;//验证经纬度是否符合要求;
		var longitude=document.getElementById("longitude").value;//经度;
		var latitude=document.getElementById("latitude").value;//纬度;
		if(longitude!=""){
			if(longitude.match(reg)==null){
				alert("经度必须为数字");
				return false;
			}
		}
		if(latitude!=""){
			if(latitude.match(reg)==null){
				alert("纬度必须为数字");
				return false;
			}
		}
		if(!c1) {//c1=false表示没有通过验证或者没有经过验证，再去验证一次，如果为true表示通过验证
			if(checkCarNumber()){
				c1 = true;
			}else{
				c1 = false;
				return false;
			}
		}
		if(!c2){
			if(checkCarGPSNumber()){
				c2 = true;
			}else{
				c2 = false;
				return false;
			}
		}
		if(!c3){
			if(checkUser()){
				c3 = true;
			}else{
				c3 = false;
				return false;
			}
		}
		return c1&c2&c3;
	}
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>
