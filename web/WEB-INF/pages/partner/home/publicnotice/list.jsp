<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
	<form id="form1" method="post" action="publish.do?method=getList&listType=${requestScope.listType}" onSubmit="return checkForm();">
					<table id="sheet" class="formTable" >	
						 <tr>
							<td class="label"  > 
								主题
							</td>
							<td class="content" colspan="4" >
								<input type="text" class="text" id="subject" name="subjectStringLike" style="width: 380PX" value="${subjectStringLike }"/>
							</td>
						</tr>
						<tr>	
							<td class="label"> 
								发布时间从
							</td>
							<td class="content" >
								<input type="text" class="text" id="publishTimeDateGreaterThan" name="publishTimeDateGreaterThan" 
										onclick="popUpCalendar(this, this,null,null,null,null,-1);"  readonly
										value="${publishTimeDateGreaterThan }"/>
							</td>
							<td class="label"> 
								到
							</td>
							<td class="content" >
								<input type="text" class="text" id="publishTimeDateLessThan" name="publishTimeDateLessThan" 
										onclick="popUpCalendar(this, this,null,null,null,null,-1);" readonly
										value="${publishTimeDateLessThan }"/>
							</td>
						</tr>
						<tr>	
							<td class="label"> 
								有效期从
							</td>
							<td class="content" >
								<input type="text" class="text" id="validDateGreaterThan" name="validDateGreaterThan" 
										onclick="popUpCalendar(this, this,null,null,null,null,-1);"  readonly
										value="${validDateGreaterThan }"/>
							</td>
							<td class="label"> 
								到
							</td>
							<td class="content" >
								<input type="text" class="text" id="validDateLessThan" name="validDateLessThan" 
										onclick="popUpCalendar(this, this,null,null,null,null,-1);" readonly
										value="${validDateLessThan }"/>
							</td>
						</tr>
						<tr>
							<td class="content"  colspan="4" style="text-align: center">							   
								<input id="select" type="submit" class="btn" value="查询" />&nbsp;&nbsp;&nbsp;
								<input id="select" type="button" class="btn" value="重置" onClick="clearForm(myJ('#form1'));"/>
							</td>
						</tr>
					</table>
			</form>

<br>
	<display:table name="publishList" cellspacing="0" cellpadding="0"
		id="publishList" pagesize="${pageSize}" class="table"
		export="false" requestURI="publish.do?method=getList&listType=${requestScope.listType}"
		sort="list" partialList="true" size="${resultSize }" >
	    <display:column  sortable="true"  title="主题">
	      ${publishList.subject}<c:if test="${requestScope.listType==1 && publishList.type==3}"><font color="red" >(被驳回)</font></c:if>
	    </display:column>
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
					<c:if test="${publishList.type==4 }">阅知</c:if>
					<c:if test="${publishList.type==5 }">过期</c:if>		
			 </display:column>
			 <display:column  headerClass="sortable" title="查看详细" media="html" >			 
				<a  href="publish.do?method=getGsp&pageName=detail&isRead=1&id=${publishList.id }" target="_blank">
				<img src="${app }/images/icons/table.gif"/></a>	
		 	</display:column>
	 	</c:if>
	 	
		<c:if test="${requestScope.listType==3}">	
		 	<display:column  headerClass="sortable" title="查阅" media="html" >						 
				<a  href="publish.do?method=getGsp&pageName=detail&isRead=0&id=${publishList.id }" >
				<img src="${app }/images/icons/table.gif"/></a>	
		 	</display:column>
		 </c:if>
	</display:table>
	<br><br>
	
<script type="text/javascript">
var myJ=jQuery.noConflict();
function clearForm(form) {
  // iterate over all of the inputs for the form
  // element that was passed in
  myJ(':input', form).each(function() {//  jQueryjQuery(expression, [context]) expression:传递一个表达式（通常由 CSS 选择器组成）; context 可选:作为待查找的 DOM 元素集、文档或 jQuery 对象。 
    var type = this.type;
    var tag = this.tagName.toLowerCase(); // normalize case
    // it's ok to reset the value attr of text inputs,
    // password inputs, and textareas
    if (type == 'text'||type == 'hidden'|| type == 'password' || tag == 'textarea')
      this.value = "";
    // checkboxes and radios need to have their checked state cleared
    // but should *not* have their 'value' changed
    else if (type == 'checkbox' || type == 'radio')
      this.checked = false;
    // select elements need to have their 'selectedIndex' property set to -1
    // (this works for both single and multiple select elements)
    else if (tag == 'select')
      this.selectedIndex = -1;
  });
};

function checkForm(){
    if( (myJ('#publishTimeDateGreaterThan').val().trim()!=''&&myJ('#publishTimeDateLessThan').val().trim()!='')
    &&myJ('#publishTimeDateGreaterThan').val().trim()>myJ('#publishTimeDateLessThan').val().trim()){
       alert('查询条件，发布时间开始时间应不大于结束时间 ');
       return false;
    }
    
    if( (myJ('#validDateGreaterThan').val().trim()!=''&&myJ('#validDateLessThan').val().trim()!='')
    &&myJ('#validDateGreaterThan').val().trim()>myJ('#validDateLessThan').val().trim()){
       alert('查询条件，有效期应不大于结束时间 ');
       return false;
    }
}

</script>  	
<%@ include file="/common/footer_eoms.jsp"%>