<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="JavaScript"
	src="<%=request.getContextPath()%>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript">
Ext.onReady(function(){
				var factoryViewer = new Ext.JsonView("citiesView",
					'<div class="viewlistitem-{nodeType}">{name}</div>',
					{ 
						emptyText : '<div>没有选择项目</div>'
				}
				);
				var data = "[]";
				factoryViewer.jsonData = eoms.JSONDecode(data);
				factoryViewer.refresh();
									 
				//area tree
	            var	factoryTreeAction='${app}/xtree.do?method=dict';
	            factoryTree = new xbox({

    	          btnId:'showCities',

    	          treeDataUrl:factoryTreeAction,treeRootId:'1012503',treeRootText:'城市',treeChkMode:'',treeChkType:'dict',
    	          showChkFldId:'showCities',saveChkFldId:'cities',viewer:factoryViewer
	            });
});


function checkValue(evt){
		if (!evt)evt = window.event;
		
		if ($('styleId').value=='compareCountry'){
			if($('city').value==''){
				alert("此模式下请选择分公司");
				return false;
			}
		}
		document.forms[0].submit();
		//if(confirm("确认查看报表吗?")){
		//	document.forms[0].submit();
		//}else{
		//	return false;
		//}
		//return false;
}

function myChange(myValue){
	var v1=eoms.form;
	if(myValue=="SINGLELINE"){
		v1.disableArea('forMultiple',true);
		v1.disableArea('citiesChoose',true);
	}
	if(myValue=="MULTIPLELINE"){
		v1.enableArea('forMultiple');
	}
}

function myChange1(myValue){
	var v1=eoms.form;
	if(myValue=="compareCity"){
		v1.disableArea('forSingle',true);
		v1.enableArea('citiesChoose');
		v1.disableArea('chartArea',true);
	}
	if(myValue=="compareMonitor"){
		v1.disableArea('citiesChoose',true);
		v1.enableArea('forSingle');
	}
}

</script>

<html:form action="circuit.do?method=getHundredCharts" method="post"
	styleId="theform">
	<table id="sheet" class="formTable">
		<!-- 配置图形样式的接口 -->
		<input type="hidden" name="singleLineShape" id="singleLineShape"
			value="Line.swf" />

		<tr>
			<td class="label">默认显示数据为</td>
			<td colspan="3" style="color: red;font: bold;">上个月所有市级单位的“百公里阻断历时(小时)”指标对比</td>
		</tr>
		<tr>
			<td class="label">报表类型</td>
			<td colspan="3"><select size='1' name='styleId' id='styleId'
				class='select'>
				<option value="compareCity" selected="selected">分公司单位数据对比</option>
				<option value="compareCountry">县级单位对比</option>
				<option value="compareMonitor">代维公司数据对比</option>
				<option value="compareFaultDuring">故障历时数据对比</option>
			</select></td>
		</tr>

		<!-- 单线图的统计域 -->
		<tr id="forSingle">
			<td class="label">分公司</td>
			<td class="content"><eoms:comboBox name="city" id="city"
				sub="country" initDicId="1012503" alt="allowBlank:false" /></td>
			<td class="label">归属县公司*</td>
			<td class="content"><eoms:comboBox name="country" id="country"
				alt="allowBlank:false" /></td>
		</tr>
		<tr>
			<td class="label">开始年份</td>
			<td class="content"><select size='1' name='startYear'
				id='startYear' class='select'>
				<option value='2009'>2009</option>
				<option value='2010'>2010</option>
				<option value='2011' selected="selected">2011</option></td>
			<td class="label">开始月份</td>
			<td class="content"><select size='1' name='startMonth'
				id='startMonth' class='select'>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">结束年份</td>
			<td class="content"><select size='1' name='endYear' id='endYear'
				class='select'>
				<option value='2009'>2009</option>
				<option value='2010'>2010</option>
				<option value='2011' selected="selected">2011</option></td>
			<td class="label">结束月份</td>
			<td class="content"><select size='1' name='endMonth'
				id='endMonth' class='select'>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>

		<tr id="citiesChoose" style="display:none">
			<td class="label">选择城市</td>
			<td colspan="3">
			<div id="citiesView" class="hide"></div>
			<textarea cols="100" rows="2" readonly="true" name="showCities"
				style="height:50px" id="showCities"
				alt="allowBlank:false,maxLength:100"><eoms:id2nameDB
				id="${cities}" beanId="tawSystemAreaDao" /></textarea> <input
				type="hidden" name="cities" id="cities" /></td>
		</tr>


	</table>
	<input type="button" class="submit" value="生成报表"
		onclick="checkValue(this)" />
</html:form>


<c:if test="${ info == 'noData' }">
	<br/><br/>
	无数据
</c:if>
<c:if test = "${ info != 'noData' }">
	<div><!-- 图表呈现域 -->
<table align="center">
	<tr id="chartArea">
		<%
			//String doc = (String) request.getAttribute("doc");
			//System.out.print(doc);
		%>
		<td><!-- javascript 嵌入 -->
		<div id="chartdiv" align="center"></div>
		<script type="text/javascript">
	    	        // function(swf, id, w, h, debugMode, registerWithJS, c, scaleMode, lang, detectFlashVersion, autoInstallRedirect)
	                var myChart = new FusionCharts("<%=request.getContextPath()%>/FusionCharts/${presentStyle}", "myChartId",document.body.clientWidth*0.8, document.body.clientHeight*0.8);
					myChart.setDataXML("${doc}");
	                myChart.render("chartdiv");	       
               </script></td>
	</tr>
</table>
</div>
</c:if>
<%@ include file="/common/footer_eoms.jsp"%>
