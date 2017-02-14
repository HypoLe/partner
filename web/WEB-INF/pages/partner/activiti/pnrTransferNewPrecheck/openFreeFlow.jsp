<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<base target="_self" />
		<%@ include file="/common/header_eoms_form.jsp"%>
		<script language="javaScript" type="text/javascript"
			src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>

		<html:form
			action="/pnrTransferNewPrecheck.do?method=doFreeFlow"
			styleId="theform">
			<table class="formTable">
				<tr>
					<td class="label">
						processInstanceId
					</td>
					<td class="content">
						<input type="text" id="processInstanceId" name="processInstanceId" value="" />
					</td>
					<td class="label">
						转至信息 例如：从cityManageExamine驳回到need
					</td>
					<td class="content">
						<input type="text" id="turnMessage" name="turnMessage" value="" />
					</td>
					
				</tr>
				<tr>
					<td class="label">
						taskId
					</td>
					<td class="content">
						<input type="text" id="taskId" name="taskId" value="" />
					</td>
					<td class="label">
						activityId
					</td>
					<td class="content">
						<input type="text" id="activityId" name="activityId" value="" />
					</td>
				</tr>

				<tr>
					<td class="label">
						下一环节处理人的key
					</td>
					<td>
						<input type="text" id="pesornKey" name="pesornKey" value="" />
					</td>
					<!-- 工单号  -->
					<td class="label">
						下一环节处理人的value
					</td>
					<td>
						<input type="text" id="pesornValue" name="pesornValue" value="" />
					</td>

				</tr>

				<tr>
					<td class="label">
						处理标识key
					</td>
					<td>
						<input type="text" id="flagKey" name="flagKey" value="" />
					</td>
					<td class="label">
						处理标识value
					</td>
					<td>
						<input type="text" id="flagValue" name="flagValue" value="" />
					</td>

				</tr>
			</table>
			<!-- buttons -->
			<div class="form-btns">
				<html:submit styleClass="btn" property="method.save"
					styleId="method.save">
                查询
                
                
            </html:submit>
				<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>

			</div>
		</html:form>

		<%@ include file="/common/footer_eoms.jsp"%>