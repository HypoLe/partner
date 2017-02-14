<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
//common golbal variant 
</script>
<script type="text/javascript">
  //没有如下代码,将报错 ext.js:TypeError: $(this.showChkFldId).update is not a function
  var jq=jQuery.noConflict();
  Ext.onReady(function(){
      pwindow=window.parent;
      pdocument=pwindow.document;
      jq('#year').val(pdocument.getElementById('year').value);
      jq('#month').val(pdocument.getElementById('month').value);
      jq('#comp').val(pdocument.getElementById('comp').value);
      jq('#area').val(pdocument.getElementById('area').value);
      jq('#mainentityid').val(pdocument.getElementById('mainentityid').value);
  
      var v = new eoms.form.Validation({form:'feeEntityForm'});
      v.custom = function(){  
          if(!validateForm()){
              return false;
          }            
          return true;
	 };  	   
      
  });
  
   function cntValidate(Input){     
     var cnt=Input.value;
     var cntReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
     if(cnt!=null&&cnt.match(cntReg) && ""!=cnt){  			
   			return true;
      }else{         	
           	return false;
      }            
  }
  
  function validateForm(){
     /*   ++++验证数量是否有没填写的++++++     */
      var prcdtltbody=document.getElementById("prcdtltbody");
      var cntArr=Ext.query("input[name='cnt']",prcdtltbody) ;//单价输入框 
      
      for(var i=0;i<cntArr.length;i++){
        if(cntValidate(cntArr[i])){
        }else{
          alert("第"+(i+1)+"数量格式不对");
          return false;
        }
      }
     
     /********验证其他必填项是否填写 和 填写格式正确与否*********/ 
      if(cntValidate(document.getElementById("evaluscore"))){
      }else{
        alert("考核得分填写不正确");
        return false;
      }
      
      if(cntValidate(document.getElementById("koukuan"))){
      }else{
        alert("扣款填写不正确");
        return false;
      }
      
      return true;
  }
  
  function rtn2initate(){
     document.forms['rtninitate'].submit();
  }
  
   //注意虽然函数以cntXXX命名，但是其不止用于验证数量
   function cntValidate1(ths,flag){
     
     //var td=ths.parentNode;//处理 onfocus="javascript:prcValidate1(this,1);"   
     // 在input.onfocus=prcValidate1;情形下，ths是函数的第一个参数 即事件focus.
     var td;
     if(flag==1||flag==2){//处理 onfocus="javascript:prcValidate1(this,1);"的情形， 传递this（元素自身 ）作为ths
       td=ths.parentNode;
     }else{
       td=this.parentNode; //处理input.onfocus=prcValidate1这种情形,this为函数prcValidate1作用的对象（标签元素）
     }
     
     var cntDiv=Ext.get(td.childNodes[1]); //div
     cntDiv.show();
  }
  
  //注意虽然函数以cntXXX命名，但是其不止用于验证数量 
  function cntValidate2(ths,flag){ //flag为null（即不传递数据时）：对应xxxObj.yyyEvent=functionName ; this为functionName所左右的对象。 传递flag值时，传递this（元素自身 ）作为ths。
     //var td=ths.parentNode; 
     var td;
     if(flag==1||flag==2){//处理 onfocus="javascript:prcValidate1(this,1);"  传递this（元素自身 ）作为ths
       td=ths.parentNode;
     }else{
       td=this.parentNode; //处理input.onfocus=prcValidate1  ,this为函数prcValidate1作用的对象（标签元素）
     }
     var cntDiv=Ext.get(td.childNodes[1]);
     
     //var prc=ths.value;
     var cnt;
     if(flag==1||flag==2){//处理 onfocus="javascript:prcValidate2(this,1or2);"  传递this（元素自身 ）作为ths
       ths.setAttribute('value',ths.value); // FF获取innerHTML中的内容,需这么做 
       cnt=ths.value;
     }else{
       this.setAttribute('value',this.value); // FF获取innerHTML中的内容,需这么做 
       cnt=this.value; // 
     }
     var cntReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
     if(cnt.match(cntReg) && ""!=cnt){  
            cntDiv.dom.innerHTML="请输入数字 例：100.00";			
   			cntDiv.hide();
   			setTotalMny();
   			//flag==2不是在操作数量 ,是在操作其他输入，如：扣款	
   			//为了实时得到 应付款，不管操作 数量 还是 扣款，都更新应付款	
   			if(jq("#koukuan").val()==null){
   			  jq("#shifukuan").val(jq("#yingfukuan").val());
   			}else{
   			  jq("#shifukuan").val( jq("#yingfukuan").val()-jq("#koukuan").val() );
   			}
   				
   			return true;
      }else{
           	cntDiv.dom.innerHTML="输入不合法,请输入数字 例：100.0";
           	cntDiv.setStyle("backgroundColor","#FF0000");//Ext的
           	cntDiv.show();
           	return false;
      }            
  } 
  
  function setTotalMny(){
      var prcarr=jq("input[name='prc']"); 
      var cntarr=jq("input[name='cnt']");  
      
      //alert(prcarr.size());
      //alert(prcarr.length);
      
      var len=prcarr.size(); 
      
      var totalmny=0;
      for(var i=0;i<len;i++){
        var cnt=0;
        try{
          cnt=cntarr[i].value;
        }catch(e){
          cnt=0;
        }finally{  
        }
        totalmny += (prcarr[i].value * cnt);
      }
      
      jq("#totalmny").val(parseFloat(totalmny).toFixed(3));
      jq("#yingfukuan").val(parseFloat(totalmny).toFixed(3));
      
  }
  
  function F_reSelectModel(ths){
      document.forms['reselectmodel'].submit();
  }
