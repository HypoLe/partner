<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
#test-chooser-centercell {background:transparent;padding:0;border:0}
#toExeOrg-chooser-centercell {background:transparent;padding:0;border:0}
.x-dlg-btns td {background-color:transparent !important;}
</style>

<script type="text/javascript">
	function getContractUrl(){
	 var partyB = document.getElementById("partyBId").value;
	 window.open ('${app}/partner/agreement/pnrAgreementMains.do?method=queryContractDo&partyBId='+partyB,'newwindow','height=600,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');
    }
	function getContractUrlNew(){
	 window.open ('${app}/partner/feeInfo/pnrFeeInfoMains.do?method=edit&contractNew=contractNew');
    }    
    function setCompactNostr(obj){
    	document.getElementById("compactNostr").innerHTML=obj;
    	document.getElementById("contractUrl").style.display="none";
    	document.getElementById("contractUrlNew").style.display="none";
    }
	function detail(){
		document.getElementById("subButton").disabled=true;
    	var url='${app}/partner/agreement/pnrAgreementAudits.do?method=audit&agreementId=${pnrAgreementMainForm.id}&id=${auditId}';
		location.href=url;
    }    
</script>
<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="partyAId" handler="partyA"
		textField="partyA"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="2" 
	  	rootText='单位' 
	  	valueField="partyBId" handler="partyB"
		textField="partyB"
		checktype="dept" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="partyAUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText='负责人' 
	  	valueField="partyAUser" handler="partyAUserName"
		textField="partyAUserName"
		checktype="user" single="true"		
	  ></eoms:xbox>
<eoms:xbox id="partyBUser" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText='负责人' 
	  	valueField="partyBUser" handler="partyBUserName"
		textField="partyBUserName"
		checktype="user" single="true"		
	  ></eoms:xbox>	
<html:form action="/pnrAgreementMains.do?method=save" styleId="pnrAgreementMainForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="pnrAgreementMain.form.heading"/></div>
	</caption>

	<tr>
		<td class="label" style="vertical-align:middle">
			服务协议甲方名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="partyA" id="partyA"  style="width:95%;"  value="${pnrAgreementMainForm.partyA}" maxLength="100" class="text medium" alt="allowBlank:false,vtext:'请输入服务协议甲方名称...'" onblur="changeRootId(xbox_partyAUser,document.getElementById('partyAId').value);"/>		
		    <input type="hidden" name="partyAId" id="partyAId" value="${pnrAgreementMainForm.partyAId}" maxLength="32" class="text medium" /> 
		</td>
		<td class="label" style="vertical-align:middle">
			服务协议乙方名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="partyB" id="partyB"  style="width:95%;"  value="${pnrAgreementMainForm.partyB}" maxLength="100" class="text medium" alt="allowBlank:false,vtext:'服务协议乙方名称...'" onblur="changeRootId(xbox_partyBUser,document.getElementById('partyBId').value);"/>		 
			<input type="hidden" name="partyBId" id="partyBId" value="${pnrAgreementMainForm.partyBId}" maxLength="32" class="text medium"  />	
		</td>
	</tr>
