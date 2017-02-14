<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/showNewDistributeAapproveBack.jsp"%>
<style>
	.test{
		font-family:verdana,​arial,​helvetica,​sans-serif;
		font-size:12px;
		color:	rgb(68,​ 68,​ 68);
		line-height:16px;
		text-align:	start;
		vertical-align:	top;
	}
	
	.class_g{
		size:4px;
	}
		
</style>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
Date.prototype.format =function(format)
{
    var o = {
        "M+" : this.getMonth()+1, //month
        "d+" : this.getDate(), //day
        "h+" : this.getHours(), //hour
        "m+" : this.getMinutes(), //minute
        "s+" : this.getSeconds(), //second
        "q+" : Math.floor((this.getMonth()+3)/3), //quarter
        "S" : this.getMilliseconds() //millisecond
    }
    if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)if(new RegExp("("+ k +")").test(format))
        format = format.replace(RegExp.$1,
                RegExp.$1.length==1? o[k] :
                        ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
var roleTree;
var v;

  Ext.onReady(function(){
  var values = "${photoList}";
	if (values!=null){
	
	var photoDiv =  document.getElementById("photoDiv");  	
	
		photoDiv.style.display = "block";
	}
   v = new eoms.form.Validation({form:'theform'});
	
	if(jq("#lineName").val()=="与资源系统一致"){
		jq("#lineName").css("color","#cccccc");
	}
	if(jq("#title").val()=="例如：济南市长清区峰山路北段管道整修"){
		jq("#title").css("color","#cccccc");
	}
   });

   function changeType1(){
   	  var _projectAmount=jq("#projectAmount").val();
   	  if(_projectAmount<=50000){
	   	  jq("#mainSceneSelect").attr("disabled",false);
	   	  jq("#projectAmount").attr("disabled",false);
	   	  //获取选择的主场景
	      var selectValue=jq("#mainSceneSelect option:selected").text();
	      jq('#workOrderTypeName').val(selectValue);
	       //var myDate=new Date();
	      // var b =$('sheetCompleteLimit').value*60;
	      // myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
	      // $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
	  }else{
	  	alert("项目金额估算不能大于50000！");
	  	return false;
	  }
   }
   
   
</script>
<script type="text/javascript">
	function check(v){
		var a=/^(\d+)(\.\d+)?$/;
		if(v==''){
			alert("项目金额估算不可以为空!");
			return;
		}
		if(!a.test(v)){
			alert("项目金额估算请输入整数或小数");
			document.getElementById("projectAmount").value=""; 
			return;
		}
		if(v>=50000){
			alert("项目金额估算不能大于等于50000");
			document.getElementById("projectAmount").value="";
			return;
		}
		
		var _compensate=jq("#compensate").val();
		if(_compensate!=''){
			if(a.test(_compensate)){
				if(v!='0'){
					var result=toDecimal(_compensate/v);
					jq("#incomeRatioDiv").text(result);
					jq("#incomeRatio").val(result);
				}else{
					jq("#incomeRatioDiv").text("0");
					jq("#incomeRatio").val("0");
				}
					
			}
		}else{
			jq("#incomeRatioDiv").text("0");
			jq("#incomeRatio").val("0");
		}
	}
	
	function checkCompensate(v){
		//alert(111111111111111);
		if(v!=''){
			var a = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
			if(!a.test(formatStringToFloat(v))){
				jq("#compensate").val("0");
				jq("#incomeRatioDiv").text("0");
				jq("#incomeRatio").val("0");				
				return;
			}else{
				var _projectAmount=jq("#projectAmount").val();
				if(_projectAmount!="0"){
					var result=toDecimal(parseFloat(v)/parseFloat(_projectAmount));
					jq("#incomeRatioDiv").text(result);
					jq("#incomeRatio").val(result);
				}else{
					jq("#compensate").val("0");
					jq("#incomeRatioDiv").text("0");
					jq("#incomeRatio").val("0");	
				}			
			}
		}else{
			jq("#compensate").val("0");
			jq("#incomeRatioDiv").text("0");
			jq("#incomeRatio").val("0");
		}
	}
	
	function calculateIncomeRatio(v){
		var _projectAmount=jq("#projectAmount").val();
		if(_projectAmount!=''){
			if(_projectAmount=='0'){
				jq("#incomeRatioDiv").text("0");
			}else{
				var a=/^(\d+)(\.\d+)?$/;
				if(a.test(v)){
					var result=toDecimal(v/_projectAmount);
					jq("#incomeRatioDiv").text(result);
					jq("#incomeRatio").val(result);
				}
			}
		}
	}
	
	//功能：将浮点数四舍五入，取小数点后2位
	 function toDecimal(x) { 
        var f = parseFloat(x);  
        if (isNaN(f)) {  
            return;  
        }  
        f = Math.round(x*100)/100;
        return f; 
    } 
    
    function checkMainEngineeringQuantity(obj,name){
    	var _value=jq(obj).val();
    	if(_value!=null&&_value!=''){
    		var _expression=/^(\d+)(\.\d+)?$/;
    		if(!_expression.test(_value)){
    			alert(name+"-请输入整数或小数!");
    			jq(obj).val("");
    			return;
    		}
    	}
    } 
</script>


<script type="text/javascript"><!--
//记录所有表格每一行的唯一标识
var uniqueNum=0;
jq(function(){
	//加载不同的块
	
	
	
	
	
	
	
	
	
	
	//获取主场景下拉选的值（默认选中主场景的第一个）
		var mainSceneUrl="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=getMainScene";
		jq.getJSON(mainSceneUrl,function (data) {
                jq(data).each(function () {
                	jq("<option/>").html(this.name).val(this.id).appendTo("#mainSceneSelect");
                });
                var mainSceneId="${pnrTransferOffice.mainSceneId}";
                //alert("mainSceneId="+mainSceneId);
                jq("#mainSceneSelect option[value='"+mainSceneId+"']").attr("selected","selected");
               // alert(jq("#mainSceneSelect option:selected").text());
                //jq("#mainSceneSelect option:selected").val(mainSceneId); 
                //初始化第一个主场景下的子场景
                var selectValue=jq("#mainSceneSelect option:selected").val();
                
				xbox_provTree.defaultTree.root.id=selectValue;
				xbox_provTree.reloadTree();    

        });
            

});

jq(document).delegate('.class_g', 'blur', function(){
		var currentTrObj=jq(this).parents("tr"); 
		formulaCalculator(currentTrObj);
	}); 

function formatStringToFloat(strValue){
	if(strValue!=""){
		return parseFloat(strValue);
	}else{
		return parseFloat("-2");
	}	
}

function formatStringToFloatNew(strValue){
	if(strValue!=""){
		return parseFloat(strValue);
	}else{
		return "无";
	}	
}

function formulaCalculator(currentTrObj){
	var _fkixObj=currentTrObj.find("[name$='_fkix']");//乙方费用（工费）对象
	var _jphyObj=currentTrObj.find("[name$='_jphy']");//乙方费用（不含材料费）对象
	//var a=/^(([1-9]+)|([1-9]+\d*.\d*[1-9]+\d*)|([0][.]\d*[1-9]+\d*)|([1-9]+.+\d+)|([0]))$/; 
	var a = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
	var expr=new RegExp("^(-?\\d+)(\\.\\d+)?$");//针对其他费用里面的工费单价和材料单价可以填负数
	var _rfgzObj=currentTrObj.find("[name$='_rfgz']");//数量对象
	var childSceneValue=_rfgzObj.attr("name");
	
	//数量
 	var _rfgz="0";
 	//alert("_rfgz="+_rfgz);
 	//alert(_rfgzObj.val());
 	
 	if(a.test(formatStringToFloat(_rfgzObj.val()))){
 		_rfgz=_rfgzObj.val();
 		_rfgzObj.val(formatStringToFloat(_rfgzObj.val()));//回避数字开头，字母结束的输入
 	}else{
 		_rfgzObj.val("0");
 	}
 	//alert("_rfgz="+_rfgz);
 	 
	//获取到对应的子场景的ID
	childSceneValue=childSceneValue.substr(0,childSceneValue.indexOf('_'));
	//alert("---------子场景ID="+childSceneValue);
	
	//1.乙方费用
	//var _fkix="0";
	if (_fkixObj.length > 0) {//乙方费用（工费） 存在 
		//alert(22222);
 		//计算乙方费用（工费）
 		var _edzaObj=currentTrObj.find("[name='"+childSceneValue+"_edza']");//工费单价（打折部分）对象
 		var _qnhnObj=currentTrObj.find("[name='"+childSceneValue+"_qnhn']");//工费单价（非定额）
 		var _ahscObj=currentTrObj.find("[name='"+childSceneValue+"_ahsc']");//工费单价（非标准定额）对象
 		var _roryObj=currentTrObj.find("[name='"+childSceneValue+"_rory']");//工费单价对象
 		
 		if(_edzaObj.length > 0){//乙方费用（工费）=  数量*（工费单价（打折部分）*折扣率+工费单价（不打折部分））
 		//alert(33);
 			//工费单价（打折部分）
 			var _edza="0";
 			// alert(_edzaObj.val());
 			if(_edzaObj.val()!=""&&a.test(formatStringToFloat(_edzaObj.val()))){
 				_edza=_edzaObj.val();
 				_edzaObj.val(formatStringToFloat(_edzaObj.val()));
				_edzaObj.parents("td").find("div").text(formatStringToFloat(_edzaObj.val()));
 			}else{
 				currentTrObj.find("[name='"+childSceneValue+"_edza']").val(_edza);
				currentTrObj.find("[name='"+childSceneValue+"_edza']").parents("td").find("div").text(_edza);
 			}
 			//alert("_edza="+_edza);
 			//_edzaObj.parents("td").find("div").text("444");
 			
 			
 			
 			
 			//折扣率
 			
			var _gvyhObj=currentTrObj.find("[name='"+childSceneValue+"_gvyh']");
		//	alert(_gvyhObj.val());
			var _gvyh="0";
			if(_gvyhObj.val()!=""&&a.test(formatStringToFloat(_gvyhObj.val()))){
 				_gvyh=_gvyhObj.val();
 			}
 			
 		//alert("_gvyh="+_gvyh);
 			
 			//工费单价（不打折部分）
 				
			var _nzymObj=currentTrObj.find("[name='"+childSceneValue+"_nzym']");
		//	alert(_nzymObj.val());
			var _nzym="0";
			if(_nzymObj.val()!=""&&a.test(formatStringToFloat(_nzymObj.val()))){
 				_nzym=_nzymObj.val();
 				_nzymObj.val(formatStringToFloat(_nzymObj.val()));
				_nzymObj.parents("td").find("div").text(formatStringToFloat(_nzymObj.val()));
 			}else{
 				currentTrObj.find("[name='"+childSceneValue+"_nzym']").val(_nzym);
				currentTrObj.find("[name='"+childSceneValue+"_nzym']").parents("td").find("div").text(_nzym);
 			}
 			
 			//alert("_nzym="+_nzym);
 			
 			//乙方费用（工费）
 			var _fkix=toDecimal(parseFloat(_rfgz)*(parseFloat(_edza)*parseFloat(_gvyh)+parseFloat(_nzym)));
 			//alert(_fkix);
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").val(_fkix);
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").parents("td").find("div").text(_fkix);
			
 		}else if(_qnhnObj.length > 0){//乙方费用（工费）=数量 *工费单价（非定额）
 			//工费单价（非定额）
 			//var _qnhnObj=currentTrObj.find("[name='"+childSceneValue+"_qnhn']");
			var _qnhn="0";
			if(_qnhnObj.val()!=""&&a.test(formatStringToFloat(_qnhnObj.val()))){
 				_qnhn=_qnhnObj.val();
 				_qnhnObj.val(formatStringToFloat(_qnhnObj.val()));
				_qnhnObj.parents("td").find("div").text(formatStringToFloat(_qnhnObj.val()));
 			}
 			//乙方费用（工费）
 			_fkix=toDecimal(parseFloat(_rfgz)*parseFloat(_qnhn));
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").val(_fkix);
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").parents("td").find("div").text(_fkix);
 		
 		}else if(_ahscObj.length > 0){//乙方费用（工费） =数量 *工费单价（非标准定额）
 			//工费单价（非标准定额）
 			//var _ahscObj=currentTrObj.find("[name='"+childSceneValue+"_ahsc']");
			var _ahsc="0";
			if(a.test(formatStringToFloat(_ahscObj.val()))){
 				_ahsc=_ahscObj.val();
 				_ahscObj.val(formatStringToFloat(_ahscObj.val()));
				_ahscObj.parents("td").find("div").text(formatStringToFloat(_ahscObj.val()));
 			}
 			//乙方费用（工费）
 			_fkix=toDecimal(parseFloat(_rfgz)*parseFloat(_ahsc));
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").val(_fkix);
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").parents("td").find("div").text(_fkix);
 		
 		}else if(_roryObj.length > 0){//乙方费用（工费）=数量 *工费单价
 			//工费单价
 			//var _roryObj=currentTrObj.find("[name='"+childSceneValue+"_rory']");//工费单价对象
 			var _rory="0";
			//if(a.test(formatStringToFloat(_roryObj.val()))){
			if(expr.test(formatStringToFloatNew(_roryObj.val()))){
 				_rory=_roryObj.val();
 				_roryObj.val(formatStringToFloat(_roryObj.val()));
				_roryObj.parents("td").find("div").text(formatStringToFloat(_roryObj.val()));
 			}else{
 				_roryObj.val("0");
 			}
 			//乙方费用（工费）
 			_fkix=toDecimal(parseFloat(_rfgz)*parseFloat(_rory));
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").val(_fkix);
			currentTrObj.find("[name='"+childSceneValue+"_fkix']").parents("td").find("div").text(_fkix);
 		
 		}
    }else if(_jphyObj.length > 0){//乙方费用（不含材料费） 存在
 		//1.乙方费用（不含材料费）=数量 *工费单价（非标准定额）
 		var _ahscObj=currentTrObj.find("[name='"+childSceneValue+"_ahsc']");//工费单价（非标准定额）对象
 		
 		//工费单价（非标准定额）
 		var _ahsc="0";
 		if(a.test(formatStringToFloat(_ahscObj.val()))){
 			_ahsc=_ahscObj.val();
 			_ahscObj.val(formatStringToFloat(_ahscObj.val()));
			_ahscObj.parents("td").find("div").text(formatStringToFloat(_ahscObj.val()));
 		}
 		//乙方费用（不含材料费）
 		var _jphy=toDecimal(parseFloat(_rfgz)*parseFloat(_ahsc));
		currentTrObj.find("[name='"+childSceneValue+"_jphy']").val(_jphy);
		currentTrObj.find("[name='"+childSceneValue+"_jphy']").parents("td").find("div").text(_jphy);
 
   }
   //else{
   		//alert("乙方费用（工费）和乙方费用（不含材料费）都不存在");
   //}
   
   //2.材料费
   var _wrdaObj=currentTrObj.find("[name='"+childSceneValue+"_wrda']");//材料费对象
   var _wrda="0";
   if(_wrdaObj.length > 0){//材料费存在
   		 var _judzObj=currentTrObj.find("[name='"+childSceneValue+"_judz']");//主材单价对象
   		 var _dgogObj=currentTrObj.find("[name='"+childSceneValue+"_dgog']");//材料单价对象
   		if(_judzObj.length > 0){//材料费=数量*主材类型 
   			//wrda=rfgz*(辅材总费用+主材总费用+材料安全生产费)
   			
   			//主材总费用=主材单价=judz=主材合价之和（界面显示）
   			var _totalCostMainObj=currentTrObj.find("[name='"+childSceneValue+"_totalCostMain']");
   			var _totalCostMain="0";
   			if(a.test(formatStringToFloat(_totalCostMainObj.val()))){
 				_totalCostMain=_totalCostMainObj.val();
 			}
 			
 			currentTrObj.find("[name='"+childSceneValue+"_judz']").val(_totalCostMain);
			currentTrObj.find("[name='"+childSceneValue+"_judz']").parents("td").find("div").text(_totalCostMain);
   			
   			//辅材总费用=辅材单价=ijfn=辅材合价之和（界面显示）
   			var _totalCostAssistObj=currentTrObj.find("[name='"+childSceneValue+"_totalCostAssist']");
   			var _totalCostAssist="0";
   			if(a.test(formatStringToFloat(_totalCostAssistObj.val()))){
 				_totalCostAssist=_totalCostAssistObj.val();
 			}
 			
 			currentTrObj.find("[name='"+childSceneValue+"_ijfn']").val(_totalCostAssist);
			currentTrObj.find("[name='"+childSceneValue+"_ijfn']").parents("td").find("div").text(_totalCostAssist);
   			
   			//材料安全生产费 材料安全生产费=（辅材总费用+主材总费用）*1.5% zias=（辅材总费用+主材总费用）*1.5%
   			var _zias=toDecimal((parseFloat(_totalCostMain)+parseFloat(_totalCostAssist))*0.015);
   			
   			currentTrObj.find("[name='"+childSceneValue+"_zias']").val(_zias);
			currentTrObj.find("[name='"+childSceneValue+"_zias']").parents("td").find("div").text(_zias);
   			
   			//材料费
 			_wrda=toDecimal(parseFloat(_rfgz)*(parseFloat(_totalCostMain)+parseFloat(_totalCostAssist)+parseFloat(_zias)));
 			
   			
   		}else if(_dgogObj.length > 0){//材料费=数量*材料单价
   			//材料单价
   			//var _dgogObj=currentTrObj.find("[name='"+childSceneValue+"_dgog']");   			
	 		var _dgog="0";
	 		//if(a.test(formatStringToFloat(_dgogObj.val()))){
	 		if(expr.test(formatStringToFloatNew(_dgogObj.val()))){
	 			_dgog=_dgogObj.val();
	 			_dgogObj.val(formatStringToFloat(_dgogObj.val()));
				_dgogObj.parents("td").find("div").text(formatStringToFloat(_dgogObj.val()));
	 		}else{
	 			_dgogObj.val("0");
	 		}
			
			//材料费
   			_wrda=toDecimal(parseFloat(_rfgz)*parseFloat(_dgog));
   		
   		}
   		currentTrObj.find("[name='"+childSceneValue+"_wrda']").val(_wrda);
		currentTrObj.find("[name='"+childSceneValue+"_wrda']").parents("td").find("div").text(_wrda);
   }
   
   
   //3.电杆二次搬运费
    var _yxsgObj=currentTrObj.find("[name='"+childSceneValue+"_yxsg']");//电杆二次搬运费对象
  	var _yxsg="0";
  	if(_yxsgObj.length > 0){
  		if(parseFloat(_rfgz)<=parseFloat("5")){
		_yxsg=parseFloat(_rfgz)*100;
		}
		currentTrObj.find("[name='"+childSceneValue+"_yxsg']").val(_yxsg);
		currentTrObj.find("[name='"+childSceneValue+"_yxsg']").parents("td").find("div").text(_yxsg);
  	}
   
   //4.总费用
	var _ffmrObj=currentTrObj.find("[name='"+childSceneValue+"_ffmr']");//总费用对象
  	var _ffmr="0";
  	if(_ffmrObj.length > 0){//总费用=乙方费用（工费）+材料费+电杆二次搬运费
  		_ffmr=toDecimal(parseFloat(_fkix)+parseFloat(_wrda)+parseFloat(_yxsg));	
  		currentTrObj.find("[name='"+childSceneValue+"_ffmr']").val(_ffmr);
		currentTrObj.find("[name='"+childSceneValue+"_ffmr']").parents("td").find("div").text(_ffmr); 		
  	}
  	
  	//计算项目金额估算
  	sumProjectAmount();
  	
}

//计算项目金额估算
function sumProjectAmount(){
	var _childSceneIdsObj=jq("#childSceneIds");
	var _childSceneIds=_childSceneIdsObj.val();
	var _projectAmountObj=jq("#projectAmount");
	var sumResult="0";
	if(_childSceneIds!=""){
		//alert(111111111);
		var strs=new Array();
		strs=_childSceneIds.split(",");
		for (i=0;i<strs.length;i++){ 
			if(strs[i]!=""){
				var childSceneValue=strs[i];//子场景ID值
				var childSceneTableObj=jq("#"+childSceneValue+"_table");
				var childSceneTableLength=childSceneTableObj.find("tr").length;
				if(childSceneTableLength > 1){
					var _ffmrObj=childSceneTableObj.find("tr:eq(1)").find("[name='"+childSceneValue+"_ffmr']");//总费用 对象
					var _fkixObj=childSceneTableObj.find("tr:eq(1)").find("[name='"+childSceneValue+"_fkix']");//乙方费用（工费） 对象
					var _jphyObj=childSceneTableObj.find("tr:eq(1)").find("[name='"+childSceneValue+"_jphy']");//乙方费用（不含材料费） 对象
					
					if(_ffmrObj.length > 0){//有总费用
						for(b=1;b<childSceneTableLength;b++){
						sumResult=parseFloat(sumResult)+parseFloat(childSceneTableObj.find("tr:eq("+b+")").find("[name='"+childSceneValue+"_ffmr']").val());
						}
					
					}else if(_fkixObj.length > 0){//乙方费用（工费）
						for(k=1;k<childSceneTableLength;k++){
						sumResult=parseFloat(sumResult)+parseFloat(childSceneTableObj.find("tr:eq("+k+")").find("[name='"+childSceneValue+"_fkix']").val());
						}
					
					
					}else if(_jphyObj.length > 0){//乙方费用（不含材料费）
						for(j=1;j<childSceneTableLength;j++){
						sumResult=parseFloat(sumResult)+parseFloat(childSceneTableObj.find("tr:eq("+j+")").find("[name='"+childSceneValue+"_jphy']").val());
						}	
					}
				}				
				//alert(childSceneValue);
			} 
		} 
	}
	_projectAmountObj.val(toDecimal(sumResult));
	
	//计算收支比
	calculateTheIncome(sumResult);
	
} 

//计算收支比
function calculateTheIncome(v){
		var a = new RegExp("^[0-9]\\d*(\\.\\d+)?$");
		var _compensate=jq("#compensate").val();
		
		//alert(formatStringToFloat(_compensate));
		
		//alert(a.test(formatStringToFloat(_compensate)));
		
		if(_compensate!=''){
			if(a.test(formatStringToFloat(_compensate))){
				if(v!='0'){
					var result=toDecimal(parseFloat(_compensate)/parseFloat(v));
					jq("#incomeRatioDiv").text(result);
					jq("#incomeRatio").val(result);
				}else{
					jq("#incomeRatioDiv").text("0");
					jq("#incomeRatio").val("0");
				}				
			}else{
				jq("#compensate").val("0");
				jq("#incomeRatioDiv").text("0");
				jq("#incomeRatio").val("0");
			}
		}else{
			jq("#compensate").val("0");
			jq("#incomeRatioDiv").text("0");
			jq("#incomeRatio").val("0");
		}
		
		
		
		
	}





//当数量被制为空的时候，清除按数量计算出来的金额显示
function cleanUpAmount(currentTrObj,className,childSceneValue){
	if(className=="class_g123"){	//g1;g2;g3
		currentTrObj.find("[name='"+childSceneValue+"_fkix']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_wrda']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_ffmr']").val("");
	}else if(className=="class_g1"){	//g1
		currentTrObj.find("[name='"+childSceneValue+"_fkix']").val("");
	}else if(className=="class_g6"){	//g6
		currentTrObj.find("[name='"+childSceneValue+"_fkix']").val("");
	}else if(className=="class_g1289"){	//g1;g2;g8;g9
		currentTrObj.find("[name='"+childSceneValue+"_fkix']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_wrda']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_yxsg']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_ffmr']").val("");
	}else if(className=="class_g623"){	//g6;g2;g3
		currentTrObj.find("[name='"+childSceneValue+"_fkix']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_wrda']").val("");
		currentTrObj.find("[name='"+childSceneValue+"_ffmr']").val("");
	}else if(className=="class_g7"){	//g7
		currentTrObj.find("[name='"+childSceneValue+"_jphy']").val("");
	}
}

 

//改变定额，加载编号，折扣率等
//1.先判断主材或辅材是否已经选择，如果选择定额不可以修改
function getOtherIndex(obj,quotaFieldName,tableName,childSceneValue){
	
//	 alert("getOtherIndex公司");
	var _trObj=jq(obj).parents("tr");
//	 alert("_trObj="+_trObj);
	var _quotaValueObj=_trObj.find("[name='"+childSceneValue+"_quotaValue']");
	var _quotaValue=_quotaValueObj.val();
	var _quotaFieldName=jq("#"+quotaFieldName).val();
	var quotaParameter="";
	var str= new Array();
	str=_quotaFieldName.split(",");
//	alert("_quotaValue="+_quotaValue);
	if(_quotaValue==""){
		//alert("是空字符串吗");
		//alert(jq("#"+quotaFieldName).val());
	
	//重置框内的值***
	/*_trObj.find("[name='"+childSceneValue+"_ahsc']").val("");
	_trObj.find("[name='"+childSceneValue+"_edza']").val("");
	_trObj.find("[name='"+childSceneValue+"_gvyh']").val("");
	_trObj.find("[name='"+childSceneValue+"_hsok']").val("");
	_trObj.find("[name='"+childSceneValue+"_nzym']").val("");
	_trObj.find("[name='"+childSceneValue+"_qnhn']").val("");
	
	_trObj.find("[name='"+childSceneValue+"_ahsc']").parents("td").find("div").text("");
	_trObj.find("[name='"+childSceneValue+"_edza']").parents("td").find("div").text("");
	_trObj.find("[name='"+childSceneValue+"_gvyh']").parents("td").find("div").text("");
	_trObj.find("[name='"+childSceneValue+"_hsok']").parents("td").find("div").text("");
	_trObj.find("[name='"+childSceneValue+"_nzym']").parents("td").find("div").text("");
	_trObj.find("[name='"+childSceneValue+"_qnhn']").parents("td").find("div").text("");*/
	
	//更新总费用
	var _rfgzObj=_trObj.find("[name$='_rfgz']");//数量对象
	var _rfgz=_rfgzObj.val();//数量值
	// alert("总费用"+_rfgz.length);
	var a=/^\d+(\.\d+)?$/;
	
	if(_rfgz==""||a.test(_rfgz)){	
	// alert(_rfgz);	
		for (i=0;i<str.length ;i++ ){
			if(str[i]!=""){
				quotaParameter+=_trObj.find("[name='"+str[i]+"']").val()+",";
			}
		}
		
		var propertyUrl="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=getOtherIndex&childSceneId="+childSceneValue+"&quotaVals="+quotaParameter;
		
		//jq.getJSON(propertyUrl, {childSceneId:childSceneValue,quotaVals:quotaParameter},function (data) {
			//alert(data);
	    	//jq(data).each(function (i,item) {
	    		 //alert("item="+item.proEnglishName);
	    		//_trObj.find("[name='"+childSceneValue+"_"+item.proEnglishName+"']").parents("td").find("div").text(item.proValue);
				//_trObj.find("[name='"+childSceneValue+"_"+item.proEnglishName+"']").val(item.proValue);
				//_trObj.find("[name='"+childSceneValue+"_itemNo']").val(item.itemNo);
	       // });
	      // formulaCalculator(_trObj);
		//}); 
		
		jq.ajax({
			type: "post",
	 		url: propertyUrl,
	 		async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data) {
	 			if( data!= null&&data!='' ){
		 			jq(data).each(function (i,item) {
			    		 //alert("item="+item.proEnglishName);
			    		_trObj.find("[name='"+childSceneValue+"_"+item.proEnglishName+"']").parents("td").find("div").text(item.proValue);
						_trObj.find("[name='"+childSceneValue+"_"+item.proEnglishName+"']").val(item.proValue);
						_trObj.find("[name='"+childSceneValue+"_itemNo']").val(item.itemNo);
						_trObj.find("[name='"+childSceneValue+"_uniqueId']").val(item.uniqueId);
		        	});
	 			}
	 			
	       		formulaCalculator(_trObj);     
	 		},
	 		error: function (err) {
	           lert(err);
	        }		
		});
		
		//formulaCalculator(_trObj);
		
	}else{
		alert("数量请输入大于等于0的数");
		return;
	}
	
 }else{
 	//alert("--------值等于"+jq(obj).val());
 	//alert("--------名字等于"+jq(obj).attr("name"));
 	
 	alert("主材或辅材已选择，不可修改定额配置!");
 	
 	//改为下拉选改变之前的值
	var quotaValueArray= new Array();
	quotaValueArray=_quotaValue.split(",");
	for (i=0;i<str.length ;i++ ){
		if(str[i]!=""&&str[i]==jq(obj).attr("name")){			
			jq(obj).parents("tr").find("[name='"+str[i]+"']").val(quotaValueArray[i]);
		}
	}
 	
 	return;
 }
 
}

