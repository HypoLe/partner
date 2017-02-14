<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
<script type="text/javascript">
<!--
Ext.QuickTips.init();
var reback = false;
xbox.prototype.onAfterCheck = function(node, checked){
		reback = false;
        if(checked &&(!node.attributes.mobile||node.attributes.mobile=='')){ 
        	alert('${eoms:a2u('该人员没有配置手机号码')}');
        	node.getUI().checkbox.checked = false;     	
        	var record = this.gridData.getById(node.id);
        	if (typeof record == "object")
					this.gridData.remove(record);
        }
};
Ext.onReady(function(){
	v = new eoms.form.Validation({form:'mmsSendForm'});
	var treeCfg = {
		btnId:'treeBtn',
		treeDataUrl:"smsSend.do?method=forward2Mobiles",treeRootId:'-1',treeRootText:'${eoms:a2u('号码组')}',treeChkMode:'',
		showChkFldId:'',saveChkFldId:'mobiles',
		panelsConfig:[{title:'${eoms:a2u('人员树')}',dataUrl:'${app}/xtree.do?method=userFromDept',checktype:'user'}]	
	};

	treeCfg.onOutput = function(textList,dataList,jsonData){
		var mobileArr = [], userArr = [];
		Ext.each(jsonData,function(item){
			userArr.push(item.name);
			var temp = item.mobile.split(",");
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

<html:form action="/smsSend.do?method=sendMessageImmediate" method="post" styleId="mmsSendForm"> 
	<table class="formTable small" style="width:255px">
		<tr>
			<td class="label" align="center">
				<bean:message key='smsSend.mobile'/>
			</td>
			<td class="content max">
				<html:text property="mobiles" styleClass="text" styleId="mobiles" alt="allowBlank:false,vtext:'${eoms:a2u('请选择联系人或输入接收人手机号码')}'"></html:text>
				<input type="button" id="treeBtn" value="${eoms:a2u('人员')}"/>
			</td>
		</tr>
		<tr>
			<td class="label"  align="center">
				<bean:message key='smsSend.brContent'/>
			</td>
			<td class="content max">
				<html:textarea property="content" styleId="content" cols="15" rows="1" alt="allowBlank:false,vtext:'${eoms:a2u('请输入发送内容')}'"></html:textarea>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<html:submit property="save"><bean:message key="smsButton.sendMessage"/></html:submit>
			</td>
		</tr>		
	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>