<!-- bww 5-8 -->
	<tr>
		<td class="label" style="vertical-align:middle">
			甲方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="text"  name="partyAUserName" id="partyAUserName" 
				styleClass="text"  style="width:95%;" 
				alt="allowBlank:false,vtext:''" value="<eoms:id2nameDB id="${pnrAgreementMainForm.partyAUser}" beanId="tawSystemUserDao" />" readonly="true"/>
			<html:hidden property="partyAUser" value="${pnrAgreementMainForm.partyAUser}" />
		</td>
		<td class="label" style="vertical-align:middle">
			乙方负责人&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="text" name="partyBUserName" id="partyBUserName" 
						style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="<eoms:id2nameDB id="${pnrAgreementMainForm.partyBUser}" beanId="tawSystemUserDao" />" readonly="true" />
			<html:hidden property="partyBUser" value="${pnrAgreementMainForm.partyBUser}" />		
		 </td>
	</tr>	
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.agreementName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<html:text property="agreementName" styleId="agreementName"
						styleClass="text"  style="width:98%;" 
						alt="allowBlank:false,vtext:''" value="${pnrAgreementMainForm.agreementName}" />
		</td>
		<!-- 
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.agreementNO" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<html:text property="agreementNO" styleId="agreementNO"
						styleClass="text"  style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${pnrAgreementMainForm.agreementNO}" />
		</td>	
		 -->
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.startTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="startTime" styleId="startTime"  style="width:95%;" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementMainForm.startTimeStr}" onclick="popUpCalendar(this, this,null,null,null,false,-1);" readonly="true" />
		</td>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.endTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"  style="width:95%;" 
						styleClass="text medium"
						 value="${pnrAgreementMainForm.endTimeStr}" onclick="popUpCalendar(this, this,null,null,null,false,-1);" alt="allowBlank:false,vtype:'moreThen',link:'startTime',vtext:'结束时间应晚于开始时间'" readonly="true" />
		</td>
	</tr>
	<tr>
		<td class="label">
			合同编号：
		</td>
		<td class="content" >
				<html:hidden property="compactNo" value="${pnrAgreementMainForm.compactNo}"/>
				<html:hidden property="compactName" value="${pnrAgreementMainForm.compactName}"/>
				<html:hidden property="contentId" value="${pnrAgreementMainForm.contentId}"/>
				<c:if test="${pnrAgreementMainForm.contentId==null||pnrAgreementMainForm.contentId==''}">
				<input type="button" name="contractUrl" id="contractUrl" value="关联已有合同" class="btn" onclick="getContractUrl();"/>
				<input type="button" name="contractUrlNew" id="contractUrlNew" value="新增关联合同" class="btn" onclick="getContractUrlNew();"/>
				</c:if>
				<span id='compactNostr'>${pnrAgreementMainForm.compactNo}<span>
		</td>
		
