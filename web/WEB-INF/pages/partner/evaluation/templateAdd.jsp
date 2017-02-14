<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<script type="text/javascript">

	Ext.onReady(function(){
	     
	      var v = new eoms.form.Validation({form:'templateForm'});
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
     Ext.get("proportionDiv").dom.innerHTML="请输入数字,且大于等于0，小于等于100";
     Ext.get("proportionDiv").show();    
  }
  function proportionValidate2(){
     Ext.get("proportionDiv").hide();    
  }
  function proportionValidate(){
        var proportionReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
        var proportion=Ext.get("id_addcol_proportion").getValue();   
        var proportionDiv=Ext.get("proportionDiv");	//获得的是Ext.element
   		if(proportion.match(proportionReg) && ""!=proportion){
   		    if(0<=proportion&&proportion<=100){ 
   		        proportionDiv.dom.innerHTML="格式正确";
   				proportionDiv.setStyle("backgroundColor","#FFFF00");
   				proportionDiv.show();
   				return true;
   		    }else{
   		       	proportionDiv.dom.innerHTML="输入不合法,请输入数字,且大于等于0，小于等于100  例：10.5";
           	    proportionDiv.setStyle("backgroundColor","#FF0000");
           		proportionDiv.show();
           		return false;
   		    }   			
      	}else{
           	proportionDiv.dom.innerHTML="输入不合法,请输入数字,且大于等于0，小于等于100  例：10.5";
           	//proportionDiv.style.backgroundColor="#FF0000";
           	proportionDiv.setStyle("backgroundColor","#FF0000");
           	proportionDiv.show();
           	return false;
      	}            
  }
 </script>



<div><font color="red">${errormsg}</font></div>
<form action="${app}/partner/evaluation/evaluation.do?method=doAddTemplate" method="post" id="templateForm" name = "templateForm">
	<input type="hidden" id="prtlinkid" name="prtlinkid" value="${prtlinkid}" />
	<input type="hidden" id="prttmpltyp" name="prttmpltyp" value="${prttmpltyp}" />
	<table id="sheet" class="formTable">
	    <tr>
	      <c:if test="${!isAddXM and !isAddZY}">
	        <td class="label">
				模板类型<font color='red'> * </font>
			</td>
			<td class="content">
			      <select id="id_addcol_tmpltyp" name="nm_addcol_tmpltyp" class="text" 
				  alt="allowBlank:false,vtext:'必须选择一个类型！'">
					<option value="Y" >
						年模板
					</option>
					<option value="M" >
						月模板
					</option>										
				  </select>
			    <!--  
			    <c:if test="${prttmpltyp=='Y'}">
			      <select id="id_addcol_tmpltyp" name="nm_addcol_tmpltyp" class="text" 
				  alt="allowBlank:false,vtext:'必须选择一个类型！'">					
					<option value="M" >
						月模板
					</option>	
					<option value="O" >
						其他
					</option>				
				  </select>
			    </c:if>
			    <c:if test="${prttmpltyp=='M'}">
			      <select id="id_addcol_tmpltyp" name="nm_addcol_tmpltyp" class="text" 
				  alt="allowBlank:false,vtext:'必须选择一个类型！'">
					<option value="O" >
						其他
					</option>				
				  </select>
			    </c:if>
			    <c:if test="${prttmpltyp=='O'}">
			      <select id="id_addcol_tmpltyp" name="nm_addcol_tmpltyp" class="text" 
				  alt="allowBlank:false,vtext:'必须选择一个类型！'">
					<option value="O" >
						其他
					</option>				
				</select>
			    </c:if>	
			   --> 			
			</td>
		  </c:if>	
			
			<td class="label">
			    <c:if test="${!isAddXM and !isAddZY}">
			       模板名称<font color='red'> * </font>
			    </c:if>				
				<c:if test="${isAddXM}">
				  考核项目<font color='red'> * </font>
				</c:if>
				<c:if test="${isAddZY}">
				  考核专业<font color='red'> * </font>
				</c:if>
			</td>
			<td>
			   <input type="hidden" name="isAddXM" value="${isAddXM}"/>
			   <input type="hidden" name="isAddZY" value="${isAddZY}"/>
			   <c:if test="${! isAddXM and !isAddZY}">
			      <input type="text" class="text" name="nm_addcol_tmplnm" id="id_addcol_tmplnm" style="width: 300px;"
					alt="allowBlank:false,vtext:'模板名称不能为空 不能超出100个汉字！',maxLength:100"
				  />
			   </c:if>
			   <c:if test="${isAddXM}">
			      <c:if test="${prttmpltyp=='Y'}">
			       <eoms:comboBox name="XM" id="id_XM" defaultValue="11227010201"
					initDicId="112270102" alt="allowBlank:false,vtext:'考核项目必选！'" 
				   /> 
				  </c:if>	
				  <c:if test="${prttmpltyp=='M'}">
			       <eoms:comboBox name="XM" id="id_XM" defaultValue="11227010101"
					initDicId="112270101" alt="allowBlank:false,vtext:'考核项目必选！'" 
				   /> 
				  </c:if>	
			   </c:if>		
			   <c:if test="${isAddZY}">
			       <eoms:comboBox name="ZY" id="id_ZY" defaultValue="1122501"
					initDicId="11225" alt="allowBlank:false,vtext:'考核专业必选！'" 
				   /> 
			   </c:if>
			</td>
			
			<c:if test="${isAddXM}">
			    <td class="label">			    
			      <label style="width:100px;">是否按专业考核<font color='red'> * </font></label> 						 
			    </td>
			    <td class="label">
			       <select name="XMHasZY" id="XMHasZY" style="width:80px;">
			         <option value="1" selected>
			            是
			         </option>
			         <option value="0">
			            否
			         </option>
			       </select>
			    </td>
			</c:if>			
			<c:if test="${isAddZY}">
			   <td class="label">
				备注
			   </td>
			   <td class="content">
				    <textarea name="nm_addcol_note" id="id_addcol_note" class="text medium" style="width: 95%; height: 80px;" 
					alt="allowBlank:true,vtext:'备注 不能超出1000个汉字！',maxLength:1000" 
					></textarea>
			   </td>
		    </c:if>
	    </tr>
	
	   <c:if test="${!isAddZY}">
		 <tr>
			<td class="label">
				分数<font color='red'> * </font>
			</td>
			<td>
				<input type="text" class="text" name="nm_addcol_proportion" id="id_addcol_proportion"
					onfocus="javascript:proportionValidate1(this);" onblur="javascript:proportionValidate2(this);"
					/>（分）
				<div id="proportionDiv" class="ui-state-highlight ui-corner-all" style="width:150px;display:none">
				  <span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				  <div>请输入数字</div>
				</div>		
			</td>
			
			<td class="label">
				备注
			</td>
			<td class="content">
				<textarea name="nm_addcol_note" id="id_addcol_note" class="text medium"  style="width: 95%; height: 80px;" 
					alt="allowBlank:true,vtext:'备注 不能超出1000个汉字！',maxLength:1000"
					></textarea>
			</td>
		 </tr>		
	  </c:if>	
	</table>
	<div id="buttonGroup">
		<input type="submit" id="saveAll" value="保存" class="btn" />
		<input type="reset" id="resetAll" value="重置" class="btn" />
	</div>
    <div id="loadIndicator" class="loading-indicator" style="display:none">保存中，请等待...</div>
</form>
<%@ include file="/common/footer_eoms.jsp"%>