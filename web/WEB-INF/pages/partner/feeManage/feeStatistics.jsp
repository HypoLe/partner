<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script type="text/javascript"> 
 var jq=jQuery.noConflict();


function clearFeeStatisticsForm(){
  
   clearForm(jq('#feeStatisticsForm'));
}
   
		window.onload = function()
			{	
			var ye='${year}';
			var mon='${month}';
			document.getElementById("year").value=ye;
			document.getElementById("month").value=mon;					
			}
									
   function clearForm(form) {
  // iterate over all of the inputs for the form
  // element that was passed in
  jq(':input', form).each(function() {//  jQueryjQuery(expression, [context]) expression:传递一个表达式（通常由 CSS 选择器组成）; context 可选:作为待查找的 DOM 元素集、文档或 jQuery 对象。 
    var type = this.type;
    var tag = this.tagName.toLowerCase(); // normalize case
    // it's ok to reset the value attr of text inputs,
    // password inputs, and textareas
    if (type == 'text' ||type == 'hidden'|| type == 'password' || tag == 'textarea')
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
</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>
<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">

<form  action="feeManage.do?method=feeStatistics" method="post" id="feeStatisticsForm">
			<table id="sheet" class="formTable">
			<tr>
			  
				<td class="label">年份:</td>
			   <td class="content"><select size='1'
				name='year' id='year' 
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011' >2011年</option>
				<option value='2012' >2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月份：</td>
			<td class="content"><select size='1' name="month"
				id="month" class='select' >
				<option value=''>请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
			 </tr><tr>
			<td class="label">费用类型：</td>
		         <td class="content">
				    <select id="feecatcd" name="feecatcd">
				      
				       <option value="">全部</option>
					<!-- 	<option value='1'>
							月度考核费用
						</option> -->
						<option value='2'>
							按月计次服务费用
						</option>
						<option value='3'>
							月度其他代维费用
						</option>
					</select>
				</td>
				                               	
				<td class="label">专业:</td>
				<td>
					<eoms:comboBox name="major" id="major" 
					initDicId="11225" alt="allowBlank:false" styleClass="select" />
				</td>
	  </tr>  <tr>
	     <td class="label">代维公司 </td>
			<td class="content">
				<input type="text" id="compName" name="compName" class="text" readonly="true" />
				<input type="hidden" id="comp" name="comp" />
				<eoms:xbox id="compTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
					rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="comp"
					handler="compName" textField="compName" checktype="dept"
					single="true">
				</eoms:xbox>
			</td>   
			<td class="label">区域</td>
		<td><input type="text" class="text" name="areaName" id="areaName" readonly="true" 
				 value="<eoms:id2nameDB id='${areaId}' beanId='tawSystemAreaDao'/>"/>
			<input type="hidden" id="areaId" name="areaId" value="${areaId}"/>
		</td>
	         <eoms:xbox id="areaTree" dataUrl="${app}/xtree.do?method=areaTree"  
	rootId="${sessionScope.sessionform.rootAreaId}" rootText="${sessionScope.sessionform.rootAreaName}" 
	valueField="areaId" handler="areaName" 
	textField="areaName" single="true">
</eoms:xbox>   
	     </tr>
			
			</table>
		<html:submit   property="method.save"  
	        styleId="method.save" value="查询"  ></html:submit>	
	        &nbsp;&nbsp;&nbsp; 
	      <input type="button"  value="清空" onClick="clearFeeStatisticsForm();"/>
	</form>
	
</div>

<div>
<table id="sheet" class="formTable">
     	<thead>
	 <tr >
	 <logic-el:present name="headList">
	 <c:forEach items="${headList}"  var="headlist" >
	 <td class="label">
	 ${headlist}
	 </td >
	 
	 </c:forEach>
	 
     </logic-el:present>
     </tr>
     </thead>

     <logic-el:present name="tableMap">
     <tbody>
     <c:forEach items="${tableMap}" var="tableList">
     <c:forEach items="${tableList.value}" var="tdList">
     <tr>
     <c:forEach items="${tdList}" var="td">
    <c:if test="${td.show}">
     <td rowspan="${td.rowspan} }">
  
     <c:if test="${!empty td.url}">
     <a href="javascript:void(0);" onclick="window.open('${app}${td.url}');">
     	<img src="${app }/images/icons/table.gif">
     </a>
     </c:if>
     <c:if test="${empty td.url}">
      ${td.name}
     </c:if>
    
     </td>
      </c:if>
     </c:forEach>
     </tr>
     </c:forEach>
     
     <tr><td class="label">合计</td><td>${tableList.key}</td></tr>
     </c:forEach>
	  </tbody>
	  </logic-el:present>
 </table>
</div>
<%@ include file="/common/footer_eoms.jsp"%>