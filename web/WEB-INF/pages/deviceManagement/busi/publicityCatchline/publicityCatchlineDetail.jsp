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
<%-----
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
 --%>
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
	
<table id="stylesheet" class="formTable">

<div align="center">媒体宣传详情页面</div>
	<tr>
		<td class="label">项目名称*</td>
		<td >
		<textarea class="textarea max" cols="3"  readonly="true">${publicityCatchlineList.publicityCatchline}</textarea>
			</td>

	</tr>


	
		<tr>
		<td class="label">提交审批人</td>
       <td class="content" colspan="3">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${publicityCatchlineList.approvalUser}"/>
		</td>
	</tr>

	<tr>

		<td class="label">备注</td>
		<td class="content" colspan="3">${publicityCatchlineList.remark}</td>

	</tr>





	</table>
	
	
	 <!-- 查看审批信息 -->
  <div id="history-info" class="tabContent">
    <display:table name="publicityCatchlineListApproval" cellspacing="0" cellpadding="0"  class="table publicityCatchlineListApproval"  
        id="publicityCatchlineListApproval" export="false" sort="list" partialList="true" size="approvalResultSize">

		<display:column headerClass="sortable" title="审批人">
			<eoms:id2nameDB id="${publicityCatchlineListApproval.approvalUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="审批时间">
			${publicityCatchlineListApproval.approvalTime}
        </display:column>
        <display:column headerClass="sortable" title="审批意见">
			${publicityCatchlineListApproval.approvalContent}
        </display:column>
        <display:column headerClass="sortable" title="审批状态">
			${publicityCatchlineType[publicityCatchlineListApproval.approvalType]}
        </display:column>
        		<display:column headerClass="sortable" title="备注">
			${publicityCatchlineListApproval.remark}
        </display:column>
 
		
	
	</display:table>
  </div>
  
  <div id="comments-info" class="tabContent">	 
  </div>


</form>

<%@ include file="/common/footer_eoms.jsp"%>