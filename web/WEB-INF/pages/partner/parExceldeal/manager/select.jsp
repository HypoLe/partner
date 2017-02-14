<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>


<style type="text/css">
input.txt{ height:30px;width:250px;readonly:true;disabled:true}
/*表格标题*/
.table_title{
	color:#0C00FF;
	text-align:center;
	height:25;
	padding:0,0,0,0;
	font-weight:bold;
}
/*表格边线*/
.table_show{
	/*background:#719FD6;*/
	background:#7EC0DC;
}
/*一般的内容样式 AddRecord.jsp -->用于包含内容的表格或单元格 例：<td class="clsfth">*/
.clsfth {
	background-color: #C9E6F7;
	font-size: 12px;
	color: #006699;
	text-align: left;
	padding: 0 3 0 3;
	word-break:keep-all;
	word-wrap:normal;
}
/*特殊的内容样式 summary.jsp*/
.clsfth1 {
	background-color: #e5edf8;
	font-size: 12px;
	color: #000000;
	border-top-width: 1;
	border-left-width: 1;
	border-left-style: solid;
	border-left-color:#709fd5;
	border-right-width: 1;
	border-right-style: solid;
	border-right-color:#709fd5;
	border-top-style: solid;
	border-top-width: 1;
	border-top-color:#709fd5;
	padding-left: 6;
	padding-right: 6;
	padding-top: 3;
	padding-bottom: 3;
}

</style>
</head>

<body>
<center>
<table cellSpacing="0" cellPadding="0" width="85%" border="0" style="height: 50px;">
	<tr>
		<td class="table_title" align="center"><b>Excle导入</b></TD>
	</tr>
</table>

<!---正文开始------------------------------------------------------------------------------------------>
<form method="post" action="excelParfile.do?method=performImport" enctype="multipart/form-data" onsubmit="return validateForm(this);">
<table border="1" width="80%" cellspacing="1" class="table_show" >
	<tr class="tr_show">
		<td width="20%" class="clsfth">
        所属模块</td>
    <td width="80%" class="clsfth1">
    		<span id = "selName" style="display:none">
			<eoms:dict key="dict-partner-excel" dictId="modelName" isQuery="false" alt="allowBlank:false,vtext:''"
				defaultId="" selectId="modelName" beanId="selectXML" />	
			</span>
			<span id = "spName" style="	background-image: url(../images/public/button_big.jpg);
	background-attachment: fixed;
	background-repeat: repeat;
	background-position: center center;
	padding: 4px;
	height: 20px;
	width: 90px
			"></span>
			<input type="button"  onclick="download()" value="下载导入模版" />
		</td>
  	</tr>	
	<tr class="tr_show">
		<td width="20%" class="clsfth" >
        Excel文件</td>
    <td width="80%" class="clsfth1">
        <input type="file" name="upfile" size="40" style="height: 20px;" ></td>
	</tr>
</table>

<table border="0" width="80%" cellspacing="0">
	<tr align="right" valign="middle" height="32">
		<td>
		<html:submit styleClass="buttons">导入</html:submit>&nbsp;
		</td>
	</tr>
</table>
<form>
<!---正文结束------------------------------------------------------------------------------------------>
</center>
<script type="text/javascript">
window.onload = function(){
	var frm = document.forms[0];
	frm.modelName.value = "${exc}";
	document.getElementById("spName").innerHTML=frm.modelName[frm.modelName.selectedIndex].innerText;
}
  function validateForm(form){
      var frm = document.forms[0];
      if( frm.modelName.value == ""){
          alert("请选择“所属模块”！");
          return false;      
      }
      if( frm.modelName.value == "jdbc"){
      	  frm.action = "excelfile.do?method=performImportJDBC";
      }
      if( frm.upfile.value == ""){
          alert("请选择“要导入的EXCEL文件”！");
          return false;      
      }else if(frm.upfile.value.indexOf(".xls")<0){
      	  alert("输入的文件格式不对");
      	  return false;
      }

      return true;
  }
  function download(){
	var modstr = "${dow}";
    window.location.href = '<html:rewrite page="/excelParfile.do?method=download&mod='+modstr+'"/>';

}
//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>