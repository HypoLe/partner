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

<table id="applicationDtlTable" name="applicationDtlTable" class="formTable">	
	      <tr><td colspan="8"><font style="color:red;font-weight:bold;">明细信息</font></td></tr>
	      <tr>	    
			<td class="label">
				专业<font color='red'> * </font>
			</td>
			<td>		   
				<eoms:comboBox name="specialty" id="specialty" 
					initDicId="11225" alt="allowBlank:false,vtext:'专业必选！'" 
				/>
			</td>	     
			
			<td class="label">代维公司<font color="red">*</font> </td>
			<td class="content">
				<input type="text" id="compName" name="compName" class="text" readonly="true" />
				<input type="hidden" id="comp" name="comp" />
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
					beanId="selectXML" cls="select" alt="allowBlank:false,vtext:'年月必选！'" />
			</td>
			<td class="label">月<font color="red">*</font> ：</td>
			<td>
				<eoms:dict key="dict-partner-inspect" selectId="month" dictId="monthflag" 
					beanId="selectXML" cls="select"  alt="allowBlank:false,vtext:'年月必选！'"/>
			</td>				
	    </tr>
	    
	    <tr>	       		
			<td class="label">
				费用类型<font color="red">*</font>  
			</td>
			<td>
				<select id="costType" name="costType" class="text">
				    <option value="">请选择</option>
					<option value="1">奖励</option>
					<option value="2">补贴</option>
					<option value="3">报销</option>
					<option value="4">其他</option>
				</select>
			</td>	   
				     
			<td class="label">
				金额<font color="red">*</font> 
			</td>
			<td>
				 <input type="text" class="text" name="mnyAmt" id="mnyAmt"
					alt="allowBlank:false,vtext:'请输入数字'"/>元				
			</td>		      
			  </tr>
	    
	    <tr>	       		
			<td class="label">款项依据<font color="red">*</font> </td>
	        <td class="content" colspan="3">
			   <textarea name="costVoucher" id="costVoucher" class="text medium"  style="width: 300px; height: 80px;" 
	             alt="allowBlank:false,vtext:'必填，但不能超出500个汉字！',maxLength:1000" ></textarea>
	        </td>
	    </tr>    	
	    
	    <tr>
	       <td colSpan="8">
	         <input type="button" id="add" name="add" value="新 增" class="btn"  style="width:60px;" onclick="javascript:addApplicationDtl();"/>   
	       </td>  
	    </tr>		
</table><br/>	
	
