<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>

	<form id="form1" method="post" action="materiaLib.do?method=search">
					<table id="sheet" class="formTable" >	
						 <tr>
							<td class="label"  > 
								主题
							</td>
							<td class="content" >
								<input type="text" id="subject" class="text" name="subjectStringLike" value="${subjectStringLike }"/>
							</td>
							<td class="label"> 
								关键词
							</td>
							<td class="content" colspan="3">
								 <input type='text' id='keyWordStringLike' class="text" name="keyWordStringLike"   value="${keyWordStringLike }"  maxlength="80"/>
			  				</td>							
						</tr>
						<tr>
							<td class="label"> 
								发布时间从
							</td>
							<td class="content" >
								<input type="text" id="publishTimeDateGreaterThan" class="text" name="publishTimeDateGreaterThan" 
										onclick="popUpCalendar(this, this,null,null,null,null,-1)"  readonly
										value="${publishTimeDateGreaterThan }"/>
							</td>
							<td class="label"> 
								到
							</td>
							<td class="content" >
								<input type="text" id="publishTimeDateLessThan" class="text" name="publishTimeDateLessThan" 
										onclick="popUpCalendar(this, this,null,null,null,null,-1)"  readonly
										value="${publishTimeDateLessThan }"/>
							</td>
						</tr>
						<tr>
							<td class="content"  colspan="4" style="text-align: center">
							    
								<input id="select" type="submit" class="btn" value="<fmt:message key="button.query"/>" /> &nbsp;&nbsp;&nbsp;
								<input id="reset1" type="button" class="btn" value="重置" onclick="clearForm(myJ('#form1'));"/>
							</td>
						</tr>
					</table>
			</form>
<br>
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
}

</script>  		
<%@ include file="/common/footer_eoms.jsp"%>