<%@ page contentType="text/html; charset=GB2312" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jstl-core.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/app.tld" prefix="eoms" %>
<%@ page import="com.boco.eoms.common.util.*,java.util.*,java.io.*"%>

<html>
<head>
<title>
题库管理
</title>
<link rel="stylesheet" type="text/css" href="css.css" />
</head>

<script language="javascript">
  function del(){
    if ( confirm("是否确认删除？") ){
      var form = document.forms[0];
      for (var i = 0; i < form.elements.length; i++){
        var obj = form.elements[i];
        if ( obj.type == 'checkbox' ){
          if ( obj.checked ){
              form.checkSel.value = form.checkSel.value + ",'" + obj.name + "'";
            }
        }
      }
      form.checkSel.value = form.checkSel.value.substring(1);
      if( form.checkSel.value == "" || form.checkSel.value == null ){
        alert("请先选择删除项");
        return false;
      }
      return true;
    }
    return false;
  }

function view(image){
        var win;
        win=window.open("/EOMS_J2EE/studyonline/manage/view.jsp?fileName="+image,"图片显示","height=350,width=350,left=0,top=350,resizable=yes,scrollbars=yes,status=no");
}
</script>

<%
  String importId = StaticMethod.null2String(request.getParameter("importId"));
  System.out.println("-----------------:"+importId);
  String urlM = "modifySub.do";
  //String urlD = "deleteSub.do";
  int i = 0;
%>
<body bgcolor="#ffffff" >
<html:form action="physicalDelSub.do" >
<html:hidden property="checkSel" value=""/>
<html:hidden property="importId" value="<%=importId%>"/>
<table cellpadding="1" cellspacing="1" width="200%" align="center" border="0" class="table_show">
  <tr>
    <td width="3%" class="td_label">
      序号
    </td>
    <td class="td_label" align="center" width="24%">
      标题
    </td>
    <td class="td_label" align="center" width="23%">
      选项
    </td>
    <td class="td_label" align="center" width="3%">
      答案
    </td>
    <td class="td_label" align="center" width="20%">
      注解
    </td>
    <td class="td_label" align="center" width="5%">
      专业
    </td>
    <td class="td_label" align="center" width="5%">
      厂家
    </td>
    <td class="td_label" align="center" width="5%">
      设备
    </td>
    <td class="td_label" align="center" width="3%">
      难度
    </td>
    <td class="td_label" align="center" width="3%">
      类型
    </td>
    
    <td class="td_label" align="center" width="3%">
      分值
    </td>
  </tr>
<logic:iterate id="onlineWarehouse" name="SUBLIST" type="com.boco.eoms.examonline.model.ExamWarehouse">
<%
  String urlImage = StaticMethod.getGBString(
      StaticMethod.null2String(onlineWarehouse.getImage()));
%>
  <tr class="tr_show" align="center">
    <td width="3%">
      <%=++i%>
    </td>
    <td align="left" width="24%">
      <bean:write name="onlineWarehouse" property="title"/>
    </td>
    <td align="left" width="23%">
      <%
        String[] options = onlineWarehouse.getOptions().split(";");
        for(int k = 0; k < options.length; k++ )
          out.print("<p>" + options[k] + "</p>");
      %>
    </td>
    <td align="left" width="3%">
      <bean:write name="onlineWarehouse" property="result"/>
    </td>
    <td align="left" width="20%">
      <bean:write name="onlineWarehouse" property="comment"/>
    </td>
    <td align="center" width="5%">
      <bean:write name="onlineWarehouse" property="specialtyName"/>
    </td>
    <td align="center" width="5%">
      <bean:write name="onlineWarehouse" property="manufacturerName"/>
    </td>
    <td align="center" width="5%">
     
    </td>
    <td align="center" width="3%">
      <bean:write name="onlineWarehouse" property="difficultyName"/>
    </td>
    <td width="3%" align="left">
      <logic:equal name="onlineWarehouse" property="issueType" value="1">
        学习
      </logic:equal>
      <logic:equal name="onlineWarehouse" property="issueType" value="2">
        考试
      </logic:equal>
    </td>
    
    <td width="3%">
      <bean:write name="onlineWarehouse" property="value"/>
    </td>
  </tr>
  </logic:iterate>
  </table>
<br>

</html:form>
</body>
</html>


