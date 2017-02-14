package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.model.Lserveinfo;
import com.boco.eoms.partner.deviceAssess.model.Repairinfo;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.RepairinfoMgr;
import com.boco.eoms.partner.deviceAssess.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ExcelImport;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;
import com.boco.eoms.partner.deviceAssess.dao.RepairinfoDao;

/**
 * <p>
 * Title:板件返修事件信息
 * </p>
 * <p>
 * Description:板件返修事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p>
 * 
 * @author zhangxuesong 
 * @version 1.0
 * 
 */
public class RepairinfoMgrImpl implements RepairinfoMgr {
 
	private RepairinfoDao  repairinfoDao;
 	
	public RepairinfoDao getRepairinfoDao() {
		return this.repairinfoDao;
	}
 	
	public void setRepairinfoDao(RepairinfoDao repairinfoDao) {
		this.repairinfoDao = repairinfoDao;
	}
 	
    public List getRepairinfos() {
    	return repairinfoDao.getRepairinfos();
    }
    
    public Repairinfo getRepairinfo(final String id) {
    	
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Repairinfo repairinfo = repairinfoDao.getRepairinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", repairinfo.getId());
		repairinfo.setDeviceAssessApprove(daa);
    	return repairinfo;
    }
    
    public void saveRepairinfo(Repairinfo repairinfo) {
    	repairinfoDao.saveRepairinfo(repairinfo);
    }
    
    public void removeRepairinfo(final String id) {
    	repairinfoDao.removeRepairinfo(id);
    }
    
    public Map getRepairinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = repairinfoDao.getRepairinfos(curPage, pageSize, whereStr);
    	List<Repairinfo> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Repairinfo repairinfo : list) {
    		daa = daaMgr.getDeviceAssessApprove("1122108", repairinfo.getId());
    		repairinfo.setDeviceAssessApprove(daa);
    	}
		return map;
		
	}
    
    public Repairinfo getRepairinfos(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Repairinfo repairinfo = repairinfoDao.getRepairinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("1122108", repairinfo.getId());
		repairinfo.setDeviceAssessApprove(daa);
    	return repairinfo;
    }
    public void saveDataAndApprove(Repairinfo repairinfo,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveRepairinfo(repairinfo);
		daa.setAssessId(repairinfo.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+repairinfo.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+repairinfo.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", repairinfo.getId());
		if(old != null) {
			try {
				String oldId = old.getId();
				BeanUtils.copyProperties(old, daa);
				old.setId(oldId);
				daaMgr.saveOrUpdateApprove(old);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			daaMgr.saveOrUpdateApprove(daa);
		}
	}
    
    public ImportResult importFromFile(FormFile formFile,final Map params) throws Exception {
		ExcelImport ei = new ExcelImport(5,1,17);
		ImportResult result = ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				Repairinfo repairinfo = this.fromRow2Object(row);
				repairinfo.setTotal(1);
				repairinfo.setTakeCountOf(1);
				//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
				DeviceAssessApprove daa = new DeviceAssessApprove();
				daa.setAssessType("1122108");//事件类型
				daa.setSheetNum(repairinfo.getSheetNum());//工单号
				daa.setName(repairinfo.getAffairName());//名称
				daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
				daa.setApproveUser(params.get("approveUser").toString());//审批人
				daa.setClassName(Repairinfo.class.getSimpleName());//实体类名
				daa.setModifyUrl("/partner/deviceAssess/repairinfos.do?method=edit");//修改URL链接
				daa.setDetailUrl("/partner/deviceAssess/repairinfos.do?method=goToDetail");//详细查看URL链接
				daa.setState(2);//审批状态（0驳回 1通过 2待审批）
				
				saveDataAndApprove(repairinfo,daa);
			}
			//将每行数据构建为考核指标对象
			public Repairinfo fromRow2Object(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				Repairinfo repairinfo = new Repairinfo();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//工单号
				repairinfo.setSheetNum(row.getCell(1).getRichStringCellValue().getString());
				//建单时间
				repairinfo.setCreateTime(sdf.parse(sdf.format(row.getCell(2).getDateCellValue())));
				//归档时间
				repairinfo.setPigeonholeTime(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
				//事件名称
				repairinfo.setAffairName(row.getCell(4).getRichStringCellValue().getString());
				//级别 (name2Id 1121501)
				String name2Id1 = StaticMethod.null2String(row.getCell(5).getRichStringCellValue().getString());
				if(!"".equals(name2Id1)) {
					name2Id1 = this.name2Id(name2Id1,"1121501");
				}
				repairinfo.setAffairLevel(name2Id1);
				//发生省份
				repairinfo.setProvince(row.getCell(6).getRichStringCellValue().getString());
				//发生地市
				repairinfo.setCity(row.getCell(7).getRichStringCellValue().getString());
				//厂家 (name2Id 1121502)
				String name2Id2 = StaticMethod.null2String(row.getCell(8).getRichStringCellValue().getString());
				if(!"".equals(name2Id2)) {
					name2Id2 = this.name2Id(name2Id2,"1121502");
				}
				repairinfo.setFactory(name2Id2);
				//专业 (name2Id 11216)
				String name2Id3 = StaticMethod.null2String(row.getCell(9).getRichStringCellValue().getString());
				if(!"".equals(name2Id3)) {
					name2Id3 = this.name2Id(name2Id3,"11216");
				}
				repairinfo.setSpeciality(name2Id3);
				//设备类型
				String name2Id4 = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
				if(!"".equals(name2Id4)) {
					name2Id4 = this.name2Id(name2Id4,name2Id3);
				}
				repairinfo.setEquipmentType(name2Id4);
				//设备名称
				repairinfo.setEquipmentName(row.getCell(11).getRichStringCellValue().getString());
				//设备版本
				repairinfo.setEquipmentVersion(row.getCell(12).getRichStringCellValue().getString());
                //板块数量
				repairinfo.setBlockNum((int)row.getCell(13).getNumericCellValue());
				//送修时间
				Date date1 = row.getCell(14).getDateCellValue();
				repairinfo.setRepairTime(sdf.parse(sdf.format(date1)));
				//返回时间
				Date date2 = row.getCell(15).getDateCellValue();
				repairinfo.setReturnTime(sdf.parse(sdf.format(date2)));
				//送修时长
				repairinfo.setRepairLong((int)row.getCell(16).getNumericCellValue());
                //计数
				repairinfo.setTakeCountOf((int)row.getCell(17).getNumericCellValue());
			
				//送修时长(小时)
				repairinfo.setRepairLongHour(this.dateSubtract(date2, date1));
				
				return repairinfo;
			}
	});
		return result;
	}    
}