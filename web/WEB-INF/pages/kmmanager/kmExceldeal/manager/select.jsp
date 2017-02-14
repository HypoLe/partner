<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>


<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<script language=javascript>
<!--  
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
//-->
</script>
</head>

<body>
<center>
<table cellSpacing="0" cellPadding="0" width="85%" border="0" style="height: 50px;">
	<tr>
		<td class="table_title" align="center"><b>Excle导入</b></TD>
	</tr>
</table>

<!---正文开始------------------------------------------------------------------------------------------>
<form method="post" action="excelfile.do?method=performImport" enctype="multipart/form-data" onsubmit="return validateForm(this);">
<table border="1" width="80%" cellspacing="1" class="table_show" >
	<tr class="tr_show">
		<td width="20%" class="clsfth">
        所属模块</td>
    <td width="80%" class="clsfth1">
			<eoms:dict key="dict-kmmanager" dictId="modelName" isQuery="false" alt="allowBlank:false,vtext:''"
				defaultId="" selectId="modelName" beanId="selectXML" />				           
		</td>
  	</tr>	
	<tr class="tr_show">
		<td width="20%" class="clsfth">
        Excel文件</td>
    <td width="80%" class="clsfth1">
        <input type="file" name="upfile" size="40" style="height: 20px;" ></td>
	</tr>
</table>

<table border="0" width="80%" cellspacing="0">
	<tr align="right" valign="middle" height="32">
		<td>
		<html:submit styleClass="clsbtn2">导入</html:submit>&nbsp;
		</td>
	</tr>
</table>
<form>
<!---正文结束------------------------------------------------------------------------------------------>
</center>
<%@ include file="/common/footer_eoms.jsp"%>