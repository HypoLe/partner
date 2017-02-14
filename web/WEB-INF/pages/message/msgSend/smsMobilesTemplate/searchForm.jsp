<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import = " java.util.*,com.boco.eoms.base.util.ApplicationContextHolder,com.boco.eoms.message.mgr.ISmsMobilesTemplateManager,com.boco.eoms.message.model.SmsMobilesTemplate;"%>
 <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
<fmt:bundle
	basename="com/boco/eoms/message/config/ApplicationResources-msg">

	<html:form action="smsUserLog.do?method=search" method="post">
		<table class="formTable">

			<tr>
				<td class="label">
					<fmt:message key="smsUserMgr.userName" />
				</td>
				<td>
					<input id="userName" name="userName" type="text" class="text" />
				</td>
				<td class="label">
					<fmt:message key="smsUserMgr.team" />
				</td>
				<td>
					<SELECT id="team" name="team" class="select" />
						<option value="">
							选择用户组
						</option>
						<%
							ISmsMobilesTemplateManager mgr = (ISmsMobilesTemplateManager)ApplicationContextHolder.getInstance().getBean("IsmsMobilesTemplateManager");
							List list = mgr.getNodes4Team();
							int len=list.size();
							SmsMobilesTemplate smsMobilesTemplate=null;
							for(int i=0;i<len;i++){
								smsMobilesTemplate = (SmsMobilesTemplate)list.get(i);
						%>
						
						 <option value="<%=smsMobilesTemplate.getId() %>">
					    <%=smsMobilesTemplate.getName() %>
						
						
					    
					
					<%} %>
					</select>
					<br>
				</td>
			</tr>
				<tr>
				<td class="label" align="left">
					开始时间：
					<html:errors property="startDate" />
				</td>
				<td>
					<input id="startDate" name="startTime" type="text" class="text"
						readonly="readonly"
						onclick="popUpCalendar(this, this,null,null,null,-1,-1);" />
				</td>
				<td class="label" align="left">
					结束时间：
					<html:errors property="endDate" />
				</td>
				<td>
					<input id="endDate" name="endTime" type="text" class="text"
					readonly="readonly"
						onclick="popUpCalendar(this, this,null,null,null,-1,-1);"
						/>
				</td>
			</tr>
			<tr>
				<td class="label">
					<fmt:message key="smsUserMgr.mobile" />
				</td>
				<td>
					<input id="mobile" name="mobile" type="text" class="text" />
				</td>
			
				<td class="label">
					<fmt:message key="smsUserMgr.status" />
				</td>
				<td>
					<input id="status" name="status" value="1" type="radio" class="radio" />短信
					<input id="status" name="status" value="2" type="radio" class="radio" />语音
				</td>
				</tr>
			<tr>
				<td colspan="4" align="center">
					<html:submit styleClass="button" property="method.search">
						查询
					</html:submit>
			</tr>
		</table>
	</html:form>
</html>
</fmt:bundle>
