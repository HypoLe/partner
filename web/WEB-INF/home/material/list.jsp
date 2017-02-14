<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>
	<form id="form1" method="post" action="materiaLib.do?method=search">
					<table id="sheet" class="formTable" >	
						 <tr>
							<td class="label"  > 
								主题
							</td>
							<td class="content" >
								<input type="text" id="subject" name="subjectStringLike" value="${subjectStringLike }"/>
							</td>
							<td class="label"> 
								发布时间
							</td>
							<td class="content" >
								<input type="text" id="publishTimeDateGreaterThan" name="publishTimeDateGreaterThan" 
										onfocus="WdatePicker({dateFmt:'yyyy-M-d H:mm:ss',maxDate:'#F{\'%y-%M-%d %H-%m-%s\'}',readOnly:true})" 
										value="${publishTimeDateGreaterThan }"/>
							</td>
						</tr>
						<tr>
							<td class="label"> 
								关键词
							</td>
							<td class="content" colspan="3">
								 <input type='text' id='keyWordStringLike' name="keyWordStringLike"   value="${keyWordStringLike }"  maxlength="80"/>
			  				</td>
						</tr>
						<tr>
							<td class="content"  colspan="4" style="text-align: center">
										<input id="select" type="submit" class="btn" value="查询结果" />
							</td>
						</tr>
					</table>
			</form>

	<display:table name="materialLibList" cellspacing="0" cellpadding="0"
						id="materialLibList" pagesize="${pageSize}" class="table"
						export="false" requestURI="materiaLib.do?method=search"
						sort="list" partialList="true" size="${resultSize }" >
	    <display:column property="subject" sortable="true" headerClass="sortable" title="主题"/>
	    <display:column property="publisherName" sortable="true" headerClass="sortable" title="发布人"/>
	    <display:column sortable="true" headerClass="sortable" title="发布时间">
	  			  ${fn:substring(materialLibList.publishTime, 0, 19)}
	    </display:column>
	    <display:column  sortable="true" headerClass="sortable" title="专业">
	    	<eoms:id2nameDB id="${materialLibList.specialty}" beanId="ItawSystemDictTypeDao" />
	    </display:column>
	    <c:if test="${!empty requestScope.operationType }">
	    	<display:column headerClass="sortable" title="修改" media="html" >
			 				   <a  href="materiaLib.do?method=getGsp&pageName=add_Edit&pageType=edit&id=${materialLibList.id }" >	
			 					<img src="${app }/images/icons/edit.gif"></a>
	    	</display:column>
	    	<display:column headerClass="sortable" title="删除" media="html" >
			 				   <a  href="materiaLib.do?method=remove&id=${materialLibList.id }"
			 				   		 onclick="return confirm('确定删除吗？');">	
			 					<img src="${app }/images/icons/delete.gif"></a>
	    	</display:column>
	    </c:if>	
	    <display:column headerClass="sortable" title="查看详细" media="html" >
			 				   <a  href="materiaLib.do?method=getGsp&pageName=detail&id=${materialLibList.id }" >	
			 					<img src="${app }/images/icons/table.gif"></a>
		</display:column>
	</display:table>
	<br><br>
	<c:if test="${!empty requestScope.operationType }">	
		<input type="button" class="btn" value="新增" 
				onclick="location.href='materiaLib.do?method=getGsp&pageName=add_Edit&pateType=add';" />
	</c:if>
<%@ include file="/common/footer_eoms.jsp"%>