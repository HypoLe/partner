<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<c:if test="${listType == ''}">
<html:form action="/pnrFeeInfoMains.do?method=search"
	styleId="pnrAgreementMainForm" method="post">
<div style="border:1px solid #98c0f4;padding:5px;width:98%;" class="x-layout-panel-hd">
工具栏： 
  <img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer"/>
  <span id="openQuery"  style="cursor:pointer" onclick="openQuery(this);">关闭快速查询</span>
</div>

<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable" width="75%">
	
	<tr>
		<td class="label" align="right">
			合同名称：
		</td>
		<td>
			<input type="text" name="contractName" class="text"/>
		</td>
		<td class="label" align="right">
			合同状态：
		</td>
		<td>
			<select name="state" id="state" style="width:150px">
				<option></option>
				<option value="8">草稿</option>
				<option value="9">待处理</option>
				<option value="2">有效</option>
			</select>
		</td>
	</tr>
	
	<tr>	
		<td class="label" align="right">
			合同金额大于等于：
		</td>
		<td>
			<input type="text" name="contractAmountMT" class="text"/><font>（万元）</font>
		</td>
		<td class="label" align="right">
			合同金额小于等于：
		</td>
		<td>
			<input type="text" name="contractAmountLT" class="text"/><font>（万元）</font>
		</td>
	</tr>
	
	<tr>
		<td colspan="4" align="center">
			<input type="submit" value="查询" id="submitSearch" class="button"/>
		</td>
	</tr>
	</table>
</div>
</html:form>
</c:if>
<div class="ui-widget-content ui-corner-top " style="margin-top: 15">
<c:set var="myCheckboxAllBtn" scope="page">
	<input type="checkbox" id="myCheckbox" />
</c:set>
<display:table name="pnrFeeInfoMainList" cellspacing="0" cellpadding="0"
	id="pnrFeeInfoMainList" pagesize="${pageSize}"
	class="table pnrFeeInfoMainList" export="false"
	requestURI="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=search&state=${state}"
	sort="list" partialList="true" size="resultSize">
	
	<display:column media="html" title="${myCheckboxAllBtn}">
		<input type="checkbox" name="dealId" class="checkAble" value="${pnrFeeInfoMainList.id}" />
	</display:column>
	
	<display:column property="contractNO" sortable="false"
		headerClass="sortable" title="合同编号" paramId="id" paramProperty="id" />
		
	<display:column property="contractName" sortable="true"
		headerClass="sortable" title="合同名称" paramId="id" paramProperty="id" />
		
	<display:column title="查看">
		<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=detail&id=${pnrFeeInfoMainList.id }"
		   target="_blank"> <img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	<display:column title="编辑">
		<a
			href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=edit&editType=proxy&id=${pnrFeeInfoMainList.id }"
			target="_blank">编辑</a>
	</display:column>
	<display:column title="历史信息">
	<a href="${historyURI }&id=${pnrFeeInfoMainList.id }" target="_blank"> <img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
</display:table>
</div>
<input type="button" class="btn" value="删除" id="deleteAll" />

<c:if test="${listType == 'draft'}">
<div class="ui-widget-content ui-corner-top " style="margin-top: 15">
<form action="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=deal" method="post" id="myForm" >
	<logic:notEmpty name="pnrFeeInfoMainList" scope="request">
		<table id="sheet" class="formTable">
			<input type="hidden" name="dealIds" id="dealIds" />
			<input type="hidden" name="listType" value="${listType}" />
			<input type="hidden" name="operateType" value="1" />
			<input type="hidden" name="myTextType" value="plainText" />
			
			<tr>
				<td class="label">操作类型*</td>
				<td class="content">
				<select id="operateType" name="operateType">
					<option id="rebut19" value="rebut19">
						提交审核
					</option>
				</select>
			</td>
			</tr>
			<tr >
				<td class="label">备注*</td>
				<td colspan="3"><textarea id="myText"
					class="textarea max" name="myText" 
					alt="width:500,allowBlank:false"></textarea></td>
			</tr>
		</table>
		<!-- This hidden area is for batch dealing. -->
		<fieldset>
			<legend> 派发给:合同审核人 </legend> 
			<eoms:chooser id="test" type="user" config="{returnJSON:true,showLeader:true}"
			category="[{id:'taskOwner',text:'派发',childType:'user',allowBlank:false,vtext:'请选择派发对象'}]" />
		</fieldset>
		<fieldset>
			<legend> 操作 </legend> 
			<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="确定"></html:submit>
			<html:reset styleClass="btn" property="method.save"
			styleId="method.save" value="重置"></html:reset>
		</fieldset>
	</logic:notEmpty>
