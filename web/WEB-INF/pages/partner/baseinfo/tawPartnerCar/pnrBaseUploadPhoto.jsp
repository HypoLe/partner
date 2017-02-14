<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.base.util.StaticMethod;"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%


String photoName = StaticMethod.nullObject2String(request.getAttribute("photoName"));
String filePath =  StaticMethod.nullObject2String(request.getAttribute("filePath"));
String step = StaticMethod.nullObject2String(request.getAttribute("step"));

String picID = StaticMethod.nullObject2String(request.getAttribute("picID"));
String picNameID = StaticMethod.nullObject2String(request.getAttribute("picNameID"));

String picNo = StaticMethod.nullObject2String(request.getAttribute("picNo"));
//formNo 区分表单 在截取缩略图页面回传时候区别返回的缩略图
String formNo = StaticMethod.nullObject2String(request.getAttribute("formNo"));

String picIDImage = StaticMethod.nullObject2String(request.getAttribute("picIDImage"));
String hdId = StaticMethod.nullObject2String(request.getAttribute("hdId"));

request.setAttribute("picNo", picNo);
String defaultPic = "images/pnr_thumbnail/man.gif";

if("1".equals(step) && !photoName.equals(""))
   defaultPic = "images/pnr_thumbnail/photo/zoom/" + photoName;

if("3".equals(step) && !photoName.equals(""))
   defaultPic = "images/pnr_thumbnail/photo/zoom/" + photoName;
%>

<link type="text/css" rel="Stylesheet"	href="${app}/scripts/kmmanager/upphoto/main.css" />
<script type="text/javascript"	src="${app}/scripts/kmmanager/upphoto/jquery1.2.6.pack.js"></script>
<script type="text/javascript"	src="${app}/scripts/kmmanager/upphoto/ui.core.packed.js"></script>
<script type="text/javascript"	src="${app}/scripts/kmmanager/upphoto/ui.draggable.packed.js"></script>
<script type="text/javascript"	src="${app}/scripts/kmmanager/upphoto/CutPic.js"></script>
<script type="text/javascript">
    function Step1() {
        $("#Step2Container").hide();           
    }
    function Step2() {
        $("#CurruntPicContainer").hide();
    }
    function Step3() {
        $("#Step2Container").hide();          
    }
   
   
    function makeSure(){
    	//formNo=1时 是仪器仪表页面上传缩略图
    	window.opener.document.all.<%= picIDImage %>.src="${app}/<%=defaultPic%>";
		window.opener.document.all.<%= hdId %>.value="${app}/<%=defaultPic%>";
        self.close(); 
		
    }
    
    
</script>

