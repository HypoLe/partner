package com.boco.eoms.netresource.line.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.netresource.line.model.Lines;

/**
 * 线路管理 Excel处理方法
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 16, 2012 2:50:16 PM
* 
* @version V1.0   
*
 */

public class ExcelParse{
	
	/**
	 *  批量新增线路信息
	 * @param book
	 * @param uDao
	 */
	public List parseLines(String filePath,HttpServletRequest request){
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		System.out.println("线路信息导入,读取Excel信息开始-------------------------");
		
		List list = new ArrayList();
		
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(new File(filePath));	//"d://userdept.xls"
		} catch (BiffException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Sheet sheet = book.getSheet(0); //第1个sheet
		Lines lines = null;
		int row = sheet.getRows();
		for (int i = 1; i < row; i++) {
			if("".equals(sheet.getCell(0,i).getContents().trim())){//如果userid为空，则跳出循环
				return list;
			}
			lines= new Lines();
			try {
				lines.setLineNo(sheet.getCell(0,i).getContents().trim());
				lines.setLineName(sheet.getCell(1,i).getContents().trim());
				lines.setRemark(sheet.getCell(2,i).getContents().trim());
				lines.setMaintainType(sheet.getCell(3,i).getContents().trim());
				lines.setRegion(sheet.getCell(4,i).getContents().trim());
				lines.setCity(sheet.getCell(5,i).getContents().trim());
				lines.setGrid(sheet.getCell(6,i).getContents().trim());
				lines.setPartner(sheet.getCell(7,i).getContents().trim());
				lines.setManager(sheet.getCell(8,i).getContents().trim());
				lines.setManagerTel(sheet.getCell(9,i).getContents().trim());
				lines.setContacter(sheet.getCell(10,i).getContents().trim());
				lines.setContacterTel(sheet.getCell(11,i).getContents().trim());
				lines.setOpenTime(sheet.getCell(12,i).getContents().trim());
				lines.setUserYear(Integer.valueOf(sheet.getCell(13,i).getContents().trim()));
				lines.setBeginLong(sheet.getCell(14,i).getContents().trim());
				lines.setBeginLat(sheet.getCell(15,i).getContents().trim());
				lines.setEndLong(sheet.getCell(16,i).getContents().trim());
				lines.setEndLat(sheet.getCell(17,i).getContents().trim());
				lines.setLevel(sheet.getCell(18,i).getContents().trim());
				lines.setCreateTime(new Date());
				lines.setCreator(sessionform.getUserid());
				lines.setIsdeleted("0");
				list.add(lines);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("线路信息导入,读取Excel信息结束-------------------------");
		
		return list;
	}
	
	
	/**
	 * 把地州名称进行转换
	 * @param sysName
	 * add 2010-6-15 15:09:07
	 * @return
	 */
	public static String getExchangeStateName(String statename){
			String regionId="";
			String[][] sysName = {{"850","维护厂家"},{"850","省公司"},
					{"851","贵阳分公司"},{"852","遵义分公司"},{"853","安顺分公司"},
					{"854","黔南分公司"},{"855","黔东南分公司"},{"856","铜仁分公司"},
					{"857","毕节分公司"},{"858","六盘水分公司"},{"859","黔西南分公司"},
					{"851","贵阳代维"},{"852","遵义代维"},{"853","安顺代维"},
					{"854","黔南代维"},{"855","黔东南代维"},{"856","铜仁代维"},
					{"857","毕节代维"},{"858","六盘水代维"},{"859","黔西南代维"}};
			boolean flag = false;
			for(int i=0;i<sysName.length;i++){
			    if(statename.equals(sysName[i][1])){
			    	regionId = sysName[i][0];
			    	flag = true;
			   }
			}
			if(flag == false){
				regionId = "850";
			}
		return regionId;
	}

}




