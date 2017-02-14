<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<link rel="stylesheet" href="${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/demos.css">
<link rel="stylesheet" type="text/css" href="${app}/nop3/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<style>
.distribCol1{text-align:justify;line-height:130%}
</style>

<!-- tab显示的顶级容器div -->
<div id="info-page" style="width:100%;overflow-x:hidden;overflow-y:hidden;">
</div>
<br/>
<div id="id_jiaoyan" style="float:left;">
 <input type="button" value="校验" onclick="validate()" class="btn"/> 
</div>
<div id="id_active1" style="width:auto;display: none; float:left">
   &nbsp; &nbsp; &nbsp;<input type="button" value="激活" onclick="activetemplate()" class="btn"/>
</div>
<div id="id_active2" style="width:auto;display: none; float:left">
   <input type="button" value="失效" onclick="notactivetemplate()" class="btn"/>
</div>
 
<div style="clear:both"></div> 
<br/> <br/> 
<div id="msg" style="clear:both"></div> <!-- 注意不要使用<div />，应使用<div></div>,否则后面的<div>将不会显示出来 -->
<div id="error" class="distribCol1"></div>

<script type="text/javascript">
 var app="${app}" ;  
 var activeornot="${activeornot}"; //true：已激活 ; false:未激活 
 var onlyView="${onlyView}"; 
 var jq=jQuery.noConflict();
 
Ext.onReady(function() {
	setTabs();
	
	var jiaoyanDiv=Ext.get("id_jiaoyan");
	var activeDiv=Ext.get("id_active1");	   
    var notactiveDiv=Ext.get("id_active2");
    if(onlyView=="true"){ 
        jiaoyanDiv.hide();
        activeDiv.hide();
      	notactiveDiv.hide();
    }
	else if(activeornot=="true"){   //已激活	  
        activeDiv.hide();//隐藏激活按钮 
       	notactiveDiv.show();
	}else{//未激活
		activeDiv.show();
       	notactiveDiv.hide();//隐藏失效按钮
	}
});


	function setTabs() {
	    var items=[];
		<%
		    List<Template> templateList=(List<Template>)request.getAttribute("templateList");
		    for(Template template:templateList){
		%>  
		       items.push({
		          text : "<%=template.getTmplnm() %>",
				  url : "${app}/partner/evaluation/evaluation.do?method=templatePreview&nodeId=<%=template.getId()%>" ,
				  isIframe : true 
		       });		       
		<%  } %>
		 		 
		var tabConfig = {
		          items : items
	         };
	    var tabs = new eoms.TabPanel('info-page', tabConfig);				  		
	}  
	
	 function validate(){
        Ext.Ajax.request({
	 	            url: app+'/partner/evaluation/evaluation.do?method=templateValidate',
                    params:{
       	  				templateid:"${templateid}"    
       				},
       				success:function(response,options){       				  
       					 var resulttext=response.responseText;
       					 rslt=Ext.decode(resulttext);        						 
       					 document.getElementById("msg").innerHTML=rslt.msg;  
       					 document.getElementById("error").innerHTML= rslt.error;  	
      				}
	 	        });
    };
    
    function activetemplate(){
         Ext.Ajax.request({
	 	            url: app+'/partner/evaluation/evaluation.do?method=templateActive',
                    params:{
       	  				templateid:"${templateid}"    
       				},
       				success:function(response,options){
       					 var resulttext=response.responseText;
       					 rslt=Ext.decode(resulttext); 
       					 if(rslt.active==true){ //激活了
       					   document.getElementById("msg").innerHTML=rslt.msg; 
       					   document.getElementById("error").innerHTML="";
       					   var activeDiv=Ext.get("id_active1");	   
       					   var notactiveDiv=Ext.get("id_active2");	
       					   activeDiv.hide();
       					   notactiveDiv.show();
       					   //刷新树
       					   try{   
		                     if (null != parent.parent.AppFrameTree) { 
		                       parent.parent.AppFrameTree.reloadNode(); 
		                      } 
		                     if (null != parent.AppFrameTree) { 
		                      // parent.AppFrameTree.refreshTree(); 
		                      // parent.AppFrameTree.refreshNodeTree(); 
		                      parent.AppFrameTree.reloadNode(); 
		                     } 
	                       }catch(e){
	                        
	                       } 
       					 } 
       					 else{
       					    document.getElementById("msg").innerHTML=rslt.msg;  
       					    document.getElementById("error").innerHTML= rslt.error; 
       					 }	
      				}
	 	        });
    }
    
    function notactivetemplate(){
         Ext.Ajax.request({
	 	            url: app+'/partner/evaluation/evaluation.do?method=templateNotActive',
                    params:{
       	  				templateid:"${templateid}"    
       				},
       				success:function(response,options){
       					 var resulttext=response.responseText;
       					 rslt=Ext.decode(resulttext);  
       					 if(rslt.notactive==true){ //失效了
       					   document.getElementById("msg").innerHTML=rslt.msg; 
       					   var activeDiv=Ext.get("id_active1");	   
       					   var notactiveDiv=Ext.get("id_active2");	
       					   activeDiv.show();
       					   notactiveDiv.hide();
       					    //刷新树
       					   try{   
		                     if (null != parent.parent.AppFrameTree) { 
		                       parent.parent.AppFrameTree.reloadNode(); 
		                      } 
		                     if (null != parent.AppFrameTree) { 
		                      // parent.AppFrameTree.refreshTree(); 
		                      // parent.AppFrameTree.refreshNodeTree(); 
		                      parent.AppFrameTree.reloadNode(); 
		                     } 
	                       }catch(e){
	                        
	                       } 
       					 } 
       					 else{
       					    document.getElementById("msg").innerHTML=rslt.msg;  
       					    document.getElementById("error").innerHTML= rslt.error; 
       					 }	 	
      				}
	 	        });
    }
</script>	
<%@ include file="/common/footer_eoms.jsp"%>	