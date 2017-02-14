<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaTaskDetail"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaKpiInstance"%>
<%@ page language="java" import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page language="java" import="com.boco.eoms.eva.util.DateTimeUtil"%>
<%@ page language="java" import="com.boco.eoms.partner.agreement.model.PnrAgreementLastScore"%>
<style type="text/css">
#test-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
  <%
  	Map scoreMap = (Map)request.getAttribute("scoreMap");
  	List detailList = (List)request.getAttribute("detailList");
  	List kpiInstance = (List)request.getAttribute("kpiInstance");
  	List lastScoreList = (List)request.getAttribute("lastScoreList");
  	EvaTaskDetail evaTaskDetail = null;
  	EvaKpiInstance evaKpiInstance = null;
  	PnrAgreementLastScore lastScore = new PnrAgreementLastScore();
  	String auditId = StaticMethod.nullObject2String(request.getAttribute("auditId"));
	String key = "";
	String sumKey = "";
	String numKey = "";
	String score = "";
	String avgScore = "";
	String textId = "";
	String evaWeight = "";
  %>

<html:form action="/pnrAgreementMains.do?method=agreementCloseAudit" styleId="pnrAgreementAuditForm" method="post">

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<table class="formTable">
	<caption>
		<div class="header center">即将结束协议</div>
	</caption>
	<%
	if(kpiInstance!=null){
	%>

	<tr>
		<td class="label" >考核得分</td>
		<td class="label" colspan="8">
		<table class="formTable">
		<tr>
		<td>
		指标名称
		</td>
		<%
		for(int j=0;j<kpiInstance.size();j++){
				evaKpiInstance = (EvaKpiInstance)kpiInstance.get(j);
		 %>
		 <td>第<%=evaKpiInstance.getTime() %>次</td>
		 <%
		}
		 %>
		 <td>平均分</td>
		 <td>最终分</td>
		 <td>打分说明</td>
		</tr>
		<%
		for(int i=0;i<detailList.size();i++){
			evaTaskDetail = (EvaTaskDetail)detailList.get(i);
			if(lastScoreList.size()>0){
				lastScore = (PnrAgreementLastScore)lastScoreList.get(i);
			}
			sumKey =  evaTaskDetail.getNodeId()+"_sum";
			numKey =  evaTaskDetail.getNodeId()+"_num";
			java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
			avgScore = df.format(Float.parseFloat(StaticMethod.nullObject2String(scoreMap.get(sumKey),"0.0"))/StaticMethod.nullObject2int(scoreMap.get(numKey)));
			textId = "avgScore_"+evaTaskDetail.getNodeId();
		%>
		<tr>
		<td>
		<eoms:id2nameDB id="<%=evaTaskDetail.getNodeId() %>" beanId="evaTreeDao" />
		<input type='hidden' name='evaName'  value='<eoms:id2nameDB id="<%=evaTaskDetail.getNodeId() %>" beanId="evaTreeDao" />'>
		</td>
		<%
		for(int j=0;j<kpiInstance.size();j++){
				evaKpiInstance = (EvaKpiInstance)kpiInstance.get(j);
				key = evaTaskDetail.getNodeId()+"_"+evaKpiInstance.getTime();
				score = StaticMethod.nullObject2String(scoreMap.get(key));
		 %>
		<td>
		<%=score %>
		</td>
		<%
		}
		%>		
		<td>
		<%=avgScore%>
		</td>
		<td>
		<%
		if(lastScoreList.size()>0){
			evaWeight = lastScore.getEvaWeight();
		%>
		<html:text property="evaScore" onblur="getSum('evaScore','evaScore_span');confirmWeight();getAllSum('allEvaScore_span');" styleClass="text medium"  style="width:97%;" value="<%=lastScore.getEvaScore()%>" />
		<%
		}else{
		%>
		<html:text property="evaScore" styleClass="text medium"  style="width:97%;" value="<%=avgScore%>"  onblur="getSum('evaScore','evaScore_span');confirmWeight();getAllSum('allEvaScore_span');" />
		<%
		}
		%>
		<input type='hidden' name='evaType'  value='eva'>
		<input type='hidden' name='evaId'  value=''>
		</td>
		<td>
			<textarea name="evaContent" maxLength="1000" style="width:100%" rows="2" id="evaContent"><%=StaticMethod.nullObject2String(lastScore.getEvaContent())%></textarea>
		</td>
		</tr>
		<%
		}
		%>
		</table>
		<table class="formTable">
			<tr>
				<td class="label"  colspan="2">总分: <span id='evaScore_span' style='color:red'></span>分</td>
				<td class="label"  colspan="2"  align=right>权重:<html:text property="evaWeight" style="width:25px" value="<%=evaWeight %>"  onblur="confirmWeight();getAllSum('allEvaScore_span');"/>%</td>
			</tr>
		</table>
		</td>
	</tr>
	<%
	}%>
	<tr>
	  <td class="label">
        	其他考核项
      </td>
	<td  class="label" colspan="8">
		<img src="${app}/images/icons/addeva.gif" alt="添加考核项" onclick="javascript:addEva();" />
	<div id="evaTemplate">
		<table>
			<tr>
					<td class="label" style="vertical-align:middle">
					</td>
					<td class="label">
					</td>
					<td class="label">
					</td>
					<td class="label">
					</td>
			</tr>
			<tr>
					<td class="content" style="vertical-align:middle">
						<html:text property="otherEvaName" style="width:100%;" value="${eva.evaName}" />
						<input type='hidden' name='otherEvaType'  value='other'>
						<input type='hidden' name='otherEvaId'  value=''>
					</td>
					<td  style="width:250px;vertical-align:middle;" class="content">
						<html:text property="otherEvaScore" style="width:100%;" onblur="confirmWeight();getAllSum('allEvaScore_span');" styleClass="text medium"  value="${eva.evaScore}" />
					</td>
					<td class="content">
					<textarea name="otherEvaContent" maxLength="1000" style="width:100%;" rows="2" id="otherEvaContent">${eva.evaContent}</textarea>
					</td>
					<td class="content" style="vertical-align:middle">
						<html:text property="otherEvaWeight" style="width:25px" value="${eva.evaWeight}"   onblur="confirmWeight();getAllSum('allEvaScore_span');" />%
					</td>
			</tr>
			<tr>
				<th colspan="8"><img src="${app}/images/icons/addeva.gif" alt="添加考核内容" align=right onclick="javascript:addEva();" /><img align=right src="${app}/images/icons/deleva.gif" alt="删除" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
			</tr>
		</table>
	</div>
	<% int index = 0; %>
	<div id="evaDiv">
		<table>
			<tr>
					<td class="label" style="vertical-align:middle">
						考核项名称
					</td>
					<td class="label">
						得分
					</td>
					<td class="label">
					打分说明
					</td>
					<td class="label">
						权重
					</td>
			</tr>
			<tr>
					<td class="content" style="vertical-align:middle">
					</td>
					<td  style="width:250px;vertical-align:middle;" class="content">
					</td>
					<td class="content">
					</td>
					<td class="content" style="vertical-align:middle">
					</td>
			</tr>
		</table>
	<c:forEach var="eva" items="${lastScoreList}">
	<c:if test="${eva.evaType=='other'}">
			<% if(index == 0){//打印表头 %>			
			<table>
			<tr>
					<td class="label" style="vertical-align:middle">
					</td>
					<td class="label">
					</td>
					<td class="label">
					</td>
					<td class="label">
					</td>
			</tr>
			<tr>
					<td class="content" style="vertical-align:middle">
						<html:text property="otherEvaName" style="width:100%;" value="${eva.evaName}" />
						<input type='hidden' name='otherEvaType'  value='other'>
						<input type='hidden' name='otherEvaId'  value=''>
					</td>
					<td  style="width:250px;vertical-align:middle;" class="content">
						<html:text property="otherEvaScore" style="width:100%;" onblur="confirmWeight();getAllSum('allEvaScore_span');" styleClass="text medium"  value="${eva.evaScore}" />
					</td>
					<td class="content">
					<textarea name="otherEvaContent" maxLength="1000" style="width:100%;" rows="2" id="otherEvaContent">${eva.evaContent}</textarea>
					</td>
					<td class="content" style="vertical-align:middle">
						<html:text property="otherEvaWeight" style="width:25px" value="${eva.evaWeight}"   onblur="confirmWeight();getAllSum('allEvaScore_span');" />%
					</td>
			</tr>
			<tr>
				<th colspan="8"><img src="${app}/images/icons/addeva.gif" alt="添加考核内容" align=right onclick="javascript:addEva();" /><img align=right src="${app}/images/icons/deleva.gif" alt="删除" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
			</tr>
			</table>
			<% }else{ %>
			<table>
			<tr>
					<td class="label" style="vertical-align:middle">
					</td>
					<td class="label">
					</td>
					<td class="label">
					</td>
					<td class="label">
					</td>
			</tr>
			<tr>
					<td class="content" style="vertical-align:middle">
						<html:text property="otherEvaName" style="width:100%;" value="${eva.evaName}" />
						<input type='hidden' name='otherEvaType'  value='other'>
						<input type='hidden' name='otherEvaId'  value=''>
					</td>
					<td  style="width:250px;vertical-align:middle;" class="content">
						<html:text property="otherEvaScore" style="width:100%;" onblur="confirmWeight();getAllSum('allEvaScore_span');" styleClass="text medium"  value="${eva.evaScore}" />
					</td>
					<td class="content">
					<textarea name="otherEvaContent" maxLength="1000" style="width:100%;" rows="2" id="otherEvaContent">${eva.evaContent}</textarea>
					</td>
					<td class="content" style="vertical-align:middle">
						<html:text property="otherEvaWeight" style="width:25px" value="${eva.evaWeight}"   onblur="confirmWeight();getAllSum('allEvaScore_span');" />%
					</td>
			</tr>
			<tr>
				<th colspan="8"><img src="${app}/images/icons/addeva.gif" alt="添加考核内容" align=right onclick="javascript:addEva();" /><img align=right src="${app}/images/icons/deleva.gif" alt="删除" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
			</tr>
			</table>
			<% } %>
			<% index = index + 1; %>	
		</c:if>
	</c:forEach>
			
	
	</div>
	</td>
	</tr>
	<tr>
      <td class="label">
        	工作总结
      </td>
      <td class="content" colspan="8">
      	<eoms:attachment name="pnrAgreementMain" property="sumUpAccessories" scope="request" idField="sumUpAccessories" appCode="agreement" viewFlag="Y"/> 								
      </td>
    </tr>
	<tr>
      <td class="label">
        	满意度
      </td>
      <td class="content" colspan="8">
      <!-- property中配一个空属性 -->
      		<INPUT type="radio" name="satisfy" value="1">非常不满意&nbsp;&nbsp;&nbsp;
      		<INPUT type="radio" name="satisfy" value="2">不满意&nbsp;&nbsp;&nbsp;
		    <INPUT type="radio" name="satisfy" value="3" checked="true">一般&nbsp;&nbsp;&nbsp;
		    <INPUT type="radio" name="satisfy" value="4">满意&nbsp;&nbsp;&nbsp;
		    <INPUT type="radio" name="satisfy" value="5">非常满意									
      </td>
    </tr>
	<tr>
      <td class="label">
        	综合评价
      </td>
      <td class="content" colspan="8">
      <!-- property中配一个空属性 -->
      		<textarea name="assessment" maxLength="1000" rows="2" style="width:98%;" id="remark">${pnrAgreementMain.assessment}</textarea>										
      </td>
    </tr>
    <%
