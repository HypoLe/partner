<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'malfunctionForm'});
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
										var county = '${malfunctionForm.county}';
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
										var partner = '${malfunctionForm.partner}';
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
		
	

</script>

<html:form action="/malfunctions.do?method=save" styleId="malfunctionForm" method="post"> 
<font color='red'>*</font>号为必填内容
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="malfunction.form.heading"/></div>
	</caption>
	
	<tr>
		<td class="label">
			<fmt:message key="malfunction.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">						
			<!-- 地市 -->			
			<select name="city" id="city"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
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
			<fmt:message key="malfunction.county" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="county" id="county" 
				alt="allowBlank:false,vtext:'请选择所在县区'" 
				onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
						
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.partner" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 合作伙伴 -->			
			<select name="partner" id="partner" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'" >
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
		</td>

		<td class="label">
			<fmt:message key="malfunction.processPeople" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="processPeople" styleId="processPeople"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${malfunctionForm.processPeople}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.devicesFactory" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">

			<eoms:comboBox name="devicesFactory" id="devicesFactory" initDicId="1121202" defaultValue="${malfunctionForm.devicesFactory}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
				
						
		</td>

		<td class="label">
			<fmt:message key="malfunction.devicesName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
					
			<eoms:comboBox name="devicesName" id="devicesName" initDicId="1121203" defaultValue="${malfunctionForm.devicesName}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
						
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.devicesLevel" />
		</td>
		<td class="content">
					
			<eoms:comboBox name="devicesLevel" id="devicesLevel" initDicId="1121204" defaultValue="${malfunctionForm.devicesLevel}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
						
		</td>

		<td class="label">
			<fmt:message key="malfunction.malfunctionLevel" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
						
			<eoms:comboBox name="malfunctionLevel" id="malfunctionLevel" initDicId="1121205" defaultValue="${malfunctionForm.malfunctionLevel}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
						
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.beginTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="beginTime" id="beginTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt="vtype:'lessThen',link:'endTime', allowBlank:true,vtext:'结束时间要大于开始时间'" value="${malfunctionForm.beginTime}" />
		</td>

		<td class="label">
			<fmt:message key="malfunction.endTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="endTime" id="endTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt="vtype:'moreThen',link:'beginTime', allowBlank:true,vtext:'结束时间要大于开始时间'" value="${malfunctionForm.endTime}" />
		</td>
	</tr>



	<tr>
		<td class="label">
			<fmt:message key="malfunction.malfunctionLast" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="malfunctionLast" styleId="malfunctionLast"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${malfunctionForm.malfunctionLast}" />
		</td>

		<td class="label">
			<fmt:message key="malfunction.malfunctionState" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="malfunctionState" styleId="malfunctionState"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:64" value="${malfunctionForm.malfunctionState}" />
		</td>
	</tr>


	
	<tr>
		<!-- 上报人 取当前用户 -->
		<td class="label">
			<fmt:message key="malfunction.reportPerson" />
		</td>
		<td class="content" colspan=3>

			<html:hidden property="reportPerson" value="${malfunctionForm.reportPerson}" />
			<eoms:id2nameDB id="${malfunctionForm.reportPerson}" beanId="tawSystemUserDao" />

		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="malfunction.causeAnalysis" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan=3>
						
			<textarea name="causeAnalysis" id="causeAnalysis" cols="50" 
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${malfunctionForm.causeAnalysis}</textarea>
						
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="malfunction.handlingInfo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan=3>
			<textarea name="handlingInfo" id="handlingInfo" cols="50" 
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${malfunctionForm.handlingInfo}</textarea>
						
		</td>
	</tr>

			<html:hidden property="userNameDel" value="${malfunctionForm.userNameDel}" />

			<html:hidden property="timeDel" value="${malfunctionForm.timeDel}" />
	

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty malfunctionForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定删除？')){
						var url=eoms.appPath+'/partner/maintenance/malfunctions.do?method=remove&id=${malfunctionForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${malfunctionForm.id}" />
</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var city = '${malfunctionForm.city}';
		
	if(city!=''){
 		document.getElementById("city").value = city;
		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>