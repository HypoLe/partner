<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/ajax.js">
</script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>

<fieldset>
	<legend>
		请输入统计条件
	</legend>
	<form id="mainForm" name="mainForm"
		action="oilEngineStatistics.do?method=statisticsNumber" method="post">
		<table class="formTable">
			<tr>
				<td class="label">
					代维公司
				</td>
				<td class="content">
					<input type="text" id="monitorCompany" name="monitorCompany"
						onfocus="getcity()" alt="allowBlank:false" />
					<input type="hidden" id="monitorCompanyid" name="monitorCompanyid"
						/>
				</td>
				<eoms:xbox id="partnerNameTree"
					dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"
					rootId="" rootText="代维公司树" valueField="monitorCompanyid"
					handler="monitorCompany" textField="monitorCompany"
					checktype="dept" single="true" />

				</td>

			</tr>

			<tr>
				<td class="label">
					地市
				</td>
				<td class="content">
					<select id="cityid" name="cityid" class="ddd"
						onchange="getRegionCity1(this.options[this.options.selectedIndex].value);">
					</select>
				</td>
				<td class="label">
					县区
				</td>
				<td class="content">
					<select id="countryid" name="countryid" class="ccc"
						onchange="getUnderContryInfo(this.options[this.options.selectedIndex].value);">
					</select>
				</td>
			</tr>

			<tr>
				<td class="label">
					驻点名称
				</td>
				<td class="content">
					<input id="residentSiteName" name="residentSiteName" type="hidden"
						value="${residentSiteName}">
					<select id=residentSiteNameid name="residentSiteNameid" class="ddd"
						onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
					</select>
				</td>
				<td class="label">
					发电基站
				</td>
				<td class="content">
					<input id="station" name="station" type="hidden"
						value="${stationid}">
					<select id="stationid" name="stationid" class="ddd"
						onchange="getCityInfo(this.options[this.options.selectedIndex].value);">
					</select>
				</td>

			</tr>

			<tr>
				<td class="label">
					开始时间
				</td>
				<td class="content">
					<input class="text" type="text" name="beginTime"
						value="${beginTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1)"
						alt="vtype:'lessThen',link:'endTime',vtext:'开始时间不能晚于结束时间！',allowBlank:false"
						id="startTime" readonly="readonly" />
				</td>
				<td class="label">
					结束时间
				</td>
				<td class="content">
					<input class="text" type="text" name="endTime" value="${endTime}"
						onclick="popUpCalendar(this, this,null,null,null,true,-1)"
						alt="vtype:'moreThen',link:'startTime',vtext:'结束时间不能早于开始时间！',allowBlank:false"
						id="endTime" readonly="readonly" />
				</td>
			</tr>

		</table>
		<input type="button" name="查询发电次数" value="查询发电次数" onclick="sendBox();" />
</fieldset>
<fieldset>
	<legend>
		请选择统计项目
	</legend>
	<table class="formTable">
		<tr>
			<td class="label">
				地市
			</td>
			<td>
				<input id="city" type="checkbox" name="cardNumber" value="city" />
			</td>
			<td class="label">
				区县
			</td>
			<td>
				<input id="country" type="checkbox" name="cardNumber"
					value="country" />
			</td>
			<td class="label">
				代维公司
			</td>
			<td>
				<input id="monitorcompany" type="checkbox" name="cardNumber"
					value="monitorcompany" />
			</td>
			<td class="label">
				驻点
			</td>
			<td>
				<input id="residentsiteName" type="checkbox" name="cardNumber"
					value="residentsiteName" />
			</td>
			<td class="label">
				基站名称
			</td>
			<td>
				<input id="station" type="checkbox" name="cardNumber"
					value="station" />
			</td>
		</tr>
		<tr>
			<td class="label">
				动环油机告警次数
			</td>
			<td>
				<input id="alarmNumber" type="checkbox" name="cardNumber"
					value="alarmNumber" />
			</td>
			<td class="label">
				代维油机发电总次数
			</td>
			<td>
				<input id="generateNumber" type="checkbox" name="cardNumber"
					value="generateNumber" />
			</td>
			<td class="label">
				普通发电次数次数
			</td>
			<td>
				<input id="NormalGenerateNumber" type="checkbox" name="cardNumber"
					value="NormalGenerateNumber" />
			</td>
			<td class="label">
				应急发电次数
			</td>
			<td>
				<input id="emerGenerateNumber" type="checkbox" name="cardNumber"
					value="emerGenerateNumber" />
			</td>
			<td class="label">
				能耗信息
			</td>
			<td>
				<input id="energyInfo" type="checkbox" name="cardNumber"
					value="energyInfo" />
			</td>
			<input type="hidden" name="deleteIds" id="deleteIds" />
			<input type="hidden" name="checks" id="checks" />
			<input type="hidden" name="checkIds" id="checkIds"
				value="${checkList}" />
		</tr>
		<tr>
			<td colspan="10">
				<font color="red">说明：以下表格所有列是根据选择的统计项目自动生成的</font>
			</td>
		</tr>
	</table>
