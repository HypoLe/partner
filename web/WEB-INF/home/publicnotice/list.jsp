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
										onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',maxDate:'#F{$dp.$D(\'validDateLessThan\')||\'%y-%M-%d %H-%m-%s\'}',readOnly:true})" 
										value="${publishTimeDateGreaterThan }"/>
							</td>
							<td class="label"> 
								结束时间
							</td>
							<td class="content" >
								<input type="text" id="validDateLessThan" name="validDateLessThan" 
										onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',minDate:'#F{$dp.$D(\'publishTimeDateGreaterThan\')}',readOnly:true})" 
										value="${validDateLessThan }"/>
							</td>
						</tr>
						<tr>
							<td class="content"  colspan="4" style="text-align: center">
										<input id="select" type="submit" class="btn" value="查询结果" />
							</td>
						</tr>
					</table>
			</form>

	<display:table name="publishList" cellspacing="0" cellpadding="0"
		id="publishList" pagesize="${pageSize}" class="table"
		export="false" requestURI="publish.do?method=getList&listType=${ requestScope.listType}"
		sort="list" partialList="true" size="${resultSize }" >
	    <display:column property="subject" sortable="true" headerClass="sortable" title="主题"/>
	    <display:column sortable="true" headerClass="sortable" title="发布时间">
			${fn:substring(publishList.publishTime, 0, 19)}
	    </display:column>
	    <display:column sortable="true" headerClass="sortable" title="有效期">
	    	${fn:substring(publishList.valid, 0, 19)}
	    </display:column>		
	    <display:column  sortable="true" headerClass="sortable" title="内容" >
	    	${fn:substring(publishList.publishContent, 0, 15)}.......
	    </display:column>
		<c:if test="${requestScope.listType==1 }">
			    <display:column  headerClass="sortable" title="修改" media="html" >					 
						<a href="publish.do?method=getGsp&pageName=draftsAdd_Edit&pageType=editDrafts&id=${publishList.id }">
						<img src="${app }/images/icons/edit.gif">	</a>			
			 	</display:column>
			 	<display:column headerClass="sortable" title="作废" media="html" >
			 					<a href="publish.do?method=invalid&id=${publishList.id }"    onclick="return confirm('确定作废吗？作废后不可恢复！');">
								<img src="${app }/images/icons/icon.gif"></a>			
			 	</display:column>
			 	<display:column headerClass="sortable" title="送审" media="html" >
			 				<c:if test="${publishList.isAudit==1 }">
			 					 <a href="publish.do?method=toAudit&id=${publishList.id }"    onclick="return confirm('确定送审吗？');">
			 					<img src="${app }/images/icons/edit.gif">	</a>	
			 				</c:if>
			 				<c:if test="${publishList.isAudit==0 }">
			 					无审批人
			 				</c:if>
			 	</display:column>
	 	</c:if>
	 	<c:if test="${requestScope.listType==2 }">
		 	<display:column  headerClass="sortable" title="审批" media="html" >					 
					<a  href="publish.do?method=getGsp&pageName=audit&id=${publishList.id }" >	
					<img src="${app }/images/icons/edit.gif">	</a>			
		 	</display:column>
	 	</c:if>
	 	
	 	<c:if test="${requestScope.listType==4 }">
	 		<display:column  headerClass="sortable" title="公告状态" media="html" >					 
					<c:if test="${publishList.type==-1 }">作废</c:if>
					<c:if test="${publishList.type==0 }">草稿</c:if>
					<c:if test="${publishList.type==1 }">审批中</c:if>
					<c:if test="${publishList.type==2 }">发布中</c:if>
					<c:if test="${publishList.type==3 }">被驳回</c:if>
					<c:if test="${publishList.type==4 }">已阅知</c:if>
					<c:if test="${publishList.type==5 }">已过期</c:if>		
			 </display:column>
	 	</c:if>
		 <c:if test="${ requestScope.listType==4 or requestScope.listType==3}">	
		 	<display:column  headerClass="sortable" title="查看详细" media="html" >			
			 	<c:if test="${ requestScope.listType==4 }">		 
						<a  href="publish.do?method=getGsp&pageName=detail&isRead=1&id=${publishList.id }" target="_blank">
						<img src="${app }/images/icons/table.gif"/></a>	
				</c:if>	
				<c:if test="${ requestScope.listType==3 }">		 
						<a  href="publish.do?method=getGsp&pageName=detail&isRead=0&id=${publishList.id }" >
						<img src="${app }/images/icons/table.gif"/></a>	
				</c:if>	
		 	</display:column>
		 </c:if>
	</display:table>
	<br><br>
<%@ include file="/common/footer_eoms.jsp"%>