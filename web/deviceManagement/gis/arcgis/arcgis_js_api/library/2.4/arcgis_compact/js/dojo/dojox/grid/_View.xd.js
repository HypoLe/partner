/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.grid._View"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dojox.grid._Builder"],["require","dojox.html.metrics"],["require","dojox.grid.util"],["require","dojo.dnd.Source"],["require","dojo.dnd.Manager"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.grid._View"]){_4._hasResource["dojox.grid._View"]=true;_4.provide("dojox.grid._View");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dojox.grid._Builder");_4.require("dojox.html.metrics");_4.require("dojox.grid.util");_4.require("dojo.dnd.Source");_4.require("dojo.dnd.Manager");(function(){var _7=function(_8,_9){return _8.style.cssText==undefined?_8.getAttribute("style"):_8.style.cssText;};_4.declare("dojox.grid._View",[_5._Widget,_5._Templated],{defaultWidth:"18em",viewWidth:"",templateString:"<div class=\"dojoxGridView\" role=\"presentation\">\r\n\t<div class=\"dojoxGridHeader\" dojoAttachPoint=\"headerNode\" role=\"presentation\">\r\n\t\t<div dojoAttachPoint=\"headerNodeContainer\" style=\"width:9000em\" role=\"presentation\">\r\n\t\t\t<div dojoAttachPoint=\"headerContentNode\" role=\"row\"></div>\r\n\t\t</div>\r\n\t</div>\r\n\t<input type=\"checkbox\" class=\"dojoxGridHiddenFocus\" dojoAttachPoint=\"hiddenFocusNode\" role=\"presentation\" />\r\n\t<input type=\"checkbox\" class=\"dojoxGridHiddenFocus\" role=\"presentation\" />\r\n\t<div class=\"dojoxGridScrollbox\" dojoAttachPoint=\"scrollboxNode\" role=\"presentation\">\r\n\t\t<div class=\"dojoxGridContent\" dojoAttachPoint=\"contentNode\" hidefocus=\"hidefocus\" role=\"presentation\"></div>\r\n\t</div>\r\n</div>\r\n",themeable:false,classTag:"dojoxGrid",marginBottom:0,rowPad:2,_togglingColumn:-1,_headerBuilderClass:_6.grid._HeaderBuilder,_contentBuilderClass:_6.grid._ContentBuilder,postMixInProperties:function(){this.rowNodes={};},postCreate:function(){this.connect(this.scrollboxNode,"onscroll","doscroll");_6.grid.util.funnelEvents(this.contentNode,this,"doContentEvent",["mouseover","mouseout","click","dblclick","contextmenu","mousedown"]);_6.grid.util.funnelEvents(this.headerNode,this,"doHeaderEvent",["dblclick","mouseover","mouseout","mousemove","mousedown","click","contextmenu"]);this.content=new this._contentBuilderClass(this);this.header=new this._headerBuilderClass(this);if(!_4._isBodyLtr()){this.headerNodeContainer.style.width="";}},destroy:function(){_4.destroy(this.headerNode);delete this.headerNode;for(var i in this.rowNodes){_4.destroy(this.rowNodes[i]);}this.rowNodes={};if(this.source){this.source.destroy();}this.inherited(arguments);},focus:function(){if(_4.isIE||_4.isWebKit||_4.isOpera){this.hiddenFocusNode.focus();}else{this.scrollboxNode.focus();}},setStructure:function(_a){var vs=(this.structure=_a);if(vs.width&&!isNaN(vs.width)){this.viewWidth=vs.width+"em";}else{this.viewWidth=vs.width||(vs.noscroll?"auto":this.viewWidth);}this._onBeforeRow=vs.onBeforeRow||function(){};this._onAfterRow=vs.onAfterRow||function(){};this.noscroll=vs.noscroll;if(this.noscroll){this.scrollboxNode.style.overflow="hidden";}this.simpleStructure=Boolean(vs.cells.length==1);this.testFlexCells();this.updateStructure();},_cleanupRowWidgets:function(_b){if(_b){_4.forEach(_4.query("[widgetId]",_b).map(_5.byNode),function(w){if(w._destroyOnRemove){w.destroy();delete w;}else{if(w.domNode&&w.domNode.parentNode){w.domNode.parentNode.removeChild(w.domNode);}}});}},onBeforeRow:function(_c,_d){this._onBeforeRow(_c,_d);if(_c>=0){this._cleanupRowWidgets(this.getRowNode(_c));}},onAfterRow:function(_e,_f,_10){this._onAfterRow(_e,_f,_10);var g=this.grid;_4.forEach(_4.query(".dojoxGridStubNode",_10),function(n){if(n&&n.parentNode){var lw=n.getAttribute("linkWidget");var _11=window.parseInt(_4.attr(n,"cellIdx"),10);var _12=g.getCell(_11);var w=_5.byId(lw);if(w){n.parentNode.replaceChild(w.domNode,n);if(!w._started){w.startup();}}else{n.innerHTML="";}}},this);},testFlexCells:function(){this.flexCells=false;for(var j=0,row;(row=this.structure.cells[j]);j++){for(var i=0,_13;(_13=row[i]);i++){_13.view=this;this.flexCells=this.flexCells||_13.isFlex();}}return this.flexCells;},updateStructure:function(){this.header.update();this.content.update();},getScrollbarWidth:function(){var _14=this.hasVScrollbar();var _15=_4.style(this.scrollboxNode,"overflow");if(this.noscroll||!_15||_15=="hidden"){_14=false;}else{if(_15=="scroll"){_14=true;}}return (_14?_6.html.metrics.getScrollbar().w:0);},getColumnsWidth:function(){var h=this.headerContentNode;return h&&h.firstChild?h.firstChild.offsetWidth:0;},setColumnsWidth:function(_16){this.headerContentNode.firstChild.style.width=_16+"px";if(this.viewWidth){this.viewWidth=_16+"px";}},getWidth:function(){return this.viewWidth||(this.getColumnsWidth()+this.getScrollbarWidth())+"px";},getContentWidth:function(){return Math.max(0,_4._getContentBox(this.domNode).w-this.getScrollbarWidth())+"px";},render:function(){this.scrollboxNode.style.height="";this.renderHeader();if(this._togglingColumn>=0){this.setColumnsWidth(this.getColumnsWidth()-this._togglingColumn);this._togglingColumn=-1;}var _17=this.grid.layout.cells;var _18=_4.hitch(this,function(_19,_1a){!_4._isBodyLtr()&&(_1a=!_1a);var inc=_1a?-1:1;var idx=this.header.getCellNodeIndex(_19)+inc;var _1b=_17[idx];while(_1b&&_1b.getHeaderNode()&&_1b.getHeaderNode().style.display=="none"){idx+=inc;_1b=_17[idx];}if(_1b){return _1b.getHeaderNode();}return null;});if(this.grid.columnReordering&&this.simpleStructure){if(this.source){this.source.destroy();}var _1c="dojoxGrid_bottomMarker";var _1d="dojoxGrid_topMarker";if(this.bottomMarker){_4.destroy(this.bottomMarker);}this.bottomMarker=_4.byId(_1c);if(this.topMarker){_4.destroy(this.topMarker);}this.topMarker=_4.byId(_1d);if(!this.bottomMarker){this.bottomMarker=_4.create("div",{"id":_1c,"class":"dojoxGridColPlaceBottom"},_4.body());this._hide(this.bottomMarker);this.topMarker=_4.create("div",{"id":_1d,"class":"dojoxGridColPlaceTop"},_4.body());this._hide(this.topMarker);}this.arrowDim=_4.contentBox(this.bottomMarker);var _1e=_4.contentBox(this.headerContentNode.firstChild.rows[0]).h;this.source=new _4.dnd.Source(this.headerContentNode.firstChild.rows[0],{horizontal:true,accept:["gridColumn_"+this.grid.id],viewIndex:this.index,generateText:false,onMouseDown:_4.hitch(this,function(e){this.header.decorateEvent(e);if((this.header.overRightResizeArea(e)||this.header.overLeftResizeArea(e))&&this.header.canResize(e)&&!this.header.moveable){this.header.beginColumnResize(e);}else{if(this.grid.headerMenu){this.grid.headerMenu.onCancel(true);}if(e.button===(_4.isIE?1:0)){_4.dnd.Source.prototype.onMouseDown.call(this.source,e);}}}),onMouseOver:_4.hitch(this,function(e){var src=this.source;if(src._getChildByEvent(e)){_4.dnd.Source.prototype.onMouseOver.apply(src,arguments);}}),_markTargetAnchor:_4.hitch(this,function(_1f){var src=this.source;if(src.current==src.targetAnchor&&src.before==_1f){return;}if(src.targetAnchor&&_18(src.targetAnchor,src.before)){src._removeItemClass(_18(src.targetAnchor,src.before),src.before?"After":"Before");}_4.dnd.Source.prototype._markTargetAnchor.call(src,_1f);var _20=_1f?src.targetAnchor:_18(src.targetAnchor,src.before);var _21=0;if(!_20){_20=src.targetAnchor;_21=_4.contentBox(_20).w+this.arrowDim.w/2+2;}var pos=(_4.position||_4._abs)(_20,true);var _22=Math.floor(pos.x-this.arrowDim.w/2+_21);_4.style(this.bottomMarker,"visibility","visible");_4.style(this.topMarker,"visibility","visible");_4.style(this.bottomMarker,{"left":_22+"px","top":(_1e+pos.y)+"px"});_4.style(this.topMarker,{"left":_22+"px","top":(pos.y-this.arrowDim.h)+"px"});if(src.targetAnchor&&_18(src.targetAnchor,src.before)){src._addItemClass(_18(src.targetAnchor,src.before),src.before?"After":"Before");}}),_unmarkTargetAnchor:_4.hitch(this,function(){var src=this.source;if(!src.targetAnchor){return;}if(src.targetAnchor&&_18(src.targetAnchor,src.before)){src._removeItemClass(_18(src.targetAnchor,src.before),src.before?"After":"Before");}this._hide(this.bottomMarker);this._hide(this.topMarker);_4.dnd.Source.prototype._unmarkTargetAnchor.call(src);}),destroy:_4.hitch(this,function(){_4.disconnect(this._source_conn);_4.unsubscribe(this._source_sub);_4.dnd.Source.prototype.destroy.call(this.source);if(this.bottomMarker){_4.destroy(this.bottomMarker);delete this.bottomMarker;}if(this.topMarker){_4.destroy(this.topMarker);delete this.topMarker;}}),onDndCancel:_4.hitch(this,function(){_4.dnd.Source.prototype.onDndCancel.call(this.source);this._hide(this.bottomMarker);this._hide(this.topMarker);})});this._source_conn=_4.connect(this.source,"onDndDrop",this,"_onDndDrop");this._source_sub=_4.subscribe("/dnd/drop/before",this,"_onDndDropBefore");this.source.startup();}},_hide:function(_23){_4.style(_23,{left:"-10000px",top:"-10000px","visibility":"hidden"});},_onDndDropBefore:function(_24,_25,_26){if(_4.dnd.manager().target!==this.source){return;}this.source._targetNode=this.source.targetAnchor;this.source._beforeTarget=this.source.before;var _27=this.grid.views.views;var _28=_27[_24.viewIndex];var _29=_27[this.index];if(_29!=_28){_28.convertColPctToFixed();_29.convertColPctToFixed();}},_onDndDrop:function(_2a,_2b,_2c){if(_4.dnd.manager().target!==this.source){if(_4.dnd.manager().source===this.source){this._removingColumn=true;}return;}this._hide(this.bottomMarker);this._hide(this.topMarker);var _2d=function(n){return n?_4.attr(n,"idx"):null;};var w=_4.marginBox(_2b[0]).w;if(_2a.viewIndex!==this.index){var _2e=this.grid.views.views;var _2f=_2e[_2a.viewIndex];var _30=_2e[this.index];if(_2f.viewWidth&&_2f.viewWidth!="auto"){_2f.setColumnsWidth(_2f.getColumnsWidth()-w);}if(_30.viewWidth&&_30.viewWidth!="auto"){_30.setColumnsWidth(_30.getColumnsWidth());}}var stn=this.source._targetNode;var stb=this.source._beforeTarget;!_4._isBodyLtr()&&(stb=!stb);var _31=this.grid.layout;var idx=this.index;delete this.source._targetNode;delete this.source._beforeTarget;_31.moveColumn(_2a.viewIndex,idx,_2d(_2b[0]),_2d(stn),stb);},renderHeader:function(){this.headerContentNode.innerHTML=this.header.generateHtml(this._getHeaderContent);if(this.flexCells){this.contentWidth=this.getContentWidth();this.headerContentNode.firstChild.style.width=this.contentWidth;}_6.grid.util.fire(this,"onAfterRow",[-1,this.structure.cells,this.headerContentNode]);},_getHeaderContent:function(_32){var n=_32.name||_32.grid.getCellName(_32);var ret=["<div class=\"dojoxGridSortNode"];if(_32.index!=_32.grid.getSortIndex()){ret.push("\">");}else{ret=ret.concat([" ",_32.grid.sortInfo>0?"dojoxGridSortUp":"dojoxGridSortDown","\"><div class=\"dojoxGridArrowButtonChar\">",_32.grid.sortInfo>0?"&#9650;":"&#9660;","</div><div class=\"dojoxGridArrowButtonNode\" role=\"presentation\"></div>","<div class=\"dojoxGridColCaption\">"]);}ret=ret.concat([n,"</div></div>"]);return ret.join("");},resize:function(){this.adaptHeight();this.adaptWidth();},hasHScrollbar:function(_33){var _34=this._hasHScroll||false;if(this._hasHScroll==undefined||_33){if(this.noscroll){this._hasHScroll=false;}else{var _35=_4.style(this.scrollboxNode,"overflow");if(_35=="hidden"){this._hasHScroll=false;}else{if(_35=="scroll"){this._hasHScroll=true;}else{this._hasHScroll=(this.scrollboxNode.offsetWidth-this.getScrollbarWidth()<this.contentNode.offsetWidth);}}}}if(_34!==this._hasHScroll){this.grid.update();}return this._hasHScroll;},hasVScrollbar:function(_36){var _37=this._hasVScroll||false;if(this._hasVScroll==undefined||_36){if(this.noscroll){this._hasVScroll=false;}else{var _38=_4.style(this.scrollboxNode,"overflow");if(_38=="hidden"){this._hasVScroll=false;}else{if(_38=="scroll"){this._hasVScroll=true;}else{this._hasVScroll=(this.scrollboxNode.scrollHeight>this.scrollboxNode.clientHeight);}}}}if(_37!==this._hasVScroll){this.grid.update();}return this._hasVScroll;},convertColPctToFixed:function(){var _39=false;this.grid.initialWidth="";var _3a=_4.query("th",this.headerContentNode);var _3b=_4.map(_3a,function(c,_3c){var w=c.style.width;_4.attr(c,"vIdx",_3c);if(w&&w.slice(-1)=="%"){_39=true;}else{if(w&&w.slice(-2)=="px"){return window.parseInt(w,10);}}return _4.contentBox(c).w;});if(_39){_4.forEach(this.grid.layout.cells,function(_3d,idx){if(_3d.view==this){var _3e=_3d.view.getHeaderCellNode(_3d.index);if(_3e&&_4.hasAttr(_3e,"vIdx")){var _3f=window.parseInt(_4.attr(_3e,"vIdx"));this.setColWidth(idx,_3b[_3f]);_4.removeAttr(_3e,"vIdx");}}},this);return true;}return false;},adaptHeight:function(_40){if(!this.grid._autoHeight){var h=(this.domNode.style.height&&parseInt(this.domNode.style.height.replace(/px/,""),10))||this.domNode.clientHeight;var _41=this;var _42=function(){var v;for(var i in _41.grid.views.views){v=_41.grid.views.views[i];if(v!==_41&&v.hasHScrollbar()){return true;}}return false;};if(_40||(this.noscroll&&_42())){h-=_6.html.metrics.getScrollbar().h;}_6.grid.util.setStyleHeightPx(this.scrollboxNode,h);}this.hasVScrollbar(true);},adaptWidth:function(){if(this.flexCells){this.contentWidth=this.getContentWidth();this.headerContentNode.firstChild.style.width=this.contentWidth;}var w=this.scrollboxNode.offsetWidth-this.getScrollbarWidth();if(!this._removingColumn){w=Math.max(w,this.getColumnsWidth())+"px";}else{w=Math.min(w,this.getColumnsWidth())+"px";this._removingColumn=false;}var cn=this.contentNode;cn.style.width=w;this.hasHScrollbar(true);},setSize:function(w,h){var ds=this.domNode.style;var hs=this.headerNode.style;if(w){ds.width=w;hs.width=w;}ds.height=(h>=0?h+"px":"");},renderRow:function(_43){var _44=this.createRowNode(_43);this.buildRow(_43,_44);this.grid.edit.restore(this,_43);return _44;},createRowNode:function(_45){var _46=document.createElement("div");_46.className=this.classTag+"Row";if(this instanceof _6.grid._RowSelector){_4.attr(_46,"role","presentation");}else{_4.attr(_46,"role","row");if(this.grid.selectionMode!="none"){_4.attr(_46,"aria-selected","false");}}_46[_6.grid.util.gridViewTag]=this.id;_46[_6.grid.util.rowIndexTag]=_45;this.rowNodes[_45]=_46;return _46;},buildRow:function(_47,_48){this.buildRowContent(_47,_48);this.styleRow(_47,_48);},buildRowContent:function(_49,_4a){_4a.innerHTML=this.content.generateHtml(_49,_49);if(this.flexCells&&this.contentWidth){_4a.firstChild.style.width=this.contentWidth;}_6.grid.util.fire(this,"onAfterRow",[_49,this.structure.cells,_4a]);},rowRemoved:function(_4b){if(_4b>=0){this._cleanupRowWidgets(this.getRowNode(_4b));}this.grid.edit.save(this,_4b);delete this.rowNodes[_4b];},getRowNode:function(_4c){return this.rowNodes[_4c];},getCellNode:function(_4d,_4e){var row=this.getRowNode(_4d);if(row){return this.content.getCellNode(row,_4e);}},getHeaderCellNode:function(_4f){if(this.headerContentNode){return this.header.getCellNode(this.headerContentNode,_4f);}},styleRow:function(_50,_51){_51._style=_7(_51);this.styleRowNode(_50,_51);},styleRowNode:function(_52,_53){if(_53){this.doStyleRowNode(_52,_53);}},doStyleRowNode:function(_54,_55){this.grid.styleRowNode(_54,_55);},updateRow:function(_56){var _57=this.getRowNode(_56);if(_57){_57.style.height="";this.buildRow(_56,_57);}return _57;},updateRowStyles:function(_58){this.styleRowNode(_58,this.getRowNode(_58));},lastTop:0,firstScroll:0,doscroll:function(_59){var _5a=_4._isBodyLtr();if(this.firstScroll<2){if((!_5a&&this.firstScroll==1)||(_5a&&this.firstScroll===0)){var s=_4.marginBox(this.headerNodeContainer);if(_4.isIE){this.headerNodeContainer.style.width=s.w+this.getScrollbarWidth()+"px";}else{if(_4.isMoz){this.headerNodeContainer.style.width=s.w-this.getScrollbarWidth()+"px";this.scrollboxNode.scrollLeft=_5a?this.scrollboxNode.clientWidth-this.scrollboxNode.scrollWidth:this.scrollboxNode.scrollWidth-this.scrollboxNode.clientWidth;}}}this.firstScroll++;}this.headerNode.scrollLeft=this.scrollboxNode.scrollLeft;var top=this.scrollboxNode.scrollTop;if(top!==this.lastTop){this.grid.scrollTo(top);}},setScrollTop:function(_5b){this.lastTop=_5b;this.scrollboxNode.scrollTop=_5b;return this.scrollboxNode.scrollTop;},doContentEvent:function(e){if(this.content.decorateEvent(e)){this.grid.onContentEvent(e);}},doHeaderEvent:function(e){if(this.header.decorateEvent(e)){this.grid.onHeaderEvent(e);}},dispatchContentEvent:function(e){return this.content.dispatchEvent(e);},dispatchHeaderEvent:function(e){return this.header.dispatchEvent(e);},setColWidth:function(_5c,_5d){this.grid.setCellWidth(_5c,_5d+"px");},update:function(){if(!this.domNode){return;}this.content.update();this.grid.update();var _5e=this.scrollboxNode.scrollLeft;this.scrollboxNode.scrollLeft=_5e;this.headerNode.scrollLeft=_5e;}});_4.declare("dojox.grid._GridAvatar",_4.dnd.Avatar,{construct:function(){var dd=_4.doc;var a=dd.createElement("table");a.cellPadding=a.cellSpacing="0";a.className="dojoxGridDndAvatar";a.style.position="absolute";a.style.zIndex=1999;a.style.margin="0px";var b=dd.createElement("tbody");var tr=dd.createElement("tr");var td=dd.createElement("td");var img=dd.createElement("td");tr.className="dojoxGridDndAvatarItem";img.className="dojoxGridDndAvatarItemImage";img.style.width="16px";var _5f=this.manager.source,_60;if(_5f.creator){_60=_5f._normalizedCreator(_5f.getItem(this.manager.nodes[0].id).data,"avatar").node;}else{_60=this.manager.nodes[0].cloneNode(true);var _61,_62;if(_60.tagName.toLowerCase()=="tr"){_61=dd.createElement("table");_62=dd.createElement("tbody");_62.appendChild(_60);_61.appendChild(_62);_60=_61;}else{if(_60.tagName.toLowerCase()=="th"){_61=dd.createElement("table");_62=dd.createElement("tbody");var r=dd.createElement("tr");_61.cellPadding=_61.cellSpacing="0";r.appendChild(_60);_62.appendChild(r);_61.appendChild(_62);_60=_61;}}}_60.id="";td.appendChild(_60);tr.appendChild(img);tr.appendChild(td);_4.style(tr,"opacity",0.9);b.appendChild(tr);a.appendChild(b);this.node=a;var m=_4.dnd.manager();this.oldOffsetY=m.OFFSET_Y;m.OFFSET_Y=1;},destroy:function(){_4.dnd.manager().OFFSET_Y=this.oldOffsetY;this.inherited(arguments);}});var _63=_4.dnd.manager().makeAvatar;_4.dnd.manager().makeAvatar=function(){var src=this.source;if(src.viewIndex!==undefined&&!_4.hasClass(_4.body(),"dijit_a11y")){return new _6.grid._GridAvatar(this);}return _63.call(_4.dnd.manager());};})();}}};});