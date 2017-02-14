<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>





<form action="statistic.do?method=saveIndicaor" method="post"
	name="IndicatorForm" id="IndicatorForm">

<div>
<table>
	<tr>
		<td class="label">专业</td>
		<td><eoms:comboBox name="special" id="special" sub="devicetype"
			initDicId="11216" alt="allowBlank:false,vtext:'请选择专业...'" /></td>
		<td class="label">设备类型</td>
		<td><eoms:comboBox name="devicetype" id="devicetype"
			initDicId="special" alt="allowBlank:false,vtext:'请选择设备类型...'"
			onchange="detail(this.value)" /></td>
	</tr>
</table>
</div>

<fieldset><legend><b>指标值设置</b></legend>
<table cellpadding="0" class="table protocolMainList" cellspacing="0">
	<thead>
		<tr>
			<th>指标分类</th>
			<th>指标名称</th>
			<th>得分</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td rowspan="4">设备质量：<input class="text" tupe="text"
				name="deviceMass" id="deviceMass" style="width:40%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>设备故障率： <input class="text" tupe="text" name="deviceFault"
				id="deviceFault" style="width:30%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore1"
				style="width:10%" id="baseScore1"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore1" id="compareScore1"
				style="width:10%" alt="allowBlank:false,vtype:'float'" /></td>
		</tr>
		<tr>
			<td>坏板率： <input class="text" tupe="text" name="boardFaule"
				id="boardFaule" style="width:30%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore2"
				id="baseScore2" style="width:10%"
				alt="allowBlank:false,vtype:'float'"
				alt="allowBlank:false,vtype:'float'" /> K <input class="text"
				tupe="text" name="k" id="k" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				id="compareScore2" tupe="text" name="compareScore2"
				id="compareScore2" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /></td>
		</tr>
		<tr>
			<td>设备重大故障数</td>
			<td>每次设备重大事故扣分： <input class="text" tupe="text"
				name="bigFaultScore" id="bigFaultScore" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /></td>
		</tr>
		<tr>
			<td>设备问题数</td>
			<td>每次设备问题扣分： <input class="text" tupe="text" name="proScore"
				id="proScore" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /></td>
		</tr>
		<tr>
			<td rowspan="5">服务质量：<input class="text" tupe="text"
				name="serveMass" style="width:40%;" id="serveMass"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>软件申请及升级问题： <input class="text" tupe="text"
				name="updateFault" id="updateFault" style="width:30%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>每次失败扣分： <input class="text" tupe="text" name="baseScore3"
				id="baseScore3" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 每次材料不足扣分： <input
				class="text" tupe="text" name="compareScore3" id="compareScore3"
				style="width:10%" alt="allowBlank:false,vtype:'float'" /></td>
		</tr>
		<tr>
			<td>故障处理平均时长： <input class="text" tupe="text" id="faultTalkTime"
				name="faultTalkTime" style="width:30%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore4"
				id="baseScore4" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore4" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore4" /></td>
		</tr>
		<tr>
			<td>业务恢复处理平均时长: <input class="text" tupe="text"
				name="busiTalkTime" id="busiTalkTime" style="width:30%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore5"
				id="baseScore5" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore5" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore5" /></td>
		</tr>
		<tr>
			<td>板件返修平均时长: <input class="text" tupe="text"
				name="boardTalkTime" style="width:30%;" id="boardTalkTime"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore6"
				id="baseScore6" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore6" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore6" /></td>
		</tr>
		<tr>
			<td>咨询服务反馈平均时长: <input class="text" tupe="text"
				name="referTalkTime" style="width:30%;" id="referTalkTime"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore7"
				id="baseScore7" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore7" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore7" /></td>
		</tr>
		<tr>
			<td rowspan="3">服务满意度：<input class="text" tupe="text"
				name="servesatisfact" style="width:40%;" id="servesatisfact"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>技术服务满意度： <input class="text" tupe="text" name="skillServe"
				style="width:30%;" id="skillServe"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore8"
				id="baseScore8" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore8" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore8" /></td>
		</tr>
		<tr>
			<td>工程服务满意度： <input class="text" tupe="text" id="projectServe"
				name="projectServe" style="width:30%;"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore9"
				id="baseScore9" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore9" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore9" /></td>
		</tr>
		<tr>
			<td>培训服务满意度: <input class="text" tupe="text" name="trainServe"
				style="width:30%;" id="trainServe"
				alt="allowBlank:false,vtype:'float'" /></td>
			<td>基础分： <input class="text" tupe="text" name="baseScore10"
				id="baseScore10" style="width:10%"
				alt="allowBlank:false,vtype:'float'" /> 比较分： <input class="text"
				tupe="text" name="compareScore10" style="width:10%"
				alt="allowBlank:false,vtype:'float'" id="compareScore10" /></td>
		</tr>
	</tbody>
</table>
</fieldset>
<input type="submit" value="提交" class="btn"></form>


<script type="text/javascript">
 var myjs=jQuery.noConflict();
