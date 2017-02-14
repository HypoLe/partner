package com.boco.eoms.autosheet.util;


import java.util.*;
import com.boco.eoms.db.util.*;
import com.boco.eoms.common.util.*;

/*****************************************************************
 ��sheetvalue�����е��ֶ�
 �ӱ���taw_sheetvalue��ȡ��������hashtable,
 ��ͨ��getSheetValue����פ�����ڴ��������ȡ���ݵķ�����
 *****************************************************************/
public class SheetValue{

  /*********************************************
   * ���������
   */

  private ArrayList value_idAL = null,
  v_nameAL=null,isattachAL=null,indexAL=null,newLineAL=null,isQueryAL=null,attr_idAL=null,
  alignAL=null,valignAL=null,colspanAL=null,rowspanAL=null,widthAL=null,heightAL=null,nowrapAL=null,colorAL=null,
  showtypeAL=null,formWidthAL=null,formHeightAL=null,typeidAL=null,v_storetypeAL=null,isDefaultAL=null,
  defaultValAL=null,v_ctrlAL=null;
  private String sheetID;

  /***********
   * headItemCounts ��ͷ����ֵ����;bodyItemCounts ��������ֵ����;tailItemCounts ��β����ֵ����
   */
  private int headItemCounts=0,bodyItemCounts=0,tailItemCounts=0;

