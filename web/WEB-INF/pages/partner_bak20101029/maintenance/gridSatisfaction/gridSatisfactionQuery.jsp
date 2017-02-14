<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridSatisfactionForm'});
});
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
    xmlhttp.send(null);
    return xmlhttp.responseText;
}

//县区联动
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									
									var json = eval(res);
									var countyName = "city";
						
									var arrOptions = json[0].cb.split(",");
									var obj=$(countyName);
											
									var i=0;
									var j=0;
									for(i=0;i<arrOptions.length;i++){
										var opt=new Option(arrOptions[i+1],arrOptions[i]);
										obj.options[j]=opt;
										j++;
										i++;
									}
									var providerName = "provider";
									var arrOptionsP=json[0].pb.split(",");
									var objp=$(providerName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsP.length;m++){
										var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
										objp.options[n]=optp;
										n++;
										m++;
										
									}
									
									
									if(con==1){
										var city = '${gridSatisfactionForm.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}	
									changePartner(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
		
//地市、县区联动合作伙伴		
function changePartner(con)
		{    
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
			//地市
			var regionValue = document.getElementById("region").value;
			//县区
			var cityValue = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changePartner&region="+regionValue+"&city="+cityValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("<\/SCRIPT>")>0){
						  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
							}
								var json = eval(res);
								
								var providerName = "provider";
								var arrOptionsP=json[0].pb.split(",");
								var objp=$(providerName);
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
									
								}
								
								if(con==1){
										var provider = '${gridSatisfactionForm.provider}';
										if(provider!=''){
											document.getElementById("provider").value = provider;
										}
										
										changeGrid(con);
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
		
		
		

		
function newAdd(){

    window.location.href=eoms.appPath+'/partner/maintenance/gridSatisfactions.do?method=add';

}			
</script>
<c:set var="buttons">
   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />

	</c:set>
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<html:form action="/gridSatisfactions.do?method=searchGridSatisfaction" styleId="gridSatisfactionForm" method="post" >

<table class="formTable">
    <caption>
        <div class="header center">
            <fmt:message key="gridSatisfaction.title.query" />
        </div>
    </caption>
 <!-- 年 -->
	<tr>
		<td class="label">
			<fmt:message key="gridSatisfaction.year" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="year" name="year" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${gridSatisfactionForm.year ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${gridSatisfactionForm.year !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>	
		</td>
<!-- 月份 -->
		<td class="label">
			<fmt:message key="gridSatisfaction.month" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select id="month" name="month" alt="allowBlank:false">
					<option id="0" value="">请选择</option>
				<c:forEach begin="1" end="12" var="num">
					<c:if test="${gridSatisfactionForm.month ==num}">
						<option id="${num}" value="${num}" selected="true">${num}月</option>
					</c:if>
					<c:if test="${gridSatisfactionForm.month !=num}">
						<option id="${num}" value="${num}">${num}月</option>
					</c:if>					
				</c:forEach>
			</select>
		</td>
	</tr>
	   
    <tr>
        <td class="label">
            <fmt:message key="gridSatisfaction.region" /><!-- 地市 -->&nbsp;<font color='red'>*</font>
        </td>
        <td class="content">
	  		<!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);"> 
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
        </td>
        
		<td class="label"> 
			县区&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'" onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>

		</td>		
       
	</tr>
	
	<tr>
	
	    <td class="label">
		    <fmt:message key="gridSatisfaction.provider" /><!-- 合作伙伴 -->&nbsp;<font color='red'>*</font>					
		</td>
		<td class="content" colspan="3">
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'" >
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>		
		</td>
	
	</tr>

</table>

<br>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>


</html:form>
	<display:table name="gridSatisfactionList" cellspacing="0" cellpadding="0"
		id="gridSatisfactionList" pagesize="${pageSize}" class="table gridSatisfactionList"
		export="false"
		requestURI="${app}/partner/maintenance/gridSatisfactions.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.reportUser"  paramId="id" paramProperty="id">
			<eoms:id2nameDB id="${gridSatisfactionList.reportUser}" beanId="tawSystemUserDao" />
	</display:column>
	<display:column property="month" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.month"  paramId="id" paramProperty="id"/>

	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="gridSatisfaction.region">
		<eoms:id2nameDB id="${gridSatisfactionList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" title="县区">
		<eoms:id2nameDB id="${gridSatisfactionList.city}" beanId="tawSystemAreaDao" />
	</display:column>			


	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.grid"  paramId="id" paramProperty="id"/>

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.provider"  paramId="id" paramProperty="id"/>

	<display:column property="synRating" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.synRating"  paramId="id" paramProperty="id"/>

	<display:column property="tieMaintenance" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.tieMaintenance"  paramId="id" paramProperty="id"/>

	<display:column property="faultDispose" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.faultDispose"  paramId="id" paramProperty="id"/>

	<display:column property="phonicsQuality" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.phonicsQuality"  paramId="id" paramProperty="id"/>

	<display:column property="businessLobby" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.businessLobby"  paramId="id" paramProperty="id"/>

	<display:column property="customerComplaints" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.customerComplaints"  paramId="id" paramProperty="id"/>

	<display:column property="valueCustomer" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.valueCustomer"  paramId="id" paramProperty="id"/>

	<display:column property="corporateCustomer" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.corporateCustomer"  paramId="id" paramProperty="id"/>

	<display:column property="companyAct" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.companyAct"  paramId="id" paramProperty="id"/>

	<display:column property="personnelStatus" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.personnelStatus"  paramId="id" paramProperty="id"/>

	<display:column property="instrumentStatus" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.instrumentStatus"  paramId="id" paramProperty="id"/>

	<display:column property="managementAbility" sortable="true"
			headerClass="sortable" titleKey="gridSatisfaction.managementAbility"  paramId="id" paramProperty="id"/>
	
	<display:column property="reportTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="gridSatisfaction.reportTime"  paramId="id" paramProperty="id"/>


	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn">
				    <a href='${app}/partner/maintenance/gridSatisfactions.do?method=detail&id=${gridSatisfactionList.id}' target='_blank'>
				       <img src="${app}/images/icons/search.gif"/></a>
			</display:column> 
			
		
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn">
				    <a href='${app}/partner/maintenance/gridSatisfactions.do?method=detail&flag=search&id=${gridSatisfactionList.id}' target='_blank'>
				       <img src="${app}/images/icons/search.gif"/></a>
			</display:column> 
		
		</c:otherwise>
	</c:choose>

		<display:setProperty name="paging.banner.item_name" value="gridSatisfaction" />
		<display:setProperty name="paging.banner.items_name" value="gridSatisfactions" />
	</display:table>

	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>
	
	
</fmt:bundle>
<br>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${gridSatisfactionForm.region}';
	var provider = '${gridSatisfactionForm.provider}';
	if(region!=''){
 		document.getElementById("region").value = region;
 		changeCity();
	}
	if(provider!=''){
 		document.getElementById("provider").value = provider;
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>