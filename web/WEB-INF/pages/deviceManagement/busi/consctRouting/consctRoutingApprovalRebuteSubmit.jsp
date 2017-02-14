<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var myjs=jQuery.noConflict();
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
			var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
    		tabs.activate(0);
    		
    	var v = new eoms.form.Validation({form:'consctRoutingForm'});
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

<div id="loadIndicator" class="loading-indicator">
	加载页面完毕...
</div>

<div id="info-page">

		<%--
	private String id; // 主键
	private String creatUser; //填报人
	private String creatDept; // 所属部门
	private String creatTime; // 填报时间
	private String status;//状态
	private String approvalUser; // 提交审核人
	private String projectName; // 项目名称
	private String repeaterSection;//施工路由中继段名称
	private String repeaterSectionMileage;//施工路由中继段里程（公里）
	private String location;//施工位置
	private String riskLevel;//施工路由中继段风险级别
	private String maintainLevel;//维护级别
	private String disseminationMeasures;//宣传措施
	private String careMeasures;//看护措施
	private String result;//效果（是否发生障碍）
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除
	private String deletedTime;//删除标记：0表示未删除，1表示逻辑删除
	private String remark;//备注 
--%>
	<!-- 查看内容信息 -->
	<div id="content-info" class="tabContent">
	<form action="consctRouting.do?method=rebuteSubmit" method="post" id="consctRoutingForm" name="consctRoutingForm" >
		<input type="hidden" name="id" value="${consctRouting.id }">
		<input type="hidden" name="creatUser" value="${consctRouting.creatUser }">
		<input type="hidden" name="creatDept" value="${consctRouting.creatDept }">
		<input type="hidden" name="areaId" value="${consctRouting.areaId }">
		<input type="hidden" name="creatTime" value="${consctRouting.creatTime }">
		<input type="hidden" name="status" value="${consctRouting.status }">
		<input type="hidden" name="deleted" value="${consctRouting.deleted }">
		<input type="hidden" name="deletedTime" value="${consctRouting.deletedTime }">
		<table id="sheet" class="formTable">
		<tr>
			<td colspan="4"><div class="ui-widget-header" >审核人</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 审核人* 
			</td>
			<td class="content" colspan="3">
					<input type="text" name="textReviewer" id="textReviewer" class="text" value="<eoms:id2nameDB id="${consctRouting.approvalUser}" beanId="tawSystemUserDao" />"/>
					<input type="button" name="userButton" id="userButton" value="请选择审核人" class="btn" alt="allowBlank:false"/>
			  		<input type="hidden" name="approvalUser" id="reviewer" value="${consctRouting.approvalUser}"/>
	  		
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
   					 rootId="-1" 
    				 rootText='用户树' 
    				 valueField="reviewer" handler="userButton"
    				 textField="textReviewer"
   					 checktype="user" single="true"></eoms:xbox>
			</td>
		</tr>
		
		<tr>
			<td colspan="4"><div class="ui-widget-header" >施工信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 项目名称* 
			</td>
			<td class="content">
				<input class="text" type="text" name="projectName" value="${consctRouting.projectName }"
					id="projectName" alt="allowBlank:false" />
			</td>
			<td class="label">
			施工路由中继段名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="repeaterSection" value="${consctRouting.repeaterSection }"
					id="repeaterSection" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 施工路由中继段里程（公里）* 
			</td>
			<td class="content">
				<input class="text" type="text" name="repeaterSectionMileage" id="repeaterSectionMileage" value="${consctRouting.repeaterSectionMileage }"
					alt="allowBlank:false" />
			</td>
			<td class="label">
			 施工位置* 
			</td>
			<td class="content">
				<input class="text" type="text" name="location" id="location" value="${consctRouting.location }"
					alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			施工路由中继段风险级别*
			</td>
			<td class="content" colspan="3">
				<eoms:comboBox name="riskLevel" defaultValue="${consctRouting.riskLevel }"
						id="riskLevel" initDicId="120010101" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td colspan="4"><div class="ui-widget-header" >其它</div></td>
		</tr>
		
		<tr>
			<td class="label">
			维护级别*
			</td>
			<td class="content">
				<eoms:comboBox name="maintainLevel" defaultValue="${consctRouting.maintainLevel }"
						id="maintainLevel" initDicId="120010102" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
			宣传措施*
			</td>
			<td class="content" >
				<input class="text" type="text" name="disseminationMeasures" value="${consctRouting.disseminationMeasures }"
					id="disseminationMeasures" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 看护措施* 
			</td>
			<td class="content" >
				<input class="text" type="text" name="careMeasures" value="${consctRouting.careMeasures }"
					id="careMeasures" alt="allowBlank:false" />
			</td>
			<td class="label">
			效果（是否发生障碍）* 
			</td>
			<td class="content">
				<eoms:comboBox name="result"
						id="result" 
						initDicId="10301"
						 alt="allowBlank:false" 
						 styleClass="select" 
						 defaultValue="${consctRouting.result }"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">
			备注 
			</td>
			<td class="content" colspan="3">
				<textarea  class="textarea max" name="remark" id="remark" alt="allowBlank:true" > ${consctRouting.remark }</textarea>
			</td>
		</tr>
		
		</table>
				<input type="submit"  class="btn"  value="确认提交审核" />
		</form>
	</div>
	
	<!-- 查看审批信息 -->
	<div id="history-info" class="tabContent">
		<display:table name="consctRoutingApprovalList" cellspacing="0"
			cellpadding="0" class="table consctRoutingApprovalList"
			id="consctRoutingApprovalList" export="false" sort="list"
			partialList="true" size="resultSize">

			<display:column headerClass="sortable" title="操作人">
				<eoms:id2nameDB id="${consctRoutingApprovalList.actionSendUser}"
					beanId="tawSystemUserDao" />
			</display:column>
			<display:column headerClass="sortable" title="待处理人">
				<eoms:id2nameDB id="${consctRoutingApprovalList.actionReceiveUser}"
					beanId="tawSystemUserDao" />
			</display:column>

			<display:column headerClass="sortable" title="操作时间">
			${consctRoutingApprovalList.actionHappenTime}
	       </display:column>
				<display:column headerClass="sortable" title="意见">
			${consctRoutingApprovalList.actionIdea}
	       </display:column>
				<display:column headerClass="sortable" title="流转状态">
			${consctRoutingApprovalList.actionStepExplain}
	       </display:column>
				<display:column headerClass="sortable" title="备注">
			${consctRoutingApprovalList.remark}
	       </display:column>

		</display:table>
	</div>

	<div id="comments-info" class="tabContent">
	</div>

<%@ include file="/common/footer_eoms.jsp"%>