<!-- 
	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.specialCase" />
		</td>
		<td class="content" colspan="3">
		<textarea name="specialCase" id="specialCase" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:true,vtext:''" >${pnrAgreementMainForm.specialCase}</textarea>	
		</td>
	</tr>
 -->	
		<td class="label" style="vertical-align:middle">
			服务类型
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-agreement" dictId="serviceType" defaultId="Proxy"
							 selectId="serverType" beanId="selectXML" alt="allowBlank:false,vtext:'请选择服务类型'" />
		</td>
	</tr>
	<tr>
	<td  class="label" colspan="4">
	服务工作任务及考核内容:

	<div id="workDiv">
		<c:forEach var="work" items="${workList}"  varStatus="stauts">
			<div id="${work.id}">
			<table class="formTable">
			<tr>
			<th colspan="4"><b>第${stauts.count} 项：</b></th>
			</tr>
				<tr>		
					<td class="label" style="vertical-align:middle" >
						工作内容
					</td>
					<td class="content">
						${work.workContent}
						<html:hidden property="workContent" value="${work.workContent}" />
					</td>
					<td class="label" style="vertical-align:middle" >
						执行人
					</td>
					<td class="content">
							${work.toOrgUserName}
						<html:hidden property="toOrgUserName" value="${work.toOrgUserName}" />
						<html:hidden property="toOrgUser" value="${work.toOrgUser}" />
					</td>
					</tr>
					<tr>
					<td class="label" style="vertical-align:middle" >
						工作完成标准
					</td>
					<td class="content" colspan="3">
							${work.workStandard}
						<html:hidden property="workStandard" value="${work.workStandard}" />
					</td>				
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle">
						工作任务关联
					</td>
					<td class="content" >
							<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${work.workType}" beanId="id2nameXML" />
						<html:hidden property="workType" value="${work.workType}" />				
					</td>
					<td class="label" style="vertical-align:middle">
						工作任务执行周期
					</td>
					<td class="content">
						<eoms:dict key="dict-partner-agreement" dictId="workCycle" itemId="${work.workCycle}" beanId="id2nameXML" />
						<html:hidden property="workCycle" value="${work.workCycle}" />	
					</td>	
				</tr>	
				<tr>		
						<td class="label" style="vertical-align:middle">
							考核指标名称
						</td>
						<td class="content">
							${work.workEvaName}
						<html:hidden property="workEvaName" value="${work.workEvaName}" />
						</td>		
						<td class="label" style="vertical-align:middle">
							算法分类
						</td>
						<td class="content">
							<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${work.algorithmType}" beanId="id2nameXML" />
						<html:hidden property="algorithmType" value="${work.algorithmType}" />
						</td>	
				</tr>
				<tr>
						<td class="label" style="vertical-align:middle">
							指标详细算法
						</td>
						<td class="content" colspan="3">
							${work.workEvaContentByType}
						<html:hidden property="workEvaContent" value="${work.workEvaContent}" />
						</td>						
				</tr>
				<tr>
					<td class="label" style="vertical-align:middle">
						考核开始时间
					</td>
					<td class="content">
						${work.workEvaStartTimeStr}
						<html:hidden property="workEvaStartTime" value="${work.workEvaStartTimeStr}" />
					</td>
					<td class="label" style="vertical-align:middle">
						考核结束时间
					</td>
					<td class="content">
						${work.workEvaEndTimeStr}
						<html:hidden property="workEvaEndTime" value="${work.workEvaEndTimeStr}" />			
					</td>			
				</tr>
				<tr>		
					<td class="label" style="vertical-align:middle">
						所占分数
					</td>
					<td class="content">
							${work.workEvaWeight}
						<html:hidden property="workEvaWeight" value="${work.workEvaWeight}" />
					</td>	
					<td class="label" style="vertical-align:middle">
						考核周期
					</td>
					<td class="content">
						<eoms:dict key="dict-eva" dictId="cycle" itemId="${work.workEvaCycle}" beanId="id2nameXML" />
						<html:hidden property="workEvaCycle" value="${work.workEvaCycle}" />
						<img align=right src="${app}/images/icons/delwork.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
						<img align=right src="${app}/images/icons/editwork.gif" alt="编辑工作任务" onclick="javascript:show_windowsWork('${app}/partner/agreement/pnrAgreementMains.do?method=createWork','','${work.id}');" />
					</td>	
				</tr>
				<html:hidden property="workId" value="${work.id}" />
				</table>
				<hr />
			</div>
		</c:forEach>
	</div>	
	<img align=right src="${app}/images/icons/addwork.gif" alt="添加工作任务" onclick="javascript:show_windowsWork('${app}/partner/agreement/pnrAgreementMains.do?method=createWork','toolbar=no,scrollBars=yes,width=800,height=450','');" />
	</td>
	</tr>
		<tr>
			<td class="label" style="vertical-align:middle">
				工作任务特殊说明
			</td>
			<td class="content" colspan="3">
			<textarea name="workRemark" id="workRemark" maxLength="1000" rows="3" style="width:98%;" alt="maxLength:1000">${pnrAgreementMainForm.workRemark}</textarea>	
			</td>
		</tr>	
	
	<tr>
		<td  class="label" colspan="4">
			其他考核内容
		<div id="evaDiv">
			<c:forEach var="eva" items="${evaList}"  varStatus="stauts">
				<div id="${eva.id}">
					<table class="formTable">
						<tr><td> </td></tr>
						<tr>
							<td class="label" style="vertical-align:middle">
								本项考核开始时间
							</td>
							<td class="content">
								${eva.evaStartTimeStr}
								<html:hidden property="evaStartTime" value="${eva.evaStartTimeStr}" />
							</td>
							<td class="label" style="vertical-align:middle">
								本项考核结束时间
							</td>
							<td class="content">
								${eva.evaEndTimeStr}
								<html:hidden property="evaEndTime" value="${eva.evaEndTimeStr}" />			
							</td>			
						</tr>		
						<tr>	
							<td class="label" style="vertical-align:middle">
									考核指标名称
								</td>
								<td class="content">
									${eva.evaName}
								<html:hidden property="evaName" value="${eva.evaName}" />
							</td>			
							<td class="label" style="vertical-align:middle" >
								被考核人
							</td>
							<td class="content">
								${eva.toEvaUserName}
								<html:hidden property="toEvaUserName" value="${eva.toEvaUserName}" />
								<html:hidden property="toEvaUser" value="${eva.toEvaUser}" />
							</td>
						</tr>
						<tr>		
							<td class="label" style="vertical-align:middle">
								得分来源
							</td>
							<td class="content" >
									<eoms:dict key="dict-partner-agreement" dictId="workType" itemId="${eva.evaSource}" beanId="id2nameXML" />
								<html:hidden property="evaSource" value="${eva.evaSource}" />				
							</td>
							<td class="label" style="vertical-align:middle">
								算法分类
							</td>
							<td class="content">
								<eoms:dict key="dict-eva" dictId="algorithmType" itemId="${eva.evaAlgorithmType}" beanId="id2nameXML" />
							<html:hidden property="evaAlgorithmType" value="${eva.evaAlgorithmType}" />
							</td>					
						</tr>	
						<tr>
							<td class="label" style="vertical-align:middle">
								指标详细算法
							</td>
							<td class="content" colspan="3">
								${eva.evaContentByType}
							<html:hidden property="evaContent" value="${eva.evaContent}" />
							</td>						
						</tr>
						<tr>		
							<td class="label" style="vertical-align:middle">
								所占分数
							</td>
							<td class="content">
								${eva.evaWeight}
								<html:hidden property="evaWeight" value="${eva.evaWeight}" />
							</td>	
							<td class="label" style="vertical-align:middle">
								考核周期
							</td>
							<td class="content">
								<eoms:dict key="dict-eva" dictId="cycle" itemId="${eva.evaCycle}" beanId="id2nameXML" />
								<html:hidden property="evaCycle" value="${eva.evaCycle}" />
								<img align=right src="${app}/images/icons/deleva.gif" alt="删除上方任务项" onclick="removeNodes(parentNode.parentNode.parentNode);"/>		
								<img align=right src="${app}/images/icons/editeva.gif" alt="编辑工作任务" onclick="javascript:show_windowsEva('${app}/partner/agreement/pnrAgreementMains.do?method=createEva','','${eva.id}');" />
							</td>	
						</tr>
						<html:hidden property="evaId" value="${eva.id}" />
					</table>
				</div>
			</c:forEach>
		</div>
		<img src="${app}/images/icons/addeva.gif" alt="添加考核内容"  align=right  onclick="javascript:show_windowsEva('${app}/partner/agreement/pnrAgreementMains.do?method=createEva','toolbar=no,scrollBars=yes,width=800,height=450','');" />
		</td>
	</tr>
	<tr style="display:none">
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.settlingRule" />
		</td>
		<td class="content" colspan="3">
		<textarea name="settlingRule" id="settlingRule" maxLength="1000" rows="1" style="width:98%;" alt="allowBlank:true,vtext:''" >${pnrAgreementMainForm.settlingRule}</textarea>

		</td>
	</tr>

	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.remark" />
		</td>
		<td class="content" colspan="3">
		<textarea name="remark" id="remark" maxLength="1000" rows="2" style="width:98%;" alt="allowBlank:true,vtext:'',maxLength:1000" >${pnrAgreementMainForm.remark}</textarea>
		</td>
	</tr>
