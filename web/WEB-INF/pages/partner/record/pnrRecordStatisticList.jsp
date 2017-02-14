<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    function sendBox() {
    	$("exportFlag").value="1";//执行的是统计操作
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
		if(idResult==""){
			alert("请至少选择一个统计项");
			return false;
		}
		document.getElementById("checkAndSearchFrom").submit();
	}
	function res(){
		var formElement=document.getElementById("checkAndSearchFrom")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
	   	 document.getElementById("area_id").value="";
	   	 document.getElementById("maintainCompany_id").value="";
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
     	$("hasSend").value="nook";//需要重新统计，否则导出功能会出错
     	changeCheckBox();
	}
	function changeCheckBox(){
		var area = myjs('#area2').val();
		var company=myjs('#maintainCompany').val();
		if(area){
			myjs('#area').attr('checked',true);
			myjs('#area').attr('disabled','disabled');
		}else{
			if((myjs('#area').attr('disabled'))){
			     myjs('#area').attr('checked',false);
			     myjs('#area').attr('disabled','');
			}
		}
		if(company){
			myjs('#company').attr('checked',true);
			myjs('#company').attr('disabled','disabled');
		}else{
			if((myjs('#company').attr('disabled'))){
			    myjs('#company').attr('checked',false);
			    myjs('#company').attr('disabled','');
			}
		}
	}
		//显示勾选框和统计图像
	Ext.onReady(function(){
		//显示已经勾选的框
		var check=document.getElementById("checkedList");
		if(check&&check.value!=""){
			//先清空所有的勾选框
			var all=document.getElementsByName("statisticsItem");
			for (i = 0 ; i <all.length; i ++) {
				var checkValue="#"+all[i].id;
				//alert(checkValue);
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
		 	alert("请先完成统计!");
			return;
		}
	};
</script>

<form id="checkAndSearchFrom" action="${app }/partner/record/recordAction.do?method=statisticSubmit" method="post">
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
	<tr>
               <td class="label">
                   发布时间从
               </td>
               <td>
	            	<input type="text" name="beginTime" id="beginTime" value="" alt="allowBlank:false,vtype:'lessThen',link:'endTime',vtext:'${eoms:a2u("开始时间不能为空或晚于结束时间")}'" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1);" class="text"/>
               
               </td>	      
               <td class="label">
                   到
               </td>
               <td> 
               	<input type="text" name="endTime" id="endTime" value="" alt="allowBlank:false,vtype:'moreThen',link:'beginTime',vtext:'${eoms:a2u("结束时间不能为空或早于开始时间")}'" readonly="readonly" onclick="popUpCalendar(this, this,null,null,null,true,-1);" class="text"/>
               
               </td>     
</tr>
		</table>
	</fieldset>
	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					<input id="city" type="checkbox" name="statisticsItem" 	value="cityTypeLikeCity" checked="checked" />
					地市
				</td>
				<td class="label">
					<input id="country" type="checkbox" name="statisticsItem"	value="countryTypeLikeCountry" checked="checked" />
					区县
				</td>
			</tr>

			<input type="hidden" name="statisticsItems" id="statisticsItems" />
			<input type="hidden" name="checkedIds" id="checkedIds" />
			<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
			<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
			<input type="hidden" name="exportFlag" id="exportFlag" >
		</table>
	</fieldset>

	<input type="button" name="统计" value="统计" onclick="sendBox()" />
	<input type="button" name="重置" value="重置" onclick="res();" />
	<logic-el:present name="tableList">
		<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</logic-el:present>
	</form>

	<display:table name="resultList" cellspacing="0" cellpadding="0"
		id="resultList" pagesize="${pageSize}" class="table resultList"
		export="false"
		requestURI="../record/recordAction.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<c:if test="${not empty cityFlag}">
	<display:column sortable="true" headerClass="sortable" title="地市" >
		<eoms:id2nameDB id="${resultList.city}" beanId="tawSystemAreaDao" />
	</display:column>	
    </c:if>	
	<c:if test="${not empty countryFlag}">
	<display:column sortable="true" headerClass="sortable" title="区县" >
		<eoms:id2nameDB id="${resultList.country}" beanId="tawSystemAreaDao" />
	</display:column>
    </c:if>	
	<display:column sortable="true" headerClass="sortable" title="更新率(100%)" >
		${resultList.updatenum}
	</display:column>		
	<display:column sortable="true" headerClass="sortable" title="完善率(100%)" >
		${resultList.updatenum}
	</display:column>	
	<display:column sortable="true" headerClass="sortable" title="下载次数" >
		${resultList.donwloadnum}
	</display:column>	
	</display:table>	
		<%@ include file="/common/footer_eoms.jsp"%>