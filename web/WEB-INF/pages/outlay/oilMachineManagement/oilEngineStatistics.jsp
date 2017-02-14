<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
 function convert(a){
  if(a==1){
      document.getElementById("focusTable").src="../../partner/oilmachine/oilEngineStatistics.do?method=electricNumber"
  }else if(a==2){
      document.getElementById("focusTable").src="../../partner/oilmachine/oilEngineStatistics.do?method=electricTimes"
  }
}

</script>
<form>
   <tr>
      <td>
          <a href="javaScript:convert(1)">统计发电次数</a>
           &nbsp;
          <a href="javaScript:convert(2)">统计发电时长</a>
      </td>   
        <br/>
   </tr>
   <br/>
  <iframe id="focusTable" width="100%;" height="95%;" scrolling="auto" src="${app}/partner/oilmachine/oilEngineStatistics.do?method=electricNumber">
  </iframe>
</form>  
<%@ include file="/common/footer_eoms.jsp"%>