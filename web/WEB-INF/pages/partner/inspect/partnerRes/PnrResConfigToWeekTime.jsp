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
function setnullOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
						  		  ddlObj.options[0].selected='selected';
            
        }

function selectRes(){
setnullOption("weektime");
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

// 资源级别，巡检周期联动
function setWeekTime(pnrid)
		{    
		setnullOption("weektime");		
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
						var weektime = "weektime";
						var arrOptions = json[0].cb.split(",");
						
						var obj=$(weektime);
						//获得长度					
						var i = arrOptions.length/2;
						//周期下拉框的个数
						var j=obj.options.length;
						 
						for(var k=0;k<i;k++){
						     for (var p=0;p<j;p++){
						 	   if(arrOptions[k+2]==obj.options[p].value){
						  		 
						  		  obj.options[p].selected='selected';
						  		  break;
						   		 }
						     
						     }
						
						}
										
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}
</script>
  <body>
<!-- PnrResConfig -->
    <html:form action="PnrResConfig.do?method=addToSuccess" method="post" styleId="resForm">
	<table class="formTable" id="sheet">
		<caption>
			<div class="header center">巡检周期配置</div>
		</caption>
		<tr>
			
			<td class="label">巡检专业&nbsp<font color='red'>*</font></td>
			<td class="content">			
				<eoms:comboBox  name="pnrResConfig.specialty" id="zhuanye" sub="resourceTeype" defaultValue="" onchange="selectRes()" 
	        			initDicId="11225" alt="allowBlank:false" styleClass="select" />				
			</td>
			<td class="label">资源类别&nbsp<font color='red'>*</font></td>
			<td class="content">
				<eoms:comboBox name="pnrResConfig.resType" defaultValue="" id="resourceTeype" initDicId="${zhuanye}" alt="allowBlank:false" sub="resourceLevel" styleClass="select" /> 
			</td>
		</tr>
		<tr>
			<td class="label">资源级别&nbsp<font color='red'>*</font></td>
			<td class="content" >
					<eoms:comboBox name="pnrResConfig.resLevel" defaultValue="" id="resourceLevel" initDicId="${zhuanye}" alt="allowBlank:false" styleClass="select" onchange="setWeekTime(this.value)"/> 
			</td>
			
			<td class="label" >周期 &nbsp<font color='red'>*</font></td>
			<td class="content">
      <eoms:comboBox  name="weektime" id="weektime" defaultValue="" initDicId="11234" alt="allowBlank:false" styleClass="select" />							</td>			
		</tr> 	
	</table>
	<br/>
	<input type="submit" class="btn" id="btn1" value="保存信息"/>
		<input type="reset" class="btn"  value="重置" />
    </html:form>
    
<%@ include file="/common/footer_eoms.jsp"%>
