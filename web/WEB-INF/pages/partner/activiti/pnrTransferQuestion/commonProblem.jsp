<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'commonProblem.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    
    <table class="formTable" style="width:100%;" bordercolor="#e8e8e8" border="3">
    	<tr style="background-color:#f0f0f0">
    	<td align="center"><h2>常见问题</h2></td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q1：如何修改密码</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A1：系统登录后在右上角有“你好，***”，点击此字段即可修改密码。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q2：如何查看巡检完成情况和巡检进度</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A2：在系统的巡检管理-巡检统计-按区域统计查看巡检完成情况；在巡检管理-巡检执行-巡检进度查询中查看巡检进度</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q3：手机查看不到传输局工单</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A3：只有施工队才可以在手机上看到和回复工单，如果施工队还看不到工单请查看工单流程是否按照维护中心-传输分局-外包公司-施工队进行派发的。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q4：如何进行资源的增、删、改。</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A4：在系统的巡检管理-巡检计划基础信息管理-巡检资源新增对资源进行添加；在巡检管理-巡检计划基础信息管理-巡检资源维护对资源进行删除和修改操作。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q5：系统是否开发苹果手机软件</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A5：目前没有开发苹果手机的计划，现在软件只适合Android系统手机。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q6：人员的增、删、改</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A6：人员的增、删、改需要由各地市管理员进行操作，管理员在系统的人员管理-自维人员管理（代维人员管理）-人员信息管理中进行操作。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q7：查看巡检站点是否巡检成功</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A7：在系统中的巡检管理-巡检执行-巡检进度查询中并打开此站点所在的计划，在计划中查找此站点，查看此站点的巡检完成情况，如果为“已完成”此站点已巡检成功；如果为“未完成”此站点未巡检或未巡检完成。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q8：忘记账号如何进行查询</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A8：各地市区县管理员账号中人员管理-自维人员管理（代维人员管理）-人员信息管理，在列表中显示本地市或本区县的人员，在“人员id”列中为各人员的登录账号。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q9：手机巡检或手机工单照片上传时出现退出</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A9：在照片上传时需要找个网络稳定的地方上传。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q10：手机工单开始正常使用，一段时间后打不开工单或拍照不成功。</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A10：在手机上的“设置-应用程序-管理应用程序-全部”找到手机工单进入，点击清除数据。返回系统就可以正常使用了。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q11：手机巡检提交照片时显示照片上传失败</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A11：在手机上的“设置-应用程序-管理应用程序-全部”找到手机巡检进入，点击清除数据。返回系统就可以正常使用了。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q12：实用工具获取经纬度获取失败</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A12：手机经纬度获取根据手机自身GPS的强度来定，如果手机本身GPS获取较弱需要离开室内而且需要远离高大建筑，影响经纬度获取的因数很多例如：天气等。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q13：故障工单派给传输分局为什么传输分局看不到工单。</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A13：派给传输分局的工单只能是传输局工单，其他工单不能发给传输局分局。</td>
    	</tr>
    	<tr style="width:100%">
    		<td style="background-color:#e0e0e0">Q14：工单派发错误怎么办</td>
    	</tr>
    	<tr>
    		<td style="height:50;background-color:#f0f0f0">A14：目前需要让发起工单的人在其“由我创建”中对此工单进行抓回。</td>
    	</tr>
    </table>
  </body>
</html>
