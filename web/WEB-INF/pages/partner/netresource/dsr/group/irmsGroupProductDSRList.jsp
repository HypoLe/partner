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
</script>

<div align="center">
	<b>直放站-数据同步-
	<font color="red">
		<c:if test="${synchType eq 'add'}">新增</c:if>
		<c:if test="${synchType eq 'delete'}">删除</c:if>
	</font>
	-列表页面</b>
</div><br><br/>

<!-- 同步结果开始 -->
 <div style="margin-top:20px;border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;">
 	最近一次同步时间：<font color="red">${dataSynchResult['datasynchTime']}</font>，本次同步共
		<c:if test="${synchType eq 'add'}">新增<font color="red">${dataSynchResult['dataSynchAddResultCount']}</font></c:if>
		<c:if test="${synchType eq 'delete'}">删除<font color="red">${dataSynchResult['dataSynchDeleteResultCount']}</font></c:if>
 	条数据。
 </div>
<!-- 同步结果结束 -->

<logic:present name="irmsGroupProductDSRList" scope="request">
	<display:table id="irmsGroupProductDSRList"
					name="irmsGroupProductDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsGroupProductDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="客户名称">
						${irmsGroupProductDSRList['customer_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户编号">
						${irmsGroupProductDSRList['customer_no']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务类型">
						<eoms:id2nameDB id="${irmsGroupProductDSRList['prod_type']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产品实例标识">
						${irmsGroupProductDSRList['prod_code']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务接入点地址A">
						${irmsGroupProductDSRList['customer_addressa']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入点A所属地市">
						${irmsGroupProductDSRList['citya']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入点A所属区县">
						${irmsGroupProductDSRList['regiona']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务接入点地址Z">
						${irmsGroupProductDSRList['customer_addressz']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入点Z所属地市">
						${irmsGroupProductDSRList['cityz']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户业务接入点Z所属区县">
						${irmsGroupProductDSRList['regionz']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户技术联系人">
						${irmsGroupProductDSRList['customer_link_man']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="客户技术联系人电话">
						${irmsGroupProductDSRList['customer_link_phon']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务描述">
						${irmsGroupProductDSRList['business_demand']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务开通日期">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务服务终止时间">
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业务保障等级">
						${irmsGroupProductDSRList['service_level']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="创建时间">
						${irmsGroupProductDSRList['create_time']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
