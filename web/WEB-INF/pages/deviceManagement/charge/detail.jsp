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
	
 function openSearch(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "查看处理详情";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭处理详情";
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
			<td class="content">
			${feeApplicationMain.feeApplicationDiscribe} 
			</td>
			</td>
			<td class="label">
				备注
			</td>
			<td class="content">
				${feeApplicationMain.feeApplicationRemark}
			</td>
		</tr>
		 <tr>
			
		</tr>			
		</table>
	
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">处理详情</span>
</div>	
		
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">	
				
		
		<logic:present name="feeApplicationLinkList" scope="request">
	<display:table name="feeApplicationLinkList" cellspacing="0" cellpadding="0"
		id="feeApplicationLinkList" 
		class="table feeApplicationMainList" export="true" sort="list"
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
</logic:present>
	
</div>		

<form action="charge.do?method=file" method="post">
	
	<input type="hidden"  name="Type" value="1" />
	<input type="hidden" name="id" value="${feeApplicationMain.id}" />
	
<c:if test="${4==(feeApplicationMain.feeApplicationStatus)}" var="return">

<html:submit styleClass="btn"  property="method.save" 
	        styleId="method.save" value="归档"  ></html:submit>

		</c:if>			
			
	
</form>		
		
		
		


