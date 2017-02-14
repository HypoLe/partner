<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
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
   v = new eoms.form.Validation({form:'theform'});
   
   v.custom = function(){ 
    if(eoms.$('jishuFlag').value.trim()=="0"){
       alert("未添加项目金额估算内容！"); return false; 
     } 
     
     var x=parseInt(eoms.$('projectAmount').value.trim());
     
     if(x <= 0){
       alert("金额值异常！"); return false; 
     } 
     
      return true;
    }	
    
  });

   function changeType1(){
       var myDate=new Date();
       var b =$('sheetCompleteLimit').value*60;
       myDate.setMinutes(myDate.getMinutes() + b, myDate.getSeconds(), 0)
       $('dueDate').value = myDate.format('yyyy-MM-dd hh:mm:ss');
   }
</script>





<script type="text/javascript">	
	jq(function(){	
		
		var tempJiShu=0; //提交的表格数据的行数
	
		//获取类别下拉选的值
		var categoryUrl="${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=getCategory";
		jq.getJSON(categoryUrl,function (data) {
                jq(data).each(function () {
                	jq("<option/>").html(this.name).val(this.id).appendTo("#categorySelect");
                });

            });
        
        //根据选中的类别的值获取场景分类
            jq("#categorySelect").change(function () {
                jq("#sceneSelect").empty();
                jq("#sceneSelect").append(jq("<option/>").text("请选择").attr("value",""));
                varurl="${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=getScene";
                //将选中的一级下拉列表项的id传过去
                jq.getJSON(varurl, { id:jq(this).attr("value") },function (data) {
                    jq(data).each(function () {
                        jq("#sceneSelect").append(jq("<option/>").text(this.name).attr("value",this.id));

                    });

                });

            });
            
         //根据场景分类，显示种类选择框
          jq("#sceneSelect").change(function () {
          	jq("#kindSelect").empty();
                jq("#kindSelect").append(jq("<option/>").text("请选择").attr("value",""));
                varurl="${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=getKind";
                //将选中的一级下拉列表项的id传过去
                jq.getJSON(varurl, { id:jq(this).attr("value") },function (data) {
                    jq(data).each(function () {
                        jq("#kindSelect").append(jq("<option/>").text(this.name).attr("value",this.id));

                    });

                });

            });

			//添加按钮事件
			var i = 0;            
            jq("#BtAdd").click(function(){
           		var tempCategoryVal=jq("#categorySelect").val();
            	var tempSceneVal=jq("#sceneSelect").val();	
            	var tempKindVal=jq("#kindSelect").val();
            	var tempQuantityVal=jq("#quantity").val();
            	if(tempCategoryVal==""){
            		alert("类别不能为空");
            		return false;
            	}
            	if(tempSceneVal==""){
            		alert("场景分类不能为空");
            		return false;
            	}
            	if(tempKindVal==""){
            		alert("种类不能为空");
            		return false;
            	}
            	
            	var ex = /^\d+$/;
            	if(tempQuantityVal==""){
            		alert("数量不能为空"); 
            		return false;
            	}else if(!ex.test(tempQuantityVal)){//判断一下数量是否为正整数
            		alert("数量应为正整数");
            		return false;
            	}
            	
            	//要加一下表格中是否已经存在了类别+场景+种类 ，稍后做
            	
            	var tempCategoryText=jq("#categorySelect").find("option:selected").text();
            	var tempSceneText=jq("#sceneSelect").find("option:selected").text();	
            	var tempKindText=jq("#kindSelect").find("option:selected").text();
            	
         /*   	var flag=false;
            	jq("#articleListTable tr:gt(0)").each(function() {
                   var tdCategoryText = jq(this).find("td").get(2).innerHTML;
                   var tdSceneText = jq(this).find("td").get(3).innerHTML;
                   var tdKindText = jq(this).find("td").get(4).innerHTML;
                   if(tdCategoryText==tempCategoryText&&tdSceneText==tempSceneText&&tdKindText==tempKindText){
                   		flag=true;
                   		alert("类别："+tempCategoryText+";场景分类："+tempSceneText+";种类："+tempKindText+";已存在。");
                   		return false;
                   };             
                });
           
           		if(!flag){ */
           		   var	newTrString="<tr> <td style='width:25px;'>"+(++i)+"</td> <td style='width:25px;'><input name='CK' type='checkbox'/> <input name='categoryHidden"+i+"' type='hidden' value='"+tempCategoryVal+"'/> <input name='sceneHidden"+i+"' type='hidden' value='"+tempSceneVal+"'/> <input name='kindHidden"+i+"' type='hidden' value='"+tempKindVal+"'/> <input name='quantityHidden"+i+"' type='hidden' value='"+tempQuantityVal+"'/></td> <td>"+tempCategoryText+"</td> <td >"+tempSceneText+"</td> <td>"+tempKindText+"</td> <td>"+tempQuantityVal+"</td> </tr>";
           		   var newTr =	jq(newTrString);
            	   newTr.appendTo("#articleListTable");
            	   jq("#jishuFlag").val(++tempJiShu);
           	//	}
           	 	
            });
            
            //删除按钮事件
	         jq("#BtDel").click(function() {
	                jq("#articleListTable tr:gt(0)").each(function() {
	                    if (jq(this).find("input[name = 'CK']").get(0).checked == true) {
	                        jq(this).remove();
	                        jq("#jishuFlag").val(--tempJiShu);
	                    }
	                });
	            i = 0;
                jq("#articleListTable tr:gt(0)").each(function() {
                    jq(this).find("td").get(0).innerHTML = ++i;
                    jq(this).find("input[type=hidden]:eq(0)").attr("name","categoryHidden"+i);
                    jq(this).find("input[type=hidden]:eq(1)").attr("name","sceneHidden"+i);
                    jq(this).find("input[type=hidden]:eq(2)").attr("name","kindHidden"+i);
                    jq(this).find("input[type=hidden]:eq(3)").attr("name","quantityHidden"+i);
                });
                jq("#CKA").attr("checked", false);
	         }); 
	         
	         //全选按钮事件
	          jq("#CKA").click(function() {          
                jq("#articleListTable tr:gt(0)").each(function() {
                    jq(this).find("input[name = 'CK']").get(0).checked = jq("#CKA").get(0).checked;
                });
            }); 
            
            //取消一个选择，全选不选中，子项选中，全选选中
     		 jq('input[name=CK]').live("click",function(){
			       var ckslength = jq('input[name=CK]').length;
					if(!jq(this)[0].checked){
						jq('#CKA')[0].checked = false;
					}else if(jq(this)[0].checked){
						if(jq('input[name=CK]:checked').length == ckslength){
							jq('#CKA')[0].checked = true;
						}
					}
			   });
            
            //要如何给后台传递表格中的值能？ 
            jq("#submitForAmountBtn").click(function(){
            	var tempJishuFlag=jq("#jishuFlag").val();
            	if(tempJishuFlag=='0'){
            		alert("请添加项目金额估算需要的材料！");
            		return false;
            	}else{
            		//var i=1;
            	//	var tempv="input[name=categoryHidden1]";
            		//alert("diyige="+jq(tempv).val());
            	
            	
            		//拼json串
            		//var data = {records: []};
            		
            		var tempData="";
            		for (var i=1;i<=tempJishuFlag;i++){
            			var categoryHiddenName="input[name=categoryHidden"+i+"]";
            		//	alert(categoryHiddenName)
            			//alert(jq(categoryHiddenName).val());
            			var sceneHiddenName="input[name=sceneHidden"+i+"]";
            			var kindHiddenName="input[name=kindHidden"+i+"]";
            			var quantityHiddenName="input[name=quantityHidden"+i+"]";   
						var row= '{"category":"'+jq(categoryHiddenName).val()+'","scene":"'+jq(sceneHiddenName).val()+'","kind":"'+jq(kindHiddenName).val()+'","quantity":"'+jq(quantityHiddenName).val()+'"}';
						tempData=tempData+row+",";
					}
					tempData=tempData.substring(0,tempData.length-1);
					tempData='{"records": ['+tempData+']}';;
					//alert(tempData);
            	
           		 var urlStr = "${app}/activiti/pnrTransferPrecheck/pnrTransferPrecheck.do?method=submitForAmount";
			      jq.ajax( {
						type : "POST",
						url : urlStr, 
						data :{records:tempData},
						dataType: "text",
						success : function(data){ // 回调函数
							jq("#projectAmount").val(data);//设置金额隐藏域的值，这个值是给后台用的
							jq("#tempProjectAmount").val(data);//设置金额显示域的值，这个值是给前台展示用的
							
							jq("#BtAdd").attr("disabled", true);//设置添加按钮不可用
							jq("#BtDel").attr("disabled", true);//设置删除按钮不可用
							
							//alert(jq("#projectAmount").val());
								
									//alert("金额为："+data);
							
						}	
					});
            	}
            });
            
            
            
	});

