package com.boco.eoms.netresource.point.mgr.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.netresource.line.util.LinesDataSaveCallback;
import com.boco.eoms.netresource.line.util.LinesExcelImport;
import com.boco.eoms.netresource.line.util.LinesImportResult;
import com.boco.eoms.netresource.point.model.Points;
import com.boco.eoms.netresource.point.mgr.IPointsMgr;
import com.boco.eoms.netresource.point.dao.IPointsDao;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;

/**
 * <p>
 * Title:标点信息管理
 * </p>
 * <p>
 * Description:标点信息管理
 * </p>
 * <p>
 * Thu Feb 16 18:22:16 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class PointsMgrImpl implements IPointsMgr {
 
    private IPointsDao  pointsDao;

    public IPointsDao getPointsDao() {
        return this.pointsDao;
    }

    public void setPointsDao(IPointsDao pointsDao) {
        this.pointsDao = pointsDao;
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsMgr#getPointss()
     *      
     */
    public List getPointss() {
        return pointsDao.getPointss();
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsMgr#getPoints(java.lang.String)
     *      
     */
    public Points getPoints(final String id) {
        return pointsDao.getPoints(id);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsMgr#savePoints(com.boco.eoms.netresource.point.Points)
     *      
     */
    public void savePoints(Points points) {
        pointsDao.savePoints(points);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsMgr#removePoints(java.lang.String)
     *      
     */
    public void removePoints(final String id) {
        pointsDao.removePoints(id);
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsMgr#removePoints(java.lang.String[])
     *      
     */
    public void removePoints(final String[] ids) {
        if (null != ids) {
            for (int i = 0; i < ids.length; i++) {
                this.removePoints(ids[i]);
            }
        }
    }

    /**
     * 
     * @see com.boco.eoms.netresource.point.IPointsMgr#getPointss(java.lang.Integer,java.lang.Integer,java.lang.String)
     *      
     */
    public Map getPointss(final Integer curPage, final Integer pageSize,
            final String whereStr) {
        return pointsDao.getPointss(curPage, pageSize, whereStr);
    }
    
    /**
     * 标点信息Excel批量导入
     */
	public LinesImportResult importFromFile(FormFile formFile, final Map params) throws Exception {
		/**
		 * @param dataStartRow  实际数据开始行(从0计数)
		 * @param dataStartCol  实际数据开始列(从0计数)
		 * @param totalCol		有效数据总列数
		 */
		LinesExcelImport ei = new LinesExcelImport(1,0,12);
		
		LinesImportResult result = ei.importFromFile(formFile, new LinesDataSaveCallback(){
			
			//获取地域rootId
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)ApplicationContextHolder.getInstance().getBean("pnrBaseAreaIdList");
		
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				
				Points points = new Points();
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				//--------其他字段--------
				points.setCreator(params.get("userId").toString());
				points.setCreateTime(new Date());
				points.setIsdeleted("0");
				
				//标点编号
				points.setPointNo( checkIsNull( row.getCell(0).getRichStringCellValue().getString() ) );
				
				//表现名称
				points.setPointName( checkIsNull( row.getCell(1).getRichStringCellValue().getString() ) );
				
				//地市
				points.setRegion( name2Id(row.getCell(2).getRichStringCellValue().getString().trim(),pnrBaseAreaIdList.getRootAreaId()) );
				
				//区县
				points.setCity( name2Id(row.getCell(3).getRichStringCellValue().getString().trim(),pnrBaseAreaIdList.getRootAreaId()) );
				
				//网格
				//points.setGrid( gridName2Id(row.getCell(4).getRichStringCellValue().getString().trim(),"xx") );
				
				//线路
				points.setLine( lineName2Id(row.getCell(5).getRichStringCellValue().getString().trim()) );
				
				//专业类型
				points.setSpecialtyType( checkIsNull( row.getCell(6).getRichStringCellValue().getString() ) );
				
				//承载业务
				points.setLoadwork( checkIsNull( row.getCell(7).getRichStringCellValue().getString() ) );
				
				//所属集团客户
				points.setGroupUser( checkIsNull( row.getCell(8).getRichStringCellValue().getString() ) );
				
				//所属集团客户编码
				points.setGroupUserNo( checkIsNull( row.getCell(9).getRichStringCellValue().getString() ) );
				
				//标点经度
				points.setLongitude( checkLongLat( String.valueOf((float)row.getCell(10).getNumericCellValue()).trim() ) );
				
				//标点纬度
				points.setLatitude( checkLongLat( String.valueOf((float)row.getCell(11).getNumericCellValue()).trim() ) );
				
				//误差范围
				points.setErrorScope( checkFloat( String.valueOf((float)row.getCell(12).getNumericCellValue()).trim() ) );
				
				/** 保存本条记录 如果有提条记录失败,则全部回滚 */
				//System.out.println("------------------ "+points.getPointName());
				savePoints(points);
			}
		});
		return result;
	}

	/**
	 * 根据条件查询
	 */
	public List getPointsByProperty(String whereStr) {
		return pointsDao.getPointsByProperty(whereStr);
	}
    
    
}