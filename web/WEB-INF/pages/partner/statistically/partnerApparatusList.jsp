<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>




		<display:table name="tawApparatusList" cellspacing="0" cellpadding="0"
		id="tawApparatusList" pagesize="${pageSize}" decorator="com.boco.eoms.partner.baseinfo.util.PartnerApparatusDecorator"
		class="table tawApparatusList" export="false"
		requestURI="${app}/partner/baseinfo/tawApparatuss.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column property="apparatusName" sortable="true"
			headerClass="sortable" title="仪器名"/>

		<display:column sortable="true" headerClass="sortable"
			title="生产厂家">
				<eoms:id2nameDB id="${tawApparatusList.factory}"
					beanId="ItawSystemDictTypeDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable"
			title="仪器类型">
				<eoms:id2nameDB id="${tawApparatusList.type}"
					beanId="ItawSystemDictTypeDao" />
		</display:column>

		<display:column property="model" sortable="true"
			headerClass="sortable" title="仪器型号"
			paramId="id" paramProperty="id" />

		<display:column sortable="true" headerClass="sortable"
			title="运行状态">
				<eoms:id2nameDB id="${tawApparatusList.state}"
					beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column property="dept_id" sortable="true"
			headerClass="sortable" title="所属公司"
			paramId="id" paramProperty="id" />

		<display:column property="area_id" sortable="true"
			headerClass="sortable" title="所属城市"
			paramId="id" paramProperty="id" />
		
	</display:table>



<%@ include file="/common/footer_eoms.jsp"%>