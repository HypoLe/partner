<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.*"%>

<% 
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<style type="text/css">
	.title {width:150px; line-height:25px; text-overflow:ellipsis; white-space:nowrap; overflow:hidden;}
	*{margin:0;padding:0; font-family:"微软雅黑";}
	.table_1{margin:4px;}
	.tltle_bg{background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fff;}
	.inter{background:url(<%=basePath%>examonline/manage/images/table_bg.gif) repeat-x; color:#9d9d9f;line-height:22px; font-size:12px;padding-left:15px;}
	.table_2{background:#d7e1ed; border-top:1px solid #a2b3cf; border-bottom:1px solid #a2b3cf; line-height:27px;padding-left:15px;font-size:12px;color:#546c8e;}
	.txt_input{background:#f0f1f6; border:1px solid #c4ceda;height:19px;width:60%;}
	.a_table_1,.a_table_2{ border-top:1px solid #fff; border-bottom:1px solid #d2e2ef; line-height:27px;color:#546c8e;padding-left:5px;font-size:12px;}
	.a_table_2{background:#e9eef2; }
	.a_table_1{background:#f1f1f1;}
	.sel_input{height:18px;border:1px solid #c5cdda; width:60%; line-height:18px;font-size:12px;}
	.table_3{height:51px; border-top:1px solid #fff; border-bottom:1px solid #fff; background:#f5f5f5;}
	.table_4{background:url(<%=basePath%>examonline/manage/images/table_bg2.gif) repeat-x;height:20px;}
	.datalist{border-bottom:1px solid #aebdd4;}
	.datalist th{background:url(<%=basePath%>examonline/manage/images/table_bg3.gif) repeat-x;color:#546c8e;border-left:1px solid #fff; border-right:1px solid #a1a7bd; font-size:12px; font-weight:normal;}
	.datalist td{border-top:1px solid #aebdd4; padding-left:5px; font-size:12px; line-height:22px;}
	.datalist tr {background-color:expression((this.sectionRowIndex%2==0)?"#fff":"#f0f1f6")}
	.datalist tr:hover, .datalist tr.altrow{background-color:#d7e1ed;/* 动态变色 */}
	.page_style{background:url(<%=basePath%>examonline/manage/images/table_bg4.gif) repeat-x;height:27px; font-size:12px; padding-left:20px;}
	
	/*-- button 风格 begin ---*/
		.add_btn_02,.add_btn_03,.add_btn_04,.add_btn_05,.add_btn_06,.add_btn_14,.add_btn_15{height:21px;border:0;font-size:12px;font-family:"宋体";line-height:21px;padding-left:3px;}
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
做题明细
</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/examonline/style.css" />

<script language="JavaScript" >
function view(image){
    var win;
    win=window.open("/eomsMain/examonline/manage/view.jsp?fileName="+image,"图片显示","height=350,width=350,left=0,top=350,resizable=yes,scrollbars=yes,status=no");
}
</script>

</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=request.getContextPath()%>/examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>做题明细</td>
          <td width="27" align="right"><img src="<%=request.getContextPath()%>/examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

	<%
		int subnum=1;
	 %>
<center>
<div id="divTable" style="position: relative; align: center; top: 5px;width:  100%; height:  440; z-index: 1; overflow: auto; overflow-x: hidden">
<table cellpadding="1" cellspacing="1" width="95%" border="0" class="table_show">
<logic:present name="ContentList">
<logic:iterate id="detailqo" name="ContentList" type="com.boco.eoms.examonline.qo.detailQO">
<%
	String myAnswer = detailqo.getAnswer() == null? "" :detailqo.getAnswer(); //参考人真实答题结果
	String displayAnswer = ""; //参考人打乱题号后的答题结果
	String[] options = null;
	List myOptions = new ArrayList();

    if(detailqo.getContype().intValue()!=4 && detailqo.getContype().intValue()!=5){
    	options = detailqo.getOptions().split(";");
    	if(options != null){
    		 for(int i = 0; i < options.length; i++ ){
   			    String comment = options[i].toString();
   			    comment = comment.trim();
   			    if(comment.substring(0,3).indexOf("|") > 0){
   			    	String trueAnswer = comment.substring(2,3); //真实题号
   			    	String[] ansArr = myAnswer.split(";");
   			    	for(int j=0;j<ansArr.length;j++){
   			    		if(trueAnswer.equals(ansArr[j])){
   			    			displayAnswer = displayAnswer + comment.substring(0,1);
   			    		}
   			    	}
   			    	comment = comment.substring(0,1)+comment.substring(3);
   			    	myOptions.add(comment);
   			    }
   			  }
    	}
  }
 
%>
 <tr>
   <td width="85%" bgcolor="#d7e1ed"><img src="<%=request.getContextPath()%>/examonline/manage/images/icon_3.gif" width="15" height="14" /> 题
   <%=subnum++%>
     <bean:write name="detailqo" property="title" scope="page"/>             <!--标题-->
     <logic:notEmpty name="detailqo" property="image">                       <!--图片-->
          <img src="<%=request.getContextPath()%>/images/bottom/an_xs.gif" border="0" onClick="view('<%=detailqo.getImage()%>')">
     </logic:notEmpty>
     <!--主观题-->
     <c:if test="${detailqo.contype==4 || detailqo.contype==5}" var="isQa">
     	 (得分：<bean:write name="detailqo" property="mark"/>   )
         
         <tr align="center" >
		    <td colspan="2" align="left" background="#546c8e">
		      <bean:write name="detailqo" property="qa"/>
		    </td>
		  </tr>
     </c:if>
     
     <!--非主观题-->
     <c:if test="${!isQa}">
     	(
	     <!-- 
	     <bean:write name="detailqo" property="answer"/>                         
	      -->
	      <%=displayAnswer %>
	     <logic:notEqual name="detailqo" property="right" value="0">                <!--对错-->
	       <font color="red">对</font>
	     </logic:notEqual>
	     <logic:equal name="detailqo" property="right" value="0">                <!--对错-->
	       错
	     </logic:equal>
	     )
	     <%
		  for(int n=0;n<myOptions.size();n++){
		  	  String cmt = (String)myOptions.get(n);
		%>
		  <tr align="center" >
		    <td colspan="2" align="left" background="#546c8e">
		      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=cmt%>
		    </td>
		  </tr>
		<%
		  }
		%>
     </c:if>
     
   </td>
   
 </tr>


</logic:iterate>
</logic:present>
</table>
</div>
<br>
	<input name="button" type="button" class="add_btn_02"  value=" 返回" onclick="history.back()" />
</center>

</body>
</html>
