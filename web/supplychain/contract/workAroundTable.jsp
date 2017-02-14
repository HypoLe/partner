<%@ page contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
  <head>
  		<%
		String webapp = request.getContextPath();
		%>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/screen.css" type="text/css"
			media="screen, print" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/css/table_style.css"
			type="text/css">
		<style type="text/css">
		table	{ font-size: 12px;
			margin-top: 0em;
			margin-left: 0em;
			margin-right: 0em;
			margin-bottom: 2em;
		}
		</style>
		<script language="javascript"
			src="<%=request.getContextPath()%>/js/dojo/dojo.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/io.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/dojo/src/string.js"></script>
		<script type="text/javascript">
			djConfig={
				isDebug:false,
				bindEncoding:"utf-8"
			};
			
			dojo.require("dojo.io.BrowserIO");
		</script>
		<script type="text/javascript">
			function saveWorkAround(id,name){
				window.parent.saveWorkAround(id,name);
			}
		</script>
  </head>
  
  <body>
		<CENTER>
			<P class="table_title" style="margin-bottom: -1em;margin-top: 1em">
				<b>工作区查询<bean:message key="label.list" />
				</b>
			</P>
			<display:table name="sessionScope.workAroundFormList" id="row" cellspacing="1" cellpadding="1">
				<display:column media="html" title="添加" style="width:50px">
					<input type="button" class="clsbtn" name="workAroundChecked" value="添加工作区" onclick="saveWorkAround(<bean:write name="row" property="workAroundId"/>,'<bean:write name="row" property="workAroundName"/>');">
				</display:column>
				<display:column property="regionName" title="地市名称"
					style="width:80px" />
				<display:column property="cityName" title="县市名称"
					style="width:80px" />
				<display:column property="workAroundName" title="工作区名称"
					style="width:100px" />
			</display:table>
			<TABLE width="98%" cellpadding="1" cellspacing="1" border="0"
				class="table_show" align="center">
				<TR class="tr_show">
					<td class="td_right" width="100%">
						<bean:write name="pagerHeader" scope="session" filter="false" />
					</td>
				</TR>
			</TABLE>
		</CENTER>
  </body>
</html>