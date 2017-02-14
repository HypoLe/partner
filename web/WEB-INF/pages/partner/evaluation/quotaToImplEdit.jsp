<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<script type="text/javascript">

	Ext.onReady(function(){
	            var v = new eoms.form.Validation({form:"quotaToImplForm"});

	
	
	}
	           
)
   

 </script>







<form action="${app}/partner/evaluation/quotaToImpl.do?method=add"
	method="post" id="quotaToImplForm" name="quotaToImplForm">
	<input type="hidden" id="id" name="id" value="${entity.id}" />
	<div>
		<font color="red">${errormsg}</font>
	</div>
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					添加修改页面
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>名字
			</td>
			<td>
				<input type="text" class="text" name="name" id="name"
					alt="allowBlank:false,vtext:'不能为空 不能超出255个字符！',maxLength:255"
					value="${entity.name}" />
			</td>

			<td class="label">
				<font color='red'> * </font>类全名
			</td>
			<td>


				<input class="text" type="text" name="className"
					value="${entity.className}" id="className" alt="allowBlank:false,vtext:'不能为空 不能超出255个字符！',maxLength:255"
					/>
				

			</td>
		</tr>
		<tr>
			<td class="label">
				<font color='red'> * </font>类说明
			</td>
			<td class="content">
				<textarea name="remark" id="remark" class="text medium"
					alt="allowBlank:false,vtext:'不能为空 不能超出255个字符！',maxLength:255">${entity.remark}</textarea>
			</td>
			
		</tr>
		
	</table>
	<tr>
		<td class="label">
		</td>
		<td>
			<input type="submit" value="提交"  />
		</td>
	</tr>

</form>
<%@ include file="/common/footer_eoms.jsp"%>