//主材选择
function chooseMainMaterial(choosebtn,childSceneValue) {
	// alert(11111111111);
	//获取当前行数量的值，如果值为空，则不允许选主材
	var _rfgz=jq(choosebtn).parents("tr").find("[name='"+childSceneValue+"_rfgz']").val();
	
	var a=/^\d+(\.\d+)?$/; 
	if(_rfgz==""){
		alert("数量不能为空，请填写数量后选择主材！");
		return;
	}else if(!a.test(_rfgz)){
		alert("数量请输入大于等于0的数！");
		return;
	}else{
		var quotaValue=jq(choosebtn).parents("tr").find("[name*='_quotaValue']").val();
		//alert("quotaValue="+quotaValue);
		
		//点击的该按钮所在的行号，回传值的时候可以根据行号确定是那一行的按钮。
		var rowIndexValue=jq(choosebtn).parents("tr").index();
		
		//alert(childSceneValue);
		var quotaFieldObjectStr=childSceneValue+"_quotaField";
		var quotaFieldObject=jq("#"+quotaFieldObjectStr);
		var quotaFields=quotaFieldObject.val();
		var quotaParameter="";
		var str= new Array();
		str=quotaFields.split(",");
		for (i=0;i<str.length ;i++ ){
			if(str[i]!=""){
				//alert(jq(choosebtn).parents("tr").find("[name='"+str[i]+"']").val());
				quotaParameter+=jq(choosebtn).parents("tr").find("[name='"+str[i]+"']").val()+",";
			}
		}
		
		var mainIds=jq(choosebtn).parents("tr").find("[name*='_main']").val();
		var _infor=jq(choosebtn).parents("tr").find("[name*='_inforMain']").val();
		var _totalCost=jq(choosebtn).parents("tr").find("[name*='_totalCostMain']").val();
	
		var existQuotaValueObject=jq("#"+childSceneValue+"_existQuotaValue");
		var existQuotaValue=existQuotaValueObject.val();
		if(existQuotaValue==null||existQuotaValue==""){
			existQuotaValue="@";//避免为空判断为零的情况
		}
	
		//if((quotaValue==""&&existQuotaValue.indexOf(quotaParameter)== -1)||quotaValue!=""){
					//查询主材的链接
			var urlStr="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=queryMainMaterial"+"&childSceneId="+childSceneValue+"&quotaVals="+quotaParameter+"&mainIds="+mainIds+"&rowIndexValue="+rowIndexValue+"&materialType=main&infor="+_infor+"&totalCost="+_totalCost;
			var _sHeight = 600;
			var _sWidth = 820;
			var sTop = (window.screen.availHeight - _sHeight) / 2;
			var sLeft = (window.screen.availWidth - _sWidth) / 2;
			var sFeatures = "dialogHeight: " + _sHeight + "px; dialogWidth: " + _sWidth + "px; dialogTop: " + sTop + "; dialogLeft: " + sLeft + "; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(urlStr, window, sFeatures);
		//}else{
			//alert("该定额配置已存在!");
			//return;
		//}
	}
  };

