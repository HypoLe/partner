<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm" %>
<%@page import="com.boco.eoms.base.util.StaticMethod" %>
 
<jsp:include page="search.jsp" />
<script type="text/javascript">
function check(){
	if(confirm('确定删除？')){
		return true;
	}else{
		return false;
	}
}
function addProgress(){
	var form = document.forms[0];
	form.action = '${app}/sheet/itrequirement/itrequirementprogress.do?method=showNewPage&itType=1';
	form.submit();
}
</script> 
 
<c:if test="${total!='0'}">
	<display:table name="itRequirementProgressList" cellspacing="0" cellpadding="0"
		id="itRequirementProgressList" pagesize="${pageSize}" class="table itRequirementProgressList"
		export="true"
		requestURI="${app}/sheet/itrequirement/itrequirementprogress.do?method=search"
		sort="list" partialList="true" size="total">
		
		<c:if test="${itType=='0'}">
			<display:column style="text-align:center;" sortable="true" headerClass="sortable" title="工单流水号"  media="html">
			<a href="${app}/sheet/itrequirement/itrequirement.do?method=showMainDetailPage&sheetKey=${itRequirementProgressList.mainId}" target="_blank">${itRequirementProgressList.sheetId}</a>
			</display:column>
			<display:column style="text-align:center;" sortable="true" headerClass="sortable" title="工单流水号"  media="csv excel xml pdf">
			 ${itRequirementProgressList.sheetId} 
			</display:column>
			<display:column  property="title" style="text-align:center;" sortable="false" headerClass="sortable" title="工单主题" />
		</c:if>
		<c:if test="${itType=='1'}">
			<display:column  property="title" style="text-align:center;" sortable="false" headerClass="sortable" title="需求主题" />
		</c:if>
		<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="需求提出部门">
			<eoms:id2nameDB id="${itRequirementProgressList.applyDeptId}" beanId="tawSystemDeptDao"/>
		</display:column>
		<c:if test="${itType=='0'}">
			<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="IT需求申请方负责人审批时间" >
				<bean:write name="itRequirementProgressList" property="auditTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="page"/>
			</display:column>
		</c:if>
		<c:if test="${itType=='1'}">
			<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="IT需求申请时间" >
				<bean:write name="itRequirementProgressList" property="applyTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="page"/>
			</display:column>	
		</c:if>	
		<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="需求分析回复时间">
			<bean:write name="itRequirementProgressList" property="replyTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="page"/>
		</display:column>
		<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="状态" >
			<eoms:id2nameDB id="${itRequirementProgressList.status}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="评估等级">
			<eoms:id2nameDB id="${itRequirementProgressList.itLevel}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="计划上线时间">
			<bean:write name="itRequirementProgressList" property="planUpTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="page"/>
		</display:column>
		<display:column  style="text-align:center;" sortable="false" headerClass="sortable" title="实际完成时间">
			<bean:write name="itRequirementProgressList" property="realCompleteTime" formatKey="date.formatDateTimeAll" bundle="sheet" scope="page"/>
		</display:column>	 
		<display:column  property="remark" style="text-align:center;" sortable="false" headerClass="sortable" title="备注" /> 
		 
    	<c:choose>
	    	<c:when test="${superAdminFlag =='1' ||operateUserId==itRequirementProgressList.applyUserId}"> <!-- 需求提出人及维护管理员角色 -->
			    <display:column  sortable="true"  headerClass="sortable" title="编辑"  media="html">
					<a href="${app}/sheet/itrequirement/itrequirementprogress.do?method=showNewPage&id=${itRequirementProgressList.id}&itType=${itRequirementProgressList.itType}"><img src="${app }/images/icons/edit.gif" width="19" height="19"></a>
				</display:column>
				<display:column  sortable="true"  headerClass="sortable" title="删除"  media="html">
					<a href="${app}/sheet/itrequirement/itrequirementprogress.do?method=deleteProgress&id=${itRequirementProgressList.id}&itType=${itRequirementProgressList.itType}" onclick="return check()"><img src="${app }/images/icons/icon.gif" width="23" height="30"></a>
			    </display:column>
			</c:when>	
		    <c:otherwise>
			    <display:column  sortable="true"  headerClass="sortable" title="编辑"  media="html">无权限</display:column>
				<display:column  sortable="true"  headerClass="sortable" title="删除"  media="html">无权限</display:column>
			</c:otherwise>
		</c:choose>	 		
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
		<display:setProperty name="export.rtf" value="false"/>				
	</display:table>
</c:if>
<c:if test="${total=='0'}">
没有记录
</c:if>	
<c:if test="${itType=='1'}">	
<input type="button" id="newpage" name="newpage" value="新增需求" class="btn" onclick="addProgress()">
 </c:if>
<%@ include file="/common/footer_eoms.jsp"%>