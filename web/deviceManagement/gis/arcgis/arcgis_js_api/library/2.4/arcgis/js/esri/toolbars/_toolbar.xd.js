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

window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(_1,_2,_3){return {depends:[["provide","esri.toolbars._toolbar"]],defineResource:function(_4,_5,_6){if(!_4._hasResource["esri.toolbars._toolbar"]){_4._hasResource["esri.toolbars._toolbar"]=true;_4.provide("esri.toolbars._toolbar");_4.declare("esri.toolbars._Toolbar",null,{constructor:function(_7){this.map=_7;},_cursors:{"move":"pointer","move-v":"pointer","move-gv":"pointer","box0":"nw-resize","box1":"n-resize","box2":"ne-resize","box3":"e-resize","box4":"se-resize","box5":"s-resize","box6":"sw-resize","box7":"w-resize","box8":"pointer"},_deactivateMapTools:function(_8,_9,_a,_b){var _c=this.map;if(_8){this._mapNavState={isDoubleClickZoom:_c.isDoubleClickZoom,isClickRecenter:_c.isClickRecenter,isPan:_c.isPan,isRubberBandZoom:_c.isRubberBandZoom,isKeyboardNavigation:_c.isKeyboardNavigation,isScrollWheelZoom:_c.isScrollWheelZoom};_c.disableDoubleClickZoom();_c.disableClickRecenter();_c.disablePan();_c.disableRubberBandZoom();_c.disableKeyboardNavigation();}if(_9){_c.hideZoomSlider();}if(_a){_c.hidePanArrows();}if(_b){_c.graphics.disableMouseEvents();}},_activateMapTools:function(_d,_e,_f,_10){var map=this.map,_11=this._mapNavState;if(_d&&_11){if(_11.isDoubleClickZoom){map.enableDoubleClickZoom();}if(_11.isClickRecenter){map.enableClickRecenter();}if(_11.isPan){map.enablePan();}if(_11.isRubberBandZoom){map.enableRubberBandZoom();}if(_11.isKeyboardNavigation){map.enableKeyboardNavigation();}if(_11.isScrollWheelZoom){map.enableScrollWheelZoom();}}if(_e){map.showZoomSlider();}if(_f){map.showPanArrows();}if(_10){map.graphics.enableMouseEvents();}}});}}};});