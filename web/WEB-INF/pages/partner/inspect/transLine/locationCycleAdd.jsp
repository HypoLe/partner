<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	var v = new eoms.form.Validation({form:'arrivedRateForm'});
});


//地区、区县连动
function changeCity(con) {    
	delAllOption("city");//地市选择更新后，重新刷新县区
	var region = document.getElementById("region").value;
	var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
	//var result=getResponseText(url);
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
			res = result.responseText;
			if(res.indexOf("[{")!=0){
						res = "[{"+result.responseText;
			}
			if(res.indexOf("<\/SCRIPT>")>0){
		  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
			}
			var json = eval(res);
			var countyName = "city";
			var arrOptions = json[0].cb.split(",");
			var obj=$(countyName);
			var i=0;
			var j=0;
			for(i=0;i<arrOptions.length;i++){
				var opt=new Option(arrOptions[i+1],arrOptions[i]);
				obj.options[j]=opt;
				j++;
				i++;
			}
			if(con==1){				
				var city = '${gridForm.city}';
				var partnerid = '${gridForm.partnerid}';
				if(city!=''){
					document.getElementById("city").value = city;
				}
				if(partnerid!=''){
					changePartner(1);
				}	
			}							
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
function delAllOption(elementid){
    var ddlObj = document.getElementById(elementid);//获取对象
     for(var i=ddlObj.length-1;i>=0;i--){
         ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
    }
    
}
</script>

<input type="button" class="btn" onclick="javascript:history.back();" value="返回" />
<div align="center">
	<b>线路到点率规则设置</b>
</div><br><br/>

<form action="${app }/partner/res/pnrTransLineAction.do?method=addLocationCycle" method="post" id="arrivedRateForm" name="arrivedRateForm" >
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">地市&nbsp<font color='red'>*</font></td>
			<td class="content">
				<select name="region" id="region" class="select"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
			</td>
			<td class="label">区县&nbsp<font color='red'>*</font></td>
			<td class="content">
				<select name="city" id="city" class="select"
				alt="allowBlank:false,vtext:'请选择所在县区'">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
			</td>
		</tr>
		<tr>
			<td class="label">
			 巡检执行方式<font color='red'>*</font></td>
			</td>
			<td class="content">
				<eoms:comboBox  name="executeType" id="executeType"  defaultValue="" 
		        			initDicId="11232" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
			 自动上报位置信息时间间隔<font color='red'>*</font></td>
			</td>
			<td class="content">
				<input name="reportInterval" id="reportInterval" type="text" alt="re:/^(\d+)?$/,re_vt:'请输入整数',allowBlank:false,vtext:'请输入时间间隔'"/>秒
			</td>
		</tr>
	</table>
		
	<br/>

	<input type="submit" class="btn"  value="保存信息" />
	<input type="reset" class="btn"  value="重置" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>