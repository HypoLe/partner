﻿<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="java.util.List"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpUtil"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="java.util.*,java.io.File,
                 com.boco.eoms.commons.accessories.service.*,
                 com.boco.eoms.commons.accessories.model.*,
                 com.boco.eoms.base.util.ApplicationContextHolder"%>
 <style type="text/css">
form {
	padding:20px 0;
}
em {font-weight:bold;color:red;margin:0 10px 0 2px;}
span.success{
	background:url(${app}/images/icons/nodetype/success.gif) no-repeat 0 center;
	padding-left:20px;
	color:green;
	font-weight:bold;
}
</style>

<%
  List executeContentVOList = (List)request.getAttribute("executecontentvolist");
  String flag = (String)request.getAttribute("flag");
  String monthPlanId = (String)request.getAttribute("monthplanid");
  String userByStub = (String)request.getAttribute("userbystub");
  String date = (String)request.getAttribute("date");
  TawwpExecuteContentVO tawwpExecuteContentVO = null;
    String ast ="";
     String s=StaticMethod.getCurrentDateTime().substring(0,10);
  String contentIdStr = "";
  String contentFlag="";
  //String nameC="";
  for(int i=0; i<executeContentVOList.size(); i++){
    contentIdStr += (((TawwpExecuteContentVO)executeContentVOList.get(i)).getId() + ",");
   // nameC+= (((TawwpExecuteContentVO)executeContentVOList.get(i)).getName() + ",");
   ast=((TawwpExecuteContentVO)executeContentVOList.get(i)).getStartDate().substring(0,10);
    if((((TawwpExecuteContentVO)executeContentVOList.get(i)).getExecuteType().equals("1")))
    {
      contentFlag="1";
    }
  }
  String executeType = "";

  String currUser = (String)request.getAttribute("curruser");
  ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesManager");
  String intime = StaticMethod.nullObject2String(request.getAttribute("intime"));
%>
                 

<script language="JavaScript">
Ext.onReady(function(){
	colorRows('listTable');
})

function onFile(obj1,obj2,obj3,obj4,obj5,obj6,obj7,obj8)
{
  var object1Name=obj1.name;
  var object1Value=obj1.value;
  var object2Name=obj2.name;
  var object2Value=obj2.value;
  var object3Name=obj3.name;
  var object3Value=obj3.value;
  var object4Name=obj4.name;
  var object4Value=obj4.value;
  var object5Name=obj5.name;
  var object5Value=obj5.value;
  var object6Name=obj6.name;
  var object6Value=obj6.value;
  var strShowSpanId = obj7;
  document.getElementById(obj8).style.display = "none";

  var _sHeight = 300;
  var _sWidth = 420;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  
  window.showModalDialog('../tawwpexecute/uploadfile.do?name='+ object1Name+'&numname='+object2Name+'&resulturl='+object1Value+'&executecontentid='+object3Value+'&executecontentuseridname='+object4Name+'&executecontentuserid='+object4Value+'&userid='+object5Value+'&stubuser='+object6Value+'&action=add&showspanid='+strShowSpanId,window,sFeatures);
  // window.open('../tawwpexecute/uploadfile.jsp?name='+ object1Name+'&numname='+object2Name+'&resulturl='+object1Value+'&executecontentid='+object3Value+'&executecontentuseridname='+object4Name+'&executecontentuserid='+object4Value+'&userid='+object5Value+'&stubuser='+object6Value+'&action=add&showspanid='+strShowSpanId);
}

function onAddons(url)
{
  var _sHeight = 600;
  var _sWidth = 800;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  window.showModalDialog(url,window,sFeatures);
}
function onAddonsNet(url)
{
  var _sHeight = 300;
  var _sWidth = 400;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  window.showModalDialog(url,window,sFeatures);
}
function GoBack()
{
  window.history.back()
}
 
function GoSms()
{
  var myform=document.executeadds;
  myform.action="../../../EOMS_J2EE/sms/sendPage1.jsp";
  myform.target="_blank";
  myform.submit();
 
}
function GoContent()
{
  var myform=document.executeadds;
  myform.action="../tawwpexecute/sameview1.do?executecontentid=<%=contentIdStr.substring(0,contentIdStr.length()-1)%>"
  myform.submit();
}