</script>

<html:form action="/pnrTransferPrecheck.do?method=performSave" styleId="theform" >
	<input type="hidden" id="viewFlag" name="viewFlag" value="newJob" /><!-- 保存标识 -->
	
	<input id="dueDate" type="hidden" name="dueDate" value="${pnrTransferOffice.endTime}">
    <input id="themeId" type="hidden" name="themeId" value="${pnrTransferOffice.id}">
    <input id="processInstanceId" type="hidden" name="processInstanceId" value="${pnrTransferOffice.processInstanceId}">
    <input id="state" type="hidden" name="state" value="${pnrTransferOffice.state}">
    <input id="themeInterface" type="hidden" name="themeInterface" value="interface">
<table class="formTable">
	<tr>
	  <td class="label">工单名称-sss*</td>
	  <td colspan="3">
	  <c:if test="${pnrTransferOffice.theme ==null}">
	  
	    <input type="text" name="title" class="text max" id="title"
			value="${pnrTransferOffice.theme}" alt="allowBlank:false,maxLength:120,vtext:'请输入工单名称，最大长度为60个汉字！'"/>
	  </c:if>
	  <c:if test="${pnrTransferOffice.theme !=null}">
	<input type="text" name="title" class="text max" value="${pnrTransferOffice.theme}" readOnly=true/>
	  </c:if>
	  </td>
	</tr>

	<tr>
	  <td class="label">操作人*</td>
	  <td class="content">
	  <eoms:id2nameDB id="${userId}" beanId="tawSystemUserDao"/>
	  <input type="hidden" name="initiator" value="${userId}" />
	  </td>
	  
	  <td class="label">操作人部门*</td>
	  <td class="content"><eoms:id2nameDB id="${deptId}" beanId="tawSystemDeptDao"/></td>
	</tr>

