<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	var myjs=jQuery.noConflict();
    var checkflag = "false";
	function chooseAll(){	
	    var objs = document.getElementsByName("checkboxId");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	}
	
	/**
	*  关联勾选项
	*/
	function relateChecked(){
		if (confirm("是否关联勾选项?")==true){
			var objs = document.getElementsByName("checkboxId");
			//取第2个form表单
			var form = document.getElementById('resCheckForm');
		    form.action = "${app }/partner/inspect/inspectPlan.do?method=assignInspectPlanRes&assignType=check&planId=${planId}";
		    var flag = false;
		    for(var i=0; i<objs.length; i++){
		       if(objs[i].checked==true){
		           flag=true;
		           break;
		       }
		    }
		    if(flag==true){
		       form.submit();
		    }
		    else if(flag==false){
		        alert("请选择资源！");
		    }
		}
	}

	/**
	*  关联查询项
	*/
	function relateQuery(){
		var assignFlag = myjs("#assignFlag").val();
		if(!assignFlag || assignFlag != 0){
			alert('关联查询出所有项时，查询条件必须为未关联');
			return;
		}else{
			var queryStr = "${queryStr}";
			if(!queryStr){
				alert('请首先进行查询操作');
				return;
			}else{
				if (confirm("是否关联查询出所有项?")==true){
					//取第2个form表单
					var form = document.getElementById('resCheckForm');
				    form.action = "${app }/partner/inspect/inspectPlan.do?method=assignInspectPlanRes&assignType=query&planId=${planId}";
				    form.submit();
				}
			}
		}
	}
	
	/**
	*  取消关联项
	*/
	function relateCacel(){
		if (confirm("是否取消关联项?")==true){
			var objs = document.getElementsByName("checkboxId");
			//取第2个form表单
			var form = document.getElementById('resCheckForm');
		    form.action = "${app }/partner/inspect/inspectPlan.do?method=assignInspectPlanRes&assignType=cancel&planId=${planId}";
		    var flag = false;
		    for(var i=0; i<objs.length; i++){
		       if(objs[i].checked==true){
		           flag=true;
		           break;
		       }
		    }
		    if(flag==true){
		       form.submit();
		    }
		    else if(flag==false){
		        alert("请选择资源！");
		    }
		}
	}
	
	/**
	*  新增临时元任务
	*/
	function addBurstRes(){
		//取第2个form表单
		var form = document.getElementById('resCheckForm');
	    form.action = "${app }/partner/inspect/inspectGenerateAction.do?method=toPnrResConfigGenerate";
	    form.submit();
	}
	
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
	
	function backPlanList(){
		var form = document.getElementById('resCheckForm');
		form.action = "${app }/partner/inspect/inspectPlan.do?method=findInspectPlanMainList";
		form.submit();
	}
</script>

<table id="stylesheet" class="listTable">
	<tr>
		<td class="label">巡检计划名称</td>
		<td class="content">${planMain.planName}</td>
		<td class="label">巡检专业</td>
		<td><eoms:id2nameDB id="${planMain.specialty}" beanId="ItawSystemDictTypeDao" /></td>
	</tr>
	<tr>
		<td class="label">代维公司</td>
		<td><eoms:id2nameDB id="${planMain.partnerDeptId}" beanId="partnerDeptDao"/></td>
		<td class="label"><strong>已关联巡检任务数</strong></td>
		<td class="content">${planMain.resNum }</td>
	</tr>
