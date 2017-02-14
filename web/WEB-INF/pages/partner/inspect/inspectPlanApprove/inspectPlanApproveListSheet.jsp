<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
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

	var planChg = '${param.planChg}';	
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
			var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '计划新增待审批');
        	tabs.addTab('approve-info', '计划变更待审批');
	    	if(planChg=='true'){ 
	       		tabs.activate(1);
	       	}else{
	       		tabs.activate(0);
	       	}
	});

</script>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
<div id="info-page">
  <!-- 查看内容信息 -->
  <div id="content-info" class="tabContent">
	<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
</hr>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 
	<form action="inspectPlanChange.do?method=findInspectPlanApproveListSheet" method="post">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">计划名称</td>
				<td><input class="text" type="text" name="planName"/></td>
				<td class="label">巡检专业</td>
				<td>
					<eoms:comboBox name="specialty" id="specialty" 
					initDicId="11225" alt="allowBlank:false"/>
				</td>
			</tr>
			<tr>		
				<td class="label">
					代维公司
				</td>
				<td>
					<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
						value="" alt="allowBlank:false" readonly="readonly"/>
					<input name="partnerDeptId" id="partnerDeptId"  value="" type="hidden" />
					<eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
				</td>
				<td class="label"></td>
				<td></td>
			</tr>
	</table>
	
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>

<!-- Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectPlanChange.do?method=findInspectPlanApproveListSheet" 
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
		<display:column title="审批状态">
			${approveStatusMap[list.approveStatus]}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="详情"
			media="html">
			<a id="${list.id }"
				href="${app }/partner/inspect/inspectPlan.do?method=getInspectPlanMainDetail&id=${list.id}"
				target="blank" shape="rect"> <img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="审批"
			media="html">
			<a href="${app }/partner/inspect/inspectPlan.do?method=toApproveInspectPlan&id=${list.id}"> 
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
		
	</display:table>
</logic:present>
	</br>
	</div>
</div>
  </div>
</div>

<div id="approve-info" class="tabContent">	 
<logic:present name="chglist" scope="request">
	<display:table name="chglist" cellspacing="0" cellpadding="0"
		id="chglist" class="table chglist" export="false" 
		pagesize="${pagesize}" requestURI="inspectPlanChange.do?method=findInspectPlanApproveListSheet&planChg=true" 
		partialList="true" size="${chgsize}">
		<display:column property="changeTitle" title="变更标题" />
		<display:column property="changeOption" title="变更说明" />
		<display:column title="变更申请日期" >
			<bean:write name="chglist" property="changeTime" format="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column title="变更申请人" >
			<eoms:id2nameDB id="${chglist.creator}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="审批状态">
			${approveStatusMap[chglist.state]}
		</display:column>
		<display:column title="变更详情">
			<a target="blank" shape="rect" href="${app }/partner/inspect/inspectPlanChange.do?method=findInspectPlanResChangeApproveList&planId=${chglist.planId }&id=${chglist.id }"> 
				<img src="${app }/images/icons/table.gif"><a>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="审批" media="html">
			<a href="${app }/partner/inspect/inspectPlanChange.do?method=toApproveInspectPlanChange&planChgId=${chglist.id}&planId=${chglist.planId}"> 
				<img src="${app }/images/icons/edit.gif"><a>
		</display:column>
	</display:table>
</logic:present>
	</br>
</div>
  	
  </div>
  </div>
<%@ include file="/common/footer_eoms.jsp"%>