//主材设置
function setMainMaterialIds(childSceneId,selectMainIds,quotaVals,rowIndexValue,assetInforStr,totalCost){
	//alert(childSceneId);
	//alert(selectMainIds);
	//alert(quotaVals);
	//alert(rowIndexValue);
	
	var tableName=childSceneId+"_table";//表格名
	var tableObject=jq("#"+tableName);
	var trObject=tableObject.find("tr:eq("+rowIndexValue+")");
	//alert(tableObject);
	

	//alert(tableObject.find("tr:eq("+rowIndexValue+")").find("input[name*='_main']").val());
	//设置选择的主材ID
	trObject.find("input[name*='_main']").val(selectMainIds);
	trObject.find("input[name*='_quotaValue']").val(quotaVals);
	trObject.find("input[name*='_inforMain']").val(assetInforStr);
	trObject.find("input[name*='_totalCostMain']").val(totalCost);
	//trObject.find("input[name*='"+childSceneId+"_judz']").val("530");
	
	
	
	var existQuotaValueObject=jq("#"+childSceneId+"_existQuotaValue");
	var existQuotaValue=existQuotaValueObject.val();
	var tempExistQuotaValue="";
	if(existQuotaValue==null||existQuotaValue==""){
			tempExistQuotaValue="@";//避免为空判断为零的情况
	}else{
		tempExistQuotaValue=existQuotaValue;
	}
	
	//alert(existQuotaValue);
	if(tempExistQuotaValue.indexOf(quotaVals)==-1){
		existQuotaValueObject.val(existQuotaValue+quotaVals+"#");
	}
	
	//更新总费用
	formulaCalculator(trObject);
	
}

