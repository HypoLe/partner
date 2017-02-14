<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page import="com.boco.eoms.base.util.StaticMethod"%>
<%
 request.setAttribute("currentDate",StaticMethod.getCurrentDateTime());
%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/extjs3/ext-base.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/extjs3/ext-all.js"></script>
<style>
.input{
	color: white;
	font-size: 17;
	background: black;
}
.select{
	color: white;
	font-size: 16;
	background: black;
	width: 60;
}
.submit{
	color: white;
	font-size: 16;
	background: black;
	width: 80;
}
.button{
	color: white;
	font-size: 16;
	background: black;
	width: 80;
}
.body{
	color: white;
	background: black;
	width: 100%,
	height:200%
}
.table{
	color: white;
	background: black;
	width: 100%,
	height:200%
}
</style>
<script type="text/javascript">
//http://192.168.0.75:8080/eoms/android/androidAction.do?method=validation&type=android&j_username=admin&j_password=loginsuccess&methodType=4&planResId=1&pageIndex=1
			var jq=jQuery.noConflict();
			
			function openInput(handler,id) {
				var el = jq('#'+id);
				if (el.is(":hidden")) {
					el.show();
				} else {
					el.hide();
				}
			}
			
			
			
			var resultSize;
			//jq(function(){
				//resultSize = '${resultSize}';
				//partner.setResultSize(resultSize);
			//});
			
			var allBodyDomId="";
			var exceptionFlag = '0'
			function addBodyDomId(domId){
				allBodyDomId = allBodyDomId+domId+";";
			}
			function saveAllResult(){
						var returnJson = "";
						if(null != allBodyDomId && '' != allBodyDomId){
						//alert(allBodyDomId);
						var tempBodyDomId = allBodyDomId.trim().split(";");
						if(tempBodyDomId.length !=0){
							for(var d_i = 0;d_i<tempBodyDomId.length-1;d_i++){
								//document.write(d_i+"");
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
							//partner.showMsg(returnJson);
							//if('' != returnJson){
								partner.saveAllResult(returnJson);
							//}
						}else{
							partner.showMsg("无数据");
						}
					}
			}
			//检查巡检结果并返回巡检结果
			function checkResultAndReturnValue(inputId,inputType){
				if(inputType == "")return;
				var val = '';
				if(inputType == 'text'){
					var domId2='#input_'+inputId;
						val = jq(domId2).val();
						if(val==''&&'1'==hasDevice+''){
							partner.showMsg("请填写巡检结果");
							return;
						}
				}
				if(inputType == 'number'){
					var domId2='#input_'+inputId;
					val = jq(domId2).val();
					if(val==''&&'1'==hasDevice+''){
						partner.showMsg("请填写巡检结果");
						return;
					}
					var len = val.length;  
					for(var i=0;i<len;i++) {  
			            c=val.charAt(i).charCodeAt(0);  
			            if(c<48 || c>57) {  
			            	partner.showMsg("填写内容必须为数字");
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
						partner.showMsg("请选择单选值");
						return;
					}
				}
				if(inputType == 'multiple'){
					var domId = '\"[name=multiple_'+inputId+'][checked]\"';
			   		jq(domId).each(function(){
			   	 			 val+=jq(this).val()+"|";
			   		 });
			   		 if(('' == val|| null == val)&&'1'==hasDevice+''){
						partner.showMsg("请选择多选值");
						return;
					}
				}
				return val;
			}
				
			function saveResult(inputId,inputType){
					exceptionFlag = 0;
					var hasDevice = jq("#hasDevice_"+inputId).val();
					if('1'==hasDevice+''){
						exceptionFlag = '-1';
					}
					var val = checkResultAndReturnValue(inputId,inputType);
				
					var exceptionDomId='#exceptionContent_'+inputId;
					partner.saveResult("{'inputId':'"+inputId+"','value':'"+val+"','exceptionContent':'"+jq(exceptionDomId).val()+"','exceptionFlag':'"+exceptionFlag+"'}");
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
							var start="<select style='width:149px' id=radio_"+id+">";
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
						partner.showMsg("操作失败");
					} 
				});
				}else{
					
				}
			}
			var isAllExpand  = false;
			function expandAllDom(){
				var tempBodyDomId = allBodyDomId.trim().split(";");
				if(tempBodyDomId.length !=0 && !isAllExpand){
					for(var d_i = 0;d_i<tempBodyDomId.length-1;d_i++){
						//document.write(d_i+"");
						var inputIdTemp = tempBodyDomId[d_i].split("|")[0];
						var hidenDom ='#tr_'+inputIdTemp;
						jq(hidenDom).show();
						//alert(jq(hidenDom)+"        "+jq(exceptionDom));
					}
						isAllExpand = true;
						jq("#expand").html("全部关闭");
				}else{
					for(var d_i = 0;d_i<tempBodyDomId.length-1;d_i++){
						//document.write(d_i+"");
						var inputIdTemp = tempBodyDomId[d_i].split("|")[0];
						var hidenDom ='#tr_'+inputIdTemp;
						jq(hidenDom).hide();
					}
					isAllExpand = false;
					jq("#expand").html("全部展开");
				}
			}
		
			function hasDevice(selDom,id){
				var hidenDom ='#r_div_'+id;
				var exceptionDom ='#exceptionContentdiv_'+id;
				if(selDom.value+"" == '1'){
					jq(hidenDom).hide();
					jq(exceptionDom).hide();
				}else{
					jq(hidenDom).show();
					jq(exceptionDom).show();
				}
			}
			function hasDevice2(value,id){
				var hidenDom ='#r_div_'+id;
				var exceptionDom ='#exceptionContentdiv_'+id;
				if(value+"" == '-1'){
					jq(hidenDom).hide();
					jq(exceptionDom).hide();
				}else{
					jq(hidenDom).show();
					jq(exceptionDom).show();
				}
			}
			
			function getVal(id){
				var msg = '';
				
				
				var domId2='#input_'+id;
				msg+=jq(domId2).val();
				msg+=""   +id;
				partner.showMsg(msg);
			}
			function pageMethod(type,msg){
				//单个巡检项
				if('1'==type){
					jq('#saveBtn_'+msg).val("已提交")
				}
			}
			//拍照
			function takePhoto(itemId){
				partner.takePhoto(itemId);
			}
			//巡检项拍照
			function takePhotoItem(itemId){
				partner.takePhotoItem(itemId);
			}
