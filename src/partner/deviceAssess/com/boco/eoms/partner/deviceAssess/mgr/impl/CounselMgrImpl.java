package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.deviceAssess.dao.CounselDao;
import com.boco.eoms.partner.deviceAssess.mgr.CounselMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.model.Counsel;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ExcelImport;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:咨询服务事件信息表
 * </p>
 * <p>
 * Description:咨询服务事件信息表
 * </p>
 * <p>
 * Mon Sep 27 15:01:30 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class CounselMgrImpl implements CounselMgr {
 
	private CounselDao  counselDao;
 	
	public CounselDao getCounselDao() {
		return this.counselDao;
	}
 	
	public void setCounselDao(CounselDao counselDao) {
		this.counselDao = counselDao;
	}
 	
    public List getCounsels() {
    	return counselDao.getCounsels();
    }
    
    public Counsel getCounsel(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Counsel counsel = counselDao.getCounsel(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("1122102", counsel.getId());
		counsel.setDeviceAssessApprove(daa);
    	return counsel;
    }
    
    public void saveCounsel(Counsel counsel) {
    	counselDao.saveCounsel(counsel);
    }
	public void saveDataAndApprove(Counsel counsel,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveCounsel(counsel);
		daa.setAssessId(counsel.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+counsel.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+counsel.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", counsel.getId());
		if(old != null) {
			daa.setId(old.getId());
		}
		daaMgr.saveOrUpdateApprove(daa);
	}
    
    public void removeCounsel(final String id) {
    	counselDao.removeCounsel(id);
    }
    
    public Map getCounsels(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = counselDao.getCounsels(curPage, pageSize, whereStr);
    	List<Counsel> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Counsel id : list) {
    		daa = daaMgr.getDeviceAssessApprove("1122102", id.getId());
    		id.setDeviceAssessApprove(daa);
    	}
		return map;
    }
    
    
    
    
	public ImportResult importFromFile(FormFile formFile,final Map params) throws Exception {
		ExcelImport ei = new ExcelImport(5,1,18);
		ImportResult result = ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				Counsel id = this.fromRow2Object(row);
				
				//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
				DeviceAssessApprove daa = new DeviceAssessApprove();
				daa.setAssessType("1122101");//事件类型
				daa.setSheetNum(id.getSheetNum());//工单号
				daa.setName(id.getAffairName());//名称
				daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
				daa.setApproveUser(params.get("approveUser").toString());//审批人
				daa.setClassName(Counsel.class.getSimpleName());//实体类名
				daa.setModifyUrl("/partner/deviceAssess/counsels.do?method=edit");//修改URL链接
				daa.setDetailUrl("/partner/deviceAssess/counsels.do?method=goToDetail");//详细查看URL链接
				daa.setState(2);//审批状态（0驳回 1通过 2待审批）
				saveDataAndApprove(id,daa);
		
			}
			//将每行数据构建为考核指标对象
			public Counsel fromRow2Object(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				Counsel id = new Counsel();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//工单号
				id.setSheetNum(row.getCell(1).getRichStringCellValue().getString());
				//建单时间
				id.setCreateTime(sdf.parse(sdf.format(row.getCell(2).getDateCellValue())));
				//转发时间
				id.setChangeTime(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
				//归档时间
				id.setPigeonholeTime(sdf.parse(sdf.format(row.getCell(4).getDateCellValue())));
				//事件名称
				id.setAffairName(row.getCell(5).getRichStringCellValue().getString());
				//级别 (name2Id 1121501)
				String name2Id1 = StaticMethod.null2String(row.getCell(6).getRichStringCellValue().getString());
				if(!"".equals(name2Id1)) {
					name2Id1 = this.name2Id(name2Id1,"1121501");
				}
				id.setAffairLevel(name2Id1);
				//发生省份
				id.setProvince(row.getCell(7).getRichStringCellValue().getString());
				//发生地市
				id.setCity(row.getCell(8).getRichStringCellValue().getString());
				//厂家 (name2Id 1121502)
				String name2Id2 = StaticMethod.null2String(row.getCell(9).getRichStringCellValue().getString());
				if(!"".equals(name2Id2)) {
					name2Id2 = this.name2Id(name2Id2,"1121502");
				}
				id.setFactory(name2Id2);
				//专业 (name2Id 11216)
				String name2Id3 = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
				if(!"".equals(name2Id3)) {
					name2Id3 = this.name2Id(name2Id3,"11216");
				}
				id.setSpeciality(name2Id3);
				//设备类型
				String name2Id4 = StaticMethod.null2String(row.getCell(11).getRichStringCellValue().getString());
				if(!"".equals(name2Id4)) {
					name2Id4 = this.name2Id(name2Id4,name2Id3);
				}
				id.setEquipmentType(name2Id4);
				//设备名称
				id.setEquipmentName(row.getCell(12).getRichStringCellValue().getString());
				//设备版本
				id.setEquipmentVersion(row.getCell(13).getRichStringCellValue().getString());
				//提出咨询时间
				Date date1 = row.getCell(14).getDateCellValue();
				id.setReferTime(sdf.parse(sdf.format(date1)));
				//最终反馈时间
				Date date2 = row.getCell(15).getDateCellValue();
				id.setFinallyTime(sdf.parse(sdf.format(date2)));
				//最终返回时长时间
				id.setFinallyLong(row.getCell(16).getRichStringCellValue().getString());
				//满意度
				id.setSatisfaction((int)row.getCell(17).getNumericCellValue());
				//计数
				id.setAmount(row.getCell(16).getRichStringCellValue().getString());
				id.setTotal(1);
				return id;
			}
	});
		return result;
	}
}