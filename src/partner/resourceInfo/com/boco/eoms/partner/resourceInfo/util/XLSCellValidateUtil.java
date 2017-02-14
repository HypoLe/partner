package com.boco.eoms.partner.resourceInfo.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.joda.time.format.DateTimeFormat;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.dict.service.Name2IDService;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.partner.appops.service.PartnerAppopsUserService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.service.CarService;

public class XLSCellValidateUtil {
	public static String CELL_TYPE_DOUBLE = "1";
	public static String CELL_TYPE_INTEGER = "2";

	/**
	 * 获取单元格的String值，当单元格的值是数值时，也返回String类型的值
	 * 
	 * @param 单元格序号:从0开始
	 * @param 单元行
	 * @return
	 */
	public static String getCellStringValue(HSSFRow row, int cellNum) {
		String cellValue = "";
		if (row.getCell(cellNum) != null) {
			if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				long cellIntValue = (long) row.getCell(cellNum)
						.getNumericCellValue();
				cellValue = String.valueOf(cellIntValue);
			}
			if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				// cellValue=row.getCell(cellNum).getRichStringCellValue().toString().trim();
				// cellValue=row.getCell(cellNum).getStringCellValue().toString().trim();
				cellValue = row.getCell(cellNum).getRichStringCellValue()
						.getString().trim();
			}
		}
		return cellValue;
	}

	/**
	 * 获取单元格的String值，当单元格的值是数值时，也返回String类型的值
	 * 
	 * @param 单元格序号:从0开始
	 * @param 单元行
	 * @return
	 */
	public static String getCellStringValue(HSSFRow row, int cellNum,
			String dataType) {
		String cellValue = "";
		if (row.getCell(cellNum) != null) {
			if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				if (dataType.equals(CELL_TYPE_DOUBLE)) {
					cellValue = (double) row.getCell(cellNum)
							.getNumericCellValue()
							+ "";
				} else if (dataType.equals(CELL_TYPE_INTEGER)) {
					cellValue = (int) row.getCell(cellNum)
							.getNumericCellValue()
							+ "";
				} else {
					cellValue = (int) row.getCell(cellNum)
							.getNumericCellValue()
							+ "";
				}
			}
			if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				cellValue = row.getCell(cellNum).getRichStringCellValue()
						.toString().trim();
			}
		}
		return cellValue;
	}

	/**
	 * 检查单元格内容是否为空
	 * 
	 * @param 单元格序号:从0开始
	 * @param 单元行
	 * @return
	 */
	public static boolean cellIsBlank(HSSFRow row, int cellNum) {
		boolean flag = false;
		String cellValue = "";
		if (row.getCell(cellNum) != null) {
			if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				int cellIntValue = (int) row.getCell(cellNum)
						.getNumericCellValue();
				cellValue = String.valueOf(cellIntValue);
			}
			if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_STRING) {
				cellValue = row.getCell(cellNum).getRichStringCellValue()
						.toString().trim();
			}
		}
		if ("".equals(cellValue)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: checkEmail
	 * @Description: 验证邮箱格式是否正确
	 * @param
	 * @return
	 * @Time:Nov 26, 2012-3:00:15 PM
	 * @Author:fengguangping
	 * @return void 返回类型
	 * @throws
	 */
	public static String checkEmail(HSSFRow row, int cellNum, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = "";
		if (allowBlank) {
			cellValue = nullCell2String(row, cellNum);
		} else {
			cellValue = checkIsNull(row, cellNum);
		}
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		if (!"".equals(cellValue)) {
			final Matcher mat = pattern.matcher(cellValue);
			if (!mat.find()) {
				throw new RuntimeException("第" + rowNumber + "行第"
						+ (cellNum + 1) + "列邮箱\"" + cellValue + "\"格式不正确！");
			}
		}
		return cellValue;
	}

	/**
	 * 判断车牌号的唯一性
	 * 
	 * @param cellValue
	 * @return
	 */
	public static String checkCarNumberIsOnly(HSSFRow row, int cellNum)
			throws Exception {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = checkIsCarNumber(row, cellNum);
		CarService carService = (CarService) ApplicationContextHolder
				.getInstance().getBean("carService");
		List<Car> list = new ArrayList<Car>();
		list = carService.findByCarNumber(cellValue, "");
		if (list.size() > 0) {
			throw new RuntimeException("第" + rowNumber + "行第" + (cellNum + 1)
					+ "列车牌号\"" + cellValue + "\"已经存在！");
		} else {
			return cellValue;
		}
	}

	/**
	 * 判断车牌号的唯一性
	 * 
	 * @param cellValue
	 * @return
	 */
	public static String checkCarNumberIsOnly(HSSFRow row, int cellNum,
			String type, String id) throws Exception {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = checkIsCarNumber(row, cellNum);
		if ("add".equals(type)) {
			if (PnrProcessCach.carnumAndGPSnumListCach.containsKey(cellValue)) {
				throw new RuntimeException("第" + rowNumber + "行第"
						+ (cellNum + 1) + "列车牌号\"" + cellValue + "\"已经存在！");
			}
		} else if ("update".equals(type)) {
			if (PnrProcessCach.carnumAndGPSnumListCach.containsKey(cellValue)) {
				String idAndNum = PnrProcessCach.carnumAndGPSnumListCach
						.get(cellValue);
				if (idAndNum.contains(id)) {
					throw new RuntimeException("第" + rowNumber + "行第"
							+ (cellNum + 1) + "列车牌号\"" + cellValue + "\"已经存在!");
				}
			}
		}
		return cellValue;
	}

	/**
	 * 判断车载GPS编号的唯一性
	 * 
	 * @param cellValue
	 * @return
	 */
	public static String checkGPSNumberIsOnly(HSSFRow row, int cellNum)
			throws Exception {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = "";
		cellValue = getCellStringValue(row, cellNum);
		CarService carService = (CarService) ApplicationContextHolder
				.getInstance().getBean("carService");
		List list = new ArrayList<Car>();
		list = carService.findByCarGPSNumber(cellValue, "");
		if (list.size() > 0) {
			throw new RuntimeException("第" + rowNumber + "行第" + (cellNum + 1)
					+ "列GPS编号\"" + cellValue + "\"已经存在！");
		} else {
			return cellValue;
		}
	}

	/**
	 * 判断车载GPS编号的唯一性
	 * 
	 * @param cellValue
	 * @return
	 */
	public static String checkGPSNumberIsOnly(HSSFRow row, int cellNum,
			String type, String id) throws Exception {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = "";
		cellValue = getCellStringValue(row, cellNum);
		if ("add".equals(type)) {
			if (PnrProcessCach.carnumAndGPSnumListCach.containsKey(cellValue)) {
				throw new RuntimeException("第" + rowNumber + "行第"
						+ (cellNum + 1) + "列GPS编号\"" + cellValue + "\"已经存在！");
			}
		} else if ("update".equals(type)) {
			if (PnrProcessCach.carnumAndGPSnumListCach.containsKey(cellValue)) {
				String idAndNum = PnrProcessCach.carnumAndGPSnumListCach
						.get(cellValue);
				if (idAndNum.contains(id)) {
					throw new RuntimeException("第" + rowNumber + "行第"
							+ (cellNum + 1) + "列GPS编号\"" + cellValue
							+ "\"已经存在！");
				}
			}
		}
		return cellValue;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return 空：false
	 */
	public static boolean isEmpty(HSSFRow row, int cellNum) {
		String cellValue = "";
		cellValue = getCellStringValue(row, cellNum);
		if (!"".equals(StaticMethod.null2String(cellValue))) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 检查字符串最大长度
	 * 
	 * @param str
	 * @param maxLength
	 *            字符串最大长度
	 * @return
	 */
	public static String checkLength(HSSFRow row, int cellNum, int maxLength) {
		String cellValue = nullCell2String(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (cellValue.length() > maxLength) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"长度不合法");
		}
		return cellValue;
	}

	/**
	 * 检查字符串最大长度
	 * 
	 * @param str
	 * @param maxLength
	 *            字符串最大长度
	 * @return
	 */
	public static String checkLength(HSSFRow row, int cellNum, int maxLength,
			boolean allowBlank) {
		String cellValue = "";
		if (!allowBlank) {
			cellValue = checkIsNull(row, cellNum);
		} else {
			cellValue = nullCell2String(row, cellNum);
		}
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (cellValue.length() > maxLength) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"长度不合法");
		}
		return cellValue;
	}

	/**
	 * 时间字段为空检查及转换
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String checkDate(HSSFRow row, int cellNum) {
		String cellValue = checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.format(sdf.parse(cellValue));
		} catch (Exception e) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列日期\"" + cellValue + "\"格式不合法!");
		}
		return cellValue;
	}

	public static String checkDateYear(HSSFRow row, int cellNum) {

		String cellValue = checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		try {
			sdf.format(sdf.parse(cellValue));
		} catch (Exception e) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列日期\"" + cellValue + "\"格式不合法!");
		}
		return cellValue;
	}

	/**
	 * 时间字段为空检查及转换
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String checkDate(HSSFRow row, int cellNum, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (allowBlank) {
				cellValue = getCellStringValue(row, cellNum);
				if ("".equals(cellValue)) {
					return cellValue;
				}
			} else {
				cellValue = checkIsNull(row, cellNum);
			}
			sdf.format(sdf.parse(cellValue));
		} catch (Exception e) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列日期\"" + cellValue + "\"格式不合法!");
		}
		return cellValue;
	}

	/**
	 * 检查字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static String checkNumber(HSSFRow row, int cellNum) {
		String cellValue = checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (!cellValue.matches("^\\d+(\\.{1}\\d+)?$")) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"必须为数字");
		}
		return cellValue;
	}

	public static String checkPhoneNumber(HSSFRow row, int cellNum) {
		String cellValue = checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (!cellValue.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"电话号码错误");
		}
		// 取字典数据
		PartnerAppopsUserService appopsUserService = (PartnerAppopsUserService) ApplicationContextHolder
				.getInstance().getBean("pnrAppopsUserService");
		int mobileCheck = appopsUserService.getMobileCheck(cellValue, "");
		if(mobileCheck==1){
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"手机号码已存在");
		}
		
		return cellValue;
	}

	/**
	 * 
	 * @param row
	 * @param cellNum
	 * @param
	 *            allowBlank：true表示可以为空，为空时直接返回值，如果有值则进行校验，false表示不允许为空，不允许为空时要校验是否为数值，
	 * @return
	 */
	public static String checkNumber(HSSFRow row, int cellNum,
			boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = nullCell2String(row, cellNum);
		if (row.getCell(cellNum) != null) {
			int cellNumber = cellNum + 1;
			if (!allowBlank) {
				checkIsNull(row, cellNum);
				if (!cellValue.matches("^\\d+(\\.{1}\\d+)?$")) {
					throw new RuntimeException("第" + rowNumber + "行第"
							+ cellNumber + "列\"" + cellValue + "\"必须为数字");
				}
			} else {
				if (!"".equals(cellValue)) {
					if (!cellValue.matches("^\\d+(\\.{1}\\d+)?$")) {
						throw new RuntimeException("第" + rowNumber + "行第"
								+ cellNumber + "列\"" + cellValue + "\"必须为数字");
					}
				}
			}
		}
		return cellValue;
	}

	public static String checkIsNull(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = "";
		if (row.getCell(cellNum) != null) {
			int cellNumber = cellNum + 1;
			cellValue = getCellStringValue(row, cellNum);
			if ("".equals(cellValue)) {
				throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
						+ "列导入的字段不能为空！");
			}
		} else {
			throw new RuntimeException("第" + rowNumber + "行第" + (cellNum + 1)
					+ "列导入的字段不能为空！");
		}
		return cellValue;
	}

	public static String checkIsCarNumber(HSSFRow row, int cellNum) {
		checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = getCellStringValue(row, cellNum);
		String re = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$";
		if (!cellValue.matches(re)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列车牌号\"" + cellValue + "\"不合法");
		}
		return cellValue;
	}

	/**
	 * 
	 * @param name
	 *            字典值名
	 * @param initDictId
	 *            该字典模块的字典值
	 * @return
	 * @throws Exception
	 */
	public static String name2Id(HSSFRow row, int cellNum, String initDictId)
			throws Exception {
		checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = getCellStringValue(row, cellNum);
		Map<String, String> dictMap = getDictMap(initDictId);
		String id = "";
		String value = "";
		for (String key : dictMap.keySet()) {
			value = dictMap.get(key);
			if (cellValue.equals(value)) {
				id = key;
				break;
			}
		}
		if ("".equals(id)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列导入数据\"" + cellValue + "\"与字典不匹配！");
		}
		return id;
	}

	public static String name2Id(String name, String initDictId)
			throws Exception {
		Map<String, String> dictMap = getDictMap(initDictId);
		String id = "";
		String value = "";
		for (String key : dictMap.keySet()) {
			value = dictMap.get(key);
			if (name.equals(value)) {
				id = key;
				break;
			}
		}
		if ("".equals(id)) {
			throw new RuntimeException("导入数据与字典不匹配！");
		}
		return id;
	}

	/**
	 * @param initDictId
	 *            字典模块值
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getDictMap(String initDictId)
			throws Exception {
		if ("".equals(initDictId) || initDictId == null) {
			return null;
		}
		// 取字典数据
		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemDictTypeManager");
		List list = dictManager.getDictSonsByDictid(initDictId);
		Map<String, String> dictMap = new HashMap<String, String>();
		if (list.size() > 0) {
			String itemName = null;
			String itemId = null;
			// 将list中的值元素插入Map
			for (Iterator it = list.iterator(); it.hasNext();) {
				TawSystemDictType item = (TawSystemDictType) it.next();
				itemName = StaticMethod.null2String(item.getDictName());
				itemId = StaticMethod.null2String(item.getDictId());
				dictMap.put(itemId, itemName);
			}
		} else {
			return null;
		}
		return dictMap;
	}

	/**
	 * 比较2个string格式的日期大小 fengguangping Sep 4, 2012-4:21:11 PM
	 * 
	 * @param row
	 * @param cellNumber1:前一个单元的cellNumber；
	 * @param cellNumber2：后一个单元格的cellNumber；
	 * @param compareType：前一个大于后一个为：greatThan，前一个小于于后一个为：lessThan
	 * @return
	 */
	public static boolean compareDate(HSSFRow row, int cellNumber1,
			int cellNumber2, String compareType) {
		Calendar cal1 = new GregorianCalendar();
		Calendar cal2 = new GregorianCalendar();
		int rowNum = row.getRowNum() + 1;
		int cellNum1 = cellNumber1 + 1;
		int cellNum2 = cellNumber2 + 1;
		cal1 = StaticMethod.String2Cal(checkDate(row, cellNumber1));
		cal2 = StaticMethod.String2Cal(checkDate(row, cellNumber2));
		if (compareType.equals("greatThan")) {
			if (cal1.before(cal2)) {
				throw new RuntimeException("第" + rowNum + "行，第" + cellNum1
						+ "列日期必须大于第" + cellNum2 + "列日期！");
			}
		}
		if (compareType.equals("lessThan")) {
			if (cal1.after(cal2)) {
				throw new RuntimeException("第" + rowNum + "行，第" + cellNum1
						+ "列日期必须小于第" + cellNum2 + "列日期！");
			}
		}
		return true;
	}

	/**
	 * 检查文件单元格的格式是不是数值格式 fengguangping Sep 4, 2012-5:44:14 PM
	 * 
	 * @param row
	 * @param cellNum
	 * @return
	 */
	public static double checkIsNumeric(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (row.getCell(cellNum).getCellType() == HSSFCell.CELL_TYPE_STRING) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列单元格格式必须为数值格式！");
		}
		return row.getCell(cellNum).getNumericCellValue();
	}

	/**
	 * 非必填项单元格值转化为string类型 fengguangping Sep 6, 2012-11:49:12 AM
	 * 
	 * @param row
	 * @param cellNum
	 * @return 单元格的string类型的值
	 */
	public static String nullCell2String(HSSFRow row, int cellNum) {
		String cellValue = "";
		if (row.getCell(cellNum) != null) {
			cellValue = getCellStringValue(row, cellNum);
		}
		return cellValue;
	}

	/**
	 * 非必填项单元格值转化为数值double类型 fengguangping Sep 6, 2012-11:49:12 AM
	 * 
	 * @param row
	 * @param cellNum
	 * @return double类型的单元格的值
	 */
	public static double nullCell2Double(HSSFRow row, int cellNum) {
		double cellValue = 0;
		if (row.getCell(cellNum) != null) {
			cellValue = row.getCell(cellNum).getNumericCellValue();
		}
		return cellValue;
	}

	/**
	 * 将string类型"yyyy-MM-dd"格式的日期转化为标准的Date格式； fengguangping Sep 6,
	 * 2012-12:24:12 PM
	 * 
	 * @param row
	 * @param cellNum
	 * @return
	 */
	public static Date toEomsStanderDate(HSSFRow row, int cellNum) {
		return DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime(
				checkDate(row, cellNum)).toDate();
	}

	/**
	 * 区域名称转化为areaid fengguangping Sep 7, 2012-4:44:31 PM
	 * 
	 * @param areaName
	 * @return
	 */
	public static String areaName2AreaId(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		checkIsNull(row, cellNum);
		ITawSystemAreaManager areaManager = (ITawSystemAreaManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemAreaManager");
		String cellValue = row.getCell(cellNum).getRichStringCellValue()
				.toString();
		boolean flag = areaManager.isExitAreaName(cellValue);
		if (!flag) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "区域\"" + cellValue + "\"不存在！");
		}
		Name2IDService name2IDService = (Name2IDService) ApplicationContextHolder
				.getInstance().getBean("name2IDService");
		String areaid = name2IDService.name2Id(cellValue, "", "areaId");
		if ("".equals(areaid)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "区域\"" + cellValue + "\"转换为字典失败！");
		}
		return areaid;
	}

	/**
	 * 验证区域格式是否正确并存在； fengguangping Sep 26, 2012-5:49:50 PM
	 * 
	 * @param row
	 * @param cellNum
	 * @return
	 */
	public static String checkAreaById(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		checkIsNull(row, cellNum);// 验证是否为空；
		String cellValue = row.getCell(cellNum).getRichStringCellValue()
				.toString();
		String[] areas = cellValue.split("_");
		if (areas.length < 2) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的区域\"" + cellValue + "\"格式不正确！");
		}
		ITawSystemAreaManager areaManager = (ITawSystemAreaManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemAreaManager");
		boolean flag = areaManager.isExitAreaName(cellValue);
		if (!flag) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的区域\"" + cellValue + "\"不存在！");
		}
		String areaid = areas[1];
		return areaid;
	}

	/**
	 * 检查单元格输入的区域是否存在，如果存在将areaid返回，如果不存在抛出异常
	 */
	public static String checkAndGetAreaId(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = checkIsNull(row, cellNum);// 验证是否为空；
		if (!PnrProcessCach.areaCach.containsKey(cellValue)) {
			PnrProcessCach.reloadAreaCache();
		}
		if (!PnrProcessCach.areaCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的区域\"" + cellValue + "\"不存在！");
		} else {
			return PnrProcessCach.areaCach.get(cellValue);
		}
	}

	/**
	 * 检查单元格输入的区域是否存在，如果存在将areaid返回，如果不存在抛出异常
	 */
	public static String checkAndGetAreaId(HSSFRow row, int cellNum,
			String areaName) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (!PnrProcessCach.areaCach.containsKey(areaName)) {
			PnrProcessCach.reloadAreaCache();
		}
		if (!PnrProcessCach.areaCach.containsKey(areaName)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的区域\"" + areaName + "\"不存在！");
		} else {
			return PnrProcessCach.areaCach.get(areaName);
		}
	}

	/**
	 * 检查单元格输入的代维公司是否存在，如果存在将deptMagId返回，如果不存在抛出异常
	 */
	public static String checkAndGetDeptId(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = checkIsNull(row, cellNum);// 验证是否为空；
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			PnrProcessCach.reloadCompanyCache();
		}
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的代维公司\"" + cellValue + "\"不存在！");
		} else {
			return PnrProcessCach.deptCompanyCach.get(cellValue).getDeptMagId();
		}
	}

	/**
	 * 检查单元格输入的代维公司是否存在，如果存在将部门主键Id返回，如果不存在抛出异常
	 */
	public static String checkAndGetDeptUUId(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = checkIsNull(row, cellNum);// 验证是否为空；
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			PnrProcessCach.reloadCompanyCache();
		}
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的代维公司\"" + cellValue + "\"不存在！");
		} else {
			return PnrProcessCach.deptCompanyCach.get(cellValue).getId();
		}
	}

	/**
	 * 检查单元格输入的代维公司是否存在，如果存在将areaid返回，如果不存在抛出异常
	 */
	public static String checkAndGetTeamId(HSSFRow row, int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = checkIsNull(row, cellNum);// 验证是否为空；
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			PnrProcessCach.reloadCompanyCache();
		}
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的代维小组\"" + cellValue + "\"不存在！");
		} else {
			return PnrProcessCach.deptCompanyCach.get(cellValue).getDeptMagId();
		}
	}

	/**
	 * 根据代维公司的"车辆负责人(驾驶员)名称"和"代维公司"检查单元格输入的代维公司人员是否存在， 如果存在将userid返回，如果不存在抛出异常
	 * cellNum:是车辆负责人所在的单元格cellNumber cellNum2：是代维公司的cellNumber
	 */
	public static String checkAndGetUserId(HSSFRow row, int cellNum,
			int cellNum2) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		checkIsNull(row, cellNum);// 验证是否为空；
		String cellUserValue = getCellStringValue(row, cellNum);
		String cellDeptValue = getCellStringValue(row, cellNum2);
		PartnerUser user = PnrProcessCach.deptUserCach.get(cellUserValue);
		if (user == null) {
			PnrProcessCach.reloadUserCach();
			user = PnrProcessCach.deptUserCach.get(cellUserValue);
		}
		if (user == null) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中车辆负责人(驾驶员)\"" + cellUserValue + "不存在!");
		}
		if (!StaticMethod.null2String(user.getDeptName()).equals(cellDeptValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中车辆负责人(驾驶员)\"" + cellUserValue + "\"没有在代维公司\""
					+ cellDeptValue + "\"");
		} else {
			return user.getId();
		}
	}

	/**
	 * 解析提醒对象为:张三/李四/13567676767/13667676767格式为userIds:zhangsan,lisi;phones:13567676767,13567676767;
	 */
	public static String checkAndGetRemindObjectIdsAndPhones(HSSFRow row,
			int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellvalue = checkIsNull(row, cellNum);// 验证是否为空；
		String[] remindObjects = cellvalue.split("/");
		String remindObject = "";
		String ids = "userIds:";
		String phones = "phones:";
		ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserManager");
		for (int i = 0; i < remindObjects.length; i++) {
			if (remindObjects[i].length() < 11) {// 长度小于11作为移动人员名称
				List<TawSystemUser> userList = userMgr
						.getUsersByName(remindObjects[i]);
				if (userList.size() < 1) {
					throw new RuntimeException("第" + rowNumber + "行第"
							+ cellNumber + "列中提醒对象\"" + remindObjects[i]
							+ "\"不存在\"");
				} else {
					String userid = userList.get(0).getUserid();
					if (ids.length() > "userIds:".length()) {
						ids += ",";
					}
					ids += userid;
				}
			} else {// 长度大于等于11作为电话号码，直接拼接电话号码
				if (phones.length() > "phones:".length()) {
					phones += ",";
				}
				phones += remindObjects[i];
			}
		}
		// 判断如果ids中为空的话表示没有添加人名字要抛出异常，请在某行某列提醒对象中添加移动管理人员名字;
		if (ids.length() < "userIds:".length() + 1) {
			throw new RuntimeException("提醒对象不能没有移动管理人员,请在第" + rowNumber + "行第"
					+ cellNumber + "列提醒对象\"" + cellvalue + "\"中添加移动管理人员名字!\"");
		}
		remindObject += ids;
		remindObject += ";";
		if (phones.length() > "phones:".length()) {// 存在电话号码
			remindObject += phones;
			remindObject += ";";
		}
		return remindObject;
	}

	/**
	 * 
	 * @Title: checkUserId
	 * @Description: 检验用户是否存在,只适合人员管理模块
	 * @param
	 * @param userid
	 * @param
	 * @return 设定文件
	 * @return String[] 返回类型
	 * @throws
	 */
	public static String[] checkUserId(HSSFRow row, int cellNum) {
		String cellValue = checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;

		if (!PnrProcessCach.deptUserCach.containsKey(cellValue)) {
			PnrProcessCach.reloadUserCach();
		}
		if (!PnrProcessCach.deptUserCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列用户ID为\"" + cellValue + "\"记录不存在");
		}
		String[] strs = new String[10];
		strs[0] = cellValue;
		strs[1] = StaticMethod.null2String(PnrProcessCach.deptUserCach.get(
				cellValue).getName());
		strs[2] = StaticMethod.null2String(PnrProcessCach.deptUserCach.get(
				cellValue).getDeptId());
		strs[3] = StaticMethod.null2String(PnrProcessCach.deptUserCach.get(
				cellValue).getAreaId());
		strs[4] = StaticMethod.null2String(PnrProcessCach.deptUserCach.get(
				cellValue).getPersonCardNo());
		return strs;
	}

	/**
	 * 
	 * @Title: checkUserId
	 * @Description: 检验用户是否存在,只适合人员管理模块
	 * @param
	 * @param userid
	 * @param
	 * @return 设定文件
	 * @return String[] 返回类型
	 * @throws
	 */
	public static PartnerUser checkByUserIdAndGetUser(HSSFRow row, int cellNum) {
		String cellValue = checkIsNull(row, cellNum);
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		PartnerUser partnerUser;
		if (!PnrProcessCach.deptUserCach.containsKey(cellValue)) {
			PnrProcessCach.reloadUserCach();
		}
		if (!PnrProcessCach.deptUserCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列用户ID为\"" + cellValue + "\"记录不存在");
		} else {
			return PnrProcessCach.deptUserCach.get(cellValue);
		}
	}

	/**
	 * 
	 * @Title: checkUserId
	 * @Description: TODO
	 * @param
	 * @Time:Nov 29, 2012-4:43:33 PM
	 * @Author:fengguangping
	 * @return String[] 返回类型
	 * @throws
	 */
	public static String[] getPartnerUserInfoByWorkid(String workid) {
		PartnerUserMgr panUserService = (PartnerUserMgr) ApplicationContextHolder
				.getInstance().getBean("partnerUserMgr");
		PartnerUser user = panUserService.getPartnerUserByUserId(workid);
		String[] strs = new String[5];
		strs[0] = workid;
		if (user != null) {
			strs[1] = StaticMethod.null2String(user.getName());
			strs[2] = StaticMethod.null2String(user.getDeptId());
			strs[3] = StaticMethod.null2String(user.getAreaId());
			strs[4] = StaticMethod.null2String(user.getPersonCardNo());
		}
		return strs;
	}

	/**
	 * 
	 * @Title: checkPersonCardNoAndCheckIsOnlyOne
	 * @Description: 校验人员的身份证号码是否唯一
	 * @param
	 * @Time:Dec 1, 2012-6:22:01 PM
	 * @Author:fengguangping
	 * @return String 返回类型
	 * @throws
	 */
	public static String checkPersonCardNoAndCheckIsOnlyOne(HSSFRow row,
			int cellNum, boolean allowBlank) {
		String cellValue = "";
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (!allowBlank) {
			cellValue = checkIsNull(row, cellNum);
		} else {
			cellValue = nullCell2String(row, cellNum);
		}
		if (cellValue.length() != 18)
			throw new RuntimeException("第" + rowNumber + "行第" + (cellNum + 1)
					+ "列身份证号码位数有误，必须为18位!");
		String personCardNo1 = cellValue.substring(0, 16);
		if (!personCardNo1.matches("^\\d+(\\.{1}\\d+)?$")) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"身份证格式有误，前17位必须全为数字!");
		}
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
				.getInstance().getBean("partnerUserMgr");
		PartnerUser partnerUser = (PartnerUser) partnerUserMgr
				.getPartnerUserByPersonCardNo(cellValue);
		if (partnerUser != null) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"身份证号码已经存在!");
		}
		return cellValue;
	}

	/**
	 * 
	 * @Title: checkPersonCardNoAndCheckIsOnlyOne
	 * @Description: 校验人员的身份证号码是否存在
	 * @param
	 * @Time:Dec 1, 2012-6:22:01 PM
	 * @Author:fengguangping
	 * @return String 返回类型，返回partnerUser
	 * @throws
	 */
	public static PartnerUser checkPersonCardNoIsExist(HSSFRow row,
			int cellNum, boolean allowBlank) {
		String cellValue = "";
		String userId = "";
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (!allowBlank) {
			cellValue = checkIsNull(row, cellNum);
		} else {
			cellValue = nullCell2String(row, cellNum);
		}
		if (cellValue.length() != 18)
			throw new RuntimeException("第" + rowNumber + "行第" + (cellNum + 1)
					+ "列身份证号码位数有误，必须为18位!");
		String personCardNo1 = cellValue.substring(0, 16);
		if (!personCardNo1.matches("^\\d+(\\.{1}\\d+)?$")) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"身份证格式有误，前17位必须全为数字!");
		}
		if (!PnrProcessCach.deptUserCach.containsKey(cellValue)) {
			PnrProcessCach.reloadUserCach();
		}
		if (!PnrProcessCach.deptUserCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列\"" + cellValue + "\"身份证号码不存在!");
		}
		return PnrProcessCach.deptUserCach.get(cellValue);
	}

	/**
	 * 
	 * @Title: checkDictName
	 * @Description: 通过缓存机制的方式校验dictName是否存在;
	 * @param row
	 * @param cellNum
	 * @param allowBlank
	 * @return void
	 * @throws
	 */
	public static String checkDictName(HSSFRow row, int cellNum,
			String parentDictId, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		Map<String, String> map = PnrProcessCach.dictMap.get(parentDictId);
		String cellValue = "";
		if (allowBlank) {
			cellValue = getCellStringValue(row, cellNum);
			if ("".equals(cellValue)) {
				return cellValue;
			}
		} else {
			cellValue = checkIsNull(row, cellNum);
		}
		if (map == null) {
			PnrProcessCach.loadDictCache(parentDictId);
			map = PnrProcessCach.dictMap.get(parentDictId);
		}
		if (map == null) {
			throw new RuntimeException("字典值\"" + parentDictId + "\"未加载成功!");
		}
		if (map.get(cellValue) == null) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列值\"" + cellValue + "\"与系统字典值不符!");
		}
		return StaticMethod.null2String(map.get(cellValue));
	}

	/**
	 * 判断工作岗位-兼职 是否为空;填写的枚举值是否正确;转换成对应的字典ID值
	 * 工作岗位兼职修改为不限专业
	 * @param row
	 * @param cellNum
	 * @param parentDictId 对应的专业类别ID
	 * @param allowBlank
	 * @return
	 */
	public static String checkOperatingPostJ(HSSFRow row, int cellNum,
			String parentDictId, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		Map<String, String> map=PnrProcessCach.operatingPostCach;
		String cellValue = "";
		if (allowBlank) {
			cellValue = getCellStringValue(row, cellNum);
			if ("".equals(cellValue)) {
				return cellValue;
			}
		} else {
			cellValue = checkIsNull(row, cellNum);
		}

		if (map == null) {
			throw new RuntimeException("工作岗位字典值未加载成功!");
		}
		// 将工作岗位-兼职转换为对应的字典ID 
		String partTimeIds = "";
		if (!cellValue.equals("") && cellValue != null) {
			String[] partTimes = cellValue.trim().split(",");//之前用#分隔的
			for (int i = 0; i < partTimes.length; i++) {
				if (map.get(partTimes[i]) == null) {
					throw new RuntimeException("第" + rowNumber + "行第"
							+ cellNumber + "列值\"" + partTimes[i] + "\"与系统字典值不符!");
				} else {
					partTimeIds += map.get(partTimes[i]) + ",";
				}
			}
			partTimeIds = partTimeIds.substring(0, partTimeIds.length() - 1);
		}
		return partTimeIds;
	}
	
	/**
	 * 获取工作岗位--兼职名称
	 * @param ids 工作岗位--兼职id
	 * @return
	 */
	public static String getOperatingPostJName(String ids) {
		String names="";	
		if(ids!=null&&!ids.equals("")){
			Map<String, String> map=PnrProcessCach.operatingPostNameCach;
			String[] tempIds = ids.trim().split(",");
			for(int i=0;i<tempIds.length;i++){
				names+=map.get(tempIds[i])+",";
			}
			names = names.substring(0, names.length() - 1);		
		}
		return names;
	}
	
	

	/**
	 * 
	 * @Title: checkDictName
	 * @Description: 通过缓存机制的方式校验dictName是否存在;
	 * @param row
	 * @param cellNum
	 * @param allowBlank
	 * @return void
	 * @throws
	 */
	public static String checkDictName(HSSFRow row, int cellNum,
			String parentDictId, String dictName, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (allowBlank) {
			if ("".equals(dictName.trim())) {
				return dictName;
			}
		} else {
			if ("".equals(dictName)) {
				throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
						+ "列值不能为空!");
			}
		}
		Map<String, String> map = PnrProcessCach.dictMap.get(parentDictId);
		if (map == null) {
			PnrProcessCach.loadDictCache(parentDictId);
			map = PnrProcessCach.dictMap.get(parentDictId);
		}
		if (map == null) {
			throw new RuntimeException("字典值\"" + parentDictId + "\"未加载成功!");
		}
		if (map.get(dictName) == null) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列值\"" + dictName + "\"与系统字典值不符!");
		}
		return StaticMethod.null2String(map.get(dictName));
	}

	/**
	 * 
	 * @Title: checkDictName
	 * @Description: 通过缓存机制的方式校验dictName是否存在;
	 * @param row
	 * @param cellNum
	 * @param allowBlank
	 * @return void
	 * @throws
	 */
	public static String dictNameToDictId(HSSFRow row, int cellNum,
			String parentDictId, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		Map<String, String> map = PnrProcessCach.dictMap.get(parentDictId);
		String cellValue = "";
		if (allowBlank) {
			cellValue = getCellStringValue(row, cellNum);
			if ("".equals(cellValue)) {
				return cellValue;
			}
		} else {
			cellValue = checkIsNull(row, cellNum);
		}
		if (map == null) {
			PnrProcessCach.loadDictCache(parentDictId);
			map = PnrProcessCach.dictMap.get(parentDictId);
		}
		if (map == null) {
			throw new RuntimeException("字典值\"" + parentDictId + "\"未加载成功!");
		}
		if (map.get(cellValue) == null) {// 避免该字典值已经加载但是中途发生修改导致无法获取字典map
			map.clear();
			PnrProcessCach.loadDictCache(parentDictId);
			map = PnrProcessCach.dictMap.get(parentDictId);
		}
		if (map.get(cellValue) == null) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列值\"" + cellValue + "\"转换字典失败!");
		}
		return StaticMethod.null2String(map.get(cellValue));
	}

	/**
	 * 
	 * @Title: checkDictName
	 * @Description: 通过缓存机制的方式校验dictName是否存在;
	 * @param row
	 * @param cellNum
	 * @param allowBlank
	 * @return void
	 * @throws
	 */
	public static String dictNameToDictId(HSSFRow row, int cellNum,
			String parentDictId, String dictName, boolean allowBlank) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		if (allowBlank) {
			if ("".equals(dictName.trim())) {
				return dictName;
			}
		} else {
			if ("".equals(dictName)) {
				throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
						+ "列值不能为空!");
			}
		}
		Map<String, String> map = PnrProcessCach.dictMap.get(parentDictId);
		if (map == null) {
			PnrProcessCach.loadDictCache(parentDictId);
			map = PnrProcessCach.dictMap.get(parentDictId);
		}
		if (map == null) {
			throw new RuntimeException("字典值\"" + parentDictId + "\"未加载成功!");
		}
		if (map.get(dictName) == null) {
			map.clear();
			PnrProcessCach.loadDictCache(parentDictId);
			map = PnrProcessCach.dictMap.get(parentDictId);
		}
		if (map.get(dictName) == null) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列值\"" + dictName + "\"转换字典失败!");
		}
		return StaticMethod.null2String(map.get(dictName));
	}

	/**
	 * 
	 * @Description：校验组织编码是否存在
	 * @date May 10, 2013 10:44:36 AM
	 * @author Fengguangping fengguangping@boco.com.cn
	 * @param row
	 * @param i
	 */
	public static String checkDeptOrgNoIsExistAndGetDeptOrgNo(HSSFRow row,
			int cellNum) {
		int rowNumber = row.getRowNum() + 1;
		int cellNumber = cellNum + 1;
		String cellValue = checkIsNull(row, cellNum);// 验证是否为空；
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			PnrProcessCach.reloadCompanyCache();
		}
		if (!PnrProcessCach.deptCompanyCach.containsKey(cellValue)) {
			throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
					+ "列中的代维公司\"" + cellValue + "\"不存在！");
		}
		return PnrProcessCach.deptCompanyCach.get(cellValue)
				.getOrganizationNo();
	}

	/**
	 * 
	 * @Description 提示“某行某列”报错位置的util
	 * @date May 10, 2013 11:01:48 AM
	 * @author Fengguangping fengguangping@boco.com.cn
	 * @param row:当前行
	 * @param cellNum：列号
	 * @param type：具体行为
	 * @return
	 */
	public static String getErrorLocationInfo(HSSFRow row, int cellNum,
			String type) {
		String error = "第" + (row.getRowNum() + 1) + "行第" + cellNum + "列"
				+ type + "出错!\n";
		return error;
	}

	/**
	 * 验证部门和班组是否合理
	 * 
	 * @author wangyue
	 * @title: checkDeptOrTeamIsNull
	 * @date Oct 11, 2014 1:53:50 PM
	 * @param row
	 * @param cellNum
	 * @return String
	 */
	public static String checkDeptOrTeamIsNull(HSSFRow row, int cellNum,
			String name) {
		int rowNumber = row.getRowNum() + 1;
		String cellValue = "";
		if (row.getCell(cellNum) != null) {
			int cellNumber = cellNum + 1;
			cellValue = getCellStringValue(row, cellNum);
			if ("".equals(name)) {
				throw new RuntimeException("第" + rowNumber + "行第" + cellNumber
						+ "列导入的字段不正确！");
			}
		} else {
			throw new RuntimeException("第" + rowNumber + "行第" + (cellNum + 1)
					+ "列导入的字段不正确！");
		}
		return cellValue;
	}
}
