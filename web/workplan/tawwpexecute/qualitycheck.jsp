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
  TawwpExecuteContentVO tawwpExecuteContentVO = (TawwpExecuteContentVO)request.getAttribute("tawwpExecuteContent");
  ITawCommonsAccessoriesManager mgr = (ITawCommonsAccessoriesManager) ApplicationContextHolder.getInstance().getBean("ItawCommonsAccessoriesManager");
  String allQuality = StaticMethod.nullObject2String(request.getAttribute("allQuality"));
  
%>
<script language="JavaScript">

function GoBack()
{
  window.history.back()
}

function passCheck(ids)
{

  document.forms[0].action = "../tawwpexecute/qualitypass.do?ids="+ids;
  document.forms[0].submit();
}
function rejectCheck(ids)
{
alert(ids);
  document.forms[0].action = "../tawwpexecute/qualityreject.do?ids='"+ids+"'";
  document.forms[0].submit();
}

</script>

<form name="executecontentform">
<!--  body begin  -->
<br>

<table class="formTable">
  <caption><bean:message key="executeadd.title.formTitle" /></caption>
  <tr>
    <td width="100" class="label"><bean:message key="executeadd.title.formName" /></td>
    <td width="310">
      <%=tawwpExecuteContentVO.getName()%>
      <input type="hidden" name="executecontentuserid" value="<%=tawwpExecuteContentVO.getExecuteContentUserId()%>">
      <input type="hidden" name="executecontentid" value="<%=tawwpExecuteContentVO.getId()%>">
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
      <%=tawwpExecuteContentVO.getContent()%>
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
     <bean:message key="executeadd.title.formRemark" />
    </td>
    <td width="310">
      <%=tawwpExecuteContentVO.getRemark()%>
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
       是否上传附件
    </td>
    <td width="310">
      	<%=tawwpExecuteContentVO.getFileFlagName()%>
    </td>
  </tr>
    <tr>
    <td width="100" class="label">
      <bean:message key="executeadd.title.formFileName" />
    </td>
    <td width="310">
        <div id="file" style="">
        <%
          if(!"".equals(tawwpExecuteContentVO.getFileName())){
            String[] tempStr = tawwpExecuteContentVO.getFileName().split(",");
            for(int j=0; j<tempStr.length; j++){
               String[] tempArray = tempStr[j].split("@");
        %>
        <a href="<%=tempArray[1]%>"><%=tempArray[0]%></a><br>
        <%
            }
          }
        %>
        </div>
        <span id="showLink"></span>
        <input type="hidden" name="filename" dataType="null" value="<%=tawwpExecuteContentVO.getFileName()%>">
    </td>
  </tr>
  <tr>
    <td width="100" class="label">
       是否正常
    </td>
    <td width="310">
      	<%=tawwpExecuteContentVO.getNormalFlagName()%>
    </td>
   </tr> 
   <tr>
     <td width="100" class="label">
       执行帮助
    </td>
    <td width="310">
      <%=StaticMethod.null2String(tawwpExecuteContentVO.getExtendremark()) %>
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
    <td width="100" class="label">
       质检意见
    </td>
    <td width="310">
      <textarea name="device" rows="5" cols="60" class="textarea"></textarea>
    </td>
  </tr>
  <input type="hidden" name="id" id="id" value="<%=tawwpExecuteContentVO.getId()%>">
  <input type="hidden" name="allQuality" id="allQuality" value="<%=allQuality%>">
</table>
<br>
<input type="button" value="质检通过" name="pass" Onclick="passCheck('<%=tawwpExecuteContentVO.getId() %>');" class="button">
<input type="button" value="质检不通过" name="reject" Onclick="rejectCheck('<%=tawwpExecuteContentVO.getId() %>');" class="button">
<!--  body end  -->
</form>
<%@ include file="/common/footer_eoms.jsp"%>

