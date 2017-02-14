package com.boco.eoms.task.dao.hibernate;

import java.util.List;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.task.dao.IEomsTaskDao;
import com.boco.eoms.task.model.Eoms_Task_User;

public class EomsTaskDaoHibernate  extends BaseDaoHibernate implements IEomsTaskDao {

	public List getEomsTaskUserByUserid(String userid) {
		String hql="from  Eoms_Task_User eomstaskuser where eomstaskuser.managerid='"+userid+"'";
		List list=getHibernateTemplate().find(hql);
		return list;
	}
	
	public void saveEomsTask(List eomstaskuserlist,String userid) {
		
		
	      String hql="from  Eoms_Task_User eomstaskuser where eomstaskuser.managerid='"+userid+"'";
		 List eomslist =search(hql);
	
		
		for(int i=0;i<eomslist.size();i++)
		{

	  Eoms_Task_User eomstaskuser=(Eoms_Task_User)eomslist.get(i);
        getHibernateTemplate().delete(eomstaskuser);
	
		}
		
		
		for(int i=0;i<eomstaskuserlist.size();i++)
		{

	    Eoms_Task_User eomstaskuser=(Eoms_Task_User)eomstaskuserlist.get(i);
	    getHibernateTemplate().save(eomstaskuser);
	
		}
		
	}
	
	public List search( String hql) 
	
	{
		
		List list=getHibernateTemplate().find(hql);
	
		return list;
	}

	public List listTaskDrafter(String principal) {
		List list=getHibernateTemplate().find(
				  " from Eoms_Task_User etu where etu.userid ='"+principal+"'") ;
		return list;
	}
	
	
}
