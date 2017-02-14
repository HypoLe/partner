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
										var companyName = '${gridKPIYearForm.companyName}';
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
											var gridName = '${gridKPIYearForm.gridName}';
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

    window.location.href=eoms.appPath+'/partner/maintenance/gridKPIYears.do?method=add';

}
</script>
<c:set var="buttons">
		   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<html:form action="/gridKPIYears.do?method=searchGridKPIYear" styleId="gridKPIYearForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">查询年报</div>
	</caption>
<!-- 年 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.year" />
		</td>
		<td class="content">
			<select id="year" name="year" >
					<option id="0" value="">请选择</option>
				<c:forEach begin="2008" end="2025" var="num">
					<c:if test="${gridKPIYearForm.year ==num}">
						<option id="${num}" value="${num}" selected="true">${num}年</option>
					</c:if>
					<c:if test="${gridKPIYearForm.year !=num}">
						<option id="${num}" value="${num}">${num}年</option>
					</c:if>					
				</c:forEach>
			</select>	
			<html:hidden property="year" value="${gridKPIYearForm.year}" />									
		</td>
<!-- 区域地市 -->
		<td class="label">
			<fmt:message key="gridKPIYear.region" />
		</td>
		<td class="content">
			<!-- 地市 -->			
			<select name="region" id="region" onchange="changeCity();">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city"  >
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
						
		</td>
	</tr>
<!-- 维护公司 -->
	<tr>
		<td class="label">
			<fmt:message key="gridKPIYear.companyName" />
		</td>
		<td class="content">
			<select name="companyName" id="companyName" onchange="changeGrid();">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
		</td>
<!-- 网格名称 -->
		<td class="label">
			<fmt:message key="gridKPIYear.gridName" />
		</td>
		<td class="content">
			<select name="gridName" id="gridName" >
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

</html:form>
	<display:table name="gridKPIYearList" cellspacing="0" cellpadding="0"
		id="gridKPIYearList" pagesize="${pageSize}" class="table gridKPIYearList"
		export="false"
		requestURI="${app}/partner/maintenance/gridKPIYears.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column property="year" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.year"  paramId="id" paramProperty="id"/>
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="gridKPIYear.region">
		<eoms:id2nameDB id="${gridKPIYearList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<display:column property="companyName" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.companyName"  paramId="id" paramProperty="id"/>
	<display:column property="gridName" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.gridName"  paramId="id" paramProperty="id"/>

	<display:column property="bsWarning" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.bsWarning"  paramId="id" paramProperty="id"/>

	<display:column property="bswReduce" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.bswReduce"  paramId="id" paramProperty="id"/>

	<display:column property="mbplot" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.mbplot"  paramId="id" paramProperty="id"/>

	<display:column property="wirelessIn" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.wirelessIn"  paramId="id" paramProperty="id"/>

	<display:column property="mostfailRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.mostfailRate"  paramId="id" paramProperty="id"/>

	<display:column property="nofaiRamRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.nofaiRamRate"  paramId="id" paramProperty="id"/>

	<display:column property="workOrderATR" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.workOrderATR"  paramId="id" paramProperty="id"/>

	<display:column property="dropCallRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.dropCallRate"  paramId="id" paramProperty="id"/>

	<display:column property="bsOutRate" sortable="true"
			headerClass="sortable" titleKey="gridKPIYear.bsOutRate"  paramId="id" paramProperty="id"/>


	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn">
				    <a href='${app}/partner/maintenance/gridKPIYears.do?method=detail&id=${gridKPIYearList.id}' target='_blank'>
				       <img src="${app}/images/icons/search.gif"/></a>
			</display:column> 
		
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn">
				    <a href='${app}/partner/maintenance/gridKPIYears.do?method=detail&flag=search&id=${gridKPIYearList.id}' target='_blank'>
				       <img src="${app}/images/icons/search.gif"/></a>
			</display:column> 
		
		</c:otherwise>
	</c:choose>
	

		<display:setProperty name="paging.banner.item_name" value="gridKPIYear" />
		<display:setProperty name="paging.banner.items_name" value="gridKPIYears" />
	</display:table>

</fmt:bundle>


	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>



<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${gridKPIYearForm.region}';
	var companyName = '${gridKPIYearForm.companyName}';
	var gridName = '${gridKPIYearForm.gridName}';	
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