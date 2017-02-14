<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<fieldset>
			<legend>请输入统计条件</legend>
<form id="mainForm" name="mainForm" action="oilEngineStatistics.do?method=statisticsTimes" method="post"> 
<input type="hidden" name="updateId" id="updateId" value="${oilEngine.id}"/> 
<input type="hidden" name="createUserId" id="createUserId" value="${createUserId}"/> 
<input type="hidden" name="nextAction" id="nextAction" value="${nextAction}"/> 
<table class="formTable">
	<td class="label">
			代维公司
		</td>
		<td class="content">				
	 <input type="text" id="monitorCompany" name="monitorCompany"  onfocus="getcity()"  alt="allowBlank:false"/>
	    	<input type="hidden" id="monitorCompanyid" name ="monitorCompanyid" /></td>
	    	<eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="代维公司树"  valueField="monitorCompanyid" handler="monitorCompany" 
						textField="monitorCompany" checktype="dept" single="true" />
		</td>

	</tr>
	<tr>
		<td class="label">
			地市
		</td>
		<td class="content">
		  <select id="cityid" name="cityid" class="ddd" onchange="getRegionCity1(this.options[this.options.selectedIndex].value);">
		  </select>
		</td>
		<td class="label">
			县区
		</td>
		<td class="content">
		  <select id="countryid" name="countryid" class="ccc" onchange="getUnderContryInfo(this.options[this.options.selectedIndex].value);">
		  </select>
		</td>
	</tr>
	<tr>
	
	<tr>
	   <td class="label">驻点名称</td>
		<td class="content">
		<select id=residentSiteNameid name="residentSiteNameid" class="ddd" onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
		    </select>	
		    </td>
		<td class="label">发电基站</td>
		<td class="content">
		<select id="stationid" name="stationid" class="ddd" onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
		    </select>	
		</td>
		
	</tr>
		<tr>
		<td class="label">开始时间</td>
		<td class="content">
		<input class="text" type="text" name="startTime" value="${oilEngine.startTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间！',allowBlank:false"
					 id="startTime" readonly="readonly"/>
		</td>	
		<td class="label">结束时间</td>
		<td class="content">
		<input class="text" type="text" name="endTime" value="${oilEngine.endTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间！',allowBlank:false"
					 id="endTime" readonly="readonly"/>
		</td>
	</tr>	
		

</table>
			<input type="button" name="查询发电时长"  value="查询发电时长" onclick="sendBox();"/>
</fieldset>
<fieldset>
			<legend>请选择统计项目</legend>
<table class="formTable">
     <tr>
         <td class="label">地市</td>
         <td><input  id="city" type="checkbox" name="cardNumber"
				value="city" /></td>
		 <td class="label">区县</td>
		 <td><input  id="country" type="checkbox" name="cardNumber"
				value="country" /></td>
		 <td class="label">代维公司</td>
		 <td><input  id="monitorcompany" type="checkbox" name="cardNumber"
				value="monitorcompany" /></td>
		 <td class="label">驻点</td>
		 <td><input  id="residentsiteName" type="checkbox" name="cardNumber"
				value="residentsiteName" /></td>
		 <td class="label">基站名称</td>
		 <td><input  id="station" type="checkbox" name="cardNumber"
				value="station" />
		 </td>
	 </tr>
	 <tr>	 
		 <td class="label">代维油机发电总时长</td>
		 <td><input  id="generateTime" type="checkbox" name="cardNumber"
				value="generateTime" /></td>
		 <td class="label">动环油机告警总时长</td>
		 <td><input  id="alarmTime" type="checkbox" name="cardNumber"
				value="alarmTime" /></td>
		 <td class="label">能耗信息</td>
		 <td colspan="5"><input  id="energyInfo" type="checkbox" name="cardNumber"
				value="energyInfo" />
		</td>	
				<input type="hidden" name="deleteIds" id="deleteIds" />
				<input type="hidden" name="checks" id="checks" />
				<input type="hidden" name="checkIds" id="checkIds" value="${checkList}" />					
     </tr>
     <tr>
       <td colspan="10"> 
       <font color="red">说明：以下表格所有列是根据选择的统计项目自动生成的</font>
       </td>
     </tr>
