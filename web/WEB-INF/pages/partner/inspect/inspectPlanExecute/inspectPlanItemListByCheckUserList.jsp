<%@page import="java.util.Date"%><%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.boco.eoms.base.util.StaticMethod"%>
<%
 request.setAttribute("currentDate",StaticMethod.getCurrentDateTime());
%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<style>
.coolscrollbar{scrollbar-arrow-color:yellow;scrollbar-base-color:lightsalmon;max-width: 330px;}
</style>
<script type="text/javascript">
var jq=jQuery.noConflict();
var pid;         //计划id
var curPage=1;   //当前是第几页
var total=0;     //总共有多少张照片
	function openImport(handler){
		var el = Ext.get('listQueryObject');
		if(el.isVisible()){
			el.slideOut('t',{useDisplay:true});
			handler.innerHTML = "打开查询界面";
		}
		else{
			el.slideIn();
			handler.innerHTML = "关闭查询界面";
		}
	}

var allBodyDomId="";
var exceptionFlag = '0'
function addBodyDomId(domId){
	allBodyDomId = allBodyDomId+domId+";";
}
var isAllSaveEnd = false;
var isAllSave= false;
function saveAllResult(){
	var inspectState = '${InspectPlanRes.inspectState}';
		if('1'==inspectState){
			alert('该资源已巡检');
			return ;
		}
	if(confirm('是否要提交该结果?')){
			isAllSave = true;
 			if(null != allBodyDomId && '' != allBodyDomId){
				var tempBodyDomId = allBodyDomId.split(";");
				if(tempBodyDomId.length !=0){
					var returnJson = "";
					for(var d_i = 0;d_i<tempBodyDomId.length-1;d_i++){
						var inputIdTemp = tempBodyDomId[d_i].split("|")[0];
						var inputTypeTemp = tempBodyDomId[d_i].split("|")[1];
						var hasDevice = jq("#hasDevice_"+inputIdTemp).val();
						exceptionFlag = '0';
						if('1'==hasDevice+''){
							exceptionFlag = '-1';
						}
						var value = checkResultAndReturnValue(inputIdTemp,inputTypeTemp);
						var exceptionDomId='#exceptionContent_'+inputIdTemp;
						returnJson+="{\"inputId\":\""+inputIdTemp+"\",\"value\":\""+value+"\",\"exceptionContent\":\""+jq(exceptionDomId).val()+"\",\"exceptionFlag\":\""+exceptionFlag+"\"}";
						if(d_i != (tempBodyDomId.length-2)){
							returnJson+=",";
						}else{
							returnJson+="";
						}
					}
					//alert("["+returnJson+"]");
					ajaxSaveAllResult("["+returnJson+"]");
				}
		}
 	}else{
       	return false;
    }
    isAllSave = false;
}
//检查巡检结果并返回巡检结果
			function checkResultAndReturnValue(inputId,inputType){
				if(inputType == "")return;
				var val = '';
				if(inputType == 'text'){
					var domId2='#input_'+inputId;
						val = jq(domId2).val();
						//if(val==''&&'1'==hasDevice+''){
						//	alert("请填写巡检结果");
						//	return;
						//}
				}
				if(inputType == 'number'){
					var domId2='#input_'+inputId;
					val = jq(domId2).val();
					//if(val==''&&'1'==hasDevice+''){
					//	alert("请填写巡检结果");
					//	return;
					//}
					var len = val.length;
					for(var i=0;i<len;i++) {
			            c=val.charAt(i).charCodeAt(0);
			            if(c<48 || c>57) {
			            	alert("填写内容必须为数字");
			                event.preventDefault();
			                return;
			            }
			        }
				}
				if(inputType == 'radio'){
					var domId = '#radio_'+inputId;
					var value = jq(domId).find("option:selected").val();
					val = value;
					//if(('' == value|| null == value)&&'1'==hasDevice+''){
					//	partner.showMsg("请选择单选值");
					//	return;
					//}
				}
				if(inputType == 'multiple'){
					var domId = '\"[name=multiple_'+inputId+'][checked]\"';
			   		jq(domId).each(function(){
			   	 			 val+=jq(this).val()+"|";
			   		 });
			   		 //if(('' == val|| null == val)&&'1'==hasDevice+''){
					//	partner.showMsg("请选择多选值");
					//	return;
					//}
				}
				return val;
			}

