<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<!--  
<form action="${app}/partner/feeManage/feeCountManage.do?method=feeCountPrcTmplListByMajor4entity"
	method="post" id="form" name="form">

<table>
<tr><td class="label">专业</td><td class="label">
<select id="majorSelect" name="majorSelect" class="text" onchange="submitForm()" >
<c:forEach items='${majorList}' var="majorList">
  <option value='${majorList.majorId}' <c:if test="${majorList.majorId==majorId}">selected='selected'</c:if>>${majorList.name}</option>
</c:forEach>

</select>
</td>
</tr>
</table>
</form>
-->
	<table id="sheet" class="formTable">
		
			<td colspan="4">
				<div class="ui-widget-header">
					计次费用模板
				</div>
			</td>
			</table>
<display:table name="feeCountPrcTmplList" cellspacing="0" cellpadding="0"
		id="feeCountPrcTmplList" pagesize="${pagesize}"
		class="table appraisalList" export="false"
	   requestURI=""
		size="${size}">
		<display:column property="prcTmplNm"   
			headerClass="sortable" title="模板名称" />
		<display:column property="majorName"   
			headerClass="sortable" title="专业名称" />
		<display:column   headerClass="sortable" title="创建人">
		<eoms:id2nameDB id="${feeCountPrcTmplList.creatorId}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column property="createdtTm"   
			headerClass="sortable" title="创建时间" format="{0,date,yyyy-MM-dd}"/>
        <display:column  title="详情">
		     <img src="${app}/images/icons/search.gif" onClick='showDetail("${feeCountPrcTmplList.id}");'/>
    	</display:column>
       
      <display:column  headerClass="sortable" title="使用该模板">
      <c:if test="${empty delEntityId}">
			<a href="${app}/partner/feeManage/feeCountManage.do?method=goToFeeCountEntity&id=${feeCountPrcTmplList.id}&feePoolMainId=${feePoolMainId}">进入</a>
			</c:if>
	  <c:if test="${not empty delEntityId}"><a href="${app}/partner/feeManage/feeCountManage.do?method=goToFeeCountEntity&id=${feeCountPrcTmplList.id}&feePoolMainId=${feePoolMainId}&delEntityId=${delEntityId}">进入</a>
			</c:if>		
		</display:column>
</display:table>
<script type="text/javascript">
  function showDetail(id){
      var url="${app}/partner/feeManage/feeCountManage.do?method=goToFeeCountDetailTmplView&id="+id;
      window.open (url, "详细", "height=450, width=800, top=200, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");     
  } 
</script>
<%@ include file="/common/footer_eoms.jsp"%>