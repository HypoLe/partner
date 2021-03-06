
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page import="com.boco.eoms.km.exam.util.KmExamSpecialtyConstants"/>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px" onclick="confirmChoice()" value="确定"/>
</c:set>
<c:set var="chooseAll">
	<input type="button" style="margin-right: 10px" onclick="chooseAll(this)" value="全部选择"/>
</c:set>
<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">
<html:form action="/kmExamRandomQuestions.do?method=searchXChoice" styleId="kmExamQuestionsForm" method="post"> 
<table align="center">
    <eoms:xbox id="tree" dataUrl="${app}/kmmanager/kmExamSpecialtys.do?method=getNodesRadioTree" 
	  	rootId="<%=KmExamSpecialtyConstants.TREE_ROOT_ID%>" 
	  	rootText='专业' 
	  	valueField="specialtyID" handler="specialtyName"
		textField="specialtyName"
		checktype="forums" single="true"		
	  ></eoms:xbox>
	  <eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=dept" 
	  	rootId="-1" 
	  	rootText='部门树' 
	  	valueField="deptId" handler="deptName"
		textField="deptName"
		checktype="dept" single="true"		
	  ></eoms:xbox>	  
  <tr >
    <td>
		<fmt:message key="kmExamQuestions.specialtyID" />
	</td>
	<td class="content">
		<input type="text"   id="specialtyName" name="specialtyName" class="text" readonly="readonly" value='<eoms:id2nameDB id="${specialtyID}" beanId="kmExamSpecialtyDao" />' alt="allowBlank:false'"/>
		<input type="hidden" id="specialtyID"   name="specialtyID" value="${specialtyID}" />
	</td>			
	<td>
	     &nbsp;&nbsp;<fmt:message key="kmExamQuestions.deptId" />
	</td>
		<td>
		    <input type="text"   id="deptName" name="deptName" class="text" readonly="readonly" value='<eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao" />' alt="allowBlank:false'"/>
			<input type="hidden" id=deptId   name="deptId" value="${deptId}" />
		</td>
	<input type="hidden" id="questionType"   name="questionType" value="${questionType}" />	
	 <td>
		 <input type="submit" class="btn" value="<fmt:message key="kmTable.query"/>"/>		
	</td>
  </tr>
</table>
<input type="hidden" name="id" value="${id}"/>
</html:form>
<content tag="heading">
	<fmt:message key="kmExamQuestions.list.heading" />
</content>

	<display:table name="kmExamQuestionsList" cellspacing="0" cellpadding="0"
		id="kmExamQuestionsList" pagesize="${pageSize}" class="table kmExamQuestionsList"
		export="false"
		requestURI="${app}/kmmanager/kmExamRandomQuestions.do?method=searchForChoice"
		 partialList="true" size="resultSize">
	<display:column  headerClass="sortable" titleKey="kmExamQuestions.questionID">
			<input type="checkbox" indexed="true"   id="checkall" name="checkall" value="${kmExamQuestionsList.questionID}"/>
	</display:column>	
	<display:column  headerClass="sortable" titleKey="kmExamQuestions.createDept">
			<eoms:id2nameDB id="${kmExamQuestionsList.createDept}" beanId="tawSystemDeptDao" />
	</display:column>
	 <display:column  headerClass="sortable" titleKey="kmExamQuestions.createUser">
		<eoms:id2nameDB id="${kmExamQuestionsList.createUser}" beanId="tawSystemUserDao" />
	</display:column>
	
	<display:column property="createTime"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="kmExamQuestions.createTime" href="${app}/kmmanager/kmExamQuestionss.do?method=edit" paramId="questionID" paramProperty="questionID"/>

	<display:column  headerClass="sortable" titleKey="kmExamQuestions.specialtyID">
		<eoms:id2nameDB id="${kmExamQuestionsList.specialtyID}" beanId="kmExamSpecialtyDao" />
	</display:column>
	 <display:column  headerClass="sortable" titleKey="kmExamQuestions.deptId">
			<eoms:id2nameDB id="${kmExamQuestionsList.deptId}" beanId="tawSystemDeptDao" />
	</display:column>
    <display:column  headerClass="sortable" titleKey="kmExamQuestions.questionType">
	   <eoms:dict key="dict-kmmanager" dictId="questionType" itemId="${kmExamQuestionsList.questionType}" beanId="id2nameXML" />			
	</display:column>
	<display:column  headerClass="sortable" titleKey="kmExamQuestions.difficulty">
	   <eoms:dict key="dict-kmmanager" dictId="difficulty" itemId="${kmExamQuestionsList.difficulty}" beanId="id2nameXML" />			
	</display:column>
	
	<display:column property="question" 
			headerClass="sortable" titleKey="kmExamQuestions.question" href="${app}/kmmanager/kmExamQuestionss.do?method=edit" paramId="questionID" paramProperty="questionID"/>

		<display:setProperty name="paging.banner.item_name" value="kmExamQuestions" />
		<display:setProperty name="paging.banner.items_name" value="kmExamQuestionss" />
	</display:table>

	<c:out value="${chooseAll}" escapeXml="false" /><c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>

<script>
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}

function saveRandomQuestions(ids)
{
	var url = "../kmmanager/kmExamRandomQuestions.do?method=save&ids="+ids+"&id=${id}";
	var httpRequest = createRequest();
	if(httpRequest){
	     httpRequest.open("POST",url,true);
	     httpRequest.onreadystatechange=function()
	     {
	     	if(httpRequest.readyState==4)
		     	if(httpRequest.status==200){
		     		window.opener.alert("添加成功！");
				}	
	     }
	     httpRequest.send(null);
	}
}
	
   function confirmChoice(){
	   var checks=document.getElementsByName('checkall');	  
		   var questionIDStr='';
		   var checkedCount=0;		   
		   for(var i =0; i<checks.length; i++){			   
			   if(checks[i].checked){	
			    if(checkedCount>0){
		          questionIDStr+=',';
		        }		       
				 questionIDStr+=checks[i].value;
				 checkedCount++;			 
			   }
		   } 
		  if(questionIDStr.length<=0){
		  	alert("请至少选择一道试题才能添加！");
		  	return;
		  }
		saveRandomQuestions(questionIDStr);
	
	   	window.close(); 
	   	window.opener.alert("添加成功！");
	   	window.opener.location.href=window.opener.location.href;   
   }
   function chooseAll(obj){
	   var checks=document.getElementsByName('checkall');
	   if(obj.value=="全部选择"){	  
		   for(var i =0; i<checks.length; i++){			   
			   checks[i].checked = true;
		   }
		   obj.value = "全部取消"; 
		}else{
		   for(var i =0; i<checks.length; i++){			   
			   checks[i].checked = false;
		   }
		   obj.value = "全部选择";
		}
   }
</script>