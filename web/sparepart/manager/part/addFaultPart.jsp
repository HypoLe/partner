
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import ="com.boco.eoms.sparepart.util.TawClassMsgTree"%>
<%@ page import ="com.boco.eoms.db.util.ConnectionPool"%>
<%@ page import ="com.boco.eoms.sparepart.bo.TawStorageBO"%>
<%@ page import ="com.boco.eoms.sparepart.controller.TawSparepartForm"%>
<%@ page import ="javax.servlet.http.HttpServletRequest"%>
<%@ page import="java.util.List,com.boco.eoms.sparepart.model.TawClassMsg,com.boco.eoms.common.util.StaticMethod"%>
<%@ page import ="com.boco.eoms.sparepart.util.TawClassMsgTree"%>
<%@ page import ="com.boco.eoms.db.util.ConnectionPool"%>
<%@ page import ="com.boco.eoms.sparepart.bo.TawStorageBO"%>
<%@ page import ="com.boco.eoms.sparepart.controller.TawSparepartForm"%>
<%@ page import ="javax.servlet.http.HttpServletRequest"%>
<%@ page import="java.util.List,com.boco.eoms.sparepart.model.TawClassMsg,com.boco.eoms.common.util.StaticMethod"%>
<html>
<head>
<%
 TawSparepartForm sparepartForm=(TawSparepartForm)request.getAttribute("sparepart");
 String str =(String)request.getAttribute("StringTree");
 List templist =(List) request.getAttribute("supplier");
 TawClassMsg tawClassMsg = null;
