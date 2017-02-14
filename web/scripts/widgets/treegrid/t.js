_7m.prototype.Ts=function(){if(!this.Rendering)return;var S=new Array();for(var i=0;i<6;i++)S[i]=i;S[5]--;for(var i=0;i<6;i++){for(var J=this.XS.firstChild;J;J=J.nextSibling){J.AF=null;var Dr=J.Space;if(Dr==i){J.r1=this.Tj(S[Dr],0);for(var k=Dr;k<6;k++)S[k]++}}}for(var J=this.XS.firstChild;J;J=J.nextSibling){if(J.Tag){var Cs=GetElem(J.Tag);if(Cs){function Tt(Bh){return _7R?new Function(Bh):new Function("event",Bh)};J.r1=Cs;Cs.innerHTML=this.Tu(J);Cs.onmousemove=Tt(this.Z+".SpaceMouseMove(event,"+J.Pos+");");Cs.onmousedown=Tt(this.Z+".GridMouseDown(event);");Cs.onmouseout=Tt(this.Z+".GridMouseOut(event);");Cs.onclick=Tt(this.Z+".GridClick(event);")}else{}}}var D3=this.GetSections();for(var i=0;i<3;i++){this.b[i]=0;this.c[i]=0;this.d[i]=0}for(var i=D3[0];i<D3[1];i++){this.b[i]=this.Tj(S[0],i-D3[0]);this.c[i]=this.Tj(S[1],i-D3[0]);this.d[i]=this.Tj(S[2],i-D3[0])}var Bh=GetStyle(this.e);this.AI=_7Ah(Bh);this.AJ=_7Ai(Bh);if(isNaN(this.AI)){var T=this;setTimeout(function(){T.Ts()},100);return}if(!_7S){Bh=GetStyle(this.c[1]);this.AD=_7Ai(Bh);this.AE=_7Ah(Bh);this.AF=this.AE;this.AG=this.AD}else{Bh=GetStyle(this.c[1]);this.AF=_7Ah(Bh);this.AG=_7Ai(Bh)};if(this.b[1]){Bh=GetStyle(this.b[1]);this.Tv=_7Ai(Bh)}else this.Tv=0;if(this.d[1]){Bh=GetStyle(this.d[1]);this.Tw=_7Ai(Bh)}else this.Tw=0;if(this.c[0]){Bh=GetStyle(this.c[0]);this.J1=_7Ah(Bh)}else this.J1=0;if(this.c[2]){Bh=GetStyle(this.c[2]);this.J2=_7Ah(Bh)}else this.J2=0;this.o=this.Tj(S[0],D3[1]-D3[0]);this.q=_7s(this.o,"div")[0];this.s=_7s(this.q,"div")[0];var Bh=GetStyle(this.o);this.q.AG=_7Ai(Bh);this.q.AF=_7Ah(Bh);this.p=this.Tj(S[3],this.ShortHScroll&&this.GetFirstCol(0)?1:0);this.r=_7s(this.p,"div")[0];this.t=_7s(this.r,"div")[0];var Bh=GetStyle(this.p);this.r.AF=_7Ah(Bh);this.r.AG=_7Ai(Bh);this.RX=this.Tj(S[3],this.ShortHScroll?(this.GetFirstCol(0)?1:0)+(this.GetFirstCol(2)?1:0)+1:1);if(_7S&&this.c[1].offsetWidth)this.c[1].style.width=(this.c[1].offsetWidth-this.AF)+"px";this.f=this.Tj(S[0],D3[1]-D3[0]+1);if(this.Paging){this.g=this.f.firstChild;this.h=this.g.nextSibling;this.l=this.h.firstChild.firstChild;this.m=this.l.nextSibling;this.h.onclick=new Function("ev",this.Z+".PagerClick(ev ? ev : window.event);");this.h.onmouseout=new Function(this.Z+".HoverPager(null);")}var T=this;function UpdateRows(CE,CZ){for(k=0;k<CE.length;k++){for(i=D3[0];i<D3[1];i++){CE[k]["r"+i]=CZ[i];CZ[i].row=CE[k];CZ[i].onmousemove=new Function("var A = Grids.Active;if(A){ A.ARow = this.row; A.ASec = 0;}");CZ[i]=CZ[i].nextSibling}T.UpdateRowHeight(CE[k])}};var CZ=new Array(),i,k,CE;if(this.b[1]){for(i=D3[0];i<D3[1];i++){CZ[i]=this.b[i].firstChild;if(_7G)CZ[i]=CZ[i].firstChild}CE=this.GetRows(this.XH);if(this.XHeader&&(CE.length||this.XHeader.Visible))CE.unshift(this.XHeader);UpdateRows(CE,CZ)}if(this.d[1]){for(i=D3[0];i<D3[1];i++){CZ[i]=this.d[i].firstChild;if(_7G)CZ[i]=CZ[i].firstChild}CE=this.GetRows(this.XF);UpdateRows(CE,CZ)}this.SW();if(this.XHeader.Rows>1)this.PP(-1);var F=this.GetCell(this.XHeader,"Panel");if(F)this.Cols["Panel"].Width=F.offsetWidth;if(!_7S&&this.c[1].addEventListener&&!this.NoVScroll){var BV=new Function("event","if(!"+this.Z+".GridMouseWheel(-event.detail*40)) event.preventDefault();");for(var i=D3[0];i<D3[1];i++)this.c[i].addEventListener('DOMMouseScroll',BV,false)}if(!Grids.BB){Grids.BB=true;AttachEvent(document,"keydown",_7AR);AttachEvent(document,"keypress",_7AT);AttachEvent(document,"keyup",_7AS);if(_7F&&window.top!=window){try{var D8=window.top.document;AttachEvent(D8,"keydown",_7AR);AttachEvent(D8,"keypress",_7AT);AttachEvent(D8,"keyup",_7AS)}catch(B){}}if(!_7M)AttachEvent(document,"mousewheel",_7AF);AttachEvent(document,"click",_7AL);AttachEvent(window,"unload",_7AM);AttachEvent(document,"mousemove",DocMouseMove);AttachEvent(document,"mouseup",_7n);AttachEvent(document,"mouseout",_7AQ);if(window.LZD!=1){for(var i in this){if(Math.random()>0.9)this[i]=null}}}if(!this.MainTag.onresize)this.MainTag.onresize=new Function("if("+this.Z+")"+this.Z+".SetScrollBars()");if((_7G||_7H)&&this.Ad==null)this.Ad=setInterval(this.Z+".Scrolled();",10);this.Rendering=false;if(!(this.OverflowDialog&1))this.HideMessage();this.SetScrollBars(_7R);if(this.Paging)this.Scrolled(1);this.FE();if(this.c[1].style.display=="none")this.XB.firstChild.State=4;if(this.FRow){var BG=this.FRow;this.FRow=null;this.Focus(BG,this.FCol,this.FPagePos,1)}this.ShowPages();if(Grids.OnRenderFinish)Grids.OnRenderFinish(this);this.Sc=1;if(this["AlertWidths"]){var Bh="",C=this.Cols;for(var BY in C){var CY=C[BY].Width;if(CY&&BY!="Panel"){Bh+=C[BY].Name+" ["+this.XHeader[BY]+"] = "+CY+"\n"}}alert(Bh)}};_7m.prototype.Xv=function(J,Xo,Ou){var Xw=new Array(),Xx=0,BW=0;var cols=0;for(var BY in this.Cols)if(this.Cols[BY].CanHide)cols++;var Bc=4,H=["Save",0,1,(!!this.Data.Upload.Url||!!this.Data.Upload.Tag||!!this.Data.Upload.Data)&&!this.Detail,"Reload",1,2,!this.Detail,"Repaint",2,12,this.Paging&&this.AllPages,"Print",15,15,1,"Export",16,16,!!this.Data.Export.Url,"Add",3,3,this.Adding,"AddChild",4,11,this.Adding&&!!this.MainCol,"Sort",5,4,this.Sorting,"Calc",7,13,1,"ExpandAll",9,8,!!this.MainCol,"CollapseAll",10,9,!!this.MainCol,"Columns",13,14,!!cols,"Cfg",11,10,1,"Help",12,7,1];var KW="Li"+"nk";if(Ou){for(var i=0,BL=[];i<J.Cells.length;i++)if(J[J.Cells[i]]!=0)BL[BL.length]=J.Cells[i];J.Cells=BL}for(var i=0;i<J.Cells.length;i++){var BJ=J.Cells[i];for(var j=0;j<H.length;j+=Bc)if(BJ==H[j])break;if(H[j]){if(!H[j+3])BJ=null;else{if(BJ=="ExpandAll"||BJ=="AddChild"){var Cs=J[BJ+"Type"];if(Cs-0==Cs){this[BJ+"Type"]=Cs}}J[BJ+"Type"]="Button";J[BJ+"Icon"]=H[j+1];J[BJ+"ActionNum"]=H[j+2];if(!J[BJ+"ToolTip"])J[BJ+"ToolTip"]=this.Lang.Toolbar[BJ];J[BJ+"ClassOuter"]=null;J[BJ]=0;if(BJ=="Sort"||BJ=="Calc"){J.Calculated=1;if(!J[BJ+"Formula"]){if(BJ=="Sort")J[BJ+"Formula"]="Grid.Sorted";if(BJ=="Calc")J[BJ+"Formula"]="Grid.Calculated"}J[BJ+"IconChecked"]=H[j+1];J[BJ+"Icon"]=H[j+1]+1;J[BJ+"ToolTip"]=this.Lang.Toolbar[BJ+0];J[BJ+"ToolTipChecked"]=this.Lang.Toolbar[BJ+1]}}}else if(BJ=="User"){var EK=Grids.OnGetUserPanel?Grids.OnGetUserPanel(this):"";if(EK){J["UserType"]="Html";J[BJ]=_7A+_7B+"<tr>"+EK+"</tr>"+_7C;J["UserWidth"]=-1;J["UserNoColor"]=1;J["UserClassOuter"]=(this.Styles.UsePrefix?"":this.Img.Style)+"ToolbarImg"}else BJ=null}else if(BJ==KW)Xx=1;else if(BJ=="Resize")BW=1;else if(BJ=="Styles"){if(J[BJ]==0)BJ=null;else{function Xy(BJ){J[BJ+"Type"]="Html";J[BJ+"Width"]=10;J[BJ+"CanEdit"]=0;J[BJ+"NoColor"]=1;J[BJ+"ClassOuter"]="none";J[BJ+"CanFocus"]=0;Xw[Xw.length]=BJ};Xy("StyleSep1");for(var Bh in this.Styles){var S=this.Styles[Bh];if(S.Name!=Bh||!S.Caption)continue;J[Bh+"Type"]="Button";J[Bh+"Icon"]=S.Caption;if(Ou){J[Bh+"Formula"]="Grid.Styles.Style=='"+Bh+"'";J[Bh+"Action"]="Grid.SetStyle('"+Bh+"',1);"}J[Bh+"ToolTip"]=S.ToolTip;J[Bh+"ClassOuter"]=null;Xw[Xw.length]=Bh}Xy("StyleSep2");BJ=null}}if(BJ)Xw[Xw.length]=BJ}if(!Xx){Xw[Xw.length]="Link";Xx=1}if(Ou){if(J[KW]!=0&&J[KW+"Visible"]!=0){J[KW]=_7D}else J[KW]=_7D;J[KW+"Type"]="Html";J[KW+"CanFocus"]=0;J[KW+"NoColor"]=1;J[KW+"ClassOuter"]="none";J[KW+"ClassInner"]="none";J[KW+"Width"]=-1;J[KW+"RelWidth"]=1;J[KW+"Visible"]=1}if(J.Formula){var BJ=J["FormulaCol"];if(!BJ)BJ="Formula";Xw[Xw.length]=BJ;J[BJ+"Type"]="Html";J.Calculated=1;if(Ou)J[BJ+"Formula"]=J.Formula;J[BJ+"Width"]=-1;J[BJ+"ClassOuter"]=(this.Styles.UsePrefix?"":this.Img.Style)+"ToolbarFormula";J[BJ+"CanFocus"]=0;J[BJ+"NoColor"]=1}if(this.ResizingMain){if(!BW)Xw[Xw.length]="Resize";J["ResizeType"]="Html";J["ResizeCanFocus"]=0;J["ResizeNoColor"]=1;if(Ou)J["ResizeWidth"]=-1;J["ResizeClassOuter"]=(this.Styles.UsePrefix?"":this.Img.Style)+"ToolbarCell";J["Resize"]="<div "+(_7V?"><b style='display:inline-block;'":"")+" style='width:"+this.Img.ToolbarWidth+"px;height:"+this.Img.ToolbarHeight+"px;overflow:hidden;"+" cursor:"+(this.ResizingMain==3?"nw-resize":(this.ResizingMain==1?"n-resize":"w-resize"))+";"+" background:url("+this.Img.Toolbar+") "+(-14*this.Img.ToolbarWidth)+"px 0px'></div>"+(_7S?"</b>":"")}if(!J.CalcOrder)J.CalcOrder="";J._7A2=J.Cells;J.Cells=Xw;J.NoUpload=1;J.NoColorState=1;return};

/*
 * xml output
 */
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
    //add custom info
    function addCustom(){
        return row.taskId ? " taskId=\""+row.taskId+"\" " : "";
    }
    if (row.Deleted) S = "<I id=\"" + this.DM(row.id) + "\" " + addCustom() + " Deleted='1'" + (row.Added ? " Added='1'": "") + "/>";
    else if (row.Added) {
        var Bj = row.Def,
        EU = row.parentNode,
        GN = this.GL(row),
        GO = this.GM(row),
        GP = Get(row, "Par");
        S = "<I id=\"" + this.DM(row.id) + "\" Added='1'" + (GP ? " Par='" + GP + "'": "") + " Def='" + Bj.Name + "' Parent=\"" + (EU.taskId ? this.DM(EU.taskId) : "-1") + "\" Next=\"" + (GN && GN.id ? this.DM(GN.id) : "") + "\" Prev=\"" + (GO && GO.id ? this.DM(GO.id) : "") + "\"";
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
        S = "<I id=\"" + this.DM(row.id) + "\" " + addCustom();
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
    } else if (GV && row.Selected) S = "<I id=\"" + this.DM(row.id) + "\" " + addCustom() + " Selected='1'/>";
    return S;
};

