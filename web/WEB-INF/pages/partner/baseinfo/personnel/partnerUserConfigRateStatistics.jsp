<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
	<script type="text/javascript" src="${app}/FusionCharts/FusionCharts.js"></script>
<form id="checkAndSearchFrom"	action="statistics.do?method=partnerUserConfigRateStatistics&isPartner=${isPartner}" method="post" >
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable" id="sheet">
			<tr>
				<td class="label">
					省份
				</td>
				<td class="content">
					<select name="provinceSearch" id="provinceSearch" class="select" onchange="changeCheckBox();">
						<option value="">
							--请选择省份--
						</option>
						<logic:iterate id="provinceList" name="provinceList">
							<c:if test="${provinceSearch==provinceList.areaid}" var="result">
								<option value="${provinceList.areaid}" selected="selected" >${provinceList.areaname}</option>
							</c:if>
							<c:if test="${!result}">
								<option value="${provinceList.areaid}" >${provinceList.areaname}</option>
							</c:if>
						</logic:iterate>
					</select>
				</td>
				<td class="label">地市</td>
				<td class="content">
					<select name="region" id="city" class="select"  onchange="changeCity(0);">
						<option value="">
							--请选择地市--
						</option>
						<logic:iterate id="city" name="city">
							<c:if test="${cityId==city.areaid}" var="result">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<c:if test="${!result}">
								<option value="${city.areaid}" >
									${city.areaname}
								</option>
							</c:if>
						</logic:iterate>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label">区县</td>
				<td class="content">
					<input type="hidden" id="country0" value="${country}">
					<select name="country" id="country"  class="select" onchange="changeCheckBox();">
						<option value="">
							请选择县区
						</option>				
					</select>
				</td>
				<td class="label">
					巡检组织&nbsp;
				</td>
				<td class="content">
					<input type="text" name="maintainCompany" class="text"  id="maintainCompany" 
					alt="allowBlank:true" onblur="changeCheckBox()" value="${maintainCompany}"/>
					<input type="hidden" name="maintainCompanyId" id="maintainCompanyId"  value="${maintainCompanyId}" maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight&isPartner=${isPartner}" rootId=""
					rootText='公司组织' valueField="maintainCompanyId" handler="maintainCompany" textField="maintainCompany"
					checktype="dept"  single="true">
					</eoms:xbox>
				</td>
			</tr>
			<tr>
				<td class="label">
					维护专业
				</td>
				<td class="content">
					<eoms:comboBox  name="maintenanceMajor" id="maintenanceMajor"  defaultValue="${major}" initDicId="11225" styleClass="input select"   onchange="changeCheckBox()" />
				</td>
				<td class="label">
					时间
				</td>
				<td class="content">
					<select id="year" name="year" onchange="changeCheckBox();">
						<option value="">
							请选择年
						</option>
						<c:forEach begin="2012" end="2050" var="year">
							<c:choose>
								<c:when test="${year1==year}">
										<option  value="${year}" selected="selected">${year}年</option>
								</c:when>
								<c:otherwise>
									<option  value="${year}">${year}年</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<select id="month" name="month" onchange="changeCheckBox();" >
						<option value="">
							请选择月
						</option>
						<c:forEach begin="1" end="12" var="month">
							<c:choose>
								<c:when test="${month1==month}">
										<option  value="${month}" selected="selected">${month}月</option>
								</c:when>
								<c:otherwise>
									<option  value="${month}">${month}月</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTable">
			<tr>
				<td class="label" colspan="2">
					<input value="add_time_y" type="checkbox" name="statisticsItem" 	id="add_time_y" checked="checked"/>
					年
				</td>
				<td class="label" colspan="2">
					<input value="add_time_m" type="checkbox" name="statisticsItem" 	id="add_time_m" checked="checked"/>
					月
				</td>
				<td class="label" colspan="2">
					<input value="province_id" type="checkbox" name="statisticsItem" 	id="province_idTypeLikeArea" checked="checked"/>
					省份
				</td>
				<td class="label" colspan="2">
					<input value="city_id" type="checkbox" name="statisticsItem" 	id="city_idTypeLikeArea" checked="checked"/>
					地市
				</td>
				<td class="label" colspan="2">
					<input value="country_id" type="checkbox" name="statisticsItem" 	id="country_idTypeLikeArea" checked="checked"/>
					区县
				</td>
			</tr>
			<tr>
				<td class="label" colspan="2">
					<input value="company_name" type="checkbox" name="statisticsItem"	id="company_name" checked="checked"/>
					巡检组织
				</td>
				<td class="label" colspan="2">
					<input value="professional" type="checkbox" name="statisticsItem" id="professionalTypeLikedict" />
					维护专业
				</td>
				<td class="label" colspan="2">
					<input value="sum(standard_amout)" type="checkbox" name="statisticsItem" id="standard_amout" checked="checked" disabled="disabled" />
					应配人数
				</td>
				<td class="label" colspan="2">
					<input value="sum(actual_config)" type="checkbox" name="statisticsItem" id="actual_config" checked="checked" disabled="disabled"/>
					实际人数
				</td>
				<td class="label" colspan="2">
					<input value="sum(config_rate)" type="checkbox" name="statisticsItem" id="config_rate" checked="checked"/>
					到位率
				</td>
			</tr>
			<tr>
				<input type="hidden" name="statisticsItems" id="statisticsItems" />
				<input type="hidden" name="checkedIds" id="checkedIds" />
				<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
				<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
				<input type="hidden" name="exportFlag" id="exportFlag" >
			</tr>
		</table>
	</fieldset>
