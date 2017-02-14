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

			var tempSelectedAssetIds = document.getElementsByName("selectedAssetId");
			var selectedAssetIds = "";
			var count=0;
			for(var i=0;i<tempSelectedAssetIds.length;i++){
				if(tempSelectedAssetIds[i].checked==true){
				selectedAssetIds = selectedAssetIds+tempSelectedAssetIds[i].value+",";
				count++;
				}
			}
			
			if(count > 0){
			//alert(photoIds);
			 var url="${app}/activiti/roomDemolition/roomAssets.do?method=selectAssetTodo&selectedAssetIds="+selectedAssetIds;
        				
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
					
					window.dialogArguments.setAssetIds(sum,selectedAssetIds);
					
					window.close();
				},
				failure: function ( result, request) { 
				alert("失败了");
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
			
			}else{
				alert("请选择资产！");
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
<form action="${app}/activiti/roomDemolition/roomAssets.do?method=queryAssets" method="post" >
	<input  type="hidden" class="text" id="assetIds" name="assetIds" value="${assetIds}"/>
	<!-- <input  type="hidden" class="text" id="assetIds" name="assetIds" value="${assetIds}"/>  -->
   <table class="formTable">
	<caption>
		<div class="header center">资产列表</div>
	</caption>
			<tr>
					<td class="label">资产编号</td>
					<td class="content">
					<input type="text" id="assetNumbers" name="assetNumbers" value="${assetNumbers}" class="text medium" > 
					</td>
					
					<td class="label">资产名称</td>
					<td class="content">
					<input type="text" id="assetName" name="assetName" value="${assetName}" class="text medium" >
					</td>
				</tr>
				<tr><td colspan="4" align="center">
				<input type="submit"  value="查询"/>
					</td></tr>
		</table> 
	</form>	
<br/><br/><br/>
<div class="form-btns" id="btns" style="display:block">
	<c:if test="${!empty roomAssetsList}">
			<input type="button" value="选择" onclick="chooseRes()"/>
	</c:if>
		</div> 

	<display:table name="roomAssetsList" cellspacing="0" cellpadding="0"
			id="roomAssetsList" pagesize="${pageSize}" class="listTable roomAssetsList"
			export="false" requestURI="roomAssets.do" sort="list"
			size="total" partialList="true">
			<display:column 
				headerClass="sortable" title="<input type='checkbox' id='archiveAll' name='archiveAll' onclick='selectArchiveAll(this);'  />">
				<input type="checkbox" name="selectedAssetId" onclick="cancelArchive(this,'archiveAll')" value="${roomAssetsList.id}" />
			</display:column>
			<display:column property="assetNumbers" sortable="false"
				headerClass="sortable" title="资产编号"/>
			<display:column property="assetName" sortable="false"
				headerClass="sortable" title="资产名称"/>
			<display:column property="assetTagNumber" sortable="false"
				headerClass="sortable" title="资产标签号"/>
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