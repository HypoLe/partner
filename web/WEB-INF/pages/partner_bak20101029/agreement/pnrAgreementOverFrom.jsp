<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaTaskDetail"%>
<%@ page language="java" import="com.boco.eoms.eva.model.EvaKpiInstance"%>
<%@ page language="java" import="com.boco.eoms.base.util.StaticMethod"%>
<%@ page language="java" import="com.boco.eoms.eva.util.DateTimeUtil"%>
<style type="text/css">
#test-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>
  <%
  	Map scoreMap = (Map)request.getAttribute("scoreMap");
  	List detailList = (List)request.getAttribute("detailList");
  	List kpiInstance = (List)request.getAttribute("kpiInstance");
  	EvaTaskDetail evaTaskDetail = null;
  	EvaKpiInstance evaKpiInstance = null;
	String key = "";
	String sumKey = "";
	String numKey = "";
	String score = "";
	String avgScore = "";
	String textId = "";
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
		<td class="label" colspan="3">
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
		</tr>
		<%
		for(int i=0;i<detailList.size();i++){
			evaTaskDetail = (EvaTaskDetail)detailList.get(i);
			sumKey =  evaTaskDetail.getNodeId()+"_sum";
			numKey =  evaTaskDetail.getNodeId()+"_num";
			avgScore = String.valueOf(Float.parseFloat(StaticMethod.nullObject2String(scoreMap.get(sumKey),"0.0"))/StaticMethod.nullObject2int(scoreMap.get(numKey)));
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
		<html:text property="evaScore" styleClass="text medium"  style="width:97%;" value="<%=avgScore%>" />
		<input type='hidden' name='evaType'  value='eva'>
		<input type='hidden' name='evaId'  value=''>
		</td>
		</tr>
		<%
		}
		%>
		</table>
		</td>
	</tr>
	<%
	}%>
	<tr>
	  <td class="label">
        	其他考核项
      </td>
	<td  class="label" colspan="3">
		<img src="${app}/images/icons/add.gif" alt="添加考核项" onclick="javascript:addEva();" />
	<div id="evaTemplate">
		<table class="formTable">
			<tr>
					<td class="label" style="vertical-align:middle">
						考核项名称
					</td>
					<td class="content">
						<html:text property="evaName" styleClass="text medium"  style="width:95%;" value="" />
						<input type='hidden' name='evaType'  value='other'>
						<input type='hidden' name='evaId'  value=''>
					</td>			
					<td>
					得分
					</td>
					<td class="content">
						<html:text property="evaScore" styleClass="text medium"  style="width:97%;" value="" />
					</td>	
			</tr>
			<tr>
				<th colspan="4"><img src="${app}/images/icons/add.gif" alt="添加考核项"  align=right onclick="javascript:addEva();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
			</tr>
		</table>
	</div>
	<div id="evaDiv">
	<c:forEach var="eva" items="${lastScoreList}">
	<c:if test="${eva.evaType=='other'}">
		<table class="formTable">
			<tr>		
					<td class="label" style="vertical-align:middle">
						考核项名称
					</td>
					<td class="content">
						<html:text property="evaName" styleClass="text medium"  style="width:90%;" value="${eva.evaName}" />
						<input type='hidden' name='evaType'  value='other'>
						<input type='hidden' name='evaId'  value=''>
					</td>			
					<td class="label" style="vertical-align:middle">
						得分
					</td>
					<td class="content">
						<html:text property="evaScore" styleClass="text medium"  value="${eva.evaScore}" />
					</td>		
			</tr>		
			<tr>
				<th colspan="4"><img src="${app}/images/icons/add.gif" alt="添加考核内容" align=right onclick="javascript:addEva();" /><img align=right src="${app}/images/icons/list-delete.gif" alt="删除" onclick="removeNodes(parentNode.parentNode.parentNode);"/></th>
			</tr>			
		</table>
		</c:if>
	</c:forEach>
	
	</div>
	</td>
	</tr>
	<tr>
      <td class="label">
        	满意度
      </td>
      <td class="content" colspan="3">
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
      <td class="content" colspan="3">
      <!-- property中配一个空属性 -->
      		<textarea name="assessment" maxLength="1000" rows="2" style="width:98%;" id="remark">${pnrAgreementMain.assessment}</textarea>										
      </td>
    </tr>
	<tr>
		<td class="label">
			是否审核		
		</td>
		<td class="content" colspan="3">
	       <INPUT type="radio" name="toAudit" value="2" checked="true" onclick="changeAudit(this.value);">审核 
	       <INPUT type="radio" name="toAudit" value="1" onclick="changeAudit(this.value);">不审核
		</td>
	</tr>    
	<tr id="audit" style="display:block;" >		
	
		<td class="label">
			审核人
		</td>
		<td class="content" colspan="3">
				<%
				String panels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept'}]";
				%>
				<eoms:chooser id="test" category="[{id:'toOrg',text:'审核',limit:1,vtext:'请选择派发人'}]" panels="<%=panels%>"/>
		</td>
		<tr>
	</tr>    

	<html:hidden property="agreementId" value="${agreementId}" />
	</table>
        <input type="submit" value="提交"  class="button" />
</fmt:bundle>
</html:form>

<script type="text/javascript">
var evaStr = document.getElementById("evaTemplate").innerHTML;
document.getElementById("evaTemplate").removeNode(true);

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
    var auditTr = document.getElementById("audit");
    if(auditFlag=='2'){
      auditTr.style.display='block';
      }else{
      auditTr.style.display='none';
      }
    };	
</script>
<c:if test="${pnrAgreementMain.satisfy!=null}">
<script type="text/javascript">
	setRadio('satisfy',${pnrAgreementMain.satisfy});
</script>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>