<!--  
	<tr>
		<td class="label">
			<fmt:message key="pnrAgreementMain.state" />
		</td>
		<td class="content">
			<html:text property="state" styleId="state"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${pnrAgreementMainForm.state}" />
		</td>
	</tr>
-->

	<tr>
		<td class="label" style="vertical-align:middle">
			<fmt:message key="pnrAgreementMain.accessories" />
		</td>
		<td class="content" colspan="3">
			<c:choose>
				<c:when test="${ not empty pnrAgreementMainForm.id }">
					<eoms:attachment name="pnrAgreementMainForm" property="accessoriesId" 
           				scope="request" idField="accessoriesId" appCode="agreement" /> 
				</c:when>
				<c:otherwise>
					<eoms:attachment idList="" idField="accessoriesId" appCode="agreement"/>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
<c:choose>
	<c:when test="${ not empty pnrAgreementMainForm.id }">
		<tr>
			<td class="label">
				确认结果			
			</td>
			<td class="content" colspan="3">
			       <INPUT type="radio" name="auditResult" value="2" onclick="detail()">确认通过
			       <INPUT type="radio" name="auditResult" value="1" checked="true" >进行修改
			</td>
		</tr>
		<tr>
	      <td class="label">
	        	确认意见
	      </td>
	      <td class="content" colspan="3">
	      <!-- property中配一个空属性 -->
	      		<textarea name="remarkConfirm" maxLength="1000" rows="3" style="width:98%;" id="remarkConfirm"></textarea>										
	      </td>
	    </tr>
	    			<html:hidden property="toExeOrg" value="${pnrAgreementMainForm.toExeOrg}" />
					<html:hidden property="toExeOrgType" value="${pnrAgreementMainForm.toExeOrgType}" />
	</c:when>
	<c:otherwise>
		<tr id='userTree'>		
			<td class="label">
				确认人&nbsp;<font color='red'>*</font>
			</td>
			<td>
					<%
					String panels = "[{text:'部门与人员',dataUrl:'/xtree.do?method=userFromDept',rootId:'1'}]";
					%>
					<eoms:chooser id="test" category="[{id:'toOrg',text:'确认',allowBlank:false,limit:1,vtext:'请选择确认人'}]" panels="<%=panels%>"/>
			</td>
			<td class="label">
				工作计划执行人&nbsp;<font color='red'>*</font>
			</td>
			<td>
					<%
					String toExeOrgPanels = "[{text:'人员',dataUrl:'/xtree.do?method=userFromDept',rootId:'2'}]";
					%>
					<eoms:chooser id="toExeOrg" category="[{id:'toExeOrg',text:'执行',allowBlank:false,limit:1,childType:'user',vtext:'请选择执行人'}]" panels="<%=toExeOrgPanels%>"/>
			</td>
		</tr>
	</c:otherwise>
