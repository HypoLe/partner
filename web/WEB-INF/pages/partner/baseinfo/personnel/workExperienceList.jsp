<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8" %>
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
<div align="center"><b>人员工作经历管理-人员工作经历列表</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form id="select_from" action="${app}/personnel/workExperience.do?method=search" method="post">
		<table class="listTable" >
			<tr>
			<td class="label">工作单位</td>
			<td colspan="3"  >		
				<input  name="companyStringLike" class="text"	value="${companyStringLike }" />
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
	<display:table name="workExperienceList" cellspacing="0" cellpadding="0"
		id="workExperienceList" pagesize="${pageSize}" class="table"
		export="true" requestURI="${app}/personnel/workExperience.do?method=search"
		sort="list" partialList="true" size="${resultSize }">
	    <display:column property="workername" sortable="true" headerClass="sortable" title="员工姓名"/>
	    <display:column property="workerid" sortable="true" headerClass="sortable" title="员工ID"/>
	    <display:column property="sysno" sortable="true" headerClass="sortable" title="系统编号"/>
	    <display:column property="entryTime" sortable="true" headerClass="sortable" title="入职时间"/>
	    <display:column property="leaveTime" sortable="true" headerClass="sortable" title="离职时间"/>
	    <display:column property="company" sortable="true" headerClass="sortable" title="工作单位"/>
	    <display:column property="duty" sortable="true" headerClass="sortable" title="工作职务"/>
	    <display:column sortable="true" headerClass="sortable" title="详细" media="html" >					 
				<a   href="${app}/personnel/workExperience.do?method=getDetail&id=${workExperienceList.id}&personCardNo=${personCardNo}">
					<img src="${app }/images/icons/table.gif">
				</a>			
		 </display:column>
	    <display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	<br>
	<input type="button" class="btn" value="刷新" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/workExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>&emsp;&emsp;
<%@ include file="/common/footer_eoms.jsp"%>