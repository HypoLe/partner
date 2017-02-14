<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'checkForm'});
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
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}

//县区联动
function changeCity(con)
		{    
		    delAllOption("county");//地市选择更新后，重新刷新县区
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
		

	/**
	*选择抽查对象（基站） 可多选
	*/
	var xmlHttp;
	function createXMLHttpRequest() {
		//表示当前浏览器不是ie,如ns,firefox
		if(window.XMLHttpRequest) {
			xmlHttp = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	
	function getRequire(site) {
		if (site != "") {
		
			var url = eoms.appPath+'/partner/serviceArea/sites.do?method=getSite&site='+site;
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
								    res = result.responseText;
									  if(res.indexOf("<\/SCRIPT>")>0){
								  	  res = res.substring(res.indexOf("<\/SCRIPT>")+10,res.length);
									  }
									  $('sample').value =  res ;
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
		
		$('sampleNo').value = site;	
	}
	
	function callback() {
	    if (xmlHttp.readyState == 4) {
		  if (xmlHttp.status == 200) {
		  	var xmlText = xmlHttp.responseText;
		  	
			document.getElementById('sample').value =  xmlText ;
		   }
	   }
	}

	//选择抽查对象的详情页面（基站名称）可多选
	function openSample(){
		 window.open(eoms.appPath+'/partner/serviceArea/sites.do?method=searchSample');
	}

</script>

<html:form action="/checks.do?method=save" styleId="checkForm" method="post"> 
<font color='red'>*</font>号为必填内容
<fmt:bundle basename="com/boco/eoms/partner/maintenance/config/applicationResource-partner-maintenance">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="check.form.heading"/></div>
	</caption>

	<tr>
	
		<!-- 抽查人 当前用户 -->
		<td class="label">
			<fmt:message key="check.person" />
		</td>
		<td class="content" colspan=3>
			<html:hidden property="person" value="${checkForm.person}" />
			<eoms:id2nameDB id="${checkForm.person}" beanId="tawSystemUserDao" />
		</td>

	</tr>


	<tr>
	
		<td class="label">
			<fmt:message key="check.city" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="check.county" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="county" id="county" 
				alt="allowBlank:false,vtext:'请选择所在县区'">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
		</td>

	</tr>


	<tr>
	
		<td class="label">
			<fmt:message key="check.speciality" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				
			<eoms:comboBox name="speciality" id="speciality" initDicId="1121201" defaultValue="${checkForm.speciality}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
		</td>

		<td class="label">
			<fmt:message key="check.checkTime" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		
	          <input type="text" size="20" readonly="true" class="text" 
                name="checkTime" id="checkTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                alt="allowBlank:false,vtext:'please...'" value="${checkForm.checkTime}" />
		</td>	

	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="check.sample" />&nbsp;<font color='red'>*</font>
		</td>
		<!-- 选择抽查对象（可多选）去基站取值 -->
		<td class="content" colspan=3>
			<textarea name="sample" id="sample" cols="50" onclick="openSample()" readonly="true"
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${checkForm.sample}</textarea>

		</td>
			<input type="hidden" name="sampleNo" id="sampleNo" value = "${checkForm.sampleNo }"/>

	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="check.condition" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan=3>
			<!-- 抽查情况 -->
			<textarea name="condition" id="condition" cols="50" 
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${checkForm.condition}</textarea>
						
		</td>
		
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="check.remark" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<textarea name="remark" id="remark" cols="50" 
				class="textarea max" alt="allowBlank:false,vtext:'',maxLength:255">${checkForm.remark}</textarea>
		</td>
		
	</tr>


			<html:hidden property="userNameDel" value="${checkForm.userNameDel}" />

			<html:hidden property="timeDel" value="${checkForm.timeDel}" />

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty checkForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定删除？')){
						var url=eoms.appPath+'/partner/maintenance/checks.do?method=remove&id=${checkForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${checkForm.id}" />
</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var city = '${checkForm.city}';
		
	if(city!=''){
 		document.getElementById("city").value = city;
		changeCity(1);
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>