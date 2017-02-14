package com.boco.eoms.autosheet.util;


import java.util.*;
import com.boco.eoms.db.util.*;
import com.boco.eoms.common.util.*;

/*****************************************************************
 �����е��ֶ�from taw_sheetattr
 ��ȡ��������hashtable,
 ��ͨ��getSheetAttribute����פ�����ڴ��������ȡ���ݵķ�����
 *****************************************************************/
public class SheetAttribute{

  /*********************************************
   * ���������
   */

  private ArrayList attr_idAL = null,
  attr_nameAL=null,isattachAL=null,indexAL=null,newLineAL=null,parent_idAL=null, levelAL=null,
  alignAL=null,valignAL=null,colspanAL=null,rowspanAL=null,widthAL=null,heightAL=null,nowrapAL=null,colorAL=null;
  private String sheetID;

  private LogMan log=LogMan.getInstance();
  /***********
   * headAttrCounts ��ͷ���Ը���;bodyAttrCounts �������Ը���;tailAttrCounts ��β���Ը���
   */
  private int headItemCounts=0,bodyItemCounts=0,tailItemCounts=0;
  /***********
   * �������ݿ��������
   */
  private RecordSet rt = null;
  private StaticObject staticobj = null;
  /***********
   *���嵱ǰ�ļ�¼�±�
   */
  private int current_index = -1;
  private int array_size = 0;
  /***********
   @param sheetID �������
   ���캯��
   */
  public SheetAttribute(String sheetID){
    this.sheetID=sheetID;
    try{
     staticobj = StaticObject.getInstance();
     current_index=-1;
     getSheetAttribute(sheetID) ;
     array_size = attr_idAL.size();
     setCounts();
     this.reset();
    }catch(Exception e){
      log.writeLog("SheetAttribute",e);
    }
  }
  private SheetAttribute(){

    try{
      staticobj = StaticObject.getInstance();
      current_index=-1;
      getSheetAttribute() ;
      setCounts();
      array_size = attr_idAL.size();
    }catch(Exception e){
      log.writeLog("SheetAttribute",e);
    }
  }
  /********************************
   staticʵ������SheetAttribute
   */
  private static SheetAttribute instance=new SheetAttribute();
  /*******
   ��̬����,��õ�ǰʵ��
   @return SheetAttribute
   */
  public static SheetAttribute getInstance(){
    return instance;
  }
  /****************
   ���õ�ǰ��������
   @return boolean
   */
  public boolean reset(){
    this.current_index=-1;
    return true;
  }
  /********
   ͳ�Ʊ�ͷ,����,��β���Եĸ���
   */
  private void setCounts(){
    while(this.next()){
       if(this.getIsattach().equals("0")){
        this.bodyItemCounts++;
      }
      if(this.getIsattach().equals("1")){
        this.headItemCounts++;
      }
      if(this.getIsattach().equals("2")){
        this.tailItemCounts++;
      }
    }
  }
  /**************************
   ˽�з���,���������������б��ʼ��
   @throws Exception
   */
  private void getSheetAttribute() throws Exception{
    if(staticobj.getObject("SheetAttribute") == null)
      setSheetAttribute();
    attr_idAL = (ArrayList)(staticobj.getRecordFromObj("SheetAttribute", "attr_idAL"));
    attr_nameAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","attr_nameAL"));
    isattachAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","isattachAL"));
    indexAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","indexAL"));
    newLineAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","newLineAL"));
    parent_idAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","parent_idAL"));
    levelAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","levelAL"));

    alignAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","alignAL"));
    valignAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","valignAL"));
    colspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","colspanAL"));
    rowspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","rowspanAL"));
    widthAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","widthAL"));
    heightAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","heightAL"));
    nowrapAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","nowrapAL"));
    colorAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute","colorAL"));

  }
  /**************************
   ˽�з���,���������������б��ʼ��
   @param sheetID �������
   @throws Exception
   */
  private void getSheetAttribute(String sheetID) throws Exception{
    //if(staticobj.getObject("SheetAttribute"+sheetID) == null)
    setSheetAttribute(sheetID);
    attr_idAL = (ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID, "attr_idAL"));
    attr_nameAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"attr_nameAL"));
    isattachAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"isattachAL"));
    indexAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"indexAL"));
    newLineAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"newLineAL"));
    parent_idAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"parent_idAL"));
    levelAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"levelAL"));

    alignAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"alignAL"));
    valignAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"valignAL"));
    colspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"colspanAL"));
    rowspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"rowspanAL"));
    widthAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"widthAL"));
    heightAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"heightAL"));
    nowrapAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"nowrapAL"));
    colorAL=(ArrayList)(staticobj.getRecordFromObj("SheetAttribute"+sheetID,"colorAL"));

  }
  private void setSheetAttribute() throws Exception{
    this.initArrayList();
    rt = new RecordSet() ;
    try{
      rt.execute("select attr_id,attr_name,isattach,index1,newLine,parent_id,level1,"+
                 "align,valign,colspan,rowspan,width,height,nowrap,color"+
                 " from taw_sheetattr") ;
      while(rt.next()){
        attr_idAL.add(StaticMethod.null2String(rt.getString("attr_id")));
        attr_nameAL.add(StaticMethod.null2String(rt.getString("attr_name")));
        isattachAL.add(StaticMethod.null2String(rt.getString("isattach")));
        indexAL.add(StaticMethod.null2String(rt.getString("index1")));
        newLineAL.add(StaticMethod.null2String(rt.getString("newLine")));
        parent_idAL.add(StaticMethod.null2String(rt.getString("parent_id")));
        levelAL.add(StaticMethod.null2String(rt.getString("level1")));

        alignAL.add(StaticMethod.null2String(rt.getString("align")));
        valignAL.add(StaticMethod.null2String(rt.getString("valign")));
        colspanAL.add(StaticMethod.null2String(rt.getString("colspan")));
        rowspanAL.add(StaticMethod.null2String(rt.getString("rowspan")));
        widthAL.add(StaticMethod.null2String(rt.getString("sytle")));
        heightAL.add(StaticMethod.null2String(rt.getString("height")));
        nowrapAL.add(StaticMethod.null2String(rt.getString("nowrap")));
        colorAL.add(StaticMethod.null2String(rt.getString("color")));

      }
    }
    catch(Exception e) {
      log.writeLog("SheetAttribute",e);
      rt=null;
      e.printStackTrace();
    }
    rt=null;
    staticobj.putRecordToObj("SheetAttribute", "attr_idAL", attr_idAL);
    staticobj.putRecordToObj("SheetAttribute","attr_nameAL",attr_nameAL);
    staticobj.putRecordToObj("SheetAttribute","isattachAL",isattachAL);
    staticobj.putRecordToObj("SheetAttribute","indexAL",indexAL);
    staticobj.putRecordToObj("SheetAttribute","newLineAL",newLineAL);
    staticobj.putRecordToObj("SheetAttribute","parent_idAL",parent_idAL);
    staticobj.putRecordToObj("SheetAttribute","levelAL",levelAL);

    staticobj.putRecordToObj("SheetAttribute","alignAL",alignAL);
    staticobj.putRecordToObj("SheetAttribute","valignAL",valignAL);
    staticobj.putRecordToObj("SheetAttribute","colspanAL",colspanAL);
    staticobj.putRecordToObj("SheetAttribute","rowspanAL",rowspanAL);
    staticobj.putRecordToObj("SheetAttribute","widthAL",widthAL);
    staticobj.putRecordToObj("SheetAttribute","heightAL",heightAL);
    staticobj.putRecordToObj("SheetAttribute","nowrapAL",nowrapAL);
    staticobj.putRecordToObj("SheetAttribute","colorAL",colorAL);

  }
  private void setSheetAttribute(String sheetID) throws Exception{
   this.initArrayList();
   rt = new RecordSet();
   try{
     String sql="select attr_id,attr_name,isattach,index1,newLine,parent_id,level1,"+
                 "align,valign,colspan,rowspan,width,height,nowrap,color"+
                 " from taw_sheetattr where sheet_id="+sheetID;
     rt.execute(sql) ;
      while(rt.next()){
        attr_idAL.add(StaticMethod.null2String(rt.getString("attr_id")));
        attr_nameAL.add(StaticMethod.null2String(rt.getString("attr_name")));
        isattachAL.add(StaticMethod.null2String(rt.getString("isattach")));
        indexAL.add(StaticMethod.null2String(rt.getString("index1")));
        newLineAL.add(StaticMethod.null2String(rt.getString("newline")));
        parent_idAL.add(StaticMethod.null2String(rt.getString("parent_id")));
        levelAL.add(StaticMethod.null2String(rt.getString("level1")));

        alignAL.add(StaticMethod.null2String(rt.getString("align")));
        valignAL.add(StaticMethod.null2String(rt.getString("valign")));
        colspanAL.add(StaticMethod.null2String(rt.getString("colspan")));
        rowspanAL.add(StaticMethod.null2String(rt.getString("rowspan")));
        widthAL.add(StaticMethod.null2String(rt.getString("width")));
        heightAL.add(StaticMethod.null2String(rt.getString("height")));
        nowrapAL.add(StaticMethod.null2String(rt.getString("nowrap")));
        colorAL.add(StaticMethod.null2String(rt.getString("color")));

      }

   }
   catch(Exception e) {
    log.writeLog("SheetAttribute",e);
    rt=null;
    e.printStackTrace();
   }
    rt=null;
   staticobj.putRecordToObj("SheetAttribute"+sheetID, "attr_idAL", attr_idAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"attr_nameAL",attr_nameAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"isattachAL",isattachAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"indexAL",indexAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"newLineAL",newLineAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"parent_idAL",parent_idAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"levelAL",levelAL);

   staticobj.putRecordToObj("SheetAttribute"+sheetID,"alignAL",alignAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"valignAL",valignAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"colspanAL",colspanAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"rowspanAL",rowspanAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"widthAL",widthAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"heightAL",heightAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"nowrapAL",nowrapAL);
   staticobj.putRecordToObj("SheetAttribute"+sheetID,"colorAL",colorAL);

 }

  public void removeOSCache(){
    staticobj.removeObject("SheetAttribute");
  }
  public void removeOSCache(String sheetID){
    try{
      staticobj.removeObject("SheetAttribute"+sheetID);
    }catch(Exception e){

    }
  }
  /*******************************************************************************
   ����Ϊ��������
   *******************************************************************************/
  /***************
   �������Եĸ���
   @return int
   */
  public int getCounts()
    {
            return 	array_size;
    }

  /*****
   ���ر�ͷ�����ĸ���
   @return int
   */
   public int getHeadItemCounts(){
     return this.headItemCounts;
   }
   /*****
   ���ر��������ĸ���
   @return int
   */
   public int getBodyItemCounts(){
     return this.bodyItemCounts;
   }
   /*****
   ���ر�β�����ĸ���
   @return int
   */
   public int getTailItemCounts(){

     return this.tailItemCounts;
   }
   /*****
    �������б���±�ֵ����1
    @return boolean
    */
   public boolean next(){

    if((current_index+1) < array_size){
      current_index++;
      return true;
    }
    else{
      current_index = -1;
      return false;
    }
  }
  /*****
   *��ʼ�������б�
   */
   private void initArrayList(){
     if(attr_idAL != null)
      attr_idAL.clear();
    else
      attr_idAL = new ArrayList();
    if(attr_nameAL!=null)
      attr_nameAL.clear();
    else
      attr_nameAL=new ArrayList();
    /*isattach*/
    if(isattachAL!=null)
      isattachAL.clear();
    else
      isattachAL=new ArrayList();
    /*index*/
    if(indexAL!=null)
      indexAL.clear();
    else
      indexAL=new ArrayList();
    /*newLineAL*/
    if(newLineAL!=null)
      newLineAL.clear();
    else
      newLineAL=new ArrayList();
    if(parent_idAL!=null)
      parent_idAL.clear();
    else
      parent_idAL=new ArrayList();
    if(levelAL!=null)
      levelAL.clear();
    else
      levelAL=new ArrayList();

    /*align*/
    if(alignAL!=null)
      alignAL.clear();
    else
      alignAL=new ArrayList();
    /*valign*/
    if(valignAL!=null)
      valignAL.clear();
    else
      valignAL=new ArrayList();

    /*colspanAL*/
    if(colspanAL!=null)
      colspanAL.clear();
    else
      colspanAL=new ArrayList();
    /*rowspanAL*/
    if(rowspanAL!=null)
      rowspanAL.clear();
    else
      rowspanAL=new ArrayList();

    if(widthAL!=null)
      widthAL.clear();
    else
      widthAL=new ArrayList();
    /*heightAL*/
    if(heightAL!=null)
      heightAL.clear();
    else
      heightAL=new ArrayList();
    /*nowrap*/
    if(nowrapAL!=null)
      nowrapAL.clear();
    else
      nowrapAL=new ArrayList();
    /*color*/
    if(colorAL!=null)
      colorAL.clear();
    else
      colorAL=new ArrayList();

   }

  /*****************************
   ���ص�ǰ������ID
   @return String
   */
  public String getAttrID(){
    if(current_index==-1)
      return "";
    return (String)(attr_idAL.get(current_index));
  }
  /*****************************
   ��������ֵ���ص�ǰ������ID
   @param attrName ��������Բ���ֵ
   @return String
   */
  public String getAttrID(String attrName){
    current_index=attr_nameAL.indexOf(attrName);
    if(current_index==-1)
      return "";
    return (String)(attr_idAL.get(current_index));
  }
  /*****************************
   ���ݵ�ǰ�������ź�������־���ص�ǰ������ID,������¼��λ����ǰ��¼
   @param isattach ���ڱ�ͷ,������β
   @param index ��ǰ���Ե�������
   @return String
   */
  public String getAttrID(String isattach,String index){
    this.reset();

    while(this.next()){
      if(this.getIsattach().equals(isattach)&&this.getIndex().equals(index))
        break;
    }
    if(this.current_index!=-1)
      return (String)(attr_idAL.get(current_index));
    else
      return "-1";
  }
  /*****************************
   ���ص�ǰ��������
   @return String
   */
  public String getAttrName(){
    if(current_index==-1)
      return "";
    return (String)(attr_nameAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ��������
   @param id ����id
   @return String
   */
  public String getAttrName(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(attr_nameAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������isattach
     @return String
     */
    public String getIsattach(){
      if(current_index==-1)
      return "";
      return (String)(isattachAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������isattach
   @param id ����id
   @return String
   */
  public String getIsattach(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(isattachAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������Index
     @return String
     */
    public String getIndex(){
      if(current_index==-1)
      return "";
      return (String)(indexAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������Index
   @param id ����id
   @return String
   */
  public String getIndex(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(indexAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������newLineAL
     @return String
     */
    public String getNewLine(){
      if(current_index==-1)
      return "";
      return (String)(newLineAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ������newLineAL
    @param id ����id
    @return String
    */
  public String getNewLine(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(newLineAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ�����Ը���id
   @return String
   */
  public String getParentID(){
    if(current_index==-1)
      return "";
    return (String)(parent_idAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ�����Ը���id
   @param id ����id
   @return String
   */
  public String getParentID(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(parent_idAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ�����Լ���
   @return String
   */
  public String getLevel(){
    if(current_index==-1)
      return "";
    return (String)(levelAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ�����Լ���
   @param id ����id
   @return String
   */
  public String getLevel(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(levelAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������align
     @return String
     */
    public String getAlign(){
      if(current_index==-1)
      return "";
      return (String)(alignAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������align
   @param id ����id
   @return String
   */
  public String getAlign(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(alignAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������valign
     @return String
     */
    public String getValign(){
      if(current_index==-1)
      return "";
      return (String)(valignAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������valign
   @param id ����id
   @return String
   */
  public String getValign(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(valignAL.get(current_index));
  }
  /*****************************
    ���ص�ǰ������colspanAL
    @return String
    */
   public String getColspan(){
     if(current_index==-1)
      return "";
     return (String)(colspanAL.get(current_index));
  }
  /*****************************
   ��������ID  ���ص�ǰ������colspan
   @param id ����id
   @return String
   */
  public String getColspan(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(colspanAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ������rowspan
   @return String
   */
  public String getRowspan(){
    if(current_index==-1)
      return "";
   return (String)(rowspanAL.get(current_index));
  }
  /*****************************
   ��������ID ���ص�ǰ������rowspan
   @param id ����id
   @return String
   */
  public String getRowspan(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(rowspanAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ��������ʾ���
     @return String
     */
    public String getWidth(){
      if(current_index==-1)
      return "";
      return (String)(widthAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ��������ʾ���
   @param id ����id
   @return String
   */
  public String getWidth(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(widthAL.get(current_index));
  }

  /*****************************
     ���ص�ǰ������heightAL
     @return String
     */
    public String getHeight(){
      if(current_index==-1)
      return "";
      return (String)(heightAL.get(current_index));
  }
  /*****************************
   ��������ID ���ص�ǰ������height
   @param id ����id
   @return String
   */
  public String getHeight(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(heightAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������nowrap
     @return String
     */
    public String getNowrap(){
      if(current_index==-1)
      return "";
      return (String)(nowrapAL.get(current_index));
    }
  /*****************************
   ��������ID   ���ص�ǰ������nowrap
   @param id ����id
   @return String
   */
  public String getNowrap(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(nowrapAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������color
     @return String
     */
    public String getColor(){
      if(current_index==-1)
      return "";
      return (String)(colorAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������color
   @param id ����id
   @return String
   */
  public String getColor(String id){
    current_index=attr_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(colorAL.get(current_index));
  }
}
