<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   

<% 
String flagValue=(String)request.getAttribute("flagValue");
String contentIDStr=(String)request.getAttribute("contentIDStr");
String scoreStr=(String)request.getAttribute("scoreStr");
String[] contentIDArr=contentIDStr.split(",");
String[] scoreArr=scoreStr.split(",");
int i=0;
int count=new Integer(((String)request.getAttribute("count"))).intValue();
int flag=count;
if(flagValue.equals("0")){
%>

<table id="table${questionType}<%=count%>">
			<c:forEach items="${kmExamQuestionsList}" var="item" varStatus="status">			   
				<tr id="rowId<%=count%>" style="width:100%;">
					<td style="width:65%;">${item.question}</td>
					<td  style="width:10%;">
						 <eoms:dict key="dict-kmmanager" dictId="questionType" itemId="${item.questionType}" beanId="id2nameXML" />
					</td>
					<td style="width:10%;">
						<eoms:dict key="dict-kmmanager" dictId="difficulty" itemId="${item.difficulty}" beanId="id2nameXML" />
					</td>			
					<input type="hidden" name="questionID${questionType}<%=count%>" id="questionID${questionType}<%=count%>" value="${item.questionID}">
					<input type="hidden" name="contentID${questionType}<%=count%>" id="contentID${questionType}<%=count%>" value="<%=contentIDArr[i]%>">
					<td style="width:10%;"><input type="text" name="score${questionType}<%=count%>" value="<%=scoreArr[i]%>" class="text medium" style="width:30px" size="2" alt="allowBlank:false,vtext:'请设置分数'"> 分</td>	
					<c:if test="${isPublic=='0'}">			
					<td style="width:10%;"><input type="button" name="del${questionType}<%=count%>" class="btn" value="删除" onclick="DeleteChoiceDB${questionType}<%=flag%>('rowId<%=count%>','<%=contentIDArr[i]%>',this)"></td>
					</c:if>
					<% count++;i++; %>
				</tr>
				</c:forEach>
</table>

<%
}else if(flagValue.equals("1")){ 
%>

<table id="table${questionType}<%=count%>">
			<c:forEach items="${kmExamQuestionsList}" var="item" varStatus="status">			   
				<tr id="rowId<%=count%>" style="width:100%;">	
					<td style="width:65%;">${item.question}</td>
					<td style="width:10%;">
						 <eoms:dict key="dict-kmmanager" dictId="questionType" itemId="${item.questionType}" beanId="id2nameXML" />
					</td>
					<td style="width:10%;">
						<eoms:dict key="dict-kmmanager" dictId="difficulty" itemId="${item.difficulty}" beanId="id2nameXML" />
					</td>				
					<input type="hidden" name="questionID${questionType}<%=count%>" id="questionID${questionType}<%=count%>" value="${item.questionID}">
					<input type="hidden" name="contentID${questionType}<%=count%>" id="contentID${questionType}<%=count%>" value="">				
					<td style="width:10%;"><input type="text" name="score${questionType}<%=count%>" class="text medium" style="width:30px" size="2" alt="allowBlank:false,vtext:'请设置分数'"> 分</td>	
					<td style="width:10%;"><input type="button" name="del${questionType}<%=count%>" class="btn" value="删除" onclick="DeleteChoice${questionType}<%=flag%>('rowId<%=count%>',this)"></td>
					<% count++; %>
				</tr>
				</c:forEach>
</table>

<%} %>
			
	<script>
		t_rownum${questionType}=<%=count%>;
		
		function DeleteChoice${questionType}<%=flag%>(rowId,obj){
			
			var myTable = document.getElementById('table${questionType}<%=flag%>');	
			//t_rownum${questionType}=t_rownum${questionType}-1;
			myTable.deleteRow(obj.parentNode.parentNode.rowIndex);
			document.getElementById("quantity${questionType}").value -= 1;	
			return;
		}
		
		function DeleteChoiceDB${questionType}<%=flag%>(rowId,contentID,obj){
		    if(confirm('您确认删除吗？')){	   
			  hidden_submit_param(contentID);      
		    }else
		      return;
		    DeleteChoice${questionType}<%=flag%>(rowId,obj);
		}
		questionsCount(${questionType});
</script>			


