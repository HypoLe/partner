<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
//将设置下拉框只读

function setReadOnly(obj){
	//var obj = document.getElementById(obj_id);
	obj.onmouseover = function(){
	
	   obj.setCapture();
	}
	
	obj.onmouseout = function(){
	
	   obj.releaseCapture();
	
	}
	
	obj.onfocus = function(){
	
	   obj.blur();
	
	}
	obj.onbeforeactivate = function(){
	
	   return false;
	
	}
	  }
	  //将指定的下拉框从只读设置为可选
	  function setSelectEnable(obj){
	
	
	obj.onmouseover = function(){
	
	   //obj.setCapture();
	}
	
	obj.onmouseout = function(){
	
	   //obj.releaseCapture();
	
	}
	
	obj.onfocus = function(){
	
	  // obj.blur();
	
	}
	obj.onbeforeactivate = function(){
	
	   return true;
	
	}
}
</script>
<script type="text/javascript">
var tr = true;
Ext.onReady(function() {  
	v = new eoms.form.Validation({form:'siteForm'});
});
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

function valsite()
{
	var obj = document.getElementById("siteNo");
    var providerValue = document.getElementById("partnerid").value;
	if(!/^\d+$/.test(obj.value)){
		document.getElementById("message").innerHTML = "<font color='red'>对不起,站点编号只能是数字</font>";
		return document.getElementById("siteNo").value='';
	}
	
	var siteNo = encodeURIComponent(document.getElementById("siteNo").value);
	var url = eoms.appPath+"/partner/net/sites.do?method=validationSiteNo"+"&siteNo="+siteNo;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		params:{providerValue:providerValue},
		success: function ( result, request ) { 
			var res = result.responseText;
			var json = eval(res);
			if(json[0].message == true){
    				if(obj.value!=""){
    					document.getElementById("message").innerHTML = "<font color='green'>此站号可以使用</font>";
    					tr = true;
    				}else{
    					obj.focus();
    				}
    			}else{
    				document.getElementById("message").innerHTML = "<font color='red'>对不起,此站点编号已存在</font>";
    				tr = false;
   				}		
		},
		failure: function (result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});		
}

function sub(){
	if(tr){
	}else{
		alert("已存在的站点编号");
		return false;
	}
	
	//经度大于纬度判断 add by WangGuangping 2012-2-29 14:23:15
	var longitude = document.getElementById("longitude").value;
	var latitude = document.getElementById("latitude").value;
	if(longitude - latitude <= 0){
		alert("中国境内,【站点经度】必须要大于【站点纬度】！");
		return false;
	}
	
	if(v.check()){
       $("siteForm").submit();
	}	
}

function delAllOption(elementid){
    var ddlObj = document.getElementById(elementid);//获取对象
     for(var i=ddlObj.length-1;i>=0;i--){
         ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
     }
}

