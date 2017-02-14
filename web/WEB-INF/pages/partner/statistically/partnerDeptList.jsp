<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page
	import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>




		<display:table name="partnerDeptList" cellspacing="0" cellpadding="0"
			id="partnerDeptList" pagesize="${pageSize}"
			class="table partnerDeptList" export="false"
			sort="page" partialList="true" size="${resultSize}">

			<display:column property="name" sortable="true"
				headerClass="sortable" title="合作伙伴名称"
				href="${app}/partner/baseinfo/aptitudes.do?method=newExpert&detail=detail"
				paramId="id" paramProperty="id" />

			<display:column property="manager" sortable="true"
				headerClass="sortable" title="合作伙伴法人" paramId="id"
				paramProperty="id" />

			<display:column property="areaName" sortable="true"
				headerClass="sortable" title="所属地市" paramId="id"
				paramProperty="id" />

			<display:column property="phone" sortable="true"
				headerClass="sortable" title="联系电话" paramId="id"
				paramProperty="id" />

			<display:column sortable="true" headerClass="sortable"
				title="合作伙伴资质" paramId="id" paramProperty="id">

				<eoms:id2nameDB id="${partnerDeptList.aptitude}"
					beanId="ItawSystemDictTypeDao" />
				</display:column>

			<display:column property="deadline" sortable="true"
				headerClass="sortable" title="资质有效期" paramId="id"
				paramProperty="id" />

		

		</display:table>



<%@ include file="/common/footer_eoms.jsp"%>