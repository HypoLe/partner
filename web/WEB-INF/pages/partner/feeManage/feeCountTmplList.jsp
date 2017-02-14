<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
  //没有如下代码,将报错 ext.js:TypeError: $(this.showChkFldId).update is not a function
  var myJ=jQuery.noConflict();
  var jq=jQuery.noConflict();
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
  
  function delTmpl(prctmplid){
     if(confirm("确定删除")){
       //myJ("#delYN").val('true');  
       //myJ("#deltmplid").val(tmplid);  
       //因为display:table标签，要记忆前面的参数，第二次请求时要传递前面的参数； 所以此处采用先Ajax请求删除，再查询出列表数据
       Ext.Ajax.request({
	 	            url: '${app}/partner/feeManage/feeCountManage.do?method=feeCountTmpldel', 
                    params:{   
       	  				prctmplid:prctmplid        	  				
       				}, 
       				success:function(response,options){         				             				    			   
       					  myJ("#tmplListConditionForm").get(0).submit();
       					  Ext.Msg.alert('提示', '成功删除.'); 
      				}
	 	        });     
     }else{
       return false;
     }
  }
  
  function addTmpl(){
    //var ahref=document.createElement("a");     
    // ahref.href="${app}/partner/feeManage/evaluFee.do?method=evalu_feeprctmpl";
    // ahref.click();
       var aform=document.createElement("form");     
       aform.action="${app}/partner/feeManage/feeCountManage.do?method=goToFeeCountDetailTmpl2";
       aform.method="post";
       document.body.appendChild(aform);
       aform.submit();
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
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="${app}/partner/feeManage/feeCountManage.do?method=tmplList" method="post" id="tmplListConditionForm">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">模板名称</td>
				<td><input class="text" type="text" name="tmplName" value="${tmplName }"/></td>
				<td class="label">专业</td>
				<td>
					<eoms:comboBox name="specialty" id="specialty" 
					initDicId="11225" alt="allowBlank:false" styleClass="select" defaultValue="${majorid}"/>
				</td>
			</tr>			
	    </table>
		<input type="submit" styleClass="btn" value="查询" />&nbsp;&nbsp;&nbsp;	
		<input type="button" styleClass="btn" value="清空" onClick="clearForm(jq('form#tmplListConditionForm'));"/>	  
	</form>
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>
         
        
        
<logic:present name="tmplList" scope="request">
    <display:table name="tmplList" cellspacing="0" cellpadding="0"
		id="tmplList" pagesize="${pagesize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/feeManage/feeCountManage.do?method=tmplList"
		sort="list" partialList="true" size="${size}">
		<display:column property="prcTmplNm"  title="模板名称"  />
				
		<display:column   title="专业" property="majorName"  />
			<display:column title="创建人" >
			 <eoms:id2nameDB id="${tmplList.creatorId}" beanId="tawSystemUserDao" />
		</display:column>
		<display:column title="创建时间" >
		  <fmt:formatDate value="${tmplList.createdtTm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column>
		<display:column  title="备注" >
			<pre>${tmplList.remark}</pre>
		</display:column>
	
		<!-- 查看、修改在新页面，删除后依旧返回列表 -->		
		<display:column  title="查看">
		    <a  href="${app}/partner/feeManage/feeCountManage.do?method=goToFeeCountDetailTmplView&id=${tmplList.id}"
				target="view" shape="rect"> 
				<img src="${app}/images/icons/search.gif" style="cursor:hand;display:block" /> 
			</a>
		</display:column>	
    	<display:column  title="修改">    
		    <a  href="${app}/partner/feeManage/feeCountManage.do?method=goToEditeFeeCountDetailTmpl2&id=${tmplList.id}"
				target="modify" shape="rect"> 
				<img src="${app}/images/icons/edit.gif" style="cursor:hand;display:block" /> 
			</a>
    	</display:column>
    	<display:column  title="删除">
				<img src="${app}/images/icons/delete.gif" style="cursor:hand;display:block" onClick="javascript:delTmpl('${tmplList.id}');" /> 		        	
    	</display:column>						
    </display:table>                
</logic:present>         

<input type="button" value="新增模板" onClick="javascript:addTmpl();"/>
<%@ include file="/common/footer_eoms.jsp"%>
