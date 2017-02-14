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
					url:"${app}/checkpoint/checkpoint.do",
					params:{method:"delete",id:id},
					success:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					},
					failure:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			}
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

	<form action="checkpoint.do?method=list" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">类型:</td>
					<td colspan="3">
						<eoms:comboBox name="typeStringEqual"
							id="typeStringEqual" initDicId="11901" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
			
				<tr>
					<td class="label">
						资源点编码:
					</td>
					<td >
						<input type="text" class="text" name="resourceCodeStringLike"/>
					</td>
					
					<td class="label">
						资源点名称:
					</td>
					<td >
						<input type="text" class="text" name="resourceNameStringLike" />
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/checkpoint/checkpoint.do?method=list'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
<%--
	private String id;
	private String resourceCode;//资源点编码
	private String resourceName;//资源点名称
	private String address;//地址
	private String type;//类型
	private String longitude;//经度
	private String latitude;//纬度
	private String cableSegment;//所属光缆段
	private String cableSystem;//所属光缆系统
	private String checkPointSegmentId;//所属巡检段
	private String importantFocus;//重要关注点
	private String isCheckPoint;//是否为检查点    
--%>
<!-- Information hints area end-->
<logic:present name="checkPointList" scope="request">
	<display:table name="checkPointList" cellspacing="0" cellpadding="0"
		id="checkPointList" pagesize="${pagesize}"
		class="table checkPointList" export="true"
		requestURI="checkpoint.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="资源点编码">
			${checkPointList.resourceCode}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="资源点名称">
			${checkPointList.resourceName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="类型">
			<eoms:id2nameDB id="${checkPointList.type}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="地址">
			${checkPointList.address}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="经度">
			${checkPointList.longitude}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="纬度">
			${checkPointList.latitude}
		</display:column>
		<%-- 
		<display:column sortable="true" headerClass="sortable" title="所属光缆段">
			${checkPointList.cableSegment}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属光缆系统">
			${checkPointList.cableSystem}
		</display:column>
		--%> 
		<display:column sortable="true" headerClass="sortable" title="所属巡检段">
			<eoms:id2nameDB id="${checkPointList.checkPointSegmentId}" beanId="checkSegmentDao" />
		</display:column>
		<%--
		<display:column sortable="true" headerClass="sortable" title="重要关注点">
			${checkPointList.importantFocus}
		</display:column>---%>
		<display:column sortable="true" headerClass="sortable" title="是否为检查点">
			<eoms:id2nameDB id="${checkPointList.isCheckPoint }" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="编辑"
			paramProperty="id" url="/checkpoint/checkpoint.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${checkPointList.id }"
				href="${app }/checkpoint/checkpoint.do?method=goToDetail&id=${checkPointList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="删除"
			 media="html">
			 <a href="javascript:void(0)" onclick="deleteInfo('${checkPointList.id}')">
				<img class="delete" src="${app }/images/icons/icon.gif" />
			</a>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
