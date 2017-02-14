<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" 	import="java.util.*;"%>
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
					${contactMain.subject }
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
				${contactMain.content }			
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
		
</div>		
		 <br>
		<table    style="width:800" 	class="formTable"  >
               <tr>
                    <td>处理人</td>   			       
			        <td>处理时间</td>
			        <td>所在环节</td>
			        <td>处理状态</td>
			        <td>处理结果</td>
			        <td>处理意见</td>
		       </tr>
           <c:forEach   begin="0"  step="1" varStatus="status" var="listTemp" items="${contactLinkList}">
               <tr>
                    <td>  		<eoms:id2nameDB id="${listTemp.operateUserId}"	beanId="tawSystemUserDao" />			       
			        </td>
			         <td>    	${listTemp.operateTime}		       
			        </td>
			         <td>    	
				        <c:if test="${listTemp.displayLinkname=='draft'}">草稿</c:if>
				    	<c:if test="${listTemp.displayLinkname=='approver'}">审批</c:if>
				    	<c:if test="${listTemp.displayLinkname=='publishing'}">处理</c:if>
				    	<c:if test="${listTemp.displayLinkname=='end'}">终止</c:if>
				    	<c:if test="${listTemp.displayLinkname=='4'}">驳回</c:if>			       
			        </td>
			         <td>   
					    <c:if test="${listTemp.operateType=='0'}">草稿</c:if>
				    	<c:if test="${listTemp.operateType=='1'}">送审</c:if>
				    	<c:if test="${listTemp.operateType=='2'}">发布</c:if>
				    	<c:if test="${listTemp.operateType=='3'}">转发</c:if>
				    	<c:if test="${listTemp.operateType=='4'}">驳回</c:if>
				    	<c:if test="${listTemp.operateType=='46'}">处理完成</c:if>
				    	<c:if test="${listTemp.operateType=='8'}">阅知</c:if>			       
			        </td>
			         <td>   
			           <c:if test="${listTemp.displayLinkname=='approver'}">
				            <c:if test="${listTemp.effect=='0'}">驳回</c:if>
					    	<c:if test="${listTemp.effect=='1'}">审核通过</c:if>
				       </c:if>	
				       	<c:if test="${listTemp.displayLinkname=='publishing'}">
				       	    <c:if test="${listTemp.effect=='0'}">转发</c:if>
					    	<c:if test="${listTemp.effect=='1'}">处理完成</c:if>
				       	</c:if>
				    	<c:if test="${listTemp.effect=='2'}">阅知</c:if>
					       
			        </td>
			        <td>   
					         ${listTemp.remark}
			        </td>
		       </tr>
		    </c:forEach>
		</table>
		<br>
     <form method="post" id="form1" action="contact.do?method=searchForLink&type=${type}&mainId=${contactMain.id}&pageName=listLink ">
		  <table style="border:0"  width="800">
		    <tr>
		    <td  height="57"  align="left"><span>找到${resultSize}条数据，以下是${first }到${end }条 </span>
			<td >
			<td  align="right">
			  <div align="right"><span align="right">
			    <a href="contact.do?method=searchForLink&type=${type}&pageIndex=1&mainId=${contactMain.id}&pageName=listLink" ><img src="${app}/partner/contact/images/back1.gif"></a>
			    <a href="contact.do?method=searchForLink&type=${type}&pageIndex=${pageIndex-1 }&mainId=${contactMain.id}&pageName=listLink"  ><img src="${app}/partner/contact/images/back.gif"></a> 
			    ${pageIndex}/${pages } 
			    <a href="contact.do?method=searchForLink&type=${type}&pageIndex=${pageIndex+1 }&mainId=${contactMain.id}&pageName=listLink"><img src="${app}/partner/contact/images/go.gif"></a>
			    <a href="contact.do?method=searchForLink&type=${type}&pageIndex=${pages }&mainId=${contactMain.id}&pageName=listLink" ><img src="${app}/partner/contact/images/go1.gif"></a> 跳到
			    <input type="text" name="pageIndex" size="2" />
			    页
			    <input name="submit" type="submit" value="确认"/>
			    </span>
		        </div></td>
			</tr>
		</table>
</form>
	<br><br>
<%@ include file="/common/footer_eoms.jsp"%>