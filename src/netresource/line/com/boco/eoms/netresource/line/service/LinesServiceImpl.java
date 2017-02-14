package com.boco.eoms.netresource.line.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.netresource.line.dao.LinesDao;
import com.boco.eoms.netresource.line.model.Lines;
import com.boco.eoms.netresource.line.util.LinesDataSaveCallback;
import com.boco.eoms.netresource.line.util.LinesExcelImport;
import com.boco.eoms.netresource.line.util.LinesImportResult;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;

/**
 * 线路管理
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

public class LinesServiceImpl implements LinesService {
	
	private LinesDao linesDao;

	public Lines getLineById(String id) {
		return linesDao.getLineById(id);
	}

	public LinesDao getLinesDao() {
		return linesDao;
	}

	public void setLinesDao(LinesDao linesDao) {
		this.linesDao = linesDao;
	}

	public String importLine(List list) {
		return linesDao.importLine(list);
	}

	public void removeLine(String id) {
		linesDao.removeLine(id);
	}

	public void saveOrUpdateLine(Lines line) {
		linesDao.saveOrUpdateLine(line);
	}

	public List searchLine(String whereStr) {
		return linesDao.searchLine(whereStr);
	}

	public Map searchLine(Integer curPage, Integer pageSize, String whereStr) {
		return linesDao.searchLine(curPage, pageSize, whereStr);
	}

	public void removeLine(String[] ids) {
		linesDao.removeLine(ids);
	}

	/**
	 * 线路信息通过Excel导入
	 */
	public LinesImportResult importFromFile(FormFile formFile, final Map params) throws Exception {
		/**
		 * @param dataStartRow  实际数据开始行(从0计数)
		 * @param dataStartCol  实际数据开始列(从0计数)
		 * @param totalCol		有效数据总列数
		 */
		LinesExcelImport ei = new LinesExcelImport(1,0,19);
		
		LinesImportResult result = ei.importFromFile(formFile, new LinesDataSaveCallback(){
			
			//获取地域rootId
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)ApplicationContextHolder.getInstance().getBean("pnrBaseAreaIdList");
		
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				HSSFCell cell;
				int colNum = 0;
				
				Lines lines = new Lines();
				
				//---------其他字段-------
				lines.setCreator(params.get("userId").toString());
				lines.setCreateTime(new Date());
				lines.setIsdeleted("0");
				
				//线路编号
				lines.setLineNo( checkIsNull( row.getCell(0).getRichStringCellValue().getString() ) );
				
				//线路名称
				lines.setLineName( checkIsNull( row.getCell(1).getRichStringCellValue().getString() ) );
				
				//线路等级
				lines.setLevel( checkNumber( String.valueOf((long)row.getCell(2).getNumericCellValue()) ) );
				
				//线路信息
				lines.setRemark( checkIsNull( row.getCell(3).getRichStringCellValue().getString() ) );
				
				//维护类型
				lines.setMaintainType( checkIsNull( row.getCell(4).getRichStringCellValue().getString() ) );
				
				//地市
				lines.setRegion( name2Id(row.getCell(5).getRichStringCellValue().getString().trim(),pnrBaseAreaIdList.getRootAreaId()) );
				
				//区县
				lines.setCity( name2Id(row.getCell(6).getRichStringCellValue().getString().trim(),pnrBaseAreaIdList.getRootAreaId()) );

				//网格
				//lines.setGrid( gridName2Id(row.getCell(7).getRichStringCellValue().getString().trim(),"xx") );
				
				//合作伙伴
				//lines.setPartner( partnerName2Id(row.getCell(8).getRichStringCellValue().getString().trim()) );
				
				//客户经理
				lines.setManager( checkIsNull ( row.getCell(9).getRichStringCellValue().getString() ) );
				
				//客户经理电话
				lines.setManagerTel( checkMobile( String.valueOf((long)row.getCell(10).getNumericCellValue()) ) );
				
				//联系人
				lines.setContacter( checkIsNull( row.getCell(11).getRichStringCellValue().getString() ) );
				
				//联系人电话
				lines.setContacterTel( checkMobile( String.valueOf((long)row.getCell(12).getNumericCellValue()) ) );
				
				//开通时间
				lines.setOpenTime( checkTime( row.getCell(13).getDateCellValue() ) );

				//使用年限
				lines.setUserYear( Integer.valueOf(checkNumber(String.valueOf((int)row.getCell(14).getNumericCellValue()))) );
				
				//起点经度
				lines.setBeginLong( checkLongLat( String.valueOf((float)row.getCell(15).getNumericCellValue()).trim() ) );
				
				//起点纬度
				lines.setBeginLat( checkLongLat( String.valueOf((float)row.getCell(16).getNumericCellValue()).trim() ) );
				
				//终点经度
				lines.setEndLong( checkLongLat( String.valueOf((float)row.getCell(17).getNumericCellValue()).trim() ) );
				
				//终点纬度
				lines.setEndLat( checkLongLat( String.valueOf((float)row.getCell(18).getNumericCellValue()).trim() ) );
				
				//误差范围
				lines.setErrorScope( checkFloat( String.valueOf((float)row.getCell(19).getNumericCellValue()).trim() ) );
				
				/** 保存本条记录 如果有提条记录失败,则全部回滚 */
				//System.out.println("------------------ "+lines.getBeginLong());
				saveOrUpdateLine(lines);
			}
		});
		return result;
	}

	/**
	 * 根据条件查询线路
	 */
	public Lines getLinesByProperty(String whereStr) {
		return linesDao.getLinesByProperty(whereStr);
	}

}