//地市、县区联动合作伙伴		
function changePartner(con)
{    
	var gridNumber = document.getElementById("gridNumber").value;
	var url="<%=request.getContextPath()%>/partner/net/sites.do?method=changePartner&gridId="+gridNumber;
	
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
			var res = result.responseText;
			if(res.indexOf("<\/SCRIPT>")>0){
		  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
			}
			var json = eval(res);
			
			document.getElementById("partnername").value = json[0].name;
			document.getElementById('partnerid').value = json[0].id;
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
		
function changeCity(con)
{    
    delAllOption("city");//地市选择更新后，重新刷新县区
	var region = document.getElementById("region").value;
	var url="../serviceArea/lines.do?method=changeCity&city="+region;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) { 
			res = result.responseText;
			if(res.indexOf("[{")!=0){
						res = "[{"+result.responseText;
			}
			if(res.indexOf("<\/SCRIPT>")>0){
		  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
			}
			
			var json = eval(res);
			var cityName = "city";
	
			var arrOptions = json[0].cb.split(",");
			var obj=$(cityName);
					
			var i=0;
			var j=0;
			for(i=0;i<arrOptions.length;i++){
				var opt=new Option(arrOptions[i+1],arrOptions[i]);
				obj.options[j]=opt;
				j++;
				i++;
			}
			
			if(con==1){
				var city = '${siteForm.city}';
				if(city!=''){
					document.getElementById("city").value = city;
				}	
				if(_city!='' && _city!=null){
					document.getElementById("city").value = _city;
				}	
				changeGrid(con);						
			}	
		},
		failure: function ( result, request) { 
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
		} 
	});
}
function changeGrid(con){
    var region = document.getElementById("region").value;
    var city = document.getElementById("city").value;
    if(city==''){
    	delAllOption("gridNumber");
    	var gridNumber = "gridNumber";
		var obj=$(gridNumber);
		var i=0;
		var j=0;
		var opt=new Option("--请选择网格--","");
		obj.options[j]=opt;
    }
    if(region!=''&&city!=''){    //  &&deptId!=''  "&deptId="+deptId+
		var url="<%=request.getContextPath()%>/partner/net/sites.do?method=changeGrid&areaId="+region+"&city="+city;
		delAllOption("gridNumber");
		Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) {
			res = result.responseText;
			if(res.indexOf("[{")!=0){
 					res = "[{"+result.responseText;
			}
			if(res.indexOf("<\/SCRIPT>")>0){
		  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
			}
				var json = eval(res);
   					var countyName = "gridNumber";
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
				var grid = '${siteForm.gridNumber}';
				if(grid != "") {
					document.getElementById("gridNumber").value = grid;
				}
				if(_gridNumber != "" && _gridNumber != null) {
					document.getElementById("gridNumber").value = _gridNumber;
				}
				changePartner(250);
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
    }
}	
//修改时，自动加载原来的地市县区显示在修改页面
var _region,_city,_gridNumber,_partnerid;	
window.onload = function(){
	var gridId;
	if(opener != null && null != opener.document.getElementById("gridId") ) {
		gridId = opener.document.getElementById("gridId").value
	}
	if(gridId != "" && gridId != null) {
		_region = opener.document.getElementById("region").value;
	    _city = opener.document.getElementById("city").value;
	    _gridNumber = opener.document.getElementById("gridId").value;
	    _partnerid = opener.document.getElementById("partnerid").value;
		
		if(_region!=''){
			document.getElementById("region").value = _region;
			changeCity(1);
		}
		if(_city!=''){
			changeGrid(1);
		}
		if(_gridNumber!=''){
			document.getElementById("gridNumber").value = _gridNumber;		
		}
		
		setReadOnly(document.getElementById("city"));
		setReadOnly(document.getElementById("region"));
		setReadOnly(document.getElementById("gridNumber"));
	} else {
	    var region = '${siteForm.region}';
	    var city = '${siteForm.city}';
	    var gridNumber = '${siteForm.gridNumber}';
	    var specialtyType = '${siteForm.specialtyType}';
	    var siteType = '${siteForm.siteType}';
		
		if(specialtyType != '') {
	 		document.getElementById("specialtyType").value = specialtyType;
		}
	   
		if(region!=''){
			document.getElementById("region").value = region;
			changeCity(1);
		}
		if(city!=''){
			changeGrid(1);
			
		}
		if(gridNumber!=''){
			document.getElementById("gridNumber").value = gridNumber;		
		}
		if(siteType!=''){
			document.getElementById("siteType").value = siteType;
		}
	}
}
</script>

