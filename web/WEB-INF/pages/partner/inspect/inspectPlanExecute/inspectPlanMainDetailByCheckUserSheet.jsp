<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

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
			jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
			Ext.Ajax.request({
				url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+pid+"&curPage="+curPage+"&idType=inspectPlan>",
				success: function(x){
					var jsonResult = Ext.util.JSON.decode(x.responseText); 
					var photoType = jsonResult[0].msg;
					alert(photoType);
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
				jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
		jq("#shoupicture").css("left",width/2-400+"px")
							.css("top",height/2-250+"px")
		jq("#pifilter").show();
		jq("#shoupicture").show();
		//下一步操作时把这张图片显示出来
		jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage='>");
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







function goToStartCheck(id,exe){
window.location="${app}/partner/inspect/inspectPlanExecute.do?method=goToInspectPlanMainItemList&planResId="+id+"&detail="+exe;
}

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

</script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>
<%--
<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
--%>

	
<table id="stylesheet" class="formTable">

	<content tag="heading">
	</content>
		<tr>
			<td class="label">巡检计划名称</td>
			<td class="content">
			${planMain.planName}
			</td>
			<td class="label">巡检专业</td>
			<td><eoms:id2nameDB id="${planMain.specialty}" beanId="ItawSystemDictTypeDao" /></td>
		</tr>

		<tr>
			<td class="label">代维公司</td>
			<td>
			<eoms:id2nameDB id="${planMain.partnerDeptId}" beanId="partnerDeptDao"/>
			
			</td>
			<td class="label">关联资源数</td>
			<td class="content">${planMain.resNum }</td>

		</tr>
		<tr>
			<td class="label">巡检日期</td>
			<td class="content">
			<c:if test="${!empty planMain.year}" var="result">
				${planMain.year}年${planMain.month}月
			</c:if>
			</td>
			<td class="label">制定人</td>
			<td class="content"><eoms:id2nameDB id="${planMain.creator }" beanId="tawSystemUserDao" /></td>
		</tr>
		<tr>
			<td class="label">制定日期</td>
			<td class="content">
				<fmt:formatDate value="${planMain.createTime }" type="date"/>
			</td>
			<td class=""></td>
			<td class="content"></td>
		</tr>
		<tr>
			<td class="label">描述</td>
			<td class="content" colspan="3">${planMain.content}</td>
		</tr>
	</table>
<%---
<div id="history-info" class="tabContent">
 --%>	
<br/>
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
	 
	<form action="${app }/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetailSheet" method="post">
	   <input type="hidden" value="${planMain.id}" name="id">
		<table id="sheet" class="listTable">
			<tr>		
				<td class="label">巡检任务名称</td>
				<td><input class="text" type="text" name="resName"/><input type="hidden" name="finsh" value="${finsh }"></td>
				<td class="label">巡检小组</td>
				<td class="content">
					<input type="text"  class="text"  name="executeObject" id="executeObject"  
					value="" 
					alt="allowBlank:true" readonly="readonly"/>
				 <input name="chgExecuteObject" id="partnerDeptId"  value="" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&checktype=excludeBigNodAndLeaf&showPartnerLevelType=4"
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="executeObject" 
						textField="executeObject" checktype="dept" single="true" />
				</td>
			</tr>
			<tr>	
				<td class="label">周期</td>	
				<td>
				<select name="inspectCycle" class="select">
					<option value="">请选择</option>
					<c:forEach items="${cycleMap}" var="cycleMap" > 
						<option value="${cycleMap.key}">${cycleMap.value}</option>
					</c:forEach> 
				</select>
				</td>
				<td class="label"></td>
				<td>
					
				</td>
			</tr>
	</table>
		<input type="submit" class="btn" value="查询"/>
	</form>
	</div>
 
<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list"
		export="false" 
		requestURI="inspectPlanExecute.do?method=getInspectPlanMainDetail"
		sort="list" partialList="true" size="${resultSize}" 
		decorator="com.boco.eoms.partner.inspect.util.InspectPlanMainDetailByCheckUserDecorator"
	>
	<center>
		<display:column title="巡检任务名称" >
			${list.resName}
		</display:column>
		<display:column title="巡检专业">
			<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源类型">
			<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column title="资源级别">
			<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />	
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="地市">
			<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>			
		<display:column  sortable="true" headerClass="sortable" title="区县">
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		
		<display:column  title="代维小组" >
		<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		
		<display:column  title="巡检开始时间" >
		<fmt:formatDate value="${list.planStartTime}" type="date" dateStyle="medium"/>
		</display:column>
		
		<display:column title="巡检结束时间" >
		<fmt:formatDate value="${list.planEndTime}" type="date"/>
		
		</display:column>
		<display:column title="签到时间" >
		<c:choose>
			<c:when test="${list.signTime != null}"><fmt:formatDate value="${list.signTime}" type="date"/></c:when>
			<c:otherwise>无</c:otherwise>
		</c:choose>
		</display:column>
		
		<display:column title="变更状态">
		<c:choose>
		<c:when test="${list.changeState eq '1'}">
		变更中...
		</c:when>
		<c:when test="${list.changeState eq '0'}">
		无
		</c:when>
		
		</c:choose>
		
		<display:column title="是否突发资源" >
			<c:choose>
				<c:when test="${list.burstFlag eq 1 }"><font color="red">是</font></c:when>
				<c:otherwise>
				否
				</c:otherwise>
			</c:choose>
		</display:column>
		
		<display:column title="填写情况">
			<c:choose>
				<c:when test="${list.inspectState eq 1}">
					<c:choose>
						<c:when test="${list.signStatus eq -1 }">
							通过PC填写
						</c:when>
						<c:when test="${list.signStatus eq 1 or list.signStatus eq 3 }">
							手机强制到站填写
						</c:when>
						<c:when test="${list.signStatus eq 0 }">
							手机正常到站填写
						</c:when>
					</c:choose>
				</c:when>
				<c:otherwise>
					未完成
				</c:otherwise>
			</c:choose>
			
			
		</display:column>	
		
			
		<display:column title="完成情况">
			<c:choose>
				<c:when test="${list.inspectState eq 1}">
					已完成
				</c:when>
				<c:otherwise>
					未完成
				</c:otherwise>
			</c:choose>
		</display:column>	
			
		</display:column>
		
		<c:if test="${finsh eq 'no'}">
		<display:column sortable="false" headerClass="sortable" title="图片上传">
			<c:if test="${sessionform.deptid eq list.executeDept }">
				<a href="${app }/partner/inspect/inspectPlanExecute.do?method=gotoInspectPlanMainUploadPicture&id=${list.id}&typeId=res" >上传</a>
			</c:if>
		</display:column>
		</c:if>
		
		<display:column title="图片查看">
			<!--  <a href="${app }/partner/inspect/inspectPlanExecute.do?method=gotoShowPicture&&id=${list.id}&idType=res&resCfgId=${list.resCfgId}" >
				<img src="${app}/images/icons/search.gif" />
			 </a> -->
			 <c:choose>
			 	<c:when test="${list.hasPicture eq 1}">
			 		<input type="button" value="查看"  onclick="pictureId('${list.id }');" class="btn"/>
			 	</c:when>
			 	<c:otherwise>
			 		无照片
			 	</c:otherwise>
			 </c:choose>
			 
		</display:column>
		<c:if test="${finsh eq 'no'}">
		<display:column  sortable="true" headerClass="sortable" title="操作" property="operation">
		</display:column>
		</c:if>
		<c:if test="${finsh eq 'yes'}">
		<display:column sortable="true" headerClass="sortable" title="操作">
		 <a href="javascript:goToStartCheck('${list.id }','detail')"> <img src="${app}/images/icons/search.gif" /></a>
		</display:column >
		
		</c:if>
	</display:table>
	<%--
  </div>
--%>	
  
  <div id="comments-info" class="tabContent">	 
  </div>
<div id="shoupicture" style="background: gray;height:480px;width: 600px;position: absolute;z-index: 15;">
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

</form>
<input type="button" class="btn" value="返回" 
						                    onclick="javascript:history.back();" /> 
<%@ include file="/common/footer_eoms.jsp"%>