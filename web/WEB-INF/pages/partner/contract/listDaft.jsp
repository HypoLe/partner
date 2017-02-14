<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
	
		 <form method="post" action="contact.do?method=export&type=${type}&pageSize=${resultSize }">
			当前位置：>>业务联系函>>草稿列表<br>
			<img src="${app}/partner/contact/images/delete1.gif"></img>
			未完成，未超时 <img src="${app}/partner/contact/images/delete.gif"></img> 
			未完成，已超时 <img src="${app}/partner/contact/images/yes.gif"></img> 
			已完成，未超时 <img src="${app}/partner/contact/images/yes1.gif"></img> 
			已完成，已超时
			
		 </form>
		 <br>
		<table    style="width:800" 	class="formTable"  >
		      <tr align='center' >
			        <td></td>
			        <td>编号</td>
			        <td>发布人</td>
			        <td>派发时间</td>
			        <td>主题</td>
			        <td>处理时限</td>
		      </tr>
		    <c:forEach   begin="0"  step="1" varStatus="status" var="listTemp" items="${contactMainList}">
		       <tr>
			        <td>
			           
				      
						
						
					         <c:if test="${listTemp.overTimeFlag==0 }">
				    			<img src="${app}/partner/contact/images/delete1.gif"></img> 
					    	</c:if>
					    	 <c:if test="${listTemp.overTimeFlag==1 }">
					    			<img src="${app}/partner/contact/images/delete.gif"></img> 
					    	</c:if>
					    	 <c:if test="${listTemp.overTimeFlag==2 }">
					    		<img src="${app}/partner/contact/images/yes.gif"></img> 
					    	</c:if>
					    	 <c:if test="${listTemp.overTimeFlag==3 }">
					    		<img src="${app}/partner/contact/images/yes1.gif"></img>
					    	</c:if>
				         
		    	    </td>
			        <td>
			          <a href="contact.do?method=getJsp&pageName=draftsPage&pageType=edit&mainId=${listTemp.id }" >
						
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
     <form method="post" action="contact.do?method=search&type=${type}">
		  <table style="border:0"  width="800">
		    <tr>
		    <td  height="57"  align="left"><span>找到${resultSize}条数据，以下是${first }到${end }条 </span>
			<td >
			<td  align="right">
			  <div align="right"><span align="right">
			    <a href="contact.do?method=search&type=${type}&pageIndex=1" ><img src="${app}/partner/contact/images/back1.gif"></a>
			    <a href="contact.do?method=search&type=${type}&pageIndex=${pageIndex-1 }"  ><img src="${app}/partner/contact/images/back.gif"></a> 
			    ${pageIndex}/${pages } 
			    <a href="contact.do?method=search&type=${type}&pageIndex=${pageIndex+1 }"><img src="${app}/partner/contact/images/go.gif"></a>
			    <a href="contact.do?method=search&type=${type}&pageIndex=${pages }" ><img src="${app}/partner/contact/images/go1.gif"></a> 跳到
			    <input type="text" name="pageIndex" size="2" />
			    页
			    <input name="submit" type="submit" value="确认"/>
			    </span>
		        </div></td>
			</tr>
		</table>
</form>
	<br><br>
<%@ include file="/common/footer_eoms.jsp"%>