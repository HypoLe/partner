/**
 *
 */
package com.boco.eoms.partner.interfaces.dto;

import java.io.*;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.boco.eoms.partner.interfaces.common.init.StaticContext;

/**
 * @author mooker
 *
 */
public class ExcelStructure {
    private static Log log = LogFactory.getLog(ExcelStructure.class);
    private Map<String, ExcelStructureTable> tableMap = new HashMap<String, ExcelStructureTable>();
    public static Map<String, String> localclassidIrmsclassidMap = new HashMap<String, String>();//福诺厂家表与合作伙伴表的映射
    public static Map<String, String> irmsclassidlocalclassidMap = new HashMap<String, String>();//合作伙伴表与福诺厂家表的映射
    public static Map<String, String> fntopartnerMap = new HashMap<String, String>();
    public ExcelStructure() {
    }

    public Map getTableMap() {
        return tableMap;
    }
    
    public void readExcel() {
        try {
            log.info("开始分析[专业网管]EXCEL字段映射表定义文件");
            //指定EXCEL文件
            String filePath = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/partner.xls");
                               // "c:\\logs\\partner.xls";
            log.info("指定读取[专业网管]EXCEL字段映射表定义文件地址：" + filePath);
            log.info("-------------------------------------------");
            InputStream excelStream = new FileInputStream(filePath);
            tableMap.clear();
            try {
                HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(excelStream));
                int sheetcount = wb.getNumberOfSheets();
                for (int i = 0; i < sheetcount; i++) {
                    List errors = new ArrayList();
                    ExcelStructureTable eStructureTable = new ExcelStructureTable();
                    HSSFSheet sheet = wb.getSheetAt(i);
                    String dtoClassName = wb.getSheetName(i).trim();
                    if (dtoClassName.trim().equals("说明")) {
                        continue;
                    }else if (dtoClassName.toUpperCase().trim().equals("CLASSID")) {
                        log.info("初始化福诺class_id与合作伙伴表名映射表：int_idTableNameMap");
                        localclassidIrmsclassidMap.clear();
                        irmsclassidlocalclassidMap.clear();
                        for (int rownumber = 1; rownumber <= sheet.getLastRowNum(); rownumber++) {
                            String partner_classid = getCell(sheet, rownumber, 1).trim();
                            String fn_classid = getCell(sheet, rownumber, 0).trim();
                            localclassidIrmsclassidMap.put(fn_classid, partner_classid);
                            irmsclassidlocalclassidMap.put(partner_classid, fn_classid);
                        }
                    }else if (dtoClassName.toUpperCase().trim().equals("FNTOPARTNER")) {
                        log.info("初始化福诺值与合作伙伴值的映射表：fntopartnerMap");
                        fntopartnerMap.clear();
                        for (int rownumber = 1; rownumber <= sheet.getLastRowNum(); rownumber++) {
                            String partner_value = getCell(sheet, rownumber, 1).trim();
                            if(partner_value.indexOf("'")==0){
                            	partner_value=partner_value.substring(1,partner_value.length());
                            }
                            String fn_value = getCell(sheet, rownumber, 0).trim();
                            fntopartnerMap.put(fn_value, partner_value);
                        }
                    }else{
	                    String dtoClassName_CN = "";
	                    // 解析表中文描述
	                    if (sheet.getRow(0) != null) {
	                        dtoClassName_CN = getCell(sheet, 0, 0).trim();
	                        log.info("解析第" + (i + 1) + "个模型定义：" + dtoClassName_CN + "[" + dtoClassName + "]");
	                    } else {
	                        errors.add("EXCEL表格[" + dtoClassName + "]页定义错误，第1行没有没有定义表中文名称！");
	                    }
	                    eStructureTable.setClassName(dtoClassName);
	                    eStructureTable.setClassNameCN(dtoClassName_CN);
	                    // 解析表字段对应关系
	                    for (int rownumber = 2; rownumber <= sheet.getLastRowNum(); rownumber++) {
	                        if (sheet.getRow(rownumber) != null) {
	                            String irmsFieldName = getCell(sheet, rownumber, 4).trim(); //合作伙伴表字段
	                            if (irmsFieldName.trim().length() == 0) {
	                                continue;
	                            }
	                            String irmsFieldLabelCn = getCell(sheet, rownumber, 1).toUpperCase().trim();
	                            String irmsFieldType = getCell(sheet, rownumber, 2).trim().toUpperCase();
	                            String nmsFieldName = getCell(sheet, rownumber, 0).toUpperCase().trim();//福诺厂家表字段
	                            String nmsFieldCheck = getCell(sheet, rownumber, 5).toUpperCase().trim();
	                            String subsql = getCell(sheet, rownumber, 6).toUpperCase().trim();
	                            // 检查EXCEL数据
	                            String msg = "IRMS字段" + "[" + irmsFieldName + "]"
	                                         + "中文描述" + "[" + irmsFieldLabelCn + "]"
	                                         + "取值类型" + "[" + irmsFieldType + "]"
	                                         + "数据字段" + "[" + nmsFieldName + "]"
	                                         + "关联关系" + "[" + nmsFieldCheck + "]";
	                            log.info(msg);
	
	                            // 判断EXCEL映射表中综合资源字段名是否定义为大写
	                            checkIrmsFieldName(irmsFieldName, errors);
	                            // 判断综合资源字段类型定义是否有误
	                            checkFieldType(irmsFieldName, irmsFieldType, errors);
	                            // 判断专业网管字段名中间是否有空格
	                            checkSqlFieldName(nmsFieldName, errors);
	
	                            ExcelStructureFieldMap efm = new ExcelStructureFieldMap();
	                            efm.setIrmsFieldName(irmsFieldName);
	                            efm.setIrmsFieldLabelCn(irmsFieldLabelCn);
	                            efm.setIrmsFieldType(irmsFieldType);
	                            efm.setDatanmsFieldName(nmsFieldName);
	                            efm.setDatanmsFieldCheck(nmsFieldCheck);
	                            efm.setSub_sql(subsql);
	                            eStructureTable.getFieldMap().put(nmsFieldName, efm);
	                            eStructureTable.getPfieldMap().put(irmsFieldName, efm);
	                        }
	                    }
	                    if (errors.size() > 0) {
	                        log.error("<<<<<<<<<" + dtoClassName + "=============");
	                        for (int k = 0; k < errors.size(); k++) {
	                            log.error(errors.get(k));
	                        }
	                        log.error("=============" + dtoClassName + ">>>>>>>>>>");
	                    } else {
	                        this.tableMap.put(dtoClassName, eStructureTable);
	                    }
	                    log.info("-------------------------------------------");
                    }
                }
            } finally {
                excelStream.close();
            }
            log.info("完成分析[专业网管]EXCEL转换定义文件");
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    // 获取sheet页第row行第col列的值
    private String getCell(HSSFSheet sheet, int row, int col) {
        String cellvalue = "";
        if (sheet != null) {
            if (sheet.getRow(row) != null) {
                if (sheet.getRow(row).getCell((short) col) != null) {
                    cellvalue = sheet.getRow(row).getCell((short) col).toString();
                }
            }
        }
        return cellvalue;
    }

    //判断综合资源字段类型定义是否有误
    private void checkFieldType(String fieldName, String fieldType, List errors) {
        if (fieldType.trim().toUpperCase().equals("STRING")
            || fieldType.trim().toUpperCase().equals("INTEGER")
            || fieldType.trim().toUpperCase().equals("FLOAT")
            || fieldType.trim().toUpperCase().equals("DATE")
            || fieldType.trim().toUpperCase().equals("BOOLEAN")) {
        } else {
            errors.add("综合资源字段名[" + fieldName + "]的字段类型定义错误：" + fieldType);
        }
    }

    //判断EXCEL映射表中综合资源字段名是否定义为大写
    private void checkIrmsFieldName(String irmsFieldName, List errors) {
        if (irmsFieldName.toUpperCase().equals(irmsFieldName)) {
        } else {
            errors.add("综合资源字段名[" + irmsFieldName + "]应该定义成大写！");
        }
    }

    //判断数据网字段名中间是否有空格
    private void checkSqlFieldName(String sqlFieldName, List errors) {
        if (sqlFieldName.trim().length() > 0 && sqlFieldName.trim().indexOf(" ") > -1) {
            errors.add("网管中的字段中间不能包含空格：" + sqlFieldName);
        }
    }
    
    // 获取sheet页第row行第col列的值
    private String getCellString(HSSFSheet sheet, int row, int col) {
        String cellvalue = "";
        if (sheet != null) {
            if (sheet.getRow(row) != null) {
            	cellvalue=sheet.getRow(row).getCell(col).getRichStringCellValue().toString();
                /*if (sheet.getRow(row).getCell((short) col) != null) {
                    cellvalue = sheet.getRow(row).getCell((short) col).toString();
                }*/
            }
        }
        return cellvalue;
    }
}
