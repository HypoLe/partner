<?php
header("Content-type:application/x-javascript");
//echo "if (typeof dojo == \"undefined\") {";
readfile("js/dojo/dojo/dojo.xd.js");
//echo "}";
readfile("js/esri/esri.js");

echo "(function() {var dojo = window[esri._dojoScopeName];var dijit = window[esri._dijitScopeName];var dojox = window[esri._dojoxScopeName];";
readfile("js/esri/jsapi.js");
echo "}());";
?>