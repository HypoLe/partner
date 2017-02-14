<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<content tag="heading">网调信息 列表</content>
	
<html:form action="/attempers.do?method=list" styleId="attemperForm" method="post"> 
<html:hidden name="attemperForm" property="strutsAction" />

<table class="formTable">
	<tr>
		<td class="label">
			专业名称
			<eoms:dict key="dict-duty" dictId="attemperSpeciality" defaultId="${attemperForm.speciality}" beanId="selectXML" selectId="speciality"/>
			&nbsp;&nbsp;&nbsp;设备所属部门
			<eoms:comboBox name="netDeptList" id="netDeptList" defaultValue="${attemperForm.netDeptList}" initDicId="1040201"/> 
			&nbsp;&nbsp;&nbsp;状态
			<eoms:dict key="dict-duty" dictId="attemperStatus2" defaultId="${attemperForm.status}" beanId="selectXML" selectId="status"/>
			&nbsp;&nbsp;&nbsp;时间段
			<eoms:dict key="dict-duty" dictId="attemperDays" defaultId="${attemperForm.days}" beanId="selectXML" selectId="days"/>
			&nbsp;&nbsp;&nbsp;<input type="submit" class="btn" value="查询" />
		</td>
	</tr>
</table>
</html:form>

	<display:table name="attemperList" cellspacing="0" cellpadding="0"
		id="attemperList" pagesize="${pageSize}" class="table attemperList"
		export="true"
		requestURI="${app}/duty/attempers.do?method=list"
		sort="list" partialList="true" size="resultSize">
	<display:column property="sheetId" sortable="true" 
			headerClass="sortable" title="网调信息编号" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id"/>
	
	<display:column property="title" sortable="true" 
			headerClass="sortable" title="主题" href="${app}/duty/attempers.do?method=deal" paramId="id" paramProperty="id" maxLength="30"/>
	
	<display:column property="beginTime" sortable="true" 
			headerClass="sortable" title="开始时间" paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" title="专业名称" >
		<eoms:dict key="dict-duty" dictId="attemperSpeciality" itemId="${attemperList.speciality}" beanId="id2nameXML" />
    </display:column>
    
	<display:column sortable="true" headerClass="sortable" title="状态" >
  		<eoms:dict key="dict-duty" dictId="attemperStatus" itemId="${attemperList.status}" beanId="id2nameXML" />
    </display:column>
    
    <display:column property="subNum" sortable="true" 
			headerClass="sortable" title="子过程数量" paramId="id" paramProperty="id"/>

	<display:column property="ifSubFinish" sortable="true" 
			headerClass="sortable" title="未结束数目" paramId="id" paramProperty="id"/>
	
	<display:column property="planSheetId" sortable="true"
			headerClass="sortable" title="来文编号"  paramId="id" paramProperty="id"/>
			
	<display:column property="recordUser" sortable="true"
			headerClass="sortable" title="联系人和联系方式"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" title="发起部门" >
     		<eoms:id2nameDB id="${attemperList.recordDept}" beanId="tawSystemDictTypeDao" />
    </display:column>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" title="结束时间"  paramId="id" paramProperty="id"/>

	<display:column property="netNames" sortable="true"
			headerClass="sortable" title="涉及网元"  paramId="id" paramProperty="id"/>

	<display:column property="netDeptName" sortable="true"
			headerClass="sortable" title="设备所属部门"  paramId="id" paramProperty="id"/>
	
	
		<display:setProperty name="paging.banner.item_name" value="attemper" />
		<display:setProperty name="paging.banner.items_name" value="attempers" />
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>
		<display:setProperty name="export.rtf" value="false"/>
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

<%@ include file="/common/footer_eoms.jsp"%>