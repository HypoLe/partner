<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<!-- <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script> -->

<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery-1.4.3.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jqueryMultiSelect/jquery.multiSelect.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery.bgiframe.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jqueryMultiSelect/jquery.multiSelect.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
jq(document).ready( function() {
	//初始化所有多选下拉框
   	 jq("#cityId").multiSelect({    
        selectAll: true,   
        oneOrMoreSelected: '*',   
        selectAllText: '全选',   
        noneSelected: '请选择' 
        }, function(){   //回调函数   
        
   	 });
});

function refresh(){
	window.location.href = "${app}/partner/res/PnrResConfig.do?method=openCollectResourceView";
} 

function collectResourceInventoryData() {
	var limitedNumber=jq("#limitedNumber").val();
	var cityIds="";  
	var cityNames="";
	jq("input[name='cityId_check']:checked").each(function(){  
		cityIds+=jq(this).val()+",";  
	}); 
	if(cityIds==""){
		alert("地市不能为空，请选择地市!");
		return;
	}else{
		cityIds=cityIds.substring(0,cityIds.length-1);
		
		jq("input[name='cityId_check']:checked").each(function(){  
			cityNames+=jq(this).parents("label").text()+",";  
		});
		cityNames=cityNames.substring(0,cityNames.length-1);
	}
	//alert("cityIds="+cityIds);
	//alert("cityNames="+cityNames);
	
    Ext.Msg.wait("保存中，请稍后...", "保存中...");
    var url = "${app}/partner/res/PnrResConfig.do?method=collectResourceInventoryData&limitedNumber="+limitedNumber+"&cityIds="+cityIds+"&cityNames="+encodeURI(encodeURI(cityNames));
    Ext.Ajax.request({
        url: url,
        method: 'POST',
        success: function(result, request) {
            res = Ext.util.JSON.decode(result.responseText);
            Ext.Msg.hide();
            if(res[0].resultMsg=="notCollecting"){
            	Ext.MessageBox.alert('提示', '资源数据'+res[0].totalNumber+'条,超出限制条数。',function(){ 
					refresh();
				});
            }else{
      			Ext.MessageBox.alert('提示', '同步数据已完成,共' + res[0].total+'条。',function(){ 
					refresh();
				});
            }
            //console.info(res[0].total);
          
            //Ext.MessageBox.hide();
        },
        failure: function(result, request) {
            Ext.MessageBox.alert('Failed', '操作失败' + result.responseText);
        }
    });
    
    // refresh();//刷新日志列表
}
</script>
<body>
	<table class="listTable" style="width:40%">
		<tr>
			<td width="20%">地市</td>
			<td  class="content">
				<select id="cityId" name="cityId" multiple="multiple" style="width:140px;">
					<option value="1">济南</option>
					<option value="2">青岛</option>
					<option value="3">烟台</option>
					<option value="4">潍坊</option>
					<option value="5">淄博</option>
					<option value="6">威海</option>
					<option value="7">济宁</option>
					<option value="8">泰安</option>
					<option value="9">德州</option>
					<option value="10">临沂</option>
					<option value="11">枣庄</option>
					<option value="12">菏泽</option>
					<option value="13">聊城</option>
					<option value="14">滨州</option>
					<option value="15">东营</option>
					<option value="16">日照</option>
					<option value="17">莱芜</option>
				</select>
			</td>
			</tr>
			<tr>
			<td >采集条数限制</td>
			<td  class="content">
				<select id="limitedNumber" name="limitedNumber" styleClass="select" style="width:160px;">
					<!-- <option value="2">2条</option> -->
					<option value="10000">10000条</option>
					<option value="20000">20000条</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" width="20%"><input type="button" id="collectButton" name="collectButton" value="点击采集" class="btn" onclick="collectResourceInventoryData()"/></td>
		</tr>
	</table>

	<br /><br />
	<!-- 显示成功日志 -->
	<display:table name="taskList" cellspacing="0" cellpadding="0"
		id="taskList" pagesize="20" class="listTable taskList"
		export="false" requestURI="PnrResConfig.do" sort="list"
		size="total" partialList="true">
		
		<display:column sortable="false" headerClass="sortable" title="账号">
			<eoms:id2nameDB id="${taskList.userId}" beanId="tawSystemUserDao"/>
		</display:column>
		<display:column sortable="true"	headerClass="sortable" title="时间" property="insertTime" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
		<display:column sortable="false" headerClass="sortable" title="地市名" property="cityNames" />
		<display:column sortable="false" headerClass="sortable" title="插入成功条数" property="insertSuccessNum" />
		<display:column sortable="false" headerClass="sortable" title="资源库查询条数" property="zyglQueryNum" />
		
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</body>
<%@ include file="/common/footer_eoms.jsp"%>
