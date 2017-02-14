<%--
  Created by IntelliJ IDEA.
  User: huangpeng
  Date: 13-6-28
  Time: 下午2:42
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
         contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">
    var jq=jQuery.noConflict();

    jq(function(){
        jq("#sheet").find("tr.jiechu,tr.guihuan").each(function(index){
            jq(this).hide();
            jq(this).find(":input").each(function(index){
                jq(this).attr("disabled",true);
            });
        });
    });


    function openImport(handler){
        var el = Ext.get('listQueryObject');
        if(el.isVisible()){
            el.slideOut('t',{useDisplay:true});
            handler.innerHTML = "打开查询界面";
        }
        else{
            el.slideIn();
            handler.innerHTML = "关闭查询界面";
        }
    }
    function deleteRecord(id){

        var a=confirm("是否确认删除？");
        if(!a){
            return;
        }
        Ext.Ajax.request({
            method:"post",
            url: "${app}/partner/key/keyManagement.do?method=deleteKeyBorrowRecordStatus",
            params:{
                id:id
            },
            success: function(response, options){
                if(response.responseText=='true'){
                    //刷新父页面
                    alert("归还记录删除成功！");
                    window.location.reload();
                }

            }
        })
    }
    function updateKeyStatus(id,minDate){

        var _sHeight = 380;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";

        var url ="${app}/partner/key/keyManagement.do?method=gotoUpdateKeyStatusPage&minDate="+minDate;
//        var obj = new Object();
//        obj.exception=exception;
        var pro =  window.showModalDialog(url,window,sFeatures);
        if(pro==null){
            return;
        }


        Ext.Ajax.request({
            method:"post",
            url: "${app}/partner/key/keyManagement.do?method=updateKeyBorrowRecordStatus",
            params:{
                id:id,
                remand:pro[0],
                remandTime:pro[1]
            },
            success: function(response, options){
                if(response.responseText=='true'){
                    //刷新父页面
                    alert("归还记录添加成功！");
                    window.location.reload();
                }

            }
        })

    }
    function selectRes(){
        var type = jq("#keyStatus").val();
        //type：字典值对应的value，参考配置
        switch(type) {
            case "123060101":
                jq("#sheet tr.jiechu").each(function(tr){
                    jq(this).show();
                    jq(this).find(":input").each(function(index){
                        jq(this).attr("disabled",false);
                    });
                });
                jq("#sheet").find("tr.guihuan").each(function(index){
                    jq(this).hide();
                    jq(this).find(":input").each(function(index){
                        jq(this).attr("disabled",true);
                    });
                });
                break;
            case "123060102":
                jq("#sheet tr.guihuan").each(function(tr){
                    jq(this).show();
                    jq(this).find(":input").each(function(index){
                        jq(this).attr("disabled",false);
                    });
                });
                jq("#sheet").find("tr.jiechu").each(function(index){
                    jq(this).hide();
                    jq(this).find(":input").each(function(index){
                        jq(this).attr("disabled",true);
                    });
                });
                break;

            default:
                jq("#sheet").find("tr.jiechu,tr.guihuan").each(function(index){
                    jq(this).hide();
                    jq(this).find(":input").each(function(index){
                        jq(this).attr("disabled",true);
                    });
                });
        }

    }
</script>

