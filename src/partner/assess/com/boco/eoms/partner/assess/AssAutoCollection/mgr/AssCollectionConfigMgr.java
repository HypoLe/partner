package com.boco.eoms.partner.assess.AssAutoCollection.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;

/**
 * <p>
 * Title:采集配置
 * </p>
 * <p>
 * Description:采集配置
 * </p>
 * <p>
 * Thu Mar 31 09:11:04 CST 2011
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
 public interface AssCollectionConfigMgr {
 
	/**
	 *
	 * 取采集配置 列表
	 * @return 返回采集配置列表
	 */
	public List getAssCollectionConfigs();
    
	/**
	 * 根据主键查询采集配置
	 * @param id 主键
	 * @return 返回某id的对象
	 */
	public AssCollectionConfig getAssCollectionConfig(final String id);
    
	/**
	 * 保存采集配置
	 * @param assCollectionConfig 采集配置
	 */
	public void saveAssCollectionConfig(AssCollectionConfig assCollectionConfig);
    
	/**
	 * 根据主键删除采集配置
	 * @param id 主键
	 */
	public void removeAssCollectionConfig(final String id);
    
	/**
	 * 根据条件分页查询采集配置
	 * @param curPage 当前页
	 * @param pageSize 每页包含记录条数
	 * @param whereStr 查询条件
	 * @return 返回采集配置的分页列表
	 */
	public Map getAssCollectionConfigs(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
    /**
     * 根据节点Id查询采集类型
     * @param nodeId 对应树节点主键
     * @return 返回对应树节点主键的采集类型
     */  
	public AssCollectionConfig getAssCollectionConfigByNodeId(final String nodeId) ;  		
}