</c:choose>	

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" id="subButton" value="提交" onclick="sub();"/>		
			<!-- 
			<c:if test="${not empty pnrAgreementMainForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/pnrAgreementMain/pnrAgreementMains.do?method=remove&id=${pnrAgreementMainForm.id}';
						location.href=url}"	/>
			</c:if>
			 -->
		</td>
	</tr>
</table>
<html:hidden property="auditId" value="${auditId}" />
<html:hidden property="id" value="${pnrAgreementMainForm.id}" />
<html:hidden property="createTime" value="${pnrAgreementMainForm.createTime}" />
<html:hidden property="createUser" value="${pnrAgreementMainForm.createUser}" />
<html:hidden property="createDept" value="${pnrAgreementMainForm.createDept}" />
<html:hidden property="state" value="${pnrAgreementMainForm.state}" />
<html:hidden property="agreementNO" value="${pnrAgreementMainForm.agreementNO}" />
<html:hidden property="isClosed" value="unClosed" />
</html:form>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrAgreementMainForm'});
});
	var workListSize = '${workListSize}';
	var count=0;
	var serverTypeValue = '${pnrAgreementMainForm.serverType}';
	if(serverTypeValue!=''){
		document.getElementById("serverType").value = serverTypeValue;
	}	
	if(workListSize!=0){
		count = workListSize;
	}
