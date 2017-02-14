<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>

<%
  List list = (List)request.getAttribute("list");
  String allQuality = StaticMethod.nullObject2String(request.getAttribute("allQuality"));
  TawwpExecuteContentVO tawwpExecuteContentVO = null;
%>

<script language="JavaScript">
Ext.onReady(function(){
	colorRows('list-table');
})

function onMonthPlanList()
{
  var itemlist = "";
  var flag = true;

  if(document.forms[0].id.length != null){
    for(var i =0; i<document.forms[0].id.length ; i++)
    {
      if(document.forms[0].id[i].checked)
      {
        if(flag)
        {
          itemlist = document.forms[0].id[i].value;
          flag = false;
        }
        else
        {
          itemlist = itemlist + "," + document.forms[0].id[i].value;
        }
      }
    }
  }
  else{
    if(document.forms[0].id.checked){
      itemlist = document.forms[0].id.value;
    }
  }
   
  if(itemlist == ""){
    alert("请选择要质检的执行项！");
  }
  else{
    document.forms[0].ids.value = itemlist;
	document.forms[0].action = "../tawwpexecute/qualitypass.do";    
	document.forms[0].submit();
  }
}
//全选/反选开关
function selectall(){
  var selobj = document.forms[0].id;
  if(selobj.length != null){
    for(i=0;i<selobj.length;i++){
      if(selobj[i].checked==true)
        selobj[i].checked = false;
      else
        selobj[i].checked = true;
    }
  }
  //增加了只有一项执行内容的情况
  else{
    if(selobj.checked==true)
      selobj.checked = false;
    else
      selobj.checked = true;
  }
}
</script>

<!--  body begin  -->

<form name="monthplanform" >

  <table width="700" class="listTable" id="list-table">
    <caption>质检列表</caption>
    <thead>
    <tr>
      <td width="5%">
        <input type="checkbox" name="" onclick="selectall();">选择
      </td>
      <td width="15%">
        作业计划名称
      </td>
      <td width="15%">
        所属网元
      </td>
      <td width="15%">
        执行项名称
      </td>
      <td width="15%">
        执行人
      </td>
      <td width="10%">
        质检
      </td>
    </tr>
    </thead>
    <tbody>
    <%
      for(int i=0; i<list.size(); i++){
        tawwpExecuteContentVO = (TawwpExecuteContentVO)list.get(i);
        
    %>
    <tr>
      <td width="5%">
        <input type="checkbox" name="id" value="<%=tawwpExecuteContentVO.getId()%>">
      </td>
      <td width="15%">
        <%=tawwpExecuteContentVO.getMonthPlanNname()%>
      </td>
      <td width="15%">
        <%=tawwpExecuteContentVO.getNetNname()%>
      </td>
      <td width="15%">
        <%=tawwpExecuteContentVO.getName()%>
      </td>
      <td width="15%">
      <%=tawwpExecuteContentVO.getUserName()%>
      </td>
      <td width="10%">
        <a href="../tawwpexecute/qualityview.do?id=<%=tawwpExecuteContentVO.getId()%>&allQuality=<%=allQuality%>">
         <img src="../images/bottom/an_pz.gif" width="19" height="18" align="absmiddle">
        </a>
      </td>
    </tr>
    <%
      }
    %>
    <tr>
      <td colspan="6">
        <input type="button" name="b1" value="批量通过" Onclick="onMonthPlanList();" Class="button">
      </td>
    </tr>
    </tbody>
  </table>
  <INPUT type="hidden" id="ids" name="ids" value="">
  <INPUT type="hidden" id="allQuality" name="allQuality" value="<%=allQuality%>">
</form>

<!--  body end  -->

<%@ include file="/common/footer_eoms.jsp"%>

