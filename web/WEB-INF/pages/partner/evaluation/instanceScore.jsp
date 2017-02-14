<%@ page language="java" pageEncoding="UTF-8"  import="java.util.*,com.boco.eoms.evaluation.model.IndicatorScore,com.boco.eoms.evaluation.model.web.TdObjectModel,com.boco.eoms.evaluation.factory.IndicatorReferScoreModel;"  %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
     var myJ=jQuery.noConflict(); 

    //算分类型：根据指标值，获得评分
    function scoreValidateAndGet(th,trIndex){  
       var indcvalue=th.value;
       var indcvalueReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
       var indcvalueDiv=Ext.get("indcvalueDiv_"+trIndex);
      
       if(indcvalue.match(indcvalueReg) && ""!=indcvalue){           
           
          var Nindcvalue=Number(indcvalue);//字符串转数字
              
          Ext.Ajax.request({
	 	            url: '${app}/partner/evaluation/evaluationEntity.do?method=getIndicatorScore', 
                    params:{ 
       	  				evaluationEntityID:"${evaluationEntityId}", 
       	  				templateid:"${templateid}", 
       	  				templatelinkid:"${templatelinkid}",
       	  				indcid:Ext.get("indcid_"+trIndex).getValue(),       	  				
       	  				indcvalue:indcvalue  
       				}, 
       				success:function(response,options){           				   
                          var inputscoreDiv=Ext.get("inputscoreDiv_"+trIndex); 
       				   				  
       					 var resulttext=response.responseText;
       					 rslt=Ext.decode(resulttext);   
       					 if(rslt.suc==true){ 
       					    var oldinputscore=Number(Ext.get("inputscore_"+trIndex).getValue()); //评分旧值
       					    Ext.get("inputscore_"+trIndex).dom.value=rslt.score; //根据指标值计算得到的评分值 重设置 评分值
       					    Ext.get("cmpinputscore_"+trIndex).dom.value=rslt.score; 
       					    
       					    //动态设置总分
       					    var oldTotalInputScore_=Number(Ext.get("totalInputScore").getValue());
       					    var newTotalInputScore_=oldTotalInputScore_-oldinputscore+Number(rslt.score);
       						Ext.get("totalInputScore").dom.value=Number(newTotalInputScore_).toFixed(2);	 
       						Ext.get("totalInputScoreDiv").dom.innerHTML='评分总占比：'+Number(newTotalInputScore_).toFixed(2)+'%';
       						
       						
       						//分数显示
       						var indicatorproportion=myJ('#proportion_'+trIndex).val();       						
       						var efraction=myJ('#fraction_'+trIndex).val();       		
       	  				    Ext.get("inputscoreF_"+trIndex).dom.value=(newTotalInputScore_/indicatorproportion*efraction).toFixed(2); 
           					
           					indcvalueDiv.hide();
                            inputscoreDiv.hide();
       					    return true; 
       					 }     						          					 
       					 else{ 
       					    indcvalueDiv.dom.innerHTML=rslt.errormsg;
           					indcvalueDiv.setStyle("backgroundColor","#FF0000"); 
           					indcvalueDiv.show();
           					return false; 
       					 }	
      				}
	 	        });
        
       }else{
           	indcvalueDiv.show();
           	return false;
       }
   }  

   function validateNum(th,trIndex){ //特定用于验证 输入的 评分 
       var inputscore=th.value;
       var indicatorproportion=myJ('#proportion_'+trIndex).val();
       var inputscoreReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
       var inputscoreDiv=Ext.get("inputscoreDiv_"+trIndex);
       if(inputscore.match(inputscoreReg) && ""!=inputscore){
            if(Number(inputscore)>Number(indicatorproportion)){
                th.value=indicatorproportion;                
                alert('输入的评分'+inputscore+'>指标最高分'+indicatorproportion+'\n  将设置为指标允许的最高分');
                inputscore=indicatorproportion;
            }
       
            var totalInputScore_=0;  
            var inputscoreArr=Ext.query("input[id^='inputscore_']");
            var indcscoretypArr=Ext.query("input[id^='indcscoretyp_']");
            var indcproportionArr=Ext.query("input[id^='indcproportion_']");
            for(var i=0;i<inputscoreArr.length;i++){
               if(indcscoretypArr[i].value=='1'){//减分
                  //alert("totalInputScore_:"+totalInputScore_+",indcproportionArr[i].value:"+indcproportionArr[i].value+",Number(inputscoreArr[i].value):"+Number(inputscoreArr[i].value));
                  //加处理：否则其他减分类型，还没有打分，就会算上的；虽说最终会算正确的，但是让人有一个错觉。
                  if((new String(inputscoreArr[i].value)).trim()!=''){
                    totalInputScore_=totalInputScore_+(Number(indcproportionArr[i].value)-Number(inputscoreArr[i].value));
                  }                 
               }else{
                  totalInputScore_=totalInputScore_+Number(inputscoreArr[i].value);
               }
            } 
       		Ext.get("totalInputScore").dom.value=Number(totalInputScore_).toFixed(2);  
       		Ext.get("totalInputScoreDiv").dom.innerHTML='评分总占比：'+Number(totalInputScore_).toFixed(2)+"%"; 
       		
       			//分数显示
       		var efraction=myJ('#fraction_'+trIndex).val();       		
       	    Ext.get("inputscoreF_"+trIndex).dom.value=(th.value/indicatorproportion*efraction).toFixed(2); 
       	    
            inputscoreDiv.hide();
            return true;
        }else{
            inputscoreDiv.show();
           	return false;
        }  
   }
   function validateNumF(th,trIndex){ //特定用于验证 输入的 评分 
       var inputscore=th.value;
       var indicatorproportion=myJ('#fraction_'+trIndex).val();
       var inputscoreReg=/^([1-9]{1}[0-9]{0,2}(\,[0-9]{3})*(\.[0-9]{0,2})?|[1-9]{1}\d*(\.[0-9]{0,2})?|0(\.[0-9]{0,2})?|(\.[0-9]{1,2})?)$/;
       var inputscoreDiv=Ext.get("inputscoreDiv_"+trIndex);
       if(inputscore.match(inputscoreReg) && ""!=inputscore){
            if(Number(inputscore)>Number(indicatorproportion)){
                th.value=indicatorproportion;                
                alert('输入的评分'+inputscore+'>指标最高分'+indicatorproportion+'\n  将设置为指标允许的最高分');
                inputscore=indicatorproportion;
            }
       
            var totalInputScore_=0;  
            var inputscoreArr=Ext.query("input[id^='inputscoreF_']");
            var indcscoretypArr=Ext.query("input[id^='indcscoretyp_']");
            var indcproportionArr=Ext.query("input[id^='indcfraction_']");
            for(var i=0;i<inputscoreArr.length;i++){
               if(indcscoretypArr[i].value=='1'){//减分
                  // alert("totalInputScore_:"+totalInputScore_+",indcproportionArr[i].value:"+indcproportionArr[i].value+",Number(inputscoreArr[i].value):"+Number(inputscoreArr[i].value));
                  //加处理：否则其他减分类型，还没有打分，就会算上的；虽说最终会算正确的，但是让人有一个错觉。
                  if((new String(inputscoreArr[i].value)).trim()!=''){
                    totalInputScore_=totalInputScore_+(Number(indcproportionArr[i].value)-Number(inputscoreArr[i].value));
                  }                 
               }else{
                  totalInputScore_=totalInputScore_+Number(inputscoreArr[i].value);
               }
            } 
       		Ext.get("totalInputScore").dom.value=Number(totalInputScore_).toFixed(2);  
       		Ext.get("totalInputScoreDiv").dom.innerHTML='总分：'+Number(totalInputScore_).toFixed(2)+"分"; 
       		//权重占比(分数/该模板分数*该模板的占比)
       		var eproportion=myJ('#proportion_'+trIndex).val();       		
       	    Ext.get("inputscore_"+trIndex).dom.value=(th.value/indicatorproportion*eproportion).toFixed(2); 
       	    
            inputscoreDiv.hide();
            return true;
        }else{
            inputscoreDiv.show();
           	return false;
        }  
   }
   
   function openImport(handler){
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
   }

   Ext.onReady(function(){	
          var isImport="${isImport}";
          if(isImport=="true"){
            var importLog="${importLog}";
            importLog=importLog.replace(/<br>/g,"\n");
            alert(importLog); 
          }     
	      var v = new eoms.form.Validation({form:'scoreForm'});
          v.custom = function(){ 
	         if(!scoreValidateAndGet()) {
	            		return false;
	         }      	 
	         return true;
	      };     
    });
    
    function openRuleDetail(ownindcid){
        var url="${app}/partner/evaluation/checkRule.do?method=showDetailByOwnIndcId&ownindcid="+ownindcid;
        window.open (url, "ruledetail", "height=450, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");  
    }
    
  function saveImport() {
	var file = myJ("#importFile").val();
	if(file==''){
		alert('请选需上传的文件');
		return ;
	}
	
	/*
	Ext.form.BasicForm().submit():
	1、默认是ajax提交.
	2、如果的确想回传,可以使用如下方式 
    var myForm = new Ext.form.BasicForm("form-el-id", { 
        onSubmit: Ext.emptyFn, 
        submit: function() { 
            this.getEl().dom.submit(); 
        } 
    }); 重写submit()和事件onSubmit,使用普通的dom form提交：dom.form.submit()，实现页面跳转。
	3、当fileUpload 为true时，表单为文件上传类型。Set to true if this form is a file upload. 
     File uploads are not performed using normal "Ajax" techniques, that is they are not performed using 
     XMLHttpRequests. Instead the form is submitted in the standard manner with the DOM <form> element ***temporarily 
     modified to have its target set to refer to a dynamically generated, hidden <iframe> ***which is inserted into 
     the document but removed after the return data has been gathered。	 enctype="multipart/form-data"时不能在form
     中传递参数，序通过url来传递。
	*/
	//如下是文件提交,因为enctype="multipart/form-data" 
	//先验证，再提交跳转 
	/*
	new Ext.form.BasicForm('importForm').submit({
	    method : 'post',
		url : "${app}/partner/evaluation/exportAndImport.do?method=importTemplateData&isValidate=true&templateid=${templateid}&evaluationEntityId=${evaluationEntityId}", 
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			//Ext.Msg.alert('提示信息', action.result.message); 
			//myJ("#importFile").val("");
			myJ("#importForm").attr("action","${app}/partner/evaluation/exportAndImport.do?method=importTemplateData&isValidate=false&templateid=${templateid}&evaluationEntityId=${evaluationEntityId}");
		    //myJ("#importForm").attr("target","mytest");  
		    document.getElementById("importForm").submit();//dom提交
		    alert("hhhhhhh");
		},
		failure : function(form, action) {
			Ext.Msg.alert('提示信息',action.result.message);
			myJ("#importFile").val("");
		}
    });*/
    //使用ext框架来进行文件上传，无法达到跳转的效果，因为 ext fileUpload 为true时，ext会创建一个hidden <iframe>作为target来接收response返回的流数据,接收完返回的数据后再删除这个hidden <iframe>,所以达不到跳转的效果
    
    Ext.get(document.body).mask('导入数据中......');
    myJ("#importForm").attr("action","${app}/partner/evaluation/exportAndImport.do?method=importTemplateData&templateid=${templateid}&evaluationEntityId=${evaluationEntityId}");
    myJ("#importForm").get(0).submit();
    setTimeout(function(){
					Ext.get(document.body).unmask();
		       },60000);	 
 }