<c:if test="${feeHaveSubmited}">
<span style="font-weight:bold;font-size:24;color:#FF0000;">
红色边框的明细不能申请，因为其“专业-代维机构”在当前日期所在年月的费用数据已填报!
</span> 
</c:if>	
<form id="feeApplicationForm" name="feeApplicationForm" 
	action="${app}/partner/feeManage/feeApplication.do?method=saveFeeApplication"  method="post">	
   <input type="hidden" name="feeApplicationId" 
      <logic:empty  name="feeApplication" scope="request">value="${feeApplicationId }"</logic:empty>
	  <logic:notEmpty name="feeApplication" scope="request">value="${feeApplication.id }"</logic:notEmpty>
   />
		
   <table   class="table protocolMainList"  name="applicationDtlListTable" id="applicationDtlListTable">
     <thead>
	   <tr>
	      <th > 专业 </th>
	      <th > 代维机构 </th>	      
	      <th width="50">  年 </th>
	      <th width="50">  月 </th>
	      <th > 费用类型</th>
	      <th width="80"> 金额 </th>
	      <th > 款项依据 </th>
	      <th width="100"> 操作 </th>
	   </tr>
     </thead>
     <tbody name="applicationDtlListBody" id="applicationDtlListBody">
       <tr hidden="true" name="cloneSrcRow" id="cloneSrcRow" style='display:none;'>
          <td > 专业<input type="hidden" /> </td>
	      <td > 代维机构<input type="hidden" /> </td>	      
	      <td width="50"> <input type="text" style="width:50;border:none;background:none;" value="2012"/>  </td>
	      <td width="50"> <input type="text" style="width:50;border:none;background:none;" value="12"/>  </td>
	      <td > 费用类型<input type="hidden" /></td>
	      <td width="80"> <input type="text" style="width:80;border:none;background:none;"/> </td>
	      <td > 
	        <textarea  class="text medium" ></textarea>
	      </td>
	      <td  width="100">
	         <a  href="javascript:void(0);" onClick="javascript:editRow(this);">修改</a>|<a href="javascript:void(0);" onClick="javascript:delRow(this);">删除</a>
	      </td>
       </tr>    
       <!--没有经过数据库保存 -->
       <logic:empty  name="feeApplication" scope="request">
          <c:forEach items="${oldList}" var="rowMap" varStatus="status">
            <c:if test="${rowMap.feeHaveSubmited}"><tr bordercolor="#FF0000"></c:if>
            <c:if test="${not rowMap.feeHaveSubmited}"><tr></c:if> 
          		<td><input type="hidden" name="majorval" value="${rowMap.majorval}" readonly=""><eoms:id2nameDB id="${rowMap.majorval}" beanId="ItawSystemDictTypeDao" /></td>
	      		<td><input type="hidden" name="compval" value="${rowMap.compval}" readonly=""><eoms:id2nameDB id='${rowMap.compval}' beanId='partnerDeptDao'/></td>	      
	      		<td width="50"> <input type="text" value="${rowMap.yearval}" style="width:50;border:none;background:none;" name="yearval" readonly="">  </td>
	     		<td width="50"> <input type="text" value="${rowMap.monthval}" style="width:50;border:none;background:none;" name="monthval" readonly="">  </td>
	      		<td><input type="hidden" name="costtypeval" value="${rowMap.costtypeval}" readonly>
					<c:if test="${rowMap.costtypeval eq '1'}">奖励</c:if>
					<c:if test="${rowMap.costtypeval eq '2'}">补贴 </c:if>
					<c:if test="${rowMap.costtypeval eq '3'}">报销</c:if>
					<c:if test="${rowMap.costtypeval eq '4'}">其他</c:if> 
                </td>
	      		<td width="80"> <input type="text" style="width:80;border:none;background:none;" name="mnyamtval" readonly value="${rowMap.mnyamtval}"> </td>
	      		<td> 
	       		 <textarea class="text medium" name="costvoucherval" readonly value="${rowMap.costvoucherval}">${rowMap.costvoucherval}</textarea>
	      		</td>
	      		<td width="100">
	       		   <a  href="javascript:void(0);" onClick="javascript:editRow(this);">修改</a>|<a href="javascript:void(0);" onClick="javascript:delRow(this);">删除</a>
	      		</td>
       		</tr>
          </c:forEach>
       </logic:empty>
       <!-- 经过数据库 -->
       <logic:notEmpty  name="feeApplication" scope="request">
         <c:forEach items="${feeApplicationDtlList}" var="feeApplicationDtl" varStatus="status">            
           <tr> 
          		<td><input type="hidden" name="majorval" value="${feeApplicationDtl.majorid}" readonly><eoms:id2nameDB id="${feeApplicationDtl.majorid}" beanId="ItawSystemDictTypeDao" /></td>
	      		<td><input type="hidden" name="compval" value="${feeApplicationDtl.compid}" readonly><eoms:id2nameDB id='${feeApplicationDtl.compid}' beanId='partnerDeptDao'/></td>	      
	      		<td width="50"> <input type="text" value="${feeApplicationDtl.year}" style="width:50;border:none;background:none;" name="yearval" readonly>  </td>
	     		<td width="50"> <input type="text" value="${feeApplicationDtl.month}" style="width:50;border:none;background:none;" name="monthval" readonly>  </td>
	      		<td><input type="hidden" name="costtypeval" value="${feeApplicationDtl.costTypeCd}" readonly>
					<c:if test="${feeApplicationDtl.costTypeCd eq '1'}">奖励</c:if>
					<c:if test="${feeApplicationDtl.costTypeCd eq '2'}">补贴</c:if>
					<c:if test="${feeApplicationDtl.costTypeCd eq '3'}">报销</c:if>
					<c:if test="${feeApplicationDtl.costTypeCd eq '4'}">其他</c:if> 
                </td>
	      		<td width="80"> <input type="text" style="width:80;border:none;background:none;" name="mnyamtval" readonly="" value="${feeApplicationDtl.mnyAmt}"> </td>
	      		<td> 
	       		 <textarea class="text medium" name="costvoucherval" readonly="" value="${feeApplicationDtl.costVoucher}">${feeApplicationDtl.costVoucher}</textarea>
	      		</td>
	      		<td width="100">
	       		   <a  href="javascript:void(0);" onClick="javascript:editRow(this);">修改</a>|<a href="javascript:void(0);" onClick="javascript:delRow(this);">删除</a>
	      		</td>
       		</tr>
          </c:forEach>
       </logic:notEmpty>      
     </tbody>
   </table>
   
   <table id="sheet" class="formTable">
		<tr>
		    <td class="label">
				审批人
			</td>
			<td class="content">
			   <input type="text" id="excutor" name="excutor" class="text" readOnly="true" 
			       <logic:empty  name="feeApplication" scope="request">value='<eoms:id2nameDB id="${nextExecutor }" beanId="tawSystemUserDao" />'</logic:empty>
				   <logic:notEmpty name="feeApplication" scope="request">value='<eoms:id2nameDB id="${feeApplication.nextExecutor }" beanId="tawSystemUserDao" />'</logic:notEmpty>
			       alt="allowBlank:false,vtext:'必须指定下级审批人'"	
			   />
			   <input type="hidden" id="excutorId" name="excutorId" class="text"  onChange="excutorValidate(this);"
                   <logic:empty  name="feeApplication" scope="request">value="${nextExecutor }"</logic:empty>
				   <logic:notEmpty name="feeApplication" scope="request">value="${feeApplication.nextExecutor }"</logic:notEmpty>
               />	
			   <eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=userFromDept"
	            rootId="1" rootText='联通公司人员' valueField="excutorId"
	            handler="excutor" textField="excutor" checktype="user" single="true">
               </eoms:xbox>
			</td>		  		
			<td class="label">
				备注
			</td>
			<td class="content">
				<textarea name="remark" id="remark" class="text medium" 
					alt="allowBlank:true,vtext:'备注不能超出500个汉字！',maxLength:500" style="width: 300px; height: 80px;" 
					<logic:empty  name="feeApplication" scope="request">value="${remark }"</logic:empty>
				    <logic:notEmpty name="feeApplication" scope="request">value="${feeApplication.remark }"</logic:notEmpty>
				 ><logic:empty  name="feeApplication" scope="request">${remark }</logic:empty><logic:notEmpty name="feeApplication" scope="request">${feeApplication.remark }</logic:notEmpty></textarea>
			</td>
		</tr>	
	</table>
	
	<input type="submit" class="btn" value="保 存" onClick="beforeSave();"/>&nbsp;&nbsp;&nbsp;
	<input type="submit" class="btn" value="提 交" onClick="beforeSubmit();"/>