<div style="border:1px solid #98c0f4;padding:5px;"
     class="x-layout-panel-hd"><img
        src="${app}/images/icons/search.gif"
        align="absmiddle"
        style="cursor:pointer" />
    <span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject"
     style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
    <form action="keyManagement.do?method=findKeyBorrowRecordList" method="post">
        <table id="sheet" class="formTable">
            <tr>
                <td class="label">基站名称</td>
                <td class="content"><input class="text" type="text" name="btsNameStringLike" id="btsName" value=""/></td>
                <td class="label">借用人</td>
                <td>
                    <input name="borrowerStringLike" id="borrower" type="text"/>
                </td>
            </tr>

            <tr>
                <td class="label">
                    批准主管
                </td>
                <td>
                    <input name="approverStringLike" id="approver" type="text"/>
                </td>
                <td class="label">
                    归还人
                </td>
                <td>
                    <input name="remandStringLike" id="remand" type="text"/>
                </td>
            </tr>
            <tr>
                <td class="label">
                    状态
                </td>
                <td class="content" colspan="3">
                    <eoms:comboBox id="keyStatus" name="keyStatusStringEqual" onchange="selectRes()" initDicId="1230601" styleClass="input select"  alt="allowBlank:false" />
                </td>
            </tr>

            <tr class="jiechu">
                <td class="label">
                    借出时间
                </td>
                <td class="content" colspan="3">
                    <input class="Wdate text" type="text" value=""  name="borrowTimeStart" id="borrowTimeStart"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'borrowTimeEnd\')||\'2050-10-01\'}',readOnly:true})" />
                    <span>至</span>
                    <input class="Wdate text" type="text" value=""  name="borrowTimeEnd" id="borrowTimeEnd"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'borrowTimeStart\')||\'2050-10-01\'}',readOnly:true})" />
                </td>
            </tr>
            <tr class="guihuan">
                <td class="label">
                    归还时间
                </td>
                <td class="content" colspan="3">
                    <input class="Wdate text" type="text" value=""  name="remandTimeStart" id="remandTimeStart"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'remandTimeEnd\')||\'2050-10-01\'}',readOnly:true})" />
                    <span>至</span>
                    <input class="Wdate text" type="text" value=""  name="remandTimeEnd" id="remandTimeEnd"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'remandTimeStart\')||\'2050-10-01\'}',readOnly:true})" />
                </td>
            </tr>

        </table>

        <html:submit styleClass="btn" property="method.findKeyBorrowRecordList"
                     styleId="method.findKeyBorrowRecordList" value="查询"></html:submit>
    </form>
</div>
</hr>
<div id="loadIndicator" class="loading-indicator" style="display:none">载入详细数据中，请稍候</div>
</hr>


<!-- Information hints area end-->
<logic:present name="list" scope="request">
    <display:table name="list" cellspacing="0" cellpadding="0"
                   id="list" class="table list" export="false" sort="list"
                   pagesize="${pagesize}" requestURI="keyManagement.do?method=findKeyBorrowRecordList"
                   partialList="true" size="${size}">
        <display:column title="基站名称" property="btsName"  />
        <display:column title="门禁卡号" property="accessCardNum" />

        <display:column title="事由" property="subject" />
        <display:column title="借用时间">
            <fmt:formatDate value="${list.borrowTime}" type="date" dateStyle="medium"/>
        </display:column>
        <display:column title="借用人" property="borrower" />
        <display:column title="借用人电话" property="borrowerPhone" />
        <display:column title="批准主管" property="approver" />
        <display:column title="主管电话" property="approverPhone" />


        <display:column title="归还人" property="remand" />
        <display:column title="归还日期" >
            <fmt:formatDate value="${list.remandTime}" type="date" dateStyle="medium"/>
        </display:column>
        <display:column title="状态" >
            <%--<a id="${list.id }"  href="#" onclick="updateKeyStatus('${list.id}');"--%>
                     <%--src="${app }/images/icons/table.gif">--%>
                <%--<eoms:id2nameDB id="${list.keyStatus}" beanId="ItawSystemDictTypeDao" />--%>
             <%--</a>--%>

            <c:choose>
                <c:when test="${list.keyStatus eq 123060101}">
                    <a id="${list.id }"  href="#" onclick="updateKeyStatus('${list.id}','<fmt:formatDate value="${list.borrowTime}" type="date" dateStyle="medium"/>');"
                       src="${app }/images/icons/table.gif">
                        <font color='#ff4500'><eoms:id2nameDB id="${list.keyStatus}" beanId="ItawSystemDictTypeDao" /></font>
                    </a>
                </c:when>
                <c:otherwise>
                    <font color='#00ff7f'><eoms:id2nameDB id="${list.keyStatus}" beanId="ItawSystemDictTypeDao" /></font>
                </c:otherwise>
            </c:choose>
        </display:column>
        <display:column title="操作" >

            <a id="${list.id }"  href="#" onclick="deleteRecord('${list.id}');"
               src="${app }/images/icons/table.gif">
                删除
            </a>

        </display:column>
        <display:column title="备注" property="remark" />

    </display:table>
</logic:present>
</br>
</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>
