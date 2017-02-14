<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<script type="text/javascript">

	Ext.onReady(function(){
	     
	      var v = new eoms.form.Validation({form:'programmeForm'});
          v.custom = function(){ 
	             if(!proportionValidate()) 
	              {
	              
	            		return false;
	              }
	            	 
	             return true;
	      };  
	   
    });
   
  function refreshTree(){
	try{   
		if (null != parent.parent.AppFrameTree) { 
		parent.parent.AppFrameTree.reloadNode(); 
		} 
		if (null != parent.AppFrameTree) { 
		// parent.AppFrameTree.refreshTree(); 
		// parent.AppFrameTree.refreshNodeTree(); 
		   parent.AppFrameTree.reloadNode(); 
		} 
	}catch(e){} 
  }
  
  function proportionValidate1(){
     Ext.get("proportionDiv").dom.innerHTML="请输入数字";
     Ext.get("proportionDiv").show();    
  }
  function proportionValidate2(proportion){
     Ext.get("proportionDiv").hide();
       var prop = Ext.get("id_col_proportion").getValue();
     if(prop!=""){
     Ext.getDom("id_col_fraction").value= (prop*proportion*0.01).toFixed(2);
     }         
  }
  function fractionValidate1(){
     Ext.get("fractionDiv").dom.innerHTML="请输入数字";
     Ext.get("fractionDiv").show();         
              
  }
  function fractionValidate2(proportion){
     Ext.get("fractionDiv").hide();
     var fraction = Ext.get("id_col_fraction").getValue();
     if(fraction!=""){
     Ext.getDom("id_col_proportion").value= ((fraction/proportion)*100).toFixed(2);
     }         
  }
  function proportionValidate(){
        var proportionReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var proportion=Ext.get("id_col_proportion").getValue();   
        var proportionDiv=Ext.get("proportionDiv");	//获得的是Ext.element
   		if(proportion.match(proportionReg) && ""!=proportion){
   			var prp=parseFloat(proportion);
   		    if(prp>0&&prp<=100){
   		        proportionDiv.dom.innerHTML="格式正确";
   		        //proportionDiv.style.backgroundColor="#FFFF00";
   				proportionDiv.setStyle("backgroundColor","#FFFF00");
   				proportionDiv.show();
   				return true;
   		    }else{
   		      	proportionDiv.dom.innerHTML="输入的数字，超出了范围，应为大于0小于等于100的数字";
             	proportionDiv.setStyle("backgroundColor","#FF0000");
             	proportionDiv.show();
            	return false;
   		    }
      	}else{
           	proportionDiv.dom.innerHTML="输入不合法,请输入数字 例：10.5";
           	//proportionDiv.style.backgroundColor="#FF0000";
           	proportionDiv.setStyle("backgroundColor","#FF0000");
           	proportionDiv.show();
           	return false;
      	}            
  }
 </script>



<div><font color="red">${errormsg}</font></div>
<form action="${app}/partner/evaluation/evaluation.do?method=doUpdateProgramme" method="post" id="programmeForm" name = "programmeForm">
	<input type="hidden" id="id" name="id" value="${programme.id}" />
	<input type="hidden" id="owntmplid" name="owntmplid" value="${owntmplid}" />
	<input type="hidden" id="prtlinkid" name="prtlinkid" value="${programme.prtlinkid}" />
	<input type="hidden" id="owntmpl" name="owntmpl" value="${owntmpl}" />
	<table id="sheet" class="formTable">
	    <tr>
	        <td class="label">
				大纲名称<font color='red'> * </font>
			</td>
			<td>
				<input type="text" class="text" name="nm_col_prgmnm" id="id_col_prgmnm" style="width: 80%;" 
					alt="allowBlank:false,vtext:'大纲名称不能为空 不能超出1000个汉字！',maxLength:1000"
				 value="${programme.prgmnm}"/>
			</td>
	      
			<td class="label">
			  <c:if test="${owntmplIsXM}">
			    所属考核项目
			  </c:if>
			  <c:if test="${owntmplIsZY}">
			    所属考核专业
			  </c:if>	 
			</td>
			<td>
				<input type="text" class="text" name="nm_col_owntmpl" id="id_col_owntmpl"					
				 value="${owntmpl}" style="border:none;background:none;" readOnly="true"/>
			</td>	     
	    </tr>
	
		<tr>
			<td class="label">
				权重占比<font color='red'> * </font>
			</td>
			<td>
				<input type="text" class="text" name="nm_col_proportion" id="id_col_proportion"
					onfocus="javascript:proportionValidate1(this);" onblur="javascript:proportionValidate2(${proportion});"
					value="${programme.proportion}"/>
				<div id="proportionDiv" class="ui-state-highlight ui-corner-all" style="width:150px;display:none">
				  <span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				  <div>请输入数字</div>
				</div>		
			</td>
		   	
		    <td class="label">
				分数<font color='red'> * </font>
			</td>
			<td>
				<input type="text" class="text" name="nm_col_fraction" id="id_col_fraction"
					onfocus="javascript:fractionValidate1(this);" onblur="javascript:fractionValidate2(${proportion});"
					value="${programme.fraction}"/>
				<div id="fractionDiv" class="ui-state-highlight ui-corner-all" style="width:150px;display:none">
				  <span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				  <div>请输入数字</div>
				</div>		
			</td>
		</tr>
		<tr>
			<td class="label">
				备注
			</td>
			<td class="content">
				<textarea name="nm_col_note" id="id_col_note" class="text medium" style="width: 95%; height: 80px;" 
					alt="allowBlank:true,vtext:'备注 不能超出1000个汉字！',maxLength:1000"
					>${programme.note}</textarea>
			</td>
		</tr>		
	</table>
	<div id="buttonGroup">
		<input type="submit" id="saveAll" value="保存" class="btn" />
		<input type="reset" id="resetAll" value="重置" class="btn" />
	</div>
    <div id="loadIndicator" class="loading-indicator" style="display:none">保存中，请等待...</div>
</form>
<%@ include file="/common/footer_eoms.jsp"%>