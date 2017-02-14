<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.tempTask.model.PnrTempTaskWork"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="com.boco.eoms.commons.system.dict.util.DictMgrLocator"%>
<%@page import="java.util.Map"%>
<%@ include file="/common/taglibs.jsp"%>
<jsp:directive.page import="java.util.List" />
<%@ include file="/common/header_eoms_form.jsp"%>
<%
List workList = (List)request.getAttribute("pnrTempTaskWorkList");
Map workNum = (Map)request.getAttribute("workNum");
String workNumStr ="";
String taskUrl = "";
String localTime = StaticMethod.getLocalString();
%>


<fmt:bundle basename="com/boco/eoms/partner/tempTask/config/applicationResources-partner-tempTask">


<table class="formTable">

	<%
	PnrTempTaskWork pnrTempTaskWork = null;
	String tempTaskId = "";
	if(workList.size()==0){
		%>
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
			</tr>
		<%
		}	
	for(int i=0,j=1;i<workList.size();i++,j++){
	pnrTempTaskWork = (PnrTempTaskWork)workList.get(i);
	workNumStr = StaticMethod.nullObject2String(workNum.get(pnrTempTaskWork.getTempTaskId()));
	if(!tempTaskId.equals(pnrTempTaskWork.getTempTaskId())){	
		if(i!=0){
	%>
	</tr>
		</table>	
	<%
		}
		j=1;
	%>
	<tr>
	<td class="label" colspan="4">
	<img align=left src="${app}/images/icons/list.gif" alt="隐藏/显示工作内容" onclick="javascript:showWork('<%=pnrTempTaskWork.getTempTaskId() %>');" />临时任务名称：
	<a href="${app}/partner/tempTask/pnrTempTaskMains.do?method=detail&id=<%=pnrTempTaskWork.getTempTaskId() %>" target="_blank">
	<eoms:id2nameDB id="<%=pnrTempTaskWork.getTempTaskId() %>" beanId="pnrTempTaskMainDao" />
	</a>
	(<font color="red"><%=workNumStr %>个任务需要处理</font>) 
	</td>
	</tr>
	<tr  id="<%=pnrTempTaskWork.getTempTaskId() %>" style="display:none;">
	<td colspan="4">
	工作子项:
	<table class="formTable" >
	
	<%
	tempTaskId = pnrTempTaskWork.getTempTaskId();
	}
	%>
	<tr>
			<th colspan="5"><b>第<%=j %>项：</b></th>
	</tr>
<tr>
		<td class="label" style="vertical-align:middle">
			开始时间
		</td>
		<td class="content">
		<%=pnrTempTaskWork.getStartTimeStr() %>	
		</td>
		<td class="label" style="vertical-align:middle" rowspan="2">
			工作描述
		</td>
		<td class="content" rowspan="2">
			<%=pnrTempTaskWork.getWorkContent() %>	
		</td>
		<td rowspan="2" valign="middle" >	
		<%
		if(pnrTempTaskWork.getWorkType()!=null&&!"subjective".equals(pnrTempTaskWork.getWorkType())){
			taskUrl = StaticMethod.nullObject2String(DictMgrLocator.getDictService().itemId2description("dict-partner-agreement#workType",pnrTempTaskWork.getWorkType()));
		%>
		<input  type="button" class="btn" value="执行页面"onclick="window.open('<%=taskUrl %>');"	/>
		<%
			}
		if(pnrTempTaskWork.getWarnTime()==null||StaticMethod.getTimeDistance(pnrTempTaskWork.getWarnTime(),localTime)>=0){
		%>
		<input id='<%=pnrTempTaskWork.getId() %>' type="button" class="btn" value="停止提醒" onclick="warmCanel('<%=pnrTempTaskWork.getId() %>');"	/>
		<%
			}
		%>
		</td>
	</tr>
		<tr>
		<td class="label" style="vertical-align:middle">
			结束时间
		</td>
		<td class="content">
		<%=pnrTempTaskWork.getEndTimeStr() %>	
		</td>	
	</tr>
	
	<%
	
	}
	%>
	</td>
	</tr>
		</table>	
</table>

</fmt:bundle>
<script language="javascript">
function showWork(trId)
{
	works = document.getElementById(trId);   
  if(works.style.display=="block")
  {
    works.style.display="none";
  }
  else
  {
    works.style.display="block";
  }
}
function warmCanel(workplanId)
{
	if(confirm("是否取消本周期的提醒")){
	            Ext.Ajax.request({
                url:eoms.appPath+'/partner/tempTask/pnrTempTaskWorks.do?method=workWarnCancel',
                params : {
                    workId : workplanId
                }
            });
    document.getElementById(workplanId).style.display="none";
    }
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>