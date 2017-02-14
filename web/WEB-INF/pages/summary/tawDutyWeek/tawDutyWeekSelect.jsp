<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.base.util.StaticMethod"%>
 <fmt:bundle basename="config/ApplicationResources-summary">
<html:form action="/tawDutyWeeks.do?method=search">

  <table border="0"  width="300" class="formTable">
    <caption>查询</caption>
    <tr>
      <td class="label">
			<fmt:message key="tawDutyWeek.title" />
		</td>
		<td class="content">
			<html:text property="title" styleId="title"/>
		</td>
    </tr>
    <tr>
      <td class="label">
			<fmt:message key="tawDutyWeek.weekFlag" />
		</td>
		<td class="content">
			<html:text property="weekFlag" styleId="weekFlag"/>
		</td>
    </tr>
       <tr>
      <td class="label">
			<fmt:message key="tawDutyWeek.userId" />
		</td>
		<td class="content">
			<html:text property="userName" styleId="userName"/>
		</td>
    </tr>
    <tr>
		<td class="label" align="left">
			<fmt:message key="tawDutyWeek.starttime" />
			<html:errors property="startTime" />
		</td>
		<td>
			<input id="startTime" name="startTime" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1);"/>
		</td>
	</tr>
	<tr>
		<td class="label" align="left">
			<fmt:message key="tawDutyWeek.endtime" />
			<html:errors property="endTime" />
		</td>
		<td>
			<input id="endTime" name="endTime" type="text" class="text" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1);"/>
		</td>
	</tr>
  </table>
  <br>
  <input type="submit" value="<fmt:message key="tawDutyWeek.submit"/>" name="B1" class="submit">
</html:form>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>

