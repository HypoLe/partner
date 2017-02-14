<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
  var myJ=jQuery.noConflict();
  var jq=jQuery.noConflict();
function addRule(){
       var aform=document.createElement("form");     
       aform.action="${app}/partner/feeManage/feeRule.do?method=gotoAdd";
       aform.method="post";
       document.body.appendChild(aform);
       aform.submit();
}

function delTmpl(prctmplid){
     if(confirm("确定删除")){
       //myJ("#delYN").val('true');  
       //myJ("#deltmplid").val(tmplid);  
       //因为display:table标签，要记忆前面的参数，第二次请求时要传递前面的参数； 所以此处采用先Ajax请求删除，再查询出列表数据
       Ext.Ajax.request({
	 	            url: '${app}/partner/feeManage/feeRule.do?method=delRule',  
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
</script>

<br/>
<form action="${app}/partner/feeManage/feeRule.do?method=gotoList" method="post" id="tmplListConditionForm">
</form>
<input type="button" value="新增规则" onClick="javascript:addRule();"/>
<br/><br/>
<logic:present name="list" scope="request">
    <display:table name="list" cellspacing="0" cellpadding="0"
		id="listEL"  class="table list" 
		export="false"  partialList="true" size="${fn:length(list)}">
		<display:column property="name"  title="规则名称"  />
		<display:column title="创建人" >
			 <eoms:id2nameDB id="${listEL.creatorid}" beanId="tawSystemUserDao" />
		</display:column>				
		<display:column title="创建时间" >
		  <fmt:formatDate value="${listEL.crdttm}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
		</display:column> 
		<display:column  title="备注" >
			<pre>${listEL.remark}</pre>
		</display:column>
			
		<!-- 查看、修改在新页面，删除后依旧返回列表 -->		
		<display:column  title="查看">
		    <a  href="${app}/partner/feeManage/feeRule.do?method=viewRule&id=${listEL.id}"
				target="view" shape="rect"> 
				<img src="${app}/images/icons/search.gif" style="cursor:hand;display:block" /> 
			</a>
		</display:column>	
    	<display:column  title="修改">    
		    <a  href="${app}/partner/feeManage/feeRule.do?method=editRule&id=${listEL.id}"
				target="modify" shape="rect"> 
				<img src="${app}/images/icons/edit.gif" style="cursor:hand;display:block" /> 
			</a>
    	</display:column>	
    	<display:column  title="删除">
				<img src="${app}/images/icons/delete.gif" style="cursor:hand;display:block" onClick="javascript:delTmpl('${listEL.id}');" /> 		        	
    	</display:column>			
    </display:table>                
</logic:present>         

<br/>

<%@ include file="/common/footer_eoms.jsp"%>