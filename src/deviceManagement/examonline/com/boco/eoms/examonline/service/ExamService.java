package com.boco.eoms.examonline.service;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005-12-20</p>
 * <p>Company: </p>
 * @author 邓林华
 * @version 1.0
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.dept.dao.hibernate.TawSystemDeptDaoHibernate;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.bo.TawSystemDictBo;
import com.boco.eoms.commons.system.user.dao.hibernate.TawSystemUserDaoHibernate;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.examonline.dao.ExamDAO;
import com.boco.eoms.examonline.model.ExamConfig;
import com.boco.eoms.examonline.model.ExamContent;
import com.boco.eoms.examonline.model.ExamInfo;
import com.boco.eoms.examonline.model.ExamIssue;
import com.boco.eoms.examonline.model.ExamStudyConfig;
import com.boco.eoms.examonline.model.ExamSubmit;
import com.boco.eoms.examonline.model.ExamTopTen;
import com.boco.eoms.examonline.model.ExamWarehouse;
import com.boco.eoms.examonline.model.TawSystemDeptExam;
import com.boco.eoms.examonline.qo.ExamDeptQueryVO;
import com.boco.eoms.examonline.qo.ExamTesterQueryVO;
import com.boco.eoms.examonline.qo.detailQO;
import com.boco.eoms.examonline.qo.studyQO;
import com.boco.eoms.examonline.util.ExamUtil;
import com.boco.eoms.gz.util.SystemResource;
//import com.boco.eoms.wsdict.dao.TawWsDictDAO;
public class ExamService {
  //题目导入类型
  public static int staticIssueStudy = 1;
  public static int staticIssueExam = 2;

  //报批流程
  public static int staticImport = 0;
  public static int staticReport = 1;
  public static int staticAuditingPass = 2;
  public static int staticReject = 3;

  //getConfigLab中的QueryType
  public static int staticQueryTypeEndtime=1;
  public static int staticQueryTypeBetweenOfIssue = 2;
  public static int staticQueryTypeExamUnfinished = 3;
  
  private ExamDAO DAO;

  public ExamDAO getDAO() {
	return DAO;
	}
	public void setDAO(ExamDAO dao) {
		DAO = dao;
	}
public ExamService() {
  }

	public void update(Object obj){
		DAO.update(obj);
	}
	
	public Object load(Class c,Serializable s){
		return DAO.load(c, s);
	}
	public void save(Object o){
		DAO.save(o);
	}
  /**
   * 提取非问答题所有选项
   * @param str
   * @return
   */
  public String getoptions(String str) throws Exception{
    String source = str.trim(); //去掉首尾空格
    source = source.replaceAll(";","");
    
    //导入的是问答题但是却选择的题目类型是非问答题
    if(source.indexOf("分数值：") != -1 || source.indexOf("分数值:") != -1){
    	throw new Exception("选择的题型是非问答题，但导入的是问答题");
    }
    
     StringTokenizer str1= new StringTokenizer(source,"@");
     String str2="";
     String str3="";
     String [] ordernum={"A.","B.","C.","D.","E.","F.","G.","H.","I.","J.","K.","L."};
     String [] order={"a.","b.","c.","d.","e.","f.","g.","h.","i.","j.","k.","l."};
     int i=0;
     int num=str1.countTokens();
     while(str1.hasMoreTokens()){//迭代选项中的每一项，
         str2=str1.nextToken();
         //去除两端的空格
         str2 = str2.trim();
         if(str2.indexOf(ordernum[i])!=0&&str2.indexOf(order[i])!=0){//如果当前项（第i项）不是以ordernum[i]或order[i]开头，则把ordernum[i]加到当前项前面
            str2=ordernum[i]+str2.trim();
         }
         if(i!=num-1)
         {
             str3 = str3.trim() + str2 + ";";//重新把";"加到当前选项末尾
         }
         else{
           str3 = str3 + str2;
         }
         i=i+1;
     }
     return str3;
 }
  
  /**
   * 解析出正确答案（非问答题）
   * @param result
   * @return
   */
  public String addresult(String result){
       result = result.replaceAll("正确答案：", "");
       result = result.replaceAll("@", "").trim();
       String obj="";
       int resultnum=result.length();
       if(resultnum>1){
         for(int i=0;i<resultnum;i++){
           if(i==resultnum-1){
             obj=obj+result.substring(i,i+1);
           }
           else{
              obj=obj+result.substring(i,i+1)+";";
           }
         }
       }
      else{
       obj= result;
      }

    return obj;
  }
  
  /**
   * 获取问答题的分数
   * @param result
   * @return
   */
  public int getAnswerPoint(String result){
	  int point = 0;
	  result = result.replaceAll("分数值：", "").replaceAll("@", "").trim();
	  point = Integer.parseInt(result);
	  return point;
  }
  
