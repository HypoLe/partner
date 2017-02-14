<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import ="java.util.Hashtable"%>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.Enumeration"%>
<%@ page import ="com.boco.eoms.workplan.util.TawwpStaticVariable"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpAddonsTableVO"%>

<script language="javascript">

function SubmitCheck(){

  frmReg = document.tawwpitemadd; 
  if(frmReg.name.value ==''){
    alert("<bean:message key="itemadd.tawwpmodel.warnInputName" />");
    return false;
  }else if(frmReg.cycle.value==''){
    alert("请选择周期");
  	 return false;
  }
  if(frmReg.remark.value.length>200)
  {
    alert("执行帮助不能超过200个字符！");
    frmReg.remark.focus();
    return false;
  }
  return true; 
}

</script>
<script language="javascript">
var onecount;
onecount=0;

subcat = new Array();
<%
int cont=0;
for(int i=1;i<13;i++){
cont++;
%>
subcat[<%=cont%>] = new Array("<bean:message key="itemadd.tawwpyear.warnNumber" /><%=i%><bean:message key="itemadd.tawwpyear.warnMonth" />","7","<%=i%>");
<%}
for(int i=1;i<7;i++){
cont++;
%>
subcat[<%=cont%>] = new Array("<bean:message key="itemadd.tawwpyear.warnNumber" /><%=i%><bean:message key="itemadd.tawwpyear.warnMonth" />","6","<%=i%>");
<%}for(int i=1;i<5;i++){
cont++;
%>
subcat[<%=cont%>] = new Array("<bean:message key="itemadd.tawwpyear.warnNumber" /><%=i%><bean:message key="itemadd.tawwpyear.warnMonth" />","9","<%=i%>");
<%}for(int i=1;i<4;i++){
cont++;
%>
subcat[<%=cont%>] = new Array("<bean:message key="itemadd.tawwpyear.warnNumber" /><%=i%><bean:message key="itemadd.tawwpyear.warnMonth" />","5","<%=i%>");
<%}
for(int i=1;i<3;i++){
cont++;
%>
subcat[<%=cont%>] = new Array("<bean:message key="itemadd.tawwpyear.warnNumber" /><%=i%><bean:message key="itemadd.tawwpyear.warnMonth" />","8","<%=i%>");
<%}
%>
onecount=<%=cont%>;
function changecycle(locationid){
if(locationid<5){
document.tawwpitemadd.monthFlag.style.display="none";
}else{
document.tawwpitemadd.monthFlag.style.display="block";
}
  document.tawwpitemadd.monthFlag.length = 0;
  var locationid=locationid;
  var i;

if(locationid==2){
    eoms.$('executeDay').show();
}else{
    eoms.$('executeDay').hide();
}

  for (i=1;i <= onecount; i++){
    if (subcat[i][1] == locationid){
      document.tawwpitemadd.monthFlag.options[document.tawwpitemadd.monthFlag.length] = new Option(subcat[i][0], subcat[i][2]);
    }
  }
}

//-->
</script>
<%
Hashtable hashtable = TawwpStaticVariable.ADDONS_RANK;
Enumeration enumeration = null;
int count = hashtable.size();
System.out.println("hashtable = " + hashtable.size());
if(hashtable != null){
  enumeration = hashtable.keys();
}

ArrayList addonsList=new ArrayList();
if(request.getAttribute("addonslist")!=null){
	addonsList=(ArrayList)request.getAttribute("addonslist");
}

