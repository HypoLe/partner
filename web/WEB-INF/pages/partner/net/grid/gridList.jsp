<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">
  	var checkflag = "false";
  	
	function choose() {
		var objs = document.getElementsByName("ids");
		if(checkflag=="false"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = true;
      			checkflag="true";
			}
		}else if(checkflag=="true"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = false;
      			checkflag="false"
			}
		}
	}
	  	
	function del(){
		if(!checkSelect()){
			return false;
		}
		if(confirm('你确定删除?')){
			return true;
		}
		return false;
	}
	
	function checkSelect(){
		var objs = document.getElementsByName("ids");
		var checkflag = false;
		for(var i=0; i<objs.length; i++) {
    	    if(objs[i].type.toLowerCase() == "checkbox" && objs[i].checked == true)      			
      		    checkflag = true;
		}		
		if(!checkflag)
			alert("请选择基站");
		return checkflag;
	}	
</script>

<script type="text/javascript">
Ext.onReady(function(){
	setDefaultValueOnSelect("region","${regionStr}");
	if("${regionStr}" != "") {
		requestCountryOnCityChange();
	}
});
function setDefaultValueOnSelect(id,value) {
	document.getElementById(id).value = value;
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
//县区联动
//地市变化 联动县区
function requestCountryOnCityChange() {  
	delAllOption("city");
	var region = document.getElementById("region").value;
	var url="${app}/partner/inspect/ajaxRequest.do?method=requestCountryOnCityChange&region="+region;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
			res = result.responseText;
			var json = eval(res);
			var obj=$("city");
			for(i=0;i<json.length;i++){
				var opt=new Option(json[i].areaName,json[i].areaId);
				obj.options[i]=opt;
			}
			setDefaultValueOnSelect("city","${cityStr}");
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
		
//地市、县区联动合作伙伴	 福建	
function changePartner(con)
		{    
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
			//地市
			var regionValue = document.getElementById("region").value;
			//县区
			var cityValue = document.getElementById("city").value;
			var url="../net/lines.do?method=changePartner&region="+regionValue+"&city="+cityValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("[{")!=0){
		  						res = "[{"+result.responseText;
							}
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
										var provider = '${gridForm.provider}';
										if(provider!=''){
											document.getElementById("provider").value = provider;
										}
										
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
	


function newAdd(){

    window.location.href=eoms.appPath+'/partner/net/grids.do?method=add';

}
</script>
<c:set var="buttons">
	  <br/>
	  <input type="button"  styleClass="btn" 
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	  <br/>
	
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">


<center> 
<div>
<html:form  action="/grids.do?method=searchGridList" styleId="gridForm" method="post" > 
	<table class="formTable">
	
		<caption>
			<div class="header center"><fmt:message key="grid.form.query"/></div>
		</caption>

		<tr>
			<td class="label">
				<fmt:message key="grid.gridName" />
			</td>
			<td class="content">
				<html:text property="gridName" styleId="gridName"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',maxLength:32" value="${gridForm.gridName}"/>
				<span id = "message"></span>			
			</td>
			
			<!-- 所属地市 -->
			<td class="label">
				<fmt:message key="grid.region" />:
			</td>
			<td >
				<!-- 地市 -->			
				<select name="region" id="region"
					alt="allowBlank:false,vtext:'请选择所在地市'"
					onchange="requestCountryOnCityChange();">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:present name="region">
					<logic:iterate id="region" name="region">
						<option value="${region.areaid}">
							${region.areaname}
						</option>
					</logic:iterate>
					</logic:present>
				</select>
			</td>
			
		</tr>

		<tr>
			
			<!-- 所属县区 -->
			<td class="label">
				<fmt:message key="grid.city" />
			</td>
			<td colspan="3">
				<!-- 县区 -->			
				<select name="city" id="city" 
					alt="allowBlank:true,vtext:'请选择所在县区'">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>
	
			<!--  
			<td class="label">
				<fmt:message key="grid.provider" />
			</td>
			<td class="content">
				
				<select name="provider" id="provider" 
					alt="allowBlank:false,vtext:'请选择合作伙伴'">
					<option value="">
						--请选择合作伙伴--
					</option>				
				</select>
			</td>-->
		</tr>
	</table>
	</center> 
<table>
    <tr>
	    <td>
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>
<br/>
<html:form action="/grids.do" method="post" styleId="lineForm">	
	<table>
	    <tr>
        <td>
			<c:if test="${param.flag!='search'}">
				<c:out value="${buttons}" escapeXml="false" />
			</c:if>
        </td>
        </tr>
	</table>
	<display:table name="gridList" cellspacing="0" cellpadding="0"
		id="gridList" pagesize="${pageSize}" class="table gridList"
		export="false" 
		requestURI="${app}/partner/net/grids.do?method=search"
		sort="list" partialList="true" size="resultSize" 
		decorator="com.boco.eoms.partner.net.displaytag.support.GridDisplaytagDecorator">

<center>
	<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" style="width:3%" />
	<display:setProperty name="css.table" value="0," />	
	
    <display:column property="gridName" sortable="true"
			headerClass="sortable" titleKey="grid.gridName"  paramId="id" paramProperty="id"/>
			
	<display:column property="gridNumber" sortable="true"
			headerClass="sortable" titleKey="grid.gridNumber"  paramId="id" paramProperty="id"/>
					
	
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="grid.region">
		<eoms:id2nameDB id="${gridList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="grid.city">
		<eoms:id2nameDB id="${gridList.city}" beanId="tawSystemAreaDao" />
	</display:column>			
			

	<display:column sortable="true"
			headerClass="sortable" titleKey="grid.provider"  paramId="id" paramProperty="id"> 
			<eoms:id2nameDB id="${gridList.partnerid}" beanId="partnerDeptDao" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="grid.addUser" >
			<eoms:id2nameDB id="${gridList.addUser}" beanId="tawSystemUserDao" />
	</display:column>

	<display:column property="addTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="grid.addTime"  paramId="id" paramProperty="id"/>
			
	<display:column title="查看" headerClass="imageColumn">
		       
		<c:choose>
			<c:when test="${param.flag!='search'}">
			    <a href="${app}/partner/net/grids.do?method=detailWithSite&gridId=${gridList.id}" target='_blank'>
			       <img src="${app}/images/icons/search.gif"/></a>
			
			</c:when>
			<c:otherwise>
				<a href="${app}/partner/net/grids.do?method=detailWithSite&flag=search&gridId=${gridList.id}" target='_blank'>
			       <img src="${app}/images/icons/search.gif"/></a>
			
			</c:otherwise>
		</c:choose>		       
	</display:column> 

		<display:setProperty name="paging.banner.item_name" value="grid" />
		<display:setProperty name="paging.banner.items_name" value="grids" />
	</display:table>
<table>
	    <tr>
	        <td>
	            <html:submit styleClass="buttons" property="method.removes" onclick="return del()">
	                删除
	            </html:submit>
	        </td>
	    
        </tr>
	</table>
	
</html:form>	
</center> 	

	


</div>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>