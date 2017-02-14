<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>



<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ftraininfoForm'});
	v.custom = function(){ 
	var trainPopulace= parseInt($('trainPopulace').value)
	var eligibPopulace= parseInt($('eligibPopulace').value)
	if(trainPopulace<eligibPopulace){
       alert("培训人数不能少于合格人数");
        return false; 
      } 
     return true;
   }
	setTabs();
});

    function setTabs() {
		var pageType = '${PAGE_TYPE}';
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.remove();}});
		var tabs = new Ext.TabPanel('info-page');
		tabs.addTab('base-info', '内容信息 ');
		if(pageType != null && pageType !="ADD_PAGE") {
	       	tabs.addTab('history-info', '审批信息 ');
		}
   		tabs.activate(0);
	}
	
	
window.onload = function(){
    var speciality = '${ftraininfoForm.speciality}';
	if(speciality!=''){
 		document.getElementById("speciality").value = speciality;
		changeFacility(1);
	}
}
function numberCheck(obj){
         var price=/^\d+$/;
         var numberValue=obj.value; 
       
         if(!numberValue.match(price) && ""!=numberValue){
         obj.value='';
         alert("格式不正确，人数只能为非负整数");
					
         }
}
</script>


<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>

<div id="info-page">
	<div id="base-info" class="tabContent">
		<div class="header center">
			<b>厂家培训事件信息</b>
		</div>
		
<html:form action="/ftraininfos.do?method=save" styleId="ftraininfoForm" method="post"> 
<html:hidden property="trainId" value="${ftraininfoForm.trainId}" />
<html:hidden property="trainEligibRate" value="${ftraininfoForm.trainEligibRate}" />
<html:hidden property="createTime" value="${ftraininfoForm.createTime}" />
<html:hidden property="takeCountOf" value="1" />
<html:hidden property="total" value="1" />
<table class="formTable">
	
	<tr>
 <td colspan="4">
 <div class="ui-widget-header">事件信息</div>
 </td>
 </tr>
 
 
	<tr>
	<td class="label">
			省份*
		</td>
		<td class="content">
			<html:text property="province" styleId="province"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ftraininfoForm.province}" />
		</td>
	
		<td class="label">
			事件名称*
		</td>
		<td class="content">
			<html:text property="eventName" styleId="eventName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ftraininfoForm.eventName}" />
		</td>

</tr>
      <tr>
		<td class="label">
			级别*
		</td>
		<td class="content">
			<eoms:comboBox name="trainLevel" id="trainLevel" initDicId="1121501" defaultValue="${ftraininfoForm.trainLevel}"
			    alt="allowBlank:false,vtext:'请选择等级...'"/>		
		</td>
	</tr>

 <tr>
 <td colspan="4">
 <div class="ui-widget-header">厂家信息</div>
 </td>
 </tr>
	<tr>
		<td class="label">
			厂家*
		</td>
		<td class="content">
			<eoms:comboBox name="factory" id="factory" initDicId="1121502" defaultValue="${ftraininfoForm.factory}"
			    alt="allowBlank:false,vtext:'请选择厂家...'"/>		
		</td>

		<td class="label">
			专业*
		</td>
		<td class="content">
			<eoms:comboBox name="speciality" id="speciality" initDicId="11216" defaultValue="${ftraininfoForm.speciality}"
			    alt="allowBlank:false,vtext:'请选择专业...'"  />	
		</td>
	</tr>
