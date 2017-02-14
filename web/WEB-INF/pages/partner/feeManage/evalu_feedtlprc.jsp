<%@ page language="java" pageEncoding="UTF-8" import="java.util.*"%>
<%@page import="com.boco.eoms.partner.feeManage.util.PriceProperty;"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
   //定义一些公共变量
   var prcrowcnt=0;
   var majorname="${majorname}" ; 
</script>
<script type="text/javascript">
  function prcValidate1(){
     var td=this.parentNode;
     var prcDiv=Ext.get(td.childNodes[1]); //div
     prcDiv.show();
  }
   
  function prcValidate2(){
     var td=this.parentNode; 
     var prcDiv=Ext.get(td.childNodes[1]);
     
     this.setAttribute('value',this.value); // FF获取innerHTML中的内容,需这么做  
     var prc=this.value;
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

  function duplicationValition(){//重复性验证。
     var prcdtltbody=document.getElementById("prcdtltbody");
     var trs=prcdtltbody.getElementsByTagName("tr");
     var xmlproarrSels=document.getElementById("selTable").getElementsByTagName("select");      
     
     for(var idx=0;idx<trs.length;idx++){
       var tr=trs[idx];
       var tds=tr.getElementsByTagName("td");

       for(var idx2=2;idx2<tds.length-1;idx2++){//跳过0,1,length-1:选择、专业名、单价
         var selVal=xmlproarrSels[idx2-2].value;
         var td=tds[idx2];
         //td下的第一个子<input>是，我们要传递的值
         var hdinputs=td.getElementsByTagName("input");
         var val=hdinputs[0].value;
         //alert('selVal:'+selVal+',val:'+val); 
         if(val==selVal){
           if(idx2==tds.length-2){//行比较要比较的最后一个  
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
     //tr.setAttribute("id","row_"+prcrowcnt);  
     tr.id="row_"+prcrowcnt;
     
     //选择
     var td=document.createElement("td");
     //td.appendChild(document.createElement("<input type='checkBox' id='cb_"+prcrowcnt+"'>"));  //仅IE可以在创建元素的同时指定属性
     var input=document.createElement("input");  
     input.setAttribute("type","checkbox");      
  	 td.appendChild(input);
  	 //input.setAttribute("id","cb_"+prcrowcnt);   
  	 input.id="cb_"+prcrowcnt;
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
          hd.value=  sel.getAttribute("sourceTableName")       
                   
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
     input.id="prc_"+prcrowcnt;   
     input.onfocus=prcValidate1;  //函数 ////////////////////  
     input.onblur=prcValidate2; 
  	 
  	 var prcdiv=document.createElement("div");
  	 td.appendChild(prcdiv); 
  	 prcdiv.id="prcdiv_"+prcrowcnt;
  	 prcdiv.className="ui-state-highlight ui-corner-all";    
  	 prcdiv.style.width="150px";    
  	 prcdiv.style.display="none";   
  	 prcdiv.innerHTML="请输入数字,如100.0" ;  
  	    
     tr.appendChild(td);
     
     prcdtltbody.appendChild(tr);
     prcrowcnt++;
  }
  
  function delBatchPrc(){
     var prcdtltbody=document.getElementById("prcdtltbody");
     var selChkArr=Ext.query("input[type='checkbox']:checked",prcdtltbody) ; 
     if(selChkArr.length<1){
       alert("请选择！");
       return false;
     }
  
     var del=window.confirm("确定删除");
     if(del==false){
       return false;
     }else{
       for(var i=0;i<selChkArr.length;i++){
         var revTr=selChkArr[i].parentNode.parentNode;//tr
         //revTr.removeNode(true);
         prcdtltbody.removeChild(revTr);
       }
       //删除后更新行号
       updateNum();
     }
  }
 
  function updateNum(){
     var prcdtltbody=document.getElementById("prcdtltbody");
     var ChkArr=Ext.query("input[type='checkbox']",prcdtltbody) ; 
     for(var i=0;i<ChkArr.length;i++){ 
       var chk=ChkArr[i]; 
       var chkprt=chk.parentNode;//td
       chkprt.removeChild(chkprt.childNodes[1]);
       chkprt.appendChild(document.createTextNode(i+1)); 
     }
  }
  
  
</script>

   <c:set var="cnt" value="1"/>
   <table id="sheet" class="formTable">
     <table id="selTable" class="formTable">
       <% 
         List<String> srcColNameList=(List<String>)request.getAttribute("srcColNameList"); 
         int i=0;
         String srcColName="";
         List<Map> srcColValList=null;
       %>
	   <c:forEach items="${pricePropertyList}"  var="priceProperty" varStatus="status"> 
	     <c:if test="${status.first}">
	        <tr>
             <td class="label"> 专业： </td>
	         <td>${majorname}</td>
	         <td class="label"> 
	           <c:if test="${priceProperty.dictYN}">${priceProperty.dictText}： </c:if>
	           <c:if test="${priceProperty.dictYN==false}">${priceProperty.sourceColText}： </c:if>
	         </td>
	         <td>
	           <c:if test="${priceProperty.businessName=='type'}">
	             <eoms:comboBox name="type" id="type" defaultValue=""
					initDicId="${majorid}" sub="${priceProperty.sub}"  />
			   </c:if>
			   <c:if test="${priceProperty.dictYN && priceProperty.businessName!='type'}">
                 <eoms:comboBox name="${priceProperty.businessName}" id="${priceProperty.businessName}" defaultValue=""
					initDicId="${priceProperty.dictId}" sub="${priceProperty.sub}" />					
			   </c:if>			   
			   <c:if test="${priceProperty.dictYN==false}">
			      <% 
			        srcColName=srcColNameList.get(i);
			        srcColValList= (List<Map>)request.getAttribute(srcColName); 
			      %>			     		     
			      <select id="${priceProperty.businessName}" name="${priceProperty.businessName}" class="text" 
			     sourceColName="${priceProperty.sourceColName}" sourceTableName="${priceProperty.sourceTableName}"> 
			         <option value="" >
			            请选择
			          </option>
			        <%   
			          for(Map map:srcColValList){
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
                <c:if test="${priceProperty.dictYN}">${priceProperty.dictText}： </c:if>
	            <c:if test="${priceProperty.dictYN==false}">${priceProperty.sourceColText}： </c:if>
             </td>
	         <td>
	           <c:if test="${priceProperty.businessName=='type'}">
	             <eoms:comboBox name="type" id="type" defaultValue=""
					initDicId="${majorid}" sub="${priceProperty.sub}"  />
			   </c:if>
			   <c:if test="${priceProperty.dictYN && priceProperty.businessName!='type'}">
                 <eoms:comboBox name="${priceProperty.businessName}" id="${priceProperty.businessName}" defaultValue=""
					initDicId="${priceProperty.dictId}" sub="${priceProperty.sub}"  />
			   </c:if>			   
			   <c:if test="${priceProperty.dictYN==false}">
                  <% 
			        srcColName=srcColNameList.get(i);
			        srcColValList= (List<Map>)request.getAttribute(srcColName); 
			      %>			     
			      <select id="${priceProperty.businessName}" name="${priceProperty.businessName}" class="text" 
			     sourceColName="${priceProperty.sourceColName}" sourceTableName="${priceProperty.sourceTableName}"> 
			        <option value="" >
			            请选择
			          </option>
			        <% 
			          for(Map map:srcColValList){
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
	      <center>
	        <input type="button" id="add" name="add" value="新增" onclick="javascript:addPrc();"/> 
	        <input type="button" id="batchDel" name="batchDel" value="批量删除" onclick="javascript:delBatchPrc();"/>
	      </center>   
	     </td>  
	   </tr>	  
	 </table>
	
	
	<div id="dtlprc_tbl_div_src" name="dtlprc_tbl_div_src" >
	   <table id="prcdtltbl" name="prcdtltbl" class="table protocolMainList">
	     <thead>
	       <tr> 
	           <th>选择</th>
	           <th>专业</th>
	         <c:forEach items="${pricePropertyList}"  var="priceProperty" varStatus="status">
	           <th>
	            <c:if test="${priceProperty.dictYN}">${priceProperty.dictText} </c:if>
	            <c:if test="${priceProperty.dictYN==false}">${priceProperty.sourceColText} </c:if>
               </th>
	         </c:forEach>
	          <th>单价</th>
	       </tr>	       
	     </thead>
	     <tbody id="prcdtltbody" name="prcdtltbody">	        
	     </tbody>
	   </table>
	</div>   
  </table>
          
<%@ include file="/common/footer_eoms.jsp"%>