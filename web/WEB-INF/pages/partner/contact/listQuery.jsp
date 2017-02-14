<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="java.util.*;"%>

当前位置：>>业务联系函>>业务联系函查询
<br/><br/>
<div style="border: 1px solid #98c0f4; padding: 5px;"
	class="x-layout-panel-hd">
	<img src="${app}/images/icons/search.gif" align="absmiddle"
		style="cursor: pointer" />
	<span id="openQuery" style="cursor: pointer"
		onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject"
	style="display: block; border: 1px solid #98c0f4; border-top: 0; padding: 5px; background-color: #eff6ff;">
	<form method="post" id="form11"
		action="contact.do?method=search&type=${type}"
		onSubmit="return checkForm();">
		<table class="formTable">
			<tr>
				<td class="label">
					编号
				</td>
				<td class="content" colspan="3">
					<input type='text' id='codeStringLike' name="codeStringLike"
						value="${codeStringLike}" maxlength="80" style="width: 70%"
						class="text" />

				</td>
			</tr>
			<tr>
				<td class="label">
					主题
				</td>
				<td class="content" colspan="3">
					<input type='text' id='subjectStringLike' name="subjectStringLike"
						value="${subjectStringLike}" maxlength="80" style="width: 70%"
						class="text" />
				</td>
			</tr>
			<tr>
				<td class="label">
					发布人
				</td>
				<td class="content">
					<input type='text' id='publiserNameStringLike'
						name="publiserNameStringLike" value="${publiserNameStringLike}"
						maxlength="80" class="text" />

				</td>

				<td class="label">
					处理人
				</td>
				<td class="content" colspan="3">
					<input type='text' id='taskOnwerNameStringLike'
						name="taskOnwerNameStringLike" value="${taskOnwerNameStringLike}"
						maxlength="80" class="text" />
				</td>
			</tr>

			<tr>
				<td class="label">
					派发开始时间
				</td>
				<td class="content">
					<input type="text" id="publishTimeDateGreaterThan"
						name="publishTimeDateGreaterThan" class="text medium"
						alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank"
						onclick="popUpCalendar(this, this,null,null,null,null,-1)"
						readonly="readonly" value='${publishTimeDateGreaterThan}' />
				</td>

				<td class="label">
					派发结束时间
				</td>
				<td class="content">
					<input type="text" id="publishTimeDateLessThan"
						name="publishTimeDateLessThan" class="text"
						alt="vtype:'moreThen',vtext:'处理期限不能为空！',allowBlank:false"
						readonly="readonly"
						onclick="popUpCalendar(this, this,null,null,null,null,-1)"
						value='${publishTimeDateLessThan}' />
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<input type="button" styleClass="btn" value="清空"
						onClick="clearForm(document.getElementById('form11'));" />
					&nbsp;&nbsp;&nbsp;
					<input type="submit" value="查询" />			
				</td>
			</tr>
		</table>
	</form>
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display: none">
	载入详细数据中，请稍候
</div>
</hr>
<br />

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
		<a href="contact.do?method=searchForLink&pageName=listLink&pageType=edit&mainId=${listTemp.id }" target="contactDetail">
					<c:if test="${listTemp.isUrgent==1 }">
						<font color="red">${listTemp.code }</font>
					</c:if> <c:if test="${listTemp.isUrgent!=1 }">
						<font color="black">${listTemp.code }</font>
					</c:if> </a>
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
<br>

<script type="text/javascript">
var myJ=jQuery.noConflict();	

function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
}

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
}

  function checkForm(){
    if( (myJ('#publishTimeDateGreaterThan').val().trim()!=''&&myJ('#publishTimeDateLessThan').val().trim()!='')
    &&myJ('#publishTimeDateGreaterThan').val().trim()>myJ('#publishTimeDateLessThan').val().trim()){
       alert('派发开始时间应不大于派发结束时间 ');
       return false;
    }
  }
	</script>
<%@ include file="/common/footer_eoms.jsp"%>