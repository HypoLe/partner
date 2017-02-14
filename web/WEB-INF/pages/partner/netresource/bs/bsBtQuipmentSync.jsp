<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<script type="text/javascript"><!--
var jq=jQuery.noConflict();

//用于测试更新的

function  usetotest(value){
    Ext.get(document.body).mask('更新数据中......');
 	
	 	        jq.ajax({
	 	          url: "${app}/netresource/bsbt/pnrbsbt.do?method=useSyn",
 				 cache: false,
 				 data:{param:value},
 				 dataType: "json",
 				 success: function(){ 
 				 var msg="没有操作！！！";	
 				 if(value==1){
 				 msg="基站机房设备同步完成！！！";
 				 }else if(value==2){
 				 msg="接入网机房设备同步完成！！！";
 				 }	
 				 	Ext.Msg.alert('提示信息',msg);		 
  				}
				});
				
				
 //   setTimeout(function(){
					Ext.get(document.body).unmask();
	//	       },60000);	
}


</script>
<div id="uset" style="text-align:center;margin-top:25%;margin-bottom:5%;">
<input type="button" onclick="usetotest(1)" value="基站机房设备同步"/>
</div>
<div id="uset2" style="text-align:center;">
<input type="button" onclick="usetotest(2)" value="接入网机房设备同步"/>
</div>
