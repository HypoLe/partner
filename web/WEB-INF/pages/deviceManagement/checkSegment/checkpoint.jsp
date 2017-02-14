<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>




<!-- Information hints area end-->
<logic:present name="checkPointList" scope="request">
	<display:table name="checkPointList" cellspacing="0" cellpadding="0"
		id="checkPointList" pagesize="${pagesize}"
		class="table checkPointList" export="true"
		requestURI="checkpoint.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="资源点编码">
			${checkPointList.resourceCode}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="资源点名称">
			${checkPointList.resourceName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="类型">
			<eoms:id2nameDB id="${checkPointList.type}" beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="地址">
			${checkPointList.address}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="经度">
			${checkPointList.longitude}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="纬度">
			${checkPointList.latitude}
		</display:column>
		<%-- 
		<display:column sortable="true" headerClass="sortable" title="所属光缆段">
			${checkPointList.cableSegment}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属光缆系统">
			${checkPointList.cableSystem}
		</display:column>
		--%> 
		<display:column sortable="true" headerClass="sortable" title="所属巡检段">
			<eoms:id2nameDB id="${checkPointList.checkPointSegmentId}" beanId="checkSegmentDao" />
		</display:column>
		<%--
		<display:column sortable="true" headerClass="sortable" title="重要关注点">
			${checkPointList.importantFocus}
		</display:column>---%>
		<display:column sortable="true" headerClass="sortable" title="是否为检查点">
			<eoms:id2nameDB id="${checkPointList.isCheckPoint }" beanId="IItawSystemDictTypeDao" />
		</display:column>
		
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${checkPointList.id }"
				href="${app }/checkpoint/checkpoint.do?method=goToDetail&id=${checkPointList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<table>
	<tr>
		<td>
		<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
		</td>
	</tr>
</table>



<%@ include file="/common/footer_eoms.jsp"%>