</fieldset>
<display:table name="resultList" cellspacing="0" cellpadding="0"
	id="resultList" class="table resultList" export="false"
	requestURI="${app}/partner/oilmachine/oilEngineStatistics.do?method=statisticsNumber"
	sort="list" partialList="true" size="resultSize">

	<center>
		<c:if test="${not empty city}">
			<display:column property="city" sortable="true"
				headerClass="sortable" title="地市" paramId="id" paramProperty="id" />
		</c:if>
		<c:if test="${not empty country}">
			<display:column property="country" sortable="true"
				headerClass="sortable" title="区县" paramId="id" paramProperty="id" />
		</c:if>
		<c:if test="${not empty monitorcompany}">
			<display:column property="monitorcompany" sortable="true"
				headerClass="sortable" title="代维公司" paramId="id" paramProperty="id" />
		</c:if>
		<c:if test="${not empty residentsiteName}">
			<display:column property="residentsiteName" sortable="true"
				headerClass="sortable" title="驻点" paramId="id" paramProperty="id" />
		</c:if>
		<c:if test="${not empty station}">
			<display:column property="station" sortable="true"
				headerClass="sortable" title="基站名称" paramId="id" paramProperty="id" />
		</c:if>
		<c:if test="${not empty alarmNumber}">
			<display:column property="alarmNumber" sortable="true"
				headerClass="sortable" title="动环油机告警次数" paramId="id"
				paramProperty="id" />
		</c:if>
		<c:if test="${not empty generateNumber}">
			<display:column property="generateNumber" sortable="true"
				headerClass="sortable" title="代维油机发电总次数" paramId="id"
				paramProperty="id" />
		</c:if>
		<c:if test="${not empty NormalGenerateNumber}">
			<display:column property="NormalGenerateNumber" sortable="true"
				headerClass="sortable" title="普通发电次数次数" paramId="id"
				paramProperty="id" />
		</c:if>
		<c:if test="${not empty emerGenerateNumber}">
			<display:column property="emerGenerateNumber" sortable="true"
				headerClass="sortable" title="应急发电次数" paramId="id"
				paramProperty="id" />
		</c:if>
		<c:if test="${not empty energyInfo}">
			<display:column property="energyInfo" sortable="true"
				headerClass="sortable" title="能耗信息" paramId="id" paramProperty="id" />
		</c:if>
</display:table>
<table>
	</form>
	<script>
  var myjs=jQuery.noConflict();
   

       window.onload = function(){ 
            
          var monitorCompanyid = "${monitorCompanyid}"; //代维公司id 
            if(monitorCompanyid != ''){
              document.forms[0].monitorCompanyid.value = monitorCompanyid ;
              document.forms[0].monitorCompany.value = '${monitorCompany}' ;//代维公司
              document.forms[0].cityid.value = '${cityid}';
          
             
              getRegionCity('${cityid}');
              getUnderContryInfo('${countryid}');
            }else{
              getRegionCity('');   
            }


           
       //   getDept('');       
       //   getRegionCounty('');    
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
				    	
				    	var  residentSiteNameid = '${residentSiteNameid}';    
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
				    	var  stationid = '${stationid}';    
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
						//  document.forms[0].city.value = data[0].area_name ; 						  
						  getRegionCounty(data[0].area_id);	  					
			        }
               );
         
        

   }
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
				return false;
	 }
//		document.getElementById("mainForm").submit();
        document.forms[0].submit();
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
       
   function  test(){
  //  alert('a');
  //  alert(document.forms[0].monitorCompanyid.value);
   }
     function  sub(){    
        var obj=document.getElementsByName("monitorCompanyid")[0];
        var monitorCompany= obj.options[obj.options.selectedIndex].text//
        document.forms[0].monitorCompany.value = monitorCompany ;
        
        var objcity=document.getElementsByName("cityid")[0];
        var city= objcity.options[objcity.options.selectedIndex].text//
        document.forms[0].city.value = city ;
        
        var objcountry=document.getElementsByName("countryid")[0];
        var country= objcountry.options[objcountry.options.selectedIndex].text//
        document.forms[0].country.value = country ;
           
        document.forms[0].submit();
   }
</script>
	<%@ include file="/common/footer_eoms.jsp"%>