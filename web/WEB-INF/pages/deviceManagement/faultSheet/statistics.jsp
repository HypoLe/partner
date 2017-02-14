<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<script type="text/javascript">


Ext.onReady(function() {
	v = new eoms.form.Validation({form:'statisticsFrom'});
});
</script>
<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" rootId="2"
	rootText='请选着部门' valueField="deptId" handler="dept" textField="dept"
	checktype="dept" single="true" ></eoms:xbox>
<form action="faulSheetStatistics.do?method=statisticsList" method="post" id="statisticsFrom">
<fieldset>
<lengend>请输入统计条件</lengend>

<table class="formTable">
<tr>
<td class="label">派单时间</td>

<td id="data" >从<input type="text" name="startTi故障工单统计me" onclick="popUpCalendar(this, this,null,null,null,true,-1);"
 alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间'" id="startTime"/>
到<input type="text" name="endTime"  id="endTime" onclick="popUpCalendar(this, this,null,null,null,true,-1);"
 alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间'"/></td>
</tr>
<tr>
<td class="label">
按部门统计处理
</td>


<td >
<input type="text" class="text"
						value="${statisticsFrom.deptT}" maxLength="100"
						name="dept" id="dept"/>
						<input type="hidden" name="deptId" id="deptId"/>
						
</td>


</tr>
</table>
<input type="submit" name="提交"/>
</fieldset>
</form>

<div>
 <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
 	 <thead>
	 <tr >
	 <logic-el:present name="headList">
	 <c:forEach items="${headList}"  var="headlist" >
	 <th>
	 ${headlist}
	 </th>
	 
	 </c:forEach>
	 
     </logic-el:present>
     </tr>
     </thead>
     <logic-el:present name="tableList">
     <tbody>
     <c:forEach items="${tableList}" var="tdList">
     <tr>
     <c:forEach items="${tdList}" var="td">
     <td>
      
     <c:if test="${!empty td.url}">
     <a href="javascript:void(0);" onclick="window.open('${app}${td.url}');">${td.name}</a>
     </c:if>
   
     <c:if test="${empty td.url}">
      ${td.name}
     </c:if>
    	
     </td>
     </c:forEach>
     </tr>
     </c:forEach>
     <tr>
     <td colspan="6">算法说明：接单及时率=及时接单总数/完成工单总数</td></tr>
     <tr>
     <td colspan="6">完成及时率=及时完成总数/完成工单总数</td></tr>
	  </tbody>
	  </logic-el:present>
 </table>
</div>



<%@ include file="/common/footer_eoms.jsp"%>