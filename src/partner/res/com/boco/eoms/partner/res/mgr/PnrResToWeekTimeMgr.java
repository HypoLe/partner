package com.boco.eoms.partner.res.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.res.model.PnrResSetWeekTime;
import com.boco.eoms.partner.res.model.PnrResToWeekTime;

/** 
 *
 * @author:     chenbing 
 * 
 */ 
public interface PnrResToWeekTimeMgr extends CommonGenericService<PnrResToWeekTime> {
	/**
	 * 通过资源级别ID（pnrId） 获取PnrResToWeekTime
	 * @param pnrId
	 * @return List
	 */
	public  List<PnrResToWeekTime> getPnrResToWeekTimeByPnrId(String pnrId);
	/**
	 * 根据资源级别ID（pnrId） 删除PnrResToWeekTime
	 * @param pnrId
	 * @return int
	 */
	public  int removeByPnrId(String pnrId);
	/**
	 * 通过字典ID（dictId），及所选的级别（1:巡检专业，2：资源级别，3：资源类别） 查找相关信息
	 * @param dictId
	 * @param level
	 * @param assign  是否已设置
	 * @return  List
	 *//*
	public  List<PnrResSetWeekTime> getTawSystemDictTypeByDictId(String dictId,int level,String assign);*/
	/**
	 * 通过字典的上级ID（parentId） 获取下级信息
	 * @param parentId
	 * @return Map
	 */
	public  Map<String,String> getTawSystemDictTypeByParentID(String parentId);
	/**
	 * 通过传进的List<TawSystemDictType> 返回List<PnrResSetWeekTime>
	 * @param list
	 * @return list
	 */	
	public List<PnrResSetWeekTime> getTawSystemDictTypeByDictId(List<TawSystemDictType> list);
}
