<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<table class="formTable">
  	<caption><div class="header center">车辆信息列表</div></caption>
</table>
	<br>	<div>操作提示：点击车牌号可查看详细信息</div><br>
	<display:table name="carList" cellspacing="0" cellpadding="0"
		id="carList" pagesize="${pageSize}" class="table"
		export="false"	requestURI="${app}/partner/resourceInfo/car.do?method=searchInto"
		sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${carList.maintainCompany}"  beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${carList.area}"  beanId="tawSystemAreaDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="车牌号">
	<a href="${app}/partner/resourceInfo/car.do?method=detail&&id=${carList.id}">${carList.carNumber}</a>
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="车型">${carList.carType}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="车载GPS编号">${carList.carGPSNumber}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="车载GPS厂家">${carList.carGPSFactory}
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="车载GPS的SIM卡号">${carList.carGPSSimCardNumber}
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="车辆状态">
		<eoms:id2nameDB id="${carList.carStatus}"  beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="产权属性">
		<eoms:id2nameDB id="${carList.carProperty}"  beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="负责人/驾驶员">
		<eoms:id2nameDB id="${carList.driver}"  beanId="partnerUserDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="联系方式/驾驶员联系方式">${carList.driverContact}
	</display:column>	
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>