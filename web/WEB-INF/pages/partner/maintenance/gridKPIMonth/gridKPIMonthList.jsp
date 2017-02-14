<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
function createRequest()
{
	var xmlhttp = null;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    return xmlhttp;
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

function changeCity(con)
		{    
		    delAllOption("companyName");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("gridName");//地市选择更新后，重新刷新网格
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeToPartner&region="+region;

			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
									 if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
									var json = eval(res);
									
			
									var companyNameName = "companyName";
									var arrOptionsP=json[0].pb.split(",");
									var objp=$(companyNameName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsP.length;m++){
										var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}
									
									var gridName1 = "gridName";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName1);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}
									if(con==1){
										var companyName = '${gridKPIMonthForm.companyName}';
										if(companyName!=''){
									 		document.getElementById("companyName").value = companyName;
										}
									}		
									changeGrid(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
			
			
		}

function changeGrid(con){
    var providerValue = document.getElementById("companyName").value;
    if(providerValue!=''){
	    delAllOption("gridName");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue;
	    
		providerValue = "${eoms:a2u(providerValue)}";
			Ext.Ajax.request({
									url : url ,
									method: 'POST',
									params:{providerValue:providerValue},
									success: function ( result, request ) { 
									res = result.responseText;
									if(res.indexOf("<\/SCRIPT>")>0){
								  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
									}
										var json = eval(res);
								     	var countyName = "gridName";
										var arrOptions = json[0].gl.split(",");
										var obj=$(countyName);
										var i=0;
										var j=0;
										for(i=0;i<arrOptions.length;i++){
												var opt=new Option(arrOptions[i+1],arrOptions[i]);
											obj.options[j]=opt;
											j++;
											i++;
										}
										if(con==1){
											var gridName = '${gridKPIMonthForm.gridName}';
											if(gridName!=''){
										 		document.getElementById("gridName").value = gridName;
											}												
										}
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}			

		
  function newAdd(){

    window.location.href=eoms.appPath+'/partner/maintenance/gridKPIMonths.do?method=add';

}
</script>
<c:set var="buttons">
		   
		   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">
<html:form action="/gridKPIMonths.do?method=searchGridKPIMonth" styleId="gridKPIMonthForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">查询月报</div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIMonth.year" />&nbsp;<fmt:message key="gridKPIMonth.month" />份
		</td>
		<td class="content">
			<select id="year" name="year" >
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${gridKPIMonthForm.year ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${gridKPIMonthForm.year !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>	
			<select id="month" name="month" >
					<option id="0" value="">请选择</option>
				<c:forEach begin="1" end="12" var="num">
					<c:if test="${gridKPIMonthForm.month ==num}">
						<option id="${num}" value="${num}" selected="true">${num}月</option>
					</c:if>
					<c:if test="${gridKPIMonthForm.month !=num}">
						<option id="${num}" value="${num}">${num}月</option>
					</c:if>					
				</c:forEach>
			</select>
		</td>
<!-- 区域地市 -->
		<td class="label">
			<fmt:message key="gridKPIMonth.region" />&nbsp;
		</td>
		<td class="content">
			<!-- 地市 -->			
			<select name="region" id="region" onchange="changeCity();">
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
	</tr>
	<tr>	
<!-- 维护公司 -->
		<td class="label" >
			<fmt:message key="gridKPIMonth.companyName" />&nbsp;
		</td>
		<td class="content" >
			<select name="companyName" id="companyName" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'" onchange="changeGrid();">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>	
		</td>
		<td class="label" >
			<fmt:message key="gridKPIMonth.gridName" />&nbsp;
		</td>
		<td class="content">
			<select name="gridName" id="gridName" 
				alt="allowBlank:false,vtext:'请选择所属网格'" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>			
		</td>
	</tr>

</table>

<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
<html:hidden property="id" value="${gridKPIMonthForm.id}" />
</html:form>


	<display:table name="gridKPIMonthList" cellspacing="0" cellpadding="0"
		id="gridKPIMonthList" pagesize="${pageSize}" class="table gridKPIMonthList"
		export="false"
		requestURI="${app}/partner/maintenance/gridKPIMonths.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column property="year" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.year"  paramId="id" paramProperty="id"/>
	<display:column property="month" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.month"  paramId="id" paramProperty="id"/>			
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="gridKPIMonth.region">
		<eoms:id2nameDB id="${gridKPIMonthList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<display:column property="companyName" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.companyName"  paramId="id" paramProperty="id"/>

	<display:column property="gridName" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.gridName"  paramId="id" paramProperty="id"/>

	<display:column property="bsWarning" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.bsWarning"  paramId="id" paramProperty="id"/>

	<display:column property="bswReduce" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.bswReduce"  paramId="id" paramProperty="id"/>

	<display:column property="mbplot" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.mbplot"  paramId="id" paramProperty="id"/>

	<display:column property="wirelessIn" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.wirelessIn"  paramId="id" paramProperty="id"/>

	<display:column property="mostfailRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.mostfailRate"  paramId="id" paramProperty="id"/>

	<display:column property="nofaiRamRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.nofaiRamRate"  paramId="id" paramProperty="id"/>

	<display:column property="dropCallRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.dropCallRate"  paramId="id" paramProperty="id"/>

	<display:column property="bsOutRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIMonth.bsOutRate"  paramId="id" paramProperty="id"/>

	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn">
				    <a href='${app}/partner/maintenance/gridKPIMonths.do?method=detail&id=${gridKPIMonthList.id}' target='_blank'>
				       <img src="${app}/images/icons/search.gif"/></a>
			</display:column> 
		
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn">
				    <a href='${app}/partner/maintenance/gridKPIMonths.do?method=detail&flag=search&id=${gridKPIMonthList.id}' target='_blank'>
				       <img src="${app}/images/icons/search.gif"/></a>
			</display:column> 
		
		
		</c:otherwise>
	</c:choose>
	
		<display:setProperty name="paging.banner.item_name" value="gridKPIMonth" />
		<display:setProperty name="paging.banner.items_name" value="gridKPIMonths" />
	</display:table>

</fmt:bundle>

	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>


<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${gridKPIMonthForm.region}';
	var companyName = '${gridKPIMonthForm.companyName}';
	var gridName = '${gridKPIMonthForm.gridName}';	
	if(region!=''){
 		document.getElementById("region").value = region;
 		changeCity();
	}
	if(companyName!=''){
 		document.getElementById("companyName").value = companyName;
 		changeGrid();
	}
	if(gridName!=''){
 		document.getElementById("gridName").value = gridName;
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>