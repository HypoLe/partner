<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants"/>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.boco.eoms.eva.util.DateTimeUtil"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaReportInfo"%>
<%@ page language="java" import="com.boco.eoms.base.util.StaticMethod" %>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaTask" %>

<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<table class="formTable" id="form">
<tr>
		<td class="label" width="25%">
			考核名称
		</td>
		<td class="label" width="75%" colspan='4'>
		考核周期
		</td>
</tr>

<%
List evaUndoList = (List)request.getAttribute("evaUndoList");
String tplId ="";
for(int i=0;i<evaUndoList.size();i++){
	Object[] obj = (Object[])evaUndoList.get(i);
	EvaReportInfo evaReportInfo = (EvaReportInfo)obj[0];
	EvaTask evaTask = (EvaTask)obj[1];
	if(!tplId.equals(evaTask.getTemplateId())){
		if(i!=0){
			%>
			</td>
			</tr>
			<%
		}
%>
<tr>
		<td class="label"   width="25%">
			<eoms:id2nameDB id="<%=evaTask.getTemplateId() %>" beanId="evaTemplateDao"/>
		</td>
		<td class="content" colspan='4'>
<%
	}
%>

	<input type="button" name="excuteTask" id="excuteTask" value="<%=evaReportInfo.getShowTimeStr() %>" class="btn" onclick="excuteTask('<%=evaReportInfo.getTaskId() %>','<%=evaReportInfo.getTime() %>','<%=evaReportInfo.getId() %>')"/>


	
	
<%
tplId = evaTask.getTemplateId();
}
%>
</td>
	</tr>
</table>
  
<script type="text/javascript">
function excuteTask(taskId,time,reportId) {
		var url=eoms.appPath+'/eva/evaTasks.do?method=getTaskDetailList&taskId='+taskId+'&reportId='+reportId+'&time='+time+'&partnerId=${template.partnerDept}';
		window.open(url);
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>