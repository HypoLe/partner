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
       	tabs.addTab('approve-info', '审批历史');
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
	</content>
		<tr>
			<td class="label">巡检计划名称</td>
			<td class="content">
			${planMain.planName}
			</td>
			<td class="label">巡检专业</td>
			<td><eoms:id2nameDB id="${planMain.specialty}" beanId="ItawSystemDictTypeDao" /></td>
		</tr>

		<tr>
			<td class="label">代维公司</td>
			<td><eoms:id2nameDB id="${planMain.partnerDeptId }" beanId="partnerDeptDao"/></td>
			<td class="label">关联资源数</td>
			<td class="content">${planMain.resNum }</td>

		</tr>
		<tr>
			<td class="label">巡检日期</td>
			<td class="content">
			<c:if test="${!empty planMain.year}" var="result">
				${planMain.year}年${planMain.month}月
			</c:if>
			</td>
			<td class="label">制定人</td>
			<td class="content"><eoms:id2nameDB id="${planMain.creator }" beanId="tawSystemUserDao" /></td>
		</tr>
		<tr>
			<td class="label">制定日期</td>
			<td class="content">
				<bean:write name="planMain" property="createTime" scope="request" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="label">描述</td>
			<td class="content" colspan="3">${planMain.content}</td>
		</tr>
		
		<caption>
		</caption>	
		</tr>		
	</table>
</div>


  <div id="approve-info" class="tabContent">	 
  	   <logic:present name="approveList" scope="request">
		<display:table name="approveList" cellspacing="0" cellpadding="0"
			id="approveList" class="table approveList" export="false" sort="list">
			<display:column title="审批人">
				<eoms:id2nameDB id="${approveList.approver}" beanId="tawSystemUserDao" />
			</display:column>
			<display:column title="审批操作">
				${approveStatusMap[approveList.approveStatus]}
			</display:column>
			<display:column property="approveIdea" title="审批意见" />
			<display:column title="审批时间" >
				<bean:write name="approveList" property="approveTime" format="yyyy-MM-dd HH:mm:ss"/>
			</display:column>
			<display:column title="计划类型">
				${planTypeMap[approveList.planType]}
			</display:column>
		</display:table>
	</logic:present>
  </div>
</div>
</form>

<%@ include file="/common/footer_eoms.jsp"%>