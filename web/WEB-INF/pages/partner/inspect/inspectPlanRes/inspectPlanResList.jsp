<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%response.setHeader("cache-control","public"); %>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	Ext.onReady(function() {
		initCountry();
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
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
	}
		
		
		/**	
	* 页面初始化地市下的区县
	*/
	function initCountry(){
		delAllOption("country");
		var city = document.getElementById("city").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
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
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${country}";
					if(arrOptions[i]==country){
						obj.options[j].selected=true;
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
	function goToStartCheck(id,exe){
	window.location="${app}/partner/inspect/inspectPlan.do?method=goToInspectPlanMainItemList&planResId="+id+"&detail="+exe;
	}
	
	function goToDeviceInspect(id,exe){
	window.location="${app}/partner/inspect/inspectPlan.do?method=goToDeviceInspectList&planResId="+id+"&detail="+exe;
	}
</script>

<center> 
<div>
<html:form  action="inspectPlan.do?method=findInspectPlanResList" styleId="gridForm" method="post" > 
	<c:if test="${transLine eq 'yes'}" var="result" >
	 <table class="listTable">
			<tr>
					<td class="label">资源名称<input type="hidden" value="${transLine}" name="transLine"></td>
					<td class="content">
						<html:text property="resName" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${resName}" />
					</td>
					
					<td class="label">区域</td>
					<td class="content">
						<html:text property="tlDis" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${tlDis}" />
					</td>
					<%-- 
					<td class="label">地市</td>
					<td class="content">
					<select name="city" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${city==city.areaid}" var="result">
							<option value="${city.areaid}" selected="selected" >
								${city.areaname}
							</option>
						</c:if>
						<c:if test="${!result}">
							<option value="${city.areaid}" >
								${city.areaname}
							</option>
						</c:if>
					</logic:iterate>
				</select>
					</td>--%>
				</tr>

				<%-- <tr>
				
					<td class="label">区县</td>
					<td class="content">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
								--请选择所在县区--
							</option>				
						</select>
					</td>
					<td class="label"></td>
					<td class="content">
					</td>
				</tr>--%>
				<tr>
					<td class="label">光缆系统</td>
					<td class="content">
						<html:text property="tlWire" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${tlWire}" />
					</td>
					<td class="label">光缆段</td>
					<td class="content">
						<html:text property="tlSeg" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${tlSeg}" />
					</td>
				</tr>
		</table> 
	</c:if>
	<c:if test="${!result}">
		<table class="formTable">
			<tr>
				<td class="label">元任务名称</td>
				<td class="content" >
					<input type="text" class="text medium" name="resName" value="${resName }"/>
				</td>
				<td class="label">巡检专业</td>
				<td class="content" >
					<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
						<eoms:comboBox name="resSpecialty" defaultValue="${specialty}" id="specialty"  styleClass="select"
							initDicId="11225" sub="resourceType" hiddenOption="1122502"/> 
					</c:if>
					<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
						<eoms:comboBox name="resSpecialty" defaultValue="${specialty}" id="specialty"  styleClass="select"
							initDicId="11225" sub="resourceType" />
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="label">资源类别</td>
				<td class="content">
					<eoms:comboBox name="resType" defaultValue="${resType}" id="resourceType" styleClass="select"
						initDicId="${specialty}" alt="allow	Blank:false" sub="resourceLevel" /> 
				</td>
				<td class="label">资源级别</td>
				<td class="content" >
						<eoms:comboBox name="resLevel" defaultValue="${resLevel}" id="resourceLevel"  styleClass="select"
							initDicId="${resType}" alt="allowBlank:false" /> 
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
							<c:if test="${mycity==city.areaid}" var="result">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<c:if test="${!result}">
								<option value="${city.areaid}" >
									${city.areaname}
								</option>
							</c:if>
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
					<select name="inspectCycle" class="select">
						<option value="">请选择</option>
						<c:forEach items="${cycleMap}" var="cycleMap" > 
							<option value="${cycleMap.key}" <c:if test="${cycleMap.key eq inspectCycle}">selected="true"</c:if>>${cycleMap.value}</option>
						</c:forEach> 
					</select>
				</td>
				<td class="label">创建日期</td>
				<td class="content" >
					<input class="text medium" name="resCreateTime" type="text" 
						value="${resCreateTime }" 
						onfocus="WdatePicker({maxDate:'%y-%M-{%d}'})"/>
				</td>
			</tr>
		</table>
	</c:if>
</center> 
<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>
<br/>
	<html:form action="inspectPlan" method="post" styleId="resCheckForm">
	<c:if test="${transLine eq 'yes'}" var="result">
		<display:table name="list" cellspacing="0" cellpadding="0"
			id="list" pagesize="${pageSize}" class="table list"
			export="false" 
			requestURI="inspectPlan.do"
			sort="list" partialList="true" size="resultSize"
		>
	
		<center>
			<display:column title="元任务名称">
				<a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.resCfgId}">${list.resName}</a>
			</display:column>
			
			<display:column  sortable="true"  title="区域"
					headerClass="sortable" >
					${list.tlDis }
			</display:column>
			
			<display:column  sortable="true"  title="光缆系统"
					headerClass="sortable" >
					${list.tlWire }
			</display:column>
			
			<display:column  sortable="true"  title="光缆段"
					headerClass="sortable" >
					${list.tlSeg }
			</display:column>
					
			<display:column  sortable="区县" title="系统级别"
					headerClass="sortable"  >
				${list.tlSystemLevel }
			</display:column>
			
			<display:column  sortable="区县" title="起点名字"
					headerClass="sortable"  >
				${list.tlPAName }
			</display:column>
					
			<display:column  sortable="区县" title="起点经度"
					headerClass="sortable"  >
				${list.tlPALo }
			</display:column>
					
			<display:column  sortable="" title="起点点纬度"
					headerClass="tlPALa"  >
				${list.tlPALa }
			</display:column>
					
			<display:column  sortable="" title="终点名字"
					headerClass="sortable"  >
				${list.tlPZName }
			</display:column>
			
			<display:column  sortable="" title="终点经度"
					headerClass="sortable"  >
				${list.tlPZLo }
			</display:column>
					
			<display:column  sortable="" title="终点纬度"
					headerClass="sortable"  >
				${list.tlPZLa }
			</display:column>
			
			<display:column  sortable="true"  title="标准到点率"
					headerClass="sortable"  >
				<c:choose>
					<c:when test="${empty list.tlArrivedRate}">
						<font color="red">待设置</font>
					</c:when>
					<c:otherwise>
						${list.tlArrivedRate }%
					</c:otherwise>
				</c:choose>
			</display:column>
			
			<display:column  sortable="true"  title="巡检上报频率"
					headerClass="sortable"  >
				<c:choose>
					<c:when test="${empty list.tlReportInterval}">
						<font color="red">待设置</font>
					</c:when>
					<c:otherwise>
						${list.tlReportInterval }秒
					</c:otherwise>
				</c:choose>
			</display:column>
			
			<display:column  sortable="true"  title="巡检方式"
					headerClass="sortable"  >
				<c:choose>
					<c:when test="${empty list.tlExecuteType}">
						<font color="red">待设置</font>
					</c:when>
					<c:otherwise>
						<eoms:id2nameDB id="${list.tlExecuteType}" beanId="ItawSystemDictTypeDao" />
					</c:otherwise>
				</c:choose>
			</display:column>
			</center> 
		</display:table>
	</c:if>
	<c:if test="${!result}">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlan.do"
		sort="list" partialList="true" size="resultSize"
			decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>

	<center>
		<display:column title="元任务名称">
			<a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.resCfgId}">${list.resName}</a>
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
		<display:column  sortable="true" title="地理环境" 
				headerClass="sortable"  >
				<eoms:id2nameDB id="${list.eographicalEnvironment}" beanId="ItawSystemDictTypeDao" />
		</display:column>	
		
		<display:column  sortable="true" title="区域类型" 
				headerClass="sortable"  >
				<eoms:id2nameDB id="${list.regionType}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column title="巡检小组" >
			<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column title="创建日期" >
			<bean:write name="list" property="createTime" format="yyyy-MM-dd"/>
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
		<display:column  sortable="true" headerClass="sortable" title="公共巡检项操作" property="operation">
		</display:column>
		<c:if test="${pnrInspect2SwitchConfig.openMainSwitch eq true}">
			<display:column  sortable="true" headerClass="sortable" title="设备巡检操作" property="deviceInspect">
			</display:column>
		</c:if>
	</display:table>
	</c:if>
	</html:form>
<table>
	</table>
</center> 	

</div>

<%@ include file="/common/footer_eoms.jsp"%>