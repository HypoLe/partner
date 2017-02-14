package com.boco.eoms.partner.personnel.util;


import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
	/**
 * <p>
 * Title:Excel验证常用方法
 * </p>
 * <p>
 * Description:Excel验证，取值方法
 * </p>
 * <p>
 * Aug 15, 2012 11:33:41 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class ExcelUtil {
	
	/**
	 * 非必填项 字符串最大长度
	 * @param str  
	 * @param maxLength 字符串最大长度
	 * @return
	 */
	public static String checkLength(String str,int maxLength){
		str = StaticMethod.null2String(str);
			if(str.length()>maxLength)
				throw new RuntimeException("长度不合法！最大长度"+maxLength);
		return str;
	}
	/**
	 * 必填项 字符串最大长度
	 * @param str  
	 * @param maxLength 字符串最大长度
	 * @return
	 */
	public static String checkNotNullLength(String str,int maxLength){
		str = ExcelUtil.checkIsNull(str);
		return checkLength(str, maxLength);
	}
	/**
	 * 非必填项 时间字段为空检查及转换
	 * @param dateTime
	 * @return
	 */
	public static String checkDate(String date){
		date = StaticMethod.null2String(date);
		if (MyUtil.isEmpty(date)) {
			return date;
		}
		if(date.length()!=10)
			throw new RuntimeException("日期格式不合法！日期格式：####-##-##");
		SimpleDateFormat sdf;
		try {
			StringBuilder ref = new StringBuilder();
			    ref.append("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|" )
					.append("(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|" )
					.append("([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|" )
					.append("(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|" )
					.append("(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|" )
					.append("(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|" )
					.append("(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))");
			if(!date.matches(ref.toString()))
				throw new RuntimeException("日期格式不合法！日期格式：####-##-##");
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.format(sdf.parse(date));
		} catch (Exception e) {
			throw new RuntimeException("日期格式不合法！日期格式：####-##-##");
		}
		return  date;
	}
	
	
	public static void main(String[] args) {
		System.out.println(checkDate("2011-12-99"));
	}
	
	
	/**
	 * 必填项 时间字段为空检查及转换
	 * @param dateTime
	 * @return
	 */
	public static String checkNotNullDate(String date){
		date = ExcelUtil.checkIsNull(date);
		return checkDate(date);
	}
	/**
	 * 非必填项 检查字符串是否为数字
	 * @param str
	 * @return
	 */
	public static String checkNumber(String str){
		str = StaticMethod.null2String(str);
		if(!MyUtil.isEmpty(str))
			if(!str.matches("^\\d+(\\.{1}\\d+)?$"))
				throw new RuntimeException("必须全为数字");
		return str;
	}
	/**
	 * 必填项 检查字符串是否为数字
	 * @param str
	 * @return
	 */
	public static String checkNotNullNumber(String str){
		str = checkIsNull(str);
		return checkNumber(str);
	}
	
	/**
	 * 非必填项 字典值处理
	 * @param dict  字典名下划线字典id   eg:   EMOS_101
	 * @return 字典id
	 */
	public  static String checkDIct(String dict){
		if(!StaticMethod.null2String(dict).contains("_"))
			throw new RuntimeException("字典数据格式错误 ： 格式为  字典名下划线字典id！");
		String pageTemp[] = dict.split("_");
		String dictId = pageTemp[1].toString();
		ID2NameService service = (ID2NameService) ApplicationContextHolder
												.getInstance().getBean("ID2NameGetServiceCatch");
		String dictIdTemp = service.id2Name(dictId, "tawSystemDictTypeDao");
		
		if(dictIdTemp==null)
			throw new RuntimeException("字典id :"+dictId+"不存在！");
		
		if(!dictIdTemp.equals(pageTemp[0].toString()))
				throw new RuntimeException(pageTemp[0]+"不存在！请不要修改字典值");
		
		return dictId;
		
	}
	/**
	 * 必填项 字典值处理
	 * @param dict  字典名下划线字典id   eg:   EMOS_101
	 * @return 字典id
	 */
	public  static String checkNotNullDIct(String dict){
		dict = checkIsNull(dict);
		return checkDIct(dict);
	}	
	/**
	 * 检查UserId在数据库中是否存在
	 * @param userid
	 * @return 1:id,2:name
	 */
	public static String[] checkUserId(String userid){
		userid = StaticMethod.null2String(userid);
		PartnerUserMgr panUserService = (PartnerUserMgr) ApplicationContextHolder
															.getInstance().getBean("partnerUserMgr");
		PartnerUser user =  panUserService.getPartnerUserByUserId(userid);
		if(user==null||user.getId()==null)
			throw new RuntimeException(userid+"在数据库不存在");
		String[] strs = new String[2];
		strs[0] = userid;
		strs[1] = user.getName();
		return strs;
	}

	/**
	 * 取出某 单元格 值
	 * @param row
	 * @param colNum  列号  从0开始
	 * @return 
	 */
	public static String getString(HSSFRow row,int colNum){
		HSSFCell  cell = row.getCell(colNum);
		String value = "";
		if(cell!=null){
			if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING)
					value = cell.getRichStringCellValue().toString();
		  else
			// throw new RuntimeException("Excel单元格数据类型 只能是文本类型");
			  return value;
		}
		return  value.trim();
	}

	/**
	 * 检查字段否是为空
	 * @param str
	 * @return
	 */
	private static String checkIsNull(String str){
		if(MyUtil.isEmpty(str)){
			throw new RuntimeException("导入的字段为空！");
		}
		return str;
	}	
}
