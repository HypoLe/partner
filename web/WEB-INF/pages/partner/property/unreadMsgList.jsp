<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	var jq=jQuery.noConflict();
	function readMsg(id) {
		if(confirm("批阅后该条不再提醒，确定要批阅吗?")){
			Ext.Ajax.request({
				url:"${app}/partner/property/remind.do",
				params:{method:"readMsg",id:id},
				success:function(res,opt) {
					Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
				},
				failure:function(res,opt) {
					Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
				}
			});
		}
	}
	function selectAll(){
		var msgids = document.getElementsByName("msgids");
		var temp=msgids[0].checked;
		if(temp==null){
			for (i = 0 ; i < msgids.length; i ++) {
			cardNumberList[i].checked='checked';
			}
		}else{
			for (i = 0 ; i < msgids.length; i ++) {
			msgids[i].checked=!msgids[i].checked;
			}
		}
	}
	function readAllmsg(){
		var ids=document.getElementsByName("msgids");
		var idResult="";
		for(i=0;i<ids.length;i++){
			if(ids[i].checked){
				var idsStr=ids[i].value;
				idResult+=idsStr.toString()+";";
			}
		}
		if(idResult==""){
			alert("请选择要批阅的记录");
			return false;
		}else{
			if(confirm("确定要批阅吗?")){
				Ext.Ajax.request({
					url:"${app}/partner/property/remind.do",
					params:{method:"readAllmsg",id:idResult},
					success:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					},
					failure:function(res,opt) {
						Ext.Msg.alert("提示：",Ext.util.JSON.decode(res.responseText).infor,function() {window.location.reload();});
					}
				});
			}
		}
	}
	function res(){
		var formElement=document.getElementById("unreadForm")
	   	 var inputs = formElement.getElementsByTagName('input');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
	}
</script>
<div align="center"><b>提醒信息-列表页面</div><br><br/>
<div id="listQueryObject" style="border:1px solid #98c0f4;padding:5px;" >
	<form action="${app}/partner/property/remind.do?method=goToUnreadMsgPage" method="post" id="unreadForm">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
						<td class="label">
						 	提醒内容
						</td>
						<td class="content" colspan="3" >
								<input class="text" type="text" name="contentStringLike" value="${contentStringLike}" alt="allowBlank:true" />
						</td>
				</tr>
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
<logic:present name="unreadMsgList" scope="request">
	<display:table id="unreadMsgList"		name="unreadMsgList" 	pagesize="${pageSize}" size="${resultSize}"
					requestURI="${app}/partner/property/remind.do?method=goToUnreadMsgPage" export="false" 	sort="list" cellspacing="0" 
					cellpadding="0" class="table unreadMsgList" partialList="true"  >
		<display:column  title="<input type='checkbox' onclick='javascript:selectAll();'>" >
         	<input type="checkbox" name="msgids" value="${unreadMsgList.id}" >
    	</display:column>
		<display:column sortable="true" headerClass="sortable" title="提醒内容">
			${unreadMsgList.content}
		</display:column>
		<display:column sortable="true"	headerClass="sortable" title="批阅" >
			 <a href="javascript:void(0)" onclick="readMsg('${unreadMsgList.id}')">
				<img src="${app}/images/icons/edit.gif">
			</a>
		</display:column>
	</display:table>
	<c:if test="${!empty unreadMsgList }">
		<input type="button" value="批量批阅" onclick="readAllmsg()">
	</c:if>
</logic:present>
</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
