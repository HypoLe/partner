<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
var pid;         //计划id
var curPage=1;   //当前是第几页
var total=0;     //总共有多少张照片
jq(function(){
	jq("#pifilter").hide();
	jq("#shoupicture").hide();
	var items = jq(".uploadPicture");
	var deptid = ${sessionform.deptid}
	for(var i=0;i<items.length;i++){
		var len = (new String(deptid)).length;
		if(len>7){
			len = len.substring(0,7);
		}
		
	}
	
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
			Ext.get(document.body).mask('请稍等......');
			jq("#showImg").html("<img width='760' height='470' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
			Ext.get(document.body).mask('请稍等......');
				jq("#showImg").html("<img width='760' height='470' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
function setProgessToDiv(divId,total,hasDone){ 
var myDivId='#'+divId;
//alert("total   "+total+"  hasDone "+hasDone)
	if(0==total||0==hasDone){
		jq(myDivId).css("width","0");
		jq(myDivId).html("0%");
	}else{
		var persent = (hasDone/total)*100+"%";
		jq(myDivId).css("width",persent)
		jq(myDivId).html(parseInt(persent)+"%");
	}
}	

function pictureId(id){
//先获得屏幕的宽度和高度
		curPage = 1;    //先把当前页设置为0
		jq(this).parent()
		pid = id;
		var height = jq(window).height();
		var width = jq(window).width();
		jq("#pifilter").css("width",width+"px")
						.css("height",height+"px");
		jq("#shoupicture").css("left",width/2-400+"px")
							.css("top",height/2-250+"px")
		jq("#pifilter").show();
		jq("#shoupicture").show();
		//下一步操作时把这张图片显示出来
		jq("#showImg").html("<img width='760' height='460' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage='>");
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

function selectPlan(){
		var objName= document.getElementsByName("radio1");
		var string = '';
		 for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string = objName[i].value.trim();
                break;
                }
        } 
		window.returnValue=string;
		window.close();
	}
	
</script>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	
	<!-- 
	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content>
	 -->
	 
	<html:form action="inspectPlanExecute.do?method=findInspectPlanMainByCheckUserList" method="post">
		<input type="hidden" value="${carApprove}" name="carApprove" />
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">计划名称</td>
				<td><input class="text" type="text" name="planNameStringLike"/></td>
				<td class="label">巡检专业</td>
				<td>
					<eoms:comboBox name="specialtyStringEqual" id="specialty" styleClass="select"
					initDicId="11225" alt="allowBlank:false"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					年度
				</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="yearStringEqual" dictId="yearflag" 
					beanId="selectXML" cls="select" />
				</td>
				<td class="label">
					月份
				</td>
				<td>
					<eoms:dict key="dict-partner-inspect" selectId="monthStringEqual" dictId="monthflag" 
					beanId="selectXML" cls="select" />
				</td>
			</tr>
			<tr>		
				<td class="label">
					巡检单位
				</td>
				<td>
					<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
						value="" alt="allowBlank:false" readonly="readonly"/>
					<input name="partnerDeptIdStringEqual" id="partnerDeptId"  value="" type="hidden" />
					<eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />	
				</td>
				<td class="label"></td>
				<td></td>
			</tr>
	</table>
	<input type="hidden" value="month" name="currentMonth">
		<html:submit styleClass="btn" property="method.approvalList"
			styleId="method.approvalList" value="查询"></html:submit>
	</html:form>
	</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr><!--
计划完成进度   total:${size}  hashDone:<br>
实际完成进度   total:${size}  hashDone:<br>

 Information hints area end-->
<logic:present name="list" scope="request">
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" class="table list" export="false" sort="list" 
		pagesize="${pagesize}" requestURI="inspectPlanExecute.do?method=findInspectPlanMainByCheckUserList" 
		partialList="true" size="${size}">
		
		<c:if test="${carApprove eq 'yes'}">
			<display:column  title="" >
	        	<input type="radio" name="radio1" id="radio1"  value="${list.id}#${list.planName}"/>
	    	</display:column>
		</c:if>
		
		<display:column property="planName" title="巡检计划名称" />
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column  title="巡检单位" >
		<eoms:id2nameDB id="${list.partnerDeptId}" beanId="partnerDeptDao"/>
		</display:column>
		<display:column title="巡检日期" >
			<c:if test="${!empty list.year}" var="result">
				${list.year}年${list.month}月
			</c:if>
		</display:column>
		
		<display:column title="实际完成进度" >
			<div style="width: 100%;background: #D3E686;"><!-- <span   id="progess2_${list.id}" style=" display:block;background-color:#839C20;"> </span>-->
			
			
			<span   style=" display:block;background-color:#839C20;width:${list.hasDone*100/list.resNumber}% ">
				<fmt:formatNumber value="${list.hasDone*100/list.resNumber}" pattern="#.##"></fmt:formatNumber>% 
			</span>
			
			</div>
			<!-- <script>setProgessToDiv('progess2_${list.id}','${list.resNumber}','${list.hasDone}')</script> -->
			<%----
			resNumber:${list.resNumber},hashDone:${list.hashDone}
			--%>
		</display:column>
		<c:if test="${carApprove ne 'yes'}">
		<display:column sortable="false" headerClass="sortable" title="查看巡检任务"
			media="html">
			<a id="${list.id }"
				href="${app }/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetail&id=${list.id}"
				<%---target="blank" --%>shape="rect"> <img
				src="${app }/images/icons/table.gif"> </a>
		</display:column>
		</c:if>
	</display:table>
</logic:present>
	</br>
	<c:if test="${carApprove eq 'yes'}">
		<input type="button" class="btn" value="确定" onclick="selectPlan();"/>
	</c:if>
	</div>
</div>
<!-- 
<div id="pifilter" style="background:#262626;width: 1000px;height: 100px;position: absolute;z-index: 9;left: 0px;top: 0px;filter:Alpha(Opacity='5',FinishOpacity='75',Style='2') ; -moz-opacity:.1;    
   opacity:0.1;   "> 
<div id="shoupicture" style="background: gray;height:500px;width: 800px;position: absolute;z-index: 15;">
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
	
	
	<div id="showImg" >
	</div>
</div> 
-->
<%@ include file="/common/footer_eoms.jsp"%>
