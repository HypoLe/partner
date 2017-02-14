<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%> 
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpModelPlanVO"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpModelExecuteVO,com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>
<script type="text/javascript" src="${app}/workplan/script/tableSort.js"></script>
 
<%
TawwpModelPlanVO tawwpModelPlanVO = (TawwpModelPlanVO)request.getAttribute("modelplan");
TawwpModelExecuteVO tawwpModelExecuteVO = null;
List list = null;
%>
 
<script language="JavaScript">
Ext.onReady(function(){
	colorRows('list-table');
})
function onModelPlanAdd()
{
  location.href="itemadd.do?modelplanid=<%=request.getParameter("modelplanid")%>";
}
function onModelGroupList()
{
  location.href="grouplist.do?modelplanid=<%=request.getParameter("modelplanid")%>";
}
function onModelExport()
{
  location.href="modelexport.do?modelid=<%=request.getParameter("modelplanid")%>&reaction=addons/filedownload.jsp";
}
function onBack()
{
 
 
//  document.modelexecutelist.action= "modellists.do?Mtype='<%=tawwpModelPlanVO.getNetTypeId() %>'";
//  document.modelexecutelist.submit();
    location.href="modellists.do?Mtype=<%=tawwpModelPlanVO.getNetTypeId() %>";
 
}
</script>
 

 
<form name="modelexecutelist">

  <table width="100%" class="listTable" id="list-table">
    <caption>
      	&lt;&nbsp; <%=tawwpModelPlanVO.getName()%> &nbsp;&gt;<bean:message key="itemlist.tawwpmodel.formTitle" />
    </caption>

  <%
  list = tawwpModelPlanVO.getModelExecuteList();
  TawSystemSessionForm tawSystemSessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
	boolean flag = true;
  %>
  <thead>
   <tr>
      <td width="200"  onclick="sort('list-table',0,'char')"><span style="cursor: hand">作业项目</span></td>
      <td width="50" onclick="sort('list-table',1,'char')"><span style="cursor: hand"><bean:message key="itemlist.tawwpmodel.formCycle" /></span></td>
      <td width="150" onclick="sort('list-table',2,'char')"><span style="cursor: hand"><bean:message key="itemlist.tawwpmodel.formDefault" /></span></td>
      <td width="150" onclick="sort('list-table',3,'char')"><span style="cursor: hand">记录模版</span></td>
      <td width="150" onclick="sort('list-table',4,'char')"><span style="cursor: hand">是否必须上传附件</span></td>
      <td width="50"><bean:message key="itemlist.tawwpmodel.formEdit" /></td>
      <td width="50"><bean:message key="itemlist.tawwpmodel.formRemove" /></td>
    </tr>
    </thead>
    <tbody>
    <%
    for(int i=0; i<list.size(); i++){
      tawwpModelExecuteVO = (TawwpModelExecuteVO)list.get(i);
    %>
	    <tr>
	      <td><%=tawwpModelExecuteVO.getName()%></td>
	      <td><%=tawwpModelExecuteVO.getCycleName()%></td>
	      <td><%=tawwpModelExecuteVO.getFormat()%></td>
	      <td>
	        <a target="_blank" href="../tawwpaddons/addonsread.do?action=read&window=new&myid=&model=50&addonsid=<%=tawwpModelExecuteVO.getFormId()%>&reaction=/tawwpaddons/addonslist.do">
				<%=tawwpModelExecuteVO.getFormName()%>
	        </a>
	      </td>
	      
	      <td>
	      <%=tawwpModelExecuteVO.getFileFlagName()%>
	      </td>
	      
	      <td>
	        <%if(tawwpModelPlanVO.getCruser().equals(tawSystemSessionForm.getUserid())){
	        
	        %>
	        <a href="itemedit.do?modelplanid=<%=tawwpModelPlanVO.getId()%>&modelexecuteid=<%=tawwpModelExecuteVO.getId()%>">
	          <img src="${app }/images/icons/edit.gif">
	        </a>
	        <%}else{
	          flag = false;
	          }
	        %>
	       
	      </td>
	      <td>
	        <%if(tawwpModelPlanVO.getCruser().equals(tawSystemSessionForm.getUserid())){%>
	        <a href="itemremove.do?modelplanid=<%=tawwpModelPlanVO.getId()%>&modelexecuteid=<%=tawwpModelExecuteVO.getId()%>">
	          <img src="${app }/images/icons/icon.gif" width="20" height="25">
	        </a>
	        <%}%>
	      </td>
	    </tr>
    <%
    }
    %>
    </tbody>
  </table>

  <br>
  <%--<input type="button" Class="button"  value="<bean:message key="itemlist.tawwpmodel.btnExport" />" onclick="javascript:onModelExport();"  >--%>
  <%if(flag){%>
  <input type="button" Class="button"  value="<bean:message key="itemlist.tawwpmodel.btnAdd" />" onclick="javascript:onModelPlanAdd();" >
  <%}%>
  <input type="button" Class="button"  value="<bean:message key="itemlist.tawwpmodel.btnBack" />" onclick="javascript:onBack();"  >

</form>  
<%@ include file="/common/footer_eoms.jsp"%>
