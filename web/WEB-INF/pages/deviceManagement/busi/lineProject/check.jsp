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
            v = new eoms.form.Validation({form:'projectLineForm'});
            v.custom = function(){ 
            	return true;
            };
            
          //  v1 = new eoms.form.Validation({form:'importForm'});
            jq("#allowResult").bind("change",chageType);
          	
          	
			
			
});
			
			
			
function chageType(e) {
	var value = jq("#allowResult").val();
	switch(value) {
		case "0":
			jq("#projectLineTable").find("tr.checkInfo").each(function(e) {
          		jq(this).show();
          		jq(this).find(":input").each(function(e) {
          			jq(this).attr("disabled",false);
          		});
          	});
			break;
		case "1": 
			jq("#projectLineTable").find("tr.checkInfo").each(function(e) {
          		jq(this).hide();
          		jq(this).find(":input").each(function(e) {
          			jq(this).attr("disabled",true);
          		});
          	});
			break;
	}
}


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
<c:out value="线路修缮审定" />
</content>  <br/><br/>
	




<br/>
	
	
	
	
	
	
	
	     
<form action="lineProjectAction.do?method=check" method="post" id="projectLineForm" name="projectLineForm" >
	
	<input name="id" type="hidden" value="${projectBaseInfo.id}"/>
	<table id="projectLineTable" class="formTable">
		
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工程名称
			</td>
			<td class="content" colspan="3">
				${projectBaseInfo.projectName}
			</td>
			
		</tr>
		<tr>
			<td class="label">
			 工程性质
			</td>
			<td class="content">
				${projectBaseInfo.projectType}
			</td>
			<td class="label">
				工程地点
			</td>
			<td class="content">
				${projectBaseInfo.projectLocation}
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				网络性质
			</td>
			<td class="content">
			 <eoms:id2nameDB id="${projectBaseInfo.networkType}" beanId="IItawSystemDictTypeDao" />
			</td> 
			<td class="label">
				影响系统
			</td>
			<td class="content">
			  ${projectBaseInfo.effect}
			</td> 
		</tr>
		<tr>
			<td class="label">
				迁改长度
			</td>
			<td class="content">
			  ${projectBaseInfo.projectLength}米
			</td> 
			<td class="label">
				计划工期
			</td>
			<td class="content">
			  ${projectBaseInfo.lastTime}天
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				申请附件
			</td>
			<td colspan="3" class="content">
				<eoms:download ids="${projectBaseInfo.attachment}"></eoms:download>
			</td> 
		</tr>
		<tr>
			<td colspan="4"><div class="ui-widget-header" >审定信息</div></td>
		</tr>
		<tr>
			<td class="label">
				审定结果
			</td>
			<td colspan="3" class="content">
				<select name="allowResult" id="allowResult">
					<option value="0" selected>审定通过</option>
					<option value="1">审定不通过</option>
				</select>
			</td> 
		</tr>
		<tr>
			<td class="label">
				审定意见
			</td>
			<td colspan="3" class="content">
				<textarea class="textarea max" name="allowRemarks"
					id="allowRemarks" alt="allowBlank:true"></textarea>
			</td> 
		</tr>
		<tr class="checkInfo">
			<td colspan="4"><div class="ui-widget-header" >勘查信息</div></td>
		</tr>
		<tr class="checkInfo">
			<td class="label">
				勘查负责人*
			</td>
			<td class="content">
			<input type="text" name="checker" id="checker" class="text" alt="allowBlank:false"/>
			<input type="button" name="userButton" id="userButton" value="请选择勘查负责人" class="btn" alt="allowBlank:false"/>
	  		<input type="hidden" name="checkerId" id="checkerId"/>
	  		
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
   					 rootId="-1" 
    				 rootText='用户树' 
    				 valueField="checkerId" handler="userButton"
    				 textField="checker"
   					 checktype="user" single="true"></eoms:xbox>
			</td> 
			<td class="label">
				工程委托人*
			</td>
			<td class="content">
			<input type="text" name="consignee" id="consignee" class="text" alt="allowBlank:false"/>
			<input type="button" name="userButton2" id="userButton2" value="请选择工程委托人人" class="btn" alt="allowBlank:false"/>
	  		<input type="hidden" name="consigneeId" id="consigneeId"/>
	  		
				<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=userFromDept" 
   					 rootId="-1" 
    				 rootText='用户树' 
    				 valueField="consigneeId" handler="userButton2"
    				 textField="consignee"
   					 checktype="user" single="true"></eoms:xbox>
			</td> 
		</tr>
		<tr class="checkInfo">
			<td class="label">
				勘查时间*
			</td>
			<td class="content">
				<input class="text" value=""
					onclick="popUpCalendar(this, this, 'yyyy-mm-dd', -1, -1, false, -1 )"
					type="text" name="checkDate" id="checkDate"
					readonly="readonly"
					alt="vtext:'勘查时间不能为空',allowBlank:false" />
			</td> 
			<td class="label">
				工程预算*
			</td>
			<td colspan="3" class="content">
				<input class="text" type="text" name="projectPay"
					id="projectPay" alt="allowBlank:false" />万元
			</td> 
		</tr>
		<tr class="checkInfo">
			<td class="label">
				勘查备注
			</td>
			<td colspan="3" class="content">
				<textarea class="textarea max" name="checkRemarks"
					id="checkRemarks"></textarea>
			</td> 
		</tr>
		<tr class="checkInfo">
			<td class="label">
				勘查附件
			</td>
			<td colspan="3" class="content">
				<eoms:attachment scope="request" idField="checkattachment" name="checkattachment" 
					appCode="faultSheet" alt="allowBlank:true" />
			</td> 
		</tr>
</table>



		
		
	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="审定" ></html:submit>	
		
	 
	

</form>



<%@ include file="/common/footer_eoms.jsp"%>