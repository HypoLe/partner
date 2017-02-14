<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PnrResConfigAdd.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
var tr = true;
var tr1 = true;
Ext.onReady(function() {
	 //jq("#zhuanye").attr("readonly","readonly");
	 eoms.form.disable("zhuanye");
	 eoms.form.disable("resourceTeype");
	 eoms.form.disable("resourceLevel");
	 
	
	 var v = new eoms.form.Validation({form:'resForm'});
	  jq("#sheet").find("tr.jizhan,tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
			jq(this).hide();
       		jq(this).find(":input").each(function(index){
       			jq(this).attr("disabled","disabled");
       		});	
		});
		 
		//初始的时候给区县默认值
		delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
					var country = "${pnrResConfig.country}";
					if(arrOptions[i]==country){
					obj.options[j].selected=true;
					}
					j++;
					i++;
				}
				
							
					var city = '${gridForm.city}';
					var partnerid = '${gridForm.partnerid}';
					if(city!=''){
						document.getElementById("city").value = city;
					}
					if(partnerid!=''){
						changePartner(1);								
                                }	
											
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
							
							
							
		
		var test = jq("#zhuanye").val();
		    switch(test) {
           		case "1122501":
					jq("#sheet tr.jizhan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;
           		
           		case "1122502":
           			jq("#sheet tr.rode").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;
           		
           		case "1122503":
           			jq("#sheet tr.jizhan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;
           		
           		case "1122504":
		            jq("#sheet tr.tieda").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;
           			
           		case "1122505":
		            jq("#sheet tr.jieke").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;	
           			
           		case "1122506":
		            jq("#sheet tr.wlan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;	
           			
           		case "1122507":
		            jq("#sheet tr.family").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
           			break;	
           	}
           	
           	
           	jq("#transData").hide();
	 var option = "${pnrResConfigStation.propertyType}";
	 if(option == 1){
	 	jq("#option1").attr("selected","selected");
	 }else if(option == 2){
	 	jq("#option2").attr("selected","selected");
	 	eoms.form.disable("sharing");
	 	
		//jq("#sharing").attr("disabled","disabled");
		jq("#shareLabel").attr("disabled","disabled");
		jq("#shareLabel").hide();
		jq("#sharing").hide();
	 }else if(option == 3){
	 	jq("#option3").attr("selected","selected");
	 	jq("#sharing").attr("disabled","disabled");
		jq("#shareLabel").attr("disabled","disabled");
		jq("#shareLabel").hide();
		jq("#sharing").hide();
	 }
	  var opt = "${ pnrResRepeaters.propertyType}";
	 if(opt == 1){
	 	jq("#option4").attr("selected","selected");
	 }else if(opt == 2){
	 	jq("#option5").attr("selected","selected");
	 	jq("#sharing2").attr("disabled","disabled");
		jq("#shareLabel2").attr("disabled","disabled");
		jq("#shareLabel2").hide();
		jq("#sharing2").hide();
	 }else if(opt == 3){
	 	jq("#option6").attr("selected","selected");
	 	jq("#sharing2").attr("disabled","disabled");
		jq("#shareLabel2").attr("disabled","disabled");
		jq("#shareLabel2").hide();
		jq("#sharing2").hide();
	 }
	 
	 var opt1 = "${ pnrResWlan.propertyType}";
	 if(opt1 == 1){
	 	jq("#option5").attr("selected","selected");
	 }else if(opt1 == 2){
	 	jq("#option8").attr("selected","selected");
	 	jq("#sharing3").attr("disabled","disabled");
		jq("#shareLabel3").attr("disabled","disabled");
		jq("#shareLabel3").hide();
		jq("#sharing2").hide();
	 }else if(opt1 == 3){
	 	jq("#option9").attr("selected","selected");
	 	jq("#sharing3").attr("disabled","disabled");
		jq("#shareLabel3").attr("disabled","disabled");
		jq("#shareLabel3").hide();
		jq("#sharing3").hide();
	 }
	 
	 var client = jq("#client").val();
	 if(client == "传输专线"){
	 	jq("#client1").attr("selected","selected");
	 }else if(client == "GPRS"){
	 	jq("#client2").attr("selected","selected");
	 }else if(client == "短彩信"){
	 	jq("#client3").attr("selected","selected");
	 }else{
	 	jq("#client0").attr("selected","selected");
	 }
		 
});
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
            
        }

