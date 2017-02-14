<%@ page language="java" pageEncoding="utf-8"%>
<script type="text/javascript">
//同步生成子场景
function createChildScene(val,name){
	var strChild="";//生成的子场景的html串
	//动态加载子场景的多选框
	var childSceneUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=getChildScene&mainId="+val;
	  jq.ajax({
			type: "post",
	 		url: childSceneUrl,
	 		async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data) {
		        jq(data).each(function () {
		          strChild=strChild+"<input name='"+val+"-check' type='checkbox' value='"+this.id+"' />"+this.name;
		        });
		      strChild="<div id='"+val+"-cond'>"+name+":"+strChild+"</div>";
	       	  jq("#childSceneCon").append(strChild); 
	       	},
	 		error: function (err) {
	           alert(err);
	        }	
        });
}

jq(document).delegate("input[name=mainScene]", "click", function(){
	 var flag=jq(this).attr("checked");
	 var obj=jq(this);//当前对象
	 var val=jq(this).val();//主场景的id值
	 var name=jq(this).next('span').text();
	 if(flag==true){
	 	//var strChild="";//生成的子场景的html串
		//动态加载子场景的多选框
		var childSceneUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=getChildScene&mainId="+val;
	 	var otherObj=jq("input[name=mainScene]:checked").not(obj);
	 	var otherObjVal=otherObj.val();
	 	if(otherObj.length==0){//第一次点
	 		//和下面的是一致的（暂时先这么写）
			createChildScene(val,name);
	        //到此结束
	        
	 	}else{
	 		otherObj.attr("checked",false);//显示上先去掉
	 		if(confirm("确定要选择该主场景吗？主场景只允许单选，之前选择的主场景相关的所有子场景和填写的信息，都将被删除！")){
	 			//先清空被取消选择的主场景的所有数据
			 	jq("#"+otherObjVal+"-cond").remove();
			 	jq("div[id^="+otherObjVal+"][id$=outlayer]").remove();
			 	otherObj.attr("checked",false);
			 	calTotalAmount();//计算总金额
			 	
			 	//和上面的是一致的（暂时先这么写）
				createChildScene(val,name);
		        //到此结束
		        
	        }else{
	        	otherObj.attr("checked",true);
	        	obj.attr("checked",false);
	        }
	 	}
	 }else{
	 	if(confirm("确定要取消该主场景的选择吗？确定后，相关的所有子场景和填写的信息，都将被删除！")){
		 	jq("#"+val+"-cond").remove();
		 	jq("div[id^="+val+"][id$=outlayer]").remove();
		 	calTotalAmount();//计算总金额
	 	}else{
	 		jq(this).attr("checked",true);
	 	}
	 }
});
	
	
//同步生成子场景明细
function createSceneDetail(idSign,mainSceneVal,val){	

			//动态加载子场景对应的详细数据信息
			var childSceneUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=getSceneDetail&mainId="+mainSceneVal+"&childId="+val;
			jq.ajax({
			type: "post",
	 		url: childSceneUrl,
	 		async: false,//控制同步        
			contentType: "application/json; charset=utf-8",
	 		dataType: "json",
	 		cache: false,
	 		success: function (data){
				if(data!=null){
					var arrayObj = new Array();
					var i=0;
					
					//材料
			        jq(data.materials.mdetail).each(function () {
			        	var temp="";
			        	var tempName="";
			        	//判断是否为下拉选
			        	if(this.cname.ctype=="select"){
			        		 jq(this.cname.cvalue).each(function () {
			        		 	tempName+="<option value="+this.id+">"+this.name+"</option>";
			        		 });
			        		 tempName="<select name='"+idSign+"-"+this.item_no+"-material'>"+tempName+"</select>";
			        	}
			        	temp+="<td>"+tempName+"</td>";
			        	//数量
			        	if(data.isHave=="1"){
			     			 temp+="<td class='cnum'><input type='text' name='"+idSign+"-"+this.item_no+"-num' value='"+this.cnum+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-oldnum' value='"+this.cnum+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-quotanum' value='"+this.cnum+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-oldquotanum' value='"+this.cnum+"' /></td>";
			    		}else{
			     	 		temp+="<td class='cnum'><input type='text' name='"+idSign+"-"+this.item_no+"-num' value='"+this.cnum+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-oldnum' value='"+this.cnum+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-quotanum' value='"+this.cnum+"' /></td>";
			     		}
			        	//单位
			        	temp+="<td>"+this.cunit+"<input type='hidden' name='"+idSign+"-"+this.item_no+"-cunit' value='"+this.cunit+"'/></td>";
			        	//单价
			        	temp+="<td class='cprice'><input type='text' name='"+idSign+"-"+this.item_no+"-price' value='"+this.cprice+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-oldprice' value='"+this.cprice+"' /><input type='hidden' name='"+idSign+"-"+this.item_no+"-initialprice' value='"+this.cprice+"' /></td>";
			        	//总额
			        	temp+="<td class='ctotal'>"+this.ctotal+"<input type='hidden' name='"+idSign+"-"+this.item_no+"-ctotal' value='"+this.ctotal+"'/></td>";
			        	//是否利旧
			        	temp+="<td class='cutilize'><select name='"+idSign+"-"+this.item_no+"-utilize' ><option value='0'>否</option><option value='1'>是</option></select></td>";
			        	arrayObj[i]=temp
			        	i++;
			        });
			        
			     var divStr="";
			     //表格外层div标签
			     divStr+="<div id='"+idSign+"-outlayer' class='outlayer'>";
			     
			     divStr+="<div id='"+idSign+"-photolayer' class='photolayer'><img name='"+idSign+"-img' class='imgcss' src='/partner/images/icons/delete.gif' /></div>";
			     
			     //table标签
			     divStr+="<table name='"+idSign+"-childTable' class='formTable'>";
			     //表头
			     divStr+="<tr><td>主场景</td><td>子场景</td><td>处理措施</td><td>单位</td><td>材料</td><td>数量</td><td>单位</td><td>单价</td><td>总额</td><td>是否利旧</td></tr>";//是否利旧
			     //表格第一行数据 
			     divStr+="<tr><td rowspan='"+data.itemSize+"'>"+data.main.name+"<input type='hidden' name='"+idSign+"-sceneName' value='"+data.main.name+"'/></td><td rowspan='"+data.itemSize+"'>"+data.child.name+"<input type='hidden' name='"+idSign+"-copyName' value='"+data.child.name+"'/></td><td rowspan='"+data.itemSize+"'>"+data.measure.name+"<input type='hidden' name='"+idSign+"-dispose' value='"+data.measure.name+"'/></td>";
			     if(data.isHave=="1"){
			     	 divStr+="<td rowspan='"+data.itemSize+"'><input type='text' value='1' name='"+idSign+"-unit' /><input type='hidden' value='1' name='"+idSign+"-oldunit' /><input type='hidden' value='"+data.unit.name+"' name='"+idSign+"-unitname' />"+data.unit.name+"</td>";
			     }else{
			     	 divStr+="<td rowspan='"+data.itemSize+"'>"+data.unit.name+"<input type='hidden' value='1' name='"+idSign+"-unit'/><input type='hidden' value='"+data.unit.name+"' name='"+idSign+"-unitname' /></td>";
			     }
			     divStr+=arrayObj[0];
			     divStr+="</tr>";
			     //表格其他行的数据
			     for(var j=1;j<arrayObj.length;j++){
	 				divStr+="<tr>"+arrayObj[j]+"</tr>";
				 }
				 //table结束标签
				 divStr+="</table>";
				 
				 //div结束标签
				 divStr+="</div>";
				 
				// alert(divStr);
			     
		       	  jq("#childSceneShow").append(divStr); 
		       	  
		       	  calTotalAmount();//计算总金额
				}
	},
			 		error: function (err) {
			           alert("选择子场景失败");
			        }
		        });
}	
jq(document).delegate("input[name$='-check']", "click", function(){
	//	alert("33333");
		var flag=jq(this).attr("checked");
		var obj=jq(this);
		var val=jq(this).val();
		var nameVal=jq(this).attr("name");
		mainSceneVal=nameVal.substr(0,nameVal.indexOf('-'));
		var idSign=mainSceneVal+"-"+val;
		if(flag==true){
			var otherObj=jq("input[name="+nameVal+"]:checked").not(obj);
	 		var otherObjVal=otherObj.val();
		    if(otherObj.length==0){//第一次点
			    createSceneDetail(idSign,mainSceneVal,val);
		    }else{
		    	otherObj.attr("checked",false);//显示上先去掉
		 		if(confirm("确定要选择该子场景吗？子场景只允许单选，之前选择的子场景相关的填写的信息，都将被删除！")){
		 			jq("#"+mainSceneVal+"-"+otherObjVal+"-outlayer").remove();
		 			calTotalAmount();//计算总金额
		 		
		 		    createSceneDetail(idSign,mainSceneVal,val);
		 		}else{
		 			otherObj.attr("checked",true);
	        	    obj.attr("checked",false);
		 		}
		    }
		}else{
			if(confirm("确定要取消该子场景的选择吗？确定后，填写相关的信息，都将被删除！")){
				jq("#"+mainSceneVal+"-"+val+"-outlayer").remove();
				var num=jq("input[name="+nameVal+"]:checked").size();
				//alert(num);
				if(num=='0'){
					jq("#"+mainSceneVal+"-cond").remove(); //移除所有该子场景
					jq("input[name='mainScene'][value="+mainSceneVal+"]").attr("checked",false);//设置对应的主场景未被选择
				}
				calTotalAmount();//计算总金额
			}else{
				jq(this).attr("checked",true);
			}
		}
	}); 

