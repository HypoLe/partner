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

<!-- 如果存在考核指标的话就显示列表，否则给出提示。 -->
<%
	List tmpList = (List)request.getAttribute("appraisalList");
	if( tmpList!=null && tmpList.size()>0){
%>
<c:set var="myCheckboxAllBtn" scope="page">
	<input type="checkbox" id="myCheckbox" />
</c:set>
<input type="button" id="showSearchTemplate" value="考核指标模板快速查询" class="btn" />
	<form action="${listURI }" method="post" id="appraisalSearchForm">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					考核模板名
				</td>
				<td>
					<input type="text" class="text" name="appraisalNameStringLike" />
				</td>
			</tr>
		<tr>
			<td class="label">
				查询开始时间：
			</td>
			<td>
				<input type="text" class="text"
					name="workTaskCreateTimeDateGreaterThan"
					onclick="popUpCalendar(this, this,null,null,null,true,-1);"
					alt="vtype:'lessThen',link:'workTaskEnd',vtext:'开始时间不能晚于结束时间'"
					id="workTaskStart" />
			</td>
			<td class="label">
				查询结束时间：
			</td>
			<td>
				<input type="text" class="text"
					name="workTaskCreateTimeDateLessThan"
					onclick="popUpCalendar(this, this);"
					alt="vtype:'moreThen',link:'workTaskStart',vtext:'结束时间不能早于开始时间'"
					id="workTaskEnd" />
			</td>
		</tr>
	</table>
		<html:submit styleClass="btn" property="method.list"
			styleId="method.list" value="提交查询结果"></html:submit>
	</form>

</hr>

	<display:table name="appraisalList" cellspacing="0" cellpadding="0"
		id="appraisalList" pagesize="${pagesize}"
		class="table appraisalList" export="false"
		requestURI="appraisal.do?method=list" sort="list" partialList="true"
		size="${size}">
		<display:column media="html" title="${myCheckboxAllBtn}">
			<input type="checkbox" class="checkAble" value="${appraisalList.id}" id="${appraisalList.id}" />
		</display:column>
		<display:column property="appraisalName"  sortable="true"
			headerClass="sortable" title="考核指标模板名" />

		<display:column sortable="true"
			headerClass="sortable" title="考核指标模板创建人" >
			<eoms:id2nameDB id="${appraisalList.createUserId}" beanId="tawSystemUserDao" />
			</display:column>
		<display:column property="createDate"  sortable="true"
			headerClass="sortable" title="考核指标模板创建时间" />
			
		<display:column title="查看" headerClass="sortable">
			<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${appraisalList.id }">
				<img src="${app}/images/icons/search.gif"/>
			</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="编辑"
			paramProperty="id" url="/partner2/appraisal.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/nop3/images/edit.gif">
		</display:column>
		<display:column sortable="false" title="删除"
				url="/partner2/appraisal.do?method=delete" paramProperty="id"
				paramId="id" media="html">
				<img src="${app }/images/icons/icon.gif"
					onclick="return(confirm('确定删除?'))" />
			</display:column>
	</display:table>
<%}else { %>
	<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-info"></span>
					当前无考核指标模板数据，点击“新增考核指标模板”按钮开始新增。
		</div>
	</div>
<%} %>

<br />
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>

<form id="deleteAllForm" action="${deleteURI }" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="id" id="deleteAllIds" />
</form>

<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%;display: none;" id="eomsUserBoxDiv">
<table class="formTable" >
		<tr>
			<td class="label">考核执行人</td>
			<td class="content">
				<input type="text" id="iUserName" name="iUserName" class="text" alt="allowBlank:false" readonly="true" value="" />
				<input type="hidden" id="iUser" name="iUser" />
			</td>
		</tr>
</table>
</div>
<br/>
<form id="evaLevelForm">
	<div id="divEva" class="ui-widget-header ui-corner-top ui-state-default">
		<table class="formTable idChangeable" id="table1">
			<tr>
				<td class="label">
					考核级次公式：
				</td>
				<td class="content" colspan="3">
					<font color="red">公式中可输入两个变量，a代表当月考核最终得分，b代表当月线路代维或者基站代维应付款,例如：(100-a)/100*b</font>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					关联考核
				</td>
				<td class="content" colspan="3">
					<input type="button" id="myExamine0" class="btn"
						onclick="addExamineLevel()" value="添加考核级次关系" />
				</td>
			</tr>
		</table>
	</div>
