<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
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
		var city = document.getElementById("cityId").value;
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
	var region = document.getElementById("cityId").value;
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
    function sendBox() {
		var statisticsItemList = document.getElementsByName("statisticsItem");//获取选中的统计项目的值与实体属性名相对应;
		$("exportFlag").value="1";
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
		if(idResult==""){
			alert("请至少选择一个统计项");
			return false;
		}
		document.getElementById("checkAndSearchFrom").submit();
	}
	//将结果导出为excel文件，先要完成统计
	function toXLSFile(flag){
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
	
	function changeCheckBox(){
	
		var province = myjs('#provinceSearch').val();
		var city=myjs('#cityId').val();
		var district=document.getElementById("country").value;
		var site=myjs('#siteStringLike').val();
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
	}
	//显示勾选框
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
	              selects[i].options[0].selected = true;
	         }
     	}
     	$("hasSend").value="nook";
     	changeCheckBox();
	} 
	Ext.onReady(function(){
		initCountry();
		//显示已经勾选的框
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
	});
	  function clearText(){
			 myjs("#provinceStringLike")[0].value="";
			 myjs("#cityStringLike")[0].value="";
			 myjs("#districtStringLike")[0].value="";
			 myjs("#siteStringLike")[0].value="";
		}
</script>
<form id="checkAndSearchFrom" action="rentCount.do?method=statistics" method="post">
	<fieldset>
		<legend >
			<b>请输入统计条件</b>
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
				<td class="label">
					所属地市
				</td>
				<td class="content">
					<select name="cityId" id="cityId" class="select"  onchange="changeCity(0);">
						<option value="">
							--请选择地市--
						</option>
						<logic:iterate id="cityList" name="cityList">
							<c:if test="${cityId==cityList.areaid}" var="result">
								<option value="${cityList.areaid}" selected="selected" >${cityList.areaname}</option>
							</c:if>
							<c:if test="${!result}">
								<option value="${cityList.areaid}" >${cityList.areaname}</option>
							</c:if>
						</logic:iterate>
					</select>
				</td>
			</tr>
			<tr>
				<td class="label">
					所属区县
				</td>
				<td class="content">
					<select name="country" id="country"  class="select" onchange="changeCheckBox();" >
						<option value="">
							--请选择县区--
						</option>				
					</select>
				</td>
				<td class="label">
					所属物业点
				</td>
				<td class="content">
					<input type="text" name="siteStringLike" id="siteStringLike" value="${siteStringLike}" class="text" onchange="changeCheckBox();">
				</td>
			</tr><%--
		<tr>
				<td class="label">
					年份
				</td>
				<td class="content" colspan="3">
					<select name="timeYearStringLike" class="select" >
						<%
								for(int i=2008;i<=2020;i++) {
						 %>
						 		<option  value="<%=i%>"  >  <%=i%></option>
						<%
								}
						 %>
					</select>
				</td>
			</tr>
		--%>
		</table>
	</fieldset>
	<fieldset>
		<legend>
			<b>请选择统计项目</b>
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
						<input value="time_year" type="checkbox" name="statisticsItem" id="time_year" checked="checked" />
						年份
					</td>
				<td class="label">
					<input value="province" type="checkbox" name="statisticsItem" 	checked="checked" id="provinceTypeLikeArea"  />
					省份
				</td>
				<td class="label">
					<input value="city" type="checkbox" name="statisticsItem" checked="checked" 	id="cityTypeLikeArea" />
					地市
				</td>
				<td class="label">
					<input value="district" type="checkbox" name="statisticsItem" checked="checked"	id="districtTypeLikeArea"  />
					区县
				</td>
			</tr>
			<tr>
				<td class="label">
					<input value="site" type="checkbox" name="statisticsItem"  checked="checked" id="site"  />
					物业点
				</td>
				<td class="label">
					<input value="sum(total_bills)" type="checkbox" name="statisticsItem" checked="checked"  id="total_bills"  />
					应付金额
				</td>
				<td class="label">
					<input value="sum(paid_total_bills)" type="checkbox" name="statisticsItem"  checked="checked" id="paid_total_bills"  />
					已付金额
				</td>
				<td class="label">
					<input value="sum(unpaid_total_bills)" type="checkbox" name="statisticsItem"  checked="checked" id="unpaid_total_bills"  />
					未付金额
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
	<input type="button" name="重置" value="重置" onclick="res222();" />
	<logic-el:present name="tableList">
		<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</logic-el:present>
	<!-- This hidden area is for batch deleting. -->
	<form>
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
	</form>
		<%@ include file="/common/footer_eoms.jsp"%>