<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page import="com.boco.eoms.base.util.StaticMethod"%>
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<META content="MSHTML 6.00.2900.2604" name=GENERATOR>
<style type="text/css">
<!--
.objdiv{position:absolute;z-index:99;background-color:#f7f7f7;Filter: Alpha(Opacity=0);}
.commdiv {font: 12px;color:#007236; background: E2FEDE; border: #00a651 1px solid; position: absolute; z-index: 100; display: none; margin: 5px; padding: 5px}
-->
</style>
</HEAD>

<BODY LEFTMARGIN=0 RIGHTMARGIN=0>
<div id="workflowCanvas" style="position:absolute;left:0px;top:0px">
<img src="<%=request.getContextPath()%>/partner/flow/${flowName}.png" border=0 />
</div>
<script type="text/javascript">
  var getCoordInDocumentExample = function(){
    var workflowCanvas = document.getElementById("workflowCanvas");
    workflowCanvas.onclick = function(e){
      var pointer = getCoordInDocument(e);
    // alert("X,Y=("+(pointer.x+68)+", "+(pointer.y-47)+")");
    }
  }
  var getCoordInDocument = function(e) {
    e = e || window.event;
    var x = e.pageX || (e.clientX +
      (document.documentElement.scrollLeft
      || document.body.scrollLeft));
    var y= e.pageY || (e.clientY +
      (document.documentElement.scrollTop
      || document.body.scrollTop));
    return {'x':x,'y':y};
  }
  window.onload = function(){
     getCoordInDocumentExample();
 };
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/partner/wz_jsgraphics.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/partner/xmlextras.js"></script>

<script type="text/javascript">
var currentStepIds = [];
var historyStepIds = [];
var hrefStepIds = [];

      //在循环外建立放DIV的数组;
var xmlHttp = XmlHttp.create();
var async = true;
xmlHttp.open("GET", "<%=request.getContextPath()%>/partner/flow/${flowName}.lyt.xml", async);
xmlHttp.onreadystatechange = function (){
  if (xmlHttp.readyState == 4){
    //set up graphics
    var jg = new jsGraphics("workflowCanvas");
    jg.setStroke(3);
    var cells = xmlHttp.responseXML.getElementsByTagName("cell");
    var imageCells = xmlHttp.responseXML.getElementsByTagName("image");
    
    var stateCells = xmlHttp.responseXML.getElementsByTagName("${step}");
    currentStepIds = stateCells[0].getAttribute("done").split(",");
	historyStepIds = stateCells[0].getAttribute("undo").split(",");
	hrefStepIds = stateCells[0].getAttribute("href").split(",");
    var xAdjust = 0 - imageCells[0].getAttribute("x") + 3;
    var yAdjust = 0 - imageCells[0].getAttribute("y") + 3;

    var widthAdjust = 0;
    var heightAdjust = 0;
    var divStr = "";
    for(var n = 0; n < cells.length; n++){
      var cell = cells[n];
      var actionName = cell.getAttribute("actionName");
      if((cell.getAttribute("type") == "StepCell")){
        for(var j = 0; j < historyStepIds.length; j++){
          if((historyStepIds[j] == parseInt(cell.getAttribute("id")))){
            jg.setColor("#00ff00");
            jg.drawRect(parseInt(cell.getAttribute("x")) + xAdjust,
                        parseInt(cell.getAttribute("y")) + yAdjust,
                        parseInt(cell.getAttribute("width")) + widthAdjust,
                        parseInt(cell.getAttribute("height")) + heightAdjust);
                        divStr+= "<div class='objDiv' onclick='showUndo(\"";
            			divStr+= actionName + "\")'";
            			divStr+= " style='display:block;position:absolute;left:";
            			divStr+= (parseInt(cell.getAttribute("x")) +xAdjust);
            			divStr+= ";top:" + (parseInt(cell.getAttribute("y")) + yAdjust);
            			divStr+= ";width:" + (parseInt(cell.getAttribute("width")) + widthAdjust);
            			divStr+= ";height:" + (parseInt(cell.getAttribute("height")) + heightAdjust);
            			divStr+= ";background-color:#ccff00; filter: Alpha(Opacity=50)";
            			divStr+= "'><\/div>";
          }
        }
        for(var i = 0; i < currentStepIds.length; i++){
          if((currentStepIds[i] == parseInt(cell.getAttribute("id")))){
            jg.setColor("#ff0000");
            jg.drawRect(parseInt(cell.getAttribute("x")) + xAdjust,
                        parseInt(cell.getAttribute("y")) + yAdjust,
                        parseInt(cell.getAttribute("width")) + widthAdjust,
                        parseInt(cell.getAttribute("height")) + heightAdjust);
                        divStr+= "<div class='objDiv' onclick='showDetail(";
            			divStr+= + cell.getAttribute("id") + ")'";
            			divStr+= " style='display:block;position:absolute;left:";
            			divStr+= (parseInt(cell.getAttribute("x")) +xAdjust);
            			divStr+= ";top:" + (parseInt(cell.getAttribute("y")) + yAdjust);
            			divStr+= ";width:" + (parseInt(cell.getAttribute("width")) + widthAdjust);
            			divStr+= ";height:" + (parseInt(cell.getAttribute("height")) + heightAdjust);
            			divStr+= ";background-color:#FFCC00; filter: Alpha(Opacity=50)";
            			divStr+= "'><\/div>";
          }
        }
       for(var j = 0; j < hrefStepIds.length; j++){
          if((hrefStepIds[j] == parseInt(cell.getAttribute("id")))){
            jg.setColor("#0000ff");
            jg.drawRect(parseInt(cell.getAttribute("x")) + xAdjust,
                        parseInt(cell.getAttribute("y")) + yAdjust,
                        parseInt(cell.getAttribute("width")) + widthAdjust,
                        parseInt(cell.getAttribute("height")) + heightAdjust);
                        divStr+= "<div class='objDiv' onclick='showHref(";
            			divStr+= + cell.getAttribute("id") + ")'";
            			divStr+= " style='display:block;position:absolute;left:";
            			divStr+= (parseInt(cell.getAttribute("x")) +xAdjust);
            			divStr+= ";top:" + (parseInt(cell.getAttribute("y")) + yAdjust);
            			divStr+= ";width:" + (parseInt(cell.getAttribute("width")) + widthAdjust);
            			divStr+= ";height:" + (parseInt(cell.getAttribute("height")) + heightAdjust);
            			divStr+= ";background-color:#00ccff; filter: Alpha(Opacity=50)";
            			divStr+= "'><\/div>";
          }
        }
      }
    }
    document.all.workflowCanvas.innerHTML+=divStr;
    jg.paint();
  }
};
xmlHttp.send(null);
function showDetail(stepId){
   
}
function showUndo(actionName){
	<%
	String flowName = StaticMethod.nullObject2String(request.getAttribute("flowName"));
	if("feeinfoFlow".equals(flowName)){
	%>
	window.open('<%=request.getContextPath()%>/partner/feeInfo/pnrFeeInfoPays.do?method=planPay&actionName='+actionName+'&planId=${planId}');
	<%
	}
	if("agreementFlow".equals(flowName)){
	%>
	if(actionName=='doEva'){
	window.open('<%=request.getContextPath()%>/eva/evaReportInfos.do?method=getTaskForTemplate&agreeId=${agreeId}');
	}else if(actionName=='doWork'||actionName=='reportWork'){
	window.open('<%=request.getContextPath()%>/partner/baseinfo/index.do?method=workplanUndoList&agreeId=${agreeId}');
	}else{
	window.open('<%=request.getContextPath()%>/partner/agreement/pnrAgreementMains.do?method=showAgreeUndoFromFlow&actionName='+actionName+'&agreementId=${agreeId}');
	}
	<%
	}
	%>
}

function showHref(stepId){
   window.open('<%=request.getContextPath()%>/partner/baseinfo/pnrFlow.do?method=showFeeFlow&id=${feeId}&flowType=feeinfo','','toolbar=no,scrollBars=no,width=800,height=470');
}

     
</script>

<div id="coord" ></div>
</BODY>
</HTML>



