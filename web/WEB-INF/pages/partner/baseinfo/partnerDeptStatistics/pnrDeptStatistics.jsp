<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<style type="text/css">
#qqonline {
position: absolute;
top: 320px;
}
.formTable{
   table-layout:fixed;
}
.formTable td{
   width:60px;
}
.formTableFor {
    border-collapse: collapse;
    font-size: 12px;
}
.formTableFor td {
    background-color: #FFFFFF;
    border: 1px solid #C9DEFA;
    padding: 6px;
}
.formTableFor td.label {
    background-color: #EDF5FD;
    vertical-align: top;
    width: 5%;
}
.formTableFor td.content {
    text-align: left;
}
 </style>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    //显示勾选框和统计图像
	Ext.onReady(function(){
		//先清空所有的勾选框
			var all=document.getElementsByName("statisticsItem");
			for (i = 0 ; i <all.length; i ++) {
				var checkValue="#"+all[i].id;
				myjs(checkValue).attr('checked',false);
			}
		//显示已经勾选的框
		var check="${checkedIdsStr}";
		if(check!=""){
			var checks=check.toString().split(";");
			for(var i=0;i<checks.length-1 ;i++){
				//alert(checks[i].toString());
				checkValue ='#'+checks[i].toString();
				myjs(checkValue).attr('checked',true);
			}
		}
		changeCheckBox();
	});
    function sendBox(deptMagId,deptlevel) {
    	if(deptMagId!=''){
    		$("deptMagId").value=deptMagId;//采用的是钻取功能,需要将deptMagId的值传到后台中;
    		document.getElementById("nextDeptLevel").value=deptlevel;
       		//${"nextDeptLevel"}.value=deptlevel;
    	}
    	$("exportFlag").value="1";
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
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var deptType = myjs('#deptType').val();
		var phone=myjs('#phone').val();
		var manager=myjs('#manager').val();
		var contactor=myjs('#contactor').val();
		var professional=myjs('#professional').val();
		if(professional){
			myjs('#isProfessional').attr('checked',true);
			myjs('#isProfessional').attr('disabled','disabled');
		}else{
			if((myjs('#isProfessional').attr('disabled'))){
			     myjs('#isProfessional').attr('checked',false);
			     myjs('#isProfessional').attr('disabled','');
			}
		}
		if(deptType||phone||manager||contactor){
			myjs('#isBaseInfo').attr('checked',true);
			myjs('#isBaseInfo').attr('disabled','disabled');
		}else{
			if((myjs('#isBaseInfo').attr('disabled'))){
			    myjs('#isBaseInfo').attr('checked',false);
			    myjs('#isBaseInfo').attr('disabled','');
			}
		}
	}
	
	function res(){
		var formElement=document.getElementById("checkAndSearchFrom")
	   	 var inputs = formElement.getElementsByTagName('input');
	   	 var selects =formElement.getElementsByTagName('select');
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
     	$("hasSend").value="nook";
     	changeCheckBox();
	}
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
<%@ include file="/common/body.jsp"%>
<form  id="checkAndSearchFrom" action="pnrDeptStatistics.do?method=pnrDetpStatistics&isPartner=${isPartner}" method="post">
	<fieldset>
				<legend>请输入统计条件</legend>
	<table class="formTableFor">
			<tr>
				<td class="label">
					组织名称
				</td>
				<td class="content" colspan="3">
					<input type="text" name="companyName" class="text max"  id="companyName"  value="${companyName}"/>
					<input type="hidden" name="companyId" id="companyId"  maxLength="32" 	class="text medium"   value="${companyId}"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf&isPartner=${isPartner}" rootId=""
					rootText='公司组织' valueField="companyId" handler="companyName" textField="companyName"
					checktype="dept"  data="${boxData}" single="true">
					</eoms:xbox>
				</td>
			</tr>
			<tr>
					<td class="label">
						区域
					</td>
					<td class="content">
						<input type="text" name="areaName" id="areaName" value="${areaName}"   class="text medium" readonly="readonly"/>
						<input type="hidden" name="areaid" id="areaid"  value="${areaId}" class="text medium"/>
						<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属区域' valueField="areaid"
						 handler="areaName" 	textField="areaName" checktype="" single="true">
						</eoms:xbox>
					</td>
					<td class="label">
						巡检专业
					</td>
					<td class="content">
							<eoms:comboBox  name="professional" id="professional"  defaultValue="${professional}" 
							initDicId="11225" styleClass="input select" onchange="changeCheckBox();"/>
					</td>
			</tr>
			<tr>
				<td class="label">
						企业性质
				</td>
				<td class="content">
					<eoms:comboBox  name="deptType" id="deptType"  defaultValue="${deptType}" 
							initDicId="1120101" styleClass="input select" onchange="changeCheckBox();"/>
				</td>
				<td class="label">
					法人代表
				</td>
				<td class="content">
					<input type="text" name="manager" id="manager" class="text" value="${manager}" onblur="changeCheckBox();"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					联系人
				</td>
				<td class="content">
					<input type="text" name="contactor" id="contactor" class="text" value="${contactor}" onblur="changeCheckBox();"/>
				</td>
				<td class="label">
					联系电话
				</td>
				<td class="content">
					<input type="text" name="phone" id="phone" class="text" value="${phone}" onblur="changeCheckBox();"/>
				</td>
			</tr>
		</table>
		</fieldset>
	<fieldset>
			<legend>请选择统计项目</legend>
	<table class="formTableFor">
		<tr>
			<td class="label">
				<input  value="isUserNum" type="checkbox" name="statisticsItem" id="isUserNum" checked="true" />&nbsp;&nbsp;
							<c:if test="${isPartner=='0'}">代维人员数量</c:if>
							<c:if test="${isPartner=='1'}">自维人员数量</c:if>	
			</td>
			<td class="label">
				<input  value="isProfessional" type="checkbox" name="statisticsItem" id="isProfessional" checked="true" />&nbsp;&nbsp;
							<c:if test="${isPartner=='0'}">代维的专业</c:if>
			   				 <c:if test="${isPartner=='1'}">自维的专业</c:if>
			</td>
			<td class="label">
				<input  value="isArea" type="checkbox" name="statisticsItem" id="isArea" checked="true" />&nbsp;&nbsp;
							<c:if test="${isPartner=='0'}">代维区域</c:if>
							<c:if test="${isPartner=='1'}">自维区域</c:if>
			</td>
			<td class="label">
				<input  value="isBaseInfo" type="checkbox" name="statisticsItem" id="isBaseInfo" checked="true" />&nbsp;&nbsp;
							<c:if test="${isPartner=='0'}">代维厂家基本信息</c:if>
						    <c:if test="${isPartner=='1'}">自维厂家基本信息</c:if>
			</td>
			<td class="label">
				<input  value="isQualify" type="checkbox" name="statisticsItem" id="isQualify" checked="true" />&nbsp;&nbsp;
							<c:if test="${isPartner=='0'}">代维厂家资质信息</c:if>
						    <c:if test="${isPartner=='1'}">自维厂家资质信息</c:if>
			</td>
			<td class="label">
				<input  value="isContact" type="checkbox" name="statisticsItem" id="isContact" checked="true" />&nbsp;&nbsp;
							<c:if test="${isPartner=='0'}">代维合同信息</c:if>
						    <c:if test="${isPartner=='1'}">自维合同信息</c:if>
			</td>
			<input type="hidden" name="statisticsItems" id="statisticsItems" />
			<input type="hidden" name="checkedIds" id="checkedIds" />
			<input type="hidden" name="checkedIdsStr" id="checkedIdsStr" value="${checkedIdsStr}" />
			<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}"/>
			<input type="hidden" name="exportFlag" id="exportFlag"/>
			<input type="hidden" name="deptMagId" id="deptMagId"/>
			<input type="hidden" name="nextDeptLevel" id="nextDeptLevel" value="${nextDeptLevel}"/>
		</tr>
	</table>
	</fieldset>
	<input type="button" name="统计" value="统计" onclick="sendBox('')"/>
	<input type="button" name="重置" value="重置" onclick="res();"/>
		<logic-el:present name="tableList">
	<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</logic-el:present>
	</form>
	<!-- This hidden area is for batch deleting. -->
	<div>
		<table class="formTable" style="width:100%" align="center" id="all">
			<c:if test="${statistics=='2'}"><!--当没有统计时不显示表头-->
				<c:if test="${checkedIdsStr!=''}"><!--当没有选项时第一行的内容不显示-->
					<tr  id="trTop">
						<td class="label" style="width:80px;"></td>
						<td class="label" style="width:300px;"></td>
						<c:if test="${isUserNum==true}">
							<td class="label" style="width:160px;" colspan="2"  align="center">
							<c:if test="${isPartner=='0'}">代维人员数量</c:if>
						    <c:if test="${isPartner=='1'}">自维人员数量</c:if>							
							</td>
						</c:if>
						<c:if test="${isProfessional==true}">
							<td  class="label" style="width:560px;" colspan="9" align="center">
							<c:if test="${isPartner=='0'}">代维的专业</c:if>
						    <c:if test="${isPartner=='1'}">自维的专业</c:if>
							</td>
						</c:if>
						<c:if test="${isArea==true}">
							<td  class="label" style="width:200px;"></td>
						</c:if>
						<c:if test="${isBaseInfo==true}">
							<td class="label"  style="width:920px;" colspan="7"  align="center">							
							<c:if test="${isPartner=='0'}">代维厂家基本信息</c:if>
						    <c:if test="${isPartner=='1'}">自维厂家基本信息</c:if>
							</td>
						</c:if>
						<c:if test="${isQualify==true}">
							<td  class="label" style="width:280px;" colspan="2" align="center">
							<c:if test="${isPartner=='0'}">代维厂家资质信息</c:if>
						    <c:if test="${isPartner=='1'}">自维厂家资质信息</c:if>
							</td>
						</c:if>
						<c:if test="${isContact==true}">
							<td class="label"  style="width:300px;" colspan="3"  align="center">							
							<c:if test="${isPartner=='0'}">代维合同信息</c:if>
						    <c:if test="${isPartner=='1'}">自维合同信息</c:if>
							</td>
						</c:if>
						<c:if test="${deptLevel!=4}">
							<td class="label" style="width:40px;"></td>
						</c:if>
					</tr>
				</c:if>
			<tr>
				<td class="label" >区域</td>
				<td class="label" style="width:300px;">			
				<c:if test="${isPartner=='0'}">代维厂家名称</c:if>
				<c:if test="${isPartner=='1'}">自维厂家名称</c:if>
				</td>
				<c:if test="${isUserNum==true}">
					<td class="label">					
						<c:if test="${isPartner=='0'}">代维人员数量</c:if>
						<c:if test="${isPartner=='1'}">自维人员数量</c:if>
					</td>
					<td class="label" >通过技能认证的人数</td>
				</c:if>
				<c:if test="${isProfessional==true}">
					<td class="label">基站机房设备及配套</td>
					<td class="label">室分</td>
					<td class="label">铁塔及天馈</td>
					<td class="label">接入网机房设备及配套</td>
					<td class="label">WLAN</td>
					<td class="label">巡检抽查</td>
					<td class="label">电费管理</td>
					<td class="label">重点客户机房</td>
					<td class="label">室外箱体</td>
				</c:if>
				<c:if test="${isArea==true}">
					<td class="label" >
						<c:if test="${isPartner=='0'}">代维区域</c:if>
						<c:if test="${isPartner=='1'}">自维区域</c:if>
					</td>
				</c:if>
				<c:if test="${isBaseInfo==true}">
					<td class="label">工商注册时间</td>
					<td class="label" style="width:400px;">工商注册地址</td>
					<td class="label">注册资金</td>
					<td class="label">企业性质</td>
					<td class="label">法人代表</td>
					<td class="label">联系人</td>
					<td class="label" style="width:120px;">联系电话</td>
				</c:if>
				<c:if test="${isQualify==true}">
					<td class="label" style="width:200px;">资质名称和级别</td>
					<td class="label" style="width:80px;">资质截止日期</td>
				</c:if>
				<c:if test="${isContact==true}">
					<td class="label" style="width:100px;">合同签订日期</td>
					<td class="label" style="width:100px;">合同起始日期</td>
					<td class="label" style="width:100px;">合同截止日期</td>
				</c:if>
				<td class="label" style="width:40px;">查看下级信息</td>
			</c:if>
						<c:forEach items="${tableList}" var="tdList">
							<tr>
								<c:forEach items="${tdList}" var="td" varStatus="i">
									<c:if test="${i.count==1}">
										<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
										</c:if>
									</c:if>
									<c:if test="${i.count==5}">
										<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
													<a  style="text-decoration:none" target="_black" href="${app}/partner/baseinfo/partnerDepts.do?method=detail&proId=${tdList[2].name}&hasRightForAdd='0'&searchInto='1'">
														${td.name}
													</a>
												</td>
										</c:if>
									</c:if>
									<!--控制是否显示人员数量-->
									<c:if test="${isUserNum==true}">
										<c:if test="${i.count==6||i.count==7}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示公司代维的专业-->
									<c:if test="${isProfessional==true}">
										<c:if test="${i.count==8||i.count==9||i.count==10||i.count==11||i.count==12||i.count==13||i.count==14||i.count==15||i.count==16}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
													<c:if test="${!empty td.name}">
														<span>√</span>
													</c:if>													
												</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示代维区域-->
									<c:if test="${isArea==true}">
										<c:if test="${i.count==15}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
											    	${td.name}
												</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示组织基本信息-->
									<c:if test="${isBaseInfo==true}">
										<c:if test="${i.count==16||i.count==17||i.count==18||i.count==19||i.count==20||i.count==21||i.count==22}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
											    	${td.name}
												</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示代维资质信息-->
									<c:if test="${isQualify==true}">
										<c:if test="${i.count==23||i.count==24}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
											    	${td.name}
												</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示代维合同信息-->
									<c:if test="${isContact==true}">
										<c:if test="${i.count==25||i.count==26||i.count==27}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
											    	${td.name}
												</td>
											</c:if>
										</c:if>
									</c:if>
									<!--如果是代维小组级别将不再有向下钻取功能-->
									<c:if test="${i.count==28}">
										<td>
											<c:if test="${tdList[27].name<5}">
												<a  style="text-decoration:none" onclick="sendBox('${tdList[3].name}','${tdList[27].name}');">
													<img src="${app}/images/icons/search.gif">
												</a>
											</c:if>
											<c:if test="${tdList[27].name>4}">
												已经是最底层次
											</c:if>
										</td>
									</c:if>
									</c:forEach>
							</tr>
						</c:forEach>
	</table>
	</div>
<%@ include file="/common/footer_eoms.jsp"%>