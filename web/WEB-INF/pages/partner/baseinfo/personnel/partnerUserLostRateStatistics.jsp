<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
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
	          		if(inputs[i].value!='latitude'&&inputs[i].value!='longtitude'){
	             		 inputs[i].checked =false;
	              	}
	         }
     	}
     	 for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
     	$("hasSend").value="nook";
     	changeCheckBox();
	}
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
		var year=document.getElementById("year").value;
		var month=document.getElementById("month").value;
		var month1=parseInt(month,10)+1;
		var min1="";
		if(month<10){
			min1="0"+month;
		}else{
			min1=month;
		}
		var temp=new Date(year+"/"+month1+"/0");
		var max=temp.getDate();
		document.getElementById("year").value=year;
		document.getElementById("month").value=month;
		document.getElementById("minDateVal").value=min1+"01";//获取当前月的最小的一天如20120901;
		document.getElementById("maxDateVal").value=min1+max;//获取当前月的最大的一天如20120131;带有闰年、大月、小月的判断
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var area = myjs('#area2').val();
		var company=myjs('#maintainCompany').val();
		var major=myjs('#maintenanceMajor').val();
		if(area){
			myjs('#area_idTypeLikeArea').attr('checked',true);
			myjs('#area_idTypeLikeArea').attr('disabled','disabled');
		}else{
			if((myjs('#area_idTypeLikeArea').attr('disabled'))){
			     myjs('#area_idTypeLikeArea').attr('checked',false);
			     myjs('#area_idTypeLikeArea').attr('disabled','');
			}
		}
		if(company){
			myjs('#dept_idTypeLikeDept').attr('checked',true);
			myjs('#dept_idTypeLikeDept').attr('disabled','disabled');
		}else{
			if((myjs('#dept_idTypeLikeDept').attr('disabled'))){
			    myjs('#dept_idTypeLikeDept').attr('checked',false);
			    myjs('#dept_idTypeLikeDept').attr('disabled','');
			}
		}
		if(major){
			myjs('#professionalTypeLikedict').attr('checked',true);
			myjs('#professionalTypeLikedict').attr('disabled','disabled');
		}else{
			if((myjs('#professionalTypeLikedict').attr('disabled'))){
			    myjs('#professionalTypeLikedict').attr('checked',false);
			    myjs('#professionalTypeLikedict').attr('disabled','');
			}
		}
	}
	Ext.onReady(function(){
		var check=document.getElementById("checkedList");
		if(check&&check.value!=""){
			//先清空所有的勾选框
			var all=document.getElementsByName("statisticsItem");
			for (i = 0 ; i <all.length; i ++) {
				var checkValue="#"+all[i].id;
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
		 	alert("请先完成统计!")
			return;
		}
	};
	
</script>
<form id="checkAndSearchFrom"	action="statistics.do?method=partnerUserLostRateStatistics" method="post" >
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					所属区域
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${areaStringLike}',name:'<eoms:id2nameDB id="${areaStringLike}" beanId="tawSystemAreaDao"/>'}]</c:set>
					<input type="text" name="area2" id="area2"    class="text medium" alt="allowBlank:true" 	onblur="changeCheckBox()"/>
					<input type="hidden" name="area_id" id="area_id"   class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属区域' valueField="area_id" handler="area2" 
					textField="area2" data="${boxData}" checktype="" single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					代维公司&nbsp;
				</td>
				<td class="content">
					<c:set var="boxData">[{id:'${maintainCompanyStringLike}',name:'<eoms:id2nameDB id="${maintainCompanyStringLike}" beanId="tawSystemDeptDao"/>'}]</c:set>
					<input type="text" name="maintainCompany" class="text"  id="maintainCompany" alt="allowBlank:true" onblur="changeCheckBox()"/>
					<input type="hidden" name="maintainCompany_id" id="maintainCompany_id"  maxLength="32" 	class="text medium" />
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=getPnrDeptWithRight" rootId=""
					rootText='代维公司组织' valueField="maintainCompany_id" handler="maintainCompany" textField="maintainCompany"
					checktype="dept"  data="${boxData}" single="true">
					</eoms:xbox>
				</td>
			</tr>
			<tr>
				<td class="label">
					所属专业
				</td>
				<td class="content">
					<eoms:comboBox  name="maintenanceMajor" id="maintenanceMajor"  defaultValue="${major}" initDicId="11225" styleClass="input select"   onchange="changeCheckBox()" />
				</td>
				<td class="label">
					时间
				</td>
				<td class="content">
					<select id="year" name="year">
						
						<c:forEach begin="2012" end="2050" var="year">
							<c:choose>
								<c:when test="${year1==year}">
										<option  value="${year}" selected="selected">${year}年</option>
								</c:when>
								<c:otherwise>
									<option  value="${year}">${year}年</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<select id="month" name="month" >
						<c:forEach begin="1" end="12" var="month">
							<c:choose>
								<c:when test="${month1==month}">
										<option  value="${month}" selected="selected">${month}月</option>
								</c:when>
								<c:otherwise>
									<option  value="${month}">${month}月</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
					<input type="hidden" name="minDateVal" id="minDateVal"/>
					<input type="hidden" name="year" id="year"/>
					<input type="hidden" name="month" id="month"/>
					<input type="hidden" name="maxDateVal" id="maxDateVal"/>
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
				<td class="label" colspan="2">
					<input value="area_id" type="checkbox" name="statisticsItem" 	id="area_idTypeLikeArea" checked="checked"/>
					区域
				</td>
				<td class="label" colspan="2">
					<input value="dept_id" type="checkbox" name="statisticsItem"	id="dept_idTypeLikeDept" checked="checked"/>
					代维公司
				</td>
				<td class="label" colspan="2">
					<input value="professional" type="checkbox" name="statisticsItem" id="professionalTypeLikedict" />
					维护专业
				</td>
				<td class="label" colspan="2">
					<input value="latitude" type="checkbox" name="statisticsItem" id="latitude" checked="checked" disabled="disabled" />
					离职人数
				</td>
				<td class="label" colspan="2">
					<input value="longtitude" type="checkbox" name="statisticsItem" id="longtitude" checked="checked" disabled="disabled"/>
					在职总数
				</td>
			</tr>
			<tr>
				<input type="hidden" name="statisticsItems" id="statisticsItems" />
				<input type="hidden" name="checkedIds" id="checkedIds" />
				<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
				<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
				<input type="hidden" name="exportFlag" id="exportFlag" >
			</tr>
		</table>
	</fieldset>
</form>
	<input type="button" name="统计" value="统计" onclick="sendBox()" />
	<input type="button" name="重置" value="重置" onclick="res()" />
	<c:if test="${!empty tableList}">
		<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</c:if>
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
						<input type="hidden" name="year" value="${year1}"/>
						<input type="hidden" name="month" value="${month1}"/>
						<c:forEach items="${tableList}" var="tdList">
							<tr>
								<c:forEach items="${tdList}" var="td">
									<c:if test="${td.show}">
										<td rowspan="${td.rowspan}">
											<c:if test="${!empty td.url}">
												<a href="javascript:void(0);"	onclick="window.open('${app}${td.url}');">${td.name}</a>
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
	</form>
<%@ include file="/common/footer_eoms.jsp"%>