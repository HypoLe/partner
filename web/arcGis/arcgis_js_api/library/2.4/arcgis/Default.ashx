<%@ WebHandler Language="C#" Class="jsapi" %>

using System;
using System.Web;
using System.IO;

public class jsapi : IHttpHandler {
  public void ProcessRequest (HttpContext context) {
  
    // GZIP if supported
    string AcceptEncoding = HttpContext.Current.Request.Headers["Accept-Encoding"];
    if (!string.IsNullOrEmpty(AcceptEncoding) && AcceptEncoding.Contains("gzip")) {
      context.Response.AppendHeader("Content-Encoding", "gzip");
      context.Response.Filter = new System.IO.Compression.GZipStream(context.Response.Filter, System.IO.Compression.CompressionMode.Compress);
    }

    context.Response.ContentType = "application/x-javascript";
    context.Response.Expires = 1800;

    context.Response.WriteFile(context.Server.MapPath("js\\dojo\\dojo\\dojo.xd.js"));
    context.Response.WriteFile(context.Server.MapPath("js\\esri\\esri.js"));

    context.Response.Write("(function() {var dojo = window[esri._dojoScopeName];var dijit = window[esri._dijitScopeName];var dojox = window[esri._dojoxScopeName];");
    context.Response.WriteFile(context.Server.MapPath("js\\esri\\jsapi.js"));
    context.Response.Write("}());");                
  }
 
  public bool IsReusable {
    get {
      return false;
    }
  }
}