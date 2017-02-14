<jsp:directive.page
	import="com.boco.partner2.workReport.util.InfopubConstants" />
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<marquee direction="up" scrolldelay="240" onmouseout="this.start()"
	onmouseover="this.stop()">
	<logic:notEmpty name="<%=InfopubConstants.THREAD_LIST%>">
		<table>
			<logic:iterate id="item" name="<%=InfopubConstants.THREAD_LIST%>">
				<tr>
					<td>
						<img src="${app }/images/infopub/title.gif">
						<a
							href='${app}/partner2/workReport/thread.do?method=detail&id=<bean:write name='item' property='id' />'
							target='_parent'><bean:write name="item"
								property="title" /> </a>
					</td>
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
</marquee>


