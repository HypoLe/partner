dojo.provide("esri.nls.jsapi_ko-kr");dojo.provide("esri.nls.jsapi");esri.nls.jsapi._built=true;dojo.provide("esri.nls.jsapi.ko_kr");esri.nls.jsapi.ko_kr={"arcgis":{"utils":{"geometryServiceError":"Provide a geometry service to open Web Map.","baseLayerError":"Unable to load the base map layer"}},"toolbars":{"draw":{"addShape":"Click to add a shape","finish":"Double-click to finish","invalidType":"Unsupported geometry type","resume":"Click to continue drawing","addPoint":"Click to add a point","freehand":"Press down to start and let go to finish","complete":"Double-click to complete","start":"Click to start drawing","addMultipoint":"Click to start adding points","convertAntiClockwisePolygon":"Polygons drawn in anti-clockwise direction will be reversed to be clockwise."},"edit":{"invalidType":"Unable to activate the tool. Check if the tool is valid for the given geometry type.","deleteLabel":"Delete"}},"widgets":{"timeSlider":{"NLS_previous":"Previous","NLS_play":"Play/Pause","NLS_next":"Next","NLS_invalidTimeExtent":"TimeExtent not specified, or in incorrect format.","NLS_first":"First"},"editor":{"tools":{"NLS_pointLbl":"Point","NLS_reshapeLbl":"Reshape","NLS_arrowLeftLbl":"Left Arrow","NLS_triangleLbl":"Triangle","NLS_autoCompleteLbl":"Auto Complete","NLS_arrowDownLbl":"Down Arrow","NLS_selectionRemoveLbl":"Subtract from selection","NLS_unionLbl":"Union","NLS_freehandPolylineLbl":"Freehand Polyline","NLS_rectangleLbl":"Rectangle","NLS_ellipseLbl":"Ellipse","NLS_attributesLbl":"Attributes","NLS_arrowUpLbl":"Up Arrow","NLS_arrowRightLbl":"Right Arrow","NLS_undoLbl":"Undo","NLS_arrowLbl":"Arrow","NLS_cutLbl":"Cut","NLS_polylineLbl":"Polyline","NLS_selectionClearLbl":"Clear selection","NLS_polygonLbl":"Polygon","NLS_selectionUnionLbl":"Union","NLS_freehandPolygonLbl":"Freehand Polygon","NLS_deleteLbl":"Delete","NLS_extentLbl":"Extent","NLS_selectionNewLbl":"New selection","NLS_circleLbl":"Circle","NLS_redoLbl":"Redo","NLS_selectionAddLbl":"Add to selection"}},"legend":{"NLS_creatingLegend":"Creating legend","NLS_noLegend":"No legend"},"measurement":{"NLS_length_kilometers":"Kilometers","NLS_area_sq_miles":"Sq Miles","NLS_length_yards":"Yards","NLS_distance":"Distance","NLS_area_acres":"Acres","NLS_resultLabel":"Measurement Result","NLS_length_miles":"Miles","NLS_area_hectares":"Hectares","NLS_area":"Area","NLS_area_sq_meters":"Sq Meters","NLS_latitude":"Latitude","NLS_area_sq_kilometers":"Sq Kilometers","NLS_area_sq_feet":"Sq Feet","NLS_longitude":"Longitude","NLS_location":"Location","NLS_length_feet":"Feet","NLS_area_sq_yards":"Sq Yards","NLS_length_meters":"Meters"},"overviewMap":{"NLS_invalidSR":"spatial reference of the given layer is not compatible with the main map","NLS_invalidType":"unsupported layer type. Valid types are 'TiledMapServiceLayer' and 'DynamicMapServiceLayer'","NLS_noMap":"'map' not found in input parameters","NLS_hide":"Hide Map Overview","NLS_drag":"Drag To Change The Map Extent","NLS_maximize":"Maximize","NLS_noLayer":"main map does not have a base layer","NLS_restore":"Restore","NLS_show":"Show Map Overview"},"attributeInspector":{"NLS_title":"Edit Attributes","NLS_validationFlt":"Value must be a float.","NLS_noFeaturesSelected":"No features selected","NLS_validationInt":"Value must be an integer.","NLS_next":"Next","NLS_errorInvalid":"Invalid","NLS_previous":"Previous","NLS_first":"First","NLS_deleteFeature":"Delete","NLS_of":"of","NLS_last":"Last"},"popup":{"NLS_nextFeature":"Next feature","NLS_searching":"Searching","NLS_maximize":"Maximize","NLS_noAttach":"No attachments found","NLS_noInfo":"No information available","NLS_restore":"Restore","NLS_prevFeature":"Previous feature","NLS_nextMedia":"Next media","NLS_close":"Close","NLS_prevMedia":"Previous media"},"attachmentEditor":{"NLS_add":"Add","NLS_none":"None","NLS_attachments":"Attachments:"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"BingMapsKey must be provided."},"vegeocode":{"bingMapsKeyNotSpecified":"BingMapsKey must be provided.","requestQueued":"Server token not retrieved. Queing request to be executed after server token retrieved."}},"layers":{"FeatureLayer":{"noGeometryField":"unable to find a field of type 'esriFieldTypeGeometry' in the layer 'fields' information. If you are using a map service layer, features will not have geometry [url: ${url}]","fieldNotFound":"unable to find '${field}' field in the layer 'fields' information [url: ${url}]","updateError":"an error occurred while updating the layer","noOIDField":"objectIdField is not set [url: ${url}]","invalidParams":"query contains one or more unsupported parameters"},"dynamic":{"imageError":"Unable to load image"},"tiled":{"tileError":"Unable to load tile"},"imageParameters":{"deprecateBBox":"Property 'bbox' deprecated. Use property 'extent'."},"agstiled":{"deprecateRoundrobin":"Constructor option 'roundrobin' deprecated. Use option 'tileServers'."},"graphics":{"drawingError":"Unable to draw graphic "}},"tasks":{"gp":{"gpDataTypeNotHandled":"GP Data type not handled."},"query":{"invalid":"Unable to perform query. Please check your parameters."},"na":{"route":{"routeNameNotSpecified":"'RouteName' not specified for atleast 1 stop in stops FeatureSet."}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}};dojo.provide("dojo.cldr.nls.number");dojo.cldr.nls.number._built=true;dojo.provide("dojo.cldr.nls.number.ko_kr");dojo.cldr.nls.number.ko_kr={"group":",","percentSign":"%","exponential":"E","percentFormat":"#,##0%","scientificFormat":"#E0","list":";","infinity":"∞","patternDigit":"#","minusSign":"-","decimal":".","nan":"NaN","nativeZeroDigit":"0","perMille":"‰","decimalFormat":"#,##0.###","currencyFormat":"¤#,##0.00","plusSign":"+","currencySpacing-afterCurrency-currencyMatch":"[:letter:]","currencySpacing-beforeCurrency-surroundingMatch":"[:digit:]","decimalFormat-short":"000T","currencySpacing-afterCurrency-insertBetween":" ","currencySpacing-afterCurrency-surroundingMatch":"[:digit:]","currencySpacing-beforeCurrency-currencyMatch":"[:letter:]","currencySpacing-beforeCurrency-insertBetween":" "};dojo.provide("dojo.cldr.nls.gregorian");dojo.cldr.nls.gregorian._built=true;dojo.provide("dojo.cldr.nls.gregorian.ko_kr");dojo.cldr.nls.gregorian.ko_kr={"months-format-narrow":["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],"field-weekday":"요일","dateFormatItem-yQQQ":"y년 QQQ","dateFormatItem-yMEd":"yyyy. M. d. EEE","dateFormatItem-MMMEd":"MMM d일 (E)","eraNarrow":["기원전","서기"],"dateFormat-long":"y년 M월 d일","months-format-wide":["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],"dateTimeFormat-medium":"{1} {0}","dateFormatItem-EEEd":"d일 EEE","dayPeriods-format-wide-pm":"오후","dateFormat-full":"y년 M월 d일 EEEE","dateFormatItem-Md":"M. d.","field-era":"연호","dateFormatItem-yM":"yyyy. M.","months-standAlone-wide":["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],"timeFormat-short":"a h:mm","quarters-format-wide":["제 1/4분기","제 2/4분기","제 3/4분기","제 4/4분기"],"timeFormat-long":"a h시 m분 s초 z","field-year":"년","dateFormatItem-yMMM":"y년 MMM","dateFormatItem-yQ":"y년 Q분기","field-hour":"시","dateFormatItem-MMdd":"MM. dd","months-format-abbr":["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],"dateFormatItem-yyQ":"yy년 Q분기","timeFormat-full":"a h시 m분 s초 zzzz","field-day-relative+0":"오늘","field-day-relative+1":"내일","field-day-relative+2":"모레","dateFormatItem-H":"H시","field-day-relative+3":"3일후","months-standAlone-abbr":["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],"quarters-format-abbr":["1분기","2분기","3분기","4분기"],"quarters-standAlone-wide":["제 1/4분기","제 2/4분기","제 3/4분기","제 4/4분기"],"dateFormatItem-HHmmss":"HH:mm:ss","dateFormatItem-M":"M월","days-standAlone-wide":["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],"dateFormatItem-yyMMM":"yy년 MMM","timeFormat-medium":"a h:mm:ss","dateFormatItem-Hm":"HH:mm","quarters-standAlone-abbr":["1분기","2분기","3분기","4분기"],"eraAbbr":["기원전","서기"],"field-minute":"분","field-dayperiod":"오전/오후","days-standAlone-abbr":["일","월","화","수","목","금","토"],"dateFormatItem-d":"d일","dateFormatItem-ms":"mm:ss","field-day-relative+-1":"어제","dateFormatItem-h":"a h시","dateTimeFormat-long":"{1} {0}","field-day-relative+-2":"그저께","field-day-relative+-3":"그끄제","dateFormatItem-MMMd":"MMM d일","dateFormatItem-MEd":"M. d. (E)","dateTimeFormat-full":"{1} {0}","field-day":"일","days-format-wide":["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],"field-zone":"시간대","dateFormatItem-yyyyMM":"yyyy. MM","dateFormatItem-y":"y년","months-standAlone-narrow":["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],"dateFormatItem-yyMM":"YY. M.","dateFormatItem-hm":"a h:mm","days-format-abbr":["일","월","화","수","목","금","토"],"dateFormatItem-yMMMd":"y년 MMM d일","eraNames":["서력기원전","서력기원"],"days-format-narrow":["일","월","화","수","목","금","토"],"field-month":"월","days-standAlone-narrow":["일","월","화","수","목","금","토"],"dateFormatItem-MMM":"LLL","dayPeriods-format-wide-am":"오전","dateFormat-short":"yy. M. d.","field-second":"초","dateFormatItem-yMMMEd":"y년 MMM d일 EEE","dateFormatItem-Ed":"d일 (E)","field-week":"주","dateFormat-medium":"yyyy. M. d.","dateFormatItem-mmss":"mm:ss","dateTimeFormat-short":"{1} {0}","dateFormatItem-Hms":"H시 m분 s초","dateFormatItem-hms":"a h:mm:ss","quarters-standAlone-narrow":["1","2","3","4"],"dateTimeFormats-appendItem-Day-Of-Week":"{0} {1}","dayPeriods-format-abbr-am":"AM","dateTimeFormats-appendItem-Second":"{0} ({2}: {1})","dateTimeFormats-appendItem-Era":"{0} {1}","dateTimeFormats-appendItem-Week":"{0} ({2}: {1})","quarters-format-narrow":["1","2","3","4"],"dayPeriods-format-narrow-am":"AM","dateTimeFormats-appendItem-Day":"{0} ({2}: {1})","dateTimeFormats-appendItem-Year":"{0} {1}","dateTimeFormats-appendItem-Hour":"{0} ({2}: {1})","dayPeriods-format-abbr-pm":"PM","dateTimeFormats-appendItem-Quarter":"{0} ({2}: {1})","dateTimeFormats-appendItem-Month":"{0} ({2}: {1})","dateTimeFormats-appendItem-Minute":"{0} ({2}: {1})","dateTimeFormats-appendItem-Timezone":"{0} {1}","dayPeriods-format-narrow-pm":"PM"};
