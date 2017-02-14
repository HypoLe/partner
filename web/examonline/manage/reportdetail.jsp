<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.*,java.util.*,java.io.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
session.setAttribute("importId",request.getParameter("importId"));
%>

<style type="text/css">
	.title {width:150px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;}
	*{margin:0;padding:0; font-family:"微软雅黑";}
	.table_1{margin:4px;}
	.tltle_bg{background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fff;}
	.inter{background:url(<%=basePath%>examonline/manage/images/table_bg.gif) repeat-x; color:#9d9d9f;line-height:22px; font-size:12px;padding-

left:15px;}
	.table_2{background:#d7e1ed; border-top:1px solid #a2b3cf; border-bottom:1px solid #a2b3cf; line-height:27px;padding-left:15px;font-

size:12px;color:#546c8e;}
	.txt_input{background:#f0f1f6; border:1px solid #c4ceda;height:19px;width:60%;}
	.a_table_1,.a_table_2{ border-top:1px solid #fff; border-bottom:1px solid #d2e2ef; line-height:27px;color:#546c8e;padding-left:5px;font-size:12px;}
	.a_table_2{background:#e9eef2; }
	.a_table_1{background:#f1f1f1;}
	.sel_input{height:18px;border:1px solid #c5cdda; width:60%; line-height:18px;font-size:12px;}
	.table_3{height:51px; border-top:1px solid #fff; border-bottom:1px solid #fff; background:#f5f5f5;}
	.table_4{background:url(<%=basePath%>examonline/manage/images/table_bg2.gif) repeat-x;height:20px;}
	.datalist{border-bottom:1px solid #aebdd4;}
	.datalist th{background:url(<%=basePath%>examonline/manage/images/table_bg3.gif) repeat-x;color:#546c8e;border-left:1px solid #fff; border-right:1px 

solid #a1a7bd; font-size:12px; font-weight:normal;}
	.datalist td{border-top:1px solid #aebdd4; padding-left:5px; font-size:12px; line-height:22px;}
	.datalist tr {background-color:expression((this.sectionRowIndex%2==0)?"#fff":"#f0f1f6")}
	.datalist tr:hover, .datalist tr.altrow{background-color:#d7e1ed;/* 动态变色 */}
	.page_style{background:url(<%=basePath%>examonline/manage/images/table_bg4.gif) repeat-x;height:27px; font-size:12px; padding-left:20px;}
	
	/*-- button 风格 begin ---*/
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_14,.add_btn_15{height:21px;border:0;font-size:12px;font-family:"宋

体";line-height:21px;padding-left:3px;}
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06{color:#dbffff;}
		.add_btn_14,.add_btn_15{color:#eee;}
		.add_btn_02{background:url(<%=basePath%>examonline/manage/img/pic_w2.png) no-repeat;width:52px;}
		.add_btn_03{background:url(<%=basePath%>examonline/manage/img/pic_w3.png) no-repeat;width:65px;}
		.add_btn_04{background:url(<%=basePath%>examonline/manage/img/pic_w4.png) no-repeat;width:77px;}
		.add_btn_05{background:url(<%=basePath%>examonline/manage/img/pic_w5.png) no-repeat;width:90px;}
		.add_btn_06{background:url(<%=basePath%>examonline/manage/img/pic_w6.png) no-repeat;width:101px;}
		.add_btn_14{background:url(<%=basePath%>examonline/manage/img/pic_1w4.png) no-repeat;width:78px;}
		.add_btn_15{background:url(<%=basePath%>examonline/manage/img/pic_1w5.png) no-repeat;width:87px;}
	/*-- button 风格 end ---*/
</style>

<html>
<head>
<title>
题库管理
</title>
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
      form.submit();
      //return true;
    }
    return false;
    //document.forms[0].submit();
  }
function ret()
{
	window.location.href="<%=basePath%>examonline/report.do";
}
function view(image){
        var win;
        win=window.open("/eomsMain/examonline/manage/view.jsp?fileName="+image,"图片显示","height=350,width=350,left=0,top=350,resizable=yes,scrollbars=yes,status=no");
}
</script>

<%
  String importId = StaticMethod.null2String(request.getParameter("importId"));
  String urlM = "modifySub.do";
  //String urlD = "deleteSub.do";
  int i = 0;
%>
<body bgcolor="#ffffff" >

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>题库管理</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<form action="${pageContext.request.contextPath}/examonline/physicalDelSub.do">
<input type="hidden" name="checkSel" />
<input type="hidden" name="importId"  value="<%=importId%>"/>
<table width="100%"  id="order"  cellspacing="1" cellpadding="1" class="datalist" align=center border="0">
  <tr>
   <td width="5%" class="a_table_2" align="center">序号</td>
    <td width="76%" align="center" nowrap class="a_table_2">
     题干    </td>
    <td class="a_table_2" align="center" width="15%">
      修改    </td>
  </tr>
  
      <logic:iterate id="onlineWarehouse" name="SUBLIST" type="com.boco.eoms.examonline.model.ExamWarehouse"> 
      <%
  		String urlImage = StaticMethod.getGBString(StaticMethod.null2String(onlineWarehouse.getImage()));
  		java.util.HashMap map = new java.util.HashMap();
  		map.put( "subId" , onlineWarehouse.getSubId());
  		if(onlineWarehouse.getContype() == 4){
  			map.put( "contype" , Integer.valueOf(4));
  		}else if(onlineWarehouse.getContype() == 5){
  			map.put( "contype" , Integer.valueOf(5));
  		}
  		pageContext.setAttribute( "map" , map );
	  %>
      <tr > 
        <td nowrap > <input type="checkbox" name="<%=onlineWarehouse.getSubId()%>">          <%=++i%> </td>
        <td width="76%" align="left"> <bean:write name="onlineWarehouse" property="title"/> 
        
        <!-- 
        <td width="6%" align="left"> <logic:equal name="onlineWarehouse" property="issueType" value="1"> 
          学习 </logic:equal> <logic:equal name="onlineWarehouse" property="issueType" value="2"> 
          考试 </logic:equal> </td>
         -->
         <logic:notEmpty name="onlineWarehouse" property="image"> 
        	<img src="./manage/image/bottom/an_xs.gif" border="0" onClick="view('<%=urlImage%>')">
         </logic:notEmpty> 
        
        </td>
        <td width="15%" align="center"> 
        	<html:link href="<%=urlM%>" name="map"> 
        		<img src="./manage/image/bottom/an_bj.gif" border="0"> 
        	</html:link> 
        </td>
      </tr>
      </logic:iterate> 
    </table>
  </div>
</center>
<br>
<center>
  <input name="button" type="button" class="add_btn_02"  value=" 删除" onclick="return del()" />
  <input name="button" type="button" class="add_btn_02"  value=" 返回" onclick="ret()" />
</center>
</form>
</body>
</html>

