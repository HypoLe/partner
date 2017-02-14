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
//var v = new eoms.form.Validation({form:'resForm'});
/*
	  jq("#sheet").find("tr.jizhan,tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
			jq(this).hide();
       		jq(this).find(":input").each(function(index){
       			jq(this).attr("disabled",true);
       		});	
		}); */
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
		            jq("#sheet").find("tr.rode,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
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
		            jq("#sheet").find("tr.jizhan,tr.zhifang,tr.tieda,tr.jieke,tr.wlan,tr.family").each(function(index){
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

function changeType(){
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

function validateFile() {
 	var reg = "(.csv)$";
  	var file = jq("#importFile").val();
  	var right = file.match(reg);
  	if(right == null) {
  		alert("请选择csv文件!");
  		jq("#importFile").val('');
  		return false;
  	} else {
  		return true;
  	}
}

function saveImport() {
	if(!validateFile()) {
		return;
	}
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
		url : "${app}/partner/res/pnrTransLineAction.do?method=importTransLine",
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
 
   <table class="formTable">
	<caption>
		光缆段导入
	</caption>
  </table>
  <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/ico_file_excel.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
  </div>
  <div id="listQueryObject" 
	 style="display:block;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 <form action="pnrTransLineAction.do?method=importTransLine"
		method="post" id="importForm"
		enctype="multipart/form-data">
		<table border=0 cellspacing="1" cellpadding="1" class="listTable">
			<tr>
				<td class="label">
					导入模板下载
				</td>
				<td>
					<input type="button" class="btn" value="下载导入文件模板" 
							onclick="javascript:location.href='${app}/partner/res/pnrTransLineAction.do?method=download&type=tl'"/>
				</td>
			</tr>
			<tr >
				<td class="label">
					光缆段导入<font color="red">*
				</td>
				<td>
					<input id="importFile" type="file" name="importFile" class="file" alt="allowBlank:false"  /><font color="red">请选择CSV格式的文件上传,并按照下载的模板格式样例填写数据,否则会产生不可预估的结果！</font>
				</td>
			</tr>
			<tr>
				<td COLSPAN="17"><input type="button" value="导入" class="submit" onclick="saveImport()">&nbsp;&nbsp;&nbsp; &nbsp;
				</td>
			</tr>
		</table>
	</form>
  </div>
  <br/>
    
<%@ include file="/common/footer_eoms.jsp"%>