</form>
<br/>

<c:if test="${not empty appraialTemplateId}">
<div id="showLastTemplate">
	上月使用考核模板:	<a href="${app}/partner2/appraisal.do?method=showDetail&templateId=${appraialTemplateId}" target="_blank">
				${appraialTemplateName}</a>
	是否使用使用上月考核模板：是：<input type="checkbox" id="useLastTemplate"/>
</div>
</c:if>


<c:if test="${empty appraialTemplateId}">
<div id="noLastTemplate" style="display:none;">
	<font color="red">上月代维规模数据没有关联考核模板，请检查！</font>
</div>
</c:if>
<br/>
<div class="demo" style="width:auto;display: none;" id="progressDiv">
	<font color="red" id="submitDataFont">数据提交中...请稍等！</font>
	<br/>
	<div id="progressbar"></div>
</div>
<input type="button" id="showNewTemplate" value="新增考核指标模板" class="btn" />
<input type="button" id="importTemplate" value="导入考核指标模板" class="btn" />
<input type="button" id="deleteAll" value="批量删除" class="btn"  />
<div id="showAgreement2AppraisalButton" style="display: none;float:left;">
	<input type="button" id="proxyScale2Appraisal" value="确认关联" class="btn" name="agreement2Appraisal"/>
</div>
<form action="${URI }?method=add&type=template" method="post" id="appraisalForm">
	<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-info"></span>
					先定制考核指标模板，然后为每个考核指标模板定制考核指标
			</p>
		</div>
	</div>
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">
				考核指标模板名称
			</td>
			<td>
				<input type="text" class="text" name="appraisalName" id="appraisalName" alt="allowBlank:false,maxLength:40" />
			</td>
		</tr>
	</table>
	<br />
	<input type="submit" value="添加模板" class="button">
</form>

<eoms:xbox id="iUserName" dataUrl="${app}/xtree.do?method=userFromDept"
	rootId="1" rootText='联通公司人员' valueField="iUser"
	handler="iUserName" textField="iUserName" checktype="user" single="true">
</eoms:xbox>

<div class="templateTr" style="display: none;">
	<table class="templateTr">
		<tr>
			<td class="label">
				考核级次
			</td>
			<td class="content" colspan="3">
				<input type="text" class="firstInput" name="evaLevelParameter"/>
				<font color="red"><=</font>
				<font>得分</font>
				<font color="red" class="fullScore"><</font>
				<input type="text" class="secondInput" name="evaLevelParameter"
					onkeyup="(this.v=function(){this.value=this.value.replace(/[^0-9]+/,'');}).call(this)"
					/>
				付款计算公式：
				<input type="text" name="evaLevelParameter" class="thirdInput" style="margin-right:0px;"/><div style="float:right;"></div>
				<img src="${app}/nop3/images/icon.gif" onmousedown="deleteLevel(this);" style="cursor:pointer;"/>
			</td>
		</tr>
	</table>
