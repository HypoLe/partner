<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	Ext.onReady(function(){
		//Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
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
<!-- --
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
  -->
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
<table id="stylesheet" class="formTable">

	<content tag="heading">
	<c:out value="媒体宣传详情页面" />
	</content><br/><br/>
	<tr>
		<td class="label">项目名称</td>
		<td class="content" colspan="3">
			${mediaPublicityList.mediaName}</td>
	</tr>
	<tr>
		<td class="label">媒体宣传形式</td>
				<td class="content" colspan="3">
			${mediaPublicityList.mediaStyle}</td>
	</tr>
	<tr>
		<td class="label">媒体宣传内容</td>
		<td class="content" colspan="3">
		${mediaPublicityList.mediaContent}
		</td>
	</tr>
	<tr>
			<td class="label">宣传效果评估</td>
		<td class="content" colspan="3">${mediaPublicityList.mediaAssess}</td>
			</tr>
		<tr>
		<td class="label">媒体宣传时间</td>
		<td class="content">${mediaPublicityList.mediaTime}</td>
		</td>
		<td class="label">提交审批人</td>
       <td class="content">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${mediaPublicityList.approvalUser}"/>
		</td>
	</tr>
	<tr>
		<td class="label">备注</td>
		<td class="content" colspan="3">${mediaPublicityList.remark}</td>
	</tr>
	</table>
	 <!-- 查看审批信息 -->
  <div id="history-info" class="tabContent">
    <display:table name="mediaPublicityListApproval" cellspacing="0" cellpadding="0"  class="table mediaPublicityListApproval"  
        id="mediaPublicityListApproval" export="false" sort="list" partialList="true" size="approvalResultSize">
		<display:column headerClass="sortable" title="审批人">
			<eoms:id2nameDB id="${mediaPublicityListApproval.approvalUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="审批时间">
			${mediaPublicityListApproval.approvalTime}
        </display:column>
        <display:column headerClass="sortable" title="审批意见">
			${mediaPublicityListApproval.approvalContent}
        </display:column>
        <display:column headerClass="sortable" title="审批状态">
			${mediaPublicityType[mediaPublicityListApproval.approvalType]}
        </display:column>
        		<display:column headerClass="sortable" title="备注">
			${mediaPublicityListApproval.remark}
        </display:column>
	</display:table>
  </div>
  <div id="comments-info" class="tabContent">	 
  </div>


</form>

<%@ include file="/common/footer_eoms.jsp"%>