</table>
</fieldset>
</form>
<script>
  var myjs=jQuery.noConflict();


   window.onload = function(){ 
          getRegionCity('');      
          getDept('');       
          getRegionCounty('');    
            
       }
          //选取县区后的关联
       function getUnderContryInfo(v){
            getResidentSiteName(v);
            getStation(v);
       }
       
        //驻点信息
       function getResidentSiteName(v){
        
         myjs.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getResidentSiteName&countryid="+v,
                   function(data){
                   	myjs("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
                
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].id)
								.text(data[i].residentSiteName)
								.appendTo("#residentSiteNameid");	
				    	}	
				    	
				    	var  residentSiteNameid = '${oilEngine.residentSiteNameid}';    
					        if(residentSiteNameid != ''){
					          document.forms[0].residentSiteNameid.value = residentSiteNameid ;						          			         
					        }			  
			        }
         );
       }
       
       //发电基站
       function getStation(v){        
 
         myjs.getJSON("${app}/partner/oilmachine/OilEngineManagement.do?method=getStation&countryid="+v,
                   function(data){
                   	myjs("#stationid").empty().append('<option value="" >--请选择--</option>');
                  
 						for(var i=0;i<data.length;i++){
							myjs('<option/>')
								.val(data[i].id)
								.text(data[i].station)
								.appendTo("#stationid");	
				    	}
				    	var  stationid = '${oilEngine.stationid}';    
					        if(stationid != ''){
					          document.forms[0].stationid.value = stationid ;						          			         
					        }				  
			        }
         );
       }
       
        function  getcity(){
  
        var monitorCompany = document.forms[0].monitorCompany.value ;
         if(monitorCompany == '' || monitorCompany == null){
            return ;
         }       
        var monitorCompanyid = document.forms[0].monitorCompanyid.value ;   
        myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCityInfo&monitorCompany="+monitorCompany+"&monitorCompanyid="+monitorCompanyid,
                   function(data){

						  document.forms[0].cityid.value = data[0].area_id ; 	
					//	  document.forms[0].city.value = data[0].area_name ; 
						  
						  getRegionCounty(data[0].area_id);	  					
			        }
               );
         
        

   }
       
var vlt = new eoms.form.Validation({form:'mainForm'});

function sendBox() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var checkId="";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";" ;
			var tempId=cardNumberList[i].id;
			checkId +=tempId.toString()+";" ;
		}
	}
	$("deleteIds").value=idResult.toString();
	$("checks").value=checkId.toString();
		if(idResult==""){
		alert("请至少选择一个统计项");
				return false;}
//		document.getElementById("mainForm").submit();
        document.forms[0].submit();
	}   

function saveConfirm(){
	if(document.all["status"].value == '${STATUS_REJECT}'){
		document.all["status"].value = '${STATUS_AUDIT}';
		return true;
	}

	if(document.all["approvedView"].selectedIndex == 0){
		alert("请选择是否审核！");
		return false;
	}
	if(document.all["approvedView"].selectedIndex == 1){
		if(confirm("确认提交审核？")){
			document.all["status"].value = '${STATUS_AUDIT}';
			return true;
		}else{
			return false;		
		}
	}else if(document.all["approvedView"].selectedIndex == 2){
		document.all["status"].value = '${STATUS_NO_NEED_AUDIT}';
		return true;
	}else{
		document.all["status"].value = '${STATUS_AUDIT}';
		return true;
	}


}

  //市区
   function getRegionCity(v){
 
           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCityCom",
                   function(data){

                    myjs("#stationid").empty().append('<option value="" >--请选择--</option>');                  
                    myjs("#residentSiteNameid").empty().append('<option value="" >--请选择--</option>');
                    myjs("#cityid").empty().append('<option value="" >--请选择--</option>');
                   
 						for(var i=0;i<data.length;i++){
 						
							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#cityid");	
				    	}
				    	myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                        
				    	if(v != ''){	
				    	 document.forms[0].cityid.value = v ;			     	
				     	 getRegionCounty(v);	
				    	}else{
				    	}				    	
				        var cityid = '${residentSiteForm.cityid}';
				      //   alert('cityid:'+cityid);
                        if (cityid != ''){                      
                            document.forms[0].cityid.value = cityid ;
                         }			  					
			        }
               );
       }
       
       //市区_选择
   function getRegionCity1(v){
 
           myjs.getJSON("${app}/partner/oilmachine/oilResidentSites.do?method=getDeptCityCom",
                   function(data){

                    myjs("#cityid").empty().append('<option value="" >--请选择--</option>');
                   
 						for(var i=0;i<data.length;i++){
 						
							myjs('<option/>')
								.val(data[i].areaid)
								.text(data[i].areaname)
								.appendTo("#cityid");	
				    	}
				    	myjs("#countryid").empty().append('<option value="" >--请选择--</option>');
                        
				    	if(v != ''){		
				    	
				    	 document.forms[0].cityid.value = v ;			     	
				     	 getRegionCounty1(v);	
				    	}	
				    	
				    				    	
				        			  					
			        }
               );
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
                v = '${residentSiteForm.cityid}' ;
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



function setHiddenStatusValue(slt){
	document.getElementById("auditUserTree").style.display = "none";
	if(slt.selectedIndex == 0){
		document.all["status"].value = '${STATUS_DRAFT}';
		document.all["taskOwner"].value = "";
		document.all["taskOwner_CN"].value = "";
	}
	if(slt.selectedIndex == 1){
		document.all["status"].value = '${STATUS_AUDIT}';
		document.getElementById("auditUserTree").style.display = "";
	}
	if(slt.selectedIndex == 2){
		document.all["status"].value = '${STATUS_NO_NEED_AUDIT}';
		document.all["taskOwner"].value = "";
		document.all["taskOwner_CN"].value = "";
	}
	
	document.all["approved"].value = document.all["approvedView"].options[document.all["approvedView"].selectedIndex].value;
}

function saveAsDraft(){
//	document.all["status"].value = '${STATUS_DRAFT}';
	return true;
}

function autoCountFee(){
	document.all["totalFee"].value =  document.all["oilConsumingCount"].value * document.all["perPrice"].value;
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>