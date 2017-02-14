<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>
<script src="GridE.js"> </script>
<script src="t.js"></script>
<link href="Examples.css" rel="stylesheet" type="text/css" />
<%=request.getParameter("grid")%>
<form>
<input type="hidden" name="grid" id="GridData">
<div style="width:100%;height:100%;">
<treegrid Layout_Url="${app}/scripts/widgets/treegrid/GanttSimpleDef.xml" 
	Data_Url="${app}/scripts/widgets/treegrid/GanttSimpleData.xml" Upload_Tag="GridData">
</treegrid>
</div>
</form>
<script>

// --- For new rows changes parent row to group task ---
Grids.OnRowAdd = function(G,row){
var P = row.parentNode;
if(P.Def==G.Def.R) G.ChangeDef(P,"Task");
}

// --- When row is moved its new parent is changed to group task and the old parent if does not contain any children to standard task ---
Grids.OnRowMove = function(G,row,par){
var P = row.parentNode;
if(P.Def==G.Def.R) G.ChangeDef(P,"Task");
if(par.Def) { 
   for(var p=par.firstChild;p;p=p.nextSibling) if(p.Visible && !p.Deleted) break;
   if(!p) G.ChangeDef(par,"R");
   }
}

// --- When row is deleted and its parent does not contain any children, the parent is changed to standard task ---
Grids.OnRowDelete = function(G,row){
var par = row.parentNode;
if(par.Def) { 
   for(var p=par.firstChild;p;p=p.nextSibling) if(p.Visible && !p.Deleted) break;
   if(!p) G.ChangeDef(par,"R");
   }  
}

// --- For undeleted rows changes parent row to group task ---
Grids.OnRowUndelete = function(G,row){
var P = row.parentNode;
if(P.Def==G.Def.R) G.ChangeDef(P,"Task");
}

Grids.OnAfterValueChanged = function(G, row, col) {
  //G.GetCell(row, 'pk', 0).Changed = true;
}

Grids.OnSave = function (G, row, autoupdate) {
}
</script>

<script type="text/javascript">
	if(location.hostname.search(/coqsoft\.|treegrid\./i)>=0) setTimeout('location.replace("../../../www/#..*Examples*Html*Gantt*GanttSimple.html"+location.hash.replace("#","\'"));',500);
</script>
<%@ include file="/common/footer_eoms.jsp"%>