/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.layout.TableContainer"],["require","dijit.layout._LayoutWidget"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.layout.TableContainer"]){_4._hasResource["dojox.layout.TableContainer"]=true;_4.experimental("dojox.layout.TableContainer");_4.provide("dojox.layout.TableContainer");_4.require("dijit.layout._LayoutWidget");_4.declare("dojox.layout.TableContainer",_5.layout._LayoutWidget,{cols:1,labelWidth:"100",showLabels:true,orientation:"horiz",spacing:1,customClass:"",postCreate:function(){this.inherited(arguments);this._children=[];this.connect(this,"set",function(_7,_8){if(_8&&(_7=="orientation"||_7=="customClass"||_7=="cols")){this.layout();}});},startup:function(){if(this._started){return;}this.inherited(arguments);if(this._initialized){return;}var _9=this.getChildren();if(_9.length<1){return;}this._initialized=true;_4.addClass(this.domNode,"dijitTableLayout");_4.forEach(_9,function(_a){if(!_a.started&&!_a._started){_a.startup();}});this.resize();this.layout();},resize:function(){_4.forEach(this.getChildren(),function(_b){if(typeof _b.resize=="function"){_b.resize();}});},layout:function(){if(!this._initialized){return;}var _c=this.getChildren();var _d={};var _e=this;function _f(_10,_11,_12){if(_e.customClass!=""){var _13=_e.customClass+"-"+(_11||_10.tagName.toLowerCase());_4.addClass(_10,_13);if(arguments.length>2){_4.addClass(_10,_13+"-"+_12);}}};_4.forEach(this._children,_4.hitch(this,function(_14){_d[_14.id]=_14;}));_4.forEach(_c,_4.hitch(this,function(_15,_16){if(!_d[_15.id]){this._children.push(_15);}}));var _17=_4.create("table",{"width":"100%","class":"tableContainer-table tableContainer-table-"+this.orientation,"cellspacing":this.spacing},this.domNode);var _18=_4.create("tbody");_17.appendChild(_18);_f(_17,"table",this.orientation);var _19=Math.floor(100/this.cols)+"%";var _1a=_4.create("tr",{},_18);var _1b=(!this.showLabels||this.orientation=="horiz")?_1a:_4.create("tr",{},_18);var _1c=this.cols*(this.showLabels?2:1);var _1d=0;_4.forEach(this._children,_4.hitch(this,function(_1e,_1f){var _20=_1e.colspan||1;if(_20>1){_20=this.showLabels?Math.min(_1c-1,_20*2-1):Math.min(_1c,_20);}if(_1d+_20-1+(this.showLabels?1:0)>=_1c){_1d=0;_1a=_4.create("tr",{},_18);_1b=this.orientation=="horiz"?_1a:_4.create("tr",{},_18);}var _21;if(this.showLabels){_21=_4.create("td",{"class":"tableContainer-labelCell"},_1a);if(_1e.spanLabel){_4.attr(_21,this.orientation=="vert"?"rowspan":"colspan",2);}else{_f(_21,"labelCell");var _22={"for":_1e.get("id")};var _23=_4.create("label",_22,_21);if(Number(this.labelWidth)>-1||String(this.labelWidth).indexOf("%")>-1){_4.style(_21,"width",String(this.labelWidth).indexOf("%")<0?this.labelWidth+"px":this.labelWidth);}_23.innerHTML=_1e.get("label")||_1e.get("title");}}var _24;if(_1e.spanLabel&&_21){_24=_21;}else{_24=_4.create("td",{"class":"tableContainer-valueCell"},_1b);}if(_20>1){_4.attr(_24,"colspan",_20);}_f(_24,"valueCell",_1f);_24.appendChild(_1e.domNode);_1d+=_20+(this.showLabels?1:0);}));if(this.table){this.table.parentNode.removeChild(this.table);}_4.forEach(_c,function(_25){if(typeof _25.layout=="function"){_25.layout();}});this.table=_17;this.resize();},destroyDescendants:function(_26){_4.forEach(this._children,function(_27){_27.destroyRecursive(_26);});},_setSpacingAttr:function(_28){this.spacing=_28;if(this.table){this.table.cellspacing=Number(_28);}}});_4.extend(_5._Widget,{label:"",title:"",spanLabel:false,colspan:1});}}};});