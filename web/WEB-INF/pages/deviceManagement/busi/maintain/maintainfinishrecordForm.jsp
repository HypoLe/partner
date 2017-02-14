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
    		tabs.activate(0);
    		
    	var v = new eoms.form.Validation({form:'maintainApprovalForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){		
       return true;
 	}else{
       return false;
     }
   }
	});

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
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
<table id="stylesheet" class="formTable">
	<tr><td colspan="4"><div class="ui-widget-header" >割接详情页面</div></td></tr>
		<tr>
			<td class="label">割接名称</td>
			<td class="content" colspan="3">
			${maintain.maintainName}
			</td>
		</tr>
		<tr>
			<td class="label">光缆线段*</td>
			<td>
			${maintain.fiberSection}
			<!-- <eoms:id2nameDB beanId="tawSystemAreaDao" id="${publicizeArticle.publicizeArticlePlace}"/>
			 --></td>
			<td class="label">割接地点*</td>
			<td class="content">
			<eoms:id2nameDB beanId="tawSystemAreaDao" id="${maintain.maintainPlace}"/>
			</td>
		</tr>
		<tr><td class="label">影响系统</td>
		<td class="content">${maintain.influenceSystem}</td>
		<td class="label">原有衰耗*</td>
			<td class="content">${maintain.attenuation}</td></tr>
		<tr>
			<td class="label">预计用时*</td>
			<td class="content">${maintain.expectationTimeConsuming}</td>
		</tr>
		<tr>
			</td>
			<td class="label">预计日期</td>
			<td class="content" colspan="3">${maintain.expectationDate}</td>
		</tr>
		<tr>
			<td class="label">提交审核人</td>
			<td class="content" colspan="1">${maintain.approvalUser}</td>
		</tr>
		<tr>
		<td class="label">割接原因*</td>
			<td class="content"  colspan=3">
			${maintain.maintainCause}
			</td>
				</tr>
		<tr>
			<td class="label">申请备注</td>
			<td class="content" colspan="3">
			${maintain.remark}
		</tr>
		<caption>
	</caption>	
		</tr>	
<td colspan="4"><div class="ui-widget-header" >上报信息</div></td>
<tr><td class="label">实际割接日期</td><td class="content">${maintainReport[0].maintainDate }</td><td class="label">实际割接用时</td><td class="content">${maintainReport[0].useTime}</td></tr>
<tr><td class="label">实际割接所用人力</td><td class="content">${maintainReport[0].maintainDate }</td><td class="label">实际衰耗</td><td class="content">${maintainReport[0].factualAttenuation }</td></tr>
<tr><td class="label">完成情况</td><td class="content">${finished[maintainReport[0].status] }</td></tr>
<tr><td class="label">施工备注</td><td class="content" colspan="3">${maintainReport[0].remark }</td></tr>
<tr><td colspan="4"><div class="ui-widget-header" >验收信息</div></td></tr>
<tr><td class="label">验收结果*</td>
	<td class="content">${checkresult[maintainFinishsearchResult[0].finishResult] }</td>
	<td class="label">开始验收时间*</td>
	<td class="content">${maintainFinishsearchResult[0].startTime }</td>
</tr>
<tr><td class="label">结束验收时间*</td>
	<td class="content">${maintainFinishsearchResult[0].endTime }</td>
</tr>
<tr><td class="label">验收备注*
	</td><td colspan="4">${maintainFinishsearchResult[0].finishRemark }</td>
</tr>
<tr><td class="label">备注</td>
	<td colspan="4">${maintainFinishsearchResult[0].remark1 }
				</td>
             </tr>
<form  action="maintainfinish.do?method=addMaintainFinishRecord" method="post" id="maintainFinishForm" name="maintainFinishForm">
<input type="hidden" value="${maintain.id }" name="maintainid" id="maintainid"/>
<table id="sheet" class="formTable">
<tr>
      <td class="label">
					归档情况*
				</td>
				<td colspan="3">
				<input type="radio" name="isRecord" id="isRecord" value="1"/>完成归档
				<input type="radio" name="isRecord" id="isRecord" value="0"/>尚未完成归档
				</td>
			</tr>
       <tr>
				<td class="label">
					资料修改
				</td>
				<td colspan="3">
				<input type="radio" name="isModify" id="isModify" value="1"/>需要修改
				<input type="radio" name="isModify" id="isModify" value="0"/>不需要修改
				</td>
             </tr>
	<input type="hidden" name="maintainNameId" value="${maintain.id}"  id="maintainNameId"/>
	<input type="hidden" name="maintainPlace" value="${maintain.maintainPlace}"  id="maintainPlace"/>
	<input type="hidden" name="maintainReportId" value="${maintainReport[0].id}"  id="maintainReportId"/>
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
</div>
  <div id="comments-info" class="tabContent">	 
  </div>
</form>
<%@ include file="/common/footer_eoms.jsp"%>