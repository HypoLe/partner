package com.boco.eoms.partner.tempTask.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskMainMgr;
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskMainDao;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskMainMgrImpl implements IPnrTempTaskMainMgr {
 
	private IPnrTempTaskMainDao  pnrTempTaskMainDao;
 	
	public IPnrTempTaskMainDao getPnrTempTaskMainDao() {
		return this.pnrTempTaskMainDao;
	}
 	
	public void setPnrTempTaskMainDao(IPnrTempTaskMainDao pnrTempTaskMainDao) {
		this.pnrTempTaskMainDao = pnrTempTaskMainDao;
	}
 	
    public List getPnrTempTaskMains() {
    	return pnrTempTaskMainDao.getPnrTempTaskMains();
    }
    
    public PnrTempTaskMain getPnrTempTaskMain(final String id) {
    	return pnrTempTaskMainDao.getPnrTempTaskMain(id);
    }
    
    public void savePnrTempTaskMain(PnrTempTaskMain pnrTempTaskMain) {
    	pnrTempTaskMainDao.savePnrTempTaskMain(pnrTempTaskMain);
    }
    
    public void removePnrTempTaskMain(final String id) {
    	pnrTempTaskMainDao.removePnrTempTaskMain(id);
    }
    
    public Map getPnrTempTaskMains(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return pnrTempTaskMainDao.getPnrTempTaskMains(curPage, pageSize, whereStr);
	}
	/** 
     * 按规则生成编号
     * type 对应模块
     * args 为其他参数
     */	
	public String creatNumber(String type,String maxNo){
		StringBuffer number = new StringBuffer();
		number.append("YNYD-");//云南移动
		number.append(type);//模块名称英文简写
		number.append("-");
		number.append(StaticMethod.getYYYYMMDD(StaticMethod.getLocalString()));//当前时间
		number.append("-");
		if(maxNo.length()==1){
			maxNo = "00"+maxNo;
		} else if (maxNo.length()==2){
			maxNo = "0"+maxNo;
		}
		number.append(maxNo);//最大编号
		return number.toString();
	}	    
}