function changeRootId(xbox_obj,rootId) {   
	xbox_obj.defaultTree.root.id = rootId; 
	xbox_obj.reset();
	}
function removeNodes(obj) {
		if(confirm("确定要删除该子项？")){   
   			obj.removeNode(true);  
		}
	}
	function croot() {   
   	alert(xbox_tree3.treeRootId); 
   	alert(xbox_tree3.treeRootId); 
   	xbox_tree3.defaultTree.root.id = "-1";
	xbox_tree3.reset();

   	alert(xbox_tree3.treeRootId);
	}
	
	
function weightPay(thisId){
	var planWeights = document.getElementsByName("evaWeight");
	var workEvaWeights = document.getElementsByName("workEvaWeight");
	var planWeightCount = 0 ;
	for(var i = 0 ;planWeights.length>i ; i++){
		var planWeightValue = planWeights[i].value;
		if(planWeightCount==0){
			planWeightCount = planWeightValue;
		} else {
			if(planWeightValue!=null&&planWeightValue!=''){
				planWeightCount = account('+',planWeightCount,planWeightValue);
			}
		}
	}
	for(var i = 0 ;workEvaWeights.length>i ; i++){
		var workEvaWeightValue = workEvaWeights[i].value;
		if(planWeightCount==0){
			planWeightCount = workEvaWeightValue;
		} else {
			if(workEvaWeightValue!=null&&workEvaWeightValue!=''){
				planWeightCount = account('+',planWeightCount,workEvaWeightValue);
			}
		}
	}	
	span();
	if(planWeightCount>100){
		if(thisId!=''){
			var valueThis = document.getElementById(thisId).value;
			var outValue = account('-',planWeightCount,100);
			outValue = account('-',valueThis,outValue);
			document.getElementById(thisId).value = '';
			
			if(thisId.length>=14){
				document.getElementById("numberEva"+thisId.substring(13)).innerHTML = '累计分数大于100分，请重新填写。';
			} else {
				document.getElementById("number"+thisId.substring(9)).innerHTML = '累计分数大于100分，请重新填写。';
			}
		}
		return false;
	}else {
		if(planWeightCount<100&&thisId==null){
			alert("累计分数不足100分，请补充");
			return false;
		}
	}

	return true;
}
/** 
* 清除所有span的值
*/ 
function span(){
	var planWeights = document.getElementsByName("evaWeight");
	var workEvaWeights = document.getElementsByName("workEvaWeight");
	for(var i = 1 ;planWeights.length>=i ; i++){
		var planWeightId = planWeights[i-1].id;
		document.getElementById("number"+planWeightId.substring(9)).innerHTML = '';
	}
	for(var i = 1 ;workEvaWeights.length>=i ; i++){
		var workEvaWeightId = workEvaWeights[i-1].id;
		document.getElementById("numberEva"+workEvaWeightId.substring(13)).innerHTML = '';
	}	
}
/** 
*实现两个数据的简单计算；计算结果进行四舍五入，只保留小数据点后两位； 
*num1，第一个数据。如果是减法与除法；则作为被减数、被除数； 
*num2，第二个数据。如果是减法与除法：则作为减数、除数； 
*str，运算符号，目前只接受:+、-、*、/:分别对应：加法、减法、乘法、除法； 
*返回计算结果；计算结果进行四舍五入，只保留小数据点后两位； 
*/ 
function account(str,num1,num2){ 
	if(num1!=""&&num2!=""&&str!=""){ 
		num1 = parseFloat(num1); 
		num2= parseFloat(num2); 
		var rs=0.0; 
		  if(str=="+"){ 
		   rs=num1+num2; 
		   return (Math.round(rs*100)/100);
		   //保保留小数点后两位数； 
		   //如果要保留三位则改为:Math.round(rs*1000)/1000; 
		   //如果要保留四位则改为:Math.round(rs*10000)/10000;.....以次类推
		  } 
		  if(str=="-"){ 
		   rs=num1-num2; 
		   return (Math.round(rs*100)/100); 
		  } 
		  if(str=="*"){ 
		   rs=num1*num2; 
		   return (Math.round(rs*100)/100); 
		  } 
		  if(str=="/"){ 
		   if(num2!="0"){ 
		    rs=num1/num2; 
		    return (Math.round(rs*100)/100); 
		   }else{ 
		    alert("Error"); 
		   } 
		  } 
	} 
}
function sub(){

	if(v.check()){
       $("pnrAgreementMainForm").submit();
	}	
}
function show_windowsWork(myurl,props,tableId)
{  
  var newWindow;
  var temp_props = props;
  var workEvaWeights = document.getElementsByName("workEvaWeight");
  var planWeights = document.getElementsByName("evaWeight");
  var toOrgId = '';
  var editWeight ='';//要编辑的记录权重
  var allWeight = 100;
  var serviceType = '';
  for(var i = 0 ;i<planWeights.length ; i++){
  	if(allWeight==0||planWeights[i].value==0){
  		allWeight = allWeight - parseFloat(planWeights[i].value);
  	} else {
 	 	allWeight = account('-',allWeight,planWeights[i].value);
  	}  
  }
  for(var i = 0 ;i<workEvaWeights.length ; i++){
  	if(allWeight==0||workEvaWeights[i].value==0){
  		allWeight = allWeight - parseFloat(workEvaWeights[i].value);
  	} else {
 	 	allWeight = account('-',allWeight,workEvaWeights[i].value);
  	}    
  }
  if(tableId!=''){
  	editWeight = document.getElementById(tableId).getElementsByTagName("input")[11].value;
  	if(allWeight==0||editWeight.value==0){
  		allWeight = allWeight + parseFloat(editWeight);
  	} else {
 	 	allWeight = account('+',allWeight,editWeight);
  	}   	
  	toOrgId = document.getElementById(tableId).getElementsByTagName("input")[2].value;
  }
  serviceType = document.getElementById("serverType").value;
  myurl = myurl+'&allWeight='+allWeight+'&tableMax='+workEvaWeights.length+'&tableId='+tableId+ '&serverType='+serviceType+'&toOrgId='+toOrgId;
  newWindow = window.open(myurl,'',temp_props);
 
}

