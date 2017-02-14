package com.boco.eoms.autosheet.util;

import java.util.*;
import com.boco.eoms.db.util.*;
import com.boco.eoms.common.util.*;

/*****************************************************************
 ��sheetname�����е��ֶ�
 �ӱ���taw_sheetname��ȡ��������hashtable,
 ��ͨ��getSheetKeyID����פ�����ڴ��������ȡ���ݵķ�����
 *****************************************************************/

public class SheetName {
  /*************************************************
   ����ArrayList����,���ڴ洢taw_sheetname���и����ֶ�
   */
  private ArrayList sheet_ids = null, module_ids = null, sh_cname = null, style = null,
      isattach = null, isattachment = null, columnWidth = null, columnHeight = null,
      width = null, height = null, para1 = null;

  private RecordSet rt = null;
 // private ConnStatement cst = null;
  private ConnStatement cstmt=new ConnStatement();
  private StaticObject staticobj = null;

  private int current_index = -1;
  private int array_size = 0;
  /*************************************************
   ���췽��
   */
  private SheetName() {
    try {
      staticobj = StaticObject.getInstance();
      current_index = -1;
      getSheetName();
      array_size = sheet_ids.size();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /***���췽��***************************************
   *@params s test commonclass
   * ******/
  public SheetName(String s) {
    try {
      staticobj = StaticObject.getInstance();
      current_index = -1;
      getSheetName();
      array_size = sheet_ids.size();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static SheetName instance = new SheetName();
  /*************************************************
   ��̬����,����SheetName���Ψһʵ��,
   @return SheetName
   */
  public static SheetName getInstance() {
    return instance;
  }

  public static void removeInstance() {
    instance = new SheetName();
  }

  /*************************************************
   ����SheetName����,����ǰ��������
   @return boolean
   */
  public boolean reset() {
    this.current_index = -1;
    return true;
  }

  /*********************************************
   ��ʼ��SheetName�ĸ�������
   @throws Exception
   */
  private void getSheetName() throws Exception {
    if (staticobj.getObject("SheetName") == null)
      setSheetName();
    sheet_ids = (ArrayList) (staticobj.getRecordFromObj("SheetName","sheet_ids"));
    module_ids = (ArrayList) (staticobj.getRecordFromObj("SheetName","module_ids"));
    sh_cname = (ArrayList) (staticobj.getRecordFromObj("SheetName", "sh_cname"));
    style = (ArrayList) (staticobj.getRecordFromObj("SheetName", "style"));
    isattach = (ArrayList) (staticobj.getRecordFromObj("SheetName", "isattach"));
    isattachment = (ArrayList) (staticobj.getRecordFromObj("SheetName","isattachment"));
    columnWidth = (ArrayList) (staticobj.getRecordFromObj("SheetName","columnWidth"));
    columnHeight = (ArrayList) (staticobj.getRecordFromObj("SheetName","columnHeight"));
    width = (ArrayList) (staticobj.getRecordFromObj("SheetName", "width"));
    height = (ArrayList) (staticobj.getRecordFromObj("SheetName", "height"));
    para1 = (ArrayList) (staticobj.getRecordFromObj("SheetName", "para1"));
  }

  /********************************************
   ��SheetName��ԭ��ֵ���,
   ��SheetName�ĸ������Դ����ݿ���ȡ����һ��statcicObject����,�������ڴ���
   @throws Exception
   */
  public void setSheetName() throws Exception {
    if (sheet_ids != null)
      sheet_ids.clear();
    else
      sheet_ids = new ArrayList();
    if (module_ids != null)
      module_ids.clear();
    else
      module_ids = new ArrayList();
    if (sh_cname != null)
      sh_cname.clear();
    else
      sh_cname = new ArrayList();
    if (style != null)
      style.clear();
    else
      style = new ArrayList();
    if (isattach != null)
      isattach.clear();
    else
      isattach = new ArrayList();
    if (isattachment != null)
      isattachment.clear();
    else
      isattachment = new ArrayList();
    if (columnWidth != null)
      columnWidth.clear();
    else
      columnWidth = new ArrayList();
    if (columnHeight != null)
      columnHeight.clear();
    else
      columnHeight = new ArrayList();
    if (width != null)
      width.clear();
    else
      width = new ArrayList();
    if (height != null)
      height.clear();
    else
      height = new ArrayList();
    if (para1 != null)
      height.clear();
    else
      para1 = new ArrayList();
    String sql = "select sheet_id,module_id,sh_cname,style,isattach,isattachment,columnWidth,columnHeight" +
        ",width,height,para1 from taw_sheetname";
    int count;
    Hashtable ht;
    rt = new RecordSet();
    try {
      rt.execute(sql);
      count = rt.getCounts();
      while (rt.next()) {
        sheet_ids.add(StaticMethod.null2String(rt.getString(1)));
        module_ids.add(StaticMethod.null2String(rt.getString(2)));
        sh_cname.add(StaticMethod.null2String(rt.getString(3)));
        style.add(StaticMethod.null2String(rt.getString(4)));
        isattach.add(StaticMethod.null2String(rt.getString(5)));
        isattachment.add(StaticMethod.null2String(rt.getString(6)));
        columnWidth.add(StaticMethod.null2String(rt.getString(7)));
        columnHeight.add(StaticMethod.null2String(rt.getString(8)));
        width.add(StaticMethod.null2String(rt.getString(9)));
        height.add(StaticMethod.null2String(rt.getString(10)));
        para1.add(StaticMethod.null2String(rt.getString(11)));
      }
    }
    catch (Exception e) {
      rt=null;
      e.printStackTrace();
    }
    rt=null;
    staticobj.putRecordToObj("SheetName", "sheet_ids", sheet_ids);
    staticobj.putRecordToObj("SheetName", "module_ids", module_ids);
    staticobj.putRecordToObj("SheetName", "sh_cname", sh_cname);
    staticobj.putRecordToObj("SheetName", "style", style);
    staticobj.putRecordToObj("SheetName", "isattach", isattach);
    staticobj.putRecordToObj("SheetName", "isattachment", isattachment);
    staticobj.putRecordToObj("SheetName", "columnWidth", columnWidth);
    staticobj.putRecordToObj("SheetName", "columnHeight", columnHeight);
    staticobj.putRecordToObj("SheetName", "width", width);
    staticobj.putRecordToObj("SheetName", "height", height);
    staticobj.putRecordToObj("SheetName", "para1", para1);
  }

  /*************************************************
    �õ�SheetName�ļ�¼����
    @return int
   */
  public int getSheetNameCounts() {
    return array_size;
  }

  /*************************************************
    ��SheetName�ļ�¼�����ƶ�һ��
    @return boolean
   */
  public boolean next() {

    if ( (current_index + 1) < array_size) {
      current_index++;
      return true;
    }
    else {
      current_index = -1;
      return false;
    }
  }

  /*************************************************
    ���ݴ����module_id����@shEname�õ�SheetName������(sheet_id)
    @param  shEName ��Ӣ������
    @return String
   */
  public String getSheetID(String moduleID) {

    current_index = module_ids.indexOf(moduleID);
    if (current_index == -1)
      return "";
    String id = "";
    try {
      id = (String) sheet_ids.get(current_index);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      return id;
    }
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(module_id)
     @param id ��id
     @return String
   */
  public String getmodule_id(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) module_ids.get(current_index);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(sh_cname)
     @param id ��id
     @return String
   */
  public String getSh_cname(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) sh_cname.get(current_index);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(style)
     @param id ����id
     @return String
   */
  public String getStyle(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) style.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(isattach)
     @param id ����id
     @return String
   */
  public String getIsattach(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) isattach.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
    ����ָ����sheet_id�õ�SheetName������(isattach)
    @param id ����id
    @return String
   */
  public String getIsattachment(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) isattachment.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(columnWidth)
     @param id ����id
     @return String
   */
  public String getColumnWidth(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) columnWidth.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(columnHeight)
     @param id ����id
     @return String
   */
  public String getColumnHeight(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) columnHeight.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(width)
     @param id ����id
     @return String
   */
  public String getWidth(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) width.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
     ����ָ����sheet_id�õ�SheetName������(height)
     @param id ����id
     @return String
   */
  public String getHeight(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) height.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
   ����ָ����sheet_id�õ�SheetName������(para1)
   @param id ����id
   @return String
   */
  public String getPara1(String id) {

    current_index = sheet_ids.indexOf(id);
    if (current_index == -1)
      return "";
    String name = "";
    try {
      name = (String) para1.get(current_index);
    }
    catch (Exception e) {
      LogMan log = LogMan.getInstance();
      log.writeLog(e.toString());
    }
    return name;
  }

  /*************************************************
   ���ص�ǰ������SheetName����(sheet_id)
     @return String
   */
  public String getSheetID() {
    if (current_index == -1)
      return "";
    return (String) (sheet_ids.get(current_index));
  }

  /*************************************************
    ���ص�ǰ������SheetName����(module_id)
   @return String
   */
  public String getmodule_id() {
    if (current_index == -1)
      return "";
    return (String) module_ids.get(current_index);
  }

  /*************************************************
    ���ص�ǰ������SheetName����(sh_cname)
     @return String
     @throws Exception
   */
  public String getSh_cname() throws Exception {
    if (current_index == -1)
      return "";
    return (String) sh_cname.get(current_index);
  }

  /*************************************************
    ���ص�ǰ������SheetName����(style)
    @return String
   */
  public String getStyle() {
    if (current_index == -1)
      return "";
    return (String) style.get(current_index);
  }

  /*************************************************
   ���ص�ǰ������SheetName����(isattach)
   @return String
   */
  public String getIsattach() {
    if (current_index == -1)
      return "";
    return (String) isattach.get(current_index);
  }

  /*************************************************
     ���ص�ǰ������SheetName����(isattachment)
     @return String
   */
  public String getIsattachment() {
    if (current_index == -1)
      return "";
    return (String) isattachment.get(current_index);
  }

  /*************************************************
    ���ص�ǰ������SheetName����(columnWidth)
    @return String
   */
  public String getColumnWidth() {
    if (current_index == -1)
      return "";
    return (String) columnWidth.get(current_index);
  }

  /*************************************************
    ���ص�ǰ������SheetName����(columnHeight)
    @return String
   */
  public String getColumnHeight() {
    if (current_index == -1)
      return "";
    return (String) columnHeight.get(current_index);
  }

  /*************************************************
    ���ص�ǰ������SheetName����(width)
    @return String
   */
  public String getWidth() {
    if (current_index == -1)
      return "";
    return (String) width.get(current_index);
  }

  /*************************************************
    ���ص�ǰ������SheetName����(height)
    @return String
   */
  public String getHeight() {
    if (current_index == -1)
      return "";
    return (String) height.get(current_index);
  }

  /*************************************************
   ���ص�ǰ������SheetName����(para1)
   @return String
   */
  public String getPara1() {
    if (current_index == -1)
      return "";
    return (String) para1.get(current_index);
  }

  /*************************************************
   ��SheetName�����StaticObject������ɾ��
   */
  public void removeOSCache() {
    staticobj.removeObject("SheetName");
  }

}