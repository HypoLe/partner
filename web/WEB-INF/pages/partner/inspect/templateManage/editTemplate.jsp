<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

var type = '${type}';
var i=2 ;
var jq=jQuery.noConflict();
jq(function(){
		var v = new eoms.form.Validation({form:'templateForm'});
		if("detail" == type){
			eoms.form.disable("specialty");
			eoms.form.disable("resType");
		}
		jq("#editTemplate").click(function() {
			submit();
		});
		jq("#editInspectTemplate").click(function() {
			jq("#edit").show();
			jq("#info").hide();
			jq("#reset").hide();
			jq("#editTemplate").val("保存");
		});
		jq("#reset").click(function() {
			jq(':input','#templateForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected'); 
		});
			
		if('detail' == type){
			jq("#edit").hide();
		}
		if('new' == type){
			jq("#info").hide();
		}
});

function vaidateForm(){
	if(jq('#templateName').val() == ''||jq('#templateName').val() == null){
		jq('#errorInfo').html("*名称不能为空");
		return false;
	}
	if(jq('#dept').val() == ''||jq('#dept').val() == null){
		jq('#errorInfo').html("*制作单位不能为空");
		return false;
	}
	if(jq('#specialty').val() == ''||jq('#specialty').val() == null){
		jq('#errorInfo').html("*专业不能为空");
		return false;
	}
	return true;
}
function submit(){
		jq('#editTemplate').hide();
		jq('#reset').hide();
		jq('#errorInfo').html("");
		if(!vaidateForm()){
			return false;
		}

		jq("#templateForm").attr("action","inspectTemplateManger.do?method=editTemplate");  
		if(confirm('您是否要提交该信息?')){
	  		Ext.Ajax.request({	
		    form: 'templateForm',
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    	 	jq('#editTemplate').hide();
			    	 if(confirm('数据提交成功')){	
					  	   closewin();
					       return true;
					 	}else{
					       return false;
					     }
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	jq('#editTemplate').show();
		    		jq('#reset').show();
		    	 	Ext.Msg.alert('提示',jsonResult[0].msg); 
      			 }
		    },
	     	failure: function(response) { 
                    Ext.Msg.alert('提示','数据提交失败'); 
            }   
		    
			});	
			
      		 return true;
 		}else{
       		return false;
    	 }
}
function closewin(){ 

		if("detail" == type){
			window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplatesList';
			return;
		}
		if("new" == type){
			window.location='${app}/partner/inspect/inspectTemplateManger.do?method=goToTemplatesList';
			return;
		}
		//opener.location.reload();
		//self.opener=null; 
		//self.close();
		//window.opener.location.reload();
}
	
function clock(){
		if(i==2){
		var myDate = new Date();
			 alert("您于 "+myDate.toLocaleString()+" 提交了该模版");
		}
		i=i-1;
		document.title="本窗口将在"+i+"秒后自动关闭!"; 
		if(i>0)setTimeout("clock();",1000); 
		else closewin();
} 

</script>


<!-- 
<table class="formTable">
	<caption>
		巡检模板主体新增
	</caption>