//辅材选择
function chooseAssistMaterial(choosebtn,childSceneValue) {
	//获取当前行数量的值，如果值为空，则不允许选辅材
	var _rfgz=jq(choosebtn).parents("tr").find("[name='"+childSceneValue+"_rfgz']").val();
	var a=/^\d+(\.\d+)?$/;
	if(_rfgz==""){
		alert("数量不能为空，请填写数量后选择辅材！");
		return;
	}else if(!a.test(_rfgz)){
		alert("数量请输入大于等于0的数！");
		return;
	}else{
		var quotaValue=jq(choosebtn).parents("tr").find("[name*='_quotaValue']").val();
	//alert("quotaValue="+quotaValue);
	
	//点击的该按钮所在的行号，回传值的时候可以根据行号确定是那一行的按钮。
	var rowIndexValue=jq(choosebtn).parents("tr").index();
	
	//alert(childSceneValue);
	var quotaFieldObjectStr=childSceneValue+"_quotaField";
	var quotaFieldObject=jq("#"+quotaFieldObjectStr);
	var quotaFields=quotaFieldObject.val();
	var quotaParameter="";
	var str= new Array();
	str=quotaFields.split(",");
	for (i=0;i<str.length ;i++ ){
		if(str[i]!=""){
			//alert(jq(choosebtn).parents("tr").find("[name='"+str[i]+"']").val());
			quotaParameter+=jq(choosebtn).parents("tr").find("[name='"+str[i]+"']").val()+",";
		}
	}
	
	var mainIds=jq(choosebtn).parents("tr").find("[name*='_assist']").val();
	var _infor=jq(choosebtn).parents("tr").find("[name*='_inforAssist']").val();
	var _totalCost=jq(choosebtn).parents("tr").find("[name*='_totalCostAssist']").val();
	
		var existQuotaValueObject=jq("#"+childSceneValue+"_assistExistQuotaValue");
		var existQuotaValue=existQuotaValueObject.val();
		if(existQuotaValue==null||existQuotaValue==""){
			existQuotaValue="@";//避免为空判断为零的情况
		}

	
		//if((quotaValue==""&&existQuotaValue.indexOf(quotaParameter)== -1)||quotaValue!=""){		
					//查询主材的链接
			var urlStr="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=queryMainMaterial"+"&childSceneId="+childSceneValue+"&quotaVals="+quotaParameter+"&mainIds="+mainIds+"&rowIndexValue="+rowIndexValue+"&materialType=assist&infor="+_infor+"&totalCost="+_totalCost;
			var _sHeight = 600;
			var _sWidth = 820;
			var sTop = (window.screen.availHeight - _sHeight) / 2;
			var sLeft = (window.screen.availWidth - _sWidth) / 2;
			var sFeatures = "dialogHeight: " + _sHeight + "px; dialogWidth: " + _sWidth + "px; dialogTop: " + sTop + "; dialogLeft: " + sLeft + "; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
			window.showModalDialog(urlStr, window, sFeatures);
		//}else{
			//alert("该定额配置已存在!");
			//return;
		//}
	}
 };

