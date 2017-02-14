package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.Ftraininfo;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;

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
 public interface FtraininfoMgr {
 
	/**
	 *
	 * 取pnr_deviceassess_ftrain_info 列表
	 * @return 返回pnr_deviceassess_ftrain_info列表
	 */
	public List getFtraininfos();
    
	/**
	 * 根据主键查询pnr_deviceassess_ftrain_info
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Ftraininfo getFtraininfo(final String id);
    
	/**
	 * 保存pnr_deviceassess_ftrain_info
	 * @param ftraininfo pnr_deviceassess_ftrain_info
	 */
	public void saveFtraininfo(Ftraininfo ftraininfo);
    
	/**
	 * 根据主键删除pnr_deviceassess_ftrain_info
	 * @param id 主键
	 */
	public void removeFtraininfo(final String id);
    
	/**
	 * 根据条件分页查询pnr_deviceassess_ftrain_info
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回pnr_deviceassess_ftrain_info的分页列表
	 */
	public Map getFtraininfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	public void saveDataAndApprove(Ftraininfo ftraininfo,
			DeviceAssessApprove daa);
}