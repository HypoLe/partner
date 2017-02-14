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
var zhuanyeType = "";
Ext.onReady(function() {
var v = new eoms.form.Validation({form:'resForm'});
zhuanyeType = jq("#zhuanye").val();
	  jq("#sheet").find("tr.jizhan,tr.rode,tr.zhifang,tr.tieda,tr.jieke").each(function(index){
			jq(this).hide();
       		jq(this).find(":input").each(function(index){
       			jq(this).attr("disabled",true);
       		});	
		});			
							
		
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
           			jq("#sheet tr.zhifang").each(function(tr){
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.tieda,tr.jieke").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		            jq("#sheet").find("tr.jizhan,tr.zhifang,tr.tieda,tr.jieke").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           		
           		case "1122503":
           			jq("#sheet tr.zhifang").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.jizhan,tr.tieda,tr.jieke").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.jieke").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		
function checkIsNull(){
    if(document.getElementById("resName").value==null||document.getElementById("resName").value.trim()==''){
        alert("资源名称不能为空！");
        return false;
    }
    if(document.getElementById("zhuanye").value==null||document.getElementById("zhuanye").value.trim()==''){
        alert("请选择巡检专业！");
        return false;
    }
    
     if(document.getElementById("resourceTeype").value==null||document.getElementById("resourceTeype").value.trim()==''){
        alert("请选择资源类别！");
        return false;
    }
    
      if(document.getElementById("region").value==null||document.getElementById("region").value.trim()==''){
        alert("请选择地区！");
        return false;
    }
	/*
	if(document.getElementById("region").value<document.getElementById("region").value){
	alert("请选择地区");
    return false;
    }*/
	
    return true;
}
		
//表单提交
function submitForm(){
	if(checkIsNull()){
      document.forms[0].submit();
    }
}

</script>
  <body>
    <html:form action="PnrAllRes.do?method=edit" method="post" styleId="resForm">
	<table class="formTable" id="sheet">
		<caption>
			<div class="header center">巡检资源信息</div>
		</caption>
		<tr>
			<td class="label" >资源名称</td>
			<td class="content">
					${pnrResConfig.resName}
			</td>
			<td class="label">巡检专业</td>
			<td class="content">
	        	<eoms:id2nameDB id="${pnrResConfig.specialty}" beanId="ItawSystemDictTypeDao" />		
	        	<input type="hidden" id="zhuanye" value="${pnrResConfig.specialty}">
			</td>
		</tr>
		<tr>
			<td class="label">资源类别</td>
			<td class="content">
				<eoms:id2nameDB id="${pnrResConfig.resType}" beanId="ItawSystemDictTypeDao" />
			</td>
			<td class="label">资源级别</td>
			<td class="content" >
					<eoms:id2nameDB id="${pnrResConfig.resLevel}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">资源经度</td>
			<td class="content">
				${pnrResConfig.resLongitude}
			</td>
			<td class="label">资源纬度</td>
			<td class="content">
				${pnrResConfig.resLatitude}
			</td>
		</tr>
		<tr>
			<td class="label">地市</td>
			<td class="content">
			
			
			<eoms:id2nameDB id="${pnrResConfig.city}" beanId="tawSystemAreaDao" />
			
			</td>
			<td class="label">区县</td>
			<td class="content">
	
			<eoms:id2nameDB id="${pnrResConfig.country}" beanId="tawSystemAreaDao" />
			</td>
		</tr>
		<tr>
			<td class="label" >乡镇/街道 </td>
			<td class="content">
					${pnrResConfig.street}
			</td>
			<td class="label">服务区域</td>
			<td class="content">
				${pnrResConfig.serviceRegion }
			</td>
		</tr>
		<tr>
			<td class="label" >业主单位名称</td>
			<td class="content">
				${pnrResConfig.companyName }
			</td>
			<td class="label">联系人</td>
			<td class="content">
				${pnrResConfig.contactName }
			</td>
		</tr>
		<tr>
			<td class="label">业主联系电话</td>
			<td class="content">
				${pnrResConfig.phone }
			</td>
			<td class="label" >备注</td>
			<td class="content">
					${pnrResConfig.remarks}
			</td>
		</tr>
		
		<!-- 基站-->
		<tr class="jizhan">
			<td class="label">站址</td>
			<td>
				${pnrResConfigStation.address}
			</td>
			<td class="label">产权类型</td>
			<td>
			
			<c:if test="${pnrResConfigStation.propertyType eq 1}">
			共享
			</c:if>
			<c:if test="${pnrResConfigStation.propertyType eq 2}">
			自建
			</c:if>
			<c:if test="${pnrResConfigStation.propertyType eq 3}">
			租用
			</c:if>
				
			</td>
		</tr>
		<tr class="jizhan">
			<td class="label">共建共享 </td>
			<td>
				${pnrResConfigStation.sharing}
			</td>
			<td class="label">网络类型 </td>
			<td>
				${pnrResConfigStation.netType}
			</td>
		</tr>
		<tr class="jizhan">
			<td class="label">2G开通年月  </td>
			<td>
				${pnrResConfigStation.openTime}
			</td>
			<td class="label">TD开通年月  </td>
			<td>
				${pnrResConfigStation.TDOpenTime}
			</td>
		</tr>
		<tr class="jizhan">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				${pnrResConfigStation.adjustmentMessage}
			</td>
			
		</tr>
		
		<!-- 传输线路-->
		<tr class="rode">
			<td class="label">终点经度</td>
			<td>
				${pnrResLine.endLongitude}
			</td>
			<td class="label">终点纬度</td>
			<td>
				${pnrResLine.endLatitude}	
			</td>
		</tr>
		<tr class="rode">
			<td class="label">管程/杆程</td>
			<td>
				${pnrResLine.tubeSide}
			</td>
			<td class="label">单路由光缆皮长</td>
			<td>
				${pnrResLine.singleRouting}
			</td>
		</tr>
		<tr class="rode">
			<td class="label">同路由光缆皮长 </td>
			<td colspan="3">
				${pnrResLine.likeRouting}
			</td>
		</tr>
		
		<!-- 直放站-->
		<tr class="zhifang">
			<td class="label">站址</td>
			<td>
				${pnrResRepeaters.address}
			</td>
			<td class="label">产权类型</td>
			<td>
			
			<c:if test="${pnrResRepeaters.propertyType eq 1}">
			共享
			</c:if>
			<c:if test="${pnrResRepeaters.propertyType eq 2}">
			自建
			</c:if>
			<c:if test="${pnrResRepeaters.propertyType eq 3}">
			租用
			</c:if>
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">共建共享 </td>
			<td>
				${pnrResRepeaters.sharing}
			</td>
			<td class="label">网络类型 </td>
			<td>
				${pnrResRepeaters.netType}
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">2G开通年月  </td>
			<td>
				${pnrResRepeaters.openTime}
			</td>
			<td class="label">TD开通年月  </td>
			<td>
				${pnrResRepeaters.TDOpenTime}
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				${pnrResRepeaters.adjustmentMessage}
			</td>
			
		</tr>
		
		
		<!-- 铁搭-->
		<tr class="tieda">
			<td class="label">型号</td>
			<td>
				${pnrResIron.ironType}
			</td>
			<td class="label">室内机编号</td>
			<td>
				${pnrResIron.indoorNumber}
			</td>
		</tr>
		<tr class="tieda">
			<td class="label">室外机编号</td>
			<td>
				${pnrResIron.outdoorNumber}
			</td>
			<td class="label">相数</td>
			<td>
				${pnrResIron.phaseNumber}
			</td>
		</tr>
		<tr class="tieda">
			<td class="label">功率</td>
			<td colspan="3">
				${pnrResIron.power}
			</td>
		</tr>
		
		<!-- 集客家客-->
		<tr class="jieke">
		
			<td class="label">客户数</td>
			<td>
				${pnrResJieke.clientNumber}
			</td>
			<td class="label">业务类型</td>
			<td>
			
			<c:choose>
				<c:when test="${pnrResJieke.clientType eq '--请选择--' }">
				
				</c:when>
				<c:otherwise>
				${pnrResJieke.clientType}
				</c:otherwise>
			</c:choose>
			
			</td>
		</tr>
		<tr class="jieke">
		
			<td class="label">所属站点名称</td>
			<td>
				${pnrResJieke.stationName}
			</td>
			<td class="label">所属站点编号</td>
			<td>
				${pnrResJieke.stationNumber}
			</td>
		</tr>
		
	</table>
	
	<table>
	    <tr>
	        <td>
	             
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
	
    </html:form>
  </body>
</html>
