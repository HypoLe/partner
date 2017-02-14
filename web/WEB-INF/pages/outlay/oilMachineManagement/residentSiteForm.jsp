<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script> 
 
  <eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="代维公司树"  valueField="monitorCompanyid" handler="monitorCompany" 
						textField="monitorCompany" checktype="dept" single="true" />
						
<eoms:xbox id="partnerTree" dataUrl="${app}/xtree.do?method=dept" rootId="1"
	rootText='代维公司' valueField="monitorCompany" handler="partnerName" textField="partnerName"
	checktype="dept1" single="true"></eoms:xbox>
<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
	rootText='负责人' valueField="dutyMan" handler="dutyManName" textField="dutyManName"
	checktype="user" single="true"></eoms:xbox>
<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=area" rootId="-1"
	rootText='地市' valueField="city" handler="cityName" textField="cityName" 
	checktype="area" single="true"></eoms:xbox>
<script type="text/javascript">
  var myjs=jQuery.noConflict();
   
       window.onload = function(){ 
            
          getDept('');       
          getRegionCounty('');    
       }
                   
       //县区-选择
       function getRegionCounty1(v){
               
           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCountyCom&city="+v,
                   function(data){

                    myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                   	 
 						for(var i=0;i<data.length;i++){

							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#countryid");	
				    	}	
				    	document.forms[0].countryid.value= v;
				    	var countryid = '${residentSiteForm.countryid}';
				    	//alert('countryid:'+countryid);
                      if (countryid != ''){
                             document.forms[0].countryid.value = countryid ;
                        }
				
			        }
               );
       }
       //县区
       function getRegionCounty(v){
               if('${residentSiteForm.cityid}' != ''){
                v = document.forms[0].cityid.value ;
               }
            
           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCountyCom&city="+v,
                   function(data){

                    myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                   	 
 						for(var i=0;i<data.length;i++){

							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#countryid");	
				    	}	
				    	document.forms[0].countryid.value= v;
				    	var countryid = '${residentSiteForm.countryid}';
				    //	alert('countryid:'+countryid);
                      if (countryid != ''){
                             document.forms[0].countryid.value = countryid ;
                        }
				
			        }
               );
       }
       //代维公司
       function getDept(v){
         myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCom",
                   function(data){

                    myjs("#monitorCompanyid").empty().append('<option value="" >--请选择--</option>');
                   	 
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].deptid)
								.text(data[i].deptname)
								.appendTo("#monitorCompanyid");	
				    	}
				    	
				    	var monitorCompanyid = '${residentSiteForm.monitorCompanyid}';
                        if (monitorCompanyid != ''){
                         document.forms[0].monitorCompanyid.value = monitorCompanyid ;
                         }
				  
			        }
         );
       }
       
   function  test(){
       
        var monitorCompany = document.forms[0].monitorCompany.value ;
         if(monitorCompany == '' || monitorCompany == null){
          return ;
         }       
        
                                   
         var id = document.forms[0].monitorCompanyid.value ;
       
      
        myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCityInfo&monitorCompany="+monitorCompany+"&id="+id,
                   function(data){
                   
						  document.forms[0].cityid.value = data[0].area_id ; 	
						  document.forms[0].city.value = data[0].area_name ; 
						  
						  getRegionCounty(data[0].area_id);	  					
			        }
               );
         
        

   }
     function  sub(){    
        
        var objcountry=document.getElementsByName("countryid")[0];
        var country= objcountry.options[objcountry.options.selectedIndex].text//
        document.forms[0].country.value = country ;         
        document.forms[0].submit();
   }
   </script>
   
<html:form action="/oilResidentSites.do?method=save"  styleId="oilResidentSitesForm" method="post"> 

<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center">驻点信息</div>
	</caption>

	<tr>
		<td class="label">
			驻点名称&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
			<html:text property="residentSiteName" styleId="residentSiteName"
						styleClass="text max"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${residentSiteForm.residentSiteName}"/>
		</td>
	</tr>
	<tr>
	<td class="label">
			代维公司&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		
				<input type="text" id="monitorCompany" name="monitorCompany" value="${residentSiteForm.monitorCompany}"  onfocus="test()"  alt="allowBlank:false"/>
				<input type="hidden" id="monitorCompanyid" name ="monitorCompanyid" value="${residentSiteForm.monitorCompanyid}"/></td>
				
				
	<!--  
		   <select id="monitorCompanyid" name="monitorCompanyid" class="ddd" >
		    </select>	
	-->	    
		</td>

	</tr>

	<tr>
		<td class="label">
			地市&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		 <input id="cityid" name="cityid" type="hidden" value="${residentSiteForm.cityid}"> 
		<input id="city" name ="city" type="text" readonly="readonly" value="${residentSiteForm.city}" />
		</td>
		<td class="label">
			县区&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		  <select id="countryid" name="countryid" class="ccc">
		  </select>
		</td>
	</tr>

	<tr>
		<td class="label">
			人员数
		</td>
		<td class="content">
		<html:text property="personNum" styleId="personNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',vtype:'number'" value="${residentSiteForm.personNum}" />
			
		</td>
		<td class="label">
			车辆数&nbsp;
		</td>
		<td class="content">
		<html:text property="carNum" styleId="carNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',vtype:'number'" value="${residentSiteForm.carNum}" />
		</td>		
	</tr>

	<tr>
		<td class="label">
			值班固定电话&nbsp;
		</td>
		<td class="content">
		<html:text property="telNum" styleId="telNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${residentSiteForm.telNum}" />
			
		</td>
		<td class="label">
			值班移动电话&nbsp;
		</td>
		<td class="content">
		<html:text property="mobileTelNum" styleId="mobileTelNum"
						styleClass="text medium"
						alt="allowBlank:true,vtext:''" value="${residentSiteForm.mobileTelNum}" />
		</td>		
	</tr>





	<tr>
		<td class="label">
			地址
		</td>
		<td class="content" colspan="3">
		<html:textarea property="address" styleId="address" 
				styleClass="text medium" style="width:80%" rows="3"
				alt="allowBlank:true,vtext:''" value="${residentSiteForm.address}" />
		</td>
		
</table>



<table>
	<tr>
		<td>
			<input type="button" class="btn" value="保&nbsp;存" onclick="sub();"/>
		   <input type="button" class="btn" value="返回" onclick="window.history.back();"/>
		</td>
	</tr>
</table>
<html:hidden property="id" styleId="id" value="${residentSiteForm.id}" />
<html:hidden property="partnerId" styleId="partnerId" value="${residentSiteForm.partnerId}" />
<html:hidden property="country" styleId="country" value="${residentSiteForm.country}" />
<html:hidden property="createTime" styleId="createTime" value="${residentSiteForm.createTime}" />
<html:hidden property="createUser" styleId="createUser" value="${residentSiteForm.createUser}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>