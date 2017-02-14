<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    
    
    function sendBox() {
    
    changeCheckBox();//add by WangGuangping 2012-3-5 17:16:29 在执行提交操作前执行
    
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var checkId="";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";" ;
			var tempId=cardNumberList[i].id;
			checkId +=tempId.toString()+";" ;
		}
	}
	$("deleteIdss").value=idResult.toString();
	$("checks").value=checkId.toString();
		if(idResult==""){
		alert("请至少选择一个统计项");
				return false;}
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var area_name = myjs('#de0area_nameT').val();
		var brand=myjs('#ct0brandT').val();
		var carState=myjs('#ct0carStateT').val();
		var carOutDate = myjs('#ct0carOutDateT').val();
	
		
		if(area_name){
		myjs('#de0area_name').attr('checked',true);
		myjs('#de0area_name').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0area_name').attr('disabled'))){
		     myjs('#de0area_name').attr('checked',false);
		     myjs('#de0area_name').attr('disabled','');
		}
		}
		if(brand){
		myjs('#ct0brand').attr('checked',true);
		myjs('#ct0brand').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0brand').attr('disabled'))){
		    myjs('#ct0brand').attr('checked',false);
		    myjs('#ct0brand').attr('disabled','');
		}
		}
		if(carState){
		myjs('#ct0carState').attr('checked',true);
		myjs('#ct0carState').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0carState').attr('disabled'))){
		     myjs('#ct0carState').attr('checked',false);
		     myjs('#ct0carState').attr('disabled','');
		}
		}
				if(carOutDate){
		myjs('#ct0carOutDate').attr('checked',true);
		myjs('#ct0carOutDate').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0carOutDate').attr('disabled'))){
		     myjs('#ct0carOutDate').attr('checked',false);
		     myjs('#ct0carOutDate').attr('disabled','');
		}
		}
				
		
	}
	
	
	
	
	Ext.onReady(function(){
	var check=document.getElementById("checkIds");
	if(check&&check.value!=""){
	checkV=check.value
	var checks=checkV.toString().split(";");
	var cardNumberList = document.getElementsByName("cardNumber");
	for(var i=0;i<checks.length-1 ;i++){
	//alert(checks[i].toString());
	checkValue ='#'+checks[i].toString();
	myjs(checkValue).attr('checked',true);
	}
	}
	});
	
	
	
</script>


<form id="checkAndSearchFrom"
	action="tawPartnerCars.do?method=staffContent" method="post">

	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					品牌
				</td>
				<td>
					<input type="text" class="text" name="ct0brandT" id="ct0brandT"
						onblur="changeCheckBox()" />
				</td>
				<td class="label">
					出厂日期
				</td>
				<td>
					<input type="text" class="text" name="ct0carOutDateT"
						id="ct0carOutDateT" onblur="changeCheckBox()" />
				</td>
			</tr>
			<tr>
			<td class="label">
					车辆状态
				</td>
				<td>
				<eoms:comboBox name="ct0carStateT" id="ct0carStateT" initDicId="1121702" defaultValue="${tawPartnerCarForm.carState}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'" onchange="changeCheckBox()" />
				</td>
			
				<td class="label">
					地域
				</td>
				<td>
						<input class="text" name="de0area_nameT" id="de0area_nameT" type="text"  readonly="true"  alt="allowBlank:true" /> 
						<input type="button" name="areatree" id="areatree" value="选择地域" class="btn" /> 
						<input type="hidden" name="areaIdStringEqual" id="areaIdStringEqual" />
						<eoms:xbox id="tree1" dataUrl="${app}/partner/baseinfo/xtree.do?method=area" 
			 				 rootId="24" 
			  				 rootText='四川' 
			  				 valueField="areaIdStringEqual"
			  				 handler="areatree"
			  				 textField="de0area_nameT"
			 				 checktype="area" 
			 				 single="true"></eoms:xbox>
			 				 
			 		<!-- 查询的时候使用上面的地域树代码		 
					<input type="text" class="text" name="de0area_nameT"
						id="de0area_nameT" onblur="changeCheckBox()" /> 
					 -->
				</td>
		</table>
	</fieldset>

	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTable">
			<tr>

	            <td class="label">
					品牌
				</td>
				<td>
					<input id="ct0brand" type="checkbox" name="cardNumber"
						value="ct0brand" />
				</td>
				<td class="label">
					出厂日期
				</td>
				<td>
					<input id="ct0carOutDate" type="checkbox" name="cardNumber"
						value="ct0carOutDate" />
				</td>
				<td class="label">
					车辆状态
				</td>
				<td>
					<input id="ct0carState" type="checkbox" name="cardNumber"
						value="ct0carStateTypeLikedict" />
				</td>
				<td class="label">
					地域
				</td>
				<td>
					<input id="de0area_name" type="checkbox" name="cardNumber"
						value="de0area_name" />
				</td>
		   	    <input type="hidden" name="deleteIdss" id="deleteIdss" />
				<input type="hidden" name="checks" id="checks" />
				<input type="hidden" name="checkIds" id="checkIds"
					value="${checkList}" />
				
			</tr>
		
		</table>
	</fieldset>

	<input type="button" name="统计" value="统计" onclick="sendBox()" />
	<!-- This hidden area is for batch deleting. -->
	<form>

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
										<td rowspan="${td.rowspan} }">

											<c:if test="${!empty td.url}">
												<a href="javascript:void(0);"
													onclick="window.open('${app}${td.url}');">${td.name}</a>
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