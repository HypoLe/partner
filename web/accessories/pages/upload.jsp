<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/header_eoms_ext.jsp" %>
<%@ page import="com.boco.eoms.base.util.StaticMethod,
                 com.boco.eoms.commons.accessories.service.*,
                 com.boco.eoms.commons.accessories.model.*,
                 com.boco.eoms.base.util.ApplicationContextHolder,
                 java.util.*,com.boco.eoms.commons.accessories.model.TawCommonsAccessoriesConfig,com.boco.eoms.commons.system.dict.util.DictMgrLocator,com.boco.eoms.commons.system.dict.util.Util" %>

<fmt:bundle basename="com/boco/eoms/commons/accessories/config/ApplicationResources-accessories">

<%
String appId = StaticMethod.nullObject2String(request.getParameter("appId"));
String startwith = StaticMethod.nullObject2String(request.getParameter("startsWith"));
//System.out.println(startwith);
String startStr = "";
if(!"0".equals(startwith)){
	/*2012-12-13合作伙伴中不需要这个校验*/
	//startStr = (String) DictMgrLocator.getDictService().itemId2name(Util.constituteDictId("dict-file","startsWith"), startwith);

}
//System.out.println(startStr);

String filelist=StaticMethod.nullObject2String(request.getParameter("filelist"));
String idField=StaticMethod.nullObject2String(request.getParameter("idField"));

