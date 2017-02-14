window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(dojo, dijit, dojox){
return {depends: [["provide", "esri.nls.jsapi_zh-cn"],
["provide", "esri.nls.jsapi"],
["provide", "esri.nls.jsapi.zh_cn"],
["provide", "dojo.cldr.nls.number"],
["provide", "dojo.cldr.nls.number.zh_cn"],
["provide", "dojo.cldr.nls.gregorian"],
["provide", "dojo.cldr.nls.gregorian.zh_cn"]],
defineResource: function(dojo, dijit, dojox){dojo.provide("esri.nls.jsapi_zh-cn");dojo.provide("esri.nls.jsapi");esri.nls.jsapi._built=true;dojo.provide("esri.nls.jsapi.zh_cn");esri.nls.jsapi.zh_cn={"arcgis":{"utils":{"geometryServiceError":"提供一个几何服务来打开 Web 地图。","baseLayerError":"无法加载底图图层"}},"toolbars":{"draw":{"addShape":"单击以添加形状","finish":"双击完成操作","invalidType":"不支持的几何类型","resume":"单击以继续绘制","addPoint":"单击以添加点","freehand":"按下后开始并直到完成","complete":"双击完成操作","start":"单击以开始绘制","addMultipoint":"单击以开始添加点","convertAntiClockwisePolygon":"以逆时针方向绘制的面将反转为顺时针方向。"},"edit":{"invalidType":"无法激活工具。检查该工具对于指定的几何类型是否有效。","deleteLabel":"删除"}},"widgets":{"timeSlider":{"NLS_previous":"上一页","NLS_play":"播放/暂停","NLS_next":"下一页","NLS_invalidTimeExtent":"时间范围未指定或格式不正确。","NLS_first":"第一页"},"editor":{"tools":{"NLS_pointLbl":"点","NLS_reshapeLbl":"整形","NLS_arrowLeftLbl":"左箭头","NLS_triangleLbl":"三角形","NLS_autoCompleteLbl":"自动完成","NLS_arrowDownLbl":"下箭头","NLS_selectionRemoveLbl":"从选择内容中剪除","NLS_unionLbl":"联合","NLS_freehandPolylineLbl":"手绘折线","NLS_rectangleLbl":"矩形","NLS_ellipseLbl":"椭圆","NLS_attributesLbl":"属性","NLS_arrowUpLbl":"上箭头","NLS_arrowRightLbl":"右箭头","NLS_undoLbl":"撤消","NLS_arrowLbl":"箭头","NLS_cutLbl":"剪切","NLS_polylineLbl":"折线","NLS_selectionClearLbl":"清除选择内容","NLS_polygonLbl":"面","NLS_selectionUnionLbl":"联合","NLS_freehandPolygonLbl":"手绘面","NLS_deleteLbl":"删除","NLS_extentLbl":"范围","NLS_selectionNewLbl":"新建选择内容","NLS_circleLbl":"圆形","NLS_redoLbl":"恢复","NLS_selectionAddLbl":"添加到选择内容"}},"legend":{"NLS_creatingLegend":"创建图例","NLS_noLegend":"没有图例"},"measurement":{"NLS_length_kilometers":"千米","NLS_area_sq_miles":"平方英里","NLS_length_yards":"码","NLS_distance":"距离","NLS_area_acres":"英亩","NLS_resultLabel":"测量结果","NLS_length_miles":"英里","NLS_area_hectares":"公顷","NLS_area":"面积","NLS_area_sq_meters":"平方米","NLS_latitude":"纬度","NLS_area_sq_kilometers":"平方千米","NLS_area_sq_feet":"平方英尺","NLS_longitude":"经度","NLS_location":"位置","NLS_length_feet":"英尺","NLS_area_sq_yards":"平方码","NLS_length_meters":"米"},"overviewMap":{"NLS_invalidSR":"指定图层的空间参考与主地图不兼容","NLS_invalidType":"不支持的图层类型。有效类型为 'TiledMapServiceLayer' 和 'DynamicMapServiceLayer'","NLS_noMap":"输入参数中未找到 'map'","NLS_hide":"隐藏鹰眼图","NLS_drag":"拖动以更改地图范围","NLS_maximize":"最大化","NLS_noLayer":"主地图不包含底图图层","NLS_restore":"恢复","NLS_show":"显示鹰眼图"},"attributeInspector":{"NLS_title":"编辑属性","NLS_validationFlt":"值必须是浮点型。","NLS_noFeaturesSelected":"未选择任何要素","NLS_validationInt":"值必须是整型。","NLS_next":"下一页","NLS_errorInvalid":"无效","NLS_previous":"上一页","NLS_first":"第一页","NLS_deleteFeature":"删除","NLS_of":"属于","NLS_last":"最后一页"},"popup":{"NLS_nextFeature":"下一个要素","NLS_searching":"搜索","NLS_maximize":"最大化","NLS_noAttach":"未找到任何附件","NLS_noInfo":"无任何可用信息","NLS_restore":"恢复","NLS_prevFeature":"上一个要素","NLS_nextMedia":"下一个介质","NLS_close":"关闭","NLS_prevMedia":"上一个介质"},"attachmentEditor":{"NLS_add":"添加","NLS_none":"无","NLS_attachments":"附件:"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"必须提供 BingMapsKey。"},"vegeocode":{"bingMapsKeyNotSpecified":"必须提供 BingMapsKey。","requestQueued":"未检索服务器令牌。要在检索了服务器令牌后执行查询请求。"}},"layers":{"FeatureLayer":{"noGeometryField":"无法在图层 'fields' 信息中找到 'esriFieldTypeGeometry' 类型的字段。如果要使用地图服务图层，要素将不包含几何 [url: ${url}]","fieldNotFound":"无法在图层 'fields' 信息中找到“${field}'字段 [url: ${url}]","updateError":"更新图层时出错","noOIDField":"objectIdField 未设置 [url: ${url}]","invalidParams":"查询包含一个或多个不支持的参数"},"dynamic":{"imageError":"无法加载影像"},"tiled":{"tileError":"无法加载切片"},"imageParameters":{"deprecateBBox":"属性 'bbox' 已被弃用。请使用属性 'extent'。"},"agstiled":{"deprecateRoundrobin":"构造器选项 'roundrobin' 已被弃用。请使用选项 'tileServers'。"},"graphics":{"drawingError":"无法绘制图形 "}},"tasks":{"gp":{"gpDataTypeNotHandled":"GP 数据类型未处理。"},"query":{"invalid":"无法执行查询。请检查参数。"},"na":{"route":{"routeNameNotSpecified":"停靠点要素集中至少 1 个停靠点未指定 'RouteName'。"}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}};dojo.provide("dojo.cldr.nls.number");dojo.cldr.nls.number._built=true;dojo.provide("dojo.cldr.nls.number.zh_cn");dojo.cldr.nls.number.zh_cn={"decimalFormat":"#,##0.###","group":",","scientificFormat":"#E0","percentFormat":"#,##0%","currencyFormat":"¤#,##0.00","decimal":".","currencySpacing-afterCurrency-currencyMatch":"[:letter:]","infinity":"∞","list":";","percentSign":"%","minusSign":"-","currencySpacing-beforeCurrency-surroundingMatch":"[:digit:]","decimalFormat-short":"000T","currencySpacing-afterCurrency-insertBetween":" ","nan":"NaN","nativeZeroDigit":"0","plusSign":"+","currencySpacing-afterCurrency-surroundingMatch":"[:digit:]","currencySpacing-beforeCurrency-currencyMatch":"[:letter:]","perMille":"‰","patternDigit":"#","currencySpacing-beforeCurrency-insertBetween":" ","exponential":"E"};dojo.provide("dojo.cldr.nls.gregorian");dojo.cldr.nls.gregorian._built=true;dojo.provide("dojo.cldr.nls.gregorian.zh_cn");dojo.cldr.nls.gregorian.zh_cn={"months-format-narrow":["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],"field-weekday":"周天","dateFormatItem-yQQQ":"y年QQQ","dateFormatItem-yMEd":"y年M月d日，E","dateFormatItem-MMMEd":"MMMd日E","eraNarrow":["公元前","公元"],"dayPeriods-format-wide-earlyMorning":"清晨","dayPeriods-format-wide-morning":"上午","dateFormat-long":"y年M月d日","months-format-wide":["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],"dateTimeFormat-medium":"{1} {0}","dayPeriods-format-wide-pm":"下午","dateFormat-full":"y年M月d日EEEE","dateFormatItem-Md":"M-d","field-era":"时期","dateFormatItem-yM":"yyyy-M","months-standAlone-wide":["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],"timeFormat-short":"ah:mm","quarters-format-wide":["第1季度","第2季度","第3季度","第4季度"],"timeFormat-long":"zah时mm分ss秒","field-year":"年","dateFormatItem-yMMM":"y年MMM","dateFormatItem-yQ":"y年QQQ","dateFormatItem-yyyyMMMM":"y年MMMM","field-hour":"小时","dateFormatItem-MMdd":"MM-dd","months-format-abbr":["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],"dateFormatItem-yyQ":"yy年第Q季度","timeFormat-full":"zzzzah时mm分ss秒","field-day-relative+0":"今天","field-day-relative+1":"明天","field-day-relative+2":"后天","dateFormatItem-H":"H时","months-standAlone-abbr":["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],"quarters-format-abbr":["1季","2季","3季","4季"],"quarters-standAlone-wide":["第1季度","第2季度","第3季度","第4季度"],"dateFormatItem-M":"M月","days-standAlone-wide":["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],"dateFormatItem-yyMMM":"yy年MMM","timeFormat-medium":"ah:mm:ss","dateFormatItem-Hm":"H:mm","quarters-standAlone-abbr":["1季","2季","3季","4季"],"eraAbbr":["公元前","公元"],"field-minute":"分钟","field-dayperiod":"上午/下午","days-standAlone-abbr":["周日","周一","周二","周三","周四","周五","周六"],"dayPeriods-format-wide-night":"晚上","dateFormatItem-d":"d日","dateFormatItem-ms":"mm:ss","field-day-relative+-1":"昨天","dateFormatItem-h":"ah时","dateTimeFormat-long":"{1}{0}","field-day-relative+-2":"前天","dateFormatItem-MMMd":"MMMd日","dayPeriods-format-wide-midDay":"中午","dateFormatItem-MEd":"M-dE","dateTimeFormat-full":"{1}{0}","field-day":"日","days-format-wide":["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],"field-zone":"区域","dateFormatItem-y":"y年","months-standAlone-narrow":["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],"dateFormatItem-yyMM":"yy-MM","dateFormatItem-hm":"ah:mm","days-format-abbr":["周日","周一","周二","周三","周四","周五","周六"],"dateFormatItem-yMMMd":"y年MMMd日","eraNames":["公元前","公元"],"days-format-narrow":["日","一","二","三","四","五","六"],"field-month":"月","days-standAlone-narrow":["日","一","二","三","四","五","六"],"dateFormatItem-MMM":"LLL","dayPeriods-format-wide-am":"上午","dateFormatItem-MMMMdd":"MMMMdd日","dayPeriods-format-wide-weeHours":"凌晨","dateFormat-short":"yy-M-d","dayPeriods-format-wide-afternoon":"下午","field-second":"秒钟","dateFormatItem-yMMMEd":"y年MMMd日EEE","dateFormatItem-Ed":"d日E","field-week":"周","dateFormat-medium":"yyyy-M-d","dateFormatItem-yyyyM":"y年M月","dateTimeFormat-short":"{1} {0}","dateFormatItem-Hms":"H:mm:ss","dateFormatItem-hms":"ah:mm:ss","dateFormatItem-yyyy":"y年","quarters-standAlone-narrow":["1","2","3","4"],"dateTimeFormats-appendItem-Day-Of-Week":"{0} {1}","dateFormatItem-EEEd":"d EEE","dayPeriods-format-abbr-am":"AM","dateTimeFormats-appendItem-Second":"{0} ({2}: {1})","dateTimeFormats-appendItem-Era":"{0} {1}","dateTimeFormats-appendItem-Week":"{0} ({2}: {1})","quarters-format-narrow":["1","2","3","4"],"dayPeriods-format-narrow-am":"AM","dateTimeFormats-appendItem-Day":"{0} ({2}: {1})","dateTimeFormats-appendItem-Year":"{0} {1}","dateTimeFormats-appendItem-Hour":"{0} ({2}: {1})","dayPeriods-format-abbr-pm":"PM","dateTimeFormats-appendItem-Quarter":"{0} ({2}: {1})","dateTimeFormats-appendItem-Month":"{0} ({2}: {1})","dateTimeFormats-appendItem-Minute":"{0} ({2}: {1})","dateTimeFormats-appendItem-Timezone":"{0} {1}","dayPeriods-format-narrow-pm":"PM"};

}};});