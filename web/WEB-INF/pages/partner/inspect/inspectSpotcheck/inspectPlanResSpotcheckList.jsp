<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
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
function backList(){
	window.location='${app}/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanMainList&yearStringEqual=${planMain.year}&monthStringEqual=${planMain.month}';
}

</script>

<center> 
<div>
<form  action="inspectSpotcheckAction.do?method=findInspectPlanResSpotcheckList" id="gridForm" method="post" > 
	<input type="hidden" value="${planId }" name="planId"/>
	<table class="formTable">
		<caption>
			<div class="header center">${planMain.planName }</div>
		</caption>
		<tr>
			<td class="label">巡检任务名称</td>
			<td class="content">
				<input class="text" type="text" name="resName"/>
			</td>
			<td class="label">资源类别</td>
			<td class="content">
				<eoms:comboBox name="resType" defaultValue="" id="resourceTeype" styleClass="select"
					initDicId="${specialty}" alt="allowBlank:false" /> 
			</td>
		</tr>
		<tr>
			<td class="label">地市</td>
			<td class="content" >
				<!-- 地市 -->			
				<select name="city" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
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
				<select name="country" id="country" class="select"
					alt="allowBlank:false,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>
		</tr>
		<tr>
		<td class="label">抽检情况</td>
		<td class="content">
			<select name="inspectState" class="select">
				<option value="-1">全部</option>
				<option value="1">已抽检</option>
				<option value="0">未抽检</option>
			</select>
		</td>
		<td class="label"></td>
		<td class="content">
		</td>
		</tr>
	</table>
</center> 
<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
	    	<input type="button" class="btn" value="返回" onclick="backList();">
		</td>
	</tr>
</table>	
</form>
<br/>
	<html:form action="inspectPlan" method="post" styleId="resCheckForm">
	<c:if test="${resultSize eq '0'}"><font color="red">当前计划没有元任务巡检完成！</font></c:if>
	<c:if test="${resultSize ne '0'}">
		<display:table name="list" cellspacing="0" cellpadding="0"
			id="list" pagesize="${pageSize}" class="table list"
			export="false" 
			requestURI="inspectSpotcheckAction.do?method=findInspectPlanResSpotcheckList"
			sort="list" partialList="true" size="resultSize"
		>
	
		<center>
			<display:column property="resName" title="巡检任务名称" />
			<display:column title="巡检专业">
				<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
			</display:column>
			<display:column title="资源类型">
				<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />	
			</display:column>
			<display:column  sortable="true" headerClass="sortable" title="地市">
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
			</display:column>			
			<display:column  sortable="true" headerClass="sortable" title="区县">
				<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
			</display:column>
			<display:column title="代维小组">
				<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao" />	
			</display:column>
			<display:column title="巡检员">
				<eoms:id2nameDB id="${list.inspectUser}" beanId="tawSystemUserDao" />	
			</display:column>
			<display:column title="是否巡检完成" >
				<c:if test="${list.inspectState == 0}" var="result">
					<font color="red">否</font>
				</c:if>
				<c:if test="${!result}">
					是
				</c:if>
			</display:column>
			<display:column title="抽检" >
				<c:if test="${list.inspectState == 0}">
				</c:if>
				<c:if test="${list.inspectState == 1 && !empty list.spotcheckUser}">
					${list.score}分
				</c:if>
				<c:if test="${list.inspectState == 1 && empty list.spotcheckUser}">
					<a href="${app}/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanItemSpotcheckList&inspectPlanResId=${list.resId}&resType=${list.resType}&planId=${planId}">
					<img src="${app }/images/icons/edit.gif"><a>
				</c:if>
			</display:column>
			<display:column title="详情" >
				<a href="${app}/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanItemSpotcheckList&inspectPlanResId=${list.resId}&resType=${list.resType}&planId=${planId}&detail=yes">
					<img src="${app }/images/icons/table.gif"><a>
			</display:column>
		</display:table>
	</c:if>
	</html:form>
<table>
	</table>
	
</center> 	
</div>

<%@ include file="/common/footer_eoms.jsp"%>