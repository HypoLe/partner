package com.boco.eoms.netresource.line.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.netresource.line.model.Lines;

/**
 * 线路管理
* @Title: 
* 
* @Description: TODO
* 
* @author WangGuangping  
* 
* @date Feb 16, 2012 2:50:16 PM
* 
* @version V1.0   
*
 */

public interface LinesDao{
	
	/**
	 * 新增/修改线路
	 * @param line
	 */
	public void saveOrUpdateLine(Lines line);
	
	/**
	 * 删除线路	将0 修改为 1
	 * @param id
	 */
	public void removeLine(String id);
	
	/**
	 * 删除线路	将0 修改为 1
	 * @param id
	 */
	public void removeLine(String[] ids);
	
	/**
	 * 查询线路
	 * @return
	 */
	public List searchLine(String whereStr);
	
	/**
	 * 分页查询线路
	 * @return
	 */
	public Map searchLine(final Integer curPage, final Integer pageSize,
			final String whereStr);
	
	/**
	 * 详情查看
	 * @param id
	 * @return
	 */
	public Lines getLineById(String id);
	
	/**
	 * 导入线路
	 * @param list
	 * @return 导入成功与否的信息
	 */
	public String importLine(List list);
	
	/**
	 * 根据条件查询线路
	 * @param whereStr
	 * @return
	 */
	public Lines getLinesByProperty(String whereStr);

}
