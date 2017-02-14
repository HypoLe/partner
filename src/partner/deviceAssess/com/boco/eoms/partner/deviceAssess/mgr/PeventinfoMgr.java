package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.deviceAssess.model.Peventinfo;

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
 public interface PeventinfoMgr {
 
	/**
	 *
	 * 取peventinfo 列表
	 * @return 返回peventinfo列表
	 */
	public List getPeventinfos();
    
	/**
	 * 根据主键查询peventinfo
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public Peventinfo getPeventinfo(final String id);
    
	/**
	 * 保存peventinfo
	 * @param peventinfo peventinfo
	 */
	public void savePeventinfo(Peventinfo peventinfo);
    
	/**
	 * 根据主键删除peventinfo
	 * @param id 主键
	 */
	public void removePeventinfo(final String id);
    
	/**
	 * 根据条件分页查询peventinfo
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回peventinfo的分页列表
	 */
	public Map getPeventinfos(final Integer curPage, final Integer pageSize,
			final String whereStr);
			
}