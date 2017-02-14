<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

		
</script>

<form  action="" id="gridForm" method="post" > 

<table class="formTable">
	<caption>
		<div class="header center">考试发布信息</div>
	</caption>

	<tr>
		<td class="label">
			标题
		</td>
		<td class="content" colspan="3">
			${en.title} 
			<span id = "message"></span>			
		</td>
	</tr>

	<tr>
		<td class="label">
			内容
		</td>
		<td class="content" colspan="3">
				${en.content}
			</td>	
	</tr>
	<tr>
		<td class="label">
			创建人
		</td>
		<td class="content">
			${en.creator} 	
		</td>
		<td class="label">
			创建时间
		</td>
		<td class="content">
			${en.createTime} 
			<span id = "message"></span>			
		</td>
	</tr>
</table>
</form>

<%@ include file="/common/footer_eoms.jsp"%>