ITawCommonsAccessoriesConfigManager mgrr = (ITawCommonsAccessoriesConfigManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesConfigManager");
TawCommonsAccessoriesConfig tawCommonsAccessoriesConfig = mgrr.getAccessoriesConfigByAppcode(appId);

String type = tawCommonsAccessoriesConfig.getAllowFileType();
%>

<style type="text/css">
  	body{background-image:none;}
</style>

<script type="text/javascript" src="${app}/scripts/util/iframe.js"></script>
<script type="text/javascript">
	var idseed = 1;
	function addFile() {
		idseed++;
		Ext.DomHelper.append('fileDiv', 
			{tag:'span', 			
			 html:'<input type="file" name="file'+idseed+'" size="30"  class="file"/>&nbsp;&nbsp;<img src="${app}/images/icons/list-delete.gif" alt="删除" onclick="removeFile(parentNode);"/><br/>'
            }
        );
        autoIframe();
	}

	function removeFile(obj) {   
   		obj.parentNode.removeChild(obj);
   		autoIframe(); 
	}
	
	function confirmForm(){ 
  		var frm=document.tawCommonsAccessoriesForm; 
  		var appId=frm.appId.value;
 		var filelist=frm.filelist.value;
  		var idField=frm.idField.value;
  		var filetype = '<%=type%>';
  		var startwith = '<%=startwith%>';
  		var startStr = '<%=startStr%>';
  		var flag = 1;
  		var filetypeflag = 1 ;
  		var fileNameStartflag = 1 ;
   		eoms.select("input[type=file]", "tawCommonsAccessoriesForm").each(
   			function(n){
   				if(n.value=="") { 
   					flag = 0; 
   				}

	   			var fileName = n.value;
    			var fileLast = fileName.substring(fileName.lastIndexOf(".") + 1)
     			var fileNameStart = "";
     			
     			if(fileName.indexOf("\\")>-1){
     				fileNameStart= fileName.substring(fileName.lastIndexOf("\\") + 1)
     			}

    			//alert(fileLast);
    			//test = frm.FILE.value.split(".");
    			//var lasttype = test[1];

    			if(filetype!=null || !filetype.eques("")){
    				if(filetype.indexOf(fileLast.toLowerCase())<0){
    					filetypeflag = 0 ;
    					return;
    				}
    			}

    			if(startStr!=""){
    				if(fileNameStart.indexOf(startStr)!=0){
    					var fileNameStartflag = 0 ;
    					return;
    				}
    			}
    		}
    	);
    	
    	if(flag==0){
    		alert("请选择上传附件!") ;
    		return false;
    	}
    	
    	if(filetypeflag==0){
    		alert("请正确选择上传文件的类型。" + "\n" + "文件类型为<%=type%>!");
    		return false;
    	}
    	
    	if(fileNameStartflag==0){
    		alert("您选择的附件必须以["+startStr+"]\n" + "开头的文件");
    		return false;
    	}
    	
    	frm.action="${app}/accessories/pages/upload.jsp?appId="+appId+"&filelist="+filelist+"&idField="+idField+"&startsWith="+startwith;
    	frm.submit();
 	}
 	
 	function deleteFile(){
    	var frm=document.tawCommonsAccessoriesForm;
    	var temp = "";
    	var filelist=frm.filelist.value;
    	var startUrlStr = '<%=startwith%>';
    	
    	if(filelist==""){
      		alert("请选择您要删除的文件");
      		return;
    	}
    	
    	if(document.uploadfile.files.length != null){
      		for(var i=0;i<document.uploadfile.files.length;i++){
        		if(document.uploadfile.files[i].checked){
          			temp = temp + "'" + document.uploadfile.files[i].value+"',";
        		}
      		}
    	}
    	else {
    		if(document.uploadfile.files.checked){
    			temp = temp + "'" + document.uploadfile.files.value+"',";
    		}
    	}
    	
    	if(temp.length==0){
    		alert("请选择您要删除的文件。");
      		return;
    	}
   		
   		if(confirm("您确定要删除上传的文件吗？")){
   			location.href="remove.jsp?appId=<%=appId%>&removeid=" + temp.substring(0,temp.length-1) + "&filelist=" + filelist + "&idField=<%=idField%>&startsWith=" + startUrlStr;
  		}
	}
</script>

<div class="upload-box" >
<%
if(!appId.equals("")){
	ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesManager");
	List fileList=mgr.saveFile(request,appId,filelist);

	if(fileList!=null){
%>
	<form name="uploadfile" >
<% 
		filelist="";
    	for(int i=0;i<fileList.size();i++){
    		TawCommonsAccessories file=(TawCommonsAccessories)fileList.get(i);
    		String flag=file.getAccessoriesName();
    		String fileName=file.getAccessoriesCnName();
    		if("exceed".equals(flag)){ 
%>
   				<script type="text/javascript">
   					alert("<%= fileName %>");
   				</script>
<%		
    		}else{
    			
        		filelist=filelist+",'"+file.getAccessoriesName()+"'";
        		%>
        		<input type='checkbox' name='files' value='<%=file.getAccessoriesName()%>' class="checkbox">
        		<a href='${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=<%=file.getId()%>' >
        				<%=fileName%>
        		</a>
        		<br>	
<% 
    		}
    	}

    	if(filelist.indexOf(",")==0){
    		filelist=filelist.substring(1);
    	} 
%> 
 </form>
<%
	}
	else {
		filelist="";
	}
}
%>

<form name="tawCommonsAccessoriesForm" enctype="multipart/form-data" onsubmit="javascript: return confirmForm();" method="post" id="tawCommonsAccessoriesForm"> 
<table>
	<tr>
    	<td colspan="2">
    		<div id="fileDiv">
    			<span>
    				<!--input type="file" id="FILE" name="FILE" size="30" class="upload" class="file"-->
    				<input type="file" id="FILE" name="FILE1" size="30" class="file">&nbsp;&nbsp;<img src="${app}/images/icons/list-delete.gif" alt="删除" onclick="removeFile(parentNode);"/><br/>
    			</span>
    		</div>    		
            <input type="hidden" name="appId" value="<%=appId%>">
            <input type="hidden" name="filelist" value="<%=filelist%>" >
            <input type="hidden" name="idField" value="<%=idField%>" >   
     	</td>
	<tr>
    	<td>
          <input type="button" class="btn" value="<fmt:message key="tawCommonsAccessories.upload"/>" name="button" onclick="confirmForm();">
          <input type="button" class="btn" value="<fmt:message key="tawCommonsAccessories.delete"/>" name="button" onclick="deleteFile();">
       </td>
       
       <td align="right"><img src="${app}/images/icons/add.gif" alt="添加需要上传附件" onclick="javascript:addFile();" /></td>
     </tr>
  </table>    
 </form>
</div>

<script type="text/javascript" src="${app}/scripts/util/iframe.js"></script>
<script type="text/javascript">
try{
	parent.document.getElementById('<%=idField%>').value= "<%=filelist%>";
	if("<%=filelist%>"!=""){
		parent.v.clear();
	}
}
catch(e){};
</script>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>