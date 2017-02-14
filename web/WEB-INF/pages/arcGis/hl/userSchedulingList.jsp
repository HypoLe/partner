<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<link rel="stylesheet" type="text/css" 	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" 	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
	
		
});
function openImport(){
	var handler = document.getElementById("openQuery");
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}

function clearText(){
	jq("#sheet").find("input:text")[0].value="";
	jq("#prov")[0].value="";
	jq("#email")[0].value="";
	jq("#phone")[0].value="";
}

function chooseRes(){
			var res= document.getElementsByName("checkbox11");
        	for (var i = 0; i<res.length; i++){
                if (res[i].checked==true){ 
                
	                var resValue = res[i].value;
	                if(eoms.isIE){
						window.dialogArguments.setRes(resValue);
					}else{
						window.opener.setRes(resValue);
					}
					alert('选择站点成功');
					window.close();
                }
        	}  
		}
</script>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/arcGis/arcGis.do?method=getDistanceForUser" method="post"  >
<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<table class="listTable" id="sheet">
	<tr>
		<td class="label">
			人员姓名
		</td>
		<td class="content">
			<input type="text" name="nameSearch" id="name"	class="text medium"	alt="allowBlank:false,vtext:''"  value="${nameSearch}"/>
		</td>
		<td class="label">
			所属公司
		</td>
		<td class="content">	
			<eoms:pnrComp name="prov" id="prov" styleClass="input select" defaultValue="${prov}"/>
		</td>
	</tr>
	<tr>	
		<td class="label">
			email
		</td>
		<td class="content">
			<input type="text" name="emailSearch" id="email"	class="text medium"	alt="allowBlank:true,vtext:''"  value="${emailSearch}" />
		</td>
		
		<td class="label">
			联系电话
		</td>
		<td class="content">
			<input type="text" name="phoneSearch" id="phone" 	class="text medium" alt="allowBlank:true,vtext:''"   value="${phoneSearch}"/>
		</td>
	</tr>
</table>
<table >
	<tr>
		<td>
			<input type="submit" class="btn" value="查询" />
			<input type="button"" class="btn" value="重置" onclick="clearText();">
			 <input type="hidden" value="tomgr" name = "operationType"/>
		</td>
	</tr>
</table>
</form>	
</div>
	<display:table name="returnList" cellspacing="0" cellpadding="0"
		id="returnList"  class="table returnList"
		export="false"
		sort="list" partialList="true" size="resultSize">
		<display:column  title="选择" >
        	<input type="radio" name="checkbox11" id="checkbox11"  value="${returnList.deptId}"/>
    	</display:column>
    	<display:column  title="姓名" >
        	${returnList.name}
    	</display:column>
    	<display:column  title="手机号" >
        	${returnList.phone}
    	</display:column>
    	<display:column  title="相距" >
        	${returnList.length}
    	</display:column>
    	<display:column  title="最后经纬度更新时间" >
        	${returnList.updatetime}
    	</display:column>
	
	</display:table>
	
	</br>
	<input class="button" type="button" onclick="chooseRes()" value="选择站点"/>
<%@ include file="/common/footer_eoms.jsp"%>
