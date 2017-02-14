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
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
        	tabs.addTab('check-info', '验收信息 ');
    		tabs.activate(0);
	});
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
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
	
<table id="stylesheet" class="formTable">

	<content tag="heading">
	<c:out value="墙体广告详情页面" />
	</content><br/><br/>
		<tr>
			<td class="label">割接名称*</td>
			<td class="content" colspan="3">
			${maintain.maintainName}
			</td>
		</tr>
		<tr>
			<td class="label">光缆线段*</td>
			
			<td>
			${maintain.fiberSection }
			<!--<eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticle.publicizeArticlePlace}"/>
			--></td>
			<td class="label">割接地点*</td>
			<td class="content">
			<eoms:id2nameDB beanId="tawSystemAreaDao" id="${maintain.maintainPlace}"/>
			</td>
		</tr>
		<tr><td class="label">影响系统*</td>
		<td>
			${maintain.influenceSystem}
		</td>
<td class="label">原有衰耗</td>
			<td class="content">
				${maintain.attenuation}
		<!--<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${publicizeArticle.publicizeArticleType}"/>
			--></td></tr>
		<tr><td class="label">预计用时</td>
		<td>
			${maintain.expectationTimeConsuming}
		</td>
<td class="label">预计日期</td>
			<td class="content">
				${maintain.expectationDate}
		<!--<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${publicizeArticle.publicizeArticleType}"/>
			--></td></tr>
		<tr>
			<td class="label">提交审核人*</td>
			<td class="content">${maintain.approvalUser}</td>
			<td class="label">割接原因*</td>
			<td class="content">${maintain.maintainCause}</td>
		</tr>
		<tr><td class="label">提交时间*</td><td class="content" colspan="3">${maintain.creatTime}</td></tr>
		<tr>
			<td class="label">备注</td>
			<td class="content" colspan="3">${maintain.remark}</td>
		</tr>
	</table>
	
	
	 <!-- 查看合同版本信息 -->
  <div id="history-info" class="tabContent">
    <display:table name="maintainApprovalApprovalList" cellspacing="0" cellpadding="0"  class="table advertisementListApproval"  
        id="maintainApprovalApprovalList" export="false" sort="list" partialList="true" size="approvalResultSize">
		<display:column headerClass="sortable" title="审批人">
			${maintainApprovalApprovalList.approvalUser}
		</display:column>
		<display:column headerClass="sortable" title="审批时间">
			${maintainApprovalApprovalList.approvalTime}
        </display:column>
        <display:column headerClass="sortable" title="审批意见">
			${maintainApprovalApprovalList.approvalContent}
        </display:column>
        <display:column headerClass="sortable" title="审批状态">
			${maintainApprovalType[maintainApprovalApprovalList.approvalType]}
        </display:column>
        		<display:column headerClass="sortable" title="备注">
			${maintainApprovalApprovalList.remark}
        </display:column>
	
	</display:table>
  </div>
  
  <div id="check-info" class="tabContent">	 
   <display:table name="maintainFinishList" cellspacing="0" cellpadding="0"  class="table advertisementListApproval"  
        id="maintainFinishList" export="false" sort="list" partialList="true" size="approvalResultSize">
		<display:column headerClass="sortable" title="验收人">
			${maintainFinishList.addMan}
		</display:column>
		<display:column headerClass="sortable" title="验收结果">
			<c:if test="${maintainFinishList.finishResult == 1}">
			通过
			</c:if>
			<c:if test="${maintainFinishList.finishResult != 1}">
			未通过
			</c:if>
        </display:column>
        <display:column headerClass="sortable" title="备注">
			${maintainFinishList.finishRemark}
        </display:column>
	</display:table>
  </div>

</form>

<%@ include file="/common/footer_eoms.jsp"%>