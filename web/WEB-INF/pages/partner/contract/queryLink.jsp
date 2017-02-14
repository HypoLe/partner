<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
	<form id="form1" method="post">
					<table id="sheet" class="formTable" >	
					
					<tr>
							<td class="label"  > 
								编号
							</td>
							<td class="content" colspan="4" >
								<input type="text" id="subject" name="codeStringLike" style="width: 380PX" value="${codeStringLike }"/>
							</td>
						</tr>
					
						 <tr>
							<td class="label"  > 
								主题
							</td>
							<td class="content" colspan="4" >
								<input type="text" id="subject" name="subjectStringLike" style="width: 380PX" value="${subjectStringLike }"/>
							</td>
						</tr>
						
						<tr>	
							<td class="label"> 
								发布开始时间
							</td>
							<td class="content" >
								<input type="text" id="publishTimeDateGreaterThan" name="publishTimeDateGreaterThan" 
										onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',maxDate:'#F{$dp.$D(\'deathTimeDateLessThan\')||\'%y-%M-%d %H-%m-%s\'}',readOnly:true})" 
										value="${publishTimeDateGreaterThan }"/>
							</td>
							<td class="label"> 
								结束时间
							</td>
							<td class="content" >
								<input type="text" id="deathTimeDateLessThan" name="deathTimeDateLessThan" 
										onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',minDate:'#F{$dp.$D(\'publishTimeDateGreaterThan\')}',readOnly:true})" 
										value="${deathTimeDateLessThan }"/>
							</td>
						</tr>
						
						<tr>	
							<td class="label"> 
								发布人
							</td>
							<td class="content" >
								<input type="text" id="publiserName" name="publiserNameStringLike" 	value="${publiserNameStringLike }"/>
							</td>
							
						</tr>
						<tr>
							<td class="content"  colspan="4" style="text-align: center">
										<input id="select" type="submit" class="btn" value="查询结果" />
							</td>
						</tr>
					</table>
			</form>
	<display:table name="contactMainList" cellspacing="0" cellpadding="0"
		id="contactMainList" pagesize="${pageSize}" class="table"
		export="false" requestURI="contact.do?method=search&listType=${ requestScope.type}"
		sort="list" partialList="true" size="${resultSize }" >
		 <display:column sortable="true"  headerClass="sortable" title="编号">
			 <c:if test="${contactMainList.isUrgent==1 }">
		    			<font color="red" >${contactMainList.code }</font>
		    	</c:if>
		    	 <c:if test="${contactMainList.isUrgent!=1 }">
		    			<font  color="black" >${contactMainList.code }</font>
		    	</c:if>
		    </display:column>
	    <display:column sortable="true"  property="subject" headerClass="sortable" title="主题"/>
	    <display:column sortable="true" headerClass="sortable" title="发布时间">
			${fn:substring(contactMainList.publishTime, 0, 19)}
	    </display:column>
	    <display:column sortable="true" headerClass="sortable" title="结束时间">
	    	${fn:substring(contactMainList.deathTime, 0, 19)}
	    </display:column>		
	    <display:column  sortable="true" headerClass="sortable" title="内容" >
	    	${fn:substring(contactMainList.content, 0, 15)}.......
	    </display:column>
		<c:if test="${requestScope.type=='draft' }">
			    <display:column  headerClass="sortable" title="修改" media="html" >					 
						<a href="contact.do?method=getJsp&pageName=draftsPage&pageType=edit&mainId=${contactMainList.id }">
						<img src="${app }/images/icons/edit.gif"></a>			
			 	</display:column>
			 	<display:column headerClass="sortable" title="送审" media="html" >
 					 <a href="contact.do?method=toAudit&mainId=${contactMainList.id }"    onclick="return confirm('确定送审吗？');">
 					<img src="${app }/images/icons/edit.gif"></a>	
			 	</display:column>
	 	</c:if>
	 	<c:if test="${requestScope.type=='todo' }">
	 		<display:column  headerClass="sortable" title="联系函状态" media="html" >	
	 				<c:if test="${contactMainList.type==1 }">审批中</c:if>				 
					<c:if test="${contactMainList.type==2 }">发布中</c:if>
					<c:if test="${contactMainList.type==3 }">转发中</c:if>
			 </display:column>
			 <display:column  headerClass="sortable" title="操作" media="html" >					 
					
						<a href="contact.do?method=searchForLink&pageName=listLink&mainId=${contactMainList.id }" target="_blank">查看</a>
					
					
			 </display:column>
	 	</c:if>
	</display:table>
	<br><br>
<%@ include file="/common/footer_eoms.jsp"%>