/**
*    单个巡检项保存
*/
function saveResult(inputId,inputType){
	var inspectState = '${InspectPlanRes.inspectState}';
	if('1'==inspectState){
		Ext.Msg.alert('提示','该资源已巡检');
		return ;
	}
	if('3'==inspectState){
	 	Ext.Msg.alert('提示','该资源已超时');
	 return;
	}
	exceptionFlag = 0;
	//alert("inputId  "+inputId+"  inputType  "+inputType);
	var hasDevice = jq("#hasDevice_"+inputId).val();
	if('1'==hasDevice+''){
		exceptionFlag = '-1';
		//return;
	}
	var domId='#'+inputId;
	if(inputType == "")return;
	var val = ''
	if(inputType == 'text'){

	var domId2='#input_'+inputId;
		val = jq(domId2).val();
		//if(val==''&&'1'==hasDevice+''){
		//	alert("请填写巡检结果");
		//	return;
		//}
	}
	if(inputType == 'number'){
		var domId2='#input_'+inputId;
		val = jq(domId2).val();
		if(val==''&&'1'==hasDevice+''){
			alert("请填写巡检结果");
			return;
		}
		//var minValueTemp = jq.trim(jq("#minValue").val());
		var len = val.length;
		for(var i=0;i<len;i++) {
            c=val.charAt(i).charCodeAt(0);
            if(c<48 || c>57) {
                alert("填写内容必须为数字");
                event.preventDefault();
                return;
            }
        }
	}
	if(inputType == 'radio'){
		var domId = '#radio_'+inputId;
		var value = jq(domId).find("option:selected").val();
		val = value;
		if(('' == value|| null == value)&&'1'==hasDevice+''){
			alert("请选择单选值");
			return;
		}
	/*
		var domId = '#radio_'+inputId+':checked';
		var value = jq(domId).val();
		//alert("domId    "+domId+"    value    "+value)
		if('' == value|| null == value){
			alert("请选择单选值");
			return;
		}
		val = value;
		*/
	}
	if(inputType == 'multiple'){
		var domId = '[name=multiple_"'+inputId+'"][checked]';
   		jq(domId).each(function(){
   	 			 val+=jq(this).val()+"|";
   		 })
   		 if(('' == val|| null == val)&&'1'==hasDevice+''){
			alert("请选择多选值");
			return;
		}
	}


		//${app }/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetail&id=${list.id}
		if(isAllSave){
			ajaxSaveAllResult(inputId,val);
		}else{
			if(confirm('是否要提交该结果?')){
	 			ajaxSaveResult(inputId,val);
	 		}else{
	       		return false;
	    	 }
		}
	}

function ajaxSaveResult(inputId,val){
	//alert("isAllSave   "+isAllSave);
	var domId='#exceptionContent_'+inputId;
	//alert(jq(domId).val());
	Ext.get(document.body).mask('保存信息中......');
	Ext.Ajax.request({
		    url: '${app }/partner/inspect/inspectPlanExecute.do?method=saveCheckResult',
		    params:{inputId:inputId,value:val,exceptionContent:jq(domId).val(),exceptionFlag:exceptionFlag},
		    success: function(response){
		    	 Ext.get(document.body).unmask();
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
				    	 Ext.Msg.alert('提示','数据提交成功');
				    	 return true;
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','该资源已巡检');
      			 }

		    },
	     	failure: function(response) {
	     			Ext.get(document.body).unmask();
                    Ext.Msg.alert('提示','数据提交失败');
            }
			});
			//alert(inputId);
}
function ajaxSaveAllResult(json){
	//alert(jq(domId).val());
	Ext.get(document.body).mask('保存信息中......');
	Ext.Ajax.request({
		    url: '${app }/partner/inspect/inspectPlanExecute.do?method=saveAllCheckResult&planResId=${planResId}',
		    params: {json:json},

		    success: function(response){
		    	 Ext.get(document.body).unmask();
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
		    		if(isAllSave){
		    			if(isAllSaveEnd){
			    			Ext.Msg.alert('提示','数据提交成功',function(){
			    			window.location.href=window.location.href;
			    			});
			    			return true;
						     isAllSaveEnd = false;
						     isAllSave= false;
		    			}
		    		}else{

				    	 Ext.Msg.alert('提示','数据提交成功',function(){
				    	 	window.location.href=window.location.href;
				    	 });
			    			return true;
					     }

      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','该资源已巡检');
      			 }

		    },
	     	failure: function(response) {
	     			Ext.get(document.body).unmask();
                    Ext.Msg.alert('提示','数据提交失败');

            }
			});
}


