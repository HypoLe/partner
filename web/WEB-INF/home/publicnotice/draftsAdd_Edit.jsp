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
		function isNum(s)
		{
		var pattern = /^d+(.d+)?$/;
		if(pattern.test(s))
		{
		   return true;
		}
		return false;
		}
	jQuery.noConflict(); 
	jQuery(function($){ 
		var auditHtml = $("#auditHtml").html();
		//重置
		$("#reset").click(function(){
			$("#publishArea").val("");
			$("#publishArea").change();
		})
		//发布范围 下拉默认
		var areaIds = '${areaIds}'
		if(areaIds!=null&&areaIds!=""){
			$("#fieldset_default").show();
			if(!isNaN(areaIds))
				$("#publishArea").val("组织机构");
			else
				$("#publishArea").val("人员");
		}
		//是否 审批 下拉默认
		var isAudit = '${requestScope.publishInfo.isAudit}'
			if(isAudit!=null&&isAudit!=""){
			$("#isAudit").val(isAudit);
			if($("#isAudit").val()=="0"){
				$("#taskOwnerNameId").attr("alt","");
				$("#audit").hide();
				$("#directlyPublish").show();
			}
			
			}		
		//发布范围 下拉框
		$("#publishArea").change(function(){
			$("#fieldset_default").hide();
			$("#publishAreaIdValues,#publishAreaNameValues").val(null);
			$("#fieldset_org_names,#fieldset_pre_names").html(null);
			if($(this).val()=="组织机构"){
				$("#fieldset_org").show();
				$("#fieldset_per").hide();
			}
			else if($(this).val()=="人员"){
				$("#fieldset_org").hide();
				$("#fieldset_per").show();
			}else{
				$("#fieldset_org").hide();
				$("#fieldset_per").hide();
			}
		});
	
		$("#isAudit").change(function(){
			if($(this).val()=="0"){
				$("#auditHtml").html(null);
				$("#audit").hide();
				$("#directlyPublish").show();
			}
			else{
				$("#auditHtml").html(auditHtml);
				$("#audit").show();
				$("#directlyPublish").hide();
			}
		});
	//表单提交 	
	       //保存草稿
		$("#save_drafts").click(function(){
			var publishid = $("#publishId").val()+"";
			//设置 form action的值
			if(publishid!=null&&publishid!="")
				$("#form1").attr("action","publish.do?method=editDrafts");
			else
				$("#form1").attr("action","publish.do?method=saveDrafts");
			//设置publishAreaNameValues值
			var selectValues = $("#publishArea").val();
				if($("#publishAreaNameValues").val()==""){
					if(selectValues=="组织机构"){
						$("#publishAreaNameValues").val(null).val($("#fieldset_org_names").html());
					}
					else if(selectValues=="人员"){
						$("#publishAreaNameValues").val(null).val($("#fieldset_per_names").html());
					}
					else
						$("#publishAreaNameValues").val(null);
			}
			if($("#publishAreaIdValues").val()==""||$("#publishAreaIdValues").val()==null){
				alert("请选择发布范围！");
				return false;
			}		
		})
	//送审
		$("#audit").click(function(){
				$("#form1").attr("action","publish.do?method=directlyToAudit");
			//设置publishAreaNameValues值
			var selectValues = $("#publishArea").val();
				if($("#publishAreaNameValues").val()==""){
					if(selectValues=="组织机构"){
						$("#publishAreaNameValues").val(null).val($("#fieldset_org_names").html());
					}
					else if(selectValues=="人员"){
						$("#publishAreaNameValues").val(null).val($("#fieldset_per_names").html());
					}
					else
						$("#publishAreaNameValues").val(null);
			}
			if($("#publishAreaIdValues").val()==""||$("#publishAreaIdValues").val()==null){
				alert("请选择发布范围！");
				return false;
			}		
		})
	//直接发布
		$("#directlyPublish").click(function(){
				$("#form1").attr("action","publish.do?method=directlyPublish");
			//设置publishAreaNameValues值
			var selectValues = $("#publishArea").val();
			if($("#publishAreaNameValues").val()==""){
					if(selectValues=="组织机构"){
						$("#publishAreaNameValues").val(null).val($("#fieldset_org_names").html());
					}
					else if(selectValues=="人员"){
						$("#publishAreaNameValues").val(null).val($("#fieldset_per_names").html());
					}
					else
						$("#publishAreaNameValues").val(null);
			}		
			if($("#publishAreaIdValues").val()==""||$("#publishAreaIdValues").val()==null){
				alert("请选择发布范围！");
				return false;
			}		
		})		
		
	}); 
