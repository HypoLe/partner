<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style>
 SELECT {width:150;}
 INPUT:TEXT {width:90%;}
</style>

<script type="text/javascript">
  //没有如下代码,将报错 ext.js:TypeError: $(this.showChkFldId).update is not a function
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
	
	function displaySelect(selectId,theValue){
      var all_options = document.getElementById(selectId).options;
      for (i=0; i<all_options.length; i++){
        if (all_options[i].value == theValue)  // 根据option标签的ID来进行判断  测试的代码这里是两个等号
        {
         all_options[i].selected = true;
        }
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
};
   
function clearForm1(){
  // clearForm(document.getElementById('queryNormalConditionForm'));
  //clearForm(jq('#queryNormalConditionForm').get()); //均可以。  jQueryjQuery(expression, [context]) expression:传递一个表达式（通常由 CSS 选择器组成）; context 可选:作为待查找的 DOM 元素集、文档或 jQuery 对象。 
   clearForm(myJ('#queryNormalConditionForm'));
}

	Ext.onReady(function(){
	   displaySelect('status','${status}');	  
	});
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:block;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/evaluation/evaluStatAndQur.do?method=queryNormal" 
	method="post" id="queryNormalConditionForm">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">考核对象：</td>
				<td>
				  <input type="text" id="evaluationTarget" name="evaluationTarget"  class="text" readonly="true" value="${evaluationTarget}"  />
				  <input type="hidden" id="evaluationTargetId" name="evaluationTargetId" value="${evaluationTargetId}"/>
				  <eoms:xbox id="evaluationTarget" dataUrl="${app}/xtree.do?method=userFromComp&popedom=false"
					rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="evaluationTargetId"
					handler="evaluationTarget" textField="evaluationTarget" checktype="dept" single="true">
				  </eoms:xbox>
				</td>
				<td class="label">年：</td>
				<td>
				    <eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
					beanId="selectXML" cls="select" defaultId="${year}" />
				</td>
				<td class="label">月：</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
					beanId="selectXML" cls="select" defaultId="${month}"/>
				</td>											
			</tr>		
			<tr>		
			    <td class="label">考核项目：</td>
				<td>
				  <eoms:comboBox name="xmdictid" id="xmdictid" defaultValue="${xmdictid}" initDicId="112270101"  />
				</td>
				<td class="label">考核专业：</td>
				<td>
				  <eoms:comboBox name="majorid" id="majorid" defaultValue="${majorid}" initDicId="11225" />
				</td>
				<td class="label">状态：</td>
				<td>
					<select name="status" id="status" >
					  <option value="0">全部</option>
					  <option value="1">已通过</option>
					  <option value="2">未通过</option>
					</select> 
				</td>
			</tr>		
	    </table>
	    <input type="button" styleClass="btn" value="重置" onClick="clearForm1();"/>&nbsp;&nbsp;&nbsp;
		<input type="submit" styleClass="btn" value="查询" />
	</form>
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>
         
        
        
<logic:present name="resultList" scope="request">
    <display:table name="resultList" cellspacing="0" cellpadding="0"
		id="listEL" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/evaluation/evaluStatAndQur.do?method=queryNormal"
		sort="external" partialList="true" size="${total}">
			
		<display:column   title="考核对象">
				<eoms:id2nameDB id="${listEL.evaluationTarget}" beanId="partnerDeptDao" />
		</display:column>					
	
		<display:column  title="代维确认人" >
		   <eoms:id2nameDB id='${listEL.dyConfirmPersonnel}' beanId='tawSystemUserDao'/>
		</display:column>		
	
		<display:column  title="考核月份" sortable="true" headerClass="sortable" sortName="yearMonth">
			${listEL.year}-${listEL.month}
		</display:column>		
				
		<display:column title="项目类型"  sortable="true" headerClass="sortable" sortName="xmdictid">
			  <c:if test="${empty listEL.xmdictid}">
			     总得分
			  </c:if>
			  <c:if test="${not empty listEL.xmdictid}">
			  	<eoms:id2nameDB id="${listEL.xmdictid}" beanId="ItawSystemDictTypeDao" />
			  </c:if>			 
		</display:column>		
				
		<display:column title="专业类型"  sortable="true" headerClass="sortable" sortName="specialty">
			 <eoms:id2nameDB id="${listEL.specialty}" beanId="ItawSystemDictTypeDao" />
		</display:column>
			
		<!-- 查看、修改在新页面，删除后依旧返回列表 -->		
		<display:column  title="得分" sortable="true" headerClass="sortable" sortName="score">
		    <a  href="${app}/partner/evaluation/evaluationEntity.do?method=gotoViewScorePage&id=${listEL.id}"
				target="view" shape="rect"> 
				  ${listEL.score} 
			</a>
		</display:column>	
    	<display:column  title="状态">  
    	  <!-- ENTITY_STATUS_CONFIRMED="2";	//已确认/审核
    	  
    	       public static final String XIANGMU_SUBMIT="02";//项目已经提交打分了，但是还没有确认
    	       public static final String XIANGMU_CONFIRMED="04";//已确认/审核
    	       
    	       public static final String MAJOR_SUBMIT="02";//专业已经提交打分了，但是还没有确认
    	       public static final String MAJOR_CONFIRMED="03";//已确认/审核
    	       展现的数据的状态来自于以上，XXX_CONFIRMED，才表示已经确认。；故非确认的为XIANGMU_SUBMIT、MAJOR_SUBMIT都是"02"
    	   -->    
		    <c:if test="${listEL.status=='02' and fn:length(listEL.status)==2}">未通过</c:if>
		    <c:if test="${listEL.status!='02' and fn:length(listEL.status)==2}">已通过</c:if> 
		    <!-- 整体的 -->
		    <c:if test="${listEL.status=='2' and fn:length(listEL.status)==1}">已通过</c:if>
		    <c:if test="${listEL.status!='2' and fn:length(listEL.status)==1}">未通过</c:if> 
    	</display:column>		
    					
    </display:table>                
</logic:present>         

<c:if test="${isBeyondPrivilege}">
  <span stlye="color:read; font-size:15pt">
    ${beyondPrivilegeMsg}
  </span>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>