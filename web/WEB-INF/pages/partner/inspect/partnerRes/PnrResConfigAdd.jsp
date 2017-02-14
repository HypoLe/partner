<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
var tr = true;
var tr1 = true;
Ext.onReady(function() {
var v = new eoms.form.Validation({form:'resForm'});

	  jq("#sheet").find("tr.jizhan,tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
			jq(this).hide();
       		jq(this).find(":input").each(function(index){
       			jq(this).attr("disabled",true);
       		});	
		}); 
});
function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
            
        }

function selectRes(){
   delAllOption("inspectCycle");// 选资源级别，重新刷新巡检周期

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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           		
           		case "1122502":
           			jq("#sheet tr.zhifang").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.jizhan,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.jieke,tr.wlan,tr.family").each(function(index){
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda,tr.wlan,tr.family").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda,tr.jieke,tr.family").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
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
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;
           			
           		case "1122511":
					jq("#sheet tr.jizhan").each(function(tr){
		            	jq(this).show();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",false);
		            	});
		           	}); 
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});
           			break;	
           			
           			default:
           			jq("#sheet").find("tr.rode,tr.zhifang,tr.jizhan,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
									jq(this).hide();
		            	jq(this).find(":input").each(function(index){
		            		jq(this).attr("disabled",true);
		            	});	
					});			
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
// 资源级别，巡检周期联动
function setWeekTime(pnrid)
		{    
   delAllOption("inspectCycle");// 选资源级别，重新刷新巡检周期
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
						var inspectCycle = "inspectCycle";
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
function changeType(){
test();
	var propertyType = jq("#propertyType").val();
	if(propertyType!="共享"){
		/*jq("#sharing").attr("disabled",true);
		eoms.form.disable("sharing");*/
		jq("#sharing").attr("disabled",true);
		jq("#shareLabel").attr("disabled",true);
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
		/*jq("#sharing").attr("disabled",true);
		eoms.form.disable("sharing");*/
		jq("#sharing2").attr("disabled",true);
		jq("#shareLabel2").attr("disabled",true);
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
		jq("#sharing3").attr("disabled",true);
		jq("#shareLabel3").attr("disabled",true);
		jq("#shareLabel3").hide();
		jq("#sharing3").hide();
	}else{
		jq("#shareLabel3").attr("disabled",false);
		jq("#sharing3").attr("disabled",false);
		jq("#sharing3").show();
		jq("#shareLabel3").show();
	}
}



/*xls导入*/
function openImport(handler){
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}

function saveImport() {
	/*表单验证
	if(!v1.check()) {
		return;
	}*/
	var text = jq("#importType").val();
	if(text==''){
		alert('请选择巡检专业');
		return;
	}
	var file = jq("#importFile").val();
	if(file==''){
		alert('请选需上传的文件');
		return ;
	}
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
		url : "${app}/partner/res/PnrAllRes.do?method=importData",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			Ext.Msg.alert('提示信息', action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			Exe.MessageBox.alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
 }

</script>
  <body>
 
   <table class="formTable" style="display:none">
	<caption>
		巡检资源批量录入
	</caption>
  </table>
  <div style="border:1px solid #98c0f4;padding:5px;display:none"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/ico_file_excel.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
  </div>
  <div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 <html:form action="PnrAllRes.do?method=importData"
		method="post" styleId="importForm"
		enctype="multipart/form-data" onsubmit="return checkForm()">
		<table border=0 cellspacing="1" cellpadding="1" class="listTable">
			<!--附加表以及XML文件基本属性表格：开始-->
			<tr >
				<td  COLSPAN="3" class="label">请选择巡检专业<font color="red">*</font></td>
				<td>
		        	
		        	<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
		        		<eoms:comboBox  name="specialty" id="importType"  defaultValue="" onchange="selectRes()" 
		        			initDicId="11225" alt="allowBlank:false" styleClass="select" hiddenOption="1122502" />
		        	</c:if>		
		        	<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
		        		<eoms:comboBox  name="specialty" id="importType"  defaultValue="" onchange="selectRes()" 
		        			initDicId="11225" alt="allowBlank:false" styleClass="select" />
		        	</c:if>		
		        			
		    	</td>
		    </tr>    
			<tr >
				<td COLSPAN="3" class="label">
					巡检资源上传<font color="red">*
				</td>
				<td COLSPAN="14">
					<input id="importFile" type="file" name="importFile" class="file" alt="allowBlank:false"  /><font color="red">请选择上传xls格式的文件</font>
				</td>
			</tr>
			<tr>
				<td COLSPAN="17"><input type="button" value="导入" class="submit" onclick="saveImport()">&nbsp;&nbsp;&nbsp; &nbsp;
					<input type="button" class="button" name="save" value="下载导入模板"
						onclick="javascript:location.href='${app}/partner/res/PnrAllRes.do?method=download'" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="hidden" class="button" onclick="javascript:location.href='${app}/partner/res/PnrAllRes.do?method=excelExport'"" value="按专业类型导出">
					<input type="hidden" name="partneridXls" id="partneridXls"value="" />
					<input type="hidden" name="formInterfaceHeadIdXls"
						id="formInterfaceHeadIdxls" value="" />
				</td>
			</tr>
		</table>
	</html:form>
  </div>
  <br/>
    <html:form action="PnrAllRes.do?method=addSuccess" method="post" styleId="resForm">
	<table class="formTable" id="sheet">
		<caption>
			<div class="header center">巡检资源添加</div>
		</caption>
		<tr>
			<td class="label" >资源名称 &nbsp<font color='red'>*</font></td>
			<td class="content">
					<html:text property="pnrResConfig.resName" 
						styleClass="text medium" name="pnrResConfig.resName"
						alt="allowBlank:false,vtext:'',maxLength:50" value=""  />
			</td>
			<td class="label">巡检专业&nbsp<font color='red'>*</font></td>
			<td class="content">
			<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
				<eoms:comboBox  name="pnrResConfig.specialty" id="zhuanye" sub="resourceTeype" defaultValue="" onchange="selectRes()" 
	        			initDicId="11225" alt="allowBlank:false" styleClass="select" hiddenOption="1122502" />
			</c:if>
			<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
				<eoms:comboBox  name="pnrResConfig.specialty" id="zhuanye" sub="resourceTeype" defaultValue="" onchange="selectRes()" 
	        			initDicId="11225" alt="allowBlank:false" styleClass="select" />
			</c:if>
				
			</td>
		</tr>
		<tr>
			<td class="label">资源类别&nbsp<font color='red'>*</font></td>
			<td class="content">
				<eoms:comboBox name="pnrResConfig.resType" defaultValue="" id="resourceTeype" initDicId="${zhuanye}" alt="allowBlank:false" sub="resourceLevel" styleClass="select" /> 
			</td>
			<td class="label">资源级别&nbsp<font color='red'>*</font></td>
			<td class="content" >
					<eoms:comboBox name="pnrResConfig.resLevel" defaultValue="" id="resourceLevel" initDicId="${zhuanye}" alt="allowBlank:false" styleClass="select" onchange="setWeekTime(this.value);" /> 
			</td>
		</tr> 
		<tr>
			<td class="label">资源经度&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.resLongitude" styleId="longitude"
						styleClass="text medium" 
						alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="" />
				
						
			</td>
			<td class="label">资源纬度&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.resLatitude" styleId="resLatitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32,re:/^(\d+)(\.\d{1,15})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'" value="" />	
			</td>
		</tr>
		<tr>
			<td class="label">地市&nbsp<font color='red'>*</font></td>
			<td class="content">
				<select name="region" id="region" class="select"
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
			<td class="label">区县&nbsp<font color='red'>*</font></td>
			<td class="content">
				<select name="city" id="city" class="select"
				alt="allowBlank:false,vtext:'请选择所在县区'">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
			</td>
		</tr>
		<tr>
			<td class="label">地理环境</td>
			<td class="content">
				<eoms:comboBox  name="pnrResConfig.eographicalEnvironment" id="environment"  defaultValue="" 
	        			initDicId="11229"  styleClass="select" />
			</td>
			<td class="label">区域类型</td>
			<td class="content">
				<eoms:comboBox  name="pnrResConfig.regionType" id="regionType"  defaultValue="" 
	        			initDicId="11230"  styleClass="select" />
			</td>
		</tr>
		<tr>
			<td class="label" >乡镇/街道 </td>
			<td class="content">
					<html:text property="pnrResConfig.street" 
						styleClass="text medium" name="pnrResConfig.street"
						alt="maxLength:50" value=""  />
			</td>
			<td class="label">服务区域&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.serviceRegion" 
						styleClass="text medium" name="pnrResConfig.serviceRegion"
						alt="allowBlank:false,vtext:'',maxLength:50" value="" />
			</td>
		</tr>
		<tr>
			<td class="label" >业主单位名称&nbsp<font color='red'>*</font></td>
			<td class="content">
					<html:text property="pnrResConfig.companyName" 
						styleClass="text medium" name="pnrResConfig.companyName"
						alt="allowBlank:false,vtext:'',maxLength:50" value="" />
			</td>
			<td class="label">联系人&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.contactName" 
						styleClass="text medium" name="pnrResConfig.contactName"
						alt="allowBlank:false,vtext:'',maxLength:50" value=""  />
			</td>
		</tr>
		<tr>
			<td class="label">业主联系电话&nbsp<font color='red'>*</font></td>
			<td class="content">
				<html:text property="pnrResConfig.phone" 
						styleClass="text medium" name="pnrResConfig.phone"
						alt="maxLength:50,re:/(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})$|(\(\d{3,4}\)?\d{7,8})$/,re_vt:'请输入正确的电话号码'" value="" />
			<td class="label">巡检周期&nbsp<font color='red'>*</font></td>
			<td class="content">
			<input type="text" name="weektime" id="weektime" style="color: gray;text-align:center;" class="text medium" value="" alt="allowBlank:false,vtext:'',maxLength:50" readOnly />
			<input type="hidden" name="inspectCycle" id="inspectCycle" value="" />
			</td>
		</tr>
		
		
		
		<!-- 基站-->
		<tr class="jizhan">
			<td class="label">站址&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResConfigStation.address" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
			<td class="label">产权类型&nbsp<font color='red'>*</font></td>
			<td>
				<select name="propertyType" id="propertyType" onchange="changeType()" class="select" >
					<option>共享</option>
					<option>自建</option>
					<option>租用</option>
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
			<td class="label">网络类型 &nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResConfigStation.netType" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
			<td class="label" ><font id="shareLabel">共建共享&nbsp<font color='red'>*</font></font>  </td>
			<td id="shareContent">
				<html:text property="pnrResConfigStation.sharing" styleId="sharing"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>
		<tr class="jizhan">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				<textarea rows="3" name="pnrResConfigStation.adjustmentMessage" style="width:98%" alt="maxLength:255" >${gridForm.regionExplain}</textarea>
				
			</td>
			
		</tr>
		
		<!-- 传输线路
		<tr class="rode">
			<td class="label">终点经度</td>
			<td>
				<html:text property="pnrResLine.endLongitude" styleId="car_number"
						styleClass="text medium" alt="maxLength:50,re:/^(\d{0,8})(\.\d{1,7})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"
						 />
			</td>
			<td class="label">终点纬度</td>
			<td>
				<html:text property="pnrResLine.endLatitude" styleId="car_number"
						styleClass="text medium" alt="maxLength:50,re:/^(\d{0,8})(\.\d{1,7})?$/,re_vt:'请输入整数（小于8位）或小数（小数位小于15位）'"
						 />
			</td>
		</tr>
		<tr class="rode">
			<td class="label">管程/杆程</td>
			<td>
				<html:text property="pnrResLine.tubeSide" styleId="car_number"
						styleClass="text medium" alt="maxLength:50"
						 />
			</td>
			<td class="label">单路由光缆皮长(千米)</td>
			<td>
				<html:text property="pnrResLine.singleRouting" styleId="car_number"
						styleClass="text medium" alt="maxLength:50,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"
						 />
			</td>
		</tr>
		<tr class="rode">
			<td class="label">同路由光缆皮长（千米） </td>
			<td colspan="3">
				<html:text property="pnrResLine.likeRouting" styleId="car_number"
						styleClass="text medium" alt="maxLength:50,re:/^(\d{0,10})(\.\d{1,4})?$/,re_vt:'请输入整数或小数'"
						 />
			</td>
		</tr>-->
		
		<!-- 直放站
		
		<tr class="zhifang">
			<td class="label">站址&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResRepeaters.address" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
			<td class="label">产权类型&nbsp<font color='red'>*</font></td>
			<td>
				<select class="select" id="propertyType2" name="propertyType2" onchange="changeType2()" >
					<option>共享</option>
					<option>自建</option>
					<option>租用</option>
				</select>
						
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">TD开通年月</td>
			<td>
				<html:text property="pnrResRepeaters.TDOpenTime"  
                        styleClass="text medium"
                        value="${pnrResRepeaters.TDOpenTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
				
				
			</td>
			<td class="label">网络类型&nbsp<font color='red'>*</font> </td>
			<td>
				<html:text property="pnrResRepeaters.netType" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">2G开通年月</td>
			<td>
				<html:text property="pnrResRepeaters.openTime" 
                        styleClass="text medium"
                        value="${pnrResRepeaters.openTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
			</td>
			<td class="label" ><font id="shareLabel2">共建共享&nbsp<font color='red'>*</font></font>  </td>
			<td id="shareContent">
				<html:text property="pnrResRepeaters.sharing" styleId="sharing2"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>
		<tr class="zhifang">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				<textarea rows="3" name="pnrResRepeaters.adjustmentMessage" style="width:98%" alt="maxLength:255" >${gridForm.regionExplain}</textarea>
				
			</td>
			
		</tr>-->
		
		
		<!-- 铁搭
		<tr class="tieda">
			<td class="label">型号</td>
			<td>
				<html:text property="pnrResIron.ironType" styleId="car_number"
						styleClass="text medium" alt="maxLength:50"
						 />
			</td>
			<td class="label">室内机编号</td>
			<td>
				<html:text property="pnrResIron.indoorNumber" styleId="car_number"
						styleClass="text medium" alt="maxLength:50" />
			</td>
		</tr>
		<tr class="tieda">
			<td class="label">室外机编号</td>
			<td>
				<html:text property="pnrResIron.outdoorNumber" styleId="car_number"
						styleClass="text medium" alt="maxLength:50" />
			</td>
			<td class="label">相数</td>
			<td>
				<html:text property="pnrResIron.phaseNumber" styleId="car_number"
						styleClass="text medium" alt="maxLength:50" />
			</td>
		</tr>
		<tr class="tieda">
			<td class="label">功率&nbsp<font color='red'>*</font></td>
			<td colspan="3">
				<html:text property="pnrResIron.power" styleId="car_number"
						styleClass="text medium" alt="allowBlank:false,vtext:'',maxLength:50"
						/>
			</td>
		</tr>-->
		
		<!-- 集客家客
		<tr class="jieke">
		
			<td class="label">客户数</td>
			<td>
				<html:text property="pnrResJieke.clientNumber" styleId="car_number"
						styleClass="text medium" alt="maxLength:50"
						/>
			</td>
			<td class="label">业务类型</td>
			<td>
				
				<select name="pnrResJieke.clientType" class="select">
					<option>--请选择--</option>
					<option>传输专线</option>
					<option>GPRS</option>
					<option>短彩信</option>
				</select>
			</td>
		</tr>
		<tr class="jieke">
		
			<td class="label">所属站点名称&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResJieke.stationName" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
			<td class="label">所属站点编号&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResJieke.stationNumber" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>-->
		
		<!-- 家庭宽带
		<tr class="family">
		
			<td class="label">客户数</td>
			<td>
				<html:text property="pnrResFamilyBroadband.clientNumber" styleId="car_number"
						styleClass="text medium" alt="maxLength:50"
						/>
			</td>
			<td class="label">业务类型</td>
			<td>
				
				<select name="pnrResFamilyBroadband.clientType" class="select">
					<option>--请选择--</option>
					<option>传输专线</option>
					<option>GPRS</option>
					<option>短彩信</option>
				</select>
			</td>
		</tr>
		<tr class="family">
		
			<td class="label">所属站点名称&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResFamilyBroadband.stationName" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
			<td class="label">所属站点编号&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResFamilyBroadband.stationNumber" styleId="car_number"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>-->
		
		<!-- WLAN--->
		
		<tr class="wlan">
			<td class="label">站址&nbsp<font color='red'>*</font></td>
			<td>
				<html:text property="pnrResWlan.address" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
			<td class="label">产权类型&nbsp<font color='red'>*</font></td>
			<td>
				<select class="select" id="propertyType3" name="propertyType3" onchange="changeType3()" >
					<option>共享</option>
					<option>自建</option>
					<option>租用</option>
				</select>
						
			</td>
		</tr>
		<tr class="wlan">
			<td class="label">TD开通年月</td>
			<td>
				<html:text property="pnrResWlan.TDOpenTime"  
                        styleClass="text medium"
                        value="${pnrResWlan.TDOpenTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
				
				
			</td>
			<td class="label">网络类型&nbsp<font color='red'>*</font> </td>
			<td>
				<html:text property="pnrResWlan.netType" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>
		<tr class="wlan">
			<td class="label">2G开通年月</td>
			<td>
				<html:text property="pnrResWlan.openTime" 
                        styleClass="text medium"
                        value="${pnrResWlan.openTime}" 
                        onclick="popUpCalendar(this, this,'yyyy-mm-dd',-1,-1,false,-1,0);" readonly="true" />
			</td>
			<td class="label" ><font id="shareLabel3">共建共享&nbsp<font color='red'>*</font></font>  </td>
			<td id="shareContent">
				<html:text property="pnrResWlan.sharing" styleId="sharing3"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:50"  />
			</td>
		</tr>
		<tr class="wlan">
			<td class="label">搬迁与配置调整情况  </td>
			<td colspan="3">
				<textarea rows="3" name="pnrResWlan.adjustmentMessage" style="width:98%" alt="maxLength:255" >${gridForm.regionExplain}</textarea>
				
			</td>
			
		</tr>
		
		
		<tr>
			<td class="label">备注  </td>
			<td colspan="3">
				<textarea rows="3" name="pnrResConfig.remarks" style="width:98%" alt="maxLength:255" >${pnrResConfig.remarks}</textarea>
				
			</td>
			
		</tr> <!--  wlan end-->
	</table>
	<br/>
	<input type="submit" class="btn" id="btn1" value="保存信息"/>
		<input type="reset" class="btn"  value="重置" />
    </html:form>
    
<%@ include file="/common/footer_eoms.jsp"%>
