/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dijit.layout.SplitContainer"],["require","dojo.cookie"],["require","dijit.layout._LayoutWidget"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dijit.layout.SplitContainer"]){_4._hasResource["dijit.layout.SplitContainer"]=true;_4.provide("dijit.layout.SplitContainer");_4.require("dojo.cookie");_4.require("dijit.layout._LayoutWidget");_4.declare("dijit.layout.SplitContainer",_5.layout._LayoutWidget,{constructor:function(){_4.deprecated("dijit.layout.SplitContainer is deprecated","use BorderContainer with splitter instead",2);},activeSizing:false,sizerWidth:7,orientation:"horizontal",persist:true,baseClass:"dijitSplitContainer",postMixInProperties:function(){this.inherited("postMixInProperties",arguments);this.isHorizontal=(this.orientation=="horizontal");},postCreate:function(){this.inherited(arguments);this.sizers=[];if(_4.isMozilla){this.domNode.style.overflow="-moz-scrollbars-none";}if(typeof this.sizerWidth=="object"){try{this.sizerWidth=parseInt(this.sizerWidth.toString());}catch(e){this.sizerWidth=7;}}var _7=_4.doc.createElement("div");this.virtualSizer=_7;_7.style.position="relative";_7.style.zIndex=10;_7.className=this.isHorizontal?"dijitSplitContainerVirtualSizerH":"dijitSplitContainerVirtualSizerV";this.domNode.appendChild(_7);_4.setSelectable(_7,false);},destroy:function(){delete this.virtualSizer;_4.forEach(this._ownconnects,_4.disconnect);this.inherited(arguments);},startup:function(){if(this._started){return;}_4.forEach(this.getChildren(),function(_8,i,_9){this._setupChild(_8);if(i<_9.length-1){this._addSizer();}},this);if(this.persist){this._restoreState();}this.inherited(arguments);},_setupChild:function(_a){this.inherited(arguments);_a.domNode.style.position="absolute";_4.addClass(_a.domNode,"dijitSplitPane");},_onSizerMouseDown:function(e){if(e.target.id){for(var i=0;i<this.sizers.length;i++){if(this.sizers[i].id==e.target.id){break;}}if(i<this.sizers.length){this.beginSizing(e,i);}}},_addSizer:function(_b){_b=_b===undefined?this.sizers.length:_b;var _c=_4.doc.createElement("div");_c.id=_5.getUniqueId("dijit_layout_SplitterContainer_Splitter");this.sizers.splice(_b,0,_c);this.domNode.appendChild(_c);_c.className=this.isHorizontal?"dijitSplitContainerSizerH":"dijitSplitContainerSizerV";var _d=_4.doc.createElement("div");_d.className="thumb";_c.appendChild(_d);this.connect(_c,"onmousedown","_onSizerMouseDown");_4.setSelectable(_c,false);},removeChild:function(_e){if(this.sizers.length){var i=_4.indexOf(this.getChildren(),_e);if(i!=-1){if(i==this.sizers.length){i--;}_4.destroy(this.sizers[i]);this.sizers.splice(i,1);}}this.inherited(arguments);if(this._started){this.layout();}},addChild:function(_f,_10){this.inherited(arguments);if(this._started){var _11=this.getChildren();if(_11.length>1){this._addSizer(_10);}this.layout();}},layout:function(){this.paneWidth=this._contentBox.w;this.paneHeight=this._contentBox.h;var _12=this.getChildren();if(!_12.length){return;}var _13=this.isHorizontal?this.paneWidth:this.paneHeight;if(_12.length>1){_13-=this.sizerWidth*(_12.length-1);}var _14=0;_4.forEach(_12,function(_15){_14+=_15.sizeShare;});var _16=_13/_14;var _17=0;_4.forEach(_12.slice(0,_12.length-1),function(_18){var _19=Math.round(_16*_18.sizeShare);_18.sizeActual=_19;_17+=_19;});_12[_12.length-1].sizeActual=_13-_17;this._checkSizes();var pos=0;var _1a=_12[0].sizeActual;this._movePanel(_12[0],pos,_1a);_12[0].position=pos;pos+=_1a;if(!this.sizers){return;}_4.some(_12.slice(1),function(_1b,i){if(!this.sizers[i]){return true;}this._moveSlider(this.sizers[i],pos,this.sizerWidth);this.sizers[i].position=pos;pos+=this.sizerWidth;_1a=_1b.sizeActual;this._movePanel(_1b,pos,_1a);_1b.position=pos;pos+=_1a;},this);},_movePanel:function(_1c,pos,_1d){if(this.isHorizontal){_1c.domNode.style.left=pos+"px";_1c.domNode.style.top=0;var box={w:_1d,h:this.paneHeight};if(_1c.resize){_1c.resize(box);}else{_4.marginBox(_1c.domNode,box);}}else{_1c.domNode.style.left=0;_1c.domNode.style.top=pos+"px";var box={w:this.paneWidth,h:_1d};if(_1c.resize){_1c.resize(box);}else{_4.marginBox(_1c.domNode,box);}}},_moveSlider:function(_1e,pos,_1f){if(this.isHorizontal){_1e.style.left=pos+"px";_1e.style.top=0;_4.marginBox(_1e,{w:_1f,h:this.paneHeight});}else{_1e.style.left=0;_1e.style.top=pos+"px";_4.marginBox(_1e,{w:this.paneWidth,h:_1f});}},_growPane:function(_20,_21){if(_20>0){if(_21.sizeActual>_21.sizeMin){if((_21.sizeActual-_21.sizeMin)>_20){_21.sizeActual=_21.sizeActual-_20;_20=0;}else{_20-=_21.sizeActual-_21.sizeMin;_21.sizeActual=_21.sizeMin;}}}return _20;},_checkSizes:function(){var _22=0;var _23=0;var _24=this.getChildren();_4.forEach(_24,function(_25){_23+=_25.sizeActual;_22+=_25.sizeMin;});if(_22<=_23){var _26=0;_4.forEach(_24,function(_27){if(_27.sizeActual<_27.sizeMin){_26+=_27.sizeMin-_27.sizeActual;_27.sizeActual=_27.sizeMin;}});if(_26>0){var _28=this.isDraggingLeft?_24.reverse():_24;_4.forEach(_28,function(_29){_26=this._growPane(_26,_29);},this);}}else{_4.forEach(_24,function(_2a){_2a.sizeActual=Math.round(_23*(_2a.sizeMin/_22));});}},beginSizing:function(e,i){var _2b=this.getChildren();this.paneBefore=_2b[i];this.paneAfter=_2b[i+1];this.isSizing=true;this.sizingSplitter=this.sizers[i];if(!this.cover){this.cover=_4.create("div",{style:{position:"absolute",zIndex:5,top:0,left:0,width:"100%",height:"100%"}},this.domNode);}else{this.cover.style.zIndex=5;}this.sizingSplitter.style.zIndex=6;this.originPos=_4.position(_2b[0].domNode,true);if(this.isHorizontal){var _2c=e.layerX||e.offsetX||0;var _2d=e.pageX;this.originPos=this.originPos.x;}else{var _2c=e.layerY||e.offsetY||0;var _2d=e.pageY;this.originPos=this.originPos.y;}this.startPoint=this.lastPoint=_2d;this.screenToClientOffset=_2d-_2c;this.dragOffset=this.lastPoint-this.paneBefore.sizeActual-this.originPos-this.paneBefore.position;if(!this.activeSizing){this._showSizingLine();}this._ownconnects=[];this._ownconnects.push(_4.connect(_4.doc.documentElement,"onmousemove",this,"changeSizing"));this._ownconnects.push(_4.connect(_4.doc.documentElement,"onmouseup",this,"endSizing"));_4.stopEvent(e);},changeSizing:function(e){if(!this.isSizing){return;}this.lastPoint=this.isHorizontal?e.pageX:e.pageY;this.movePoint();if(this.activeSizing){this._updateSize();}else{this._moveSizingLine();}_4.stopEvent(e);},endSizing:function(e){if(!this.isSizing){return;}if(this.cover){this.cover.style.zIndex=-1;}if(!this.activeSizing){this._hideSizingLine();}this._updateSize();this.isSizing=false;if(this.persist){this._saveState(this);}_4.forEach(this._ownconnects,_4.disconnect);},movePoint:function(){var p=this.lastPoint-this.screenToClientOffset;var a=p-this.dragOffset;a=this.legaliseSplitPoint(a);p=a+this.dragOffset;this.lastPoint=p+this.screenToClientOffset;},legaliseSplitPoint:function(a){a+=this.sizingSplitter.position;this.isDraggingLeft=!!(a>0);if(!this.activeSizing){var min=this.paneBefore.position+this.paneBefore.sizeMin;if(a<min){a=min;}var max=this.paneAfter.position+(this.paneAfter.sizeActual-(this.sizerWidth+this.paneAfter.sizeMin));if(a>max){a=max;}}a-=this.sizingSplitter.position;this._checkSizes();return a;},_updateSize:function(){var pos=this.lastPoint-this.dragOffset-this.originPos;var _2e=this.paneBefore.position;var _2f=this.paneAfter.position+this.paneAfter.sizeActual;this.paneBefore.sizeActual=pos-_2e;this.paneAfter.position=pos+this.sizerWidth;this.paneAfter.sizeActual=_2f-this.paneAfter.position;_4.forEach(this.getChildren(),function(_30){_30.sizeShare=_30.sizeActual;});if(this._started){this.layout();}},_showSizingLine:function(){this._moveSizingLine();_4.marginBox(this.virtualSizer,this.isHorizontal?{w:this.sizerWidth,h:this.paneHeight}:{w:this.paneWidth,h:this.sizerWidth});this.virtualSizer.style.display="block";},_hideSizingLine:function(){this.virtualSizer.style.display="none";},_moveSizingLine:function(){var pos=(this.lastPoint-this.startPoint)+this.sizingSplitter.position;_4.style(this.virtualSizer,(this.isHorizontal?"left":"top"),pos+"px");},_getCookieName:function(i){return this.id+"_"+i;},_restoreState:function(){_4.forEach(this.getChildren(),function(_31,i){var _32=this._getCookieName(i);var _33=_4.cookie(_32);if(_33){var pos=parseInt(_33);if(typeof pos=="number"){_31.sizeShare=pos;}}},this);},_saveState:function(){if(!this.persist){return;}_4.forEach(this.getChildren(),function(_34,i){_4.cookie(this._getCookieName(i),_34.sizeShare,{expires:365});},this);}});_4.extend(_5._Widget,{sizeMin:10,sizeShare:10});}}};});