//材料改变	
jq(document).delegate("select[name$='-material']", "change", function(){
	//alert(jq(this).val());
	
	//材料下拉选的值
	var materialObj=jq(this);
	var materialVal=materialObj.val();
	var _trObj=materialObj.parents("tr");
	//alert(_trObj.find("td[class='cprice']").find("span").text());
	var price=0;
	var num=0;
	var total=0;
	//通过材料动态加载单价
	var priceUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=getPriceByMaterial&materialVal="+materialVal;
	jq.getJSON(priceUrl,function (data) {
		price = data.cpric;
		//_trObj.find("td[class='cprice']").find("span").text(price);//设置新单价
		_trObj.find("td[class='cprice']").find("input[name$='-price']").val(price);//设置新单价
		_trObj.find("td[class='cprice']").find("input[name$='-oldprice']").val(price);//设置新单价变化之前的值
		_trObj.find("td[class='cprice']").find("input[name$='-initialprice']").val(price);//设置初始单价
		 num = _trObj.find("td[class='cnum']").find("input[name$='-num']").val();//取数量
	 	total = calSingleTotal(price,num);
		var ctotalTdObj=_trObj.find("td[class='ctotal']");
		var ctotalInputObj=ctotalTdObj.find("input");
		ctotalTdObj.text(total)
		ctotalInputObj.val(total);
		ctotalTdObj.append(ctotalInputObj);
		//_trObj.find("td[class='ctotal']").text(total);
		
		calTotalAmount();//计算总金额
    }); 	
});