</form>
	<div>
		<span style="color:red">统计说明:统计的数据均是轮询产生的,数据只有本月以前的数据，当前月的数据要在下月才可以统计</span><br>
	</div>
	<input type="button" name="统计" value="统计" onclick="sendBox()" />
	<input type="button" name="重置" value="重置" onclick="res222()" />
	<c:if test="${!empty tableList}">
		<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</c:if>
	<!-- This hidden area is for batch deleting. -->
		<div>
			<table cellpadding="0" class="table contentStaffList" cellspacing="0">
				<thead>
					<tr>
						<logic-el:present name="headList">
							<c:forEach items="${headList}" var="headlist">
								<th>
									${headlist}
								</th>
							</c:forEach>
						</logic-el:present>
					</tr>
				</thead>
				<logic-el:present name="tableList">
					<tbody>
						<input type="hidden" name="year" value="${year1}"/>
						<input type="hidden" name="month" value="${month1}"/>
						<c:forEach items="${tableList}" var="tdList">
							<tr>
								<c:forEach items="${tdList}" var="td">
									<c:if test="${td.show}">
										<td rowspan="${td.rowspan}">
											<c:if test="${!empty td.url}">
												<a href="javascript:void(0);"	onclick="window.open('${app}${td.url}');">${td.name}</a>
											</c:if>
											<c:if test="${empty td.url}">
										    	${td.name}
										    </c:if>
										</td>
									</c:if>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</logic-el:present>
			</table>
		</div>
	<c:if test="${!empty tableList}">
		<div id="fusionchart"></div>
	</c:if>
	
	</div>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    function res222(){
		var formElement=document.getElementById("checkAndSearchFrom")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
		          inputs[i].value = '';
	         }
	          if(inputs[i].type == 'checkbox'){
	          	if(inputs[i].id!="standard_amout"&&inputs[i].id!="actual_config"){
	             	inputs[i].checked =false;
	             }
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              	selects[i].options[0].selected = true;
	         }
     	}
     	$("hasSend").value="nook";
     	changeCheckBox();
	}
    function sendBox() {
    	$("exportFlag").value="1";//执行的是统计操作
		var statisticsItemList = document.getElementsByName("statisticsItem");//获取选中的统计项目的值与实体属性名相对应
		var idResult = "";
		var checkedIds="";
		for (i = 0 ; i < statisticsItemList.length; i ++) {
			if (statisticsItemList[i].checked) {
				var itemV=statisticsItemList[i].value;
				idResult+=itemV.toString()+";" ;
				var checkedId=statisticsItemList[i].id;
				checkedIds+=checkedId.toString()+";";
			}
		}
		$("statisticsItems").value=idResult.toString();//拼接被勾选的统计项目的value其值包含"TypeLikedict"标识；
		$("checkedIds").value=checkedIds.toString();//获取选中的统计项目的值与实体属性数据库名相对应
		if(idResult==""){
			alert("请至少选择一个统计项");
			return false;
		}
		document.getElementById("checkAndSearchFrom").submit();
	}
	function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	         for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
        }
	/**	
	* 页面初始化地市下的区县
	*/
	function initCountry(){
		delAllOption("country");
		var city = document.getElementById("city").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
		Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
					res = "[{"+result.responseText;
				}
				if(res.indexOf("<\/SCRIPT>")>0){
			  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
				}
				var json = eval(res);
				var countyName = "country";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				var country = "${country}";
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					if(arrOptions[i]==country){
						obj.options[j].selected=true;
					}
					j++;
					i++;
				}
				changeCheckBox();	
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
	}
