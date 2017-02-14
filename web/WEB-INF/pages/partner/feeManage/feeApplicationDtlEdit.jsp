<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" 
 import="java.util.*,com.boco.eoms.partner.feeManage.util.*" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript">
 function getOs() 
   { 
    //var OsObject = "";  
    if(navigator.userAgent.indexOf("MSIE")>0) { 
         return "MSIE"; 
    } 
    if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){ 
         return "Firefox"; 
    } 
    if(isSafari=navigator.userAgent.indexOf("Safari")>0) { 
         return "Safari"; 
    }  
    if(isCamino=navigator.userAgent.indexOf("Camino")>0){ 
         return "Camino"; 
    } 
    if(isMozilla=navigator.userAgent.indexOf("Gecko")>0){ 
         return "Gecko"; 
    }    
  } 
</script>


<form id="feeApplicationForm" name="feeApplicationForm" 
	action="${app}/partner/feeManage/feeApplication.do?method=saveFeeApplication"  method="post">		
<table id="applicationDtlTable" name="applicationDtlTable" class="formTable">	
	      <tr><td colspan="8"><font style="color:red;font-weight:bold;">修改明细</font></td></tr>
	      
	      <tr>	    
			<td class="label">
				专业<font color='red'> * </font>
			</td>
			<td>		   
				<eoms:comboBox name="specialty" id="specialty" 
					initDicId="11225" alt="allowBlank:false,vtext:'专业必选！'" defaultValue='<%=request.getParameter("majorval") %>'
				/>
			</td>	     			
			<td class="label">代维公司<font color="red">*</font> </td>
			<td class="content">
			    <input type="text" id="compName" name="compName" class="text" readonly="true" 
				 value="<eoms:id2nameDB id='<%=request.getParameter("compval") %>' beanId='partnerDeptDao'/>"
				 />  
				<input type="hidden" id="comp" name="comp" value='<%=request.getParameter("compval") %>'/>
				<eoms:xbox id="compTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true&showPartnerLevelType=2"
					rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="comp"
					handler="compName" textField="compName" checktype="dept"
					single="true">
				</eoms:xbox>
			</td>   	   							
	    </tr>
	       
	    <tr>
	        <td class="label">年<font color="red">*</font> ：</td>
			<td>
				<eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
					beanId="selectXML" cls="select" alt="allowBlank:false,vtext:'年月必选！'" 
					defaultId='<%=request.getParameter("yearval") %>'/>
			</td>
			<td class="label">月<font color="red">*</font> ：</td>
			<td>
				<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
					beanId="selectXML" cls="select"  alt="allowBlank:false,vtext:'年月必选！'"
					defaultId='<%=request.getParameter("monthval") %>'/>
			</td>	
	    </tr>
	    
	    <tr>	       		
			<td class="label">
				费用类型<font color="red">*</font>  
			</td>
			<td>
				<select id="costType" name="costType" class="text" value='<%=request.getParameter("costtypeval") %>'>
				    <option value="">请选择</option>
					<option value="1" <c:if test='<%="1".equals(request.getParameter("costtypeval")) %>'>selected</c:if>>奖励</option>
					<option value="2" <c:if test='<%="2".equals(request.getParameter("costtypeval")) %>'>selected</c:if>>补贴</option>
					<option value="3" <c:if test='<%="3".equals(request.getParameter("costtypeval")) %>'>selected</c:if>>报销</option>
					<option value="4" <c:if test='<%="4".equals(request.getParameter("costtypeval")) %>'>selected</c:if>>其他</option>
				</select>
			</td>	   			     
			<td class="label">
				金额<font color="red">*</font> 
			</td>
			<td>
				 <input type="text" class="text" name="mnyAmt" id="mnyAmt"
					alt="allowBlank:false,vtext:'请输入数字'" value='<%=request.getParameter("mnyamtval") %>'/>元				
			</td>		      			
	    </tr>    
	    
	    <tr>
	        <td class="label">款项依据<font color="red">*</font> </td>
	        <td class="content" colspan="3">
			   <textarea name="costVoucher" id="costVoucher" class="text medium" style="width: 300px; height: 80px;" 
	             alt="allowBlank:false,vtext:'必填，但不能超出500个汉字！',maxLength:1000" value='<%=request.getParameter("costvoucherval") %>'><%=request.getParameter("costvoucherval") %></textarea>
	        </td>
	    </tr>	
