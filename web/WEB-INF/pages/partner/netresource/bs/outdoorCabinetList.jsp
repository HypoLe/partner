<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

<script type="text/javascript">
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

function openExcelImport(){
	var handler = document.getElementById("openExcelHandle");
	var el = Ext.get('listExcelPanel');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开手动同步界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭手动同步界面";
	}
}

</script>
<div align="center"><b>室外箱体-列表页面</b></div><br><br/>

<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery">查询</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;display:none">
	<form action="${app}/netresource/bsbt/pnrbsbt.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.OutdoorCabinet" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
						<tr>
						<td class="label">
						 	放置点名称
						</td>
						<td class="content" >
								<input class="text" type="text" name="outCabinetNameStringLike" value="${outCabinetNameStringLike}" />
						</td>
						<td class="label">
						 	机房位置
						</td>
						<td class="content" >
								<input class="text" type="text" name="ocAddressStringLike" value="${ocAddressStringLike}" />
						</td>
						
						
						</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app}/netresource/bsbt/pnrbsbt.do?method=gotoListPage&model=com.boco.eoms.partner.netresource.model.bs.OutdoorCabinet'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>

<!--导出功能开始-->
<%--
<eoms:excelExport modelName="com.boco.eoms.partner.netresource.model.bs.IrmsBsBtssite" 
							 serviceBeanId="eomsService"   title="网络资源--基站设备及配套--BTSSITE" >
		<eoms:row name="OMC中网元名称" value="userlabelCm"/>
		<eoms:row name="中文名称" value="labelCn"/>
		<eoms:row name="所属BSC" value="relatedBsc"/>
		<eoms:row name="移动通信系统标识" value="freBand"/>
		<eoms:row name="传输类型" value="transmode"/>
		<eoms:row name="基站应急类型" value="btssiteCrisisType"/>
		<eoms:row name="VIP基站" value="vipType"/>
		<eoms:row name="所属机房" value="relatedRoom"/>
		<eoms:row name="蜂窝类型" value="beehiveType"/>
		<eoms:row name="基站编号" value="btssiteNo"/>
		<eoms:row name="设备型号" value="model"/>
		<eoms:row name="故障受理单位(统一考虑)" value="failAccUnit"/>
		<eoms:row name="代维公司（统一考虑）" value="manageCompany"/>
		<eoms:row name="网元状态" value="status"/>
		<eoms:row name="设备供应商" value="relatedVendor"/>
		<eoms:row name="软件版本信息" value="softVersion"/>
		<eoms:row name="开通时间" value="openTime"/>
</eoms:excelExport>
--%>
<!--导出功能结束-->

<logic:present name="outdoorCabinetList" scope="request">
	<display:table id="outdoorCabinetList"
					name="outdoorCabinetList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/bsbt/pnrbsbt.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsBtssiteList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="放置点名称">
						${outdoorCabinetList.outCabinetName}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所在地市">
						<eoms:id2nameDB id="${outdoorCabinetList.city}" beanId="tawSystemAreaDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所在区域">
						<eoms:id2nameDB id="${outdoorCabinetList.country}" beanId="tawSystemAreaDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护区域">
						${outdoorCabinetList.maintenanceArea}
				</display:column>		
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