function show_windowsEva(myurl,props,tableId)
{  
  var newWindow;
  var temp_props = props;
  var workEvaWeights = document.getElementsByName("workEvaWeight");
  var planWeights = document.getElementsByName("evaWeight");
  var toOrgId = '';
  var editWeight ='';//要编辑的记录权重
  var allWeight = 100;
  for(var i = 0 ;i<planWeights.length ; i++){
  	if(allWeight==0||planWeights[i].value==0){
  		allWeight = allWeight - parseFloat(planWeights[i].value);
  	} else {
 	 	allWeight = account('-',allWeight,planWeights[i].value);
  	}   
  }
  for(var i = 0 ;i<workEvaWeights.length ; i++){
  	if(allWeight==0||workEvaWeights[i].value==0){
  		allWeight = allWeight - parseFloat(workEvaWeights[i].value);
  	} else {
 	 	allWeight = account('-',allWeight,workEvaWeights[i].value);
  	}    
  }
  if(tableId!=''){
  	editWeight = document.getElementById(tableId).getElementsByTagName("input")[8].value;
  	if(allWeight==0||editWeight.value==0){
  		allWeight = allWeight + parseFloat(editWeight);
  	} else {
 	 	allWeight = account('+',allWeight,editWeight);
  	}    	
  	toOrgId = document.getElementById(tableId).getElementsByTagName("input")[4].value;
  }
  serviceType = document.getElementById("serverType").value;
  myurl = myurl+'&allWeight='+allWeight+'&tableId='+tableId+'&toOrgId='+toOrgId+'&serviceType='+serviceType;
  newWindow = window.open(myurl,'',temp_props);
 
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>