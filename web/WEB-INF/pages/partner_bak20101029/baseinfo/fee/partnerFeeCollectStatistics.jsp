<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language = 'javascript'>
	function period(){
	    var startDate = document.getElementById("startDate").value;
	    var endDate = document.getElementById("endDate").value;
	    var collectUnit = document.getElementById("collectUnit").value;
	    if(startDate == ""){
	        alert("请选择开始时间！");
	        return false;	    
	    }
	    if(startDate > endDate){
	        alert("开始时间要小于结束时间！");
	        return false;
	    }
	    location.href=eoms.appPath+'/fee/partnerFeeInfos.do?method=searchFeeCollectStatistics&startDate='+startDate+'&endDate='+endDate+'&collectUnit='+collectUnit;
	}
</script>

<eoms:xbox id="tree" dataUrl="${app}/fee/partnerFeePlans.do?method=getPartnerTree" 
	  	rootId="1" 
	  	rootText='单位' 
	  	valueField="collectUnit" handler="collectUnitName"
		textField="collectUnitName"
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
            收款单位(可选）<input type="text"   id="collectUnitName" name="collectUnitName" class="text" readonly="readonly" value='<eoms:id2nameDB id="${collectUnit}" beanId="partnerDeptDao" />' />
			<input type="hidden" id="collectUnit"   name="collectUnit" value="${collectUnit}" />
          <a style="CURSOR:hand" onclick="period()">统计</a>&nbsp;&nbsp;
	    </td>		
	</tr>

	<display:table name="partnerFeePlanList" cellspacing="0" cellpadding="0"
		id="partnerFeePlanList" pagesize="${pageSize}" class="table partnerFeePlanList"
		export="false"
		requestURI="${app}/fee/partnerFeeInfos.do?method=searchFeeCollectStatistics"
		sort="list" partialList="false" size="resultSize">


	<display:column  sortable="true"  headerClass="sortable" title="收款单位" >
		<eoms:id2nameDB id="${partnerFeePlanList.collectUnit}"  beanId="partnerDeptDao" />
	</display:column>

	<display:column property="countFee" sortable="true"
			headerClass="sortable" title="总金额(元)"  paramId="id" paramProperty="id"/>

	<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var collectUnit = '${partnerFeePlanList.collectUnit }';
		                        var beginDate = '${partnerFeePlanList.beginDate}';
		                        var endDate = '${partnerFeePlanList.endDate}';
		                        var url=eoms.appPath+'/fee/partnerFeeInfos.do?method=searchInfoByCollect&collectUnit='+collectUnit+'&beginDate='+beginDate+'&endDate='+endDate;
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
