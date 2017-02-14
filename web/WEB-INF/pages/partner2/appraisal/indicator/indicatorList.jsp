<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>

<div class="message" id="msgDiv" style="width: 300;display: none;font: bold;">${msg }</div>

<c:set var="myCheckboxAllBtn" scope="page">
	<input type="checkbox" id="myCheckbox" />
</c:set>
<input type="button" id="showSearchTemplate" value="查询" class="btn" />
	<form action="${listURI }" method="post" id="indicatorSearchForm">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					考核指标名称
				</td>
				<td>
					<input type="text" class="text" name="indicatorNameStringLike" />
				</td>
			</tr>
		</table>
		<html:submit styleClass="btn" property="method.list"
			styleId="method.list" value="提交查询结果"></html:submit>
	</form>

</hr>

	<display:table name="indicatorList" cellspacing="0" cellpadding="0"
		id="indicatorList" pagesize="${pagesize}"
		class="table indicatorList" export="false"
		requestURI="indicator.do?method=list" sort="list" partialList="true"
		size="${size}">
		<display:column media="html" title="${myCheckboxAllBtn}">
			<input type="checkbox" class="checkAble" value="${indicatorList.id}" id="${indicatorList.id}" />
		</display:column>
		<display:column property="appraisalName"  sortable="true"
			headerClass="sortable" title="考核指标模板名" />
		<display:column property="execType"  sortable="true"
			headerClass="sortable" title="执行类型" />
		<display:column property="status"  sortable="true"
			headerClass="sortable" title="执行状态" />
		<display:column property="execCategory"  sortable="true"
			headerClass="sortable" title="脚本执行方式" />
		<display:column property="scriptFile"  sortable="true"
			headerClass="sortable" title="执行脚本" />
		<display:column title="查看" headerClass="sortable">
			<a href="${app}/partner2/indicator.do?method=goToDetail&id=${indicatorList.id }">
				<img src="${app}/images/icons/search.gif"/>
			</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="编辑"
			paramProperty="id" url="/partner2/indicator.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/nop3/images/edit.gif">
		</display:column>
		<display:column sortable="false" title="删除"
				url="/partner2/indicator.do?method=delete" paramProperty="id"
				paramId="id" media="html">
				<img src="${app }/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>
<script type="text/javascript">

 var myJ=$.noConflict();
 myJ(function(){
 
 	//在表头中间加上提示效果，仿QQ邮箱正中间的提示。
 	var myMsg = "${msg}";
 	if(myMsg&&myMsg!=""){
 		 myJ('#msgDiv').show();
 		 myJ("#msgDiv").hide('fade',{},3000);
 	}
 	
 	//为Tr加上点击事件，选择checkbox
 	//在非关联窗口打开时使用，否则会与关联时checkbox单选操作冲突
 	if(!window.opener){
		myJ('table#indicatorList').find('tr:gt(0)')
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
	}
	
	myJ('input#myCheckbox').bind('click',function(event){
		//至少有1个checkbox没被选中的话，则执行全选操作，否则执行反选操作
		var iCheckbox = myJ('input:checkbox.checkAble');
		var iCheckedbox = myJ(':checked.checkAble');
		var iCheckboxSize = iCheckbox.size();
		var iCheckedboxSize = iCheckedbox.size();
		
		if(iCheckboxSize>iCheckedboxSize){
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
 
 	//隐藏所有form，在需要的时候调用
 	myJ('form').hide();
 	
	myJ('input[type=button]').bind('click',function(event){
  			var buttonId= event.target.id;
  			
  			//Go to import page here.
  			if(buttonId=='importTemplate'){
				location.href='${URI}?method=goToImportExcel';
  			}
  			
  			if(buttonId=='showNewTemplate'){
				myJ('form#indicatorForm').toggle();
  			}
  			//删除所有选中的记录
  			else if(buttonId=='deleteAll'){
				var id = "";
				myJ(':checked.checkAble').each(
					function(){
						id += myJ(this).val()+",";
					}
				);
				if(id==""){
					alert("请选择执行批量删除的记录");
					return false;
				}
				if(confirm('确认删除吗?')){
					myJ('#deleteAllIds').val(id);
					myJ('#deleteAllForm').submit();
					myJ('msgDiv').text('记录删除中，请稍候').show();
				}else{
					return false;
				}
  			}else if(buttonId=='showSearchTemplate'){
  				myJ('form#indicatorSearchForm').toggle();
  			}
	});
 	//判断是否为window.open打开
 	if(window.opener){
 		var myDocument=myJ(window.opener.document);
 		
 	}
 });
 
</script>

<%@ include file="/common/footer_eoms.jsp"%>