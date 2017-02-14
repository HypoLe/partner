<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" import="java.util.*,java.lang.*, org.apache.struts.util.*"%>
<%@ page import ="com.boco.eoms.workplan.vo.TawwpNetVO"%>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>

<%
  String yearPlanId = (String)request.getAttribute("yearplanid");
  String monthFlag = (String)request.getAttribute("monthid");
  TawwpNetVO tawwpNetVO = null;
%>

<SCRIPT LANGUAGE=javascript>
function GoBack()
{
  window.history.back();
}

function sent_room_user()
{
 if(confirm("确认生成所选月份月计划？"))
    {   
        var status=false; 
        var boxlength= document.getElementsByName("months").length;
       
        for(var i=0;i<boxlength;i++){
        	if(document.getElementsByName("months")[i].checked){
        	   status=true;
        	   break;
        	}
        }
        var temp = "";
    	document.forms[0].netids.value = ""
    	var sel_sprlen=document.forms[0].sOper_O.options.length-1;
    		if (sel_sprlen>= 0){
    			document.forms[0].netids.value =  document.forms[0].sOper_O.options[0].value;
      			for(j=1;j<=sel_sprlen;j++){
              		document.forms[0].netids.value = document.forms[0].netids.value + "," + document.forms[0].sOper_O.options[j].value;
        		}
    		}
    		temp = document.forms[0].netids.value;
		  if(!status){
		    alert("请选择月份");
		    return false;
		  }
		  if(temp==""){
		    alert("<bean:message key="netselect.tawwpmonth.warnNoSelect" />");
		    return false;
		  }
		  document.form1.action="monthadd.do";
		  document.form1.submit();
    	 
    } 
 
}
function checkAll(){
 
	var e=document.getElementById("monthsAll"); 
	var cbxList = document.getElementsByName("months");
	for(var i=0;i<cbxList.length;i++){
       cbxList[i].checked=e.checked;
   }
}
</SCRIPT>

<form  name="form1" id="form1" method="post" >

<br>

<table cellSpacing="0" cellPadding="0" border="0">
<caption><bean:message key="netselect.tawwpmonth.formTitle" /></caption>
  <tr>
    <td>
    <bean:message key="netselect.tawwpmonth.labSelectMore" />
    </td>
  </tr>
  <tr>
    <td>
	    <table width="390" cellspacing="1" cellpadding="1" border="0">
	     
	     <tr height="50px">
	     <td colspan=3>月份: <input type="checkbox" mane="monthsAll" value="" onclick='checkAll()' id="monthsAll" />全选
 
	     </td>
	     </tr> 
	     <tr height="50px">
	     <td colspan=3> 
<%
	        int month = StaticMethod.null2int(monthFlag);
	        for(int i=month;i<=12;i++){
	           if(month<7){
	           	if(i==month + 6){
	             out.print("<br>");
	           	}
	           }
	           out.print("<input type=checkbox name=months value='"+i+"'/>"+i);%>&#26376;
<%   		}
%>
	     </td>
	     </tr>
	      <tr height="50px">
	        <td colspan="3" align="center">
	          <bean:message key="netselect.tawwpmonth.formNet" />
	        </td>
	     </tr>
	     <tr>
	       <td width="40%" align="center">
	
	       <%
	         List netVOList = new ArrayList();
	         if(request.getAttribute("netvolist")!=null){
	         	netVOList = (ArrayList)request.getAttribute("netvolist");
	         }
	       %>
	
	         <b><bean:message key="netselect.tawwpmonth.formNetList" /></b>
	         <select size=15 name="sOper" style="width:150" multiple="true" ondblclick=javascript:add_RoomTrue();>
	         <%
		           for (int i=0;i<netVOList.size();i++)
		           {
		         %>
		
		         <option value="<%=((TawwpNetVO)netVOList.get(i)).getId()%>">
		         <%=((TawwpNetVO)netVOList.get(i)).getName()%>
		         </option>
		
		         <%
		          }
	           
	         %>
	         </select>
	       </td>
	       <td width="20%" align="center" valign = "top">
	         <br>
	         <br>
	         <br>
	         <input type="button" name="btnAddItem" value="<bean:message key="label.add1"/>" onclick=javascript:add_MoreTrue(); class="button">
	         <p>
	         <input type="button" name="btnDelItem" value="<bean:message key="label.remove1"/>" onclick=javascript:del_MoreTrue(); class="button">
	         <p>
	         <input type="button" name="btnAddAll" value="<bean:message key="label.alladd"/>" onclick=javascript:add_all_RoomTrue(); class="button">
	         <p>
	         <input type="button" name="btnDelAll" value="<bean:message key="label.alldel"/>" onclick=javascript:del_all_RoomTrue(); class="button">
	       </td>
	       <td width="40%" align="center">
	       <%
	         List netChangeNetList = new ArrayList();
	         if(request.getAttribute("netChangeNetList")!=null){
	         	netChangeNetList = (ArrayList)request.getAttribute("netChangeNetList");
	         }
	       %>
	         <b>已选网元</b>
	         <select size=15 name="sOper_O" style="width:150"  multiple="true" ondblclick=javascript:del_RoomTrue();>
	         <%
		           for (int i=0;i<netChangeNetList.size();i++)
		           {
		         %>
		
		         <option value="<%=((TawwpNetVO)netChangeNetList.get(i)).getId()%>">
		         <%=((TawwpNetVO)netChangeNetList.get(i)).getName()%>
		         </option>
		
		         <%
		          }
	           
	         %>
	         </select>
	       </td>
	     </tr>
	     
	 	</table>
       </td>
     </tr>
</table>
<br>

<input type="hidden" size = 390 name = "netids" value = "">
<input type="hidden" size = 390 name = "flag" value = "0">
<input type="hidden" size = 390 name = "yearplanid" value = "<%=yearPlanId%>">
<input type="hidden" size = 390 name = "monthid" value = "<%=monthFlag%>">
<input type="button" name="save" value="<bean:message key="label.save"/>" onclick="sent_room_user();" class="button">
<input type="button" value="<bean:message key="netselect.tawwpmonth.btnBack" />" name="B1" class="button" onclick="javascript:GoBack();">

</form>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
var src = document.forms[0].sOper;
var std = document.forms[0].sOper_O;

function add_MoreTrue(){
	$A(src.options).each(function(o){
		if(o.selected && !eoms.form.Options.findValue(std, o.value)){
			eoms.form.Options.add(std, o.text, o.value);
		}		
	});
}
function add_all_RoomTrue(){
	$A(src.options).each(function(o){
		if(!eoms.form.Options.findValue(std, o.value)){
			eoms.form.Options.add(std, o.text, o.value);
		}		
	});
}

function del_MoreTrue(){
    for(var i=(std.options.length-1);i>=0;i--){
        var o=std.options[i];
        if(o.selected){std.options[i] = null;}
    }
}

function del_all_RoomTrue(){
  	std.length = 0;
}
</script>	

