<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.personnel.webapp.form.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
<!--
	jQuery.noConflict(); 
	jQuery(function($){  
		$("#reset").click(function(){
			$("#select_from input[type='text']").each(function(){
				$(this).val(null);
			})
		})
	}); 
//-->
</script>
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
function res(){
		var formElement=document.getElementById("select_from")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
	              inputs[i].value = '';
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
	}
</script>
<div align="center"><b>人员证书信息管理-人员证书信息列表</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form id="select_from" action="${app}/personnel/certificate.do?method=search" method="post">
		<table class="listTable" >
			<tr>		
				<td class="label">证书类别:</td>
					<td><input type="text" name="typeStringLike" value="${typeStringLike}" class="text"/>
				</td>
				<td class="label">有效期</td>
					<td>
					<input type="text"  id="validityStringGreaterOrEqual" name="validityStringGreaterOrEqual" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true"  value="${validityStringGreaterOrEqual}"/>
					<span>至</span>
					<input type="text"  id="validityStringLessOrEqual" name="validityStringLessOrEqual" class="text medium" onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii:ss',null,null,false,-1);"  readonly="true"   value="${validityStringLessOrEqual}"/>
					</td>
			</tr>
		</table>
		<table >
			<tr>
				<td>
					<input type="submit" class="btn" value="查询" />
					<input type="button"   class="btn" id="reset" value="重置" onclick="res();">
					 <input type="hidden" value="tomgr" name = "operationType"/>
					  <input type="hidden" value="${personCardNo}" name = "personCardNo"/>
				</td>
			</tr>
		</table>
	</form>	
</div>
	<br/><br/>
	<display:table name="certificateList" cellspacing="0" cellpadding="0"
		id="certificateList" pagesize="${pageSize}" class="table"
		export="true" requestURI="${app}/personnel/certificate.do?method=search"
		sort="list" partialList="true" size="${resultSize }" >
	    <display:column property="workername" sortable="true" headerClass="sortable" title="员工姓名"/>
	    <display:column property="sysno" sortable="true" headerClass="sortable" title="系统编号"/>
	    <display:column property="type" sortable="true" headerClass="sortable" title="类别"/>
	    <display:column property="level" sortable="true" headerClass="sortable" title="等级"/>
	    <display:column property="issueorg" sortable="true" headerClass="sortable" title="发证机关"/>
	    <display:column property="issuetime" sortable="true" headerClass="sortable" title="颁发时间"/>
	    <display:column property="validity" sortable="true" headerClass="sortable" title="有效期"/>
	    <display:column property="codeno" sortable="true" headerClass="sortable" title="编号"/>
	 	<display:column sortable="true" headerClass="sortable" title="详细" media="html" >					 
				<a   href="${app}/personnel/certificate.do?method=getDetail&id=${certificateList.id}&personCardNo=${personCardNo}">
					<img src="${app }/images/icons/table.gif">
				</a>			
		 </display:column>
	    <display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	<br>
	<input type="button" class="btn" value="刷新" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/certificate.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>
<%@ include file="/common/footer_eoms.jsp"%>