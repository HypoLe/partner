/*
 COPYRIGHT 2009 ESRI

 TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
 Unpublished material - all rights reserved under the
 Copyright Laws of the United States and applicable international
 laws, treaties, and conventions.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts and Legal Services Department
 380 New York Street
 Redlands, California, 92373
 USA

 email: contracts@esri.com
 */

if(!dojo._hasResource["esri.dijit.Popup"]){dojo._hasResource["esri.dijit.Popup"]=true;dojo.provide("esri.dijit.Popup");dojo.require("esri.InfoWindowBase");dojo.require("esri.PopupBase");dojo.require("esri.utils");dojo.require("dijit._Widget");dojo.require("dijit._Templated");dojo.require("dojo.number");dojo.require("dojo.date.locale");dojo.require("dojox.charting.Chart2D");dojo.require("dojox.charting.themes.PlotKit.base");dojo.require("dojox.charting.action2d.Tooltip");dojo.require("dojo.i18n");(function(){var dc=dojox.charting,pk=dc.themes.PlotKit;pk.popup=pk.base.clone();pk.popup.chart.fill=pk.popup.plotarea.fill="#e7eef6";pk.popup.colors=["#284B70","#702828","#5F7143","#F6BC0C","#382C6C","#50224F","#1D7554","#4C4C4C","#0271AE","#706E41","#446A73","#0C3E69","#757575","#B7B7B7","#A3A3A3"];pk.popup.series.stroke.width=1;pk.popup.marker.stroke.width=1;}());dojo.declare("esri.dijit.Popup",[esri.InfoWindowBase,esri.PopupBase],{offsetX:3,offsetY:3,zoomFactor:4,marginLeft:10,marginTop:10,highlight:true,constructor:function(_1,_2){this.initialize();dojo.mixin(this,_1);this.domNode=dojo.byId(_2);var _3=this._nls=dojo.mixin({},esri.bundle.widgets.popup);var _4=this.domNode;dojo.addClass(_4,"esriPopup");var _5="<div class='sizer'>"+"<div class='titlePane'>"+"<div class='spinner hidden' title='"+_3.NLS_searching+"...'></div>"+"<div class='title'></div>"+"<div class='titleButton prev hidden' title='"+_3.NLS_prevFeature+"'></div>"+"<div class='titleButton next hidden' title='"+_3.NLS_nextFeature+"'></div>"+"<div class='titleButton maximize' title='"+_3.NLS_maximize+"'></div>"+"<div class='titleButton close' title='"+_3.NLS_close+"'></div>"+"</div>"+"</div>"+"<div class='sizer content'>"+"<div class='contentPane'>"+"</div>"+"</div>"+"<div class='sizer'>"+"<div class='actionsPane'>"+"<div class='actionList hidden'>"+"<a class='action zoomTo' href='javascript:void(0);'>Zoom to</a>"+"</div>"+"</div>"+"</div>"+"<div class='pointer top hidden'></div>"+"<div class='pointer bottom hidden'></div>"+"<div class='pointer left hidden'></div>"+"<div class='pointer right hidden'></div>"+"<div class='pointer topLeft hidden'></div>"+"<div class='pointer topRight hidden'></div>"+"<div class='pointer bottomLeft hidden'></div>"+"<div class='pointer bottomRight hidden'></div>";dojo.attr(_4,"innerHTML",_5);this._sizers=dojo.query(".sizer",_4);var _6=dojo.query(".titlePane",_4)[0];dojo.setSelectable(_6,false);this._title=dojo.query(".title",_6)[0];this._prevFeatureButton=dojo.query(".prev",_6)[0];this._nextFeatureButton=dojo.query(".next",_6)[0];this._maxButton=dojo.query(".maximize",_6)[0];this._spinner=dojo.query(".spinner",_6)[0];this._contentPane=dojo.query(".contentPane",_4)[0];this._actionList=dojo.query(".actionsPane .actionList",_4)[0];this._eventConnections=[dojo.connect(dojo.query(".close",_6)[0],"onclick",this,this.hide),dojo.connect(this._prevFeatureButton,"onclick",this,this.selectPrevious),dojo.connect(this._nextFeatureButton,"onclick",this,this.selectNext),dojo.connect(this._maxButton,"onclick",this,this._toggleSize),dojo.connect(dojo.query(".zoomTo",this._actionList)[0],"onclick",this,this._zoomToFeature)];if(esri.isTouchEnabled){var _7=esri.setScrollable(this._contentPane);this._eventConnections.push(_7[0],_7[1]);}esri.hide(_4);this.isShowing=false;},setMap:function(_8){this.inherited(arguments);if(this.highlight){this.enableHighlight(_8);}},unsetMap:function(){this.disableHighlight(this.map);this.inherited(arguments);},setTitle:function(_9){this.destroyDijits(this._title);this.place(_9,this._title);if(this.isShowing){this.startupDijits(this._title);}},setContent:function(_a){this.destroyDijits(this._contentPane);this.place(_a,this._contentPane);if(this.isShowing){this.startupDijits(this._contentPane);}},show:function(_b){if(!_b){esri.show(this.domNode);this.isShowing=true;return;}var _c=this.map,_d;if(_b.spatialReference){this._location=_b;_d=_c.toScreen(_b);}else{this._location=_c.toMap(_b);_d=_b;}var _e=_c._getFrameWidth();if(_e!==-1){_d.x=_d.x%_e;if(_d.x<0){_d.x+=_e;}if(_c.width>_e){var _f=(_c.width-_e)/2;while(_d.x<_f){_d.x+=_e;}}}if(this._maximized){this.restore();}else{this._setPosition(_d);}if(!this.isShowing){esri.show(this.domNode);this.isShowing=true;this.onShow();}},hide:function(){if(this.isShowing){esri.hide(this.domNode);this.isShowing=false;this.onHide();}},resize:function(_10,_11){this._sizers.style({width:_10+"px"});dojo.style(this._contentPane,"maxHeight",_11+"px");},onShow:function(){this._followMap();this.startupDijits(this._title);this.startupDijits(this._contentPane);this.showHighlight();},onHide:function(){this._unfollowMap();this.hideHighlight();},maximize:function(){var map=this.map;if(!map||this._maximized){return;}this._maximized=true;var max=this._maxButton;dojo.removeClass(max,"maximize");dojo.addClass(max,"restore");dojo.attr(max,"title",this._nls.NLS_restore);var _12=this.marginLeft,_13=this.marginTop,_14=map.width-(2*_12),_15=map.height-(2*_13),_16=this.domNode;dojo.style(_16,{left:_12+"px",right:null,top:_13+"px",bottom:null});this._savedWidth=dojo.style(this._sizers[0],"width");this._savedHeight=dojo.style(this._contentPane,"maxHeight");this._sizers.style({width:_14+"px"});dojo.style(this._contentPane,{maxHeight:(_15-65)+"px",height:(_15-65)+"px"});this._showPointer("");this._unfollowMap();},restore:function(){var map=this.map;if(!map||!this._maximized){return;}this._maximized=false;var max=this._maxButton;dojo.removeClass(max,"restore");dojo.addClass(max,"maximize");dojo.attr(max,"title",this._nls.NLS_maximize);dojo.style(this._contentPane,"height",null);this.resize(this._savedWidth,this._savedHeight);this._savedWidth=this._savedHeight=null;this.show(this._location);this._followMap();},destroy:function(){if(this.map){this.unsetMap();}this.cleanup();if(this.isShowing){this.hide();}this.destroyDijits(this._title);this.destroyDijits(this._content);dojo.forEach(this._eventConnections,dojo.disconnect);dojo.destroy(this.domNode);this._sizers=this._contentPane=this._actionList=this._title=this._prevFeatureButton=this._nextFeatureButton=this._spinner=this._eventConnections=this._pagerScope=this._targetLocation=this._nls=this._maxButton=null;},selectNext:function(){this.select(this.selectedIndex+1);},selectPrevious:function(){this.select(this.selectedIndex-1);},setFeatures:function(){this.inherited(arguments);this._updateUI();},onSetFeatures:function(){},onClearFeatures:function(){this.setTitle("&nbsp;");this.setContent("&nbsp;");this._setPagerCallbacks(this);this._updateUI();this.hideHighlight();},onSelectionChange:function(){var ptr=this.selectedIndex;this._updateUI();if(ptr>=0){this.setContent(this.features[ptr].getContent());this.updateHighlight(this.map,this.features[ptr]);if(this.isShowing){this.showHighlight();}}},onDfdComplete:function(){this._updateUI();},_followMap:function(){this._unfollowMap();var map=this.map;this._handles=[dojo.connect(map,"onPanStart",this,this._onPanStart),dojo.connect(map,"onPan",this,this._onPan),dojo.connect(map,"onZoomStart",this,this._onZoomStart),dojo.connect(map,"onExtentChange",this,this._onExtentChange)];},_unfollowMap:function(){var _17=this._handles;if(_17){dojo.forEach(_17,dojo.disconnect,dojo);this._handles=null;}},_onPanStart:function(){var _18=this.domNode.style;this._panOrigin={left:_18.left,top:_18.top,right:_18.right,bottom:_18.bottom};},_onPan:function(_19,_1a){var _1b=this._panOrigin,dx=_1a.x,dy=_1a.y,_1c=_1b.left,top=_1b.top,_1d=_1b.right,_1e=_1b.bottom;if(_1c){_1c=(parseFloat(_1c)+dx)+"px";}if(top){top=(parseFloat(top)+dy)+"px";}if(_1d){_1d=(parseFloat(_1d)-dx)+"px";}if(_1e){_1e=(parseFloat(_1e)-dy)+"px";}dojo.style(this.domNode,{left:_1c,top:top,right:_1d,bottom:_1e});},_onZoomStart:function(){esri.hide(this.domNode);},_onExtentChange:function(_1f,_20,_21){if(_21){esri.show(this.domNode);}this.show(this._targetLocation||this._location);this._targetLocation=null;},_toggleSize:function(){if(this._maximized){this.restore();}else{this.maximize();}},_setPosition:function(_22){var _23=_22.x,_24=_22.y,_25=this.offsetX||0,_26=this.offsetY||0,_27=0,_28=dojo.contentBox(this.map.container),_29=_28.w,_2a=_28.h,_2b="Right",_2c="top",_2d="Left",_2e="bottom";if(_23<=_29/2){_2b="Right";_2d="Left";}else{if(_23<=_29){_2b="Left";_2d="Right";}}if(_24<=_2a/2){_2c="bottom";_2e="top";}else{if(_24<=_2a){_2c="top";_2e="bottom";}}var _2f=_2c+_2b,_30=_2e+_2d;this._showPointer(_30);switch(_30){case "topLeft":case "topRight":case "bottomLeft":case "bottomRight":_27=45;break;}switch(_2f){case "topLeft":dojo.style(this.domNode,{left:null,right:(_29-_23+_25)+"px",top:null,bottom:(_2a-_24+_27+_26)+"px"});break;case "topRight":dojo.style(this.domNode,{left:(_23+_25)+"px",right:null,top:null,bottom:(_2a-_24+_27+_26)+"px"});break;case "bottomLeft":dojo.style(this.domNode,{left:null,right:(_29-_23+_25)+"px",top:(_24+_27+_26)+"px",bottom:null});break;case "bottomRight":dojo.style(this.domNode,{left:(_23+_25)+"px",right:null,top:(_24+_27+_26)+"px",bottom:null});break;}},_showPointer:function(_31){var _32=["topLeft","topRight","bottomRight","bottomLeft"];dojo.forEach(_32,function(ptr){if(ptr===_31){dojo.query(".pointer."+ptr,this.domNode).removeClass("hidden");}else{dojo.query(".pointer."+ptr,this.domNode).addClass("hidden");}},this);},_setPagerCallbacks:function(_33,_34,_35){if(_33===this&&(!this._pagerScope||this._pagerScope===this)){return;}if(_33===this._pagerScope){return;}this._pagerScope=_33;if(_33===this){_34=this.selectPrevious;_35=this.selectNext;}var _36=this._eventConnections;dojo.disconnect(_36[1]);dojo.disconnect(_36[2]);if(_34){_36[1]=dojo.connect(this._prevFeatureButton,"onclick",_33,_34);}if(_35){_36[2]=dojo.connect(this._nextFeatureButton,"onclick",_33,_35);}},_zoomToFeature:function(){var _37=this.features,ptr=this.selectedIndex,map=this.map;if(_37){var _38=_37[ptr].geometry,_39,_3a,_3b=0,_3c;if(_38){switch(_38.type){case "point":_39=_38;break;case "multipoint":_39=_38.getPoint(0);_3a=_38.getExtent();break;case "polyline":_39=_38.getPoint(0,0);_3a=_38.getExtent();if(map._getFrameWidth()!==-1){dojo.forEach(_38.paths,function(_3d){var _3e={"paths":[_3d,map.spatialReference]},_3f=new esri.geometry.Polyline(_3e),_40=_3f.getExtent(),_41=Math.abs(_40.ymax-_40.ymin),_42=Math.abs(_40.xmax-_40.xmin),_43=(_42>_41)?_42:_41;if(_43>_3b){_3b=_43;_3c=_40;}});_3a=_3c;}break;case "polygon":_39=_38.getPoint(0,0);_3a=_38.getExtent();if(map._getFrameWidth()!==-1){dojo.forEach(_38.rings,function(_44){var _45={"rings":[_44,map.spatialReference]},_46=new esri.geometry.Polygon(_45),_47=_46.getExtent(),_48=Math.abs(_47.ymax-_47.ymin),_49=Math.abs(_47.xmax-_47.xmin),_4a=(_49>_48)?_49:_48;if(_4a>_3b){_3b=_4a;_3c=_47;}});_3a=_3c;}break;}}if(!_39){_39=this._location;}if(!_3a||!_3a.intersects(this._location)){this._location=_39;}if(_3a){map.setExtent(_3a,true);}else{var _4b=map.getNumLevels(),_4c=map.getLevel(),_4d=_4b-1,_4e=this.zoomFactor||1;if(_4b>0){if(_4c===_4d){return;}var _4f=_4c+_4e;if(_4f>_4d){_4f=_4d;}map._scrollZoomHandler({value:(_4f-_4c),mapPoint:_39});}else{map._scrollZoomHandler({value:(1/Math.pow(2,_4e))*2,mapPoint:_39});}}}},_updateUI:function(){var _50="&nbsp;",ptr=this.selectedIndex,_51=this.features,_52=this.deferreds,_53=this._prevFeatureButton,_54=this._nextFeatureButton,_55=this._spinner,_56=this._actionList,nls=this._nls;if(_51&&_51.length>1){_50="("+(ptr+1)+" of "+_51.length+")";if(ptr===0){dojo.addClass(_53,"hidden");}else{dojo.removeClass(_53,"hidden");}if(ptr===_51.length-1){dojo.addClass(_54,"hidden");}else{dojo.removeClass(_54,"hidden");}}else{dojo.addClass(_53,"hidden");dojo.addClass(_54,"hidden");}this.setTitle(_50);if(_52&&_52.length){if(_51){dojo.removeClass(_55,"hidden");}else{this.setContent("<div style='text-align: center;'>"+nls.NLS_searching+"...</div>");}}else{dojo.addClass(_55,"hidden");if(!_51||!_51.length){this.setContent("<div style='text-align: center;'>"+nls.NLS_noInfo+".</div>");}}if(_51&&_51.length){dojo.removeClass(_56,"hidden");}else{dojo.addClass(_56,"hidden");}}});dojo.declare("esri.dijit.PopupTemplate",[esri.PopupInfoTemplate],{constructor:function(_57){this.initialize(_57);this._nls=dojo.mixin({},esri.bundle.widgets.popup);},getTitle:function(_58){return this.info?this.getComponents(_58).title:"";},getContent:function(_59){return this.info?new esri.dijit._PopupRenderer({template:this,graphic:_59,_nls:this._nls},dojo.create("div")).domNode:"";}});dojo.declare("esri.dijit._PopupRenderer",[dijit._Widget,dijit._Templated],{templateString:"<div class='esriViewPopup'>"+"<div class='mainSection'>"+"<div class='header' dojoAttachPoint='_title'></div>"+"<div class='hzLine'></div>"+"<div dojoAttachPoint='_description'></div>"+"<div class='break'></div>"+"</div>"+"<div class='attachmentsSection hidden'>"+"<div>Attachments:</div>"+"<ul dojoAttachPoint='_attachmentsList'>"+"</ul>"+"<div class='break'></div>"+"</div>"+"<div class='mediaSection hidden'>"+"<div class='header' dojoAttachPoint='_mediaTitle'></div>"+"<div class='hzLine'></div>"+"<div class='caption' dojoAttachPoint='_mediaCaption'></div>"+"<div class='gallery' dojoAttachPoint='_gallery'>"+"<div class='mediaHandle prev' dojoAttachPoint='_prevMedia' dojoAttachEvent='onclick: _goToPrevMedia'></div>"+"<div class='mediaHandle next' dojoAttachPoint='_nextMedia' dojoAttachEvent='onclick: _goToNextMedia'></div>"+"<ul class='summary'>"+"<li class='image mediaCount hidden' dojoAttachPoint='_imageCount'>0</li>"+"<li class='image mediaIcon hidden'></li>"+"<li class='chart mediaCount hidden' dojoAttachPoint='_chartCount'>0</li>"+"<li class='chart mediaIcon hidden'></li>"+"</ul>"+"<div class='frame' dojoAttachPoint='_mediaFrame'></div>"+"</div>"+"</div>"+"</div>",startup:function(){this.inherited(arguments);var _5a=this.template,_5b=this.graphic,_5c=_5a.getComponents(_5b),_5d=_5c.title,_5e=_5c.description,_5f=_5c.fields,_60=_5c.mediaInfos,_61=this.domNode,nls=this._nls;this._prevMedia.title=nls.NLS_prevMedia;this._nextMedia.title=nls.NLS_nextMedia;dojo.attr(this._title,"innerHTML",_5d);if(!_5d){dojo.addClass(this._title,"hidden");}if(!_5e&&_5f){_5e="";dojo.forEach(_5f,function(row){_5e+=("<tr valign='top'>");_5e+=("<td class='attrName'>"+row[0]+"</td>");_5e+=("<td class='attrValue'>"+row[1].replace(/^\s*(https?:\/\/[^\s]+)\s*$/i,"<a target='_blank' href='$1' title='$1'>More info</a>")+"</td>");_5e+=("</tr>");});if(_5e){_5e="<table class='attrTable' cellpadding='0px' cellspacing='0px'>"+_5e+"</table>";}}dojo.attr(this._description,"innerHTML",_5e);if(!_5e){dojo.addClass(this._description,"hidden");}dojo.query("a",this._description).forEach(function(_62){dojo.attr(_62,"target","_blank");});if(_5d&&_5e){dojo.query(".mainSection .hzLine",_61).removeClass("hidden");}else{if(_5d||_5e){dojo.query(".mainSection .hzLine",_61).addClass("hidden");}else{dojo.query(".mainSection",_61).addClass("hidden");}}var dfd=(this._dfd=_5a.getAttachments(_5b));if(dfd){dfd.addBoth(dojo.hitch(this,this._attListHandler,dfd));dojo.attr(this._attachmentsList,"innerHTML","<li>"+nls.NLS_searching+"...</li>");dojo.query(".attachmentsSection",_61).removeClass("hidden");}if(_60&&_60.length){dojo.query(".mediaSection",_61).removeClass("hidden");dojo.setSelectable(this._mediaFrame,false);this._mediaInfos=_60;this._mediaPtr=0;this._updateUI();this._displayMedia();}},destroy:function(){if(this._dfd){this._dfd.cancel();}this._destroyFrame();this.template=this.graphic=this._nls=this._mediaInfos=this._mediaPtr=this._dfd=null;this.inherited(arguments);},_goToPrevMedia:function(){var ptr=this._mediaPtr-1;if(ptr<0){return;}this._mediaPtr--;this._updateUI();this._displayMedia();},_goToNextMedia:function(){var ptr=this._mediaPtr+1;if(ptr===this._mediaInfos.length){return;}this._mediaPtr++;this._updateUI();this._displayMedia();},_updateUI:function(){var _63=this._mediaInfos,_64=_63.length,_65=this.domNode,_66=this._prevMedia,_67=this._nextMedia;if(_64>1){var _68=0,_69=0;dojo.forEach(_63,function(_6a){if(_6a.type==="image"){_68++;}else{if(_6a.type.indexOf("chart")!==-1){_69++;}}});if(_68){dojo.attr(this._imageCount,"innerHTML",_68);dojo.query(".summary .image",_65).removeClass("hidden");}if(_69){dojo.attr(this._chartCount,"innerHTML",_69);dojo.query(".summary .chart",_65).removeClass("hidden");}}else{dojo.query(".summary",_65).addClass("hidden");dojo.addClass(_66,"hidden");dojo.addClass(_67,"hidden");}var ptr=this._mediaPtr;if(ptr===0){dojo.addClass(_66,"hidden");}else{dojo.removeClass(_66,"hidden");}if(ptr===_64-1){dojo.addClass(_67,"hidden");}else{dojo.removeClass(_67,"hidden");}this._destroyFrame();},_displayMedia:function(){var _6b=this._mediaInfos[this._mediaPtr],_6c=_6b.title,_6d=_6b.caption,_6e=dojo.query(".mediaSection .hzLine",this.domNode)[0];dojo.attr(this._mediaTitle,"innerHTML",_6c);dojo[_6c?"removeClass":"addClass"](this._mediaTitle,"hidden");dojo.attr(this._mediaCaption,"innerHTML",_6d);dojo[_6d?"removeClass":"addClass"](this._mediaCaption,"hidden");dojo[(_6c&&_6d)?"removeClass":"addClass"](_6e,"hidden");if(_6b.type==="image"){this._showImage(_6b.value);}else{this._showChart(_6b.type,_6b.value);}},_showImage:function(_6f){dojo.addClass(this._mediaFrame,"image");var _70=dojo.style(this._gallery,"height"),_71="<img src='"+_6f.sourceURL+"' onload='esri.dijit._PopupRenderer.prototype._imageLoaded(this,"+_70+");' />";if(_6f.linkURL){_71="<a target='_blank' href='"+_6f.linkURL+"'>"+_71+"</a>";}dojo.attr(this._mediaFrame,"innerHTML",_71);},_showChart:function(_72,_73){dojo.removeClass(this._mediaFrame,"image");var _74=this._chart=new dojox.charting.Chart2D(dojo.create("div",{"class":"chart"},this._mediaFrame),{margins:{l:4,t:4,r:4,b:4}});_74.setTheme(dojo.getObject("dojox.charting.themes."+(_73.theme||"PlotKit.popup")));switch(_72){case "piechart":_74.addPlot("default",{type:"Pie",labels:false});_74.addSeries("Series A",_73.fields);break;case "linechart":_74.addPlot("default",{type:"Markers"});_74.addAxis("x",{min:0,majorTicks:false,minorTicks:false,majorLabels:false,minorLabels:false});_74.addAxis("y",{includeZero:true,vertical:true,fixUpper:"minor"});dojo.forEach(_73.fields,function(_75,idx){_75.x=idx+1;});_74.addSeries("Series A",_73.fields);break;case "columnchart":_74.addPlot("default",{type:"Columns",gap:3});_74.addAxis("y",{includeZero:true,vertical:true,fixUpper:"minor"});_74.addSeries("Series A",_73.fields);break;case "barchart":_74.addPlot("default",{type:"Bars",gap:3});_74.addAxis("x",{includeZero:true,fixUpper:"minor",minorLabels:false});_74.addAxis("y",{vertical:true,majorTicks:false,minorTicks:false,majorLabels:false,minorLabels:false});_74.addSeries("Series A",_73.fields);break;}this._action=new dojox.charting.action2d.Tooltip(_74);_74.render();},_destroyFrame:function(){if(this._chart){this._chart.destroy();this._chart=null;}if(this._action){this._action.destroy();this._action=null;}dojo.attr(this._mediaFrame,"innerHTML","");},_imageLoaded:function(img,_76){var _77=img.height;if(_77<_76){var _78=Math.round((_76-_77)/2);dojo.style(img,"marginTop",_78+"px");}},_attListHandler:function(dfd,_79){if(dfd===this._dfd){this._dfd=null;var _7a="";if(!(_79 instanceof Error)&&_79&&_79.length){dojo.forEach(_79,function(_7b){_7a+=("<li>");_7a+=("<a href='"+_7b.url+"' target='_blank'>"+(_7b.name||"[No name]")+"</a>");_7a+=("</li>");});}dojo.attr(this._attachmentsList,"innerHTML",_7a||"<li>"+this._nls.NLS_noAttach+"</li>");}}});}