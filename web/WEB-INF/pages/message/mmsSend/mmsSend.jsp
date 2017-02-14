<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
<!--
Ext.QuickTips.init();
var reback = false;
xbox.prototype.onAfterCheck = function(node, checked){
		reback = false;
        if(checked &&(!node.attributes.mobile||node.attributes.mobile=='')){      
        	alert('没有该人员的电话号码！');
        	node.getUI().checkbox.checked = false;     	
        	var record = this.gridData.getById(node.id);
        	if (typeof record == "object")
					this.gridData.remove(record);
        }
};
Ext.onReady(function(){
	var treeCfg = {
		btnId:'treeBtn',
		treeDataUrl:"smsSend.do?method=forward2Mobiles",treeRootId:'-1',treeRootText:'号码组',
		showChkFldId:'',saveChkFldId:'mobiles',
		panelsConfig:[{title:'人员树',dataUrl:'${app}/xtree.do?method=userFromDept',checktype:'user'}]
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
<title><bean:message key="mms.title"/></title>

<html:form action="/mms.do?method=sendMms" method="post" styleId="mmsSendForm"> 
	<table class="formTable">
		<caption><bean:message key="mms.title"/></caption>
		<tr>
			<td class="label">
				<bean:message key='mms.mobiles'/>
			</td>
			<td class="content max">
				<html:textarea property="mobiles" styleId="mobiles" cols="90" rows="2"></html:textarea>
				<input type="button" id="treeBtn" value="选择群组"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='mms.subject'/>
			</td>
			<td class="content">
				<html:text property="subject" styleId="subject" size="123"></html:text>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='mms.txtcontent'/>
			</td>
			<td class="content max">
				<html:textarea property="txtContent" styleId="txtContent" cols="90" rows="2"></html:textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				<bean:message key='mms.content'/>
			</td>
			<td class="content max">
				<eoms:attachment idList="" idField="accessories" appCode="Mms"/>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
				<html:submit property="save"><bean:message key="mms.send"/></html:submit>
			</td>
		</tr>		
	</table>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>