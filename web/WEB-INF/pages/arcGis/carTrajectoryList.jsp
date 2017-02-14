<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
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
		var jsonList='<%=jsonList%>';
		jsonList = eval( '(' + jsonList + ')' );
		var pathUrl="${contextPath}";
		var type="siteList";
				if(parent.frames['arcGis-page'].map != null&&jsonList!=null&&jsonList.length!=0) {
					parent.frames['arcGis-page'].userlocus(jsonList,pathUrl,type,true,null);
				}
				
				jq("#trajectorylist tbody tr").bind("mouseover",function(e) {
					jq(this).addClass("trMouseOver");
				});

				jq("#trajectorylist tbody tr").bind("mouseout",function(e) {
					jq(this).removeClass("trMouseOver");
				});
				
				jq("#trajectorylist tbody tr").bind("click",function(e) {
					var planResId = jq(this).find("input:hidden").val();
					parent.addblock();
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
					}else{
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
					var url="${app}/partner/arcGis/arcGis.do?method=userlocusdetail";
					var resjson;
					
					Ext.Ajax.request({  
					       url : url ,
						   method: 'POST',
						   params:{planResId:planResId},
							success: function (result, request) { 
									var resjson = result.responseText;
									var json = eval( '(' + resjson + ')' ); //转换为json对象 
									if(json!=null&&json.length!=0){
										parent.frames['arcGis-page'].addLocusMarker(json,pathUrl,type,false,other,true,true)
									}else{
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
				parent.removeblock();
});
</script>
<input type="button" class="btn" value="返回车辆列表"  onclick="javascript:history.back();" /> 

	<display:table name="trajectorylist" cellspacing="0" cellpadding="0"	id="trajectorylist" pagesize="${pageSize}" class="table" export="false"
				requestURI="${app}/partner/arcGis/arcGis.do?method=trajectorylist" sort="list" partialList="true" size="${resultSize}">
		<display:column  sortable="false" headerClass="sortable" title="序号">${trajectorylist_rowNum}
			<input type="hidden" id="dataId_${trajectorylist_rowNum }" value="${trajectorylist.id}"/>
		</display:column>			
		<display:column  sortable="false" headerClass="sortable" title="经度">${trajectorylist.x}</display:column>	
		<display:column  sortable="false" headerClass="sortable" title="纬度">${trajectorylist.y}</display:column>	
		<display:column property="insertTime" sortable="false" headerClass="sortable" title="轨迹时间" />
	</display:table>
	

	
<br><br>

<%@ include file="/common/footer_eoms.jsp"%>