if("".equals(auditId)){
%>
	<tr id="toAuditDiv" >
		<td class="label">
			<span id='toAuditTitle_span' >是否审核</span>		
		</td>
		<td class="content" >
	       <INPUT type="radio" name="toAudit" value="2" checked="true" onclick="changeAudit(this.value);"><span id='toAudit_2_span' >审核</span>
	       <INPUT type="radio" name="toAudit" value="1" onclick="changeAudit(this.value);"><span id='toAudit_1_span' >不审核</span>
		</td>
		<td class="label">
			最终得分		
		</td>
		<td class="content">
	       <span id='allEvaScore_span' style='color:red'></span>
	       <html:hidden property="allEvaScore" value="" />
		</td>
	</tr>    
	<tr id="userTree" >		
		<td class="label">
			审核人
		</td>
		<td  colspan="8">
				<%
				String panels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept',rootId:'1'}]";
				%>
				<eoms:chooser id="test" category="[{id:'toOrg',text:'审核',limit:1,vtext:'请选择派发人'}]" panels="<%=panels%>"/>
		</td>

	</tr>    
	
<%
	}else{
%>	
	
	<tr  id="auditResultDiv" >
	
		<html:hidden property="auditId" value="${auditId}" />
		<td class="label">
			审核结果			
		</td>
		<td class="content">
		       <INPUT type="radio" name="auditResult" value="2" checked="true">通过，可以关闭
		       <INPUT type="radio" name="auditResult" value="1">不通过，不能关闭
		</td>
		<td class="label">
			最终得分		
		</td>
		<td class="content">
	       <span id='allEvaScore_span' style='color:red'></span><input type="button" value="计算"  align="right" class="button"  onclick="getAllSum('allEvaScore_span')"/>
	       <html:hidden property="allEvaScore" value="" />
		</td>
	</tr>
	<tr  id="auditRemarkDiv" >
      <td class="label">
        	审核意见
      </td>
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
      		<textarea name="auditRemark" maxLength="1000" rows="2" style="width:98%;" id="auditRemark"></textarea>										
      </td>
    </tr>
	
	<%
	}
    %>
	
	
	
	
	
	
	
	

	<html:hidden property="agreementId" value="${agreementId}" />
	</table>
		<input type="button" value="提交" onclick="getAllSum('allEvaScore_span');checkOver();"  class="button" />
        <input id="btnOk" type="submit" value="提交" style="display: none" class="button" />
        <c:if test="${auditId==''}">
        <input type="button" value="驳回"  class="button"  onClick="javascript:var agreementId = '${agreementId}';
		                												var url='${app}/partner/agreement/pnrAgreementMains.do?method=rejectWorkSummary';
		                												var win_props = 'toolbar=no,scrollBars=yes,width=800,height=450';
		                												url = url + '&agreementId=' + agreementId ;window.location=url;"/>
		</c:if>                												
