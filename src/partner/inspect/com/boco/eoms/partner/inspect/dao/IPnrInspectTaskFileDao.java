package com.boco.eoms.partner.inspect.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;

/**
 * 
 * <p>
 * Title:属地化巡检文件上传（Base64转码）Dao接口
 * </p>
 * <p>
 * Description:属地化巡检文件上传（Base64转码）Dao接口
 * </p>
 * <p>
 * Date:2012-08-20 上午08:29:50
 * </p>
 * 
 * @author lee
 * @version 1.0
 * 
 */
public interface IPnrInspectTaskFileDao extends Dao {

	void save(PnrInspectTaskFile taskFile, String data) throws SQLException;
	public List findByHQL(String hql);
	
	public Map getResources(Integer curPage, Integer pageSize,
			final String whereStr);
}