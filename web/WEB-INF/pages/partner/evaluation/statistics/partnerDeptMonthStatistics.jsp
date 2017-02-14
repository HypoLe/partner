<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" href="${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/demos.css">
<link rel="stylesheet" type="text/css" href="${app}/nop3/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
function resetMsg(){
	jq(".text").val('');
	jq(".select").val('');
}
</script>
	
<form action="${app}/partner/evaluation/evaluStatAndQur.do?method=monthStatistics" 
	method="post" id="queryNormalConditionForm" name="queryNormalConditionForm"> 
	<fieldset>
	  <legend>请输入统计条件</legend>
	  <table id="sheet" class="listTable">
			<tr>						
				<td class="label">年<font color="red">*</font>：</td>
				<td>
				    <eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
					beanId="selectXML" cls="select" defaultId="${year}" alt="allowBlank:false,vtext:'年月必选！'" />
				</td>
				<td class="label">月<font color="red">*</font>：</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
					beanId="selectXML" cls="select" defaultId="${month}" alt="allowBlank:false,vtext:'年月必选！'"/>
				</td>											
			</tr>			
	    </table>
	</fieldset>
    <input type="submit" class="btn" value="统计" />
	 <input type="button" class="btn" value="重置" onclick="resetMsg();"  />

</form>	
<br/>	
<c:if test="${isBeyondPrivilege}">
  <span stlye="color:read; font-size:15pt">
    ${beyondPrivilegeMsg}
  </span>
</c:if>	
	
<logic:notEmpty name="resultList" scope="request">
    <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
     <thead>
	   <tr>
	      <th rowspan="1" colspan="1"> 代维公司 </th>	    
	      <th rowspan="1" colspan="1"> 代维办事处 </th>
	      <th rowspan="1" colspan="1"> 代维中心 </th>	        
	      <th rowspan="1" colspan="1"> 代维中心得分 </th>
	      <th rowspan="1" colspan="1"> 代维办事处得分 </th>
	      <th rowspan="1" colspan="1"> 代维公司得分</th>
	   </tr>
     </thead>
     <tbody>
        <c:forEach items="${resultList}" var="tdList">
          <tr>
           <c:forEach items="${tdList}" var="td" varStatus="status">
             <c:if test="${td.show}">             
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> 
                 <c:choose>
                  <c:when test="${status.index==0 or status.index==1 or status.index==2}"><eoms:id2nameDB id='${td.name}' beanId='partnerDeptDao'/></c:when>                  
                  <c:otherwise>
                     <c:choose>
                        <c:when test="${ empty td.href  or  td.href==''}"> 
                           ${td.name}
                        </c:when>
                        <c:otherwise>
                            <a  href="${app}${td.href}"
				            target="view" shape="rect">${td.name}</a>
                        </c:otherwise>
                     </c:choose>                                      
                  </c:otherwise> 
                 </c:choose>
                </td>
             </c:if> 
           </c:forEach>
          </tr> 
        </c:forEach>
     </tbody>
   </table>
            
</logic:notEmpty>     
<logic:empty  name="resultList" scope="request">
  没有记录
</logic:empty>    

<script type="text/javascript">
var myJ=jQuery.noConflict();

Ext.onReady(function(){             
	var v = new eoms.form.Validation({form:'queryNormalConditionForm'});
    v.custom = function(){            
        return true;
	};  		  
}); 
</script>
<%@ include file="/common/footer_eoms.jsp"%>		