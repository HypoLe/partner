// --- TreeGrid Header ---
Component = {

// --- File information ---
Name:      "Editable JavaScript TreeGrid",
Address:   "http://www.treegrid.com",
Type:      "TreeGrid with Extended API",
Version:   "5.9.11",
Release:   "16. July 2009 10:29",

// --- License information ---
Registered_to:  "TRIAL UNREGISTERED",

License:        "This component can be used for evaluation purposes only.",

Restrictions:   "This file must NOT be modified or decompressed",

Limitation:     "The trial information is shown and toolbar cannot be hidden except on localhost.",

// --- TreeGrid modules included in file ---
Modules:
   "Add, Ajax, Api, AutoSort, Bool, Button, Calc, CalcAgg, Cfg, CfgPersistent, "+
   "ColApi, ColHide, ColMove, Color, ColResize, ColSpan, ColWidth, Copy, "+
   "Corners, CPages, Date, DatePick, Debug, Defaults, Delete, Dialog, Drag, "+
   "Edit, Enum, ExpandAll, Export, Filter, Gantt, GenId, Group, HeaderMulti, "+
   "CharCodes, Check, Key, Lines, Link, LinkEdit, List, MaskEdit, Master, "+
   "MaxHeight, MenuCfg, Message, MouseObject, Move, Navigate, Number, Pager, "+
   "Paging, Panel, Paste, Print, Radio, Range, RelHeight, Reload, ReloadBody, "+
   "RelWidth, Resize, RightButton, Search, Select, ServerChanges, ShortFormat, "+
   "Show, SimpleExport, Sort, Space, Submit, Text, Toolbar, Tree, Undo, Upload, "+
   "UploadType, VarHeight, Xml"
}

var MS = new Object(),
ME = new Object(),
MX = new Object();
var _7A = "<table cellspacing='0' cellpadding='0' ";
var _7B = "><tfoot>",
_7C = "</tfoot></table>";
var _7D = "&#160;",
_7E = "='1'";
var _7M = navigator.appName.search("Opera") >= 0;
var _7G = _7M ? 0 : navigator.userAgent.search("Opera") >= 0;
var _7N = parseFloat(navigator.appVersion);
var _7O = navigator.userAgent.search("Safari") >= 0;
var _7P = _7O && navigator.userAgent.search("Windows") >= 0;
var _7H = navigator.userAgent.search("KHTML") >= 0;
var _7Q = _7H ? parseFloat(navigator.userAgent.slice(navigator.userAgent.search("KHTML") + 6)) : 0;
var _7F = navigator.appName.search("Netscape") >= 0 && !_7G && !_7H;
var _7I = document.compatMode == "CSS1Compat";
var _7R = navigator.appName.search("Microsoft") >= 0 && !_7G;
var _7S = _7R && !_7I;
var _7T = _7R && !_7S;
var _7U = _7S && navigator.appVersion.search("MSIE 5") >= 0;
var _7V = _7R && navigator.appVersion.search("MSIE 6") >= 0 || _7U;
var _7X = _7R && document.documentMode == 8;
var A = _7X && _7T;
if (_7V) try {
    document.execCommand("BackgroundImageCache", false, true);
} catch(B) {};
var _7W = navigator.userAgent.search("Netscape") >= 0;
var _7J = _7F && navigator.userAgent.search("Firefox/1.") >= 0;
var _7K = _7F && navigator.userAgent.search("Firefox/1.5") >= 0;
var _7L = _7F && navigator.userAgent.search("Firefox/3.") >= 0;
if (_7U) undefined = null;
if (_7H) {
    _7B = ">";
    _7C = "</table>";
}
var _7Y = 0,
_7Z = 0;
var _7a = _7U ? "cursor:hand;": "cursor:pointer;";
function _7b() {
    this.childNodes = {
        length: 0
    };
};
_7b.prototype.removeChild = function (D) {
    if (!D) return null;
    var F = D.parentNode;
    if (!F) return null;
    if (D == F.firstChild) F.firstChild = D.nextSibling;
    else D.previousSibling.nextSibling = D.nextSibling;
    if (D == F.lastChild) F.lastChild = D.previousSibling;
    else D.nextSibling.previousSibling = D.previousSibling;
    D.parentNode = null;
    D.previousSibling = null;
    D.nextSibling = null;
    this.childNodes.length--;
    return D;
};
_7b.prototype.appendChild = function (D) {
    if (!D) return null;
    if (D.parentNode) D.parentNode.removeChild(D);
    if (this.lastChild) {
        this.lastChild.nextSibling = D;
        D.previousSibling = this.lastChild;
    } else {
        this.firstChild = D;
        D.previousSibling = null;
    };
    this.lastChild = D;
    D.nextSibling = null;
    D.parentNode = this;
    this.childNodes.length++;
    return D;
};
_7b.prototype.insertBefore = function (D, H) {
    if (!D) return null;
    if (!H) return this.appendChild(D);
    if (H.parentNode != this) return null;
    if (D.parentNode) D.parentNode.removeChild(D);
    if (H.previousSibling) H.previousSibling.nextSibling = D;
    else this.firstChild = D;
    D.previousSibling = H.previousSibling;
    D.nextSibling = H;
    H.previousSibling = D;
    D.parentNode = this;
    this.childNodes.length++;
    return D;
};
_7b.prototype._7c = function (I) {
    for (var J = this.firstChild; J && I; J = J.nextSibling) I--;
    return J;
};
_7b.prototype._7d = function () {
    this.firstChild = null;
    this.lastChild = null;
    this.childNodes.length = 0;
};
function _7c(row, I) {
    return row._7c ? row._7c(I) : row.childNodes[I];
};
function _7d(row) {
    if (row._7d) row._7d();
    else row.innerHTML = "";
};
function _7e() {};
_7e.prototype = new _7b();
_7e.prototype.createElement = function (name) {
    var E = new _7b();
    E.tagName = name;
    E.nodeName = name;
    return E;
};
var _7f = _7R ? document: new _7e();
var _7g = {
    "Bool": "Bool",
    "Int": "Number",
    "Float": "Number",
    "Enum": "Text",
    "Date": "Number",
    "Text": "Text",
    "Pass": "Text",
    "Html": "Html",
    "Lines": "Lines",
    "Link": "Text",
    "Img": "Text",
    "Radio": "",
    "List": "Html",
    "Select": "Html",
    "Button": "ToolbarButton",
    "Abs": "Abs",
    "Gantt": "GanttInner"
};
var _7h = ["LeftVal", "MidVal", "RightVal"];
function _7i() {
    this.Visible = true;
};
function _7j() {};
function _7k() {
    this.Q = 0;
    this.U = 0;
    this.Tag = null;
    this.Move = 3;
};
function _7l() {};
function TDataIO() {
    var V = new Object;
    V.Param = new Object;
    this.Text = V;
    V.Method = "Get";
    V.Timeout = 60;
    V.W = "Fatal error !<br/>TreeGrid cannot load";
    V.ErrXMLFormat = "TreeGrid language file is missing or corrupted";
    V.X = "Cannot download data, timeout expired";
    V = new Object;
    V.Param = new Object;
    this.Defaults = V;
    V.Method = "Get";
    V.Timeout = 60;
    V = new Object;
    V.Param = new Object;
    this.Layout = V;
    V.Method = "Get";
    V.Timeout = 60;
    V = new Object;
    V.Param = new Object;
    this.Data = V;
    V.Method = "Get";
    V.Timeout = 60;
    V = new Object;
    V.Param = new Object;
    this.Page = V;
    V.Method = "Form";
    V.Xml = 0;
    V.Timeout = 60;
    V = new Object;
    V.Param = new Object;
    this.Upload = V;
    V.Method = "Form";
    V.Format = "DTD";
    V.Type = 1;
    V.Xml = 0;
    V.Timeout = 60;
    V = new Object;
    V.Param = new Object;
    this.Export = V;
    V.Method = "Form";
    V.Xml = 0;
    V.Type = 0;
    V.Timeout = 60;
    V = new Object;
    V.Param = new Object;
    this.Check = V;
    V.Method = "Get";
    V.Timeout = 60;
    V.Interval = 60;
    V = new Object;
    V.Param = new Object;
    this.Cell = V;
    V.Method = "Form";
    V.Xml = 0;
    V.Timeout = 60;
    this.Cache = 0;
    this.Debug = 1;
    if (location.hostname.search(/coq\.cz|coqsoft\.|treegrid\.|81\.2\.225\.174/i) >= 0) this.Cache = 1;
};
function _7m() {
    this.Index = -1;
    this.Z = "";
    this.id = "";
    this.Tag = "";
    this.b = [null, null, null];
    this.c = [null, null, null];
    this.d = [null, null, null];
    this.u = 1;
    this.v = 1;
    this.w = new Array();
    this.z = new Array();
    this.AA = new Array();
    this.AB = new Array();
    this.AD = 0;
    this.AE = 0;
    this.AF = 0;
    this.AG = 0;
    this.AH = 0;
    this.AI = 0;
    this.AJ = 0;
    this.AK = 0;
    this.AL = 0;
    this.Paging = 0;
    this.PageLength = 20;
    this.ChildPaging = 0;
    this.AN = 1;
    this.AP = 0;
    this.Panel = new _7i();
    this.Cols = new Object();
    this.RowCount = 0;
    this.SortCols = new Array();
    this.SortTypes = new Array();
    this.AX = 0;
    this.GroupCols = new Array();
    this.GroupTypes = new Array();
    this.AZ = 0;
    this.Ab = 0;
    this.MaxHeight = 0;
    this.Ag = 0;
    this.Ah = 0;
    this.Lang = new Object();
    this.Def = new Object();
    this.Ak = null;
    this.Toolbar = _7f.createElement("I");
    this.MenuCfg = new Object;
    this.Pager = new Object;
    this.ColNames = new Array();
    this.Par = new Object;
    this.Img = new Object;
    this.Styles = new Object();
    this.Al = new Object;
    for (var i = 0; i < 3; i++) this.ColNames[i] = new Array();
    this.Am = new Object();
    this.An = new Object();
    this.Ao = new Object();
    this.Ap = new Object();
    this.Aq = new Object();
    this.Actions = new Object;
    this.Mouse = new Object();
    this.Ar = new Object;
    for (var i = 65; i < 91; i++) this.Ar[i] = String.fromCharCode(i);
    for (var i = 48; i < 58; i++) this.Ar[i] = "D" + String.fromCharCode(i);
    this.Ax = 1;
    this.Ay = new Object();
};
if (!window.Grids || !Grids.OnDemand) var Grids = new Array();
Grids.Keys = new Array();
Grids.BD = [0, 2, 5, 10, 30, 60, 120, 300, 600];
Grids.CacheVersion = Math.floor(Math.random() * 1e8);
_7m.prototype.Debug = function () {};
_7m.prototype.BE = function () {};
if (!window.TCalc || !TCalc.OnDemand) var TCalc = function () {};
_7m.prototype.BF = function (BG, cells, BH) {
    if (typeof(BG) == "number") BG = BG + "";
    if (typeof(BG) != "string") return BG;
    BG = ' ' + BG + ' ';
    function BI(BJ) {
        var R = new RegExp("^(\\\"([^\\\"]|\\\\\\\")*[^\\\\\\\"]\\\"|\\'([^\\']|\\\\\\')*[^\\\\\\']\\'|[^\\'\\\"]|(\\'\\')|(\\\"\\\"))*[^\\w\\d\\\"\\'\\$\\_\\.]" + BJ + "[^\\w\\d\\\"\\'\\$\\_\\.]");
        var M = BG.match(R);
        while (M) {
            BG = BG.slice(0, M[0].length - 1 - BJ.length) + "Get(Calc.Row,\"" + BJ + "\")" + BG.slice(M[0].length - 1);
            M = BG.match(R);
        }
    };
    if (cells) for (var i = 0; i < cells.length; i++) BI(cells[i]);
    else for (var BJ in this.Cols) BI(BJ);
    if (this.DebugCalc && !BH && BG.search("return") < 0) BG = "Check((" + BG + "),\"" + BG.replace(/\"/g, "'") + "\")";
    if (BH || BG.search("return") >= 0) BG = "with(Calc){" + BG + "}";
    else BG = "with(Calc) return " + BG + ";";
    if (this.DebugCalc == 2) return new Function("Calc", BG);
    try {
        return new Function("Calc", "try {" + BG + "} catch(e){ return " + (this.DebugCalc ? "Calc.Check(NaN,\"" + BG.replace(/\"/g, "\\\"").replace(/[\r\n]/g, "\\n") + "\",e)": "NaN") + "; }");
    } catch(B) {
        if (this.DebugCalc) alert("Error in formula:\n" + BG + "\n\n" + (B.message ? B.message: B));
        return new Function(BH ? "": "return '" + _7o.NaN + "';");
    }
};
TCalc.prototype.BK = function () {
    var BL = this.Children;
    if (BL) return BL;
    BL = new Array();
    if (this.Row.Fixed) {
        for (var H = this.Grid.XB.firstChild; H; H = H.nextSibling) this.Grid.BM(H, BL);
    } else this.Grid.BM(this.Row, BL);
    return BL;
};
TCalc.prototype.sum = function (BJ) {
    if (!BJ) BJ = this.Col;
    var sum = 0,
    BL = this.BK(),
    BN = BL.length,
    i,
    BO = BJ + "sum";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    if (this.Row.Fixed) {
        for (i = 0; i < BN; i++) {
            if (BL[i].Page) sum += BL[i][BO] - 0;
            else sum += Get(BL[i], BJ) - 0;
        }
    } else for (i = 0; i < BN; i++) sum += Get(BL[i], BJ) - 0;
    return sum;
};
TCalc.prototype.sumrange = function (BJ) {
    if (!BJ) BJ = this.Col;
    var sum = "",
    BL = this.BK(),
    BN = BL.length,
    i,
    BO = BJ + "sum";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    if (this.Row.Fixed) {
        for (i = 0; i < BN; i++) {
            if (BL[i].Page) sum += BL[i][BO] + ";";
            else sum += Get(BL[i], BJ) + ";";
        }
    } else for (i = 0; i < BN; i++) sum += Get(BL[i], BJ) + ";";
    return this.Grid.BP(sum);
};
TCalc.prototype.count = function (BJ) {
    if (this.Row.Fixed && this.Grid.Paging) {
        var BQ = 0,
        BL = this.BK(),
        BN = BL.length;
        for (var i = 0; i < BN; i++) {
            if (BL[i].Page) BQ += BL[i].Count;
            else BQ++;
        }
        return BQ;
    }
    if (this.Row.State < 2) return this.Row.Count - 0;
    return this.BK().length;
};
TCalc.prototype.sumsq = function (BJ) {
    if (!BJ) BJ = this.Col;
    var sum = 0,
    BL = this.BK(),
    BN = BL.length,
    i,
    BO = BJ + "sumsq";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    for (i = 0; i < BN; i++) {
        if (this.Row.Fixed && BL[i].Page) sum += BL[i][BO] - 0;
        else {
            BR = Get(BL[i], BJ) - 0;
            sum += BR * BR;
        }
    }
    return sum;
};
TCalc.prototype.counta = function (BJ) {
    if (!BJ) BJ = this.Col;
    var BQ = 0,
    BL = this.BK(),
    BN = BL.length,
    i,
    BO = BJ + "counta";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    for (i = 0; i < BN; i++) {
        if (this.Row.Fixed && BL[i].Page) BQ += BL[i][BO] - 0;
        else if (Get(BL[i], BJ) != null) BQ++;
    }
    return BQ;
};
TCalc.prototype.countblank = function (BJ) {
    if (!BJ) BJ = this.Col;
    var BQ = 0,
    BL = this.BK(),
    BN = BL.length,
    i,
    BO = BJ + "countblank";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    for (i = 0; i < BN; i++) {
        if (this.Row.Fixed && BL[i].Page) BQ += BL[i][BO] - 0;
        else if (Get(BL[i], BJ) == null) BQ++;
    }
    return BQ;
};
TCalc.prototype.BS = function (BJ, BT, BU, BV) {
    try {
        if (!BT) {
            if (!BJ) return null;
            BT = BJ;
            BJ = this.Col;
        }
        if (!BU) BU = BJ;
        var BW = 0,
        BL = this.BK(),
        BN = BL.length,
        i,
        BO = BJ + "sumif" + BU,
        BX = BJ + "countif",
        row = this.Row;
        if (row.State < 2) {
            if (BV == 1) return row[BO] - 0;
            else if (BV == 2) return row[BX] - 0;
        }
        var BG = this.Grid.Am[BT];
        if (!BG) {
            var BY = BT.charAt(0);
            BG = this.Grid.BF((BY == '=' || BY == '>' || BY == '<' || BY == '!') && BT.indexOf("val") < 0 ? "val" + BT: BT);
            this.Grid.Am[BT] = BG;
        }
        for (i = 0; i < BN; i++) {
            if (row.Fixed && BL[i].Page) {
                if (BV == 1) BW += BL[i][BO] - 0;
                else if (BV == 2) BW += BL[i][BX] - 0;
            } else {
                var BZ = Get(BL[i], BJ);
                this["val"] = (BZ - 0) + "" == BZ ? BZ - 0 : BZ;
                this.Row = BL[i];
                if (BG(this)) {
                    if (BV == 1) BW += Get(BL[i], BU) - 0;
                    else if (BV == 2) BW++;
                }
            }
        }
        this.Row = row;
        return BW;
    } catch(B) {
        return null;
    }
};
TCalc.prototype.sumif = function (BJ, BT, BU) {
    return this.BS(BJ, BT, BU, 1);
};
TCalc.prototype.countif = function (BJ, BT) {
    return this.BS(BJ, BT, null, 2);
};
TCalc.prototype.product = function (BJ) {
    if (!BJ) BJ = this.Col;
    var Ba = 1,
    i, BO = BJ + "product";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    var BL = this.BK(),
    BN = BL.length;
    if (!BN) return 0;
    for (i = 0; i < BN; i++) {
        Ba *= this.Row.Fixed && BL[i].Page ? BL[i][BO] - 0 : Get(BL[i], BJ) - 0;
    }
    return Ba;
};
TCalc.prototype.max = function (BJ) {
    if (!BJ) BJ = this.Col;
    var max = -Infinity,
    BR, i, BO = BJ + "max";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    var BL = this.BK(),
    BN = BL.length;
    if (!BN) return "";
    for (i = 0; i < BN; i++) {
        BR = this.Row.Fixed && BL[i].Page ? BL[i][BO] : Get(BL[i], BJ);
        if (BR - 0 + "" == BR && BR > max) max = BR;
    }
    return isFinite(max) ? max: "";
};
TCalc.prototype.min = function (BJ) {
    if (!BJ) BJ = this.Col;
    var min = Infinity,
    BR, i, BO = BJ + "min";
    if (this.Row.State < 2) return this.Row[BO] - 0;
    var BL = this.BK(),
    BN = BL.length;
    if (!BN) return "";
    for (i = 0; i < BN; i++) {
        BR = this.Row.Fixed && BL[i].Page ? BL[i][BO] : Get(BL[i], BJ);
        if (BR - 0 + "" == BR && BR < min) min = BR;
    }
    return isFinite(min) ? min: "";
};
TCalc.prototype.maximum = function () {
    for (var i = 0, max = -Infinity; i < arguments.length; i++) {
        var BR = arguments[i];
        if (BR - 0 + "" == BR && BR > max) max = BR;
    }
    return isFinite(max) ? max: "";
};
TCalc.prototype.minimum = function () {
    for (var i = 0, min = Infinity; i < arguments.length; i++) {
        var BR = arguments[i];
        if (BR - 0 + "" == BR && BR < min) min = BR;
    }
    return isFinite(min) ? min: "";
};
TCalc.prototype.average = function (BJ) {
    var BQ = this.count(BJ);
    return BQ ? this.sum(BJ) / BQ: 0;
};
TCalc.prototype.rank = function (BJ, Bb) {
    var BL = this.BK();
    if (!BJ) BJ = this.Col;
    BL.sort(function (a, Bc) {
        return Get(a, BJ) < Get(Bc, BJ) ? -1 : 1;
    });
    for (var i = 0; i < BL.length; i++) if (Get(BL[i], BJ) >= Bb) return i;
    return BL.length;
};
TCalc.prototype.median = function (BJ) {
    var BL = this.BK();
    if (!BJ) BJ = this.Col;
    BL.sort(function (a, Bc) {
        return Get(a, BJ) < Get(Bc, BJ) ? -1 : 1;
    });
    var Bd = Math.floor(BL.length / 2);
    if (Bd == BL.length / 2) return (Get(BL[Bd], BJ) + Get(BL[Bd - 1], BJ)) / 2;
    else return Get(BL[Bd], BJ);
};
TCalc.prototype.mode = function (BJ) {
    var BL = this.BK(),
    H = new Object(),
    BR,
    i;
    if (!BJ) BJ = this.Col;
    for (i in BL) {
        BR = Get(BL[i], BJ);
        if (!H[BR]) H[BR] = 1;
        else H[BR]++;
    }
    var max = 0,
    BQ = 0;
    for (i in H) {
        if (H[i] > BQ) {
            max = i;
            BQ = H[i];
        }
    }
    return max;
};
TCalc.prototype.avedev = function (BJ) {
    var BL = this.BK(),
    Be = 0,
    i;
    if (!BJ) BJ = this.Col;
    for (i in BL) Be += Get(BL[i], BJ);
    Be /= BL.length;
    var sum = 0;
    for (i in BL) sum += Math.abs(Be - Get(BL[i], BJ));
    return sum / BL.length;
};
TCalc.prototype.Bf = function (BJ, Bg) {
    var BL = this.BK(),
    Bh = 0,
    Bi = 0,
    i,
    BR;
    if (!BJ) BJ = this.Col;
    for (i in BL) {
        BR = Get(BL[i], BJ);
        Bh += BR;
        Bi += BR * BR;
    }
    BR = (BL.length * Bi - Bh * Bh) / BL.length / (BL.length - (Bg == 1 || Bg == 3 ? 1 : 0));
    if (Bg == 0 || Bg == 1) return Math.sqrt(BR);
    else return BR;
};
TCalc.prototype.stdev = function (BJ) {
    return this.Bf(BJ, 0);
};
TCalc.prototype.stdevp = function (BJ) {
    return this.Bf(BJ, 1);
};
TCalc.prototype.vara = function (BJ) {
    return this.Bf(BJ, 2);
};
TCalc.prototype.varp = function (BJ) {
    return this.Bf(BJ, 3);
};
TCalc.prototype.Check = function (BR, BG, B) {
    var S = "*** DebugCalc alert ***\r\n\r\nGrid:\t" + this.Grid.id + "\r\nRow:\t" + this.Row.id + "\r\nCol:\t" + this.Col + "\r\n\r\nFormula: " + BG + "\r\n\r\n";
    if (B) alert(S + "Formula raised exception:\r\n" + (B.message ? B.message: B));
    else if (typeof(BR) == "number" && isNaN(BR)) alert(S + "Formula returned NaN");
    else if (BR == null) alert(S + "Formula returned null");
    return BR;
};
TCalc.prototype.countrows = function (type, Bj, row, BO) {
    var BQ = 0;
    if (!row) {
        BO = "countrows" + type;
        row = this.Row;
        if (Bj) {
            BO += Bj;
            Bj = this.Grid.Def[Bj];
        }
    }
    if (row.Fixed) {
        for (var F = this.Grid.XB.firstChild; F; F = F.nextSibling) {
            if (F.State == 0 || F.State == 1) {
                var BZ = F[BO] - 0;
                if (BZ) BQ += BZ;
            } else for (var J = F.firstChild; J; J = J.nextSibling) {
                if (!J.Deleted || type & 2) {
                    if ((!J.Filtered || type & 1) && (J.Selected || !(type & 8)) && (!Bj || J.Def == Bj)) BQ++;
                    if (type & 4) {
                        if (J.firstChild) BQ += this.countrows(type, Bj, J, BO);
                        else if (J.Count) {
                            var BZ = J[BO] - 0;
                            if (BZ) BQ += BZ;
                        }
                    }
                }
            }
        }
    } else {
        if (row.State < 2) {
            var BZ = row[BO] - 0;
            if (BZ) BQ += BZ;
        } else for (var J = row.firstChild; J; J = J.nextSibling) {
            if (!J.Deleted || type & 2) {
                if ((!J.Filtered || type & 1) && (J.Selected || !(type & 8)) && (!Bj || J.Def == Bj)) BQ++;
                if (type & 4) {
                    if (J.firstChild) BQ += this.countrows(type, Bj, J, BO);
                    else if (J.Count) {
                        var BZ = J[BO] - 0;
                        if (BZ) BQ += BZ;
                    }
                }
            }
        }
    };
    return BQ;
};
TCalc.prototype.choose = function (BR, Bk, Bl, Bm) {
    var Bn = 0,
    Bo = 0;
    if (!Bl) {
        Bn = 1;
        Bl = Get(this.Row, this.Col + "Defaults");
        if (Bl) Bl = Bl.split(Bl.charAt(0));
    } else if (typeof(Bl) == "string") Bl = Bl.split(',');
    if (!Bk) {
        Bo = 1;
        Bk = Get(this.Row, this.Col + "Defaults");
        if (Bk) Bk = Bk.split(Bk.charAt(0));
    } else if (typeof(Bk) == "string") Bk = Bk.split(',');
    if (BR == null) BR = Get(this.Row, this.Col);
    if (BR == null) BR = "";
    var BN = Bk ? Bk.length: (Bl ? Bl.length: 0);
    for (var i = Bo; i < BN; i++) if (Bk ? BR == Bk[i] : BR == i) return Bl ? Bl[i + Bn - Bo] : i;
    return Bm == null ? Get(this.Row, this.Col + "Custom") : Bm;
};
TCalc.prototype.split = function (BR, Bp) {
    if (BR == null) BR = Get(this.Row, this.Col);
    BR += "";
    if (Bp) return BR.split(Bp);
    BR = BR.split(BR.charAt(0));
    BR.shift();
    return BR;
};
_7m.prototype.Bq = function (row, Br) {
    var Bs = "CalcOrder" + Br,
    BL = Get(row, Bs);
    if (!BL) {
        if (row.Calculated) {
            BL = new Array();
            var Bg = 0;
            BL[Bg++] = "*";
            if (row.Space != null) {
                var Bt = row.Cells;
                if (Bt) for (var i = 0; i < Bt.length; i++) if (Get(row, Bt[i] + "Formula")) BL[Bg++] = Bt[i];
            } else for (var i in this.Cols) if (Get(row, i + "Formula")) BL[Bg++] = i;
            BL.sort();
            row[Bs] = BL;
        } else {
            BL = this.Bu;
            if (!BL) {
                BL = new Array();
                var Bg = 0;
                BL[Bg++] = "*";
                for (var i in this.Cols) if (this.Cols[i].Formula) BL[Bg++] = i;
                BL.sort();
                this.Bu = BL;
            }
        }
    } else if (typeof(BL) == "string") {
        if (BL.indexOf('*') < 0) BL = "*," + BL;
        BL = BL.split(",");
        if (row[Bs]) row[Bs] = BL;
        else row.Def[Bs] = BL;
    }
    return BL;
};
_7m.prototype.Bv = function () {
    var BL = new TCalc,
    R = this.GetFixedRows();
    for (var i = 0; i < R.length; i++) BL[R[i].id] = R[i];
    BL.Grid = this;
    return BL;
};
_7m.prototype.Bw = function (row, F, Br, Bx, By, Bz) {
    var B0 = row.Calculated;
    if (B0) {
        if (this.ChildPaging == 3 && !row.firstChild && row.State == null && row.Count) row.State = 0;
        var B1 = new Array();
        this.BM(row, B1);
        F.Children = B1;
    } else if (By || row.Kind != "Data") return;
    F.Row = row;
    F.Parent = row.parentNode;
    var C = this.Bq(row, Br),
    B2 = Grids.OnCalculateCell != null;;
    for (var j = 0; j < C.length; j++) {
        var B3 = C[j];
        if (B3.charAt(0) == '*') {
            if (Bz) continue;
            var Bh = B3.slice(1);
            if (Bh.length && Bh - 0 != Bh) {} else {
                for (var J = row.firstChild; J; J = J.nextSibling) this.Bw(J, F, Bh, Bx, By);
                F.Children = B1;
                F.Row = row;
                F.Parent = row.parentNode;
            }
        } else if (this.Cols[B3]) {
            F.Col = B3;
            var BG = B0 ? Get(row, B3 + "Formula") : this.Cols[B3].Formula;
            if (BG) {
                BG = this.B4(BG, row.Cells);
                var BZ = BG(F);
                if (B2) {
                    var B5 = Grids.OnCalculateCell(this, row, B3, BZ, Bx);
                    if (B5 != null) BZ = B5;
                }
                if (Bx) {
                    if (BZ != row[B3]) {
                        row[B3] = BZ;
                        this.RefreshCell(row, B3);
                    }
                } else row[B3] = BZ;
            }
        }
    }
    if (Bx && row.RelWidth) this.B6(row);
};
_7m.prototype.BM = function (row, B1) {
    if (row.State == 0 || row.State == 1) B1[B1.length] = row;
    else for (var J = row.firstChild; J; J = J.nextSibling) if (!J.Deleted && !J.Filtered) {
        if (Get(J, "AggChildren")) this.BM(J, B1);
        else B1[B1.length] = J;
    }
};
_7m.prototype.Calculate = function (Bx, By, B7, B8, B9) {
    var F = this.Bv(),
    T = this;
    F.CA = Bx;
    if (!this.Calculating || !this.Calculated || B8) {
        for (var J = this.XS.firstChild; J; J = J.nextSibling) {
            if (J.Kind == "Pager") {
                var Bg = this.GetFPage();
                if (Bg) Bg = this.GetPageNum(Bg);
                if (J.CB != Bg) {
                    var r1 = J.r1.getElementsByTagName("a");
                    if (J.CB != null && r1[J.CB]) {
                        r1[J.CB].className = this.Img.Style + "SimplePager";
                    }
                    if (Bg != null) {
                        var CC = r1[Bg];
                        CC.className = this.Img.Style + "SimplePagerActive";
                        var I = CC.offsetLeft - J.r1.scrollLeft;
                        if (I < 0) J.r1.scrollLeft += I;
                        else {
                            I += CC.offsetWidth - J.r1.offsetWidth;
                            if (I > 0) J.r1.scrollLeft += I;
                        }
                    }
                    J.CB = Bg;
                }
            }
            if (J.Calculated) {
                var C = this.Bq(J, "");
                for (var j = 0; j < C.length; j++) Calc(J, C[j]);
                if (Bx && J.RelWidth) this.B6(J);
            }
        }
        return;
    }
    if (B8) return;
    if (Grids.OnCalculate && Grids.OnCalculate(this, Bx)) return;
    var CD = By;
    if (!By) {
        By = true;
        for (var BY in this.Cols) if (this.Cols[BY].Formula) {
            By = false;
            break;
        }
    }
    var CE = this.GetFixedRows(),
    B1 = null,
    BN = CE.length,
    B2 = Grids.OnCalculateCell != null;
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Calculated) CE[CE.length] = J;
    for (var i = 0; i < CE.length; i++) {
        if (CE[i].Kind == 'Data' && CE[i].Calculated || CE[i].Space != null) {
            B1 = new Array();
            for (var H = this.XB.firstChild; H; H = H.nextSibling) this.BM(H, B1);
            F.Children = B1;
            break;
        }
    }
    function Calc(row, BJ) {
        F.Col = BJ;
        F.Row = row;
        var BG = row.Calculated ? Get(row, BJ + "Formula") : T.Cols[BJ].Formula;
        if (BG) {
            BG = T.B4(BG, row.Cells);
            var BZ = BG(F);
            if (B2) {
                var B5 = Grids.OnCalculateCell(T, row, BJ, BZ, Bx);
                if (B5 != null) BZ = B5;
            }
            if (Bx) {
                if (BZ != row[BJ]) {
                    row[BJ] = BZ;
                    T.RefreshCell(row, BJ);
                }
            } else row[BJ] = BZ;
        }
    };
    var C = this.CalcOrder;
    if (!C) {
        for (var i = 0, BQ = 0; i < BN; i++) {
            if (CE[i].Kind == 'Data') {
                C = Get(CE[i], "CalcOrder");
                BQ++;
            }
        }
        if (BQ != 1) C = null;
    }
    if (C) {
        for (var i = 0; i < BN; i++) if (CE[i].id) CE[CE[i].id] = CE[i];
        if (typeof(C) == "string") {
            if (C.indexOf('*') < 0) C = "*," + C;
            C = C.split(',');
            this.CalcOrder = C;
        }
        for (var j = 0; j < C.length; j++) {
            var B3 = C[j];
            if (B3.charAt(0) == '*') {
                if (B7) continue;
                var Bh = B3.slice(1);
                if (Bh.length && Bh - 0 != Bh) {} else {
                    for (var H = this.XB.firstChild; H; H = H.nextSibling) for (var J = H.firstChild; J; J = J.nextSibling) this.Bw(J, F, Bh, Bx, By);
                    F.Children = B1;
                }
            } else {
                var CF = B3.lastIndexOf('$');
                if (CF >= 0) {
                    var BJ = B3.slice(CF + 1);
                    var id = B3.slice(0, CF);
                    if (CE[id].Kind == 'Data') Calc(CE[id], BJ);
                } else {
                    for (var i = 0; i < BN; i++) if (CE[i].Kind == 'Data') Calc(CE[i], B3);
                }
            }
        }
    } else {
        if (!B7) for (var H = this.XB.firstChild; H; H = H.nextSibling) for (var J = H.firstChild; J; J = J.nextSibling) this.Bw(J, F, "", Bx, By);
        F.Children = B1;
        for (var i = 0; i < BN; i++) {
            if (CE[i].Kind == "Data") {
                C = this.Bq(CE[i], "");
                for (var j = 0; j < C.length; j++) if (C[j].indexOf('*') < 0 && this.Cols[C[j]]) Calc(CE[i], C[j]);
            }
        }
    };
    for (var J = this.XS.firstChild; J; J = J.nextSibling) {
        C = this.Bq(J, "");
        for (var j = 0; j < C.length; j++) if (C[j].indexOf('*') < 0 && J.Calculated) Calc(J, C[j]);
        if (Bx && J.RelWidth) this.B6(J);
    }
    F.Children = null;
    if (F.CG) this.Calculate(Bx, By, B7, B8);
    else {
        if (this.Gantt && !B7 && !B8 && !B9) this.CH(Bx, null, CD);
    };
};
_7m.prototype.Recalculate = function (row, BJ, Bx) {
    if (!this.Calculating || !this.Calculated) {
        this.Calculate(Bx, 1, 1, 1);
        return;
    }
    if (Grids.OnCalculate && Grids.OnCalculate(this, Bx, row, BJ)) return;
    var Calc = this.Bv();
    Calc.CA = Bx;
    if (row && !row.Page) {
        var CI = null;
        if (BJ) CI = Get(row, BJ + "Recalc");
        if (CI == null) CI = Get(row, "Recalc");
        do {
            if (Calc.CG) CI = Calc.CG;
            Calc.CG = 0;
            if (CI & 256) this.Calculate(Bx);
            else {
                if (CI & 16) {
                    this.Bw(row, Calc, "", Bx, 0, 1);
                    if (this.Gantt) this.CJ(row, null, Bx);
                } else if (CI & 8) for (var J = row.firstChild; J; J = J.nextSibling) {
                    this.Bw(J, Calc, "", Bx, 0, 1);
                    if (this.Gantt) this.CJ(J, null, Bx);
                }
                if (CI & 1 && !(CI & 16)) {
                    this.Bw(row, Calc, "", Bx, 0, 1);
                    if (this.Gantt) this.CJ(row, null, Bx);
                }
                if (CI & 6 && !row.Fixed) for (var J = row.parentNode; ! J.Page; J = J.parentNode) if (CI & 4 || J.Calculated) {
                    this.Bw(J, Calc, "", Bx, 0, 1);
                    if (this.Gantt) this.CJ(J, null, Bx);
                }
                this.Calculate(Bx, 0, 1);
            }
        } while (Calc.CG);
    }
};
_7m.prototype.ActionCalcOff = function () {
    if (!this.Calculating || !this.Calculated) return false;
    this.Calculated = false;
    this.SaveCfg();
    this.Calculate(1, 1, 1);
    return true;
};
_7m.prototype.ActionCalcOn = function () {
    if (!this.Calculating || this.Calculated) return false;
    if (this.Paging == 3 && !this.CK()) return false;
    this.ShowMessage(this.GetText("Calculate"));
    this.Calculated = true;
    var T = this;
    if (this.Paging == 3) {
        setTimeout(function () {
            T.CL = true;
            T.ReloadBody(function () {
                T.CL = false;
            },
            0, "ReCalc");
        },
        10);
    } else setTimeout(function () {
        T.Calculate(true);
        T.HideMessage();
    },
    10);
    this.SaveCfg();
    this.Calculate(1, 1, 1);
    return true;
};
_7m.prototype.LoadCfg = function (CM) {
    if (Grids.OnLoadCfg && Grids.OnLoadCfg(this)) return;
    if (this.SuppressCfg & 1) {
        this.Debug(4, "Loading configuration is suppressed, default values from XML are used");
        return;
    }
    if (CM == null) {
        if (this.SuppressCfg & 4) {
            this.Debug(4, "No Cookie attribute set, default values from XML are used");
            return;
        }
        if (this.PersistentCfg) {
            var CM = LoadCache("TreeGrid" + this.id);
            if (CM == false && this.PersistentCfg == 1) CM = Grids.CN(this.id);
            this.Debug(4, "Loading configuration from persistent storage");
        } else {
            var CM = Grids.CN(this.id);
            this.Debug(4, "Loading configuration from cookies");
        }
    } else {
        this.Debug(4, "Loading configuration from Cookie attribute");
    };
    if (!CM) {
        this.Debug(4, "No saved configuration is found, default values from XML are used");
        return;
    }
    CM += "";
    if (CM.charAt(0) == '+') {
        CM = CM.slice(1);
        this.BE(CM);
        var Cfg = Grids.CO,
        E;
        Cfg.Data = CM;
        var CP = Cfg.CQ();
        if (this.Version && CP != parseInt(this.Version)) return;
        var CR = Cfg.CS(),
        CT = Cfg.CS(),
        CU = Cfg.CS(),
        CV = Cfg.CS(),
        CW = Cfg.CS(),
        CX = Cfg.CS();
        if (!this["ShowDeletedLap"]) this.ShowDeleted = CR & 1 ? 1 : 0;
        if (!this["SortedLap"]) this.Sorted = CR & 2 ? 1 : 0;
        if (!this["AutoSortLap"]) this.AutoSort = CR & 4 ? 1 : 0;
        if (!this["CalculatedLap"]) this.Calculated = CR & 8 ? 1 : 0;
        if (!this["AutoUpdateLap"]) this.AutoUpdate = CR & 16 ? 1 : 0;
        if (!this["HidePanelLap"] && !this.Cols.Panel["VisibleLap"]) this.Cols.Panel.Visible = CR & 32 ? 0 : 1;
        if (!this["ShowDragLap"]) this.ShowDrag = CT & 1 ? 1 : 0;
        if (!this["ShowPagerLap"] && !this.Pager["VisibleLap"]) this.Pager.Visible = CT & 2 ? 1 : 0;
        if (!this["AllPagesLap"]) this.AllPages = CT & 4 ? 1 : 0;
        if (!this["HoverLap"]) this.Hover = (CT >> 3) & 3;
        if (CT & 32) {
            E = Cfg.CQ();
            if (E < 10) E = 10;
            if (!this["PagerWidthLap"]) this.Pager.Width = E;
        }
        if (!this["SortIconsLap"]) this.SortIcons = CU & 3;
        if (CU & 4) {
            E = Cfg.CQ();
            if (!this["CheckIntervalLap"]) this.Data.Check.Interval = E;
        }
        if (CU & 8) {
            if (!this["ResizingMainLap"] && this.MainTag) {
                var CY = Cfg.CQ(),
                CZ = Cfg.CQ();
                if (CY) this.MainTag.style.width = CY + "px";
                if (CZ) this.MainTag.style.height = CZ + "px";
            } else {
                Cfg.CQ();
                Cfg.CQ();
            }
        }
        if (CX & 8) {
            var Bh = Cfg.GetString();
            if (!this["StyleLap"]) this.Styles.Style = Bh;
        }
        var BQ = (CU >> 4) & 3;
        if (this["SortColsLap"] || this["SortLap"]) {
            for (var i = 0; i < BQ; i++) Cfg.GetString(Cfg.CQ() & 3);
        } else {
            this.AX = BQ;
            this.SortCols = new Array();
            this.SortTypes = new Array();
            for (var i = 0; i < this.AX; i++) {
                var Ca = Cfg.CQ();
                this.SortTypes[i] = Ca >> 2;
                this.SortCols[i] = Cfg.GetString(Ca & 3);
            }
            this.Cb();
        };
        if (this["GroupColsLap"] || this["GroupLap"]) {
            if (CV & 1) {
                var BQ = Cfg.CQ();
                for (var i = 0; i < BQ; i++) Cfg.GetString(Cfg.CQ() & 3);
            }
        } else {
            this.GroupCols = new Array();
            this.GroupTypes = new Array();
            this.AZ = 0;
            if (!this["GroupedLap"]) this.Grouped = CX & 1 ? 0 : 1;
            if (CV & 1) {
                this.AZ = Cfg.CQ();
                for (var i = 0; i < this.AZ; i++) {
                    var Ca = Cfg.CQ();
                    this.GroupTypes[i] = Ca >> 2;
                    this.GroupCols[i] = Cfg.GetString(Ca & 3);
                }
            }
        };
        var CE = this["FilterLap"] ? new Array() : this.Cc();
        var BQ = (CV >> 1) & 3;
        for (var i = 0; i < BQ; i++) {
            if (CE[i]) for (var BJ in this.Cols) {
                CE[i][BJ + "Filter"] = 0;
                CE[i][BJ] = "";
            }
            while (Cfg.Data && Cfg.Data.charAt(0) != '+') {
                var Cd = Cfg.CS(),
                BJ = Cfg.GetString(Cd & 3),
                BR = Cfg.GetString();
                if (this.Cols[BJ] && CE[i]) {
                    CE[i][BJ + "Filter"] = Cd >> 2;
                    CE[i][BJ] = (BR - 0) + "" == BR ? BR - 0 : BR;
                }
            }
            Cfg.Data = Cfg.Data.slice(1);
        }
        if (!this["FilteredLap"]) this.Filtered = CX & 2 ? 0 : 1;
        if (CW & 1) {
            var a = Cfg.CS(),
            BL = ["", "Filter", "Select", "Mark", "Find"];
            this.SearchAction = BL[a & 7];
            this.SearchMethod = a >> 3;
        }
        if (CW & 2) this.SearchExpression = Cfg.GetString();
        if (CW & 4) this.SearchType = Cfg.CQ();
        if (CW & 8) this.SearchDefs = Cfg.GetString();
        if (CW & 16) this.SearchCols = Cfg.GetString();
        if (!this["SearchedLap"]) this.Searched = CX & 4 ? 0 : 1;
        if (CV & 8) {
            var CY = 0,
            Ce = 0,
            Cf = this["ColsLap"];
            while (Cfg.Data && Cfg.Data.charAt(0) != '+') {
                var Ca = Cfg.CS(),
                BJ = Cfg.GetString(Ca & 3),
                I = Cfg.CQ();
                var Cg = Ca & 12;
                var Ch = Cg == 4 ? -Cfg.CQ() : (Cg == 8 ? Cfg.CQ() : 0);
                CY += Ch;
                var BY = this.Cols[BJ];
                if (BY) {
                    if (!BY["WidthLap"] && !Cf) {
                        if (Cg == 12) {
                            if (!BY.RelWidth) BY.Ci = 1;
                        } else {
                            if (BY.RelWidth) {
                                BY.Cj = BY.RelWidth;
                                BY.RelWidth = null;
                            }
                            BY.Ci = 0;
                            BY.Width = CY;
                        };
                    }
                    if (!this["ColsPosLap"] && !Cf) {
                        if (Ca & 32) {
                            BY.K = 1;
                            BY.Pos = I;
                        } else {
                            BY.K = I & 1 ? 0 : 2;
                            BY.Pos = I >> 1;
                        }
                    }
                    if (!BY["VisibleLap"] && !Cf) BY.Visible = Ca & 16 ? 1 : 0;
                }
            }
            Cfg.Data = Cfg.Data.slice(1);
        }
        if (CV & 16) {
            var Ck = Cfg.Data.indexOf("+");
            if (this.SaveExpanded) this.Cl = "&" + Cfg.Data.slice(0, Ck) + "&";
            Cfg.Data = Cfg.Data.slice(Ck + 1);
            var Ck = Cfg.Data.indexOf("+");
            if (this.SaveExpanded) this.Cm = "&" + Cfg.Data.slice(0, Ck) + "&";
            Cfg.Data = Cfg.Data.slice(Ck + 1);
        }
        if (CV & 32) {
            var Ck = Cfg.Data.indexOf("+");
            if (this.SaveSelected) this.Cn = "&" + Cfg.Data.slice(0, Ck) + "&";
            Cfg.Data = Cfg.Data.slice(Ck + 1);
        }
        if (CW & 32) {
            var BL = Cfg.GetString();
            if (BL) {
                BL = BL.split('@');
                for (var i = 0; i < BL.length; i += 2) {
                    var BR = Cfg.GetString();
                    if ((BR - 0) + "" == BR) BR -= 0;
                    if (BL[i]) {
                        var J = this.GetRowById(BL[i]);
                        if (J) J[BL[i + 1]] = BR;
                    } else this[BL[i + 1]] = BR;
                }
            }
        }
        if (this.SaveValues && Cfg.Data) {
            var BL = Cfg.Data.split("+");
            this.Co = new Object();
            for (var BY in BL) {
                if (BL[BY]) {
                    var x = BL[BY].split("]");
                    this.Co[x[0]] = x[1];
                }
            }
        }
    } else {};
    if (!this["ColsLap"] && !this["ColsPosLap"]) {
        var C = this.Cols;
        for (var i = 0; i < 3; i++) {
            var Cq = new Array();
            for (var BY in C) if (C[BY].K == i) Cq[Cq.length] = BY;
            this.ColNames[i] = Cq;
        }
        function Ct(a, Bc) {
            return C[a].Pos < C[Bc].Pos ? -1 : C[a].Pos > C[Bc].Pos ? 1 : 0;
        };
        for (var i = 0; i < 3; i++) {
            var Cq = this.ColNames[i];
            Cq.sort(Ct);
            var I = 0;
            for (var j = 0; j < Cq.length; j++) this.Cols[Cq[j]].Pos = I++;
        }
    }
    if (Grids.OnCfgLoaded) Grids.OnCfgLoaded(this);
};
_7m.prototype.SaveCfg = function (Cu) {
    if (Grids.OnSaveCfg && Grids.OnSaveCfg(this)) return;
    if (this.SuppressCfg & 2) return;
    if (!Cu && (this.SuppressCfg & 4)) return;
    var Cfg = Grids.CO,
    C = this.Cols;
    Cfg.Data = "+";
    var CE = this["FilterLap"] ? new Array() : this.Cc();
    var CY = 0,
    CZ = 0;
    if (this.ResizingMain & 2) {
        var Cv = this.MainTag.style.width;
        CY = parseInt(Cv);
        if (CY + "px" != Cv) CY = 0;
    }
    if (this.ResizingMain & 1) {
        var Cw = this.MainTag.style.height;
        CZ = parseInt(Cw);
        if (CZ + "px" != Cw) CZ = 0;
    }
    Cfg.Cx(this.Version);
    Cfg.Cy((this.ShowDeleted ? 1 : 0) + (this.Sorted ? 2 : 0) + (this.AutoSort ? 4 : 0) + (this.Calculated ? 8 : 0) + (this.AutoUpdate ? 16 : 0) + (this.Cols.Panel && this.Cols.Panel.Visible ? 0 : 32));
    Cfg.Cy((this.ShowDrag ? 1 : 0) + (this.Pager.Visible ? 2 : 0) + (this.AllPages ? 4 : 0) + (this.Hover * 8) + (this.Paging ? 32 : 0));
    Cfg.Cy((this.SortIcons & 3) + (this.Data.Check.Interval != 60 ? 4 : 0) + (CY || CZ ? 8 : 0) + ((this.AX < 3 ? this.AX: 3) * 16));
    Cfg.Cy((this.AZ ? 1 : 0) + ((CE.length & 3) * 2) + (this["ColsLap"] ? 0 : 8) + (this.SaveExpanded ? 16 : 0) + (this.SaveSelected ? 32 : 0));
    Cfg.Cy((this.SearchAction != null || this.SearchMethod != null ? 1 : 0) + (this.SearchExpression != null ? 2 : 0) + (this.SearchType != null ? 4 : 0) + (this.SearchDefs != null ? 8 : 0) + (this.SearchCols != null ? 16 : 0) + (this.SaveAttrs != null ? 32 : 0));
    Cfg.Cy((this.Grouped ? 0 : 1) + (this.Filtered ? 0 : 2) + (this.Searched ? 0 : 4) + (this["StyleLap"] ? 0 : 8));
    if (this.Paging) Cfg.Cx(this.Pager.Width);
    if (this.Data.Check.Interval != 60) Cfg.Cx(this.Data.Check.Interval);
    if (CY || CZ) {
        Cfg.Cx(CY);
        Cfg.Cx(CZ);
    }
    if (!this["StyleLap"]) Cfg.SetString(this.Styles.Style);
    for (var i = 0; i < this.AX && i < 3; i++) {
        var BN = this.SortCols[i].length;
        if (BN > 3) BN = 0;
        Cfg.Cx(BN + (this.SortTypes[i] ? this.SortTypes[i] * 4 : 0));
        Cfg.SetString(this.SortCols[i], BN);
    }
    if (this.AZ) {
        Cfg.Cx(this.AZ);
        for (var i = 0; i < this.AZ; i++) {
            var BN = this.GroupCols[i].length,
            Cz = this.GroupTypes[i] * 4;
            if (BN > 3) BN = 0;
            Cfg.Cx(BN + (Cz ? Cz: 0));
            Cfg.SetString(this.GroupCols[i], BN);
        }
    }
    for (var i = 0; i < CE.length; i++) {
        for (var BY in this.Cols) {
            var BG = CE[i][BY + "Filter"];
            if (BG || this["SaveAllFilterValues"]) {
                var BN = BY.length;
                if (BN > 3) BN = 0;
                Cfg.Cy(BN + BG * 4);
                Cfg.SetString(BY, BN);
                Cfg.SetString(CE[i][BY], null, this.SaveAttrsTrim);
            }
        }
        Cfg.Data += "+";
    }
    if (this.SearchAction != null || this.SearchMethod != null) {
        var BL = ["", "Filter", "Select", "Mark", "Find"];
        for (var i = 0; i < BL.length; i++) if (this.SearchAction == BL[i]) break;
        if (i == BL.length) i = 0;
        var C0 = this.SearchMethod;
        if (!C0) C0 = 0;
        Cfg.Cy(i + C0 * 8);
    }
    if (this.SearchExpression != null) Cfg.SetString(this.SearchExpression, null, 0);
    if (this.SearchType != null) Cfg.Cx(this.SearchType);
    if (this.SearchDefs != null) Cfg.SetString(this.SearchDefs, null, 0);
    if (this.SearchCols != null) Cfg.SetString(this.SearchCols, null, 0);
    if (!this["ColsLap"]) {
        var CY = 0;
        for (var BJ in this.Cols) {
            if (BJ == "Panel") continue;
            var BY = C[BJ],
            C1 = BY.Width - CY;
            if (!BY.Ci && !BY.RelWidth) CY = BY.Width;
            var BN = BJ.length;
            if (BN > 3) BN = 0;
            Cfg.Cy(BN + (BY.Ci || BY.RelWidth ? 12 : (C1 < 0 ? 4 : (C1 > 0 ? 8 : 0))) + (BY.Visible ? 16 : 0) + (BY.K == 1 ? 32 : 0));
            Cfg.SetString(BJ, BN);
            Cfg.Cx(BY.K == 1 ? BY.Pos: BY.Pos * 2 + (BY.K == 0 ? 1 : 0));
            if (C1 && !BY.Ci && !BY.RelWidth) Cfg.Cx(C1 > 0 ? C1: -C1);
        }
        Cfg.Data += "+";
    }
    if (this.SaveExpanded) {
        var B = "",
        BY = "";
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
            if (Is(J, "CanExpand") && J.firstChild && J.State != 0 && (J.Expanded != J.Def.Expanded || this.SaveExpanded == 1) && J.id) {
                if (J.Expanded) B += (B ? "&": "") + J.id;
                else BY += (BY ? "&": "") + J.id;
            }
        }
        Cfg.Data += B + "+";
        Cfg.Data += BY + "+";
    }
    if (this.SaveSelected) {
        var Bh = "";
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
            if (Is(J, "CanSelect") && J.Selected && J.id) Bh += (Bh ? "&": "") + J.id;
        }
        Cfg.Data += Bh + "+";
    }
    if (this.SaveAttrs) {
        Cfg.SetString(this.SaveAttrs.replace(/\,/g, '@'), null, 0);
        var BL = this.SaveAttrs.split(",");
        for (var i = 0; i < BL.length; i += 2) {
            var BR = null;
            if (BL[i]) {
                var J = this.GetRowById(BL[i]);
                if (J) BR = J[BL[i + 1]];
            } else BR = this[BL[i + 1]];
            Cfg.SetString(BR == null ? "": (typeof(BR) == "boolean" ? BR - 0 + "": BR + ""), null, this.SaveAttrsTrim);
        }
    }
    if (this.SaveValues) {
        var Bh, CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) {
            if (CE[i].id) {
                Bh = this.C2(CE[i]);
                if (Bh) Cfg.Data += CE[i].id + "]" + Bh + "+";
            }
        }
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
            if (J.id) {
                Bh = this.C2(J);
                if (Bh) Cfg.Data += J.id + "]" + Bh + "+";
            }
        }
    }
    if (!window["DeLZ"]) this.Enable = this.Disable;
    if (Cu) return Cfg.Data;
    if (this.PersistentCfg) {
        if (SaveCache("TreeGrid" + this.id, Cfg.Data) || this.PersistentCfg == 2) return;
    }
    Grids.C3(this.id, Cfg.Data);
};
_7m.prototype.C4 = function (row, C5) {
    var BL = this.C6;
    if (!BL) {
        var H = this.SaveAttrs;
        if (!H) return;
        H = H.split(",");
        BL = new Object();
        for (var i = 0; i < H.length; i += 2) BL[H[i] + "_" + H[i + 1]] = 1;
        this.C6 = BL;
    }
    if (BL[row.id + "_" + C5]) this.SaveCfg();
};
Grids.CN = function (id) {
    if (Grids.Cfg == null) {
        var BY = document.cookie;
        var C7 = BY.search(/Grids\s?\=/);
        if (C7 == -1) return "";
        BY = BY.slice(C7);
        var Bh = BY.search("=");
        if (Bh == -1) return "";
        var B = BY.search(";");
        if (B < 0) BY = BY.slice(Bh + 1);
        else BY = BY.slice(Bh + 1, B);
        Grids.Cfg = BY.split("|");
        Grids.A4 = new Array(Grids.Cfg.length);
        for (var i = 0; i < Grids.Cfg.length; i++) {
            var Bh = Grids.Cfg[i];
            var I = Bh.search("#");
            if (I < 0) continue;
            Grids.Cfg[i] = Bh.slice(I + 1);
            Grids.A4[i] = Bh.slice(0, I);
        }
    }
    for (var i = 0; i < Grids.A4.length; i++) if (id == Grids.A4[i]) return Grids.Cfg[i];
    return "";
};
Grids.C3 = function (id, CM) {
    if (Grids.Cfg == null) {
        Grids.Cfg = new Array();
        Grids.A4 = new Array();
    }
    var BN = Grids.A4.length;
    for (var i = 0; i < BN; i++) if (id == Grids.A4[i]) {
        Grids.Cfg[i] = CM;
        break;
    }
    if (i == Grids.A4.length) {
        Grids.A4[i] = id;
        Grids.Cfg[i] = CM;
        BN++;
    }
    var BL = new Array(BN),
    Bg = 0,
    exp = ";";
    for (var i = 0; i < BN; i++) {
        BL[Bg++] = Grids.A4[i] + "#" + Grids.Cfg[i];
    }
    if (Grids.CookieExpires) {
        if (Grids.CookieExpires > 1) exp = "; expires=" + (new Date((new Date).getTime() + Grids.CookieExpires * 1000)).toUTCString();
        else if (Grids.CookieExpires.toUTCString) exp = "; expires=" + Grids.CookieExpires.toUTCString();
    } else {
        exp = new Date();
        exp.setFullYear(exp.getFullYear() + 1);
        exp = "; expires=" + exp.toUTCString();
    };
    document.cookie = "Grids=" + (BL.join("|")) + exp + (Grids.CookieParam ? Grids.CookieParam: "");
};
function _7p() {
    this.C8 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234(";
    this.C9 = "abcdefghijklmnopqrstuvwxyz56789)";
    this.DA = new Object();
    this.DB = new Object();
    for (var i = 0; i < 32; i++) {
        this.DA[this.C8.charAt(i)] = i;
        this.DB[this.C9.charAt(i)] = i;
    }
    this.Data = "";
};
_7p.prototype.Cy = function (Bb) {
    if (Bb > 31) this.Data += this.C9.charAt(Bb - 32);
    else this.Data += this.C8.charAt(Bb);
};
_7p.prototype.CS = function () {
    var B3 = this.Data.charAt(0);
    this.Data = this.Data.slice(1);
    var x = this.DA[B3];
    return x == null ? this.DB[B3] + 32 : x;
};
_7p.prototype.Cx = function (Bb) {
    if (Bb < 0) {
        Bb = -Bb;
        this.Data += "-";
    }
    while (1) {
        var B3 = Bb & 31;
        Bb >>= 5;
        if (!Bb) {
            this.Data += this.C9.charAt(B3);
            return;
        }
        this.Data += this.C8.charAt(B3);
    }
};
_7p.prototype.CQ = function () {
    var i = 0,
    B3 = this.Data.charAt(0),
    Bb = 0,
    DC;
    if (B3 == '[' || B3 == ']') return this.GetString();
    while (1) {
        B3 = this.Data.charAt(i);
        var x = this.DA[B3];
        if (x == null) {
            x = this.DB[B3];
            if (x != null) {
                Bb += x << (i * 5);
                this.Data = this.Data.slice(i + 1);
                return DC ? -(Bb >> 5) : Bb;
            }
            if (B3 == '-') DC = true;
            else return null;
        }
        Bb += x << (i * 5);
        i++;
    }
};
_7p.prototype.SetString = function (DD, BN, DE) {
    if (BN) {
        this.Data += escape(DD);
    } else if (!DD && DD !== 0) this.Data += "]";
    else {
        if (DE == null) DE = 50;
        if (DE != 0 && DD.length > DE) DD = DD.slice(0, DE);
        this.Data += escape(DD) + ']';
    }
};
_7p.prototype.GetString = function (BN) {
    if (BN) {
        var DD = unescape(this.Data.slice(0, BN));
        this.Data = this.Data.slice(BN);
        return DD;
    }
    var B3 = this.Data.charAt(0);
    if (B3 == ']') {
        this.Data = this.Data.slice(1);
        return "";
    }
    var i = this.Data.indexOf(']');
    if (i < 0) return null;
    var DD = unescape(this.Data.slice(0, i));
    this.Data = this.Data.slice(i + 1);
    return DD;
};
Grids.CO = new _7p();
function _7q() {
    if (window._7r) return _7r;
    var C = document.createElement("cacher");
    if (!C.addBehavior) return null;
    C.addBehavior("#default#userdata");
    document.body.appendChild(C);
    _7r = C;
    return C;
};
function SaveCache(id, BR) {
    if (window.globalStorage) {
        var DF = location.host;
        if (DF == "localhost") DF += ".localdomain";
        try {
            globalStorage[DF][id] = BR;
            return true;
        } catch(B) {
            return false;
        }
    }
    var C = _7q();
    if (!C) return false;
    C.setAttribute("cache", BR);
    C.save(id);
    return true;
};
function LoadCache(id) {
    if (window.globalStorage) {
        var DF = location.host;
        if (DF == "localhost") DF += ".localdomain";
        try {
            return globalStorage[DF][id];
        } catch(B) {
            return false;
        }
    }
    var C = _7q();
    if (!C) return false;
    C.load(id);
    var Cu = C.getAttribute("cache");
    return Cu;
};
_7m.prototype.GetCfgRequest = function (DG, DH) {
    var BL = new Array(),
    Bg = 0,
    DI = !DG || DG.toLowerCase() == "dtd";
    BL[Bg++] = "<Cfg";
    if (this.Sorting && this.Sorted) {
        var DJ = "",
        DK = "";
        for (var i = 0; i < this.AX; i++) {
            if (DJ) DJ += ",";
            DJ += this.SortCols[i];
            if (DK) DK += ",";
            if (this.SortTypes[i] != null) DK += this.SortTypes[i];
        }
        if (DJ) BL[Bg++] = " SortCols='" + DJ + "'";
        if (DK) BL[Bg++] = " SortTypes='" + DK + "'";
        if (this.DL) BL[Bg++] = " ReSort='1'";
    }
    if (this.CL) BL[Bg++] = " ReCalc='1'";
    var DJ = "",
    DK = "";
    if (this.AZ && this.Grouped && this.Grouping) {
        BL[Bg++] = " GroupCols='" + this.GroupCols.join(",") + "'";
        BL[Bg++] = " GroupTypes='" + this.GroupTypes.join(",") + "'";
    }
    if (this.SearchAction && this.Searching && this.Searched) {
        BL[Bg++] = " SearchAction='" + this.SearchAction + "'";
        BL[Bg++] = " SearchExpression=\"" + this.DM(this.SearchExpression) + "\"";
        BL[Bg++] = " SearchType='" + this.SearchType + "'";
        BL[Bg++] = " SearchMethod='" + (this.SearchMethod == null ? "": this.SearchMethod) + "'";
        BL[Bg++] = " SearchDefs='" + (this.SearchDefs == null ? "": this.SearchDefs) + "'";
        BL[Bg++] = " SearchCols='" + (this.SearchCols == null ? "": this.SearchCols) + "'";
    }
    BL[Bg++] = " TimeZone='" + (new Date()).getTimezoneOffset() + "'";
    if (this.Focused != null) {
        BL[Bg++] = " Focused='" + this.Focused + "'";
        if (this.FocusedPos != null) BL[Bg++] = " FocusedPos='" + this.FocusedPos + "'";
        if (this.FocusedCol != null) BL[Bg++] = " FocusedCol='" + this.FocusedCol + "'";
    }
    if (DH) BL[Bg++] = DH;
    BL[Bg++] = "/>";
    if (this.Filtering && this.Filtered) {
        BL[Bg++] = "<Filters>";
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) {
            var DN, Bh = "",
            J = CE[i];
            if (J.Kind != "Filter") continue;
            var Bj = J.Def;
            if (typeof(J.Def) == "string") J.Def = this.Def[J.Def];
            if (!J.Def) J.Def = this.Def["Fixed"];
            BL[Bg++] = "<I";
            if (CE[i].id) BL[Bg++] = " id='" + CE[i].id + "'";
            if (DI) BL[Bg++] = ">";
            for (var BJ in this.Cols) {
                var Cs = J[BJ + "Filter"];
                if (Cs) {
                    var BR = J[BJ];
                    BR = this.DO(J, BJ, BR);
                    if (BR == null) BR = "";
                    BR = this.DM(BR);
                    if (DI) BL[Bg++] = "<U N='" + BJ + "' V=\"" + BR + "\" Filter='" + Cs + "'/>";
                    else BL[Bg++] = " " + BJ + "=\"" + BR + "\" " + BJ + "Filter='" + Cs + "'";
                }
            }
            BL[Bg++] = DI ? "</I>": "/>";
            J.Def = Bj;
        }
        BL[Bg++] = "</Filters>";
    }
    return BL.join("");
};
_7m.prototype.GetSections = function () {
    var BY = this.ColNames;
    var DP = this.GetFirstCol(0) ? 0 : 1;
    var DQ = this.GetFirstCol(2) ? 3 : 2;
    return [DP, DQ];
};
_7m.prototype.GetNextCol = function (BJ, row) {
    if (!BJ) return null;
    if (row && row.Space != null) {
        for (var i = 0; i < row.Cells.length; i++) {
            if (row.Cells[i] == BJ) return row.Cells[i + 1];
        }
    }
    var C = this.Cols;
    if (!C[BJ]) return null;
    var DR = C[BJ].K,
    S = this.ColNames,
    Bg = C[BJ].Pos + 1;
    while (1) {
        while (S[DR][Bg] && (!C[S[DR][Bg]].Visible && (!row || !row.Spanned || !(Get(row, S[DR][Bg] + "Span") > 1) || this.DS(row, S[DR][Bg]) <= 0) || row && row.Spanned && Get(row, S[DR][Bg] + "Span") == 0)) Bg++;
        if (S[DR][Bg]) return S[DR][Bg];
        DR++;
        Bg = 0;
        if (DR > 2) return null;
    }
};
_7m.prototype.GetPrevCol = function (BJ, row) {
    if (!BJ) return null;
    if (row && row.Space != null) {
        for (var i = 0; i < row.Cells.length; i++) {
            if (row.Cells[i] == BJ) return i ? row.Cells[i - 1] : null;
        }
    }
    var C = this.Cols;
    if (!C[BJ]) return null;
    var DR = C[BJ].K,
    S = this.ColNames,
    Bg = C[BJ].Pos - 1;
    while (1) {
        while (S[DR][Bg] && (!C[S[DR][Bg]].Visible && (!row || !row.Spanned || !(Get(row, S[DR][Bg] + "Span") > 1) || this.DS(row, S[DR][Bg]) <= 0) || row && row.Spanned && Get(row, S[DR][Bg] + "Span") == 0)) Bg--;
        if (S[DR][Bg]) return S[DR][Bg];
        DR--;
        if (DR < 0) return null;
        Bg = S[DR].length - 1;
    }
};
_7m.prototype.GetLastCol = function (DR, row) {
    if (row && row.Space != null) return row.Cells[row.Cells.length - 1];
    if (DR == null) {
        for (var i = 2; i >= 0; i--) {
            var BJ = this.GetLastCol(i, row);
            if (BJ) return BJ;
        }
        return null;
    }
    var S = this.ColNames[DR],
    Bg = S.length - 1,
    C = this.Cols;
    while (S[Bg] && (!C[S[Bg]].Visible && (!row || !row.Spanned || !(Get(row, S[Bg] + "Span") > 1) || this.DS(row, S[Bg]) <= 0) || row && row.Spanned && Get(row, S[Bg] + "Span") == 0)) Bg--;
    return S[Bg];
};
_7m.prototype.GetFirstCol = function (DR, row) {
    if (row && row.Space != null) return row.Cells[0];
    if (DR == null) {
        for (var i = 0; i < 3; i++) {
            var BJ = this.GetFirstCol(i);
            if (BJ) return BJ;
        }
        return null;
    }
    var S = this.ColNames[DR],
    Bg = 0,
    C = this.Cols;
    while (S[Bg] && !C[S[Bg]].Visible) Bg++;
    return S[Bg];
};
_7m.prototype.GetCaption = function (BJ) {
    return this.XHeader[BJ];
};
_7m.prototype.DT = function (DU, DV) {
    var BL = new Array(),
    Bg = 0;
    for (var i = 0; i < 3; i++) {
        for (var j = 0; j < this.ColNames[i].length; j++) {
            var BJ = this.ColNames[i][j];
            if (BJ == "Panel" && !DU || !this.Cols[BJ].Visible || DV && !this.Cols[BJ].CanCopy) continue;
            BL[Bg++] = BJ;
        }
    }
    return BL;
};
_7m.prototype.AddCol = function (BJ, DR, I, width, Bx, type, caption, DW, DX) {
    if (this.Cols[BJ]) return null;
    var C = new _7i();
    for (var BY in this.Def["C"]) C[BY] = this.Def["C"][BY];
    for (var Bj in this.Def) if (this.Def[Bj][BJ] == null) this.Def[Bj][BJ] = "";
    this.Cols[BJ] = C;
    if (DR != 0 && DR != 2) DR = 1;
    C.K = DR;
    var Cq = this.ColNames[DR];
    if (! (I >= 0 && I < Cq.length)) I = Cq.length;
    else for (var i = Cq.length; i > I; i--) {
        Cq[i] = Cq[i - 1];
        this.Cols[Cq[i]].Pos = i;
    }
    Cq[I] = BJ;
    C.Pos = I;
    C.Name = BJ;
    C.Index = BJ;
    C.Group = 0;
    if (type) C.Type = type;
    this.XHeader[BJ] = caption ? caption: BJ;
    C.Visible = false;
    if (width) {
        C.Width = width;
        C.Ci = 0;
    } else this.CalcWidth(BJ);
    if (Bx) this.ShowCol(BJ);
    return C;
};
_7m.prototype.DelCol = function (BJ) {
    var C = this.Cols[BJ];
    if (!C) return;
    this.HideCol(BJ);
    var Cq = this.ColNames[C.K];
    for (var i = C.Pos; i < Cq.length - 1; i++) {
        Cq[i] = Cq[i + 1];
        this.Cols[Cq[i]].Pos--;
    }
    Cq.length--;
    var D = new Object();
    for (var BY in this.Cols) if (BY != BJ && !D[BY]) D[BY] = this.Cols[BY];
    this.Cols = D;
};
_7m.prototype.ShowCol = function (BJ) {
    var C = this.Cols[BJ];
    if (!C || C.Visible) return;
    if (!this.GetFirstCol(C.K)) {
        C.Visible = 1;
        this.SetVPos();
        this.Render();
    } else {
        C.Visible = 1;
        this.SetVPos();
        this.DY(BJ);
        this.DZ(C.K);
        this.SetScrollBars();
    }
};
_7m.prototype.HideCol = function (BJ) {
    var C = this.Cols[BJ];
    if (!C || !C.Visible) return;
    C.Visible = 0;
    if (!this.GetFirstCol(C.K)) {
        if (C.K == 1) {
            C.Visible = 1;
            return;
        }
        this.SetVPos();
        this.Render();
    } else {
        this.Da(BJ);
        this.SetVPos();
        this.DZ(C.K);
        this.SetScrollBars();
    };
};
_7m.prototype.ActionHideCol = function (CE) {
    var BJ = CE ? this.FCol: this.ACol,
    C = this.Cols;
    if (!BJ || !C[BJ] || !C[BJ].CanHide || !C[BJ].Visible) return false;
    if (this.FRow && this.FRow.Space == null && this.FCol == BJ) {
        var Db = this.GetNextCol(BJ);
        if (!Db) Db = this.GetPrevCol(BJ);
        if (Db) this.Focus(this.FRow, Db);
    }
    this.HideCol(BJ);
    return true;
};
_7m.prototype.ActionShowColLeft = function (CE) {
    var BJ = CE ? this.FCol: this.ACol,
    C = this.Cols;
    if (!BJ || !C[BJ]) return false;
    var DR = C[BJ].K,
    S = this.ColNames,
    Bg = C[BJ].Pos - 1;
    while (1) {
        while (S[DR][Bg] && !C[S[DR][Bg]].CanHide && !C[S[DR][Bg]].Visible) Bg--;
        var Db = S[DR][Bg];
        if (Db) {
            if (C[Db].Visible) return false;
            this.ShowCol(Db);
            if (this.FRow && this.FCol == BJ) this.Focus(this.FRow, Db);
            return true;
        }
        DR--;
        if (DR < 0) return false;
        Bg = S[DR].length - 1;
    }
};
_7m.prototype.ActionShowColRight = function (CE) {
    var BJ = CE ? this.FCol: this.ACol,
    C = this.Cols;
    if (!BJ || !C[BJ]) return false;
    var DR = C[BJ].K,
    S = this.ColNames,
    Bg = C[BJ].Pos + 1;
    while (1) {
        while (S[DR][Bg] && !C[S[DR][Bg]].CanHide && !C[S[DR][Bg]].Visible) Bg++;
        var Db = S[DR][Bg];
        if (Db) {
            if (C[Db].Visible) return false;
            this.ShowCol(Db);
            if (this.FRow && this.FCol == BJ) this.Focus(this.FRow, Db);
            return true;
        }
        DR++;
        Bg = 0;
        if (DR > 2) return false;
    }
};
_7m.prototype.ChangeColsVisibility = function (CA, Dc, Dd) {
    var C = this.Cols,
    BL = new Object();
    if (Dc) for (var i = 0; i < Dc.length; i++) {
        var BY = C[Dc[i]];
        BL[Dc[i]] = BY && BY.Visible ? -1 : 0;
    }
    if (CA) for (var i = 0; i < CA.length; i++) {
        var BY = C[CA[i]];
        if (BL[CA[i]] != null) {
            if (Dd) continue;
            else BL[CA[i]] = null;
        }
        BL[CA[i]] = BY && !BY.Visible ? 1 : 0;
    }
    var De = new Array();
    for (var i = 0; i < 3; i++) {
        var Cq = this.ColNames[i],
        BQ = 0,
        Df = 0;
        for (var j = 0; j < Cq.length; j++) {
            var BY = Cq[j];
            if (C[BY].Visible) BQ++;
            if (BL[BY]) {
                Df += BL[BY];
                De[i] = 1;
            }
        }
        if (BQ == 0 && Df > 0 || BQ > 0 && BQ + Df == 0) {
            for (var BY in BL) {
                if (!BY || !BL[BY]) continue;
                C[BY].Visible = BL[BY] == 1;
            }
            this.SetVPos();
            return true;
        }
    }
    if (!this.Loading && !this.Rendering) this.Dg();
    var Dh = this.Rendering;
    this.Rendering = 1;
    for (var BY in BL) {
        if (!BY || !BL[BY]) continue;
        C[BY].Visible = BL[BY] == 1;
        if (BL[BY] == 1) {
            this.SetVPos();
            this.DY(BY);
        } else {
            this.Da(BY);
            this.SetVPos();
        }
    }
    this.SetVPos();
    if (!this.Loading && !this.Rendering) for (var i = 0; i < 3; i++) if (De[i]) this.DZ(i);
    this.Rendering = Dh;
    this.SetScrollBars();
    return false;
};
_7m.prototype.MoveCol = function (BJ, Di, Dj) {
    var C = this.Cols,
    DR = C[BJ].K,
    I = C[BJ].Pos,
    S = this.ColNames[DR];
    if (Di > I) Di--;
    var Dk = C[S[Di]].Dl;
    if (!C[S[Di]].Visible) {
        var BY = this.GetPrevCol(S[Di]);
        if (BY && C[BY].K == C[BJ].K) Dk = C[BY].Dl + (Di <= I);
        else Dk = 0;
    }
    if (!Dj) this.Dm(BJ, Dk);
    if (Di < I) for (var i = I; i >= Di; i--) {
        C[S[i]].Pos++;
        S[i] = S[i - 1];
    } else if (Di > I) {
        for (var i = I; i <= Di; i++) {
            C[S[i]].Pos--;
            S[i] = S[i + 1];
        }
    }
    C[BJ].Pos = Di;
    S[Di] = BJ;
    this.SetVPos();
    if (!Dj && Grids.OnDisplaceRow) {
        Grids.OnDisplaceRow(this, this.XHeader, BJ);
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) Grids.OnDisplaceRow(this, CE[i], BJ);
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (J.r1) Grids.OnDisplaceRow(this, J, BJ);
    }
};
_7m.prototype.ChangeColsPositions = function (Dn, cols, Do) {
    for (var i = 0; i < 3; i++) {
        var Cq = new Array(),
        BL = arguments[i],
        k = 0;
        if (!i) {
            Cq[k++] = "Panel";
        }
        if (BL) for (var j = 0; j < BL.length; j++) {
            var BY = this.Cols[BL[j]];
            if (BY) {
                BY.K = i;
                BY.Pos = k;
                Cq[k++] = BL[j];
            }
        }
        this.ColNames[i] = Cq;
    }
    this.SetVPos();
};
_7m.prototype.DS = function (row, BJ) {
    if (row.Space != null) return row[BJ + "Width"];
    var C = this.Cols[BJ];
    if (!row.Spanned) return C.Width;
    var Span = Get(row, BJ + "Span");
    Span = Span == null ? 1 : Span - 0;
    if (Span == 0) return 0;
    var Pos = C.Pos,
    Dp = 0;
    for (var i = 0; i < Span; i++) {
        var BY = this.ColNames[C.K][Pos + i];
        if (!BY) break;
        if (this.Cols[BY].Visible) Dp += this.Cols[BY].Width + this.CellSpacing;
    }
    return Dp - this.CellSpacing;
};
_7m.prototype.SetVPos = function () {
    var Cq = this.ColNames,
    C = this.Cols;
    for (var i = 0; i < Cq.length; i++) {
        var D = Cq[i],
        Dk = 0;
        for (var BY = 0; BY < D.length; BY++) {
            if (C[D[BY]].Visible) C[D[BY]].Dl = Dk++;
            else C[D[BY]].Dl = -1;
        }
    }
};
_7m.prototype.Dq = function (row, BJ) {
    if (!row.Spanned) return BJ;
    var Dr = Get(row, BJ + "Span");
    if (Dr != 0) return BJ;
    var C = this.Cols[BJ],
    S = this.ColNames[C.K],
    Bg = C.Pos - 1;
    while (Bg && !Get(row, S[Bg] + "Span")) Bg--;
    if (Get(row, S[Bg] + "Span") + this.Cols[S[Bg]].Pos > C.Pos) BJ = S[Bg];
    return BJ;
};
_7m.prototype.GetColor = function (row, BJ) {
    if (row == this.XHeader) return "";
    var C = this.Al,
    Ds;
    var Dt = BJ + "Color";
    if (BJ && (row[Dt] || row.Def[Dt])) {
        Ds = Get(row, Dt);
        if (typeof(Ds) == "string") {
            Ds = Ds.split(",");
            Ds = (Ds[0] << 16) + (Ds[1] << 8) + (Ds[2] - 0);
            row[Dt] = Ds;
        }
    } else if (row.Color || row.Def.Color) {
        Ds = Get(row, "Color");
        if (typeof(Ds) == "string") {
            Ds = Ds.split(",");
            Ds = (Ds[0] << 16) + (Ds[1] << 8) + (Ds[2] - 0);
            row.Color = Ds;
        }
    } else Ds = C["Default"];
    if (row.ColorPos == 1) {
        var alt = Get(row, "AlternateColor");
        if (alt) {
            if (typeof(alt) == "string") {
                alt = alt.split(",");
                alt = (alt[0] << 16) + (alt[1] << 8) + (alt[2] - 0);
                row["AlternateColor"] = alt;
            }
            Ds += alt;
        } else Ds += C["Alternate"];
    }
    if (Grids.OnGetDefaultColor) {
        var BY = Grids.OnGetDefaultColor(this, row, BJ, Ds);
        if (BY != null) Ds = BY;
    }
    var Du, Dv;
    if (BJ) {
        Dv = this.CanFocus(row, BJ);
        if (Dv) Du = this.CanEdit(row, BJ);
        else Ds += C["CannotFocus"];
        var Dw = Get(row, BJ + "NoColor");
        if (Dw || Dw == null && Get(row, "NoColor")) {
            return "";
        }
    } else {
        Dv = Get(row, "CanFocus");
        if (Dv) Du = Get(row, "CanEdit");
        else Ds += C["CannotFocus"];
        if (Get(row, "NoColor")) return "transparent";
    };
    if (!Du) Ds += C["ReadOnly"];
    else if (Du == 2) Ds += C["Preview"];
    if (Get(row, "Detail")) {
        Ds += C[row.DetailRow ? "DetailSelected": "Detail"];
    }
    if (row == this.ARow && this.Hover >= 2) Ds += C[BJ && BJ == this.ACol && Dv ? "HoveredCell": "Hovered"];
    if (row.Dx) Ds += C["ChildPage"];
    if (row.Selected - 0) {
        if (row.Selected == 1 || Is(row, BJ + "Selected")) Ds += C["Selected"];
    }
    if (row.NoColorState);
    else if (BJ && Get(row, BJ + "Error")) Ds += C["Error"];
    else if (row.Deleted - 0) Ds += C["Deleted"];
    else if (row.Added - 0) Ds += C["Added"];
    else if (row.Changed - 0) {
        if (BJ && row[BJ + "Changed"] - 0) Ds += C["ChangedCell"];
        else Ds += C["Changed"];
    } else if (row.Moved == 2) Ds += C["Moved"];
    if (this.SearchAction == "Mark") {
        if (C["FoundAbsolute"]) {
            if (row.Dy) Ds = row.Dy;
            if (row[BJ + "MarkColor"]) Ds = row[BJ + "MarkColor"];
        } else {
            if (row.Dy) Ds += row.Dy;
            if (row[BJ + "MarkColor"]) Ds += row[BJ + "MarkColor"];
        }
    }
    if (row == this.FRow) {
        if (row.Spanned && BJ) BJ = this.Dq(row, BJ);
        if (BJ && BJ == this.FCol && this.FocusWholeRow != 1 || this.FocusWholeRow == 2) {
            var BY = this.EditMode ? C[Du == 2 ? "ViewedCell": "EditedCell"] : C["FocusedCell"];
            if (BY) Ds = BY;
            else Ds += C["Focused"];
        } else Ds += C["Focused"];
    }
    if (Grids.OnGetColor) {
        var Dt = Grids.OnGetColor(this, row, BJ, (Ds >> 16) & 255, (Ds >> 8) & 255, Ds & 255, 0);
        if (Dt != null) return Dt;
    }
    if (Ds >= 16777215 || Ds < 0) {
        return "";
    }
    if (Ds < 1048576) {
        Ds = Number(Ds).toString(16);
        while (Ds.length < 6) Ds = '0' + Ds;
        return '#' + Ds;
    }
    return "#" + Number(Ds).toString(16);
};
_7m.prototype.Dz = function (Dj) {
    if (!this.D0) return;
    for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) if (Bg.firstChild) this.D1(Bg, Dj);
};
_7m.prototype.D1 = function (Bg, Dj) {
    if (!this.D0) return;
    var a = this.D2;
    for (var J = Bg.firstChild, BY = a; J; J = J.nextSibling) {
        if (!J.Visible) continue;
        if (!Dj && J.ColorPos != !BY) {
            J.ColorPos = BY;
            this.ColorRow(J);
        } else J.ColorPos = !BY;
        if (!--BY) BY = a;
        if (J.firstChild) this.D1(J, Dj);
    }
};
_7m.prototype.ColorRow = function (row) {
    if (!this.e || !row || !row.Visible || row.Kind == "Page" || row.Kind == "User") return;
    var BJ, color = this.GetColor(row),
    D3 = this.GetSections();
    if (row.Space != null) {
        var J = row.r1;
        if (J) {
            J = _7s(J, "tr")[0];
            if (J) {
                var cells = row.Cells;
                if (cells) for (var i = 0; i < cells.length; i++) {
                    J.cells[row[cells[i] + "Pos"]].style.backgroundColor = this.GetColor(row, cells[i]);
                }
            }
        }
    } else for (var i = D3[0]; i < D3[1]; i++) {
        var J = this.GetRow(row, i);
        if (J) {
            J = _7s(J, "tr")[0];
            if (J) {
                var BJ = this.GetFirstCol(i);
                for (var j = 0; j < J.cells.length; j++) {
                    if (BJ != "Panel") J.cells[j].style.backgroundColor = this.GetColor(row, BJ);
                    BJ = this.GetNextCol(BJ, row);
                }
            }
        }
    }
};
_7m.prototype.ColorCell = function (row, BJ) {
    if (this.FocusWholeRow || BJ == "Panel" || !row || !row.Visible || row.Kind == "Page") return;
    var D4 = this.GetCell(row, BJ);
    if (!D4) return;
    var color = this.GetColor(row, BJ);
    D4.style.backgroundColor = color;
};
_7m.prototype.D5 = function () {
    if (this.AT == this.ARow && this.AU == this.ACol) return;
    var J, BY, i;
    if (this.AT != this.ARow) {
        if (this.AT) this.ColorRow(this.AT);
        if (this.ARow) this.ColorRow(this.ARow);
    } else if (this.ARow) {
        this.ColorCell(this.ARow, this.ACol);
        this.ColorCell(this.AT, this.AU);
    }
};
_7m.prototype.Communicate = function (D6, Data, D7) {
    if (Grids.OnDataSend && Grids.OnDataSend(this, D6, Data, D7)) return;
    var D8 = D6;
    if (!D8.Url) {
        if (D7) D7( - 2, this.GetAlert("ErrBadUrl"));
        return;
    }
    for (var Ce = 0; this.z[Ce]; Ce++);
    this.z[Ce] = (new Date()).getTime() + (D6.Timeout >= 0 ? D6.Timeout * 1000 : 60000);
    var D9 = _7t();
    if (!D9 || typeof(D9) == "string") {
        this.z[Ce] = null;
        var E = this.GetAlert("ErrXMLSupport");
        if (!E) E = _7R ? "In your browser is denied ActiveX object XmlHttpRequest !": "Browser does not support XmlHttpRequest !";
        var EA = D9 ? D9 + "\n\n" + E: E;
        if (D7) D7( - 1, EA);
        return;
    }
    var content = "",
    EB = "",
    EC = D8.Method ? D8.Method.toLowerCase() : null;
    if (!EC || EC == "post") EC = "form";
    if (Data && !(D8.Xml - 0)) Data = (Data + "").replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
    switch (EC) {
    case "get":
    case "form":
        if (D8.Param) for (var i in D8.Param) {
            if (EB) EB += "&";
            EB += encodeURIComponent(i) + "=" + encodeURIComponent(D8.Param[i]);
        }
        if (D8.Params) {
            if (EB) EB += "&";
            EB += encodeURI(D8.Params);
        }
        if (EC == "form" && D8.Data) {
            if (EB) EB += "&";
            EB += encodeURIComponent(D8.Data) + "=" + encodeURIComponent(Data);
        }
        if (EC == "form") content = "application/x-www-form-urlencoded";
        break;
    case "soap":
        var ED = "soap";
        EB = '<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">';
        if (D8.Envelope) {
            EB = D8.Envelope;
            var M = EB.match(/^\<(\w*)\:/);
            if (M && M[1]) ED = M[1];
        }
        EB += "<" + ED + ":Body><" + D8.Function + (D8.Namespace ? " xmlns='" + D8.Namespace + "'>": "");
        if (D8.Data) EB += "<" + D8.Data + ">" + Data + "</" + D8.Data + ">";
        if (D8.Param) for (var i in D8.Param) EB += "<" + i + ">" + D8.Param[i] + "</" + i + ">";
        if (D8.Params) EB += D8.Params;
        EB += "</" + D8.Function + "></" + ED + ":Body></" + ED + ":Envelope>";
        content = "text/xml";
        break;
    };
    var EE = 0;
    if ((_7R || _7L) && !content && EC == "get" && location.protocol == "file:" && D8.Url.indexOf("p://") < 0) {
        if (_7R) {
            var EF = new ActiveXObject("Microsoft.XMLDOM");
            if (EF) {
                EE = 1;
                D9 = EF;
                D9.async = "false";
            }
        } else {
            if (document.cookie.search(/TGFF3SA\s?\=\s?1/) < 0) {
                alert("In Firefox 3 is disabled reading local files from different directories.\nTreeGrid requires access to its data source XML files.\nYou need to permit the access in the next dialog.\nOr you can run the page from your localhost server.");
                var exp = new Date();
                exp.setDate(exp.getDate() + 1);
                document.cookie = "TGFF3SA=1; expires=" + exp.toUTCString();
            }
            netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
        }
    }
    this.w[Ce] = D9;
    this.AB[Ce] = D7;
    this.AA[Ce] = D6;
    try {
        D9.onreadystatechange = new Function("if(Grids[" + this.Index + "]) Grids[" + this.Index + "].CommunicateFinish(" + Ce + "," + EE + ");");
        var EG = D8.Url;
        if (EC == "get") {
            if (EB) {
                EG = EG + ((EG + "").indexOf('?') >= 0 ? "&": "?") + EB;
                EB = "";
            }
            EG = this.EH(EG, D8.Cache);
        }
        if (EE) {
            D9.load(EG);
            return;
        }
        D9.open(content ? "POST": "GET", EG, D8.Sync ? false: true);
        if (content) D9.setRequestHeader("Content-Type", content + "; charset=UTF-8");
        if (EC == "soap") D9.setRequestHeader("SOAPAction", (D8.Namespace ? D8.Namespace + "/": "") + D8.Function);
    } catch(B) {
        if (D7) D7( - 2, typeof(B) == "string" ? B: B.message);
        return;
    };
    if (D8.Debug >= 3) ShowHTML(Data, D8.Debug == 3 ? "InputData": "_blank");
    try {
        D9.send(EB);
    } catch(B) {
        if (D7) D7( - 3, typeof(B) == "string" ? B: B.message);
        return;
    };
};
_7m.prototype.CommunicateFinish = function (Ce, EE) {
    var D9 = this.w[Ce];
    if (!D9 || D9.readyState != 4) return;
    var D8 = this.AA[Ce],
    D7 = this.AB[Ce],
    EI = 0;
    if (EE) EI = 200;
    else try {
        EI = D9.status;
    } catch(B) {};
    function EA(EJ, text, EK) {
        T.z[Ce] = null;
        if (Grids.OnDataErrorDebug) Grids.OnDataErrorDebug(T, EJ, text, EK);
        if (D8.Debug && (D8.Debug < 3 || EJ == -3)) EL = ShowHTML(EK, D8.Debug == 1 || D8.Debug == 3 ? "OutputData": "_blank");
        if (D7) {
            var EM = T.GetAlert(D8.Debug ? (EL ? "ErrSeeDebug": "ErrPermitPopup") : "ErrSetDebug");
            if (!EM) EM = "\n\nTreeGrid texts are missing";
            D7(EJ, text + EM);
        }
    };
    var EL, T = this;
    this.w[Ce] = null;
    if (EI == 0) {
        if (this.z[Ce] < 0) {
            this.z[Ce] = null;
            if (D7) D7( - 6, this.GetAlert(D8 == this.Data.Upload ? "UploadTimeout": "ErrTimeout"));
            return;
        }
    } else if (EI < 100 || EI >= 400) {
        if (_7R && EI < 100 && D9.responseText == "") return;
        EA( - 3, "[" + EI + "] " + D9.statusText, D9.responseText);
        return;
    }
    if (D8.Debug >= 3) EL = ShowHTML(D9.responseText, D8.Debug == 3 ? "OutputData": "_blank");
    if (!EE && (_7u(D9.responseText) != '<' || D9.responseText.slice(0, 3).toLowerCase() == "<br")) {
        if (D9.responseText.search("^\s*$") >= 0) {
            this.z[Ce] = null;
            if (D7) D7( - 4, this.GetAlert("ErrEmptyReturn"));
        } else EA( - 4, this.GetAlert("ErrTextReturn"), "<html>" + this.GetAlert("ErrFormatReturn") + "<br/><br/>" + D9.responseText + "</html>");
        return;
    }
    var EN = EE ? D9: D9.responseXML;
    if (!EE && D9.responseText != "" && (!EN || !EN.lastChild)) {
        EN = CreateXML(D9.responseText);
    }
    if (EE) {
        if (EN.parseError.errorCode != 0) {
            EA( - 4, this.GetAlert("ErrXMLFormat"), EN.parseError.reason);
            return;
        }
    } else if (!EN || !EN.lastChild || !EN.documentElement) {
        var Bh = D9.responseText.slice(0, 5).toLowerCase();
        EA( - 4, this.GetAlert(Bh == "<html" || Bh == "<body" || Bh == "<!doc" ? "ErrHtmlReturn": "ErrXMLFormat"), D9.responseText);
        return;
    }
    if (EN.documentElement.tagName == "parsererror") {
        var Bh = D9.responseText.slice(0, 5).toLowerCase();
        EA( - 4, this.GetAlert(Bh == "<html" || Bh == "<body" || Bh == "<!doc" ? "ErrHtmlReturn": "ErrXMLFormat"), EN.documentElement.firstChild.nodeValue + "\n\n" + D9.responseText);
        return;
    }
    this.z[Ce] = null;
    var D4 = _7s(EN, "Grid")[0];
    if (D4) {
        if (_7O && !_7P) {
            if (window._7v) D4 = _7v(D9.responseText).firstChild;
            else _7w("Xml");
        }
        if (D7) D7(0, D4);
        return;
    }
    var ED = "soap";
    if (D8.Envelope) {
        var M = D8.Envelope.match(/^\<(\w*)\:/);
        if (M && M[1]) ED = M[1];
    }
    if (_7x(EN, "Fault", ED)[0]) {
        var D4 = _7s(EN, "faultstring")[0];
        if (!D4) D4 = _7s(EN, "Reason")[0];
        EA( - 5, D4 && D4.firstChild ? D4.firstChild.nodeValue: this.GetAlert("SoapErr"), D9.responseText);
        return;
    }
    if (D8.Function) {
        D4 = _7s(EN, D8.Function + "Result")[0];
        if (!D4) D4 = _7s(EN, D8.Function + "Return")[0];
        if (!D4) {
            D4 = _7s(EN, D8.Function + "Response")[0];
            if (D4) D4 = D4.firstChild;
        }
    }
    if (!D4) {
        D4 = _7x(EN, "Body", ED)[0];
        if (D4) {
            D4 = D4.firstChild;
            if (D4) D4 = D4.firstChild;
        }
    }
    if (!D4) D4 = EN.lastChild;
    if (D4) D4 = D4.firstChild;
    if (D4) {
        var DD = "";
        while (D4) {
            if (D4.nodeType == 3) DD += D4.nodeValue;
            D4 = D4.nextSibling;
        }
        var xml = CreateXML(DD);
        if (D7) D7(0, xml ? xml.documentElement: "");
        return;
    }
    if (D7) D7(0, "");
    return;
};
_7m.prototype.Communicate2 = function (D6, Data, D7, row) {
    var T = this;
    this.Communicate(D6, Data, function (code, EM) {
        T.EO(code, EM, D6, D7, row, Data)
    });
};
_7m.prototype.EO = function (code, EM, D6, D7, row, Data) {
    if (code < 0) {
        if (!Grids.OnDataError || !Grids.OnDataError(this, code, EM, D6, row)) {
            if (code == -6 && D6.Repeat) {
                var EP = 0;
                if (D6.Repeat == 3) EP = 1;
                else if (D6.Repeat == 2) {
                    if (confirm(this.GetAlert(D6 == this.Data.Upload ? "AskUploadTimeout": "AskTimeout"))) EP = 1;
                }
                if (EP) {
                    this.Communicate2(D6, Data, D7, row);
                    return;
                }
            } else {
                alert(code + " : " + EM + this.EQ(D6));
            }
        }
        if (D7) D7(code);
        return;
    }
    this.ER(EM, null, 1);
    var ES = this.IO;
    if (!ES) ES = new Object();
    if (ES.Message) alert(ES.Message);
    if (!ES.UpdateMessage || confirm(ES.UpdateMessage)) this.ER(EM, row);
    if (ES.ReloadMessage && !confirm(ES.ReloadMessage)) ES.Reload = 0;
    if (Grids.OnDataReceive) Grids.OnDataReceive(this, row);
    var EJ = ES.Result < 0 ? ES.Result: 0;
    if (!EJ && ES.Reload == 1) this.ReloadBody(null, 0, "Reload");
    if (D7) D7(EJ);
};
function _7y() {
    var Cs = (new Date()).getTime();
    for (var i = 0; i < Grids.length; i++) {
        var G = Grids[i];
        if (G) for (var j = 0; j < G.z.length; j++) if (G.z[j] > 0 && G.z[j] < Cs) {
            G.z[j] = -1;
            if (G.w[j]) G.w[j].abort();
        }
    }
};
_7m.prototype.EQ = function (D6) {
    var Bh = "\n";
    if (D6.Debug - 0) {
        if (D6.Url) Bh += "\nUrl: " + D6.Url;
        if (D6.Tag) Bh += "\nTag: " + D6.Tag;
        if (D6.Function) Bh += "\nFunc: " + (D6.Namespace ? "[" + D6.Namespace + "] ": "") + D6.Function;
        if (D6.Param) {
            var Dr = "";
            for (var Bg in D6.Param) Dr += "\n " + Bg + " = " + D6.Param[Bg];
            if (Dr) Bh += "\nParam: " + Dr;
        }
    } else {
        Bh = this.GetAlert("NoDebugInfo");
        if (Bh == null) Bh = "\n\nNo information is provided due debugging level 0";
    };
    return Bh;
};
_7m.prototype.ET = function (EU) {
    var H = _7f.createElement("B");
    EU.appendChild(H);
    if (EU.Page) {
        H.Def = this.Def["R"];
        var EV = 1;
    } else {
        var EW = Grids.A8;
        for (var BY in EU) if (!EW[BY]) H[BY] = EU[BY];
        var EV = this.EmptyChildPages;
    };
    H.Dx = 1;
    H.AggChildren = 1;
    H.Level = EU.Level + 1;
    H.CanEdit = 0;
    H.Expanded = 0;
    H.Visible = 0;
    H.State = 2;
    H.CanDelete = 0;
    H.CanSelect = 0;
    H.CanCopy = 0;
    H.Selected = 0;
    H.CanDrag = 0;
    H.r0 = null;
    H.r1 = null;
    H.r2 = null;
    for (var BY in this.Cols) {
        H[BY + "CanEdit"] = 0;
        H[BY + "Button"] = "None";
        if (EV) H[BY + "Visible"] = 0;
    }
    if (EV) H[this.MainCol + "Visible"] = 1;
    return H;
};
_7m.prototype.EX = function (H) {
    if (this.FRow == H) this.FRow = H.parentNode;
    var B1 = H.State == 4 && H.firstChild;
    for (var i = 0; i < 3; i++) {
        var J = H["r" + i];
        if (!J) continue;
        var Bg = J.parentNode;
        if (B1) Bg.removeChild(J.nextSibling);
        Bg.removeChild(J);
    }
    var EU = H.parentNode;
    EU.removeChild(H);
    H.Removed = 1;
    if (!EU.firstChild) EU.CDef = H.CDef;
};
_7m.prototype.EY = function (EU, Bx) {
    if (EU == this.XB) {
        if (!this.MainCol) return;
        for (var J = EU.firstChild; J; J = J.nextSibling) this.EY(J, Bx);
    } else if (EU.Page) {
        for (var J = EU.firstChild; J; J = J.nextSibling) if (J.firstChild) this.EZ(J, Bx, null, 1);
    } else this.EZ(EU, Bx, null, 1);
};
_7m.prototype.EZ = function (EU, Bx, BL, Ea) {
    var max = EU.Page ? 1e10: Get(EU, "MaxChildren"),
    Eb = EU.firstChild,
    BQ = 0,
    BW = EU.Page ? 0 : Get(EU, "MaxChildrenDiff");
    if (!BL) {
        if (!Eb || !EU.Visible && !EU.Page) return;
        if (Eb.Dx) {
            BL = new Array();
            for (var Ec = EU.firstChild, Bg = 0; Ec; Ec = Ec.nextSibling) {
                for (var J = Ec.firstChild; J; J = J.nextSibling) {
                    BL[Bg++] = J;
                    if (J.Visible) BQ++;
                    if (Ea && J.firstChild) this.EZ(J, Bx, null, 1);
                }
            }
        } else {
            var BN = EU.childNodes.length;
            if (BN > max + BW) {
                BL = new Array();
                for (var J = EU.firstChild, Bg = 0; J; J = J.nextSibling) {
                    BL[Bg++] = J;
                    if (J.Visible) BQ++;
                    if (Ea && J.firstChild) this.EZ(J, Bx, null, 1);
                }
            } else {
                BQ = BN;
                if (Ea) for (var J = EU.firstChild; J; J = J.nextSibling) if (J.firstChild) this.EZ(J, Bx, null, 1);
            }
        }
    } else {
        var BN = BL.length;
        if (BN > max + BW) for (var i = 0; i < BN; i++) if (BL[i].Visible) BQ++;
        else BQ = BN;
        if (Ea) for (var i = 0; i < BN; i++) if (BL[i].firstChild) this.EZ(BL[i], Bx, null, 1);
    };
    var T = this;
    function Ed(row, Bx, Ee) {
        for (var J = row.firstChild; J; J = J.nextSibling) {
            T.Ef(J, Bx, Ee);
            if (J.firstChild) Ed(J, Bx, Ee);
        }
    };
    if (BQ > max + BW) {
        var Eg = BQ < 100 ? 2 : BQ < 1000 ? 3 : 4,
        Eh = "0000",
        Ei = this.GetText("Items");
        var BQ = 0,
        body, Ej = EU.childNodes.length;
        EU._Count = BQ;
        var Ek = Eb.Dx ? Eb.CDef: Get(EU, "CDef");
        for (var i = 0; i < BL.length; i++) {
            if (BL[i].Visible || !body) {
                if (!BQ) {
                    BQ = max;
                    body = this.ET(EU);
                    body.CDef = Ek;
                }
                BQ--;
            }
            if (Bx && EU.Expanded && EI != 3 && BL[i].r1) this.El(BL[i]);
            body.appendChild(BL[i]);
            BL[i].Level = body.Level + 1;
        }
        EU.CDef = "*";
        if (Eb.Dx) {
            for (var i = 0; i < Ej; i++) this.EX(EU.firstChild);
        }
        var Em = this.MainCol,
        En = 0,
        EI = EU.State;
        if (EU.State == 4) EU.State = 2;
        function Eo(BR) {
            BR += "";
            return Eh.slice(0, Eg - BR.length) + BR;
        };
        var Ee = this.Cols[this.MainCol];
        for (var J = EU.firstChild; J; J = J.nextSibling) {
            for (var BY = J.firstChild, Ep = 0; BY; BY = BY.nextSibling) if (BY.Visible) Ep++;
            this.Recalculate(J, null, Bx);
            J[Em + "Type"] = "Html";
            J[Em + "Format"] = "";
            J[Em] = "<div nowrap" + _7E + ">" + Ei.replace(/\%d/, Eo(En + 1)).replace(/\%d/, Eo(En + Ep)) + "</div>";
            if (Bx && EU.Expanded && EI != 3) {
                this.ShowRow(J);
                this.RefreshCell(J, Em);
            }
            J.Visible = 1;
            En += Ep;
        }
        Ed(EU, 0, Ee);
    } else if (BL) {
        if (_7W) for (var i = 0; i < BL.length; i++) EU.removeChild(BL[i]);
        for (var i = 0; i < BL.length; i++) EU.appendChild(BL[i]);
        if (Eb.Dx) {
            for (var J = Eb; J && J.Dx; J = EU.firstChild) this.EX(EU.firstChild);
            Ed(EU, Bx, Ee);
            if (Bx && EU.Expanded && EI != 3) {
                var r1 = 1;
                for (var J = EU.firstChild; J; J = J.nextSibling) if (!J.r1) {
                    r1 = 0;
                    break;
                }
                if (!r1) {
                    this.Collapse(EU);
                    EU.State = 2;
                    this._7d(EU);
                    this.Expand(EU);
                }
                if (!this.HasChildren(EU) || !this.Eq(EU)) this.Er(EU);
            }
            this.Es(EU);
        }
        if (Bx) this.Et(BL, Eb);
    }
};
_7m.prototype.Eu = function (EU, Bx) {
    var BQ = 0,
    Eg = EU._Count < 100 ? 2 : EU._Count < 1000 ? 3 : 4,
    Eh = "0000",
    Ei = this.GetText("Items"),
    Em = this.MainCol;
    function Eo(BR) {
        BR += "";
        return Eh.slice(0, Eg - BR.length) + BR;
    };
    for (var J = EU.firstChild; J;) {
        if (!J.Dx) continue;
        var BY = J.childNodes.length;
        if (!BY) {
            var Ev = J.nextSibling;
            this.DelRow(J);
            J = Ev;
            continue;
        }
        var S = Ei.replace(/\%d/, Eo(BQ + 1)).replace(/\%d/, Eo(BQ + BY));
        if (S != J[Em]) {
            J[Em] = S;
            this.RefreshCell(J, Em);
        }
        BQ += BY;
        J = J.nextSibling;
    }
};
_7m.prototype.Et = function (BL, Eb) {
    var D3 = this.GetSections(),
    DP = D3[0],
    DQ = D3[1];
    for (var j = DP; j < DQ; j++) {
        var B3 = "r" + j,
        Bg = BL[0][B3];
        if (Bg) Bg = Bg.parentNode;
        else for (var i = 0; i < BL.length; i++) if (BL[i][B3]) {
            Bg = BL[i][B3].parentNode;
            break;
        }
        if (Eb && Eb.Dx) Bg = BL[i][B3].nextSibling;
        var Ew = j == 1 && Grids.OnDisplaceRow != null;
        if (Bg) for (var i = 0; i < BL.length; i++) {
            if (!BL[i][B3]) continue;
            var B1 = BL[i].firstChild ? BL[i][B3].nextSibling: null;
            Bg.appendChild(BL[i][B3]);
            if (B1) Bg.appendChild(B1);
            if (Ew) Grids.OnDisplaceRow(this, BL[i]);
        }
    }
};
_7m.prototype.Ex = function (D6, row) {
    var Ey = D6.Url;
    if (Ey.search(/\*/) >= 0) {
        D6 = CopyObject(D6);
        if (row) Ey = Ey.replace(/\*Pos/g, row.Pos != null ? row.Pos: "").replace(/\*id/g, row.id != null ? row.id: "").replace(/\*Rows/g, row.Rows != null ? row.Rows: "");
        for (var i = 0; i < 3; i++) {
            var r1 = new RegExp("\\*SortCols" + i, "g"),
            r2 = new RegExp("\\*SortTypes" + i, "g");
            Ey = Ey.replace(r1, this.AX > i ? this.SortCols[i] : "").replace(r2, this.AX > i ? this.SortTypes[i] & 1 : "");
        }
        D6.Url = Ey;
    }
    return D6;
};
_7m.prototype.DownloadData = function (D6, D7) {
    if (!D6.Url) {
        if (D7) D7( - 2);
        return;
    }
    var Ez = "";
    if (this.ColNames[1] && this.ColNames[1].length && this.XH && this.XF) Ez = this.GetCfgRequest(D6.Format, Ez);
    else if (Ez) Ez = "<Cfg " + Ez + "/>";
    if (Ez) Ez = "<Grid><IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/>" + Ez + "</Grid>";
    this.Communicate2(this.Ex(D6), Ez, D7);
};
_7m.prototype.DownloadPage = function (row, D7) {
    if (Grids.OnDownloadPage && Grids.OnDownloadPage(this, row, D7)) return;
    row = this.E0(row);
    if (row.State > 1) {
        if (D7) D7(0);
        return;
    }
    if (!this.Data.Page.Url) {
        alert(this.GetAlert("ErrPageNoUrl"));
        if (D7) D7( - 1);
        return;
    }
    var BL = new Array(),
    Bg = 0;
    BL[Bg++] = "<Grid><IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/>";
    BL[Bg++] = this.GetCfgRequest(this.Data.Page.Format);
    BL[Bg++] = "<Body><B";
    if (row.Copy) BL[Bg++] = " id='" + row.Copy + "'";
    else if (row.id) BL[Bg++] = " id='" + row.id + "'";
    if (row.Rows) BL[Bg++] = " Rows=\"" + this.DM(row.Rows) + "\"";
    if (row.tagName == "B") {
        if (row.Pos != null) BL[Bg++] = " Pos='" + row.Pos + "'";
        else BL[Bg++] = " Pos='" + this.GetPageNum(row) + "'";
    }
    BL[Bg++] = "/></Body></Grid>";
    this.Communicate2(this.Ex(this.Data.Page, row), BL.join(""), D7, row);
};
_7m.prototype.E1 = function (row, BJ, D7) {
    if (!this.Data.Cell.Url) {
        alert(this.GetAlert("ErrCellNoUrl"));
        if (D7) D7( - 1);
        return;
    }
    var BL = new Array(),
    Bg = 0;
    BL[Bg++] = "<Grid><IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/>";
    BL[Bg++] = this.GetCfgRequest(this.Data.Cell.Format);
    BL[Bg++] = "<Body><B";
    if (row.Copy) BL[Bg++] = " id='" + row.Copy + "'";
    else if (row.id) BL[Bg++] = " id='" + row.id + "'";
    BL[Bg++] = " Col='" + BJ + "'";
    BL[Bg++] = "/></Body></Grid>";
    this.Communicate2(this.Ex(this.Data.Cell, row), BL.join(""), D7, row);
};
_7m.prototype.UploadChanges = function (row, D7) {
    if (this.E2) return this.E2.UploadChanges(row ? row.MasterRow: null, D7);
    if (!this.AutoUpdate || row && (!row.id || Is(row, "NoUpload"))) {
        if (D7) D7(0);
        return;
    }
    if (Grids.OnSave && Grids.OnSave(this, row, true)) {
        if (D7) D7(0);
        return;
    }
    var D8 = this.Data.Upload;
    if (!D8.Url) {
        if (D7) D7( - 2);
        return;
    };
    var T = this;
    this.Communicate2(D8, row ? this.GetChanges(row) : this.GetXmlData(), function (E3) {
        if (E3 >= 0) T.AcceptChanges(row);
        if (Grids.OnAfterSave) Grids.OnAfterSave(T, E3);
        if (D7) D7(E3);
    },
    row);
    return 0;
};
_7m.prototype.ReadData = function (D6, D7) {
    if (Grids.OnReadData && Grids.OnReadData(this, D6, D7)) return;
    var xml = null;
    if (D6.Url) {
        if (this.DownloadData) this.DownloadData(D6, D7);
        else _7w("Ajax");
        return;
    }
    if (D6.Tag) {
        var E4 = this.E5(D6.Tag);
        if (!E4) {
            if (D7) D7( - 3);
        } else xml = CreateXML(E4.value);
    } else if (D6.Data) {
        xml = CreateXML(D6.Data);
    }
    if (!xml) {
        alert(this.GetAlert("ErrXMLFormat") + this.EQ(D6));
        if (D7) D7( - 4);
    } else {
        this.ER(xml);
        if (D7) D7(0);
    }
};
_7m.prototype.E6 = function () {
    this.FocusedPos = null;
    if (this.FRow) {
        this.Focused = this.FRow.id;
        if (this.FRow.Page) {
            if (!this.Focused) this.Focused = this.GetPageNum(this.FRow);
            this.FocusedPos = this.FPagePos;
        }
    } else this.Focused = null;
    this.FocusedCol = this.FCol;
};
_7m.prototype.Reload = function (D8, id) {
    if (this.Rendering || this.Loading) return false;
    if (!Grids.OnReload || !Grids.OnReload(this)) {
        if (!D8) D8 = this.Data;
        if (!D8) return false;
        if (!this.CK(1)) return false;
        this.Loading = 1;
        this.Disable();
        if (this.RefreshDetail) this.RefreshDetail(null, 1);
        var T = this;
        if (this.id != id) delete Grids[this.id];
        Grids.CacheVersion = Math.floor(Math.random() * 1e8);
        setTimeout(function () {
            T.Clear(1);
            TreeGrid(D8, T.MainTag, id, T.Index, T.Ax);
        },
        10);
    }
    return true;
};
_7m.prototype.ActionReload = function () {
    if (!this.E7()) return false;
    return this.Reload();
};
_7m.prototype.ReloadBody = function (D7, E8, reason) {
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, true) == -1) return;
    this.Aw = reason ? reason: "Reload";
    this.E6();
    this.FRow = null;
    this.FCol = null;
    this.Clear(4);
    if (this.RefreshDetail) this.RefreshDetail(null, 1);
    _7d(this.XB);
    this.ClearUndo();
    Grids.CacheVersion = Math.floor(Math.random() * 1e8);
    var T = this;
    this.ReadData(this.Data.Data, function (E3) {
        T.E9(E3, D7, E8);
    });
};
_7m.prototype.E9 = function (E3, D7, E8) {
    var D8 = this.Data.Data;
    if (E3 < 0) {
        this.HideMessage();
        if (!this.XB.firstChild) this.FA();
        this.Render();
        if (D7) D7(E3);
        return;
    }
    if (D8.Bonus) this.ER(CreateXML(D8.Bonus));
    this.FB();
    this.FC(1);
    if (this.LoadCfg) this.LoadCfg();
    this.FD();
    if (E8) {
        this.Render();
    } else {
        if (this.UpdatePager) this.UpdatePager();
        this.UpdateBody();
        this.FE();
        if (this.FRow) {
            var BG = this.FRow;
            this.FRow = null;
            this.Focus(BG, this.FCol, this.FPagePos, 1);
        }
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) if (CE[i].Kind != "User") this.RefreshRow(CE[i]);
        for (var J = this.XS.firstChild; J; J = J.nextSibling) this.RefreshRow(J);
        this.HideMessage();
        this.ShowPages();
    };
    if (D7) D7(E3);
};
_7m.prototype.ActionValidate = function () {
    if (!this.Validate) return true;
    if (!this.E7()) return false;
    var R = new Array(),
    C = new Array(),
    Bg = 0,
    T = this,
    Cr = this.Validate;
    function FG(J, cols) {
        for (var BY in cols) {
            var FH = T.FI(J, BY, 0);
            if (FH) {
                var V = T.FJ(J, BY);
                try {
                    if (V.Value.search(new RegExp(FH, "")) == -1) {
                        R[Bg] = J;
                        C[Bg] = BY;
                        Bg++;
                    }
                } catch(B) {
                    alert("Wrong result mask at [" + J.id + "," + BY + "]");
                }
            }
        }
    };
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
        if (Cr & 2 || J.Added) FG(J, this.Cols);
    }
    if (Cr & 2) {
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) if (CE[i].Kind == "Data" || !CE[i].Kind) FG(CE[i], this.Cols);
        for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Space") FG(J);
    }
    if (Bg) {
        if (Grids.OnValidateError) {
            var Cu = Grids.OnValidateError(this, R, C);
            if (Cu == 1) return;
            if (Cu == 2) Bg = 0;
        }
        if (Bg) {
            var Cu = true,
            text = "";
            if (! (Cr & 32)) text = Get(R[0], C[0] + "ResultText");
            text = text ? "\n" + (this.ValidateText ? this.ValidateText: "") : this.ValidateText;
            if (text) {
                if (! (Cr & 4)) alert(text);
                if (Cr & 4 && confirm(text)) Cu = false;
            }
            if (Cu) {
                if (Cr & 24) this.Focus(R[0], C[0]);
                if (Cr & 16) this.StartEdit(R[0], C[0]);
                if (! (Cr & 64)) {
                    for (var i = 0; i < Bg; i++) {
                        var B = Get(R[0], C[0] + "ResultText");
                        if (!B) B = this.GetAlert("Invalid");
                        R[i][C[i] + "Error"] = B;
                        this.ColorCell(R[i], C[i]);
                    }
                }
                return false;
            }
        }
    }
    return true;
};
_7m.prototype.Save = function (event) {
    var D8 = this.Data.Upload;
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, true) == -1) {
        if (event) CancelEvent(event);
        return false;
    }
    for (var i = 0; i < Grids.length; i++) {
        var G = Grids[i];
        if (G && G != this && G.E2 == this && G.EditMode && G.EndEdit(G.FRow, G.FCol, true) == -1) {
            if (event) CancelEvent(event);
            return false;
        }
    }
    if (event && this.ActionValidate && !this.ActionValidate()) return false;
    if (!Grids.OnSave || !Grids.OnSave(this)) {
        if (D8.Url) {
            var T = this;
            this.Communicate2(D8, this.GetXmlData(), function (E3) {
                if (E3 >= 0) T.AcceptChanges();
                T.HideMessage();
                if (T.Paging) T.ShowPages();
                if (Grids.OnAfterSave) Grids.OnAfterSave(T, E3);
            });
            return;
        } else if (D8.Tag) {
            var E4 = this.E5(D8.Tag);
            if (E4) {
                this.E6();
                var BR = this.GetXmlData();
                E4.value = D8.Xml - 0 ? BR: BR.replace(/\&/g, "&amp;").replace(/\</g, "&lt;").replace(/>/g, "&gt;");
            }
            var CE = this.FK(E4);
            if (CE) {
                CE.method = "POST";
                if (this.Ac && !this.Ac(event)) {
                    this.HideMessage();
                    return false;
                }
                if (!event) CE.submit();
                return true;
            }
        } else if (D8.Data) {
            this.ER(D8.Data);
            var ES = this.IO;
            if (ES && ES.Message) alert(ES.Message);
            if (!ES || ES.Result == null || ES.Result >= 0) this.AcceptChanges();
        }
    } else if (event) return false;
    this.HideMessage();
};
_7m.prototype.ActionSave = function () {
    if (!this.E7()) return false;
    return this.Save();
};
function _7z() {
    for (var i = 0; i < Grids.length; i++) {
        var G = Grids[i];
        if (G && G.Data.Check.Url && G.Data.Check.Interval && !G.Loading && !G.Rendering && G.FL != null) {
            if (++G.FL >= G.Data.Check.Interval) {
                G.FL = 0;
                G.CheckForUpdates();
            }
        }
    }
};
_7m.prototype.CheckForUpdates = function (D7) {
    if (this.FM) return;
    var T = this,
    D8 = this.Data.Check;
    this.FM = 1;
    var Ez = "<Grid><IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/></Grid>";
    this.Communicate2(D8, Ez, function (E3) {
        if (E3 < 0) {
            if (confirm(T.GetAlert("ErrCheck"))) T.Data.Check.Interval = 0;
        }
        T.FM = 0;
        if (D7) D7(E3);
    });
};
_7m.prototype.FN = function (value, name) {
    var BZ = (value + "").match(/\"[^\"]*\"|\'[^\']*\'|[a-zA-Z_][a-zA-Z_0-9]*|[^\'\"a-zA-Z_\s]+/g);
    if (!BZ) return null;
    for (var j = 0; j < BZ.length; j++) {
        if (BZ[j].search(/^[\'\"]/) == 0) continue;
        if (BZ[j].search(/^[a-zA-Z_]/) == 0) {
            var BR = BZ[j].toUpperCase();
            if (BR == "AND") BZ[j] = "&&";
            else if (BR == "OR") BZ[j] = "||";
            else if (BZ[j] != "true" && BZ[j] != "false" && BZ[j] != "this" && (!j || BZ[j - 1].search(/\.$/) < 0)) {
                var BG = BZ[j],
                FO = false;
                var FP = this["Action" + BG];
                if (!FP && BG.search(/F$/) > 0) {
                    BG = BG.slice(0, BG.length - 1);
                    FP = this["Action" + BG];
                    if (FP) {
                        if (!BZ[j + 1] || BZ[j + 1].indexOf('(') != 0) BG += "(1)";
                        else BZ[j + 1] = "(1," + BZ[j + 1].slice(1);
                        FO = true;
                    } else BG = BZ[j];
                }
                if (!FP && !window[BG]) {
                    if (this.DebugActions) alert("Unknown action '" + BG + "':\n" + name + " = " + value);
                    this["Action" + BG] = new Function("return false;");
                    FP = true;
                }
                if (FP) {
                    if (!FO && (!BZ[j + 1] || BZ[j + 1].indexOf('(') != 0)) BG += "()";
                    BZ[j] = "this.Action" + BG;
                }
            }
        }
    }
    BZ = BZ.join("");
    try {
        BZ = new Function("return " + BZ.replace(/\\/g, "\\\\"));
    } catch(B) {
        if (this.DebugActions) alert("Wrong action syntax:\n" + name + " = " + value);
    };
    return BZ;
};
_7m.prototype.FQ = function (FR, Cs, D4) {
    var FS = 0,
    i, j, Bg;
    for (Bg = 0; Cs.charCodeAt(Bg) <= 32; Bg++);
    if (Cs.charAt(Bg) != Cs.charAt(Bg + 1) && !Grids.A9[Cs.charCodeAt(Bg + 1)]) {
        FS = Cs.charAt(Bg);
        Bg++;
    }
    var BL = Cs.split(Cs.charAt(Bg)),
    FT = BL.length;
    var F = this.Par[BL[1]],
    Bg = 2;
    if (F) for (i = 0; i < F.length; i++) {
        var FU = BL[Bg++];
        FR[F[i]] = (FU - 0) + "" == FU ? FU - 0 : FU;
    }
    if (FT > Bg) {
        F = this.Par[BL[Bg++]];
        if (!F) return;
        var BQ = BL[Bg++] - 0,
        FV = F.length,
        J;
        for (j = 0; j < Bg; j++) BL[j] = null;
        if (_7R) {
            if (!FV) {
                BL = new Array();
                for (j = 0; j < BQ; j++) BL[Bg++] = "<BR>";
            } else if (FS) {
                for (var FT = BL.length; Bg < FT; Bg++) {
                    var H = BL[Bg].split(FS),
                    FW = H.length,
                    Bc = 1;
                    while (Bc < FW) {
                        H[Bc] = "<BR " + F[0] + "=\"" + H[Bc] + "\"";
                        Bc++;
                        for (i = 1; i < FV; i++) {
                            H[Bc] = " " + F[i] + "=\"" + (H[Bc] + "").replace(/\&/g, "&amp;").replace(/\</g, "&lt;").replace(/\"/g, "&quot;") + "\"";
                            Bc++;
                        }
                        H[Bc - 1] += ">";
                    }
                    BL[Bg] = H.join("");
                }
            } else for (j = 0; j < BQ; j++) {
                BL[Bg] = "<BR " + F[0] + "=\"" + BL[Bg] + "\"";
                Bg++;
                for (i = 1; i < FV; i++) {
                    BL[Bg] = " " + F[i] + "=\"" + (BL[Bg] + "").replace(/\&/g, "&amp;").replace(/\</g, "&lt;").replace(/\"/g, "&quot;") + "\"";
                    Bg++;
                }
                BL[Bg - 1] += ">";
            }
            while (Bg < BL.length) BL[Bg++] = null;
            FR.innerHTML = BL.join("");
        } else if (BQ) {
            var FX = _7f.createElement(D4),
            J = FX;
            FX.parentNode = FR;
            FR.firstChild = FX;
            for (j = 1; j < BQ; j++) {
                J = _7f.createElement(D4);
                J.parentNode = FR;
                FX.nextSibling = J;
                J.previousSibling = FX;
                FX = J;
            }
            FR.childNodes.length = BQ;
            FR.lastChild = J;
            J = FR.firstChild;
            if (FS && FV) {
                for (var FT = BL.length; Bg < FT; Bg++) {
                    var H = BL[Bg].split(FS),
                    FW = H.length,
                    Bc = 1;
                    while (Bc < FW) {
                        for (i = 0; i < FV; i++) J[F[i]] = H[Bc++];
                        J = J.nextSibling;
                    }
                }
            } else if (FV) for (j = 0; j < BQ; j++) {
                for (i = 0; i < FV; i++) J[F[i]] = BL[Bg++];
                J = J.nextSibling;
            }
        }
    }
};
_7m.prototype.FY = function (FR, FZ, Fa, D4) {
    if (!Fa) {
        var BL = FZ.attributes,
        FT = BL.length;
        for (var i = 0; i < FT; i++) {
            var a = BL[i],
            FU = a.nodeValue;
            FR[a.nodeName] = (FU - 0) + "" == FU ? FU - 0 : FU;
        }
    }
    if (!D4) D4 = "I";
    var T = new Array(),
    Fb = 0;
    for (var J = FZ.firstChild; J; J = J.nextSibling) {
        if (J.nodeType == 3) T[Fb++] = J.nodeValue;
        else if (J.nodeType == 1) {
            if (Fb) {
                var Bh = T.join("");
                this.FQ(FR, Bh, D4);
                if (D4 == "B" && Bh.search(/^\s*$/) < 0) FR.Fc = 1;
                Fb = 0;
            }
            if (J.nodeName == D4) {
                if (FR.appendChild) {
                    var B1 = _7f.createElement(D4);
                    this.FY(B1, J);
                    FR.appendChild(B1);
                } else {
                    if (!FR.Children) FR.Children = new Array();
                    var B1 = new Object();
                    this.FY(B1, J);
                    FR.Children[FR.Children.length] = B1;
                }
            } else {
                var name = J.getAttribute("N");
                BL = J.attributes;
                FT = BL.length;
                for (var i = 0; i < FT; i++) {
                    var a = BL[i],
                    Fd = a.nodeName,
                    FU = a.nodeValue;
                    if (Fd != "N") FR[name + (Fd == "V" ? "": Fd)] = (FU - 0) + "" == FU ? FU - 0 : FU;
                }
            }
        }
    }
    if (Fb) {
        var Bh = T.join("");
        this.FQ(FR, Bh, D4);
        if (D4 == "B" && Bh.search(/^\s*$/) < 0) FR.Fc = 1;
    }
};
_7m.prototype.Fe = function (FR, FZ) {
    for (var J = FZ.firstChild; J; J = J.nextSibling) {
        if (J.nodeType != 1) continue;
        var id = J.getAttribute("id"),
        CZ;
        if (id) {
            for (CZ = FR.firstChild; CZ; CZ = CZ.nextSibling) if (CZ.id == id) break;
        } else CZ = null;
        if (CZ) this.FY(CZ, J);
        else {
            CZ = _7f.createElement("I");
            this.FY(CZ, J);
            var Ff = J.tagName.toLowerCase();
            if (Ff == "space") Ff = "Space";
            else if (Ff == "group") Ff = "Group";
            else if (Ff == "simplepager") Ff = "Pager";
            else if (Ff == "search") Ff = "Search";
            else if (Ff == "filter") Ff = "Filter";
            else if (Ff == "user") Ff = "User";
            else Ff = CZ.Kind;
            CZ.Kind = Ff;
            FR.appendChild(CZ);
        }
    }
};
_7m.prototype.ER = function (xml, page, type) {
    function Fg(Fh) {
        var a = Fh.nodeValue;
        if ((a - 0) + "" == a) return a - 0;
        return a;
    };
    function Fi(D4, xml) {
        var BL = xml.attributes,
        FT = BL.length;
        for (var i = 0; i < FT; i++) {
            var a = BL[i],
            BZ = a.nodeValue;
            D4[a.nodeName] = (BZ - 0) + "" == BZ ? BZ - 0 : BZ;
        }
    };
    function Fj(D4, xml) {
        var BL = xml.attributes,
        FT = BL.length;
        for (var i = 0; i < FT; i++) {
            var a = BL[i],
            BZ = a.nodeValue,
            B3 = a.nodeName;
            if (B3 == "AlternateRepeat" || B3 == "FoundAbsolute") D4[B3] = BZ - 0;
            else {
                if (!BZ) D4[B3] = 0;
                else {
                    BZ = (BZ + "").split(",");
                    BZ = (BZ[0] << 16) + (BZ[1] << 8) + (BZ[2] - 0);
                    if (! (BZ >= -16777215 && BZ <= 16777215)) alert("In grid " + this.id + " the color '" + B3 + "' has wrong value");
                    else D4[B3] = BZ;
                    D4[B3 + "ByStyle"] = null;
                }
            }
        }
    };
    var C, i, J, BL, P, a, FT;
    if (!xml) return;
    if (typeof(xml) == "string") {
        xml = CreateXML(xml);
        if (!xml) return;
    }
    if (typeof(xml) != "object") return;
    if (xml.documentElement) xml = xml.documentElement;
    var Fk = 0;
    this.IO = new Object;
    if (!this.XHeader) this.XHeader = _7f.createElement("U");
    if (!this.XH) this.XH = _7f.createElement("U");
    if (!this.XF) this.XF = _7f.createElement("U");
    if (!this.XB) this.XB = _7f.createElement("U");
    if (!this.XS) this.XS = _7f.createElement("U");
    for (var R = xml.firstChild; R; R = R.nextSibling) if (R.nodeType == 1) {
        var Fl = R.nodeName;
        if (Fl == "IO") {
            Fi(this.IO, R);
            continue;
        }
        if (type & 1) continue;
        if (Fl == "Cfg") {
            Fi(this, R);
            continue;
        }
        if (Fl == "Actions") {
            var BL = R.attributes,
            FT = BL.length;
            for (i = 0; i < FT; i++) {
                var a = BL[i],
                B3 = a.nodeName,
                BZ = a.nodeValue;
                if (B3.indexOf("KeyCodes") == 0) {
                    BZ = BZ.replace(/\s/g, "").split(",");
                    for (var j = 0; j < BZ.length; j++) {
                        var x = BZ[j].split("=");
                        if (x.length != 2) continue;
                        this.Ar[x[1]] = x[0];
                    }
                } else if (B3.search(/Parent|Size/) >= 0) this.Mouse[B3] = BZ;
                else {
                    this.Actions[B3] = this.FN(BZ, B3);
                }
            }
        }
        if (Fl == "Styles") {
            P = this.Styles;
            Fi(P, R);
            for (J = R.firstChild; J; J = J.nextSibling) {
                if (J.nodeType != 1) continue;
                a = J.getAttribute("Name");
                var D8 = P[a];
                if (!D8) {
                    D8 = new Object;
                    P[a] = D8;
                }
                Fi(D8, J);
                for (var Fm = J.firstChild; Fm; Fm = Fm.nextSibling) {
                    var Fn = Fm.nodeName;
                    if (Fn == "Cfg" || Fn == "Img") {
                        if (!D8[Fn]) D8[Fn] = new Object();
                        Fi(D8[Fn], Fm);
                    }
                    if (Fn == "Colors") {
                        if (!D8[Fn]) D8[Fn] = new Object();
                        Fj(D8[Fn], Fm);
                    }
                }
            }
            P.Changed = true;
            continue;
        }
        if (Fl == "Img") {
            this.Styles.Changed = true;
            Fi(this.Img, R);
            continue;
        }
        if (Fl == "Colors") {
            this.Styles.Changed = true;
            Fj(this.Al, R);
            continue;
        }
        if (Fl == "Toolbar") {
            Fi(this.Toolbar, R);
            continue;
        }
        if (Fl == "MenuCfg") {
            Fi(this.MenuCfg, R);
            continue;
        }
        if (Fl == "Pager") {
            Fi(this.Pager, R);
            continue;
        }
        if (Fl == "Def") {
            P = this.Def;
            for (J = R.firstChild; J; J = J.nextSibling) {
                if (J.nodeType != 1) continue;
                a = J.getAttribute("Name");
                var D8 = P[a];
                if (!D8) {
                    D8 = new Object;
                    P[a] = D8;
                }
                this.FY(D8, J);
                if (!D8.Def && a != 'R' && a != 'C') D8.Def = "R";
                D8.Fo = 0;
            }
            continue;
        }
        if (Fl == "Panel") {
            C = this.Cols[Fl];
            if (!C) {
                C = new _7i();
                C.Index = Fl;
                this.Cols[Fl] = C;
            }
            BL = R.attributes,
            FT = BL.length;
            for (var i = 0; i < FT; i++) {
                a = BL[i];
                C[a.nodeName] = Fg(a);
            }
            C.Pos = 0;
            var Cq = this.ColNames[0];
            if (Cq.length && Cq[0] != Fl) {
                for (i = Cq.length; i >= 0; i--) {
                    Cq[i] = Cq[i - 1];
                    Cq[i].Pos++;
                }
            }
            Cq[0] = Fl;
            C.K = 0;
            C.Name = C.Index;
            continue;
        }
        var Ce = -1;
        if (Fl == "LeftCols") Ce = 0;
        else if (Fl == "Cols") Ce = 1;
        else if (Fl == "RightCols") Ce = 2;
        if (Ce >= 0) {
            var Cq = this.ColNames[Ce];
            for (J = R.firstChild; J; J = J.nextSibling) {
                if (J.nodeType != 1) continue;
                var name = J.getAttribute("Name");
                if (!name) continue;
                C = this.Cols[name];
                if (!C) {
                    C = new _7i();
                    C.Index = name;
                    this.Cols[C.Index] = C;
                    var D8 = this.Def["C"];
                    if (D8) for (var j in D8) C[j] = D8[j];
                    C.Pos = Cq.length;
                    Cq[Cq.length] = C.Index;
                    C.K = Ce;
                    C.Name = C.Index;
                }
                var BL = J.attributes;
                for (var j = 0; j < BL.length; j++) C[BL[j].nodeName] = Fg(BL[j]);
                C.Ci = C.Width == null;
            }
            continue;
        }
        if (Fl == "Par") {
            for (J = R.firstChild; J; J = J.nextSibling) {
                if (J.nodeType == 1) {
                    var DF = J.getAttribute("List");
                    this.Par[J.getAttribute("Name")] = DF ? DF.split(",") : new Array();
                }
            }
            continue;
        }
        if (Fl == "Header") {
            this.FY(this.XHeader, R);
            continue;
        }
        if (Fl == "Root") {
            this.FY(this.XHeader, R);
            continue;
        }
        if (Fl == "Head") {
            this.Fe(this.XH, R);
            continue;
        }
        if (Fl == "Solid") {
            var J = this.XH.lastChild;
            this.Fe(this.XH, R);
            for (J = J ? J.nextSibling: this.XH.firstChild; J; J = J.nextSibling) if (!J.Kind) J.Kind = "Space";
            continue;
        }
        if (Fl == "Foot") {
            this.Fe(this.XF, R);
            continue;
        }
        if (Fl == "Body") {
            var P = this.XB,
            H = R;
            if (!page) {
                P.Fc = 0;
                this.FY(P, H, true, "B");
            } else {
                if (page.Pos == null && !page.id) page.Pos = this.GetPageNum(page);
                for (J = H.firstChild; J; J = J.nextSibling) {
                    if (J.nodeType != 1) continue;
                    var B1 = null;
                    var id = J.getAttribute("id"),
                    Rows = J.getAttribute("Rows"),
                    Pos = J.getAttribute("Pos");
                    if (id && (id == page.id || id == page.Copy) || Rows && Rows == page.Rows || Pos != null && Pos == page.Pos) B1 = page;
                    if (B1) this.FY(B1, J, B1.tagName != J.tagName);
                }
            };
            continue;
        }
        if (Fl == "Changes") {
            var H = _7f.createElement("B");
            this.FY(H, R);
            this.Fp(H);
            continue;
        }
        if (Fl == "Lang") {
            for (J = R.firstChild; J; J = J.nextSibling) {
                if (J.nodeType != 1) continue;
                P = this.Lang[J.tagName];
                if (!P) {
                    P = new Object;
                    this.Lang[J.tagName] = P;
                }
                BL = J.attributes;
                FT = BL.length;
                for (i = 0; i < FT; i++) {
                    a = BL[i];
                    P[a.nodeName] = a.nodeValue;
                }
            }
            _7o = this.Lang["Format"];
            var CE = ["LongDayNames", "ShortDayNames", "Day2CharNames", "Day1CharNames", "DayNumbers", "LongMonthNames", "LongMonthNames2", "ShortMonthNames", "Quarters", "Halves"];
            for (var i = 0; i < CE.length; i++) if (typeof(_7o[CE[i]]) == "string") _7o[CE[i]] = _7o[CE[i]].split(",");
            if (_7o.NaN == null) _7o.NaN = "NaN";
            if (_7o.NaD == null) _7o.NaD = "NaN";
            continue;
        }
    }
    if (this.IO.Session != null) this.SetSession(this.IO.Session);
};
_7m.prototype.AddDataFromServer = function (xml) {
    this.ER(xml);
    if (this.Styles.Changed) this.SetStyle();
    this.SetScrollBars();
};
_7m.prototype.SetSession = function (Fq) {
    if (Fq == "") Fq = null;
    this.Data.Session = Fq;
    if (Fq == null) Fq = "";
    if (this.SaveSession == 1 && Grids.C3) Grids.C3(this.id + "&" + (this.SessionId ? this.SessionId: "Session"), Fq ? escape(Fq) : "");
    else {
        var Fr = this.SaveSession ? this.SaveSession: this.id + "_" + (this.SessionId ? this.SessionId: "Session");
        var E = GetElem(Fr);
        if (!E) {
            E = document.createElement("input");
            E.type = "hidden";
            E.id = Fr;
            document.body.appendChild(E);
        }
        E.value = Fq;
    }
};
_7m.prototype.Fp = function (Fs) {
    var BI = Fs["Update"];
    if (BI == null) BI = 1;
    function Ft(row, Fu) {
        if (!T.Copying) return;
        var Fv = row.id;
        for (var J = T.GetFirst(); J; J = T.GetNext(J)) if (J.Copy == Fu) J.Copy = Fv;
        if (T.Cols[T.Av]) T.RefreshCell(row, T.Av);
    };
    function Fw(J, B3, C5) {
        if (J[C5]) {
            var Fx = J[C5];
            Fx = (Fx + "").split(",");
            Fx = (Fx[0] << 16) + (Fx[1] << 8) + (Fx[2] - 0);
            B3[C5] = Fx;
            return 1;
        }
        return 0;
    };
    var Fy = ["CanEdit", "CanFocus", "CanDelete", "CanSelect", "CanExpand", "Selected", "Expanded"];
    var Fz = ["CanEdit", "CanFocus", "Error", "Selected", "Defaults", "EditEnum", "EnumKeys", "Tip"];
    var F0 = ["ClassOuter", "ClassInner", "HtmlPrefix", "HtmlPostfix", "Visible", "ToolTip", "Error", "Type", "Format", "EditFormat", "EditMask", "ResultMask", "IntFormat", "Button", "ButtonText", "Enum"];
    var Dh = this.AutoUpdate,
    T = this;
    this.AutoUpdate = false;
    var F1 = this.EditMode,
    EI = 0,
    D = new Array();
    for (var J = Fs.firstChild; J; J = J.nextSibling) {
        var B3, Bg = J.Parent;
        if (Bg != null) Bg = (Bg - 0) >= 0 ? _7c(this.XB, Bg - 0) : this.GetRowById(Bg);
        if (J.Added || J.Moved) {
            var F2 = J.Next ? this.GetRowById(J.Next) : null;
            if (F2 && Bg && F2.parentNode != Bg) F2 = null;
            var Ec = J.Prev ? this.GetRowById(J.Prev) : null;
            if (Ec && Bg && Ec.parentNode != Bg) Ec = null;
            if (J.Next && J.Prev && Ec && !F2) F2 = Ec.nextSibling;
        }
        if (J.Added) {
            if (Bg && Bg.State < 2) continue;
            if (J.id) {
                var F3 = this.GetRowById(J.id);
                if (F3) {
                    if (F3.Added) {
                        this.F4(F3);
                        Ft(F3, J.id);
                    } else {}
                }
            }
            B3 = this.AddRow(Bg, F2, 0, J.id ? 1 : 0, J.Def, null, 1);
            if (B3) {
                if (J.id) B3.id = J.id;
                for (var i = 0; i < Fy.length; i++) if (J[Fy[i]] != null) B3[Fy[i]] = J[Fy[i]];
                for (var BY in this.Cols) {
                    if (J[BY]) B3[BY] = J[BY];
                    for (var i = 0; i < F0.length; i++) if (J[BY + F0[i]] != null) B3[BY + F0[i]] = J[BY + F0[i]];
                    for (var i = 0; i < Fz.length; i++) if (J[BY + Fz[i]] != null) B3[BY + Fz[i]] = J[BY + Fz[i]];
                    Fw(J, B3, BY + "Color");
                }
                Fw(J, B3, "Color");
                if (J.Copy) {
                    var CC = this.GetRowById(J.Copy);
                    while (CC && CC.Copy) CC = this.GetRowById(CC);
                    if (CC) {
                        B3.Copy = CC.id;
                        B3.Count = CC.State >= 2 ? CC.childNodes.length: CC.Count;
                        B3.State = 0;
                    }
                }
                B3.Def = J.Def ? J.Def: B3.Def.Name;
                this.F5(B3, true);
                var FP = Bg && !Bg.Expanded && !Bg.Page && Bg.firstChild == B3 && Bg.lastChild == B3;
                if (FP) {
                    B3.Visible = 1;
                    this.Expand(Bg);
                    B3.Visible = 0;
                }
                this.ShowRow(B3, 0, 0, 1);
                F6 = 1;
                if (FP) this.Collapse(Bg);
                this.F7(B3, 1);
            }
        } else {
            B3 = this.GetRowById(J.id);
            if (B3) {
                if (J.Deleted) {
                    if (this.DeleteRowT) this.DeleteRowT(B3, 2);
                } else {
                    if (J.Moved) {
                        this.MoveRow(B3, Bg, F2, true);
                    }
                    if (J.Selected != null && J.Selected != B3.Selected) {
                        if (J.Selected == 2) B3.Selected = 2;
                        else this.SelectRow(B3);
                    }
                    if (J.CanSelect != null) {
                        B3.CanSelect = J.CanSelect;
                        this.RefreshCell(B3, "Panel");
                    }
                    if (J.Expanded != null && J.Expanded != B3.Expanded) {
                        if (J.Expanded) this.Expand(B3);
                        else this.Collapse(B3);
                    }
                    if (J.F8 != null) {
                        B3.F8 = J.F8;
                        this.Es(B3);
                    }
                    if (J.CanDelete != null) {
                        B3.CanDelete = J.CanDelete;
                        this.RefreshCell(B3, "Panel");
                    }
                    var color = 0;
                    if (J.CanEdit != null) {
                        B3.CanEdit = J.CanEdit;
                        color = 1;
                    }
                    if (J.CanFocus != null) {
                        B3.CanFocus = J.CanFocus;
                        color = 1;
                    }
                    if (J.Color != null) {
                        Fw(J, B3, "Color");
                        color = 1;
                    }
                    if (color) this.ColorRow(B3);
                    for (var BY in this.Cols) {
                        var BZ = J[BY],
                        F9 = 0,
                        GA = 0;
                        for (var i = 0; i < F0.length; i++) if (J[BY + F0[i]] != null) {
                            F9 = 1;
                            B3[BY + F0[i]] = J[BY + F0[i]];
                        }
                        for (var i = 0; i < Fz.length; i++) if (J[BY + Fz[i]] != null) {
                            GA = 1;
                            B3[BY + Fz[i]] = J[BY + Fz[i]];
                        }
                        GA |= Fw(J, B3, BY + "Color");
                        if (GA) this.ColorCell(B3, BY);
                        if (BZ != null || F9) {
                            if (F1 && this.FRow == B3 && this.FCol == BY) {
                                this.EndEdit(B3, BY);
                                EI = 1;
                            }
                            if (BZ != null) this.SetString(B3, BY, BZ + "");
                            this.RefreshCell(B3, BY);
                            if (EI) {
                                this.StartEdit(B3, BY);
                                EI = 0;
                            }
                        }
                    }
                    B3.Def = J.Def ? J.Def: B3.Def.Name;
                    this.F5(B3, true);
                    if (J.NewId) {
                        B3.id = J.NewId;
                        Ft(B3, J.id);
                    }
                }
            }
        };
        if (B3) {
            D[D.length] = B3;
            if (Grids.OnUpdateRow) Grids.OnUpdateRow(this, B3, J);
        }
    }
    var Ee = this.Cols[this.MainCol],
    GB = this.GC,
    GD = this.GE,
    GF = this.GG,
    F6 = 0,
    GH = BI & 4 && (GB || GD || GF || Grids.OnRowFilter);
    for (j = 0; j < D.length; j++) {
        var B3 = D[j];
        if (BI & 1) this.AcceptChanges(B3);
        if (GH) {
            this.Recalculate(B3, null, 0);
            var R = new Array();
            for (var Fy = B3; ! Fy.Page; Fy = Fy.parentNode) R[R.length] = Fy;
            for (var i = R.length - 1; i >= 0; i--) {
                var Fy = R[i];
                if (Fy.Kind == "Data" && (!Fy.Deleted || this.ShowDeleted)) {
                    var BG = Get(Fy, "CanFilter") & 1 && (GB && !GB(Fy) || GD && !GD(Fy) || GF && GF(Fy));
                    if (Grids.OnRowFilter) BG = !Grids.OnRowFilter(this, Fy, !BG);
                    if (BG) {
                        if (!Fy.Filtered) this.HideRow(Fy, false);
                    } else if (Fy.Filtered) {
                        if (this.ShowRow(Fy, 0, 0, 1) == 0) {
                            if (!Fy.nextSibling) for (var Ev = Fy.parentNode.firstChild; Ev; Ev = Ev.nextSibling) this.Ef(Ev);
                        } else if (Ee) this.Ef(Fy, 1, Ee);
                        F6 = 1;
                    }
                    Fy.Filtered = BG;
                    if (BG) break;
                }
            }
            if (this.FilterEmpty) {
                for (var Fy = B3; ! Fy.Page; Fy = Fy.parentNode) {
                    if (Fy.Filtered) continue;
                    if ((Get(Fy, "CanFilter") & 2 || Fy.Dx) && !this.HasChildren(Fy)) {
                        if (!Fy.Deleted || this.ShowDeleted) this.HideRow(Fy);
                        Fy.Filtered = 1;
                    }
                }
            }
        }
        if (BI & 2) this.Recalculate(B3, null, 1);
        if (BI & 8 && this.SearchAction) {
            var EI = this.SearchType;
            if (EI == null) EI = 176;
            var CE = this.GI(this.SearchExpression, EI);
            var Method = CE[1];
            CE = CE[0];
            switch (this.SearchAction) {
            case "Select":
                if (!Is(B3, "CanSelect")) break;
                var GJ = CE(B3);
                GJ = typeof(GJ) == "string" || GJ > 0;
                if (B3.Selected && !GJ) this.SelectRow(B3);
                else if (!B3.Selected && GJ) {
                    this.SelectRow(B3);
                    if (EI & 8) this.ExpandParents(B3);
                }
                break;
            case "Mark":
                var Color = this.Al["Found" + this.GK];
                if (EI & 1 && Method == 1) {
                    var GJ = CE(B3);
                    while (GJ && typeof(GJ) == "string") {
                        B3[GJ + "MarkColor"] = Color;
                        this.ColorCell(B3, GJ);
                        if (EI & 8) this.ExpandParents(B3);
                        GJ = CE(B3, GJ);
                    }
                } else {
                    var GJ = CE(B3) > 0;
                    if (GJ) {
                        B3.Dy = Color;
                        this.ColorRow(B3);
                        if (EI & 8) this.ExpandParents(B3);
                    }
                };
                break;
            }
        }
    }
    this.AutoUpdate = Dh;
    if (F6) this.SetScrollBars();
};
_7m.prototype.DM = function (DD) {
    if (typeof(DD) != "string" || DD.search(/[\&\"\<\>\n]/) < 0) return DD;
    return DD.replace(/\&/g, "&amp;").replace(/\"/g, "&quot;").replace(/\</g, "&lt;").replace(/\>/g, "&gt;").replace(/\n/g, "&#x0A;");
};
_7m.prototype.GL = function (row) {
    var J = row.nextSibling;
    while (J && J.Deleted) J = J.nextSibling;
    return J;
};
_7m.prototype.GM = function (row) {
    var J = row.previousSibling;
    while (J && J.Deleted) J = J.previousSibling;
    return J;
};
_7m.prototype.C2 = function (row) {
    var S = "",
    C = this.Cols;
    if (row.Deleted) S = "D";
    else if (row.Added) {
        var Bj = row.Def,
        EU = row.parentNode,
        GN = this.GL(row),
        GO = this.GM(row),
        GP = Get(row, "Par");
        S = "A&" + (GP ? GP: "") + "&" + Bj.Name + "&" + (EU.id ? EU.id: EU.Pos != null ? EU.Pos: "") + "&" + (GN && GN.id ? GN.id: "") + "&" + (row.Copy ? row.Copy: "");
        for (var BY in C) {
            if (row[BY] != null && row[BY] != Bj[BY] && !C[BY].Formula && (!row.Calculated || !row[BY + "Formula"]) && typeof(row[BY]) != "function") {
                S += "&" + BY + "&" + escape(row[BY]);
            }
        }
    } else if (row.Changed || row.Moved) {
        if (row.Changed) S = "C";
        if (row.Moved) {
            var Bj = row.Def,
            EU = row.parentNode,
            GN = this.GL(row),
            GO = this.GM(row);
            S = "M&" + row.Moved + "&" + (EU.id ? EU.id: EU.Pos != null ? EU.Pos: "") + "&" + (GN && GN.id ? GN.id: "") + "'";
        }
        if (row.Changed) {
            if (row.Cells) {
                for (var i = 0; i < row.Cells.length; i++) if (row[row.Cells[i] + "Changed"]) S += "&" + BY + "&" + escape(row[row.Cells[i]]);
            } else {
                for (var BY in C) if (row[BY + "Changed"]) S += "&" + BY + "&" + escape(row[BY]);
            }
        }
    }
    return S;
};
_7m.prototype.DO = function (row, BJ, BR) {
    if (this.DateStrings && this.GetType(row, BJ) == "Date") {
        if (!BR) return "";
        var D8 = DateToString(BR, this.DateStrings == 2 ? "yyyy-MM-dd HH:mm:ss": (this.DateStrings == 1 ? null: this.DateStrings), this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "");
        return D8;
    }
    if (this.EnumKeys && this.GetType(row, BJ) == "Enum") {
        if (BR == null) BR = 0;
        var GS = this.GT(row, BJ);
        if (GS) {
            if (BR >= 0) return GS[BR + 1];
        } else if (this.EnumKeys == 2) {
            GS = this.GetEnum(row, BJ);
            if (GS) {
                BR = GS[BR];
                if (BR == null) BR = "";
            }
        }
    }
    return BR;
};
_7m.prototype.GU = function (row, DG, GV, GW) {
    var S = "",
    C = this.Cols,
    DI = DG == "dtd",
    T = this;
    if (Is(row, "NoUpload")) return "";
    function GX(row, BY) {
        if (BY == "id") return;
        var DN = GW ? Get(row, BY) : row[BY];
        if (DN == null) DN = "";
        DN = T.DO(row, BY, DN);
        if (DI) S += "<U N='" + BY + "' V=\"" + T.DM(DN) + "\"/>";
        else S += " " + BY + "=\"" + T.DM(DN) + "\"";
    };
    if (row.Deleted) S = "<I id=\"" + this.DM(row.id) + "\" Deleted='1'" + (row.Added ? " Added='1'": "") + "/>";
    else if (row.Added) {
        var Bj = row.Def,
        EU = row.parentNode,
        GN = this.GL(row),
        GO = this.GM(row),
        GP = Get(row, "Par");
        S = "<I id=\"" + this.DM(row.id) + "\" Added='1'" + (GP ? " Par='" + GP + "'": "") + " Def='" + Bj.Name + "' Parent=\"" + (EU.id ? this.DM(EU.id) : EU.Pos != null ? EU.Pos: "") + "\" Next=\"" + (GN && GN.id ? this.DM(GN.id) : "") + "\" Prev=\"" + (GO && GO.id ? this.DM(GO.id) : "") + "\"";
        if (GV && row.Selected) S += " Selected='1'";
        if (row.Copy) S += " Copy='" + row.Copy + "'";
        if (DI) S += ">";
        if (this.Au) {
            for (var i = 0; i < this.Au; i++) {
                var B3 = this.IdNames[i];
                if (row[B3] == null) row[B3] = Bj[B3];
                if (B3 != "Def") GX(row, B3);
            }
        }
        for (var BY in C) {
            if ((GW && BY != "Panel" || row[BY] != null && (row[BY] != Bj[BY] || !Bj[BY])) && !this.At[BY] && !C[BY].Formula && (!row.Calculated || !row[BY + "Formula"]) && typeof(row[BY]) != "function") {
                GX(row, BY);
            }
        }
        S += DI ? "</I>": "/>";
    } else if (row.Changed || row.Moved) {
        S = "<I id=\"" + this.DM(row.id) + "\"";
        if (row.Changed) S += " Changed='1'";
        if (row.Moved) {
            var Bj = row.Def,
            EU = row.parentNode,
            GN = this.GL(row),
            GO = this.GM(row);
            S += " Moved='" + row.Moved + "' Parent=\"" + (EU.id ? this.DM(EU.id) : EU.Pos != null ? EU.Pos: "") + "\" Next=\"" + (GN && GN.id ? this.DM(GN.id) : "") + "\" Prev=\"" + (GO && GO.id ? this.DM(GO.id) : "") + "\"";
        }
        if (GV && row.Selected) S += " Selected='1'";
        if (DI) S += ">";
        if (row.Changed) {
            if (row.Cells) {
                for (var i = 0; i < row.Cells.length; i++) if (row[row.Cells[i] + "Changed"]) GX(row, row.Cells[i]);
            } else {
                for (var BY in C) if (row[BY + "Changed"]) GX(row, BY);
            }
        }
        if (this.Au && row[this.Av + "Changed"] && !this.Cols[this.Av]) GX(row, this.Av);
        S += DI ? "</I>": "/>";
    } else if (GV && row.Selected) S = "<I id=\"" + this.DM(row.id) + "\" Selected='1'/>";
    return S;
};
_7m.prototype.GY = function (row, DG, GV, GW) {
    var BL = new Array(),
    Bg = 0;
    for (var J = row.firstChild; J; J = J.nextSibling) {
        BL[Bg++] = this.GU(J, DG, GV, GW);
        BL[Bg++] = this.GY(J, DG, GV, GW);
    }
    return BL.join("");
};
_7m.prototype.HasChanges = function (row) {
    var Cu = 0;
    if (!row) {
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) if (CE[i].Kind != "Filter") Cu |= this.HasChanges(CE[i]);
        for (var J = this.XS.firstChild; J; J = J.nextSibling) Cu |= this.HasChanges(J);
        for (var Bc = this.XB.firstChild; Bc; Bc = Bc.nextSibling) for (var J = Bc.firstChild; J; J = J.nextSibling) Cu |= this.HasChanges(J);
    } else {
        if (row.NoUpload - 0 || row.Def && row.Def.NoUpload - 0) return 0;
        for (var J = row.firstChild; J; J = J.nextSibling) Cu |= this.HasChanges(J);
        if (row.Added || row.Deleted || row.Changed || row.Moved) Cu |= 1;
        if (row.Selected) Cu |= 2;
    };
    if (this.SaveSelected) Cu &= ~2;
    if (this.SaveValues) Cu &= ~1;
    return Cu;
};
_7m.prototype.GetChanges = function (row, type, GW) {
    var BG = this.Data.Upload.Format.toLowerCase();
    var GZ = "<IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/>";
    if (row) return "<Grid>" + GZ + "<Changes>" + this.GU(row, BG, type == 2, GW) + "</Changes></Grid>";
    var BL = new Array(),
    Bg = 0;
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) if (CE[i].Kind != "Filter") BL[Bg++] = this.GU(CE[i], BG, type == 2, GW);
    for (var J = this.XS.firstChild; J; J = J.nextSibling) BL[Bg++] = this.GU(J, BG, type == 2, GW);
    for (var Bc = this.XB.firstChild; Bc; Bc = Bc.nextSibling) BL[Bg++] = this.GY(Bc, BG, type == 2, GW);
    return (type ? "": "<Grid>" + GZ) + "<Changes>" + BL.join("") + "</Changes>" + (type ? "": "</Grid>");
};
_7m.prototype.AcceptChanges = function (row, Ga) {
    this.ClearUndo();
    if (row) {
        if (row.Deleted) {
            if (row == this.FRow) this.FRow = null;
            this.DelRow(row);
        } else if (row.Added || row.Changed || row.Moved) {
            if (this.Au) {
                var Gb = this.Cols["id"];
                if (row.Changed) {
                    var Df = 0,
                    id = null,
                    EI;
                    for (var i = 0; i < this.Au; i++) if (row[this.IdNames[i] + "Changed"]) {
                        if (!Df) {
                            id = row.id.split('$');
                            Df = 1;
                            EI = id.length - this.Au;
                        }
                        var x = Get(row, this.IdNames[i]);
                        if (x == null) x = "";
                        id[EI + i] = (x + "").replace(/\$/g, "_");
                    }
                    if (Df) {
                        row.id = id.join("$");
                        if (Gb) this.RefreshCell(row, "id");
                    }
                }
                if (this.FullId && (row.Changed && Df || row.Moved == 2)) {
                    var BQ = this.Au,
                    T = this;
                    if (row.Moved) {
                        var a = row.id.split('$'),
                        EU = row.parentNode,
                        id = EU.id;
                        if (!id) id = "";
                        for (var i = a.length - BQ; i < a.length; i++) id += "$" + a[i];
                        if (EU.Page) id = id.slice(1);
                        row.id = id;
                        if (Gb) T.RefreshCell(row, "id");
                    }
                    function Gc(EU) {
                        for (var J = EU.firstChild; J; J = J.nextSibling) {
                            var a = J.id.split('$'),
                            id = EU.id;
                            for (var i = a.length - BQ; i < a.length; i++) id += "$" + a[i];
                            J.id = id;
                            if (Gb) T.RefreshCell(J, "id");
                            if (J.firstChild) Gc(J);
                        }
                    };
                    if (row.firstChild) Gc(row);
                }
            }
            if (row.Changed) {
                if (row.Cells) {
                    for (var i = 0; i < row.Cells.length; i++) if (row[row.Cells[i] + "Changed"]) row[row.Cells[i] + "Changed"] = null;
                } else {
                    for (var BY in this.Cols) if (row[BY + "Changed"]) row[BY + "Changed"] = null;
                };
            }
            if (this.Au && (row.Changed || row.Moved) && row[this.Av + "Changed"]) row[this.Av + "Changed"] = null;
            row.Added = null;
            row.Changed = null;
            row.Moved = null;
            row.Copy = null;
            this.ColorRow(row);
        }
        if (!Ga && this.RefreshDetail) this.RefreshDetail();
        return;
    }
    var CE = this.GetFixedRows(),
    J,
    B3,
    DF;
    for (J = 0; J < CE.length; J++) if (CE[J].Kind != "Filter") this.AcceptChanges(CE[J], 1);
    for (J = this.XS.firstChild; J; J = J.nextSibling) this.AcceptChanges(J, 1);
    for (J = this.GetFirst(); J;) {
        if (J.Deleted) {
            B3 = J;
            DF = J.Level;
            J = this.GetNext(J);
            if (DF != null) while (J && J.Level > DF) J = this.GetNext(J);
            this.AcceptChanges(B3, 1);
        } else {
            this.AcceptChanges(J, 1);
            J = this.GetNext(J);
        }
    }
    if (this.RefreshDetail) this.RefreshDetail();
};
_7m.prototype.GetXmlData = function (type, Gd) {
    if (!type) type = this.Data.Upload.Type;
    if (Gd == null) Gd = this.Data.Upload.Attrs;
    if (typeof(Gd) == "string") Gd = Gd.split(",");
    var BG = this.Data.Upload.Format.toLowerCase();
    var BL = new Array(),
    Bg = 0,
    T = this,
    Cs = type & 7;
    if (! (type & 0x10000)) BL[Bg++] = "<Grid>";
    if (! (type & 0x20000)) BL[Bg++] = "<IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/>";
    var GW = type & 2048;
    if (Cs == 1 || Cs == 3) {
        BL[Bg++] = this.GetChanges(null, Cs == 1 ? 1 : 2, GW);
    }
    if (type == 1 || type == 3) return BL.join("") + "\n</Grid>";
    if (Cs == 2) {
        BL[Bg++] = "\n<Changes>";
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) if (CE[i].Selected) {
            BL[Bg++] = "<I";
            GX("id", CE[i].id);
            BL[Bg++] = "/>";
        }
        for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Selected) {
            BL[Bg++] = "<I";
            GX("id", J.id);
            BL[Bg++] = "/>";
        }
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (J.Selected) {
            BL[Bg++] = "<I";
            GX("id", J.id);
            BL[Bg++] = "/>";
        }
        BL[Bg++] = "\n</Changes>";
    }
    if (type & 8) {
        BL[Bg++] = this.GetCfgRequest(BG);
    }
    function Ge(B3, BZ) {
        if (typeof(BZ) == "boolean") BZ = BZ ? 1 : 0;
        else if (typeof(BZ) != "string" && typeof(BZ) != "number") return "";
        if (B3.charAt(0) == '_') {
            if (B3.charAt(1) == '7') return "";
            B3 = B3.slice(1);
        }
        return " " + B3 + "=\"" + T.DM(BZ) + "\"";
    };
    function GX(B3, BZ) {
        if (typeof(BZ) == "boolean") BZ = BZ ? 1 : 0;
        else if (typeof(BZ) != "string" && typeof(BZ) != "number") return;
        if (B3.charAt(0) == '_') {
            if (B3.charAt(1) == '7') return;
            B3 = B3.slice(1);
        }
        BL[Bg++] = " " + B3 + "=\"" + T.DM(BZ) + "\"";
    };
    function Gf(Gg, D8) {
        var S = "\n<" + Gg;
        for (var B3 in D8) S += Ge(B3, D8[B3]);
        BL[Bg++] = S + "/>";
    };
    function Gh(D8) {
        var EU = D8.parentNode,
        GN = T.GL(D8),
        GO = T.GM(D8);
        return " Parent=\"" + (EU.id ? T.DM(EU.id) : EU.Pos != null ? EU.Pos: "") + "\" Next=\"" + (GN && GN.id ? T.DM(GN.id) : "") + "\" Prev=\"" + (GO && GO.id ? T.DM(GO.id) : "") + "\"";
    };
    function Gi(Gg, D8) {
        if (Cs == 4 && (!D8.Changed && !D8.Added && !D8.Deleted && !D8.Moved && !D8.Selected || D8.Kind == "Filter")) return;
        BL[Bg++] = "\n<" + Gg;
        if (D8.id) GX("id", D8.id);
        if (D8.Def) GX("Def", D8.Def.Name);
        if (Cs == 4) BL[Bg++] = Gh(D8);
        if (Gd) for (var B3 = 0; B3 < Gd.length; B3++) GX(Gd[B3], GW && D8.Def ? Get(D8, Gd[B3]) : D8[Gd[B3]]);
        else if (GW && D8.Def) for (var B3 in D8) {
            if (!EW[B3]) GX(B3, Get(D8, B3));
        } else for (var B3 in D8) if (!EW[B3]) GX(B3, D8[B3]);
        BL[Bg++] = "/>";
    };
    function Gj(Gg, D8) {
        var S = "\n<" + Gg;
        if (D8.id) S += Ge("id", D8.id);
        if (D8.Def) S += Ge("Def", D8.Def.Name);
        if (Cs == 4) S += Gh(D8);
        if (Gd) for (var B3 = 0; B3 < Gd.length; B3++) S += Ge(Gd[B3], GW && D8.Def ? Get(D8, Gd[B3]) : D8[Gd[B3]]);
        else if (GW && D8.Def) for (var B3 in D8) {
            if (!EW[B3]) S += Ge(B3, Get(D8, B3));
        } else for (var B3 in D8) if (!EW[B3]) S += Ge(B3, D8[B3]);
        BL[Bg++] = S + ">";
        for (var J = D8.firstChild; J; J = J.nextSibling) Gj(J.tagName, J);
        BL[Bg++] = "\n</" + Gg + ">";
    };
    var EW = Grids.A8;
    if (type & 4112) {
        BL[Bg++] = "\n<Cfg";
        if (type & 16) {
            this.E6();
            for (var B3 in this) if (B3 != "Path") GX(B3, this[B3]);
            if (this.AX) {
                GX("SortCols", this.SortCols.join(","));
                GX("SortTypes", this.SortTypes.join(","));
            }
        }
        if (type & 4096) {
            GX("Cookie", this.SaveCfg(1));
        }
        BL[Bg++] = "/>";
    }
    if (type & 512) Gf("Img", this.Img);
    if (type & 32) {
        BL[Bg++] = "\n<Def>";
        for (var D8 in this.Def) Gf("D", this.Def[D8]);
        BL[Bg++] = "\n</Def>";
    }
    var C = this.Cols;
    if (type & 256) {
        var F = C["Panel"];
        if (F) Gf("Panel", F);
    }
    if (type & 72) {
        var Cols = ["LeftCols", "Cols", "RightCols"];
        var Gk = type & 8;
        for (var i = 0; i < 3; i++) {
            BL[Bg++] = "\n<" + Cols[i] + ">";
            var D = this.ColNames[i];
            for (var BY = 0; BY < D.length; BY++) {
                if (D[BY] == "Panel") continue;
                if (Gk) {
                    BL[Bg++] = "<C";
                    GX("Name", D[BY]);
                    GX("Width", C[D[BY]].Width);
                    GX("Visible", C[D[BY]].Visible);
                    BL[Bg++] = "/>";
                } else Gf("C", C[D[BY]]);
            }
            BL[Bg++] = "\n</" + Cols[i] + ">";
        }
    }
    if (type & 128) if (this.XHeader) Gi("Header", this.XHeader);
    if (Cs == 4) {
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) Gi("I", CE[i]);
        for (var J = this.XS.firstChild; J; J = J.nextSibling) Gi("I", J);
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) Gi("I", J);
    }
    if (Cs == 5 || Cs == 7) {
        var Rows = ["Head", "Foot", "Solid"],
        Gl = [this.XH, this.XF, this.XS];
        for (var j = 0; j < 3; j++) {
            var CE = this.GetRows(Gl[j]);
            if (CE.length) {
                BL[Bg++] = "\n<" + Rows[j] + ">";
                for (var i = 0; i < CE.length; i++) Gi("I", CE[i]);
                BL[Bg++] = "\n</" + Rows[j] + ">";
            }
        }
    }
    if (Cs == 6 || Cs == 7) {
        BL[Bg++] = "\n<Body>";
        for (var H = this.XB.firstChild; H; H = H.nextSibling) {
            var S = "\n<B";
            if (H.id) S += Ge("id", H.id);
            for (var B3 in H) if (!EW[B3]) S += Ge(B3, H[B3]);
            BL[Bg++] = S + ">";
            for (var J = H.firstChild; J; J = J.nextSibling) Gj("I", J);
            BL[Bg++] = "\n</B>"
        }
        BL[Bg++] = "\n</Body>";
    }
    if (type & 256) {
        Gf("Toolbar", this.Toolbar);
        Gf("MenuCfg", this.MenuCfg);
        Gf("Pager", this.Pager);
    }
    if (type & 1024) {
        BL[Bg++] = "\n<Lang>";
        for (var i in this.Lang) {
            var BZ = this.Lang[i];
            BL[Bg++] = "\n<" + i;
            for (var B3 in BZ) {
                if (BZ[B3].join) GX(B3, BZ[B3].join(","));
                else GX(B3, BZ[B3]);
            }
            BL[Bg++] = "/>";
        }
        BL[Bg++] = "\n</Lang>";
    }
    if (! (type & 0x10000)) BL[Bg++] = "\n</Grid>";
    return BL.join("");
    _7w("UploadType");
};
_7m.prototype.Gm = function () {
    if (this.Gn) {
        this.Gn[this.Go++] = {
            Type: "Start"
        };
        this.Gn.length = this.Go;
    }
};
_7m.prototype.Gp = function () {
    if (this.Gn) {
        this.Gn[this.Go++] = {
            Type: "End"
        };
    }
};
_7m.prototype.Gq = function (Gr) {
    if (this.Gn) {
        this.Gn[this.Go++] = Gr;
        this.Gn.length = this.Go;
    }
};
_7m.prototype.Gs = function (Gr) {
    if (this.Gn) {
        this.Gn[this.Go - 1] = Gr;
    }
};
_7m.prototype.ClearUndo = function () {
    if (this.Undo) {
        this.Gn = [];
        this.Go = 0;
    }
};
_7m.prototype.Gt = function (BQ) {
    if (!BQ) BQ = 1e5;
    var Gu = 0,
    Bl = 0;
    for (var i = this.Go - 1; i >= 0 && BQ; i--) {
        var Cs = this.Gn[i].Type;
        if (Cs == "End") Gu++;
        else if (Cs == "Start") Gu--;
        else Bl++;
        if (!Gu) BQ--;
    }
    return Bl;
};
_7m.prototype.DoUndo = function (BQ, refresh, Gv) {
    var Gw = this.Gn;
    if (!Gw || !this.Go) return false;
    BQ = this.Gt(BQ);
    if (!BQ) return false;
    if (!Gv && refresh && BQ >= this.SynchroCount) {
        this.ShowMessage(this.GetText("DoUndo"));
        var T = this;
        setTimeout(function () {
            T.DoUndo(BQ, 1, 1);
            T.HideMessage();
        },
        10);
        return;
    }
    this.Gn = null;
    for (var i = this.Go - 1; i >= 0 && BQ; i--) {
        var Gr = Gw[i];
        switch (Gr.Type) {
        case "Change":
            this.SetValue(Gr.Row, Gr.Col, Gr.Gx, refresh);
            Gr.Row.Changed = Gr.Gy;
            Gr.Row[Gr.Col + "Changed"] = Gr.Gz;
            BQ--;
            break;
        case "Add":
        case "Copy":
            this.DelRow(Gr.Row);
            BQ--;
            break;
        case "Delete":
            Gr.Row.Deleted = Gr.Deleted;
            Gr.Row.Selected = Gr.Selected;
            if (refresh) {
                if (!this.ShowDeleted) {
                    if (Gr.Row.Deleted) this.HideRow(Gr.Row);
                    else this.ShowRow(Gr.Row);
                }
                this.ColorRow(Gr.Row);
            }
            if (!Gr.Row.Expanded && Gr.Expanded) {
                if (refresh) this.Expand(Gr.Row);
                else row.Expanded = 1;
            }
            BQ--;
            break;
        case "Move":
            this.MoveRow(Gr.Row, Gr.G0, Gr.G1, refresh);
            Gr.Row.Moved = Gr.Moved;
            if (refresh) this.ColorRow(Gr.Row);
            BQ--;
            break;
        }
    }
    while (i >= 0 && Gw[i].Type == "Start") i--;
    this.Go = i + 1;
    this.Gn = Gw;
    return true;
};
_7m.prototype.G2 = function (BQ) {
    if (!BQ) BQ = 1e5;
    var Gu = 0,
    Bl = 0;
    for (var i = this.Go; i < this.Gn.length && BQ; i++) {
        var Cs = this.Gn[i].Type;
        if (Cs == "End") Gu--;
        else if (Cs == "Start") Gu++;
        else Bl++;
        if (!Gu) BQ--;
    }
    return Bl;
};
_7m.prototype.DoRedo = function (BQ, refresh, Gv) {
    var Gw = this.Gn;
    if (!Gw || !Gw.length) return false;
    BQ = this.G2(BQ);
    if (!BQ) return false;
    if (!Gv && refresh && BQ >= this.SynchroCount) {
        this.ShowMessage(this.GetText("DoRedo"));
        var T = this;
        setTimeout(function () {
            T.DoRedo(BQ, 1, 1);
            T.HideMessage();
        },
        10);
        return;
    }
    this.Gn = null;
    for (var i = this.Go; i < Gw.length && BQ; i++) {
        var Gr = Gw[i];
        switch (Gr.Type) {
        case "Change":
            this.SetValue(Gr.Row, Gr.Col, Gr.G3, refresh);
            BQ--;
            break;
        case "Add":
        case "Copy":
            var G4 = Gr.Row;
            if (Gr.Type == "Add") Gr.Row = this.AddRow(Gr.Parent, Gr.Next, refresh, Gr.G5, Gr.Def, Gr.G6);
            else {
                Gr.Row = this.CopyRow(Gr.G7, Gr.Parent, Gr.Next, Gr.Grid, Gr.G8, Gr.G9);
                if (refresh) this.ShowRow(Gr.Row);
            };
            for (var j = i + 1; j < Gw.length; j++) {
                var Ey = Gw[j];
                for (var k in Ey) {
                    if (Ey[k] == G4) Ey[k] = Gr.Row;
                }
            }
            BQ--;
            break;
        case "Delete":
            Gr.Row.Deleted = Gr.HA;
            if (refresh) {
                if (!this.ShowDeleted) {
                    if (Gr.Row.Deleted) this.HideRow(Gr.Row);
                    else this.ShowRow(Gr.Row);
                }
                this.ColorRow(Gr.Row);
            }
            BQ--;
            break;
        case "Move":
            this.MoveRow(Gr.Row, Gr.Par, Gr.Next, refresh);
            BQ--;
            break;
        }
    }
    while (i < Gw.length && Gw[i].Type == "End") i++;
    this.Go = i;
    this.Gn = Gw;
    return true;
};
_7m.prototype.ActionUndo = function () {
    return this.DoUndo(1, 1);
};
_7m.prototype.ActionUndoAll = function () {
    return this.DoUndo(0, 1);
};
_7m.prototype.ActionRedo = function () {
    return this.DoRedo(1, 1);
};
_7m.prototype.ActionRedoAll = function () {
    return this.DoRedo(0, 1);
};
_7m.prototype.ActionClearUndo = function () {
    this.ClearUndo();
    return true;
};
function _7AA(HB, HC, HD) {
    if (HD) {
        this.HE = [],
        Bg = 0;
        if (!HB) HB = new Date();
        else {
            if (typeof(HB) == "object") {
                if (_7o.GMT - 0) HB.setUTCHours(0, 0, 0, 0);
                else HB.setHours(0, 0, 0, 0);
                HB = HB.getTime();
            }
            var BL = (HB + "").split(HD.charAt(0));
            HB = null;
            for (var i = 0; i < BL.length; i++) {
                var H = BL[i].split(HD.charAt(1));
                if (!HB) HB = new Date(H[0] - 0);
                this.HE[Bg++] = H[0] - 0;
                this.HE[Bg++] = H[H[1] ? 1 : 0] - 0;
            }
        };
    } else if (!HB) HB = new Date();
    else if (typeof(HB) == "string" || typeof(HB) == "number") HB = new Date(HB);
    if (_7o.GMT - 0) {
        this.Y = HB.getUTCFullYear();
        this.M = HB.getUTCMonth();
        this.D8 = HB.getUTCDate();
    } else {
        this.Y = HB.getFullYear();
        this.M = HB.getMonth();
        this.D8 = HB.getDate();
    };
    if (HC) this.HF = _7AC(HC);
    this.HG = DateToString(HB, this.HF);
    this.HH = this.Y;
    this.HI = this.M;
    this.HJ = this.D8;
    this.Z = "";
    this.HK = "";
    this.Dialog = null;
    this.Grid = null;
    HB = new Date();
    if (_7o.GMT - 0) {
        this.HL = HB.getFullYear();
        this.HM = HB.getMonth();
        this.HN = HB.getDate();
    } else {
        this.HL = HB.getFullYear();
        this.HM = HB.getMonth();
        this.HN = HB.getDate();
    };
};
_7AA.prototype.HO = function (HK, Grid) {
    if (HK == null) HK = this.HK;
    if (Grid != null) this.Grid = Grid;
    var G = this.Grid,
    row = this.Row ? this.Row: G.FRow,
    BJ = this.Col ? this.Col: G.FCol,
    HP = _7o.GMT - 0;
    if (typeof(HK) == "number") HK = "Grids[" + HK + "].Dialog";
    this.HK = HK;
    this.Z = HK + ".Object";
    var HQ = _7o.FirstWeekDay - 0;
    var HR = new Date(this.Y, this.M, 0);
    HR = _7o.GMT - 0 ? HR.getUTCDate() : HR.getDate();
    var HS = new Date(this.Y, this.M + 1, 0);
    HS = _7o.GMT - 0 ? HS.getUTCDate() : HS.getDate();
    var HT = new Date(this.Y, this.M, 1);
    HT = _7o.GMT - 0 ? HT.getUTCDay() : HT.getDay();
    HT -= HQ;
    if (HT < 0) HT += 7;
    var BL = new Array(),
    Bg = 0,
    BO = " class='" + G.Img.Style,
    HU = "<td " + BO + "PickBorder'></td>";
    BL[Bg++] = "<div" + BO + "PickTag' onmouseup='" + this.Z + ".PickDrag(4);' onmousemove='CancelEvent(event);' onclick='if(" + this.HK + ")" + this.HK + ".Start=true;'>" + _7A + _7B;
    BL[Bg++] = "<tr" + BO + "PickHeader'>" + HU;
    BL[Bg++] = "<td" + BO + "PickHeadText' colspan='6'>" + G.GetText("SelectDate") + "</td>";
    BL[Bg++] = "<td><div" + BO + "PickClose' onclick='" + this.Z + ".Close();'><div " + (_7V ? "><b style='display:inline-block;": " style='") + " width:" + G.Img.CalendarWidth + "px;height:" + G.Img.CalendarHeight + "px;overflow:hidden;margin-" + (G.HV ? "right": "left") + ":auto;background:url(" + G.Img.Grid + ")" + " -400px -480px'></div>" + (_7V ? "</b>": "") + "</div></td>";
    BL[Bg++] = HU + "</tr>";
    BL[Bg++] = "<tr>" + HU + "<td colspan='7'><div" + BO + "PickSep'>" + _7D + "</div></td>" + HU + "</tr>";
    BL[Bg++] = "<tr" + BO + "PickDate'>" + HU;
    BL[Bg++] = "<td colspan='7'>";
    BL[Bg++] = "<div" + BO + "PickBL' onclick='" + this.Z + ".MYChg(-1);'>" + G.HW(15, 400 + G.Img.CalendarWidth * 3, G.Img.CalendarWidth, null, null, G.Img.CalendarHeight) + "</div>";
    BL[Bg++] = "<div" + BO + "PickBR' onclick='" + this.Z + ".MYChg(1);'>" + G.HW(15, 400 + G.Img.CalendarWidth * 4, G.Img.CalendarWidth, null, null, G.Img.CalendarHeight) + "</div>";
    var HX = _7o.LongMonthNames[this.M] + _7D + this.Y + _7D;
    BL[Bg++] = "<center>" + _7A + _7B + "<tr onclick='" + this.Z + ".ShowMY()' style='cursor:pointer;'>" + "<td" + BO + "PickMY' dir='ltr'>" + HX + "</td>" + "<td>" + G.HW(15, 400 + G.Img.CalendarWidth, G.Img.CalendarWidth, null, null, G.Img.CalendarHeight) + "</td></tr>" + _7C + "</center></td>";
    BL[Bg++] = HU + "</tr>";
    BL[Bg++] = "<tr" + BO + "PickRowW'>" + HU;
    var x = HQ,
    HY = "<td" + BO + "PickCell'><div";
    for (var i = 0; i < 7; i++) {
        BL[Bg++] = HY + BO + "PickWDN'>" + _7o.Day2CharNames[x + i >= 7 ? x + i - 7 : x + i] + "</div></td>";
    }
    BL[Bg++] = HU + "</tr>";
    var Ck = 1 - HT,
    Du = G.CanEdit(row, BJ) == 1;
    var HZ = -HQ;
    if (HZ < 0) HZ += 7;
    var Ha = 6 - HQ;
    if (Ha < 0) Ha += 7;
    for (var J = 0; J < 6; J++) {
        BL[Bg++] = "<tr" + BO + "PickRow'>" + HU;
        for (var BY = 0; BY < 7; BY++) {
            var Hb = BO + "PickWD";
            if (BY == HZ) Hb = BO + "PickSu";
            else if (BY == Ha) Hb = BO + "PickSa";
            var Hc = Ck;
            if (Ck <= 0) {
                Hc = Ck + HR;
                Hb = BO + "PickOM";
            } else if (Ck > HS) {
                Hc = Ck - HS;
                Hb = BO + "PickOM";
            }
            var D8 = HP ? new Date(Date.UTC(this.Y, this.M, Ck)) : new Date(this.Y, this.M, Ck);
            if (this.Y == this.HL && this.M == this.HM && Ck == this.HN) Hb = BO + "PickNow";
            if (!this.HE && this.Y == this.HH && this.M == this.HI && Ck == this.HJ) Hb = BO + "PickSel";
            var Hd = Grids.OnCanEditDate ? Grids.OnCanEditDate(G, row, BJ, D8) : Du;
            if (Hd) {
                var He = D8.getTime();
                if (this.HE) BL[Bg++] = HY + (this.IsSel(He) ? BO + "PickSel": Hb) + "'" + " onmouseover='this.className=" + this.Z + ".IsSel(" + He + ")?\"" + BO.slice(8) + "PickSelHover\":\"" + BO.slice(8) + "PickHover\";" + this.Z + ".PickDrag(1," + He + ");'" + " onmouseout='this.className=" + this.Z + ".IsSel(" + He + ")?\"" + BO.slice(8) + "PickSel\":\"" + Hb.slice(8) + "\";" + this.Z + ".PickDrag(2," + He + ");'" + " onmousedown='" + this.Z + ".PickDrag(3," + He + ");CancelEvent(event);'" + " id='GPick_" + He + "_" + Hb.slice(8) + "'" + ">" + Hc + "</div></td>";
                else BL[Bg++] = HY + Hb + "'" + " onmouseover='this.className=\"" + G.Img.Style + "PickHover\";'" + " onmouseout='this.className=\"" + Hb.slice(8) + "\";'" + " onclick='" + this.Z + ".DayClick(" + Ck + ");'>" + Hc + "</div></td>";
            } else BL[Bg++] = HY + Hb + "NE'>" + Hc + "</div></td>";
            Ck++;
        }
        BL[Bg++] = HU + "</tr>";
    }
    if (this.HF && !this.HE) {
        this.Hf = "G" + G.Index + "PickTime";
        var Hg = this.HF.indexOf('t') >= 0 && this.HF.indexOf('h') >= 0;
        BL[Bg++] = "<tr>" + HU;
        BL[Bg++] = "<td colspan='7'><div" + BO + "PickTimeCell'><input" + BO + "PickTime' id='" + this.Hf + "' type='text'";
        BL[Bg++] = " value='" + this.HG + "'";
        BL[Bg++] = " onkeydown='var k = GetKey(event); if(k==13)" + this.Z + ".DayClick(); if(k==27)" + this.Z + ".Close(); CancelEvent(event,1)'";
        BL[Bg++] = " onkeypress='KeyDate(event,this," + (Hg ? 1 : 0) + ")'";
        BL[Bg++] = "/></div></td>";
        BL[Bg++] = HU + "</tr>";
    }
    var Hh = G.CalendarButtons;
    if (Hh == null) Hh = this.HE ? 7 : 0;
    if (Hh) {
        var Hi = " style='visibility:hidden;'";
        BL[Bg++] = "<tr" + BO + "PickFooter'>" + HU;
        BL[Bg++] = "<td" + BO + "PickFootText' colspan='7' align='right'>";
        BL[Bg++] = "<button" + BO + "PickButton'" + (Hh & 1 ? "": Hi) + " onclick='" + this.Z + ".BClick(0);'>" + G.GetAlert("DateToday") + "</button>";
        BL[Bg++] = "<button" + BO + "PickButton'" + (Hh & 2 ? "": Hi) + " onclick='" + this.Z + ".BClick(1);'>" + G.GetAlert("DateClear") + "</button>";
        BL[Bg++] = "<button" + BO + "PickButton'" + (Hh & 4 ? "": Hi) + " onclick='" + this.Z + ".BClick(2);'>" + G.GetAlert("DateOk") + "</button>";
        BL[Bg++] = "</td>" + HU + "</tr>";
    }
    BL[Bg++] = _7C + "</div>";
    return BL.join("");
};
_7AA.prototype.ShowMY = function (GA) {
    var G = this.Grid;
    var BL = new Array(),
    Bg = 0,
    BO = " class='" + G.Img.Style,
    HU = "<td " + BO + "PickBorder'></td>";
    BL[Bg++] = "<div" + BO + "PickTag' onmouseup='" + this.Z + ".PickDrag(4);' onmousemove='CancelEvent(event);' onclick='if(" + this.HK + ")" + this.HK + ".Start=true;'>" + _7A + _7B;
    BL[Bg++] = "<tr" + BO + "PickHeader'>" + HU;
    BL[Bg++] = "<td" + BO + "PickHeadText' colspan='4'>" + G.GetText("SelectDateMY") + "</td>";
    BL[Bg++] = "<td><div" + BO + "PickClose' onclick='" + this.Z + ".Close();'><div " + (_7V ? "><b style='display:inline-block;": " style='") + " width:" + G.Img.CalendarWidth + "px;height:" + G.Img.CalendarHeight + "px;overflow:hidden;margin-" + (G.HV ? "right": "left") + ":auto;background:url(" + G.Img.Grid + ")" + " -400px -480px'></div>" + (_7V ? "</b>": "") + "</div></td>";
    BL[Bg++] = HU + "</tr>";
    BL[Bg++] = "<tr><td colspan='7'><div" + BO + "PickSep'>" + _7D + "</div></td></tr>";
    BL[Bg++] = "<tr" + BO + "PickDate'>" + HU;
    BL[Bg++] = "<td colspan='5'>";
    BL[Bg++] = "<div" + BO + "PickBL' onclick='" + this.Z + ".MYChg(-1);'>" + G.HW(15, 400 + G.Img.CalendarWidth * 3, G.Img.CalendarWidth, null, null, G.Img.CalendarHeight) + "</div>";
    BL[Bg++] = "<div" + BO + "PickBR' onclick='" + this.Z + ".MYChg(1);'>" + G.HW(15, 400 + G.Img.CalendarWidth * 4, G.Img.CalendarWidth, null, null, G.Img.CalendarHeight) + "</div>";
    var HX = _7o.LongMonthNames[this.M] + _7D + this.Y + _7D;
    BL[Bg++] = "<center>" + _7A + _7B + "<tr onclick='" + this.Z + ".Render()' style='cursor:pointer;'>" + "<td" + BO + "PickMY' dir='ltr'>" + HX + "</td>" + "<td>" + G.HW(15, 400 + G.Img.CalendarWidth * 2, G.Img.CalendarWidth, null, null, G.Img.CalendarHeight) + "</td></tr>" + _7C + "</center></td>";
    BL[Bg++] = HU + "</tr>";
    BL[Bg++] = "<tr><td colspan='7'><div" + BO + "Pick2SepH'>" + _7D + "</div></td></tr>";
    if (!GA) this["FY"] = Math.floor(this.Y / 5) * 5;
    if (this.Y - Hj < 2) Hj -= 5;
    var Hj = this["FY"];
    for (var i = 0; i < 6; i++) {
        BL[Bg++] = "<tr>" + HU;
        for (var j = 0; j <= 6; j += 6) {
            BL[Bg++] = "<td" + BO + "Pick2Cell'><div" + BO + "Pick2M" + (i + j == this.M ? "Sel": "") + "'" + " onmouseover='this.className=\"" + G.Img.Style + "Pick2M" + ((i + j == this.M ? "Sel": "")) + "Hover\";'" + " onmouseout='this.className=\"" + G.Img.Style + "Pick2M" + ((i + j == this.M ? "Sel": "")) + "\";'" + " onclick='" + this.Z + ".M=" + (i + j) + ";" + this.Z + ".ShowMY(1);'" + ">" + _7o.ShortMonthNames[i + j] + "</div></td>";
        }
        BL[Bg++] = "<td><div" + BO + "Pick2Sep'>" + _7D + "</div></td>";
        if (!i) {
            BL[Bg++] = "<td" + BO + "Pick2Cell'><div" + BO + "Pick2BL'" + " onclick='" + this.Z + ".FY-=5;" + this.Z + ".ShowMY(1);'" + ">" + G.HW(15, 400 + G.Img.CalendarWidth * 6 + (G.HV ? G.Img.Row: 0), G.Img.Row) + "</div></td>";
            BL[Bg++] = "<td" + BO + "Pick2Cell'><div" + BO + "Pick2BR'" + " onclick='" + this.Z + ".FY+=5;" + this.Z + ".ShowMY(1);'" + ">" + G.HW(15, 400 + G.Img.CalendarWidth * 6 + (G.HV ? 0 : G.Img.Row), G.Img.Row) + "</div></td>";
        } else {
            for (var j = -1; j <= 4; j += 5) {
                BL[Bg++] = "<td" + BO + "Pick2Cell'><div" + BO + "Pick2Y" + (Hj + i + j == this.Y ? "Sel": "") + "'" + " onmouseover='this.className=\"" + G.Img.Style + "Pick2Y" + ((Hj + i + j == this.Y ? "Sel": "")) + "Hover\";'" + " onmouseout='this.className=\"" + G.Img.Style + "Pick2Y" + ((Hj + i + j == this.Y ? "Sel": "")) + "\";'" + " onclick='" + this.Z + ".Y=" + (Hj + i + j) + ";" + this.Z + ".ShowMY(1);'" + ">" + (Hj + i + j) + "</div></td>";
            }
        };
        BL[Bg++] = HU + "</tr>";
    }
    BL[Bg++] = "<tr" + BO + "PickFooter'>" + HU;
    BL[Bg++] = "<td" + BO + "PickFootText' colspan='5' align='right'>";
    BL[Bg++] = "<button" + BO + "PickButton' onclick='" + this.Z + ".Render();'>" + G.GetAlert("DateOk") + "</button>";
    BL[Bg++] = "</td>" + HU + "</tr>";
    BL[Bg++] = _7C + "</div>";
    var E4 = GetElem(this.Hf);
    if (E4) this.HG = E4.value;
    this.Dialog.Tag.innerHTML = BL.join("");
    G.Hk(this.Dialog.Tag, G.ShadowDialog);
};
_7AA.prototype.PickDrag = function (Ca, Hl, Hm) {
    if (!this.Az && Ca != 3) return;
    switch (Ca) {
    case 1:
        this.Hn = Hl;
        this.BI();
        break;
    case 2:
        break;
    case 3:
        this.Az = 1;
        this.Ho = Hl;
        this.Hn = Hl;
        this.Hp = 1;
        this.BI();
        break;
    case 4:
        this.Az = 0;
        if (this.Ho == this.Hn) {
            this.DaySelect(this.Ho);
            this.BI();
        } else {
            var M = this.HE;
            M[M.length] = this.Ho < this.Hn ? this.Ho: this.Hn;
            M[M.length] = this.Ho < this.Hn ? this.Hn: this.Ho;
            this.BI();
        };
        break;
    };
    return false;
};
_7AA.prototype.IsSel = function (Ck) {
    if (this.Az && (Ck >= this.Ho && Ck <= this.Hn || Ck >= this.Hn && Ck <= this.Ho)) return this.Hp;
    for (var i = 0; i < this.HE.length; i += 2) {
        if (Ck >= this.HE[i] && Ck <= this.HE[i + 1]) return true;
    }
    return false;
};
_7AA.prototype.Hq = function () {
    if (!this.HF) return;
    var E4 = GetElem(this.Hf);
    if (!E4) return;
    try {
        if (_7G && window._7AE) _7AE(E4);
        E4.focus();
        if (E4.select) E4.select();
    } catch(B) {}
};
_7AA.prototype.Render = function () {
    this.Dialog.Tag.innerHTML = this.HO();
    this.Hr();
    this.Hq();
    this.Grid.Hk(this.Dialog.Tag, this.Grid.ShadowDialog);
};
_7AA.prototype.MYChg = function (dir) {
    this.M += dir;
    while (this.M < 0) {
        this.Y--;
        this.M += 12;
    }
    while (this.M > 11) {
        this.Y++;
        this.M -= 12;
    }
    this.Render();
};
_7AA.prototype.Close = function () {
    var G = this.Dialog.Grid;
    G.CloseDialog(true);
    G.EndEdit(G.FRow, G.FCol);
};
_7AA.prototype.BI = function () {
    var G = this.Dialog.Grid,
    M = this.HE;
    var Hm = this.Dialog.Tag.getElementsByTagName("div");
    for (var i = 0; i < Hm.length; i++) {
        var id = Hm[i].id;
        if (!id) continue;
        id = id.split('_');
        var Hb = Hm[i].className,
        Hs = Hb.toLowerCase().indexOf("hover") >= 0;
        var Ht = this.IsSel(id[1]) ? G.Img.Style + "PickSel" + (Hs ? "Hover": "") : (Hs ? G.Img.Style + "PickHover": id[2]);
        if (Hb != Ht) Hm[i].className = Ht;
    }
    this.Hr();
};
_7AA.prototype.DaySelect = function (Hl, Hm) {
    var G = this.Dialog.Grid;
    var M = this.HE;
    var HT = new Date(Hl),
    Hu = new Date(Hl);
    HT.setDate(HT.getDate() + 1);
    HT = HT.getTime();
    Hu.setDate(Hu.getDate() - 1);
    Hu = Hu.getTime();
    for (var i = 0; i < M.length; i += 2) {
        if (Hl >= M[i] && Hl <= M[i + 1]) {
            if (Hl == M[i]) {
                if (Hl == M[i + 1]) {
                    M[i] = null;
                    M[i + 1] = null;
                } else M[i] = HT;
            } else if (Hl == M[i + 1]) M[i + 1] = Hu;
            else {
                M[M.length] = HT;
                M[M.length] = M[i + 1];
                M[i + 1] = Hu;
            };
            if (Hm) Hm.className = G.Img.Style + "PickHover";
            return;
        }
    }
    if (Hm) Hm.className = G.Img.Style + "PickSelHover";
    for (var i = 0; i < M.length; i += 2) {
        if (HT == M[i]) {
            M[i] = Hl;
            return;
        }
        if (Hu == M[i + 1]) {
            M[i + 1] = Hl;
            return;
        }
    }
    M[M.length] = Hl;
    M[M.length] = Hl;
};
_7AA.prototype.Hr = function () {
    var G = this.Dialog.Grid;
    if (!G.AutoCalendar) return;
    var E4 = G.GetCell(G.FRow, G.FCol, 1).firstChild;
    if (this.HE) {
        var Dh = G.FRow[G.FCol];
        G.FRow[G.FCol] = G.BP(this.HE);
        var V = G.FJ(G.FRow, G.FCol);
        G.FRow[G.FCol] = Dh;
        E4.value = V.Value;
    }
    E4.focus();
};
_7AA.prototype.BClick = function (Hv) {
    var G = this.Dialog.Grid;
    if (Hv == 0) {
        var HP = _7o.GMT - 0;
        var BR = new Date();
        if (HP) BR.setUTCHours(0, 0, 0, 0);
        else BR.setHours(0, 0, 0, 0);
        this.Save(BR.getTime());
    }
    if (Hv == 1) {
        if (this.HE) {
            this.HE = [];
            this.Hr();
            this.Render();
        } else {
            this.Save("");
        }
    }
    if (Hv == 2) {
        if (this.HE) this.Save(G.BP(this.HE));
        else this.DayClick();
    }
};
_7AA.prototype.Save = function (BR) {
    var G = this.Dialog.Grid,
    row = this.Row ? this.Row: G.FRow,
    BJ = this.Col ? this.Col: G.FCol;
    if (G.EditMode) {
        if (G.EndEdit(row, BJ) == -1) return;
    } else if (Grids.OnEndEdit && Grids.OnEndEdit(G, row, BJ, true, BR)) return;
    G.CloseDialog(true);
    if (Grids.OnValueChanged) BR = Grids.OnValueChanged(G, row, BJ, BR);
    var Df = G.SetValue(row, BJ, BR);
    if (Df && G.SortRow) G.SortRow(row, BJ, true);
    if (Df && Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(G, row, BJ);
    G.RefreshCell(row, BJ);
    if (row && row.Kind == "Filter" && G.DoFilter) {
        if (!BR) {
            row[BJ + "Filter"] = 0;
            G.RefreshCell(row, BJ);
        }
        G.DoFilter(BR ? row: null, BJ);
    }
};
_7AA.prototype.DayClick = function (Ck) {
    var HP = _7o.GMT - 0;
    var BR = HP ? new Date(Date.UTC(this.Y, this.M, Ck == null ? this.D8: Ck)) : new Date(this.Y, this.M, Ck == null ? this.D8: Ck);
    if (this.HF) {
        var E4 = GetElem(this.Hf);
        if (E4) {
            var Cs = StringToDate(E4.value, this.HF);
            if (Cs) {
                if (HP) BR.setUTCHours(Cs.getUTCHours(), Cs.getUTCMinutes(), Cs.getSeconds());
                else BR.setHours(Cs.getHours(), Cs.getMinutes(), Cs.getSeconds());
            }
        }
    }
    BR = BR.getTime();
    this.Save(BR);
};
_7m.prototype.Hw = function (row, BJ) {
    if (this.Ae) return;
    var HD = this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "";
    var F = new _7AA(HD ? Get(row, BJ) : this.GetValue(row, BJ), this.GetFormat(row, BJ, 1), HD);
    F.Row = row;
    F.Col = BJ;
    var DD = F.HO(this.Index, this);
    this.ShowDialog(row, BJ, DD);
    this.Dialog.Object = F;
    F.Dialog = this.Dialog;
    if (!this.AutoCalendar || !this.EditMode) F.Hq();
};
_7m.prototype.ActionShowCalendar = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !BJ || this.GetType(row, BJ) != "Date" || !this.CanEdit(row, BJ)) return false;
    if (row != this.Hx || BJ != this.Hy) this.Hw(row, BJ);
    return true;
};
_7m.prototype.MoveRows = function (row, Hz, type, H0) {
    var Cu, H1 = row.parentNode;
    if (!Hz || Hz == row || type == 0) return;
    switch (type) {
    case 1:
        Cu = this.MoveRow(row, null, Hz, true, null, H0);
        break;
    case 2:
        if (Hz.firstChild && Hz.firstChild.Dx) Hz = Hz.lastChild;
        if (!H0) {
            if (!Hz.Expanded && !Hz.Page) this.Expand(Hz);
            if (Hz.State < 4) {
                var T = this;
                setTimeout(function () {
                    T.MoveRows(row, Hz, type);
                },
                100);
                return;
            }
        }
        Cu = this.MoveRow(row, Hz, null, true, null, H0);
        break;
    case 3:
        Cu = this.MoveRow(row, Hz.parentNode, Hz.nextSibling, true, null, H0);
        break;
    };
    if (Cu) this.UploadChanges(row);
    var H2 = row.parentNode;
    if (!this.E2 && H1 != H2 && (!H1.Page || !H2.Page)) {
        this.Recalculate(row, null, true);
        if (!H1.Removed) this.Recalculate(H1, null, true);
    }
    if (this.H3) {
        this.RefreshGanttDependencies();
    }
};
_7m.prototype.H4 = function (BL, Hz, type) {
    if (type == 2) {
        if (!Hz.Expanded) this.Expand(Hz);
        if (Hz.State < 4) {
            var T = this;
            setTimeout(function () {
                T.H4(BL, Hz, type);
            },
            100);
            return;
        }
    }
    this.MoveRows(BL[0], Hz, type);
    for (var i = 1; i < BL.length; i++) this.MoveRows(BL[i], BL[i - 1], 3);
};
_7m.prototype.MoveRowsToGrid = function (row, G, Hz, type, DV, Gv) {
    if (DV == null) DV = this.DragCopy;
    if (row.MasterRow && G == this.E2) {
        G.MoveRows(row.MasterRow, Hz, type, 1);
        return;
    }
    if (G.E2 == this) {
        this.MoveRows(row, Hz ? Hz.MasterRow: G.XB.firstChild.MasterRow, type, 1);
        return;
    }
    var H5 = row.id && !DV ? 1 : 0;
    var J = H5 ? G.GetRowById(row.id) : null;
    if (J) {
        if (Hz != null) {
            if (!Gv) {
                if (type == 2 && !Hz.Expanded) {
                    this.Expand(Hz);
                    if (Hz.State < 4) {
                        var T = this;
                        setTimeout(function () {
                            T.MoveRowsToGrid(row, G, Hz, type, DV);
                        },
                        100);
                        return;
                    }
                }
            }
            G.MoveRows(J, Hz, type);
        }
        if (J.Deleted && !row.Deleted) {
            if (G.DeleteRowT) G.DeleteRowT(J, 3);
        } else if (!row.Deleted) G.ShowRow(J);
    } else {
        var Bj = this.DropFree ? row.Def.Name: null;
        if (Hz == null) {
            J = G.AddRow(null, null, 1, H5, Bj);
        } else {
            switch (type) {
            case 1:
                J = G.AddRow(null, Hz, 1, H5, Bj);
                break;
            case 2:
                if (!Gv) {
                    if (!Hz.Expanded) G.Expand(Hz);
                    if (Hz.State < 4) {
                        var T = this;
                        setTimeout(function () {
                            T.MoveRowsToGrid(row, G, Hz, type, DV);
                        },
                        100);
                        return;
                    }
                }
                J = G.AddRow(Hz, null, 1, H5, Bj);
                break;
            case 3:
                J = G.AddRow(Hz.parentNode, Hz.nextSibling, 1, H5, Bj);
                break;
            }
        };
        if (!J) return;
        if (row.Deleted && G.DeleteRowT) G.DeleteRowT(J, 2);
    };
    J.Deleted = row.Deleted;
    if (H5) J.id = row.id;
    for (var BY in this.Cols) if (Get(row, BY) != null && BY != 'id') J[BY] = Get(row, BY);
    for (var BY in G.Cols) if (Get(row, BY) != null && BY != 'id') J[BY] = Get(row, BY);
    J.Selected = row.Selected;
    if (DV != 2) {
        for (var B3 = row.firstChild; B3; B3 = B3.nextSibling) {
            this.MoveRowsToGrid(B3, G, J, 2, DV, 1);
        }
    }
    if (row.Expanded) {
        G.Collapse(J);
        G.Expand(J);
    } else {
        G.Expand(J);
        G.Collapse(J);
    };
    G.RefreshRow(J);
    G.Recalculate(J, null, true);
    if (G.SortRow) G.SortRow(J, null, true);
    if (Grids.OnRowMoveToGrid) Grids.OnRowMoveToGrid(this, row, G, J, DV);
    if (!DV) {
        if (this.DeleteRowT) this.DeleteRowT(row, 2);
    } else this.UploadChanges(row);
    G.UploadChanges(J);
    return J;
};
_7m.prototype.H6 = function (G, H7, row, y, DV) {
    var type = -1,
    EU = row.parentNode;
    var H8 = (EU.Page ? 1 : 0) || Is(EU, "AcceptChild"),
    H9 = H8,
    IA = Is(row, "AcceptChild");
    if (this.MainCol) {
        if (!this.DropFree) {
            var Def = Get(H7[0], "Def").Name;
            if (IA && this.GetCDef(row) != Def) {
                if (!row.firstChild || !row.firstChild.Dx || this.GetCDef(row.firstChild) != Def) IA = false;
            }
            if (this.GetCDef(EU) != Def) {
                H8 = false;
                H9 = false;
            }
        } else {
            if (row.firstChild && row.firstChild.Dx) IA = false;
            if (row.Dx) {
                H8 = false;
                H9 = false;
            }
        }
    }
    if (!DV && G == this) {
        for (var i = 0; i < H7.length; i++) {
            var DF = row.Level - H7[i].Level;
            if (DF > 0) {
                for (var Bg = row; DF; DF--) Bg = Bg.parentNode;
                if (Bg == H7[i]) type = 0;
                break;
            } else if (!DF && row == H7[i]) IA = false;
        }
        if (H7.length == 1) {
            if (H7[0] == row || H7[0] == row.previousSibling) H8 = false;
            if (H7[0] == row || H7[0] == row.nextSibling) H9 = false;
            if (row == H7[0].parentNode) IA = false;
        }
    }
    if (EU.Deleted) type = 0;
    if (row.Deleted) IA = 0;
    if (row.Fixed) type = 0;
    if (type == -1) {
        var height = this.GetRowHeight(row),
        IB = height / 3 - 1,
        IC = (height) / 3 * 2,
        ID = (IC + IB) / 2;
        if (y == null) y = ID;
        if (!this.MainCol) {
            IB = (IB + IC) / 2;
            IC = IB;
            IA = false;
        }
        if (y <= IB) {
            if (H8) type = 1;
            else if (IA) type = 2;
            else if (H9) type = 3;
        } else if (y >= IC) {
            if (H9) type = 3;
            else if (IA) type = 2;
            else if (H8) type = 1;
        } else {
            if (IA) type = 2;
            else if (y < ID && H8) type = 1;
            else if (H9) type = 3;
            else if (H8) type = 1;
        };
        if (type == -1) type = 0;
    }
    if (Grids.OnCanDrag) type = Grids.OnCanDrag(G, H7[0], this, row, type);
    return type;
};
_7m.prototype.ActionDragRow = function () {
    return this.IE(0);
};
_7m.prototype.ActionDragSelected = function () {
    return this.IE(1);
};
_7m.prototype.ActionDragCopy = function () {
    return this.IE(0, 2);
};
_7m.prototype.ActionDragSelectedCopy = function () {
    return this.IE(1, 2);
};
_7m.prototype.ActionDragCopyChildren = function () {
    return this.IE(0, 1);
};
_7m.prototype.ActionDragSelectedCopyChildren = function () {
    return this.IE(1, 1);
};
_7m.prototype.IF = function (row, BL) {
    for (var J = row.firstChild; J; J = J.nextSibling) {
        if (!J.Visible || J.Deleted) continue;
        if (J.Selected) {
            if (Is(J, "CanDrag") && J != BL[0] && (this.DropFree || !BL.length || J.Def == BL[0].Def)) BL[BL.length] = J;
        } else if (J.firstChild) this.IF(J, BL);
    }
};
_7m.prototype.IG = function (BL) {
    for (var J = this.XB.firstChild; J; J = J.nextSibling) this.IF(J, BL);
};
_7m.prototype.IE = function (GV, DV) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var row = D8.Row;
    if (!row || row.Fixed || row.Deleted || !this.Dragging) return false;
    if (GV && !row.Selected || !Is(row, "CanDrag") || this.Cols[this.ACol] && !this.Cols[this.ACol].CanDrag) return false;
    if (GV == null) GV = !!row.Selected;
    if (GV) {
        var BL = new Array();
        BL[0] = row;
        this.IG(BL);
        if (BL.length > 1) D8.Rows = BL;
    }
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, true) == -1) return false;
    if (Grids.OnStartDrag && Grids.OnStartDrag(this, D8.Row, D8.Col, !!D8.Rows)) return false;
    D8.Action = "Drag";
    D8.Move = this.IH;
    D8.II = this.IJ;
    D8.Copy = DV;
    D8.O = document.documentElement.style.cursor;
    document.documentElement.style.cursor = this.Img["Drag" + (D8.Rows ? "More": "One")];
    var EK = "<div style='height:32px;overflow:hidden;' align='left'>" + _7A + _7B + "<tr><td valign='top'>" + this.IK(row, 0) + "</td><td valign='top'>" + this.IK(row, 1) + "</td><td valign='top'>" + this.IK(row, 2) + "</td></tr>" + _7C + "</div>";
    var J = this.GetRow(row, this.HV ? 2 : 0);
    if (!J) J = this.GetRow(row, 1);
    var IL = {
        clientX: D8.P,
        clientY: D8.Y
    };
    this.IM(J, IL, 3, EK);
    if (Grids.A5) {
        if (D8.Rows) {
            var B = Grids.A5.Tag;
            B.style.borderRight = "5px groove gray";
            B.style.borderBottom = "5px groove gray";
        }
    }
    return true;
};
_7m.prototype.IH = function (Click, IN) {
    var D8 = Grids.Az,
    row = Click.Row;
    if (!D8) return false;
    this.AS = null;
    if (!row || row.Fixed) {
        if (!IN) this.IP(row);
        if (!this.DropFixed || !row) row = null;
        else if (this.AS == 1) {
            row = this.GetFirstVisible();
            Click.Y = 1;
        } else if (this.AS == 2) {
            row = this.GetLastVisible();
            while (!row.parentNode.Page) row = row.parentNode;
            Click.Y = this.GetRowHeight(row) - 1;
        } else row = null;
    }
    if (row != D8.L && D8.L) D8.N.Es(D8.L);
    if (row) {
        if (this.ExpandOnDrag && Click.Col == this.MainCol && this.HasChildren(row) && !row.Expanded) {
            var x = Click.P - row.Level * this.Img.Line;
            if (x < this.Img.Tree && x >= 0) this.Expand(row);
        }
        var type = this.Dropping ? this.H6(D8.Grid, D8.Rows ? D8.Rows: [D8.Row], row, Click.Y) : 0;
        if (D8.Type != type || row != D8.L) {
            this.Es(row, type);
            D8.Type = type;
            D8.L = row;
            D8.N = this;
        }
    } else {
        D8.L = null;
        D8.Type = 0;
    };
    return true;
};
_7m.prototype.IJ = function () {
    var D8 = Grids.Az,
    G = D8.Grid,
    T = D8.N;
    if (!D8) return false;
    if (Grids.Active && Grids.Active != D8.N && !Grids.Active.GetFirstVisible()) {
        if (D8.Row.Def.Name == Grids.Active.XHeader.CDef) {
            T = Grids.Active;
            if (!T.Detail || T.XB.firstChild.MasterRow) {
                D8.Type = 2;
            }
        }
    }
    if (Grids.OnEndDrag) {
        Grids.Az = null;
        D8.Type = Grids.OnEndDrag(G, D8.Row, T, D8.L, D8.Type, D8.IQ, D8.IR);
        Grids.Az = D8;
    }
    if (D8.Type && !T.Ae) {
        this.Gm();
        if (D8.Rows) {
            if (D8.Grid == T && !D8.Copy) T.H4(D8.Rows, D8.L, D8.Type);
            else {
                var B3 = G.MoveRowsToGrid(D8.Rows[0], T, D8.L, D8.Type, D8.Copy);
                for (var i = 1; i < D8.Rows.length; i++) B3 = G.MoveRowsToGrid(D8.Rows[i], T, B3, 3, D8.Copy);
            }
        } else {
            if (D8.Grid == T && !D8.Copy) {
                if (_7X) var D = T.GetNextVisible(D8.Row);
                T.MoveRows(D8.Row, D8.L, D8.Type);
                if (_7X && D) T.RefreshRow(D);
            } else {
                G.MoveRowsToGrid(D8.Row, T, D8.L, D8.Type, D8.Copy);
            };
        };
        this.Gp();
    }
    document.documentElement.style.cursor = D8.O;
    if (D8.L) this.Es(D8.L);
    if (this.SaveValues) this.SaveCfg();
    return true;
};
_7m.prototype.StartEdit = function (row, BJ, IS) {
    if (row == null) row = this.FRow;
    if (BJ == null) BJ = this.FCol;
    if (!row || !BJ || BJ == "Panel" || row.Kind == "User" || row.Page) return;
    if (this.EditMode || !this.CanEdit(row, BJ)) return;
    var C = this.Cols,
    BX = BJ,
    IT = null,
    CY, IU, IV;
    if (row.Spanned && Get(row, BJ + "Merge")) {
        IT = Get(row, BJ + "MergeEdit");
        if (!IT) IT = 0;
        BX = this.ColNames[C[BJ].K][C[BJ].Pos + IT];
        if (!C[BX].Visible && !(Get(row, BJ + "MergeType") & 2)) return;
    }
    if (Grids.OnStartEdit && Grids.OnStartEdit(this, row, BX)) return;
    this.HideHint();
    if (!IS && Is(row, BX + "EditServer")) {
        var T = this;
        this.E1(row, BJ, function (EJ) {
            if (EJ >= 0) T.StartEdit(row, BJ, 1);
        });
        return;
    }
    var IW = this.CanEdit(row, BX) == 2,
    color = "",
    BO = this.Img.Style;
    color = this.Al[IW ? "ViewedCell": "EditedCell"];
    if (Grids.OnGetColor) {
        var Dt = Grids.OnGetColor(this, row, BJ, (color >> 16) & 255, (color >> 8) & 255, color & 255, 2);
        if (Dt != null) color = Dt;
    }
    if (typeof(color) == "number") color = "#" + Number(color).toString(16);
    if (Is(row, BJ + "NoColor") || Is(row, "NoColor")) color = "";
    var type = this.GetType(row, BX);
    function focus(E4) {
        try {
            if (_7G) _7AE(E4);
            E4.focus();
            if (!IW && E4.select) E4.select();
            if (_7S && IW) {
                var S = E4.createTextRange();
                S.move("character", 0);
                S.select();
            }
        } catch(B) {}
    };
    if (type == "Bool" || type == "Radio") {
        if (type == "Radio") {
            var BG = this.GetFormat(row, BJ);
            if (BG) {
                BG = BG.split(BG.charAt(0));
                if ((BG[1] - 0) & 16) return;
            }
            this.IX = Get(row, BJ);
        }
        this.EditMode = true;
        this.ColorCell(row, BJ);
        return;
    }
    var D3 = C[BJ];
    if (!D3 && row.Space != null) D3 = this.Def["C"];
    if (D3) {
        var Dh = D3.Visible;
        D3.Visible = 1;
    }
    IU = this.GetCell(row, BJ, 1);
    IV = this.GetCell(row, BJ);
    if (D3) D3.Visible = Dh;
    if (!IU) return;
    if (IT) {
        var IY = Get(row, BJ + "MergeStart"),
        S = this.ColNames[C[BJ].K],
        IZ = Get(row, BJ + "MergeType");
        if (!IY) IY = 0;
        for (; IY < IT; IY++) if (C[S[C[BJ].Pos + IY]].Visible || IZ & 2) IU = IZ & 1 ? IU.parentNode.nextSibling.firstChild: IU.nextSibling;
    }
    if (BJ == this.MainCol && !row.Fixed) {
        CY = this.GetCell(row, BJ).clientWidth;
        CY -= row.Level * this.Img.Line + this.Img.Tree;
        if (this.HideRootTree) CY += row.Level ? this.Img.Line: this.Img.Tree;
        var Dp = this.Ia(row, BJ);
        if (Dp) CY -= Dp;
        if (IY) CY -= IU.offsetLeft;
    } else CY = IU.clientWidth;
    if (!CY) CY = IV.clientWidth;
    var Hb = Get(row, BJ + "ClassEdit");
    if (Hb) Hb = this.Img.Ib + Hb;
    if (type == "Enum") {
        var Ic = this.GetEnum(row, BX, 1);
        if (!Ic) return;
        if (Grids.OnCustomStartEdit) {
            this.Ie = Grids.OnCustomStartEdit(this, row, BX, Get(row, BX), IU, CY);
            if (this.Ie) {
                this.EditMode = true;
                return;
            }
        }
        var type = Get(row, BX + "EnumType");
        if (type == null && this.Cols[BX]) type = this.Cols[BX]["EnumType"];
        if (type) {
            if (this.Hx == row && this.Hy == BJ) {
                this.CloseDialog(1);
                return;
            }
            var D = new Array(),
            E4 = new Array(),
            Bg = 0,
            BZ = Get(row, BX);
            if (!BZ) BZ = 0;
            var If = type & 8 ? "<center" + (_7V ? "><i style='display:inline-block;": " style='") + "width:" + this.Img.Enum + "px;overflow:hidden;background:url(" + this.Img.Grid + ") -" + (this.Img.Tree * 7 + 128) + "px -" + 15 * 32 + "px;'>" + _7D + "</center>" + (_7V ? "</i>": "") : "";
            var Ig = Get(row, BX + "FilterOff");
            for (var i = 0; i < Ic.length; i++) {
                D[Bg] = Ic[i];
                if (!D[Bg]) D[i] = "&nbsp;";
                E4[Bg] = If;
                Bg++;
            }
            var Hb = Get(row, BX + "ClassEnum");
            if (type == null && this.Cols[BX]) type = this.Cols[BX]["ClassEnum"];
            if (!Hb) Hb = "Enum";
            var Ih = null;
            if (type & 8) Ih = "-" + (this.Img.Tree * 6 + 128) + "px -" + 15 * 32 + "px";
            var T = this,
            BR = Get(row, BX);
            var M = this.ShowMenu(row, BJ, D, E4, function (BR) {
                if (Grids.OnEndEdit && Grids.OnEndEdit(T, row, BJ, true, BR)) return;
                if (Grids.OnValueChanged) BR = Grids.OnValueChanged(T, row, BJ, BR);
                if (T.SetValue(row, BJ, BR)) {
                    if (T.SaveValues) T.SaveCfg();
                    if (T.SortRow) T.SortRow(row, BJ, true);
                    if (Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(T, row, BJ);
                    T.RefreshCell(row, BJ);
                    T.Ii(row, BJ);
                    if (row.Space != null) T.SetScrollBars();
                    if (row.Kind == "Filter" && T.DoFilter) {
                        T.Ij(row, BJ);
                        if (BR == Ig) {
                            if (row[BJ + "Filter"]) T.SetFilterOp(row, BJ, 0);
                        } else {
                            T.DoFilter(row, BJ);
                        }
                    }
                }
            },
            this.HV ? 0 : 1, null, null, null, null, null, Hb, Ih, BR);
            M.Type = "Enum";
            var D4 = document.createElement("div");
            D4.style.position = "absolute";
            D4.className = this.Img.Style + Hb + "Icon";
            D4.style.zIndex = this.ZIndex;
            var IU = this.GetCell(row, BJ, 3).firstChild;
            var CY = IU.offsetWidth;
            var BL = ElemToParent(IU, _7T || _7H && !_7P ? document.documentElement: document.body);
            if (BJ == this.MainCol && !row.Fixed || row.Kind == "Filter" && Get(row, BJ + "ShowMenu") != 0) {
                IU = this.GetCell(row, BJ, 1);
                var Ik = IU.offsetLeft;
                BL[0] += Ik;
                this.Dialog.Tag.style.left = (parseInt(this.Dialog.Tag.style.left) + Ik) + "px";
                IU = this.GetCell(row, BJ);
                CY = IU.offsetWidth - Ik - this.Ia(row, BJ);
            }
            if (BL[1] < this.Dialog.Tag.offsetTop) {
                D4.style.left = BL[0] + "px";
                D4.style.top = BL[1] + "px";
                var EK = this.Il(row, BJ, 1);
                var Hb = Get(row, BJ + "ClassEdit");
                if (Hb) EK = EK.replace(/\s*\<div\s*class\s*\=\s*\'[^\']*\'/, "<div class='" + this.Img.Ib + Hb + "'");
                D4.innerHTML = EK;
                D4.style.width = "10px";
                D4.style.overflow = "hidden";
                document.body.insertBefore(D4, this.Dialog.Tag.nextSibling);
                D4.style.width = CY + "px";
                D4.style.width = (CY * 2 - D4.offsetWidth) + "px";
                D4.onclick = new Function("ev", "if(!ev) ev = event; CancelEvent(ev);" + this.Z + ".CloseDialog();");
                this.Im(D4.offsetWidth);
                this.Dialog.Caption = D4;
                this.Hk(D4, this["ShadowEnumIcon"]);
            }
            this.Hk(this.Dialog.Tag, this["ShadowEnum"]);
            return;
        }
        IV.style.borderTopWidth = "0px";
        IV.style.borderBottomWidth = "0px";
        IU.innerHTML = this.In(row, BX, CY);
        if (this.VarHeight) {
            this.UpdateRowHeight(row);
            this.SetScrollBars();
        }
        var GS = IU.firstChild;
        if (_7F) setTimeout(function () {
            GS.focus()
        },
        1);
        else GS.focus();
    } else if (type == "Pass") {
        if (IW) return;
        var E4 = document.createElement("input");
        E4.type = "password";
        E4.style.width = CY + "px";
        E4.className = Hb ? Hb: BO + "I" + _7g[type];
        IU.replaceChild(E4, IU.firstChild);
        if ((_7H || _7G || _7M || _7T) && color) E4.style.background = color;
        E4.value = this.GetValue(row, BX);
        focus(E4);
    } else {
        var V = this.FJ(row, BX);
        if (Grids.OnGetInputValue) V = Grids.OnGetInputValue(this, row, BX, V);
        if (Grids.OnCustomStartEdit) {
            this.Ie = Grids.OnCustomStartEdit(this, row, BX, V.Value, IU, CY);
            if (this.Ie) {
                this.EditMode = true;
                return;
            }
        }
        if (V.Type == "Lines") {
            if (!this.n) {
                var T = document.createElement(_7H || _7G || _7M ? "pre": "textarea");
                T.className = Hb ? Hb: BO + "TextArea";
                T.style.position = "absolute";
                T.style.left = "0px";
                T.style.top = "0px";
                T.style.visibility = "hidden";
                if (_7G || _7M || _7H) T.style.whiteSpace = "normal";
                else T.style.overflow = "visible";
                this.MainTag.appendChild(T);
                this.n = T;
            }
            var E4 = document.createElement("textarea"),
            T = this;
            E4.onkeydown = function (IL) {
                return T.Io(row, BX, E4, IL ? IL: event);
            };
            E4.onkeyup = function (IL) {
                return T.Ip(row, BX, E4, IL ? IL: event);
            };
            E4.className = Hb ? Hb: BO + "TextArea";
            E4.style.width = CY + "px";
            E4.style.overflow = _7S ? "visible": "auto";
            if (_7F || _7H || _7U || _7T) E4.style.height = IU.firstChild.offsetHeight + "px";
            if (_7F && _7I) E4.style.marginBottom = "-2px";
            IU.replaceChild(E4, IU.firstChild);
            E4.value = V.Value;
            if (_7T) E4.style.width = (CY * 2 - E4.offsetWidth) + "px";
            this.Ip(row, BX, E4, null);
            if (_7M) DetachEvent(document, "mousewheel", _7AF);
        } else {
            var E4 = document.createElement("input");
            E4.type = "text";
            if (!V.Mask) {
                var Bn = this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "";
                if (V.Type == "Int") E4.onkeypress = function (IL) {
                    KeyInt(IL ? IL: event, this, Bn);
                };
                else if (V.Type == "Float") E4.onkeypress = function (IL) {
                    KeyFloat(IL ? IL: event, this, Bn);
                };
                else if (V.Type == "Date") {
                    var BG = this.GetFormat(row, BX, 1);
                    var Hg = BG && BG.indexOf('t') >= 0 && BG.indexOf('h') >= 0;
                    E4.onkeypress = function (IL) {
                        KeyDate(IL ? IL: event, this, Hg, Bn);
                    };
                }
            }
            if (this.AutoCalendar && V.Type == "Date") {
                var T = this;
                E4.onkeyup = function (IL) {
                    var Dh = row[BX];
                    row[BX] = T.Iq(row, BX, this.value);
                    T.Hw(row, BX);
                    row[BX] = Dh;
                }
            }
            E4.className = Hb ? Hb: BO + "I" + _7g[V.Type];
            E4.style.width = CY + "px";
            IU.replaceChild(E4, IU.firstChild);
            E4.value = V.Value;
            var Ir = this.GetCell(row, BX, 3);
            if (Ir) {
                var It = CY + Ir.offsetWidth - Ir.scrollWidth;
                E4.style.width = It + "px";
            }
            var size = Get(row, BX + "Size");
            if (size == null) size = C[BX].Size;
            if (size) E4.maxLength = size;
        };
        if (_7H) E4.style.borderLeft = "1px solid " + color;
        if ((_7H || _7G || _7M || _7T) && color) E4.style.background = color;
        if (IW) E4.readOnly = true;
        if (V.Mask) {
            var Dt = Get(row, BX + "MaskColor");
            if (!Dt) Dt = this.Cols[BX]["MaskColor"];
            if (!Dt) Dt = "red";
            _7AG(E4, V.Mask, Grids.BA, Dt, this.Silent ? null: this.Iu('Ding.wav'), 1000);
        }
        if (_7F) E4.setAttribute('autocomplete', 'off');
        if ((type == "Link" || type == "Img") && this.VarHeight) {
            var Iv = this.GetSections();
            for (var i = Iv[0]; i < Iv[1]; i++) {
                var J = this.GetRow(row, i);
                if (J) J.firstChild.style.height = "1px";
            }
            this.EditMode = true;
            this.UpdateRowHeight(row);
            this.SetScrollBars();
        }
        focus(E4);
    };
    this.EditMode = true;
    IV.style.backgroundColor = color;
    this.ScrollIntoView(row, BJ);
    if (_7H) this.AW = (new Date).getTime();
    if (_7O) {
        this.UpdateRowHeight(row);
        this.SetScrollBars();
    }
    if (this.AutoCalendar && V && V.Type == "Date" && (!this.Dialog || this.Hx != row || this.Hy != BX)) {
        this.Hw(row, BX);
    }
    return;
};
_7m.prototype.EndEdit = function (row, BJ, save, Iw) {
    if (!row || !BJ || BJ == "Panel") return null;
    var C = this.Cols[BJ];
    if (!this.EditMode || !this.CanEdit(row, BJ)) return null;
    var BX = BJ;
    if (row.Spanned && Get(row, BJ + "Merge")) {
        var IT = Get(row, BJ + "MergeEdit");
        if (!IT) IT = 0;
        BX = this.ColNames[this.Cols[BJ].K][this.Cols[BJ].Pos + IT];
    }
    var D3 = this.Cols[BJ];
    if (!D3 && row.Space != null) D3 = this.Def["C"];
    var Dh = D3.Visible;
    D3.Visible = 1;
    var IU = this.GetCell(row, BJ),
    type = this.GetType(row, BX),
    Df = false,
    Ix = this.GetValue(row, BX),
    T = this;
    if (save && this.CanEdit(row, BX) == 2) save = false;
    D3.Visible = Dh;
    var EV = false;
    function Iy(BR) {
        return Grids.OnEndEdit && !Iw ? Grids.OnEndEdit(T, row, BX, save, BR) : 0;
    };
    if (IU) {
        if (type == "Enum") {
            var BR, Iz = _7s(IU, "select")[0];
            if (this.Ie && Grids.OnCustomEndEdit) {
                BR = Grids.OnCustomEndEdit(this, row, BJ, save, this.Ie);
            } else if (save && Iz) BR = Iz.selectedIndex;
            var Cu = Iy(BR);
            if (Cu == -1) return - 1;
            if (Cu == 1) return true;
            if (Cu == 2) return false;
            if (_7R && Iz) {
                if (_7V && window.event && window.event.srcElement == Iz) CancelEvent(window.event);
                Iz.parentNode.removeChild(Iz);
            }
            if (save) {
                if (Grids.OnValueChanged) BR = Grids.OnValueChanged(this, row, BX, BR);
                IU = this.GetCell(row, BJ);
                Df = this.SetValue(row, BX, BR);
            }
            IU.innerHTML = this.Il(row, BJ);
            if (row.Space != null && row.RelWidth) this.B6(row);
            IU.style.borderTopWidth = "";
            IU.style.borderBottomWidth = "";
        } else if (type == "Radio") {
            var BZ = this.IX;
            if (save && BZ != null) {
                var BG = this.GetFormat(row, BJ);
                if (BG) BG = BG.split(BG.charAt(0));
                if (BG && BZ >= BG.length - 7 || BZ < 0) BZ = null;
            }
            var Cu = Iy(null);
            if (Cu == -1) return - 1;
            if (Cu == 1) return true;
            if (Cu == 2) return false;
            if (save) {
                if (Grids.OnValueChanged) BZ = Grids.OnValueChanged(this, row, BX, BZ);
                IU = this.GetCell(row, BJ);
                Df = this.SetValue(row, BJ, BZ);
            }
            this.RefreshCell(row, BJ);
            this.IX = null;
        } else if (type == "Bool") {
            var Cu = Iy(save ? BZ: null);
            if (Cu == -1) return - 1;
            if (Cu == 1) return true;
            if (Cu == 2) return false;
            this.RefreshCell(row, BJ);
        } else {
            var BG = this.GetFormat(row, BX, 1),
            BR,
            Cu,
            I0;
            if (!BG && (type == "Img" || type == "Link")) BG = "|0|1";
            var I1 = type == "Lines" || (type == "Img" || type == "Link") && BG.split(BG.charAt(0))[1] != "0";
            var I0 = _7s(IU, I1 ? "textarea": "input")[0];
            if (I1 && _7M) AttachEvent(document, "mousewheel", _7AF);
            if (this.Ie && Grids.OnCustomEndEdit) {
                BR = Grids.OnCustomEndEdit(this, row, BJ, save, this.Ie);
            } else if (save && I0) BR = I0.value;
            if (BR != null) {
                if (BR == "") EV = true;
                var FH = this.FI(row, BJ, 0);
                try {
                    if (FH && BR.search(new RegExp(FH, "")) == -1) {
                        if (Grids.OnResultMask) Cu = Grids.OnResultMask(this, row, BJ, BR);
                        else Cu = 0;
                        if (!Cu) {
                            var Cs = Get(row, BJ + "ResultText");
                            if (!Cs) Cs = this.Cols[BJ].ResultText;
                            if (!Cs) Cs = this.GetAlert("ErrResultMask");
                            if (Cs) {
                                if (I0) var Bh = _7AH(I0);
                                alert(Cs);
                                this.AW = (new Date).getTime();
                                if (I0) setTimeout(function () {
                                    I0.focus();
                                    _7AI(I0, Bh[0], Bh[1]);
                                },
                                10);
                            }
                            return - 1;
                        } else if (Cu == 2) save = 0;
                        else if (Cu == 1) return - 1;
                    }
                } catch(B) {
                    alert("Wrong result mask at [" + row.id + "," + BX + "]");
                };
                if (save) {
                    if (I1 && this.I2(row, BX)) BR = BR.replace(/\r/g, "");
                    BR = this.Iq(row, BX, BR);
                    if (Grids.OnSetInputValue) BR = Grids.OnSetInputValue(this, row, BX, BR);
                }
            }
            var Bh = I0 ? _7AH(I0) : null;
            var Cu = Iy(BR);
            if (Cu == -1) {
                if (Bh) setTimeout(function () {
                    I0.focus();
                    _7AI(I0, Bh[0], Bh[1]);
                },
                10);
                return - 1;
            }
            if (Cu == 1) return true;
            if (Cu == 2) return false;
            this.EditMode = false;
            if (this.CanFocus(row, BX) == 2) this.Focus(this.I3, this.I4);
            if (save) {
                if (Grids.OnValueChanged) BR = Grids.OnValueChanged(this, row, BX, BR);
                Df = this.SetValue(row, BX, BR);
            }
            IU = this.GetCell(row, BJ);
            if (_7U && I0) I0.blur();
            if (I0) {
                I0.onkeypress = null;
                I0.onkeydown = null;
                I0.onkeyup = null;
            }
            if (IU) {
                IU.innerHTML = this.Il(row, BJ);
                if (Grids.OnRenderRow) Grids.OnRenderRow(this, row, BJ);
            }
            if (row.Space != null && row.RelWidth) this.B6(row);
        }
    }
    if (Iw) return true;
    if (this.AutoCalendar && type == "Date") this.CloseDialog(1);
    if ((this.VarHeight || _7O) && row.Space == null && row.r1) {
        if (this.Ip) this.Ip(row);
        if (this.MainCol) this.RefreshCell(row, this.MainCol);
        var D3 = this.GetSections();
        for (var i = D3[0]; i < D3[1]; i++) row["r" + i].firstChild.style.height = "1px";
        this.UpdateRowHeight(row);
        this.SetScrollBars();
    }
    this.EditMode = false;
    if (save && row[BJ + "Error"]) row[BJ + "Error"] = "";
    if (Df && this.SortRow) this.SortRow(row, BX, true);
    D3.Visible = 1;
    var I5 = this.GetCell(row, BJ);
    if (I5 != IU) this.Focus(row, BJ);
    if (Df) this.ColorRow(row);
    else this.ColorCell(row, BJ);
    D3.Visible = Dh;
    if (Df && Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(this, row, BX);
    if (row.Kind == "Filter" && Df) {
        if (type == "Enum") {
            this.Ij(row, BJ);
        }
        var Ig = Get(row, BX + "FilterOff");
        if (this.Paging == 3 && !(this.OnePage & 2) && !this.CK()) {
            Df = false;
            this.SetValue(row, BX, Ix);
            this.RefreshCell(row, BJ);
        } else if ((Ig != null && BR == Ig || BR === "" || !BR && EV && (type == "Int" || type == "Float")) && row[BX + "Filter"] && Get(row, BX + "DefaultFilter") != 0) {
            this.SetFilterOp(row, BX, 0);
        } else if (BR != Ig || Ig == null) this.DoFilter(row, BX);
    }
    if (Df && this.SaveValues) this.SaveCfg();
    this.Ii(row, BJ);
    if (_7H) {
        var D8 = this.MainTag.lastChild;
        if (D8.tagName != "input") {
            D8 = document.createElement("input");
            D8.style.position = "absolute";
            D8.style.visibility = "hidden";
            this.MainTag.appendChild(D8);
        }
        D8.focus();
    }
    if (_7O) {
        this.SetScrollBars();
    }
    return Df;
};
_7m.prototype.E7 = function () {
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, true) == -1) return false;
    for (var i = 0; i < Grids.length; i++) {
        var G = Grids[i];
        if (G && G.EditMode && G.EndEdit(G.FRow, G.FCol, true) == -1) return false;
    }
    return true;
};
_7m.prototype.Ii = function (row, BJ) {
    var BG = Get(row, BJ + "Refresh");
    if (BG == null) {
        BG = this.Cols[BJ];
        if (BG) BG = BG["Refresh"];
    }
    if (BG) {
        BG = BG.split(',');
        for (var i = 0; i < BG.length; i++) this.RefreshCell(row, BG[i]);
    }
};
_7m.prototype.Ip = function (row, BJ, T, IL) {
    var D3 = this.GetSections();
    if (T) {
        if (_7S) {
            if (T.style.overflow == "auto") {
                var P = this.n;
                P.style.width = T.offsetWidth + "px";
                P.value = T.value;
                var max = Get(row, "MaxHeight"),
                CZ = P.scrollHeight + T.offsetHeight - T.clientHeight;
                if (CZ < max - this.AM) {
                    T.style.overflow = "visible";
                    T.style.height = CZ + "px";
                    T.style.height = "";
                    this.GetCell(row, BJ, 3).style.height = "auto";
                }
            } else if (Get(row, "MaxHeight")) {
                var CZ = Get(row, "MaxHeight");
                if (T.offsetHeight > CZ - this.AM) {
                    T.style.overflow = "auto";
                    T.style.height = (CZ - this.AM) + "px";
                    this.GetCell(row, BJ, 3).style.height = (CZ - this.AM) + "px";
                }
            }
        } else {
            var P = this.n,
            max = Get(row, "MaxHeight"),
            CZ;
            P.style.height = "10px";
            if (_7T || _7F) {
                P.style.width = T.offsetWidth + "px";
                P.value = T.value;
                if (_7T) T.clientHeight;
                CZ = P.scrollHeight + T.offsetHeight - T.clientHeight;
            } else {
                P.style.width = (T.offsetWidth + 5) + "px";
                P.style.overflow = "scroll";
                P.innerHTML = T.value.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(new RegExp(String.fromCharCode(160), "g"), " ").replace(/\n/g, "<br/>") + _7D;
                CZ = P.scrollHeight + T.offsetHeight - T.clientHeight + 1;
                if (P.offsetWidth >= P.scrollWidth && CZ > _7Z + 10) CZ -= _7Z;
            };
            if (max) {
                max -= this.AM;
                T.style.height = (max < CZ ? max: CZ) + "px";
                var Bh = this.GetCell(row, BJ, 3).style;
                Bh.height = (max < CZ ? max: CZ) + "px";
                Bh.height = max < CZ ? max + "px": "";
            } else T.style.height = CZ + "px";
        }
    }
    if (this.MainCol && !_7F && !row.Fixed) {
        var BY = this.GetCell(row, this.MainCol, 3);
        if (BY) {
            BY = _7s(BY, _7V ? "u": "center");
            for (var i = 0; i < BY.length; i++) {
                BY[i].style.height = "1px";
            }
        }
    }
    var Ck = document.createElement("div"),
    DR = BJ ? this.Cols[BJ].K: -1,
    max = 0,
    C0;
    Ck.visibility = "hidden";
    Ck.position = "absolute";
    Ck.left = 0;
    Ck.top = 0;
    for (var i = D3[0]; i < D3[1]; i++) {
        if (i == DR) {
            var J = this.GetRow(row, i);
            J.firstChild.style.height = "1px";
            C0 = J.offsetHeight;
        } else {
            Ck.innerHTML = this.IK(row, i);
            this.MainTag.appendChild(Ck);
            C0 = Ck.offsetHeight;
            this.MainTag.removeChild(Ck);
        };
        if (C0 > max) max = C0;
    }
    if (Grids.OnRenderRow) Grids.OnRenderRow(this, row);
    this.UpdateRowHeight(row, max);
    this.SetScrollBars();
    this.ScrollIntoView(row);
};
_7m.prototype.Io = function (row, BJ, T, IL) {};
_7m.prototype.ActionStartEdit = function () {
    var row = this.FRow,
    BJ = this.FCol;
    if (this.EditMode || !row || row.Page || !BJ || !this.CanEdit(row, BJ)) return false;
    this.StartEdit(row, BJ);
    return true;
};
_7m.prototype.ActionCancelEdit = function () {
    if (!this.EditMode) return false;
    return this.EndEdit(this.FRow, this.FCol, false) === false;
};
_7m.prototype.ActionAcceptEdit = function () {
    if (!this.EditMode) return false;
    var Cu = this.EndEdit(this.FRow, this.FCol, true);
    return Cu === false || Cu === true;
};
_7m.prototype.ActionChangeBool = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !BJ || this.GetType(row, BJ) != "Bool" || !this.CanEdit(row, BJ)) return false;
    var BR = this.GetValue(row, BJ) ? 0 : 1;
    if (Grids.OnEndEdit && Grids.OnEndEdit(this, row, BJ, true, BR)) return false;
    if (Grids.OnValueChanged) BR = Grids.OnValueChanged(this, row, BJ, BR);
    this.SetValue(row, BJ, BR);
    if (Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(this, row, BJ);
    this.RefreshCell(row, BJ);
    if (row.Kind == "Filter" && this.DoFilter) this.DoFilter(row, BJ);
    return true;
};
_7m.prototype.ActionChangeRadioLeft = function () {
    this.I6(1);
};
_7m.prototype.ActionChangeRadioRight = function () {
    this.I6(0);
};
_7m.prototype.I6 = function (shift) {
    var I7 = this.FRow,
    Eb = this.FCol;
    if (!this.EditMode || !I7 || !Eb || this.GetType(I7, Eb) != "Radio") return false;
    var BG = this.GetFormat(I7, Eb);
    if (BG) {
        BG = BG.split(BG.charAt(0));
        var I8 = BG[1] - 0;
        if (! (I8 & 16)) {
            var BZ = this.IX != null ? this.IX: this.GetValue(I7, Eb);
            if (BZ == null || BZ === "") {
                if (shift) BZ = BG.length - 8;
                else BZ = 0;
            } else {
                if (shift && (BZ > 0 || I8 & 8 && BZ == 0)) BZ--;
                if (!shift && (BZ < BG.length - 8 || I8 & 8 && BZ == BG.length - 8)) BZ++;
            };
            this.IX = BZ;
            var Dh = I7[Eb];
            I7[Eb] = BZ;
            this.RefreshCell(I7, Eb);
            I7[Eb] = Dh;
        }
    }
};
_7m.prototype.I9 = function (Click, IL) {
    var row = Click.Row,
    BJ = Click.Col,
    P = Click.P,
    Y = Click.Y,
    C = this.Cols[BJ],
    B3 = "";
    this.JA = "Mouse";
    this.JB = P;
    this.JC = Y;
    if (!row || !BJ) {
        if (BJ == "Pager") {
            if (P <= this.Mouse.EdgeSize) B3 = this.HV ? "PagerHeaderRight": "PagerHeaderLeft";
            else if (P >= this.Pager.Width - this.Mouse.EdgeSize - this.AH + 1) B3 = this.HV ? "PagerHeaderLeft": "PagerHeaderRight";
            else B3 = "PagerHeader";
        }
    } else if (BJ == "Panel") {
        B3 = "Panel";
        var x = P,
        JD = this.Img.Panel;
        if (this.Dragging && C.Move) x -= JD;
        if (x < 0) {
            x = 1000;
            if (Is(row, "CanDrag")) B3 = "Move";
        }
        if (this.Selecting && C.Select) x -= JD;
        if (x < 0) {
            x = 1000;
            if (Is(row, "CanSelect")) B3 = "Select";
        }
        if (this.Deleting && C.Delete) x -= JD;
        if (x < 0) {
            x = 1000;
            if (Is(row, "CanDelete")) B3 = "Delete";
        }
        if (this.Copying && C.Copy) x -= JD;
        if (x < 0) {
            x = 1000;
            if (Is(row, "CanCopy")) B3 = "Copy";
        }
        if (row.Dx) B3 = "Panel";
        if (row == this.XHeader) B3 = "Header" + B3;
        else if (row.Kind == "Group") B3 = "Grouped";
        else if (row.Kind == "Filter") B3 = "Filtered";
        else if (row.Kind == "Search") B3 = "Searched";
    } else if (row == this.XHeader) {
        if (P <= this.Mouse.EdgeSize) B3 = "HeaderLeft";
        else if (P >= C.Width - this.Mouse.EdgeSize - this.AH + 1) B3 = "HeaderRight";
        else if (this.XHeader.Rows > 1 && this.XHeader.ARow != this.XHeader.Main) B3 = "HeaderMulti";
        else {
            if (this.Sorting && C.CanSort & 1 && C.Type != "Pass") {
                var JE = true;
                if (this.SortIcons == 2) {
                    var IU = this.GetCell(row, BJ),
                    Hm = null;
                    if (IU) Hm = IU.getElementsByTagName("tr")[0];
                    if (Hm) Hm = Hm.cells[Hm.cells.length - 1];
                    if (Hm) {
                        var x = ElemToParent(Hm, IU)[0];
                        if (P < x || P > x + Hm.offsetWidth) JE = false;
                    }
                }
                if (JE) {
                    B3 = "Sort";
                    this.ACol = BJ;
                    if (this.SortIcons >= 2) {
                        var IU = this.GetCell(row, BJ);
                        if (IU) {
                            var dir = this["ReversedSortIcons"] ? 1 : 0;
                            B3 += Y >= IU.offsetHeight / 2 ? "Down": "Up";
                        }
                    }
                }
            }
            if (!B3) B3 = "Header";
        }
    } else {
        var Ca = this.GetType(row, BJ);
        if (Ca == "Gantt") B3 = "Gantt";
        if (Ca == "Bool") {
            if (BJ == this.MainCol && !row.Fixed) {
                var CY = row.Level * this.Img.Line + this.Img.Tree;
                if (this.HideRootTree) CY -= row.Level ? this.Img.Line: this.Img.Tree;
            } else {
                var JF = row.Kind == "Filter" && Get(row, BJ + "ShowMenu") != 0 ? this.Img.Row: 0;
                var CY = row.Space != null ? Get(row, BJ + "Width") : C.Width;
                if (CY <= 0) {
                    var IU = this.GetCell(row, BJ);
                    if (IU) CY = IU.offsetWidth;
                }
                CY = (CY - this.Img.Row - JF) / 2 + JF;
            };
            if (P >= CY && P <= CY + this.Img.Height) B3 = "Bool";
        }
        if (Ca == "Radio") B3 = "Radio";
        if ((Ca == "Img" || Ca == "Link") && !this.EditMode) {
            var BZ = this.JG(row, BJ);
            if (BZ) {
                var IU = this.GetCell(row, BJ, 1),
                BL = 0,
                E4 = IU;
                function JH(JI) {
                    for (var i = JI.firstChild; i; i = i.nextSibling) {
                        if (i.firstChild && i.firstChild.offsetWidth) JH(i);
                        else {
                            var a = i.offsetHeight * i.offsetWidth;
                            if (a > BL) {
                                BL = a;
                                E4 = i;
                            }
                        }
                    }
                };
                JH(E4);
                var E = EventXYToElement(IL, E4);
                if (E[0] >= 0 && E[0] <= E4.offsetWidth && E[1] >= 0 && E[1] <= E4.offsetHeight) B3 = "Link";
            }
        }
        if (row.Space != null) {
            if (Ca == "DropCols") B3 = "DropCols";
            else if (Ca == "Button") B3 = "Button" + BJ;
        }
        if (!B3 && BJ == this.MainCol && !row.Fixed) {
            var x = P - row.Level * this.Img.Line;
            if (this.HideRootTree) x += this.Img.Line;
            if (x < this.Img.Tree && x >= 0 && Is(row, "CanExpand") && this.HasChildren(row)) B3 = "Expand";
        }
        if (!B3 && this.Ia(row, BJ)) {
            var IU = this.GetCell(row, BJ),
            Hm = null;
            if (IU) Hm = IU.getElementsByTagName("tr")[0];
            if (Hm) Hm = Hm.cells[Hm.cells.length - 1];
            if (Hm) {
                var BL = ElemToParent(Hm, IU);
                if (P >= BL[0] && P <= BL[0] + Hm.offsetWidth && Y >= BL[1] && Y <= BL[1] + Hm.offsetHeight) {
                    var JJ = Get(row, BJ + "Button");
                    if (JJ == null && this.Cols[BJ]) JJ = this.Cols[BJ].Button;
                    if (JJ == "Date" || !JJ && this.GetType(row, BJ) == "Date") B3 = "ButtonCalendar";
                    else if (JJ == "Defaults") B3 = "ButtonDefaults";
                    else if (JJ && JJ != "None") B3 = "ButtonUser";
                }
            }
        }
        if ((!B3 || B3 == "Link") && this.EditMode && row == this.FRow && BJ == this.FCol) B3 = "Edited";
        if (!B3) B3 = "Cell";
    };
    if (this.ResizingMain && row && row == this.XS.lastChild && IL) {
        var Ik = EventXYToElement(IL, row.r1)[0];
        if (!this.HV) Ik = row.r1.offsetWidth - Ik;
        if (Ik >= 0 && Ik <= _7Y) B3 = "Resize";
    }
    if (!B3) B3 = "Empty";
    return B3;
};
_7m.prototype.JK = function (IL) {
    var shift = IL.shiftKey;
    alt = IL.altKey;
    JL = IL.ctrlKey;
    if (shift == null) shift = Grids.Keys[16];
    if (JL == null) JL = Grids.Keys[17];
    if (alt == null) alt = Grids.Keys[18];
    return (shift ? "Shift": "") + (JL ? "Ctrl": "") + (alt ? "Alt": "");
};
_7m.prototype.JM = function (IL) {
    if (this.EditMode) return;
    try {
        var V = GetWindowScroll();
        var S = document.selection;
        if (S) {
            S = S.createRange();
            if (S && S.text) {
                if (IL) S.moveToPoint(IL.clientX / _7AJ, IL.clientY / _7AJ);
                else S.collapse();
                S.select();
            }
        } else if (_7P && window.getSelection) {
            S = getSelection();
            if (S) S.collapse();
        }
        if (!this.Dialog && _7F && this.e && this.e.focus) this.e.focus();
        var JN = GetWindowScroll();
        if (JN[0] != V[0] || JN[1] != V[1]) _7AK(V[0], V[1]);
    } catch(B) {}
};
_7m.prototype.GridClick = function (IL) {
    if (!IL) IL = {
        clientX: this.JO,
        clientY: this.JP
    };
    this.AW = (new Date).getTime();
    if (this.Ae) return true;
    if (this.Af) {
        if ((new Date).getTime() < this.Af) return true;
        this.Af = null;
    }
    if (Grids.Az && Grids.Az.II) Grids.Az.II.apply(this);
    var Click = this.GetClick(IL),
    row = Click.Row,
    BJ = Click.Col;
    if (row != this.XHeader) this.JQ = 0;
    this.Hx = null,
    this.Hy = null;
    if (this.Dialog) {
        this.Hx = this.Dialog.Row;
        this.Hy = this.Dialog.Col;
    }
    if (!this.EditMode || !this.AutoCalendar || this.Hx != row || this.Hy != BJ || row && this.GetType(row, BJ) != "Date") this.CloseDialog();
    if (Click.Y < 0) {
        this.EndEdit(this.FRow, this.FCol, true);
        CancelEvent(IL);
        return false;
    }
    var name = this.I9(Click, IL),
    Cu = false;
    if (name == "Radio" && this.CanEdit(row, BJ)) {
        var BL = this.GetCell(row, BJ).getElementsByTagName(_7V ? "u": "center");
        var BG = this.GetFormat(row, BJ),
        I8 = BG ? BG.split(BG.charAt(0))[1] - 0 : 0;
        var shift = row.Kind == "Filter" ? 1 : 0,
        JE = false;
        for (var i = 0; i < BL.length - shift; i++) {
            var E = EventXYToElement(IL, BL[i + shift], !_7O || _7P);
            if (E[0] >= 0 && E[0] <= this.Img.Row && E[1] >= 0 && E[1] <= this.Img.Height) {
                var BZ = this.IX != null ? this.IX: this.GetValue(row, BJ),
                Df = 0;
                if (I8 & 16) {
                    var x = 1 << i;
                    if (BZ & x) BZ &= ~x;
                    else BZ |= x;
                    Df = 1;
                } else if (BZ != i || I8 & 8) {
                    BZ = BZ != i ? i: null;
                    Df = 1;
                }
                if (Df) {
                    if (this.EditMode && this.FRow == row && this.FCol == BJ) {
                        this.IX = BZ;
                        var Dh = row[BJ];
                        row[BJ] = BZ;
                        this.RefreshCell(row, BJ);
                        row[BJ] = Dh;
                    } else {
                        if (this.EditMode) this.EndEdit(this.FRow, this.FCol, 1);
                        if (Grids.OnEndEdit && Grids.OnEndEdit(this, row, BJ, true, BZ)) break;
                        if (Grids.OnValueChanged) BZ = Grids.OnValueChanged(this, row, BJ, BZ);
                        if (this.SetValue(row, BJ, BZ) && Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(this, row, BJ);
                        this.RefreshCell(row, BJ);
                        if (row.Kind == "Filter" && this.DoFilter) this.DoFilter(row, BJ);
                    }
                }
                JE = true;
                break;
            }
        }
        if (!JE) name = "Cell";
    }
    if (this.EditMode && name == "ButtonCalendar" && this.ACol == this.FCol && this.ARow == this.FRow);
    else if (name != "Edited" && name != "Radio" && !this.E7()) return true;
    if (Grids.OnPanelClick && row && BJ && row[BJ + "ActionNum"] != null && Grids.OnPanelClick(this, row[BJ + "ActionNum"])) name = "Toolbar";
    if (row && BJ && this.GetType(row, BJ) == "Button") {
        var T = this;
        function JR(JS) {
            var data = row[BJ + "ReloadData"];
            var JT = row[BJ + "Tab"] - 0;
            if (JT) {
                row[BJ] = 1;
                var C = row.Cells;
                for (var i = 0; i < C.length; i++) {
                    if (BJ != C[i] && row[C[i] + "Tab"] == JT) {
                        row[C[i]] = 0;
                    }
                }
                T.Calculate(1, 1, 1);
                T.RefreshRow(row);
            }
            if (data && JS != null) {
                data = data.split(data.charAt(0));
                data = data[JS + 1];
            }
            if (data) {
                T.Data.Data.Url = null;
                T.Data.Data.Tag = null;
                T.Data.Data.Data = null;
                if (_7u(data) == '<') T.Data.Data.Data = data;
                else if (GetElem(data)) T.Data.Data.Tag = data;
                else T.Data.Data.Url = data;
                T.ReloadBody(null, 0, "Reload");
            }
        };
        if (row[BJ + "Menu"]) {
            if (this.Hx != row || this.Hy != BJ) {
                var M = row[BJ + "Menu"];
                M = M.split(M.charAt(0));
                var D = new Array(),
                Bg = 0;
                for (var i = 1; i < M.length; i++) {
                    D[Bg] = M[i];
                    if (!D[Bg]) D[i] = "&nbsp;";
                    Bg++;
                }
                var Hb = Get(row, BJ + "ClassButton");
                this.ShowMenu(row, BJ, D, null, function (BR) {
                    T.CloseDialog();
                    var JT = row[BJ + "Tab"] - 0;
                    if (JT) {
                        row[BJ + "IconChecked"] = M[BR + 1];
                        JR(BR);
                    }
                    var JU = row[BJ + "Actions"];
                    if (JU) {
                        JU = JU.split(JU.charAt(0));
                        JU = JU[BR + 1];
                        if (JU) {
                            JU = T.B4(JU, row.Cells, 1);
                            var F = new TCalc();
                            F.Row = row;
                            F.Col = BJ;
                            F.Grid = T;
                            F["Value"] = row[BJ];
                            JU(F);
                            T.Calculate(1, 1, 1);
                        }
                    }
                },
                1, null, null, null, null, null, Hb, null);
            }
        } else JR();
        if (this.DoAction && row[BJ + "Action"]) {
            Cu = this.DoAction(row, BJ);
            this.Calculate(1, 1, 1);
        }
        this.JV();
    }
    var ED = this.JK(IL);
    this.JW("", "BeforeClick", "", row, BJ);
    if (Grids.OnClick && row && BJ) Cu = Grids.OnClick(this, row, BJ, Click.P, Click.Y, false, IL) || Cu;
    if (!Cu) Cu = this.JW(ED, "Click", name, row, BJ);
    this.JW("", "AfterClick", "", row, BJ);
    if (Cu) {
        CancelEvent(IL);
        this.JM(IL);
        this.JV();
    }
    if (row == this.FRow && BJ == this.FCol) this.JX(this.FRow, this.FCol);
    return ! Cu;
};
_7m.prototype.GridDblClick = function (IL) {
    var Click = this.GetClick(IL),
    Cu = false,
    row = Click.Row,
    BJ = Click.Col;
    var CE = Grids.Focused;
    var name = this.I9(Click, IL);
    var Cu, ED = this.JK(IL);
    this.JW("", "BeforeDblClick", "", row, BJ);
    if (Grids.OnDblClick) Cu = Grids.OnDblClick(this, row, BJ, Click.P, Click.Y, IL);
    if (!Cu) Cu = this.JW(ED, "DblClick", name, row, BJ);
    this.JW("", "AfterDblClick", "", row, BJ);
    if (Cu) CancelEvent(IL);
    return ! Cu;
};
_7m.prototype.GridRightClick = function (IL) {
    var Click = this.GetClick(IL),
    Cu = false,
    row = Click.Row,
    BJ = Click.Col;
    this.JV();
    var name = this.I9(Click, IL);
    var Cu, ED = this.JK(IL);
    this.JW("", "BeforeRightClick", "", row, BJ);
    if (Grids.OnRightClick) Cu = Grids.OnRightClick(this, row, BJ, Click.P, Click.Y, IL);
    if (!Cu) Cu = this.JW(ED, "RightClick", name, row, BJ);
    this.JW("", "AfterRightClick", "", row, BJ);
    if (Cu) CancelEvent(IL);
    return ! Cu;
};
function _7AL(IL) {
    if (!IL) IL = window.event;
    var Cu = true;
    var CE = Grids.Focused;
    if (CE && !CE.Ae) {
        var C = (new Date).getTime();
        if (CE.AW && CE.AW + 500 > C) CE.AW = null;
        else {
            CE.CloseDialog(true);
            CE.JQ = 0;
            var ED = CE.JK(IL);
            var Cu = CE.JW(ED, "Click", "Outside", null, null);
            if (!Cu) {
                CancelEvent(IL);
                return;
            }
            Grids.Focused = null;
        }
    }
    var BL = Grids.Active;
    if (BL) {
        BL.AS = null;
        BL.JY = null;
        Grids.Active = null;
        if (_7M) DetachEvent(document, "mousewheel", _7AF);
    }
    if (Grids.Click) return Grids.Click(IL);
    if (!Cu) CancelEvent(IL);
};
function _7AF(IL) {
    if (!IL) IL = window.event;
    var Cu = true;
    if (Grids.Active && !Grids.Active.Ae && !Grids.Active.NoVScroll) Cu = Grids.Active.GridMouseWheel(_7M && _7N < 9.5 ? -IL.wheelDelta: IL.wheelDelta);
    if (!Cu) CancelEvent(IL);
    return Cu;
};
function _7AM(IL) {
    try {
        if (Grids) {
            for (var i = 0; i < Grids.length; i++) {
                var G = Grids[i];
                if (G && G.E2 && !G.JZ) {
                    var H = G.XB.firstChild;
                    if (H && H.MasterRow) G.Ja(H.MasterRow);
                    H.MasterRow = null;
                }
            }
            for (var i = 0; i < Grids.length; i++) {
                var G = Grids[i];
                if (G && !G.JZ) {
                    if (G.EditMode) {
                        try {
                            G.EndEdit(G.FRow, G.FCol, 0, 1);
                        } catch(B) {}
                    }
                    G.Clear(1);
                }
            }
        }
    } catch(B) {};
};
function _7AN() {
    if (Grids.A2) return;
    if (_7T) _7AO();
    if (Grids.MaxHeight || Grids.MaxWidth) {
        var F = Grids.Jb,
        Jc = document.body;
        if (!F) {
            F = new Array();
            Grids.Jb = F;
        }
        for (var i = 0; i < Grids.length; i++) {
            var G = Grids[i];
            if (G && G.MainTag && (G.MaxHeight || G.MaxWidth) && !G.Jd && !G.JZ) {
                for (var Bg = G.MainTag.parentNode; Bg && Bg.tagName.toLowerCase() != "body"; Bg = Bg.parentNode) {
                    var Bh = GetStyle(Bg);
                    if (Bh.overflow != "" && Bh.overflow.toLowerCase() != "visible" || Bh.display.toLowerCase() == 'none' || Bh.position.toLowerCase() == 'absolute') {
                        G.Jd = Bg;
                        Bg.Je = null;
                        for (var j = 0; j < F.length; j++) if (F[j] == Bg) break;
                        if (j == F.length) F[F.length] = Bg;
                        break;
                    }
                }
                if (!G.Jd) {
                    G.Jd = Jc;
                    for (var j = 0; j < F.length; j++) if (F[j] == Jc) break;
                    if (j == F.length) F[F.length] = Jc;
                    Jc.Je = null;
                }
            }
        }
    }
    if (Grids.MaxHeight) {
        for (var j = 0; j < F.length; j++) {
            var Ch, D8;
            if (F[j] == Jc) {
                var Jf = document.documentElement;
                if (_7H) Ch = window.innerHeight;
                else if (_7I && (_7R || _7F)) Ch = Jf.clientHeight;
                else Ch = Jc.clientHeight;
                if (_7S || _7X) D8 = Jc.scrollHeight;
                else if (_7H || _7T) D8 = Jf.scrollHeight;
                else if (_7F) {
                    var HT = Jc.offsetHeight,
                    Hu = Jf.offsetHeight;
                    D8 = _7J && !_7K && Hu == window.innerHeight ? HT + 22 : Hu;
                } else D8 = Jf.offsetHeight + (_7G ? 22 : 0);
            } else {
                Ch = F[j].clientHeight;
                D8 = F[j].scrollHeight;
                if (Ch == D8 && !_7R) {
                    for (var Bg = F[j].lastChild; Bg; Bg = Bg.previousSibling) {
                        if (Bg.offsetHeight) {
                            D8 = Bg.offsetTop + Bg.offsetHeight - (Bg.offsetParent != F[j] ? F[j].offsetTop: 0);
                            if (Ch < D8) D8 = Ch;
                            break;
                        }
                    }
                }
            };
            F[j].Jg = 0;
            F[j].Jh = 0;
            F[j].Ji = (Ch - D8) / _7AJ;
        }
        for (var i = 0; i < Grids.length; i++) {
            var G = Grids[i];
            if (G && G.Jd && !G.JZ) {
                if (G.Rendering) return;
                G.Jd.Jh += G.MaxHeight;
                G.Jd.Jg += G.MainTag.offsetHeight;
            }
        }
    }
    for (var i = 0; i < Grids.length; i++) {
        var G = Grids[i];
        if (G && G.MainTag && !G.JZ) {
            var D4 = G.MainTag,
            Jj = G.MinTagHeight,
            Jk = G.MinTagWidth;
            if (Jj == null) Jj = G.MinHeight;
            if (Jk == null) Jk = G.MinWidth;
            if (G.Jd) {
                var Jl = 0,
                EU = G.Jd;
                if (G.MaxHeight && (EU.Ji != EU.Je && (EU.Ji < 0 || EU.Ji > 8) || EU.Je == null)) {
                    var CZ = Math.floor((EU.Ji + EU.Jg) / EU.Jh * G.MaxHeight - (_7S ? 0 : G.AL));
                    if (CZ < Jj) CZ = Jj;
                    if (CZ > 20) G.MainTag.style.height = CZ + "px";
                }
                if (G.MaxWidth && !_7U) {
                    var EU = G.Jd,
                    CY = EU.clientWidth;
                    var top = D4.offsetTop,
                    Jm = top + D4.offsetHeight;
                    for (var Cs = D4.parentNode.firstChild; Cs; Cs = Cs.nextSibling) {
                        if (Cs != D4 && Cs.offsetTop >= top && Cs.offsetTop < Jm && Cs.offsetLeft < CY && Cs.offsetLeft >= D4.offsetLeft + D4.offsetWidth && Cs.offsetWidth && Cs.offsetHeight) CY = Cs.offsetLeft;
                    }
                    CY = (CY - D4.offsetLeft + (EU != D4.offsetParent ? EU.offsetLeft: 0) - (_7S ? 0 : G.AK) - ((!_7I || _7M || _7G) && EU == Jc ? 8 : 0));
                    if (CY < Jk) CY = Jk;
                    if (CY > 20) D4.style.width = CY + "px";
                }
            }
            if (Jk && D4.offsetWidth < Jk) D4.style.width = Jk + "px";
            if (Jj && D4.offsetHeight < Jj) D4.style.height = Jj + "px";
            if (G.Ag != D4.offsetWidth || G.Ah != D4.offsetHeight) G.SetScrollBars();
        }
    }
    if (Grids.MaxHeight) for (var j = 0; j < F.length; j++) F[j].Je = F[j].Ji;
};
function _7AP() {
    var G = Grids.Active,
    scroll = 0;
    if (Grids.Az && G && G.AS) {
        var Cs = G.q.scrollTop;
        if (G.AS == 1) {
            G.q.scrollTop -= G.u * 50;
        } else {
            G.q.scrollTop += G.u * 50;
        };
        if (Cs != G.q.scrollTop) scroll++;
    }
    if (Grids.Az && G && G.JY) {
        var Cs = G.r.scrollLeft;
        if (G.JY < 0) G.r.scrollLeft -= G.v * 50;
        else if (G.JY > 0) G.r.scrollLeft += G.v * 50;
        if (Cs != G.r.scrollLeft) scroll++;
    }
    if (scroll) G.Scrolled();
    if (G) {
        G.Jn++;
        if (G.ARow && G.ACol) {
            if (G.Jo) {
                if (G.Jn * 50 > G.TipEnd) {
                    G.HideTip();
                    G.Jn = -1e5;
                }
            } else if (G.Jn * 50 > G.TipStart) {
                var Jp = Get(G.ARow, G.ACol + "Tip"),
                Jq = Get(G.ARow, G.ACol + "TipClass");
                if (Jp == null) {
                    Jp = Get(G.ARow, "Tip");
                    Jq = Get(G.ARow, "TipClass");
                }
                var IL = {
                    clientX: G.JO,
                    clientY: G.JP
                };
                if (Grids.OnTip) {
                    var Click = G.GetClick(IL);
                    Jp = Grids.OnTip(G, G.ARow, G.ACol, Jp, G.JO, G.JP, Click.P, Click.Y);
                }
                if (Jp) {
                    var BL = EventXYToElement(IL, _7S || _7F || _7M || _7G ? document.body: document.documentElement);
                    var Bh = G.TipPos.split(",");
                    G.ShowTip(Bh[0] - 0 + BL[0], Bh[1] - 0 + BL[1], Jp, Jq);
                } else G.Jn = -1e5;
            }
        }
    }
};
function _7AQ(IL) {
    try {
        var Jf = document.documentElement;
    } catch(B) {
        return;
    };
    if (!IL) IL = event;
    if (_7R && IL.clientX < 0 && IL.clientY < 0 || !_7R && !IL.relatedTarget) {
        DocMouseMove(IL);
        _7n(IL);
        if (Grids.Active) {
            var G = Grids.Active;
            var row = G.ARow;
            G.HideHint();
            G.ARow = null;
            if (row) {
                if (row.Space != null) row.ACol = null;
                G.ColorRow(row);
            }
            if (G.HoverPager) G.HoverPager(null);
        }
    }
};
function DocMouseMove(IL) {
    if (!IL) IL = event;
    var Cu = true;
    if (IL.Handled) return true;
    if (Grids.Jr) {
        try {
            Grids.Jr.className = Grids.Js;
            Grids.Jr = null;
        } catch(B) {}
    }
    var D8 = Grids.Az;
    if (D8) {
        var G = D8.Grid;
        if (Grids.A5) G.Jt(IL);
        Cu = !G.Ju(G.GetClick(IL), IL, true);
        if (!G.EditMode) G.Refresh();
    }
    if (!Cu) CancelEvent();
};
function _7n(IL) {
    if (!IL) IL = event;
    var Cu = true;
    var D8 = Grids.Az;
    if (D8 && D8.II) {
        Cu = !D8.II.apply(D8.N, [true]);
        if (Grids.A5) D8.N.Jv(IL);
    }
    Grids.Az = null;
    if (!Cu) CancelEvent();
};
_7m.prototype.SpaceMouseMove = function (IL, I, BJ) {
    var J = _7c(this.XS, I);
    if (!J) return;
    this.ARow = J;
    this.ASec = 3;
    if (BJ) J["XACol"] = BJ;
    else {
        J.ACol = J["XACol"];
        J["XACol"] = null;
        if (J.Tag) this.GridMouseMove(IL);
    }
};
function _7AR(IL) {
    if (!IL) IL = window.event;
    var Jw = IL.keyCode;
    if (!Jw) Jw = IL.charCode;
    Grids.Keys[Jw] = true;
    if (Jw == 45) Grids.BA = !Grids.BA;
    var Cu = true;
    if (Grids.Focused && !Grids.Focused.Ae) Cu = Grids.Focused.Jx(IL);
    if (!Cu) CancelEvent(IL);
};
function _7AS(IL) {
    if (!IL) IL = window.event;
    var Jw = IL.keyCode;
    if (!Jw) Jw = IL.charCode;
    if (!Jw) Grids.Keys = new Array();
    else Grids.Keys[Jw] = null;
};
function _7AT(IL) {
    if (!IL) IL = window.event;
    var Cu = true;
    var Jw = IL.keyCode ? IL.keyCode: IL.charCode;
    if (Grids.A6 == Jw) {
        Grids.A6 = 0;
        Cu = false;
    }
    if (Grids.Focused && !Grids.Focused.Ae) {
        if (Grids.OnKeyPress) {
            if (Grids.OnKeyPress(Grids.Focused, Jw, IL)) return true;
        }
        if (Jw == 9) Cu = false;
    }
    if (!Cu) CancelEvent(IL);
};
_7m.prototype.Jx = function (IL) {
    if (this.Ae) return true;
    var Jw = IL.keyCode ? IL.keyCode: IL.charCode;
    var ED = this.JK(IL);
    if (Grids.OnKeyDown && Grids.OnKeyDown(this, Jw, IL, this.Ar[Jw], ED)) return true;
    var I7 = this.FRow,
    Eb = this.FCol,
    target = "";
    if (this.EditMode) {
        target = "Edit";
        if (_7R && (Jw == 37 || Jw == 39) && I7 && Eb && this.GetType(I7, Eb) == "Enum") IL.keyCode++;
        if (this.Jy(this.FRow, this.FCol)) {
            target = "EditMultiline";
            if (_7S && (Jw == 38 || Jw == 40)) this.Af = (new Date).getTime() + 100;
            if (Jw == 13 && ED.search(/Alt|Ctrl/) < 0 && this.I2(I7, Eb) && this.CanEdit(I7, Eb) == 1) return true;
        }
    }
    this.JA = "Key";
    this.Hx = null,
    this.Hy = null;
    if (this.Dialog) {
        this.Hx = this.Dialog.Row;
        this.Hy = this.Dialog.Col;
        if (Jw == 9 && this.Dialog.Type == "Enum") target = "Edit";
        if (Jw != 16 && Jw != 17 && Jw != 18 && (!this.EditMode || !this.AutoCalendar || this.GetType(this.FRow, this.FCol) != "Date")) this.CloseDialog();
    }
    var Cu = this.JW(ED, this.Ar[Jw], target, I7, Eb);
    if (Cu) {
        CancelEvent(IL);
        Grids.A6 = Jw;
    } else Grids.A6 = 0;
    return ! Cu;
};
_7m.prototype.GetColLeft = function (row, left) {
    var C = this.Cols;
    var BJ = null,
    DR = 1,
    x, a = -1,
    Jz = 0,
    J0 = this.HV ? -1 : 1;
    left -= this.AI / 2;
    var H = this.XHeader.Visible ? this.b: this.c;
    if (!H[1].offsetWidth) {
        if (this.b[1].offsetWidth) H = this.b;
        else if (this.d[1].offsetWidth) H = this.d;
    }
    if (H[1 - J0] && left < H[1 - J0].offsetWidth) {
        a = 1 - J0;
        left -= this.J1 / 2;
    } else {
        DR = 2;
        if (H[1 - J0]) left -= H[1 - J0].offsetWidth;
        if (left < H[1].offsetWidth) {
            a = 1;
            left += this.GetScrollLeft();
            left -= this.AF / 2;
        } else {
            left -= H[1].offsetWidth;
            DR = 3;
            if (H[1 + J0] && left < H[1 + J0].offsetWidth) {
                a = 1 + J0;
                left -= this.J2 / 2;
            } else {
                if (H[1 + J0]) left -= H[1 + J0].offsetWidth;
            }
        }
    };
    if (a < 0) return [null, 0];
    var S = this.ColNames[a];
    var Dr = row && row.Spanned;
    for (var i = 0; i < S.length; i++) {
        var J3 = S[J0 < 0 ? S.length - i - 1 : i];
        var BY = C[J3];
        if (BY.Visible || Dr && Get(row, J3 + "Span") > 1) left -= Dr ? this.DS(row, J3) : BY.Width + this.CellSpacing;
        if (left <= 0) {
            BJ = J3;
            x = left + (Dr ? this.DS(row, BJ) : BY.Width + this.CellSpacing);
            break;
        }
    }
    return [BJ, x];
};
_7m.prototype.GetClick = function (IL) {
    var J4 = EventXYToElement(IL, this.e);
    var Click = new Object();
    Click.Row = this.ARow;
    if (!Click.Row) {
        if (this.ASec == -6) {
            J4 = EventXYToElement(IL, this.g);
            if (J4[0] >= 0 && J4[0] < this.g.offsetWidth && J4[1] >= 0 && J4[1] < this.g.offsetHeight) {
                Click.Col = "Pager";
                Click.P = J4[0];
                Click.Y = J4[1];
                return Click;
            }
        }
        Click.Col = null;
        return Click;
    } else if (Click.Row.Kind == "User") {
        Click.Row = null;
        return Click;
    }
    if (Click.Row.Space != null) {
        Click.Col = Click.Row.ACol;
        var IU = this.GetCell(Click.Row, Click.Col);
        if (IU) {
            var size = ElemToParent(IU, this.e);
            Click.P = J4[0] - size[0];
            Click.Y = J4[1] - size[1];
        }
        return Click;
    }
    var S = this.GetSections(),
    J = this.GetRow(Click.Row, S[0]);
    if (!J) return new Object();
    var size = ElemToParent(J.firstChild, this.e);
    Click.Y = J4[1] - size[1];
    var BL = this.GetColLeft(Click.Row, J4[0]);
    Click.Col = BL[0];
    Click.C = this.Cols[Click.Col];
    Click.P = BL[1];
    return Click;
};
_7m.prototype.Ju = function (Click, IL, IN) {
    var D8 = Grids.Az;
    if (D8 && !D8.Action) {
        var Ik = Math.floor(IL.clientX / (IL.type ? _7AJ: 1)) - D8.P,
        J5 = Math.floor(IL.clientY / (IL.type ? _7AJ: 1)) - D8.Y;
        var a = this.Mouse.DragSize;
        if (Ik >= a || Ik <= -a || J5 >= a || J5 < -a) {
            Click = {
                P: D8.J6,
                Y: D8.J7,
                Col: D8.Col,
                Row: D8.Row
            };
            var name = this.I9(Click, {
                clientX: D8.P,
                clientY: D8.Y
            });
            var ED = this.JK(IL);
            var Cu = this.JW(ED, "Drag", name, D8.Row, D8.Col);
            if (Cu) {
                D8.J8 = true;
                if (!this.SelectingText) this.JM(IL);
            } else Grids.Az = null;
        }
    }
    D8 = Grids.Az;
    if (D8 && D8.Move) {
        D8.IQ = Math.floor(IL.clientX / (IL.type ? _7AJ: 1));
        D8.IR = Math.floor(IL.clientY / (IL.type ? _7AJ: 1));
        var Cu = D8.Move.apply(this, [Click, IN]);
        if (_7P && !this.SelectingText && window.getSelection) {
            S = getSelection();
            if (S) S.collapse();
        }
        return Cu;
    }
    return false;
};
_7m.prototype.GridMouseMove = function (IL) {
    if (Grids.A5) this.Jt(IL);
    if (this.Ae) return true;
    if (_7M && !Grids.Active) AttachEvent(document, "mousewheel", _7AF);
    var G = Grids.Active;
    if (G && G != Grids.Active) {
        G.AS = null;
        G.JY = null;
    }
    Grids.Active = this;
    var Click = this.GetClick(IL),
    row = Click.Row,
    BJ = Click.Col,
    Cu = false;
    this.ACol = BJ;
    if (row == this.XHeader || BJ == "Pager") Cu = this.J9(Click, IL);
    else if (row && BJ == "Panel") {
        if (this.Hover >= 1) {
            if (row.Kind != "User") {
                var Hb = this.KA(row, "Panel"),
                KB = Get(row, "PanelClassOuterHover");
                this.KC(this.GetCell(row, "Panel"), Hb, KB ? this.Img.Ib + KB: Hb + "Hover");
            }
            Cu = true;
        }
    } else if (Grids.Jr) {
        try {
            Grids.Jr.className = Grids.Js;
            Grids.Jr = null;
        } catch(B) {}
    }
    var D8 = Grids.Az;
    if (this.Hover >= 2 && (!D8 || (D8.Action != "Select" && D8.Action != "Fill"))) {
        if (!this.EditMode) this.Refresh();
        this.D5();
    }
    if (this.Gantt && row) this.KD(row, BJ, Click.P, Click.Y);
    if (this.AT != row || this.AU != BJ) {
        this.HideHint();
        this.ShowHint(row, BJ);
    }
    this.AT = row;
    this.AU = BJ;
    var FP = IL.clientX,
    KE = IL.clientY;
    if (_7T && _7AJ != 1) {
        FP = Math.floor(FP / _7AJ);
        KE = Math.floor(KE / _7AJ);
    }
    if (this.JO != FP || this.JP != KE) {
        this.Jn = 0;
        this.JO = FP;
        this.JP = KE;
        this.HideTip();
    }
    if (this.ASec == -6) {
        var Bg = this.KF(IL);
        this.HoverPager(Bg);
    } else {
        if (this.HoverPager) this.HoverPager(null);
        if (!row && !Grids.Az) return true;
    };
    if (Grids.OnMouseMove) Cu = Grids.OnMouseMove(row, BJ, Click.P, Click.Y, IL);
    if (Grids.Az) Cu = this.Ju(Click, IL);
    else if (_7F) {
        Cu = false;
        CancelEvent(IL, 4);
    }
    if (!Cu && !this.EditMode && !_7F && (this.Dragging && this.Cols[BJ] && this.Cols[BJ].CanDrag || row == this.XHeader)) Cu = true;
    if (Cu) CancelEvent(IL);
    return ! Cu;
};
_7m.prototype.GridMouseDown = function (IL) {
    if (this.Ae) return true;
    var Click = this.GetClick(IL),
    row = Click.Row,
    BJ = Click.Col;
    if (this.EditMode && this.DragEdit && (row != this.FRow || BJ != this.FCol) && row && BJ) this.EndEdit(this.FRow, this.FCol, 1);
    if (!this.EditMode) {
        var D8 = new _7j();
        D8.Action = null;
        D8.P = Math.floor(IL.clientX / _7AJ);
        D8.Y = Math.floor(IL.clientY / _7AJ);
        D8.Row = row;
        D8.Col = BJ;
        D8.Grid = this;
        D8.J6 = Click.P;
        D8.J7 = Click.Y;
        D8.L = row;
        D8.KG = BJ;
        D8.IQ = D8.P;
        D8.IR = D8.Y;
        D8.N = this;
        Grids.Az = D8;
        this.KH(row, BJ);
    }
    var name = this.I9(Click, IL),
    Cu = false;
    var ED = this.JK(IL);
    if (!Cu && Grids.OnMouseDown) Cu = Grids.OnMouseDown(this, row, BJ, Click.P, Click.Y, IL);
    if (!Cu) Cu = this.JW(ED, "Down", name, row, BJ);
    if ((_7F || _7H) && (this.EditMode || !row || this.GetType(row, BJ) == "Html")) Cu = false;
    if (Cu) CancelEvent(IL);
    return ! Cu;
};
_7m.prototype.GridMouseUp = function (IL) {
    var Click = this.GetClick(IL),
    row = Click.Row,
    BJ = Click.Col;
    var D8 = Grids.Az,
    Cu = false;
    if (D8) {
        if (D8.II) Cu = D8.II.apply(this);
        if (Cu && _7R) this.Af = (new Date).getTime() + 100;
        Grids.Az = null;
        this.Jv();
    }
    var name = this.I9(Click, IL);
    var ED = this.JK(IL);
    if (!Cu && Grids.OnMouseUp) Cu = Grids.OnMouseUp(this, row, BJ, Click.P, Click.Y, IL);
    if (!Cu) Cu = this.JW(ED, "Up", name, row, BJ);
    if (!_7H) Cu = true;
    if ((_7F || _7H) && this.EditMode) Cu = false;
    if (Cu) CancelEvent(IL);
    return ! Cu;
};
_7m.prototype.GridMouseWheel = function (KI) {
    if (this.Ae || this.NoVScroll) return true;
    if (this.ASec == -6) {
        var F = this.h;
        if (F.offsetHeight < F.firstChild.offsetHeight) {
            F.scrollTop -= KI;
            return false;
        }
    }
    if (this.EditMode && this.Jy(this.FRow, this.FCol)) return true;
    this.q.scrollTop -= KI;
    this.Scrolled();
    if (this.ARow && this.ARow.Space != null) this.ARow.ACol = null;
    this.ARow = null;
    return false;
};
_7m.prototype.GridMouseOut = function (IL) {
    if (!this.e) return;
    var BL = EventXYToElement(IL, this.e);
    if (BL[0] >= 0 && BL[1] >= 0 && BL[0] < this.e.offsetWidth && BL[1] < this.e.offsetHeight) return;
    var row = this.ARow;
    this.ARow = null;
    if (row && row.Kind != "User") this.ColorRow(row);
    if (row && row.Space != null) row.ACol = null;
    this.AT = null;
    if (this.HoverPager) this.HoverPager(null);
    if (Grids.Active == this) {
        this.AS = null;
        this.JY = null;
        Grids.Active = null;
        this.HideHint();
        if (_7M) DetachEvent(document, "mousewheel", _7AF);
    }
};
_7m.prototype.KJ = function (KK, BV, BL, Bg, Bc, I, BQ) {
    if (this.CancelProgress) {
        this.HideMessage();
        this.CancelProgress = 0;
        return;
    }
    var i, J, Cols = this.DT(false, true),
    C = this.Cols,
    T = this;
    var cols = new Array(),
    CC = 0;
    if (this.ExportType & 8) {
        for (var i = 0; i < 3; i++) {
            for (var j = 0; j < this.ColNames[i].length; j++) {
                var BX = this.ColNames[i][j];
                if (BX == 'Panel') continue;
                if (C[BX].CanExport) cols[CC++] = BX;
            }
        }
    } else {
        for (i = 0; i < Cols.length; i++) if (C[Cols[i]].CanExport) cols[CC++] = Cols[i];
    };
    Cols = cols;
    if (!Bc) {
        BL = new Array();
        Bg = 0;
        BL[Bg++] = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">';
        BL[Bg++] = '<head><meta http-equiv=Content-Type content="text/html; charset=utf-8"></head><body>';
        BL[Bg++] = '<style>td {white-space:nowrap} br {mso-data-placement:same-cell;} .sd {mso-number-format:"Short Date";} .st {mso-number-format:"Short Time";} .gd {mso-number-format:"General Date";}</style>';
        BL[Bg++] = '<!--[if gte mso 9]><xml><o:OfficeDocumentSettings><o:Colors><o:Color><o:Index>37</o:Index><o:RGB>#F0F0E0</o:RGB></o:Color><o:Color><o:Index>38</o:Index><o:RGB>#F6F6F6</o:RGB></o:Color></o:Colors></o:OfficeDocumentSettings></xml><![endif]-->';
        if (this.ExportType & 16) BL[Bg++] = "<!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>" + this.id + "</x:Name><x:WorksheetOptions><x:NoSummaryRowsBelowDetail/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->";
        if (this.ExportPrefix) BL[Bg++] = this.ExportPrefix;
        BL[Bg++] = "<table border='1' bordercolor='silver' style='table-layout:fixed;border-collapse:collapse;border:1px solid black'>";
        for (i = 0; i < Cols.length; i++) BL[Bg++] = "<col width='" + C[Cols[i]].Width + "'>";
        var KL = this.XHeader;
        function KM() {
            BL[Bg++] = "\r\n<tr>";
            for (i = 0; i < Cols.length; i++) {
                var BZ = KL[Cols[i] + "ExportValue"];
                BL[Bg++] = "<td style='border-bottom:1px solid black;background:#F0F0E0;font-weight:bold;'>" + (BZ ? BZ: KL[Cols[i]]) + "</td>";
            }
            BL[Bg++] = "</tr>";
        };
        if (KL.CanExport) {
            if (KL.Rows > 1) {
                for (var i = 0; i < KL.Rows; i++) {
                    if (i == KL.Main) KM();
                    else {
                        BL[Bg++] = "\r\n<tr>";
                        for (var DR = 0; DR < 3; DR++) {
                            var S = this.ColNames[DR];
                            var CY = 0,
                            KN = 0,
                            KO = 0,
                            BQ = 0,
                            BX = 0,
                            D = (DR == 0 ? "Left": DR == 1 ? "Mid": "Right") + i,
                            rows = KL[D + BX + "Span"];
                            if (!rows) rows = 1;
                            for (; KN < S.length; KN++) {
                                var BY = C[S[KN]];
                                if (BY.Visible && BY.CanExport && S[KN] != "Panel") {
                                    CY += C[S[KN]].Width - this.AH;
                                    KO++;
                                }
                                BQ++;
                                if (rows == BQ) {
                                    if (CY > 0) BL[Bg++] = "<td colspan='" + KO + "' style='background:#F0F0E0;'>" + "<div" + this.KP(CY) + ">" + (KL[D + BX] ? KL[D + BX] : "&nbsp;") + "</div></td>";
                                    BQ = 0;
                                    CY = 0;
                                    KO = 0;
                                    BX++;
                                    rows = KL[D + BX + "Span"];
                                    if (!rows) rows = 1;
                                }
                            }
                        }
                        BL[Bg++] = "</tr>";
                    }
                }
            }
            if (! (KL.Rows > 1)) KM();
        }
    }
    var KQ = ["", "", "", "&lt;", "&lt;=", "&gt;", "&gt;="];
    var KR = this.ExportType & 1,
    exp = (this.ExportType & 18) == 0 ? 1 : 0,
    KS = this.ExportType & 16 ? 1 : 0;
    function AddRow(J, KT) {
        if (J.Kind == "User" || !Is(J, "CanExport")) return;
        if (!J.Visible && (!J.Filtered || !KR)) return;
        var BG = J.Kind == "Filter";
        if (BG && KR) return;
        if (KS && !J.Fixed) {
            var KU = "";
            if (! (T.ExportType & 2)) {
                for (var EU = J.parentNode; EU && !EU.Page; EU = EU.parentNode) if (!EU.Expanded) {
                    KU = "display:none;";
                    break;
                }
            }
            BL[Bg++] = "\r\n<tr style='mso-outline-level:" + J.Level + ";" + (!J.Expanded && !(T.ExportType & 2) ? "mso-outline-parent:collapsed;": "") + KU + "'>";
        } else BL[Bg++] = "\r\n<tr>";
        for (var i = 0; i < Cols.length; i++) {
            KV = KT;
            if (Em == Cols[i] && J.Level > 0 && !(T.ExportType & 32)) KV += "padding-left:" + J.Level * CF + "px;";
            var BX = Cols[i];
            if (J.Spanned) {
                BX = T.Dq(J, BX);
                span = Get(J, BX + "Span");
                if (span) {
                    span = 0;
                    var BJ = T.GetNextCol(BX, J);
                    while (BJ != Cols[i] && Cols[i]) {
                        i++;
                        span++;
                    }
                    i--;
                    span = " colspan=" + span;
                } else span = "";
            } else span = "";
            if (i == KW) KV += "border-right:1px solid black;";
            else if (i == I7) KV += "border-left:1px solid black;";
            if (C[BX].K != 1) KV += "background:#F6F6F6;";
            var Ca = T.GetType(J, BX);
            if (Ca == "Lines") KV += "white-space:normal;";
            if (!BG) DD = T.KX(J, BX, ">");
            else {
                var Cd = Get(J, BX + "Filter");
                if (!Cd) DD = "";
                else {
                    var DN = "<b style='color:red;'>";
                    var KY = "";
                    if (Cd == 2 || Cd == 8 || Cd == 10 || Cd == 12) KV += "text-decoration:line-through;";
                    if (Cd <= 6) DD = DN + " " + KQ[Cd] + " </b>";
                    else {
                        if (Cd != 7 && Cd != 8) DD = DN + "*</b>";
                        if (Cd != 9 && Cd != 10) KY = DN + "*</b>"
                    };
                    DD = ">" + DD + T.GetString(J, BX) + KY;
                };
            };
            var KZ = Get(J, BX + "ExportFormat");
            if (!KZ) KZ = T.Cols[BX].ExportFormat;
            if (KZ && (!(T.ExportType & 64) || Ca != "Date")) KV += " mso-number-format:" + KZ + ";";
            if (KV) KV = " style='" + KV + "'";
            if (Get(J, BX + "Visible") == 0) DD = ">";
            BL[Bg++] = "<td" + span + KV + DD + "</td>";
        }
        BL[Bg++] = "</tr>";
        return true;
    };
    var Em = this.MainCol,
    CF = 30,
    KW = -1,
    I7 = -1,
    KV = "",
    span = "",
    DD, type;
    for (i = 0; i < Cols.length; i++) {
        if (C[Cols[i]].K == 0) KW = i;
        if (C[Cols[i]].K == 2) {
            I7 = i;
            break;
        }
    }
    if (!Bc) {
        var CE = this.GetRows(this.XH);
        for (i = 0; i < CE.length; i++) AddRow(CE[i], "background:#F6F6F6;" + (i == CE.length - 1 ? "border-bottom:1px solid black;": ""));
        if (this.ExportRows) {
            var Bc = this.GetFirst(),
            Ka = 1000,
            BQ = 0;
            if (Bc) while (1) {
                if (!Bc.Visible && (!Bc.Filtered || !KR)) Ka = Bc.Level;
                else {
                    Ka = 1000;
                    BQ++;
                };
                Bc = this.GetNext(Bc, exp);
                while (Bc && Bc.Level > Ka) Bc = this.GetNext(Bc, exp);
                if (!Bc) break;
            }
        }
        Bc = this.GetFirst();
        Ka = 1000;
        I = 0;
    }
    while (Bc) {
        if (AddRow(Bc, "")) Ka = 1000;
        else Ka = Bc.Level;
        Bc = this.GetNext(Bc, exp);
        while (Bc && Bc.Level > Ka) Bc = this.GetNext(Bc, exp);
        if (Ka == 1000 && this.ExportRows && Bc) {
            I++;
            if (! (I % this.ExportRows)) {
                this.ShowMessage(this.GetText("ExportProgress").replace('%d', I).replace('%d', BQ).replace('%d', Math.floor(I / BQ * 100)).replace('%d', this.Z + ".CancelProgress=1;"));
                setTimeout(function () {
                    T.KJ(KK, BV, BL, Bg, Bc, I, BQ);
                },
                10);
                return;
            }
        }
    }
    var CE = this.GetRows(this.XF);
    for (i = 0; i < CE.length; i++) AddRow(CE[i], "background:#F6F6F6;" + (i ? "": "border-top:1px solid black;"));
    BL[Bg++] = "</table>";
    if (this.ExportPostfix) BL[Bg++] = this.ExportPostfix;
    BL[Bg++] = "</body></html>";
    this.HideMessage();
    this.CancelProgress = 0;
    if (KK && BV) BV.apply(KK, [BL.join("")]);
};
_7m.prototype.ActionExport = function () {
    var E = this.Data.Export;
    if (E.Type & 1) {
        if (!this.CK()) return false;
        var BR = "",
        K = ["LeftCols", "Cols", "RightCols"];
        for (var i = 0; i < 3; i++) {
            BR += "<" + K[i] + ">";
            var Cq = this.ColNames[i];
            for (var j = 0; j < Cq.length; j++) {
                var BJ = Cq[j],
                C = this.Cols[BJ];
                if (BJ != "Panel") BR += "<C Name='" + BJ + "' Width='" + C.Width + "' Visible='" + (C.Visible ? 1 : 0) + "'/>";
            }
            BR += "</" + K[i] + ">";
        }
        this.Kb("<Grid><IO" + (this.Data.Session != null ? " Session='" + this.Data.Session + "'": "") + "/>" + this.GetCfgRequest(E.Format) + BR + "</Grid>");
    } else {
        if (this.KJ) this.KJ(this, this.Kb);
        else _7w("SimpleExport");
    };
    return true;
};
_7m.prototype.Kb = function (BR) {
    if (Grids.OnExport && Grids.OnExport(this, BR)) return;
    var E = this.Data.Export;
    var D8 = document.createElement("form");
    D8.method = "POST";
    D8.action = E.Url;
    D8.acceptCharset = "UTF-8";
    var E4 = document.createElement("input");
    E4.type = "hidden";
    E4.name = E.Data;
    if (!E.Xml) BR = BR.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
    E4.value = BR;
    D8.appendChild(E4);
    for (var i in E.Param) {
        E4 = document.createElement("input");
        E4.type = "hidden";
        E4.name = i;
        E4.value = E.Param[i];
        D8.appendChild(E4);
    }
    if (E.Params) {
        var BL = E.Params.split('&');
        for (var i = 0; i < BL.length; i++) {
            var Bh = BL[i].split('=');
            if (Bh[0]) {
                E4 = document.createElement("input");
                E4.type = "hidden";
                E4.name = unescape(Bh[0]);
                E4.value = unescape(Bh[1]);
                D8.appendChild(E4);
            }
        }
    }
    document.body.appendChild(D8);
    D8.submit();
    document.body.removeChild(D8);
};
_7m.prototype.GetStringFilter = function (row, BJ) {
    var BZ = Get(row, BJ + "FilterValue");
    if (BZ == null) BZ = this.GetString(row, BJ, 3);
    else BZ += "";
    if (Grids.OnGetFilterValue) BZ = Grids.OnGetFilterValue(this, row, BJ, BZ);
    return BZ;
};
_7m.prototype.GetNumberFilter = function (row, BJ) {
    var BZ = Get(row, BJ + "FilterValue");
    if (BZ == null) BZ = Get(row, BJ);
    if (BZ === "" && this.EmptyNumber == null) BZ = 0;
    if (Grids.OnGetFilterValue) BZ = Grids.OnGetFilterValue(this, row, BJ, BZ);
    if (BZ && typeof(BZ) == "string" && this.GR(row, BJ)) {
        BZ = BZ.split(this.Lang.Format.ValueSeparator)[0];
        var Kc = BZ.indexOf(this.Lang.Format.RangeSeparator);
        if (Kc >= 0) BZ = BZ.slice(0, Kc) - 0;
        else BZ -= 0;
    }
    return BZ;
};
_7m.prototype.Kd = function () {
    var CE = "",
    C = this.Cols,
    R = this.GetFixedRows();
    var KQ = ["", "==", "!=", "<", "<=", ">", ">="];
    var Ke = ["", "==0", "!=0", "<0", "<=0", ">0", ">=0"];
    for (var i = 0; i < R.length; i++) {
        var J = R[i];
        if (J.Kind != "Filter") continue;
        for (var BY in this.Cols) {
            var Kf = J[BY + "Filter"] - 0;
            if (!Kf) continue;
            var Dv = C[BY].CanFilter;
            if (!Dv) continue;
            if (CE) CE += " &&";
            if (Dv == 2) CE += "(Get(Row,'" + BY + "CanFilter')==0||";
            var type = this.GetType(J, BY),
            HD = this.GR(J, BY);
            if (type == "Text" || type == "Lines" || type == "Link" || type == "Img" || type == "Html" || type == "Abs" || type == "Gantt" || type == "List" || Kf > 6) {
                var BR = this.GetString(J, BY, 3);
                var Kg = Get(J, BY + "FilterType");
                if (!Kg) Kg = 0;
                var Kh = this.Z + ".GetStringFilter(Row,'" + BY + "')";
                if (C[BY].CharCodes) {
                    Kh = this.Z + ".UseCharCodes(" + Kh + ",'" + BY + "')";
                    BR = this.UseCharCodes(BR, BY);
                }
                if (Kg & 8) {
                    var Ki = C[BY].WhiteChars;
                    BR = BR.replace(Ki, "");
                    Kh = Kh + ".replace(" + Ki + ",'')";
                }
                BR = (BR + "").replace(/\\/g, "\\\\").replace(/\'/g, "\\\'");
                if (this.I2 && this.I2(J, BY)) BR = BR.replace(/\n/, "\\n");
                if (HD && (Kf <= 2 || Kf >= 7)) {
                    if (Kf == 2 || Kf == 8 || Kf == 10 || Kf == 12) CE += "!";
                    CE += "(";
                    var BL = BR.split(this.Lang.Format.ValueSeparator);
                    for (var j = 0; j < BL.length; j++) {
                        if (j) CE += " || ";
                        if (BL[j] == String.fromCharCode(160)) CE += Kh + (Kf & 1 ? "==": "!=") + "''";
                        else if (Kf <= 2) CE += " CompareStrings(" + Kh + ",'" + BL[j] + "'," + Kg + ")==0";
                        else if (Kf <= 8) CE += "!CompareStrings(" + Kh + ".slice(0,String('" + BL[j] + "').length),'" + BL[j] + "'," + Kg + ")";
                        else if (Kf <= 10) CE += "!CompareStrings(" + Kh + ".slice(" + Kh + ".length-String('" + BL[j] + "').length," + Kh + ".length),'" + BL[j] + "'," + Kg + ")";
                        else if (Kf <= 12) CE += "SearchString(" + Kh + ",'" + BL[j] + "'," + Kg + ")>=0";
                    }
                    CE += ")";
                } else if (BR == String.fromCharCode(160)) CE += Kh + (Kf & 1 ? "==": "!=") + "''";
                else if (Kf <= 6) CE += " CompareStrings(" + Kh + ",'" + BR + "'," + Kg + ")" + Ke[Kf];
                else if (Kf <= 8) CE += (Kf == 8 ? " ": " !") + "CompareStrings(" + Kh + ".slice(0,String('" + BR + "').length),'" + BR + "'," + Kg + ")";
                else if (Kf <= 10) CE += (Kf == 10 ? " ": " !") + "CompareStrings(" + Kh + ".slice(" + Kh + ".length-String('" + BR + "').length," + Kh + ".length),'" + BR + "'," + Kg + ")";
                else if (Kf <= 12) CE += (Kf == 12 ? " !": " ") + "(SearchString(" + Kh + ",'" + BR + "'," + Kg + ")>=0)";
            } else {
                var Kh = this.Z + ".GetNumberFilter(Row,'" + BY + "')";
                var BR = HD ? Get(J, BY) : this.GetValue(J, BY);
                if (type == "Enum") {
                    var Ig = Get(J, BY + "FilterOff");
                    if (BR > Ig) BR--;
                }
                if (HD && BR && Kf <= 2 && typeof(BR) == "string") {
                    var BR = Get(J, BY);
                    if (!BR) BR = "";
                    if (Kf == 2) CE += "!";
                    CE += "(";
                    var BL = BR.split(this.Lang.Format.ValueSeparator);
                    for (var j = 0; j < BL.length; j++) {
                        var H = BL[j].split(this.Lang.Format.RangeSeparator);
                        if (j) CE += " || ";
                        if (H[1]) CE += Kh + ">=" + H[0] + " && " + Kh + "<=" + H[1];
                        else CE += Kh + "==" + H[0];
                    }
                    CE += ")";
                } else {
                    if (!BR) {
                        if (BR === "") BR = "''";
                        if (this.EmptyNumber && (Kf == 1 || Kf == 2)) BR = "=" + BR;
                    }
                    CE += " " + Kh + KQ[Kf] + BR;
                }
            };
            if (Dv == 2) CE += ")";
        }
    }
    this.GC = CE ? new Function("Row", "return " + CE) : null;
};
_7m.prototype.DoFilter = function (row, BJ) {
    if (!this.Filtering) return;
    if (row) {
        if (!row[BJ + "Filter"]) {
            var Bj = Get(row, BJ + "DefaultFilter");
            if (Bj == 0) return;
            if (Bj > 0) row[BJ + "Filter"] = Bj;
            else {
                var type = this.GetType(row, BJ);
                row[BJ + "Filter"] = type == "Text" || type == "Lines" || type == "Link" || type == "Img" || type == "Html" || type == "Abs" || type == "Gantt" || type == "List" ? 11 : 1;
            };
            this.RefreshCell(row, BJ);
        }
    }
    this.SaveCfg();
    if (Grids.OnCanFilter) if (!Grids.OnCanFilter(this, false)) return;
    this.Kd();
    this.Kj();
    var T = this;
    function filter() {
        T.Kk();
        T.Dz();
        T.HideMessage();
        if (Grids.OnFilterFinish) Grids.OnFilterFinish(T);
    };
    if (this.RowCount < this.SynchroCount) filter();
    else {
        this.ShowMessage(this.GetText("DoFilter"));
        setTimeout(filter, 10);
    }
};
_7m.prototype.Kj = function (Dj) {
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) {
        var row = CE[i];
        if (row.Kind != "Filter") continue;
        var Df = false;
        for (var BY in this.Cols) {
            var Kl = row[BY + "Filter"] ? 1 : 0;
            row[BY + "Changed"] = Kl;
            if (Kl) Df = true;
        }
        row.Changed = Df;
        if (!Dj) this.ColorRow(row);
    }
};
_7m.prototype.Km = function (Dj) {
    for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) this.Kn(Bg, Dj);
};
_7m.prototype.Kn = function (row, Dj) {
    for (var J = row.firstChild; J; J = J.nextSibling) {
        if (J.Filtered) continue;
        this.Kn(J, Dj);
        if ((Get(J, "CanFilter") & 2 || J.Dx) && !this.HasChildren(J)) {
            if (!J.Deleted || this.ShowDeleted) {
                if (this.Paging || Dj) J.Visible = 0;
                else this.HideRow(J);
            }
            J.Filtered = 1;
        }
    }
};
_7m.prototype.Kk = function (Dj) {
    if (!this.Filtering || !this.Filtered) return;
    var Ko = this.OnePage & 2 && !this.AllPages && this.Paging ? this.GetFPage() : null;
    if (this.Paging == 3 && !Ko) {
        if (!this.AllPages) this.AP = 0;
        this.ReloadBody(null, 0, "Filter");
        return;
    }
    if (this.Paging > 0 && !Dj && !Ko) this.Kp();
    var Count = 0,
    Ka = 1000,
    Kq = 0,
    T = this,
    Dh = this.Rendering;
    if ((this.Rendering || this.Loading) && !Dj) Dj = 1;
    if (!Dj) this.Rendering = true;
    var Ee = this.Cols[this.MainCol],
    GB = this.GC,
    GD = this.GE,
    GF = this.GG;
    function Kr(J) {
        if (J.Kind == "Data" && (!J.Deleted || T.ShowDeleted)) {
            var BG = Get(J, "CanFilter") & 1 && (GB && !GB(J) || GD && !GD(J) || GF && GF(J));
            if (BG && T.StandardFilter == 2 && T.HasChildren(J)) BG = 0;
            if (Grids.OnRowFilter) BG = !Grids.OnRowFilter(T, J, !BG);
            if (T.Paging || Dj) J.Visible = !BG;
            else {
                if (BG) {
                    if (!J.Filtered) T.HideRow(J, false, Dj);
                } else if (J.Filtered) {
                    if (T.ShowRow(J, 0, 0, 1) == 0) Kq = 1;
                    else if (Ee) T.Ef(J, 1, Ee);
                }
            };
            if (Kq && !J.nextSibling) {
                Kq = 0;
                for (var Ev = J.parentNode.firstChild; Ev; Ev = Ev.nextSibling) T.Ef(Ev);
            }
            J.Filtered = BG;
            return BG;
        }
        return true;
    };
    function Ks(Bg) {
        for (var J = Bg.firstChild; J; J = J.nextSibling) Ks(J);
        if (Bg.Calculated) T.Bw(Bg, Calc, "", !Dj, 0, 1);
        if (Kr(Bg)) Count++;
    };
    if (this.VarHeight && !Dj) {
        for (var i = 0; i < 3; i++) {
            var H = this.Kt(i);
            if (H) H.parentNode.style.display = "";
        }
    }
    if (Ko) {
        var Calc = this.Bv();
        for (var J = Ko.firstChild; J; J = J.nextSibling) Ks(J);
        if (this.FilterEmpty && (GB || GD || GF || Grids.OnRowFilter)) this.Kn(Ko, Dj);
        if (this.ChildPaging) this.EY(Ko, !Dj);
    } else {
        if (this.StandardFilter) {
            var Calc = this.Bv();
            for (var J = this.GetFirst(); J; J = this.GetNextSibling(J)) Ks(J);
        } else for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
            if (Ka < J.Level) continue;
            if (Kr(J)) {
                Ka = J.Level;
                Count++;
            } else Ka = 1000;
        }
        if (this.FilterEmpty && (GB || GD || GF || Grids.OnRowFilter)) this.Km(Dj);
        if (this.ChildPaging) this.EY(this.XB, !Dj);
    };
    if (!Dj) this.Rendering = Dh;
    if (Dj) return Count;
    if (Ko) {
        Ko.State = 3;
        this.FF(Ko);
    }
    if (this.Paging > 0 && !Ko) {
        this.CreatePages();
        this.Ku();
    } else {
        this.SetScrollBars();
        this.HideMessage();
    };
    this.FE();
    if (this.Calculating && this.Calculated) {
        if (_7H || this.RowCount < this.SynchroCount) {
            this.Calculate(true, true);
            this.HideMessage();
        } else {
            this.ShowMessage(this.GetText("Calculate"));
            var T = this;
            setTimeout(function () {
                T.Calculate(true, true);
                T.HideMessage();
            },
            10);
        }
    } else this.Calculate(1, 0, 0, 1);
    return Count;
};
_7m.prototype.ShowFilter = function (row, BJ) {
    if (this.Ae || !row) return;
    if (this.Focus(row, BJ) == null) return;
    var D8 = this.Dialog;
    if (D8 && D8.Row == row && D8.Col == BJ) {
        this.CloseDialog();
        return true;
    }
    var D = new Array(),
    E4 = new Array();
    var Kv = row[BJ + "MenuItems"];
    if (Kv) {
        Kv = (Kv + "").split(",");
        for (var B3 = 0; B3 < Kv.length; B3++) {
            var i = Kv[B3];
            if (i >= 0 && i <= 12) {
                D[i] = this.Lang["MenuFilter"]["F" + i];
                E4[i] = this.HW(14, this.Img.Filter * i, this.Img.Filter);
            }
        }
    } else {
        var Ca = this.GetType(row, BJ);
        var a = 7;
        if (Ca == "Bool") a = 2;
        else if (Ca == "Text" || Ca == "Lines" || Ca == "Link" || Ca == "Img" || Ca == "Html" || Ca == "Abs" || Ca == "Gantt" || Ca == "List") a = 13;
        for (var i = 0; i < a; i++) {
            D[i] = this.Lang["MenuFilter"]["F" + i];
            E4[i] = this.HW(14, this.Img.Filter * i, this.Img.Filter);
        }
    };
    this.ShowMenu(row, BJ, D, E4, new Function("i", this.Z + ".SetFilterOp(" + this.Z + ".FRow,\"" + BJ + "\",i);"), 1, null, null, null, null, null, null, null, row[BJ + "Filter"] ? row[BJ + "Filter"] : 0);
    var D4 = document.createElement("div");
    D4.style.position = "absolute";
    D4.className = this.Img.Style + "MenuIcon";
    D4.style.zIndex = this.ZIndex;
    var IU = this.GetCell(row, BJ);
    var BL = ElemToParent(IU, _7T || _7H && !_7P ? document.documentElement: document.body);
    if (BL[1] < this.Dialog.Tag.offsetTop) {
        D4.style.left = BL[0] + "px";
        D4.style.top = BL[1] + "px";
        var Ce = row[BJ + "Filter"];
        if (!Ce) Ce = 0;
        D4.innerHTML = this.HW(14, this.Img.Filter * Ce, this.Img.Filter);
        D4.onclick = new Function("ev", "if(!ev) ev = event; CancelEvent(ev);" + this.Z + ".CloseDialog();");
        document.body.insertBefore(D4, this.Dialog.Tag.nextSibling);
        this.Dialog.Caption = D4;
        this.Hk(D4, this["ShadowFilterIcon"]);
    }
    this.Hk(this.Dialog.Tag, this["ShadowFilter"]);
    return true;
};
_7m.prototype.Ij = function (row, BJ) {
    var rel = Get(row, BJ + "Related");
    if (!rel) rel = this.Cols[BJ]["Related"];
    if (rel) {
        rel = rel.split(",");
        for (var i = 0; i < rel.length; i++) {
            if (!row[rel[i] + "Filter"]) {
                row[rel[i] + "Filter"] = 1;
                this.RefreshCell(row, rel[i]);
            }
        }
    }
    var F9 = Get(row, BJ + "Refresh");
    if (!F9) F9 = this.Cols[BJ]["Refresh"];
    if (F9) {
        F9 = F9.split(",");
        for (var i = 0; i < F9.length; i++) {
            if (row[F9[i] + "Filter"]) {
                row[F9[i] + "Filter"] = 0;
                this.RefreshCell(row, F9[i]);
            }
        }
    }
};
_7m.prototype.SetFilterOp = function (row, BJ, Cd) {
    if (!row) return;
    this.CloseDialog();
    if (row[BJ + "Filter"] == Cd || !row[BJ + "Filter"] && !Cd || this.Paging == 3 && !(this.OnePage & 2) && !this.CK()) return;
    row[BJ + "Filter"] = Cd;
    this.Ij(row, BJ);
    if (this.EditMode) {
        var Kw = this.FRow;
        Kx = this.FCol;
        if (this.EndEdit(Kw, Kx, true) && row == Kw && BJ == Kx) return;
    }
    this.RefreshCell(row, BJ);
    this.DoFilter();
};
_7m.prototype.ChangeFilter = function (cols, Ky, Kz, K0) {
    cols = _7AU(cols);
    Ky = _7AU(Ky);
    Kz = _7AU(Kz);
    var CE = this.Cc()[0];
    if (!CE) return;
    var V = new Object();
    CE.Changed = cols.length ? 1 : 0;
    for (var i = 0; i < cols.length; i++) {
        var BJ = cols[i];
        V[BJ] = 1;
        CE[BJ] = Ky[i];
        CE[BJ + "Filter"] = Kz[i] - 0;
        CE[BJ + "Changed"] = 1;
    }
    for (var BJ in this.Cols) {
        if (!V[BJ]) {
            CE[BJ] = null;
            CE[BJ + "Filter"] = 0;
            CE[BJ + "Changed"] = 0;
        }
    }
    this.RefreshRow(CE);
    if (!K0) this.DoFilter();
};
_7m.prototype.ActionFilterOff = function () {
    return this.K1(0);
};
_7m.prototype.ActionFilterOn = function () {
    return this.K1(1);
};
_7m.prototype.K1 = function (BR) {
    if (!this.Filtering || this.Filtered == BR) return false;
    if (Grids.OnCanFilter && !Grids.OnCanFilter(this, false)) return false;
    var Ko = this.OnePage & 2 && !this.AllPages && this.Paging ? this.GetFPage() : null;
    if (this.Paging == 3 && !Ko) {
        this.Filtered = BR;
        if (!this.AllPages) this.AP = 0;
        var CE = this.Cc();
        for (var i = 0; i < CE.length; i++) this.K2(CE[i]);
        this.SaveCfg();
        this.ReloadBody(null, 0, "Filter");
        return true;
    }
    if (BR) this.Filtered = true;
    else {
        var GB = this.GC;
        this.GC = null;
    };
    var T = this;
    function filter() {
        T.Kk();
        T.Dz();
        if (!BR) {
            T.Filtered = false;
            T.GC = GB;
        }
        T.SaveCfg();
        if (Grids.OnFilterFinish) Grids.OnFilterFinish(T);
        var CE = T.Cc();
        for (var i = 0; i < CE.length; i++) T.K2(CE[i]);
    };
    if (this.RowCount < this.SynchroCount) filter();
    else {
        this.ShowMessage(this.GetText("DoFilter"));
        setTimeout(filter, 10);
    };
    return true;
};
_7m.prototype.FilterTextRange = function (BJ, BR, Dj) {
    var Bp = this.Lang.Format.ValueSeparator;
    if (BR) {
        BR = (BR + "").split(Bp);
        for (var i = 0; i < BR.length; i++) BR[i] = BR[i].replace(/[\^\$\.\*\+\?\=\!\:\|\\\/\(\)\[\]\{\}\/]/g, "\\$&");
        BR = "(^|\\" + Bp + ")(" + BR.join('|') + ")($|\\" + Bp + ")";
        var CE = this.Z + ".GetStringFilter(Row,'" + BJ + "').search(/" + BR + "/)<0";
    } else CE = "1";
    this.GG = new Function("Row", "return " + CE);
    this.DoFilter(Dj);
};
function DateToString(HB, DG, HD, K3, K4) {
    if (HD && HB && typeof(HB) == "string") {
        var BL = HB.split(HD.charAt(0));
        for (var i = 0; i < BL.length; i++) {
            var H = BL[i].split(HD.charAt(1));
            for (var j = 0; j < H.length; j++) H[j] = DateToString(H[j] - 0, DG);
            BL[i] = H.join(K4 ? K4: HD.charAt(1));
        }
        return BL.join(K3 ? K3: HD.charAt(0));
    }
    if (!isFinite(HB)) return _7o.NaD;
    var K5 = "",
    HP = _7o.GMT - 0;
    if (typeof(HB) == "string" || typeof(HB) == "number") HB = new Date(HB);
    var GQ = 0;
    if (DG == null || DG == "") return GQ ? K9(HB) : _7AV(HB);
    if (DG.length == 1) {
        switch (DG) {
        case 'r':
        case 'R':
            return HP ? HB.toUTCString() : HB.toString();
        case 'y':
        case 'Y':
            if (HP) return _7o.LongMonthNames2[HB.getUTCMonth()] + " " + HB.getUTCFullYear();
            return _7o.LongMonthNames2[HB.getMonth()] + " " + HB.getFullYear();
        case 'U':
            return HB.getUTCDate() + ". " + _7o.LongMonthNames[HB.getUTCMonth()] + " " + HB.getUTCFullYear() + " " + (HB.getUTCHours() < 10 ? "0": "") + HB.getUTCHours() + ":" + DateToString(HB, "mm:ss");
        default:
            if (_7o[DG]) DG = _7o[DG];
        }
    }
    for (var i = 0; i < DG.length; i++) {
        switch (DG.charAt(i)) {
        case '%':
            break;
        case '/':
            K5 += _7o.DateSeparator;
            break;
        case ':':
            K5 += _7o.TimeSeparator;
            break;
        case 'd':
            var Ck = GQ ? K8: (HP ? HB.getUTCDate() : HB.getDate());
            if (DG.charAt(i + 1) != 'd') {
                K5 += Ck;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'd') {
                if (Ck < 10) K5 += '0' + Ck;
                else K5 += Ck;
                break;
            }
            var Hl = HP ? HB.getUTCDay() : HB.getDay();
            i++;
            if (DG.charAt(i + 1) != 'd') {
                K5 += _7o.ShortDayNames[Hl];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'd') {
                K5 += _7o.LongDayNames[Hl];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'd') {
                K5 += _7o.Day1CharNames[Hl];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'd') {
                K5 += _7o.DayNumbers[Ck - 1];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'd') {
                var LA = new Date(HB.getFullYear(), 0, 1);
                K5 += Math.ceil((((HB - LA) / 86400000) + LA.getDay()) / 7);
            }
            break;
        case 'M':
            var C0 = GQ ? K7: (HP ? HB.getUTCMonth() : HB.getMonth());
            if (DG.charAt(i + 1) != 'M') {
                K5 += C0 + 1;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'M') {
                if (C0 + 1 < 10) K5 += '0' + (C0 + 1);
                else K5 += C0 + 1;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'M') {
                K5 += _7o.ShortMonthNames[C0];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'M') {
                K5 += _7o.LongMonthNames[C0];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'M') {
                K5 += _7o.Quarters[Math.floor(C0 / 3)];
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'M') {
                K5 += _7o.Halves[Math.floor(C0 / 6)];
                break;
            }
            break;
        case 'y':
            var y = GQ ? K6: (HP ? HB.getUTCFullYear() : HB.getFullYear());
            var LB = y % 100;
            if (DG.charAt(i + 1) != 'y') {
                K5 += LB;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'y') {
                if (LB < 10) K5 += '0' + LB;
                else K5 += LB;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'y') break;
            i++;
            if (DG.charAt(i + 1) != 'y') {
                K5 += y;
                break;
            }
            break;
        case 'h':
            var CZ = (HP ? HB.getUTCHours() : HB.getHours()) % 12;
            if (!CZ) CZ = 12;
            if (DG.charAt(i + 1) != 'h') {
                K5 += CZ;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'h') {
                if (CZ < 10) K5 += '0' + CZ;
                else K5 += CZ;
                break;
            }
            break;
        case 'H':
            var CZ = (HP ? HB.getUTCHours() : HB.getHours());
            if (DG.charAt(i + 1) != 'H') {
                K5 += CZ;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'H') {
                if (CZ < 10) K5 += '0' + CZ;
                else K5 += CZ;
                break;
            }
            break;
        case 'm':
            var C0 = HP ? HB.getUTCMinutes() : HB.getMinutes();
            if (DG.charAt(i + 1) != 'm') {
                K5 += C0;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'm') {
                if (C0 < 10) K5 += '0' + C0;
                else K5 += C0;
                break;
            }
            break;
        case 's':
            var Bh = HB.getSeconds();
            if (DG.charAt(i + 1) != 's') {
                K5 += Bh;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 's') {
                if (Bh < 10) K5 += '0' + Bh;
                else K5 += Bh;
                break;
            }
            break;
        case 'f':
            var IY = HB.getMilliseconds ? HB.getMilliseconds() : HB.LC();
            var LD = ".0";
            while (DG.charAt(i + 1) == 'f') {
                i++;
                LD += "0";
            }
            K5 += NumberToString(IY / 1000, LD).substring(1);
            break;
        case 't':
            var CZ = HP ? HB.getUTCHours() : HB.getHours();
            if (DG.charAt(i + 1) != 't') {
                K5 += CZ < 12 ? _7o.AMDesignator.charAt(0) : _7o.PMDesignator.charAt(0);
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 't') {
                K5 += CZ < 12 ? _7o.AMDesignator: _7o.PMDesignator;
                break;
            }
            break;
        case 'z':
            var LE = HB.getTimezoneOffset();
            if (LE < 0) {
                K5 += "-";
                LE = -LE;
            } else K5 += "+";
            var LF = Math.round(LE / 60);
            if (DG.charAt(i + 1) != 'z') {
                K5 += LF;
                break;
            }
            i++;
            if (DG.charAt(i + 1) != 'z') {
                K5 += (LF > -10 && LE < 10 ? '0': '') + LF;
                break;
            }
            i++;
            K5 += Math.floor(LE / 60) + ":" + (LE % 60 < 10 ? "0" + LE % 60 : LE % 60);
            break;
        case '\\':
            i++;
            K5 += DG.charAt(i);
            break;
        case '"':
        case "'":
            var I = DG.indexOf(DG.charAt(i), i + 1);
            if (I == -1) break;
            K5 += DG.substring(i + 1, I);
            i = I;
            break;
        default:
            K5 += DG.charAt(i);
        }
    }
    return K5;
};
function _7AV(HB, LG) {
    if (!HB) return "#";
    if (typeof(HB) == "string" || typeof(HB) == "number") HB = new Date(HB);
    var P = new Array(6);
    if (_7o.GMT - 0) {
        P[0] = HB.getUTCDate();
        P[1] = HB.getUTCMonth() + 1;
        P[2] = HB.getUTCFullYear();
        P[3] = HB.getUTCHours();
        P[4] = HB.getUTCMinutes();
        P[5] = HB.getSeconds();
    } else {
        P[0] = HB.getDate();
        P[1] = HB.getMonth() + 1;
        P[2] = HB.getFullYear();
        P[3] = HB.getHours();
        P[4] = HB.getMinutes();
        P[5] = HB.getSeconds();
    };
    var D8, T = P[3] || P[4] || P[5];
    if (!HB.toDateString) {
        D8 = new Date();
        if (_7o.GMT - 0) D8 = D8.getUTCDate() != P[0] || D8.getUTCMonth() + 1 != P[1] || D8.getUTCFullYear() != P[2];
        else D8 = D8.getDate() != P[0] || D8.getMonth() + 1 != P[1] || D8.getFullYear() != P[2];
    } else D8 = (new Date()).toDateString() != HB.toDateString() || !T;
    for (var k = 0; k < 6; k++) if (P[k] < 10) P[k] = "0" + P[k];
    return (D8 ? P[1] + "/" + P[0] + "/" + P[2] : "") + (T ? " " + P[3] + ":" + P[4] + (P[5] != "00" ? ":" + P[5] : "") : "");
};
function StringToDate(DD, DG) {
    if (!DD) return new Date(0);
    if (!DG) return _7AX(DD);
    if (DG.length == 1) {
        switch (DG) {
        default:
            DG = _7o[DG];
        }
    }
    var HP = _7o.GMT - 0;
    var Ca = 0;
    var I = 0,
    BR = "",
    LH = 0,
    LI = 0;
    var HB = new Date();
    if (HP) {
        HB.setUTCHours(0, 0, 0, 0);
        var Ck = HB.getUTCDate(),
        C0 = HB.getUTCMonth() + 1,
        y = HB.getUTCFullYear();
    } else {
        HB.setHours(0, 0, 0, 0);
        var Ck = HB.getDate(),
        C0 = HB.getMonth() + 1,
        y = HB.getFullYear();
    };
    if (DG.indexOf('t') >= 0 && DG.indexOf('h') >= 0) {
        LI = DD.search(new RegExp(_7o.PMDesignator.charAt(0), 'i')) > 0;
        LH = DD.search(new RegExp(_7o.AMDesignator.charAt(0), 'i')) > 0;
    }
    if (DD.indexOf(_7o.DateSeparator) < 0 && DD.indexOf(_7o.TimeSeparator) >= 0 && DG.indexOf(_7o.TimeSeparator) >= 0) {
        DG = _7AC(DG);
    }
    if (DD - 0) {
        var JE = 0;
        var BN = DD.length;
        if (DG.indexOf('M') < 0) {
            var LJ = HP ? "setUTCHours": "setHours";
            if (BN == 6) {
                HB[LJ](DD.slice(0, 2) - 0, DD.slice(2, 4) - 0, DD.slice(4, 6) - 0);
                return HB;
            }
            if (BN == 5) {
                HB[LJ](DD.slice(0, 1) - 0, DD.slice(1, 3) - 0, DD.slice(3, 5) - 0);
                return HB;
            }
            if (BN == 4) {
                if (DG.search(/h/) >= 0) HB[LJ](DD.slice(0, 2) - 0, DD.slice(2, 4) - 0);
                else HB.setMinutes(DD.slice(0, 2) - 0, DD.slice(2, 4) - 0);
                return HB;
            }
            if (BN == 3) {
                if (DG.search(/h/) >= 0) HB[LJ](DD.slice(0, 1) - 0, DD.slice(1, 3) - 0);
                else HB.setMinutes(DD.slice(0, 1) - 0, DD.slice(1, 3) - 0);
                return HB;
            }
        } else {
            if (BN == 3) {
                if (DG.search(/M.*d/) >= 0) {
                    C0 = DD.slice(0, 1) - 0;
                    Ck = DD.slice(1, 3) - 0;
                    JE = 1;
                } else if (DG.search(/d.*M/) >= 0) {
                    Ck = DD.slice(0, 1) - 0;
                    C0 = DD.slice(1, 3) - 0;
                    JE = 1;
                }
            } else if (BN == 4) {
                if (DG.search(/M.*d/) >= 0) {
                    C0 = DD.slice(0, 2) - 0;
                    Ck = DD.slice(2, 4) - 0;
                    JE = 1;
                } else if (DG.search(/d.*M/) >= 0) {
                    Ck = DD.slice(0, 2) - 0;
                    C0 = DD.slice(2, 4) - 0;
                    JE = 1;
                } else if (DG.search(/y.*M/) >= 0) {
                    y = DD.slice(0, 2) - 0;
                    C0 = DD.slice(2, 4) - 0;
                    y += y < 70 ? 2000 : 1900;
                    JE = 1;
                } else if (DG.search(/M.*y/) >= 0) {
                    C0 = DD.slice(0, 2) - 0;
                    y = DD.slice(2, 4) - 0;
                    y += y < 70 ? 2000 : 1900;
                    JE = 1;
                }
            } else if (BN == 6) {
                if (DG.search(/M.*d.*y/) >= 0) {
                    C0 = DD.slice(0, 2) - 0;
                    Ck = DD.slice(2, 4) - 0;
                    y = DD.slice(4, 6) - 0;
                    y += y < 70 ? 2000 : 1900;
                    JE = 1;
                } else if (DG.search(/d.*M.*y/) >= 0) {
                    Ck = DD.slice(0, 2) - 0;
                    C0 = DD.slice(2, 4) - 0;
                    y = DD.slice(4, 6) - 0;
                    y += y < 70 ? 2000 : 1900;
                    JE = 1;
                } else if (DG.search(/y.*M.*d/) >= 0) {
                    y = DD.slice(0, 2) - 0;
                    C0 = DD.slice(2, 4) - 0;
                    Ck = DD.slice(4, 6) - 0;
                    y += y < 70 ? 2000 : 1900;
                    JE = 1;
                } else if (DG.search(/y.*d.*M/) >= 0) {
                    y = DD.slice(0, 2) - 0;
                    Ck = DD.slice(2, 4) - 0;
                    C0 = DD.slice(4, 6) - 0;
                    y += y < 70 ? 2000 : 1900;
                    JE = 1;
                } else if (DG.search(/yyyy.*M/) >= 0) {
                    y = DD.slice(0, 4) - 0;
                    C0 = DD.slice(4, 6) - 0;
                    JE = 1;
                } else if (DG.search(/M.*yyyy/) >= 0) {
                    C0 = DD.slice(0, 2) - 0;
                    y = DD.slice(2, 6) - 0;
                    JE = 1;
                }
            } else if (BN == 8) {
                if (DG.search(/M.*d.*y/) >= 0) {
                    C0 = DD.slice(0, 2) - 0;
                    Ck = DD.slice(2, 4) - 0;
                    y = DD.slice(4, 8) - 0;
                    JE = 1;
                } else if (DG.search(/d.*M.*y/) >= 0) {
                    Ck = DD.slice(0, 2) - 0;
                    C0 = DD.slice(2, 4) - 0;
                    y = DD.slice(4, 8) - 0;
                    JE = 1;
                } else if (DG.search(/y.*M.*d/) >= 0) {
                    y = DD.slice(0, 4) - 0;
                    C0 = DD.slice(4, 6) - 0;
                    Ck = DD.slice(6, 8) - 0;
                    JE = 1;
                } else if (DG.search(/y.*d.*M/) >= 0) {
                    y = DD.slice(0, 4) - 0;
                    Ck = DD.slice(4, 6) - 0;
                    C0 = DD.slice(6, 8) - 0;
                    JE = 1;
                }
            }
        };
        if (JE) {
            if (HP) HB.setUTCFullYear(y, C0 - 1, Ck);
            else HB.setFullYear(y, C0 - 1, Ck);
            return HB;
        }
    }
    for (var i = 0; i <= DD.length; i++) {
        var BY = i == DD.length ? -1 : DD.charAt(i);
        if (BY >= '0' && BY <= '9') {
            if (Ca == 1) BR += BY;
            else {
                Ca = 1;
                BR = BY;
            }
        } else {
            if (Ca == 1) {
                var LK = true;
                while (I < DG.length && LK) {
                    var Bh = DG.charAt(I++);
                    LK = false;
                    switch (Bh) {
                    case 'd':
                        Ck = Number(BR);
                        break;
                    case 'M':
                        C0 = Number(BR);
                        break;
                    case 'y':
                        y = Number(BR);
                        if (y < 100) {
                            if (y < 70) y += 2000;
                            else y += 1900;
                        }
                        break;
                    case 'H':
                    case 'h':
                        BR -= 0;
                        if (LH) {
                            if (BR >= 12) BR = 0;
                        } else if (LI) {
                            if (BR < 12) BR += 12;
                            else if (BR > 12) BR = 12;
                        }
                        if (HP) HB.setUTCHours(BR);
                        else HB.setHours(BR);
                        break;
                    case 'm':
                        if (HP) HB.setUTCMinutes(BR);
                        else HB.setMinutes(BR);
                        break;
                    case 's':
                        HB.setSeconds(BR);
                        break;
                    case 'f':
                        HB.setMilliseconds(BR);
                        break;
                    default:
                        LK = true;
                    }
                }
                while (DG.charAt(I++) == Bh);
                Ca = 0;
            }
        }
    }
    if (HP) HB.setUTCFullYear(y, C0 - 1, Ck);
    else HB.setFullYear(y, C0 - 1, Ck);
    return HB;
};
function _7AX(DD) {
    var HP = _7o.GMT - 0;
    var HB = new Date();
    if (HP) HB.setUTCHours(0, 0, 0, 0);
    else HB.setHours(0, 0, 0, 0);
    var BZ = 0,
    Ck, C0;
    var Ca = 0;
    for (var i = 0; i <= DD.length; i++) {
        var BY = i == DD.length ? -1 : DD.charAt(i);
        if (BY == '/') {
            if (Ca == 0) {
                C0 = BZ;
                Ca = 1;
            } else if (Ca == 1) {
                Ck = BZ;
                Ca = 3;
            } else return null;
        } else if (BY == '.') {
            if (Ca == 0) {
                Ck = BZ;
                Ca = 2;
            } else if (Ca == 2) {
                C0 = BZ;
                Ca = 3;
            } else if (Ca == 6) {
                HB.setSeconds(BZ);
                Ca = 7;
            } else return null;
        } else if (BY == '-') {
            if (Ca == 0) {
                if (BZ < 100) BZ += BZ < 70 ? 2000 : 1900;
                if (HP) HB.setUTCFullYear(BZ);
                else HB.setFullYear(BZ);
                Ca = 2;
            } else if (Ca == 2) {
                C0 = BZ;
                Ca = 1;
            } else return null;
        } else if (BY == ':') {
            if (Ca == 4 || Ca == 0) {
                if (HP) HB.setUTCHours(BZ);
                else HB.setHours(BZ);
                Ca = 5;
            } else if (Ca == 5) {
                if (HP) HB.setUTCMinutes(BZ);
                else HB.setMinutes(BZ);
                Ca = 6;
            } else if (Ca == 6) {
                HB.setSeconds(BZ);
                Ca = 7;
            } else return null;
        } else if (BY == ' ' || BY == ',' && Ca != 6 || BY == ';' || BY == 'T' || BY == -1) {
            if (Ca == 0 || BZ == 0) continue;
            else if (Ca == 1) {
                Ck = BZ;
                Ca = 4;
            } else if (Ca == 2) {
                C0 = BZ;
                Ca = 4;
            } else if (Ca == 3) {
                if (BZ < 100) BZ += BZ < 70 ? 2000 : 1900;
                if (HP) HB.setUTCFullYear(BZ);
                else HB.setFullYear(BZ);
                Ca = 4;
            } else if (Ca == 4) return null;
            else if (Ca == 5) {
                if (HP) HB.setUTCMinutes(BZ);
                else HB.setMinutes(BZ);
                Ca = 6;
            } else if (Ca == 6) {
                HB.setSeconds(BZ);
                Ca = 7;
            } else if (Ca == 7) {
                HB.setMilliseconds(BZ);
                Ca = 8;
            } else if (Ca == 8) continue;
        }
        if (BY >= '0' && BY <= '9') {
            BZ *= 10;
            BZ += BY - 0;
        } else BZ = 0;
    }
    if (C0 != null) {
        if (Ck != null) {
            HB.setDate(10);
        }
        if (HP) HB.setUTCMonth(C0 - 1);
        else HB.setMonth(C0 - 1);
    }
    if (Ck != null) {
        if (HP) HB.setUTCDate(Ck);
        else HB.setDate(Ck);
    }
    return HB;
};
function _7AC(DG) {
    var Cs = DG,
    Hg = "",
    B1 = ' :hHmstz';
    if (Cs.length == 1) Cs = _7o[Cs];
    for (var i = 0; i < Cs.length; i++) {
        var BY = Cs.charAt(i);
        if (B1.indexOf(BY) >= 0) Hg += BY;
        else if (BY == '\\') i++;
        else if (BY == '"' || BY == "'") while (i < Cs.length && i.charAt(i) != BY) i++;
    }
    Hg = Hg.replace(/(^\s*)|(\s*$)/g, "");
    return Hg;
};
function _7Ac(DG, LT, I) {
    if (!I) I = 0;
    var x = DG.indexOf(LT, I);
    if (x <= 0) return x;
    if (DG.charAt(x - 1) != '\\' && DG.indexOf("'") < 0 && DG.indexOf('"') < 0) return x;
    for (var i = 0; i < DG.length; i++) {
        var BY = DG.charAt(i);
        if (i >= I && BY == LT) return i;
        if (BY == '\\') {
            i++;
            continue;
        }
        if (BY == '"' || BY == "'") {
            i = DG.indexOf(BY, i + 1);
            if (i < 0) return - 1;
        }
    }
    return - 1;
};
function NumberToString(Bb, DG, HD, K3, K4) {
    if (HD && Bb && typeof(Bb) == "string") {
        var BL = Bb.split(HD.charAt(0));
        for (var i = 0; i < BL.length; i++) {
            var H = BL[i].split(HD.charAt(1));
            for (var j = 0; j < H.length; j++) H[j] = NumberToString(H[j] - 0, DG);
            BL[i] = H.join(K4 ? K4: HD.charAt(1));
        }
        return BL.join(K3 ? K3: HD.charAt(0));
    }
    if (Bb == null) Bb = 0;
    else if (!isFinite(Bb)) return _7o.NaN;
    Bb = Math.round(Bb * 1000000) / 1000000;
    if (!DG) return Bb + "";
    if (DG.length == 1) {
        switch (DG) {
        case 'g':
            DG = BR >= 1e10 || BR <= -1e10 ? _7o.LU: _7o.LV;
            break;
        case 'f':
            DG = _7o.LW;
            break;
        case 'c':
            DG = _7o.LX;
            break;
        case 'p':
            DG = _7o.LY;
            break;
        case 'r':
            DG = BR >= 1e10 || BR <= -1e10 ? _7o.LZ: _7o.La;
            break;
        case 'e':
            DG = _7o.Lb;
            break;
        }
    }
    if (!DG) return Bb + "";
    var x = _7Ac(DG, ';', 0);
    if (x >= 0) {
        var y = _7Ac(DG, ';', x + 1);
        if (Bb > 0) DG = DG.substring(0, x);
        else if (Bb == 0) {
            if (y < 0) DG = DG.substring(0, x);
            else DG = DG.substring(y + 1);
        } else {
            if (y < 0) DG = DG.substring(x + 1);
            else DG = DG.substring(x + 1, y);
            Bb = -Bb;
        }
    }
    var exp = "",
    Lc = 0,
    Ld = -1;
    x = _7Ac(DG, 'e', 0);
    if (x < 0) x = _7Ac(DG, 'E', 0);
    if (x >= 0 && (x == 0 || DG.charAt(x - 1) != '\\')) {
        Ld = x;
        var B = DG.charAt(x);
        x++;
        var BY = DG.charAt(x);
        var Le = false;
        if (BY == '+') {
            Le = true;
            x++;
        }
        if (BY == '-') x++;
        var Lf = x;
        while (DG.charAt(x) == '0') x++;
        var form = DG.substring(Lf, x);
        if (Lf > 0) {
            var Ck = _7Ac(DG, '.');
            Ck = Lg(DG, 0, Ck ? Ck: DG.length);
            if (!Ck) Ck = 1;
            var Lh = Math.floor(Math.LOG10E * Math.log(Math.abs(Bb))) - Ck + 1;
            Bb /= Math.pow(10, Lh);
            exp = B + (Lh >= 0 && Le ? "+": "") + NumberToString(Lh, form);
            Lc = x - Ld;
        }
    }
    if (_7Ac(DG, '%') >= 0) Bb *= 100;
    function Lg(DG, Li, Hz) {
        var Lj = 0;
        for (var Ck = Li; Ck < Hz; Ck++) {
            var BY = DG.charAt(Ck);
            if (BY == '\\') Ck++;
            else if (BY == '#' || BY == '0') Lj++;
            else if (BY == '"' || BY == "'") {
                Ck = DG.indexOf(BY, Ck + 1);
                if (Ck < 0) break;
            }
        }
        return Lj;
    };
    x = _7Ac(DG, '.');
    if (x >= 0) {
        var Lj = Lg(DG, x + 1, DG.length);
        if (Lj && Bb) {
            var Hl = Math.pow(10, Lj);
            Bb = Math.round(Bb * Hl) / Hl;
            if (Bb > -1 / Hl && Bb < 1 / Hl) Bb = 0;
        } else Bb = Math.round(Bb);
    } else Bb = Math.round(Bb);
    var y = 0;
    while (x > 0 && DG.charAt(x - 1) == ',') {
        y++;
        x--;
    }
    if (y) Bb /= Math.pow(1000, y);
    var Lk = false;
    y = _7Ac(DG, ',');
    if (y >= 0 && (y < x || x < 0)) Lk = true;
    var LD = new Array();
    var a = 0,
    Ll = 0,
    Lm;
    var DC = null;
    if (Bb < 0) {
        Bb = -Bb;
        DC = true;
    }
    x = _7Ac(DG, '.', 0);
    if (x < 0) x = DG.length;
    x--;
    var LE = Math.floor(Bb);
    var BQ = -1;
    while (x >= 0) {
        var BY = DG.charAt(x);
        if (x && DG.charAt(x - 1) == '\\') LD[a++] = BY;
        else if (x == Lc + Ld - 1) {
            LD[a++] = exp;
            x -= Lc + 1;
        } else switch (BY) {
        case '#':
            if (LE < 1) break;
        case '0':
            BQ++;
            if (Lk && BQ == 3) {
                LD[a++] = _7o.GroupSeparator;
                BQ = 0;
            }
            LD[a++] = LE % 10;
            LE -= LE % 10;
            LE /= 10;
            Ll = a;
            break;
        case ',':
            break;
        case '"':
        case "'":
            var I = DG.lastIndexOf(BY, x - 1);
            if (I == -1) break;
            LD[a++] = DG.substring(I + 1, x);
            x = I;
            break;
        case '\\':
            break;
        default:
            LD[a++] = BY;
        };
        x--;
    }
    if (LE >= 1 && Ll != a) Lm = LD.slice(Ll, a);
    a = Ll;
    while (LE >= 1) {
        BQ++;
        if (Lk && BQ == 3) {
            LD[a++] = _7o.GroupSeparator;
            BQ = 0;
        }
        LD[a++] = LE % 10;
        LE -= LE % 10;
        LE /= 10;
    }
    if (Lm) {
        for (var i = 0; i < Lm.length; i++) LD[a++] = Lm[i];
    }
    if (LD.length > 1) LD.reverse();
    var K5 = "";
    var min = Math.pow(10, Math.round(Math.log(Bb) * Math.LOG10E) - 14);
    if (min > 0.001) min = 0.001;
    LE = Bb - Math.floor(Bb) + min / 2;
    x = _7Ac(DG, '.', 0);
    if (x >= 0) {
        for (x++; x < DG.length; x++) {
            if (x == Ld) {
                x += Lc - 1;
                K5 += exp;
                continue;
            }
            var BY = DG.charAt(x);
            switch (BY) {
            case '#':
                if (LE <= min) break;
            case '0':
                LE = LE * 10;
                min = min * 10;
                var a = Math.floor(LE % 10);
                LE -= a;
                K5 += a;
                break;
            case '"':
            case "'":
                var I = DG.indexOf(DG.charAt(x), x + 1);
                if (I == -1) break;
                K5 += DG.substring(x + 1, I);
                x = I;
                break;
            case '\\':
                break;
            default:
                K5 += BY;
            }
        }
    }
    if (DC) {
        for (var i = 0; i < LD.length && (LD[i] < "0" || LD[i] > "9"); i++);
        LD[i] = "-" + (LD[i] == null ? "": LD[i]);
    }
    return LD.join("") + (K5.length && K5.charAt(0) >= '0' && K5.charAt(0) <= '9' ? _7o.DecimalSeparator: "") + K5;
};
function StringToNumber(DD, DG, HD) {
    if (!DD) return 0;
    if (typeof(DD) == "number") return DD;
    if (HD) {
        var BL = DD.split(HD.charAt(0));
        for (var i = 0; i < BL.length; i++) {
            var H = BL[i].split(HD.charAt(1));
            for (var j = 0; j < H.length; j++) H[j] = StringToNumber(H[j], DG);
            BL[i] = H.join(HD.charAt(1));
        }
        return BL.join(HD.charAt(0));
    }
    if (!DD.replace) return 0;
    DD = DD.replace(new RegExp("\\" + _7o.GroupSeparator, "g"), "").replace(_7o.DecimalSeparator, ".");
    if (DD.search(/[xe]/i) >= 0) {
        if (DD.charAt(0).toLowerCase() == "x") DD = "0" + DD;
        try {
            return eval(DD);
        } catch(B) {
            return 0;
        }
    }
    return parseFloat(DD);
};
function KeyInt(IL, D4, Bn) {
    var Ln = !!IL.preventDefault;
    var Lo = IL.keyCode;
    if (Lo == 0 || _7F) Lo = IL.charCode;
    var Lp = Lo,
    Cu = true,
    Value = D4.value;
    if (Bn) for (var i = 0; i < Bn.length; i++) if (Lo == Bn.charCodeAt(i)) return true;
    if (Lo == 88 || Lo == 120) {
        if (Value.indexOf('x', 0) >= 0 || Value.indexOf('X', 0) >= 0) Cu = false;
        else Lo = 120;
    } else if (Lo == 45) {
        if (Value.indexOf('-', 0) >= 0) Cu = false;
    } else if (Lo >= 65 && Lo <= 70 || Lo >= 97 && Lo <= 102) {
        if (Value.indexOf('x', 0) < 0 && Value.indexOf('X', 0) < 0) Cu = false;
        else if (Lo >= 97) Lo -= 32;
    } else if ((Lo < 48 || Lo > 57) && Lo != 8 && Lo != 37 && Lo != 39 && Lo != 0) Cu = false;
    if (Lp != Lo) {
        if (!Ln) IL.keyCode = Lo;
        else if (Cu) {
            ReplaceKey(D4, Lo);
            Cu = false;
        }
    }
    if (!Cu) {
        if (Ln) IL.preventDefault();
        else IL.returnValue = Cu;
    }
    return Cu;
};
function KeyFloat(IL, D4, Bn) {
    var Ln = !!IL.preventDefault;
    var Lo = IL.keyCode;
    if (Lo == 0 || _7F) Lo = IL.charCode;
    var Lp = Lo,
    Cu = true,
    Value = D4.value;
    if (Bn) for (var i = 0; i < Bn.length; i++) if (Lo == Bn.charCodeAt(i)) return true;
    if (Lo == 45) {
        var a = Value.indexOf('-', 0);
        if (a >= 0 && (Value.indexOf('E', 0) < 0 && Value.indexOf('e', 0) < 0 || Value.indexOf('-', a + 1) >= 0)) Cu = false;
    } else if (Lo == 46 || Lo == 44) {
        var Bp = _7o.DecimalSeparator;
        Lo = Bp.charCodeAt(0);
        if (Value.indexOf(_7o.DecimalSeparator, 0) >= 0) Cu = false;
    } else if (Lo == 101 || Lo == 69) {
        if (Value.indexOf('E', 0) >= 0 || Value.indexOf('e', 0) >= 0) Cu = false;
        else Lo = 69;
    } else if ((Lo < 48 || Lo > 57) && Lo != 8 && Lo != 37 && Lo != 39 && Lo != 0) Cu = false;
    if (Lp != Lo) {
        if (!Ln) IL.keyCode = Lo;
        else if (Cu) {
            ReplaceKey(D4, Lo);
            Cu = false;
        }
    }
    if (!Cu) {
        if (Ln) IL.preventDefault();
        else IL.returnValue = Cu;
    }
    return Cu;
};
function KeyDate(IL, D4, Hg, Bn) {
    var Ln = !!IL.preventDefault;
    var Lo = IL.keyCode;
    if (Lo == 0 || _7F) Lo = IL.charCode;
    var Lp = Lo,
    Cu = true;
    if (Bn) for (var i = 0; i < Bn.length; i++) if (Lo == Bn.charCodeAt(i)) return true;
    if (Lo == 58 || Lo == 45) {
        Lo = _7o.TimeSeparator.charCodeAt(0);
    } else if (Lo == 46 || Lo == 47) {
        Lo = _7o.DateSeparator.charCodeAt(0);
    } else if (Lo == 32 || Lo == 44) {
        Lo = 32;
    } else if (Hg && (Lo == 65 || Lo == 97 || Lo == 80 || Lo == 112 || Lo == 77 || Lo == 109));
    else if ((Lo < 48 || Lo > 57) && Lo != 8 && Lo != 37 && Lo != 39 && Lo != 0) Cu = false;
    if (Lp != Lo) {
        if (!Ln) IL.keyCode = Lo;
        else if (Cu) {
            ReplaceKey(D4, Lo);
            Cu = false;
        }
    }
    if (!Cu) {
        if (Ln) IL.preventDefault();
        else IL.returnValue = Cu;
    }
    return Cu;
};
function ReplaceKey(D4, Jw) {
    try {
        var Bh = D4.selectionStart,
        B = D4.selectionEnd;
        if (B == null) B = Bh;
        D4.value = D4.value.slice(0, Bh) + String.fromCharCode(Jw) + D4.value.slice(B);
        D4.selectionStart = Bh + 1;
        D4.selectionEnd = Bh + 1;
    } catch(B) {}
};
function CompareStrings(Lq, Lr, type) {
    var EE = !!Lq.localeCompare && type & 2;
    if (type & 4) {
        if (EE) {
            Lq = Lq.toLocaleLowerCase();
            Lr = Lr.toLocaleLowerCase();
        } else {
            Lq = Lq.toLowerCase();
            Lr = Lr.toLowerCase();
        }
    }
    if (type & 8) {
        Lq = Lq.replace(/\s/g, "");
        Lr = Lr.replace(/\s/g, "");
    }
    if (EE) return Lq.localeCompare(Lr);
    else return Lq < Lr ? -1 : (Lq > Lr ? 1 : 0);
};
function _7Ad(Lq, Lr, type) {
    if (!Lq || !Lr) return 0;
    if (type & 4) {
        if (EE) {
            Lq = Lq.toLocaleLowerCase();
            Lr = Lr.toLocaleLowerCase();
        } else {
            Lq = Lq.toLowerCase();
            Lr = Lr.toLowerCase();
        }
    }
    if (type & 8) {
        Lq = Lq.replace(/\s/g, "");
        Lr = Lr.replace(/\s/g, "");
    }
    var BN = Lq.length < Lr.length ? Lq.length: Lr.length;
    for (var i = 0; i < BN; i++) {
        if (Lq.charCodeAt(i) != Lr.charCodeAt(i)) return i;
    }
    return BN;
};
function SearchString(Lq, Lr, type) {
    var EE = Lq.localeCompare != null && type & 2;
    if (type & 4) {
        if (EE) {
            Lq = Lq.toLocaleLowerCase();
            Lr = Lr.toLocaleLowerCase();
        } else {
            Lq = Lq.toLowerCase();
            Lr = Lr.toLowerCase();
        }
    }
    if (type & 8) {
        Lq = Lq.replace(/\s/g, "");
        Lr = Lr.replace(/\s/g, "");
    }
    return Lq.search(Lr.replace(/[\^\$\.\*\+\?\=\!\:\|\\\/\(\)\[\]\{\}\/]/g, "\\$&"));
};
function GetElem(id) {
    if (document.getElementById) return document.getElementById(id);
    else return document.all[id];
};
function _7s(D4, name) {
    return D4 ? D4.getElementsByTagName(name) : new Array();
};
function GetKey(event) {
    return event.keyCode ? event.keyCode: event.charCode;
};
function GetWindowSize() {
    var CY = 0,
    CZ = 0;
    if (window.innerWidth) return [window.innerWidth, window.innerHeight];
    var Jf = document.documentElement;
    if (!Jf || !Jf.clientWidth) Jf = document.body;
    return [Jf.clientWidth, Jf.clientHeight];
};
function GetWindowScroll() {
    var Jf = document.documentElement;
    if (!Jf || !Jf.clientWidth) return [document.body.scrollLeft, document.body.scrollTop];
    var Jc = document.body;
    return [Jf.scrollLeft ? Jf.scrollLeft: Jc.scrollLeft, Jf.scrollTop ? Jf.scrollTop: Jc.scrollTop];
};
function _7AK(x, y) {
    try {
        var Jf = document.documentElement;
        if (!Jf || !Jf.clientWidth || window.pageXOffset != null && document.compatMode != "CSS1Compat") Jf = document.body;
        Jf.scrollLeft = x;
        Jf.scrollTop = y;
    } catch(B) {}
};
function _7Ae() {
    if (window.scrollMaxX != null) return [window.scrollMaxX + window.innerWidth, window.scrollMaxY + window.innerHeight];
    var Jf = document.documentElement;
    if (!Jf || !Jf.clientWidth) Jf = document.body;
    return [Jf.scrollLeft, Jf.scrollTop];
};
function EventXYToElement(IL, Ls, scroll, Lt) {
    var size = scroll == false ? [0, 0] : GetWindowScroll();
    var x = IL.clientX + size[0];
    var y = IL.clientY + size[1];
    var Jf = document.body;
    if (Jf) {
        if (_7S) {
            x -= Jf.clientLeft;
            y -= Jf.clientTop;
        }
        if (_7T) {
            if (_7AJ != 1) {
                if (IL.type) {
                    x = Math.floor(x / _7AJ);
                    y = Math.floor(y / _7AJ);
                } else {
                    x = IL.clientX + Math.floor(size[0] / _7AJ);
                    y = IL.clientY + Math.floor(size[1] / _7AJ);
                }
            }
            x -= Math.floor((document.documentElement.clientLeft + Jf.offsetLeft) / _7AJ);
            y -= Math.floor((document.documentElement.clientTop + Jf.offsetTop) / _7AJ);
        }
        if (_7O && !_7P) {
            x -= Jf.scrollLeft;
            y -= Jf.scrollTop;
            x -= Jf.offsetLeft;
            y -= Jf.offsetTop;
        }
        if (_7F) {
            x -= Grids.Lu;
            y -= Grids.Lv;
        }
    }
    var Bg = ElemToParent(Ls, Jf);
    return [x - Bg[0], y - Bg[1]];
};
function ElemToParent(Ls, EU, Lw) {
    if (!Ls) return null;
    if (_7R && Ls.getBoundingClientRect) {
        var BL = Ls.getBoundingClientRect();
        var x = BL.left,
        y = BL.top;
        if (!EU) EU = document.documentElement;
        if (_7S && EU == document.body) {
            x -= EU.clientLeft;
            y -= EU.clientTop;
        }
        if (_7T && EU == document.documentElement || _7S && EU == document.body) {
            x += EU.scrollLeft;
            y += EU.scrollTop;
        }
        if (_7F || _7X && EU == document.body) {
            x += document.documentElement.scrollLeft;
            y += document.documentElement.scrollTop;
        }
        if (_7V && _7I && EU == document.documentElement) {
            x -= document.documentElement.clientLeft;
            y -= document.documentElement.clientTop;
        }
        if (_7R && !_7X || EU != document.body && (!_7X || EU != document.documentElement)) {
            var H = EU.getBoundingClientRect();
            x -= H.left;
            y -= H.top;
        }
        return [x / _7AJ, y / _7AJ];
    }
    var x = Ls.offsetLeft,
    y = Ls.offsetTop,
    Bg, Jf = document.body,
    Lx = Ls;
    try {
        Bg = Ls.offsetParent;
    } catch(B) {
        return [x, y];
    };
    if (_7R) {
        if (_7T && _7AJ != 1 && Lx.style.position == "absolute") {
            x += Math.floor(Lx.offsetLeft / _7AJ - Lx.offsetLeft);
            y += Math.floor(Lx.offsetTop / _7AJ - Lx.offsetTop);
        }
        while (Bg && Bg != EU) {
            var D4 = Bg.tagName.toLowerCase();
            if (D4 == "html") break;
            if (D4 == "table") {
                x -= Bg.clientLeft;
                y -= Bg.clientTop;
            }
            if (_7T && _7AJ != 1 && D4 == "body") {
                x += Math.floor(Bg.offsetLeft / _7AJ - Bg.offsetLeft);
                y += Math.floor(Bg.offsetTop / _7AJ - Bg.offsetTop);
            }
            x += Bg.offsetLeft + Bg.clientLeft - Bg.scrollLeft;
            y += Bg.offsetTop + Bg.clientTop - Bg.scrollTop;
            Bg = Bg.offsetParent;
        }
    } else {
        var Ly = Ls;
        while (1) {
            while (Ly != Bg) {
                Ly = Ly.parentNode;
                if (!Ly || Ly == document.documentElement || Ly == EU && Bg == EU) break;
                if (_7F && ((!_7J || _7K) && !_7W && (Ly.scrollWidth != Ly.offsetWidth || Ly.scrollHeight != Ly.offsetHeight) || Ly == Bg) || _7G && Ly == Bg) {
                    if (Ly.clientWidth && Ly.clientHeight && Ly.offsetHeight && Ly != document.body) {
                        var Ik = Ly.offsetWidth - Ly.clientWidth,
                        J5 = Ly.offsetHeight - Ly.clientHeight;
                        if (_7J) {
                            if (Ik >= 30) Ik -= 30;
                            if (J5 >= 24) J5 -= 24;
                        } else {
                            if (Ik >= 16) Ik -= 16;
                            if (J5 >= 16) J5 -= 16;
                        };
                        if (_7F && (Ly == Bg && (Ly.scrollWidth != Ly.offsetWidth || Ly.scrollHeight != Ly.offsetHeight))) {
                            x += Math.floor(Ik / 2) * 2;
                            y += Math.floor(J5 / 2) * 2;
                        } else {
                            var D4 = Ly.tagName.toLowerCase();
                            if (D4 != "table" && (D4 != "td" || _7G)) {
                                x += Math.floor(Ik / 2);
                                y += Math.floor(J5 / 2);
                            }
                        }
                    }
                }
                if (Ly != document.body && !Lw) {
                    x -= Ly.scrollLeft;
                    y -= Ly.scrollTop;
                }
                if ((_7G || _7M && _7N < 9.5) && Ly.tagName) {
                    var D4 = Ly.tagName.toLowerCase();
                    if (D4 == 'tr') {
                        y += Ly.offsetTop;
                        x += Ly.scrollLeft;
                    } else if (D4 == 'tbody') x += Ly.scrollLeft;
                    else if (_7M && D4 == 'table' && Ly.style.borderCollapse != 'collapse') y += Math.floor((Ly.offsetHeight - Ly.clientHeight) / 2);
                }
                if (Ly == EU) {
                    var size = ElemToParent(Ly, Bg, 1);
                    x -= size[0];
                    y -= size[1];
                    Bg = EU;
                    break;
                }
            }
            if (!Bg || Bg == EU) break;
            x += Bg.offsetLeft;
            y += Bg.offsetTop;
            if (Bg != document.body) Lx = Bg;
            Bg = Bg.offsetParent;
        }
        if (_7O && (Bg == document.body || !Bg) && Lx.style.position == "absolute") {
            x -= document.body.offsetLeft;
            y -= document.body.offsetTop;
        }
        if (_7F && (Bg == document.body || !Bg) && Lx.style.position == "absolute") {
            x -= Grids.Lu;
            y -= Grids.Lv;
        }
        if ((_7J && !_7K || _7W) && Lx.style.position == "absolute") {
            var Ik = Lx.offsetWidth - Lx.clientWidth,
            J5 = Lx.offsetHeight - Lx.clientHeight;
            x -= Math.floor(Ik / 2);
            y -= Math.floor(J5 / 2);
        }
    };
    if (EU) {
        if (Bg != EU) {
            var size = ElemToParent(EU, null);
            x -= size[0];
            y -= size[1];
        } else if (_7S && EU != document.body || _7T) {
            x += EU.clientLeft;
            y += EU.clientTop;
        }
    }
    return [x, y];
};
function CancelEvent(IL, type) {
    if (!IL) IL = window.event;
    if (!IL) return;
    if (!type) type = 3;
    if (type & 4) {
        try {
            IL.Handled = true;
        } catch(B) {
            type |= 1;
        }
    }
    if (type & 1) {
        if (IL.Lz) IL.Lz();
        else if (IL.stopPropagation) IL.stopPropagation();
        else IL.cancelBubble = true;
    }
    if (type & 2) {
        if (IL.preventDefault) {
            if (IL.cancelable) IL.preventDefault();
        } else IL.returnValue = false;
    }
};
function _7Af(IL) {
    CancelEvent(IL, 1);
};
function _7Ag(IL) {
    CancelEvent(IL, 2);
};
function GetStyle(Ls) {
    if (!Ls) return null;
    if (window.getComputedStyle) return getComputedStyle(Ls, null);
    var BZ = document.defaultView;
    if (BZ && BZ.getComputedStyle) return BZ.getComputedStyle(Ls, null);
    return Ls.currentStyle;
};
function _7Ah(style, L0) {
    if (!style) return 0;
    var L1 = parseInt(style.borderLeftWidth),
    L2 = parseInt(style.borderRightWidth);
    var L3 = parseInt(style.paddingLeft),
    L4 = parseInt(style.paddingRight);
    return (L1 ? L1: 0) + (L2 ? L2: 0) + (!L0 ? (L3 ? L3: 0) + (L4 ? L4: 0) : 0);
};
function _7Ai(style, L0) {
    if (!style) return 0;
    var L5 = parseInt(style.borderTopWidth),
    L6 = parseInt(style.borderBottomWidth);
    var L7 = parseInt(style.paddingTop),
    L8 = parseInt(style.paddingBottom);
    return (L5 ? L5: 0) + (L6 ? L6: 0) + (!L0 ? (L7 ? L7: 0) + (L8 ? L8: 0) : 0);
};
function CreateXML(DD) {
    var EN = null;
    try {
        if (DD.charCodeAt(0) <= 32) {
            for (var i = 0; i < DD.length && DD.charCodeAt(i) <= 32; i++);
            DD = DD.slice(i);
        }
        if (window.DOMParser) {
            EN = (new DOMParser()).parseFromString(DD, "text/xml");
            if (EN && EN.firstChild && EN.firstChild.tagName.toLowerCase() == 'parsererror') EN = null;
        } else if (window.ActiveXObject && (!Grids || !Grids.NoTryActiveX)) {
            EN = new ActiveXObject("Microsoft.XMLDOM");
            EN.async = "false";
            EN.loadXML(DD);
        }
    } catch(B) {};
    if ((!EN || !EN.firstChild) && window._7v) return _7v(DD);
    return EN;
};
if (window.Node && Node.prototype && Node.prototype.__defineGetter__) {
    Node.prototype.__defineGetter__("xml", function () {
        return (new XMLSerializer).serializeToString(this);
    });
}
function _7u(DD) {
    if (typeof(DD) != "string") {
        if (!DD) return "";
        else DD = DD.toString();
    }
    var Bg = 0;
    while (DD.charCodeAt(Bg) <= 32) Bg++;
    return DD.charAt(Bg);
};
function _7AE(Ls) {
    if (!Ls || !Ls.focus) return;
    var E = new Array(),
    CE = new Array();
    var Bg = 0,
    B = Ls;
    while (B) {
        if (B.onclick) {
            E[Bg] = B;
            CE[Bg] = B.onclick;
            Bg++;
            B.onclick = null;
        }
        B = B.parentNode;
    }
    Ls.focus();
    for (var i = 0; i < Bg; i++) E[i].onclick = CE[i];
};
function CopyObject(FX) {
    if (FX && typeof(FX) == "object") {
        var L9 = new FX.constructor(),
        Bg,
        Cd;
        for (Bg in FX) {
            Cd = FX[Bg];
            if (Cd && typeof(Cd) == "object") L9[Bg] = CopyObject(Cd);
            else L9[Bg] = Cd;
        }
        return L9;
    } else return FX;
};
function ShowHTML(EK, name) {
    if (!EK) EK = "--- Empty ---";
    else EK = EK + "";
    var Bg = 0;
    while (EK.charCodeAt(Bg) <= 32) Bg++;
    var Bh = EK.slice(Bg, Bg + 5).toLowerCase();
    if (Bh != "<html" && Bh != "<body" && Bh != "<!doc") {
        EK = "<pre>" + EK.replace(/\&amp;/g, "&").replace(/\&lt;/g, "<").replace(/\&gt;/g, ">").replace(/&/g, "&amp;").replace(/<\//g, "&lt;/").replace(/>/g, "&gt;").replace(/</g, "<br>&lt;") + "</pre>";
    }
    var CY = window.open("", name, "", false);
    if (!CY) return false;
    try {
        CY.document.write(EK);
        CY.document.close();
    } catch(B) {
        return false;
    };
    return true;
};
function _7Aj(D4, parent, x, y, J0, rel, MA) {
    if (!D4 || !parent) return;
    if (rel) {
        var Ik = 0,
        J5 = 0;
    } else {
        var Ik = D4.offsetLeft,
        J5 = D4.offsetTop;
        var BL = ElemToParent(D4, parent);
        Ik -= BL[0];
        J5 -= BL[1];
    };
    if (x == null) x = Math.floor((parent.clientWidth - D4.offsetWidth) / 2) + Ik;
    if (y == null) y = Math.floor((parent.clientHeight - D4.offsetHeight) / 2) + J5;
    if (MA) {
        D4.style.left = (x < 0 ? Ik: x) + "px";
        D4.style.top = (y < 0 ? J5: y) + "px";
    } else {
        if (x < 0 || y < 0) D4.style.overflow = "auto";
        if (x < 0) {
            D4.style.left = Ik + "px";
            D4.style.width = parent.clientWidth + "px";
        } else D4.style.left = x + "px";
        if (y < 0) {
            D4.style.top = J5 + "px";
            D4.style.height = parent.clientHeight + "px";
            if (x >= 0) D4.style.width = (D4.offsetWidth + _7Y) + "px";
        } else D4.style.top = y + "px";
    }
};
function _7Ak(text, D4, MB, height) {
    if (!text) return;
    if (typeof(D4) == "string") D4 = GetElem(D4);
    if (!D4) return;
    MB = MB ? " class='" + MB + "'": "";
    D4.innerHTML = "<div style='visibility:hidden;position:absolute;left:0px;top:0px;'><div " + MB + ">" + text + "</div></div>";
    var CY = D4.firstChild.offsetWidth;
    var Bh = "<table style='margin-left:0px;' width='100%' height='100%'" + _7B;
    var BQ = height ? Math.floor(D4.offsetHeight / height + 0.3) : 1;
    if (BQ < 1) BQ = 1;
    for (var i = 0; i < BQ; i++) Bh += "<tr><td valign='middle' align='center'>" + "<div" + MB + " style='width:" + CY + "px;'>" + text + "</div></td></tr>";
    Bh += _7C;
    D4.innerHTML = Bh;
    if (_7X) D4.firstChild.rows[0].style.height = D4.firstChild.offsetHeight + "px";
};
function _7x(xml, name, ED) {
    var BL = [ED, "soap", "soap12", "soapenv"],
    D4;
    for (var i = 0; i < BL.length; i++) {
        D4 = xml.getElementsByTagName(BL[i] + ":" + name);
        if (D4 && D4[0]) return D4;
        D4 = xml.getElementsByTagName(BL[i] + ":" + name.toLowerCase());
        if (D4 && D4[0]) return D4;
    }
    return new Array();
};
function AttachEvent(D4, name, BV) {
    if (D4.addEventListener) D4.addEventListener(name, BV, false);
    else if (D4.attachEvent) D4.attachEvent("on" + name, BV);
    else D4["on" + name] = BV;
};
function DetachEvent(D4, name, BV) {
    if (D4.removeEventListener) D4.removeEventListener(name, BV, false);
    else if (D4.detachEvent) D4.detachEvent("on" + name, BV);
    else D4["on" + name] = null;
};
function _7t() {
    if (window.XMLHttpRequest) return new XMLHttpRequest();
    if (window.ActiveXObject) {
        if (window._7Al) return new ActiveXObject(_7Al);
        var EA, MC = ["Msxml3.XMLHTTP", "Msxml2.XMLHTTP", "Microsoft.XMLHTTP"];
        for (var i = 0; i < MC.length; i++) {
            try {
                var D9 = new ActiveXObject(MC[i]);
                _7Al = MC[i];
                return D9;
            } catch(B) {
                EA = B.message;
            }
        }
        return EA;
    }
    return null;
};
if (!Array.prototype.shift) {
    Array.prototype.shift = function () {
        var BL = this[0];
        for (var i = 0; i < this.length; i++) this[i] = this[i + 1];
        this.length--;
        return BL;
    };
    Array.prototype.unshift = function () {
        var BN = arguments.length;
        for (var i = this.length - 1; i >= 0; i--) this[i + BN] = this[i];
        for (var i = 0; i < BN; i++) this[i] = arguments[i];
    }
}
if (!Function.prototype.apply) {
    Function.prototype.apply = function (Z, MD) {
        Z.MF = this;
        if (!MD) return Z.MF();
        var DF = MD.length;
        if (!DF) return Z.MF();
        if (DF == 1) return Z.MF(MD[0]);
        if (DF == 2) return Z.MF(MD[0], MD[1]);
        if (DF == 3) return Z.MF(MD[0], MD[1], MD[2]);
    }
}
if (!window.encodeURIComponent) {
    window.encodeURIComponent = function (Bh) {
        return escape(Bh).replace(/\@/g, "%40").replace(/\+/g, "%2B").replace(/\//g, "%2F").replace(new RegExp("%21", "g"), "!").replace(new RegExp("%7E", "g"), "~").replace(new RegExp("%27", "g"), "'").replace(new RegExp("%28", "g"), "(").replace(new RegExp("%29", "g"), ")")
    }
}
function SetEvent(name, id, BV) {
    if (!Grids[name]) {
        Grids[name] = function (G) {
            if (G && G.id && Grids[name + "_" + G.id]) return Grids[name + "_" + G.id].apply(this, arguments);
            if (Grids[name + "_"]) return Grids[name + "_"].apply(this, arguments);
        }
    }
    if (id || id == 0) Grids[name + "_" + id] = BV;
    else Grids[name + "_"] = BV;
};
function AddEvent(name, id, BV) {
    if (!Grids[name]) {
        Grids[name] = function (G) {
            var Cu = null,
            MG = null;
            for (var i = 0; i < 10; i++) {
                if (G && G.id && Grids[name + "_" + G.id + "_" + i]) {
                    Cu = Grids[name + "_" + G.id + "_" + i].apply(this, arguments);
                    if (Cu != null) MG = Cu;
                }
            }
            for (var i = 0; i < 10; i++) {
                if (Grids[name + "_" + i]) {
                    Cu = Grids[name + "_" + i].apply(this, arguments);
                    if (Cu != null) MG = Cu;
                }
            }
            return MG;
        }
    } else DelEvent(name, id, BV);
    if (id || id == 0) name = name + "_" + id;
    for (var i = 0; i < 10; i++) {
        if (Grids[name + "_" + i] == null) {
            Grids[name + "_" + i] = BV;
            break;
        }
    }
};
function DelEvent(name, id, BV) {
    if (id || id == 0) name = name + "_" + id;
    for (var i = 0; i < 10; i++) {
        if (Grids[name + "_" + i] == BV) Grids[name + "_" + i] = null;
    }
};
function _7AU(DD) {
    if (DD == null) return new Array();
    if (typeof(DD) == 'string') DD = DD.split(",");
    if (typeof(DD) == 'number') DD = [DD];
    return DD;
};
function _7Am() {
    if (_7Y && _7Z) return;
    var D8 = document.createElement("div"),
    Bh = D8.style;
    Bh.position = "absolute";
    Bh.left = "0px";
    Bh.top = "0px";
    Bh.width = "100px";
    Bh.height = "100px";
    Bh.overflow = "scroll";
    Bh.innerHTML = "<div style='width:200px;height:200px;overflow:hidden'>" + _7D + "</div>";
    document.body.appendChild(D8);
    _7Y = D8.offsetWidth - D8.clientWidth;
    _7Z = D8.offsetHeight - D8.clientHeight;
    if (_7R || _7H && !_7O || _7G || _7M) _7Y++;
    if (_7H && !_7O || _7G || _7M) _7Z++;
    else if (_7O) {
        _7Z++;
        _7Y++;
    }
    if (! (_7Y > 10)) _7Y = 22;
    if (! (_7Z > 10)) _7Z = 22;
    document.body.removeChild(D8);
};
function _7w(MH) {
    alert("Module \"" + MH + "\" is not available !");
};
var _7AJ = 1;
var _7An;
function _7AO() {
    if (!_7An) {
        _7An = document.createElement("div");
        var Bh = _7An.style;
        Bh.position = "absolute";
        Bh.left = "500px";
        Bh.top = "0px";
        Bh.width = "0px";
        Bh.height = "0px";
        Bh.visibility = "hidden";
        document.body.insertBefore(_7An, document.body.firstChild);
    }
    _7AJ = _7An.offsetLeft / 500;
    if (_7AJ > 0.95 && _7AJ < 1.05) _7AJ = 1;
    return _7AJ;
};
function _7Ao(D4) {
    var Fk = true;
    for (var Bg = D4.firstChild; Bg; Bg = Bg.nextSibling) {
        if (Bg.nodeType == 1) {
            _7Ao(Bg);
            Fk = false;
        }
    }
    if (Fk) {
        try {
            D4.innerHTML = D4.innerHTML;
        } catch(B) {}
    }
};
var _7Ap = {
    "m": 60000,
    "m5": 60000 * 5,
    "m10": 60000 * 10,
    "m15": 60000 * 15,
    "m30": 60000 * 30,
    "h": 3600000,
    "h2": 3600000 * 2,
    "h3": 3600000 * 3,
    "h6": 3600000 * 6,
    "h8": 3600000 * 8,
    "h12": 3600000 * 12,
    "d": 86400000,
    "w": 86400000 * 7,
    "w1": 86400000 * 7,
    "M": 86400000 * 30.4,
    "M3": 86400000 * 30.4 * 3,
    "M6": 86400000 * 30.4 * 6,
    "y": 86400000 * 365
};
TCalc.prototype.ganttpercent = function (MI, MJ, MK, ML, MM) {
    if (!ML) ML = this.Col;
    var sum = 0,
    BL = this.BK(),
    BN = BL.length,
    G = this.Grid;
    var MN = [],
    F = [],
    BQ = 0;
    for (var i = 0; i < BN; i++) {
        var J = BL[i],
        Bh = Get(J, MI),
        B = Get(J, MJ),
        Bg = Get(J, ML);
        if (Bh && B && Bg != null) {
            B = new Date(B);
            this.Grid.MO(B, MK);
            var DF = B - Bh;
            MN[i] = DF;
            F[i] = Bg;
            BQ += DF;
        }
    }
    for (var i = 0; i < BN; i++) {
        if (!F[i]) continue;
        sum += F[i] * MN[i];
    }
    return sum ? Math.round(sum / BQ) : "";
};
TCalc.prototype.ganttpercentduration = function (MP, ML, MM) {
    if (!ML) ML = this.Col;
    var sum = 0,
    BL = this.BK(),
    BN = BL.length,
    G = this.Grid;
    var MN = [],
    F = [],
    BQ = 0;
    for (var i = 0; i < BN; i++) {
        var J = BL[i],
        DF = Get(J, MP),
        Bg = Get(J, ML);
        if (DF && Bg != null) {
            MN[i] = DF;
            F[i] = Bg;
            BQ += DF;
        }
    }
    for (var i = 0; i < BN; i++) {
        if (!F[i]) continue;
        sum += F[i] * MN[i];
    }
    return sum ? Math.round(sum / BQ) : "";
};
TCalc.prototype.ganttduration = function (MQ, MR, MT) {
    if (!MR) MR = this.Col;
    if (!MT) MT = "d";
    var BL = this.BK(),
    BN = BL.length,
    max = 0;
    var D8 = Get(this.Row, MQ);
    if (!D8) return 0;
    D8 = new Date(D8);
    for (var i = 0; i < BN; i++) {
        var J = BL[i],
        Ck = Get(J, MQ),
        DF = Get(J, MR);
        if (!Ck || !DF) continue;
        Ck = new Date(Ck);
        this.Grid.MO(Ck, MT, DF);
        if (Ck - D8 > max) max = Ck - D8;
    }
    return max / _7Ap[MT];
};
_7m.prototype.MU = function (Ck, Cs) {
    if (Cs == 'm') Ck.setSeconds(0, 0);
    else if (Cs == 'm5') Ck.setMinutes(Math.floor(Ck.getMinutes() / 5) * 5, 0, 0);
    else if (Cs == 'm10') Ck.setMinutes(Math.floor(Ck.getMinutes() / 10) * 10, 0, 0);
    else if (Cs == 'm15') Ck.setMinutes(Math.floor(Ck.getMinutes() / 15) * 15, 0, 0);
    else if (Cs == 'm30') Ck.setMinutes(Math.floor(Ck.getMinutes() / 30) * 30, 0, 0);
    else if (Cs == 'h') Ck.setMinutes(0, 0, 0);
    else if (Cs == 'h2') Ck.setHours(Math.floor(Ck.getHours() / 2) * 2, 0, 0, 0);
    else if (Cs == 'h3') Ck.setHours(Math.floor(Ck.getHours() / 3) * 3, 0, 0, 0);
    else if (Cs == 'h6') Ck.setHours(Math.floor(Ck.getHours() / 6) * 6, 0, 0, 0);
    else if (Cs == 'h8') Ck.setHours(Math.floor(Ck.getHours() / 8) * 8, 0, 0, 0);
    else if (Cs == 'h12') Ck.setHours(Math.floor(Ck.getHours() / 12) * 12, 0, 0, 0);
    else {
        Ck.setHours(0, 0, 0, 0);
        if (Cs == 'w') Ck.setDate(Ck.getDate() - Ck.getDay());
        else if (Cs == 'w1') Ck.setDate(Ck.getDate() - Ck.getDay() + 1);
        else if (Cs == 'M') Ck.setDate(1);
        else if (Cs == 'M3') Ck.setMonth(Math.floor(Ck.getMonth() / 3) * 3, 1);
        else if (Cs == 'M6') Ck.setMonth(Math.floor(Ck.getMonth() / 6) * 6, 1);
        else if (Cs == 'y') Ck.setMonth(0, 1);
    }
};
_7m.prototype.MV = function (Ck, Cs) {
    if (Cs == 'm') Ck.setUTCSeconds(0, 0);
    else if (Cs == 'm5') Ck.setUTCMinutes(Math.floor(Ck.getUTCMinutes() / 5) * 5, 0, 0);
    else if (Cs == 'm10') Ck.setUTCMinutes(Math.floor(Ck.getUTCMinutes() / 10) * 10, 0, 0);
    else if (Cs == 'm15') Ck.setUTCMinutes(Math.floor(Ck.getUTCMinutes() / 15) * 15, 0, 0);
    else if (Cs == 'm30') Ck.setUTCMinutes(Math.floor(Ck.getUTCMinutes() / 30) * 30, 0, 0);
    else if (Cs == 'h') Ck.setUTCMinutes(0, 0, 0);
    else if (Cs == 'h2') Ck.setUTCHours(Math.floor(Ck.getUTCHours() / 2) * 2, 0, 0, 0);
    else if (Cs == 'h3') Ck.setUTCHours(Math.floor(Ck.getUTCHours() / 3) * 3, 0, 0, 0);
    else if (Cs == 'h6') Ck.setUTCHours(Math.floor(Ck.getUTCHours() / 6) * 6, 0, 0, 0);
    else if (Cs == 'h8') Ck.setUTCHours(Math.floor(Ck.getUTCHours() / 8) * 8, 0, 0, 0);
    else if (Cs == 'h12') Ck.setUTCHours(Math.floor(Ck.getUTCHours() / 12) * 12, 0, 0, 0);
    else {
        Ck.setUTCHours(0, 0, 0, 0);
        if (Cs == 'w') Ck.setUTCDate(Ck.getUTCDate() - Ck.getUTCDay());
        else if (Cs == 'w1') Ck.setUTCDate(Ck.getUTCDate() - Ck.getUTCDay() + 1);
        else if (Cs == 'M') Ck.setUTCDate(1);
        else if (Cs == 'M3') Ck.setUTCMonth(Math.floor(Ck.getUTCMonth() / 3) * 3, 1);
        else if (Cs == 'M6') Ck.setUTCMonth(Math.floor(Ck.getUTCMonth() / 6) * 6, 1);
        else if (Cs == 'y') Ck.setUTCMonth(0, 1);
    }
};
_7m.prototype.MW = function (Ck, Cs, BY) {
    if (!BY) BY = 1;
    if (Cs == 'm') Ck.setMinutes(Ck.getMinutes() + BY);
    else if (Cs == 'm5') Ck.setMinutes(Ck.getMinutes() + BY * 5);
    else if (Cs == 'm10') Ck.setMinutes(Ck.getMinutes() + BY * 10);
    else if (Cs == 'm15') Ck.setMinutes(Ck.getMinutes() + BY * 15);
    else if (Cs == 'm30') Ck.setMinutes(Ck.getMinutes() + BY * 30);
    else if (Cs == 'h') Ck.setHours(Ck.getHours() + BY);
    else if (Cs == 'h2') Ck.setHours(Ck.getHours() + BY * 2);
    else if (Cs == 'h3') Ck.setHours(Ck.getHours() + BY * 3);
    else if (Cs == 'h6') Ck.setHours(Ck.getHours() + BY * 6);
    else if (Cs == 'h8') Ck.setHours(Ck.getHours() + BY * 8);
    else if (Cs == 'h12') Ck.setHours(Ck.getHours() + BY * 12);
    else if (Cs == 'd') Ck.setDate(Ck.getDate() + BY);
    else if (Cs == 'w' || Cs == 'w1') Ck.setDate(Ck.getDate() + BY * 7);
    else {
        if (Cs == 'M') Ck.setMonth(Ck.getMonth() + BY);
        else if (Cs == 'M3') Ck.setMonth(Ck.getMonth() + BY * 3);
        else if (Cs == 'M6') Ck.setMonth(Ck.getMonth() + BY * 6);
        else if (Cs == 'y') Ck.setFullYear(Ck.getFullYear() + BY);
    }
};
_7m.prototype.MY = function (Ck, Cs, BY) {
    if (!BY) BY = 1;
    if (Cs == 'm') Ck.setUTCMinutes(Ck.getUTCMinutes() + BY);
    else if (Cs == 'm5') Ck.setUTCMinutes(Ck.getUTCMinutes() + BY * 5);
    else if (Cs == 'm10') Ck.setUTCMinutes(Ck.getUTCMinutes() + BY * 10);
    else if (Cs == 'm15') Ck.setUTCMinutes(Ck.getUTCMinutes() + BY * 15);
    else if (Cs == 'm30') Ck.setUTCMinutes(Ck.getUTCMinutes() + BY * 30);
    else if (Cs == 'h') Ck.setUTCHours(Ck.getUTCHours() + BY);
    else if (Cs == 'h2') Ck.setUTCHours(Ck.getUTCHours() + BY * 2);
    else if (Cs == 'h3') Ck.setUTCHours(Ck.getUTCHours() + BY * 3);
    else if (Cs == 'h6') Ck.setUTCHours(Ck.getUTCHours() + BY * 6);
    else if (Cs == 'h8') Ck.setUTCHours(Ck.getUTCHours() + BY * 8);
    else if (Cs == 'h12') Ck.setUTCHours(Ck.getUTCHours() + BY * 12);
    else if (Cs == 'd') Ck.setUTCDate(Ck.getUTCDate() + BY);
    else if (Cs == 'w' || Cs == 'w1') Ck.setUTCDate(Ck.getUTCDate() + BY * 7);
    else {
        if (Cs == 'M') Ck.setUTCMonth(Ck.getUTCMonth() + BY);
        else if (Cs == 'M3') Ck.setUTCMonth(Ck.getUTCMonth() + BY * 3);
        else if (Cs == 'M6') Ck.setUTCMonth(Ck.getUTCMonth() + BY * 6);
        else if (Cs == 'y') Ck.setUTCFullYear(Ck.getUTCFullYear() + BY);
    }
};
_7m.prototype.MZ = function (Ck) {
    if (!Ck) return null;
    if (Ck - 0 == Ck) return new Date(Ck - 0);
    var Hl = new Date(Ck);
    if (!isNaN(Hl)) return Hl;
    return _7AX(Ck);
};
_7m.prototype.Ma = function (Ck, Cs, MN) {
    Ck = (Ck + "").split(MN.ValueSeparator);
    for (var i = 0; i < Ck.length; i++) {
        var a = Ck[i].split(MN.RangeSeparator);
        a[0] = this.MZ(a[0]);
        if (Cs) this.Mb(a[0], Cs);
        if (!a[1]) a[1] = new Date(a[0]);
        else {
            a[1] = this.MZ(a[1]);
            if (Cs) this.Mb(a[1], Cs);
        };
        if (a[0] > a[1]) {
            var Mc = a[1];
            a[1] = a[0];
            a[0] = Mc;
        }
        this.MO(a[1], Cs ? Cs: "m");
        Ck[i] = a;
    }
    return Ck;
};
_7m.prototype.Md = function (row, BJ, C5, Bj) {
    var Me = BJ + C5,
    BR = row[Me];
    if (BR == null) {
        BR = row.Def[Me];
        if (BR == null) {
            BR = this.Cols[BJ][C5];
            if (BR == null) return Bj;
        }
    }
    if (!this.Cols[BR]) return BR;
    var BZ = row[BR];
    if (BZ != null) return BZ;
    BZ = row.Def[BR];
    if (BZ != null) return BZ;
    return Bj;
};
_7m.prototype.Mf = function (Bx, BJ) {
    if (!BJ) {
        for (var BJ in this.Cols) if (this.Cols[BJ].Type == "Gantt") this.Mf(Bx, BJ);
        return;
    }
    var C = this.Cols[BJ],
    width = C["GanttWidth"],
    MT = C["GanttUnits"],
    Mg = width / _7Ap[MT];
    var BL = new Array,
    Bg = 0,
    KL = this.XHeader,
    Mh = C.Mi,
    Mj = C.Mk,
    options = C["GanttHeaderOptions"],
    MN = this.Lang.Format;
    if (C.Mi == 1e16 || C.Mk == -1) return;
    var KV = " class='" + this.Img.Style + "Gantt";
    for (var i = 1; i <= 5; i++) {
        var Cs = C["GanttHeader" + i],
        BG = C["GanttFormat" + i];
        if (!Cs || !_7Ap[Cs]) continue;
        BL[Bg++] = _7A + KV + "HeaderTable'" + _7B + "<tr>";
        var Ck = new Date(Mh);
        this.Mb(Ck, Cs);
        var Ml = new Date(Mj);
        this.Mb(Ml, Cs);
        var It = 0;
        if (Mh - Ck) {
            var Hl = new Date(Ck);
            this.MO(Ck, Cs);
            It = Math.round(((Ck > Ml ? Mj: Ck) - Mh) * Mg);
            BL[Bg++] = "<td><div style='overflow:hidden;white-space:nowrap;width:" + It + "px;'><div" + KV + "Header" + i + "'>" + (options & 1 ? MN["Cont"] : MN["ContLeft"] + DateToString(Hl, BG) + (Ck > Ml ? MN["ContRight"] : "")) + "</div></div></td>";
            if (Ck > Ml) Ml = Mj;
        }
        while (1) {
            var Hl = new Date(Ck);
            this.MO(Ck, Cs);
            if (Ck > Ml) break;
            var CY = Math.round((Ck - Mh) * Mg);
            BL[Bg++] = "<td><div style='overflow:hidden;white-space:nowrap;width:" + (CY - It) + "px;'><div" + KV + "Header" + i + "'>" + DateToString(Hl, BG) + "</div></div></td>";
            It = CY;
        }
        if (Mj - Ml) {
            BL[Bg++] = "<td><div style='overflow:hidden;white-space:nowrap;width:" + Math.round((Mj - Ml) * Mg) + "px;'><div" + KV + "Header" + i + "'>" + (options & 1 ? MN["Cont"] : DateToString(Hl, BG) + MN["ContRight"]) + "</div></div></td>";
        }
        BL[Bg++] = "</tr>" + _7C;
    }
    KL[BJ] = BL.join("");
    KL.NoEscape = 1;
    if (Bx && KL.r1) {
        var D3 = this.GetSections();
        for (var i = D3[0]; i < D3[1]; i++) KL["r" + i].firstChild.style.height = "1px";
        KL.Height = null;
        this.RefreshCell(KL, BJ);
        this.UpdateRowHeight(KL);
    }
};
_7m.prototype.Mm = function (Bx, BJ) {
    if (!BJ) {
        for (var BJ in this.Cols) if (this.Cols[BJ].Type == "Gantt") this.Mm(Bx, BJ);
        return;
    }
    var C = this.Cols[BJ];
    var width = C["GanttWidth"];
    if (!width) {
        width = 18;
        C["GanttWidth"] = width;
    }
    var MT = C["GanttUnits"];
    if (!MT) {
        MT = 'd';
        C["GanttUnits"] = MT;
    }
    var Mg = width / _7Ap[MT];
    C.Mi = 1e16;
    C.Mk = -1;
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) this.CJ(J, BJ, 0, 1);
    var CY = Math.round((C.Mk - C.Mi) * Mg);
    if (CY < C.MinWidth) CY = C.MinWidth;
    if (CY != C.Width && !C.RelWidth) {
        if (Bx && !this.Loading && !this.Rendering) this.SetWidth(BJ, CY - C.Width, 2);
        else C.Width = CY;
        C.Ci = false;
    }
};
_7m.prototype.CH = function (Bx, BJ, By) {
    if (!this.Gantt) return;
    if (!BJ) {
        for (var BJ in this.Cols) if (this.Cols[BJ].Type == "Gantt") this.CH(Bx, BJ);
        return;
    }
    this.Mm(Bx, BJ);
    this.Mf(Bx, BJ);
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (!By || J.Calculated) this.CJ(J, BJ, 0, 2);
    this.Mn(BJ);
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (!By || J.Calculated) this.CJ(J, BJ, Bx == null || Bx, 3);
};
_7m.prototype.RefreshGantt = _7m.prototype.CH;
_7m.prototype.RefreshGanttDependencies = function (Bx, BJ) {
    if (!this.Gantt) return;
    if (!BJ) {
        for (var BJ in this.Cols) if (this.Cols[BJ].Type == "Gantt") this.RefreshGanttDependencies(Bx, BJ);
        return;
    }
    this.Mn();
    if (Bx == null || Bx) this.Mo();
};
_7m.prototype.CJ = function (row, BJ, Bx, type) {
    if (!row || row.Fixed) return;
    if (!BJ) {
        for (var BJ in this.Cols) if (this.Cols[BJ].Type == "Gantt") this.CJ(row, BJ, Bx, type);
        return;
    }
    var MN = this.Lang.Format,
    E4 = this.Img,
    Df = 0,
    C = this.Cols[BJ];
    var width = C["GanttWidth"],
    MT = C["GanttUnits"],
    Mg = width / _7Ap[MT];
    var MK = C["GanttRound"];
    if (!MK) MK = MT;
    var Mp = C["GanttChartRound"];
    if (!Mp) Mp = 'm';
    var left = C["GanttLeft"],
    right = C["GanttRight"];
    var Mq = this.Md(row, BJ, "GanttStart"),
    Mr = this.Md(row, BJ, "GanttEnd"),
    Ms = this.Md(row, BJ, "GanttDuration");
    var D = Mq + MT + Mr + MK + Ms;
    var D8 = this.Aq[D];
    if (D8) {
        Mq = D8[0];
        Mr = D8[1];
        Mt = D8[2];
    } else {
        Mq = this.MZ(Mq);
        Mr = this.MZ(Mr);
        if (!Mr && Ms) {
            Mr = new Date(Mq);
            if (Ms > 1) this.MO(Mr, MT, Ms - 1);
        }
        if (Mr && Mq > Mr) {
            Mq = null;
            Mr = null;
        }
        if (Mq) this.Mb(Mq, MK);
        if (Mr) {
            this.Mb(Mr, MK);
            this.MO(Mr, MK);
        }
        var Mt = 0;
        if (!Mq && Mr || !Mr && Mq) {
            if (!Mq) Mq = Mr;
            Mr = new Date((Mq - 0) + E4.GanttMilestone / Mg);
            Mt = 1;
        }
        this.Aq[D] = [Mq, Mr, Mt];
    };
    var Mu = this.Md(row, BJ, "GanttFlow");
    if (Mu) {
        var Mv = this.Ge(row, BJ, "GanttFlowRound", MK);
        var Mw = this.Aq[Mu + Mv];
        if (!Mw) {
            Mw = this.Ma(Mu, Mv, MN);
            this.Aq[Mu + Mv] = Mw;
        }
        Mu = Mw;
    }
    var Mx = this.Md(row, BJ, "GanttFlags");
    if (Mx) {
        var Mw = this.Aq[Mx + MK];
        if (!Mw) Mw = this.Ma(Mx, MK, MN);
        Mx = Mw;
    }
    var My = this.Md(row, BJ, "GanttResources");
    var Mh = C.Mi,
    Mj = C.Mk;
    if (!type || type == 1) {
        var Mz = null,
        M0 = null;
        if (Mu) {
            var Mz = Mu[0][0];
            var M0 = Mu[Mu.length - 1][1];
            if (Mz > Mq) Mz = Mq;
            if (M0 < Mr) M0 = Mr;
        } else {
            Mz = Mq;
            M0 = Mr;
            if (!M0 && Mz) M0 = Mz;
            if (!Mz && M0) Mz = M0;
        };
        if (Mx) {
            if (Mx[0][0] < Mz) Mz = Mx[0][0];
            if (Mx[Mx.length - 1][1] > M0) M0 = Mx[Mx.length - 1][1];
        }
        if (My) {
            var Hu = Mr ? Mr: Mq;
            if (Hu) {
                var CY = (My + "").length * E4.GanttResource + E4.GanttEnd;
                if (!right) right = 0;
                CY -= width * (right - 1);
                Hu = new Date(Hu - 0 + CY / width * _7Ap[MT]);
                if (Hu > M0) M0 = Hu;
            }
        }
        if (Mz) {
            var D = "F1" + MK + left + Mp + (Mz - 0);
            var Kf = this.Aq[D];
            if (!Kf) {
                Mz = new Date(Mz);
                if (left) {
                    this.Mb(Mz, MK);
                    Mz.setMilliseconds( - 1);
                    this.Mb(Mz, MK);
                }
                this.Mb(Mz, Mp, 0);
                this.Aq[D] = Mz;
            } else Mz = Kf;
            if (!type && row[BJ + "F1"] != Mz) Df = 1;
            row[BJ + "F1"] = Mz;
            if (!Mh || Mh > Mz) {
                Mh = Mz;
                var min = C["GanttMin"];
                if (min && typeof(min) == "string") {
                    min = this.MZ(min) - 0;
                    C["GanttMin"] = min;
                }
                if (min && Mh < min) Mh = new Date(min);
                if (C.Mi > Mh) {
                    C.Mi = Mh;
                    Df = 2;
                }
            }
        } else row[BJ + "F1"] = null;
        if (M0) {
            var D = "F2" + MK + right + Mp + (M0 - 0);
            var Kf = this.Aq[D];
            if (!Kf) {
                M0 = new Date(M0);
                if (right) this.MO(M0, MK, right);
                this.Mb(M0, MK);
                M0.setMilliseconds( - 1);
                this.Mb(M0, Mp);
                this.MO(M0, Mp);
                this.Aq[D] = M0;
            } else M0 = Kf;
            if (!type && row[BJ + "F2"] != M0 && !Df) Df = 1;
            row[BJ + "F2"] = M0;
            if (!Mj || Mj < M0) {
                Mj = M0;
                var max = C["GanttMax"];
                if (max && typeof(max) == "string") {
                    max = this.MZ(max) - 0;
                    C["GanttMax"] = max;
                }
                if (max && Mj > max) Mj = new Date(max);
                if (C.Mk < Mj) {
                    C.Mk = Mj;
                    Df = 2;
                }
            }
        } else row[BJ + "F2"] = null;
        if (type == 1) return;
    }
    if (!type || type == 2) {
        var HT, Hu;
        if (Mq && Mr) {
            HT = Mq - Mh;
            Hu = Mr - Mh;
        } else {
            HT = (Mj - Mh) / 2;
            Hu = HT;
        };
        HT = Math.ceil(HT * Mg),
        Hu = Math.ceil(Hu * Mg);
        var M1 = this.Md(row, BJ, "GanttDependencies");
        if (M1 && this.H3 != 0) this.H3 = 1;
        var Ca = this.Md(row, BJ, "GanttDependencyTypes");
        if (!type && this.H3 && (row[BJ + "Gdate1"] != HT || row[BJ + "Gdate2"] != Hu || row[BJ + "Gdependency"] != M1 || row[BJ + "Gdeptype"] != Ca)) {
            row[BJ + "Gdate1"] = HT;
            row[BJ + "Gdate2"] = Hu;
            row[BJ + "Gdependency"] = M1;
            row[BJ + "Gdeptype"] = Ca;
            this.Mn(BJ);
            if (Bx) this.Mo(BJ);
        } else {
            row[BJ + "Gdate1"] = HT;
            row[BJ + "Gdate2"] = Hu;
            row[BJ + "Gdependency"] = M1;
            row[BJ + "Gdeptype"] = Ca;
        };
        if (type == 2) return;
    }
    if (!Mh || !Mj) return;
    if (!type && Df) {
        if (Df == 2) {
            if (this.RowCount < this.SynchroCount && !this.Paging || !Bx) this.CH(Bx, BJ);
            else {
                var T = this;
                this.ShowMessage(this.GetText("GanttUpdate"));
                setTimeout(function () {
                    T.CH(Bx, BJ);
                    T.HideMessage();
                    if (Bx) T.SetScrollBars();
                },
                10);
            };
            return;
        }
        if (this.M2(Bx, BJ)) return;
    }
    var M3 = this.Ge(row, BJ, "GanttClass", "Gantt"),
    KV = " class='" + E4.Style + M3;
    var Bh = "",
    D4 = 0,
    J0 = this.HV;
    var options = this.Ge(row, BJ, "GanttOptions");
    if (_7R) {
        var BL = new Array(),
        Bg = 0;
    }
    var background = this.Md(row, BJ, "GanttBackground");
    if (background) {
        var M4 = this.Ge(row, BJ, "GanttBackgroundRepeat") + "";
        var D = "gantt" + background + M4 + MT + (Mh - 0) + M3 + (Mj - 0);
        if (!this.Aq[D]) {
            background = this.Ma(background, null, MN);
            var EP = M4.search(/[\,\;\|]/);
            if (EP >= 0) EP = (M4 + "").split(M4.charAt(EP));
            else EP = [M4];
            var H = new Array,
            Bc = 0;
            for (var i = 0; i < background.length; i++) {
                var Cs = EP[i],
                Ck = background[i][0],
                Hu = background[i][1],
                It = Math.round((Hu - Ck) * Mg);
                if (!It) It = 1;
                if (!Ck) continue;
                if (Cs && _7Ap[Cs]) {
                    var Hl = new Date(Ck);
                    this.Mb(Hl, Cs);
                    var M5 = new Date(Mh);
                    this.Mb(M5, Cs);
                    M5 = new Date(M5 - 1);
                    this.Mb(M5, Cs);
                    Ck = new Date(M5 - (Hl - Ck));
                    while (Ck < Mj) {
                        var CY = Math.round((Ck - Mh) * Mg);
                        D4++;
                        H[Bc++] = "|" + CY + "|0|" + It + "||<div" + KV + "Back" + (i + 1) + "'>" + _7D + "</div>";
                        this.MO(Ck, Cs);
                    }
                } else if (Hu > Mh && Ck < Mj) {
                    if (! (Ck - Hu)) {
                        this.MO(Hu, MT);
                        It = Math.round((Hu - Ck) * Mg);
                    }
                    CY = Math.round((Ck - Mh) * Mg);
                    H[Bc++] = "|" + CY + "|0|" + It + "||<div" + KV + "Back" + (i + 1) + "'>" + _7D + "</div>";
                    D4++;
                }
            }
            this.Aq[D] = H.join("");
            this.Aq[D + "Cnt"] = D4;
        }
        if (_7R) BL[Bg++] = this.Aq[D];
        else Bh += this.Aq[D];
        row[BJ + "Gback"] = D4;
        D4 = this.Aq[D + "Cnt"];
    }
    if (Mt) {
        row[BJ + "Gdate"] = D4++;
        row[BJ + "Gdatecnt"] = 0;
        var CY = (Mq - Mh) * Mg;
        if (_7R) BL[Bg++] = ["|", CY, "|0|", E4.GanttMilestone, "||", this.HW(15, 360, E4.GanttMilestone)].join("");
        else Bh += "|" + CY + "|0|" + E4.GanttMilestone + "||" + this.HW(15, 360, E4.GanttMilestone);
    } else if (Mq) {
        row[BJ + "Gdate"] = D4;
        var complete = this.Md(row, BJ, "GanttComplete");
        var CY = (Mq - Mh) * Mg;
        var It = Math.ceil((Mr - Mq) * Mg);
        if (!isNaN(complete)) {
            if (complete < 0) complete = 0;
            if (complete > 100) complete = 100;
            var M6 = complete == 100 ? It: Math.floor(It / 100 * complete);
            if (M6 > 0 && M6 < 3) M6 = 3;
            var Cv = It - M6;
            if (Cv > 0 && Cv < 3 && It >= 6) {
                Cv = 3;
                M6 = It - Cv;
            }
            if (_7R) {
                if (M6) {
                    D4++;
                    BL[Bg++] = ["|", (CY + (J0 ? Cv: 0)), "|0|", M6, "||<div", KV, (complete == 100 ? 100 : 90), "Out'><div", KV, (complete == 100 ? 100 : 90), "In'>", _7D, "</div></div>"].join("");
                }
                if (Cv) {
                    D4++;
                    BL[Bg++] = ["|", (CY + (J0 ? 0 : M6)), "|0|", Cv, "||<div", KV, (complete == 0 ? 0 : 10), "Out'><div", KV, (complete == 0 ? 0 : 10), "In'>", _7D, "</div></div>"].join("");
                }
            } else {
                if (M6) {
                    D4++;
                    Bh += "|" + (CY + (J0 ? Cv: 0)) + "|0|" + M6 + "||<div" + KV + (complete == 100 ? 100 : 90) + "Out'><div" + KV + (complete == 100 ? 100 : 90) + "In'>" + _7D + "</div></div>";
                }
                if (Cv) {
                    D4++;
                    Bh += "|" + (CY + (J0 ? 0 : M6)) + "|0|" + Cv + "||<div" + KV + (complete == 0 ? 0 : 10) + "Out'><div" + KV + (complete == 0 ? 0 : 10) + "In'>" + _7D + "</div></div>";
                }
            }
        } else {
            if (_7R) BL[Bg++] = ["|", CY, "|0|", It, "||<div", KV, "NullOut'><div", KV, "NullIn'>", _7D, "</div></div>"].join("");
            else Bh += "|" + CY + "|0|" + It + "||<div" + KV + "NullOut'><div" + KV + "NullIn'>" + _7D + "</div></div>";
            D4++;
        };
        if (options & 2) {
            if (_7R) BL[Bg++] = ["|", (CY + (J0 ? It - 10 : -10)), "|0|", 20, "||", this.HW(14, 460, 20), "|", (CY + (J0 ? -10 : It - 10)), "|0|", 20, "||", this.HW(14, 490, 20)].join("");
            else Bh += "|" + (CY + (J0 ? It - 10 : -10)) + "|0|" + 20 + "||" + this.HW(14, 460, 20) + "|" + (CY + (J0 ? -10 : It - 10)) + "|0|" + 20 + "||" + this.HW(14, 490, 20);
            D4 += 2;
        }
        row[BJ + "Gdatecnt"] = D4 - row[BJ + "Gdate"];
    } else {
        row[BJ + "Gdate"] = null;
        row[BJ + "Gdatecnt"] = null;
    };
    if (Mu) {
        row[BJ + "Gflow"] = D4;
        D4 += Mu.length;
        row[BJ + "Gflowcnt"] = Mu.length;
        for (var i = 0; i < Mu.length; i++) {
            var CY = (Mu[i][0] - Mh) * Mg;
            var It = Math.ceil((Mu[i][1] - Mu[i][0]) * Mg);
            if (_7R) BL[Bg++] = ["|", CY, "|0|", It, "||<div", KV, "FlowOut'><div", KV, "FlowIn'>", _7D, "</div></div>"].join("");
            else Bh += "|" + CY + "|0|" + It + "||<div" + KV + "FlowOut'><div" + KV + "FlowIn'>" + _7D + "</div></div>";
        }
    } else row[BJ + "Gflow"] = null;
    var M7 = this.Md(row, BJ, "GanttMark");
    if (M7) {
        var M8 = this.Ge(row, BJ, "GanttMarkRound", "m");
        var D = "gantt" + M7 + M8 + MT + (Mh - 0) + M3 + (Mj - 0);
        if (!this.Aq[D]) {
            var IY = "",
            BQ = 0;
            M7 = this.Ma(M7, M8, MN);
            for (var i = 0; i < M7.length; i++) {
                if (M7[i][0] < Mh || M7[i][1] > Mj) continue;
                var CY = (M7[i][0] - Mh) * Mg;
                var It = Math.ceil((M7[i][1] - M7[i][0]) * Mg);
                IY += "|" + CY + "|0|" + It + "||<div" + KV + "Mark'>" + _7D + "</div>";
                BQ++;
            }
            this.Aq[D] = IY;
            this.Aq[D + "Cnt"] = BQ;
        }
        if (_7R) BL[Bg++] = this.Aq[D];
        else Bh += this.Aq[D];
        row[BJ + "Gmark"] = D4;
        D4 += this.Aq[D + "Cnt"];
    }
    if (_7R) BL[Bg++] = "|0|0|||";
    else Bh += "|0|0|||";
    row[BJ + "Gdep"] = D4++;
    row[BJ + "Gdeprep"] = null;
    if (My && Mr) {
        row[BJ + "Gres"] = D4++;
        if (_7R) BL[Bg++] = ["|", ((Mr - Mh) * Mg + E4.GanttEnd), "|0|||<div", KV, "Resource'>", this.Escape(My), "</div>"].join("");
        else Bh += "|" + ((Mr - Mh) * Mg + E4.GanttEnd) + "|0|||<div" + KV + "Resource'>" + this.Escape(My) + "</div>";
    }
    if (Mx) {
        var M9 = this.Md(row, BJ, "GanttFlagTexts");
        row[BJ + "Gflag"] = D4;
        var NA = M9 ? (M9 + "").split(MN.ValueSeparator) : [],
        NB = 0;
        var Le = E4.GanttFlag;
        Le = Math.ceil((width - Le) / 2);
        for (var i = 0; i < Mx.length; i++) {
            do {
                if (_7R) BL[Bg++] = ["|", Math.floor((Mx[i][0] - Mh) * Mg + Le), "|0|", E4.GanttFlag, "||", this.HW(14, 440, E4.GanttFlag, NA[NB] ? " title='" + this.Escape(NA[NB++]) + "'": "")].join("");
                else Bh += "|" + Math.floor((Mx[i][0] - Mh) * Mg + Le) + "|0|" + E4.GanttFlag + "||" + this.HW(14, 440, E4.GanttFlag, NA[NB] ? " title='" + this.Escape(NA[NB++]) + "'": "");
                this.MO(Mx[i][0], MT);
                D4++;
            } while (Mx[i][0] < Mx[i][1]);
        }
        row[BJ + "Gflagcnt"] = D4 - row[BJ + "Gflag"];
    } else row[BJ + "Gflag"] = null;
    row[BJ + "Gantt"] = this.Img.Style + M3;
    row[BJ + "Gdw"] = Mg;
    row[BJ] = _7R ? BL.join("") : Bh;
    if (Bx) this.RefreshCell(row, BJ);
    return Bh;
};
_7m.prototype.Mn = function (BJ) {
    if (!BJ) {
        for (var BY in this.Cols) if (this.Cols[BY].Type == "Gantt") this.Mn(BY);
        return;
    }
    var NC = this.Img.GanttStart,
    Iy = this.Img.GanttEnd;
    var Bp = this.Lang.Format.ValueSeparator,
    ED = this.Img.Style + "GanttDep",
    margin = this.HV ? "margin-right:": "margin-left:";
    var Jz = this.ND,
    NE = this.NF;
    if (Jz == null) {
        var Ck = document.createElement("div");
        Ck.style.position = "absolute";
        Ck.style.top = "0px";
        Ck.style.left = "0px";
        Ck.style.visibility = "hidden";
        Ck.className = ED + "Vert";
        this.MainTag.appendChild(Ck);
        var Bh = GetStyle(Ck);
        var FW = parseInt(Bh.borderLeftWidth);
        if (!FW) FW = 0;
        var NG = parseInt(Bh.borderRightWidth);
        if (!NG) NG = 0;
        var CY = parseInt(Bh.width);
        if (!CY) CY = 0;
        Jz = CY ? FW: 0;
        NE = _7S ? "width:" + (CY + FW + NG) + "px;": "";
        this.ND = Jz;
        this.NF = NE;
        this.MainTag.removeChild(Ck);
    }
    if (!this.NH) {
        var NI = _7D;
        if (_7J || _7H) NI = "<div style='width:1px;height:1px;overflow:hidden;'></div>";
        this.NH = ["<div class='" + ED + "HorzOut' style='" + margin, "<div class='" + ED + "Icon' style='" + margin, "<div class='" + ED + "Vert' style='" + NE + margin, "<div class='" + ED + "VertBottom' style='" + NE + margin, "<div class='" + ED + "VertTop' style='" + NE + margin, "<div class='" + ED + "VertBottom3' style='" + NE + margin, "<div class='" + ED + "VertBottom2' style='" + NE + margin, "<div class='" + ED + "VertTop2' style='" + NE + margin, "px;'>" + _7D + "</div>", "px;width:", "px;'><div class='" + ED + "HorzIn'>" + NI + "</div></div>", "px;'><div class='" + ED + "HorzTop'>" + NI + "</div></div>", "px;'><div class='" + ED + "HorzBottom'>" + NI + "</div></div>", "px;'>" + this.HW(14, this.HV ? 400 : 420, Iy + Jz + 1) + "</div>", "px;'>" + this.HW(14, this.HV ? 420 : 400, Iy + Jz) + "</div>", "px;'>" + this.HW(14, this.HV ? 420 : 400, Iy + Jz + 1) + "</div>", "px;'>" + this.HW(14, this.HV ? 401 : 420, Iy + Jz + (this.HV && _7R ? -2 : 1)) + "</div>", "<div class='" + ED + "First'>" + _7D + "</div>"];
    }
    var E4 = {},
    R = [],
    D = [],
    T = [],
    F = [],
    NJ = BJ + "Gdependency",
    NK = BJ + "Gdeptype";
    for (var J = this.GetFirst(), BN = 0; J; J = this.GetNext(J)) if (J.Visible) {
        E4[J.id] = BN;
        R[BN++] = J;
    }
    for (var Ce = 0; Ce < BN; Ce++) {
        var J = R[Ce],
        a = J[NJ];
        if (a) {
            a = a.split ? a.split(Bp) : [a];
            var Cs = J[NK],
            Bc = [];
            T[Ce] = Cs ? (Cs + "").toLowerCase().split(Bp) : [];
            for (var i = 0; i < a.length; i++) {
                var NL = E4[a[i]];
                if (NL != null) {
                    Bc[Bc.length] = NL;
                    if (!F[NL]) F[NL] = [Ce];
                    else F[NL][F[NL].length] = Ce;
                }
            }
            D[Ce] = Bc;
        }
    }
    delete E4;
    var NM = BJ + "Gdepss",
    NN = BJ + "Gdepsf",
    NO = BJ + "Gdepfs",
    NP = BJ + "Gdepff";
    for (var Ce = 0; Ce < BN; Ce++) {
        var Bg = D[Ce],
        Bh = "",
        BG = "",
        J = R[Ce];
        if (Bg) for (var i = 0; i < Bg.length; i++) {
            if (T[Ce][i] && T[Ce][i].charAt(0) == 's') Bh += (Bh ? Bp: "") + R[Bg[i]].id;
            else BG += (BG ? Bp: "") + R[Bg[i]].id;
        }
        J[NM] = Bh;
        J[NN] = BG;
        var Bg = F[Ce],
        Bh = "",
        BG = "";
        if (Bg) for (var i = 0; i < Bg.length; i++) {
            var B3 = D[Bg[i]];
            if (B3) for (var j = 0; j < B3.length; j++) if (B3[j] == Ce) break;
            if (T[Bg[i]][j] && T[Bg[i]][j].charAt(1) != 's') BG += (BG ? Bp: "") + R[Bg[i]].id;
            else Bh += (Bh ? Bp: "") + R[Bg[i]].id;
        }
        J[NO] = Bh;
        J[NP] = BG;
    }
    var BL = {};
    var Ch = [];
    var NQ = BJ + "Gdate1",
    NR = BJ + "Gdate2",
    NS = BJ + "Gdepdata",
    NT = BJ + "Gdeprep",
    NU = [2, 3, 4, 2],
    NV = [2, 6, 7, 2];
    var NW = 0,
    NX = this.Cols[BJ]["GanttMaxDependencies"];
    if (NX == null) NX = 100;
    for (var Ce = 0; Ce < BN; Ce++) {
        var J = R[Ce],
        NY = [],
        F6 = [],
        IU,
        NZ = 0;
        var a = BL[Ce],
        H = [],
        Bc = 0;
        if (a) {
            for (var i = 0; i < a.length; i += 2) {
                var CY = a[i] - Jz;
                for (var j = 0; j < Ch.length; j++) if (Ch[j] == CY) {
                    Ch.splice(j, 1);
                    NW = Ch.length;
                    break;
                }
            }
        }
        var Na = Ch.join("|");
        var B3 = D[Ce],
        Nb = 0,
        Nc = 0,
        Ca;
        if (B3) {
            var M6 = J[NQ] - NC,
            Cv = J[NR] + NC,
            CY;
            for (var i = 0; i < B3.length; i++) {
                var Nd = B3[i];
                if (!Nd) continue;
                if (!R[Nd]) continue;
                var Bg = F[Nd];
                if (Bg) for (var j = 0; j < Bg.length; j++) if (Bg[j] == Ce) {
                    Bg[j] = null;
                    break;
                }
                if (T[Ce][i] && T[Ce][i].charAt(0) == 's') {
                    Nb |= 1;
                    CY = M6;
                } else {
                    Nc |= 1;
                    CY = Cv;
                };
                if (T[Ce][i] && T[Ce][i].charAt(1) != 's') Ca = 2;
                else Ca = 0;
                if (BL[Nd]) {
                    BL[Nd][BL[Nd].length] = CY;
                    BL[Nd][BL[Nd].length] = Ca;
                } else BL[Nd] = [CY, Ca];
                Ch[NW++] = CY - Jz;
                if (NW > NX) NW = 0;
            }
        }
        if (a) {
            for (var i = 1; i < a.length; i += 2) {
                if (a[i] == 1) Nb |= 2;
                else if (a[i] == 3) Nc |= 2;
            }
        }
        if (Nb) {
            var CY = J[NQ];
            F6.push(0, CY - NC, NC, 10);
            NY[NZ++] = NU[Nb];
            NY[NZ++] = CY - NC - Jz;
        }
        if (Nc) {
            var CY = J[NR];
            F6.push(0, CY, NC + Jz, 10);
            NY[NZ++] = NU[Nc];
            NY[NZ++] = CY + NC - Jz;
        }
        var Bg = F[Ce];
        if (Bg || a) {
            var min = 1e6,
            max = 0,
            Ne = 1e6,
            Nf = 0,
            Ng = 1e6,
            Nh = 0;
            var M6 = J[NQ] - Iy,
            Cv = J[NR] + Iy;
        }
        if (Bg) {
            for (var i = 0; i < Bg.length; i++) {
                var Nd = Bg[i];
                if (!Nd) continue;
                var B3 = D[Nd];
                if (B3) for (var j = 0; j < B3.length; j++) if (B3[j] == Ce) {
                    B3[j] = null;
                    break;
                }
                var F3 = R[Nd],
                CY,
                Ca;
                if (T[Nd][j] && T[Nd][j].charAt(0) == 's') {
                    CY = F3[NQ] - NC;
                    Ca = 1;
                } else {
                    CY = F3[NR] + NC;
                    Ca = 3;
                };
                if (T[Nd][j] && T[Nd][j].charAt(1) != 's') {
                    if (CY >= Cv) {
                        NY[NZ++] = 3;
                        NY[NZ++] = CY - Jz;
                        if (max < CY) max = CY;
                    } else {
                        if (Ng > CY) Ng = CY;
                    }
                } else {
                    if (CY <= M6) {
                        NY[NZ++] = 3;
                        NY[NZ++] = CY - Jz;
                        if (min > CY) min = CY;
                    } else {
                        if (Nh < CY) Nh = CY;
                    }
                };
                if (BL[Nd]) {
                    BL[Nd][BL[Nd].length] = CY;
                    BL[Nd][BL[Nd].length] = Ca;
                } else BL[Nd] = [CY, Ca];
                Ch[NW++] = CY - Jz;
                if (NW > NX) NW = 0;
            }
        }
        if (a) {
            for (var i = 0; i < a.length; i += 2) {
                var CY = a[i];
                if (a[i + 1] == 0) {
                    if (CY <= M6) {
                        NY[NZ++] = 4;
                        NY[NZ++] = CY - Jz;
                        if (min > CY) min = CY;
                    } else {
                        if (Nf < CY) Nf = CY;
                    }
                } else if (a[i + 1] == 2) {
                    if (CY >= Cv) {
                        NY[NZ++] = 4;
                        NY[NZ++] = CY - Jz;
                        if (max < CY) max = CY;
                    } else {
                        if (Ne > CY) Ne = CY;
                    }
                }
            }
        }
        if (Bg || a) {
            var Nb = 0,
            Nc = 0;
            if (min != 1e6) {
                F6.push(1, M6 - Jz - 1, Iy + Jz + 1, 13, 0, min, M6 - min + Jz, 10);
            }
            if (max) {
                F6.push(1, Cv - Iy, Iy + Jz, 14, 0, Cv, max - Cv + Jz, 10);
            }
            if (Ne != 1e6) {
                F6.push(0, Ne, Cv - Ne + Jz, 11);
                Nc |= 2;
            }
            if (Ng != 1e6) {
                F6.push(0, Ng, Cv - Ng + Jz, 12);
                NY[NZ++] = 5;
                NY[NZ++] = Ng - Jz;
                Nc |= 1;
            }
            if (Nf) {
                F6.push(0, M6, Nf - M6 + Jz, 11);
                Nb |= 2;
            }
            if (Nh) {
                F6.push(0, M6, Nh - M6 + Jz, 12);
                NY[NZ++] = 5;
                NY[NZ++] = Nh - Jz;
                Nb |= 1;
            }
            if (Nb) {
                NY[NZ++] = NV[Nb];
                NY[NZ++] = M6 - Jz;
                F6.push(1, M6 - Jz - 1, Iy + Jz + 1, 16);
            }
            if (Nc) {
                NY[NZ++] = NV[Nc];
                NY[NZ++] = Cv - Jz;
                F6.push(1, Cv - Iy, Iy + Jz + 1, 15);
            }
        }
        var Ni = F6.length,
        Nj = NZ++;
        var P = Ni || Nj ? Ni + "|" + Nj + "|" + (Ni ? F6.join("|") : "") + (Ni && Nj ? "|": "") + (Nj ? NY.join("|") : "") + (Na ? "|" + Na: "") : (Na ? "0|0|" + Na: "");
        var BR = J[NS];
        if (!BR) BR = "";
        if (BR != P) {
            J[NS] = P;
            J[NT] = 1;
        }
    }
    this.Nk = 0;
};
_7m.prototype.Nl = function (row, BJ) {
    var D8 = row[BJ + "Gdepdata"],
    BL = [],
    Bg = 0,
    C = this.NH;
    if (D8) {
        D8 = D8.split('|');
        var Nm = D8[0] - 0 + 2,
        Nn = D8[1] - 0 + Nm;
        BL[Bg++] = "";
        for (var i = 2; i < Nm; i += 4) {
            BL[Bg++] = C[D8[i]];
            BL[Bg++] = D8[i + 1];
            BL[Bg++] = C[9];
            BL[Bg++] = D8[i + 2];
            BL[Bg++] = C[D8[i + 3]];
        }
        for (; i < Nn; i += 2) {
            BL[Bg++] = C[D8[i]];
            BL[Bg++] = D8[i + 1];
            BL[Bg++] = C[8];
        }
        for (; D8[i]; i++) {
            BL[Bg++] = C[2];
            BL[Bg++] = D8[i];
            BL[Bg++] = C[8];
        }
        if (Bg > 1) BL[0] = C[17];
    }
    return BL.join("");
};
_7m.prototype.M2 = function (Bx, BJ) {
    if (!BJ) {
        for (var BY in this.Cols) if (this.Cols[BY].Type == "Gantt") this.M2(Bx, BY);
        return;
    }
    var GB = null,
    GD = null,
    GA = false;
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
        var Mz = J[BJ + "F1"],
        M0 = J[BJ + "F2"];
        if (Mz && (Mz < GB || !GB)) GB = Mz;
        if (M0 && M0 > GD) GD = M0;
    }
    var C = this.Cols[BJ];
    if (C.Mi - GB) {
        C.Mi = new Date(GB);
        GA = true;
    }
    if (C.Mk - GD) {
        C.Mk = new Date(GD);
        GA = true;
    }
    if (GA) {
        if (this.RowCount < this.SynchroCount && !this.Paging || !Bx) this.CH(Bx);
        else {
            var T = this;
            this.ShowMessage(this.GetText("GanttUpdate"));
            setTimeout(function () {
                T.CH(Bx);
                T.HideMessage();
                if (Bx) T.SetScrollBars();
            },
            10);
        }
    }
    return GA;
};
_7m.prototype.Mo = function (BJ, page) {
    if (this.Rendering || this.Loading) return;
    if (!BJ) {
        for (var BY in this.Cols) if (this.Cols[BY].Type == "Gantt") this.Mo(BY, page);
        return;
    }
    if (!page) {
        for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) this.Mo(BJ, Bg);
        return;
    }
    for (var J = page.firstChild; J; J = J.nextSibling) {
        if (J[BJ + "Gdeprep"]) {
            var IU = this.GetCell(J, BJ, 1);
            if (IU) IU = IU.firstChild;
            var M1 = J[BJ + "Gdep"];
            if (IU && M1 != null) {
                IU.childNodes[J[BJ + "Gdep"]].innerHTML = this.Nl(J, BJ);
                var No = this.Np;
                if (No == null) {
                    No = _7Ai(GetStyle(this.GetCell(J, BJ)));
                    this.AM = No;
                }
                var KL = IU.parentNode.parentNode.offsetHeight - No;
                IU.style.height = KL + "px";
                J[BJ + "Gdeprep"] = null;
            }
        }
        if (J.firstChild) this.Mo(BJ, J);
    }
};
_7m.prototype.GetGanttXY = function (row, BJ, P, Y) {
    if (typeof(row) == "number") {
        var Click = this.GetClick({
            clientX: row,
            clientY: BJ
        });
        row = Click.Row;
        BJ = Click.Col;
        P = Click.P;
        Y = Click.Y;
    }
    if (!row || !BJ || !row[BJ + "Gantt"]) return null;
    var IU = this.GetCell(row, BJ, 1),
    BL = {};
    if (!IU) return null;
    IU = IU.firstChild;
    BL.J6 = P;
    BL.J7 = Y;
    var D8 = row[BJ + "Gdate"];
    if (D8 != null) {
        D8 = IU.childNodes[D8];
        if (!D8) return null;
        var Ck = D8.firstChild;
        var y = Y - D8.offsetTop - Ck.offsetTop;
        var BQ = row[BJ + "Gdatecnt"];
        if (y >= 0 && y < Ck.offsetHeight || Y == null) {
            if (this.H3) {
                if (P < D8.offsetLeft) {
                    var Bh = this.HV ? 'f': 's',
                    x = D8.offsetLeft - P;
                    if (x <= this.Img.GanttStart + 2) {
                        var a = row[BJ + "Gdeps" + Bh];
                        if (a) BL.DependencyStartLeft = a;
                    }
                    if (x <= this.Img.GanttEnd + 2) {
                        var a = row[BJ + "Gdepf" + Bh];
                        if (a) BL.DependencyEndLeft = a;
                    }
                }
                var D = BQ == 2 || BQ == 4 ? D8.nextSibling: D8;
                if (P >= D.offsetLeft + D.offsetWidth) {
                    var BG = this.HV ? 's': 'f',
                    x = P - D.offsetLeft - D.offsetWidth;
                    if (x <= this.Img.GanttStart + 2) {
                        var a = row[BJ + "Gdeps" + BG];
                        if (a) BL.DependencyStartRight = a;
                    }
                    if (x <= this.Img.GanttEnd + 2) {
                        var a = row[BJ + "Gdepf" + BG];
                        if (a) BL.DependencyEndRight = a;
                    }
                }
            }
            if (P >= D8.offsetLeft && P < D8.offsetLeft + D8.offsetWidth) {
                BL.Main = Ck;
                BL.MainX = P - D8.offsetLeft;
                if (BQ == 2 || BQ == 4) {
                    BL.Main2 = D8.nextSibling.firstChild;
                    BL.MainPos = 1;
                } else BL.MainPos = 0;
            } else if (BQ == 2 || BQ == 4) {
                D8 = D8.nextSibling;
                if (D8 && P >= D8.offsetLeft && P < D8.offsetLeft + D8.offsetWidth) {
                    BL.Main = D8.firstChild;
                    BL.MainX = P - D8.offsetLeft;
                    BL.Main2 = D8.previousSibling.firstChild;
                    BL.MainPos = 2;
                }
            }
        }
        if (BL.Main) {
            var BN = row[BJ + "Gantt"].length;
            var M = BL.Main;
            M = M.className.substring(BN, M.className.length - 3);
            BL.MainType = BQ ? (M - 0 == M ? M: null) : "Milestone";
        }
    }
    var CE = row[BJ + "Gflow"];
    if (CE != null) {
        CE = IU.childNodes[CE];
        var BQ = row[BJ + "Gflowcnt"];
        for (var i = 0; i < BQ && CE; i++) {
            var y = Y - CE.offsetTop - CE.firstChild.offsetTop;
            if ((Y == null || y >= 0 && y < CE.firstChild.offsetHeight) && P >= CE.offsetLeft && P < CE.offsetLeft + CE.offsetWidth) {
                BL.Flow = CE.firstChild;
                BL.FlowIndex = i;
                BL.FlowX = P - CE.offsetLeft;
                break;
            }
            CE = CE.nextSibling;
        }
    }
    var CE = row[BJ + "Gflag"];
    if (CE != null) {
        CE = IU.childNodes[CE];
        var BQ = row[BJ + "Gflagcnt"];
        for (var i = 0; i < BQ && CE; i++) {
            if (Y >= 0 && Y < CE.offsetHeight && P >= CE.offsetLeft && P < CE.offsetLeft + CE.offsetWidth) {
                BL.Nq = CE;
                BL.Nr = i;
                BL.Ns = P - CE.offsetLeft;
                break;
            }
            CE = CE.nextSibling;
        }
    }
    return BL;
};
_7m.prototype.KD = function (row, BJ, P, Y) {
    if (Grids.Az && Grids.Az.Row == row) return;
    var MN = this.Nt,
    V, options = this.Ge(row, BJ, "GanttOptions");
    var BL = options & 17 ? this.GetGanttXY(row, BJ, P, Y) : null;
    if (BL) {
        BL.Row = row;
        BL.Col = BJ;
        V = BL.Nq;
        if (!V) V = BL.Flow;
        if (!V) V = BL.Main;
        BL.V = V;
        BL.Nu = options;
    }
    if (MN && MN.V && MN.V != V && MN.Nu & 1) {
        var Hb = MN.Row[MN.Col + "Gantt"];
        if (MN.Nq && MN.Nu & 64)(_7V ? MN.Nq.firstChild.firstChild: MN.Nq.firstChild).style.backgroundPosition = "-440px -448px";
        else if (MN.Flow && MN.Nu & 32) MN.Flow.className = Hb + "FlowOut";
        else if (MN.Main && MN.Nu & 144) {
            if (MN.MainType == "Milestone")(_7V ? MN.Main.firstChild: MN.Main).style.backgroundPosition = "-360px -480px";
            else {
                MN.Main.className = Hb + (MN.MainType != null ? MN.MainType: "Null") + "Out";
                if (MN.Main2) MN.Main2.className = Hb + (100 - MN.MainType) + "Out";
            }
        }
    }
    if (BL && V && (!MN || MN.V != V) && options & 1) {
        var Hb = row[BJ + "Gantt"];
        if (BL.Nq && options & 64)(_7V ? BL.Nq.firstChild.firstChild: BL.Nq.firstChild).style.backgroundPosition = "-380px -448px";
        else if (BL.Flow && options & 32) BL.Flow.className = Hb + "FlowHov";
        else if (BL.Main && options & 144) {
            if (BL.MainType == "Milestone")(_7V ? BL.Main.firstChild: BL.Main).style.backgroundPosition = "-380px -480px";
            else {
                BL.Main.className = Hb + (BL.MainType != null ? BL.MainType: "Null") + "Hov";
                if (BL.Main2) BL.Main2.className = Hb + (100 - BL.MainType) + "Hov";
            }
        }
    }
    if (BL && options & 48 && !Grids.Az) {
        var Nv = "",
        max = this.Mouse.EdgeSize;
        if (V && (V == BL.Main && options & 16 && (BL.MainX <= max && BL.MainPos != 2 || BL.MainX >= BL.Main.offsetWidth - max && BL.MainPos != 1) && BL.MainType != "Milestone" || V == BL.Flow && options & 32 && (BL.FlowX <= max || BL.FlowX >= BL.Flow.offsetWidth - max))) Nv = "e-resize";
        if (!MN || MN.Nw != Nv || row != this.AT || BJ != this.AU) this.GetCell(row, BJ, 1).style.cursor = Nv;
        BL.Nw = Nv;
    }
    this.Nt = BL;
};
_7m.prototype.Nx = function (row, BJ, C5) {
    var Me = BJ + "Gantt" + C5,
    BR = row[Me];
    if (BR == null) {
        BR = row.Def[Me];
        if (BR == null) {
            BR = this.Cols[BJ]["Gantt" + C5];
            if (BR == null) return null;
        }
    }
    if (this.Cols[BR]) return BR;
    return null;
};
_7m.prototype.Ny = function (BL, row, BJ, P) {
    var V = row[BJ + "Gdate"];
    if (V == null) return false;
    var IU = this.GetCell(row, BJ, 1);
    if (!IU) return false;
    V = IU.firstChild.childNodes[V];
    var Ck = V.firstChild;
    var BQ = row[BJ + "Gdatecnt"];
    if (BQ == 2 || BQ == 4) {
        var Hu = V.nextSibling.firstChild;
        if (P < V.offsetLeft + V.offsetWidth) {
            BL.Main = Ck;
            BL.Main2 = Hu;
            BL.MainPos = 1;
        } else {
            BL.Main = Hu;
            BL.Main2 = Ck;
            BL.MainPos = 2;
        };
        if (P < V.offsetLeft + (Ck.offsetWidth + Hu.offsetWidth) / 2) BL.MainX = 5;
        else BL.MainX = BL.Main.offsetWidth - 8;
    } else {
        BL.Main = Ck;
        BL.MainX = P < V.offsetLeft + Ck.offsetWidth / 2 ? 5 : Ck.offsetWidth - 8;
        BL.MainPos = 0;
    };
    return true;
};
_7m.prototype.GanttStart = function () {
    this.Gm();
    this.Nz = this.H3;
    this.H3 = 0;
    this.Calculated = 0;
};
_7m.prototype.GanttEnd = function (row, BJ) {
    this.Calculated = 1;
    this.H3 = this.Nz;
    if (row) this.Recalculate(row, BJ, 1);
    if (this.H3 && this.Nk) {
        this.Mn();
        this.Mo();
    }
};
_7m.prototype.ActionDragGantt = function () {
    return this.N0(31);
};
_7m.prototype.ActionDragGanttResize = function () {
    return this.N0(1);
};
_7m.prototype.ActionDragGanttNew = function () {
    return this.N0(2);
};
_7m.prototype.ActionDragGanttMove = function () {
    return this.N0(4);
};
_7m.prototype.ActionDragGanttDependency = function () {
    return this.N1(1);
};
_7m.prototype.N0 = function (type) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var row = D8.Row,
    BJ = D8.Col,
    options = this.Ge(row, BJ, "GanttOptions");
    var BL = this.GetGanttXY(row, BJ, D8.J6, D8.J7);
    if (!BL) return false;
    var max = this.Mouse.EdgeSize - 0,
    V;
    BL.N2 = this.Cols[BJ]["GanttWidth"];
    if (BL.Nq && type & 4 && options & 64) {
        BL.Object = BL.Nq;
        BL.N3 = BL.N2 - BL.Ns;
        BL.N4 = 3;
        BL.Type = 3;
        BL.N5 = type & 8 && options & 128;
    } else if (BL.Flow && type & 5 && options & 32) {
        if (type & 1) {
            if (BL.FlowX <= max) {
                BL.N4 = 1;
                BL.N3 = BL.N2 - BL.FlowX;
            } else if (BL.FlowX >= BL.Flow.offsetWidth - max) {
                BL.N4 = 2;
                BL.N3 = BL.Flow.offsetWidth - BL.FlowX;
            }
        }
        if (!BL.N4 && type & 4) {
            BL.N4 = 3;
            BL.N3 = 0;
        }
        BL.Object = BL.Flow.parentNode;
        BL.Type = 2;
    } else if (BL.Main && type & 13 && options & 144) {
        if (type & 1 && options & 16 && BL.MainType != "Milestone") {
            if (BL.MainX <= max && BL.MainPos != 2) {
                BL.N4 = this.HV ? 2 : 1;
                BL.N3 = this.HV ? BL.MainX: BL.N2 - BL.MainX;
            } else if (BL.MainX >= BL.Main.offsetWidth - max && BL.MainPos != 1) {
                BL.N4 = this.HV ? 1 : 2;
                BL.N3 = this.HV ? BL.N2 - BL.Main.offsetWidth + BL.MainX: BL.Main.offsetWidth - BL.MainX;
            }
        }
        if (!BL.N4 && type & 4 && options & 16) {
            BL.N4 = 3;
            BL.N3 = BL.MainType == "Milestone" ? BL.N2 - BL.MainX: 0;
            BL.N5 = type & 8 && options & 128;
        } else if (!BL.N4 && type & 8 && options & 128) return this.N1(0, BL);
        BL.Object = BL.Main.parentNode;
        BL.Type = 1;
    } else if (type & 2) {
        var Mg = row[BJ + "Gdw"],
        Mh;
        var BU = this.Nx(row, BJ, "Start");
        Mh = this.Cols[BJ].Mi;
        Mh = Mh - 0 + Math.floor(D8.J6 / BL.N2) * BL.N2 / Mg;
        var BR = Get(row, BU);
        var Dh = this.H3;
        this.H3 = 0;
        this.Calculated = 0;
        if (!BR && options & 16) {
            this.Gm();
            this.SetValue(row, BU, Mh, 1);
            var NK = this.Nx(row, BJ, "End");
            if (NK) this.SetValue(row, NK, Mh, 1);
            else {
                NK = this.Nx(row, BJ, "Duration");
                if (NK) this.SetValue(row, NK, 1, 1);
            };
            this.Gp(row, BU);
            this.CJ(row, BJ, 1);
            BL = this.GetGanttXY(row, BJ, D8.J6, null);
            if (BL.Main) {
                BL.N2 = this.Cols[BJ]["GanttWidth"];
                BL.N4 = 2;
                BL.Object = BL.Main.parentNode;
                BL.N3 = BL.Main.offsetWidth - BL.MainX;
                this.GetCell(row, BJ, 1).style.cursor = "e-resize";
                BL.Type = 1;
            }
        } else if (options & 32) {
            var BU = this.Nx(row, BJ, "Flow");
            if (BU) {
                BR = Get(row, BU);
                BR = (BR ? BR + this.Lang.Format.ValueSeparator: "") + (Mh - 0);
                this.SetValue(row, BU, BR, 1);
                this.CJ(row, BJ, 1);
                BL = this.GetGanttXY(row, BJ, D8.J6, null);
                if (BL.Flow) {
                    BL.N2 = this.Cols[BJ]["GanttWidth"];
                    BL.N4 = 2;
                    BL.Object = BL.Flow.parentNode;
                    BL.N3 = BL.Flow.offsetWidth - BL.FlowX;
                    this.GetCell(row, BJ, 1).style.cursor = "e-resize";
                    BL.Type = 2;
                }
            }
        }
        this.Calculated = 1;
        this.H3 = Dh;
    }
    if (!BL.N4) return false;
    BL.N6 = parseInt(BL.Object.style.width);
    if (BL.Type == 1 && BL.Main2) {
        BL.N7 = BL.Main2.parentNode;
        var CY = parseInt(BL.N7.style.width);
        BL.N6 += CY;
        BL.N8 = CY / BL.N6;
        BL.N9 = parseInt(BL.N7.style[this.HV ? "marginRight": "marginLeft"]);
        if (!BL.N9) BL.N9 = 0;
    }
    BL.OA = parseInt(BL.Object.style[this.HV ? "marginRight": "marginLeft"]);
    if (!BL.OA) BL.OA = 0;
    BL.OB = 0;
    D8.Gantt = BL;
    D8.Action = "DragGantt";
    D8.Move = this.OC;
    D8.II = this.OD;
    this.OE(D8.P, D8.Y, null, BL.N4 == 3 ? 1 : 0);
    return true;
};
_7m.prototype.OC = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var Ik = D8.IQ - D8.P,
    BL = D8.Gantt,
    margin = this.HV ? "marginRight": "marginLeft";
    D8.P += Ik;
    BL.OB += Ik;
    var Df = Math.ceil((BL.OB - BL.N3) / BL.N2) * BL.N2;
    if (BL.N4 == 3) {
        if (BL.N5 && this.ARow != D8.Row && this.ARow && BL.Type != 3) {
            BL.Object.style[margin] = BL.OA + "px";
            if (BL.N7) BL.N7.style[margin] = BL.N9 + "px";
            D8.P -= BL.OB;
            return this.N1(0, BL);
        }
        BL.Object.style[margin] = (BL.OA + Df) + "px";
        if (BL.N7) BL.N7.style[margin] = (BL.N9 + Df) + "px";
    } else if (BL.N4 == 2) {
        var CY = BL.N6 + Df;
        if (CY < 0) CY = 0;
        if (BL.N7) {
            var Cv = Math.round(CY * BL.N8);
            BL.N7.style.width = Cv + "px";
            BL.Object.style[margin] = Math.round(BL.OA + Df * BL.N8) + "px";
            BL.Object.style.width = (CY - Cv) + "px";
        } else BL.Object.style.width = CY + "px";
    } else if (BL.N4 == 1) {
        var CY = BL.N6 - Df;
        var x = BL.OA + Df;
        if (CY < 0) CY = 0;
        BL.Object.style[margin] = x + "px";
        if (BL.N7) {
            var Cv = Math.round(CY * BL.N8);
            BL.N7.style.width = Cv + "px";
            BL.N7.style[margin] = Math.round(BL.N9 + Df * BL.N8) + "px";
            BL.Object.style.width = (CY - Cv) + "px";
        } else BL.Object.style.width = CY + "px";
    }
    var Gr = this.GetColLeft(this.XHeader, D8.IQ);
    this.OF(Gr[0], Gr[1]);
    return true;
};
_7m.prototype.OD = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BL = D8.Gantt,
    row = D8.Row,
    BJ = D8.Col;
    this.Nt = null;
    this.JY = null;
    var Df = Math.ceil((BL.OB - BL.N3) / BL.N2) * BL.N2;
    if (Df) {
        var Mg = row[BJ + "Gdw"];
        if (BL.Type == 3) {
            var BU = this.Nx(row, BJ, "Flags");
            if (BU) {
                var BR = Get(row, BU),
                MN = this.Lang.Format;
                BR = (BR + "").split(MN.ValueSeparator);
                var BZ = BR[BL.Nr] - 0;
                if (BZ) {
                    BR[BL.Nr] = BZ + Df / Mg;
                    this.SetValue(row, BU, BR.join(MN.ValueSeparator), 1);
                    return true;
                }
            }
        } else if (BL.Type == 2) {
            var BU = this.Nx(row, BJ, "Flow");
            if (BU) {
                var BR = Get(row, BU),
                MN = this.Lang.Format;
                BR = (BR + "").split(MN.ValueSeparator);
                var BZ = BR[BL.FlowIndex];
                if (BZ) {
                    if (BL.N6 + (BL.N4 == 1 ? -Df: Df) >= BL.N2 || BL.N4 == 3) {
                        BZ = BZ.split(MN.RangeSeparator);
                        if (!BZ[1]) BZ[1] = BZ[0];
                        if (BL.N4 == 3) {
                            BZ[0] = BZ[0] - 0 + Df / Mg;
                            BZ[1] = BZ[1] - 0 + Df / Mg;
                        } else {
                            BZ[BL.N4 - 1] -= 0;
                            BZ[BL.N4 - 1] += Df / Mg;
                        }
                    } else BZ = [];
                    BR[BL.FlowIndex] = BZ.join(MN.RangeSeparator);
                    this.SetValue(row, BU, this.BP(BR.join(MN.ValueSeparator)), 1);
                    return true;
                }
            }
        } else if (BL.Type == 1) {
            if (BL.N4 == 3) {
                var BU = this.Nx(row, BJ, "Start");
                if (BU) {
                    if (BL.MainType == "Milestone") {
                        var BR = Get(row, BU);
                        if (BR) this.SetValue(row, BU, BR + Df / Mg, 1);
                        else {
                            BU = this.Nx(row, BJ, "End");
                            this.SetValue(row, BU, Get(row, BU) + Df / Mg, 1);
                        }
                    } else {
                        this.GanttStart();
                        var BR = Get(row, BU);
                        BR += Df / Mg;
                        this.SetValue(row, BU, BR, 1);
                        var NK = this.Nx(row, BJ, "End");
                        if (NK) {
                            var BR = Get(row, NK);
                            BR += Df / Mg;
                            this.SetValue(row, NK, BR, 1);
                        }
                        this.GanttEnd(row, BU);
                    };
                    return true;
                }
            } else if (BL.N6 + (BL.N4 == 1 ? -Df: Df) >= BL.N2) {
                if (BL.N4 == 1) {
                    var BU = this.Nx(row, BJ, "Start");
                    var BR = Get(row, BU);
                    BR += Df / Mg;
                    var NK = this.Nx(row, BJ, "Duration");
                    if (NK) {
                        var BZ = Get(row, NK);
                        BZ += -Df / BL.N2;
                        this.GanttStart();
                        this.SetValue(row, NK, BZ, 1);
                        this.SetValue(row, BU, BR, 1);
                        this.GanttEnd(row, BU);
                    } else this.SetValue(row, BU, BR, 1);
                } else {
                    var BU = this.Nx(row, BJ, "End");
                    if (BU) {
                        var BR = Get(row, BU);
                        BR += Df / Mg;
                        this.SetValue(row, BU, BR, 1);
                    } else {
                        BU = this.Nx(row, BJ, "Duration");
                        if (BU) {
                            var BR = Get(row, BU);
                            BR += Df / BL.N2;
                            this.SetValue(row, BU, BR, 1);
                        }
                    }
                }
            } else {
                var BU = this.Nx(row, BJ, "Start");
                if (BU) {
                    this.GanttStart();
                    this.SetValue(row, BU, null, 1);
                    var NK = this.Nx(row, BJ, "End");
                    if (NK) this.SetValue(row, NK, null, 1);
                    var NK = this.Nx(row, BJ, "Duration");
                    if (NK) this.SetValue(row, NK, null, 1);
                    this.GanttEnd(row, BU);
                    return true;
                }
            }
        }
    }
    this.RefreshCell(row, BJ);
    return false;
};
_7m.prototype.N1 = function (OG, BL) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var row = D8.Row,
    BJ = D8.Col;
    if (! (this.Ge(row, BJ, "GanttOptions") & 128)) return false;
    if (!BL) BL = this.GetGanttXY(row, BJ, D8.J6, D8.J7);
    if (!BL) return false;
    if (!BL.Main && OG) {
        if (!this.Ny(BL, row, BJ, D8.J6)) return false;
        D8.P += BL.Main.parentNode.offsetLeft - D8.J6 + BL.MainX;
        D8.Y += Math.floor(BL.Main.parentNode.offsetTop + BL.Main.offsetHeight / 2 - D8.J7);
        BL.OH = 1;
    }
    var OI = GetWindowScroll(),
    OJ = OI[0] / _7AJ,
    LB = OI[1] / _7AJ;
    if (_7R) OJ -= _7S ? document.body.clientLeft: (_7T ? document.documentElement.clientLeft: 0);
    OJ += 2;
    D8.Gantt = BL;
    D8.Action = "DragGanttDependency";
    D8.Move = this.OK;
    D8.II = this.OL;
    var H = document.body,
    Cr = document.createElement("div");
    Cr.innerHTML = "<div class='" + this.Img.Style + "GanttDepVert' style='margin-top:0px;" + this.NF + "'>" + _7D + "</div>";
    Cr.style.overflow = "hidden";
    Cr.style.height = "0px";
    Cr.style.left = (D8.P + OJ) + "px";
    Cr.style.top = (D8.Y + LB) + "px";
    Cr.style.position = "absolute";
    if (_7R && !_7U) H.insertBefore(Cr, H.firstChild);
    else H.appendChild(Cr);
    BL.OM = Cr;
    var ON = Cr.cloneNode(1);
    H.insertBefore(ON, Cr);
    BL.OO = ON;
    var KL = document.createElement("div");
    KL.innerHTML = "<div class='" + this.Img.Style + "GanttDepHorzIn' style='margin-top:0px;'>" + _7D + "</div>";
    KL.style.overflow = "hidden";
    KL.style.fontSize = "1px";
    KL.style.lineHeight = "1px";
    KL.style.width = "0px";
    KL.style.left = (D8.P + OJ) + "px";
    KL.style.top = (D8.Y + LB) + "px";
    KL.style.position = "absolute";
    H.insertBefore(KL, Cr);
    BL.OP = KL;
    return true;
};
_7m.prototype.OK = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var Ik = D8.IQ - D8.P,
    J5 = D8.IR - D8.Y,
    OQ = Math.floor(J5 / 2),
    BL = D8.Gantt;
    var Cr = BL.OM,
    KL = BL.OP,
    ON = BL.OO,
    Jz = this.ND;
    var OI = GetWindowScroll(),
    OJ = OI[0] / _7AJ,
    LB = OI[1] / _7AJ;
    if (_7R) OJ -= _7S ? document.body.clientLeft: (_7T ? document.documentElement.clientLeft: 0);
    OJ += 2;
    if (J5 >= 0) {
        Cr.style.top = (D8.Y + LB) + "px";
        Cr.style.height = (OQ + Jz) + "px";
        ON.style.top = (D8.Y + OQ + Jz + LB) + "px";
        ON.style.height = OQ + "px";
        KL.style.top = (D8.Y + OQ + LB) + "px";
    } else {
        Cr.style.top = (D8.IR - OQ + Jz * 2 + LB) + "px";
        Cr.style.height = (OQ - J5) + "px";
        ON.style.top = (D8.IR + Jz + LB) + "px";
        ON.style.height = ( - OQ + Jz) + "px";
        KL.style.top = (D8.IR - OQ + LB) + "px";
    };
    ON.style.left = (D8.IQ + OJ) + "px";
    if (Ik >= 0) {
        KL.style.left = (D8.P + Jz + OJ) + "px";
        KL.style.width = Ik + "px";
    } else {
        KL.style.left = (D8.IQ + Jz * 2 + OJ) + "px";
        KL.style.width = ( - Ik) + "px";
    };
    var Gr = this.GetColLeft(this.XHeader, D8.IQ);
    this.OF(Gr[0], Gr[1]);
    this.IP(this.ARow);
    return true;
};
_7m.prototype.OL = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BL = D8.Gantt,
    row = D8.Row,
    BJ = D8.Col,
    Bp = this.Lang.Format.ValueSeparator;
    var MN = this.GetGanttXY(D8.IQ, D8.IR);
    var H = document.body;
    H.removeChild(BL.OM);
    H.removeChild(BL.OO);
    H.removeChild(BL.OP);
    this.JY = null;
    if (row != this.ARow && MN && this.Ge(this.ARow, this.ACol, "GanttOptions") & 128) {
        if (!MN.Main) this.Ny(MN, this.ARow, this.ACol, MN.J6);
        var BU = this.Nx(row, BJ, "Dependencies"),
        NK = this.Nx(row, BJ, "DependencyTypes");
        if (BU) {
            var BR = Get(row, BU),
            id = this.ARow.id;
            BR = BR ? (BR + "").split(Bp) : [];
            for (var i = 0; i < BR.length; i++) if (BR[i] == id) break;
            BR[i] = id;
            BR = BR.join(Bp);
            if (NK) {
                var Ca = Get(row, NK);
                Ca = Ca ? (Ca + "").split(Bp) : [];
                for (var j = 0; j < i; j++) if (!Ca[j]) Ca[j] = "";
                var DN = this.HV ? "f": "s",
                Kf = this.HV ? "s": "f",
                OR = Kf,
                OS = DN;
                if (BL.Main) {
                    var OT = BL.MainX,
                    M6 = BL.Main.offsetWidth;
                    if (BL.MainPos) M6 += BL.Main2.offsetWidth;
                    if (BL.MainPos == 2) OT += BL.Main2.offsetWidth;
                    OR = OT < M6 / 2 ? DN: Kf;
                }
                if (MN.Main) {
                    var OU = MN.MainX,
                    Cv = MN.Main.offsetWidth;
                    if (MN.MainPos) Cv += MN.Main2.offsetWidth;
                    if (MN.MainPos == 2) OU += MN.Main2.offsetWidth;
                    OS = OU < Cv / 2 ? DN: Kf;
                }
                Ca[i] = OR + OS;
                this.GanttStart();
                this.SetValue(row, NK, Ca.join(Bp), 1);
                this.SetValue(row, BU, BR, 1);
                this.GanttEnd(row, BU);
            } else this.SetValue(row, BU, BR, 1);
            return true;
        }
    }
    var OV = this.GetAlert("ErrGanttDep");
    if (OV) {
        Grids.Az = null;
        alert(OV);
    }
    return false;
};
_7m.prototype.OW = function (row, J, BJ) {
    var BU = this.Nx(row, BJ, "Dependencies");
    if (!BU) return;
    var BR = Get(row, BU),
    Bp = this.Lang.Format.ValueSeparator;
    if (!BR) return;
    BR = (BR + "").split(Bp);
    for (var j = 0; j < BR.length; j++) {
        if (BR[j] == J.id) {
            BR.splice(j, 1);
            BR = BR.join(Bp);
            this.SetValue(row, BU, BR, 1);
            row[BJ + "Gdependency"] = BR;
            this.Nk = 1;
            var NK = this.Nx(row, BJ, "DependencyTypes");
            if (NK) {
                var Ca = Get(row, NK);
                if (Ca) {
                    Ca = (Ca + "").split(Bp);
                    if (Ca.length > j) {
                        Ca.splice(j, 1);
                        Ca = Ca.join(Bp);
                        this.SetValue(row, NK, Ca, 1);
                        row[BJ + "Gdeptype"] = Ca;
                    }
                }
            }
        }
    }
};
_7m.prototype.OX = function (row, BJ, OY) {
    var BU = this.Nx(row, BJ, "Dependencies");
    if (!BU) return false;
    if (!OY) this.GanttStart();
    var BR = row[BJ + "Gdependency"];
    if (BR) {
        this.SetValue(row, BU, "", 1);
        row[BJ + "Gdependency"] = "";
        this.Nk = 1;
    }
    var Bp = this.Lang.Format.ValueSeparator;
    var a = row[BJ + "Gdepfs"],
    Bc = row[BJ + "Gdepff"];
    if (a && Bc) a += Bp;
    a += Bc;
    if (a) {
        a = (a + "").split(Bp);
        for (var i = 0; i < a.length; i++) {
            var J = this.GetRowById(a[i]);
            if (J) this.OW(J, row, BJ);
        }
    }
    if (!OY) this.GanttEnd(row, BU);
    return true;
};
_7m.prototype.ActionDeleteGanttDependencies = function (CE, OY) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !(this.Ge(row, BJ, "GanttOptions") & 128)) return false;
    return this.OX(row, BJ);
};
_7m.prototype.ActionDeleteGanttDependency = function () {
    var row = this.ARow,
    BJ = this.ACol,
    Bp = this.Lang.Format.ValueSeparator;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 128)) return false;
    var a = BL.DependencyEndRight;
    if (!a) a = BL.DependencyEndLeft;
    if (a) {
        a = (a + "").split(Bp);
        for (var i = 0; i < a.length; i++) {
            var J = this.GetRowById(a[i]);
            if (J) {
                this.OW(J, row, BJ);
                return true;
            }
        }
    }
    var BU = this.Nx(row, BJ, "Dependencies");
    if (!BU) return false;
    var a = BL.DependencyStartLeft;
    if (!a) a = BL.DependencyStartRight;
    if (a) {
        a = (a + "").split(Bp);
        var BR = Get(row, BU);
        if (BR) {
            BR = (BR + "").split(Bp);
            for (var i = 0; i < BR.length; i++) if (BR[i] == a[0]) {
                BR.splice(i, 1);
                this.SetValue(row, BU, BR.join(Bp), 1);
                return true;
            }
        }
    }
    return false;
};
_7m.prototype.OZ = function (row, BJ, Ce) {
    var BU = this.Nx(row, BJ, "Flags");
    if (!BU) return false;
    var BR = Get(row, BU);
    if (!BR) return false;
    this.GanttStart();
    var Bp = this.Lang.Format.ValueSeparator;
    BR = (BR + "").split(Bp);
    BR.splice(Ce, 1);
    this.SetValue(row, BU, BR.join(Bp), 1);
    var NK = this.Nx(row, BJ, "FlagTexts");
    if (NK) {
        var BR = Get(row, NK);
        if (BR) {
            BR = (BR + "").split(Bp);
            BR.splice(Ce, 1);
            this.SetValue(row, NK, BR.join(Bp), 1);
        }
    }
    this.GanttEnd(row, BU);
    this.Nt = null;
    return true;
};
_7m.prototype.ActionDeleteGanttFlag = function () {
    var row = this.ARow,
    BJ = this.ACol;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL || !BL.Nq) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 64)) return false;
    return this.OZ(row, BJ, BL.Nr);
};
_7m.prototype.Oa = function (row, BJ) {
    var BU = this.Nx(row, BJ, "Flags");
    if (!BU) return false;
    this.GanttStart();
    this.SetValue(row, BU, null, 1);
    var NK = this.Nx(row, BJ, "FlagTexts");
    if (NK) this.SetValue(row, NK, null, 1);
    this.GanttEnd(row, BU);
    this.Nt = null;
    return true;
};
_7m.prototype.Ob = function (row, BJ) {
    this.GanttStart();
    var BU = this.Nx(row, BJ, "Start");
    if (BU) this.SetValue(row, BU, null, 1);
    BU = this.Nx(row, BJ, "End");
    if (BU) this.SetValue(row, BU, null, 1);
    BU = this.Nx(row, BJ, "Duration");
    if (BU) this.SetValue(row, BU, null, 1);
    this.GanttEnd(row, BU);
    this.Nt = null;
    return true;
};
_7m.prototype.ActionDeleteGanttMain = function () {
    var row = this.ARow,
    BJ = this.ACol;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL || !BL.Main) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 16)) return false;
    return this.Ob(row, BJ);
};
_7m.prototype.Oc = function (row, BJ, Ce) {
    var BU = this.Nx(row, BJ, "Flow");
    if (!BU) return false;
    if (Ce != null) {
        var BR = Get(row, BU);
        if (!BR) return false;
        var Bp = this.Lang.Format.ValueSeparator;
        BR = (BR + "").split(Bp);
        BR.splice(Ce, 1);
        this.SetValue(row, BU, BR.join(Bp), 1);
    } else this.SetValue(row, BU, null, 1);
    this.Nt = null;
    return true;
};
_7m.prototype.ActionDeleteGanttFlow = function () {
    var row = this.ARow,
    BJ = this.ACol;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL || !BL.Flow) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 32)) return false;
    return this.Oc(row, BJ, BL.FlowIndex);
};
_7m.prototype.Od = function (row, BJ) {
    this.GanttStart();
    this.OX(row, BJ, 1);
    var F = ["Start", "End", "Duration", "Complete", "Resources", "Flow", "Flags", "FlagTexts", "Dependencies", "DependencyTexts"];
    for (var i = 0; i < F.length; i++) {
        var BU = this.Nx(row, BJ, F[i]);
        if (BU) this.SetValue(row, BU, null, 1);
    }
    this.GanttEnd(row, BJ);
    this.Nt = null;
    return true;
};
_7m.prototype.ActionDeleteGanttAll = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || (this.Ge(row, BJ, "GanttOptions") & 240) != 240) return false;
    return this.Od(row, BJ);
};
_7m.prototype.ActionSetGanttPercent = function () {
    var row = this.ARow,
    BJ = this.ACol;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL || !BL.Main || BL.MainType == "Milestone") return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 16)) return false;
    var BU = this.Nx(row, BJ, "Complete");
    if (!BU) return false;
    var CY = BL.Main.parentNode.offsetWidth;
    var x = BL.MainX;
    if (BL.MainPos) CY += BL.Main2.parentNode.offsetWidth;
    if (BL.MainPos == 2) x += BL.Main2.parentNode.offsetWidth;
    var It = this.Cols[BJ]["GanttWidth"];
    x = Math.round(x / It) * It;
    var BR = Math.round(x / CY * 100);
    this.SetValue(row, BU, BR, 1);
    this.Nt = null;
    return true;
};
_7m.prototype.Oe = function (row, BJ) {
    var BU = this.Nx(row, BJ, "Complete");
    if (!BU) return false;
    var BR = Get(row, BU),
    T = this;
    this.Prompt(this.GetAlert("GanttPercentEdit"), BR, function (BR) {
        if (BR == null) return;
        if (isNaN(BR) || BR < 0 || BR > 100) {
            alert(T.GetAlert("ErrGanttPercentEdit"));
            return;
        }
        T.SetValue(row, BU, BR, 1);
        T.Nt = null;
    });
    return true;
};
_7m.prototype.Of = function (row, BJ) {
    var BU = this.Nx(row, BJ, "Resources");
    if (!BU) return false;
    var BR = Get(row, BU),
    T = this;
    if (this.Og(row, BU, 0, {
        Row: row,
        Col: BJ,
        P: this.JB - 5,
        Y: this.JC - 5
    },
    this.GetText("AssignGanttResource"))) {
        this.Nt = null;
        return true;
    }
    this.Prompt(this.GetAlert("GanttResourceEdit"), BR, function (BR) {
        if (BR == null) return;
        T.SetValue(row, BU, BR, 1);
        T.Nt = null;
    });
    return true;
};
_7m.prototype.ActionEditGanttResource = function () {
    var row = this.ARow,
    BJ = this.ACol;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL || !BL.Main) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 8)) return false;
    return this.Of(row, BJ, BL.Nr);
};
_7m.prototype.Oh = function (row, BJ, Ce) {
    var BU = this.Nx(row, BJ, "FlagTexts");
    if (!BU) return false;
    var BR = Get(row, BU),
    Bp = this.Lang.Format.ValueSeparator,
    T = this;
    BR = BR ? (BR + "").split(Bp) : [];
    this.Prompt(this.GetAlert("GanttFlagEdit"), BR[Ce], function (BZ) {
        if (BZ == null) return;
        BZ = (BZ + "").replace(Bp, Bp == ',' ? ';': ',');
        BR[Ce] = BZ;
        T.SetValue(row, BU, BR.join(Bp), 1);
        T.Nt = null;
    });
    return true;
};
_7m.prototype.ActionEditGanttFlag = function () {
    var row = this.ARow,
    BJ = this.ACol;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL || !BL.Nq) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 64)) return false;
    return this.Oh(row, BJ, BL.Nr);
};
_7m.prototype.ActionNewGanttFlag = function () {
    var row = this.ARow,
    BJ = this.ACol,
    T = this,
    P = this.JB,
    Bp = this.Lang.Format.ValueSeparator;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 64)) return false;
    var BU = this.Nx(row, BJ, "Flags");
    if (!BU) return false;
    this.Prompt(this.GetAlert("GanttFlagEdit"), "", function (BZ) {
        if (BZ == null) return;
        var CY = P / row[BJ + "Gdw"],
        It = this.Cols[BJ]["GanttWidth"],
        Mh;
        Mh = this.Cols[BJ].Mi - 0;
        Mh += Math.round(CY / It) * It;
        var BR = Get(row, BU);
        BR = BR ? (BR + "").split(Bp) : [];
        var Ce = BR.length;
        BR[Ce] = Mh;
        T.GanttStart();
        T.SetValue(row, BU, BR.join(Bp), 1);
        var NK = T.Nx(row, BJ, "FlagTexts");
        if (NK) {
            BR = Get(row, NK);
            BR = BR ? (BR + "").split(Bp) : [];
            BZ = (BZ + "").replace(Bp, Bp == ',' ? ';': ',');
            BR[Ce] = BZ;
            T.SetValue(row, NK, BR.join(Bp), 1);
        }
        T.GanttEnd(row, BU);
        T.Nt = null;
    });
};
_7m.prototype.ActionNewGanttMilestone = function () {
    var row = this.ARow,
    BJ = this.ACol,
    P = this.JB,
    Bp = this.Lang.Format.ValueSeparator;
    var BL = this.GetGanttXY(row, BJ, this.JB, this.JC);
    if (!BL) return false;
    if (! (this.Ge(row, BJ, "GanttOptions") & 16)) return false;
    var BU = this.Nx(row, BJ, "Start"),
    NK = this.Nx(row, BJ, "End");
    if (!BU || !NK || Get(row, BU) || Get(row, NK)) return false;
    var CY = P / row[BJ + "Gdw"],
    It = this.Cols[BJ]["GanttWidth"],
    Mh;
    Mh = this.Cols[BJ].Mi - 0;
    Mh += Math.round(CY / It) * It;
    this.SetValue(row, BU, Mh, 1);
    this.Nt = null;
    return true;
};
_7m.prototype.ActionGanttMenu = function () {
    var row = this.ARow,
    BJ = this.ACol,
    P = this.JB,
    Y = this.JC;
    var BL = this.GetGanttXY(row, BJ, P, Y);
    if (!BL) return false;
    var M = [],
    CE = [],
    F = [],
    Bg = 0,
    OJ = 0,
    T = this,
    options = this.Ge(row, BJ, "GanttOptions");
    var Bp = this.Lang.Format.ValueSeparator;
    var C0 = Get(row, BJ + "Menu");
    if (!C0) C0 = Get(row, "Menu");
    if (!C0 && BJ && this.Cols[BJ]) C0 = this.Cols[BJ]["Menu"];
    if (C0) {
        C0 += "";
        M = C0.slice(1).split(C0.charAt(0));
        Bg = M.length;
        C0 = Bg;
    }
    if (Bg != OJ) {
        M[Bg++] = "*-";
        OJ = Bg;
    }
    if (BL.Nq && options & 64) {
        var OV = this.GetText("DelGanttFlag");
        if (OV) {
            CE[Bg] = this.OZ;
            F[Bg] = [row, BJ, BL.Nr];
            M[Bg++] = OV;
        }
        var OV = this.GetText("EditGanttFlag");
        if (OV) {
            CE[Bg] = this.Oh;
            F[Bg] = [row, BJ, BL.Nr];
            M[Bg++] = OV;
        }
    }
    if (BL.Flow && options & 32) {
        var OV = this.GetText("DelGanttFlowPart");
        if (OV) {
            CE[Bg] = this.Oc;
            F[Bg] = [row, BJ, BL.FlowIndex];
            M[Bg++] = OV;
        }
    }
    if (BL.Main && options & 16) {
        if (BL.MainType == "Milestone") {
            var OV = this.GetText("DelGanttMilestone");
            if (OV) {
                CE[Bg] = this.Ob;
                F[Bg] = [row, BJ];
                M[Bg++] = OV;
            }
        } else {
            var OV = this.GetText("DelGanttMain");
            if (OV) {
                CE[Bg] = this.Ob;
                F[Bg] = [row, BJ];
                M[Bg++] = OV;
            }
            var OV = this.GetText("SetGanttPercent");
            if (OV) {
                CE[Bg] = this.Oi;
                F[Bg] = [row, BJ, P, Y, this.ActionSetGanttPercent];
                M[Bg++] = OV;
            }
            var OV = this.GetText("EditGanttPercent");
            if (OV) {
                CE[Bg] = this.Oe;
                F[Bg] = [row, BJ];
                M[Bg++] = OV;
            }
        }
    }
    if (BL.Main && options & 8 && this.Nx(row, BJ, "Resources")) {
        var OV = this.GetText("EditGanttResource");
        if (OV) {
            CE[Bg] = this.Of;
            F[Bg] = [row, BJ];
            M[Bg++] = OV;
        }
    }
    if (Bg != OJ) {
        M[Bg++] = "*-";
        OJ = Bg;
    }
    if (options & 128) {
        var Cr = [["Gdepss", "Gdepsf", "DelGanttDepStart"], ["Gdepfs", "Gdepff", "DelGanttDepEnd"]],
        Oj = Bg;
        for (var j = 0; j < Cr.length; j++) {
            var a = row[BJ + Cr[j][0]],
            Bc = row[BJ + Cr[j][1]];
            if (a && Bc) a += Bp;
            a += Bc;
            if (a) {
                a = (a + "").split(Bp);
                for (var i = 0; i < a.length; i++) {
                    var J = this.GetRowById(a[i]);
                    var OV = this.GetText(Cr[j][2]);
                    if (J && OV) {
                        CE[Bg] = this.OW;
                        F[Bg] = j == 0 ? [row, J, BJ] : [J, row, BJ];
                        M[Bg++] = OV.replace("%d", (J.Name ? J.Name: J.id));
                    }
                }
            }
        }
        if (Oj != Bg) {
            var OV = this.GetText("DelAllGanttDep");
            if (OV) {
                CE[Bg] = this.OX;
                F[Bg] = [row, BJ];
                M[Bg++] = OV.replace("%d", Bg - Oj);
            }
        }
    }
    if ((options & 144) == 144) {
        var BU = this.Nx(row, BJ, "Start"),
        NK = this.Nx(row, BJ, "End"),
        Ok = 0;
        if (!NK) NK = this.Nx(row, BJ, "Duration");
        if (BU && NK) {
            var B = Get(row, BU),
            Cs = Get(row, NK);
            if (B || Cs) {
                var OV = this.GetText(B && Cs ? "DelGanttMainDep": "DelGanttMilestoneDep");
                Ok = 1;
                if (OV) {
                    CE[Bg] = function (row, BJ) {
                        T.OX(row, BJ);
                        T.Ob(row, BJ);
                    };
                    F[Bg] = [row, BJ];
                    M[Bg++] = OV;
                }
            }
        }
    }
    if (options & 32) {
        var BU = this.Nx(row, BJ, "Flow"),
        Mu = 0;
        if (BU && Get(row, BU)) {
            var OV = this.GetText("DelGanttFlow");
            Mu = 1;
            if (OV) {
                CE[Bg] = this.Oc;
                F[Bg] = [row, BJ];
                M[Bg++] = OV;
            }
        }
    }
    if (options & 64) {
        var BU = this.Nx(row, BJ, "Flags");
        if (BU && Get(row, BU)) {
            var OV = this.GetText("DelGanttFlags");
            if (OV) {
                CE[Bg] = this.Oa;
                F[Bg] = [row, BJ];
                M[Bg++] = OV;
            }
        }
    }
    if ((options & 240) == 240) {
        if (Oj != Bg) {
            var OV = this.GetText("DelGanttAll");
            if (OV) {
                CE[Bg] = this.Od;
                F[Bg] = [row, BJ];
                M[Bg++] = OV;
            }
        }
    }
    if (Bg != OJ) {
        M[Bg++] = "*-";
        OJ = Bg;
    }
    if (!BL.Nq && options & 64 && this.Nx(row, BJ, "Flags")) {
        var OV = this.GetText("NewGanttFlag");
        if (OV) {
            CE[Bg] = this.Oi;
            F[Bg] = [row, BJ, P, Y, this.ActionNewGanttFlag];
            M[Bg++] = OV;
        }
    }
    if (!BL.Main && options & 16 && Ok) {
        var OV = this.GetText("EditGanttPercent");
        if (OV) {
            CE[Bg] = this.Oe;
            F[Bg] = [row, BJ];
            M[Bg++] = OV;
        }
    }
    if (!BL.Main && options & 8 && this.Nx(row, BJ, "Resources")) {
        var OV = this.GetText("EditGanttResource");
        if (OV) {
            CE[Bg] = this.Of;
            F[Bg] = [row, BJ];
            M[Bg++] = OV;
        }
    }
    if (Bg && Bg == OJ) M.length = Bg - 1;
    if (!Bg) return false;
    this.ShowMenu(row, BJ, M, null, function (Ce) {
        if (Ce < C0) {
            if (Grids.OnContextMenu) return ! Grids.OnContextMenu(T, row, BJ, Ce, M[Ce]);
            alert("No OnContextMenu event defined");
            return true;
        }
        var BG = CE[Ce];
        if (BG) BG.apply(T, F[Ce]);
        if (BG == T.Of) return false;
    },
    1, null, this.JB - 5, this.JC - 5);
    return true;
};
_7m.prototype.Oi = function (row, BJ, P, Y, Mz, M0, Ol) {
    var J = this.ARow,
    BY = this.ACol,
    x = this.JB,
    y = this.JC;
    this.ARow = row;
    this.ACol = BJ;
    this.JB = P;
    this.JC = Y;
    if (Mz) Mz.apply(this);
    if (M0) M0.apply(this);
    if (Ol) Ol.apply(this);
    this.ARow = J;
    this.ACol = BY;
    this.JB = x;
    this.JC = y;
};
_7m.prototype.Focus = function (row, BJ, Om, Bx) {
    var I7 = this.FRow,
    Eb = this.FCol;
    if (!row) {
        if (this.EditMode && this.EndEdit(I7, Eb, true) == -1) return null;
        this.FCol = null;
        this.FPagePos = null;
        this.FRow = null;
        if (I7 && !I7.Page) {
            if (_7R && Eb && this.GetType(I7, Eb) == "Enum") {
                var T = this;
                setTimeout(function () {
                    T.ColorCell(I7, Eb);
                    T.ColorRow(I7);
                },
                10);
            } else {
                this.ColorCell(I7, Eb);
                this.ColorRow(I7);
            }
        }
        this.CloseDialog();
        this.On = null;
        this.Oo = null;
        return true;
    }
    var Op = this.Paging ? this.GetRowPage(row) : null;
    this.JV();
    if (Om != null && !row.Page) Om = null;
    if (this.Paging && Op && Op != row && Op.State != 4) {
        if (Bx && !(Op.State < 2)) {
            if (this.AllPages) this.FF(Op);
            else this.GoToPage(Op);
        } else {
            Om = this.GetPagePos(row);
            row = this.GetRowPage(row);
        }
    }
    if (row.Page && row.State == 4) {
        return this.Focus(this.PagePosToRow(row, Om), BJ);
    }
    if (I7 == row && Eb == BJ && (Om == null || Om == this.FPagePos)) return false;
    var Dv = this.CanFocus(row, BJ);
    if (Om == null && !Dv) return null;
    if (Om != null && row.State == 4) {
        var J = this.PagePosToRow(row, Om);
        row = J ? J: this.GetLastVisible(row, 1);
        Om = null;
    }
    if (this.EndEdit(I7, Eb, true) == -1) return null;
    this.I3 = Dv == 2 ? this.FRow: null;
    this.I4 = Dv == 2 ? this.FCol: null;
    this.FRow = row;
    this.FPagePos = Om;
    if (BJ && (BJ == "Panel" || Dv)) this.FCol = BJ;
    if (!this.c[1]) return null;
    if (I7 && !I7.Page && Dv != 2) {
        this.ColorCell(I7, Eb);
        this.ColorRow(I7);
    }
    if (Bx && !row.Page && !row.Fixed) {
        if (this.ShowRow) this.ShowRow(row);
        var EU = row.parentNode,
        exp = false;
        while (EU && !EU.Page) {
            if (!EU.Expanded) {
                exp = true;
                this.Expand(EU);
            }
            EU = EU.parentNode;
        }
        if (exp) {
            var T = this;
            setTimeout(function () {
                T.ScrollIntoView(row, T.FCol, Om);
            },
            100);
        }
    }
    if (!row.Page) this.ColorRow(row);
    this.ScrollIntoView(row, this.FCol, Om);
    this.CloseDialog();
    var Cu = this.JW("", "Focus", "", row, BJ);
    if (this.Paging) this.Calculate(1, 1, 1, 1);
    if (Grids.OnFocus) Grids.OnFocus(this, row, BJ, I7, Eb, Om);
    this.Refresh();
    return true;
};
_7m.prototype.ActionFocus = function () {
    return this.Focus(this.ARow, this.ACol) != null;
};
_7m.prototype.ActionChangeFocus = function () {
    return this.Focus(this.ARow, this.ACol) == true;
};
_7m.prototype.ActionFocusRow = function () {
    return this.Focus(this.ARow, null) != null;
};
_7m.prototype.ActionBlur = function () {
    if (this.StaticCursor) return this.EditMode ? this.EndEdit(this.FRow, this.FCol, 1) != -1 : true;
    return this.Focus(null, null);
};
_7m.prototype.JV = function () {
    var CE = Grids.Focused;
    if (CE != this) {
        if (CE && !CE.Ae) {
            CE.CloseDialog(true);
            if (!CE.JW("", "Click", "Outside", null, null)) return false;
        }
        Grids.Focused = this;
    }
};
_7m.prototype.GetText = function (Oq) {
    var Cs = this.Lang["Text"];
    return Cs ? Cs[Oq] : Oq;
};
_7m.prototype.GetAlert = function (Oq) {
    var Cs = this.Lang["Alert"];
    return Cs ? Cs[Oq] : Oq;
};
_7m.prototype.Disable = function () {
    if (this.Ae) return;
    if (Grids.OnDisable) Grids.OnDisable(this);
    this.CloseDialog();
    this.Ae = true;
    if (!this.e) return;
    if (_7R) {
        this.e.style.filter = "alpha(opacity=50)";
        this.q.disabled = true;
        this.r.disabled = true;
    } else {
        this.q.style.visibility = "hidden";
        this.r.style.visibility = "hidden";
        this.e.style.opacity = "0.5";
    };
    this.Or = null;
};
_7m.prototype.Enable = function () {
    if (!this.Ae) return;
    this.Ae = false;
    if (!this.e) return;
    if (_7R) {
        this.e.style.removeAttribute("filter");
        this.q.disabled = false;
        this.r.disabled = false;
    } else {
        this.q.style.visibility = "";
        this.r.style.visibility = "";
        this.e.style.opacity = "";
    };
    if (Grids.Focused != this && this.FRow) {
        this.JW("", "Click", "Outside", null, null);
    }
    if (Grids.OnEnable) Grids.OnEnable(this);
};
_7m.prototype.Os = function () {
    var S = document.styleSheets;
    if (!S) S = document.getElementsByTagName("link");
    if (!S || !S.length) return null;
    for (var i = 0; i < S.length; i++) if (S[i].title == "GridStyle") return S[i];
    return S[0];
};
_7m.prototype.SetStyle = function (name, Ot) {
    if (!name) {
        name = this.Styles.Style;
        var Ou = 1;
    }
    var S = this.Styles[name];
    if (!S) return;
    if (Grids.OnSetStyle && Grids.OnSetStyle(this, name)) return;
    if (!this.VarHeight) this.RowHeight = null;
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) {
        if (CE[i].Height && !CE[i].Ov) CE[i].Ov = CE[i].Height;
        else CE[i].Height = CE[i].Ov;
    }
    function Fi(Hz, Li, T) {
        if (!Li) return;
        for (var B3 in Li) {
            var BZ = Li[B3],
            Cs = typeof(BZ);
            if (Cs == "number" || Cs == "string" || Cs == "boolean") {
                if (Hz[B3] == null || Hz[B3 + "ByStyle"]) {
                    if (T && (B3 == "Grid" || B3 == "Toolbar" || B3 == "GridIE6" || B3 == "ToolbarIE6")) BZ = T.EH(BZ);
                    Hz[B3] = BZ;
                    Hz[B3 + "ByStyle"] = 1;
                }
            }
        }
    };
    var D8 = this.Styles["Default"];
    if (D8) {
        Fi(this, D8["Cfg"]);
        Fi(this.Img, D8["Img"], this);
        Fi(this.Al, D8["Colors"]);
    }
    Fi(this, S["Cfg"]);
    Fi(this.Img, S["Img"], this);
    Fi(this.Al, S["Colors"]);
    if (S.Prefix) this.Img.Style = S.Prefix;
    if (S.File) {
        var CE = this.Iu(S.File),
        Jf = document.documentElement;
        var MN = Jf.getElementsByTagName("link");
        for (var i = 0; i < MN.length; i++) {
            if (MN[i].rel.toLowerCase() == "stylesheet" && MN[i].href && MN[i].href.indexOf(CE) >= 0) break;
        }
        if (i == MN.length) {
            var P = document.createElement("link");
            P.href = this.EH(CE);
            P.rel = "Stylesheet";
            P.title = "GridStyle";
            var KL = Jf.getElementsByTagName("head")[0];
            if (KL) KL.insertBefore(P, KL.firstChild);
        }
    }
    this.Img.Ib = this.Styles.UsePrefix ? this.Img.Style: "";
    this.Styles.Style = name;
    this.Styles.Changed = false;
    this.AM = null;
    this.Ab = null;
    if (_7V || _7U) {
        if (this.Img["ToolbarIE6"]) {
            this.Img.Toolbar = this.Img["ToolbarIE6"];
            this.Img["ToolbarIE6"] = null;
        }
        if (this.Img["GridIE6"]) {
            this.Img.Grid = this.Img["GridIE6"];
            this.Img["GridIE6"] = null;
        }
    }
    this.D0 = 0;
    if (this.Al["Alternate"]) {
        this.D0 = 1;
        this.D2 = this.Al["AlternateRepeat"];
        if (! (this.D2 > 1)) this.D2 = 2;
    }
    if (!Ou) this.Dz(0);
    if (!Ou) this.SaveCfg();
    this.Ow();
    if (this.Ox) this.Ox();
    if (Grids.OnAfterSetStyle) Grids.OnAfterSetStyle(this, name);
    if (this.NH) {
        this.NH = null;
        this.Mn();
    }
    if (Ot) this.Render(this.GetText("ReRender"));
};
_7m.prototype.EH = function (EG, Oy) {
    if (Oy == null) Oy = this.Cache;
    if (Oy == 3 || !EG || (EG + "").search(/\#|^javascript\:/i) >= 0) return EG;
    var BZ = Oy == 1 ? Component.Version.replace(/\s/g, "") : (Oy == 2 ? this.CacheVersion: Grids.CacheVersion);
    if (BZ == null) return EG;
    return EG + ((EG + "").indexOf('?') >= 0 ? "&": "?") + "_tgc=" + escape(BZ);
};
_7m.prototype.ChangeStyle = function (Oz, style) {
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) CE[i].Height = 0;
    this.XHeader.Height = 0;
    this.RowHeight = 0;
    this.ER(CreateXML(Oz));
    var S = this.Os();
    try {
        if (S && style) S.href = style;
    } catch(B) {};
    this.Render();
};
_7m.prototype.Og = function (row, BJ, IS, Click, Caption) {
    if (!IS && Is(row, BJ + "DefaultsServer")) {
        var T = this;
        this.E1(row, BJ, function (EJ) {
            if (EJ >= 0) T.Og(row, BJ, 1);
        });
        return;
    }
    var Ck = Get(row, BJ + "Defaults");
    if (Ck == null && this.Cols[BJ]) Ck = this.Cols[BJ]["Defaults"];
    if (Grids.OnGetDefaults) Ck = Grids.OnGetDefaults(this, row, BJ, Ck);
    if (!Ck) return false;
    Ck = Ck.split(Ck.charAt(0));
    var T = this,
    B3 = new Array(),
    BZ = new Array(),
    Bg = 0;
    Du = this.CanEdit(row, BJ),
    type = this.GetType(row, BJ);
    var Hv = -1,
    O0 = -1,
    O1 = -1,
    all = -1,
    cursor = null,
    B5 = Get(row, BJ);
    for (var i = 1; i < Ck.length; i++) {
        var Ey = Ck[i];
        if ((Ey + "").charAt(0) == '*') {
            if (Ey == "*All") {
                B3[Bg] = this.GetText("DefaultsAll");
                if (B3[Bg]) all = Bg++;
                continue;
            }
            if (Ey == "*Date") {
                B3[Bg] = this.GetText("DefaultsDate");
                if (B3[Bg]) O0 = Bg++;
                continue;
            }
            if (Ey == "*Button") {
                B3[Bg] = this.GetText("DefaultsButton");
                if (B3[Bg]) Hv = Bg++;
                continue;
            }
            if (Ey == "*Default") {
                B3[Bg] = this.O2(row, BJ, type, Du, row.Def[BJ]);
                if (B3[Bg]) BZ[Bg++] = row.Def[BJ];
                continue;
            }
            if (Ey == "*FilterOff") {
                B3[Bg] = this.GetText("DefaultsFilterOff");
                if (B3[Bg]) O1 = Bg++;
                continue;
            }
            if (Ey.search(/^\*Rows/) >= 0) {
                var P = new Object(),
                Y = new Object(),
                KR = Ey.search("CanFilter") >= 0,
                O3 = Ey.search("Visible") >= 0,
                Bj = Ey.search("Def") >= 0 ? row.Def: null,
                BX = BJ,
                O4 = row.Kind == "Filter" ? 1 : 0;
                function O5(J) {
                    if (KR) {
                        if (! (Get(J, "CanFilter") & 1)) return;
                        if (T.Cols[BJ].CanFilter == 2 && Get(J, BJ + "CanFilter") == 0) return;
                    }
                    if (J != row && (!Bj || J.Def == Bj) && (!O3 || J.Visible)) {
                        if (O4) {
                            var BZ = Get(J, BX + "FilterValue");
                            if (!BZ) BZ = Get(J, BX);
                        } else var BZ = Get(J, BX);
                        if (BZ != null) {
                            if (P[BZ]) P[BZ]++;
                            else {
                                P[BZ] = 1;
                                Y[BZ] = J;
                            }
                        }
                    }
                };
                var O6 = Ey.match(/Col(\w*)\*/);
                if (O6) BX = O6[1];
                if (Ey.search("Variable") >= 0 || Ey.search("All") >= 0) {
                    for (var J = this.GetFirst(); J; J = this.GetNext(J)) O5(J);
                }
                if (Ey.search("Fixed") >= 0 || Ey.search("All") >= 0) {
                    var CE = this.GetFixedRows();
                    for (var J = 0; J < CE.length; J++) O5(CE[J]);
                }
                if (Ey.search("Sibling") >= 0) {
                    var EU = row.parentNode;
                    if (EU.Page) EU = this.XB.firstChild;
                    for (var J = EU.firstChild; J; J = this.GetNextSibling(J)) O5(J);
                }
                var BQ = Ey.match(/\d\d*/),
                CR = new Array(),
                O7 = 0;
                if (BQ) BQ = BQ[0];
                var O8 = 0;
                if (this.Cols[BJ] && this.Cols[BJ].CharCodes) O8 = 1;
                for (var x in P) {
                    var V = new Object();
                    V.BY = P[x];
                    V.BZ = x;
                    V.O9 = x;
                    if (O8) V.O9 = this.UseCharCodes(V.O9, BJ);
                    V.J = Y[x];
                    CR[O7++] = V;
                }
                if (BQ) CR.sort(function (a, Bc) {
                    return a.BY < Bc.BY ? 1 : (a.BY > Bc.BY ? -1 : (a.O9 < Bc.O9 ? -1 : 1));
                });
                else CR.sort(function (a, Bc) {
                    return a.O9 < Bc.O9 ? -1 : 1;
                });
                if (!BQ) BQ = O7;
                for (var j = 0; j < BQ && CR[j]; j++) {
                    B3[Bg] = this.O2(CR[j].J, BX, type, Du);
                    BZ[Bg++] = CR[j].BZ;
                }
                for (var j = 0; j < Bg - BQ; j++) if ((B3[j] + "").indexOf("*+") == 0) B3[j] = "*+" + (B3[j].slice(2) - 1 + BQ);
                if (row.Kind == "Filter") for (var j = 0; j < BQ; j++) if (BZ[j + Bg - BQ] === "") BZ[j + Bg - BQ] = String.fromCharCode(160);
                if (!cursor) for (var j = 0; j < BQ; j++) if (BZ[j + Bg - BQ] == B5) cursor = j + Bg - BQ;
                continue;
            }
        }
        if (type == "Date" && isNaN(Ey - 0)) Ey = (new Date(Ey)).getTime();
        B3[Bg] = this.O2(row, BJ, type, Du, Ey);
        if (row.Kind == "Filter" && Ey === "") Ey = String.fromCharCode(160);
        if (Ey == B5) cursor = Bg;
        BZ[Bg++] = Ey;
    }
    var PA = null;
    if (this.GR(row, BJ)) {
        PA = [];
        var BR = Get(row, BJ);
        if (BR || BR == "0") {
            BR = (BR + "").split(this.Lang.Format.ValueSeparator);
            for (var i = 0; i < BR.length; i++) {
                for (var j = 0; j < Bg; j++) if (BR[i] == BZ[j]) PA[j] = 1;
            }
        }
        if (O0 >= 0) PA[O0] = -1;
        if (O1 >= 0) PA[O1] = -1;
        if (Hv >= 0) PA[Hv] = -1;
        if (all >= 0) PA[all] = -1;
    }
    var D8 = B3.length ? this.ShowMenu(Click ? Click.Row: row, Click ? Click.Col: BJ, B3, null, function (Ce, G) {
        if (G.EditMode) {
            if (G.EndEdit(G.FRow, G.FCol, 0) == -1) return;
        }
        if (typeof(Ce) == "object") {
            var BR = "";
            for (var i = 0; i < Ce.length; i++) {
                if (Ce[i] > 0) BR += T.Lang.Format.ValueSeparator + BZ[i];
            }
            if (BR) BR = BR.slice(1);
            else if (row.Kind == "Filter") {
                Ce = O1;
                row[BJ + "Filter"] = 0;
            }
        } else {
            if (Ce == O0 && T.Hw) {
                T.Hw(row, BJ);
                return false;
            }
            if (Ce == Hv) {
                if (Grids.OnButtonClick) return Grids.OnButtonClick(T, row, BJ);
                return;
            }
            if (Ce == all) {
                BR = "";
                for (var i = 0; i < BZ.length; i++) if (BZ[i] != null && BZ[i] != "*-" && (BZ[i] + "").indexOf("*+") != 0) BR += T.Lang.Format.ValueSeparator + BZ[i];
                if (BR) BR = BR.slice(1);
                BZ[Ce] = BR;
            }
            if (Ce == O1 && row.Kind == "Filter") {
                BZ[Ce] = null;
                row[BJ + "Filter"] = 0;
            }
            var BR = BZ[Ce];
            if (BR - 0 + "" == BR) BR -= 0;
        };
        if (Grids.OnEndEdit && Grids.OnEndEdit(T, row, BJ, true, BR)) return;
        if (Grids.OnValueChanged) BR = Grids.OnValueChanged(T, row, BJ, BR);
        T.SetValue(row, BJ, BR);
        if (T.SaveValues) T.SaveCfg();
        if (T.SortRow) T.SortRow(row, BJ, true);
        if (Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(T, row, BJ);
        T.RefreshCell(row, BJ);
        if (row.Space != null) T.SetScrollBars();
        if (row.Kind == "Filter" && T.DoFilter) T.DoFilter(Ce == O1 ? null: row, BJ);
    },
    this.HV ? 0 : 1, Caption, Click ? Click.P: null, Click ? Click.Y: null, null, PA, null, null, cursor) : this.ShowMenu(row, BJ, [this.GetText("NoMenuItem")], null, function () {},
    1);
    this.Im(this.DS(row, BJ));
    return true;
};
_7m.prototype.ActionShowDefaults = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !BJ) return false;
    var Ck = Get(row, BJ + "Button");
    if (Ck == null && this.Cols[BJ]) Ck = this.Cols[BJ]["Button"];
    if (Ck != "Defaults") return false;
    if (row != this.Hx || BJ != this.Hy) return this.Og(row, BJ);
    return true;
};
_7m.prototype.DoAction = function (row, BJ) {
    var JU = Get(row, BJ + "Action");
    if (!JU) return false;
    if (typeof(JU) != "function") JU = this.B4(JU, row.Cells, 1);
    var F = new TCalc();
    F.Row = row;
    F.Col = BJ;
    F.Grid = this;
    if (row) F["Value"] = row[BJ];
    JU(F);
    return true;
};
_7m.prototype.JW = function (ED, name, target, row, BJ) {
    if (Grids["OnEvent" + ED + name + target]) {
        var PB = Grids["OnEvent" + ED + name + target](this, row, BJ);
        if (PB !== undefined) name = PB;
    }
    if (!name) return false;
    while (1) {
        var PC = ED + name + target;
        if (row && BJ) {
            var BL = row[BJ + "Event" + PC];
            if (!BL && row.Def) BL = row.Def[BJ + "Event" + PC];
            if (BL) BL = this.FN(BL, "Cell [" + row.id + "," + BJ + "] " + PC);
            if (BL && typeof(BL) == "function") {
                var Cu = BL.apply(this);
                if (Cu) return Cu < 100;
            }
        }
        var BL = this.Actions[PC];
        if (BL && typeof(BL) == "string") BL = this.FN(BL, PC);
        if (BL && typeof(BL) == "function") {
            var Cu = BL.apply(this);
            if (Cu) return Cu < 100;
        }
        target = this.Mouse[target + "Parent"];
        if (!target) return false;
        if (target == "Default") target = "";
    }
};
_7m.prototype.ChangeAction = function (name, value) {
    if (!value) {
        this.Actions[name] = null;
        return;
    }
    if (typeof(value) != "function") value = this.FN(value + "", name);
    this.Actions[name] = value;
};
_7m.prototype.ActionShowLink = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !BJ) return false;
    var Ca = this.GetType(row, BJ);
    if (Ca == "Link" || Ca == "Img") {
        var BZ = this.JG(row, BJ);
        if (BZ) {
            if (Grids.OnLinkClick && Grids.OnLinkClick(this, row, BJ, BZ[0], BZ[1])) return true;
            try {
                window.open(BZ[0], BZ[1]);
            } catch(B) {};
            return true;
        }
    }
    return false;
};
_7m.prototype.ActionButtonClick = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !BJ) return false;
    var Ck = Get(row, BJ + "Button");
    if (Ck == null && this.Cols[BJ]) Ck = this.Cols[BJ]["Button"];
    if (Ck != "Button" && Ck != "Img" && Ck != "Html") return false;
    if (Grids.OnButtonClick) {
        if (this.EditMode) this.EndEdit(this.FRow, this.FCol, true);
        Grids.OnButtonClick(this, row, BJ);
    }
    return true;
};
_7m.prototype.ActionShowPopupMenu = function (CE) {
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!row || !BJ) return false;
    var C = this.Cols;
    function Ge(C5) {
        var C0 = Get(row, BJ + C5);
        if (!C0) C0 = Get(row, C5);
        if (!C0 && BJ && C[BJ]) C0 = C[BJ][C5];
        return C0;
    };
    var C0 = Ge("Menu");
    if (!C0) return false;
    function PD(Ce, G, row, BJ, PE) {
        if (Grids.OnContextMenu) return ! Grids.OnContextMenu(G, row, BJ, Ce, C0[Ce]);
        alert("No OnContextMenu event defined");
        return true;
    };
    C0 = C0.split(C0.charAt(0));
    var PF = Ge("MenuIcons");
    if (PF) PF = PF.split(PF.charAt(0));
    var Jw = this.JA != "Mouse";
    this.ShowMenu(row, BJ, C0, PF, PD, 0, Ge("MenuCaption"), Jw ? null: this.JB - 5, Jw ? null: this.JC - 5);
    return true;
};
_7m.prototype.ActionShowHelp = function () {
    try {
        window.open(this.EH(this.HelpFile));
    } catch(B) {};
};
_7m.prototype.Iu = function (name) {
    if (!name) return this.As;
    name += "";
    if (name.search(/^\s*http\:\/\/|^\s*javascript\:/i) >= 0) return name;
    return this.As + name;
};
_7m.prototype.GroupRows = function (Cols, PG) {
    if (!this.Grouping) return false;
    if (this.Paging == 3 && !(this.OnePage & 4) && !this.CK()) return;
    var T = this;
    function PH() {
        T.DoGrouping(Cols, PG, null, function (Cu) {
            T.SaveCfg();
            if (Cu) {
                T.HideMessage();
                T.ShowPages();
            }
        });
    };
    if (this.RowCount < this.SynchroCount && !this.Paging) PH();
    else {
        this.ShowMessage(this.GetText("Group"));
        setTimeout(PH, 10);
    }
};
_7m.prototype.DoGrouping = function (Cols, PG, Main, D7) {
    if (!this.Grouping) return false;
    if (typeof(Cols) == "string") Cols = Cols.split(',');
    if (typeof(PG) == "string") PG = PG.split(',');
    if (Cols && !Cols[0]) Cols = null;
    if (Grids.OnGroup && Grids.OnGroup(this, Cols, PG, Main)) return;
    if (!this.DoSort) {
        _7w("Sort");
        return;
    }
    this._7Aq = new Array();
    for (var i = 0; i < this.AZ; i++) {
        var BJ = this.GroupCols[i];
        if (BJ != this.MainCol && this.Cols[BJ].CanGroup != 2) this._7Aq[this._7Aq.length] = this.GroupCols[i];
    }
    var PJ = this.Paging == 3 && (!(this.OnePage & 4) || this.AllPages);
    var PK = !Cols && (this.GroupFlags & 2 && this._7Ar != null && (this.AX != this._7Ar || this.SortCols.join(',') != this._7As.join(',') || this.SortTypes.join(',') != this._7At.join(',')));
    if (this.AZ) {
        if (PJ) {
            this.GroupCols = new Array();
            this.GroupTypes = new Array();
            this.AZ = 0;
            this.PL();
        } else this.PM(Cols && Cols.length || PK, D7);
    }
    this.GroupCols = new Array();
    this.GroupTypes = new Array();
    if (Cols) {
        this.PN();
        for (var i = 0, Bg = 0; i < Cols.length; i++) {
            var BY = this.Cols[Cols[i]];
            if (!BY) continue;
            this.GroupCols[Bg] = Cols[i];
            this.GroupTypes[Bg] = PG ? PG[i] : BY.GroupType;
            if (!this.GroupTypes[Bg]) this.GroupTypes[Bg] = 0;
            Bg++;
        }
        this.AZ = Bg;
        this.PO();
        if (Main && this.Cols[Main]) this.MainCol = Main;
    } else {
        this.AZ = 0;
        if (PK) {
            this.AX = this._7Ar;
            this._7Ar = null;
            this.SortCols = CopyObject(this._7As);
            this.SortTypes = CopyObject(this._7At);
            this.PP();
            var Cu = this.PQ();
            if (typeof(D7) == "function") D7(Cu);
        }
    };
    if (!this.AZ && !PJ) {
        if (D7 && typeof(D7) == "function") D7(true);
        return;
    }
    if (this.FRow && this.FRow.Def && this.FRow.Def.Group) {
        this.Focus(null, null);
    }
    if (PJ) {
        this.Calculate(1, 0, 0, 1);
        var PR = this.PS();
        this.SetScrollBars();
        if (this.AZ) {
            this.PT(this.SortCols, this.SortTypes);
            this.PP();
        }
        this.SaveCfg();
        this.ReloadBody(null, PR, "Group");
    } else this.PU(0, D7);
};
_7m.prototype.PN = function () {
    if (this.GroupFlags & 2 && this._7Ar == null) {
        this._7Ar = this.AX;
        this._7As = CopyObject(this.SortCols);
        this._7At = CopyObject(this.SortTypes);
    }
};
_7m.prototype.PV = function () {
    var PW = this.GroupCols.join(",");
    var BL = new Array(),
    Bg = 0,
    IQ = new Array(),
    PX = 0,
    D8 = this.Def;
    for (var Bj in D8) {
        if (!D8[Bj].Group) continue;
        var PY = D8[Bj].GroupCols;
        if (PY == null) IQ[PX++] = D8[Bj];
        else if (!PY) continue;
        else {
            PY = PY.split(PY.charAt(0));
            for (var i = 1; i < PY.length; i++) {
                if (PW.search(new RegExp("^" + PY[i].replace(/\./g, "\\.").replace(/\*/g, ".*") + "$", "g")) >= 0) {
                    BL[Bg++] = D8[Bj];
                    break;
                }
            }
        }
    }
    for (var i = 0; i < PX; i++) if (IQ[i].Name != "Group") BL[Bg++] = IQ[i];
    for (var i = 0; i < PX; i++) if (IQ[i].Name == "Group") BL[Bg++] = IQ[i];
    this.Ak = BL;
};
_7m.prototype.PZ = function (KN) {
    var Def = this.Ak,
    Pa = null,
    BY = this.GroupCols[KN];
    for (var i = 0; i < Def.length; i++) {
        var I = Def[i].GroupCol;
        if (I == null) {
            if (!Pa) Pa = Def[i];
        }
        I = (I + "").split(",");
        for (var j = 0; j < I.length; j++) {
            if (KN == I[j] || BY == I[j] || KN == this.GroupCols.length + (I[j] - 0)) return Def[i];
        }
    }
    var D8 = this.Cols[BY].GroupDef;
    if (D8 && Def[D8]) return Def[D8];
    if (Pa) return Pa;
    return this.Def["Group"];
};
_7m.prototype.PO = function () {
    this.PV();
    var D8 = this.Ak;
    for (var i = 0; i < D8.length; i++) if (D8[i].Action) {
        var JU = this.B4(D8[i].Action, null, 1);
        var F = new TCalc();
        F.Row = D8[i];
        F.Grid = this;
        JU(F);
        break;
    }
    for (var i = 0; i < D8.length; i++) if (D8[i].GroupMain && this.Cols[D8[i].GroupMain]) {
        this.MainCol = D8[i].GroupMain;
        return;
    }
    if (this.GroupMain && this.Cols[this.GroupMain]) {
        this.MainCol = this.GroupMain;
        return;
    }
    if (this.MainCol) return;
    var Main = this.ColNames[0][0];
    if (Main == "Panel") Main = this.ColNames[0][1];
    if (!Main) Main = this.ColNames[1][0];
    this.MainCol = Main;
};
_7m.prototype.PL = function () {
    this.MainCol = this._7Au;
    this.XHeader.CDef = this.RootCDef;
    this.XHeader[this.MainCol + "NumberSort"] = this.Pb;
    if (this.UngroupAction) {
        var JU = this.B4(this.UngroupAction, null, 1);
        var F = new TCalc();
        F.Grid = this;
        JU(F);
    }
};
_7m.prototype.PT = function (Pc, Pd) {
    var Iv = new Array(),
    EI = new Array(),
    Pe = this.GroupCols.length,
    Bg = 0;
    if (this.GroupFlags & 12) {
        Iv[Bg] = this.MainCol;
        EI[Bg] = this.GroupFlags & 4 ? 0 : 1;
        Bg++;
    }
    for (var i = 0; i < Pc.length; i++) {
        for (var j = 0; j < Pe; j++) if (Pc[i] == this.GroupCols[j] && Pc[i] != this.MainCol || Pc[i] == this.MainCol && this.GroupFlags & 12) break;
        if (j == Pe) {
            Iv[Bg] = Pc[i];
            EI[Bg] = Pd[i];
            if (++Bg == 3) break;
        }
    }
    this.SortCols = Iv;
    this.SortTypes = EI;
    this.AX = Iv.length;
    this.Cb();
    return Bg != i;
};
_7m.prototype.PU = function (Pf, D7) {
    if (!this.DoSort) {
        _7w("Sort");
        return;
    }
    if (!this.Grouping) return;
    if (!this.Grouped) {
        if (D7) D7(true);
        return;
    }
    var Def = this.Def,
    Pg = this.Paging;
    var Ko = this.Paging && this.OnePage & 4 && !this.AllPages ? this.GetFPage() : null;
    this.Paging = 0;
    var Pc = CopyObject(this.SortCols),
    Pd = CopyObject(this.SortTypes);
    for (var KN = 0; KN < this.GroupCols.length; KN++) {
        var D8 = null,
        BY = this.GroupCols[KN],
        C = this.Cols[BY];
        if (!C) {
            this.GroupCols = new Array();
            this.PM(Pf, D7);
            return;
        }
        var D8 = this.PZ(KN);
        if (Grids.OnGetGroupDef) D8 = Def[Grids.OnGetGroupDef(this, BY, D8.Name)];
        var Ph = D8.EditCols;
        if (Ph) Ph = (Ph + "").split(',');
        this.Pi(Ko, this.GroupCols[KN], this.GroupTypes[KN], D8, Ph, KN != 0);
    }
    var FV = this.MaxGroupLength,
    EW = Grids.A8;
    function Pj(row) {
        if (row.Def && row.Def.Group && row.childNodes.length > FV) {
            var J = _7f.createElement("I");
            row.parentNode.insertBefore(J, row);
            for (var i in row) if (!EW[i]) J[i] = row[i];
            for (var i = 0; i < FV; i++) J.appendChild(row.firstChild);
            Pj(J);
            Pj(row);
        } else {
            for (var J = row.firstChild; J; J = J.nextSibling) if (J.firstChild) Pj(J);
        }
    };
    if (FV > 1) Pj(Ko ? Ko: this.XB.firstChild);
    this.Paging = Pg;
    if (this.PT(Pc, Pd) && !Pf) this.PP();
    if (Pf) {
        this.Calculate();
        for (var i = 0; i < this.AZ; i++) {
            var BJ = this.GroupCols[i];
            if (BJ != this.MainCol && this.Cols[BJ].CanGroup != 2) this.Cols[this.GroupCols[i]].Visible = 0;
        }
        this.Pk(0);
    } else if (D7) {
        var T = this;
        function Pl() {
            var Cu = T.PQ();
            if (typeof(D7) == "function") D7(Cu);
        };
        if (this.RowCount < this.SynchroCount) Pl();
        else setTimeout(Pl, 10);
    } else this.PQ();
};
_7m.prototype.PQ = function () {
    this.Calculate(1, 1, 0, 0, 1);
    if (Grids.OnGroupFinish) Grids.OnGroupFinish(this);
    var Ko = this.Paging && this.OnePage & 4 && !this.AllPages ? this.GetFPage() : null;
    if (this.Sorting && this.Sorted && this.AX) {
        if (Ko) this.Pm(Ko);
        else {
            this.DoSort();
            for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) this.Pn(Bg);
        }
    } else {
        if ((this.Paging == 1 || this.Paging == 2) && !Ko) this.CreatePages();
        if (this.ChildPaging) this.EY(this.XB);
    };
    if (this.FRow && (!this.FRow.parentNode || !this.FRow.parentNode.tagName)) this.FRow = null;
    if (this.Kk) this.Kk(1);
    this.SetVPos();
    this.Po(0, 1);
    this.Dz(0);
    if (this.Gantt) this.CH(0);
    if (this.PS()) {
        this.Render();
        return false;
    } else {
        if (this.Paging == 2 || this.Paging == 1 && !this.AllPages) this.Ku();
        else this.UpdateBody();
        return true;
    }
};
_7m.prototype.PS = function () {
    this.Clear(0);
    this.Rendering = 1;
    var BL = new Array(),
    Bg = 0;
    for (var i = 0; i < this.AZ; i++) {
        var BJ = this.GroupCols[i];
        if (BJ != this.MainCol && this.Cols[BJ].CanGroup != 2) BL[Bg++] = this.GroupCols[i];
    }
    var PR = this.ChangeColsVisibility ? this.ChangeColsVisibility(this._7Aq, BL, 1) : 0;
    this.Pk(1);
    this._7Aq = null;
    this.Rendering = 0;
    if (!PR) {
        this.PP();
        var D3 = this.GetSections();
        for (var i = D3[0]; i < D3[1]; i++) this.DZ(i);
    }
    return PR;
};
_7m.prototype.Pk = function (refresh) {
    for (var BJ in this.Cols) {
        var C = this.Cols[BJ];
        if (C.Pp) {
            if (C.Visible && refresh) this.SetWidth(BJ, C.Pp - C.Width, 0, 0, 1);
            else C.Width = C.Pp;
            C.Pp = null;
        }
    }
    var Ee = this.Cols[this.MainCol];
    if (Ee && Ee.GroupWidth) {
        Ee.Pp = Ee.Width;
        if (Ee.Visible && refresh) this.SetWidth(this.MainCol, Ee.GroupWidth - Ee.Width, 0, 0, 1);
        else Ee.Width = Ee.GroupWidth;
    }
    if (this.GroupFlags & 1 && this.Ox) this.Ox(refresh);
};
_7m.prototype.Pi = function (row, BJ, type, D8, Pq, Pr) {
    if (!this.Cols[BJ]) {
        alert("Unknown grouping column " + BJ);
        return;
    }
    this.AX = 1;
    this.SortCols = [BJ];
    this.SortTypes = [type & 14];
    if (!row) {
        if (!Pr) {
            var Dh = this.Sorted;
            this.Sorted = true;
            this.DoSort(0, 1);
            this.Sorted = Dh;
        }
        row = this.XB.firstChild;
    } else {
        var Ps = row.MaxChildren;
        row.MaxChildren = 1e10;
        this.Pm(row, 0, 1);
        row.MaxChildren = Ps;
    };;;
    if (!D8) D8 = this.Def["Group"];
    var Ix = "@Start#",
    BQ = 0,
    V, C = this.Cols[BJ],
    BZ,
    Pt = D8[BJ + "MaxChars"];
    var M = this.MainCol,
    Pu = M + "Type",
    Pv = M + "Format",
    ME = M + "Enum",
    Pw = M + "IntFormat",
    Px = M + "Visible",
    Py = M + "Related",
    Pz = M + "Refresh",
    P0 = M + "Range",
    P1 = M + "CanEdit";
    var P2 = BJ + "Type",
    Dv = BJ + "Format",
    Du = BJ + "Enum",
    KN = BJ + "IntFormat",
    O9 = BJ + "Visible",
    P3 = BJ + "Related",
    P4 = BJ + "Refresh",
    P5 = BJ + "Range";
    var J = row.firstChild;
    var P6 = D8.ParentCDef != null,
    Ek = row.CDef,
    P7 = 1;
    var EU = row.Page ? this.XHeader: row;
    while (J) {
        if (J.Def && J.Def.Group || !Is(J, "CanGroup")) {
            this.Pi(J, BJ, type, D8, Pq);
            J = J.nextSibling;
        } else if (J.Deleted && (!this.ShowDeleted || this.GroupTypes & 128)) {
            J = J.nextSibling;
        } else {
            if (P6) {
                EU.CDef = D8.ParentCDef;
                P6 = 0;
            }
            if (P7) {
                var B3 = Get(EU, BJ + "NumberSort");
                EU[M + "NumberSort"] = B3 == null ? C.Type == "Int" || C.Type == "Float" || C.Type == "Date": B3;
                P7 = 0;
            }
            var BR = Get(J, BJ + "SortValue");
            if (BR == null) BR = this.GetString(J, BJ, 2);
            if (Grids.OnGetSortValue) BR = Grids.OnGetSortValue(this, J, BJ, BR, 0, 1);
            var B3 = J.nextSibling;
            if (C.CharCodes) BR = this.UseCharCodes(BR, BJ);
            if (type & 6) BR = type & 2 ? BR.toLocaleLowerCase() : BR.toLowerCase();
            if (type & 8) BR = BR.replace(this.Cols[BJ].WhiteChars, "");
            if (type & 16 && !BR && BR != 0 || type & 64 && BR == 0) {
                J = B3;
                continue;
            }
            if (Pt) BR = (BR + "").slice(0, Pt);
            if (Ix != BR) {
                BQ++;
                V = _7f.createElement("I");
                row.insertBefore(V, J);
                V.Def = D8;
                V.CDef = Ek;
                V.Kind = D8.Kind;
                V.Expanded = D8.Expanded ? 1 : 0;
                V.Spanned = D8.Spanned ? 1 : 0;
                V.Calculated = D8.Calculated ? 1 : 0;
                V.Level = row.Level + 1;
                V.State = 4;
                V.Visible = 1;
                if (Pt) {
                    V[M] = BR;
                    V[P1] = 0;
                } else V[M] = Get(J, BJ);
                BZ = Get(J, P2);
                if (BZ == null) BZ = C.Type;
                if (BZ != null) V[Pu] = BZ;
                BZ = Get(J, Dv);
                if (BZ == null) BZ = C.Format;
                if (BZ != null) V[Pv] = BZ;
                BZ = Get(J, Du);
                if (BZ == null) BZ = C.Enum;
                if (BZ != null) V[ME] = BZ;
                BZ = Get(J, KN);
                if (BZ == null) BZ = C.P8;
                if (BZ != null) V[Pw] = BZ;
                BZ = Get(J, P5);
                if (BZ != null) V[P0] = BZ;
                BZ = Get(J, P4);
                if (BZ == null) BZ = C.Refresh;
                if (BZ != null) V[Pz] = BZ;
                BZ = Get(J, P3);
                if (BZ == null) BZ = C.Related;
                if (BZ) {
                    var P9 = BZ.split(",");
                    var QA = this.GetEnum(J, P9[0]),
                    QB = QA.length,
                    QC = P9.length;
                    for (var i = 0; i < QC; i++) V[P9[i]] = Get(J, P9[i]);
                    if (QC == 1) {
                        for (var i = 0; i < QB; i++) {
                            var QD = Get(J, Du + i);
                            if (!QD) QD = C["Enum" + i];
                            if (QD) V[ME + i] = QD;
                        }
                    } else if (QC == 2) {
                        var QE = this.Cols[P9[1]];
                        for (var i = 0; i < QB; i++) {
                            var QF = Get(J, Du + i);
                            if (!QF) QF = QE["Enum" + i];
                            if (!QF) continue;
                            var QG = QF.length,
                            QH = Du + i + "_",
                            QI = "Enum" + i + "_",
                            QJ = ME + i + "_";
                            for (var j = 0; j < QG; j++) {
                                var QD = Get(J, QH + j);
                                if (!QD) QD = C[QI + j];
                                if (QD) V[QJ + j] = QD;
                            }
                        }
                    }
                    V[Py] = BZ;
                }
                if (Pq) {
                    for (var i = 0; i < Pq.length; i++) {
                        var BY = Pq[i];
                        if (BY == "Main" || BY == "MainCol") {
                            V[M + "CopyTo"] = "Children," + BJ;
                            V[M + "CanEdit"] = 1;
                            V[BJ] = J[BJ];
                        } else {
                            V[BY + "CopyTo"] = "Children," + BY;
                            V[BY + "CanEdit"] = 1;
                            V[BY] = J[BY];
                        }
                    }
                    V._7Av = 1;
                }
                V[Px] = 1;
                Ix = BR;
            }
            V.appendChild(J);
            J.Level = V.Level + 1;
            J = B3;
        }
    }
    if (V && type & 32 && BQ == 1) {
        while (V.firstChild) row.insertBefore(V.firstChild, V);
        row.removeChild(V);
    }
};
_7m.prototype.PM = function (Pf, D7) {
    var PW = this.GroupCols;
    if (!PW || PW.length == 0 || !this.Grouping) return;
    if (!this.Grouped) {
        if (D7) D7(true);
        return;
    }
    this.PL();
    this.Clear(0);
    this.QK();
    this.AZ = 0;
    this.GroupCols = new Array();
    this.GroupTypes = new Array();
    if (Pf) return;
    if (D7) {
        var Cu = this.PQ();
        if (typeof(D7) == "function") D7(Cu);
    } else this.PQ();
};
_7m.prototype.QK = function () {
    var J = this.GetFirst(),
    B3;
    while (J) {
        B3 = this.GetNext(J);
        if (J.Def && J.Def.Group) {
            var EU = J.parentNode;
            while (J.firstChild) EU.insertBefore(J.firstChild, J);
            EU.removeChild(J);
        }
        J = B3;
    }
};
_7m.prototype.QL = function (CE, type) {
    var BJ = CE ? this.FCol: this.ACol;
    if (!BJ || !this.Cols[BJ] || !this.Cols[BJ].CanGroup) return false;
    for (var i = 0; i < this.AZ; i++) if (this.GroupCols[i] == BJ) return false;
    var Cols = CopyObject(this.GroupCols),
    PG = CopyObject(this.GroupTypes);
    if (type == 0) {
        Cols.unshift(BJ);
        PG.unshift(this.Cols[BJ].GroupType);
    } else if (type == 1) {
        Cols[this.AZ] = BJ;
        PG[this.AZ] = this.Cols[BJ].GroupType;
    }
    this.GroupRows(Cols, PG);
    return true;
};
_7m.prototype.ActionGroupBy = function (CE) {
    return this.QL(CE, 0);
};
_7m.prototype.ActionGroupByLast = function (CE) {
    return this.QL(CE, 1);
};
_7m.prototype.ActionUngroupBy = function (CE) {
    var BJ = CE ? this.FCol: this.ACol;
    if (!BJ || !this.Cols[BJ] || !this.Cols[BJ].CanGroup) return false;
    var Cols = new Array(),
    PG = new Array();
    for (var i = 0; i < this.AZ; i++) {
        if (this.GroupCols[i] == BJ) break;
        Cols[i] = this.GroupCols[i];
        PG[i] = this.GroupTypes[i];
    }
    if (i == this.AZ) return false;
    for (i++; i < this.AZ; i++) {
        Cols[i - 1] = this.GroupCols[i];
        PG[i - 1] = this.GroupTypes[i];
    }
    this.GroupRows(Cols, PG);
    return true;
};
_7m.prototype.ActionGroupOff = function () {
    if (!this.Grouping || !this.Grouped) return false;
    var PJ = this.Paging == 3 && (!(this.OnePage & 4) || this.AllPages);
    if (PJ) {
        this.Grouped = false;
        for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Group") this.K2(J);
        this.SaveCfg();
        this.ReloadBody(null, false, "Group");
        return true;
    }
    if (this.AZ) {
        this.PL();
        this.Clear(0);
        this.QK();
        var Dh = this.GroupCols;
        this.GroupCols = [];
        this.AZ = 0;
        this._7Aq = Dh;
        this.PQ();
        this.GroupCols = Dh;
        this.AZ = Dh.length;
    }
    this.Grouped = false;
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Group") this.K2(J);
    this.SaveCfg();
    return true;
};
_7m.prototype.ActionGroupOn = function () {
    if (!this.Grouping || this.Grouped) return false;
    this.Grouped = true;
    var PJ = this.Paging == 3 && (!(this.OnePage & 4) || this.AllPages);
    if (PJ) {
        for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Group") this.K2(J);
        this.SaveCfg();
        this.ReloadBody(null, false, "Group");
        return true;
    }
    if (this.AZ) {
        this.PO();
        this.PU();
    }
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Group") this.K2(J);
    this.SaveCfg();
    return true;
};
_7m.prototype.PP = function (DR, QM) {
    if (this.Rendering && DR != -1) return;
    var D3 = this.GetSections(),
    QN = D3[0],
    QO = D3[1];
    if (DR == null) {
        for (DR = QN; DR < QO; DR++) this.PP(DR);
        this.UpdateRowHeight(this.XHeader);
        return;
    }
    var KL = this.XHeader,
    BQ = KL.r1.firstChild.rows.length;
    if (DR != -1) {
        for (k = 0; k < BQ; k++) KL["r" + DR].firstChild.rows[k].onmousemove = null;
        KL["r" + DR].onmousemove = null;
        this.GetRow(KL, DR).innerHTML = this.IK(KL, DR);
        KL["r" + DR].row = KL;
        KL["r" + DR].onmousemove = new Function("var A = Grids.Active;if(A){ A.ARow = this.row; A.ASec = 0;}");
        QN = DR;
        QO = DR + 1;
        if (!QM) this.UpdateRowHeight(KL);
    }
    if (KL.Rows > 1) for (k = 0; k < BQ; k++) {
        for (i = QN; i < QO; i++) KL["r" + i].firstChild.rows[k].onmousemove = new Function(this.Z + ".XHeader.ARow=" + k + ";");
        if (k == KL.Main) continue;
        var max = 0;
        for (i = QN; i < QO; i++) {
            var CZ = KL["r" + i].firstChild.rows[k].offsetHeight;
            if (CZ > max) max = CZ;
        }
        for (i = QN; i < QO; i++) {
            var J = KL["r" + i].firstChild.rows[k];
            if (J.offsetHeight < max) J.style.height = max + "px";
        }
    }
};
_7m.prototype.KC = function (D4, QP, QQ, OG) {
    if (!OG && (!D4 || Grids.Az)) return;
    if (Grids.Jr) {
        if (Grids.Jr == D4) return;
        try {
            Grids.Jr.className = Grids.Js;
        } catch(B) {}
    }
    D4.className = QQ;
    Grids.Jr = D4;
    Grids.Js = QP;
};
_7m.prototype.J9 = function (Click, IL) {
    var BJ = Click.Col;
    if (!BJ) return false;
    var Nv = "";
    if (this.ColResizing && this.QR(BJ, Click.P)) Nv = "e-resize";
    this.QS(BJ, Nv);
    if (this.Hover >= 1 && BJ != "Pager") {
        if (this.XHeader.Rows > 1 && this.XHeader.ARow != this.XHeader.Main) {
            if (this.XHeader.ARow != null) {
                var IU = this.QT(this.XHeader.ARow, BJ);
                var Hb = this.Img.Style + "HeaderMulti" + (IU.className.indexOf("Even") >= 0 ? "Even": "");
                this.KC(IU, Hb, Hb + "Hover");
            }
        } else {
            var Pr = !this.Sorting || this.SortIcons == 2 || this.Cols[BJ] && !this.Cols[BJ].CanSort;
            var Hb = this.XHeader[BJ + "ClassOuter"];
            if (!Hb) Hb = this.Img.Style + (BJ != "Panel" ? Pr ? "HeaderNoSort": "Header": "PanelTop");
            else Hb = this.Img.Ib + Hb;
            var QU = this.XHeader[BJ + "ClassOuterHover"];
            if (!QU) QU = this.Img.Style + (BJ != "Panel" ? Pr ? "HeaderNoSortHover": "HeaderHover": "PanelTopHover");
            else QU = this.Img.Ib + QU;
            this.KC(this.GetCell(this.XHeader, BJ), Hb, QU);
        }
    }
    return true;
};
_7m.prototype.QS = function (BJ, Nv, QV) {
    if (BJ < 0) return;
    if (this.XHeader.Rows > 1 && BJ != "Pager") {
        for (var i = 0; i < this.XHeader.Rows; i++) {
            var QW = this.QT(i, BJ);
            if (QW) QW.style.cursor = Nv;
        }
        return;
    }
    var QW = this.GetCell(this.XHeader, BJ);
    if (!QW) return;
    QW.style.cursor = Nv;
    var I0 = _7s(QW, "div")[0];
    if (I0) I0.style.cursor = Nv;
    var Oz = _7s(QW, "img")[0];
    if (Oz) Oz.style.cursor = QV ? Nv: "";
};
_7m.prototype.ActionColResize = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BJ = this.QR(D8.Col, D8.J6),
    C = this.Cols[BJ];
    if (!BJ) return false;
    if (BJ == "Pager") {
        if (!this.Pager.CanResize) return true;
    } else if (!this.ColResizing || !C.CanResize) return true;
    D8.QX = BJ;
    this.CloseDialog();
    if (BJ == "Pager" || this.Paging && this.AllPages || (!this.Paging || !this.AllPages) && this.RowCount > this.SynchroCount || this.ColNames[C.K].length - 1 == C.Pos && this.RowCount > 20) {
        this.OE(D8.P, D8.Y, BJ == "Pager");
        D8.CA = false;
    } else D8.CA = true;
    if (BJ != "Pager") this.GetCell(this.XHeader, BJ).firstChild.className = this.Img.Style + "HeaderFocus";
    D8.Action = "ColResize";
    D8.Move = this.QY;
    D8.II = this.QZ;
    return true;
};
_7m.prototype.QY = function (Click) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BJ = D8.QX,
    C = this.Cols[BJ],
    Ik = D8.IQ - D8.P;
    this.K2(this.XHeader);
    if (BJ && BJ != "Pager") {
        if (C.RelWidth) {
            C.Cj = C.RelWidth;
            C.RelWidth = null;
        }
        if (C.K != 1) {
            if ((this.b[0] ? this.b[0].offsetWidth: 0) + (this.b[2] ? this.b[2].offsetWidth: 0) + (this.HV ? -Ik: Ik) + _7Y + 40 > this.MainTag.offsetWidth) return false;
        }
    }
    if (D8.CA) this.SetWidth(BJ, this.HV ? -Ik: Ik, 0);
    else if (BJ && BJ != "Pager" && C.Ci) C.Ci = false;
    if (D8.CA) D8.P += Ik;
    else D8.Q = Ik;
    if (BJ) this.QS(BJ, "e-resize");
    if (D8.Col && D8.Col != "Pager" && (C.RelWidth || C.Ci)) {
        this.QZ();
        Grids.Az = null;
    }
    return true;
};
_7m.prototype.QZ = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BJ = D8.QX,
    C = this.Cols[BJ];
    var Ik = D8.CA ? 0 : D8.IQ - D8.P;
    if (BJ == "Pager") {
        this.Pager.Width += Ik;
        if (this.Pager.Width <= 10) this.Pager.Width = 10;
        this.Dg();
        this.f.style.width = this.Pager.Width + "px";
        this.SaveCfg();
        this.SetScrollBars();
    } else {
        this.GetCell(this.XHeader, BJ).firstChild.className = "";
        if (this.RowCount < this.SynchroCount) {
            Grids.Az = null;
            this.Qa(BJ, Ik, D8.Col);
        } else {
            this.ShowMessage(this.GetText("ColWidth") + " '" + this.XHeader[BJ] + "'");
            status = this.GetText("ColWidth");
            this.QS(D8.Col, "wait");
            var T = this;
            setTimeout(function () {
                T.Qa(BJ, Ik, D8.Col);
            },
            10);
        };
        this.Af = (new Date).getTime() + 100;
    };
    return true;
};
_7m.prototype.Qa = function (BJ, Ik, QW) {
    this.SetWidth(BJ, Ik, 1);
    this.SaveCfg();
    this.HideMessage();
    this.QS(QW, "");
    if (!_7F) this.PP();
    status = "";
};
_7m.prototype.QR = function (BJ, x) {
    var Ik = this.Mouse.EdgeSize;
    var C = this.Cols;
    if (BJ == "Pager") {
        x -= this.HV ? Ik: this.Pager.Width;
        if (x >= -Ik && x < 0 && this.Pager.CanResize) return BJ;
        return null;
    }
    if (x < Ik) {
        if (!BJ || BJ == "Panel") return null;
        if (!this.HV) BJ = this.GetPrevCol(BJ);
    } else {
        if (BJ && C[BJ].K == 1 && C[BJ].Width >= this.b[1].offsetWidth) {
            x += this.GetScrollLeft();
        }
        if (!BJ || C[BJ].Width - x >= Ik) return null;
    };
    if (!BJ || !C[BJ].CanResize) return null;
    return BJ;
};
_7m.prototype.ActionDropColMove = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BJ = D8.Col,
    C = this.Cols[BJ];
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, 1) == -1) return false;
    var IL = {
        clientX: D8.P,
        clientY: D8.Y
    };
    var row = D8.Row,
    Qb = D8.Col;
    var I = this.Qc(row, Qb, IL);
    var BR = row[Qb];
    if (!BR) return false;
    BR = (BR + "").split(',');
    if (I < 0 || I >= BR.length) return false;
    D8.Col = BR[I];
    D8.Hy = Qb;
    D8.Qd = Qb;
    D8.Row = row;
    D8.Qe = row;
    D8.Qf = Qb;
    D8.K = this.Cols[D8.Col].K;
    D8.Qg = I;
    if (_7F) {
        var Bs = this.Qh(D8.Row, D8.Qd, I);
        if (Bs) Bs.innerHTML = Bs.innerHTML;
    }
    var IU = this.Qh(D8.Row, D8.Hy, D8.Qg);
    IU.className = this.Img.Style + "HeaderGroupFocus";
    this.IM(IU, IL, 3, null);
    D8.Action = "DropColMove";
    D8.Move = this.Qi;
    D8.II = this.Qj;
    return true;
};
_7m.prototype.ActionColMove = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BJ = D8.Col,
    C = this.Cols[BJ];
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, 1) == -1) return false;
    if (BJ == "Pager" || !this.ColMoving || C.CanMove == 0 || this.XHeader.Rows > 1 && this.XHeader.ARow != this.XHeader.Main || !C.Visible) return false;
    if (C.K == 1) {
        var Cq = this.ColNames[1],
        BQ = 0;
        for (var i = 0; i < Cq.length; i++) if (this.Cols[Cq[i]].Visible) BQ++;
        if (BQ <= 1) return false;
    }
    var IU = this.GetCell(this.XHeader, BJ);
    IU.firstChild.className = this.Img.Style + "HeaderFocus";
    var EK;
    if (C.Qk && (!this.Ql || !C.CanGroup)) {
        EK = _7A + _7B + "<tr>";
        for (var BY = BJ; BY && this.Cols[BY].Group == this.Cols[BJ].Group; BY = this.GetNextCol(BY)) {
            EK += "<td class='" + this.Img.Style + "Header' style='cursor:url(\"" + this.Img.DragMore + "\");'>" + this.Il(this.XHeader, BY) + "</td>";
        }
        EK += "</tr>" + _7C;
    }
    if (BJ) this.QS(BJ, EK ? this.Img.DragMore: this.Img.DragOne);
    var IL = {
        clientX: D8.P,
        clientY: D8.Y
    };
    this.IM(IU, IL, this.Ql ? 3 : 1, EK);
    D8.Action = "ColMove";
    D8.Move = this.Qi;
    D8.II = this.Qj;
    D8.K = C.K;
    D8.Row = this.XHeader;
    D8.Qm = -1;
    return true;
};
_7m.prototype.Qi = function (Click) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var IL = {
        clientX: D8.IQ,
        clientY: D8.IR
    };
    var BL = this.GetColLeft(this.XHeader, EventXYToElement(IL, this.e)[0]);
    var NJ = D8.Col,
    BJ = BL[0],
    x = BL[1];
    var Ik = IL.clientX - D8.P,
    type = 0,
    C = this.Cols,
    T = this;
    function Qn() {
        T.Qo(D8.Hy, D8.Qp, D8.Qm, D8.Row);
        D8.Hy = null;
        D8.Qp = null;
        if (D8.Qm != -2) D8.Qm = -1;
    };
    if (this.Ql) {
        if (this.ARow && this.ARow.Space != null && this.ARow[this.ARow.ACol + "Type"] == "DropCols" && C[D8.Col].CanGroup) {
            Qn();
            var BJ = this.ARow.ACol;
            var Ce = this.Qc(this.ARow, BJ, IL);
            var BR = this.ARow[BJ];
            if (BR && Ce >= 0) {
                BR = (BR + "").split(',');
                if (D8.Col == BR[Ce] || D8.Col == BR[Ce - 1]) Ce = -1;
            }
            if (Ce >= 0) {
                var Dt = this.Al["MovingColOutside"];
                if (Dt < 0xfffff) Dt += 0x100000;
                this.Qh(this.ARow, BJ, Ce).style[this.HV ? "borderRight": "borderLeft"] = this.MovingColBorder + "px solid #" + Number(Dt).toString(16);
            }
            if (D8.Qe && D8.Qe != this.ARow) this.Qh(D8.Qe, D8.Qd, D8.Qg).className = this.Img.Style + "HeaderGroupDelete";
            else if (D8.Qg >= 0) T.Qh(D8.Qe, D8.Qd, D8.Qg).className = T.Img.Style + "HeaderGroupFocus";
            D8.Qm = Ce;
            D8.Hy = BJ;
            D8.Row = this.ARow;
            D8.Qd = BJ;
            return true;
        }
        if (D8.Qg >= 0 && D8.Qm != -2 && D8.Hy) {
            this.Qh(D8.Qe, D8.Hy, D8.Qg).className = this.Img.Style + "HeaderGroupDelete";
            Qn();
            D8.Qm = -2;
            return true;
        }
        if (this.ARow != this.XHeader || D8.Qg >= 0) {
            Qn();
            return true;
        }
    }
    if (!BJ || BJ == "Panel" && !this.Cols[BJ].CanMove) {
        Qn();
        return true;
    }
    this.OF(BJ, x);
    var DR = C[BJ].K,
    BX = this.GetSections(),
    Cq = this.ColNames[1],
    Qq = 0;
    if (x > C[BJ].Width / 2) type = 1;
    var G = C[D8.Col].Group,
    BY = type == 1 ? this.GetNextCol(BJ) : this.GetPrevCol(BJ),
    PW = BY ? C[BY].Group: 0,
    Qd = C[BJ].Group;
    if (DR != D8.K && C[D8.Col].CanMove != 2 || BY == D8.Col && DR == D8.K || BJ == D8.Col && (C[BJ].CanMove != 2 || DR != 1 || type == 0 && (Cq[0] != BJ || BX[0] != 1 && BY != "Panel") || type == 1 && (Cq[Cq.length - 1] != BJ || BX[1] != 2))) {
        Qn();
        return true;
    }
    if (C[D8.Col].Qk) {
        if ((PW == G || Qd == G) && D8.Col != BJ) {
            if (C[BJ].CanMove != 2 || type != 1 || C[Cq[Cq.length - 1]].Group != G || BX[1] != 2) {
                Qn();
                return true;
            }
            Qq = 1;
        }
        G = 0;
    }
    if (G) {
        if (type == 0 && PW != G || type == 1 && Qd != G) {
            Qn();
            return true;
        }
    } else {
        if (PW && PW == Qd) {
            Qn();
            return true;
        }
    };
    this.Qo(D8.Hy, D8.Qp, D8.Qm, D8.Row);
    D8.Hy = BJ;
    D8.Qp = type;
    if (D8.Hy) {
        var Dt = this.Al[DR != D8.K || BJ == D8.Col || Qq ? "MovingColOutside": "MovingColInside"];
        if (Dt < 0xfffff) Dt += 0x100000;
        var NG = this.MovingColBorder + "px solid #" + Number(Dt).toString(16);
        var IU = this.GetCell(this.XHeader, D8.Hy);
        if (IU) {
            IU = IU.firstChild;
            if (D8.Qp) IU.style.borderRight = NG;
            else IU.style.borderLeft = NG;
            if (!_7S) {
                if (!IU.Pp) IU.Pp = IU.style.width;
                var CY = parseInt(IU.Pp) - this.MovingColBorder;
                if (CY > 0) IU.style.width = CY + "px";
            }
        }
    }
    D8.Row = this.XHeader;
    this.QS(BJ, this.Img.DragOne, true);
    return true;
};
_7m.prototype.Qj = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BJ = D8.Col,
    C = this.Cols;
    if (D8.Qg >= 0 && this.Qh) this.Qh(D8.Qe, D8.Qf, D8.Qg).className = this.Img.Style + "HeaderGroup";
    else this.GetCell(this.XHeader, D8.Col).firstChild.className = "";
    if (D8.Hy || D8.Qm != -1) {
        this.Qo(D8.Hy, D8.Qp, D8.Qm, D8.Row);
        this.CloseDialog();
        if (D8.Qm != -1) {
            var T = this;
            function Qr(row, BJ, BR) {
                if (Grids.OnValueChanged) BR = Grids.OnValueChanged(T, row, BJ, BR);
                if (T.SetValue(row, BJ, BR, 1) && Grids.OnAfterValueChanged) Grids.OnAfterValueChanged(T, row, BJ);
                T.B6(row);
            };
            if (D8.Qe && (D8.Row != D8.Qe || D8.Qm == -2)) {
                var BR = D8.Qe[D8.Qf];
                BR = BR ? (BR + "").split(',') : new Array();
                var Cols = new Array(),
                k = 0;
                for (var i = 0; i < BR.length; i++) {
                    if (i == D8.Qg) continue;
                    Cols[k++] = BR[i];
                }
                Qr(D8.Qe, D8.Qf, Cols.join(','));
                if (D8.Qm >= 0) {
                    BR = D8.Row[D8.Qd];
                    BR = BR ? (BR + "").split(',') : new Array();
                    Cols = new Array();
                    k = 0;
                    for (var i = 0; i < BR.length; i++) {
                        if (i == D8.Qm) Cols[k++] = D8.Col;
                        Cols[k++] = BR[i];
                    }
                    if (i == D8.Qm) Cols[k++] = D8.Col;
                    Qr(D8.Row, D8.Qd, Cols.join(','));
                }
            } else {
                var BR = D8.Row[D8.Qd];
                BR = BR ? (BR + "").split(',') : new Array();
                var Cols = new Array(),
                k = 0;
                for (var i = 0; i < D8.Qm; i++) {
                    if (i == D8.Qg) continue;
                    Cols[k++] = BR[i];
                }
                Cols[k++] = D8.Col;
                for (var i = D8.Qm; i < BR.length; i++) {
                    if (i == D8.Qg) continue;
                    Cols[k++] = BR[i];
                }
                Qr(D8.Row, D8.Qd, Cols.join(','));
            };
        } else if (!D8.Row || D8.Row == this.XHeader) {
            var DR = C[D8.Hy].K,
            I = C[D8.Hy].Pos,
            BQ = 1,
            Qs = this.ColNames[C[D8.Col].K],
            Bg;
            if (D8.Col == D8.Hy || C[D8.Col].Qk && C[D8.Col].Group == C[D8.Hy].Group) {
                DR = D8.Qp ? 2 : 0;
                I = 0;
            }
            if (C[D8.Col].Qk) {
                for (BQ = 0, Bg = C[D8.Col].Pos; Qs[Bg] && C[Qs[Bg]].Group == C[D8.Col].Group; BQ++, Bg++);
            }
            if (C[D8.Col].K != DR) {
                if (D8.Col == D8.Hy && !D8.Qp) D8.Qp = 1;
                var Qt = this.ColNames[DR];
                var BL = Qs.splice(C[D8.Col].Pos, BQ);
                for (var i = 0; i < BQ; i++) {
                    Qt.splice(I + D8.Qp + i, 0, BL[i]);
                    C[BL[i]].K = DR;
                }
                for (var i = 0; i < Qs.length; i++) C[Qs[i]].Pos = i;
                for (var i = 0; i < Qt.length; i++) C[Qt[i]].Pos = i;
                this.SetVPos();
                this.SaveCfg();
                if (Grids.OnColMove) Grids.OnColMove(this, D8.Col);
                this.Render(this.GetText("ColMove"));
            } else if (C[D8.Col].Qk) {
                var BL = Qs.splice(C[D8.Col].Pos, BQ);
                for (var i = 0; i < BQ; i++) Qs.splice(I + D8.Qp - (Bg < I ? BQ: 0) + i, 0, BL[i]);
                for (var i = 0; i < Qs.length; i++) C[Qs[i]].Pos = i;
                this.SetVPos();
                this.SaveCfg();
                if (Grids.OnColMove) Grids.OnColMove(this, D8.Col);
                this.Render(this.GetText("ColMove"));
            } else if (this.RowCount < this.SynchroCount) this.Qu(D8.Col, D8.Hy, D8.Qp);
            else {
                this.ShowMessage(this.GetText("ColMove") + " '" + this.XHeader[D8.Col] + "'");
                status = this.GetText("ColMove");
                var T = this;
                setTimeout(function () {
                    T.Qu(D8.Col, D8.Hy, D8.Qp);
                },
                10);
            }
        }
        if (_7AJ != 1) {
            var KL = this.XHeader;
            setTimeout(function () {
                _7Ao(KL.r1);
            },
            10);
        }
    }
    this.Af = (new Date).getTime() + 100;
    this.JY = null;
    return true;
};
_7m.prototype.Qu = function (BJ, NJ, type) {
    if (!NJ) return;
    var C = this.Cols,
    S = this.ColNames[C[NJ].K],
    I = C[NJ].Pos + type;
    if (type == 0) {
        while (1) {
            var BY = S[I - 1];
            if (!BY || C[BY].Group == C[BJ].Group) break;
            I--;
        }
    } else if (C[NJ].Group != C[BJ].Group) {
        while (1) {
            var BY = S[I];
            if (!BY || C[BY].Group == C[BJ].Group) break;
            I++;
        }
    }
    this.MoveCol(BJ, I);
    this.PP();
    this.SaveCfg();
    this.HideMessage();
    status = "";
    if (Grids.OnColMove) Grids.OnColMove(this, BJ);
};
_7m.prototype.Qo = function (BJ, type, I, row) {
    if (BJ && (row == this.XHeader || row == null)) {
        var IU = this.GetCell(this.XHeader, BJ);
        if (IU) {
            IU = IU.firstChild;
            if (type) {
                IU.style.borderRightWidth = "";
                IU.style.borderRightStyle = "";
                IU.style.borderRightColor = "";
            } else {
                IU.style.borderLeftWidth = "";
                IU.style.borderLeftStyle = "";
                IU.style.borderLeftColor = "";
            };
            if (IU.Pp) IU.style.width = IU.Pp;
        }
    } else if (I >= 0) {
        var Bs = this.Qh(row, BJ, I);
        if (Bs) {
            if (this.HV) {} else {
                Bs.style.borderLeftWidth = "";
                Bs.style.borderLeftStyle = "";
                Bs.style.borderLeftColor = "";
            }
        }
    }
};
_7m.prototype.ActionGridResize = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    this.CloseDialog();
    if (this.ResizingMain & 2) {
        D8.Width = this.e.offsetWidth + (_7S ? this.AK: 0);
    }
    if (this.ResizingMain & 1) {
        D8.Height = this.e.offsetHeight + (_7S ? this.AL: 0);
    }
    D8.Qv = new Object();
    var M = ["Top", "Bottom", "Left", "Right"],
    S = ["Color", "Style", "Width"];
    for (var i = 0; i < 4; i++) for (var j = 0; j < 3; j++) {
        var Bh = "border" + M[i] + S[j];
        D8.Qv[Bh] = this.MainTag.style[Bh];
    }
    D8.MinWidth = D8.Width - (this.c[1].offsetWidth ? this.c[1].offsetWidth: this.b[1].offsetWidth) + 25 + _7Y;
    if (D8.MinWidth < this.MinTagWidth) D8.MinWidth = this.MinTagWidth;
    if (D8.MinWidth < this.MinWidth) D8.MinWidth = this.MinWidth;
    D8.MinHeight = D8.Height - this.c[1].offsetHeight + 25 + _7Z;
    var CE = this.GetFixedRows();
    for (var J = this.XS.firstChild; J; J = J.nextSibling) CE[CE.length] = J;
    for (var i = 0; i < CE.length; i++) if (CE[i].RelHeight && CE[i].r1) D8.MinHeight -= CE[i].r1.offsetHeight - (CE[i].MinHeight ? CE[i].MinHeight: 0);
    if (D8.MinHeight < this.MinTagHeight) D8.MinHeight = this.MinTagHeight;
    if (D8.MinHeight < this.MinHeight) D8.MinHeight = this.MinHeight;
    this.Rendering = 1;
    this.Ae = 1;
    if (_7T) {
        D8.Qw = this.e.nextSibling;
        this.e.parentNode.removeChild(this.e);
        D8.Hidden = true;
    }
    this.MainTag.style.border = "1px dashed black";
    D8.Action = "GridResize";
    D8.Move = this.Qx;
    D8.II = this.Qy;
    return true;
};
_7m.prototype.Qx = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    if (this.ResizingMain & 2) {
        var CY = D8.Width + (this.HV ? D8.P - D8.IQ: D8.IQ - D8.P);
        if (CY < D8.MinWidth) {
            D8.P = D8.IQ - CY + D8.MinWidth;
            CY = D8.MinWidth;
        } else D8.P = D8.IQ;
        this.MainTag.style.width = CY + "px";
        D8.Width = CY;
    }
    if (this.ResizingMain & 1) {
        var CZ = D8.Height - D8.Y + D8.IR;
        if (_7T) this.Refresh();
        if (CZ < D8.MinHeight) {
            D8.Y = D8.IR - CZ + D8.MinHeight;
            CZ = D8.MinHeight;
        } else D8.Y = D8.IR;
        this.MainTag.style.height = CZ + "px";
        D8.Height = CZ;
    }
    return true;
};
_7m.prototype.Qy = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    for (var i in D8.Qv) this.MainTag.style[i] = D8.Qv[i];
    if (D8.Hidden) {
        if (D8.Qw) this.MainTag.insertBefore(this.e, D8.Qw);
        else this.MainTag.appendChild(this.e);
    }
    this.e.style.display = "";
    Grids.A2 = null;
    this.Rendering = 0;
    this.Ae = 0;
    this.SetScrollBars();
    this.SaveCfg();
    if (Grids.OnResizeMain) Grids.OnResizeMain(this);
    return true;
};
_7m.prototype.ActionGridResizeDefault = function () {
    this.MainTag.style.width = this.Qz;
    this.MainTag.style.height = this.Q0;
    this.SaveCfg();
    if (Grids.OnResizeMain) Grids.OnResizeMain(this);
};
_7m.prototype.ShowMessage = function (message, x, y, OG) {
    if (!OG && this.SuppressMessage || !message) return;
    this.Disable();
    if (!this.MainTag.offsetWidth || this.Q1) return;
    var EU = this.OverflowDialog & 1 ? this.MainTag: document.body;
    if (_7AJ != 1 && this.Message) {
        this.Hk(this.Message);
        this.Message.parentNode.removeChild(this.Message);
        this.Message = null;
    }
    if (this.Message) {
        var D4 = this.Message;
    } else {
        var D4 = document.createElement("div");
        D4.style.position = "absolute";
        D4.style.visibility = "hidden";
        D4.style.left = 0;
        D4.style.top = 0;
        D4.style.zIndex = this.ZIndex;
        if (_7R && !_7U && EU == document.body) EU.insertBefore(D4, EU.firstChild);
        else EU.appendChild(D4);
    };
    D4.innerHTML = "<div class='" + this.Img.Style + "Message'>" + message + "</div>";
    var It = this.MessageWidth;
    if (It && D4.offsetWidth < It) D4.style.width = It + "px";
    if (this.OverflowDialog & 1) {
        if (D4.offsetHeight > this.MainTag.offsetHeight) {
            y = -1;
            D4.style.overflow = "scroll";
        }
        if (D4.offsetWidth > this.MainTag.offsetWidth) {
            x = -1;
            D4.style.overflow = "scroll";
        }
        if (D4.offsetHeight > this.MainTag.offsetHeight) y = -1;
    }
    if (this.OverflowDialog & 8) {
        var BL = GetWindowScroll(),
        H = GetWindowSize();
        y = (H[1] - D4.offsetHeight) / 2 + BL[1];
    }
    _7Aj(D4, this.MainTag, x, y, this.HV, 0, this.OverflowDialog & 1 ? 0 : 1);
    if (Grids.OnShowMessage) Grids.OnShowMessage(this, D4);
    D4.style.visibility = "";
    this.Hk(D4, this.ShadowMessage);
    this.Message = D4;
};
_7m.prototype.HideMessage = function () {
    this.Enable();
    if (this.Message == null) return;
    var EU = this.OverflowDialog & 1 ? this.MainTag: document.body;
    if (this.Message.parentNode == EU) {
        this.Hk(this.Message);
        EU.removeChild(this.Message);
    }
    this.Message = null;
};
_7m.prototype.ShowDialog = function (row, BJ, EK, align, P, Y, Q2) {
    this.CloseDialog(1);
    var D4 = document.createElement("div");
    D4.onclick = _7Af;
    D4.innerHTML = EK;
    D4.style.position = "absolute";
    D4.style.visibility = "hidden";
    D4.style.zIndex = this.ZIndex;
    D4.style.left = "0px";
    D4.style.top = "0px";
    var height = this.MainTag.clientHeight;
    if (_7R && !_7U) {
        document.body.insertBefore(D4, document.body.firstChild);
        D4.style.top = "1px";
        D4.style.left = "1px";
    } else document.body.appendChild(D4);
    var IU = row ? this.GetCell(row, BJ) : BJ;
    if (!IU) return;
    var Bg = ElemToParent(IU, this.MainTag);
    var x = Bg[0],
    y = Bg[1];
    if (row == this.XHeader && this.XHeader.Rows > 1) y = ElemToParent(this.XHeader.r1, this.MainTag)[1];
    if (P != null) {
        x += P;
        align = this.HV ? 0 : 1;
    }
    if (Y != null) y += Y;
    var DR = !row || row.Space != null ? 0 : this.Cols[BJ].K;
    var CZ = D4.offsetHeight;
    if (Q2 && CZ > Q2) {
        CZ = Q2;
        D4.style.height = CZ + "px";
        D4.style.width = (D4.offsetWidth + _7Y) + "px";
        D4.style.overflow = "auto";
    }
    var CY = D4.offsetWidth,
    C1, Q3;
    if (!row) {
        C1 = IU.offsetWidth;
        Q3 = IU.offsetHeight;
    } else if (row.Space != null) {
        Q3 = IU.offsetHeight - 1;
        C1 = this.DS(row, BJ);
    } else {
        C1 = this.DS(row, BJ);
        Q3 = IU.offsetHeight;
        if (!Q3) Q3 = this.GetRowHeight(row);
    };
    if (Y != null) Q3 = 0;
    if (this.OverflowDialog & 4) {
        if (y + Q3 + CZ > height) y = y - CZ - (_7S ? 6 : 0);
        else y = y + Q3;
        if (y < 4) y = 4;
    } else y = y + Q3;
    if (!align) x = x + C1 - CY;
    var Q4 = 1;
    if (row && DR != 1 && this.Cols["Panel"].Visible && (row.Space == null || row.Panel)) Q4 = this.GetCell(this.XHeader, "Panel").offsetWidth / 2;
    if (x < Q4) x = Q4;
    _7Aj(D4, this.MainTag, x, y, this.HV);
    if (y < 0 && row && row.Tag) D4.style.height = "";
    if (this.OverflowDialog & 2) {
        var Q5 = this.MainTag.clientHeight - y - 4;
        if (D4.offsetHeight > Q5 && Q5 > 0) {
            D4.style.height = Q5 + "px";
            D4.style.width = (D4.offsetWidth + _7Y) + "px";
            D4.style.overflow = "auto";
        }
        if (D4.offsetWidth > this.MainTag.clientWidth - x - 4) {
            x = this.MainTag.clientWidth - D4.offsetWidth - 4;
            if (x < 4) x = 4;
            D4.style.left = x + "px";
        }
        if (D4.offsetWidth > this.MainTag.clientWidth - x - 4) {
            D4.style.width = (this.MainTag.clientWidth - x - 4) + "px";
            if (D4.style.overflow != "auto") {
                D4.style.height = (D4.offsetHeight + _7Z) + "px";
                D4.style.overflow = "auto";
            }
        }
    }
    var BL = ElemToParent(D4, this.MainTag);
    if (_7F && this.MainTag.style.position.toLowerCase() == "absolute") {
        BL[0] -= this.MainTag.offsetWidth - this.MainTag.clientWidth;
        BL[1] -= this.MainTag.offsetHeight - this.MainTag.clientHeight;
    }
    if (x >= 0) D4.style.left = (x * 2 - BL[0]) + "px";
    if (y >= 0) D4.style.top = (y * 2 - BL[1]) + "px";
    if (_7G || _7M || _7H && !_7O) {
        this.t.style.display = "none";
    }
    var D8 = new _7l();
    D8.Grid = this;
    D8.Tag = D4;
    this.Hk(D4, this.ShadowDialog);
    this.Dialog = D8;
    D8.MainTag = this.MainTag;
    D4.style.visibility = "";
    D8.Row = row;
    D8.Col = BJ;
    if (_7T) this.MainTag.clientHeight;
    if (Grids.OnShowDialog) Grids.OnShowDialog(this, row, BJ, D8.Tag);
    return D8;
};
_7m.prototype.CloseDialog = function (OG) {
    var D8 = this.Dialog;
    if (D8 == null) return;
    if (D8.Start && OG != true) {
        D8.Start = false;
        return;
    }
    if (D8.Close) D8.Close(D8);
    if (D8.Tag && !_7U) D8.Tag.innerHTML = "";
    this.Hk(D8.Tag);
    document.body.removeChild(D8.Tag);
    if (D8.Caption) {
        this.Hk(D8.Caption);
        document.body.removeChild(D8.Caption);
    }
    this.Dialog = null;
    if (_7G || _7M || _7H && !_7O) {
        this.t.style.display = "";
    }
};
_7m.prototype.ShowMenu = function (row, BJ, Q6, Q7, BV, align, caption, P, Y, Q8, Q9, RA, RB, cursor) {
    var RC = [],
    RD = [],
    Ka = 0,
    EU = [];
    for (var i = 0; i < Q6.length; i++) {
        RC[i] = Ka;
        if (Q6[i - 1] && Q6[i - 1].indexOf("*+") == 0) {
            if (!Q7) Q7 = [];
            Q7[i] = this.HW(15, 340, this.Img["MenuLevelIcon"], null, null, "auto");
            var BQ = Q6[i - 1].slice(2) - 0;
            if (BQ) {
                RD[++Ka] = BQ;
                for (var j = 0; j < Ka; j++) RD[j] -= RD[Ka];
                EU[i] = 1;
            } else EU[i] = 2;
        } else while (Ka && --RD[Ka] <= 0) Ka--;
    }
    if (!RA) RA = "Menu";
    var BO = " class='" + this.Img.Style,
    Bh = "<div" + BO + RA + "'>" + _7A + _7B,
    span = " colspan='" + (1 + (Q9 ? 1 : 0) + (Q7 ? 1 : 0)) + "'";
    var Bp = "<tr" + BO + RA + "Item'><td" + span + " style='cursor:default;'><div" + BO + RA + "Separator'>" + _7D + "</div></td></tr>";
    if (caption) Bh += "<tr" + BO + RA + "Item'><td" + span + BO + RA + "Caption'>" + caption + "</td></tr><tr><td" + span + " style='cursor:default;'><hr/></td></tr>";
    var RE = caption ? 2 : 0;
    for (var i = 0; i < Q6.length; i++) {
        if (!Q6[i]) Bh += "<tr style='display:none;'></tr>";
        else if (Q6[i] == '*-') Bh += Bp;
        else if (Q6[i].indexOf("*+") == 0) Bh += "<tr style='display:none'><td></td></tr>";
        else {
            Bh += "<tr" + BO + RA + (cursor == i ? "Cursor": (EU[i] ? "Group": "Item")) + "'";
            if (RC[i]) Bh += " style='display:none;'";
            if (EU[i] == 2) Bh += "style='cursor:default;'";
            Bh += ">";
            if (Q9) {
                if (Q9[i] < 0) Bh += "<td><span style='width:" + this.Img.Row + ";'>" + _7D + "</span></td>";
                else if (EU[i]) Bh += "<td>" + (Q7[i] ? Q7[i] : _7D) + "</td>";
                else Bh += "<td>" + this.HW(15, Q9[i] ? this.Img.Row: 0, this.Img.Row, null, _7a) + "</td>";
            }
            if (Q7) Bh += "<td>" + (Q7[i] && (!EU[i] || !Q9) ? Q7[i] : _7D) + "</td>";
            Bh += "<td nowrap" + _7E + " style='padding-left:" + (RC[i] * this.Img["MenuLevel"] + 3) + "px;padding-right:3px;'>" + Q6[i] + "</td>";
            Bh += "</tr>"
        }
    }
    if (Q9) {
        Bh += Bp;
        Bh += "<tr" + BO + RA + "Item'><td" + span + "' style='cursor:default;' align='center'>";
        Bh += "<button " + BO + RA + "Button'>" + this.GetAlert("MenuClear") + "</button>";
        Bh += "<button " + BO + RA + "Button'>" + this.GetAlert("MenuOk") + "</button>";
        Bh += "</td></tr>";
    }
    Bh += _7C + "</div>";
    var D8 = this.ShowDialog(row, BJ, Bh, align, P, Y, this.MaxMenuHeight);
    if (!D8) return D8;
    if (BV) {
        var RF = D8.Tag.getElementsByTagName("tr");
        var BN = RF.length;
        if (Q9) BN -= 2;
        var RG = "this.cells[0].firstChild." + (_7V ? "firstChild.": "") + "style.backgroundPosition";
        var GB = new Function("this.className=\"" + this.Img.Style + RA + "Hover\";" + (RB ? "if(!this.Zal)this.Zal=" + RG + ";" + RG + "=\"" + RB + "\";": ""));
        var RH = new Function("this.className=\"" + this.Img.Style + RA + "Hover\";");
        var GD = new Function("this.className=\"" + this.Img.Style + RA + "Item\";" + (RB ? "if(this.Zal)" + RG + "=this.Zal;": ""));
        var RI = new Function("this.className=\"" + this.Img.Style + RA + "Cursor\";" + (RB ? "if(this.Zal)" + RG + "=this.Zal;": ""));
        var RJ = new Function("this.className=\"" + this.Img.Style + RA + "Group\";");
        for (var i = RE; i < BN; i++) {
            if (Q6[i] == '*-') {
                EU[i] = 1;
                continue;
            }
            if (EU[i] == 2) continue;
            if (EU[i]) RF[i].onclick = function () {
                var J = this.nextSibling,
                Ce = this.rowIndex - RE;
                if (J) {
                    var KV = J.style.display ? "": "none",
                    BQ = Q6[Ce - 1].slice(2);
                    if (!BQ) BQ = 0;
                    for (var j = 0; j < BQ && J; j++, J = J.nextSibling) if (KV || RC[Ce + j + 1] == RC[Ce + 1]) J.style.display = KV;
                    if (D8.Tag.RK) D8.Grid.Hk(D8.Tag, D8.Tag.RK.RK);
                }
            };
            else if (Q9) RF[i].onclick = function () {
                var Ce = this.rowIndex - RE;
                if (Q9[Ce] < 0) {
                    if (BV(Ce, D8.Grid, row, BJ, Q8) != false) D8.Grid.CloseDialog();
                    return;
                }
                Q9[Ce] = Q9[Ce] ? 0 : 1;
                this.cells[0].innerHTML = D8.Grid.HW(15, Q9[Ce] ? D8.Grid.Img.Row: 0, D8.Grid.Img.Row, null, _7a);
            };
            else RF[i].onclick = function () {
                if (BV(this.rowIndex - RE, D8.Grid, row, BJ, Q8) != false) D8.Grid.CloseDialog();
            };
            RF[i].onmouseover = EU[i] ? RH: GB;
            RF[i].onmouseout = EU[i] ? RJ: (i == cursor ? RI: GD);
        }
        if (Q9) {
            var H = RF[RF.length - 1].getElementsByTagName("button");
            H[0].onclick = function () {
                for (var i = RE; i < BN; i++) {
                    if (Q9[i - RE] == 1 && !EU[i - RE]) {
                        Q9[i - RE] = 0;
                        RF[i].cells[0].innerHTML = D8.Grid.HW(15, 0, D8.Grid.Img.Row, null, _7a);
                    }
                }
            };
            H[1].onclick = function () {
                if (BV(Q9, D8.Grid, row, BJ, Q8) != false) D8.Grid.CloseDialog();
            }
        }
    }
    return D8;
};
_7m.prototype.Prompt = function (text, Bj, BV, width) {
    if (!width) width = 200;
    if (Bj == null) Bj = "";
    this["PromptFunc"] = BV ? BV: function () {};
    var Bh = "<div style='padding-bottom:10px;width:" + width + "px;'>" + text + "</div>";
    Bh += "<div style='padding-bottom:10px;'><input type='text' value='" + Bj + "' style='width:" + width + "px;'";
    Bh += " onkeydown='if(event.charCode==13 || event.keyCode==13) {" + this.Z + ".HideMessage();" + this.Z + ".PromptFunc(this.value);} if(event.charCode==27 || event.keyCode==27) {" + this.Z + ".HideMessage();" + this.Z + ".PromptFunc(null);}'></div>";
    Bh += "<button onclick='" + this.Z + ".HideMessage();" + this.Z + ".PromptFunc(this.previousSibling.firstChild.value);' class='" + this.Img.Style + "CfgMenuButton'>" + this.Lang["MenuCfg"]["OK"] + "</button>";
    Bh += "<button onclick='" + this.Z + ".HideMessage();" + this.Z + ".PromptFunc(null);'class='" + this.Img.Style + "CfgMenuButton'>" + this.Lang["MenuCfg"]["Cancel"] + "</button>";
    this.ShowMessage(Bh);
    var E4 = this.Message.getElementsByTagName("input")[0];
    if (E4) {
        E4.focus();
        E4.select();
    }
};
_7m.prototype.Im = function (CY) {
    var D8 = this.Dialog;
    if (D8.Tag.offsetWidth < CY) {
        if (D8.Tag.RK) {
            var Mg = CY - D8.Tag.offsetWidth;
            if (Mg) {
                var Na = parseInt(D8.Tag.RK.style.width);
                if (Na) D8.Tag.RK.style.width = (Na + Mg) + "px";
            }
        }
        D8.Tag.style.width = CY + "px";
        var Hm = D8.Tag.getElementsByTagName("tr");
        for (var i = 0; i < Hm.length; i++) {
            var Cs = Hm[i].lastChild;
            if (Cs) Cs.style.width = (CY - Cs.offsetLeft) + "px";
        }
        var Mg = D8.Tag.offsetWidth - CY;
        if (Mg > 0) {
            for (var i = 0; i < Hm.length; i++) {
                var Cs = Hm[i].lastChild;
                if (Cs) Cs.style.width = (CY - Cs.offsetLeft - Mg) + "px";
            }
        }
    }
};
_7m.prototype.HideHint = function (IL, OT, RL, OU, RM) {
    if (!this.RN) return;
    if (IL) {
        var BL = EventXYToElement(IL, this.RN);
        if (BL[0] >= OT && BL[0] <= OU && BL[1] >= RL && BL[1] <= RM + 3) return;
    }
    this.Hk(this.RN);
    document.body.removeChild(this.RN);
    this.RN = null;
};
_7m.prototype.ShowHint = function (row, BJ) {
    if (!row || !BJ || row == this.XHeader) return;
    var Ca = this.GetType(row, BJ);
    if (Ca == "Abs" || Ca == "Gantt") return;
    var BX = BJ,
    IT = null;
    if (row.Spanned && Get(row, BJ + "Merge")) {
        IT = Get(row, BJ + "MergeEdit");
        if (!IT) IT = 0;
        BX = this.ColNames[this.Cols[BJ].K][this.Cols[BJ].Pos + IT];
        if (!this.Cols[BX].Visible && !(Get(row, BX + "MergeType") & 2)) return;
    }
    if (this.EditMode && this.FRow == row && this.FCol == BJ) return;
    var F6 = Get(row, BX + "ShowHint");
    if (F6 == null && this.Cols[BX]) F6 = this.Cols[BX].ShowHint;
    if (!F6) return;
    var IU = this.GetCell(row, BJ, 3);
    if (!IU) return;
    var CY = IU.offsetWidth,
    CZ = IU.offsetHeight,
    Na = IU.scrollWidth,
    F6 = IU.scrollHeight,
    type = this.GetType(row, BX);
    if (Na <= CY && F6 <= CZ || CY < 10) return;
    var IU = this.GetCell(row, BJ, 1);
    if (!IU) return;
    if (IT) {
        var IY = Get(row, BJ + "MergeStart"),
        S = this.ColNames[this.Cols[BJ].K],
        IZ = Get(row, BJ + "MergeType");
        if (!IY) IY = 0;
        for (; IY < IT; IY++) if (this.Cols[S[this.Cols[BJ].Pos + IY]].Visible || IZ & 2) IU = IZ & 1 ? IU.parentNode.nextSibling.firstChild: IU.nextSibling;
    }
    this.HideHint();
    var OV = this.Il(row, BX, 1);
    if (Grids.OnHint) OV = Grids.OnHint(this, row, BX, OV);
    if (!OV) return;
    var D4 = document.createElement("div");
    D4.innerHTML = "<div class='" + this.Img.Style + "Hint'>" + OV + "</div>";
    D4.style.position = "absolute";
    D4.style.zIndex = this.ZIndex;
    var BL = ElemToParent(IU, _7T || _7H && !_7P ? document.documentElement: document.body);
    D4.style.left = BL[0] + "px";
    D4.style.top = BL[1] + "px";
    if (_7R && !_7U) document.body.insertBefore(D4, document.body.firstChild);
    else document.body.appendChild(D4);
    var BL = ElemToParent(IU, this.MainTag);
    var RO = this.MainTag.offsetWidth - BL[0] - 6;
    if (Na <= CY && RO > CY) RO = CY;
    if (RO < D4.offsetWidth) {
        var RP = D4.firstChild.style;
        RP.overflow = "hidden";
        RP.width = RO + "px";
        RP.whiteSpace = "normal";
    }
    var Jh = this.MainTag.offsetHeight - BL[1] - 6;
    if (Jh < D4.offsetHeight) {
        var RP = D4.firstChild.style;
        if (_7V && RO >= D4.offsetWidth) RP.width = D4.offsetWidth + "px";
        RP.overflow = "hidden";
        RP.height = Jh + "px";
        RP.whiteSpace = "normal";
    }
    D4.onmousedown = new Function("ev", this.Z + ".GridMouseDown(ev?ev:event);");
    D4.onclick = new Function("ev", this.Z + ".HideHint();" + this.Z + ".GridClick(ev?ev:event);");
    D4.oncontextmenu = new Function("ev", this.Z + ".HideHint();" + this.Z + ".GridRightClick(ev?ev:event);");
    var RQ = 0;
    if (!row.Fixed) {
        var H = ElemToParent(IU, this.c[1]);
        var RR = H[1];
        if (RR < 0) RQ = -RR;
        if (RR + CZ > this.c[1].offsetHeight) CZ = this.c[1].offsetHeight - RR;
    }
    var Ik = 0;
    D4.onmousemove = new Function("ev", this.Z + ".HideHint(ev?ev:event," + Ik + "," + RQ + "," + (CY - IU.offsetLeft + Ik) + "," + CZ + ");");
    this.RN = D4;
    this.Hk(D4, this.ShadowHint);
    if (!_7R && D4.addEventListener && !this.NoVScroll) {
        var BV = new Function("event", "if(!" + this.Z + ".GridMouseWheel(-event.detail*40)) event.preventDefault();");
        D4.addEventListener('DOMMouseScroll', BV, false);
    }
};
_7m.prototype.HideTip = function () {
    if (!this.Jo) return;
    this.Hk(this.Jo);
    document.body.removeChild(this.Jo);
    this.Jo = null;
};
_7m.prototype.ShowTip = function (x, y, Jp, Hb) {
    this.HideTip();
    if (!Hb) Hb = this.Img.Style + "Tip";
    else Hb = this.Img.Ib + Hb;
    var D4 = document.createElement("div");
    D4.innerHTML = "<div class='" + Hb + "'>" + Jp + "</div>";
    D4.style.position = "absolute";
    D4.style.zIndex = this.ZIndex;
    D4.style.left = x + "px";
    D4.style.top = y + "px";
    if (_7R && !_7U) document.body.insertBefore(D4, document.body.firstChild);
    else document.body.appendChild(D4);
    this.Jo = D4;
    this.Hk(D4, this.ShadowTip);
};
_7m.prototype.Hk = function (RS, RT) {
    if (!RS || _7U) return;
    RS.offsetHeight;
    var D4 = RS.RK;
    if (!RT) {
        if (D4) {
            RS.parentNode.removeChild(D4);
            RS.RK = null;
        }
        return;
    }
    var Bh = RT.split(",");
    if (!D4) D4 = document.createElement("div");
    D4.style.position = "absolute";
    D4.style.zIndex = this.ZIndex - 1;
    D4.style.left = (Bh[0] - 0 + Math.floor(RS.offsetLeft / _7AJ)) + "px";
    D4.style.top = (Bh[0] - 0 + Math.floor(RS.offsetTop / _7AJ)) + "px";
    D4.style.width = (Bh[2] - Bh[0] + RS.offsetWidth) + "px";
    D4.style.height = (Bh[3] - Bh[1] + RS.offsetHeight) + "px";
    D4.className = this.Img.Style + "Shadow";
    D4.RK = RT;
    RS.parentNode.insertBefore(D4, RS);
    RS.RK = D4;
    if (_7AJ != 1) {
        _7Ao(RS);
        setTimeout(function () {
            _7Ao(RS);
        },
        10);
    }
};
_7m.prototype.FE = function () {
    if (this.Rendering || this.Loading) return;
    var KU = this.GetFirstVisible() || this.XB.firstChild.State < 2 ? "": "none",
    Df = 0;
    for (var i = 0; i < 3; i++) {
        var H = this.Kt(i);
        if (H) {
            Df = H.parentNode.style.display != KU;
            H.parentNode.style.display = KU;
            if (Df && KU) {
                var Bh = this.XB.firstChild["r" + i].style;
                Bh.overflow = "";
                if (!_7U) Bh.height = "";
            }
        }
    }
    if (Df) this.SetScrollBars();
};
_7m.prototype.GetScrollLeft = function () {
    return _7G ? -this.b[1].firstChild.firstChild.offsetLeft: this.b[1].scrollLeft;
};
_7m.prototype.GetScrollTop = function () {
    return _7G ? -this.c[1].firstChild.firstChild.offsetTop: this.c[1].scrollTop;
};
_7m.prototype.IM = function (object, IL, move, EK) {
    if (!this.ShowDrag) return;
    this.Jv();
    var M = new _7k;
    var B = document.createElement("div");
    B.style.position = "absolute";
    if (object) {
        var I = EventXYToElement(IL, object, false, true);
        M.Q = I[0];
        M.U = I[1];
        B.style.left = Math.floor((IL.clientX / (IL.type ? _7AJ: 1) - M.Q)) + "px";
        B.style.top = Math.floor((IL.clientY / (IL.type ? _7AJ: 1) - M.U)) + "px";
        B.className = object.className;
    } else {
        B.style.left = 0;
        B.style.top = 0;
    };
    B.style.zIndex = this.ZIndex;
    B.innerHTML = EK ? EK: object.innerHTML;
    B.style.opacity = "0.5";
    B.style.filter = "alpha(opacity=50)";
    document.body.appendChild(B);
    M.Tag = B;
    if (move >= 0) M.Move = move;
    Grids.A5 = M;
    M.U = -5 - GetWindowScroll()[1] / _7AJ;
};
_7m.prototype.Jt = function (IL) {
    var M = Grids.A5;
    if (M && IL) {
        if (M.Move & 1) M.Tag.style.left = Math.floor(IL.clientX / (IL.type ? _7AJ: 1) - M.Q) + "px";
        if (M.Move & 2) M.Tag.style.top = Math.floor(IL.clientY / (IL.type ? _7AJ: 1) - M.U) + "px";
    }
};
_7m.prototype.Jv = function () {
    var M = Grids.A5;
    if (M) {
        document.body.removeChild(M.Tag);
        Grids.A5 = null;
    }
};
_7m.prototype.OE = function (P, Y, RU, RV) {
    var CZ = this.b[1].offsetHeight + this.c[1].offsetHeight + (this.d[1] ? this.d[1].offsetHeight: 0);
    if (RU) {
        var RW = this.p.parentNode.parentNode.offsetHeight;
        if (RW) CZ += RW;
    }
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Visible && J.r1 && J.Space > 0 && J.Space < 4) CZ += J.r1.offsetHeight;
    var IL = {
        clientX: P,
        clientY: Y
    };
    this.IM(null, IL, 1, "<div style='" + (RV ? "": "cursor:e-resize;") + "width:1px;height:" + CZ + "px;border-left:1px solid;overflow:hidden;'>" + _7D + "</div>");
    var J5 = ElemToParent(this.b[1], _7T || _7H && !_7P ? document.documentElement: document.body)[1];
    var M = Grids.A5;
    M.Q = _7S ? document.body.clientLeft: (_7T ? document.documentElement.clientLeft: 0);
    M.Q -= 2 + GetWindowScroll()[0] / _7AJ;
    M.Tag.style.left = P + "px";
    M.Tag.style.top = J5 + "px";
};
_7m.prototype.Refresh = function () {
    if (_7R && !this.Rendering) {
        this.RX.innerHTML = "";
        this.c[1].scrollTop = this.c[1].scrollTop;
    }
};
_7m.prototype.E5 = function (id) {
    var E4 = GetElem(id);
    if (E4) {
        var D4 = E4.tagName.toLowerCase();
        if (D4 == "input" || D4 == "textarea") return E4;
    }
    var BL = document.getElementsByName(id);
    var Pv = this.FK(this.e);
    var Pw = null;
    for (var i = 0; i < BL.length; i++) {
        E4 = BL[i];
        var D4 = E4.tagName.toLowerCase();
        if (D4 == "input" || D4 == "textarea") {
            var CE = this.FK(D4);
            if (CE && (CE == Pv || !Pv)) return E4;
            Pw = E4;
        }
    }
    return Pw;
};
_7m.prototype.FK = function (D4) {
    if (!D4 || !D4.tagName) return null;
    while (D4 && D4.tagName) {
        if (D4.tagName.toLowerCase() == "form") return D4;
        D4 = D4.parentNode;
    }
};
function _7Aw(src, BN) {
    var H = _7Aw.RY,
    RZ = _7Aw.Ra;
    function Rb() {
        if (RZ) {
            clearTimeout(RZ);
            _7Aw.Ra = null;
            RZ = null;
        }
        if (_7F) {
            try {
                document.documentElement.removeChild(H);
            } catch(B) {};
            H = null;
        } else {
            H.src = "";
        }
    };
    if (RZ) Rb();
    if (!src) return;
    if (!H) {
        if (_7F) {
            H = document.createElement("div");
            H.innerHTML = '<embed src="' + src + '" autostart=true hidden width=0 height=0 enablejavascript="true">';
            H = H.firstChild;
        } else {
            H = document.createElement("bgsound");
            H.src = src;
        };
        document.documentElement.appendChild(H);
        _7Aw.RY = H;
    } else {
        H.src = src;
    };
    if (BN) _7Aw.Ra = setTimeout(function () {
        Rb();
    },
    BN);
};
function _7AH(T) {
    var Bh, B;
    if (T.selectionStart != null) {
        Bh = T.selectionStart,
        B = T.selectionEnd;
    } else {
        function Rc(BZ) {
            return BZ.replace(/\r\n/g, "\n").length;
        };
        var S = document.selection.createRange(),
        Rd = T.createTextRange();
        Rd.moveToBookmark(S.getBookmark());
        var BZ = T.value,
        BN = Rc(BZ),
        Re = Rc(Rd.text);
        Bh = -Rd.moveStart('character', -BN);
        if (Bh < Rc(Rd.text) - Re) Bh = BN;
        B = Bh + Re;
    };
    return [Bh, B];
};
function _7AI(T, Bh, B) {
    if (T.selectionStart != null) {
        T.selectionStart = Bh;
        T.selectionEnd = B;
    } else {
        var S = T.createTextRange();
        S.move('character', Bh);
        S.moveEnd('character', B - Bh);
        S.select();
    }
};
function _7AG(T, Mask, Rf, color, Rg, Rh) {
    var Ri, Rj, Rk = new RegExp(Mask, ""),
    Rl = null,
    Rm = 0,
    Rn = T.style.backgroundColor,
    Ro = null;
    var Rp = T.onkeypress,
    Rq = T.onkeydown,
    Rr = T.onkeyup;
    T.onkeypress = Rs;
    T.onkeydown = Rt;
    T.onkeyup = Ru;
    function Rv(BZ) {
        if (BZ.search(Rk) == -1) {
            if (color) {
                T.style.backgroundColor = color;
                if (Rl) clearTimeout(Rl);
                Rl = setTimeout(function () {
                    T.style.backgroundColor = Rn;
                },
                200);
            }
            if (Rg) _7Aw(Rg, Rh);
            if (Ro != null) {
                T.selectionEnd = Ro;
                Ro = null;
            }
            return 1;
        }
        return 0;
    };
    function Rs(IL) {
        if (!IL) IL = window.event;
        if (T.readOnly) return true;
        if (Rm) {
            CancelEvent(IL);
            return false;
        }
        if (IL && !_7G && !_7M && _7H && (!_7R || Rf != null)) {
            var BY = _7R ? IL.keyCode: IL.charCode;
            if (BY != 0) {
                if (BY == 13) BY = 10;
                var BZ = T.value.replace(/\r\n/g, "\n");
                BZ = BZ.slice(0, Rj[0]) + String.fromCharCode(BY) + BZ.slice(Rj[1] + (_7R && Rf ? 1 : 0));
                if (Rv(BZ)) {
                    CancelEvent(IL);
                    return false;
                }
            }
        }
        if (Rp) Rp(IL);
        return true;
    };
    function Rt(IL) {
        if (T.readOnly) return true;
        function Iy() {
            if (Rq) Rq(IL);
            return true;
        };
        if (!IL) IL = window.event;
        if (T.value.search(Rk) == -1 && Rj) {
            T.value = Ri;
            _7AI(T, Rj[0], Rj[1]);
        }
        Ri = T.value;
        Ro = null;
        if (Rf && _7F) {
            var BY = IL.keyCode;
            if (BY != 37 && BY != 38 && BY != 40 && BY != 8 && BY != 45) {
                Ro = T.selectionEnd;
                T.selectionEnd += 1;
            }
        }
        Rj = _7AH(T);
        Rm = 0;
        if (IL) {
            if (IL.shiftKey || IL.altKey || IL.Rw) return Iy();
            var BY = IL.keyCode,
            Bh = Rj[0],
            B = Rj[1],
            BZ = _7G || _7M ? T.value: T.value.replace(/\r\n/g, "\n");
            if (BY == 0) BY = IL.charCode;
            if (BY == 45) {
                if (Rf != null) Rf = !Rf;
                return Iy();
            } else if (BY == 8) {
                if (Bh && Bh == B) Bh--;
            } else if (BY == 46 && B < BZ.length - 1 && Bh == B) B++;
            else return Iy();
            BZ = BZ.slice(0, Bh) + BZ.slice(B);
            if (Rv(BZ)) {
                CancelEvent(IL);
                Rm = 1;
                return false;
            }
        }
        return Iy();
    };
    function Ru(IL) {
        if (T.readOnly || !Rj) return true;
        if (!IL) IL = window.event;
        if (Rv(T.value)) {
            T.value = Ri;
            _7AI(T, Rj[0], Rj[1]);
        }
        if (Rm) {
            Rm = 0;
            CancelEvent(IL);
            return false;
        }
        if (Rr) Rr(IL);
        return true;
    };
};
_7m.prototype.Ja = function (row) {
    if (!row) return;
    row.DetailRow = null;
    row.DetailGrid = null;
    for (var J = row.firstChild; J; J = J.nextSibling) this.Ja(J);
};
_7m.prototype.ShowDetail = function (row, detail, OG) {
    var D8 = Grids[detail];
    if (!D8) return false;
    if (D8 == this) row = row.MasterRow;
    else {
        D8.E2 = this;
        if (D8.Loading || D8.Rendering) {
            var T = this;
            setTimeout(function () {
                T.ShowDetail(row, detail);
            },
            100);
            return true;
        }
    };
    if (Grids.OnShowDetail && Grids.OnShowDetail(this, D8, row)) return false;
    var H = D8.XB.firstChild;
    var Rx = H.MasterRow;
    if (Rx == row && !OG) return false;
    D8.Clear(4);
    if (Rx) this.ColorRow(Rx);
    if (_7R) H.innerHTML = "";
    else {
        H.firstChild = null;
        H.lastChild = null;
    };
    if (row.Deleted && !D8.ShowDeleted) {
        this.RefreshDetail(D8, 1);
        return true;
    }
    D8.Pn(H);
    H.State = 4;
    H.MasterRow = row;
    H.id = row.id;
    H.Def = row.Def;
    row.DetailRow = H;
    row.DetailGrid = D8;
    if (this.ChildPaging == 3 && row.State < 2 && !row.Expanded) {
        this.Ry(row);
        this.Rz(row);
        this.R0(row);
        return true;
    }
    var EW = Grids.A8;
    function Copy(Hz, Li) {
        for (var J = Li.firstChild; J; J = J.nextSibling) {
            var La = _7f.createElement("I");
            for (var a in J) if (!EW[a]) La[a] = J[a];
            La.id = J.id;
            if (La.Filtered) {
                La.Filtered = 0;
                La.Visible = 1;
            }
            La.r0 = null;
            La.r1 = null;
            La.r2 = null;
            La.MasterRow = J;
            J.DetailRow = La;
            J.DetailGrid = D8;
            if (J.firstChild) Copy(La, J);
            Hz.appendChild(La);
        }
    };
    Copy(H, row);
    D8.Calculate(1, 1, 1);
    D8.FC(1);
    D8.UpdateBody();
    D8.FE();
    this.ColorRow(row);
    if (Grids.OnShowDetailFinish) Grids.OnShowDetailFinish(this, D8, row);
    return true;
};
_7m.prototype.RefreshDetail = function (G, clear) {
    if (G) {
        if (G.E2 != this) return;
        if (clear) {
            if (G.ReloadBody) G.ReloadBody(null, 0, "Detail");
        } else {
            var H = G.XB.firstChild;
            if (H && H.MasterRow) this.ShowDetail(H.MasterRow, Get(H.MasterRow, "Detail"), 1);
        }
    } else {
        for (var i = 0; i < Grids.length; i++) {
            var G = Grids[i];
            if (G && G.E2 == this) this.RefreshDetail(G, clear);
        }
    }
};
_7m.prototype.R1 = function (CE, OG, clear) {
    var row = CE ? this.FRow: this.ARow;
    if (!row || row.Page) return false;
    var Ce = Get(row, "Detail");
    if (Ce == null) return false;
    if (clear) {
        var G = Grids[Ce];
        if (!G || !G.ReloadBody) return false;
        G.ReloadBody(null, 0, "Detail");
        return true;
    }
    return this.ShowDetail(row, Ce, OG);
};
_7m.prototype.ActionShowDetail = function (CE) {
    return this.R1(CE, 0);
};
_7m.prototype.ActionRefreshDetail = function (CE) {
    return this.R1(CE, 1);
};
_7m.prototype.ActionClearDetail = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (!row) return false;
    var G = row.DetailGrid;
    if (!G || !G.ReloadBody) return false;
    G.ReloadBody(null, 0, "Detail");
    return true;
};
_7m.prototype.ToolbarClick = function (Bb) {
    if (Bb == "MenuCfg") Bb = "Cfg";
    var BL = ["", "Save", "Reload", "Add", "Sort", "", "", "Help", "ExpandAll", "CollapseAll", "Cfg", "AddChild", "Repaint", "Calc", "Columns", "Print", "Export"];
    if (BL[Bb]) Bb = BL[Bb];
    this.JA = "Key";
    this.JW("", "Click", "Button" + Bb, null, null);
};
_7m.prototype.R2 = function (Bb, Oz, title, I0, Bx) {
    if (Bx == null) Bx = true;
    if (Grids.OnCanShowCfgItem) {
        if (!Grids.OnCanShowCfgItem(this, Bb, Bx)) return "";
    } else if (!Bx) return "";
    var DD = "",
    BO = this.Img.Style;
    DD = "<td>" + title + _7D + "</td><td id='G" + this.Index + "Cfg" + Bb + "' style='text-align:right'>" + I0 + "</td>";
    return "<tr class='" + BO + "CfgMenuItem'" + " onmouseover='this.className=\"" + BO + "CfgMenuHover\";'" + " onmouseout='this.className=\"" + BO + "CfgMenuItem\";'" + "><td>" + _7D + "</td><td>" + Oz + "</td>" + DD + "<td>" + _7D + "</td></tr>";
};
_7m.prototype.ActionShowCfg = function () {
    var MN = this.Lang["MenuCfg"],
    BO = this.Img.Style;
    function R3(R4) {
        return "<input type='checkbox' " + (R4 ? "checked" + _7E: "") + "/>";
    };
    function R5(R4, QW) {
        return "<option " + (R4 ? "selected" + _7E: "") + ">" + MN[QW] + "</option>";
    };
    var R6 = this.Data.Check.Interval;
    var R7 = "",
    R8 = Grids.BD,
    Bp = "<tr><td colspan='5'><div class='" + BO + "CfgMenuSeparator'>" + _7D + "</div></td></tr>";
    for (var i = 0; i < R8.length; i++) R7 += R5(R6 == R8[i], "CheckUpdates" + R8[i]);
    var DD = _7A + _7B + (MN["Caption"] ? "<tr><td colspan='5' class='" + BO + "CfgMenuHeader' style='text-align:center'>" + MN["Caption"] + "</td></tr>" + Bp: "") + this.R2(1, "", MN["ShowDeleted"], R3(this.ShowDeleted), this.Deleting && !!this.MenuCfg.ShowDeleted) + this.R2(2, "", MN["AutoSort"], R3(this.AutoSort), this.Sorting && !!this.MenuCfg.AutoSort && this.Paging <= 2) + this.R2(13, "", MN["SortClick"], "<select class='" + BO + "Select'>" + R5(this.SortIcons == 0, "SortClick1") + R5(this.SortIcons == 1, "SortClick2") + R5(this.SortIcons == 2, "SortClick3") + R5(this.SortIcons == 3, "SortClick4") + "</select>", !!this.Sorting && !!this.MenuCfg.ShowIcons) + this.R2(3, "", MN["AutoUpdate"], R3(this.AutoUpdate), !!this.Data.Upload.Url && !!this.MenuCfg.AutoUpdate && !this.Detail) + this.R2(4, "", MN["CheckUpdates"], "<select class='" + BO + "Select'>" + R7 + "</select>", !!this.Data.Check.Url && !!this.MenuCfg.CheckUpdates && !this.Detail) + (this.MenuCfg.Separator1 ? Bp: "") + this.R2(10, "", MN["MouseHover"], "<select class='" + BO + "Select'>" + R5(this.Hover == 0, "Hover1") + R5(this.Hover == 1, "Hover2") + R5(this.Hover == 2, "Hover3") + "</select>", !!this.MenuCfg.MouseHover) + this.R2(11, "", MN["ShowDrag"], R3(this.ShowDrag), (this.Dragging || this.ColMoving) && !!this.MenuCfg.ShowDrag) + this.R2(12, "", MN["ShowPanel"], R3(this.Cols.Panel && this.Cols.Panel.Visible), !!this.MenuCfg.ShowPanel) + this.R2(14, "", MN["ShowPager"], R3(this.Pager.Visible), !!this.MenuCfg.ShowPager) + this.R2(15, "", MN["ShowAllPages"], R3(this.AllPages), this.Paging && !!this.MenuCfg.ShowAllPages) + Bp + "<tr style='padding-top:10px;'><td>" + _7D + "</td><td colspan='3'><div style='text-align:center'>" + "<button class='" + BO + "CfgMenuButton' onclick='" + this.Z + ".HideCfg(1)'>" + MN["OK"] + "</button>" + "<button class='" + BO + "CfgMenuButton' onclick='" + this.Z + ".HideCfg(0)'>" + MN["Cancel"] + "</button>" + "</div></td><td>" + _7D + "</td></tr>" + _7C;
    this.ShowMessage(DD, null, null, true);
    this.Message.firstChild.className = BO + "CfgMenu";
    this.Hk(this.Message, this.ShadowMenu);
    if (Grids.OnShowCfg) Grids.OnShowCfg(this, this.Message);
};
_7m.prototype.HideCfg = function (save) {
    var E, R9 = false,
    Ot = false,
    SA = false;
    if (save) {
        var id = "G" + this.Index + "Cfg";
        if (this.Deleting) {
            E = GetElem(id + 1);
            if (E) {
                var SB = E.firstChild.checked;
                if (SB != this.ShowDeleted) {
                    if (this.SC()) {
                        if (this.Paging) R9 = true;
                        else Ot = true;
                    }
                    if (!this.ShowDeleted) SA = true;
                }
            }
        }
        if (this.Sorting && this.Paging <= 2) {
            E = GetElem(id + 2);
            if (E) {
                var SD = E.firstChild.checked;
                if (this.AutoSort != SD) {
                    if (SD) this.SortRows();
                    this.AutoSort = SD;
                }
            }
        }
        E = GetElem(id + 13);
        if (E) {
            this.SortIcons = E.firstChild.selectedIndex;
            this.PP();
        }
        E = GetElem(id + 3);
        if (E) this.AutoUpdate = E.firstChild.checked;
        E = GetElem(id + 4);
        if (E) this.Data.Check.Interval = Grids.BD[E.firstChild.selectedIndex];
        E = GetElem(id + 10);
        if (E) this.Hover = E.firstChild.selectedIndex;
        E = GetElem(id + 11);
        if (E) this.ShowDrag = E.firstChild.checked;
        E = GetElem(id + 12);
        if (E && this.Cols.Panel.Visible != E.firstChild.checked) {
            Ot = true;
            this.Cols.Panel.Visible = !this.Cols.Panel.Visible;
            this.SetVPos();
        }
        E = GetElem(id + 14);
        if (E && this.Pager.Visible != E.firstChild.checked) {
            this.SE(E.firstChild.checked);
            if (!Ot) {
                if (this.Pager.Visible && !_7S) Ot = true;
                else SA = true;
            }
        }
        E = GetElem(id + 15);
        if (E && this.AllPages != E.firstChild.checked) {
            this.SetAllPages(E.firstChild.checked, true);
            SA = true;
        }
        if (Grids.OnCfgChanged) Grids.OnCfgChanged(this);
        this.SaveCfg();
    }
    this.HideMessage();
    if (Ot || R9) this.ShowMessage(this.GetText("UpdateCfg"));
    var T = this;
    if (Ot) setTimeout(function () {
        T.Render();
    },
    10);
    else {
        if (SA) {
            this.SetScrollBars();
            var J = this.FRow;
            this.FRow = null;
            this.Focus(J, this.FCol);
        }
        if (R9) setTimeout(function () {
            T.Ku();
        },
        10);
    }
};
_7m.prototype.ActionShowColumns = function () {
    var count = this.Toolbar["ColumnsCount"];
    if (!count) count = 1;
    var MN = this.Lang["MenuCfg"],
    BO = this.Img.Style,
    Bp = "<tr><td colspan='" + (count * 2) + "'><div class='" + BO + "CfgMenuSeparator'>" + _7D + "</div></td></tr>";
    var DD = _7A + _7B;
    var QW = this.GetText("ColumnsCaption");
    if (QW) DD += "<tr><td colspan='" + (count * 2) + "' class='" + BO + "CfgMenuHeader' style='text-align:center'>" + QW + "</td></tr>" + Bp;
    for (var i = 0; i < 4; i++) {
        var S = i == 3 ? ["Pager"] : this.ColNames[i],
        BQ = 0;
        for (var BY = 0, Bg = 0; BY < S.length; BY++) {
            var BJ = S[BY],
            C = i == 3 ? this.Pager: this.Cols[BJ];
            if (C.CanHide) {
                var QW = C.MenuName;
                if (!QW) QW = i == 3 ? C.Caption: this.XHeader[BJ];
                if (!QW) QW = BJ;
                if (!Bg) DD += "<tr class='" + BO + "CfgMenuItem'>";
                DD += "<td style='padding-" + (this.HV ? "right": "left") + ":10px;" + (Bg ? "border-" + (this.HV ? "right": "left") + ":1px solid black;": "") + "'";
                DD += " onmouseover='this.className=\"" + BO + "CfgMenuHover\";this.nextSibling.className=\"" + BO + "CfgMenuHover\";'";
                DD += " onmouseout='this.className=\"\";this.nextSibling.className=\"\";'";
                DD += " onclick='this.nextSibling.firstChild.checked=!this.nextSibling.firstChild.checked;'";
                DD += ">" + QW + "</td>";
                DD += "<td id='G" + this.Index + "Cfg" + BJ + "' style='text-align:" + (this.HV ? "left": "right") + ";padding-" + (this.HV ? "left": "right") + ":10px;'";
                DD += " onclick='this.firstChild.checked=!this.firstChild.checked;'";
                DD += " onmouseover='this.className=\"" + BO + "CfgMenuHover\";this.previousSibling.className=\"" + BO + "CfgMenuHover\";'";
                DD += " onmouseout='this.className=\"\";this.previousSibling.className=\"\";'";
                DD += "><input onclick='CancelEvent(event,1)' type='checkbox' " + (C.Visible ? "checked" + _7E: "") + "/></td>";
                Bg++;
                if (Bg == count) {
                    DD += "</tr>";
                    Bg = 0;
                }
                BQ++;
            }
        }
        if (BQ) {
            if (++Bg != count) DD += "</tr>";
            DD += Bp;
        }
    }
    DD += "<tr style='padding-top:10px;'><td colspan='" + (count * 2) + "'><div style='text-align:center'>" + "<button class='" + BO + "CfgMenuButton' onclick='" + this.Z + ".HideColumns(1)'>" + MN["OK"] + "</button>" + "<button class='" + BO + "CfgMenuButton' onclick='" + this.Z + ".HideColumns(0)'>" + MN["Cancel"] + "</button>" + "</div></td></tr>";
    DD += _7C;
    this.ShowMessage(DD, null, null, true);
    this.Message.firstChild.className = BO + "CfgMenu";
    this.Hk(this.Message, this.ShadowMenu);
    if (Grids.OnShowColumns) Grids.OnShowColumns(this, this.Message);
};
_7m.prototype.HideColumns = function (save) {
    if (!save) {
        this.HideMessage();
        return;
    }
    var D3 = this.GetSections(),
    C = this.Cols,
    O3 = new Object,
    id = "G" + this.Index + "Cfg",
    CY = 0;
    var SF = 0;
    for (var Bh = 0; Bh < 3; Bh++) {
        var S = this.ColNames[Bh],
        BQ = 0;
        for (var i = 0; i < S.length; i++) {
            var E = GetElem(id + S[i]),
            BY = C[S[i]];
            if (E) {
                var BZ = E.firstChild.checked;
                if (BY.Visible != BZ) O3[S[i]] = BZ;
                if (BZ) {
                    BQ++;
                    if (Bh != 1) CY += BY.RelWidth ? BY.MinWidth: BY.Width;
                }
            } else if (BY.Visible) {
                if (Bh != 1) CY += BY.RelWidth ? BY.MinWidth: BY.Width;
                BQ++;
            }
        }
        if (Bh == 1 && !BQ) {
            alert(this.GetAlert("ErrHide"));
            return;
        }
        if (!BQ && Bh >= D3[0] && Bh < D3[1] || BQ && (Bh < D3[0] || Bh >= D3[1])) SF = 1;
    }
    var E = GetElem(id + "Pager"),
    BY = this.Pager,
    SG = null;
    if (E && BY.Visible != E.firstChild.checked) {
        SG = !BY.Visible;
        CY += BY.Visible ? -BY.Width: BY.Width;
    }
    if (CY > this.MainTag.offsetWidth - _7Y - 40) {
        alert(this.GetAlert("ErrHideExt"));
        return;
    }
    if (SG != null) this.SE(SG);
    var BQ = 0,
    De = new Array();
    for (var BY in O3) if (C[BY].Visible != O3[BY]) {
        De[C[BY].K] = 1;
        BQ++;
    }
    if (!BQ) {
        this.HideMessage();
        if (SG != null) {
            this.SaveCfg();
            if (_7R || SG == 0) this.SetScrollBars();
            else this.Render(this.GetText("ColUpdate"));
        }
        return;
    }
    if (BQ * this.RowCount > this.SynchroCount * 2) SF = 1;
    if (Grids.OnColumnsChanged) Grids.OnColumnsChanged(this, O3, BQ);
    if (SF || this.Paging >= 2) {
        for (var BY in O3) C[BY].Visible = O3[BY];
        this.SaveCfg();
        this.SetVPos();
        this.Render(this.GetText("ColUpdate"));
    } else {
        for (var BY in O3) {
            C[BY].Visible = O3[BY];
            if (O3[BY]) {
                this.SetVPos();
                this.DY(BY);
            } else {
                this.Da(BY);
                this.SetVPos();
            }
        }
        if (BQ && this.VarHeight) {
            var CE = this.GetFixedRows();
            for (var i = 0; i < CE.length; i++) if (Is(CE[i], "Spanned")) this.UpdateRowHeight(CE[i]);
            for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) if (Is(J, "Spanned")) this.UpdateRowHeight(J);
        }
        for (var i = 0; i < 3; i++) if (De[i]) this.DZ(i);
        this.SetScrollBars();
        this.SaveCfg();
        this.HideMessage();
        this.SetVPos();
    }
};
_7m.prototype.SH = function (BJ) {
    var C = this.Cols[BJ];
    if (!C) return null;
    var SI = 0,
    K = this.ColNames[C.K];
    for (var i = 0; i < K.length; i++) {
        if (BJ == K[i]) break;
        if (this.Cols[K[i]].Visible) SI += this.Cols[K[i]].Width;
    }
    if (C.K == 1) SI -= this.c[1].scrollLeft;
    else if (C.K == 2) SI += this.c[1].offsetWidth;
    if (C.K >= 1 && this.c[0]) SI += this.c[0].offsetWidth;
    return SI;
};
_7m.prototype.SJ = function (row) {
    var BJ = this.AO;
    if (!BJ) return null;
    if (row.Space == null) {
        if (this.Cols[BJ] && !this.Cols[BJ].Visible) BJ = this.GetNextCol(BJ);
        if (row.Spanned && Get(row, BJ + "Span") == 0) BJ = this.GetPrevCol(BJ, row);
        return BJ;
    }
    var SI = this.SH(BJ),
    CY = 0;
    SI += this.Cols[BJ].Width / 2;
    if (!row.Cells) return null;
    for (var i = 0; i < row.Cells.length; i++) {
        BJ = row.Cells[i];
        if (row[BJ + "Visible"] !== 0) {
            var IU = this.GetCell(row, BJ);
            if (IU) CY += IU.offsetWidth;
        }
        if (CY > SI) break;
    }
    if (!this.CanFocus(row, BJ)) {
        var DP = BJ,
        DQ = BJ;
        while (DP || DQ) {
            if (DP) {
                if (!this.CanFocus(row, DP)) DP = this.GetPrevCol(DP, row);
                else {
                    BJ = DP;
                    break;
                }
            }
            if (DQ) {
                if (!this.CanFocus(row, DQ)) DQ = this.GetNextCol(DQ, row);
                else {
                    BJ = DQ;
                    break;
                }
            }
        }
    }
    return BJ;
};
_7m.prototype.JX = function (row, BJ) {
    if (!row || !BJ) return;
    if (row.Space == null) {
        this.AO = BJ;
        return;
    }
    var IU = this.GetCell(row, BJ);
    if (!IU) return;
    var CY = IU.offsetLeft + IU.offsetWidth / 2;
    this.AO = this.GetColLeft(null, CY)[0];
};
_7m.prototype.ActionTabLeft = function () {
    return this.SK(1, 0);
};
_7m.prototype.ActionTabRight = function () {
    return this.SK(0, 0);
};
_7m.prototype.ActionTabLeftEdit = function () {
    return this.SK(1, 1);
};
_7m.prototype.ActionTabRightEdit = function () {
    return this.SK(0, 1);
};
_7m.prototype.ActionGoUp = function () {
    return this.SL(1, 0);
};
_7m.prototype.ActionGoDown = function () {
    return this.SL(0, 0);
};
_7m.prototype.ActionGoUpEdit = function () {
    return this.SL(1, 1);
};
_7m.prototype.ActionGoDownEdit = function () {
    return this.SL(0, 1);
};
_7m.prototype.ActionGoLeft = function () {
    return this.SM(1, 0);
};
_7m.prototype.ActionGoRight = function () {
    return this.SM(0, 0);
};
_7m.prototype.ActionGoLeftEdit = function () {
    return this.SM(1, 1);
};
_7m.prototype.ActionGoRightEdit = function () {
    return this.SM(0, 1);
};
_7m.prototype.SK = function (shift, F1) {
    var SN = shift ? -1 : 1;
    var BJ = this.FCol,
    row = this.FRow,
    Om = this.FPagePos,
    BL;
    if (!BJ || BJ == "Panel") BJ = SN > 0 ? this.GetFirstCol(null, row) : this.GetLastCol(null, row);
    else BJ = shift ? this.GetPrevCol(BJ, row) : this.GetNextCol(BJ, row);
    if (!row || row == this.XHeader) {
        BL = shift ? this.GetPrevShift(null, null, 1) : this.GetNextShift(null, null, 1);
        row = BL[0];
        Om = BL[1];
    }
    while (true) {
        if (!BJ) {
            BL = shift ? this.GetPrevShift(row, Om, 1) : this.GetNextShift(row, Om, 1);
            row = BL[0];
            Om = BL[1];
            BJ = shift ? this.GetLastCol(null, row) : this.GetFirstCol(null, row);
            if (!row) break;
        }
        if (!F1 && this.CanFocus(row, BJ) || F1 && !row.Page && this.CanEdit(row, BJ)) {
            this.Focus(row, BJ);
            if (_7R) this.e.focus();
            this.JX(row, BJ);
            return true;
        }
        BJ = shift ? this.GetPrevCol(BJ, row) : this.GetNextCol(BJ, row);
    }
    if (!row) {
        if (Grids.OnTabOutside && Grids.OnTabOutside(this, shift ? -1 : 1)) {
            this.Focus(null, null);
            Grids.Focused = null;
        }
    }
    return false;
};
_7m.prototype.SL = function (shift, F1) {
    if (this.EditMode && this.AutoSort) return false;
    var I7 = this.FRow,
    Eb = this.FCol;
    if ((_7G || _7M || _7H) && this.EditMode && I7 && Eb && this.GetType(I7, Eb) == "Enum") return false;
    var BL = shift ? this.GetPrevShift(I7, this.FPagePos, 1) : this.GetNextShift(I7, this.FPagePos, 1);
    var row = BL[0],
    Om = BL[1];
    while (row) {
        if (row.Page) {
            if (F1) this.ScrollIntoView(row, Eb, Om);
            else this.Focus(row, Eb, Om);
            return true;
        }
        var BJ = this.SJ(row);
        if (!F1 && this.CanFocus(row, BJ) || F1 && this.CanEdit(row, BJ)) {
            this.Focus(row, BJ);
            return true;
        }
        var BL = shift ? this.GetPrevShift(row, Om, 1) : this.GetNextShift(row, Om, 1);
        row = BL[0];
        Om = BL[1];
    }
    return false;
};
_7m.prototype.SM = function (shift, F1) {
    var I7 = this.FRow,
    BJ = this.FCol;
    if (!I7) return false;
    if (!BJ || BJ == "Panel") BJ = this.GetFirstCol();
    else BJ = shift ? this.GetPrevCol(BJ, I7) : this.GetNextCol(BJ, I7);
    while (BJ && (this.Cols[BJ] || I7.Space != null)) {
        if (!F1 && this.CanFocus(I7, BJ) || F1 && this.CanEdit(I7, BJ)) {
            this.Focus(I7, BJ);
            this.JX(I7, BJ);
            return true;
        }
        BJ = shift ? this.GetPrevCol(BJ, I7) : this.GetNextCol(BJ, I7);
    }
    return false;
};
_7m.prototype.ActionGoFirst = function () {
    if (this.AllPages && this.XB.firstChild.State < 2) {
        this.Focus(this.XB.firstChild, this.FCol, 0);
        return true;
    }
    var J = this.GetFirstVisible(this.AllPages ? null: _7c(this.XB, this.AP), 1);
    if (J) {
        var BJ = this.SJ(J);
        this.Focus(J, BJ);
    }
};
_7m.prototype.ActionGoLast = function () {
    if (this.AllPages && this.XB.lastChild.State < 2) {
        var J = this.XB.lastChild;
        if (J.SO && this.PageLengthDiv > 1) {
            this.SP(J);
            this.SetScrollBars();
        }
        this.Focus(J, this.FCol, J.Count - 1);
        return true;
    }
    var J = this.GetLastVisible(this.AllPages ? null: _7c(this.XB, this.AP), 1);
    if (J) {
        var BJ = this.SJ(J);
        this.Focus(J, BJ);
    }
    return true;
};
_7m.prototype.ActionPageUp = function () {
    var JE = false;
    if (!this.AllPages && this.GoToPrevPage()) JE = true;
    if (!JE) {
        var J = this.FRow,
        Lx = this.FPagePos;
        var BQ = Math.floor(this.c[1].offsetHeight / this.RowHeight);
        if (J) {
            var BL = this.GetPrevShift(J, Lx, BQ);
            J = BL[0];
            Lx = BL[1];
        }
        if (!J || J.Fixed) {
            J = this.GetFirstVisible(null, 1);
        }
        if (J) {
            var BJ = this.SJ(J);
            this.Focus(J, BJ, Lx);
        }
    }
    return true;
};
_7m.prototype.ActionPageDown = function () {
    var JE = false;
    if (!this.AllPages && this.GoToNextPage && this.GoToNextPage()) JE = true;
    if (!JE) {
        var J = this.FRow,
        Lx = this.FPagePos;
        var BQ = Math.floor(this.c[1].offsetHeight / this.RowHeight);
        if (J) {
            var BL = this.GetNextShift(J, Lx, BQ);
            J = BL[0];
            Lx = BL[1];
        }
        if (!J || J.Fixed) {
            var J = this.GetLastVisible(null, 1);
        }
        if (J) {
            var BJ = this.SJ(J);
            this.Focus(J, BJ, Lx);
        }
    }
    return true;
};
_7m.prototype.TabInside = function (event, move) {
    if (GetKey(event) == 9) {
        if (move > 0 && !event.shiftKey) {
            if (this.FRow) this.Focus(null, null);
            this.Jx(event);
            return true;
        } else if (move < 0 && event.shiftKey) {
            if (this.FRow) this.Focus(null, null);
            this.Jx(event);
            return true;
        }
    }
    return false;
};
_7m.prototype.SQ = function (row, count) {
    if (row.SO && row.Height != null) return row.Height;
    if (!count && row.r1) return row.r1.offsetHeight;
    if (row.State < 2) return row.Count * this.RowHeight;
    var F9 = row,
    BQ = 0;
    if (!row.firstChild) return 0;
    while (1) {
        var J = row.firstChild;
        if (!J || (!row.Expanded || !row.Visible) && !row.Page) {
            J = row.nextSibling;
            if (!J) {
                J = row.parentNode;
                while (J != F9 && !J.nextSibling) J = J.parentNode;
                if (J != F9) J = J.nextSibling;
            }
        }
        if (!J || J == F9) break;
        if (J.Visible) BQ++;
        row = J;
    }
    return BQ * this.RowHeight;
};
_7m.prototype.Pn = function (H) {
    H.Level = -1;
    H.LevelImg = "";
    H.Page = 1;
    H.State = this.Paging >= 2 || !this.AllPages ? 2 : 4;
    if (this.Paging == 1 && !this.AllPages && this.AP == this.XB.childNodes.length - 2) H.State = 4;
};
_7m.prototype.FA = function (Bx) {
    var H = _7f.createElement("B");
    this.XB.appendChild(H);
    this.Pn(H);
    if (Bx) {
        var Bg = H.previousSibling,
        SR = H.State < 4 ? this.SQ(H, true) : 0,
        CZ;
        for (var i = 0; i < 3; i++) {
            if (Bg) {
                CZ = Bg["r" + i];
                if (CZ) CZ = CZ.parentNode;
            } else CZ = this.Kt(i);
            if (CZ) {
                var Ck = document.createElement("div");
                CZ.appendChild(Ck);
                if (SR) Ck.style.height = SR + "px";
                Ck.className = this.Img.Style + "Page";
            }
        }
    }
    return H;
};
_7m.prototype.SS = function (H) {
    if (this.AllPages) for (var i = 0; i < 3; i++) {
        var CZ = H["r" + i];
        if (CZ) {
            H["r" + i] = null;
            CZ.row = null;
            CZ.parentNode.removeChild(CZ);
        }
    }
    this.XB.removeChild(H);
    H.Removed = 1;
    if (!this.AllPages) {
        while (this.AP >= this.XB.childNodes.length) this.AP--;
        if (this.Paging == 3) this.UpdateBody();
    }
};
_7m.prototype.ST = function (row) {
    var J = row.firstChild,
    i;
    var D3 = this.GetSections(),
    DP = D3[0],
    DQ = D3[1];
    var QO = DP == 0,
    SU = DQ == 3,
    SV, RQ, Cw;
    if (!row.r1) return;
    if (row.Page) {
        if (QO) SV = row.r0.firstChild;
        RQ = row.r1.firstChild;
        if (SU) Cw = row.r2.firstChild;
    } else {
        if (QO) SV = row.r0.nextSibling.firstChild;
        RQ = row.r1.nextSibling.firstChild;
        if (SU) Cw = row.r2.nextSibling.firstChild;
    };
    if (!RQ || RQ.nodeType != 1) {
        _7d(row);
        return;
    }
    var CE = new Function("var A = Grids.Active;if(A){ A.ARow = this.row; A.ASec = 0;}");
    var Ew = Grids.OnRenderRow != null;
    function _7d(row) {
        for (var J = row.firstChild; J; J = J.nextSibling) {
            J.r1 = null;
            if (J.State > 2) J.State = 2;
            if (J.firstChild) _7d(J);
        }
    };
    while (J) {
        var BZ = J.Visible;
        if (BZ) {
            if (QO) {
                J.r0 = SV;
                SV.row = J;
                SV.onmousemove = CE;
            }
            J.r1 = RQ;
            RQ.row = J;
            RQ.onmousemove = CE;
            if (SU) {
                J.r2 = Cw;
                Cw.row = J;
                Cw.onmousemove = CE;
            }
            if (Ew) Grids.OnRenderRow(this, J);
        } else {
            J.r1 = null;
            if (J.State > 2) J.State = 2;
        };
        if (this.VarHeight) this.UpdateRowHeight(J);
        if (J._7Ax) {
            if (J._7Ax == 4) {
                if (QO) SV = SV.nextSibling.firstChild;
                RQ = RQ.nextSibling.firstChild;
                if (SU) Cw = Cw.nextSibling.firstChild;
                J = J.firstChild;
                continue;
            } else {
                if (QO) SV = SV.nextSibling;
                RQ = RQ.nextSibling;
                if (SU) Cw = Cw.nextSibling;
                _7d(J);
            }
        }
        if (J.nextSibling) {
            if (BZ && RQ.nextSibling) {
                if (QO) SV = SV.nextSibling;
                RQ = RQ.nextSibling;
                if (SU) Cw = Cw.nextSibling;
            }
            J = J.nextSibling;
        } else {
            while (J != row && !J.nextSibling) {
                J = J.parentNode;
                if (QO) SV = SV.parentNode;
                RQ = RQ.parentNode;
                if (SU) Cw = Cw.parentNode;
            }
            if (J == row) break;
            J = J.nextSibling;
            if (RQ.nextSibling) {
                if (QO) SV = SV.nextSibling;
                RQ = RQ.nextSibling;
                if (SU) Cw = Cw.nextSibling;
            }
        }
    }
};
_7m.prototype.SW = function () {
    var D3 = this.GetSections(),
    DP = D3[0],
    DQ = D3[1],
    i;
    if (!this.AllPages) {
        var J = _7c(this.XB, this.AP),
        H;
        for (i = DP; i < DQ; i++) {
            H = this.Kt(i);
            J["r" + i] = H;
            H.row = J;
        }
        if (J.State == 4) this.ST(J);
    } else {
        var BQ = 0,
        SX, SY, SZ;
        if (!DP) SX = this.Kt(0);
        SY = this.Kt(1);
        if (DQ > 2) SZ = this.Kt(2);
        for (var J = this.XB.firstChild; J; J = J.nextSibling) {
            if (J.SO) {
                if (!BQ) {
                    SY.Sa = J;
                    SY.Count = 1;
                } else SY.Count++;
                SY.Ri = J;
                J.Height = J.State < 2 ? J.Count * this.RowHeight: this.SQ(J);
                if (SX) J.r0 = SX;
                J.r1 = SY;
                if (SZ) J.r2 = SZ;
                if (++BQ == this.FastPages) {
                    if (SX) SX = SX.nextSibling;
                    SY = SY.nextSibling;
                    if (SZ) SZ = SZ.nextSibling;
                    BQ = 0;
                }
            } else {
                if (SX) {
                    J.r0 = SX;
                    SX.row = J;
                    SX = SX.nextSibling;
                }
                J.r1 = SY;
                SY.row = J;
                SY = SY.nextSibling;
                if (SZ) {
                    J.r2 = SZ;
                    SZ.row = J;
                    SZ = SZ.nextSibling;
                }
                if (J.State == 4) this.ST(J);
            }
        }
    }
};
_7m.prototype.FF = function (row, Sb) {
    if (row.State == 4 || this.Loading || Sb != null && this.Ax != Sb) return;
    if (Grids.OnRenderPageStart) Grids.OnRenderPageStart(this, row);
    if (this.Sc) {
        this.UpdateRowHeight(this.XHeader);
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; CE++) this.UpdateRowHeight(CE[i]);
        this.Sc = null;
    }
    if (row.Page && !this.AllPages && this.OnePage) {
        if (this.OnePage & 4 && this.PU) {
            this.QK();
            this.PU(1);
        }
        if (this.OnePage & 2 && this.Kk) this.Kk(true);
        if (this.OnePage & 1 && this.Sorting && this.Pm) this.Pm(row, false);
        if (this.OnePage & 7) {
            var C = this.Cols[this.MainCol];
            if (C) for (var J = row.firstChild; J; J = J.nextSibling) this.Sd(J, false, C);
        }
    }
    var BL = new Array(),
    page = row.Page;
    for (var i = 0; i < 3; i++) {
        var J = row["r" + i];
        if (J) {
            if (!page) J = J.nextSibling;
            BL.length = 0;
            this.Se(BL, row, i);
            if (BL.length == 0) {
                J.style.height = "1px";
                J.style.overflow = "hidden";
                J.innerHTML = _7D;
            } else {
                J.innerHTML = BL.join("");
                J.style.overflow = "";
                if (!_7U) J.style.height = "";
            };
            if (!page) J.style.display = "";
        }
    }
    BL = null;
    row.State = 4;
    this.Refresh();
    this.ST(row);
    if (this.FRow == row && row.Page) {
        this.Focus(this.PagePosToRow(row, this.FPagePos), this.FCol);
        this.FPagePos = null;
    }
    this.SetScrollBars();
    if (Grids.OnRenderPageFinish) Grids.OnRenderPageFinish(this, row);
    if (this.Paging) this.ShowPages();
    if (this.H3) this.Mo(null, row);
};
_7m.prototype.UpdateBody = function () {
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, true) == -1) return;
    this.Clear(0);
    for (var BZ = 0; BZ < 3; BZ++) {
        if (this.c[BZ]) this.c[BZ].innerHTML = this.Sf(BZ);
    }
    this.SW();
    if (!this.Paging) this.FF(this.XB.firstChild);
    this.SetScrollBars();
};
_7m.prototype.CK = function (Sg) {
    var Df = this.HasChanges ? this.HasChanges() : 0;
    if (Grids.OnCanReload) return Grids.OnCanReload(this, Df, Sg);
    var BZ = this.ReloadChanged;
    if (! (BZ & 4)) Df &= ~2;
    if (Df) {
        BZ &= 3;
        if (BZ == 3) BZ = confirm(this.GetAlert("CanReloadStart") + (Df & 1 ? this.GetAlert(Sg ? "CanCancelChanges": "CanReloadChanges") : "") + ((Df & 3) == 3 ? this.GetAlert("And") : "") + (Df & 2 ? this.GetAlert("CanReloadSelect") : "") + this.GetAlert("CanReloadEnd")) ? 1 : 0;
        if (BZ == 1 && Df & 1 && !Sg && this.Save) this.Save();
        return !! BZ;
    }
    return true;
};
_7m.prototype.Sh = function () {
    var BO = " class='" + this.Img.Style;
    for (var Bg = this.XB.firstChild, Ce = 1; Bg; Bg = Bg.nextSibling, Ce++) {
        var E = "",
        Si = "";
        for (var i = 0; i < 3; i++) {
            var B3 = "Name0" + i;
            if (!Bg[B3]) break;
            if (E) {
                E += ", ";
                Si += ", ";
            }
            E += "<b" + BO + "PagerSort" + (i + 1) + "'>" + Bg[B3] + "</b>";
            Si += Bg[B3];
        }
        if (Bg["Name1" + i] || Bg["Name2" + i]) {
            if (E) {
                E += "<b" + BO + "PagerSortS'> | </b>";
                Si += _7R ? "\n": " | ";
            }
            if (!Bg["Name1" + i]) {
                E += "<span" + BO + "PagerSort" + (i + 1) + "'>...</span>";
                Si += "...";
            } else for (var j = i; j < 3 && Bg["Name1" + j]; j++) {
                E += (j == i ? "": ", ") + "<span" + BO + "PagerSort" + (j + 1) + "'>" + Bg["Name1" + j] + "</span>";
                Si += (j == i ? "": ", ") + Bg["Name1" + j];
            }
            E += "<span" + BO + "PagerSortS'> =&gt; </span>";
            Si += _7R ? "\n": " => ";
            if (!Bg["Name2" + i]) {
                E += "<span" + BO + "PagerSort" + (i + 1) + "'>...</span>";
                Si += "...";
            } else for (var j = i; j < 3 && Bg["Name2" + j]; j++) {
                E += (j == i ? "": ", ") + "<span" + BO + "PagerSort" + (j + 1) + "'>" + Bg["Name2" + j] + "</span>";
                Si += (j == i ? "": ", ") + Bg["Name2" + j];
            }
        }
        Bg.Name = E ? E: (Bg.Name ? Bg.Name: this.GetText("Page") + " " + Ce);
        if (Si) Bg.Title = Si;
        if (Grids.OnSetPageName) Grids.OnSetPageName(this, Bg);
    }
    if (Ce == 2) {
        Bg = this.XB.firstChild;
        if (Bg.Name == this.GetText("Page") + " " + 1) Bg.Name = this.GetText("NoPages");
        if (Grids.OnSetPageName) Grids.OnSetPageName(this, Bg);
    }
};
_7m.prototype.Sj = function () {
    function GetString(T, row, BJ) {
        var BR = Get(row, BJ + "PageNameValue");
        if (BR == null) BR = T.GetString(row, BJ, 2);
        if (Grids.OnGetPageNameValue) BR = Grids.OnGetPageNameValue(T, row, BJ, BR);
        return BR;
    };
    if (this.PageLength == 1) {
        var BJ = this.MainCol ? this.MainCol: this.AX ? this.SortCols[0] : null;
        if (BJ) {
            for (var Bg = this.XB.firstChild, Ce = 1; Bg; Bg = Bg.nextSibling, Ce++) {
                var J = this.GetFirstVisible(Bg);
                Bg.Name = J ? GetString(this, J, BJ, 2) : Ce;
                if (J) Bg.Title = GetString(this, J, BJ, 2);
                if (Grids.OnSetPageName) Grids.OnSetPageName(this, Bg);
            }
            return;
        }
    }
    if (this.XB.childNodes.length == 1) {
        this.XB.firstChild.Name = this.GetText("NoPages");
        return;
    }
    var BQ = this.Sorting ? this.AX: 0,
    C = this.Cols;
    var Sk = this.Pager["MaxColumns"];
    if (Sk != null && Sk < BQ) BQ = Sk;
    function Sl(T, BL, Bb) {
        var a = Bb ? Bg.nextSibling: Bg.previousSibling,
        Bc = Bb ? Bg.lastChild: Bg.firstChild;
        if (!a) {
            if (Bb) {
                while (Bc && !Bc.Visible) Bc = Bc.previousSibling;
            } else {
                while (Bc && !Bc.Visible) Bc = Bc.nextSibling;
            };
            if (Bc) BL[0] = Get(Bc, T.SortCols[0] + "Visible") == 0 ? "": GetString(T, Bc, T.SortCols[0], 2);
        } else {
            a = Bb ? a.firstChild: a.lastChild;
            if (Bb) {
                while (a && !a.Visible) a = a.nextSibling;
                while (Bc && !Bc.Visible) Bc = Bc.previousSibling;
            } else {
                while (a && !a.Visible) a = a.previousSibling;
                while (Bc && !Bc.Visible) Bc = Bc.nextSibling;
            };
            if (!a || !Bc) return;
            for (var i = 0; i < BQ; i++) {
                var BY = T.SortCols[i];
                if (T.Sm(i, a, Bc) == 0) BL[i] = Get(Bc, BY + "Visible") == 0 ? "": GetString(T, Bc, BY, 2);
                else {
                    if (C[BY].Type != "Text" && C[BY].Type != "Lines") BL[i] = Get(Bc, BY + "Visible") == 0 ? "": GetString(T, Bc, BY, 2);
                    else {
                        var Bh = Get(Bc, BY + "Visible") == 0 ? "": GetString(T, Bc, BY, 2) + "",
                        BN = _7Ad(GetString(T, a, BY, 2), Bh, T.SortTypes[i]);
                        if (BN + 3 < Bh.length) BL[i] = Bh.slice(0, BN + 1);
                        else BL[i] = Bh;
                    };
                    break;
                }
            }
        }
    };
    if (BQ) {
        var BO = " class='" + this.Img.Style;
        for (var Bg = this.XB.firstChild, Ce = 1; Bg; Bg = Bg.nextSibling, Ce++) {
            var Sn = new Array(),
            So = new Array();
            Sl(this, Sn, 0);
            Sl(this, So, 1);
            var E = "",
            Si = "";
            for (var i = 0; i < 3; i++) {
                if (Sn[i] == null || So[i] == null || CompareStrings(Sn[i], So[i], this.SortTypes[i]) != 0) break;
                if (E) {
                    E += ", ";
                    Si += ", ";
                }
                if (Sn[i]) {
                    E += "<b" + BO + "PagerSort" + (i + 1) + "'>" + Sn[i] + "</b>";
                    Si += Sn[i];
                }
            }
            if (Sn[i] != null || So[i] != null) {
                if (E) {
                    E += "<b" + BO + "PagerSortS'> | </b>";
                    Si += _7R ? "\n": " | ";
                }
                if (Sn[i] == null || Sn[i] == "") {
                    E += "<span" + BO + "PagerSort" + (i + 1) + "'>...</span>";
                    Si += "...";
                } else for (var j = i; j < Sn.length; j++) {
                    E += (j == i ? "": ", ") + "<span" + BO + "PagerSort" + (j + 1) + "'>" + Sn[j] + "</span>";
                    Si += (j == i ? "": ", ") + Sn[j];
                }
                E += "<span" + BO + "PagerSortS'> =&gt; </span>";
                Si += _7R ? "\n": " => ";
                if (So[i] == null || So[i] == "") E += "<span" + BO + "PagerSort" + (i + 1) + "'>...</span>";
                else for (var j = i; j < So.length; j++) {
                    E += (j == i ? "": ", ") + "<span" + BO + "PagerSort" + (j + 1) + "'>" + So[j] + "</span>";
                    Si += (j == i ? "": ", ") + So[j];
                }
            }
            Bg.Name = E ? E: this.GetText("Page") + " " + Ce;
            if (Si) Bg.Title = Si;
            if (Grids.OnSetPageName) Grids.OnSetPageName(this, Bg);
        }
    } else for (var Bg = this.XB.firstChild, i = 1; Bg; Bg = Bg.nextSibling, i++) {
        Bg.Name = this.GetText("Page") + " " + i;
        if (Grids.OnSetPageName) Grids.OnSetPageName(this, Bg);
    }
};
_7m.prototype.KF = function (IL) {
    var J4 = EventXYToElement(IL, this.h);
    var y = J4[1] + this.h.scrollTop;
    var Bg = Math.floor(y / this.l.firstChild.offsetHeight);
    return Bg >= 0 && _7c(this.XB, Bg) ? Bg: null;
};
_7m.prototype.PagerClick = function (IL) {
    if (_7G && this.h.scrollHeight != this.h.offsetHeight) {
        var BL = EventXYToElement(IL, this.h, false);
        if (this.h.offsetWidth - BL[0] <= 28) return;
    }
    var Bg = this.KF(IL);
    if (Bg != null) {
        if (this.EditMode && this.EndEdit(this.FRow, this.FCol) == -1) return;
        this.GoToPage(_7c(this.XB, Bg));
        CancelEvent(IL);
    }
};
_7m.prototype.UpdatePager = function () {
    if (!this.Paging) return;
    if (this.Pager.Visible) this.l.innerHTML = this.Sp();
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Pager") this.RefreshRow(J);
};
_7m.prototype.SE = function (Bx) {
    if (this.Pager.Visible && Bx || !this.Pager.Visible && !Bx) return;
    this.Pager.Visible = Bx ? 1 : 0;
    this.UpdatePager();
    this.f.parentNode.style.display = Bx ? "": "none";
    if (_7R) {
        for (var J = this.XS.firstChild; J; J = J.nextSibling) {
            if (J.Space == 0 || J.Space >= 4) J.r1.parentNode.colSpan += Bx ? 1 : -1;
        }
    }
    this.Dg();
    if (this.ASec == -6) this.ASec = -5;
    this.HoverPager(null);
};
_7m.prototype.HoverPager = function (Bg) {
    if (Bg != this.AV) {
        if (this.Hover >= 1 && this.AN < 1000) {
            {
                if (this.AV != null) this.l.childNodes[this.AV].className = this.Img.Style + "PagerItem";
                if (Bg != null) this.l.childNodes[Bg].className = this.Img.Style + "PagerHover";
            };
            if (Grids.Az && Bg != null) this.GoToPage(_7c(this.XB, Bg));
        }
        this.AV = Bg;
    }
};
_7m.prototype.GetPage = function (Bb) {
    return _7c(this.XB, Bb);
};
_7m.prototype.GetPageNum = function (row) {
    for (var J = this.XB.firstChild, Bb = 0; J; J = J.nextSibling, Bb++) if (J == row) return Bb;
    return 0;
};
_7m.prototype.CreatePages = function () {
    var BQ = this.PageLength,
    Sq = this.FA(),
    body = Sq;
    var H = this.XB.firstChild;
    while (H && H != Sq) {
        var J = H.firstChild;
        while (J) {
            if (J.Visible) {
                if (!BQ) {
                    BQ = this.PageLength;
                    body = this.FA();
                }
                BQ--;
            }
            var B3 = J.nextSibling;
            body.appendChild(J);
            J = B3;
        }
        var D = H.nextSibling;
        this.XB.removeChild(H);
        H = D;
    }
    if (this.FRow) this.AP = this.GetPageNum(this.GetRowPage(this.FRow));
    else if (this.AP >= this.XB.childNodes.length) this.AP = 0;
    if (this.Sj) this.Sj();
};
_7m.prototype.Kp = function () {
    var XB = this.XB,
    P = XB.firstChild;
    for (var H = P.nextSibling; H; H = P.nextSibling) {
        for (var J = H.firstChild; J; J = H.firstChild) P.appendChild(J);
        XB.removeChild(H);
    }
};
_7m.prototype.ShowPages = function () {
    if (this.Rendering || this.Loading || this.Ae) return;
    var BL = this.Sr();
    for (var i = 0; i < BL.length - 1; i += 2) {
        var Bg = _7c(this.XB, BL[i]);
        if (Bg.State == 0 || Bg.State == 2) {
            Bg.State++;
            var CE = new Function("p", "setTimeout(function(){if(" + this.Z + ")" + this.Z + ".StartPage(p,0," + this.Ax + ");}," + (this.Paging > 1 && this.AllPages ? this.PageTime: 10) + ");");
            CE(Bg);
        }
    }
};
_7m.prototype.SP = function (row) {
    var D3 = this.GetSections(),
    RQ = 0,
    Cw = 0,
    BQ = 0,
    BO = this.Img.Style,
    Ss = this.PageLengthDiv;
    for (var Bg = row.r1.Sa; Bg != row; Bg = Bg.nextSibling) {
        RQ += Bg.Height / Ss;
        BQ++;
    }
    if (row != row.r1.Ri) {
        for (var Bg = row.nextSibling;; Bg = Bg.nextSibling) {
            Cw += Bg.Height / Ss;
            if (Bg == row.r1.Ri) break;
        }
    }
    if (this.PageLengthDiv > 1) {
        var CE = this.FRow;
        if (CE && !CE.Fixed) {
            CE = this.GetRowPage(CE);
            if ((row == CE || this.GetPageNum(row) < this.GetPageNum(CE)) && this.St(CE)) {
                var BV = function () {
                    if (CE.State == 3 || CE.State == 1) setTimeout(BV, 100);
                    else {
                        T.SetScrollBars();
                        T.ScrollIntoView(T.FRow);
                    }
                };
                var T = this;
                setTimeout(BV, 10);
            }
        }
    }
    function Su() {
        var Ck = document.createElement("div");
        Ck.style.overflow = "hidden";
        Ck.style.height = CZ + "px";
        Ck.className = BO + "Page";
        return Ck;
    };
    var CZ = row.Height;
    row.SO = null;
    if (RQ == 0) {
        if (Cw == 0) return;
        row.r1.Sa = row.nextSibling;
        row.r1.Count--;
        for (var BY = D3[0]; BY < D3[1]; BY++) {
            var J = row["r" + BY],
            Ck = Su();
            J.style.height = Cw + "px";
            J.parentNode.insertBefore(Ck, J);
            row["r" + BY] = Ck;
            Ck.row = row;
        }
    } else if (Cw == 0) {
        row.r1.Ri = row.previousSibling;
        row.r1.Count--;
        for (var BY = D3[0]; BY < D3[1]; BY++) {
            var J = row["r" + BY],
            Ck = Su();
            J.style.height = RQ + "px";
            if (J.nextSibling) J.parentNode.insertBefore(Ck, J.nextSibling);
            else J.parentNode.appendChild(Ck);
            row["r" + BY] = Ck;
            Ck.row = row;
        }
    } else {
        var I7 = row.r1.Sa;
        row.r1.Sa = row.nextSibling;
        row.r1.Count = row.r1.Count - BQ - 1;
        for (var BY = D3[0]; BY < D3[1]; BY++) {
            var Sv = "r" + BY,
            J = row[Sv],
            Ck = Su();
            J.style.height = Cw + "px";
            var Ev = Su();
            Ev.style.height = RQ + "px";
            J.parentNode.insertBefore(Ev, J);
            J.parentNode.insertBefore(Ck, J);
            row[Sv] = Ck;
            Ck.row = row;
            if (BY == 1) {
                Ev.Sa = I7;
                Ev.Ri = row.previousSibling;
                Ev.Count = BQ;
            }
            for (var BG = I7, Sw = row.previousSibling; BG != Sw; BG = BG.nextSibling) BG[Sv] = Ev;
            Sw[Sv] = Ev;
        }
    };
};
_7m.prototype.StartPage = function (row, OG, Sb) {
    if (this.Loading || Sb != null && this.Ax != Sb) return;
    if (!OG && !this.St(row)) {
        if (row.State == 1 || row.State == 3) row.State--;
        return;
    }
    if (row.State == 4) return;
    if (row.SO && this.AllPages) this.SP(row);
    var Q3 = this.GetRow(row, 1);
    if (row.State == 1) {
        row = this.E0(row);
        Q3.style.width = "100%";
        if (Q3.offsetHeight > 40 && !this.SuppressMessage) _7Ak(this.GetText("Load"), Q3, this.Img.Style + "PageMessage", this.c[1].offsetHeight);
        var T = this;
        this.DownloadPage(row, function (E3) {
            T.Sx(E3, row, Sb);
        });
    } else {
        Q3.style.width = "100%";
        if (Q3.offsetHeight > 40 && !this.SuppressMessage) _7Ak(this.GetText("Render"), Q3, this.Img.Style + "PageMessage", this.c[1].offsetHeight);
        var T = this;
        setTimeout(function () {
            T.FF(row, Sb);
        },
        10);
    }
};
_7m.prototype.Sx = function (E3, row, Sb) {
    if (this.Loading || Sb != null && this.Ax != Sb) return;
    if (E3 < 0) {
        row.State = 0;
        if (row.tagName == "B") _7Ak(this.GetText("PageErr"), this.GetRow(row, 1), this.Img.Style + "PageMessage", this.c[1].offsetHeight);
    } else {
        this.F5(row);
        if (row.Page) this.EY(row);
        else if (!this.Sorting || !this.Pm || !this.Pm(row)) this.EZ(row);
        this.Dz(false);
        if (this.MainCol) {
            var C = this.Cols[this.MainCol],
            T = this;
            function Ef(row) {
                for (var J = row.firstChild; J; J = J.nextSibling) {
                    T.Ef(J, false, C);
                    if (J.firstChild) Ef(J);
                }
            };
            if (row.Page) {
                row.Level = -1;
                row.LevelImg = "";
            }
            Ef(row);
        }
        row.State = 3;
        row.Count = null;
        if (!Grids.OnCalculate || !Grids.OnCalculate(this, 0, row)) {
            var F = this.Bv();
            for (var J = row.firstChild; J; J = J.nextSibling) this.Bw(J, F, "", 1);
        }
        if (row.tagName == "B") {
            if (row.firstChild || this.XB.childNodes.length == 1) this.StartPage(row);
            else if (this.XB.childNodes.length > 1) {
                this.SS(row);
                if (this.UpdatePager) this.UpdatePager();
                this.SetScrollBars();
            }
        } else if (row.Deleted) for (var J = this.GetNext(row); J && row.Level < J.Level; J = this.GetNext(J)) if (!J.Deleted) J.Deleted = 2;
    }
};
_7m.prototype.GoToPageNum = function (Bb) {
    var row = _7c(this.XB, Bb - 1);
    if (row) this.GoToPage(row);
};
_7m.prototype.GoToPage = function (row) {
    if (Grids.OnGoToPage && Grids.OnGoToPage(this, row, this.GetPageNum(row))) return;
    var Sy = this.FRow ? this.FRow.parentNode: null;
    var Sz = Sy == this.XH || Sy == this.XF;
    if (!Sz) {
        var I = this.FPagePos;
        if (I == null && this.FRow) I = this.GetPagePos(this.FRow);
        if (I == null) I = 0;
    }
    if (this.AllPages) {
        this.ScrollIntoView(row, this.FCol);
        if (!Sz) this.Focus(row, this.FCol, I);
    } else {
        var B3 = this.AP,
        J = _7c(this.XB, B3);
        if (J == row) return;
        J.State = 2;
        J.r1 = null;
        if (row.State == null) row.State = this.Paging == 1 ? 4 : 2;
        this.AP = this.GetPageNum(row);
        this.UpdateBody();
        if (!Sz) this.Focus(row, this.FCol, I);
        this.SetScrollBars();
    }
};
_7m.prototype.Ku = function (F1) {
    this.Po(false);
    for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) if (Bg.State == 4) Bg.State = 2;
    if (!this.AllPages) {
        var Bg = _7c(this.XB, this.AP);
        if (Bg.State < 2) {
            Bg = this.E0(Bg);
            if (this.DownloadPage(Bg) < 0) return;
            Bg.Count = null;
        }
        Bg.State = 4;
    }
    this.UpdateBody();
    if (this.UpdatePager) this.UpdatePager();
    this.HideMessage();
    var BG = this.FRow;
    if (BG && !BG.Fixed) {
        if (!this.AllPages && this.GoToPage) this.GoToPage(this.GetRowPage(BG));
        else this.FRow = null;
        this.Focus(BG, this.FCol, this.FPagePos);
        if (F1 && BG == this.FRow) this.StartEdit(BG, this.FCol);
    }
    this.SetScrollBars();
    this.FE();
    this.Dz();
};
_7m.prototype.ActionRepaint = function () {
    this.Ku();
    return true;
};
_7m.prototype.SetAllPages = function (BR, S0) {
    if (!this.Paging) return;
    BR = !!BR;
    if (BR) {} else {
        var Bg = this.FRow ? this.GetRowPage(this.FRow) : null;
        if (!Bg) {
            var BL = this.Sr();
            if (BL.length == 3) Bg = BL[0];
            else if (BL.length == 4) Bg = BL[1] > BL[3] ? BL[0] : BL[2];
            else Bg = BL[2];
        }
        this.AP = this.GetPageNum(Bg);
    };
    this.AllPages = BR;
    for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) {
        if (Bg.State >= 2) Bg.State = this.Paging == 1 && this.AllPages ? 4 : 2;
        Bg.r0 = null;
        Bg.r1 = null;
        Bg.r2 = null;
    }
    if (this.Paging == 1) _7c(this.XB, this.AP).State = 4;
    if (S0) {
        this.UpdateBody();
    }
};
_7m.prototype.GoToNextPage = function (BQ) {
    if (this.AllPages) {
        var J = this.FRow;
        if (!J || J.Fixed) {
            var BL = this.Sr();
            if (!BL[3]) J = _7c(this.XB, BL[0]).nextSibling;
            else {
                J = _7c(this.XB, BL[BL.length - 2]);
                if (BL[BL.length - 1] >= 1) J = J.nextSibling;
            }
        } else J = this.GetRowPage(J).nextSibling;
    } else {
        var J = _7c(this.XB, this.AP);
        if (J) J = J.nextSibling;
    };
    if (!J) return false;
    this.GoToPage(J);
    return true;
};
_7m.prototype.GoToPrevPage = function () {
    if (this.AllPages) {
        var J = this.FRow;
        if (!J || J.Fixed) {
            var BL = this.Sr();
            J = _7c(this.XB, BL[0]);
            if (BL[1] >= 1) J = J.previousSibling;
        } else J = this.GetRowPage(J).previousSibling;
    } else {
        var J = _7c(this.XB, this.AP);
        if (J) J = J.previousSibling;
    };
    if (!J) return false;
    this.GoToPage(J);
    return true;
};
_7m.prototype.GetFPage = function () {
    if (!this.Paging || !this.c[1]) return null;
    if (!this.AllPages) return _7c(this.XB, this.AP);
    var J = this.FRow;
    if (!J || J.Fixed) {
        var BL = this.Sr();
        return _7c(this.XB, BL[3] > BL[1] ? BL[2] : BL[0]);
    }
    return this.GetRowPage(J);
};
_7m.prototype.AddPage = function (name, xml, count) {
    var H = _7f.createElement("B");
    H.Level = -1;
    H.LevelImg = "";
    H.Page = 1;
    H.State = 0;
    H.Count = count == null ? this.PageLength: count;
    this.XB.appendChild(H);
    var Bg = H.previousSibling,
    SR = this.SQ(H, true),
    CZ;
    H.Pos = Bg && Bg.Pos ? Bg.Pos + 1 : 0;
    H.Name = name ? name: "Page " + (H.Pos + 1);
    if (this.AllPages) {
        for (var i = 0; i < 3; i++) {
            if (Bg) {
                CZ = Bg["r" + i];
                if (CZ) CZ = CZ.parentNode;
            } else CZ = this.Kt(i);
            if (CZ) {
                var Ck = document.createElement("div");
                CZ.appendChild(Ck);
                if (SR) Ck.style.height = SR + "px";
                Ck.className = this.Img.Style + "Page";
                H["r" + i] = Ck;
            }
        }
    }
    if (this.Pager.Visible) {
        var F = document.createElement("div");
        F.className = this.Img.Style + "PagerItem";
        if (H.Title) F.title = H.Title;
        if (_7U) F.noWrap = true;
        F.innerHTML = H.Name;
        this.l.appendChild(F);
    }
    if (xml) {
        this.ER("<Grid><Body><B Pos='" + H.Pos + "'>" + xml + "</B></Body></Grid>", H);
        if (H.firstChild) {
            this.F5(H);
            H.State = 2;
            if (this.MainCol) {
                var C = this.Cols[this.MainCol];
                for (var J = H.firstChild; J; J = J.nextSibling) this.Sd(J, 0, C);
            }
            var Q5 = this.SQ(H, true);
            if (Q5 && Q5 != SR) for (var i = 0; i < 3; i++) if (H["r" + i]) H["r" + i].style.height = Q5 + "px";
        }
    }
    return H;
};
_7m.prototype.S1 = function (row, OG) {
    this._7d(row);
    var CZ = this.SQ(row, row.Expanded || row.Page) + "px";
    for (var i = 0; i < 3; i++) {
        var J = row["r" + i];
        if (J) {
            if (!row.Page) J = J.nextSibling;
            J.style.overflow = "";
            J.style.height = CZ;
            if (!OG) J.innerHTML = "";
        }
    }
};
_7m.prototype.ReloadPage = function (row, OG) {
    if (row.State < 2 || row.Page && this.Paging < 3 || !row.Page && this.ChildPaging < 3) return;
    row.Count = this.SQ(row, row.Expanded || row.Page) / this.RowHeight;
    if (!row.Count) row.Count = 1;
    if (row.State == 4) this.S1(row, 0);
    if (_7R) row.innerHTML = "";
    else {
        row.firstChild = null;
        row.lastChild = null;
    };
    row.State = 1;
    if (!row.Page) {
        if (!row.Expanded) row.State = 0;
        else this.R0(row);
    } else {
        this.StartPage(row);
    }
};
_7m.prototype.RefreshPage = function (row, OG) {
    if (row.State != 4) return;
    this.S1(row, OG);
    row.State = 3;
    if (!row.Page && !row.Expanded) {
        if (OG || this.ChildPaging < 2) {
            this.FF(row);
            this.Rz(row);
        } else row.State = 2;
    } else if (OG) this.FF(row);
    else this.StartPage(row);
};
_7m.prototype.S2 = function (row, BJ) {
    var BZ = Get(row, BJ + "CopyValue");
    if (BZ == null) BZ = this.FJ(row, BJ).Value;
    if (Grids.OnGetCopyValue) BZ = Grids.OnGetCopyValue(this, row, BJ, BZ);
    BZ += "";
    if (BZ.search(/[\r\n\t]/) < 0) return BZ;
    return '"' + BZ.replace(/\"/g, '""') + '"';
};
_7m.prototype.S3 = function (OV) {
    var S4 = GetWindowScroll();
    var T = this.S5();
    T.value = OV;
    T.focus();
    T.select();
    _7AK(S4[0], S4[1]);
    return true;
};
_7m.prototype.ActionCCopyRow = function () {
    var row = this.FRow;
    if (!row) return false;
    var Cols = this.DT(false, true);
    var OV = "";
    for (var i = 0; i < Cols.length; i++) OV += (i ? "\t": "") + this.S2(row, Cols[i]);
    OV += "\r\n";
    this.S3(OV);
    return true;
};
_7m.prototype.ActionCCopyCell = function () {
    if (!this.FRow || !this.FCol) return false;
    this.S3(this.S2(this.FRow, this.FCol));
    return true;
};
_7m.prototype.ActionCCopyCol = function () {
    if (!this.FRow || !this.FCol) return false;
    var BL = new Array(),
    Bg = 0;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
        BL[Bg++] = this.S2(J, this.FCol) + "\r\n";
    }
    this.S3(BL.join(""));
    return true;
};
_7m.prototype.ActionCCopyColLevel = function () {
    if (!this.FRow || !this.FCol) return false;
    var BL = new Array(),
    Bg = 0;
    var EU = this.FRow.parentNode;
    if (EU.Page) {
        for (var J = this.XB.firstChild; J; J = this.GetNextSibling(J)) if (J.Visible) BL[Bg++] = this.S2(J, this.FCol) + "\r\n";
    } else for (var J = EU.firstChild; J; J = J.nextSibling) if (J.Visible) BL[Bg++] = this.S2(J, this.FCol) + "\r\n";
    this.S3(BL.join(""));
    return true;
};
_7m.prototype.ActionCCopySelected = function () {
    var BL = new Array(),
    Bg = 0,
    Cols = this.DT(false, true);
    if (this.AY && this.Paging != 3) {
        var row = this.XHeader;
        for (var i = 0; i < Cols.length; i++) {
            var BZ = row[Cols[i] + "CopyValue"];
            if (BZ == null) BZ = row[Cols[i]];
            if (Grids.OnGetCopyValue) BZ = Grids.OnGetCopyValue(this, row, Cols[i], BZ);
            BL[Bg++] = (i ? "\t": "") + BZ;
        }
        BL[Bg++] = "\r\n";
    }
    var CE = this.GetSelRows();
    if (!CE.length) return false;
    for (var i = 0; i < CE.length; i++) {
        var row = CE[i],
        OV = "";
        for (var j = 0; j < Cols.length; j++) {
            if (row.Selected == 2 && !row[Cols[j] + "Selected"]) continue;
            OV += (j ? "\t": "") + this.S2(row, Cols[j]);
        }
        BL[Bg++] = OV + "\r\n";
    }
    this.S3(BL.join(""));
    return true;
};
_7m.prototype.ActionExcludeRow = function () {
    if (!this.ActionCCopyRow()) return false;
    var T = this;
    setTimeout(function () {
        T.ActionDeleteRow(1);
    },
    10);
    return true;
};
_7m.prototype.ActionExcludeSelected = function () {
    if (!this.ActionCCopySelected()) return false;
    var T = this;
    setTimeout(function () {
        T.ActionDeleteSelected();
    },
    10);
    return true;
};
_7m.prototype.S6 = function (OV, Bg) {
    if (!OV) return;
    OV = OV + "";
    if (this.Pasting != 1) Bg = this.Pasting;
    if (Bg == null) Bg = 3;
    var BL = OV.split(/\r\n|\n|\r/);
    if (OV.search(/(^|[\t\r\n])\"/) >= 0) {
        OV = OV.replace(/\\/g, "\\x");
        while (1) {
            var M = OV.match(/(^|[\t\r\n])\"(\"\"|[^\"\r\n\t])*[\r\n\t](\"\"|[^\"])*\"([\t\r\n]|$)/);
            if (!M) break;
            var C0 = M[0].split('"');
            var BG = C0[0];
            C0[0] = "";
            var B = C0[C0.length - 1];
            C0.length--;
            C0 = BG + C0.join('"').slice(1).replace(/\n/g, "\\n").replace(/\r/g, "\\r").replace(/\t/g, " ").replace(/\"\"/g, '"') + B;
            OV = OV.replace(M[0], C0);
        }
        BL = OV.split(/\r\n|\n|\r/);
        for (var i = 0; i < BL.length; i++) {
            if (BL[i].indexOf("\\") >= 0) BL[i] = BL[i].replace(/\\n/g, "\n").replace(/\\r/g, "\r").replace(/\\x/g, "\\");
        }
    }
    while (BL.length && !BL[BL.length - 1]) BL.length--;
    if (!BL.length) return;
    if (Bg & 64) {
        if (!this.FRow) return;
        CE = new Array();
        for (var J = Bg & 32 ? this.GetFirstVisible() : this.FRow; J; J = this.GetNextVisible(J)) CE[CE.length] = J;
    } else if (Bg & 128) {
        if (!this.FRow) return;
        CE = new Array();
        var EU = this.FRow.parentNode;
        if (EU.Page) {
            for (var J = Bg & 32 ? this.GetFirstVisible() : this.FRow; J; J = this.GetNextSibling(J)) if (J.Visible) CE[CE.length] = J;
        } else for (var J = Bg & 32 ? EU.firstChild: this.FRow; J; J = J.nextSibling) if (J.Visible) CE[CE.length] = J;
    } else CE = Bg & 1 ? this.GetSelRows() : new Array();
    var P = BL[0].split("\t"),
    Cols,
    CE,
    Df = false;
    if (Bg & 192) {
        if (!this.FCol) return;
        if ((Bg & 12) == 4) {
            Cols = new Array();
            for (var BY = this.FCol, i = 0; BY && i < P.length; BY = this.GetNextCol(BY), i++) Cols[Cols.length] = BY;
        } else Cols = [this.FCol];
    } else Cols = this.DT(false, true);
    if (this.AY && this.Paging != 3) {
        for (var i = 0; i < Cols.length; i++) if (P[i] != this.S2(this.XHeader, Cols[i])) break;
        if (i == Cols.length) {
            BL.shift();
            if (!BL.length) return;
            P = BL[0].split("\t");
        }
    }
    var start = 0;
    if (Bg & 16 || P.length < Cols.length && (!CE.length || CE[0].Selected != 2) && !(Bg & 32)) {
        for (start = 0; start < Cols.length && Cols[start] != this.FCol; start++);
        if (start == Cols.length) start = 0;
    }
    var S7 = 0;
    if (!CE.length && this.FRow && Bg & 2) {
        S7 = 1;
        if ((Bg & 12) != 12) CE[0] = this.FRow;
        if ((Bg & 12) == 4) for (var i = 1, BG = this.FRow.nextSibling; i < P.length && BG; i++, BG = BG.nextSibling) CE[i] = BG;
    }
    if (Grids.OnPaste && Grids.OnPaste(this, CE, Cols, start, OV)) return;
    var BN = Cols.length - start;
    if (CE.length && CE[0].Selected == 2) {
        BN = 0;
        for (var i in Cols) if (CE[0][Cols[i] + "Selected"]) BN++;
    }
    if (P.length > BN || !S7 && CE.length != BL.length && !(Bg & 192) || S7 && BL.length > 1 && (Bg & 12) == 0) {
        if (!confirm(this.GetAlert("ErrPasteRange").replace(/\%d/, BL.length).replace(/\%d/, P.length).replace(/\%d/, CE.length ? CE.length: 1).replace(/\%d/, BN))) return;
        if (start && P.length > Cols.length - start && !(Bg & 16)) start = 0;
    }
    this.Gm();
    if (S7 && (Bg & 12) >= 8) {
        if ((Bg & 12) == 12) {
            CE[0] = this.S8(null, this.FRow, 1);
            if (!CE[0]) {
                this.Gp();
                return;
            }
            this.Focus(CE[0], this.FCol);
        }
        var EU = this.FRow.parentNode;
        if (EU.Page) EU = null;
        for (var i = 1; i < BL.length; i++) {
            CE[i] = this.S8(EU, this.FRow.nextSibling, 1);
        }
        if (BL.length > 1 && CE[1] == null) {
            this.Gp();
            return;
        }
    }
    var Dj = CE.length >= 50 && BL.length >= 50;
    for (var i = 0; i < CE.length && i < BL.length; i++) this.S9(CE[i], Cols, BL[i], start, Dj);
    this.Gp();
    this.SetScrollBars();
    this.UploadChanges();
    if (Dj) {
        var T = this;
        this.ShowMessage(this.GetText("UpdateGrid"));
        setTimeout(function () {
            T.Calculate();
            T.UpdateBody();
            var CE = T.GetFixedRows();
            for (var i = 0; i < CE.length; i++) T.RefreshRow(CE[i]);
            T.HideMessage();
            if (Grids.OnPasteFinish) Grids.OnPasteFinish(T, CE.length ? CE: [T.FRow], Cols, start ? start: 0);
            if (T.Paging) T.ShowPages();
        },
        10);
    } else if (Grids.OnPasteFinish) Grids.OnPasteFinish(this, CE.length ? CE: [this.FRow], Cols, start ? start: 0);
};
_7m.prototype.S9 = function (row, Cols, OV, start, Dj) {
    var BL = OV.split("\t"),
    Df = false;
    if (!start) start = 0;
    for (var i = 0; i < BL.length; i++) {
        if (row.Selected == 2) {
            while (Cols[i + start] && !row[Cols[i + start] + "Selected"]) start++;
        }
        var BJ = Cols[i + start];
        if (!BJ || !this.CanEdit(row, BJ)) continue;
        var BR = BL[i] + "";
        var size = Get(row, BJ + "Size");
        if (size == null) size = this.Cols[BJ].Size;
        if (BR.length > size) BR = BR.slice(0, size);
        var FH = this.FI(row, BJ, 1);
        if (FH && BR.search(new RegExp(FH, "")) == -1) continue;
        var FH = this.FI(row, BJ, 0);
        try {
            if (FH && BR.search(new RegExp(FH, "")) == -1) {
                if (!Grids.OnResultMask || Grids.OnResultMask(this, row, BJ, BR) != 3) continue;
            }
        } catch(B) {};
        BR = this.Iq(row, BJ, BR);
        if (!this.SetValue(row, BJ, BR, 0, 0, 1)) continue;
        Df = true;
        this.RefreshCell(row, BJ);
    }
    if (Df && row.Kind != "Filter" && !Dj) {
        this.Recalculate(row, null, true);
        this.ColorRow(row);
    }
    if (this.VarHeight) this.UpdateRowHeight(row);
    return Df;
};
_7m.prototype.S5 = function () {
    var T = Grids.A7;
    if (T) return T;
    T = document.createElement("textarea");
    Grids.A7 = T;
    T.style.position = "absolute";
    T.style.top = "-2500px";
    T.rows = 100;
    T.cols = 150;
    if (!this.HV) T.style.left = "-2500px";
    else {};
    if (_7R && !_7U) document.body.insertBefore(T, document.body.firstChild);
    else document.body.appendChild(T);
    return Grids.A7;
};
_7m.prototype.TA = function (type) {
    if (!this.Pasting) return false;
    if ((type & 3) == 2 && !this.FRow) return false;
    if ((type & 3) == 1) {
        var CE = this.GetSelRows();
        if (!CE.length) return false;
    }
    var S4 = GetWindowScroll();
    var T = this.S5(),
    G = this;
    T.onkeyup = function () {
        this.blur();
        this.onkeyup = null;
        G.S6(this.value, type);
    };
    T.focus();
    T.select();
    _7AK(S4[0], S4[1]);
    setTimeout(function () {
        _7AK(S4[0], S4[1]);
    },
    10);
    return true;
};
_7m.prototype.ActionPasteToRow = function () {
    return this.TA(2);
};
_7m.prototype.ActionPasteToRowFill = function () {
    return this.TA(6);
};
_7m.prototype.ActionPasteToRowAdd = function () {
    return this.TA(10);
};
_7m.prototype.ActionPasteToNewRow = function () {
    return this.TA(14);
};
_7m.prototype.ActionPasteToSelected = function () {
    return this.TA(1);
};
_7m.prototype.ActionPasteToRowFirst = function () {
    return this.TA(2 + 32);
};
_7m.prototype.ActionPasteToRowFillFirst = function () {
    return this.TA(6 + 32);
};
_7m.prototype.ActionPasteToRowAddFirst = function () {
    return this.TA(10 + 32);
};
_7m.prototype.ActionPasteToNewRowFirst = function () {
    return this.TA(14 + 32);
};
_7m.prototype.ActionPasteToSelectedFirst = function () {
    return this.TA(1 + 32);
};
_7m.prototype.ActionPasteToCol = function () {
    return this.TA(2 + 64);
};
_7m.prototype.ActionPasteToColLevel = function () {
    return this.TA(2 + 128);
};
_7m.prototype.ActionPasteToColFill = function () {
    return this.TA(6 + 64);
};
_7m.prototype.ActionPasteToColFillLevel = function () {
    return this.TA(6 + 128);
};
_7m.prototype.ActionPasteToColFirst = function () {
    return this.TA(2 + 32 + 64);
};
_7m.prototype.ActionPasteToColLevelFirst = function () {
    return this.TA(2 + 32 + 128);
};
_7m.prototype.ActionPasteToColFillFirst = function () {
    return this.TA(6 + 32 + 64);
};
_7m.prototype.ActionPasteToColFillLevelFirst = function () {
    return this.TA(6 + 32 + 128);
};
_7m.prototype.GetPrintable = function (Bx, type, KK, BV, BL, Bg, Bc, I, BQ, TB, I7) {
    if (Bx == null) Bx = type >> 4;
    if (!Bx) Bx = 31;
    if (this.CancelProgress) {
        this.HideMessage();
        this.CancelProgress = 0;
        return;
    }
    var D3 = this.GetSections(),
    T = this,
    Ca = type & 2 ? 0 : 1,
    J0 = this.HV ? " dir='rtl'": "";
    function GetRow(J) {
        BL[Bg++] = _7A + (J.Class ? " class='" + T.Img.Ib + J.Class + "'": "") + J0 + _7B + "<tr>";
        for (var BZ = D3[0]; BZ < D3[1]; BZ++) BL[Bg++] = T.IK(J, BZ, 1);
        BL[Bg++] = "</tr>" + _7C;
    };
    if (!Bc) {
        this.TC = 1;
        TB = this.Cols.Panel.Visible;
        this.Cols.Panel.Visible = 0;
        I7 = this.FRow;
        this.FRow = null;
        BL = new Array(),
        Bg = 0;
        BL[Bg++] = _7A + " id='GridPrintID' class='" + this.Img.Style + "Table'" + _7B;
        if (Bx & 1 && Bx & 16) BL[Bg++] = this.TD(0, D3);
    }
    if (!Bc || type & 512) {
        var R = this.GetRows(this.XH);
        if (R.length > 0 && Bx & 2 || Bx & 1) {
            if (Bc) {
                BL[Bg++] = _7C + _7A + "style='page-break-before:always;' class='" + this.Img.Style + "Table'" + _7B;
            }
            BL[Bg++] = "<tr><td><div class='" + this.Img.Style + "HeadMid'>";
            if (Bx & 1) {
                if (this.XHeader.Rows > 1) {
                    BL[Bg++] = _7A + _7B + "<tr>";
                    for (var BZ = D3[0]; BZ < D3[1]; BZ++) BL[Bg++] = "<td>" + this.IK(this.XHeader, BZ) + "</td>";
                    BL[Bg++] = "</tr>" + _7C;
                } else GetRow(this.XHeader);
            }
            if (Bx & 2 && !Bc) for (var i = 0; i < R.length; i++) if (R[i].Visible) GetRow(R[i]);
            BL[Bg++] = "</div></td></tr>";
        }
    }
    if (!Bc) {
        if (Bx & 2 && Bx & 16) BL[Bg++] = this.TD(1, D3);
        if (Bx & 8) {
            BL[Bg++] = "<tr><td><div class='" + this.Img.Style + "BodyMid'>";
            for (BQ = 0, Bc = this.GetFirstVisible(); Bc; Bc = this.GetNextVisible(Bc, Ca)) BQ++;
            Bc = this.GetFirstVisible();
            I = 0;
        }
    }
    if (Bx & 8) {
        while (Bc) {
            var Dh = Bc.Expanded;
            if (Ca == 0) Bc.Expanded = 1;
            GetRow(Bc);
            Bc.Expanded = Dh;
            Bc = this.GetNextVisible(Bc, Ca);
            if (this.PrintRows && Bc && KK) {
                I++;
                if (! (I % this.PrintRows)) {
                    this.ShowMessage(this.GetText("ExportProgress").replace('%d', I).replace('%d', BQ).replace('%d', Math.floor(I / BQ * 100)).replace('%d', this.Z + ".CancelProgress=1;"));
                    setTimeout(function () {
                        T.GetPrintable(Bx, type, KK, BV, BL, Bg, Bc, I, BQ, TB, I7);
                    },
                    10);
                    return;
                }
            }
        }
        BL[Bg++] = "</div></td></tr>";
    }
    if (Bx & 4 && Bx & 16) BL[Bg++] = this.TD(2, D3);
    var R = this.GetRows(this.XF);
    if (Bx & 4 && R.length > 0) {
        BL[Bg++] = "<tr><td><div class='" + this.Img.Style + "FootMid'>";
        for (var i = 0; i < R.length; i++) if (R[i].Visible) GetRow(R[i]);
        BL[Bg++] = "</div></td></tr>";
    }
    if (Bx & 4 && Bx & 16) BL[Bg++] = this.TD(3, D3);
    BL[Bg++] = _7C;
    this.Cols.Panel.Visible = TB;
    this.FRow = I7;
    this.TC = 0;
    if (KK && BV) {
        this.HideMessage();
        BV.apply(KK, [BL.join("")]);
    } else return BL.join("");
};
_7m.prototype.UpdatePrintable = function (Jf, type) {
    if (!this.MainCol || !this.VarHeight || this.NoTreeLines) return;
    var H = Jf.getElementsByTagName("bdo"),
    type = type & 2 ? 0 : 1;
    var KL = this.Img.Height + this.AM;
    if (!KL) KL = 0;
    for (var J = this.GetFirstVisible(), Bc = 0; J && Bc < H.length; J = this.GetNextVisible(J, type)) {
        while (H[Bc].className != "_Tree") Bc++;
        Dh = J.Expanded;
        if (type == 0) J.Expanded = 1;
        var CZ = H[Bc].parentNode.parentNode.clientHeight;
        if (CZ > 128) H[Bc].innerHTML = _7A + _7B + "<tr>" + this.TE(J, null, CZ) + "<td valign='top'>" + "</td></tr>" + _7C;
        else if (CZ > KL) {
            BY = _7s(H[Bc], _7V ? "u": "center");
            for (var i = 0; i < BY.length; i++) BY[i].style.height = CZ + "px";
        }
        J.Expanded = Dh;
        Bc++;
    }
};
_7m.prototype.ActionPrint = function () {
    this.GetPrintable(null, this.PrintType, this, this.TF);
    return true;
};
_7m.prototype.TF = function (BR) {
    var CY = window.open("", "TreeGridPrint", this["PrintWindowProp"], false);
    if (!CY) {
        alert(this.GetAlert("ErrPrintOpen"));
        return;
    }
    try {
        var BL, TG = "";
        if (this.PrintCSS) TG = '<link href=' + this.PrintCSS + ' type="text/css" rel="stylesheet"/>';
        else {
            var S = document.styleSheets;
            if (!S) S = document.getElementsByTagName("link");
            if (S) {
                for (var i = 0; i < S.length; i++) TG += '<link href=' + S[i].href + ' type="text/css" rel="stylesheet"/>';
            }
        };
        var TH = this.PrintPrefix ? this.PrintPrefix: "";
        var Ly = (this.PrintPostfix ? this.PrintPostfix: "");
        if (Grids.OnPrint) {
            BL = Grids.OnPrint(this, CY);
            if (BL == true) return;
            if (BL && BL.length) {
                TH = BL[0];
                Ly = BL[1];
            }
        }
        if (!_7H || _7O) CY.document.write(this.GetText("Print"));
        CY.document.close();
        this.ShowMessage(this.GetText("Printed"));
        var T = this;
        setTimeout(function () {
            try {
                if (_7I) CY.document.write('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN">');
                CY.document.write('<html><head>' + TG + '</head><body' + (this.HV ? " dir='rtl'": "") + '>' + TH + BR + Ly + '</body></html>');
                BR = null;
                CY.document.close();
                CY.focus();
                if (!T.VarHeight) {
                    if (Grids.OnPrintFinish && Grids.OnPrintFinish(T, CY)) return;
                    if (!CY.closed) {
                        CY.print();
                        CY.close();
                    }
                    T.HideMessage();
                } else setTimeout(function () {
                    try {
                        T.UpdatePrintable(CY.document, T.PrintType);
                        if (Grids.OnPrintFinish && Grids.OnPrintFinish(T, CY)) return;
                        if (!CY.closed) {
                            CY.print();
                            CY.close();
                        }
                        T.HideMessage();
                    } catch(B) {
                        T.HideMessage();
                    }
                },
                10);
            } catch(B) {
                T.HideMessage();
            }
        },
        10);
    } catch(B) {
        alert(this.GetAlert("ErrPrint"));
        this.HideMessage();
    }
};
_7m.prototype.HW = function (row, x, width, Gd, style, height) {
    if (height > 128) {
        var Bh = this.HW(row, x, width, Gd, style, 128);
        height -= 128;
        while (1) {
            if (height < 96) {
                Bh += this.HW(row + 1, x, width, Gd, style, height);
                break;
            }
            Bh += this.HW(row + 1, x, width, Gd, style, 96);
            height -= 96;
        }
        return Bh;
    }
    return "<center" + (_7V ? "><u style='display:inline-block;": " style='") + "width:" + width + "px;height:" + (height ? height: this.Img.Height) + "px;overflow:hidden;background:url(" + this.Img.Grid + ") -" + x + "px -" + row * 32 + "px;" + (style ? style: "") + "'" + (Gd ? " " + Gd: "") + ">" + (_7X ? _7D: "") + "</center>" + (_7V ? "</u>": "");
};
_7m.prototype.KP = function (Dp, TI, Gd) {
    return " style='" + (Dp >= 0 ? "width:" + Dp + "px;overflow:hidden;": "") + (TI ? "white-space:nowrap;": "") + (Gd ? Gd: "") + "'";
};
_7m.prototype.In = function (row, BJ, Dp, TJ, Gd) {
    var C = this.Cols[BJ];
    var BR = this.GetValue(row, BJ);
    var Hb = Get(row, BJ + "ClassOuter");
    if (!Hb) Hb = this.Img.Style + "Select";
    else Hb = this.Img.Ib + Hb;
    if (Grids.OnGetClass) Hb = Grids.OnGetClass(this, row, BJ, Hb);
    var data = "<select size='1' style='width:" + Dp + "px;' class='" + Hb + "'" + (Gd ? Gd: "");
    if (_7M) data += " onclick='" + this.Z + ".NoClickTo = (new Date).getTime()+100;'";
    if (_7P || _7M) data += " onchange='" + this.Z + ".GridClick(event)'";
    data += ">";
    if (TJ) data += "<option " + (BR == null || BR < 0 ? "selected" + _7E: "") + ">" + _7D + "</option>";
    var Enum = this.GetEnum(row, BJ, 1);
    if (Enum) for (var i = 0; i < Enum.length; i++) data += "<option " + (BR == i ? "selected" + _7E: "") + ">" + this.Escape(Enum[i]) + "</option>";
    data += "</select>";
    return data;
};
_7m.prototype.Escape = function (BR) {
    if (BR == null || typeof(BR) != "string" || BR.search(/[\&\<\>\'\"]/) < 0) return BR + "";
    var BL = new Array(BR.length);
    for (var i = 0; i < BR.length; i++) {
        var BY = BR.charCodeAt(i);
        if (BY == 38 || BY == 60 || BY == 62 || BY == 39 || BY == 34) {
            BL[i] = "&#" + BY + ";";
        } else BL[i] = BR.charAt(i);
    }
    return BL.join("");
};
_7m.prototype.TE = function (row, TK, height) {
    if (!this.MainCol) return "";
    if (!height) height = this.Img.Height;
    else row.TL = height;
    if (row.parentNode == this.XH || row.parentNode == this.XF) return "";
    var C = this.Cols[this.MainCol],
    Ok = "",
    TM = row.Level,
    TN = row.LevelImg,
    TO = 0,
    E4 = this.Img,
    Hm = "<td class='" + this.Img.Style + "TreeIcon'>";
    if (!TN) TN = "";
    if (this.HideRootTree) {
        TM--;
        TN = TN.slice(1);
        if (TM < 0) return "";
    }
    Ok += Hm;
    if (this.HasChildren(row) && Is(row, "CanExpand")) TO = row.Expanded ? 2 : 4;
    if (this.NoTreeLines) {
        Ok += "<center style='width:" + (TM * E4.Line + (!TO && TK == null ? E4.Tree: 0)) + "px;height:" + height + "px;overflow:hidden;'>" + _7D + "</center>";
        if (TK != null) Ok += "</td>" + Hm + this.HW(15, 128 + (2 + TK) * E4.Tree, E4.Tree, null, null, height);
        else if (TO) {
            Ok += "</td>" + Hm;
            var Oz = Get(row, "TreeIcon" + TO);
            if (Oz) {
                var left = Get(row, "TreeIconLeft" + TO),
                top = Get(row, "TreeIconTop" + TO);
                Ok += "<center" + (_7V ? "><u style='display:inline-block;": " style='") + "width:" + E4.Tree + "px;height:" + (height ? height: this.Img.Height) + "px;overflow:hidden;background:url(" + Oz + ")" + " -" + (left ? left: 0) + "px -" + (top ? top: 0) + "px no-repeat;" + (TO > 1 ? _7a: "") + "'></center>" + (_7V ? "</u>": "");
            } else Ok += this.HW(15, 128 + (row.Expanded ? 0 : E4.Tree), E4.Tree, null, _7a, height);
        }
    } else {
        if (TN.charAt(TN.length - 1) == "0") TO++;
        TN = TN.slice(0, TM);
        var Bb = TM == 0 ? 0 : Math.floor((TM - 1) / 3),
        TP = E4.Line * 3 + E4.Tree,
        J,
        BY;
        for (var i = 0; i < Bb; i++) {
            var name = TN.substr(i * 3, 3);
            J = name.charAt(0) * 4;
            BY = (name.charAt(1) - 0) + (name.charAt(2) - 0) * 2;
            Ok += this.HW(J, BY * TP, TP - E4.Tree, null, null, height);
            Ok += "</td>" + Hm;
        }
        var name = TN.substr(Bb * 3, 3);
        if (!name) {
            J = 0;
            BY = 0;
        } else if (name.length == 1) {
            J = 0;
            BY = (name.charAt(0) - 0) * 2;
        } else if (name.length == 2) {
            J = 0;
            BY = (name.charAt(0) - 0) + (name.charAt(1) - 0) * 2;
        } else {
            J = name.charAt(0) * 4;
            BY = (name.charAt(1) - 0) + (name.charAt(2) - 0) * 2;
        };
        var KW = name.length * E4.Line;
        if (!TO && TK == null) Ok += this.HW(J, BY * TP + TP - KW - E4.Tree, KW + E4.Tree, null, null, height);
        else {
            if (name != "") Ok += this.HW(J, BY * TP + TP - KW - E4.Tree, KW, null, null, height) + "</td>" + Hm;
            if (TK != null) Ok += this.HW(8, (6 + TK) * E4.Tree, E4.Tree, null, null, height);
            else {
                var Oz = Get(row, "TreeIcon" + TO);
                if (Oz) {
                    var left = Get(row, "TreeIconLeft" + TO),
                    top = Get(row, "TreeIconTop" + TO);
                    Ok += "<center" + (_7V ? "><u style='display:inline-block;": " style='") + "width:" + E4.Tree + "px;height:" + (height ? height: this.Img.Height) + "px;overflow:hidden;background:url(" + Oz + ")" + " -" + (left ? left: 0) + "px -" + (top ? top: 0) + "px no-repeat;" + (TO > 1 ? _7a: "") + "'></center>" + (_7V ? "</u>": "");
                } else Ok += this.HW(8, TO * E4.Tree, E4.Tree, null, TO > 1 ? _7a: "", height);
            }
        }
    };
    Ok += "</td>";
    return Ok;
};
_7m.prototype.TQ = function (BJ) {
    var C = this.Cols,
    TR = this["ReversedSortIcons"],
    Iv = this.AX;
    if (C[BJ].CanSort && this.Sorting) {
        for (var i = 0, EI = 0; i < Iv; i++) {
            if (this.SortCols[i] == BJ) return this.SortTypes[i] & 1 ? (TR ? 1 : 4) + EI: (TR ? 4 : 1) + EI;
            var D3 = C[this.SortCols[i]];
            if (EI < 2 && D3 && (D3.Visible || D3.CanHide)) EI++;
        }
    }
    return 0;
};
_7m.prototype.Il = function (row, BJ, TS, TK, height) {
    var C = this.Cols[BJ];
    if (BJ == "Panel") {
        if (row.Space != null && (row.Kind == "Group" || row.Kind == "Search") || row.Kind == "Filter") return this.TT(row);
        var J = 12,
        a = 0,
        BY = 0,
        CY = 4,
        TU = "",
        TV = this.Img.Panel,
        TW = 2;
        var O6 = !this.Dragging || !C.Move ? -1 : (Is(row, "CanDrag") ? 1 : 0);
        var BO = !this.Selecting || !C.Select ? -1 : (Is(row, "CanSelect") ? (row.Selected ? 2 : 1) : 0);
        var TX = !this.Deleting || !C.Delete ? -1 : (Is(row, "CanDelete") ? 1 : 0);
        var BX = !this.Copying || !C.Copy ? -1 : (Is(row, "CanCopy") ? 1 : 0);
        if (TX < 0) {
            if (BO < 0) {
                BY = 30;
                CY = 2;
            } else {
                J = 13;
                a = 300;
                CY = 3;
                TW = 1;
            }
        } else if (BO < 0) {
            CY = 3;
            BY = 24;
        } else {
            CY = 4;
        };
        if (!TX) BY += CY;
        if (!BO) BY += CY * TW * 2;
        else if (BO == 2) BY += CY * TW;
        if (O6 < 0) {
            BY++;
            CY--;
        } else if (!O6) {
            BY++;
            CY--;
            TU += "margin-left:" + TV + "px;";
        }
        if (BX < 0) {
            CY--;
        } else if (!BX) {
            CY--;
            TU += "margin-right:" + TV + "px;";
        }
        if (!CY && !TU) return "<div>" + _7D + "</div>";
        if (row["PanelVisible"] == 0 || O6 <= 0 && BO <= 0 && TX <= 0 && BX <= 0) return "<center style='overflow:hidden;width:" + (CY * TV) + "px;" + TU + "height:1px'>" + _7D + "</center>";
        return this.HW(J, BY * TV + a, CY * TV, null, TU);
    }
    var Dp = this.DS(row, BJ) - this.AH;
    var TY = row.Space != null;
    if (row == this.XHeader) {
        var Hb = this.Img.Style + "HeadText";
        if (Grids.OnGetClass) Hb = Grids.OnGetClass(this, row, BJ, Hb);
        var TZ = this.XHeader[BJ + "Align"];
        if (TZ != null) TZ = " align='" + ["left", "center", "right"][TZ] + "'";
        else TZ = "";
        var sort = "";
        if (C.CanSort & 1 && this.Sorting && C.Type != "Pass" && (this.SortIcons != 0)) sort = "</td><td>" + this.HW(13, this.TQ(BJ) * this.Img.Sort, this.Img.Sort, this.SortIcons == 2 ? " class=" + this.Img.Style + "HeadSort ": "");
        var BR = Get(row, BJ);
        if (!row.NoEscape) BR = this.Escape(BR);
        if (BR == "") BR = "&nbsp;";
        return "<div " + this.KP(Dp) + ">" + _7A + TZ + _7B + "<tr><td" + (_7U ? " nowrap" + _7E: "") + ">" + "<div class='" + Hb + "'" + (this.XHeader.Wrap ? " style='white-space:normal;'": "") + ">" + BR + "</div>" + sort + "</td></tr>" + _7C + "</div>";
    }
    var data;
    if (TY) C = this.Cols.Panel;
    if (!TS && row.Spanned && Get(row, BJ + "Merge") && this.Ta) {
        var BY = Get(row, BJ + "Merge") - 0,
        Bh = Get(row, BJ + "MergeStart"),
        S = this.ColNames[C.K],
        Bg = C.Pos,
        IZ = Get(row, BJ + "MergeType") - 0;
        if (Bh) Bg += Bh;
        data = "";
        if (IZ & 1) {
            for (var i = 0; i < BY; i++) {
                if (this.Cols[S[Bg + i]].Visible || IZ & 2) data += "<tr><td>" + this.Il(row, S[Bg + i], 1, TK, height) + "</td></tr>";
            }
            if (data) data = _7A + _7B + data + _7C;
            else data = _7D;
        } else {
            for (var i = 0; i < BY; i++) {
                if (this.Cols[S[Bg + i]].Visible || IZ & 2) data += "<td>" + this.Il(row, S[Bg + i], 1, TK, height) + "</td>";
            }
            if (data) data = _7A + _7B + "<tr>" + data + "</tr>" + _7C;
            else data = _7D;
        };
    } else {
        var Tb = this.CanEdit(row, BJ),
        type = this.GetType(row, BJ),
        BR = this.O2(row, BJ, type, Tb);
        if (BR == "" || BR == _7D) {
            var BZ = Get(row, BJ + "EmptyValue");
            if (BZ != null) BR = BZ;
        }
        if (type == "Text") {
            var I1 = Get(row, BJ + "Rows");
            if (I1 == null) I1 = this.Cols[BJ].Rows;
            if (I1 > 1) {
                row[BJ + "Type"] = "Lines";
                this.VarHeight = 1;
            }
        }
        var title = Get(row, BJ + "Error");
        if (title == null) {
            if (TY || C.ToolTip) {
                title = Get(row, BJ + "ToolTip");
                if (!title) title = TY ? "": this.Escape(this.GetString(row, BJ));
                else if (TY && title == 1) title = this.Escape(this.GetString(row, BJ));
                else {
                    title += "";
                    if (title.indexOf("&") < 0 || title.search(/(\&\#\x)|(\&amp\;)|(\&lt\;)|(\&quot\;)|(\&apos\;)/) < 0) title = this.Escape(title);
                }
            } else title = "";
        } else title = this.Escape(title);
        if (title) title = " title = '" + title + "'";
        var Hb = Get(row, BJ + "ClassInner");
        if (!Hb || type == "Button") Hb = this.Img.Style + _7g[type];
        else Hb = this.Img.Ib + Hb;
        if (Grids.OnGetClass) Hb = Grids.OnGetClass(this, row, BJ, Hb);
        var TH = Get(row, BJ + "HtmlPrefix"),
        Ly = Get(row, BJ + "HtmlPostfix");
        if (TH || Ly) BR = (TH ? TH: "") + BR + (Ly ? Ly: "");
        data = "<div class='" + Hb + "'" + (Tb && type == "Enum" ? " style='" + _7a + "'": "") + title + (_7U && type != "Lines" ? " nowrap" + _7E: "") + ">" + BR + "</div>";
        if (type == "Enum" && Tb) {
            var Tc = Get(row, BJ + "EnumType");
            if (Tc == null && this.Cols[BJ]) Tc = this.Cols[BJ]["EnumType"];
            if (Tc & 3 > 1) {
                var TO = this.HW(15, this.Img.Row * 6, this.Img.Enum);
                var Hb = Get(row, BJ + "ClassEnum");
                if (type == null && this.Cols[BJ]) type = this.Cols[BJ]["ClassEnum"];
                if (!Hb) Hb = "Enum";
                data = "<div class='" + this.Img.Style + Hb + "Cell'>" + _7A + _7B + "<tr><td>" + TO + "</td><td style='white-space:nowrap'>" + data + "</td></tr>" + _7C + "</div>";
            }
        }
        if (TS) return data;
    };
    var Td = "",
    button = "",
    JJ = Get(row, BJ + "Button");
    if (JJ == null) JJ = C.Button;
    if (!JJ && Tb && type == "Date" && !this.AutoCalendar) JJ = "Date";
    if (JJ) {
        var SI = this.Ia(row, BJ);
        if (!SI && JJ == "Date") {
            SI = this.Img.Row;
            row[BJ + "WidthPad"] = SI;
        }
        if (!SI && JJ == "Defaults") {
            SI = row.Space == null ? this.Img.Row: this.Img.SpaceRow;
            row[BJ + "WidthPad"] = SI;
        }
        if (SI) {
            var Te = Get(row, BJ + "ButtonText");
            if (Te == null) Te = C.ButtonText;
            if (!Te) Te = "";
            if (JJ == "Button") {
                button = "<button" + this.KP(SI) + " class='" + this.Img.Style + "Button'>" + Te + "</button>";
            } else if (JJ == "Img") {
                button = "<img class='" + this.Img.Style + "ButtonImg' align='right' src = '" + Te + "'/>";
            } else if (JJ == "Date") {
                if (Te) {
                    button = "<img class='" + this.Img.Style + "ButtonImg' align='right' width='" + this.Img.Row + "' height='" + this.Img.Height + "' src='" + Te + "'/>";
                } else {
                    button = this.HW(15, this.Img.Row * 2, this.Img.Row, " class='" + this.Img.Style + "ButtonImg'");
                }
            } else if (JJ == "Html") {
                button = Te;
            } else if (JJ == "Defaults") {
                if (Te) {
                    button = "<img class='" + this.Img.Style + "ButtonImg' align='right' width='" + this.Img.Row + "' height='" + this.Img.Height + "' src='" + Te + "'/>";
                } else {
                    if (row.Space != null) {
                        button = this.HW(14, 300, this.Img.SpaceRow, " class='" + this.Img.Style + "ButtonImg'", null, this.Img.SpaceHeight);
                        Td = " class='" + this.Img.Style + "SpaceSelect'";
                    } else button = this.HW(15, this.Img.Row * 5, this.Img.Row, " class='" + this.Img.Style + "ButtonImg'");
                }
            } else button = "";
            Dp -= SI;
        }
    }
    var Tf;
    if (Dp >= 0) {
        Tf = "<div style='width:" + Dp + "px;overflow:hidden;";
        var Tg = Get(row, BJ + "OverflowEllipsis");
        if (Tg == null) Tg = C.OverflowEllipsis;
        if (Tg) Tf += "text-overflow:ellipsis;";
        Tf += "'>";
    } else Tf = "<div>";
    if ((row.Kind == "Filter" && type != "Pass" && C.CanFilter && Get(row, BJ + "ShowMenu") != 0 || Get(row, BJ + "ShowMenu") == 1) && Tb && !this.TC) {
        var filter = Get(row, BJ + "Filter");
        if (!filter) filter = 0;
        var Th = this.HW(14, this.Img.Filter * filter, this.Img.Filter, " class='" + this.Img.Style + "ButtonImg'" + " onclick='" + this.Z + ".ShowFilter(" + this.Z + ".ARow,\"" + BJ + "\");CancelEvent(event);'");
        data = _7A + " width='100%'" + _7B + "<tr><td width='" + this.Img.Height + "' class='" + this.Img.Style + "FilterButton'>" + Th + "</td><td>" + data + "</td></tr>" + _7C;
    } else;
    if (BJ == this.MainCol && !row.Fixed && C.Visible) {
        data = _7A + _7B + "<tr>" + this.TE(row, TK, height) + "<td valign='top'>" + data + "</td></tr>" + _7C;
        if (this.TC && this.VarHeight) data = "<bdo class='_Tree'>" + data + "</bdo>";
    }
    if (SI) {
        return _7A + _7B + "<tr><td valign='top' " + Td + ">" + Tf + data + "</div></td><td class='" + this.Img.Style + "RightButton'><div style='width:" + SI + "px;height:" + (row.Space != null ? this.Img.SpaceHeight: this.Img.Height) + "px;overflow:hidden;'>" + button + "</div></td></tr>" + _7C;
    }
    return Tf + data + "</div>";
};
_7m.prototype.TT = function (row) {
    var C = this.Cols["Panel"],
    Ti,
    CY = 0,
    BL = new Array(),
    Bg = 0;
    if (this.Dragging && C.Move) CY++;
    if (this.Selecting && C.Select) CY++;
    if (this.Deleting && C.Delete) CY++;
    if (this.Copying && C.Copy) CY++;
    if (row.Kind == "Filter") {
        if (!CY) return "<div></div>";
        if (_7R) Ti = "<div style='width:" + CY * this.Img.Panel + "px'>";
        else Ti = "<div style='width:" + (CY + 1) / 2 * this.Img.Panel + "px;padding-left:" + (CY - 1) / 2 * this.Img.Panel + "px;'>";
        if (!this.Filtering || row["PanelVisible"] == 0) return Ti + "</div>";
        var BY = this.Filtered ? 9 : 10;
        return Ti + this.HW(13, 300 + BY * this.Img.Panel, this.Img.Panel) + "</div>";
    }
    if (!CY && row.Panel != 2) return "<div></div>";
    if (_7R) Ti = "<div style='width:" + CY * this.Img.Panel + "px'>";
    else Ti = "<div style='width:" + (CY * this.Img.Panel + this.Img.SpacePanel) / 2 + "px;padding-left:" + (CY * this.Img.Panel - this.Img.SpacePanel) / 2 + "px;'>";
    if (row.Kind == "Group") {
        var BY = this.Grouping ? (this.Grouped ? 0 : 1) : 2;
        return Ti + this.HW(13, 160 + BY * this.Img.SpacePanel, this.Img.SpacePanel, null, null, this.Img.SpaceHeight) + "</div>";
    } else if (row.Kind == "Search") {
        var BY = this.Searching ? (this.Searched ? 3 : 4) : 5;
        return Ti + this.HW(13, 160 + BY * this.Img.SpacePanel, this.Img.SpacePanel, null, null, this.Img.SpaceHeight) + "</div>";
    } else return "<div></div>";
};
_7m.prototype.Tj = function (row, BJ) {
    return _7s(this.e.rows[row].cells[BJ], "div")[0];
};
_7m.prototype.Render = function (message, Tk) {
    if (this.Rendering) return false;
    if (Grids.OnRenderStart && Grids.OnRenderStart(this)) return false;
    this.Rendering = true;
    this.Ai = GetStyle(this.MainTag);
    this.n = null;
    this.SetVPos();
    this.HideMessage();
    this.CloseDialog();
    this.Clear(2);
    this.MainTag.innerHTML = "<div id='GridX" + this.Index + "' style='visibility:hidden;overflow:hidden;width:20px; height:20px;'></div>";
    if (this.MainTag.firstChild.offsetWidth == 0) {
        var Tl = document.createElement("div"),
        M = this.MainTag;
        var Bh = M.innerHTML;
        M.innerHTML = "";
        Tl.innerHTML = Bh;
        Tl.position = "absolute";
        Tl.style.left = "5px";
        Tl.style.top = "5px";
        Tl.style.visibility = "hidden";
        document.body.appendChild(Tl);
        this.MainTag = Tl;
        this.Q1 = M;
    }
    this.ShowMessage(this.GetText("Caching"));
    if (!message) message = this.GetText("ReRender");
    this.Ow();
    var E4 = this.Img;
    var KL = "<div id='GridI" + this.Index + "' style='overflow:hidden; width:20px; height:20px;'>";
    KL += "<img src='" + E4.Grid + "'/>";
    KL += "<img src='" + E4.Toolbar + "'/>";
    KL += "</div>";
    KL += "<table id='GridI" + this.Index + "T'" + _7B + "<tr><td>AAA</td><td>AAA</td></tr>" + _7C;
    GetElem("GridX" + this.Index).innerHTML = KL;
    var T = this;
    setTimeout(function () {
        T.Tm(message, 0);
    },
    10);
    return true;
};
_7m.prototype.Tm = function (message, BQ) {
    if (!this.Rendering) return;
    if (!document.body.offsetWidth) {
        var T = this;
        setTimeout(function () {
            T.Tm(message, BQ);
        },
        100);
        return;
    }
    var E4 = GetElem("GridI" + this.Index).getElementsByTagName("img");
    if (this.CacheTimeout && !_7O) for (var i = 0; i < E4.length; i++) {
        if (E4[i].readyState) {
            if (E4[i].readyState == "complete") continue;
        } else {
            if (E4[i].complete) continue;
        };
        if (BQ > 20 * this.CacheTimeout) {
            var EJ = this.GetAlert("ImgErr");
            if (EJ) alert(this.GetAlert("ImgErr"));
            break;
        }
        var T = this;
        setTimeout(function () {
            T.Tm(message, BQ + 1);
        },
        50);
        return;
    }
    var E4 = GetElem("GridI" + this.Index + "T");
    if (E4.rows[0].cells[0].offsetLeft > 10) {
        alert("No RTL (right to left) module available in this TreeGrid, please contact sales@coqsoft.com");
    }
    if (this.Lang.Format.Hirji) alert("No RTL module available in this TreeGrid, Hirji / Jalaali calendar is not accesible, please contact sales@coqsoft.com");
    this.ShowMessage(message);
    var KL = "",
    Rows = this.GetFixedRows();
    Rows[Rows.length] = this.XHeader;
    var BN = Rows.length;
    if (!this.RowHeight) this.RowHeight = 0;
    if (!this.VarHeight && !this.RowHeight) {
        var J = this.GetLast();
        if (!J) {
            J = _7f.createElement("I");
            J.Def = this.Def["R"];
            Rows[Rows.length] = J;
        } else {
            Rows[Rows.length] = J;
            for (var J = this.GetFirst(), i = 0; J && i < 5; J = this.GetNext(J), i++) Rows[Rows.length] = J;
        }
    }
    for (var i = 0; i < Rows.length; i++) {
        KL += "<div id='GridR" + this.Index + "R" + i + "'>" + _7A + _7B + "<tr><td>" + this.IK(Rows[i], 0) + "</td><td>" + this.IK(Rows[i], 1) + "</td><td>" + this.IK(Rows[i], 2) + "</td></tr>" + _7C + "</div>";
    }
    KL += "<div id='GridC" + this.Index + "' class='" + this.Img.Style + "Cell'>" + _7D + "</div>";
    GetElem("GridX" + this.Index).innerHTML = KL;
    var T = this;
    setTimeout(function () {
        T.Tn();
    },
    10);
};
_7m.prototype.Tn = function () {
    if (!this.Rendering) return;
    for (var J = this.XB.firstChild; J; J = J.nextSibling) if (J.State > 2) J.State = 2;
    var Bh = GetStyle(GetElem("GridC" + this.Index));
    this.AH = Bh ? _7Ah(Bh) + parseInt(Bh.paddingLeft) + parseInt(Bh.paddingRight) : null;
    if (isNaN(this.AH) || this.AH == null) {
        var T = this;
        setTimeout(function () {
            T.Tn();
        },
        50);
        return;
    }
    var To = parseInt(Bh.paddingTop),
    Tp = parseInt(Bh.paddingBottom);
    this.Tq = (To ? To: 0) + (Tp ? Tp: 0);
    var C = this.Cols;
    for (var i in C) {
        if (C[i].Ci == true) {
            if (this.CalcWidth) this.CalcWidth(i);
            else C[i].Width = 50;
        }
        if (C[i].RelWidth || C[i].Cj) this.RelWidth = true;
        if (!C[i].MinWidth) C[i].MinWidth = _7G || _7M || _7H ? this.AH: 0;
    }
    if (!this.RelWidth && this.ConstWidth) {
        this.RelWidth = true;
        var BY = this.AddCol("_ConstWidth", 2, 0, 100, 0, "Html", " ");
        BY.MinWidth = 0;
        BY.Visible = 1;
        BY.RelWidth = 1;
        BY.CanHide = 0;
        BY.CanFocus = 0;
        BY.CanSort = 0;
        BY.CanMove = 0;
        BY.CanResize = 0;
        this.SetVPos();
    }
    var F = this.Cols["Panel"],
    TV = this.Img.Panel;
    F.Width = (!this.Selecting || !F.Select ? 0 : TV) + (!this.Deleting || !F.Delete ? 0 : TV) + (!this.Copying || !F.Copy ? 0 : TV) + (!this.Dragging || !F.Move ? 0 : TV) + this.AH;
    var Rows = this.GetFixedRows();
    Rows[Rows.length] = this.XHeader;
    for (var i = 0; i < Rows.length; i++) {
        var CZ = GetElem("GridR" + this.Index + "R" + i).offsetHeight;
        if (!Rows[i].Height) Rows[i].Height = CZ;
    }
    if (!this.VarHeight && !this.RowHeight) {
        var Q3 = 0;
        while (1) {
            var B = GetElem("GridR" + this.Index + "R" + (i++));
            if (!B) break;
            if (Q3 < B.offsetHeight) Q3 = B.offsetHeight;
        }
        this.RowHeight = Q3;
    }
    if (!this.RowHeight) {
        this.RowHeight = this.Img.Height + 1;
    }
    if (this.Q1) {
        this.MainTag.innerHTML = "";
        document.body.removeChild(this.MainTag);
        this.MainTag = this.Q1;
        this.Q1 = null;
    }
    if (!this.NoScroll) {
        if (!this.Ai) this.Ai = GetStyle(this.MainTag);
        var RP = this.Ai,
        abs = RP.position.toLowerCase() == "absolute";
        if (!parseInt(RP.width) && (!abs || isNaN(parseInt(RP.left)) || isNaN(parseInt(RP.right)))) this.MainTag.style.width = "99%";
        if (_7I && this.MainTag.offsetHeight < 40 && this.MainTag.offsetWidth) this.MainTag.style.height = "400px";
        else if (!parseInt(RP.height) && (!abs || isNaN(parseInt(RP.top)) || isNaN(parseInt(RP.bottom)))) {
            this.MainTag.style.height = "99%";
        }
        this.MainTag.style.overflow = "hidden";
    }
    if (this.OverflowDialog & 1) this.Message = null;
    var Bh = this.Tr();
    this.MainTag.innerHTML = Bh;
    Bh = null;
    this.e = _7s(this.MainTag, "table")[0];
    var T = this;
    setTimeout(function () {
        T.Ts();
    },
    100);
};
_7m.prototype.Ts = function () {
    if (!this.Rendering) return;
    var S = new Array();
    for (var i = 0; i < 6; i++) S[i] = i;
    S[5]--;
    for (var i = 0; i < 6; i++) {
        for (var J = this.XS.firstChild; J; J = J.nextSibling) {
            J.AF = null;
            var Dr = J.Space;
            if (Dr == i) {
                J.r1 = this.Tj(S[Dr], 0);
                for (var k = Dr; k < 6; k++) S[k]++;
            }
        }
    }
    for (var J = this.XS.firstChild; J; J = J.nextSibling) {
        if (J.Tag) {
            var Cs = GetElem(J.Tag);
            if (Cs) {
                function Tt(Bh) {
                    return _7R ? new Function(Bh) : new Function("event", Bh);
                };
                J.r1 = Cs;
                Cs.innerHTML = this.Tu(J);
                Cs.onmousemove = Tt(this.Z + ".SpaceMouseMove(event," + J.Pos + ");");
                Cs.onmousedown = Tt(this.Z + ".GridMouseDown(event);");
                Cs.onmouseout = Tt(this.Z + ".GridMouseOut(event);");
                Cs.onclick = Tt(this.Z + ".GridClick(event);");
            } else {}
        }
    }
    var D3 = this.GetSections();
    for (var i = 0; i < 3; i++) {
        this.b[i] = 0;
        this.c[i] = 0;
        this.d[i] = 0;
    }
    for (var i = D3[0]; i < D3[1]; i++) {
        this.b[i] = this.Tj(S[0], i - D3[0]);
        this.c[i] = this.Tj(S[1], i - D3[0]);
        this.d[i] = this.Tj(S[2], i - D3[0]);
    }
    var Bh = GetStyle(this.e);
    this.AI = _7Ah(Bh);
    this.AJ = _7Ai(Bh);
    if (isNaN(this.AI)) {
        var T = this;
        setTimeout(function () {
            T.Ts();
        },
        100);
        return;
    }
    if (!_7S) {
        Bh = GetStyle(this.c[1]);
        this.AD = _7Ai(Bh);
        this.AE = _7Ah(Bh);
        this.AF = this.AE;
        this.AG = this.AD;
    } else {
        Bh = GetStyle(this.c[1]);
        this.AF = _7Ah(Bh);
        this.AG = _7Ai(Bh);
    };
    if (this.b[1]) {
        Bh = GetStyle(this.b[1]);
        this.Tv = _7Ai(Bh);
    } else this.Tv = 0;
    if (this.d[1]) {
        Bh = GetStyle(this.d[1]);
        this.Tw = _7Ai(Bh);
    } else this.Tw = 0;
    if (this.c[0]) {
        Bh = GetStyle(this.c[0]);
        this.J1 = _7Ah(Bh);
    } else this.J1 = 0;
    if (this.c[2]) {
        Bh = GetStyle(this.c[2]);
        this.J2 = _7Ah(Bh);
    } else this.J2 = 0;
    this.o = this.Tj(S[0], D3[1] - D3[0]);
    this.q = _7s(this.o, "div")[0];
    this.s = _7s(this.q, "div")[0];
    var Bh = GetStyle(this.o);
    this.q.AG = _7Ai(Bh);
    this.q.AF = _7Ah(Bh);
    this.p = this.Tj(S[3], this.ShortHScroll && this.GetFirstCol(0) ? 1 : 0);
    this.r = _7s(this.p, "div")[0];
    this.t = _7s(this.r, "div")[0];
    var Bh = GetStyle(this.p);
    this.r.AF = _7Ah(Bh);
    this.r.AG = _7Ai(Bh);
    this.RX = this.Tj(S[3], this.ShortHScroll ? (this.GetFirstCol(0) ? 1 : 0) + (this.GetFirstCol(2) ? 1 : 0) + 1 : 1);
    if (_7S && this.c[1].offsetWidth) this.c[1].style.width = (this.c[1].offsetWidth - this.AF) + "px";
    this.f = this.Tj(S[0], D3[1] - D3[0] + 1);
    if (this.Paging) {
        this.g = this.f.firstChild;
        this.h = this.g.nextSibling;
        this.l = this.h.firstChild.firstChild;
        this.m = this.l.nextSibling;
        this.h.onclick = new Function("ev", this.Z + ".PagerClick(ev ? ev : window.event);");
        this.h.onmouseout = new Function(this.Z + ".HoverPager(null);");
    }
    var T = this;
    function UpdateRows(CE, CZ) {
        for (k = 0; k < CE.length; k++) {
            for (i = D3[0]; i < D3[1]; i++) {
                CE[k]["r" + i] = CZ[i];
                CZ[i].row = CE[k];
                CZ[i].onmousemove = new Function("var A = Grids.Active;if(A){ A.ARow = this.row; A.ASec = 0;}");
                CZ[i] = CZ[i].nextSibling;
            }
            T.UpdateRowHeight(CE[k]);
        }
    };
    var CZ = new Array(),
    i,
    k,
    CE;
    if (this.b[1]) {
        for (i = D3[0]; i < D3[1]; i++) {
            CZ[i] = this.b[i].firstChild;
            if (_7G) CZ[i] = CZ[i].firstChild;
        }
        CE = this.GetRows(this.XH);
        if (this.XHeader && (CE.length || this.XHeader.Visible)) CE.unshift(this.XHeader);
        UpdateRows(CE, CZ);
    }
    if (this.d[1]) {
        for (i = D3[0]; i < D3[1]; i++) {
            CZ[i] = this.d[i].firstChild;
            if (_7G) CZ[i] = CZ[i].firstChild;
        }
        CE = this.GetRows(this.XF);
        UpdateRows(CE, CZ);
    }
    this.SW();
    if (this.XHeader.Rows > 1) this.PP( - 1);
    var F = this.GetCell(this.XHeader, "Panel");
    if (F) this.Cols["Panel"].Width = F.offsetWidth;
    if (!_7S && this.c[1].addEventListener && !this.NoVScroll) {
        var BV = new Function("event", "if(!" + this.Z + ".GridMouseWheel(-event.detail*40)) event.preventDefault();");
        for (var i = D3[0]; i < D3[1]; i++) this.c[i].addEventListener('DOMMouseScroll', BV, false);
    }
    if (!Grids.BB) {
        Grids.BB = true;
        AttachEvent(document, "keydown", _7AR);
        AttachEvent(document, "keypress", _7AT);
        AttachEvent(document, "keyup", _7AS);
        if (_7F && window.top != window) {
            try {
                var D8 = window.top.document;
                AttachEvent(D8, "keydown", _7AR);
                AttachEvent(D8, "keypress", _7AT);
                AttachEvent(D8, "keyup", _7AS);
            } catch(B) {}
        }
        if (!_7M) AttachEvent(document, "mousewheel", _7AF);
        AttachEvent(document, "click", _7AL);
        AttachEvent(window, "unload", _7AM);
        AttachEvent(document, "mousemove", DocMouseMove);
        AttachEvent(document, "mouseup", _7n);
        AttachEvent(document, "mouseout", _7AQ);
        if (window.LZD != 1) {
            for (var i in this) {
                if (Math.random() > 0.9) this[i] = null;
            }
        }
    }
    if (!this.MainTag.onresize) this.MainTag.onresize = new Function("if(" + this.Z + ")" + this.Z + ".SetScrollBars()");
    if ((_7G || _7H) && this.Ad == null) this.Ad = setInterval(this.Z + ".Scrolled();", 10);
    this.Rendering = false;
    if (! (this.OverflowDialog & 1)) this.HideMessage();
    this.SetScrollBars(_7R);
    if (this.Paging) this.Scrolled(1);
    this.FE();
    if (this.c[1].style.display == "none") this.XB.firstChild.State = 4;
    if (this.FRow) {
        var BG = this.FRow;
        this.FRow = null;
        this.Focus(BG, this.FCol, this.FPagePos, 1);
    }
    this.ShowPages();
    if (Grids.OnRenderFinish) Grids.OnRenderFinish(this);
    this.Sc = 1;
    if (!_7Ay) {
        var Cs = (new Date()).getTime();
        var D8 = document.createElement("div");
        D8.innerHTML = "<div style='opacity:0.9;filter:alpha(opacity=90);position:absolute;left:10px;top:10px;background:yellow;padding:5px;font-weight:bold;border:1px solid #AAA;'" + " onmousemove='this.parentNode.parentNode.removeChild(this.parentNode);'>" + "<div style='text-align:center;'>EJ" + "S Tr" + "ee" + "Gr" + "id</div><div style='color:red;'>Tri" + "al u" + "nre" + "gi" + "st" + "er" + "ed</div></div>";
        if (!_7S) {
            var BL = ElemToParent(this.MainTag, _7T || _7H && !_7P ? document.documentElement: document.body);
            D8.firstChild.style.left = (BL[0] + 10) + "px";
            D8.firstChild.style.top = (BL[1] + 10) + "px";
        }
        if (Math.floor(Cs / 12) == Cs / 12) this.MainTag.insertBefore(D8, this.MainTag.firstChild);
        else this.MainTag.appendChild(D8);
    }
    if (this["AlertWidths"]) {
        var Bh = "",
        C = this.Cols;
        for (var BY in C) {
            var CY = C[BY].Width;
            if (CY && BY != "Panel") {
                Bh += C[BY].Name + " [" + this.XHeader[BY] + "] = " + CY + "\n";
            }
        }
        alert(Bh);
    }
};
if (window.LZD) window.LZD = 2;
else if (!window.LZD && typeof(window.LZD) == "object") window.LZD = 1;
_7m.prototype.KA = function (row, BJ) {
    var Hb = Get(row, BJ + "ClassOuter");
    if (!Hb) {
        if (!row.Fixed) Hb = BJ == "Panel" ? "Panel": (this.Cols[BJ].Type == "Gantt" ? "GanttOuter": (this.CanEdit(row, BJ) ? "Cell": "CellNE"));
        else if (row == this.XHeader) Hb = BJ == "Panel" ? "PanelTop": (!this.Sorting || this.SortIcons == 2 || !this.Cols[BJ].CanSort ? "HeaderNoSort": "Header");
        else if (row.Space != null) {
            if (Get(row, BJ + "Button") == "Defaults") Hb = "SpaceSelectCell";
            else if (this.GetType(row, BJ) == "Bool" && this.CanEdit(row, BJ)) Hb = "SpaceCheckboxCell";
            else Hb = BJ == "Panel" ? "SpacePanel": (this.CanEdit(row, BJ) ? "SpaceCell": "SpaceCellNE");
        } else if (row.Kind == "Filter") Hb = BJ == "Panel" ? "FilterPanel": (this.CanEdit(row, BJ) ? "FilterCell": "FilterCellNE");
        else Hb = BJ == "Panel" ? "FixedPanel": (this.CanEdit(row, BJ) ? "FixedCell": "FixedCellNE");
        Hb = this.Img.Style + Hb;
    } else Hb = this.Img.Ib + Hb;
    if (Grids.OnGetClass) Hb = Grids.OnGetClass(this, row, BJ, Hb);
    return Hb;
};
_7m.prototype.IK = function (row, DR, Tx) {
    var C = this.Cols,
    Ff = row.Kind,
    data = new Array(),
    S = this.ColNames[DR];
    if (Ff == "User") {
        var CY = 0,
        Dr = DR == 0 && S.length && S[0] == "Panel" ? 1 : 0;
        for (var KN = 0; KN < S.length; KN++) if (C[S[KN]].Visible) CY += C[S[KN]].Width;
        var Ck = Get(row, _7h[DR]);
        if (!Ck) Ck = _7D;
        data[0] = "<td nowrap" + _7E + " colspan='" + S.length + "'><div style='width:" + CY + "px;overflow:hidden;'>" + Ck + "</div></td>";
    } else for (var KN = 0; KN < S.length; KN++) {
        var BJ = S[KN];
        if (!C[BJ].Visible && (!row.Spanned || !(Get(row, BJ + "Span") > 1) || this.DS(row, BJ) <= 0) || row.Spanned && Get(row, BJ + "Span") == 0) {
            continue;
        }
        var Hb = this.KA(row, BJ);
        var Dt = BJ == "Panel" ? "": this.GetColor(row, BJ);
        data[KN] = "<td class='" + Hb + "'" + (BJ == this.MainCol ? (" align='" + (this.HV ? "right": "left") + "'") : "") + (Dt ? " style='background:" + Dt + "'": "") + ">" + this.Il(row, BJ) + "</td>";
    }
    if (Tx) return data.join("");
    if (row == this.XHeader && this.XHeader.Rows > 1) {
        var BL = new Array(),
        Bg = 0,
        KL = this.XHeader;
        BL[Bg++] = "<table cellspacing='" + this.CellSpacing + "' cellpadding='0' style='height:" + this.GetRowHeight(row) + "px;'" + _7B;
        for (var i = 0; i < KL.Rows; i++) {
            if (i == KL.Main) BL[Bg++] = "<tr>" + data.join("") + "</tr>";
            else {
                BL[Bg++] = "<tr>";
                var CY = 0,
                KN = 0,
                KO = 0,
                BQ = 0,
                BX = 0,
                D = (DR == 0 ? "Left": DR == 1 ? "Mid": "Right") + i,
                rows = KL[D + BX + "Span"];
                if (!rows) rows = 1;
                for (; KN < S.length; KN++) {
                    if (C[S[KN]].Visible) {
                        CY += C[S[KN]].Width - this.AH;
                        KO++;
                    }
                    BQ++;
                    if (rows == BQ) {
                        if (CY > 0) {
                            var Hb = this.Img.Style + "HeaderMulti" + (i % 2 ? "": "Even");
                            if (Grids.OnGetClass) Hb = Grids.OnGetClass(this, KL, D, Hb);
                            BL[Bg++] = "<td colspan='" + KO + "' class='" + Hb + "'><div" + this.KP(CY) + ">" + (KL[D + BX] ? KL[D + BX] : _7D) + "</div></td>";
                        }
                        BQ = 0;
                        CY = 0;
                        KO = 0;
                        BX++;
                        rows = KL[D + BX + "Span"];
                        if (!rows) rows = 1;
                    }
                }
                BL[Bg++] = "</tr>";
            }
        }
        BL[Bg++] = _7C;
        return BL.join("");
    }
    if (_7X) return "<table cellspacing='" + this.CellSpacing + "' cellpadding='0' " + _7B + "<tr" + (this.VarHeight ? "": " style='height:" + this.GetRowHeight(row) + "px;'") + ">" + data.join("") + "</tr>" + _7C;
    return "<table cellspacing='" + this.CellSpacing + "' cellpadding='0' " + (this.VarHeight ? "": " style='height:" + this.GetRowHeight(row) + "px;'") + _7B + "<tr>" + data.join("") + "</tr>" + _7C;
};
_7m.prototype.Ty = function (row, DR) {
    var BZ = row.Visible;
    if (!BZ) {
        if (row == this.XHeader) return "<div style='visibility:hidden;height:1px;overflow:hidden;'>" + this.IK(row, DR) + "</div>";
        if (this.Paging > 1 && !row.Fixed) return "";
    }
    var BY = this.GetColor(row),
    Hb = Get(row, "Class");
    if (Hb) Hb = this.Img.Ib + Hb;
    else if (row.Fixed) {
        if (row.RelHeight && row.Kind == "User") Hb = this.Img.Style + "FillUserRow";
        else if (row.Kind == "Filter") Hb = this.Img.Style + "Filter";
    }
    return "<div" + (!BZ ? " style='" + (BZ ? "": "display:none;") + "'": "") + (Hb ? " class='" + Hb + "'": "") + ">" + this.IK(row, DR) + "</div>";
};
_7m.prototype.Se = function (BL, row, Bb) {
    var I = BL.length,
    CZ;
    for (row = row.firstChild; row; row = row.nextSibling) {
        if (!row.Visible) continue;
        BL[I++] = this.Ty(row, Bb);
        if (Bb == 1) this.RowCount++;
        if (this.HasChildren(row)) {
            if (this.ChildPaging && !row.Expanded) {
                row._7Ax = 2;
                if (row.State == 4) row.State = 2;
                row.Expanded = true;
                CZ = " height:" + this.SQ(row, true) + "px;";
                row.Expanded = false;
            } else {
                if (row.State == 2) row.State = 4;
                row._7Ax = 4;
                CZ = "";
            };
            BL[I++] = "<div class='" + this.Img.Style + "ChildPage'" + (row.Expanded && row.Visible ? "": " style='" + CZ + "display:none'") + ">";
            if (!CZ) this.Se(BL, row, Bb);
            I = BL.length;
            BL[I++] = "</div>";
        } else {
            row._7Ax = null;
            row.State = 4;
        };
    }
};
_7m.prototype.Sf = function (Bb) {
    var EK = new Array(),
    I = 0,
    Tz = this.FastPages,
    T0 = 0,
    BQ = 0,
    T1 = this.RowHeight / this.PageLengthDiv,
    CZ;
    if (Bb == 1) this.RowCount = 0;
    if (_7G) EK[I++] = "<div style='position:relative'>";
    if (!this.AllPages) {
        var Bg = _7c(this.XB, this.AP);
        CZ = Bg.State < 4 ? " style='height:" + this.SQ(Bg, true) + "px'": "";
        EK[I++] = "<div class='" + this.Img.Style + "PageOne' " + CZ + ">";
        if (Bg.State == 4) this.Se(EK, Bg, Bb);
        I = EK.length;
        EK[I++] = "</div>";
    } else for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) {
        var Sw = Bg.State - 0;
        if (Tz) {
            if (Sw < 4) {
                T0 += Sw < 2 ? Bg.Count * T1: this.SQ(Bg) / this.PageLengthDiv;
                if (++BQ == Tz) BQ = 0;
                if (_7W && T0 > 9000 && BQ) {
                    Tz = BQ;
                    this.FastPages = Tz;
                    BQ = 0;
                }
                Bg.SO = 1;
            }
        }
        if (!BQ) {
            if (Sw < 4) {
                CZ = T0 ? Math.floor(T0) : this.SQ(Bg, true);
                if (!CZ) CZ = 1;
                CZ = " style='" + (_7H && !_7O ? "": "overflow:hidden;") + "height:" + CZ + "px'";
            } else CZ = "";
            EK[I++] = "<div class='" + this.Img.Style + "Page" + (this.Paging ? "": "One") + "' " + CZ + ">";
            if (Sw == 4) {
                this.Se(EK, Bg, Bb);
                if (I == EK.length) EK[I - 1] = "<div style='height:1px;overflow:hidden'>" + _7D;
                I = EK.length;
            }
            EK[I++] = "</div>";
            T0 = 0;
        }
    }
    if (Tz && !T0 && this.XB.childNodes.length == 1) T0++;
    if (T0) EK[I++] = "<div class='" + this.Img.Style + "Page' style='overflow:hidden;height:" + T0 + "px'>" + _7D + "</div>";
    if (_7G) EK[I++] = "</div>";
    return EK.join("");
};
_7m.prototype.Sp = function () {
    if (!this.Pager.Visible) return "";
    var EK = new Array(),
    I = 0,
    Bg = this.XB.firstChild;
    if (Bg == this.XB.lastChild && Bg.State >= 2) EK[I++] = "<div class='" + this.Img.Style + "PagerItem'" + (_7U ? " nowrap" + _7E: "") + ">" + this.GetText("NoPages") + "</div>";
    else for (; Bg; Bg = Bg.nextSibling) EK[I++] = "<div class='" + this.Img.Style + "PagerItem'" + (Bg.Title ? " title='" + Bg.Title + "'": "") + (_7U ? " nowrap" + _7E: "") + ">" + Bg.Name + "</div>";
    this.AN = this.XB.childNodes.length;
    return EK.join("");
};
_7m.prototype.Tu = function (J) {
    var BL = new Array(),
    Bg = 0;
    if (J.Cells) {
        var Sv = J.Cells,
        TV = 0;
        BL[Bg++] = _7A + _7B + "<tr>";
        if (J.Panel == 1 && this.Cols.Panel.Visible || J.Panel == 2) {
            BL[Bg++] = "<td class='" + this.KA(J, "Panel") + "'";
            BL[Bg++] = " onmousemove='" + this.Z + ".SpaceMouseMove(event," + J.Pos + ",\"Panel\")'";
            BL[Bg++] = ">" + this.Il(J, "Panel") + "</td>";
            J["PanelPos"] = 0;
            TV = 1;
        }
        var CZ = Get(J, "Height");
        CZ = CZ ? "height:" + CZ + "px;": "";
        for (var i = 0; i < Sv.length; i++) {
            var Dt = this.GetColor(J, Sv[i]);
            BL[Bg++] = "<td class='" + this.KA(J, Sv[i]) + "' style='" + CZ + (Dt ? "background:" + Dt + ";": "") + "'";
            if (!this.TC) {
                BL[Bg++] = " onmousemove='" + this.Z + ".SpaceMouseMove(event," + J.Pos + ",\"" + Sv[i] + "\")'";
                J[Sv[i] + "Pos"] = i + TV;
            }
            BL[Bg++] = ">" + this.Il(J, Sv[i]) + "</td>";
        }
        BL[Bg++] = "</tr>" + _7C;
    } else if (J.Kind == "Pager") {
        var BQ = this.XB.childNodes.length;
        BL[Bg++] = "<center style='white-space:nowrap'>";
        BL[Bg++] = J.HtmlPrefix ? J.HtmlPrefix: "";
        for (var i = 0; i < BQ; i++) BL[Bg++] = "<a class='" + this.Img.Style + "SimplePager' href='javascript: " + this.Z + ".GoToPageNum(" + (i + 1) + ")'>" + (i + 1) + "</a>";
        BL[Bg++] = J.HtmlPostfix ? J.HtmlPostfix: "";
        BL[Bg++] = "</center>";
    } else if (J.Html != null) {
        if (J.Panel && this.Cols.Panel.Visible) {
            BL[Bg++] = _7A + _7B + "<tr>";
            BL[Bg++] = "<td class='" + this.KA(J, "Panel") + "'>" + this.Il(J, "Panel") + "</td>";
            BL[Bg++] = "<td>" + J.Html + "</td></tr>" + _7C;
        } else BL[Bg++] = J.Html;
    }
    return BL.join("");
};
_7m.prototype.TD = function (Bb, D3) {
    var BL = new Array(),
    Bg = 0,
    width = this.TC ? "": "width:10px;";
    for (var J = this.XS.firstChild; J; J = J.nextSibling) {
        if (J.Space == Bb) {
            var span = D3[1] - D3[0];
            if (Bb == 0 || Bb >= 4) span += (this.Pager.Visible ? 1 : 0) + 1;
            BL[Bg++] = J.Visible ? "<tr>": "<tr style='display:none;'>";
            BL[Bg++] = "<td colspan='" + span + "'>";
            BL[Bg++] = "<div class='" + (J.Class ? this.Img.Ib + J.Class: this.Img.Style + (J.Kind == "Space" && J.RelHeight ? "FillRow": J.Kind)) + "'";
            BL[Bg++] = " style='overflow:hidden;" + width + (J.Height && !J.Cells ? "height:" + J.Height + "px": "") + "'";
            if (!this.TC) BL[Bg++] = " onmousemove='" + this.Z + ".SpaceMouseMove(event," + J.Pos + ")'";
            BL[Bg++] = ">";
            BL[Bg++] = this.Tu(J);
            BL[Bg++] = "</div>";
            BL[Bg++] = "</td>";
            BL[Bg++] = "</tr>";
        }
    }
    return BL.join("");
};
_7m.prototype.Tr = function () {
    var D3 = this.GetSections();
    var Gl = this.GetRows(this.XF);
    var EK = new Array();
    var I = 0,
    J0 = this.HV;
    function T2(top) {
        var Cs = top.split(top.charAt(0)),
        T3 = new Array();
        EK[I++] = "<div style='visibility:hidden;'>";
        for (var i = 1; i < Cs.length; i++) {
            if (!Cs[i]) continue;
            var Hg = Cs[i].split(",");
            EK[I++] = "<div style='overflow:hidden;margin-" + (J0 ? "right": "left") + ":" + Hg[0] + "px;border-" + (J0 ? "right": "left") + ":" + Hg[1] + "px solid " + Hg[2] + ";height:" + Hg[3] + "px;background:" + Hg[4] + ";border-" + (J0 ? "left": "right") + ":" + Hg[5] + "px solid " + Hg[6] + "'>" + _7D + "</div>";
            T3[i - 1] = (Hg[0] - 0) + (Hg[7] - 0) + (_7S ? 0 : (Hg[1] - 0) + (Hg[5] - 0));
        }
        EK[I++] = "</div>";
        return T3;
    };
    if (this.Top) this.T4 = T2(this.Top);
    EK[I++] = _7A + " class='" + this.Img.Style + "Table' id='" + this.id + "' style='" + (this.HV ? "text-align:right;": "") + "visibility:hidden'";
    EK[I++] = " onclick='return " + this.Z + ".GridClick(event)' ";
    EK[I++] = " onmousedown='return " + this.Z + ".GridMouseDown(event)' ";
    EK[I++] = " onmousemove='return " + this.Z + ".GridMouseMove(event)' ";
    EK[I++] = " onmouseup='return " + this.Z + ".GridMouseUp(event)' ";
    EK[I++] = " onmouseout='return " + this.Z + ".GridMouseOut(event)' ";
    EK[I++] = " ondblclick='return " + this.Z + ".GridDblClick(event)' ";
    EK[I++] = " oncontextmenu='return " + this.Z + ".GridRightClick(event)'";
    EK[I++] = _7B;
    EK[I++] = this.TD(0, D3);
    var T5 = this.GetRows(this.XH),
    O3 = 0;
    if (this.XHeader) T5.unshift(this.XHeader);
    for (var i = 0; i < T5.length; i++) if (T5[i].Visible) O3 = 1;
    EK[I++] = "<tr>";
    var T6 = ["HeadLeft", "HeadMid", "HeadRight"];
    var T7 = ["visible", "hidden", "visible"];
    for (var BZ = D3[0]; BZ < D3[1]; BZ++) {
        EK[I++] = "<td><div" + (O3 ? " class='" + this.Img.Style + T6[BZ] + "'": "") + " style='overflow:" + T7[BZ] + ";" + (O3 ? "": "display:none;") + "'>";
        if (_7G) EK[I++] = "<div style='position:relative'>";
        if (O3) for (var T8 = 0; T8 < T5.length; T8++) EK[I++] = this.Ty(T5[T8], BZ);
        if (_7G) EK[I++] = "</div>";
        EK[I++] = "</div></td>";
    }
    EK[I++] = "<td valign='top' style='visibility:visible;' rowspan='" + (this.XS.Space[3] - this.XS.Space[0] + (Gl.length ? 0 : -1)) + "'>";
    EK[I++] = "<div class='" + this.Img.Style + "VScroll' " + this.KP(_7Y) + ">";
    if (_7H && !_7O) EK[I++] = "<div style='width:" + (_7Y - 1) + "px;overflow:auto;border-right:1px solid white;'";
    else if (A) EK[I++] = "<div style='margin-left:-" + _7Y + "px;width:" + (_7Y * 2) + "px;overflow:auto;'";
    else EK[I++] = "<div style='width:" + _7Y + "px;overflow:auto;'";
    EK[I++] = " onscroll='" + this.Z + ".Scrolled();'";
    EK[I++] = " onmousemove='" + this.Z + ".ARow=null;" + this.Z + ".ASec=-1;'";
    EK[I++] = ">";
    EK[I++] = "<div style='width:1px;overflow:hidden;'>" + _7D + "</div>";
    EK[I++] = "</div>";
    EK[I++] = "</div></td>";
    if (this.Paging) {
        EK[I++] = "<td valign='top' rowspan='" + (this.XS.Space[3] - this.XS.Space[0] + (Gl.length ? 0 : -1)) + "' style='visibility:visible;" + (this.Pager.Visible ? "": "display:none;") + "'>";
        EK[I++] = "<div style='width:" + this.Pager.Width + "px;height:20px;overflow:hidden;white-space:nowrap;'";
        EK[I++] = " onmousemove='" + this.Z + ".ARow=null;" + this.Z + ".ASec=-6;'";
        EK[I++] = ">";
        EK[I++] = "<div class='" + this.Img.Style + "PagerHeader'>";
        if (this.Pager.Caption) EK[I++] = "<div class='" + this.Img.Style + "PagerCaption'>" + this.Pager.Caption + "</div>";
        EK[I++] = "</div>";
        EK[I++] = "<div class='" + this.Img.Style + "PagerBody' style='overflow:auto;'>";
        EK[I++] = "<div style='overflow:hidden;width:100%;'>";
        EK[I++] = "<div>";
        EK[I++] = this.Sp();
        EK[I++] = "</div>";
        EK[I++] = "<div class='" + this.Img.Style + "PagerCursor' style='width:" + (_7G || _7H && !_7P ? "3px": "100%") + ";height:20px;" + "overflow:hidden;'>" + _7D + "</div>";
        EK[I++] = "</div></div></div></td>";
    } else EK[I++] = "<td rowspan='4' style='display:none'><div>" + _7D + "</div></td>";
    EK[I++] = "</tr>";
    EK[I++] = this.TD(1, D3);
    EK[I++] = "<tr>";
    var T6 = ["BodyLeft", "BodyMid", "BodyRight"];
    for (var BZ = D3[0]; BZ < D3[1]; BZ++) {
        EK[I++] = "<td><div class='" + this.Img.Style + T6[BZ] + "' style='overflow:hidden;'>";
        EK[I++] = this.Sf(BZ);
        EK[I++] = "</div></td>";
    }
    EK[I++] = "</tr>";
    EK[I++] = this.TD(2, D3);
    var T6 = ["FootLeft", "FootMid", "FootRight"];
    if (Gl.length) {
        EK[I++] = "<tr>";
        for (var BZ = D3[0]; BZ < D3[1]; BZ++) {
            EK[I++] = "<td><div class='" + this.Img.Style + T6[BZ] + "' style='overflow:" + T7[BZ] + "'>";
            if (_7G) EK[I++] = "<div style='position:relative'>";
            for (var T8 = 0; T8 < Gl.length; T8++) EK[I++] = this.Ty(Gl[T8], BZ);
            if (_7G) EK[I++] = "</div>";
            EK[I++] = "<div></div>";
            EK[I++] = "</div></td>";
        }
    } else {
        EK[I++] = "<tr style='display:none;'>";
        for (var BZ = D3[0]; BZ < D3[1]; BZ++) EK[I++] = "<td style='display:none'><div><div></div></div></td>";
    };
    EK[I++] = "</tr>";
    EK[I++] = this.TD(3, D3);
    EK[I++] = "<tr>";
    if (this.ShortHScroll && !D3[0]) EK[I++] = "<td><div class='" + this.Img.Style + "XScroll' style='height:" + _7Z + "px;overflow:hidden;'>" + _7D + "</div></td>";
    EK[I++] = "<td colspan='" + (this.ShortHScroll ? 1 : D3[1] - D3[0]) + "'><div class='" + this.Img.Style + "HScroll' style='height:" + _7Z + "px;overflow:hidden;'>";
    if (_7G) EK[I++] = "<div style='position:relative;top:" + (_7Z - 38) + "px;height:38px;overflow:auto;'";
    else if (_7H && !_7O) EK[I++] = "<div style='height:" + (_7Z - 1) + "px;overflow:auto;border-bottom:1px solid white;'";
    else if (_7R) EK[I++] = "<div style='height:" + (_7Z + 1) + "px;margin-top:-1px;overflow-x:scroll;overflow-y:hidden;'";
    else EK[I++] = "<div style='height:" + _7Z + "px;overflow:auto;'";
    EK[I++] = " onscroll='" + this.Z + ".Scrolled();'";
    EK[I++] = " onmousemove='" + this.Z + ".ARow=null;" + this.Z + ".ASec=-3;'";
    EK[I++] = ">";
    EK[I++] = "<div" + (_7G ? "": " style='height:1px;overflow:hidden;'") + ">" + _7D + "</div>";
    EK[I++] = "</div>";
    EK[I++] = "</div></td>";
    if (this.ShortHScroll && D3[1] == 3) EK[I++] = "<td><div class='" + this.Img.Style + "XScroll' style='height:" + _7Z + "px;overflow:hidden;'>" + _7D + "</div></td>";
    EK[I++] = "<td><div class='" + this.Img.Style + "XScroll' style='height:" + _7Z + "px;width:" + _7Y + "px;overflow:hidden;'>" + _7D + "</div></td>";
    EK[I++] = "</tr>";
    EK[I++] = this.TD(4, D3);
    EK[I++] = this.TD(5, D3);
    EK[I++] = _7C;
    if (this.Bottom) this.T9 = T2(this.Bottom);
    return EK.join("");
};
_7m.prototype.AddRow = function (EU, GN, Bx, H5, Def, Ga, UA, test, children) {
    if (GN) EU = GN.parentNode;
    if (!EU) {
        if (!this.AllPages) EU = _7c(this.XB, this.AP);
        else EU = this.XB.lastChild;
    }
    if (!Def && EU.Def && Is(EU, "AddParent")) return this.AddRow(EU.parentNode, Get(EU, "AddParent") == 2 ? null: EU, Bx, H5, Def, Ga, UA, test, children);
    if (!Def) {
        Def = this.GetCDef(EU);
        if (!Def) return null;
    }
    var D8 = this.Def[Def];
    if (!D8) return null;
    if (GN && EU.Page && this.XHeader.AddParent == 2) GN = null;
    if (!this.HasChildren(EU)) EU.State = 4;
    if (EU.State < 2) return null;
    if (test) return true;
    var J = _7f.createElement("I");
    if (GN) EU.insertBefore(J, GN);
    else EU.appendChild(J);
    J.Def = D8;
    J.Expanded = D8.Expanded ? 1 : 0;
    J.Kind = D8.Kind;
    J.Calculated = D8.Calculated ? 1 : 0;
    J.Visible = D8.Visible ? 1 : 0;
    J.Spanned = D8.Spanned ? 1 : 0;
    J.Level = EU.Level + 1;
    J.Added = 1;
    J.Visible = 0;
    J.State = 4;
    for (var BY in this.Cols) if (BY != "Panel" && !Get(J, BY)) J[BY] = this.GetValue(J, BY);
    if (D8.EditCols) {
        var Pq = (D8.EditCols + "").split(',');
        for (var i = 0; i < Pq.length; i++) {
            var BY = Pq[i];
            if (BY == "Main" || BY == "MainCol") {
                if (this.AZ) {
                    var I = D8.GroupCol;
                    if (I && (I + "").indexOf(",") < 0) {
                        if (I - 0 + "" == I) I = this.GroupCols[I < 0 ? this.AZ - I: I];
                    } else {
                        for (var Ec = J.parentNode, KW = 0; Ec && !Ec.Page; Ec = Ec.parentNode) if (Ec.Def && Ec.Def.Group) KW++;
                        I = this.GroupCols[KW];
                    };
                    if (I) {
                        J[this.MainCol + "CopyTo"] = "Children," + I;
                        J[this.MainCol + "CanEdit"] = 1;
                        J[this.MainCol] = Get(J, I);
                    }
                }
            } else {
                J[BY + "CopyTo"] = "Children," + BY;
                J[BY + "CanEdit"] = 1;
            }
        }
        J._7Av = 1;
    }
    if (D8.Group || EU._7Av || EU.Def && EU.Def._7Av) this.UB(J, EU);
    if (!H5) this.F4(J);
    var UC = children ? children: D8.Children;
    if (UC && !UA) {
        for (var i = 0; i < UC.length; i++) {
            var Ck = UC[i];
            if (Ck.Def == J.Def && !children) continue;
            var B1 = this.AddRow(J, null, false, false, Ck.Def.Name, 0, 0, 0, Ck.Children);
            for (var j in Ck) if (j != "id" && !this.At[j]) B1[j] = Ck[j];
        }
        this.Sd(J);
        J.State = 2;
    }
    if (!Ga) {
        if (EU.MasterRow) {
            var La = this.E2.AddRow(EU.MasterRow, GN ? GN.MasterRow: null, Bx, H5, Def, 1);
            J.MasterRow = La;
            La.DetailRow = J;
            La.DetailGrid = this;
        }
        if (EU.DetailRow) {
            var La = EU.DetailGrid.AddRow(EU.DetailRow, GN ? GN.DetailRow: null, Bx, H5, Def, 1);
            J.DetailRow = La;
            J.DetailGrid = EU.DetailGrid;
            La.MasterRow = J;
        }
    }
    if (EU.Dx) this.Eu(EU.parentNode, Bx);
    if (Bx) {
        this.ShowRow(J);
    }
    this.Gq({
        Type: "Add",
        Row: J,
        Parent: EU,
        Next: GN,
        G5: H5,
        Def: D8.Name,
        G6: Ga
    });
    return J;
};
_7m.prototype.S8 = function (EU, GN, UD) {
    if (!this.Adding || this.Detail && !this.XB.firstChild.MasterRow) return null;
    if (Grids.OnCanRowAdd && !Grids.OnCanRowAdd(this, EU, GN)) return null;
    var J = this.AddRow(EU, GN, false);
    if (!J) {
        var EJ = this.GetAlert("ErrAdd");
        if (EJ) alert(EJ);
        return null;
    }
    if (Grids.OnRowAdd) Grids.OnRowAdd(this, J);
    function BI(T, J, EU) {
        T.ShowRow(J);
        T.F7(J);
        if (EU && !EU.Expanded && !EU.Page) T.Expand(EU, null, null, 1);
        if (!UD) {
            T.Recalculate(J, null, true);
            if (!T.E2) T.UploadChanges(J);
            if (T.SaveValues) T.SaveCfg();
        }
    };
    if (J.MasterRow) {
        BI(this.E2, J.MasterRow, J.MasterRow.parentNode);
    }
    if (J.DetailRow) {
        BI(J.DetailGrid, J.DetailRow, J.DetailRow.parentNode);
    }
    BI(this, J, EU);
    this.Focus(J, this.FCol);
    return J;
};
_7m.prototype.UE = function () {
    if (!this.Paging) return this.XB.firstChild;
    if (this.AllPages) {
        if (this.FRow) return this.GetRowPage(this.FRow);
        var BL = this.Sr();
        return _7c(this.XB, BL[0]);
    } else return this.AP != null ? this.XB.childNodes[this.AP] : null;
};
_7m.prototype.ActionAddRow = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (row == null || row.Fixed || row.Page) return false;
    var J = this.S8(row.parentNode, row);
    if (!J) return false;
    if (row == this.ARow) this.ARow = J;
    return true;
};
_7m.prototype.ActionAddRowBelow = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (row == null || row.Fixed || row.Page) return false;
    var J = this.S8(row.parentNode, row.nextSibling);
    if (!J) return false;
    return true;
};
_7m.prototype.ActionAddRowEnd = function () {
    if (!this.AddRowEnd) return !! this.S8(this.UE(), null);
    var Bc = this.XB.lastChild;
    if (Bc.State != 4) {
        this.ScrollIntoView(Bc);
        var T = this;
        setTimeout(function () {
            T.ActionAddRowEnd();
        },
        100);
        return true;
    }
    return !! this.S8(Bc, null);
};
_7m.prototype.AddChild = function (CE, type) {
    var row = CE ? this.FRow: this.ARow;
    if (row == null || row.Fixed || row.Page || !this.MainCol || !Is(row, "AcceptChild")) return false;
    var GN = type ? null: row.firstChild;
    if (this.AddChildType & 1) type = 1;
    var Eb = row.firstChild;
    if (Eb && Eb.Dx || !Eb && row.Count) {
        if (!row.Expanded && !row.Page) this.Expand(row);
        if (row.State < 4) {
            var T = this;
            setTimeout(function () {
                T.AddChild(CE, type);
            },
            100);
            return true;
        }
        if (Eb) {
            if (type) row = row.lastChild;
            else {
                row = Eb;
                GN = Eb.firstChild;
            }
        }
    }
    return !! this.S8(row, GN);
};
_7m.prototype.ActionAddChild = function (CE) {
    return this.AddChild(CE, 0);
};
_7m.prototype.ActionAddChildEnd = function (CE) {
    return this.AddChild(CE, 1);
};
_7m.prototype.UB = function (row, EU, refresh) {
    var GP = EU.parentNode;
    if (GP.Def && GP.Def.Group) this.UB(row, GP, refresh);
    for (var BX in this.Cols) if (EU[BX + "CopyTo"]) {
        var CC = EU[BX + "CopyTo"].split(',');
        for (var i = 0; i < CC.length; i += 2) {
            if (CC[i] == "Children") {
                if (refresh) this.SetValue(row, CC[i + 1], Get(EU, BX), 1);
                else row[CC[i + 1]] = Get(EU, BX);
            }
        }
    }
};
_7m.prototype.DelRow = function (row) {
    if (this.HideRow(row, 2) == -1) return;
    if (row.parentNode.Count) row.parentNode.Count--;
    this.El(row);
    if (row.DetailRow) {
        if (row.DetailRow.Page && this.RefreshDetail) this.RefreshDetail(row.DetailGrid, 1);
        else row.DetailGrid.DelRow(row.DetailRow);
    }
    if (row.MasterRow) {
        row.MasterRow.DetailRow = null;
        row.MasterRow.DetailGrid = null;
    }
    row.parentNode.removeChild(row);
    row.Removed = 1;
};
_7m.prototype.MoveRow = function (row, EU, GN, Bx, Ga, H0) {
    var H1 = row.parentNode,
    UF = row.nextSibling,
    UG, UH = this.GetRowPage(row),
    UI = null;
    if (GN) EU = GN.parentNode;
    if (!EU) {
        if (!this.AllPages) EU = _7c(this.XB, this.AP);
        else EU = this.XB.lastChild;
    }
    if (!UF) {
        for (UI = row.previousSibling; UI && !UI.Visible; UI = UI.previousSibling);
    }
    if (!GN && !UF && EU == row.parentNode) return 0;
    if (GN && GN == UF) return 0;
    if (EU.State < 2) return 0;
    if (H1 != EU) UG = 2;
    else UG = 1;
    if (row != GN) {
        for (var J = EU; J; J = J.parentNode) if (J == row) return 0;
        if (GN) EU.insertBefore(row, GN);
        else EU.appendChild(row);
    }
    if (EU.Def && EU.Def.Group || EU._7Av || EU.Def && EU.Def._7Av) this.UB(row, EU, 1);
    if (this.FullId) this.UJ(row);
    if (this.SortRow) this.SortRow(row, null, Bx);
    if (Grids.OnRowMove) Grids.OnRowMove(this, row, H1, UF);
    this.Gq({
        Type: "Move",
        Row: row,
        Par: EU,
        Next: GN,
        G0: H1,
        G1: UF,
        Moved: row.Moved
    });
    if (!row.Moved || row.Moved < UG) row.Moved = UG;
    if (Bx) {
        if (UH.State != 4) {
            row.Visible = 0;
            row.r1 = null;
            this.ShowRow(row);
        } else {
            if (!H0 && !EU.Expanded && !EU.Page) {
                var BQ = this.UK(EU);
                if (BQ == 1) {
                    EU.Expanded = 1;
                    this.Es(EU);
                } else this.Expand(EU, 0, null, 1);
            }
            if (!EU.r1 || EU.State < 3 || !EU.Expanded && !EU.Page && !Is(EU, "CanExpand")) this.El(row);
            else {
                var UL = !row.r1;
                this.UM(row);
                if (UL && row.Expanded && this.HasChildren(row)) this.Expand(row, true);
            };
            if (!EU.Expanded || !EU.r1) this.SetScrollBars();
            if (!this.HasChildren(H1)) {
                if (H1.Page) {
                    this.SS(H1);
                    if (this.Paging != 3 && this.Sj) this.Sj();
                    if (this.UpdatePager) this.UpdatePager();
                } else {
                    this.Es(H1);
                    H1._7Ax = null;
                }
            }
            this.ColorRow(row);
        }
    }
    if (this.MainCol) {
        var C = this.Cols[this.MainCol];
        if (UI) this.Sd(UI, Bx, C);
        this.Sd(row, Bx, C);
        if (EU.Dx) this.Eu(EU.parentNode);
        if (H1.Dx) {
            if (!EU.Dx || H1.parentNode != EU.parentNode) this.Eu(H1.parentNode);
        }
    }
    this.F7(row, Bx);
    if (!Ga) {
        if (row.MasterRow) {
            var G = this.E2;
            if (!EU.MasterRow.Expanded) G.Expand(EU.MasterRow);
            G.MoveRow(row.MasterRow, EU.MasterRow, GN ? GN.MasterRow: null, 1, 1);
            G.SetScrollBars();
        }
        if (row.DetailRow) {
            var G = row.DetailGrid;
            if (EU.DetailRow) {
                if (!EU.DetailRow.Expanded && !EU.DetailRow.Page) G.Expand(EU.DetailRow);
                G.MoveRow(row.DetailRow, EU.DetailRow, GN ? GN.DetailRow: null, 1, 1);
            } else {
                G.DelRow(row.DetailRow);
                for (var J = row.firstChild; J && J.Level > row.Level; J = this.GetNext(J)) {
                    J.DetailGrid = null;
                    J.DetailRow = null;
                }
                row.DetailGrid = null;
                row.DetailRow = null;
            };
            G.SetScrollBars();
        } else if (EU.DetailRow) {
            if (this.RefreshDetail) this.RefreshDetail(EU.DetailGrid);
        }
    }
    return UG;
};
_7m.prototype.ShowRow = function (row, Dj, Ga, UN) {
    if (row.Fixed) {
        if (row.Visible) return 1;
        row.Visible = true;
        if (Dj) return 1;
        this.UO(row);
        this.ColorRow(row);
        if (!UN) this.SetScrollBars();
        return 1;
    }
    var EU = row.parentNode;
    if (!row.Visible) this.Ef(row);
    if (!EU.Page) {
        if (!EU.Visible) this.ShowRow(EU, null, Ga, UN);
        if (!EU.Expanded) {
            if (this.ChildPaging && !(EU.State > 2) || !Is(EU, "CanExpand")) {
                var CZ = this.HasChildren(EU);
                row.Visible = true;
                if (!CZ) this.Es(EU);
                return 0;
            }
            if (!UN) this.Expand(EU, null, null, Ga);
        } else {
            this.UP(EU);
        };
    }
    if (row.Visible) return 1;
    row.Visible = true;
    if (Dj) return 1;
    this.FE();
    this.UO(row);
    if (row.Expanded && this.HasChildren(row)) this.Expand(row, true);
    this.ColorRow(row);
    this.F7(row, true);
    if (!UN) this.SetScrollBars();
    return 1;
};
_7m.prototype.HideRow = function (row, UQ, Dj) {
    if (!row.Visible) return;
    if (row.Fixed) {
        if (row == this.FRow) this.Focus(null, null);
        if (row == this.AT) this.AT = null;
        if (row == this.ARow) this.ARow = null;
        this.UR(row);
        row.Visible = false;
        row.Selected = false;
        if (!Dj) this.SetScrollBars();
        return;
    }
    if (this.FRow) {
        for (var J = this.FRow; J && J != row; J = J.parentNode);
        if (row == J) {
            var J = this.GetNextSibling(row);
            if (J && !J.Visible) J = this.GetNextVisible(J);
            if (!J) J = this.GetPrevVisible(row, 1);
            if (!J) J = this.GetFirstVisible(null, 4);
            if (J && J != row) this.Focus(J, this.FCol);
            else this.Focus(null, null);
        }
    }
    var US = 1;
    if (row == this.AT) this.AT = null;
    if (row == this.ARow) this.ARow = null;
    if (!Dj && this.Paging && this.GetRowPage(row).State < 4) Dj = true;
    if (!Dj) {
        if (row.firstChild) {
            if (row.Expanded) {
                this.Collapse(row);
                row.Expanded = true;
            }
        }
        if (UQ) US = this.El(row);
        else this.UR(row);
    }
    if (this.UT) this.UT(row, 0);
    row.Visible = false;
    var EU = row.parentNode;
    if (EU.Page && !this.HasChildren(EU)) {
        var T = this;
        function UU() {
            row.Visible = true;
            if (B3.State == 0) {
                B3.State = 1;
                T.StartPage(B3, 1);
            }
            setTimeout(function () {
                T.HideRow(row, UQ, Dj);
                if (UQ == 2) T.DelRow(row);
            },
            200);
        };
        var B3 = EU.nextSibling;
        if (B3) {
            if (!this.AllPages) this.GoToPage(B3);
            if (B3.State < 2) {
                UU();
                return - 1;
            }
            var BG = B3.firstChild;
            for (var J = EU.firstChild; J; J = EU.firstChild) B3.insertBefore(J, BG);
        } else {
            B3 = EU.previousSibling;
            if (B3) {
                if (!this.AllPages) this.GoToPage(B3);
                if (B3.State < 2) {
                    UU();
                    return - 1;
                }
                for (var J = EU.firstChild; J; J = EU.firstChild) B3.appendChild(J);
            } else {}
        };
        if (B3) {
            if (!this.AllPages && B3 == EU.nextSibling) this.AP--;
            this.SS(EU);
            if (this.Paging != 3 && this.Sj) this.Sj();
            if (this.UpdatePager) this.UpdatePager();
        }
    } else if (EU.Expanded && !this.HasChildren(EU) && US) {
        if (this.Rz) this.Rz(EU);
    }
    this.F7(row, !Dj);
    if (!Dj) {
        this.SetScrollBars();
        this.FE();
    }
};
function Get(row, UV) {
    var BZ = row[UV];
    if (BZ != null) return BZ;
    return row.Def[UV];
};
function GetLower(row, UV) {
    var BZ = row[UV];
    if (BZ == null) BZ = row.Def[UV];
    if (typeof(BZ) != "string") return BZ;
    return BZ.toLowerCase();
};
function Is(row, UV) {
    var BZ = row[UV];
    if (BZ != null) return BZ - 0;
    return row.Def[UV] - 0;
};
_7m.prototype.GetPos = function (row) {
    for (var J = row.parentNode.firstChild, I = 0; J && J != row; J = J.nextSibling) if (J.Visible) I++;
    return I;
};
_7m.prototype.GetRowPage = function (row) {
    for (var Bg = row; Bg && !Bg.Page; Bg = Bg.parentNode);
    return Bg;
};
_7m.prototype.GetPagePos = function (row) {
    var Bg = this.GetRowPage(row);
    for (var J = this.GetFirstVisible(Bg, 1), I = 0; J && J != row; J = this.GetNextVisible(J, 3), I++);
    return I;
};
_7m.prototype.PagePosToRow = function (row, I) {
    if (row.State < 2) return null;
    if (!I) I = 0;
    for (var J = this.GetFirstVisible(row, 1); J && I; J = this.GetNextVisible(J, 3), I--);
    return J ? J: this.GetLastVisible(row, 1);
};
_7m.prototype.HasChildren = function (row) {
    if (row.Count) return 1;
    var B3 = row.firstChild;
    while (B3) {
        if (B3.Visible) return 1;
        B3 = B3.nextSibling;
    }
    return 0;
};
_7m.prototype.Eq = function (row) {
    if (this.ChildPaging <= 1) return row.firstChild != null;
    if (row.Count) return 1;
    return row.firstChild != null;
};
_7m.prototype.UK = function (row) {
    if (row.State < 2) return row.Count;
    var B3 = row.firstChild,
    Ka = row.Level;
    if (!B3) return 0;
    var BQ = B3.Visible ? 0 : -1;
    do {
        B3 = this.GetNextVisible(B3, 1);
        BQ++;
    } while (B3 && B3.Level > Ka);
    return BQ;
};
_7m.prototype.UW = function (row, count) {
    if (row.State < 2) return row.Count >= count;
    var B3 = row.firstChild,
    Ka = row.Level;
    if (!B3) return 0;
    if (B3.Visible) count--;
    do {
        if (! (count--)) return 1;
        B3 = this.GetNextVisible(B3, 1);
    } while (B3 && B3.Level > Ka);
    return 0;
};
_7m.prototype.Ge = function (row, BJ, C5, Bj) {
    if (!row || !BJ) return Bj;
    var a = row[BJ + C5];
    if (a != null) return a;
    if (row.Def) {
        a = row.Def[BJ + C5];
        if (a != null) return a;
    }
    var C = this.Cols[BJ];
    if (C) {
        a = C[C5];
        return a == null ? Bj: a;
    }
    return Bj;
};
_7m.prototype.GetType = function (row, BJ) {
    if (row.Kind == "User") return "Html";
    var Cs = Get(row, BJ + "Type");
    if (Cs == null) {
        var BY = this.Cols[BJ];
        if (BY) Cs = BY.Type;
    }
    if (Grids.OnGetType) Cs = Grids.OnGetType(this, row, BJ, Cs);
    return Cs;
};
_7m.prototype.GetFormat = function (row, BJ, Tb) {
    var B = Tb ? "EditFormat": "Format";
    var BG = Get(row, BJ + B);
    if (BG == null) BG = this.Cols[BJ][B];
    if (Grids.OnGetFormat) BG = Grids.OnGetFormat(this, row, BJ, BG, Tb);
    return BG;
};
_7m.prototype.UX = function (UY) {
    if (!UY || typeof(UY) == "object") return UY;
    var a = this.An[UY];
    if (a) return a;
    a = UY.split(',');
    this.An[UY] = a;
    return a;
};
_7m.prototype.UZ = function (UY) {
    if (!UY || typeof(UY) == "object") return UY;
    var a = this.Ao[UY];
    if (a) return a;
    a = UY.split(UY.charAt(0));
    this.Ao[UY] = a;
    return a;
};
_7m.prototype.Ua = function (UY) {
    if (!UY || typeof(UY) == "object") return UY;
    var a = this.An[UY];
    if (a) return a;
    a = UY.slice(1).split(UY.charAt(0));
    this.An[UY] = a;
    return a;
};
_7m.prototype.GetEnum = function (row, BJ, Tb) {
    var BG, J = Get(row, BJ + "Related");
    if (J == null) J = this.Cols[BJ]["Related"];
    if (J) {
        J = J.split(',');
        var B3 = "",
        x;
        for (var i = 0; i < J.length; i++) {
            if (B3) B3 += "_";
            x = Get(row, J[i]);
            if (!x && x != 0) {
                if (this.EmptyEnum != null) x = "_";
                else x = 0;
            }
            B3 += x;
        }
        if (Tb) {
            BG = Get(row, BJ + "EditEnum" + B3);
            if (!BG) BG = this.Cols[BJ]["EditEnum" + B3];
            if (!BG) {
                BG = Get(row, BJ + "EditEnum");
                if (!BG) BG = this.Cols[BJ]["EditEnum"];
            }
        }
        if (!BG) {
            BG = Get(row, BJ + "Enum" + B3);
            if (!BG) BG = this.Cols[BJ]["Enum" + B3];
        }
        if (!BG) {
            BG = Get(row, BJ + "Enum");
            if (!BG) BG = this.Cols[BJ]["Enum"];
        }
    } else {
        if (Tb) {
            BG = Get(row, BJ + "EditEnum");
            if (!BG) BG = this.Cols[BJ]["EditEnum"];
        }
        if (!BG) {
            BG = Get(row, BJ + "Enum");
            if (!BG) BG = this.Cols[BJ]["Enum"];
        }
    };
    if (BG) BG = this.Ua(BG);
    if (Grids.OnGetEnum) BG = Grids.OnGetEnum(this, row, BJ, BG);
    return BG;
};
_7m.prototype.GT = function (row, BJ) {
    var BG = Get(row, BJ + "EnumKeys");
    if (BG == null && this.Cols[BJ]) BG = this.Cols[BJ].EnumKeys;
    if (Grids.OnGetEnumKeys) BG = Grids.OnGetEnumKeys(this, row, BJ, BG);
    return BG ? this.UZ(BG) : BG;
};
_7m.prototype.GetEnumIndex = function (row, BJ, BR) {
    if (this.EnumKeys) {
        var GS = this.GT(row, BJ);
        if (GS) {
            if (BR === 0) BR = "0";
            if (BR) for (var k = 0; k < GS.length; k++) if (GS[k] == BR) return k - 1;
        }
    }
    if (BR && BR - 0 != BR) {
        GS = this.GetEnum(row, BJ);
        if (GS) for (var k = 0; k < GS.length; k++) if (BR == GS[k]) return k;
    }
    return null;
};
_7m.prototype.CanEdit = function (row, BJ) {
    if (!BJ || !this.Editing && row.Kind != "Filter" && row.Kind != "Group" && row.Kind != "Search" && !row[BJ + "AlwaysEdit"]) return false;
    var Du = Get(row, BJ + "CanEdit");
    if (Du != null) Du = Du - 0;
    else {
        var C = this.Cols[BJ];
        if (!this.CanFocus(row, BJ)) Du = false;
        else if (row.Kind == "Filter") {
            var type = this.GetType(row, BJ);
            Du = type != "Pass" && C.CanFilter > 0;
        } else if (C && C.CanEdit != 1) Du = C.CanEdit;
        else if (Get(row, "CanEdit") != 1) Du = Is(row, "CanEdit") ? 2 : 0;
        else if (row.Calculated) Du = Get(row, BJ + "Formula") == null;
        else Du = !C || C.Formula == null;
    };
    if (Du == 1 && this.Editing == 2 && row.Kind != "Filter") Du = 2;
    if (Grids.OnCanEdit) return Grids.OnCanEdit(this, row, BJ, Du);
    return Du;
};
_7m.prototype.CanFocus = function (row, BJ) {
    if (!BJ) return row.Page ? 1 : Is(row, "CanFocus");
    if (row.Page) return this.Cols[BJ] ? this.Cols[BJ].CanFocus: 1;
    var Dv = Get(row, BJ + "CanFocus");
    if (Dv != null) return Dv - 0;
    return Is(row, "CanFocus") && (!this.Cols[BJ] || this.Cols[BJ].CanFocus);
};
_7m.prototype.I2 = function (row, BJ) {
    var Ub = Get(row, BJ + "AcceptEnters");
    if (Ub != null) return Ub;
    return this.AcceptEnters;
};
_7m.prototype.GetRowHeight = function (row) {
    if (this.VarHeight) {
        var J = this.GetRow(row, 1);
        if (J) return J.offsetHeight;
    }
    var CZ = row.Height;
    if (CZ != null) return CZ;
    if (row.Page) return this.SQ(row);
    CZ = row.Def.Height;
    if (CZ != null) return CZ;
    return this.RowHeight;
};
_7m.prototype.GetCDef = function (row) {
    if (row.Page) return Get(this.XHeader, "CDef");
    else return Get(row, "CDef");
};
_7m.prototype.Ia = function (row, BJ) {
    var SI = Get(row, BJ + "WidthPad");
    if (SI != null) return SI - 0;
    var C = this.Cols[BJ];
    return C ? C.WidthPad: 0;
};
_7m.prototype.FI = function (row, BJ, Tb) {
    var IY = Tb ? "EditMask": "ResultMask";
    var FH = Get(row, BJ + IY);
    if (FH != null) return FH;
    return this.Cols[BJ][IY];
};
_7m.prototype.Jy = function (row, BJ) {
    var Cs = this.GetType(row, BJ);
    if (Cs == "Lines" || Cs == "Html" || Cs == "List") return 1;
    if (Cs == "Radio") {
        var BG = this.GetFormat(row, BJ);
        return BG && ((BG.split(BG.charAt(0))[1] - 0) & 3) > 0;
    }
    if (Cs != "Img" && Cs != "Link") return 0;
    var BG = this.GetFormat(row, BJ, 1);
    return BG && BG.charAt(1) != '0';
};
_7m.prototype.JG = function (row, BJ) {
    var Ca = this.GetType(row, BJ);
    if (Ca != "Img" && Ca != "Link") return null;
    var BZ = this.GetValue(row, BJ);
    if (!BZ) return null;
    BZ = BZ.split(BZ.charAt(0));
    if (Ca == "Img") {
        BZ[1] = BZ[6];
        BZ[3] = BZ[7];
    }
    if (!BZ[1]) return null;
    var BG = this.GetFormat(row, BJ);
    if (BG) {
        BG = BG.split(BG.charAt(0));
        if (Ca == "Img") {
            BG[1] = BG[5];
            BG[2] = BG[6];
        }
        BZ[1] = (BG[1] ? BG[1] : "") + BZ[1] + (BG[2] ? BG[2] : "");
    }
    return [BZ[1], BZ[3]];
};
_7m.prototype.B4 = function (DW, cells, BH) {
    var BG = this.Am[DW + (cells ? 1 : 0)];
    if (BG) return BG;
    BG = this.BF(DW, cells, BH);
    this.Am[DW + (cells ? 1 : 0)] = BG;
    return BG;
};
_7m.prototype.GR = function (row, BJ) {
    var J = row[BJ + "Range"];
    if (J != null) return J - 0;
    J = row.Def[BJ + "Range"];
    if (J != null) return J - 0;
    J = this.Cols[BJ];
    return J ? J["Range"] : 0;
};
_7m.prototype.Ef = function (row, Bx, C) {
    if (!this.MainCol || row.Kind == "User") return;
    var EU = row.parentNode,
    TN = EU.LevelImg;
    for (var J = row.nextSibling; J; J = J.nextSibling) if (J.Visible) {
        row.LevelImg = TN + "1";
        break;
    }
    if (!J && this.Paging && row.parentNode.parentNode == this.XB) {
        J = this.GetNextSiblingVisible(row);
        if (!J && row.parentNode.nextSibling && row.parentNode.nextSibling.Count) J = 1;
        if (J) row.LevelImg = TN + "1";
    }
    if (!J) row.LevelImg = TN + "0";
    row.Level = EU.Level + 1;
    if (Bx) {
        if (!C) C = this.Cols[this.MainCol];
        var J = row["r" + C.K],
        BY,
        CZ;
        if (J && J.firstChild && (!row.Spanned || row[this.MainCol + "Span"] != 0) && C.Visible) {
            BY = J.firstChild.rows[0].cells[C.Dl];
            if (this.VarHeight) {
                CZ = BY.firstChild.offsetHeight;
                if (!CZ) CZ = row.TL;
            }
            BY.innerHTML = this.Il(row, this.MainCol, null, null, CZ);
            if (Grids.OnRenderRow) Grids.OnRenderRow(this, row, this.MainCol);
        }
    }
};
_7m.prototype.Sd = function (row, Bx, C) {
    this.Ef(row, Bx, C);
    for (var J = row.firstChild; J; J = J.nextSibling) this.Sd(J, Bx, C);
};
_7m.prototype.Po = function (Bx, all) {
    if (!this.MainCol) return;
    for (var J = this.XB.firstChild; J; J = J.nextSibling) {
        J.LevelImg = "";
        J.Level = -1;
    }
    var C = this.Cols[this.MainCol];
    if (all) for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
        if (!J.Filtered) this.Ef(J, Bx, C);
        else J.Level = J.parentNode.Level + 1;
    } else for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) this.Ef(J, Bx, C);
};
_7m.prototype.Es = function (row, TK) {
    if (this.e == null || !row || !this.MainCol || row.Fixed || row.Kind == "User") return;
    for (var H = row; H && H != this.XB; H = H.parentNode);
    if (!H) return;
    var IU = this.GetCell(row, this.MainCol);
    if (IU) {
        IU.innerHTML = this.Il(row, this.MainCol, null, TK, this.VarHeight ? IU.firstChild.offsetHeight: null);
        if (Grids.OnRenderRow) Grids.OnRenderRow(this, row, this.MainCol);
    }
    if (this.VarHeight) this.Uc(row);
};
_7m.prototype.F7 = function (row, Bx) {
    if (this.e == null || !this.MainCol || row.Fixed) return;
    if (row.LevelImg.charAt(row.LevelImg.length - 1) == "1") return;
    for (var J = row.previousSibling; J && !J.Visible; J = J.previousSibling);
    if (!J) {
        var EU = row.parentNode;
        if (Bx && !EU.Page) this.Es(EU);
    } else {
        var Ka = row.Level,
        C = this.Cols[this.MainCol];
        while (J && J.Level >= Ka) {
            this.Ef(J, Bx, C);
            J = this.GetNextVisible(J);
        }
    };
};
_7m.prototype.CopyRow = function (row, EU, GN, G, Ud, EV) {
    if (!G) G = this;
    var J = G.AddRow(EU, GN, false, true, row.Def.Name, null, Ud);
    if (!J) return;
    function Copy(row, J, G) {
        G.Gs({
            Type: "Copy",
            Row: J,
            G7: row,
            Par: EU,
            Next: GN,
            Grid: G,
            G8: Ud,
            G9: EV
        });
        if (!EV || row.Dx) for (var i in row) if (!Grids.A8[i] && i != "DetailRow" && i != "DetailGrid" && i != "MasterRow" && i != "Def" && row[i] != null) J[i] = row[i];
        G.F4(J);
        J.Selected = 0;
        J.Visible = 0;
        G.Ef(J);
        J.r0 = null;
        J.r1 = null;
        J.r2 = null;
        J.Count = null;
        J._7Ax = null;
        if (Grids.OnRowAdd) Grids.OnRowAdd(G, J);
        if (Grids.OnRowCopy) Grids.OnRowCopy(G, J, row, EV);
        if (Ud && row.Count) {
            J.State = 0;
            J.Count = row.Count;
            J.Copy = row.Copy ? row.Copy: row.id;
        }
    };
    Copy(row, J, G);
    if (row.MasterRow && J.MasterRow) Copy(row.MasterRow, J.MasterRow, G.E2);
    if (row.DetailRow && J.DetailRow) Copy(row.DetailRow, J.DetailRow, row.DetailGrid);
    if (Ud && (row.State == null || row.State >= 2)) {
        G.ShowRow(J, null, 1);
        for (var B3 = row.firstChild; B3; B3 = B3.nextSibling) {
            if (B3.Visible) {
                var x = G.CopyRow(B3, J, null, G, true, EV);
                G.ShowRow(x, null, 1);
                if (!B3.Expanded) {
                    x.Expanded = 1;
                    G.Collapse(x);
                    x.Expanded = 0;
                }
                if (B3.MasterRow && x.MasterRow) {
                    G.E2.ShowRow(x.MasterRow, null, 1);
                    if (!B3.MasterRow.Expanded) {
                        x.MasterRow.Expanded = 1;
                        G.E2.Collapse(x.MasterRow);
                        x.MasterRow.Expanded = 0;
                    }
                }
                if (B3.DetailRow && x.DetailRow) {
                    B3.DetailGrid.ShowRow(x.DetailRow, null, 1);
                    if (!B3.DetailRow.Expanded) {
                        x.DetailRow.Expanded = 1;
                        B3.DetailGrid.Collapse(x.DetailRow);
                        x.DetailRow.Expanded = 0;
                    }
                }
            }
        }
    }
    G.Recalculate(J, null, true);
    if (row.MasterRow && J.MasterRow) G.E2.Recalculate(J.MasterRow, null, true);
    if (row.DetailRow && J.DetailRow) row.DetailGrid.Recalculate(J.DetailRow, null, true);
    return J;
};
_7m.prototype.Ue = function (row, EU, GN) {
    if (!EU && GN) EU = GN.parentNode;
    if (!this.Copying || !this.Adding || EU && (EU.Deleted || EU.Fixed)) return false;
    if (!this.CopyFree && this.MainCol) {
        var Def = Get(row, "Def").Name;
        if (this.GetCDef(EU) != Def) return false;
    }
    if (Grids.OnCanRowAdd && !Grids.OnCanRowAdd(this, EU, GN)) return false;
    if (!this.AddRow(EU, GN, null, null, row.Def.Name, null, null, 1)) return false;
    return true;
};
_7m.prototype.CopyRows = function (rows, EU, GN, G, Ud, EV) {
    if (!G) G = this;
    if (!G.Adding || !rows || !rows.length) return false;
    var Bg = EU ? EU: (GN ? GN.parentNode: null);
    if (Bg && !Bg.Page) {
        if (!Bg.Expanded) G.Expand(Bg);
        if (Bg.State < 4) {
            setTimeout(function () {
                G.CopyRows(rows, EU, GN, G, Ud, EV);
            },
            100);
            return true;
        }
    }
    if (Grids.OnCanRowAdd) if (!Grids.OnCanRowAdd(G, EU, GN)) return false;
    var H = new Array(),
    Bc = rows.length;
    this.Gm();
    for (var i = 0; i < Bc; i++) H[i] = G.CopyRow(rows[i], EU, GN, G, Ud, EV);
    this.Gp();
    for (var i = 0; i < Bc; i++) {
        if (H[i]) {
            var row = H[i],
            NG = null,
            Uf;
            if (row.MasterRow) {
                NG = row.MasterRow;
                Uf = G.E2;
                Uf.ShowRow(NG);
            }
            if (row.DetailRow) {
                NG = row.DetailRow;
                Uf = row.DetailGrid;
                if (!NG.Page) Uf.ShowRow(NG);
            }
            G.ShowRow(H[i]);
            if (!rows[i].Expanded && H[i]._7Ax) {
                H[i].Expanded = 1;
                G.Collapse(H[i]);
                H[i].Expanded = 0;
                if (NG) {
                    NG.Expanded = 1;
                    Uf.Collapse(NG);
                    NG.Expanded = 0;
                }
            }
        }
    }
    G.UploadChanges();
    return true;
};
_7m.prototype.Ug = function (CE, Ud, EV) {
    if (!this.Copying) return false;
    var row = CE ? this.FRow: this.ARow;
    if (row == null || row.Fixed || row.Page) return false;
    var Cu = this.CopyRows([row], row.parentNode, row, this, Ud, EV);
    if (!Cu) return false;
    if (row == this.ARow) this.ARow = row.previousSibling;
    this.Focus(this.ARow, this.FCol);
    return true;
};
_7m.prototype.ActionCopyRow = function (CE) {
    return this.Ug(CE, 0, 0);
};
_7m.prototype.ActionCopyTree = function (CE) {
    return this.Ug(CE, 1, 0);
};
_7m.prototype.ActionCopyEmpty = function (CE) {
    return this.Ug(CE, 1, 1);
};
_7m.prototype.Uh = function (CE, Ud, EV, test) {
    if (!this.Copying) return false;
    var GN = CE ? this.FRow: this.ARow;
    if (GN == null || GN.Fixed || GN.Page) return false;
    var GO = GN.previousSibling,
    EU = GN.parentNode;
    var BL = new Array(),
    Bg = 0;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) if (J.Selected) {
        if (!this.Ue(J, null, GN)) continue;
        BL[Bg++] = J;
        if (test && (J.firstChild || J.Count)) test++;
    }
    if (!Bg) return false;
    if (test) return test;
    var Cu = this.CopyRows(BL, EU, GN, this, Ud, EV);
    if (!Cu) return false;
    if (GN == this.ARow) this.ARow = GO ? GO.previousSibling: EU.firstChild;
    this.Focus(this.ARow, this.FCol);
    return true;
};
_7m.prototype.Ui = function (CE, Ud, EV, test, Iy) {
    if (!this.Copying) return false;
    var EU = CE ? this.FRow: this.ARow;
    if (EU == null || EU.Fixed || EU.Page) return false;
    var GN = Iy ? null: EU.firstChild;
    var GO = Iy ? EU.lastChild: null;
    var BL = new Array(),
    Bg = 0;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) if (J.Selected) {
        if (!this.Ue(J, EU, GN)) continue;
        BL[Bg++] = J;
        if (test && J.firstChild) test++;
    }
    if (!Bg) return false;
    if (test) return test;
    var Cu = this.CopyRows(BL, EU, GN, this, Ud, EV);
    if (!Cu) return false;
    if (EU == this.ARow) this.ARow = GO ? GO.nextSibling: EU.firstChild;
    this.Focus(this.ARow, this.FCol);
    return true;
};
_7m.prototype.Uj = function (Ud, EV, test) {
    if (!this.Copying) return false;
    var EU;
    if (this.AddRowEnd && !test) {
        EU = this.XB.lastChild;
        if (EU.State != 4) {
            this.ScrollIntoView(EU);
            var T = this;
            setTimeout(function () {
                T.Uj(Ud, EV, test);
            },
            100);
            return true;
        }
    } else EU = this.UE();
    if (!EU) return false;
    var GO = EU.lastChild;
    var BL = new Array(),
    Bg = 0;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) if (J.Selected) {
        if (!this.Ue(J, EU, null)) continue;
        BL[Bg++] = J;
        if (test && J.firstChild) test++;
    }
    if (!Bg) return false;
    if (test) return test;
    var Cu = this.CopyRows(BL, EU, null, this, Ud, EV);
    if (!Cu) return false;
    this.Focus(GO.nextSibling, this.FCol);
    return true;
};
_7m.prototype.ActionCopySelected = function (CE) {
    return this.Uh(CE, 0, 0);
};
_7m.prototype.ActionCopySelectedTree = function (CE) {
    return this.Uh(CE, 1, 0);
};
_7m.prototype.ActionCopySelectedEmpty = function (CE) {
    return this.Uh(CE, 1, 1);
};
_7m.prototype.ActionCopySelectedChild = function (CE) {
    return this.Ui(CE, 0, 0);
};
_7m.prototype.ActionCopySelectedTreeChild = function (CE) {
    return this.Ui(CE, 1, 0);
};
_7m.prototype.ActionCopySelectedEmptyChild = function (CE) {
    return this.Ui(CE, 1, 1);
};
_7m.prototype.ActionCopySelectedChildEnd = function (CE) {
    return this.Ui(CE, 0, 0, null, 1);
};
_7m.prototype.ActionCopySelectedTreeChildEnd = function (CE) {
    return this.Ui(CE, 1, 0, null, 1);
};
_7m.prototype.ActionCopySelectedEmptyChildEnd = function (CE) {
    return this.Ui(CE, 1, 1, null, 1);
};
_7m.prototype.ActionCopySelectedEnd = function () {
    return this.Uj(0, 0);
};
_7m.prototype.ActionCopySelectedTreeEnd = function () {
    return this.Uj(1, 0);
};
_7m.prototype.ActionCopySelectedEmptyEnd = function () {
    return this.Uj(1, 1);
};
_7m.prototype.Uk = function (CE, EU, Ul, Iy) {
    if (this.Hx == this.ARow && this.Hy == this.ACol) return true;
    var Name = new Array(),
    D7 = new Array(),
    Bg = 0;
    var row = CE ? this.FRow: this.ARow,
    T = this,
    DV = this.Cols.Panel ? this.Cols.Panel.Copy: 7;
    if (!DV && this.ARow && this.ARow.Space != null) DV = this.Toolbar.Copy;
    function Add(name, action) {
        var text = T.GetText(name);
        if (text) {
            Name[Bg] = text;
            D7[Bg++] = action;
        }
    };
    if (EU && row && !row.Fixed && !row.Page) {
        if (this.Ue(row, null, row)) {
            if (DV & 1) Add("CopyRow", this.ActionCopyRow);
            if (row.firstChild || row.Count || !this.GetText("CopyRow")) {
                if (DV & 2) Add("CopyTree", this.ActionCopyTree);
                if (DV & 4) Add("CopyEmpty", this.ActionCopyEmpty);
            }
        }
    }
    if (EU) {
        var test = this.Uh(CE, null, null, 1);
        if (test) {
            if (DV & 1) Add("CopySelected", this.ActionCopySelected);
            if (test > 1 || !this.GetText("CopySelected")) {
                if (DV & 2) Add("CopySelectedTree", this.ActionCopySelectedTree);
                if (DV & 4) Add("CopySelectedEmpty", this.ActionCopySelectedEmpty);
            }
        }
    }
    if (Ul) {
        var test = this.Ui(CE, null, null, 1);
        if (test) {
            if (DV & 1) Add("CopySelectedChild", this.ActionCopySelectedChild);
            if (test > 1 || !this.GetText("CopySelectedChild")) {
                if (DV & 2) Add("CopySelectedTreeChild", this.ActionCopySelectedTreeChild);
                if (DV & 4) Add("CopySelectedEmptyChild", this.ActionCopySelectedEmptyChild);
            }
            if (Iy && (row.firstChild || !this.GetText("CopySelectedChild"))) {
                if (DV & 1) Add("CopySelectedChildEnd", this.ActionCopySelectedChildEnd);
                if (test > 1 || !this.GetText("CopySelectedChildEnd")) {
                    if (DV & 2) Add("CopySelectedTreeChildEnd", this.ActionCopySelectedTreeChildEnd);
                    if (DV & 4) Add("CopySelectedEmptyChildEnd", this.ActionCopySelectedEmptyChildEnd);
                }
            }
        }
    }
    if (EU && row != this.ARow && Iy) {
        var test = this.Uj(null, null, 1);
        if (test) {
            if (DV & 1) Add("CopySelectedEnd", this.ActionCopySelectedEnd);
            if (test > 1 || !this.GetText("CopySelectedEnd")) {
                if (DV & 2) Add("CopySelectedTreeEnd", this.ActionCopySelectedTreeEnd);
                if (DV & 4) Add("CopySelectedEmptyEnd", this.ActionCopySelectedEmptyEnd);
            }
        }
    }
    if (EU && row && !row.Fixed && this.AddRow(null, row, null, null, null, null, null, 1)) {
        Add("AddRow", this.ActionAddRow);
        Add("AddRowBelow", this.ActionAddRowBelow);
    }
    if (EU && Iy && row != this.ARow && this.AddRow(this.UE(), null, null, null, null, null, null, 1)) Add("AddRowEnd", this.ActionAddRowEnd);
    if (this.MainCol && Ul && row && !row.Fixed && this.AddRow(row, null, null, null, null, null, null, 1)) {
        Add("AddRowChild", this.ActionAddChild);
        if ((row.firstChild || !this.GetText("AddRowChild")) && Iy) Add("AddRowChildEnd", this.ActionAddChildEnd);
    }
    if (!Bg) return false;
    if (Bg == 1) return D7[0] ? D7[0].apply(this) : false;
    if (!this.ARow) return false;
    var T = this;
    if (row != this.ARow) this.ScrollIntoView(row);
    var BJ = this.ACol;
    this.ShowMenu(this.ARow, this.ACol, Name, null, function (Ce) {
        var Um = T.ARow,
        Un = T.ACol;
        T.ARow = row;
        T.ACol = BJ;
        if (D7[Ce]) D7[Ce].apply(T, [CE]);
        T.ARow = Um;
        T.ACol = Un;
    },
    1);
    return true;
};
_7m.prototype.ActionCopyMenu = function (CE) {
    return this.Uk(CE, 1, 1);
};
_7m.prototype.ActionCopyRowMenu = function (CE) {
    return this.Uk(CE, 1, 0);
};
_7m.prototype.ActionCopyChildMenu = function (CE) {
    return this.Uk(CE, 0, 1);
};
_7m.prototype.ActionCopyMenuEnd = function (CE) {
    return this.Uk(CE, 1, 1, 1);
};
_7m.prototype.ActionCopyRowMenuEnd = function (CE) {
    return this.Uk(CE, 1, 0, 1);
};
_7m.prototype.ActionCopyChildMenuEnd = function (CE) {
    return this.Uk(CE, 0, 1, 1);
};
_7m.prototype.Uo = function (row, UQ, Dj, Gv) {
    if (!Gv) this.Gm();
    for (var J = row.firstChild; J; J = J.nextSibling) this.Uo(J, UQ, Dj, 1);
    if (Gv && (UQ && row.Deleted || !UQ && row.Deleted != 2)) return;
    if (Gv && UQ) UQ = 2;
    this.Gq({
        Type: "Delete",
        Row: row,
        HA: UQ,
        Deleted: row.Deleted,
        Selected: row.Selected,
        Expanded: row.Expanded
    });
    row.Deleted = UQ;
    if (UQ && Grids.OnRowDelete) Grids.OnRowDelete(this, row, UQ);
    if (!UQ && Grids.OnRowUndelete) Grids.OnRowUndelete(this, row);
    if (!Dj) this.ColorRow(row);
    if (!Gv) this.Gp();
};
_7m.prototype.DeleteRow = function (row, type, Up) {
    if (!row || !this.Deleting || !Is(row, "CanDelete")) return false;
    var UQ = row.Deleted - 0;
    if (!type) {
        if (this.ShowDeleted && !this.AutoUpdate) {
            if (UQ) type = 3;
            else type = 2;
        } else type = 1;
    }
    if (type == 3) {
        if (row.parentNode.Deleted - 0) return false;
    }
    if (Grids.OnCanRowDelete) type = Grids.OnCanRowDelete(this, row, type);
    var BJ = this.MainCol;
    if (!BJ && this.AX) BJ = this.SortCols[0];
    if (type == 1) type = confirm(this.GetAlert("ConfirmDel") + " " + this.GetAlert("Row2") + " " + (BJ ? "'" + this.GetString(row, BJ, 1) + "'": row.id) + " ?") ? 2 : 0;
    if (type <= 1) return false;
    if (type == 2 && UQ || type == 3 && !UQ) return false;
    this.DeleteRowT(row, type, null, Up);
    if (this.H3 && !this.ShowDeleted) this.RefreshGanttDependencies();
    return true;
};
_7m.prototype.DeleteRowT = function (row, type, Dj, Up, UD, Ga) {
    if (this.ShowDeleted) {
        this.Uo(row, type == 2);
    } else {
        if (type == 2) {
            this.Uo(row, 1, Dj);
            if (this.HasChildren(row)) {
                this.Collapse(row);
                if (this.ChildPaging && row.State > 2) row.State = 2;
            }
            row.Selected = 0;
            if (row.parentNode.Count) row.parentNode.Count--;
            if (row.Page && Ga) {
                for (var J = row.firstChild; J; J = J.nextSibling) this.HideRow(J, true, Dj);
            } else this.HideRow(row, true, Dj);
            if (row.Def.Group) {
                function Uq(row) {
                    for (var J = row.firstChild; J; J = J.nextSibling) {
                        if (J.Def.Group) Uq(J);
                        else J.Visible = 0;
                    }
                };
                Uq(row);
            }
        } else if (type == 3) {
            this.Uo(row, 0, Dj);
            row.Deleted = 0;
            if (row.Page && Ga && this.ShowDetail) {;
                this.E2.ShowDetail(row.MasterRow, this.id);
            } else this.ShowRow(row, Dj);
        }
    };
    if (!Ga) {
        if (row.MasterRow) {
            this.E2.DeleteRowT(row.MasterRow, type, Dj, Up, 1, 1);
        }
        if (row.DetailRow) {
            row.DetailGrid.DeleteRowT(row.DetailRow, type, Dj, Up, 1, 1);
        }
    }
    if (!Dj) this.Recalculate(row, null, !Dj);
    if (Up) {
        this.DelRow(row);
        if (!Dj) {
            this.HideMessage();
            if (!this.GetFirstVisible() && this.Paging != 3) this.UpdatePager();
            this.SetScrollBars();
        }
    } else if (!Dj) {
        if (!UD) {
            this.UploadChanges(row);
            if (this.SaveValues) this.SaveCfg();
        }
        this.HideMessage();
        if (!this.ShowDeleted) {
            if (!this.GetFirstVisible() && this.Paging != 3) this.UpdatePager();
            this.SetScrollBars();
        }
    }
    status = "";
};
_7m.prototype.SC = function () {
    var BQ = 0,
    Dj = false;
    this.ShowDeleted = !this.ShowDeleted;
    this.Rendering = true;
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
        if (J.Deleted) {
            BQ++;
            if (BQ > 30) {
                Dj = true;
                break;
            }
        }
    }
    if (this.ShowDeleted) {
        for (var J = this.GetFirst(null, 1); J; J = this.GetNext(J, 1)) if (J.Deleted && !J.Filtered) this.ShowRow(J, Dj);
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (J.Deleted && !J.Filtered && !J.Visible) {
            if (!Dj) {
                var Bg = J.parentNode;
                if (Bg.r1 && !(Bg.State < 4) && this.HasChildren(Bg)) {
                    var B3 = this.GetNextVisible(Bg);
                    if (B3 && B3.parentNode == Bg && B3.r1) {
                        J.Visible = true;
                        this.UO(J);
                        this.F7(J, true);
                    }
                }
                if (Bg.r1) {
                    J.Visible = true;
                    this.Es(Bg);
                }
            }
            J.Visible = true;
        }
        var CE = this.GetFixedRows();
        for (var J = 0; J < J.length; J++) if (J.Deleted) this.ShowRow(J);
        if (Dj && this.Paging > 0 && this.Paging < 3) this.CreatePages();
        if (Dj && this.MainCol) this.Po();
    } else {
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (J.Deleted && !J.Filtered) this.HideRow(J, true, Dj);
        var CE = this.GetFixedRows();
        for (var J = 0; J < J.length; J++) if (J.Deleted && !J.Filtered) this.HideRow(J, true);
    };
    this.Rendering = false;
    if (this.H3) this.RefreshGanttDependencies();
    if (!Dj) this.SetScrollBars();
    return Dj;
};
_7m.prototype.ActionDeleteRow = function (CE) {
    return this.DeleteRow(CE ? this.FRow: this.ARow, this.ShowDeleted && !this.AutoUpdate ? 2 : 1);
};
_7m.prototype.ActionUndeleteRow = function (CE) {
    return this.DeleteRow(CE ? this.FRow: this.ARow, 3);
};
_7m.prototype.ActionRemoveRow = function (CE) {
    return this.DeleteRow(CE ? this.FRow: this.ARow, 1, 1);
};
_7m.prototype.Ur = function (UQ, Up) {
    if (!this.Deleting) return false;
    var BL = this.Us();
    if (BL.length == 0) return false;
    var BQ = 0;
    for (var i = 0; i < BL.length; i++) if (BL[i].Deleted) BQ++;
    if (UQ == null) UQ = !BQ;
    else if (!BQ && !UQ || BQ == BL.length && UQ) return false;
    if ((!this.ShowDeleted || this.AutoUpdate) && UQ) {
        var Ut = this.GetAlert("ConfirmDel"),
        Uu = this.GetAlert("SelRows");
        if (Ut && Uu && !confirm(Ut + " " + Uu + " (" + BL.length + ") ?")) return false;
    }
    var type = UQ ? 2 : 3;
    status = this.GetText("DelSelected");
    if (this.RowCount < this.SynchroCount) this.Uv(BL, type, Up);
    else {
        this.ShowMessage(this.GetText("DelSelected"));
        var T = this;
        setTimeout(function () {
            T.Uv(BL, type, Up);
        },
        10);
    };
    return true;
};
_7m.prototype.Uv = function (BL, type, Up) {
    var BN = BL.length;
    for (var i = 0; i < BL.length; i++) {
        var Cs = type;
        if (Grids.OnCanRowDelete) Cs = Grids.OnCanRowDelete(this, BL[i], type);
        if (Cs == 2 && BL[i].Deleted || Cs == 3 && !BL[i].Deleted) {
            BN--;
            BL[i] = null;
        }
    }
    var Dj = BN > 30;
    var UD = BN > 1 && this.AutoUpdate;
    this.Gm();
    for (var i = 0; i < BL.length; i++) {
        if (BL[i]) {
            var UQ = BL[i].Deleted;
            if (!UQ || type == 3) this.DeleteRowT(BL[i], UQ ? 3 : 2, BL[i].Fixed ? false: Dj, Up, UD);
        }
    }
    this.Gp();
    if (Dj || UD) this.UploadChanges();
    if (this.H3 && !this.ShowDeleted) this.RefreshGanttDependencies();
    if (Dj) {
        this.Calculate(2, true);
        if (this.Paging) this.Ku();
        else {
            this.FE();
            this.UpdateBody();
            this.HideMessage();
            this.SetScrollBars();
        }
    } else this.HideMessage();
    if (this.SaveValues) this.SaveCfg();
    status = "";
};
_7m.prototype.ActionDeleteSelected = function () {
    if (!this.XHeader.CanDelete || Grids.OnDeleteAll && Grids.OnDeleteAll(this, 1)) return false;
    return this.Ur(true);
};
_7m.prototype.ActionUndeleteSelected = function () {
    if (!this.XHeader.CanDelete || Grids.OnDeleteAll && Grids.OnDeleteAll(this, 0)) return false;
    return this.Ur(false);
};
_7m.prototype.ActionRemoveSelected = function () {
    if (!this.XHeader.CanDelete || Grids.OnDeleteAll && Grids.OnDeleteAll(this, 2)) return false;
    return this.Ur(true, true);
};
_7m.prototype.GenerateId = function (row, Uw) {
    var id = this.LastId;
    if (id != null) id += "";
    var D = this.IdNames,
    Av = this.Av,
    C = this.IdChars,
    Ux = this.Uy;
    var BR, Uz, J, Bc, U0 = D.length - 1,
    U1 = this.AppendId,
    GJ = 0,
    U2 = this.IdCompare & 4 ? 1 : 0;
    var U3 = id.length,
    U4 = this.IdPrefix,
    U5 = this.U6;
    if (U1) {
        BR = Uw ? Uw: Get(row, this.Av);
        Uz = BR == null ? "": (BR + "").replace(/\$/g, "_").replace(this.U7, "\\$&");
    }
    var R = new RegExp("^" + (Uz ? Uz: "") + this.U8, U2 ? "i": "");
    if (U0) {
        var U9 = new Array();
        for (var i = 0; i < U0; i++) {
            if (D[i] == "Def") x = row.Def.Name;
            else x = Get(row, D[i]);
            if (x == null) x = "";
            x = (x + "").replace(/\$/g, "_");
            if (U2) x = x.toLowerCase();
            U9[i] = x;
        }
    }
    function VA(J, test, RS) {
        if (J == row) return;
        if (RS) {
            var VB = J.id;
            if (VB == null) return;
            VB = (VB + "").split('$');
            VB = VB[VB.length - 1];
        } else {
            VA(J, test, 1);
            if (U0) {
                for (var i = 0, x; i < U0; i++) {
                    if (D[i] == "Def") x = J.Def.Name;
                    else x = Get(J, D[i]);
                    if (x == null) x = "";
                    else x = (x + "").replace(/\$/g, "_");
                    if (U2) x = x.toLowerCase();
                    if (U9[i] != x) return;
                }
            }
            var VB = Get(J, Av);
            if (VB == null) return;
            VB = (VB + "").replace(/\$/g, "_");
        };
        var M = VB.match(R);
        if (!M) return;
        VB = M[1];
        if (Uw) {
            if (U1) {
                if (!VB) GJ = 1;
            } else if (VB == Uw) GJ = 1;
        }
        if (test) return VB == id;
        var BN = VB.length;
        if (BN == U3) {
            for (var i = 0; i < BN; i++) {
                var VC = Ux[id.charAt(i)];
                var VD = Ux[VB.charAt(i)];
                if (VD != VC) {
                    if (VD > VC) id = VB;
                    break;
                }
            }
        } else if (BN > U3) {
            id = VB;
            U3 = BN;
        }
    };
    if (this.FullId) {
        if (row.parentNode.Page) {
            for (Bc = this.XB.firstChild; Bc; Bc = Bc.nextSibling) {
                for (J = Bc.firstChild; J; J = J.nextSibling) VA(J);
            }
        } else for (J = row.parentNode.firstChild; J; J = J.nextSibling) VA(J);
    } else {
        for (J = this.GetFirst(); J; J = this.GetNext(J)) VA(J);
    };
    var CE = this.GetFixedRows();
    if (Uw && !GJ) {
        if (!this.FullId || row.parentNode.Page) for (J = 0; J < CE.length; J++) VA(CE[J], 1);
        if (!GJ) return Uw;
    }
    while (1) {
        for (var i = U3 - 1; i >= 0; i--) {
            var BY = id.charAt(i);
            var Ce = Ux[BY];
            if (Ce == null || Ce == C.length - 1) {
                id = id.slice(0, i) + C.charAt(0) + id.slice(i + 1);
                continue;
            }
            id = id.slice(0, i) + C.charAt(Ce + 1) + id.slice(i + 1);
            break;
        }
        if (i < 0) {
            id = C.charAt(this.NumberId ? this.NumberId: 0);
            for (i = 0; i < U3; i++) id += C.charAt(0);
        }
        var JE = 1;
        if (!this.FullId || row.parentNode.Page) for (J = 0; J < CE.length; J++) if (VA(CE[J], 1)) {
            JE = 0;
            break;
        }
        if (JE) break;
    }
    id = this.IdPrefix + id + this.IdPostfix;
    if (U1) id = BR + id;
    if (Grids.OnGenerateId) id = Grids.OnGenerateId(this, row, id);
    return id;
};
_7m.prototype.VE = function (row) {
    if (this.Au) {
        var id = "",
        x, i, B3;
        for (i = 0; i < this.Au; i++) {
            B3 = this.IdNames[i];
            if (B3 == "Def") x = row.Def.Name;
            else x = Get(row, B3);
            if (x == null) x = "";
            id += "$" + (x + "").replace(/\$/g, "_");
        }
        id = id.slice(1);
        if (this.FullId) {
            var Nd = row.parentNode.id;
            if (Nd) id = Nd + "$" + id;
        }
        return id;
    }
    return row.id;
};
_7m.prototype.UJ = function (row) {
    var BY = this.Av;
    var BZ = Get(row, BY);
    var B3 = this.GenerateId(row, BZ);
    if (B3 != BZ) {
        row[BY] = B3;
        row[BY + "Changed"] = 1;
        row.Changed = 1;
        if (this.Cols[BY]) this.RefreshCell(row, BY);
    }
};
_7m.prototype.F4 = function (row) {
    var BY = this.Av,
    id = this.GenerateId(row, this.AppendId ? Get(row, BY) : null);
    row[BY] = id;
    if (this.Cols[BY]) {
        row[BY + "Changed"] = 1;
        row.Changed = 1;
    }
    id = this.VE(row);
    if (Grids.OnSetRowId) id = Grids.OnSetRowId(this, row, id);
    row.id = id;
};
_7m.prototype.GetRows = function (K) {
    var J, BL = new Array(),
    Bg = 0;
    for (J = K.firstChild; J; J = J.nextSibling) BL[Bg++] = J;
    return BL;
};
_7m.prototype.GetFixedRows = function () {
    var BL = new Array(),
    Bg = 0,
    J;
    for (J = this.XH.firstChild; J; J = J.nextSibling) BL[Bg++] = J;
    for (J = this.XF.firstChild; J; J = J.nextSibling) BL[Bg++] = J;
    return BL;
};
_7m.prototype.Cc = function () {
    var BL = new Array(),
    Bg = 0,
    J;
    for (J = this.XH.firstChild; J; J = J.nextSibling) if (J.Kind == "Filter") BL[Bg++] = J;
    for (J = this.XF.firstChild; J; J = J.nextSibling) if (J.Kind == "Filter") BL[Bg++] = J;
    return BL;
};
_7m.prototype.GetSelRows = function () {
    var BL = new Array(),
    Bg = 0,
    CE;
    CE = this.GetRows(this.XH);
    for (var i = 0; i < CE.length; i++) if (CE[i].Selected) BL[Bg++] = CE[i];
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) if (J.Selected) BL[Bg++] = J;
    CE = this.GetRows(this.XF);
    for (var i = 0; i < CE.length; i++) if (CE[i].Selected) BL[Bg++] = CE[i];
    return BL;
};
_7m.prototype.GetRowById = function (id) {
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) if (CE[i].id == id) return CE[i];
    if (this.XS) for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.id == id) return J;
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) if (J.id == id) return J;
    return null;
};
_7m.prototype.GetFirst = function (H, type) {
    if (type & 4 && this.XH.firstChild) return this.XH.firstChild;
    if (!H) {
        H = this.XB.firstChild;
        while (H && !H.firstChild) H = H.nextSibling;
    }
    if (!H) return null;
    var row = H.firstChild;
    if (!row && type & 4) return this.XF.firstChild;
    return row;
};
_7m.prototype.GetLast = function (H, type) {
    if (type & 4 && this.XF.lastChild) return this.XF.lastChild;
    if (!H) {
        H = this.XB.lastChild;
        while (H && !H.firstChild) H = H.previousSibling;
    }
    if (!H) return null;
    var row = H.lastChild;
    if (!row && type & 4) return this.XH.lastChild;
    while (row.lastChild && (!(type & 1) || row.Expanded)) row = row.lastChild;
    return row;
};
_7m.prototype.GetNext = function (row, type) {
    var J = row.firstChild;
    if (J && (!(type & 1) || row.Expanded)) return J;
    J = row.nextSibling;
    if (J) return J;
    while (!row.nextSibling && row.parentNode) row = row.parentNode;
    if (row.Page) {
        if (type & 2 || !row.nextSibling) return null;
        row = row.nextSibling;
        while (row && !row.firstChild) row = row.nextSibling;
        return row ? row.firstChild: null;
    }
    return row.nextSibling;
};
_7m.prototype.GetPrev = function (row, type) {
    var J = row.previousSibling;
    if (J) while (J.lastChild && (!(type & 1) || J.Expanded)) J = J.lastChild;
    else {
        J = row.parentNode;
        if (J.Page) {
            if (type & 2) return null;
            J = J.previousSibling;
            while (J && !J.firstChild) J = J.previousSibling;
            if (J) {
                J = J.lastChild;
                while (J.lastChild && (!(type & 1) || J.Expanded)) J = J.lastChild;
            }
        }
    };
    return J;
};
_7m.prototype.GetFirstVisible = function (H, type) {
    var row = this.GetFirst(H, type);
    if (row && !row.Visible) return this.GetNextVisible(row, H ? type | 2 : type);
    return row;
};
_7m.prototype.GetLastVisible = function (H, type) {
    if (!H) {
        H = this.XB.lastChild;
        while (H && !H.firstChild) H = H.previousSibling;
    }
    if (!H) return null;
    var row = H.lastChild;
    if (!row) return null;
    if (row && !row.Visible) return this.GetPrevVisible(row, H ? type | 2 : type);
    while (row.lastChild && row.Visible && (!(type & 1) || row.Expanded)) row = row.lastChild;
    if (row && !row.Visible) return this.GetPrevVisible(row, H ? type | 2 : type);
    return row;
};
_7m.prototype.GetNextVisible = function (row, type) {
    while (1) {
        var J = row.Visible ? row.firstChild: null;
        if (!J || type & 1 && !row.Expanded) {
            J = row.nextSibling;
            if (!J) {
                var J = row;
                while (!J.nextSibling && !J.Page) J = J.parentNode;
                if (J.Page) {
                    if (type & 2 || !J.nextSibling) break;
                    J = J.nextSibling;
                    while (J && !J.firstChild) J = J.nextSibling;
                    if (J) J = J.firstChild;
                } else J = J.nextSibling;
            }
        }
        if (!J) break;
        if (J.Visible) return J;
        row = J;
    }
    return null;
};
_7m.prototype.GetPrevVisible = function (row, type) {
    var J = row.previousSibling;
    while (J && !J.Visible) J = J.previousSibling;
    if (J) while (J.lastChild && J.lastChild.Visible && (!(type & 1) || J.Expanded)) J = J.lastChild;
    else {
        J = row.parentNode;
        if (J.Page) {
            if (type & 2) return null;
            J = J.previousSibling;
            while (J && !J.firstChild) J = J.previousSibling;
            if (J) {
                J = J.lastChild;
                while (J.lastChild && J.lastChild.Visible && (!(type & 1) || J.Expanded)) J = J.lastChild;
            }
        }
    };
    return J;
};
_7m.prototype.GetPrevShift = function (row, Om, BQ) {
    var Ej = BQ;
    if (row && row.Space >= 10) {
        for (var J = row.previousSibling; J && BQ > 0; J = J.previousSibling) {
            if (J.Space == row.Space && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        return [BQ > 0 ? null: row, 0];
    }
    if (!row || BQ != Ej || row.Space >= 3 && row.Space <= 9) {
        for (var J = row ? row.previousSibling: this.XS.lastChild; J && BQ > 0; J = J.previousSibling) {
            if (J.Space >= 3 && J.Space <= 9 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Fixed == "Foot") {
        for (var J = row ? row.previousSibling: this.XF.lastChild; J && BQ > 0; J = J.previousSibling) {
            if (J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Space == 2) {
        for (var J = row ? row.previousSibling: this.XS.lastChild; J && BQ > 0; J = J.previousSibling) {
            if (J.Space == 2 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if ((!row || BQ != Ej || row.Fixed == 'Foot' || !row.Fixed || row.Space >= 2) && BQ > 0) {
        if (!row || row.Fixed) {
            row = this.XB.lastChild;
            if (row.State == 4) {
                row = this.GetLastVisible(null, 1);
                Om = null;
            } else Om = this.SQ(row) / this.RowHeight - 1;
            if (row && row.Visible) BQ--;
        }
        if (row && BQ > 0) {
            while (BQ && row) {
                if (row.Page && !this.AllPages) {
                    row = null;
                    Om = null;
                    break;
                }
                if (row.Page && row.State < 4) {
                    if (Om == null) Om = row.State < 2 ? row.Count: row.childNodes.length;
                    if (BQ <= Om) {
                        Om -= BQ;
                        BQ = 0;
                    } else {
                        BQ -= Om;
                        row = row.previousSibling;
                        if (row) Om = this.SQ(row) / this.RowHeight - 1;
                    }
                } else {
                    var J = row.previousSibling;
                    if (J && J.Page) {
                        if (J.State < 4) {
                            row = J;
                            Om = row.State < 2 ? row.Count: row.childNodes.length;
                            continue;
                        }
                        J = J.lastChild;
                    }
                    if (J) while (J.lastChild && J.Expanded) J = J.lastChild;
                    else J = row.parentNode;
                    if (J && !J.Page && J.Visible) BQ--;
                    row = J;
                }
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Space == 1) {
        for (var J = row ? row.previousSibling: this.XS.lastChild; J && BQ > 0; J = J.previousSibling) {
            if (J.Space == 1 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Fixed == "Head") {
        for (var J = row ? row.previousSibling: this.XH.lastChild; J && BQ > 0; J = J.previousSibling) {
            if (J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Space == 0) {
        for (var J = row ? row.previousSibling: this.XS.lastChild; J && BQ > 0; J = J.previousSibling) {
            if (J.Space == 0 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (BQ > 0) return [null, 0];
    return [row, Om];
};
_7m.prototype.GetNextShift = function (row, Om, BQ) {
    var Ej = BQ;
    if (row && row.Space >= 10) {
        for (var J = row.nextSibling; J && BQ > 0; J = J.nextSibling) {
            if (J.Space == row.Space && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        return [BQ > 0 ? null: row, 0];
    }
    if (!row || BQ != Ej || row.Space == 0) {
        for (var J = row ? row.nextSibling: this.XS.firstChild; J && BQ > 0; J = J.nextSibling) {
            if (J.Space == 0 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Fixed == 'Head') {
        for (var J = row ? row.nextSibling: this.XH.firstChild; J && BQ > 0; J = J.nextSibling) {
            if (J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Space == 1) {
        for (var J = row ? row.nextSibling: this.XS.firstChild; J && BQ > 0; J = J.nextSibling) {
            if (J.Space == 1 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if ((!row || BQ != Ej || row.Fixed == 'Head' || !row.Fixed || row.Space <= 1) && BQ > 0) {
        if (!row || row.Fixed) {
            row = this.XB.firstChild;
            if (row.State == 4) {
                row = this.GetFirstVisible(null, 1);
                Om = null;
            } else Om = this.SQ(row) / this.RowHeight - 1;
            if (row && row.Visible) BQ--;
        }
        if (row && BQ > 0) {
            while (BQ && row) {
                if (row.Page && row.State < 4) {
                    if (!this.AllPages) {
                        row = null;
                        Om = null;
                        break;
                    }
                    if (Om == null) Om = -1;
                    var Bb = this.SQ(row) / this.RowHeight;
                    if (BQ < Bb - Om) {
                        Om += BQ;
                        BQ = 0;
                    } else {
                        BQ -= Bb - Om;
                        row = row.nextSibling;
                        Om = 0;
                    }
                } else {
                    var J = row.firstChild;
                    if (!J || !row.Page && !row.Expanded) {
                        J = row.nextSibling;
                        if (!J) {
                            J = row.parentNode;
                            while (J && !J.nextSibling) J = J.parentNode;
                            if (J) J = J.nextSibling;
                        }
                    }
                    if (J && !J.Page && J.Visible) BQ--;
                    row = J;
                }
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Space == 2) {
        for (var J = row ? row.nextSibling: this.XS.firstChild; J && BQ > 0; J = J.nextSibling) {
            if (J.Space == 2 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Fixed == 'Foot') {
        for (var J = row ? row.nextSibling: this.XF.firstChild; J && BQ > 0; J = J.nextSibling) {
            if (J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (!row || BQ != Ej || row.Space >= 3 && row.Space <= 9) {
        for (var J = row ? row.nextSibling: this.XS.firstChild; J && BQ > 0; J = J.nextSibling) {
            if (J.Space >= 3 && J.Space <= 9 && J.Cells && J.Visible) {
                row = J;
                BQ--;
            }
        }
        if (BQ > 0) row = null;
    }
    if (BQ > 0) return [null, 0];
    return [row, Om];
};
_7m.prototype.GetNextSibling = function (row) {
    var B3 = row.nextSibling;
    if (B3) return B3;
    B3 = row.parentNode.nextSibling;
    while (B3) {
        if (B3.firstChild) return B3.firstChild;
        B3 = B3.nextSibling;
    }
    return null;
};
_7m.prototype.GetNextSiblingVisible = function (row) {
    do {
        row = this.GetNextSibling(row);
    } while (row && !row.Visible);
    return row;
};
_7m.prototype.UT = function (row, select) {
    for (var x = row.firstChild; x; x = x.nextSibling) if (x.Visible) this.UT(x, select);
    if ((row.Selected ? row.Selected: 0) != select) {
        if (Grids.OnSelect) Grids.OnSelect(this, row, !select);
        row.Selected = !row.Selected;
        this.ColorRow(row);
        this.K2(row);
    }
};
_7m.prototype.VF = function (row, BL) {
    for (var J = row.firstChild; J; J = J.nextSibling) {
        if (J.Selected && J.Visible && Is(J, "CanDelete")) BL[BL.length] = J;
        this.VF(J, BL);
    }
};
_7m.prototype.Us = function () {
    var BL = new Array();
    for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) if (! (Bg.State < 2)) this.VF(Bg, BL);
    return BL;
};
_7m.prototype.SelectRow = function (row, VG, GV) {
    if (!row || row.Page || !Is(row, "CanSelect") || !this.Selecting) return false;
    this.AY = null;
    if (row.Selected == 2) {
        for (var BY in this.Cols) {
            var BO = BY + "Selected";
            if (row[BO]) row[BO] = null;
        }
    }
    if (GV != null) if (!row.Selected == !GV) return false;
    if (Grids.OnSelect) Grids.OnSelect(this, row, row.Selected ? 1 : 0);
    row.Selected = GV == null ? !(row.Selected - 0) : GV;
    this.ColorRow(row);
    this.K2(row);
    if (!VG && this.SaveSelected) this.SaveCfg();
    return true;
};
_7m.prototype.SelectAllRows = function (select) {
    if (!this.Selecting || !this.XHeader.CanSelect) return false;
    var page = this.Paging == 3 || this.Paging && !this.AllPages ? this.GetFPage() : null,
    type = page ? 2 : 0;
    if (this.Paging == 3 && (!this.FRow || this.FRow.Fixed)) {
        var OV = this.GetAlert("NoActivePage");
        if (OV) {
            if (Grids.OnSelectAll && Grids.OnSelectAll(this, select)) return true;
            alert(OV);
            return true;
        }
        if (!page || page.State < 2) return false;
    }
    var GV = 0,
    VH = 0;
    for (var J = this.GetFirstVisible(page, type | 1); J; J = this.GetNextVisible(J, type | 1)) {
        if (!Is(J, "CanSelect")) continue;
        if (J.Selected) {
            GV = 1;
            if (VH) break;
        } else {
            VH = 1;
            if (GV) break;
        }
    }
    if (select == null) select = !GV;
    if (select == -1) {
        if (!GV) select = 1;
        else if (!VH) select = 0;
    }
    if (select == 0 && GV == 0 || select == 1 && VH == 0) return false;
    if (Grids.OnSelectAll && Grids.OnSelectAll(this, select)) return true;
    if (select == 1) type |= 1;
    if (this.RowCount < this.SynchroCount) this.VI(select, type, page);
    else {
        this.ShowMessage(this.GetText("SelectAll"));
        var T = this;
        setTimeout(function () {
            T.VI(select, type, page);
        },
        10);
    };
    return true;
};
_7m.prototype.VI = function (select, type, page) {
    for (var J = this.GetFirstVisible(page, type); J; J = this.GetNextVisible(J, type)) {
        if (Is(J, "CanSelect") && !J.Selected == select || select == -1) this.SelectRow(J);
    }
    this.AY = select == -1 ? null: select;
    if (this.SaveSelected) this.SaveCfg();
    this.HideMessage();
};
_7m.prototype.SelectRange = function (r1, DP, r2, DQ, GV, O3, OT, OU, BL) {
    if (!this.Selecting) return false;
    var GN = O3 & 1 ? this.GetNextVisible: this.GetNext;
    var type = O3 & 2 ? 1 : 0;
    var Bg, J, dir = 0;
    if (GV == 2) {
        if (!r1 || r1.Fixed) GV = 1;
        else if (!DP) GV = r1.Selected ? 0 : 1;
        else GV = r1.Selected == 1 || r1.Selected == 2 && Is(r1, DP + "Selected") ? 0 : 1;
    }
    if (!r1 || r1.Fixed == "Foot" || r1.Space >= 2) {
        if (O3 & 4) return;
        r1 = dir & 1 ? this.GetLastVisible(null, type) : this.GetLast(null, type);
        if (DP && OT != null) DP = this.GetColLeft(r1, OT)[0];
        dir = -1;
    } else if (r1.Fixed == "Head" || r1.Space <= 1 || r1 == this.XHeader) {
        if (O3 & 4) return;
        r1 = dir & 1 ? this.GetFirstVisible() : this.GetFirst();
        if (DP && OT != null) DP = this.GetColLeft(r1, OT)[0];
        dir = 1;
    }
    if (!r2 || r2.Fixed == "Foot" || r2.Space >= 2) {
        if (O3 & 4 || dir == -1) return;
        r2 = dir & 1 ? this.GetLastVisible(null, type) : this.GetLast(null, type);
        if (DP && OU != null) DQ = this.GetColLeft(r2, OU)[0];
        dir = 1;
    } else if (r2.Fixed == "Head" || r2.Space <= 1 || r2 == this.XHeader) {
        if (O3 & 4 || dir == 1) return;
        r2 = dir & 1 ? this.GetFirstVisible() : this.GetFirst();
        if (DP && OU != null) DQ = this.GetColLeft(r2, OU)[0];
        dir = -1;
    }
    if (!r1 || !r2) return;
    if (!dir && this.Paging) {
        var VJ = this.GetRowPage(r1),
        VK = this.GetRowPage(r2);
        if (VJ != VK) {
            for (Bg = VJ; Bg && Bg != VK; Bg = Bg.nextSibling);
            dir = Bg == VK ? 1 : -1;
        }
    }
    if (!dir) {
        var OR = r1,
        OS = r2;
        while (1) {
            if (!OR || OS == r1) {
                dir = -1;
                break;
            }
            if (!OS || OR == r2) {
                dir = 1;
                break;
            }
            OR = GN(OR);
            OS = GN(OS);
        }
    }
    if (dir < 0) {
        J = r1;
        r1 = r2;
        r2 = J;
    }
    if (BL) {
        var VL = BL[0],
        VM = BL[2];
        var VN = [],
        VO = [],
        Fl = [],
        R = [];
        for (var J = r1; J; J = GN(J, type)) {
            if (J == VL) {
                Fl = R;
                R = [];
                VL = null;
            }
            R[R.length] = J;
            if (J == VM) {
                VN = R;
                R = [];
                VM = null;
            }
            if (J == r2) {
                if (!VM) {
                    Fl = Fl.length ? Fl.concat(R) : R;
                } else if (VL) {
                    for (var J = VL; J; J = GN(J, type)) {
                        if (J == r1) break;
                        if (J == VM) {
                            Fl = R;
                            for (var J = VL; J; J = GN(J, type)) {
                                VO[VO.length] = J;
                                if (J == VM) break;
                            }
                            VL = null;
                            VM = null;
                            break;
                        }
                    }
                }
                if (VM) {
                    VN = R;
                    for (J = GN(r2, type); J; J = GN(J, type)) {
                        VO[VO.length] = J;
                        if (J == VM) break;
                    }
                }
                if (VL) for (J = VL; J && J != r1; J = GN(J, type)) VO[VO.length] = J;
                break;
            }
        }
    }
    if (!DP) {
        if (!BL) {
            for (var J = r1; J; J = GN(J, type)) {
                if (!J.Selected == GV && Is(J, "CanSelect")) {
                    if (J.Selected == 2) for (var BY in this.Cols) {
                        var BO = BY + "Selected";
                        if (row[BO]) row[BO] = null;
                    }
                    if (Grids.OnSelect) Grids.OnSelect(this, J, !GV);
                    J.Selected = GV;
                    if (! (O3 & 8)) this.ColorRow(J);
                    this.K2(J);
                }
                if (J == r2) break;
            }
        } else {
            var VP = this.ARow;
            this.ARow = null;
            for (var i = 0, Bg = VO.length; i < Bg; i++) if (Is(VO[i], "CanSelect")) this.ColorRow(VO[i]);
            for (var i = 0, Bg = Fl.length; i < Bg; i++) {
                var J = Fl[i],
                VQ = J.Selected;
                J.Selected = GV;
                this.ColorRow(J);
                J.Selected = VQ;
            }
            this.ARow = VP;
        }
    } else {
        var S = [];
        for (var BY = DP; BY; BY = this.GetNextCol(BY)) {
            S[S.length] = BY;
            if (BY == DQ) break;
        }
        if (!BY) {
            S = [];
            BY = DP;
            DP = DQ;
            DQ = BY;
            for (var BY = DP; BY; BY = this.GetNextCol(BY)) {
                S[S.length] = BY;
                if (BY == DQ) break;
            }
        }
        if (!BL) {
            var Bg = S.length,
            BZ = !(O3 & 8);
            var BL = new Object();
            for (var i = 0; i < Bg; i++) BL[S[i]] = 1;
            var P = new Array(),
            x = 0;
            for (var BY in this.Cols) if (!BL[BY] && BY != "Panel") P[x++] = BY;
            for (var J = r1; J; J = GN(J, type)) {
                if (!Is(J, "CanSelect")) {
                    if (J == r2) break;
                    continue;
                }
                var Bh = J.Selected;
                if (!Bh) {
                    if (GV) {
                        if (Grids.OnSelect) Grids.OnSelect(this, J, 0);
                        if (!x) {
                            J.Selected = 1;
                            this.ColorRow(J);
                        } else {
                            J.Selected = 2;
                            for (var i = 0; i < Bg; i++) {
                                J[S[i] + "Selected"] = 1;
                                if (BZ) this.ColorCell(J, S[i]);
                            }
                        };
                        this.K2(J);
                    }
                } else if (Bh == 1) {
                    if (!GV) {
                        if (Grids.OnSelect) Grids.OnSelect(this, J, 1);
                        if (!x) {
                            J.Selected = 0;
                            this.ColorRow();
                        } else {
                            J.Selected = 2;
                            for (var i = 0; i < x; i++) J[P[i] + "Selected"] = 1;
                            for (var i = 0; i < Bg; i++) if (this.Cols[S[i]].Visible) if (BZ) this.ColorCell(J, S[i]);
                        };
                        this.K2(J);
                    }
                } else {
                    var BQ = 0;
                    for (var i = 0; i < x; i++) if (!J[P[i] + "Selected"] != GV) BQ++;
                    for (var i = 0; i < Bg; i++) {
                        var VR = S[i] + "Selected";
                        if (!J[VR] == GV) {
                            J[VR] = GV;
                            if (BZ) this.ColorCell(J, S[i]);
                        }
                    }
                    if (BQ == x) {
                        for (var i = 0; i < Bg; i++) J[S[i] + "Selected"] = null;
                        for (var i = 0; i < x; i++) J[P[i] + "Selected"] = null;
                        J.Selected = GV;
                    }
                    if (!GV) this.K2(J);
                };
                if (J == r2) break;
            }
        } else {
            var VP = this.ARow;
            this.ARow = null;
            var P = [],
            VS = [],
            VT = [],
            ES = {};
            for (var BY = BL[1]; BY; BY = this.GetNextCol(BY)) {
                P[P.length] = BY;
                ES[BY] = 1;
                if (BY == BL[3]) break;
            }
            for (var i = 0; i < S.length; i++) {
                if (!ES[S[i]]) VT[VT.length] = S[i];
                else ES[S[i]] = null;
            }
            for (var BY in ES) if (ES[BY]) VS[VS.length] = BY;
            var VU = VS.length,
            VV = VT.length,
            VW = P.length,
            VR = S.length;
            for (var i = 0; i < VO.length; i++) {
                var J = VO[i];
                if (!Is(J, "CanSelect")) continue;
                for (var j = 0; j < VW; j++) this.ColorCell(J, P[j]);
            }
            for (var i = 0; i < Fl.length; i++) {
                var J = Fl[i];
                if (!Is(J, "CanSelect")) continue;
                var VQ = J.Selected;
                J.Selected = GV;
                for (var j = 0; j < VR; j++) this.ColorCell(J, S[j]);
                J.Selected = VQ;
            }
            for (var i = 0; i < VN.length; i++) {
                var J = VN[i];
                if (!Is(J, "CanSelect")) continue;
                var VQ = J.Selected;
                J.Selected = GV;
                for (var j = 0; j < VV; j++) this.ColorCell(J, VT[j]);
                J.Selected = VQ;
                for (var j = 0; j < VU; j++) this.ColorCell(J, VS[j]);
            }
            this.ARow = VP;
        }
    };
    this.Refresh();
    if (this.SaveSelected) this.SaveCfg();
    this.AY = null;
    return [r1, DP, r2, DQ];
};
_7m.prototype.VX = function (row) {
    for (var J = row.firstChild; J; J = J.nextSibling) {
        if (J.Selected && (J.CanSelect == null ? J.Def.CanSelect: J.CanSelect)) this.SelectRow(J);
        if (J.firstChild) this.VX(J);
    }
};
_7m.prototype.ActionClearSelection = function () {
    for (var J = this.XB.firstChild; J; J = J.nextSibling) this.VX(J);
    this.AY = 0;
    return true;
};
_7m.prototype.ActionSelectRow = function (CE) {
    return this.SelectRow(CE ? this.FRow: this.ARow, null, true);
};
_7m.prototype.ActionDeselectRow = function (CE) {
    return this.SelectRow(CE ? this.FRow: this.ARow, null, false);
};
_7m.prototype.ActionSelectCell = function (CE) {
    if (!this.Selecting || !this.SelectingCells) return false;
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!Is(row, "CanSelect") || row.Selected == 1 || row.Selected == 2 && Is(row, BJ + "Selected")) return false;
    this.SelectRange(row, BJ, row, BJ, 1);
    return true;
};
_7m.prototype.ActionDeselectCell = function (CE) {
    if (!this.Selecting || !this.SelectingCells) return false;
    var row = CE ? this.FRow: this.ARow,
    BJ = CE ? this.FCol: this.ACol;
    if (!Is(row, "CanSelect") || row.Selected == 0 || row.Selected == 2 && !Is(row, BJ + "Selected")) return false;
    this.SelectRange(row, BJ, row, BJ, 0);
    return true;
};
_7m.prototype.ActionSelectRowRange = function () {
    return this.SelectRange(this.FRow, null, this.ARow, null, 1, 7) ? 1 : 0;
};
_7m.prototype.ActionDeselectRowRange = function () {
    return this.SelectRange(this.FRow, null, this.ARow, null, 0, 7) ? 1 : 0;
};
_7m.prototype.ActionInvertRowRangeFirst = function () {
    return this.SelectRange(this.FRow, null, this.ARow, null, 2, 7) ? 1 : 0;
};
_7m.prototype.ActionSelectCellRange = function () {
    return this.SelectingCells && this.SelectRange(this.FRow, this.FCol, this.ARow, this.ACol, 1, 7) ? 1 : 0;
};
_7m.prototype.ActionDeselectCellRange = function () {
    return this.SelectingCells && this.SelectRange(this.FRow, this.FCol, this.ARow, this.ACol, 0, 7) ? 1 : 0;
};
_7m.prototype.ActionInvertCellRangeFirst = function () {
    return this.SelectingCells && this.SelectRange(this.FRow, this.FCol, this.ARow, this.ACol, 2, 7) ? 1 : 0;
};
_7m.prototype.ActionSelectAll = function () {
    return this.SelectAllRows(1);
};
_7m.prototype.ActionDeselectAll = function () {
    return this.SelectAllRows(0);
};
_7m.prototype.ActionInvertAll = function () {
    return this.SelectAllRows( - 1);
};
_7m.prototype.ActionSelectRows = function () {
    return this.VY(0, 1);
};
_7m.prototype.ActionDeselectRows = function () {
    return this.VY(0, 0);
};
_7m.prototype.ActionInvertRowsFirst = function () {
    return this.VY(0, 2);
};
_7m.prototype.ActionSelectCells = function () {
    return this.VY(1, 1);
};
_7m.prototype.ActionDeselectCells = function () {
    return this.VY(1, 0);
};
_7m.prototype.ActionInvertCellsFirst = function () {
    return this.VY(1, 2);
};
_7m.prototype.VY = function (cells, GV) {
    if (!this.Selecting || cells && !this.SelectingCells) return false;
    var D8 = Grids.Az;
    if (!D8) return false;
    if (D8.Row.Fixed) return false;
    D8.Action = "Select";
    D8.Move = this.VZ;
    D8.II = this.Va;
    D8.Type = 7;
    D8.Rj = GV;
    D8.Cells = cells;
    D8.Vb = [];
    return true;
};
_7m.prototype.VZ = function (Click) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BL = this.SelectRange(D8.Row, D8.Cells ? D8.Col: null, Click.Row, Click.Col, D8.Rj, D8.Type, null, Click.P, D8.Vb);
    if (BL) D8.Vb = BL;
    this.IP(Click.Row);
    return true;
};
_7m.prototype.Va = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    this.SelectRange(D8.Row, D8.Cells ? D8.Col: null, D8.Row == D8.Vb[0] ? D8.Vb[2] : D8.Vb[0], D8.Col == D8.Vb[1] ? D8.Vb[3] : D8.Vb[1], D8.Rj, D8.Type | 8, null, 0);
    return true;
};
_7m.prototype.ActionSelectOddRows = function () {
    return this.Vc(1);
};
_7m.prototype.ActionDeselectOddRows = function () {
    return this.Vc(0);
};
_7m.prototype.ActionInvertOddRows = function () {
    return this.Vc(2);
};
_7m.prototype.Vc = function (GV) {
    var D8 = Grids.Az;
    if (!D8) return false;
    D8.Action = "SelectPanel";
    D8.Move = this.Vd;
    D8.Rj = GV;
    if (GV == 2 || !GV != !D8.Row.Selected) this.SelectRow(D8.Row);
    return true;
};
_7m.prototype.Vd = function (Click) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var r1 = D8.Row,
    r2 = Click.Row;
    if (!r2 || r2.Fixed || r2 == this.XHeader || r1 == r2) return true;
    var OR = r1,
    OS = r2,
    GV = D8.Rj;
    while (1) {
        if (!OR || OS == r1) {
            for (var J = r2; J && J != r1; J = this.GetNextVisible(J, 1)) {
                if (GV == 2 || !GV != !J.Selected) this.SelectRow(J);
            }
            break;
        }
        if (!OS || OR == r2) {
            for (var J = this.GetNextVisible(r1, 1); J; J = this.GetNextVisible(J, 1)) {
                if (GV == 2 || !GV != !J.Selected) this.SelectRow(J);
                if (J == r2) break;
            }
            break;
        }
        OR = this.GetNextVisible(OR, 1);
        OS = this.GetNextVisible(OS, 1);
    }
    D8.Row = Click.Row;
    return true;
};
_7m.prototype.ActionSelectCol = function (CE) {
    var BJ = CE ? this.FCol: this.ACol;
    if (!BJ || !this.SelectingCells) return false;
    var BQ = 0;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
        if (!Is(J, "CanSelect") || J.Selected == 1) continue;
        if (!J.Selected) J.Selected = 2;
        else if (J[BJ + "Selected"]) continue;
        J[BJ + "Selected"] = 1;
        BQ++;
        this.ColorCell(J, BJ);
    }
    return BQ ? true: false;
};
_7m.prototype.ActionDeselectCol = function (CE) {
    var BJ = CE ? this.FCol: this.ACol;
    if (!BJ || !this.SelectingCells) return false;
    var BQ = 0;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
        if (!Is(J, "CanSelect") || !J.Selected) continue;
        if (J.Selected == 1) {
            J.Selected = 2;
            for (var BY in this.Cols) J[BY + "Selected"] = 1;
        } else {
            if (!J[BJ + "Selected"]) continue;
            var Ve = 0;
            for (var BY in this.Cols) if (BY != BJ && J[BY + "Selected"]) {
                Ve++;
                break;
            }
            if (!Ve) J.Selected = 0;
        };
        J[BJ + "Selected"] = 0;
        BQ++;
        this.ColorCell(J, BJ);
    }
    return BQ ? true: false;
};
_7m.prototype.Vf = function (dir) {
    var D8 = Grids.Az;
    if (!D8) return false;
    if (D8.Row.Fixed) return false;
    D8.Action = "Fill";
    D8.Move = this.Vg;
    D8.II = this.Vh;
    D8.Type = 7;
    D8.N4 = dir;
    D8.Vb = [];
    return true;
};
_7m.prototype.Vg = function (Click) {
    var D8 = Grids.Az;
    if (!D8) return false;
    var BL = this.SelectRange(D8.Row, D8.Col, D8.N4 & 1 ? Click.Row: D8.Row, D8.N4 & 2 ? Click.Col: D8.Col, 1, D8.Type, null, Click.P, D8.Vb);
    if (BL) D8.Vb = BL;
    this.IP(Click.Row);
    return true;
};
_7m.prototype.Vh = function () {
    var D8 = Grids.Az;
    if (!D8) return false;
    this.SelectRange(D8.Row, D8.Col, D8.Row, D8.Col, 1, D8.Type, null, null, D8.Vb);
    this.ColorCell(D8.Row, D8.Col);
    var Vi, r2, Vj, DQ;
    if (D8.Row == D8.Vb[0]) {
        r2 = D8.Vb[2];
        Vi = 0
    } else {
        r2 = D8.Vb[0];
        Vi = 1;
    };
    if (D8.Col == D8.Vb[1]) {
        Vj = 0,
        DQ = D8.Vb[3];
    } else {
        Vj = 1,
        DQ = D8.Vb[1];
    };
    if (Grids.OnAutoFill && Grids.OnAutoFill(this, D8.Row, D8.Col, r2, DQ, Vi, Vj)) return true;
    var Vk = 0;
    if (this.Calculating && this.Calculated) {
        Vk = 1;
        this.Calculated = 0;
    }
    var BR = this.GetValue(D8.Row, D8.Col),
    BZ = null;
    for (var J = D8.Row, T8 = 0; J; J = Vi ? this.GetPrevVisible(J) : this.GetNextVisible(J), T8++) {
        for (var BY = D8.Col, KN = 0; BY; BY = Vj ? this.GetPrevCol(BY) : this.GetNextCol(BY), KN++) {
            if (this.CanEdit(J, BY)) {
                BZ = Grids.OnAutoFillValue ? Grids.OnAutoFillValue(this, J, BY, D8.Row, D8.Col, BR, BZ, Vi ? -T8: T8, Vj ? -KN: KN) : BR;
                this.SetValue(J, BY, BZ, 1);
            }
            if (BY == DQ) break;
        }
        if (J == r2) break;
    }
    if (Vk) {
        this.Calculated = 1;
        this.Calculate(1);
    }
    return true;
};
_7m.prototype.ActionFillCells = function () {
    return this.Vf(3);
};
_7m.prototype.ActionFillRow = function () {
    return this.Vf(2);
};
_7m.prototype.ActionFillCol = function () {
    return this.Vf(1);
};
_7m.prototype.Expand = function (row, OG, VG, Ga, Vl) {
    var D8 = Get(row, "Detail");
    if (D8) {
        var Dh = row.Expanded;
        row.Expanded = 1;
        if (!Ga && this.DetailExpand != 1) this.ShowDetail(row, D8);
        row.Expanded = Dh;
        if (!this.DetailExpand) return;
    }
    if ((!this.HasChildren(row) || row.Expanded) && !OG || Get(row, "CanExpand") == 0) return;
    var E8 = 0,
    T = this;
    row.Expanded = 1;
    if (this.SaveExpanded && !VG) this.SaveCfg();
    if (!row.firstChild && row.State == null && this.ChildPaging == 3) row.State = 0;
    function BI() {
        if (OG) {
            for (var J = row.firstChild; J; J = J.nextSibling) if (J.Visible) {
                T.Ef(J);
                T.Es(J);
            }
        }
    };
    if (!row._7Ax) this.Ry(row);
    if (!this.ChildPaging || row.State == 4) {
        this.UP(row);
        BI();
        this.Vm(row);
    } else {
        var Vn = this.UW(row, 4);
        if (! (row.State < 2) && !Vn) {
            this.FF(row);
            BI();
            this.Vm(row);
        } else {
            if (!row.r1) {
                return;
            }
            this.Vm(row);
            if (this.ChildPaging == 3 && row.State < 2) {
                if (Vn && !this.SuppressMessage) _7Ak(this.GetText("Load"), this.GetRowChildren(row, 1), this.Img.Style + "PageMessage", this.c[1].offsetHeight);
                this.R0(row, Vn);
            } else if (Vl) {
                this.FF(row);
                this.SetScrollBars();
                row.State = 4;
            } else this.Vo(row);
        }
    };
};
_7m.prototype.ExpandParents = function (row) {
    if (!row || row.Fixed || !row.Visible || row.Page) return;
    var EU = row.parentNode;
    while (EU && !EU.Page) {
        if (!EU.Expanded) this.Expand(EU);
        EU = EU.parentNode;
    }
};
_7m.prototype.R0 = function (row, Vn) {
    var T = this,
    Sb = this.Ax;
    row = this.E0(row);
    this.DownloadPage(row, function (E3) {
        if (Sb != T.Ax) return;
        T.Sx(E3, row);
        if (row.firstChild == null) {
            row.Count = 0;
            row.State = 4;
            if (row.Copy) row.Copy = null;
            T.Er(row);
            T.RefreshRow(row);
            return;
        }
        if (E3 >= 0) {
            function Vp(row) {
                for (var J = row.firstChild; J; J = J.nextSibling) {
                    J.Added = 1;
                    var id = J.id;
                    T.F4(J);
                    if (J.firstChild) Vp(J);
                    else if (J.Count) J.Copy = id;
                    if (Grids.OnRowAdd) Grids.OnRowAdd(T, J);
                }
            };
            if (row.Copy) Vp(row);
            if (row.Expanded) {
                if (Vn) T.Vo(row);
                else {
                    T.FF(row);
                    T.SetScrollBars();
                }
            } else row.State = 2;
            if (row.DetailRow) {
                T.ShowDetail(row, Get(row, "Detail"), 1);
            }
        }
    });
};
_7m.prototype.Vo = function (row) {
    var T = this,
    Sb = this.Ax;
    if (!this.SuppressMessage) _7Ak(T.GetText("Render"), T.GetRowChildren(row, 1), this.Img.Style + "PageMessage", T.c[1].offsetHeight);
    row.State = 3;
    setTimeout(function () {
        if (Sb != T.Ax) return;
        if (row.State < 4) {
            T.FF(row);
            T.SetScrollBars();
            row.State = 4;
        }
    },
    10);
};
_7m.prototype.Vm = function (row) {
    this.Es(row);
    if (!this.NoScrollAfterExpand) {
        if (!row.lastChild || !row.lastChild.r1) this.ScrollIntoView(row, null, this.UK(row));
        else this.ScrollIntoView(row.lastChild);
        this.ScrollIntoView(row);
    }
};
_7m.prototype.Collapse = function (row, VG) {
    if (!this.HasChildren(row) || !row.Expanded) return;
    row.Expanded = 0;
    if (this.SaveExpanded && !VG) this.SaveCfg();
    if (row.State == null) row.State = 4;
    this.Rz(row);
    this.Es(row);
    if (this.RemoveCollapsed >= 3) {
        this._7d(row, 1);
        var BQ = 0;
        while (row.firstChild) {
            row.removeChild(row.firstChild);
            BQ++;
        }
        row.Count = BQ;
        row.State = 0;
        if (Grids.OnRemoveCollapsed) Grids.OnRemoveCollapsed(this, row);
    }
};
_7m.prototype.ExpandAll = function (Vq, Vr, Vs) {
    if (!Vq) Vq = 5 * 1000;
    if (!Vs) Vs = 500;
    this.Rendering = true;
    var type = 0,
    body = null;
    if (!this.AllPages) {
        type = 2;
        body = _7c(this.XB, this.AP);
    }
    var Vt = (new Date).getTime() + Vq;
    var T = this;
    function Vu(J) {
        if (T.HasChildren(J) && !J.Expanded && (!(Vr & 1) || T.ChildPaging != 3 || !(J.State < 2))) {
            if (T.ChildPaging == 2 && J.State != 4) {
                if (!T.Paging || T.Paging == 1 || T.GetRowPage(J).State == 4) {
                    Vs -= J.childNodes.length;
                    if (Vs < 0) return 1;
                }
            }
            if (!Grids.OnExpand || !Grids.OnExpand(T, J)) T.Expand(J, null, true, null, 1);
        }
    };
    if (this.FRow && !this.FRow.Fixed) for (var J = this.FRow; J; J = this.GetNextVisible(J, type)) {
        if (Vu(J)) break;
        if ((new Date).getTime() > Vt) break;
    }
    for (var J = this.GetFirstVisible(body); J && J != this.FRow; J = this.GetNextVisible(J, type)) {
        if ((new Date).getTime() > Vt) break;
        if (Vu(J)) break;
    }
    if (this.SaveExpanded) this.SaveCfg();
    this.Rendering = false;
    this.SetScrollBars();
    this.HideMessage();
};
_7m.prototype.CollapseAll = function () {
    this.Rendering = true;
    for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
        if (J.firstChild && J.Expanded) {
            if (!Grids.OnExpand || !Grids.OnExpand(this, J)) this.Collapse(J, true);
        }
    }
    var I7 = this.FRow;
    if (I7 && !I7.Page && !I7.Fixed) {
        for (var J = I7.parentNode; J && !J.Page; J = J.parentNode) if (!J.Expanded) I7 = J;
    }
    this.Rendering = false;
    this.SetScrollBars();
    this.HideMessage();
    if (I7 != this.FRow) this.Focus(I7, this.FCol);
    if (this.SaveExpanded) this.SaveCfg();
    this.ShowPages();
};
_7m.prototype.Vv = function (type) {
    if (!this.Paging && this.RowCount <= this.SynchroCount) {
        this.ExpandAll(null, type);
        return true;
    }
    this.ShowMessage(this.GetText("ExpandAll"));
    var T = this;
    setTimeout(function () {
        T.ExpandAll(null, type);
    },
    10);
    return true;
};
_7m.prototype.ActionExpandAll = function () {
    this.Vv(this.ExpandAllType);
    return true;
};
_7m.prototype.ActionExpandAllLoaded = function () {
    this.Vv(this.ExpandAllType);
    return true;
};
_7m.prototype.ActionCollapseAll = function () {
    if (!this.Paging && this.RowCount <= this.SynchroCount) {
        this.CollapseAll();
        return true;
    }
    this.ShowMessage(this.GetText("CollapseAll"));
    var T = this;
    setTimeout(function () {
        T.CollapseAll();
    },
    10);
    return true;
};
_7m.prototype.ActionExpand = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (!row || !this.HasChildren(row) && !row.Detail || !Is(row, "CanExpand") || row.Expanded || Grids.OnExpand && Grids.OnExpand(this, row)) return false;
    this.Expand(row);
    return true;
};
_7m.prototype.ActionCollapse = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (!row || !this.HasChildren(row) && !row.Detail || !Is(row, "CanExpand") || !row.Expanded || Grids.OnExpand && Grids.OnExpand(this, row)) return false;
    this.Collapse(row);
    return true;
};
_7m.prototype.ActionIndent = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (!row || row.Fixed || row.Space != null || row.Page) return false;
    var Bg = this.GetPrevVisible(row);
    if (!Bg || Bg.Level < row.Level) return false;
    while (Bg.Level > row.Level) Bg = this.GetPrevVisible(Bg);
    if (!this.DropFree && this.GetCDef(row) != row.Def.Name) return false;
    this.MoveRow(row, Bg, null, 1);
    return true;
};
_7m.prototype.ActionOutdent = function (CE) {
    var row = CE ? this.FRow: this.ARow;
    if (!row || row.Fixed || row.Space != null || row.Level <= 0) return false;
    var Bg = row.parentNode,
    EU = Bg.parentNode;
    if (!this.DropFree && this.GetCDef(EU) != row.Def.Name) return false;
    for (Bg = this.GetNextVisible(Bg); Bg && Bg.Level >= row.Level; Bg = this.GetNextVisible(Bg));
    this.MoveRow(row, EU, Bg, 1);
    return true;
};
_7m.prototype.Scrolled = function (OG) {
    if (this.Rendering || this.Loading || this.Ae) return;
    var Vw = Math.ceil(this.r.scrollLeft / this.v);
    if (Vw <= 2 && (!this.HV || Vw >= -2) || isNaN(Vw)) Vw = 0;
    var Dl = Math.ceil(this.q.scrollTop / this.u);
    if (Dl <= 2 || isNaN(Dl)) Dl = 0;
    if (Grids.OnScroll || Grids.OnScrollRow || Grids.OnScrollCol) {
        var Vx = this.GetScrollLeft(),
        Vy = this.GetScrollTop();
        if (Grids.OnScrollRow) {
            var Vz = this.V0();
        }
        if (Grids.OnScrollCol) {
            var V1 = this.V2();
        }
    }
    if (_7G) {
        this.b[1].firstChild.style.left = -Vw;
        this.c[1].firstChild.style.left = -Vw;
        this.d[1].firstChild.style.left = -Vw;
        if (!OG && parseInt(this.c[1].firstChild.style.top) == -Dl) return;
        for (var i = 0; i < 3; i++) if (this.c[i]) this.c[i].firstChild.style.top = -Dl;
    } else {
        if (!OG) {
            var Dk = parseInt(this.c[1].scrollTop),
            V3 = parseInt(this.c[1].scrollLeft);
            if (!V3) V3 = parseInt(this.b[1].scrollLeft);
            if (Dk == Dl && V3 == Vw) return;
        }
        this.b[1].scrollLeft = Vw;
        this.c[1].scrollLeft = Vw;
        if (_7AJ != 1) {
            var KL = this.XHeader;
            setTimeout(function () {
                _7Ao(KL.r1);
            },
            10);
        }
        this.d[1].scrollLeft = Vw;
        for (var i = 0; i < 3; i++) if (this.c[i]) this.c[i].scrollTop = Dl;
    };
    if (this.Pager.Visible && this.h) {
        var Height, Top;
        if (this.AllPages) {
            var BL = this.Sr(),
            CZ = this.Ab,
            DF = BL.length - 2;
            if (!CZ) {
                CZ = this.l.firstChild.offsetHeight;
                this.Ab = CZ;
            }
            if (DF == 1) {
                Top = BL[0] * CZ + BL[2] * CZ;
                Height = BL[1] * CZ;
            } else {
                Top = BL[0] * CZ + (1 - BL[1]) * CZ;
                Height = BL[DF] * CZ + BL[DF + 1] * CZ - Top;
            };
        } else {
            Height = this.l.firstChild.offsetHeight;
            Top = this.AP * Height;
        };
        if (! (Height > 0)) Height = 1;
        this.m.style.height = Math.floor(Height) + "px";
        if (isFinite(Top)) {
            this.m.style.marginTop = Math.floor( - this.l.offsetHeight + Top) + "px";
            this.h.scrollTop = Math.floor(Top - this.h.offsetHeight / 2 + Height / 2);
        }
    }
    if (Grids.OnScroll || Grids.OnScrollRow || Grids.OnScrollCol) {
        var V4 = this.GetScrollLeft(),
        V5 = this.GetScrollTop();
        if (Vx != V4 || Vy != V5) {
            if (Grids.OnScroll) Grids.OnScroll(this, V4, V5, Vx, Vy);
            if (Grids.OnScrollRow) {
                var V6 = this.V0();
                if (V5 < Vy) {
                    var R = Vz[0];
                    for (var i = 0; i < V6.length && V6[i] != R; i++) Grids.OnScrollRow(this, V6[i]);
                } else if (V5 > Vy) {
                    var R = Vz[Vz.length - 1];
                    for (var i = V6.length - 1; i >= 0 && V6[i] != R; i--) Grids.OnScrollRow(this, V6[i]);
                }
            }
            if (Grids.OnScrollCol) {
                var V7 = this.V2();
                if (V4 < Vx) {
                    var C = V1[0];
                    for (var i = 0; i < V7.length && V7[i] != C; i++) Grids.OnScrollCol(this, V7[i]);
                } else if (V4 > Vx) {
                    var C = V1[V1.length - 1];
                    for (var i = V7.length - 1; i >= 0 && V7[i] != C; i--) Grids.OnScrollCol(this, V7[i]);
                }
            }
        }
    }
    if (!OG) {
        this.HideHint();
        this.HideTip();
    }
    if (this.Paging) {
        this.Calculate(1, 1, 1, 1);
        this.ShowPages();
    }
};
_7m.prototype.SetScrollBars = function (V8) {
    if (this.c[1] == null || this.Rendering || this.p.parentNode == null) return true;
    if (!this.Ag && this.MainTag.offsetWidth) {
        _7AO();
        var CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) this.UpdateRowHeight(CE[i]);
    }
    this.Ag = this.MainTag.offsetWidth;
    this.Ah = this.MainTag.offsetHeight;
    if (!this.Ag && !this.Ah) return true;
    this.Rendering = true;
    if (this.RelHeight && this.Pager.Visible) {
        this.f.style.height = "10px";
    }
    if (V8 && _7S) {
        if (this.q.AF) {
            var CY = (parseInt(this.o.style.width) + this.q.AF) + "px";
            this.o.style.width = CY;
            this.RX.style.width = CY;
        }
        if (this.r.AG) {
            var CZ = (parseInt(this.p.style.height) + this.r.AG) + "px";
            this.p.style.height = CZ;
            this.RX.style.height = CZ;
        }
    }
    var H = this.XHeader.Visible ? this.b: this.c;
    if (!H[1].offsetWidth) {
        if (this.b[1].offsetWidth) H = this.b;
        else if (this.d[1].offsetWidth) H = this.d;
    }
    var V9 = (H[0] ? H[0].offsetWidth: 0) + (H[2] ? H[2].offsetWidth: 0);
    var WA = H[1].scrollWidth;
    var WB = this.Pager.Visible ? this.f.offsetWidth: 0;
    var WC = _7Y + this.q.AF;
    if (_7G || _7M || this.RelWidth || _7X) {
        WA = 0;
        for (var i = 0; i < this.ColNames[1].length; i++) {
            if (this.Cols[this.ColNames[1][i]].Visible) {
                WA += this.Cols[this.ColNames[1][i]].Width + this.CellSpacing;
            }
        }
        WA -= (H[0] ? this.CellSpacing: 0) + (H[2] ? this.CellSpacing: 0);
    }
    var WD = this.MainTag.clientWidth - V9 - WB - WC - this.AI - this.AF;
    var WE = (this.b[1].offsetHeight ? this.b[1].offsetHeight: 0) + (this.d[1].offsetHeight ? this.d[1].offsetHeight: 0);
    var WF = _7Z + this.r.AG;
    var WG = 0;
    for (var J = this.XS.firstChild; J; J = J.nextSibling) {
        if (!J.r1) continue;
        var Q5 = J.r1.parentNode.offsetHeight;
        if (Q5 && !J.Tag) {
            if (J.Space > 0 && J.Space <= 3) WE += Q5;
            else WG += Q5;
        }
    }
    if (_7U) this.c[1].scrollHeight;
    var WH = this.c[1].style.display == "none" ? 0 : this.c[1].scrollHeight + this.AG;
    if (_7F || _7M || _7H && (!_7O || _7P) || _7U || _7X) {
        var WI = 0;
        for (var H = this.c[1].firstChild; H; H = H.nextSibling) WI += H.offsetHeight;
        WI += this.AG;
        if (WH > WI) WH = WI;
    }
    if (_7G) WH = this.c[1].firstChild.offsetHeight;
    var WJ = 0;
    if (this.Top && this.e.previousSibling) WJ += this.e.previousSibling.offsetHeight;
    if (this.Bottom && this.e.nextSibling) WJ += this.e.nextSibling.offsetHeight;
    var WK = this.MainTag.clientHeight - WJ - WE - WF - this.AJ - WG;
    if (this.Pager.Visible && this.Kt(1) && this.Kt(1).parentNode.style.display == "none") WK = 1;
    var WL = "",
    WM = "";
    if (WD >= WA || this.NoScroll || this.NoHScroll) {
        WK += WF;
        WD = WA;
        WM = "none";
    }
    if ((WK >= WH || this.NoScroll || this.NoVScroll) && !this.ShowVScrollbar) {
        WD += WC;
        if (WD >= WA) {
            WD = WA;
            WM = "none";
        }
        WK = WH;
        WL = "none";
    }
    if (!WM && !WL && WA - WD <= WC && WH - WK <= WF && !this.ShowVScrollbar) {
        WM = "none";
        WL = "none";
        WK = WH;
        WD = WA;
    }
    if (this.RelWidth && !this.NoHScroll) {
        if (this.RelWidth && !WL && !this.ShowVScrollbar) {
            var CE = this.GetFixedRows(),
            KL = this.MainTag.clientHeight - WJ,
            WN = 0,
            Gl = new Array(),
            Df = 0;
            KL -= WH;
            if (this.XHeader.r1) KL -= this.XHeader.r1.offsetHeight;
            for (var J = this.XS.firstChild; J; J = J.nextSibling) CE[CE.length] = J;
            for (var i = 0; i < CE.length; i++) {
                if (!CE[i].r1) continue;
                if (CE[i].RelHeight && (CE[i].Kind == "User" || CE[i].Kind == "Space")) {
                    Gl[Gl.length] = CE[i];
                    WN += CE[i].RelHeight;
                    if (CE[i].MinHeight) KL -= CE[i].MinHeight;
                } else KL -= CE[i].r1.offsetHeight;
            }
            KL -= this.Tv + this.Tw + this.AJ;
            WL = KL >= WF ? "none": "";
        }
        if (!this.WL) this.WL = 1;
        else this.WL++;
        if (this.WO(WL) && this.WL < 3) {
            this.Rendering = false;
            return this.SetScrollBars();
        }
        this.WL = 0;
    }
    if (this.RelHeight && !this.NoVScroll) {
        if (!this.WM) this.WM = 1;
        else this.WM++;
        var CE = this.GetFixedRows(),
        KL = this.MainTag.clientHeight - WJ,
        WN = 0,
        Gl = new Array(),
        Df = 0;
        KL -= WH ? WH: 1;
        if (this.XHeader.r1) KL -= this.XHeader.r1.offsetHeight;
        for (var J = this.XS.firstChild; J; J = J.nextSibling) CE[CE.length] = J;
        for (var i = 0; i < CE.length; i++) {
            if (!CE[i].r1) continue;
            if (CE[i].RelHeight && (CE[i].Kind == "User" || CE[i].Kind == "Space")) {
                Gl[Gl.length] = CE[i];
                WN += CE[i].RelHeight;
                if (CE[i].MinHeight) KL -= CE[i].MinHeight;
            } else KL -= CE[i].r1.offsetHeight;
        }
        KL -= this.Tv + this.Tw + this.AJ;
        if (!WM) KL -= WF;
        var D3 = this.GetSections();
        if (WN) for (var i = 0; i < Gl.length; i++) {
            var CZ = Math.floor(KL * Gl[i].RelHeight / WN);
            if (CZ <= 0) CZ = 0;
            if (Gl[i].MinHeight) CZ += Gl[i].MinHeight;
            if (CZ != Gl[i].r1.offsetHeight) {
                if (Gl[i].Kind == "Space") {
                    var J = Gl[i].r1;
                    var Op = _7R ? J.parentNode.parentNode: J.parentNode;
                    if (CZ > 0) {
                        Op.style.display = "";
                        J.style.overflow = "hidden";
                        var RR = _7S ? 0 : J.offsetHeight - J.clientHeight;
                        J.style.height = (RR ? CZ - RR: CZ) + "px";
                    } else {
                        Op.style.display = "none";
                    };
                } else for (var k = D3[0]; k < D3[1]; k++) {
                    var J = Gl[i]["r" + k];
                    if (CZ > 0) {
                        J.style.overflow = "hidden";
                        var RR = _7S || !J.clientHeight ? 0 : J.offsetHeight - J.clientHeight;
                        J.style.height = (RR ? CZ - RR: CZ) + "px";
                        J.style.display = "";
                    } else {
                        J.style.display = "none";
                    }
                }
                Df = 1;
            }
        }
        if (Df && this.WM < 3) {
            this.Rendering = false;
            return this.SetScrollBars();
        }
        this.WM = 0;
    }
    if (WD < 40 && WA > WD && WA > 0) {
        var WP = new Array(),
        WQ = 0,
        WR = 0,
        Ot = false;
        for (var Kh in this.Cols) {
            var BY = this.Cols[Kh];
            if ((BY.K == 0 || BY.K == 2) && BY.Visible) {
                if (BY.CanResize) WR += BY.Width - (BY.MinWidth ? BY.MinWidth: 20);
                if (BY.CanHide) WQ += BY.Width;
                else if (BY.CanResize) WQ += BY.Width - (BY.MinWidth ? BY.MinWidth: 20);
                WP[WP.length] = BY;
            }
        }
        if (this.Pager.Visible) {
            var BY = this.Pager;
            if (BY.Visible) {
                if (BY.CanResize) WR += BY.Width - (BY.MinWidth ? BY.MinWidth: 20);
                if (BY.CanHide) WQ += BY.Width;
                else if (BY.CanResize) WQ += BY.Width - (BY.MinWidth ? BY.MinWidth: 20);
                WP[WP.length] = BY;
            }
        }
        if (WR + WD < _7Y + 40) {
            if (WQ + WD <= 0) {
                if (this.MainTag.offsetHeight > 0) this.ShowMessage(this.GetText("ExtentErr"), null, null, 1);
                this.e.style.visibility = "hidden";
                this.Rendering = false;
                return false;
            }
            for (var i = 0; i < WP.length; i++) {
                var BY = WP[i];
                if (BY && BY.CanHide) {
                    if (BY == this.Pager && this.SE) this.SE(0);
                    else {
                        BY.Visible = 0;
                        Ot = true;
                    };
                    WP[i] = null;
                    WD += BY.Width;
                    if (BY.CanResize) WR -= BY.Width - (BY.MinWidth ? BY.MinWidth: 20);
                    if (WD >= _7Y + 40) break;
                }
            }
            if (Ot) this.SetVPos();
            if (WD >= _7Y + 40) {
                this.Rendering = false;
                return Ot ? (this.Render(), true) : this.SetScrollBars();
            }
        }
        var WS = WR + WD < _7Y + 40 ? WR + WD: _7Y + 40;
        while (1) {
            for (var i = 0; i < WP.length; i++) {
                var BY = WP[i];
                if (!BY || !BY.CanResize) continue;
                var min = BY.MinWidth ? BY.MinWidth: 20;
                if (BY.Width - 5 <= min) {
                    WD += BY.Width - min;
                    BY.Width = min;
                    WP[i] = null;
                } else {
                    BY.Width -= 5;
                    WD += 5;
                };
                BY.Ci = 0;
                if (WD > WS) {
                    this.Rendering = false;
                    this.Render();
                    return;
                }
            }
        }
    }
    if (WD <= 0 || WK < 0) {
        if (this.MainTag.offsetHeight > 0) this.ShowMessage(this.GetText("ExtentErr"), null, null, 1);
        this.e.style.visibility = "hidden";
        this.Rendering = false;
        return false;
    } else if (this.e.style.visibility == "hidden") {
        this.e.style.visibility = "";
        this.HideMessage();
    }
    if (_7R && !this.o.parentNode) return;
    if (_7R && this.o.parentNode.style.display != WL) {
        for (var J = this.XS.firstChild; J; J = J.nextSibling) {
            if ((J.Space == 0 || J.Space >= 4) && J.r1) J.r1.parentNode.colSpan += WL == "none" ? -1 : 1;
        }
    }
    if (this.Pager.Visible && this.f.parentNode && this.h) {
        var WT = WM || _7H ? 0 : WF;
        this.f.parentNode.rowSpan = (WM ? 0 : 1) + (this.XF.firstChild ? 0 : -1) + this.XS.Space[3] - this.XS.Space[0];
        this.f.style.height = (WK + WE + WT) + "px";
        var WU = WK + WE + WT - this.f.firstChild.offsetHeight;
        this.h.style.height = (WU < 0 ? 0 : WU) + "px";
        this.h.firstChild.style.height = this.l.offsetHeight + "px";
        if (_7R) {
            var WV = this.Pager.Width - this.h.offsetWidth + this.h.clientWidth;
            if (WV < 15) WV = 15;
            this.h.firstChild.style.width = WV + "px";
        }
    }
    if (_7R) this.q.style.overflow = "hidden";
    var WW = WK - this.AD;
    if (WW < 0) WW = 0;
    for (var i = 0; i < 3; i++) if (this.c[i]) this.c[i].style.height = WW + "px";
    this.o.parentNode.style.display = WL;
    this.RX.parentNode.style.display = WL;
    if (WK || WE) {
        this.q.style.height = (WK + WE - this.q.AG) + "px";
        this.o.style.height = (WK + WE - (_7S ? 0 : this.q.AG)) + "px";
        if (WK) {
            this.u = this.q.offsetHeight / (WK);
            var F6 = WH * this.u;
            if (_7W) {
                var BL = new Array(),
                Bg = 0;
                while (F6 > 9999) {
                    BL[Bg++] = "<div style='height:9999px'>" + _7D + "</div>";
                    F6 -= 9999;
                }
                BL[Bg++] = "<div style='height:" + F6 + "px'>" + _7D + "</div>";
                this.q.innerHTML = BL.join("");
            } else this.s.style.height = F6 + "px";
        }
    }
    var WX = WD + (V8 ? 0 : this.AF);
    if (_7U) this.Dg();
    var CY = (WX - this.AE) + "px";
    this.b[1].style.width = CY;
    this.c[1].style.width = CY;
    this.d[1].style.width = CY;
    var WY = V9 + WX;
    var Q4 = V9 + WX;
    if (this.f && this.f.offsetWidth) Q4 += this.f.offsetWidth;
    if (!WL) Q4 += WC;
    for (var J = this.XS.firstChild; J; J = J.nextSibling) {
        if (!J.r1) continue;
        var It = J.Space > 0 && J.Space <= 3 ? WY: Q4;
        var WZ = J.AF;
        if (WZ == null) {
            var Bh = GetStyle(J.r1);
            WZ = _7Ah(Bh);
            if (Bh) {
                var Wa = parseInt(Bh.marginLeft),
                Wb = parseInt(Bh.marginRight);
                if (!_7H || !this.HV) WZ += (Wa ? Wa: 0) + (_7H ? (Wa ? Wa: 0) : (Wb ? Wb: 0));
            }
            if (!WZ) WZ = 0;
            J.AF = WZ;
        }
        It -= WZ;
        if (J.Tag) It += this.AI;
        J.r1.style.width = _7S ? (It + WZ) + "px": It + "px";
        if (J.RelWidth) this.B6(J, It);
    }
    if (this.Aj) {
        var Ck = this.Aj.r1;
        if (Ck.offsetWidth < Ck.scrollWidth) {
            if (Ck.style.overflow == "hidden") {
                Ck.style.overflow = "auto";
                if (!_7M) Ck.style.height = (Ck.offsetHeight + WF) + "px";
                this.Rendering = false;
                return this.SetScrollBars();
            }
        }
    }
    this.p.parentNode.parentNode.style.display = WM;
    this.r.style.width = (WX + (this.ShortHScroll ? 0 : V9) - this.r.AF) + "px";
    this.p.style.width = (WX + (this.ShortHScroll ? 0 : V9) - (_7S ? 0 : this.r.AF)) + "px";
    this.v = this.r.offsetWidth / (WD);
    this.t.style.width = Math.floor(WA * this.v) + "px";
    if (this.NoVScroll) {
        var Vx = this.e.offsetHeight + WJ,
        F6 = this.MainTag.scrollHeight;
        if (Vx > 0) {
            this.MainTag.style.height = ((Vx < F6 ? Vx: F6) + (_7S ? this.AL: 0)) + "px";
            if (_7S) this.MainTag.style.width = this.MainTag.offsetWidth + "px";
        }
    }
    if (this.NoHScroll) {
        var Wc = this.e.offsetWidth,
        Na = this.MainTag.scrollWidth;
        if (Wc > 0) {
            this.MainTag.style.width = ((Wc < Na ? Wc: Na) + (_7S ? this.AK: 0)) + "px";
        }
    }
    function Wd(M, We, T3) {
        if (!M) return;
        M.style.visibility = "";
        var Ch = We.offsetWidth;
        for (var i = 0, J = M.firstChild; J; J = J.nextSibling, i++) J.style.width = (Ch - T3[i]) + "px";
    };
    if (this.Top) Wd(this.e.previousSibling, this.e, this.T4);
    if (this.Bottom) Wd(this.e.nextSibling, this.e, this.T9);
    this.Ag = this.MainTag.offsetWidth;
    this.Ah = this.MainTag.offsetHeight;
    this.Rendering = false;
    if (this.MaxTagHeight) {
        if (this.NoVScroll) {
            if (this.MaxTagHeight && this.Ah > this.MaxTagHeight) {
                this.MainTag.style.height = this.MaxTagHeight + "px";
                this.NoVScroll = 0;
                return this.SetScrollBars();
            }
        } else if (this.Ah < this.MaxTagHeight && !WL) {
            this.MainTag.style.height = this.MaxTagHeight + "px";
            return this.SetScrollBars();
        } else {
            var CZ = this.e.offsetHeight;
            if (this.Top) CZ += this.e.previousSibling.offsetHeight;
            if (this.Bottom) CZ += this.e.nextSibling.offsetHeight;
            if (CZ < this.Ah) {
                this.NoVScroll = 1;
                return this.SetScrollBars();
            }
        }
    }
    this.Scrolled(1);
    if (this.ShowPages) this.ShowPages();
    if (_7R) this.q.style.overflowY = this.ShowVScrollbar ? "scroll": "auto";
    return true;
};
_7m.prototype.ScrollIntoView = function (row, BJ, Wf) {
    if (!row || row.Space != null) return;
    if (BJ == null) BJ = this.FCol;
    var C = BJ ? this.Cols[BJ] : null;
    var Wg = !row.Fixed;
    var Fx = C ? C.K == 1 : false;
    if (!Wg && !Fx) return;
    var a = C && !row.Page ? C.K: 1;
    var H = row.parentNode == this.XH ? this.b[a] : (row.parentNode == this.XF ? this.d[a] : this.c[a]);
    var left = this.GetScrollLeft();
    var top = this.GetScrollTop();
    var size = ElemToParent(this.GetCell(row, BJ), H);
    if (size == null) {
        size = ElemToParent(this.GetRow(row, 1), H);
        Fx = 0;
    }
    if (size == null) return;
    size[0] += left;
    size[1] += top;
    var Wh = _7S ? 0 : this.AG / 2,
    Wi = _7S ? 0 : this.AF / 2;
    if (Fx) {
        size[0] -= Wi;
        if (!this.HV) {
            var OU = size[0] + this.DS(row, BJ) - H.offsetWidth + Wi * 2;
            if (size[0] < left) {
                if (OU < left) this.r.scrollLeft = this.v * (size[0] - 2);
            } else {
                if (OU > size[0]) OU = size[0];
                if (OU > left) this.r.scrollLeft = this.v * OU;
            }
        }
    }
    if (Wg) {
        if (row.SO && this.AllPages) {
            for (var J = row.r1.Sa; J != row; J = J.nextSibling) size[1] += J.Height / this.PageLengthDiv;
        }
        if (Wf) size[1] += Wf * this.RowHeight;
        size[1] -= Wh;
        var RM = size[1] + (Wf != null ? this.RowHeight: this.GetRowHeight(row) / (row.Page ? this.PageLengthDiv: 1)) - H.offsetHeight + Wh * 2;
        if (size[1] < top) this.q.scrollTop = this.u * (size[1] - 2);
        else if (RM > top) this.q.scrollTop = this.u * (RM + (_7S ? 2 : 0));
    }
    this.Scrolled();
};
_7m.prototype.Sr = function () {
    if (!this.AllPages) {
        var BL = new Array();
        BL[0] = this.AP;
        BL[1] = 1;
        BL[2] = 0;
        return BL;
    }
    var Wj, Kg = this.GetScrollTop(),
    Wk = Kg + this.c[1].clientHeight,
    BL = new Array(),
    Bg = 0,
    Wl = 0;
    if (!Wk) Wk++;
    if (this.Paging == 1) {
        for (var J = this.XB.firstChild, B3 = 0; J; J = J.nextSibling, B3++) {
            BL[Bg++] = B3;
            BL[Bg++] = 1;
        }
        return BL;
    }
    for (var J = this.XB.firstChild, B3 = 0; J; J = J.nextSibling, B3++, Wl = Wj) {
        var Wm = J.r1 ? J.r1.offsetHeight: 0;
        if (J.SO) {
            if (Wl + Wm <= Kg) {
                B3 += J.r1.Count - 1;
                J = J.r1.Ri;
                Wj = Wl + Wm;
                continue;
            }
            Wm = J.Height / this.PageLengthDiv;
        }
        Wj = Wl + Wm;
        if (Wj <= Kg) {
            continue;
        }
        if (Wl >= Wk) break;
        BL[Bg++] = B3;
        if (Wl > Kg) {
            if (Wj <= Wk) BL[Bg++] = 1;
            else BL[Bg++] = (Wk - Wl) / Wm;
        } else {
            if (Wj <= Wk) {
                BL[Bg++] = (Wj - Kg) / Wm;
                if (this.XB.childNodes.length == 1) BL[Bg++] = (Kg - Wl) / Wm;
            } else {
                BL[Bg++] = (Wk - Kg) / Wm;
                BL[Bg++] = (Kg - Wl) / Wm;
            }
        }
    }
    return BL;
};
_7m.prototype.St = function (row) {
    if (!this.AllPages) return row.r1 != null;
    if (this.Paging == 1) return 1;
    var top = this.GetScrollTop(),
    CZ;
    var y = ElemToParent(this.GetRow(row, 1), this.c[1])[1];
    y += top;
    if (row.SO) {
        for (var J = row.r1.Sa; J != row; J = J.nextSibling) y += J.Height / this.PageLengthDiv;
        CZ = row.Height / this.PageLengthDiv;
    } else CZ = this.SQ(row, false);
    if (y < top) return y + CZ > top ? 2 : 0;
    var Wn = top + this.c[1].clientHeight;
    if (y > Wn - CZ) return y > Wn ? 0 : 2;
    return 1;
};
_7m.prototype.V0 = function () {
    var top = this.GetScrollTop() + 2;
    for (var Bc = this.XB.firstChild; Bc; Bc = Bc.nextSibling) {
        var Wm = Bc.r1 ? Bc.r1.offsetHeight: 0;
        if (Bc.SO) {
            if (top - Wm >= 0) {
                Bc = Bc.r1.Ri;
                top -= Wm;
                continue;
            }
            Wm = Bc.Height / this.PageLengthDiv;
        }
        if (top - Wm < 0) break;
        top -= Wm;
    }
    for (var J = this.GetFirstVisible(Bc); J; J = this.GetNextVisible(J)) {
        var Wm = this.GetRowHeight(J);
        if (top - Wm < 0) break;
        top -= Wm;
    }
    var BL = [],
    Bg = 0;
    top += this.c[1].clientHeight - 4;
    for (; J; J = this.GetNextVisible(J)) {
        if (J.r1) BL[Bg++] = J;
        var Wm = this.GetRowHeight(J);
        if (top - Wm < 0) break;
        top -= Wm;
    }
    return BL;
};
_7m.prototype.V2 = function () {
    var left = this.GetScrollLeft() + 2;
    var C = this.ColNames[1],
    BL = [],
    Bg = 0;
    for (var i = 0; i < C.length; i++) {
        var BY = this.Cols[C[i]];
        if (!BY.Visible) continue;
        if (left < BY.Width) break;
        left -= BY.Width;
    }
    left += this.c[1].clientWidth - 4;
    for (; i < C.length; i++) {
        var BY = this.Cols[C[i]];
        if (!BY.Visible) continue;
        BL[Bg++] = C[i];
        if (left < BY.Width) break;
        left -= BY.Width;
    }
    return BL;
};
_7m.prototype.Dg = function () {
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.r1) J.r1.style.width = "10px";
    if (!_7F) {
        var Bg = this.p.parentNode;
        if (Bg) Bg.parentNode.style.display = "none";
    }
};
_7m.prototype.IP = function (row) {
    if (row && (row.Fixed == "Head" || row.Space <= 1) || row == this.XHeader) this.AS = 1;
    else if (row && (row.Fixed == "Foot" || row.Space >= 2) || this.ASec == -3 || this.ASec == -4) this.AS = 2;
    else this.AS = null;
};
_7m.prototype.OF = function (BJ, left) {
    this.JY = null;
    var Wo = this.GetCell(this.XHeader, BJ),
    Wp = this.b[1].offsetWidth;
    if (Wo) {
        Wo = ElemToParent(Wo, this.b[1])[0] + left;
        if (Wo >= 0 && Wo <= 10) this.JY = -1;
        else if (Wo >= Wp - 10 && Wo <= Wp) this.JY = 1;
    }
};
function GetS(row, UV) {
    return Get(row, UV) + "";
};
function GetLowerS(row, UV) {
    return GetLower(row, UV) + "";
};
_7m.prototype.SearchRows = function (action, Dj) {
    if (this.Paging == 3 && !(this.OnePage & 4) && !this.CK()) return;
    if (action == "Find") {
        this.DoSearch(action, Dj);
    } else if (this.RowCount < this.SynchroCount) this.DoSearch(action, Dj);
    else {
        this.ShowMessage(this.GetText("Search"));
        var T = this;
        setTimeout(function () {
            T.DoSearch(action, Dj);
            T.HideMessage();
        },
        10);
    }
};
_7m.prototype.SearchInRow = function (J, BL, R, Tl, Cols) {
    for (var i = 0; i < BL.length; i++) {
        for (var j = 0; j < Cols.length; j++) {
            var BY = Cols[j],
            Cs = this.GetType(J, BY);
            if (Cs == "Int" || Cs == "Float") {
                Tl[i] = BL[i] - 0 == this.GetValue(J, BY);
            } else {
                var S = Get(J, BY + "FilterValue");
                if (S == null) S = this.GetString(J, BY, 2);
                if (Grids.OnGetFilterValue) S = Grids.OnGetFilterValue(this, J, BY, S);
                if (!S) Tl[i] = BL[i] == "#";
                else Tl[i] = BL[i] ? (R ? S.search(R[i]) >= 0 : S.indexOf(BL[i]) >= 0) : 0;
            };
            if (Tl[i]) break;
        }
    }
};
_7m.prototype.SearchInCell = function (J, BL, R, Tl, BY) {
    for (var i = 0; i < BL.length; i++) {
        var Cs = this.GetType(J, BY);
        if (Cs == "Int" || Cs == "Float") {
            Tl[i] = BL[i] - 0 == this.GetValue(J, BY);
        } else {
            var S = Get(J, BY + "FilterValue");
            if (S == null) S = this.GetString(J, BY, 2);
            if (Grids.OnGetFilterValue) S = Grids.OnGetFilterValue(this, J, BY, S);
            if (!S) Tl[i] = BL[i] == "#";
            else Tl[i] = BL[i] ? (R ? S.search(R[i]) >= 0 : S.indexOf(BL[i]) >= 0) : 0;
        }
    }
};
_7m.prototype.GI = function (Cr, EI) {
    var Method = this.SearchMethod;
    if (!Method) {
        if (Cr.search(/[\(\)\=\!\<\>]/) >= 0) Method = 2;
    }
    var Wq = "";
    var Wr = this.SearchDefs;
    if (Wr) {
        Wr = Wr.split(',');
        for (var i = 0; i < Wr.length; i++) if (this.Def[Wr[i]]) Wq += (Wq ? ":1,": "") + Wr[i];
        if (Wq) Wq = "if(!{" + Wq + ":1}[Row.Def.Name]) return -1;";
    }
    if (!Method || Method == 2) {
        function Ws(BY) {
            return BY >= 48 && BY <= 57 || BY >= 65 && BY <= 90 || BY >= 97 && BY <= 122 || BY == 46 || BY == 44 || BY == 95 || BY == 34 || BY == 39 || BY == 35 || BY == 36 || BY == 64 || BY >= 128
        };
        var BL = new Array(),
        H = new Array(),
        C = new Array();
        for (var Bg = 0, i = 0, Cs = -1; Cr; i++) {
            var BY = Cr.charCodeAt(i);
            if (!BY) {
                if (Cr && Cr.charCodeAt(0) > 32) BL[Bg++] = Cr;
                break;
            }
            if (BY <= 32 && !i) {
                Cr = Cr.slice(1);
                i = -1;
                continue;
            }
            var x = Ws(BY);
            if (!i) {
                if (BY == 34 || BY == 39) {
                    for (i++;; i++) {
                        var Ck = Cr.charCodeAt(i);
                        if (!Ck) break;
                        if (Ck == BY) {
                            if (Cr.charCodeAt(i + 1) == BY) Cr = Cr.slice(0, i) + Cr.slice(i + 1);
                            else break;
                        }
                    }
                }
                Cs = x;
            } else if (Cs && !x || !Cs && x || BY <= 32) {
                BL[Bg++] = Cr.slice(0, i);
                Cr = Cr.slice(i);
                i = -1;
            }
        }
        function Wt(D, Wh, Wu, Wv) {
            D = D.split(",");
            for (var i = 0; i < D.length; i++) {
                var Ww = D[i].toLowerCase();
                for (var j = 0; j < BL.length; j++) {
                    var Bh = BL[j].toLowerCase();
                    if (Ww == Bh && H[j] == null && (!BL[j + 1] || BL[j + 1].charAt(0) != '(')) {
                        H[j] = Wh;
                        C[j] = Wu;
                    }
                    if (Ww.indexOf(Bh) == 0) {
                        for (var x = j + 1; Bh.length < Ww.length && BL[x]; x++) Bh += ' ' + BL[x].toLowerCase();
                        if (Ww == Bh && H[j] != "" && (H[j] == null || x - j > 1)) {
                            H[j] = Wh;
                            if (Wu) BL[j] = Wu;
                            while (x > j + 1) {
                                H[--x] = "";
                                if (!Wv) C[x] = "";
                            }
                        }
                    }
                }
            }
        };
        var BV = EI & 4 ? "Get": "GetLower";
        for (var BY in this.Cols) {
            var D = this.Cols[BY].SearchNames;
            if (!D) D = this.XHeader[BY];
            Wt(D, BV + "(Row,'" + BY + "')", null, 1);
        }
        Wt(this.GetText("Starts"), "|*");
        Wt(this.GetText("Ends"), "*|");
        Wt(this.GetText("Contains"), "*|*");
        if (!Method) {
            for (var i = 0, a = 0; i < H.length; i++) if (H[i]) a++;
            if (a >= 2) Method = 2;
            else Method = 1;
        }
        if (Method == 2) {
            Wt(this.GetText("And"), "&&");
            Wt(this.GetText("Or"), "||");
            Wt(this.GetText("Not"), "!");
            Wt("<>", "!=");
            for (var i = 0; i < BL.length; i++) {
                var Bh = BL[i],
                BY = Bh.charCodeAt(0);
                if (BY == 34 || BY == 39) {
                    if (! (EI & 4)) {
                        function Rv(Ce) {
                            var a = H[Ce] ? H[Ce] : BL[Ce];
                            return a == "==" || a == "!=" || a == ">" || a == ">=" || a == "<" || a == "<=" || a == "|*" || a == "*|" || a == "*|*";
                        };
                        if (Rv(i - 1) || Rv(i + 1)) BL[i] = BL[i].toLowerCase();
                    }
                    continue;
                }
                if (H[i] != null) continue;
                var Ce = Bh.indexOf('=');
                if (Ce >= 0) {
                    var DP = Ce ? Bh.charAt(Ce - 1) : null;
                    if (DP == '=' || DP == '>' || DP == '<' || Bh.charAt(Ce + 1) == '=') continue;
                    H[i] = Bh.slice(0, Ce) + "=" + Bh.slice(Ce);
                } else if (BY >= 48 && BY <= 57) {
                    if (Bh.indexOf(',') >= 0) H[i] = Bh.replace(',', '.');
                } else if (Ws(BY)) {
                    var j = i;
                    for (i++; i < BL.length; i++) {
                        if (!Ws(BL[i].charCodeAt(0)) || H[i] != null) break;
                        Bh += ' ' + BL[i];
                    }
                    if (BL[i] && BL[i].charCodeAt(0) == 40) continue;
                    if (Bh && !(EI & 4)) Bh = Bh.toLowerCase();
                    if (Bh.indexOf('"') < 0) Bh = '"' + Bh + '"';
                    else if (Bh.indexOf("'") < 0) Bh = "'" + Bh + "'";
                    else Bh = '"' + Bh.replace('"', '""') + '"';
                    H[j] = Bh;
                    C[j] = Bh;
                    for (j++; j < i; j++) {
                        H[j] = "";
                        C[j] = "";
                    }
                    i--;
                }
            }
            for (var i = 0; i < BL.length; i++) {
                if (BL[i].search(/^\'?\"?\d{1,2}[\/\-\:\.]\d{1,2}([\/\-\:\.]\d{1,4})?(..?\d{1,2}[\:\-]\d{1,2}([\-\:]\d{1,2})?)?\'?\"?$/) >= 0 && BL[i].search(/^\d*.\d*$/) < 0) {
                    var Bh = BL[i];
                    if (Bh.charAt(0) == '"' || Bh.charAt(0) == "'") Bh = Bh.slice(1, Bh.length - 1);
                    H[i] = StringToDate(Bh, this.SearchDateFormat).getTime();
                    var BY = Bh.charAt(0);
                    if (BY != '"' && BY != "'") Bh = '"' + Bh + '"';
                    C[i] = Bh;
                }
                if (BL[i] == '/' || BL[i] == '-' || BL[i] == ':') {
                    if (!i || H[i - 1] != null || H[i + 1] != null || isNaN(BL[i - 1] - 0) || isNaN(BL[i + 1] - 0)) continue;
                    var j = BL[i + 2] == BL[i] && !isNaN(BL[i + 3]) && H[i + 3] == null ? i + 4 : i + 2,
                    Wx = null;
                    if (BL[j + 1] == ':' && H[j] == null && H[j + 2] == null && !isNaN(BL[j] - 0) && !isNaN(BL[j + 2] - 0)) {
                        Wx = j;
                        j = BL[j + 3] == ':' && !isNaN(BL[j + 4]) && H[j + 4] == null ? j + 5 : j + 3;
                    }
                    for (var k = i, TG = ""; k < j; k++) {
                        if (k == Wx) TG += ' ';
                        TG += BL[k];
                        H[k] = "";
                        C[k] = ""
                    }
                    H[i - 1] = StringToDate(BL[i - 1] + TG, this.SearchDateFormat).getTime();
                    C[i - 1] = '"' + BL[i - 1] + TG + '"';
                    if (BL[i] == ':') {
                        if (i > 1 && H[i - 2] - 0) {
                            var BY = BL[i - 2].charAt(0);
                            if (BY != '"' && BY != "'") {
                                H[i - 2] = StringToDate(BL[i - 2] + " " + BL[i - 1] + TG, this.SearchDateFormat).getTime();
                                H[i - 1] = "";
                                C[i - 2] = '"' + BL[i - 2] + " " + BL[i - 1] + TG + '"';
                                C[i - 1] = "";
                            }
                        }
                    }
                }
            }
            for (var i = 0; i < BL.length; i++) {
                var CC = null;
                switch (H[i]) {
                case '|*':
                    H[i] = '.indexOf(';
                    CC = "==0";
                    if (i && H[i - 1] && H[i - 1] == '!') {
                        H[i - 1] = "";
                        CC = "!=0";
                    }
                    if (H[i + 1]) H[i + 1] += ")" + CC;
                    else H[i + 1] = BL[i + 1] + ")" + CC;
                    break;
                case '*|':
                    H[i] = '.lastIndexOf(';
                    CC = "==";
                    if (i && H[i - 1] && H[i - 1] == '!') {
                        H[i - 1] = "";
                        CC = "!=";
                    }
                    var Wy = H[i - 1] ? H[i - 1] : BL[i - 1],
                    Wz = H[i + 1] ? H[i + 1] : BL[i + 1];
                    H[i + 1] = Wz + ")" + CC + "(" + Wy + ".length-(" + Wz + "+'').length)";
                    break;
                case '*|*':
                    H[i] = '.indexOf(';
                    CC = ">=0";
                    if (i && H[i - 1] && H[i - 1] == '!') {
                        H[i - 1] = "";
                        CC = "<0";
                    }
                    if (H[i + 1]) H[i + 1] += ")" + CC;
                    else H[i + 1] = BL[i + 1] + ")" + CC;
                    break;
                };
                if (CC && H[i - 1]) {
                    H[i - 1] = (H[i - 1] + "").replace("Get(", "GetS(").replace("GetLower(", "GetLowerS(");
                }
            }
            var S = "",
            W0 = "";
            for (var i = 0; i < BL.length; i++) {
                S += (i ? " ": "") + (H[i] != null ? H[i] : BL[i]);
                W0 += (i && C[i] != "" ? " ": "") + (C[i] != null ? C[i] : BL[i]);
            }
            this.SearchExpression = W0;
            if (Grids.OnRowSearch) S = Wq + "var found=(" + S + "); return Grids.OnRowSearch(" + this.Z + ",Row,null,found,arguments.callee,UserVal);";
            else S = Wq + "return " + S + ";";
        }
    }
    if (Method == 1) {
        Cr = this.SearchExpression;
        var S = '([^\\"\\s]*)?\\s*(\"([^\\"]*)\")?\\s*';
        for (var i = 0; i < 4; i++) S += S;
        S = "^\\s*" + S + "$";
        var P = "",
        F = new Array(),
        BL = new Array(),
        Bg = 0;
        if (Cr) {
            Cr = Cr.match(S);
            for (var i = 1; i < Cr.length; i++) if (Cr[i] && Cr[i].charAt(0) != '"') F[Bg++] = Cr[i];
            for (var i = 0, x = 0; i < Bg; i++) {
                if (F[i] == "OR") continue;
                if (i) P += F[i - 1] == "OR" ? "||": "&&";
                if (F[i + 1] == "OR" && (!i || F[i - 1] != "OR")) P += "(";
                if (F[i].charAt(0) == '-') {
                    P += "!";
                    F[i] = F[i].slice(1);
                }
                P += "Z[" + x + "]";
                BL[x++] = F[i];
                if (i && F[i + 1] != "OR" && F[i - 1] == "OR") P += ")";
            }
        }
        var Cols = this.SearchCols;
        if (Cols) {
            Cols = Cols.split(",");
            var W1 = new Object();
            for (var i = 0; i < Cols.length; i++) W1[Cols[i]] = 1;
        }
        var W2 = new Object();
        Cols = new Array();
        for (var BY = this.GetFirstCol(); BY; BY = this.GetNextCol(BY)) {
            if (this.Cols[BY].CanSearch && (!W1 || W1[BY])) {
                W2[BY] = 1;
                Cols[Cols.length] = '\"' + BY + '\"';
            }
        }
        if (EI & 2) {
            for (var BY in this.Cols) {
                if (this.Cols[BY].CanSearch && !W2[BY]) {
                    Cols[Cols.length] = '\"' + BY + '\"';
                }
            }
        }
        var S = Wq;
        S += "var Z=new Array();";
        S += "var A=[";
        for (var i = 0; i < BL.length; i++) S += (i ? ",": "") + "\"" + BL[i].replace("\"", "\\\"") + "\"";
        S += "];";
        if (EI & 4) S += "var R=null;";
        else {
            S += "var R=[";
            for (var i = 0; i < BL.length; i++) S += (i ? ",": "") + "/" + BL[i].replace(/[\\\^\$\*\+\?\.\{\}\[\]\(\)\|\-\{\}\/]/g, "\\$&") + "/i";
            S += "];";
        };
        if (EI & 1) {
            S += "var Cols=[" + Cols.join(",") + "],i=0;";
            S += "if(Col){";
            S += "for(i=0;i<Cols.length;i++) if(Cols[i]==Col) { i++; break; }";
            S += "}";
            S += "for(;i<Cols.length;i++){";
            S += this.Z + ".SearchInCell(Row,A,R,Z,Cols[i]);";
            if (Grids.OnRowSearch) S += "var found = Grids.OnRowSearch(" + this.Z + ",Row,Cols[i]," + P + ",arguments.callee,UserVal);";
            else S += "var found= " + P + ";";
            S += "if(found==-1) return -1;";
            S += "if(found==1) return Cols[i];";
            S += "if(found) return found;";
            S += "} return 0;";
        } else {
            S += this.Z + ".SearchInRow(Row,A,R,Z,[" + Cols.join(",") + "]);";
            if (Grids.OnRowSearch) S += "return Grids.OnRowSearch(" + this.Z + ",Row,null," + P + ",arguments.callee,UserVal);";
            else S += "return " + P + ";";
        }
    }
    try {
        var CE = new Function("Row", "Col", "UserVal", S);
        return [CE, Method];
    } catch(B) {
        return null;
    }
};
_7m.prototype.DoSearch = function (action, Dj) {
    if (!this.Searching) return;
    if (action == "Last") action = this.W3;
    if (Grids.OnSearch && Grids.OnSearch(this, action, !Dj)) return;
    if (action == "Help") {
        alert(this.GetAlert("SearchHelp"));
        return;
    }
    if (action == "Refresh") {
        var Ix = this.SearchAction;
        if (Ix) {
            this.DoSearch("Clear", Ix == "Filter" ? Dj: 1);
            this.DoSearch(Ix, Dj);
        } else this.SaveCfg();
        return;
    }
    var Cr = this.SearchExpression;
    if (this.W4 && Cr != this.W5) {
        this.GK++;
        this.W4 = 0;
    }
    this.W5 = Cr;
    var W6 = this.SearchAction;
    if (!Cr) action = "Clear";
    if (this.SearchAction != action) {
        this.W7(action, Dj);
        this.SearchAction = action;
    }
    if (action == "Clear") {
        this.SearchAction = "";
        this.Calculate(!Dj, 1, 1);
        this.SaveCfg();
        return;
    }
    this.W3 = this.SearchAction;
    if (this.Paging == 3 && (!(this.OnePage & 4) || this.AllPages)) {
        this.Calculate(!Dj, 1, 1);
        this.SaveCfg();
        this.ReloadBody(null, 0, "Search");
        if (Grids.OnSearchFinish) Grids.OnSearchFinish(this, action, !Dj);
        return 1;
    }
    var EI = this.SearchType;
    if (EI == null) EI = 176;
    var CE = this.GI(Cr, EI);
    if (!CE) {
        alert(this.GetAlert("SearchError"));
        this.SearchAction = "";
        return;
    }
    var Method = CE[1];
    CE = CE[0];
    var Cu = 0;
    if (this.Searched) switch (action) {
    case "Filter":
        this.GE = CE;
        var Dh = this.Filtered;
        this.Filtered = true;
        if (this.Kk) Cu = this.Kk(Dj);
        else _7w("Filter");
        this.Filtered = Dh;
        if (this.Paging) this.ShowPages();
        break;
    case "Select":
        for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
            if (!Is(J, "CanSelect")) continue;
            var GJ = CE(J);
            GJ = typeof(GJ) == "string" || GJ > 0;
            if (J.Selected && !GJ) this.SelectRow(J);
            else if (!J.Selected && GJ) {
                this.SelectRow(J);
                if (EI & 8) this.ExpandParents(J);
            }
        }
        break;
    case "Mark":
        this.W4 = 1;
        if (!this.GK) this.GK = 1;
        var Color = this.Al["Found" + this.GK];
        if (!Color) {
            Color = this.Al["Found1"];
            this.GK = 1;
        }
        if (EI & 1 && Method == 1) for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
            var GJ = CE(J);
            while (GJ && typeof(GJ) == "string") {
                J[GJ + "MarkColor"] = Color;
                this.ColorCell(J, GJ);
                if (EI & 8) this.ExpandParents(J);
                GJ = CE(J, GJ);
            }
        } else for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
            var GJ = CE(J) > 0;
            if (GJ) {
                J.Dy = Color;
                this.ColorRow(J);
                if (EI & 8) this.ExpandParents(J);
            }
        }
        break;
    case "Find":
        var start = this.FRow && !this.FRow.Fixed && (!(EI & 64) || W6 == "Find") ? this.FRow: null;
        if (this.FRow && this.FRow.Space != null) this.Focus();
        if (EI & 1 && Method == 1 && start && this.FCol) {
            var GJ = CE(start, this.FCol);
            if (typeof(GJ) == "string") {
                this.Focus(start, GJ, null, 1);
                break;
            }
        }
        var J = start ? this.GetNextVisible(start) : this.GetFirstVisible();
        if (J) while (J != start) {
            var GJ = CE(J);
            if (GJ) {
                if (typeof(GJ) == "string") {
                    this.Focus(J, GJ, null, 1);
                    break;
                } else if (GJ > 0) {
                    this.Focus(J, this.FCol, null, 1);
                    break;
                } else GJ = 0;
            }
            J = this.GetNextVisible(J);
            if (!J) {
                if (start && EI & 16) {
                    if (EI & 32) {
                        if (!confirm(this.GetAlert("SearchStart"))) {
                            GJ = 1;
                            break;
                        }
                    }
                    J = this.GetFirstVisible();
                } else break;
            }
        }
        if (!GJ && EI & 128) alert(this.GetAlert("NotFound"));
        break;
    };
    this.Calculate(!Dj, 1, 1);
    this.SaveCfg();
    if (Grids.OnSearchFinish) Grids.OnSearchFinish(this, action, !Dj);
    return Cu;
};
_7m.prototype.W7 = function (action, Dj) {
    if (this.Searched) switch (this.SearchAction) {
    case "Filter":
        this.GE = null;
        var Dh = this.Filtered;
        this.Filtered = true;
        if (this.Kk) this.Kk(Dj);
        this.Filtered = Dh;
        if (this.Paging) this.ShowPages();
        break;
    case "Select":
        for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
            if (J.Selected && Is(J, "CanSelect")) this.SelectRow(J);
        }
        break;
    case "Find":
        if (action == "Clear") this.Focus();
        break;
    case "Mark":
        for (var J = this.GetFirstVisible(); J; J = this.GetNextVisible(J)) {
            if (J.Dy) {
                J.Dy = null;
                this.ColorRow(J);
            }
            for (var BY in this.Cols) {
                if (J[BY + "MarkColor"]) {
                    J[BY + "MarkColor"] = null;
                    this.ColorCell(J, BY);
                }
            }
        }
        this.GK = 0;
        this.W4 = 1;
        break;
    }
};
_7m.prototype.ActionSearchOff = function () {
    if (!this.Searching || !this.Searched) return false;
    this.W7();
    this.Searched = false;
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Search") this.K2(J);
    this.SaveCfg();
    return true;
};
_7m.prototype.ActionSearchOn = function () {
    if (!this.Searching || this.Searched) return false;
    this.Searched = true;
    if (this.SearchAction) this.DoSearch(this.SearchAction);
    for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Search") this.K2(J);
    this.SaveCfg();
    return true;
};
_7m.prototype.UseCharCodes = function (DD, BJ) {
    var B3 = "",
    BX = this.Cols[BJ].CharCodes;
    DD += "";
    if (!BX) return DD;
    for (var i = 0; i < DD.length; i++) {
        var x = BX[DD.charAt(i)];
        B3 += x ? x: DD.charAt(i);
    }
    return B3;
};
_7m.prototype.Sm = function (W8, r1, r2) {
    var DK = this.SortTypes,
    BJ = this.SortCols[W8],
    W9 = DK[W8] & 1;
    var Lq = this.GetValue(r1, BJ),
    Lr = this.GetValue(r2, BJ);
    if ((DK[W8] & 30 || this.Cols[BJ].CharCodes) && (this.Cols[BJ].Type == "Text" || this.Cols[BJ].Type == "Lines")) {
        if (Lq == null) return Lr == null ? 0 : -1;
        if (Lr == null) return 1;
        var Cu = this.Cols[BJ].CharCodes && this.UseCharCodes ? CompareStrings(this.UseCharCodes(Lq, BJ), this.UseCharCodes(Lr, BJ), DK[W8]) : CompareStrings(Lq, Lr, DK[W8]);
        return W9 ? -Cu: Cu;
    } else {
        if (Lq < Lr) return W9 ? 1 : -1;
        if (Lq > Lr) return W9 ? -1 : 1;
    };
    return 0;
};
_7m.prototype.XA = function (r1, r2) {
    for (var i = 0; i < this.AX; i++) {
        var x = this.Sm(i, r1, r2);
        if (x) return x;
    }
    return 0;
};
_7m.prototype.SortRow = function (row, BJ, Bx) {
    if (!this.Sorted || !this.AutoSort || !this.Sorting || row.Fixed) return;
    if (BJ && BJ != "Panel") {
        var JE = false;
        for (var i = 0; i < this.AX; i++) if (this.SortCols[i] == BJ) {
            JE = true;
            break;
        }
        if (!JE) return;
    }
    var EU = row.parentNode,
    J;
    if (EU.Page) {
        if (!this.XHeader.CanSort) return;
        for (J = this.GetFirst(); J; J = this.GetNextSibling(J)) if (this.XA(row, J) < 0) break;
    } else {
        if (!Get(EU, "CanSort")) return;
        for (J = EU.firstChild; J; J = J.nextSibling) if (this.XA(row, J) < 0) break;
    };
    if (this.MoveRow) this.MoveRow(row, EU, J, Bx);
    if (Bx) this.ScrollIntoView(row, BJ);
};
function _7Az(Bb) {
    if (Bb > 0) {
        var XC = Math.log(Bb) / 100;
        return XC >= 0 ? XC + "": "/" + (10 + XC);
    }
    if (Bb < 0) {
        var XC = Math.log( - Bb) / 100;
        return XC > 0 ? "-/" + (10 - XC) : "-" + ( - XC);
    }
    if (Bb == "0") return ".";
    return Bb + "";
};
function _7A0(DD) {
    if (DD === "") return _7A0.G9;
    var Bh = "",
    BN = DD.length;
    if (BN > 20) BN = 20;
    for (var i = 0; i < BN; i++) {
        Bh += String.fromCharCode(65535 - DD.charCodeAt(i));
    }
    return Bh;
};
_7A0.G9 = String.fromCharCode(65530);
_7m.prototype.XD = function (J, Ca, XE, EI) {
    var Bh = "";
    var Pr = J.SortPos;
    if (Pr == null) Pr = J.Def.SortPos;
    if (Pr) Bh = EI ? String.fromCharCode(65535 - Pr) : String.fromCharCode(Pr);
    for (var i = 0; i < this.AX; i++) {
        var BZ, Kh, BX = J.Spanned && Is(J, "SortSpan") ? this.Dq(J, this.SortCols[i]) : this.SortCols[i],
        XG = Ca[i];
        if (XG & 1) {
            Kh = BX + "SortDescValue";
            BZ = J[Kh];
            if (BZ == null) BZ = J.Def[Kh];
            if (BZ == null) {
                Kh = BX + "SortValue";
                BZ = J[Kh];
                if (BZ == null) BZ = J.Def[Kh];
            }
        } else {
            Kh = BX + "SortValue";
            BZ = J[Kh];
            if (BZ == null) BZ = J.Def[Kh];
        };
        if (BZ == null) {
            if (XE[i]) {
                BZ = J[BX];
                if (BZ == null) BZ = J.Def[BX];
                if (BZ && typeof(BZ) == "string") {
                    var Wg = J[BX + "Range"];
                    if (Wg == null) Wg = J.Def[BX + "Range"];
                    if (Wg == null) Wg = this.Cols[BX]["Range"];
                    if (Wg) {
                        BZ = BZ.split(this.Lang.Format.ValueSeparator);
                        BZ = XG & 1 ? BZ[BZ.length - 1] : BZ[0];
                        var Kc = BZ.indexOf(this.Lang.Format.RangeSeparator);
                        if (Kc >= 0) BZ = XG & 1 ? BZ.slice(Kc + 1) - 0 : BZ.slice(0, Kc) - 0;
                        else BZ -= 0;
                    }
                }
            } else BZ = this.GetString(J, BX, 2);
        }
        if (Grids.OnGetSortValue) BZ = Grids.OnGetSortValue(this, J, BX, BZ, XG & 1);
        BZ = BZ == null ? "": BZ + "";
        if (this.Cols[BX].CharCodes) BZ = this.UseCharCodes(BZ, BX);
        if (XG & 6) BZ = XG & 2 ? BZ.toLocaleLowerCase() : BZ.toLowerCase();
        if (XG & 8) BZ = BZ.replace(this.Cols[BX].WhiteChars, "");
        if (EI == (XG & 1)) {
            Bh += (XE[i] ? _7Az(BZ) : BZ) + " ";
        } else {
            Bh += (XE[i] && (BZ - 0 || BZ == "0") ? _7Az( - BZ) : _7A0(BZ)) + " ";
        }
    }
    return Bh;
};
_7m.prototype.XI = function (XJ, SortType, EU) {
    var BQ = this.AX;
    for (var i = 0; i < BQ; i++) {
        var C = this.Cols[this.SortCols[i]];
        if (!C) return;
        var B3 = Get(EU, this.SortCols[i] + "NumberSort");
        XJ[i] = B3 == null ? C.Type == "Int" || C.Type == "Float" || C.Type == "Date": B3;
        SortType[i] = (C.SortType ? C.SortType: 0) | (this.SortTypes[i] ? this.SortTypes[i] : 0);
    }
    var dir = 0;
    for (var i = 0; i < BQ; i++) {
        if (!XJ[i]) dir += SortType[i] & 1 ? -1 : 1;
    }
    if (dir) dir = dir < 0 ? 1 : 0;
    else dir = SortType[0] & 1;
    return dir;
};
_7m.prototype.Pm = function (EU, Bx, UA) {
    var XK = false,
    BQ = this.AX;
    if (!BQ) return false;
    var XJ = new Array(),
    SortType = new Array();
    var dir = this.XI(XJ, SortType, this.XHeader);
    var BL = new Array(),
    H = new Array(),
    Bg = 0,
    J = EU.firstChild;
    if (J && J.Dx) {
        for (; J; J = J.nextSibling) {
            for (var Sv = J.firstChild; Sv; Sv = Sv.nextSibling) {
                if (!UA && Sv.firstChild && this.Pm(Sv, Bx)) XK = true;
                BL[Bg] = this.XD(Sv, SortType, XJ, dir) + " " + Bg;
                H[Bg++] = Sv;
            }
        }
    } else {
        for (; J; J = J.nextSibling) {
            if (!UA && J.firstChild && this.Pm(J, Bx)) XK = true;
            BL[Bg] = this.XD(J, SortType, XJ, dir) + " " + Bg;
            H[Bg++] = J;
        }
    };;
    if (Bg <= 1 || EU.Page && !this.XHeader.CanSort || !Is(EU, "CanSort")) return XK;
    BL.sort();
    var C = new Array(),
    XL = BL.length,
    XM = H.length - 1;
    for (var i = 0; i < XL; i++) {
        C[i] = dir ? H[BL[XM - i].slice(BL[XM - i].length - 6) - 0] : H[BL[i].slice(BL[i].length - 6) - 0];
    }
    this.EZ(EU, Bx, C);
    XK = true;
    return XK;
};
_7m.prototype.DoSort = function (Bx, UA) {
    for (var i = 0; i < this.AX; i++) {
        if (!this.Cols[this.SortCols[i]]) this.AX = 0;
    }
    if (!this.AX) return false;
    var XK = false;
    var XJ = new Array(),
    SortType = new Array();
    var dir = this.XI(XJ, SortType, this.XHeader);
    var BL = new Array(),
    H = new Array(),
    Bg = 0,
    C0 = !!this.MainCol && !UA;
    for (var Bc = this.XB.firstChild; Bc; Bc = Bc.nextSibling) {
        for (var J = Bc.firstChild; J; J = J.nextSibling) {
            if (C0 && J.firstChild && (J.firstChild.nextSibling || J.firstChild.firstChild) && this.Pm(J, Bx)) XK = true;
            BL[Bg] = this.XD(J, SortType, XJ, dir) + " " + Bg;
            H[Bg++] = J;
        }
    }
    if (Bg <= 1 || !this.XHeader.CanSort) {
        if (this.Paging && this.Sj) this.Sj();
        return XK;
    }
    BL.sort();
    var body = this.XB.firstChild,
    XM = H.length - 1,
    XL = BL.length;
    var BQ = this.Paging ? this.PageLength: 0xffffffff;
    this.Pn(body);
    for (var i = 0; i < XL; i++) {
        var J = dir ? H[BL[XM - i].slice(BL[XM - i].length - 6) - 0] : H[BL[i].slice(BL[i].length - 6) - 0];
        if (_7W) J.parentNode.removeChild(J);
        if (J.Visible) {
            if (!BQ) {
                BQ = this.PageLength;
                body = body.nextSibling;
                if (!body) body = this.FA(Bx);
                else this.Pn(body);
            }
            BQ--;
        }
        body.appendChild(J);
    }
    if (this.Paging && this.Sj) this.Sj();
    if (Bx) {
        var D3 = this.GetSections(),
        DP = D3[0],
        DQ = D3[1];
        for (var j = DP; j < DQ; j++) {
            var B3 = "r" + j,
            Ew = j == 1 && Grids.OnDisplaceRow != null;
            for (var i = 0; i < XL; i++) {
                var J = dir ? H[BL[XM - i].slice(BL[XM - i].length - 6) - 0] : H[BL[i].slice(BL[i].length - 6) - 0];
                if (J[B3]) {
                    var Bg = J.parentNode[B3];
                    var B1 = J.firstChild ? J[B3].nextSibling: null;
                    Bg.appendChild(J[B3]);
                    if (B1) {
                        Bg.appendChild(B1);
                        if (Ew) {
                            for (var BY = J.firstChild; BY && BY.Level > J.Level; BY = this.GetNext(BY)) if (BY.r1) Grids.OnDisplaceRow(this, BY);
                        }
                    }
                    if (Ew) Grids.OnDisplaceRow(this, J);
                }
            }
        }
    }
    body = body.nextSibling;
    while (body) {
        var B3 = body.nextSibling;
        this.SS(body);
        body = B3;
    }
    XK = true;
    return XK;
};
_7m.prototype.SortRows = function () {
    if (!this.AllPages && this.OnePage & 1 && this.Paging) {
        var Ko = this.GetFPage();
        this.Pm(Ko, true);
        var C = this.Cols[this.MainCol];
        if (C) for (var J = Ko.firstChild; J; J = J.nextSibling) this.Sd(J, true, C);
    } else if (this.Paging == 3) {
        this.ShowMessage(this.GetText("Sort"));
        this.ReloadBody(null, 0, "Sort");
    } else if (this.Paging == 2 || this.Paging == 1 && !this.AllPages) {
        this.ShowMessage(this.GetText("Sort"));
        var T = this;
        setTimeout(function () {
            var F1 = T.EditMode;
            if (F1 && T.EndEdit(T.FRow, T.FCol, true) == -1) {
                T.HideMessage();
                return;
            }
            if (T.DoSort()) {
                if (T.H3) T.Mn();
                T.Ku(F1);
            } else T.HideMessage();
            if (Grids.OnSortFinish) Grids.OnSortFinish(T);
        },
        10);
    } else {
        var T = this;
        function sort() {
            if (T.DoSort(true)) {
                T.Po(true);
                T.Dz();
            }
            T.HideMessage();
            if (T.Paging == 1 && T.UpdatePager) T.UpdatePager();
            if (T.FRow && !T.FRow.Fixed) T.ScrollIntoView(T.FRow, T.FCol);
            if (T.FRow) T.ExpandParents(T.FRow);
            if (T.H3) {
                T.RefreshGanttDependencies();
            }
            if (Grids.OnSortFinish) Grids.OnSortFinish(T);
        };
        if (this.RowCount < this.SynchroCount) sort();
        else {
            this.ShowMessage(this.GetText("Sort"));
            setTimeout(sort, 10);
        }
    }
};
_7m.prototype.ChangeSort = function (cols, XN, Pr) {
    cols = _7AU(cols);
    XN = _7AU(XN);
    if (!XN) XN = new Array();
    this.SortCols = cols;
    for (var i = 0; i < cols.length; i++) XN[i] = XN[i] ? XN[i] - 0 : 0;
    this.SortTypes = XN;
    this.AX = cols.length;
    for (var BY = this.GetFirstCol(); BY; BY = this.GetNextCol(BY)) this.RefreshCell(this.XHeader, BY);
    if (!Pr) this.SortRows();
};
_7m.prototype.Cb = function (Bx) {
    var XO = this.DefaultSortCol,
    XP = this.MaxSortColumns;
    if (XO) {
        var C = this.Cols[XO];
        if (!C) return;
        for (var i = 0; i < this.AX; i++) if (XO == this.SortCols[i]) break;
        if (i == this.AX || i == XP && C.Visible) {
            if (i > XP - 1 && C.Visible) i = XP - 1;
            else if (i > XP) i = XP;
            else this.AX++;
            this.SortCols[i] = XO;
            this.SortTypes[i] = 0;
            if (Bx) this.PP();
        }
    }
};
_7m.prototype.SortClick = function (BJ, type, dir, Up, RS) {
    if (this.Ae || !this.Sorting) return false;
    var C = this.Cols;
    if (this.EditMode && this.EndEdit(this.FRow, this.FCol, true) == -1) return false;
    var XQ = 0;
    if (this.Sorted) {
        if (Grids.OnSort) {
            XQ = Grids.OnSort(this, BJ);
            if (XQ == -1) return true;
            XQ = !XQ;
        } else XQ = 1;
    }
    if (!RS && dir != null && this.AX && this.SortCols[0] == BJ && (this.SortTypes[0] & 1) == dir) return false;
    if (XQ && this.Paging == 3 && !(this.OnePage & 1) && !this.CK()) return false;
    for (var EI = 0; EI < this.AX; EI++) {
        var D3 = C[this.SortCols[EI]];
        if (!D3 || D3.Visible || D3.CanHide || this.SortCols[EI] == this.DefaultSortCol) break;
    }
    for (var i = EI; i < this.AX; i++) if (this.SortCols[i] == BJ) break;
    if (RS) {
        this.SortCols = CopyObject(this.XR);
        this.SortTypes = CopyObject(this.XT);
        this.AX = this.SortCols.length;
    } else if (Up) {
        if (i == this.AX) return false;
        for (i++; i < this.AX; i++) {
            this.SortCols[i - 1] = this.SortCols[i];
            this.SortTypes[i - 1] = this.SortTypes[i];
        }
        this.AX--;
    } else {
        var XP = this.MaxSortColumns;
        if (i == this.AX && this.AX < XP) {
            if (i == EI) this.SortTypes[EI] = C[BJ].CanSort & 0xFE | 1;
            this.AX++;
        }
        if (i != EI) {
            var Cs = this.SortTypes[i];
            if (i == XP) i--;
            for (var j = i - 1; j >= EI; j--) {
                this.SortTypes[j + 1] = this.SortTypes[j];
                this.SortCols[j + 1] = this.SortCols[j];
            }
            this.SortTypes[EI] = C[BJ].CanSort & 0xFE | 1;
        }
        if (this.SortTypes[EI] == null) this.SortTypes[EI] = C[BJ].CanSort & 0xFE | 1;
        this.SortCols[EI] = BJ;
        if (type != null) this.SortTypes[EI] = type;
        else if (dir != null) this.SortTypes[EI] = this.SortTypes[EI] & 0xFE | dir & 1;
        else this.SortTypes[EI] = (~this.SortTypes[EI]) & 1 | this.SortTypes[EI] & 0xFE;
        if (this.AX > XP) {
            this.SortCols.length = XP;
            this.AX = XP;
        }
        this.Cb();
    };
    for (var BY = this.GetFirstCol(); BY; BY = this.GetNextCol(BY)) this.RefreshCell(this.XHeader, BY);
    this.SaveCfg();
    if (XQ) this.SortRows();
    return true;
};
_7m.prototype.ActionSortAsc = function (CE) {
    return this.SortClick(CE ? this.FCol: this.ACol, null, 0);
};
_7m.prototype.ActionSortDesc = function (CE) {
    return this.SortClick(CE ? this.FCol: this.ACol, null, 1);
};
_7m.prototype.ActionNoSort = function (CE) {
    return this.SortClick(CE ? this.FCol: this.ACol, null, null, 1);
};
_7m.prototype.ActionDefaultSort = function () {
    return this.SortClick(null, null, null, null, 1);
};
_7m.prototype.ActionSortOff = function () {
    if (!this.Sorting || !this.Sorted) return false;
    this.Sorted = false;
    this.SaveCfg();
    this.Calculate(1, 1, 1);
    return true;
};
_7m.prototype.ActionSortOn = function () {
    if (!this.Sorting || this.Sorted) return false;
    var sort = true;
    if (Grids.OnSort) sort = !Grids.OnSort(this);
    if (sort && this.Paging == 3 && !this.CK()) return;
    this.Sorted = true;
    if (sort) {
        if (this.Paging == 3) {
            var T = this;
            setTimeout(function () {
                T.DL = true;
                T.ReloadBody(function () {
                    T.DL = false;
                },
                0, "ReSort");
            },
            10);
        } else this.SortRows();
    }
    this.SaveCfg();
    this.Calculate(1, 1, 1);
    return true;
};
_7m.prototype.XU = function (BJ, XV) {
    var Cs = (new Date()).getTime(),
    XW = this.DefaultSortCol;
    if (Cs - this.JQ < 2000) {
        var V7 = [],
        XX = [],
        BQ = this.AX,
        Bn = 1;
        for (var i = 0; i < BQ; i++) {
            V7[i] = this.SortCols[i];
            XX[i] = this.SortTypes[i];
            if (this.SortCols[i] == BJ) {
                Bn = 0;
                if (XX[i] & 1) {
                    if (XV) return false;
                    XX[i] &= 0xFFFE;
                } else {
                    if (XV == false) return false;
                    XX[i] |= 1;
                }
            }
        }
        if (Bn && BQ < this.MaxSortColumns) {
            if (XW && BJ != XW && this.SortCols[BQ - 1] == XW && !this.XY) {
                V7[BQ] = XW;
                XX[BQ] = this.Cols[BJ].SortType;
                BQ--;
            }
            V7[BQ] = BJ;
            XX[BQ] = this.Cols[BJ].SortType;
            if (XV) XX[BQ] |= 1;
        } else if (Bn) return false;
        this.ChangeSort(V7, XX);
    } else {
        this.ChangeSort([BJ], [this.Cols[BJ].SortType]);
        this.Cb(1);
    };
    this.XY = BJ == XW;
    this.JQ = Cs;
    this.SaveCfg();
    return true;
};
_7m.prototype.ActionSortAscAdd = function (CE) {
    return this.XU(CE ? this.FCol: this.ACol, 0);
};
_7m.prototype.ActionSortDescAdd = function (CE) {
    return this.XU(CE ? this.FCol: this.ACol, 1);
};
_7m.prototype.Xn = function (row) {
    var Xo = new Object(),
    cells = row.Cells;
    if (cells) for (var i = 0; i < cells.length; i++) Xo[cells[i]] = 1;
    return Xo;
};
_7m.prototype.Xp = function (J, Xo) {
    if (Xo["List"]) {
        if (!J["ListDefaults"]) J["ListDefaults"] = J["List"];
        if (!J["ListType"]) J["ListType"] = "Select";
        if (!J["ListAction"]) J["ListAction"] = "Group";
        var Cols = J["Cols"],
        PG = J["Types"];
        if (Cols) {
            Cols = Cols.split(Cols.charAt(0));
            var Bh = "",
            Cs = "";
            for (var i = 1; i < Cols.length; i++) Bh += "','" + Cols[i];
            Bh = "['" + Bh.slice(3) + "']";
            J["ListFormula"] = "choose(Grid.GroupCols.join(',')," + Bh + ")";
            if (PG) {
                PG = PG.split(PG.charAt(0));
                for (var i = 1; i < PG.length; i++) Cs += "','" + PG[i];
                Cs = "['" + Cs.slice(3) + "']";
            }
            J["ListAction"] = "Grid.GroupRows(choose(null,null," + Bh + ")" + (Cs ? ",choose(null,null," + Cs + ")": "") + ");"
        }
    }
    if (Xo["Custom"] || J["Custom"]) {
        if (!Xo["Custom"]) J.Cells[J.Cells.length] = "Custom";
        J["CustomType"] = "DropCols";
        if (J["CustomText"] == null) J["CustomText"] = this.GetText("GroupCustom");
        J["CustomCanFocus"] = 0;
        J["CustomAction"] = "Grid.GroupRows(Row[Col])";
        J["CustomFormula"] = "Grid.GroupCols.join(',')";
        J["CustomRelWidth"] = 1;
    }
    if (!J.CalcOrder) J.CalcOrder = "";
    J.NoColor = 1;
    J.NoUpload = 1;
    return;
};
_7m.prototype.Xq = function (J, Xo) {
    if (Xo["Expression"]) {
        if (!J["ExpressionType"]) J["ExpressionType"] = "Text";
        if (J["ExpressionRelWidth"] == null) J["ExpressionRelWidth"] = 1;
        if (!J["ExpressionFormula"]) J["ExpressionFormula"] = "Grid.SearchExpression==null?'':Grid.SearchExpression";
        if (J["ExpressionCanEdit"] == null) J["ExpressionCanEdit"] = 1;
        if (J["ExpressionToolTip"] == null) J["ExpressionToolTip"] = 1;
        var JU = J["ExpressionAction"],
        Bh = "Grid.SearchExpression=Value;";
        if (!JU) JU = Xo["Actions"] ? Bh + "Grid.SearchRows(Actions);": Bh;
        else if (JU == "Filter" || JU == "Select" || JU == "Mark" || JU == "Find" || JU == "Last") JU = Bh + "Grid.SearchRows('" + JU + "');";
        else if (JU == "Actions") JU = Bh + "Grid.SearchRows(Actions);";
        J["ExpressionAction"] = JU;
        if (J.Cells.length == 1) J.Height = this.Img.Height + 1;
    }
    var H = ["Filter", "Select", "Mark", "Find", "Clear", "Help"];
    for (var i = 0; i < H.length; i++) {
        var Bc = H[i];
        if (Xo[Bc]) {
            if (!J[Bc + "Type"]) J[Bc + "Type"] = "Button";
            if (!J[Bc + "Caption"]) J[Bc + "Caption"] = Bc;
            if (!J[Bc + "Action"]) J[Bc + "Action"] = "Grid.SearchRows('" + Bc + "');";
            if (!J[Bc + "Formula"]) J[Bc + "Formula"] = "Grid.SearchAction=='" + Bc + "'";
        }
    }
    if (Xo["Search"]) {
        if (!J["SearchType"]) J["SearchType"] = "Button";
        if (!J["SearchCaption"]) J["SearchCaption"] = "Search";
        if (!J["SearchAction"]) J["SearchAction"] = "Grid.SearchRows(Actions);";
        if (!J["SearchFormula"]) J["SearchFormula"] = "Grid.SearchAction==Actions";
    }
    if (Xo["Actions"]) {
        if (!J["ActionsType"]) J["ActionsType"] = "Select";
        if (!J["ActionsDefaults"]) J["ActionsDefaults"] = "|Filter|Select|Mark|Find";
        if (!J["Actions"]) J["Actions"] = "Filter";
        if (J["ActionsAction"] == "Refresh") J["ActionsAction"] = "Grid.SearchRows(Value);";
    }
    function Xr(name, Xs) {
        var BY = new Array();
        for (var i = 0, j = 0; i < J.Cells.length; i++) {
            if (J.Cells[i] == Xs) BY[j++] = name;
            BY[j++] = J.Cells[i];
        }
        J.Cells = BY;
    };
    function Xt(BY) {
        var Bt = BY + "Label";
        if (!J[Bt]) return;
        if (!Xo[Bt]) Xr(Bt, BY);
        if (!J[Bt + "Type"]) J[Bt + "Type"] = "Html";
        J[Bt + "CanFocus"] = 0;
        if (J[Bt + "NoColor"] == null) J[Bt + "NoColor"] = 1;
        if (!J[Bt + "ToolTip"]) J[Bt + "ToolTip"] = J[BY + "ToolTip"];
        if (J[Bt + "Width"] == null) J[Bt + "Width"] = -1;
    };
    if (Xo["Cols"]) {
        Xt("Cols");
        if (!J["ColsType"]) J["ColsType"] = "Select";
        if (!J["ColsCanFocus"]) J["ColsCanFocus"] = 0;
        if (!J["ColsAction"]) {
            var Cols = J["ColsCols"];
            if (Cols) {
                Cols = Cols.split(Cols.charAt(0));
                var Bh = "";
                for (var i = 1; i < Cols.length; i++) Bh += "','" + Cols[i];
                Bh = "['" + Bh.slice(3) + "']";
                J["ColsFormula"] = "choose(Grid.SearchCols?Grid.SearchCols:''," + Bh + ")";
                J["ColsAction"] = "Grid.SearchCols=choose(null,null," + Bh + ");Grid.SearchRows('Refresh');"
            }
        }
    }
    if (Xo["Defs"]) {
        Xt("Defs");
        if (!J["DefsType"]) J["DefsType"] = "Select";
        if (!J["DefsCanFocus"]) J["DefsCanFocus"] = 0;
        if (!J["DefsAction"]) {
            var Cols = J["DefsDefs"];
            if (Cols) {
                Cols = Cols.split(Cols.charAt(0));
                var Bh = "";
                for (var i = 1; i < Cols.length; i++) Bh += "','" + Cols[i];
                Bh = "['" + Bh.slice(3) + "']";
                J["DefsFormula"] = "choose(Grid.SearchDefs?Grid.SearchDefs:''," + Bh + ")";
                J["DefsAction"] = "Grid.SearchDefs=choose(null,null," + Bh + ");Grid.SearchRows('Refresh');"
            }
        }
    }
    if (Xo["Case"]) {
        Xt("Case");
        if (!J["CaseType"]) J["CaseType"] = "Bool";
        if (!J["CaseWidth"]) J["CaseWidth"] = -1;
        if (!J["CaseAction"]) J["CaseAction"] = "Value?Grid.SearchType|=4:Grid.SearchType&=~4;Grid.SearchRows('Refresh');";
        if (!J["CaseFormula"]) J["CaseFormula"] = "Grid.SearchType&4?1:0";
        if (J["CaseCanEdit"] == null) J["CaseCanEdit"] = 1;
    }
    if (Xo["Type"]) {
        Xt("Type");
        if (!J["TypeType"]) J["TypeType"] = "Bool";
        if (!J["TypeWidth"]) J["TypeWidth"] = -1;
        if (!J["TypeAction"]) J["TypeAction"] = "Value?Grid.SearchType|=1:Grid.SearchType&=~1;Grid.SearchRows('Refresh');";
        if (!J["TypeFormula"]) J["TypeFormula"] = "Grid.SearchType&1";
        if (J["TypeCanEdit"] == null) J["TypeCanEdit"] = 1;
    }
    if (Xo["List"]) {
        Xt["List"];
        if (!J["ListType"]) J["ListType"] = "Select";
        var exp = J["ListExpressions"];
        if (exp) {
            exp = exp.split(exp.charAt(0));
            var Bh = "";
            for (var i = 1; i < exp.length; i++) Bh += "','" + exp[i].replace(/\'/g, "\\'");
            Bh = "Grid.SearchExpression=choose(null,null,['" + Bh.slice(3) + "']);";
            var JU = J["ListAction"];
            if (!JU) JU = Xo["Actions"] ? Bh + "Grid.SearchRows(Actions);": Bh;
            else if (JU == "Filter" || JU == "Select" || JU == "Mark" || JU == "Find") JU = Bh + "Grid.SearchRows('" + JU + "');";
            else if (JU == "Actions") JU = Bh + "Grid.SearchRows(Actions);";
            J["ListAction"] = JU;
        }
    }
    if (!J.CalcOrder) J.CalcOrder = "";
    J.NoUpload = 1;
    J.NoColorState = 1;
    return;
};
_7m.prototype.Xu = function (J, Xo) {
    if (Xo["Pager"]) {
        J["PagerType"] = "Pager";
        J["PagerCanFocus"] = 0;
        if (J["PagerRelWidth"] == null) J["PagerRelWidth"] = 1;
    }
    J.NoUpload = 1;
    J.NoColorState = 1;
    return;
};
_7m.prototype.Xv = function (J, Xo, Ou) {
    var Xw = new Array(),
    Xx = 0,
    BW = 0;
    var cols = 0;
    for (var BY in this.Cols) if (this.Cols[BY].CanHide) cols++;
    var Bc = 4,
    H = ["Save", 0, 1, ( !! this.Data.Upload.Url || !!this.Data.Upload.Tag || !!this.Data.Upload.Data) && !this.Detail, "Reload", 1, 2, !this.Detail, "Repaint", 2, 12, this.Paging && this.AllPages, "Print", 15, 15, 1, "Export", 16, 16, !!this.Data.Export.Url, "Add", 3, 3, this.Adding, "AddChild", 4, 11, this.Adding && !!this.MainCol, "Sort", 5, 4, this.Sorting, "Calc", 7, 13, 1, "ExpandAll", 9, 8, !!this.MainCol, "CollapseAll", 10, 9, !!this.MainCol, "Columns", 13, 14, !!cols, "Cfg", 11, 10, 1, "Help", 12, 7, 1];
    var KW = "Li" + "nk";
    if (Ou) {
        for (var i = 0, BL = []; i < J.Cells.length; i++) if (J[J.Cells[i]] != 0) BL[BL.length] = J.Cells[i];
        J.Cells = BL;
    }
    for (var i = 0; i < J.Cells.length; i++) {
        var BJ = J.Cells[i];
        for (var j = 0; j < H.length; j += Bc) if (BJ == H[j]) break;
        if (H[j]) {
            if (!H[j + 3]) BJ = null;
            else {
                if (BJ == "ExpandAll" || BJ == "AddChild") {
                    var Cs = J[BJ + "Type"];
                    if (Cs - 0 == Cs) {
                        this[BJ + "Type"] = Cs;
                    }
                }
                J[BJ + "Type"] = "Button";
                J[BJ + "Icon"] = H[j + 1];
                J[BJ + "ActionNum"] = H[j + 2];
                if (!J[BJ + "ToolTip"]) J[BJ + "ToolTip"] = this.Lang.Toolbar[BJ];
                J[BJ + "ClassOuter"] = null;
                J[BJ] = 0;
                if (BJ == "Sort" || BJ == "Calc") {
                    J.Calculated = 1;
                    if (!J[BJ + "Formula"]) {
                        if (BJ == "Sort") J[BJ + "Formula"] = "Grid.Sorted";
                        if (BJ == "Calc") J[BJ + "Formula"] = "Grid.Calculated";
                    }
                    J[BJ + "IconChecked"] = H[j + 1];
                    J[BJ + "Icon"] = H[j + 1] + 1;
                    J[BJ + "ToolTip"] = this.Lang.Toolbar[BJ + 0];
                    J[BJ + "ToolTipChecked"] = this.Lang.Toolbar[BJ + 1];
                }
            }
        } else if (BJ == "User") {
            var EK = Grids.OnGetUserPanel ? Grids.OnGetUserPanel(this) : "";
            if (EK) {
                J["UserType"] = "Html";
                J[BJ] = _7A + _7B + "<tr>" + EK + "</tr>" + _7C;
                J["UserWidth"] = -1;
                J["UserNoColor"] = 1;
                J["UserClassOuter"] = (this.Styles.UsePrefix ? "": this.Img.Style) + "ToolbarImg";
            } else BJ = null;
        } else if (BJ == KW) Xx = 1;
        else if (BJ == "Resize") BW = 1;
        else if (BJ == "Styles") {
            if (J[BJ] == 0) BJ = null;
            else {
                function Xy(BJ) {
                    J[BJ + "Type"] = "Html";
                    J[BJ + "Width"] = 10;
                    J[BJ + "CanEdit"] = 0;
                    J[BJ + "NoColor"] = 1;
                    J[BJ + "ClassOuter"] = "none";
                    J[BJ + "CanFocus"] = 0;
                    Xw[Xw.length] = BJ;
                };
                Xy("StyleSep1");
                for (var Bh in this.Styles) {
                    var S = this.Styles[Bh];
                    if (S.Name != Bh || !S.Caption) continue;
                    J[Bh + "Type"] = "Button";
                    J[Bh + "Icon"] = S.Caption;
                    if (Ou) {
                        J[Bh + "Formula"] = "Grid.Styles.Style=='" + Bh + "'";
                        J[Bh + "Action"] = "Grid.SetStyle('" + Bh + "',1);";
                    }
                    J[Bh + "ToolTip"] = S.ToolTip;
                    J[Bh + "ClassOuter"] = null;
                    Xw[Xw.length] = Bh;
                }
                Xy("StyleSep2");
                BJ = null;
            }
        }
        if (BJ) Xw[Xw.length] = BJ;
    }
    if (!Xx) {
        Xw[Xw.length] = "Link";
        Xx = 1;
    }
    if (Ou) {
        if (J[KW] != 0 && J[KW + "Visible"] != 0) {
            var Xz = J[KW];
            var X0 = "ht" + "t" + "p:/" + "/ww" + "w.t" + "re" + "eg" + "ri" + "d." + "co" + "m/";
            if (Xz && (Xz + "").length > 1) {
                Xz = Xz.split(Xz.charAt(0));
                Xz = (Xz[3] ? " title='" + Xz[3] + "'": "") + " onclick='try {window.open(\"" + Xz[1] + "\");} catch(e) {}'>" + (Xz[2] ? Xz[2] : Xz[1]);
            } else {
                var _7A1 = window["Co" + "mp" + "on" + "en" + "t"];
                if (_7A1) _7A1 = _7A1["Ve" + "rs" + "io" + "n"];
                if (!_7A1) _7A1 = "";
                else if (!_7U) _7A1 = (_7A1 + "").replace(new RegExp("\\.\\d+(?=[^\\.]*$)"), "");
                if (_7Ay == 11) Xz = "><span style='color:red;font:bold 9px Arial;background:white;text-decoration:inherit;'>" + _7D + "De" + "mon" + "st" + "rat" + "ion" + _7D + "</span> " + "of EJ" + "S Tre" + "eG" + "rid v" + _7A1;
                else {
                    Xz = " title='" + "Yo" + "u c" + "an " + "bu" + "y i" + "t a" + "t " + X0 + "'" + " onclick='window.open(\"" + X0 + "\");'" + ">EJ" + "S Tre" + "eG" + "rid v" + _7A1 + " <span style='color:red;font:bold 9px Arial;background:white;text-decoration:inherit;'>" + _7D + "Tr" + "ia" + "l u" + "nr" + "eg" + "i" + "st" + "er" + "ed" + _7D + "</span>";
                };
            };
            J[KW] = "<div style='cursor:default;padding-top:2px;padding-left:3px;padding-right:3px;white-space:nowrap;font:9px arial; color:#777777;'>" + "<span style='" + _7a + "'" + (this.Hover >= 1 ? " onmouseover='this.style.color=\"black\";this.style.textDecoration=\"underline\"' onmouseout='this.style.color=\"#777777\";this.style.textDecoration=\"\"'": "") + Xz + "</span></div>";
        } else J[KW] = _7D;
        J[KW + "Type"] = "Html";
        J[KW + "CanFocus"] = 0;
        J[KW + "NoColor"] = 1;
        J[KW + "ClassOuter"] = "none";
        J[KW + "ClassInner"] = "none";
        J[KW + "Width"] = -1;
        J[KW + "RelWidth"] = 1;
        J[KW + "Visible"] = 1;
    }
    if (J.Formula) {
        var BJ = J["FormulaCol"];
        if (!BJ) BJ = "Formula";
        Xw[Xw.length] = BJ;
        J[BJ + "Type"] = "Html";
        J.Calculated = 1;
        if (Ou) J[BJ + "Formula"] = J.Formula;
        J[BJ + "Width"] = -1;
        J[BJ + "ClassOuter"] = (this.Styles.UsePrefix ? "": this.Img.Style) + "ToolbarFormula";
        J[BJ + "CanFocus"] = 0;
        J[BJ + "NoColor"] = 1;
    }
    if (this.ResizingMain) {
        if (!BW) Xw[Xw.length] = "Resize";
        J["ResizeType"] = "Html";
        J["ResizeCanFocus"] = 0;
        J["ResizeNoColor"] = 1;
        if (Ou) J["ResizeWidth"] = -1;
        J["ResizeClassOuter"] = (this.Styles.UsePrefix ? "": this.Img.Style) + "ToolbarCell";
        J["Resize"] = "<div " + (_7V ? "><b style='display:inline-block;'": "") + " style='width:" + this.Img.ToolbarWidth + "px;height:" + this.Img.ToolbarHeight + "px;overflow:hidden;" + " cursor:" + (this.ResizingMain == 3 ? "nw-resize": (this.ResizingMain == 1 ? "n-resize": "w-resize")) + ";" + " background:url(" + this.Img.Toolbar + ") " + ( - 14 * this.Img.ToolbarWidth) + "px 0px'></div>" + (_7S ? "</b>": "");
    }
    if (!J.CalcOrder) J.CalcOrder = "";
    J._7A2 = J.Cells;
    J.Cells = Xw;
    J.NoUpload = 1;
    J.NoColorState = 1;
    return;
};
_7m.prototype.X1 = function (J) {
    if (!J.Cells) return;
    for (var i = 0; i < J.Cells.length; i++) {
        var BJ = J.Cells[i],
        type = J[BJ + "Type"];
        if (type == 'Button') {
            if (!J[BJ + "Width"]) J[BJ + "Width"] = -1;
            if (!J[BJ + "ClassOuter"]) J[BJ + "ClassOuter"] = (this.Styles.UsePrefix ? "": this.Img.Style) + "ToolbarCell";
            J[BJ + "CanFocus"] = 0;
            J[BJ + "NoColor"] = 1;
        } else if (type == 'DropCols') {
            this.Ql = 1;
            J[BJ + "CanFocus"] = 0;
        } else if (type == 'Pager') {
            var LX = new Array(),
            Bg = 0;
            for (var j = 0; j < i; j++) LX[Bg++] = J.Cells[j];
            var BX = BJ + "First";
            LX[Bg++] = BX;
            J[BX + "Type"] = "Button";
            J[BX + "Icon"] = 17;
            J[BX + "IconChecked"] = 18;
            J[BX + "Formula"] = "Grid.GetFPage()==Grid.XB.firstChild";
            J[BX + "Action"] = "Grid.GoToPageNum(1)";
            BX = BJ + "Prev";
            LX[Bg++] = BX;
            J[BX + "Type"] = "Button";
            J[BX + "Icon"] = 19;
            J[BX + "IconChecked"] = 20;
            J[BX + "Action"] = "Grid.GoToPrevPage()";
            J[BX + "Formula"] = "Grid.GetFPage()==Grid.XB.firstChild";
            BX = BJ + "Edit";
            LX[Bg++] = BX;
            var Hb = (this.Styles.UsePrefix ? "": this.Img.Style) + "PagerEdit";
            J[BX + "ClassOuter"] = "none";
            J[BX + "NoColor"] = 1;
            J[BX + "ClassInner"] = Hb;
            J[BX + "ClassEdit"] = Hb;
            J[BX + "Type"] = "Int";
            if (!J[BX + "Width"]) J[BX + "Width"] = 60;
            J[BX + "CanEdit"] = 1;
            J[BX + "Action"] = "Grid.GoToPageNum(Value<1?1:(Value>Grid.XB.childNodes.length?Grid.XB.childNodes.length : Value));";
            J[BX + "Formula"] = "if(Grid.Loading||Grid.Rendering)return -1;Row[Col+'HtmlPostfix']=' / '+Grid.XB.childNodes.length;return Grid.GetPageNum(Grid.GetFPage())+1";
            J[BX + "CanFocus"] = 2;
            J[BX + "AlwaysEdit"] = 1;
            BX = BJ + "Next";
            LX[Bg++] = BX;
            J[BX + "Type"] = "Button";
            J[BX + "Icon"] = 21;
            J[BX + "IconChecked"] = 22;
            J[BX + "Action"] = "Grid.GoToNextPage()";
            J[BX + "Formula"] = "Grid.GetFPage()==Grid.XB.lastChild";
            BX = BJ + "Last";
            LX[Bg++] = BX;
            J[BX + "Type"] = "Button";
            J[BX + "Icon"] = 23;
            J[BX + "IconChecked"] = 24;
            J[BX + "Action"] = "Grid.GoToPageNum(Grid.XB.childNodes.length)";
            J[BX + "Formula"] = "Grid.GetFPage()==Grid.XB.lastChild";
            for (var j = i + 1; j < J.Cells.length; j++) LX[Bg++] = J.Cells[j];
            J.Cells = LX;
            i--;
            continue;
        }
        if (!J[BJ + "Width"]) J[BJ + "Width"] = this.Cols[BJ] && this.Cols[BJ].Width ? this.Cols[BJ].Width: 100;
        if (!this.Cols[BJ]) {
            if (!J[BJ + "Type"]) J[BJ + "Type"] = 'Text';
            if (J[BJ + "Enum"] && !J[BJ + "EditEnum"]) J[BJ + "EditEnum"] = J[BJ + "Enum"];
            if (J[BJ + "CanEdit"] == null) J[BJ + "CanEdit"] = J.CanFocus == 0 || J.CanEdit == 0 || J[BJ + "Formula"] ? 0 : 1;
            if (J[BJ + "CanFocus"] == null) J[BJ + "CanFocus"] = J.CanFocus == 0 ? 0 : 1;
            if (J[BJ + "WidthPad"] == null) J[BJ + "WidthPad"] = 0;
            if (J[BJ + "RelWidth"]) J.RelWidth = 1;
            var D8 = ["Format", "EditFormat", "EditMask", "ResultMask", "Size", "Refresh", "Related", "Rows"];
            for (var j = 0; j < D8.length; j++) if (J[BJ + D8[j]] == null) J[BJ + D8[j]] = "";
        }
        var X2 = J[BJ + "Formula"];
        if (X2) {
            J.Calculated = 1;
        }
        if (J[BJ + "Names"]) J._7A3 = 1;
    }
};
_7m.prototype.X3 = function () {
    var CE = this.GetFixedRows();
    this.Aj = null;
    this.RelHeight = 0;
    for (var i = 0; i < CE.length; i++) {
        var Ff = CE[i].Kind;
        if (Ff == "Space" || Ff == "Group" || Ff == "Pager" || Ff == 'Search' || Ff == 'Toolbar') this.XS.appendChild(CE[i]);
        if (CE[i].RelHeight) this.RelHeight = true;
    }
    if ((this.ShowVScrollbar || this.ConstHeight) && !this.RelHeight) {
        this.RelHeight = true;
        var J = _7f.createElement("I");
        J.Def = "R";
        J.Space = 2;
        J.Kind = "Space";
        J.RelHeight = 1;
        if (this.XS.firstChild) this.XS.insertBefore(J, this.XS.firstChild);
        else this.XS.appendChild(J);
    }
    var Cs = this.Toolbar;
    this.XS.appendChild(Cs);
    Cs.Kind = "Toolbar";
    if (Cs.Space == null) Cs.Space = 4;
    var S = new Array();
    for (var i = 0; i < 6; i++) S[i] = i;
    for (var J = this.XS.firstChild, I = 0; J; J = J.nextSibling, I++) {
        if (J.Kind == 'Group') this.XGroup = J;
        if (J.Kind == 'Pager') this.Aj = J;
        var cells = J.Cells;
        if (!cells) {
            if (J.Kind == 'Group') cells = J["List"] || J["ListDefaults"] ? ["List"] : new Array();
            else if (J.Kind == 'Search') cells = ["Expression"];
        } else if (typeof(cells) == "string") {
            cells = cells.split(",");
        }
        J.Cells = cells;
        var Xo = this.Xn(J);
        if (J.Kind == 'Group') this.Xp(J, Xo);
        if (J.Kind == 'Search') this.Xq(J, Xo);
        if (J.Kind == 'Pager') this.Xu(J, Xo);
        if (J.Kind == 'Toolbar') this.Xv(J, Xo, 1);
        this.X1(J);
        J.Fixed = "Solid";
        J.Pos = I;
        if (!J.Def) J.Def = "Solid";
        if (J.Space == null) J.Space = J.Kind == "Group" || J.Kind == "Search" ? 1 : (J.Kind == "Pager" ? 4 : 2);
        if (J.Space == 3 && !this.XF.firstChild) J.Space = 2;
        if (J.Tag && !(J.Space > 9)) J.Space = 10;
        var Dr = J.Space;
        for (var i = Dr; i < 6; i++) S[i]++;
    }
    S[5]--;
    this.XS.Space = S;
};
_7m.prototype.Ox = function (refresh) {
    var Cs = this.Toolbar;
    if (!Cs || !Cs._7A2) return;
    Cs.Cells = Cs._7A2;
    this.Xv(Cs, this.Xn(Cs));
    this.X1(Cs);
    this.Calculate(0, 1, 1, 1);
    if (refresh) this.RefreshRow(Cs);
};
if (Grids.OnDemand) setTimeout(StartTreeGrid, 10);
else AttachEvent(window, "load", StartTreeGrid);
function StartTreeGrid(event) {
    function X4(X5, X6) {
        var BL = X6.attributes,
        FT = BL.length;
        for (var i = 0; i < FT; i++) {
            var a = BL[i],
            FU = a.nodeValue;
            if ((FU - 0) + "" == FU) FU = FU - 0;
            var D = a.nodeName.toLowerCase().split("_");
            for (var j = 0; j < D.length; j++) D[j] = D[j].charAt(0).toUpperCase() + D[j].slice(1);
            if (!D[1]) continue;
            var Cs = D[0],
            Ck = X5[Cs];
            if (!Ck) {
                Ck = new Object;
                X5[Cs] = Ck;
                Ck.Param = new Object;
            }
            if (!D[2]) Ck[D[1]] = FU;
            else Ck.Param[D[2]] = FU;
        }
    };
    function Ge(T, name) {
        var BL = T.getAttribute(name);
        if (!BL) BL = T.getAttribute(name.toLowerCase());
        if (!BL) BL = T.getAttribute(name.toUpperCase());
        return BL;
    };
    if (!Grids.J8) {
        _7Am();
        if (_7T) _7AO();
        Grids.J8 = true;
        setInterval(_7AP, 50);
        if (window._7AN) setInterval(_7AN, 200);
        if (window._7y) setInterval(_7y, 500);
        if (window._7z) setInterval(_7z, 1000);
        if (_7O) CreateXML = _7v;
        var Bh = GetStyle(document.body);
        Grids.Lu = Math.floor(_7Ah(Bh, 1) / 2);
        Grids.Lv = Math.floor(_7Ai(Bh, 1) / 2);
        var Bh = GetStyle(document.documentElement);
        Grids.X7 = Math.floor(_7Ah(Bh, 1) / 2);
        Grids.X8 = Math.floor(_7Ai(Bh, 1) / 2);
        Grids.A8 = new Object();
        var E4 = _7R ? _7f.createElement("I") : {
            parentNode: 1,
            firstChild: 1,
            lastChild: 1,
            previousSibling: 1,
            nextSibling: 1,
            id: 1,
            childNodes: 1,
            tagName: 1,
            nodeName: 1
        };
        for (var i in E4) Grids.A8[i] = true;
        var BL = new Object();
        BL[95] = 1;
        for (var i = 65; i < 91; i++) {
            BL[i] = 1;
            BL[i + 32] = 1;
        }
        for (var i = 48; i < 58; i++) BL[i] = 1;
        Grids.A9 = BL;
    }
    var BL = document.getElementsByTagName("treegrid");
    if (!BL.length) BL = document.getElementsByTagName("bdo");
    var P = new Array(),
    Bg = 0,
    X9 = Grids.NoTryActiveX ? -1 : 0;
    if (_7H || _7G || _7M) {
        for (var i = 0; i < BL.length; i++) if (!BL[i].dir || BL[i].tagName.toLowerCase() != "bdo") P[Bg++] = BL[i];
    } else for (var i in BL) if (typeof(BL[i]) == "object" && (!BL[i].dir || BL[i].tagName.toLowerCase() != "bdo")) P[Bg++] = BL[i];
    BL = P;
    for (var i = 0; i < BL.length; i++) {
        var T = BL[i];
        var M = T.parentNode;
        if (_7U) {
            if (window._7v) T = _7v(M.innerHTML, true).firstChild;
            else {
                _7w("Xml");
                T = YA;
            };
            if (!T) {
                _7Ak("<b style='color:red'>Bad syntax in &lt;" + BL[i].tagName + "> tag !</b>", M, "GMessage");
                continue;
            }
            T = T.firstChild;
        }
        if (T.length) {
            alert("Duplicit attribute id=" + T[0].id);
            continue;
        }
        var Id = Ge(T, "Id");
        if (!Id) Id = null;
        var D8 = new TDataIO();
        X4(D8, T);
        var YB = Ge(T, "Debug"),
        YC = Ge(T, "Sync") - 0,
        Oy = Ge(T, "Cache");
        if (YB == null) YB = D8.Debug;
        else {
            YB -= 0;
            D8.Debug = YB;
        };
        for (var j in D8) if (typeof(D8[j]) == "object") {
            if (D8[j].Debug == null) D8[j].Debug = YB;
            else D8[j].Debug -= 0;
            if (D8[j].Sync == null) D8[j].Sync = YC & 1;
            if (D8[j].Cache == null && Oy != null) D8[j].Cache = Oy - 0;
        }
        D8.Sync = YC & 2;
        if (Oy != null) D8.Cache = Oy - 0;
        var O9 = Ge(T, "CacheVersion");
        if (O9 != null) D8.CacheVersion = O9;
        if (D8.Text.YD) D8.Text.W = D8.Text.YD;
        if (D8.Text.YE) D8.Text.ErrXMLFormat = D8.Text.YE;
        if (Ge(T, "SuppressMessage")) {
            D8.SuppressMessage = 1;
            D8.Text.Start = "";
        }
        D8.BasePath = Ge(T, "BasePath");
        var EA = "";
        if (!X9 && window._7t) {
            var D9 = _7t();
            if (D9 && typeof(D9) != "string") X9 = 1;
            else X9 = -1;
        }
        if (!_7R && !_7F && !_7G && !_7M && !_7H) {
            EA = "<b style='color:red'>The browser is not supported !</b><br/>TreeGrid supports only these browsers: <br/>Internet Explorer 5.0+, Mozilla/Firefox 1.0+,<br/> Netscape Navigator 6.0+, Opera 7.60+<br/>KHTML (Konqueror 3.0+, Safari 1.2+)";
        } else if (X9 < 0 && (D8.Text.Url || D8.Defaults.Url || D8.Layout.Url || D8.Data.Url || D8.Upload.Url)) {
            EA = _7R ? "The browser has disabled the <b>Security</b> option<br/>'<i>Run ActiveX controls and plug-ins</i>'": "The browser does not support XmlHttpRequest object (AJAX) !";
        }
        if (EA) {
            _7Ak(EA, M, "GMessage");
        } else {
            if (D8.Text.Start) _7Ak(D8.Text.Start, M, "GMessage");
            else M.innerHTML = "";
            TreeGrid(D8, M, Id);
        }
    }
};
if (typeof(self.DLZ) == "string") DLZ = 23;
else if (typeof(self.DLZ) == "number") DLZ = 19;
function TreeGrid(D8, D4, id, index, Sb) {
    if (!D8) return;
    if (typeof(D8) == "string") {
        var YF = new TDataIO();
        YF.Data.Data = D8;
        D8 = YF;
    }
    var G = new _7m();
    G.Data = D8;
    if (Sb != null) G.Ax = Sb;
    if (D8.SuppressMessage) G.SuppressMessage = 1;
    if (index != null && Grids[index] && !id) G.id = Grids[index].id;
    G.Index = index == null ? Grids.length: index;
    Grids[G.Index] = G;
    G.MainTag = typeof(D4) == "string" ? GetElem(D4) : D4;
    G.Lang.Alert = new Object();
    G.Lang.Alert["ErrXMLFormat"] = D8.Text.ErrXMLFormat;
    G.Lang.Alert["ErrFormatReturn"] = D8.Text.ErrXMLFormat;
    G.Lang.Alert["ErrTimeout"] = D8.Text.X;
    G.Lang.Text = new Object();
    G.Lang.Text["StartErr"] = D8.Text.W;
    G.AC = 0;
    G.Loading = 1;
    G.Cache = D8.Cache;
    G.CacheVersion = D8.CacheVersion;
    if (D8.BasePath) G.As = D8.BasePath;
    else {
        G.As = "";
        var Jf = document.documentElement;
        var MN = Jf.getElementsByTagName("link");
        for (var i = 0; i < MN.length; i++) {
            if (MN[i].rel.toLowerCase() == "stylesheet" && MN[i].href && MN[i].href.search(/Grid[^\/\\]*\.css/) >= 0) {
                G.As = MN[i].href;
                break;
            }
        }
    };
    if (!G.As) {
        var name = /([\\\/]|^)GridE\.js($|\?|\#)/i;
        var S = Jf.getElementsByTagName("script");
        for (var i = 0; i < S.length; i++) {
            if (S[i].src && S[i].src.search(name) >= 0) {
                G.As = S[i].src;
                break;
            }
        }
        if (!G.As) {
            for (var i = 0; i < S.length; i++) {
                if (S[i].src) {
                    G.As = S[i].src;
                    break;
                }
            }
        }
    }
    if (G.As) G.As = G.As.replace(/[^\\\/]*$/, "");
    function YG(E3) {
        G.YH(E3, D8, D4, id, index);
    };
    var V = D8.Text;
    if (!V.Url && !V.Tag && !V.Data) V.Url = G.Iu("Text.xml");
    G.ReadData(V, YG);
    var V = D8.Defaults;
    if (!V.Url && !V.Tag && !V.Data) V.Url = G.Iu("Defaults.xml");
    G.ReadData(V, YG);
    return G;
};
_7m.prototype.YI = function (text) {
    if (this.Styles.Changed) this.SetStyle();
    this.HideMessage();
    if (!text) text = this.GetText("StartErr");
    if (this.MainTag) _7Ak("<b style='color:red;'>" + text + "</b>", this.MainTag, (this.Img.Style ? this.Img.Style: "G") + "Message");
    if (Grids.OnLoadError) Grids.OnLoadError(this);
    this.FL = null;
    this.Loading = 0;
};
_7m.prototype.YH = function (E3, D8, D4, id, index) {
    if (!this.Loading) return;
    if (E3 < 0) {
        this.AC = 3;
        this.YI();
        return;
    }
    if (!this.AC) {
        this.AC = 1;
        return;
    }
    if (this.AC == 3) return;
    this.AC = 2;
    if (this.DebugActions == null) this.DebugActions = 1;
    if (this.DebugCalc == null) this.DebugCalc = 1;
    if (D8.Text.Bonus) this.ER(CreateXML(D8.Text.Bonus));
    if (D8.Defaults.Bonus) this.ER(CreateXML(D8.Defaults.Bonus));
    if (!this.SuppressMessage) _7Ak(this.GetText("Layout"), this.MainTag, "GMessage");
    if (D8.Layout.Url || D8.Layout.Tag || D8.Layout.Data) {
        var T = this;
        this.ReadData(D8.Layout, function (E3) {
            T.YJ(E3, D8, D4, id, index);
        });
    } else {
        if (!D8.Data.Url && !D8.Data.Tag && !D8.Data.Data) {
            if (this.Styles.Changed) this.SetStyle();
            _7Ak(this.GetText("BadLoadData"), this.MainTag, this.Img.Style + "Message");
        } else this.YJ(0, D8, D4, id, index);
    }
};
_7m.prototype.YJ = function (E3, D8, D4, id, index) {
    if (!this.Loading) return;
    if (E3 < 0) {
        this.YI();
        return;
    }
    if (D8.Layout.Bonus) this.ER(CreateXML(D8.Layout.Bonus));
    var YK = false;
    if (this.ColNames[1] && this.ColNames[1].length) {
        if (id) this.id = id;
        this.YL();
        YK = true;
    }
    this.SetStyle();
    if (!this.SuppressMessage) _7Ak(this.GetText("Load"), this.MainTag, this.Img.Style + "Message");
    if (D8.Data.Url || D8.Data.Tag || D8.Data.Data) {
        var T = this;
        this.ReadData(D8.Data, function (E3) {
            T.YM(E3, D8, D4, id, index, YK);
        });
    } else {
        this.YM(0, D8);
    }
};
_7m.prototype.YM = function (E3, D8, D4, id, index, YK) {
    if (!this.Loading) return;
    if (E3 < 0 || !(self.DLZ > 20)) {
        this.YI();
        return;
    }
    if (D8.Data.Bonus) this.ER(CreateXML(D8.Data.Bonus));
    if (Grids.OnLoaded) Grids.OnLoaded(this);
    if (this.Styles.Changed) this.SetStyle();
    if (!this.Img.Style) {
        for (var Bh in this.Styles) {
            this.SetStyle(Bh);
            if (this.Img.Style) break;
        }
    }
    if (!this.GetFirstCol(1) || !this.XB.firstChild) {
        this.YI(this.GetText("BadLayoutErr"));
        return;
    }
    if (!this.SetIdentity(D4, id, index)) {
        this.YI();
        return;
    }
    if (!YK) this.YL();
    var Bh = GetStyle(this.MainTag);
    this.AK = _7Ah(Bh);
    this.AL = _7Ai(Bh);
    if (this.MaxWidth) Grids.MaxWidth = 1;
    if (this.MaxHeight) {
        Grids.MaxHeight = 1;
        _7AN();
    }
    if (D8.Upload.Tag && index == null) {
        var E4 = this.E5(D8.Upload.Tag),
        CE = this.FK(E4);
        if (CE) {
            if (CE.onsubmit) this.Ac = CE.onsubmit;
            CE.onsubmit = new Function("e", "return " + this.Z + ".Save(e?e:window.event);");
        }
    }
    this.MainTag.innerHTML = "";
    this.ShowMessage(this.GetText("UpdateValues"));
    if (this.Data.Sync) {
        this.YN(1);
        this.Render(this.GetText("Render"), 1);
        return;
    }
    var T = this;
    setTimeout(function () {
        T.YN();
    },
    10);
};
_7m.prototype.SetIdentity = function (D4, id, index) {
    if (D4) {
        if (typeof(D4) == "object") {
            this.MainTag = D4;
            this.Tag = this.MainTag.getAttribute("id");
        } else this.Tag = D4;
    }
    if (!this.MainTag) {
        if (!this.Tag) return false;
        this.MainTag = GetElem(this.Tag);
    }
    if (!this.MainTag) return false;
    if (index != null) {
        if (Grids[index]) delete Grids[Grids[index].id];
        this.Index = index;
    } else if (this.Index == null) this.Index = Grids.length;
    this.Z = "Grids[" + this.Index + "]";
    Grids[this.Index] = this;
    if (id) this.id = id;
    if (!this.id) this.id = "Table" + this.Index;
    Grids[this.id] = this;
    return true;
};
_7m.prototype.DelIdentity = function () {
    Grids[this.Index] = null;
    Grids[this.id] = null;
    this.Loading = 0;
    this.Rendering = 0;
};
_7m.prototype.YL = function () {
    if (this.SortCols && typeof(this.SortCols) == "string") {
        this.SortCols = this.SortCols.split(",");
        this.AX = this.SortCols.length;
    }
    if (typeof(this.SortTypes) != "object") {
        this.SortTypes = typeof(this.SortTypes) == "string" ? this.SortTypes.split(",") : [this.SortTypes - 0];
    }
    if (this.AX) {
        var Cs = this.SortTypes;
        for (var i = 0; i < this.AX; i++) Cs[i] = Cs[i] ? Cs[i] - 0 : 0;
    }
    this.Cb();
    this.XR = CopyObject(this.SortCols);
    this.XT = CopyObject(this.SortTypes);
    if (!this.GroupCols) {
        this.GroupCols = [];
        this.AZ = 0;
    }
    if (typeof(this.GroupCols) == "string") {
        this.GroupCols = this.GroupCols.split(",");
        this.AZ = this.GroupCols.length;
    }
    if (typeof(this.GroupTypes) != "object") {
        this.GroupTypes = typeof(this.GroupTypes) == "string" ? this.GroupTypes.split(",") : [this.GroupTypes - 0];
    }
    if (this.AZ) {
        var Cs = this.GroupTypes;
        for (var i = 0; i < this.AZ; i++) {
            if (Cs[i] == null) {
                var BY = this.Cols[this.GroupCols[i]];
                if (BY) Cs[i] = BY.GroupType;
            }
            Cs[i] = Cs[i] ? Cs[i] - 0 : 0;
        }
    }
    if (this.ResizingMain && this.MainTag) {
        this.Qz = this.MainTag.style.width;
        this.Q0 = this.MainTag.style.height;
    }
    if (this.HidePanel) this.Cols.Panel.Visible = 0;
    if (this.LoadCfg) this.LoadCfg(this.Cookie);
    var YO;
    if (this.SaveSession == 1 && Grids.CN) YO = Grids.CN(this.id + "&" + (this.SessionId ? this.SessionId: "Session"));
    else {
        var Fr = this.SaveSession ? this.SaveSession: this.id + "_" + (this.SessionId ? this.SessionId: "Session");
        var E = GetElem(Fr);
        if (E) YO = E.value;
    };
    if (YO != "") this.Data.Session = YO;
};
_7m.prototype.Ow = function () {
    var E4 = this.Img,
    YP = ["Grid", "Toolbar", "DragOne", "DragMore"];
    for (var i = 0; i < YP.length; i++) if (E4[YP[i]] && E4[YP[i]].search(/[\\\/]/) < 0) E4[YP[i]] = this.Iu(E4[YP[i]]);
    if (this.HelpFile.search(/[\\\/]/) < 0) this.HelpFile = this.Iu(this.HelpFile);
};
_7m.prototype.YN = function (YC) {
    if (!this.Loading) return;
    if (!this.Vm && this.MainCol) {
        this.MainCol = null;
        _7w("Tree");
    }
    if (!this.ShowPages && this.Paging) {
        this.Paging = 0;
        _7w("Paging");
    }
    if (!this.Paging) {
        this.NoPager = 1;
        this.AllPages = 1;
    }
    if (this.NoPager) {
        this.Pager.CanHide = 0;
        this.Pager.Visible = 0;
        this.MenuCfg.ShowPager = 0;
    }
    if (this.RemoveCollapsed > this.ChildPaging) this.RemoveCollapsed = this.ChildPaging;
    this.Ta = 1;
    if (this.InEditMode == 0) {
        this.ChangeAction("ClickCell", "ChangeFocus OR Focus AND StartEdit");
        this.ChangeAction("DblClickCell", "Focus AND StartEdit");
    }
    var YQ = this.DetailOn & 7;
    if (YQ == 1 || YQ == 3) this.ChangeAction("AfterClick", "");
    if (YQ == 1) this.ChangeAction("Focus", "ShowDetailF");
    if (YQ == 3) this.ChangeAction("AfterDblClick", "ShowDetail");
    this.ControlPanel = {
        Click: new Function("num", this.Z + ".ToolbarClick(num);"),
        HideCfg: new Function("save", this.Z + ".HideCfg(save);")
    };
    if (this.Selecting & 8) this.SelectingCells = 1;
    var EE = location.hostname.toLowerCase();
    _7Ay = null;
    if (EE.search("co" + "q." + "cz") >= 0 || EE.search("co" + "qs" + "of" + "t.c" + "om") >= 0 || EE.search("tre" + "egr" + "id.c" + "om") >= 0 || EE.search("tre" + "egr" + "id.n" + "et") >= 0) _7Ay = 11;
    else if (EE == "" || EE == "l" + "oc" + "al" + "ho" + "st" || EE == "12" + "7." + "0." + "0." + "1") _7Ay = 12;
    if (!_7Ay) {
        if (!this.Toolbar.Visible) {
            this.Toolbar = _7f.createElement("I");
            this.Toolbar.Visible = true;
            this.Toolbar.Cells = "";
        }
        this.Toolbar.Link = null;
        this.Toolbar.Tag = "";
    }
    this.Ow();
    this.X3();
    this.F5();
    if (!this.Calculating) for (var J = this.XS.firstChild; J; J = J.nextSibling) if (J.Kind == "Toolbar") J["CalcVisible"] = 0;
    this._7Au = this.MainCol;
    this.RootCDef = this.XHeader.CDef;
    this.Pb = this.XHeader[this.MainCol + "NumberSort"];
    if (this.Def["GroupLast"]) this.Def["GroupLast"].GroupCol = -1;
    var BQ = 0;
    for (var BY in this.Cols) {
        var C = this.Cols[BY];
        if (this.XHeader[BY] == null) this.XHeader[BY] = C.Name;
        if (C.Width != null && C.Width <= 0) {
            C.Width = null;
            C.Visible = 0;
        }
        if (C.WhiteChars && typeof(C.WhiteChars == "string")) {
            C.WhiteChars = new RegExp("[" + (C.WhiteChars + "").replace(/[\^\$\.\*\+\?\=\!\:\|\\\/\(\)\[\]\{\}\/]/g, "\\$&") + "]", "g");
        }
        if (C.CharCodes && typeof(C.CharCodes == "string")) {
            var BY = C.CharCodes.split(C.CharCodes.charAt(0));
            var C7 = new Object();
            for (var i = 1; i < BY.length; i += 2) C7[BY[i]] = BY[i + 1];
            C.CharCodes = C7;
        }
        if (!C.CharCodes && Grids.CharCodes) C.CharCodes = Grids.CharCodes;
        BQ++;
    }
    var F = this.Cols["Panel"];
    if ((!this.Selecting || !F.Select) && (!this.Deleting || !F.Delete) && (!this.Copying || !F.Copy) && (!this.Dragging || !F.Move)) F.Visible = 0;
    var S = this.ColNames,
    D3 = this.GetSections(),
    C7 = 0,
    C = this.Cols;
    for (var i = D3[0]; i < D3[1]; i++) {
        for (var j = 0; j < S[i].length; j++) {
            var YT = C[S[i][j]].Group;
            if (YT && YT != C7) {
                C[S[i][j]].Qk = 1;
                C7 = YT;
            }
            if (!C[S[i][j]].Group) C[S[i][j]].Group = 0;
        }
    }
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) if (CE[i].Kind == "User") {
        CE[i].CanFocus = 0;
        if (CE[i].LeftVal == null) CE[i].LeftVal = _7D;
        if (CE[i].MidVal == null) CE[i].MidVal = _7D;
        if (CE[i].RightVal == null) CE[i].RightVal = _7D;
    }
    this.FD();
    if (this.XHeader.Rows > 1 && this.XHeader.Main == null) this.XHeader.Main = this.XHeader.Rows - 1;
    this.IdPrefix = this.IdPrefix == null ? "": this.IdPrefix + "";
    this.IdPostfix = this.IdPostfix == null ? "": this.IdPostfix + "";
    this.IdChars = this.IdChars + "";
    if (!this.IdChars) this.IdChars = "0123456789";
    this.U7 = /[\^\$\.\*\+\?\=\!\:\|\\\/\(\)\[\]\{\}]/g;
    this.U8 = this.IdPrefix.replace(this.U7, "\\$&") + "([" + this.IdChars.replace(this.U7, "\\$&") + "]*)" + this.IdPostfix.replace(this.U7, "\\$&") + "$";
    this.Uy = new Object();
    for (var i = 0; i < this.IdChars.length; i++) this.Uy[this.IdChars.charAt(i)] = i;
    this.LastId = this.LastId == null ? "": this.LastId + "";
    if (this.LastId) {
        var M = this.LastId.match(new RegExp("^" + this.U8, this.IdCompare & 4 ? "i": ""));
        if (M) this.LastId = M[1];
    }
    var YU = window["tnenopmoC".split("").reverse().join("")];
    if (YU) {
        var BL = new Array(),
        Bg = 0,
        Bh = "",
        YV = 0,
        YW = "seludoM".split("").reverse().join("");
        for (var i in YU) if (typeof(YU[i]) == "string" && i != YW) BL[Bg++] = i;
        BL.sort();
        for (var i = 0; i < Bg; i++) Bh += YU[BL[i]];
        Bh += YU[YW];
        for (var i = 0; i < Bh.length; i++) {
            if (i % 3) YV += Bh.charCodeAt(i);
            else if (i % 11) YV -= Bh.charCodeAt(i);
            else YV ^= Bh.charCodeAt(i);
        }
        if (YV != 29546) YU = null;
    }
    if (!YU) {
        this.YI("ecnecil gnorW".split("").reverse().join(""));
        return;
    }
    if (this.Lang.Format.GMT - 0) {
        this.Mb = this.MV;
        this.MO = this.MY;
    } else {
        this.Mb = this.MU;
        this.MO = this.MW;
    };
    if (YC) {
        this.FC(1);
        return;
    }
    this.ShowMessage(this.GetText("Calculate"));
    var T = this;
    setTimeout(function () {
        T.FC();
    },
    10);
};
_7m.prototype.FC = function (YC) {
    if (!this.Loading && !YC) return;
    this.Calculate(false, false);
    if (this.Paging == 3) {
        if (this.Pager.CanHide || this.Pager.Visible || this.MenuCfg.ShowPager) this.Sh();
        if (this.Kj) this.Kj(true);
        if (this.AZ && (!this.Aw || this.Aw == "Group")) {
            this.PN();
            this.PO();
            this.Pk(0);
            for (var i = 0; i < this.AZ; i++) {
                var BJ = this.GroupCols[i];
                if (BJ != this.MainCol && this.Cols[BJ].CanGroup != 2) this.Cols[BJ].Visible = 0;
            }
            this.PT(this.SortCols, this.SortTypes);
        }
    }
    if (this.Paging == 3) {
        if (YC) {
            this.YX(1);
            return;
        }
        this.ShowMessage(this.GetText("UpdateTree"));
        var T = this;
        setTimeout(function () {
            T.YX();
        },
        10);
    } else {
        if (YC) {
            this.YY(1);
            return;
        }
        this.ShowMessage(this.GetText("DoFilter"));
        var T = this;
        setTimeout(function () {
            T.YY();
        },
        10);
    }
};
_7m.prototype.YY = function (YC) {
    if (!this.Loading && !YC) return;
    var BG = 0;
    var Dv = this.Kd && (!Grids.OnCanFilter || Grids.OnCanFilter(this, true));
    if (Dv) {
        this.Kd();
        this.Kj();
    }
    var BO = this.DoSearch && this.SearchAction && this.SearchAction != "Find" && (!Grids.OnCanSearch || Grids.OnCanSearch(this, true));
    var YZ = BO && this.SearchAction == "Filter";
    if (YZ) BG = this.DoSearch(this.SearchAction, true);
    else if (Dv && (this.GC || this.GG)) BG = this.Kk(true);
    if (BG) {
        if (this.Calculating && this.Calculated) this.Calculate(false, false);
        if (this.MainCol) this.Po(false, true);
    }
    if (BO && !YZ) this.DoSearch(this.SearchAction, true);
    if (YC) {
        this.Ya(1);
        return;
    }
    this.ShowMessage(this.GetText("Group"));
    var T = this;
    setTimeout(function () {
        T.Ya();
    },
    10);
};
_7m.prototype.Ya = function (YC) {
    if (!this.Loading && !YC) return;
    if (this.AZ && (!(this.OnePage & 4) || this.AllPages)) {
        this.PN();
        this.PO();
        this.PU(1);
        if (this.FilterEmpty && (this.GC || this.GE || this.GG || Grids.OnRowFilter)) this.Km(1);
    }
    if (YC) {
        this.Yb(1);
        return;
    }
    this.ShowMessage(this.GetText("Sort"));
    var T = this;
    setTimeout(function () {
        T.Yb();
    },
    10);
};
_7m.prototype.Yb = function (YC) {
    if (!this.Loading && !YC) return;
    if (this.Sorting && this.Sorted && this.AX && this.DoSort) {
        this.DoSort();
        for (var Bg = this.XB.firstChild; Bg; Bg = Bg.nextSibling) this.Pn(Bg);
    } else {
        if (this.Paging == 1 || this.Paging == 2) this.CreatePages();
        if (this.ChildPaging) this.EY(this.XB);
    };
    if (YC) {
        this.YX(1);
        return;
    }
    this.ShowMessage(this.GetText("UpdateTree"));
    var T = this;
    setTimeout(function () {
        T.YX();
    },
    10);
};
_7m.prototype.YX = function (Yc) {
    if (!this.Loading && !Yc) return;
    if (this.MainCol) {
        this.Po();
    }
    this.Dz(0);
    this.Yd();
    this.SetVPos();
    this.FL = 0;
    this.ClearUndo();
    for (var BJ in this.Cols) if (this.Cols[BJ].Type == "Gantt") {
        this.Gantt = 1;
        if (!Grids.OnGanttStart || !Grids.OnGanttStart(this)) this.CH();
        break;
    }
    this.Loading = 0;
    this.JZ = 0;
    if (!Yc) this.Render(this.GetText("Render"), 1);
};
_7m.prototype.GetRow = function (row, DR) {
    return row["r" + DR];
};
_7m.prototype.GetRowChildren = function (row, DR) {
    var J = row["r" + DR];
    if (!J) return null;
    J = J.nextSibling;
    return J;
};
_7m.prototype.GetCell = function (row, BJ, type) {
    if (!row || row.Kind == 'User' || !BJ) return null;
    if (BJ == "Pager" && (!row || row.Space == null)) return this.g;
    if (row.Space != null) {
        var J = row.r1;
        if (!J) return null;
        J = _7s(J, "tr")[0];
        if (!J) return null;
        J = J.cells[row[BJ + "Pos"]];
    } else {
        var C = this.Cols[BJ];
        if (!C) return null;
        var J = row["r" + C.K];
        if (!J) return null;
        if (row == this.XHeader && this.XHeader.Rows > 1) {
            var C0 = this.XHeader.Main;
            if (C0 == null) C0 = this.XHeader.Rows - 1;
            J = _7s(J, "tr")[C0];
        } else J = _7s(J, "tr")[0];
        if (!J) return null;
        if (row.Spanned && this.Ta) {
            for (var BY = this.GetFirstCol(C.K), Bg = 0; BY && this.Cols[BY].Pos < C.Pos; BY = this.GetNextCol(BY, row), Bg++);
            if (!BY) return null;
            if (this.Cols[BY].Pos > C.Pos && !(Get(row, BY + "Span") < 1)) return null;
            J = J.cells[Bg];
        } else if (C.Dl < 0) return null;
        else J = J.cells[C.Dl];
    };
    if (!type || !J) return J;
    if (type == 1) {
        if (this.Ia(row, BJ)) J = _7s(J.firstChild, "div")[0].parentNode;
        var BY = _7s(J.firstChild, "div")[0];
        if (BY) return BY.parentNode;
        BY = _7s(J.firstChild, "input")[0];
        if (BY) return BY.parentNode;
        BY = _7s(J.firstChild, "select")[0];
        if (BY) return BY.parentNode;
        BY = _7s(J.firstChild, "textarea")[0];
        if (BY) return BY.parentNode;
        return null;
    } else if (type == 2) {
        J = _7s(J.firstChild, "tr")[0];
        if (!J) return null;
        if (row.Kind == "Filter") return J.cells[0].firstChild;
        return J.cells[J.cells.length - 1].firstChild;
    } else if (type == 3) {
        if (this.Ia(row, BJ)) J = _7s(J.firstChild, "div")[0].parentNode;
        return J.firstChild;
    }
    return null;
};
_7m.prototype.QT = function (Bb, BJ) {
    var KL = this.XHeader;
    if (! (KL.Rows > 1) || Bb == KL.Main) return this.GetCell(KL, BJ);
    var C = this.Cols,
    DR = C[BJ].K,
    I = C[BJ].Pos,
    S = this.ColNames[DR];
    var J = KL["r" + DR];
    if (J) {
        J = J.firstChild.rows;
        var D = (DR == 0 ? "Left": DR == 1 ? "Mid": "Right") + Bb;
        var KN = 0,
        Bg = 0;
        for (var BX = 0;; BX++) {
            var rows = KL[D + BX + "Span"];
            if (!rows) rows = 1;
            if (!S[KN + rows] || C[S[KN + rows]].Pos > I) return J[Bb].cells[Bg];
            for (var i = 0; i < rows; i++) if (C[S[KN + i]].Visible) {
                Bg++;
                break;
            }
            KN += rows;
        }
    }
    return null;
};
_7m.prototype.Qh = function (row, BJ, I) {
    var BO = this.GetCell(row, BJ, 1).firstChild.firstChild.rows[0].cells;
    return BO[I];
};
_7m.prototype.Qc = function (row, BJ, IL) {
    var BO = this.GetCell(row, BJ, 1).firstChild.firstChild.rows;
    if (!BO) return - 1;
    BO = BO[0].cells;
    if (this.HV) {} else {
        var IU = BO[BO.length - 1];
        for (var Ce = BO.length - 1; Ce >= 0 && IU; Ce--) {
            var Wo = EventXYToElement(IL, IU)[0];
            if (Wo >= 0) return Ce;
            IU = IU.previousSibling;
        }
    };
    return - 1;
};
_7m.prototype.Kt = function (DR) {
    var H = this.c[DR].firstChild;
    if (_7G && H) H = H.firstChild;
    return H;
};
_7m.prototype.Uc = function (row, max) {
    if (row.Fixed || !row.r1) return;
    var Ee = this.Cols[this.MainCol];
    if (!Ee || !Ee.Visible) return;
    if (this.AM == null) this.AM = _7Ai(GetStyle(this.GetCell(row, this.MainCol)));
    if (!max) max = this.GetRow(row, Ee.K).offsetHeight;
    var C0 = max - this.AM - this.CellSpacing * 2;
    if (C0 > 0) {
        if (C0 > 128 || row.TL > 128) {
            this.GetCell(row, this.MainCol).innerHTML = this.Il(row, this.MainCol, null, null, C0);
            if (Grids.OnRenderRow) Grids.OnRenderRow(this, row, this.MainCol);
            row.TL = C0;
            return;
        }
        var BY = this.GetCell(row, this.MainCol, 3);
        if (BY) {
            BY = _7s(BY, _7V ? "u": "center");
            row.TL = C0;
            for (var i = 0; i < BY.length; i++) BY[i].style.height = C0 + "px";
        }
    }
};
_7m.prototype.UpdateRowHeight = function (row, Ye) {
    if (row.Space != null) return;
    var D3 = this.GetSections();
    var KL = new Array(),
    max = Ye ? Ye: 0;
    var Q5 = row == this.XHeader && row.Rows > 1;
    if (row.Yf) {
        for (var BJ in this.Cols) {
            var Ca = this.GetType(row, BJ);
            if (Ca == "Abs" || Ca == "Gantt") {
                var IU = this.GetCell(row, BJ, 1);
                if (IU) IU.firstChild.style.height = "";
            }
        }
    }
    for (var i = D3[0]; i < D3[1]; i++) {
        var J = this.GetRow(row, i);
        if (J) {
            if (Q5) J = J.firstChild.rows[row.Main];
            KL[i] = J.offsetHeight;
            if (!KL[i]) {
                for (var Ev = row.parentNode; Ev && Ev.Expanded && Ev.Visible; Ev = Ev.parentNode);
                if (Ev) Ev._UpdateHeight = 1;
                return;
            }
            if (!Ye && KL[i] > max) max = KL[i];
        }
    }
    var Jj = Get(row, "MaxHeight");
    if (Jj) {
        Jj = Jj - 0 + this.CellSpacing * 2;
        if (Jj < max) max = Jj;
    }
    Jj = Get(row, "MinHeight");
    if (Jj) {
        Jj = Jj - 0 + this.CellSpacing * 2;
        if (Jj > max) max = Jj;
    }
    if (_7F && this.MainCol && (!this.EditMode || this.FCol != this.MainCol)) this.Uc(row, max);
    for (var i = D3[0]; i < D3[1]; i++) {
        var J = this.GetRow(row, i);
        if (J) {
            var Ev = J.firstChild;
            if (Q5) Ev = Ev.rows[row.Main];
            else if (_7X) Ev = Ev.rows[0];
            if (KL[i] < max) {
                Ev.style.height = max + "px";
                if (!Q5 && J.offsetHeight > max) Ev.style.height = (max * 2 - J.offsetHeight) + "px";
            } else if (KL[i] > max) {
                Ev.style.height = max + "px";
                var Cq = this.ColNames[i];
                for (var j = 0; j < Cq.length; j++) {
                    var BJ = Cq[j];
                    if (this.Jy(row, BJ)) {
                        if (this.AM == null) this.AM = _7Ai(GetStyle(this.GetCell(row, BJ)));
                        if (row.Spanned && !this.Cols[BJ].Visible) {
                            var Dr = Get(row, BJ + "Span");
                            while (Dr > 1 && !this.Cols[BJ].Visible) {
                                BJ = this.GetNextCol(BJ);
                                Dr--;
                            }
                        }
                        var BY = this.GetCell(row, BJ, 3);
                        if (BY && BY.offsetHeight >= max - this.AM) {
                            BY.style.height = (max - this.AM - this.CellSpacing * 2) + "px";
                        }
                    }
                }
            }
        }
    }
    if (this.MainCol && (!this.EditMode || this.FCol != this.MainCol)) this.Uc(row, max);
    if (row.Yf) {
        for (var BJ in this.Cols) {
            var Ca = this.GetType(row, BJ);
            if (Ca == "Abs" || Ca == "Gantt") {
                var IU = this.GetCell(row, BJ, 1);
                if (IU) {
                    var No = row[BJ + "BorderHeight"];
                    if (No == null) {
                        No = _7Ai(GetStyle(this.GetCell(row, BJ)));
                        row[BJ + "BorderHeight"] = No;
                    }
                    IU.firstChild.style.height = (max - No) + "px";
                }
            }
        }
    }
};
_7m.prototype.DY = function (BJ) {
    if (!this.e) return;
    var C = this.Cols[BJ],
    DR = C.K,
    I = C.Dl,
    T = this,
    Ew = Grids.OnRenderRow != null;
    function KU(row) {
        var J = row["r" + DR];
        if (!J) return;
        J = _7s(J, "tr")[0];
        if (J) {
            var BX = BJ;
            if (row.Spanned) {
                C.Visible = 0;
                var S = T.ColNames[DR],
                Bg = 0,
                Yg = 0,
                Lx;
                while (Bg <= C.Pos) {
                    BX = S[Bg];
                    var BY = T.Cols[BX],
                    Dr = Get(row, BX + "Span");
                    if (Dr > S.length - Bg) Dr = S.length - Bg;
                    if (!Dr) Dr = 1;
                    Lx = 0;
                    for (var i = 0; i < Dr; i++) if (T.Cols[S[Bg + i]].Visible) {
                        Yg++;
                        Lx++;
                        break;
                    }
                    Bg += Dr;
                }
                if (!Lx) J = J.insertCell(Yg);
                else {
                    if (!Get(row, BX + "Merge")) {
                        J = J.cells[Yg - 1].firstChild;
                        if (J.tagName == "table") J = _7s(J, "div")[0];
                        var CY = J.offsetWidth;
                        if (!CY) {
                            CY = parseInt(J.style.width);
                            if (!CY) CY = J.clientWidth;
                        }
                        J.style.width = (CY + C.Width) + "px";
                        J = null;
                    } else J = J.cells[Yg - 1];
                };
                C.Visible = 1;
            } else J = J.insertCell(I);
        }
        if (J) {
            J.innerHTML = T.Il(row, BX);
            J.className = T.KA(row, BX);
            J.style.backgroundColor = T.GetColor(row, BX);
            if (Ew) Grids.OnRenderRow(T, row, BX);
        }
        if (T.VarHeight) T.UpdateRowHeight(row);
    };
    if (this.XHeader.Rows > 1) this.PP(DR);
    else KU(this.XHeader);
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) if (CE[i].Kind != "User") KU(CE[i]);
    for (var row = this.GetFirst(); row; row = this.GetNext(row)) KU(row);
    var CY = C.Width;
    C.Width = 0;
    this.SetWidth(BJ, CY, 0, 1);
    this.Cb(1);
};
_7m.prototype.Da = function (BJ) {
    if (!this.e) return;
    var C = this.Cols[BJ],
    DR = C.K,
    I = C.Dl,
    T = this;
    function KU(row) {
        var J = row["r" + DR];
        if (!J) return;
        J = _7s(J, "tr")[0];
        if (J) {
            if (row.Spanned) {
                var S = T.ColNames[DR],
                Bg = 0,
                Yg = 0;
                while (Bg <= C.Pos) {
                    BX = S[Bg];
                    var BY = T.Cols[S[Bg]],
                    Dr = Get(row, S[Bg] + "Span");
                    if (!Dr) Dr = 1;
                    for (var i = 0; i < Dr; i++) if (T.Cols[S[Bg + i]].Visible) {
                        Yg++;
                        break;
                    }
                    Bg += Dr;
                }
                Yg--;
                var Sv = J.cells[Yg].firstChild,
                SI = 0;
                if (Sv.tagName == "table") {
                    Sv = _7s(Sv, "div")[0];
                    SI = T.Ia(row, BJ);
                }
                var Dp = parseInt(Sv.style.width);
                if (!Dp) Dp = Sv.clientWidth;
                if (!Dp) return;
                Dp += T.AH + SI;
                if (Dp <= C.Width) {
                    if (J.cells.length > Yg) J.deleteCell(Yg);
                } else if (Get(row, BX + "Merge")) {
                    C.Visible = 0;
                    J.cells[Yg].innerHTML = T.Il(row, BX);
                    C.Visible = 1;
                } else Sv.style.width = (Dp - C.Width - T.AH - SI) + "px";
            } else J.deleteCell(I);
        }
        if (T.VarHeight) T.UpdateRowHeight(row);
    };
    if (this.XHeader.Rows > 1) this.PP(DR);
    else KU(this.XHeader);
    C.Visible = 1;
    var CE = this.GetFixedRows();
    for (var i = 0; i < CE.length; i++) {
        if (CE[i].Kind == "User") {
            var IU = _7s(CE[i]["r" + DR], "td")[0];
            if (IU) IU.firstChild.style.width = (parseInt(IU.firstChild.style.width) - C.Width) + "px";
        } else KU(CE[i]);
    }
    for (var row = this.GetFirst(); row; row = this.GetNext(row)) KU(row);
    C.Visible = 0;
    this.SetVPos();
    var CY = C.Width;
    this.SetWidth(BJ, -CY, 0, 1);
    C.Width = CY;
    this.SetScrollBars();
};
_7m.prototype.Dm = function (BJ, I) {
    var C = this.Cols[BJ],
    DR = C.K,
    Yh = C.Dl;
    if (Yh == I) return;
    if (Yh < I) I++;
    var T = this,
    BX = null;
    for (var BY in this.Cols) if (this.Cols[BY].K == DR && this.Cols[BY].Dl == I) BX = BY;
    function Move(row) {
        if (row.Kind == "User" || !row.r1) return;
        if (row.Spanned) {
            if (Get(row, BJ + "Span") == 0) return;
            var BY = T.GetCell(row, BJ);
            BY.parentNode.insertBefore(BY, T.GetCell(row, BX));
        } else {
            var J = row["r" + DR];
            if (J) J = _7s(J, "tr")[0];
            if (J) {
                var BY = J.cells[I];
                if (!BY) BY = null;
                J.insertBefore(J.cells[Yh], BY);
            }
        }
    };
    if (this.XHeader.Rows > 1) this.PP(DR);
    else Move(this.XHeader);
    var CE = this.GetFixedRows();
    for (var J = 0; J < CE.length; J++) Move(CE[J]);
    for (var J = this.GetFirst(); J; J = this.GetNext(J)) Move(J);
};
_7m.prototype.UM = function (row) {
    var EU = row.parentNode;
    if (!this.e || !EU.r1) return;
    var J, Yi, B1, Yj, move = row.r1 ? 1 : 0,
    D3 = this.GetSections(),
    DP = D3[0],
    DQ = D3[1],
    Yk = this.HasChildren(row),
    Yl = 0;
    for (var B3 = row.nextSibling; B3; B3 = B3.nextSibling) if (B3.r1) break;
    if (EU.Page) {
        if (EU.State != 4) return;
        if (EU.r1.style.overflow == "hidden") {
            for (var i = DP; i < DQ; i++) {
                var Op = row.parentNode["r" + i];
                Op.innerHTML = "";
                Op.style.height = "";
                Op.style.overflow = "";
            }
        }
    }
    for (var i = DP; i < DQ; i++) {
        if (EU.Page) B1 = EU["r" + i];
        else if (EU._7Ax) B1 = EU["r" + i].nextSibling;
        else {
            B1 = document.createElement("div");
            B1.className = this.Img.Style + "ChildPage";
            var Ec = EU["r" + i];
            if (Ec.nextSibling) Ec.parentNode.insertBefore(B1, Ec.nextSibling);
            else Ec.parentNode.appendChild(B1);
        };
        if (move) {
            J = row["r" + i];
            Yi = J.nextSibling;
            if ((!Yi || Yk && !Yi.nextSibling) && !J.previousSibling) Yj = J.parentNode;
        } else {
            J = document.createElement("div");
            var Hb = Get(row, "Class");
            if (Hb) J.className = this.Img.Ib + Hb;
            J.innerHTML = this.IK(row, i);
            J.onmousemove = new Function("var A = Grids.Active;if(A){ A.ARow = this.row; A.ASec = 0;}");
            row["r" + i] = J;
            J.row = row;
            if (!row.Visible) J.style.display = "none";
        };
        if (B3) B1.insertBefore(J, B3["r" + i]);
        else B1.appendChild(J);
        if (_7X && Yi && EU != B1) {
            for (var F3 = Yi.nextSibling; F3; F3 = F3.nextSibling) {
                F3.style.height = "1px";
                F3.style.height = "";
            }
            for (var F3 = J.nextSibling; F3; F3 = F3.nextSibling) {
                F3.style.height = "1px";
                F3.style.height = "";
            }
        }
        if (move && Yj) {
            Yj.parentNode.removeChild(Yj);
        }
        if (Yk) {
            if (!move) {
                Yi = document.createElement("div");
                Yi.className = this.Img.Style + "ChildPage";
                Yi.style.display = "none";
            }
            if (B3) B1.insertBefore(Yi, B3["r" + i]);
            else B1.appendChild(Yi);
            if (move && i == 1 && Grids.OnDisplaceRow) {
                for (var BY = row.firstChild; BY && BY.Level > row.Level; BY = this.GetNext(BY)) if (BY.r1) Grids.OnDisplaceRow(this, BY);
            }
            row._7Ax = 4;
        }
    }
    if (Grids.OnRenderRow && !move) Grids.OnRenderRow(this, row);
    if (Grids.OnDisplaceRow && move) Grids.OnDisplaceRow(this, row);
    EU._7Ax = 4;
    if (this.VarHeight) this.UpdateRowHeight(row);
    if (!move) this.RowCount++;
};
_7m.prototype.El = function (row) {
    if (!this.e || !row.r1) return;
    try {
        var B1 = row._7Ax;
        var US = 1;
        if (!row.parentNode.Page) {
            for (var B3 = row.nextSibling; B3; B3 = B3.nextSibling) if (B3.r1) break;
            if (!B3) {
                for (var Bg = row.parentNode.firstChild; Bg != row; Bg = Bg.nextSibling) if (Bg.r1) break;
                if (Bg == row) US = 0;
            }
        }
        this._7d(row);
        for (var i = 0; i < 3; i++) {
            var J = row["r" + i];
            if (!J) continue;
            var Bg = J.parentNode;
            if (!US) Bg.parentNode.removeChild(Bg);
            else {
                if (B1) Bg.removeChild(J.nextSibling);
                Bg.removeChild(J);
            };
            row["r" + i] = null;
        }
        if (!US) row.parentNode._7Ax = null;
        row._7Ax = null;
    } catch(B) {};
    this.RowCount--;
    return US;
};
_7m.prototype.UO = function (row) {
    if (!this.e) return;
    var J = row.r1;
    if (J) {
        if (row.Space != null) J.parentNode.parentNode.style.display = "";
        else {
            var Wg = this.VarHeight && !J.offsetHeight;
            J.style.display = "";
            J = row.r0;
            if (J) J.style.display = "";
            J = row.r2;
            if (J) J.style.display = "";
            if (Wg) this.UpdateRowHeight(row);
            if (Grids.OnDisplayRow) Grids.OnDisplayRow(this, row);
        }
    } else this.UM(row);
    this.K2(row);
};
_7m.prototype.UR = function (row) {
    if (!this.e || !row.r1) return;
    if (row.Space != null) {
        row.r1.parentNode.parentNode.style.display = "none";
        return;
    }
    try {
        for (var i = 0; i < 3; i++) {
            var J = row["r" + i];
            if (J) J.style.display = "none";
        }
    } catch(B) {}
};
_7m.prototype.UP = function (row) {
    if (!this.e || !row.r1) return;
    if (!row._7Ax) this.Ry(row);
    var J = this.GetRowChildren(row, 1);
    if (J && J.style.display == "") return;
    for (var i = 0; i < 3; i++) {
        var J = this.GetRowChildren(row, i);
        if (J) J.style.display = "";
    }
    if (row._UpdateHeight) {
        row._UpdateHeight = 0;
        var T = this;
        function BI(row) {
            for (var J = row.firstChild; J; J = J.nextSibling) {
                T.UpdateRowHeight(J);
                if (J.Expanded) BI(J);
            }
        };
        BI(row);
    }
    if (Grids.OnDisplayRow) {
        var Ka = row.Level;
        for (var J = this.GetNextVisible(row, 1); J && J.Level > Ka; J = this.GetNextVisible(J, 1)) Grids.OnDisplayRow(this, J);
    }
    this.SetScrollBars();
};
_7m.prototype.Rz = function (row) {
    if (!this.e || !row.r1) return;
    if (this.RemoveCollapsed) {
        this._7d(row);
        row.State = 2;
        row._7Ax = 2;
        for (var i = 0; i < 3; i++) {
            var J = this.GetRowChildren(row, i);
            if (J) {
                J.innerHTML = "";
                J.style.display = "none";
            }
        }
    } else for (var i = 0; i < 3; i++) {
        var J = this.GetRowChildren(row, i);
        if (J) J.style.display = "none";
    }
    this.SetScrollBars();
};
_7m.prototype.Ry = function (row) {
    if (!row || !row.r1) return;
    var D3 = this.GetSections();
    for (var i = D3[0]; i < D3[1]; i++) {
        var B1 = document.createElement("div");
        B1.className = this.Img.Style + "ChildPage";
        var EU = row["r" + i];
        if (EU.nextSibling) EU.parentNode.insertBefore(B1, EU.nextSibling);
        else EU.parentNode.appendChild(B1);
    }
    row._7Ax = 4;
};
_7m.prototype.Er = function (row) {
    var D3 = this.GetSections();
    for (var i = D3[0]; i < D3[1]; i++) {
        var J = this.GetRowChildren(row, i);
        if (J) J.parentNode.removeChild(J);
    }
    row._7Ax = null;
    this.SetScrollBars();
};
_7m.prototype.RefreshRow = function (row) {
    if (!row.r1) return;
    try {
        if (row.r0) row.r0.innerHTML = this.IK(row, 0);
        row.r1.innerHTML = row.Space == null || !this.Tu ? this.IK(row, 1) : this.Tu(row);
        if (row.r2) row.r2.innerHTML = this.IK(row, 2);
    } catch(B) {};
    if (Grids.OnRenderRow) Grids.OnRenderRow(this, row);
    if ((this.VarHeight || row.Fixed) && row.Space == null) {
        var D3 = this.GetSections();
        for (var i = D3[0]; i < D3[1]; i++) row["r" + i].firstChild.style.height = "1px";
        this.UpdateRowHeight(row);
    }
    if (row.RelWidth) this.B6(row);
};
_7m.prototype.RefreshCell = function (row, BJ) {
    if (row.Spanned) BJ = this.Dq(row, BJ);
    var BY = this.GetCell(row, BJ);
    if (BY) {
        BY.innerHTML = BJ == this.MainCol && !row.Fixed ? this.Il(row, BJ, null, null, (_7H ? BY.parentNode.parentNode.clientHeight - BY.offsetHeight + BY.clientHeight: BY.clientHeight) - this.Tq) : this.Il(row, BJ);
        if (_7AJ != 1) {
            setTimeout(function () {
                _7Ao(BY);
            },
            10);
        }
        if (row.Space != null && row.RelWidth) this.B6(row);
    }
    if (Grids.OnRenderRow) Grids.OnRenderRow(this, row, BJ);
};
_7m.prototype.KH = function (row, BJ) {
    if (_7F) {
        if (!row || !BJ || BJ == "Pager" || BJ == "Panel" && !_7L || _7L && this.CanEdit(row, BJ) || _7J && !_7K && row.Space != null || this.Ge(row, BJ, "NoFFDrag") == 1) return;
        var IU = this.GetCell(row, BJ);
        if (IU) {
            var BG = IU.firstChild;
            var Ck = document.createElement("div");
            IU.appendChild(Ck);
            Ck.appendChild(BG);
            var T = this;
            if (this.GetType(row, BJ) == "Gantt") {
                IU.appendChild(BG);
                IU.removeChild(Ck);
                if (Grids.OnDisplaceRow) Grids.OnDisplaceRow(T, row, BJ);
            } else {
                setTimeout(function () {
                    try {
                        if (IU.firstChild == Ck) {
                            IU.appendChild(BG);
                            IU.removeChild(Ck);
                            if (Grids.OnDisplaceRow) Grids.OnDisplaceRow(T, row, BJ);
                        }
                    } catch(B) {}
                },
                10);
            }
        }
    }
};
_7m.prototype.K2 = function (row) {
    if (!this.Cols["Panel"].Visible) return;
    try {
        var BY = this.GetCell(row, "Panel");
        if (BY) BY.innerHTML = this.Il(row, "Panel");
    } catch(B) {};
};
_7m.prototype.Ym = function (J) {
    for (var i = 0; i < 3; i++) {
        var Cq = this.ColNames[i];
        for (var j = 0; j < Cq.length; j++) {
            var Dr = J[Cq[j] + "Span"];
            if (Dr > 1) {
                for (j++; Dr > 1; Dr--, j++) J[Cq[j] + "Span"] = 0;
                j--;
            }
        }
    }
};
_7m.prototype.Yn = function (DD, DG) {
    if (!DD) return "";
    var BL = (DD + "").split(this.Lang.Format.ValueSeparator),
    GQ = 0;
    for (var i = 0; i < BL.length; i++) {
        var H = BL[i].split(this.Lang.Format.RangeSeparator);
        for (var j = 0; j < H.length; j++) {
            var Bc = H[j];
            if ((! (Bc - 0) || Bc < 3000) && Bc != "0") {
                var Ck = DG ? StringToDate(Bc, DG) : (GQ ? _7AW(Bc) : _7AX(Bc));
                if (Ck) H[j] = Ck.getTime();
            }
        }
        BL[i] = H.join(this.Lang.Format.RangeSeparator);
    }
    return BL.join(this.Lang.Format.ValueSeparator);
};
_7m.prototype.BP = function (DD) {
    var M = DD;
    if (typeof(M) == "string") {
        M = new Array(),
        Bg = 0;
        DD = DD.split(this.Lang.Format.ValueSeparator);
        for (var i = 0; i < DD.length; i++) {
            if (!DD[i]) continue;
            var BZ = DD[i].split(this.Lang.Format.RangeSeparator);
            M[Bg++] = BZ[0] - 0;
            M[Bg++] = BZ[1] ? BZ[1] - 0 : BZ[0] - 0;
        }
    }
    for (var i = 0; i < M.length; i += 2) {
        var a = M[i],
        Bc = M[i + 1];
        if (a == null) continue;
        a = new Date(a);
        Bc = new Date(Bc);
        a.setDate(a.getDate() - 1);
        a = a.getTime();
        Bc.setDate(Bc.getDate() + 1);
        Bc = Bc.getTime();
        for (var j = 0; j < M.length; j += 2) {
            if (i == j || M[j] == null) continue;
            if (M[j] >= a && M[j] <= Bc) {
                if (M[j + 1] >= Bc) M[i + 1] = M[j + 1];
                if (M[j] == a) M[i] = a;
                M[j] = null;
                M[j + 1] = null;
                i = -2;
                break;
            } else if (M[j + 1] >= a && M[j + 1] <= Bc) {
                M[i] = M[j];
                M[j] = null;
                M[j + 1] = null;
                i = -2;
                break;
            }
        }
    }
    M.sort();
    var Bh = "";
    for (var i = 0; i < M.length; i += 2) {
        if (M[i] == null) continue;
        if (Bh) Bh += this.Lang.Format.ValueSeparator;
        Bh += M[i];
        if (M[i + 1] != M[i]) Bh += this.Lang.Format.RangeSeparator + M[i + 1];
    }
    return Bh;
};
_7m.prototype.Yo = function (J, U0, D3, Yp, Yq) {
    var D8 = J.Def,
    HP = _7o.GMT - 0,
    GQ = 0;
    if (!D8) {
        var Yr = J.parentNode;
        D8 = Get(Yr.Def ? Yr: this.XHeader, "CDef");
    } else if (typeof(D8) != "string") return;
    D8 = this.Def[D8];
    if (!D8) D8 = this.Def["R"];
    J.Def = D8;
    if (D8.Expanded != null && J.Expanded == null) J.Expanded = D8.Expanded - 0;
    if (D8.Kind != null && J.Kind == null) J.Kind = D8.Kind;
    if (D8.Visible != null && J.Visible == null) J.Visible = D8.Visible - 0;
    if (D8.Calculated != null && J.Calculated == null) J.Calculated = D8.Calculated - 0;
    if (D8.Spanned != null && J.Spanned == null) J.Spanned = D8.Spanned - 0;
    if (J.Spanned) this.Ym(J);
    if (U0 && J.Space == null) {
        var id = "",
        x, i, B3;
        for (i = 0; i < U0; i++) {
            B3 = this.IdNames[i];
            if (B3 == "Def") x = D8.Name;
            else x = Get(J, B3);
            if (x == null) x = "";
            id += "$" + (x + "").replace(/\$/g, "_");
        }
        J.id = id.slice(1);
        if (this.FullId) {
            var Nd = J.parentNode.id;
            if (Nd) J.id = Nd + "$" + J.id;
        }
    }
    var BY, Ck;
    if (!Yq) {
        var Ys = Grids.OnGetType != null,
        Yt = this.EnumKeys,
        Bg = D3.length,
        C = this.Cols;
        for (var i = 0; i < Bg; i++) {
            var type = J[Yp[i]];
            if (type == null) {
                type = D8[Yp[i]];
                if (type == null) {
                    BY = C[D3[i]];
                    if (BY) type = BY.Type;
                }
            }
            if (Ys) type = Grids.OnGetType(this, J, D3[i], type);
            if (type == "Date") {
                BY = D3[i];
                Ck = J[BY];
                if (Ck && (Ck - 0) + "" != Ck) {
                    J[BY] = Date.parse(Ck);
                    if (isNaN(J[BY]) || HP || GQ || (_7G || _7M) && this.GR(J, BY)) {
                        if (this.GR(J, BY)) {
                            Ck = this.Yn(Ck);
                            if (Ck) J[BY] = (Ck - 0) + "" == Ck ? Ck - 0 : Ck;
                        } else {
                            Ck = GQ ? _7AW(Ck) : _7AX(Ck);
                            if (Ck) J[BY] = Ck.getTime();
                        }
                    }
                }
            } else if (type == "Float") {
                BY = D3[i];
                if (typeof(J[BY]) == "string" && (this.EmptyNumber == null || J[BY]) && ((J[BY] - 0) + "" == J[BY] || !this.GR(J, BY))) J[BY] -= 0;
            } else if (type == "Enum") {
                BY = D3[i];
                if (Yt) {
                    var GS = this.GT(J, BY);
                    if (GS) {
                        var BR = Get(J, BY);
                        if (BR === 0) BR = "0";
                        if (BR) for (var k = 0; k < GS.length; k++) if (GS[k] == BR) {
                            J[BY] = k - 1;
                            break;
                        }
                    }
                }
                var BR = J[BY];
                if (BR && BR - 0 != BR) {
                    var GS = this.GetEnum(J, BY);
                    if (GS) for (var k = 0; k < GS.length; k++) if (BR == GS[k]) {
                        J[BY] = k;
                        break;
                    }
                }
            } else if (type == "Select") {
                BY = D3[i];
                J[BY + "Button"] = "Defaults";
                J[BY + "CanEdit"] = 0;
                J[BY + "Type"] = "Html";
                if (J[BY + "CanFocus"] == null) J[BY + "CanFocus"] = 0;
            }
        }
    }
    if (J.Calculated) {
        if (!Get(J, "CalcOrder")) {
            var Yu = "";
            for (var i = 0; i < Bg; i++) if (Get(J, D3[i] + "Formula")) Yu += (Yu ? ",": "") + D3[i];
            J.CalcOrder = Yu;
        }
        if (this.Calculating == null) this.Calculating = 1;
    }
};
_7m.prototype.Yv = function (row, U0, D3, Yp, Yq) {
    for (var J = row.firstChild; J; J = J.nextSibling) {
        this.Yo(J, U0, D3, Yp, Yq);
        if (J.firstChild) this.Yv(J, U0, D3, Yp, Yq);
    }
};
_7m.prototype.Yw = function (Bj, U0, D3, Yp, Yq) {
    var B1 = Bj.Children;
    if (!B1) return;
    for (var i = 0; i < B1.length; i++) {
        var Ck = B1[i];
        if (!Ck.Def) Ck.Def = 'R';
        this.Yo(Ck, U0, D3, Yp, Yq);
        this.Yw(Ck, U0, D3, Yp, Yq);
    }
};
_7m.prototype.Yx = function (J) {
    var R = this.Def[J.Def];
    if (R) this.Yx(R);
    for (var i in R) if (J[i] == null) J[i] = R[i];
    delete J.Def;
};
_7m.prototype.F5 = function (row, Yy) {
    var C = this.Cols,
    D3 = new Array(),
    Yp = new Array(),
    Bg = 0,
    U0 = 0;
    var R = this.Def["R"],
    HP = _7o.GMT - 0,
    GQ = 0;
    for (var BY in C) {
        if (BY == "Panel") continue;
        Yp[Bg] = BY + "Type";
        D3[Bg++] = BY;
        if (R[BY] == null) R[BY] = "";
        if (C[BY].Type == "Select") {
            C[BY]["Button"] = "Defaults";
            C[BY]["CanEdit"] = 0;
            C[BY]["Type"] = "Html";
            if (C[BY]["CanFocus"] == null) C[BY]["CanFocus"] = 0;
        }
    }
    var P = this.Def;
    for (var J in P) if (P[J].Def) this.Yx(P[J]);
    if (!row) {
        if (!this.IdNames) this.IdNames = "id";
        if (typeof(this.IdNames) == "string") {
            this.IdNames = this.IdNames.split(",");
            this.At = null;
        }
        U0 = this.IdNames.length != 1 || this.IdNames[0] != "id" ? this.IdNames.length: 0;
        if (!U0 && this.FullId) U0 = 1;
        this.Au = U0;
        if (!this.At) {
            this.At = new Object();
            for (var i = 0; i < this.IdNames.length; i++) this.At[this.IdNames[i]] = 1;
        }
        this.Av = this.IdNames[this.IdNames.length - 1];
    } else var U0 = this.Au;
    if (!row && this.Calculating == null) {
        for (var BY in C) if (C[BY].Formula) {
            this.Calculating = 1;
            break;
        }
    }
    if (!Yy) for (var Yz in this.Def) {
        var J = this.Def[Yz];
        if (J.Fo) continue;
        J.Fo = 1;
        this.Yw(this.Def[Yz], U0, D3, Yp);
        var BY, Ck, Y0 = 0;
        if (J.Kind != "User") for (var i = 0; i < Bg; i++) {
            BY = D3[i];
            var type = J[BY + "Type"];
            if (type == null) type = this.Cols[BY].Type;
            if (type == "Date") {
                Ck = J[BY];
                if (Ck && (Ck - 0) + "" != Ck) {
                    J[BY] = Date.parse(Ck);
                    if (isNaN(J[BY]) || HP || GQ) {
                        var Wg = J[BY + "Range"];
                        if (Wg == null && this.Cols[BY]) Wg = this.Cols[BY]["Range"];
                        if (Wg) {
                            Ck = this.Yn(Ck);
                            if (Ck) J[BY] = (Ck - 0) + "" == Ck ? Ck - 0 : Ck;
                        } else {
                            Ck = GQ ? _7AW(Ck) : _7AX(Ck);
                            if (Ck) J[BY] = Ck.getTime();
                        };
                    }
                }
            } else if (type == "Float" && typeof(J[BY]) == "string") {
                if (J[BY] || this.EmptyNumber == null) {
                    var Wg = J[BY + "Range"];
                    if (Wg == null && this.Cols[BY]) Wg = this.Cols[BY]["Range"];
                    if (!Wg || (J[BY] - 0) + "" == J[BY]) J[BY] -= 0;
                }
            }
            var BR = J[BY];
            if (BR && BR - 0 != BR) {
                var GS = J[BY + "Enum"];
                if (!GS) GS = C[BY].Enum;
                if (GS) {
                    GS = this.Ua(GS);
                    for (var k = 0; k < GS.length; k++) if (BR == GS[k]) {
                        J[BY] = k;
                        break;
                    }
                }
            }
            if (J[BY + "CopyTo"]) Y0 = 1;
        }
        if (Y0) J._7Av = 1;
        if (J.Calculated && this.Calculating == null) this.Calculating = 1;
        if (J.Spanned) this.Ym(J);
    }
    if (row) {
        if (Yy) this.Yo(row, U0, D3, Yp);
        else this.Yv(row, U0, D3, Yp);
        return;
    }
    var KL = this.XHeader;
    if (!KL.Def) KL.Def = new Object;
    KL.CDef = KL.CDef != null ? KL.CDef: "R";
    if (KL.Visible == null) KL.Visible = 1;
    KL.Fixed = "Header";
    var R = this.GetFixedRows(),
    Y1 = this.XH.childNodes.length;
    for (var J = 0; J < R.length; J++) {
        if (!R[J].Def) R[J].Def = "Fixed";
        this.Yo(R[J], U0, D3, Yp);
        R[J].Fixed = J >= Y1 ? "Foot": "Head";
    }
    var Yq = this.Prepared;
    for (var H = this.XB.firstChild; H; H = H.nextSibling) this.Yv(H, U0, D3, Yp, Yq);
    this.XB.firstChild.Page = 1;
    if (!this.Paging) {
        this.XB.firstChild.State = 4;
        var CE = this.XB.firstChild;
        if (CE.nextSibling) {
            for (var H = CE.nextSibling; H; H = CE.nextSibling) {
                for (var J = H.firstChild; J; J = H.firstChild) CE.appendChild(J);
                this.XB.removeChild(H);
            }
        }
    } else if (this.Paging == 2) {
        for (var Bc = this.XB.firstChild; Bc; Bc = Bc.nextSibling) {
            Bc.Page = 1;
            Bc.State = 2;
        }
    } else if (this.Paging == 3) {
        if (this.FB) this.FB();
        else _7w("ReloadBody");
    }
    if (this.Calculating == null) this.Calculating = 0;
    for (var J = this.XS.firstChild; J; J = J.nextSibling) {
        var Y2 = J.Cells;
        var Y3 = new Array();
        if (Y2) {
            for (var i = 0; i < Y2.length; i++) Y3[i] = Y2[i] + "Type";
        } else Y2 = new Array();
        this.Yo(J, U0, Y2, Y3);
    }
};
_7m.prototype.FB = function () {
    if (this.RootCount && this.XB.lastChild.Count == null) this.XB.lastChild.Count = this.RootCount - (this.XB.childNodes.length - 1) * this.PageLength;
    for (var Bg = this.XB.firstChild, I = 0; Bg; Bg = Bg.nextSibling) {
        if (Bg.State == null) Bg.State = Bg.firstChild || this.Paging != 3 ? 2 : 0;
        Bg.Page = 1;
        Bg.Pos = I++;
        if (Bg.State >= 2) Bg.Count = null;
        else if (!Bg.Count) Bg.Count = this.PageLength;
        if (!this.XB.Fc && Bg.firstChild) this.F5(Bg);
    }
};
_7m.prototype.Yd = function () {
    var BG = this.Focused;
    if (BG != null && !this.IgnoreFocused) {
        var H;
        if (typeof(BG) == "number") H = _7c(this.XB, BG);
        else for (H = this.XB.firstChild; H; H = H.nextSibling) if (H.id == BG) break;
        if (H) {
            this.Focus(H, this.FocusedCol, this.FocusedPos);
        } else {
            BG = this.GetRowById(BG);
            this.Focus(BG, this.FocusedCol);
        }
    }
    this.Focused = null;
    this.FocusedPos = null;
    this.FocusedCol = null;
};
_7m.prototype.E0 = function (row) {
    if (row.tagName == "BR") {
        var J = _7f.createElement(row.Page ? "B": "I");
        for (var i in row) if (!Grids.A8[i]) J[i] = row[i];
        J.id = row.id;
        row.parentNode.replaceChild(J, row);
        if (this.FRow == row) this.FRow = J;
        row = J;
    }
    return row;
};
_7m.prototype._7d = function (row, type) {
    for (var J = row.firstChild; J; J = J.nextSibling) {
        if (J.r0) J.r0.row = null;
        if (J.r1) {
            J.r1.row = null;
            this.RowCount--;
        }
        if (J.r2) J.r2.row = null;
        J.r0 = null;
        J.r1 = null;
        J.r2 = null;
        if (type == 1) {
            J.Def = null;
            if (J.CalcOrder) J.CalcOrder = null;
        }
        if (J.State >= 3) J.State = 2;
        else if (J.State == 1) J.State = 0;
        if (J.firstChild) this._7d(J);
        J._7Ax = null;
    }
};
_7m.prototype.Dispose = function () {
    this.Clear(1);
    Grids[this.id] = null;
    if (Grids.Active == this) Grids.Active = null;
    if (Grids.Focused == this) Grids.Focused = null;
};
_7m.prototype.Clear = function (type) {
    this.Ax++;
    if (this.XB) try {
        this._7d(this.XB, type);
        if (type & 3) {
            var CE = this.GetFixedRows();
            for (var i = 0; i < CE.length; i++) {
                var J = CE[i];
                if (J.r0) J.r0.row = null;
                if (J.r1) J.r1.row = null;
                if (J.r2) J.r2.row = null;
                J.r0 = null;
                J.r1 = null;
                J.r2 = null;
                if (type == 1) {
                    J.Def = null;
                    J.CalcOrder = null;
                }
            }
            for (var J = this.XS.firstChild; J; J = J.nextSibling) {
                if (J.r1) J.r1.row = null;
                J.r1 = null;
                if (type == 1) {
                    J.Cells = null;
                    J.Def = null;
                    J.CalcOrder = null;
                }
            }
            var J = this.XHeader;
            if (J.r0) J.r0.row = null;
            if (J.r1) J.r1.row = null;
            if (J.r2) J.r2.row = null;
            J.r0 = null;
            J.r1 = null;
            J.r2 = null;
            if (type == 1) J.Def = null;
        }
    } catch(B) {};
    if (this.ARow && (!this.ARow.Fixed || type & 3)) this.ARow = null;
    if (this.AT && (!this.AT.Fixed || type & 3)) this.AT = null;
    if (type & 4 && this.E2) {
        var H = this.XB.firstChild;
        if (H && H.MasterRow) this.Ja(H.MasterRow);
        H.MasterRow = null;
    }
    if (type & 1 && this.MainTag) {
        this.n = null;
        this.Loading = 0;
        this.Rendering = 0;
        this.MainTag.innerHTML = "";
    }
    if (type & 1) {
        try {
            this.HideMessage();
        } catch(B) {};
        try {
            this.HideTip();
        } catch(B) {};
        try {
            this.CloseDialog();
        } catch(B) {};
        try {
            this.HideHint();
        } catch(B) {};
        this.FRow = null;
        try {
            if (this.XB) _7d(this.XB);
        } catch(B) {};
        try {
            if (this.XF) _7d(this.XF);
        } catch(B) {};
        try {
            if (this.XH) _7d(this.XH);
        } catch(B) {};
        try {
            if (this.XS) _7d(this.XS);
        } catch(B) {};
        try {
            if (this.XHeader) _7d(this.XHeader);
        } catch(B) {};
        this.JZ = 1;
    }
};
_7m.prototype.FD = function () {
    if ((this.Cl || this.Cm) && this.SaveExpanded) {
        var E = this.Cl,
        C = this.Cm;
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
            if (Is(J, "CanExpand") && J.id) {
                if (E.search("&" + J.id + "&") >= 0) J.Expanded = 1;
                else if (C.search("&" + J.id + "&") >= 0) J.Expanded = 0;
            }
        }
    }
    if (this.Cn && this.SaveSelected) {
        var S = this.Cn;
        for (var J = this.GetFirst(); J; J = this.GetNext(J)) {
            if (Is(J, "CanSelect") && J.id) {
                if (S.search("&" + J.id + "&") >= 0) J.Selected = 1;
                else J.Selected = 0;
            }
        }
    }
    if (this.SaveValues) {
        var S = this.Co;
        for (var VB in S) {
            var J = this.GetRowById(VB);
            if (J) {
                var Cr = S[VB].split("&"),
                Bg = 1;
                if (Cr[0] == 'D') {
                    J.Deleted = 1;
                    J.Visible = 0;
                } else if (Cr[0] == 'A') {
                    J.Added = 1;
                    J.Par = Cr[1];
                    J.Def = this.Def[Cr[2]];
                    J.Parent = (Cr[3] - 0) >= 0 ? _7c(this.XB, Cr[3] - 0) : (Cr[3] ? this.GetRowById(Cr[3]) : null);
                    J.Next = Cr[4] ? this.GetRowById(Cr[4]) : null;
                    J.Copy = Cr[5] ? this.GetRowById(Cr[5]) : null;
                    Bg = 6;
                } else if (Cr[0] == 'M') {
                    J.Moved = Cr[1] - 0;
                    J.Parent = (Cr[2] - 0) >= 0 ? _7c(this.XB, Cr[2] - 0) : (Cr[2] ? this.GetRowById(Cr[2]) : null);
                    J.Next = Cr[3] ? this.GetRowById(Cr[3]) : null;
                    Bg = 4;
                } else if (Cr[0] == 'C') J.Changed = 1;
                for (var i = Bg; i < Cr.length; i += 2) {
                    var BZ = unescape(Cr[i + 1]);
                    if ((BZ - 0) + "" == BZ) BZ = BZ - 0;
                    J[Cr[i]] = BZ;
                    J[Cr[i] + "Changed"] = 1;
                }
            }
        }
    }
};
_7m.prototype.ChangeDef = function (J, D8, Bx) {
    if (typeof(D8) == "string") D8 = this.Def[D8];
    if (!D8) return false;
    J.Def = D8;
    if (D8.Expanded != null) J.Expanded = D8.Expanded - 0;
    if (D8.Kind != null) J.Kind = D8.Kind;
    if (D8.Visible != null) J.Visible = D8.Visible - 0;
    if (D8.Calculated != null) J.Calculated = D8.Calculated - 0;
    var Y4 = J.Spanned;
    if (D8.Spanned != null) J.Spanned = D8.Spanned - 0;
    if (Y4 || J.Spanned) this.Ym(J);
    if (J.Calculated) {
        if (!Get(J, "CalcOrder")) {
            var Yu = "";
            for (var i = 0; i < Bg; i++) if (Get(J, D3[i] + "Formula")) Yu += (Yu ? ",": "") + D3[i];
            J.CalcOrder = Yu;
        }
        if (this.Calculating == null) this.Calculating = 1;
    }
    this.Recalculate(J);
    if (Bx != 0) this.RefreshRow(J);
};
_7m.prototype.Y5 = function (Value, DG, Y6) {
    if (Value == null) Value = "";
    else Value += "";
    if (!DG) return Y6 ? this.Escape(Value).replace(new RegExp(_7U ? "^ | $| ": "^ | $| (?= )", "g"), _7D).replace(/\n/g, "<br/>") : Value;
    var BG = DG.split(DG.charAt(0));
    if (BG[4]) {
        try {
            Value = Value.replace(new RegExp(BG[4], BG[5]), BG[6]);
        } catch(B) {
            alert((B.message ? B.message: B) + "\n\nin value: " + Value + "\nwith format: " + DG + "\nregex: /" + BG[4] + "/" + BG[5] + " => " + BG[6]);
        }
    }
    switch (BG[1] - 0) {
    case 1:
        Value = Value.toLowerCase();
        break;
    case 2:
        Value = Value.toUpperCase();
        break;
    case 3:
        Value = Value.toLocaleLowerCase();
        break;
    case 4:
        Value = Value.toLocaleUpperCase();
        break;
    };
    return (BG[2] ? (Y6 > 1 ? this.Escape(BG[2]) : BG[2]) : "") + (Y6 ? this.Escape(Value).replace(new RegExp(_7U ? "^ | $| ": "^ | $| (?= )", "g"), _7D).replace(/\n/g, "<br/>") : Value) + (BG[3] ? (Y6 > 1 ? this.Escape(BG[3]) : BG[3]) : "");
};
_7m.prototype.Y7 = function (Value, DG) {
    if (!Value) Value = "|";
    else Value += "";
    Value = Value.split(Value.charAt(0));
    if (!DG) return Value.join("");
    var BG = DG.split(DG.charAt(0)),
    Bh = "",
    BN = BG.length,
    k = 5,
    Jm = BG[1] - 0,
    Nn = Value.length;
    for (var i = 1; i < Nn; i++) {
        if (BG[k] && (i != 1 || !(Jm & 1))) Bh += BG[k];
        if (BG[k + 2]) Value[i] = Value[i].replace(new RegExp(BG[k + 2], BG[k + 3]), BG[k + 4]);
        if (Value[i]) Bh += Jm & 2 ? this.Escape(Value[i]) : Value[i];
        if (BG[k + 1] && (i != Nn - 1 || !(Jm & 1))) Bh += BG[k + 1];
        k += 6;
        if (k >= BN) k = 5;
    }
    return (BG[2] ? BG[2] : "") + Bh + (BG[3] ? BG[3] : "");
};
_7m.prototype.O2 = function (row, BJ, type, Tb, BR) {
    if (Get(row, BJ + "Visible") == 0) {
        if (type == "Bool") return "<div class='" + this.Img.Style + "Text'>" + _7D + "</div>";
        return _7D;
    }
    if (BR == null) BR = Get(row, BJ);
    if (Grids.OnGetHtmlValue) {
        var BZ = Grids.OnGetHtmlValue(this, row, BJ, BR);
        if (BZ != null) return BZ;
    }
    switch (type) {
    case "Lines":
    case "Text":
        BR = this.Y5(BR, this.GetFormat(row, BJ), this.NoFormatEscape ? 1 : 2);
        return BR ? BR: _7D;
    case "Bool":
        if (this.TC) return "<center><input type=checkbox disabled" + _7E + " " + (BR - 0 ? " checked" + _7E: "") + " class='" + this.Img.Style + "IText'></center>";
        if (row.Space != null) return "<center>" + this.HW(14, 300 + (BR - 0 ? this.Img.SpaceRow * 2 : this.Img.SpaceRow), this.Img.SpaceRow, null, Tb ? _7a: "", this.Img.SpaceHeight) + "</center>";
        return "<center>" + this.HW(15, BR - 0 ? this.Img.Row: 0, this.Img.Row, null, Tb ? _7a: "") + "</center>";
    case "Int":
    case "Float":
        var HD = this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "";
        if (BR === "" && this.EmptyNumber != null) BR = this.EmptyNumber;
        else BR = NumberToString(BR, this.GetFormat(row, BJ), HD, HD ? this.Lang.Format.ValueSeparatorHtml: null, HD ? this.Lang.Format.RangeSeparatorHtml: null);
        if (!BR || BR == " ") return _7D;
        return this.NoFormatEscape ? BR: this.Escape(BR);
    case "Enum":
        if (BR === "" && this.EmptyEnum != null) {
            BR = this.EmptyEnum;
            if (!BR || BR == " ") return _7D;
            return BR;
        }
        return this.Y8(row, BJ, 1);
    case "Html":
        BR = this.Y5(BR, this.GetFormat(row, BJ), 0);
        return BR ? BR: _7D;
    case "Pass":
        return "***";
    case "Date":
        var HD = this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "";
        if (BR === "" && this.EmptyDate != null) BR = this.EmptyDate;
        else if (!BR && this.EmptyDate == null) BR = "";
        else BR = DateToString(BR, this.GetFormat(row, BJ), HD, HD ? this.Lang.Format.ValueSeparatorHtml: null, HD ? this.Lang.Format.RangeSeparatorHtml: null);
        if (!BR || BR == " ") return _7D;
        return this.NoFormatEscape ? BR: this.Escape(BR);
    case "Link":
        if (!BR || !BR.split) return _7D;
        BR = BR.split(BR.charAt(0));
        var Nv = Tb != 1,
        BG = this.GetFormat(row, BJ),
        Ec = "",
        Ly = "";
        if (BG) {
            BG = BG.split(BG.charAt(0));
            if (BG[3]) Ec = BG[3];
            if (BG[4]) Ly = BG[4];
            BR[1] = (BG[5] & 1 && this.BaseUrl ? this.BaseUrl: "") + (BG[1] ? BG[1] : "") + BR[1] + (BG[2] ? BG[2] : "");
        }
        return Ec + "<a href='" + this.Escape(BR[1]) + "'" + (BR[3] ? " target='" + BR[3] + "'": "") + " onclick='CancelEvent(event,2);'" + (Nv ? "": " style='cursor:default;'") + ">" + (BR[2] ? BR[2] : BR[1]) + "</a>" + Ly;
    case "Img":
        if (!BR || !BR.split) return _7D;
        BR = BR.split(BR.charAt(0));
        if (BR[1] == "") return _7D;
        var Nv = BR[6] && Tb != 1,
        BG = this.GetFormat(row, BJ),
        Ec = "",
        Ly = "";
        if (BG) {
            BG = BG.split(BG.charAt(0));
            if (BG[3]) Ec = BG[3];
            if (BG[4]) Ly = BG[4];
            BR[1] = (BG[7] & 1 && this.BaseUrl ? this.BaseUrl: "") + (BG[1] ? BG[1] : "") + BR[1] + (BG[2] ? BG[2] : "");
            if (BR[6]) BR[6] = (BG[7] & 2 && this.BaseUrl ? this.BaseUrl: "") + (BG[5] ? BG[5] : "") + BR[6] + (BG[6] ? BG[6] : "");
        }
        if (Nv) {
            Ec += "<a href='" + this.Escape(BR[6]) + "' onclick='CancelEvent(event,2);'>";
            Ly = "</a>" + Ly;
            BR[6] = "";
        }
        if (!BR[4] || !BR[5]) {
            if (BR[3] && _7F) {
                Ec = "<div style='overflow:hidden;height:" + BR[3] + "px;'>";
                Ly = "</div>";
            }
            return Ec + "<img border='0'" + (BR[2] ? " width='" + BR[2] + "'": "") + (BR[3] ? " height='" + BR[3] + "'": "") + " src='" + this.EH(this.Escape(BR[1])) + "'" + (Nv ? " style='" + _7a + "' title='" + this.Escape(BR[6]) + "'": "") + "/>" + Ly;
        }
        return Ec + (_7V ? "<div><b style='display:inline-block;": "<div style='") + (Nv ? _7a: "") + "width:" + BR[2] + "px;height:" + BR[3] + "px;overflow:hidden;background:url(" + this.EH(this.Escape(BR[1])) + ") -" + BR[4] + "px -" + BR[5] + "px;'" + (Nv ? " title='" + this.Escape(BR[6]) + "'": "") + ">" + (_7X ? _7D: "") + (_7V ? "</div></b>": "</div>") + Ly;
    case "List":
        BR = this.Y7(BR, this.GetFormat(row, BJ));
        return BR ? BR: _7D;
    case "Radio":
        var BG = this.GetFormat(row, BJ);
        if (!BG) return "";
        BG = BG.split(BG.charAt(0));
        var Mw = BG.length;
        if (Mw <= 7) return "";
        var I8 = BG[1] - 0;
        var Bh = BG[2] ? BG[2] : "",
        TH,
        Ly;
        if (this.TC) {
            var Y9 = "<td><input type='radio' disabled" + _7E + " class='" + this.Img.Style + "IText'></td>";
            var ZA = "<td><input type='radio' disabled" + _7E + " class='" + this.Img.Style + "IText' checked" + _7E + "></td>";
        } else {
            var Y9 = "<td>" + this.HW(15, I8 & 32 ? 0 : 51, this.Img.Row, null, Tb ? _7a: "") + "</td>";
            var ZA = "<td>" + this.HW(15, I8 & 32 ? 17 : 68, this.Img.Row, null, Tb ? _7a: "") + "</td>";
        };
        if ((I8 & 3) == 0) {
            Bh += _7A + _7B + "<tr>";
            TH = "";
            Ly = "";
        } else if ((I8 & 3) == 1) {
            TH = _7A + "style='float:left;'" + _7B + "<tr>";
            Ly = "</tr>" + _7C;
        } else {
            TH = _7A + _7B + "<tr>";
            Ly = "</tr>" + _7C;
        };
        for (var i = 7; i < Mw; i++) {
            var Hm = I8 & 16 ? BR & (1 << (i - 7)) ? ZA: Y9: i - 7 === BR ? ZA: Y9;
            Bh += TH + (I8 & 4 ? "": Hm) + "<td class='" + this.Img.Style + "Radio'>" + (BG[5] ? BG[5] : "") + (I8 & 8 ? this.Escape(BG[i]) : BG[i]) + (BG[6] ? BG[6] : "") + "</td>" + (I8 & 4 ? Hm: "") + Ly;
        }
        if ((I8 & 3) == 0) Bh += "</tr>" + _7C;
        if (BG[3]) Bh += BG[3];
        return Bh;
    case "Button":
        var BO = Get(row, BJ + "ClassInner");
        var checked = Get(row, BJ) ? "Checked": "";
        var QW = Get(row, BJ + "Caption");
        var title = Get(row, BJ + "ToolTip" + checked);
        if (title == null) title = Get(row, BJ + "ToolTip");
        if (title) title = this.Escape(title);
        if (QW) {
            if (!BO) BO = this.Img.Style + "ButtonSpace" + checked;
            else BO = this.Img.Ib + BO;
            var CY = Get(row, BJ + "Width");
            CY = CY ? " style='width:" + CY + "px'": "";
            return "<div class='" + this.Img.Style + "CellSpaceButton'" + CY + ">" + "<button class='" + BO + "'" + CY + (title ? " title='" + title + "'": "") + ">" + QW + "</button></div>";
        } else {
            if (!BO) BO = this.Img.Style + "ToolbarImg";
            else BO = this.Img.Ib + BO;
            var Oz = Get(row, BJ + "Icon" + checked);
            if (Oz == null) Oz = Get(row, BJ + "Icon");
            var CZ = Get(row, BJ + "Height");
            if (CZ == null) CZ = this.Img.ToolbarHeight;
            return "<div class='" + BO + checked + "'" + (this.Hover >= 1 ? " onmouseover='this.className=\"" + BO + "Hover" + checked + "\"'" + " onmouseout='this.className=\"" + BO + checked + "\"'": "") + ">" + (Oz - 0 == Oz ? "<div " + (_7V ? "><b style='display:inline-block;": " style='") + " width:" + this.Img.ToolbarWidth + "px;height:" + this.Img.ToolbarHeight + "px;overflow:hidden;background:url(" + this.Img.Toolbar + ") " + ( - Oz * this.Img.ToolbarWidth) + "px 0px'" + (title ? " title='" + title + "'": "") + ">" + (_7X ? _7D: "") + "</div>" + (_7V ? "</b>": "") : "<div style='height:" + CZ + "px'><div class='" + this.Img.Style + "ToolbarText'>" + Oz + "</div></div>") + "</div>";
        };
    case "Abs":
    case "Gantt":
        if (!BR) return _7D;
        row.Yf = 1;
        BR += "";
        var BZ = BR.split(BR.charAt(0)),
        BN = BZ.length,
        Bh = "",
        J0 = this.HV ? "margin-right:": "margin-left:";
        if (type == "Gantt") {
            var M1 = row[BJ + "Gdep"];
            if (M1 != null) BZ[M1 * 5 + 5] = this.Nl(row, BJ);
        }
        for (var i = 1; i < BN; i += 5) {
            var x = BZ[i] - 0;
            var y = BZ[i + 1] - 0;
            var CY = BZ[i + 2];
            var CZ = BZ[i + 3];
            CZ = CZ === "" ? 512 : CZ - 0;
            Bh += "<div style='" + (x ? J0 + x + "px;": "") + (CY ? "width:" + CY + "px;": "") + "margin-top:" + (i == 1 ? y: y - 512) + "px;height:" + CZ + "px;margin-bottom:" + (512 - y - CZ) + "px;overflow:hidden;'>" + BZ[i + 4] + "</div>";
        }
        return Bh;
    case "DropCols":
        BR = BR ? (BR + "").split(',') : new Array();
        var BL = _7A + _7B + "<tr>";
        for (var i = 0; i < BR.length; i++) {
            if (!BR[i] || !this.Cols[BR[i]]) continue;
            var QW = Get(this.XHeader, BR[i]);
            BL += "<td class='" + this.Img.Style + "HeaderGroup'><div class='" + this.Img.Style + "GroupText'" + this.KP(this.Cols[BR[i]].Width) + ">";
            BL += this.XHeader.NoEscape ? QW: this.Escape(QW);
            BL += "</div></td>";
        }
        var OV = Get(row, BJ + "Text");
        if (OV == null) OV = this.GetText("GroupCustom");
        if (!this.TC) BL += "<td nowrap" + _7E + " class='" + this.Img.Style + "GroupCustom'>" + OV + "</td>";
        BL += "</tr>" + _7C;
        return BL;
    default:
        return "#Error";
    }
};
_7m.prototype.KX = function (row, BJ, DD) {
    var V = new Object(),
    BZ;
    if (Grids.OnGetExportValue) {
        BZ = Grids.OnGetExportValue(this, row, BJ, DD);
        if (BZ != null) return BZ;
    }
    BZ = Get(row, BJ + "ExportValue");
    if (BZ != null) return DD + BZ;
    var Value = Get(row, BJ),
    type = this.GetType(row, BJ),
    DG = this.GetFormat(row, BJ);
    switch (type) {
    case "Bool":
        if (DG) {
            DG = (DG + "").split((DG + "").charAt(0));
            if (DG.length > 2) return DD + DG[Value ? 2 : 1];
        }
        return DD + (Value ? 1 : 0);
    case "Int":
    case "Float":
        if (Value === "" && this.EmptyNumber != null) return " x:str=''" + DD;
        if (typeof(Value) == "string" && this.GR(row, BJ)) return DD + NumberToString(Value, DG, this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator);
        if (isNaN(Value - 0)) return " x:str=''" + DD;
        Value -= 0;
        if (Value > -1e-10 && Value < 1e-10) Value = 0;
        return " x:num='" + Value + "'" + DD + NumberToString(Value, DG);
    case "Date":
        if (Value === "" || !Value && this.EmptyDate == null) return " x:str=''" + DD;
        if (this.ExportType & 64) {
            var KZ = Get(row, BJ + "ExportFormat");
            if (!KZ) KZ = this.Cols[BJ].ExportFormat;
            if (KZ) DG = KZ;
        }
        if (typeof(Value) == "string" && this.GR(row, BJ)) return DD + DateToString(Value, DG, this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator);
        var Hb = "gd",
        BG = DG.toLowerCase();
        if (!DG || BG == 'd' || BG == 'm') Hb = "sd";
        else if (BG == 't') Hb = "st";
        else if (BG.length > 1) {
            if (DG.search(/[dMy]/) < 0) Hb = "st";
            else if (DG.search(/[hHmst]/) < 0) Hb = "sd";
        }
        var Bb = (Value / 60000 - (new Date(Value)).getTimezoneOffset() - Date.UTC(1900, 0, 1) / 60000) / 1440;
        var BR = DateToString(Value - 0, DG);
        return " class='" + Hb + (this.ExportType & 64 ? "' x:str='" + BR: "' x:num='" + (Bb + 2)) + "'" + DD + BR;
    case "Text":
        Value = this.Y5(Value, DG);
        if (Value == "") return " x:str=''" + DD;
        Value = Value.replace(/\&/g, "&amp;").replace(/\</g, "&lt;");
        if (this.ExportType & 4) return ' x:str="' + Value.replace(/\"/g, "&quot;") + '"' + DD + Value;
        return DD + Value;
    case "Lines":
        Value = this.Y5(Value, DG);
        if (Value == "") return " x:str=''" + DD;
        Value = Value.replace(/\&/g, "&amp;").replace(/\</g, "&lt;");
        if (this.ExportType & 4 && Value.indexOf('\n') < 0) return ' x:str="' + Value.replace(/\"/g, "&quot;") + '"' + DD + Value.replace(/\n/g, "<br>");
        return DD + Value.replace(/\n/g, "<br>");
    case "Link":
        if (!Value || !Value.split) return DD;
        Value = Value.split(Value.charAt(0));
        var Ec = "",
        Ly = "";
        if (DG) {
            DG = DG.split(DG.charAt(0));
            if (DG[3]) Ec = DG[3];
            if (DG[4]) Ly = DG[4];
            Value[1] = (DG[5] & 1 && this.BaseUrl ? this.BaseUrl: "") + (DG[1] ? DG[1] : "") + Value[1] + (DG[2] ? DG[2] : "");
        }
        if (!Value[1] || Value[1].search(/javascript\:/) >= 0) Value[1] = "";
        if (this.ExportType & 4 && Value[2]) DD = ' x:str="' + Value[2].replace(/\"/g, "&quot;") + '"' + DD;
        return DD + Ec + "<a href='" + this.Escape(Value[1]) + "'>" + (Value[2] ? Value[2] : Value[1]) + "</a>" + Ly;
    case "Img":
        if (!Value || !Value.split) return DD;
        Value = Value.split(Value.charAt(0));
        if (Value[6] == "") return DD;
        var Ec = "",
        Ly = "";
        if (DG) {
            DG = DG.split(DG.charAt(0));
            if (DG[3]) Ec = DG[3];
            if (DG[4]) Ly = DG[4];
            Value[6] = (DG[7] & 2 && this.BaseUrl ? this.BaseUrl: "") + (DG[5] ? DG[5] : "") + Value[6] + (DG[6] ? DG[6] : "");
        }
        if (!Value[6] || Value[6].search(/javascript\:/) >= 0) Value[6] = "";
        return DD + Ec + "<a href='" + this.Escape(Value[6]) + "'>" + this.GetText("Picture") + "</a>" + Ly;
    case "List":
    case "Abs":
    case "Gantt":
    case "Html":
        return DD;
    default:
        return DD + this.ValueToString(Value, type, type == "Enum" ? this.GetEnum(row, BJ, 1) : DG, 1);
    }
};
_7m.prototype.FJ = function (row, BJ) {
    var DG = this.GetFormat(row, BJ, 1),
    Value = Get(row, BJ),
    type = this.GetType(row, BJ);
    var V = new Object();
    V.Type = type;
    if (this.FI) V.Mask = this.FI(row, BJ, 1);
    switch (type) {
    case "Int":
    case "Float":
        if (Value === "" && this.EmptyNumber != null) V.Value = "";
        else V.Value = NumberToString(Value, DG, this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "");
        return V;
    case "Date":
        if (!Value && this.EmptyDate == null || Value === "") V.Value = "";
        else V.Value = DateToString(Value, DG, this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "");
        return V;
    case "Text":
        V.Value = this.Y5(Value, DG);
        return V;
    case "Lines":
        V.Value = this.Y5(Value, DG);
        return V;
    case "Link":
        if (!Value) Value = '|';
        else Value += "";
        Value = Value.split(Value.charAt(0));
        for (var i = 1; i < 4; i++) if (!Value[i]) Value[i] = "";
        if (!DG) DG = "|0|1";
        var BG = DG.split(DG.charAt(0));
        var BR = "",
        FH = "^";
        if (BG[2] - 0) {
            if (BG[3] - 0) {
                BR += "URL: ";
                FH += "URL\\: ";
            }
            BR += Value[1];
            FH += ".*";
        }
        if (BG[3] - 0) {
            if (BG[2] - 0) {
                if (BG[1] - 0 == 2) {
                    BR += "\n";
                    FH += "\\r?\\n";
                } else {
                    BR += "; ";
                    FH += "; ";
                };
                BR += "TEXT: ";
                FH += "TEXT\\: ";
            }
            BR += Value[2];
            FH += ".*";
        }
        FH += "$";
        if (BG[1] - 0) V.Type = "Lines";
        V.Mask = FH;
        V.Value = BR;
        return V;
    case "Img":
        if (!Value) Value = '|';
        Value = Value.split(Value.charAt(0));
        for (var i = 1; i < 7; i++) if (!Value[i]) Value[i] = "";
        if (!DG) DG = "|0|1";
        var BG = DG.split(DG.charAt(0));
        var BR = "",
        FH = "^";
        if (BG[2] - 0) {
            if (BG[3] - 0 || BG[4] - 0) {
                BR += "SRC: ";
                FH += "SRC: ";
            }
            BR += Value[1];
            FH += ".*";
        }
        if (BG[3] - 0) {
            var BZ = "W:" + Value[2] + ",H:" + Value[3] + "; X:" + Value[4] + ",Y:" + Value[5];
            var C0 = "W\\:\\d{0,3},H\\:\\d{0,3};\\sX\\:\\d{0,4},Y\\:\\d{0,4}";
            if (BG[2] - 0 && BG[1] - 0 == 2) {
                BR += "\n" + BZ;
                FH += "\\r?\\n" + C0;
            } else {
                BR += " [" + BZ + "]";
                FH += " \\[" + C0 + "]";
            }
        }
        if (BG[4] - 0) {
            if (FH.length > 1) {
                if (BG[1] - 0 == 2) {
                    BR += "\n";
                    FH += "\\r?\\n";
                } else {
                    BR += "; ";
                    FH += "; ";
                }
            }
            BR += "URL: " + Value[6];
            FH += "URL: .*";
        }
        FH += "$";
        if (BG[1] - 0) V.Type = "Lines";
        V.Mask = FH;
        V.Value = BR;
        return V;
    default:
        V.Value = this.ValueToString(Value, type, type == "Enum" ? this.GetEnum(row, BJ, 1) : DG, 1);
        return V;
    }
};
_7m.prototype.Iq = function (row, BJ, Value) {
    var DG = this.GetFormat(row, BJ, 1),
    type = this.GetType(row, BJ);
    switch (type) {
    case "Int":
    case "Float":
        if (Value === "" && this.EmptyNumber != null) return "";
        var HD = this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "";
        Value = StringToNumber(Value, DG, HD);
        return type == "Int" && !HD ? Math.round(Value) : Value;
    case "Date":
        if (Value === "" && this.EmptyDate != null) return "";
        if (Value == this.Lang.Format.NaN) return '/';
        if (this.GR(row, BJ)) return this.Yn(Value, DG ? DG: "");
        Value = StringToDate(Value, DG);
        if (Value) return Value.getTime();
        return '/';
    case "Text":
    case "Lines":
        if (!DG) return Value;
        var BG = DG.split(DG.charAt(0));
        if (BG[2] != "" && Value.slice(0, BG[2].length) == BG[2]) Value = Value.slice(BG[2].length);
        if (BG[3] != "" && Value.slice(Value.length - BG[3].length, Value.length) == BG[3]) Value = Value.slice(0, Value.length - BG[3].length);
        return Value;
    case "Pass":
        return Value;
    case "Link":
        if (!DG) DG = "|0|1";
        var BG = DG.split(DG.charAt(0));
        var EG, text;
        var BZ = Get(row, BJ);
        if (BZ == null) BZ = '|';
        else BZ += "";
        var Bp = BZ.charAt(0);
        if (!Bp) Bp = '|';
        BZ = BZ.split(Bp);
        if (BG[2] - 0) {
            if (BG[3] - 0) {
                text = Value.search(/\sTEXT\: /);
                EG = Value.slice(5, text).replace(/[\;\s]*$/, "");
                text = Value.slice(text + 7);
            } else {
                EG = Value;
                text = BZ[2];
            }
        } else {
            EG = BZ[1] == null ? "": BZ[1];
            text = Value;
        };
        Value = Bp + EG;
        if (text != null) Value += Bp + text;
        if (BZ[3]) Value += Bp + BZ[3];
        return Value;
    case "Img":
        if (!DG) DG = "|0|1";
        var BG = DG.split(DG.charAt(0));
        var src = BG[2] - 0 ? (BG[3] - 0 || BG[4] - 0 ? 5 : 0) : null;
        var ZB = BG[3] - 0 ? Value.search(new RegExp("W\\:\\d{0,3},H\\:\\d{0,3};\\sX\\:\\d{0,4},Y\\:\\d{0,4}", "")) : null;
        var Xz = BG[4] - 0 ? Value.search(/\sURL\: /) : null;
        var BZ = Get(row, BJ);
        if (!BZ) BZ = '|';
        var Bp = BZ.charAt(0);
        BZ = BZ.split(Bp);
        if (src != null) {
            if (ZB) src = Value.slice(src, ZB - 1);
            else if (Xz) src = Value.slice(src, Xz);
            else src = Value.slice(src);
            src = src.replace(/[\;\s]*$/, "");
        } else src = BZ[1] == null ? "": BZ[1];
        if (ZB) {
            if (Xz) ZB = Value.slice(ZB, Xz);
            else ZB = Value.slice(ZB);
            ZB = ZB.replace(/[\]\;\s]*$/, "").replace(/W\:/, Bp).replace(/,H\:/, Bp).replace(/;\sX\:/, Bp).replace(/,Y\:/, Bp);
        } else ZB = BZ[2] == null ? Bp + Bp + Bp + Bp: Bp + BZ[2] + Bp + BZ[3] + Bp + BZ[4] + Bp + BZ[5];
        if (Xz) Xz = Value.slice(Xz + 6);
        else Xz = BZ[6] == null ? "": BZ[6];
        Value = Bp + src + ZB + Bp + Xz;
        if (BZ[7]) Value += Bp + BZ[7];
        return Value;
    default:
        return this.StringToValue(Value, type, type == "Enum" ? this.GetEnum(row, BJ, 1) : DG, 1);
    }
};
_7m.prototype.ValueToString = function (Value, type, DG, ZC, HD) {
    if (Value == null) Value = "";
    switch (type) {
    case "Bool":
        return ! Value ? "0": "1";
    case "Int":
    case "Float":
        return Value === "" && this.EmptyNumber != null ? this.EmptyNumber: NumberToString(Value, DG, HD);
    case "Enum":
        if (Value === "" && this.EmptyEnum != null) return this.EmptyEnum;
        if (!DG || typeof(DG) != "object") return Value + "";
        Value -= 0;
        if (!Value || Value < 0) Value = 0;
        else if (Value >= DG.length) Value = DG.length - 1;
        return DG[Value];
    case "Radio":
        if (!DG) return Value + "";
        DG = DG.split(DG.charAt(0));
        if (DG.length <= 7) return Value + "";
        Value -= 0;
        if (!Value || Value < 0) Value = 0;
        Value += 7;
        if (Value >= DG.length) Value = DG.length - 1;
        return DG[Value];
    case "Date":
        if (Value === "" && this.EmptyDate != null) return this.EmptyDate;
        return ! Value ? "": DateToString(HD ? Value: Value - 0, DG, HD);
    case "Text":
        return this.Y5(Value, DG);
    case "Lines":
        return this.Y5(Value, DG);
    case "Pass":
        return ZC ? "": Value + "";
    case "Link":
        if (Value == "") return "";
        if (!ZC || ZC == 1) return Value;
        Value = Value.split(Value.charAt(0));
        return Value[2] != null ? Value[2] : (Value[1] != null ? Value[1] : "");
    case "Img":
        if (!ZC || ZC == 1) return Value;
        Value = Value.split(Value.charAt(0));
        return Value[6] != null ? Value[6] : (Value[1] != null ? Value[1] : "");
    case "Html":
        return Value + "";
    case "Abs":
    case "Gantt":
        return Value + "";
    case "List":
        return Value + "";
    };
    return "";
};
_7m.prototype.StringToValue = function (Value, type, DG) {
    if (!Value) Value = "";
    switch (type) {
    case "Bool":
        return (Value - 0) == 1;
    case "Int":
    case "Float":
        if (this.EmptyNumber != null && (Value === "" || Value === this.EmptyNumber)) return "";
        Value = StringToNumber(Value, DG);
        return type == "Int" ? Math.round(Value) : Value;
    case "Enum":
        if (this.EmptyEnum != null && (Value === "" || Value === this.EmptyEnum)) return "";
        if (typeof(DG) == "object") {
            for (var i = 0; i < DG.length; i++) if (DG[i] == Value) return i;
        }
        if (isNaN(Value - 0)) return 0;
        return Value - 0;
    case "Radio":
        if (DG) {
            DG = DG.split(DG.charAt(0));
            for (var i = DG.length - 1; i >= 0; i--) if (DG[i] == Value) return i;
        }
        if (isNaN(Value - 0)) return 0;
        return Value - 0;
    case "Date":
        if (this.EmptyDate != null && (Value === "" || Value === this.EmptyDate)) return "";
        if (Value - 0 + "" == Value && (Value > 3000 || Value == "0")) return Value - 0;
        return StringToDate(Value, DG).getTime();
    case "Link":
    case "Img":
        if (Value.search(/^[\w\/]/) >= 0) return "|" + Value;
        return Value;
    default:
        return Value;
    }
};
_7m.prototype.Y8 = function (row, BJ, EK) {
    var Enum = this.GetEnum(row, BJ);
    if (!Enum) return EK ? _7D: "";
    var BR = Get(row, BJ) - 0;
    if (BR < 0 || BR >= Enum.length) {
        var BG = Get(row, BJ + "IntFormat");
        if (BG == null) BG = this.Cols[BJ]["IntFormat"];
        if (BG == 0) {
            if (BR < 0) BR = 0;
            else BR = Enum.length - 1;
        } else {
            BR = NumberToString(BR, BG);
            if (!BR || BR == " ") return EK ? _7D: "";
            return ! EK || this.NoFormatEscape ? BR: this.Escape(BR);
        }
    }
    BR = Enum[BR];
    return BR ? BR: EK ? _7D: "";
};
_7m.prototype.GetString = function (row, BJ, ZC) {
    var type = this.GetType(row, BJ);
    if (type == "Enum") return this.Y8(row, BJ, 0);
    return this.ValueToString(Get(row, BJ), type, this.GetFormat(row, BJ), ZC, this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "");
};
_7m.prototype.GetValue = function (row, BJ) {
    var BR = Get(row, BJ),
    type = this.GetType(row, BJ);
    switch (type) {
    case "Int":
    case "Float":
        if (BR === "" && this.EmptyNumber != null) return "";
        if (BR && typeof(BR) == "string" && this.GR(row, BJ)) return BR;
        return BR ? BR - 0 : 0;
    case "Bool":
        return BR ? BR - 0 : 0;
    case "Date":
        if (BR === "" && this.EmptyDate != null) return "";
        if (typeof(BR) == "string" && this.GR(row, BJ)) return BR;
        return BR ? BR - 0 : 0;
    case "Enum":
        if (BR === "" && this.EmptyEnum != null) return "";
        BR = BR ? BR - 0 : 0;
        var a = this.GetEnum(row, BJ);
        if (!a) return BR;
        if (isNaN(BR) || !BR) BR = 0;
        if (BR < 0 || BR >= a.length) {
            var BG = Get(row, BJ + "IntFormat");
            if (BG == null) BG = this.Cols[BJ]["IntFormat"];
            if (!BG) {
                if (BR < 0) BR = 0;
                else BR = a.length - 1;
            }
        }
        return BR;
    case "Radio":
        return BR;
    default:
        return BR == null ? "": BR;
    }
};
_7m.prototype.SetString = function (row, BJ, Value, refresh) {
    var C = this.Cols[BJ],
    type = this.GetType(row, BJ),
    BR;
    if (type == "Enum") {
        if (this.EnumKeys) {
            BR = null;
            var GS = this.GT(row, BJ);
            if (GS) for (var k = 0; k < GS.length; k++) if (GS[k] == Value) {
                BR = k - 1;
                break;
            }
        }
        if (BR == null) BR = this.StringToValue(Value, type, this.GetEnum(row, BJ));
    } else BR = this.StringToValue(Value, type, this.GetFormat(row, BJ, 1));
    this.SetValue(row, BJ, BR, refresh);
    return this.ValueToString(BR, type, type == "Enum" ? this.GetEnum(row, BJ) : this.GetFormat(row, BJ), null, this.GR(row, BJ) ? this.Lang.Format.ValueSeparator + this.Lang.Format.RangeSeparator: "");
};
_7m.prototype.SetValue = function (row, BJ, Value, refresh, Ga, UD) {
    var BR = this.GetValue(row, BJ);
    if (Value == BR && (Value || Value === BR)) {
        if (!Value && !isNaN(Value) && isNaN(row[BJ]) && row[BJ] != null);
        else return false;
    }
    if (!row.Dx) {
        if (this.StoreOriginalValues && !row[BJ + "Changed"]) row[BJ + "Orig"] = BR;
        if (row.Kind != "Filter" && !row.NoUpload) this.Gq({
            Type: "Change",
            Row: row,
            Col: BJ,
            Gx: BR,
            G3: Value,
            Gy: row.Changed,
            Gz: row[BJ + "Changed"]
        });
        row.Changed = 1;
        row[BJ + "Changed"] = 1;
    }
    row[BJ] = Value;
    if (!Ga) {
        if (row.MasterRow) {
            this.E2.SetValue(row.MasterRow, BJ, Value, 1, 1, UD);
        }
        if (row.DetailRow && !row.DetailRow.Page) {
            row.DetailGrid.SetValue(row.DetailRow, BJ, Value, 1, 1, UD);
        }
    }
    var CC = Get(row, BJ + "CopyTo");
    if (CC) {
        CC = CC.split(',');
        for (var i = 0; i < CC.length; i += 2) {
            var J = null;
            if (CC[i] == "Parent") J = row.parentNode;
            else if (CC[i] == "Next") J = row.nextSibling;
            else if (CC[i] == "Prev") J = row.previousSibling;
            else {
                var Bh = CC[i].split('_'),
                Bg = null;
                if (Bh[0] == "Child") Bg = row;
                else if (Bh[0] == "Sibling") Bg = row.parentNode;
                else if (Bh[0] == "Children") {
                    for (var J = row.firstChild; J; J = J.nextSibling) if (!Bh[1] || J.Def.Name == Bh[1]) {
                        this.SetValue(J, CC[i + 1], Value, 1, 0, UD);
                        if (this.Ii) this.Ii(J, CC[i + 1]);
                    }
                    continue;
                }
                if (Bg) {
                    if (Bh[1] - 0 + "" == Bh[1]) J = _7c(Bg, Bh[1]);
                    else for (var J = Bg.firstChild; J; J = J.nextSibling) if (J.Def.Name == Bh[1]) break;
                } else J = this.GetRowById(CC[i]);
            };
            if (J) {
                this.SetValue(J, CC[i + 1], Value, 1, 0, UD);
                if (this.Ii) this.Ii(J, CC[i + 1]);
            }
        }
    }
    if (row.Def.Group && BJ == this.MainCol) {
        var T = this;
        for (var PY = -1, EU = row; EU.Def && EU.Def.Group; EU = EU.parentNode, PY++);
        PY = this.GroupCols[PY];
        function Qr(row) {
            for (var J = row.firstChild; J; J = J.nextSibling) {
                if (J.Def && J.Def.Group) Qr(J);
                else T.SetValue(J, PY, Value, 1, 0, UD);
            }
        };
        Qr(row);
        if (this.AutoUpdate) {
            row.Changed = null;
            row[BJ + "Changed"] = null;
        }
    }
    if (this.SaveAttrs) this.C4(row, BJ);
    var I7 = row.firstChild;
    if (I7 && I7.Dx) {
        for (; I7; I7 = I7.nextSibling) this.SetValue(I7, BJ, Value, 1, 1, UD);
    }
    if (!UD) this.DoAction(row, BJ);
    if (this.StoreOriginalValues) {
        var ZD = row[BJ + "Orig"];
        if (ZD == Value && (Value || ZD === Value)) {
            row[BJ + "Changed"] = null;
            var B1 = 0;
            for (var BX in this.Cols) if (row[BX + "Changed"]) {
                B1 = 1;
                break;
            }
            if (!B1) row.Changed = null;
        }
    }
    if (this.At && this.At[BJ]) this.UJ(row);
    if (row.Kind != "Filter" && !UD) this.Recalculate(row, BJ, true);
    if (!Ga && !UD) {
        if (row.MasterRow) this.E2.UploadChanges(row.MasterRow);
        else this.UploadChanges(row);
    }
    if (refresh) {
        this.RefreshCell(row, BJ);
        this.ColorRow(row);
    }
    return true;
};
_7m.prototype.SetWidth = function (BJ, Ik, all, ZE, ZF) {
    var C = this.Cols[BJ],
    BZ = C.K;
    var Dp = C.Width + Ik - this.AH;
    if (!C.Visible) Dp = 0;
    var min = C.MinWidth;
    if (min < C.WidthPad) min = C.WidthPad;
    min -= this.AH;
    if (min < 0) min = 0;
    if (Dp < min) {
        if (C.RelWidth) Dp = 0;
        else {
            if (C.Cj) {
                C.RelWidth = C.Cj;
                if (!ZF) this.SetScrollBars();
                return;
            }
            if (C.MinWidth >= 20) C.Width = C.MinWidth;
            else {
                C.Ci = true;
                this.CalcWidth(BJ);
            };
            Ik = C.Width + Ik - Dp - this.AH;
            Dp = C.Width - this.AH;
        }
    } else {
        C.Width = Dp + this.AH;
        C.Ci = false;
    };
    var max = this.RowCount < this.SynchroCount || all;
    var Ew = Grids.OnRenderRow != null;
    function ZG(row, children) {
        if (row.Kind == "User") {
            var IU = _7s(T.GetRow(row, C.K), "td")[0];
            if (IU) {
                var CY = parseInt(IU.firstChild.style.width) + Ik;
                if (CY < 0) CY = 0;
                IU.firstChild.style.width = CY + "px";
            }
        } else if (max) {
            var BY = BJ,
            CY = Dp;
            if (row.Spanned) {
                var Dr = Get(row, BJ + "Span");
                if (Dr == 0) BY = T.GetPrevCol(BJ, row);
                if (Dr == 0 || Dr > 1) {
                    CY += T.DS(row, BY) - T.Cols[BJ].Width;
                }
            }
            var IU = T.GetCell(row, BY),
            I5,
            CZ;
            if (IU) {
                if (T.VarHeight) {
                    if (all | 1) {
                        I5 = T.GetCell(row, BY, 1);
                        if (I5) {
                            CZ = I5.firstChild.offsetHeight;
                            if (CZ < 5) CZ = IU.clientHeight;
                        }
                    } else CZ = IU.clientHeight;
                }
                if (IU.firstChild.style.width == "") {
                    var SI = T.Ia(row, BJ);
                    if (CY - SI <= 0) {
                        if (!C.RelWidth) {
                            T.SetWidth(BJ, Ik - SI, all, ZE, ZF);
                            return true;
                        }
                        CY = SI;
                    }
                    if (SI) {
                        IU = _7s(IU.firstChild, "div")[0].parentNode;
                        IU.firstChild.style.width = (CY - SI) + "px";
                    } else {
                        IU.innerHTML = T.Il(row, BY, null, null, IU.clientHeight);
                        if (Ew) Grids.OnRenderRow(T, row, BY);
                    }
                } else IU.firstChild.style.width = CY + "px";
                if (_7AJ != 1) {
                    setTimeout(function () {
                        _7Ao(IU);
                    },
                    10);
                }
                if (T.VarHeight) {
                    var Cw = (all | 1) && I5 ? I5.offsetHeight: IU.clientHeight;
                    if (Cw < 5) CZ = IU.clientHeight;
                    if (Cw < CZ) {
                        var D3 = T.GetSections();
                        for (var i = D3[0]; i < D3[1]; i++) row["r" + i].firstChild.style.height = Cw + "px";
                        if (T.MainCol) T.Uc(row, Cw);
                        T.GetCell(row, BY, 3).style.height = "";
                    }
                    if (Cw != CZ || CZ == 0) T.UpdateRowHeight(row);
                }
            }
        }
        if (children) for (var J = row.firstChild; J; J = J.nextSibling) ZG(J, 1);
    };
    if (C.Visible) {
        if (this.EditMode && BJ == this.FCol) if (this.EndEdit(this.FRow, BJ, 1) == -1) return;
        var row, T = this,
        CE = this.GetFixedRows();
        for (var i = 0; i < CE.length; i++) ZG(CE[i]);
        if (all) for (var H = this.XB.firstChild; H; H = H.nextSibling) {
            for (row = H.firstChild; row; row = row.nextSibling) if (ZG(row, 1)) return;
        } else for (row = this.GetFirstVisible(null, 1); row; row = this.GetNextVisible(row, 1)) if (ZG(row)) return;
    }
    if (this.XHeader.Rows > 1 && C.Visible && !ZE) {
        var KL = this.XHeader,
        C0 = KL.Main;
        if (!C0) C0 = KL.Rows - 1;
        for (var i = 0; i < KL.Rows; i++) {
            var IU = this.QT(i, BJ);
            if (IU) {
                Wo = parseInt(IU.firstChild.style.width) + Ik;
                if (Wo < 0) Wo = 0;
                IU.firstChild.style.width = Wo + "px";
            }
        }
    }
    if (C.Visible && this.XHeader.r1) this.GetCell(this.XHeader, BJ).innerHTML = this.Il(this.XHeader, BJ);
    this.DZ(BZ);
    if (this.XHeader.Wrap) {
        var D3 = this.GetSections();
        for (var i = D3[0]; i < D3[1]; i++) this.XHeader["r" + i].firstChild.style.height = "1px";
    }
    this.UpdateRowHeight(this.XHeader);
    if (!ZF) this.SetScrollBars();
    if (all == 1 && this.ColNames[C.K].length - 1 == C.Pos) {
        this.SetWidth(BJ, 1, 0);
        this.SetWidth(BJ, -1, 0);
    }
    if (all && Grids.OnColResize) Grids.OnColResize(this, BJ);
};
_7m.prototype.DZ = function (DR) {
    this.Dg();
    var CY = 0,
    J = this.GetRow(this.XHeader, DR);
    if (J) {
        CY = J.firstChild.offsetWidth;
    } else {
        for (var i = 0; i < this.ColNames[DR].length; i++) {
            var BJ = this.ColNames[DR][i];
            if (this.Cols[BJ].Visible) CY += this.Cols[BJ].Width;
        }
    };
    CY += this.AF - this.AE;
    if (DR != 1 || CY < this.b[DR].offsetWidth) {
        this.c[DR].style.width = CY + "px";
        this.b[DR].style.width = CY + "px";
        this.d[DR].style.width = CY + "px";
    }
    try {
        CY = this.GetRow(this.XHeader, 0);
        if (CY) {
            CY = CY.firstChild.offsetWidth;
            if (_7S) CY += this.J1;
            this.c[0].style.width = CY + "px";
            this.b[0].style.width = CY + "px";
            this.d[0].style.width = CY + "px";
        }
        CY = this.GetRow(this.XHeader, 2);
        if (CY) {
            CY = CY.firstChild.offsetWidth;
            if (_7S) CY += this.J2;
            this.c[2].style.width = CY + "px";
            this.b[2].style.width = CY + "px";
            this.d[2].style.width = CY + "px";
        }
    } catch(B) {};
};
_7m.prototype.CalcWidth = function (BJ) {
    if (BJ == "Panel") return 0;
    var C = this.Cols[BJ];
    C.Width = null;
    var ZH = document.createElement(_7U ? "span": "div");
    if (!_7U) ZH.style.position = "absolute";
    ZH.style.visibility = "hidden";
    this.MainTag.appendChild(ZH);
    ZH.className = (Grids.OnGetClass ? Grids.OnGetClass(this, this.XHeader, BJ, this.Img.Style + "Header") : this.Img.Style + "Header") + " " + this.Img.Style + "HeadText";
    ZH.innerHTML = this.XHeader[BJ];
    var Dp = ZH.offsetWidth + (this.Sorting && C.CanSort ? this.Img.Sort: 0) + 2;
    var Hb = "";
    if (Grids.OnGetClass) Hb = Grids.OnGetClass(this, 0, BJ, "");
    ZH.className = Hb == "" ? this.Img.Style + "Text": Hb;
    function Count(J) {
        if (J.Kind != "Data") return;
        if (J.Spanned) {
            var Dr = Get(J, BJ + "Span");
            if (Dr > 1 || Dr == 0) return;
        }
        var DD = T.GetString(J, BJ, 1);
        var BN = DD.length + (Em ? J.Level * 3 : 0);
        if (BN > ZI) {
            max = J;
            ZI = BN;
        }
    };
    switch (C.Type) {
    case "Bool":
        if (Dp < 23) Dp = 23;
        break;
    case "Enum":
        var max = 0;
        if (!C.Enum) break;
        var GS = this.Ua(C.Enum);
        for (var i = 1; i < GS.length; i++) {
            if (GS[max].toString().length < GS[i].toString().length) max = i;
        }
        ZH.innerHTML = GS[max].toString();
        var CY = ZH.offsetWidth + C.WidthPad;
        if (CY > Dp) Dp = CY;
        break;
    case "Html":
    case "Abs":
    case "Gantt":
    case "List":
    case "Img":
    case "Link":
    case "Radio":
        break;
    default:
        var Em = BJ == this.MainCol;
        var max = null,
        ZI = 0;
        var T = this;
        var Bh = 100;
        for (var J = this.GetFirst(); J && Bh; J = this.GetNext(J), Bh--) Count(J);
        var R = this.GetFixedRows();
        for (var T8 = 0; T8 < R.length; T8++) Count(R[T8]);
        if (max) {
            var Dh = max[BJ];
            var B1 = C["AutoWidthChar"];
            if (B1) {
                for (var Bh = "", i = 0; i < ZI; i++) Bh += B1;
                max[BJ] = Bh;
            }
            ZH.innerHTML = this.O2(max, BJ, this.GetType(max, BJ), 0);
            max[BJ] = Dh;
            var SI = 0;
            SI = this.Ia(max, BJ);
            if (! (SI > C.WidthPad)) SI = C.WidthPad;
            if (!SI) {
                var JJ = Get(max, BJ + "Button");
                if (JJ == null) JJ = C.Button;
                if (JJ == "Date" || this.CanEdit(max, BJ) && this.GetType(max, BJ) == "Date") SI = this.Img.Row;
            }
            var Ik = C["AutoWidthPlus"];
            var CY = ZH.offsetWidth + (Em ? max.Level * this.Img.Line + this.Img.Tree: 0) + SI + (Ik ? Ik: 0);
            if (CY > Dp) Dp = CY + 2;
        }
    };
    if (Dp > this.MainTag.offsetWidth - 10) Dp = this.MainTag.offsetWidth - 10;
    if (Dp < C.MinWidth) Dp = C.MinWidth;
    C.Width = Dp;
    if (!_7U) this.MainTag.removeChild(ZH);
};
_7m.prototype.WO = function (WL) {
    if (!this.b[1] || this.TC) return;
    var C = this.Cols,
    Lm = null,
    ZJ = 0,
    Df = false;
    var Ch = this.MainTag.clientWidth;
    if (!WL) Ch -= _7Y + this.q.AF;
    Ch -= this.AF + this.J1 + this.J2 + this.AI;
    if (this.h) Ch -= this.f.offsetWidth;
    for (var BY in C) {
        if (!C[BY].RelWidth) {
            if (C[BY].Visible) Ch -= C[BY].Width + this.CellSpacing;
        } else {
            if (C[BY].Visible) {
                ZJ += C[BY].RelWidth;
                Ch -= C[BY].MinWidth;
            }
            Lm = BY;
        }
    }
    var ZK = Ch;
    for (var BY in C) {
        if (C[BY].RelWidth && C[BY].Visible) {
            var CY = Math.floor(Ch * C[BY].RelWidth / ZJ);
            if (CY < 0) CY = 1;
            if (BY == Lm && ZK > 0) CY = ZK;
            ZK -= CY;
            CY += C[BY].MinWidth;
            var Mg = CY - C[BY].Width - this.CellSpacing;
            if (Mg != 0) {
                this.SetWidth(BY, Mg, true, null, true);
                Df = true;
            }
        }
    }
    return Df;
};
_7m.prototype.B6 = function (J, Ch) {
    if (!this.b[1] || !J.r1 || this.TC) return;
    var Lm = null,
    ZJ = 0;
    if (!Ch) Ch = J.r1.clientWidth;
    for (var i = 0; i < J.Cells.length; i++) {
        var BY = J.Cells[i],
        I1 = J[BY + "RelWidth"];
        if (!I1) Ch -= this.GetCell(J, BY).offsetWidth;
        else {
            ZJ += I1;
            var Jk = J[BY + "MinWidth"];
            if (Jk) Ch -= Jk;
            Lm = BY;
        }
    }
    if (J.Panel && this.Cols.Panel.Visible) Ch -= J.r1.getElementsByTagName("td")[0].offsetWidth;
    var ZK = Ch;
    for (var i = 0; i < J.Cells.length; i++) {
        var BY = J.Cells[i],
        I1 = J[BY + "RelWidth"];
        if (I1) {
            var CY = Math.floor(Ch * I1 / ZJ);
            if (CY < 0) CY = 1;
            if (BY == Lm && ZK > 0) CY = ZK;
            ZK -= CY;
            var Jk = J[BY + "MinWidth"];
            if (Jk) CY += Jk;
            var IU = this.GetCell(J, BY);
            if (IU.offsetWidth != CY) {
                IU.firstChild.style.width = CY + "px";
                IU.firstChild.style.overflow = "hidden";
            }
        }
        if (J[BY + "Type"] == "DropCols") {
            var IU = this.GetCell(J, BY, 1);
            var BR = Get(J, BY);
            if (BR && IU) {
                BR = (BR + "").split(",");
                var ZL = Math.floor((IU.parentNode.offsetWidth - 40) / BR.length);
                if (ZL <= 0) continue;
                for (var j = 0; j < BR.length; j++) {
                    if (BR[j] && this.Cols[BR[j]] && ZL < this.Cols[BR[j]].Width) {
                        var style = IU.firstChild.firstChild.rows[0].cells[j].firstChild.style;
                        if (style) style.width = ZL + "px";
                    }
                }
            }
        }
    }
};
function _7A4() {
    this.nodeType = 1;
    this.attributes = [];
    this.childNodes = {
        length: 0
    };
};
_7A4.prototype.removeChild = function (D) {
    if (!D) return null;
    var F = D.parentNode;
    if (!F) return null;
    if (D == F.firstChild) F.firstChild = D.nextSibling;
    else D.previousSibling.nextSibling = D.nextSibling;
    if (D == F.lastChild) F.lastChild = D.previousSibling;
    else D.nextSibling.previousSibling = D.previousSibling;
    D.parentNode = null;
    D.previousSibling = null;
    D.nextSibling = null;
    this.childNodes.length--;
    return D;
};
_7A4.prototype.appendChild = function (D) {
    if (!D) return null;
    if (D.parentNode) D.parentNode.removeChild(D);
    if (this.lastChild) {
        this.lastChild.nextSibling = D;
        D.previousSibling = this.lastChild;
    } else {
        this.firstChild = D;
        D.previousSibling = null;
    };
    this.lastChild = D;
    D.nextSibling = null;
    D.parentNode = this;
    D.index = this.childNodes.length++;
    return D;
};
_7A4.prototype.insertBefore = function (D, C) {
    if (!D) return null;
    if (!C) return this.appendChild(D);
    if (D.parentNode) D.parentNode.removeChild(D);
    if (C.previousSibling) C.previousSibling.nextSibling = D;
    else this.firstChild = D;
    D.previousSibling = C.previousSibling;
    D.nextSibling = C;
    D.parentNode = this;
    D.index = this.childNodes.length++;
    return D;
};
_7A4.prototype.getAttribute = function (name) {
    return this[name];
};
_7A4.prototype.hasAttribute = function (name) {
    return this[name] != undefined;
};
_7A4.prototype.setAttribute = function (name, value) {
    var BL = this.attributes;
    if (this[name] == undefined) {
        this[name] = value;
        BL[BL.length] = {
            nodeName: name,
            nodeValue: value
        }
    } else {
        this[name] = value;
        for (var i = 0; i < BL.length; i++) if (BL[i].nodeName == name) {
            BL[i].nodeValue = value;
            break;
        }
    }
};
_7A4.prototype.getElementsByTagName = function (name) {
    var BL = [];
    for (var J = this.firstChild; J; J = J.nextSibling) {
        if (J.nodeName == name) BL[BL.length] = J;
        if (J.firstChild) {
            var H = J.getElementsByTagName(name);
            if (H.length) BL.concat(H);
        }
    }
    return BL;
};
function _7A5() {};
_7A5.prototype = new _7A4();
_7A5.prototype.createElement = function (name) {
    var E = new _7A4();
    E.tagName = name;
    E.nodeName = name;
    return E;
};
function _7A6(H, BL) {
    var Bh = "<" + H.tagName;
    for (a in H) {
        var Cs = typeof(H[a]);
        if (a != 'tagName') {
            if (Cs == "string") Bh += " " + a + "=\"" + H[a].replace(/\&/g, "&amp;").replace(/\</g, "&lt;").replace(/"/g, "&quot;").replace(/\n/g, "&#x0A;") + "\"";
            else if (Cs == "number") Bh += " " + a + "=\"" + H[a] + "\"";
            else if (Cs == "boolean") Bh += " " + a + "=\"" + (H[a] ? 1 : 0) + "\"";
        }
    }
    if (BL) {
        if (H.firstChild) {
            BL[BL.length] = Bh + ">";
            for (var J = H.firstChild; J; J = J.nextSibling) _7A6(J, BL);
            BL[BL.length] = "</" + H.tagName + ">";
        } else BL[BL.length] = Bh;
    } else if (H.firstChild) {
        Bh += ">";
        for (var J = H.firstChild; J; J = J.nextSibling) Bh += _7A6(J);
        Bh += "</" + H.tagName + ">";
        return Bh;
    } else return Bh + "/>";
};
function _7A7(Xml, ZM, Parent) {
    var F = Parent ? Parent: ZM;
    var ZN = /[^\s\/\>]/;
    var J = /\w+|\'[^\']*\'|\"[^\"]*\"|\/?\>[\s\S]*/g;
    Xml = Xml.replace(/\<\!\-\-([^\-]|\-[^\-]|\-+[^\-\>])*\-{2,}\>/g, "");
    var I7 = Xml.search(/\<\w+\>/);
    if (I7 < 0) return false;
    Xml = Xml.slice(I7 + 1);
    var BL = Xml.split('<'),
    FT = BL.length;
    function ZO(BZ) {
        if (BZ.indexOf('&#') >= 0) {
            var BL = BZ.match(/\&\#x\w*\;/g);
            if (BL) for (var i = 0; i < BL.length; i++) BZ = BZ.replace(BL[i], String.fromCharCode(parseInt(BL[i].slice(3), 16)));
            var BL = BZ.match(/\&\#\w*\;/g);
            if (BL) for (var i = 0; i < BL.length; i++) BZ = BZ.replace(BL[i], String.fromCharCode(parseInt(BL[i].slice(2), 10)));
        }
        return BZ.replace(/\&lt\;/g, "<").replace(/\&gt\;/g, ">").replace(/\&quot\;/g, "\"").replace(/\&apos\;/g, "'").replace(/\&amp\;/g, "&");
    };
    for (var i = 0; i < FT; i++) {
        var Bh = BL[i];
        if (Bh.charCodeAt(0) == 47) {
            F = F.parentNode;
            if (!F) return false;
            continue;
        }
        var H = Bh.match(J),
        FW = H.length - 1;
        D = ZM.createElement(H[0]);
        F.appendChild(D);
        for (var j = 1; j < FW; j += 2) {
            var BZ = H[j + 1].slice(1, -1);
            if (BZ.indexOf('&') >= 0) BZ = ZO(BZ);
            BZ = (BZ - 0) + "" == BZ ? BZ - 0 : BZ;
            D.setAttribute(H[j], BZ);
        }
        var Bh = H[FW],
        Cs = 0;
        if (Bh.charCodeAt(0) != 47) {
            F = D;
            if (Bh.length > 6 || Bh.search(ZN) >= 0) Cs = 1;
        } else if (Bh.length > 7 || Bh.search(ZN) >= 0) Cs = 2;
        if (Cs) {
            Cs = Bh.slice(Cs);
            if (Cs.indexOf('&') >= 0) Cs = ZO(Cs);
            var T = ZM.createElement("#text");
            T.nodeType = 3;
            T.nodeValue = Cs;
            F.appendChild(T);
        }
    }
    ZM.documentElement = ZM.firstChild;
    return true;
};
function _7v(DD) {
    var Jf = new _7A5();
    if (_7A7(DD, Jf)) return Jf;
    return null;
};