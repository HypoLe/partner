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
<div align="center"><b>人员教育经历管理-人员教育经历列表</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form id="select_from" action="${app}/personnel/studyExperience.do?method=search" method="post">
		<table class="listTable" >
			<tr>		
				<td class="label">毕业院校</td>
				<td><input type="text" name="universityStringLike" class="text" value="${universityStringLike }"/></td>
				<td class="label">所学专业</td>
				<td><input type="text" name="professionalStringLike" class="text"  value="${professionalStringLike }"/></td>
			</tr>
			<tr>
			<td class="label">所获学历</td>
			<td colspan="3"  >		
				<eoms:comboBox name="degreeStringEqual" styleClass="input select"	id="testParent"  defaultValue="${degreeStringEqual }"
					initDicId="12405" alt="allowBlank:true"  />
			</td>
			</tr>
			</table>
			<table >
				<tr>
					<td>
						<input type="submit" class="btn" value="查询" />
						<input type="button"    class="btn" id="reset" value="重置" onclick="res();">
					   <input type="hidden" value="tomgr" name = "operationType"/>
					     <input type="hidden" value="${personCardNo}" name = "personCardNo"/>
					
					</td>
				</tr>
			</table>
	</form>	
</div>
	<display:table name="studyExperienceList" cellspacing="0" cellpadding="0"
		id="studyExperienceList" pagesize="${pageSize}" class="table"
		export="true" requestURI="${app}/personnel/studyExperience.do?method=search"
		sort="list" partialList="true" size="${resultSize }">
	    <display:column property="workername" sortable="true" headerClass="sortable" title="员工姓名"/>
	    <display:column property="sysno" sortable="true" headerClass="sortable" title="系统编号"/>
	    <display:column property="intime" sortable="true" headerClass="sortable" title="入学时间"/>
	    <display:column property="leavetime" sortable="true" headerClass="sortable" title="毕业时间"/>
	    <display:column property="university" sortable="true" headerClass="sortable" title="毕业院校"/>
	    <display:column property="professional" sortable="true" headerClass="sortable" title="所学专业"/>
	    <display:column  sortable="true" headerClass="sortable" title="所获学历">
	    	<eoms:id2nameDB id="${studyExperienceList.degree}" beanId="ItawSystemDictTypeDao" />
	    </display:column>
	    <display:column property="code" sortable="true" headerClass="sortable" title="学历证书编号" maxLength="10"/><%--
	    <display:column sortable="true" headerClass="sortable" title="学历证书扫描件" 
	    				media="html">
	    	<eoms:download ids="${studyExperienceList.imagepath }"></eoms:download>
	    </display:column>
	     --%><display:column sortable="true" headerClass="sortable" title="详细" media="html" >					 
				<a   href="${app}/personnel/studyExperience.do?method=getDetail&id=${studyExperienceList.id}&personCardNo=${personCardNo}">
					<img src="${app }/images/icons/table.gif">
				</a>			
 		</display:column>
	    <display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	<br>
<input type="button" class="btn" value="刷新" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/studyExperience.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>&emsp;&emsp;
<%@ include file="/common/footer_eoms.jsp"%>