<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript" src="${app}/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript">
<!-- 
    jQuery.noConflict();
    jQuery(function($){
    //查询条件发生改变
    function checkState(val, index){
		    	var str = "[name='rowsPramters_c']:eq("+index+")";
		     	var $c = $(str);
		    	$c.attr("checked",true).attr("disabled",true);
		    	if(val=="")
		    		$c.attr("checked",false).attr("disabled",false);
		    
		    $("[name='rowsPramters_c']").change();//要显示的列 发生改变		
	    }
    	//地市
    $("#select_city_id").blur(function(){
    	checkState($(this).val(),0);
    });
    	//县区
    $("#select_county_id").blur(function(){
        checkState($(this).val(),1);
    })
    	//巡检组织
    $("#select_deptname_id").blur(function(){
    	 checkState($(this).val(),2);
    })
    	//巡检专业
    $("[name='select_prof_c']").change(function(){
	   	    var str=""; 
		    $("[name='select_prof_c']").each(function(){
                if($(this).attr("checked"))  
                  str+=$(this).val()+",";  
			})
    	 checkState(str,3);
    	 $("#select_prof_hidden").val(str);
    });
    //要显示的列
	    $("[name='rowsPramters_c']").change(function(){
		 		var str="";  
			    $("[name='rowsPramters_c']").each(function(){  
               if($(this).attr("checked"))
			   				 str+=$(this).val()+",";  
			  	}) ; 
			  	str+="skilllevel,"//技能等级类别，默认显示
			  	$("#rowsPramters_hidden").val(str);
	    	});
	  //表单提交
	  $("#statistics_submit").click(function(){
	  	if($("#rowsPramters_hidden").val()==""){
	  		alert("至少选择一个统计项目");
	  		return false;
	  		}
	  }) 
	  
	  //显示报表图像
      var width = '${width}'
    	  if(width!=null||width!=""){
	    var myChart = new FusionCharts("${app}/FusionCharts/StackedColumn3D.swf", "fusionchart", width, "480", "0", "0");
	    myChart.setDataXML("${xml}");
		myChart.render("fusionchart");
    	  }
    })
//-->    
</script>
	<eoms:xbox id="tree2" dataUrl="${app}/xtree.do?method=area" rootId="-1"
		rootText='地市' valueField="hidden_tree2_none" handler="select_city_id" textField="select_city_id"
		checktype="area" single="true">
	</eoms:xbox>
	<eoms:xbox id="tree3" dataUrl="${app}/xtree.do?method=area" rootId="-1"
		rootText='县区' valueField="hidden_tree3_none" handler="select_county_id" textField="select_county_id"
		checktype="area" single="true">
	</eoms:xbox>
	<eoms:xbox id="tree4" dataUrl="${app}/partner/statistically/paternerStaff.do?method=dept" rootId="2"
		rootText='巡检组织' valueField="hidden_tree4_none" handler="select_deptname_id" textField="select_deptname_id"
		checktype="dept" single="true">
	</eoms:xbox>
	<input type="hidden"  id="hidden_tree2_none">
	<input type="hidden"  id="hidden_tree3_none">
	<input type="hidden"  id="hidden_tree4_none">

<form action="statistics.do?method=skillStatistics" method="post">
<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
			<td class="label">地市</td>    
			<td><input type="text" class="text"	 maxLength="100"	name="select_city" id="select_city_id" /></td>
			<td class="label">县区</td>    
			<td><input type="text" class="text"	 maxLength="100"	name="select_county" id="select_county_id" /></td>
			<td class="label">巡检组织</td>
			<td><input type="text" class="text"	 maxLength="100"	name="select_deptname" id="select_deptname_id" /></td>
		</tr>
		<tr>
			<td class="label">巡检专业</td>
			<td colspan="5">
			    <c:forEach items="${specialtyList}" var="dictBigType" >
							<input type="checkbox"  name="select_prof_c"
								value="${dictBigType.dictId}" />${dictBigType.dictName}
				 </c:forEach>
				 <input id="select_prof_hidden" type="hidden" name="select_prof" />
			 </td>
		</tr>
		
	</table>
	</fieldset>

<fieldset>
			<legend>请选择统计项目</legend>
<table class="formTable">
	<tr>
		<td class="label">地市</td>
		<td>
			<input type="checkbox" name="rowsPramters_c"	value="city" />
		</td>
		<td class="label">县区</td>
		<td>
			<input type="checkbox" name="rowsPramters_c"	value="county" />
		</td>
		<td class="label">巡检组织</td>
		<td>
			<input type="checkbox" name="rowsPramters_c"	value="deptname" />
		</td>
		<td class="label">巡检专业</td>
		<td>
			<input type="checkbox" name="rowsPramters_c"	value="profTypeFor" />
		</td>
		<input id="rowsPramters_hidden" type="hidden" name="rowsPramters"/>
	</tr>
</table>
</fieldset>
	<input id="statistics_submit" type="submit"  value="统计" />
<form>

<div>
 <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
 	 <thead>
	 <tr >
	 <logic-el:present name="headList">
	 <c:forEach items="${headList}"  var="headlist" >
	 <th>
	 ${headlist}
	 </th>
	 
	 </c:forEach>
	 
     </logic-el:present>
     </tr>
     </thead>
     <logic-el:present name="tableList">
     <tbody>
     <c:forEach items="${tableList}" var="tdList">
     <tr>
     <c:forEach items="${tdList}" var="td">
    <c:if test="${td.show}">
     <td rowspan="${td.rowspan} }">
  
     <c:if test="${!empty td.url}">
     <a href="javascript:void(0);" onclick="window.open('${app}${td.url}');">${td.name}</a>
     </c:if>
     <c:if test="${empty td.url}">
      ${td.name}
     </c:if>
    
     </td>
      </c:if>
     </c:forEach>
     </tr>
     </c:forEach>
	  </tbody>
	  </logic-el:present>
 </table>
</div>
<div id="fusionchart"></div>





<%@ include file="/common/footer_eoms.jsp"%>