<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
<title>My JSP 'designate.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	  <script type="text/javascript" src="${app}/scripts/base/prototype.js"></script>
<script type="text/javascript">
var num=0;
function doUserSelect(){
      var form = document.forms[0];
      var flag = false;
      var userSel = form.userLabel.options[form.userLabel.selectedIndex].text;
      for(var i = 0 ; i < form.testerSel.options.length ; i++){
        if (form.testerSel.options[i].text == userSel)
          flag = true;
      }
      if( !flag ){
      num=num+1;
        var text = new Option(userSel);
        text.value = form.userLabel.value;
       // alert(text.value);
        form.testerSel.options[form.testerSel.options.length]=text;
      }
      else
        alert("所选项重复");
  }
  function deleteUserSel(){
    var form = document.forms[0];
    form.testerSel.options[form.testerSel.selectedIndex] = null;
     num=num-1;
  }
  
  /**
  *  点击提交按钮执行
  */
  function sub(){
   var form = document.forms[0];
   for(var i = 0; i < form.testerSel.options.length; i++){
      if (i == 0)
        form.testers.value = form.testerSel[i].value;
      else
        form.testers.value = form.testers.value + ";" + form.testerSel[i].value;
    }
  var testerCount=${param.testerCount};
  if(testerCount==num){
     if (confirm("确认要提交？注:提交后,数据不可更改,请慎重!")) {
		  var url="<%=basePath%>examonline/uniontesters.do";
		  var ret;
		  var pars="testers="+$F('testers')+"&deptid="+$F("deptid")+"&issueid="+$F("issueid");
		         var b= new Ajax.Request(url,{method: 'post',
		         asynchronous:false,//必选,以保证在后台程序未完全返回前,前面的线程不先向前执行(即前台js线程要与后台java线程同步,
		         //保证前面调用另一函数去取数据库数据时,当前的线程已经完成事务务提交完成)
		         parameters:pars,
		         onComplete:function(x)
		         {
                    ret=x.responseText;
                 }
            });
		   if(ret==1){
	           window.returnValue =true;
	           window.close();
	       }
      }
  }else{
        alert("本次考试要求参考人数:"+testerCount+",你当前选择人数不匹配,请核对!");
        return;
        }
  }
  
  function updept(o)
		{
		var url="<%=basePath%>examonline/ajaxgetuser.do";
		 var parssf="deptid="+o.value;
		      new Ajax.Updater("userLabel",url,{method:'post',parameters:parssf});
		}
  
</script>
</head>
<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tltle_bg" style="background:url(<%=basePath%>examonline/manage/images/title_bg.gif) repeat-x; font:bold 14px/27px "微软雅黑";color:#fffff;" >
        <tr>
          <td width="25" align="left"><img src="<%=basePath%>examonline/manage/images/title_bg_left.gif" width="20" height="27" /></td>
          <td>指派参考人界面</td>
          <td width="27" align="right"><img src="<%=basePath%>examonline/manage/images/title_bg_right.gif" width="25" height="27" /></td>
        </tr>
</table>

<form action="">
  <div align="center">
    <input type="hidden" name="testers" id="testers">
    <input type="hidden" name="deptid" id="deptid" value="${param.deptid}">
    <input type="hidden" name="issueid" id="issueid" value="${param.issueid }">
    <input type="hidden" name="testerCount" id="testerCount" value="${param.testerCount }" >
  </div>
  <table width="100%"    cellspacing="1" cellpadding="1" align=center border="0">
     <tr class="a_table_2" style="background:#d7e1ed;" >
       <td><div align="center"><img src="<%=basePath%>examonline/manage/images/icon_3.gif" width="15" height="14" /> 部门:
           <select name="deptsel" id="deptsel" onChange="updept(this)">
           <option>--请选择部门--</option>
             <c:forEach items="${depts}" var="dept">
               <option value="${dept.deptId}">${dept.deptName}</option>
             </c:forEach>
             </select>
       </div></td>
     </tr>
     <tr class="a_table_1">
       <td><div align="center"><img src="<%=basePath%>examonline/manage/images/icon_4.gif" width="15" height="14" /> 待选人员:(双击选择人员)
           <select name="userLabel" id="userLabel" size="9" style="width:200" ondblclick="doUserSelect()">
             </select><img src="<%=basePath%>examonline/manage/images/icon_4.gif" width="15" height="14" /> 已选人员:(双击删除人员)
<select size="9" name="testerSel" id="testerSel" style="width:200"  ondblclick="deleteUserSel()">
</select>
       </div></td>
     </tr>
     <tr class="a_table_2" style="background:#d7e1ed;" >
       <td>  <div align="center">
         <input name="button" type="button" class="add_btn_02"  value=" 提交" onclick="sub()"/>
         <input name="button" type="button" class="add_btn_02"  value=" 关闭" onclick="window.close()"/>
       </div></td>
     </tr>
   </table>
   <br>
 
</form>
</body>
</html>
