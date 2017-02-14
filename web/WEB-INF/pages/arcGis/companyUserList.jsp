<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<link rel="stylesheet" type="text/css" 	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" 	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style>
<%
String jsonList=request.getAttribute("jsonString").toString();
%>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
	var pathUrl="${app}";
	if(parent.frames['arcGis-page'].map!=null){
		parent.frames['arcGis-page'].mapCenterAndZoom(true,true);
		}
	parent.removeblock();
	jq("#partnerUserList tbody tr").bind("mouseover",function(e) {
		jq(this).addClass("trMouseOver");
	});

	jq("#partnerUserList tbody tr").bind("mouseout",function(e) {
		jq(this).removeClass("trMouseOver");
	});
	
	jq("#partnerUserList tbody tr").bind("click",function(e) {
		var id = jq(this).find("input:hidden").val();
		parent.addblock();
		var url="${app}/partner/arcGis/arcGis.do?method=detail";
		Ext.Ajax.request({  
		       url : url ,
			   method: 'POST',
			   params:{id:id},
				success: function (result, request) { 
				resjson = result.responseText;
				if(""!=resjson){
					var json = eval( '(' + resjson + ')' ); //转换为json对象
					if(json!=null&&json.length!=0){
						
						parent.frames['arcGis-page'].addUserMarker(json,pathUrl,false,true,true);
					}
					else{
						alert("该资源数据错误！");
					}
				}else{
					alert("该人员无今日GIS数据");
				}
				parent.removeblock();
				 },
				 failure: function ( result, request) { 
					 parent.removeblock();
						 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					 } 
			 });
	});
	var jsonList='<%=jsonList%>';
	jsonList = eval( '(' + jsonList + ')' );
			if(parent.frames['arcGis-page'].map != null&&jsonList.length!=0&&parent.frames['arcGis-page'].map.loaded) {
				for(var j=0;j<jsonList.length;j++){
					var jsonForMap=[];
					jsonForMap.push(jsonList[j]);
					parent.frames['arcGis-page'].addUserMarker(jsonForMap,pathUrl,true,false,false);
				}
			}
			parent.removeblock();
		
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

function changePage(v) {
		location.href = '${app}/partner/arcGis/arcGis.do?method=userList&pagesize='+v;
		addblock();
	}
</script>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
<form action="${app}/partner/arcGis/arcGis.do?method=userList" method="post"  id="partnerUserForm">
<input type="hidden" name="treeNodeId" value="${treeNodeId }">
<input type="hidden" name="pagesize" value="${pageSize}">
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
	<display:table name="partnerUserList" cellspacing="0" cellpadding="0"
		id="partnerUserList" pagesize="${pageSize}" class="table partnerUserList"
		export="false"
		requestURI="${app}/partner/arcGis/arcGis.do?method=userList"
		sort="list" partialList="true" size="resultSize">

	<%--<display:column property="name" sortable="true"      
			headerClass="sortable" title="人员姓名" href="${app}/partner/baseinfo/resumes.do?method=newExpert" paramId="id" paramProperty="id"/>--%>
	<display:column  media="html" sortable="true" headerClass="sortable" title="人员姓名">${partnerUserList.name}
	<input type="hidden" id="dataId_${partnerUserList_rowNum }" value="${partnerUserList.id}"/>
	</display:column>

	<display:column property="areaName" sortable="true"
			headerClass="sortable" title="所属地市"  paramId="id" paramProperty="id"/>
			
	<display:column property="deptName" sortable="true"
			headerClass="sortable" title="所属公司"  paramId="id" paramProperty="id"/>

	<display:column property="mobilePhone" sortable="true"
			headerClass="sortable" title="联系电话"  paramId="id" paramProperty="id"/>
			
	<display:column style="text-align:center;" sortable = "false" title = "今日轨迹">
		  		<a href="${app}/partner/arcGis/arcGis.do?method=getUserTrajectory&userPhone=${partnerUserList.phone}">查看</a>
	</display:column>
	</display:table>
	 <center>
	<div>
		<table>
			<tr>
				<td>
				设置每页显示条数
				</td>
				<td>
					<select id="pagesize" name="pagesize" style="width: 58px" onchange=changePage(this.value) >
											<option value="15" ${pageSize == "15" ? 'selected="selected"':'' } >15</option>
											<option value="40" ${pageSize == "40" ? 'selected="selected"':'' } >40</option>
											<option value="80" ${pageSize == "80" ? 'selected="selected"':'' } >80</option>
											<option value="160" ${pageSize == "160" ? 'selected="selected"':'' } >160</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
</center>
<%@ include file="/common/footer_eoms.jsp"%>
