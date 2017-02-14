<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
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
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}
 function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            for(var i=ddlObj.length-1;i>=0;i--){
                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
function changeCity(con)
		{    
			delAllOption("town");//地市选择更新后，重新刷新县区
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/pnrBaseSites.do?method=changeCity&city="+city;
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					var json = eval(res);
		
					var townName = "town";
					var arrOptions = json[0].cb.split(",");
					var obj=$(townName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						obj.options[j]=opt;
						j++;
						i++;
					}
					if(con==1){
						var town = '${pnrBaseSiteForm.town}';
						if(city!=''){
							document.getElementById("town").value = town;
						}									
					}					
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}
</script>



<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/pnrBaseSites.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="/pnrBaseSites.do?method=search" styleId="stationForm" method="post"> 
<table class="formTable">
	<caption>
		<div class="header center">合作伙伴站址信息管理</div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="pnrBaseSite.code" />
		</td>
		<td class="content">
			<html:text property="code" styleId="code"
						styleClass="text medium"/>
		</td>

		<td class="label">
			<fmt:message key="pnrBaseSite.name" />
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium"/>
				
		</td>
	

	</tr>

	<tr>
	
		
		<td class="label">
			地市
		</td>
		<td class="content">

					<!-- 地市 -->			
					<select name="city" id="city" onchange="changeCity();">
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
			县区
		</td>
		<td class="content">
	
					<!-- 县区 -->			
					<select name="town" id="town" onchange="">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
 
 
 		</td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>


	<display:table name="pnrBaseSiteList" cellspacing="0" cellpadding="0"
		id="pnrBaseSiteList" pagesize="${pageSize}" class="table pnrBaseSiteList"
		export="false"
		requestURI="${app}/partner/baseinfo/pnrBaseSites.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="name" sortable="true"
			headerClass="sortable" titleKey="pnrBaseSite.name" href="${app}/partner/baseinfo/pnrBaseSites.do?method=detail" paramId="id" paramProperty="id"/>

	<display:column property="addTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="pnrBaseSite.addTime" />

	<display:column property="code" sortable="true"
			headerClass="sortable" titleKey="pnrBaseSite.code"/>

	<display:column property="state" sortable="true"
			headerClass="sortable" titleKey="pnrBaseSite.state"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrBaseSite.city">
			<eoms:id2nameDB id="${pnrBaseSiteList.city}" beanId="tawSystemAreaDao" />
	</display:column>		

	<display:column sortable="true" headerClass="sortable" titleKey="pnrBaseSite.town">
			<eoms:id2nameDB id="${pnrBaseSiteList.town}" beanId="tawSystemAreaDao" />
	</display:column>
	<display:column title="修改" headerClass="imageColumn" paramId="id" paramProperty="id">
		<a href='${app}/partner/baseinfo/pnrBaseSites.do?method=edit&id=${pnrBaseSiteList.id}' target='_blank'>
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
		<display:setProperty name="paging.banner.item_name" value="pnrBaseSite" />
		<display:setProperty name="paging.banner.items_name" value="pnrBaseSites" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>