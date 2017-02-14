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
    		
    	var v = new eoms.form.Validation({form:'consctRoutingApprovalForm'});
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
	加载详细信息页面完毕...
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
		<table id="stylesheet" class="formTable">
			<content tag="heading">
			<c:out value="护线宣传管理-施工路由风险评估审核" />
			</content>
			<br />
			<br />
			<tr>
				<td colspan="4">
					<div class="ui-widget-header">
						审核人
					</div>
				</td>
			</tr>

			<tr>
				<td class="label">
					审核人*
				</td>
				<td class="content" colspan="3">
					${consctRouting.approvalUser}
				</td>
			</tr>

			<tr>
				<td colspan="4">
					<div class="ui-widget-header">
						施工信息
					</div>
				</td>
			</tr>

			<tr>
				<td class="label">
					项目名称*
				</td>
				<td class="content">
					${consctRouting.projectName}
				</td>
				<td class="label">
					施工路由中继段名称*
				</td>
				<td class="content">
					${consctRouting.repeaterSection}
				</td>
			</tr>

			<tr>
				<td class="label">
					施工路由中继段里程（公里）*
				</td>
				<td class="content">
					${consctRouting.repeaterSectionMileage}
				</td>
				<td class="label">
					施工位置*
				</td>
				<td class="content">
					${consctRouting.location}
				</td>
			</tr>

			<tr>
				<td class="label">
					施工路由中继段风险级别*
				</td>
				<td class="content" colspan="3">
					${consctRouting.riskLevel}
				</td>
			</tr>

			<tr>
				<td colspan="4">
					<div class="ui-widget-header">
						其它
					</div>
				</td>
			</tr>

			<tr>
				<td class="label">
					维护级别*
				</td>
				<td class="content">
					${consctRouting.maintainLevel}
				</td>
				<td class="label">
					宣传措施*
				</td>
				<td class="content">
					${consctRouting.disseminationMeasures}
				</td>
			</tr>

			<tr>
				<td class="label">
					看护措施*
				</td>
				<td class="content">
					${consctRouting.careMeasures}
				</td>
				<td class="label">
					效果（是否发生障碍）*
				</td>
				<td class="content">
					<eoms:id2nameDB id="${consctRouting.result}" beanId="IItawSystemDictTypeDao" />
				</td>
			</tr>

			<tr>
				<td class="label">
					备注
				</td>
				<td class="content" colspan="3">
					${consctRouting.remark}
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<caption>
						<div class="header left">
							请填写审批意见
						</div>
					</caption>

	</table>
					<form action="consctRouting.do?method=approval"
						method="post" id="consctRoutingApprovalForm"
						name="consctRoutingApprovalForm">
						<input type="hidden" name="id" value="${consctRouting.id}" />
						<table id="sheet" class="formTable">
							<tr>
								<td class="label">
									审核结果*
								</td>
								<td class="content">
									<select id="currentStatus" name="currentStatus">
										<option value='2' selected="selected">
											审核通过
										</option>
										<option value='3'>
											驳回
										</option>
									</select>
								</td>
							</tr>

							<tr>
								<td class="label">
									审核意见*
								</td>
								<td colspan="3">
									<textarea id="actionIdea" class="textarea max"
										name="actionIdea" alt="width:500,allowBlank:false"></textarea>
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
						</table>

						<fieldset>
							<legend>
								操作
							</legend>
							<html:submit styleClass="btn" property="method.save"
								styleId="method.save" value="确定"></html:submit>
							<input type="button" style="margin-right: 5px"
								onclick="returnBack();" value="返回" class="btn" />
							<br />
							<br />
						</fieldset>
		</table>
					</form>

				</td>
			</tr>
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