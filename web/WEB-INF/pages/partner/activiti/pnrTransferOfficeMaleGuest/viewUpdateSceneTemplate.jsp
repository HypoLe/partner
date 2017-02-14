<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>
<head>
<base target="_self" />
</head>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneCssUtil.jsp"%><!-- 引入场景模板公用css代码  sceneCssUtil.jsp -->
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
</script>

<html:form action="/pnrTransferOfficeMaleGuest.do?method=saveTemplate" styleId="theform" >
<input type="hidden" name="processType" value="${processType}">
<input type="hidden" name="processInstanceId" value="${processInstanceId}">
<input type="hidden" name="linkType" value="${linkType}">
<input type="hidden" name="operateFlag" value="${operateFlag}">

<c:if test="${auditorSceneExistFlag eq 'Y'}">
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneDivUtil.jsp"%><!-- 引入场景模板公用div代码  sceneDivUtil.jsp -->
</c:if>
<c:if test="${auditorSceneExistFlag ne 'Y'}">
	<div>施工队没有场景模板数据！</div>
</c:if>

<!-- buttons -->
	<div class="form-btns" id="btns" style="display:block">
		<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
			提交
		</html:submit>
	</div>
	
	<!-- <div id="tiShiDiv" style="display:none"><font color="red">当前登录人只有查看权限，没有处理权限！</font></div> -->
</html:form>

<script type="text/javascript">
	//页面加载后，控制场景模板可编辑或者不可编辑
	jq(function(){
		var operateFlag = "${operateFlag}";	
		var linkType = "${linkType}";	
		var auditorSceneExistFlag = "${auditorSceneExistFlag}";
		var handleFlag = "${handleFlag}";	
		if(operateFlag != 'yes' || linkType == 'auditReport' || handleFlag == 'Y' || auditorSceneExistFlag !='Y'){ //没有操作权限，或者周期领用列表，或者该工单已经审批或者已经超时不能审批的（二次抽检超过72小时未审批的就不能审批），不可以在列表里修改场景模板
			jq("input").attr('disabled',"true");
			jq("select").attr('disabled',"true");
			jq(".btn").css('display','none');
			jq(".photolayer").css('display','none'); //隐藏删除按钮
			//jq("#tiShiDiv").css('display','block');//显示操作人权限提示
		}
	});
</script>
	
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferOfficeMaleGuest/sceneJsUtil.jsp"%><!-- 引入场景模板公用js代码  sceneJsUtil.jsp -->
<%@ include file="/common/footer_eoms.jsp"%>
