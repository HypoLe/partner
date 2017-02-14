<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>

	<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/scripts/jquery/plugin/jquery-ztree-3.1/css/demo.css">
	<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/scripts/jquery/plugin/jquery-ztree-3.1/css/zTreeStyle/zTreeStyle.css">
  	<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/plugin/jquery-ztree-3.1/js/jquery.ztree.core-3.1.min.js"></script>
  	<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/plugin/jquery-ztree-3.1/js/jquery.ztree.excheck-3.1.min.js"></script>
  	<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/plugin/jquery-ztree-3.1/js/jquery.ztree.exedit-3.1.min.js"></script>
	<SCRIPT type="text/javascript">
		var myjs=jQuery.noConflict();
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "all"
			},
			view: {
				dblClickExpand: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onCheck: onCheck
			}
		};

		var zNodes =${approveObjectJson};

		function beforeClick(treeId, treeNode) {
			var zTree = myjs.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
		
		function onCheck(e, treeId, treeNode) {
			var zTree = myjs.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getCheckedNodes(true),
			v = "";
			var id='';
			var type='';
			for (var i=0, l=nodes.length; i<l; i++) {
				v += nodes[i].name + ",";
				id += nodes[i].id + ",";
				type += nodes[i].type + ",";
			}
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			if (id.length > 0 ) id =id.substring(0, id.length-1);
			if (type.length > 0 ) type =type.substring(0, type.length-1);
			var cityObj = myjs("#citySel");
			cityObj.attr("value", v);
			var approveObject = myjs("#approveObject");
			approveObject.attr("value", id);
			var approveObjectType = myjs("#approveObjectType");
			approveObjectType.val(type);
		}

		function showMenu() {
			var cityObj = myjs("#citySel");
			var cityOffset = myjs("#citySel").offset();
			myjs("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

			myjs("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			myjs("#menuContent").fadeOut("fast");
			myjs("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || myjs(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		myjs(document).ready(function(){
			 v = new eoms.form.Validation({form:'taskOrderForm'});
			    v.custom = function(){ 
			    	return true;
			    };
			    var id='${id}';
			    if(id){
			    	myjs("#specialty").attr("disabled","disabled");
			    }
		
			myjs.fn.zTree.init(myjs("#treeDemo"), setting, zNodes);
		});
		
		function commitApprove(){
			var approveObject = myjs("#approveObject").val();
			var approveObjectType = myjs("#approveObjectType").val();
			if(approveObject == ''){
				alert('请选择审批对象');
				return;
			}
		 	Ext.Ajax.request({
				method:"post",
				url: "${app}/partner/inspect/inspectPlan.do?method=commitInspectPlanToApprover",
				params:{
					planId: '${planId}',
					approveObject: approveObject,
					approveObjectType: approveObjectType,
					isChangePlan: '${isChangePlan}',
					planChgId: '${planChgId}'
				},
				success: function(response, options){
					if(response.responseText=='false'){
						window.returnValue = false;
						if('${isChangePlan}'==''){
							alert('未关联资源的巡检计划不能提交审批');
						}else{
							alert('未变更资源的巡检计划不能提交审批');
						}
					}else{
						window.returnValue = true;
					}
					window.close();
				}
			 });
		 }
	</SCRIPT>


<form action="" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
		<input type="hidden" name="planId" value="${planId}">
		<input type="hidden" name="approveObject" id="approveObject" value="">
		<input type="hidden" name="approveObjectType" id="approveObjectType" value="">
		<tr>
			<td class="content">
			<div class="content_wrap">
					<div class="zTreeDemoBackground left">
						<ul class="list">
							<li class="title">审批对象:
							<input id="citySel" type="text" class="text" readonly value=""  style="width:200px;"
							onclick="showMenu();" />
							</li>
							
						</ul>
					</div>
				</div>
				<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
					<ul id="treeDemo" class="ztree" style="margin-top:0; width:280px; height: 200px;"></ul>
				</div>
			</td>
		</tr>
		
</table>
<input type="button" class="btn" value="提交审批" onclick="commitApprove()"/>


</form>

<%@ include file="/common/footer_eoms.jsp"%>