package com.boco.eoms.partner.deviceAssess.mgr.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessApproveMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FtraininfoMgr;
import com.boco.eoms.partner.deviceAssess.dao.FtraininfoDao;

/**
 * <p>
 * Title:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Description:pnr_deviceassess_ftrain_info
 * </p>
 * <p>
 * Sun Sep 26 09:11:03 CST 2010
 * </p> 
 * 
 * @author zhangxuesong
 * @version 1.0
 * 
 */
public class FtraininfoMgrImpl implements FtraininfoMgr {
 
	private FtraininfoDao  ftraininfoDao;
 	
	public FtraininfoDao getFtraininfoDao() {
		return this.ftraininfoDao;
	}
 	
	public void setFtraininfoDao(FtraininfoDao ftraininfoDao) {
		this.ftraininfoDao = ftraininfoDao;
	}
 	
    public List getFtraininfos() {
    	return ftraininfoDao.getFtraininfos();
    }
    
    public Ftraininfo getFtraininfo(final String id) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		Ftraininfo ftraininfo = ftraininfoDao.getFtraininfo(id);
		DeviceAssessApprove daa = daaMgr.getDeviceAssessApprove("", ftraininfo.getId());
		ftraininfo.setDeviceAssessApprove(daa);
    	return ftraininfo;
    	
    }
    
    public void saveFtraininfo(Ftraininfo ftraininfo) {
    	ftraininfoDao.saveFtraininfo(ftraininfo);
    }
    
    public void removeFtraininfo(final String id) {
    	ftraininfoDao.removeFtraininfo(id);
    }
    
    public Map getFtraininfos(final Integer curPage, final Integer pageSize,
			final String whereStr) {
    	ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
    	Map map = ftraininfoDao.getFtraininfos(curPage, pageSize, whereStr);
    	List<Ftraininfo> list = (List) map.get("result");
    	DeviceAssessApprove daa;
    	for(Ftraininfo ftraininfo : list) {
    		daa = daaMgr.getDeviceAssessApprove("", ftraininfo.getId());
    		ftraininfo.setDeviceAssessApprove(daa);
    	}
		return map;
	}
    
    public void saveDataAndApprove(Ftraininfo ftraininfo,
			DeviceAssessApprove daa) {
		ApplicationContext ctx = ApplicationContextHolder.getInstance().getCtx();
		DeviceAssessApproveMgr daaMgr = (DeviceAssessApproveMgr)ctx.getBean("deviceAssessApproveMgr");
		this.saveFtraininfo(ftraininfo);
		daa.setAssessId(ftraininfo.getId());//事件ID
		daa.setModifyUrl(daa.getModifyUrl()+"&id="+ftraininfo.getId());
		daa.setDetailUrl(daa.getDetailUrl()+"&id="+ftraininfo.getId());
		
		DeviceAssessApprove old = daaMgr.getDeviceAssessApprove("", ftraininfo.getId());
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