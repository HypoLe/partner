<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpUtil"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpMonthPlanVO"%>
<%@ page import ="java.util.List"%>
<html>
<head>
<title>作业计划</title>
</head>

<%
  List monthPlanVOList = (List)request.getAttribute("monthplan");
  String cycleName = (String)request.getAttribute("cyclename");
  String cycle = (String)request.getAttribute("cycle");
  TawwpMonthPlanVO tawwpMonthPlanVO = null;
%>

<script language="javascript">
//弹出选择部门窗口
function selectTree(){
  dWinOrnaments = "status:no;scroll:no;resizable:yes;dialogHeight:450px;dialogWidth:480px;";
  dWin = showModalDialog('<%=request.getContextPath()%>/css/listbox_zyjh/listbox_depttoinput.jsp', window, dWinOrnaments);
}
function SubmitCheck()
{
  if(document.tawwpcheckmonthplanform.deptid.value=="")
  {
    alert("请选择部门");
    return false;
  }else if(document.tawwpcheckmonthplanform.deptid.value.indexOf(",")>0)
  {
  	 alert("系统不支持同时查询两个部门的作业计划！");
    return false;
  }
}


	Ext.onReady(function(){
	var	userTreeAction='${app}/xtree.do?method=userFromDept';
	userViewer = new Ext.JsonView("user-list",	
			'<div id="user-{id}" class="viewlistitem-user">{name}</div>',
			{ 
				multiSelect: true,		
				emptyText : '<div></div>'								
			}
		);
		userViewer.refresh();
		
		userTree = new xbox({
			btnId:'userTreeBtn',dlgId:'dlg-user',	
			treeDataUrl:userTreeAction,treeRootId:'-1',treeRootText:'部门',treeChkMode:'single',treeChkType:'dept',
			viewer:userViewer,saveChkFldId:'deptid' 
		});
	})

</script>
 
<form name='tawwpcheckmonthplanform' method="post" action="../tawwpcheck/checkmonthplanresult.do" onsubmit ="return SubmitCheck()">


  <table width="700" align=center  class="formTable">
    <tr>
      <td width="700" align="center" valign="middle" class="table_title">
      	<caption>
       		 作业计划执行情况巡查  --  周期类型:<%=cycleName%>
        </caption>
      </td>
    </tr>
  </table>

  <table border="0"  width="500" cellspacing="1" cellpadding="1" class="formTable" align=center>

    <tr class="tr_show">
      <td width="100" class="label">
        年份
      </td>
      <td width="100">
        <select name="yearflag">
          <option value="0">-- 请选择 --</option>
          <option value="2006">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
        </select>
      </td>

      <td width="100" class="label">
        月度
      </td>
      <td width="300" colspan="3">
        <select name="monthflag">
          <option value="0">-- 请选择 --</option>
          <option value="01">01</option>
          <option value="02">02</option>
          <option value="03">03</option>
          <option value="04">04</option>
          <option value="05">05</option>
          <option value="06">06</option>
          <option value="07">07</option>
          <option value="08">08</option>
          <option value="09">09</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
        </select>
      </td>
    </tr>
    <tr class="tr_show">
      <td width="100" class="label">
        执行部门
      </td>
       <td width="500" colspan="6">
        <div id="user-list" class="viewer-list"></div>
        <input type="button" value="<bean:message key="gzquerymonth.title.btnSelDept" />" id="userTreeBtn" class="btn"/>
        <INPUT TYPE="hidden" name="deptid" id="deptid" value="">
      </td>
    </tr>
    <!-- added 2007-08-13 添加周期作为查询条件 begin -->
     <tr class="tr_show">
      <td width="100" class="label">
        周期
      </td>
      <td width="500" colspan="5">
       <select size='1' name='cycle' value=''>
         <option  value='-1' selected="selected">全部</option>
         <option  value='1'>天</option>
         <option  value='2'>周</option>
         <option  value='3'>半月</option>
         <option  value='4'>月</option>
         <option  value='8'>两月</option>
         <option  value='5'>季度</option>
         <option  value='9'>四月</option>
         <option  value='6'>半年</option>
         <option  value='7'>年</option>
         <option  value='0'>其他</option>
        </select>
      </td>
    </tr>
    <!-- added 2007-08-13 添加周期作为查询条件 end -->
    <tr class="tr_show">
      <td align="middle" colSpan="6">
        <html:submit styleClass="button">
          查询
        </html:submit>
        <input type="reset" name="取消" value="取消" Class="button">
      </td>
    </tr>
  </table>

  <script language="javascript">
    document.tawwpcheckmonthplanform.yearflag.value='<%=TawwpUtil.getCurrentDateTime("yyyy")%>';
    document.tawwpcheckmonthplanform.monthflag.value='<%=TawwpUtil.getCurrentDateTime("MM")%>';
  </script>


  <table width="700" align=center  class="formTable">
    <tr>
      <td width="700" align="center" valign="middle">
      	<caption>
		    月度作业计划查询结果
		</caption>
      </td>
    </tr>
  </table>

  <table width="750" border="0" align="center" cellpadding="1"  cellspacing="1" class="formTable">
    <tr class="label">
      <td width="250">月度作业计划名称</td>
      <td width="100">网元名称</td>
      <td width="100">所属部门</td>
      <td width="50">年度</td>
      <td width="50">月度</td>
      <td width="75">制定状态</td>
      <td width="75">执行状态</td>
      <td width="50">详细</td>
    </tr>
    <%
    if(monthPlanVOList.size()>0){
      for(int i=0; i<monthPlanVOList.size(); i++){
        tawwpMonthPlanVO = (TawwpMonthPlanVO)monthPlanVOList.get(i);
    %>
    <tr class="tr_show">
      <td width="250"><%=tawwpMonthPlanVO.getName()%></td>
      <td width="100"><%=tawwpMonthPlanVO.getNetName()%></td>
      <td width="100"><%=tawwpMonthPlanVO.getDeptName()%></td>
      <td width="50"><%=tawwpMonthPlanVO.getYearFlag()%></td>
      <td width="50"><%=tawwpMonthPlanVO.getMonthFlag()%></td>
      <td width="75"><%=tawwpMonthPlanVO.getConstituteStateName()%></td>
      <td width="75"><%=tawwpMonthPlanVO.getExecuteStateName()%></td>
      <td width="50">
        <%
        if(tawwpMonthPlanVO.getConstituteState().equals("1")){
        %>
        <a href="checkmonthplancontent.do?monthplanid=<%=tawwpMonthPlanVO.getId()%>&cycle=<%=cycle%>">
          详细
        </a>
        <%
        }
        %>
      </td>
    </tr>
    <%
      }
    }
    else{
    %>
    <tr class="tr_show">
      <td align="middle" width="700" colspan="8">
        没有符合条件的月度作业计划
      </td>
    </tr>
    <%
    }
    %>
    <tr class="tr_show">
      <td align="middle"  width="700"  class="clsthd2" colspan='8'>
        <input type="button" value="返回" onclick="javascript:window.history.back();" class="button">
      </td>
    </tr>
  </table>
</form>
