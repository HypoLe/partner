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
var type = '${type}';
var i=2 ;
var jq=jQuery.noConflict();
jq(function(){
		var v = new eoms.form.Validation({form:'androidAppForm'});
		jq("#editAppInfo").click(function() {
			jq("#edit").show();
			jq("#info").hide();
			jq("#reset").hide();
			jq("#editTemplate").val("保存");
		});
		jq("#reset").click(function() {
			jq(':input','#templateForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected'); 
		});
			
		if('detail' == type){
			jq("#edit").hide();
		}
		if('new' == type){
			jq("#info").hide();
		}
});

function submitValue(){
	if(vaidateForm()){
		jq('#androidAppForm').submit();
	}
}

function vaidateForm(){
	if(jq('#appName').val() == ''||jq('#appName').val() == null){
		jq('#errorInfo').html("*应用名称不能为空");
		return false;
	}
	if(jq('#packageName').val() == ''||jq('#packageName').val() == null){
		jq('#errorInfo').html("*包名不能为空");
		return false;
	}
	if(jq('#versionName').val() == ''||jq('#versionName').val() == null){
		jq('#errorInfo').html("*版本名称不能为空");
		return false;
	}
	if(jq('#versionCode').val() == ''||jq('#versionCode').val() == null){
		jq('#errorInfo').html("*版本号不能为空");
		return false;
	}
	if(jq('#appFile').val() == ''||jq('#appFile').val() == null){
		jq('#errorInfo').html("*文件不能为空");
		return false;
	}
	return true;
}
function clock(){
		if(i==2){
		var myDate = new Date();
			 alert("您于 "+myDate.toLocaleString()+" 提交了该模版");
		}
		i=i-1;
		document.title="本窗口将在"+i+"秒后自动关闭!"; 
		if(i>0)setTimeout("clock();",1000); 
		else closewin();
} 

</script>

<!-- 
	<div align="center" class="title">
		android应用新增
	</div>
	 -->
<div id="edit">
<form method="post" id="androidAppForm" action="androidAppManagerAction.do?method=editAndroidApp" name="androidAppForm"  enctype="multipart/form-data">
	<table class="formTable" id="sheet">
		<tr>
			<td class="label">
				应用名称*
			</td>
			<td class="content" colspan="1">
				<input type="text"  class="text" id="appName" name="appName" value="${androidAppInfo.appName }"/>
			</td>
		</tr>
		<tr>	
			<td class="label">
				包名*
			</td>
			<td class="content" colspan="1">
			<input type="text" class="text" id="packageName" name="packageName" value="${androidAppInfo.packageName }"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				版本名称*
			</td>
			<td class="content" colspan="1">
			<input type="text" class="text" id="versionName" name="versionName" value="${androidAppInfo.versionName }"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				版本号*
			</td>
			<td class="content" colspan="1">
			<input type="text" class="text" id="versionCode" name="versionCode" value="${androidAppInfo.versionCode}"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				选择文件*
			</td>
			<td class="content" colspan="1">
			<input type="file" class="text" id="appFile" name="appFile" value=""/>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				版本介绍*
			</td>
			<td class="content" colspan="1">
			<textarea  name="introduction" id="introduction" class="textarea max" style="width: 98%">${androidAppInfo.introduction}</textarea>
			</td>
		</tr>
	</table>
	<input class="content" type="hidden" name="id"
					id="id" value="${androidAppInfo.id }" />
	<br/>
	<input type="button" value="保存" class="btn"  onclick="submitValue()"/>
	<input type="reset" value="重置" class="btn" />
	<div id="errorInfo"></div>
</form>
</div>
<div id="info"><table class="formTable">
		<tr>
			<td class="label">
				应用名称*
			</td>
			<td class="content" colspan="3">
				${androidAppInfo.appName }
			</td>
		</tr>
		<tr>
			<td class="label">
				包名*
			</td>
			<td class="content" colspan="3">
						${androidAppInfo.packageName }
						
			</td>
		</tr>
		<tr>
			<td class="label">
				版本名称*
			</td>
			<td class="content" colspan="3">
			${androidAppInfo.versionName }
			</td>
		</tr>
		<tr>
			<td class="label">
				版本号*
			</td>
			<td class="content" colspan="3">
			${androidAppInfo.versionCode}
			</td>
		</tr>
		<tr>
			<td class="label">
				版本介绍*
			</td>
			<td class="content" colspan="3">
			${androidAppInfo.introduction}
			</td>
		</tr>
		<tr>
			<td class="label">
				添加时间*
			</td>
			<td class="content" colspan="3">
			${androidAppInfo.addTime}
			</td>
		</tr>
		<tr>
			<td class="label">
				添加人*
			</td>
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="tawSystemUserDao"
				id="${androidAppInfo.addUser}" />
			</td>
		</tr>
		
		<c:if test="${sessionform.userid eq androidAppInfo.addUser }">
			<tr><td colspan="4"><input type="button" id="editAppInfo" class="btn"  name='submitInput' value="编辑"/></td></tr>
		</c:if>
		
	</table></div>
	
	<script>
	</script>
<%@ include file="/common/footer_eoms.jsp"%>