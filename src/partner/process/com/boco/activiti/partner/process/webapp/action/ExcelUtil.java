package com.boco.activiti.partner.process.webapp.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.eoms.base.util.StaticMethod;

public class ExcelUtil {
	private static Logger  logger = Logger.getLogger(ExcelUtil.class);
	/**
	 * 获取配置文件的内容
	 * 
	 * @return
	 */
	public static Map getConfigByXml(String xmlPath, String id) {
		Map map = new HashMap();
		try {
			SAXReader saxReader = new SAXReader();
			URL url = ExcelUtil.class.getClassLoader().getResource(xmlPath);
			Document document = saxReader.read(url);
			List list = document.selectNodes("//cep[@id='" + id + "']");
			if (list != null && list.size() > 0) {
				Element element = (Element) list.get(0);
				if (element != null) {
					List sublist = element.elements();
					if (sublist != null && sublist.size() > 0) {
						for (int i = 0; i < sublist.size(); i++) {
							Element subElement = (Element) sublist.get(i);
							String subName = subElement.getName();
							List subElementList = subElement.elements();
							if (subElementList != null && subElementList.size() > 0) {
								if(subName.equals("columnName")){
									List subSubList = new ArrayList();
									for (int j = 0; j < subElementList.size(); j++) {
										Element subSubElement = (Element) subElementList.get(j);
										String value = subSubElement.getTextTrim();
										if (value != null && !value.equals("")) {
											String[] subStrArr = new String[2];
											subStrArr[0] = subSubElement.getName();
											subStrArr[1] = value;
											subSubList.add(subStrArr);
										}
									}
									map.put(subName, subSubList);
								}else{
									Map subMap = new HashMap();
									for (int j = 0; j < subElementList.size(); j++) {
										Element subSubElement = (Element) subElementList.get(j);
										String value = subSubElement.getTextTrim();
										if (value != null && !value.equals("")) {
											subMap.put(subSubElement.getName(),subSubElement.getTextTrim());
										}
									}
									map.put(subName, subMap);
								}
							} else {
								map.put(subName, subElement.getTextTrim());
							}
						}
					}
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return map;
	}

	/**
	 * 获取缓存配置文件的内容
	 * 
	 * @return
	 */
	public static Map getCacheConfigByXml(String xmlPath) {
		Map cacheMap = new HashMap();
		try {
			SAXReader saxReader = new SAXReader();
			URL url = ExcelUtil.class.getClassLoader().getResource(xmlPath);
			Document document = saxReader.read(url);
			List list = document.selectNodes("//cache");
			if (list != null && list.size() > 0) {
				for (int k = 0; k < list.size(); k++) {
					Element element = (Element) list.get(k);
					if (element != null) {
						Map map = new HashMap();
						List sublist = element.elements();
						if (sublist != null && sublist.size() > 0) {
							for (int i = 0; i < sublist.size(); i++) {
								Element subElement = (Element) sublist.get(i);
								List subElementList = subElement.elements();
								if (subElementList != null&& subElementList.size() > 0) {
									Map subMap = new HashMap();
									for (int j = 0; j < subElementList.size(); j++) {
										Element subSubElement = (Element) subElementList.get(j);
										String value = subSubElement.getTextTrim();
										if (value != null && !value.equals("")) {
											subMap.put(subSubElement.getName(),subSubElement.getTextTrim());
										}
									}
									map.put(subElement.getName(), subMap);
								} else {
									map.put(subElement.getName(), subElement.getTextTrim());
								}
							}
						}
						String name = element.attributeValue("name");
						cacheMap.put(name, map);
					}
				}
			}
		} catch (Exception err) {
			err.printStackTrace();
		}
		return cacheMap;
	}

	/**
	 * 判断字段是否需要id2name
	 * 
	 * @param columnName
	 * @param value
	 * @param id2NameMap
	 * @param cacheManager
	 * @return
	 */
	public static String id2NameColumn(String columnName, String columnValue,
			Map id2NameMap, CacheManager cacheManager) {
		String realValue = columnValue;
		if (id2NameMap != null && id2NameMap.size() > 0) {
			Iterator it = id2NameMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = StaticMethod.nullObject2String(id2NameMap.get(key));
				if (value != null && !value.equals("")) {
					// 需要根据id获取值
					String[] valueArr = value.split(",");
					for (int i = 0; i < valueArr.length; i++) {
						if (columnName.equalsIgnoreCase(valueArr[i])) {
							// 从缓存中获取
							Cache cache = cacheManager.getCache(key);
//							System.out.println("cache value:"+cache);
							if (cache != null) {
								net.sf.ehcache.Element element = cache.get(columnValue);
								if (element != null&& element.isExpired() == false) {
									realValue = StaticMethod.nullObject2String(element.getValue());
//									System.out.println("dictid:"+columnValue+" value:"+realValue);
								}
							}
							return realValue;
						}
					}
				}
			}
		}
		return realValue;
	}

	public static void ExportExcel(HashMap result, String[] fieldName,
			List sheetNameList, OutputStream output) {
		// 产生工作薄对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 为了工作表能支持中文,设置字符集为UTF_16
		Iterator it = sheetNameList.iterator();
		String sheetName = "";
		int num = 0;
		while (it.hasNext()) {
			sheetName = (String) it.next();
			List ObjectArray = (List) result.get(sheetName);
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(num, sheetName);
			num++;
			// 产生一行
			HSSFRow row = sheet.createRow(0);
			// 产生单元格
			HSSFCell cell;
			// 写入各个字段的名称
			for (int i = 0; i < fieldName.length; i++) {
				// 创建第一行各个字段名称的单元格
				cell = row.createCell((short) i);
				// 设置单元格内容为字符串型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 给单元格内容赋值
				cell.setCellValue(new HSSFRichTextString(fieldName[i]));
			}

			// 写入各条记录,每条记录对应excel表中的一行
			// 循环遍历 list 对象 list中放置的也是list对象
			for (int i = 0; i < ObjectArray.size(); i++) {
				List tmp = ObjectArray;
				row = sheet.createRow(i + 1);
				for (int j = 0; j < tmp.size(); j++) {
					cell = row.createCell((short) j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// System.out.println(ObjectArray[i][j]);
					cell.setCellValue(((DownWorkOrderModel)tmp.get(j)).getProcessInstanceId());
				}
			}
		}
		try {
			workbook.write(output);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Output is closed");
		}finally{//合并所有附件到一个excel中
			System.out.println("所有附件已经写入完毕，准备合并!");
		}
	}
	/**
	 * 导出csv
	 * @param mapList
	 * @param pageIndex
	 * @param configMap
	 * @param message
	 * @return
	 */
	
}
