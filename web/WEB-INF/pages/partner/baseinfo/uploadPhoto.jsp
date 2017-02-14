<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
<link rel="stylesheet" type="text/css" media="all" href="${app}/styles/default/theme.css" />
<script>
function checkForm(form,num){ 
    if(document.getElementById("accessoryName").value==null||document.getElementById("accessoryName").value==""){
    	alert("请选择上传文件");
    	return false;
    }
   if(document.getElementById("accessoryName").value.indexOf(".JPG")>0 ||document.getElementById("accessoryName").value.indexOf(".png")>0 ||document.getElementById("accessoryName").value.indexOf(".bmt")>0 ||document.getElementById("accessoryName").value.indexOf(".jif")>0 ||document.getElementById("accessoryName").value.indexOf(".jpg")>0 ){
    	 return true;
   }
    else{
  	    alert("请选择图片格式的文件上传,比如说.JPG,.png,.jif 文件!");
    	return false;
    }     
}
function GoBack()
{
  window.history.back()
}
</script>

<title>Insert title here</title>
</head>

<body style="background-image:none" id="body">
<input type="hidden" name="isSaveFile" id="isSaveFile" value="${isSaveFile}">
<div class="upload-box" id="div">
<html:form action="/partnerUsers.do?method=savaPhoto" method="post"
	styleId="partnerUserForm" enctype="multipart/form-data" onsubmit="return checkForm()">	
	<table border=0 cellspacing="1" cellpadding="1" id="table" >
	   <tr>
		   <td>
		        <span style="color:red">${tooLargeInfo }</span>
		        <span style="color:red">${info }</span>
				<input name="accessoryName" type="file"  class="upload"/>
				<input type="submit" value="上传" class="btn">
			</td>
		</tr>
	</table>
</html:form>
</div>
</body>
<script type="text/javascript">
   var isSaveFile = document.getElementById("isSaveFile");
   if(isSaveFile.value!=""){//附件保存成功后
	   window.parent.document.getElementById("imgUser").src= "${app }${imgSrc }";
	   window.parent.document.getElementById("imgdiv").style.display = "block";
	   window.parent.document.getElementById("photo").value= "${photo }";
	   window.parent.document.getElementById("accessory").value= "${accessory }";
	   document.getElementById("div").style.display = "none";
	   window.parent.document.getElementById("upframe").style.height = "0pt";
   }
</script>
</html>