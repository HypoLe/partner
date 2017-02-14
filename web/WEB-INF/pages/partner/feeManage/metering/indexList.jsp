<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<script type="text/javascript">
 function returnBack(){
  document.getElementById("aFrom").submit();
		//window.history.back();
	}
 function subMeteringForm(){
  document.getElementById("meteringForm").submit();
	}
	
</script>
    
	<logic:present name="feePoolIndexList" scope="request">
    <table class="formTable">
	<caption>
	<div class="header center">计次指标列表</div>
	</caption></table>
	<display:table name="feePoolIndexList" cellspacing="0" cellpadding="0"
		id="feePoolIndexList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		requestURI="${app}/partner/feeManage/feeMetering.do?method=meteringIndexList"
		sort="list" partialList="true"
		>
			
		<display:column sortable="false" headerClass="sortable" title="计次类型">
			${feePoolIndexList.meteringName}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="专业">
		<eoms:id2nameDB id="${feePoolIndexList.major}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>
		
		
		<display:column sortable="false" headerClass="sortable" title="指标名称">
			${feePoolIndexList.indexName}
		</display:column>
		<display:column title="修改" >
		 <a href="${app}/partner/feeManage/feeMetering.do?method=addIndex&id=${feePoolIndexList.id}&meteringId=${meteringId}">
				<img src="${app}/images/icons/edit.gif"/>
			</a>
		</display:column>
		<display:column title="管理计次属性" 
		  url="/partner/feeManage/feeMetering.do?method=factorList"  paramProperty="id"
				paramId="id" media="html" >
				<img src="${app}/images/icons/search.gif"/>
			
		</display:column>
		<display:column  title="删除"
				url="/partner/feeManage/feeMetering.do?method=deleteIndex" paramProperty="id"
				paramId="id" media="html">
				<img src="${app}/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>
</logic:present>
<table> 
<tr> <td><input type="button" onclick="subMeteringForm();"
		value="添加计次指标"  class="btn"/>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" onclick="returnBack();"
		value="返回"  class="btn"/></td></tr>
</table>


<form action="${app}/partner/feeManage/feeMetering.do?method=addIndex"
	method="post" id="meteringForm" name="meteringForm"/>
    <input type="hidden" id="meteringId" name="meteringId"  value="${meteringId}"/>
</form>
<form action="${app}/partner/feeManage/feeMetering.do?method=meteringList"
	method="post" id="aFrom" name="aFrom">
</form>


<%@ include file="/common/footer_eoms.jsp"%>