</script>

<!--  		
<form id="excelForm" name="excelForm" enctype="multipart/form-data"  method="post" action="${app}/partner/evaluation/ExportAndImport.do?method=importXiangMuTemplate&templateid=${templateid}&evaluationEntityId=${evaluationEntityId}">
  <input type="button" value="导出模板" onClick="exportTemplate();"/>&nbsp;&nbsp;&nbsp;
  <input type="file" id="FILE1" name="FILE" size="30" class="file"><input type="submit" value="导入数据"/> 
</form>
-->
 <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/ico_file_excel.png"
	 align="absmiddle"
	 style="cursor:pointer" > 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
  </div>
  <div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 <html:form action="/exportAndImport.do?method=importTemplateData" 
		method="post" styleId="importForm"
		enctype="multipart/form-data" >
		<table border=0 cellspacing="1" cellpadding="1" class="listTable">
		    <tr>
				<td>
				  <input id="importFile" type="file" name="importFile" class="file" alt="allowBlank:false"  />
			      <font color="red">请选择上传xls格式的文件</font>								
				</td>
			</tr>			
			<tr>
				<td>
				   <input type="button" value="导入" class="submit" onclick="saveImport()">&nbsp;&nbsp;&nbsp; &nbsp;
				   <input type="button" value="下载导入模板" class="button" name="save" 
						onclick="javascript:location.href='${app}/partner/evaluation/exportAndImport.do?method=exportTemplate&templateid=${templateid}'" />								
				</td>
			</tr>
		</table>
	</html:form>
  </div>
 <br/> 
 
