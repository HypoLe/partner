/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.grid.enhanced._FocusManager"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.grid.enhanced._FocusManager"]){_4._hasResource["dojox.grid.enhanced._FocusManager"]=true;_4.provide("dojox.grid.enhanced._FocusManager");_4.declare("dojox.grid.enhanced._FocusArea",null,{constructor:function(_7,_8){this._fm=_8;this._evtStack=[_7.name];var _9=function(){return true;};_7.onFocus=_7.onFocus||_9;_7.onBlur=_7.onBlur||_9;_7.onMove=_7.onMove||_9;_7.onKeyUp=_7.onKeyUp||_9;_7.onKeyDown=_7.onKeyDown||_9;_4.mixin(this,_7);},move:function(_a,_b,_c){if(this.name){var i,_d=this._evtStack.length;for(i=_d-1;i>=0;--i){if(this._fm._areas[this._evtStack[i]].onMove(_a,_b,_c)===false){return false;}}}return true;},_onKeyEvent:function(_e,_f){if(this.name){var i,len=this._evtStack.length;for(i=len-1;i>=0;--i){if(this._fm._areas[this._evtStack[i]][_f](_e,false)===false){return false;}}for(i=0;i<len;++i){if(this._fm._areas[this._evtStack[i]][_f](_e,true)===false){return false;}}}return true;},keydown:function(evt){return this._onKeyEvent(evt,"onKeyDown");},keyup:function(evt){return this._onKeyEvent(evt,"onKeyUp");},contentMouseEventPlanner:function(){return 0;},headerMouseEventPlanner:function(){return 0;}});_4.declare("dojox.grid.enhanced._FocusManager",_6.grid._FocusManager,{_stopEvent:function(evt){try{if(evt&&evt.preventDefault){_4.stopEvent(evt);}}catch(e){}},constructor:function(_10){this.grid=_10;this._areas={};this._areaQueue=[];this._contentMouseEventHandlers=[];this._headerMouseEventHandlers=[];this._currentAreaIdx=-1;this._gridBlured=true;this._connects.push(_4.connect(_10,"onBlur",this,"_doBlur"));this._connects.push(_4.connect(_10.scroller,"renderPage",this,"_delayedCellFocus"));this.addArea({name:"header",onFocus:_4.hitch(this,this.focusHeader),onBlur:_4.hitch(this,this._blurHeader),onMove:_4.hitch(this,this._navHeader),getRegions:_4.hitch(this,this._findHeaderCells),onRegionFocus:_4.hitch(this,this.doColHeaderFocus),onRegionBlur:_4.hitch(this,this.doColHeaderBlur),onKeyDown:_4.hitch(this,this._onHeaderKeyDown)});this.addArea({name:"content",onFocus:_4.hitch(this,this._focusContent),onBlur:_4.hitch(this,this._blurContent),onMove:_4.hitch(this,this._navContent),onKeyDown:_4.hitch(this,this._onContentKeyDown)});this.addArea({name:"editableCell",onFocus:_4.hitch(this,this._focusEditableCell),onBlur:_4.hitch(this,this._blurEditableCell),onKeyDown:_4.hitch(this,this._onEditableCellKeyDown),onContentMouseEvent:_4.hitch(this,this._onEditableCellMouseEvent),contentMouseEventPlanner:function(evt,_11){return -1;}});this.placeArea("header");this.placeArea("content");this.placeArea("editableCell");this.placeArea("editableCell","above","content");},destroy:function(){for(var _12 in this._areas){var _13=this._areas[_12];_4.forEach(_13._connects,_4.disconnect);_13._connects=null;if(_13.uninitialize){_13.uninitialize();}}this.inherited(arguments);},addArea:function(_14){if(_14.name&&_4.isString(_14.name)){if(this._areas[_14.name]){_4.forEach(_14._connects,_4.disconnect);}this._areas[_14.name]=new _6.grid.enhanced._FocusArea(_14,this);if(_14.onHeaderMouseEvent){this._headerMouseEventHandlers.push(_14.name);}if(_14.onContentMouseEvent){this._contentMouseEventHandlers.push(_14.name);}}},getArea:function(_15){return this._areas[_15];},_bindAreaEvents:function(){var _16,hdl,_17=this._areas;_4.forEach(this._areaQueue,function(_18){_16=_17[_18];if(!_16._initialized&&_4.isFunction(_16.initialize)){_16.initialize();_16._initialized=true;}if(_16.getRegions){_16._regions=_16.getRegions()||[];_4.forEach(_16._connects||[],_4.disconnect);_16._connects=[];_4.forEach(_16._regions,function(r){if(_16.onRegionFocus){hdl=_4.connect(r,"onfocus",_16.onRegionFocus);_16._connects.push(hdl);}if(_16.onRegionBlur){hdl=_4.connect(r,"onblur",_16.onRegionBlur);_16._connects.push(hdl);}});}});},removeArea:function(_19){var _1a=this._areas[_19];if(_1a){this.ignoreArea(_19);var i=_4.indexOf(this._contentMouseEventHandlers,_19);if(i>=0){this._contentMouseEventHandlers.splice(i,1);}i=_4.indexOf(this._headerMouseEventHandlers,_19);if(i>=0){this._headerMouseEventHandlers.splice(i,1);}_4.forEach(_1a._connects,_4.disconnect);if(_1a.uninitialize){_1a.uninitialize();}delete this._areas[_19];}},currentArea:function(_1b,_1c){var idx,cai=this._currentAreaIdx;if(_4.isString(_1b)&&(idx=_4.indexOf(this._areaQueue,_1b))>=0){if(cai!=idx){this.tabbingOut=false;if(_1c&&cai>=0&&cai<this._areaQueue.length){this._areas[this._areaQueue[cai]].onBlur();}this._currentAreaIdx=idx;}}else{return (cai<0||cai>=this._areaQueue.length)?new _6.grid.enhanced._FocusArea({},this):this._areas[this._areaQueue[this._currentAreaIdx]];}return null;},placeArea:function(_1d,pos,_1e){if(!this._areas[_1d]){return;}var idx=_4.indexOf(this._areaQueue,_1e);switch(pos){case "after":if(idx>=0){++idx;}case "before":if(idx>=0){this._areaQueue.splice(idx,0,_1d);break;}default:this._areaQueue.push(_1d);break;case "above":var _1f=true;case "below":var _20=this._areas[_1e];if(_20){if(_1f){_20._evtStack.push(_1d);}else{_20._evtStack.splice(0,0,_1d);}}}},ignoreArea:function(_21){this._areaQueue=_4.filter(this._areaQueue,function(_22){return _22!=_21;});},focusArea:function(_23,evt){var idx;if(typeof _23=="number"){idx=_23<0?this._areaQueue.length+_23:_23;}else{idx=_4.indexOf(this._areaQueue,_4.isString(_23)?_23:(_23&&_23.name));}if(idx<0){idx=0;}var _24=idx-this._currentAreaIdx;this._gridBlured=false;if(_24){this.tab(_24,evt);}else{this.currentArea().onFocus(evt,_24);}},tab:function(_25,evt){this._gridBlured=false;this.tabbingOut=false;if(_25===0){return;}var cai=this._currentAreaIdx;var dir=_25>0?1:-1;if(cai<0||cai>=this._areaQueue.length){cai=(this._currentAreaIdx+=_25);}else{var _26=this._areas[this._areaQueue[cai]].onBlur(evt,_25);if(_26===true){cai=(this._currentAreaIdx+=_25);}else{if(_4.isString(_26)&&this._areas[_26]){cai=this._currentAreaIdx=_4.indexOf(this._areaQueue,_26);}}}for(;cai>=0&&cai<this._areaQueue.length;cai+=dir){this._currentAreaIdx=cai;if(this._areaQueue[cai]&&this._areas[this._areaQueue[cai]].onFocus(evt,_25)){return;}}this.tabbingOut=true;if(_25<0){this._currentAreaIdx=-1;_5.focus(this.grid.domNode);}else{this._currentAreaIdx=this._areaQueue.length;_5.focus(this.grid.lastFocusNode);}},_onMouseEvent:function(_27,evt){var _28=_27.toLowerCase(),_29=this["_"+_28+"MouseEventHandlers"],res=_4.map(_29,function(_2a){return {"area":_2a,"idx":this._areas[_2a][_28+"MouseEventPlanner"](evt,_29)};},this).sort(function(a,b){return b.idx-a.idx;}),_2b=_4.map(res,function(_2c){return res.area;}),i=res.length;while(--i>=0){if(this._areas[res[i].area]["on"+_27+"MouseEvent"](evt,_2b)===false){return;}}},contentMouseEvent:function(evt){this._onMouseEvent("Content",evt);},headerMouseEvent:function(evt){this._onMouseEvent("Header",evt);},initFocusView:function(){this.focusView=this.grid.views.getFirstScrollingView()||this.focusView||this.grid.views.views[0];this._bindAreaEvents();},isNavHeader:function(){return this._areaQueue[this._currentAreaIdx]=="header";},previousKey:function(e){this.tab(-1,e);},nextKey:function(e){this.tab(1,e);},setFocusCell:function(_2d,_2e){if(_2d){this.currentArea(this.grid.edit.isEditing()?"editableCell":"content",true);this._focusifyCellNode(false);this.cell=_2d;this.rowIndex=_2e;this._focusifyCellNode(true);}this.grid.onCellFocus(this.cell,this.rowIndex);},doFocus:function(e){if(e&&e.target==e.currentTarget&&!this.tabbingOut){if(this._gridBlured){this._gridBlured=false;if(this._currentAreaIdx<0||this._currentAreaIdx>=this._areaQueue.length){this.focusArea(0,e);}else{this.focusArea(this._currentAreaIdx,e);}}}else{this.tabbingOut=false;}_4.stopEvent(e);},_doBlur:function(){this._gridBlured=true;},doLastNodeFocus:function(e){if(this.tabbingOut){this.tabbingOut=false;}else{this.focusArea(-1,e);}},_delayedHeaderFocus:function(){if(this.isNavHeader()){this.focusHeader();}},_delayedCellFocus:function(){this.currentArea("header",true);this.focusArea(this._currentAreaIdx);},_changeMenuBindNode:function(_2f,_30){var hm=this.grid.headerMenu;if(hm&&this._contextMenuBindNode==_2f){hm.unBindDomNode(_2f);hm.bindDomNode(_30);this._contextMenuBindNode=_30;}},focusHeader:function(evt,_31){var _32=false;this.inherited(arguments);if(this._colHeadNode&&_4.style(this._colHeadNode,"display")!="none"){_5.focus(this._colHeadNode);this._stopEvent(evt);_32=true;}return _32;},_blurHeader:function(evt,_33){if(this._colHeadNode){_4.removeClass(this._colHeadNode,this.focusClass);}_4.removeAttr(this.grid.domNode,"aria-activedescendant");this._changeMenuBindNode(this.grid.domNode,this.grid.viewsHeaderNode);this._colHeadNode=this._colHeadFocusIdx=null;return true;},_navHeader:function(_34,_35,evt){var _36=_35<0?-1:1,_37=_4.indexOf(this._findHeaderCells(),this._colHeadNode);if(_37>=0&&(evt.shiftKey&&evt.ctrlKey)){this.colSizeAdjust(evt,_37,_36*5);return;}this.move(_34,_35);},_onHeaderKeyDown:function(e,_38){if(_38){var dk=_4.keys;switch(e.keyCode){case dk.ENTER:case dk.SPACE:var _39=this.getHeaderIndex();if(_39>=0&&!this.grid.pluginMgr.isFixedCell(e.cell)){this.grid.setSortIndex(_39,null,e);_4.stopEvent(e);}break;}}return true;},_setActiveColHeader:function(){this.inherited(arguments);_5.focus(this._colHeadNode);},findAndFocusGridCell:function(){this._focusContent();},_focusContent:function(evt,_3a){var _3b=true;var _3c=(this.grid.rowCount===0);if(this.isNoFocusCell()&&!_3c){for(var i=0,_3d=this.grid.getCell(0);_3d&&_3d.hidden;_3d=this.grid.getCell(++i)){}this.setFocusIndex(0,_3d?i:0);}else{if(this.cell&&!_3c){if(this.focusView&&!this.focusView.rowNodes[this.rowIndex]){this.grid.scrollToRow(this.rowIndex);this.focusGrid();}else{this.setFocusIndex(this.rowIndex,this.cell.index);}}else{_3b=false;}}if(_3b){this._stopEvent(evt);}return _3b;},_blurContent:function(evt,_3e){this._focusifyCellNode(false);return true;},_navContent:function(_3f,_40,evt){if((this.rowIndex===0&&_3f<0)||(this.rowIndex===this.grid.rowCount-1&&_3f>0)){return;}this._colHeadNode=null;this.move(_3f,_40,evt);if(evt){_4.stopEvent(evt);}},_onContentKeyDown:function(e,_41){if(_41){var dk=_4.keys,s=this.grid.scroller;switch(e.keyCode){case dk.ENTER:case dk.SPACE:var g=this.grid;if(g.indirectSelection){break;}g.selection.clickSelect(this.rowIndex,_4.isCopyKey(e),e.shiftKey);g.onRowClick(e);_4.stopEvent(e);break;case dk.PAGE_UP:if(this.rowIndex!==0){if(this.rowIndex!=s.firstVisibleRow+1){this._navContent(s.firstVisibleRow-this.rowIndex,0);}else{this.grid.setScrollTop(s.findScrollTop(this.rowIndex-1));this._navContent(s.firstVisibleRow-s.lastVisibleRow+1,0);}_4.stopEvent(e);}break;case dk.PAGE_DOWN:if(this.rowIndex+1!=this.grid.rowCount){_4.stopEvent(e);if(this.rowIndex!=s.lastVisibleRow-1){this._navContent(s.lastVisibleRow-this.rowIndex-1,0);}else{this.grid.setScrollTop(s.findScrollTop(this.rowIndex+1));this._navContent(s.lastVisibleRow-s.firstVisibleRow-1,0);}_4.stopEvent(e);}break;}}return true;},_blurFromEditableCell:false,_isNavigating:false,_navElems:null,_focusEditableCell:function(evt,_42){var _43=false;if(this._isNavigating){_43=true;}else{if(this.grid.edit.isEditing()&&this.cell){if(this._blurFromEditableCell||!this._blurEditableCell(evt,_42)){this.setFocusIndex(this.rowIndex,this.cell.index);_43=true;}this._stopEvent(evt);}}return _43;},_applyEditableCell:function(){try{this.grid.edit.apply();}catch(e){console.warn("_FocusManager._applyEditableCell() error:",e);}},_blurEditableCell:function(evt,_44){this._blurFromEditableCell=false;if(this._isNavigating){var _45=true;if(evt){var _46=this._navElems;var _47=_46.lowest||_46.first;var _48=_46.last||_46.highest||_47;var _49=_4.isIE?evt.srcElement:evt.target;_45=_49==(_44>0?_48:_47);}if(_45){this._isNavigating=false;return "content";}return false;}else{if(this.grid.edit.isEditing()&&this.cell){if(!_44||typeof _44!="number"){return false;}var dir=_44>0?1:-1;var cc=this.grid.layout.cellCount;for(var _4a,col=this.cell.index+dir;col>=0&&col<cc;col+=dir){_4a=this.grid.getCell(col);if(_4a.editable){this.cell=_4a;this._blurFromEditableCell=true;return false;}}if((this.rowIndex>0||dir==1)&&(this.rowIndex<this.grid.rowCount||dir==-1)){this.rowIndex+=dir;for(col=dir>0?0:cc-1;col>=0&&col<cc;col+=dir){_4a=this.grid.getCell(col);if(_4a.editable){this.cell=_4a;break;}}this._applyEditableCell();return "content";}}}return true;},_initNavigatableElems:function(){this._navElems=_5._getTabNavigable(this.cell.getNode(this.rowIndex));},_onEditableCellKeyDown:function(e,_4b){var dk=_4.keys,g=this.grid,_4c=g.edit,_4d=false,_4e=true;switch(e.keyCode){case dk.ENTER:if(_4b&&_4c.isEditing()){this._applyEditableCell();_4d=true;_4.stopEvent(e);}case dk.SPACE:if(!_4b&&this._isNavigating){_4e=false;break;}if(_4b){if(!this.cell.editable&&this.cell.navigatable){this._initNavigatableElems();var _4f=this._navElems.lowest||this._navElems.first;if(_4f){this._isNavigating=true;_5.focus(_4f);_4.stopEvent(e);this.currentArea("editableCell",true);break;}}if(!_4d&&!_4c.isEditing()&&!g.pluginMgr.isFixedCell(this.cell)){_4c.setEditCell(this.cell,this.rowIndex);}if(_4d){this.currentArea("content",true);}else{if(this.cell.editable&&g.canEdit()){this.currentArea("editableCell",true);}}}break;case dk.PAGE_UP:case dk.PAGE_DOWN:if(!_4b&&_4c.isEditing()){_4e=false;}break;case dk.ESCAPE:if(!_4b){_4c.cancel();this.currentArea("content",true);}}return _4e;},_onEditableCellMouseEvent:function(evt){if(evt.type=="click"){var _50=this.cell||evt.cell;if(_50&&!_50.editable&&_50.navigatable){this._initNavigatableElems();if(this._navElems.lowest||this._navElems.first){var _51=_4.isIE?evt.srcElement:evt.target;if(_51!=_50.getNode(evt.rowIndex)){this._isNavigating=true;this.focusArea("editableCell",evt);_5.focus(_51);return false;}}}else{if(this.grid.singleClickEdit){this.currentArea("editableCell");return false;}}}return true;}});}}};});