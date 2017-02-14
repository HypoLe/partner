<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>




		<display:table name="tawPartnerCarList" cellspacing="0"
			cellpadding="0" id="tawPartnerCarList" pagesize="${pageSize}"
			class="table tawPartnerCarList" export="false"
			decorator="com.boco.eoms.partner.baseinfo.util.PartnerCarDecorator"
			requestURI="${app}/partner/baseinfo/tawPartnerCars.do?method=search"
			sort="list" partialList="true" size="resultSize">
			<display:column property="car_number" sortable="true"
				headerClass="sortable" title="车牌号"
				href="${app}/partner/baseinfo/tawPartnerCars.do?method=detail"
				paramId="id" paramProperty="id" />

			<display:column property="dept_id" sortable="true"
				headerClass="sortable" title="所属公司" />

			<display:column property="area_id" sortable="true"
				headerClass="sortable" title="所属地市" />

			<display:column property="start_time" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" headerClass="sortable"
				title="开始使用时间" />
			<display:column property="remark" sortable="true"
				headerClass="sortable" title="备注" />

		</display:table>



<%@ include file="/common/footer_eoms.jsp"%>