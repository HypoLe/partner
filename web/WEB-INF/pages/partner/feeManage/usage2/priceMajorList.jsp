<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>







	<display:table name="majorList" cellspacing="0" cellpadding="0"
		id="majorList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		sort="list" partialList="true"
		>
		<display:column property="majorId"  title="字典值" />

		<display:column property="name" title="专业名" />
			
			
		<display:column title="修改" 
		  url="/partner/feeManageUsage2/usage2.do?method=priceMajorEdit"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/edit.gif"/>
			
		</display:column>
		<display:column title="管理下属计次类型" 
		  url="/partner/feeManageUsage2/usage2.do?method=cntTypList"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column  title="删除"
				url="/partner/feeManageUsage2/usage2.do?method=deletePriceMajor" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>


<br />

<table class="table">
  <tr>
    <td><a href="${app}/partner/feeManageUsage2/usage2.do?method=priceMajorEdit"><input type="button" value="新增"/></a></td>
  </tr>
</table>





<%@ include file="/common/footer_eoms.jsp"%>