//-->
</script>
<br/><br/>
<form method="post" id="form1" >
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 主题<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					<input type='text' id='subject' name="subject"   value="${requestScope.publishInfo.subject }"  maxlength="80"
						  	   style="width: 80%" alt="allowBlank:false,vtext:'主题不能为空！'" />
				</td>
			</tr>
		 <tr>
			<td class="label"> 
				内容<font color="red" >*</font>
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="publishContent"id="publishContent"  onkeyup="this.value = this.value.slice(0, 900)"
							alt="allowBlank:false,vtext:'内容不能为空！'" >${requestScope.publishInfo.publishContent }</textarea>
			</td>
		</tr>
		  <tr>
			 <td class="label" >
				 有效期<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					<input type="text" id="valid" name="valid"  class="text medium"
						   alt="vtype:'moreThen',vtext:'有效期不能为空！',allowBlank:false" 
						   onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',minDate:'%y-%M-%d %H-%m-%s',readOnly:true})" 
						   value='${fn:substring(requestScope.publishInfo.valid, 0, 19)}'/>
				</td>
			</tr>
			  <tr>
			 <td class="label" >
				 发布范围<font color="red" >*</font>
				</td>
				<td class="content"  colspan="3">
					<select id="publishArea" name="publishArea" alt="allowBlank:false,vtext:'发布范围不能为空！'" >
						<option value="">--请选择--</option>
						<option value="组织机构">组织机构</option>
						<option value="人员">人员</option>
					</select>
					<fieldset id="fieldset_org" style="display: none">
						<legend>发布到下列组织机构 </legend> 
						<input class="btn" type="button" value="请选择组织"   id="orgSelect">
						<eoms:xbox id="depttree" dataUrl="${app}/xtree.do?method=dept" rootId=""
								rootText='组织机构' valueField="publishAreaIdValues" handler="orgSelect" textField="fieldset_org_names"
								checktype="dept" single="false"></eoms:xbox>
						<div id="fieldset_org_names"  class="viewer-list"></div>		
					</fieldset>
					<fieldset id="fieldset_per" style="display: none">
						<legend>发布到下列人员 </legend> 
						<input class="btn" type="button" value="请选择人员"   id="userSelect">
						<eoms:xbox id="usertree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
								rootText='人员' valueField="publishAreaIdValues" handler="userSelect" textField="fieldset_per_names"
								checktype="user" single="false"></eoms:xbox>
						<div id="fieldset_per_names"  class="viewer-list"></div>		
					</fieldset>
						<fieldset id="fieldset_default" style="display: none">
							<legend>发布到 </legend> 
							${requestScope.areaNames }
						</fieldset>
						<input type="hidden" id="publishAreaIdValues"  name="publishAreaIdValues"    value="${requestScope.areaIds }"/>
						<input type="hidden" id="publishAreaNameValues"  name="publishAreaNameValues"  value="${requestScope.areaNames }"/>					
				</td>
				
			</tr>  
			<tr>
			 <td class="label" >
				 是否需要审批<font color="red" >*</font>
				</td>
				<td class="content" >
					<select id="isAudit" name="isAudit" >
						<option value="1"  selected="selected">是</option>
						<option value="0">否</option>
					</select>
				</td>
				<td class="label" >
				 审批人
				</td>	
				<td class="content"  id="auditHtml">	
					<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
							rootText='审批人' valueField="taskOwnerIdId" handler="taskOwnerNameId" textField="taskOwnerNameId"
							checktype="user" single="true">
					</eoms:xbox>
							<input type='text' id="taskOwnerNameId" name="taskOwnerName"  
								   readonly="true" value="${requestScope.auditName }"  
								   alt="allowBlank:false,vtext:'审批人不能为空！'" />
							<input type='hidden' id="taskOwnerIdId" name="taskOwnerId"  value="${requestScope.auditId }" />
				</td>
			</tr>
			
		</table>
		<br/>
		<input type='hidden' id="publishId" name="id"  value="${requestScope.publishInfo.id }"/>
		<input id="save_drafts" type="submit" class="btn" value="保存草稿" />
		<input id="audit" type="submit" class="btn" value="送审" />
		<input id="directlyPublish" type="submit" class="btn" value="发布" style="display: none"/>
		<input id="reset" type="reset" class="submit" value="重置" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>