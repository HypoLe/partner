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
            v = new eoms.form.Validation({form:'projectLineForm'});
            v.custom = function(){ 
            	return true;
            };
            
          //  v1 = new eoms.form.Validation({form:'importForm'});
            
			//$('startTime').value = new Date().format('Y-m-d'); 
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


  

</script>
 
<content tag="heading">
<c:out value="线路修缮申请" />
</content>  <br/><br/>
	




<br/>
	
	
	
	
	
	
	
	     
<form action="lineProjectAction.do?method=apply" method="post" id="projectLineForm" name="projectLineForm" >
	<table id="projectLineTable" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >线路修缮申请信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工程名称* 
			</td>
			<td class="content" colspan="3">
				<input class="text" type="text" name="projectName"
					id="topic" alt="allowBlank:false" />
			</td>
			
		</tr>
		<tr>
			<td class="label">
			 工程性质* 
			</td>
			<td class="content">
				<input class="text" type="text" name="projectType"
					id="topic" alt="allowBlank:false" />
			</td>
			<td class="label">
				工程地点*
			</td>
			<td class="content">
				<input class="text" type="text" name="projectLocation"
					id="topic" alt="allowBlank:false" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				网络性质*
			</td>
			<td class="content">
			  <eoms:comboBox name="networkType"
					id="networkType" initDicId="1010104" alt="allowBlank:false" styleClass="select" />
			</td> 
			<td class="label">
				影响系统*
			</td>
			<td class="content">
			  <input class="text" type="text" name="effect"
					id="topic" alt="allowBlank:false" />
			</td> 
		</tr>
		<tr>
			<td class="label">
				迁改长度*
			</td>
			<td class="content">
			  <input class="text" type="text" name="length1"
					id="length1" alt="allowBlank:false,vtype:'number'" />米
			</td> 
			<td class="label">
				计划工期*
			</td>
			<td class="content">
			  <input class="text" type="text" name="lastTime"
					id="lastTime" alt="allowBlank:false, vtype:'number'" />天
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				开始时间*
			</td>
			<td colspan="3" class="content">
			 <input class="text" value=""
					onclick="popUpCalendar(this, this, 'yyyy-mm-dd', -1, -1, false, -1 )"
					type="text" name="startTime" id="startTime"
					readonly="readonly"
					alt="vtext:'不能为空',allowBlank:false" />
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				申请附件*
			</td>
			<td colspan="3" class="content">
				<eoms:attachment scope="request" idField="attachment" name="attachment" 
					appCode="faultSheet" alt="allowBlank:true" />
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				审核人*
			</td>
			<td colspan="3" class="content">
			<input type="text" name="textReviewer" id="textReviewer" class="text"/>
			<input type="button" name="userButton" id="userButton" value="请选择审核人" class="btn" alt="allowBlank:false"/>
	  		<input type="hidden" name="allower" id="allower"/>
	  		
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
   					 rootId="-1" 
    				 rootText='用户树' 
    				 valueField="allower" handler="userButton"
    				 textField="textReviewer"
   					 checktype="user" single="true"></eoms:xbox>
			</td> 
		</tr>
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="申请" ></html:submit>	
		
	 
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>

</form>



<%@ include file="/common/footer_eoms.jsp"%>