<div>
    <!-- 界面左半部分 -->
	<div class="left">
		
		<!--当前照片-->
		<div id="CurruntPicContainer">
			<div class="title">
				<br><b>当前照片</b>
			</div>
			<div id='pic' class="photocontainer">
				<img id="imgphoto" src="${app}/<%=defaultPic%>" style="border-width: 0px;" />
			</div>
		<!-- <input type="submit" class="btn" name="btn_Image" value="确定" id="btn_Image" /> -->	
		
		<input type="button" name="btn_Image" id="btn_Image" value="确定" class="btn" onclick="makeSure()"/>
		
		<input id='photoName' type="hidden" name=photoName value="<%=photoName%>" />
		<input type="hidden" name="step" value="<%=step%>" />
		</div>

		<!--Step 2-->
		<div id="Step2Container">
			<div class="title">
				<b> 裁切头像照片</b>
			</div>
			<div class="uploadtooltip">
				您可以拖动照片以裁剪满意的头像
			</div>
			<div id="Canvas" class="uploaddiv">
				<div id="ImageDragContainer">
					<img id="ImageDrag" class="imagePhoto"
						src="${app}/<%=filePath%>" style="border-width: 0px;" />
				</div>
				<div id="IconContainer">
					<img id="ImageIcon" class="imagePhoto"
						src="${app}/<%=filePath%>" style="border-width: 0px;" />
				</div>
			</div>
			<div class="uploaddiv">
				<table>
					<tr>
						<td id="Min">
							<img alt="缩小" src="${app}/images/pnr_thumbnail/_c.gif"
								onmouseover="this.src='${app}/images/pnr_thumbnail/_c.gif';"
								onmouseout="this.src='${app}/images/pnr_thumbnail/_h.gif';" id="moresmall"
								class="smallbig" />
						</td>
						<td>
							<div id="bar">
								<div class="child">
								</div>
							</div>
						</td>
						<td id="Max">
							<img alt="放大" src="${app}/images/pnr_thumbnail/c.gif"
								onmouseover="this.src='${app}/images/pnr_thumbnail/c.gif';"
								onmouseout="this.src='${app}/images/pnr_thumbnail/h.gif';" id="morebig"
								class="smallbig" />
						</td>
					</tr>
				</table>
			</div>

			<form action="${app}/partner/baseinfo/pnrBasePhotos.do?method=zoomPhotoJDo&picID=<%= picID %>&picNameID=<%= picNameID %>" method="post">				
				<div class="uploaddiv">
				    <!-- <html:hidden property="userId" value="${kmExpertPhotoForm.userId}" /> -->
				    
				    <input type="hidden" name="picture" value="<%=photoName%>" />
			<input type="hidden" name="picIDImage" id="picIDImage" value="${picIDImage }"/>
			<input type="hidden" name="hdId" id="hdId" value="${hdId }"/>
					<input type="submit" class="btn" name="btn_Image" value="保存头像" id="btn_Image" />
				</div>
				<div>
                    <input type="hidden" name="txt_width"      id="txt_width"      value="1"  /><!-- 图片实际宽度 -->
                    <input type="hidden" name="txt_height"     id="txt_height"     value="1"  /><!-- 图片实际高度 -->
                    <input type="hidden" name="txt_top"        id="txt_top"        value="82" /><!-- 距离顶部 -->
                    <input type="hidden" name="txt_left"       id="txt_left"       value="73" /><!-- 距离左边 -->
                    <input type="hidden" name="txt_DropWidth"  id="txt_DropWidth"  value="120" /><!-- 截取框的宽 -->
                    <input type="hidden" name="txt_DropHeight" id="txt_DropHeight" value="120" /><!-- 截取框的高 -->
                    <input type="hidden" name="txt_Zoom"       id="txt_Zoom" /><!-- 放大倍数 -->
				</div>
			</form>
		</div>
	</div>
	
	<!-- 界面右半部分 -->
	<form  method="post" action="${app}/partner/baseinfo/pnrBasePhotos.do?method=uploadPhotoJDo&picID=<%= picID %>&picNameID=<%= picNameID %>&picNo=<%=picNo %>" enctype="multipart/form-data"  styleId="kmExpertPhotoForm"> 	
		<div class="right">
			<!--Step 1-->
			<input type="hidden" name="picIDImage" id="picIDImage" value="${picIDImage }"/>
			<input type="hidden" name="hdId" id="hdId" value="${hdId }"/>
			<div id="Step1Container">
				<div class="title">
					<br><b>更换照片</b>
				</div>
				<div id="uploadcontainer">
					<div class="uploadtooltip">
						请选择新的照片文件，文件需小于2.5MB
					</div>
					<div class="uploaddiv">
						<input type="file" name="thumbnailFile" id="thumbnailFile" title="选择照片" />
					</div>
					<div class="uploaddiv">
						<input type="submit" class="btn" name="btnUpload" value="上传" id="btnUpload" />
					</div>
				</div>

			</div>
		</div>
	</form>
</div>

<% if("".equals(filePath) || "".equals(photoName)) {%>
<script type='text/javascript'>Step1();</script>
<%}else if(!"".equals(filePath)&& "2".equals(step)){%>
<script type='text/javascript'>Step2();</script>
<%}else if(!"".equals(filePath)&& "3".equals(step)){ %>
<script type='text/javascript'>Step3();</script>
<%}%>

<%@ include file="/common/footer_eoms.jsp"%>