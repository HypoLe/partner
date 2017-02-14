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

<logic:present name="irmsBsJzwdDSRList" scope="request">
	<display:table id="irmsBsJzwdDSRList"
					name="irmsBsJzwdDSRList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrNetResourceAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table irmsBsJzwdDSRList" partialList="true" >
				<display:column sortable="true" headerClass="sortable" title="所属省份">
						${irmsBsJzwdDSRList['related_province']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属地市">
						${irmsBsJzwdDSRList['related_city']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属区县">
						${irmsBsJzwdDSRList['related_county']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="所属站点">
						${irmsBsJzwdDSRList['related_site']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机楼（基站）名称">
						${irmsBsJzwdDSRList['related_site_name']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机楼（基站）编号">
						${irmsBsJzwdDSRList['related_site_no']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="产权属性">
						<eoms:id2nameDB id="${irmsBsJzwdDSRList['property']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="重要级别">
						<eoms:id2nameDB id="${irmsBsJzwdDSRList['important_level']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="机楼(基站)地址">
						${irmsBsJzwdDSRList['room_addr']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="变电站一">
						${irmsBsJzwdDSRList['one_substation']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="变电站二">
						${irmsBsJzwdDSRList['two_substation']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="维护负责人">
						${irmsBsJzwdDSRList['preserver']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="市电容量(KVA)">
						${irmsBsJzwdDSRList['utility_cpacity']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="市电引入方式">
						<eoms:id2nameDB id="${irmsBsJzwdDSRList['utility_mode']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="线径(m)">
						${irmsBsJzwdDSRList['line']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="线缆长度(m)">
						${irmsBsJzwdDSRList['cables_length']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供电性质">
						<eoms:id2nameDB id="${irmsBsJzwdDSRList['elect_kind']}" beanId="ItawSystemDictTypeDao"/>
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="转供信息">
						${irmsBsJzwdDSRList['turn_info']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="海拔高度">
						${irmsBsJzwdDSRList['height']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="经度">
						${irmsBsJzwdDSRList['longitude']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="纬度">
						${irmsBsJzwdDSRList['latitude']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="供电合同号">
						${irmsBsJzwdDSRList['elect_contract']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="变电站电话">
						${irmsBsJzwdDSRList['substation_tel']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业主联系人">
						${irmsBsJzwdDSRList['owner_linkman']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="查询联系人">
						${irmsBsJzwdDSRList['query_linkman']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="业主电话">
						${irmsBsJzwdDSRList['owner_linktel']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="查询电话">
						${irmsBsJzwdDSRList['query_linktel']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="动力代维">
						${irmsBsJzwdDSRList['power_maint']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="传输代维">
						${irmsBsJzwdDSRList['trans_maint']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="环境代维">
						${irmsBsJzwdDSRList['environment_maint']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="消防代维">
						${irmsBsJzwdDSRList['fire_maint']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="空调代维">
						${irmsBsJzwdDSRList['air_condition_main']}
				</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
