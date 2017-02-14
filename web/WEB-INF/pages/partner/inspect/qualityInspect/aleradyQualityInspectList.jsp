<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
	Ext.onReady(function(){
	    v = new eoms.form.Validation({form:'taskOrderForm'});
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
function goToStartCheck(id,exe){
window.location="${app}/partner/inspect/inspectPlanExecute.do?method=goToInspectPlanMainItemList&planResId="+id+"&detail="+exe;
}

function delAllOption(elementid){
        var ddlObj = document.getElementById(elementid);//获取对象
        for(var i=ddlObj.length-1;i>=0;i--){
             ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
        }
    }

	//地市区县联动
	function changeCity(con){    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
			Ext.Ajax.request({
				url : url,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					var json = eval(res);
					var countyName = "country";
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
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}
	

</script>

<table class="formTable" id="sheet">
   		<caption>
			<div class="header center">
				默认查询的是当前年月已经质检的元任务
			</div>
		</caption>
	</table>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<html:form  action="inspectPlanExecute.do?method=aleradyWiteQualityInspectList" styleId="taskOrderForm" method="post" > 
	<table class="listTable">
		<tr>
			<td class="label">元任务名称</td>
			<td class="content" >
				<input type="text" class="text medium" name="resName" value="${res.resName }"/>
			</td>
			<td class="label">巡检专业</td>
			<td class="content" >
					<eoms:comboBox name="resSpecialty" defaultValue="${res.specialty }" id="specialty"  styleClass="select"
						initDicId="11225" sub="resourceType"/> 
			</td>
		</tr>
		<tr>
			<td class="label">资源类别</td>
			<td class="content">
				<eoms:comboBox name="resType" defaultValue="${res.resType }" id="resourceType" styleClass="select"
					initDicId="${specialty}"  sub="resourceLevel" /> 
			</td>
			<td class="label">资源级别</td>
			<td class="content" >
					<eoms:comboBox name="resLevel" defaultValue="${res.resLevel }" id="resourceLevel"  styleClass="select"
						initDicId="${zhuanye}"  /> 
			</td>
		</tr>
		<tr>
			<td class="label">地市</td>
			<td class="content" >
				<!-- 地市 -->			
				<select name="city" id="city" class="select" onchange="changeCity(0);">
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
			<td class="label">区县</td>
			<td class="content" >
				<!-- 区县 -->			
				<select name="country" id="country" class="select">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>
		</tr>
		<tr>
			<td class="label">
					年度*
				</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" alt="allowBlank:false"
					beanId="selectXML" cls="select" />
				</td>
				<td class="label">
					月份*
				</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" alt="allowBlank:false"
					beanId="selectXML" cls="select" />
				</td>
		</tr>
	</table>
</center> 
<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>
</br>
<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pageSize}" requestURI="inspectPlanExecute.do" 
		partialList="true" size="${resultSize}">
		<display:column property="res_name" title="元任务名称" />
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源专业类别">
			<eoms:id2nameDB id="${list.res_type}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源专业级别">
			<eoms:id2nameDB id="${list.res_level}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column title="代维小组" >
			<eoms:id2nameDB id="${list.execute_object}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column title="周期开始日期" >
			<bean:write name="list" property="cycle_start_time" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="周期结束日期" >
			<bean:write name="list" property="cycle_end_time" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="巡检日期" >
			<bean:write name="list" property="inspect_time" format="yyyy-MM-dd"/>
		</display:column>

		
		<display:column sortable="true" headerClass="sortable" title="质检详情">
		 <a target="blank" href="${app}/partner/inspect/inspectPlanResQc.do?method=getResQualityCheck&id=${list.id }">
		 <img src="${app}/images/icons/search.gif" /></a>
		</display:column >
		
		<display:column sortable="true" headerClass="sortable" title="查看公共巡检项">
		 <a href="javascript:goToStartCheck('${list.id }','detail')"> <img src="${app}/images/icons/search.gif" /></a>
		</display:column>
		
		<c:if test="${pnrInspect2SwitchConfig.openMainSwitch eq true}">
			<display:column  sortable="true" headerClass="sortable" title="查看设备巡检项">
				<a target="blank" href="${app}/partner/inspect/inspectPlanExecute.do?method=goToDeviceInspectList&planResId=${list.id}&detail=detail">
		 			<img src="${app}/images/icons/search.gif" /></a>
			</display:column>
		</c:if>
	</display:table>
</logic:present>
	</br>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
