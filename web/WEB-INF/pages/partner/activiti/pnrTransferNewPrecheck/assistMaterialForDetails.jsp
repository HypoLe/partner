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

<!-- <table class="formTable">
  <tr>
    <td style="background-color: #f7f7f7;vertical-align: top;">序号</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">名称</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">规格程式</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">单位</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">数量</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">单价</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">合价</td>
    <td style="background-color: #f7f7f7;vertical-align: top;">材料是否利旧</td>
  </tr>
  <tr>
    <td>1</td>
    <td>光缆1</td>
    <td>长飞纤</td>
    <td>米</td>
    <td>1000.00</td>
    <td>23.00</td>
    <td>23000.00</td>
    <td>是</td>
  </tr>
  <tr>
    <td>2</td>
    <td>光缆2</td>
    <td>长飞纤</td>
    <td>米</td>
    <td>1000.00</td>
    <td>23.00</td>
    <td>23000.00</td>
    <td>是</td>
  </tr>
</table>
 -->
 
<!-- 根据后台数据呈现表格 -->
<c:forEach  items="${assistMaterialTableList}" var="assistMaterialTable" >
	<table class="formTable">
	<tr>
		<c:forEach items="${assistMaterialTable.tableHeader}" var="tableHeader">
			<td style="background-color: #f7f7f7;vertical-align: top;">${tableHeader}</td>
		</c:forEach>
	</tr>
	
	<c:forEach items="${assistMaterialTable.tableData}" var="ds">
		<tr>
			<c:forEach items="${ds}" var="d">
				<td>${d}</td>
			</c:forEach>
		</tr>
	</c:forEach>
	</table>	
</c:forEach>	
<%@ include file="/common/footer_eoms.jsp"%>
