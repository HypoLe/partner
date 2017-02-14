<%@ page language="java" pageEncoding="UTF-8"  import="java.util.List,com.boco.eoms.evaluation.model.IndicatorScore,com.boco.eoms.evaluation.model.web.TdObjectModel ;"  %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css">
#toAuditOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
<script type="text/javascript">
   function openRuleDetail(ownindcid){ 
        var url="${app}/partner/evaluation/checkRule.do?method=showDetailByOwnIndcId&ownindcid="+ownindcid;
        window.open (url, "ruledetail", "height=450, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");  
   } 
</script> 

<form action="javascript:void(0)" method="post" id="scoreForm" name="scoreForm">
 <table  cellpadding="0" class="table" cellspacing="0">
    <caption>
        <div style="text-align:right;float:left">
			<c:if test="${xmhaszy}">此考核项目由考核专业组成</c:if>
		</div>
		<div style="text-align:right" id='totalInputScore'>
			评分总占比：<fmt:formatNumber value="${evaluationEntity.inputscore}" pattern="0.00"/>% &nbsp;&nbsp;&nbsp;换算成考核得分：<fmt:formatNumber value="${evaluationEntity.score}" pattern="0.00"/>分
		</div>
   </caption>
   <thead>
	 <tr>
	   <c:forEach items="${headList}"  var="headList" >
	     <c:if test="${headList.show}">
	      <th rowspan="${headList.rowspan}" colspan="${headList.colspan}"> ${headList.name} </th>  
	     </c:if>
	   </c:forEach>
	   <!-- 评分表头相关 -->
	    <th rowspan="1" colspan="1"> 规则详情 </th>
	    <th rowspan="1" colspan="1" style="width:100px;"> 指标值</th>  
	    <th rowspan="1" colspan="1"> 计算得分</th>
	    <th rowspan="1" colspan="1"> 评分</th>  
	    <th rowspan="1" colspan="1">没得满分原因</th> 
	 </tr>
   </thead>
   <tbody>
     <!-- 如果已经评过分，再一次评分是对历史评分的修改 -->
      <% 
      //注意变量声明不能放在 c:if内，放在某个if内 作用范围仅是这个if内,tomcat5 jboss4不支持泛型<E>，使用强转
        int trIndex = 0; 
        //List<IndicatorScore>  indicatorScoreHistoryList=null;
        //List<TdObjectModel> indicatorsList=(List<TdObjectModel>)request.getAttribute("indicatorsList");
        List   indicatorScoreHistoryList=null;
        List   indicatorsList=(List)request.getAttribute("indicatorsList");
        IndicatorScore indicatorScoreH=null; 
        TdObjectModel indicator=null;
       %>
     <c:if test="${hashistory}">
       <% 
        indicatorScoreHistoryList= (List)request.getAttribute("indicatorScoreHistoryList");    
       %>
     </c:if>
     
     <c:forEach items="${templateLlt}"  var="tdList">
       <%indicator=(TdObjectModel)indicatorsList.get(trIndex);         
       %>
       <c:if test="${hashistory}">
         <% indicatorScoreH=(IndicatorScore)indicatorScoreHistoryList.get(trIndex); %>
       </c:if>
       <tr>
         <c:forEach items="${tdList}" var="td">
           <c:if test="${td.show}">
              <c:if test="${td.datatype=='template' or td.datatype=='xiangmu'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}(${td.proportion}分) </td>
              </c:if>
               <c:if test="${td.datatype=='zhuanye'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}-专业(100.0) </td>
              </c:if>
              <c:if test="${td.datatype=='programme' or td.datatype=='indicator'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}(${td.fraction}分占${td.proportion}%) </td>
              </c:if>
              <c:if test="${td.datatype=='scoretyp'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name} </td>
              </c:if>
              <c:if test="${td.datatype=='indicatorextra'}"><td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name} </td></c:if>
           </c:if>
         </c:forEach>
         <!-- 规则详情  -->
         <td rowspan="1" colspan="1">                   
                <%
                  if("2".equals(indicator.getScoretyp())){ //算分
                %>
                 <a href="javascript:openRuleDetail('<%=indicator.getId()%>');">查看</a>
                <%}else{ %>
                     无
                <%} %>    
         </td>         
         <!-- 指标值 -->
         <td rowspan="1" colspan="1"> 
              <%
              if("2".equals(indicator.getScoretyp())){ //算分
             %>             
             <input type="text" class="text" id="indcvalue_<%=trIndex%>" name="indcvalue" 
                <c:if test="${hashistory}"> value='<%= indicatorScoreH==null?"":indicatorScoreH.getIndcvalue().toString() %>'</c:if>
             readonly='true' style="border:none;background:none;"/> 
             <%}else{ %>
                  
             <%} %>                          
         </td>        
         <!--  评分 -->
         <td rowspan="1" colspan="1" style="width:100px;">
            <input type="text" class="text" id="cmpinputscore_<%=trIndex%>" name="cmpinputscore" 
              <c:if test="${hashistory}"> value='<%= indicatorScoreH==null?"":(indicatorScoreH.getCmpinputscore()==null?"":indicatorScoreH.getCmpinputscore().toString()) %>'</c:if>
             readonly='true' style="border:none;background:none;"/> 
         </td>
         <td rowspan="1" colspan="1" style="width:100px;">
            <input type="text" class="text" id="inputscoreF_<%=trIndex%>" name="inputscore" 
            <% if("1".equals(indicator.getScoretyp())){//此情况为减分 %>  
              <c:if test="${hashistory}"> value='<%= indicatorScoreH==null?"":(indicator.getFraction()-indicatorScoreH.getScore()) %>分'</c:if>
            <%}else{ %>            
              <c:if test="${hashistory}"> value='<%= indicatorScoreH==null?"":indicatorScoreH.getScore().toString() %>分'</c:if>
            <%} %>             
             readonly='true' style="border:none;background:none;"/>     
            <input type="text" class="text" id="inputscore_<%=trIndex%>" name="inputscore" 
              <c:if test="${hashistory}"> value='<%= indicatorScoreH==null?"":indicatorScoreH.getInputscore().toString() %>%'</c:if>
             readonly='true' style="border:none;background:none;"/>     
         </td>    
         <td rowspan="1" colspan="1"> 
           <pre>
             <c:if test="${hashistory}"><%= indicatorScoreH==null?"":(indicatorScoreH.getRemark()==null?"":indicatorScoreH.getRemark())%></c:if>
           </pre>
         </td>
       </tr>
       <% trIndex++; %>
     </c:forEach> 
   </tbody>  
 </table>
 
</form>
<br/>
<c:if test="${isConfirmed and isOpenEvaluationSwitch}">
   <form id="form1" name="form1" >
     <table class="table">
       <tr><td colspan="${fn:length(auditLinkList)}">考核发起人签字: <eoms:id2nameDB id='${receiveLink.operateuserid}' beanId='tawSystemUserDao'/></td></tr>
       <tr><td colspan="${fn:length(auditLinkList)}">代维公司确认人签字: <eoms:id2nameDB id='${ScoreConfirmLink.operateuserid}' beanId='tawSystemUserDao'/></td></tr>
       <tr>
         <c:forEach var="auditLink" items="${auditLinkList}">
           <td>${auditLink.phasename}签字：<eoms:id2nameDB id='${auditLink.operateuserid}' beanId='tawSystemUserDao'/></td>
         </c:forEach>
       </tr>
     </table>
     <input type="button" value="导出考核结果" class="button" name="save" 
		onclick="javascript:location.href='${app}/partner/evaluation/exportAndImport.do?method=exportEvaluationData&id=${evaluationEntity.id}'" />								
   </form>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>