<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.personnel.webapp.form.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
<!--
	jQuery.noConflict(); 
	jQuery(function($){  
		$("#reset").click(function(){
			$("#select_from input[type='text']").each(function(){
				$(this).val(null);
			})
		})
	}); 
//-->
</script>
	<form id="select_from" action="${app}/personnel/certificate.do?method=search" method="post">
		<table class="formTable" >
			<tr>		
				<td class="label">证书类别:</td>
				<td><input type="text" name="typeStringLike" value="${typeStringLike }"/></td>
				
				<td class="label">有效期</td>
				<td><input type="text" name="validityStringEqual" value="${validityStringEqual }"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"  readonly="true" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" class="btn" value="查询" />
					<input id="reset" type="button" class="btn" value="重置" />
				</td>
			</tr>
		</table>
	</form>	
	<br/><br/>
	<display:table name="certificateList" cellspacing="0" cellpadding="0"
		id="certificateList" pagesize="${pageSize}" class="table"
		export="true" requestURI="${app}/personnel/certificate.do?method=search"
		sort="list" partialList="true" size="${resultSize }" >
		
	    
	    <display:column property="workername" sortable="true" headerClass="sortable" title="员工姓名"/>
	    <display:column property="type" sortable="true" headerClass="sortable" title="类别"/>
	    <display:column property="level" sortable="true" headerClass="sortable" title="等级"/>
	    <display:column property="issueorg" sortable="true" headerClass="sortable" title="发证机关"/>
	    <display:column property="issuetime" sortable="true" headerClass="sortable" title="颁发时间"/>
	    <display:column property="validity" sortable="true" headerClass="sortable" title="有效期"/>
	    <display:column property="codeno" sortable="true" headerClass="sortable" title="编号"/>
	    <display:column property="memo" sortable="true" headerClass="sortable" title="备注"/>
	 	<display:column sortable="true" headerClass="sortable" title="详细" media="html" >					 
				<a   href="${app}/personnel/certificate.do?method=getDetail&id=${certificateList.id}">
					<img src="${app }/images/icons/table.gif">
				</a>			
		 </display:column>
	    <display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
<%@ include file="/common/footer_eoms.jsp"%>