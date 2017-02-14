package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceAssess.dao.SoftApplyRecordDao;
import com.boco.eoms.partner.deviceAssess.dao.jdbc.QueryEomsSheetJDBC;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.SoftApplyRecordMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.SoftApplyRecord;

/**
 * <p>
 * Title:软件申请问题记录
 * </p>
 * <p>
 * Description:软件申请问题记录
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2011
 * </p>
 *  
 * @author zhankeqi
 * @version 1.0
 * 
 */
public class SoftApplyRecordMgrImpl implements SoftApplyRecordMgr {
 
	private SoftApplyRecordDao  softApplyRecordDao;
	
	public SoftApplyRecordDao getSoftApplyRecordDao() {
		return this.softApplyRecordDao;
	}
 	
	public void setSoftApplyRecordDao(SoftApplyRecordDao softApplyRecordDao) {
		this.softApplyRecordDao = softApplyRecordDao;
	}
 	
    public List getSoftApplyRecords() {
    	return softApplyRecordDao.getSoftApplyRecords();
    }
    
    public SoftApplyRecord getSoftApplyRecord(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		SoftApplyRecord softApplyRecord = softApplyRecordDao.getSoftApplyRecord(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", softApplyRecord.getId());
		softApplyRecord.setDeviceAssessApprove(daa);
    	return softApplyRecord;
    }
    
    public void saveSoftApplyRecord(SoftApplyRecord softApplyRecord) {
    	softApplyRecordDao.saveOrUpdateSoftApplyRecord(softApplyRecord);
    }
    
    public void removeSoftApplyRecord(final String id) {
    	softApplyRecordDao.removeSoftApplyRecord(id);
    }
    
    public Map getSoftApplyRecords(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = softApplyRecordDao.getSoftApplyRecords(curPage, pageSize, whereStr);
    	List<SoftApplyRecord> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(SoftApplyRecord id : list) {
    		daa = daaMgr.getDeviceAssessApprove("", id.getId());
    		id.setDeviceAssessApprove(daa);
    	}
		return map;
	}
    
    public Map getSoftApplyRecordsWithHQL(final Integer curPage, final Integer pageSize,
    		final String hql) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
    	DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = softApplyRecordDao.getSoftApplyRecordsWithHQL(curPage, pageSize, hql);
    	List<SoftApplyRecord> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(SoftApplyRecord id : list) {
    		daa = daaMgr.getDeviceAssessApprove("", id.getId());
    		id.setDeviceAssessApprove(daa);
    	}
    	return map;
    }

	public void saveDataAndApprove(SoftApplyRecord softApplyRecord,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveSoftApplyRecord(softApplyRecord);
		daa.setAssessId(softApplyRecord.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+softApplyRecord.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+softApplyRecord.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", softApplyRecord.getId());
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
        
}