</table>
		
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
		<td class="label">
				工单类型*
			</td>
			<td class="content">
					<input type="text" class="text max" name="provName" id="provName"
						value="${pnrTransferOffice.workOrderTypeName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="workOrderType" id="workOrderType" value="${pnrTransferOffice.workOrderType}" type="hidden" />
					<eoms:xbox id="provTree2"
						dataUrl="${app}/xtree.do?method=dictXbox&level=3"
						rootId="1012307" rootText="工单类型" valueField="workOrderType" handler="provName"
						textField="provName" checktype="dict" />
				</td>
			
			
			
			
			<td class="label">
				工单子类型*
			</td>
			<td class="content">
					<input type="text" class="text max" name="subTypeName" id="subTypeName"
						value="${pnrTransferOffice.subTypeName}"
						alt="allowBlank:false" readonly="readonly" />
					<input name="subType" id="subType" value="${pnrTransferOffice.subType}" type="hidden" />
					<eoms:xbox id="provTree"
						dataUrl="${app}/xtree.do?method=dictXbox&level=4"
						rootId="1012307" rootText="工单子类型" valueField="subType" handler="subTypeName"
						textField="subTypeName" checktype="dict" />
				</td>
			
		</tr>
		<tr>
			<td class="label">
				线路属性*
			</td>
			<td class="content">
			<eoms:comboBox name="workType" id="workType"
					defaultValue="${pnrTransferOffice.workType}" initDicId="1012310"
					alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				预检预修申请提交时间*
			</td>
			<td class="content">
				<input type="text" class="text" name="mainFaultOccurTime"
					readonly="readonly" id="mainFaultOccurTime"
					value="${eoms:date2String(pnrTransferOffice.submitApplicationTime)}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1,0);"
					alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				项目金额估算*
			</td>
			 <td class="content"> 
			    <input type="hidden" id="projectAmount" name="projectAmount" value="-1"  />
			    <input type="text" class="text" id="tempProjectAmount" name="tempProjectAmount" value="-1" disabled="disabled" />(单位:元)
		     </td>
			<td class="label">
				预检预修描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" id="mainRemark"
					alt="allowBlank:false,maxLength:2000,vtext:'请填入预检预修描述，最多输入 2000 字符'">${pnrTransferOffice.faultDescription}</textarea>
			</td>
		</tr>
		<tr>
			
			<td class="label">
				关键字*
			</td>
			<td class="content">
				<eoms:comboBox name="keyWord" id="keyWord"
					defaultValue="${pnrTransferOffice.keyWord}" initDicId="1012308"
					alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				紧急程度*
			</td>
			<td class="content">
			<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" />
			</td>
			
		</tr>
