<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>








	<display:table name="entityList" cellspacing="0" cellpadding="0"
		id="entityList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		requestURI="quotaToImpl.do?method=list" sort="list" partialList="true"
		>
		<display:column property="name"  title="名称" />
		<display:column property="className"  title="类全名" />
		<display:column title="类说明">
		  <pre>${entityList.remark }</pre>
		</display:column>
			
			
		<display:column title="编辑" 
		  url="/partner/evaluation/quotaToImpl.do?method=goToEdit"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column  title="删除"
				url="/partner/evaluation/quotaToImpl.do?method=delete" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>


<br />






<%@ include file="/common/footer_eoms.jsp"%>