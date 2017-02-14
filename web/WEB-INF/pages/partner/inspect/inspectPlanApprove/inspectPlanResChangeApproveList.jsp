<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '变更内容');
        	tabs.addTab('approve-info', '审批历史');
    		tabs.activate(0);
    		changeCity('2');
	});
	
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
						if(con = 2){
							var country = "${res.country}";
							if(arrOptions[i]==country){
								obj.options[j].selected=true;
							}
						}
						j++;
						i++;
					}
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}
		function resetRes(){
			jq("#resourceTeype").val('');
			jq("#resourceLevel").val('');
			jq("#city").val('');
			changeCity(0);
			jq("#inspectCycle").val('');
		}
</script>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
<form  action="inspectPlanChange.do?method=findInspectPlanResChangeApproveList" id="gridForm" method="post" > 
	<input type="hidden" value="${planChgId }" name="id"/>
	<input type="hidden" value="${planId }" name="planId"/>
	<table class="formTable">
		<caption>
			<div class="header center">${planName }</div>
		</caption>
		<tr>
			<td class="label">资源类别</td>
			<td class="content">
				<eoms:comboBox name="resType" defaultValue="${res.resType}" id="resourceTeype" styleClass="select"
					initDicId="${specialty}" alt="allowBlank:false" sub="resourceLevel" /> 
			</td>
			<td class="label">资源级别</td>
			<td class="content" >
					<eoms:comboBox name="resLevel" defaultValue="${res.resLevel}" id="resourceLevel"  styleClass="select"
						initDicId="${res.resType}" alt="allowBlank:false" /> 
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
						<c:choose>
							<c:when test="${city.areaid eq res.city}">
								<option value="${city.areaid}" selected="selected">
								${city.areaname}
							</option>
							</c:when>
							<c:otherwise>
								<option value="${city.areaid}">
									${city.areaname}
								</option>
							</c:otherwise>
						</c:choose>
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
			<td class="label">周期</td>
			<td class="content" >
				<select name="inspectCycle" class="select" id="inspectCycle">
					<option value="">请选择</option>
					<c:forEach items="${cycleMap}" var="cycleMap" > 
						<option value="${cycleMap.key}" <c:if test="${res.inspectCycle eq cycleMap.key}">selected='selected'</c:if> >${cycleMap.value}</option>
					</c:forEach> 
				</select>
			</td>
			<td class="label"></td> 
			<td class="content" >
			</td>
		</tr>
	</table>
<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
	    	
	    	<input type="button" class="btn" value="重置" onclick="resetRes();" />&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</form>
<br/>
	<html:form action="inspectPlan" method="post" styleId="resCheckForm">
	
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanChange.do?method=findInspectPlanResChangeList"
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
		<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
			<display:column title="资源级别">
				<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />	
			</display:column>
		</c:if>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column title="巡检周期" >
			${cycleMap[list.inspectCycle]}
		</display:column>
		<display:column title="周期开始日期" >
			<bean:write name="list" property="cycleStartTime" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="周期结束日期" >
			<bean:write name="list" property="cycleEndTime" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="巡检开始日期" >
			<bean:write name="list" property="planStartTime" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="巡检结束日期" >
			<bean:write name="list" property="planEndTime" format="yyyy-MM-dd"/>
		</display:column>
		<display:column title="是否本月执行" >
			<c:if test="${empty list.planId}" var="result">
				<font color="red">否</font>
			</c:if>
			<c:if test="${!result}">
				是
			</c:if>
		</display:column>
		<display:column title="详情" >
			<a target="_blank" shape="rect" href="${app}/partner/inspect/inspectPlanChange.do?method=showInspectPlanResChangeDetail&resId=${list.id}&planChgId=${planChgId }">
				<img src="${app }/images/icons/table.gif"><a>
		</display:column>
	</display:table>
	</html:form>
<table>
	</table>
	
</center> 	
</div>

<div id="approve-info" class="tabContent">	 
  	   <logic:present name="approveList" scope="request">
		<display:table name="approveList" cellspacing="0" cellpadding="0"
			id="approveList" class="table approveList" export="false" sort="list">
			<display:column title="审批人">
				<eoms:id2nameDB id="${approveList.approver}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column title="审批操作">
				${approveStatusMap[approveList.approveStatus]}
			</display:column>
			<display:column property="approveIdea" title="审批意见" />
			<display:column title="审批时间" >
				<bean:write name="approveList" property="approveTime" format="yyyy-MM-dd HH:mm:ss"/>
			</display:column>
			<display:column title="计划类型">
				${planTypeMap[approveList.planType]}
			</display:column>
		</display:table>
	</logic:present>
  	
  </div>
  </div>
<%@ include file="/common/footer_eoms.jsp"%>