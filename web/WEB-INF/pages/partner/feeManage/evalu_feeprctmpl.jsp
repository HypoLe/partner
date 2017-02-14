<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.*"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
	.ui-progressbar-value { background-image: url(${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/images/pbar-ani.gif); }
</style>
<script type="text/javascript"> 
  var tmplNameExistYN=false; //全局变量
  var majorname="<eoms:id2nameDB id="${majorid}" beanId="ItawSystemDictTypeDao" />" ; 
</script>

<form action="${app}/partner/feeManage/evaluFee.do?method=saveOrUpdatePrcTmpl&opertyp=add" method="post" id="prctmplForm" name="prctmplForm">
    <table id="sheet" class="formTable">
        <tr>
	        <td class="label">
				模板名称<font color='red'> * </font>
			</td>
			<td>
				<input type="text" class="text" name="tmpl" id="id_tmpl" value="${prctmplnm}"
					alt="allowBlank:false,vtext:'名称不能超出50个汉字！',maxLength:100" 
					onchange="javascript:tmplNameExist();"
				 />
				 <div id="id_tmpldiv" class="ui-state-highlight ui-corner-all" style="width:150px;display:none">
				   <span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;"></span>
				   <div>模板已存在,请另命名</div>
			     </div>	
			</td>	    
			<td class="label">
				专业<font color='red'> * </font>
			</td>
			<td>
				<eoms:comboBox name="specialty" id="specialty" defaultValue="${majorid}"
					initDicId="11225" alt="allowBlank:false,vtext:'专业必选！'" 
					onchange="javascript:loadfeeprctmplwithdtl(this);"/>
			</td>	     	 			
	    </tr>
	    
	    <tr>	       		
			<td class="label">
				区域</font>
			</td>
			<td>
				<input type="text" class="text" name="areaName" id="areaName" readonly="true" 
				 value="<eoms:id2nameDB id='${areaid}' beanId='tawSystemAreaDao'/>"/>
				<input type="hidden" id="area" name="area" value="${areaid}"/>
			</td>	   
			<td class="label">代维公司</td>
			<td class="content">
				<input type="text" id="compName" name="compName" class="text" readonly="true" 
                  value="<eoms:id2nameDB id='${compid}' beanId='partnerDeptDao'/>" />
				<input type="hidden" id="comp" name="comp" value="${compid}"/>
			</td>   	  	
	    </tr>
	     <tr>	       		
			 <td class="label">备注</td>
	         <td class="content" colspan="3">
			   <textarea name="remark" id="id_remark" class="text medium" style="width:80%" value="${remark}"
	             alt="allowBlank:true,vtext:'备注不能超出500个汉字！',maxLength:1000" >${remark}</textarea>
	         </td>
	    </tr>
    </table>
    <br/>
    
  <input type="hidden" name="ruleid" id="ruleid" value=""/>  
  <logic:present name="list" scope="request">
   <table id="sheet" class="formTable">
		<td colspan="6">
            <font style="color:red;font-weight:bold;">请选择费用计算规则</font>
		</td>
    <display:table name="list" cellspacing="0" cellpadding="0"
		id="listEL"  class="table list" 
		export="false"  partialList="true" size="${fn:length(list)}">
		<display:column media="html" >
			<input type="checkbox" class="checkAble" value="${listEL.id}" id="${listEL.id}" />
		</display:column>
		<display:column property="name"  title="规则名称"  />
		<display:column title="创建人" >
			 <eoms:id2nameDB id="${listEL.creatorid}" beanId="tawSystemUserDao" />
		</display:column>				
		<display:column title="创建时间" property="crdttm"  />
		
		<display:column  title="备注" >
			 <textarea style="width:400px">${listEL.remark}</textarea>
		</display:column>
			
		<!-- 查看、修改在新页面，删除后依旧返回列表 -->		
		<display:column  title="查看">
		    <a  href="${app}/partner/feeManage/feeRule.do?method=viewRule&id=${listEL.id}"
				target="view" shape="rect"> 
				<img src="${app}/images/icons/search.gif" style="cursor:hand;display:block" /> 
			</a>
		</display:column>			
       </display:table>  
     </table>                
    </logic:present>  
    <br/>
    
    <!-- 单价明细 -->   
    <logic:present name="prcFilterList" scope="request">
       <input type="hidden" name="modelid" value="${modelid}"/>
       <c:set var="cnt" value="1"/>
       <table id="selTable" class="formTable">
         <tr><td colspan="6"><font style="color:red;font-weight:bold;">请制定明细单价</font></td></tr>
       <% 
         List srcColNameList=(List)request.getAttribute("srcColNameList"); 
         int i=0;
         String srcColName="";
         List srcColValList=null;
       %>
	   <c:forEach items="${prcFilterList}"  var="prcFilter" varStatus="status"> 
	     <c:if test="${status.first}">	        
             <td class="label"> 专业： </td>
	         <td><eoms:id2nameDB id="${majorid}" beanId="ItawSystemDictTypeDao" /></td>
	         <td class="label"> 
	           ${prcFilter.text}：
	         </td>
	         <td>	 
	           <input type="hidden" name="dictTyp" value="${prcFilter.dictTyp}"/>	
			   <c:if test="${prcFilter.dictYN}">
			      <eoms:comboBox name="${prcFilter.businessName}" id="${prcFilter.businessName}" defaultValue=""
					initDicId="${prcFilter.prtDictId}" sub="${prcFilter.sub}" />	 	
			   </c:if>	   
			   <c:if test="${prcFilter.dictYN==false}">
			      <% 
			        srcColName=(String)srcColNameList.get(i);
			        srcColValList= (List)request.getAttribute(srcColName); 
			        //后台传递数据是这样传递的，request.setAttribute(sourceColName,srcColValList)，srcColValList是jdbcService.queryForList(sql)的
			        //结果，这样srcColValList的每条数据E对于sql结果的一行 其类型 是一个map，但是这个行只有一个列即sourceColName. 这要求列名是唯一的（今后通过“表名_列名”来确定唯一性）
			      %>			     		     
			      <select id="${prcFilter.businessName}" name="${prcFilter.businessName}" class="text" 
			     sourceColName="${prcFilter.sourceColName}" sourceTableName="${prcFilter.sourceTableName}"> 
			         <option value="" >
			            请选择
			          </option>
			        <%   
			          for(int j=0;j<srcColValList.size();j++){
			            Map map=(Map)srcColValList.get(j);
			         %>
			          <option value="<%=map.get(srcColName)%>" >
			            <%=map.get(srcColName) %>
			          </option>
			        <%    
			          }
			          i++;
			        %>       		
				  </select> 
			   </c:if>
	         </td> 
	         <c:set var="cnt" value="${cnt+1}"/>
          </c:if>
          <c:if test="${not status.first}">   
             <td class="label"> 
	            ${prcFilter.text}
             </td>
	         <td>
	           <input type="hidden" name="dictTyp" value="${prcFilter.dictTyp}"/>	
	           <c:if test="${prcFilter.dictYN}">
			      <eoms:comboBox name="${prcFilter.businessName}" id="${prcFilter.businessName}" defaultValue=""
					initDicId="${prcFilter.prtDictId}" sub="${prcFilter.sub}" />	
			   </c:if>	   	   
			   <c:if test="${prcFilter.dictYN==false}">
                   <% 
			        srcColName=(String)srcColNameList.get(i);
			        srcColValList= (List)request.getAttribute(srcColName); 
			      %>			     		     
			      <select id="${prcFilter.businessName}" name="${prcFilter.businessName}" class="text" 
			     sourceColName="${prcFilter.sourceColName}" sourceTableName="${prcFilter.sourceTableName}"> 
			         <option value="" >
			            请选择
			          </option>
			        <%   
			          for(int j=0;j<srcColValList.size();j++){
			            Map map=(Map)srcColValList.get(j);
			         %>
			          <option value="<%=map.get(srcColName)%>" >
			            <%=map.get(srcColName) %>
			          </option>
			        <%    
			          }
			          i++;
			        %>       		
				  </select> 
			   </c:if>
             </td> 
	         <c:set var="cnt" value="${cnt+1}"/>
	         <c:if test="${cnt%3==0}">
	           </tr><tr>
	         </c:if>
          </c:if>
          <c:if test="${status.last}"> 
            </tr>
          </c:if>
	     </c:forEach> 
	     <tr>
	       <td colSpan="6">
	         <input type="button" class="btn" id="add" name="add" value="新增" style="width:60px;" onclick="javascript:addPrc();"/>   
	       </td>
	     </tr>	  
	    </table>
    
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
	          <th>删除</th>
	       </tr>	       
	     </thead>
	     <tbody id="prcdtltbody" name="prcdtltbody">	   
	          
	     </tbody>
	   </table>   
    </logic:present >
    <c:if test="${ (not empty majorid) and majorid!=''}">
       <logic:notPresent name="prcFilterList" scope="request">
        <table  id="prcdtltbl" name="prcdtltbl" class="table protocolMainList">
         <thead>
	   	     <tr>
	           <th>序号</th>
	           <th>专业</th>
	           <th>单价</th>	       
	         <tr>
	     </thead>    
	     <tbody id="prcdtltbody" name="prcdtltbody">
	       <tr>
	           <td>1</td>
	           <td><eoms:id2nameDB id="${majorid}" beanId="ItawSystemDictTypeDao" /></td>
	           <td><input name="prc" id="prc_1" class="text" /> </td>
	       </tr>
	     </tbody>  
	  </table>   
     </logic:notPresent>
    </c:if>
   
    <input type="submit" value="保存" class="btn" style="width:60px;"/>    	        
</form>

<eoms:xbox id="areaTree" dataUrl="${app}/xtree.do?method=areaTree"  
	rootId="${sessionScope.sessionform.rootAreaId}" rootText="${sessionScope.sessionform.rootAreaName}" 
	valueField="area" handler="areaName" 
	textField="areaName" single="true">
</eoms:xbox>
<eoms:xbox id="compTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
	rootId="" rootText='${sessionScope.sessionform.rootAreaName}' valueField="comp"
	handler="compName" textField="compName" checktype="dept"
	single="true"></eoms:xbox>

<!--  prctmplForm是要进行form验证的，通过其他form提交  --> 	
<form id="prctmplHiddenForm" name="prctmplHiddenForm" 
action="${app}/partner/feeManage/evaluFee.do?method=loadfeeprctmplwithdtl" 
method="post" style="display:none">
    <input type="hidden" name="tmpl" />
    <input type="hidden" name="specialty" />
    <input type="hidden" name="area" />
	<input type="hidden" name="comp" />
	<input type="hidden" name="remark"/>	
</form>	
	
	
	
<script type="text/javascript">
  //没有如下代码,将报错 ext.js:TypeError: $(this.showChkFldId).update is not a function 
  var myJ=jQuery.noConflict();

  function loadfeeprctmplwithdtl(){    
      var prctmplHiddenForm=document.prctmplHiddenForm;   
      var prctmplForm=document.prctmplForm;
      
      prctmplHiddenForm.tmpl.value=prctmplForm.tmpl.value;   
      prctmplHiddenForm.specialty.value=prctmplForm.specialty.value;  
      prctmplHiddenForm.area.value=prctmplForm.area.value;  
      prctmplHiddenForm.comp.value=prctmplForm.comp.value;  
      prctmplHiddenForm.remark.value=prctmplForm.remark.value;  
         
      prctmplHiddenForm.submit();
   }
   
   function prcValidate1(ths,flag){
     
     //var td=ths.parentNode;//处理 onfocus="javascript:prcValidate1(this,1);"   
     // 在input.onfocus=prcValidate1;情形下，ths是函数的第一个参数 即事件focus.
     var td;
     if(flag==1){//处理 onfocus="javascript:prcValidate1(this,1);"的情形， 传递this（元素自身 ）作为ths
       td=ths.parentNode;
     }else{
       td=this.parentNode; //处理input.onfocus=prcValidate1这种情形,this为函数prcValidate1作用的对象（标签元素）
     }
     
     var prcDiv=Ext.get(td.childNodes[1]); //div
     prcDiv.show();
  }
  
  function prcValidate2(ths,flag){
     //var td=ths.parentNode; 
     var td;
     if(flag==1){//处理 onfocus="javascript:prcValidate1(this,1);"  传递this（元素自身 ）作为ths
       td=ths.parentNode;
     }else{
       td=this.parentNode; //处理input.onfocus=prcValidate1  ,this为函数prcValidate1作用的对象（标签元素）
     }
     var prcDiv=Ext.get(td.childNodes[1]);
     
     //var prc=ths.value;
     var prc;
     if(flag==1){//处理 onfocus="javascript:prcValidate2(this,1);"  传递this（元素自身 ）作为ths
       ths.setAttribute('value',ths.value); // FF获取innerHTML中的内容,需这么做 
       prc=ths.value;
     }else{
       this.setAttribute('value',this.value); // FF获取innerHTML中的内容,需这么做 
       prc=this.value; //处理input.onfocus=prcValidate1  ,this为函数prcValidate1作用的对象（标签元素） 
     }
     var prcReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
     if(prc.match(prcReg) && ""!=prc){  
            prcDiv.dom.innerHTML="请输入数字 例：100.0";			
   			prcDiv.hide();
   			return true;
      }else{
           	prcDiv.dom.innerHTML="输入不合法,请输入数字 例：100.0";
           	prcDiv.setStyle("backgroundColor","#FF0000");//Ext的
           	prcDiv.show();
           	return false;
      }            
  } 
  
   function delRow(ths){ //仅一种情形onclick='delRow(this)'  
     var del=window.confirm("确定删除");
     if(del==false){
       return false;
     }else{
        var tr = ths.parentNode.parentNode;
        tr.parentNode.deleteRow(tr.rowIndex-1);
        //删除后更新行号
        updateNum();
     }
      
     return true;
  }
 
  function updateNum(){
     var prcdtltbody=document.getElementById("prcdtltbody");
     var numarr=Ext.query("td[name='num']",prcdtltbody) ; 
     for(var i=0;i<numarr.length;i++){ 
       var num=numarr[i]; 
       num.removeChild(num.childNodes[0]);
       num.appendChild(document.createTextNode(i+1)); 
     }
      
  }
  
   function duplicationValition(){//重复性验证。
     var prcdtltbody=document.getElementById("prcdtltbody");
     var trs=prcdtltbody.getElementsByTagName("tr");
     var xmlproarrSels=document.getElementById("selTable").getElementsByTagName("select");      
     
     for(var idx=0;idx<trs.length;idx++){
       var tr=trs[idx];
       var tds=tr.getElementsByTagName("td");

       for(var idx2=2;idx2<tds.length-2;idx2++){//跳过0,1,length-2,length-1:序号、专业名、单价、删除
         var selVal=xmlproarrSels[idx2-2].value;
         var td=tds[idx2];
         //td下的第一个子<input>是，我们要传递的值
         var hdinputs=td.getElementsByTagName("input");
         var val=hdinputs[0].value;
         //alert('selVal:'+selVal+',val:'+val); 
         if(val==selVal){
           if(idx2==tds.length-3){//行比较要比较的最后一个  
             return idx;
           }
         }else{
           break; //进如下一行检验
         }
       }
     }
     
     return -1;//不存在重复的
  }
   
   
   function addPrc(){
     var len=document.getElementById("prcdtltbody").getElementsByTagName("tr").length;
  
     var prcdtltbody=document.getElementById("prcdtltbody");
     var tr=document.createElement("tr"); 
     var input;
     //tr.setAttribute("id","row_"+prcrowcnt);  
     
     //序号
     var td;    
     try {
	          // IE6/IE7 构建方式 
	          td = document.createElement('<td name="num" >');    
          } catch (e) { 
	         // W3C 构建方式
	         td = document.createElement("td"); 
	         td.setAttribute("name","num");    
          }      
  	 td.appendChild(document.createTextNode(len+1));  //删除时需更新编号，采用对此td的td.childNodes[1]先删除后增加（td.childNodes[0]:选择，td.childNodes[1]：文本行号）  
     tr.appendChild(td);
     
     //专业名
     td=document.createElement("td");
     td.appendChild(document.createTextNode(majorname));  
     tr.appendChild(td);
         
     //重复性验证。    
      var existIdx=duplicationValition();
      if(existIdx>-1){
        alert("单价组成类型已存在,见第"+(existIdx+1)+"条 单价组成类型");
        return false;
      }
         
     //xmlproarrSels=document.getElementsByTagName("select"); //selTable
     var xmlproarrSels=document.getElementById("selTable").getElementsByTagName("select");//剔除公司框架<script type="text/javascript" src="/eoms_pt_0905/scripts/widgets/calendar/calendar.js"> 生成的隐藏<select> 
     for(var idx=0;idx<xmlproarrSels.length;idx++){
          var sel=xmlproarrSels[idx];
       //if(sel.id!='selecthour'&&sel.id!='selectminute'&&sel.id!='selectsecond'){ //剔除公司框架<script type="text/javascript" src="/eoms_pt_0905/scripts/widgets/calendar/calendar.js"> 生成的隐藏<select> 
          td=document.createElement("td");
          if(sel.value==""){ 
            td.appendChild( document.createTextNode("") ); 
          }else{
            td.appendChild( document.createTextNode(sel.options[sel.selectedIndex].text) ); 
          }        
          //通过隐藏的input传递所选的值 
          //var hd=document.createElement("input"); 
          //解决name属性动态设定的问题
          var hd;
          try {
	          // IE6/IE7 构建方式
	          hd = document.createElement('<input name="hd_'+sel.name+'" />'); 
          } catch (e) {
	         // W3C 构建方式
	         hd = document.createElement('input');
	         hd.name = "hd_"+sel.name;
          }   
          hd.setAttribute("type","hidden");  
          //hd.setAttribute("name","hd_"+sel.name);      
          td.appendChild(hd);    
           //兼容IE           
          //hd.setAttribute("value",sel.value);      
          //hd.name="hd_"+sel.name; 
          hd.value=sel.value;
          
          //hd=document.createElement("input");
          try {
	          // IE6/IE7 构建方式 
	          hd = document.createElement('<input name="hd_'+sel.name+'_sourceColName" />');    
          } catch (e) { 
	         // W3C 构建方式
	         hd = document.createElement('input');  
	         hd.name = "hd_"+sel.name+"_sourceColName";   
          }     
          hd.setAttribute("type","hidden"); 
          td.appendChild(hd);      
          //hd.setAttribute("name","hd_"+sel.name+"_sourceColName");                         
          //hd.setAttribute("value",sel.getAttribute("sourceColName")); 
          //hd.name="hd_"+sel.name+"_sourceColName"; 
          hd.value=sel.getAttribute("sourceColName"); //自定义属性必须使用此获取方式   
               
          //hd=document.createElement("input");
          try {
	          // IE6/IE7 构建方式 
	          hd = document.createElement('<input name="hd_'+sel.name+'_sourceTableName" />');    
          } catch (e) { 
	         // W3C 构建方式
	         hd = document.createElement('input');  
	         hd.name = "hd_"+sel.name+"_sourceTableName";   
          }     
          hd.setAttribute("type","hidden"); 
          //hd.setAttribute("name","hd_"+sel.name+"_sourceTableName");      
          td.appendChild(hd); 
          //hd.setAttribute("value",sel.getAttribute("sourceTableName"));  //HTML属性名不区分大小写    
          //hd.name="hd_"+sel.name+"_sourceTableName";
          hd.value=  sel.getAttribute("sourceTableName"); //Variant. Returns a string, number, or Boolean, defined by sAttrName. If an explicit attribute doesn't exist, an empty string is returned. If a custom attribute doesn't exist, null is returned       
                   
          tr.appendChild(td);      
          
           //hd=document.createElement("input");
          try {
	          // IE6/IE7 构建方式 
	          hd = document.createElement('<input name="hd_'+sel.name+'_sourceTableName" />');    
          } catch (e) { 
	         // W3C 构建方式
	         hd = document.createElement('input');  
	         hd.name = "hd_"+sel.name+"_dict";   
          }     
          hd.setAttribute("type","hidden"); 
          //hd.setAttribute("name","hd_"+sel.name+"_sourceTableName");      
          td.appendChild(hd); 
          //hd.setAttribute("value",sel.getAttribute("sourceTableName"));  //HTML属性名不区分大小写    
          //hd.name="hd_"+sel.name+"_sourceTableName";
          hd.value=  sel.parentNode.getElementsByTagName("input")[0].value;      
                   
          tr.appendChild(td);  
       //}  
     }
     
     td=document.createElement("td");
     //td.appendChild(document.createElement("<input type='text' class='text' name='prc' id='prc_"+prcrowcnt+"'>"));  
     //input=document.createElement("input"); 
     try {
	          // IE6/IE7 构建方式 
	          input = document.createElement('<input name="prc" />');    
     } catch (e) { 
	         // W3C 构建方式
	         input = document.createElement('input');  
	         input.name = "prc";   
      }    
     input.setAttribute("type","text"); 
     //input.setAttribute("name","prc");   
     td.appendChild(input);  
     /* //火狐可以如下
     input.setAttribute("class","text");  
     input.setAttribute("name","prc"); 
     input.setAttribute("id","prc_"+prcrowcnt);   
     input.setAttribute("onfocus","prcValidate1(this)");   
     input.setAttribute("onblur","prcValidate2(this)");    

     IE并不是不支持setAttribute这个函数，而是不支持用setAttribute设置某些属性，例如对象属性、集合属性、事件属性，
     也就是说用setAttribute设置style和onclick这些属性在IE中是行不通的.
     */
     //兼容IE做法  
     input.className="text";    
     //input.name="prc"; 
     //input.id="prc_"+prcrowcnt;   
     input.onfocus=prcValidate1;  //函数 ////////////////////  
     input.onblur=prcValidate2; 
  	 // 
  	 var prcdiv=document.createElement("div");
  	 td.appendChild(prcdiv); 
  	 prcdiv.className="ui-state-highlight ui-corner-all";    
  	 prcdiv.style.width="150px";    
  	 prcdiv.style.display="none";   
  	 prcdiv.innerHTML="请输入数字,如100.0" ;  
  	    
     tr.appendChild(td);
     
     td=document.createElement("td");
     td.innerHTML="<img src='${app}/images/icons/delete.gif' onclick='delRow(this)' style='cursor:hand;'/>" ; 
     tr.appendChild(td);
     
     prcdtltbody.appendChild(tr);

  }
  
  

  function tmplNameExist(){
    tmplNameExistYN=false; //全局变量置为初值tmplNameExistYN=false 
    var tmplname=document.getElementById("id_tmpl").value.trim();
    var tmpldiv=Ext.get("id_tmpldiv"); 	
    tmpldiv.hide(); 
     
    Ext.Ajax.request({
	 	            url: '${app}/partner/feeManage/evaluFee.do?method=tmplNameExist', 
                    params:{ 
       	  				tmplname:tmplname,   
       	  				opertyp:'add'  //add与edit时的同名验证会不同     	  				
       				}, 
       				success:function(response,options){      
       				    			  
       					 var resulttext=response.responseText;
       					 var rslt=Ext.decode(resulttext);   
       					  
       					 if(rslt.exist==true){ 
       					    tmplNameExistYN=true;      					     					     
       					    tmpldiv.show(); 
       					 }     						   
       					 //document.getElementById("inputscoreDiv_"+trIndex).value= rslt.score; 
       					 else{     	
       					    tmplNameExistYN=false;				    
           					tmpldiv.hide(); 
       					 }	
      				}
	 	        }); 
  }
 
  function prcValidate(prcInput){     
     var prc=prcInput.value;
     var prcReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
     if(prc.match(prcReg) && ""!=prc){  			
   			return true;
      }else{         	
           	return false;
      }            
  }
 
  function prcDtlValidate(){
      var prcdtltbody=document.getElementById("prcdtltbody");
      var prcArr=Ext.query("input[name='prc']",prcdtltbody) ;//单价输入框 
      
      if(prcArr.length<1){
         alert("你还没有定制任何单价");
         return false;
      }
      
      for(var i=0;i<prcArr.length;i++){
        if(prcValidate(prcArr[i])){
        }else{
          alert("第"+(i+1)+"单价格式不对");
          return false;
        }
      }
      
      return true;
  }

  function validateForm(){ //form提交前的校验
     //（月度考核费用单价）模板名称  同名校验 
     if(tmplNameExistYN){
       alert("模板名已存在");
       return false; 
     }
     
     //单价详情必须有，且必填写单价！
     if(prcDtlValidate()){
     }else{
       return false;
     }
     
     return true;
  }
  
 Ext.onReady(function(){             
	    var v = new eoms.form.Validation({form:'prctmplForm'});
        v.custom = function(){  
            if(!validateForm()){
              return false;
            }            
            
            var id = "";
			myJ(':checked.checkAble').each(	
					function(){
						id += myJ(this).val();
					}
			);
            if(id==""){
					alert("请选择需要关联的模板");
					return false;
			}
				
            return true;
	   };  	
	   
	   myJ('input.checkAble:checkbox').unbind('click');
	   myJ('input.checkAble:checkbox').click(function(e){
			if(myJ(this).attr('checked')==true){
				myJ('#ruleid').val(e.target.value);
				myJ("input.checkAble:checkbox[id!="+e.target.id+"]").removeAttr('checked'); 
			}else{				 
			}
		});   
}); 
</script>	
<%@ include file="/common/footer_eoms.jsp"%>