function selectRes(){
	var type = jq("#zhuanye").val();
           	//type：字典值对应的value，参考配置
           	switch(type) {
           		case "1122501":
					jq("#sheet tr.jizhan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           			break;
           		
           		case "1122502":
           			jq("#sheet tr.rode").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.jizhan,tr.zhifang,tr.tieda,tr.jieke,tr.wlan").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           			break;
           		
           		case "1122503":
           			jq("#sheet tr.jizhan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.jizhan,tr.tieda,tr.jieke,tr.wlan").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           			break;
           		
           		case "1122504":
		            jq("#sheet tr.tieda").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.jieke,tr.wlan").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           			break;
           			
           		case "1122505":
           		
           			jq("#sheet tr.jieke").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda,tr.wlan").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           		
           			break;	
           		
           		case "1122506":
           		
           			jq("#sheet tr.wlan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda,tr.jieke").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           		
           			break;
           			
           		case "1122507":
           		
           			jq("#sheet tr.family").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda,tr.jieke,tr.wlan").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled","disabled");
		            	});	
					});
           		
           			break;			
           	
           	}
	
}

//地区、区县连动
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
		
		
//表单提交
function submitForm(){
	if(checkIsNull()){
      document.forms[0].submit();
    }
}

function changeType(){
	var propertyType = jq("#propertyType").val();
	if(propertyType!="共享"){
		/*jq("#sharing").attr("disabled","disabled");
		eoms.form.disable("sharing");*/
		jq("#sharing").attr("disabled","disabled");
		jq("#shareLabel").attr("disabled","disabled");
		jq("#shareLabel").hide();
		jq("#sharing").hide();
	}else{
		
		jq("#shareLabel").attr("disabled",false);
		jq("#sharing").attr("disabled",false);
		jq("#sharing").show();
		jq("#shareLabel").show();
	}
}

function changeType2(){
	var propertyType = jq("#propertyType2").val();
	if(propertyType!="共享"){
		/*jq("#sharing").attr("disabled","disabled");
		eoms.form.disable("sharing");*/
		jq("#sharing2").attr("disabled","disabled");
		jq("#shareLabel2").attr("disabled","disabled");
		jq("#shareLabel2").hide();
		jq("#sharing2").hide();
	}else{
		
		jq("#shareLabel2").attr("disabled",false);
		jq("#sharing2").attr("disabled",false);
		jq("#sharing2").show();
		jq("#shareLabel2").show();
	}
}

function changeType3(){
	var propertyType = jq("#propertyType3").val();
	if(propertyType!="共享"){
		jq("#sharing3").attr("disabled","disabled");
		jq("#shareLabel3").attr("disabled","disabled");
		jq("#shareLabel3").hide();
		jq("#sharing3").hide();
	}else{
		
		jq("#shareLabel3").attr("disabled",false);
		jq("#sharing3").attr("disabled",false);
		jq("#sharing3").show();
		jq("#shareLabel3").show();
	}
}
// 资源级别，巡检周期联动
function setWeekTime(pnrid)
		{    
var resourceLevel = document.getElementById("resourceLevel").value;// 跟 pnrid是一致的
var url="${app}/partner/baseinfo/linkage.do?method=changeWeekTime&pnrid="+resourceLevel;
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
						var inspectCycle = "pnrResConfig.inspectCycle";
					    var weektime = "weektime";						
						var arrOptions = json[0].cb.split(",");
						var obj=$(inspectCycle);
						var obj2=$(weektime);
						
						if(arrOptions.length>2){
						  obj2.value=arrOptions[3];
						  obj.value=arrOptions[2];
						}else{
						  obj2.value=arrOptions[1];
						  obj.value=arrOptions[0];
						alert("请到\"巡检管理\"->\"巡检计划基础信息管理\"->\"巡检周期配置\"设置巡检周期");
						}				
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}
	
