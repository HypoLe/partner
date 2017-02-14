<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpExecuteContentVO"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpUtil"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="java.util.*,java.io.File,
                 com.boco.eoms.commons.accessories.service.*,
                 com.boco.eoms.commons.accessories.model.*,
                 com.boco.eoms.base.util.ApplicationContextHolder"%>
<%
  TawwpExecuteContentVO tawwpExecuteContentVO = (TawwpExecuteContentVO)request.getAttribute("tawwpexecutecontentvo");
  String currUser = (String)request.getAttribute("curruser");
  String monthPlanId = (String)request.getAttribute("monthplanid");
  String userByStub = (String)request.getAttribute("userbystub");
  String userId = "";
  String stubUser = "";
 String time=StaticMethod.getLocalString();
  if(!"".equals(userByStub))
  {
    userId = userByStub;
    stubUser = currUser;
  }
  else{
    userId = currUser;
  }
  ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesManager");
  String intime = StaticMethod.getCurrentDateTime().substring(0,10);
  String starTime=tawwpExecuteContentVO.getStartDate().substring(0,10);

%>
<script language="JavaScript">

function GoBack()
{
  window.history.back()
}

function onFile(obj1,obj2,obj3,obj4,obj5,obj6,obj7)
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

  var _sHeight = 300;
  var _sWidth = 420;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  window.showModalDialog('../tawwpexecute/uploadfile.do?name='+ object1Name+'&numname='+object2Name+'&resulturl='+object1Value+'&executecontentid='+object3Value+'&executecontentuseridname='+object4Name+'&executecontentuserid='+object4Value+'&userid='+object5Value+'&stubuser='+object6Value+'&action=add&showspanid='+strShowSpanId,window,sFeatures);
  //window.open('../tawwpexecute/uploadfile.jsp?name='+ object1Name+'&numname='+object2Name+'&resulturl='+object1Value+'&executecontentid='+object3Value+'&executecontentuseridname='+object4Name+'&executecontentuserid='+object4Value+'&userid='+object5Value+'&stubuser='+object6Value+'&action=add&showspanid='+strShowSpanId);
}

function onAddons(url)
{
  var _sHeight = 600;
  var _sWidth = 820;
  var sTop=(window.screen.availHeight-_sHeight)/2;
  var sLeft=(window.screen.availWidth-_sWidth)/2;
  var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
  window.showModalDialog(url,window,sFeatures);
}

function submitform()
{
  document.forms[0].action = "../tawwpexecute/executeinterfacesave.do?monthplanid=<%=monthPlanId%>";
  document.forms[0].submit();
}

function onSubmit()
{
  var normalFlag = document.forms[0].normalFlag.value;
  var remark = document.forms[0].remark.value;
  var fileFlag = document.forms[0].fileFlag.value;
  var count =  document.forms[0].count.value;
  var starTime=document.forms[0].starTime.value;
   var intime=document.forms[0].intime.value;
  if(normalFlag=='0' && remark==''){
  	alert("您选择了非正常，请填写备注信息");
  }else if(fileFlag==1&&count<1){
     alert('此模板选择了必须上传附件,请上传附件');
  }else{
  document.forms[0].action="../tawwpexecute/executesave.do?monthplanid=<%=monthPlanId%>"
  document.forms[0].submit();
  }
}

</script>

<form name="executecontentform" method="POST" >
<!--  body begin  -->
<br>

