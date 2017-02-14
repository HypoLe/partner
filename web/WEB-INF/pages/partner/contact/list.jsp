<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>

当前位置：>>业务联系函>>待办业务联系函<br/><br/>	
<form id="exportForm" method="post" action="contact.do?method=export&type=${type}&pageSize=${pageSize}&pageIndex=${pageIndex}">
			<c:if test="${type=='todo'||type=='done'}">
				<c:if test="${resultSize>0}">
				    <input type="hidden" id="allYN" name="allYN" value="false" />
					<input type="submit" value="导出"  onclick="exportPage();"/>&nbsp;&nbsp;&nbsp;
					<input type="submit" value="全部导出" onclick="exportAll();"/>
				</c:if>
			</c:if>
</form>
<br>
		 
<logic:notEmpty name="contactMainList" scope="request">
   <display:table name="contactMainList" cellspacing="0" cellpadding="0"
		id="listTemp" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="contact.do?method=search&type=${type}"
		sort="list" partialList="true" size="${resultSize}">
	<display:caption media="html"> 
		<span class="map"><img
				src="${app}/partner/contact/images/delete1.gif"></img>未完成，未超时</span>
		<span class="map"><img
				src="${app}/partner/contact/images/delete.gif"></img> 未完成，已超时</span>
		<span class="map"><img
				src="${app}/partner/contact/images/yes.gif"></img> 已完成，未超时</span>
		<span class="map"><img
				src="${app}/partner/contact/images/yes1.gif"></img>已完成，已超时</span>
	</display:caption> 
	<display:column>
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
	</display:column>
	<display:column title="编号">
		                <c:if test="${listTemp.type=='1' }">
						  <a href="contact.do?method=getJsp&pageName=auditPage&pageType=edit&mainId=${listTemp.id }" >
						</c:if>
						<c:if test="${listTemp.type=='2' or listTemp.type=='3'  }">
						  <a href="contact.do?method=getJsp&pageName=handlePage&pageType=edit&mainId=${listTemp.id }" >
						</c:if>			           
			            <c:if test="${listTemp.isUrgent==1 }">
		    			        <font color="red" >${listTemp.code }</font>
				    	</c:if>
				    	 <c:if test="${listTemp.isUrgent!=1 }">
				    			<font  color="black" >${listTemp.code }</font>
				    	</c:if>
			             </a>
	</display:column>
	<display:column title="发布人">
		${listTemp.publisherName}
	</display:column>
	<display:column title="派发时间">
		${fn:substring(listTemp.publishTime, 0, 19)}
	</display:column>
	<display:column title="主题">
		<c:if test="${listTemp.isUrgent==1}">加急：</c:if>${listTemp.subject}
	</display:column>
	<display:column title="处理时限">
		${fn:substring(listTemp.deathTime, 0, 19)}
	</display:column>
   </display:table>
</logic:notEmpty>   
<logic:empty name="contactMainList" scope="request">
 没有数据
</logic:empty>		 
	
<script type="text/javascript">
     function exportAll(){
         $('#allYN').val('true');         
     }
     function exportPage(){
         $('#allYN').val('false');   
     }
	</script>		
<%@ include file="/common/footer_eoms.jsp"%>