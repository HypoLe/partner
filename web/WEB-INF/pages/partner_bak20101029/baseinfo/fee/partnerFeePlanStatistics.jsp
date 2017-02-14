<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language = 'javascript'>
	function period(){
	    var startDate = document.getElementById("startDate").value;
	    var endDate = document.getElementById("endDate").value;
	    var plan = document.getElementById("plan").value;
	    if(startDate == ""){
	        alert("请选择开始时间！");
	        return false;	    
	    }
	    if(startDate > endDate){
	        alert("开始时间要小于结束时间！");
	        return false;
	    }
	    location.href=eoms.appPath+'/fee/partnerFeePlans.do?method=searchFeePlanStatistics&startDate='+startDate+'&endDate='+endDate+'&plan='+plan;
	}
</script>

<eoms:xbox id="tree" dataUrl="${app}/fee/partnerFeePlans.do?method=getNodesRadioTree1" 
	  	rootId="1" 
	  	rootText='付款计划' 
	  	valueField="plan" handler="planName"
		textField="planName"
		checktype="forums" single="true"		
	  ></eoms:xbox>
	  
<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">

<content tag="heading">
	<b>单位收款金额统计</b>
</content>

	<br><br>

	<tr align = 'center'>	
        <td>
            统计时间：
            从&nbsp;&nbsp;<input type="text" name="startDate" id="startDate" value="${startDate}" size="10" onclick="popUpCalendar(this,this,null,null,null,false,-1);" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择开始时间...'" />&nbsp;&nbsp;
            到&nbsp;&nbsp;<input type="text" name="endDate"   id="endDate"   value="${endDate}"   size="10" onclick="popUpCalendar(this,this,null,null,null,false,-1);" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择结束时间...'" />&nbsp;&nbsp;
            付款计划名(可选）<input type="text"   id="planName" name=""planName"" class="text" readonly="readonly" value='<eoms:id2nameDB id="${plan}" beanId="partnerFeePlanDao" />' />
			<input type="hidden" id="plan"   name="plan" value="${plan}" />
          <a style="CURSOR:hand" onclick="period()">统计</a>&nbsp;&nbsp;
	    </td>		
	</tr>

	<display:table name="partnerFeePlanList" cellspacing="0" cellpadding="0"
		id="partnerFeePlanList" pagesize="${pageSize}" class="table partnerFeePlanList"
		export="false"
		requestURI="${app}/fee/partnerFeePlans.do?method=searchFeePlanStatistics"
		sort="list" partialList="false" size="resultSize">

	<display:column property="name" sortable="true" 
			headerClass="sortable" titleKey="partnerFeePlan.name"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="partnerFeePlan.payUnit" >
		<eoms:id2nameDB id="${partnerFeePlanList.payUnit}"  beanId="partnerDeptDao" />
	</display:column>

	<display:column property="planPayDate" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="partnerFeePlan.planPayDate"  paramId="id" paramProperty="id"/>

	<display:column property="planPayFee" sortable="true"
			headerClass="sortable" title="计划付款金额(元)"/>

	<display:column sortable="true"	headerClass="sortable" title="是否已付款" >
			<eoms:dict key="dict-kmmanager" dictId="isOrNot"
				itemId="${partnerFeePlanList.payStatus}" beanId="id2nameXML" />
	</display:column>

	<display:column property="payStage" sortable="true"
			headerClass="sortable" titleKey="partnerFeePlan.payStage"  paramId="id" paramProperty="id"/>
	
	<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${partnerFeePlanList.id }';
		                        var url=eoms.appPath+'/fee/partnerFeePlans.do?method=searchDetail&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeeInfo" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeeInfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