</div>

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
		myJ('table#appraisalList').find('tr:gt(0)')
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
 	v = new eoms.form.Validation({form:'appraisalForm'});
 	
	myJ('input[type=button]').bind('click',function(event){
  			var buttonId= event.target.id;
  			
  			//Go to import page here.
  			if(buttonId=='importTemplate'){
				location.href='${URI}?method=goToImportExcel';
  			}
  			
  			if(buttonId=='showNewTemplate'){
				myJ('form#appraisalForm').toggle();
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
  				myJ('form#appraisalSearchForm').toggle();
  			}
	});
 	//判断是否为window.open打开
 	if(window.opener){
 		var myDocument=myJ(window.opener.document);
 		
 		myJ('form#evaLevelForm').show();
 		myJ('div#eomsUserBoxDiv').show();
 		myJ('div#noLastTemplate').show();
 		myJ('div#showAgreement2AppraisalButton').show();
 		//取消全选功能
 		myJ('input#myCheckbox').attr('disabled','disabled');
 		var _TemplateId='';
 		//如果存在选中情况则把其余所有的设置为没有选中,限制考核模板只能单选
 		myJ('input.checkAble:checkbox').unbind('click');
 		myJ('input#useLastTemplate').click(function(){
 			if(myJ(this).attr('checked')==true){
 				myJ("input.checkAble:checkbox[id!="+'${appraialTemplateId}'+"]").removeAttr('checked');
	 			myJ('input#'+'${appraialTemplateId}').attr('checked','checked');
	 			_TemplateId='${appraialTemplateId}';
	 		}else{
	 			myJ('input.checkAble:checkbox').removeAttr('checked');
 				_TemplateId='';
	 		}
 		});
 		myJ('input.checkAble:checkbox').click(function(e){
			if(myJ(this).attr('checked')==true){
				_TemplateId=e.target.value;
				myJ('input#useLastTemplate').removeAttr('checked');
				myJ("input.checkAble:checkbox[id!="+e.target.id+"]").removeAttr('checked');
			}else{
				_TemplateId='';
			}
		});
		//添加一行考核级次信息
		addExamineLevel();
 		myJ('input#proxyScale2Appraisal').click(function(){
 			myJ(this).attr('disabled','disabled');
 			//代维规模数据编辑操作打开
 			if("${operateType}"=="edit"){
 				if(_TemplateId!=''){
 				var templateName=myJ.trim(myJ("input#"+_TemplateId).parent().next().text());
 					var templateLinkHtml="<a href="+"${app}/partner2/appraisal.do?method=showDetail&templateId="+_TemplateId+" target='_blank'>"+templateName+"</a>";
 					myDocument.find("td#templateLink").html(templateLinkHtml);
 					alert("修改成功!" );
 					window.close();
 				}else{
 					alert("考核模板未修改!" );
 					myJ(this).removeAttr('disabled');
 					return false;
 				}
 			//代维规模数据新增操作打开
 			}else{
 			 	//验证考核级次信息
 				var canSubmit='true';
 				var firstScore='';
 				var secondScore='';
 				var lastScore=myJ('#divEva').find('input.secondInput').eq(-1);
 				var iUserName=myJ('input#iUserName').val();
 				var iUserId=myJ('input#iUser').val();
 				var evaSerialize=myJ('form#evaLevelForm').serialize();
		 		if(_TemplateId==''){
		 			alert('请选择需要关联的模板');
		 			myJ(this).removeAttr('disabled');
		 			return false;
		 		}
		 		if(iUserName==''){
		 			alert('请选择考核执行人！');
		 			myJ(this).removeAttr('disabled');
		 			return false;
		 		}
 				myJ('#divEva').find("input.secondInput").each(function(){
 					secondScore=myJ(this).val();
 					if(secondScore==''){
 						myJ(this).focus();
 						alert('分数不能为空，请填写！');
 						canSubmit='false';
 						myJ('input#proxyScale2Appraisal').removeAttr('disabled');
 						return false;
 					}else if(parseInt(secondScore)<= parseInt(firstScore)){
 						myJ(this).focus();
						alert("得分区间不连续，请重新填写");
						canSubmit='false';
						myJ('input#proxyScale2Appraisal').removeAttr('disabled');
						return false;
 					}
 				});
 				if(parseInt(lastScore.val()) != 100&&canSubmit=='true'){
 					lastScore.focus();
					alert('最后一项考核得分需为100,已自动设置为100');
					lastScore.val('100');
					myJ('input#proxyScale2Appraisal').removeAttr('disabled');
					return false;
 				}
 				if(canSubmit=='true'){
	 				myJ('#divEva').find("input.thirdInput").each(function(){
						var s = myJ(this).val();
						var patt1=new RegExp("a","g");
						var patt2=new RegExp("b","g");
						s=s.replace(patt1,"(22.36247003+1)");
						s=s.replace(patt2,"(52.15+0.0000001)");
						try 
						{ 	
							if(!isNaN(eval(s))&&isFinite(eval(s))){
							}
							else
							{ 	
							myJ(this).focus();
							alert('输入公式不合法，请检查！');
							canSubmit='false';
							myJ('input#proxyScale2Appraisal').removeAttr('disabled');
							return false;
							}
						} 
						catch (ex) 
						{ 
							myJ(this).focus();
							alert('输入公式不合法，请检查！');
							canSubmit='false';
							myJ('input#proxyScale2Appraisal').removeAttr('disabled');
							return false;
						} 
					});
				}
				if(canSubmit=='false'){
 					myJ(this).removeAttr('disabled');
 					return false;
 				}
		 		else{
	 				var proxyScaleId=window.opener.document.getElementById('proxyScaleId').value;
					myDocument.find('input#button_'+proxyScaleId).parent().html('<font color=red>已关联考核</font>');
					myJ('input:button').hide();
					myJ('#divEva').find("input.secondInput").attr('readonly','readonly');
					myJ('#divEva').find("input.thirdInput").attr('readonly','readonly');
					myJ('input:checkbox').attr('disabled','disabled');
					myJ('input:radio').attr('disabled','disabled');
					myJ("div#noLastTemplate").hide();
					myJ('div#progressDiv').show();
					var progess=function(v){
						return function(){
							myJ("#progressbar").progressbar({
								value: v
							});
						};
					};
					for(var i=1;i<=100;i++){
						setTimeout(progess(i),i*50);
					}
		 			myJ.ajax({
						type: "POST",
				    	url: "${app}/partner2/appraisal.do?method=saveAppraisalInstance&"+evaSerialize,
				    	data: "iUserId="+iUserId+"&proxyScaleType="+window.opener.document.getElementById("proxyScaleType").value+"&templateId="+_TemplateId+"&proxyScaleId="+proxyScaleId,
				    	success: function(msg){
				    		myJ('font#submitDataFont').html('数据提交成功！');
			  				alert("添加成功，可在详情中查看!" );
			  				window.close();
		   				}
		 			});
		 		}
	 		}
 		});
 	}
 });
 
 //添加考核级次
