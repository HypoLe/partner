<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>
<head>
<base target="_self" />
</head>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
function chooseRes(){
			var tempSelectedAssetIds = document.getElementsByName("selectedAssetId");
			var selectedAssetIds = "";
			var assetInforStr="";
			var count=0;
			for(var i=0;i<tempSelectedAssetIds.length;i++){
				if(tempSelectedAssetIds[i].checked==true){
				selectedAssetIds = selectedAssetIds+tempSelectedAssetIds[i].value+",";
				var obj=jq(tempSelectedAssetIds[i]);
				//assetInforStr +=obj.val()+","+obj.parents("tr").find("td:eq(5)").text()+","+obj.parents("tr").find("td:eq(6)").text()+","+obj.parents("tr").find("td:eq(7)").text()+","+obj.parents("tr").find("td:eq(8) select").val()+","+"#";
				assetInforStr +=obj.val()+","+obj.parents("tr").find("td:eq(5)").text()+","+obj.parents("tr").find("td:eq(6)").text()+","+obj.parents("tr").find("td:eq(7)").text()+","+obj.parents("tr").find("td:eq(8) select").val()+","+";";
				count++;
				}
			}
			
			//alert(assetInforStr);
			if(count > 0){
			//alert(count+"----"+selectedAssetIds);
			
				var quotaVals="${quotaVals}";
				var childSceneId="${childSceneId}";
				var rowIndexValue="${rowIndexValue}";
				var materialType="${materialType}";
				
				var totalCost=jq("#totalCost").val();
				
				//alert(totalCost);
				
				if(materialType=="main"){
					window.dialogArguments.setMainMaterialIds(childSceneId,selectedAssetIds,quotaVals,rowIndexValue,assetInforStr,totalCost);
				}else if(materialType=="assist"){
					window.dialogArguments.setAssistMaterialIds(childSceneId,selectedAssetIds,quotaVals,rowIndexValue,assetInforStr,totalCost);
				}
				
				window.close();
			}else{
				alert("请选择主材！");
			}
		}
		
function selectArchiveAll(obj) {
//alert(33)
        var temp = document.getElementsByName("selectedAssetId"); 
        for (var i =0; i<temp.length; i++) 
        { 
            temp[i].checked = obj.checked; 
        } 
        
       //更新合计数	
		updateTotalCost(); 
        
    } 
    
    function cancelArchive(obj,all) {
        var flag = 0; 
        var all = document.getElementsByName(all)[0]; 
        if (!obj.checked) 
        { 
            all.checked = false; 
        } 
        else 
        { 
            for (var i=0; i<document.getElementsByName(obj.name).length; i++) 
            { 
                if (!document.getElementsByName(obj.name)[i].checked) 
                { 
                    all.checked = false; 
                } 
                else 
                { 
                    flag++; 
                } 
            } 
            if (flag == document.getElementsByName(obj.name).length) 
            { 
                all.checked = true; 
            } 
        }
        
        //更新合计数	
		updateTotalCost(); 
    } 



Ext.onReady(function(){
	    var total="${total}";		
		var mainIds="${mainIds}";
		var temp=document.getElementsByName("selectedAssetId");
		var str= new Array();
		str=mainIds.split(","); 
		for (i=0;i<str.length ;i++ ){
			if(str[i]!=""){
				 for (var j =0; j<temp.length; j++) 
        			{ 
            			if(temp[j].value==str[i]){
            				 temp[j].checked=true;
            				 cancelArchive(temp[j],'archiveAll');
            			}
        			} 
			}
		}
   });
</script>

<script type="text/javascript">
	//是否利旧为YES时,单价置为0
	function updatePerPrice(obj){
		//alert(jq(obj).val());
		var _selectVal=jq(obj).val();
		if(_selectVal=="YES"){
			//alert(	jq(obj).parents("tr").find("td:eq(6)").text());
			jq(obj).parents("tr").find("td:eq(6)").text("0");
		}else if(_selectVal=="NO"){
			//alert(jq(obj).parents("tr").find("[name='bakPerPrice']").val());
			jq(obj).parents("tr").find("td:eq(6)").text(jq(obj).parents("tr").find("[name='bakPerPrice']").val());
		}
		
		//更新合价
		var _num=jq(obj).parents("tr").find("td:eq(5)").text();
		var _perPrice=jq(obj).parents("tr").find("td:eq(6)").text();
		var _totalPriceObj=jq(obj).parents("tr").find("td:eq(7)");
		_totalPriceObj.text(toDecimal(parseFloat(_num)*parseFloat(_perPrice)));
		
		//更新合计数	
		updateTotalCost();	
	}	
	
