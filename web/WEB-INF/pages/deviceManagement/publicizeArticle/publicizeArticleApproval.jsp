<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var myjs=jQuery.noConflict();
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
    		tabs.activate(0);
    		
    	//var v = new eoms.form.Validation({form:'publicizeArticleApprovalForm'});
 // v.custom = function(){ 
  //	if(confirm('您是否要提交该信息?')){		
   //    return true;
 	//}else{
   //    return false;
   //  }
   //}
	});

function returnBack(){
		window.history.back();
	}

	function subMit(){
		var approvalType=document.getElementById("approvalType").value
			var approvalContent=document.getElementById("approvalContent").value
		if(approvalType == '3' && approvalContent == null && approvalContent== ''){
			alert("请填写驳回意见");
			return false;
		}

	form[0].submit();

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
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
	
<table id="stylesheet" class="formTable">

	<content tag="heading">
	<c:out value="墙体广告详情页面" />
	</content><br/><br/>

		<tr>
			<td class="label">项目名称*</td>
			<td class="content" colspan="3">
			${publicizeArticle.projectName}
			</td>

		</tr>


		<tr>
			<td class="label">宣传单/宣传品发放地点*</td>
			<td><eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticle.publicizeArticlePlace}"/>
			</td>
			<td class="label">计划发放宣传单数量（份）*</td>
			<td class="content">${publicizeArticle.publicizeArticlePlanAmount}</td>

		</tr>
		<tr><td class="label">实际发放宣传单(份)</td>
		<td class="content">${publicizeArticle.publicizeArticleActualAmount}</td>
		<td class="label">完成时间*</td>
			<td class="content">${publicizeArticle.finishTime}</td></tr>
		<tr>
			
			<td class="label">提交审核人*</td>
			<td class="content">${publicizeArticle.approvalUser}</td>
		</tr>
		<tr>

			</td>
			<td class="label">未完成原因</td>
			<td class="content" colspan="3">${publicizeArticle.incompleteCause}</td>


		</tr>

		<tr>

			<td class="label">备注</td>
			<td class="content" colspan="3">${publicizeArticle.remark}</td>

		</tr>
		
		<caption>
		<div class="header left">请填写审批意见 </div>
	</caption>	
			
		</tr>		
		
<form  action="publicizeArticleApproval.do?method=approval" method="post" id="publicizeArticleApprovalForm" name="publicizeArticleApprovalForm">
<table id="sheet" class="formTable">

<tr>
	<td class="label">
					审批结果*
	</td>
		<td class="content">
				<select id="approvalType" name="approvalType">
						<option value='2' selected="selected">
							审批通过
						</option>
						<option value='3'>
							驳回
						</option>
					</select>
				</td>
			</tr>

<tr>
      <td class="label">
					审批意见*
				</td>
				<td colspan="3">
					<textarea id="approvalContent" class="textarea max" name="approvalContent"
						alt="width:500,allowBlank:false"></textarea>
				</td>
			</tr>
       <tr>
				<td class="label">
					备注
				</td>
				<td colspan="3">
					<textarea id="remark" class="textarea max" name="remark"
						alt="width:500,allowBlank:true"></textarea>
				</td>
             </tr>


	<input type="hidden" name="id" value="${publicizeArticle.id}" />
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



	</table>
	
	
	 <!-- 查看合同版本信息 -->
  <div id="history-info" class="tabContent">
    <display:table name="publicizeArticleApprovalList" cellspacing="0" cellpadding="0"  class="table advertisementListApproval"  
        id="publicizeArticleApprovalList" export="false" sort="list" partialList="true" size="approvalResultSize">

		<display:column headerClass="sortable" title="审批人">
			<eoms:id2nameDB id="${publicizeArticleApprovalList.approvalUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column headerClass="sortable" title="审批时间">
			${publicizeArticleApprovalList.approvalTime}
        </display:column>
        <display:column headerClass="sortable" title="审批意见">
			${publicizeArticleApprovalList.approvalContent}
        </display:column>
        <display:column headerClass="sortable" title="审批状态">
			${publicizeArticleType[publicizeArticleApprovalList.approvalType]}
        </display:column>
        		<display:column headerClass="sortable" title="备注">
			${publicizeArticleApprovalList.remark}
        </display:column>
 
	</display:table>
  </div>
  
  <div id="comments-info" class="tabContent">	 
  </div>


</form>

<%@ include file="/common/footer_eoms.jsp"%>