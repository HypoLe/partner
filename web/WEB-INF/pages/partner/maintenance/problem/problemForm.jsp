<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'problemForm'});
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
		
		

</script>

<html:form action="/problems.do?method=save" styleId="problemForm" method="post"> 
<font color='red'>*</font>号为必填内容
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="problem.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="problem.city" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="problem.county" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="problem.partner" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="problem.reportDept" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
<!-- 		<html:text property="reportDept" styleId="report_dept"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:64" value="${problemForm.reportDept}" />
 -->	
<!-- 		<eoms:id2nameDB beanId="tawSystemDeptDao" id="${sessionScope.sessionform.deptid}"/>		
			<html:hidden property="reportDept" value="${sessionScope.sessionform.deptid}" />	
 -->				
			<eoms:dict key="dict-partner-maintenance" dictId="reportDept" isQuery="false" alt="allowBlank:false,vtext:'请选择是否开放(字典)...'"
				defaultId="${problemForm.reportDept}" selectId="reportDept" beanId="selectXML" />
						
		</td>
	</tr>
	<tr>
		<!-- 上报人姓名 取当前用户 -->
		<td class="label">
			<fmt:message key="problem.reportPerson" />
		</td>
		<td class="content" colspan=3>

			<html:hidden property="reportPerson" value="${problemForm.reportPerson}" />
			<eoms:id2nameDB id="${problemForm.reportPerson}" beanId="tawSystemUserDao" />

		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="problem.problem" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan=3>
						
			<textarea name="problem" id="problem" cols="50" 
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${problemForm.problem}</textarea>
						
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="problem.handlingInfo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan=3>
						
			<textarea name="handlingInfo" id="handlingInfo" cols="50" 
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${problemForm.handlingInfo}</textarea>
						
		</td>
	
	</tr>


			<html:hidden property="userNameDel" value="${problemForm.userNameDel}" />

			<html:hidden property="timeDel" value="${problemForm.timeDel}" />


</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty problemForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除？')){
						var url=eoms.appPath+'/partner/maintenance/problems.do?method=remove&id=${problemForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${problemForm.id}" />
</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var city = '${problemForm.city}';
		
	if(city!=''){
 		document.getElementById("city").value = city;
		changeCity(1);
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>