<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<style type="text/css">
#qqonline {
position: absolute;
top: 320px;
}
.formTable{
   table-layout:fixed;
}
.formTable td{
   width:60px;
}
.formTableFor {
    border-collapse: collapse;
    font-size: 12px;
}
.formTableFor td {
    background-color: #FFFFFF;
    border: 1px solid #C9DEFA;
    padding: 6px;
}
.formTableFor td.label {
    background-color: #EDF5FD;
    vertical-align: top;
    width: 15%;
}
.formTableFor td.content {
    text-align: left;
}
 </style>
  
<body>	
<div id="qqonline" style="display:none">
 <table class="formTable" >
 <tr>
<td class="label"></td>
<c:choose>
<c:when test="${searchType eq 'allSearch' }">    
<td class="label">代维公司</td>
<td class="label"></td>
<td class="label" colspan="4" style="width:320px;">故障处理</td>
<td class="label" colspan="4" style="width:320px;">投诉处理</td>
<td class="label" colspan="4" style="width:320px;">网络变更调整</td>
<td class="label" colspan="4" style="width:320px;">应急保障</td>
<td class="label" colspan="4" style="width:320px;">发电保障</td>
<td class="label" colspan="4" style="width:320px;">网络优化实施</td>
<td class="label" colspan="4" style="width:320px;">工程验收与交维</td>
<td class="label" colspan="4" style="width:320px;">随工任务</td>
<td class="label" colspan="4" style="width:320px;">资源核查</td>
<td class="label" colspan="4" style="width:320px;">其他任务</td>
<td class="label" colspan="4" style="width:320px;">巡检任务</td>
<td class="label" colspan="4" style="width:320px;">隐患处理</td>
<td class="label" colspan="4" style="width:320px;">业务咨询</td>
<td class="label" style="width:120px;"></td>
<td class="label" style="width:120px;"></td>
</c:when>
<c:otherwise>
<td class="label"></td>
<td class="label" colspan="3" style="width:320px;">故障处理</td>
<td class="label" colspan="3" style="width:320px;">投诉处理</td>
<td class="label" colspan="3" style="width:320px;">网络变更调整</td>
<td class="label" colspan="3" style="width:320px;">应急保障</td>
<td class="label" colspan="3" style="width:320px;">发电保障</td>
<td class="label" colspan="3" style="width:320px;">网络优化实施</td>
<td class="label" colspan="3" style="width:320px;">工程验收与交维</td>
<td class="label" colspan="3" style="width:320px;">随工任务</td>
<td class="label" colspan="3" style="width:320px;">资源核查</td>
<td class="label" colspan="3" style="width:320px;">其他任务</td>
<td class="label" colspan="3" style="width:320px;">巡检任务</td>
<td class="label" colspan="3" style="width:320px;">隐患处理</td>
<td class="label" colspan="3" style="width:320px;">业务咨询</td>
<td class="label" style="width:120px;"></td>
<td class="label" style="width:120px;"></td>
 </c:otherwise>
</c:choose>
</tr>
<tr>
<c:choose>
<c:when test="${searchType eq 'allSearch' }">   
<td class="label">地区</td><td class="label">代维公司</td><td class="label">专业</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td>
<td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td>
<td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td>
<td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td>
<td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">专业合计费</td><td class="label">公司合计费</td>
</c:when>
<c:otherwise>
<td class="label">地区</td><td class="label">专业</td><td class="label">数量</td><td class="label">标准次</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td>
<td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> 
<td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td>
 <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td>
<td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">合计</td><td class="label">专业合计费</td><td class="label">区域合计</td>

 </c:otherwise>
</c:choose>
</tr>
</table>
    </div>

