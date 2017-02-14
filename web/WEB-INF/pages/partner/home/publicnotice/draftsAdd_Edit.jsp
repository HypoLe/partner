<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<script type="text/javascript">
<!--
	Ext.onReady(function() {
	    v = new eoms.form.Validation({form:'form1'});
	    var saveSuccess=Boolean(${saveSuccess});
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
	
	var myJ=jQuery.noConflict(); 
	
	 
	function frset(auditHtml_){
	       var isAudit = '${requestScope.publishInfo.isAudit}';
	      
		   if(isAudit==""||isAudit=="0"){//不需要审批 
			myJ("#auditorname").attr("alt","");
			myJ("#audit").hide();
			myJ("#directlyPublish").show();
		  }else{
		    myJ("#auditHtml").html(auditHtml_);
		    myJ("#directlyPublish").hide();
			myJ("#audit").show();			
		  }		
		}
	
	jQuery(function($){ 
	  function setAndCheckPublishValue(){		
		var publishNames = $("#publishedRangeName_div").html();
		$("#publishedRangeName").val(publishNames);
		
	    if($("#publishedRange").val()==""||$("#publishedRange").val()==null){
			alert("请选择发布范围！");
			return false;
	    }	
	    return true;	
	   }
	
		var auditHtml = $("#auditHtml").html();
		//是否 审批 下拉默认	
		frset(auditHtml);	
		//重置
		$("#reset").click(function(){
			frset(auditHtml);	
		});	
		
		$("#isAudit").change(function(){
			if($(this).val()=="0"){//不需要审批
				//$("#auditHtml").html(null);
				myJ("#auditorname").attr("alt",""); 
				$("#audit").hide();
				$("#directlyPublish").show();
			}
			else{
				$("#auditHtml").html(auditHtml);
				$("#audit").show();
				$("#directlyPublish").hide();
			}
		});
		
	///////////////////////////////////////////////////////////////////////	
	    //表单提交 	
	    //保存草稿
		$("#save_drafts").click(function(){		
			$("#form1").attr("action","publish.do?method=saveDrafts");
		    var publishNames = $("#publishedRangeName_div").html();
		    $("#publishedRangeName").val(publishNames); //存草稿不需要校验
		});
		
		$("#rtnDraftsList").click(function(){		
			$("#form1").attr("action","publish.do?method=getList&listType=1");
		});
	   //送审
		$("#audit").click(function(){
			$("#form1").attr("action","publish.do?method=toAudit&isToNextAudit=false");		
			if($('#auditorid').val().trim()==''){
			    alert("没指定审批人！");
			    return false;
			}else if($('#auditorid').val().trim()=='${sessionform.userid}'){
			   alert("审批人不能是自己！");
			    return false;
			}	 	
			return setAndCheckPublishValue();
			//需审批时，alt="allowBlank:false,vtext:'审批人不能为空！'"进行验证			
		});
	    //直接发布
		$("#directlyPublish").click(function(){
			$("#form1").attr("action","publish.do?method=directlyPublish");			
			return setAndCheckPublishValue();
		})	;
		
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
						  	class='text'   style="width: 80%" alt="allowBlank:false,vtext:'主题不能为空！'" />
				</td>
			</tr>
			
			<tr>		 
			    <td class="label" >
				 发布时间<font color="red" >*</font>
				</td>
				<td class="content" >
					<input type="text" id="publishTime" name="publishTime"  class="text medium"
						   alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank:false" readOnly					
						   value='${fn:substring(requestScope.publishInfo.publishTime,0,19)}'/>
				</td>
			    <td class="label" >
				 有效期<font color="red" >*</font>
				</td>
				<td class="content" colspan="3">
					<input type="text" id="valid" name="valid"  class="text medium"
						   alt="vtype:'moreThen',vtext:'有效期不能为空！',allowBlank:false" 
						   onclick="popUpCalendar(this, this,null,null,null,null,-1);"  readonly
						   value='${fn:substring(requestScope.publishInfo.valid, 0, 19)}'/>
				</td>
			</tr>
			
			<tr>
				<td class="label" >
				发布人 
				</td>
				<td class="content">
					 ${requestScope.publishInfo.publisherName }  
				</td>
				<td class="label" >
				发布人部门 
				</td>
				<td class="content">
					${requestScope.publishInfo.publisherDeptName } 
				</td>
			</tr>
			
		   <tr>
			<td class="label"> 
				内容<font color="red" >*</font>
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="publishContent" id="publishContent" onblur="this.value = this.value.slice(0, 2000)"
							alt="allowBlank:false,vtext:'内容不能为空！'" >${requestScope.publishInfo.publishContent }</textarea>
			</td>
		   </tr>
		  
		   <tr>
			 <td class="label" >
				 发布范围<font color="red" >*</font>
				</td>
				<td class="content"  colspan="3">
					<fieldset id="fieldset_per" >
						<legend>发布到下列组织机构或人员 </legend> 
						<input class="btn" type="button" value="请选择"   id="userSelect">
						<eoms:xbox id="usertree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
								rootText='发布范围' valueField="publishedRange" handler="userSelect" textField="publishedRangeName_div"
								single="false"></eoms:xbox>
						<input  type="hidden" name="publishedRange" id="publishedRange" value="${publishInfo.publishedRange}" />
						<input  type="hidden" name="publishedRangeName" id="publishedRangeName" value="${publishInfo.publishedRangeName}" />
						<div id="publishedRangeName_div"  class="viewer-list">${publishInfo.publishedRangeName }</div>		
					</fieldset>		 				
				</td>			
			</tr>  
		  
		   <tr>
				<td class="label">
				 附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<eoms:attachment scope="request"  name="publishInfo"   idField="file" property="file"  appCode="pnrcontact"  alt="allowBlank:true,vtext:'附件'"  />
				</td>
		   <tr>
		  
		   <tr>
			    <td class="label" >
				 是否需要审批<font color="red" >*</font>
				</td>
				<td class="content" >
					<select id="isAudit" name="isAudit" >
						<option value="1" <c:if test="${publishInfo.isAudit==1}">selected</c:if>>是</option>
						<option value="0" <c:if test="${publishInfo.isAudit==0}">selected</c:if>>否</option>
					</select>
				</td>
				<td class="label" >
				 审批人
				</td>	
				<td class="content"  id="auditHtml">	
					<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId=""
							rootText='审批人' valueField="auditorid" handler="auditorname" textField="auditorname"
							checktype="user" single="true">
					</eoms:xbox>
							<input type='text' id="auditorname" name="auditorname"  class='text'
								   readonly="true" value="${publishInfo.auditorname}"  
								   alt="allowBlank:false,vtext:'审批人不能为空！'" />
							<input type='hidden' id="auditorid" name="auditorid"  value="${publishInfo.auditorid}" />
				</td>
			</tr>
		
		</table>
		<br/>
		<input type='hidden' id="id" name="id"  value="${requestScope.publishInfo.id }"/>
		<input type='hidden' id="publishId" name="publishId"  value="${requestScope.publishInfo.id }"/>
		<input id="save_drafts" type="submit" class="btn" value="保存草稿" />
		<input id="rtnDraftsList" type="submit" class="btn" value="返回草稿列表" />
		<input id="audit" type="submit" class="btn" value="送审" />
		<input id="directlyPublish" type="submit" class="btn" value="发布" style="display: none"/>
		<input id="reset" type="reset" class="submit" value="重置" />
		
</form>

<%@ include file="/common/footer_eoms.jsp"%>