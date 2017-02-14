<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
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
<form action="${app }/deviceManagement/line/maintainReport.do?method=addMaintainReport" 
	 method="post"
	id="maintainReportForm">	
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
			</td>
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
		</td></tr>
		<tr><td class="label">预计用时</td>
		<td>
			${maintain.expectationTimeConsuming}
		</td>
<td class="label">预计日期</td>
			<td class="content">
				${maintain.expectationDate}
			</td></tr>
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
	<tr></tr>
	<tr></tr>
	<tr></tr>
		<table id="stylesheet" class="formTable">
		<tr>
			<td class="label">实际割接日期*</td>
			<td class="content" colspan="3">
			<input class="text" type="text"
				name="maintainDate" id="maintainDate"
				alt="allowBlank:false,vtext:'请输入预计日期...'"
				onclick="popUpCalendar(this, this,null,null,null,true,-1);"
				readonly="true" />
				</td>
		</tr>
		<tr>
			<td class="label">割接用时*</td>
			<td class="content" colspan="3">
			<input class="text" type="text"
				name="useTime" id="useTime"
				alt="allowBlank:false" />小时	
				</td>
		</tr>
		<tr>
			<td class="label">所用人力*</td>
			<td class="content" colspan="3">
			<input class="text" type="text"
				name="peopleNum" id="peopleNum"
				alt="allowBlank:false" />工日	
				</td>
		</tr>
		
		<tr>
			<td class="label">实际衰耗*</td>
			<td class="content" colspan="3">
			<input class="text" type="text"
				name="factualAttenuation" id="factualAttenuation"
				alt="allowBlank:false" />分贝	
				</td>
		</tr>
		<tr>
			<td class="label">完成填写*</td>
			<td class="content" colspan="3">	
			<input type="radio" name="status"  value="1" checked="checked"/>已经完成
			<input type="radio" name="status" value="0"/>尚未完成
				</td>
		</tr>
		<tr>
			<td class="label">施工备注*</td>
			<td class="content" colspan="3">	
						<textarea class="text max"
				type="text" name="remark" id="remark" alt="allowBlank:true"></textarea>
				</td>
		</tr>
		<tr>
		<input type="hidden" name="maintainNameId" id="maintainNameId" value="${maintain.id }" />
		</tr>
		</table>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="保存" />
	</form>
  <div id="comments-info" class="tabContent">	 
  </div>
</form>
<script type="text/javascript">
		 var v = new eoms.form.Validation({form:'maintainReportForm'});
  v.custom = function(){ 
  	if(confirm('您是否要提交该信息?')){
       return true;
 	}else{
       return false;
     }
   }
	
</script>
<%@ include file="/common/footer_eoms.jsp"%>