</table>
<br/>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img src="${app}/images/icons/search.gif"
	 align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" style="display:true;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<html:form  action="inspectPlan.do?method=findPlanResAssignList" styleId="gridForm" method="post"> 
		<input type="hidden" value="${planId }" name="id"/>
		<center> 
			<table class="listTable">
				<tr>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resType" defaultValue="${resType}" id="resourceType" styleClass="select"
							initDicId="${specialty}" alt="allowBlank:false" sub="resourceLevel" /> 
					</td>
					<td class="label">资源级别</td>
					<td class="content" >
							<eoms:comboBox name="resLevel" defaultValue="${resLevel}" id="resourceLevel"  styleClass="select"
								initDicId="${resType}" alt="allowBlank:false" />
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
					<td class="label">关联情况</td> 
					<td class="content" >
						<select name="assignFlag" id="assignFlag" class="select">
							<option value=""  <c:if test="${assignFlag == ''}">selected="true"</c:if>>所有</option>
							<option value="0" <c:if test="${assignFlag == '0'}">selected="true"</c:if>>未关联</option>
							<option value="1" <c:if test="${assignFlag == '1'}">selected="true"</c:if>>已关联</option>
						</select>
					</td>
				</tr>
			</table>
		</center> 
		<table>
		    <tr>
			    <td>
			    	<input type="submit" class="btn" value="查询" />&nbsp;&nbsp;
			    	<input type="button" class="btn" value="关联勾选项" onclick="relateChecked()"/>&nbsp;&nbsp;
			    	<input type="button" class="btn" value="关联查询出所有项" id="queryItemBtn" onclick="relateQuery()"/>&nbsp;&nbsp;
			    	<input type="button" class="btn" value="取消关联勾选项" onclick="relateCacel()"/>&nbsp;&nbsp;
			    	<input type="button" class="btn" value="返回" onclick="backPlanList();">
				</td>
			</tr>
		</table>	
	</html:form>
	<br/>
</div>

<html:form action="inspectPlan" method="post" styleId="resCheckForm">
	<input type="hidden" value="${queryStr}" name="queryStr"/>
	<input type="hidden" value="${baseQueryStr}" name="baseQueryStr"/>
	<c:if test="${transLine eq 'yes'}" var="result" >
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlan.do?method=findPlanResAssignList"
		sort="list" partialList="true" size="resultSize">
		<center>
			<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
		         <c:if test="${list.forceAssign==0}" var="result">
		         	<input type="checkbox" name="checkboxId" value="<c:out value='${list.id}'/>" />
				 </c:if>
	    	</display:column>
		
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
			<display:column title="巡检周期" >
				${cycleMap[list.inspectCycle]}
			</display:column>
			<display:column title="周期开始时间" >
				<bean:write name="list" property="cycleStartTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="周期结束时间" >
				<bean:write name="list" property="cycleEndTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="巡检开始日期" >
				<bean:write name="list" property="planStartTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="巡检结束日期" >
				<bean:write name="list" property="planEndTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="是否临时元任务" >
				<c:choose>
					<c:when test="${list.burstFlag eq 1 }">是</c:when>
					<c:otherwise>否</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="是否关联本计划" >
				<c:if test="${empty list.planId}" var="res">
					<font color="red">否</font>
				</c:if>
				<c:if test="${!res}">
					是
				</c:if>
			</display:column>
		</center> 
	</display:table>
	</c:if>
	<c:if test="${!result}">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlan.do?method=findPlanResAssignList"
		sort="list" partialList="true" size="resultSize">
		<center>
			<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
	       <%--<c:if test="${list.forceAssign==0}" var="result"> --%> 
	         	<input type="checkbox" name="checkboxId" value="<c:out value='${list.id}'/>" />
		   <%-- </c:if> --%> 
	    	</display:column>
			<display:column title="巡检任务名称">
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
			<display:column title="代维小组">
				<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>	
			</display:column>
			<display:column title="巡检周期" >
				${cycleMap[list.inspectCycle]}
			</display:column>
			<display:column title="周期开始时间" >
				<bean:write name="list" property="cycleStartTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="周期结束时间" >
				<bean:write name="list" property="cycleEndTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="巡检开始日期" >
				<bean:write name="list" property="planStartTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="巡检结束日期" >
				<bean:write name="list" property="planEndTime" format="yyyy-MM-dd"/>
			</display:column>
			<display:column title="是否临时元任务" >
				<c:choose>
					<c:when test="${list.burstFlag eq 1 }">是</c:when>
					<c:otherwise>否</c:otherwise>
				</c:choose>
			</display:column>
			<display:column title="是否关联本计划" >
				<c:if test="${empty list.planId}" var="result">
					<font color="red">否</font>
				</c:if>
				<c:if test="${!result}">
					是
				</c:if>
			</display:column>
			
		</center> 
	</display:table>
	</c:if>
	<br>
	<input type="button" class="btn" value="新增临时元任务" onclick="addBurstRes()"/>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>