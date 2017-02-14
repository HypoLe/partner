
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

<script type="text/javascript">
$(function(){
	$("#up").click(function(){
		var curPage = $("#curPage").val();
		var id= '${id }';
		var idType = '${idType}';
		if(curPage == '' || curPage-0<=1){
			alert('当前已经是第一张');
			return;
		}else{
			$("#curPage").val(curPage-0-1);
			curPage = curPage-1;   //表示该显示第几页
			Ext.get(document.body).mask('请稍等......');
			$("#showImg").html("<img width='760' height='470' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage="+curPage+"&idType="+idType+"'>");
			Ext.Ajax.request({
				url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+id+"&curPage="+curPage+"&idType="+idType+">",
				success: function(x){
					var jsonResult = Ext.util.JSON.decode(x.responseText); 
					var photoType = jsonResult[0].msg;
					if(photoType == '0'){
						$("#photoType").html('签退图片');
					} else if(photoType == '1'){
						$("#photoType").html('签到图片');
					} else if(photoType == '2'){
						$("#photoType").html('巡检图片');
					} else if(photoType == '3'){
						$("#photoType").html('服务器上传图片');
					} else{
						$("#photoType").html('');	
					}          
				}
			});
			setTimeout(function(){
					Ext.get(document.body).unmask();
			},5000);
		}
	});

	/*$("#up").click(function(){
		var curPage = '${curPage}';
		if(curPage == '' || curPage=='1'){
			alert('当前已经是第一张');
			return;
		}else{
			$("#curPage").val(curPage-0-1);
			$("form").submit();
		}
	});
	
	$("#down").click(function(){
		var curPage = '${curPage}';
		var total = '${total}';
		if(curPage==''){
			if(total-0>1){
				$("#curPage").val('2');
				if(curPage-0>=total){
				alert("当前已是最后一张");
				return;
			}
				$("form").submit();
			}
		}else{
			$("#curPage").val(curPage-0+1);
			var total = '${total}';
			if(curPage-0>=total){
				alert("当前已是最后一张");
				return;
			}else{
				$("form").submit();
			}
		}
	});*/
	
	$("#down").click(function(){
		var total = '${total}';
		var id= '${id }';
		var curPage = $("#curPage").val();
		var idType = '${idType}';
		if(curPage==''){
			if(total-0>1 || total-1==0){
				if(curPage-0>=total){
				alert("当前已是最后一张");
				return;
			}
			//如果当前不是最后一张，而且当前是第一张，那下一张是第二张
				Ext.get(document.body).mask('请稍等......');
				$("#showImg").html("<img width='760' height='470' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage="+2+"&idType="+idType+"'>");
				//得到该图片是什么类型的
				Ext.Ajax.request({
					url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+id+"&curPage="+2+"&idType="+idType+">",
					success: function(x){
					    var jsonResult = Ext.util.JSON.decode(x.responseText);              
						var photoType = jsonResult[0].msg;
						if(photoType == '0'){
							$("#photoType").html('签退图片');
						} else if(photoType == '1'){
							$("#photoType").html('签到图片');
						} else if(photoType == '2'){
							$("#photoType").html('巡检图片');
						} else if(photoType == '3'){
							$("#photoType").html('服务器上传图片');
						} else{
							$("#photoType").html('');	
						}          
					}
				});
				setTimeout(function(){
					Ext.get(document.body).unmask();
				},5000);
			$("#curPage").val(2);   //表示下次应该显示第三页
			
			}
		}else{
			curPage = curPage-0+1;
			if(curPage-0>total){
				alert("当前已是最后一张");
				return;
			}else{
				Ext.get(document.body).mask('请稍等......');
				$("#showImg").html("<img width='760' height='470' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage="+curPage+"&idType="+idType+"'>");
				Ext.Ajax.request({
					url:"${app }/partner/inspect/inspectPlanExecute.do?method=getTaskFilePhotoType&id="+id+"&curPage="+curPage+"&idType="+idType+">",
					success: function(x){
						var jsonResult = Ext.util.JSON.decode(x.responseText);              
						var photoType = jsonResult[0].msg;
					if(photoType == '0'){
						$("#photoType").html('签退图片');
						} else if(photoType == '1'){
							$("#photoType").html('签到图片');
						} else if(photoType == '2'){
							$("#photoType").html('巡检图片');
						} else if(photoType == '3'){
							$("#photoType").html('服务器上传图片');
						} else{
							$("#photoType").html('');	
						}          		
					}
				});
				setTimeout(function(){
					Ext.get(document.body).unmask();
				},5000);
			}
			$("#curPage").val(curPage);
		}
	});
	
})
</script>

