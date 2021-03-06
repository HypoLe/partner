<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script language=javascript>
<!--  
	Ext.onReady(function() {
		v = new eoms.form.Validation({form:'malfunctionForm'});
	});
  function validateForm(){
      var frm = document.forms[0];
      if( frm.malfunctionBeginTime.value == "" && frm.malfunctionEndTime.value != "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      if( frm.malfunctionBeginTime.value != "" && frm.malfunctionEndTime.value == "" ){
          alert("请填写正确的时间段！");
          return false;      
      }
      
      return true;
  }
  
  
  	function sub(){
		
		var validate = validateForm();
		
		if(validate){
			if(v.check())
			 $("malfunctionForm").submit();
		}else{
			return false;
			
		}
		
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
		    delAllOption("county");//地市选择更新后，重新刷新县区
		    delAllOption("partner");//地市选择更新后，重新刷新合作伙伴
			var city = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
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
									var countyName = "county";
						
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
									var providerName = "partner";
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
										var county = '${problemForm.county}';
										if(county!=''){
											document.getElementById("county").value = county;
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
		    delAllOption("partner");//地市选择更新后，重新刷新合作伙伴
			//地市
			var cityValue = document.getElementById("city").value;
			//县区
			var countyValue = document.getElementById("county").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changePartner&region="+cityValue+"&city="+countyValue;
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
								
								var providerName = "partner";
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
										var partner = '${problemForm.partner}';
										if(partner!=''){
											document.getElementById("partner").value = partner;
										}
										
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
		
	
		
 function newAdd(){

    window.location.href=eoms.appPath+'/partner/maintenance/malfunctions.do?method=add';

}		

//-->
</script>

<c:set var="buttons">

   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
		
	</c:set>

<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">


	<html:form action="/malfunctions.do?method=searchX" styleId="malfunctionForm" method="post" >
		<table align="center">
			<tr>
				<!-- 地市 -->
				<td>
					<fmt:message key="malfunction.city" />:
				</td>
				<td style="width:80px">
					<select name="city" id="city"
						alt="allowBlank:true,vtext:'请选择所在地市'"
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

				<!-- 县区 -->
				<td>
					<fmt:message key="malfunction.county" />:
				</td>
				<td style="width:80px">
					<select name="county" id="county" 
						alt="allowBlank:true,vtext:'请选择所在县区'" 
						onchange="changePartner();">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
				</td>
				
				<!-- 合作伙伴 -->
				<td>
					<fmt:message key="malfunction.partner" />:
				</td>
				<td style="width:80px">
					<!-- 合作伙伴 -->			
					<select name="partner" id="partner" 
						alt="allowBlank:true,vtext:'请选择合作伙伴'" >
						<option value="">
							--请选择合作伙伴--
						</option>				
					</select>
				</td>
				
			</tr>

			
			<tr>
				<!-- 上报开始时间 -->
			  	<td>
					上报时间段：&nbsp;
				</td>
				<td class="content" style="width:80">
			          <input type="text" size="20" readonly="true" class="text" 
			               name="malfunctionBeginTime" id="malfunctionBeginTime"
			               onclick="popUpCalendar(this,this,null,null,null,true,-1);"
			               alt=" vtype:'lessThen',link:'malfunctionEndTime', allowBlank:true,vtext:'结束时间要大于开始时间'"
			               value="${malfunctionBeginTime}" />
				</td>
				<!-- 上报结束时间 -->
				<td>
					至
				</td>
				<td class="content" style="width:80">
			          <input type="text" size="20" readonly="true" class="text" 
		                name="malfunctionEndTime" id="malfunctionEndTime"
		                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
		                alt=" vtype:'moreThen',link:'malfunctionBeginTime',allowBlank:true,vtext:'结束时间要大于开始时间'"
		                value="${malfunctionEndTime}" />
				</td>
			</tr>

			
			<tr>
				<!-- 提交按钮 -->
				<td>
					<input type="button" class="btn" value="<fmt:message key="button.search"/>"  onclick="sub();"/>
				</td>
			</tr>
			
		</table>
	</html:form>


<content tag="heading">
	<fmt:message key="malfunction.list.heading" />
</content>

	<display:table name="malfunctionList" cellspacing="0" cellpadding="0"
		id="malfunctionList" pagesize="${pageSize}" class="table malfunctionList"
		export="false"
		requestURI="${app}/partner/maintenance/malfunctions.do?method=search"
		sort="list" partialList="true" size="resultSize">


	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="malfunction.city">
		<eoms:id2nameDB id="${malfunctionList.city}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="malfunction.county">
		<eoms:id2nameDB id="${malfunctionList.county}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 设备厂家 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="malfunction.devicesFactory">
		<eoms:id2nameDB id="${malfunctionList.devicesFactory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
			

	<!-- 设备名称 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="malfunction.devicesName">
		<eoms:id2nameDB id="${malfunctionList.devicesName}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
			
			
	<!-- 故障级别 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="malfunction.malfunctionLevel">
		<eoms:id2nameDB id="${malfunctionList.malfunctionLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>			
			

	<display:column property="beginTime" sortable="true"
			headerClass="sortable" titleKey="malfunction.beginTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"  paramId="id" paramProperty="id"/>




	<!-- 上报人 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="malfunction.reportPerson">
			<eoms:id2nameDB id="${malfunctionList.reportPerson}" beanId="tawSystemUserDao" />
	</display:column>

	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/maintenance/malfunctions.do?method=detail&id=${malfunctionList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/maintenance/malfunctions.do?method=detail&flag=search&id=${malfunctionList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:otherwise>
	</c:choose>
			

		<display:setProperty name="paging.banner.item_name" value="malfunction" />
		<display:setProperty name="paging.banner.items_name" value="malfunctions" />
	</display:table>



	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>