<!--11号添加-->
<table id="sheet" class="formTable">

<td colspan="4">
	<div class="ui-widget-header"> 
		评分细则 
    </div>
</td>	
<form method="post" id="scoreForm" name="scoreForm">
  <input type="hidden" class="text" name="evaluationEntityId" value="${evaluationEntityId}"/>
  <input type="hidden" class="text" name="templateid" value="${templateid}"/>
  <input type="hidden" class="text" name="templatelinkid" value="${templatelinkid}"/>
  
  <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
    <caption>
		<c:if test="${not empty auditUser}">
			<div style="text-align:left">
				由<font color="red">${auditUser}</font>驳回
			</div>
		</c:if>
		<intput type="hidden" name="totalScoreHidden" id="totalScoreHidden" value=""/>
		<div style="text-align:right" id='totalInputScoreDiv'>
			评分总占比：<c:if test="${hashistory}"><fmt:formatNumber value="${totalInputScore}" pattern="0.00"/>%</c:if>
			     <c:if test="${not hashistory}"><fmt:formatNumber value="${totalCmpReferScore}" pattern="0.00"/></c:if>  
			总分：<c:if test="${hashistory}"><fmt:formatNumber value="${totalScore }" pattern="0.00"/>分</c:if> 
		</div>
		<input type="hidden" id="totalInputScore" name="totalInputScore"
		  <c:if test="${hashistory}">value="${totalInputScore}"</c:if> 
		  <c:if test="${not hashistory}">value="${totalCmpReferScore}"</c:if>
		/>
   </caption>
   <thead>
	 <tr>
	   <c:forEach items="${headList}"  var="headList" >
	     <c:if test="${headList.show}">
	      <th rowspan="${headList.rowspan}" colspan="${headList.colspan}"> ${headList.name} </th>  
	     </c:if>
	   </c:forEach>
	   <!-- 评分表头相关 -->
	    <th rowspan="1" colspan="1"> 规则详情 </th>
	    <th rowspan="1" colspan="1"> 指标值来源 </th>
	    <th rowspan="1" colspan="1" style="width:100px;"> 指标值</th>  
	    <th rowspan="1" colspan="1"> 计算得分</th>
	    <th rowspan="1" colspan="1"> 评分</th>  
	    <th rowspan="1" colspan="1">没得满分原因</th>
	 </tr>
   </thead>
   <tbody>
      <%int trIndex = 0;%>
      <%List indicatorScoreHistoryList=null;
      IndicatorScore indicatorScoreH=null;
      List indicatorsList=(List)request.getAttribute("indicatorsList");
      TdObjectModel indicator=null;
      Map indicatorReferScoreMap=(Map)request.getAttribute("indicatorReferScoreMap");
      IndicatorReferScoreModel indicatorReferScoreModel=null;
      %>
     <c:if test="${hashistory}">
       <%indicatorScoreHistoryList= (List)request.getAttribute("indicatorScoreHistoryList");%>
     </c:if>
     
     <c:forEach items="${templateLlt}"  var="tdList">
       <%indicator=(TdObjectModel)indicatorsList.get(trIndex);
         indicatorReferScoreModel=(IndicatorReferScoreModel)indicatorReferScoreMap.get(indicator.getId());
       %>
       <c:if test="${hashistory}">
         <%indicatorScoreH=(IndicatorScore)indicatorScoreHistoryList.get(trIndex);%>
       </c:if>
       <tr>
         <c:forEach items="${tdList}" var="td" varStatus="status">
           <c:if test="${td.datatype=='indicator'}"> 
           <input type="hidden" id="fraction_<%=trIndex%>" value="${td.fraction}" />
           <input type="hidden" id="proportion_<%=trIndex%>" value="${td.proportion}" /></c:if>
           <c:if test="${td.show}">
              <c:if test="${td.datatype=='template' or td.datatype=='xiangmu'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}(${td.proportion}分) </td>
              </c:if>
              <c:if test="${td.datatype=='zhuanye'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}-专业(100.0) </td>
              </c:if>
              <c:if test="${td.datatype=='programme' or td.datatype=='indicator'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name}(${td.fraction}分占${td.proportion}%) </td>
              </c:if>
              <c:if test="${td.datatype=='scoretyp'}">
                <td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name} </td>
                <input type="hidden" id="indcscoretyp_<%=trIndex%>" name="indcscoretyp" value="${td.value}" />
              </c:if>
              <c:if test="${td.datatype=='indicatorextra'}"><td rowspan="${td.rowspan}" colspan="${td.colspan}"> ${td.name} </td></c:if>
           </c:if>
         </c:forEach>
         <!-- 指标权重 -->
         <input type="hidden" id="indcproportion_<%=trIndex%>" name="indcproportion" value="<%=indicator.getProportion()!=null?indicator.getProportion().toString():"0" %>"/>
         <input type="hidden" id="indcfraction_<%=trIndex%>" name="indcfraction" value="<%=indicator.getFraction()!=null?indicator.getFraction().toString():"0" %>"/>
         <!-- 规则详情  -->
         <td rowspan="1" colspan="1">                   
                <%
                  if("2".equals(indicator.getScoretyp())){ //算分
                %>
                 <a href="javascript:openRuleDetail('<%=indicator.getId()%>');">查看</a>
                <%}else{ %>
                     无
                <%} %>    
         </td>
         <!-- 指标值来源 -->
         <td rowspan="1" colspan="1">   
                <%
                  if("2".equals(indicator.getScoretyp())){//算分，算分才会出现指标值
                     if(indicatorReferScoreModel!=null){
                 %>
                          <a href="javascript:alert('<%=indicatorReferScoreModel.getReferScoreDescription()%>');">系统采集</a>
                    <%}else{%>
                          手工输入        
                <%}}else{ }%>                   
         </td>
         <!-- 指标值 -->
         <td rowspan="1" colspan="1">  
             <%
              if("2".equals(indicator.getScoretyp())){ //算分
             %>
                 <input type="text" class="text" id="indcvalue_<%=trIndex%>" name="indcvalue" 
                   <c:if test="${hashistory  and not importSuccess}"> value="<%=indicatorScoreH.getIndcvalue()==null?"":indicatorScoreH.getIndcvalue().toString()%>"</c:if>
                   <c:if test="${(hashistory and importSuccess) or not hashistory}"> value="<%=indicatorReferScoreModel!=null?indicatorReferScoreModel.getReferScore().toString():""%>"</c:if>   
                     <%
                       if(indicatorReferScoreModel!=null){//具有采集的指标值（非手工输入），不能修改
                     %>           
                         readonly style="border:none;background:none;"
                     <%}else{} %>
                     alt="allowBlank:false,vtext:'请输入数字!'"
                     onChange="javascript:scoreValidateAndGet(this,<%=trIndex%>)" style="width:65px;">
                 </input>  
                 <div id="indcvalueDiv_<%=trIndex%>" class="ui-state-highlight ui-corner-all" style="width:150px;display:none">
				   <span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;">请输入非负数,且小数位不要超过2位</span>
			     </div> 
             <%}else{ %>
                 <input type="hidden"  id="indcvalue_<%=trIndex%>" name="indcvalue" value="0"/>
             <%} %>                          
         </td>
         
         <!-- 计算得分 -->
         <td rowspan="1" colspan="1" style="width:100px;">
            <input type="text" class="text" id="cmpinputscore_<%=trIndex%>" name="cmpinputscore" 
              <c:if test="${hashistory and not importSuccess}"> value="<%=indicatorScoreH.getCmpinputscore()==null?"":indicatorScoreH.getCmpinputscore().toString()%>"</c:if>
              <c:if test="${(hashistory and importSuccess) or not hashistory}"> value="<%=indicatorReferScoreModel!=null?indicatorReferScoreModel.getCmpReferScore().toString():""%>"</c:if>                                                             
              style="border:none;background:none;" readonly>
            </input>                    
         </td>   
         <!--  评分 -->
         <td rowspan="1" colspan="1" style="width:100px;">
         <!--hashistory表示有数据，可以是数据库中的数据，也可以是excel中的数据。 
          当通过excel导入时，使用了导入的文件数据作为/覆盖历史数据indicatorScoreHistoryList，所以此处直接hashistory作为判断，
          还一个原因是每一个指标都有inputscore；  但是indcvalue、cmpinputscore是 算分类型所特有的； 所以取inputscore时，直接以
         hashistory作为判断，如果以indicatorReferScoreModel.getCmpReferScore()只会得到 算分类型的指标（的计算分为）inputscore
         forrest:20130401,不能简单的hashistory,因为当导入时，可以是导入了部分这是也是hashistory，但不是所有的指标都导入成功了，故indicatorScoreH也许为null
           -->
            <input type="text" class="text" id="inputscoreF_<%=trIndex%>" name="inputscoreF" 
             <% if("1".equals(indicator.getScoretyp())){//此情况为减分 %>
                  <c:if test="${hashistory and not importSuccess}"> value="<%=(indicator.getFraction()-indicatorScoreH.getScore()) %>"</c:if>                 
            <%}else{ %>
                  <c:if test="${hashistory and not importSuccess}"> value="<%=indicatorScoreH.getScore()%>"</c:if>
            <%} %> 
              alt="allowBlank:false,vtext:'请输入数字!'"
              onChange="javascript:validateNumF(this,<%=trIndex%>)" style="width:50px;">分
            </input>        
            <input type="text" class="text" id="inputscore_<%=trIndex%>" name="inputscore" 
              <c:if test="${hashistory and not importSuccess}"> value="<%=indicatorScoreH.getInputscore()%>"</c:if>
              <c:if test="${(hashistory and importSuccess) or not hashistory}"> value="<%=indicatorScoreH!=null?(indicatorScoreH.getInputscore()>0?indicatorScoreH.getInputscore().toString():(indicatorReferScoreModel!=null?indicatorReferScoreModel.getCmpReferScore().toString():"")):(indicatorReferScoreModel!=null?indicatorReferScoreModel.getCmpReferScore().toString():"")%>"</c:if>                         
              alt="allowBlank:false,vtext:'请输入数字!'"
              onChange="javascript:validateNum(this,<%=trIndex%>)" style="width:50px;">%
            </input>        
            <input type="hidden" class="text" name="indcid" id="indcid_<%=trIndex%>" value="<%=indicator.getId()%>"/>      
            <input type="hidden" class="text" name="owntmpllinkid" id="owntmpllinkid_<%=trIndex%>" value="<%=indicator.getOwntmplid()%>"/>
            <input type="hidden" class="text" name="ownprgmlinkid" id="ownprgmlinkid_<%=trIndex%>" value="<%=indicator.getOwnprgmid() %>"/>
            <div id="inputscoreDiv_<%=trIndex%>" class="ui-state-highlight ui-corner-all" style="width:150px;display:none">
				<span class="ui-icon ui-icon-circle-triangle-e" style="float: left; margin-right: .6em;">请输入非负数,且小数位不要超过2位</span>
			</div>	
         </td>        
         <td rowspan="1" colspan="1">
            <textArea id="remark_<%=trIndex%>" name="remark" onblur="this.value = this.value.slice(0, 254)" style="width:150px;height:80px;"
              <c:if test="${hashistory and not importSuccess}"> value="<%=indicatorScoreH.getRemark()%>"</c:if>
              <c:if test="${(hashistory and importSuccess) or not hashistory}">value="<%=indicatorScoreH!=null?indicatorScoreH.getRemark():(indicatorReferScoreModel!=null?indicatorReferScoreModel.getRemark():"")%>"</c:if>                         
          ><c:if test="${hashistory and not importSuccess}"><%=indicatorScoreH.getRemark()==null?"":indicatorScoreH.getRemark()%></c:if><c:if test="${(hashistory and importSuccess) or not hashistory}"><%=indicatorScoreH!=null?(indicatorScoreH.getRemark()==null?"":indicatorScoreH.getRemark()):(indicatorReferScoreModel!=null?(indicatorReferScoreModel.getRemark()==null?"":indicatorReferScoreModel.getRemark()):"")%></c:if></textArea>
         </td>
       </tr>
       <%trIndex++;%>
     </c:forEach>    
   </tbody>  
 </table>
 
 
 <div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
  <table class="formTable">
	<tr>
		<td>	
			<input type="button" class="btn" value="保存" onclick="saveScore();" />
			&nbsp;&nbsp;&nbsp;
			<input type="button" class="btn" value="提交" onclick="submitScore();" />
		</td>		
	</tr>
  </table>
 </div>
