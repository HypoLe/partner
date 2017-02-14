<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>

	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
var pid;         //计划id
var curPage=1;   //当前是第几页
var total=6;     //总共有多少张照片



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
		jq("#showImg").html("<img width='600' height='480'  src='${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=showPicture&id="+id+"&curPage="+curPage+"'>");
	
}


function chooseRes(){

			var photoes = document.getElementsByName("photoes");
			var photoIds = "";
			
			for(var i=0;i<photoes.length;i++){
			if(photoes[i].checked==true){
			photoIds = photoIds+photoes[i].value+",";
			}
			}
			 var url="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=selectPhotoTodo&photoes="+photoIds;
        				
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					var sum = eval("("+res+")");
	                if(eoms.isIE){
						window.dialogArguments.setPhoto(sum,photoIds);
					}else{
						window.opener.setPhoto(sum,photoIds);
					}
					
					window.close();
				},
				failure: function ( result, request) { 
				alert("失败了");
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
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
<form action="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=conditionSelectPhoto" method="post" >
   <table class="formTable">
	<caption>
		<div class="header center">图片列表</div>
	</caption>
			<tr>
					<td class="label">描述</td>
					<td class="content">
					<input type="text" name="photoDescribe" value="${photoDescribe }" styleClass="text medium" >
					</td>
					<td class="label">拍照时间</td>
						<td class="content">
							<input type="text" class="text" name="createPhotoTime"
								readonly="readonly" id="createPhotoTime" value="${createPhotoTime }"
								onclick="popUpCalendar(this, this,null,null,null,null,-1)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'拍照时间过时',allowBlank:true" />
						</td>
				</tr>
				<tr>
				<td class="label">
					拍照人
				</td>
					<td>
						<div class="x-form-item" >
		<eoms:chooser id="photoCreate" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDept',rootId:'${country}'}]"  config="{returnJSON:true,showLeader:true}"
		   category="[{id:'photoCreate',text:'接收',allowBlank:false,childType:'user',limit:1,vtext:'请选择接收对象'}]"
		  data="" />
	</div>	
					</td>
				</tr>
				<tr><td colspan="4" align="center">
				<input type="submit"  value="查询"/>
					</td></tr>
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
			<img src="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=showPicture&id=${list.id}" border="0" height="20px" width="50px" id="${list_rowNum}" onclick="pictureId('${list.id}','${list_rowNum}')"/>
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
			<input type="button" value="选择" onclick="chooseRes()"/>
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