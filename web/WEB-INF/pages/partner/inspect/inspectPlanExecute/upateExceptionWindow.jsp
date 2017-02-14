<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'pnrResConfigWindow.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script  type="text/javascript">
    var jq=jQuery.noConflict();
    jq(function(){
        jq("#tijiao").click(function(){
            var exception = jq("#exception").val();

            if(exception==""){
                alert("请填写异常信息");
                return ;
            }
            window.returnValue=exception;
            window.close();

        });
    })


</script>
  </head>

  <body>
   <html:form action="inspectPlanExecute.do" method="post" >

   	<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
			    修改巡检项异常信息
			</div>
		</caption>
			<tr>
				<td class="label">异常信息</td>
				<td class="content">
                    <textarea id="exception" cols=40 rows=7 maxlength="128" ></textarea>

				</td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="button" value="提交" id="tijiao">
				</td>
			</tr>
	</table>
   </html:form>
  </body>
  <script type="text/javascript">
      var obj = window.dialogArguments;
      document.getElementById("exception").value=obj.exception;
  </script>
</html>
