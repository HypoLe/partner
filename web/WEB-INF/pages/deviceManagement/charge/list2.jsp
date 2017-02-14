<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<script type="text/javascript">
function dealAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";"
		}
	}
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		if(confirm("确定要全部处理吗？")){
		
		var txt=document.getElementById("operateOpinion"); 
        if (txt.value=="") 
        { 
            alert('请填写审批意见'); 
            txt.focus(); 
            return false;
        }     
		document.getElementById("dealIds").value=idResult.toString();
		var formObj = document.getElementById("dealAllForm");
 		formObj.submit();
		}
		else{
			return false;
		}
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
			
				<input type="hidden"  name="Type" value="2" />
				<tr>
					
					<td class="label">
						费用申请编号:
					</td>
					<td>
						<input type="text" class="text" name="feeApplicationIDStringLike"/>
					</td>
					
					
					<td class="label">费用申请类别:</td>
					<td>
							<input type="text" class="text" name="feeApplicationTypeStringLike"/>
					</td>
				</tr>
			
				<tr>
					<td class="label">
						费用申请时间:
					</td>
					<td >
						<input type="text" class="text" name="feeApplicationGreatTime" onclick="popUpCalendar(this, this,null,null,null,true,-1)" />
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
							<input type="button" class="btn" value="查询所有" onclick="window.location.href='${app }/deviceManagement/chargeFeeAppli/charge.do?method=list&&Type=2'"/>
						</div>          
					</td>
				</tr>
			</table> 
		</fieldset>
	</html:form>
	
	
</div>

<br/>
 

<!-- Information hints area end-->

	<display:table name="feeApplicationMainList" cellspacing="0" cellpadding="0"
		id="feeApplicationMainList" pagesize="${pagesize}"
		class="table feeApplicationMainList" export="true"
		requestURI="charge.do" sort="list"
		partialList="true" size="${size}">
		
		
	
		<display:column sortable="true" headerClass="sortable" title="申请人">
			${feeApplicationMainList.feeApplicationUser}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请日期">
			${feeApplicationMainList.feeApplicationGreatTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
            <eoms:id2nameDB beanId="tawSystemAreaDao" id="${feeApplicationMainList.feeApplicationCity}"/>
			</display:column>
		<display:column sortable="true" headerClass="sortable" title="费用类型">
			${feeApplicationMainList.feeApplicationType}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="费用金额">
			${feeApplicationMainList.feeApplicationMoney}
		</display:column>
		
	
		<display:column sortable="true" headerClass="sortable" title="状态"  >					 
		
					<c:if test="${1==(feeApplicationMainList.feeApplicationStatus)}" >草稿</c:if>		
					<c:if test="${2==(feeApplicationMainList.feeApplicationStatus)}" >等待审批</c:if>
					<c:if test="${3==(feeApplicationMainList.feeApplicationStatus)}" >审批完成</c:if>			
						
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="详情" 
		paramProperty="id" url="/deviceManagement/chargeFeeAppli/charge.do?method=goToDeal"
			paramId="id" media="html" >					 
						
				<img src="${app }/images/icons/table.gif">		
		</display:column>	
		
		
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>

</div>



<%@ include file="/common/footer_eoms.jsp"%>
