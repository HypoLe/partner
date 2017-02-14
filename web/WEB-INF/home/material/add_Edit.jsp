<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
<!--
	Ext.onReady(function() {
	    v = new eoms.form.Validation({form:'form1'});
	});
	
	jQuery.noConflict(); 
	jQuery(function($){ 
		if($("#id").val()!=""){
			$("#form1").attr("action","materiaLib.do?method=edit");
			$("#add_save").hide();
			$("#edit_save").show();
		}
		$("#form1").submit(function(){
				$("#scopeNames").val($("#fieldset_org_names").html())
		})	
		
		var fileTdHtml = $("#fileTdHtml").html();
		
		$("#reset").click(function(){
			$("#fileTdHtml").html(fileTdHtml)
			$("#fieldset_org_names").html(null);
			$("#scopeIds,scopeNames").val(null);
		})
		
		
	}); 
//-->
</script>
<br/><br/>
<form action="materiaLib.do?method=save" method="post" id="form1" >
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 主题<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					<input type='text' id='subject' name="subject"   value="${requestScope.materialLib.subject }"  maxlength="80"
						  	   style="width: 80%" alt="allowBlank:false,vtext:'主题不能为空！'"  />
				</td>
			</tr>
		 <tr>
			<td class="label"> 
				概述<font color="red" >*</font>
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="outline"id="outline"   onkeyup="this.value = this.value.slice(0, 230)"
							alt="allowBlank:false,vtext:'概述不能为空！'" >${requestScope.materialLib.outline }</textarea>
			</td>
		</tr>
			  <tr>
			 <td class="label" >
				 发布范围<font color="red" >*</font>
				</td>
				<td class="content"  colspan="3">
					<fieldset id="fieldset_org" >
						<legend>发布到下列组织机构 </legend> 
						<input class="btn" type="button" value="请选择组织"   id="orgSelect">
						<eoms:xbox id="user" dataUrl="${app}/xtree.do?method=dept" rootId=""
								rootText='组织机构' valueField="scopeIds" handler="orgSelect" textField="fieldset_org_names" 
								checktype='dept' single="false"></eoms:xbox>
						<div id="fieldset_org_names"  class="viewer-list">${requestScope.materialLib.scopeNames }</div>		
					</fieldset>
						<input type="hidden" id="scopeIds"  name="scopeIds"    
									value="${requestScope.materialLib.scopeIds }" alt="allowBlank:false,vtext:'发布范围不能为空！'" >
						<input type="hidden" id="scopeNames"  name="scopeNames" value="${requestScope.materialLib.scopeNames }">					
				</td>
			</tr>  
			<tr>
				<td class="label" >
				 关键词<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
			  	   <input type='text' id='keyWord' name="keyWord"   value="${requestScope.materialLib.keyWord }"  maxlength="80"
			  	   style="width: 80%" alt="allowBlank:false,vtext:'关键词不能为空！'"  />
				</td>
			</tr>
			<tr>
				<td class="label" >
				 巡检专业<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
				<eoms:comboBox name="specialty" 
					id="specialty" sub="testChildren" defaultValue="${requestScope.materialLib.specialty }"
					initDicId="11225" alt="allowBlank:false"  />
				</td>
			</tr>
			<tr>
				<td class="label" >
				 附件
				</td>
				<td class="content" colspan="3"  id="fileTdHtml"  >
					<eoms:download ids="${requestScope.materialLib.file }"></eoms:download>
					<eoms:attachment scope="request" idField="file" appCode="baseinfo"   />
				</td>
			</tr>
		</table>
		<br/>
		<input type='hidden' id="id" name="id"  value="${requestScope.materialLib.id }"/>
		<input id="add_save" type="submit" class="btn" value="保存" />
		<input id="edit_save" type="submit" class="btn" value="保存"  style="display: none"/>
		<input id="reset" type="reset" class="btn" value="重置" />
</form>

<%@ include file="/common/footer_eoms.jsp"%>