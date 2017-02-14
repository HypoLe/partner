<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<script type="text/javascript">
Ext.onReady(function() {
    v = new eoms.form.Validation({form:'linesForm'});
});

function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i); //firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
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

//地市 联动 县区
function changeCity(con){    
		    delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
									
									if(con==1){
										var city = '${lines.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}
									changeGrid(con);					
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
		
//网格联动合作伙伴		
function changePartner(con)
		{    
			var grid = document.getElementById("grid").value;
			var url="<%=request.getContextPath()%>/partner/net/sites.do?method=changePartner&gridId="+grid;
			
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
								document.getElementById('partner').value = json[0].id;
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}

function changeGrid(con){
    var region = document.getElementById("region").value;
   // var deptId = document.getElementById("dept_id").value;
    var city = document.getElementById("city").value;
    if(city==''){
    	delAllOption("grid");
    	var gridNumber = "grid";
		var obj=$(grid);
		var i=0;
		var j=0;
		var opt=new Option("--请选择网格--","");
		obj.options[j]=opt;
    }
    if(region!=''&&city!=''){    //  &&deptId!=''  "&deptId="+deptId+
		var url="<%=request.getContextPath()%>/partner/net/sites.do?method=changeGrid&areaId="+region+"&city="+city;
		delAllOption("grid");
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
											var grid = '${lines.grid}';
												document.getElementById("grid").value = grid;
										}
										 changeCompany(con);
										 
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}	

function changeCompany(con){
    var grid = document.getElementById("grid").options[document.getElementById("grid").selectedIndex].text; 
    var gridNumber = document.getElementById("grid").value;
   // document.getElementById("grid").value = grid;
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
										document.getElementById('partner').value = partnerId;
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

<html:form action="/lines.do?method=save" styleId="linesForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/netresource/line/config/ApplicationResources-line">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="line.edit.heading"/></div>
    </caption>
                                                    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.no" />
        </td>  
        <td class="content">
            <html:text property="lineNo" styleId="lineNo"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路编号'" value="${lines.lineNo}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.name" />
        </td>
        <td class="content">
            <html:text property="lineName" styleId="lineName"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路名称'" value="${lines.lineName}" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.remark" />
        </td>
        <td class="content">
            <html:text property="remark" styleId="remark"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路信息'" value="${lines.remark}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.maintainType" />
        </td>
        <td class="content">
            <html:text property="maintainType" styleId="maintainType"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'维护类型'" value="${lines.maintainType}" />
        </td>
    </tr>
    
    <td class="label">
            <font color='red'> * </font><fmt:message key="line.region" />
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
            <font color='red'> * </font><fmt:message key="line.city" />
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
            <font color='red'> * </font><fmt:message key="line.grid" />
        </td>
        <td class="content">
            <!-- 所属网格 -->			
			<select name="grid" id="grid" 
				alt="allowBlank:true,vtext:'请选择所属网格'" onchange="changePartner(0)">
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.partner" />
        </td>
        <td class="content">
            <html:text property="partner" styleId="partner"
								styleClass="text medium" readonly="true"
								alt="allowBlank:true,vtext:''" value="${linesForm.partner}"/>
				<font color='greed'>根据地市县区网格自动填写</font>
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.manager" />
        </td>
        <td class="content">
            <html:text property="manager" styleId="manager"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'客户经理'" value="${lines.manager}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.managertel" />
        </td>
        <td class="content">
            <html:text property="managerTel" styleId="managerTel"
                        styleClass="text medium"	value="${lines.managerTel}"
                        alt="vtype:'number',allowBlank:false,vtext:'客户经理电话'" />
        </td>
    </tr>
    
    <tr>    
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.contacter" />
        </td>
        <td class="content">
            <html:text property="contacter" styleId="contacter"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'联系人'" value="${lines.contacter}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.contactertel" />
        </td>
        <td class="content">
            <html:text property="contacterTel" styleId="contacterTel"
                        styleClass="text medium"	value="${lines.contacterTel}"
                        alt="vtype:'number',allowBlank:false,vtext:'联系人电话'" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.openTime" />
        </td>
        <td class="content">
            <input property="openTime" id="openTime" name="openTime"
                        readonly="readonly" type="text" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1);" value="${lines.openTime}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.userYear" />
        </td>
        <td class="content">
            <html:text property="userYear" styleId="userYear"
                        styleClass="text medium"	value="${lines.userYear}"
                        alt="vtype:'number',allowBlank:false,vtext:'使用年限'" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.beginLong" />
        </td>
        <td class="content">
            <html:text property="beginLong" styleId="beginLong"
                        styleClass="text medium"	value="${lines.beginLong}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.beginLat" />
        </td>
        <td class="content">
            <html:text property="beginLat" styleId="beginLat"
                        styleClass="text medium"	value="${lines.beginLat}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
        </td>
    </tr>
    
    <tr>    
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.endLong" />
        </td>
        <td class="content">
            <html:text property="endLong" styleId="endLong"
                        styleClass="text medium"	value="${lines.endLong}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.endLat" />
        </td>
        <td class="content">
            <html:text property="endLat" styleId="endLat"
                        styleClass="text medium"	value="${lines.endLat}"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.level" />
        </td>
        <td class="content">
            <html:text property="level" styleId="level"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'线路等级'" value="${lines.level}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="line.errorScope" />
        </td>
        <td class="content">
            <html:text property="errorScope" styleId="errorScope" value='0'
                        styleClass="text medium"
                        alt="re:/^(\d+)(\.\d+)?$/,allowBlank:true,re_vt:'误差范围必须为整数或小数'" />
        </td>
    </tr>
                                        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" />&nbsp;&nbsp;
            <input type="button" class="btn" onclick="javascript:history.back();" value="<fmt:message key="button.back"/>" />
        </td>
    </tr>
</table>
<html:hidden property="id" value="${lines.id}" />
</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
	var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var region = '${lines.region}';
    var city = '${lines.city}';
    var grid = '${lines.grid}';
	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
	if(city!=''){
 		document.getElementById("city").value = city;
		changeGrid(1);
	}
	if(gridNumber!=''){
		document.getElementById("grid").value = grid;
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>