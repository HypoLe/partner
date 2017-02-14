<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>
<head>
<base target="_self" />
</head>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

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
	        		url:"${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=deletePictureById",//和本地网共用一个方法
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

			var obj = document.getElementsByName("selectedOltBbuRoomId");
			var selectedOltBbuRoomId="";
			for(var i=0;i<obj.length;i++){
				if(obj[i].checked==true){
					selectedOltBbuRoomId=obj[i].value;
				}
			}
			if(selectedOltBbuRoomId.length > 0){
			 var url="${app}/activiti/pnrOltBbuRoom/pnrOltBbuRoom.do?method=selectOltBbuRoomTodo&selectedOltBbuRoomId="+selectedOltBbuRoomId;
        				
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					var sum = eval("("+res+")");
	                //if(eoms.isIE){
					//	window.dialogArguments.setPhoto(sum,photoIds);
					//}else{
					//	window.opener.setPhoto(sum,photoIds);
					//}
					
					window.dialogArguments.setOltBbuRoom(sum);
					
					window.close();
				},
				failure: function ( result, request) { 
				alert("失败了");
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
			
			
			}else{
				alert("请选择机房！");
			}
			
		}

	function selectArchiveAll(obj) {
        var temp = document.getElementsByName("selectedAssetId"); 
        for (var i =0; i<temp.length; i++) 
        { 
            temp[i].checked = obj.checked; 
        } 
    } 
    
    function cancelArchive(obj,all) {
        var flag = 0; 
        var all = document.getElementsByName(all)[0]; 
        if (!obj.checked) 
        { 
            all.checked = false; 
        } 
        else 
        { 
            for (var i=0; i<document.getElementsByName(obj.name).length; i++) 
            { 
                if (!document.getElementsByName(obj.name)[i].checked) 
                { 
                    all.checked = false; 
                } 
                else 
                { 
                    flag++; 
                } 
            } 
            if (flag == document.getElementsByName(obj.name).length) 
            { 
                all.checked = true; 
            } 
        } 
    } 
    
    
	function newReset(){
		document.getElementById("jfAddressName").value="";
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
<div id="sheetform">
	<html:form action="/pnrOltBbuRoom.do?method=selectOltBbuRool"
				styleId="theform">
				<input type="hidden" id="condition" name="condition" value="${condition}" />
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					
					<tr>
						<!-- 工单号  -->
						<td class="label" style="width:10%">
							设备位置/局址名称
						</td>
						<td style="width:20%">
							<input type="text" name="jfAddressName" class="text" id="jfAddressName"
								value="${jfAddressName}" />
						</td>
						
						
					</tr>

                  
                 
					
				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
				<c:if test="${!empty roomAssetsList}">
			 		<html:button property="" styleClass="btn" onclick="chooseRes()">选择</html:button>
				</c:if>
				</div>
			
			</html:form>
			
		</div>	
<div class="form-btns" id="btns" style="display:block">
 
	
		</div> 

	<display:table name="roomAssetsList" cellspacing="0" cellpadding="0"
			id="roomAssetsList" pagesize="${pageSize}" class="listTable roomAssetsList"
			export="false" requestURI="pnrOltBbuRoom.do" sort="list"
			size="total" partialList="true">
			<display:column 
				headerClass="sortable" title="">
				<input type="radio" name="selectedOltBbuRoomId" <c:if test="${oltBbuRoomId eq roomAssetsList.id}">checked="checked"</c:if>  value="${roomAssetsList.id}" />
			</display:column>
			<display:column property="jfCity" sortable="false"
				headerClass="sortable" title="地市"/>
			<display:column property="jfCountry" sortable="false"
				headerClass="sortable" title="区县"/>
			<display:column property="jfAddressName" sortable="false"
				headerClass="sortable" title="设备位置/局址名称"/>
			
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