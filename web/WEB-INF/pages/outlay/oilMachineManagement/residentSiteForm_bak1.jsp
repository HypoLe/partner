<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script> 
 
<eoms:xbox id="partnerTree" dataUrl="${app}/xtree.do?method=dept" rootId="1"
	rootText='代维公司' valueField="monitorCompany" handler="partnerName" textField="partnerName"
	checktype="dept" single="true"></eoms:xbox>
<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=userFromDept" rootId="2"
	rootText='负责人' valueField="dutyMan" handler="dutyManName" textField="dutyManName"
	checktype="user" single="true"></eoms:xbox>
	<eoms:xbox id="dutyManTree" dataUrl="${app}/xtree.do?method=area" rootId="-1"
	rootText='地市' valueField="city" handler="cityName" textField="cityName"
	checktype="area" single="true"></eoms:xbox>
<script type="text/javascript">
  var myjs=jQuery.noConflict();
     Ext.onReady(function(){
		xbox_partnerNameTree = new xbox({"id":"partnerNameTree","single":true,"showChkFldId":"partnerName_CN",
		"saveChkFldId":"partnerName","treeDataUrl":"/HLJ/xtree.do?method=userFromDept&popedom=true",
		"btnId":"partnerName_CN","treeRootId":"","checktype":"dept","treeRootText":"代维公司树"});
	});

       window.onload = function(){ 
          getRegionCity('');      
          getDept('');  
         }
  
   function getRegionCity(v){
 
           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCityCom",
                   function(data){

                    myjs("#city").empty().append('<option value="" >--请选择--</option>');
                   
 						for(var i=0;i<data.length;i++){
 						
							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#city");	
				    	}
				    	myjs("#country").empty().append('<option value="" >--请选择--</option>');
				    //	alert(v);
				    	if(v != ''){
				     	
				     	document.forms[0].city.value= v;
				     	 getRegionCounty(v);	
				    	}				  
						//   var isshowregionid=document.getElementById("isshowregionid");
						//   if(isshowregionid.value != ""){
						//     document.appraosalScoreFrom.regionid.value= isshowregionid.value;
					 //  }
			        }
               );
       }
       //县区
       function getRegionCounty(v){

           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCountyCom&city="+v,
                   function(data){

                    myjs("#country").empty().append('<option value="" >--请选择--</option>');
                   	 
 						for(var i=0;i<data.length;i++){
 						
 						//alert('data[i].deptid:'+data[i].deptid);
 					//	alert('data[i].deptidname:'+data[i].deptname);
							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#country");	
				    	}	
				    	document.forms[0].country.value= v;
					//	   var isshowregionid=document.getElementById("isshowregionid");
					//	   if(isshowregionid.value != ""){
					//	     document.appraosalScoreFrom.regionid.value= isshowregionid.value;
					//	   }
			        }
               );
       }
       function getDept(v){
         myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCom",
                   function(data){

                    myjs("#monitorCompany").empty().append('<option value="" >--请选择--</option>');
                   	 
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].deptid)
								.text(data[i].deptname)
								.appendTo("#monitorCompany");	
				    	}
				    	var isshowdeptid=document.getElementById("isshowdeptid");
						   if(isshowdeptid.value != ""){
						     document.appraosalScoreFrom.deptid.value= isshowdeptid.value;
						   }	
			        }
         );
       }
       
   function  test(){
    alert('a');
    alert(document.forms[0].monitorCompany.value);
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
		   <select id="monitorCompany" name="monitorCompany" class="ddd" >
		    </select>	
		</td>
	<td class="label">
			负责人&nbsp;
		</td>
		<td class="content">		
			<input type='text' id='dutyManName' name="dutyManName"  readonly="true" value='<eoms:id2nameDB id="${residentSiteForm.dutyMan}" beanId="tawSystemUserDao" />'/>			
		    <input type='hidden' id="dutyMan" name="dutyMan"  />
		</td>
	</tr>

	<tr>
		<td class="label">
			地市&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		  <select id="city" name="city" class="ddd" onchange="getRegionCity(this.options[this.options.selectedIndex].value);">
		  </select>
		</td>
		<td class="label">
			县区&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		  <select id="country" name="country" class="ccc">
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
						alt="allowBlank:true,vtext:''" value="${residentSiteForm.telNum}" />
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
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>"/>
		   <input type="button" class="btn" value="test" onclick="test()"/>
		</td>
	</tr>
</table>
<html:hidden property="id" styleId="id" value="${residentSiteForm.id}" />
<html:hidden property="partnerId" styleId="partnerId" value="${residentSiteForm.partnerId}" />
<html:hidden property="dutyMan" styleId="dutyMan" value="${residentSiteForm.dutyMan}" />
<html:hidden property="createTime" styleId="createTime" value="${residentSiteForm.createTime}" />
<html:hidden property="createUser" styleId="createUser" value="${residentSiteForm.createUser}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>