//辅材设置
function setAssistMaterialIds(childSceneId,selectMainIds,quotaVals,rowIndexValue,assetInforStr,totalCost){
	//alert(childSceneId);
	//alert(selectMainIds);
	//alert(quotaVals);
	//alert(rowIndexValue);
	
	var tableName=childSceneId+"_table";//表格名
	var tableObject=jq("#"+tableName);
	var trObject=tableObject.find("tr:eq("+rowIndexValue+")");
	//alert(tableObject);
	

	//alert(tableObject.find("tr:eq("+rowIndexValue+")").find("input[name*='_main']").val());
	//设置选择的主材ID
	trObject.find("input[name*='_assist']").val(selectMainIds);
	trObject.find("input[name*='_quotaValue']").val(quotaVals);
	trObject.find("input[name*='_inforAssist']").val(assetInforStr);
	trObject.find("input[name*='_totalCostAssist']").val(totalCost);
	
	
	var existQuotaValueObject=jq("#"+childSceneId+"_assistExistQuotaValue");
	var existQuotaValue=existQuotaValueObject.val();
	var tempExistQuotaValue="";
	if(existQuotaValue==null||existQuotaValue==""){
			tempExistQuotaValue="@";//避免为空判断为零的情况
	}else{
		tempExistQuotaValue=existQuotaValue;
	}
	
	//alert(existQuotaValue);
	if(tempExistQuotaValue.indexOf(quotaVals)==-1){
		existQuotaValueObject.val(existQuotaValue+quotaVals+"#");
	}
		//trObject.find("input[name*='"+childSceneId+"_ijfn']").val("50.3");
	
	
	//更新总费用
	formulaCalculator(trObject);
	
}

//添加行
function addtr(addbtn,childSceneValue) {
	
//复制第一行
	var tableName=childSceneValue+"_table";//表格名
	var tableObject=jq("#"+tableName);
	var newTr=tableObject.find("tr:eq(1)").clone().appendTo(tableObject);
	newTr.find("input[type='text']").val("");
	newTr.find("input[name*='_main']").val("");
	newTr.find("input[name*='_assist']").val("");
	newTr.find("input[name*='_quotaValue']").val("");
	
	newTr.find("input[name*='_inforMain']").val("");
	newTr.find("input[name*='_inforAssist']").val("");
	newTr.find("input[name*='_totalCostMain']").val("0");
	newTr.find("input[name*='_totalCostAssist']").val("0");
	newTr.find("input[name*='_itemNo']").val("");
	
	//清空隐藏域
	newTr.find("input[type='hidden']").val("");
	//清空div
	newTr.find("input[type='hidden']").parents("td").find("div").text("");
	
	
	 //初始化其他指标
	 getOtherIndex(newTr.find("input:eq(0)"),childSceneValue+'_quotaField',childSceneValue+'_table',childSceneValue);
				

	
};

//删除行
function deltr(delbtn,childSceneValue) {
	var tableName=childSceneValue+"_table";//表格名
	var tableObject=jq("#"+tableName);
	var tableDiv=tableObject.parent("div");
	var tableStr=jq(delbtn).parents("table");
	var quotaValues=jq(delbtn).parents("tr").find("[name*='_quotaValue']").val();
	
	//移除主材ID
	var existQuotaValueObject=jq("#"+childSceneValue+"_existQuotaValue");
	var existQuotaValue=existQuotaValueObject.val();
	if(quotaValues!=null&&quotaValues!=""){
		existQuotaValueObject.val(existQuotaValue.replace(quotaValues+"#",""));
	}
	
	//移除辅材ID
	var assistExistQuotaValueObject=jq("#"+childSceneValue+"_assistExistQuotaValue");
	var assistExistQuotaValue=assistExistQuotaValueObject.val();
	if(quotaValues!=null&&quotaValues!=""){
		assistExistQuotaValueObject.val(assistExistQuotaValue.replace(quotaValues+"#",""));
	}
	
	
	
	jq(delbtn).parents("tr").remove();
	
	//当表格中的所有行都删除了，则隐藏div
	var t01 =tableStr.find("tr").length;
    if(t01=='1'){
    	tableDiv.remove();    	
    	var tempChildSceneIds=jq("#childSceneIds").val();
		tempChildSceneIds=tempChildSceneIds.replace(childSceneValue+",","");
		jq("#childSceneIds").val(tempChildSceneIds);
    }
    
    //计算项目金额估算
  	sumProjectAmount();
  	
  	//判断主场景是否禁用
	var _tempChildSceneIds=jq("#childSceneIds").val();
	if(_tempChildSceneIds!=""){
		jq("#mainSceneSelect").attr("disabled",true);
	}else{
		jq("#mainSceneSelect").removeAttr("disabled");
	}	
  	
};

