<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
	<form method="post" id="form11" action="contact.do?method=search&type=${type}">
	当前位置：>>业务联系函>>业务联系函查询<br><br>
	<table class="formTable"  style="width:800">
			<tr>
				<td class="label" >
				编号
				</td>
				<td class="content" colspan="3">
					<input type='text' id='codeStringLike' name="codeStringLike"   value="${codeStringLike}"  maxlength="80"
						  	   style="width: 40%"  />
					
				</td>
			</tr>
			<tr>
				<td class="label" >
				 主题
				</td>
				<td class="content" colspan="3">
					<input type='text' id='subjectStringLike' name="subjectStringLike"   value="${subjectStringLike}"  maxlength="80"
						  	   style="width: 70%"  />
				</td>
			</tr>
		    <tr>
				<td class="label" >
				发布人
				</td>
				<td class="content" >
					<input type='text' id='publiserNameStringLike' name="publiserNameStringLike"   value="${publiserNameStringLike}"  maxlength="80"
						  	   />
					
				</td>
			
				<td class="label" >
				处理人
				</td>
				<td class="content" colspan="3">
					<input type='text' id='taskOnwerNameStringLike' name="taskOnwerNameStringLike"   value="${taskOnwerNameStringLike}"  maxlength="80"
						  	   style="width: 40%" />
				</td>
			</tr>
		
			<tr>
				<td class="label" >
				 发布开始时间
				</td>
				<td class="content" >
					<input type="text" id="publishTimeDateGreaterThan" name="publishTimeDateGreaterThan"  class="text medium"
						   alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank:false" 
						   onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',readOnly:true})" 
						   value='${fn:substring(publishTimeDateGreaterThan, 0, 19)}'/>
				</td>
			
			<td class="label" >
				发布结束时间
				</td>
				<td class="content" >
					<input type="text" id="deathTimeDateLessThan" name="deathTimeDateLessThan"  class="text medium"
						   alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank:false" 
						   onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',readOnly:true})" 
						   value='${fn:substring(deathTimeDateLessThan, 0, 19)}'/>
				</td>
			
				
				
			</tr>
				<tr>
				  <td colspan="4" align="center"><input type="submit" value="查询" /><input  id="pageIndexSize" name="pageIndex" type="hidden" vaule="" /></td>
				</tr>
		</table>
		</form>
		<br>
		<table class="formTable" style="width:800"  >
		      <tr align='center' >
			    
			        <td>编号</td>
			        <td>发布人</td>
			        <td>派发时间</td>
			        <td>主题</td>
			        <td>处理时限</td>
		      </tr>
		    <c:forEach   begin="0"  step="1" varStatus="status" var="listTemp" items="${contactMainList}">
		       <tr>
			   
			        <td>
			            <a  href="contact.do?method=searchForLink&pageName=listLink&pageType=edit&mainId=${listTemp.id }" >
			            <c:if test="${listTemp.isUrgent==1 }">
		    			        <font color="red" >${listTemp.code }</font>
				    	</c:if>
				    	 <c:if test="${listTemp.isUrgent!=1 }">
				    			<font  color="black" >${listTemp.code }</font>
				    	</c:if>
				    	</a>
			        </td>
			        <td>
			           ${listTemp.publisherName}
			        </td>
			        <td>
			           ${fn:substring(listTemp.publishTime, 0, 19)}
			        </td>
			        <td>
			           ${listTemp.subject}
			        </td>
			        <td>
			           	${fn:substring(listTemp.deathTime, 0, 19)}
			        </td>
		       </tr>
		    </c:forEach>
		</table>
		<br>
    
		  <table style="border:0"  width="800">
		    <tr>
		    <td  height="57"  align="left"><span>找到${resultSize}条数据，以下是${first }到${end }条 </span>
			<td >
			<td  align="right">
			  <div align="right"><span align="right">
			    <a href="javaScript:dos(1)" ><img src="${app}/partner/contact/images/back1.gif"></a>
			    <a href="javaScript:dos(${pageIndex-1 })"  ><img src="${app}/partner/contact/images/back.gif"></a>
			     ${pageIndex}/${pages }  
			    <a href="javaScript:dos(${pageIndex+1 })"><img src="${app}/partner/contact/images/go.gif"></a>
			    <a href="javaScript:dos(${pages })" ><img src="${app}/partner/contact/images/go1.gif"></a> 跳到
			    <input type="text" name="pageIndex" id="pageIndex2" size="2" />
			    页
			    <input name="submit" type="button" onclick="dos(-2)" value="确认"/>
			    </span>
		        </div></td>
			</tr>
		</table>

	<br><br>
	<script type="text/javascript">
	  function dos(index){
	  if(index=="-2"){
	    document.getElementById("pageIndexSize").value= document.getElementById("pageIndex2").value;
	  }
	  else{
		  document.getElementById("pageIndexSize").value=index;
		  }
		  document.getElementById("form11").submit();
	  }
	</script>
<%@ include file="/common/footer_eoms.jsp"%>