<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<table class="formTable">
  	<caption><div class="header center">${carNumber}&nbsp;&nbsp;当前任务信息</div></caption>
  		<tr>
				<td class="label">
					当前使用人
				</td>
				<td class="content">
					<eoms:id2nameDB  id="${carTask.carUser}" beanId="tawSystemUserDao"/>
				</td>
		</tr>
		<tr>
				<td class="label">
					任务类型
				</td>
				<td class="content" >
					<eoms:id2nameDB  id="${carTask.taskType}" beanId="ItawSystemDictTypeDao"/>
				</td>
		</tr>
		<tr>			
				<td class="label">
					具体任务
				</td>
				<td class="content">
					${carTask.taskName}
				</td>
		</tr>
	</table>

<%@ include file="/common/footer_eoms.jsp"%>
