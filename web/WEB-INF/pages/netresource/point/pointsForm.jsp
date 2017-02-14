<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">

var FlagPointNo = true;//标点编号是否已经存在验证
var FlagPointName = true;//标点名称是否已经存在验证

//验证form
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'pointsForm'});
    v.custom = function(){
     	if(!FlagPointNo){
			alert("【标点编号】 已存在！");
			return false;
		}
		if(!FlagPointName){
			alert("【标点名称】 已存在！");
			return false;
		}
		//经度大于纬度判断
		var longitude = document.getElementById("longitude").value;
		var latitude = document.getElementById("latitude").value;
		if(longitude - latitude <= 0){
			alert("中国境内,【标点经度】必须要大于【标点纬度】！");
			return false;
		}
      	document.getElementById("submitInput").disabled = true;//锁定提交按钮
      	return true;
   	 }
});

//清空对象方法
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i); //firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }

//getResponseText        
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
		    delAllOption("grid");//网格
		    delAllOption("line");//地市选择更新后，重新刷新合作伙伴
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
										var city = '${pointsForm.city}';
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

//地市 区县 联动网格
function changeGrid(con){
    var region = document.getElementById("region").value;
    var city = document.getElementById("city").value;
    if(city==''){
    	delAllOption("grid");
    	var grid = "grid";
		var obj=$(grid);
		var i=0;
		var j=0;
		var opt=new Option("--请选择网格--","");
		obj.options[j]=opt;
    }
    if(region!=''&&city!=''){
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
											var grid = '${pointsForm.grid}';
											document.getElementById("grid").value = grid;
										}
										changeLines(con);
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}

//网格 联动 线路
function changeLines(con){
    var grid = document.getElementById("grid").value;
    if(grid==''){
    	delAllOption("line");
    	var line = "line";
		var obj=$(line);
		var i=0;
		var j=0;
		var opt = new Option("--请选择线路--","");
		obj.options[j]=opt;
    }
    if(grid!=''){
		var url="<%=request.getContextPath()%>/netresource/line/lines.do?method=getLinesJson&grid="+grid;
		delAllOption("line");
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
				     					var countyName = "line";
										var arrOptions = json[0].lines.split(",");
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
											var line = '${pointsForm.line}';
											document.getElementById("line").value = line;
										}
									},
									failure: function ( result, request) { 
										Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
									} 
								});
    }
}

