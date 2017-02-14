<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.personnel.webapp.form.*;"%>
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
<div align="center"><b>人员技能信息管理-人员技能信息列表</div><br><br/>
<div style="border:1px solid #98c0f4;padding:5px;background-color: #CAE8EA;cursor:pointer" onclick="openImport();"  class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle" style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" >查询</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff" >
	<form id="select_from" action="${app}/personnel/dwInfo.do?method=search" method="post">
		<table class="listTable" >
			<tr>		
				<td class="label">所属组织:</td>
				<td><input type="text" name="groupStringLike" class="text" value="${groupStringLike }"/></td>
				<td class="label">岗位:</td>
				<td><input type="text" name="dutyStringLike"  class="text" value="${dutyStringLike }"/></td>
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
	<display:table name="dwInfoList" cellspacing="0" cellpadding="0"
		id="dwInfoList" pagesize="${pageSize}" class="table"
		export="true" requestURI="${app}/personnel/dwInfo.do?method=search"
		sort="list" partialList="true" size="${resultSize }">
	    <display:column property="workername" sortable="true" headerClass="sortable" title="员工姓名"/>
	    <display:column property="group" sortable="true" headerClass="sortable" title="所属组织"/>
	    <display:column property="sysno" sortable="true" headerClass="sortable" title="系统编号"/>
	    <display:column sortable="true" headerClass="sortable" title="巡检专业">
	    			<eoms:id2nameDB id="${dwInfoList.professional}" beanId="ItawSystemDictTypeDao" />
	    </display:column>
	    <display:column  property="skilllevel" sortable="true" headerClass="sortable" title="技能等级">
	    	<eoms:id2nameDB id="${dwInfoList.skilllevel}" beanId="ItawSystemDictTypeDao" />
	    </display:column>
	    <display:column property="duty" sortable="true" headerClass="sortable" title="岗位"/>
	   	 <display:column sortable="true" headerClass="sortable" title="拥有帐号"><%--
	   		 <c:forTokens items="${dwInfoList.accountno}" delims="," var="accountno" varStatus="s2">
	   		 	<c:if test="${!empty accountno||accountno!=null}">
	    			--%><eoms:id2nameDB id="${dwInfoList.accountno}" beanId="ItawSystemDictTypeDao" /><%--
	    			<c:if test="${!s2.last}">,
	    			</c:if>
	    		</c:if>
	    	</c:forTokens>
	    --%></display:column>
	     <display:column sortable="true" headerClass="sortable" title="详细" media="html" >					 
				<a   href="${app}/personnel/dwInfo.do?method=getDetail&id=${dwInfoList.id}&personCardNo=${personCardNo}">
					<img src="${app }/images/icons/table.gif">
				</a>			
		</display:column>
	    <display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
	<br>
	<input type="button" class="btn" value="刷新" onclick="javascript:window.location.href='<%=request.getContextPath()%>/personnel/dwInfo.do?method=search&operationType=tomgr&personCardNo=${personCardNo}'"/>&emsp;&emsp;
<%@ include file="/common/footer_eoms.jsp"%>