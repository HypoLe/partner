<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<%
String jsonList=request.getAttribute("jsonString").toString();
%>
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
		var jsonList='<%=jsonList%>';
		jsonList = eval( '(' + jsonList + ')' );
		var pathUrl="${app}";
		var type="siteList";
				if(parent.frames['arcGis-page'].map != null&&jsonList!=null&&jsonList.length!=0) {
					parent.frames['arcGis-page'].allResources(jsonList,pathUrl,type,true,null);
				}
				
				
				jq("#list tbody tr").bind("mouseover",function(e) {
					jq(this).addClass("trMouseOver");
				});

				jq("#list tbody tr").bind("mouseout",function(e) {
					jq(this).removeClass("trMouseOver");
				});
				
				jq("#list tbody tr").bind("click",function(e) {
					var planResId = jq(this).find("input:hidden").val();
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
					var pathUrl="${app}";
					var type="selected";
					var url="${app}/partner/arcGis/arcGis.do?method=getInspectPlanMainDetailForArcGis";
					var resjson;
					Ext.Ajax.request({  
					       url : url ,
						   method: 'POST',
						   params:{planResId:planResId},
							success: function (result, request) { 
									var resjson = result.responseText;
									var json = eval( '(' + resjson + ')' ); //转换为json对象 
									if(json!=null&&json.length!=0){
									var specialty=json[0].specialty;
									if("传输线路"!=specialty){
										parent.frames['arcGis-page'].addMarker(json,pathUrl,type,false,other,true,true)
									}
									else{
										//parent.frames['arcGis-page'].addLine(json,pathUrl,type,true,other)
										parent.frames['arcGis-page'].addTransLine(json,pathUrl,true,false);
										resJson=json;
						var url1="${app}/partner/arcGis/arcGis.do?method=queryLinePointBySeg";
								Ext.Ajax.request({  
					      			 url : url1 ,
						  			 method: 'POST',
						   			params:{planResId:planResId},
									success: function (result, request) { 
									var resjson1 = result.responseText;
									var json1 = eval(resjson1); //转换为json对象 
									if(json1!=null&&json1.length!=0){
									parent.frames['arcGis-page'].addTransLineLocus(json1,pathUrl,false,false);
									} else{
									parent.frames['arcGis-page'].addTransLineLocus(resJson,pathUrl,false,false);
									}
									},
							 failure: function ( result, request) { 
									 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									 parent.removeblock();
								 } 
						 });
										
										
										
									}
									}
									else{
										alert("该资源数据错误！");
										parent.removeblock();
									}
									parent.removeblock();
							 },
							 failure: function ( result, request) { 
									 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									 parent.removeblock();
								 } 
						 });
				});
				parent.removeblock();
});
</script>

 
<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
	<center>
		<display:column title="资源名称" >
			${list.resName}
			<input type="hidden" id="dataId_${list_rowNum }" value="${list.id}"/>
		</display:column>
		<display:column title="巡检专业">
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
		
			
		<display:column title="完成情况">
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
  </br>
<input type="button" class="btn" value="返回"  onclick="javascript:history.back();" /> 
<%@ include file="/common/footer_eoms.jsp"%>