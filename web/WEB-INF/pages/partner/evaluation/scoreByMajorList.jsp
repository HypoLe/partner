<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<table id="sheet" class="formTable">
	<c:if test="${not empty evaluationEntityList}">
			<td colspan="4">
				<div class="ui-widget-header">
					等待打分列表
				</div>
			</td>
	</c:if>

   <logic:present name="list" scope="request">
    <display:table name="list" cellspacing="0" cellpadding="0"
		id="listEL" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/evaluation/evaluation.do?method=gotoScoreByMajorList"
		sort="list" partialList="true" size="${size}">
		<display:column property="year"   
			headerClass="sortable" title="考核年份" />
		<display:column property="month"   
			headerClass="sortable" title="考核月份" />
		<display:column    
			headerClass="sortable" title="考核专业" >
			<eoms:id2nameDB id="${listEL.zydictid}" beanId="ItawSystemDictTypeDao"/>
			</display:column>
		<display:column  
			headerClass="sortable" title="考核模板">
			<a href="${app}/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId=${listEL.usedtemplateid}" target="_blank">
				${listEL.usedtemplatename}
			</a>
		</display:column>
		<display:column   headerClass="sortable" title="被考核单位">
			<eoms:id2nameDB id="${listEL.evaluationtarget}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column   headerClass="sortable" title="考核发起人">
		    <eoms:id2nameDB id="${listEL.initate}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column   headerClass="sortable" title="考核执行人">
		    <eoms:id2nameDB id="${listEL.executor}" beanId="tawSystemUserDao"/>
		</display:column>		
		<display:column  
			headerClass="sortable" title="打分">
			<a href="${app}/partner/evaluation/evaluationEntity.do?method=gotoScoreByMajorTab&id=${listEL.ownentityid}&zydictid=${listEL.zydictid }">考核打分</a>
		</display:column> 					
    </display:table>                
 </logic:present>     

</table>

<%@ include file="/common/footer_eoms.jsp"%>