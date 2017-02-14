package com.boco.eoms.partner.process.dao.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;
import com.boco.eoms.partner.process.dao.PnrAndroidPhotoFileDao;
import com.boco.eoms.partner.process.model.PartnerProcessLink;

public class PnrAndroidPhotoFileDaoHibernate extends CommonGenericDaoImpl<PnrAndroidPhotoFile, String> implements PnrAndroidPhotoFileDao{

	@Override
	public void save(PnrAndroidPhotoFile photoFile, String data)
			throws SQLException {
		// TODO Auto-generated method stub
		String curTime = StaticMethod.getCurrentDateTime();
		if(photoFile.getId()!=null && !"".equals(photoFile.getId())) {
		
			photoFile.setCreateTime(curTime);
		}
		
		photoFile.setFileData(Hibernate.createClob(data));
		
		this.getHibernateTemplate().save(photoFile);
	}
	
   public Map getResources(Integer curPage, Integer pageSize, String whereStr) {
		
		List<PnrInspectTaskFile> list = this.getSession().createQuery(" from PnrAndroidPhotoFile "+whereStr).setFirstResult(curPage).setMaxResults(pageSize).list();
		
		int count = Integer.parseInt(this.getSession().createQuery(" select count(*) from PnrAndroidPhotoFile "+whereStr).uniqueResult().toString());
		
		HashMap map = new HashMap();
		map.put("total", new Integer(count));
		map.put("result", list);
		
		return map;
	}
}