function setMaste(vals,texts){
			
		// var vals="ZEJ3AFR8,EY3NZBTZ";
		// var texts="敷设光缆（不含接续）,光缆接续";
		// alert("vals="+vals);
				
		if(vals!=null&&vals!=""){
		
			//拆分选择的子场景文本串
			var childSceneStrS= new Array();
			childSceneStrS=texts.split(",");
			//拆分选择的子场景ID串
			var childSceneValueS= new Array();
			childSceneValueS=vals.split(",");
			
			var childSceneStr="";
			var childSceneValue="";
			//遍历子场景ID串
			for (var i=0;i<childSceneValueS.length;i++){
				if(childSceneValueS[i]!=""){//-----------------start
					childSceneStr=childSceneStrS[i]; //子场景对应的中文
					//alert(childSceneStr);
					childSceneValue=childSceneValueS[i];//子场景的ID值
					//alert(childSceneValue);
					
					//获取已经存在的子场景的ID值			
					var _childSceneIds=jq("#childSceneIds").val();
					var tempChildSceneIds="";
					if(_childSceneIds==null||_childSceneIds==""){
						tempChildSceneIds="@";//避免为空判断为零的情况
					}else{
						tempChildSceneIds=_childSceneIds;
					}
					
					//选择的子场景id在记录选择的子场景ID中不存在生成表格；存在不进行任何操作。
					if(tempChildSceneIds.indexOf(childSceneValue)==-1){
						var tableObject=jq("#"+childSceneValue+"_table");//子场景对应的表格名
						if(tableObject.find("tr").length=='0'){
							if(childSceneValue!=""){
							var propertyUrl="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=getPropertisByChildSceneId&id="+childSceneValue;
							var firstTr="";
							var twoTr="";
							var tabelStr="";
							//var propertyVals="";
							var isQuotaVals="";
							
							jq.ajax({
	 type: "post",
	 url: propertyUrl,
	 async: false,//控制同步        
	 contentType: "application/json; charset=utf-8",
	 dataType: "json",
	 cache: false,
	 success: function (data) {
		 jq(data).each(function (i,item) {
				var tempSelect="";
				firstTr+="<td>"+item.proChineseName+"</td>";
				var tempProEnglishName=item.proEnglishName;
				//propertyVals+=childSceneValue+"_"+tempProEnglishName+",";
				if(item.proType=="select"){
					jq(item.proValue).each(function (k,field) {
						tempSelect+="<option value='"+field.id+"'>"+field.name+"</option>";
					});
					twoTr+="<td><select name='"+childSceneValue+"_"+tempProEnglishName+"' onchange='getOtherIndex(this,&quot;"+childSceneValue+"_quotaField&quot;,&quot;"+childSceneValue+"_table&quot;,&quot;"+childSceneValue+"&quot;)' >"+tempSelect+"</select></td>";
				}else if(item.proType=="inputText"){
				
					 twoTr+="<td><input type='text' ";
					 if("1"==item.isRequire){
						 twoTr+=" alt=\"allowBlank:false,maxLength:120,vtext:&quot;请输入值，不能为空！&quot;\"";
					 }
				
					 if(item.className!=""&&item.className!=null){
					 			if(item.className=="lengthen"){
					 				twoTr+=" name='"+childSceneValue+"_"+tempProEnglishName+"' value='' size='90' class='"+item.className+"' /></td>";
					 			}else{
					 				twoTr+=" name='"+childSceneValue+"_"+tempProEnglishName+"' value='' size='4' class='"+item.className+"' /></td>";
					 			}          					
         			    }else{
         					    twoTr+=" name='"+childSceneValue+"_"+tempProEnglishName+"' size='4' value='' /></td>";
         			    }
				}else if(item.proType=="text"){
					twoTr+="<td><div>"+item.proValue+"</div><input type='hidden' name='"+childSceneValue+"_"+tempProEnglishName+"' size='8' value='"+item.proValue+"'/></td>";
				}else if(item.proType=="mainButton"){
						twoTr+="<td><input type='button' name='"+childSceneValue+"_"+tempProEnglishName+"' value='主材选择' onclick='chooseMainMaterial(this,&quot;"+childSceneValue+"&quot;)' /></td>";
				}else if(item.proType=="assistButton"){
						twoTr+="<td><input type='button' name='"+childSceneValue+"_"+tempProEnglishName+"' value='辅材选择' onclick='chooseAssistMaterial(this,&quot;"+childSceneValue+"&quot;)' /></td>";
				}
				if(item.isQuota=="YES"){
					isQuotaVals+=childSceneValue+"_"+tempProEnglishName+",";
				}

				
		});
		
		 firstTr="<tr>"+firstTr+"<td><input type='button' value='添加' onclick='addtr(this,&quot;"+childSceneValue+"&quot;)' /></td>"+"</tr>";
		 twoTr="<tr>"+twoTr+"<td>"
		 +"<input type='hidden' name='"+childSceneValue+"_main' value='' />"
		 +"<input type='hidden' name='"+childSceneValue+"_assist' value='' />"
		 +"<input type='hidden' name='"+childSceneValue+"_quotaValue' value='' />"
		 +"<input type='hidden' name='"+childSceneValue+"_inforMain' value='' />"
		 +"<input type='hidden' name='"+childSceneValue+"_inforAssist' value='' />"
		 +"<input type='hidden' name='"+childSceneValue+"_totalCostMain' value='0' />"
		 +"<input type='hidden' name='"+childSceneValue+"_totalCostAssist' value='0' />"
		 +"<input type='hidden' name='"+childSceneValue+"_itemNo' value='' />"
		 +"<input type='hidden' name='"+childSceneValue+"_uniqueId' value='' />"
		 
		 +"<input type='button' value='删除' onclick='deltr(this,&quot;"+childSceneValue+"&quot;)' /></td>"+"</tr>";
		 tabelStr="<table class='formTable' id='"+childSceneValue+"_table' name='"+childSceneValue+"_table' class='formTable'>"+firstTr+twoTr+"</table><br />";
		 tabelStr="<div>子场景-"+childSceneStr+":"
		 
		 +"<input type='hidden' size='120' id='"+childSceneValue+"_quotaField' name='"+childSceneValue+"_quotaField' value='"+isQuotaVals+"' /></br>"
		 +"<input type='hidden' id='"+childSceneValue+"_existQuotaValue' size='120' name='"+childSceneValue+"_existQuotaValue' value='' /></br>"
		 +"<input type='hidden' size='120' id='"+childSceneValue+"_assistExistQuotaValue' name='"+childSceneValue+"_assistExistQuotaValue' value='' /><br />"+tabelStr+"</div>";
		
		 jq("#sceneMainDiv").append(tabelStr);
		 
		 //初始化其他指标
		 getOtherIndex(jq("#"+childSceneValue+"_table tr:eq(1) input:eq(0)"),childSceneValue+'_quotaField',childSceneValue+'_table',childSceneValue);
	 },
	 error: function (err) {
	    alert(err);
	 }
});
						
									
						//记录子场景的ID
						var tempChildSceneIds=jq("#childSceneIds").val();
						tempChildSceneIds+=childSceneValue+",";
						jq("#childSceneIds").val(tempChildSceneIds);
						//uniqueNum++;
			      	}
			      	//else{
			      		//alert("子场景不允许为空。");
			      	//}
				}else{
					alert("子场景-"+childSceneStr+",已存在。");
				}
						
						
					
						
					}		
					
				}//--------------------
			}
		//回显主场景
		var _tempChildSceneIds=jq("#childSceneIds").val();
		if(_tempChildSceneIds!=""){
			jq("#mainSceneSelect").attr("disabled",true);
		}			
			
		}else{
			alert("子场景不可以为空，请选择子场景！");
		}	
	}

   function selectTeam(){
    
 		var mainSceneSelect = document.getElementById("mainSceneSelect").value;
 		// alert(mainSceneSelect);
		xbox_provTree.defaultTree.root.id=mainSceneSelect;
		xbox_provTree.reloadTree();
		xbox_provTree.show();
	}
--></script>
<script type="text/javascript">
function changeColorOne(obj){
	var _linkName=jq(obj).val();
	if(_linkName==""){
		jq(obj).val("与资源系统一致");
		jq(obj).css("color","#888888");
	}
}

function changeColorTwo(obj){
	var _linkName=jq(obj).val();
	if(_linkName=="与资源系统一致"){
		jq(obj).val("");
		jq(obj).css("color","black");
	}
	
}
function changeThemeColorOne(obj){
	var _linkName=jq(obj).val();
	if(_linkName==""){
		jq(obj).val("例如：济南市长清区峰山路北段管道整修");
		jq(obj).css("color","#888888");
	}
}
function changeThemeColorTwo(obj){
	var _linkName=jq(obj).val();
	if(_linkName=="例如：济南市长清区峰山路北段管道整修"){
		jq(obj).val("");
		jq(obj).css("color","black");
	}
}

</script>

