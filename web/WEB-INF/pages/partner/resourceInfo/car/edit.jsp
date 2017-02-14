<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style>
	.error{
		color:red;
	}
</style>
<script type="text/javascript">
var jq=jQuery.noConflict();
function removeCar(id){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/car.do?method=delete&id="+id;
}
function goBack(){
window.location.href="<%=request.getContextPath()%>/partner/resourceInfo/car.do?method=search";
}
var c1 =false;
var c2=false;
var c5=false;
	/*校验人员是否来自所选公司*/
function checkUser(){
	c5=true;
	var companyName=document.getElementById("company").value;
	var driverId=document.getElementById("driver_txt").value;
	if(companyName!=""&&driverId!=""){
		Ext.Ajax.request({
		 		url:"${app}/partner/resourceInfo/car.do",
		 		params:{method:"dirverValidate",companyName:companyName,driverName:driverId},
		 		success:function(res,opt){
		 		 	var re=Ext.util.JSON.decode(res.responseText).infor;
		 		 	if(re=="same"){
			 				jq("#error3").html("");//清空错误信息
			 				c5 = true;
			 				return true;
			 			}else{
			 				jq("#error3").html("");//先清空错误信息
							jq("#error3").html(" 负责人不在所选代维公司请核对!");
			 				c5= false;
							return false;
						}
				},
				failure:function(){
					c5=false;
					alert("请求校验人员信息失败!");
					return false;
				}
		});
	}
	return c5;
}
function checkCarNumber(){
		c1 = true;
		var reg=/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/;
		var carNumber=jq("#carNumber").val();
		var isCarNumber=carNumber.match(reg);
		var id=jq("#id").val();
		if(isCarNumber==null){
			jq("#error1").html("");//先清空错误信息
			jq("#error1").html("填写的车牌号不合法");
			c1 = false;
			return false;
		}else{
			Ext.Ajax.request({
		 		url:"${app}/partner/resourceInfo/car.do",
		 		params:{method:"onlyValidate",carNumber:carNumber,id:id},
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
					alert("请求校验车牌号失败！");
		 			c1 = false;
					return false;
	 			}
			});
		}
		return c1;
}

