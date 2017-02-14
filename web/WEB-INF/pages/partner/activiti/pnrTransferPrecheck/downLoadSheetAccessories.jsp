<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
  function download() {
  	var obj = document.getElementById("list");
  	var index = obj.selectedIndex;
  	var text  = obj.options[index].text;
  	var value = obj.options[index].value;
  	
  	if(value!=""){  	

  		if(confirm("确定导出<"+text+">附件信息")){
  		
    	window.location ='${app}/accessories/tawCommonsAccessoriesConfigs.do?method=downloadzipBatch&value='+ value;
    	//window.location.href ='${app}/accessories/tawCommonsAccessoriesConfigs.do?method=downloadzipBatch&value='+ value;
  		//window.location.reload();
  		}
  	}
	
  }
</script>

<table >

<tr>
<td>
<select name="list" id ="list">
<option value="2811">威海</option>
<option value="2813">滨州</option>
<option value="2819">济南</option>
<option value="2823">东营</option>
<option value="2816">临沂</option>
<option value="2827">泰安</option>
<option value="2820">青岛</option>
<option value="2821">淄博</option>
<option value="2822">枣庄</option>
<option value="2824">烟台</option>
<option value="2825">潍坊</option>
<option value="2826">济宁</option>
<option value="2814">德州</option>
<option value="2812">日照</option>
<option value="2817">菏泽</option>
<option value="2815">聊城</option>
<option value="2818">莱芜</option>
</select>
</td>

<td>
-------------------------
</td>
<td>
-------------------------
</td>
<td>
<input type="button" name="button" class="button" value="批量导出" onclick="download()" >
</td>
<tr>
</table>	  	
<div id="hh">目前是选择一个地市，点击“批量导出”，确定后的程序操作：<br>1、后台开始导出，请去后台对应的文件夹下看文件包；<br>2、当前页面立即刷新，不在等待后台回执的压缩包完成；<br>3、请在系统繁忙时，有间隔的进行各个地市附件导出。</div>		