//标点编号唯一性验证
function valPointNo(){
	var pointNo = document.getElementById("pointNo").value;
	pointNo = encodeURIComponent(pointNo); //编码转换
	var url = "<%=request.getContextPath()%>/netresource/point/points.do?method=validationPointInfo&flag=pointNo&pointNo="+pointNo;
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
							if(json[0].message == true){
			     				if(pointNo != ""){
			     					document.getElementById("pointNoMessage").innerHTML = "<font color='blue'>此标点编号可以使用.</font>";
			     					FlagPointNo = true;
			     				}
			     			}else{
			     				document.getElementById("pointNoMessage").innerHTML = "<font color='red'>对不起,此标点编号已存在.</font>";
			     				FlagPointNo = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}

//标点名称唯一性验证
function valPointName(){
	var pointName = document.getElementById("pointName").value;
	pointName = encodeURIComponent(pointName); //编码转换
	var url = "<%=request.getContextPath()%>/netresource/point/points.do?method=validationPointInfo&flag=pointName&pointName="+pointName;
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
							if(json[0].message == true){
			     				if(pointName != ""){
			     					document.getElementById("pointNameMessage").innerHTML = "<font color='blue'>此标点名称可以使用.</font>";
			     					FlagPointName = true;
			     				}
			     			}else{
			     				document.getElementById("pointNameMessage").innerHTML = "<font color='red'>对不起,此标点名称已存在.</font>";
			     				FlagPointName = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}


</script>

<html:form action="/points.do?method=save" styleId="pointsForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/netresource/point/config/applicationResource-points">

<table class="formTable">
    <caption>
        <div class="header center"><fmt:message key="points.form.heading"/></div>
    </caption>
                                                    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.pointName" />
        </td>
        <td class="content">
            <html:text property="pointName" styleId="pointName"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'标点名称',maxLength:100" value="${pointsForm.pointName}" onblur="valPointName()" />
			<span id = "pointNameMessage"></span>                        
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.pointNo" />
        </td>
        <td class="content">
            <html:text property="pointNo" styleId="pointNo"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'标点编号',maxLength:100" value="${pointsForm.pointNo}" onblur="valPointNo()" />
			<span id = "pointNoMessage"></span>                        
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.region" />
        </td>
        <td class="content">
            <!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所属地市'"
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
            <font color='red'> * </font><fmt:message key="points.city" />
        </td>
        <td class="content">
            <!-- 区县 -->			
			<select name="city" id="city"
				alt="allowBlank:false,vtext:'请选择所属区县'"
				onchange="changeGrid(0);">
				<option value="">
					--请选择所属区县--
				</option>
			</select>
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.grid" />
        </td>
        <td class="content">
            <!-- 所属网格 -->			
			<select name="grid" id="grid" 
				alt="allowBlank:true,vtext:'请选择所属网格'" onchange="changeLines(0)">
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.line" />
        </td>
        <td class="content">
            <!-- 所属线路 -->			
			<select name="line" id="line" 
				alt="allowBlank:true,vtext:'请选择所属线路'" >
				<option value="">
					--请选择所属线路--
				</option>				
			</select>
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.groupUser" />
        </td>
        <td class="content">
            <html:text property="groupUser" styleId="groupUser"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'所属集团客户',maxLength:100" value="${pointsForm.groupUser}" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.groupUserNo" />
        </td>
        <td class="content">
            <html:text property="groupUserNo" styleId="groupUserNo"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'所属集团客户编码',maxLength:100" value="${pointsForm.groupUserNo}" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.longitude" />
        </td>
        <td class="content">
            <html:text property="longitude" styleId="longitude"
                        styleClass="text medium"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.latitude" />
        </td>
        <td class="content">
            <html:text property="latitude" styleId="latitude"
                        styleClass="text medium"
                        alt="re:/^-?\d{0,4}\.{1}\d{0,7}$/,re_vt:'经纬度格式必须为:xxxx.xxxxxx,小数点前最多 4 位,小数点后最多 7 位.'" />
        </td>
    </tr>
    
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.loadwork" />
        </td>
        <td class="content">
            <html:text property="loadwork" styleId="loadwork"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'承载业务',maxLength:200" value="${pointsForm.loadwork}" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.errorScope" />(单位:米)
        </td>
        <td class="content">
            <html:text property="errorScope" styleId="errorScope"
                        styleClass="text medium"
                        alt="re:/^(\d+)(\.\d+)?$/,re_vt:'误差范围必须为整数或小数',maxLength:25" value="${pointsForm.errorScope}" />
        </td>
    </tr>
    
    <tr>
    	<td class="label">
            <font color='red'> * </font><fmt:message key="points.specialtyType" />
        </td>
        <td class="content" colspan='3'>
            <html:text property="specialtyType" styleId="specialtyType"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'专业类型',maxLength:100" value="${pointsForm.specialtyType}" />
        </td>
    </tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" id='submitInput' name='submitInput' />
            <c:if test="${not empty pointsForm.id}">
                <input type="button" class="btn" value="<fmt:message key="button.back"/>" 
                    onclick="javascript:history.back();"    />
            </c:if>
        </td>
    </tr>
</table>

<html:hidden property="id" value="${pointsForm.id}" />
<html:hidden property="createTime" value="${pointsForm.createTime}" />
<html:hidden property="creator" value="${pointsForm.creator}" />
<html:hidden property="isdeleted" value="${pointsForm.isdeleted}" />
<html:hidden property="partner" value="${pointsForm.partner}" />

</html:form>


<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
	var err = '${err}';
    if(err!=''){
    	alert(err);
    }
    var region = '${pointsForm.region}';
    var city = '${pointsForm.city}';
    var grid = '${pointsForm.grid}';
    var line = '${pointsForm.line}';
    if(region != ''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
	if(city != ''){
 		document.getElementById("city").value = city;
		changeGrid(1);
	}
	if(grid != ''){
		document.getElementById("grid").value = grid;
		changeLines(1);
	}
	if(line != ''){
		document.getElementById("line").value = line;
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>