<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/attempers.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">

<content tag="heading">
	<fmt:message key="attemper.list.heading" />
</content>

	<display:table name="attemperList" cellspacing="0" cellpadding="0"
		id="attemperList" pagesize="${pageSize}" class="table attemperList"
		export="false"
		requestURI="${app}/duty/attempers.do?method=list"
		sort="list" partialList="true" size="resultSize">
    
    <display:column sortable="true" headerClass="sortable" titleKey="attemper.deptId" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:id2nameDB id="${attemperList.deptId}" beanId="tawSystemDeptDao" />
       </html:link>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="attemper.cruser" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:id2nameDB id="${attemperList.cruser}" beanId="tawSystemUserDao" />
       </html:link>
    </display:column>
   
	<display:column property="crtime" sortable="true"
			headerClass="sortable" titleKey="attemper.crtime" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="sheetId" sortable="true"
			headerClass="sortable" titleKey="attemper.sheetId" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="planSheetId" sortable="true"
			headerClass="sortable" titleKey="attemper.planSheetId" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="attemper.roomId" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:id2nameDB id="${attemperList.roomId}" beanId="tawSystemCptroomDao" />
       </html:link>
    </display:column>
    
	<display:column property="recordUser" sortable="true"
			headerClass="sortable" titleKey="attemper.recordUser" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="attemper.recordDept" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:id2nameDB id="${attemperList.recordDept}" beanId="tawSystemDictTypeDao" />
       </html:link>
    </display:column>

	<display:column property="recordContace" sortable="true"
			headerClass="sortable" titleKey="attemper.recordContace" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="recordDeptName" sortable="true"
			headerClass="sortable" titleKey="attemper.recordDeptName" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="contact" sortable="true"
			headerClass="sortable" titleKey="attemper.contact" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="beginTime" sortable="true"
			headerClass="sortable" titleKey="attemper.beginTime" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" titleKey="attemper.endTime" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="timeLong" sortable="true"
			headerClass="sortable" titleKey="attemper.timeLong" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="attemper.speciality" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
       </html:link>
    </display:column>
    
    <display:column sortable="true" headerClass="sortable" titleKey="attemper.subSpeciality" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:dict key="dict-duty" dictId="attemperSpecialitySub" itemId="${attemperList.subSpeciality}" beanId="id2nameXML" />
       </html:link>
    </display:column>

	<display:column property="isAppEffect" sortable="true"
			headerClass="sortable" titleKey="attemper.isAppEffect" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="effectOperation" sortable="true"
			headerClass="sortable" titleKey="attemper.effectOperation" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="netNames" sortable="true"
			headerClass="sortable" titleKey="attemper.netNames" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="netDeptName" sortable="true"
			headerClass="sortable" titleKey="attemper.netDeptName" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="adjustReason" sortable="true"
			headerClass="sortable" titleKey="attemper.adjustReason" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="title" sortable="true"
			headerClass="sortable" titleKey="attemper.title" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="content" sortable="true"
			headerClass="sortable" titleKey="attemper.content" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="remark" sortable="true"
			headerClass="sortable" titleKey="attemper.remark" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="delReason" sortable="true"
			headerClass="sortable" titleKey="attemper.delReason" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="attemper.status" >
		<html:link href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" paramName="attemperList">
     		<eoms:dict key="dict-duty" dictId="attemperStatus" itemId="${attemperList.status}" beanId="id2nameXML" />
       </html:link>
    </display:column>

	<display:column property="holdTime" sortable="true"
			headerClass="sortable" titleKey="attemper.holdTime" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="finishUser" sortable="true"
			headerClass="sortable" titleKey="attemper.finishUser" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="serial" sortable="true"
			headerClass="sortable" titleKey="attemper.serial" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="deleted" sortable="true"
			headerClass="sortable" titleKey="attemper.deleted" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="ifSubFinish" sortable="true"
			headerClass="sortable" titleKey="attemper.ifSubFinish" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>

	<display:column property="subNum" sortable="true"
			headerClass="sortable" titleKey="attemper.subNum" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>
	
		<display:setProperty name="paging.banner.item_name" value="attemper" />
		<display:setProperty name="paging.banner.items_name" value="attempers" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>