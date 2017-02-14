<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

</script>

<form action="../record/recordAction.do?method=save" id="recordForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">新增档案管理</div>
	</caption>
	<tr>
	    <td class="label">
	       主题<font color="red" > *</font>
	    </td>
	    <td colspan="3">
	       ${pnrRecord.title}
	    </td>
	</tr>
		<tr>
 			<td class="label">地市<font color="red" > *</font></td>
			<td class="content" >
						<eoms:id2nameDB id="${pnrRecord.city}" beanId="tawSystemAreaDao" />		
				
			</td>
 			<td class="label">区县<font color="red" > *</font></td>
			<td class="content" >
		       <eoms:id2nameDB id="${pnrRecord.country}" beanId="tawSystemAreaDao" />
			</td>
 		</tr>

	<tr>
	    <td class="label">
	       站点<font color="red" > *</font>
	    </td>
	    <td class="content">
	       ${pnrRecord.site}
	    </td>
	    <td class="label">
	       维护专业<font color="red" > *</font>
	    </td>
			<td class="content">
		       <eoms:id2nameDB id="${pnrRecord.specialty}" beanId="ItawSystemDictTypeDao" />
			</td>
	</tr>	
	<tr>
	    <td class="label">
	       档案分类<font color="red" > *</font>
	    </td>
			<td class="content">
		       <eoms:id2nameDB id="${pnrRecord.recordType}" beanId="ItawSystemDictTypeDao" />
			</td>
	</tr>

	<tr>
	    <td class="label">
	       概述<font color="red" > *</font>
	    </td>
	    <td colspan="3">
            ${pnrRecord.summary}
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       关键词<font color="red" > *</font>
	    </td>
	    <td colspan="3">
	       ${pnrRecord.keyword}
	    </td>
	</tr>
	<tr>
	    <td class="label">
	       附件
	    </td>
        <td class="content" colspan="3" height="100px">
              <eoms:download ids="${pnrRecord.attachment}"></eoms:download>
        </td> 
	</tr>
	
</table>
</form>



<%@ include file="/common/footer_eoms.jsp"%>