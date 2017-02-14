<%@page contentType="application/x-javascript" session="false" %>
<jsp:include page="js/dojo/dojo/dojo.xd.js" />
<jsp:include page="js/esri/esri.js" />

<%--
// We want to enable this at v2.0. But will break applications
// where resources are referenced before dojo.addOnLoad. For example:
// dojo.declare("my.PortlandTiledMapServiceLayer", esri.layers.TiledMapServiceLayer, ...);
// function init() {...}
// dojo.addOnLoad(init);
// will not work. Need to move dojo.declare into init()

<jsp:include page="js/esri/jsapi.xd.js" />
--%>

<% out.print("(function() {var dojo = window[esri._dojoScopeName];var dijit = window[esri._dijitScopeName];var dojox = window[esri._dojoxScopeName];"); %>
<jsp:include page="js/esri/jsapi.js" />
<% out.print("}());"); %>