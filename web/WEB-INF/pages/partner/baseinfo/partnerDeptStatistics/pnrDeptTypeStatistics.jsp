<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    //显示勾选框和统计图像
	Ext.onReady(function(){
		//显示已经勾选的框
		var check="${checkedIdsStr}";
		if(check!=""){
			//先清空所有的勾选框
			var all=document.getElementsByName("statisticsItem");
			for (i = 0 ; i <all.length; i ++) {
				var checkValue="#"+all[i].id;
				myjs(checkValue).attr('checked',false);
			}
			var checks=check.toString().split(";");
			for(var i=0;i<checks.length-1 ;i++){
				//alert(checks[i].toString());
				checkValue ='#'+checks[i].toString();
				myjs(checkValue).attr('checked',true);
			}
		}
		changeCheckBox();
		//显示报表图形
		/*
		var width = '${width}';
    	 if(width!=null||width!=""){
		    var myChart = new FusionCharts("${app}/FusionCharts/MSColumn3D.swf", "fusionchart", "1000", "480","0","0");
		    myChart.setDataXML("${xml}");
			myChart.render("fusionchart");
    	 }*/
	});
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
		var reg="comtype_";
		if(idResult==""){
			alert("请至少选择一个统计项目");
			return false;
		}
		document.getElementById("checkAndSearchFrom").submit();
	}
	function res222(){
		var formElement=document.getElementById("checkAndSearchFrom")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	   	 document.getElementById("provinceid").value="";
	   	 document.getElementById("cityid").value="";
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
	              selects[i].options[0].selected = true;
	         }
     	}
     	$("hasSend").value="nook";
     	changeCheckBox();
	}
	function changeCheckBox(){
		var province = myjs('#provinceid').val();
		var city=myjs('#cityid').val();
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
	}
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
		delAllOption("countryid");
		var city = document.getElementById("cityid").value;
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
				var countyName = "countryid";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				var country = "${countryid}";
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
   delAllOption("countryid");//地市选择更新后，重新刷新县区
   changeCheckBox();//检查是否选中
	var region = document.getElementById("cityid").value;
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
						var countyName = "countryid";
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
</script>
<%@ include file="/common/body.jsp"%>
<form  id="checkAndSearchFrom" action="pnrDeptStatistics.do?method=pnrDetpTypeStatistics" method="post">
	<fieldset>
				<legend>请输入统计条件</legend>
	<table class="formTable">
		<tr>
				<td class="label">
					省份
				</td>
				<td class="content">
					<select name="provinceid" id="provinceid" class="select" onchange="changeCheckBox();">
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
					<select name="cityid" id="cityid" class="select"  onchange="changeCheckBox();">
						<option value="">
							--请选择地市--
						</option>
						<logic:iterate id="city" name="city">
							<c:if test="${cityid==city.areaid}" var="result">
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
		</table>
		</fieldset>
	<fieldset>
			<legend>请选择统计项目</legend>
	<table class="formTable">
		<tr>
			<td class="label"><input  value="province_id" type="checkbox" name="statisticsItem" id="province_idTypeLikeArea" checked="true" />&nbsp;&nbsp;省份</td>
			<td class="label"><input 	value="city_id" type="checkbox" name="statisticsItem" id="city_idTypeLikeArea" checked="true" />&nbsp;&nbsp;地市</td>
			<td class="label"><input  value="sum(comtype_1)" type="checkbox" name="statisticsItem" id="comtype_1"  checked="true"/>&nbsp;&nbsp;国有企业</td>
			<td class="label"><input  value="sum(comtype_2)" type="checkbox" name="statisticsItem" id="comtype_2" checked="true" />&nbsp;&nbsp;集体企业</td>
			<td class="label"><input  value="sum(comtype_3)" type="checkbox" name="statisticsItem" id="comtype_3" checked="true" />&nbsp;&nbsp;联营企业</td>
		</tr>
		<tr>
			<td class="label"><input  value="sum(comtype_4)" type="checkbox" name="statisticsItem" id="comtype_4"  checked="true" />&nbsp;&nbsp;股份合作制企业</td>
			<td class="label"><input  value="sum(comtype_5)" type="checkbox" name="statisticsItem" id="comtype_5" checked="true"/>&nbsp;&nbsp;私营企业</td>
			<td class="label"><input  value="sum(comtype_6)" type="checkbox" name="statisticsItem" id="comtype_6" checked="true"/>&nbsp;&nbsp;外商独资企业</td>
			<td class="label"><input  value="sum(comtype_7)" type="checkbox" name="statisticsItem" id="comtype_7" checked="true"/>&nbsp;&nbsp;有限责任公司</td>
			<td class="label"><input  value="sum(comtype_8)" type="checkbox" name="statisticsItem" id="comtype_8" checked="true"/>&nbsp;&nbsp;股份有限公司</td>
			<input type="hidden" name="statisticsItems" id="statisticsItems" />
			<input type="hidden" name="checkedIds" id="checkedIds" />
			<input type="hidden" name="checkedIdsStr" id="checkedIdsStr" value="${checkedIdsStr}" />
			<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
			<input type="hidden" name="exportFlag" id="exportFlag"  >
		</tr>
	</table>
		<input type="button" name="统计" value="统计" onclick="sendBox()"/>
		<input type="button" name="重置" value="重置" onclick="res222();"/>
			<logic-el:present name="tableList">
		<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
		</logic-el:present>
	</fieldset>
	</form>
	<br>
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
<%@ include file="/common/footer_eoms.jsp"%>