</table> -->
<div id="edit">
<form method="post" id="templateForm" action="inspectTemplateManger.do?method=editTemplate" name="templateForm" >
	<table class="formTable" id="sheet">
		<!--  <tr>
			<td colspan="4">
				<div class="ui-widget-header">
					巡检模板主体新增
				</div>
			</td>
		</tr>
		-->
		<tr>
			<td class="label">
				模版名称*
			</td>
			<td class="content" colspan="1">
						
				<html:text property="templateName" 
						styleClass="text medium" name="templateName"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${inspectTemplate.templateName }" />		
					
			</td>
		</tr>
	<%----	<tr>
			<td class="label">
				模板制作单位*
			</td>
			
			<td class="content" colspan="1">
			
				<eoms:comboBox name="dept" id="dept" initDicId="1122002" defaultValue="${inspectTemplate.dept }"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />
			</td>
			
			<td class="content">
				<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
					value="<eoms:id2nameDB id="${inspectTemplate.dept}" beanId="partnerDeptDao"/>" 
					alt="allowBlank:false" readonly="readonly"/>
				 <input name="dept" id="dept"  value="${inspectTemplate.dept}" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="公司树"  valueField="dept" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
			</td>
		</tr>--%>
		<tr>	
			<td class="label">
				巡检专业*
			</td>
			<td class="content" colspan="1">
			
			<eoms:comboBox name="specialty" id="specialty" sub="resType" initDicId="11225"  defaultValue="${inspectTemplate.specialty }" alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
				<%---
				<eoms:comboBox defaultValue="${inspectTemplate.specialty }" name="specialty" id="specialty" initDicId="1122003"
					alt="allowBlank:false,vtext:'请选择(单选字典)...'" />--%>
					
					<c:if test="${not empty inspectTemplate.specialty }">
					
						<input name="specialty" type="hidden" value="${inspectTemplate.specialty}">	
					</c:if>
					
			</td>
		</tr>
		<tr>
			<td class="label">
				资源类别*
			</td>
			<td class="content" colspan="1">
			
			<eoms:comboBox name="resType" id="resType"  initDicId="${inspectTemplate.specialty }" defaultValue="${inspectTemplate.resType }" alt="allowBlank:false,vtext:'请选择(单选字典)...'" styleClass="select" />
				<c:if test="${not empty inspectTemplate.specialty }">
					
						<input name="resType" type="hidden" value="${inspectTemplate.resType}">	
					</c:if>	
					
			</td>
		</tr>
		<tr>
			<td class="label">
				模板概述
			</td>
			
			<td >
				<textarea  name="content" id="content" class="textarea max" style="width:98%">${inspectTemplate.content }</textarea>
				
			</td>
			
		</tr>
	</table>
	<input class="content" type="hidden" name="id"
					id="id" value="${inspectTemplate.id }" />
	<br/>
	<input type="submit" value="保存" class="btn" />
	<input type="reset" value="重置" class="btn" />
	<input type="button" class="btn" value="返回" onclick="javascript:history.back();"
	<div id="errorInfo"></div>
</form>
</div>
<div id="info"><table class="formTable">
		<!-- <tr>
			<td colspan="4">
				<div class="ui-widget-header">
					巡检模板信息
				</div>
			</td>
		</tr>
 -->
		<tr>
			<td class="label">
				模版名称*
			</td>
			<td class="content" colspan="3">
				${inspectTemplate.templateName }
			</td>
		</tr>
		<tr>
			<td class="label">
				模板制作单位*
			</td>
			<td class="content" colspan="3">
						<eoms:id2nameDB id="${inspectTemplate.dept}" beanId="tawSystemDeptDao" />
			</td>
		</tr>
		<tr>
			<td class="label">
				巡检专业*
			</td>
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplate.specialty}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				资源类型*
			</td>
			<td class="content" colspan="3">
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectTemplate.resType}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				添加人*
			</td>
			<td class="content" colspan="3">
			<eoms:id2nameDB beanId="tawSystemUserDao"
						id="${inspectTemplate.addUser}" />
			</td>
		</tr>
		<tr>
			<td class="label">
				添加时间*
			</td>
			<td class="content" colspan="3">
			${inspectTemplate.addTime}
			</td>
		</tr>
		<tr>
			<td class="label">
				模板概述
			</td>
			<td class="content" colspan="3">
				<pre>${inspectTemplate.content }</pre>
				<input class="text" type="hidden" name="id" 
							id="id" value="${inspectTemplate.id }" alt="allowBlank:false" />
			</td>
		</tr>
		
		
		<c:if test="${sessionform.userid eq inspectTemplate.addUser }">
		
			<tr><td colspan="4"><input type="button" id="editInspectTemplate" class="btn"  name='submitInput' value="编辑"/><input type="button" class="btn" value="返回" style="margin-left: 20px;" onclick="javascript:history.back();"</td></tr>
		</c:if>
		
	</table></div>
	
	<script>
	</script>
<%@ include file="/common/footer_eoms.jsp"%>