<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var flag="";
var myjs = jQuery.noConflict();
myjs(function(){
  myjs('input#carrierNum').bind('change',preExp);
  });
 function preExp(event){
 
 var telephone=new RegExp("^(0|[1-9][0-9]*)$");
	 var phone=/^((\d{2,})-)(\d{7,})(-(\d{3,}))?$/;
	 
var carrierNum=myjs("input#carrierNum").val();
var aa=myjs('#fuck');
 aa.removeClass('ui-state-error');
 if(carrierNum.match(telephone)||carrierNum.match(phone)){       
          aa.addClass('ui-state-highlight ui-corner-all');         
           aa.children("div").html("输入正确");
      	   aa.children("span").attr("class","ui-icon ui-icon-info");
      	   flag=true;
      	   return flag;
      }else{       
           aa.addClass('ui-state-error ui-corner-all');
           aa.children("div").html("输入不合法,请输入数字");
           aa.children("span").attr("class","ui-icon ui-icon-alert");
            flag=false;
      	   return flag;
      }
}

Ext.onReady(function(){
            v = new eoms.form.Validation({form:'subform'});
            v.custom = function(){ 
            	return true;
            };
            v1 = new eoms.form.Validation({form:'importForm'});
            //经纬度限制
        myjs("#longitude").bind("change",numberValueCheck);
        myjs("#latitude").bind("change",numberValueCheck);
        
        function numberValueCheck(e) {
        	var value = e.target.value;
        	var regStr = "^(\\d{1,}|\\d{1,}.\\d{1,4})$";
        	var right = value.match(regStr);
        	if(right == null) {
        		alert("请输入正确的数据,支持精确小数点后四位！");
        		e.target.value = "";
        	} else {
        		e.target.value = Number(e.target.value).toFixed(4);
        	}
        }
});
function check(){
	if(document.getElementById("baseStationName").value==""||document.getElementById("baseStationName").value==null){
		alert("请填写基站名称");
		return false;
	}
	if(document.getElementById("stationForm").value==""||document.getElementById("stationForm").value==null){
		alert("请选择基站站型");
		return false;
	}
	if(document.getElementById("maintenanceLevel").value==""||document.getElementById("maintenanceLevel").value==null){
		alert("请选择维护级别");
		return false;
	}
	if(document.getElementById("stationHouseType").value==""||document.getElementById("stationHouseType").value==null){
		alert("请选择机房名称");
		return false;
	}
	if(document.getElementById("manufacturer").value==""||document.getElementById("manufacturer").value==null){
		alert("请填选厂商");
		return false;
	}
	if(document.getElementById("carrierFacility").value==""||document.getElementById("carrierFacility").value==null){
		alert("请选择载波设备");
		return false;
	}
	var tf=preExp(event);
	if(tf==false){
		alert("请输入载波数量,且必需为数字");
		return false;
	}
return true;
}

function openImport(handler){
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
  
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/baseinfo/baseStation.do?method=xlsSave",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
	    //alert(action.result.problemRow);
	    document.getElementById("problemRow").innerHTML=action.result.problemRow;
		},
		failure : function(form, action) {
			document.getElementById("problemRow").innerHTML=action.result.problemRow;
		}
    });
}
	function submitform(){
		if(check()==true){
			document.forms[1].submit();
		}
	}
	
	
	
	
</script>
 <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/baseinfo/baseStation.do?method=xlsSave" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table id="sheet" class="formTable">
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="35%">
							<input type="file" name="accessoryName" class="file"  alt="allowBlank:false"/>
							<input type="button" class="button" name="save" value="下载导入模板" Onclick="window.location.href ='${app}/baseinfo/baseStation.do?method=outPutModel'" >
							
						</td>
						<td width="35%">
							<input type="button" onclick="saveImport()"  value="确定"/><div style="color: red">基站名称相同的不能被保存</div>
						</td>
					</tr>
					<tr>
					<td colspan="17">
						<div id="problemRow"><font color="red">${problemRow }</font></div>
						</td></tr>
				</table>
			</fieldset>
	</form>