</fmt:bundle>
</html:form>

<script type="text/javascript">
var evaStr = document.getElementById("evaTemplate").innerHTML;
var historyRecordNum = 0;
document.getElementById("evaTemplate").removeNode(true);
	getSum('evaScore','evaScore_span');
<%
if(lastScoreList.size()>0){
%>

	getAllSum('allEvaScore_span');
	
<%
			}
%>

function addEva() {
	Ext.DomHelper.append('evaDiv', 
		{tag:'div', 			
		html:evaStr
        }
    );
}
function removeNodes(obj) {   
   	obj.removeNode(true);  
	}
	//通过值修改所选中的单选按钮

function setRadio(name,sRadioValue){        //传入radio的name和选中项的值
	var oRadio = document.getElementsByName(name);
	for(var i=0;i<oRadio.length;i++) //循环
	{
        if(oRadio[i].value==sRadioValue) //比较值
        {
         	oRadio[i].checked=true; //修改选中状态
         	break; //停止循环
        }
	}
}
function changeAudit(auditFlag)
    {
    var auditTr = document.getElementById("userTree");
    if(auditFlag=='2'){
      auditTr.style.display='block';
      }else{
      auditTr.style.display='none';
      }
    };
function fomatFloat(src,pos){   
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);   
   }  	