function Submit()
{
  var myform=document.executeadds;
  var k = <%=executeContentVOList.size()%>;
  var remark = "";
  var normflag = "";
  var bool = false;
  var reason="";
  var ats="";
  var s="";  
  for(var i=0;i<k;i++){
  	remark = document.all('remark'+i).value;
  	normflag = document.all('normalFlag'+i).value;
  	ast=document.all('ast'+i).value;
  	s=document.all('s'+i).value;
  	name = document.all('name'+i).value;
  	fileflag = document.all('fileflag'+i).value;
  	count = document.all('count'+i).value;
  	if(ast<s){
  		reason=document.all('reason'+i).value;
  	}
  	if(ast<s){
  		if(reason==""||reason==null){
  			alert('请输入补填原因');
  		}
  	}
  	if(normflag=="0" && remark==""){
  		alert("在执行项“" + name + "”中，您选择了非正常，请填写备注信息！");
  	}else if(fileflag==1 && count<1){
     	alert("执行项“" + name + "”必须上传附件，请您上传附件！");
  	}else{
  		myform.action="../tawwpexecute/gxexecutesaves.do?contentidstr=<%=contentIdStr.substring(0,contentIdStr.length()-1)%>&flag=<%=flag%>&executetype=<%=executeType%>";
  		ajaxPost(myform);
  	}
  }
}

function ajaxPost(frm){
	
	function success(x){
		Ext.get(frm).unmask();
		var info = {};
		try{info = eoms.JSONDecode(x.responseText);}catch(e){}
		if(info.success==false){
			alert(info.message || "保存中发现错误，请联系管理员");
			return;
		}
		else if(info.success==true){
		
			Ext.fly('status').update(info.message || "保存成功").highlight();
		}
	}
	function failure(){
		Ext.get(frm).unmask();
		alert("保存中发现错误，请联系管理员");
	}
	
	Ext.get(frm).mask('保存中，请稍候...');
	
	Ext.Ajax.request({
		method : 'Post',
		url : frm.action,
		success : success,
		failure : failure,
		params : Ext.lib.Ajax.serializeForm(frm)
	});
}

function addworkflow(i,id) {

   var workplanType=document.all('workplanType'+i).options[document.all('workplanType'+i).selectedIndex].value;
      
   var url = '${app}/workbench/onlineuser/tawWorkbenchOnlineUsers.do';
   	   
  if(workplanType=="1"){
  	   	  url="${app}/sheet/commonfault/commonfault.do?method=showNewSheetPage";
  }else{
  	   	  url="";	
  }
   window.open(url, '', 'channelMode, menubar=no, toolbar=no, location=no, directories=no, status=yes, resizable=yes, scrollbars=yes, width=600, height=400, fullscreen=no');
   document.all("workflow"+i).innerHTML = "编号<input type='text' name='workflowId"+i+"' /><input type='button' value='确定' onclick=submitworkflow('"+i+"','"+id+"')><input type='button' value='取消' onclick='removeDiv("+i+")'>";
  
}
function removeDiv(i){
  
  
  document.all("workflow"+i).innerHTML="";
     
}
function submitworkflow(i,id){
    if(document.all("workflowId"+i).value==""){
       alert("请输入工单编号");
    }else {  
      var workflowId=document.all('workflowId'+i).value;
      var workplanType=document.all('workplanType'+i).options[document.all('workplanType'+i).selectedIndex].value;
      window.frames['workflowFrame'+i].document.forms[0].action='../tawwpexecute/workflowAdd.do?workflowId='+workflowId+'&executeId='+id+'&executeType='+workplanType; 
      window.frames['workflowFrame'+i].document.forms[0].submit();
      document.all("workflow"+i).innerHTML="";
    }
}  
function onCopy()
{
  var monthPlanId = document.forms[0].monthplanid.value;
  var userByStub = document.forms[0].userbystub.value;
  var date = document.forms[0].date.value;
  var flag = document.forms[0].flag.value;
  if( !confirm("复制上一工作日执行周期为天的执行内容,请确认!") ) return;
  window.navigate( "../tawwpexecute/previous.do?monthplanid=" + monthPlanId + "&userbystub=" + userByStub + "&date=" + date + "&flag=" + flag);
}
</script>