jq(function(){	
	//判断单价变化：在上下20%浮动
	jq("#materialModelList tbody tr ").find("td:eq(6)").click(function(){
		var _isWhetherOld=jq(this).parents("tr").find("[name='isWhetherOld']").val();
		if(_isWhetherOld=="NO"){
			var td = jq(this); 
		var _originalPerPrice=td.parents("tr").find("[name='originalPerPrice']").val();
		var _bakPerPriceObj=td.parents("tr").find("[name='bakPerPrice']");
		var _num=td.parents("tr").find("td:eq(5)").text();
		var _totalPriceObj=td.parents("tr").find("td:eq(7)");
		//alert(_num);
		//alert(_originalPerPrice);
		var txt = td.text(); 
		var input = jq("<input type='text' style='width:100%;' value='" + txt + "'/>"); 
		td.html(input); 
		input.click(function() { return false; }); 
	
		//获取焦点 
		input.trigger("focus"); 
		
		//文本框失去焦点后提交内容，重新变为文本 <p></p>
		input.blur(function() {
		    var a=/^\d+(\.\d+)?$/; 
			var newtxt = jq(this).val(); 
			//alert("newtxt="+newtxt);
			if (newtxt != txt) {
				if(newtxt==''){
					alert("单价不可以为空!");
					td.html(txt); 
					return;
				}
				if(!a.test(newtxt)){
					alert("请输入大于等于0的数");
					td.html(txt);
				}else{
				
					var lowerValue=toDecimal(parseFloat(_originalPerPrice)*0.8);
					//alert(lowerValue);
					var upperValue=toDecimal(parseFloat(_originalPerPrice)*1.2);
					//alert(upperValue);
					
					if(parseFloat(newtxt)>=parseFloat(lowerValue)&&parseFloat(newtxt)<=parseFloat(upperValue)){
						td.html(newtxt);
						_bakPerPriceObj.val(newtxt);//当切换是否利旧时，回显上一次的单价用的
						_totalPriceObj.text(toDecimal(parseFloat(_num)*parseFloat(newtxt)));
						//更新合计数	
						updateTotalCost();
						return;
					}else{
						alert("超出变化范围,基准值为："+_originalPerPrice);
						td.html(txt);
						return;
					}
				
					
				}
			} 
		else 
		{ 
			td.html(newtxt); 
		} 	
	}); 
}
});
  
  //判断数量变化：不能大于基准值
	jq("#materialModelList tbody tr ").find("td:eq(5)").click(function(){
		 var _materialType="${materialType}";
		 if(_materialType=="assist"){	//要改成辅材的枚举值
		 	var td = jq(this); 
			var _originalNum=td.parents("tr").find("[name='originalNum']").val();
			var _perPrice=td.parents("tr").find("td:eq(6)").text();
			var _totalPriceObj=td.parents("tr").find("td:eq(7)");
			//alert(_perPrice);
			//alert(_originalNum);
			var txt = td.text(); 
			var input = jq("<input type='text' style='width:100%;' value='" + txt + "'/>"); 
			td.html(input); 
			input.click(function() { return false; }); 
		
			//获取焦点 
			input.trigger("focus");
		 	//文本框失去焦点后提交内容，重新变为文本 <p></p>
			input.blur(function() {
			    var a=/^\d+(\.\d+)?$/; 
				var newtxt = jq(this).val(); 
				//alert("newtxt="+newtxt);
				if (newtxt != txt) {
					if(newtxt==''){
						alert("数量不可以为空!");
						td.html(txt); 
						return;
					}
					if(!a.test(newtxt)){
						alert("请输入大于等于0的数");
						td.html(txt);
					}else{
						if(parseFloat(newtxt)<=parseFloat(_originalNum)){
							td.html(newtxt);
							_totalPriceObj.text(toDecimal(parseFloat(_perPrice)*parseFloat(newtxt)));
							//更新合计数	
							updateTotalCost();
							
							return;
						}else{
							alert("超出变化范围,基准值为："+_originalNum);
							td.html(txt);
							return;
						}
					
						
					}
				} 
			else 
			{ 
				td.html(newtxt); 
			} 	
		}); 
		 	
	}
  });	
  
  
  	
});	

//功能：将浮点数四舍五入，取小数点后2位
	 function toDecimal(x) { 
        var f = parseFloat(x);  
        if (isNaN(f)) {  
            return;  
        }  
        f = Math.round(x*100)/100;
        return f; 
    }
  
//更细合计    
function updateTotalCost(){
	
	//alert(jq('input:checkbox[name=selectedAssetId]:checked').length);
	var _checkedLength=jq('input:checkbox[name=selectedAssetId]:checked').length;
	if(_checkedLength!='0'){
		var _totalCost=0;
		jq('input:checkbox[name=selectedAssetId]:checked').each(function(i){
			//alert(jq(this).parents("tr").find("td:eq(7)").text());
		_totalCost+=parseFloat(jq(this).parents("tr").find("td:eq(7)").text());
       
       });	
		jq("#totalCost").val(toDecimal(_totalCost));
	}else{
		jq("#totalCost").val("0");
	}
}
    
 

</script>

