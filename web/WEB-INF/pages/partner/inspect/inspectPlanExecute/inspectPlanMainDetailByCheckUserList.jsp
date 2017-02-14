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


Ext.onReady(function(){
  		// 初始的时候给区县默认值
		delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
					obj.options[j]=opt;
					var country = "${country}";
					if(arrOptions[i]==country){
					obj.options[j].selected=true;
					}
					j++;
					i++;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
		
  });
  
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
//			Ext.get(document.body).mask('请稍等......');
			//jq("#showImg").html("<img width='600' height='480'  src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
			jq("#showImg").html("<img width='600' height='480'  src='${app }/partner/inspect/inspectPlanExecute.do?method=showPictureByPath&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
				//jq("#showImg").html("<img width='600' height='480'  src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
				jq("#showImg").html("<img width='600' height='480'  src='${app }/partner/inspect/inspectPlanExecute.do?method=showPictureByPath&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
							.css("top",height/2-300+"px")
		jq("#pifilter").show();
		jq("#shoupicture").show();
		//下一步操作时把这张图片显示出来
		jq("#showImg").html("<img width='600' height='480'  src='${app }/partner/inspect/inspectPlanExecute.do?method=showPictureByPath&id="+id+"&curPage='>");
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
window.location="${app}/partner/inspect/inspectPlanExecute.do?method=goToInspectPlanMainItemLists&planResId="+id+"&detail="+exe;
}

function goToDeviceInspect(id,exe){
window.location="${app}/partner/inspect/inspectPlanExecute.do?method=goToDeviceInspectList&planResId="+id+"&detail="+exe;
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
	
function gotoPlanList(){
window.location="${app}/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetailList";
}


function uploadpicture(typeid){
     	var _sHeight = 480;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	    var url ="${app }/partner/inspect/inspectPlanExecute.do?method=gotoInspectPlanMainUploadPicture&id="+typeid+"&typeId=res";
	    var pro =  window.showModalDialog(url,window,sFeatures);
	}
	
function updatePictureNum(id,picturnNum){
	jq("#checkPicture"+id).html("<input type='button' value='查看'  onclick='pictureId("+id+")' class='btn'/>");
};	
/**
 * 跳转审核页面
 */

function gotoAuditState(id){

        var _sHeight = 380;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";

        var url ="${app}/partner/inspect/inspectPlanExecute.do?method=gotoUpdateAuditStatePage";
//        var obj = new Object();
//        obj.exception=exception;
        var pro =  window.showModalDialog(url,window,sFeatures);
        if(pro==null){
            return;
        }

        Ext.Ajax.request({
            method:"post",
            url: "${app}/partner/inspect/inspectPlanExecute.do?method=updateAuditStatePage",
            params:{
                id:id,
                remand:pro[0],
                remandTime:pro[1]
            },
            success: function(response, options){
                if(response.responseText=='true'){
                    //刷新父页面
                    alert("审核成功！");
                    window.location.reload();
                }

            }
        })

    }
		// 地区、区县连动
function changeCity(con)
		{    
		    delAllOption("country");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
						obj.options[j]=opt;
						j++;
						i++;
					}
										
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}	
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
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
<c:if test="${transLine eq 'yes'}" var="result_1" >
</c:if>
	

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
	 
	<form action="${app }/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetailList" method="post">
	   <input type="hidden" value="${planMain.id}" name="id">
	   <table class="listTable">
			<tr>
					<td class="label">资源名称</td>
					<td class="content">
						<html:text property="resName" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					<td class="label">巡检专业</td>
					<td class="content">
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
								<eoms:comboBox name="specialty" id="zhuanye" styleClass="select"
									sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225"
									alt="allowBlank:false"  hiddenOption="1122502"/>
							</c:if>
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
								<eoms:comboBox name="specialty" id="zhuanye" styleClass="select"
									sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225"
									alt="allowBlank:false"  />
							</c:if>
					</td>
				</tr>

				<tr>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resType" defaultValue="${pnrResConfigForm.resType}"
							id="resourceTeype" initDicId="${pnrResConfigForm.specialty}" alt="allowBlank:false"
							sub="resourceLevel" styleClass="select" />
					</td>
					<td class="label">资源级别</td>
					<td class="content">
						<eoms:comboBox name="resLevel" defaultValue="${pnrResConfigForm.resLevel}"
							id="resourceLevel" initDicId="${pnrResConfigForm.resType}" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
				 <tr>
					<td class="label">地市</td>
					<td class="content">
						<select name="region"  id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${city.areaid ==region}">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</logic:iterate>
						</select>
					</td>
					<td class="label">区县</td>
					<td class="content">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				<tr>	
					
					<td class="label">完成情况</td>
					<td>
						<select name="inspectState" class="select">
							<option value="">全部</option>
							<option value="1">已完成</option>
							<option value="0">未完成</option>
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
			export="true" 
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
			
			<display:column  title="巡检小组" >
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
			
						
			<display:column title="完成情况">
				<c:choose>
					<c:when test="${list.inspectState eq 1}">
						已完成
					</c:when>
					<c:when test="${list.inspectState eq 3}">
						超时未完成
					</c:when>
					<c:otherwise>
						未完成
					</c:otherwise>
				</c:choose>
			</display:column>	
			
			
			<display:column title="图片查看">
				<!--  <a href="${app }/partner/inspect/inspectPlanExecute.do?method=gotoShowPicture&&id=${list.id}&idType=res&resCfgId=${list.resCfgId}" >
					<img src="${app}/images/icons/search.gif" />
				 </a> -->
				 <font id="checkPicture${list.id}">
					 <c:choose>
					 	<c:when test="${list.hasPicture eq 1}">
					 		<input type="button" value="查看"  onclick="pictureId('${list.id }');" class="btn"/>
					 	</c:when>
					 	<c:otherwise>
					 		无照片
					 	</c:otherwise>
					 </c:choose>
				 </font>
			</display:column>
			
			<%--2015-1-4 将操作隐藏，使用户不能在web端进行巡检
			<display:column  sortable="true" headerClass="sortable" title="巡检结果查看" property="operation">
			</display:column>
			
			--%>
		    <display:column  sortable="true" headerClass="sortable" title="巡检结果查看" >
		    <a href="javascript:goToStartCheck('${list.id}','detail')"> <img src="${app}/images/icons/search.gif"/></a>
			
			</display:column>
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
	
	<%--
  </div>
--%>	
  
  <div id="comments-info" class="tabContent">	 
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
	
	
	<div id="showImg" align="center">
	</div>
</div> 

</form>
<%@ include file="/common/footer_eoms.jsp"%>