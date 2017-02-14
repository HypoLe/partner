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

<html:form action="/addPointByLine.do?method=savePointByLine" styleId="pointsForm" method="post">

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
                        alt="allowBlank:false,vtext:'标点名称',maxLength:100" onblur="valPointName()" />
			<span id = "pointNameMessage"></span>                        
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.pointNo" />
        </td>
        <td class="content">
            <html:text property="pointNo" styleId="pointNo"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'标点编号',maxLength:100" onblur="valPointNo()" />
			<span id = "pointNoMessage"></span>                        
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.region" />
        </td>
        <td class="content">
            <!-- 地市 -->			
			<eoms:id2nameDB id="${linesForm.region}" beanId="tawSystemAreaDao" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.city" />
        </td>
        <td class="content">
            <!-- 区县 -->			
			<eoms:id2nameDB id="${linesForm.city}" beanId="tawSystemAreaDao" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.grid" />
        </td>
        <td class="content">
        	<!-- 网格 -->	
            <eoms:id2nameDB id="${linesForm.grid}" beanId="gridDao" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.line" />
        </td>
        <td class="content">
            <!-- 线路 -->			
			<eoms:id2nameDB id="${linesForm.id}" beanId="linesDao" />
        </td>
    </tr>
                                        
    <tr>
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.groupUser" />
        </td>
        <td class="content">
            <html:text property="groupUser" styleId="groupUser"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'所属集团客户',maxLength:100" />
        </td>
    
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.groupUserNo" />
        </td>
        <td class="content">
            <html:text property="groupUserNo" styleId="groupUserNo"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'所属集团客户编码',maxLength:100" />
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
                        alt="allowBlank:false,vtext:'承载业务',maxLength:200" />
        </td>
        
        <td class="label">
            <font color='red'> * </font><fmt:message key="points.errorScope" />(单位:米)
        </td>
        <td class="content">
            <html:text property="errorScope" styleId="errorScope"
                        styleClass="text medium"
                        alt="re:/^(\d+)(\.\d+)?$/,re_vt:'误差范围必须为整数或小数',maxLength:25" />
        </td>
    </tr>
    
    <tr>
    	<td class="label">
            <font color='red'> * </font><fmt:message key="points.specialtyType" />
        </td>
        <td class="content" colspan='3'>
            <html:text property="specialtyType" styleId="specialtyType"
                        styleClass="text medium"
                        alt="allowBlank:false,vtext:'专业类型',maxLength:100" />
        </td>
    </tr>
        
</table>
</fmt:bundle>

<table>
    <tr>
        <td>
            <input type="submit" class="btn" value="<fmt:message key="button.save"/>" id='submitInput' name='submitInput' />
            &nbsp;&nbsp;
            <input type="button" class="btn" value="<fmt:message key="button.back"/>" onclick="javascript:history.back();" />
        </td>
    </tr>
</table>

<html:hidden property="region" value="${linesForm.region}" />
<html:hidden property="city" value="${linesForm.city}" />
<html:hidden property="grid" value="${linesForm.grid}" />
<html:hidden property="line" value="${linesForm.id}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>