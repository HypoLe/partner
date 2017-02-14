package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.model.Softupinfo;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.SoftupinfoMgr;
import com.boco.eoms.partner.deviceAssess.dao.SoftupinfoDao;

/**
 * <p>
 * Title:软件升级事件信息
 * </p>
 * <p>
 * Description:软件升级事件信息
 * </p>
 * <p>
 * Mon Sep 27 11:46:52 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class SoftupinfoMgrImpl implements SoftupinfoMgr {
 
	private SoftupinfoDao  softupinfoDao;
 	
	public SoftupinfoDao getSoftupinfoDao() {
		return this.softupinfoDao;
	}
 	
	public void setSoftupinfoDao(SoftupinfoDao softupinfoDao) {
		this.softupinfoDao = softupinfoDao;
	}
 	
    public List getSoftupinfos() {
    	return softupinfoDao.getSoftupinfos();
    }
    
    public Softupinfo getSoftupinfo(final String id) {
    	
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Softupinfo softupinfo = softupinfoDao.getSoftupinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", softupinfo.getId());
		softupinfo.setDeviceAssessApprove(daa);
    	return softupinfo;
   
    }
    
    public void saveSoftupinfo(Softupinfo softupinfo) {
    	softupinfoDao.saveSoftupinfo(softupinfo);
    }
    
    public void removeSoftupinfo(final String id) {
    	softupinfoDao.removeSoftupinfo(id);
    }
    
    public Map getSoftupinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = softupinfoDao.getSoftupinfos(curPage, pageSize, whereStr);
    	List<Softupinfo> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Softupinfo softupinfo : list) {
    		daa = daaMgr.getDeviceAssessApprove("1122109", softupinfo.getId());
    		softupinfo.setDeviceAssessApprove(daa);
    	}
		return map;
	}
    public Softupinfo getSoftupinfos(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Softupinfo softupinfo = softupinfoDao.getSoftupinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("1122109", softupinfo.getId());
		softupinfo.setDeviceAssessApprove(daa);
    	return softupinfo;
    }
    public void saveDataAndApprove(Softupinfo softupinfo,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveSoftupinfo(softupinfo);
		daa.setAssessId(softupinfo.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+softupinfo.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+softupinfo.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", softupinfo.getId());
		
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
}