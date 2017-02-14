<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>






<logic:present name="feePoolMeteringList" scope="request">
 <table class="formTable">
	<caption>
	<div class="header center">计次类型列表</div>
	</caption></table>
	<display:table name="feePoolMeteringList" cellspacing="0" cellpadding="0"
	    requestURI="${app}/partner/feeManage/feeMetering.do?method=meteringList"
		id="feePoolMeteringList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		sort="list" partialList="true"
		>
		
		<display:column sortable="false" headerClass="sortable" title="计次类型名称">
			${feePoolMeteringList.meteringName}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="专业">
		<eoms:id2nameDB id="${feePoolMeteringList.major}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>
		
		<display:column title="修改" 
		  url="/partner/feeManage/feeMetering.do?method=addMetering"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/edit.gif"/>
			
		</display:column>
		<display:column title="管理计次指标" 
		  url="/partner/feeManage/feeMetering.do?method=meteringIndexList"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column  title="删除"
				url="/partner/feeManage/feeMetering.do?method=deleteMetering" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>
</logic:present>

<br />
<form action="${app}/partner/feeManage/feeMetering.do?method=addMetering"
	method="post" id="meteringForm" name="meteringForm"/>

    <tr>
		<td class="label">
		</td>
		<td>
			<input type="submit" value="新增计次类型"  />
		</td>
	</tr>
</form>






<%@ include file="/common/footer_eoms.jsp"%>