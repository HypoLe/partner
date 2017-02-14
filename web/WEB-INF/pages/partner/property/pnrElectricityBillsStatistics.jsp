<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
	<script type="text/javascript" src="${app}/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    
    function sendBox() {
    	$("exportFlag").value="1";
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
		var reg="bills";
		if(idResult.match(reg)==null){
			alert("请至少选择一个月统计");
			return false;
		}
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var province = myjs('#provinceSearch').val();
		var city=myjs('#citySearch').val();
		var district=myjs('#country').val();
		var site=myjs('#siteStringLike').val();
		var year=myjs('#timeYearStringLike').val();
		if(province){
			myjs('#provinceTypeLikeArea').attr('checked',true);
			myjs('#provinceTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#provinceTypeLikeArea').attr('disabled'))){
			     myjs('#provinceTypeLikeArea').attr('checked',false);
			     myjs('#provinceTypeLikeArea').attr('disabled','');
			}
		}
		if(city){
			myjs('#cityTypeLikeArea').attr('checked',true);
			myjs('#cityTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#cityTypeLikeArea').attr('disabled'))){
			    myjs('#cityTypeLikeArea').attr('checked',false);
			    myjs('#cityTypeLikeArea').attr('disabled','');
			}
		}
		if(district){
			myjs('#districtTypeLikeArea').attr('checked',true);
			myjs('#districtTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#districtTypeLikeArea').attr('disabled'))){
			    myjs('#districtTypeLikeArea').attr('checked',false);
			    myjs('#districtTypeLikeArea').attr('disabled','');
			}
		}
		if(site){
			myjs('#site').attr('checked',true);
			myjs('#site').attr('disabled','disabled');
		}else{
			if((myjs('#site').attr('disabled'))){
			    myjs('#site').attr('checked',false);
			    myjs('#site').attr('disabled','');
			}
		}
		if(year){
			myjs('#time_year').attr('checked',true);
			myjs('#time_year').attr('disabled','disabled');
		}else{
			if((myjs('#time_year').attr('disabled'))){
			    myjs('#time_year').attr('checked',false);
			    myjs('#time_year').attr('disabled','');
			}
		}
	}
	var myjs=jQuery.noConflict();
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
		var city = document.getElementById("citySearch").value;
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
	var region = document.getElementById("citySearch").value;
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
								document.getElementById("citySearch").value = city;
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
	//显示勾选框和统计图像
	Ext.onReady(function(){
		//显示已经勾选的框
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
		var width = '${width}';
    	 if(width!=null||width!=""){
		    var myChart = new FusionCharts("${app}/FusionCharts/MSColumn3D.swf", "fusionchart", "1000", "480","0","0");
		    myChart.setDataXML("${xml}");
			myChart.render("fusionchart");
    	 }
	});
	//将结果导出为excel文件，先要完成统计
	function toXLSFile(){
		var hasSend=$("hasSend").value;
		$("exportFlag").value="2";
		//先核对前后的数据是否相同,如果前后数据不相同时，要将提示先完成统计
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
	function checkIsChecked(){
		 var c=$("site").checked;
		 var siteValue=$("siteStringLike").value;
		 if(c&&siteValue==""){
		 	alert("您当前统计的是所有的站点，统计信息过多会降低统计图表质量!\n\n建议统计某个站点");
		 }
	}
	function res222(){
		var formElement=document.getElementById("checkAndSearchFrom")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	     for(var i=0;i<inputs.length;i++){
	         if(inputs[i].type == 'text'){
		          inputs[i].value = '';
	         }
	          if(inputs[i].type == 'checkbox'){
	             inputs[i].checked =false;
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	         	  if(selects[i].id!="timeYearStringLike"){
	              	selects[i].options[0].selected = true;
	              }
	         }
     	}
     	$("hasSend").value="nook";
     	changeCheckBox();
	} 
</script>
<form id="checkAndSearchFrom"	action="electricityCount.do?method=statistics" method="post">
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
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
					<select name="citySearch" id="citySearch" class="select"  onchange="changeCity(0);">
						<option value="">
							--请选择地市--
						</option>
						<logic:iterate id="cityList" name="cityList">
							<c:if test="${cityId==cityList.areaid}" var="result">
								<option value="${cityList.areaid}" selected="selected" >
									${cityList.areaname}
								</option>
							</c:if>
							<c:if test="${!result}">
								<option value="${cityList.areaid}" >
									${cityList.areaname}
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
					所属站点
				</td>
				<td class="content">
					<input type="text" name="siteStringLike" id="siteStringLike" value="${siteStringLike}" class="text" onblur="changeCheckBox();">
				</td>
			</tr>
			<tr>
				<td class="label">
					年份
				</td>
				<td class="content" colspan="4">
					<select  name="timeYearStringLike" id="timeYearStringLike" class="select">
						<c:forEach  var="year" begin="2012" end="2050">
							<c:choose>
								<c:when test="${timeYear==year}">
										<option  value="${year}" selected="selected">${year}年</option>
								</c:when>
								<c:otherwise>
									<option  value="${year}">${year}年</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
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
				<td class="label">
					<input value="time_year" type="checkbox" name="statisticsItem" id="time_year" checked="checked" disabled="disabled" />
					年份
				</td>
				<td class="label">
					<input value="province" type="checkbox" name="statisticsItem" 	id="provinceTypeLikeArea"  checked="checked"/>
					省份
				</td>
				<td class="label">
					<input value="city" type="checkbox" name="statisticsItem" 	id="cityTypeLikeArea" checked="checked"/>
					地市
				</td>
				<td class="label">
					<input value="district" type="checkbox" name="statisticsItem" 	id="districtTypeLikeArea" checked="checked" />
					区县
				</td>
				<td class="label">
					<input value="site" type="checkbox" name="statisticsItem" id="site" onclick="checkIsChecked();" />
					站点
				</td>
			</tr>
			<tr>
				<td class="label">
					<input value="sum(january_bills)" type="checkbox" name="statisticsItem" id="january_bills" checked="checked"  />
					1月
				</td>
				<td class="label">
					<input value="sum(february_bills)" type="checkbox" name="statisticsItem" id="february_bills" checked="checked"  />
					2月
				</td>
				<td class="label">
					<input value="sum(march_bills)" type="checkbox" name="statisticsItem" id="march_bills" checked="checked"  />
					3月
				</td>
				<td class="label">
					<input value="sum(april_bills)" type="checkbox" name="statisticsItem" id="april_bills" checked="checked"  />
					4月
				</td>
				<td class="label">
					<input value="sum(may_bills)" type="checkbox" name="statisticsItem" id="may_bills" checked="checked"  />
					5月
				</td>
			</tr>
			<tr>
				<td class="label">
					<input value="sum(june_bills)" type="checkbox" name="statisticsItem" id="june_bills" checked="checked"  />
					6月
				</td>
				<td class="label">
					<input value="sum(july_bills)" type="checkbox" name="statisticsItem" id="july_bills" checked="checked"  />
					7月
				</td>
				<td class="label">
					<input value="sum(august_bills)" type="checkbox" name="statisticsItem" id="august_bills" checked="checked"  />
					8月
				</td>
				<td class="label">
					<input value="sum(september_bills)" type="checkbox" name="statisticsItem" id="september_bills" checked="checked"  />
					9月
				</td>
				<td class="label">
					<input value="sum(october_bills)" type="checkbox" name="statisticsItem" id="october_bills" checked="checked"  />
					10月
				</td>
			</tr>
			<tr>
				<td class="label">
					<input value="sum(november_bills)" type="checkbox" name="statisticsItem" id="november_bills" checked="checked"  />
					11月
				</td>
				<td class="label" colspan="7">
					<input value="sum(december_bills)" type="checkbox" name="statisticsItem" id="december_bills" checked="checked"  />
					12月
				</td>
				<input type="hidden" name="statisticsItems" id="statisticsItems" />
				<input type="hidden" name="checkedIds" id="checkedIds" />
				<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
				<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
				<input type="hidden" name="exportFlag" id="exportFlag"  >
			</tr>
		</table>
	</fieldset>
</form>
	<input type="button" name="统计" value="统计" onclick="sendBox()" />&nbsp;
	<input type="button" name="重置" value="重置" onclick="res222()" />
	<logic-el:present name="tableList">
	<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</logic-el:present>
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
			<div id="fusionchart" style="z-index: -1">
			</div>
		</c:if>
		<%@ include file="/common/footer_eoms.jsp"%>