</form>
</div>
</c:if>

<c:if test="${listType == 'audit'}">
<div class="ui-widget-content ui-corner-top " style="margin-top: 15">
<form action="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=deal" method="post" id="myForm" >
	<logic:notEmpty name="pnrFeeInfoMainList" scope="request">
		<table id="sheet" class="formTable">
			<input type="hidden" name="dealIds" id="dealIds" />
			<input type="hidden" name="listType" value="${listType}" />
			<input type="hidden" name="myTextType" value="plainText" />
			<tr>
				<td class="label">操作类型*</td>
				<td class="content">
				<select id="myOption" name="operateType" >
					<option selected="selected" value="3">
						确认通过
					</option>
					<option value="2">
						驳回
					</option>
				</select>
			</td>
			</tr>
			<tr>
				<td class="label">派发对象</td>
				<td colspan="3">
				<span id="mySpan" >合同付款负责人</span>
				</td>
			</tr>
			<tr >
				<td class="label">备注*</td>
				<td colspan="3"><textarea id="myText"
					class="textarea max" name="myText" 
					alt="width:500,allowBlank:false"></textarea></td>
			</tr>
		</table>
		<fieldset>
			<legend> 操作 </legend>
			<input type="submit" class="btn" value="确定" /> 
			<input type="reset" class="btn" value="重置" /> 
		</fieldset>
	</logic:notEmpty>
</form>
</div>
</c:if>

<script type="text/javascript">
var myJ = jQuery.noConflict();

myJ(function() {
	
	myJ('input#deleteAll').click(function(){
		var dealIds="";
		myJ('input:checked').each(function(){
			dealIds+=myJ(this).val()+";";
		});
		if(dealIds==''){
			alert('请选择需要删除的合同!');
			return false;
		}else{
			location.href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=delete&listType=${listType}&dealIds="+dealIds;
		}
	});
	
	var myValidationForm = document.getElementById('myForm');
	if(myValidationForm){
		v = new eoms.form.Validation({form:'myForm'});
		v.custom = function(){
			var dealIdList = document.getElementsByName("dealId");
			var idResult = "";
			for (i = 0 ; i < dealIdList.length; i ++) {
				if (dealIdList[i].checked) {
					var myTempStr=dealIdList[i].value;
					idResult+=myTempStr.toString()+","
				}
			}
			
			document.getElementById("dealIds").value=idResult.toString();
			
			if (idResult == "") {
				alert("请选择需要处理的合同");
				return false;
			} else {
				if(confirm("确定要全部处理吗？")){
		 			return true;
				}
				else{
					return false;
				}
			}
		};
	}
	
	function openQuery(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开快速查询";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭快速查询";
	}
}
	
	myJ('select#myOption').bind('change',function(event){
		if(this.value=='3'){
			myJ('span#mySpan').text('合同付款负责人');
		}else if(this.value=='2'){
			myJ('span#mySpan').text('合同草稿提交人');
		}else{
		}
	});
	
	
	//为Tr加上点击事件，点击时候可以选中改行的checkbox。
	//屏蔽掉tr的index为0的情况，我们认为表头是不可以点击的
	myJ('table#pnrFeeInfoMainList').find('tr:gt(0)')
	.css('cursor','pointer')
	.bind('click',function(event){
		var myCheckbox =  myJ(this).find('input:checkbox');
		if( myCheckbox[0].checked){
			myCheckbox.attr('checked',false);
		}else{
			myCheckbox.attr('checked',true);
		}
		
		//只有当可选的checkBox的个数实际等于已经选择的checkbox的个数时，才选择上用于全选的checkbox
		if( myJ('input:checkbox.checkAble').size() == myJ(':checked.checkAble').size()){
			myJ('input#myCheckbox').attr('checked',true);		 
		}else{
			myJ('input#myCheckbox').attr('checked',false);	
		}
		
	});
	
	myJ('input#myCheckbox').bind('click',function(event){
		//至少有1个checkbox没被选中的话，则执行全选操作，否则执行反选操作
		if(myJ('input:checkbox.checkAble').size()>myJ(':checked.checkAble').size()){
			myJ('input.checkAble').attr('checked',true);
		}else{
			myJ('input.checkAble').attr('checked',false);
		}
	});
	
	myJ('input.checkAble').bind('click',function(event){
		if(myJ(this)[0].checked){
			myJ(this).attr('checked',false);
		}else{
			myJ(this).attr('checked',true);
		}
	});
});

</script>

<%@ include file="/common/footer_eoms.jsp"%>