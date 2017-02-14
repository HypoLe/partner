<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

	function openImport(){
		var handler = document.getElementById("openQuery");
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}

</script>


<table class="formTable">
	<caption>
		<div class="header center">代维质量报告列表</div>
	</caption>
</table>	
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form action="${app}/partner/quality/qualityAction.do?method=search" id="pnrRecordForm"	method="post">
		<table id="sheet" class="listTable">
	<tr>
	    <td class="label">
	       标题
	    </td>
	    <td colspan="3">
	       <input  type="text" class="text" name="title" id="title" style="width: 90%"
					value=""/>
	    </td>
	</tr>		

	<tr>
	    <td class="label">
	       报告类型
	    </td>
	    <td class="content">
           <select name="type" id="type" onchange="chooseTime();" alt="allowBlank:false,vtext:'请选择周期'">
				<option value="">
					--请选择周期--
				</option>
				<option value="week">周</option>
				<option value="month">月</option>
				<option value="quarter">季度</option>
				<option value="year">年</option>				
			</select>	
	    </td>
		<tr>
				<td colspan='4' class='label'>
					<input type="submit" value="查询">
					<input type="reset" value="重置">
				</td>
		</tr>
	</table>
	</form>
</div>
	<display:table name="pnrQualityMainList" cellspacing="0" cellpadding="0"
		id="pnrQualityMainList" pagesize="${pageSize}" class="table pnrQualityMainList"
		export="false"
		requestURI="../quality/qualityAction.do?method=search"
		sort="list" partialList="true" size="resultSize">
		
	<display:column property="title" sortable="true"
			headerClass="sortable" title="标题"  paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" title="发布人" >
		<eoms:id2nameDB id="${pnrQualityMainList.publishUser}" beanId="tawSystemUserDao" />
	</display:column>	

	<display:column sortable="true" headerClass="sortable" title="发布部门" >
		<eoms:id2nameDB id="${pnrQualityMainList.publishDept}" beanId="tawSystemDeptDao" />
	</display:column>	
	<display:column sortable="true" headerClass="sortable" title="审批状态" >
		<c:if test="${pnrQualityMainList.state == '4'}">
		  审核通过
		</c:if>
		<c:if test="${pnrQualityMainList.state == '2'}">
		  上报待审批
		</c:if>
		<c:if test="${pnrQualityMainList.state == '1'}">
		  保存待上报
		</c:if>
		<c:if test="${pnrQualityMainList.state == '3'}">
		  已驳回
		</c:if>
	</display:column>		
		
    <display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='../quality/qualityAction.do?method=detail&id=${pnrQualityMainList.id}' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
	</display:table>	
	
<%@ include file="/common/footer_eoms.jsp"%>