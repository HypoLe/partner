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
	function res(){
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
		var province = myjs('#provinceIdStringLike').val();
		var city=myjs('#cityIdStringLike').val();
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
						<c:set var="boxData">[{id:'${provinceIdStringLike}',name:'<eoms:id2nameDB id="${provinceIdStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
						<input type="text" name="provinceIdStringLike" id="provinceIdStringLike"    class="text medium" readonly="readonly"	onblur="changeCheckBox()"/>
						<input type="hidden" name="provinceid" id="provinceid"   class="text medium"/>
						<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属省份' valueField="provinceid"
						 handler="provinceIdStringLike" 	textField="provinceIdStringLike" data="${boxData}" checktype="" single="true">
						</eoms:xbox>
					</td>
					<td class="label">
						地市
					</td>
					<td class="content">
						<c:set var="boxData">[{id:'${cityIdStringLike}',name:'<eoms:id2nameDB id="${cityIdStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
						<input type="text" name="cityIdStringLike" id="cityIdStringLike"    class="text medium"readonly="readonly"	onblur="changeCheckBox()"/>
						<input type="hidden" name="cityid" id="cityid"   class="text medium"/>
						<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属地市' valueField="cityid"
						 handler="cityIdStringLike" 	textField="cityIdStringLike" data="${boxData}" checktype="" single="true">
						</eoms:xbox>
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
	</fieldset>
	<input type="button" name="统计" value="统计" onclick="sendBox()"/>
	<input type="button" name="重置" value="重置" onclick="res();"/>
		<logic-el:present name="tableList">
	<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</logic-el:present>
	</form>
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