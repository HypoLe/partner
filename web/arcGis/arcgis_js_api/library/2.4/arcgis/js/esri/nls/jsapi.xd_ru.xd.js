window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(dojo, dijit, dojox){
return {depends: [["provide", "esri.nls.jsapi_ru"],
["provide", "esri.nls.jsapi"],
["provide", "esri.nls.jsapi.ru"],
["provide", "dojo.cldr.nls.number"],
["provide", "dojo.cldr.nls.number.ru"],
["provide", "dojo.cldr.nls.gregorian"],
["provide", "dojo.cldr.nls.gregorian.ru"]],
defineResource: function(dojo, dijit, dojox){dojo.provide("esri.nls.jsapi_ru");dojo.provide("esri.nls.jsapi");esri.nls.jsapi._built=true;dojo.provide("esri.nls.jsapi.ru");esri.nls.jsapi.ru={"arcgis":{"utils":{"geometryServiceError":"����� ������� ���-�����, �������������� ������� �������������� ����������.","baseLayerError":"���������� ��������� ������� ���� �����"}},"toolbars":{"draw":{"addShape":"��������, ����� �������� �����","finish":"������ �������� ��� ����������","invalidType":"���������������� ��� �������������� ������","resume":"��������, ����� ���������� �������� �������","addPoint":"��������, ����� �������� �����","freehand":"������� ������, ����� ������, � ��������� ��, ����� ���������","complete":"������ �������� ��� ����������","start":"��������, ����� ���������� � �������� �������","addMultipoint":"��������, ����� ������ ���������� �����","convertAntiClockwisePolygon":"��������������, ����������� ������ ������� �������, ����� ��������� �� ������� �������."},"edit":{"invalidType":"���������� ������������ ����������. ��������� � ���, ��� ������ ��� �������������� ������ �������������� ���� ������������.","deleteLabel":"�������"}},"widgets":{"timeSlider":{"NLS_previous":"�����","NLS_play":"���������������/�����","NLS_next":"�����","NLS_invalidTimeExtent":"�������� \"TimeExtent\" �� ����� ��� ����� �������� ������.","NLS_first":"������"},"editor":{"tools":{"NLS_pointLbl":"�����","NLS_reshapeLbl":"�������� �����","NLS_arrowLeftLbl":"������� �����","NLS_triangleLbl":"�����������","NLS_autoCompleteLbl":"�������������� ����������","NLS_arrowDownLbl":"������� ����","NLS_selectionRemoveLbl":"������ �� ���������","NLS_unionLbl":"�����������","NLS_freehandPolylineLbl":"������� ����� ������������ �����","NLS_rectangleLbl":"�������������","NLS_ellipseLbl":"������","NLS_attributesLbl":"��������","NLS_arrowUpLbl":"������� �����","NLS_arrowRightLbl":"������� ������","NLS_undoLbl":"��������","NLS_arrowLbl":"�������","NLS_cutLbl":"��������","NLS_polylineLbl":"������� �����","NLS_selectionClearLbl":"�������� ���������","NLS_polygonLbl":"�������������","NLS_selectionUnionLbl":"�����������","NLS_freehandPolygonLbl":"������������� ������������ �����","NLS_deleteLbl":"�������","NLS_extentLbl":"���������","NLS_selectionNewLbl":"������� ���������","NLS_circleLbl":"����","NLS_redoLbl":"���������","NLS_selectionAddLbl":"�������� � ���������"}},"legend":{"NLS_creatingLegend":"�������� ��������� �����������","NLS_noLegend":"��� �������� �����������"},"measurement":{"NLS_length_kilometers":"���������","NLS_area_sq_miles":"��. ����","NLS_length_yards":"����","NLS_distance":"����������","NLS_area_acres":"����","NLS_resultLabel":"��������� ���������","NLS_length_miles":"����","NLS_area_hectares":"�������","NLS_area":"�������","NLS_area_sq_meters":"��. �����","NLS_latitude":"������","NLS_area_sq_kilometers":"��. ���������","NLS_area_sq_feet":"��. ����","NLS_longitude":"�������","NLS_location":"��������������","NLS_length_feet":"����","NLS_area_sq_yards":"��. ����","NLS_length_meters":"�����"},"overviewMap":{"NLS_invalidSR":"���������������� �������� ���������� ���� ����������� � ������� ������","NLS_invalidType":"���������������� ��� ����. ���������� ����: \"TiledMapServiceLayer\" � \"DynamicMapServiceLayer\"","NLS_noMap":"�������� \"map\" �� ������ � ���������� �����","NLS_hide":"������ ����� �����","NLS_drag":"���������� ��� ��������� ����� �����","NLS_maximize":"����������","NLS_noLayer":"� ������� ����� ��� �������� ����","NLS_restore":"������������","NLS_show":"�������� ����� �����"},"attributeInspector":{"NLS_title":"�������� ��������","NLS_validationFlt":"�������� ������ ���� ������ � ��������� ������.","NLS_noFeaturesSelected":"������� �� �������","NLS_validationInt":"�������� ������ ���� ����� ������.","NLS_next":"�����","NLS_errorInvalid":"�����������","NLS_previous":"�����","NLS_first":"������","NLS_deleteFeature":"�������","NLS_of":"��","NLS_last":"���������"},"popup":{"NLS_nextFeature":"��������� ������","NLS_searching":"�����","NLS_maximize":"����������","NLS_noAttach":"�������� �� �������","NLS_noInfo":"���������� ����������","NLS_restore":"������������","NLS_prevFeature":"���������� ������","NLS_nextMedia":"��������� ��������","NLS_close":"�������","NLS_prevMedia":"���������� ��������"},"attachmentEditor":{"NLS_add":"��������","NLS_none":"���","NLS_attachments":"��������:"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"���������� ������ �������� \"BingMapsKey\"."},"vegeocode":{"bingMapsKeyNotSpecified":"���������� ������ �������� \"BingMapsKey\".","requestQueued":"������ ������� �� ��������. ������ ��������� � ������� � ����� �������� ����� ���������� ������� �������."}},"layers":{"FeatureLayer":{"noGeometryField":"���������� ����� ���� ���� \"esriFieldTypeGeometry\" � ������ � ���� \"fields\". ���� ������������ ��������� ���� �����, ������� �� ������������ �������������� ������ [URL-�����: ${url}]","fieldNotFound":"���������� ����� ���� \"${field}\" � ������ � ���� \"fields\" [URL-�����: ${url}]","updateError":"��� ���������� ���� ��������� ������","noOIDField":"�������� \"objectIdField\" �� ����� [URL-�����: ${url}]","invalidParams":"� ������� ���������� ���� ��� ��������� ���������������� ����������"},"dynamic":{"imageError":"���������� ��������� �����������"},"tiled":{"tileError":"���������� ��������� �������"},"imageParameters":{"deprecateBBox":"�������� \"bbox\" ���������. ����������� �������� \"extent\"."},"agstiled":{"deprecateRoundrobin":"�������� ������������ \"roundrobin\" ��������. ����������� �������� \"tileServers\"."},"graphics":{"drawingError":"���������� ������� ������� "}},"tasks":{"gp":{"gpDataTypeNotHandled":"��� ������ GP �� ���������."},"query":{"invalid":"���������� ��������� ������. ��������� ���������."},"na":{"route":{"routeNameNotSpecified":"�������� \"RouteName\" �� ����� ��� ������� ��� 1 ��������� � ������ ��������� FeatureSet."}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}};dojo.provide("dojo.cldr.nls.number");dojo.cldr.nls.number._built=true;dojo.provide("dojo.cldr.nls.number.ru");dojo.cldr.nls.number.ru={"group":" ","percentSign":"%","exponential":"E","percentFormat":"#,##0 %","scientificFormat":"#E0","list":";","infinity":"∞","patternDigit":"#","minusSign":"-","decimal":",","nativeZeroDigit":"0","perMille":"‰","decimalFormat":"#,##0.###","currencyFormat":"#,##0.00 ¤","plusSign":"+","currencySpacing-afterCurrency-currencyMatch":"[:letter:]","currencySpacing-beforeCurrency-surroundingMatch":"[:digit:]","decimalFormat-short":"000T","currencySpacing-afterCurrency-insertBetween":" ","nan":"NaN","currencySpacing-afterCurrency-surroundingMatch":"[:digit:]","currencySpacing-beforeCurrency-currencyMatch":"[:letter:]","currencySpacing-beforeCurrency-insertBetween":" "};dojo.provide("dojo.cldr.nls.gregorian");dojo.cldr.nls.gregorian._built=true;dojo.provide("dojo.cldr.nls.gregorian.ru");dojo.cldr.nls.gregorian.ru={"dateFormatItem-yM":"M.y","field-dayperiod":"AM/PM","field-minute":"Минута","eraNames":["до н.э.","н.э."],"dateFormatItem-MMMEd":"ccc, d MMM","field-day-relative+-1":"Вчера","field-weekday":"День недели","dateFormatItem-yQQQ":"y QQQ","field-day-relative+-2":"Позавчера","dateFormatItem-MMdd":"dd.MM","days-standAlone-wide":["Воскресенье","Понедельник","Вторник","Среда","Четверг","Пятница","Суббота"],"dateFormatItem-MMM":"LLL","months-standAlone-narrow":["Я","Ф","М","А","М","И","И","А","С","О","Н","Д"],"field-era":"Эра","field-hour":"Час","quarters-standAlone-abbr":["1-й кв.","2-й кв.","3-й кв.","4-й кв."],"dateFormatItem-yyMMMEEEd":"EEE, d MMM yy","dateFormatItem-y":"y","timeFormat-full":"H:mm:ss zzzz","dateFormatItem-yyyy":"y","months-standAlone-abbr":["янв.","февр.","март","апр.","май","июнь","июль","авг.","сент.","окт.","нояб.","дек."],"dateFormatItem-Ed":"E, d","dateFormatItem-yMMM":"LLL y","field-day-relative+0":"Сегодня","dateFormatItem-yyyyLLLL":"LLLL y","field-day-relative+1":"Завтра","days-standAlone-narrow":["В","П","В","С","Ч","П","С"],"eraAbbr":["до н.э.","н.э."],"field-day-relative+2":"Послезавтра","dateFormatItem-yyyyMM":"MM.yyyy","dateFormatItem-yyyyMMMM":"LLLL y","dateFormat-long":"d MMMM y 'г'.","timeFormat-medium":"H:mm:ss","field-zone":"Часовой пояс","dateFormatItem-Hm":"H:mm","dateFormat-medium":"dd.MM.yyyy","dateFormatItem-yyMM":"MM.yy","dateFormatItem-Hms":"H:mm:ss","dateFormatItem-yyMMM":"LLL yy","quarters-standAlone-wide":["1-й квартал","2-й квартал","3-й квартал","4-й квартал"],"dateFormatItem-ms":"mm:ss","dateFormatItem-yyyyQQQQ":"QQQQ y 'г'.","field-year":"Год","months-standAlone-wide":["Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"],"field-week":"Неделя","dateFormatItem-MMMd":"d MMM","dateFormatItem-yyQ":"Q yy","timeFormat-long":"H:mm:ss z","months-format-abbr":["янв.","февр.","марта","апр.","мая","июня","июля","авг.","сент.","окт.","нояб.","дек."],"timeFormat-short":"H:mm","dateFormatItem-H":"H","field-month":"Месяц","quarters-format-abbr":["1-й кв.","2-й кв.","3-й кв.","4-й кв."],"days-format-abbr":["вс","пн","вт","ср","чт","пт","сб"],"dateFormatItem-M":"L","days-format-narrow":["В","П","В","С","Ч","П","С"],"field-second":"Секунда","field-day":"День","dateFormatItem-MEd":"E, d.M","months-format-narrow":["Я","Ф","М","А","М","И","И","А","С","О","Н","Д"],"days-standAlone-abbr":["Вс","Пн","Вт","Ср","Чт","Пт","Сб"],"dateFormat-short":"dd.MM.yy","dateFormatItem-yMMMEd":"E, d MMM y","dateFormat-full":"EEEE, d MMMM y 'г'.","dateFormatItem-Md":"d.M","dateFormatItem-yMEd":"EEE, d.M.y","months-format-wide":["января","февраля","марта","апреля","мая","июня","июля","августа","сентября","октября","ноября","декабря"],"dateFormatItem-d":"d","quarters-format-wide":["1-й квартал","2-й квартал","3-й квартал","4-й квартал"],"days-format-wide":["воскресенье","понедельник","вторник","среда","четверг","пятница","суббота"],"eraNarrow":["до н.э.","н.э."],"quarters-standAlone-narrow":["1","2","3","4"],"dateTimeFormats-appendItem-Day-Of-Week":"{0} {1}","dateTimeFormat-medium":"{1} {0}","dateFormatItem-EEEd":"d EEE","dayPeriods-format-wide-pm":"PM","dayPeriods-format-abbr-am":"AM","dateTimeFormats-appendItem-Second":"{0} ({2}: {1})","dateFormatItem-yQ":"y Q","dateTimeFormats-appendItem-Era":"{0} {1}","dateTimeFormats-appendItem-Week":"{0} ({2}: {1})","quarters-format-narrow":["1","2","3","4"],"dateFormatItem-h":"h a","dateTimeFormat-long":"{1} {0}","dayPeriods-format-narrow-am":"AM","dateTimeFormat-full":"{1} {0}","dateTimeFormats-appendItem-Day":"{0} ({2}: {1})","dateFormatItem-hm":"h:mm a","dateTimeFormats-appendItem-Year":"{0} {1}","dateTimeFormats-appendItem-Hour":"{0} ({2}: {1})","dayPeriods-format-abbr-pm":"PM","dateTimeFormats-appendItem-Quarter":"{0} ({2}: {1})","dayPeriods-format-wide-am":"AM","dateTimeFormats-appendItem-Month":"{0} ({2}: {1})","dateTimeFormats-appendItem-Minute":"{0} ({2}: {1})","dateTimeFormats-appendItem-Timezone":"{0} {1}","dayPeriods-format-narrow-pm":"PM","dateTimeFormat-short":"{1} {0}","dateFormatItem-hms":"h:mm:ss a"};

}};});