<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
 <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
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
   width:70px;
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
    width: 11%;
}
.formTableFor td.content {
    text-align: left;
}
 </style>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    //显示勾选框和统计图像
	Ext.onReady(function(){
		//按照统计前的方式展示条件查询table
		var btnVal="${btnValue}";
		if(btnVal=="收缩"){
			eoms.form.enableArea('see1');
			eoms.form.enableArea('see2');
			eoms.form.enableArea('see3');
			eoms.form.enableArea('see4');
			eoms.form.enableArea('see5');
			eoms.form.enableArea('see6');
			eoms.form.enableArea('see7');
			document.getElementById("moreBtn").value="收缩";
			document.getElementById("btnValue").value="收缩";
		}else{
			eoms.form.disableArea("see1",true);
			eoms.form.disableArea("see2",true);
			eoms.form.disableArea("see3",true);
			eoms.form.disableArea("see4",true);
			eoms.form.disableArea("see5",true);
			eoms.form.disableArea("see6",true);
			eoms.form.disableArea("see7",true);
			document.getElementById("moreBtn").value="更多...";
			document.getElementById("btnValue").value="更多...";
		}
		var check=document.getElementById("checkedIdsStr");
		var all=document.getElementsByName("statisticsItem");
		var checkedIdsDisableVal="${checkedIdsDisableVal}";
		var disablesVal=checkedIdsDisableVal.split(";");
		checkV=check.value;
		var export2="${exportFlag}";//刚加载的时候该值为空，如果是统计后加载为1，导出后为2;由此可以判断加载时的状态,避免checkbox的默认状态被冲刷
		if(export2!=""){
			for (i = 0 ; i <all.length; i ++) {
				var checkValue="#"+all[i].id;
				if(checkV.contains(all[i].id)){
					myjs(checkValue).attr('checked',true);
				}else{
					myjs(checkValue).attr('checked',false);
				}
				if(disablesVal[i]=="true"){
					myjs(checkValue).attr('disabled','disabled');
				}
			}
		}
	});
    function sendBox() {
    	$("exportFlag").value="1";
		var statisticsItemList = document.getElementsByName("statisticsItem");//获取选中的统计项目的值与实体属性名相对应
		var idResult = "";
		var checkedIds="";
		var checkedIdsDisableVal="";//被选中的checkbox的disable属性值;
		for (i = 0 ; i < statisticsItemList.length; i ++) {
			checkedIdsDisableVal+=statisticsItemList[i].disabled+";";
			if (statisticsItemList[i].checked) {
				var itemV=statisticsItemList[i].value;
				idResult+=itemV.toString()+";" ;
				var checkedId=statisticsItemList[i].id;
				checkedIds+=checkedId.toString()+";";
			}
		}
		$("statisticsItems").value=idResult.toString();//拼接被勾选的统计项目的value其值包含"TypeLikedict"标识；
		$("checkedIds").value=checkedIds.toString();//获取选中的统计项目的值与实体属性数据库名相对应
		$("checkedIdsDisableVal").value=checkedIdsDisableVal.toString();//checkbox的disable属性值;
		document.getElementById("checkAndSearchFrom").submit();
	}
	var baseinfoCount=0;//用于baseinfo触发的计数器，当这个值为0时表示没有任何值，此时的checkbox不能被disable;
	function changeCheckBox(obj){
		var objid=obj.id;
		var index=0;
		if(objid!="diplomaStringEqual"&&objid!="learnspecialtyStringEqual"&&objid!="graduateschoolStringEqual"&&objid!="diplomaStringLessThan"){
			index=objid.indexOf("String");
		}
		if(index>0){//如果大于0说明是在选择人员基本信息;
			var objval=obj.value;
			if(objval){//不为空时对baseinfoCount加1；
				baseinfoCount=baseinfoCount+1;
			}else{
				if(baseinfoCount>0){
					baseinfoCount=baseinfoCount-1;
				}else{
					baseinfoCount=0;
				}
			}
			if(baseinfoCount>0){
				myjs('#isBaseinfo').attr('checked',true);
				myjs('#isBaseinfo').attr('disabled','disabled');
			}else{
				if(myjs('#isBaseinfo').attr('disabled')){
					myjs('#isBaseinfo').attr('checked',false);
					myjs('#isBaseinfo').attr('disabled','');
				}
			}
		}
		var certType=myjs("#certType").val();
		var workcompany=myjs("#workCompany").val();
		var skill=myjs("#skill").val();
		var skilllevel=myjs("#skilllevel").val();
		var reward=myjs("#rewardName").val();
		var pxContent=myjs("#pxContent").val();
		var study1=myjs("#diplomaStringEqual").val();
		var study2=myjs("#learnspecialtyStringEqual").val();
		var study3=myjs("#graduateschoolStringEqual").val();
		var study4=myjs("#diplomaStringLessThan").val();
		if(skill||skilllevel){//技能的选择框被勾选
			myjs('#isDwinfo').attr('checked',true);
			myjs('#isDwinfo').attr('disabled','disabled');
		}else{
			if((myjs('#isDwinfo').attr('disabled'))){
			     myjs('#isDwinfo').attr('checked',false);
			     myjs('#isDwinfo').attr('disabled','');
			}
		}
		if(reward){//奖励的选择框被勾选
			myjs('#isRewardinfo').attr('checked',true);
			myjs('#isRewardinfo').attr('disabled','disabled');
		}else{
			if((myjs('#isRewardinfo').attr('disabled'))){
			     myjs('#isRewardinfo').attr('checked',false);
			     myjs('#isRewardinfo').attr('disabled','');
			}
		}
		if(certType){//工作经历的选择框被勾选
			myjs('#isCertinfo').attr('checked',true);
			myjs('#isCertinfo').attr('disabled','disabled');
		}else{
			if((myjs('#isCertinfo').attr('disabled'))){
			     myjs('#isCertinfo').attr('checked',false);
			     myjs('#isCertinfo').attr('disabled','');
			}
		}
		if(workcompany){//证书的选择框被勾选
			myjs('#isWorkinfo').attr('checked',true);
			myjs('#isWorkinfo').attr('disabled','disabled');
		}else{
			if((myjs('#isWorkinfo').attr('disabled'))){
			     myjs('#isWorkinfo').attr('checked',false);
			     myjs('#isWorkinfo').attr('disabled','');
			}
		}
		if(pxContent){//培训内容的选择框被勾选
			myjs('#isPxinfo').attr('checked',true);
			myjs('#isPxinfo').attr('disabled','disabled');
		}else{
			if((myjs('#isPxinfo').attr('disabled'))){
			     myjs('#isPxinfo').attr('checked',false);
			     myjs('#isPxinfo').attr('disabled','');
			}
		}
		if(study1||study2||study3||study4){//教育经历的选择框被勾选
			myjs('#isStudyinfo').attr('checked',true);
			myjs('#isStudyinfo').attr('disabled','disabled');
		}else{
			if((myjs('#isStudyinfo').attr('disabled'))){
			     myjs('#isStudyinfo').attr('checked',false);
			     myjs('#isStudyinfo').attr('disabled','');
			}
		}
	}
	//获取更多搜索条件
	function moreSearch(){
		var btnVal=document.getElementById("moreBtn").value;
		if(btnVal=="更多..."){
			eoms.form.enableArea('see1');
			eoms.form.enableArea('see2');
			eoms.form.enableArea('see3');
			eoms.form.enableArea('see4');
			eoms.form.enableArea('see5');
			eoms.form.enableArea('see6');
			eoms.form.enableArea('see7');
			document.getElementById("moreBtn").value="收缩";
			document.getElementById("btnValue").value="收缩";
		}else{
			eoms.form.disableArea("see1",true);
			eoms.form.disableArea("see2",true);
			eoms.form.disableArea("see3",true);
			eoms.form.disableArea("see4",true);
			eoms.form.disableArea("see5",true);
			eoms.form.disableArea("see6",true);
			eoms.form.disableArea("see7",true);
			document.getElementById("moreBtn").value="更多...";
			document.getElementById("btnValue").value="更多...";
		}
	}
	//重置
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
	              inputs[i].disabled ="";
	         }
     	}
     	for(var i=0;i<selects.length;i++){
	         if(selects[i].type == 'select-one'){
	              selects[i].options[0].selected = true;
	         }
     	}
     	$("hasSend").value="nook";
     	document.getElementById("checkedIds").value="";
		document.getElementById("checkedIdsDisableVal").value="";
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
<form  id="checkAndSearchFrom" action="statistics.do?method=pnrUserStatistics&isPartner=${isPartner}" method="post">
	<fieldset>
				<legend>请输入统计条件</legend>
	<table class="formTableFor" style="width:100%">
			<tr>
				<td class="label">
					人员姓名
				</td>
				<td class="content">
					<input type="text" name="nameStringLike" class="text"  id="nameStringLike"  value="${nameStringLike}"/>
				</td>
				<td class="label">
					身份证号
				</td>
				<td class="content">
					<input type="text" name="person_card_noStringEqual" class="text"  id="person_card_noStringEqual"  value="${person_card_noStringEqual}"/>
				</td>
				<td class="label">
						区域
				</td>
				<td class="content">
					<input type="text" name="areaName" id="areaName" value="${areaName}"   class="text medium" readonly="readonly"/>
					<input type="hidden" name="areaId" id="areaId"  value="${areaId}" class="text medium"/>
					<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="-1" rootText='所属区域' valueField="areaId"
					 handler="areaName" 	textField="areaName" checktype="" single="true">
					</eoms:xbox>
				</td>
			</tr>
			<tr>
				<td class="label">
					 <c:if test="${isPartner == '0'}" >代维公司</c:if>
					 <c:if test="${isPartner == '1'}" >自维公司</c:if>
					
				</td>
				<td class="content">
					<input type="text" name="companyName" class="text"  id="companyName"  value="${companyName}"/>
					<input type="hidden" name="companyId" id="companyId"  maxLength="32" 	class="text medium"   value="${companyId}"/>
					<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf&isPartner=${isPartner}" rootId=""
					rootText='公司组织' valueField="companyId" handler="companyName" textField="companyName"
					checktype="dept"  single="true">
					</eoms:xbox>
				</td>
				<td class="label">
					人员ID
				</td>
				<td class="content">
					<input type="text" name="user_idStringLike" class="text"  id="user_idStringLike" 
					 value="${user_idStringLike}"/>
				</td>
				<td class="label">
					在职状态
				</td>
				<td class="content">
					<eoms:comboBox  name="post_stateStringEqual" id="post_stateStringEqual"  defaultValue="${post_stateStringEqual}" 
							initDicId="12409" styleClass="input select" onchange="changeCheckBox(this);"/>
				</td>
			</tr>
			<tr>
				<td class="label">
						手机号码
				</td>
				<td class="content">
					<input type="text" name="mobile_phoneStringLike" class="text"  id="mobile_phoneStringLike" 
					onblur="changeCheckBox(this);"  value="${mobile_phoneStringLike}"/>
				</td>
				<td class="label">
					Email
				</td>
				<td class="content">
					<input type="text" name="emailStringLike" class="text"  id="emailStringLike"  
					onblur="changeCheckBox(this);" value="${emailStringLike}"/>
				</td>
				<td class="label">
					员工编号
				</td>
				<td class="content">
					<input type="text" name="usernoStringLike" class="text"  id="usernoStringLike" 
					onblur="changeCheckBox(this);"  value="${usernoStringLike}"/>
				</td>
			</tr>
			<tr  style="display:none" id="see1">
					<td class="label">
						集团短号
					</td>
					<td class="content">
						<input type="text" name="groupphoneStringLike" class="text"  id="groupphoneStringLike" 
						onblur="changeCheckBox(this);"  value="${groupphoneStringLike}"/>
					</td>
					<td class="label">
						毕业院校
					</td>
					<td class="content">
						<input type="text" name="graduateschoolStringLike" class="text"  id="graduateschoolStringLike" 
						onblur="changeCheckBox(this);"  value="${graduateschoolStringLike}"/>
					</td>
					<td class="label">
						所学专业
					</td>
					<td class="content">
						<input type="text" name="learnspecialtyStringLike" class="text"  id="learnspecialtyStringLike"
						value="${learnspecialtyStringLike}" onchange="changeCheckBox(this);"/>
					</td>
				</tr>
				<tr style="display:none" id="see2">
					<td class="label">
						学历(等于)
					</td>
					<td class="content">
						<eoms:comboBox  name="diplomaStringEqual" id="diplomaStringEqual"  defaultValue="${diplomaStringEqual}" 
							initDicId="12405" styleClass="input select" onchange="changeCheckBox(this);"/>
					</td>
					<td class="label">
						学历(以上)
					</td>
					<td class="content" colspan="5">
						<eoms:comboBox  name="diplomaStringLessThan" id="diplomaStringLessThan"  defaultValue="${diplomaStringLessThan}" 
							initDicId="12405" styleClass="input select" onchange="changeCheckBox(this);"/>
					</td>
				</tr>
				<tr style="display:none" id="see3">
					<td class="label">
						性别
					</td>
					<td class="content">
						<eoms:comboBox  name="sexStringEqual" id="sexStringEqual"  defaultValue="${sexStringEqual}" 
							initDicId="1120202" styleClass="input select" onchange="changeCheckBox(this);"/>
					</td>
					<td class="label">
						出生日期
					</td>
					<td class="content" colspan="5">
						<input type="text" name="birthdeyStringGreatOrEqual" class="text"  id="birthdeyStringGreatOrEqual"  
						onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)" onblur="changeCheckBox(this);" value="${birthdeyStringGreatOrEqual}"/>
						&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
						<input type="text" name="birthdeyStringLessOrEqual" class="text"  id="birthdeyStringLessOrEqual"  
						onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)" onblur="changeCheckBox(this);"  value="${birthdeyStringLessOrEqual}"/>
					</td>
				</tr>
				<tr style="display:none" id="see4">
					<td class="label">
						民族
					</td>
					<td class="content">
						<eoms:comboBox  name="nationalityStringEqual" id="nationalityStringEqual"  defaultValue="${nationalityStringEqual}" 
							initDicId="12408" styleClass="input select" onchange="changeCheckBox(this);"/>
					</td>
					<td class="label">
						年龄
					</td>
					<td class="content" colspan="3">
						<input type="text" name="ageStringGreatOrEqual" class="text"  id="ageStringGreatOrEqual"  
						onblur="changeCheckBox(this);" value="${ageStringGreatOrEqual}"/>
						&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
						<input type="text" name="ageStringLessOrEqual" class="text"  id="ageStringLessOrEqual"  
						onblur="changeCheckBox(this);" value="${ageStringLessOrEqual}"/>
					</td>
				</tr>
				<tr style="display:none" id="see5">
					<td class="label">
						籍贯
					</td>
					<td class="content">
						<input type="text" name="nativeplaceStringLike" class="text"  id="nativeplaceStringLike"  
						onblur="changeCheckBox(this);" value="${nativeplaceStringLike}"/>
					</td>
					<td class="label">
						参加工作时间
					</td>
					<td class="content" colspan="3">
						<input type="text" name="work_timeStringGreatOrEqual" class="text"  id="work_timeStringGreatOrEqual"  
						value="${work_timeStringGreatOrEqual}" onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)"/>
						&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;
						<input type="text" name="work_timeStringLessOrEqual" class="text"  id="work_timeStringLessOrEqual"  
						value="${work_timeStringLessOrEqual}" 	onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1)"/>
					</td>
				</tr>
				<tr style="display:none" id="see6">
					<td class="label">
						证书类别
					</td>
					<td class="content">
						<input type="text" name="certType" class="text"  id="certType"  value="${certType}"  onblur="changeCheckBox(this);"/>
					</td>
					<td class="label">
						工作单位
					</td>
					<td class="content">
						<input type="text" name="workCompany" class="text"  id="workCompany"  value="${workCompany}" onblur="changeCheckBox(this);"/>
					</td>
					<td class="label">
						曾获奖励名称
					</td>
					<td class="content">
						<input type="text" name="rewardName" class="text"  id="rewardName"  value="${rewardName}" onblur="changeCheckBox(this);";/>
					</td>
				</tr>
				<tr style="display:none" id="see7">
					<td class="label">
						技能
					</td>
					<td class="content">
						<eoms:comboBox  name="skill" id="skill"  defaultValue="${skill}" 
							initDicId="11225" styleClass="input select" onchange="changeCheckBox(this);"/>
					</td>
					<td class="label">
						技能级别
					</td>
					<td class="content">
						<eoms:comboBox  name="skilllevel" id="skilllevel"  defaultValue="${skilllevel}"
						initDicId="12410" styleClass="input select" onchange="changeCheckBox(this);"/>
					</td>
					<td class="label">
						最近培训内容
					</td>
					<td class="content">
						<input type="text" name="pxContent" class="text"  id="pxContent"  value="${pxContent}" 
						onblur="changeCheckBox(this);";/>
					</td>
				</tr>
		</table>
		<table>
			<input type="button" name="更多" value="更多..." onclick="moreSearch();" id="moreBtn"/>
			<input type="hidden" name="btnValue" value="${btnValue}" id="btnValue"/><!-- 用于存储btn的值，便于统计后返回数据的页面判断状态-->
		</table>
		</fieldset>
	<fieldset>
			<legend>请选择统计项目</legend>
	<table class="formTableFor" style="width:100%">
		<tr>
			<td class="label">
				<input  value="isBaseinfo" type="checkbox" name="statisticsItem" id="isBaseinfo" checked="true" />&nbsp;&nbsp;人员基本信息
			</td>
			<td class="label">
				<input  value="isWorkinfo" type="checkbox" name="statisticsItem" id="isWorkinfo" checked="true" />&nbsp;&nbsp;工作经历
			</td>
			<td class="label">
				<input  value="isDwinfo" type="checkbox" name="statisticsItem" id="isDwinfo" checked="true" />&nbsp;&nbsp;技能信息
			</td>
			<td class="label">
				<input  value="isCertinfo" type="checkbox" name="statisticsItem" id="isCertinfo" checked="true" />&nbsp;&nbsp;证书信息
			</td>
			<td class="label">
				<input  value="isRewardinfo" type="checkbox" name="statisticsItem" id="isRewardinfo" checked="true" />&nbsp;&nbsp;奖励信息
			</td>
			<td class="label">
				<input  value="isStudyinfo" type="checkbox" name="statisticsItem" id="isStudyinfo" checked="true" />&nbsp;&nbsp;教育经历
			</td>
			<td class="label">
				<input  value="isPxinfo" type="checkbox" name="statisticsItem" id="isPxinfo" checked="true" />&nbsp;&nbsp;培训经历
			</td>
			<input type="hidden" name="statisticsItems" id="statisticsItems" />
			<input type="hidden" name="checkedIds" id="checkedIds" />
			<input type="hidden" name="checkedIdsStr" id="checkedIdsStr" value="${checkedIdsStr}" />
			<input type="hidden" name="checkedIdsDisableVal" id="checkedIdsDisableVal" value="${checkedIdsDisableVal}" />
			<input type="hidden" name="hasSend" id="hasSend" value="${hasSend}"/>
			<input type="hidden" name="exportFlag" id="exportFlag"/>
		</tr>
	</table>
	</fieldset>
	<input type="button" name="统计" value="统计" onclick="sendBox()"/>
	<input type="button" name="重置" value="重置" onclick="res();"/>
	<logic-el:present name="tableList">
		<input type="button" name="导出" value="导出" onclick="toXLSFile()" />
	</logic-el:present>
	</form>
	<!-- This hidden area is for batch deleting. -->
	<div>
		<table class="formTable" style="width:100%" align="center" id="all">
			<c:if test="${statistics=='2'}"><!--当没有统计时不显示表头-->
					<tr  id="trTop">
						<td class="label" style="width:40px;">区域</td>
						<c:if test="${isPartner=='0'}">
						<td class="label" style="width:200px;">代维公司</td>
						</c:if>
						<c:if test="${isPartner=='1'}">
						<td class="label" style="width:200px;">自维公司</td>
						</c:if>
						<td class="label" style="width:60px;">姓名</td>
						<td class="label" style="width:30px;">性别</td>
						<td class="label" style="width:150px;">身份证号</td>
						<td class="label" style="width:100px;">用户ID</td>
						<c:if test="${isBaseinfo==true}">
							<td class="label" style="width:180px;">员工编码</td>
							<td class="label" style="width:150px;">籍贯</td>
							<td class="label" style="width:40px;">民族</td>
							<td class="label" style="width:75px;">出生日期</td>
							<td class="label" style="width:40px;">年龄</td>
							<td class="label" style="width:150px;">集团短号</td>
							<td class="label" style="width:180px;">Email</td>
							<td class="label" style="width:75px;">参加工作时间</td>
							<td class="label" style="width:150px;">移动电话</td>
							<td class="label" style="width:50px;">在职状态</td>
							<td class="label" style="width:60px;">黑名单标识</td>
						</c:if>
						<c:if test="${isWorkinfo==true}">
							<td class="label" style="width:150px;"  align="center">工作经历(工作单位|职责)</td>
						</c:if>
						<c:if test="${isDwinfo==true}">
							<td class="label" style="width:150px;"   align="center" >技能信息(技能|等级)</td>
						</c:if>
						<c:if test="${isCertinfo==true}">
							<td class="label" style="width:150px;"   align="center" >证书信息(证书|等级)</td>
						</c:if>
						<c:if test="${isRewardinfo==true}">
							<td class="label" style="width:150px;">奖励信息(曾获奖励名称)</td>
						</c:if>
						<c:if test="${isStudyinfo==true}">
							<td class="label" style="width:100px;">毕业院校</td>
							<td class="label" style="width:100px;">所学专业</td>
							<td class="label" style="width:60px;">学历</td>
						</c:if>
						<c:if test="${isPxinfo==true}">
							<td class="label" style="width:150px;">培训经历(最近培训内容)</td>
						</c:if>
					</tr>
			</c:if>
						<c:forEach items="${tableList}" var="tdList">
							<tr>
								<c:forEach items="${tdList}" var="td" varStatus="i">
									<c:if test="${i.count<=6}">
										<c:if test="${td.show}">
												<c:if test="${i.count==3}">
													<td rowspan="${td.rowspan}">
														<a  target="_blank" style="text-decoration: none" href="${app}/personnel/statistics.do?method=pnrUserSearchInto&&userid=${tdList[5].name}">${td.name}</a>
													</td>
												</c:if>
												<c:if test="${i.count!=3}">
													<td rowspan="${td.rowspan}">
														${td.name}
													</td>
												</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示人员基本信息-->
									<c:if test="${isBaseinfo==true}">
										<c:if test="${i.count>=7&&i.count<18}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示公司工作经历信息-->
									<c:if test="${isWorkinfo==true}">
										<c:if test="${i.count==18}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示人员技能-->
									<c:if test="${isDwinfo==true}">
										<c:if test="${i.count==19}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示人员证书-->
									<c:if test="${isCertinfo==true}">
										<c:if test="${i.count==20}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示奖励信息-->
									<c:if test="${isRewardinfo==true}">
										<c:if test="${i.count==21}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">
											    	${td.name}
												</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示代维学习经历信息-->
									<c:if test="${isStudyinfo==true}">
										<c:if test="${i.count==22||i.count==23||i.count==24}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}</td>
											</c:if>
										</c:if>
									</c:if>
									<!--控制是否显示代维培训经历信息-->
									<c:if test="${isPxinfo==true}">
										<c:if test="${i.count==25}">
											<c:if test="${td.show}">
												<td rowspan="${td.rowspan}">${td.name}
												</td>
											</c:if>
										</c:if>
									</c:if>
									</c:forEach>
							</tr>
						</c:forEach>
	</table>
	</div>
<%@ include file="/common/footer_eoms.jsp"%>