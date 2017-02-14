
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
			//Ext.get(document.body).mask('请稍等......');
			//jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
			jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPictureByPath&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
			//Ext.get(document.body).mask('请稍等......');
				//jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
				jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPictureByPath&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
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
		jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPictureByPath&id="+id+"&curPage='>");
		//jq("#showImg").html("<img width='600' height='480' src='${app }/partner/inspect/inspectPlanExecute.do?method=showPicture&id="+id+"&curPage='>");
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

function backList(id){
window.location="${app }/partner/inspect/inspectPlanExecute.do?method=getInspectPlanMainDetailList&id="+id
}




var checkflag = "false";
function chooseAll(){
    var objs = document.getElementsByName("checkbox11");
    if(checkflag=="false"){
        for(var i=0; i<objs.length; i++){
            objs[i].checked="checked";
        }
        checkflag="checked";
    }
    else if(checkflag=="checked"){
        for(var i=0; i<objs.length; i++){
            objs[i].checked=false;
        }
        checkflag="false";
    }
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
	
		<display:column title="巡检任务名称">
			${InspectPlanRes.resName }
		</display:column>
		<display:column title="巡检项目" >
		 ${inspectPlanItemList.inspectItem }	
		</display:column>
		<display:column title="巡检内容">
		${inspectPlanItemList.content }
		<%---
			<eoms:id2nameDB id="${inspectPlanItemList.specialty}" beanId="ItawSystemDictTypeDao" />	
		--%>
		</display:column>
		<display:column title="是否有设备" >
		<c:choose>
				<c:when test="${inspectPlanItemList.exceptionFlag eq '-1'}">
					无
				</c:when>
				<c:otherwise>
					有
				</c:otherwise>
				</c:choose>
		</display:column>
		
		<display:column title="输入方式" >
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
		
			
			<display:column title="异常结果">
                <c:choose>
                    <c:when test="${inspectPlanItemList.exceptionContent == null && inspectPlanItemList.exceptionFlag eq 0}">
                        <a href="#" onclick="updateExcepctionContent('${inspectPlanItemList.exceptionContent}','${inspectPlanItemList.id}');"><font color='#ff4500'>无异常信息</font></a>
                    </c:when>
                    <c:otherwise>
                        <a href="#" onclick="updateExcepctionContent('${inspectPlanItemList.exceptionContent}','${inspectPlanItemList.id}');"><font color='#ff4500'>${inspectPlanItemList.exceptionContent}</font></a>
                    </c:otherwise>
                </c:choose>

			</display:column>
			<display:column title="巡检结果" style="width:10px;">
				<c:if test="${inspectPlanItemList.inputType eq 'radio'}">
					<eoms:id2nameDB id="${inspectPlanItemList.itemResult}" beanId="ItawSystemDictTypeDao" />
				</c:if>
				<c:if test="${inspectPlanItemList.inputType eq 'multiple'}">
					<c:forTokens items="${inspectPlanItemList.itemResult}" delims="|" var="default" varStatus="statue">
						<c:if test="${statue.last}" var="it">
							<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${default}" /> 
						</c:if>
						<c:if test="${!it}">
						<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${default}" />  | 
						</c:if>
					</c:forTokens>
				</c:if>
				<c:if test="${inspectPlanItemList.inputType eq 'text'}">
				<div style="word-break:break-all;width:100px;">${inspectPlanItemList.itemResult}</div>
				</c:if>
				<c:if test="${inspectPlanItemList.inputType eq 'number'}">${inspectPlanItemList.itemResult}</c:if>
				
			</display:column>
		<display:column title="保存日期" >
			${inspectPlanItemList.saveTime}
		</display:column>
		<display:column title="到期日期" >${inspectPlanItemList.endTime}
		</display:column>
		<display:column title="是否异常" >
			<c:choose>
				<c:when test="${inspectPlanItemList.exceptionFlag eq 0}">
				    <font class="exception">是</font>
                    <%--<div style="background-color:red;width: 100; height: 100">是</div>--%>
				</c:when>
				<c:otherwise>
				否
				</c:otherwise>
			</c:choose>
		</display:column>
        <%--<c:choose>
            <c:when test="${inspectPlanItemList.exceptionFlag eq 0}">
                <display:column title="是否异常"  style="background-color:red" >
                    是
                </display:column>
            </c:when>
            <c:otherwise>
                <display:column title="是否异常" >
                    否
                </display:column>
            </c:otherwise>
        </c:choose>--%>


			<display:column title="图片查看">
			 <c:choose>
			 	<c:when test="${inspectPlanItemList.hasPicture eq 1}">
			 		<input type="button" value="查看"  onclick="pictureId('${inspectPlanItemList.id }');" class="btn"/>
			 	</c:when>
			 	<c:otherwise>
			 		无照片
			 	</c:otherwise>
			 </c:choose>
		</display:column>
	</display:table>
</logic:present>

<br/>

<input type="button" onclick="backList('${planId}');" class="btn" value="返回"/>
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
                <%--<input type="button" value="删除" id="delete" class="btn" >--%>
			</td>
		</tr>
	</table>
	
	
	<div id="showImg" align="center">
	</div>
</div>
<input type="hidden" id="exception" />

<script type="text/javascript">
    jq(function () {
        jq('.exception').parent().css("background-color", "#ff4500");

    });
    function updateExcepctionContent(exception,id){

        var _sHeight = 280;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";

        var url ="${app}/partner/inspect/inspectPlanExecute.do?method=gotoUpdateExcectipnPage";
        var obj = new Object();
        obj.exception=exception;
        var pro =  window.showModalDialog(url,obj,sFeatures);
        if(pro==null){
            return;
        }
        document.getElementById("exception").value=pro;
//        alert( document.getElementById("exception").value);
//        alert(id);
//        return;
        Ext.Ajax.request({
            method:"post",
            url: "${app}/partner/inspect/inspectPlanExecute.do?method=updateExcectipn",
            params:{
                inspect_plan_item_id:id,
                exception_content:pro
            },
            success: function(response, options){
                if(response.responseText=='true'){
                    //刷新父页面
                    alert("异常信息修改成功！");
                    window.location.reload();
                }

            }
        })

    }

</script>
<%@ include file="/common/footer_eoms.jsp"%>
