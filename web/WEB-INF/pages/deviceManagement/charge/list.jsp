<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<script type="text/javascript">
function deleteFeeAppli(id) {
	
      if(confirm("确定要删除吗？")){
				Ext.Ajax.request({
					url:"${app}/deviceManagement/chargeFeeAppli/charge.do",
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
	
	<html:form action="charge.do?method=list" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
					
			<input type="hidden"  name="Type" value="1" />
			
				<tr>

					<td class="label">费用申请类别:</td>
					<td>
							<input type="text" class="text" name="feeApplicationTypeStringLike"/>
					</td>
					
					<td class="label">
					费用申请状态
	                </td>
		            <td class="content">
				    <select id="feeApplicationStatusStringEqual" name="feeApplicationStatusStringEqual">
				    
						<option value='1' selected="selected">
							草稿
						</option>
						<option value='2'>
							一级审批中
						</option>
						<option value='3'>
							二级审批中
						</option>
						<option value='4'>
							审批通过
						</option>
						<option value='5'>
							已归档
						</option>
					</select>
				</td>
			</tr>
					
				</tr>
			
				<tr>
					<td class="label">
						费用申请时间:
					</td>
					<td >
						<input type="text" class="text" name="feeApplicationGreatTimeDateGreaterThan" onclick="popUpCalendar(this, this,null,null,null,true,-1)" />
					</td>                                                                                       
					
					<td class="label">
						费用申请名称:
					</td>
					<td >
						<input type="text" class="text" name="feeApplicationNameStringLike" />
					</td>
				</tr>
				
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="确认查询" />
							&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/deviceManagement/chargeFeeAppli/charge.do?method=list&&Type=1'"/>
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</html:form>
	
	
</div>

<br/>
 


	
	

<!-- Information hints area end-->
<logic:present name="feeApplicationMainList" scope="request">
	<display:table name="feeApplicationMainList" cellspacing="0" cellpadding="0"
		id="feeApplicationMainList" pagesize="${pagesize}"
		class="table feeApplicationMainList" export="true"
		requestURI="charge.do" sort="list"
		partialList="true" size="${size}">
		

		
		<display:column sortable="true" headerClass="sortable" title="申请人">
			${feeApplicationMainList.feeApplicationUser}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请部门">
			${feeApplicationMainList.feeApplicationDept}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${feeApplicationMainList.feeApplicationCity}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请日期">
			${feeApplicationMainList.feeApplicationGreatTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="费用类型">
			${feeApplicationMainList.feeApplicationType}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="费用金额">
			${feeApplicationMainList.feeApplicationMoney}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="状态" >					 
					<c:if test="${1==(feeApplicationMainList.feeApplicationStatus)}" >草稿</c:if>		
					<c:if test="${2==(feeApplicationMainList.feeApplicationStatus)}" >一级审批中</c:if>
					<c:if test="${3==(feeApplicationMainList.feeApplicationStatus)}" >二级审批中</c:if>			
					<c:if test="${4==(feeApplicationMainList.feeApplicationStatus)}" >审批通过</c:if>
					<c:if test="${5==(feeApplicationMainList.feeApplicationStatus)}" >已归档</c:if>		
		</display:column>	
		
		
		
		
		<display:column  headerClass="sortable" title="详情">
			 
			 <c:if test="${1==(feeApplicationMainList.feeApplicationStatus)}" > 
			 
			<a id="${feeApplicationMainList.id }"
				href="${app }/deviceManagement/chargeFeeAppli/charge.do?method=goToEdit&id=${feeApplicationMainList.id}"
				 >
				<img src="${app }/images/icons/edit.gif">
				</a>
			</c:if>
				
			 <c:if test="${(1<(feeApplicationMainList.feeApplicationStatus))&&(5>(feeApplicationMainList.feeApplicationStatus))}" > 
			<a id="${feeApplicationMainList.id }"
				href="${app }/deviceManagement/chargeFeeAppli/charge.do?method=goToDetail&id=${feeApplicationMainList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/search.gif">
				</a>	
			</c:if>	
		</display:column>

	
	
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
</div>




<%@ include file="/common/footer_eoms.jsp"%>
