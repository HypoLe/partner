<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script language="JavaScript"
	src="${app}/nop3/scripts/jquery/jquery-1.5.js">
	</script>
	<script type="text/javascript">
	var jq = jQuery.noConflict();

         function  urlChange(imgId,index) {
        	var img = jq("#"+imgId);
        	
        	var ot=img.offset();
        	
    	    var items=jq("#see_"+index);
    	    if((index+1)%3!=0){
    	    var lef = ot.left+img.width();
    	    var tp = ot.top+img.height()/4;
    	    items[0].style.top=tp;
    	    items[0].style.left=lef;
    	    items.toggle(500);
         }
         else{
        	 var lef = ot.left-items.width();
     	    var tp = ot.top+items.height()/4;
     	    items[0].style.top=tp;
     	    items[0].style.left=lef;
     	    items.toggle(500);
         }
    	}
   
         function mouseout(index) {
        	var items=jq("#see_"+index);
     	    items.toggle();
         }
         
    </script>
	
	<table style="width: 100%;"  >
	<caption>
	<div class="header center" style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd">系统功能聚合</div>
	</br>
	</br>
	</br>
	</caption>
	
<logic-el:present name="showPageList">
				<c:forEach items="${showPageList}" var="showList" varStatus="i">
				<c:if test="${i.index%3==0}">
					<tr>
				</c:if>
					<td>
					<div style="height: 250px;">
						<div align="center">
						<a id="content" href="${app}/${showList.moduleUrl}" >
							 <img  id="imgUser_${i.index}" src="${showList.photo}"  border="0" width="130" height="130" 
							 		onmouseover="urlChange('imgUser_${i.index}','${i.index}');"
							 		onmouseout="mouseout('${i.index}')">
							 		</a>
						</div>
						<div align="center">
							 <font size="5"><b>${showList.moduleName}</b></font>
					 		 <div style="position: absolute; z-index: 101; top: 952px; left: 673px;display:none" id="see_${i.index}" >
					 		  <table id="stylesheet" class="formTable">
					 		  <tr>
					 		  <td width="200" align="center">
					 		  <img src="${showList.photo}"  border="0" width="130" height="130" />
					 		  </td>
					 		  </tr>
					 		  <tr>
									<td class="label" width="200">模块介绍</td></tr>
								<tr>	
									<td class="content" width="200">${showList.content}</td>
								</tr>
					 		  
					 		  </table>
					 		  </br>
					 		 </div>
					 	</div>
					</div>
					</td>
				<c:if test="${(i.index+1)%3==0}">
					</tr>
				</c:if>
					
				</c:forEach>
			</logic-el:present>
			
			

</table>
<%@ include file="/common/footer_eoms.jsp"%>
