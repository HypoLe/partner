<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js">
</script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js">
</script>
<script type="text/javascript">
var myjs = jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'mechaicalArmManagementForm'});
            v.custom = function(){ 
            	return true;
            };
            })
myjs(function(){
  myjs('input#contactNumber').bind('change',preExp);
  });
 function preExp(event){
 
 var telephone=new RegExp("^(0|[1-9][0-9]*)$");
	 var phone=/^((\d{2,})-)(\d{7,})(-(\d{3,}))?$/;
	 
var contactNumber=myjs("input#contactNumber").val();
var aa=myjs('#fuck');
 aa.removeClass('ui-state-error');
 if(contactNumber.match(telephone)||contactNumber.match(phone)){       
          aa.addClass('ui-state-highlight ui-corner-all');         
           aa.children("div").html("输入正确");
      	   aa.children("span").attr("class","ui-icon ui-icon-info");
      }else{       
           aa.addClass('ui-state-error ui-corner-all');
           aa.children("div").html("输入不合法,请输入正确的固话或者移动电话");
           aa.children("span").attr("class","ui-icon ui-icon-alert");
           
      }

}

</script>
<div align="center">大型机械手管理添加页面</div><br>
<form action="mechanicalArmManagement.do?method=add"
	id="mechaicalArmManagementForm" method="post"
	name="mechaicalArmManagementForm">
	<fieldset>

		<table id="sheet" class="formTable">

			<tr>
			<td class="label">
					项目名称*
				</td>

				<td class="content">
					<input class="text" type="text" name="projectName" id="projectName"
						alt="allowBlank:false" />
				</td>
				<td class="label">
					审核人*
				</td>
				<td class="content">
				<input type="text"  class="text"  name="approvingPerson2" id="approvingPerson2"  
					value="" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="approvingPerson" id="approvingPerson"  value=""/>
				<eoms:xbox id="approvingPerson" dataUrl="${app}/xtree.do?method=userFromDept"  
				rootId="2" rootText="用户树"  valueField="approvingPerson" handler="approvingPerson2" 
				textField="approvingPerson2" checktype="user" single="true" />
				
				</td>
			</tr>
			<tr>
				<td class="label">
					大型施工机械名称*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="constructionMachineryName"
						id="constructionMachineryName" alt="allowBlank:false" />
				</td>

				<td class="label">
					大型施工机械施工地点*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="mechanicalArm_workyard"
						id="mechanicalArm_workyard" alt="allowBlank:false" />
				</td>
			</tr>


			<tr>
				<td class="label">
					大型施工机械手姓名*
					<br>
				</td>
				<td class="content">
					<input class="text" type="text" name="mechanicalArmName"
						id="mechanicalArmName" alt="allowBlank:false" />
				</td>
				<td class="label">
					联系电话*
				</td>
				<td class="content">
					<input class="text" type="text" name="contactNumber"
						id="contactNumber" alt="vtype:'number'" />
					<div id="fuck" class="ui-state-highlight ui-corner-all"
						style="width: 150px">
						<span class="ui-icon ui-icon-circle-triangle-e"
							style="float: left; margin-right: .6em;"></span>
						<div>
							支持固话,移动电话
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td class="label">
					走访情况（每日/每周）*
					<br>
				</td>


				<td class="content" colspan="3">
					<textarea class="textarea max" name="visitSituation"
						id="visitSituation" alt="allowBlank:false"></textarea>
				</td>
			</tr>
			<tr>
				<td class="label">
					备注
				</td>
				<td colspan="3">
					<textarea class="textarea max" name="remarks" id="remarks"></textarea>
					<br>
				</td>
			</tr>
		</table>
	</fieldset>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="提交审核"></html:submit>
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>
<%@ include file="/common/footer_eoms.jsp"%>