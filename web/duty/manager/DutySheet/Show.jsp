<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.boco.eoms.duty.model.TawRmDoneTask,com.boco.eoms.duty.model.TawRmUnDoneTask" %>
<script type="text/javascript" src="${app}/duty/js/tableSort.js"></script>
<script type="text/javascript">
	Ext.onReady(function(){
	var tabs = new Ext.TabPanel('tabs');
    var formTab = tabs.addTab('undo', "未处理工单情况");
    var infoTab = tabs.addTab('done', "已处理工单情况");
    formTab.on('activate',function(){
    	//location.href = "/interfaceMonitoringLog.do?method=newInterfaceMonitoring&id=ssssss"
    });
    infoTab.on('activate',function(){
    	//location.href = "/interfaceMonitoringLog.do?method=newInterfaceMonitoring&id=ss"
    });
    tabs.activate('undo');	
});
</script>
<%List list=(List)request.getAttribute("list"); 
List listname=(List)request.getAttribute("listname"); 
%>
<%!
public String appendStr(String str){
	//start
	String temp = str.substring(0,str.lastIndexOf(".")) ;
	String temp1 = temp.substring(temp.lastIndexOf(".")+1,temp.length()) ;
	if(temp1.trim().length()==1){
		temp1="0"+temp1.trim() ;
	}else{
		temp1 = temp1.trim() ;
	}
	temp = temp.substring(0,temp.lastIndexOf("."))+":"+temp1 ;
	temp1 = temp.substring(temp.lastIndexOf(".")+1,temp.length());
	if(temp1.trim().length()==4){
		temp1 = "0"+temp1 ;
	}
	temp = temp.substring(0,temp.lastIndexOf("."))+":"+temp1 ;
	temp = temp.substring(0,temp.lastIndexOf("."))+" "+temp.substring(temp.lastIndexOf(".")+1,temp.length()) ;
	//System.out.println( temp) ;
	//end
	return temp ;
}
%>
<div id="tabs">
<div id="undo">
<table border="0" width="100%" cellspacing="1" cellpadding="1" class="listTable" align=center id="undoList">
<!--  tr><td colspan="4" align="center">未处理工单情况</td></tr>-->
<thead>
<tr class="tr_show">
<td width="20%" align="center" onclick="sort('undoList',0,'char')"><span style="cursor: hand">工单名称</span></td>
<td width="20%" align="center" onclick="sort('undoList',1,'char')"><span style="cursor: hand">工单类型</span></td>
<td width="20%" align="center" onclick="sort('undoList',2,'char')"><span style="cursor: hand">工单号</span></td>
<td width="20%" align="center" onclick="sort('undoList',3,'char')"><span style="cursor: hand">接单时间</span></td>
<td align="center" >处理</td>
</tr>
</thead>
<%
System.out.println(list.size());
for(int i=0;i<list.size();i++)
{
ArrayList list1=(ArrayList)list.get(i);
//String name=listname.get(i).toString();
%>
<%

List list3=(List)list1.get(0);
for(int h=0;h<list3.size();h++)
{
TawRmUnDoneTask undoneTask=(TawRmUnDoneTask)list3.get(h);
String temp = appendStr(undoneTask.getCreatetime()) ;

%>
<tr>
<td align="center"><%=undoneTask.getTitle() %></td>
<td align="center"><%=listname.get(i) %></td>
<td align="center"><%=undoneTask.getSheetid()%></td>
<td align="center"><%=temp%></td>
<td align="center"><a href="${app}<%=undoneTask.getFirsturl()+undoneTask.getSecondurl() %>"  target="_parent">处理</td>
</tr>
<%}
}
%>
</table>
</div>
<br><br>
<div id="done">
<table border="0" width="100%" cellspacing="1" cellpadding="1" class="listTable" align=center id="doneList">
<!--  tr><td colspan="4" align="center">已处理工单情况</td></tr>-->
<thead>
<tr class="tr_show">
<td width="20%" align="center" onclick="sort('doneList',0,'char')"><span style="cursor: hand">工单名称</span></td>
<td width="20%" align="center" onclick="sort('doneList',1,'char')"><span style="cursor: hand">工单类型</span></td>
<td width="20%" align="center" onclick="sort('doneList',2,'char')"><span style="cursor: hand">工单号</span></td>
<td width="20%" align="center" onclick="sort('doneList',3,'char')"><span style="cursor: hand">接单时间</span></td>
<td align="center">已处理</td>
</tr>
</thead>
<%
System.out.println(list.size());
for(int i=0;i<list.size();i++)
{
ArrayList list1=(ArrayList)list.get(i);
//String name=listname.get(i).toString();
%>
<%
List list2=(List)list1.get(1);
for(int j=0;j<list2.size();j++)
{
TawRmDoneTask task=(TawRmDoneTask)list2.get(j);
String temp = appendStr(task.getCreatetime()) ;
%>
<tr>
<td align="center"><%=task.getTitle()%></td>
<td align="center"><%=listname.get(i)%></td>
<td align="center"><%=task.getSheetid()%></td>
<td align="center"><%=temp%></td>
<td align="center">已处理</td>
<!--  td><a href=""  target="_parent">查看</a></td <%=task.getUrl()%>-->
</tr>
<%} 
}
%>
</table>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>