<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">
var checkflag = "false";

function addEmptyOption(id,content) {
	var opt=new Option(content,"");
	$(id).options[0]=opt;
}

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

<script language=javascript>
function createRequest() {
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
//地市变化 联动县区
function requestCountryOnCityChange() {  
	delAllOption("city");
	delAllOption("gridNumber");
	addEmptyOption("gridNumber","--请选择网格--");
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
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
//地市，县区变化 联动网格
function requestGridOnChangeCountry() {
	delAllOption("gridNumber");
	var region = document.getElementById("region").value;
	var city = document.getElementById("city").value;
	var url="${app}/partner/inspect/ajaxRequest.do?method=requestGridOnChangeCountry&region="+region+"&city="+city;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
			res = result.responseText;
			var json = eval(res);
			var obj=$("gridNumber");
			for(i=0;i<json.length;i++){
				var opt=new Option(json[i].gridName,json[i].gridId);
				obj.options[i]=opt;
			}
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
function changeCompany(con){
    var grid = document.getElementById("gridNumber").options[document.getElementById("gridNumber").selectedIndex].text; 
    var gridNumber = document.getElementById("gridNumber").value;
    if(gridNumber!=''){
		var url="../baseinfo/tawApparatuss.do?method=changeCompany&gridNumber="+gridNumber;
		Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
 						res = "[{"+result.responseText;
				}
				var json = eval(res);
				var arrOptions = json[0].gl.split(",");
				var provider = arrOptions[0];
				var partnerId= arrOptions[1];
				if(provider!=null){ 
					document.getElementById("partnername").value = provider;	
				}
				else{
					document.getElementById("partnername").value = "该网格下没有所属公司";
				}
				
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
    }
}
</script>

<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">
	<div>
		<html:form  action="/sites.do" styleId="siteForm" method="post"> 
			<table class="formTable" align="center">
				<caption>
					<div class="header center"><fmt:message key="site.form.query"/></div>
				</caption>
				
				<tr>	
					<td class="label">
						站点名称
					</td class="label">
					<td>
						<html:text property="siteName" styleId="siteName"
									styleClass="text medium"
									alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.siteName}"/>
					</td>
					
					<td class="label">	
						站点编号
					</td>
					<td>
						<html:text property="siteNo" styleId="siteNo"
									styleClass="text medium"
									alt="allowBlank:false,vtext:'',vtype:'',maxLength:64" value="${siteForm.siteNo}" />
					</td>
				</tr>
				
		        <tr>    
		            <td class="label">
						专业类型
					</td>
		            <td>
						<select class="select max" id="specialtyType" name="specialtyType" alt="allowBlank:true">
							<option value="">--请选择专业--</option>
							<option value="base">基站</option>
						</select>
					</td>
					
					<td class="label">
						<fmt:message key="site.maintainType" />
					</td>	
					<td>	
						<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" isQuery="false"  alt="allowBlank:false,vtext:''"
									defaultId="${siteForm.maintainType}" selectId="maintainType" beanId="selectXML" />			
					</td>			
				</tr>
		
				<tr>	
					<td class="label">				
						<fmt:message key="site.region" />							
					</td>
					<td>				
						<!-- 地市 -->			
						<select name="region" id="region"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="requestCountryOnCityChange();">
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
						<fmt:message key="site.city" />	
					</td>
					<td>
						<!-- 县区 -->			
						<select name="city" id="city" 
							alt="allowBlank:true,vtext:'请选择所在县区'"
							onchange="requestGridOnChangeCountry();">
							<option value="">
								--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">
						<fmt:message key="site.grid" />
					</td>
					<td>
						<!-- 所属网格 -->
						<select name="gridNumber" id="gridNumber" 
							alt="allowBlank:false,vtext:'请选择所属网格'">
							<option value="">
								--请选择所属网格--
							</option>				
						</select>
					</td>
					<td class="label">	
						<fmt:message key="site.siteLevle" />
					</td>
					<td>	
						<html:select styleClass="select max" styleId="siteLevle" property="siteLevle">
								<html:option value="">--请选择站点等级--</html:option>
					    	<c:forEach var="cycle" items="${cycleList}">
					    		<html:option value="${cycle.checkLevel}">${cycle.checkLevel}</html:option>
					    	</c:forEach>
					    </html:select>
					</td>
				</tr>
				
				<tr>
					<td class="label">				
						<fmt:message key="site.siteType" />							
					</td>
					<td colspan='3'>				
						<eoms:comboBox  name="siteType" id="siteType" defaultValue="${siteForm.siteType}" initDicId="12102" alt="allowBlank:false" />
					</td>
				</tr>
				
			</table>
			
			<table align="center">
			    <tr>
				    <td width="200">
				    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
						<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
					</td>
				</tr>
			</table>	
		</html:form>
		
		<html:form action="/sites.do" method="post" styleId="lineForm">	
			<table>
			    <tr>
			        <td>
					    <c:if test="${param.flag!='search'}">
							<c:out value="${buttons}" escapeXml="false" />
						</c:if>
					</td>
				 </tr>
			</table>
				
			<display:table name="siteList" cellspacing="0" cellpadding="0"
				id="siteList" pagesize="${pageSize}" class="table siteList"
				export="false" 
				requestURI="${app}/partner/net/sites.do?method=search"
				sort="list" partialList="true" size="resultSize"  
				decorator="com.boco.eoms.partner.net.displaytag.support.SiteDisplaytagDecorator">
			
				<display:column property="id" title="<input type='checkbox' onclick='javascript:choose();'>" style="width:3%" />
				<display:setProperty name="css.table" value="0," />	
				
				<display:column property="siteName" sortable="true"
						headerClass="sortable" titleKey="site.siteName" paramId="siteNo" paramProperty="siteNo"/>
			
				<display:column property="siteNo" sortable="true"
						headerClass="sortable" titleKey="site.siteNo"  paramId="id" paramProperty="id"/>
				<!-- 所在地市 -->
				<display:column  sortable="true" headerClass="sortable" titleKey="line.region">
					<eoms:id2nameDB id="${siteList.region}" beanId="tawSystemAreaDao" />
				</display:column>			
			
				<!-- 所在县区 -->
				<display:column  sortable="true" headerClass="sortable" titleKey="line.city">
					<eoms:id2nameDB id="${siteList.city}" beanId="tawSystemAreaDao" />
				</display:column>			
				
			
				<display:column property="grid" sortable="true"
						headerClass="sortable" titleKey="site.grid"  paramId="id" paramProperty="id"/>
				<display:column sortable="true" headerClass="sortable" titleKey="site.siteType">
					<c:if test="${siteList.siteType!=null}">
						<eoms:id2nameDB id="${siteList.siteType}"  beanId="IItawSystemDictTypeDao" />
					</c:if>
				</display:column>
			
				<display:column property="partnername" sortable="true"
						headerClass="sortable" titleKey="site.provider"  paramId="id" paramProperty="id"/>
				<display:column property="longitude" sortable="true"
						headerClass="sortable" titleKey="site.longitude"  paramId="id" paramProperty="id"/>
			
				<display:column property="latitude" sortable="true"
						headerClass="sortable" titleKey="site.latitude"  paramId="id" paramProperty="id"/>
						
				<display:column sortable="true" headerClass="sortable" titleKey="site.siteLevle"  paramId="id" paramProperty="id">
					${siteList.siteLevle}
				</display:column>
				<c:if test="${param.flag!='search'}">
					<display:column title="操作" headerClass="imageColumn">
						
						    <a href='${app}/partner/net/sites.do?method=edit&idSite=${siteList.id}' target='_blank'>
						         修改</a>
						    <a href='${app}/partner/net/sites.do?method=detailWithInspect&siteId=${siteList.id}' target='_blank'>
						         查看</a>
					    	
					</display:column> 
				</c:if>	
				<c:if test="${param.flag=='search'}">
					<display:column title="操作" headerClass="imageColumn">
						    <a href='${app}/partner/net/sites.do?method=detailWithInspect&siteId=${siteList.id}' target='_blank'>
						           <img src="${app}/images/icons/search.gif"/></a></a>
					</display:column> 
				</c:if>	
			
				<display:setProperty name="paging.banner.item_name" value="site" />
				<display:setProperty name="paging.banner.items_name" value="sites" />
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
	</div>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>