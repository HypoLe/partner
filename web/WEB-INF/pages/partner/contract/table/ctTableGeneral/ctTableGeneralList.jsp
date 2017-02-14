<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page	import="com.boco.eoms.partner.contract.table.util.CtTableGeneralConstants" />

<script>
function search(){  
    var themeId = eoms.$('themeId').value;
    var queryCon=" where 1=1";
    if(themeId!=null&&themeId!=""){
       queryCon = queryCon + " and ctTableGeneral.themeId like '"+themeId+"%'";
    }
    var tableChname = eoms.$('tableChname').value;
     if(tableChname!=null&&tableChname!=""){
       queryCon=queryCon+" and ctTableGeneral.tableChname like '%"+tableChname+"%'";
    }
    var url='${app}/partner/contract/ctTableGenerals.do?method=searchX&whereStr=yy';
	location.href=url;
} 
</script>

	<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

	<html:form action="/ctTableGenerals.do?method=searchX" styleId="ctTableGeneralForm" method="post">

		<eoms:xbox id="tree"
			dataUrl="${app}/partner/contract/ctTableThemes.do?method=getUsedFirstTree"
			rootId="<%=CtTableGeneralConstants.TREE_ROOT_ID%>" rootText="合同分类"
			valueField="themeId" handler="themeName" textField="themeName"
			checktype="forums" single="true">
		</eoms:xbox>

		<table align="center">
			<tr>
				<td>
					<fmt:message key="ctTableGeneral.themeId" />：
				</td>
				<td class="content">
					<input type="text" id="themeName" name="themeName" class="text" readonly="readonly"
						value='<eoms:id2nameDB id="${ctTableGeneralForm.themeId}" beanId="ctTableThemeDao" />'
						alt="allowBlank:false'" />
					<input type="hidden" id="themeId" name="themeId" value="${ctTableGeneralForm.themeId}" />
				</td>
				<td>
					<fmt:message key="ctTableGeneral.tableChname" />：
				</td>
				<td>
					<input type="text" id="tableChname" name="tableChname" class="text" value="${ctTableGeneralForm.tableChname}">
				</td>
			</tr>
			<tr>
			<td>
					<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
					<input type="reset" class="btn"	value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
			</td>
			</tr>
		</table>
	</html:form>

	<content tag="heading">
	    <b>模型定义<fmt:message key="ctTableGeneral.list.heading" /></b>
	</content>

	<display:table name="ctTableGeneralList" cellspacing="0"
		cellpadding="0" id="ctTableGeneralList" pagesize="${pageSize}"
		class="table ctTableGeneralList" export="false"
		requestURI="${app}/partner/contract/ctTableGenerals.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column sortable="true" property="tableChname" titleKey="ctTableGeneral.tableChname"			
			paramId="id" paramProperty="id" 
			headerClass="sortable" href="${app}/partner/contract/ctTableGenerals.do?method=edit" />
			
		<display:column sortable="true" property="tableName" titleKey="ctTableGeneral.tableName"			
			paramId="id" paramProperty="id" 
			headerClass="sortable" href="${app}/partner/contract/ctTableGenerals.do?method=edit" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctTableGeneral.themeId">
			<eoms:id2nameDB id="${ctTableGeneralList.themeId}" beanId="ctTableThemeDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctTableGeneral.createUser">
			<eoms:id2nameDB id="${ctTableGeneralList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctTableGeneral.createDept">
			<eoms:id2nameDB id="${ctTableGeneralList.createDept}" beanId="tawSystemDeptDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctTableGeneral.createTime" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctTableGeneral.isOpen">
			<eoms:dict key="dict-partner-contract" dictId="isOrNot" itemId="${ctTableGeneralList.isOpen}" beanId="id2nameXML" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctTableGeneral.isCreated">
			<eoms:dict key="dict-partner-contract" dictId="isOrNot" itemId="${ctTableGeneralList.isCreated}" beanId="id2nameXML" />
		</display:column>

		<display:column titleKey="ctTableGeneral.layout" headerClass="imageColumn">
			<c:if test="${ctTableGeneralList.isCreated == 1}">
				<a href="javascript:if(confirm('确定要调整页面?')){
		                        var id = '${ctTableGeneralList.id }';
		                        var url='${app}/partner/contract/ctTableColumns.do?method=addLayout';
		                        url = url + '&TABLE_ID=' + id ;
		                        location.href=url}">
					<img src="${app}/images/icons/table.png" />
				</a>
			</c:if>
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="ctTableGeneral" />
		<display:setProperty name="paging.banner.items_name" value="ctTableGenerals" />
	</display:table>
	
	<br>
	
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/ctTableGenerals.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>