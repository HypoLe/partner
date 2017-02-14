<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<script type="text/javascript">
function deletePromoAg(id) {
	
      if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/deviceManagement/warningboard/warningboard.do",
					params:{method:"delete",id:id},
					success:function(res) {
						Ext.Msg.alert("提示：","删除成功！",function() {window.location.reload();});
					},
					failuer:function(res) {
						Ext.Msg.alert("提示：","删除失败！");
					}
				});
			}
}
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
	
	
	<html:form action="warningboard.do?method=list&&type=${type}" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>

					<td class="label">审核人:</td>
					<td>
		<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"   alt="allowBlank:false" readonly="readonly"/>
		<input type="hidden" name="auditIdStringEqual" id="auditIdStringEqual"  />
		<eoms:xbox id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
		  rootId="2" rootText="用户树"  valueField="auditIdStringEqual" handler="approvalUserName" 
		  textField="approvalUserName" checktype="user" single="true" />		
					</td>
					
					<td class="label">
					费用申请状态
	                </td>
		            <td class="content">
				    <select id="statusStringEqual" name="statusStringEqual">
				       <option selected="selected"/>
						
						<option value='1'>
							草稿
						</option>
						<option value='2'>
							审核中
						</option>
						<option value='3'>
							审核通过
						</option>
						<option value='4'>
							已归档
						</option>
					</select>
				</td>
			</tr>
					
				</tr>
			
				<tr>
					<td class="label">
						填报时间:
					</td>
					<td >
						<input type="text" class="text" name="greatTimeDateGreaterThan" onclick="popUpCalendar(this, this,null,null,null,true,-1)" />
					</td>                                                                                       
					
					<td class="label">
						项目名称:
					</td>
					<td >
						<input type="text" class="text" name="itemNameStringLike" />
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/deviceManagement/chargeFeeAppli/charge.do?method=list&&type=1'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>
	
	
</div>

<br/>

<!-- Information hints area end-->
<logic:present name="warningBoardList" scope="request">
	<display:table name="warningBoardList" cellspacing="0" cellpadding="0"
		id="warningBoardList" pagesize="${pagesize}"
		class="table warningBoardList" 
		requestURI="warningboard.do" sort="list"
		partialList="true" size="${size}">
     <c:if test="${type eq '1' }" var="return"> 
        <display:column media="html" title="${myTitleSelectBtn}">	
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${warningBoardList.id}" />
		</display:column>
        </c:if>

		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${warningBoardList.itemName}
		</display:column>
		
		
		<display:column sortable="true" headerClass="sortable" title="所属地市">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${warningBoardList.areaId}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
		        <eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoardList.auditId}"/>
		</display:column>

		<display:column sortable="true" headerClass="sortable" title="中继段名称">
			${warningBoardList.repeaterSection}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="中继段里程（公里）">
			${warningBoardList.repeaterSectionMileage}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="宣传牌数量（块）">
			${warningBoardList.warningBoardOldNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="计划宣传牌数量（块）">
			${warningBoardList.warningBoardNewNumber	}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="实际完成情况（块）">
			${warningBoardList.actualCompletion	}
		</display:column>
		<%---
		<display:column sortable="true" headerClass="sortable" title="完成时间">
			${warningBoardList.completionTime	}
		</display:column>--%>
		
		<c:if test="${type eq '1' }" var="return"> 
		<display:column sortable="false" headerClass="sortable" title="删除"
			 media="html">
			 <a href="javascript:void(0)" onclick="deletePromoAg('${warningBoardList.id}')">
				<img class="delete" src="${app }/images/icons/icon.gif" />
			</a>
		</display:column>
		</c:if>	
		
		<display:column sortable="true" headerClass="sortable" title="状态" >					 
					<c:if test="${1==(warningBoardList.status)}" >草稿</c:if>	
					<c:if test="${5==(warningBoardList.status)}" >驳回</c:if>	
					<c:if test="${2==(warningBoardList.status)}" >审核中</c:if>
					<c:if test="${3==(warningBoardList.status)}" >审核通过</c:if>			
					<c:if test="${4==(warningBoardList.status)}" >已归档</c:if>	
		</display:column>	
		<display:column  headerClass="sortable" title="操作">
					 <%--<c:if test="${(1<(warningBoardList.status))&&(5>(warningBoardList.status))}" > 
			---%><a id="${warningBoardList.id }"
				href="${app }/deviceManagement/warningboard/warningboard.do?method=goToDetail&id=${warningBoardList.id}&type=${type}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>	
			 <%--</c:if>---%>	
		</display:column>
		<display:column  headerClass="sortable" title="详情">
			 <%--<c:if test="${(1<(warningBoardList.status))&&(5>(warningBoardList.status))}" > 
			---%><a id="${warningBoardList.id }"
				href="${app }/deviceManagement/warningboard/warningboard.do?method=goToDetail&id=${warningBoardList.id}&type=7"
				>
				<img src="${app }/images/icons/table.gif">
				</a>	
			 <%--</c:if>---%>	
		</display:column>

		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
<%@ include file="/common/footer_eoms.jsp"%>
