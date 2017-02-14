<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
Ext.onReady(function(){
		
  });


</script>

		<div id="sheetform">
			<html:form action="/pnrTransferOfficeMaleGuest.do?method=gotoDetailCycleReport"
				styleId="theform">
			    	
		        <!--区县 --><input type="hidden" id="tCountry" name="tCountry" value="${tCountry}" />			
		        <!--开始日期 --><input type="hidden" id="tStartTime" name="tStartTime" value="${tStartTime}" />			
		        <!--结束日期 --><input type="hidden" id="tEndTime" name="tEndTime" value="${tEndTime}" />			
		        <!-- 总条数 --><input type="hidden" id="tTotal" name="tTotal" value="${total}" />
		        <!--可操作标识 --><input type="hidden" id="operateFlag" name="operateFlag" value="${operateFlag}" />
		        <!--报表编号 --><input type="hidden" id="processInstanceId" name="processInstanceId" value="${reportNum}" />
		        
		        <!-- 以下隐藏域给列表导出用 -->
		        <!--<input type="text" id="country" name="country" value="${country2}" /> 区县 -->	
		        <!--<input type="text" id="sheetAcceptLimit" name="sheetAcceptLimit" value="${sheetAcceptLimit}" /> 开始日期 -->	
		        <!--<input type="text" id="sheetCompleteLimit" name="sheetCompleteLimit" value="${sheetCompleteLimit}" />结束日期 -->	
				
				<!-- buttons -->
				<fieldset style="border-width: 2px; border-color: #98C0F4;margin-bottom:10px;">
					<legend>
						<B>审批</B>
					</legend>
					<div>
						<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/materialQuantityUtil.jsp"%><!-- 材料数量 -->
					</div>
			</fieldset>
				
			</html:form>
		</div>
<br />
<bean:define id="url" value="pnrTransferOfficeMaleGuest.do"/>

	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="${pageSize}" class="listTable taskList"
		export="${export}" requestURI="pnrTransferOfficeMaleGuest.do"
		sort="list" size="total" partialList="true">
   		<display:column  sortable="true" headerClass="sortable" title="工单号">
   		 	<a href="${app}/activiti/${taskList.pathOne}/${taskList.pathTwo}.do?method=gotoDetail&processInstanceId=${taskList.processInstanceId}" target="_blank">
						${taskList.processInstanceId}
			</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="工单类型">${taskList.processTypeName}</display:column>	
		<display:column sortable="true"
			headerClass="sortable" title="工单主题"  >
			${taskList.theme}
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市" >
			<eoms:id2nameDB id="${taskList.city}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${taskList.country}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="是否归集">
			${taskList.maleGuestStateName}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="归集工单查看">
			<c:if test="${taskList.maleGuestState eq '1'}">
				<a href="javascript:void(0);" onclick="viewSingleCollection(&quot;${taskList.processInstanceId}&quot;,&quot;${taskList.siteCd}&quot;)">查看</a>
			</c:if>
			<c:if test="${taskList.maleGuestState ne '1'}">
				-
			</c:if>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="主场景" >
			${taskList.recentMainScenesName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="子场景" >
			${taskList.recentCopyScenesName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="场景模板" >
			<a href="javascript:void(0);" onclick="viewUpdateSceneTemplate(this,&quot;${taskList.processType}&quot;,&quot;${taskList.processInstanceId}&quot;,'auditReport',&quot;${operateFlag}&quot;,'Y')">查看</a>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="公客系统申告时间">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		<display:column sortable="true"
			headerClass="sortable" title="派单时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${taskList.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="处理时限(小时)">
				${taskList.processLimit}
		</display:column>
		
       <display:column  sortable="true"
			headerClass="sortable" title="业务号码" >
			${taskList.barrierNumber}
			</display:column>
			
       <display:column  sortable="true"
			headerClass="sortable" title="工单子类型" >
				<eoms:id2nameDB id="${taskList.subType}" beanId="ItawSystemDictTypeDao" />
			</display:column>

       <display:column  sortable="true"
			headerClass="sortable" title="局站名称" >
				${taskList.stationName}
			</display:column>
       	<display:column  sortable="true" headerClass="sortable" title="所属区域">
			<eoms:id2nameDB id="${taskList.deptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="代维公司" >
			<eoms:id2nameDB id="${taskList.initiator}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="故障原因" >
			<eoms:id2nameDB id="${taskList.faultSource}" beanId="ItawSystemDictTypeDao"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="现场拍照" >
			<a href="javascript:void(0);" onclick="viewLiveCamera(&quot;${taskList.processInstanceId}&quot;)">查看</a>
		</display:column>
		<display:column  sortable="true"
			headerClass="sortable" title="故障发生时间">
				<fmt:formatDate value="${taskList.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>				
		</display:column>
		
		<display:column sortable="true"
			headerClass="sortable" title="故障处理回复时间"
			format="{0,date,yyyy-MM-dd HH:mm:ss}" >
				<fmt:formatDate value="${taskList.lastReplyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="工单历时">
				${taskList.workTask}
		</display:column>
		
		<display:column  sortable="true"
			headerClass="sortable" title="当前状态">
			${taskList.statusName}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="联系人" >
			${taskList.connectPerson}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="地址" maxLength="15">
			${taskList.installAddress}
		</display:column>
		
		<display:column  sortable="true" headerClass="sortable" title="当前处理人" >
			<eoms:id2nameDB id="${taskList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
	</display:table>


<script type="text/javascript">
jq(function(){
	jq("#exportMaterialQuantity").css('display','none');
});

//导出材料数量汇总结果
//jq(document).delegate("#exportMaterialQuantity","click", function(){
	//var tCountry = jq("#tCountry").val(); //区县
	//var tStartTime = jq("#tStartTime").val(); //开始日期
	//var tEndTime = jq("#tEndTime").val(); //结束日期

	//window.location.href="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=exportMaterialQuantity&reportFlag=auditReport&country="+tCountry+"&sheetAcceptLimit="+tStartTime+"&sheetCompleteLimit="+tEndTime;
//});
</script>
<!-- 引入场景模板公用js代码  lookSceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/lookSceneJsUtil.jsp"%>	
<%@ include file="/common/footer_eoms.jsp"%>