</script>

<div class="body">
<table   align="center" id="table" class="table">
	<tr><td colspan="6" align="center" style="color: white;font: oblique;font-size: 23;background: black" onclick="expandAllDom()">&emsp;作业填写&emsp;&emsp;共(${fn:length(inspectPlanItemList)})条&emsp;&emsp;<span id="expand">全部展开<span><br>
	</tr>
	<tr><td><hr style="border-color: blue"></td></td></tr>
  <c:forEach var="inspectPlanItemList" items="${inspectPlanItemList}" varStatus= "list"> 
	<tr>
	<td align="left" style="background: black;color: white;" name="inspectContent" id="inspectContent" onclick="openInput(this,'tr_${inspectPlanItemList.id}')">
	${list.index+1},&nbsp;巡检内容:${inspectPlanItemList.inspectItem }<br>&emsp;巡检规范:${inspectPlanItemList.content }
	</td>
	</tr>
	<tr style="display: none;" id="tr_${inspectPlanItemList.id}">
		<td>
		<div class="input">是否有设备:
			<c:choose>
					<c:when test="${inspectPlanItemList.exceptionFlag eq '-1'}">
						<select class="select" id="hasDevice_${inspectPlanItemList.id}" onchange="hasDevice(this,'${inspectPlanItemList.id}')">
							  <option value ="0">有</option>
							  <option value ="1" selected="selected">无</option>
						</select>
					</c:when>
					<c:otherwise>
						<select class="select" id="hasDevice_${inspectPlanItemList.id}" onchange="hasDevice(this,'${inspectPlanItemList.id}')">
							  <option value ="0">有</option>
							  <option value ="1">无</option>
						</select>
					</c:otherwise>
				</c:choose>
				<br>
				参&nbsp;考&nbsp;值:
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
				<br>
				
				
				
				<c:choose>
				<c:when test="${inspectPlanItemList.inputType eq 'text'}">
						<div id='r_div_${inspectPlanItemList.id}'>结&emsp;&emsp;果:
						<c:if test="${(inspectPlanItemList.saveTime  eq ''|| empty inspectPlanItemList.saveTime)&& currentDate<inspectPlanItemList.endTime}">
								<input id='input_${inspectPlanItemList.id}' type="text" style="width: 80dip"  class="input" value="${inspectPlanItemList.itemResult}"/>
						</c:if>
						<c:if test="${currentDate>inspectPlanItemList.endTime}">
							<span>已结束</span>
						</c:if>
						<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
										<input id='input_${inspectPlanItemList.id}' type="text" style="width: 80dip"  class="input" value="${inspectPlanItemList.itemResult}"/>
								</c:when>
							</c:choose>	
						</c:if>
						</div>
				</c:when>
				
				<c:when test="${inspectPlanItemList.inputType eq 'number'}">
						<div id='r_div_${inspectPlanItemList.id}'>结&emsp;&emsp;果:
							<c:if test="${(inspectPlanItemList.saveTime  eq ''|| empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
									<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>
							</c:if>
							<c:if test="${currentDate>inspectPlanItemList.endTime}">
								<span>已结束<%--:<input readonly="readonly"  id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>--%></span>
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
						<div id='r_div_${inspectPlanItemList.id}'>结&emsp;&emsp;果:
							<c:if test="${(inspectPlanItemList.saveTime  eq '' || empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
								<span id="span_${inspectPlanItemList.id}">
								<script type="text/javascript">
								getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','false')
								</script>
								</span>
							</c:if>
							
							<c:if test="${currentDate>inspectPlanItemList.endTime}">
								<span>已结束<%--:<input readonly="readonly" id='input_${inspectPlanItemList.id}' type="text" class="text"  value="<eoms:id2nameDB beanId='ItawSystemDictTypeDao' id='${inspectPlanItemList.itemResult}' />"/>--%></span>
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
					<div id='r_div_${inspectPlanItemList.id}'>结&emsp;&emsp;果:
						<c:if test="${(inspectPlanItemList.saveTime  eq '' || empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
								<span id="span_${inspectPlanItemList.id}">
								<script type="text/javascript">
								getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','false')
								</script>
								</span>
						</c:if>
						<c:if test="${currentDate>inspectPlanItemList.endTime}">
							已结束<%--
									<span id="span_${inspectPlanItemList.id}" >
										<script type="text/javascript">
										getValues('${inspectPlanItemList.dictId}','${inspectPlanItemList.inputType}','${inspectPlanItemList.id}','${inspectPlanItemList.itemResult}','true')
										</script>
									</span>
									--%>
						</c:if>
						
						<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
								<div id='r_div_'${inspectPlanItemList.id}'>
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
				<script>
						addBodyDomId('${inspectPlanItemList.id}'+'|'+'${inspectPlanItemList.inputType}');
						if(${inspectPlanItemList.exceptionFlag}+'' == '1'){
							var hidenDom ='#r_div_'+${inspectPlanItemList.id};
								jq(hidenDom).hide();
						}
					</script>
				
				<br>
				<div class="input" id="exceptionContentdiv_${inspectPlanItemList.id}">异常情况:
				<input id='exceptionContent_${inspectPlanItemList.id}' name='exceptionContent' type="text" class="input"  value="${inspectPlanItemList.exceptionContent}"/>
				</div>
				
				
				<!-- 结果填写和生成 -->
				<br>
				<c:choose>
				<c:when test="${inspectPlanItemList.inputType eq 'text'}">
					<div align="right">
						<c:if test="${(inspectPlanItemList.saveTime  eq ''|| empty inspectPlanItemList.saveTime)&& currentDate<inspectPlanItemList.endTime}">
						<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
								<input type="button" id='saveBtn_${inspectPlanItemList.id}' class="button" value="提交" onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
						</c:if>
						<c:if test="${currentDate>inspectPlanItemList.endTime}">
							<span>已结束<!--:<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>---></span>
						</c:if>
						<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
								<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
										<input type="button" id='modifyBtn_${inspectPlanItemList.id}' class="button" value="提交" onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
								</c:when>
							</c:choose>	
						</c:if>
						</div>
				</c:when>
				
				<c:when test="${inspectPlanItemList.inputType eq 'number'}">
						<div id='r_div_${inspectPlanItemList.id}'>
							<c:if test="${(inspectPlanItemList.saveTime  eq ''|| empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
							<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
									<input type="button" value="提交" class="button" id='saveBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
							</c:if>
							<c:if test="${currentDate>inspectPlanItemList.endTime}">
								<span>已结束:<!--  <input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="${inspectPlanItemList.itemResult}"/>--></span>
							</c:if>
							<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
								<c:choose>
									<c:when test="${currentDate<inspectPlanItemList.endTime }">
									<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
										<input type="button" id='modifyBtn_${inspectPlanItemList.id}' class="button" value="提交" onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
									</c:when>
								</c:choose>	
							</c:if>
						</div>
				</c:when>
				<c:when test="${inspectPlanItemList.inputType eq 'radio'}">
							<c:if test="${(inspectPlanItemList.saveTime  eq '' || empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
							<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
								<input type="button" value="提交" id='saveBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
							</c:if>
							
							<c:if test="${currentDate>inspectPlanItemList.endTime}">
								<span>已结束:<%--<input id='input_${inspectPlanItemList.id}' type="text" class="text"  value="<eoms:id2nameDB beanId='ItawSystemDictTypeDao' id='${inspectPlanItemList.itemResult}' />"/>---%></span>
							</c:if>
							
							
							<c:if test="${inspectPlanItemList.saveTime ne '' && !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
								<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
								<input type="button" value="提交" class="button" id='modifyBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
								</c:when>
							</c:choose>	
							</c:if>
				</c:when>
				<c:when test="${inspectPlanItemList.inputType eq 'multiple'}">
						<c:if test="${(inspectPlanItemList.saveTime  eq '' || empty inspectPlanItemList.saveTime)&&currentDate<inspectPlanItemList.endTime}">
								<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
								<input type="button" value="提交" class="button" id='saveBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
						</c:if>
						<c:if test="${currentDate>inspectPlanItemList.endTime}">
							已结束
						</c:if>
						
						<c:if test="${inspectPlanItemList.saveTime ne ''&& !empty inspectPlanItemList.saveTime}">
							<c:choose>
								<c:when test="${currentDate<inspectPlanItemList.endTime }">
									<input type="button" value="拍照" class="button" id='takePhoto_${inspectPlanItemList.id}' onclick="takePhotoItem('${inspectPlanItemList.id}')"/>
									<input type="button" value="提交" class="button" id='modifyBtn_${inspectPlanItemList.id}' onclick="saveResult('${inspectPlanItemList.id}','${inspectPlanItemList.inputType}')"/>
								</c:when>
							</c:choose>
						</c:if>
				</c:when>
				</c:choose>
				<script type="text/javascript">
				hasDevice2('${inspectPlanItemList.exceptionFlag}','${inspectPlanItemList.id}');
				</script>
			</div>
		</td>
</tr>
<tr><td><hr></td></tr>
</c:forEach> 
</table>
<div align="center" style="margin-left: 30;margin-top: 20">
</div>
	</br>
	</div>
</div>
</div>
