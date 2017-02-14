<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>

<script type="text/javascript">
 function returnAll(){
	 location.href='${app}/partner/statistically/paternerStaff.do?method=goToShowPage';
	 }
</script>



<display:table name="staffList" cellspacing="0" cellpadding="0"
		id="partnerUserList" pagesize="${pageSize}" class="table partnerUserList"	export="false"
		requestURI="${app}/partner/statistically/paternerStaffSearch.do?method=list"
		sort="list" partialList="true" size="${resultSize}">

	<display:column property="name" sortable="true"      
			headerClass="sortable" title="人员姓名" href="${app}/partner/baseinfo/resumes.do?method=newExpert&nodeId=${nodeId}" paramId="id" paramProperty="id"/>
	
	<display:column  sortable="true"
			headerClass="sortable" title="性别"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${partnerUserList.sex}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>		

	<display:column property="areaName" sortable="true"
			headerClass="sortable" title="所属地市"  paramId="id" paramProperty="id"/>

	<display:column property="personCardNo" sortable="true"
			headerClass="sortable" title="身份证号"  paramId="id" paramProperty="id"/>
			
	<display:column property="deptName" sortable="true"
			headerClass="sortable" title="所属公司"  paramId="id" paramProperty="id"/>

	<display:column property="phone" sortable="true"
			headerClass="sortable" title="联系电话"  paramId="id" paramProperty="id"/>

	<display:column property="email" sortable="true"
			headerClass="sortable" title="电子邮件"  paramId="id" paramProperty="id"/>
	
	<display:column  sortable="true"
			headerClass="sortable" title="岗位信息"  >
				<eoms:id2nameDB id="${partnerUserList.post}" beanId="ItawSystemDictTypeDao"/> 
	</display:column>			
				
	</display:table>
<input type="button" onclick="returnAll()" value="返回" class="btn"/>
<%@ include file="/common/footer_eoms.jsp"%>