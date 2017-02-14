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
import com.boco.eoms.partner.deviceAssess.dao.BigFaultDao;
import com.boco.eoms.partner.deviceAssess.mgr.BigFaultMgr;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.model.BigFault;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ExcelImport;
import com.boco.eoms.partner.deviceAssess.util.excelimport.ImportResult;

/**
 * <p>
 * Title:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Description:厂家设备重大故障事件信息
 * </p>
 * <p>
 * Mon Sep 27 13:45:23 CST 2010 
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class BigFaultMgrImpl implements BigFaultMgr {
 
	private BigFaultDao  bigFaultDao;
 	
	public BigFaultDao getBigFaultDao() {
		return this.bigFaultDao;
	}
 	
	public void setBigFaultDao(BigFaultDao bigFaultDao) {
		this.bigFaultDao = bigFaultDao;
	}
 	
    public List getBigFaults() {
    	return bigFaultDao.getBigFaults();
    }
    
    public BigFault getBigFault(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		BigFault bigFault = bigFaultDao.getBigFault(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", bigFault.getId());
		bigFault.setDeviceAssessApprove(daa);
    	return bigFault;
    }
    
    public void saveBigFault(BigFault bigFault) {
    	bigFaultDao.saveBigFault(bigFault);
    }
    
    public void removeBigFault(final String id) {
    	bigFaultDao.removeBigFault(id);
    }
    
    public Map getBigFaults(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = bigFaultDao.getBigFaults(curPage, pageSize, whereStr);
    	List<BigFault> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(BigFault bigFault : list) {
    		daa = daaMgr.getDeviceAssessApprove("", bigFault.getId());
    		bigFault.setDeviceAssessApprove(daa);
    	}
		return map;
	}

	public void saveDataAndApprove(BigFault bigFault, DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveBigFault(bigFault);
		daa.setAssessId(bigFault.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+bigFault.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+bigFault.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", bigFault.getId());
		if(old != null) {
			try {
				String oldId = old.getId();
				BeanUtils.copyProperties(old, daa);
				old.setId(oldId);
				daaMgr.saveOrUpdateApprove(old);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			daaMgr.saveOrUpdateApprove(daa);
		}
	}
	
	public ImportResult importFromFile(FormFile formFile, final Map params) throws Exception {
		ExcelImport ei = new ExcelImport(5,1,18);
		ImportResult result = ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				BigFault bigFault = this.fromRow2Object(row);
				bigFault.setTotal(1);
				
				//设置审批信息(事件ID在保存了insideDispose生成主键后设置)
				DeviceAssessApprove daa = new DeviceAssessApprove();
				daa.setAssessType("1122105");//事件类型
				daa.setSheetNum(bigFault.getSheetNum());//工单号
				daa.setName(bigFault.getBigFaultName());//名称
				daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
				daa.setApproveUser(params.get("approveUser").toString());//审批人
				daa.setClassName(BigFault.class.getSimpleName());//实体类名
				daa.setModifyUrl("/partner/deviceAssess/bigFaults.do?method=edit");//修改URL链接
				daa.setDetailUrl("/partner/deviceAssess/bigFaults.do?method=goToDetail");//详细查看URL链接
				daa.setState(2);//审批状态（0驳回 1通过 2待审批）
				
				saveDataAndApprove(bigFault,daa);
			}
			//将每行数据构建为考核指标对象
			public BigFault fromRow2Object(HSSFRow row) throws Exception {
				SimpleDateFormat sdf = null;
				HSSFCell cell;
				int colNum = 0;
				BigFault bigFault = new BigFault();
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//工单号
				bigFault.setSheetNum(row.getCell(1).getRichStringCellValue().getString());
				//建单时间
				bigFault.setCreateTime(sdf.parse(sdf.format(row.getCell(2).getDateCellValue())));
				//归档时间
				bigFault.setPigeonholeTime(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
				//事件名称
				bigFault.setBigFaultName(row.getCell(4).getRichStringCellValue().getString());
				//重大故障定义编号
				bigFault.setBigFaultNo(row.getCell(5).getRichStringCellValue().getString());
				//发生省份
				bigFault.setProvince(row.getCell(6).getRichStringCellValue().getString());
				//发生地市
				bigFault.setCity(row.getCell(7).getRichStringCellValue().getString());
				//厂家 (name2Id 1121502)
				String name2Id2 = StaticMethod.null2String(row.getCell(8).getRichStringCellValue().getString());
				if(!"".equals(name2Id2)) {
					name2Id2 = this.name2Id(name2Id2,"1121502");
				}
				bigFault.setFactory(name2Id2);
				//专业 (name2Id 11216)
				String name2Id3 = StaticMethod.null2String(row.getCell(9).getRichStringCellValue().getString());
				if(!"".equals(name2Id3)) {
					name2Id3 = this.name2Id(name2Id3,"11216");
				}
				bigFault.setSpeciality(name2Id3);
				//设备类型
				String name2Id4 = StaticMethod.null2String(row.getCell(10).getRichStringCellValue().getString());
				if(!"".equals(name2Id4)) {
					name2Id4 = this.name2Id(name2Id4,name2Id3);
				}
				bigFault.setEquipmentType(name2Id4);
				//设备名称
				bigFault.setEquipmentName(row.getCell(11).getRichStringCellValue().getString());
				//设备版本
				bigFault.setEquipmentVersion(row.getCell(12).getRichStringCellValue().getString());
				//故障开始时间
				Date date1 = row.getCell(13).getDateCellValue();
				bigFault.setBeginTime(sdf.parse(sdf.format(date1)));
				//业务恢复时间
				Date date2 = row.getCell(14).getDateCellValue();
				bigFault.setResumeTime(sdf.parse(sdf.format(date2)));
				//故障消除时间
				Date date3 = row.getCell(15).getDateCellValue();
				bigFault.setRemoveTime(sdf.parse(sdf.format(date3)));
				//业务恢复时长
				bigFault.setResumeLong(this.dateSubtract(date2, date1));
				//故障处理时长
				bigFault.setFaultLong(this.dateSubtract(date3, date1));
				//计数
				bigFault.setAmount((int)row.getCell(18).getNumericCellValue());
				
				return bigFault;
			}
	});
		return result;
	}
	
}