<table class="formTable">
  <caption><bean:message key="executeadd.title.formTitle" /></caption>
  <tr>
    <td width="100" class="label"><bean:message key="executeadd.title.formName" /></td>
    <td width="310">
      <%=tawwpExecuteContentVO.getName()%>
      <%
        if(!"".equals(StaticMethod.null2String(userByStub)))
        {
      %>
      <bean:message key="executeadd.title.labAgent" />
      <%
        }
      %>
      <input type="hidden" name="executecontentuserid" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
      <input type="hidden" name="executecontentid" value="<%=tawwpExecuteContentVO.getId()%>">
      <input type="hidden" name="fileFlag" value="<%=tawwpExecuteContentVO.getFileFlag()%>">
    </td>
  </tr>
  <tr>
    <td width="100" class="label"><bean:message key="executeadd.title.formStartDate" /></td>
    <td width="310">
      <%=tawwpExecuteContentVO.getStartDate().substring(0,10)%>
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
      <bean:message key="executeadd.title.formCurrentDate" />
    </td>
    <td width="310">
      <INPUT type="textoggleDatePickert" size="10" name="crtime" readonly value="<%=TawwpUtil.getCurrentDateTime()%>">
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
      <bean:message key="executeadd.title.formContent" />
    </td>
    <td width="310">
      <textarea name="content" rows="5" cols="40" class="textarea"><%=tawwpExecuteContentVO.getContent()%></textarea>
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
     <bean:message key="executeadd.title.formRemark" />
    </td>
    <td width="310">
      <textarea name="remark" rows="5" cols="40" class="textarea"></textarea>
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
      <bean:message key="executeadd.title.formForm" />
    </td>
    <td width="310">
     <%
      if("".equals(StaticMethod.null2String(tawwpExecuteContentVO.getFormDataId()))){
        String formDataId = TawwpUtil.getCurrentDateTime("yyyyMMddHHmmss");
    %>

      <a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=edit&window=new&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
      <input type="hidden" name="formdataid" value="<%=formDataId%>">
    <%
      }else{
    %>
    	<a href="javascript:onAddons('../tawwpaddons/addonsread.do?action=edit&window=new&executeId=<%=tawwpExecuteContentVO.getId() %>&reaction=/tawwpexecute/redirection.jsp');"><%=tawwpExecuteContentVO.getFormName()%></a>
      <input type="hidden" name="formdataid" value="<%=tawwpExecuteContentVO.getFormDataId()%>">
    <%
      }
    %>
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
      <bean:message key="executeadd.title.formFileName" />
      <%if(StaticMethod.null2String(tawwpExecuteContentVO.getFileFlag()).equals("1")){%>
      <font color='red'>*</font>
      <%} %>
    </td>
    <td width="310">
        <span id="showLink"></span>
        <input type="hidden" name="filename" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
        <input type="button" name="count" class="button" onclick="javascript:onFile(filename,count,executecontentid,executecontentuserid,cruser,stubuser,'showLink');" value="<%=tawwpExecuteContentVO.getFileCount()%>"><bean:message key="executeadd.title.labFile" />
    </td>
  </tr>
 <tr>
    <td width="100" class="label">
       是否正常
    </td>
    <td width="310">
      	<eoms:dict key="dict-workplan" dictId="normalFlag" beanId="selectXML" defaultId="<%=tawwpExecuteContentVO.getNormalFlag()%>"  isQuery="false"  selectId="normalFlag"  />
    </td>
   </tr> 
  <tr>
   
     <td width="100" class="label">
       执行帮助
    </td>
    <td width="310">
      <%=StaticMethod.nullObject2String(tawwpExecuteContentVO.getExtendremark()) %>
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
       指导文件
    </td>
    <td width="310">
          <%
          List list1 = null;
          if(!StaticMethod.null2String(tawwpExecuteContentVO.getAccessories()).equals("")){
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
  </tr>
  <tr>
    <td width="410" colspan="2">
      <input type="hidden" name="cruser" value="<%=userId%>">
      <input type="hidden" name="stubuser" value="<%=stubUser%>">
      <input type="hidden" name="monthplanid" value="<%=monthPlanId%>">
      <input type="hidden" name="starTime" value="<%=starTime%>">
      <input type="hidden" name="intime" value="<%=intime%>">
    </td>
  </tr>
</table>
<br>
<%if(tawwpExecuteContentVO.getStartDate().substring(0,10).equals(TawwpUtil.getCurrentDateTime().substring(0,10))){ %>
<input type="button" value="<bean:message key="executeadd.title.btnSubmit" />" name="B1" class="submit" onclick="javascript:onSubmit();">
<%
  if(tawwpExecuteContentVO.getCommand() != null && !tawwpExecuteContentVO.getCommand().equals("")){
%>
    <input type="button" value="<bean:message key="executeadd.title.btnAutoLog" />" name="B1" Onclick="submitform();" class="button">
<%
  }
%>    
<%} %>
 
<INPUT type=button value="<bean:message key="executeadd.title.btnBack" />"  name=b2 Onclick="GoBack();"  class="button">


<!--  body end  -->
</form>
<%@ include file="/common/footer_eoms.jsp"%>

