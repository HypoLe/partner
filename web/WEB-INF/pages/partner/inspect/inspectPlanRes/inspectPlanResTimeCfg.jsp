<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

function cfg(){
	var startTime = document.getElementById('startTime').value;
	var endTime = document.getElementById('endTime').value;
	if(startTime == ''){
		alert('巡检开始日期不能为空');
		return;
	}
	if(endTime == ''){
		alert('巡检结束日期不能为空');
		return;
	}
	if(startTime>endTime){
		alert('开始日期不能晚于结束日期');
		return;
	}
	Ext.Ajax.request({
		method:"post",
		url: "${app}/partner/inspect/inspectPlan.do?method=cfgInspectPlanResTime",
		params:{
			startTime: startTime,
			endTime: endTime,
			resId: '${resId}'
		},
		success: function(x){
			window.returnValue = true;
	        window.close();
		}
	 });
} 
function cancelCfg(){
	window.close();
}
</script>

<center> 
<div>
<html:form  action="inspectPlan.do?method=cfgInspectPlanResTime" styleId="gridForm" method="post" > 
	<input type="hidden" name="resId" value="${resId }"/>
	<table class="formTable">
		<caption>
			<div class="header center">巡检起止日期设置</div>
		</caption>
		<tr>
			<td class="label">巡检开始日期</td>
			<td class="content">
			<input class="Wdate text" type="text"  name="startTime" id="startTime" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${minConfigDate }',maxDate:'${maxConfigDate }',readOnly:true})" />
			</td>
		</tr>
		<tr>
			<td class="label">巡检结束日期</td>
			<td class="content" >
			<input class="Wdate text" type="text"  name="endTime" id="endTime" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${minConfigDate }',maxDate:'${maxConfigDate }',readOnly:true})" />
			</td>
		</tr>
	</table>
	</center> 
<table>
    <tr>
	    <td>
	    	<input type="button" class="btn"  value="保存" onclick="cfg()" />&nbsp;&nbsp;
	    	<input type="button" class="btn" value="取消" onclick="cancelCfg()"/>
		</td>
	</tr>
</table>	
</html:form>

	
</center> 	

	


</div>

<%@ include file="/common/footer_eoms.jsp"%>