function checkCarGPSNumber(){
		c2 = true;
		var id=jq("#id").val();
		var carGPSNumber=jq("#carGPSNumber").val();
		if(carGPSNumber==""){
			jq("#error2").html("");//先清空错误信息
			jq("#error2").html("GPS编号不能为空!");
			c2 = false;
			return false;
		}else{
			Ext.Ajax.request({
		 		url:"${app}/partner/resourceInfo/car.do",
		 		params:{method:"onlyGPSNumberValidate",carGPSNumber:carGPSNumber,id:id},
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
					alert("请求GPS编号失败！");
		 			c2 = false;
					return false;
	 			}
			});
		}
		return c2;
}
Ext.onReady(function(){
	  c1=true;
	  c2=true;
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
			return false;
		}
		if(!c2){
			return false;
		}
		if(!c5){
			return false;
		}
		return true;
	}
});
</script>
<form action="${app}/partner/resourceInfo/car.do?method=update"   id="carForm" method="post"  >
	<table class="formTable">
			<caption>
					<div class="header center">
						修改车辆信息
					</div>
			</caption>
		<tr>
				<td class="label">
					所属区域&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${car.area}',name:'<eoms:id2nameDB id="${car.area}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area_name" id="area_name"    class="text medium" alt="allowBlank:false" readonly="readonly"/>
					<input type="hidden" name="area" id="area"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1"
					rootText='所属区域' valueField="area" handler="area_name" textField="area_name"
					checktype="" data="${boxData}" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;*
				</td>
				<td class="content" >
					<c:set var="boxData">[{id:'${car.maintainCompany}',name:'<eoms:id2nameDB id="${car.maintainCompany}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="company" id="company" class="text max" onblur="checkUser();"  alt="allowBlank:false" />
			  		<input type="hidden" name="maintainCompany" id="maintainCompany"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintainCompany" handler="company" textField="company"
					checktype="dept" single="true" data="${boxData}">
					</eoms:xbox>
				</td>
		</tr>
		<tr>
				<td class="label">
					车牌号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carNumber" class="text medium" 	value="${car.carNumber}" id="carNumber" onblur="checkCarNumber();" />
						<span class="error" id="error1"></span>
				</td>
				<td class="label">
					车辆类型&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carType" class="text medium" 	value="${car.carType}" id="carType" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					车辆品牌&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carBrand" class="text medium" id="carBrand" value="${car.carBrand}" alt="allowBlank:false"/>
				</td>
				<td class="label">
					车辆型号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carModel"class="text medium" id="carModel" value="${car.carModel}" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					燃料种类&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="fuleType" id="fuleType"  initDicId="1230203" alt="allowBlank:false" defaultValue="${car.fuleType}" styleClass="input select">
					</eoms:comboBox> 
				</td>
				<td class="label">
					标准油耗(L/百公里)&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="fuleConStandard" class="text medium" id="fuleConStandard" value="${car.fuleConStandard}"
					alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					产权属性&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="carProperty" id="carProperty"  initDicId="1230201"  styleClass="input select"
					defaultValue="${car.carProperty}" alt="allowBlank:false">
					</eoms:comboBox> 
				</td>
				<td class="label">
					车辆状态&nbsp;*
				</td>
				<td class="content">
					<eoms:comboBox name="carStatus" id="carStatus" 	initDicId="1230202"
					defaultValue="${car.carStatus}"styleClass="input select"alt="allowBlank:false" />
				</td>
		</tr>
		<tr>
				<td class="label">
					经度&nbsp;
				</td>
				<td class="content">
					<input type="text" name="longitude"   id="longitude"   class="text"  value="${car.longitude}" >
				</td>
				<td class="label">
					纬度&nbsp;
				</td>
				<td class="content">
					<input type="text" name="latitude"    id="latitude"    class="text" value="${car.latitude}">
				</td>
		</tr>
		<tr>
				<td class="label">
					负责人（驾驶员）&nbsp;*
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${car.driver}',name:'<eoms:id2nameDB id="${car.driver}" beanId="partnerUserDao"/>',mobile:'${car.driverContact}'}]</c:set>
					<input type="text" onblur="checkUser();" name="driver_txt" class="text medium" id="driver_txt" alt="allowBlank:false" readonly="readonly"/>
					<span class="error" id="error3"></span>
					<input type="hidden" name="driver" id="driver"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/partner/statistically/paternerStaff.do?method=user" rootId="${sessionform.rootPnrDeptId}" 
					rootText='代维公司人员树' valueField="driver,driverContact" handler="driver_txt" textField="driver_txt"
					checktype="user" single="true" data="${boxData}" >
					</eoms:xbox>
				</td>
				<td class="label">
					联系方式（驾驶员手机）&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="driverContact" class="text medium" 	 id="driverContact"
					alt="allowBlank:false,re:/^1[3|4|5|8][0-9]\d{4,8}$/,re_vt:'不是完整的11位手机号或者正确的手机号前七位'" />
				</td>
		</tr>
		<tr>
				<td class="label">
					车载GPS设备编号&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carGPSNumber" class="text medium" 	value="${car.carGPSNumber}"   id="carGPSNumber" onblur="checkCarGPSNumber();" />
					<span class="error" id="error2"></span>
				</td>
				<td class="label">
					车载GPS设备厂家&nbsp;*
				</td>
				<td class="content">
					<input type="text" name="carGPSFactory" class="text medium" 	value="${car.carGPSFactory}" id="carGPSFactory" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					车载GPS移动号码&nbsp;*
				</td>
				<td class="content" colspan="3">
					<input type="text" name="carGPSSimCardNumber" class="text medium" 	value="${car.carGPSSimCardNumber}" id="carGPSSimCardNumber" alt="allowBlank:false"/>
				</td>
		</tr>
		<tr>
				<td class="label">
				 	备注
				</td>
				<td class="content" colspan="3"  >
						<textarea class="textarea max"  name="notes" id="notes" >${car.notes}</textarea>
				</td>
		</tr>
			<input type="hidden" name="id" value="${car.id }" id="id">
			<input type="hidden" name="applyId" value="${car.applyId }" id="applyId">
			<input type="hidden" name="dispatchStatus" value="${car.dispatchStatus }" id="dispatchStatus">
			<input type="hidden" name="locateTime" value="${car.locateTime }" id="locateTime">
	</table>
	<input type="submit" value="保存"> 		
	<input type="button" value="返回" onclick="goBack()">
</form>
<%@ include file="/common/footer_eoms.jsp"%>
