<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/scripts/ext3/resources/css/ext-all.css" />
<script type="text/javascript" src="${app}/scripts/ext3/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${app}/scripts/ext3/ext-all.js"></script>
<%@ include file="/common/body.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
  var myJq=jQuery.noConflict();
  var cnttypStore = new Ext.data.ArrayStore({ //ArrayStore的每一个一个元素是一个数组 
			url : '${app}/partner/feeManage/feeManage.do?method=outCnttypStoreJson',
			baseParams:{}, 
			fields: [{name: 'cnttypcd',type: 'string'},
                     {name: 'cnttyp',type:'string'}]
		});
	var cnttypCombo= new Ext.form.ComboBox({
	        id:'id_cnttypCombo',
			store : cnttypStore,
			emptyText:'请选择',
			valueField : "cnttypcd",
			displayField : "cnttyp",
			mode : 'remote',
			forceSelection : true,			
			editable : false,
			disabled:true,
			triggerAction : 'all',
			hiddenName : 'cnttyp',
			hiddenId:'cnttyp'
		});	

  Ext.onReady(function(){
     cnttypCombo.render("div_cnttyp");       
  });

  function majorChange(ths){
     Ext.getDom("modelid").value="";
     cnttypCombo.clearValue();
  
     var majorid=ths.value;
     if(majorid&&(new String(majorid)).trim()!=''){
         Ext.getDom("modelid").disabled=false; 
         cnttypStore.baseParams['majorid']=majorid;
         cnttypStore.load();
         //cnttypCombo.enable();   
     }else{
        //myJq('modelid').attr('disabled',true);        
        Ext.getDom("modelid").disabled=true; 
        cnttypCombo.disable();       
     }
     
  }
  
  function modelChange(ths){
    cnttypCombo.clearValue() ;
  
    var modelid=ths.value;
     if(modelid&&(new String(modelid)).trim()!=''){
         cnttypCombo.enable();   
     }else{
        cnttypCombo.disable();       
     }
  }
  
</script>

<form action="${app}/partner/feeManage/feeManage.do?method=priceConfigChangedConfirm" name="priceConfigChangedConfirmForm" method="post">
    <table id="sheet" class="formTable">
        <tr>	    
			<td class="label">
				专业<font color='red'> * </font>
			</td>
			<td>
				<eoms:comboBox name="specialty" id="specialty" onchange="javascript:majorChange(this);"
					initDicId="11225" alt="allowBlank:false,vtext:'专业必选！'" />
			</td>	
			<td class="label">
				专业内模型ID
			</td>
			<td>
				<select name="modelid" id="modelid" disabled="true" onChange="javascript:modelChange(this);">
				  <option value="">请选择</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				  <option value="4">4</option>
				  <option value="5">5</option>
				  <option value="6">6</option>
				</select>
			</td>     	 			
	    </tr>	    
	    <tr>	       		
			<td class="label">
				计次类型
			</td>
			<td>
			  <div id="div_cnttyp"></div>
			</td>	   
			<td class="label"></td>
			<td class="content">				
			</td>   	  	
	    </tr>
	    
	    
	    <tr>	       		
			<td class="label">
				变更类型
			</td>
			<td>
			    <select name="changetype">
				  <option value="1">修改</option>
				  <option value="2">删除</option>
				</select>
			</td>	   
			<td class="label"></td>
			<td class="content">
			   <input type="submit" value="确定"/>				
			</td>   	  	
	    </tr>
    </table>
</form>

<%@ include file="/common/footer_eoms.jsp"%>