<html:form action="/sites.do?method=save" styleId="siteForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="site.form.heading"/></div>
	</caption>
	<input type="hidden" name="partnerid" id="partnerid" value="${siteForm.partnerid}"/>
	<html:hidden property="grid" styleId="grid"/>
	<tr>
		<td class="label">
			<fmt:message key="site.siteName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="siteName" styleId="siteName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.siteName}"/>
		</td>
		<td class="label">
			站点编号&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="siteNo" styleId="siteNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'',maxLength:64" value="${siteForm.siteNo}" onblur="valsite()" />
						<span id="message"></span>
		</td>		
	</tr>
	
	<tr>

		<td class="label">
			<fmt:message key="site.region" />&nbsp;<font color='red'>*</font>
		</td>
	
		<td class="content">						
			<!-- 地市 -->			
			<select name="region" id="region"
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
			<fmt:message key="site.city" />&nbsp;<font color='red'>*</font>
		</td>
		
		<td class="content">
			<!-- 县区 -->			
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'"
				onchange="changeGrid(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>

		</td>		
		
	</tr>

	<tr>
			
		<td class="label">
			<fmt:message key="site.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 所属网格 -->
			<select name="gridNumber" id="gridNumber" 
				alt="allowBlank:false,vtext:'请选择所属网格'" onchange="changePartner(0)" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
		</td>
		<td class="label">
			<fmt:message key="site.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 合作伙伴 -->		
			<html:text property="partnername" styleId="partnername"
								styleClass="text medium" readonly="true"
								alt="allowBlank:false,vtext:''" value="${siteForm.partnername}"/>
				<font color='greed'>根据地市县区网格自动填写</font>	
			<%-- 
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'"
				onchange="changeGrid(0);">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
			--%>			
		</td>	
	</tr>
		
	<tr>
		<td class="label">
			<fmt:message key="site.siteType" />
		</td>
		<td class="content">
			<eoms:comboBox  name="siteType" id="siteType" defaultValue="${siteForm.siteType}" initDicId="12102" alt="allowBlank:false" />
		</td>
		
		<td class="label">
			专业类型&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<%--<eoms:dict key="dict-partner-serviceArea" dictId="specialtyType" isQuery="false"  alt="allowBlank:false,vtext:''"
				defaultId="${siteForm.specialtyType}" selectId="specialtyType" beanId="selectXML" />
		--%>
			<select class="select max" id="specialtyType" name="specialtyType"  alt="allowBlank:false,vtext:'请选择专业'">
				<option value="">--请选择专业--</option>
				<option value="base">基站</option>
			</select>
		</td>
	</tr>	
	

	<tr>
		<td class="label">
			<fmt:message key="site.longitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="longitude" styleId="longitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="${siteForm.longitude}" />
		</td>
		<td class="label">
			<fmt:message key="site.latitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="latitude" styleId="latitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="${siteForm.latitude}" />
		</td>
	

	</tr>
	<tr>
		<td class="label">
			<fmt:message key="site.maintainType" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict  key="dict-partner-serviceArea" dictId="maintainType" isQuery="false"  alt="allowBlank:false,vtext:''"
				defaultId="${siteForm.maintainType}" selectId="maintainType" beanId="selectXML" />
		</td>			
		<td class="label">
			<fmt:message key="site.siteLevle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			 <!--<eoms:comboBox name="siteLevle" id="siteLevle" initDicId="1121106" defaultValue="${siteForm.siteLevle}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/> -->
			    
			    <html:select property="siteLevle" styleClass="select max">
			    	<c:forEach var="cycle" items="${cycleList}">
			    		<html:option value="${cycle.checkLevel}">${cycle.checkLevel}</html:option>
			    	</c:forEach>
			    </html:select>
		</td>	
	</tr>
	

	<tr>
		<td class="label">
			站点客户经理
		</td>
		<td class="content">
	          <input type="text" size="20" class="text" name="siteManager" id="siteManager" value="${siteForm.siteManager}" />		
		</td>
		<td class="label">
			站点客户经理电话
		</td>
		<td class="content">
	          <input type="text" size="20" class="text" name="siteManagerTele" id="siteManagerTele" value="${siteForm.siteManagerTele}" />	
		</td>
	</tr>
	<tr>
		<td class="label">
			站点联系人
		</td>
		<td class="content">
	          <input type="text" size="20" class="text" name="siteLinkman" id="siteLinkman" value="${siteForm.siteLinkman}" />		
		</td>
		<td class="label">
			站点联系人电话
		</td>
		<td class="content">
	          <input type="text" size="20" class="text" name="siteLinkmanTele" id="siteLinkmanTele" value="${siteForm.siteLinkmanTele}" />	
		</td>
	</tr>
 	<tr>
		<td class="label">
			站址信息
		</td>
		<td class="content" colspan="3">
			<textarea name="address" id="address" rows="3" style="width:98%;"  alt="allowBlank:true,vtext:'请填写站址信息',maxLength:255">${siteForm.address}</textarea>			
		</td>
		
	</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick=" sub();"/>
			<c:if test="${not empty siteForm.id}">
					<input type="button" class="btn" value='<fmt:message key="button.back"/>' onclick="javascript:parent.window.history.back();" />
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="addUser" value="${siteForm.addUser}" />
<html:hidden property="addTime" value="${siteForm.addTime}" />
<html:hidden property="updateUser" value="${siteForm.updateUser}" />
<html:hidden property="delTime" value="${siteForm.delTime}" />
<html:hidden property="delUser" value="${siteForm.delUser}" />
<html:hidden property="updateTime" value="${siteForm.updateTime}" />
<html:hidden property="id" styleId="id" value="${siteForm.id}" />
<html:hidden property="gridNumber" styleId="id" value="${siteForm.gridNumber}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>	