%>
<title>
<bean:message key="sparepart.addnew"/>
</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
<style>
body,select
{
	font-size:9pt;
	font-family:Verdana;
}
select {background-color:#F0F0F0;}
</style>
<script language="JavaScript" src="<%=request.getContextPath()%>/css/area.js"></script>
<SCRIPT LANGUAGE = JavaScript>
var s=["s1","s2","s3"];
var opt0 = ["<%=StaticMethod.null2String(sparepartForm.getDepartment())%>","<%=StaticMethod.null2String(sparepartForm.getNecode())%>","<%=StaticMethod.null2String(sparepartForm.getObjecttype())%>"];
var dsy = new Dsy();
<%=str%>
function setup()
{
	for(i=0;i<s.length-1;i++)
		document.getElementById(s[i]).onchange=new Function("change(dsy,"+(i+1)+",s,opt0)");
	change(dsy,0,s,opt0);
}
function changeType(v){
  document.getElementById("sheettype").style.display = (v==11 || v==12) ? "none" : "block";
  document.getElementById("accessorytype").style.display = (v==11 || v==12) ? "none" : "block";
}
</SCRIPT>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/table_style.css" type="text/css">
</head>
<body background="<%=request.getContextPath()%>/images/bottom/bj_1_1.gif" onload="setup()">
<div align="center">
<br>
<form method="post" action="<%=request.getContextPath()%>/sparepart/part/addFaultPartSubmit.do" name="frm" onsubmit="return checkdata()" enctype="multipart/form-data">
<table border="0" width="80%" cellspacing="0">
    <tr>
      <td class="table_title" align="center">
        <b>${eoms:a2u('故障备件入库')}</b>
      </td>
    </tr>
</table>
<table border="0" width="80%" cellspacing="1" cellpadding="1" align=center>
    <tr class="tr_show_left">
      <td>
        ${eoms:a2u('您当前操作的仓库是：')}<font color="red"><bean:write name="storage_dept_name" scope="session"/></font>&nbsp;&nbsp;${eoms:a2u('的')}<b>&nbsp;&nbsp;<font color="blue"><bean:write name="storage" scope="session"/></font></b>
      </td>
    </tr>
</table>
<table border="0" width="80%" cellspacing="1" cellpadding="1" class="table_show" align=center>
    <tr class="tr_show">
      <td class="clsfth" colspan="3" height="25" align="left">
        <font color="#CC0000">${eoms:a2u('注意：带有 ** 号的栏目是必须填写的，其他的栏目可以不填。')}</font>
      </td>
    </tr>
   <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0"><bean:message key="sparepart.department"/>${eoms:a2u('：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                  <select name="department" id="s1" style="width: 6.8cm;"></select>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0"><bean:message key="sparepart.necode"/>${eoms:a2u('：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                   <select name="necode" id="s2" style="width: 6.8cm;"></select>
                  </td>
    </tr>
     <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('备件类型：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                   <select name="objecttype" id="s3" style="width: 6.8cm;"></select>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('备件名称：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text"size="35" value="<%=StaticMethod.null2String(sparepartForm.getEname())%>"  name="ename">
                  </td>
    </tr>

    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0"><bean:message key="sparepart.supplier"/>：</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                        <select name="supplier" size="1" style="FONT-SIZE: 8pt" style="width: 6.8cm;">
                          <option value=""><bean:message key="supplier.choose"/></option>
                          <%
                             for (int i = 0; i < templist.size(); i++) {
 								                 tawClassMsg = (TawClassMsg)templist.get(i);
                            if(tawClassMsg.getId() == StaticMethod.null2int(sparepartForm.getSupplier())) {
                          %>
                          	<option value="<%=tawClassMsg.getId()%>" selected="selected"><%=tawClassMsg.getCname()%></option>
                            <%} else {%>
                            <option value="<%=tawClassMsg.getId()%>"><%=tawClassMsg.getCname()%></option>
                            <%
                            }
                            }
                            %>
                        </select>
                        <font color="#FF0000">**</font>
                  </td>
    </tr>

    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">${eoms:a2u('备件版本号：')}</td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" value="<%=StaticMethod.null2String(sparepartForm.getProductcode())%>" name="productcode"size="35">
                     <font color="#FF0000">**</font>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0"><bean:message key="sparepart.managecode"/>${eoms:a2u('：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text"size="35" value="<%=StaticMethod.null2String(sparepartForm.getManagecode())%>" name="managecode">
                    <font color="#FF0000">**</font></td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25"><bean:message key="sparepart.serialno"/>${eoms:a2u('：')}</td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" value="<%=StaticMethod.null2String(sparepartForm.getSerialno())%>" name="serialno"size="35">
                     <font color="#FF0000">**</font>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25"><bean:message key="sparepart.operator"/>${eoms:a2u('：')}</td>
                  <td width="70%" height="25" colspan="2">
                    <input size="35" readonly="readonly" value="<%=StaticMethod.null2String(sparepartForm.getOperator())%>" name="operator" />
                  </td>
    </tr>
    <%--
    入库状态：故障
    --%>
    <input type="hidden" value="12" name="state" />
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('备件性能：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                   		<select name="proform" size="1" style="FONT-SIZE: 8pt" style="width: 6.8cm;">
                            <option value="1">${eoms:a2u('正常')}</option>
                            <option value="2">${eoms:a2u('不正常')}</option>
                      </select>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('是否保修：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                   		<select name="warrantyFlag" size="1" style="FONT-SIZE: 8pt" style="width: 6.8cm;">
                            <option value="1">${eoms:a2u('保修内')}</option>
                            <option value="2">${eoms:a2u('保修外')}</option>
                      </select>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('是否停产：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                   		<select name="stopproductFlag" size="1" style="FONT-SIZE: 8pt" style="width: 6.8cm;">
                            <option value="1">${eoms:a2u('未停产')}</option>
                            <option value="2">${eoms:a2u('已停产')}</option>
                      </select>
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('所属工程/合同：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" size="35" value="<%=StaticMethod.null2String(sparepartForm.getContract())%>" name="contract">
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('订购金额：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" size="35" value="<%=StaticMethod.null2String(sparepartForm.getMoney())%>" name="money">
                  </td>
    </tr>
    <tr class="tr_show" id="sheettype">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('工单号：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" size="35" value="<%=StaticMethod.null2String(sparepartForm.getSheetid())%>" name="sheetid" readonly="readonly">
										<input type="submit" name="toquery" value="${eoms:a2u('查询工单号')}" onclick=document.frm.action="../../newworksheet/part/accessory.do" />
                  </td>
    </tr>
    <tr class="tr_show" id="accessorytype">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('附件：')}</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="file" size="35" value="<%=StaticMethod.null2String(sparepartForm.getAccessory())%>" name="theFile" />
                  </td>
    </tr>

    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0">${eoms:a2u('全选')}位置：</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" size="35" value="<%=StaticMethod.null2String(sparepartForm.getPosition())%>" name="position">
                  </td>
    </tr>
    <tr class="tr_show">
                  <td class="clsfth" width="30%" height="25">
                    <p style="margin-top: 0; margin-bottom: 0"><bean:message key="sparepart.note"/>：</p>
                  </td>
                  <td width="70%" height="25" colspan="2">
                    <input type="text" size="35" value="<%=StaticMethod.null2String(sparepartForm.getNote())%>" name="note">
                  </td>
    </tr>
</table>
<INPUT TYPE="hidden" name="differ" >
<table border="0" width="80%" cellspacing="0">
    <tr>
      <td height="32" align="right">
          <input type="submit" value="<bean:message key="label.submit"/>" name="submit" onclick="frm.differ.value=1" class="clsbtn2">
      </td>
    </tr>
</table>

<script language="javascript">
changeType(12);
function checkdata() {
        if ( document.frm.department.value == "" ) {
                alert('${eoms:a2u("请选择备件所属专业！")}');
                document.frm.department.focus();
                return false;
        }
        if ( document.frm.necode.value == "" ) {
                alert('${eoms:a2u("请选择备件网元类型！")}');
                document.frm.necode.focus();
                return false;
        }
        if ( document.frm.supplier.value == "" ) {
                alert('${eoms:a2u("请选择供货商！")}');
                document.frm.supplier.focus();
                return false;
        }
        if ( document.frm.productcode.value == "" ) {
                alert('${eoms:a2u("请输入版本号！")}');
                document.frm.productcode.focus();
                return false;
        }
        if ( document.frm.managecode.value == "" ) {
                alert('${eoms:a2u("请输入备件管理信息编码！")}');
                document.frm.managecode.focus();
                return false;
        }
       if ( document.frm.serialno.value == "" ) {
                alert('${eoms:a2u("请输入备件序列号！")}');
                document.frm.serialno.focus();
                return false;
        }
      return true;
}
</script>
</form>
</div>
</body>
<%@ include file="/common/footer_eoms.jsp"%>

</html>


