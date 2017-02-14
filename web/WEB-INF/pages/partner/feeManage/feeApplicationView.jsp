<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
 function getOs() 
   { 
    //var OsObject = "";  
    if(navigator.userAgent.indexOf("MSIE")>0) { 
         return "MSIE"; 
    } 
    if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
         return "Firefox"; 
    } 
    if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
         return "Safari"; 
    }  
    if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
         return "Camino"; 
    } 
    if(isMozilla=navigator.userAgent.indexOf("Gecko")>0){ 
         return "Gecko"; 
    }    
  } 
</script>

<table id="applicationDtlTable" name="applicationDtlTable" class="formTable">	
	      <tr><td colspan="8"><font style="color:red;font-weight:bold;">报销信息</font></td></tr>
	      <tr>	    
			<td class="label">
				报销单号
			</td>
			<td>		   
				${feeApplication.aplBillCd}
			</td>	     
			
			<td class="label">申请人</td>
			<td>
				<eoms:id2nameDB id="${feeApplication.applicant}" beanId="tawSystemUserDao" />
			</td>   	   
			
			<td class="label">年</td>
			<td>
				 ${feeApplication.year}
			</td>
			<td class="label">月</td>
			<td>
				${feeApplication.month}
			</td>				
	    </tr>
	    
	    <tr>	       		
			<td class="label">
				金额
			</td>
			<td>		   
				${feeApplication.mnyAmt}
			</td>	     
			
			<td class="label">状态</td>
			<td>${feeApplication.status }</td>   	   
			
			<td class="label">申请时间</td>
			<td>
				 ${fn:substring(feeApplication.crDtTm,0,19)}
			</td>
			<td class="label">备注</td>
			<td>
				<pre>${feeApplication.remark}</pre>
			</td>	
		</tr>					
</table><br/>	
		
<form id="feeApplicationForm" name="feeApplicationForm" 
	action=""  method="post">	
<span style="color:#FF0000;font-weight:bold;">
明细列表
</span> 	
   <table   class="table protocolMainList"  name="applicationDtlListTable" id="applicationDtlListTable">
     <thead>
	   <tr>
	      <th > 专业 </th>
	      <th > 代维机构 </th>	      
	      <th width="50">  年 </th>
	      <th width="50">  月 </th>
	      <th > 费用类型</th>
	      <th width="80"> 金额 </th>
	      <th > 款项依据 </th>
	   </tr>
     </thead>
     <tbody name="applicationDtlListBody" id="applicationDtlListBody">
       <tr hidden="true" name="cloneSrcRow" id="cloneSrcRow" style='display:none;'>
          <td > 专业<input type="hidden" /> </td>
	      <td > 代维机构<input type="hidden" /> </td>	      
	      <td width="50"> <input type="text" style="width:50;border:none;background:none;" value="2012"/>  </td>
	      <td width="50"> <input type="text" style="width:50;border:none;background:none;" value="12"/>  </td>
	      <td > 费用类型<input type="hidden" /></td>
	      <td width="80"> <input type="text" style="width:80;border:none;background:none;"/> </td>
	      <td > 
	        <textarea  class="text medium" ></textarea>
	      </td>
       </tr>    
       <logic:notEmpty  name="feeApplication" scope="request">
         <c:forEach items="${feeApplicationDtlList}" var="feeApplicationDtl" varStatus="status">            
           <tr> 
          		<td><input type="hidden" name="majorval" value="${feeApplicationDtl.majorid}" readonly=""><eoms:id2nameDB id="${feeApplicationDtl.majorid}" beanId="ItawSystemDictTypeDao" /></td>
	      		<td><input type="hidden" name="compval" value="${feeApplicationDtl.compid}" readonly=""><eoms:id2nameDB id='${feeApplicationDtl.compid}' beanId='partnerDeptDao'/></td>	      
	      		<td width="50"> <input type="text" value="${feeApplicationDtl.year}" style="width:50;border:none;background:none;" name="yearval" readonly="">  </td>
	     		<td width="50"> <input type="text" value="${feeApplicationDtl.month}" style="width:50;border:none;background:none;" name="monthval" readonly="">  </td>
	      		<td><input type="hidden" name="costtypeval" value="${feeApplicationDtl.costTypeCd}" readonly="">
					<c:if test="${feeApplicationDtl.costTypeCd eq '1'}">奖励</c:if>
					<c:if test="${feeApplicationDtl.costTypeCd eq '2'}">补贴</c:if>
					<c:if test="${feeApplicationDtl.costTypeCd eq '3'}">报销</c:if>
					<c:if test="${feeApplicationDtl.costTypeCd eq '4'}">其他</c:if> 
                </td>
	      		<td width="80"> <input type="text" style="width:80;border:none;background:none;" name="mnyamtval" readonly="" value="${feeApplicationDtl.mnyAmt}"> </td>
	      		<td> 
	       		   ${feeApplicationDtl.costVoucher}
	      		</td>	      		
       		</tr>
          </c:forEach>
       </logic:notEmpty>      
     </tbody>
   </table>
   
   <logic:notEmpty  name="feeTaskWkflList" scope="request">
      <br/>
      <span style="color:#FF0000;font-weight:bold;"> 申请单流转情况</span> 
      <table   class="table protocolMainList" >
     	<thead>
	   	  <tr>
	       <th>审批流程</th>
	       <th>状态 </th>	      
	       <th>处理时间</th>
	       <th>审批人</th>
	       <th>审批意见</th>	      
	     </tr>
        </thead>
        <tbody>
           <c:forEach items="${feeTaskWkflList}" var="feeTaskWkfl" varStatus="status">            
           <tr> 
          		<td> ${feeTaskWkfl.taskName}</td>	      
          		<td> ${feeTaskWkfl.taskStatus}</td>	      
          		<td> ${fn:substring(feeTaskWkfl.maintaintDtTm,0,19)}</td>	      
          		<td> <eoms:id2nameDB id="${feeTaskWkfl.excutorId}" beanId="tawSystemUserDao" /></td>	      
          		<td><pre> ${feeTaskWkfl.advs}</pre></td>	      		
       		</tr>
          </c:forEach>
        </tbody>
      </table>  
   </logic:notEmpty>  
</form>

<script type="text/javascript">
   var jq=jQuery.noConflict();
     
     
   Ext.onReady(function(){     
      var navg=getOs();
      /*if(navg=='MSIE'){ 
         var cloneSrcRow=document.getElementById("cloneSrcRow"); 
         //cloneSrcRow.setAttribute('style','display:none;');       
         var attr=document.createAttribute('style'); 
         cloneSrcRow.setAttributeNode(attr);
         cloneSrcRow.setAttribute('style','display:none;');  //直接在html代码处都加上属性hidden,style
         alert('MSIE'); 
      }*/
      if(navg=='MSIE'){
          var cloneSrcRow=document.getElementById("cloneSrcRow"); 
          cloneSrcRow.removeAttribute('hidden');
      }
      else{//'Firefox'
          var cloneSrcRow=document.getElementById("cloneSrcRow"); 
          cloneSrcRow.removeAttribute('style');
      } 
   });
</script>

<%@ include file="/common/footer_eoms.jsp"%>
