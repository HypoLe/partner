<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
<!--
	Ext.onReady(function() {
	    v = new eoms.form.Validation({form:'form1'});
	    v.custom = function(){ 
	    	return true;
	    };
	    var saveSuccess=Boolean(${saveSuccess}) ; 
	    if(saveSuccess){
	       alert("${saveMessage}");
	    }	    
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
		/* 提交表单之前 把发布范围，审批人数据封装好 */
		function setTaskOwnerValue(){
			
			var publishIds = $("#taskOwnerId_temp").val();
			var publishNames = $("#fieldset_names").html();
			var auditerId = $("#auditId_temp").val();
			var auditName = $("#auditName_temp").val(); 
			
			var ids = auditerId +","+publishIds;
			var names = auditName +","+publishNames;
			
			if(ids!=","&&names!=","){
				$("#taskOwnerIds").val(ids);
				$("#taskOwnerNames").val(names);
			}
		}
	//--------------- 表单提交-----------------	
		$("#save_drafts").click(function(){
			setTaskOwnerValue();
			$("#form1").attr("action","contact.do?method=saveDraft")
		});
		
		$("#rtnDraftsList").click(function(){		
			$("#form1").attr("action","contact.do?method=search&type=draft");
		});
		
		$("#audit").click(function(){
			setTaskOwnerValue();
				 
			if($('#auditId_temp').val().trim()==''){
			    alert("没指定审批人！");
			    return false;
			}else if($('#auditId_temp').val().trim()=='${sessionform.userid}'){
			   alert("审批人不能是自己！");
			    return false;
			}	 
			if($('#taskOwnerId_temp').val()!=''){
			  $("#form1").attr("action","contact.do?method=toAudit")
			}else{
			  alert("发布范围不能为空！");
			  return false;
			}
		});
		$("#publish").click(function(){
			setTaskOwnerValue();
			if($('#taskOwnerId_temp').val()!=''){
			  $("#form1").attr("action","contact.do?method=publish")
			}else{
			  alert("发布范围不能为空！");
			  return false;
			}
		})
	}); 
//-->
</script>
<br/><br/>
<%com.boco.eoms.commons.system.session.form.TawSystemSessionForm sessionInfo = (com.boco.eoms.commons.system.session.form.TawSystemSessionForm) request.getSession().getAttribute("sessionform"); %>
<form method="post" id="form1" >
        
		<table id="sheet" class="formTable">
		<span style="line-height:130%;">
		   当前位置：>>业务联系函>>新建业务联系函<br/>
		</span>	<br/> 	
			<tr > 
			    <td colspan='4' align='right'> 
			         是否短信
						<input type="checkbox" name="isSendSMS"  value="1"  
							<c:if test="${contactMain.isSendSMS==1 }">
								<c:out value="checked='checked'"></c:out>
							</c:if>
							/>
					是否紧急
						<input type="checkbox" name="isUrgent" value="1"
							<c:if test="${contactMain.isUrgent==1 }">
								<c:out value="checked='checked'"></c:out>
							</c:if>
					/>
				 </td>
			</tr>
			<tr>
				<td class="label" >
				 主题<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					<input type='text' id='subject' name="subject"    class="text" value="${contactMain.subject }"  maxlength="80"
						  	   style="width: 90%" alt="allowBlank:false,vtext:'主题不能为空！'" />
				</td>
			</tr>
			<c:if test="${!empty contactMain.code }">
				<tr>
					<td class="label" >
					 文号<font color="red" >*</font>
					</td>
					<td class="content" colspan="3">
						<input type='hidden' id='code' name="code"   value="${contactMain.code }"  maxlength="80"
							  	   style="width: 70%" alt="allowBlank:false" />
						 ${contactMain.code }
					</td>
				</tr>
		</c:if>
		<tr>	
			<td class="label" >
					发布人<font color="red" >*</font>
					</td>
					<td class="content" >
						<input type="text"  class="text medium"  readonly='true'   value='<%=sessionInfo.getUsername()%>'/>
					</td>
			
					<td class="label" >
					 发布部门<font color="red" >*</font>
					</td>
					<td class="content" >
						<input type="text"     readonly='true'  class="text medium"   value='<%=sessionInfo.getDeptname()%>'/>
					</td>
				
			</tr>
		
			<tr>
				<td class="label" >
				 发布时间<font color="red" >*</font>
				</td>
				<td class="content" >
					<input type="text" id="valid" name="publishTime"  class="text medium"
						   alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank:false" readOnly					
						   value='${fn:substring(requestScope.contactMain.publishTime,0,19)}'/>
				</td>
			
			<td class="label" >
				 处理期限<font color="red" >*</font>
				</td>
				<td class="content" >
					<input type="text" id="valid" name="deathTime"  class="text medium"
						   alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank:false" readOnly
						   onclick="popUpCalendar(this, this,null,null,null,null,-1)" 
						   value='${fn:substring(requestScope.contactMain.deathTime,0,19)}'/>
				</td>
			
				
				
			</tr>
		 <tr>
			<td class="label"> 
				内容<font color="red" >*</font>
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="content" id="content"  onblur="this.value = this.value.slice(0, 2000)"
							alt="allowBlank:false,vtext:'内容不能为空！'" >${contactMain.content }</textarea>
			</td>
		  </tr>
			<tr>
				<td class="label">
				 附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<eoms:attachment scope="request"  name="contactMain"   idField="file" property="file"  appCode="pnrcontact"  alt="allowBlank:true,vtext:'证书附件'"  />
				</td>
			<tr>
			
			  <tr>
			  <td class="label" >
				 审核人
				</td>	
				<td class="content"  id="auditHtml">	
					<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
							rootText='审核人' valueField="auditId_temp" handler="auditName_temp" textField="auditName_temp"
							checktype="user" single="true">
					</eoms:xbox>
					 <input type='text' id="auditName_temp" name="auditName_temp"  
								   readonly="true" value="${requestScope.auditName }"  
								   class="text" />
					<input  type="hidden"  name='auditIds' id="auditId_temp" value="${contactMain.approver}"/>		
				</td>
			 <td class="label" >
				 发布范围<font color="red" >*</font>
				</td>
				<td class="content"  colspan="3">
					<fieldset id="fieldset_per" >
						<legend>发布到下列组织机构或人员 </legend> 
						<input class="btn" type="button" value="请选择"   id="userSelect">
						<eoms:xbox id="usertree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
								rootText='发布范围' valueField="taskOwnerId_temp" handler="userSelect" textField="fieldset_names"
								single="false"></eoms:xbox>
								<input  type="hidden"  id="taskOwnerId_temp" value="${contactMain.publishedRange}" />
						<div id="fieldset_names"  class="viewer-list">${contactMain.publishedRangeName }</div>		
					</fieldset>
				</td>
				
			</tr>  
			
		</table>
		<br/>
		<input type='hidden'  value="${requestScope.taskOwnerIds }" id="taskOwnerIds" name="taskOwnerIds"  alt="allowBlank:false,vtext:'发布范围不能为空！'" />
		<input type='hidden'  value="${requestScope.taskOwnerNames }" id="taskOwnerNames" name="taskOwnerNames"  />
		<input type='hidden'  value="${requestScope.contactMain.id }"  name="mainId" />
		<input id="save_drafts" type="submit" class="btn" value="保存草稿" />
		<input id="rtnDraftsList" type="submit" class="btn" value="返回草稿列表" />
		<input id="audit" type="submit" class="btn" value="送审" />
		<input id="publish" type="submit" class="btn" value="发布" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>