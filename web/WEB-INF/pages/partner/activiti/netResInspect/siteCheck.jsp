<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<%@ include file="/WEB-INF/pages/partner/activiti/netResInspect/siteCheck_basis.jsp"%>
<script type="text/javascript">
	var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
	var v;
	Ext.onReady(function(){
	   v = new eoms.form.Validation({form:'theform'});
	   v.custom = function(){
	   var turnSendValue = jq("input[name='isTurnSend']:checked").val();
	   if(turnSendValue == null){
	   		alert("请选择是否转派！");
	        return false;
	   }else if(turnSendValue == "0"){ //不转派
		   	var value=jq("input[name='isHiddenDanger']:checked").val();
			var specialty = jq("#specialty").val();
			if(value==null){
		        alert("请选择是否隐患！");
		        return false;
	        }else if(value == "1"){
	        	if(specialty == "1280103"){
			       	var isBuild = jq("input[name='isBuild']:checked").val();
		       		if(isBuild == null){
		       			alert("请选择是否建设！");
		        		return false;
		       		}
	        	}else if(specialty == "1280102"){
	        		var autoSendProcess = jq("input[name='autoSendProcess']:checked").val();
	       			if(autoSendProcess == null){
	       				alert("请选择要派发的流程！");
	        			return false;
	       			}
	        	}
	        }
	   }else if(turnSendValue == "1"){ //转派
	   		var isSendTypeValue = jq("input[name='isSendType']:checked").val();//转派类型
	   		if(isSendTypeValue == null){
	   			alert("请选择转派类型！");
	        	return false;
	   		}
	   		var oldcounty='${netResInspect.county}';
		   var oldResourceType='${netResInspect.resourceType}';
		   var county=jq("#country").val();
		   var isSendType=jq("input[name='isSendType']:checked").val();
		   if(oldcounty==county&&oldResourceType==isSendType){
		   		alert("不可转派本人，请重新选择区县或专业类型！");
		   		return false;
		   }
	   }
	   
        return true;
   	   }
	});
	
