<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>


<%@ include file="/common/taglibs.jsp"%>
<%
 String taskName = com.boco.eoms.base.util.StaticMethod.nullObject2String(request.getAttribute("taskName"));
 String dealPerformer = (String)request.getAttribute("dealPerformer");
 String dealPerformerLeader = (String)request.getAttribute("dealPerformerLeader");
 String dealPerformerType = (String)request.getAttribute("dealPerformerType");
 %>

    <input type="hidden" name="beanId" value="iPnrComplaintMainManager"/>
    <input type="hidden" name="mainClassName" value="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintMain"/>	
    <input type="hidden" name="linkClassName" value="com.boco.eoms.partner.sheet.pnrcomplaint.model.PnrComplaintLink"/> 
 	<input type="hidden" name="processTemplateName" id="processTemplateName" value="PnrComplaint" />
    <input type="hidden" name="operateName" id="operateName" value="nonFlowOperate" />
    <input type="hidden" name="dealPerformer" value="${dealPerformer}"/> 
    <input type="hidden" name="dealPerformerLeader" value="${dealPerformerLeader}"/> 
    <input type="hidden" name="dealPerformerType" value="${dealPerformerType}"/> 
<%@ include file="/WEB-INF/pages/wfworksheet/pnrcomplaint/baseinputlinkhtmlnew.jsp"%>
     <table  class="formTable">
	</table>
<%} %>