<div id="searchTop">
<c:if test="${seeOnly!='seeOnly'}">
<form id="checkAndSearchFrom"	action="pnrHomePageAction.do?method=allFindSearch" method="post" >
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTableFor" style="width:100%">
		<logic:notPresent name="level">
			<tr>
				<td class="label">
					区域
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${areaStringLike}',name:'<eoms:id2nameDB id="${areaStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area" id="area"    class="text medium"  onblur="changeCheckBox()"/>
					<input type="hidden" name="areaIdSearch" id="areaIdSearch"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属区域' valueField="areaIdSearch" handler="area" 
					textField="area" data="${boxData}" checktype="" single="true">
					</eoms:xbox>
				</td>
				
				<td class="label">
					代维公司&nbsp;
				</td>
				<td class="content">
					<input type="text" name="maintainCompany" class="text"  id="maintainCompany" 
					 onblur="changeCheckBox()" value="${maintainCompany}"/>
					<input type="hidden" name="maintainCompanyId" id="maintainCompanyId"  value="${maintainCompanyId}" maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=dept" rootId="${rootDeptId}"
					rootText='代维公司组织' valueField="maintainCompanyId" handler="maintainCompany" textField="maintainCompany"
					checktype="dept"  single="true">
					</eoms:xbox>
				</td>
				
			</tr>
			</logic:notPresent>
			<%--<tr>
				<td class="label">
					巡检专业
				</td>
				<td class="content">
					<eoms:comboBox  name="maintenanceMajor" id="maintenanceMajor"  defaultValue="${major}" initDicId="11225" styleClass="input select"   onchange="changeCheckBox()" />
				</td>
				<td class="label">
					计次类型
				</td>
				<td class="content">
					<select id="eventtypes" name="eventtypes"> 
						<option id="0" value="0">--请选择计次类型--</option>
						<option id="1" value="1">故障处理</option>
						<option id="2" value="2">投诉处理</option>
						<option id="3" value="3">网络变更调整</option>
						<option id="4" value="4">应急保障</option>
						<option  id="5" value="5">发电保障</option>
						<option id="6"  value="6">网络优化实施</option>
						<option  id="7" value="7">工程验收与交维</option>
						<option id="8" value="8">随工任务</option>
						<option id="9" value="9">资源核查</option>
						<option id="10" value="10">其他任务</option>
						<option id="11" value="11">巡检任务</option>
						<option id="12" value="12">隐患处理</option>
						<option id="13" value="13">业务咨询</option>
					</select>
				</td>
			</tr>
			--%><tr>
				<td class="label">
					时间
				</td>
				<td class="content" colspan="4">
					<select id="year" name="year" onchange="changeCheckBox();">
						<option value="">
							请选择年
						</option>
						<c:forEach begin="2012" end="2050" var="year">
							<c:choose>
								<c:when test="${year1==year}">
										<option  value="${year}" selected="selected">${year}年</option>
								</c:when>
								<c:otherwise>
									<option  value="${year}">${year}年</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<select id="month" name="month" onchange="changeCheckBox();" >
						<option value="">
							请选择月
						</option>
						<c:forEach begin="1" end="12" var="month">
							<c:choose>
								<c:when test="${month1==month}">
										<option  value="${month}" selected="selected">${month}月</option>
								</c:when>
								<c:otherwise>
									<option  value="${month}">${month}月</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTableFor" style="width:100%">
			<tr>
				<td class="label" colspan="2">
					<input value="" type="checkbox" name="statisticsItem" 	id="area_idTypeLikeArea" />
					区域
				</td>
				<td class="label" colspan="2">
					<input value="allSearch" type="checkbox" name="statisticsItem"	id="company_name" />
					代维公司
				</td>
			</tr>
			<tr>
				<input type="hidden" name="statisticsItems" id="statisticsItems" />
				<input type="hidden" name="checkedIds" id="checkedIds" />
				<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
				<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
				<input type="hidden" name="exportFlag" id="exportFlag" >
			</tr>
		</table>
	</fieldset>
	<input type="button" name="统计" value="统计" onclick="sendBox();"/>
	<input type="button" name="重置" value="重置" onclick="res()" />
</form>

	
	</br>
	</br>
	</c:if>
</div>	
	
