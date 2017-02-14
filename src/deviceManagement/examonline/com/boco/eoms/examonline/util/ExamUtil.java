package com.boco.eoms.examonline.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.examonline.dao.ExamDAO;

public class ExamUtil {

	public static Map examCompanyMap = new HashMap();
	public static final String provDeptStr = "1,1000801,1000803,1784";
	public static final String netDeptStr = "1721,1722,1769,10102,1301,1011001,1011002,100070201,1010402,1010401";
	public static final String TYPESEL = "typeSelList";
	public static final String EXAMWAREHOUSELIST = "examWarehouseList";
	
	static {
		examCompanyMap.put("13","监控室");
		examCompanyMap.put("14","贵阳分公司");
	    examCompanyMap.put("15","遵义分公司");
	    examCompanyMap.put("16","安顺分公司");
	    examCompanyMap.put("17","黔南分公司");
	    examCompanyMap.put("18","黔东南分公司");
	    examCompanyMap.put("19","铜仁分公司");
	    examCompanyMap.put("20","毕节分公司");
	    examCompanyMap.put("21","六盘水分公司");
	    examCompanyMap.put("22","黔西南分公司");
	    examCompanyMap.put("115","代维公司");
	    examCompanyMap.put("1721","交换维护室");
	    examCompanyMap.put("1722","支撑室");
	    examCompanyMap.put("1723","无线网优室");
	    examCompanyMap.put("1301","数据传输室");
	    examCompanyMap.put("10103","综合分析室");
	}
	
	  /**
	   * 字符串strs是否包含str
	   * @param str
	   * @param strs 
	   * @return
	   */
	  public static boolean isContainStr(String str,String strs){
		  String[] strArr = strs.split(",");
		  for(int i=0;i<strArr.length;i++){
			  if(str.equals(strArr[i])){
				  return true;
			  }
		  }
		  return false;
	  }
	  
	  /**
		 * 获取随机文件名，在同一目录下不重复
		 * @param uploadFileName 原始文件名
		 * @param path 文件保存路径
		 * @return
		 */
		public static String getRandomFileName(String fileName,String path){
			String[] split = fileName.split("\\.");
			String extension = "." + split[split.length - 1].toLowerCase(); //获取原文件扩展名
			Random random = new Random();
			int randomNum = random.nextInt(1000000);
			String randomName = randomNum + extension;
			while(isFileExist(randomName,path)){
				randomNum = random.nextInt(1000000);
				randomName = randomNum + extension;
			}
			return randomName;
		}
		
		/**
		 * 判断文件是否存在
		 * @param fileName
		 * @param path
		 * @return
		 */
		public static boolean isFileExist(String fileName,String path){
			File file = new File(path + fileName);
			return file.exists();
		}
		
		/**
		 * 通过字典ID查询字典名称
		 * @param dictid
		 */
		public static String getDictNameByDictid( String dictid ){
			String tmpstr=StaticMethod.null2String(dictid).trim();
			System.out.println("tmpstr========"+tmpstr);
			if(tmpstr.length()>0)
			{
			ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager)ApplicationContextHolder
			.getInstance().getBean("ItawSystemDictTypeManager");
			return mgr.getDictByDictId(dictid).getDictName();
			}
			return "";
		}
		public static String getSpecialtySel(String... dictid)
		{
//			String sql=" from TawSystemDictType tdt where tdt.parentDictId='11801'";
			String sql=" from TawSystemDictType tdt where tdt.parentDictId='11223'";
			 return getDictNameSel(sql,dictid);
		}
		public static String getDictNameSel(String sql,String... dictid)
		{
			StringBuffer sb=new StringBuffer();
			//String hql="from TawSystemDictType tdt where tdt.parentDictId='11801'";
			ExamDAO DAO = (ExamDAO)ApplicationContextHolder.getInstance().getBean("ExamDAO");
			List l=DAO.getHibernateTemplate().find(sql);
			for (int i = 0; i < l.size(); i++) {
				TawSystemDictType tdt=(TawSystemDictType)l.get(i);
				if(dictid.length>0&&String.valueOf(dictid[0]).equals(tdt.getDictId()))
					sb.append("<option value='").append(tdt.getDictId()).append("' selected>").append(tdt.getDictName()).append("</option>");
				else
					sb.append("<option value='").append(tdt.getDictId()).append("'>").append(tdt.getDictName()).append("</option>");
			}
			return sb.toString();
		}
		public static String nullObject2String(Object s) {
			String str = "";
			try {
				str = s.toString();
			} catch (Exception e) {
				str = "";
			}
			return str;
		}
}
