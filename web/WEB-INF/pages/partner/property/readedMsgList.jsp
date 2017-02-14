<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
	var jq=jQuery.noConflict();
	function res(){
		var formElement=document.getElementById("readForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
	}
</script>
<div align="center"><b>提醒信息-列表页面</div><br>
<div id="listQueryObject" style="border:1px solid #98c0f4;padding:5px;" >
	<form action="${app}/partner/property/remind.do?method=goToReadedMsgPage" method="post" id="readForm">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
						<td class="label">
						 	提醒内容
						</td>
						<td class="content" colspan="4">
								<input class="text" type="text" name="contentStringLike" value="${contentStringLike}" alt="allowBlank:true" />
						</td>
				</tr>
				<!--<tr>
						<td class="label">
						 	阅读时间
						</td>
						<td class="content" colspan="3" >
								<input type="text" id="readDateGreaterThan" name="readDateGreaterThan" class="text medium"  readonly="true" 
								onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,true,-1);" alt="allowBlank:false,vtext:'请选择阅读时间！'" value="${readDateGreaterThan}"/>
								&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;			
								<input type="text" id="readDateLessThan" name="readDateLessThan" class="text medium"  readonly="true"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,true,-1);"  alt="allowBlank:false,vtext:'请选择阅读时间！'" value="${readDateLessThan}"/>
						</td>
				</tr>
				  -->
			</table>
			<table>
				<tr>
					<td>
					<input type="submit" class="btn" value="查询" />
					<input type="button"  class="btn"  id="reset" value="重置" onclick="res();">
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>
<logic:present name="readedMsgList" scope="request">
	<display:table id="readedMsgList"		name="readedMsgList" 	pagesize="${pageSize}" size="${resultSize}"	
		requestURI="${app}/partner/property/remind.do?method=goToReadedMsgPage" export="false" 
		sort="list" cellspacing="0" 	cellpadding="0" class="table readedMsgList" partialList="true"  >
		<display:column sortable="true" headerClass="sortable" title="提醒内容">
			${readedMsgList.content}
		</display:column>
	</display:table>
</logic:present>
	
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