</script>
  <body>
    <html:form action="PnrAllRes.do?method=edit" method="post" styleId="resForm">
	<table class="formTable" id="sheet">
		<caption>
			<div class="header center">巡检资源修改</div>
		</caption>
		<tr>
			<td class="label" >资源名称&nbsp<font color='red'>*</font></td>
			<td class="content">
					<html:text property="pnrResConfig.resName" styleId="resName"
						styleClass="text medium" name="pnrResConfig.resName"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfig.resName}"  />
						<input type="hidden" name="testName" value="${pnrResConfig.resName}"/>
			</td>
			<td class="label">巡检专业&nbsp<font color='red'>*</font></td>
			<td class="content">
				<eoms:comboBox name="pnrResConfig.specialty" id="zhuanye" sub="resourceTeype" defaultValue="${pnrResConfig.specialty}" onchange="selectRes()" 
	        			initDicId="11225" alt="allowBlank:false" attributes="false" styleClass="select" />
	        			
	        	<input name="pnrResConfig.specialty" type="hidden" value="${pnrResConfig.specialty}">		
			</td>
		</tr>
		<tr>
			<td class="label">资源类别&nbsp<font color='red'>*</font></td>
			<td class="content">
				<eoms:comboBox name="pnrResConfig.resType" initDicId="${pnrResConfig.specialty}" defaultValue="${pnrResConfig.resType}" id="resourceTeype" alt="allowBlank:false" sub="resourceLevel" styleClass="select" /> 
	<input name="pnrResConfig.resType" type="hidden" value="${pnrResConfig.resType}">		
			
			</td>
			<td class="label">资源级别&nbsp<font color='red'>*</font></td>
			<td class="content" >
					<eoms:comboBox name="pnrResConfig.resLevel" initDicId="${pnrResConfig.resType}" defaultValue="${pnrResConfig.resLevel}" id="resourceLevel" alt="allowBlank:false" styleClass="select" onchange="setWeekTime(this.value);"/>
				<input type="hidden" name="testLevel" value="${pnrResConfig.resLevel}"/>	 
	<input name="pnrResConfig.resLevel" type="hidden" value="${pnrResConfig.resLevel}">		
			</td>
		</tr>
		<tr>
			<td class="label">资源经度&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.resLongitude" styleId="car_number"
						styleClass="text medium" name="pnrResConfig.resLongitude"
						alt="allowBlank:false,vtext:'',maxLength:8,re:/^(\d{1,3})(\.\d{1,5})?$/,re_vt:'请输入整数（小于4位）或小数（小数位小于5位）'" value="${pnrResConfig.resLongitude}" />
			</td>
			<td class="label">资源纬度&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.resLatitude" styleId="car_number"
						styleClass="text medium" name="pnrResConfig.resLatitude"
						alt="allowBlank:false,vtext:'',maxLength:8,re:/^(\d{1,3})(\.\d{1,5})?$/,re_vt:'请输入整数（小于4位）或小数（小数位小于5位）'" value="${pnrResConfig.resLatitude}"  />	
			</td>
		</tr>
		<tr>
			<td class="label">地市&nbsp<font color='red'>*</font></td>
			<td class="content">
				<select name="region" id="region" class="select"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
				<option value="${pnrResConfig.city }">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
				<c:if test="${pnrResConfig.city==city.areaid}">
					<option value="${city.areaid}" selected="selected" >
						${city.areaname}
					</option>
				</c:if>
					<option value="${city.areaid}" >
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
			</td>
			<td class="label">区县&nbsp<font color='red'>*</font></td>
			<td class="content">
				<select name="city" id="city" class="select"
				alt="allowBlank:false,vtext:'请选择所在县区'"" onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
			</td>
		</tr>
		
		<tr>
			<td class="label">地理环境</td>
			<td class="content">
				<eoms:comboBox  name="pnrResConfig.eographicalEnvironment" id="environment"  defaultValue="${pnrResConfig.eographicalEnvironment}" 
	        			initDicId="11229"  styleClass="select" />
			</td>
			<td class="label">区域类型</td>
			<td class="content">
				<eoms:comboBox  name="pnrResConfig.regionType" id="regionType"  defaultValue="${pnrResConfig.regionType}" 
	        			initDicId="11230"  styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label" >乡镇/街道 </td>
			<td class="content">
					<html:text property="pnrResConfig.street" 
						styleClass="text medium" name="pnrResConfig.street"
						value="${pnrResConfig.street}" alt="maxLength:50" />
			</td>
			<td class="label">服务区域&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.serviceRegion" 
						styleClass="text medium" name="pnrResConfig.serviceRegion"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfig.serviceRegion }"  />
			</td>
		</tr>
		<tr>
			<td class="label" >业主单位名称&nbsp<font color='red'>*</font></td>
			<td class="content">
					<html:text property="pnrResConfig.companyName" 
						styleClass="text medium" name="pnrResConfig.companyName"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfig.companyName }"  />
			</td>
			<td class="label">联系人&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.contactName" 
						styleClass="text medium" name="pnrResConfig.contactName"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfig.contactName }"  />
			</td>
		</tr>
		<tr>
			<td class="label">业主联系电话&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.phone" 
						styleClass="text medium" name="pnrResConfig.phone"
						alt="maxLength:50,re:/(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})$|(\(\d{3,4}\)?\d{7,8})$/,re_vt:'请输入正确的电话号码'" value="${pnrResConfig.phone }"/>
			</td>
			<td class="label" >巡检周期&nbsp<font color='red'>*</font></td>
			<td class="content">
			<c:choose>
			<c:when test="${pnrResConfig.inspectCycle eq 'week'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="周" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:when test="${pnrResConfig.inspectCycle eq 'month'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="月" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:when test="${pnrResConfig.inspectCycle eq 'doubleMonth'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="双月" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:when test="${pnrResConfig.inspectCycle eq 'quarter'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="季度" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:when test="${pnrResConfig.inspectCycle eq 'halfOfYear'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="半年" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:when test="${pnrResConfig.inspectCycle eq 'year'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="年" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:when test="${pnrResConfig.inspectCycle eq 'halfOfMonth'}">
							<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="半月" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			</c:when>
			<c:otherwise>
										<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
		
		
		<!-- 基站-->
		<tr class="jizhan">
			<td class="label">站址&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResConfigStation.address" styleId="car_number"
						styleClass="text medium" name="${pnrResConfigStation.address}"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfigStation.address}"  />
			</td>
			<td class="label">产权类型&nbsp<font color='red'>*</font></td>
			<td>
				<select name="propertyType" id="propertyType" onchange="changeType()" class="select" >
					<option id="option1">共享</option>
					<option id="option2">自建</option>
					<option id="option3">租用</option>
				</select>
			</td>
		</tr>
		<!--<tr class="jizhan">
			<td class="label">TD开通年月</td>
			<td>
				<html:text property="pnrResConfigStation.TDOpenTime"  styleId="assetUseTime"
                        styleClass="text medium"
                        value="${pnrResConfigStation.TDOpenTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true"  />
				
			</td>
			<td class="label">2G开通年月</td>
			<td>
				<html:text property="pnrResConfigStation.openTime"  styleId="assetUseTime"
                        styleClass="text medium"
                        value="${pnrResConfigStation.openTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
			</td>
		</tr>-->
		<tr class="jizhan">
			
			<td class="label">网络类型&nbsp<font color='red'>*</font> </td>
			<td>
				<html:text property="pnrResConfigStation.netType"  styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfigStation.netType}"  />
			</td>
			<td class="label" ><font id="shareLabel">共建共享</font> &nbsp<font color='red'>*</font> </td>
			<td id="shareContent">
				<html:text property="pnrResConfigStation.sharing" styleId="sharing"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResConfigStation.sharing}"  />
			</td>
		</tr>
		<tr class="jizhan">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				<textarea name="pnrResConfigStation.adjustmentMessage" style="width:98%;text-align: left;" alt="maxLength:255"  >${pnrResConfigStation.adjustmentMessage}</textarea>
			</td>
			
		</tr>
		
		<!-- 传输线路
		<tr class="rode">
			<td class="label">终点经度</td>
			<td>
				
				
				<html:text property="pnrResLine.endLongitude" styleId="latitude"
						styleClass="text medium"
						value="${pnrResLine.endLongitude}" alt="maxLength:50,re:/^(\d{0,8})(\.\d{1,7})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" />		
				
			</td>
			<td class="label">终点纬度</td>
			<td>
				<html:text property="pnrResLine.endLatitude" styleId="latitude"
						styleClass="text medium"
						value="${pnrResLine.endLatitude}" alt="maxLength:50,re:/^(\d{0,8})(\.\d{1,7})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"/>	
			</td>
		</tr>	
		<tr class="rode">
			<td class="label">管程/杆程</td>
			<td>
				<html:text property="pnrResLine.tubeSide" styleId="car_number"
						styleClass="text medium"
						value="${pnrResLine.tubeSide}" alt="maxLength:50" />
			</td>
			<td class="label">单路由光缆皮长(千米)</td>
			<td>
				<html:text property="pnrResLine.singleRouting" styleId="car_number"
						styleClass="text medium"
						value="${pnrResLine.singleRouting}" alt="maxLength:50,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'" />
			</td>
		</tr>
		<tr class="rode">
			<td class="label">同路由光缆皮长(千米)</td>
			<td colspan="3">
				<html:text property="pnrResLine.likeRouting" styleId="car_number"
						styleClass="text medium"
						value="${pnrResLine.likeRouting}" alt="maxLength:50,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"/>
			</td>
		</tr>-->
		
		<!-- 直放站
		<tr class="zhifang">
			<td class="label">站址&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResRepeaters.address" styleId="car_number"
						styleClass="text medium" name="${pnrResRepeaters.address}"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResRepeaters.address}"  />
			</td>
			<td class="label">产权类型&nbsp<font color='red'>*</font></td>
			<td>
				<select name="propertyType2" id="propertyType2" onchange="changeType2()" class="select" >
					<option id="option4">共享</option>
					<option id="option5">自建</option>
					<option id="option6">租用</option>
				</select>
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">TD开通年月</td>
			<td>
				<html:text property="pnrResRepeaters.TDOpenTime"  styleId="assetUseTime"
                        styleClass="text medium"
                        value="${pnrResRepeaters.TDOpenTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
				
			</td>
			<td class="label">网络类型&nbsp<font color='red'>*</font> </td>
			<td>
				<html:text property="pnrResRepeaters.netType"  styleId="car_number"
						styleClass="text medium"
						value="${pnrResRepeaters.netType}" alt="allowBlank:false,vtext:'',maxLength:50" />
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">2G开通年月</td>
			<td>
				<html:text property="pnrResRepeaters.openTime"  styleId="assetUseTime"
                        styleClass="text medium"
                        value="${pnrResRepeaters.openTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
			</td>
			<td class="label" ><font id="shareLabel2">共建共享</font>  </td>
			<td id="shareContent">
				<html:text property="pnrResRepeaters.sharing" styleId="sharing2"
						styleClass="text medium" 
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResRepeaters.sharing}" />
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				<textarea name="pnrResRepeaters.adjustmentMessage" style="width:98%;text-align: left;" alt="maxLength:255" >${pnrResRepeaters.adjustmentMessage}</textarea>
			</td>
			
		</tr>-->
		
		
		<!-- 铁搭
		<tr class="tieda">
			<td class="label">型号</td>
			<td>
				<html:text property="pnrResIron.ironType" styleId="car_number"
						styleClass="text medium"
						value="${pnrResIron.ironType}" alt="maxLength:50" />
			</td>
			<td class="label">室内机编号</td>
			<td>
				<html:text property="pnrResIron.indoorNumber" styleId="car_number"
						styleClass="text medium"
						value="${pnrResIron.indoorNumber}" alt="maxLength:50" />
			</td>
		</tr>
		<tr class="tieda">
			<td class="label">室外机编号</td>
			<td>
				<html:text property="pnrResIron.outdoorNumber" styleId="car_number"
						styleClass="text medium"
						value="${pnrResIron.outdoorNumber}" alt="maxLength:50" />
			</td>
			<td class="label">相数</td>
			<td>
				<html:text property="pnrResIron.phaseNumber" styleId="car_number"
						styleClass="text medium"
						value="${pnrResIron.phaseNumber}" alt="maxLength:50" />
			</td>
		</tr>
		<tr class="tieda">
			<td class="label">功率&nbsp<font color='red'>*</font></td>
			<td colspan="3">
				<html:text property="pnrResIron.power" styleId="car_number"
						styleClass="text medium" alt="allowBlank:false,vtext:'',maxLength:50"
						value="${pnrResIron.power}" />
			</td>
		</tr>-->
		
		<!-- 集客家客
		<tr class="jieke">
		
			<td class="label">客户数</td>
			<td>
				<html:text property="pnrResJieke.clientNumber" styleId="car_number"
						styleClass="text medium"
						value="${pnrResJieke.clientNumber}" alt="maxLength:50" />
			</td>
			<td class="label">业务类型</td>
			<td>
				<select name="pnrResJieke.clientType" class="select">
					<option id="client0">--请选择--</option>
					<option id="client1" <c:if test="${pnrResJieke.clientType eq '传输专线' }">selected="selected" </c:if>>传输专线</option>
					<option id="client2" <c:if test="${pnrResJieke.clientType eq 'GPRS' }">selected="selected" </c:if>>GPRS</option>
					<option id="client3" <c:if test="${pnrResJieke.clientType eq '短彩信' }">selected="selected" </c:if>>短彩信</option>
				</select>
				<input type="hidden" value="${pnrResJieke.clientType}" id="client">
			</td>
		</tr>
		<tr class="jieke">
		
			<td class="label">所属站点名称&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResJieke.stationName" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResJieke.stationName}" />
			</td>
			<td class="label">所属站点编号&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResJieke.stationNumber" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResJieke.stationNumber}" />
			</td>
		</tr>-->
		
		<!-- 家庭宽带
		<tr class="family">
		
			<td class="label">客户数</td>
			<td>
				<html:text property="pnrResFamilyBroadband.clientNumber" styleId="car_number"
						styleClass="text medium"
						value="${pnrResFamilyBroadband.clientNumber}" alt="maxLength:50" />
			</td>
			<td class="label">业务类型</td>
			<td>
				<select name="pnrResFamilyBroadband.clientType" class="select">
					<option id="client0">--请选择--</option>
					<option id="client1" <c:if test="${pnrResFamilyBroadband.clientType eq '传输专线' }">selected="selected" </c:if>>传输专线</option>
					<option id="client2" <c:if test="${pnrResFamilyBroadband.clientType eq 'GPRS' }">selected="selected" </c:if>>GPRS</option>
					<option id="client3" <c:if test="${pnrResFamilyBroadband.clientType eq '短彩信' }">selected="selected" </c:if>>短彩信</option>
				</select>
				<input type="hidden" value="${pnrResFamilyBroadband.clientType}" id="client">
			</td>
		</tr>
		<tr class="family">
		
			<td class="label">所属站点名称&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResFamilyBroadband.stationName" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResFamilyBroadband.stationName}" />
			</td>
			<td class="label">所属站点编号&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResFamilyBroadband.stationNumber" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResFamilyBroadband.stationNumber}" />
			</td>
		</tr>-->
		
		
		<!-- WLAN
		<tr class="wlan">
			<td class="label">站址&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResWlan.address" styleId="car_number"
						styleClass="text medium" name="${pnrResWlan.address}"
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResWlan.address}"  />
			</td>
			<td class="label">产权类型&nbsp<font color='red'>*</font></td>
			<td>
				<select name="propertyType3" id="propertyType3" onchange="changeType3()" class="select" >
					<option id="option7">共享</option>
					<option id="option8">自建</option>
					<option id="option9">租用</option>
				</select>
			</td>
		</tr>
		<tr class="wlan">
			<td class="label">TD开通年月</td>
			<td>
				<html:text property="pnrResWlan.TDOpenTime"  styleId="assetUseTime"
                        styleClass="text medium"
                        value="${pnrResWlan.TDOpenTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
				
			</td>
			<td class="label">网络类型&nbsp<font color='red'>*</font> </td>
			<td>
				<html:text property="pnrResWlan.netType"  styleId="car_number"
						styleClass="text medium"
						value="${pnrResWlan.netType}" alt="allowBlank:false,vtext:'',maxLength:50" />
			</td>
		</tr>
		<tr class="wlan">
			<td class="label">2G开通年月</td>
			<td>
				<html:text property="pnrResWlan.openTime"  styleId="assetUseTime"
                        styleClass="text medium"
                        value="${pnrResWlan.openTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
			</td>
			<td class="label" ><font id="shareLabel3">共建共享</font>  </td>
			<td id="shareContent">
				<html:text property="pnrResWlan.sharing" styleId="sharing3"
						styleClass="text medium" 
						alt="allowBlank:false,vtext:'',maxLength:50" value="${pnrResWlan.sharing}" />
			</td>
		</tr>
		<tr class="wlan">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				<textarea name="pnrResWlan.adjustmentMessage" style="width:98%;text-align: left;" alt="maxLength:255" >${pnrResWlan.adjustmentMessage}</textarea>
			</td>
			
		</tr>-->
		
		
		<tr>
			<td class="label">备注  </td>
			<td colspan="3">
				<textarea rows="3" name="pnrResConfig.remarks" style="width:98%" alt="maxLength:255">${pnrResConfig.remarks}</textarea>
			</td>
		</tr>
		
	</table>
	
	<table>
	    <tr>
	        <td>
	            <input type="submit" class="btn" value="保存" id='submitInput' name='submitInput' />
	            <input type="button" class="btn" value="返回" 
	                    onclick="javascript:history.back();" 
	            <!--<c:if test="${not empty modifyForm.id}">-->
	            <input type="submit" class="btn" value="保存" id='submitInput' name='submitInput' />
	                <input type="button" class="btn" value="返回" 
	                    onclick="javascript:history.back();"    />
	            <!--</c:if>-->
	        </td>
	    </tr>
	</table>
	<table id="transData">
		<tr>
			<td>
				<html:text property="pnrResConfig.id" styleId="id"
						styleClass="text medium" name="pnrResConfig.id"
						value="${pnrResConfig.id}" />
			</td>
			<td>
				<html:text property="pnrResConfig.subResId" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
						value="${pnrResConfig.subResId}" />
			</td>
			<td>
				<html:text property="pnrResConfig.subResTable" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
						value="${pnrResConfig.subResTable}" />
			</td>
			<td>
				<html:text property="pnrResConfig.executeType" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
						value="${pnrResConfig.executeType}" />
			</td>
		</tr>
		<tr>
			<td>
				<html:text property="pnrResConfig.inspectCycle" styleId="pnrResConfig.inspectCycle"
						styleClass="text medium" name="pnrResConfig.inspectCycle"
					    value="${pnrResConfig.inspectCycle}" />
			</td>
			<td>
				<html:text property="pnrResConfig.executeObject" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
					    value="${pnrResConfig.executeObject}" />
			</td>
			<td>
				<html:text property="pnrResConfig.executeDept" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
					    value="${pnrResConfig.executeDept}" />
			</td>
			<td>
				<html:text property="pnrResConfig.executeDept" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
					    value="${pnrResConfig.executeDept}" />
			</td>
		</tr>
		<tr>
			<td>
				<html:text property="pnrResConfig.creator" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
					    value="${pnrResConfig.creator}" />
			</td>
			<td>
				<html:text property="pnrResConfig.createTime" styleId="resId"
						styleClass="text medium" name="pnrResConfig.resId"
					    value="${pnrResConfig.createTime}" />
			</td>
		</tr>
	</table>
	
	<!-- 
	<input type="hidden" id="resId" value="${pnrResConfig.id }" name="resId" />
	<input type="hidden" value="${pnrResConfig.subResId}" name="pnrResConfig.subResId"/>
	<input type="hidden" value="${pnrResConfig.subResTable}" name="pnrResConfig.subResTable"/>
	<input type="hidden" value="${pnrResConfig.executeType}" name="pnrResConfig.executeType"/>
	<input type="hidden" value="${pnrResConfig.inspectCycle}" name="pnrResConfig.inspectCycle"/>
	
	 -->
    </html:form>
  </body>
</html>
