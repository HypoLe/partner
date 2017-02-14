<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var myjs=jQuery.noConflict();
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}
	myjs(function(){
	 v = new eoms.form.Validation({form:'taskOrderForm'});
})

</script>
	
	 
	<form action="${app }/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanMainList" method="post" id="taskOrderForm">
		<table id="sheet" class="listTable">
			<caption>
				<font color="red">默认查询上个月的计划，如需抽检其它月份的请修改时间再查询！</font>
			</caption>
			<tr>		
				<td class="label">计划名称</td><input type="hidden" name="ischeck" value="ischeck" />
				<td><input class="text" type="text" name="planNameStringLike"/></td>
				<td class="label">巡检专业</td>
				<td>
					<eoms:comboBox name="specialtyStringEqual" id="specialty" styleClass="select"  initDicId="11225" />
				</td>
			</tr>
			<tr>		
				<td class="label">年&nbsp<font color='red'>*</font></td>
				<td class="content">
					<eoms:dict key="dict-partner-inspect" selectId="yearStringEqual" dictId="yearflag" 
						beanId="selectXML" cls="select" alt="allowBlank:false" defaultId="${year}" />
				</td>
				<td class="label">月&nbsp<font color='red'>*</font></td>
				<td class="content">
					<eoms:dict key="dict-partner-inspect" selectId="monthStringEqual" dictId="monthflag" 
						beanId="selectXML" cls="select" alt="allowBlank:false" defaultId="${month}" />
				</td>
			</tr>
			<tr>
				<td class="label">
					代维公司
				</td>
				<td>
					<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
						value=""  readonly="readonly"/>
					<input name="partnerDeptIdStringEqual" id="partnerDeptId"  value="" type="hidden" />
					<eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
				</td>
				<td class="label"></td>
				<td class="content"></td>
			</tr>
			<tr>
				<td colspan="4">
					<div align="left">
						<input type="submit" class="btn" value="查询" />
						&nbsp;&nbsp;&nbsp;
					</div>
				</td>
			</tr>
	</table>
	</form>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>

<br>
<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectSpotcheckAction.do?method=findInspectPlanMainList" 
		partialList="true" size="${size}">
		<display:column property="planName" title="巡检计划名称" />
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="代维公司" >
			<eoms:id2nameDB id="${list.partnerDeptId}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column title="巡检日期" >
			<c:if test="${!empty list.year}" var="result">
				${list.year}年${list.month}月
			</c:if>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${list.id }"
				href="${app }/partner/inspect/inspectPlan.do?method=getInspectPlanMainDetail&id=${list.id}"
				target="blank" shape="rect"> <img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="巡检资源" media="html">
			<a href="${app }/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanResSpotcheckList&planId=${list.id}"> 
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>




<%@ include file="/common/footer_eoms.jsp"%>
