package com.boco.eoms.partner.inspect.dao.hibernate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.lob.SerializableClob;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.partner.inspect.dao.IPnrInspectTaskFileDao;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;
import com.boco.eoms.partner.res.model.PnrResConfig;

public class PnrInspectTaskFileDaoHibernate extends BaseDaoHibernate implements
		IPnrInspectTaskFileDao {

	public void save(PnrInspectTaskFile taskFile, String data)
			throws SQLException {
		
		String curTime = StaticMethod.getCurrentDateTime();
		if(taskFile.getId()!=null && !"".equals(taskFile.getId())) {
			taskFile.setModifyTime(curTime);
		} else {
			taskFile.setCreateTime(curTime);
		}
		
		taskFile.setFileData(Hibernate.createClob(data));
		
		this.getHibernateTemplate().save(taskFile);

		// taskFile.setId("aaaaa");
		// taskFile.setFileData(Hibernate.createClob("1"));
		// this.getHibernateTemplate().save(taskFile);
		// getHibernateTemplate().saveOrUpdate(taskFile);
		// this.getHibernateTemplate().merge(taskFile);
		// this.getHibernateTemplate().flush();

		// this.getHibernateTemplate().refresh(taskFile, LockMode.UPGRADE);
		// SerializableClob sc = (SerializableClob)taskFile.getFileData();
		// Clob wrapclob = sc.getWrappedClob();
		// CLOB clob = (CLOB)wrapclob;
		// clob.putString(1,data);
		// getHibernateTemplate().saveOrUpdate(taskFile);
		// this.getHibernateTemplate().flush();
	}

	public List findByHQL(String hql) {
		List list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@SuppressWarnings("unchecked")
	public Map getResources(Integer curPage, Integer pageSize, String whereStr) {
		
		List<PnrInspectTaskFile> list = this.getSession().createQuery(" from PnrInspectTaskFile "+whereStr).setFirstResult(curPage).setMaxResults(pageSize).list();
		
		int count = Integer.parseInt(this.getSession().createQuery(" select count(*) from PnrInspectTaskFile "+whereStr).uniqueResult().toString());
		
		HashMap map = new HashMap();
		map.put("total", new Integer(count));
		map.put("result", list);
		
		return map;
	}
}
