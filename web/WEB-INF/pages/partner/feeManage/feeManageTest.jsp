<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


<script type="text/javascript"> 

 function addRecord(){
 
        var year=document.getElementById("year").value;
        var month=document.getElementById("month").value;
        if(year==''||month=='') {
        
        alert("请选择需要生成费用的年份和月份！");
        return false;
        }
		if(!confirm("是否要生成该月费用？")){
		   return;
		  }
		 document.getElementById("aFrom").submit();							
}

</script>	



<form action="${app}/partner/feeManage/feeManage.do?method=add"  method="post"  id="aFrom" name="aFrom" > 
<table class="formTable">

<tr>
			  
				<td class="label">年份:</td>
			   <td class="content"><select size='1'
				name='year' id='year' 
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011' >2011年</option>
				<option value='2012' >2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
				<option value='2015'>2015年</option>
			</select></td>
			<td class="label">月份：</td>
			<td class="content"><select size='1' name="month"
				id="month" class='select' >
				<option value=''>请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
			 </tr><tr>
		
		
</table>
<input type="button" value="提交" onclick="addRecord()" />
</form>


<%@ include file="/common/footer_eoms.jsp"%>