function getValues(parentDict,inputType,id,checkResult,isTimeOut){
	htmlStr = '';
	if(parentDict!=''){
	var url="<%=request.getContextPath()%>/partner/inspect/inspectTemplateManger.do?method=getDictValues&dict="+parentDict;
	Ext.Ajax.request({
		url : url ,
		method: 'POST',
		success: function ( result, request ) {
			res = result.responseText;
			var json = eval(res);
			if(inputType == 'radio'){
				htmlStr ='';
				if(json.length==0)return;
				//for(var i=0;i<json.length;i++){
				//	htmlStr = htmlStr+'<input type="radio" id=radio_'+id+' name="dictNormalValue" value="'+json[i].dictId+'" />'+json[i].name;
				//}
				var start="<select id=radio_"+id+">";
				for(var i=0;i<json.length;i++){
					//htmlStr = htmlStr+'<input type="radio" id=radio_'+id+' name="dictNormalValue" value="'+json[i].dictId+'" />'+json[i].name;
					if('' != checkResult && checkResult==json[i].dictId ){
						htmlStr = htmlStr+'<option selected=\'selected\'  value="'+json[i].dictId+'">'+json[i].name+'</option>';
					}else{
						htmlStr = htmlStr+'<option value="'+json[i].dictId+'">'+json[i].name+'</option>';
					}
				}
				var tempId = "#radio_"+id;
				jq(tempId).attr("class","text");
				var end='</select>';
				var domId="#span_"+id;
				var val=start+htmlStr+end;
				jq(domId).html(val);
			}
			if(inputType == 'multiple'){
				if(isTimeOut == 'true'){
					htmlStr='';
					if(json.length==0) return;
					var resultData='';
					if(''!=checkResult){
						resultData = checkResult.split("|");
					}
					for(var i=0;i<json.length;i++){
						if('' != checkResult && resultData.length != 0){
							 for(var resultData_i=0;resultData_i<resultData.length;resultData_i++){
								if(resultData[resultData_i]==json[i].dictId){
									htmlStr=htmlStr+json[i].name+',';
								}
							 }
						}
					}
					var domId="#span_"+id;
					var showDom = "<input type='text' class='text'  value='"+htmlStr+"' />"
					jq(domId).html(showDom);


				}else{
					htmlStr='';
					if(json.length==0) return;
					var resultData='';
					if(''!=checkResult){
						resultData = checkResult.split("|");
					}
					for(var i=0;i<json.length;i++){
						var checkedFlag = false;
						if('' != checkResult && resultData.length != 0){
							 for(var resultData_i=0;resultData_i<resultData.length;resultData_i++){
								if(resultData[resultData_i]==json[i].dictId){
									htmlStr=htmlStr+'<input checked="true" type="checkbox" name=multiple_'+id+' value="'+json[i].dictId+'">'+json[i].name+'</input>'
									checkedFlag = true;
									break;
								}
							 }
							 if(!checkedFlag){
								 htmlStr = htmlStr+'<input type="checkbox" name=multiple_'+id+' value="'+json[i].dictId+'">'+json[i].name+'</input>';
							 }
						}else{
							htmlStr = htmlStr+'<input type="checkbox" name=multiple_'+id+' value="'+json[i].dictId+'">'+json[i].name+'</input>';
						}
						if(i!=0&&i%3==0){
							htmlStr+="<br>";
						}
					}
					var domId="#span_"+id;
					jq(domId).html(htmlStr);
				}
			}
		},
		failure: function ( result, request) {
			Ext.MessageBox.alert('Failed', '操作失败'+result.responseText);
		}
	});
	}else{

	}
}

