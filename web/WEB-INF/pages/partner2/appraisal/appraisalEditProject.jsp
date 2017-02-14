<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<script type="text/javascript"><!--
var myJ = jQuery.noConflict();
myJ(function() {

	//计算当前页面总分，填入当前分值的span，计算开始
	var totalScore = 0;
	myJ('input.score').each(function(){
		totalScore += new Number(myJ(this).val());
	});
	myJ('span#totalScore').text(totalScore);
	//计算结束

v = new eoms.form.Validation({form:'appraisalForm'});
	myJ('input[type=button]').bind('click',function(event){
	  			var buttonId= event.target.id;
	  			var contentEmpty = false;
	  			var mark;
	  			var check;
	  			var submitNumber = 0;
	  			if(buttonId=='saveAll'){
	  				myJ("input[name!=deductionStandard].text").each(function(){
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

	  				if(new Number(myJ('span#totalScore').text())>new Number('${appraisalProject.appraiSalScore }')){
	  					alert("不能超过考核大项目总分值,请修改!");
	  					return false;
	  				}
	  				
	  				if(confirm("确认保存当前的"+submitNumber+"条记录的全部更改吗?")){
	  					var appraisalForm$ = myJ('form#appraisalForm');
	  						myJ('div#loadIndicator').show();
	  						myJ('div#buttonGroup').hide();
	  						myJ('input.btn').hide();
	  						myJ.ajax({
		 					  type:"POST",
							  url: appraisalForm$.attr('action'),
							  data: appraisalForm$.serialize(),
							  success: function(jsonMsg){
							  	location.href='${app}/partner2/appraisal.do?method=goToEditProject&templateId=${templateId}&id=${projectId}';
								myJ('div#loadIndicator').hide();
								myJ('div#buttonGroup').show();
	  						}});
	  				}else{
	  					return false;
	  				}
	  			}else if(buttonId=='resetAll'){
	  				if(confirm("确认重置所有的数据吗?")){
		  				location.href='${app}/partner2/appraisal.do?method=goToEditProject&templateId=${templateId}&id=${projectId}';
	  				}else{
	  					return false;
	  				}
	  			}else if(buttonId=='returnAll'){
	  				location.href='${app}/partner2/appraisal.do?method=goToEdit&id=${templateId}';
	  			}
	});
  });
  
function openInspectionWindow(hrefDomObject){
	//This id is <tr> id.
	var trId = myJ(hrefDomObject).parent().parent().attr('id');
	var inspectionUrl = '${app}/inspectionplan/inspectionPlanMonth.do?method=list&trId='+trId;
	window.open(inspectionUrl, 'newInspectionWindow', 'height=800, width=1000, top=200, left=200,toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no');
}

function resetInspection(hrefDomObject){
	myJ(hrefDomObject).parent().parent().find('input#inspectionId').val("");
	myJ(hrefDomObject).parent().parent().find('input#inspectionName').val("");
	//!!!!!!!!!!!!Note this cause dom changed, place it last please.
	myJ(hrefDomObject).parent().parent().find('td:eq(4)').html('<a href="#" onclick="openInspectionWindow(this)"><img src="${app}/nop3/images/add.gif" /> </a>');
}
  
function addIt(){//
		var index= myJ('#indexSymbol').val();
		myJ('table#workTable tr:last').after('<tr id="tr'+(index)+'">'
			+'<td style="display: none"><input name="id"></input></td>'
			+'<td><input name="appraisalName" class="text"></input></td>'
			+'<td><input name="appraiSalScore" class="text score" onkeyup="calcScore()" onblur="calcScore()"></input></td>'
			+'<td><input name="deductionStandard" class="text"></input></td>'
			+'<td><select name="autoCalculate"><option value="否" selected>否</option><option value="是" >是</option></select></td>'								
			+'<td><a href="#" onclick="deleteIt(this.id)" id="href'+index+'"><img src="${app}/nop3/images/icon.gif" class="delete"/> </a></td>'
			+'<td style="display: none"> <input id="inspectionId" name="inspectionId" value="" type="hidden"></input></td>'
			+'<td style="display: none"> <input id="inspectionName" name="inspectionName" value="" type="hidden"></input></td>'
			+'</tr>'
		);
		var y= new Number(index)+1;
		myJ('#indexSymbol').val(y);
}

function deleteIt(identifier){
	var myId = 'tr#tr'+identifier.substring(4);
	myJ(myId).remove();
	calcScore();
}

function calcScore(identifier){
	var totalScore = 0;
	myJ('input.score').each(function(){
		totalScore += new Number(myJ(this).val());
	});
	myJ('span#totalScore').text(totalScore);
}
function setOptionSelected(selectObjName,index,toEqualOptionValue){
		var autoCalculate = document.getElementsByName(selectObjName);
		for(var i=0;i<autoCalculate[index].options.length;i++){
			if(autoCalculate[index].options[i].value==toEqualOptionValue){
				autoCalculate[index].options[i].selected = true;
			}
		}
}
--></script>
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
		返回"考核模板管理"列表
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<a class="linkDraft" href="${app }/partner2/appraisal.do?method=goToEdit&id=${templateId}">
		返回考核模板<span style="color: red">"${appraisalTemplate.appraisalName }"</span>考核项列表
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	考核项<span style="color: red">"${appraisalProject.appraisalName }"</span>实体编辑页面
</div>

<!-- 用做行号的递增参数 -->
<%
	int index = 0;
%>
<form action="${URI}?method=add&type=object" method="post" id="appraisalForm">
<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 考核指标项目信息
		</div>
	</div>
<table id="sheet" class="formTable">
		<tr >
			<td rowspan="2" class="label">
				考核项目名称*
			</td>
			<td rowspan="2">
				<input type="text" class="text" name="appraisalProjectName" 
					id="appraisalTemplateName" alt="allowBlank:false,maxLength:40" value="${appraisalProject.appraisalName }"/>
			</td>
			<td class="label">
				考核项目总分值*
			</td>
			<td>
				<input type="text" class="text" name="appraiSalProjectScore" 
					id="appraisalTemplateName" alt="allowBlank:false,maxLength:40" value="${appraisalProject.appraiSalScore }"/>
			</td>
		</tr>
		<tr >
			<td class="label">
				当前考核项目总分值:
			</td>
			<td>
				<span id="totalScore"></span>
			</td>
		</tr>
	</table>

<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 考核指标实体信息
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
					考核实体名称*
				</td>
				<td>
					考核实体分值*
				</td>
				<td>
					考核实体扣分标准
				</td>

				<td>
					是否自动计分
				</td>
				<td>
					删除
				</td>
				<td style="display: none">
					工作计划id
				</td>
				<td style="display: none">
					工作计划名
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
						<input name="appraiSalScore" class="text score"
							value="${appraisal.appraiSalScore}" onkeyup="calcScore()" onblur="calcScore()"></input>
					</td>
					<td>
						<input name="deductionStandard" class="text"
							value="${appraisal.deductionStandard}"></input>
					</td>
					<td>
						<select name="autoCalculate">
							<option value="no" selected>否</option>
							<!--
							<option value="yes" >是</option>
							-->
						</select>
						<script>setOptionSelected('autoCalculate','<%=index%>','${appraisal.autoCalculate}')</script>
					</td>
					<td>
						<a href="#" onclick="deleteIt(this.id)" id="href<%=index%>"> <img
								src="${app}/nop3/images/delete.gif" class="delete" /> </a>
					</td>
					<td style="display: none">
						<input id='inspectionId' name="inspectionId" value="${appraisal.inspectionId }" type="hidden"></input>
					</td>
					<td style="display: none">
						<input id='inspectionName' name="inspectionName" value="${appraisal.inspectionName }" type="hidden"></input>
					</td>
				</tr>
				<%
					index += 1;
				%>
			</c:forEach>
		</tbody>
	</table>
	<input type="hidden" name="projectId"  value="${projectId}" />
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