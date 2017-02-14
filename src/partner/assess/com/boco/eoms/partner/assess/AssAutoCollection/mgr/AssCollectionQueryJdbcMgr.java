package com.boco.eoms.partner.assess.AssAutoCollection.mgr;


/**
 * <p>
 * Title:得到自动采集结果
 * </p>
 * <p>
 * Description:得到自动采集结果
 * </p>
 * <p>
 * Thu Apr 07 14:49:20 CST 2011
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public interface AssCollectionQueryJdbcMgr{
	/**
	 * 查询采集结果
	 * @param jdbcConfig jdbc配置
	 * @param username 访问数据库用户名
	 * @param password 访问数据库密码
	 * @param dbDriver 数据库驱动
	 * @param sql 执行的sql语句
	 * @return 返回采集结果的分页列表
	 */	
    public float getAssCollectionResult(String jdbcConfig ,String username,String password,String dbDriver,String sql);
    
}