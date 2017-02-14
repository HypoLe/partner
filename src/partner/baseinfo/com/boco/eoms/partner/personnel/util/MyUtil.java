package com.boco.eoms.partner.personnel.util;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
	/**
 * <p>
 * Title:常用方法
 * </p>
 * <p>
 * Description:常用方法
 * </p>
 * <p>
 * Jul 25, 2012 2:33:41 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class MyUtil {
	
	/**
	 * 字符串编码转换
	 * @param str
	 * @return
	 */
	public static String getString(String str) {
		String newStr = "";
		 if(!isEmpty(str))
			 try {
			//	 newStr = new String(str.getBytes("ISO8859-1"),"UTF-8");
				 newStr = str;
			} catch (Exception e) {
				newStr = "";
			}
			 return newStr;
	}
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 空：true
	 */
	public static boolean isEmpty(String str){
		   if("".equals(StaticMethod.null2String(str)))
			   return true;
		   else
			   return false;
	   }
	/**
	 * 统计查询 SQL基本语句
	 * @return
	 */
	public static String getBaseSqlStr(){
		StringBuilder sbd = new StringBuilder();
		sbd.append(" ( select city.area_name as city,county.area_name as county,")
			.append(" dwdept.name as deptname,dwdept.id as deptid, county.big_type as profTypeFor")      
			.append(" from pnr_dept as city,pnr_dept as county, pnr_dept as dwdept")
			.append(" where county.interface_head_id= city.id")
			.append(" and dwdept.interface_head_id = county.id ) ")
			.append(" as base ");
		return sbd.toString();
	}
	
	/**
	 * 字符串2字符串数组
	 * @param str
	 * @param regex 要分割字符
	 * @return
	 */
	public static String[] getStrings(String str,String regex){
		if(!isEmpty(str))
			return str.split(regex);
		else
			throw new RuntimeException("字符串为空");
	}
	
	/**
	 * 字典值 转换为 字典名称  
	 * 即：取得字典对应中文名
	 * @param field 结果集 字段名
	 * @param value
	 * @return
	 */
	 public static String dict2Name(String field,String value){
		 ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		 //modify by fengguangping 后面添加的或条件是为了兼容Oracle数据; 2013-03-04
		if(field.endsWith("typelikedict")||field.endsWith("typelikedict".toUpperCase())){
			value =service.id2Name(value, "tawSystemDictTypeDao");
		}
		else if(field.endsWith("typelikearea")||field.endsWith("typelikearea".toUpperCase())){
				value =service.id2Name(value, "tawSystemAreaDao");
			}
		else if(field.endsWith("typelikedept")||field.endsWith("typelikedept".toUpperCase())){
			value =service.id2Name(value, "tawSystemDeptDao");
		}
		else if(field.endsWith("typelikeuser")||field.endsWith("typelikeuser".toUpperCase())){
				value =service.id2Name(value, "tawSystemUserDao");
			}
		else if(field.endsWith("typeper")||field.endsWith("typeper".toUpperCase())){
			 NumberFormat formatter = new DecimalFormat("0.000");
			 Double per = Double.parseDouble(value)*100;
			 value = formatter.format(per)+"%";
//			 value = formatter.format(per);
			}
		else if(field.endsWith("typefor")||field.endsWith("typefor".toUpperCase())){
				String aa[]=value.split(",");
				String bb="";
				for(int ii=0;ii<aa.length;ii++){
					bb =bb+service.id2Name(aa[ii], "tawSystemDictTypeDao")+";";
				}
				value=bb;
			}
		return value;
	}
	 /**
	  * 格式化 取时间 yyyy-MM-dd HH:mm:ss
	  * @param time
	  * @return
	  */
	 public static String getStrTime(Date time){
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String timeStr ;
		 try {
			 timeStr = dateFormat.format(time);
		} catch (Exception e) {
			timeStr = "1900-01-01 00:00:00";
		}
		 return timeStr;
	 }
	 public static String getUUID(){
		 return UUID.randomUUID().toString().replace("-", "");
	 }
	 
}