<tr>
 <td colspan="4">
 <div class="ui-widget-header">统计信息</div>
 </td>
 </tr>
	<tr>
		<td class="label">
			开始时间*
		</td>
		<td class="content">
			<html:text property="beginTime" styleId="beginTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间',allowBlank:false"
						 value="${ftraininfoForm.beginTime}" readonly="true"/>
		</td>

		<td class="label">
		结束时间*
		</td>
		<td class="content">
			<html:text property="endTime" styleId="endTime"
						styleClass="text medium"
						onclick="popUpCalendar(this,this,null,null,null,true,-1);"
						alt="vtype:'moreThen',link:'beginTime',vtext:'结束时间不能早于开始时间',allowBlank:false"
						 value="${ftraininfoForm.endTime}" readonly="true"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			培训人数*
		</td>
		<td class="content">
			<html:text property="trainPopulace" styleId="trainPopulace"
						styleClass="text medium"  
						alt="vtype:'number',vtext:'格式不正确，人数只能为非负整数',allowBlank:false"
						value="${ftraininfoForm.trainPopulace}" />
		</td>

		<td class="label">
			合格人数*
		</td>
		<td class="content">
			<html:text property="eligibPopulace" styleId="eligibPopulace"
						styleClass="text medium"   
						alt="vtype:'number',vtext:'格式不正确，人数只能为非负整数',allowBlank:false"
						value="${ftraininfoForm.eligibPopulace}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			满意度*
		</td>
		<td class="content" colspan="3">
			<html:text property="satisfaction" styleId="satisfaction"
						styleClass="text medium"  
						alt="re:/^([1]{1}[0]{1}[0]{1}?)$|^([(0-9)]{1,2}?)$/,re_vt:'请输入小于等于100的正整数',allowBlank:false,maxLength:5"
						value="${ftraininfoForm.satisfaction}" />
		</td>
	</tr>
        <tr>
			<td class="label">
			满意度打分原因
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="satisfactionReason"
					id="satisfactionReason" alt="width:500,allowBlank:true">${ftraininfoForm.satisfactionReason}</textarea>
			
			</td>
				</tr>
         <td colspan="4">
							<div class="ui-widget-header">
								审批信息
							</div>
						</td>
					</tr>

					<tr>
						<td class="label">
							选择审批人*
						</td>
						<td class="content" colspan="3">
							<input type="text" name="textReviewer" id="textReviewer"  value="<eoms:id2nameDB id="${ftraininfoForm.deviceAssessApprove.approveUser}" beanId="tawSystemUserDao" />"
								class="text" readonly="readonly" alt="allowBlank:false" />
							<input type="button" name="approvalButton" id="approvalButton"
								value="请选择审核人" class="btn" alt="allowBlank:false" />
							<input type="hidden" name="approvalUser" id="approvalUser" value="${ftraininfoForm.deviceAssessApprove.approveUser}"/>

							<eoms:xbox id="tree1"
								dataUrl="${app}/xtree.do?method=userFromDept" rootId="1"
								rootText='用户树' valueField="approvalUser" handler="approvalButton"
								textField="textReviewer" checktype="user" single="true"></eoms:xbox>
						</td>
					</tr>


</table>


<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<input type="button" class="btn" value="返回" onclick="javascript :history.back(-1)">
			<c:if test="${not empty ftraininfoForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除?')){
						var url='${app}/partner/deviceAssess/ftraininfos.do?method=remove&id=${ftraininfoForm.id}';
						location.href=url}"	/>
						
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${ftraininfoForm.id}" />
</html:form>
</div>
<div id="history-info" class="tabContent">
		<logic:notEmpty name="dacList" scope="request">
			<display:table name="dacList" cellspacing="0" cellpadding="0"
				class="table dacList" id="dacList" export="false" sort="list"
				partialList="true" size="${size}">

				<display:column headerClass="sortable" title="提交时间">
				${dacList.commitTime}
		       </display:column>
				<display:column headerClass="sortable" title="审批人">
					<eoms:id2nameDB id="${dacList.approveUser}"
						beanId="tawSystemUserDao" />
				</display:column>
				<display:column headerClass="sortable" title="意见">
				${dacList.content}
		       </display:column>
				<display:column headerClass="sortable" title="流转状态">
					<c:if test="${dacList.state=='0'}">
						驳回
					</c:if>
					<c:if test="${dacList.state=='1'}">
						同意
					</c:if>
				</display:column>
				<display:column headerClass="sortable" title="备注">
				${dacList.remark}
		       </display:column>

			</display:table>
		</logic:notEmpty>
		<logic:empty name="dacList" scope="request">
			没有记录!
		</logic:empty>
	</div>
	</div>

<%@ include file="/common/footer_eoms.jsp"%>