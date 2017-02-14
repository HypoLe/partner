/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.grid.enhanced.plugins.Pagination"],["require","dijit.form.NumberTextBox"],["require","dijit.form.Button"],["require","dojox.grid.enhanced._Plugin"],["require","dojox.grid.enhanced.plugins.Dialog"],["require","dojox.grid.enhanced.plugins._StoreLayer"],["requireLocalization","dojox.grid.enhanced","Pagination",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hr,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hr,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.grid.enhanced.plugins.Pagination"]){_4._hasResource["dojox.grid.enhanced.plugins.Pagination"]=true;_4.provide("dojox.grid.enhanced.plugins.Pagination");_4.require("dijit.form.NumberTextBox");_4.require("dijit.form.Button");_4.require("dojox.grid.enhanced._Plugin");_4.require("dojox.grid.enhanced.plugins.Dialog");_4.require("dojox.grid.enhanced.plugins._StoreLayer");_4.declare("dojox.grid.enhanced.plugins.Pagination",_6.grid.enhanced._Plugin,{name:"pagination",pageSize:25,defaultRows:25,_currentPage:0,_maxSize:0,init:function(){this.gh=null;this.grid.rowsPerPage=this.pageSize=this.grid.rowsPerPage?this.grid.rowsPerPage:this.pageSize;this.grid.usingPagination=true;this.nls=_4.i18n.getLocalization("dojox.grid.enhanced","Pagination");this._wrapStoreLayer();this._createPaginators(this.option);this._regApis();},_createPaginators:function(_7){this.paginators=[];if(_7.position==="both"){this.paginators=[new _6.grid.enhanced.plugins._Paginator(_4.mixin(_7,{position:"bottom",plugin:this})),new _6.grid.enhanced.plugins._Paginator(_4.mixin(_7,{position:"top",plugin:this}))];}else{this.paginators=[new _6.grid.enhanced.plugins._Paginator(_4.mixin(_7,{plugin:this}))];}},_wrapStoreLayer:function(){var g=this.grid,ns=_6.grid.enhanced.plugins;this._store=g.store;this.query=g.query;this.forcePageStoreLayer=new ns._ForcedPageStoreLayer(this);ns.wrap(g,"_storeLayerFetch",this.forcePageStoreLayer);this.connect(g,"setQuery",function(_8){if(_8!==this.query){this.query=_8;}});},_stopEvent:function(_9){try{_4.stopEvent(_9);}catch(e){}},_onNew:function(_a,_b){var _c=Math.ceil(this._maxSize/this.pageSize);if(((this._currentPage+1===_c||_c===0)&&this.grid.rowCount<this.pageSize)||this.showAll){_4.hitch(this.grid,this._originalOnNew)(_a,_b);this.forcePageStoreLayer.endIdx++;}this._maxSize++;if(this.showAll){this.pageSize++;}if(this.showAll&&this.grid.autoHeight){this.grid._refresh();}else{_4.forEach(this.paginators,function(p){p.update();});}},_removeSelectedRows:function(){this._multiRemoving=true;this._originalRemove();this._multiRemoving=false;this.grid.resize();this.grid._refresh();},_onDelete:function(){if(!this._multiRemoving){this.grid.resize();if(this.showAll){this.grid._refresh();}}if(this.grid.get("rowCount")===0){this.prevPage();}},_regApis:function(){var g=this.grid;g.gotoPage=_4.hitch(this,this.gotoPage);g.nextPage=_4.hitch(this,this.nextPage);g.prevPage=_4.hitch(this,this.prevPage);g.gotoFirstPage=_4.hitch(this,this.gotoFirstPage);g.gotoLastPage=_4.hitch(this,this.gotoLastPage);g.changePageSize=_4.hitch(this,this.changePageSize);g.showGotoPageButton=_4.hitch(this,this.showGotoPageButton);g.getTotalRowCount=_4.hitch(this,this.getTotalRowCount);this.originalScrollToRow=_4.hitch(g,g.scrollToRow);g.scrollToRow=_4.hitch(this,this.scrollToRow);this._originalOnNew=_4.hitch(g,g._onNew);this._originalRemove=_4.hitch(g,g.removeSelectedRows);g.removeSelectedRows=_4.hitch(this,this._removeSelectedRows);g._onNew=_4.hitch(this,this._onNew);this.connect(g,"_onDelete",_4.hitch(this,this._onDelete));},destroy:function(){this.inherited(arguments);var g=this.grid;try{_4.forEach(this.paginators,function(p){p.destroy();});g.unwrap(this.forcePageStoreLayer.name());g._onNew=this._originalOnNew;g.removeSelectedRows=this._originalRemove;g.scrollToRow=this.originalScrollToRow;this.paginators=null;this.nls=null;}catch(e){console.warn("Pagination.destroy() error: ",e);}},nextPage:function(){if(this._maxSize>((this._currentPage+1)*this.pageSize)){this.gotoPage(this._currentPage+2);}},prevPage:function(){if(this._currentPage>0){this.gotoPage(this._currentPage);}},gotoPage:function(_d){var _e=Math.ceil(this._maxSize/this.pageSize);_d--;if(_d<_e&&_d>=0&&this._currentPage!==_d){this._currentPage=_d;this.grid.setQuery(this.query);this.grid.resize();}},gotoFirstPage:function(){this.gotoPage(1);},gotoLastPage:function(){var _f=Math.ceil(this._maxSize/this.pageSize);this.gotoPage(_f);},changePageSize:function(_10){if(typeof _10=="string"){_10=parseInt(_10,10);}var _11=this.pageSize*this._currentPage;_4.forEach(this.paginators,function(f){f.currentPageSize=this.grid.rowsPerPage=this.pageSize=_10;if(_10>=this._maxSize){this.grid.rowsPerPage=this.defaultRows;this.grid.usingPagination=false;}else{this.grid.usingPagination=true;}},this);var _12=_11+Math.min(this.pageSize,this._maxSize);if(_12>this._maxSize){this.gotoLastPage();}else{var cp=Math.ceil(_11/this.pageSize);if(cp!==this._currentPage){this.gotoPage(cp+1);}else{this.grid._refresh(true);}}this.grid.resize();},showGotoPageButton:function(_13){_4.forEach(this.paginators,function(p){p._showGotoButton(_13);});},scrollToRow:function(_14){var _15=parseInt(_14/this.pageSize,10),_16=Math.ceil(this._maxSize/this.pageSize);if(_15>_16){return;}this.gotoPage(_15+1);var _17=_14%this.pageSize;this.grid.setScrollTop(this.grid.scroller.findScrollTop(_17)+1);},getTotalRowCount:function(){return this._maxSize;}});_4.declare("dojox.grid.enhanced.plugins._ForcedPageStoreLayer",_6.grid.enhanced.plugins._StoreLayer,{tags:["presentation"],constructor:function(_18){this._plugin=_18;},_fetch:function(_19){var _1a=this,_1b=_1a._plugin,_1c=_1b.grid,_1d=_19.scope||_4.global,_1e=_19.onBegin;_19.start=_1b._currentPage*_1b.pageSize+_19.start;_1a.startIdx=_19.start;_1a.endIdx=_19.start+_1b.pageSize-1;if(_1e&&(_1b.showAll||_4.every(_1b.paginators,function(p){return _1b.showAll=!p.sizeSwitch&&!p.pageStepper&&!p.gotoButton;}))){_19.onBegin=function(_1f,req){_1b._maxSize=_1b.pageSize=_1f;_1a.startIdx=0;_1a.endIdx=_1f-1;_4.forEach(_1b.paginators,function(f){f.update();});req.onBegin=_1e;req.onBegin.call(_1d,_1f,req);};}else{if(_1e){_19.onBegin=function(_20,req){req.start=0;req.count=_1b.pageSize;_1b._maxSize=_20;_1a.endIdx=_1a.endIdx>=_20?(_20-1):_1a.endIdx;if(_1a.startIdx>_20&&_20!==0){_1c._pending_requests[req.start]=false;_1b.gotoFirstPage();}_4.forEach(_1b.paginators,function(f){f.update();});req.onBegin=_1e;req.onBegin.call(_1d,Math.min(_1b.pageSize,(_20-_1a.startIdx)),req);};}}return _4.hitch(this._store,this._originFetch)(_19);}});_4.declare("dojox.grid.enhanced.plugins._Paginator",[_5._Widget,_5._Templated],{templateString:"<div dojoAttachPoint=\"paginatorBar\">\r\n\t<table cellpadding=\"0\" cellspacing=\"0\"  class=\"dojoxGridPaginator\">\r\n\t\t<tr>\r\n\t\t\t<td dojoAttachPoint=\"descriptionTd\" class=\"dojoxGridDescriptionTd\">\r\n\t\t\t\t<div dojoAttachPoint=\"descriptionDiv\" class=\"dojoxGridDescription\" />\r\n\t\t\t</td>\r\n\t\t\t<td dojoAttachPoint=\"sizeSwitchTd\"></td>\r\n\t\t\t<td dojoAttachPoint=\"pageStepperTd\" class=\"dojoxGridPaginatorFastStep\">\r\n\t\t\t\t<div dojoAttachPoint=\"pageStepperDiv\" class=\"dojoxGridPaginatorStep\"></div>\r\n\t\t\t</td>\r\n\t\t</tr>\r\n\t</table>\r\n</div>\r\n",position:"bottom",_maxItemSize:0,description:true,pageStepper:true,maxPageStep:7,sizeSwitch:true,pageSizes:["10","25","50","100","All"],gotoButton:false,constructor:function(_21){_4.mixin(this,_21);this.grid=this.plugin.grid;this.itemTitle=this.itemTitle?this.itemTitle:this.plugin.nls.itemTitle;this.descTemplate=this.descTemplate?this.descTemplate:this.plugin.nls.descTemplate;},postCreate:function(){this.inherited(arguments);this._setWidthValue();var _22=this;var g=this.grid;this.plugin.connect(g,"_resize",_4.hitch(this,"_resetGridHeight"));this._originalResize=_4.hitch(g,"resize");g.resize=function(_23,_24){_22._changeSize=g._pendingChangeSize=_23;_22._resultSize=g._pendingResultSize=_24;g.sizeChange();};this._placeSelf();},destroy:function(){this.inherited(arguments);this.grid.focus.removeArea("pagination"+this.position.toLowerCase());if(this._gotoPageDialog){this._gotoPageDialog.destroy();_4.destroy(this.gotoPageTd);delete this.gotoPageTd;delete this._gotoPageDialog;}this.grid.resize=this._originalResize;this.pageSizes=null;},update:function(){this.currentPageSize=this.plugin.pageSize;this._maxItemSize=this.plugin._maxSize;this._updateDescription();this._updatePageStepper();this._updateSizeSwitch();this._updateGotoButton();},_setWidthValue:function(){var _25=["description","sizeSwitch","pageStepper"];var _26=function(_27,_28){var reg=new RegExp(_28+"$");return reg.test(_27);};_4.forEach(_25,function(t){var _29,_2a=this[t];if(_2a===undefined||typeof _2a=="boolean"){return;}if(_4.isString(_2a)){_29=_26(_2a,"px")||_26(_2a,"%")||_26(_2a,"em")?_2a:parseInt(_2a,10)>0?parseInt(_2a,10)+"px":null;}else{if(typeof _2a==="number"&&_2a>0){_29=_2a+"px";}}this[t]=_29?true:false;this[t+"Width"]=_29;},this);},_regFocusMgr:function(_2b){this.grid.focus.addArea({name:"pagination"+_2b,onFocus:_4.hitch(this,this._onFocusPaginator),onBlur:_4.hitch(this,this._onBlurPaginator),onMove:_4.hitch(this,this._moveFocus),onKeyDown:_4.hitch(this,this._onKeyDown)});switch(_2b){case "top":this.grid.focus.placeArea("pagination"+_2b,"before","header");break;case "bottom":default:this.grid.focus.placeArea("pagination"+_2b,"after","content");break;}},_placeSelf:function(){var g=this.grid;var _2c=_4.trim(this.position.toLowerCase());switch(_2c){case "top":this.placeAt(g.viewsHeaderNode,"before");this._regFocusMgr("top");break;case "bottom":default:this.placeAt(g.viewsNode,"after");this._regFocusMgr("bottom");break;}},_resetGridHeight:function(_2d,_2e){var g=this.grid;_2d=_2d||this._changeSize;_2e=_2e||this._resultSize;delete this._changeSize;delete this._resultSize;if(g._autoHeight){return;}var _2f=g._getPadBorder().h;if(!this.plugin.gh){this.plugin.gh=_4.contentBox(g.domNode).h+2*_2f;}if(_2e){_2d=_2e;}if(_2d){this.plugin.gh=_4.contentBox(g.domNode).h+2*_2f;}var gh=this.plugin.gh,hh=g._getHeaderHeight(),ph=_4.marginBox(this.domNode).h;ph=this.plugin.paginators[1]?ph*2:ph;if(typeof g.autoHeight=="number"){var cgh=gh+ph-_2f;_4.style(g.domNode,"height",cgh+"px");_4.style(g.viewsNode,"height",(cgh-ph-hh)+"px");this._styleMsgNode(hh,_4.marginBox(g.viewsNode).w,cgh-ph-hh);}else{var h=gh-ph-hh-_2f;_4.style(g.viewsNode,"height",h+"px");var _30=_4.some(g.views.views,function(v){return v.hasHScrollbar();});_4.forEach(g.viewsNode.childNodes,function(c,idx){_4.style(c,"height",h+"px");});_4.forEach(g.views.views,function(v,idx){if(v.scrollboxNode){if(!v.hasHScrollbar()&&_30){_4.style(v.scrollboxNode,"height",(h-_6.html.metrics.getScrollbar().h)+"px");}else{_4.style(v.scrollboxNode,"height",h+"px");}}});this._styleMsgNode(hh,_4.marginBox(g.viewsNode).w,h);}},_styleMsgNode:function(top,_31,_32){var _33=this.grid.messagesNode;_4.style(_33,{"position":"absolute","top":top+"px","width":_31+"px","height":_32+"px","z-Index":"100"});},_updateDescription:function(){var s=this.plugin.forcePageStoreLayer;if(this.description&&this.descriptionDiv){this.descriptionDiv.innerHTML=this._maxItemSize>0?_4.string.substitute(this.descTemplate,[this.itemTitle,this._maxItemSize,s.startIdx+1,s.endIdx+1]):"0 "+this.itemTitle;}if(this.descriptionWidth){_4.style(this.descriptionTd,"width",this.descriptionWidth);}},_updateSizeSwitch:function(){if(!this.sizeSwitchTd){return;}if(!this.sizeSwitch||this._maxItemSize<=0){_4.style(this.sizeSwitchTd,"display","none");return;}else{_4.style(this.sizeSwitchTd,"display","");}if(this.initializedSizeNode&&!this.pageSizeValue){return;}if(this.sizeSwitchTd.childNodes.length<1){this._createSizeSwitchNodes();}this._updateSwitchNodeClass();this._moveToNextActivableNode(this._getAllPageSizeNodes(),this.pageSizeValue);this.pageSizeValue=null;},_createSizeSwitchNodes:function(){var _34=null;if(!this.pageSizes||this.pageSizes.length<1){return;}_4.forEach(this.pageSizes,function(_35){_35=_4.trim(_35);var _36=_35.toLowerCase()=="all"?this.plugin.nls.allItemsLabelTemplate:_4.string.substitute(this.plugin.nls.pageSizeLabelTemplate,[_35]);_34=_4.create("span",{innerHTML:_35,title:_36,value:_35,tabindex:0},this.sizeSwitchTd,"last");_5.setWaiState(_34,"label",_36);this.plugin.connect(_34,"onclick",_4.hitch(this,"_onSwitchPageSize"));this.plugin.connect(_34,"onmouseover",function(e){_4.addClass(e.target,"dojoxGridPageTextHover");});this.plugin.connect(_34,"onmouseout",function(e){_4.removeClass(e.target,"dojoxGridPageTextHover");});_34=_4.create("span",{innerHTML:"|"},this.sizeSwitchTd,"last");_4.addClass(_34,"dojoxGridSeparator");},this);_4.destroy(_34);this.initializedSizeNode=true;if(this.sizeSwitchWidth){_4.style(this.sizeSwitchTd,"width",this.sizeSwitchWidth);}},_updateSwitchNodeClass:function(){var _37=null;var _38=false;var _39=function(_3a,_3b){if(_3b){_4.addClass(_3a,"dojoxGridActivedSwitch");_4.attr(_3a,"tabindex","-1");_38=true;}else{_4.addClass(_3a,"dojoxGridInactiveSwitch");_4.attr(_3a,"tabindex","0");}};_4.forEach(this.sizeSwitchTd.childNodes,function(_3c){if(_3c.value){_37=_3c.value;_4.removeClass(_3c);if(this.pageSizeValue){_39(_3c,_37===this.pageSizeValue&&!_38);}else{if(_37.toLowerCase()=="all"){_37=this._maxItemSize;}_39(_3c,this.currentPageSize===parseInt(_37,10)&&!_38);}}},this);},_updatePageStepper:function(){if(!this.pageStepperTd){return;}if(!this.pageStepper||this._maxItemSize<=0){_4.style(this.pageStepperTd,"display","none");return;}else{_4.style(this.pageStepperTd,"display","");}if(this.pageStepperDiv.childNodes.length<1){this._createPageStepNodes();this._createWardBtns();}else{this._resetPageStepNodes();}this._updatePageStepNodeClass();this._moveToNextActivableNode(this._getAllPageStepNodes(),this.pageStepValue);this.pageStepValue=null;},_createPageStepNodes:function(){var _3d=this._getStartPage(),_3e=this._getStepPageSize(),_3f="",_40=null;for(var i=_3d;i<this.maxPageStep+1;i++){_3f=_4.string.substitute(this.plugin.nls.pageStepLabelTemplate,[i+""]);_40=_4.create("div",{innerHTML:i,value:i,title:_3f,tabindex:i<_3d+_3e?0:-1},this.pageStepperDiv,"last");_5.setWaiState(_40,"label",_3f);this.plugin.connect(_40,"onclick",_4.hitch(this,"_onPageStep"));this.plugin.connect(_40,"onmouseover",function(e){_4.addClass(e.target,"dojoxGridPageTextHover");});this.plugin.connect(_40,"onmouseout",function(e){_4.removeClass(e.target,"dojoxGridPageTextHover");});_4.style(_40,"display",i<_3d+_3e?"block":"none");}if(this.pageStepperWidth){_4.style(this.pageStepperTd,"width",this.pageStepperWidth);}},_createWardBtns:function(){var _41=this;var _42={prevPage:"&#60;",firstPage:"&#171;",nextPage:"&#62;",lastPage:"&#187;"};var _43=function(_44,_45,_46){var _47=_4.create("div",{value:_44,title:_45,tabindex:1},_41.pageStepperDiv,_46);_41.plugin.connect(_47,"onclick",_4.hitch(_41,"_onPageStep"));_5.setWaiState(_47,"label",_45);var _48=_4.create("span",{value:_44,title:_45,innerHTML:_42[_44]},_47,_46);_4.addClass(_48,"dojoxGridWardButtonInner");};_43("prevPage",this.plugin.nls.prevTip,"first");_43("firstPage",this.plugin.nls.firstTip,"first");_43("nextPage",this.plugin.nls.nextTip,"last");_43("lastPage",this.plugin.nls.lastTip,"last");},_resetPageStepNodes:function(){var _49=this._getStartPage(),_4a=this._getStepPageSize(),_4b=this.pageStepperDiv.childNodes,_4c=null;for(var i=_49,j=2;j<_4b.length-2;j++,i++){_4c=_4b[j];if(i<_49+_4a){_4.attr(_4c,"innerHTML",i);_4.attr(_4c,"value",i);_4.style(_4c,"display","block");_5.setWaiState(_4c,"label",_4.string.substitute(this.plugin.nls.pageStepLabelTemplate,[i+""]));}else{_4.style(_4c,"display","none");}}},_updatePageStepNodeClass:function(){var _4d=null,_4e=this._getCurrentPageNo(),_4f=this._getPageCount(),_50=0;var _51=function(_52,_53,_54){var _55=_52.value,_56=_53?"dojoxGrid"+_55+"Btn":"dojoxGridInactived",_57=_53?"dojoxGrid"+_55+"BtnDisable":"dojoxGridActived";if(_54){_4.addClass(_52,_57);_4.attr(_52,"tabindex","-1");}else{_4.addClass(_52,_56);_4.attr(_52,"tabindex","0");}};_4.forEach(this.pageStepperDiv.childNodes,function(_58){_4.removeClass(_58);if(isNaN(parseInt(_58.value,10))){_4.addClass(_58,"dojoxGridWardButton");var _59=_58.value=="prevPage"||_58.value=="firstPage"?1:_4f;_51(_58,true,(_4e==_59));}else{_4d=parseInt(_58.value,10);_51(_58,false,(_4d===_4e||_4.style(_58,"display")==="none"));}},this);},_showGotoButton:function(_5a){this.gotoButton=_5a;this._updateGotoButton();},_updateGotoButton:function(){if(!this.gotoButton){if(this.gotoPageTd){if(this._gotoPageDialog){this._gotoPageDialog.destroy();}_4.destroy(this.gotoPageDiv);_4.destroy(this.gotoPageTd);delete this.gotoPageDiv;delete this.gotoPageTd;}return;}if(!this.gotoPageTd){this._createGotoNode();}_4.toggleClass(this.gotoPageDiv,"dojoxGridPaginatorGotoDivDisabled",this.plugin.pageSize>=this.plugin._maxSize);},_createGotoNode:function(){this.gotoPageTd=_4.create("td",{},_4.query("tr",this.domNode)[0],"last");_4.addClass(this.gotoPageTd,"dojoxGridPaginatorGotoTd");this.gotoPageDiv=_4.create("div",{tabindex:"0",title:this.plugin.nls.gotoButtonTitle},this.gotoPageTd,"first");_4.addClass(this.gotoPageDiv,"dojoxGridPaginatorGotoDiv");this.plugin.connect(this.gotoPageDiv,"onclick",_4.hitch(this,"_openGotopageDialog"));var _5b=_4.create("span",{title:this.plugin.nls.gotoButtonTitle,innerHTML:"&#8869;"},this.gotoPageDiv,"last");_4.addClass(_5b,"dojoxGridWardButtonInner");},_openGotopageDialog:function(_5c){if(!this._gotoPageDialog){this._gotoPageDialog=new _6.grid.enhanced.plugins.pagination._GotoPageDialog(this.plugin);}if(!this._currentFocusNode){this.grid.focus.focusArea("pagination"+this.position,_5c);}else{this._currentFocusNode=this.gotoPageDiv;}if(this.focusArea!="pageStep"){this.focusArea="pageStep";}this._gotoPageDialog.updatePageCount();this._gotoPageDialog.showDialog();},_onFocusPaginator:function(_5d,_5e){if(!this._currentFocusNode){if(_5e>0){return this._onFocusPageSizeNode(_5d)?true:this._onFocusPageStepNode(_5d);}else{if(_5e<0){return this._onFocusPageStepNode(_5d)?true:this._onFocusPageSizeNode(_5d);}else{return false;}}}else{if(_5e>0){return this.focusArea==="pageSize"?this._onFocusPageStepNode(_5d):false;}else{if(_5e<0){return this.focusArea==="pageStep"?this._onFocusPageSizeNode(_5d):false;}else{return false;}}}},_onFocusPageSizeNode:function(_5f){var _60=this._getPageSizeActivableNodes();if(_5f&&_5f.type!=="click"){if(_60[0]){_5.focus(_60[0]);this._currentFocusNode=_60[0];this.focusArea="pageSize";this.plugin._stopEvent(_5f);return true;}else{return false;}}if(_5f&&_5f.type=="click"){if(_4.indexOf(this._getPageSizeActivableNodes(),_5f.target)>-1){this.focusArea="pageSize";this.plugin._stopEvent(_5f);return true;}}return false;},_onFocusPageStepNode:function(_61){var _62=this._getPageStepActivableNodes();if(_61&&_61.type!=="click"){if(_62[0]){_5.focus(_62[0]);this._currentFocusNode=_62[0];this.focusArea="pageStep";this.plugin._stopEvent(_61);return true;}else{if(this.gotoPageDiv){_5.focus(this.gotoPageDiv);this._currentFocusNode=this.gotoPageDiv;this.focusArea="pageStep";this.plugin._stopEvent(_61);return true;}else{return false;}}}if(_61&&_61.type=="click"){if(_4.indexOf(this._getPageStepActivableNodes(),_61.target)>-1){this.focusArea="pageStep";this.plugin._stopEvent(_61);return true;}else{if(_61.target==this.gotoPageDiv){_5.focus(this.gotoPageDiv);this._currentFocusNode=this.gotoPageDiv;this.focusArea="pageStep";this.plugin._stopEvent(_61);return true;}}}return false;},_onFocusGotoPageNode:function(_63){if(!this.gotoButton||!this.gotoPageTd){return false;}if(_63&&_63.type!=="click"||(_63.type=="click"&&_63.target==this.gotoPageDiv)){_5.focus(this.gotoPageDiv);this._currentFocusNode=this.gotoPageDiv;this.focusArea="gotoButton";this.plugin._stopEvent(_63);return true;}return true;},_onBlurPaginator:function(_64,_65){var _66=this._getPageSizeActivableNodes(),_67=this._getPageStepActivableNodes();if(_65>0&&this.focusArea==="pageSize"&&(_67.length>1||this.gotoButton)){return false;}else{if(_65<0&&this.focusArea==="pageStep"&&_66.length>1){return false;}}this._currentFocusNode=null;this.focusArea=null;return true;},_onKeyDown:function(_68,_69){if(_69){return;}if(_68.altKey||_68.metaKey){return;}var dk=_4.keys;if(_68.keyCode===dk.ENTER||_68.keyCode===dk.SPACE){if(_4.indexOf(this._getPageStepActivableNodes(),this._currentFocusNode)>-1){this._onPageStep(_68);}else{if(_4.indexOf(this._getPageSizeActivableNodes(),this._currentFocusNode)>-1){this._onSwitchPageSize(_68);}else{if(this._currentFocusNode===this.gotoPageDiv){this._openGotopageDialog(_68);}}}}this.plugin._stopEvent(_68);},_moveFocus:function(_6a,_6b,evt){var _6c;if(this.focusArea=="pageSize"){_6c=this._getPageSizeActivableNodes();}else{if(this.focusArea=="pageStep"){_6c=this._getPageStepActivableNodes();if(this.gotoPageDiv){_6c.push(this.gotoPageDiv);}}}if(_6c.length<1){return;}var _6d=_4.indexOf(_6c,this._currentFocusNode);var _6e=_6d+_6b;if(_6e>=0&&_6e<_6c.length){_5.focus(_6c[_6e]);this._currentFocusNode=_6c[_6e];}this.plugin._stopEvent(evt);},_getPageSizeActivableNodes:function(){return _4.query("span[tabindex='0']",this.sizeSwitchTd);},_getPageStepActivableNodes:function(){return (_4.query("div[tabindex='0']",this.pageStepperDiv));},_getAllPageSizeNodes:function(){var _6f=[];_4.forEach(this.sizeSwitchTd.childNodes,function(_70){if(_70.value){_6f.push(_70);}});return _6f;},_getAllPageStepNodes:function(){var _71=[];for(var i=0,len=this.pageStepperDiv.childNodes.length;i<len;i++){_71.push(this.pageStepperDiv.childNodes[i]);}return _71;},_moveToNextActivableNode:function(_72,_73){if(!_73){return;}if(_72.length<2){this.grid.focus.tab(1);}var nl=[],_74=null,_75=0;_4.forEach(_72,function(n){if(n.value==_73){nl.push(n);_74=n;}else{if(_4.attr(n,"tabindex")=="0"){nl.push(n);}}});if(nl.length<2){this.grid.focus.tab(1);}_75=_4.indexOf(nl,_74);if(_4.attr(_74,"tabindex")!="0"){_74=nl[_75+1]?nl[_75+1]:nl[_75-1];}_5.focus(_74);this._currentFocusNode=_74;},_onSwitchPageSize:function(e){var _76=this.pageSizeValue=e.target.value;if(!_76){return;}if(_4.trim(_76.toLowerCase())=="all"){_76=this._maxItemSize;showAll=true;}this.plugin.grid.usingPagination=!this.plugin.showAll;_76=parseInt(_76,10);if(isNaN(_76)||_76<=0){return;}if(!this._currentFocusNode){this.grid.focus.currentArea("pagination"+this.position);}if(this.focusArea!="pageSize"){this.focusArea="pageSize";}this.plugin.changePageSize(_76);},_onPageStep:function(e){var p=this.plugin,_77=this.pageStepValue=e.target.value;if(!this._currentFocusNode){this.grid.focus.currentArea("pagination"+this.position);}if(this.focusArea!="pageStep"){this.focusArea="pageStep";}if(!isNaN(parseInt(_77,10))){p.gotoPage(_77);}else{switch(e.target.value){case "prevPage":p.prevPage();break;case "nextPage":p.nextPage();break;case "firstPage":p.gotoFirstPage();break;case "lastPage":p.gotoLastPage();}}},_getCurrentPageNo:function(){return this.plugin._currentPage+1;},_getPageCount:function(){if(!this._maxItemSize||!this.currentPageSize){return 0;}return Math.ceil(this._maxItemSize/this.currentPageSize);},_getStartPage:function(){var cp=this._getCurrentPageNo();var ms=parseInt(this.maxPageStep/2,10);var pc=this._getPageCount();if(cp<ms||(cp-ms)<1){return 1;}else{if(pc<=this.maxPageStep){return 1;}else{if(pc-cp<ms&&cp-this.maxPageStep>=0){return pc-this.maxPageStep+1;}else{return (cp-ms);}}}},_getStepPageSize:function(){var sp=this._getStartPage();var _78=this._getPageCount();if((sp+this.maxPageStep)>_78){return _78-sp+1;}else{return this.maxPageStep;}}});_4.declare("dojox.grid.enhanced.plugins.pagination._GotoPageDialog",null,{pageCount:0,constructor:function(_79){this.plugin=_79;this.pageCount=this.plugin.paginators[0]._getPageCount();this._dialogNode=_4.create("div",{},_4.body(),"last");this._gotoPageDialog=new _6.grid.enhanced.plugins.Dialog({"refNode":_79.grid.domNode,"title":this.plugin.nls.dialogTitle},this._dialogNode);this._createDialogContent();this._gotoPageDialog.startup();},_createDialogContent:function(){this._specifyNode=_4.create("div",{innerHTML:this.plugin.nls.dialogIndication},this._gotoPageDialog.containerNode,"last");this._pageInputDiv=_4.create("div",{},this._gotoPageDialog.containerNode,"last");this._pageTextBox=new _5.form.NumberTextBox();this._pageTextBox.constraints={fractional:false,min:1,max:this.pageCount};this.plugin.connect(this._pageTextBox.textbox,"onkeyup",_4.hitch(this,"_setConfirmBtnState"));this._pageInputDiv.appendChild(this._pageTextBox.domNode);this._pageLabel=_4.create("label",{innerHTML:_4.string.substitute(this.plugin.nls.pageCountIndication,[this.pageCount])},this._pageInputDiv,"last");this._buttonDiv=_4.create("div",{},this._gotoPageDialog.containerNode,"last");this._confirmBtn=new _5.form.Button({label:this.plugin.nls.dialogConfirm,onClick:_4.hitch(this,this._onConfirm)});this._confirmBtn.set("disabled",true);this._cancelBtn=new _5.form.Button({label:this.plugin.nls.dialogCancel,onClick:_4.hitch(this,this._onCancel)});this._buttonDiv.appendChild(this._confirmBtn.domNode);this._buttonDiv.appendChild(this._cancelBtn.domNode);this._styleContent();this._gotoPageDialog.onCancel=_4.hitch(this,this._onCancel);this.plugin.connect(this._gotoPageDialog,"_onKey",_4.hitch(this,"_onKeyDown"));},_styleContent:function(){_4.addClass(this._specifyNode,"dojoxGridDialogMargin");_4.addClass(this._pageInputDiv,"dojoxGridDialogMargin");_4.addClass(this._buttonDiv,"dojoxGridDialogButton");_4.style(this._pageTextBox.domNode,"width","50px");},updatePageCount:function(){this.pageCount=this.plugin.paginators[0]._getPageCount();this._pageTextBox.constraints={fractional:false,min:1,max:this.pageCount};_4.attr(this._pageLabel,"innerHTML",_4.string.substitute(this.plugin.nls.pageCountIndication,[this.pageCount]));},showDialog:function(){this._gotoPageDialog.show();},_onConfirm:function(_7a){if(this._pageTextBox.isValid()&&this._pageTextBox.getDisplayedValue()!==""){this.plugin.gotoPage(this._pageTextBox.parse(this._pageTextBox.getDisplayedValue()));this._gotoPageDialog.hide();this._pageTextBox.reset();}this.plugin._stopEvent(_7a);},_onCancel:function(_7b){this._pageTextBox.reset();this._gotoPageDialog.hide();this.plugin._stopEvent(_7b);},_onKeyDown:function(_7c){if(_7c.altKey||_7c.metaKey){return;}var dk=_4.keys;if(_7c.keyCode===dk.ENTER){this._onConfirm(_7c);}},_setConfirmBtnState:function(){if(this._pageTextBox.isValid()&&this._pageTextBox.getDisplayedValue()!==""){this._confirmBtn.set("disabled",false);}else{this._confirmBtn.set("disabled",true);}},destroy:function(){this._pageTextBox.destroy();this._confirmBtn.destroy();this._cancelBtn.destroy();this._gotoPageDialog.destroy();_4.destroy(this._specifyNode);_4.destroy(this._pageInputDiv);_4.destroy(this._pageLabel);_4.destroy(this._buttonDiv);_4.destroy(this._dialogNode);}});_6.grid.EnhancedGrid.registerPlugin(_6.grid.enhanced.plugins.Pagination);}}};});