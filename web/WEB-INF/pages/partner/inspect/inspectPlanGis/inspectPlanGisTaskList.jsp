<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
	parent.changeMap();
	jq("#list tbody tr").bind("mouseover",function(e) {
			jq(this).addClass("trMouseOver");
		});

		jq("#list tbody tr").bind("mouseout",function(e) {
			jq(this).removeClass("trMouseOver");
		});
		
		jq("#list tbody tr").bind(	"click",function(e) {
			var seldelcar = jq(this).find("input:hidden").val();
			parent.addblock();
			var pathUrl="${app}";
			var type="selected";
			var app="${app}";
			var url="${app}/partner/gis/baiduGis.do?method=detial";
			Ext.Ajax.request({  
			       url : url ,
				   method: 'POST',
				   params:{seldelcar:seldelcar},
					success: function (result, request) { 
					resjson = result.responseText;
					var json = eval( '(' + resjson + ')' ); //转换为json对象
					var specialty=json[0].specialty;
					if(specialty!="传输线路"){
						var pInterval = setInterval(function(){
							if(parent.map != null) {
								parent.ifOnload(json,pathUrl,type);
								window.clearInterval(pInterval);
								parent.removeblock();
							}
						},1000);
						
						//setTimeout(function(){
						//	parent.lineMarker(json,app);},1000);
					}
					else {
						setTimeout(function(){
							parent.lineMarker(json,app);},1000);
						parent.removeblock();
							
							
					}
					 },
					 failure: function ( result, request) { 
							 parent.removeblock();
							 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText);
						 } 
				 });
		});
		
});
</script>
	
 	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
		<display:column media="html" sortable="false"  headerClass="sortable" title="资源名称">
			${list.resName}
			<input type="hidden" id="dataId_${list_rowNum }" value="${list.resCfgId}"/>
		</display:column>
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		
		<display:column  title="代维小组" >
		<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		
		<display:column  title="巡检开始时间" >
		<fmt:formatDate value="${list.planStartTime}" type="date" dateStyle="medium"/>
		</display:column>
		
		<display:column title="巡检结束时间" >
		<fmt:formatDate value="${list.planEndTime}" type="date"/>
		</display:column>
		<%--<display:column title="经度" >
		${list.resLongitude}
		</display:column>
		<display:column title="纬度" >
		${list.resLatitude}
		</display:column>
		<display:column title="终点经度" >
		${list.endLongitude}
		</display:column>
		<display:column title="终点纬度" >
		${list.endLatitude}
		</display:column>
		
			
		--%><display:column title="完成情况">
			<c:choose>
				<c:when test="${list.inspectState eq 1}">
					已完成
				</c:when>
				<c:otherwise>
					未完成
				</c:otherwise>
			</c:choose>
		</display:column>	
			
		
	</display:table>
	
	
	
<%@ include file="/common/footer_eoms.jsp"%>