  /**
   * 获取问答题的正确答案
   * @param result
   * @return
   */
  public String getAnswerResult(String result){
	  result = result.replaceAll("正确答案：", "").replaceAll("@", "").trim();
	  return result;
  }
  
  
  /**
   * 试题导入
   * @param fileName 导入附件文件名
   * @param specialty 专业类别
   * @param manufacturer 厂家
   * @param contype 试题类型：单选1，多选2，判断题3，简答题4，论述题5
   * @param issueType 试题类型：有练习和考试两类
   * @param value 题目分数
   * @param userId 用户id
   * @return
   * @throws Exception
   */
public String importwordWarehouse(String fileName,String specialty,int manufacturer,int contype,int issueType, 
		int value,int difficulty,  String userId) throws Exception {
	
        String resultReturn = "all"; //跟踪是否所有行全部导入
        WordExtractor word=null;
    	FileInputStream fo=new FileInputStream(fileName);
        word = new WordExtractor(fo);
        String str = word.getText();//.extractText(fo);//注：提取字符串时，不会提取word中自动生成的编号
        char[] a = {'\r', '\n'};
        String b = String.valueOf(a);
        str = str.replaceAll(b, "@");//首先是用"@"把各行分好(注,这里的行不是word中的行为标准,word中的行有可能是该行容不下了自动写到下一行,
        //此处的行指含有回车符'\r'或换行符'\n'结尾的为一行)
        StringTokenizer str1 = new StringTokenizer(str, "##");//然后用"##"提取出完整的每一道试题
        //试题导入
        List onlineWarehouseList = new ArrayList();
        //去掉第一句str1.nextToken();
       // str1.nextToken();//wsy改因在文件名处规定了题目类型
        int rowCount = str1.countTokens();
        String exceptMsg = "";
        String exceptMsgDetail = "";
        String title = null;
        String options = null;
        String result = null;
        //先将Excel文件中的数据插入对象再放入list中。确保数据无误才进行入库操作
        for (int i = 1; i <= rowCount; i++) {
        	try{
        		  String source = str1.nextToken();//此句每执行一次，取到的就是一道题
			      StringTokenizer source_1= new StringTokenizer(source,"$");//用"$"把每道题分成三部分(题干,选项,正确答案))
			      int num=0;
			      num=source_1.countTokens();
			      if(num!=3){//如果当前这道题没有四部分，则抛出异常//现改为3,因为把难易度提到导入表单处规定,剩下三部分
			        if (exceptMsg.equals("")) {
			             exceptMsg = String.valueOf(i);
			             exceptMsgDetail = exceptMsg + "." + source.substring(0,10)+"......";
			           }
			           else {
			             exceptMsg = exceptMsg + " , " + String.valueOf(i);
			             exceptMsgDetail = exceptMsgDetail + "," +String.valueOf(i)+"."+ source.substring(0,10)+"......";
			           }
			           continue; //必填项为空时，跳过此跳记录。
			      }
			      else{
			        title = source_1.nextToken();//提取题干//new String(source_1.nextToken().getBytes("GBK"),"ISO-8859-1");
			        
			        if(contype != 4 && contype != 5){ //如果不是问答题
			        	options = getoptions(source_1.nextToken());//提取所有选项 //new String(getoptions(source_1.nextToken()).getBytes("GBK"), "ISO-8859-1");
					    result = addresult(source_1.nextToken()); //解析正确答案
			        }else{ //如果是问答题
			        	value = this.getAnswerPoint(source_1.nextToken());
			        	options = getAnswerResult(source_1.nextToken()); //把问答题正确答案放到options字段
			        	options = options.equals("") ? " " : options;
			        	result = " ";
			        }
			       
			        //int issueType =2 //发布类型：1、学习库 2、试题库
			        String comment = ""; //备注
			        //判断必添项是否为空
			        if (title.length() == 0 || options.length() == 0 || result.length() == 0
			            || difficulty==0 || issueType == 0 || "0".equals(specialty) || manufacturer == 0) {
			        	if (exceptMsg.equals("")) {
			        		exceptMsg = String.valueOf(i);
			            }
			            else {
			            	exceptMsg = exceptMsg + " , " + String.valueOf(i);
			            }
			        	continue; //必填项为空时，跳过此跳记录。
			        }
			        if (contype == 1) value = 2;
			        else if (contype == 2) value = 4;
			        else if (contype == 3) value = 1;
			        ExamWarehouse onlineWarehouse = new ExamWarehouse();
			        onlineWarehouse.setTitle(title);
			        onlineWarehouse.setOptions(options);
			        onlineWarehouse.setResult(result);
			        onlineWarehouse.setDifficulty(difficulty);
			        onlineWarehouse.setIssueType(issueType);
			        onlineWarehouse.setValue(value);
			        onlineWarehouse.setSpecialty(specialty);
			        onlineWarehouse.setManufacturer(manufacturer);
			        onlineWarehouse.setContype(contype);
			        onlineWarehouse.setComment(comment);
			        onlineWarehouseList.add(onlineWarehouse);
			      }
        	}
		    catch (Exception ex) {
		        ex.printStackTrace();
		        if (exceptMsg.equals("")) {
		        	exceptMsg = String.valueOf(i);
		        }
		        else {
		        	exceptMsg = exceptMsg + " , " + String.valueOf(i);
		        }
	        }
        }
        
         try{
        	if(onlineWarehouseList.size() == 0){
        		throw new Exception("请检查导入题型是否正确");
        	} 
        	 
	        //建立导入信息
	        java.util.Date currentDate = new java.util.Date();
	        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss");
	        String date = dateFormat.format(currentDate);
	        ExamInfo onlineInfo = new ExamInfo();
	        onlineInfo.setImportTime(StaticMethod.getTimestamp(date));
	        onlineInfo.setImportUser(userId);
	        onlineInfo.setSpecialty(specialty);
	        onlineInfo.setManufacturer(manufacturer);
	        onlineInfo.setDifficulty(difficulty);
	        onlineInfo.setContype(contype);
	        
	        String drId = DAO.save(onlineInfo).toString();
	        if (drId.length() == 0) {
	          return "error"; //导入不成功
	        }
		        //保存数据
		    for (int i = 0; i < onlineWarehouseList.size(); i++){
		    	ExamWarehouse onlineWarehouse = new ExamWarehouse();
		        onlineWarehouse = (ExamWarehouse) onlineWarehouseList.get(i);
		        onlineWarehouse.setImportId(drId);
		        //尚未审批的题目deleted都为1
		        onlineWarehouse.setDeleted(0);
		        DAO.save(onlineWarehouse);
		    }
		    if (StaticMethod.null2String(exceptMsg).length() != 0) 
		          resultReturn = "第  " + exceptMsg + "  题数据异常未能导入成功,错误试题部分题目如下：" + exceptMsgDetail;
		    else resultReturn="all";
		}catch (Exception e) {
			 String errMsg = "试题入库失败：" + e.getMessage();
		     throw new Exception(errMsg);
	  }
      finally {
        try {
             //删除临时文件
		     java.io.File f = new java.io.File(fileName);
		     f.delete();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      return resultReturn;
    }


  public String importWarehouse(String fileName, String userId) throws
      Exception {

    String resultReturn = "all"; //跟踪是否所有行全部导入
    FileInputStream file=null;
    try {
      //试题导入
      file= new FileInputStream(fileName);
      POIFSFileSystem fs = new POIFSFileSystem(file);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      List onlineWarehouseList = new ArrayList();
      int rowCount = sheet.getLastRowNum();
      String exceptMsg = "";

      //先将Excel文件中的数据插入对象再放入list中。确保数据无误才进行入库操作
      for (int i = 1; i <= rowCount; i++) {

        HSSFRow row = sheet.getRow(i);
        if (row == null) {
          continue;
        }
        HSSFCell cell0 = row.getCell( (short) 0);
        HSSFCell cell1 = row.getCell( (short) 1);
        HSSFCell cell2 = row.getCell( (short) 2);
        HSSFCell cell3 = row.getCell( (short) 3);
        HSSFCell cell4 = row.getCell( (short) 4);
        HSSFCell cell5 = row.getCell( (short) 5);
        HSSFCell cell6 = row.getCell( (short) 6);
        HSSFCell cell7 = row.getCell( (short) 7);
        HSSFCell cell8 = row.getCell( (short) 8);
        HSSFCell cell9 = row.getCell( (short) 9);

        //判断必添项是否为空
        if (cell0 == null || cell1 == null || cell2 == null
            || cell3 == null || cell4 == null || cell5 == null
            || cell6 == null || cell7 == null || cell8 == null) {
          if (exceptMsg.equals("")) {
            exceptMsg = String.valueOf(i + 1);
          }
          else {
            exceptMsg = exceptMsg + " , " + String.valueOf(i + 1);

          }
          continue; //必填项为空时，跳过此跳记录。
        }

        String title = StaticMethod.getString(StaticMethod.null2String(cell0.
            getStringCellValue())); //标题
        String options = StaticMethod.getString(StaticMethod.null2String(cell1.
            getStringCellValue())); //选项
        String result = StaticMethod.null2String(cell2.getStringCellValue()); //答案
        int difficulty = (int) StaticMethod.null2double(cell3.
            getNumericCellValue()); //难度
        int issueType = (int) StaticMethod.null2double(cell4.
            getNumericCellValue()); //发布类型：1、学习库 2、试题库
        int value = (int) StaticMethod.null2double(cell5.getNumericCellValue()); //分值. 只有学习库拥有分值;
        String specialty = StaticMethod.null2String(cell6.getStringCellValue()); //所属专业
/*        int specialty = (int) StaticMethod.null2double(cell6.
        		getNumericCellValue()); //所属专业
*/        int manufacturer = (int) StaticMethod.null2double(cell7.
            getNumericCellValue()); //所属厂家
        int equipment = (int) StaticMethod.null2double(cell8.
            getNumericCellValue()); //设备类型
        String comment = ""; //备注

        if (cell9 != null) {
          comment = StaticMethod.getString(StaticMethod.null2String(cell9.
              getStringCellValue())); //备注
        }

        //判断必添项是否为空
        if (title.length() == 0 || options.length() == 0 ||
            result.length() == 0
            || difficulty == 0 || issueType == 0 || value == 0 ||
            specialty == "0"
            || manufacturer == 0 || equipment == 0) {
          if (exceptMsg.equals("")) {
            exceptMsg = String.valueOf(i + 1);
          }
          else {
            exceptMsg = exceptMsg + " , " + String.valueOf(i + 1);

          }
          continue; //必填项为空时，跳过此跳记录。
        }

        ExamWarehouse onlineWarehouse = new ExamWarehouse();
        //onlineWarehouse.setDrId(drId);
        onlineWarehouse.setTitle(title);
        onlineWarehouse.setOptions(options);
        onlineWarehouse.setResult(result);
        onlineWarehouse.setDifficulty(difficulty);
        onlineWarehouse.setIssueType(issueType);
        onlineWarehouse.setValue(value);
        onlineWarehouse.setSpecialty(specialty);
        onlineWarehouse.setManufacturer(manufacturer);
        
        onlineWarehouse.setComment(comment);

        onlineWarehouseList.add(onlineWarehouse);
      }
      //建立导入信息
      java.util.Date currentDate = new java.util.Date();
      java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
          "yyyy-MM-dd HH:mm:ss");
      String date = dateFormat.format(currentDate);
      ExamInfo onlineInfo = new ExamInfo();
      onlineInfo.setImportTime(StaticMethod.getTimestamp(date));
      onlineInfo.setImportUser(StaticMethod.getString(userId));
      DAO.save(onlineInfo);

      List onlineInfoList = DAO.query(
          "from ExamInfo onlineInfo where onlineInfo.importTime = '" + date +
          "' and onlineInfo.importUser = '" + StaticMethod.getString(userId) +
          "'");
      String drId = "";
      if (!onlineInfoList.isEmpty()) {
        drId = ( (ExamInfo) onlineInfoList.get(0)).getImportId();
      }

      if (drId.length() == 0) {
        return "error"; //导入不成功
      }

      //保存数据
      for (int i = 0; i < onlineWarehouseList.size(); i++) {
    	  ExamWarehouse onlineWarehouse = new ExamWarehouse();
        onlineWarehouse = (ExamWarehouse) onlineWarehouseList.get(i);
        onlineWarehouse.setImportId(drId);
        //尚未审批的题目deleted都为1
        onlineWarehouse.setDeleted(1);
        DAO.save(onlineWarehouse);
      }

      //删除临时文件
      if (StaticMethod.null2String(exceptMsg).length() != 0) {
        resultReturn = "第  " + exceptMsg + "  行数据异常未能导入";
      }
    }
    catch (Exception ex) {
      String errMsg = "试题入库失败：" + ex.getMessage();
      throw new Exception(errMsg);
    }
    finally {
      try {
        //删除临时文件
       file.close();
	   java.io.File f = new java.io.File(fileName);
	   f.delete();
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return resultReturn;
  }

  /**
   * 随机取出一个学习类型的试题
   * typeSel 为8>2>3>1;4>3>2>3 形式
   * ";"表示选项分隔,":"表示类型分隔
   * @throws Exception
   * @return List
   */
  public List NewSubject(String typeSel) throws Exception {

    List subObjlist = new ArrayList();
    List subWarehouselist = null;
    ExamWarehouse subObj = new ExamWarehouse();
    String condition = "";
    condition = getCondition(typeSel);

    //取出所有试题
    subWarehouselist = DAO.getSubjectlist(condition, staticIssueExam);

    if (subWarehouselist.size() != 0) {
      int i = SubjectRandom(subWarehouselist.size());
      subObj = (ExamWarehouse) subWarehouselist.get(i);
      subObj.setTitle(subObj.getTitle());
      subObj.setOptions(subObj.getOptions());
      subObjlist.add(subObj);
    }
    else {
      subObjlist = subWarehouselist;
    }
    return subObjlist;
  }

  /**
   * 返回用户学习积分的基本信息。
   * @param userId String
   * @throws Exception
   * @return ExamContent
   */
  public ExamContent getUserStudycontent(String userId) throws Exception {
//    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    List Contentlist = null;
    ExamContent UserContent = new ExamContent();
    Contentlist = DAO.getUserContent(userId, "study");
    int numM = 0;
    int numR = 0;

    UserContent.setUserId(userId);

    if (Contentlist.size() == 0) {
      UserContent.setMark(0);
      UserContent.setRight(0);
      UserContent.setTotal(0);
    }
    else {
      for (int i = 0; i < Contentlist.size(); i++) {
    	  ExamContent objTemp = (ExamContent) Contentlist.get(i);
        numM += objTemp.getMark();
        numR += objTemp.getRight() > 1 ? 1 : 0;
      }
      UserContent.setMark(numM);
      UserContent.setRight(numR);
      UserContent.setTotal(Contentlist.size());
    }

    return UserContent;
  }

  /**
   * 取出一个小于count的随机数
   * @param count int
   * @return int
   */
  public int SubjectRandom(int count) {
    Random random = new Random();
    int subId = Math.abs(random.nextInt()) % count;
    return subId;
  }

  /**
   * 通过页面返回的字符串选项，提取出每题的题号和答案放入List中
   * @param optionsTemp String
   * @return List
   */
  public List getOptions(String optionsTemp) throws Exception {
    ArrayList Optionlist = new ArrayList();

    if (optionsTemp.length() > 33) { 
      String[] temp = optionsTemp.split(";");
      for (int i = 0; i < temp.length; i++) 
      {
        String subId = temp[i].toString().substring(0, 32);//取题号id
        String result = temp[i].toString().substring(32, 33);//取答案

        //判断用户是否多选 , 如是的话将多选答案进行连接。如：   A;C;D(判断方式：因提交时每题的各选项是连续的，即每题的)
        if (Optionlist.size() != 0 &&
            ((ExamWarehouse)Optionlist.get(Optionlist.size()-1)).getSubId().equalsIgnoreCase(subId)) 
        {
          ExamWarehouse LastSub =
              (ExamWarehouse) Optionlist.get(Optionlist.size() - 1);
          LastSub.setResult(LastSub.getResult() + ";" + result);
        }
        else 
        {
          ExamWarehouse warehouse = new ExamWarehouse();
          warehouse.setSubId(subId);
          warehouse.setResult(result);
          Optionlist.add(warehouse);
        }
      }
    }
    else {
      ExamWarehouse warehouse = new ExamWarehouse();
      warehouse.setSubId(optionsTemp.substring(0, 32));
      warehouse.setResult(optionsTemp.substring(32, 33));
      Optionlist.add(warehouse);
    }
    return Optionlist;
  }

  /**
   * 将提交答案情况入库
   * 并将题目项目返回，该题的完成情况存放在 value 字段中
   * @param answerList List
   * @param userId String
   * @throws Exception
   */
  public ExamWarehouse submitStudy(List answerList, String userId) throws
      Exception {
//    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
   
    ExamWarehouse answer = (ExamWarehouse) answerList.get(0);
    ExamContent examContent = new ExamContent();
    examContent.setAnswer(answer.getResult());
    examContent.setMark(StudyCheckAnswer(answer));
    //答对Right=2 ， 半对Right=1(半对暂时不用) ， 不对Right=0
    examContent.setRight( examContent.getMark() > 1 ? 2 : examContent.getMark());
    examContent.setSubId(answer.getSubId());
    examContent.setSubmitTime(StaticMethod.getTimestamp());
    examContent.setUserId(userId);
    DAO.save(examContent);
    
    ExamWarehouse subObj =
        (ExamWarehouse) DAO.load(ExamWarehouse.class,answer.getSubId());
    subObj.setValue(examContent.getRight());
    subObj.setTitle(subObj.getTitle());
    //本来试题导入的时候存放正确答案comment字段应该有值 但是数据库该字段为空
    //推断是导入代码问题；在导入代码修改正确之前暂时换一种实现方式
    //2011-01-16 liuchang修改
//    subObj.setComment(subObj.getComment());
    String rightChoise = subObj.getResult();            //正确答案字符串
    String[] rightChoiseArray = rightChoise.split(";"); //正确答案数组
    String[] optionsArray = subObj.getOptions().split(";");//选项数组
    StringBuffer comment = new StringBuffer();
    //有可能多选所以需要两层循环
    for(int i=0;i<optionsArray.length;i++){
    	for(int j=0;j<rightChoiseArray.length;j++){
    		if(optionsArray[i].substring(0,1).equals(rightChoiseArray[j])){
    			comment.append(optionsArray[i]+"    ");
        		continue;
        	}
    	}
    }
    subObj.setComment(comment.toString());
    return subObj;
  }

  /**
   * 判断提交的答案是否正确。并返回该题得分
   * 全对返回分值，半对返回1分。
   *
   * @param answer ExamWarehouse
   * @throws Exception
   * @return int
   */
  public int StudyCheckAnswer(ExamWarehouse answer) throws Exception {
    String subId = answer.getSubId();
//    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    ExamWarehouse standard =
        (ExamWarehouse) DAO.getHibernateTemplate().get(ExamWarehouse.class,subId);
    String anResult = answer.getResult().trim();
    String stResult = standard.getResult().trim();
    int value = 0;
    boolean chk=true;
    String[] anArray = anResult.split(";");
    for (int i = 0; i < anArray.length; i++) {
      if (stResult.indexOf(anArray[i]) == -1) {
        //value = 0;
    	  chk=false;
        break;
      }
    }
    if (chk&& stResult.length() == anResult.length()) {
      value = standard.getValue();

    }
    return value ;//> 1 ? 2 : 0; //如开启半对得1分功能时改为 "return value;"
  }
  public int checkAnswer(ExamWarehouse answer) throws Exception {
	    String subId = answer.getSubId();
//	    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	    ExamIssue standard =(ExamIssue) DAO.getHibernateTemplate().get(ExamIssue.class,subId);
	    String anResult = answer.getResult().trim();
	    String stResult = standard.getResult();
	    int value = 0;//当设置成1时,即是对半得1分,不然就是对半不得分
        boolean chk=true;
	    String[] anArray = anResult.split(";");
	    for (int i = 0; i < anArray.length; i++) {//逐个判断所选答案是否在标准答案内
	      if (stResult.indexOf(anArray[i]) == -1) {
	        chk=false;
	        break;
	      }
	    }
	    if (chk && stResult.length() == anResult.length()) {
	      value = standard.getValue();

	    }
	    return value ;//> 1 ? 2 : 0; //如开启半对得1分功能时改为 "return value;"
	  }

  /**
   * 将"专业>设备>厂家>难度"形式用";"分隔的字符串整理成为where的条件
   * @param typeSel String
   * @throws Exception
   * @return String
   * @author
   */
  public String getCondition(String typeSel)  {
    String condition = "";
    String[] StringArray = typeSel.split(";");

    for (int i = 0; i < StringArray.length; i++) {
      if (i == 0) {
        condition += " and";
      }
      else {
        condition += " or";
      }
      String[] tempArray = StringArray[i].split(">");
      condition += " (specialty=" + tempArray[0];
      condition += " and manufacturer=" + tempArray[1];
     
      condition += " and difficulty=" + tempArray[2] ;
      condition += " and contype=" + tempArray[3]+ ")";
    }
    return condition;
  }

  public List getTopTen() throws Exception {
//    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    
    TawSystemUserDaoHibernate tsudh=
    	(TawSystemUserDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");
    List toplist = new ArrayList();
    List temp = DAO.getTopTen();
    for (int i = 0; i < 10 && i < temp.size(); i++){
      ExamTopTen topTen = new ExamTopTen();
      Object[] tmpObj = (Object[]) temp.get(i);
      topTen.setSum(((Integer) tmpObj[0]).intValue());
      topTen.setUserId(tsudh.id2Name( (String) tmpObj[1]));
      toplist.add(topTen);
    }

    return toplist;
  }

  /**
   * 返回导入批次信息List,形式为"text=2006-03-23 17:39:37-系统管理员 , value=importId".
   * @throws Exception
   * @return List
   */
  public List getInfoLab() throws Exception {
//    ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    List tmplist = DAO.getAllExamInfo();
    List infoLablist = new ArrayList();
    Iterator iterator = tmplist.iterator();

    while (iterator.hasNext()) {
      ExamInfo onlineInfo = new ExamInfo();
      onlineInfo = (ExamInfo) iterator.next();
      String label = onlineInfo.getImportTime().toString().substring(0, 19) +
          "-"
          + StaticMethod.getGBString(onlineInfo.getImportUser());
      infoLablist.add(new org.apache.struts.util.LabelValueBean(
          label, onlineInfo.getImportId()));
    }

    return infoLablist;
  }

  /**
   * 返回用户标签List,形式为"text=省公司-系统管理员 , value=admin".
   * @throws Exception
   * @return List
   */
  public List getUserLab() throws Exception {
	ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
    String name = "";
    String deptName = "";
    String userId = "";
    List userLablist = new ArrayList();
    List userlist = null;

    userlist =SystemResource.getUserDao().getNoDelUser();// tawRmUserDAO.list();
    Iterator iterator = userlist.iterator();

    while (iterator.hasNext()) {
    	TawSystemUser  tawRmUser = (TawSystemUser) iterator.next();
    	userId = tawRmUser.getUserid();
    	deptName = service.id2Name(tawRmUser.getDeptid(), "tawSystemUserDao");
//    	deptName = SystemResource.deptId2Name(tawRmUser.getDeptid());
    	name = tawRmUser.getUsername();
    	userLablist.add(new org.apache.struts.util.LabelValueBean(
          deptName + "-" + name, userId));
    }
    return userLablist;
  }
  public List getUserLabNew(int deptId) throws Exception {
    List userLablist =SystemResource.getUserDao().getUserBydeptids(String.valueOf(deptId)) ;

    return userLablist;
  }

  public List getDeptLab() throws Exception {
    List deptLablist = new ArrayList();
    TawSystemDeptDao tawSystemDeptDao=(TawSystemDeptDao)ApplicationContextHolder.getInstance().getBean("tawSystemDeptDao");
    
    List deptlist = tawSystemDeptDao.getworkplanDeptnames();
    Iterator iterator = deptlist.iterator();
    while (iterator.hasNext()) {
    	TawSystemDept tawDept =
          new TawSystemDept();
      tawDept = (TawSystemDept) iterator.next();
      deptLablist.add(new org.apache.struts.util.LabelValueBean(
          tawDept.getDeptName(), String.valueOf(tawDept.getDeptId())));
    }

    return deptLablist;
  }
  
//  ///////////////////////////////////////////////////////////////
//  /**
//   * 生成试卷
//   */
//public String configSubmit(ActionMapping mapping,HttpServletRequest request,HttpServletResponse response, String userId) throws Exception {	String title = StaticMethod.nullObject2String(request.getParameter("title"));
//	String typeSel = StaticMethod.nullObject2String(request.getParameter("typeSel")).trim();
//    String starttime =StaticMethod.nullObject2String(request.getParameter("starttime"));
//    String endtime = StaticMethod.nullObject2String(request.getParameter("endtime"));
//    String companyId = StaticMethod.nullObject2String(request.getParameter("companyId"));
//    int testerCount=StaticMethod.null2int(request.getParameter("testerCount"));
//    String companys=StaticMethod.nullObject2String(request.getParameter("companys"));
//    String reGenerate = StaticMethod.nullObject2String(request.getParameter("reGenerate"));
//    String typeArrAll=null;
//    //试卷列表
//    List examlist1=null;
//   
//	    ExamConfig onlineConfig = new ExamConfig();
//	    if(title.trim().length()>0){ //由配置页面跳转而来
//		    onlineConfig.setTitle(title);
//		    onlineConfig.setIssueUser(userId);
//		    onlineConfig.setTypeSel(typeSel);
//		    
//		    //格式1180101>1180201>1>20:10:20:10:10,最后一个>号之前的格式是：专业，厂家，难易度
//		    //20:10:20:10:10 分别代表单选、多选、判断、简单、论述 各种题的数目
//		    String[] typeSel1 = typeSel.split("\\|");
//		    String[] typeArr=null;
//		   
//		    for(int i=0;i<typeSel1.length;i++){
//		    typeArr = typeSel1[i].split(">");
//		    typeArr=typeSel1[i].split(":");
//		    typeArrAll+=typeArr;
//		    }
//	    	String spec = typeArr[0]; //专业（实际值）
//	    	String manu = typeArr[1]; //厂家（实际值）
//	    	String diff = typeArr[2]; //难度（实际值）
//		    request.getSession().setAttribute("specialtySel", spec);    
//	    	request.getSession().setAttribute("manufacturerSel", manu); 
//	    	request.getSession().setAttribute("difficulty", diff);      
//		    
//		    request.getSession().setAttribute("onlineConfig", onlineConfig);
//	    	request.getSession().setAttribute("examlist1", examlist1);
//	    	
//	    	request.getSession().setAttribute("title", request.getParameter("title"));
//	    	request.getSession().setAttribute("specialtySelValue", request.getParameter("specialtySelValue"));  //专业（显示值）
//	    	request.getSession().setAttribute("manufacturerSelValue", request.getParameter("manufacturerSelValue"));//厂家（显示值）
//	    	request.getSession().setAttribute("difficultyValue", request.getParameter("difficultyValue")); //难度（显示值）
//	    	//每种题在页面上设置的题数
//	    	String radio= request.getParameter("radio");
//	    	request.getSession().setAttribute("radio",radio);
//	    	String multiple=request.getParameter("multiple");
//	    	request.getSession().setAttribute("multiple",multiple );
//	    	String judge=request.getParameter("judge");
//	    	request.getSession().setAttribute("judge", judge);
//	    	String qa=request.getParameter("qa");
//	    	request.getSession().setAttribute("qa", qa);
//	    	String essay=request.getParameter("essay");
//	    	request.getSession().setAttribute("essay", essay);
//	    	
//	    	//每种题在页面上设置的单题分值
//	    	request.getSession().setAttribute("radioScore", request.getParameter("radioScore"));
//	    	request.getSession().setAttribute("multipleScore", request.getParameter("multipleScore"));
//	    	request.getSession().setAttribute("judgeScore", request.getParameter("judgeScore"));
//	    	request.getSession().setAttribute("qaScore", request.getParameter("qaScore"));
//	    	request.getSession().setAttribute("essayScore", request.getParameter("essayScore"));
//	    	
//	    	//设置每种题型的题目总数量（在按钮上显示题目总数）
//	    	int radioCount = DAO.findExamWarehouseCount(spec, manu, diff, "1");
//	        int multipleCount = DAO.findExamWarehouseCount(spec, manu, diff, "2");
//	        int judgeCount = DAO.findExamWarehouseCount(spec, manu, diff, "3");
//	        int qaCount = DAO.findExamWarehouseCount(spec, manu, diff, "4");
//	        int essayCount = DAO.findExamWarehouseCount(spec, manu, diff, "5");
//	        request.getSession().setAttribute("radioCount", radioCount);
//	      	request.getSession().setAttribute("multipleCount", multipleCount);
//	      	request.getSession().setAttribute("judgeCount", judgeCount);
//	      	request.getSession().setAttribute("qaCount", qaCount);
//	      	request.getSession().setAttribute("essayCount", essayCount);
//	    	return "generate";
//	    }
//	    else if(reGenerate.trim().length()>0){//重新生成试卷
//	    	onlineConfig=(ExamConfig)request.getSession().getAttribute("onlineConfig");
//	    	examlist1= getIssueExamList(onlineConfig.getTypeSel());//取得随机试卷
//	    	if (examlist1.size() == 0) {
//	    		return "failure";
//	   	    }
//	    	//每种题在页面上设置的单题分值
//    	    String radioScoreStr =  (String)request.getSession().getAttribute("radioScore");
//    	    String multipleScoreStr = (String)request.getSession().getAttribute("multipleScore");
//    	    String judgeScoreStr = (String)request.getSession().getAttribute("judgeScore");
//	    	String qaScoreStr = (String)request.getSession().getAttribute("qaScore");
//	    	String essayScoreStr = (String)request.getSession().getAttribute("essayScore");
//	    	  
//	    	Integer radioScore = !StringUtils.isEmpty(radioScoreStr)? Integer.parseInt(radioScoreStr) : 0;
//	    	Integer multipleScore = !StringUtils.isEmpty(multipleScoreStr)? Integer.parseInt(multipleScoreStr) : 0;
//	    	Integer judgeScore = !StringUtils.isEmpty(judgeScoreStr)? Integer.parseInt(judgeScoreStr) : 0;
//	    	Integer qaScore = !StringUtils.isEmpty(qaScoreStr)? Integer.parseInt(qaScoreStr) : 0;
//	    	Integer essayScore = !StringUtils.isEmpty(essayScoreStr)? Integer.parseInt(essayScoreStr) : 0;
//	    	
//	    	Integer totalScore = 0;
//	    	List<ExamWarehouse> l = new ArrayList<ExamWarehouse>();
//	    	
//	    	//计算总分
//	    	for(int i=0;i<examlist1.size();i++){
//	    		ExamWarehouse warehouse = (ExamWarehouse) examlist1.get(i);
//	    		ExamWarehouse e = new ExamWarehouse();
//	    		BeanUtils.copyProperties(e, warehouse);
//	    		
//	    		if(warehouse.getContype() == 1){
//	    			e.setValue(radioScore);
//	    		}else if(warehouse.getContype() == 2){
//	    			e.setValue(multipleScore);
//	    		}else if(warehouse.getContype() == 3){
//	    			e.setValue(judgeScore);
//	    		}else if(warehouse.getContype() == 4){
//	    			e.setValue(qaScore);
//	    		}else if(warehouse.getContype() == 5){
//	    			e.setValue(essayScore);
//	    		}
//	    		totalScore += e.getValue();
//	    		l.add(e);
//	    	}
//	    	
//	    	//在session内保存生成的试卷
//	    	request.getSession().setAttribute("examlist1", l);
//	    	//在session返回内保存总分
//	    	request.getSession().setAttribute("totalScore", totalScore);	    	 
//	    	return "selectDeptSub";
//	    }
//	    //提交生成的试卷（点击制定按钮）
//	    else{ 
//	    	String issueId="";
//	    	 try{
//		    	String carray[]=companys.split(";");
//		    	for(int i=0;i<carray.length;i++){
//		    		ExamConfig ocf=new ExamConfig();
//		    		ExamConfig tmp=(ExamConfig)request.getSession().getAttribute("onlineConfig");
//		    		String typesel = tmp.getTypeSel().trim();
//		    		ocf.setTitle(tmp.getTitle());
//		    		ocf.setIssueUser(tmp.getIssueUser());
//		    		ocf.setTypeSel(typesel);
//		    		
//		    		//typesel 格式1180101>1180201>1>20:10:20:10:10(增加简答题和论述题) 1180101>1180201>1>20:10:20(未增加简答题和论述题)
//		    		String[] typeselArr = typesel.split(">");
//		    		String count = typeselArr[3];
//		    		String qaCount = ""; //简答题题数
//		    		String essayCount = count.substring(count.lastIndexOf(":")+1,count.length()); //论述题题数
//		    		if("".equals(essayCount)||"0".equals(essayCount)){ //如果没有设置论述题
//		    			String temp = count.substring(0,count.lastIndexOf(":"));
//		    			qaCount = temp.substring(temp.lastIndexOf(":")+1,temp.length());
//		    			if("".equals(qaCount)||"0".equals(qaCount)){//如果没有设置简答题
//		    				ocf.setMarkFlag(0); //无需阅卷（没有设置主观题）
//		    			}else{
//			    			ocf.setMarkFlag(1); //待阅卷
//			    		}
//		    		}else{
//		    			ocf.setMarkFlag(1); //待阅卷
//		    		}
//		    		String singlecompany[]=carray[i].split(":");
//		    		companyId=singlecompany[0];
//		    		testerCount=Integer.valueOf(singlecompany[1]);
//		    	    ocf.setCompanyId(companyId);
//			    	ocf.setTesterCount(testerCount);
//			    	ocf.setStartTime(Timestamp.valueOf(starttime));
//			    	ocf.setEndTime(Timestamp.valueOf(endtime));
//			    	
//			    	Integer totalScore = (Integer)request.getSession().getAttribute("totalScore");
//			    	ocf.setMark(totalScore);
//			    	issueId=(String) DAO.save(ocf);
//			    	
//			    	examlist1=(List)request.getSession().getAttribute("examlist1");
//			    	Iterator iterator = examlist1.iterator();
//			    	while (iterator.hasNext()) {
//			    		ExamWarehouse warehouse = (ExamWarehouse) iterator.next();
//			    		ExamIssue ei=new ExamIssue();
//			    		BeanUtils.copyProperties(ei,warehouse);
//			    		ei.setIssueid(issueId);
//			    		DAO.save(ei);
//			    	}
//		    	}
//			  }catch (Exception e){
//			      e.printStackTrace();
//			  }
//			 return "success";
//	    }
//  }
/////////////////////////////////////////////////////////////////////
/**
 * 生成试卷
 */
public String configSubmit(ActionMapping mapping,HttpServletRequest request,HttpServletResponse response, String userId) throws Exception {	String title = StaticMethod.nullObject2String(request.getParameter("title"));
String typeSel = StaticMethod.nullObject2String(request.getParameter("typeSel")).trim();
String starttime =StaticMethod.nullObject2String(request.getParameter("starttime"));
String endtime = StaticMethod.nullObject2String(request.getParameter("endtime"));
String companyId = StaticMethod.nullObject2String(request.getParameter("companyId"));
int testerCount=StaticMethod.null2int(request.getParameter("testerCount"));
String companys=StaticMethod.nullObject2String(request.getParameter("companys"));
String reGenerate = StaticMethod.nullObject2String(request.getParameter("reGenerate"));
//试卷列表
List examlist1=null;

ExamConfig onlineConfig = new ExamConfig();
if(title.trim().length()>0){ //由配置页面跳转而来
	onlineConfig.setTitle(title);
	onlineConfig.setIssueUser(userId);
	onlineConfig.setTypeSel(typeSel);
	
	//格式1180101>1180201>1>20:10:20:10:10,最后一个>号之前的格式是：专业，厂家，难易度
	//20:10:20:10:10 分别代表单选、多选、判断、简单、论述 各种题的数目
	String[] typeArr = typeSel.split(">");
	
	String spec = typeArr[0]; //专业（实际值）
	String manu = typeArr[1]; //厂家（实际值）
	String diff = typeArr[2]; //难度（实际值）
	request.getSession().setAttribute("specialtySel", spec);    
	request.getSession().setAttribute("manufacturerSel", manu); 
	request.getSession().setAttribute("difficulty", diff);      
	
	request.getSession().setAttribute("onlineConfig", onlineConfig);
	request.getSession().setAttribute("examlist1", examlist1);
	
	request.getSession().setAttribute("title", request.getParameter("title"));
	request.getSession().setAttribute("specialtySelValue", request.getParameter("specialtySelValue"));  //专业（显示值）
	request.getSession().setAttribute("manufacturerSelValue", request.getParameter("manufacturerSelValue"));//厂家（显示值）
	request.getSession().setAttribute("difficultyValue", request.getParameter("difficultyValue")); //难度（显示值）
	//每种题在页面上设置的题数
	request.getSession().setAttribute("radio", request.getParameter("radio"));
	request.getSession().setAttribute("multiple", request.getParameter("multiple"));
	request.getSession().setAttribute("judge", request.getParameter("judge"));
	request.getSession().setAttribute("qa", request.getParameter("qa"));
	request.getSession().setAttribute("essay", request.getParameter("essay"));
	
	//每种题在页面上设置的单题分值
	request.getSession().setAttribute("radioScore", request.getParameter("radioScore"));
	request.getSession().setAttribute("multipleScore", request.getParameter("multipleScore"));
	request.getSession().setAttribute("judgeScore", request.getParameter("judgeScore"));
	request.getSession().setAttribute("qaScore", request.getParameter("qaScore"));
	request.getSession().setAttribute("essayScore", request.getParameter("essayScore"));
	
	//设置每种题型的题目总数量（在按钮上显示题目总数）
	int radioCount = DAO.findExamWarehouseCount(spec, manu, diff, "1");
	int multipleCount = DAO.findExamWarehouseCount(spec, manu, diff, "2");
	int judgeCount = DAO.findExamWarehouseCount(spec, manu, diff, "3");
	int qaCount = DAO.findExamWarehouseCount(spec, manu, diff, "4");
	int essayCount = DAO.findExamWarehouseCount(spec, manu, diff, "5");
	request.getSession().setAttribute("radioCount", radioCount);
	request.getSession().setAttribute("multipleCount", multipleCount);
	request.getSession().setAttribute("judgeCount", judgeCount);
	request.getSession().setAttribute("qaCount", qaCount);
	request.getSession().setAttribute("essayCount", essayCount);
	return "generate";
}
else if(reGenerate.trim().length()>0){//重新生成试卷
	onlineConfig=(ExamConfig)request.getSession().getAttribute("onlineConfig");
	examlist1= getIssueExamList(onlineConfig.getTypeSel());//取得随机试卷
	if (examlist1.size() == 0) {
		return "failure";
	}
	//每种题在页面上设置的单题分值
	String radioScoreStr =  (String)request.getSession().getAttribute("radioScore");
	String multipleScoreStr = (String)request.getSession().getAttribute("multipleScore");
	String judgeScoreStr = (String)request.getSession().getAttribute("judgeScore");
	String qaScoreStr = (String)request.getSession().getAttribute("qaScore");
	String essayScoreStr = (String)request.getSession().getAttribute("essayScore");
	
	Integer radioScore = !StringUtils.isEmpty(radioScoreStr)? Integer.parseInt(radioScoreStr) : 0;
	Integer multipleScore = !StringUtils.isEmpty(multipleScoreStr)? Integer.parseInt(multipleScoreStr) : 0;
	Integer judgeScore = !StringUtils.isEmpty(judgeScoreStr)? Integer.parseInt(judgeScoreStr) : 0;
	Integer qaScore = !StringUtils.isEmpty(qaScoreStr)? Integer.parseInt(qaScoreStr) : 0;
	Integer essayScore = !StringUtils.isEmpty(essayScoreStr)? Integer.parseInt(essayScoreStr) : 0;
	
	Integer totalScore = 0;
	List<ExamWarehouse> l = new ArrayList<ExamWarehouse>();
	
	//计算总分
	for(int i=0;i<examlist1.size();i++){
		ExamWarehouse warehouse = (ExamWarehouse) examlist1.get(i);
		ExamWarehouse e = new ExamWarehouse();
		BeanUtils.copyProperties(e, warehouse);
		
		if(warehouse.getContype() == 1){
			e.setValue(radioScore);
		}else if(warehouse.getContype() == 2){
			e.setValue(multipleScore);
		}else if(warehouse.getContype() == 3){
			e.setValue(judgeScore);
		}else if(warehouse.getContype() == 4){
			e.setValue(qaScore);
		}else if(warehouse.getContype() == 5){
			e.setValue(essayScore);
		}
		totalScore += e.getValue();
		l.add(e);
	}
	
	//在session内保存生成的试卷
	request.getSession().setAttribute("examlist1", l);
	//在session返回内保存总分
	request.getSession().setAttribute("totalScore", totalScore);	    	 
	return "selectDeptSub";
}
//提交生成的试卷（点击制定按钮）
else{ 
	String issueId="";
	try{
		String carray[]=companys.split(";");
		for(int i=0;i<carray.length;i++){
			ExamConfig ocf=new ExamConfig();
			ExamConfig tmp=(ExamConfig)request.getSession().getAttribute("onlineConfig");
			String typesel = tmp.getTypeSel().trim();
			ocf.setTitle(tmp.getTitle());
			ocf.setIssueUser(tmp.getIssueUser());
			ocf.setTypeSel(typesel);
			
			//typesel 格式1180101>1180201>1>20:10:20:10:10(增加简答题和论述题) 1180101>1180201>1>20:10:20(未增加简答题和论述题)
			String[] typeselArr = typesel.split(">");
			String count = typeselArr[3];
			String qaCount = ""; //简答题题数
			String essayCount = count.substring(count.lastIndexOf(":")+1,count.length()); //论述题题数
			if("".equals(essayCount)||"0".equals(essayCount)){ //如果没有设置论述题
				String temp = count.substring(0,count.lastIndexOf(":"));
				qaCount = temp.substring(temp.lastIndexOf(":")+1,temp.length());
				if("".equals(qaCount)||"0".equals(qaCount)){//如果没有设置简答题
					ocf.setMarkFlag(0); //无需阅卷（没有设置主观题）
				}else{
					ocf.setMarkFlag(1); //待阅卷
				}
			}else{
				ocf.setMarkFlag(1); //待阅卷
			}
			String singlecompany[]=carray[i].split(":");
			companyId=singlecompany[0];
			testerCount=Integer.valueOf(singlecompany[1]);
			ocf.setCompanyId(companyId);
			ocf.setTesterCount(testerCount);
			ocf.setStartTime(Timestamp.valueOf(starttime));
			ocf.setEndTime(Timestamp.valueOf(endtime));
			
			Integer totalScore = (Integer)request.getSession().getAttribute("totalScore");
			ocf.setMark(totalScore);
			issueId=(String) DAO.save(ocf);
			
			examlist1=(List)request.getSession().getAttribute("examlist1");
			Iterator iterator = examlist1.iterator();
			while (iterator.hasNext()) {
				ExamWarehouse warehouse = (ExamWarehouse) iterator.next();
				ExamIssue ei=new ExamIssue();
				BeanUtils.copyProperties(ei,warehouse);
				ei.setIssueid(issueId);
				DAO.save(ei);
			}
		}
	}catch (Exception e){
		e.printStackTrace();
	}
	return "success";
}
}
  public void printExamcfg(HttpServletResponse response,ExamConfig ocf,String msg) 
  {
	
	  StringBuffer sb=new StringBuffer();
	  if(msg.length()>0){  
		  sb.append(msg);
	  }
      List<ExamConfig> l=DAO.getHibernateTemplate().find("from ExamConfig e where e.title='"+ocf.getTitle()+"'");
      sb.append("<table width=\"200px\" border=\"1\">");
      
      for (int i = 0; i < l.size(); i++) 
      {
    	  TawSystemDeptDaoHibernate tsddh=(TawSystemDeptDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemDeptDao");
      	try {
			sb.append("<tr><td>").append(l.get(i).getTitle()).append("</td><td>").append(tsddh.id2Name(l.get(i).getCompanyId()))
			.append("</td><td>")
				.append(tsddh.id2Name(l.get(i).getDeptId())).append("</td><td>").append(l.get(i).getTesterCount()).append("</td><td>")
				.append("</td><td>").append(l.get(i).getStartTime()).append("</td><td>")
				.append("</td><td>").append(l.get(i).getEndTime()).append("</td><td>")
				.append("<input type=\"button\" value=\"删除\" onclick=\"del('").append(l.get(i).getIssueId())
				.append("')\" class=\"button\"></td></tr>");
		} catch (DictDAOException e) {
			e.printStackTrace();
		}
      	
		}
      sb.append("</table>"); 
      response.setCharacterEncoding("UTF-8");
		PrintWriter write;
		try {
			write = response.getWriter();
			write.write(sb.toString());
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
  }
  
/**
 * 将指定的参考人与已制定的考试题目相关联
 * @param issueId 发布试卷id
 * @param testers 参加考试人员名单（多人）
 * @param leaderid 相关部门参考人员的部门领导id（因是由网络部出题，指定人数及部门，由被指定部门领导来选定参考人）
 */
public int issueRalateUser(String issueId,String  testers){
	
	String[] users = testers.split(";");
	if(users.length < 0){
		return 0;
	}
	String hql="from ExamIssue ei where ei.issueid='"+issueId.trim()+"'";
	List examlist1=DAO.query(hql);
	try{
		for (int i = 0; i < users.length; i++) {
	      //  examlist = getIssueExamList(typeSel, session);//通过处理取得要发布的试卷题目
	        Iterator iterator = examlist1.iterator();
	        while (iterator.hasNext()) {
	          ExamIssue examIssue = (ExamIssue) iterator.next();
	          ExamContent examContent = new ExamContent();
	          examContent.setIssueId(issueId);
	          examContent.setSubId(examIssue.getSubId());
//	          examContent.setValue(examIssue.getValue());
	          examContent.setUserId(users[i]);
	          if(examIssue.getContype() != 4 && examIssue.getContype() != 5){//如果不是简答题或者论述题
	        	  //如果是非主观题打乱所有选项
	        	  examContent.setOptions(randomOptions(examIssue.getOptions()));
	          }
	          DAO.save(examContent);
	        }
	      }
		ExamConfig config = (ExamConfig)DAO.getHibernateTemplate().get(ExamConfig.class, issueId);
		config.setAssignment(true);
		config.setTester(testers);
		DAO.update(config);
 	    return 1;
	}catch (final Exception e){
        e.printStackTrace();
        return 0;
    }
}
  /**
   * 按难易度比例分类取出所有题目
   * @param typeSel String
   * @param Proportion String
   * @throws Exception
   * @return List
   */
@Deprecated
  public List getIssueExamList(String typeSel, ExamDAO DAO) throws
      Exception {
    List examlist = new ArrayList();
    String[] StrArray = typeSel.split(";");

    int num = 0;
    String tempType = "";
    String condition = "";

    //按类型和比例随机出题。
    for (int i = 0; i < StrArray.length; i++) {
      String[] Strtmp = StrArray[i].split(">");
      String[] propArray = Strtmp[3].split(":");
      for (int j = 0; j < propArray.length; j++) {
        num = Integer.parseInt(propArray[j]);
        tempType = Strtmp[0] + ">" + Strtmp[1]
            + ">" + Strtmp[2] + ">" + (j + 1);
        condition = getCondition(tempType);
        //取出一类专业、厂家、设备和难度的题目。
        List templist = DAO.getSubjectlist(condition, staticIssueExam);
        //控制题目数量
        if (templist.size() == 0) { //题数为0走failure页面
          return templist;
        }
        else {
          for (int k = 0; k < num; k++) {
            examlist.add(templist.get(SubjectRandom(templist.size())));
            templist.remove(SubjectRandom(templist.size()));
          }
        }
      }
    }
    return examlist;
  }

  /**
   * 按难易度比例分类取出所有题目
   * @param typeSel String
   * @param Proportion String
   * @throws Exception
   * @return List
   */
  public List getIssueExamList(String typeSel) {
    List examlist = new ArrayList();
//    String[] StrArray = typeSel.split(";");
    String[] StrArray = typeSel.split("\\|");
    int num = 0;
    String tempType = "";
    String condition = "";

    //按类型和比例随机出题。
    for (int i = 0; i < StrArray.length; i++) {
      String[] Strtmp = StrArray[i].split(">");//StrArray[i]的格式1180101>1180201>1>20:10:20:10,最后一个>号之前的格式是：专业，厂家，难易度
      String[] propArray = Strtmp[3].split(":");//Strtmp[3]中的数据格式：单选，多选，判断，简答，论述（的数量）
      for (int j = 0; j < propArray.length; j++){//按难度级别从难到易，依次取出所有级别的题，每次取得相应级别的所有题后，
    	                                          //在之后的num循环中用Random随机抽取题
    	if(!StringUtils.isEmpty(propArray[j])){
    		num = Integer.parseInt(propArray[j]);
    	}else{
    		num = 0;
    	}
        if(num>0)//即要抽的该类型的题数大于0，才去查数据库
        {
        tempType = Strtmp[0] + ">" + Strtmp[1]+ ">" + Strtmp[2] + ">" + (j + 1); //j+1 为contype
        condition = getCondition(tempType);
        //取出一类专业、厂家、设备和难度的题目。
        List templist=DAO.getHibernateTemplate().find("from ExamWarehouse examWarehouse where examWarehouse.issueType="
                + staticIssueExam + " and examWarehouse.deleted=0" + condition);
      //  List templist = DAO.getSubjectlist(condition, staticIssueExam);
        //控制题目数量
        if (templist.size() == 0) { //题数为0走failure页面
        	continue;
          //return templist;
        }
        else {
          for (int k = 0; k < num; k++) {
        	  if(templist.size()==0)break;
        	  int randomItem=SubjectRandom(templist.size());
            examlist.add(templist.get(randomItem));//从当前题库中随机抽一题，加入到试卷题列表中
            templist.remove(randomItem);//从当前题库中移除被抽中的题，以避免下次被抽中该题
          }
        }
        }
      }
    }
    return examlist;
  }
  public List getIssueExamList1(String typeSel) {
	  List examlist = new ArrayList();
	  String[] StrArray = typeSel.split(";");
	  int num = 0;
	  String tempType = "";
	  String condition = "";
	  
	  //按类型和比例随机出题。
	  for (int i = 0; i < StrArray.length; i++) {
		  String[] Strtmp = StrArray[i].split(">");//StrArray[i]的格式1180101>1180201>1>20:10:20:10,最后一个>号之前的格式是：专业，厂家，难易度
		  String[] propArray = Strtmp[3].split(":");//Strtmp[3]中的数据格式：单选，多选，判断，简答，论述（的数量）
		  for (int j = 0; j < propArray.length; j++){//按难度级别从难到易，依次取出所有级别的题，每次取得相应级别的所有题后，
			  //在之后的num循环中用Random随机抽取题
			  if(!StringUtils.isEmpty(propArray[j])){
				  num = Integer.parseInt(propArray[j]);
			  }else{
				  num = 0;
			  }
			  if(num>0)//即要抽的该类型的题数大于0，才去查数据库
			  {
				  tempType = Strtmp[0] + ">" + Strtmp[1]+ ">" + Strtmp[2] + ">" + (j + 1); //j+1 为contype
				  condition = getCondition(tempType);
				  //取出一类专业、厂家、设备和难度的题目。
				  List templist=DAO.getHibernateTemplate().find("from ExamWarehouse examWarehouse where examWarehouse.issueType="
						  + staticIssueExam + " and examWarehouse.deleted=0" + condition);
				  //  List templist = DAO.getSubjectlist(condition, staticIssueExam);
				  //控制题目数量
				  if (templist.size() == 0) { //题数为0走failure页面
					  continue;
					  //return templist;
				  }
				  else {
					  for (int k = 0; k < num; k++) {
						  if(templist.size()==0)break;
						  int randomItem=SubjectRandom(templist.size());
						  examlist.add(templist.get(randomItem));//从当前题库中随机抽一题，加入到试卷题列表中
						  templist.remove(randomItem);//从当前题库中移除被抽中的题，以避免下次被抽中该题
					  }
				  }
			  }
		  }
	  }
	  return examlist;
  }
  
  /**
   * 翻页并获取试题
   * @param userId 
   * @param issueId
   * @param pagePra
   * @return
   * @throws Exception
   */
  public List getExamsubs(String userId, String issueId, int[] pagePra) throws Exception {
	  return DAO.getExamsubs(userId, issueId, pagePra);
  }

  /**
   * 将提交答案、得分和对错情况入库
   */
  public void submitExam(List answerlist, String issueId, String userId) throws Exception {
    Iterator iterator = answerlist.iterator();
    while (iterator.hasNext()) {
      ExamWarehouse answer =(ExamWarehouse) iterator.next();
      
      ExamContent content = null;
      List list = DAO.query("from ExamContent examContent where examContent.issueId='"
              + issueId + "' and examContent.subId='" + answer.getSubId() +
              "' and examContent.userId='" + userId + "'");
      if(list != null && list.size()>0){
    	  content = (ExamContent)list.get(0);
      }
      
      if (content!=null) {
        content.setAnswer(answer.getResult());
        content.setMark(checkAnswer(answer));
        //答对Right=2 ， 半对Right=1 ， 不对Right=0
        
        content.setRight(content.getMark() > 1 ? 2 : content.getMark());
        DAO.update(content);
      }
    }
  }
  
  /**
   * 保存问答题答案
   */
  public void saveQaExam(String userId,String issueId,String subId,String answer){
	  ExamContent content = null;
	  List list = DAO.query("from ExamContent examContent where examContent.issueId='"
              + issueId + "' and examContent.subId='" + subId +
              "' and examContent.userId='" + userId + "'");
	  if(list != null && list.size()>0){
    	  content = (ExamContent)list.get(0);
      }
	  if (content!=null) {
          content.setOptions(answer);
          DAO.update(content);
      }
  }

  /**
   * 将该页题目的已选答案以 subId + 选项 的形式连接成String。
   * 如 8a4282900a01e37a010a02137ead0005A;8a4282900a01e37a010a02137ead0005C
   * @param sublist List
   * @param issueId String
   * @param userId String
   * @throws Exception
   * @return String
   */
  public Map getAnswerOfExam(List sublist, String issueId, String userId) throws
      Exception {
	  Map map = new HashMap();
      String Sel = "";
      Iterator iterator = sublist.iterator();
      while (iterator.hasNext()) {
    	  ExamIssue issue = new ExamIssue();
    	  issue = (ExamIssue) iterator.next();
    	  String subId = issue.getSubId();
    	  List tmplist = DAO.query(
	          "from ExamContent examContent where examContent.issueId='"
	          + issueId + "' and examContent.subId='" + issue.getSubId() +
	          "' and examContent.userId='" + userId + "'");
	      if (tmplist.size() != 0) {
	        ExamContent content = (ExamContent) tmplist.get(0);
	        if(4!= issue.getContype() && 5!= issue.getContype()){
	        	String[] strArray = content.getAnswer().trim().split(";");
		        if (!"".equalsIgnoreCase(strArray[0])) {
		          for (int i = 0; i < strArray.length; i++) {
		            Sel += ";" + subId + strArray[i];
		          }
		        }
		        map.put("Sel", Sel.length() > 0 ? Sel.substring(1) : Sel); //非问答题
	        }else{
	        	map.put("qa_"+subId, content.getOptions()); //问答题
	        }
	      }
      }
      return map;
  }
  
  /**
   * 随机打乱选项的顺序。并在原有选项前加上逻辑顺序.如"A|"
   * 格式如:A|B.选项1;B|D.选项2
   * @param options String
   * @throws Exception
   * @return String
   */
  public String randomOptions(String options)   {
    String newOptions = "";
    String[] strArray = options.trim().split(";");
    List tmplist = new ArrayList();
    char tag;
    int random;
    String option;

    for (int i = 0; i < strArray.length; i++) {
      tmplist.add(strArray[i]);
    }
    for (int j = 0; j < strArray.length; j++) {
      tag = (char) ( (byte) 'A' + j);
      random = SubjectRandom(tmplist.size());
      option = String.valueOf(tmplist.get(random));
      newOptions += ";" + String.valueOf(tag) + "|" + option;
      tmplist.remove(random);
    }
    return newOptions.substring(1);
  }

  /**
   * 保存标记   将题号保存到tag字段中
   * @param tags String   格式为 subId + 值 (值:0-未标记;1-已标记)
   * @param issueId String
   * @param userId String
   * @throws Exception
   */
  public void saveTag(String tags, String issueId, String userId) throws
      Exception {
    String[] tagArray = tags.split(";");
    for (int i = 0; i < tagArray.length && !"".equalsIgnoreCase(tagArray[0]); i++) {
      String subId = tagArray[i].substring(0, 32);
      int value = Integer.parseInt(tagArray[i].substring(32));
      List tmplist = DAO.query(
          "from ExamContent examContent where examContent.issueId='"
          + issueId + "' and examContent.subId='" + subId + "' and "
          + "examContent.userId='" + userId + "'");
      if (tmplist.size() != 0) {
        ExamContent content = (ExamContent) tmplist.get(0);
        content.setTag(value);
        DAO.update(content);
      }
    }
    
  }

  /**
   * 将该页题目的已选标已 (题号):页号 的形式连接成String。
   * 如 (1):0;(16):15
   * @param sublist List
   * @param issueId String
   * @param userId String
   * @throws Exception
   * @return String
   */
  public String getTagsOfExam(String issueId, String userId, int length) throws
      Exception {
    String tags = "";
    List contentlist = DAO.query(
        "from ExamContent examContent where examContent.issueId='"
        + issueId + "' and examContent.userId='" + userId + "'");

    Iterator iterator = contentlist.iterator();
    while (iterator.hasNext()) {
      ExamContent content = new ExamContent();
      content = (ExamContent) iterator.next();
      if (content.getTag() != 0) {
        int offset =
            content.getTag() % length == 0 ? content.getTag() :
            content.getTag() - content.getTag() % length;
        tags += ";" + "(" + content.getTag() + "):" + offset;
      }
    }
    return tags.length() > 0 ? tags.substring(1) : tags;
  }

  /**
   * 查询优先级(高到低):按用户ID查询 , 按用户名查询 , 按部门查询。
   * 只满足其中的一个条件成立。
   * @param form Map
   * @throws Exception
   * @return List
   */
  public List getStudyQuerylist(HttpServletRequest request) throws Exception {
    String userId = StaticMethod.nullObject2String(request.getParameter("userId"));
    String deptId = StaticMethod.nullObject2String(request.getParameter("deptid"));
    ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
    String condition = "";
    List QOlist = new ArrayList();
    if (!"".equalsIgnoreCase(userId)) {
      condition = " and content.userId='" + userId + "'";
    }
    else if (!"".equalsIgnoreCase(deptId)) {
      List userlist = SystemResource.getUserDao().getUserBydeptids(deptId);
      Iterator iterator = userlist.iterator();
      while (iterator.hasNext()) {
        userId += ",'" +
            ( (TawSystemUser) iterator.next()).getUserid() +
            "'";
      }
      condition = " and content.userId in(" + userId.substring(1) + ")";
    }

    if (!"".equalsIgnoreCase(condition)) {
      List querylist = null;
      condition = "content.issueId='study' " + condition;
      querylist = DAO.getStudyQOlist(condition);
      //遍历list逐个累加用户信息存入QO中
      Iterator iterator = querylist.iterator();
      while (iterator.hasNext()) {
        studyQO qo = new studyQO();
        Object[] tmpObj = (Object[]) iterator.next();
        qo.setMark( ( (Integer) tmpObj[0]).intValue());
        qo.setRight( ( (Integer) tmpObj[1]).intValue() / 2);
        qo.setTotal( ( (Integer) tmpObj[2]).intValue());
        qo.setUserId(tmpObj[3].toString());
        qo.setRate(Math.round( (float) qo.getRight() / qo.getTotal() * 10000) /
                   100.0);
        qo.setUserName(service.id2Name(qo.getUserId(), "tawSystemUserDao"));
//        qo.setUserName(SystemResource.getUserNameById(qo.getUserId()));
        qo.setIssueId("study");
        QOlist.add(qo);
      }
    }

    return QOlist;
  }
  
  public List getStudyQuerySelfList(String userId) throws Exception {
	    String condition = "";
	    ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
	    List QOlist = new ArrayList();
	    //ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
	    if (!"".equalsIgnoreCase(userId)) {
	      condition = " and content.userId='" + userId + "'";
	    }
	    if (!"".equalsIgnoreCase(condition)) {
	      List querylist = null;
	      condition = "content.issueId='study' " + condition;
	      querylist = DAO.getStudyQOlist(condition);
	      //遍历list逐个累加用户信息存入QO中
	      Iterator iterator = querylist.iterator();
	      while (iterator.hasNext()) {
	        studyQO qo = new studyQO();
	        Object[] tmpObj = (Object[]) iterator.next();
	        qo.setMark( ( (Integer) tmpObj[0]).intValue());
	        qo.setRight( ( (Integer) tmpObj[1]).intValue() / 2);
	        qo.setTotal( ( (Integer) tmpObj[2]).intValue());
	        qo.setUserId(tmpObj[3].toString());
	        qo.setRate(Math.round( (float) qo.getRight() / qo.getTotal() * 10000) /
	                   100.0);
	        qo.setUserName(service.id2Name(qo.getUserId(), "tawSystemUserDao"));
//	        qo.setUserName(SystemResource.getUserNameById(qo.getUserId()));
	        qo.setIssueId("study");
	        QOlist.add(qo);
	      }
	    }

	    return QOlist;
	  }

  public List getDetailOfStudy(HttpServletRequest request) throws Exception {
    String userId = StaticMethod.nullObject2String(request.getParameter("userId"));
    String issueId = StaticMethod.nullObject2String(request.getParameter("issueId"));
    List detaillist = new ArrayList();
    //ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");

    List contentlist = DAO.getUserContent(userId, issueId);
    Iterator iterator = contentlist.iterator();
    if(issueId!=null&&issueId.equals("study"))
    {
    	 while (iterator.hasNext()) {
    	 ExamContent content = (ExamContent) iterator.next();
    	 ExamWarehouse warehouse =
             (ExamWarehouse) DAO.load(ExamWarehouse.class,content.getSubId());
         detailQO qo = new detailQO();
         qo.setTitle(warehouse.getTitle());
         qo.setOptions(warehouse.getOptions());
         qo.setAnswer(content.getAnswer());
         qo.setRight(content.getRight());
         qo.setImage(warehouse.getImage());
         if (content.getSubmitTime() != null) {
           qo.setSubmitTime(StaticMethod.getTimestampString(content.getSubmitTime()));
         }
         detaillist.add(qo);
       }
    }else{
    while (iterator.hasNext()) {
      ExamContent content = (ExamContent) iterator.next();
      ExamIssue issue =
          (ExamIssue) DAO.load(ExamIssue.class,content.getSubId());
      detailQO qo = new detailQO();
      qo.setTitle(issue.getTitle());
//      qo.setOptions(issue.getOptions());
      qo.setOptions(content.getOptions());
      qo.setAnswer(content.getAnswer());
      qo.setRight(content.getRight());
      qo.setImage(issue.getImage());
      qo.setContype(issue.getContype());
      qo.setMark(content.getMark());
      if(issue.getContype() == 4 || issue.getContype() == 5){
    	  qo.setQa(content.getOptions());
      }
      if (content.getSubmitTime() != null) {
        qo.setSubmitTime(StaticMethod.getTimestampString(content.getSubmitTime()));
      }
      detaillist.add(qo);
    }
    }
    return detaillist;
  }

  /**
   * 取得考试列表放到下拉选择框中
 * @param timeLimit
 * @param QueryType
 * @return
 * @throws Exception
 */
public List getConfigLab(String timeLimit, int QueryType) throws Exception {
    List tmplist = null;
    if (QueryType == staticQueryTypeEndtime) {
      tmplist = DAO.getConfiglist(timeLimit);
    }
    else if (QueryType == staticQueryTypeExamUnfinished) {
      tmplist = DAO.getConfiglistExamUnfinished(timeLimit);
    }
    else {
      tmplist = DAO.getConfiglistBetweenOfIssue(timeLimit);
    }
    List configLablist = new ArrayList();
    Iterator iterator = tmplist.iterator();

    while (iterator.hasNext()) {
      ExamConfig config = new ExamConfig();
      config = (ExamConfig) iterator.next();
      String title = config.getTitle();
      if (title.length() > 20) {
        title = title.substring(0, 20) + "...";
      }
      String typesel[]=config.getTypeSel().split(">");
      String label = "(" + StaticMethod.getTimestampString(config.getStartTime()) +
          "-"
          + StaticMethod.getTimestampString(config.getEndTime()) + ")"
          + title+TawSystemDictBo.getDictNameByDictid(typesel[0]);
      configLablist.add(new org.apache.struts.util.LabelValueBean(
          label, config.getIssueId()));
    }

    return configLablist;
  }
public List getConfigList(String timeLimit, int QueryType) throws Exception {
    //ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    List tmplist = null;
    if (QueryType == staticQueryTypeEndtime) {
      tmplist = DAO.getConfiglist(timeLimit);
    }
    else if (QueryType == staticQueryTypeExamUnfinished) {
      tmplist = DAO.getConfiglistExamUnfinished(timeLimit);
    }
    else {
      tmplist = DAO.getConfiglistBetweenOfIssue(timeLimit);
    }
    return tmplist;
  }

  /**
   * 成绩查询  把制定的多个单位的同一次考试（在examconfig表是多条记录）整合成一条记录显示(省级、网络部)
   * @param timeLimit
   * @param pageSize
   * @param pageIndex
   * @return
   */
  public PageModel getAllConfigList(String timeLimit,String deptStr,int pageSize,int pageIndex, String tester, boolean isNet ){
	  
	  //分组查询
	  String sql = null;

	    if (!(isNet))
	      sql = "select c.title,c.start_Time,c.end_Time,cast(c.type_Sel as varchar(100)) from ExamConfig c,Taw_System_User u where c.end_Time<='" + 
	        timeLimit + "' and (c.markFlag=0 or c.markFlag=2 or c.markFlag is null) " + 
	        " and c.issue_user=u.userid and u.deptid in (" + deptStr + ") or (c.tester like '%;" + tester + "' " + 
	        " or  c.tester like '" + tester + ";%'   or c.tester like '%;" + tester + ";%'" + 
	        "or  c.tester like '" + tester + "') " + 
	        "group by c.title,c.start_Time,c.end_Time,c.type_Sel order by c.start_Time desc";
	    else {
	      sql = "select c.title,c.start_Time,c.end_Time,cast(c.type_Sel as varchar(100)) from ExamConfig c,Taw_System_User u where c.end_Time<='" + 
	        timeLimit + "' and (c.markFlag=0 or c.markFlag=2 or c.markFlag is null) " + 
	        " and c.issue_user=u.userid and u.deptid in (" + deptStr + ") " + 
	        "group by c.title,c.start_Time,c.end_Time,c.type_Sel order by c.start_Time desc";
	    }
	  PageModel pm = DAO.searchSqlPaginated(sql, null, pageIndex, pageSize);
	  
	  List datas = pm.getDatas();
	  List showDatas = new ArrayList();
	  for(int i=0;i<datas.size();i++){
		  Object obj[] = (Object[])datas.get(i);
		  ExamConfig config = new ExamConfig();
		  config.setTitle(obj[0].toString());
		  config.setStartTime((Timestamp)obj[1]);
		  config.setEndTime((Timestamp)obj[2]);
		  StringBuffer companyIdBf = new StringBuffer();
		  StringBuffer issueIdBf = new StringBuffer();
		  
		  //子查询 属于同一次考试的
		  String subHql = "from ExamConfig where title=? and startTime=? and endTime=? and typeSel=? " +
		  		"and (markFlag=0 or markFlag=2 or markFlag is null) ";
		  List l = DAO.getHibernateTemplate().find(subHql, obj);
		  //组装用逗号分隔的部门名称和IssueId
		  for(int j=0;j<l.size();j++){
			  ExamConfig ec = (ExamConfig)l.get(j);
			  if(j == l.size()-1){
				  companyIdBf.append(ExamUtil.examCompanyMap.get(ec.getCompanyId()));
				  issueIdBf.append(ec.getIssueId());
			  }else{
				  companyIdBf.append(ExamUtil.examCompanyMap.get(ec.getCompanyId())).append(",");
				  issueIdBf.append(ec.getIssueId()).append(",");
			  }
		  }
		  config.setCompanyId(companyIdBf.toString());
		  config.setIssueId(issueIdBf.toString());
		  showDatas.add(config);
	  }
	  pm.setDatas(showDatas);
	  return pm;
  }
  
  /**
   * 成绩查询  把制定的多个单位的同一次考试（在examconfig表是多条记录）整合成一条记录显示（地市）
   * @param timeLimit
   * @param isCityFlag
   * @param deptStr
   * @param pageSize
   * @param pageIndex
   * @return
   */
  public PageModel getAllConfigListCity(String timeLimit,boolean isCityFlag,
		  	String deptStr,int pageSize,int pageIndex , String tester){
	  //分组查询
	  String sql = "select c.title,c.start_Time,c.end_Time,cast(c.type_Sel as varchar(100)) from ExamConfig c,Taw_System_User u " +
		"where c.end_Time<='"+timeLimit+"' and (c.markFlag=0 or c.markFlag=2 or c.markFlag is null) ";
	  
	  //如果是地市的用户，那么只能看本地市
		if(isCityFlag){
			sql = sql + "and  c.issue_user=u.userid and u.deptid = '" + deptStr + "' or (c.tester like '%;" + tester + "' or  c.tester like '" + tester + ";%'   or c.tester like '%;" + tester + ";%'" + 
	        "or  c.tester like '" + tester + "') ";
		}else{//不是地市的（那么就是比地市级别更高的网络部或者省级的，那么可以看所有地市的）
			sql= sql + " and c.issue_user=u.userid and u.deptid not in (" + deptStr + ")";
		}
	   sql = sql + " group by c.title,c.start_Time,c.end_Time,c.type_Sel order by c.start_Time desc";
	  
	  PageModel pm = DAO.searchSqlPaginated(sql, null, pageIndex, pageSize);
	  
	  List datas = pm.getDatas();
	  List showDatas = new ArrayList();
	  for(int i=0;i<datas.size();i++){
		  Object obj[] = (Object[])datas.get(i);
		  ExamConfig config = new ExamConfig();
		  config.setTitle(obj[0].toString());
		  config.setStartTime((Timestamp)obj[1]);
		  config.setEndTime((Timestamp)obj[2]);
		  StringBuffer companyIdBf = new StringBuffer();
		  StringBuffer issueIdBf = new StringBuffer();
		  
		  //子查询 属于同一次考试的
		  String subHql = "from ExamConfig where title=? and startTime=? and endTime=? and typeSel=? " +
		  		"and (markFlag=0 or markFlag=2 or markFlag is null) ";
		  List l = DAO.getHibernateTemplate().find(subHql, obj);
		  //组装用逗号分隔的部门名称和IssueId
		  for(int j=0;j<l.size();j++){
			  ExamConfig ec = (ExamConfig)l.get(j);
			  if(j == l.size()-1){
				  companyIdBf.append(ExamUtil.examCompanyMap.get(ec.getCompanyId()));
				  issueIdBf.append(ec.getIssueId());
			  }else{
				  companyIdBf.append(ExamUtil.examCompanyMap.get(ec.getCompanyId())).append(",");
				  issueIdBf.append(ec.getIssueId()).append(",");
			  }
		  }
		  config.setCompanyId(companyIdBf.toString());
		  config.setIssueId(issueIdBf.toString());
		  showDatas.add(config);
	  }
	  pm.setDatas(showDatas);
	  return pm;
  }
	
  

  public List getExamQueryList(HttpServletRequest request) throws Exception {

	  String issueId = StaticMethod.nullObject2String(request.getParameter("issueId"));
    List QOlist = new ArrayList();
    TawSystemUserDaoHibernate tsudh=(TawSystemUserDaoHibernate)ApplicationContextHolder.getInstance().getBean("tawSystemUserDao");
   
    List querylist = null;
    String condition = "content.issueId='" + issueId + "'";
    querylist = DAO.getStudyQOlist(condition);
    //遍历list逐个累加用户信息存入QO中
    Iterator iterator = querylist.iterator();
    while (iterator.hasNext()) {
      studyQO qo = new studyQO();
      Object[] tmpObj = (Object[]) iterator.next();
      qo.setMark( ( (Integer) tmpObj[0]).intValue());
      qo.setRight( ( (Integer) tmpObj[1]).intValue() / 2);
      qo.setTotal( ( (Integer) tmpObj[2]).intValue());
      qo.setUserId(tmpObj[3].toString());
      qo.setRate(Math.round( (float) qo.getRight() / qo.getTotal() * 10000) /
                 100.0);
      qo.setUserName( tsudh.getUserByuserid(qo.getUserId()).getUsername());
      qo.setIssueId(issueId);
      QOlist.add(qo);
    }

    return QOlist;

  }

  public boolean ifFinish(String issueId, String userId) throws Exception {
    List userSubmit =
        DAO.query("from ExamSubmit submit where submit.userId ='" + userId +
                  "' and submit.issueId = '"+ issueId +"'");
    if (userSubmit.size() != 0) {
      return true;
    }
    return false;
  }

  public void examFinish(String issueId, String userId) throws Exception {
    ExamSubmit submit = new ExamSubmit();
    submit.setIssueId(issueId);
    submit.setUserId(userId);
    submit.setSubmitTime(StaticMethod.getTimestamp());
    DAO.save(submit);
  }

  public List getInfolistOfcondition(String condition) throws Exception {
    List infolist = DAO.getInfoList(condition);

    Iterator iterator = infolist.iterator();
    while (iterator.hasNext()) {
      ExamInfo onlineInfo = new ExamInfo();
      onlineInfo = (ExamInfo) iterator.next();
      onlineInfo.setSpecialtyName(getDictNameByStringDictId("SYSTEM.DICTTYPE.Specialty", onlineInfo.getSpecialty()));
      onlineInfo.setImportUser(onlineInfo.getImportUser());
      onlineInfo.setStatusName(getAuditingStatus(onlineInfo.getAuditing()));
      onlineInfo.setComment(onlineInfo.getComment());
    }
    return infolist;
  }
  
  /**
   * 查询试题历史导入记录
   * @param currentUserName
   * @param pageIndex
   * @param pageSize
   * @return
   */
  public PageModel findHistoryImport(String currentUserName,int pageIndex,int pageSize){
	  String hql = "from ExamInfo onlineInfo where onlineInfo.deleted=0 " +
	  		"and onlineInfo.auditing in (0,3) and onlineInfo.importUser=? order by importTime desc";
	  return DAO.searchPaginated(hql, new Object[]{currentUserName}, pageIndex, pageSize);
  }

  public void reportSubmit(String checkSel, String specialty) throws Exception {
    List infolist = DAO.query(
        "from ExamInfo onlineInfo where onlineInfo.importId in(" + checkSel +
        ")");
    Iterator iterator = infolist.iterator();

    while (iterator.hasNext()) {
      ExamInfo onlineInfo = new ExamInfo();
      onlineInfo = (ExamInfo) iterator.next();
      onlineInfo.setAuditing(staticReport);
      onlineInfo.setSpecialty(specialty);
      DAO.update(onlineInfo);
    }
  }

  public void delImport(String checkSel) throws Exception {
    DAO.delExamInfo(checkSel);
    DAO.delExamWarehouseOfBatch(checkSel);
  }

  public void reject(String checkSel, String comment) throws Exception {
    List infolist = DAO.query(
        "from ExamInfo onlineInfo where onlineInfo.importId in(" + checkSel +
        ")");
    
    Iterator iterator = infolist.iterator();
    while (iterator.hasNext()) {
      ExamInfo onlineInfo = new ExamInfo();
      onlineInfo = (ExamInfo) iterator.next();
      onlineInfo.setAuditing(staticReject);
      onlineInfo.setComment(comment);
      DAO.update(onlineInfo);
    }
  }

  public void auditingDO(String checkSel) throws Exception {
    List infolist = DAO.query(
        "from ExamInfo onlineInfo where onlineInfo.importId in(" + checkSel +
        ")");
    Iterator iterator = infolist.iterator();
    while (iterator.hasNext()) {
      ExamInfo onlineInfo = new ExamInfo();
      onlineInfo = (ExamInfo) iterator.next();
      onlineInfo.setAuditing(staticAuditingPass);
      DAO.update(onlineInfo);
    }
    //将试题发布
    List warehouselist = DAO.query(
        "from ExamWarehouse warehouse where warehouse.importId in(" +
        checkSel + ")");
    Iterator iteratorWare = warehouselist.iterator();
    while (iteratorWare.hasNext()) {
      ExamWarehouse warehouse = new ExamWarehouse();
      warehouse = (ExamWarehouse) iteratorWare.next();
      warehouse.setDeleted(0);
      DAO.update(warehouse);
    }
  }

  public List getAllOnlineWarehouse(String importId) throws Exception {
    List onlineWarehouselist = null;

    onlineWarehouselist = DAO.query(
        "from ExamWarehouse examWarehouse where examWarehouse.importId='" +
        importId + "'");

    Iterator iterator = onlineWarehouselist.iterator();
    while (iterator.hasNext()) {
      ExamWarehouse 
      onlineWarehouse = (ExamWarehouse) iterator.next();
      //if(iterator.)
      onlineWarehouse.setTitle(onlineWarehouse.getTitle());
      onlineWarehouse.setOptions(onlineWarehouse.
          getOptions());
      onlineWarehouse.setComment(onlineWarehouse.
          getComment());
      onlineWarehouse.setDifficultyName(
          StaticMethod.null2String(this.getDifficultyName(onlineWarehouse.
          getDifficulty())));
      onlineWarehouse.setSpecialtyName(
          StaticMethod.null2String(getDictNameByStringDictId("SYSTEM.DICTTYPE.Specialty",
                                               onlineWarehouse.getSpecialty())));
      
      onlineWarehouse.setManufacturerName(
          StaticMethod.null2String(getDictName("SYSTEM.DICTTYPE.Manufacturer",
                                               onlineWarehouse.getManufacturer())));
    }

    return onlineWarehouselist;
  }

  public void updateIssueTime(HttpServletRequest request) throws Exception {
    String issueId = StaticMethod.nullObject2String(request.getParameter("configSel"));
    String issueStarttime = StaticMethod.nullObject2String(request.getParameter(
        "issueStarttime"));
    String issueEndtime = StaticMethod.nullObject2String(request.getParameter("issueEndtime"));
    //ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    List infolist = DAO.query(
        "from ExamConfig config where config.issueId in('" + issueId + "')");
    Iterator iterator = infolist.iterator();

   
    while (iterator.hasNext()) {
      ExamConfig config = new ExamConfig();
      config = (ExamConfig) iterator.next();
      config.setIssueStarttime(StaticMethod.getTimestamp(issueStarttime));
      config.setIssueEndtime(StaticMethod.getTimestamp(issueEndtime));
      DAO.update(config);
    }	
  }

  public void saveDefault(String userId, Map map) throws Exception {
	  String typeSel = StaticMethod.nullObject2String(map.get("typeSel"));
	  String typeSelName = StaticMethod.nullObject2String(map.get("typeSelName"));
	  DAO.delete("from ExamStudyConfig studyConfig where studyConfig.userId ='"+userId+"'");
      ExamStudyConfig studyConfig = new ExamStudyConfig();
      studyConfig.setSelName(typeSelName);
      studyConfig.setSelValue(typeSel);
      studyConfig.setUserId(userId);
      DAO.save(studyConfig);
  }

  /**
   * 通过userId取得默认类型标签
   *
   * @param userId String
   * @throws Exception
   * @return List
   */
  public List getStudyConfigLabel(String userId) throws Exception{
    //ExamDAO DAO =(ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
    List configLabel = new ArrayList();
    List configList = null;
    configList = DAO.query("from ExamStudyConfig studyConfig where studyConfig.userId ='"+userId+"'");
    if (configList.size() != 0){
      ExamStudyConfig studyConfig = new ExamStudyConfig();
      studyConfig = (ExamStudyConfig) configList.get(0);
      String[] selValue = studyConfig.getSelValue().split(";");
      String[] selName = studyConfig.getSelName().split(";");
      for (int i = 0; i < selValue.length; i++) {
        configLabel.add(new org.apache.struts.util.LabelValueBean(
            StaticMethod.getGBString(selName[i]), selValue[i]));
      }
    }
    return configLabel;
  }

  public String getDictName(String DictType, int DictId) throws Exception {
    int dictType = StaticMethod.null2int(StaticMethod.getNodeName(DictType));
    String name ="";
    try{
    	name=StaticMethod.null2String(
    		TawSystemDictBo.getDictNameByDictid(String.valueOf(DictId)));
    }
    finally{
    	 return name;
    }
    
  }
  public String getDictNameByStringDictId(String DictType, String DictId) throws Exception {
	  int dictType = StaticMethod.null2int(StaticMethod.getNodeName(DictType));
	  String name ="";
	  try{
		  name=StaticMethod.null2String(
				  TawSystemDictBo.getDictNameByDictid(String.valueOf(DictId)));
	  }
	  finally{
		  return name;
	  }
	  
  }

  public String getAuditingStatus(int auditing) {
    switch (auditing) {
      case 0:
        return "未报批";
      case 1:
        return "待审核";
      case 2:
        return "已审核";
      case 3:
        return "被驳回";
      default:
        return "";
    }
  }

  public String getDifficultyName(int difficulty) {
    switch (difficulty) {
      case 1:
        return "初级";
      case 2:
        return "中级";
      case 3:
        return "高级";
      case 4:
        return "专家";
      default:
        return "";
    }
  }

  public List getRankList() throws Exception {

    List rank = new ArrayList();
    
    rank.add(new org.apache.struts.util.LabelValueBean(
        "中级", "2"));
    
    rank.add(new org.apache.struts.util.LabelValueBean(
        "初级", "1"));

    return rank;
  }

  public List getIssueTypeList() throws Exception {

    List issueTypelist = new ArrayList();
    issueTypelist.add(new org.apache.struts.util.LabelValueBean(
        "学习", "1"));
    issueTypelist.add(new org.apache.struts.util.LabelValueBean(
        "考试", "2"));

    return issueTypelist;
  }

  public String getPager(int offset, int pagesize,  int count,String url) throws Exception {
    String pref1 = "&";
    String pref;
    int MAX_PAGE_INDEX = 200;

    String param = "page.size" + count;

    if (url.indexOf("?") > -1) {
      pref = "&";
    } else {
      pref = "?";
    }

   int pageNum = 0;
   int pageNo = offset / pagesize + 1;

   if (count % pagesize == 0) {
     pageNum = count / pagesize;
   }
   else {
     pageNum = count / pagesize + 1;
   }

    StringBuffer header = new StringBuffer();
    header.append("共&nbsp;");
    header.append(count + "&nbsp;");
    header.append("题&nbsp;");
    header.append(String.valueOf(pageNum) + "&nbsp;");
    header.append("页记录 当前在第&nbsp;");
    header.append("<select name=\"page\" onchange=\"goPage("+count+",this.value);\">");

    for (int i=1;i<=pageNum;i++)
    {
      header.append("<option value=\'" + (i-1) * pagesize + "\'");
      if (pageNo==i)
        header.append(" selected");
      header.append(">" + i + "</option>");
    }

    header.append("</select>页&nbsp;&nbsp;");

    if (offset > 0) {
      header.append("&nbsp;<input type='button' class='btn' value='上一页' onclick=\"goPage("+count+","+(offset-pagesize)+")\">");
    }
    int start;
    int radius = MAX_PAGE_INDEX/2*pagesize;
    if (offset < radius) {
      start = 0;
    } else if(offset < count-radius) {
      start = offset - radius;
    } else {
      start = (count/pagesize-MAX_PAGE_INDEX)*pagesize;
    }
    if(offset < count - pagesize) {
      header.append("&nbsp;<input type='button'class='btn' value='下一页' onclick=\"goPage("+count+","+((int)offset+(int)pagesize)+")\">");
    }
    return header.toString();
  }
  
	public TawSystemDeptExam getDeptinfobydeptid(String deptid, String delid) {

		TawSystemDeptExam sysdept = new TawSystemDeptExam();
		sysdept = DAO.getDeptinfobydeptid(deptid, delid);
		if (null == sysdept) {
			return new TawSystemDeptExam();
		}
		return sysdept;
	}
	
	/**
	 * 查询试题添加列表
	 * @param spec    专业
	 * @param manu    厂家
	 * @param diff    难度
	 * @param contype 题型
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel findExamConfigAdd(String spec,String manu,String diff,String contype,int pageIndex,int pageSize){
		String hql = "select a from ExamWarehouse a,ExamInfo b where a.importId=b.importId and a.issueType=2 and a.deleted=0 " +
		"and a.specialty=? and a.manufacturer=? and a.difficulty=? and a.contype=? " +
		"order by b.importTime desc";
			Object[] params = {spec,Integer.parseInt(manu),Integer.parseInt(diff),Integer.parseInt(contype)};
			PageModel pm = DAO.searchPaginated(hql, params, pageIndex, pageSize);
		return pm;
	}
	
	/**
	 * 查询试题库题目数量
	 * @param spec 专业
	 * @param manu 厂家
	 * @param diff 难度
	 * @param contype 题型
	 * @return
	 */
	public int findExamWarehouseCount(String spec,String manu,String diff,String contype){
		return DAO.findExamWarehouseCount(spec, manu, diff, contype);
	}
	
	/**
	 * 查询问答题打分的列表
	 * @param issueId
	 * @return
	 */
	public List findQaMarkList(String issueId){
		ExamConfig config = (ExamConfig)DAO.load(ExamConfig.class,issueId);
		ID2NameService service = (ID2NameService)ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String hql = "select  con.userId,sum(con.mark)as score from ExamContent con,ExamIssue iss " +
				"where  con.subId=iss.subId and  con.issueId='" + issueId + "' and iss.contype !=4 and iss.contype !=5 group by con.userId";
		List list = DAO.query(hql);
		List resultList = new ArrayList();
		Map map = null;
		for(int i=0;i<list.size();i++){
			map = new HashMap();
			Object[] obj = (Object[])list.get(i);
			String userId = obj[0].toString();
			Integer noqa = Integer.parseInt(obj[1].toString());
			String hqlSub = "select sum(con.mark)as score from ExamContent con " +
				",ExamIssue iss where con.subId=iss.subId " +
				"and  con.issueId='" + issueId + "' and (iss.contype =4 or iss.contype =5) and con.userId='"+ userId + "'  group by con.userId";
			List listSub = DAO.query(hqlSub);
			Integer qa = (Integer)listSub.get(0);
			map.put("title", config.getTitle());
			map.put("userId", userId);
			map.put("userName", service.id2Name(userId, "tawSystemUserDao"));
//			map.put("userName", SystemResource.getUserNameById(userId));
			map.put("noqa", noqa);
			map.put("qa", qa);
			map.put("issueId", issueId);
			resultList.add(map);
		}
		
		//查询考生是否已交卷
		String hqlSubmit = "from ExamSubmit where issueId='"+ issueId +"'";
		List submitList = DAO.query(hqlSubmit);
		for(int j=0;j<submitList.size();j++){
			ExamSubmit s = (ExamSubmit)submitList.get(j);
			for(int n=0;n<resultList.size();n++){
				Map m = (Map)resultList.get(n);
				if(m.get("userId").toString().equals(s.getUserId())){
					m.put("finish", "true");
					break;
				}
			}
		}
		return resultList;
	}
	
	public List findQaMarkPerson(String userId,String issueId){
		String hql ="select con.id,iss.title,con.options ,iss.options,iss.value  " +
				"from ExamContent con ,ExamIssue iss " +
				"where con.subId=iss.subId and  con.issueId='"+issueId+"' and (iss.contype =4 or iss.contype =5)" +
				" and con.userId='"+userId+"'";
		List list = DAO.query(hql);
		List result = new ArrayList();
		for(int i=0;i<list.size();i++){
			Map map = new HashMap();
			Object[] obj = (Object[])list.get(i);
			map.put("id", obj[0]);
			map.put("title", obj[1]);
			map.put("answerPerson", obj[2]);
			map.put("answer", obj[3]);
			map.put("point", obj[4]);
			result.add(map);
		}
		return result;
	}
	
	/**
	 * 获取模拟考试题(预留未使用)
	 */
	public List findMockExamList(String typeSel){
	    return null;
	}
	
	/**
	 * 查询各级考试汇总(省级、网络部)
	 * @param deptStr
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel findExamConfigList(String deptStr,int pageIndex,int pageSize){
//		StringBuffer hql = new StringBuffer("from ExamConfig where companyId in (select dept.deptType from  TawSystemDeptExam dept where dept.deptId in (");
//		hql.append(deptStr).append(")) order by startTime desc");
		StringBuffer hql = new StringBuffer("select e from ExamConfig e,TawSystemUser u where e.issueUser=u.userid and examType=1 and u.deptid in (");
		hql.append(deptStr).append(") order by e.startTime desc");
		Object[] params = {};
		PageModel pm = DAO.searchPaginated(hql.toString(), params, pageIndex, pageSize);
		return pm;
	}
	
	/**
	 * 查询各级考试汇总(地市)
	 * @param isCityFlag true地市 false不是地市
	 * @param deptStr isCityFlag=true则为单一部门ID，如果isCityFlag=false则为部门逗号分隔字符串
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel findExamConfigListCity(boolean isCityFlag,String deptStr,int pageIndex,int pageSize){
		StringBuffer hql = new StringBuffer("");
		
		//如果是地市的用户，那么只能看本地市
		if(isCityFlag){
			hql.append("select e from ExamConfig e,TawSystemUser u where e.issueUser=u.userid and examType=1 and u.deptid = '")
					.append(deptStr).append("' order by e.startTime desc");
		}else{//不是地市的（那么就是比地市级别更高的网络部或者省级的，那么可以看所有地市的）
			hql.append("select e from ExamConfig e,TawSystemUser u where e.issueUser=u.userid and examType=1 and u.deptid not in (")
					.append(deptStr).append(") order by e.startTime desc");
		}
		Object[] params = {};
		PageModel pm = DAO.searchPaginated(hql.toString(), params, pageIndex, pageSize);
		return pm;
	}
	//导出成绩
	/**
	 * 按部门导出成绩
	 */
	public void exportScoreByDept(String issueId, OutputStream toClient){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("score");
		sheet.setColumnWidth((short) 0, (short) 2300);
		sheet.setColumnWidth((short) 1, (short) 4300);
		sheet.setColumnWidth((short) 2, (short) 4500);
		sheet.setColumnWidth((short) 3, (short) 2400);
		sheet.setColumnWidth((short) 4, (short) 2400);
		
		HSSFRow row = sheet.createRow((int) 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setColor(HSSFFont.BOLDWEIGHT_NORMAL); // 字体颜色
		font.setFontName("黑体"); // 字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font.setItalic(false); // 是否使用斜体
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中中
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("单位");
		cell.setCellStyle(cellStyle);

		cell = row.createCell((short) 1);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("要求参考人数");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell((short) 2);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("实际参考人数");
		cell.setCellStyle(cellStyle);
		
		
		cell = row.createCell((short) 3);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("缺考人数");
		cell.setCellStyle(cellStyle);

		cell = row.createCell((short) 4);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("平均成绩");
		cell.setCellStyle(cellStyle);

		
		
		//String hql ="select con.id,iss.title,con.options ,";
		List list = DAO.findExamDeptQuery(issueId);
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			// 得到考试信息
			String  companyName=((ExamDeptQueryVO) list.get(i)).getCompanyName();
			int  testerCountAll=((ExamDeptQueryVO) list.get(i)).getTesterCountAll();
			int  testerCount=((ExamDeptQueryVO) list.get(i)).getTesterCount();
			int  notesterCount=((ExamDeptQueryVO) list.get(i)).getNotesterCount();
			String  averagePoint=((ExamDeptQueryVO) list.get(i)).getAveragePoint();
			
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue(companyName);

			HSSFCell cell2 = row.createCell((short) 1);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(testerCountAll);
			
			HSSFCell cell3 = row.createCell((short) 2);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(testerCount);
			
			HSSFCell cell4 = row.createCell((short) 3);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(notesterCount);
			
			HSSFCell cell5 = row.createCell((short) 4);
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue(averagePoint);
		}
		try {
			workbook.write(toClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按成绩导出成绩
	 */
	public void exportScoreByScore(String issueId, OutputStream toClient){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("score");
		sheet.setColumnWidth((short) 0, (short) 2300);
		sheet.setColumnWidth((short) 1, (short) 4300);	
		sheet.setColumnWidth((short) 2, (short) 4500);
		sheet.setColumnWidth((short) 3, (short) 2400);
		
		HSSFRow row = sheet.createRow((int) 0);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
		font.setFontName("黑体"); // 字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font.setItalic(false); // 是否使用斜体
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中中
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("姓名");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell((short) 1);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("单位");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell((short) 2);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("考试成绩");
		cell.setCellStyle(cellStyle);
		
		
		cell = row.createCell((short) 3);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("考试时长");
		cell.setCellStyle(cellStyle);
		
		List list = DAO.findExamTesterQuery(issueId);
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			String  name=((ExamTesterQueryVO) list.get(i)).getName();
			String  companyName=((ExamTesterQueryVO) list.get(i)).getCompanyName();
			String  point=((ExamTesterQueryVO) list.get(i)).getPoint();
			String  examTime=((ExamTesterQueryVO) list.get(i)).getExamTime();
			
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue(name);
			
			HSSFCell cell2 = row.createCell((short) 1);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(companyName);
			
			HSSFCell cell3 = row.createCell((short) 2);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(point);
			
			HSSFCell cell4 = row.createCell((short) 3);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(examTime);
		}
		try {
			workbook.write(toClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 按成绩导出成绩
	 */
	public void exportScoreByCompany(HttpServletRequest request, OutputStream toClient){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("score");
		sheet.setColumnWidth((short) 0, (short) 2300);
		sheet.setColumnWidth((short) 1, (short) 4300);
		sheet.setColumnWidth((short) 2, (short) 4500);
		sheet.setColumnWidth((short) 3, (short) 2400);
		sheet.setColumnWidth((short) 4, (short) 2400);
		
		HSSFRow row = sheet.createRow((int) 0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10); // 字体高度
		font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
		font.setFontName("黑体"); // 字体
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font.setItalic(false); // 是否使用斜体
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中中
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("用户名");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell((short) 1);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("总分");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell((short) 2);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("正确数");
		cell.setCellStyle(cellStyle);
		
		
		cell = row.createCell((short) 3);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("总题数");
		cell.setCellStyle(cellStyle);
		
		cell = row.createCell((short) 4);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("正确率");
		cell.setCellStyle(cellStyle);
		List list=null;
		try {
			list = getExamQueryList(request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			// 得到考试信息
			String  userName=((studyQO) list.get(i)).getUserName();
			int  mark=((studyQO) list.get(i)).getMark();
			int  right=((studyQO) list.get(i)).getRight();
			int  total=((studyQO) list.get(i)).getTotal();
			double  rate=((studyQO) list.get(i)).getRate();
			
			HSSFCell cell1 = row.createCell((short) 0);
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue(userName);
			
			HSSFCell cell2 = row.createCell((short) 1);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(mark);
			
			HSSFCell cell3 = row.createCell((short) 2);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(right);
			
			HSSFCell cell4 = row.createCell((short) 3);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(total);
			
			HSSFCell cell5 = row.createCell((short) 4);
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue(rate);
		}
		try {
			workbook.write(toClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
