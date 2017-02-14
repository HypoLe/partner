<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.*,com.boco.eoms.common.tree.RelativeDrop,
com.boco.eoms.examonline.model.ExamConfig,"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html:html>
<head>
<title>
考试试题发布配置页面
</title>
<link rel="stylesheet" type="text/css" href="/eoms35/styles/default/theme.css" />



<script language="javascript">
var User = new Array;
<%
  RelativeDrop rel = new RelativeDrop();
  String users = rel.strRelativeDrop("");
  out.println(users);
%>
  
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/scripts/base/eoms.js"></script>
  
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/default/theme.css" />
  <%@ include file="/common/header.jsp"%>
</script>
<script language="text/javascript" src="<%=path %>/css/checkform.js"></script>
<script language="text/javaScript" src="<%=path %>/css/table_calendar.js"></script>

</head>

<eoms:DictType typeName="Specialty"/>
<eoms:DictType typeName="Manufacturer"/>

<body >

<SCRIPT language=javascript>
<!--
//以下四行用于日历显示
var outObject;
var old_dd = null;
writeCalender();
bltSetDay(bltTheYear,bltTheMonth);
//-->
function selectDept(){
        var form = document.forms[0];
        var deptId = form.deptLabel.value;
        var sel_sprlen = form.deptLabel.options.length-1;
        var j=0;
        for(j=sel_sprlen;j>=0;j--)
        {
             form.userLabel.options[j]=null;
        }

         var m=0;
         form.userLabel.options[m]=new Option("","");
         if (deptId != "0")
         {
            var i;
            if (User[deptId]){
            k=User[deptId].length;
            for (i=0;i*2<k;i++)
            {
             var tempoption=new Option(User[deptId][i*2],User[deptId][i*2+1]);
             form.userLabel.options[m++]=tempoption;
            }
            }
         }
  }
  function select(){
  }
  function doselect(){
  var acontv;
    var form = document.forms[0];
    var valueString = "";
    valueString  = form.specialtySel[form.specialtySel.selectedIndex].text;
    valueString = valueString + ">" + form.manufacturerSel[form.manufacturerSel.selectedIndex].text;
    valueString = valueString + ">" + form.difficulty[form.difficulty.selectedIndex].text;
     
                              valueString = valueString+ ">" + form.radio.value;
                             
                             valueString = valueString + ":" + form.multiple.value;
                             valueString = valueString + ":" + form.judge.value;
   
    form.Value.value = valueString;
    var flag = false;
    if ( form.Value.value != null && form.Value.value != "" ){
      for(var i = 0 ; i < form.studySel.options.length ; i++){
        if (form.studySel.options[i].text == form.Value.value)
          flag = true;
      }
      if(!flag ){
      if(isNaN(acontv))
      acontv=0;
      var contv=parseInt(form.radio.value)*2+parseInt(form.multiple.value)*4+parseInt(form.judge.value)+parseInt(acontv);
      if(contv<100)
      alert("已经选择"+contv+"分题目，还差"+(100-contv));
      else if(contv==100)
      alert("已经选足100分");
      else if(contv>100)
      {
      alert("所选择题分数已经超出"+(contv-100)+"分，请减掉超出部分");
      return;
      }
        var text = new Option(form.Value.value);
        var SelString = form.specialtySel.value
                  + ">" + form.manufacturerSel.value
                  + ">" + form.difficulty.value  
                  + ">" +   form.radio.value
                  + ":" +  form.multiple.value 
                  + ":" + form.judge.value;
        text.value = SelString;
        form.studySel.options[form.studySel.options.length]=text;
      }
      else
        alert("所选项重复");
    }
  }

  function deleteSel(){
    var form = document.forms[0];
    form.studySel.options[form.studySel.selectedIndex] = null;
  }

  function doUserSelect(){
      var form = document.forms[0];
      var flag = false;
      var userSel = form.userLabel.options[form.userLabel.selectedIndex].text;
      for(var i = 0 ; i < form.testerSel.options.length ; i++){
        if (form.testerSel.options[i].text == userSel)
          flag = true;
      }
      if( !flag ){
        var text = new Option(userSel);
        text.value = form.userLabel.value;
        form.testerSel.options[form.testerSel.options.length]=text;
      }
      else
        alert("所选项重复");
  }

  function deleteUserSel(){
    var form = document.forms[0];
    form.testerSel.options[form.testerSel.selectedIndex] = null;
  }

  function confirm(){
    var form = document.forms[0];
    if (form.studySel.options.length == 0){
      alert("选择类型为空");
      return false;
    }
    //返回类型typeSel为 8>2>3>1:1:1:1;4>3>2>1:2:2:1 的形式
    for(var i = 0; i < form.studySel.options.length; i++){
      if (i == 0)
        form.typeSel.value = form.studySel[i].value;
      else
        form.typeSel.value = form.typeSel.value + ";" + form.studySel[i].value;
    }
    //返回类型testers为 ";" 分隔的形式
    for(var i = 0; i < form.testerSel.options.length; i++){
      if (i == 0)
        form.testers.value = form.testerSel[i].value;
      else
        form.testers.value = form.testers.value + ";" + form.testerSel[i].value;
    }

    if (!checkLength(form.testers,1,2000)) return false;
    if (!checkLength(form.typeSel,1,1000)) return false;
    if (!checkLength(form.starttime,1,20)) return false;
    if (!checkLength(form.endtime,1,20)) return false;
    if (!checkLength(form.title,1,255)) return false;
  }
  function designate(isid)
    {
        var _sHeight = 600;
        var _sWidth = 820;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
      var url='<%=basePath%>examonline/designate.do?issueid='+isid;
      var chkk= window.showModalDialog(url,window,sFeatures);
         //if(chkk){
          //  window.location.reload();
        }
    }
