<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<table class="formTable">
  	<caption><div class="header center">移动终端信息列表	</div></caption>
</table>
	<br>	<div>操作提示：点击设备编号可查看详细信息</div><br>
	<display:table name="mobileTerminalList" cellspacing="0" cellpadding="0"
		id="mobileTerminalList" pagesize="${pageSize}" class="table" requestURI="${app}/partner/resourceInfo/mobileTerminal.do?method=searchInto"
		export="false"	sort="list" partialList="true" size="${resultSize}">
	<display:column  sortable="true" headerClass="sortable" title="代维公司">
		<eoms:id2nameDB id="${mobileTerminalList.maintainCompany}"  beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="区域">
		<eoms:id2nameDB id="${mobileTerminalList.area}"  beanId="tawSystemAreaDao" />
	</display:column>
	<display:column  sortable="true" headerClass="sortable" title="维护组">
		<eoms:id2nameDB id="${mobileTerminalList.maintainTeam}"  beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="设备编号">
	<a href="${app}/partner/resourceInfo/mobileTerminal.do?method=detail&&id=${mobileTerminalList.id}">${mobileTerminalList.mobileTerminalNumber}</a>
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="设备序列号">${mobileTerminalList.mobileTerminalSerilNumber}
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="手机卡号">${mobileTerminalList.simCardNumber}
	</display:column>			
	<%--<display:column  sortable="true" headerClass="sortable" title="SIM类型">
		<eoms:id2nameDB id="${mobileTerminalList.simCardType}" beanId="ItawSystemDictTypeDao" />
	</display:column>			
	<display:column  sortable="true" headerClass="sortable" title="设备识别码">${mobileTerminalList.identificationCode}
	</display:column>	
	--%>
	<display:column  sortable="true" headerClass="sortable" title="终端类型">
		<eoms:id2nameDB id="${mobileTerminalList.mobileTerminalType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>	
	<display:column  sortable="true" headerClass="sortable" title="生产厂家">${mobileTerminalList.mobileTerminalFactory}
	</display:column>	
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>