</form>

<script type="text/javascript">
   var jq=jQuery.noConflict();
     
   function beforeSave(){
     var feeApplicationForm=document.getElementById('feeApplicationForm'); 
     
     var saveOrSubmit=null;
     try { // IE6/IE7 构建方式
	     saveOrSubmit = document.createElement('<input name="saveOrSubmit" />');   
     } catch (e) {// W3C 构建方式
	     saveOrSubmit = document.createElement('input');
	     saveOrSubmit.name = "saveOrSubmit";
     }   
     saveOrSubmit.setAttribute("type","hidden");    
     feeApplicationForm.appendChild(saveOrSubmit);    
     saveOrSubmit.value='save';
     
    //feeApplicationForm.submit(); 由此项，v = new eoms.form.Validation({form:'feeApplicationForm'});不起作用
   }  
   function beforeSubmit(){
     var feeApplicationForm=document.getElementById('feeApplicationForm'); 
     
     var saveOrSubmit=null;
     try { // IE6/IE7 构建方式
	     saveOrSubmit = document.createElement('<input name="saveOrSubmit" />');   
     } catch (e) {// W3C 构建方式
	     saveOrSubmit = document.createElement('input');
	     saveOrSubmit.name = "saveOrSubmit";
     }   
     saveOrSubmit.setAttribute("type","hidden");    
     feeApplicationForm.appendChild(saveOrSubmit);    
     saveOrSubmit.value='submit'; 
     
    //feeApplicationForm.submit(); 由此项，v = new eoms.form.Validation({form:'feeApplicationForm'});不起作用
   } 
   
   function excutorValidate(ths){
     var v1=ths.value;
     var v2="${sessionScope.sessionform.userid}";
     if(v1==v2){
       alert("审批人不能是自己！");
       return false;
     }
   }
   
    function delRow(ths){
     var thsTbody=ths.parentNode.parentNode.parentNode;
     var thsTr=ths.parentNode.parentNode;
     //thsTbody.removeNode(thsTr);
     thsTbody.removeChild(thsTr);
  }
     
    function editRow(ths){
     var thsTr=ths.parentNode.parentNode;
     thsTr.id="themodifyrow";//在feeApplicationDtlEdit.jsp修改后，将会去掉此id属性 
     var tempForm=document.createElement("form");
     tempForm.action="${app}/partner/feeManage/feeApplication.do?method=goToFeeApplicationDtlEdit"; 
     tempForm.method="post";//不指定默认以get方式传递参数
     tempForm.target="modify";
     tempForm.style.display="none";
     /*var majorval=document.createElement("input");
     try {
	      // IE6/IE7 构建方式
	     majorval = document.createElement('<input name="majorval" />');  
     } catch (e) {
	      // W3C 构建方式
	     majorval = document.createElement('input');
	     majorval.name = "majorval";
     }   
     majorval.setAttribute("type","hidden");     
     tempForm.appendChild(majorval);    
     majorval.value=thsTr.getElementByName('majorval')[0].value;
     
     var compval=document.createElement("compval");
     var yearval=document.createElement("input");
     var monthval=document.createElement("input");
     var costtypeval=document.createElement("input");
     var mnyamtval=document.createElement("input");
     var costvoucherval=document.createElement("input");
     */
     
     var tempTable=document.createElement("table");
     //tempTable.appendChild(thsTr);//直接将导致thsTr被移走
     //alert("before:"+thsTr.getElementsByName("costvoucherval")[0].value) ;  js中的方法不是所有对象都有的，例如getElementsByName只有document才有
     //alert("before:"+thsTr.getElementsByTagName("textarea")[0].value) ;
     var tempTr=thsTr.cloneNode(true);
     tempTable.appendChild(tempTr); 
     //alert("after:"+tempTr.getElementsByTagName("textarea")[0].value);
     //clone不会clone textarea的数据的
     tempTr.getElementsByTagName("textarea")[0].value=thsTr.getElementsByTagName("textarea")[0].value;
     //alert("after,after:"+tempTr.getElementsByTagName("textarea")[0].value);
     tempForm.appendChild(tempTable);
     document.body.appendChild(tempForm);  
     tempForm.submit();
  }  
     
   Ext.onReady(function(){     
      var navg=getOs();
      /*if(navg=='MSIE'){ 
         var cloneSrcRow=document.getElementById("cloneSrcRow"); 
         //cloneSrcRow.setAttribute('style','display:none;');       
         var attr=document.createAttribute('style'); 
         cloneSrcRow.setAttributeNode(attr);
         cloneSrcRow.setAttribute('style','display:none;');  //直接在html代码处都加上属性hidden,style
         alert('MSIE'); 
      }*/
      if(navg=='MSIE'){
          var cloneSrcRow=document.getElementById("cloneSrcRow"); 
          cloneSrcRow.removeAttribute('hidden');
      }
      else{//'Firefox'
          var cloneSrcRow=document.getElementById("cloneSrcRow"); 
          cloneSrcRow.removeAttribute('style');
      } 
      
      var v = new eoms.form.Validation({form:'feeApplicationForm'});
       v.custom = function(){  	
            if(jq('input[name="compval"]').length==0){
               alert("一条明细也没有");
               return false;
            }
            return true;
	   };  
   });

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
