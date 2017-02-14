<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script language=javascript>

function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}

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



</script>

<c:set var="buttons">
	<br />
	<input type="button" style="margin-right: 5px" onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />

</c:set>

	<display:table name="residentSiteList" cellspacing="0" cellpadding="0"
		id="residentSiteList" pagesize="${pageSize}"
		class="table residentSiteList" export="false"
		requestURI="${app}/partner/serviceArea/residentSites.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column property="residentSiteName" sortable="true"
			headerClass="sortable" title="驻点名称" paramId="siteId"
			paramProperty="siteId" />
		<!-- 所在地市 -->
		<display:column sortable="true"   headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${residentSiteList.cityid}"
				beanId="tawSystemAreaDao" />
		</display:column>


		<display:column sortable="true"  headerClass="sortable" title="县区">
			<eoms:id2nameDB id="${residentSiteList.countryid}"
				beanId="tawSystemAreaDao" />
		</display:column>

		<display:column sortable="true" property="monitorCompany"  headerClass="sortable" title="代维公司">
			
		</display:column>

		<c:if test="${param.flag!='search'}">
			<display:column
				title="&nbsp;&nbsp;&nbsp;&nbsp;操&nbsp;&nbsp;&nbsp;&nbsp;作&nbsp;&nbsp;&nbsp;"
				headerClass="imageColumn">

				<a
					href='${app}/partner/oilmachine/oilResidentSites.do?method=edit&id=${residentSiteList.id}'
					target='_blank'> &nbsp;&nbsp;修改</a>
				<a
					href="javascript: if(confirm('确定要删除该文件?')){var id='${residentSiteList.id} ';var nodeId='${nodeId}';var url=eoms.appPath+'/partner/oilmachine/oilResidentSites.do?method=remove&id=' + id ;location.href=url}">
					&nbsp;&nbsp;删除 </a>
			</display:column>
		</c:if>

		<display:setProperty name="paging.banner.item_name" value="site" />
		<display:setProperty name="paging.banner.items_name" value="sites" />
	</display:table>
	<br />
	<a href="${app}/partner/oilmachine/oilResidentSites.do?method=add">添加记录</a>



<%@ include file="/common/footer_eoms.jsp"%>