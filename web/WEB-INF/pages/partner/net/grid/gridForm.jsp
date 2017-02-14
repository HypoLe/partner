<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
var tr1 = true;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridForm'});
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
function valgrid()
{
	var obj = document.getElementById("gridName");
	var gridName = document.getElementById("gridName").value;
	gridName = encodeURIComponent(gridName);
	var url = "../net/grids.do?method=validationGridName"+"&gridName="+gridName;
		Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						var json = eval(res);
							if(json[0].message == true){
			     				if(obj.value!=""){
			     					document.getElementById("message").innerHTML = "<font color='green'>此名称可以使用</font>";
			     					tr = true;
			     				}else{
			     				}
			     			}else{
			     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此网格名称已存在</font>";
			     				tr = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}

function valgridNumber()
{
	var obj = document.getElementById("gridNumber");
	var gridNumber = document.getElementById("gridNumber").value;
	gridNumber = encodeURIComponent(gridNumber);
	var url = "../net/grids.do?method=validationGridNumber"+"&gridNumber="+gridNumber;

		Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						var json = eval(res);
							if(json[0].message == true){
			     				if(obj.value!=""){
			     					document.getElementById("message1").innerHTML = "<font color='green'>此编号可以使用</font>";
			     					tr1 = true;
			     				}else{
			     				}
			     			}else{
			     				document.getElementById("message1").innerHTML = "<font color='red'>对不起,此网格编号已存在</font>";
			     				tr1 = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}


function sub(){
	if(tr){
	}else{
		alert("存在的网格名称");
		return false;
	}
	if(tr1){
	}else{
		alert("存在的网格编号");
		return false;
	}
	
	//经度大于纬度判断 add by WangGuangping 2012-2-29 14:23:15
	var longitude = document.getElementById("longitude").value;
	var latitude = document.getElementById("latitude").value;
	if(longitude - latitude <= 0){
		alert("中国境内,【经度】必须要大于【纬度】！");
		return false;
	}
	
	if(v.check()){
       $("gridForm").submit();
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

function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="../baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
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
										var city = '${gridForm.city}';
										var partnerid = '${gridForm.partnerid}';
										if(city!=''){
											document.getElementById("city").value = city;
										}
										if(partnerid!=''){
											changePartner(1);											    
	                                    }	
									}							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
 function searchByName(id,value){     
    var spl=value.toLowerCase();//将输入值转成小写  
    var selectProjects=document.getElementById(id);     
    var options=selectProjects.options;     
    var total = -1;     
    var meetArray = new Array();     
    for(var i=0;i<options.length;i++){     
        var op_text=options[i].text.toLowerCase();//将option的文本转成小写  
        var opArray=op_text.split(spl);     
        if(spl.length>0 && opArray.length>1){  //匹配到了  
            total++;  
            meetArray[total]=i;  
        }  
    }     
    var beginIndex = 0;     
    for(var i=0;i<=total;i++){     
        var index = meetArray[i];     
        if(index!=beginIndex){     
            var tempText = options[index].text;     
            var tempValue = options[index].value;     
            options[index].text = options[beginIndex].text;     
            options[index].title = options[beginIndex].text;     
            options[index].value = options[beginIndex].value;     
            options[beginIndex].text=tempText;     
            options[beginIndex].title=tempText;     
            options[beginIndex].value=tempValue;     
        }     
        beginIndex++;  
        //selectProjects.options[i].selected = true;  
        selectProjects.options[0].selected = true;  
    }  
    document.getElementById(id+"span").innerHTML="匹配到"+(total+1)+"个选项";
}  

//地市、县区联动合作伙伴		
function changePartner(con)
		{    
		    delAllOption("partnerid");//地市选择更新后，重新刷新合作伙伴
			//地市
			var regionValue = document.getElementById("region").value;
			//县区
			var cityValue = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/net/grids.do?method=changePartner&region="+regionValue+"&city="+cityValue;
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
								
								var providerName = "partnerid";
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
										var provider = '${gridForm.partnerid}';
										if(provider!=''){
											document.getElementById("partnerid").value = provider;
										}
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}


 function searchWin(id){ 
	Ext.MessageBox.prompt('筛选排序', '请输入关键字，支持模糊查询，匹配的选项会排在顶端！', function(btn, text) {
	searchByName(id,text);
    //alert('你刚刚点击了 ' + btn + '，刚刚输入了 ' + text);
});
}
</script>
<eoms:xbox id="monitorTree" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText="网格运维监督员" 
	  	valueField="gridMonitor" handler="gridMonitorName"
		textField="gridMonitorName"
		checktype="user" single="true"	
		data='${monitorData}'></eoms:xbox>
<eoms:xbox id="yardmanTree" dataUrl="${app}/xtree.do?method=userFromDept" 
	  	rootId="-1" 
	  	rootText="网格运维调度员" 
	  	valueField="gridYardman" handler="gridYardmanName"
		textField="gridYardmanName"
		checktype="user" single="true"
		data='${yardmanData}'></eoms:xbox>
<html:form  action="/grids.do?method=save" styleId="gridForm" method="post" > 

<fmt:bundle basename="com/boco/eoms/partner/net/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">

	<caption>
		<div class="header center"><fmt:message key="grid.form.heading"/></div>
	</caption>
	

	<tr>
		<td class="label">
			<fmt:message key="grid.gridName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="gridName" styleId="gridName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridForm.gridName}" onblur="valgrid()"/>
			<span id = "message"></span>			
		</td>
		<c:if test="${not empty gridForm.id}">
		<td class="label">
			网格编号&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="gridNumber" styleId="gridNumber"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridForm.gridNumber}" onblur="valgridNumber()"
						readonly="true"/>
			<span id = "message1"></span>			
		</td>
		</c:if>
		<c:if test="${ gridForm.id==null}">
		    <td class="label">
			网格编号&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="gridNumber" styleId="gridNumber"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridForm.gridNumber}" onblur="valgridNumber()"/>
			<span id = "message1"></span>			
		</td>
		</c:if>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="grid.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
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
			<fmt:message key="grid.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'"" onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
						
		</td>
	</tr>  

	<tr>
		<td class="label">
			<fmt:message key="grid.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<!-- 合作伙伴 -->			
			<select name="partnerid" id="partnerid" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'">
					<option value="">--请选择合作伙伴--</option>
			</select>
			<input type="button" class="btn" onclick="JavaScript:searchWin('partnerid');" value=" 搜 索 "><span id="partneridspan"></span>
		</td>
		<td class="label">
			自维/代维&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" isQuery="false"  alt="allowBlank:false,vtext:''"
				defaultId="${gridForm.maintainType}" selectId="maintainType" beanId="selectXML" />
		</td>			
	</tr>
	<tr>
		<td class="label">
			网格运维监督员&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" >
			<input type="hidden" id="gridMonitor" name="gridMonitor" value="${gridForm.gridMonitor}" />
			<input class="input text" type="text" name="gridMonitorName" id="gridMonitorName" 
						alt="allowBlank:false,vtext:'请选择网格运维监督员'" value="<eoms:id2nameDB id="${gridForm.gridMonitor}" beanId="tawSystemUserDao" />" readonly="true" />
			
		</td>
		<td class="label">
			网格运维调度员&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<input type="hidden" id="gridYardman" name="gridYardman" value="${gridForm.gridYardman}" />
			<input class="input text" type="text" name="gridYardmanName" id="gridYardmanName" 
						alt="allowBlank:false,vtext:'请选择网格运维调度员'" value="<eoms:id2nameDB id="${gridForm.gridYardman}" beanId="tawSystemUserDao" />" readonly="true" />

		</td>		
	</tr>
	
	<tr>
		<td class="label">
			经度&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="longitude" styleId="longitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="${gridForm.longitude}" />
		</td>
		<td class="label">
			纬度&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="latitude" styleId="latitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="${gridForm.latitude}" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			网格说明
		</td>
		<td class="content" colspan="3">
			<textarea name="regionExplain" id="regionExplain" maxLength="255" rows="3" style="width:98%;" alt="maxLength:255">${gridForm.regionExplain}</textarea>		
		</td>		
	</tr>
