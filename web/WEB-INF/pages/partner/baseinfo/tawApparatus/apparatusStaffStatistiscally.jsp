<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    
    
    function sendBox() {
    	
    	//在选择分公司的时候,如果默认没选,则传递空值给后台 add by WangGuangoing 2012-3-6 11:00:32
		if(document.getElementById("de0nameT")[document.getElementById("de0nameT").selectedIndex].text == '请选择'){
			document.getElementById("de0nameT").value = '';
		}
		
	    changeCheckBox();//提交之前执行,地域选择后无法自动执行 add by WangGuangping 2012年3月5日17:45:48
	    
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
			return false;
		}
		
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var ct0apparatusId = myjs('#ct0apparatusIdT').val();
		var name=myjs('#de0nameT').val();
		var apparatusname=myjs('#ct0apparatusnameT').val();
		var state = myjs('#ct0stateT').val();
		var storage=myjs('#ct0storageT').val();
		var whether_allocate=myjs('#ct0whether_allocateT').val();
		
		if(ct0apparatusId){
		myjs('#ct0apparatusId').attr('checked',true);
		myjs('#ct0apparatusId').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0apparatusId').attr('disabled'))){
		     myjs('#ct0apparatusId').attr('checked',false);
		     myjs('#ct0apparatusId').attr('disabled','');
		}
		}
		if(name){
			myjs('#de0name').attr('checked',true);
			myjs('#de0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0name').attr('disabled'))){
		    myjs('#de0name').attr('checked',false);
		    myjs('#de0name').attr('disabled','');
		}
		}
		if(apparatusname){
			myjs('#ct0apparatusname').attr('checked',true);
			myjs('#ct0apparatusname').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0apparatusname').attr('disabled'))){
		     myjs('#ct0apparatusname').attr('checked',false);
		     myjs('#ct0apparatusname').attr('disabled','');
		}
		}
		if(state){
			myjs('#ct0state').attr('checked',true);
			myjs('#ct0state').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0state').attr('disabled'))){
		     myjs('#ct0state').attr('checked',false);
		     myjs('#ct0state').attr('disabled','');
		}
		}
				if(storage){
		myjs('#ct0storage').attr('checked',true);
		myjs('#ct0storage').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0storage').attr('disabled'))){
		     myjs('#ct0storage').attr('checked',false);
		     myjs('#ct0storage').attr('disabled','');
		}
		}
				if(whether_allocate){
		myjs('#ct0whether_allocate').attr('checked',true);
		myjs('#ct0whether_allocate').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0whether_allocate').attr('disabled'))){
		     myjs('#ct0whether_allocate').attr('checked',false);
		     myjs('#ct0whether_allocate').attr('disabled','');
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
	action="tawApparatuss.do?method=staffContent" method="post">

	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
			<tr>
			<!-- 因新增的时候添加地域字段的代码被注销,因此无法通过地域id查询信息 modify by WangGuangping 2012-3-6 10:25:13
				<td class="label">
					选择地域
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
			 				 
			 		<!-- modify by WangGuangping 2012-3-5 17:31:37
					<input type="text" class="text" name="de0area_nameT"
						id="de0area_nameT" onblur="changeCheckBox()" />
					-- >
				</td>
			 -->
			 	
				<td class="label">
					仪器仪表编号
				</td>
				<td>
					<input type="text" class="text" name="ct0apparatusIdT"
						id="ct0apparatusIdT" onblur="changeCheckBox()" />
				</td>
				
				<td class="label">
					仪器仪表名称
				</td>
				<td>
					<input type="text" class="text" name="ct0apparatusnameT"
						id="ct0apparatusnameT" onblur="changeCheckBox()" />
				</td>
			</tr>
			
			<tr>
				<td class="label">
					仪器仪表运行状态
				</td>
				<td>
						<select name="ct0stateT" id="ct0stateT"
						onchange="changeCheckBox()">
						<option value="" >请选择</option>
						<option value="1120501">
							正常
						</option>
						<option value="1120502">
							检修
						</option>
							<option value="1120503">
							报废
						</option>

					</select>
				</td>
				
				<td class="label">
					选择公司
				</td>
				<td>
					<eoms:pnrComp name="de0nameT" id="de0nameT" />
					
					<!-- modify by WangGuangping 2012年3月5日17:42:15
					<input type="text" class="text" name="de0nameT" id="de0nameT"
						onblur="changeCheckBox()" />
					 -->
				</td>
			</tr>
			
			<tr>	
				<td class="label">
					存放地点
				</td>
				<td>
					<input type="text" class="text" name="ct0storageT" id="ct0storageT"
						onblur="changeCheckBox()" />
				</td>
				<td class="label">
					是否可调配
				</td>
				<td>
					<select name="ct0whether_allocateT" id="ct0whether_allocateT"
						onchange="changeCheckBox()">
						<option value="">请选择</option>
						<option value="1030101">
							是
						</option>
						<option value="1030102">
							否
						</option>

					</select>
					<!--			
                        <input type="text" class="text"
						name="ct0whether_allocateT" id="ct0whether_allocateT" onblur="changeCheckBox()"/></td>
-->
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
					仪器仪表编号
				</td>
				<td>
					<input id="ct0apparatusId" type="checkbox" name="cardNumber"
						value="ct0apparatusId" />
				</td>
				<td class="label">
					仪器仪表名称
				</td>
				<td>
					<input id="ct0apparatusname" type="checkbox" name="cardNumber"
						value="ct0apparatusname" />
				</td>
				<td class="label">
					公司名
				</td>
				<td>
					<input id="de0name" type="checkbox" name="cardNumber"
						value="de0name" />
				</td>
			</tr>
			<tr>
				<td class="label">
					仪器仪表运行状态
				</td>
				<td>
					<input id="ct0state" type="checkbox" name="cardNumber"
						value="ct0stateTypeLikedict" />
				</td>
				<td class="label">
					存放地点
				</td>
				<td>
					<input id="ct0storage" type="checkbox" name="cardNumber"
						value="ct0storage" />
				</td>
				<td class="label">
					是否可调配
				</td>
				<td>
					<input id="ct0whether_allocate" type="checkbox" name="cardNumber"
						value="ct0whether_allocateTypeLikedict" />
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