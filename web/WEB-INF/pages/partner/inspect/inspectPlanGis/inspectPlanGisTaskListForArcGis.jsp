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
	if(parent.frames['arcGis-page'].map!=null){
		parent.frames['arcGis-page'].mapCenterAndZoom(true,true);
		}
	jq("#list tbody tr").bind("mouseover",function(e) {
			jq(this).addClass("trMouseOver");
		});

		jq("#list tbody tr").bind("mouseout",function(e) {
			jq(this).removeClass("trMouseOver");
		});
		
		jq("#list tbody tr").bind(	"click",function(e) {
			var seldelcar = jq(this).find("input:hidden").val();
			//get other information form the page who clicked
			var otherInformation=[];
					var other=[];
					if(Ext.isIE){
						otherInformation=jq(this)[0].innerText.split(' ');
						if(otherInformation.length!=0){
							for(var i=0;i<otherInformation.length;i++){
								var oi=otherInformation[i];
								if(oi!=""){
									other.push(oi);
								}
							}
						}
						}
						else{
					 otherInformation=jq(this)[0].textContent.trim().split('\n');
							if(otherInformation.length!=0){
								for(var i=0;i<otherInformation.length;i++){
									var oi=otherInformation[i].trim()
									if(oi!=""){
										other.push(oi);
									}
								}
							}
						}
			parent.addblock();
			var pathUrl="${app}";
			var type="selected";
			var app="${app}";
			var url="${app}/partner/arcGis/arcGis.do?method=detial";
			Ext.Ajax.request({  
			       url : url ,
				   method: 'POST',
				   params:{seldelcar:seldelcar},
					success: function (result, request) { 
					resjson = result.responseText;
					var json = eval( '(' + resjson + ')' ); //转换为json对象
					if(json!=null&&json.length!=0){
					var specialty=json[0].specialty;
					if(specialty!="传输线路"){
						parent.frames['arcGis-page'].addMarker(json,pathUrl,type,true,other,false,true);
						
					}
					else {
						//setTimeout(function(){
							//parent.frames['arcGis-page'].addLine(json,pathUrl,type,true,other);
							//},1000);
							parent.frames['arcGis-page'].addTransLine(json,pathUrl,true,false);
					}
					}
					else{
						alert("该资源数据错误！");
						parent.removeblock();
					}
					parent.removeblock();
					 },
					 failure: function ( result, request) { 
						 parent.removeblock();
							 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText);
						 } 
				 });
		});
		
});

function backList(){
	window.location.href= '${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindListForArcGis&city=${city}';
}

</script>
	
	<input type="button" value="返回" onclick="backList();" />
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
		<display:column title="资源专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="tawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="tawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="tawSystemDictTypeDao" />	
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