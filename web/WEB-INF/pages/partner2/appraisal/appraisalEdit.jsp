<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<script type="text/javascript">

var myJ = jQuery.noConflict();
myJ(function() {
v = new eoms.form.Validation({form:'appraisalForm'});
	myJ('input[type=button]').bind('click',function(event){
	  			var buttonId= event.target.id;
	  			var contentEmpty = false;
	  			var mark;
	  			var check;
	  			var submitNumber = 0;
	  			if(buttonId=='saveAll'){
	  				myJ("input.text").each(function(){
   					 	if(myJ(this).val().trim()==""){
   					 		contentEmpty = true;
   					 		return false;
   					 	}
  					});
  					
  					if(contentEmpty){
  						alert("文本框内容不能为空");
  						return false;
  					}
	  				
	  				myJ("input[name=appraisalName]").each(function(){
	  					submitNumber++;
  					});
  					
  					if(submitNumber<1){
  						alert("至少需要保留一条记录");
  						return false;
  					}
	  				
	  				var score = document.getElementsByName("appraiSalScore");
	  				var totalScore = 0;
	  				for(var i=0;i<score.length;i++){
	  					totalScore += new Number(score[i].value);
	  				}
	  				if(totalScore>100){
	  				  	alert("总分数不能超过100,请修改!");
  						return false;
	  				}
	  				
	  				if(confirm("确认保存当前的"+submitNumber+"条记录的全部更改吗?")){
	  					var appraisalForm$ = myJ('form#appraisalForm');
	  						myJ('div#loadIndicator').show();
	  						myJ('div#buttonGroup').hide();
	  						myJ.ajax({
		 					  type:"POST",
							  url: appraisalForm$.attr('action'),
							  data: appraisalForm$.serialize(),
							  success: function(jsonMsg){
							  	location.href='${app}/partner2/appraisal.do?method=goToEdit&id='+'${templateId}';
								myJ('div#loadIndicator').hide();
								myJ('div#buttonGroup').show();
	  						}});
	  				}else{
	  					return false;
	  				}
	  			}else if(buttonId=='resetAll'){
	  				if(confirm("确认重置所有的数据吗?")){
		  				location.href='${app}/partner2/appraisal.do?method=goToEdit&id='+'${templateId}';
	  				}else{
	  					return false;
	  				}
	  			}else if(buttonId=='returnAll'){
	  				location.href='${app}/partner2/appraisal.do?method=list';
	  			}
	});
  });
  
function addIt(){
		var index= myJ('#indexSymbol').val();
		myJ('table#workTable tr:last').after('<tr id="tr'+(index)+'">'
			+'<td style="display: none"><input name="id"></input></td>'
			+'<td><input name="appraisalName" class="text"></input></td>'
			+'<td><input name="appraiSalScore" class="text"></input></td>'
			+'<td></td>'
			+'<td><a href="#" onclick="deleteIt(this.id)" id="href'+index+'"><img src="${app}/nop3/images/icon.gif" class="delete" /> </a></td>'
			+'</tr>'
		);
		var y= new Number(index)+1;
		myJ('#indexSymbol').val(y);
}

function deleteIt(identifier){
	var myId = 'tr#tr'+identifier.substring(4);
	myJ(myId).remove();
}

</script>

<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 快速导航
		</div>
	</div>
<div id="qucikNavRef" style="margin-bottom: 15px">
	<a class="linkDraft" href="${app }/partner2/appraisal.do?method=list">
		返回"考核模板管理"列表<font color="red">${currentWorkReportForm.title }</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"考核项编辑页面"</span>	
</div>


<!-- 用做行号的递增参数 -->
<%
	int index = 0;
%>
<form action="${URI }?method=add&type=project" method="post" id="appraisalForm">
<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 考核指标模板信息
		</div>
	</div>
<table id="sheet" class="formTable">
		<tr>
			<td class="label">
				考核指标模板名*
			</td>
			<td>
				<input type="text" class="text" name="appraisalTemplateName" 
					id="appraisalTemplateName" alt="allowBlank:false,maxLength:40" value="${appraisalTemplate.appraisalName }"/>
			</td>
		</tr>
	</table>

<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 考核指标项目信息
		</div>
	</div>
	
<input type="button" id="newRecord" value="新增一个考核项目" class="btn" onclick="addIt()" />

	<table id="workTable" class="formTable">
		<thead>
			<tr>
				<td style="display: none">
					考核项目主键
				</td>
				<td>
					考核项目名称*
				</td>
				<td>
					考核项目总分值*
				</td>
				<td>
					编辑
				</td>
				<td>
					删除
				</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="appraisal" items="${orderedAppraisalList}">
				<tr id="tr<%=index%>">
					<td style="display: none">
						<input name="id" value="${appraisal.id}"></input>
					</td>
					<td>
						<input name="appraisalName" class="text" 
							value="${appraisal.appraisalName}"></input>
					</td>
					<td>
						<input name="appraiSalScore" class="text"
							value="${appraisal.appraiSalScore}"></input>
					</td>
					<td>
						<a href="${app }/partner2/appraisal.do?method=goToEditProject&templateId=${templateId}&id=${appraisal.id}" > <img
								src="${app}/nop3/images/add.gif" class="delete" /> </a>
					</td>
					<td>
						<a href="#" onclick="deleteIt(this.id)" id="href<%=index%>"> <img
								src="${app}/nop3/images/delete.gif" class="delete" /> </a>
					</td>
				</tr>
				<%
					index += 1;
				%>
			</c:forEach>
		</tbody>
	</table>
	
	<input type="hidden" name="templateId"  value="${templateId}" />
	<div id="buttonGroup">
		<input type="button" id="saveAll" value="保存" class="btn" />
		<input type="button" id="resetAll" value="重置" class="btn" />
	</div>
	<div id="loadIndicator" class="loading-indicator" style="display:none">保存中，请等待...</div>
</form>

<div id="dialog-confirm" title="删除信息?" style="display:none">
	<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>您确定要删除此信息？</p>
</div>
<div id="dialog-message" title="删除结果" style="display:none">
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
		删除成功
	</p>
	
</div>
<input type="hidden" id="indexSymbol" value="<%=index%>" />
<%@ include file="/common/footer_eoms.jsp"%>