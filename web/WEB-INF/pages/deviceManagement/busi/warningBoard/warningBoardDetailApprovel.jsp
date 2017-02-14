<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	



<script type="text/javascript">
	Ext.onReady(function(){
        //  var v = new eoms.form.Validation({form:'warningBoardOperateForm'});
        Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
					var tabs = new Ext.TabPanel('info-page');
			tabs.addTab('content-info', '内容信息 ');
        	tabs.addTab('history-info', '审批信息 ');
        	//tabs.addTab('check-info', '验收信息 ');
    		tabs.activate(0);
	});
	
 function openSearch(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "查看处理详情";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭处理详情";
	}
}

function returnBack(){
		window.history.back();
	}
</script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
<div id="info-page">
<div id='content-info'>
		<table id="sheet" class="formTable">    
		<tr>
		<caption>
		<div class="header center" >警示牌、宣传牌信息</div>
	</caption>	
			
		</tr>
		
		
		<tr>
			<td class="label">
			 填报人 
			</td>
			<td class="content">
			${warningBoard.personnelId}				
			</td>
			<td class="label">
				填报时间
			</td>
			<td class="content">
				${warningBoard.greatTime}
			</td>
		</tr>
		
		<tr>
			
			<td class="label">
			 所属地市 
			</td>
			<td class="content">
			<eoms:id2nameDB beanId="tawSystemAreaDao" id="${warningBoard.areaId}"/>
			</td>
			<td class="label">
			 审核人 
			</td>
			<td class="content">
			<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoard.auditId}"/>
			</td>
		</tr>
		
		
		<tr>
		
			<td class="label">
				项目名称
			</td>
			<td class="content">
				${warningBoard.itemName}
			</td>
			
				<td class="label">
			增补警示牌/宣传牌中继段名称
			</td>
			<td class="content">
				${warningBoard.repeaterSection}
			</td>
		</tr>
		
		<tr>
		
			<td class="label">
				增补警示牌/宣传牌中继段里程（公里）
			</td>
			<td class="content">
				${warningBoard.repeaterSectionMileage}
			</td>
			
			<td class="label">
				中继段原有警示牌/宣传牌数量（块）
				<br>
			</td>
			<td class="content">
				${warningBoard.warningBoardOldNumber}
			</td>
		</tr>
		
		<tr>
		
			<td class="label">
				计划增补警示牌/宣传牌数量（块）	
			</td>
			<td class="content">
				${warningBoard.warningBoardOldNumber}
			</td>
			
			<td class="label">
				实际完成情况（块）
				<br>
			</td>
			<td class="content">
				${warningBoard.actualCompletion}
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 完成时间
			</td>
			<td class="content">
			${warningBoard.completionTime} 
			</td>
		</tr>
		<tr>
			<td class="label">
			 未完成原因
			</td>
			<td colspan="3">
			${warningBoard.unfinishedReason} 
			</td>
			</td>
		</tr>	
		<tr>
			<td class="label">
			 新增补警示牌/宣传牌质量评估结果
			</td>
			<td colspan="3">
			${warningBoard.assessResult} 
			</td>
			</td>
		</tr>	
		<tr>	
			<td class="label">
				备注
			</td>
			<td colspan="3">
				${warningBoard.remarks}
			</td>
		</tr>
		 		
		</table>
	
<%--	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">处理详情</span>---%>
	<c:if test="${3==(warningBoard.status)}" var="return">
<form action="warningboard.do?method=file" method="post">	
   <input type="hidden" name="id" value="${warningBoard.id}" />	
   
   <html:submit styleClass="btn"  property="method.save" 
        styleId="method.save" value="归档"  ></html:submit>
			
</form>		
</c:if>		


    <c:if test="${type eq '2' }" var="return"> 	
	<tr>
		<caption>
		<div class="header left">请填写审批意见 </div>
	</caption>	
			
		</tr>		
		
					
    <form  action="${app}/deviceManagement/warningboard/warningboardoperate.do?method=deal" method="post" id="warningBoardOperateForm" name="warningBoardOperateForm">
    <table id="sheet" class="formTable">

    <tr>
	<td class="label">
					审批结果*
	</td>
		<td class="content">
				<select id="operateResult" name="operateResult"	>
						<option value='3' selected="selected">
							审批通过
						</option>
						<option value='5'>
							驳回
						</option>
					</select>
				</td>
			</tr>

    <tr>
      <td class="label">
					审批意见*
				</td>
				<td colspan="3">
					<textarea id="operateOpinion" class="textarea max" name="operateOpinion"
						alt="width:500,allowBlank:false"></textarea>
				</td>
			</tr>
       <tr>
				<td class="label">
					备注
				</td>
				<td colspan="3">
					<textarea id="operateRemark" class="textarea max" name="operateRemark"
						alt="width:500,allowBlank:true"></textarea>
				</td>
             </tr>

    
  	
	<input type="hidden" name="id" value="${warningBoard.id}" />
	<input type="hidden" name="status" value="${warningBoard.status}" />
	<input type="hidden"  name="auditId" value="${warningBoard.auditId}" />
	<input type="hidden"  name="personnelId" value="${warningBoard.personnelId}" />
	
	</table>
	

  <html:submit styleClass="btn"  property="method.save"  
	        styleId="method.save" value="确定"  ></html:submit>	 		
  <input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>

</form>		
    </c:if>
	
</div>	
<div 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">	
<div id="history-info" >
		<logic:present name="warningBoardOperateList" scope="request">
	<display:table name="warningBoardOperateList" cellspacing="0" cellpadding="0"
		id="warningBoardOperateList" 
		class="table warningBoardOperateList"  sort="list"
		 >
		
	
		
		
		<display:column headerClass="sortable" title="审核人">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoardOperateList.operateUserId}"/>
		</display:column>
		<display:column  headerClass="sortable" title="审核时间">
			${warningBoardOperateList.operateTime}
		</display:column>
	
		<display:column  headerClass="sortable" title="审核结果">
			
				<c:if test="${3==(warningBoardOperateList.operateResult)}" >审核通过</c:if>	
			<c:if test="${5==(warningBoardOperateList.operateResult)}" >审核不通过</c:if>
			
		</display:column>
		<display:column  headerClass="sortable" title="派往对象">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoardOperateList.operateTarget}"/>
		</display:column>
		<display:column  headerClass="sortable" title="审核意见">
			${warningBoardOperateList.operateOpinion}
		</display:column>
		<display:column  headerClass="sortable" title="备注">
			${warningBoardOperateList.operateRemark}
		</display:column>
	</display:table>
</div>		
</logic:present>
</div>		
</div>