Ext.onReady(function(){
            v = new eoms.form.Validation({form:'IndicatorForm'});
            v.custom = function(){ 
              var deviceMass = eoms.$('deviceMass').value;
           	 var serveMass = eoms.$('serveMass').value;
           	 var servesatisfact = eoms.$('servesatisfact').value;
           if(parseInt(deviceMass)+parseInt(serveMass)+parseInt(servesatisfact)!=100){

           alert("所有指标类型相加分值不为100");
           			return false;
           }
           var deviceFault = eoms.$('deviceFault').value;
           var boardFaule = eoms.$('boardFaule').value;
           if(parseInt(deviceFault)+parseInt(boardFaule)!=parseInt(deviceMass)){
             alert("子类指标分数相加不等于父类指标");
             eoms.$('deviceMass').focus();
           		return false;}
           var updateFault = eoms.$('updateFault').value;
           var faultTalkTime = eoms.$('faultTalkTime').value;
           var busiTalkTime = eoms.$('busiTalkTime').value;
           var boardTalkTime = eoms.$('boardTalkTime').value;
           var referTalkTime = eoms.$('referTalkTime').value;
           if(parseInt(updateFault)+parseInt(faultTalkTime)+parseInt(busiTalkTime)+parseInt(boardTalkTime)+parseInt(referTalkTime)!=parseInt(serveMass)){
            alert("子类指标分数相加不等于父类指标");
             eoms.$('serveMass').focus();
           		return false;}
           var skillServe = eoms.$('skillServe').value;
           var projectServe = eoms.$('projectServe').value;
           var trainServe = eoms.$('trainServe').value;
           if(parseInt(skillServe)+parseInt(projectServe)+parseInt(trainServe)!=parseInt(servesatisfact)){
            alert("子类指标分数相加不等于父类指标");
             eoms.$('servesatisfact').focus();
           		return false;}
           	var baseScore1 = eoms.$('baseScore1').value;
           	var compareScore1 = eoms.$('compareScore1').value;
           		if(parseInt(baseScore1)+parseInt(compareScore1)!=parseInt(deviceFault)){
           		 alert("基础分加比较分不等于指标分数");
           		 eoms.$('deviceFault').focus();
           						return  false;}
        	var baseScore2 = eoms.$('baseScore2').value;
           	var compareScore2 = eoms.$('compareScore2').value;
           		if(parseInt(baseScore2)+parseInt(compareScore2)!=parseInt(boardFaule)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('boardFaule').focus();
           						return  false;}
           
           	var baseScore4= eoms.$('baseScore4').value;
           	var compareScore4= eoms.$('compareScore4').value;
           		if(parseInt(baseScore4)+parseInt(compareScore4)!=parseInt(faultTalkTime)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('faultTalkTime').focus();
           					return  false;}
           	var baseScore5 = eoms.$('baseScore5').value;
           	var compareScore5 = eoms.$('compareScore5').value;
           		if(parseInt(baseScore5)+parseInt(compareScore5)!=parseInt(busiTalkTime)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('busiTalkTime').focus();
           						return  false;}
           	var baseScore6 = eoms.$('baseScore6').value;
           	var compareScore6 = eoms.$('compareScore6').value;
           		if(parseInt(baseScore6)+parseInt(compareScore6)!=parseInt(boardTalkTime)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('boardTalkTime').focus();
           						return  false;}
           	var baseScore7 = eoms.$('baseScore7').value;
           	var compareScore7 = eoms.$('compareScore7').value;
           		if(parseInt(baseScore7)+parseInt(compareScore7)!=parseInt(referTalkTime)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('referTalkTime').focus();
           						return  false;}
           	var baseScore8 = eoms.$('baseScore8').value;
           	var compareScore8 = eoms.$('compareScore8').value;
           		if(parseInt(baseScore8)+parseInt(compareScore8)!=parseInt(skillServe)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('skillServe').focus();
           						return  false;}
           	var baseScore9 = eoms.$('baseScore9').value;
           	var compareScore9 = eoms.$('compareScore9').value;
           		if(parseInt(baseScore9)+parseInt(compareScore9)!=parseInt(projectServe)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('projectServe').focus();
           						return  false;}
           	var baseScore10 = eoms.$('baseScore10').value;
           	var compareScore10 = eoms.$('compareScore10').value;
           		if(parseInt(baseScore10)+parseInt(compareScore10)!=parseInt(trainServe)){
           		alert("基础分加比较分不等于指标分数");
           		eoms.$('trainServe').focus();
           						return  false;}	
      return true;
           }
            });
            function detail(typeId){
            Ext.Ajax.request({
					url:"${app}/partner/deviceAssess/statistic.do",
					params:{method:"detai",typeId:typeId},
					dataType: "JSON",
					success:function(result) {
					if(result&&result.responseText!=""){
					var er = eval(result.responseText);
					for(var i=0;i<er.length;i++){
					if(er[i].设备质量)
					eoms.$('deviceMass').value=er[i].设备质量;
					if(er[i].服务质量)
					eoms.$('serveMass').value=er[i].服务质量;
					if(er[i].服务满意度)
					eoms.$('servesatisfact').value=er[i].服务满意度;
					if(er[i].设备故障率)
					eoms.$('deviceFault').value=er[i].设备故障率;
					if(er[i].设备问题数score)
					eoms.$('proScore').value=er[i].设备问题数score.substring(2,3);
					if(er[i].设备重大故障数score)
					eoms.$('bigFaultScore').value=er[i].设备重大故障数score.substring(2,3);
					if(er[i].设备故障率score){
					 var score=er[i].设备故障率score;
					 var scores = score.split(";");
					 	eoms.$('baseScore1').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore1').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].坏板率)
					eoms.$('boardFaule').value=er[i].坏板率;
					if(er[i].坏板率score){
					 var score=er[i].坏板率score;
					 var scores = score.split(";");
					 	eoms.$('baseScore2').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore2').value=scores[1].substring(2,scores[1].length);
					 	eoms.$('k').value=scores[2].substring(2,scores[2].length);
					}
					if(er[i].软件申请及升级问题)
					eoms.$('updateFault').value=er[i].软件申请及升级问题;
					if(er[i].软件申请及升级问题score){
					 var score=er[i].软件申请及升级问题score;
					 var scores = score.split(";");
					 	eoms.$('baseScore3').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore3').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].故障处理平均时长)
					eoms.$('faultTalkTime').value=er[i].故障处理平均时长;
					if(er[i].故障处理平均时长score){
					 var score=er[i].故障处理平均时长score;
					 var scores = score.split(";");
					 	eoms.$('baseScore4').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore4').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].业务恢复处理平均时长)
					eoms.$('busiTalkTime').value=er[i].业务恢复处理平均时长;
					if(er[i].业务恢复处理平均时长score){
					 var score=er[i].业务恢复处理平均时长score;
					 var scores = score.split(";");
					 	eoms.$('baseScore5').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore5').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].板件返修平均时长)
					eoms.$('boardTalkTime').value=er[i].板件返修平均时长;
					if(er[i].板件返修平均时长score){
					 var score=er[i].板件返修平均时长score;
					 var scores = score.split(";");
					 	eoms.$('baseScore6').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore6').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].咨询服务反馈平均时长)
					eoms.$('referTalkTime').value=er[i].咨询服务反馈平均时长;
					if(er[i].咨询服务反馈平均时长score){
					 var score=er[i].咨询服务反馈平均时长score;
					 var scores = score.split(";");
					 	eoms.$('baseScore7').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore7').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].技术服务满意度)
					eoms.$('skillServe').value=er[i].技术服务满意度;
					if(er[i].技术服务满意度score){
					 var score=er[i].技术服务满意度score;
					 var scores = score.split(";");
					 	eoms.$('baseScore8').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore8').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].工程服务满意度)
					eoms.$('projectServe').value=er[i].工程服务满意度;
					if(er[i].工程服务满意度score){
					 var score=er[i].工程服务满意度score;
					 var scores = score.split(";");
					 	eoms.$('baseScore9').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore9').value=scores[1].substring(2,scores[1].length);
					}
					if(er[i].培训服务满意度)
					eoms.$('trainServe').value=er[i].培训服务满意度;
					if(er[i].培训服务满意度score){
					 var score=er[i].培训服务满意度score;
					 var scores = score.split(";");
					 	eoms.$('baseScore10').value=scores[0].substring(2,scores[0].length);
					 	eoms.$('compareScore10').value=scores[1].substring(2,scores[1].length);
					}
					
					}
					}
					else{
					eoms.$('deviceMass').value="";
					eoms.$('serveMass').value="";
					eoms.$('servesatisfact').value="";
					eoms.$('deviceFault').value="";
					eoms.$('proScore').value="";
					eoms.$('bigFaultScore').value="";
					 	eoms.$('baseScore1').value="";
					 	eoms.$('compareScore1').value="";
					eoms.$('boardFaule').value="";
					 	eoms.$('baseScore2').value="";
					 	eoms.$('compareScore2').value="";
					 	eoms.$('k').value="";
					eoms.$('updateFault').value="";
					 	eoms.$('baseScore3').value="";
					 	eoms.$('compareScore3').value="";
					eoms.$('faultTalkTime').value="";
					 	eoms.$('baseScore4').value="";
					 	eoms.$('compareScore4').value="";
					eoms.$('busiTalkTime').value="";
					 	eoms.$('baseScore5').value="";
					 	eoms.$('compareScore5').value="";
					eoms.$('boardTalkTime').value="";
					 	eoms.$('baseScore6').value="";
					 	eoms.$('compareScore6').value="";
					eoms.$('referTalkTime').value="";
					 	eoms.$('baseScore7').value="";
					 	eoms.$('compareScore7').value="";
					eoms.$('skillServe').value="";
					 	eoms.$('baseScore8').value="";
					 	eoms.$('compareScore8').value="";
					eoms.$('projectServe').value="";
					 	eoms.$('baseScore9').value="";
					 	eoms.$('compareScore9').value="";
					eoms.$('trainServe').value="";
					 	eoms.$('baseScore10').value="";
					 	eoms.$('compareScore10').value="";
					}	
					},
					})
            }
</script>

<%@ include file="/common/footer_eoms.jsp"%>
