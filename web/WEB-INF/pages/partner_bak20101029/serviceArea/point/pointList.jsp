<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
		  
		  
		  	  <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	
</c:set>

<script language=javascript>
<!--
function newAdd(){

    window.location.href=eoms.appPath+'/partner/serviceArea/points.do?method=add';

}
  function validateForm(frm){
		if(frm.pointName.value=="" && frm.region.value== "" && frm.city.value== "" && frm.grid.value =="" && frm.provider.value ==""){
			alert("请至少填写一项查询信息");
			return false;
		}
		
		return true; 
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
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
		    delAllOption("grid");
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
									
									var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
									
									if(con==1){
										var city = '${pointForm.city}';
									
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
		    delAllOption("grid");
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
								
								var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
								if(con==1){
										var provider = '${pointForm.provider}';
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
		
		
		
//地市、县区、合作伙伴 联动 网格		
function changeGrid(con){
    var cityValue = document.getElementById("city").value;
    var providerValue = document.getElementById("provider").value;
    if(cityValue!=''&&providerValue!=''){
	    delAllOption("grid");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue+"&cityValue="+cityValue;
		
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
		     					var countyName = "grid";
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
										var grid = '${pointForm.grid}';
										if(grid!=''){
											document.getElementById("grid").value = grid;
										}
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
    }
    
}	
	

//-->
</script>


<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">



	<html:form action="/points.do?method=searchX" styleId="pointForm" method="post" >
		<table align="center">
			<tr>
				<!-- 数据点名称 -->
				<td>
					<fmt:message key="point.pointName" />:
				</td>
				<td >
					<input type="text" id=pointName name="pointName" class="text" value="${pointForm.pointName}" maxlength="32">&nbsp;&nbsp;
				</td>
				<!-- 所属地市 -->
				<td>
					<fmt:message key="point.region" />:
				</td>
				<td >
					<!-- 地市 -->			
					<select name="region" id="region"
						alt="allowBlank:false,vtext:'请选择所在地市'"
						onchange="changeCity();">
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
				<!-- 所属县区 -->
				<td>
					<fmt:message key="point.city" />:
				</td>
				<td >
					<!-- 县区 -->			
					<select name="city" id="city" 
						alt="allowBlank:true,vtext:'请选择所在县区'" 
						onchange="changePartner();">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
				</td>

				<!-- 合作伙伴 -->
				<td>
					<fmt:message key="point.provider" />:
				</td>
				<td >
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'" 
						onchange="changeGrid();">
						<option value="">
							--请选择合作伙伴--
						</option>				
					</select>
				</td>

			</tr>			

			<tr>

				<!-- 所属网格 -->
				<td>
					<fmt:message key="point.grid" />:
				</td>
				<td >
					<!-- 所属网格 -->
					<select name="grid" id="grid" 
						alt="allowBlank:false,vtext:'请选择所属网格'" >
						<option value="">
							--请选择所属网格--
						</option>				
					</select>
				</td>


			</tr>	
			



			<tr>
				<!-- 提交按钮 -->
				<td>
					<input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
				</td>
			</tr>
			
		</table>
	</html:form>









<content tag="heading">
	<fmt:message key="point.list.heading" />
</content>

	<display:table name="pointList" cellspacing="0" cellpadding="0"
		id="pointList" pagesize="${pageSize}" class="table pointList"
		export="false"
		requestURI="${app}/partner/serviceArea/points.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="pointName" sortable="true"
			headerClass="sortable" titleKey="point.pointName"  paramId="id" paramProperty="id"/>

			
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="point.region">
		<eoms:id2nameDB id="${pointList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="point.city">
		<eoms:id2nameDB id="${pointList.city}" beanId="tawSystemAreaDao" />
	</display:column>			
			

	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="point.grid"  paramId="id" paramProperty="id"/>

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="point.provider"  paramId="id" paramProperty="id"/>


	<!-- 新增人员姓名 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="point.userNameNew">
		<eoms:id2nameDB id="${pointList.userNameNew}" beanId="tawSystemUserDao" />
	</display:column>
	
	<display:column property="timeNew" sortable="true"
			headerClass="sortable" titleKey="point.timeNew" format="{0,date,yyyy-MM-dd HH:mm:ss}"  paramId="id" paramProperty="id"/>

	<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
			
			
			
		<c:choose>
			<c:when test="${param.flag!='search'}">
			
				<a href='${app}/partner/serviceArea/points.do?method=detail&id=${pointList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
				
			</c:when>
			<c:otherwise>
				<a href='${app}/partner/serviceArea/points.do?method=detail&flag=search&id=${pointList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			
			</c:otherwise>
		</c:choose>				
	</display:column>


		<display:setProperty name="paging.banner.item_name" value="point" />
		<display:setProperty name="paging.banner.items_name" value="points" />
	</display:table>

	
	
	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>