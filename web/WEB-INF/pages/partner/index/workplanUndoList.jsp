<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.workplan.model.PnrWorkplanWork"%>
<%@page import="com.boco.eoms.base.util.StaticMethod"%>
<%@page import="com.boco.eoms.commons.system.dict.util.DictMgrLocator"%>
<%@page import="java.util.Map"%>
<%@ include file="/common/taglibs.jsp"%>
<jsp:directive.page import="java.util.List" />
<%@ include file="/common/header_eoms_form.jsp"%>
<%
List workList = (List)request.getAttribute("pnrWorkplanWorkList");
Map workNum = (Map)request.getAttribute("workNum");
String workNumStr ="";
String taskUrl = "";
String localTime = StaticMethod.getLocalString();
%>

<fmt:bundle basename="com/boco/eoms/partner/workplan/config/applicationResources-partner-workplan">


<table class="formTable">


	<%
	PnrWorkplanWork pnrWorkplanWork = null;
	String workplanId = "";
	if(workList.size()==0){
	%>
		<tr>
			<td  style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
		</tr>
	<%
	}
	for(int i=0,j=1;i<workList.size();i++,j++){
	pnrWorkplanWork = (PnrWorkplanWork)workList.get(i);
	workNumStr = StaticMethod.nullObject2String(workNum.get(pnrWorkplanWork.getWorkplanId()));
	if(!workplanId.equals(pnrWorkplanWork.getWorkplanId())){	
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
	<img align=right src="${app}/images/icons/open.gif" alt="隐藏/显示工作内容" onclick="javascript:showWork('<%=pnrWorkplanWork.getWorkplanId() %>');" />计划名称：
	<a href="${app}/partner/workplan/pnrWorkplanMains.do?method=detail&id=<%=pnrWorkplanWork.getWorkplanId() %>" target="_blank">
	<eoms:id2nameDB id="<%=pnrWorkplanWork.getWorkplanId() %>" beanId="pnrWorkplanMainDao" />
	</a>
	(<font color="red"><%=workNumStr %>个任务需要处理</font>) 
	</td>
	</tr>
	<tr  id="<%=pnrWorkplanWork.getWorkplanId() %>" style="display:none; ">
	<td colspan="4">
	工作子项:
	<table class="formTable" >
	
	<%
	workplanId = pnrWorkplanWork.getWorkplanId();
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
		<%=pnrWorkplanWork.getStartTimeStr() %>	
		</td>
		<td class="label" style="vertical-align:middle" rowspan="2">
			工作描述
		</td>
		<td class="content" rowspan="2">
			<%=pnrWorkplanWork.getWorkContent() %>	
		</td>
		<td rowspan="2" valign="middle" >						
		<%
		if(pnrWorkplanWork.getWorkType()!=null&&!"subjective".equals(pnrWorkplanWork.getWorkType())){
			taskUrl = StaticMethod.nullObject2String(DictMgrLocator.getDictService().itemId2description("dict-partner-agreement#workType",pnrWorkplanWork.getWorkType()));
		%>
		<input  type="button" class="btn" value="执行页面"onclick="window.open('<%=taskUrl %>');"	/>
		<%
			}
		if(StaticMethod.getTimeDistance(pnrWorkplanWork.getWarnTime()+" 00:00:00",localTime)>=0){
		%>
		<input id='<%=pnrWorkplanWork.getId() %>' type="button" class="btn" value="确认完成" onclick="warmCanel('<%=pnrWorkplanWork.getId() %>');"	/>
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
		<%=pnrWorkplanWork.getEndTimeStr() %>	
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
	if(confirm("您确认本周期已完成了该项内容吗？")){
	            Ext.Ajax.request({
                url:eoms.appPath+'/partner/workplan/pnrWorkplanWorks.do?method=workWarnCancel',
                params : {
                    workId : workplanId                    
                },
                success: function(resp,opts) {         
    				document.getElementById(workplanId).style.display="none";
                    location.reload();
                               
             	},    
            	 failure: function(resp,opts) {    
           	        alert('提交错误，请联系管理员');       
	            }     
            });
    }
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>