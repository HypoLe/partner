<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
<!--
function checkContet(){
	if(document.forms[0].users.value==""){
    		alert("请选择群组!");
    		return false; 
    	}
    	if(document.forms[0].content.value==""){
    		alert("请填写内容!");
    		return false; 
    	}
    	if(document.forms[0].content.value.length>"200") {
			alert("发送内容长度不能超过200个字符！");
			return false;
			}
		
}
function showMobiles() {
	window.open("smsSend.do?method=forward2Mobiles&deleted=2",'win1','directories=no,fullscreen=no,height=420,width=600,top=0,left=0,location=no,menubar=no,resizable=yes,scrollbars=yes,status=yes,toolbar=no');	
}
Ext.QuickTips.init();
var reback = false;

Ext.onReady(function(){
	var treeCfg = {
		btnId:'treeBtn',
		treeDataUrl:"smsSend.do?method=forward2Mobiles&deleted=2",treeRootId:'-1',treeRootText:'号码组',treeChkMode:'',
		showChkFldId:'users',saveChkFldId:'mobiles'
	};
	
	treeCfg.onOutput = function(textList,dataList,jsonData){
		var mobileArr = [], userArr = [];
		Ext.each(jsonData,function(item){
			userArr.push(item.name);
			var temp = item.id.split(",");
			mobileArr = mobileArr.concat(temp);
			
		});
		if (this.showChkFldId) {

			$(this.showChkFldId).update(userArr.toString());
			//激活文本域的验证
			try {
				$(this.showChkFldId).focus();
				$(this.showChkFldId).blur();
			}
			catch(e){}
		}
		if (this.saveChkFldId) {
				$(this.saveChkFldId).update(mobileArr.toString());
		}
	};
	var tree = new xbox(treeCfg);

});
//-->
</script>
<!--根据给定的实例名生成标题 -->
<title><fmt:message key="smsContentTemplateDetail.title" />
</title>
<!-- <content tag="heading"><fmt:message key="smsContentTemplateDetail.heading"/></content> -->


<html:form action="/smsSend.do?method=sendImmediate" method="post"
	styleId="smsSendForm">
	<table class="formTable">
		<caption>
			<bean:message key='smsTitle.newContent' />
		</caption>
		<html:hidden property="mobiles" />

				<html:hidden property="mobiles" />
		
		<tr>
			<td class="label">
				<bean:message key='smsSend.code' />
			</td>
			<td class="content max">
				<html:textarea property="users" styleId="users" cols="90" rows="2" readonly="true"></html:textarea>
				<input type="button" id="treeBtn" value="选择群组"/>
			</td>
		</tr>

		<tr>
			<td class="label">
				<bean:message key='smsSend.content' />
			</td>
			<td class="content max">
				<html:textarea property="content" cols="90" rows="5"></html:textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="label">
				<html:checkbox styleId="isSendVoice" property="isSendVoice">&nbsp;<bean:message
						key='smsSend.isSendVoice' />
				</html:checkbox>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<html:submit property="save" onclick="return checkContet()">
					<bean:message key="smsButton.send" />
				</html:submit>
			</td>
		</tr>
	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>