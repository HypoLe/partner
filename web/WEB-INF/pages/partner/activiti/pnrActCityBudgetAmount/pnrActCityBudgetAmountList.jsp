<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
Ext.onReady(function(){
	//地域树
    new xbox({
		btnId:'cityName',
			single : true,
		treeDataUrl:'${app}/xtree.do?method=area&areaMaxLevel=2',treeRootId:'28',treeRootText:'全省',treeChkType:'area',
		showChkFldId:'cityName',saveChkFldId:'cityId',returnJSON:false
	});
})
function openAddView(){
	window.location= "${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=addPnrActCityBudgetAmount";
}

//重置
		function newReset(){
			document.getElementById("budgetYear").value="";	        
			document.getElementById("budgetQuarter").value="";	      
			document.getElementById("cityName").value="";	       
			document.getElementById("cityId").value="";	        
		}
</script>

<div id="sheetform">
    <html:form action="/pnrActCityBudgetAmount.do?method=queryPnrActCityBudgetAmount" styleId="theform">
        <table class="formTable">
        <tr>
			<td class="label">
				年份*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-task" selectId="budgetYear"
					dictId="yearflag" beanId="selectXML" cls="select" defaultId="${budgetYear}"/>
			</td>
			<td class="label">
				季度*
			</td>
			<td class="content">
			<eoms:dict key="dict-partner-task" selectId="budgetQuarter"
					dictId="monthflag" beanId="selectXML" cls="select" defaultId="${budgetQuarter}"/>
			</td>
		</tr>
         <tr>
		<td class="label">
			地市*
		</td>
		<td class="content" colspan=3>
			<html:text property="cityName" styleId="cityName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${cityName}" />
			<html:hidden property="cityId" styleId="cityId" value="${cityId}"/>
		</td>
	    </tr>
        </table>
        <!-- buttons -->
        <div class="form-btns">
            <html:submit styleClass="btn" property="method.save" styleId="method.save">
                查询
            </html:submit>
           <html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
            
        </div>
    </html:form>
</div>

<display:table name="pnrActCityBudgetAmountList" cellspacing="0" cellpadding="0"
		id="pnrActCityBudgetAmountList" pagesize="${pageSize}" class="table pnrActCityBudgetAmountList"
		export="false"
		requestURI="${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=queryPnrActCityBudgetAmount"
		sort="list" partialList="true" size="${total}">
		
		<display:column property="cityName" sortable="false" headerClass="sortable" title="地市"  />
		<display:column property="budgetYear" sortable="false" headerClass="sortable" title="年份"  />
		<display:column sortable="false" headerClass="sortable" title="季度">
			<c:if test="${pnrActCityBudgetAmountList.budgetQuarter eq '01'}">
				第一季度
			</c:if>
			<c:if test="${pnrActCityBudgetAmountList.budgetQuarter eq '04'}">
				第二季度
			</c:if>
			<c:if test="${pnrActCityBudgetAmountList.budgetQuarter eq '07'}">
				第三季度
			</c:if>
			<c:if test="${pnrActCityBudgetAmountList.budgetQuarter eq '10'}">
				第四季度
			</c:if>
		</display:column>
		<display:column  sortable="false" headerClass="sortable" title="金额">
			<fmt:formatNumber  value="${pnrActCityBudgetAmountList.budgetAmount}" pattern="##.##" maxFractionDigits="8" minFractionDigits="0"/>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="处理">			
			<a href="${app}/activiti/pnrTransferPrecheck/pnrActCityBudgetAmount.do?method=updatePnrActCityBudgetAmount&id=${pnrActCityBudgetAmountList.id}"
					title="修改"> 修改 </a>
		</display:column> 
</display:table>
<!-- 
<table>
	<tr>
		<td>
			<input type="button" class="btn" value="添加" onclick="openAddView();"/>
		</td>
	</tr>
</table>
 -->
<%@ include file="/common/footer_eoms.jsp"%>