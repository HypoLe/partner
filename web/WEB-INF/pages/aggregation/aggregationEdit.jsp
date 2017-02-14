<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<script type="text/javascript">
Ext.onReady(function() {
	var v = new eoms.form.Validation( {
		form : 'aggregationForm'
		});
	});
	

	
function returnBack() {
	window.history.back();
}
function windowsClose() {
	window.close();
}
function remove() {
	if (confirm('您是否要删除该信息?')) {
		var url = '${app}/aggregation/aggregation.do?method=delete&id=${aggregationList.id}';
		location.href = url;
		
	}
	
function deletePhoto()
	{    
		 var id = "${partnerUserForm.id}";
		 var url = null;
		 if(id!=null&&id!="")//编辑时
		    url="<%=request.getContextPath()%>/aggregation/aggregation.do?method=delPhoto&id="+id;
		 else {//新建时
		    var accessory = document.getElementById("accessory").value;
		    url="<%=request.getContextPath()%>/aggregation/aggregation.do?method=delPhoto&accessory="+accessory;
		 }
		 var info = getResponseText(url);
         if(info!=null&&info=="delIsSuccess"){
             document.getElementById("accessory").value = "";
             document.getElementById("photo").value = "";
             document.getElementById("imgdiv").style.display = "none";
             if(id!=null&&id!=""){//修改时
                 document.getElementById("upframe").style.display = "block";
             }
             document.getElementById("upframe").style.height = "50pt";
             window.frames['upframe'].document.getElementById('div').style.display = "";
         }
	}
}

function openUserPhotoPage(){
	window.open('${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhoto&picNo=1&formNo=1&picIDImage=imgUser&hdId=photo');;
}
</script>
<form action="aggregation.do?method=add" method="post"
	id="aggregationForm"><!-- hidden area start --> <input
	type="hidden" name="id" value="${aggregationList.id}" /> <input
	type="hidden" name="creatTime" value="${aggregationList.creatTime}" />
<input type="hidden" name="creatUser"
	value="${aggregationList.creatUser}" /> <input
	type="hidden" name="deleted" value="${aggregationList.deleted}" /> <input
	type="hidden" name="remark2" value="${aggregationList.remark2}" /> <input
	type="hidden" name="remark3" value="${aggregationList.remark3}" /> <input
	type="hidden" name="remark4" value="${aggregationList.remark4}" /> <!-- hidden area end -->
*号为必填内容
<table id="stylesheet" class="formTable">
	<caption>
	<div class="header center">资源设置页面</div>
	</caption>
	<br />
	<br />
	<tr>
		<td class="label">模块名称*</td>
		<td class="content" colspan="3"><input class="text" type="text"
			name="moduleName" id="moduleName" alt="allowBlank:false"
			value="${aggregationList.moduleName}" /></td>
	</tr>
	<tr>
		<td class="label">模块URL*</td>
		<td class="content" colspan="3"><textarea class="text max"
			type="text" name="moduleUrl" id="moduleUrl" alt="allowBlank:false" />${aggregationList.moduleUrl}</textarea></td>
	</tr>
	<tr>
		<td class="label">模块介绍*</td>
		<td class="content" colspan="3"><textarea class="text max"
			type="text" name="content" id=""content"" alt="allowBlank:false" />${aggregationList.content}</textarea></td>
	</tr>
    <tr>
	<td valign="middle">图像</td>
				<input name="picName" id ="picName"  type="hidden" value="">
				<input name="photo" id ="photo"  type="hidden" value="${aggregationList.photo}">
				<td  valign="bottom" colspan="3">
				<input name="accessory" id ="accessory"  type="hidden" value="${aggregationList.accessory}">					
		   <c:if test="${aggregationList.photo!=null}"><!-- 修改时 -->
			   <div id="imgdiv">
			      <img id="imgUser" name="imgUser" src="${aggregationList.photo}"  border="0" width="130" height="130"><br>
			      <input type="button" name="selectPicUser" id="selectPicUser" value="选择图片" class="btn" onclick="openUserPhotoPage()"/>
			      <!-- <br>
			      <input type="button" name="delPhoto" id="delPhoto" value="删除" class="btn" onclick="if(confirm('确定删除电子照片吗？'))deletePhoto();">
			   --> </div>
		   </c:if>
		   
		   <c:if test="${aggregationList.photo==null}"><!-- 新建时 -->
			   <div id="imgdiv" style="display:none">
			   <!-- 
			      <input id="thumbnail" type="hidden" name="thumbnail" value=""/>
			    
			      <input type="button" name="delPhoto" id="delPhoto" value="删除" class="btn" onclick="if(confirm('确定删除电子照片吗？'))javascript:deletePhoto();" >
			   --></div>
		      <img id="imgUser" name="imgUser" src="${app }/images/pnr_thumbnail/man.gif" style="border-width: 0px;" ><br>
		      <input type="button" name="changePicUser" id="changePicUser" value="编辑图片" class="btn" onclick="openUserPhotoPage()"/>
		   </c:if>
		</td>
		</tr>
     <tr>
	<td class="label" >备注</td>
		<td class="content"  colspan="3"><input class="text" type="text"
			name="remark" id="remark" alt="allowBlank:true"
			value="${aggregationList.remark}" /></td>
	  </tr>

</table>
<br />
<table>
	<tr>
		<td><input type="submit" class="btn" value="提交"
			onclick="aggregationSubmit();" />&nbsp;</td>
		<td><input type="button" style="margin-right: 5px"
			onclick="returnBack();" value="返回" class="btn" /></td>
	</tr>
</table>
</form>
<script type="text/javascript">
function aggregationSubmit() {
	if (confirm('您是否要提交该信息?')) {
		return true;
	} else {
		return false;
	}
}
 </script>
<%@ include file="/common/footer_eoms.jsp"%>
