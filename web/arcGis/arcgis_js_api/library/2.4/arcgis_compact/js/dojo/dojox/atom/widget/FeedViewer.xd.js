/*
	Copyright (c) 2004-2011, The Dojo Foundation All Rights Reserved.
	Available via Academic Free License >= 2.1 OR the modified BSD license.
	see: http://dojotoolkit.org/license for details
*/


window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","dojox.atom.widget.FeedViewer"],["require","dijit._Widget"],["require","dijit._Templated"],["require","dijit._Container"],["require","dojox.atom.io.Connection"],["requireLocalization","dojox.atom.widget","FeedViewerEntry",null,"ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw","ROOT,ar,ca,cs,da,de,el,es,fi,fr,he,hu,it,ja,kk,ko,nb,nl,pl,pt,pt-pt,ro,ru,sk,sl,sv,th,tr,zh,zh-tw"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["dojox.atom.widget.FeedViewer"]){_4._hasResource["dojox.atom.widget.FeedViewer"]=true;_4.provide("dojox.atom.widget.FeedViewer");_4.require("dijit._Widget");_4.require("dijit._Templated");_4.require("dijit._Container");_4.require("dojox.atom.io.Connection");_4.experimental("dojox.atom.widget.FeedViewer");_4.declare("dojox.atom.widget.FeedViewer",[_5._Widget,_5._Templated,_5._Container],{feedViewerTableBody:null,feedViewerTable:null,entrySelectionTopic:"",url:"",xmethod:false,localSaveOnly:false,templateString:_4.cache("dojox.atom","widget/templates/FeedViewer.html","<div class=\"feedViewerContainer\" dojoAttachPoint=\"feedViewerContainerNode\">\r\n\t<table cellspacing=\"0\" cellpadding=\"0\" class=\"feedViewerTable\">\r\n\t\t<tbody dojoAttachPoint=\"feedViewerTableBody\" class=\"feedViewerTableBody\">\r\n\t\t</tbody>\r\n\t</table>\r\n</div>\r\n"),_feed:null,_currentSelection:null,_includeFilters:null,alertsEnabled:false,postCreate:function(){this._includeFilters=[];if(this.entrySelectionTopic!==""){this._subscriptions=[_4.subscribe(this.entrySelectionTopic,this,"_handleEvent")];}this.atomIO=new _6.atom.io.Connection();this.childWidgets=[];},startup:function(){this.containerNode=this.feedViewerTableBody;var _7=this.getDescendants();for(var i in _7){var _8=_7[i];if(_8&&_8.isFilter){this._includeFilters.push(new _6.atom.widget.FeedViewer.CategoryIncludeFilter(_8.scheme,_8.term,_8.label));_8.destroy();}}if(this.url!==""){this.setFeedFromUrl(this.url);}},clear:function(){this.destroyDescendants();},setFeedFromUrl:function(_9){if(_9!==""){if(this._isRelativeURL(_9)){var _a="";if(_9.charAt(0)!=="/"){_a=this._calculateBaseURL(window.location.href,true);}else{_a=this._calculateBaseURL(window.location.href,false);}this.url=_a+_9;}this.atomIO.getFeed(_9,_4.hitch(this,this.setFeed));}},setFeed:function(_b){this._feed=_b;this.clear();var _c=function(a,b){var _d=this._displayDateForEntry(a);var _e=this._displayDateForEntry(b);if(_d>_e){return -1;}if(_d<_e){return 1;}return 0;};var _f=function(_10){var _11=_10.split(",");_11.pop();return _11.join(",");};var _12=_b.entries.sort(_4.hitch(this,_c));if(_b){var _13=null;for(var i=0;i<_12.length;i++){var _14=_12[i];if(this._isFilterAccepted(_14)){var _15=this._displayDateForEntry(_14);var _16="";if(_15!==null){_16=_f(_15.toLocaleString());if(_16===""){_16=""+(_15.getMonth()+1)+"/"+_15.getDate()+"/"+_15.getFullYear();}}if((_13===null)||(_13!=_16)){this.appendGrouping(_16);_13=_16;}this.appendEntry(_14);}}}},_displayDateForEntry:function(_17){if(_17.updated){return _17.updated;}if(_17.modified){return _17.modified;}if(_17.issued){return _17.issued;}return new Date();},appendGrouping:function(_18){var _19=new _6.atom.widget.FeedViewerGrouping({});_19.setText(_18);this.addChild(_19);this.childWidgets.push(_19);},appendEntry:function(_1a){var _1b=new _6.atom.widget.FeedViewerEntry({"xmethod":this.xmethod});_1b.setTitle(_1a.title.value);_1b.setTime(this._displayDateForEntry(_1a).toLocaleTimeString());_1b.entrySelectionTopic=this.entrySelectionTopic;_1b.feed=this;this.addChild(_1b);this.childWidgets.push(_1b);this.connect(_1b,"onClick","_rowSelected");_1a.domNode=_1b.entryNode;_1a._entryWidget=_1b;_1b.entry=_1a;},deleteEntry:function(_1c){if(!this.localSaveOnly){this.atomIO.deleteEntry(_1c.entry,_4.hitch(this,this._removeEntry,_1c),null,this.xmethod);}else{this._removeEntry(_1c,true);}_4.publish(this.entrySelectionTopic,[{action:"delete",source:this,entry:_1c.entry}]);},_removeEntry:function(_1d,_1e){if(_1e){var idx=_4.indexOf(this.childWidgets,_1d);var _1f=this.childWidgets[idx-1];var _20=this.childWidgets[idx+1];if(_1f.declaredClass==="dojox.atom.widget.FeedViewerGrouping"&&(_20===undefined||_20.declaredClass==="dojox.atom.widget.FeedViewerGrouping")){_1f.destroy();}_1d.destroy();}else{}},_rowSelected:function(evt){var _21=evt.target;while(_21){if(_21.attributes){var _22=_21.attributes.getNamedItem("widgetid");if(_22&&_22.value.indexOf("FeedViewerEntry")!=-1){break;}}_21=_21.parentNode;}for(var i=0;i<this._feed.entries.length;i++){var _23=this._feed.entries[i];if((_21===_23.domNode)&&(this._currentSelection!==_23)){_4.addClass(_23.domNode,"feedViewerEntrySelected");_4.removeClass(_23._entryWidget.timeNode,"feedViewerEntryUpdated");_4.addClass(_23._entryWidget.timeNode,"feedViewerEntryUpdatedSelected");this.onEntrySelected(_23);if(this.entrySelectionTopic!==""){_4.publish(this.entrySelectionTopic,[{action:"set",source:this,feed:this._feed,entry:_23}]);}if(this._isEditable(_23)){_23._entryWidget.enableDelete();}this._deselectCurrentSelection();this._currentSelection=_23;break;}else{if((_21===_23.domNode)&&(this._currentSelection===_23)){_4.publish(this.entrySelectionTopic,[{action:"delete",source:this,entry:_23}]);this._deselectCurrentSelection();break;}}}},_deselectCurrentSelection:function(){if(this._currentSelection){_4.addClass(this._currentSelection._entryWidget.timeNode,"feedViewerEntryUpdated");_4.removeClass(this._currentSelection.domNode,"feedViewerEntrySelected");_4.removeClass(this._currentSelection._entryWidget.timeNode,"feedViewerEntryUpdatedSelected");this._currentSelection._entryWidget.disableDelete();this._currentSelection=null;}},_isEditable:function(_24){var _25=false;if(_24&&_24!==null&&_24.links&&_24.links!==null){for(var x in _24.links){if(_24.links[x].rel&&_24.links[x].rel=="edit"){_25=true;break;}}}return _25;},onEntrySelected:function(_26){},_isRelativeURL:function(url){var _27=function(url){var _28=false;if(url.indexOf("file://")===0){_28=true;}return _28;};var _29=function(url){var _2a=false;if(url.indexOf("http://")===0){_2a=true;}return _2a;};var _2b=false;if(url!==null){if(!_27(url)&&!_29(url)){_2b=true;}}return _2b;},_calculateBaseURL:function(_2c,_2d){var _2e=null;if(_2c!==null){var _2f=_2c.indexOf("?");if(_2f!=-1){_2c=_2c.substring(0,_2f);}if(_2d){_2f=_2c.lastIndexOf("/");if((_2f>0)&&(_2f<_2c.length)&&(_2f!==(_2c.length-1))){_2e=_2c.substring(0,(_2f+1));}else{_2e=_2c;}}else{_2f=_2c.indexOf("://");if(_2f>0){_2f=_2f+3;var _30=_2c.substring(0,_2f);var _31=_2c.substring(_2f,_2c.length);_2f=_31.indexOf("/");if((_2f<_31.length)&&(_2f>0)){_2e=_30+_31.substring(0,_2f);}else{_2e=_30+_31;}}}}return _2e;},_isFilterAccepted:function(_32){var _33=false;if(this._includeFilters&&(this._includeFilters.length>0)){for(var i=0;i<this._includeFilters.length;i++){var _34=this._includeFilters[i];if(_34.match(_32)){_33=true;break;}}}else{_33=true;}return _33;},addCategoryIncludeFilter:function(_35){if(_35){var _36=_35.scheme;var _37=_35.term;var _38=_35.label;var _39=true;if(!_36){_36=null;}if(!_37){_36=null;}if(!_38){_36=null;}if(this._includeFilters&&this._includeFilters.length>0){for(var i=0;i<this._includeFilters.length;i++){var _3a=this._includeFilters[i];if((_3a.term===_37)&&(_3a.scheme===_36)&&(_3a.label===_38)){_39=false;break;}}}if(_39){this._includeFilters.push(_6.atom.widget.FeedViewer.CategoryIncludeFilter(_36,_37,_38));}}},removeCategoryIncludeFilter:function(_3b){if(_3b){var _3c=_3b.scheme;var _3d=_3b.term;var _3e=_3b.label;if(!_3c){_3c=null;}if(!_3d){_3c=null;}if(!_3e){_3c=null;}var _3f=[];if(this._includeFilters&&this._includeFilters.length>0){for(var i=0;i<this._includeFilters.length;i++){var _40=this._includeFilters[i];if(!((_40.term===_3d)&&(_40.scheme===_3c)&&(_40.label===_3e))){_3f.push(_40);}}this._includeFilters=_3f;}}},_handleEvent:function(_41){if(_41.source!=this){if(_41.action=="update"&&_41.entry){var evt=_41;if(!this.localSaveOnly){this.atomIO.updateEntry(evt.entry,_4.hitch(evt.source,evt.callback),null,true);}this._currentSelection._entryWidget.setTime(this._displayDateForEntry(evt.entry).toLocaleTimeString());this._currentSelection._entryWidget.setTitle(evt.entry.title.value);}else{if(_41.action=="post"&&_41.entry){if(!this.localSaveOnly){this.atomIO.addEntry(_41.entry,this.url,_4.hitch(this,this._addEntry));}else{this._addEntry(_41.entry);}}}}},_addEntry:function(_42){this._feed.addEntry(_42);this.setFeed(this._feed);_4.publish(this.entrySelectionTopic,[{action:"set",source:this,feed:this._feed,entry:_42}]);},destroy:function(){this.clear();_4.forEach(this._subscriptions,_4.unsubscribe);}});_4.declare("dojox.atom.widget.FeedViewerEntry",[_5._Widget,_5._Templated],{templateString:_4.cache("dojox.atom","widget/templates/FeedViewerEntry.html","<tr class=\"feedViewerEntry\" dojoAttachPoint=\"entryNode\" dojoAttachEvent=\"onclick:onClick\">\r\n    <td class=\"feedViewerEntryUpdated\" dojoAttachPoint=\"timeNode\">\r\n    </td>\r\n    <td>\r\n        <table border=\"0\" width=\"100%\" dojoAttachPoint=\"titleRow\">\r\n            <tr padding=\"0\" border=\"0\">\r\n                <td class=\"feedViewerEntryTitle\" dojoAttachPoint=\"titleNode\">\r\n                </td>\r\n                <td class=\"feedViewerEntryDelete\" align=\"right\">\r\n                    <span dojoAttachPoint=\"deleteButton\" dojoAttachEvent=\"onclick:deleteEntry\" class=\"feedViewerDeleteButton\" style=\"display:none;\">[delete]</span>\r\n                </td>\r\n            <tr>\r\n        </table>\r\n    </td>\r\n</tr>\r\n"),entryNode:null,timeNode:null,deleteButton:null,entry:null,feed:null,postCreate:function(){var _43=_4.i18n.getLocalization("dojox.atom.widget","FeedViewerEntry");this.deleteButton.innerHTML=_43.deleteButton;},setTitle:function(_44){if(this.titleNode.lastChild){this.titleNode.removeChild(this.titleNode.lastChild);}var _45=document.createElement("div");_45.innerHTML=_44;this.titleNode.appendChild(_45);},setTime:function(_46){if(this.timeNode.lastChild){this.timeNode.removeChild(this.timeNode.lastChild);}var _47=document.createTextNode(_46);this.timeNode.appendChild(_47);},enableDelete:function(){if(this.deleteButton!==null){this.deleteButton.style.display="inline";}},disableDelete:function(){if(this.deleteButton!==null){this.deleteButton.style.display="none";}},deleteEntry:function(_48){_48.preventDefault();_48.stopPropagation();this.feed.deleteEntry(this);},onClick:function(e){}});_4.declare("dojox.atom.widget.FeedViewerGrouping",[_5._Widget,_5._Templated],{templateString:_4.cache("dojox.atom","widget/templates/FeedViewerGrouping.html","<tr dojoAttachPoint=\"groupingNode\" class=\"feedViewerGrouping\">\r\n\t<td colspan=\"2\" dojoAttachPoint=\"titleNode\" class=\"feedViewerGroupingTitle\">\r\n\t</td>\r\n</tr>\r\n"),groupingNode:null,titleNode:null,setText:function(_49){if(this.titleNode.lastChild){this.titleNode.removeChild(this.titleNode.lastChild);}var _4a=document.createTextNode(_49);this.titleNode.appendChild(_4a);}});_4.declare("dojox.atom.widget.AtomEntryCategoryFilter",[_5._Widget,_5._Templated],{scheme:"",term:"",label:"",isFilter:true});_4.declare("dojox.atom.widget.FeedViewer.CategoryIncludeFilter",null,{constructor:function(_4b,_4c,_4d){this.scheme=_4b;this.term=_4c;this.label=_4d;},match:function(_4e){var _4f=false;if(_4e!==null){var _50=_4e.categories;if(_50!==null){for(var i=0;i<_50.length;i++){var _51=_50[i];if(this.scheme!==""){if(this.scheme!==_51.scheme){break;}}if(this.term!==""){if(this.term!==_51.term){break;}}if(this.label!==""){if(this.label!==_51.label){break;}}_4f=true;}}}return _4f;}});}}};});