<table class="formTable" style="width:100%" align="center" id="all">
<tr  id="trTop">
<td class="label"></td>
<c:choose>
<c:when test="${searchType eq 'allSearch' }">    
<td class="label">代维公司</td>
<td class="label"></td>
<td class="label" colspan="4" style="width:320px;">故障处理</td>
<td class="label" colspan="4" style="width:320px;">投诉处理</td>
<td class="label" colspan="4" style="width:320px;">网络变更调整</td>
<td class="label" colspan="4" style="width:320px;">应急保障</td>
<td class="label" colspan="4" style="width:320px;">发电保障</td>
<td class="label" colspan="4" style="width:320px;">网络优化实施</td>
<td class="label" colspan="4" style="width:320px;">工程验收与交维</td>
<td class="label" colspan="4" style="width:320px;">随工任务</td>
<td class="label" colspan="4" style="width:320px;">资源核查</td>
<td class="label" colspan="4" style="width:320px;">其他任务</td>
<td class="label" colspan="4" style="width:320px;">巡检任务</td>
<td class="label" colspan="4" style="width:320px;">隐患处理</td>
<td class="label" colspan="4" style="width:320px;">业务咨询</td>
<td class="label" style="width:120px;"></td>
<td class="label" style="width:120px;"></td>
</c:when>
<c:otherwise>
<td class="label"></td>
<td class="label" colspan="3" style="width:320px;">故障处理</td>
<td class="label" colspan="3" style="width:320px;">投诉处理</td>
<td class="label" colspan="3" style="width:320px;">网络变更调整</td>
<td class="label" colspan="3" style="width:320px;">应急保障</td>
<td class="label" colspan="3" style="width:320px;">发电保障</td>
<td class="label" colspan="3" style="width:320px;">网络优化实施</td>
<td class="label" colspan="3" style="width:320px;">工程验收与交维</td>
<td class="label" colspan="3" style="width:320px;">随工任务</td>
<td class="label" colspan="3" style="width:320px;">资源核查</td>
<td class="label" colspan="3" style="width:320px;">其他任务</td>
<td class="label" colspan="3" style="width:320px;">巡检任务</td>
<td class="label" colspan="3" style="width:320px;">隐患处理</td>
<td class="label" colspan="3" style="width:320px;">业务咨询</td>
<td class="label" style="width:120px;"></td>
<td class="label" style="width:120px;"></td>
 </c:otherwise>
</c:choose>
</tr>
<tr>
<c:choose>
<c:when test="${searchType eq 'allSearch' }">   
<td class="label">地区</td><td class="label">代维公司</td><td class="label">专业</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td>
<td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td>
<td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td>
<td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td>
<td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">单价</td><td class="label">合计</td><td class="label">专业合计费</td><td class="label">公司合计费</td>
</c:when>
<c:otherwise>
<td class="label">地区</td><td class="label">专业</td><td class="label">数量</td><td class="label">标准次</td><td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td>
<td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> 
<td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td>
 <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td>
<td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td> <td class="label">合计</td><td class="label">数量</td><td class="label">标准次</td><td class="label">合计</td><td class="label">专业合计费</td><td class="label">区域合计</td>

 </c:otherwise>