var gMyTr = myJ('div.templateTr').html();
function addExamineLevel(){
	myJ('#divEva').append(gMyTr);
	
	var myDiv = myJ('#divEva');
	var nextFirstInput = myDiv.find('table.templateTr').last().find('input.firstInput');
	//将每行的第一个分数设置为readonly
	nextFirstInput.attr('readonly','readonly');
	//第一行的第一个值设为0
	var firstInput = myDiv.find('table.templateTr').eq(0).find("input[type='text']").eq(0);
	firstInput.val('0');
	//下一行的第一个分数等于上一行的第二个分数
	var prevSecondInput = nextFirstInput.parents('table.templateTr').prev().find('input.secondInput');
	nextFirstInput.attr('value',prevSecondInput.val());
	
	myDiv.find("input[class='secondInput']").keyup(function(){
		if(myJ(this).val()>100){
			myJ(this).val(myJ(this).val().substring(0,2));
		}
		var _ParentTable = myJ(this).parents('table.templateTr');
		//第一行的第二个分数赋值给第二行的第一个分数
		_ParentTable.next().find("input[class='firstInput']").val(myJ(this).val());
		//分数为100时将小于改为小于等于
		if(myJ(this).val()=='100'){
			_ParentTable.find("font.fullScore").text('<=');
		}else{
			_ParentTable.find("font.fullScore").text('<');
		}
	});
	
	//为考核公式绑定keyup时间验证输入正确性
	myDiv.find("input[type='text']").last().keyup(function(){
		var s = myJ(this).val();
		var patt1=new RegExp("a","g");
		var patt2=new RegExp("b","g");
		s=s.replace(patt1,"(22.362473+1)");
		s=s.replace(patt2,"(52.15+0.0000001)");
		try 
		{ 
			if(!isNaN(eval(s))&&isFinite(eval(s)))
			{   
				myJ(this).parents('table.templateTr').find('div').html("<font color='green'>输入公式合法，可进行操作！</font>");
			}else{
				myJ(this).parents('table.templateTr').find('div').html("<font color='red'>输入公式不合法，请检查！</font>");
			}
		} 
		catch (ex) 
		{ 
			myJ(this).parents('table.templateTr').find('div').html("<font color='red'>输入公式不合法，请检查！</font>");
		} 
	});
}

//删除考核级次
function deleteLevel(childInput){
	var deleteDiv = myJ('#divEva');
	myJ(childInput).parents('table.templateTr').remove();
	var holdFirstInput = deleteDiv.find('table.templateTr').eq(0).find('input.firstInput');
	holdFirstInput.val('0');
	holdFirstInput.attr('readonly','readonly');
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>