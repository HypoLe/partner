<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>

<form action="${app}/partner/feeManage/feeCountManage.do?method=saveFeeCountPrcTmpl"
	method="post" id="filterForm" name="filterForm">

<table class="table">


	<tr>
	
	<td class="label">
				<font color='red'> * </font>模板名称
			</td>
			<td>
				<input type="text" class="text" name="prcTmplNm" id="prcTmplNm"
					alt="allowBlank:false,vtext:'考核指标名不能为空 不能超出25个汉字！',maxLength:25"
					value="${quota.idctNm }" />
			</td>
	
	<td class="label">
				<font color='red'> * </font>专业：
			</td>
		
		<td class="label">
			<select id="majorSelect" name="majorSelect" class="text">


			</select>
		</td>
	
		
	</tr>
	<tr>
	<td class="label">
				备注
			</td>

			<td class="content">
				<textarea name="remark" id="remark" class="text medium"
					alt="allowBlank:true,vtext:'备注 不能超出125个汉字！',maxLength:325"></textarea>
			</td>
	
	</tr>

</table>

					
					 <input type="submit" value="添加详细信息"  />
					 
</form>
<script type="text/javascript">
	window.onload=function(){


	var ajax = createXmlHttpRequest();
	//设置请求（Method,Url）
	ajax.open("GET","${app}/partner/feeManage/feeCountManage.do?method=majorSelect",true);

	ajax.onreadystatechange=function(){
		//判断是否完成请求,并且状态是成功的
		if(ajax.readyState==4 && ajax.status==200){
			var result = ajax.responseText;
			var prv = document.getElementById("majorSelect");
			prv.innerHTML = result;
		
		}
	};
	//第三步发送请求
	ajax.send();
};

	function createXmlHttpRequest(){
	var xmlHttp = null;
	try{
		//Firefox, Opera 8.0+, Safari
	     xmlHttp=new XMLHttpRequest();
	}catch(e){
		//IEIE7.0以下的浏览器以ActiveX组件的方式来创建XMLHttpRequest对象
		 var MSXML = ['MSXML2.XMLHTTP.6.0','MSXML2.XMLHTTP.5.0',
		'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',
		'MSXML2.XMLHTTP','Microsoft.XMLHTTP'];
		 
		 for(var n = 0; n < MSXML.length; n ++){
		    try{
		    	xmlHttp = new ActiveXObject(MSXML[n]);
		      break;
		    }catch(e){
		    }
		  }
	}
	return xmlHttp;
}




	</script>
<%@ include file="/common/footer_eoms.jsp"%>