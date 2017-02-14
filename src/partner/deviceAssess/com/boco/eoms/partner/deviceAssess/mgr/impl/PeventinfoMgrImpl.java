package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceAssess.model.Counsel;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Peventinfo;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.PeventinfoMgr;
import com.boco.eoms.partner.deviceAssess.dao.PeventinfoDao;

/**
 * <p>
 * Title:peventinfo
 * </p>
 * <p>
 * Description:peventinfo
 * </p>
 * <p>
 * Sat Sep 25 10:35:07 CST 2010
 * </p>
 *  
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class PeventinfoMgrImpl implements PeventinfoMgr {
 
	private PeventinfoDao  peventinfoDao;
 	
	public PeventinfoDao getPeventinfoDao() {
		return this.peventinfoDao;
	}
 	
	public void setPeventinfoDao(PeventinfoDao peventinfoDao) {
		this.peventinfoDao = peventinfoDao;
	}
 	
    public List getPeventinfos() {
    	return peventinfoDao.getPeventinfos();
    }
    
    public Peventinfo getPeventinfo(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Peventinfo peventinfo = peventinfoDao.getPeventinfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("1122104", peventinfo.getId());
		peventinfo.setDeviceAssessApprove(daa);
    	return peventinfo;
    }
    
    public void savePeventinfo(Peventinfo peventinfo) {
    	peventinfoDao.savePeventinfo(peventinfo);
    }
    
    public void removePeventinfo(final String id) {
    	peventinfoDao.removePeventinfo(id);
    }
    
    public Map getPeventinfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = peventinfoDao.getPeventinfos(curPage, pageSize, whereStr);
    	List<Peventinfo> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Peventinfo id : list) {
    		daa = daaMgr.getDeviceAssessApprove("1122104", id.getId());
    		id.setDeviceAssessApprove(daa);
    	}
		return map;
	}
	
}