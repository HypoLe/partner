<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%response.setHeader("cache-control","public"); %>
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
		
		jq("#list tbody tr").bind("click",function(e) {
			var id = jq(this).find("input:hidden").val();
			parent.addblock();
			var app="${app}";
			var url="${app}/partner/gis/baiduGis.do?method=gisTrack";
			Ext.Ajax.request({  
			       url : url ,
				   method: 'POST',
				   params:{resCfgId:id},
					success: function (result, request) { 
					resjson = result.responseText;
					var json = eval( '(' + resjson + ')' ); //转换为json对象
					parent.trackMarker(json,app);
					parent.removeblock();
					 },
					 failure: function ( result, request) { 
							 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText);
							 parent.removeblock();
						 } 
				 });
		});
		
});
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
	
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
            
        }
	
//地区、区县连动
function changeCity(con)
		{    
   delAllOption("city");//地市选择更新后，重新刷新县区
var region = document.getElementById("region").value;
var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
//var result=getResponseText(url);
Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("[{")!=0){
 								res = "[{"+result.responseText;
						}
						if(res.indexOf("<\/SCRIPT>")>0){
					  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						
						var json = eval(res);
						
						var countyName = "city";
						var arrOptions = json[0].cb.split(",");
						var obj=$(countyName);
						var i=0;
						var j=0;
						for(i=0;i<arrOptions.length;i++){
							var opt=new Option(arrOptions[i+1],arrOptions[i]);
							obj.options[j]=opt;
							j++;
							i++;
						}
						
						if(con==1){				
							var city = '${gridForm.city}';
							var partnerid = '${gridForm.partnerid}';
							if(city!=''){
								document.getElementById("city").value = city;
							}
							if(partnerid!=''){
								changePartner(1);								

    
                                  }	
						}							
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}
	
</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">关闭查询界面</span>
</div>

<div id="listQueryObject" 
	 style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 
	<html:form action="inspectPlanGisAction.do?method=inspectPlanGisLocusFindList" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">巡检开始日期:</td>
				<td class="content">
	            <input id="planStartTime" name="planStartTime" class="text medium" type="text" readonly="readonly" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'planEndTime\')}'})"/>             
				</td>
				<td class="label">巡检结束日期:</td>
				<td class="content">
	            <input id="planEndTime" name="planEndTime" class="text medium" type="text" readonly="readonly" onclick="WdatePicker({minDate:'#F{$dp.$D(\'planStartTime\')}'})"/>
				</td>
			</tr>
			<tr>		
				<td class="label">地市：</td>
				<td>
						<select name="region" id="region" class="select"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
				</td>
				<td class="label">区县：</td>
				<td>
					<select name="country" id="city" class="select"
					alt="allowBlank:false,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>	
				</td>
			</tr>
			<tr>		
				<td class="label">巡检专业</td>
				<td class="content">
				<eoms:comboBox  name="specialty" id="zhuanye"  defaultValue="" 
	        			initDicId="11225" alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label">代维公司：</td>
				<td>
					<input type="text" class="text" name="provName" id="provName"
						value="" alt="allowBlank:false" readonly="readonly" />
					<input name="executeObject" id="prov" value="" type="hidden" />
					<eoms:xbox id="provTree"
						dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
						rootId="" rootText="公司树" valueField="prov" handler="provName"
						textField="provName" checktype="dept" single="true" />
				</td>
			</tr>
			
	</table>
	<input type="hidden" value="find" name="find">
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</html:form>
	</div>
	<br/>
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanGisAction.do"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
		<%--<display:column title="资源名称" >
			<a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.resCfgId}">${list.resName}</a>
		</display:column>--%>
		<display:column media="html" sortable="false"  headerClass="sortable" title="资源名称">
			${list.resName}
			<input type="hidden" id="dataId_${list_rowNum }" value="${list.id}"/>
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
		<display:column title="巡检轨迹" >
		<a href="${app}/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTrack&&resCfgId=${list.id}&&resName=${list.resName }">巡检轨迹</a>
		</display:column>--%>
		<display:column  title="巡检完成时间" >
		<fmt:formatDate value="${list.inspectTime}" type="date" dateStyle="medium"/>
		</display:column>
			
		<display:column title="处理结果">
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