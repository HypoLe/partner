<%@page import="java.util.List"%>
<%@page import="com.boco.eoms.commons.system.area.model.TawSystemArea"%>
<%@page import="java.util.Map"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'cityWeightForm'});
});
var sumWeight = '${sumWeight}';
function total(id){
	var value = document.getElementById(id).value;
	if(value==''){
		value = 0 ;
	}
	sumWeight = fomatFloat(sumWeight) + fomatFloat(value);
	document.getElementById(id+'Lost').innerHTML = '*可分配权重范围：0.0~'+sumWeight;
	document.getElementById(id+'Lost').style.display = '';
}
function sum(id){
	var value = document.getElementById(id).value;
	if(value==''){
		value = 0 ;
	}
	if(value.length>1&&value.indexOf('0')==0&&value.indexOf('.')==-1){
		alert('对不起，数字不可以0开头');
		document.getElementById(id).value = '0';
		document.getElementById(id).focus();
		return;
	}
	if(isNaN(value)){
		document.getElementById(id).value = '0';
		document.getElementById(id).focus();
		alert('权重值请输入数字');
		return;
	}
	if(sumWeight<value){
		alert('您输入的权重不在可分配范围内');
		document.getElementById(id).value = '0';
		document.getElementById(id).focus();
		return;
	}
	sumWeight = fomatFloat(sumWeight) - fomatFloat(value);
	document.getElementById(id+'Lost').style.display = 'none';
}
function fomatFloat(score)
{ 
	return Math.round(score*Math.pow(10, 2))/Math.pow(10, 2); 
}
function sub(score)
{ 
	if(sumWeight!=0){
		alert("对不起，权重总分应为100分！");
		return;
	}
	$("cityWeightForm").submit();
}
</script>
<center>
<html:form action="/changeCityWeights.do?method=save" styleId="cityWeightForm" method="post"> 
<table class="listTable"  style="width:400px;text-align:center;" >
	<caption>
		<div class="header center">地市权重配置</div>
		<div class="header center">权重合计值为100,地市为0的不参与考核</div>
	</caption>
	<thead>
		<tr>
			<td style="background-color:#EDF5FD;text-align:center;">
				地市
			</td>
			<td style="background-color:#EDF5FD;text-align:center;">
				所占权重
			</td>
		</tr>
	</thead>
	<tbody>
	<%
		Map map = (Map)request.getAttribute("map");
	 	List cityList = (List)request.getAttribute("cityList");
	   	for(int i=0;i<cityList.size();i++){
			TawSystemArea tawSystemArea = (TawSystemArea)cityList.get(i);
			String weight = StaticMethod.nullObject2String(map.get(tawSystemArea.getAreaid()+"_weight"),"0.0");
			String id = StaticMethod.nullObject2String(map.get(tawSystemArea.getAreaid()+"_id"),"");
	%>		
		<tr>
			<td style="background-color:#EDF5FD;text-align:center;">
				<%=tawSystemArea.getAreaname() %>
			</td>
			<td>
				<input id="<%=tawSystemArea.getAreaid()%>_weight" name = "<%=tawSystemArea.getAreaid()%>_weight"  value = "<%=weight %>" onblur="sum(this.id);" onfocus="total(this.id);">
				<font color="red"><span id='<%=tawSystemArea.getAreaid()%>_weightLost'  style="display:none;" >*可分配权重范围：0.0~100.0</span></font>
			</td>
		</tr>
		<input type ='hidden' id = "<%=tawSystemArea.getAreaid() %>_id" name ="<%=tawSystemArea.getAreaid() %>_id" value ="<%=id %>"/>
	<%		
		}
	%>

</table>
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="提交"  onclick="sub();"/>
		</td>
	</tr>
</table>
<html:hidden property="templateId" value="${templateId}" />
<html:hidden property="templateTreeId" value="${templateTreeId}" />
<html:hidden property="deleted" value="${deleted}" />
<html:hidden property="createTime" value="${createTime}" />
<html:hidden property="creator" value="${creator}" />

</html:form>
</center>
<%@ include file="/common/footer_eoms.jsp"%>