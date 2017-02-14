<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	myJ('select#country').html('<option value="">请选择县</option>').attr('disabled',true);
	myJ.ajax({
		  type:"POST",
		  url: "${app}/partner/baseinfo/partnerDepts.do?method=retriveCCC",
		  data: "flag=city&parentAreaId=24",
		  success: function(jsonMsg){
        	  var cityHtml =myJ.parseJSON(jsonMsg).city;
			  myJ('select#city').html(cityHtml);
      	  }
	});
	
	myJ('select#city').bind('change',function(event){
		var cityValue = myJ(this).val();
		if(cityValue==""){
			 myJ('select#country').html('<option value="">请选择县</option>').attr('disabled',true);
		}else{
			myJ('select#country').html('<option value="">载入中...</option>').attr('disabled',false);
			myJ.ajax({
				  type:"POST",
		  		  url: "${app}/partner/baseinfo/partnerDepts.do?method=retriveCCC",
				  data: "flag=country&parentAreaId="+cityValue,
		  		  success: function(jsonMsg){
        		  var countryHtml =myJ.parseJSON(jsonMsg).country;
        		  var monitorCompanyHtml =myJ.parseJSON(jsonMsg).monitorCompany;
			 	  myJ('select#country').html(countryHtml);
			 	  myJ('select#monitorCompany').html(monitorCompanyHtml);
      	  		}
			});
		}
	});
	
});
</script>

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;" id="opBasicInfo">分公司和归属县公司</div>
<div id="basicInfo" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
<table class="formTable">
	<tr>
		<td class="label">分公司*</td>
		<td><select id='city' name="city"></select></td>
		<td class="label">归属县公司*</td>
		<td><select id='country' name="country"></select></td>
	</tr>
	<tr>
		 <td class="label">
			代维公司&nbsp;*
		</td>
		<td class="content">
		
		 <input type="text"  class="text"  name="provName" id="provName"  
					value="<eoms:id2nameDB id="tmpIdX1" beanId="partnerDeptDao"/>" 
					alt="allowBlank:false" readonly="readonly"/>
					
		 <input name="monitorCompany" id="monitorCompany" type="hidden" />
		 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
				rootId="" rootText="代维公司树"  valueField="monitorCompany" handler="provName" 
				textField="provName" checktype="dept" single="true" />
		</td>
		
		
	</tr>
</table>
</div>