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
           
            var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
            
       		new xbox({
				btnId:'areatree',
				treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
				treeRootId:'10',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
				showChkFldId:'advertisementCity',saveChkFldId:'advertisementArea',returnJSON:false
				});
            
         
});
function deleteInfo(id) {
			if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/checkSegment/checkSegmentAction.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/checkSegmentAction.do?method=list'/>'});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}

function deleteSome() {
			var cardNumberList = document.getElementsByName("cardNumber");
				var ids = "";
				for (i = 0 ; i < cardNumberList.length; i ++) {
				if (cardNumberList[i].checked) {
					var myTempStr=cardNumberList[i].value;
					ids+=myTempStr.toString()+";"
					}
				};
			if (ids == "") {
				alert("请至少选择一项");
				return false;
			} 
			if(confirm("确定要删除吗？")){
				
				Ext.Ajax.request({
					url:"${app}/checkSegment/checkSegmentAction.do",
					params:{method:"deleteSome",ids:ids},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {location.href='<html:rewrite page='/checkSegmentAction.do?method=list'/>'});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}


function jumpToAdd(){
	location.href='<html:rewrite page='/checkSegmentAction.do?method=goToAdd'/>';
}
</script>
<script type="text/javascript">
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
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">	
	<form action="advertisementPlanAction.do?method=listReview" method="post" id="checkSegmentFindForm" name="checkSegmentFindForm">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label" >所属地市</td>
					<td colspan="3" class="content">
						<input class="text" type="text" name="advertisementCity"
						id="advertisementCity" readonly="true" value=""
						alt="allowBlank:false" /> 
						<input type="button" name="areatree" id="areatree" value="选择所属地市" class="btn" /> 
						<input type="hidden" name="advertisementArea" id="advertisementArea" />
					</td>
					
				</tr>
			
			
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
<!-- Information hints area end-->
<logic:present name="advertisementPlanList" scope="request">
	<display:table name="advertisementPlanList" cellspacing="0" cellpadding="0"
		id="advertisementPlanList" pagesize="${pagesize}"
		class="table advertisementPlanList" export="true"
		requestURI="advertisementPlanAction.do" sort="list"
		partialList="true" size="${size}">
		<display:column sortable="true" headerClass="sortable" title="所属地市">
			
			<eoms:id2nameDB id="${advertisementPlanList.city}" beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="普通标石数量">
			${advertisementPlanList.generalStone}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="检测标石数量">
			${advertisementPlanList.detectStone}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="计划增补宣传牌数量">
			${advertisementPlanList.advertisement}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="提交人">
			<eoms:id2nameDB id="${advertisementPlanList.createUserId}" beanId="tawSystemUserDao" />			
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="提交时间">
			${advertisementPlanList.createTime}
		</display:column>
		
		
		
		<display:column sortable="false" headerClass="sortable" title="审核"
			paramProperty="id" url="/advertisementPlan/advertisementPlanAction.do?method=goToReview"
			paramId="id" media="html">
			<img src="${app }/images/icons/edit.gif">
		</display:column>
	<%-- 	<display:column sortable="false" headerClass="sortable" title="详情"
			paramProperty="id" url="/advertisementPlan/advertisementPlanAction.do?method=goToReview1"
			paramId="id" media="html">
			<img src="${app }/images/icons/table.gif">
		</display:column>--%>
		<display:column sortable="false" headerClass="sortable" title="详情"
			paramProperty="id" url="/advertisementPlan/advertisementPlanAction.do?method=goToDetail"
			paramId="id" media="html">
			<img src="${app }/images/icons/table.gif">
		</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	

	<br/>
		
	<!--  <input type="button" onclick="jumpToAdd()" value="添加" class="btn"/>-->
	<!--  <input type="button" onclick="deleteSome()" value="删除" class="btn"/>-->






<%@ include file="/common/footer_eoms.jsp"%>
