/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.gantt.GanttTaskItem"],["require","dojo.date.locale"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.gantt.GanttTaskItem"]){_4._hasResource["dojox.gantt.GanttTaskItem"]=true;_4.provide("dojox.gantt.GanttTaskItem");_4.require("dojo.date.locale");_4.declare("dojox.gantt.GanttTaskControl",null,{constructor:function(_7,_8,_9){this.ganttChart=_9;this.project=_8;this.taskItem=_7;this.checkMove=false;this.checkResize=false;this.moveChild=false;this.maxPosXMove=-1;this.minPosXMove=-1;this.maxWidthResize=-1;this.minWidthResize=-1;this.posX=0;this.posY=0;this.mouseX=0;this.taskItemWidth=0;this.isHide=false;this.hideTasksHeight=0;this.isExpanded=true;this.descrTask=null;this.cTaskItem=null;this.cTaskNameItem=null;this.parentTask=null;this.predTask=null;this.childTask=[];this.childPredTask=[];this.nextChildTask=null;this.previousChildTask=null;this.nextParentTask=null;this.previousParentTask=null;},createConnectingLinesPN:function(){var _a=[];var _b=_4.create("div",{innerHTML:"&nbsp;",className:"ganttTaskLineVerticalLeft"},this.ganttChart.panelNames.firstChild);var _c=this.cTaskNameItem[0],_d=this.parentTask.cTaskNameItem[0];_4.style(_b,{height:(_c.offsetTop-_d.offsetTop)+"px",top:(_d.offsetTop+5)+"px",left:(_d.offsetLeft-9)+"px"});var _e=_4.create("div",{noShade:true,color:"#000000",className:"ganttTaskLineHorizontalLeft"},this.ganttChart.panelNames.firstChild);_4.style(_e,{left:(_d.offsetLeft-9)+"px",top:(_c.offsetTop+5)+"px",height:"1px",width:(_c.offsetLeft-_d.offsetLeft+4)+"px"});_a.push(_b);_a.push(_e);return _a;},createConnectingLinesDS:function(){var _f=this.ganttChart.contentData.firstChild;var _10=[];var _11=new Image();var _11=_4.create("div",{className:"ganttImageArrow"});var _12=document.createElement("div");var _13=document.createElement("div");var _14=_4.style(this.predTask.cTaskItem[0],"left");var _15=_4.style(this.predTask.cTaskItem[0],"top");var _16=_4.style(this.cTaskItem[0],"left");var _17=this.posY+2;var _18=parseInt(this.predTask.cTaskItem[0].firstChild.firstChild.width);var _19=parseInt(this.predTask.cTaskItem[0].firstChild.firstChild.width);if(_15<_17){_4.addClass(_12,"ganttTaskLineVerticalRight");_4.style(_12,{height:(_17-this.ganttChart.heightTaskItem/2-_15-3)+"px",width:"1px",left:(_14+_19-20)+"px",top:(_15+this.ganttChart.heightTaskItem)+"px"});_4.addClass(_13,"ganttTaskLineHorizontal");_4.style(_13,{width:(15+(_16-(_19+_14)))+"px",left:(_14+_19-20)+"px",top:(_17+2)+"px"});_4.addClass(_11,"ganttTaskArrowImg");_4.style(_11,{left:(_16-7)+"px",top:(_17-1)+"px"});}else{_4.addClass(_12,"ganttTaskLineVerticalRightPlus");_4.style(_12,{height:(_15+2-_17)+"px",width:"1px",left:(_14+_19-20)+"px",top:(_17+2)+"px"});_4.addClass(_13,"ganttTaskLineHorizontalPlus");_4.style(_13,{width:(15+(_16-(_19+_14)))+"px",left:(_14+_19-20)+"px",top:(_17+2)+"px"});_4.addClass(_11,"ganttTaskArrowImgPlus");_4.style(_11,{left:(_16-7)+"px",top:(_17-1)+"px"});}_f.appendChild(_12);_f.appendChild(_13);_f.appendChild(_11);_10.push(_12);_10.push(_11);_10.push(_13);return _10;},showChildTasks:function(_1a,_1b){if(_1b){for(var i=0;i<_1a.childTask.length;i++){var _1c=_1a.childTask[i],_1d=_1c.cTaskItem[0],_1e=_1c.cTaskNameItem[0],_1f=_1c.cTaskItem[1],_20=_1c.cTaskNameItem[1],_21=_1c.cTaskItem[2],_22=_1c.cTaskNameItem[2];if(_1d.style.display=="none"){_1d.style.display="inline";_1e.style.display="inline";_1c.showDescTask();_1a.isHide=false;if(_22){_22.style.display="inline";_1b=_1c.isExpanded;}for(var k=0;k<_1f.length;k++){_1f[k].style.display="inline";}for(var k=0;k<_20.length;k++){_20[k].style.display="inline";}(_1c.taskIdentifier)&&(_1c.taskIdentifier.style.display="inline");this.hideTasksHeight+=this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;if(_1c.childTask.length>0){this.showChildTasks(_1c,_1b);}}}}},hideChildTasks:function(_23){for(var i=0;i<_23.childTask.length;i++){var _24=_23.childTask[i],_25=_24.cTaskItem[0],_26=_24.cTaskNameItem[0],_27=_24.cTaskItem[1],_28=_24.cTaskNameItem[1],_29=_24.cTaskItem[2],_2a=_24.cTaskNameItem[2];if(_25.style.display!="none"){_25.style.display="none";_26.style.display="none";_24.hideDescTask();_23.isHide=true;if(_2a){_2a.style.display="none";}for(var k=0;k<_27.length;k++){_27[k].style.display="none";}for(var k=0;k<_28.length;k++){_28[k].style.display="none";}(_24.taskIdentifier)&&(_24.taskIdentifier.style.display="none");this.hideTasksHeight+=(this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra);if(_24.childTask.length>0){this.hideChildTasks(_24);}}}},shiftCurrentTasks:function(_2b,_2c){this.shiftNextTask(this,_2c);_2b.project.shiftNextProject(_2b.project,_2c);},shiftTask:function(_2d,_2e){_2d.posY=_2d.posY+_2e;var _2f=_2d.cTaskItem[0],_30=_2d.cTaskNameItem[0],_31=_2d.cTaskItem[1],_32=_2d.cTaskNameItem[1],_33=_2d.cTaskItem[2],_34=_2d.cTaskNameItem[2];_30.style.top=parseInt(_30.style.top)+_2e+"px";if(_34){_34.style.top=parseInt(_34.style.top)+_2e+"px";}if(_2d.parentTask){if(parseInt(this.cTaskNameItem[0].style.top)>parseInt(_2d.parentTask.cTaskNameItem[0].style.top)&&(_32[0].style.display!="none")){_32[0].style.height=parseInt(_32[0].style.height)+_2e+"px";}else{_32[0].style.top=parseInt(_32[0].style.top)+_2e+"px";}_32[1].style.top=parseInt(_32[1].style.top)+_2e+"px";}_2f.style.top=parseInt(_2f.style.top)+_2e+"px";_2d.descrTask.style.top=parseInt(_2d.descrTask.style.top)+_2e+"px";if(_2d.predTask){if(((parseInt(this.cTaskItem[0].style.top)>parseInt(_2d.predTask.cTaskItem[0].style.top))||(this.cTaskItem[0].id==_2d.predTask.taskItem.id))&&_31[0].style.display!="none"){_31[0].style.height=parseInt(_31[0].style.height)+_2e+"px";}else{_31[0].style.top=parseInt(_31[0].style.top)+_2e+"px";}_31[1].style.top=parseInt(_31[1].style.top)+_2e+"px";_31[2].style.top=parseInt(_31[2].style.top)+_2e+"px";}},shiftNextTask:function(_35,_36){if(_35.nextChildTask){this.shiftTask(_35.nextChildTask,_36);this.shiftChildTask(_35.nextChildTask,_36);this.shiftNextTask(_35.nextChildTask,_36);}else{if(_35.parentTask){this.shiftNextTask(_35.parentTask,_36);}else{if(_35.nextParentTask){this.shiftTask(_35.nextParentTask,_36);this.shiftChildTask(_35.nextParentTask,_36);this.shiftNextTask(_35.nextParentTask,_36);}}}},shiftChildTask:function(_37,_38){_4.forEach(_37.childTask,function(_39){this.shiftTask(_39,_38);if(_39.childTask.length>0){this.shiftChildTask(_39,_38);}},this);},endMove:function(){var _3a=this.cTaskItem[0];var _3b=_4.style(_3a,"left")-this.posX;var _3c=this.getDateOnPosition(_4.style(_3a,"left"));_3c=this.checkPos(_3c);if(this.checkMove){_3b=this.ganttChart.getPosOnDate(_3c)-this.posX;this.moveCurrentTaskItem(_3b,this.moveChild);this.project.shiftProjectItem();}this.checkMove=false;this.posX=0;this.maxPosXMove=-1;this.minPosXMove=-1;_3a.childNodes[1].firstChild.rows[0].cells[0].innerHTML="";this.adjustPanelTime();if(this.ganttChart.resource){this.ganttChart.resource.refresh();}},checkPos:function(_3d){var _3e=this.cTaskItem[0];var h=_3d.getHours();if(h>=12){_3d.setDate(_3d.getDate()+1);_3d.setHours(0);if((parseInt(_3e.firstChild.firstChild.width)+this.ganttChart.getPosOnDate(_3d)>this.maxPosXMove)&&(this.maxPosXMove!=-1)){_3d.setDate(_3d.getDate()-1);_3d.setHours(0);}}else{if((h<12)&&(h!=0)){_3d.setHours(0);if((this.ganttChart.getPosOnDate(_3d)<this.minPosXMove)){_3d.setDate(_3d.getDate()+1);}}}_3e.style.left=this.ganttChart.getPosOnDate(_3d)+"px";return _3d;},getMaxPosPredChildTaskItem:function(){var _3f=0;var _40=0;for(var i=0;i<this.childPredTask.length;i++){_40=this.getMaxPosPredChildTaskItemInTree(this.childPredTask[i]);if(_40>_3f){_3f=_40;}}return _3f;},getMaxPosPredChildTaskItemInTree:function(_41){var _42=_41.cTaskItem[0];var _43=parseInt(_42.firstChild.firstChild.width)+_4.style(_42,"left");var _44=0;var _45=0;_4.forEach(_41.childPredTask,function(_46){_45=this.getMaxPosPredChildTaskItemInTree(_46);if(_45>_44){_44=_45;}},this);return _44>_43?_44:_43;},moveCurrentTaskItem:function(_47,_48){var _49=this.cTaskItem[0];this.taskItem.startTime=new Date(this.ganttChart.startDate);this.taskItem.startTime.setHours(this.taskItem.startTime.getHours()+(parseInt(_49.style.left)/this.ganttChart.pixelsPerHour));this.showDescTask();var _4a=this.cTaskItem[1];if(_4a.length>0){_4a[2].style.width=parseInt(_4a[2].style.width)+_47+"px";_4a[1].style.left=parseInt(_4a[1].style.left)+_47+"px";}_4.forEach(this.childTask,function(_4b){if(!_4b.predTask){this.moveChildTaskItems(_4b,_47,_48);}},this);_4.forEach(this.childPredTask,function(_4c){this.moveChildTaskItems(_4c,_47,_48);},this);},moveChildTaskItems:function(_4d,_4e,_4f){var _50=_4d.cTaskItem[0];if(_4f){_50.style.left=parseInt(_50.style.left)+_4e+"px";_4d.adjustPanelTime();_4d.taskItem.startTime=new Date(this.ganttChart.startDate);_4d.taskItem.startTime.setHours(_4d.taskItem.startTime.getHours()+(parseInt(_50.style.left)/this.ganttChart.pixelsPerHour));var _51=_4d.cTaskItem[1];_4.forEach(_51,function(_52){_52.style.left=parseInt(_52.style.left)+_4e+"px";},this);_4.forEach(_4d.childTask,function(_53){if(!_53.predTask){this.moveChildTaskItems(_53,_4e,_4f);}},this);_4.forEach(_4d.childPredTask,function(_54){this.moveChildTaskItems(_54,_4e,_4f);},this);}else{var _51=_4d.cTaskItem[1];if(_51.length>0){var _55=_51[0],_56=_51[2];_56.style.left=parseInt(_56.style.left)+_4e+"px";_56.style.width=parseInt(_56.style.width)-_4e+"px";_55.style.left=parseInt(_55.style.left)+_4e+"px";}}_4d.moveDescTask();},adjustPanelTime:function(){var _57=this.cTaskItem[0];var _58=parseInt(_57.style.left)+parseInt(_57.firstChild.firstChild.width)+this.ganttChart.panelTimeExpandDelta;_58+=this.descrTask.offsetWidth;this.ganttChart.adjustPanelTime(_58);},getDateOnPosition:function(_59){var _5a=new Date(this.ganttChart.startDate);_5a.setHours(_5a.getHours()+(_59/this.ganttChart.pixelsPerHour));return _5a;},moveItem:function(_5b){var _5c=_5b.screenX;var _5d=(this.posX+(_5c-this.mouseX));var _5e=parseInt(this.cTaskItem[0].childNodes[0].firstChild.width);var _5f=_5d+_5e;if(this.checkMove){if(((this.minPosXMove<=_5d))&&((_5f<=this.maxPosXMove)||(this.maxPosXMove==-1))){this.moveTaskItem(_5d);}}},moveTaskItem:function(_60){var _61=this.cTaskItem[0];_61.style.left=_60+"px";_61.childNodes[1].firstChild.rows[0].cells[0].innerHTML=this.getDateOnPosition(_60).getDate()+"."+(this.getDateOnPosition(_60).getMonth()+1)+"."+this.getDateOnPosition(_60).getUTCFullYear();},resizeItem:function(_62){if(this.checkResize){var _63=this.cTaskItem[0];var _64=_62.screenX;var _65=(_64-this.mouseX);var _66=this.taskItemWidth+(_64-this.mouseX);if(_66>=this.taskItemWidth){if((_66<=this.maxWidthResize)||(this.maxWidthResize==-1)){this.resizeTaskItem(_66);}else{if((this.maxWidthResize!=-1)&&(_66>this.maxWidthResize)){this.resizeTaskItem(this.maxWidthResize);}}}else{if(_66<=this.taskItemWidth){if(_66>=this.minWidthResize){this.resizeTaskItem(_66);}else{if(_66<this.minWidthResize){this.resizeTaskItem(this.minWidthResize);}}}}}},resizeTaskItem:function(_67){var _68=this.cTaskItem[0];var _69=Math.round(_67/this.ganttChart.pixelsPerWorkHour);var _6a=_68.childNodes[0].firstChild.rows[0],rc0=_6a.cells[0],rc1=_6a.cells[1];rc0&&(rc0.firstChild.style.width=parseInt(rc0.width)*_67/100+"px");rc1&&(rc1.firstChild.style.width=parseInt(rc1.width)*_67/100+"px");_68.childNodes[0].firstChild.width=_67+"px";_68.childNodes[1].firstChild.width=_67+"px";this.cTaskItem[0].childNodes[1].firstChild.rows[0].cells[0].innerHTML=_69;var _6b=_68.childNodes[2];_6b.childNodes[0].style.width=_67+"px";_6b.childNodes[1].style.left=_67-10+"px";},endResizeItem:function(){var _6c=this.cTaskItem[0];if((this.taskItemWidth!=parseInt(_6c.childNodes[0].firstChild.width))){var _6d=_6c.offsetLeft;var _6e=_6c.offsetLeft+parseInt(_6c.childNodes[0].firstChild.width);var _6f=Math.round((_6e-_6d)/this.ganttChart.pixelsPerWorkHour);this.taskItem.duration=_6f;if(this.childPredTask.length>0){for(var j=0;j<this.childPredTask.length;j++){var _70=this.childPredTask[j].cTaskItem[1],_71=_70[0],_72=_70[2],_73=_6c.childNodes[0];_72.style.width=parseInt(_72.style.width)-(parseInt(_73.firstChild.width)-this.taskItemWidth)+"px";_72.style.left=parseInt(_72.style.left)+(parseInt(_73.firstChild.width)-this.taskItemWidth)+"px";_71.style.left=parseInt(_71.style.left)+(parseInt(_73.firstChild.width)-this.taskItemWidth)+"px";}}}this.cTaskItem[0].childNodes[1].firstChild.rows[0].cells[0].innerHTML="";this.checkResize=false;this.taskItemWidth=0;this.mouseX=0;this.showDescTask();this.project.shiftProjectItem();this.adjustPanelTime();if(this.ganttChart.resource){this.ganttChart.resource.refresh();}},startMove:function(_74){this.moveChild=_74.ctrlKey;this.mouseX=_74.screenX;this.getMoveInfo();this.checkMove=true;this.hideDescTask();},showDescTask:function(){var _75=(parseInt(this.cTaskItem[0].style.left)+this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+10);this.descrTask.style.left=_75+"px";this.descrTask.innerHTML=this.objKeyToStr(this.getTaskOwner());this.descrTask.style.visibility="visible";},hideDescTask:function(){_4.style(this.descrTask,"visibility","hidden");},buildResourceInfo:function(_76){if(this.childTask&&this.childTask.length>0){for(var i=0;i<this.childTask.length;i++){var _77=this.childTask[i];_77.buildResourceInfo(_76);}}if(_4.trim(this.taskItem.taskOwner).length>0){var _78=this.taskItem.taskOwner.split(";");for(var i=0;i<_78.length;i++){var o=_78[i];if(_4.trim(o).length<=0){continue;}_76[o]?(_76[o].push(this)):(_76[o]=[this]);}}},objKeyToStr:function(obj,_79){var _7a="";_79=_79||" ";if(obj){for(var key in obj){_7a+=_79+key;}}return _7a;},getTaskOwner:function(){var _7b={};if(_4.trim(this.taskItem.taskOwner).length>0){var _7c=this.taskItem.taskOwner.split(";");for(var i=0;i<_7c.length;i++){var o=_7c[i];_7b[o]=1;}}_4.forEach(this.childTask,function(_7d){_4.mixin(_7b,_7d.getTaskOwner());},this);return _7b;},moveDescTask:function(){var _7e=(parseInt(this.cTaskItem[0].style.left)+this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+10);this.descrTask.style.left=_7e+"px";},getMoveInfo:function(){this.posX=parseInt(this.cTaskItem[0].style.left);var _7f=parseInt(this.cTaskItem[0].childNodes[0].firstChild.width);var _80=!this.parentTask?0:parseInt(this.parentTask.cTaskItem[0].style.left);var _81=!this.predTask?0:parseInt(this.predTask.cTaskItem[0].style.left)+parseInt(this.predTask.cTaskItem[0].childNodes[0].firstChild.width);var _82=!this.parentTask?0:parseInt(this.parentTask.cTaskItem[0].childNodes[0].firstChild.width);var _83=0;var _84=0;var _85=0;if(this.childPredTask.length>0){var _86=null;_4.forEach(this.childPredTask,function(_87){if((!_86)||((_86)&&(_86>parseInt(_87.cTaskItem[0].style.left)))){_86=parseInt(_87.cTaskItem[0].style.left);}},this);_83=_86;}if(this.childTask.length>0){var _88=null;_4.forEach(this.childTask,function(_89){if((!_88)||((_88)&&(_88>(parseInt(_89.cTaskItem[0].style.left))))){_88=parseInt(_89.cTaskItem[0].style.left);}},this);_85=_88;var _86=null;_4.forEach(this.childTask,function(_8a){if((!_86)||((_86)&&(_86<(parseInt(_8a.cTaskItem[0].style.left)+parseInt(_8a.cTaskItem[0].firstChild.firstChild.width))))){_86=parseInt(_8a.cTaskItem[0].style.left)+parseInt(_8a.cTaskItem[0].firstChild.firstChild.width);}},this);_84=_86;}if(!this.moveChild){if(this.childPredTask.length>0){if(this.maxPosXMove<_83){this.maxPosXMove=_83;}}if(this.childTask.length>0){if((this.childPredTask.length>0)&&(this.maxPosXMove-_7f)>_85){this.maxPosXMove=this.maxPosXMove-((this.maxPosXMove-_7f)-_85);}if(!(this.childPredTask.length>0)){this.maxPosXMove=_85+_7f;}this.minPosXMove=(_84-_7f);}if(_80>0){if((!(this.childPredTask.length>0))&&(this.childTask.length>0)){if(this.maxPosXMove>_80+_82){this.maxPosXMove=_80+_82;}}if(this.minPosXMove<=_80){this.minPosXMove=_80;}if((!(this.childTask.length>0))&&(!(this.childPredTask.length>0))){this.maxPosXMove=_80+_82;}else{if((!(this.childTask.length>0))&&(this.childPredTask.length>0)){if((_80+_82)>_81){this.maxPosXMove=_83;}}}}if(_81>0){if(this.minPosXMove<=_81){this.minPosXMove=_81;}}if((_81==0)&&(_80==0)){if(this.minPosXMove<=this.ganttChart.initialPos){this.minPosXMove=this.ganttChart.initialPos;}}}else{if((_80>0)&&(_81==0)){this.minPosXMove=_80;this.maxPosXMove=_80+_82;}else{if((_80==0)&&(_81==0)){this.minPosXMove=this.ganttChart.initialPos;this.maxPosXMove=-1;}else{if((_80>0)&&(_81>0)){this.minPosXMove=_81;this.maxPosXMove=_80+_82;}else{if((_80==0)&&(_81>0)){this.minPosXMove=_81;this.maxPosXMove=-1;}}}}if((this.parentTask)&&(this.childPredTask.length>0)){var _86=this.getMaxPosPredChildTaskItem(this);var _80=parseInt(this.parentTask.cTaskItem[0].style.left)+parseInt(this.parentTask.cTaskItem[0].firstChild.firstChild.width);this.maxPosXMove=this.posX+_7f+_80-_86;}}},startResize:function(_8b){this.mouseX=_8b.screenX;this.getResizeInfo();this.hideDescTask();this.checkResize=true;this.taskItemWidth=parseInt(this.cTaskItem[0].firstChild.firstChild.width);},getResizeInfo:function(){var _8c=this.cTaskItem[0];var _8d=!this.parentTask?0:parseInt(this.parentTask.cTaskItem[0].style.left);var _8e=!this.parentTask?0:parseInt(this.parentTask.cTaskItem[0].childNodes[0].firstChild.width);var _8f=parseInt(_8c.style.left);var _90=0;var _91=0;if(this.childPredTask.length>0){var _92=null;_4.forEach(this.childPredTask,function(_93){if((!_92)||((_92)&&(_92>parseInt(_93.cTaskItem[0].style.left)))){_92=parseInt(_93.cTaskItem[0].style.left);}},this);_90=_92;}if(this.childTask.length>0){var _92=null;_4.forEach(this.childTask,function(_94){if((!_92)||((_92)&&(_92<(parseInt(_94.cTaskItem[0].style.left)+parseInt(_94.cTaskItem[0].firstChild.firstChild.width))))){_92=parseInt(_94.cTaskItem[0].style.left)+parseInt(_94.cTaskItem[0].firstChild.firstChild.width);}},this);_91=_92;}this.minWidthResize=this.ganttChart.pixelsPerDay;if(this.childTask.length>0){this.minWidthResize=_91-_8f;}if((this.childPredTask.length>0)&&(!this.parentTask)){this.maxWidthResize=_90-_8f;}else{if((this.childPredTask.length>0)&&(this.parentTask)){var w1=_8d+_8e-_8f;var w2=_90-_8f;this.maxWidthResize=Math.min(w1,w2);}else{if((this.childPredTask.length==0)&&(this.parentTask)){this.maxWidthResize=_8d+_8e-_8f;}}}},createTaskItem:function(){this.posX=this.ganttChart.getPosOnDate(this.taskItem.startTime);var _95=_4.create("div",{id:this.taskItem.id,className:"ganttTaskItemControl"});_4.style(_95,{left:this.posX+"px",top:this.posY+"px"});var _96=_4.create("div",{className:"ganttTaskDivTaskItem"},_95);var _97=_4.create("table",{cellPadding:"0",cellSpacing:"0",width:this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+"px",className:"ganttTaskTblTaskItem"},_96);var _98=_97.insertRow(_97.rows.length);if(this.taskItem.percentage!=0){var _99=_4.create("td",{height:this.ganttChart.heightTaskItem+"px",width:this.taskItem.percentage+"%"},_98);_99.style.lineHeight="1px";var _9a=_4.create("div",{className:"ganttImageTaskProgressFilled"},_99);_4.style(_9a,{width:(this.taskItem.percentage*this.taskItem.duration*this.ganttChart.pixelsPerWorkHour)/100+"px",height:this.ganttChart.heightTaskItem+"px"});}if(this.taskItem.percentage!=100){var _99=_4.create("td",{height:this.ganttChart.heightTaskItem+"px",width:(100-this.taskItem.percentage)+"%"},_98);_99.style.lineHeight="1px";var _9b=_4.create("div",{className:"ganttImageTaskProgressBg"},_99);_4.style(_9b,{width:((100-this.taskItem.percentage)*this.taskItem.duration*this.ganttChart.pixelsPerWorkHour)/100+"px",height:this.ganttChart.heightTaskItem+"px"});}if(this.ganttChart.isContentEditable){var _9c=_4.create("div",{className:"ganttTaskDivTaskInfo"},_95);var _9d=_4.create("table",{cellPadding:"0",cellSpacing:"0",height:this.ganttChart.heightTaskItem+"px",width:this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+"px"},_9c);var _9e=_9d.insertRow(0);var _9f=_4.create("td",{align:"center",vAlign:"top",height:this.ganttChart.heightTaskItem+"px",className:"ganttMoveInfo"},_9e);var _a0=_4.create("div",{className:"ganttTaskDivTaskName"},_95);var _a1=_4.create("div",{},_a0);_4.create("input",{className:"ganttTaskDivMoveInput",type:"text"},_a1);_4.isIE&&_4.style(_a1,{background:"#000000",filter:"alpha(opacity=0)"});_4.style(_a1,{height:this.ganttChart.heightTaskItem+"px",width:this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+"px"});var _a2=_4.create("div",{className:"ganttTaskDivResize"},_a0);_4.create("input",{className:"ganttTaskDivResizeInput",type:"text"},_a2);_4.style(_a2,{left:(this.taskItem.duration*this.ganttChart.pixelsPerWorkHour-10)+"px",height:this.ganttChart.heightTaskItem+"px",width:"10px"});this.ganttChart._events.push(_4.connect(_a1,"onmousedown",this,function(_a3){this.moveMoveConn=_4.connect(document,"onmousemove",this,function(e){this.checkMove&&this.moveItem(e);});this.moveUpConn=_4.connect(document,"onmouseup",this,function(e){if(this.checkMove){this.endMove();this.ganttChart.isMoving=false;document.body.releaseCapture&&document.body.releaseCapture();_4.disconnect(this.moveMoveConn);_4.disconnect(this.moveUpConn);}});this.startMove(_a3);this.ganttChart.isMoving=true;document.body.setCapture&&document.body.setCapture(false);}));this.ganttChart._events.push(_4.connect(_a1,"onmouseover",this,function(_a4){_a4.target&&(_a4.target.style.cursor="move");}));this.ganttChart._events.push(_4.connect(_a1,"onmouseout",this,function(_a5){_a5.target.style.cursor="";}));this.ganttChart._events.push(_4.connect(_a2,"onmousedown",this,function(_a6){this.resizeMoveConn=_4.connect(document,"onmousemove",this,function(e){this.checkResize&&this.resizeItem(e);});this.resizeUpConn=_4.connect(document,"onmouseup",this,function(e){if(this.checkResize){this.endResizeItem();this.ganttChart.isResizing=false;document.body.releaseCapture&&document.body.releaseCapture();_4.disconnect(this.resizeMoveConn);_4.disconnect(this.resizeUpConn);}});this.startResize(_a6);this.ganttChart.isResizing=true;document.body.setCapture&&document.body.setCapture(false);}));this.ganttChart._events.push(_4.connect(_a2,"onmouseover",this,function(_a7){(!this.ganttChart.isMoving)&&(!this.ganttChart.isResizing)&&_a7.target&&(_a7.target.style.cursor="e-resize");}));this.ganttChart._events.push(_4.connect(_a2,"onmouseout",this,function(_a8){!this.checkResize&&_a8.target&&(_a8.target.style.cursor="");}));}return _95;},createTaskNameItem:function(){var _a9=_4.create("div",{id:this.taskItem.id,className:"ganttTaskTaskNameItem",title:this.taskItem.name+", id: "+this.taskItem.id+" ",innerHTML:this.taskItem.name});_4.style(_a9,"top",this.posY+"px");_4.attr(_a9,"tabIndex",0);if(this.ganttChart.isShowConMenu){this.ganttChart._events.push(_4.connect(_a9,"onmouseover",this,function(_aa){_4.addClass(_a9,"ganttTaskTaskNameItemHover");clearTimeout(this.ganttChart.menuTimer);this.ganttChart.tabMenu.clear();this.ganttChart.tabMenu.show(_aa.target,this);}));this.ganttChart._events.push(_4.connect(_a9,"onkeydown",this,function(_ab){if(_ab.keyCode==_4.keys.ENTER){this.ganttChart.tabMenu.clear();this.ganttChart.tabMenu.show(_ab.target,this);}if(this.ganttChart.tabMenu.isShow&&(_ab.keyCode==_4.keys.LEFT_ARROW||_ab.keyCode==_4.keys.RIGHT_ARROW)){_5.focus(this.ganttChart.tabMenu.menuPanel.firstChild.rows[0].cells[0]);}if(this.ganttChart.tabMenu.isShow&&_ab.keyCode==_4.keys.ESCAPE){this.ganttChart.tabMenu.hide();}}));this.ganttChart._events.push(_4.connect(_a9,"onmouseout",this,function(){_4.removeClass(_a9,"ganttTaskTaskNameItemHover");clearTimeout(this.ganttChart.menuTimer);this.ganttChart.menuTimer=setTimeout(_4.hitch(this,function(){this.ganttChart.tabMenu.hide();}),200);}));this.ganttChart._events.push(_4.connect(this.ganttChart.tabMenu.menuPanel,"onmouseover",this,function(){clearTimeout(this.ganttChart.menuTimer);}));this.ganttChart._events.push(_4.connect(this.ganttChart.tabMenu.menuPanel,"onkeydown",this,function(_ac){if(this.ganttChart.tabMenu.isShow&&_ac.keyCode==_4.keys.ESCAPE){this.ganttChart.tabMenu.hide();}}));this.ganttChart._events.push(_4.connect(this.ganttChart.tabMenu.menuPanel,"onmouseout",this,function(){clearTimeout(this.ganttChart.menuTimer);this.ganttChart.menuTimer=setTimeout(_4.hitch(this,function(){this.ganttChart.tabMenu.hide();}),200);}));}return _a9;},createTaskDescItem:function(){var _ad=(this.posX+this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+10);var _ae=_4.create("div",{innerHTML:this.objKeyToStr(this.getTaskOwner()),className:"ganttTaskDescTask"});_4.style(_ae,{left:_ad+"px",top:this.posY+"px"});return this.descrTask=_ae;},checkWidthTaskNameItem:function(){if(this.cTaskNameItem[0].offsetWidth+this.cTaskNameItem[0].offsetLeft>this.ganttChart.maxWidthTaskNames){var _af=this.cTaskNameItem[0].offsetWidth+this.cTaskNameItem[0].offsetLeft-this.ganttChart.maxWidthTaskNames;var _b0=Math.round(_af/(this.cTaskNameItem[0].offsetWidth/this.cTaskNameItem[0].firstChild.length));var _b1=this.taskItem.name.substring(0,this.cTaskNameItem[0].firstChild.length-_b0-3);_b1+="...";this.cTaskNameItem[0].innerHTML=_b1;}},refreshTaskItem:function(_b2){this.posX=this.ganttChart.getPosOnDate(this.taskItem.startTime);_4.style(_b2,{"left":this.posX+"px"});var _b3=_b2.childNodes[0];var _b4=_b3.firstChild;_b4.width=(!this.taskItem.duration?1:this.taskItem.duration*this.ganttChart.pixelsPerWorkHour)+"px";var _b5=_b4.rows[0];if(this.taskItem.percentage!=0){var _b6=_b5.firstChild;_b6.height=this.ganttChart.heightTaskItem+"px";_b6.width=this.taskItem.percentage+"%";_b6.style.lineHeight="1px";var _b7=_b6.firstChild;_4.style(_b7,{width:(!this.taskItem.duration?1:(this.taskItem.percentage*this.taskItem.duration*this.ganttChart.pixelsPerWorkHour/100))+"px",height:this.ganttChart.heightTaskItem+"px"});}if(this.taskItem.percentage!=100){var _b6=_b5.lastChild;_b6.height=this.ganttChart.heightTaskItem+"px";_b6.width=(100-this.taskItem.percentage)+"%";_b6.style.lineHeight="1px";var _b8=_b6.firstChild;_4.style(_b8,{width:(!this.taskItem.duration?1:((100-this.taskItem.percentage)*this.taskItem.duration*this.ganttChart.pixelsPerWorkHour/100))+"px",height:this.ganttChart.heightTaskItem+"px"});}if(this.ganttChart.isContentEditable){var _b9=_b2.childNodes[1];var _ba=_b9.firstChild;_ba.height=this.ganttChart.heightTaskItem+"px";_ba.width=(!this.taskItem.duration?1:(this.taskItem.duration*this.ganttChart.pixelsPerWorkHour))+"px";var _bb=_ba.rows[0];var _bc=_bb.firstChild;_bc.height=this.ganttChart.heightTaskItem+"px";var _bd=_b2.childNodes[2];var _be=_bd.firstChild;_be.style.height=this.ganttChart.heightTaskItem+"px";_be.style.width=(!this.taskItem.duration?1:(this.taskItem.duration*this.ganttChart.pixelsPerWorkHour))+"px";var _bf=_bd.lastChild;_4.style(_bf,{"left":(this.taskItem.duration*this.ganttChart.pixelsPerWorkHour-10)+"px"});_bf.style.height=this.ganttChart.heightTaskItem+"px";_bf.style.width="10px";}return _b2;},refreshTaskDesc:function(_c0){var _c1=(this.posX+this.taskItem.duration*this.ganttChart.pixelsPerWorkHour+10);_4.style(_c0,{"left":_c1+"px"});return _c0;},refreshConnectingLinesDS:function(_c2){var _c3=_c2[1];var _c4=_c2[0];var _c5=_c2[2];var _c6=_4.style(this.predTask.cTaskItem[0],"left");var _c7=_4.style(this.predTask.cTaskItem[0],"top");var _c8=_4.style(this.cTaskItem[0],"left");var _c9=this.posY+2;var _ca=parseInt(this.predTask.cTaskItem[0].firstChild.firstChild.width);var _cb=parseInt(this.predTask.cTaskItem[0].firstChild.firstChild.width);if(_c7<_c9){_4.style(_c4,{"height":(_c9-this.ganttChart.heightTaskItem/2-_c7-3)+"px","left":(_c6+_cb-20)+"px"});_4.style(_c5,{"width":(15+(_c8-(_cb+_c6)))+"px","left":(_c6+_cb-20)+"px"});_4.style(_c3,{"left":(_c8-7)+"px"});}else{_4.style(_c4,{"height":(_c7+2-_c9)+"px","left":(_c6+_cb-20)+"px"});_4.style(_c5,{"width":(15+(_c8-(_cb+_c6)))+"px","left":(_c6+_cb-20)+"px"});_4.style(_c3,{"left":(_c8-7)+"px"});}return _c2;},postLoadData:function(){},refresh:function(){if(this.childTask&&this.childTask.length>0){_4.forEach(this.childTask,function(_cc){_cc.refresh();},this);}this.refreshTaskItem(this.cTaskItem[0]);this.refreshTaskDesc(this.cTaskItem[0].nextSibling);var _cd=[];if(this.taskItem.previousTask&&this.predTask){this.refreshConnectingLinesDS(this.cTaskItem[1]);}return this;},create:function(){var _ce=this.ganttChart.contentData.firstChild;var _cf=this.ganttChart.panelNames.firstChild;var _d0=this.taskItem.previousTask;var _d1=this.taskItem.parentTask;var _d2=(this.taskItem.cldTasks.length>0)?true:false;this.cTaskItem=[];this.cTaskNameItem=[];if(!_d1){if(this.taskItem.previousParentTask){this.previousParentTask=this.project.getTaskById(this.taskItem.previousParentTask.id);var _d3=this.ganttChart.getLastChildTask(this.previousParentTask);this.posY=parseInt(_d3.cTaskItem[0].style.top)+this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;this.previousParentTask.nextParentTask=this;}else{this.posY=parseInt(this.project.projectItem[0].style.top)+this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;}}if(_d1){var _d4=this.project.getTaskById(this.taskItem.parentTask.id);this.parentTask=_d4;if(this.taskItem.previousChildTask){this.previousChildTask=this.project.getTaskById(this.taskItem.previousChildTask.id);var _d3=this.ganttChart.getLastChildTask(this.previousChildTask);this.posY=_4.style(_d3.cTaskItem[0],"top")+this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;this.previousChildTask.nextChildTask=this;}else{this.posY=_4.style(_d4.cTaskItem[0],"top")+this.ganttChart.heightTaskItem+this.ganttChart.heightTaskItemExtra;}_d4.childTask.push(this);}if(_d0){var _d4=this.project.getTaskById(_d0.id);this.predTask=_d4;_d4.childPredTask.push(this);}this.cTaskItem.push(this.createTaskItem());_ce.appendChild(this.cTaskItem[0]);if(this.ganttChart.panelNames){this.cTaskNameItem.push(this.createTaskNameItem());this.ganttChart.panelNames.firstChild.appendChild(this.cTaskNameItem[0]);}_ce.appendChild(this.createTaskDescItem());var _d5=[];if(_d0){_d5=this.createConnectingLinesDS();}this.cTaskItem.push(_d5);if(this.ganttChart.panelNames){var _d6=[];if(_d1){this.cTaskNameItem[0].style.left=_4.style(this.parentTask.cTaskNameItem[0],"left")+15+"px";_d6=this.createConnectingLinesPN();}this.checkWidthTaskNameItem();this.checkPosition();var _d7=null;if(_d2){_d7=this.createTreeImg();}this.cTaskNameItem.push(_d6);this.cTaskNameItem.push(_d7);}this.adjustPanelTime();return this;},checkPosition:function(){if(!this.ganttChart.withTaskId){return;}var pos=_4.coords(this.cTaskNameItem[0],true);if(this.taskIdentifier){if(this.childTask&&this.childTask.length>0){_4.forEach(this.childTask,function(_d8){_d8.checkPosition();},this);}_4.style(this.taskIdentifier,{"left":(pos.l+pos.w+4)+"px","top":(pos.t-1)+"px"});}else{this.taskIdentifier=_4.create("div",{id:"TaskId_"+this.taskItem.id,className:"ganttTaskIdentifier",title:this.taskItem.id,innerHTML:this.taskItem.id},this.cTaskNameItem[0].parentNode);_4.style(this.taskIdentifier,{left:(pos.l+pos.w+4)+"px",top:(pos.t-1)+"px"});}},createTreeImg:function(){var _d9=_4.create("div",{id:this.taskItem.id,className:"ganttImageTreeCollapse"});_4.attr(_d9,"tabIndex",0);_4.forEach(["onclick","onkeydown"],function(e){this.ganttChart._events.push(_4.connect(_d9,e,this,function(evt){if(e=="onkeydown"&&evt.keyCode!=_4.keys.ENTER){return;}if(this.isExpanded){_4.removeClass(_d9,"ganttImageTreeCollapse");_4.addClass(_d9,"ganttImageTreeExpand");this.isExpanded=false;this.hideChildTasks(this);this.shiftCurrentTasks(this,-this.hideTasksHeight);this.ganttChart.checkPosition();}else{_4.removeClass(_d9,"ganttImageTreeExpand");_4.addClass(_d9,"ganttImageTreeCollapse");this.isExpanded=true;this.shiftCurrentTasks(this,this.hideTasksHeight);this.showChildTasks(this,true);this.hideTasksHeight=0;this.ganttChart.checkPosition();}}));},this);this.ganttChart.panelNames.firstChild.appendChild(_d9);_4.addClass(_d9,"ganttTaskTreeImage");_4.style(_d9,{left:(_4.style(this.cTaskNameItem[0],"left")-12)+"px",top:(_4.style(this.cTaskNameItem[0],"top")+3)+"px"});return _d9;},setPreviousTask:function(_da){if(_da==""){this.clearPredTask();}else{var _db=this.taskItem;if(_db.id==_da){return false;}var _dc=this.project.getTaskById(_da);if(!_dc){return false;}var _dd=_dc.taskItem;var a1=_dd.parentTask==null,a2=_db.parentTask==null;if(a1&&!a2||!a1&&a2||!a1&&!a2&&(_dd.parentTask.id!=_db.parentTask.id)){return false;}var _de=_db.startTime.getTime(),_df=_dd.startTime.getTime(),_e0=_dd.duration*24*60*60*1000/_dc.ganttChart.hsPerDay;if((_df+_e0)>_de){return false;}this.clearPredTask();if(!this.ganttChart.checkPosPreviousTask(_dd,_db)){this.ganttChart.correctPosPreviousTask(_dd,_db,this);}_db.previousTaskId=_da;_db.previousTask=_dd;this.predTask=_dc;_dc.childPredTask.push(this);this.cTaskItem[1]=this.createConnectingLinesDS();}return true;},clearPredTask:function(){if(this.predTask){var ch=this.predTask.childPredTask;for(var i=0;i<ch.length;i++){if(ch[i]==this){ch.splice(i,1);break;}}for(var i=0;i<this.cTaskItem[1].length;i++){this.cTaskItem[1][i].parentNode.removeChild(this.cTaskItem[1][i]);}this.cTaskItem[1]=[];this.taskItem.previousTaskId=null;this.taskItem.previousTask=null;this.predTask=null;}},setStartTime:function(_e1,_e2){this.moveChild=_e2;this.getMoveInfo();var pos=this.ganttChart.getPosOnDate(_e1);if((parseInt(this.cTaskItem[0].firstChild.firstChild.width)+pos>this.maxPosXMove)&&(this.maxPosXMove!=-1)){this.maxPosXMove=-1;this.minPosXMove=-1;return false;}if(pos<this.minPosXMove){this.maxPosXMove=-1;this.minPosXMove=-1;return false;}this.cTaskItem[0].style.left=pos;var _e3=pos-this.posX;this.moveCurrentTaskItem(_e3,_e2);this.project.shiftProjectItem();this.descrTask.innerHTML=this.objKeyToStr(this.getTaskOwner());this.adjustPanelTime();this.posX=0;this.maxPosXMove=-1;this.minPosXMove=-1;return true;},setDuration:function(_e4){this.getResizeInfo();var _e5=this.ganttChart.getWidthOnDuration(_e4);if((_e5>this.maxWidthResize)&&(this.maxWidthResize!=-1)){return false;}else{if(_e5<this.minWidthResize){return false;}else{this.taskItemWidth=parseInt(this.cTaskItem[0].firstChild.firstChild.width);this.resizeTaskItem(_e5);this.endResizeItem();this.descrTask.innerHTML=this.objKeyToStr(this.getTaskOwner());return true;}}},setTaskOwner:function(_e6){_e6=(_e6==null||_e6==undefined)?"":_e6;this.taskItem.taskOwner=_e6;this.descrTask.innerHTML=this.objKeyToStr(this.getTaskOwner());return true;},setPercentCompleted:function(_e7){_e7=parseInt(_e7);if(isNaN(_e7)||_e7>100||_e7<0){return false;}var _e8=this.cTaskItem[0].childNodes[0].firstChild.rows[0],rc0=_e8.cells[0],rc1=_e8.cells[1];if((_e7!=0)&&(_e7!=100)){if((this.taskItem.percentage!=0)&&(this.taskItem.percentage!=100)){rc0.width=_e7+"%";rc1.width=100-_e7+"%";}else{if((this.taskItem.percentage==0)||(this.taskItem.percentage==100)){rc0.parentNode.removeChild(rc0);var _e9=_4.create("td",{height:this.ganttChart.heightTaskItem+"px",width:_e7+"%"},_e8);_e9.style.lineHeight="1px";var _ea=_4.create("div",{className:"ganttImageTaskProgressFilled"},_e9);_4.style(_ea,{width:(_e7*this.taskItem.duration*this.ganttChart.pixelsPerWorkHour)/100+"px",height:this.ganttChart.heightTaskItem+"px"});_e9=_4.create("td",{height:this.ganttChart.heightTaskItem+"px",width:(100-_e7)+"%"},_e8);_e9.style.lineHeight="1px";_ea=_4.create("div",{className:"ganttImageTaskProgressBg"},_e9);_4.style(_ea,{width:((100-_e7)*this.taskItem.duration*this.ganttChart.pixelsPerWorkHour)/100+"px",height:this.ganttChart.heightTaskItem+"px"});}}}else{if(_e7==0){if((this.taskItem.percentage!=0)&&(this.taskItem.percentage!=100)){rc0.parentNode.removeChild(rc0);rc1.width=100+"%";}else{_4.removeClass(rc0.firstChild,"ganttImageTaskProgressFilled");_4.addClass(rc0.firstChild,"ganttImageTaskProgressBg");}}else{if(_e7==100){if((this.taskItem.percentage!=0)&&(this.taskItem.percentage!=100)){rc1.parentNode.removeChild(rc1);rc0.width=100+"%";}else{_4.removeClass(rc0.firstChild,"ganttImageTaskProgressBg");_4.addClass(rc0.firstChild,"ganttImageTaskProgressFilled");}}}}this.taskItem.percentage=_e7;this.taskItemWidth=parseInt(this.cTaskItem[0].firstChild.firstChild.width);this.resizeTaskItem(this.taskItemWidth);this.endResizeItem();this.descrTask.innerHTML=this.objKeyToStr(this.getTaskOwner());return true;},setName:function(_eb){if(_eb){this.taskItem.name=_eb;this.cTaskNameItem[0].innerHTML=_eb;this.cTaskNameItem[0].title=_eb;this.checkWidthTaskNameItem();this.checkPosition();this.descrTask.innerHTML=this.objKeyToStr(this.getTaskOwner());this.adjustPanelTime();}}});_4.declare("dojox.gantt.GanttTaskItem",null,{constructor:function(_ec){this.id=_ec.id;this.name=_ec.name||this.id;this.startTime=_ec.startTime||new Date();this.duration=_ec.duration||8;this.percentage=_ec.percentage||0;this.previousTaskId=_ec.previousTaskId||"";this.taskOwner=_ec.taskOwner||"";this.cldTasks=[];this.cldPreTasks=[];this.parentTask=null;this.previousTask=null;this.project=null;this.nextChildTask=null;this.previousChildTask=null;this.nextParentTask=null;this.previousParentTask=null;},addChildTask:function(_ed){this.cldTasks.push(_ed);_ed.parentTask=this;},setProject:function(_ee){this.project=_ee;for(var j=0;j<this.cldTasks.length;j++){this.cldTasks[j].setProject(_ee);}}});}}};});