<form action="${app }/partner/inspect/inspectPlanExecute.do?method=gotoShowPicture" method="post" id="form">
	<input type="hidden" name="id" value="${id }" />
	<input type="hidden" name="curPage" value="${curPage}" id="curPage" />
	<input type="hidden" name="idType" value="${idType}" />
	<c:if test="${idType eq 'res'}">
		<input type="hidden" name="resCfgId" value="${resCfgId}" />
		<table class="formTable" id="sheet">
			<caption>
				<div class="header center">巡检资源信息</div>
			</caption>
			<tr>
				<td class="label" >巡检任务名称</td>
				<td class="content">
						${pnrResConfig.resName}
				</td>
				<td class="label">巡检专业</td>
				<td class="content">
		        	<eoms:id2nameDB id="${pnrResConfig.specialty}" beanId="ItawSystemDictTypeDao" />		
		        	<input type="hidden" id="zhuanye" value="${pnrResConfig.specialty}">
				</td>
			</tr>
			<tr>
				<td class="label">资源类别</td>
				<td class="content">
					<eoms:id2nameDB id="${pnrResConfig.resType}" beanId="ItawSystemDictTypeDao" />
				</td>
				<td class="label">资源级别</td>
				<td class="content" >
						<eoms:id2nameDB id="${pnrResConfig.resLevel}" beanId="ItawSystemDictTypeDao" />
				</td>
			</tr>
			<tr>
				<td class="label">资源经度</td>
				<td class="content">
					${pnrResConfig.resLongitude}
				</td>
				<td class="label">资源纬度</td>
				<td class="content">
					${pnrResConfig.resLatitude}
				</td>
			</tr>
			<tr>
				<td class="label">地市</td>
				<td class="content">
				
				
				<eoms:id2nameDB id="${pnrResConfig.city}" beanId="tawSystemAreaDao" />
				
				</td>
				<td class="label">区县</td>
				<td class="content">
		
				<eoms:id2nameDB id="${pnrResConfig.country}" beanId="tawSystemAreaDao" />
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${total == 0}">
	无照片
	</br></br>
	<input type="button" class="btn" value="返回" 
						                    onclick="javascript:history.back();" />
	</c:if>
	<c:if test="${total > 0}">
		<table  class="formTable" align="left">
			<tr>
				<td class="conten">
					<div id="showImg" >
						<img width="760" height="470" src="${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id=${id }&curPage=${curPage}&idType=${idType}">
					</div>
				</td>
				<td>
					<input type="button" class="btn" value="上一张" id="up" />
					<input type="button" class="btn" value="下一张" id="down" />
				</td>
			</tr>
			<tr>
				<td colspan="1">
					<input type="button" class="btn" value="返回" 
						                    onclick="javascript:history.back();" />
				</td>
				<td id="photoType">
					<c:if test="${photoType eq '0' }">
					签退图片
					</c:if>
					<c:if test="${photoType eq '1' }">
					签到图片
					</c:if>
					<c:if test="${photoType eq '2' }">
					巡检图片
					</c:if>
					<c:if test="${photoType eq '3' }">
					服务器上传图片
					</c:if>
				</td>
			</tr>
		</table>
	
	</c:if>
	
	
</form>
<!-- 
<div style="background: #F0F4F5;width: 1000px;height: 600px;position: absolute;z-index: 123;left: 0px;top: 0px;filter:Alpha(Opacity='5',FinishOpacity='75',Style='2') ">
	 
</div> 
<div style="background: #F0F4F5;width: 1000px;height: 600px;position: absolute;z-index: 123;left: 0px;top: 0px;filter:Alpha(Opacity='5',FinishOpacity='75',Style='2') ; -moz-opacity:.1;    
   opacity:0.1;   ">-->
	 
</div>
<%@ include file="/common/footer_eoms.jsp"%>
