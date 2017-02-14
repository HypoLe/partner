<%@ page language="java" pageEncoding="utf-8"%>
<script type="text/javascript">
	function check(v){
		var a=/^(\d+)(\.\d+)?$/;
		if(v==''){
			alert("项目金额估算不可以为空!");
			document.getElementById("projectAmount").value="0";
		}else{
			if(!a.test(v)){
				alert("项目金额估算请输入整数或小数");
				document.getElementById("projectAmount").value="0"; 
			}
			if(v>=50000){
				alert("项目金额估算不能大于等于50000");
				document.getElementById("projectAmount").value="0";
			}
		}
		//没问题算一下收支比
		calPayments();
	}
	
	function checkOverhaul(v){
		var a=/^(\d+)(\.\d+)?$/;
		if(v==''){
			alert("项目金额估算不可以为空!");
			document.getElementById("projectAmount").value="0";
		}else{
			if(!a.test(v)){
				alert("项目金额估算请输入整数或小数");
				document.getElementById("projectAmount").value="0"; 
			}
			/*if(v<50000){
				alert("项目金额估算不能小于5万元");
				document.getElementById("projectAmount").value="";
				return;
			}*/
		}
	
		//没问题算一下收支比
		calPayments();
	}
	
	function formatStringToFloat(strValue){
		if(strValue!=""){
			return parseFloat(strValue);
		}else{
			return parseFloat("-2");
		}	
	}
	
	//实物赔补校验
	function checkSumFormat(v){
		if(v != ''){
			var a = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
			if(!a.test(v)){
				jq("#kindRestitution").val("0");				
			}
		}else{
			jq("#kindRestitution").val("0");
		}
		//没问题算一下收支比
		calPayments();
	}
	
	//赔补金额校验
	function checkSumFormat2(v){
		if(v != ''){
			var a = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
			if(!a.test(v)){
				jq("#compensate").val("0");				
			}
		}else{
			jq("#compensate").val("0");
		}
		//没问题算一下收支比
		calPayments();
	}
	
	//新的收支比计算规则 线路工单页面添加字段20160608
	function calPayments(){
		var projectAmount = jq("#projectAmount").val(); //项目金额
		var kindRestitution = jq("#kindRestitution").val();//实物赔补
		var compensate = jq("#compensate").val(); //现金赔补（赔补收入）
		if(projectAmount != "0"){
			var result = toDecimal((parseFloat(kindRestitution)+parseFloat(compensate))/parseFloat(projectAmount));
			jq("#incomeRatioDiv").text(result);
			jq("#incomeRatio").val(result);
		}else{
			jq("#incomeRatioDiv").text("0");
			jq("#incomeRatio").val("0");
		}
	}
	
	function toDecimal(x) { 
        var f = parseFloat(x);  
        if (isNaN(f)) {  
            return;  
        }  
        f = Math.round(x*100)/100;
        return f; 
    }
</script>