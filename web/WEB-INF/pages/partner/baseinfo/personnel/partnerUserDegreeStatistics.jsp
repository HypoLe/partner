<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript" src="${app}/FusionCharts/FusionCharts.js"></script>
	
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
			myjs('#professinalTypeLikedict').attr('checked',true);
			myjs('#professinalTypeLikedict').attr('disabled','disabled');
		}else{
			if((myjs('#professinalTypeLikedict').attr('disabled'))){
			    myjs('#professinalTypeLikedict').attr('checked',false);
			    myjs('#professinalTypeLikedict').attr('disabled','');
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
		//显示报表图形
		var list='${tableList}';
		var checkedList='${checkedList}';
		var reg="_degree";
		if (list!=''){
			if(checkedList.match(reg)!=null){//当统计选项没有具体的某种学历时该图表不显示
				var width = '${width}';
		    	 if(width!=null||width!=""){
				    var myChart = new FusionCharts("${app}/FusionCharts/MSColumn3D.swf", "fusionchart", "1000", "480","0","0");
				    myChart.setDataXML("${xml}");
					myChart.render("fusionchart");
		    	 }
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
		 	alert("请先完成统计!")
			return;
		}
	};
</script>
<form id="checkAndSearchFrom"	action="statistics.do?method=partnerUserDegreeStatistics" method="post">
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
					巡检组织&nbsp;
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
					巡检专业
				</td>
				<td class="content" colspan="3">
						<eoms:comboBox  name="maintenanceMajor" id="maintenanceMajor"  defaultValue="${major}" initDicId="11225" styleClass="input select"   onchange="changeCheckBox()" />
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
					<input value="u.area_id" type="checkbox" name="statisticsItem" id="area_idTypeLikeArea" checked="true" />
					所属区域
				</td>
				<td class="label" colspan="2">
					<input value="u.dept_id" type="checkbox" name="statisticsItem"  checked="true" id="dept_idTypeLikeDept" />
					巡检组织
				</td>
				<td class="label" colspan="2">
					<input value="dw.professional" type="checkbox" name="statisticsItem"  checked="true"  id="professinalTypeLikedict" />
					巡检专业
				</td>
				<td class="label" colspan="2">
					<input value="sum(p.junior_degree)" type="checkbox" name="statisticsItem"  checked="true" id="junior_degree" />
					初中及以下
				</td>
				<td class="label" colspan="2">
					<input value="sum(p.technical_degree)" type="checkbox" name="statisticsItem"  checked="true" id="technical_degree" />
					中专
				</td>
			</tr>
			<tr>
				<td class="label" colspan="2">
					<input value=" sum(p.senior_degree)" type="checkbox" name="statisticsItem"  checked="true" id="senior_degree" />
					高中
				</td>
				<td class="label" colspan="2">
					<input value="sum(p.college_degree)" type="checkbox" name="statisticsItem"   checked="true" id="college_degree" />
					大专
				</td>
				<td class="label" colspan="2">
					<input value="sum(p.undergraduate_degree)" type="checkbox" name="statisticsItem"  checked="true" id="undergraduate_degree" />
					本科
				</td>
				<td class="label" colspan="4">
					<input value="sum(p.master_degree)" type="checkbox" name="statisticsItem"  checked="true" id="master_degree" />
					硕士及以上
				</td>
				<input type="hidden" name="statisticsItems" id="statisticsItems" />
				<input type="hidden" name="checkedIds" id="checkedIds" />
				<input type="hidden" name="checkedList" id="checkedList" value="${checkedList}" />
				<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}">
				<input type="hidden" name="exportFlag" id="exportFlag" >
			</tr>
		</table>
	</fieldset>
</form>
	<div>
		<span style="color:red">统计说明:人员信息-技能信息-教育经历信息3个信息关联统计，如果人员技能信息中无人员的专业信息不论统计项目"巡检专业"
		是否选中均不会统计出该人员的教育经历信息</span><br>
	</div>
	<input type="button" name="统计" value="统计" onclick="sendBox();" />
	<input type="button" name="重置" value="重置" onclick="res();" />
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
	<c:if test="${!empty tableList}">
		<div id="fusionchart"></div>
	</c:if>
		<%@ include file="/common/footer_eoms.jsp"%>