<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
var roleTree;
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   v.custom = function(){
   		var pnrState = jq("#isSpotCheck").val();
   		if(pnrState!='10'){
   			if(confirm('工单尚未抽检是否确认提交?')){
		      	 return tijiao();
	      	}
   		}else{
   			return tijiao();
   		}
	}
  });

 function tijiao(){
     var _sumNeedCostOfPartyB=jq("#sumNeedCostOfPartyB").val();//新建派发环节的乙方费用总额
  	 var _sumCostOfPartyB=jq("#sumCostOfPartyB").val();//工单处理环节的乙方费用总额
  	 if(parseFloat(_sumCostOfPartyB) > parseFloat(_sumNeedCostOfPartyB)){
  	 	alert("工费大于预算，请调整！");
	  	return false;
  	 }else{
  	 	//提交的时候把不可编辑的都变成可编辑的，后台才能接收到值
		jq("#sceneMainDiv table tr").find("td").find("select").removeAttr("disabled");//定额
		jq("#projectAmount").attr("disabled",false);//项目金额估算
		jq("#compensate").attr("disabled",false);//赔补收入
		jq("#sumCostOfPartyB").attr("disabled",false);//工费
		jq("#kindRestitution").attr("disabled",false);//实物赔补
  	 	return true;
  	 }
 }

  function changeType1()
  {
		
  }
  function selectRes(){
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openRejectView';
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listBacklog"+condition;
  }
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/transfer_basis_after_interface.jsp"%>


<div style="height:10"></div>

<html:form action="/pnrTransferNewPrecheck.do?method=doOneWorkOrderArchiving" styleId="theform" >
			<input type="hidden" id="marker" name="marker" value="orderAudit" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.createWork}"><!-- 驳回人 -->
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->

			<input type="hidden" id="isSpotCheck" name="isSpotCheck" value="${pnrTransferOffice.state}" /><!-- 是否抽查标识 等于10 可以修改场景模板-->
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="step" name="step" value="15"><!-- 附件用 -->
            <input type="hidden" id="userId" name="userId" value="${userId}">
            <input type="hidden" id="linkName" name="linkName" value="orderAudit">
            <input type="hidden" id="orderAuditHandleState" name="orderAuditHandleState" value="handle" /><!-- 归档标识 -->
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />

            

	<!-- 审批结果 -->
    <fieldset id="fieldset0">
		<legend>
			审批结果
		</legend>	
	<table id="sheet" class="formTable" >				
			 <tr>
			    <td class="label">
			    	附件
				</td>	
				<td colspan="3">
			    <eoms:attachment name="sheetMain" property="sheetAccessories" 
			            scope="request" idField="orderAudit" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 		          				
			    </td>
		   </tr>
	</table>