</script>
<html:form action="/netResInspect.do?method=siteCheckSubmit" styleId="theform" >
<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}" /><!-- 流程id -->
<input type="hidden" id="taskId" name="taskId" value="${taskId}" /><!-- 任务id -->
<input type="hidden" id="specialty" name="specialty" value="${netResInspect.specialty}" /><!-- 专业 -->
<input type="hidden" id="resourceType" name="resourceType" value="${netResInspect.resourceType}" /><!-- 资源类型 -->
<input type="hidden" id="resourceType" name="resourceType" value="${netResInspect.resourceType}" /><!-- 资源类型 -->
<br/>
<table id="sheet" class="formTable" style="width:100%">
	<caption>现场核实信息</caption>
	<tr>
	  <td class="label" style="width:10%">资源名称<font name="bitian">*</font></td>
	  <td colspan="5" style="width:20%">
	    <input type="text" class="text max" id="resourceName" name="resourceName" value="${netResInspect.resourceName}" alt="allowBlank:false"/>
	  </td>
	</tr>
	
	<tr>
	  <td class="label" style="width:10%">是否我方资源<font name="bitian">*</font></td>
	  <td class="content"  style="width:20%">
	  	<eoms:comboBox id="isOurResources" name="isOurResources" defaultValue="${netResInspect.isOurResources}" initDicId="12804" alt="allowBlank:false" styleClass="select" />
	  </td>
	  <td class="label" style="width:10%">是否现场施工作业<font name="bitian">*</font></td>
	  <td class="content" style="width:20%">
	  	<eoms:comboBox id="isSiteOperation" name="isSiteOperation"  defaultValue="${netResInspect.isSiteOperation}" initDicId="12805" alt="allowBlank:false" styleClass="select" />
	  </td>
	  <td class="label" style="width:10%">紧急程度<font name="bitian">*</font></td>
	  <td class="content" style="width:20%">
	  	<eoms:comboBox id="degree" name="degree" defaultValue="${netResInspect.degree}" initDicId="12806" alt="allowBlank:false" styleClass="select" />
	  </td>
	</tr>
	
	<tr>
	  <td class="label" style="width:10%">有效性<font name="bitian">*</font></td>
	  <td class="content"  style="width:20%">
	  	<eoms:comboBox id="validity" name="validity" defaultValue="${netResInspect.validity}" initDicId="12808" alt="allowBlank:false" styleClass="select" />
	  </td>
	  <td class="label" style="width:10%">重要程度<font name="bitian">*</font></td>
	  <td class="content" style="width:20%">
	  	<eoms:comboBox id="importance" name="importance" defaultValue="${netResInspect.importance}" initDicId="12807" alt="allowBlank:false" styleClass="select" />
	  </td>
	 <!--  <td class="label" style="width:10%">是否隐患*</td>
	  <td class="content" style="width:20%">
	  	<eoms:comboBox id="isHidden" name="isHidden" defaultValue="${netResInspect.isHidden}" initDicId="12809" alt="allowBlank:false" styleClass="select" />
	  </td> -->
	</tr>
	
	<tr>
	  <td class="label" style="width:10%">照片清单</td>
	  <td colspan="5" style="width:20%">
	  	<input type="button" class="btn" id="showPhotos" name="showPhotos" value="查看照片" onclick="selectPhoto()">
	  	<!-- <input type="button" class="btn" id="showMap" name="showMap" value="地图"> -->
	  </td>
	</tr>
	
	<tr id="isTurnSendTr">
	  <td class="label" style="width:10%">是否转派*</td>
	  <td colspan="5" style="width:20%">
	  		<input name="isTurnSend" type="radio" value="0" />不转派
	  		<input name="isTurnSend" type="radio" value="1" />转派
	  </td>
	</tr>
	
	<tr id="isSendTypeTr" style="display:none">
	  <td class="label" style="width:10%">转派类型*</td>
	  <td colspan="5" style="width:20%">
	  		<input name="isSendType" type="radio" value="1281101" /><span name="1281101_span">设备类</span>
	  		<input name="isSendType" type="radio" value="1281102" /><span name="1281102_span">线路类</span>
	  		<input name="isSendType" type="radio" value="1281103" /><span name="1281103_span">无线类</span>
	  </td>
	</tr>
	
	<tr id="isSendCountryTr" style="display:none">
	  <td class="label" style="width:10%">转派区县*</td>
	  <td colspan="5" style="width:20%">
	  		<select name="country" id="country" class="select">
	  			<option value="">
							--请选择所在县区--
				</option>
	  		</select>
	  </td>
	</tr>
	
	<tr id="isHiddenDangerTr" style="display:none"> 
	  <td class="label" style="width:10%">是否隐患*</td>
	  <td colspan="5" style="width:20%">
	  		<input name="isHiddenDanger" type="radio" value="1" />是
	  		<input name="isHiddenDanger" type="radio" value="0" />否
	  </td>
	</tr>
	
	<tr id="isBuildTr" style="display:none">
	  <td class="label" style="width:10%">是否建设*</td>
	  <td colspan="5" style="width:20%">
	  		<input name="isBuild" type="radio" value="1" />是
	  		<input name="isBuild" type="radio" value="0" />否
	  </td>
	</tr>
	
	<tr id="autoSendProcessTr" style="display:none">
	  <td class="label" style="width:10%">派发流程*</td>
	  <td colspan="5" style="width:20%">
	  		<input name="autoSendProcess" type="radio" value="1" />预检预修流程
	  		<input name="autoSendProcess" type="radio" value="0" />线路抢修流程
	  </td>
	</tr>
	
	<tr>
	  <td class="label" style="width:10%">备注</td>
	  <td colspan="5" style="width:20%">
	    <textarea class="textarea max" name="siteCheckRemark" id="siteCheckRemark"
					alt="allowBlank:true,maxLength:2000,vtext:'请填入备注，最多输入 2000 字符'"></textarea>
	  </td>
	</tr>
</table>
<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" styleId="method.save" >
				处理
			</html:submit>
		</div>
	</html:form>
<script type="text/javascript">
//隐藏必填
function hiddenRequired(){
		jq("#resourceName").removeAttr("alt"); //资源名称
		jq("#isOurResources").removeAttr("alt"); //是否我方资源
		jq("#isSiteOperation").removeAttr("alt"); //是否现场施工作业
		jq("#degree").removeAttr("alt"); //紧急程度
		jq("#validity").removeAttr("alt"); //有效性
		jq("#importance").removeAttr("alt"); //重要程度
		
		jq("font[name='bitian']").hide();
	}
	