</table>
<!-- 附件-->
<table id="sheet" class="formTable">
	  <tr>
		    <td class="label">
		    	附件
			</td>	
			<td colspan="3">
		    <eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="sheetAccessories" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
		          				
		    </td>
	   </tr>			  
</table>
<!-- 项目金额估算 -->
<div style="margin:10 0px;">
	<table id="articleAddTable" class="formTable">
		<tr >
		  <td class="label" colspan="4" style="text-align:center;vertical-align: middle;">项目金额估算</td>
		</tr>
		<tr>
		  <td class="label">类别*</td>
		  <td class="content">
			  <select id="categorySelect" name="categorySelect" class="select">
			  	<option value="">请选择</option>
			  </select>
		  </td>
		  
		  <td class="label">场景分类*</td>
		  <td class="content">
			  <select id="sceneSelect" name="sceneSelect" class="select">
			  <option value="">请选择</option>
			  </select>
		  </td>
		</tr>
		<tr>
		  <td class="label">种类*</td>
		  <td class="content">
			  <select id="kindSelect" name="kindSelect" class="select">
			  	<option value="">请选择</option>
			  </select>
		  </td>
		  
		  <td class="label">数量*</td>
		  <td class="content">
			  <input type="text" id="quantity" name="quantity" style="" />
		  </td>
		</tr>
		<tr>
			<td colspan="4" style="text-align:right">	
				<input id="BtAdd" name="BtAdd" type="button" value="添加" />&nbsp;<input id="BtDel" name="BtDel" type="button" value="删除" />
			</td>
		</tr>
	</table>
</div>

<input type="hidden" id="jishuFlag" name="jishuFlag"  value="0" /><!-- 记录表格条数 -->
<div style="margin:10 0px;">
<table id="articleListTable" class="formTable">
		 <tr>
                <td style="width:25px;"></td>
                <td  style="width:25px;">
                    <input id="CKA" name="CKA" type="checkbox"/></td>
                <td>
                    类别</td>
                <td >
                    场景分类</td>
                <td>
                    种类</td>
                <td>
                    数量</td>
            </tr>
	</table>
</div>




<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				保存
			</html:submit>
		<input type="button" class="btn" id="submitForAmountBtn" name="submitForAmountBtn" value="提交获取金额" />
		</div>
	</html:form>
<%@ include file="/common/footer_eoms.jsp"%>