<!--  body begin  -->

<form name="executeadds" method="POST" >

  <br>
  <table>
   <tr>
       <td  >
  <table  class="listTable">
    <caption><bean:message key="executeadds.title.formTitle" /></caption>
       
    <%--<tr>
       <td   align="right" >
        <a href="../tawwpexecute/333.doc" target="_blank" >
        <strong><bean:message key="executeadds.title.linkWAPGuideBook" /></strong> <img src="${app }/images/icons/list.png">
        </a>
       </td>
    </tr>
    <tr>
       <td   align="right" >
        <a href="../tawwpexecute/333.doc" target="_blank" >
        <strong><bean:message key="executeadds.title.linkSMSGuideBook" /></strong> <img src="${app }/images/icons/list.png">
        </a>
       </td>
    </tr>
  --%></table>
   </td>
   </tr>
   <tr>
   <td>
   
  <table class="listTable" id="listTable">
     
    <thead>
    <%if(contentFlag.equals("1")){%>
    <%--<tr>
      <td colspan="6">
		<input type="button" value="<bean:message key="executeadds.title.btnSelectGroup" />" name="<bean:message key="executeadds.title.btnSelectGroup" />" class="button" onclick="javascript:GoContent();">
      </td>
    </tr>
    --%><%}%>
    <tr>
      <td width="15%">
         作业项目
      </td>
      <!-- 
      <td width="15%">
         业务类型
      </td>
      <td width="15%">
         执行单位级别
      </td>
      <td width="15%">
         适用说明
      </td>
      -->
 	<td width="5%" >
        网元
      </td>
      <td width="5%" >
        <bean:message key="executeadds.title.formCycle" />
      </td>
      <td width="10%">
         填写时间
      </td>
      <td width="20%">
        <bean:message key="executeadds.title.formContent" />
      </td>
      <td width="6%">
         是否正常
      </td>
      <td width="10%">
     <bean:message key="executeadd.title.formRemark" />
      </td>
      <td width="10%">
        <bean:message key="executeadds.title.formFileName" />
      </td>
      <td width="10%">
        <bean:message key="executeadds.title.formForm" />
      </td>
      <td width="6%">
        <bean:message key="executeadds.title.formGroup" />
      </td>
      <td width="15%"> 
         关联工单
      </td>
      <td width="10%">
         执行帮助
      </td>
      <td width="5%">
         指导文件
      </td>
          <%
        if(ast.compareTo(s)<0){
      
    %>
      <td width="5%">
        补填原因
      </td>
      <%  } %>
    </tr>
    </thead>
    <tbody>

    <%
      for(int i=0; i<executeContentVOList.size(); i++){
        tawwpExecuteContentVO = (TawwpExecuteContentVO)executeContentVOList.get(i);
        String writeDate = "";
        if(tawwpExecuteContentVO.getWriteDate()!=null&&!tawwpExecuteContentVO.getWriteDate().equals("")){
            writeDate = tawwpExecuteContentVO.getWriteDate();
        }
    %>
<tr >
<td colspan="13">
          
      
        
<div class="skin-blue">
<div class="box">
        	<span class="rc-tp"><span></span></span>
        	<div class="bd" >
        
        	<span id="status" class="success">
        	<%
        	if(!"0".equals(tawwpExecuteContentVO.getExecuteFlag())&& !"".equals(tawwpExecuteContentVO.getExecuteFlag())){
          	    out.print("本计划执行项已执行");
          	}
          	%>
        	</span>
        	</div>    
        	<span class="rc-bt"><span></span></span>
		</div></div></td>
</tr>
    <tr>
       
      <td width="25%">
 
        <%=tawwpExecuteContentVO.getName()%>
        <input type="hidden" name="name<%=i%>" value="<%=tawwpExecuteContentVO.getName()%>">
        <input type="hidden" name="executecontentuserid<%=i%>" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
        <input type="hidden" name="executecontentid<%=i%>" value="<%=tawwpExecuteContentVO.getId()%>">
        <%
          if("1".equals(tawwpExecuteContentVO.getStubFlag())){
        %>
        <bean:message key="stubuseradd.title.formAgent" />
        <input type="hidden" name="userid<%=i%>" value="<%=tawwpExecuteContentVO.getUserByStub()%>">
        <input type="hidden" name="stubuser<%=i%>" value="<%=currUser%>">
         <input type="hidden" name="ast<%=i%>" value="<%=ast%>">
         <input type="hidden" name="s<%=i%>" value="<%=s%>">
        <%
          }
          else
          {
        %>
        <input type="hidden" name="userid<%=i%>" value="<%=currUser%>">
        <input type="hidden" name="stubuser<%=i%>" value="">
         <input type="hidden" name="ast<%=i%>" value="<%=ast%>">
         <input type="hidden" name="s<%=i%>" value="<%=s%>">
        <%
          }
        %>
      </td>
      <!-- 
      <td width="15%">
        <%=StaticMethod.null2String(tawwpExecuteContentVO.getBotype()) %>
      </td>
      <td width="15%">
        <%=StaticMethod.null2String(tawwpExecuteContentVO.getExecutedeptlevel()) %>
      </td>
      <td width="15%">
        <%=StaticMethod.null2String(tawwpExecuteContentVO.getAppdesc()) %>
      </td>
       -->
     
 	<td width="15%">
       <a href="javascript:onAddonsNet('../tawwpnet/executenetlist.do?&executeId=<%=StaticMethod.null2String(tawwpExecuteContentVO.getId())%>');">查看
      </a>
      </td>
      <td width="5%">
        <%=tawwpExecuteContentVO.getCycleName()%>
      </td>
      <td width="10%">
        <input type="hidden" name="writeDate<%=i%>" size="20"    class="text" value="<%=StaticMethod.getCurrentDateTime()%>"><%=StaticMethod.getCurrentDateTime() %>
      </td>
      <td width="20%">
      
        <textarea name="content<%=i%>"  rows=3 cols="45" value=""><%=tawwpExecuteContentVO.getContent()%></textarea>
      </td>
      <td width="6%" align=center>
      <%String normalFlag ="normalFlag"+i; %>
       <eoms:dict key="dict-workplan" dictId="normalFlag" beanId="selectXML" defaultId="<%=tawwpExecuteContentVO.getNormalFlag()%>"  isQuery="false"  selectId="<%=normalFlag%>"  />
      </td>
      <td width="10%">
      <textarea name="remark<%=i%>"  rows=3 cols="20" value=""><%=tawwpExecuteContentVO.getRemark()%></textarea>
     </td>
      <td width="10%" align="left">
       	<input type="hidden" name="fileflag<%=i%>" value="<%=tawwpExecuteContentVO.getFileFlag()%>">
        <div id="file<%=i%>" style="">
        <%
          if(!"".equals(tawwpExecuteContentVO.getFileName())){
            String[] tempStr = tawwpExecuteContentVO.getFileName().split(",");
            for(int j=0; j<tempStr.length; j++){
               String[] tempArray = tempStr[j].split("@");
        %>
        <%--
        <a href="do_download.jsp?id=<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
        --%>
        <a href="<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
        <%
            }
          }
        %>
        </div>
        <span id="showLink_<%=i%>"></span>
        <input type="hidden" name="filename<%=i%>" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
        
        <input type="button" class="button" name="count<%=i%>" onclick="javascript:onFile(filename<%=i%>,count<%=i%>,executecontentid<%=i%>,executecontentuserid<%=i%>,userid<%=i%>,stubuser<%=i%>,'showLink_<%=i%>','file<%=i%>');" value="<%=tawwpExecuteContentVO.getFileCount()%>"><bean:message key="executeadds.title.labFile" />
      </td>
      <td width="10%">
     <%
      String formDataId=StaticMethod.null2String(tawwpExecuteContentVO.getFormDataId(),"");
       
      if(formDataId!=null||"".equals(tawwpExecuteContentVO.getFormDataId())){
         
    %>
      <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=new&window=new&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
      <input type="hidden" name="formdataid<%=i%>" value="<%=formDataId%>">
    <%
      }else{
    %>
      <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=edit&window=edit&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
      <input type="hidden" name="formdataid<%=i%>" value="<%=tawwpExecuteContentVO.getFormDataId()%>">
    <%
      }
    %>
      </td>
      <td width="6%" align=center>
        <%
          if("1".equals(tawwpExecuteContentVO.getExecuteType())){
            executeType = tawwpExecuteContentVO.getExecuteType();
        %>
        <a href="../tawwpexecute/sameview.do?executecontentid=<%=tawwpExecuteContentVO.getId()%>" >
          <img src="${app }/images/icons/list.png"> 
        </a>
        <%
          }
        %>
      </td>
      <td width="6%" align=center >
       <iframe name="workflowFrame<%=i%>" src="../tawwpexecute/workflowList.do?executeId=<%=tawwpExecuteContentVO.getId()%>" frameborder=0>
        </iframe>
       <div align=center > 
       
		<select name="workplanType<%=i%>" >
			<option value="1">故障处理工单</option>
            <option value="2">网优工单</option>
        </select>&nbsp; 
        <input type="button" value="添加" class="button"  onclick="javascript:addworkflow(<%=i%>,'<%=tawwpExecuteContentVO.getId()%>');" >
       </div> 
       <div id="workflow<%=i%>" > 
        </div>  
      </td>
      <td width="10%">
        <%=tawwpExecuteContentVO.getExtendremark()%>
      </td>
      <td width="5%">
        <%
          List list1 = null;
          if(!tawwpExecuteContentVO.getAccessories().equals("")){
            list1 =mgr.getAllFileById(tawwpExecuteContentVO.getAccessories());
          }
          if(list1 != null) {
            for(int num=0; num<list1.size(); num++){
            TawCommonsAccessories accessories=(TawCommonsAccessories)list1.get(num);

         %>
         <script language="javascript">
         document.write("<a href=\"${app}/accessories/tawCommonsAccessoriesConfigs.do?method=download&id=<%=accessories.getId()%>&filelist=<%=tawwpExecuteContentVO.getAccessories()%>\">");
         document.write("<img src=\"${app}/images/page.gif\" border=\"0\" align=\"absmiddle\"><%=StaticMethod.nullObject2String(accessories.getAccessoriesCnName())%></a>");
         </script>
         <%
            } 
          }
         %>
      </td>
   <% if(ast.compareTo(s)<0){
   		if(tawwpExecuteContentVO.getReason()==null||"".equals(tawwpExecuteContentVO.getReason())){
   			tawwpExecuteContentVO.setReason("");
   		}
   %>
  	<td width="5%">
        <textarea name="reason<%=i%>"  rows=3 cols="45" ><%=tawwpExecuteContentVO.getReason()%></textarea>
  	</td>
  	<%} %>
  </tr>
   <%
      }
    %>
  </tbody>
  </table>
<br>
 <input type="hidden" value="<%=monthPlanId%>" name="monthplanid">
 <input type="hidden" value="<%=flag%>" name="flag">
 <input type="hidden" value="<%=userByStub%>" name="userbystub">
 <input type="hidden" value="<%=date%>" name="date">
 <input type="button" value="<bean:message key="executeadds.title.btnSubmit" />" name="B1" class="submit" onclick="javascript:Submit(this);"> 
 <%--<input type="button" value="复制并保存" name="B3" class="button" onclick="javascript:onCopy();">
 --%><input type="button" value="<bean:message key="executeadds.title.btnBack" />"  name="B2" class="button" onclick="javascript:GoBack();">

</form>

<!--  body end  -->
<%@ include file="/common/footer_eoms.jsp"%>

