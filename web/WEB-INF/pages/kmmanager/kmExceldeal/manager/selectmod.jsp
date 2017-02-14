<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>


<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<script language=javascript>

  function validateForm(form){
      var frm = document.forms[0];
      if( frm.model.value == ""){
          alert("请选择“所属模块”！");
          return false;      
      }
      return true;
  }
function download(){
    var frm = document.forms[0];
    if( frm.model.value == ""){
        alert("请选择“所属模块”！");
    }else{
    window.location.href = '<html:rewrite page="/excelfile.do?method=download&mod='+frm.model.value+'"/>';
    }
}  

</script>
</head>

<body>
<center>
<form method="post" >
<table cellSpacing="0" cellPadding="0" width="85%" border="0" style="height: 50px;">
	<tr>
		<td class="table_title" align="center"><b>Excle模板下载</b></TD>
	</tr>
</table>

<!---正文开始------------------------------------------------------------------------------------------>

<table border="1" width="80%" cellspacing="1" class="table_show" >
	<tr class="tr_show">
		<td width="20%" class="clsfth">
        所属模块</td>
    	<td width="80%" class="clsfth1">
						<eoms:dict key="dict-kmmanager" dictId="model" isQuery="false" alt="allowBlank:false,vtext:''"
				defaultId="" selectId="model" beanId="selectXML" />			</td>
  	</tr>	 

</table>

<table border="0" width="80%" cellspacing="0">
	<tr align="right" valign="middle" height="32">
		<td>
			<input type="button" style="height: 25px;"
				onclick="download()" value="下载导入模版" />
		</td>
	</tr>
</table>
</form>
<!---正文结束------------------------------------------------------------------------------------------>
</center>
<%@ include file="/common/footer_eoms.jsp"%>