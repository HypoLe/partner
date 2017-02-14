<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<style type="text/css">
*{
	font-size:12px;
	margin:0px;
	padding:0px;
}
a {
	text-decoration: none;
	color: #3d7fb3;
}
a:hover {
	text-decoration: underline;
	color: #3d7fb3;
}
</style>
</head>
<body>
<html:form action="/kmExamQuestionss.do?method=uploadPicture" styleId="kmExamQuestionsFileForm" method="post" enctype="multipart/form-data">

<script type="text/javascript">
	
	//var tempTextRange = null;
	function showAddAccessory()
	{
		window.parent.grin();
		var fileDiv = document.getElementById("addFile");
		var num = fileDiv.childNodes.length;
		var file = document.createElement("input");
		file.type = "file";
		file.name = "uploadFile["+num+"].file";
		file.onchange = function(){
			showAccessoryName(this);
		}
		fileDiv.appendChild(file);
	}
	function showAccessoryName(obj)
	{
		var path = obj.value;
		var type = path.substring(path.lastIndexOf(".")+1).toUpperCase();
		if(type!="JPEG"   &&   type!="JPEG"   &&   type!="JPG"   &&   type!="GIF")
		{
			alert("请上传图片类型(JPEG,JPEG,JPG,GIF)的附件");
			obj.parentNode.removeChild(obj);
			return;
		}
		
		var pictureName = ""+Math.ceil(new Date().getTime())+parseInt(Math.random()*100)+"."+type;
		window.parent.document.getElementById("accessory").value += pictureName + ",";
		document.getElementById("pictureName").value += pictureName +",";
		
		var pos = path.lastIndexOf("/");
        if(pos == -1)
            pos = path.lastIndexOf("\\")
        var filename = path.substr(pos +1)
        
		var fileNameDiv = document.createElement("DIV");
		var deleteHander = document.createElement("A");
		deleteHander.href = "javascript:void(0)";
		deleteHander.onclick = function(){deleteAccessory(pictureName,obj,fileNameDiv)};
		deleteHander.innerHTML = "删除图片";
		fileNameDiv.innerHTML = pictureName;
		fileNameDiv.appendChild(deleteHander);
		document.getElementById("showName").appendChild(fileNameDiv);
		
		var myField = document.getElementById('question'); 
		//myField.value = myField.value.replace("[attachimg][/attachimg]","[attachimg]"+pictureName+"[/attachimg]");
		window.parent.tempTextRange.text = "[attachimg]"+pictureName+"[/attachimg]";
		obj.style.display="none";
		
		
	}
	function deleteAccessory(filename,obj,fileNameDiv)
	{	
		if(obj!=null)
			obj.parentNode.removeChild(obj);
		document.getElementById("showName").removeChild(fileNameDiv);
		var str = window.parent.document.getElementById('question').value;
		if(str.indexOf("[attachimg]")!=-1)
		{
			str = str.replace("[attachimg]"+filename+"[/attachimg]","");
			window.parent.document.getElementById("question").value=str;
		}
	}
</script>

	<a href="javascript:void(0)" onclick="showAddAccessory();">添加图片:</a>
	<div id="addFile"></div>
	<div id="showName"></div>
	<input type="hidden" id="pictureName" name="pictureName">
	<c:if test="${pictureName!=null}">
		<script>
			var accessory = "${pictureName}";
			var fileName = accessory.split(",");
			for(var i=0;i<fileName.length-1;i++)
			{
				 var fileNameDiv = document.createElement("DIV");
				 var deleteHander = document.createElement("A");
				 deleteHander.fileName = fileName[i];
				 deleteHander.href = "javascript:void(0)";
				 deleteHander.onclick = function(){
				     var question = window.parent.document.getElementById("question");
				 	 var accessoryObj = window.parent.document.getElementById("accessory");
				 	 question.value = question.value.replace("[attachimg]"+this.fileName+"[/attachimg]","");
				 	 accessoryObj.value = accessoryObj.value.replace(this.fileName+",",""); 
				 	 deleteAccessory(this.fileName,null,this.parentNode);
				 }
				 deleteHander.innerHTML = "删除图片";
				 fileNameDiv.innerHTML = fileName[i];
				 fileNameDiv.appendChild(deleteHander);
				 document.getElementById("showName").appendChild(fileNameDiv);
			}
		</script>
	</c:if>
</html:form>
</body>
</html>
