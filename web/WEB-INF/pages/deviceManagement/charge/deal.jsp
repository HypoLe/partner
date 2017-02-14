<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	});
	
 function feeAppliSubmit(){
			if(confirm("确定提交申请？")){
			document.getElementById("feeApplicationStatus").value='2'
			document.getElementById("feeApplicationMainForm").submit();
			}else{
			return  false;
			}
}
 function feeAppliFile(){
			if(confirm("确定归档？")){
			document.getElementById("feeApplicationStatus").value='5'
			document.getElementById("feeApplicationMainForm").submit();
			}else{
			return  false;
			}
}
function returnBack(){
		window.history.back();
	}
</script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
	
	
		<table id="sheet" class="formTable">
	    
		
		<tr>
		<caption>
		<div class="header center">线路代维费用申请信息</div>
	</caption>	
			
		</tr>
		
	
		
		<tr>
			<td class="label">
			 费用申请人 
			</td>
			<td class="content">
			${feeApplicationMain.feeApplicationUser}				
			</td>
			<td class="label">
				申请人联系电话
			</td>
			<td class="content">
				${feeApplicationMain.feeApplicationCall}
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 申请人部门 
			</td>
			<td class="content">
					${feeApplicationMain.feeApplicationDept}
			</td>
			<td class="label">
			 所属地市 
			</td>
			<td class="content">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${feeApplicationMain.feeApplicationCity}"/>
			</td>
		</tr>
		
		
		<tr>
		
			<td class="label">
				费用申请日期
			</td>
			<td class="content">
				${feeApplicationMain.feeApplicationGreatTime}
			</td>
			
				<td class="label">
			 费用类型 
			</td>
			<td class="content">
				${feeApplicationMain.feeApplicationType}
			</td>
		</tr>
		
		<tr>
		
			<td class="label">
				费用金额
			</td>
			<td class="content">
				${feeApplicationMain.feeApplicationMoney}
			</td>
			
			<td class="label">
				附件
				<br>
			</td>
			<br>
			<td colspan="3">
				<c:if test="${empty feeApplicationMain.feeApplicationAccessory}">
					没有附件!
				</c:if>
				<c:if test="${not empty feeApplicationMain.feeApplicationAccessory}">
					<eoms:download ids="${feeApplicationMain.feeApplicationAccessory}"></eoms:download><br/>
				</c:if>
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
			 费用描述
			</td>
			<td colspan="3">
			${feeApplicationMain.feeApplicationDiscribe} 
			</td>
		</tr>
		 <tr>
			<td class="label">
				备注
			</td>
			<td colspan="3">
				${feeApplicationMain.feeApplicationRemark}
			</td>
		
		
			
		</tr>			

		</table>

<c:if test="${!empty feeApplicationLinkList}">
	

	<display:table name="feeApplicationLinkList" cellspacing="0" cellpadding="0"
		id="feeApplicationLinkList" 
		class="table feeApplicationMainList" sort="list"
		 >
		
	
		
		
		<display:column headerClass="sortable" title="操作人">
			${feeApplicationLinkList.operateUser}
		</display:column>
		<display:column  headerClass="sortable" title="时间">
			${feeApplicationLinkList.operateTime}
		</display:column>
		<display:column  headerClass="sortable" title="部门">
			${feeApplicationLinkList.operateDept}
		</display:column>
		<display:column  headerClass="sortable" title="结果">
		
			<c:if test="${1==(feeApplicationLinkList.operateResult)}" >审批通过</c:if>	
			<c:if test="${-1==(feeApplicationLinkList.operateResult)}" >审批不通过</c:if>
			
		</display:column>
		<display:column  headerClass="sortable" title="意见">
			${feeApplicationLinkList.operateOpinion}
		</display:column>
		
		<display:column  headerClass="sortable" title="附件">
		<c:if test="${empty feeApplicationLinkList.operateAccessory}">
					没有附件!
				</c:if>
				<c:if test="${not empty feeApplicationLinkList.operateAccessory}">
					<eoms:download ids="${feeApplicationLinkList.operateAccessory}"></eoms:download>
				</c:if>
		</display:column>
	
	</display:table>

	

</c:if>		




	

		<tr>
		<caption>
		<div class="header left">请填写审批意见 </div>
	</caption>	
			
		</tr>		
		
<form  action="charge.do?method=deal" method="post" id="feeApplicationLinkForm" name="feeApplicationLinkForm">
<table id="sheet" class="formTable">

<tr>
	<td class="label">
					审批结果*
	</td>
		<td class="content">
				<select id="operateResult" name="operateResult"	>
						<option value='1' selected="selected">
							审批通过
						</option>
						<option value='-1'>
							审批不通过
						</option>
					</select>
				</td>
			</tr>

<tr>
      <td class="label">
					审批意见*
				</td>
				<td colspan="3">
					<textarea id="operateOpinion" class="textarea max" name="operateOpinion"
						alt="width:500,allowBlank:false"></textarea>
				</td>
			</tr>
       <tr>
				<td class="label">
					备注
				</td>
				<td colspan="3">
					<textarea id="operateRemark" class="textarea max" name="operateRemark"
						alt="width:500,allowBlank:false"></textarea>
				</td>
             </tr>

       <tr>
			<td class="label">
				附件
				<br>
			</td>
			<br>
			<td colspan="3">
				<eoms:attachment scope="request" idField="operateAccessory"
					name="operateAccessory" appCode="charge" alt="allowBlank:true" />
			</td>
		</tr>

  	
	<input type="hidden" name="id" value="${feeApplicationMain.id}" />
	<input type="hidden" name="feeApplicationStatus" value="${feeApplicationMain.feeApplicationStatus}" />
	<input type="hidden"  name="Type" value="2" />
	</table>
	
	<fieldset>
			<legend>
				操作
			</legend>
  <html:submit styleClass="btn"  property="method.save"  
	        styleId="method.save" value="确定"  ></html:submit>	 		
  <input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>
	</fieldset>	
</form>
			
			

 
<%@ include file="/common/footer_eoms.jsp"%>