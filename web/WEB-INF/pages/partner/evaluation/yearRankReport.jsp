<%@ page language="java" pageEncoding="UTF-8"  import="java.util.*,com.boco.eoms.evaluation.model.IndicatorScore,com.boco.eoms.evaluation.model.web.TdObjectModel,com.boco.eoms.evaluation.factory.IndicatorReferScoreModel;"  %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<link rel="stylesheet" type="text/css" href="${app}/styles/default/displaytag.css" />

<form action="${app}/partner/evaluation/evaluStatAndQur.do?method=yearRankReport" 
	method="post" id="yearRankReportForm" name="yearRankReportForm"> 
	  <fieldset>
	  <legend>请输入排名条件</legend>	
	<table id="sheet" class="listTable">
			<tr>		
				<td class="label">排名模式：</td> 
				<td>
				   <html:select property="operationCd" styleId="operationCd" value="${operationCd}" alt="allowBlank:false,vtext:'请选择排名模式！'">
				     <html:options collection="operationOptions" property="value" labelProperty="label"/>
				   </html:select>
				<td class="label">年：</td>
				<td>
				    <eoms:dict key="dict-partner-inspect" selectId="year" dictId="yearflag" 
					beanId="selectXML" cls="select" defaultId="${year}" alt="allowBlank:false,vtext:'年月必选！'" />
				</td>													
			</tr>			
	    </table>
	    </fieldset>
		<input type="submit" styleClass="btn" value="确定" />
</form>	
<br/>

<logic:present name="resultList" scope="request">
    <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
     <thead>
	   <tr>
	      <th rowspan="1" colspan="1" onClick="setSortCss(this);"  id="th_rank"
	      <c:if test="${rankDir=='asc'}"> class="sorted order2"</c:if> 
	      <c:if test="${rankDir=='desc'}">class="sorted order1"</c:if>
	      >
	       <a href="javascript:displayRank(0);">名次</a> 
	      </th>
	      <c:choose>
              <c:when test="${deptLevel=='1'}">
         		 <th rowspan="1" colspan="1"> 代维公司 </th>
              </c:when>
              <c:when test="${deptLevel=='2'}">
                  <th rowspan="1" colspan="1"> 代维公司 </th>
	      		  <th rowspan="1" colspan="1"> 代维办事处 </th>
			  </c:when>
              <c:otherwise>
                  <th rowspan="1" colspan="1"> 代维公司 </th>
	     		  <th rowspan="1" colspan="1"> 代维办事处 </th>
	     		  <th rowspan="1" colspan="1"> 代维中心 </th>
			  </c:otherwise> 
          </c:choose>     
	      <th rowspan="1" colspan="1"> 代维业务 </th>
	      <!-- 注意考核项目的列排列是有顺序的，按考核项目的字典顺序排列的 -->
	      <th rowspan="1" colspan="1"> 月度考核平均 </th>
	      <th rowspan="1" colspan="1"> 基础管理 </th>
	      <th rowspan="1" colspan="1"> 现场检查 </th>
	      <th rowspan="1" colspan="1"> 加分项 </th>
	      <th rowspan="1" colspan="1"> 扣分项 </th>
	      <th rowspan="1" colspan="1"> 合计 </th>
	      <th rowspan="1" colspan="1"> 总计 </th>
	   </tr>
     </thead>
     <tbody>
        <c:forEach items="${resultList}" var="tdList">
          <tr>
           <c:forEach items="${tdList}" var="td" varStatus="status">
             <c:if test="${td.show}">   
                <td rowspan="${td.rowspan}" colspan="${td.colspan}">
                  <c:choose>
              		<c:when test="${deptLevel=='1'}">
         		         <c:choose>
                          <c:when test="${status.index==1}"><eoms:id2nameDB id='${td.name}' beanId='partnerDeptDao'/></c:when>
                          <c:when test="${status.index==2}"><eoms:id2nameDB id="${td.name}" beanId="ItawSystemDictTypeDao" /></c:when>
                          <c:otherwise> 
                               <c:choose>
                       			 <c:when test="${ empty td.href  or  td.href==''}"> 
                          			 ${td.name}
                        		 </c:when>
                        		 <c:otherwise>
                            		<a  href="${app}${td.href}"
				           			 target="view" shape="rect">${td.name}</a>
                        		 </c:otherwise>
                     		   </c:choose>    
                          </c:otherwise> 
                         </c:choose>
                    </c:when>
             	    <c:when test="${deptLevel=='2'}">
                         <c:choose>
                 			 <c:when test="${status.index==1 or status.index==2}"><eoms:id2nameDB id='${td.name}' beanId='partnerDeptDao'/></c:when>
                  			 <c:when test="${status.index==3}"><eoms:id2nameDB id="${td.name}" beanId="ItawSystemDictTypeDao" /></c:when>
                  		     <c:otherwise>
                  		         <c:choose>
                       			 <c:when test="${ empty td.href  or  td.href==''}"> 
                          			 ${td.name}
                        		 </c:when>
                        		 <c:otherwise>
                            		<a  href="${app}${td.href}"
				           			 target="view" shape="rect">${td.name}</a>
                        		 </c:otherwise>
                     		   </c:choose>    
							 </c:otherwise> 
                	     </c:choose>
			        </c:when>
                    <c:otherwise>
                       <c:choose>
                 		 <c:when test="${status.index>=1 and status.index<=3}"><eoms:id2nameDB id='${td.name}' beanId='partnerDeptDao'/></c:when>
                 		 <c:when test="${status.index==4}"><eoms:id2nameDB id="${td.name}" beanId="ItawSystemDictTypeDao" /></c:when>
                 		 <c:otherwise> 
                 		     <c:choose>
                       			 <c:when test="${ empty td.href  or  td.href==''}"> 
                          			 ${td.name}
                        		 </c:when>
                        		 <c:otherwise>
                            		<a  href="${app}${td.href}"
				           			 target="view" shape="rect">${td.name}</a>
                        		 </c:otherwise>
                     		   </c:choose>    
						 </c:otherwise> 
                	   </c:choose>
			        </c:otherwise> 
          		</c:choose>             
               </td>
             </c:if> 
           </c:forEach>
          </tr> 
        </c:forEach>
     </tbody>
   </table>
            
</logic:present>    

<script type="text/javascript">
var myJ=jQuery.noConflict();

Ext.onReady(function(){             
	var v = new eoms.form.Validation({form:'yearRankReportForm'});
    v.custom = function(){            
        return true;
	};  		  
}); 

function setSortCss(ths){
   var thsClass=myJ(ths).attr('class');
   if(thsClass=='sorted order1'){
       myJ(ths).attr('class','sorted order2');//arrow_up
   }else{
      myJ(ths).attr('class','sorted order1'); //arrow_down
   }
}

function displayRank(){
   var rankDir=myJ("<input type='hidden' >"); //创建一个 <input> 元素必须同时设定 type 属性。因为微软规定 <input> 元素的 type 只能写一次
   rankDir.attr('name','rankDir');
   if(myJ('#th_rank').attr('class')=='sorted order1'){
     rankDir.val('desc');
   }else{
     rankDir.val('asc');
   }   
   rankDir.appendTo(myJ('#yearRankReportForm'));
   Ext.get(document.body).mask('处理中，请等待......');;
   myJ('#yearRankReportForm').submit();
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>