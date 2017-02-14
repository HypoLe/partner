dojo.provide("esri.nls.jsapi_fr");dojo.provide("esri.nls.jsapi");esri.nls.jsapi._built=true;dojo.provide("esri.nls.jsapi.fr");esri.nls.jsapi.fr={"arcgis":{"utils":{"geometryServiceError":"Fournissez un service de géométrie pour ouvrir une carte Web.","baseLayerError":"Chargement de la couche de fond de carte impossible"}},"toolbars":{"draw":{"addShape":"Cliquez pour ajouter une forme","finish":"Double-cliquez pour terminer","invalidType":"Type de géométrie non pris en charge","resume":"Cliquez pour continuer à dessiner","addPoint":"Cliquez pour ajouter un point","freehand":"Appuyez pour commencer et relâchez pour terminer","complete":"Double-cliquez pour exécuter","start":"Cliquez pour commencer à dessiner","addMultipoint":"Cliquez pour commencer à ajouter des points","convertAntiClockwisePolygon":"Les polygones dessinés dans le sens anti-horaire seront inversés pour respecter le sens horaire."},"edit":{"invalidType":"Impossible d'activer l'outil. Vérifiez que l'outil est valide pour le type de géométrie donné.","deleteLabel":"Supprimer"}},"widgets":{"timeSlider":{"NLS_previous":"Précédente","NLS_play":"Lecture/Pause","NLS_next":"Suivante","NLS_invalidTimeExtent":"TimeExtent non précisé ou le format est incorrect.","NLS_first":"Premier"},"editor":{"tools":{"NLS_pointLbl":"Point","NLS_reshapeLbl":"Remodeler","NLS_arrowLeftLbl":"Flèche gauche","NLS_triangleLbl":"Triangle","NLS_autoCompleteLbl":"Automatique","NLS_arrowDownLbl":"Flèche bas","NLS_selectionRemoveLbl":"Soustraire de la sélection","NLS_unionLbl":"Agréger","NLS_freehandPolylineLbl":"Polyligne à main levée","NLS_rectangleLbl":"Rectangle","NLS_ellipseLbl":"Ellipse","NLS_attributesLbl":"Attributs","NLS_arrowUpLbl":"Flèche haut","NLS_arrowRightLbl":"Flèche droite","NLS_undoLbl":"Annuler","NLS_arrowLbl":"Flèche","NLS_cutLbl":"Couper","NLS_polylineLbl":"Polyligne","NLS_selectionClearLbl":"Effacer la sélection","NLS_polygonLbl":"Polygone","NLS_selectionUnionLbl":"Agréger","NLS_freehandPolygonLbl":"Polygone à main levée","NLS_deleteLbl":"Supprimer","NLS_extentLbl":"Etendue","NLS_selectionNewLbl":"Nouvelle sélection","NLS_circleLbl":"Cercle","NLS_redoLbl":"Répéter","NLS_selectionAddLbl":"Ajouter à la sélection"}},"legend":{"NLS_creatingLegend":"Creation de la legende","NLS_noLegend":"Pas de legende"},"measurement":{"NLS_length_kilometers":"Kilomètres","NLS_area_sq_miles":"Miles carrés","NLS_length_yards":"Yards","NLS_distance":"Distance","NLS_area_acres":"Acres","NLS_resultLabel":"Résultat de la mesure","NLS_length_miles":"Miles","NLS_area_hectares":"Hectares","NLS_area":"Surface","NLS_area_sq_meters":"Mètres carrés","NLS_latitude":"Latitude","NLS_area_sq_kilometers":"Kilomètres carrés","NLS_area_sq_feet":"Pieds carrés","NLS_longitude":"Longitude","NLS_location":"Emplacement","NLS_length_feet":"Pieds","NLS_area_sq_yards":"Yards carrés","NLS_length_meters":"Mètres"},"overviewMap":{"NLS_invalidSR":"la référence spatiale de la couche donnée n'est pas compatible avec la carte principale","NLS_invalidType":"type de couche non pris en charge. Les types valides sont 'TiledMapServiceLayer' et 'DynamicMapServiceLayer'","NLS_noMap":"'map' introuvable dans les paramètres en entrée","NLS_hide":"Masquer la vue d'ensemble de la carte","NLS_drag":"Faites glisser le curseur pour modifier l'étendue de la carte","NLS_maximize":"Agrandir","NLS_noLayer":"carte principale sans couche de base","NLS_restore":"Restaurer","NLS_show":"Afficher la vue d'ensemble de la carte"},"attributeInspector":{"NLS_title":"Modifier des attributs","NLS_validationFlt":"La valeur doit être un nombre réel.","NLS_noFeaturesSelected":"Aucune entité sélectionnée","NLS_validationInt":"La valeur doit être un nombre entier.","NLS_next":"Suivante","NLS_errorInvalid":"Non valide","NLS_previous":"Précédente","NLS_first":"Premier","NLS_deleteFeature":"Supprimer","NLS_of":"de","NLS_last":"Dernier"},"popup":{"NLS_nextFeature":"Entité suivante","NLS_searching":"Recherche","NLS_maximize":"Agrandir","NLS_noAttach":"Aucune pièce jointe n'a été trouvée","NLS_noInfo":"Aucune information n'est disponible","NLS_restore":"Restaurer","NLS_prevFeature":"Entité précédente","NLS_nextMedia":"Support suivant","NLS_close":"Fermer","NLS_prevMedia":"Support précédent"},"attachmentEditor":{"NLS_add":"Ajouter","NLS_none":"Aucun","NLS_attachments":"Pièces jointes :"}},"geometry":{"deprecateToMapPoint":"esri.geometry.toMapPoint deprecated. Use esri.geometry.toMapGeometry.","deprecateToScreenPoint":"esri.geometry.toScreenPoint deprecated. Use esri.geometry.toScreenGeometry."},"io":{"proxyNotSet":"esri.config.defaults.io.proxyUrl is not set."},"virtualearth":{"vetiledlayer":{"bingMapsKeyNotSpecified":"BingMapsKey doit être indiqué."},"vegeocode":{"bingMapsKeyNotSpecified":"BingMapsKey doit être indiqué.","requestQueued":"Impossible de récupérer le jeton du serveur. Mise en file d'attente de la requête à exécuter une fois le jeton récupéré du serveur."}},"layers":{"FeatureLayer":{"noGeometryField":"impossible de trouver un champ de type 'esriFieldTypeGeometry' dans les informations 'fields' de la couche. Si vous utilisez une couche de service de carte, les entités n'auront pas de géométrie [url: ${url}]","fieldNotFound":"le champ '${field}' est introuvable dans les informations 'fields' de la couche [url: ${url}]","updateError":"une erreur est survenue lors de la mise à jour de la couche","noOIDField":"objectIdField n'est pas défini [url: ${url}]","invalidParams":"la requête contient un ou plusieurs paramètres non pris en charge"},"dynamic":{"imageError":"Chargement de l'image impossible"},"tiled":{"tileError":"Chargement de la tuile impossible"},"imageParameters":{"deprecateBBox":"Propriété 'bbox' déconseillée. Utilisez la propriété 'extent'."},"agstiled":{"deprecateRoundrobin":"Option de constructeur 'roundrobin' déconseillée. Utilisez l'option 'tileServers'."},"graphics":{"drawingError":"Affichage du graphique impossible "}},"tasks":{"gp":{"gpDataTypeNotHandled":"Type de données GP non géré."},"query":{"invalid":"Impossible d'exécuter la requête. Vérifiez vos paramètres."},"na":{"route":{"routeNameNotSpecified":"'RouteName' non spécifié pour au moins 1 arrêt dans le jeu d'entités des arrêts."}}},"map":{"deprecateShiftDblClickZoom":"Map.(enable/disable)ShiftDoubleClickZoom deprecated. Shift-Double-Click zoom behavior will not be supported.","deprecateReorderLayerString":"Map.reorderLayer(/*String*/ id, /*Number*/ index) deprecated. Use Map.reorderLayer(/*Layer*/ layer, /*Number*/ index)."}};
