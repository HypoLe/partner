<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/lineProject/lineProjectAction.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {window.location.reload();});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}

function jumpToAdd(){
	location.href='<html:rewrite page='/lineProjectAction.do?method=goToAdd'/>';
}


</script>

<script type="text/javascript">
	function openImport(handler){
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
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">	
	<form action="lineProjectAction.do?method=listFind" method="post" id="lineProjectFindForm" name="lineProjectFindForm">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label" >工程名称</td>
					<td colspan="3" class="content">
						<input class="text" type="text" name="projectName"
						id="projectName" alt="allowBlank:true" /> 
					</td>					
				</tr>
			
				<tr>
					<td class="label" >网络类型</td>
					<td class="content">
						<eoms:comboBox name="networkType"
					id="networkType" initDicId="1010104" alt="allowBlank:true" styleClass="select" />
					</td>	
					<td class="label" >工程状态</td>
					<td class="content">
						<select name="status" id="status">
							<option value="">请选择</option>
							<option value="申请中">申请中</option>
							<option value="申请通过">申请通过</option>
							<option value="申请驳回">申请驳回</option>
							<option value="施工中">施工中</option>
							<option value="待验收">待验收</option>
							<option value="合格归档">合格归档</option>
							<option value="不合格归档">不合格归档</option>
						</select>
					</td>				
				</tr>
			
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>

</div>
<content tag="heading">
<c:out value="线路修缮待审列表" />
</content>  <br/><br/>

<!-- Information hints area end-->
<logic:present name="projectBaseInfoList" scope="request">
	<display:table name="projectBaseInfoList" cellspacing="0" cellpadding="0"
		id="projectBaseInfoList" pagesize="${pagesize}"
		class="table projectBaseInfoList" export="true"
		requestURI="lineProjectAction.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="工程名称">
			${projectBaseInfoList.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单类型">
			${projectBaseInfoList.projectType}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="工程地点">
			${projectBaseInfoList.projectLocation}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="网络性质">
			<eoms:id2nameDB id="${projectBaseInfoList.networkType}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="影响系统">
			${projectBaseInfoList.effect}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="迁改长度">
			${projectBaseInfoList.projectLength}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请人">
			<eoms:id2nameDB id="${projectBaseInfoList.applyer}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请时间">
			${projectBaseInfoList.applyTime}
		</display:column>
	
		<display:column sortable="true" headerClass="sortable" title="工程状态">
			${projectBaseInfoList.status}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			paramProperty="id" url="/lineProject/lineProjectAction.do?method=goToDetail"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>
		
		
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>

	



<%@ include file="/common/footer_eoms.jsp"%>