</form>

</table>


<script type="text/javascript">
 function saveScore(){
 	var canSubmit='';
 	
 	myJ("input[id^='indcvalue_']").each(function(){	    
 		if((new String(myJ(this).val())).trim()==''){
 			myJ(this).focus();
 			alert('指标值不能为空！');
 			canSubmit='false';
 			return false;
 		}		
 	});
 	if(canSubmit=='false'){
 		return false;
 	}
 	
 	myJ("input[id^='inputscore_']").each(function(){	    
 		if( (new String(myJ(this).val())).trim()==''){
 		    myJ(this).focus();
 			alert('评分不能为空！');
 			canSubmit='false';
 			return false;
 		}		
 	});
    if(canSubmit=='false'){
 		return false;
 	}
 	
    myJ("textArea[id^='remark_']").each(function(){	    
 		if( (new String(myJ(this).val())).trim()==''){
 			var trIdx=this.id.slice(7);
 			if(myJ('#indcscoretyp_'+trIdx).val()=='1'){//评分类型为减分 
 			    myJ(this).focus();
 			    alert('减分，需说明原因');
 			   	canSubmit='false';
 			    return false;
 			}else{//评分类型为加分 和 算分
 			    if(Number(myJ('#proportion_'+trIdx).val())==Number(myJ('#inputscore_'+trIdx).val())){
 		    	}else{
 		       	  myJ(this).focus();
 			   	  alert('没得满分，需说明原因');
 			   	  canSubmit='false';
 			      return false;
 		       }
 			}
 		}		
 	});
    
 	if(canSubmit=='false'){
 		return false;
 	}else{
 	    Ext.get(document.body).mask('保存数据中......');  
 	    myJ('#scoreForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=saveOrSubmitScore&opertyp=save");
 		myJ('#scoreForm').submit(); 	
 		setTimeout(function(){
					Ext.get(document.body).unmask();
		 },30000);	
 	}	
 }
 
 function submitScore(){
 	var canSubmit='';
 	
 	myJ("input[id^='indcvalue_']").each(function(){	    
 		if((new String(myJ(this).val())).trim()==''){
 			myJ(this).focus();
 			alert('指标值不能为空！');
 			canSubmit='false';
 			return false;
 		}		
 	});
 	if(canSubmit=='false'){
 		return false;
 	}
 	
 	myJ("input[id^='inputscore_']").each(function(){	    
 		if( (new String(myJ(this).val())).trim()==''){
 			myJ(this).focus();
 			alert('评分不能为空！');
 			canSubmit='false';
 			return false;
 		}		
 	});
 	if(canSubmit=='false'){
 		return false;
 	}
   
    myJ("textArea[id^='remark_']").each(function(){	    
 		if( (new String(myJ(this).val())).trim()==''){
 			var trIdx=this.id.slice(7);
 		    if(myJ('#indcscoretyp_'+trIdx).val()=='1'){//评分类型为减分 
 			    myJ(this).focus();
 			    alert('减分，需说明原因');
 			   	canSubmit='false';
 			    return false;
 			}else{//评分类型为加分 和 算分
 			    if(Number(myJ('#proportion_'+trIdx).val())==Number(myJ('#inputscore_'+trIdx).val())){
 		    	}else{
 		       	  myJ(this).focus();
 			   	  alert('没得满分，需说明原因');
 			   	  canSubmit='false';
 			      return false;
 		       }
 			}
 		}		
 	});
   
 	if(canSubmit=='false'){
 		return false;
 	}else{
 	    Ext.get(document.body).mask('提交数据中......');
 	    myJ('#scoreForm').attr("action","${app}/partner/evaluation/evaluationEntity.do?method=saveOrSubmitScore&opertyp=submit");
 		myJ('#scoreForm').submit();
 	    setTimeout(function(){
					Ext.get(document.body).unmask();
	   },30000);	  
 	}	
 }
 
 function exportTemplate(){
     //var nowURL="${app}/partner/evaluation/ExportAndImport.do?method=exportXiangMuTemplate";  
	 //window.open(nowURL);
	
	 
	 var tempForm = document.createElement("form");  
     tempForm.id="tempForm1";  
     tempForm.method="post";  
     tempForm.action="${app}/partner/evaluation/ExportAndImport.do?method=exportXiangMuTemplate";  
     tempForm.target="exportexceltemplate";
     
      var hideInput = document.createElement("input");  
      hideInput.type="hidden";  
      hideInput.name= "templateId"; 
      hideInput.value= "${templateid}";//传递多个数据时，可以封装成json进行传递 
      tempForm.appendChild(hideInput);  
      hideInput = document.createElement("input");  
      hideInput.type="hidden";  
      hideInput.name= "prtTmplnm";
      var prt_templatename=""
      try{
       var pwindow=window.parent;
       var pdocument=pwindow.document;
        prt_templatename=pdocument.getElementById('prt_templatename').value;
      }catch(e){
        prt_templatename="";
      }   
      hideInput.value= prt_templatename;//传递多个数据时，可以封装成json进行传递  
      tempForm.appendChild(hideInput); 
      
      tempForm.onsubmit=function(){  window.open('about:blank',"exportexceltemplate"); } 
     
      document.body.appendChild(tempForm);  
      tempForm.submit();
      document.body.removeChild(tempForm);
 }
</script>
<%@ include file="/common/footer_eoms.jsp"%>