<!-- 
	<tr>
		<td class="label">
			<fmt:message key="grid.addUser" />
		</td>
		<td class="content">
			<html:text property="addUser" styleId="addUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.addUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.addTime" />
		</td>
		<td class="content">
			<html:text property="addTime" styleId="addTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.addTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.updateUser" />
		</td>
		<td class="content">
			<html:text property="updateUser" styleId="updateUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.updateUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.updateTime" />
		</td>
		<td class="content">
			<html:text property="updateTime" styleId="updateTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.updateTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.delUser" />
		</td>
		<td class="content">
			<html:text property="delUser" styleId="delUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.delUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.delTime" />
		</td>
		<td class="content">
			<html:text property="delTime" styleId="delTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.delTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.isDel" />
		</td>
		<td class="content">
			<html:text property="isDel" styleId="isDel"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.isDel}" />
		</td>
	</tr>
 -->

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick=" sub();"/>
			<c:if test="${not empty gridForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url=eoms.appPath+'/partner/serviceArea/grids.do?method=remove&id=${gridForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="addUser" value="${gridForm.addUser}" />
<html:hidden property="addTime" value="${gridForm.addTime}" />
<html:hidden property="updateUser" value="${gridForm.updateUser}" />
<html:hidden property="delTime" value="${gridForm.delTime}" />
<html:hidden property="delUser" value="${gridForm.delUser}" />
<html:hidden property="updateTime" value="${gridForm.updateTime}" />
<html:hidden property="id" value="${gridForm.id}" />
</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${gridForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
 		/*联动福建版本*/
		changeCity(1);
		/*联动广西版本
		changePartner(1);*/
	}
}
</script>
<c:if test="">
	<script type="text/javascript">
		var partnerId = '${gridForm.partnerid}';
		document.getElementById("partnerid").value = partnerId;
	</script>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>