<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div id="div">
<logic:notEmpty name="list" scope="request">

    <span style="font-weight:bold;color:#FF0000">
        费用申请明细:
    </span>
     <display:table name="list" cellspacing="0" cellpadding="0"
		id="listEL"  class="table list" 
		export="false"  partialList="true" size="${fn:length(list)}">
		<display:column  title="申请单编号">
		    <a  href="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationView&feeApplicationId=${listEL.feeAplId}"
				target="view" shape="rect"> 
				${listEL.aplBillCd }
			</a>
		</display:column>		
		<display:column  title="申请人">
		     <eoms:id2nameDB id="${listEL.creatorId}" beanId="tawSystemUserDao" />
		</display:column>	
		<display:column property="creatdtTm" title="申请时间" />
		<display:column title="状态" >	
		   通过<!--编辑时都是已经通过了的申请费用  -->
		</display:column>
		<display:column property="year"  title="作用年"  />
		<display:column property="month"  title="作用月"  />
		<display:column  title="费用类型" >
		  <input type="hidden" name="costType" value="${listEL.costType}" />	
		  <c:if test="${listEL.costType eq '1'}">奖励</c:if>
		  <c:if test="${listEL.costType eq '2'}">补贴</c:if>
		  <c:if test="${listEL.costType eq '3'}">报销</c:if>
		  <c:if test="${listEL.costType eq '4'}">其他</c:if>
		</display:column>
		<display:column property="costVoucher" title="款项依据"  />	
		<display:column property="shdMnyAmt" title="应付款"  />
		<display:column property="realMnyAmt" title="实付款"  />
    </display:table>                      
</logic:notEmpty>    
</div>
<c:if test="${empty list}">没有记录。</c:if>
<%@ include file="/common/footer_eoms.jsp"%>