  /*******************************
   * �������ݿ��������
   */
  private RecordSet rt = null;
  private StaticObject staticobj = null;
  private LogMan log=LogMan.getInstance();
  /******************************
   *���嵱ǰ�ļ�¼�±�
   */
  private int current_index = -1;
  private int array_size = 0;
  /************************
   ���캯��
   */
  private SheetValue(){
    try{
      staticobj = StaticObject.getInstance();
      current_index=-1;
      getSheetValue() ;
      setCounts();
      array_size = value_idAL.size();
    }catch(Exception e){
      log.writeLog("SheetValue",e);
      e.printStackTrace();
    }
  }
  /************************
   ���캯��
   @param sheetID ������ID
   */
  public SheetValue(String sheetID){
    try{
      staticobj = StaticObject.getInstance();
      current_index=-1;
      getSheetValue(sheetID) ;
      array_size = value_idAL.size();
      setCounts();
      this.reset();
    }catch(Exception e){
      log.writeLog("SheetValue",e);
      e.printStackTrace();
    }
  }
  /***********************************
   staticʵ������SheetValue
   */
  private static SheetValue instance=new SheetValue();
  /*******
   ��̬����,��õ�ǰʵ��
   @return SheetValue
   */
  public static SheetValue getInstance(){
    return instance;
  }
  public static void removeInstanace(){
    instance=new SheetValue();
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
      if(this.getIsattach().equals("0")){//��������
        this.bodyItemCounts++;
      }
      if(this.getIsattach().equals("1")){//��ͷ����
        this.headItemCounts++;
      }
      if(this.getIsattach().equals("2")){//��β����
        this.tailItemCounts++;
      }
    }
  }
  /**************************
   ˽�з���,���������������б��ʼ��
   @throws Exception
   */
  private void getSheetValue() throws Exception{
    if(staticobj.getObject("SheetValue") == null)
      setSheetValue();
    value_idAL = (ArrayList)(staticobj.getRecordFromObj("SheetValue", "value_id"));
    v_nameAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","v_name"));
    isattachAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","isattach"));
    indexAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","index"));
    newLineAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","newLine"));
    isQueryAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","isQuery"));
    attr_idAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","attr_id"));
    /*��������ֵ��������е���ʾ����*/
    alignAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","align"));
    valignAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","valign"));
    colspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","colspan"));
    rowspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","rowspan"));
    widthAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","width"));
    heightAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","height"));
    nowrapAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","nowrap"));
    colorAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","color"));
    /*��������ֵ����������е���ʾ����*/
    showtypeAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","showtype"));
    formWidthAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","formWidth"));
    formHeightAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","formHeight"));
    typeidAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","typeid"));
    /*��������ֵ�����ݿ�洢����*/
    v_storetypeAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","v_storetype"));//�ֶ�����
    isDefaultAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","isDefault"));//�Ƿ�Ĭ��ֵ
    defaultValAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","defaultval"));//�Ƿ�Ĭ��ֵ
    v_ctrlAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue","v_ctrl"));
  }
  /**************************
   ˽�з���,���������������б��ʼ��
   @param sheetID ������ID
   @throws Exception
   */
  private void getSheetValue(String sheetID) throws Exception{
    //if(staticobj.getObject("SheetValue"+sheetID) == null)
    setSheetValue(sheetID);
    value_idAL = (ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID, "value_id"));
    v_nameAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"v_name"));
    isattachAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"isattach"));
    indexAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"index"));
    newLineAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"newLine"));
    isQueryAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"isQuery"));
    attr_idAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"attr_id"));

    alignAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"align"));
    valignAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"valign"));
    colspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"colspan"));
    rowspanAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"rowspan"));
    widthAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"width"));
    heightAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"height"));
    nowrapAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"nowrap"));
    colorAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"color"));

    showtypeAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"showtype"));
    formWidthAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"formWidth"));
    formHeightAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"formHeight"));
    typeidAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"typeid"));

    v_storetypeAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"v_storetype"));
    isDefaultAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"isDefault"));
    defaultValAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"defaultval"));
    v_ctrlAL=(ArrayList)(staticobj.getRecordFromObj("SheetValue"+sheetID,"v_ctrl"));
  }
  private void setSheetValue() throws Exception{
    this.initArrayList();
    rt = new RecordSet() ;
    try{
      String sql="select distinct value_id,v_name,isattach,index1,newLine,isQuery,attr_id,"+
                 "align,valign,colspan,rowspan,width,height,nowrap,color,"+
                 "showtype,formWidth,formHeight,typeid,"+
                 "v_storetype,isDefault,defaultval,v_ctrl from taw_sheetvalue";
      rt.execute(sql) ;
      while(rt.next()){
        value_idAL.add(StaticMethod.null2String(rt.getString("value_id")));
        v_nameAL.add(StaticMethod.null2String(rt.getString("v_name")));
        isattachAL.add(StaticMethod.null2String(rt.getString("isattach")));
        indexAL.add(StaticMethod.null2String(rt.getString("index1")));
        newLineAL.add(StaticMethod.null2String(rt.getString("newline")));
        isQueryAL.add(StaticMethod.null2String(rt.getString("isQuery")));
        attr_idAL.add(StaticMethod.null2String(rt.getString("attr_id")));

        alignAL.add(StaticMethod.null2String(rt.getString("align")));
        valignAL.add(StaticMethod.null2String(rt.getString("valign")));
        colspanAL.add(StaticMethod.null2String(rt.getString("colspan")));
        rowspanAL.add(StaticMethod.null2String(rt.getString("rowspan")));
        widthAL.add(StaticMethod.null2String(rt.getString("width")));
        heightAL.add(StaticMethod.null2String(rt.getString("height")));
        nowrapAL.add(StaticMethod.null2String(rt.getString("nowrap")));
        colorAL.add(StaticMethod.null2String(rt.getString("color")));

        showtypeAL.add(StaticMethod.null2String(rt.getString("showtype")));
        formWidthAL.add(StaticMethod.null2String(rt.getString("formwidth")));
        formHeightAL.add(StaticMethod.null2String(rt.getString("formheight")));
        typeidAL.add(StaticMethod.null2String(rt.getString("typeid")));

        v_storetypeAL.add(StaticMethod.null2String(rt.getString("v_storetype")));
        isDefaultAL.add(StaticMethod.null2String(rt.getString("isDefault")));
        defaultValAL.add(StaticMethod.null2String(rt.getString("defaultval")));
        v_ctrlAL.add(StaticMethod.null2String(rt.getString("v_ctrl")));


      }rt.givebackConnection();
    }
    catch(Exception e) {
      log.writeLog("SheetValue",e);
      e.printStackTrace();
      throw e ;
    }

    staticobj.putRecordToObj("SheetValue", "value_id", value_idAL);
    staticobj.putRecordToObj("SheetValue","v_name",v_nameAL);
    staticobj.putRecordToObj("SheetValue","isattach",isattachAL);
    staticobj.putRecordToObj("SheetValue","index",indexAL);
    staticobj.putRecordToObj("SheetValue","newLine",newLineAL);
    staticobj.putRecordToObj("SheetValue","isQuery",isQueryAL);
    staticobj.putRecordToObj("SheetValue","attr_id",attr_idAL);

    staticobj.putRecordToObj("SheetValue","align",alignAL);
    staticobj.putRecordToObj("SheetValue","valign",valignAL);
    staticobj.putRecordToObj("SheetValue","colspan",colspanAL);
    staticobj.putRecordToObj("SheetValue","rowspan",rowspanAL);
    staticobj.putRecordToObj("SheetValue","width",widthAL);
    staticobj.putRecordToObj("SheetValue","height",heightAL);
    staticobj.putRecordToObj("SheetValue","nowrap",nowrapAL);
    staticobj.putRecordToObj("SheetValue","color",colorAL);

    staticobj.putRecordToObj("SheetValue","showtype",showtypeAL);
    staticobj.putRecordToObj("SheetValue","formWidth",formWidthAL);
    staticobj.putRecordToObj("SheetValue","formHeight",formHeightAL);
    staticobj.putRecordToObj("SheetValue","typeid",typeidAL);

    staticobj.putRecordToObj("SheetValue","v_storetype",v_storetypeAL);
    staticobj.putRecordToObj("SheetValue","isDefault",isDefaultAL);
    staticobj.putRecordToObj("SheetValue","defaultval",isDefaultAL);
    staticobj.putRecordToObj("SheetValue","v_ctrl",v_ctrlAL);
  }
  private void setSheetValue(String sheetID) throws Exception{
    this.initArrayList();
    rt = new RecordSet() ;
    try{
      String sql="select distinct value_id,v_name,isattach,index1,newline,isquery,attr_id,"+
                 "align,valign,colspan,rowspan,width,height,nowrap,color,"+
                 "showtype,formWidth,formHeight,typeid,"+
                 "v_storetype,isDefault,defaultval,v_ctrl from taw_sheetvalue where sheet_id="+sheetID;
      rt.execute(sql) ;
      while(rt.next()){
        value_idAL.add(StaticMethod.null2String(rt.getString("value_id")));
        v_nameAL.add(StaticMethod.null2String(rt.getString("v_name")));
        isattachAL.add(StaticMethod.null2String(rt.getString("isattach")));
        indexAL.add(StaticMethod.null2String(rt.getString("index1")));
        newLineAL.add(StaticMethod.null2String(rt.getString("newline")));
        isQueryAL.add(StaticMethod.null2String(rt.getString("isquery")));
        attr_idAL.add(StaticMethod.null2String(rt.getString("attr_id")));

        alignAL.add(StaticMethod.null2String(rt.getString("align")));
        valignAL.add(StaticMethod.null2String(rt.getString("valign")));
        colspanAL.add(StaticMethod.null2String(rt.getString("colspan")));
        rowspanAL.add(StaticMethod.null2String(rt.getString("rowspan")));
        widthAL.add(StaticMethod.null2String(rt.getString("width")));
        heightAL.add(StaticMethod.null2String(rt.getString("height")));
        nowrapAL.add(StaticMethod.null2String(rt.getString("nowrap")));
        colorAL.add(StaticMethod.null2String(rt.getString("color")));

        showtypeAL.add(StaticMethod.null2String(rt.getString("showtype")));
        formWidthAL.add(StaticMethod.null2String(rt.getString("formwidth")));
        formHeightAL.add(StaticMethod.null2String(rt.getString("formheight")));
        typeidAL.add(StaticMethod.null2String(rt.getString("typeid")));

        v_storetypeAL.add(StaticMethod.null2String(rt.getString("v_storetype")));
        isDefaultAL.add(StaticMethod.null2String(rt.getString("isdefault")));
        defaultValAL.add(StaticMethod.null2String(rt.getString("defaultval")));
        v_ctrlAL.add(StaticMethod.null2String(rt.getString("v_ctrl")));
      }
    }
    catch(Exception e) {
     log.writeLog("SheetValue",e);
     e.printStackTrace();
      throw e ;
    }

    staticobj.putRecordToObj("SheetValue"+sheetID, "value_id", value_idAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"v_name",v_nameAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"isattach",isattachAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"index",indexAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"newLine",newLineAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"isQuery",isQueryAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"attr_id",attr_idAL);

    staticobj.putRecordToObj("SheetValue"+sheetID,"align",alignAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"valign",valignAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"colspan",colspanAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"rowspan",rowspanAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"width",widthAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"height",heightAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"nowrap",nowrapAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"color",colorAL);

    staticobj.putRecordToObj("SheetValue"+sheetID,"showtype",showtypeAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"formWidth",formWidthAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"formHeight",formHeightAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"typeid",typeidAL);

    staticobj.putRecordToObj("SheetValue"+sheetID,"v_storetype",v_storetypeAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"isDefault",isDefaultAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"defaultval",defaultValAL);
    staticobj.putRecordToObj("SheetValue"+sheetID,"v_ctrl",v_ctrlAL);

  }
  /*****
   �����������ܸ���
   @return int
   */
  public int getCounts()
  {
    return array_size;
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

  public void removeOSCache(){
    staticobj.removeObject("SheetValue");

  }
  public void removeOSCache(String sheetID){
    try{
      staticobj.removeObject("SheetValue"+sheetID);
    }catch(Exception e){
    }

  }
  /****
   *��ʼ�������б����
   */
  private void initArrayList(){
    if(value_idAL != null)
      value_idAL.clear();
    else
      value_idAL = new ArrayList();
    if(v_nameAL!=null)
      v_nameAL.clear();
    else
      v_nameAL=new ArrayList();
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
    /*isQuery*/
    if(isQueryAL!=null)
      isQueryAL.clear();
    else
      isQueryAL=new ArrayList();
    /*attr_idAL*/
    if(attr_idAL!=null)
      attr_idAL.clear();
    else
      attr_idAL=new ArrayList();

    /*valign*/
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
    /*widthAL*/
    if(widthAL!=null)
      widthAL.clear();
    else
      widthAL=new ArrayList();
    /*heightAL*/
    if(heightAL!=null)
      heightAL.clear();
    else
      heightAL=new ArrayList();
    /*nowrapAL*/
    if(nowrapAL!=null)
      nowrapAL.clear();
    else
      nowrapAL=new ArrayList();
    /*color*/
    if(colorAL!=null)
      colorAL.clear();
    else
      colorAL=new ArrayList();

    if(showtypeAL!=null)
      showtypeAL.clear();
    else
      showtypeAL=new ArrayList();
    /*formWidth*/
    if(formWidthAL!=null)
      formWidthAL.clear();
    else
      formWidthAL=new ArrayList();
    /*formHeight*/
    if(formHeightAL!=null)
      formHeightAL.clear();
    else
      formHeightAL=new ArrayList();
    /*typeid*/
    if(typeidAL!=null)
      typeidAL.clear();
    else
      typeidAL=new ArrayList();

    if(v_storetypeAL!=null)
      v_storetypeAL.clear();
    else
      v_storetypeAL=new ArrayList();
    /*isDefault*/
    if(isDefaultAL!=null)
      isDefaultAL.clear();
    else
      isDefaultAL=new ArrayList();
    /*defaultval*/
     if(defaultValAL!=null)
       defaultValAL.clear();
     else
       defaultValAL=new ArrayList();
    /*v_ctrl*/
    if(v_ctrlAL!=null)
      v_ctrlAL.clear();
    else
      v_ctrlAL=new ArrayList();
  }

  /*****************************
   ���ص�ǰ������ֵID
   @return String
   */
  public String getValueID(){
    if(current_index==-1)
      return "";
    return (String)(value_idAL.get(current_index));
  }
  /*************************
   ͨ������ֵ����������ֵid
   @param vName ����ֵ��
   @return String
   */
  public String getValueID(String vName){
    current_index=v_nameAL.indexOf(vName);
    String id="";
    try{
      if(current_index==-1)
      id= "";
      else
      id=(String)value_idAL.get(current_index);
    }catch(Exception e){
      e.printStackTrace();
    }finally{
      return id;
    }
  }

  /*****************************
  ���ݵ�ǰ�������ź�������־���ص�ǰ������ֵID,������¼��λ����ǰ��¼
  @param isattach ���ڱ�ͷ,������β
  @param index ��ǰ���Ե�������
  @return String
  */
 public String getValueID(String isattach,String index){
   this.reset();
   while(this.next()){
     if(this.getIsattach().equals(isattach)&&this.getIndex().equals(index))
       break;
   }
   if(current_index==-1)
      return "";
   return (String)(value_idAL.get(current_index));

  }
  /*****************************
   ���ص�ǰ��������
   @return String
   */
  public String getVName(){
    if(current_index==-1)
      return "";
    return (String)(v_nameAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ��������
   @param id ����id
   @return String
   */
  public String getVName(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(v_nameAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������isattach��0Ϊ���壬1Ϊ��ͷ��2Ϊ��β
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
    current_index=value_idAL.indexOf(id);
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
   ��������ID   ���ص�ǰ������index
   @param id ����id
   @return String
   */
  public String getIndex(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(indexAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������newLineAL
     @return String
     */
  public String getNewLine() {
      if (current_index == -1)
        return "";
      return (String) (newLineAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ������newLineAL
    @param id ����id
   @return String
   */
  public String getNewLine(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(newLineAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������isQuery
     @return String
     */
    public String getIsQuery(){
      if(current_index==-1)
      return "";
      return (String)(isQueryAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������isQuery
   @param id ����id
   @return String
   */
  public String getIsQuery(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(isQueryAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������attr_id
     @return String
     */
    public String getAttrID(){
      if(current_index==-1)
      return "";
      return (String)(attr_idAL.get(current_index));
  }
  /*****************************
   ��������ID   ���ص�ǰ������Index
   @param id ����id
   @return String
   */
  public String getAttrID(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(attr_idAL.get(current_index));
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
    current_index=value_idAL.indexOf(id);
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
    current_index=value_idAL.indexOf(id);
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
   ��������ID  ���ص�ǰ������colspanAL
   @param id ����id
   @return String
   */
  public String getColspan(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(colspanAL.get(current_index));
  }
  /*****************************
  ���ص�ǰ������rowspanAL
  @return String
  */
  public String getRowspan(){
    if(current_index==-1)
      return "";
    return (String)(rowspanAL.get(current_index));
  }
  /*****************************
   ��������ID ���ص�ǰ������rowspanAL
   @param id ����id
   @return String
   */
  public String getRowspan(String id){
  current_index=value_idAL.indexOf(id);
  if(current_index==-1)
      return "";
  return (String)(rowspanAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������widthAL
     @return String
     */
    public String getWidth(){
      if(current_index==-1)
      return "";
      return (String)(widthAL.get(current_index));
  }
  /*****************************
   ��������ID ���ص�ǰ������widthAL
   @param id ����id
   @return String
   */
  public String getWidth(String id){
    current_index=value_idAL.indexOf(id);
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
   ��������ID ���ص�ǰ������heightAL
   @param id ����id
   @return String
   */
  public String getHeight(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(heightAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������nowrapAL
     @return String
     */
  public String getNowrap(){
    if(current_index==-1)
      return "";
      return (String)(nowrapAL.get(current_index));
  }
  /*****************************
   ��������ID ���ص�ǰ������nowrapAL
   @param id ����id
   @return String
   */
  public String getNowrap(String id){
    current_index=value_idAL.indexOf(id);
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
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(colorAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ������ֵ��ʾ����,����5������,0ΪText Field,1ΪRedio Box 2 Ϊcheckbox
   3Ϊselect 4Ϊmultiple select 5textarea
   @return String
   */
  public String getShowtype(){
    if(current_index==-1)
      return "";
    return (String)(showtypeAL.get(current_index));
  }
  /*****************************
     ��������ID���ص�ǰ������ֵ��ʾ����,����5������,0ΪText Field,1ΪRedio Box 2 Ϊcheckbox
   3Ϊselect 4Ϊmultiple select 5textarea
     @param id ����id
     @return String
   */
  public String getShowtype(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(showtypeAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ������ֵ��ʾ���formWidth
   @return String
   */
  public String getFormWidth(){
    if(current_index==-1)
      return "";
    return (String)(formWidthAL.get(current_index));
  }
  /*****************************
     ��������ID���ص�ǰ������ֵ��ʾ���formWidth
     @param id ����id
     @return String
   */
  public String getFormWidth(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(formWidthAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ������ֵ��ʾ�߶�formHeight
   @return String
   */
  public String getFormHeight(){
    if(current_index==-1)
      return "";
    return (String)(formHeightAL.get(current_index));
  }
  /*****************************
     ��������ID���ص�ǰ������ֵ��ʾ�߶�formHeight
     @param id ����id
     @return String
   */
  public String getFormHeight(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(formHeightAL.get(current_index));
  }
  /*****************************
   ���ص�ǰ������ֵ��ʾ����typeid
   @return String
   */
  public String getTypeid(){
    if(current_index==-1)
      return "";
    return (String)(typeidAL.get(current_index));
  }
  /*****************************
     ��������ID���ص�ǰ����������typeid
     @param id ����id
     @return String
   */
  public String getTypeid(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(typeidAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ���������ݴ洢����v_storetype
     @return String
     */
    public String getVstoretype() {
      if(current_index==-1)
      return "";
      return (String) (v_storetypeAL.get(current_index));
  }

  /*****************************
    ���ص�ǰ���������ݴ洢����v_storetype
    @return String
    */
   public String getVstoretypeStr() {
     String tempStr = (String) (v_storetypeAL.get(current_index));
     if (tempStr.equals("date"))
       tempStr = "varchar(30)";
     else if (tempStr.equals("datetime"))
       tempStr = "varchar(50)";
     //��ֵ�Ĳ�ͬ����
    // else if (tempStr.equals("integer"))
    //   tempStr = "varchar(10)";
    // else if (tempStr.equals("float"))
    //   tempStr = "varchar(20)";
     return tempStr;
 }

  /*****************************
  ��������ID���ص�ǰ���������ݴ洢����v_storetype
  @param id ����id
   @return String
   */
  public String getVstoretype(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(v_storetypeAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������ֵ����isDefault
     @return String
     */
    public String getIsDefault(){
      if(current_index==-1)
      return "";
      return (String)(isDefaultAL.get(current_index));
  }
  /*****************************
     ���ص�ǰ������ֵ����isDefault
     @return String
     */
    public String getDefaultVal(){
      if(current_index==-1)
      return "";
      return (String)(defaultValAL.get(current_index));
  }

  /*****************************
  ��������ID���ص�ǰ������ֵ����isDefault
  @param id ����id
   @return String
   */
  public String getIsDefault(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(isDefaultAL.get(current_index));
  }

  /*****************************
 ��������ID���ص�ǰ������ֵ����isDefault
 @param id ����id
  @return String
  */
 public String getDefaultVal(String id){
   current_index=value_idAL.indexOf(id);
   if(current_index==-1)
     return "";
   return (String)(defaultValAL.get(current_index));
 }

  /*****************************
    ���ص�ǰ������vCtrl
    @return String
    */
   public String getVCtrl(){
     if(current_index==-1)
      return "";
     return (String)(v_ctrlAL.get(current_index));
  }
  /*****************************
   ��������ID���ص�ǰ�����Կ���
   @param id ����id
   @return String
  */
  public String getVCtrl(String id){
    current_index=value_idAL.indexOf(id);
    if(current_index==-1)
      return "";
    return (String)(v_ctrlAL.get(current_index));
  }

}
