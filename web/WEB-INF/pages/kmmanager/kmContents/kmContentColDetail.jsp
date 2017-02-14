<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>

	<%--
		modify by mios:
		mark有4个状态： 0：半行， 1：整行， 2：左半行，右为空行， 3：右半行，左为空行,通过mark控制是否输出空列
		flag用来控制是否换行，每输出两列后，flag+1 
	--%>
	<tr>
		<c:set var="flag" value="${0}" />
		<c:forEach items="${KmTableColumnList}" var="item" varStatus="status">
			<c:if test="${item.mark == 3}">
			<%-- 有右半行，填充左半行为空行--%>
			<td class="label"></td>
			<td class="content" role="4"></td>
			<c:set var="flag" value="${flag+1}" />
			</c:if>

		<c:if test="${item.isOpen == 1}">
			<td class="label">
				${item.colChname}<c:if test="${item.isNullable == 0}"><font color='red'>*</font></c:if>
			</td>
			<td class="content" <c:if test="${item.mark == 1}">colspan="3"</c:if> role="${item.mark}">
				<c:choose>
					<c:when test="${item.colDictType == 0}">
						<c:choose>
							<c:when test="${item.colType == 1}">								
								<%-- 不绑定_普通文本 --%>								
								${KmContentsMap[item.colName]}
							</c:when>
							<c:when test="${item.colType == 2}">
								<%-- 不绑定_大文本域 --%>
								<textarea type="_moz" class="textarea max" cols="50" readonly="readonly">${KmContentsMap[item.colName]}</textarea>
							</c:when>
							<c:when test="${item.colType == 3}">
								<%-- 不绑定_数字类型 --%>
								${KmContentsMap[item.colName]}
							</c:when>
							<c:when test="${item.colType == 4}">
								<%-- 不绑定_日期时间 --%>
								${KmContentsMap[item.colName]}
							</c:when>
							<c:when test="${item.colType == 7}">
								<%-- 不绑定_附件上传 --%>
								<eoms:attachment name="KmContents" property="${item.colName}" scope="request" idField="${item.colName}" appCode="kmmanager" viewFlag="Y"/>																
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${item.colDictType == 1}">
						<c:choose>
							<c:when test="${item.colType == 5}">
								<%-- 普通字典_单选字典 --%>
								<eoms:id2nameDB id="${KmContentsMap[item.colName]}" beanId="kmSystemDictTypeDaoHibernate" />
							</c:when>
							<c:when test="${item.colType == 6}">
								<%-- 普通字典_多选字段 --%>
								<eoms:id2nameDB id="${KmContentsMap[item.colName]}" beanId="kmSystemDictTypeDaoHibernate" />
							</c:when>
						</c:choose>
					</c:when>
					<c:when test="${item.colDictType == 2}">
						<c:choose>
							<c:when test="${item.colType == 5}">
								<%-- 知识字典_单选字段 --%>
							</c:when>
							<c:when test="${item.colType == 6}">
								<%-- 知识字典_多选字段 --%>
							</c:when>
						</c:choose>
					</c:when>					
				</c:choose>
			</td>
		</c:if>
		<c:if test="${item.mark != 1}">
			<%-- 半行--%>
			<c:set var="flag" value="${flag+1}" />
		</c:if>
		<c:if test="${item.mark == 2}">
			<%-- 有左半行，填充右半行为空行--%>
			<td class="label"></td>
			<td class="content" role="4"></td>
			<c:set var="flag" value="${flag+1}" />
		</c:if>
		<c:if test="${item.mark == 1 || flag>=2}">
			<%-- 整行，可以换行--%>
			</tr>
			${!status.last?"<tr>":""}
			<c:set var="flag" value="${0}" />
		</c:if>		
		</c:forEach>
	</tr>
