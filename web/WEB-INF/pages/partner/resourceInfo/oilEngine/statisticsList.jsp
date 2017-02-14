<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<table class="formTable">
  	<caption><div class="header center">油机信息列表	</div></caption>
</table>
	<br>	<div>操作提示：点击油机编号可查看详细信息</div><br>
	<display:table name="oilEngineList" cellspacing="0" cellpadding="0" 	id="oilEngineList" pagesize="${pageSize}" class="table"
		export="false" 	sort="list" partialList="true" size="${resultSize}" requestURI="${app}/partner/resourceInfo/oilEngine.do?method=searchInto">
		
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${oilEngineList.maintainCompany}"  beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${oilEngineList.area}"  beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="油机编号">
	<a href="${app}/partner/resourceInfo/oilEngine.do?method=detail&&id=${oilEngineList.id}">${oilEngineList.oilEngineNumber}</a>
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="规格型号">${oilEngineList.oilEngineModel}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="额定功率(KW)">
		${oilEngineList.powerRating}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="标准油耗(L/小时)">${oilEngineList.standardFuelConsumption}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="油机类型">
		<eoms:id2nameDB id="${oilEngineList.oilEngineType}"  beanId="ItawSystemDictTypeDao"/>
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="燃料种类">
		<eoms:id2nameDB id="${oilEngineList.fuleType}" beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="产权归属">
		<eoms:id2nameDB id="${oilEngineList.oilEngineProperty}"  beanId="ItawSystemDictTypeDao"/>
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="油机状态">
		<eoms:id2nameDB id="${oilEngineList.oilEngineStatus}"  beanId="ItawSystemDictTypeDao"/>
	</display:column>
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>