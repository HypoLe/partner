<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
        v = new eoms.form.Validation({form:'searchForm'});
        
        jq("#statusStringEqual").bind("change",function() {
        	jq("#searchForm").submit();
        });
       	jq("#statusStringEqual option[value='${statusStringEqual}']").attr("selected",true);
        
        
});

function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/consctRouting/consctRouting.do",
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
<div>
已审核列表页
</div>
	
	<form action="consctRouting.do?method=approvaledList" method="post" name="searchForm" id="searchForm">
		<input type="hidden" name="projectNameStringLike"/>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">
						项目名称:
					</td>
					<td >
						<input type="text" class="text" name="projectNameStringLike"/>
					</td>
					
					<td class="label">
						施工位置:
					</td>
					<td >
						<input type="text" class="text" name="locationStringLike" />
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/consctRouting/consctRouting.do?method=list'"/>
						</div>
					</td>
				</tr>
			</table>
	</form>
</div>
<logic:present name="consctRoutingList" scope="request">
	<display:table name="consctRoutingList" cellspacing="0" cellpadding="0"
		id="consctRoutingList" pagesize="${pagesize}"
		class="table consctRoutingList" export="true"
		requestURI="consctRouting.do" sort="list"
		partialList="true" size="${size}">
				<display:column sortable="true" headerClass="sortable" title="项目名称">
			${consctRoutingList.projectName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="填报人">
			
			<eoms:id2nameDB id="${consctRoutingList.creatUser}" beanId="tawSystemUserDao" />
		</display:column>
		<%---
		<display:column sortable="true" headerClass="sortable" title="填报时间">
			${consctRoutingList.creatTime}
		</display:column>--%>
		
		<display:column sortable="true" headerClass="sortable" title="提交审核人">
			<eoms:id2nameDB id="${consctRoutingList.approvalUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="施工路由中继段名称">
			${consctRoutingList.repeaterSection}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="施工路由中继段里程（公里）">
			${consctRoutingList.repeaterSectionMileage}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="施工位置">
			${consctRoutingList.location}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="施工路由中继段风险级别">
			<eoms:id2nameDB id="${consctRoutingList.riskLevel}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="维护级别">
			<eoms:id2nameDB id="${consctRoutingList.maintainLevel}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="宣传措施">
			${consctRoutingList.disseminationMeasures}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="看护措施">
			${consctRoutingList.disseminationMeasures}
		</display:column>
		<display:column sortable="true" headerClass="sortable"  title="效果(是否发生障碍)">
			<eoms:id2nameDB id="${consctRoutingList.result}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
			审核通过
		</display:column>
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${consctRoutingList.id }"
				href="${app }/consctRouting/consctRouting.do?method=goToDetail&id=${consctRoutingList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
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




<%@ include file="/common/footer_eoms.jsp"%>