<script type="text/javascript">
	//定额唯一时使用 查看主材
	//function checkMainMaterialForDetails(itemNo,processInstanceId){
			//alert(processInstanceId+"--"+itemNo);
			
			//var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkMainMaterialForDetails&processInstanceId='+processInstanceId+'&itemNo='+itemNo;
			//var _sHeight = 300;
		    //var _sWidth = 820;
		    //var sTop=(window.screen.availHeight-_sHeight)/2;
		    //var sLeft=(window.screen.availWidth-_sWidth)/2;
			//var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			//window.showModalDialog(url,window,sFeatures);
			
		//}
	
	//定额唯一时使用 查看辅材
	//function checkAssistMaterialForDetails(itemNo,processInstanceId){
			//alert(processInstanceId+"--"+itemNo);
			
			//var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkAssistMaterialForDetails&processInstanceId='+processInstanceId+'&itemNo='+itemNo;
			//var _sHeight = 300;
		   // var _sWidth = 820;
		   // var sTop=(window.screen.availHeight-_sHeight)/2;
		   // var sLeft=(window.screen.availWidth-_sWidth)/2;
			//var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			//window.showModalDialog(url,window,sFeatures);
			
		//}
		
		
	//定额不唯一时使用 查看主材
function checkMainMaterialForDetails(itemNo, processInstanceId,uniqueId) {
        //alert(processInstanceId+"--"+itemNo);
        var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkMainMaterialForDetails&processInstanceId=' + processInstanceId + '&itemNo=' + itemNo+'&uniqueId='+uniqueId;
        var _sHeight = 300;
        var _sWidth = 820;
        var sTop = (window.screen.availHeight - _sHeight) / 2;
        var sLeft = (window.screen.availWidth - _sWidth) / 2;
        var sFeatures = "dialogHeight: " + _sHeight + "px; dialogWidth: " + _sWidth + "px; dialogTop: " + sTop + "; dialogLeft: " + sLeft + "; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
        window.showModalDialog(url, window, sFeatures);
}
	
	//定额不唯一时使用 查看辅材
function checkAssistMaterialForDetails(itemNo, processInstanceId,uniqueId) {
        //alert(processInstanceId+"--"+itemNo);
        var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=checkAssistMaterialForDetails&processInstanceId=' + processInstanceId + '&itemNo=' + itemNo+'&uniqueId='+uniqueId;
        var _sHeight = 300;
        var _sWidth = 820;
        var sTop = (window.screen.availHeight - _sHeight) / 2;
        var sLeft = (window.screen.availWidth - _sWidth) / 2;
        var sFeatures = "dialogHeight: " + _sHeight + "px; dialogWidth: " + _sWidth + "px; dialogTop: " + sTop + "; dialogLeft: " + sLeft + "; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
        window.showModalDialog(url, window, sFeatures);
}
		
</script>

<script type="text/javascript">
<!--
	function scenarioTemplateExcel(processInstanceId){
		window.location.href="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=scenarioTemplateExcel&processInstanceId="+processInstanceId;
		/*alert(processInstanceId);
		$.ajax({      
		    url:'${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=scenarioTemplateExcel',      
		    type:'post',      
		    data:'processInstanceId='+processInstanceId,      
		    async : false,
		    dateType:"String",    
		    error:function(){      
		       alert('error');      
		    },      
		    success:function(data){      
		       alert('成功');
		    }   
		});
		*/
		
		
	}
//-->
</script>

<!-- <div style="margin-bottom:10px;">子场景-光缆接续</div>
<table class="formTable">
  <tr>
    <td style="background-color: #f7f7f7;vertical-align: top;">数量</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">单位</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">型号</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">定额编号</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">工费单价（打折部分）</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">工费单价（不打折部分）</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">折扣率</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">乙方费用（工费）</td>
    <td style="background-color: #f7f7f7;vertical-align: top;width:5%;">主材</td>
    <td style="background-color: #f7f7f7;vertical-align: top;width:5%;">辅材</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">材料安全生产费</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">材料费</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">总费用</td>
  </tr>
  <tr>
    <td>Row 1: Col 1</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td><a href="javascript:void(0);" onclick="checkMainMaterialForDetails()">查看</a></td>
    <td><a href="javascript:void(0);" onclick="checkAssistMaterialForDetails()">查看</a></td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
    <td>Row 1: Col 2</td>
  </tr>
</table>
 -->
 
<c:if test="${!empty sceneTableList}">			
		   <input type="button" id="export" name="export" value="导出到Excel" onclick="javaScript:scenarioTemplateExcel('${processInstanceId}')">		
</c:if>
  
<c:forEach  items="${sceneTableList}" var="sceneTable" >
	<div style="margin-bottom:10px;">子场景-${sceneTable.childSceneName}</div>
	<table class="formTable">
	<tr>
		<c:forEach items="${sceneTable.tableHeader}" var="tableHeader">
			<td style="background-color: #f7f7f7;vertical-align: top;">${tableHeader}</td>
		</c:forEach>
	</tr>
	
	<c:forEach items="${sceneTable.tableData}" var="ds">
		<tr>
			<c:forEach items="${ds}" var="d">
				<td>${d}</td>
			</c:forEach>
		</tr>
	</c:forEach>
	</table>	
</c:forEach>




	

<%@ include file="/common/footer_eoms.jsp"%>
