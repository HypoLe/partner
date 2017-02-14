<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>

<base target="_self" />
</head>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
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
<script type="text/javascript">
	//重置
function newReset(){
	document.getElementById("sheetAcceptLimit").value="";            //开始时间
	document.getElementById("sheetCompleteLimit").value="";		//结束时间
	document.getElementById("photoDescribe").value="";		//故障描述
	document.getElementById("faultLocation").value="";	        //故障地址
}
</script>

<script type="text/javascript">
var jq=jQuery.noConflict();
var pid;         //计划id
var curPage=1;   //当前是第几页
//var total=6;     //总共有多少张照片
var total=${total};     //总共有多少张照片


jq(function(){
	jq("#pifilter").hide();
	jq("#shoupicture").hide();
	var items = jq(".uploadPicture");
	var deptid = ${sessionform.deptid};
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
	
		//删除图片功能
		jq(".lj").click(function (){
			div=jq(this).parent().parent(); //先获取父级元素
		 	if(confirm("是否确认删除")){
		 		jq.ajax({
	        		type:"POST",
	        		url:"${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=deletePictureById",
	        		data:{"id":jq(this).find("input:eq(0)").val()},
	        		dataType:"json",
	        		success:function(data){
	        			if(data.result=="success"){
	        			    div.remove();
	        			}else{
	        				alert(data.content);
	        			}                           			
	        		}
	        	});
		 	}else{
		 		return false;
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
		jq("#showImg").html("<img width='600' height='480'  src='${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=showPicture&id="+id+"&curPage="+curPage+"'>");
	
}



function chooseRes(){
			var photoes = document.getElementsByName("photoes");
			var photoIds = "";
			var count=0;
			for(var i=0;i<photoes.length;i++){
				if(photoes[i].checked==true){
					photoIds = photoIds+"'"+photoes[i].value+"',";
					count++;
				}
			}
			
			//判断是否选择了照片
			if(count == 0 ){
				alert("请选择要补录的照片后再提交！");
				return;
			}else{
				var processInstanceId=jq("#processInstanceId").val();
				var url="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=submitMakeupPhotos&photoes="+photoIds+"&processInstanceId="+processInstanceId;
				jq.ajax({
	        		type:"POST",
	        		url:url,
	        		dataType:"json",
					success:function(data){
						if(data.result=="success"){//提交成功
						   alert(data.content);
						  
						}else if(data.result=="error"){//提交失败
							 alert(data.content);
						}  
						window.dialogArguments.refresh(); //调用父窗体中定义的刷新方法从而刷新父窗体
	                    window.close(); //关闭本页面                        			
					},
		 			error: function (err) {
		           		alert(err);
		        	}	
				});
			}
		}
</script>


<form action="${app}/activiti/pnrTransferMaleGuest/pnrTransferOfficeMaleGuest.do?method=conditionSelectPhoto" method="post" >
	<input type="hidden" id="photoType" name="photoType" value="${photoType}"><!-- 照片类型 -->
	<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程号 -->
	<input type="hidden" id="replyTime" name="replyTime" value="${replyTime}"><!-- 回复时间 -->
	
   <table class="formTable">
	<caption>
		<div class="header center">图片列表</div>
	</caption>
			<tr>
					
				 	<td class="label">
							开始时间
						</td>
						<td class="content">
							<input type="text" class="text" name="sheetAcceptLimit"
								readonly="readonly" id="sheetAcceptLimit" value="${sheetAcceptLimit}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'开始时间不能晚于结束时间',allowBlank:true" />

						</td>
						<td class="label">
							结束时间
						</td>
						<td class="content">
							<input type="text" class="text" name="sheetCompleteLimit"
								readonly="readonly" id="sheetCompleteLimit" value="${sheetCompleteLimit}"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'结束时间不能早于开始时间',allowBlank:true" />
						</td>
				</tr>
				<tr>
				<td class="label">故障描述</td>
					<td class="content">
					<input type="text" class="text" id="photoDescribe" name="photoDescribe" value="${photoDescribe}"  >
					</td>
					<td class="label">故障地址</td>
					<td class="content">
						<input type="text" class="text" id="faultLocation" name="faultLocation" value="${faultLocation}"  >
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="submit"  value="查询"/>
						<input type="button"  value="重置" onclick="newReset()"/>
					</td>
				</tr>
		</table> 
	</form>	
<br/><br/><br/>
	<display:table name="list" cellspacing="0" cellpadding="0"
			id="list" class="table list">
			<display:column  sortable="false"
			headerClass="sortable" title="选择">
			<input type="checkbox" name="photoes" id="photoes" value="${list.id}"/>
			</display:column>
			<display:column sortable="false"
			headerClass="sortable" title="照片">
			<img src="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=showPicture&id=${list.id}" border="0" height="20px" width="50px" id="${list_rowNum}" onclick="pictureId('${list.id}','${list_rowNum}')"/>
			 </display:column>
			<display:column property="createTime" sortable="false"
			headerClass="sortable" title="拍照时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" />
			<display:column  sortable="false"
			headerClass="sortable" title="拍照人员">
			<eoms:id2nameDB id="${list.userId}" beanId="tawSystemUserDao"/>
			</display:column>
			<display:column  sortable="false"
			headerClass="sortable" title="经纬度">
			${list.longitude};${list.latitude}
			</display:column>
			<display:column property="faultLocation" sortable="false"
			headerClass="sortable" title="照片地址"/>
			<display:column property="faultDescription" sortable="false"
			headerClass="sortable" title="照片描述"/>
	</display:table> 
	
	
	<div class="form-btns" id="btns" style="display:block">
	<c:if test="${list!= null}">
			<input type="button" value="提交" onclick="chooseRes()"/>
	</c:if>
		</div> 
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