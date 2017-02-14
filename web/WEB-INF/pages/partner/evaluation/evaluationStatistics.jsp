<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
    	Ext.onReady(function(){
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
	
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${sessionScope.sessionform.rootAreaId}',
		treeRootText:'${sessionScope.sessionform.rootAreaName}',
		treeChkMode:'',treeChkType:'',single:true,
		showChkFldId:'areaName',saveChkFldId:'areaId',returnJSON:false
	});

			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}
    
	


	
</script>





<form action="${app}/partner/evaluation/theTree.do?method=evaluationStatistics" method="post" >

<fieldset><legend>请输入查询条件</legend>
<table class="formTable">
<tr>
	
				<td class="label">
			所属地市
		</td>
		 <td>
            <input class="text" type="text"  id="areaName" readonly="true"        
                   alt="allowBlank:false" />
		<input type="button" name="areatree" id="areatree" value="选择地域" class="btn" />				 
		<input type="hidden" name="areaId" id="areaId"  /> 			 
  		</td>
      <td class="label">被考核单位</td>
			<td class="content">
				<input type="text" id="evaluationTarget" name="evaluationTarget" class="text"  readonly="true" />
				<input type="hidden" id="monitorCompany" name="monitorCompany" />
			</td>	
	
			</tr>
		<tr>
			<td class="label">考核年度</td>
			<td class="content"><select size='1'
				name='year' id='year'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011' >2011年</option>
				<option value='2012' selected="selected">2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">考核月度</td>
			<td class="content"><select size='1' name="month"
				id="month" class='select' >
				<option value="">请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>

			<input type="hidden" id="evalType" name="evalType" value="${evalType}" />	
</table>
</fieldset>
	<input type="submit" value="查询" />
</form>


<eoms:xbox id="evaluationTarget" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="" rootText='代维公司' valueField="monitorCompany"
	handler="evaluationTarget" textField="evaluationTarget" checktype="dept"
	single="true"></eoms:xbox>

<logic:present name="evaluationEntityList" scope="request">
<display:table name="evaluationEntityList" cellspacing="0" cellpadding="0"
		id="evaluationEntityList" pagesize="${pagesize}"
		class="table" export="false"
	    sort="list" partialList="true"
		size="${size}">
		<display:column property="year"  
			headerClass="sortable" title="考核年份" />
		<display:column property="month"  
			headerClass="sortable" title="考核月份" />
		<display:column 
			headerClass="sortable" title="考核模板">
			<a href="${app}/partner/evaluation/evaluation.do?method=templateOnlyView&nodeId=${evaluationEntityList.usedTemplateId}" target="_blank">
				${evaluationEntityList.usedTemplateName}
			</a>
		</display:column>
		<display:column  headerClass="sortable" title="被考核单位">
			<eoms:id2nameDB id="${evaluationEntityList.evaluationTarget}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column  headerClass="sortable" title="考核发起人">
		<eoms:id2nameDB id="${evaluationEntityList.initate}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column  headerClass="sortable" title="考核执行人">
		<eoms:id2nameDB id="${evaluationEntityList.executor}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column property="score"  
			headerClass="sortable" title="得分" />
		<display:column 
			headerClass="sortable" title="查看详情">
			<a href="${app}/partner/evaluation/evaluationEntity.do?method=evaluationConfirmForm&id=${evaluationEntityList.id}&confirmPersonnel=${confirmPersonnel}">打分详情</a>
		</display:column>
</display:table>
</logic:present>






<%@ include file="/common/footer_eoms.jsp"%>