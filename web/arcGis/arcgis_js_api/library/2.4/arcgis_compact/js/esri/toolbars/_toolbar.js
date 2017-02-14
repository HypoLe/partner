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

if(!dojo._hasResource["esri.toolbars._toolbar"]){dojo._hasResource["esri.toolbars._toolbar"]=true;dojo.provide("esri.toolbars._toolbar");dojo.declare("esri.toolbars._Toolbar",null,{constructor:function(_1){this.map=_1;},_cursors:{"move":"pointer","move-v":"pointer","move-gv":"pointer","box0":"nw-resize","box1":"n-resize","box2":"ne-resize","box3":"e-resize","box4":"se-resize","box5":"s-resize","box6":"sw-resize","box7":"w-resize","box8":"pointer"},_deactivateMapTools:function(_2,_3,_4,_5){var _6=this.map;if(_2){this._mapNavState={isDoubleClickZoom:_6.isDoubleClickZoom,isClickRecenter:_6.isClickRecenter,isPan:_6.isPan,isRubberBandZoom:_6.isRubberBandZoom,isKeyboardNavigation:_6.isKeyboardNavigation,isScrollWheelZoom:_6.isScrollWheelZoom};_6.disableDoubleClickZoom();_6.disableClickRecenter();_6.disablePan();_6.disableRubberBandZoom();_6.disableKeyboardNavigation();}if(_3){_6.hideZoomSlider();}if(_4){_6.hidePanArrows();}if(_5){_6.graphics.disableMouseEvents();}},_activateMapTools:function(_7,_8,_9,_a){var _b=this.map,_c=this._mapNavState;if(_7&&_c){if(_c.isDoubleClickZoom){_b.enableDoubleClickZoom();}if(_c.isClickRecenter){_b.enableClickRecenter();}if(_c.isPan){_b.enablePan();}if(_c.isRubberBandZoom){_b.enableRubberBandZoom();}if(_c.isKeyboardNavigation){_b.enableKeyboardNavigation();}if(_c.isScrollWheelZoom){_b.enableScrollWheelZoom();}}if(_8){_b.showZoomSlider();}if(_9){_b.showPanArrows();}if(_a){_b.graphics.enableMouseEvents();}}});}