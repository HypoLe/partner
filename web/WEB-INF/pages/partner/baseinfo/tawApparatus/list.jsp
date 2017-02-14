<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style type="text/css">
.small {
	width: 10px;
}
</style>

<!-- Information hints area end-->
<logic:present name="tawApparatusList" scope="request">

	<display:table name="tawApparatusList" cellspacing="0" cellpadding="0"
		id="tawApparatusList" pagesize="${resultSize}"
		class="table tawApparatusList" export="true" size="${resultSize}"
		requestURI="apparatuss.do" sort="list" partialList="true">

		<display:column property="apparatusId" sortable="true"
			headerClass="sortable" title="仪器仪表编号">
			${tawApparatusList.apparatusId}
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="仪器仪表名称">
			${tawApparatusList.apparatusName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="生产厂商">
			<eoms:id2nameDB id="${tawApparatusList.factory}"
				beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="类型">
			<eoms:id2nameDB id="${tawApparatusList.type}"
				beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="型号">
			${tawApparatusList.model}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="运行状态">
			<eoms:id2nameDB id="${tawApparatusList.state}"
				beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属公司">
			<eoms:id2nameDB id="${tawApparatusList.partnerid}"
				beanId="partnerDeptDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
			<eoms:id2nameDB id="${tawApparatusList.partnerid}"
				beanId="partnerDeptAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="是否可调配">
			<eoms:id2nameDB id="${tawApparatusList.whetherallocate}"
				beanId="ItawSystemDictTypeDao" />
		</display:column>

		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${tawApparatusList.id }"
				href="${app}/partner/baseinfo/tawApparatuss.do?method=tawApparatusStaffDetail&id=${tawApparatusList.id}"
				target="blank" shape="rect"> <img
				src="${app }/images/icons/search.gif"> </a>
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
