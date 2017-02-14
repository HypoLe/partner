<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<script type="text/javascript">

var myJ = jQuery.noConflict();
myJ(function() {
v = new eoms.form.Validation({form:'indicatorForm'});
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
	  				
	  				if(confirm("确认保存当前的更改吗?")){
	  					var indicatorForm$ = myJ('form#indicatorForm');
	  						myJ('div#loadIndicator').show();
	  						myJ('div#buttonGroup').hide();
	  						myJ.ajax({
		 					  type:"POST",
							  url: indicatorForm$.attr('action'),
							  data: indicatorForm$.serialize(),
							  success: function(jsonMsg){
							  	location.href='${app}/partner2/indicator.do?method=goToEdit&id='+'${indicator.id}';
								myJ('div#loadIndicator').hide();
								myJ('div#buttonGroup').show();
	  						}});
	  				}else{
	  					return false;
	  				}
	  			}else if(buttonId=='resetAll'){
	  				if(confirm("确认重置所有的数据吗?")){
		  				location.href='${app}/partner2/indicator.do?method=goToEdit&id='+'${indicator.id}';
	  				}else{
	  					return false;
	  				}
	  			}else if(buttonId=='returnAll'){
	  				location.href='${app}/partner2/indicator.do?method=list';
	  			}
	});
  });
  

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
	<a class="linkDraft" href="${app }/partner2/indicator.do?method=list">
		返回列表<font color="red">${currentWorkReportForm.title }</font>
	</a>
	<input type="image" src="${app }/nop3/images/arrow_right.png">
	<span>"编辑页面"</span>	
</div>


<form action="${URI }?method=add&id=${indicator.id}" method="post" id="indicatorForm">
<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> 考核指标模板信息
		</div>
	</div>

<div class="ui-widget">

	<table id="workTable" class="formTable">
		<thead>
			<tr>
				<td style="display: none">
					考核项目主键
				</td>
				<td>
					考核指标名称*
				</td>
				<td>
					状态*
				</td>
				<td>
					类型*
				</td>
				<td>
					脚本执行方式*
				</td>
				<td>
					执行脚本*
				</td>
			</tr>
		</thead>
		<tbody>
				<tr>
					<td style="display: none">
						<input name="id" value="${indicator.id}"></input>
					</td>
					<td>
						<input name="appraisalName" class="text" 
							value="${indicator.appraisalName}"></input>
					</td>
					<td>
						<input name="status" class="text"
							value="${indicator.status}"></input>
					</td>
					<td>
						<input name="execType" class="text"
							value="${indicator.execType}"></input>
					</td>
					<td>
						<input name="execCategory" class="text"
							value="${indicator.execCategory}"></input>
					</td>
					<td>
						<input name="scriptFile" class="text"
							value="${indicator.scriptFile}"></input>
					</td>
				</tr>
		</tbody>
	</table>
	
	<input type="hidden" name="id"  value="${id}" />
	<div id="buttonGroup">
		<input type="button" id="saveAll" value="保存" class="btn" />
		<input type="button" id="resetAll" value="重置" class="btn" />
	</div>
	
</div>

<%@ include file="/common/footer_eoms.jsp"%>