</table>

 <input type="button" id="ok" name="ok" value="修改" class="btn"  style="width:60px;" onclick="javascript:okBT();"/>   
 <input type="button" id="rtn" name="rtn" value="返回" class="btn"  style="width:60px;" onclick="javascript:window.close();"/>
</form>

<script type="text/javascript">
   var jq=jQuery.noConflict();
        
   Ext.onReady(function(){     
          
   });
   
  function okBT(){
     var theOpener=window.opener;
     var themodifyrow=theOpener.document.getElementById('themodifyrow'); 
     
     var specialty=jq('#specialty').val();
     if(specialty==''){
       alert('专业必选');
       return false;
     }
     var specialtyNm=getSelectText('specialty'); 
     var comp=jq('#comp').val();
     if(comp==''){
       alert('代维公司必选');
       return false;
     }
     var compName=jq('#compName').val();
     var year=jq('#year').val();     
     var month=jq('#month').val();
     if(year==''||month==''){
       alert('年月必选');
       return false;
     }
     var costType=jq('#costType').val();
     if(costType==''){
       alert('费用类型必选');
       return false;
     }
     var costTypeNm=getSelectText('costType');    
     var mnyAmt=jq('#mnyAmt').val();
     if(!validateNumber(mnyAmt)){
        alert('输入格式不对，请输入数字');
        return false;
     }
     var costVoucher=jq('#costVoucher').val();    
     if(costVoucher==''){
       alert('款项依据必填');
       return false;
     }
     
     var tds=themodifyrow.getElementsByTagName("td");     
     //tds[0]专业
     jq(tds[0]).contents().filter(function(){return this.nodeType == 3;}).remove();
     jq(tds[0]).append(specialtyNm);  
     //tds[0].getElementsByTagName("INPUT")[0].id=''; 
     tds[0].getElementsByTagName("INPUT")[0].name='majorval';
     tds[0].getElementsByTagName("INPUT")[0].value = specialty;
     tds[0].getElementsByTagName("INPUT")[0].readOnly = true;   
     //tds[1]代维机构
     jq(tds[1]).contents().filter(function(){return this.nodeType == 3;}).remove();
     jq(tds[1]).append(compName);  
     tds[1].getElementsByTagName("INPUT")[0].name='compval';
     tds[1].getElementsByTagName("INPUT")[0].value = comp;
     tds[1].getElementsByTagName("INPUT")[0].readOnly = true;  
     //tds[2]年
     tds[2].getElementsByTagName("INPUT")[0].name='yearval';
     tds[2].getElementsByTagName("INPUT")[0].value = year;
     tds[2].getElementsByTagName("INPUT")[0].readOnly = true; 
     //tds[3]月
     tds[3].getElementsByTagName("INPUT")[0].name='monthval';
     tds[3].getElementsByTagName("INPUT")[0].value = month;
     tds[3].getElementsByTagName("INPUT")[0].readOnly = true; 
     //tds[4]费用类型
     jq(tds[4]).contents().filter(function(){return this.nodeType == 3;}).remove();
     jq(tds[4]).append(costTypeNm);  
     tds[4].getElementsByTagName("INPUT")[0].name='costtypeval';
     tds[4].getElementsByTagName("INPUT")[0].value = costType;
     tds[4].getElementsByTagName("INPUT")[0].readOnly = true;  
     //tds[5]金额
     tds[5].getElementsByTagName("INPUT")[0].name='mnyamtval';
     tds[5].getElementsByTagName("INPUT")[0].value = mnyAmt;
     tds[5].getElementsByTagName("INPUT")[0].readOnly = true; 
     //tds[6]款项依据
     tds[6].getElementsByTagName("textarea")[0].name='costvoucherval';
     //alert(costVoucher+":1");
     tds[6].getElementsByTagName("textarea")[0].value = costVoucher;
     //alert(tds[6].getElementsByTagName("textarea")[0].value+":2");
     tds[6].getElementsByTagName("textarea")[0].readOnly = true;
     
     themodifyrow.removeAttribute('id');
     window.close();
  }

  function addApplicationDtl(){
     var specialty=jq('#specialty').val();
     if(specialty==''){
       alert('专业必选');
       return false;
     }
     var specialtyNm=getSelectText('specialty'); 
     var comp=jq('#comp').val();
     if(comp==''){
       alert('代维公司必选');
       return false;
     }
     var compName=jq('#compName').val();
     var year=jq('#year').val();     
     var month=jq('#month').val();
     if(year==''||month==''){
       alert('年月必选');
       return false;
     }
     var costType=jq('#costType').val();
     if(costType==''){
       alert('费用类型必选');
       return false;
     }
     var costTypeNm=getSelectText('costType');    
     var mnyAmt=jq('#mnyAmt').val();
     if(!validateNumber(mnyAmt)){
        alert('输入格式不对，请输入数字');
        return false;
     }
     var costVoucher=jq('#costVoucher').val();    
     if(costVoucher==''){
       alert('款项依据必填');
       return false;
     }
     
     var applicationDtlListBody=document.getElementById("applicationDtlListBody");
     var cloneSrcRow=document.getElementById("cloneSrcRow");
     var newTR = cloneSrcRow.cloneNode(true);
    
     newTR.removeAttribute('id');
     newTR.removeAttribute('name');
     var navg=getOs();
     if(navg=='MSIE'){           
          newTR.removeAttribute('style');
     }
     else{//'Firefox'        
          newTR.removeAttribute('hidden');
     }
     
     var tds=newTR.getElementsByTagName("td");    
     //tds[0]专业
     jq(tds[0]).contents().filter(function(){return this.nodeType == 3;}).remove();
     jq(tds[0]).append(specialtyNm);  
     //tds[0].getElementsByTagName("INPUT")[0].id=''; 
     tds[0].getElementsByTagName("INPUT")[0].name='majorval';
     tds[0].getElementsByTagName("INPUT")[0].value = specialty;
     tds[0].getElementsByTagName("INPUT")[0].readOnly = true;   
     //tds[1]代维机构
     jq(tds[1]).contents().filter(function(){return this.nodeType == 3;}).remove();
     jq(tds[1]).append(compName);  
     tds[1].getElementsByTagName("INPUT")[0].name='compval';
     tds[1].getElementsByTagName("INPUT")[0].value = comp;
     tds[1].getElementsByTagName("INPUT")[0].readOnly = true;  
     //tds[2]年
     tds[2].getElementsByTagName("INPUT")[0].name='yearval';
     tds[2].getElementsByTagName("INPUT")[0].value = year;
     tds[2].getElementsByTagName("INPUT")[0].readOnly = true; 
     //tds[3]月
     tds[3].getElementsByTagName("INPUT")[0].name='monthval';
     tds[3].getElementsByTagName("INPUT")[0].value = month;
     tds[3].getElementsByTagName("INPUT")[0].readOnly = true; 
     //tds[4]费用类型
     jq(tds[4]).contents().filter(function(){return this.nodeType == 3;}).remove();
     jq(tds[4]).append(costTypeNm);  
     tds[4].getElementsByTagName("INPUT")[0].name='costtypeval';
     tds[4].getElementsByTagName("INPUT")[0].value = costType;
     tds[4].getElementsByTagName("INPUT")[0].readOnly = true;  
     //tds[5]金额
     tds[5].getElementsByTagName("INPUT")[0].name='mnyamtval';
     tds[5].getElementsByTagName("INPUT")[0].value = mnyAmt;
     tds[5].getElementsByTagName("INPUT")[0].readOnly = true; 
     //tds[6]款项依据
     tds[6].getElementsByTagName("textarea")[0].name='costvoucherval';
     tds[6].getElementsByTagName("textarea")[0].value = costVoucher;
     tds[6].getElementsByTagName("textarea")[0].readOnly = true;
     //tds[7]操作   
     applicationDtlListBody.appendChild(newTR);
  } 
  
  function  getSelectText(selectId){
      var theSelect=jq('#'+selectId).get(0);
      var options=theSelect.options;
      
      for(var i=0;i<options.length;i++){
        if(theSelect.value==options[i].value){
          return options[i].text;
        }        
      }
      return "";
  }
  
  function validateNumber(theVar){ 
     Reg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
     if(theVar.match(Reg) && ""!=theVar){  			
   			return true;
      }else{         	
           	return false;
      }   
  }
</script>

<%@ include file="/common/footer_eoms.jsp"%>
