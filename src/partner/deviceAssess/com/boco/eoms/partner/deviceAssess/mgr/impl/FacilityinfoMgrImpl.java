package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Facilityinfo;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityinfoMgr;
import com.boco.eoms.partner.deviceAssess.dao.FacilityinfoDao;

/**
 * <p>
 * Title:厂家设备问题事件信息
 * </p>
 * <p>
 * Description:厂家设备问题事件信息
 * </p>
 * <p>
 * Tue Sep 28 15:24:09 CST 2010
 * </p> 
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class FacilityinfoMgrImpl implements FacilityinfoMgr {
 
	private FacilityinfoDao  facilityinfoDao;
 	
	public FacilityinfoDao getFacilityinfoDao() {
		return this.facilityinfoDao;
	}
 	
	public void setFacilityinfoDao(FacilityinfoDao facilityinfoDao) {
		this.facilityinfoDao = facilityinfoDao;
	}
 	
    public List getFacilityinfos() {
    	return facilityinfoDao.getFacilityinfos();
    }
    
    public Facilityinfo getFacilityinfo(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Facilityinfo facilityinfo = facilityinfoDao.getFacilityinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", facilityinfo.getId());
		facilityinfo.setDeviceAssessApprove(daa);
    	return facilityinfo;
    }
    
    public void saveFacilityinfo(Facilityinfo facilityinfo) {
    	facilityinfoDao.saveFacilityinfo(facilityinfo);
    }
    
    public void removeFacilityinfo(final String id) {
    	facilityinfoDao.removeFacilityinfo(id);
    }
    
    public Map getFacilityinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = facilityinfoDao.getFacilityinfos(curPage, pageSize, whereStr);
    	List<Facilityinfo> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Facilityinfo facilityinfo : list) {
    		daa = daaMgr.getDeviceAssessApprove("", facilityinfo.getId());
    		facilityinfo.setDeviceAssessApprove(daa);
    	}
		return map;
	}

	public void saveDataAndApprove(Facilityinfo facilityinfo,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveFacilityinfo(facilityinfo);
		daa.setAssessId(facilityinfo.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+facilityinfo.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+facilityinfo.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", facilityinfo.getId());
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