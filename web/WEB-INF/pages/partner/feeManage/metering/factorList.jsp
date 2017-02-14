<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<script type="text/javascript">
 function returnBack(){
  document.getElementById("aFrom").submit();
	//	window.history.back();
	}
 function subMeteringForm(){
  document.getElementById("meteringForm").submit();
	}
	
</script>


    
	<logic:present name="feePoolFactorList" scope="request">
    <table class="formTable">
	<caption>
	<div class="header center">计次指标值列表</div>
	</caption></table>
	<display:table name="feePoolFactorList" cellspacing="0" cellpadding="0"
		id="feePoolFactorList"  pagesize="${pagesize}"
		requestURI="${app}/partner/feeManage/feeMetering.do?method=factorList"
	
		class="table" export="false"  size="${size}"
		sort="list" partialList="true"
		>
			
		<display:column sortable="false" headerClass="sortable" title="计次类型">
			${feePoolFactorList.meteringName}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="专业">
		<eoms:id2nameDB id="${feePoolFactorList.major}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>
		
		
		<display:column sortable="false" headerClass="sortable" title="指标名称">
			${feePoolFactorList.indexName}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="指标值">
		<eoms:id2nameDB id="${feePoolFactorList.indexSelected}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>	
		<display:column sortable="false" headerClass="sortable" title="计次系数">
			${feePoolFactorList.meteringFactor}
		</display:column>	
		<display:column title="修改" >
		 <a href="${app}/partner/feeManage/feeMetering.do?method=addFactor&id=${feePoolFactorList.id}&meteringId=${meteringId}">
				<img src="${app}/images/icons/edit.gif"/>
			</a>
		</display:column>
		<display:column  title="删除"
				url="/partner/feeManage/feeMetering.do?method=deleteFactor" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>
   </logic:present>

<table> 
<tr> <td><input type="button" onclick="subMeteringForm();"
		value="添加计次指标值"  class="btn"/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" onclick="returnBack();"
		value="返回"  class="btn"/></td></tr>
</table>


<form action="${app}/partner/feeManage/feeMetering.do?method=addFactor"
	method="post" id="meteringForm" name="meteringForm"/>
    <input type="hidden" id="meteringId" name="meteringId"  value="${indexId}"/>
</form>
<form action="${app}/partner/feeManage/feeMetering.do?method=meteringIndexList&id=${meteringId}"

	method="post" id="aFrom" name="aFrom">
</form>


<%@ include file="/common/footer_eoms.jsp"%>