<html:form action="/pnrTransferNewPrecheck.do?method=performAdd" styleId="theform" >
	<input type="hidden" id="viewFlag" name="viewFlag" value="newJob" /><!-- 保存标识 -->
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    <input id="themeInterface" type="hidden" name="themeInterface" value="interface">
    <input id="sheetId" type="hidden" name="sheetId" value="${pnrTransferOffice.sheetId}">
    <input id="workOrderTypeName" type="hidden" name="workOrderTypeName" value="${pnrTransferOffice.workOrderTypeName}">
    
    <!-- 基本信息 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			<B>工单基本信息</B>
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width:10%">
					工单名称*
				</td>
				<td colspan="5">
					<c:if test="${pnrTransferOffice.theme ==null}">

							<input type="text" name="title" class="text max" id="title" onfocus="changeThemeColorTwo(this)" onblur="changeThemeColorOne(this)"
							value="例如：济南市长清区峰山路北段管道整修"
							alt="allowBlank:false,maxLength:120,vtext:'请输入工单名称，最大长度为60个汉字！'" />
		
					</c:if>
					<c:if test="${pnrTransferOffice.theme !=null}">
						<input type="text" name="title" class="text max"
							value="${pnrTransferOffice.theme}" readOnly=true />
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="label" style="width: 10%">
					工单发起人*
				</td>
				<td class="content" style="width: 23%">
					<eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao" />
					<input type="hidden" name="initiator" value="${userId}" />
				</td>

				<td class="label" style="width: 10%">
					发起人部门*
				</td>
				<td class="content" style="width: 23%">
					<eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao" />
					<input type="hidden" id="initiatorDept" name="initiatorDept" value="${deptId}" />
				</td>

				<td class="label" style="width: 10%">
					发起人电话*
				</td>
				<td class="content" style="width: 24%">
					<input type="text" id="initiatorMobilePhone" name="initiatorMobilePhone" value="${mobilePhone}" />加校验
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width:10%">
					工单类型*
				</td>
				<td class="content" style="width:15%">
					<select id="themeInterface" name="themeInterface" class="select">
						<option value="olt">
							OLT机房优化清单
						</option>
						<option value="bbu">
							BBU机房优化清单
						</option>
						<option value="oltbbu">
							OLT、BBU共站址机房
						</option>
					</select>
				</td>
				<td class="label" style="width: 10%">
					主场景*
				</td>
				<td class="content" style="width: 23%">
					 <select id="mainSceneSelect" name="mainSceneSelect" onchange="selectTeam()" class="select" alt="allowBlank:false"></select>
				
				</td>

				<td class="label" style="width: 10%">
					子场景*
				</td>
				<td class="content" style="width: 24%"> 
				    <input type="text" class="text max" name="subTypeName" id="subTypeName"
						value="${pnrTransferOffice.subTypeName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="childSceneSelect" id="childSceneSelect" value="${pnrTransferOffice.subType}" type="hidden" />
					<eoms:xbox id="provTree"
						dataUrl="${app}/xtree.do?method=masteXbox&level=4"
						rootId="" rootText="子场景" valueField="childSceneSelect" handler="subTypeName"
						textField="subTypeName" checktype="dict"  jsCfg="k:1"/>
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					批次*
				</td>
				<td class="content" colspan="5" style="width: 23%">
					 <input type="text" class="text" id="batch"  name="batch" value="${pnrTransferOffice.batch}" alt="allowBlank:false,maxLength:100,vtext:'请输入批次，最大长度为50个汉字！'" />
				</td>
			</tr>

		</table>
	</fieldset>
	
	<!-- 机房基本信息 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			<B>机房基本信息</B>
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width: 10%">
					地市*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" class="text" id="projectStartPoint"  name="projectStartPoint" value="${pnrTransferOffice.projectStartPoint}" />
				</td>
				<td class="label" style="width: 10%">
					区县*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" class="text" id="projectEndPoint"  name="projectEndPoint" value="${pnrTransferOffice.projectEndPoint}" alt="allowBlank:true,maxLength:100,vtext:'请输入项目终点，最大长度为50个汉字！'" />
				</td>
				<td class="label" style="width: 10%">
					设备位置/局址名称*
				</td>
				<td class="content" style="width: 24%">
					<input type="text" class="text" id="specificLocation"  name="specificLocation" value="${pnrTransferOffice.specificLocation}" alt="allowBlank:true,maxLength:200,vtext:'请输入具体地点，最大长度为100个汉字！'" />
				</td>			
			</tr>
			<div id="oneDiv">
				<!-- <tr>
					<td class="label" style="width: 10%">
						机房内OLT数量*
					</td>
					<td class="content" style="width: 23%">
						<input type="text" class="text" id="projectStartPoint"  name="projectStartPoint" value="${pnrTransferOffice.projectStartPoint}" alt="allowBlank:true,maxLength:100,vtext:'请输入项目起点，最大长度为50个汉字！'" />
					</td>
					<td class="label" style="width: 10%">
						总用户数*
					</td>
					<td class="content" style="width: 23%">
						<input type="text" class="text" id="projectEndPoint"  name="projectEndPoint" value="${pnrTransferOffice.projectEndPoint}" alt="allowBlank:true,maxLength:100,vtext:'请输入项目终点，最大长度为50个汉字！'" />
					</td>
					<td class="label" style="width: 10%">
						语音用户数*
					</td>
					<td class="content" style="width: 24%">
						<input type="text" class="text" id="specificLocation"  name="specificLocation" value="${pnrTransferOffice.specificLocation}" alt="allowBlank:true,maxLength:200,vtext:'请输入具体地点，最大长度为100个汉字！'" />
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 10%">
						宽带用户数*
					</td>
					<td class="content" style="width: 23%">
						<input type="text" class="text" id="projectStartPoint"  name="projectStartPoint" value="${pnrTransferOffice.projectStartPoint}" alt="allowBlank:true,maxLength:100,vtext:'请输入项目起点，最大长度为50个汉字！'" />
					</td>
					<td class="label" style="width: 10%">
						IPTV用户数*
					</td>
					<td class="content" style="width: 23%">
						<input type="text" class="text" id="projectEndPoint"  name="projectEndPoint" value="${pnrTransferOffice.projectEndPoint}" alt="allowBlank:true,maxLength:100,vtext:'请输入项目终点，最大长度为50个汉字！'" />
					</td>
					<td class="label" style="width: 10%">
						机房内有无BBU*
					</td>
					<td class="content" style="width: 24%">
						<input type="text" class="text" id="specificLocation"  name="specificLocation" value="${pnrTransferOffice.specificLocation}" alt="allowBlank:true,maxLength:200,vtext:'请输入具体地点，最大长度为100个汉字！'" />
					</td>
				</tr> -->
			</div>
		</table>
	</fieldset>
	<!-- 优化信息 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			<B>优化信息</B>
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width: 10%">
					是否需要杆路投资*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="layingCable" name="layingCable" class="text" 
						value="${pnrTransferOffice.layingCable}" onblur="checkMainEngineeringQuantity(this,'光缆');" />
				</td>
				
				<td class="label" style="width: 10%">
					是否需要光缆投资*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="rightingDemolitionPole" name="rightingDemolitionPole" class="text" 
						value="${pnrTransferOffice.rightingDemolitionPole}"
						onblur="checkMainEngineeringQuantity(this,'电杆');" />
				</td>
				<td class="label" style="width: 10%">
					新建杆路长度*（1000M以内）
				</td>
				<td class="content" style="width: 24%">
					<input type="text" id="wireLaying" name="wireLaying" class="text" 
						value="${pnrTransferOffice.wireLaying}"
						onblur="checkMainEngineeringQuantity(this,'钢绞线');" />
				</td>				
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					新建杆路投资*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="repairPipeline" name="repairPipeline" class="text" 
						value="${pnrTransferOffice.repairPipeline}"
						onblur="checkMainEngineeringQuantity(this,'管道');" />
				</td>
				<td class="label" style="width: 10%">
					原光缆路由简述*（如A-B-C-D）
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="excavationTrench" name="excavationTrench" class="text" 
						value="${pnrTransferOffice.excavationTrench}"
						onblur="checkMainEngineeringQuantity(this,'光交接箱');" />
				</td>
				<td class="label" style="width: 10%">
					新光缆路由简述*（如A-E-F-D）
				</td>
				<td class="content" style="width: 24%">
					<input type="text" id="fiberOpticCableConnector" name="fiberOpticCableConnector" class="text" 
						value="${pnrTransferOffice.fiberOpticCableConnector}"
						onblur="checkMainEngineeringQuantity(this,'光分路器箱');" />
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					新建段落（如E-F）
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="wireLaying" name="wireLaying" class="text" 
						value="${pnrTransferOffice.wireLaying}"
						onblur="checkMainEngineeringQuantity(this,'钢绞线');" />
				</td>
				<td class="label" style="width: 10%">
					光缆布放芯数*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="excavationTrench" name="excavationTrench" class="text" 
						value="${pnrTransferOffice.excavationTrench}"
						onblur="checkMainEngineeringQuantity(this,'光交接箱');" />
				</td>
				<td class="label" style="width: 10%">
					光缆布放长度*（5KM以内）
				</td>
				<td class="content" style="width: 24%">
					<input type="text" id="fiberOpticCableConnector" name="fiberOpticCableConnector" class="text" 
						value="${pnrTransferOffice.fiberOpticCableConnector}"
						onblur="checkMainEngineeringQuantity(this,'光分路器箱');" />
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width: 10%">
					光缆投资估算*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="wireLaying" name="wireLaying" class="text" 
						value="${pnrTransferOffice.wireLaying}"
						onblur="checkMainEngineeringQuantity(this,'钢绞线');" />
				</td>
				<td class="label" style="width: 10%">
					线路总投资估算*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" id="excavationTrench" name="excavationTrench" class="text" 
						value="${pnrTransferOffice.excavationTrench}"
						onblur="checkMainEngineeringQuantity(this,'光交接箱');" />
				</td>
				<div id="twoDiv">
					<!-- <td class="label" style="width: 10%">
						设备板卡需求*
					</td>
					<td class="content" style="width: 24%">
						<input type="text" id="boardDemand" name="boardDemand" class="text" value="${pnrTransferOffice.boardDemand}" />
					</td> -->
				</div>
			</tr>
			<tr>
				<div id="threeDiv">
					<!-- <td class="label" style="width: 10%">
						设备光模块需求*
					</td>
					<td class="content" style="width: 23%">
						<input type="text" id="lightModuleDemand" name="lightModuleDemand" class="text" value="${pnrTransferOffice.lightModuleDemand}" />
					</td> -->
				</div>
				<td class="label" style="width: 10%">
					设备类投资估算*
				</td>
				<td class="content" style="width: 23%" colspan="3">
					<input type="text" id="excavationTrench" name="excavationTrench" class="text" 
						value="${pnrTransferOffice.excavationTrench}"
						onblur="checkMainEngineeringQuantity(this,'光交接箱');" />
				</td>
			</tr>
			
			<tr>
				<td class="label" style="width:10%">
					备注*
				</td>
				<td colspan="5" style="width:90%">
					<textarea id="constructionReasons" name="constructionReasons" class="textarea max" alt="allowBlank:false,maxLength:1000,vtext:'请输入建设原因及必要性，最大长度为500个汉字！'">${pnrTransferOffice.constructionReasons}</textarea>
				</td>
			</tr>
			
			<tr>
				<td class="label">
					新建线路路由简图*
				</td>
				<td colspan="5">
					<eoms:attachment name="sheetMain" property="sheetAccessories"
						scope="request" idField="sheetAccessories"
						appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件" />

				</td>
			</tr>
			
			<tr>
				<td class="label">
					附件
				</td>
				<td colspan="5">
					<eoms:attachment name="sheetMain" property="sheetAccessories"
						scope="request" idField="sheetAccessories"
						appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件" />

				</td>
			</tr>
		    <tr>
		    	<td class="label" style="width:10%">
					新光缆段落起点照片*
				</td>
				<td colspan="2" style="width:40%">
					<div id="photoDiv" style="display: none">
						<table id="myPhotoTable">
							${photoList }
						</table>
					</div>
					<input class="text" type="hidden" name="photoIds" readonly="true"
						id="photoIds" alt="allowBlank:true" value="${photoIds }" />
					<input type="hidden" name="mainResId" id="mainResId" value="" />
					<input type="button" class="btn" value="选择" onclick="selectPhoto()"
						id="sig" />
				</td>
				<td class="label" style="width:10%">
					新光缆段落终点照片*
				</td>
				<td colspan="2" style="width:40%">
					<div id="photoDiv" style="display: none">
						<table id="myPhotoTable">
							${photoList }
						</table>
					</div>
					<input class="text" type="hidden" name="photoIds" readonly="true"
						id="photoIds" alt="allowBlank:true" value="${photoIds }" />
					<input type="hidden" name="mainResId" id="mainResId" value="" />
					<input type="button" class="btn" value="选择" onclick="selectPhoto()"
						id="sig" />
				</td>
		    </tr>
		</table>
	</fieldset>
	
