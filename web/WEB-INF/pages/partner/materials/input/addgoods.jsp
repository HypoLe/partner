<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<%@ page contentType="text/html;charset=utf-8" import="java.util.*"%>
<%@ page import="com.boco.eoms.materials.model.BillMaterial"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
  </head>
  
  <body>
   <form action="storeInput.do?method=save" method="post">
		<table class="table">
		<tr>
			<td class="label">操作</td>
			<td class="label">材料编码</td>
			<td class="label">材料名称</td>
			<td class="label">材料规格</td>
			<td class="label">单位</td>
			<td class="label">数量</td>
			<td class="label">单价</td>
			<td class="label">总额</td>
			<td class="label">备注</td>
		</tr>
			
		<tr>
		<%!int i = 0; %>
		<% 
		
		List<BillMaterial> list = (List<BillMaterial>)session.getAttribute("sessionlist");
		if(list!=null){
		for(i = 0; i< list.size(); i ++){
		%>
		<tr>
		<td>
		<%
			String app = pageContext.getAttribute("app").toString();
			
			String url = "<a href='"+app+"/materials/billMaterial.do?flag=1&&method=delete&&billId="+list.get(i).getStoreBillId()+"&&id="+list.get(i).getId()+"'>删除</a>";
			out.print(url);
			String id = "<input type='hidden' name='id_"+i+"' value='"+list.get(i).getId()+"'>";
			out.print(id);
		
		  %>
		</td>
		<td>
		<%
			out.print(list.get(i).getEncode());
		
		  %>
		</td>
		<td>
		<%
			out.print(list.get(i).getMaterialName());
		
		  %>
		</td>
		<td>
		<%
			out.print(list.get(i).getSpecification());
		
		  %>
		</td>
		<td>
		<%
			out.print(list.get(i).getUnit());
		
		  %>
		</td>
		<td>
		<%
			String amount = "<input type='text' name='materialAmount_"+i+"'>";
			out.print(amount);
		
		  %>
		</td>
		<td>
		<%
			String price = "<input type='text' name='materialPrice_"+i+"'>";
			out.print(price);
			
		  %>
		</td>
		<td>
		</td>
		<td>
		<%
			String remark = "<input type='text' name='remark_'"+i+"'>";
			out.print(remark);
		
		  %>
		</td>
		</tr>
		<%
		}
		}
		%>
		

</table>
<%
			String sum =  "<input type='hidden' name='sum' value='"+i+"'>";
			out.print(sum);
			 %>


</form>
  </body>
</html>
