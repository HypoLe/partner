<%@ page language="java"  pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@page import="com.boco.eoms.deviceManagement.technicData.util.FileAttriubuteLocator"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="${app}/deviceManagement/technicData/uploadprogress.css" />
<script src="${app}/deviceManagement/technicData/prototype.js" language="JavaScript"
			type="text/javascript"></script>

<script type="text/javascript" language="JavaScript">
var updater = null;

function startStatusCheck()
{
  //设置上传按钮为不可用状态，避免多次提交
  $('submitButton').disabled = true;
  //创建周期性发送请求的Ajax对象
  updater = new Ajax.PeriodicalUpdater(
                              'status',
                              '${app}/technicData/technicDataFiles.do?method=status',
                              {asynchronous:true, frequency:1, method: 'get', parameters: 'c=status&t='+new Date(), onFailure: reportError});
}
//出错时处理方法
function reportError(request)
{
  $('submitButton').disabled = false;
  if(null!=updater)
  {
    //停止刷新获取进度
    updater.stop();
  }
  $('status').innerHTML = '<div class="error"><b>Error communicating with server. Please try again.</b></div>';
}
//上传完毕后,取消周期性获取进度状态，将最终的状态显示在客户端
function killUpdate(message)
{
  $('submitButton').disabled = false;
  if(null!=updater)
  {
    //停止刷新获取进度
    updater.stop();
  }
  if(message != '')//如果有错误信息，则显示出来
  {
    $('status').innerHTML = '<div class="error"><b>Error processing results: ' + message + '</b></div>';
  }
  else//如果没有错误信息
  {
    //获取上传文件的完成状态，显示到客户端
    new Ajax.Updater('status',
                   '${app}/technicData/technicDataFiles.do?method=status',
                   {asynchronous:true, method: 'get', parameters: 'c=status',onComplete:reloadFrame, onFailure: reportError});
  }
}
function reloadFrame(){
	$('fileList').src="${app}/technicData/technicDataFiles.do?method=listFiles";
}

function upload() {
	
  if(!fileTypeCheck()){
  	return;
  }
  var name = document.forms[0].file.value;
  if (name == '' || name.length <= 0) {
  	alert('请选择要上传的文件');
  	return false;
  }
  document.forms[0].submit();
  startStatusCheck();
}

function fileTypeCheck() {
	var file = document.getElementById("file").value;
	var reg = "(.pdf|.doc|.docx|.ppt|.pptx|.xls|.xlsx|.rar)$";
	var matchResult = file.match(reg);
	if(matchResult == null) {
		alert("支持以下文件上传，请选择正确的文件格式!\n.pdf,.doc,.docx,.ppt,.pptx,.xls,.xlsx,.rar");
		return false;
	} else {
		return true;
	}
}


function display(){
  var statusDisplay = document.all("statusDisplay");
  if(statusDisplay.checked == true){
    var status = document.getElementById("status");
    status.style.display="block";
  }else{
    var status = document.getElementById("status");
    status.style.display="none";
  }
		
}
</script>
<script type="text/javascript">
Ext.onReady(function(){
	var loading = Ext.get('loading');
	loading.fadeOut({duration:.2,remove:true});
})
</script>

<!-- loading -->
<div id="loading">
<div class="loading-indicator">数据装载中,请稍候...</div>
</div>

<!-- 这个是隐藏的<ifame>作为表单提交后处理的后台目标-->
<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>
<!-- 当表单提交后,调用startStatusCheck()方法-->
<div class="list-title">
	<B>
	请不要上传超过
	<%=FileAttriubuteLocator.getNetDiskAttributes().getMaxFileSize()%>
	MB的文件
	请使用【右键点击目标另存为】的方式下载<br/>
	PDF格式文件上传会自动为文档添加水印
	</B>
</div>
<form enctype="multipart/form-data" name="technicDataFileForm" method="post"
			action="${app}/technicData/technicDataFiles.do?method=upload" 
			target="target_upload">
<input type="hidden" name="fileName" />
<BR>
请选择上传的文件：
<BR>
<input id="file" name="file" type="file" style="width:50%;height:22;border:1px solid #006699" />
<input id="submitButton" type="button" value="上传" class="btn"  onclick="upload();" />
</form>
<BR>
<input type="checkbox" onclick="display();" name="statusDisplay" CHECKED />
是否显示文件上传状态
<!-- 这里显示进度条 -->
<div id="status"></div>
<BR>

<iframe id="fileList" name="fileList" frameborder="0" width="100%" height="100%" 
	src="${app}/technicData/technicDataFiles.do?method=listFiles">
</iframe>
<%@ include file="/common/footer_eoms.jsp"%>