function getSum(score,scoreShow){
	var score = document.getElementsByName(score);
	var sumScore = 0;
	for(var i=0;i<score.length;i++){
		sumScore = parseFloat(sumScore) +parseFloat(score[i].value);
	}
	document.getElementById(scoreShow).innerHTML=sumScore;
}
function getAllSum(scoreShow){
	var score = document.getElementsByName('evaScore');
	var evaWeight = document.getElementById('evaWeight').value;
	var otherScore = document.getElementsByName('otherEvaScore');
	var otherEvaWeight = document.getElementsByName('otherEvaWeight');
	var sumAllScore = 0;
	var sumScore = 0;
	for(var i=0;i<score.length;i++){
		sumScore = parseFloat(sumScore) +parseFloat(score[i].value);
	}
	if(evaWeight!=''){
		sumAllScore =  parseFloat(sumAllScore)+parseFloat(sumScore)*parseFloat(evaWeight)/100;
		}
	for(var i=0;i<otherScore.length;i++){
		if(otherEvaWeight[i].value!=''){
		sumAllScore = parseFloat(sumAllScore) + parseFloat(otherScore[i].value)*parseFloat(otherEvaWeight[i].value)/100;
		}
	}
	document.getElementById(scoreShow).innerHTML=fomatFloat(sumAllScore);
}
function fomatFloat(score)
	{ 
		return Math.round(score*Math.pow(10, 2))/Math.pow(10, 2); 
	}
function confirmWeight(){ 
		var evaWeight = document.getElementById('evaWeight').value;
		var otherEvaWeight = document.getElementsByName('otherEvaWeight');
		var sumWeight = 0;
		if(evaWeight!=''){
			sumWeight =  parseFloat(sumWeight)+parseFloat(evaWeight);
		}
		for(var i=0;i<otherEvaWeight.length;i++){
			if(otherEvaWeight[i].value!=''){
				sumWeight = parseFloat(sumWeight) + parseFloat(otherEvaWeight[i].value);
			}
		}
	}
	
//检查权重之和
function checkOver(){
	var evaWeight = document.getElementById('evaWeight').value;
		var otherEvaWeight = document.getElementsByName('otherEvaWeight');
		var sumWeight = 0;
		if(evaWeight!=''){
			sumWeight =  parseFloat(sumWeight)+parseFloat(evaWeight);
		}
		for(var i=0;i<otherEvaWeight.length;i++){
			if(otherEvaWeight[i].value!=''){
				sumWeight = parseFloat(sumWeight) + parseFloat(otherEvaWeight[i].value);
			}
		}
		if(sumWeight>100){
			var isPass = confirm("总权重超过100,是否确认提交?");
			if(isPass){
				document.getElementById('btnOk').click();
			}
		}
		if(sumWeight<100){
			var isPass = confirm("总权重小于100,是否确认提交?");
			if(isPass){
				document.getElementById('btnOk').click();
			}
		}
		if(sumWeight==100){
			var isPass = confirm("确认提交该评分");
			if(isPass){
				document.getElementById('btnOk').click();
			}
		}
	}
</script>
<c:if test="${pnrAgreementMain.satisfy!=null}">
<script type="text/javascript">
	setRadio('satisfy',${pnrAgreementMain.satisfy});
</script>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>