window[esri._dojoScopeName||"dojo"]._xdResourceLoaded(function(dojo, dijit, dojox){
return {depends: [["provide", "esri.nls.zh.jsapi"]],
defineResource: function(dojo, dijit, dojox){dojo.provide("esri.nls.zh.jsapi");dojo._xdLoadFlattenedBundle("esri", "jsapi", "zh", {"arcgis":{"utils":{"geometryServiceError":"提供一个几何服务来打开 Web 地图。","baseLayerError":"无法加载底图图层"}},"toolbars":{"draw":{"addShape":"单击以添加形状","finish":"双击完成操作","invalidType":"不支持的几何类型","resume":"单击以继续绘制","addPoint":"单击以添加点","freehand":"按下后开始并直到完成","complete":"双击完成操作","start":"单击以开始绘制","addMultipoint":"单击以开始添加点","convertAntiClockwisePolygon":"以逆时针方向绘制的面将反转为顺时针方向。"},"edit":{"invalidType":"无法激活工具。检查该工具对于指定的几何类型是否有效。","deleteLabel":"删除"}},"widgets":{"timeSlider":{"NLS_previous":"上一页","NLS_play":"播放/暂停","NLS_next":"下一页","NLS_invalidTimeExtent":"时间范围未指定或格式不正确。","NLS_first":"第一页"},"editor":{"tools":{"NLS_pointLbl":"点","NLS_reshapeLbl":"整形","NLS_arrowLeftLbl":"左箭头","NLS_triangleLbl":"三角形","NLS_autoCompleteLbl":"自动完成","NLS_arrowDownLbl":"下箭头","NLS_selectionRemoveLbl":"从选择内容中剪除","NLS_unionLbl":"联合","NLS_freehandPolylineLbl":"手绘折线","NLS_rectangleLbl":"矩形","NLS_ellipseLbl":"椭圆","NLS_attributesLbl":"属性","NLS_arrowUpLbl":"上箭头","NLS_arrowRightLbl":"右箭头","NLS_undoLbl":"撤消","NLS_arrowLbl":"箭头","NLS_cutLbl":"剪切","NLS_polylineLbl":"折线","NLS_selectionClearLbl":"清除选择内容","NLS_polygonLbl":"面","NLS_selectionUnionLbl":"联合","NLS_freehandPolygonLbl":"手绘面","NLS_deleteLbl":"删除","NLS_extentLbl":"范围","NLS_selectionNewLbl":"新建选择内容","NLS_circleLbl":"圆形","NLS_redoLbl":"恢复","NLS_selectionAddLbl":"添加到选择内容"}},"legend":{"NLS_creatingLegend":"创建图例","NLS_noLegend":"没有图例"},"measurement":{"NLS_length_kilometers":"千米","NLS_area_sq_miles":"平方英里","NLS_length_yards":"码","NLS_distance":"距离","NLS_area_acres":"英亩","NLS_resultLabel":"测量结果","NLS_length_miles":"英里","NLS_area_hectares":"公顷","NLS_area":"面积","NLS_area_sq_meters":"平方米","NLS_latitude":"纬度","NLS_area_sq_kilometers":"平方千米","NLS_area_sq_feet":"平方英尺","NLS_longitude":"经度","NLS_location":"位置","NLS_length_feet":"英尺","NLS_area_sq_yards":"平方码","NLS_length_meters":"米"},"overviewMap":{"NLS_invalidSR":"指定图层的空间参考与主地图不兼容","NLS_invalidType":"不支持的图层类型。有效类型为 'TiledMapServiceLayer' 和 'DynamicMapServiceLayer'","NLS_noMap":"输入参数中未找到 'map'","NLS_hide":"隐藏鹰眼图","NLS_drag":"拖动以更改地图范围","NLS_maximize":"最大化","NLS_noLayer":"主地图不包含底图图层","NLS_restore":"恢复","NLS_show":"显示鹰眼图"},"attributeInspector":{"NLS_title":"编辑属性","NLS_validationFlt":"值必须是浮点型。","NLS_noFeaturesSelected":"未选择任何要素","NLS_validationInt":"值必须是整型。","NLS_next":"下一页","NLS_errorInvalid":"无效","NLS_previous":"上一页","NLS_first":"第一页","NLS_deleteFeature":"删除","NLS_of":"属于","NLS_last":"最后一页"},"popup":{"NLS_nextFeature":"下一个要素","NLS_searching":"搜索","NLS_maximize":"最大化","NLS_noAttach":"未找到任何附件","NLS_noInfo":"无任何可用信息","NLS_restore":"恢复","NLS_prevFeature":"上一个要素","NLS_nextMedia":"下一个介质","NLS_close":"关闭","NLS_prevMedia":"上一个介质"},"attachmentEditor":{"NLS_add":"添加","NLS_none":"无","NLS_attachments":"附件:"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"必须提供 BingMapsKey。"},"vegeocode":{"bingMapsKeyNotSpecified":"必须提供 BingMapsKey。","requestQueued":"未检索服务器令牌。要在检索了服务器令牌后执行查询请求。"}},"layers":{"FeatureLayer":{"noGeometryField":"无法在图层 'fields' 信息中找到 'esriFieldTypeGeometry' 类型的字段。如果要使用地图服务图层，要素将不包含几何 [url: ${url}]","fieldNotFound":"无法在图层 'fields' 信息中找到“${field}'字段 [url: ${url}]","updateError":"更新图层时出错","noOIDField":"objectIdField 未设置 [url: ${url}]","invalidParams":"查询包含一个或多个不支持的参数"},"dynamic":{"imageError":"无法加载影像"},"tiled":{"tileError":"无法加载切片"},"imageParameters":{"deprecateBBox":"属性 'bbox' 已被弃用。请使用属性 'extent'。"},"agstiled":{"deprecateRoundrobin":"构造器选项 'roundrobin' 已被弃用。请使用选项 'tileServers'。"},"graphics":{"drawingError":"无法绘制图形 "}},"tasks":{"gp":{"gpDataTypeNotHandled":"GP 数据类型未处理。"},"query":{"invalid":"无法执行查询。请检查参数。"},"na":{"route":{"routeNameNotSpecified":"停靠点要素集中至少 1 个停靠点未指定 'RouteName'。"}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}});
}};});