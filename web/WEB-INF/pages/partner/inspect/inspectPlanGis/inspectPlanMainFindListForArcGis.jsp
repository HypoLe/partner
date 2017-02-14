<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
	if(parent.frames['arcGis-page'].map!=null){
		parent.frames['arcGis-page'].mapCenterAndZoom(true,true);
		}
	parent.removeblock();
});
</script>

 <form  action="${app}/partner/inspect/inspectPlanGisAction.do?method=saveInspectPlanGis" method="post">
 <input type="hidden" value="提交"/>
  </form>
  
  <c:if test="${isDept eq 'no'}">
  
  <table class="table list" cellpadding="0" cellspacing="0">
  	<thead>
  		<tr>
  			<th>位置</th>
  			<th>完成数</th>
  			<th>任务数</th>
  			<th>完成率</th>
  		</tr>
  	</thead>
  
  	<c:forEach var="list" items="${citylist}">
  		<tr>
  			<td><c:if test="${iscity eq 'yes'}"><a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindListForArcGis&city=${list.areaid }">${list.areaname }</a></c:if>
  				<c:if test="${iscity eq 'no'}">${list.areaname }</c:if>
  			</td>
  			<c:choose>
  				<c:when test="${list.resdonenum eq 0 and list.resnum >0}">
  					<td style="background-color:#FF0000">${list.resdonenum }</td>
  				</c:when>
  				<c:when test="${list.resdonenum eq 0 and list.resnum eq 0}">
  					<td >${list.resdonenum }</td>
  				</c:when>
  				<c:when test="${list.resdonenum eq list.resnum}">
  					
  					<td style="background-color:#66FF66">
  						<c:if test="${iscity eq 'yes'}">
  						<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindListForArcGis&city=${list.areaid }">${list.resdonenum }</a>
  						</c:if>
  						<c:if test="${iscity eq 'no'}">
  						<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskListArcGis&city=${list.areaid}&isDone=yes" >${list.resdonenum }</a>
  						</c:if>
  					</td>
  					
  				</c:when>
  				<c:otherwise>
  					<td style="background-color:#FFFF66">
  					<c:if test="${iscity eq 'yes'}">
  					<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindListForArcGis&city=${list.areaid }">${list.resdonenum }</a>
  					</c:if>
  					<c:if test="${iscity eq 'no'}">
  					<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskListArcGis&city=${list.areaid}&isDone=yes" >${list.resdonenum }</a>
  					</c:if>
  					</td>
  				</c:otherwise>
  			</c:choose>
  			<td>
	  			<c:if test="${iscity eq 'yes'}">
				<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanMainFindListForArcGis&city=${list.areaid}">${list.resnum }</a>
				</c:if>
				<c:if test="${iscity eq 'no'}">
				<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskListArcGis&city=${list.areaid}&isDone=no" >${list.resnum }</a>
				</c:if>
  			</td>
  			
  			<c:choose>
				<c:when test="${list.resnum eq 0}">
				<td>0%</td></c:when>
				<c:otherwise>
					<td>
						<fmt:formatNumber value="${list.resdonenum*100/list.resnum}" pattern="#.##"></fmt:formatNumber>%
					</td>
				</c:otherwise>
			</c:choose>
			
  		</tr>
  	</c:forEach>

  </table>
  </c:if>
  
  <c:if test="${isDept eq 'yes'}">
	
	<logic:present name="list">
	<logic:notEmpty name="list">
		 <display:table name="list" cellspacing="0" cellpadding="0"
			id="list" pagesize="${pageSize}" class="table list"
			export="false" 
			requestURI="inspectPlanGisAction.do"
			sort="list" partialList="true" size="${resultSize}" 
		>
			<display:column  title="部门"  >
				<eoms:id2nameDB id="${list.pnrDept}" beanId="tawSystemDeptDao" />
			</display:column>
			<c:choose>
				<c:when test="${empty list.resDoneNum or (list.resDoneNum eq 0 and list.resNum>0) }">
					<display:column  title="完成数" style="background-color:#FF0000" >
						0
					</display:column>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${list.resDoneNum eq list.resNum}">
							<display:column  title="完成数" style="background-color:#66FF66" >
								<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskListArcGis&pnrDept=${list.pnrDept}&isDone=yes" >${list.resDoneNum }</a>
							</display:column>
						</c:when>
						<c:otherwise>
							<display:column  title="完成数" style="background-color:#FFFF66" >
								<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskListArcGis&pnrDept=${list.pnrDept}&isDone=yes" >${list.resDoneNum }</a>
							</display:column>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			
			
			<display:column title="任务数" >
				<a href="${app }/partner/inspect/inspectPlanGisAction.do?method=inspectPlanGisTaskListArcGis&pnrDept=${list.pnrDept}&isDone=no" >${list.resNum }</a>
			</display:column>
		</display:table>
		</logic:notEmpty>
	</logic:present>
	</c:if>
  
  
	<br/>
	<table>
		<tr>
			<td><div style="background-color: #FF0000;width: 10px;height: 10px;"></div></td><td style="padding-right: 15px;">表示"未开始"</td>
			<td><div style="background-color: #FFFF66;width: 10px;height: 10px;"></div></td><td style="padding-right: 15px;">表示"进行中"</td>
			<td><div style="background-color: #66FF66;width: 10px;height: 10px;"></div></td><td style="padding-right: 15px;">表示"已完成"</td>
		</tr>
	</table>
 <c:if test="${backShow=='backShow' }">
 <input type="button" class="btn" value="返回"  onclick="javascript:history.back();" /> 
 </c:if>
<%@ include file="/common/footer_eoms.jsp"%>