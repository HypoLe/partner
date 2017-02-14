<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%!//时间显示方式转换
public String appendStr(String str){
	String temp="" ;
	if(str.lastIndexOf(".")!=-1){
		temp = str.substring(0,str.lastIndexOf(".")) ;
		String temp1 = temp.substring(temp.lastIndexOf(".")+1,temp.length()) ;
		if(temp1.trim().length()==1){
			temp1="0"+temp1.trim() ;
		}else{
			temp1 = temp1.trim() ;
		}
		if(temp.lastIndexOf(".")!=-1){
			temp = temp.substring(0,temp.lastIndexOf("."))+":"+temp1 ;
			temp1 = temp.substring(temp.lastIndexOf(".")+1,temp.length());
			if(temp1.trim().length()==4){
				temp1 = "0"+temp1 ;
			}
			if(temp.lastIndexOf(".")!=-1){
				temp = temp.substring(0,temp.lastIndexOf("."))+":"+temp1 ;
				temp = temp.substring(0,temp.lastIndexOf("."))+" "+temp.substring(temp.lastIndexOf(".")+1,temp.length()) ;
			}
		}
	}else{
		temp = str ;
	}
	return temp ;
}
%>
<script type="text/javascript" src="${app}/duty/js/tableSort.js"></script>
<script type="text/javascript">
var tabs ;
var diff = '<%=request.getParameter("diff") %>' ;
var aa = '<%=request.getAttribute("aa") %>' ;
Ext.onReady(function(){
	tabs = new Ext.TabPanel('tabs');
    var formTab = tabs.addTab('undo', "未处理工单情况");
    var infoTab = tabs.addTab('done', "已处理工单情况");
	if(diff=='null'){
    	tabs.activate('undo');	
	}else if(diff=='done'){
		tabs.activate('done');	
	}else if(diff=='undo'){
		tabs.activate('undo');	
	}
	
});
    	// location.href = "${app}/duty/dutysheet/showundo.do?workSerial="+workSerial;
    	// location.href = "${app}/duty/dutysheet/showdone.do?workSerial="+workSerial;

</script>
<div id="tabs">
<div id="undo" >
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="formTable">
	<tr>
		<td width="100%" align="right">
			<bean:write name="undoPagerHeader" scope="request" filter="false" />
			<%!String undokey;%>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="1" cellpadding="1" class="listTable" align="center" id="undoList">
<thead>
<tr>
<td width="20%" align="center" onclick="sort('undoList',0,'char')"><span style="cursor: hand">工单名称</span></td>
<td width="20%" align="center" onclick="sort('undoList',1,'char')"><span style="cursor: hand">工单执行人</span></td>
<td width="20%" align="center" onclick="sort('undoList',2,'char')"><span style="cursor: hand">工单类型</span></td>
<td width="20%" align="center" onclick="sort('undoList',3,'char')"><span style="cursor: hand">工单号</span></td>
<td width="20%" align="center" onclick="sort('undoList',4,'char')"><span style="cursor: hand">创建日期</span></td>
</tr>
</thead>

<logic:iterate id="tawRmUnDoneTask" name="undoList"
					type="com.boco.eoms.duty.model.TawRmUnDoneTask">
	<tr>
	<td align="center" ><bean:write name="tawRmUnDoneTask" property="title" scope="page" /></td>
	<td align="center" ><bean:write name="tawRmUnDoneTask" property="userid" scope="page" /></td>
	<td align="center" ><bean:write name="tawRmUnDoneTask" property="type" scope="page" /></td>
	<td align="center" ><bean:write name="tawRmUnDoneTask" property="sheetid" scope="page" /></td>
	<td align="center" ><%=appendStr(tawRmUnDoneTask.getCreatetime()) %></td>
</tr>
</logic:iterate>

</table>
</div>
<br><br>
<div id="done">
<table cellpadding="0" cellspacing="0" border="0" width="100%" class="formTable">
	<tr>
		<td width="100%" align="right">
			<bean:write name="donePagerHeader" scope="request" filter="false" />
			<%!String donekey;%>
		</td>
	</tr>
</table>
<table border="0" width="100%" cellspacing="1" cellpadding="1" class="listTable" align="center" id="doneList">
<!--  caption>已处理工单情况</caption>-->
<thead>
<tr class="tr_show">
<td width="20%" align="center" onclick="sort('doneList',0,'char')"><span style="cursor: hand">工单名称</span></td>
<td width="20%" align="center" onclick="sort('doneList',1,'char')"><span style="cursor: hand">工单执行人</span></td>
<td width="20%" align="center" onclick="sort('doneList',2,'char')"><span style="cursor: hand">工单类型</span></td>
<td width="20%" align="center" onclick="sort('doneList',3,'char')"><span style="cursor: hand">工单号</span></td>
<td width="20%" align="center" onclick="sort('doneList',4,'char')"><span style="cursor: hand">创建日期</span></td>
<!--  td align="center">已处理</td>-->
</tr>
</thead>

<logic:iterate id="tawRmDoneTask" name="doneList"
					type="com.boco.eoms.duty.model.TawRmDoneTask">
<tr>
<td align="center"><bean:write name="tawRmDoneTask" property="title" scope="page" /></td>
<td align="center"><bean:write name="tawRmDoneTask" property="userid" scope="page" /></td>
<td align="center"><bean:write name="tawRmDoneTask" property="type" scope="page" /></td>
<td align="center"><bean:write name="tawRmDoneTask" property="sheetid" scope="page" /></td>
<td align="center"><%=appendStr(tawRmDoneTask.getCreatetime()) %></td>
</tr>
</logic:iterate>

</table>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>