function querySelect(){
	var value = jq("#inputTypeStringEqual").val();
	//alert(value);

	jq("#queryForm").submit();
}
function queryAll(){
	document.location.href = '${app}/partner/inspect/inspectPlanExecute.do?method=goToInspectPlanMainItemList&planResId=${planResId }';
//jq("#queryForm").attr('action','inspectPlanExecute.do?method=goToInspectPlanMainItemList&planResId=${planResId }')
//	jq("#queryForm").submit();
}


function hasDevice(selDom,id){
	var hidenDom ='#r_div_'+id;
	var exceptionDom ='#exceptionContent_'+id;
	if(selDom.value+"" == '1'){
		jq(hidenDom).hide();
		jq(exceptionDom).hide();
	}else{
		jq(hidenDom).show();
		jq(exceptionDom).show();
	}
}


jq(function(){
	jq("#pifilter").hide();
	jq("#shoupicture").hide();

	jq("#close").click(function(){
		jq("#pifilter").hide();
		jq("#shoupicture").hide();
	});

	jq("#up").click(function(){
		if(curPage == '' || curPage-0<=1){
			alert('当前已经是第一张');
			return;
		}else{
			curPage = curPage-1;   //表示该显示第几页
//			Ext.get(document.body).mask('请稍等......');
			jq("#showImg").html("<img  width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
			Ext.Ajax.request({
				url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+pid+"&curPage="+curPage+"&idType=inspectPlan>",
				success: function(x){
					var jsonResult = Ext.util.JSON.decode(x.responseText);
					var photoType = jsonResult[0].msg;
					if(photoType == '0'){
						jq("#photoType").html('签退图片');
					} else if(photoType == '1'){
						jq("#photoType").html('签到图片');
					} else if(photoType == '2'){
						jq("#photoType").html('巡检图片');
					} else if(photoType == '3'){
						jq("#photoType").html('服务器上传图片');
					} else{
						jq("#photoType").html('');
					}
				}
			});
			setTimeout(function(){
					Ext.get(document.body).unmask();
			},5000);
		}
	});

	jq("#down").click(function(){
		if(curPage<total){
			curPage=curPage-0+1;
//			Ext.get(document.body).mask('请稍等......');
				jq("#showImg").html("<img  width='600' height='480'  src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
				Ext.Ajax.request({
					url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+pid+"&curPage="+curPage+"&idType=inspectPlan>",
					success: function(x){
						var jsonResult = Ext.util.JSON.decode(x.responseText);
						var photoType = jsonResult[0].msg;
					if(photoType == '0'){
						jq("#photoType").html('签退图片');
						} else if(photoType == '1'){
							jq("#photoType").html('签到图片');
						} else if(photoType == '2'){
							jq("#photoType").html('巡检图片');
						} else if(photoType == '3'){
							jq("#photoType").html('服务器上传图片');
						} else{
							jq("#photoType").html('');
						}
					}
				});
				setTimeout(function(){
					Ext.get(document.body).unmask();
				},5000);
		}else{
			alert("当前已是最后一张");
			return;
		}
	});
})


function pictureId(id){
//先获得屏幕的宽度和高度
		curPage = 1;    //先把当前页设置为0
		jq(this).parent()
		pid = id;
		var height = jq(window).height();
		var width = jq(window).width();
		jq("#pifilter").css("width",width+"px")
						.css("height",height+"px");
		jq("#shoupicture").css("left",width/2-200+"px")
							.css("top",height/2-300+"px")
		jq("#pifilter").show();
		jq("#shoupicture").show();
		//下一步操作时把这张图片显示出来
		jq("#showImg").html("<img  width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage='>");
		//jq("#mypicture").html("<img width='760' height='470' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage=1>");
		Ext.Ajax.request({
					url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+pid+"&curPage=1&idType=inspectPlan>",
					success: function(x){
						var jsonResult = Ext.util.JSON.decode(x.responseText);
						var photoType = jsonResult[0].msg;
						total=jsonResult[0].msg2;
					if(photoType == '0'){
						jq("#photoType").html('签退图片');
						} else if(photoType == '1'){
							jq("#photoType").html('签到图片');
						} else if(photoType == '2'){
							jq("#photoType").html('巡检图片');
						} else if(photoType == '3'){
							jq("#photoType").html('服务器上传图片');
						} else{
							jq("#photoType").html('');
						}
					}
				});
}

function showSound(id){
	if(!jq("#hidsoundControl_"+id).is(":visible")){
		Ext.Ajax.request({
					url:"${app }/partner/inspect/inspectPlanExecute.do?method=getAudioPath&id="+id,
					success: function(response){
						//var audioPath = Ext.util.JSON.decode(x.responseText);
						//alert(response.responseText);
						var audioDom = "<embed id='media_'"+id+" type=audio/x-pn-realaudio-plugin src=${app }/fileTemp/"+response.responseText+" mastersound hidden='false' loop='false' autostart='false' controls='PlayButton'>"
						//var audioDom = "<embed id='media_'"+id+" type=audio/x-pn-realaudio-plugin src=${app }/17.mp3 mastersound hidden='false' loop='false' autostart='false' controls='PlayButton'>"
						jq("#hidsoundControl_"+id).html(audioDom);
						jq("#hidsoundControl_"+id).show();
						jq("#playBtn_"+id).val("隐藏播放器");
					}
				});
	}else{
		var obj=document.getElementById("media_"+id);
		if(obj){
			obj.stop();
		}
		jq("#hidsoundControl_"+id).hide();
		jq("#playBtn_"+id).val("显示播放器");
	}
}

function uploadpicture(typeid){
     	var _sHeight = 480;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	    var url ="${app }/partner/inspect/inspectPlanExecute.do?method=gotoInspectPlanMainUploadPicture&id="+typeid+"&typeId=planItem";
	    var pro =  window.showModalDialog(url,window,sFeatures);
	}

function updatePictureNum(id,picturnNum){
	jq("#picTrue"+id).html(picturnNum);
	jq("#checkPicture"+id).html("<input type='button' value='查看'  onclick='pictureId("+id+")' class='btn'/>");
};

function backList(id){
window.location="${app }/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetailList&id="+id
}

</script>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>

<!-- Information hints area end-->

<logic:present name="inspectPlanItemList" scope="request">
	<display:table name="inspectPlanItemList" cellspacing="0" cellpadding="0"
		id="inspectPlanItemList" class="table inspectPlanItemList" export="false" sort="list"
		pagesize="${pageSize}" requestURI="inspectPlanExecute.do?method=goToInspectPlanMainItemList"
		partialList="true" size="${resultSize}">

		<display:column sortable="true" headerClass="sortable" title="序号">
			${inspectPlanItemList_rowNum }
	    </display:column>

	    <display:column sortable="true" headerClass="sortable" title="设备类别">
			${inspectPlanItemList.bigitemName }
	    </display:column>

		<display:column title="巡检项目" style="width:60px;">
		 ${inspectPlanItemList.inspectItem }
		</display:column>
		<display:column title="巡检内容" style="width:60px;">
		<pre>${inspectPlanItemList.content }</pre>
		<%---
			<eoms:id2nameDB id="${inspectPlanItemList.specialty}" beanId="ItawSystemDictTypeDao" />
		--%>
		</display:column>
		<c:if test="${isCheckUser}">
			<display:column title="是否有设备" >
				<c:choose>
				<c:when test="${inspectPlanItemList.exceptionFlag eq '-1'}">
					<select id="hasDevice_${inspectPlanItemList.id}" onchange="hasDevice(this,'${inspectPlanItemList.id}')">
						  <option value ="0">有</option>
						  <option value ="1" selected="selected">无</option>
					</select>
				</c:when>
				<c:otherwise>
					<select id="hasDevice_${inspectPlanItemList.id}" onchange="hasDevice(this,'${inspectPlanItemList.id}')">
						  <option value ="0">有</option>
						  <option value ="1">无</option>
					</select>
				</c:otherwise>
				</c:choose>
			</display:column>
			</c:if>
		<display:column title="输入方式" style="width:60px;">
		<c:choose>
		<c:when test="${inspectPlanItemList.inputType eq 'text'}">
		文本
		</c:when>
		<c:when test="${inspectPlanItemList.inputType eq 'number'}">
		数值
		</c:when>
		<c:when test="${inspectPlanItemList.inputType eq 'radio'}">
		单选
		</c:when>
		<c:when test="${inspectPlanItemList.inputType eq 'multiple'}">
		多选
		</c:when>
		</c:choose>
		</display:column>
		<%--<display:column title="正常值范围" >
			<c:choose>
			<c:when test="${inspectPlanItemList.inputType eq 'text'|| inspectPlanItemList.inputType eq 'number'}">
				${inspectPlanItemList.normalRange}
			</c:when>
			<c:when test="${inspectPlanItemList.inputType eq 'radio'}">
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectPlanItemList.normalRange}" />
			</c:when>
			<c:when test="${inspectPlanItemList.inputType eq 'multiple'}">
				<eoms:id2nameDB beanId="ItawSystemDictTypeDao"
						id="${inspectPlanItemList.normalRange}" />
			</c:when>
			<c:otherwise>
			空
			</c:otherwise>
			</c:choose>
		</display:column>--%>
		<c:if test="${isCheckUser}">
		<display:column sortable="false" headerClass="sortable" title="填写巡检结果"
			media="html">
			<div class="coolscrollbar">
				<c:choose>
				<c:when test="${inspectPlanItemList.inputType eq 'text'}">
						<div id='r_div_${inspectPlanItemList.id}'>
						<c:if test="${(inspectPlanItemList.saveTime  eq ''|| empty inspectPlanItemList.saveTime)&& currentDate<inspectPlanItemList.endTime}">
								<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>
						</c:if>
						<c:if test="${currentDate>inspectPlanItemList.endTime}">
							<span><input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/></span>
						</c:if>
						<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
										<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>
								</c:when>
							</c:choose>
						</c:if>
						</div>
				</c:when>

				<c:when test="${inspectPlanItemList.inputType eq 'number'}">
						<div id='r_div_${inspectPlanItemList.id}'>
							<c:if test="${(inspectPlanItemList.saveTime  eq ''|| empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
									<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>
									<!--
									<input type="button" value="保存" id='saveBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
									 -->
							</c:if>
							<c:if test="${currentDate>inspectPlanItemList.endTime}">
								<span><input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/></span>
							</c:if>
							<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
								<c:choose>
									<c:when test="${currentDate<inspectPlanItemList.endTime }">
										<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>
									</c:when>
								</c:choose>
							</c:if>
						</div>
				</c:when>
				<c:when test="${inspectPlanItemList.inputType eq 'radio'}">
						<div id='r_div_${inspectPlanItemList.id}'>
							<c:if test="${(inspectPlanItemList.saveTime  eq '' || empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
								<span id="span_${inspectPlanItemList.id}">
								<script type="text/javascript">
								getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','false')
								</script>
								</span>
							</c:if>

							<c:if test="${currentDate>inspectPlanItemList.endTime}">
								<span><input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="<eoms:id2nameDB beanId='ItawSystemDictTypeDao' id='${inspectPlanItemList.itemResult}' />"/></span>
							</c:if>


							<c:if test="${inspectPlanItemList.saveTime ne '' && !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
									<span id="span_${inspectPlanItemList.id}">
								<script type="text/javascript">
								getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','false')
								</script>
								</span>
								</c:when>
							</c:choose>
							</c:if>
						</div>
				</c:when>
				<c:when test="${inspectPlanItemList.inputType eq 'multiple'}">
					<div id='r_div_${inspectPlanItemList.id}'>
						<c:if test="${(inspectPlanItemList.saveTime  eq '' || empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
								<span id="span_${inspectPlanItemList.id}">
								<script type="text/javascript">
								getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','false')
								</script>
								</span>
						</c:if>
						<c:if test="${currentDate>inspectPlanItemList.endTime}">
							已结束
									<span id="span_${inspectPlanItemList.id}">
										<script type="text/javascript">
										getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','true')
										</script>
									</span>
						</c:if>

						<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
								<div id='r_div_'${inspectPlanItemList.id}>
									<span id="span_${inspectPlanItemList.id}">
									<script type="text/javascript">
										getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','false')
									</script>
									</span>
									</div>
								</c:when>
							</c:choose>
						</c:if>
					</div>
				</c:when>
				</c:choose>
			</div>

		</display:column>
		</c:if>

		<c:if test="${isCheckUser}">
			<display:column title="填写异常">
				<input width="2" id='exceptionContent_${inspectPlanItemList.id}' name='exceptionContent' type="text" class="text"  value="${inspectPlanItemList.exceptionContent}"/>
				<script>
						addBodyDomId('${inspectPlanItemList.id}'+'|'+'${inspectPlanItemList.inputType}');
						if(${inspectPlanItemList.exceptionFlag}+'' == '-1'){
							var hidenDom ='#r_div_'+${inspectPlanItemList.id};
							var exceptionDom ='#exceptionContent_'+${inspectPlanItemList.id};
							jq(hidenDom).hide();
							jq(exceptionDom).hide();
						}
					</script>
			</display:column>
		</c:if>
		<c:if test="${!isCheckUser}">
			<display:column title="巡检结果" >
				<input readonly="true" id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>
			</display:column>
		</c:if>
		<display:column title="保存日期" >
			${inspectPlanItemList.saveTime}
		</display:column>
		<display:column title="到期日期" >${inspectPlanItemList.endTime}
		</display:column>
			<display:column title="操作">
				<input type="button" class="btn" value="保存" id='saveBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="图片上传" style="width:60px;">
				<input type="button" value="上传" class="btn" onclick="uploadpicture(${inspectPlanItemList.id});" />
			</display:column>
			<display:column title="图片查看" style="width:60px;">
			 <!-- <a href="${app }/partner/inspect/inspectPlanExecute.do?method=gotoShowPicture&&id=${inspectPlanItemList.id}&idType=planItem" >
				<img src="${app}/images/icons/search.gif" />
			 </a> -->
			 <font id="checkPicture${inspectPlanItemList.id}">
				 <c:choose>
				 	<c:when test="${inspectPlanItemList.hasPicture eq 1}">
				 		<input type="button" value="查看"  onclick="pictureId('${inspectPlanItemList.id}');" class="btn"/>
				 	</c:when>
				 	<c:otherwise>
				 		无照片
				 	</c:otherwise>
				 </c:choose>
			 </font>
		</display:column>
		<display:column title="已上传照片数">
			<font id="picTrue${inspectPlanItemList.id}">${inspectPlanItemList.pictureUploadNum}</font>
		</display:column>
		<display:column title="需上传照片数">
			${inspectPlanItemList.pictureNum}
		</display:column>

		<display:column title="录音">
			 <c:choose>
			 	<c:when test="${inspectPlanItemList.hasAudio eq 1}">
			 	<!--
			 		<embed height=25 src=${app }/aa.wma type=audio/x-pn-realaudio-plugin width=50 autostart="false" controls="PlayButton" onclick="javascript:alert(0)">
			 		 -->
			 		 <input type="button" class="btn" id="playBtn_${inspectPlanItemList.id}" value="显示播放器"  onclick ="showSound(${inspectPlanItemList.id})"/>
			 		 <div id="hidsoundControl_${inspectPlanItemList.id}" style="display: none;">
			 		</div>
			 	</c:when>
			 	<c:otherwise>
			 		无录音
			 	</c:otherwise>
			 </c:choose>
		</display:column>
	</display:table>
</logic:present>

<br/>

<input type="button" onclick="backList('${planId}');" class="btn" value="返回"/>
<c:if test="${isCheckUser}">
<input type="button" onclick="saveAllResult()" class="btn" value="批量保存"/>
</c:if>
	</br>
	</div>
</div>
<div id="shoupicture" style="background: gray; height:480px;width: 600px; position: absolute;z-index: 15;">
	<table style="width: 100%;">
		<tr>
			<td id="photoType" style="width: 15%;text-align: center;color: white;"></td>
			<td style="width: 70%;text-align: center"><input type="button" class="btn" value="上一张" id="up" />
				<input type="button" class="btn" value="下一张" id="down" /></td>
			<td style="text-align: center;">
				<input type="button" value="关闭" id="close" class="btn" >
			</td>
		</tr>
	</table>


	<div id="showImg" align="center" >
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