//地区、区县连动
function changeCity(con){    
   delAllOption("country");//地市选择更新后，重新刷新县区
   changeCheckBox();//检查是否选中
	var region = document.getElementById("city").value;
	var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
	//var result=getResponseText(url);
	Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("[{")!=0){
 								res = "[{"+result.responseText;
						}
						if(res.indexOf("<\/SCRIPT>")>0){
					  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						var json = eval(res);
						var countyName = "country";
						var arrOptions = json[0].cb.split(",");
						var obj=$(countyName);
						var i=0;
						var j=0;
						for(i=0;i<arrOptions.length;i++){
							var opt=new Option(arrOptions[i+1],arrOptions[i]);
							obj.options[j]=opt;
							j++;
							i++;
						}
						if(con==1){				
							var city = '${gridForm.city}';
							var partnerid = '${gridForm.partnerid}';
							if(city!=''){
								document.getElementById("city").value = city;
							}
							if(partnerid!=''){
								changePartner(1);								
                            }	
						}
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}
	function changeCheckBox(){
		var year = myjs('#year').val();
		var month = myjs('#month').val();
		var province = myjs('#provinceSearch').val();
		var city = myjs('#city').val();
		var country = myjs('#country').val();
		var company=myjs('#maintainCompany').val();
		var major=myjs('#maintenanceMajor').val();
		if(year){
			myjs('#add_time_y').attr('checked',true);
			myjs('#add_time_y').attr('disabled','disabled');
		}else{
			if((myjs('#add_time_y').attr('disabled'))){
			     myjs('#add_time_y').attr('checked',false);
			     myjs('#add_time_y').attr('disabled','');
			}
		}
		if(month){
			myjs('#add_time_m').attr('checked',true);
			myjs('#add_time_m').attr('disabled','disabled');
		}else{
			if((myjs('#add_time_m').attr('disabled'))){
			     myjs('#add_time_m').attr('checked',false);
			     myjs('#add_time_m').attr('disabled','');
			}
		}
		if(province){
			myjs('#province_idTypeLikeArea').attr('checked',true);
			myjs('#province_idTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#province_idTypeLikeArea').attr('disabled'))){
			     myjs('#province_idTypeLikeArea').attr('checked',false);
			     myjs('#province_idTypeLikeArea').attr('disabled','');
			}
		}
		if(city){
			myjs('#city_idTypeLikeArea').attr('checked',true);
			myjs('#city_idTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#city_idTypeLikeArea').attr('disabled'))){
			     myjs('#city_idTypeLikeArea').attr('checked',false);
			     myjs('#city_idTypeLikeArea').attr('disabled','');
			}
		}
		if(country){
			myjs('#country_idTypeLikeArea').attr('checked',true);
			myjs('#country_idTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#country_idTypeLikeArea').attr('disabled'))){
			     myjs('#country_idTypeLikeArea').attr('checked',false);
			     myjs('#country_idTypeLikeArea').attr('disabled','');
			}
		}
		if(company){
			myjs('#company_name').attr('checked',true);
			myjs('#company_name').attr('disabled','disabled');
		}else{
			if((myjs('#company_name').attr('disabled'))){
			    myjs('#company_name').attr('checked',false);
			    myjs('#company_name').attr('disabled','');
			}
		}
		if(major){
			myjs('#professionalTypeLikedict').attr('checked',true);
			myjs('#professionalTypeLikedict').attr('disabled','disabled');
		}else{
			if((myjs('#professionalTypeLikedict').attr('disabled'))){
			    myjs('#professionalTypeLikedict').attr('checked',false);
			    myjs('#professionalTypeLikedict').attr('disabled','');
			}
		}
	}
	Ext.onReady(function(){
		initCountry();
		var check=document.getElementById("checkedList");
		if(check&&check.value!=""){
			//先清空所有的勾选框
			var all=document.getElementsByName("statisticsItem");
			for (i = 0 ; i <all.length; i ++) {
				var checkValue="#"+all[i].id;
				myjs(checkValue).attr('checked',false);
			}
			checkV=check.value;
			var checks=checkV.toString().split(";");
			for(var i=0;i<checks.length-1 ;i++){
				//alert(checks[i].toString());
				checkValue ='#'+checks[i].toString();
				myjs(checkValue).attr('checked',true);
			}
		}
		//显示报表图形
		var list='${tableList}';
		var checkedList='${checkedList}';
		if (list!=''){
			var width = '${width}';
	    	 if(width!=null||width!=""){
			    var myChart = new FusionCharts("${app}/FusionCharts/MSColumn3D.swf", "fusionchart", "1000", "480","0","0");
			    myChart.setDataXML("${xml}");
				myChart.render("fusionchart");
			}
    	 }
	});
	//将结果导出为excel文件，先要完成统计
	function toXLSFile(){
		var hasSend=$("hasSend").value;
		$("exportFlag").value="2";
		if(hasSend=="ok"){ //是否完成统计
			var statisticsItemList = document.getElementsByName("statisticsItem");//获取选中的统计项目的值与实体属性名相对应;
			var idResult = "";
			var checkedIds="";
			for (i = 0 ; i < statisticsItemList.length; i ++) {
				if (statisticsItemList[i].checked) {
					var itemV=statisticsItemList[i].value;
					idResult+=itemV.toString()+";" ;
					var checkedId=statisticsItemList[i].id;
					checkedIds+=checkedId.toString()+";";
				}
			}
			$("statisticsItems").value=idResult.toString();//拼接被勾选的统计项目的value其值包含"TypeLikedict"标识；
			$("checkedIds").value=checkedIds.toString();//获取选中的统计项目的值与实体属性数据库名相对应;
			document.getElementById("checkAndSearchFrom").submit();
		}else{
		 	alert("请先完成统计!")
			return;
		}
	};
	
</script>
<%@ include file="/common/footer_eoms.jsp"%>