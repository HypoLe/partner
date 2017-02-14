<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<script type="text/javascript"><!--
var jq=jQuery.noConflict();
var pid;         //计划id
var curPage=1;   //当前是第几页
//var total=6;     //总共有多少张照片
var total=${total};     //总共有多少张照片



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
		var src =document.getElementById(curPage).src;
		jq("#showImg").html("<img width='600' height='480'  src='"+src+"'>");
//			jq("#showImg").html("<img width='600' height='480'  src='${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
/*			setTimeout(function(){
					Ext.get(document.body).unmask();
			},5000);
*/			
		}
	});
	
	jq("#down").click(function(){
		if(curPage<total){
			curPage=curPage-0+1;
//			Ext.get(document.body).mask('请稍等......');
		var src =document.getElementById(curPage).src;
		jq("#showImg").html("<img width='600' height='480'  src='"+src+"'>");
//				jq("#showImg").html("<img width='600' height='480'  src='${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=showPicture&id="+pid+"&curPage="+curPage+"&idType=inspectPlan'>");
				
/*				setTimeout(function(){
					Ext.get(document.body).unmask();
				},5000);
				*/
		}else{
			alert("当前已是最后一张");
			return;
		}
	});
})


function pictureId(id,cur){
//先获得屏幕的宽度和高度
		curPage = cur;    //先把当前页设置为0
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
		var src =document.getElementById(curPage).src;
	//	jq("#showImg").html("<img width='600' height='480'  src='"+src+"'>");
		jq("#showImg").html("<img width='600' height='480'  src='${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=showPicture&id="+id+"&curPage="+curPage+"'>");
	
}


--></script>

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

	<display:table name="list" cellspacing="0" cellpadding="0"
			id="list" class="table list">
			<display:column sortable="false"
			headerClass="sortable" title="照片">
			<img src="${app}/activiti/pnrTroubleTicket/pnrTroubleTicket.do?method=showPicture&id=${list.id}" border="0" height="20px" width="50px" id="${list_rowNum}" onclick="pictureId('${list.id}','${list_rowNum}')"/>
			 </display:column>
			<display:column property="createTime" sortable="false"
			headerClass="sortable" title="时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column  sortable="false"
			headerClass="sortable" title="经纬度">
			${list.longitude};${list.latitude}
			</display:column>
	</display:table>  
<div id="comments-info" class="tabContent"></div>
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
	
	<div id="showImg" align="center"></div>
</div> 

<%@ include file="/common/footer_eoms.jsp"%>