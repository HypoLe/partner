<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var myJ=jQuery.noConflict();

function openSearch(handler){
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
};
   
function displaySelect(selectId,theValue){
      var all_options = document.getElementById(selectId).options;
      for (i=0; i<all_options.length; i++){
        if (all_options[i].value == theValue)  // 根据option标签的ID来进行判断  测试的代码这里是两个等号
        {
         all_options[i].selected = true;
        }
      }
}
    
Ext.onReady(function(){
	displaySelect('feecatcdStringEqual','${feecatcdStringEqual}');	  
	displaySelect('yearStringEqual','${yearStringEqual}');	
	displaySelect('monthStringEqual','${monthStringEqual}');	
	displaySelect('statusStringEqual','${statusStringEqual}');		
});    
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openSearch(this);">快速查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	
<form  action="feeManage.do?method=goToBaseEntityList" method="post" id="queryForm1">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
			<tr>
			    <td class="label">区域:</td>
				<td>
				 <input type="text" class="text" name="areaName" id="areaName" readonly="true" 
				 	value="<eoms:id2nameDB id='${areaid}' beanId='tawSystemAreaDao'/>"/>
				 <input type="hidden" id="areaid" name="areaid" value="${areaid}"/>
				  <eoms:xbox id="areaTree" dataUrl="${app}/xtree.do?method=areaTree"  
					rootId="${sessionScope.sessionform.rootAreaId}" rootText="${sessionScope.sessionform.rootAreaName}" 
					valueField="areaid" handler="areaName" 
					textField="areaName" single="true">
				  </eoms:xbox>
				</td>
				<td class="label">费用类型：</td>
		         <td class="content">
				    <select id="feecatcdStringEqual" name="feecatcdStringEqual" value="${feecatcdStringEqual }">
				      
				       <option value="">全部</option>
						<option value='1'>
							月度考核费用
						</option>
						<option value='2'>
							按月计次服务费用
						</option>
						<option value='3'>
							月度其他代维费用
						</option>
					</select>
				</td>
				<td class="label">年份:</td>
			   <td class="content">
				<eoms:dict key="dict-partner-inspect" selectId="yearStringEqual" dictId="yearflag" 
					beanId="selectXML" cls="select" defaultId="${yearStringEqual }" alt="allowBlank:true" />
			   </td>
			<td class="label">月份：</td>
			<td class="content">
			  <eoms:dict key="dict-partner-inspect" selectId="monthStringEqual" dictId="monthflag" 
					beanId="selectXML" cls="select"  defaultId="${monthStringEqual }" alt="allowBlank:true"/>
			</td>
	  </tr>
			<tr>
			<td class="label">代维公司:</td>
				<td >
				  <input type="text" id="compname" name="compname"  class="text" readonly="true" value="${compname}" />
				  <input type="hidden" id="compid" name="compid" value="${compid}"/>
				  <eoms:xbox id="compTarget" dataUrl="${app}/xtree.do?method=userFromComp&popedom=false"
					rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="compid"
					handler="compname" textField="compname" checktype="dept" single="true">
				  </eoms:xbox>
		   
				</td>                                  	
				<td class="label">专业:</td>
				<td>
					<eoms:comboBox name="majoridStringEqual" id="majorid" 
					initDicId="11225" alt="allowBlank:false" styleClass="select" defaultValue="${majoridStringEqual}"/>
				</td>
				<td class="label">状态:</td>
				<td >
				 <select id="statusStringEqual" name="statusStringEqual" value="${statusStringEqual }">
				      
				       <option value="">全部</option>
						<option value='0'>
							未审批
						</option>
						<option value='1'>
							已审批
						</option>
					</select>
				</td>
				
				</tr>
			</table>
		</fieldset>
		<input type="button" styleClass="btn" value="清空" onClick="javascript:clearForm(myJ('#queryForm1'));"/>&nbsp;&nbsp;&nbsp;
		<html:submit   property="method.save"  
	        styleId="method.save" value="查询"  ></html:submit>	 
	</form>
	
	
	
</div>

<br/>

<c:if test="${isBeyondPrivilege}">
  <span stlye="color:read; font-size:15pt">
    ${beyondPrivilegeMsg}
  </span>
</c:if>

<!-- Information hints area end-->
<logic:present name="baseEntityList" scope="request">
	<display:table name="baseEntityList" cellspacing="0" cellpadding="0"
		id="baseEntityList" pagesize="${pagesize}"
		class="table baseEntityList" 
		requestURI="feeManage.do" sort="list"
		partialList="true" size="${size}">

		<display:column sortable="true" headerClass="sortable" title="区域">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${baseEntityList.areaid}"/>
		</display:column>
				
		<display:column sortable="true" headerClass="sortable" title="费用类型">
		${baseEntityList.feecat}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="月份">
		${baseEntityList.year}年${baseEntityList.month}月
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${baseEntityList.compid}" beanId="partnerDeptDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="专业">
		<eoms:id2nameDB id="${baseEntityList.majorid}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="实付款">
		${baseEntityList.realmnyamt}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="上报人员">
		        <eoms:id2nameDB beanId="tawSystemUserDao" id="${baseEntityList.creatorid}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="上报时间">
		  ${fn:substring(baseEntityList.creatdttm,0,19)}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="状态">
		<c:if test="${baseEntityList.status=='0'}">未审批</c:if>
		<c:if test="${baseEntityList.status=='1'}">已审批</c:if>
		</display:column>
		
		
	 <display:column  headerClass="sortable" title="详情">
	 <c:if test="${baseEntityList.feecatcd=='1'}">
	 
		<a target="blank" shape="rect"
				href="${app }/partner/feeManage/evaluFee.do?method=evalu_feeentity_view&evalu_entityid=${baseEntityList.ownentityid}&feePoolMainId=${baseEntityList.ownmainentityid}"
				>
			<img src="${app }/images/icons/table.gif">
				</a>	
	
	 </c:if>
	 <c:if test="${baseEntityList.feecatcd=='2'}">
	 
		<a target="blank" shape="rect"
				href="${app }/partner/feeManage/feeCountManage.do?method=goToViewFeeCountEntity&id=${baseEntityList.ownentityid}&feePoolMainId=${baseEntityList.ownmainentityid}"
				>
			<img src="${app }/images/icons/table.gif">
				</a>	
	
	 </c:if>
	 
	 <c:if test="${baseEntityList.feecatcd=='3'}">
	 
		<a target="blank" shape="rect"
				href="${app }/partner/feeManage/monthOtherPay.do?method=goToShowMonthOtherPay&id=${baseEntityList.ownentityid}"
				>
			<img src="${app }/images/icons/table.gif">
				</a>	
	
	 </c:if>
		</display:column> 

		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
<%@ include file="/common/footer_eoms.jsp"%>