//计算单个总额
function calSingleTotal(price,num){
	return toDecimal(price*num);	
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
function formatStringToFloat(strValue){
	if(strValue!=""){
		return parseFloat(strValue);
	}else{
		return parseFloat("-2");
	}	
}
//数量变
jq(document).delegate("input[name$='-num']","keyup", function(){
	//alert(jq(this).val());
	var numObj=jq(this);//数量对象
	var num=numObj.val();//数量
	var oldnumObj=numObj.parents("td").find("input[name$='-oldnum']");//上一次数量对象
	var oldnum=oldnumObj.val();//上一次数量
		if(oldnum.charAt(oldnum.length-1)=='.'){
		oldnum=oldnum.substring(0,oldnum.length-1);
	}
	var quotanumObj=numObj.parents("td").find("input[name$='-quotanum']");//定额对象
	var quotanum=quotanumObj.val();//定额
	
	//alert(parseFloat(num));
	
	//var a=/^\d+(\.\d+)?$/; //包括0的正整数
	//var a=/^[0-9]\\d*(\\.\\d+)?$/; //包括0的正整数
	var a=/^[0-9]+\.{0,1}[0-9]{0,2}$/; //包括0的正整数
	if(num==""){
		alert("数量不能为空！");
		numObj.val(oldnum);
		return;
	}else if(!a.test(num)){
		alert("请输入大于等于0的整数！");
		numObj.val(oldnum);
		return;
	}else if(parseFloat(num) > parseFloat(quotanum)){
		alert("超出定额，定额值为："+quotanum);
		numObj.val(oldnum);
		return;
	}else{
		var _trObj=numObj.parents("tr");
		//var price=_trObj.find("td[class='cprice']").find("span").text();//单价
		var price=_trObj.find("td[class='cprice']").find("input[name$='-price']").val();//单价
		total = calSingleTotal(price,num);
		var ctotalTdObj=_trObj.find("td[class='ctotal']");
		var ctotalInputObj=ctotalTdObj.find("input");
		ctotalTdObj.text(total)
		ctotalInputObj.val(total);
		ctotalTdObj.append(ctotalInputObj);
		//_trObj.find("td[class='ctotal']").text(total);
		
		oldnumObj.val(num);
		
		calTotalAmount();//计算总金额
	}
});


//单位变
jq(document).delegate("input[name$='-unit']","keyup", function(){
	//alert(jq(this).val());
	var unitObj=jq(this);//单位对象
	var unit=unitObj.val();//单位
	var oldunitObj=unitObj.parents("td").find("input[name$='-oldunit']");//上一次单位对象
	var oldunit=oldunitObj.val();//单位
	
	var a=/^\d+(\.\d+)?$/; //包括0的正整数
	if(unit==""){
		alert("单位不能为空！");
		unitObj.val(oldunit);
		return;
	}else if(!a.test(unit)){
		alert("请输入大于等于1的整数！");
		unitObj.val(oldunit);
		return;
	}else if(parseFloat(unit) < 1){
		alert("请输入大于等于1的整数！");
		unitObj.val(oldunit);
		return;
	}else{
		var oldquotaObjs=unitObj.parents("table").find("input[name$='-oldquotanum']");
		//判断一下数量的值，定额修改后，是否超出，超出不让修改
		var flag = false;
		oldquotaObjs.each(function(){
			if(jq(this).parents("td").find("input[name$='-num']").val() > jq(this).val()*unit){
				flag=true;
			}
  		});
  		
  		if(flag==true){
  			alert("当前数量值已近超出修改后的定额值，请先修改数量，再修改定额！");
  			unitObj.val(oldunit);
  			return;
  		}else{
  			oldquotaObjs.each(function(){
				jq(this).parents("td").find("input[name$='-quotanum']").val(jq(this).val()*unit);
  			});
  			oldunitObj.val(unit);
  		}
	}
});

//单价变 0=<单价<=单价*20%
jq(document).delegate("input[name$='-price']","keyup", function(){
	var priceObj=jq(this);//单价对象
	var price=priceObj.val();//单价
	var oldpriceObj=priceObj.parents("td").find("input[name$='-oldprice']");//上一次单价对象
	var oldprice=oldpriceObj.val();//上一次单价
	var initialpriceObj=priceObj.parents("td").find("input[name$='-initialprice']");//初始单价对象
	var initialprice=initialpriceObj.val();//初始单价对象
	var maxprice=initialprice*(1+0.2);//单价上线20%
	//alert(maxprice);
	
	var a=/^[0-9]+\.{0,1}[0-9]{0,10}$/; //大于等于0的数
	if(price==""){
		alert("单价不能为空！");
		priceObj.val(oldprice);
		return;
	}else if(!a.test(price)){
		alert("请输入大于等于0的数！");
		priceObj.val(oldprice);
		return;
	}else if(parseFloat(price) > parseFloat(maxprice)){
		alert("修改后的单价超过标准单价的20%");
		priceObj.val(oldprice);
		return;
	}else{
		var _trObj=priceObj.parents("tr");
		var num=_trObj.find("td[class='cnum']").find("input[name$='-num']").val();//数量
		total = calSingleTotal(price,num);
		var ctotalTdObj=_trObj.find("td[class='ctotal']");
		var ctotalInputObj=ctotalTdObj.find("input");
		ctotalTdObj.text(total)
		ctotalInputObj.val(total);
		ctotalTdObj.append(ctotalInputObj);
		//_trObj.find("td[class='ctotal']").text(total);
		
		oldpriceObj.val(price);
		calTotalAmount();//计算总金额
	}
});



//计算总金额
function calTotalAmount(){
	var total=0;
	var objs=jq("table[name$='-childTable']").find("td[class='ctotal']");
	if(objs.length > 0){
		objs.each(function(){
			total+=parseFloat(jq(this).text());
  		});
	}
	total=toDecimal(total);
	jq("#totalAmountSpan").text(total);
	jq("#totalAmount").val(total);
}


//删除
jq(document).delegate("img[name$='-img']","click", function(){
   if(confirm("确定要删除吗？确定后，填写的数据将被彻底删除！")){
     	var imgObj=jq(this);
		var nameVal=imgObj.attr("name");
		var mainSceneVal=nameVal.substring(0,nameVal.indexOf('-'));
		var childSceneVal=nameVal.substring(nameVal.indexOf('-')+1,nameVal.indexOf('-',nameVal.indexOf('-')+1));
		var idSign=mainSceneVal+"-"+childSceneVal;
		//删除所在的outlayerdiv
		jq("#"+idSign+"-outlayer").remove();
		
		//取消子场景中的选择
		jq("input[name='"+mainSceneVal+"-check'][value='"+childSceneVal+"']").attr("checked",false);
		
		var num=jq("input[name='"+mainSceneVal+"-check']:checked").size();
		
		if(num=='0'){
			jq("#"+mainSceneVal+"-cond").remove(); //移除所有该子场景
			jq("input[name='mainScene'][value="+mainSceneVal+"]").attr("checked",false);//设置对应的主场景未被选择
		}
		calTotalAmount();//计算总金额
   }
});

//是否利旧
jq(document).delegate("select[name$='-utilize']","change", function(){
	var utilizeObj=jq(this);
	var utilizeVal=utilizeObj.val();
	var _trObj=utilizeObj.parents("tr");
	if(utilizeVal == '1'){
		//设置单价为0
		_trObj.find("input[name$='-price']").val('0');
		_trObj.find("input[name$='-oldprice']").val('0');
		
		//计算总额
		var ctotalTdObj=_trObj.find("td[class='ctotal']");
		var ctotalInputObj=ctotalTdObj.find("input");
		ctotalTdObj.text('0')
		ctotalInputObj.val('0');
		ctotalTdObj.append(ctotalInputObj);
		
		//计算总金额
		calTotalAmount();
	}else if(utilizeVal == '0'){
		var utilizeName=utilizeObj.attr("name");
		//alert(utilizeName);
		var names = utilizeName.split('-');
		//主场景的值
		var mainSceneVal=names[0];
		//alert(mainSceneVal);
		//子场景的值
		var childSceneVal=names[1]
		//alert(childSceneVal);
		//item_no的值
		var itemNoVal=names[2];
		//alert(itemNoVal);
		//该行材料字段当前下拉选的值
		var materialVal = _trObj.find("select[name$='-material']").val();
		//alert(materialVal);
		
		//通过材料动态加载单价
		var priceUrl="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=getPriceByMaterial&materialVal="+materialVal;
		jq.getJSON(priceUrl,function (data) {
			price = data.cpric;
			//_trObj.find("td[class='cprice']").find("span").text(price);//设置新单价
			_trObj.find("td[class='cprice']").find("input[name$='-price']").val(price);//设置新单价
			_trObj.find("td[class='cprice']").find("input[name$='-oldprice']").val(price);//设置新单价变化之前的值
			_trObj.find("td[class='cprice']").find("input[name$='-initialprice']").val(price);//设置初始单价
			 num = _trObj.find("td[class='cnum']").find("input[name$='-num']").val();//取数量
		 	total = calSingleTotal(price,num);
			var ctotalTdObj=_trObj.find("td[class='ctotal']");
			var ctotalInputObj=ctotalTdObj.find("input");
			ctotalTdObj.text(total)
			ctotalInputObj.val(total);
			ctotalTdObj.append(ctotalInputObj);
			//_trObj.find("td[class='ctotal']").text(total);
		
			calTotalAmount();//计算总金额
	    }); 
	}
});


</script>
