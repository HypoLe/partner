window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(dojo, dijit, dojox){
return {depends: [["provide", "esri.nls.jsapi_pt-br"],
["provide", "esri.nls.jsapi"],
["provide", "esri.nls.jsapi.pt_br"],
["provide", "dojo.cldr.nls.number"],
["provide", "dojo.cldr.nls.number.pt_br"],
["provide", "dojo.cldr.nls.gregorian"],
["provide", "dojo.cldr.nls.gregorian.pt_br"]],
defineResource: function(dojo, dijit, dojox){dojo.provide("esri.nls.jsapi_pt-br");dojo.provide("esri.nls.jsapi");esri.nls.jsapi._built=true;dojo.provide("esri.nls.jsapi.pt_br");esri.nls.jsapi.pt_br={"arcgis":{"utils":{"geometryServiceError":"Provide a geometry service to open Web Map.","baseLayerError":"Unable to load the base map layer"}},"toolbars":{"draw":{"addShape":"Click to add a shape","finish":"Double-click to finish","invalidType":"Unsupported geometry type","resume":"Click to continue drawing","addPoint":"Click to add a point","freehand":"Press down to start and let go to finish","complete":"Double-click to complete","start":"Click to start drawing","addMultipoint":"Click to start adding points","convertAntiClockwisePolygon":"Polygons drawn in anti-clockwise direction will be reversed to be clockwise."},"edit":{"invalidType":"Unable to activate the tool. Check if the tool is valid for the given geometry type.","deleteLabel":"Delete"}},"widgets":{"timeSlider":{"NLS_previous":"Previous","NLS_play":"Play/Pause","NLS_next":"Next","NLS_invalidTimeExtent":"TimeExtent not specified, or in incorrect format.","NLS_first":"First"},"editor":{"tools":{"NLS_pointLbl":"Point","NLS_reshapeLbl":"Reshape","NLS_arrowLeftLbl":"Left Arrow","NLS_triangleLbl":"Triangle","NLS_autoCompleteLbl":"Auto Complete","NLS_arrowDownLbl":"Down Arrow","NLS_selectionRemoveLbl":"Subtract from selection","NLS_unionLbl":"Union","NLS_freehandPolylineLbl":"Freehand Polyline","NLS_rectangleLbl":"Rectangle","NLS_ellipseLbl":"Ellipse","NLS_attributesLbl":"Attributes","NLS_arrowUpLbl":"Up Arrow","NLS_arrowRightLbl":"Right Arrow","NLS_undoLbl":"Undo","NLS_arrowLbl":"Arrow","NLS_cutLbl":"Cut","NLS_polylineLbl":"Polyline","NLS_selectionClearLbl":"Clear selection","NLS_polygonLbl":"Polygon","NLS_selectionUnionLbl":"Union","NLS_freehandPolygonLbl":"Freehand Polygon","NLS_deleteLbl":"Delete","NLS_extentLbl":"Extent","NLS_selectionNewLbl":"New selection","NLS_circleLbl":"Circle","NLS_redoLbl":"Redo","NLS_selectionAddLbl":"Add to selection"}},"legend":{"NLS_creatingLegend":"Creating legend","NLS_noLegend":"No legend"},"measurement":{"NLS_length_kilometers":"Kilometers","NLS_area_sq_miles":"Sq Miles","NLS_length_yards":"Yards","NLS_distance":"Distance","NLS_area_acres":"Acres","NLS_resultLabel":"Measurement Result","NLS_length_miles":"Miles","NLS_area_hectares":"Hectares","NLS_area":"Area","NLS_area_sq_meters":"Sq Meters","NLS_latitude":"Latitude","NLS_area_sq_kilometers":"Sq Kilometers","NLS_area_sq_feet":"Sq Feet","NLS_longitude":"Longitude","NLS_location":"Location","NLS_length_feet":"Feet","NLS_area_sq_yards":"Sq Yards","NLS_length_meters":"Meters"},"overviewMap":{"NLS_invalidSR":"spatial reference of the given layer is not compatible with the main map","NLS_invalidType":"unsupported layer type. Valid types are 'TiledMapServiceLayer' and 'DynamicMapServiceLayer'","NLS_noMap":"'map' not found in input parameters","NLS_hide":"Hide Map Overview","NLS_drag":"Drag To Change The Map Extent","NLS_maximize":"Maximize","NLS_noLayer":"main map does not have a base layer","NLS_restore":"Restore","NLS_show":"Show Map Overview"},"attributeInspector":{"NLS_title":"Edit Attributes","NLS_validationFlt":"Value must be a float.","NLS_noFeaturesSelected":"No features selected","NLS_validationInt":"Value must be an integer.","NLS_next":"Next","NLS_errorInvalid":"Invalid","NLS_previous":"Previous","NLS_first":"First","NLS_deleteFeature":"Delete","NLS_of":"of","NLS_last":"Last"},"popup":{"NLS_nextFeature":"Next feature","NLS_searching":"Searching","NLS_maximize":"Maximize","NLS_noAttach":"No attachments found","NLS_noInfo":"No information available","NLS_restore":"Restore","NLS_prevFeature":"Previous feature","NLS_nextMedia":"Next media","NLS_close":"Close","NLS_prevMedia":"Previous media"},"attachmentEditor":{"NLS_add":"Add","NLS_none":"None","NLS_attachments":"Attachments:"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"BingMapsKey must be provided."},"vegeocode":{"bingMapsKeyNotSpecified":"BingMapsKey must be provided.","requestQueued":"Server token not retrieved. Queing request to be executed after server token retrieved."}},"layers":{"FeatureLayer":{"noGeometryField":"unable to find a field of type 'esriFieldTypeGeometry' in the layer 'fields' information. If you are using a map service layer, features will not have geometry [url: ${url}]","fieldNotFound":"unable to find '${field}' field in the layer 'fields' information [url: ${url}]","updateError":"an error occurred while updating the layer","noOIDField":"objectIdField is not set [url: ${url}]","invalidParams":"query contains one or more unsupported parameters"},"dynamic":{"imageError":"Unable to load image"},"tiled":{"tileError":"Unable to load tile"},"imageParameters":{"deprecateBBox":"Property 'bbox' deprecated. Use property 'extent'."},"agstiled":{"deprecateRoundrobin":"Constructor option 'roundrobin' deprecated. Use option 'tileServers'."},"graphics":{"drawingError":"Unable to draw graphic "}},"tasks":{"gp":{"gpDataTypeNotHandled":"GP Data type not handled."},"query":{"invalid":"Unable to perform query. Please check your parameters."},"na":{"route":{"routeNameNotSpecified":"'RouteName' not specified for atleast 1 stop in stops FeatureSet."}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}};dojo.provide("dojo.cldr.nls.number");dojo.cldr.nls.number._built=true;dojo.provide("dojo.cldr.nls.number.pt_br");dojo.cldr.nls.number.pt_br={"group":".","percentSign":"%","exponential":"E","percentFormat":"#,##0%","scientificFormat":"#E0","list":";","infinity":"∞","patternDigit":"#","minusSign":"-","decimal":",","nan":"NaN","nativeZeroDigit":"0","perMille":"‰","decimalFormat":"#,##0.###","currencyFormat":"¤#,##0.00;(¤#,##0.00)","plusSign":"+","currencySpacing-afterCurrency-currencyMatch":"[:letter:]","currencySpacing-beforeCurrency-surroundingMatch":"[:digit:]","decimalFormat-short":"000T","currencySpacing-afterCurrency-insertBetween":" ","currencySpacing-afterCurrency-surroundingMatch":"[:digit:]","currencySpacing-beforeCurrency-currencyMatch":"[:letter:]","currencySpacing-beforeCurrency-insertBetween":" "};dojo.provide("dojo.cldr.nls.gregorian");dojo.cldr.nls.gregorian._built=true;dojo.provide("dojo.cldr.nls.gregorian.pt_br");dojo.cldr.nls.gregorian.pt_br={"months-format-narrow":["J","F","M","A","M","J","J","A","S","O","N","D"],"field-weekday":"Dia da semana","dateFormatItem-yQQQ":"y QQQ","dateFormatItem-yMEd":"EEE, dd/MM/yyyy","dateFormatItem-MMMEd":"EEE, d 'de' MMM","eraNarrow":["a.C.","d.C."],"dayPeriods-format-wide-morning":"manhã","dateFormat-long":"d 'de' MMMM 'de' y","months-format-wide":["janeiro","fevereiro","março","abril","maio","junho","julho","agosto","setembro","outubro","novembro","dezembro"],"dateFormatItem-EEEd":"EEE, d","dateFormat-full":"EEEE, d 'de' MMMM 'de' y","dateFormatItem-Md":"d/M","dayPeriods-format-wide-noon":"meio-dia","field-era":"Era","dateFormatItem-yM":"MM/yyyy","months-standAlone-wide":["janeiro","fevereiro","março","abril","maio","junho","julho","agosto","setembro","outubro","novembro","dezembro"],"timeFormat-short":"HH:mm","quarters-format-wide":["1º trimestre","2º trimestre","3º trimestre","4º trimestre"],"timeFormat-long":"HH'h'mm'min'ss's' z","field-year":"Ano","dateFormatItem-yMMM":"MMM 'de' y","dateFormatItem-yQ":"yyyy Q","field-hour":"Hora","dateFormatItem-MMdd":"dd/MM","months-format-abbr":["jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"],"dateFormatItem-yyQ":"Q yy","timeFormat-full":"HH'h'mm'min'ss's' zzzz","field-day-relative+0":"Hoje","field-day-relative+1":"Amanhã","field-day-relative+2":"Depois de amanhã","field-day-relative+3":"Daqui a três dias","months-standAlone-abbr":["jan","fev","mar","abr","mai","jun","jul","ago","set","out","nov","dez"],"quarters-format-abbr":["T1","T2","T3","T4"],"quarters-standAlone-wide":["1º trimestre","2º trimestre","3º trimestre","4º trimestre"],"dateFormatItem-HHmmss":"HH'h'mm'min'ss's'","dateFormatItem-M":"L","days-standAlone-wide":["domingo","segunda-feira","terça-feira","quarta-feira","quinta-feira","sexta-feira","sábado"],"dateFormatItem-yyyyMMM":"MMM 'de' y","dateFormatItem-yyMMMEEEd":"EEE, d 'de' MMM 'de' yy","dateFormatItem-yyMMM":"MMM 'de' yy","timeFormat-medium":"HH:mm:ss","dateFormatItem-Hm":"HH'h'mm","quarters-standAlone-abbr":["T1","T2","T3","T4"],"eraAbbr":["a.C.","d.C."],"field-minute":"Minuto","field-dayperiod":"Período do dia","days-standAlone-abbr":["dom","seg","ter","qua","qui","sex","sáb"],"dayPeriods-format-wide-night":"noite","dateFormatItem-yyMMMd":"d 'de' MMM 'de' yy","dateFormatItem-d":"d","dateFormatItem-ms":"mm'min'ss's'","field-day-relative+-1":"Ontem","field-day-relative+-2":"Anteontem","field-day-relative+-3":"Há três dias","dateFormatItem-MMMd":"d 'de' MMM","dateFormatItem-MEd":"EEE, dd/MM","field-day":"Dia","days-format-wide":["domingo","segunda-feira","terça-feira","quarta-feira","quinta-feira","sexta-feira","sábado"],"field-zone":"Fuso","dateFormatItem-yyyyMM":"MM/yyyy","dateFormatItem-y":"y","months-standAlone-narrow":["J","F","M","A","M","J","J","A","S","O","N","D"],"dateFormatItem-yyMM":"MM/yy","days-format-abbr":["dom","seg","ter","qua","qui","sex","sáb"],"eraNames":["Antes de Cristo","Ano do Senhor"],"days-format-narrow":["D","S","T","Q","Q","S","S"],"field-month":"Mês","days-standAlone-narrow":["D","S","T","Q","Q","S","S"],"dateFormatItem-MMM":"LLL","dateFormatItem-HHmm":"HH'h'mm","dateFormat-short":"dd/MM/yy","dayPeriods-format-wide-afternoon":"tarde","field-second":"Segundo","dateFormatItem-yMMMEd":"EEE, d 'de' MMM 'de' y","field-week":"Semana","dateFormat-medium":"dd/MM/yyyy","quarters-standAlone-narrow":["1","2","3","4"],"dateTimeFormats-appendItem-Day-Of-Week":"{0} {1}","dateTimeFormat-medium":"{1} {0}","dayPeriods-format-wide-pm":"PM","dayPeriods-format-abbr-am":"AM","dateTimeFormats-appendItem-Second":"{0} ({2}: {1})","dateTimeFormats-appendItem-Era":"{0} {1}","dateTimeFormats-appendItem-Week":"{0} ({2}: {1})","dateFormatItem-H":"HH","quarters-format-narrow":["1","2","3","4"],"dateFormatItem-h":"h a","dateTimeFormat-long":"{1} {0}","dayPeriods-format-narrow-am":"AM","dateTimeFormat-full":"{1} {0}","dateTimeFormats-appendItem-Day":"{0} ({2}: {1})","dateFormatItem-hm":"h:mm a","dateTimeFormats-appendItem-Year":"{0} {1}","dateTimeFormats-appendItem-Hour":"{0} ({2}: {1})","dayPeriods-format-abbr-pm":"PM","dateTimeFormats-appendItem-Quarter":"{0} ({2}: {1})","dayPeriods-format-wide-am":"AM","dateTimeFormats-appendItem-Month":"{0} ({2}: {1})","dateTimeFormats-appendItem-Minute":"{0} ({2}: {1})","dateTimeFormats-appendItem-Timezone":"{0} {1}","dayPeriods-format-narrow-pm":"PM","dateTimeFormat-short":"{1} {0}","dateFormatItem-Hms":"HH:mm:ss","dateFormatItem-hms":"h:mm:ss a"};

}};});