TawwpAddonsTableVO tawwpAddonsTableVO = null;
String id = null;
%>
<form name="tawwpitemadd" method="post" action="../tawwpmodel/itemsave.do?modelplanid=<%=request.getParameter("modelplanid")%>" onsubmit='return SubmitCheck()'>
  <table class="formTable" >
    <caption><bean:message key="itemadd.tawwpmodel.formTitle" /></caption> 
    
    <tr>
      <td class="label">
       作业项目
      </td>
      <td width="400">
        <input type="text" name="name" size="40" class="text">
      </td>
    </tr>
    
    
    <tr>
      <td class="label">
       业务类型
      </td>
      <td width="400">
        <input type="text" name="botype" size="40" class="text">
      </td>
    </tr>
    <tr>
      <td class="label">
       执行单位级别
      </td>
      <td width="400">
        <input type="text" name="executedeptlevel" size="40" class="text">
      </td>
    </tr>
    <tr>
      <td class="label">
       适用说明
      </td>
      <td width="400">
        <input type="text" name="appdesc" size="40" class="text">
      </td>
    </tr>
    
    
    <tr>
      <td class="label">
       执行周期
       
      </td>
      <td width="400">
       <eoms:dict key="dict-workplan" dictId="cycle" isQuery="false" defaultId="1" onchange="changecycle(document.tawwpitemadd.cycle.options[document.tawwpitemadd.cycle.selectedIndex].value)" selectId="cycle" beanId="selectXML"/>
       	<br>
       	<select  class="select" name="executeDay" id="executeDay" style="display:none">
         <option  value=''>请选择</option>
         <option  value='1'>周一</option>
         <option  value='2'>周二</option>
         <option  value='3'>周三</option>
         <option  value='4'>周四</option>
         <option  value='5'>周五</option>
         <option  value='6'>周六</option>
         <option  value='7'>周日</option>
        </select>
        <select size="1" name="monthFlag" class="text" style="display:none">
        </select>
      </td> 
    </tr>
    <tr>
      <td class="label">
        <bean:message key="itemadd.tawwpmodel.formDefault" />
      </td>
      <td width="400">
        <input type="text" name="format" size="40" class="text">
      </td>
    </tr>
    
        <tr>
      <td class="label">
        任务id
      </td>
      <td width="400">
        <input type="text" name="taskid" size="40" class="text">
      </td>
    </tr>
    
    <tr>
      <td class="label">
         <bean:message key="itemadd.tawwpmodel.formHoliday" />
      </td>
      <td width="400">
        <input type=radio name='isHoliday' value='0' checked >是<input type=radio name='isHoliday' value='1'>否
      </td>
    </tr>
    <tr>
      <td class="label">
         <bean:message key="itemadd.tawwpmodel.formWeekend" />
      </td>
      <td width="400">
        <input type=radio name='isWeekend' value='0' checked >是<input type=radio name='isWeekend' value='1' >否
      </td>
    </tr>
    <tr>
       <td class="label">
        记录模版
      </td>
      <td width="400">
       <select name='formid' value='0' class="select">
         <option value='0'>选择记录模版</option>
          <%
         for(int j=0;j<addonsList.size();j++){
           tawwpAddonsTableVO=(TawwpAddonsTableVO)addonsList.get(j);
         %>
         <option value='<%=tawwpAddonsTableVO.getId()%>'><%=tawwpAddonsTableVO.getName()%></option>
         <%
         }
         %>
        </select>
      </td>
    </tr>   
     <tr>
      <td class="label">
         是否必须上传附件
      </td>
      <td width="400">
        <input type=radio name='fileFlag' value='1' >是<input type=radio name='fileFlag' value='0' checked >否
      </td>
    </tr>
    
    <tr>
       <td class="label">
       	执行帮助
      </td>
      <td>
        <textarea name="remark" class="textarea max"></textarea>
      </td>
    </tr>
     <tr>
       <td class="label">
       	备注
      </td>
      <td>
        <textarea name="note" class="textarea max"></textarea>
      </td>
    </tr>
    <tr>
      <td class="label">
       	指导文件
      </td>
      <td>
        <eoms:attachment idList="" idField="accessories" appCode="workplan"/>
      </td>
    </tr>
    
    <tr>
      <td colspan='2'>
        <input type="submit" value="<bean:message key="itemadd.tawwpmodel.btnSubmit" />" name="B1"  Class="submit">
        <input type="button" value="<bean:message key="itemadd.tawwpmodel.btnBack" />" onclick="javascript:window.history.back();" class="button">
      </td>
    </tr>
  </table>
</form>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
function filter(el){
  if(el.options[el.selectedIndex].value==2){
    eoms.$('executeDay').show();
  }
  else{
    eoms.$('executeDay').hide();
  }
}
</script>


