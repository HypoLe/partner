package com.boco.eoms.netresource.line.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
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

public class LinesDaoImpl extends BaseDaoHibernate implements LinesDao , ID2NameDAO{

	/**
	 * 删除线路信息 假删除
	 */
	public void removeLine(String id) {
		if (id != null && !"".equals(id)) {
			Lines line = getLineById(id);
			if(null != line){
				line.setIsdeleted("1");
				saveOrUpdateLine(line);
			}
		}
	}
	
	/**
	 * 删除线路信息 假删除
	 */
	public void removeLine(String[] ids) {
		for(int i=0;i<ids.length;i++){
			if (ids[i] != null && !"".equals(ids[i])) {
				this.removeLine(ids[i]);
			}
		}
	}
	
	/**
	 * 保存或更新
	 */
	public void saveOrUpdateLine(Lines lines) {
		if (lines.getId() == null || "".equals(lines.getId())) {
			getHibernateTemplate().save(lines);
		} else {
			getHibernateTemplate().saveOrUpdate(lines);
		}
	}
	
	/**
	 * 查询
	 */
	public List searchLine(String whereStr) {
		String queryStr = "FROM Lines lines WHERE 1=1 AND isdeleted = '0' ";
        
    	if(whereStr.length() > 0 && null != whereStr){
    		queryStr += whereStr;
    		queryStr += " order by createTime desc ";
    	}else{
    		queryStr += " ORDER BY createTime DESC ";
    	}
    	
    	return (List)getHibernateTemplate().find(queryStr);
	}

	/**
	 * 明细
	 */
	public Lines getLineById(String id) {
		String hql = "FROM Lines lines WHERE 1=1 AND id= '"+id+"' " ;
		List list = (List)getHibernateTemplate().find(hql);
		if(null == list || list.isEmpty() || "".equals(list)){
			return null;
		}
		return (Lines)list.get(0);
		
	}

	/**
	 * 分页查询
	 */
	public Map searchLine(final Integer curPage, final Integer pageSize, final String whereStr) {
		  HibernateCallback callback = new HibernateCallback() {
	            public Object doInHibernate(Session session)
	                    throws HibernateException {
	                String queryStr = "FROM Lines lines  WHERE 1=1 AND isdeleted = '0' ";
	                if (whereStr != null && whereStr.length() > 0)
	                    queryStr += whereStr;
	                queryStr += " order by createTime desc ";
	                
	                String queryCountStr = "select count(*) FROM Lines lines WHERE isdeleted = '0' ";
	                if (whereStr != null && whereStr.length() > 0)
	                	queryCountStr += whereStr;

	                int total = ((Integer) session.createQuery(queryCountStr)
	                        .iterate().next()).intValue();
	                Query query = session.createQuery(queryStr);
	                query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
	                query.setMaxResults(pageSize.intValue());
	                List result = query.list();
	                HashMap map = new HashMap();
	                map.put("total", new Integer(total));
	                map.put("result", result);
	                return map;
	            }
	        };
	        return (Map) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 导入
	 */
	public String importLine(List list) {
		return null;
	}

	/**
	 * 根据条件查询线路
	 */
	public Lines getLinesByProperty(String whereStr) {
		String hql = "FROM Lines lines WHERE 1=1 AND isdeleted = '0' " ;
		if(whereStr != null && whereStr.length() > 0){
			hql += whereStr;
		}
		List list = (List)getHibernateTemplate().find(hql);
		if(null == list || "".equals(list) || list.isEmpty()){
			return null;
		}
		return (Lines)list.get(0);
	}

	/**
	 * 名称转换 Id2Name
	 */
	public String id2Name(String id) throws DictDAOException {
		Lines lines = this.getLineById(id);
		if(null == lines || "".equals(lines)){
			return "";
		}
		return lines.getLineName();
	}

}
