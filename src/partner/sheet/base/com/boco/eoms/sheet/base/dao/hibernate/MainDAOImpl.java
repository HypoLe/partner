/*
 * Created on 2007-8-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.sheet.base.dao.hibernate;

import java.util.List;

import org.hibernate.HibernateException;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.sheet.base.model.BaseMain;

/**
 * <p>
 * Title:MainDAO基础方法，表单信息
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2007-8-22 10:12:52
 * </p>
 * 
 * @author 曲静波
 * @version 1.0
 *  
 */
public class MainDAOImpl extends MainDAO {

    /*
     * (non-Javadoc)
     * 
     * @see com.boco.eoms.sheet.base.dao.IMainDAO#deleteMain(java.lang.String)
     */
    public void deleteMain(String id, Object mainObject) throws HibernateException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.boco.eoms.sheet.base.dao.IMainDAO#loadSinglePO(java.lang.String)
     */
    public BaseMain loadSinglePO(String id, Object obj) throws HibernateException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.boco.eoms.sheet.base.dao.IMainDAO#loadSinglePOByProcessId(java.lang.String)
     */
    public BaseMain loadSinglePOByProcessId(String processId, Object obj)
            throws HibernateException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.boco.eoms.sheet.base.dao.IMainDAO#getMainName()
     */
//    public String getMainName() {
//        return "BaseMain";
//    }
    
    public BaseMain getMain(String id ,String className) throws Exception{
    	try{
    		BaseMain mainObject=null;
    		String pojoName = className.substring(className.lastIndexOf(".")+1);
	    	String hql = "from " + pojoName + " p where p.id= '" + id + "'";
	    	Object object=getHibernateTemplate().get(Class.forName(className),id);
	    	if(object!=null){
	    		mainObject=(BaseMain)object;
	    	}
	    	return mainObject;
    	}catch(Exception e){
    		throw e;
    	}
    }

	public int bulkUpdate(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int bulkUpdate(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List find(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public List find(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List find(String arg0, List arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public List findByVarParams(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageModel searchPaginated(String arg0, Object[] arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