</fieldset>

	<!-- 新建工单环节的乙方费用总额 -->   
	<input type="hidden" id="sumNeedCostOfPartyB"  name="sumNeedCostOfPartyB"  value="<fmt:formatNumber value='${pnrTransferOffice.sumNeedCostOfPartyB!=null&&pnrTransferOffice.sumNeedCostOfPartyB!=""?pnrTransferOffice.sumNeedCostOfPartyB:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" />
	    
    <!-- 审批结果 -->
    <fieldset id="fieldset01" style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			市公司抽检-场景模板
		</legend>
		<table id="sheet" class="formTable" style="width: auto;">
    	<tr>
			<td class="label" style="width: 10%">
				主场景*
			</td>
			<td class="content" style="width:15%">
				${pnrTransferOffice.workOrderTypeName }			
			</td>

			<td class="label" style="width: 10%">
				子场景
			</td>
			<td class="content"> 
			    <input type="text" class="text max" name="subTypeName" id="subTypeName"
					value=""
					alt="allowBlank:true" readonly="readonly" />
				<input name="childSceneSelect" id="childSceneSelect" value="" type="hidden" />
				<eoms:xbox id="provTree"
					dataUrl="${app}/xtree.do?method=loadOtherCostsSubScene&level=4&processInstanceId=${processInstanceId}&mainSceneId=${pnrTransferOffice.mainSceneId}&linkType=${flagLinkType}"
					rootId="" rootText="子场景" valueField="childSceneSelect" handler="subTypeName"
					textField="subTypeName" checktype="dict"  jsCfg="k:1"/>
			</td>
		</tr>
	</table>
			
		<!-- 场景选择模板 开始 -->
		<div id="sceneMainDiv">
			<!-- 记录选择的子场景ID --><input type="hidden" id="childSceneIds" name="childSceneIds" value="${childSceneIds}"/><!-- (childSceneIds)--><br /><br />
			<c:if test="${!empty echoChildSceneTableList}">
			<c:forEach items="${echoChildSceneTableList}" var="sceneTable" >
			<div>子场景-${sceneTable.childSceneName}:<br />
				<input type="hidden" value="${sceneTable.quotaField}" name="${sceneTable.childSceneId}_quotaField" id="${sceneTable.childSceneId}_quotaField" size="120">
				<input type="hidden" value="${sceneTable.existQuotaValue}" name="${sceneTable.childSceneId}_existQuotaValue" size="120" id="${sceneTable.childSceneId}_existQuotaValue">
				<input type="hidden" value="${sceneTable.assistExistQuotaValue}" name="${sceneTable.childSceneId}_assistExistQuotaValue" id="${sceneTable.childSceneId}_assistExistQuotaValue" size="120"><br>
				
				<table name="${sceneTable.childSceneId}_table" id="${sceneTable.childSceneId}_table" class="formTable" style="width: auto;">
					<tbody>
						<tr>
						<c:forEach items="${sceneTable.tableHeader}" var="tableHeader">
							<td align='center' style='white-space:nowrap;'>${tableHeader}</td>
						</c:forEach>
						</tr>
					
					<c:forEach items="${sceneTable.tableData}" var="ds">
						<tr>
							<c:forEach items="${ds}" var="d">
								<td>${d}</td>
							</c:forEach>
						</tr>
					</c:forEach>
					</tbody>
				</table>	
			</div>
			<br />
			</c:forEach>
			</c:if>
		</div> 
		</fieldset>
		<!-- 金额信息 -->
		<fieldset id="fieldset02" style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
			<legend>
				市公司抽检-金额信息
			</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label" style="width: 10%">
						项目金额估算*
					</td>
					<td class="content" style="width: 23%">
						<input type="text" class="text" id="projectAmount"  name="projectAmount" disabled="disabled" value="<fmt:formatNumber value='${pnrTransferOffice.daiweiAuditProjectAmount!=null&&pnrTransferOffice.daiweiAuditProjectAmount!=""?pnrTransferOffice.daiweiAuditProjectAmount:pnrTransferOffice.workerProjectAmount}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数,且小于50000'"/>(单位:元)
					</td>
					
					<td class="label" style="width: 10%">
					     实物赔补*
				    </td>
				    <td class="content" style="width: 23%">
					    <input type="text" class="text" id="kindRestitution"  name="kindRestitution" disabled="disabled" value="<fmt:formatNumber value='${pnrTransferOffice.kindRestitution!=null&&pnrTransferOffice.kindRestitution!=""?pnrTransferOffice.kindRestitution:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />"  onblur="checkSumFormat(this.value);"/>(单位:元)
				    </td>
	
					<td class="label" style="width: 10%">
						现金赔补*
					</td>
					<td class="content">
						<input type="text" class="text" id="compensate"  name="compensate" disabled="disabled" value="<fmt:formatNumber value='${pnrTransferOffice.compensate!=null&&pnrTransferOffice.compensate!=""?pnrTransferOffice.compensate:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />"  onblur="checkSumFormat2(this.value);"/>(单位:元)
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 10%">
						收支比
					</td>
					<td class="content" style="width: 24%">
						<div id="incomeRatioDiv"><fmt:formatNumber value='${pnrTransferOffice.daiweiAuditIncomeRatio!=null&&pnrTransferOffice.daiweiAuditIncomeRatio!=""?pnrTransferOffice.daiweiAuditIncomeRatio:pnrTransferOffice.workerIncomeRatio}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' /></div>
						<input type="hidden" id="incomeRatio" name="incomeRatio" value="<fmt:formatNumber value='${pnrTransferOffice.daiweiAuditIncomeRatio!=null&&pnrTransferOffice.daiweiAuditIncomeRatio!=""?pnrTransferOffice.daiweiAuditIncomeRatio:pnrTransferOffice.workerIncomeRatio}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" />
					</td>
					<td class="label" style="width: 10%">
						工费
					</td>
					<td class="content" style="width: 23%" colspan="5">
						<!-- 抽查环节的乙方费用总额（当前环节） -->  
						<input type="text" class="text" id="sumCostOfPartyB"  name="sumCostOfPartyB" disabled="disabled" value=""  />(单位:元) 
					</td>
				</tr>
	
			</table>
		</fieldset>
<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				归档
			</html:submit>&nbsp;&nbsp;
		</div>

</html:form>
<!-- 引入场景模板公用js代码  sceneJsUtil.jsp -->	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/sceneJsUtil.jsp"%>
<script type="text/javascript">
	jq(function(){
			var flag = "${pnrTransferOffice.state}";
			if(flag != '10'){
				jq("#fieldset0").css("display","none");
				jq("#fieldset01").css("display","none");
				jq("#fieldset02").css("display","none");
			}
	});
</script>

<%@ include file="/common/footer_eoms.jsp"%>
