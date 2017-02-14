<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>







    <input type="hidden" id="parentId" name="parentId"  value="${parentId}"/>
	<display:table name="cntTypList" cellspacing="0" cellpadding="0"
		id="cntTypList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		sort="list" partialList="true"
		>
		<display:column property="cntTypId"  title="字典值" />

		<display:column property="name" title="专业名" />
			
			
		<display:column title="修改" 
		  url="/partner/feeManageUsage2/usage2.do?method=cntTypEdit"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/edit.gif"/>
			
		</display:column>
		<display:column title="管理下属过滤字段" 
		  url="/partner/feeManageUsage2/usage2.do?method=prcFilterList"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column  title="删除"
				url="/partner/feeManageUsage2/usage2.do?method=deleteCntTyp" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>


<br />

<table class="table">
  <tr>
    <td><a href="${app}/partner/feeManageUsage2/usage2.do?method=cntTypEdit&parentId=${parentId}"><input type="button" value="新增"/></a></td>
  </tr>
</table>





<%@ include file="/common/footer_eoms.jsp"%>