<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script type="text/javascript">
var jq=jQuery.noConflict();

/*xls导入*/
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

	var text = jq("#importType").val();
	if(text==''){
		alert('请选择资源');
		return;
	}
	var file = jq("#file0").val();
	if(file==''){
		alert('请选需上传的文件');
		return ;
	}
	new Ext.form.BasicForm('importForm').submit({
		timeout:600000,
		method :'post',
		url : "${app}/netresource/bsbt/pnrbsbt.do?method=importBsBtData",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			Ext.Msg.alert('提示信息', action.result.infor);
			jq("#importFile").val("");
			errData(action.result.flagdiv);
		},
		failure : function(form, action) {
			Exe.MessageBox.alert(action.result.infor);
			jq("#importFile").val("");
		}
  });
    
}
function errData(flagdiv){

var flag = flagdiv.split(",");
for(var i=0;i<flag.length;i++){
jq("#errorData"+flag[i]).show();
}
}

function onerrData(sth){
if(sth.value=="1122501"){
jq("#errorData1").hide();
// jq("#errorData2").hide();
}else if(sth.value=="1122505"){
// jq("#errorData4").hide();
jq("#errorData4").hide();
}else if(sth.value=="1122506"){
jq("#errorData5").hide();
}else if(sth.value=="1122510"){
jq("#errorData6").hide();
}else if(sth.value=="1122503"){
jq("#errorData7").hide();
}else if(sth.value=="1122504"){
jq("#errorData8").hide();
}else if(sth.value=="1122509"){ // 添加重点客户机房
jq("#errorData9").hide();
}

}
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/ico_file_excel.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
  </div>
  <div id="listQueryObject" 
	 style="display:block;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 <form action="${app}/netresource/bsbt/pnrbsbt.do?method=importBsBtData"
		method="post" id="importForm" name="importForm"
		enctype="multipart/form-data" onsubmit="return checkForm()">
		<table border=0 cellspacing="1" cellpadding="1" class="listTable">
			<!--附加表以及XML文件基本属性表格：开始-->
			<tr >
				<td  COLSPAN="2" class="label" width="20%">请选择资源<font color="red">*</font></td>
				<td width="40%" style="border-right-style: none; border-right-width: medium">
		        	<select name ="specialty" id="importType" onchange="onerrData(this)">
		        	<option value="1122501">基站</option>
		        	<option value="1122505">接入网</option>
		        	<option value="1122506">WLAN</option>
		        	<option value="1122503">室分</option>
		        	<option value="1122504">铁塔及天馈</option>
		        	<option value="1122510">室外箱体</option>
		        	<option value="1122509">重点客户机房</option><!-- 添加重点客户机房 -->
		        	</select>    
		    	</td>
		    	<td width="40%" style="border-left-style: none; border-left-width: medium">
		    	<a id="errorData1" style="display:none;" href="${app}/errorfile/jizhanjifangziyuan${userid}.xls"><font color="red" size="3">下载"基站"错误信息</font></a>	        			
		    	<!-- <a id="errorData2" style="display:none;" href="${app}/errorfile/jizhanshebei${userid}.xls"><font color="red" size="3">下载"基站机房设备"错误信息</font></a> -->	        			
		    	<a id="errorData4" style="display:none;" href="${app}/errorfile/jiruwangziyuan${userid}.xls"><font color="red" size="3">下载"接入网资源"错误信息</font></a>	        			
		    	<!-- <a id="errorData3" style="display:none;" href="${app}/errorfile/jiruwangshebei${userid}.xls"><font color="red" size="3">下载"接入网设备"错误信息</font></a> -->	        			
		    	<a id="errorData5" style="display:none;" href="${app}/errorfile/wlanziyuan${userid}.xls"><font color="red" size="3">下载"WLAN资源"错误信息</font></a>	        			
		    	<a id="errorData7" style="display:none;" href="${app}/errorfile/shifenziyuan${userid}.xls"><font color="red" size="3">下载"室分资源"错误信息</font></a>	        			
		    	<a id="errorData8" style="display:none;" href="${app}/errorfile/tietatiankui${userid}.xls"><font color="red" size="3">下载"铁塔天馈"错误信息</font></a>	        			
		    	<a id="errorData6" style="display:none;" href="${app}/errorfile/shiwaixiangti${userid}.xls"><font color="red" size="3">下载"室外箱体"错误信息</font></a>	        			
		    	<a id="errorData9" style="display:none;" href="${app}/errorfile/zhongdiankehujifang${userid}.xls"><font color="red" size="3">下载"重点客户机房"错误信息</font></a><!-- 添加重点客户机房 -->        			
		    	</td>
		    	</td>	
		    </tr>    
			<tr >
				<td COLSPAN="2" class="label">
					资源上传<font color="red">*
				</td>
				<td COLSPAN="14">
					<input id="importFile" type="file" name="importFile" class="file" alt="allowBlank:false"  /><font color="red">请选择上传xls格式的文件</font>
				</td>
			</tr>
			<tr>
				<td COLSPAN="17"><input type="button" value="导入" class="submit" onclick="saveImport()">&nbsp;&nbsp;&nbsp; &nbsp;

                    <input type="button" class="button" name="save" value="下载导入模板"
                           onclick="javascript:location.href='${app}/netresource/bsbt/pnrbsbt.do?method=downloadBsbt'" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>

			</tr>
		</table>
	</form>
  </div>
  <br/>