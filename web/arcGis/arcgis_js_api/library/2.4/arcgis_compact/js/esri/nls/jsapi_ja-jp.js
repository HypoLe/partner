dojo.provide("esri.nls.jsapi_ja-jp");dojo.provide("esri.nls.jsapi");esri.nls.jsapi._built=true;dojo.provide("esri.nls.jsapi.ja_jp");esri.nls.jsapi.ja_jp={"arcgis":{"utils":{"geometryServiceError":"ジオメトリ サービスを指定して Web マップを開きます。","baseLayerError":"ベース マップ レイヤを読み込めません"}},"toolbars":{"draw":{"addShape":"クリックして図形を追加します","finish":"ダブルクリックして終了します","invalidType":"サポートされていないジオメトリ タイプ","resume":"クリックして描画を続行します","addPoint":"クリックしてポイントを追加します","freehand":"ドラッグしてください","complete":"ダブルクリックして完了します","start":"クリックして描画を開始します","addMultipoint":"クリックしてポイントの追加を開始します","convertAntiClockwisePolygon":"反時計回りの方向で描画されたポリゴンは、時計回りに反転されます。"},"edit":{"invalidType":"ツールを有効にすることができません。指定したジオメトリ タイプに対してツールが有効であることを確認してください。","deleteLabel":"削除"}},"widgets":{"timeSlider":{"NLS_previous":"前へ","NLS_play":"再生/一時停止","NLS_next":"次へ","NLS_invalidTimeExtent":"TimeExtent が指定されていないか、形式が正しくありません。","NLS_first":"最初"},"editor":{"tools":{"NLS_pointLbl":"ポイント","NLS_reshapeLbl":"形状変更","NLS_arrowLeftLbl":"左矢印","NLS_triangleLbl":"三角形","NLS_autoCompleteLbl":"自動完成ポリゴン","NLS_arrowDownLbl":"下矢印","NLS_selectionRemoveLbl":"選択から削除","NLS_unionLbl":"ユニオン","NLS_freehandPolylineLbl":"フリーハンド ポリライン","NLS_rectangleLbl":"四角形","NLS_ellipseLbl":"楕円","NLS_attributesLbl":"属性","NLS_arrowUpLbl":"上矢印","NLS_arrowRightLbl":"右矢印","NLS_undoLbl":"元に戻す","NLS_arrowLbl":"矢印","NLS_cutLbl":"切断","NLS_polylineLbl":"ポリライン","NLS_selectionClearLbl":"選択の解除","NLS_polygonLbl":"ポリゴン","NLS_selectionUnionLbl":"ユニオン","NLS_freehandPolygonLbl":"フリーハンド ポリゴン","NLS_deleteLbl":"削除","NLS_extentLbl":"範囲","NLS_selectionNewLbl":"新規選択","NLS_circleLbl":"円","NLS_redoLbl":"やり直し","NLS_selectionAddLbl":"選択に追加"}},"legend":{"NLS_creatingLegend":"凡例を作成しています","NLS_noLegend":"凡例がありません"},"measurement":{"NLS_length_kilometers":"キロメートル","NLS_area_sq_miles":"平方マイル","NLS_length_yards":"ヤード","NLS_distance":"距離","NLS_area_acres":"エーカー","NLS_resultLabel":"計測精度","NLS_length_miles":"マイル","NLS_area_hectares":"ヘクタール","NLS_area":"面積","NLS_area_sq_meters":"平方メートル","NLS_latitude":"緯度","NLS_area_sq_kilometers":"平方キロメートル","NLS_area_sq_feet":"平方フィート","NLS_longitude":"経度","NLS_location":"位置","NLS_length_feet":"フィート","NLS_area_sq_yards":"平方ヤード","NLS_length_meters":"メートル"},"overviewMap":{"NLS_invalidSR":"指定したレイヤの空間参照はメイン マップの空間参照と一致していません","NLS_invalidType":"サポートされていないレイヤ タイプです。有効なタイプは 'TiledMapServiceLayer' および 'DynamicMapServiceLayer' です","NLS_noMap":"入力パラメータに 'map' が見つかりません","NLS_hide":"概観図の非表示","NLS_drag":"ドラッグしてマップ範囲を変更","NLS_maximize":"最大化","NLS_noLayer":"メイン マップにベース レイヤがありません","NLS_restore":"復元","NLS_show":"概観図の表示"},"attributeInspector":{"NLS_title":"属性編集","NLS_validationFlt":"値は浮動小数点型でなければなりません。","NLS_noFeaturesSelected":"フィーチャは選択されていません","NLS_validationInt":"値は整数でなければなりません。","NLS_next":"次へ","NLS_errorInvalid":"無効","NLS_previous":"前へ","NLS_first":"最初","NLS_deleteFeature":"削除","NLS_of":"/","NLS_last":"最後"},"popup":{"NLS_nextFeature":"次のフィーチャ","NLS_searching":"検索しています","NLS_maximize":"最大化","NLS_noAttach":"アタッチメントがありません","NLS_noInfo":"利用できる情報がありません","NLS_restore":"復元","NLS_prevFeature":"前のフィーチャ","NLS_nextMedia":"次のメディア","NLS_close":"閉じる","NLS_prevMedia":"前のメディア"},"attachmentEditor":{"NLS_add":"追加","NLS_none":"なし","NLS_attachments":"添付ファイル:"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"BingMapsKey を指定する必要があります。"},"vegeocode":{"bingMapsKeyNotSpecified":"BingMapsKey を指定する必要があります。","requestQueued":"サーバのトークンを取得できません。サーバのトークンが取得されるまでリクエストを待機させます。"}},"layers":{"FeatureLayer":{"noGeometryField":"レイヤの 'fields' 情報に 'esriFieldTypeGeometry' 型のフィールドが見つかりません。マップ サービス レイヤを使用しているのであれば、フィーチャにジオメトリがありません [url: ${url}]","fieldNotFound":"レイヤの 'fields' 情報に '${field}' フィールドが見つかりません [url: ${url}]","updateError":"レイヤの更新中にエラーが発生しました","noOIDField":"objectIdField が設定されていません [url: ${url}]","invalidParams":"クエリに 1 つ以上のサポートされていないパラメータが含まれています"},"dynamic":{"imageError":"イメージを読み込めません"},"tiled":{"tileError":"タイルを読み込めません"},"imageParameters":{"deprecateBBox":"プロパティ 'bbox' はサポートされていません。プロパティ 'extent' を使用してください。"},"agstiled":{"deprecateRoundrobin":"コンストラクタ オプション 'roundrobin' はサポートされていません。オプション 'tileServers' を使用してください。"},"graphics":{"drawingError":"グラフィックスを描画できません "}},"tasks":{"gp":{"gpDataTypeNotHandled":"GP データ タイプは処理されません。"},"query":{"invalid":"クエリを実行できません。パラメータを確認してください。"},"na":{"route":{"routeNameNotSpecified":"ストップ FeatureSet の中で、少なくとも 1 つのストップに対して 'RouteName' が指定されていません。"}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}};