</SCRIPT>
<html:form action="/configSubmit" method="post">
<input type="hidden" name="Value" >
<html:hidden property="typeSel" title="已选类别"/>
<html:hidden property="testers" title="已选人员"/>
<table width="100%"    cellspacing="1" cellpadding="1" class="formTable" align=center border="0">
  <tr>
    <td width="100%" align="center" class="table_title">
  考试试题发布配置页面    </td>
  </tr>
</table>
<table class="table resourceAssessTable" cellpadding="1" cellspacing="1" width="95%" border="0">
  <tr class="tr_show">
    <td class="td_label">
      标题<font color="red">*</font>    </td>
    <td>
      <html:text property="title" style="width:400" title="标题"/>    </td>
  </tr>
  <tr class="tr_show">
    <td align="center" rowspan="2" class="td_label">
      <p>
        专业
        <html:select property="specialtySel" style="width:120" value="1" onchange="select();">
          <html:options collection="Specialty"  property="value" labelProperty="label"/>
        </html:select>
      </p>
      <p>
        厂家
        <html:select property="manufacturerSel" style="width:120" value="6" onclick="select();">
          <html:options collection="Manufacturer"  property="value" labelProperty="label"/>
        </html:select>
      </p>
      <p>
       
      
        易难度
        <select name="difficulty"  style="width:120">
<option value="1">初级</option>
<option value="2">中级</option>
</select>
      </p>
      
      
        单选题
        <input type="text" name="radio" id="radio"/>
       
      </p>
        多选题
         <input type="text" name="multiple"  id="multiple"/>
       
      </p>
        判断题
         <input type="text" name="judge"  id="judge"/>
        
      </p>    </td>
    <td>
      <p>已选类别:<font color="red">*</font></p>
      <html:select size="5" property="studySel" style="width:400" value="0" ondblclick="deleteSel()" title="已选类别">      </html:select>    </td>
  </tr>
  <tr class="tr_show">
    <td >
     <html:button value="选择" property="add" onclick="doselect()"/>
     <html:button value="删除" property="delete" onclick="deleteSel()"/>    </td>
  </tr>
 

   <tr class="tr_show">
    <td colspan="2" align="right">
      <html:submit value="生成试卷" onclick="return confirm()"/>    </td>
  </tr>
</table>
<table width="100%"    cellspacing="1" cellpadding="1" class="formTable" align=center border="0">
  <tr>
    <th scope="col">试卷名</th>
    <th scope="col">参考单位</th>
    <th scope="col">参考部门</th>
    <th scope="col">参考人数</th>
    <th scope="col">开考时间</th>
    <th scope="col">结束时间</th>
    <th scope="col">&nbsp;</th>
  </tr>
  <%
  List l=(List)request.getAttribute("ExamConfigList");
  for(int i=0;i<l.size();i++)
  {
  ExamConfig ec=(ExamConfig)l.get(i);
  out.print(l.size());
   %>
    
  <tr><td><%=ec.getTitle() %></td>
    <td><%--<%=SystemResource.deptId2Name(ec.getCompanyId()) %>
    <c:set var="array" value="<%=ec.getCompanyId() %>"/>--%>
       <eoms:id2nameDB id="${ec.companyId}" beanId="ItawSystemDictTypeDao"/>
    </td>
    <td><%--<%=SystemResource.deptId2Name(ec.getDeptId()) %>
    <c:set var="array" value="<%=ec.getDeptId() %>"/>--%>
       <eoms:id2nameDB id="${ec.deptId}" beanId="ItawSystemDictTypeDao"/>
    </td>
    <td><%=ec.getTesterCount() %></td>
    <td><%=ec.getStartTime() %></td>
    <td><%=ec.getEndTime() %></td>
    <td><input type="button" onClick="designate(<%=ec.getIssueId()%>)" class="btn"   value="指派参考人"> </td>
    
  </tr>
  <%} %>
 
</table>
<p>&nbsp;</p>
</html:form>
</body>
</html:html>