</script>	

<body>
    <form action="${app}/partner/feeManage/evaluFee.do?method=updateFeeEntity" method="post" id="feeEntityForm" name="feeEntityForm">
      <input type="hidden" name="prctmplid"  value="${monthEvaluEntity.ownfeeprctmplid}">   
      <input type="hidden" name="ownfeeprctmp" value="${monthEvaluEntity.ownfeeprctmp}">
      
      <!-- 其值在页面onload后，通过js设置，见Ext.onReady() -->
      <input type="hidden" id="year" name="year" />
      <input type="hidden" id="month" name="month"/>
      <input type="hidden" id="comp" name="comp" />
      <input type="hidden" id="area" name="area" />
      <input type="hidden" id="major" name="major" value="${monthEvaluEntity.majorid}"/>
      <!--考核费用实例id-->
      <input type="hidden" id="evalu_entityid" name="evalu_entityid" value="${monthEvaluEntity.id}"/>
      <input type="hidden" id="mainentityid" name="mainentityid" value="${monthEvaluEntity.ownmainentityid}"/>
      
      <fieldset>
        <legend>
          <span style="color:red;font-size:15pt">实例已存在，可做修改</span>
        </legend> 
        <p style="color:red">修改分为两种形式:</p> 
        <p style="color:red">1、在当前模板做修改</p>
        <p style="color:red">2、重选模板，重新生成费用</p>
      </fieldset>
      
      <fieldset>
        <legend>计量详情-模板:${monthEvaluEntity.ownfeeprctmp}</legend>
        <c:set var="cnt" value="1"/>    
	    <table id="prcdtltbl" name="prcdtltbl" class="table protocolMainList">
	     <thead>
	       <tr> 
	           <th>序号</th>
	           <th>专业</th>
	         <c:forEach items="${prcFilterList}"  var="prcFilter" varStatus="status">
	           <th>
	            ${prcFilter.text}
               </th>
	         </c:forEach>
	          <th>单价</th>
	          <th>数量<font color="red">*</font></th>
	       </tr>	       
	     </thead>
	     <tbody id="prcdtltbody" name="prcdtltbody">	   
	          <c:forEach items="${dtlentityList}" var="dtlentity" varStatus="status">
	            <tr>
	              <td name="num">${status.count}<input type="hidden" name="dtlprctmplid" value="${dtlentity.owndtlprctmplid}"/></td> 
	              <td><eoms:id2nameDB id='${dtlentity.majorid}' beanId='ItawSystemDictTypeDao' /></td> 
	              <c:forEach items="${prcFilterList}" var="prcFilter">
	                   <td>
	                     <!-- 属性值来源于字典 -->
	                     <c:set var="dictname" value="${prcFilter.businessName}dict"/>
	                     <c:if test="${prcFilter.dictYN}">	                       	                      
	                       <eoms:id2nameDB id="${dtlentity[prcFilter['businessName']]}" beanId="ItawSystemDictTypeDao" />
	                       <input name="hd_${prcFilter.businessName}" type="hidden" value="${dtlentity[prcFilter['businessName']]}"/>  
	                       <input name="hd_${prcFilter.businessName}_sourceColName" type="hidden" value=""/> 
	                       <input name="hd_${prcFilter.businessName}_sourceTableName" type="hidden" value=""/> 
	                       <input name="hd_${prcFilter.businessName}_dict" type="hidden" value='${dtlentity[dictname]}'/>                     
	                     </c:if>
	                     <!-- 属性值来源于数据表 -->
	                     <c:set var="tablename" value="${prcFilter.businessName}table"/>
	                     <c:set var="colname" value="${prcFilter.businessName}col"/>
	                     <c:if test="${prcFilter.dictYN==false}">
	                       ${dtlentity[prcFilter['businessName']]}		                      
	                       <input name="hd_${prcFilter.businessName}" type="hidden" value="${dtlentity[prcFilter['businessName']]}"/>                     
	                       <input name="hd_${prcFilter.businessName}_sourceColName" type="hidden" value='${dtlentity[colname]}'/> 
	                       <input name="hd_${prcFilter.businessName}_sourceTableName" type="hidden" value='${dtlentity[tablename]}'/> 
	                       <input name="hd_${prcFilter.businessName}_dict" type="hidden" value=""/>      
	                     </c:if>
	                   </td> 
	                </c:forEach>
	                <td>${dtlentity.dtlprc}
	                   <input name="prc" type="hidden" value="${dtlentity.dtlprc}" />
	                </td>
	                <td><input name="cnt" type="text"  value="${dtlentity.dtlaamt}" onfocus="javascript:cntValidate1(this,1);" onChange="javascript:cntValidate2(this,1);" class="text"/><div class="ui-state-highlight ui-corner-all" style="width:150;display:none">请输入数字,如100.0,没有就为0</div>
	                </td> 
	            </tr>
	          </c:forEach>  
	          <tr>
	            <td colspan="${(fn:length(prcFilterList)) + 2 + 2 - 2 }"></td>
	            <td>总计</td>
	            <td><input id="totalmny" name="totalmny" readOnly="true" class="text" value="${amtTotalDouble}"/></td>
	          </tr>
	     </tbody>
	  </table>   
      </fieldset>
      
      <br/>   
    <!-- 显示考核费用需填写的信息 -->
      <TABLE class="formTable">
        <tr>	    
            <td class="label">考核得分<font color="red">*</font></td>
            <td class="content"><input id="evaluscore" name="evaluscore" type="text" value="${monthEvaluEntity.evalscore }" onfocus="javascript:cntValidate1(this,2);" onChange="javascript:cntValidate2(this,1);" class="text"/><div class="ui-state-highlight ui-corner-all" style="width:150;display:none">请输入数字,如100.0,没有就为0</div></td>  
			   
			<td class="label"> 扣款（元）<font color="red">*</font> </td>   
			<td class="content"><input id="koukuan" name="koukuan" type="text"  value="${monthEvaluEntity.reducemnyamt }" onfocus="javascript:cntValidate1(this,2);" onChange="javascript:cntValidate2(this,1);" class="text"/><div class="ui-state-highlight ui-corner-all" style="width:150;display:none">请输入数字,如100.0,没有就为0</div></td>			 		  	 			
	    </tr>   
	    <tr>	    
            <td class="label">应付款（元）</td>
			<td class="content">	
			   <input type="text" id="yingfukuan" name="yingfukuan" class="text" value="${monthEvaluEntity.shdmnyamt }" readOnly="true" />	
			</td>   
			   
			<td class="label">实付款（元）</td>
			<td class="content">
			  <input type="text" id="shifukuan" name="shifukuan" class="text" value="${monthEvaluEntity.realmnyamt }" readOnly="true" />
            </td>	 		  	 			
	    </tr>  
	  <!--  <tr>	    
            <td class="label">费用数据确认人<font color="red">*</font></td>
			<td class="content">	
			   <input type="text" id="nextstepexecutor" name="nextstepexecutor" class="text" readOnly="true" value='<eoms:id2nameDB id="kf10086" beanId="tawSystemUserDao" />' />
			   <input type="hidden" id="nextstepexecutorid" name="nextstepexecutorid" class="text"  value="kf10086"/>	
			   <eoms:xbox id="userTree" dataUrl="${app}/xtree.do?method=userFromDept"
	            rootId="1" rootText='联通公司人员' valueField="nextstepexecutorid"
	            handler="nextstepexecutor" textField="nextstepexecutor" checktype="user" single="true">
               </eoms:xbox>
			</td>   
			   				 		  	 			
	    </tr>   --> 
	    <tr>
	      <td class="label">备注</td>
	      <td colspan="3">
	        <TEXTAREA cols="100" name="remark" value="${monthEvaluEntity.remark }">${monthEvaluEntity.remark }</TEXTAREA>
	      </td>
	    </tr> 
      </TABLE>
      
      
      <br/>      
      <input type="submit" value="提交"></input>
      <input type="button" value="重选模板" onClick="javascript:F_reSelectModel(this);"></input>          
    </form>   

    <!--不显示，供重选模板时传递数据 -->
    <form action="${app}/partner/feeManage/evaluFee.do?method=initateFeeEntity&operType=reselectmodel" method="post" name="reselectmodel" style="display:none">
		 <input type="hidden" id="specialty" name="specialty" value="${monthEvaluEntity.majorid}"/>		
		 <input type="hidden"  name="mainentityid" value="${monthEvaluEntity.ownmainentityid}"/>				 	
    </form>
<%@ include file="/common/footer_eoms.jsp"%>