</c:choose>
</tr>
 <logic-el:present name="tableList">
						<c:forEach items="${tableList}" var="tdList" >
							<tr>
							<% int ihref=0; %>
								<c:forEach items="${tdList}" var="td" varStatus="i">
								<c:if test="${i.index!=0 && i.index!=2 && i.index!=4 && searchType eq 'allSearch'}">
								<c:if test="${td.show}">
				<td rowspan="${td.rowspan}"  align="right">
		 	 <c:if test="${i.index!=1 && i.index != 3 }">
				 	<c:if test="${i.index>5 && (i.index-2)%4==0 }">
				 	<% ihref= ihref+1; 
				 	request.setAttribute("ihref",ihref);%>
				 		<c:if test="${allTypeList != null && ihref<=fn:length(allTypeList)}">
				 			<c:if test="${tdList[i.index].name!='0'}">
				 			<%
				 			java.util.List allTypeList = (java.util.List)request.getAttribute("allTypeList");
				 			String type = com.boco.eoms.base.util.StaticMethod.nullObject2String(allTypeList.get(ihref-1));
				 			
				 			
				 			
				 			%>
				 				<c:if test="${displayLevel=='1'}">
				 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&specialty=${tdList[4].name}&holdyear=${year1}&holdmonth=${month1}&zqprovincedeptid=${tdList[2].name}&metering_type=<%=type%>" target="zuan">${td.name}</a>
				 				</c:if>
				 				 <c:if test="${displayLevel=='2'}">
				 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&specialty=${tdList[4].name}&holdyear=${year1}&holdmonth=${month1}&zqcitydeptid=${tdList[2].name}&metering_type=<%=type%>" target="zuan">${td.name}</a>
				 				</c:if>
				 				<c:if test="${displayLevel=='3'}">
				 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&specialty=${tdList[4].name}&holdyear=${year1}&holdmonth=${month1}&zqcountrydeptid=${tdList[2].name}&metering_type=<%=type%>" target="zuan">${td.name}</a>
				 				</c:if>
				 				<c:if test="${displayLevel=='4'}">
				 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&specialty=${tdList[4].name}&holdyear=${year1}&holdmonth=${month1}&zqgroupdeptid=${tdList[2].name}&metering_type=<%=type%>" target="zuan">${td.name}</a>
				 				</c:if>
				 				
				 			</c:if>
				 			<c:if test="${tdList[i.index].name=='0'}">
				 				-
				 			</c:if>
				 		</c:if>
				 		<c:if test="${!(allTypeList != null && ihref<=fn:length(allTypeList))}">
				 			${td.name}
				 		</c:if>
				 	</c:if>
				 	<c:if test="${!(i.index>5 && (i.index-2)%4==0)}">
				 	<c:if test="${tdList[i.index].name=='0'}">
				 				-
			 			</c:if>
		 			<c:if test="${tdList[i.index].name!='0'}">
		 			<%--
		 			<fmt:formatNumber type="number" value="${td.name}" minFractionDigits="3"/> 
		 			--%>
		 			${td.name}
		 			</c:if>
				 	</c:if>
			 </c:if>
			
			 <c:if test="${i.index == 1}"> 
			 <logic:notPresent name="deptlevel">
				 <a href="${app}/partner/mainPage/pnrHomePageAction.do?method=allFindSearch&areaId=${tdList[i.index-1].name}&statisticsItems=${searchType}&searchFor=searchFor&year=${year1}&month=${month1}&seeOnly=${seeOnly }"  >${td.name}</a>
			 </logic:notPresent>
			 <logic:present name="deptlevel">
				${td.name}
				</logic:present>
			 </c:if>
			 <c:if test="${searchType!='allSearch' &&i.index == 3 }">
			 		${td.name}
			 </c:if>
			 <c:if test="${searchType eq 'allSearch' && i.index == 3 }"> 
			 <logic:notPresent name="deptlevel">
				 <a href="${app}/partner/mainPage/pnrHomePageAction.do?method=allFindSearch&maintainCompanyId=${tdList[i.index-1].name}&areaId=${tdList[i.index-3].name}&statisticsItems=${searchType}&searchFor=searchFor&year=${year1}&month=${month1}&seeOnly=${seeOnly }"  >${td.name}</a>
			 </logic:notPresent>
			  <logic:present name="deptlevel">
				${td.name}
				</logic:present>
			 </c:if>
			 
			 	</td>
			 	</c:if>
			 	</c:if>
			 	
								
									  <c:if test="${i.index!=0&&i.index!=2&& searchType != 'allSearch'}">
										<c:if test="${td.show}">
										<td rowspan="${td.rowspan}"  align="right">
										   <c:if test="${i.index!=1 && i.index != 3 }">
										   		<c:if test="${i.index>3 && (i.index)%3==1&&i.index!=43 }">
													 	<% ihref= ihref+1; 
													 	request.setAttribute("ihref",ihref);%>
													 		<c:if test="${allTypeList != null && ihref<=fn:length(allTypeList)}">
													 			<% String szHref = ""; %>
													 			<c:if test="${tdList[i.index].name!='0'}">
													 			<%
													 			java.util.List allTypeList = (java.util.List)request.getAttribute("allTypeList");
													 			String type = com.boco.eoms.base.util.StaticMethod.nullObject2String(allTypeList.get(ihref-1));
													 			%>
																		 <c:if test="${displayLevel=='1'}">
															 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&province=${tdList[0].name}&specialty=${tdList[2].name}&holdyear=${year1}&holdmonth=${month1}&metering_type=<%=type%>" target="zuan">${td.name}</a>
														 				</c:if>
														 				 <c:if test="${displayLevel=='2'}">
															 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&city=${tdList[0].name}&specialty=${tdList[2].name}&holdyear=${year1}&holdmonth=${month1}&metering_type=<%=type%>" target="zuan">${td.name}</a>
														 				</c:if>
														 				<c:if test="${displayLevel=='3'}">
															 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&country=${tdList[0].name}&specialty=${tdList[2].name}&holdyear=${year1}&holdmonth=${month1}&metering_type=<%=type%>" target="zuan">${td.name}</a>
														 				</c:if>
														 				<c:if test="${displayLevel=='4'}">
															 				<a href="${app}/partner/mainPage/pnrHomePageAction.do?method=goToSheetStatisticsDetailPage&country=${tdList[0].name}&specialty=${tdList[2].name}&holdyear=${year1}&holdmonth=${month1}&metering_type=<%=type%>" target="zuan">${td.name}</a>
														 				</c:if>
													 			</c:if>	
																 		<c:if test="${tdList[i.index].name=='0'}">
											 									-
										 								</c:if>	
							 								</c:if>
																 		<c:if test="${!(allTypeList != null && ihref<=fn:length(allTypeList))}">
																 			${td.name}
																 		</c:if>
			 									</c:if>	
								 			<c:if test="${!(i.index>3 && (i.index)%3==1)}">
											 	<c:if test="${tdList[i.index].name=='0'}">
											 				-
									 			</c:if>
									 			<c:if test="${tdList[i.index].name!='0'}">
									 					<%--
											 			<fmt:formatNumber type="number" value="${td.name}" minFractionDigits="3"/> 
											 			--%>
											 			${td.name}
									 			</c:if>
				 							</c:if>
								 			<c:if test="${i.index==43}">
								 			${td.name}
								 			</c:if>
								 				
										</c:if>
										
										 <c:if test="${i.index == 1 }"> 
											 <logic:notPresent name="deptlevel">
												 <a href="${app}/partner/mainPage/pnrHomePageAction.do?method=allFindSearch&areaId=${tdList[i.index-1].name}&statisticsItems=${searchType}&searchFor=searchFor&year=${year1}&month=${month1}&seeOnly=${seeOnly }"  >${td.name}</a>
											 </logic:notPresent>
											 <logic:present name="deptlevel">
												${td.name}
												</logic:present>
											 </c:if>
											 <c:if test="${searchType!='allSearch' &&i.index == 3 }">
											 		${td.name}
											 </c:if>
											 
										</td>
										</c:if>
									</c:if>
									
									
									
								</c:forEach>
								
							</tr>
						</c:forEach>
						</logic-el:present>
						 <tr>
	   <c:if test="${searchType eq 'allSearch'}"> 
 	<td class="label" colspan="3">
				  合计
		  </td>
	   </c:if>
	  <c:if test="${searchType != 'allSearch'}"> 
		  <td class="label" colspan="2">
		 		 合计
		  </td>
	   </c:if>
	   
  <c:forEach items="${chargingTotalEndList}"  var="totalList" varStatus="i" >
  
  <c:if test="${searchType eq 'allSearch'}"> 
		  <c:if test="${i.last}"> 
		  <td class="label" colspan="2"  align="right">
	 			 ${totalList}
		 </td>
		  </c:if>
	  <c:if test="${!i.last}"> 
	   	<td class="label"  align="right">
	 			 ${totalList}
		 </td>
		 </c:if>
	 </c:if>
	 
	 <c:if test="${searchType != 'allSearch'}"> 
		  <c:if test="${i.last}" > 
		  <td class="label" colspan="2"  align="right">
	 			 ${totalList}
		 </td>
		  </c:if>
	  <c:if test="${!i.last}"> 
	   	<td class="label"  align="right">
	 			 ${totalList}
		 </td>
		 </c:if>
	 </c:if>
  </c:forEach>
