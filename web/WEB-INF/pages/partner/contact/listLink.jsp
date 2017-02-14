<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="java.util.*;"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">

	
function openImport(handler){
	var el = Ext.get('infoContent');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开联系函具体信息界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭联系函具体信息界面";
	}
}
	


</script>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">联系函具体信息</span>
</div>

<div id="infoContent" 
		style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;"
	>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" >
				 主题
				</td>
				<td class="content" colspan="3">
					<c:if test="${contactMain.isUrgent==1}">加急：</c:if>${contactMain.subject }
				</td>
			</tr>
			<tr>
				<td class="label" >
				 编号
				</td>
				<td class="content" colspan="3">
					${contactMain.code }
				</td>
			</tr>
		 <tr>
			<td class="label"> 
				内容
			</td>
			<td class="content" colspan="3">
				<pre>${contactMain.content }</pre>			
			</td>
		</tr>
				<tr>	
			<td class="label" >
					发布人
					</td>
					<td class="content" >
					${contactMain.publisherName }
					</td>
			
					<td class="label" >
					 发布部门
					</td>
					<td class="content" >
						${contactMain.publisherDeptName}
					</td>
				
		</tr>
	    <tr>
			  <td class="label" >
					 审核人
				</td>	
				<td class="content"  >	
				   <div id="fieldset_names"  class="viewer-list">${contactMain.approverName }	</div>		
				</td>
			 <td class="label" >
				 发布范围
				</td>
				<td class="content"  >
					<fieldset id="fieldset_per" >
						<legend>发布到下列组织机构或人员 </legend> 
						<div id="fieldset_names_values"  class="viewer-list">${contactMain.publishedRangeName }</div>		
					</fieldset>
				</td>
		</tr>  
		<tr>
		<tr>
			<td class="label" >
				 处理期限
				</td>
				<td class="content"  >
				 	${fn:substring(requestScope.contactMain.publishTime, 0, 19)}
				 </td>	
			<td class="label" >
				 处理期限
				</td>
				<td class="content"  >
				 	${fn:substring(requestScope.contactMain.deathTime, 0, 19)}
				 </td>	
		</tr>
			
			<tr>
				<td class="label">
				 证书附件 
				</td>
				<td class="content" colspan="3" height="100px">
					<eoms:download ids="${requestScope.contactMain.file }"></eoms:download>
				</td>
		<tr>
		
		<tr>
				<td class="label"> 是否短信 </td>
				<td class="content">
						<c:if test="${contactMain.isSendSMS==1 }">
							<c:out value="是"></c:out>
						</c:if>
						<c:if test="${contactMain.isSendSMS==0}">
								<c:out value="否"></c:out>
						</c:if>
				</td>
				<td class="label"> 是否紧急</td>
				<td class="content">
						<c:if test="${contactMain.isUrgent==1 }">
							<c:out value="是"></c:out>
						</c:if>
						<c:if test="${contactMain.isUrgent==0 }">
							<c:out value="否"></c:out>
						</c:if>
				</td>
				
		<tr>
			
		</table>
		
</div><br>


  <span style="font-weight:bold;line-height:130%;">流转记录</span>
  <logic:notEmpty name="contactLinkList" scope="request">
   <display:table name="contactLinkList" cellspacing="0" cellpadding="0"
		id="listTemp" class="table list" 
		export="false" 
		sort="list" partialList="true" size="${resultSize}">
	<display:column title="处理人">
		<eoms:id2nameDB id="${listTemp.operateUserId}"	beanId="tawSystemUserDao" />	
	</display:column>
	<display:column title="处理时间">
		${fn:substring(listTemp.operateTime, 0, 19)}
	</display:column>
	<display:column title="所在环节">
		<c:if test="${listTemp.displayLinkname=='draft'}">创建</c:if>
		<c:if test="${listTemp.displayLinkname=='approver'}">审批</c:if>
		<c:if test="${listTemp.displayLinkname=='publishing'}">发布中</c:if>
		<c:if test="${listTemp.displayLinkname=='end'}">处理</c:if>
	</display:column>
	<display:column title="处理状态">
		 <c:if test="${listTemp.operateType=='0'}">存草稿</c:if>
		 <c:if test="${listTemp.operateType=='1'}">送审</c:if>
		 <c:if test="${listTemp.operateType=='2'}">
		    <c:if test="${listTemp.displayLinkname=='draft'}">发布</c:if>
		    <c:if test="${listTemp.displayLinkname=='approver'}">通过</c:if>
		 </c:if>
		 <c:if test="${listTemp.operateType=='3'}">转发</c:if>
		 <c:if test="${listTemp.operateType=='4'}">驳回</c:if>			    
         <c:if test="${listTemp.operateType=='8'}">阅知</c:if>	
		 <c:if test="${listTemp.operateType=='46'}">处理完成</c:if>
	</display:column>
	<display:column title="处理内容">
		<pre>${listTemp.remark}</pre>
	</display:column>
   </display:table>
</logic:notEmpty>   
<logic:empty name="contactLinkList" scope="request">
 没有数据
</logic:empty>	



<%@ include file="/common/footer_eoms.jsp"%>