</div>
<br/>
<form action="${app}/baseinfo/baseStation.do?method=add" method="post" id="subform">
<tr><td><input type="hidden" value="${baseStation.id }" id="id" name="id"/></td></tr>
	<table id="sheet" class="formTable">
		<tr>
		<c:choose>
		<c:when test="${empty baseStation.id }">
		<caption>
		<div class="header center">新增基站信息</div>
	</caption>	
		</c:when>
		<c:otherwise>
				<caption>
		<div class="header center">修改基站信息</div>
	</caption>	
		</c:otherwise>
		</c:choose>
		</tr>
		<tr>
			<td class="label">
			 基站名称&nbsp;* 
			</td>
			<td class="content">
				<input class="text" type="text" name="baseStationName"
					id="baseStationName" alt="allowBlank:false"  value="${baseStation.baseStationName }"/>
			</td>
			<td class="label">
				站型&nbsp;*
			</td>
			<td class="content" >
			<eoms:comboBox name="stationForm" id="stationForm" initDicId="1121801" defaultValue="${baseStation.stationForm }"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				<!-- <input class="text" type="text" name="stationForm"
				 id="stationForm" alt="allowBlank:false" value="${baseStation.stationForm }"></input> -->
			</td>
		</tr>
	<tr>	
	<td class="label">
			维护级别&nbsp;*
		</td>
		 <td >
		 <eoms:comboBox name="maintenanceLevel" id="maintenanceLevel" initDicId="1121803" defaultValue="${baseStation.maintenanceLevel }"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
  		</td>
			<td class="label">
			 机房类型
			</td>
			<td ><eoms:comboBox name="stationHouseType" id="stationHouseType" initDicId="1121802" defaultValue="${baseStation.stationHouseType }"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				<!-- <input type="text" class="text" name="stationHouseType"
					id="stationHouseType" alt="allowBlank:false" value="${baseStation.stationHouseType}"/> -->
			</td>
	</tr>
		<tr>
			<td class="label">
				设备厂家
			</td>
			<td >
				<input type="text" class="text" name="manufacturer"
					id="manufacturer" alt="allowBlank:true" value="${baseStation.stationHouseType}"/>
			</td>
			<td class="label">
			载波设备
			</td>
			<td>
				<input type="text" class="text" name="carrierFacility"
					id="carrierFacility" alt="allowBlank:true" value="${baseStation.carrierFacility }"/>
			</td>
			</tr>
		<tr>
		<tr>
			<td class="label">
			 经度* 
			</td>
			<td class="content">
				<input class="text" type="text" name="longitude"
					id="longitude" alt="allowBlank:false" />
			</td>
			<td class="label">
				纬度*
			</td>
			<td class="content">
				<input class="text" type="text" name="latitude"
					id="latitude" alt="allowBlank:false" />
			</td>
		</tr>
			<tr>
			<td class="label">
			载波数量
			</td>
			<td >
				<input type="text" class="text" name="carrierNum"
					id="carrierNum" alt="allowBlank:true" value="${baseStation.carrierNum }"/>
										<div id="fuck" class="ui-state-highlight ui-corner-all"
						style="width: 150px">
						<span class="ui-icon ui-icon-circle-triangle-e"
							style="float: left; margin-right: .6em;"></span>
						<div>
							仅支持数字
						</div>
					</div>
			</td>
		</tr>
		<tr><td  class="label">备注</td><td class="content" colspan="3">
		<textarea property="remark" styleId="remark"  name="remark" id="remark"
						class="text max" alt="allowBlank:true"
						>${baseStation.remark}</textarea>
						
		</td></tr>
		</table>
		<c:choose>
		<c:when test="${empty baseStation.id }">
		<html:button styleClass="btn" property="method.save" onclick="submitform()"
			styleId="method.save" value="新增" ></html:button>
		</c:when>
		<c:otherwise>
				<html:button styleClass="btn" property="method.save" onclick="submitform()"
			styleId="method.save" value="修改" ></html:button>
		</c:otherwise>
		</c:choose>
		
</form>
<%@ include file="/common/footer_eoms.jsp"%>