</tr>
</table>
</body>
<script type="text/javascript">
		var jq=jQuery.noConflict();
		Ext.onReady(function(){
			var checked="${checkedIdsStr}";
			if(checked!=null&&checked!=""){
				if(checked=="area_idTypeLikeArea;"){
					document.getElementById("area_idTypeLikeArea").checked=true;
				}
				else if(checked=="company_name;"){
					document.getElementById("company_name").checked=true;
				}
				else{
					document.getElementById("area_idTypeLikeArea").checked=true;	
					document.getElementById("company_name").checked=true;
				}
			}
			changeCheckBox();
			//getDefaultSelect();
			var check=document.getElementById("checkedList");
			if(check&&check.value!=""){
				//先清空所有的勾选框
				var all=document.getElementsByName("statisticsItem");
				for (i = 0 ; i <all.length; i ++) {
					var checkValue="#"+all[i].id;
					jq(checkValue).attr('checked',false);
				}
				checkV=check.value;
				var checks=checkV.toString().split(";");
				for(var i=0;i<checks.length-1 ;i++){
					//alert(checks[i].toString());
					checkValue ='#'+checks[i].toString();
					jq(checkValue).attr('checked',true);
				}
			}
			var searchTop;
			var seeOnly="${seeOnly}";
			if(seeOnly=='seeOnly'){
				searchTop=0;
			}
			else{
				searchTop=document.getElementById("searchTop").scrollHeight;
			}
			var trTop=document.getElementById("trTop").scrollHeight;
			var allTop=searchTop*1+trTop*1;	
			jq(window).scroll(function(){//触发滚动条事件，并绑定一个事件函�?

				var bodyTop = 0;

				if (typeof window.pageYOffset != 'undefined')

				{

				bodyTop = window.pageYOffset;//指的是滚动条顶部到网页顶部的距离

				//document.body.scrollTop滚动条顶部到网页顶部的这段距�?

				} else if (typeof document.compatMode!='undefined' && document.compatMode!='BackCompat')

				{

				bodyTop = document.documentElement.scrollTop;

				} else if (typeof document.body != 'undefined')

				{

				bodyTop = document.body.scrollTop;

				}

				// 设置层的CSS样式中的top属�? 注意要是小写，要符合“标准�?
		        if(bodyTop>allTop){
				jq("#qqonline").css("top",bodyTop);
					document.getElementById("qqonline").style.display="block";
		        }
		        else{
		      	 document.getElementById("qqonline").style.display="none";
		        }
				// 设置层的内容，这里只是显示当前的scrollTop

				//jq("#qqonline").text(bodyTop);

				 });
			
		});
		
		
		
			
			function openImport(){
				var handler = document.getElementById("openQuery");
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
			
			 function res(){
					var formElement=document.getElementById("checkAndSearchFrom")
				   	 var inputs = formElement.getElementsByTagName('input');
				   	 var selects =formElement.getElementsByTagName('select');
				     for(var i=0;i<inputs.length;i++){
				         if(inputs[i].type == 'text'){
				         	  if(inputs[i].id!='province'&&inputs[i].id!='provinceAreaId'){
					              inputs[i].value = '';
				         	  }
				         }
				          if(inputs[i].type == 'checkbox'){
				          		if(inputs[i].id!='actual_config'&&inputs[i].id!='standard_config'){
				             		 inputs[i].checked =false;
				              	}
				         }
			     	}
			     	 for(var i=0;i<selects.length;i++){
				         if(selects[i].type == 'select-one'){
				              selects[i].options[0].selected = true;
				         }
			     	}
			     	document.getElementById("hasSend").value="nook";
			     	changeCheckBox();
				}
			    function sendBox() {
			    	document.getElementById("exportFlag").value="1";//执行的是统计操作
					var statisticsItemList = document.getElementsByName("statisticsItem");//获取选中的统计项目的值与实体属性名相对应
					var idResult = "";
					var checkedIds="";
					for (i = 0 ; i < statisticsItemList.length; i ++) {
						if (statisticsItemList[i].checked) {
							var itemV=statisticsItemList[i].value;
							idResult+=itemV.toString();
							var checkedId=statisticsItemList[i].id;
							checkedIds+=checkedId.toString()+";";
						}
					}
					document.getElementById("statisticsItems").value=idResult.toString();//拼接被勾选的统计项目的value其值包含"TypeLikedict"标识；
					document.getElementById("checkedIds").value=checkedIds.toString();//获取选中的统计项目的值与实体属性数据库名相对应
					//if(idResult==""){
					//	alert("请至少选择一个统计项");
					//	return false;
					//}
				 	//var year=document.getElementById("year").value;
				 	//var month=document.getElementById("month").value;
				 	//if(year==""){
				 	//	alert("请选择统计时间【年】!");
				 	// 	return false;
				 	//}
				 	//if(month==""){
				 	//	alert("请选择统计时间【月】!");
				 	//	return false;
				 //	}
					document.getElementById("checkAndSearchFrom").submit();
				}
				function changeCheckBox(){
					var area = jq('#area').val();
					var company=jq('#maintainCompany').val();
					if(area){
						jq('#area_idTypeLikeArea').attr('checked',true);
						jq('#area_idTypeLikeArea').attr('disabled','disabled');
					}else{
						if((jq('#area_idTypeLikeArea').attr('disabled'))){
							jq('#area_idTypeLikeArea').attr('checked',false);
							jq('#area_idTypeLikeArea').attr('disabled','');
						}
					}
					if(company){
						jq('#company_name').attr('checked',true);
						jq('#company_name').attr('disabled','disabled');
					}else{
						if((jq('#company_name').attr('disabled'))){
							jq('#company_name').attr('checked',false);
							jq('#company_name').attr('disabled','');
						}
					}
				}
				
				/*给计次类型统计后添加默认值*/
			//	function getDefaultSelect(){
				//	var vl="${eventtypes}";
				//	document.getElementById("eventtypes").value=vl;
				//}	
		</script>	
<%@ include file="/common/footer_eoms.jsp"%>
