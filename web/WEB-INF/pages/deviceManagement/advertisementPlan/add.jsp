<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'advertisementPlanForm'});
            v.custom = function(){ 
            	return true;
            };
            var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
            
       		new xbox({
				btnId:'areatree',
				treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
				treeRootId:'10',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
				showChkFldId:'advertisementCity',saveChkFldId:'advertisementArea',returnJSON:false
				});
            
         
});
   
function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}

function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}

function save2Draft() {
	var reviewer = document.getElementById("reviewer");
	var advertisementArea = document.getElementById("advertisementArea").value;
	//var generalStone = document.getElementById("generalStone").value;
	//var detectStone = document.getElementById("detectStone").value;
	//var city = document.getElementById("city").value;
	//console.dir(reviewer);
	alert(advertisementArea);
	//location.href='<html:rewrite page='/advertisementPlanAction.do?method=save'/>'
}

  
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/taskOrder/taskOrderAction.do?method=importRecord",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
		},
		failure : function(form, action) {
			alert(action.result.infor);
		}
    });
	
}
</script>
<div align="center">宣传牌增补计划信息</div>
<br/>
<form action="advertisementPlanAction.do?method=sumbit" method="post" id="advertisementPlanForm" name="advertisementPlanForm" >
	<table id="checkSegmentTable" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >宣传牌增补计划信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 地市名称* 
			</td>
			<td class="content" >
				<input class="text" type="text" name="advertisementCity"
				id="advertisementCity" readonly="true" value=""
				alt="allowBlank:false" /> 
				<input type="button" name="areatree" id="areatree" value="选择所属地市" class="btn" /> 
				<input type="hidden" name="advertisementArea" id="advertisementArea" />
		</td>		
			
			<td class="label">
			计划增加宣传牌数量* 
			</td>
			<td class="content">
				<input class="text" type="text" name="advertisement"
					id="topic" alt="allowBlank:false, vtype:'number'" />套
			</td>
			
		</tr>
		<tr>
			<td class="label">
			计划增补普通标石* 
			</td>
			<td class="content">
				<input class="text" type="text" name="generalStone"
					id="generalStone" alt="allowBlank:false,vtype:'number'" />块
			</td>
			<td class="label">
			计划增补检测标示*
			</td>
			<td class="content">
				<input class="text" type="text" name="detectStone"
					id="detectStone" alt="allowBlank:false,vtype:'number'" />块
			</td>
		</tr>
		
		
		
		
		
		<tr>
			<td class="label">
				审核人*
			</td>
			<td colspan="3" class="content">
			<input type="text" name="textReviewer" id="textReviewer" class="text"/>
			<input type="button" name="userButton" id="userButton" value="请选择审核人" class="btn" alt="allowBlank:false"/>
	  		<input type="hidden" name="reviewer" id="reviewer"/>
	  		
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
				备注信息
			</td>
			<td colspan="3" class="content">
				<textarea class="textarea max" name="remarks"
					id="remarks1" alt="allowBlank:true"></textarea>
			</td> 
		</tr>
		
		
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="提交审核" ></html:submit>	
	<!-- 
	<html:button styleClass="btn" property="method.save"
			styleId="method.save" value="保存草稿" onclick="save2Draft()"></html:button> 
	 -->	
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>

</form>



<%@ include file="/common/footer_eoms.jsp"%>