<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<%
	String operateType = StaticMethod.nullObject2String(request.getAttribute("operateType"));
	TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
	String operateUserId = sessionform.getUserid();
	String auditName = StaticMethod.nullObject2String(request.getAttribute("auditName"));
%>

<script type="text/javascript">
<% if(!"".equals(auditName)){ %>
    alert('该条记录已提交 <%=auditName%> 审核');
<% } %>

	function add(){
		var nodeId = '${KM_FILETREE_NODEID}';
		var create ='${create}';
		var url ='${app}/kmmanager/files.do?method=add&nodeId=' + nodeId;
		if(create=='0'){
			alert('您没有添加的权限!!')
		}else{
			location.href=url
		}
	}
</script>

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

	<content tag="heading">
	    <fmt:message key="kmFile.title" />&nbsp;>&nbsp;<eoms:id2nameDB id="${KM_FILETREE_NODEID}" beanId="kmFileTreeDao" />
	</content>

	<display:table name="fileList" cellspacing="0" cellpadding="0"
		id="fileList" pagesize="${pageSize}" class="table fileList"
		export="false"
		requestURI="${app}/kmmanager/files.do?method=search&nodeId=${KM_FILETREE_NODEID}"
		sort="list" partialList="true" size="resultSize">

		<display:column sortable="true" headerClass="sortable" titleKey="kmFile.fileName" 
		    paramId="id" paramProperty="id">
			<a href="${app}/kmmanager/files.do?method=detail&id=${fileList.id}&nodeId=${KM_FILETREE_NODEID}&operateType=<%=operateType%>">${fileList.fileName}</a>
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="kmFileTree.nodeName" 
		    paramId="id" paramProperty="id">
			<eoms:id2nameDB beanId="kmFileTreeDao" id="${fileList.nodeId}" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="kmFile.userId" 
		    paramId="id" paramProperty="id">
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${fileList.userId}" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="kmFile.uploadTime" 
			property="uploadTime" paramId="id" paramProperty="id" />

		<display:column sortable="true" headerClass="sortable" titleKey="kmFile.fileSize" 
		    paramId="id" paramProperty="id">
		    ${fileList.fileSize}&nbsp;KB
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="kmFile.fileGrade" 
		    paramId="id" paramProperty="id">
			<eoms:dict key="dict-kmmanager" dictId="fileGrade" itemId="${fileList.fileGrade}" beanId="id2nameXML" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="kmFile.clickCount" 
		    property="clickCount" paramId="id" paramProperty="id" />

		<display:column sortable="false" headerClass="sortable" titleKey="kmFile.allOperate">
			<!-- 增加权限判断 add by lvweihua 9 27 2009 -->
			<a href="javascript:if(confirm('确定要修改该文件?')){var mappingName='${fileList.id }';var nodeId='${KM_FILETREE_NODEID}'; 
									var modify='${modify}';
									if(modify=='0'){
										alert('您没有修改权限');
									}else{
										var url='${app}/kmmanager/files.do?method=edit&id=' + mappingName + '&nodeId=' + nodeId;location.href=url
									}}
									">
			    <fmt:message key="kmFile.update" />
			</a>&nbsp;&nbsp;
			<a href="javascript:if(confirm('确定要删除该文件?')){var mappingName='${fileList.id }';var nodeId='${KM_FILETREE_NODEID}';
									var del='${del}';
									if(del=='0'){
										alert('您没有删除权限');
									}else{
										var url='${app}/kmmanager/files.do?method=remove&id=' + mappingName + '&nodeId=' + nodeId;location.href=url
									}
									}">
			    <fmt:message key="kmFile.delete" />
			</a> 
			
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="file" />
		<display:setProperty name="paging.banner.items_name" value="files" />
	</display:table>
	
	<br><br>
	<!-- 增加权限1.0  add by lvweihua 9.27 -->
	<input type="button" class="btn"
		onclick="add()"
		value="<fmt:message key="button.add"/>" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>