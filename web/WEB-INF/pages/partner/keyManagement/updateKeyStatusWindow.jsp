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
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script  type="text/javascript">
    var jq=jQuery.noConflict();
    var value
    jq(function(){
        var args = new Object();
        args = GetUrlParms();
        value = args['minDate'];//从url中获取参数
        jq("#remandTime").click(function(){
            if(value!=''&&value!='undefined'){
                WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:value});
            }else{
                WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true});
            }
        });
        jq("#tijiao").click(function(){
            var remand = jq('#remand').val();
            var remandTime = jq('#remandTime').val();
            var myArray=new Array();
            myArray[0]= remand;
            myArray[1]= remandTime;

            if(remand==""){
                alert("请填写归还人姓名");
                return ;
            }
            window.returnValue=myArray;
            window.close();

        });
    })

    function GetUrlParms()
    {
        var args=new Object();
        var query=location.search.substring(1);//获取查询串
        var pairs=query.split("&");//在逗号处断开
        for(var    i=0;i<pairs.length;i++)
        {
            var pos=pairs[i].indexOf('=');//查找name=value
            if(pos==-1)   continue;//如果没有找到就跳过
            var argname=pairs[i].substring(0,pos);//提取name
            var value=pairs[i].substring(pos+1);//提取value
            args[argname]=unescape(value);//存为属性
        }
        return args;
    }

</script>
  </head>

  <body>
   <html:form action="keyManagement.do" method="post" styleId="resForm">

   	<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
			    钥匙归还登记
			</div>
		</caption>
			<tr>
				<td class="label">归还人</td>
				<td class="content">
                    <input name="remand" id="remand" type="text"/>
				</td>
			</tr>
        <tr>
            <td class="label">归还日期</td>
            <td class="content">
                <input class="Wdate text" type="text" value=""  name="remandTime" id="remandTime" />
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

      var jq=jQuery.noConflict();
      Date.prototype.Format = function (fmt) { //author: meizz
          var o = {
              "M+": this.getMonth() + 1, //月份
              "d+": this.getDate(), //日
              "h+": this.getHours(), //小时
              "m+": this.getMinutes(), //分
              "s+": this.getSeconds(), //秒
              "q+": Math.floor((this.getMonth() + 3) / 3), //季度
              "S": this.getMilliseconds() //毫秒
          };
          if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
          for (var k in o)
              if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
          return fmt;
      }
      jq("#remandTime").val(new Date().Format("yyyy-MM-dd"));
  </script>
</html>
