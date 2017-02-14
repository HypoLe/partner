<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<table class="formTable">
  	<caption><div class="header center">人员配置统计详细列表</div></caption>
</table>
	<br>	<div>操作提示：点击仪器仪表名称可查看详细信息</div><br>
	<display:table name="apparatusList" cellspacing="0" cellpadding="0"
		id="apparatusList" pagesize="${pageSize}" class="table" requestURI="${app}/partner/resourceInfo/apparatus.do?method=searchInto"
		export="false" sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${apparatusList.area}"  beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${apparatusList.maintenanceCompany}"  beanId="tawSystemDeptDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="仪器仪表名称">
	<a href="${app}/partner/resourceInfo/apparatus.do?method=detail&&id=${apparatusList.id }">
		<eoms:id2nameDB id="${apparatusList.apparatusName}"  beanId="ItawSystemDictTypeDao" /></a>
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="维护专业">
		<eoms:id2nameDB id="${apparatusList.maintenanceMajor}" beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="仪器仪表型号">${apparatusList.apparatusType}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="产品序列号">${apparatusList.apparatusSerialNumber}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="采购时间">${apparatusList.purchasingTime}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="出厂日期">${apparatusList.apparatusDateOfProduction}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="使用年限">${apparatusList.apparatusServiceLife}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="状态">
		<eoms:id2nameDB id="${apparatusList.apparatusStatus}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="产权属性">
		<eoms:id2nameDB id="${apparatusList.apparatusProperty}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="资产所属">${apparatusList.apparatusBelongs}
	</display:column>			
	
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>