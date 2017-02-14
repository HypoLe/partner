<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

var jq=jQuery.noConflict();
Ext.onReady(function(){
        v = new eoms.form.Validation({form:'consctRoutingForm'});
        jq("#repeaterSectionMileage").bind("change",function(e) {
	       		var reg = "^(\\d{1,}|\\d{1,}.\\d{1,2})$";
	        	var file = jq("#repeaterSectionMileage").val();
	        	var right = file.match(reg);
	        	if(right == null) {
	        		alert("请输入正确的数据,支持精确小数点后两位！");
	        		jq("#repeaterSectionMileage").val("");
	        		return false;
	        	} else {
        			e.target.value = Number(e.target.value).toFixed(2);
	        		return true;
	        	}
        });
});

function formSubmit(type) {
	Ext.get("consctRoutingForm").mask('正在提交，请稍候...');
	if(!v.check()) {
		Ext.get(this.form).unmask();
		return;
	}
	
	var form = jq("#consctRoutingForm");
	if(type == 'saveDraft') {
		form.attr("action","consctRouting.do?method=saveDraft");
		form.submit();
	}
	if(type == 'submitApproval') {
		form.attr("action","consctRouting.do?method=submitApproval");
		form.submit();
	}
}

</script>
*号为必填内容</br>
 		<div class="header center">护线宣传管理-施工路由风险评估添加页面</div>
<br/>
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
<form action="consctRouting.do?method=add" method="post" id="consctRoutingForm" name="consctRoutingForm" >
	<table id="sheet" class="formTable">
		<%--
		<tr>
			<td colspan="4"><div class="ui-widget-header" >审核人</div></td>
		</tr>---%>
		<tr>
			<td class="label">
			 项目名称* 
			</td>
			<td class="content">
				<input class="text" type="text" name="projectName"
					id="projectName" alt="allowBlank:false" />
			</td>
			<td class="label">
			施工路由中继段名称*
			</td>
			<td class="content">
				<input class="text" type="text" name="repeaterSection"
					id="repeaterSection" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 施工路由中继段里程（公里）* 
			</td>
			<td class="content">
				<input class="text" type="text" name="repeaterSectionMileage" id="repeaterSectionMileage"
					alt="allowBlank:false" />
			</td>
			<td class="label">
			 施工位置* 
			</td>
			<td class="content">
				<input class="text" type="text" name="location" id="location"
					alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
			维护级别*
			</td>
			<td class="content">
					<eoms:comboBox name="maintainLevel"
						id="maintainLevel" initDicId="120010102" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
			宣传措施*
			</td>
			<td class="content" >
				<input class="text" type="text" name="disseminationMeasures"
					id="disseminationMeasures" alt="allowBlank:false" />
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label">
			施工路由中继段风险级别*
			</td>
			<td class="content" >
				<eoms:comboBox name="riskLevel"
						id="riskLevel" initDicId="120010101" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
			 看护措施* 
			</td>
			<td class="content" >
				<input class="text" type="text" name="careMeasures"
					id="careMeasures" alt="allowBlank:false" />
			</td>
		</tr>
		<%--
		<tr>
			<td colspan="4"><div class="ui-widget-header" >其它</div></td>
		</tr>--%>
		
		
		
		<tr>
			
			<td class="label">
			效果(是否发生障碍)* 
			</td>
			<td class="content">
				<eoms:comboBox name="result"
						id="result" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
			 审核人* 
			</td>
			<td class="content">
					<input type="text" name="textReviewer" id="textReviewer" class="text" readonly="readonly" alt="allowBlank:false"/>
					<input type="button" name="userButton" id="userButton" value="请选择审核人" class="btn" alt="allowBlank:false"/>
			  		<input type="hidden" name="approvalUser" id="reviewer"/>
	  		
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
   					 rootId="2" 
    				 rootText='用户树' 
    				 valueField="reviewer" handler="userButton"
    				 textField="textReviewer"
   					 checktype="user" single="true"></eoms:xbox>
			</td>
		</tr>
		<tr>
			<td class="label">
			备注
			</td>
			<td class="content" colspan="3">
				<textarea  class="textarea max" name="remark" id="remark" alt="allowBlank:true" ></textarea>
			</td>
		</tr>
		
		</table>
		
		<br/>

		<!-- <input type="button" onclick="formSubmit('saveDraft')" class="btn"  value="保存草稿" /> -->
		<input type="button" onclick="formSubmit('submitApproval')" class="btn"  value="提交审核" />
</form>



<%@ include file="/common/footer_eoms.jsp"%>