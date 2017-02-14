<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.personnel.webapp.form.*;"%>
	<script type="text/javascript">
		<!--
		  var checkflag = "false";
			function chooseAll(){	
			   var objs = document.getElementsByName("checkbox11");    
			    if(checkflag=="false"){
			        for(var i=0; i<objs.length; i++){
			           objs[i].checked="checked";
			        } 
			        checkflag="checked";
			    }
			    else if(checkflag=="checked"){ 	    	    
				    for(var i=0; i<objs.length; i++){
				           objs[i].checked=false;
				    } 
				    checkflag="false";
			    }
			}
			function add(){
				location.href="${app}/personnel/certificate.do?method=getjsp&operationType=toadd&workerid=${workerid}&workername=${workername}";
			}
			function del(){
			var string="";
			 var objName= document.getElementsByName("checkbox11");
			        for (var i = 0; i<objName.length; i++){
			                if (objName[i].checked==true){ 
			                string+=objName[i].value;   
			                string+="|";
			                }
			        }  
			        if(confirm("确认要删除吗？")){
			        	if(string == null || "" ==  string){
			        		alert("请选择要删除的证书信息");
			        		return false;
			        	}
					 	location.href="${app}/personnel/certificate.do?method=remove&selectids="+string;
					 }else{
					 return false;
					 }
			}
		//-->
		</script>
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
	<c:if test="${empty workerid}">
	<form id="select_from" action="${app}/personnel/certificate.do?method=search" method="post">
		<table class="formTable" >
			<tr>		
				<td class="label">证书类别:</td>
				<td><input type="text" name="typeStringLike" value="${typeStringLike }"/></td>
				
				<td class="label">有效期</td>
				<td><input type="text" name="validityStringEqual" value="${validityStringEqual }"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"  readonly="true" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="submit" class="btn" value="查询" />
					<input id="reset" type="button" class="btn" value="重置" />
					<input type="hidden" value="tomgr" name = "operationType"/>
				</td>
			</tr>
		</table>
	</form>	
	<br/><br/>
	</c:if>
	<display:table name="certificateList" cellspacing="0" cellpadding="0"
		id="certificateList" pagesize="${pageSize}" class="table"
		export="false" requestURI="${app}/personnel/certificate.do?method=search"
		sort="list" partialList="true" size="${resultSize }" >
		
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" media="html">
	         <input type="checkbox" name="checkbox11" value="<c:out value='${certificateList.id}'/>" >
	    </display:column>
	    <c:if test="${empty workerid}">
	  	  <display:column property="workername" sortable="true" headerClass="sortable" title="员工姓名"/>
	    </c:if>
	    <display:column property="type" sortable="true" headerClass="sortable" title="类别"/>
	    <display:column property="level" sortable="true" headerClass="sortable" title="等级"/>
	    <display:column property="issueorg" sortable="true" headerClass="sortable" title="发证机关"/>
	    <display:column property="issuetime" sortable="true" headerClass="sortable" title="颁发时间"/>
	    <display:column property="validity" sortable="true" headerClass="sortable" title="有效期"/>
	    <display:column property="codeno" sortable="true" headerClass="sortable" title="编号"/>
	    <display:column property="memo" sortable="true" headerClass="sortable" title="备注"/>
	    <display:column sortable="true" headerClass="sortable" title="修改" media="html" >					 
		<a id="${certificateList.id}"
		   href="${app}/personnel/certificate.do?method=getjsp&operationType=toedit&id=${certificateList.id}&workerid=${workerid}&workername=${workername}">
			<img src="${app }/images/icons/edit.gif">
		</a>			
	 </display:column>
	 
	 <display:column sortable="true" headerClass="sortable" title="详细" media="html" >					 
				<a   href="${app}/personnel/certificate.do?method=getDetail&id=${certificateList.id}">
					<img src="${app }/images/icons/table.gif">
				</a>			
	 </display:column>
	 
	</display:table>
	<br><br>
		<input class="button" type="button" onclick="add()" value="添加"/>&emsp;&emsp;
		<c:if test="${!empty certificateList}">
			<input class="button" type="button" onclick="del()" value="删除"/>
		</c:if>
<%@ include file="/common/footer_eoms.jsp"%>