//显示必填
function displayRequired(){
		jq("#resourceName").attr("alt","allowBlank:false"); //资源名称
		jq("#isOurResources").attr("alt","allowBlank:false"); //是否我方资源
		jq("#isSiteOperation").attr("alt","allowBlank:false"); //是否现场施工作业
		jq("#degree").attr("alt","allowBlank:false"); //紧急程度
		jq("#validity").attr("alt","allowBlank:false"); //有效性
		jq("#importance").attr("alt","allowBlank:false"); //重要程度
		
		jq("font[name='bitian']").show();
	}


	jq(function(){
		var resourceType = jq("#resourceType").val();
		jq("input[type='radio'][name='isSendType'][value='"+resourceType+"']").attr("checked",'checked');
	});

	//是否转派
	jq(document).delegate("input[name='isTurnSend']", "change", function(){
		var specialty = jq("#specialty").val();
		//alert("----specialty="+specialty);
		if(specialty != "1280101" && specialty != "1280102" && specialty != "1280103"){
			alert("专业类型不在枚举范围之内！");
			disableSubmitBtn();
			return;
		}
		
		var resourceType = jq("#resourceType").val();
		//alert("------resourceType="+resourceType);
		
		var obj = jq(this);
		var value = obj.val();
		if(value == "0"){ //不转派
			displayRequired();
			jq("#isSendTypeTr").css("display","none");
			jq("input[name='isSendType']").removeAttr('checked');
			jq("#isHiddenDangerTr").css("display","block");
			var resourceType = jq("#resourceType").val();
			jq("#isSendCountryTr").css("display","none");
			jq("input[type='radio'][name='isSendType'][value='"+resourceType+"']").attr("checked",'checked'); ;
		}else if(value == "1"){ //转派
			hiddenRequired();
			//是否隐患
			jq("#isHiddenDangerTr").css("display","none");
			jq("input[name='isHiddenDanger']").removeAttr('checked');
			//派发流程
			jq("#autoSendProcessTr").css("display","none");
			jq("input[name='autoSendProcess']").removeAttr('checked');
			//是否转建设
			jq("#isBuildTr").css("display","none");
			jq("input[name='isBuild']").removeAttr('checked');
			
			//转派类型
			jq("#isSendTypeTr").css("display","block");
			jq("#isSendCountryTr").css("display","block");
			var city='${netResInspect.city}';
			var country='${netResInspect.county}';
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+city;
			//var result=getResponseText(url);
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("[{")!=0){
								res = "[{"+result.responseText;
					}
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					
					var json = eval(res);
					
					var countyName = "country";
					var arrOptions = json[0].cb.split(",");
					var obj=$(countyName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						if(arrOptions[i]==country){
							opt.selected = true;
						}
						obj.options[j]=opt;
						j++;
						i++;
					}
										
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
			//jq("input[type='radio'][name='isSendType'][value='"+resourceType+"']").remove();
			//jq("span[name='"+resourceType+"_span']").remove();
		}else{
		
		}
	});

	//是否隐患
	jq(document).delegate("input[name='isHiddenDanger']", "change", function(){
		var specialty = jq("#specialty").val();
		if(specialty != "1280101" && specialty != "1280102" && specialty != "1280103"){
			alert("专业类型不在枚举范围之内！");
			disableSubmitBtn();
			return;
		}
		var obj = jq(this);
		var value = obj.val();
		if(specialty != ""){
			if(value == "1"){
				if(specialty == "1280103"){ //是无线专业，显示是否转建设
					jq("#isBuildTr").css("display","block");
				}else if (specialty == "1280102"){ //线路专业
					jq("#autoSendProcessTr").css("display","block");
				}
			}else if(value == "0"){
				jq("#isBuildTr").css("display","none");
				jq("input[name='isBuild']").removeAttr('checked');
				jq("#isLineHiddenDangerTr").css("display","none");
				jq("input[name='isLineHiddenDanger']").removeAttr('checked');
				jq("#autoSendProcessTr").css("display","none");
				jq("input[name='autoSendProcess']").removeAttr('checked');
			}
		}else{
			alert("未找到该工单的专业！");
			disableSubmitBtn();
		}
	});
	
	//禁用处理按钮
	function disableSubmitBtn(){
		jq("input[type='submit']").attr("disabled",true);
	}
	/**
	*  打开图片选择页面
	*/
	function selectPhoto(){	
		var pid='${netResInspect.processInstanceId}';
		//alert(pid);
		var url = '${app}/activiti/netResInspect/netResInspect.do?method=showCreateWorkPhoto&pid='+pid;
        //window.open(url);
		var _sHeight = 600;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
</script>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
</script>