<!-- 场景选择模板 开始 -->
<div id="sceneMainDiv">
<!-- 记录选择的子场景ID --><input type="hidden" id="childSceneIds" name="childSceneIds" value="${childSceneIds}"/><!-- (childSceneIds)--><br /><br />
<c:if test="${!empty echoChildSceneTableList}">
<c:forEach items="${echoChildSceneTableList}" var="sceneTable" >
<div>子场景-${sceneTable.childSceneName}:
	<input type="hidden" value="${sceneTable.quotaField}" name="${sceneTable.childSceneId}_quotaField" id="${sceneTable.childSceneId}_quotaField" size="120"><br>
	<input type="hidden" value="${sceneTable.existQuotaValue}" name="${sceneTable.childSceneId}_existQuotaValue" size="120" id="${sceneTable.childSceneId}_existQuotaValue"><br>
	<input type="hidden" value="${sceneTable.assistExistQuotaValue}" name="${sceneTable.childSceneId}_assistExistQuotaValue" id="${sceneTable.childSceneId}_assistExistQuotaValue" size="120"><br>
	
	<table name="${sceneTable.childSceneId}_table" id="${sceneTable.childSceneId}_table" class="formTable">
		<tbody>
			<tr>
			<c:forEach items="${sceneTable.tableHeader}" var="tableHeader">
				<td>${tableHeader}</td>
			</c:forEach>
			</tr>
		
		<c:forEach items="${sceneTable.tableData}" var="ds">
			<tr>
				<c:forEach items="${ds}" var="d">
					<td>${d}</td>
				</c:forEach>
			</tr>
		</c:forEach>
		</tbody>
	</table>	
</div>
</c:forEach>
</c:if>

</div> 
<!-- 金额信息 -->
	<fieldset style="border-width: 2px; border-color: #98C0F4;">
		<legend>
			<B>金额信息</B>
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label" style="width: 10%">
					项目金额估算*
				</td>
				<td class="content" style="width: 23%">
					<input type="text" class="text" id="projectAmount"  name="projectAmount" disabled="disabled" value="<fmt:formatNumber value='${pnrTransferOffice.projectAmount!=null&&pnrTransferOffice.projectAmount!=""?pnrTransferOffice.projectAmount:"0"}' pattern='##.##' maxFractionDigits='2' minFractionDigits='0' />" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入整数或小数,且小于50000'"/>(单位:元)
				</td>
			</tr>

		</table>
	</fieldset>
	

<!-- 场景选择模板 结束 -->
	<!-- 多附件框 -->
<!-- <table id="sheet" class="formTable">
	  <tr>
		    
		    <td class="label">
		    	附件1
			</td>	
			<td>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="temp1" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
		    <td class="label">
		    	附件2
			</td>	
			<td>
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="temp2" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>			  
</table>
 -->

<br/>



<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
				派发
			</html:submit>
		</div>
	</html:form>
	<script type="text/javascript">
	/**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var selectedPhotoIds=jq("#photoIds").val();
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=conditionSelectPhoto&selectedPhotoIds='+selectedPhotoIds;
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
	
	
	/**
	* 设置图片名称和ID
	*/
	function setPhoto(photoes,photoIds){
	var photoId = document.getElementById("photoIds");
	photoId.value=photoIds;
		if(photoes.length>0&&photoes.length<=2){
				alert("您选择的图片数量不足三张，请重新选择！");
				}else if(photoes.length>2){
				var photoDiv = document.getElementById("photoDiv");
					photoDiv.style.display="block";
					deleteAllChild();
				for(var i=0;i<photoes.length;i++){
					var id = photoes[i].id;
					var longitude = photoes[i].longitude;
					var latitude = photoes[i].latitude;
					var createtime = photoes[i].createtime;
					var userId = photoes[i].userId;
					addMess(longitude,latitude,createtime,userId);
					}
				}
			}
	
	
	function addMess(longitude,latitude,createtime,userId){
		
		var myTable = document.getElementById("myPhotoTable");
		var newTR = myTable.insertRow(myTable.rows.length);
		var myTdTime1=newTR.insertCell(0);
		var myTdTime2=newTR.insertCell(1);
			myTdTime1.innerHTML = "拍照时间：";
			myTdTime2.innerHTML = createtime;
		
		var myTdPerson1 = newTR.insertCell(2);
		var myTdPerson2 = newTR.insertCell(3);
			myTdPerson1.innerHTML = "拍照人：";
			myTdPerson2.innerHTML = userId;
			
		var myTdLongitude1 = newTR.insertCell(4);
		var myTdLongitude2 = newTR.insertCell(5);
			myTdLongitude1.innerHTML = "经度：";
			myTdLongitude2.innerHTML = longitude;
			
		var myTdLatitude1 = newTR.insertCell(6);
		var myTdLatitude2 = newTR.insertCell(7);
			myTdLatitude1.innerHTML = "纬度：";
			myTdLatitude2.innerHTML = latitude;
	}
	
	function deleteAllChild(){
		
		var myTable = document.getElementById("myPhotoTable");
		while(myTable.hasChildNodes()){
		myTable.removeChild(myTable.lastChild);
		}
		
	}
	</script>
<!--

//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
  
  
</script>  